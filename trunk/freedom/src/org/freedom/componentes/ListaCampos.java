/**
 * @version 01/08/2000 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva/Robson Sanchez/Anderson Sanchez
 *         <BR>
 * 
 * Projeto: Freedom <BR>
 * Pacote: org.freedom.componentes <BR>
 * Classe:
 * @(#)ListaCampos.java <BR>
 * 
 * Este programa é licenciado de acordo com a LPG-PC (Licença Pública Geral para
 * Programas de Computador), <BR>
 * versão 2.1.0 ou qualquer versão posterior. <BR>
 * A LPG-PC deve acompanhar todas PUBLICAÇÕES, DISTRIBUIÇÕES e REPRODUÇÕES deste
 * Programa. <BR>
 * Caso uma cópia da LPG-PC não esteja disponível junto com este Programa, você
 * pode contatar <BR>
 * o LICENCIADOR ou então pegar uma cópia em: <BR>
 * Licença: http://www.lpg.adv.br/licencas/lpgpc.rtf <BR>
 * Para poder USAR, PUBLICAR, DISTRIBUIR, REPRODUZIR ou ALTERAR este Programa é
 * preciso estar <BR>
 * de acordo com os termos da LPG-PC <BR>
 * <BR>
 * 
 * Classe de acesso direto a dados SQL.
 */

package org.freedom.componentes;

import java.awt.Component;
import java.awt.Container;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.text.JTextComponent;

import org.freedom.acao.CancelEvent;
import org.freedom.acao.CancelListener;
import org.freedom.acao.CarregaEvent;
import org.freedom.acao.CarregaListener;
import org.freedom.acao.DeleteEvent;
import org.freedom.acao.DeleteListener;
import org.freedom.acao.EditEvent;
import org.freedom.acao.EditListener;
import org.freedom.acao.InsertEvent;
import org.freedom.acao.InsertListener;
import org.freedom.acao.PostEvent;
import org.freedom.acao.PostListener;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;

public class ListaCampos extends Container implements PostListener,
		InsertListener, EditListener, CancelListener, DeleteListener,
		CarregaListener, MouseListener {
	/**
	 * ListaCampos em modo espera. Sem trasação para o momento.
	 */
	public static final int LCS_NONE = -1;

	/**
	 * ListaCampos em modo carregado. Sem trasação para o momento.
	 */
	public static final int LCS_SELECT = 0;

	/**
	 * ListaCampos em modo de edição. Trasação ativa, aguardando post ou
	 * cancela.
	 */
	public static final int LCS_EDIT = 1;

	/**
	 * ListaCampos em modo de inserção. Trasação ativa, aguardando post ou
	 * cancela.
	 */
	public static final int LCS_INSERT = 2;

	/**
	 * ListaCampos em modo carregado (veja detalhes). Sem trasação para o
	 * momento. Desabilitado as funcoes: post, edit, insert
	 */
	public static final int LCS_READ_ONLY = 3;

	/**
	 * ListaCampos em modo carregado (veja detalhes). Sem trasação para o
	 * momento. Habilitado as funcoes: post, edit, insert
	 */
	public static final int LCS_WRITE_READ = 4;

	/**
	 * Constante de erro do interbase quando a PK é duplicada.
	 */
	public static int FB_PK_DUPLA = 335544665;

	/**
	 * Constante de erro do interbase quando a integridade é quebrada.
	 */
	public static int FB_FK_INVALIDA = 335544466;

	private int lcState = LCS_NONE;

	private int lcStateAnt = LCS_NONE;

	private Connection con = null;

	private PreparedStatement sqlMax = null;

	private PreparedStatement sqlLC = null;

	private PreparedStatement sqlItens = null;

	private ResultSet rsMax = null;

	private ResultSet rsLC = null;

	private ResultSet rsItens = null;

	private String sPK = "";

	private String sSQLTab = "";

	private boolean bAutoInc;

	private boolean bConfirmaDelecao = true;

	private boolean readOnly = false;

	private boolean bCancelPost = false;

	private boolean bCancelEdit = false;

	private boolean bCancelCarrega = false;

	private boolean bCancelInsert = false;

	private boolean bCancelDelete = false;

	private boolean bCancelCancel = false;

	private String sTabela;

	public String sSigla;

	private String sArea;

	private String sSQLSelect = "";

	private String sSQLMax = "";

	private String sSQLInsert = "";

	private String sSQLUpdate = "";

	private String sSQLDelete = "";

	private String sOrdem = "";

	private Vector vCache = new Vector();

	private Navegador nvLC = null;

	private PostListener posLis = this;

	private EditListener editLis = this;

	private InsertListener insLis = this;

	private DeleteListener delLis = this;

	private CancelListener canLis = this;

	private CarregaListener carLis = this;

	private ListaCampos lcMaster = null;

	private ListaCampos lcM = null;

	private Vector vLcDetalhe = new Vector();

	private Tabela tab = null;

	private int[] iTipos = new int[1];

	private String[] sMascs = new String[1];

	private boolean bMaster = false;

	private boolean bDetalhe = false;

	private int iNumDescs = 0;

	private Vector vDescFK = new Vector();

	private String sWhereAdic = "";

	private boolean[] bCamposCanc = null;

	private boolean bQueryCommit = true;

	private boolean bQueryInsert = true;

	private boolean bUsaME = true;

	private boolean bTiraFI = false;

	private boolean bAutoLimpaPK = true;

	private boolean bPodeIns = true;

	private boolean bPodeExc = true;

	private boolean bPodeCommit = true;

	public Vector vTxtValor = null;

	private JTextComponent txtValor = null;

	private int iCodEmp = Aplicativo.iCodEmp;

	private int iCodFilial = 0;

	private int iNumPKs = 0;

	private Component cOwner = null;

	private boolean mensInserir = true;

	public static final byte DB_PF = 3; // Não implementado

	public static final byte DB_FK = 2; // Foreign Key

	public static final byte DB_PK = 1; // Primary Key

	public static final byte DB_SI = -3; // Single

	private String sSepU = ""; // Separador para query de update

	private String sSepI = ""; // Separador para query de insert

	private String sSepT = ""; // Separador para query da tabela

	private String sSepM = ""; // Separador para query de max

	private String sSepD = ""; // Separador para query de delete

	private String sCampoSQL = "";

	private String sWhereT = ""; //Where para query da tabela

	private String sSepParam = "";

	private int iParamPost = 0;

	private int iParamMax = 0;

	private int iParamC = 0;

	private int iParamDelete = 0;
	
	private int iOrdemT = 1;

	public int getCodEmp() {
		return iCodEmp;
	}

	/**
	 * Construtor da classe (nada em especial). <BR>
	 * Simplesmente carrega as heranças.
	 *  
	 */
	public ListaCampos(Component cOwner) {
		this.cOwner = cOwner;
	}

	public ListaCampos(Component cOwner, String sSig) {
		this.cOwner = cOwner;
		sSigla = sSig;
	}

	public void setNumPKs(int iNumPKs) {
		this.iNumPKs = iNumPKs;
	}

	public int getNumPKs() {
		return this.iNumPKs;
	}

	public void setReadOnly(boolean RO) {
		readOnly = RO;
		if (RO)
			setState(LCS_READ_ONLY);
		else {
			setState(LCS_WRITE_READ);
			setState(LCS_NONE);
		}
	}

	/**
	 * Retorna o estado atual do ListaCampos. <BR>
	 * Retorna o estado que o ListaCampos esta <BR>
	 * no momento, por exemplo: Se o ListaCampos <BR>
	 * ja realizou a query e ja distribuiu os <BR>
	 * resultados; o estado dele será LCS_SELECT. Para mais informações com as
	 * constantes, de uma <BR>
	 * olhada na ajuda delas.
	 * 
	 * @return LCS_NONE,LCS_INSERT,LCS_EDIT,LCS_SELECT. <BR>
	 *         Tipos especiais: <BR>
	 *         LCS_READ_ONLY,LCS_WRITE_READ
	 * @see #setState
	 *  
	 */
	public int getStatus() {
		return lcState;
	}

	/**
	 * Pega o status anterior.
	 * 
	 * @see #getStatus
	 *  
	 */
	public int getStatusAnt() {
		return lcStateAnt;
	}

	/**
	 * Retorno o owner do componente. <BR>
	 * O Owner de retorno dever ser um Frame <BR>
	 */
	public Component getOwnerTela() {
		Component cFrame = null;
		Component cRetorno = null;
		cFrame = this.getParent();
		if (cFrame != null) {
			for (int i = 1; 1 <= 10; i++) {
				if ((cFrame instanceof JFrame)
						|| (cFrame instanceof JInternalFrame)
						|| (cFrame instanceof JDialog)) {
					cRetorno = cFrame;
					break;
				}
				cFrame = cFrame.getParent();
			}
		}
		return cRetorno;
	}

	/**
	 * Ajusta o estado atual do ListaCampos. <BR>
	 * Ajusta o estado que o ListaCampos esta <BR>
	 * no momento, por exemplo: Se o ListaCampos <BR>
	 * ja realizou a query e ja distribuiu os <BR>
	 * resultados; o estado deverá ser LCS_SELECT. Para mais informações com as
	 * constantes, de uma <BR>
	 * olhada na ajuda delas.
	 * 
	 * @param Estado
	 *            LCS_NONE,LCS_INSERT,LCS_EDIT,LCS_SELECT. <BR>
	 *            Tipos especiais: <BR>
	 *            LCS_READ_ONLY,LCS_WRITE_READ
	 * @see #getStatus
	 *  
	 */
	public boolean setState(int iLc) {
		if (!(lcState == LCS_READ_ONLY) || iLc == LCS_WRITE_READ) {
			lcStateAnt = lcState;
			lcState = iLc;
		}
		if (nvLC != null) {
			if (lcState == LCS_NONE) {
				nvLC.visivel("SAVE", false); // Salvar
				nvLC.visivel("CANCEL", false); //Cancelar
				nvLC.visivel("DELETE", bPodeExc); // Excluir
				nvLC.visivel("NEW", bPodeIns); //Novo
				nvLC.visivel("EDIT", true); //Editar
			} else if (lcState == LCS_SELECT) {
				nvLC.visivel("SAVE", false); // Salvar
				nvLC.visivel("CANCEL", false); //Cancelar
				nvLC.visivel("DELETE", bPodeExc); // Excluir
				nvLC.visivel("NEW", bPodeIns); //Novo
				nvLC.visivel("EDIT", true); //Editar
			} else if (lcState == LCS_INSERT) {
				nvLC.visivel("SAVE", true); // Salvar
				nvLC.visivel("CANCEL", true); //Cancelar
				nvLC.visivel("DELETE", false); // Excluir
				nvLC.visivel("NEW", false); //Novo
				nvLC.visivel("EDIT", false); //Editar
			} else if (lcState == LCS_EDIT) {
				nvLC.visivel("SAVE", true); // Salvar
				nvLC.visivel("CANCEL", true); //Cancelar
				nvLC.visivel("DELETE", bPodeExc); // Excluir
				nvLC.visivel("NEW", bPodeIns); //Novo
				nvLC.visivel("EDIT", false); //Editar
			} else if (lcState == LCS_READ_ONLY) {
				if (nvLC != null) {
					if (nvLC.bDet) {
						nvLC.setAtivo(4, false); //Novo
						nvLC.setAtivo(5, false); // Excluir
						nvLC.setAtivo(6, false); //Editar
						nvLC.setAtivo(7, false); // Salvar
						nvLC.setAtivo(8, false); //Cancelar
					} else {
						nvLC.setAtivo(0, false); //Novo
						nvLC.setAtivo(1, false); // Excluir
						nvLC.setAtivo(2, false); //Editar
						nvLC.setAtivo(3, false); // Salvar
						nvLC.setAtivo(4, false); //Cancelar
					}
				}
			} else if (lcState == LCS_WRITE_READ) {
				if (nvLC != null) {
					if (nvLC.bDet) {
						nvLC.setAtivo(4, bPodeIns); //Novo
						nvLC.setAtivo(5, bPodeExc); // Excluir
						nvLC.setAtivo(6, true); //Editar
						nvLC.setAtivo(7, true); // Salvar
						nvLC.setAtivo(8, true); //Cancelar
					} else {
						nvLC.setAtivo(0, bPodeIns); //Novo
						nvLC.setAtivo(1, bPodeExc); // Excluir
						nvLC.setAtivo(2, true); //Editar
						nvLC.setAtivo(3, true); // Salvar
						nvLC.setAtivo(4, true); //Cancelar
					}
				}
			}
		}
		return true;
	}

	/**
	 * Ajusta a tabela para este ListaCampos. <BR>
	 * Ajusta a tabela para um ListaCampos Master.
	 * 
	 * @param Tabela
	 *            A tabela que desaja adicionar
	 * @see #getTab
	 *  
	 */
	public void setTabela(Tabela tb) {
		tab = tb;
		tab.addMouseListener(this);
	}

	public void setMensInserir(boolean mensInserir) {
		this.mensInserir = mensInserir;
	}

	public boolean getMensInserir() {
		return this.mensInserir;
	}

	public void adicDetalhe(ListaCampos lc) {
		if (lc != null) {
			vLcDetalhe.addElement(lc);
			bMaster = true;
		}
	}

	public void setMaster(ListaCampos lc) {
		lcMaster = lc;
		bDetalhe = true;
	}

	public ListaCampos getDetalhe(int ind) {
		return (ListaCampos) vLcDetalhe.elementAt(ind);
	}

	/**
	 * Retorna se o ListaCampos é Master. <BR>
	 * Retorna se o ListaCampos é Master, para <BR>
	 * entender isso é necessário ter um conhecimento <BR>
	 * sobre o conceiro Master-Detail, onde Master é <BR>
	 * a tebela mãe ex: Pedido e o Detail é a tabela <BR>
	 * filho ex: ITPedido.
	 * 
	 * @return Se verdeiro a tabela é Master <BR>
	 *         se false ela não é.<BR>
	 *         Atencao!! quando o retorno for false ela não é <BR>
	 *         necessáriamente Detail.
	 *  
	 */
	public ListaCampos getMaster() {
		return lcMaster;
	}

	/**
	 * Retorna tabela deste ListaCampos. <BR>
	 * Retorna a tabela de um ListaCampos Master.
	 * 
	 * @return Tabela que pertencente a este ListaCampos Master
	 * @see #setTabela
	 *  
	 */
	public Tabela getTab() {
		return tab;
	}

	/**
	 * Retorna sigla deste ListaCampos. <BR>
	 * Retorna a sigla da tabela do listacampos. <BR>
	 * Essa função é muito usada para definir o <BR>
	 * campo de codemp para FKs.
	 * 
	 * @return Uma string com 2 caracteres que representam a sigla da tabela.
	 * @see #ListaCampos
	 *  
	 */
	public String getSigla() {
		return sSigla;
	}

	/**
	 * Retorna area deste ListaCampos. <BR>
	 * Retorna a area da tabela do listacampos. <BR>
	 * Essa função eh muito usada para montar os <BR>
	 * nomes das tabelas. <BR>
	 * 
	 * @return Uma string com 2 caracteres que representam a area da tabela.
	 * @see #montaSql
	 *  
	 */
	public String getArea() {
		return sArea;
	}

	/**
	 * Ajusta o ListaCampos para limpar a PK. <BR>
	 * Ajusta o ListaCampos para limpar a PK caso <BR>
	 * não retorne linhas de dados do carregaDados.
	 * 
	 * @param AutoLimpaPK
	 *            Se true limpa a PK.
	 * @see #getAutoLimpaPK #carregaDados
	 *  
	 */

	public void setAutoLimpaPK(boolean bVal) {
		bAutoLimpaPK = bVal;
	}

	/**
	 * Ajusta o ListaCampos para permitir ou não a inserção. <BR>
	 * 
	 * @param bVal -
	 *            Se false não será possível criar novo registro.
	 * @see #getPodeIns
	 *  
	 */

	public void setPodeIns(boolean bVal) {
		bPodeIns = bVal;
		setState(lcState);
	}

	/**
	 * Retorna se o ListaCampos permite inserção. <BR>
	 * 
	 * @return bPodeIns - Se false não será possível criar novo registro.
	 * @see #setPodeIns
	 *  
	 */

	public boolean getPodeIns() {
		return bPodeIns;
	}

	/**
	 * Ajusta o ListaCampos para permitir ou não exclusão. <BR>
	 * 
	 * @param bVal -
	 *            Se false não será possível excluir registro.
	 * @see #getPodeExc
	 *  
	 */

	public void setPodeExc(boolean bVal) {
		bPodeExc = bVal;
		setState(lcState);
	}

	/**
	 * Retorna se o ListaCampos permite exclusão. <BR>
	 * 
	 * @return bPodeExc - Se false não será possível excluir registro.
	 * @see #setPodeExc
	 *  
	 */

	public boolean getPodeExc() {
		return bPodeExc;
	}

	/**
	 * Ajusta o ListaCampos para restrições de Commit no SELECT. <BR>
	 * Ajusta o ListaCampos para realizar ou não commit <BR>
	 * após a query.
	 * 
	 * @param Realizar
	 *            Se true realiza commit se false não realiza.
	 * @see #getQueryCommit
	 *  
	 */

	public void setQueryCommit(boolean bVal) {
		bQueryCommit = bVal;
	}

	/**
	 * Ajusta o ListaCampos para restrições de Commit. <BR>
	 * Ajusta o ListaCampos para realizar ou não commit <BR>
	 * após qualque SQL.
	 * 
	 * @param Realizar
	 *            Se true realiza commit se false não realiza.
	 * @see #getPodeCommit
	 *  
	 */

	public void setPodeCommit(boolean bVal) {
		bPodeCommit = bVal;
	}

	/**
	 * Retorna se o listaCampos vai limpar a PK. <BR>
	 * Retorna se o lisaCampos vai limpar a PK caso <BR>
	 * a SELECT não retorne nenhuma linha de dados <BR>
	 * no carregaDados.
	 * 
	 * @return AutoLimpaPK Se true limpa a PK.
	 * @see #setAutoLimpaPK #carregaDados
	 *  
	 */

	public boolean getAutoLimpaPK() {
		return bAutoLimpaPK;
	}

	/**
	 * Retorna true se o ListaCampos esta ajustado para QueryCommit. <BR>
	 * 
	 * @return QueryCommit.
	 * @see #setQueryCommit
	 *  
	 */
	public boolean getQueryCommit() {
		return bQueryCommit;
	}

	/**
	 * Retorna true se o ListaCampos esta ajustado para executar Commit. <BR>
	 * 
	 * @return PodeCommit.
	 * @see #setPodeCommit
	 *  
	 */
	public boolean getPodeCommit() {
		return bPodeCommit;
	}

	/**
	 * Ajusta o ListaCampos para restrições de Multi Empresa. <BR>
	 * Ajusta o ListaCampos para não realizar tratamento por <BR>
	 * código de empresa e filial.
	 * 
	 * @param Se
	 *            false não realiza tratamento, de true(padrão) realiza.
	 *  
	 */
	public void setUsaME(boolean bVal) {
		bUsaME = bVal;
	}

	/**
	 * Ajusta o ListaCampos para restrições de Filial. <BR>
	 * Ajusta o ListaCampos para não realizar tratamento por <BR>
	 * código de filial.
	 * 
	 * @param Se
	 *            false não realiza tratamento, de true(padrão) realiza.
	 *  
	 */
	public void setUsaFI(boolean bVal) {
		bTiraFI = !bVal;
	}

	/**
	 * Retorna se o ListaCampos tem restrições de Multi Empresa. <BR>
	 * 
	 * @return Se falso não realiza tratamento, de true(padrão) realiza.
	 *  
	 */
	public boolean getUsaFI() {
		return !bTiraFI;
	}

	/**
	 * Retorna se o ListaCampos tem restrições de Filial. <BR>
	 * 
	 * @return Se falso não realiza tratamento, de true(padrão) realiza.
	 *  
	 */
	public boolean getUsaME() {
		return bUsaME;
	}

	/**
	 * Ajusta o ListaCampos para restrições de Inserção. <BR>
	 * Ajusta o ListaCampos para realizar ou não carregaDados <BR>
	 * após a inserção.
	 * 
	 * @param Carregar
	 *            Se true executa o carregaDados se false não carrega.
	 * @see #carregaDados
	 * @see #getQueryInsert
	 *  
	 */
	public void setQueryInsert(boolean bVal) {
		bQueryInsert = bVal;
	}

	/**
	 * Retorna true se o ListaCampos esta ajustado para QueryInsert. <BR>
	 * 
	 * @return QueryInsert.
	 * @see #setQueryInsert
	 *  
	 */
	public boolean getQueryInsert() {
		return bQueryInsert;
	}

	/**
	 * Ajusta o navegador para este ListaCampos. <BR>
	 * Ajusta o navegador que disponibilizará os <BR>
	 * controles para gravar, excluir, desfazer...etc.
	 * 
	 * @param Navegador
	 *            A classe já instânciada do navegador.
	 *  
	 */
	public void setNavegador(Navegador nv) {
		nvLC = nv;
	}

	public Navegador getNavegador() {
		return nvLC;
	}

	/**
	 * Retorna o código da filial. <BR>
	 * Retorna o código da filial deviadamente tratado, <BR>
	 * ou seja foi vereficado se a tabela é ME ou não. <BR>
	 * IMPORTANTE: O código só é trabalhado após a execução <BR>
	 * da função setConexao.
	 * 
	 * @return Caso a tabela seja ME o código da matriz é retornado, <BR>
	 *         caso contrário é retornado a filial selecionada no login.
	 * @see #setConexao
	 *  
	 */
	public int getCodFilial() {
		return iCodFilial;
	}

	/**
	 * Retorna a condição adicional. <BR>
	 * Retorna a condição adicional ajustada para este <BR>
	 * lista campos. Geralmente esta condição vem herdada <BR>
	 * desde o JTextFieldPad porque quando ajustada pelo <BR>
	 * método do JTextFieldPad também é ajusta para a Dialog <BR>
	 * de consulta.
	 * 
	 * @return Condição adicional.
	 * @see #setWhereAdic
	 *  
	 */
	public String getWhereAdic() {
		return sWhereAdic;
	}

	/**
	 * Ajusta a condição adicional. <BR>
	 * Ajusta a condição adicional para este ListaCampos, <BR>
	 * bom isto é usado para ajustar uma condição que o <BR>
	 * ListaCampos não tenha montado sozinho. Geralmente <BR>
	 * esta condição vem herdada desde o JTextFieldPad porque <BR>
	 * quando ajustada pelo método do JTextFieldPad também <BR>
	 * é ajusta para a Dialog de consulta.
	 * 
	 * @param WhereAdic
	 *            Condição adicional.
	 * @see #getWhereAdic
	 *  
	 */
	public void setWhereAdic(String sW) {
		sWhereAdic = sW;
	}

	/**
	 * Adiciona dinamicamente o valor para o where. <BR>
	 * Busca na hora do carregadados() o valor where e adiciona-o no lugar de #_
	 * <BR>
	 * onde '_' pode ser S (String), D (Date) ou N (Outros). <BR>
	 * Ex: setDinWhereAdic("TESTE = #N",txtTeste); <BR>
	 *  
	 */
	public void setDinWhereAdic(String sDinWhere, JTextComponent jtValor) {
		sWhereAdic += (sWhereAdic.trim().equals("")
				|| sDinWhere.trim().equals("") ? "" : " AND ")
				+ sDinWhere;
		txtValor = jtValor;
		if (vTxtValor == null) {
			vTxtValor = new Vector();
		}
		vTxtValor.addElement(txtValor);
	}

	public void montaPostCarregaItens(ListaCampos lc) {
		GuardaCampo gcComp = null;
		ListaCampos lcM = lc.getMaster();
		if (lcM != null) {
			try {
				for (int i = 0; i < lcM.getComponentCount(); i++) {
					gcComp = (GuardaCampo) lcM.getComponent(i);
					if (gcComp.ehPK()) {
						if (gcComp.getTipo() == JTextFieldPad.TP_STRING)
							sqlItens.setString(iOrdemT, gcComp.getVlrString());
						else if (gcComp.getTipo() == JTextFieldPad.TP_INTEGER)
							sqlItens.setInt(iOrdemT, gcComp.getVlrInteger().intValue());
						else if (gcComp.getTipo() == JTextFieldPad.TP_DATE)
							sqlItens.setDate(iOrdemT, Funcoes.dateToSQLDate(gcComp.getVlrDate()));
						else if (gcComp.getTipo() == JTextFieldPad.TP_TIME)
							sqlItens.setTime(iOrdemT, Funcoes.dateToSQLTime(gcComp.getVlrDate()));
						iOrdemT++;
					}
				}
				montaPostCarregaItens(lcM);
			} catch (Exception err) {
				err.printStackTrace();
			}
		}

	}

	public void montaPostMax(ListaCampos lc) {
		GuardaCampo gcComp = null;
		ListaCampos lcM = lc.getMaster();
		if (lcM != null) {
			try {
				for (int i = 0; i < lcM.getComponentCount(); i++) {
					if (((GuardaCampo) lcM.getComponent(i)).ehPK()) {
						if (((GuardaCampo) lcM.getComponent(i)).getTipo() == JTextFieldPad.TP_INTEGER) {
							sqlMax.setInt(iParamMax, ((GuardaCampo) lcM.getComponent(i)).getVlrInteger().intValue());
							iParamMax++;
						} 
						else {
							sqlMax.setString(iParamMax, ((GuardaCampo) lcM.getComponent(i)).getVlrString());
							iParamMax++;
						}
					}
				}
				montaPostMax(lcM);
			} 
			catch (Exception err) {
				err.printStackTrace();
			}
		}

	}

	public void montaPostCircular4(ListaCampos lc) {
		Component comp = null;
		ListaCampos lcM = lc.getMaster();
		if (lcM != null) {
			try {
				for (int i = 0; i < lcM.getComponentCount(); i++) {
					comp = lcM.getComponent(i);
					if (((GuardaCampo) comp).ehPK()) {
						if (((GuardaCampo) comp).getTipo() == JTextFieldPad.TP_INTEGER) {
							sqlLC.setInt(iParamC, ((GuardaCampo) comp).getVlrInteger().intValue());
							iParamC++;
						} 
						else if (((GuardaCampo) comp).getTipo() == JTextFieldPad.TP_STRING) {
							sqlLC.setString(iParamC, ((GuardaCampo) comp).getVlrString());
							iParamC++;
						} 
						else if (((GuardaCampo) comp).getTipo() == JTextFieldPad.TP_DATE) {
							sqlLC.setDate(iParamC, Funcoes.dateToSQLDate(((GuardaCampo) comp).getVlrDate()));
							iParamC++;
						} 
						else if (((GuardaCampo) comp).getTipo() == JTextFieldPad.TP_TIME) {
							sqlLC.setTime(iParamC, Funcoes.dateToSQLTime(((GuardaCampo) comp).getVlrTime()));
							iParamC++;
						}

					}
				}
				montaPostCircular4(lcM);
			} 
			catch (Exception err) {
				err.printStackTrace();
			}
		}
	}

	/**
	 * Carrega itens do detalhe. <BR>
	 * Atualiza os dados em uma tabela devidamente adicionada <BR>
	 * pela função setTabela(). Geralmente este método é utilizado para
	 * atualizar os dados de um JTable em um form FDetalhe.
	 * 
	 * @see #setTabela
	 *  
	 */
	public void carregaItens() {
		iOrdemT = 1;
		GuardaCampo gcComp = null;
		if ((bDetalhe) & (tab != null)) {
			limpaCampos(true);
			tab.limpa();
			try {
				String sTmp = sSQLTab;

				for (int i = 0; i < getComponentCount(); i++) {
					gcComp = (GuardaCampo) getComponent(i);
					if (gcComp.ehFK()) {
						ListaCampos lcExt = gcComp.getCampo().getTabelaExterna();
						if (!lcExt.getWhereAdic().equals("")) {
							sTmp = lcExt.inDinWhereAdic(sTmp, lcExt.vTxtValor);
						}
					}
				}

				String sNovaSelect = inDinWhereAdic(sTmp, vTxtValor);
				sqlItens = con.prepareStatement(sNovaSelect);
				if (bUsaME && !bTiraFI) {
					sqlItens.setInt(iOrdemT, iCodEmp);
					iOrdemT++;
					sqlItens.setInt(iOrdemT, iCodFilial);
					iOrdemT++;
				}

				montaPostCarregaItens(this);

				rsItens = sqlItens.executeQuery();
				for (int iLin = 0; rsItens.next(); iLin++) {
					tab.adicLinha();
					for (int iCol = 0; iCol < (getComponentCount() + iNumDescs); iCol++) {
						if (rsItens.getString(iCol + 1) == null) {
							tab.setValor("", iLin, iCol);
						} 
						else if (iTipos[iCol] == JTextFieldPad.TP_STRING)
							tab.setValor(rsItens.getString(iCol + 1), iLin,iCol);
						else if (iTipos[iCol] == JTextFieldPad.TP_INTEGER)
							tab.setValor(new Integer(rsItens.getInt(iCol + 1)),iLin, iCol);
						else if (iTipos[iCol] == JTextFieldPad.TP_DATE)
							tab.setValor(Funcoes.sqlDateToStrDate(rsItens.getDate(iCol + 1)), iLin, iCol);
						else if (iTipos[iCol] == JTextFieldPad.TP_TIME)
							tab.setValor(Funcoes.sqlTimeToStrTime(rsItens.getTime(iCol + 1)), iLin, iCol);
						else if (iTipos[iCol] == JTextFieldPad.TP_DECIMAL)
							tab.setValor(Funcoes.setPontoDec(rsItens.getString(iCol + 1)), iLin, iCol);
						else if (iTipos[iCol] == JTextFieldPad.TP_NUMERIC)
							tab.setValor(Funcoes.setPontoDec(rsItens.getString(iCol + 1)), iLin, iCol);
						else if (iTipos[iCol] == JTextFieldPad.TP_BYTES)
							tab.setValor("# BINÁRIO #", iLin, iCol);
					}
				}
				if ((bQueryCommit) && (!con.getAutoCommit()) && bPodeCommit)
					con.commit();

			} 
			catch (SQLException err) {
				Funcoes.mensagemErro(cOwner, "Erro ao montar grid para tabela " + sTabela + "\n" + err.getMessage());
				err.printStackTrace();
			}
			carregaItem(0);
		}
	}

	/**
	 * Carrega um item da tabela. <BR>
	 * Carrega no detalhe o item da linha 'n' na tabela.
	 * 
	 * @see #setTabela
	 *  
	 */
	public boolean carregaItem(int ind) {
		boolean bRetorno = false;
		GuardaCampo gcCampo = null;
		int iSalto = 0;
		if ((tab != null) && (bDetalhe)) {
			if (tab.getNumLinhas() > 0) {
				for (int i = 0; i < getComponentCount(); i++) {
					gcCampo = ((GuardaCampo) getComponent(i));
//					String sTeste = gcCampo.getNomeCampo();
//					if(sTeste.equalsIgnoreCase("SEQPP"))
//						System.out.println("Teste");
					if (gcCampo.ehPK()) {
//						String sTestex =  tab.getValor(ind, i+iSalto)+"";
						gcCampo.getCampo().setVlrString("" + tab.getValor(ind, i+iSalto));
					}
					else
						iSalto++;
				}
				bRetorno = carregaDados();
			}
		}
		return bRetorno;
	}

	private String montaSubSelect(Component comp, int iNpk) {
		GuardaCampo gc = (GuardaCampo) comp;
		GuardaCampo gcFK = null;
		GuardaCampo gcFKCampo = null;
		String sRetorno = "";
		String sWhere = "";
		String sAnd = "";
		ListaCampos lcFK = gc.getCampo().getTabelaExterna();
		sAnd = "";
//		if (getNomeTabela().equals("EQPRODACESSO")) {
//			System.out.println("Teste");
//		}
		boolean bPrim = true;
		int i;
		int i2 = 0;
		int iPK = -1;
		int iFK = 0;
		for (i = 0; i < lcFK.getComponentCount(); i++) {
			gcFK = (GuardaCampo) lcFK.getComponent(i);
//			String sTeste = lcFK.getNomeTabela();
			if (gcFK.ehPK()) {
				/*
				 * Se for a primeira PK ele linka com o nome do campo que esta
				 * neste listaCampos, assim ele consegue fazer ex.:
				 * CODPROD=CODPRODPD
				 */

				if (iNpk > 1) {
					while ((getComponentCount() > i2) & (iPK <= iNpk)) {
//						String steste31 = ((GuardaCampo) getComponent(i2)).getNomeCampo();
						if ((((GuardaCampo) getComponent(i2)).ehPK()) || (((GuardaCampo) getComponent(i2)).ehFK()) ) {
							if(((GuardaCampo) getComponent(i2)).ehPK())
								iPK++;

							if (((GuardaCampo) getComponent(i2)).ehFK()) {
								String sTabelaExternaComp = ((GuardaCampo) getComponent(i2)).getCampo().getTabelaExterna().getNomeTabela();
								String sTabelaExternaFK = lcFK.getNomeTabela();

								if (sTabelaExternaComp.equals(sTabelaExternaFK)) {
									GuardaCampo gcPK = (GuardaCampo) getComponent(i2);
									GuardaCampo gcFK2 = (GuardaCampo) lcFK.getComponent(iFK);
									iFK++;
//									String steste1 = gcFK2.getNomeCampo();
//									String steste2 = gcPK.getNomeCampo();

									sWhere = sWhere + sAnd + gcFK2.getNomeCampo() + " = master." + gcPK.getNomeCampo();
									sAnd = " AND ";

								}
							}
						} 
						i2++;
					}
				} 
				else if (!bPrim)
					sWhere = sWhere + sAnd + gcFK.getNomeCampo() + " = master." + gcFK.getNomeCampo();
				else {
					sWhere = sWhere + sAnd + gcFK.getNomeCampo() + " = master." + gc.getNomeCampo();
					bPrim = false;
				}
				sAnd = " AND ";
			} 
			else if (gcFKCampo == null)
				gcFKCampo = (GuardaCampo) lcFK.getComponent(i);
		}
		sWhere += lcFK.getWhereAdic().equals("") ? "" : sAnd + lcFK.getWhereAdic();
		sRetorno = "(SELECT " + (gcFKCampo != null ? gcFKCampo : gcFK).getNomeCampo()
				+ " FROM "
				+ lcFK.getNomeTabela()
				+ " WHERE "
				+ (lcFK.getUsaME() ? "CODEMP=master.CODEMP"
						+ lcFK.getSigla()
						+ (lcFK.getUsaFI() ? " AND CODFILIAL=master.CODFILIAL"
								+ lcFK.getSigla() : "") + " AND " : "")
				+ sWhere + ")";
		return sRetorno;
	}

	public void setOrdem(String sOrd) {
		sOrdem = " ORDER BY " + sOrd;
	}

	public String inDinWhereAdic(String sVal, Vector vTxtVal) {
		if (vTxtVal == null)
			return sVal;
		String sValOrig = sVal;
		String sRet = "";
		for (int i = 0; i < vTxtVal.size(); i++) {
			txtValor = (JTextComponent) vTxtVal.elementAt(i);
			int iPos = sValOrig.indexOf("#");
			if (iPos == -1)
				return sValOrig;
			sRet = sValOrig.substring(0, iPos);
			switch (sValOrig.charAt(iPos + 1)) {
			case 'N':
				sRet += (txtValor.getText().trim().equals("") ? "0" : txtValor.getText().trim());
				break;
			case 'S':
				sRet += "'" + (txtValor.getText().trim().equals("") ? "" : txtValor.getText().trim()) + "'";
				break;
			case 'D':
				sRet += "'" + (txtValor.getText().trim().equals("") ? "" : Funcoes.strDateToStrDB(txtValor.getText().trim()))+ "'";
				break;
			}
			sRet += sValOrig.substring(iPos + 2);
			sValOrig = sRet;
		}
		return sRet;
	}

	public void montaTab() {
		Component comp = null;
		String sTitulo = "";
		String sSubSelect = "";
		String sNome = "";
		sSepT = " ";
		sWhereT = "";
		HashMap hmTabelasExternas = new HashMap();
		int i = 0;
		int iTot = 0;
		sSQLTab = "SELECT ";
		iTipos = new int[150];
		sMascs = new String[150];
		iTot = getComponentCount();
		iNumDescs = 0;
		vDescFK = new Vector();

//		if (getNomeTabela().equals("EQPRODACESSO")) {
//			System.out.println("Teste");
//		}
		while (i < iTot) {
			comp = getComponent(i - iNumDescs);
			sTitulo = ((GuardaCampo) comp).getTituloCampo();
			sNome = ((GuardaCampo) comp).getNomeCampo();
			iTipos[i] = ((GuardaCampo) comp).getTipo();
			if (((GuardaCampo) comp).getCampo() != null)
				sMascs[i] = ((GuardaCampo) comp).getCampo().getStrMascara();
			tab.adicColuna(sTitulo);
			sSQLTab += sSepT + sNome;
			sSepT = ",";
			i++;
			vDescFK.addElement(null);
			if (((GuardaCampo) comp).ehFK() && (((GuardaCampo) comp).getDescFK() != null)) {
				int iNpk = ((GuardaCampo) comp).getCampo().getTabelaExterna() != null ? ((GuardaCampo) comp).getCampo().getTabelaExterna().getNumPKs() : ((GuardaCampo) comp).getCampo().getListaCampos().getNumPKs();
				String sSigla = ((GuardaCampo) comp).getCampo().getTabelaExterna() != null ? ((GuardaCampo) comp).getCampo().getTabelaExterna().getSigla() : ((GuardaCampo) comp).getCampo().getListaCampos().getSigla();
				if (sSigla != null) {
					if (sSigla.length() > 0) {
						String sNomeTab = ((GuardaCampo) comp).getCampo().getListaCampos().getNomeTabela()+ sSigla;
						int iP = hmTabelasExternas.get(sNomeTab) == null ? 0 : ((Integer) (hmTabelasExternas.get(sNomeTab))).intValue();
						hmTabelasExternas.put(((GuardaCampo) comp).getCampo().getListaCampos().getNomeTabela()+ sSigla, new Integer(iP + 1));
						if (iNpk == (iP + 1)) {	//Implementado para incluir descrição da fk apenas após ultimo campo da pk.
							hmTabelasExternas.put(((GuardaCampo) comp).getCampo().getListaCampos().getNomeTabela(), new Integer(iP + 1));
							sSubSelect = montaSubSelect(comp, iNpk);
							if (sSubSelect.trim().length() != 0) {
								sSQLTab += sSepT + sSubSelect;
								sTitulo = ((GuardaCampo) comp).getDescFK().getLabel();
								tab.adicColuna(sTitulo);
								iTipos[i] = ((GuardaCampo) comp).getDescFK().getTipo();
								vDescFK.addElement(((GuardaCampo) comp).getDescFK());
								i++;
								iTot++;
								iNumDescs++;
							}
						}
					}
				}
			}
		}
		if (bTiraFI)
			sSQLTab += " FROM " + sTabela + " master WHERE master.CODEMP=?";
		// alterado aqui para gerar codemp dinâmico + iCodEmp;
		else if (bUsaME)
			sSQLTab += " FROM "
					+ sTabela
					+ " master WHERE master.CODEMP=?"
					/* + iCodEmp --código da empresa dinâmico */+ " AND master.CODFILIAL=?";
		else
			sSQLTab += " FROM " + sTabela + " master ";

		sSepT = (bTiraFI || bUsaME ? " AND " : " WHERE ");

		montaWhereCircular(this);

		sSQLTab += sWhereAdic.trim().equals("") ? "" : sSepT + sWhereAdic;

		if (sWhereT.length() > 0)
			sSQLTab += sWhereT + sOrdem;
	}

	public Vector getCamposPK() {
		Component comp = null;
		GuardaCampo gcCampo = null;
		Vector vRetorno = new Vector();
		try {
			for (int i = 0; i < getComponentCount(); i++) {
				comp = getComponent(i);
				if (comp instanceof GuardaCampo) {
					gcCampo = ((GuardaCampo) comp);
					if (gcCampo.ehPK())
						vRetorno.addElement(gcCampo);
				}
			}
		} 
		finally {
			comp = null;
			gcCampo = null;
		}
		return vRetorno;
	}

	public void montaWhereCircular(ListaCampos lc) {
		Component comp = null;
		ListaCampos lcM = lc.getMaster();

		if (lcM != null) {
			String sCampo = "";
			String sWhereEmp = "";
			for (int i2 = 0; i2 < lcM.getComponentCount(); i2++) {
				if (((GuardaCampo) lcM.getComponent(i2)).ehPK()) {
					sWhereT += sSepT + ((GuardaCampo) lcM.getComponent(i2)).getNomeCampo() + "=?";
					sSepT = " AND ";
				}
			}
			montaWhereCircular(lcM);
		}
	}

	public void montaWhereCircular2(ListaCampos lc) {
		Component comp = null;
		ListaCampos lcM = lc.getMaster();
//		if (lc.getNomeTabela().equals("PPITESTRUTURA")) {
//			System.out.println("teste");
//		}
		if (lcM != null) {
			if (bAutoInc) {
				String sCampo = "";
				String sWhereEmp = "";
				for (int i = 0; i < lcM.getComponentCount(); i++) {
					comp = lcM.getComponent(i);
					if (((GuardaCampo) comp).ehPK()) {
						sCampo = ((GuardaCampo) comp).getNomeCampo();
						sSQLMax += sSepM + sCampo + "=?";
						sSepM = " AND ";
					}
				}
			}
			for (int i = 0; i < lcM.getComponentCount(); i++) {
				comp = lcM.getComponent(i);
				if (((GuardaCampo) comp).ehPK()) {
					sCampoSQL = ((GuardaCampo) comp).getNomeCampo();
					sSQLUpdate += sSepU + sCampoSQL + "=?";
					sSQLSelect += sSepParam + sCampoSQL + "=?";
					sSQLDelete += sSepD + sCampoSQL + "=?";
					sSepD = sSepU = sSepParam = " AND ";
				}
			}
			montaWhereCircular2(lcM);
		}

	}

	public void montaSqlCircular2(ListaCampos lc) {
		Component comp = null;
		ListaCampos lcM = lc.getMaster();
		if (lcM != null) {
			String sCampo = "";
			String sWhereEmp = "";
			for (int i = 0; i < lcM.getComponentCount(); i++) {
				comp = lcM.getComponent(i);
				if (((GuardaCampo) comp).ehPK()) {
					sCampo = ((GuardaCampo) comp).getNomeCampo();
					sSQLInsert += sSepI + sCampo;
					sSepI = ",";
					if (((GuardaCampo) comp).ehFK()) {
						ListaCampos lcExt = ((GuardaCampo) comp).getCampo().getTabelaExterna();
						if ((lcExt != null) && lcExt.getUsaME()) {
							sSQLInsert += ",CODEMP" + lcExt.getSigla() + (lcExt.getUsaFI() ? ",CODFILIAL" + lcExt.getSigla() : "");
							if (!((GuardaCampo) comp).ehPK())
								sSQLUpdate += ",CODEMP" + lcExt.getSigla() + "=?" + (lcExt.getUsaFI() ? ",CODFILIAL" + lcExt.getSigla() + "=?" : "");
						}
					}

				}
			}
			montaSqlCircular2(lcM);
		}
	}

	public void montaSqlCircular1(ListaCampos lc) {
		Component comp = null;
		HashMap hmTabelasExternas = new HashMap();
		lcM = lc.getMaster();
		if (lcM != null) {
			for (int i = 0; i < lcM.getComponentCount(); i++) {
				comp = lcM.getComponent(i);
				if (((GuardaCampo) comp).ehPK()) {
					sSQLInsert += sSepParam + "?";
					sSepParam = ",";
					if (((GuardaCampo) comp).ehFK()) {
						String sSigla = ((GuardaCampo) comp).getCampo().getTabelaExterna() != null ? ((GuardaCampo) comp).getCampo().getTabelaExterna().getSigla() : ((GuardaCampo) comp).getCampo().getListaCampos().getSigla();
						if (sSigla != null) {
							if (sSigla.length() > 0) {
								String sNomeTab = ((GuardaCampo) comp).getCampo().getListaCampos().getNomeTabela();
								if (hmTabelasExternas.get(sNomeTab) == null) {
									hmTabelasExternas.put(((GuardaCampo) comp).getCampo().getListaCampos().getNomeTabela(), new Integer(i));
									sSQLInsert += sSepParam + "?,?";
								}
							}
						}
					}
				}
			}
			montaSqlCircular1(lcM);
		}
	}

	public void montaSql(boolean bAuto, String sTab, String sA) {

		sSepParam = "";
		sSepD = "";
		sSepU = "";
		sSepI = "";
		sSepM = "";
		sCampoSQL = "";
		String sWhereEmp = "";

		bAutoInc = bAuto;
		sArea = sA;
		sTabela = sArea + sTab;
		sPK = retPK();
		this.iNumPKs = 0;

		if (bAutoInc) {
			/*
			 * Bom esse negocio que vem ai é para colar o codemp e o codfilial
			 * caso seja necessário e se não for necessário, colocar o 'WHERE'
			 * se este listacampos não for detalhe....porque se for detalhe é
			 * preciso colocar as pks do listacampos master.
			 */
			if (bTiraFI)
				sSQLMax = "SELECT MAX(" + sPK + ") FROM " + sTabela
						+ " WHERE CODEMP=?" /* + iCodEmp */;
			else if (bUsaME)
				sSQLMax = "SELECT MAX(" + sPK + ") FROM " + sTabela
						+ " WHERE CODEMP=?" + /* iCodEmp + */" AND CODFILIAL=?";
			else
				sSQLMax = "SELECT MAX(" + sPK + ") FROM " + sTabela
						+ (bDetalhe ? " WHERE " : "");
		}
		if (bTiraFI)
			sSQLInsert = "INSERT INTO " + sTabela + " (CODEMP,";
		else if (bUsaME)
			sSQLInsert = "INSERT INTO " + sTabela + " (CODEMP,CODFILIAL,";
		else
			sSQLInsert = "INSERT INTO " + sTabela + " (";

//		if (sTabela.equals("EQPRODPLAN")) {
//			System.out.println("teste");
//		}

		sSQLUpdate = "UPDATE " + sTabela + " SET  ";
		sSQLSelect = "SELECT ";
		sSQLDelete = "DELETE";

		Component comp = null;

		if ((bDetalhe) && (lcMaster != null)) {
			montaSqlCircular2(this);
		}

		sSepParam = "";
		for (int i = 0; i < getComponentCount(); i++) {
			comp = getComponent(i);
			GuardaCampo gcCampo = ((GuardaCampo) comp);
			sCampoSQL = gcCampo.getNomeCampo();
			if (!gcCampo.getSoLeitura()) {
				if (!gcCampo.ehPK()) {
					sSQLUpdate += sSepU + sCampoSQL + "=?";
					sSepU = ",";
				}
				sSQLInsert += sSepI + sCampoSQL;
				if (gcCampo.ehFK()) {
					ListaCampos lcExt = gcCampo.getCampo().getTabelaExterna();
					if ((lcExt != null) && lcExt.getUsaME()) {
						sSQLInsert += ",CODEMP" + lcExt.getSigla() + (lcExt.getUsaFI() ? ",CODFILIAL" + lcExt.getSigla() : "");
						if (!gcCampo.ehPK())
							sSQLUpdate += ",CODEMP" + lcExt.getSigla() + "=?" + (lcExt.getUsaFI() ? ",CODFILIAL" + lcExt.getSigla() + "=?" : "");
					}
				}
				sSepI = ",";
			}

			if (gcCampo.ehPK())
				this.iNumPKs++;

			sSQLSelect += sSepParam + sCampoSQL;
			sSepParam = ",";
		}
		sSepParam = "";

		sWhereEmp = bUsaME ? "CODEMP=?" + (bTiraFI ? "" : " AND CODFILIAL = ?") : "";

		sSQLUpdate += " WHERE ";
		sSQLDelete += " FROM " + sTabela + " WHERE " + sWhereEmp;
		sSQLSelect += " FROM " + sTabela + " WHERE " + sWhereEmp;

/*		if(getNomeTabela().equals("EQPRODPLAN")){
			System.out.println("TESTE:");
		}*/
		sSepD = sSepM = sSepParam = (bTiraFI || bUsaME ? " AND " : "");

		if (!sWhereAdic.trim().equals("")) {
			sSQLSelect += sSepParam + sWhereAdic;
			sSepParam = " AND ";
		}

		//	Monta o where com as pks da master:

		/*if (getNomeTabela().equals("PPITESTRUTURA")) {
			System.out.println("teste");
		}*/

		sSepU = "";
		if ((bDetalhe) && (lcMaster != null)) {
			montaWhereCircular2(this);
		}

		//Coloca no where as pks deste lista campos:

		for (int i = 0; i < getComponentCount(); i++) {
			GuardaCampo gcCampo = ((GuardaCampo) getComponent(i));
			ListaCampos lcExt = null;
			if (gcCampo.ehPK()) {
				if (bDetalhe) {
					gcCampo.getCampo().cancelaDLF2();
				}
				if (!gcCampo.getSoLeitura()) {
					sCampoSQL = gcCampo.getNomeCampo();
					sSQLUpdate += sSepU + sCampoSQL + "=?";
					sSQLDelete += sSepD + sCampoSQL + "=?";
					if (gcCampo.ehFK()) {
						lcExt = gcCampo.getCampo().getTabelaExterna();
						if ((lcExt != null) && lcExt.getUsaME()) {
							sSQLUpdate += " AND CODEMP" + lcExt.getSigla() + "=?" + (lcExt.getUsaFI() ? " AND CODFILIAL" + lcExt.getSigla() + "=?" : "");
							sSQLDelete += " AND CODEMP" + lcExt.getSigla() + "=?" + (lcExt.getUsaFI() ? " AND CODFILIAL" + lcExt.getSigla() + "=?" : "");
						}
					}
					sSepU = " AND ";
				}
				sSQLSelect += sSepParam + sCampoSQL + "=?";
				if (gcCampo.ehFK()) {
					lcExt = gcCampo.getCampo().getTabelaExterna();
					if ((lcExt != null) && lcExt.getUsaME()) {
						sSQLSelect += " AND CODEMP" + lcExt.getSigla() + "=?" + (lcExt.getUsaFI() ? " AND CODFILIAL" + lcExt.getSigla() + "=?" : "");
					}
				}
				sSepD = sSepParam = " AND ";
			}
		}

		if (bTiraFI)
			sSQLUpdate += sSepU + "CODEMP=?";
		else if (bUsaME)
			sSQLUpdate += sSepU + "CODEMP=?" + " AND CODFILIAL=?";
		else
			sSQLUpdate += "";

		if (bTiraFI)
			sSQLInsert += ") VALUES (?,";
		else if (bUsaME)
			sSQLInsert += ") VALUES (?, ?,";
		else
			sSQLInsert += ") VALUES (";

		sSepParam = "";

		String sSQLX = sSQLInsert;
		if ((bDetalhe) & (lcMaster != null)) {
			montaSqlCircular1(this);
		}
		for (int i = 0; i < getComponentCount(); i++) {
			GuardaCampo gcCampo = ((GuardaCampo) getComponent(i));
			String sNomeCampo = gcCampo.getNomeCampo();

			if (!gcCampo.getSoLeitura()) {
				sSQLInsert = sSQLInsert + sSepParam + "?";
				if (gcCampo.ehFK()) {
					ListaCampos lcExt = gcCampo.getCampo().getTabelaExterna();
					if ((lcExt != null) && lcExt.getUsaME()) {
						sSQLInsert += ",?" + (lcExt.getUsaFI() ? ",?" : "");
					}
				}
				sSepParam = ",";
			}
		}

		sSQLInsert = sSQLInsert + ")";

		bCamposCanc = new boolean[getComponentCount()];

	}

	public void first() {
		if ((bDetalhe) & (tab != null) & (tab.getNumLinhas() > 0)) {
			tab.setLinhaSel(0);
			carregaItem(0);
		}
	}

	public void prior() {
		int iLin = 0;
		if ((bDetalhe) & (tab != null) & (tab.getNumLinhas() > 0)) {
			iLin = getNumLinha();
			if (iLin > 0) {
				tab.setLinhaSel(iLin - 1);
				carregaItem(iLin - 1);
			}
		}
	}

	public void next() {
		int iLin = 0;
		int iNumLinhas = 0;
		if ((bDetalhe) && (tab != null)) {
			iNumLinhas = tab.getNumLinhas();
			if (iNumLinhas > 0) {
				iLin = getNumLinha();
				if (iLin < (iNumLinhas - 1)) {
					tab.setLinhaSel(iLin + 1);
					carregaItem(iLin + 1);
				}
			}
		}
	}

	public void last() {
		if ((bDetalhe) & (tab != null) & (tab.getNumLinhas() > 0)) {
			tab.setLinhaSel(tab.getNumLinhas() - 1);
			carregaItem(tab.getNumLinhas() - 1);
		}
	}

	private int getNumLinha() {
		int iCol = -1;
		int iRetorno = -1;
		for (int i = 0; i < getComponentCount(); i++) {
			if (((GuardaCampo) getComponent(i)).ehPK()) {
				iCol = i;
				break;
			}
		}
		if (iCol >= 0) {
			for (int i = 0; i < tab.getNumLinhas(); i++) {
				if (((GuardaCampo) getComponent(iCol)).getCampo().getText().trim().compareTo(("" + tab.getValor(i, iCol)).trim()) == 0) {
					tab.setLinhaSel(i);
					iRetorno = i;
					break;
				}
			}
		}
		return iRetorno;
	}

	public boolean carregaDados() {
		iParamC = 1;
		boolean bResultado = true;
		Component comp = null;
		Component compFocus = null;
		fireBeforeCarrega();
		if (bCancelCarrega) {
			bCancelCarrega = false;
			bResultado = false;
		} else if (lcState == LCS_EDIT) {
			if (Funcoes.mensagemConfirma(cOwner,"Registro ainda não foi salvo! Deseja salvar?") == 0) {
				cancel(false);
				post();
			} else {
				cancel(true);
				bResultado = false;
			}
		}
		vCache = new Vector();
		if (bResultado) {
			if (con == null) {
				Funcoes.mensagemErro(this, "Conexão nula!!");
				return false;
			}
			try {
				String sNovaSelect = inDinWhereAdic(sSQLSelect, vTxtValor);
				sqlLC = con.prepareStatement(sNovaSelect);
				if (bUsaME) {
					sqlLC.setInt(iParamC, iCodEmp);
					iParamC++;
				}
				if (bUsaME && !bTiraFI) {
					sqlLC.setInt(iParamC, iCodFilial);
					iParamC++;
				}
				if ((bDetalhe) && (lcMaster != null)) {
					montaPostCircular4(this);

				}

				bResultado = montaPostCircular5(this);
				if (!bResultado)
					return bResultado;

				rsLC = sqlLC.executeQuery();

				if (rsLC.next()) {
					for (int i = 0; i < getComponentCount(); i++) {
						comp = getComponent(i);
						if (!bCamposCanc[i]) {
							if (((GuardaCampo) comp).getTipo() == JTextFieldPad.TP_BYTES) {
								Blob bVal = rsLC.getBlob(((GuardaCampo) comp).getNomeCampo());
								if (bVal != null) {
									((GuardaCampo) comp).setVlrBytes(bVal.getBinaryStream());
								}
							} 
							else if (rsLC.getString(((GuardaCampo) comp).getNomeCampo()) != null) {
								if (((GuardaCampo) comp).getTipo() == JTextFieldPad.TP_INTEGER) {
									((GuardaCampo) comp).setVlrInteger(new Integer(rsLC.getInt(((GuardaCampo) comp).getNomeCampo())));
								} 
								else if (((GuardaCampo) comp).getTipo() == JTextFieldPad.TP_STRING) {
									((GuardaCampo) comp).setVlrString(rsLC.getString(((GuardaCampo) comp).getNomeCampo()));
								} 
								else if (((GuardaCampo) comp).getTipo() == JTextFieldPad.TP_DECIMAL) {
									((GuardaCampo) comp).setVlrBigDecimal(new java.math.BigDecimal(rsLC.getString(((GuardaCampo) comp).getNomeCampo())));
								} 
								else if (((GuardaCampo) comp).getTipo() == JTextFieldPad.TP_NUMERIC) {
									((GuardaCampo) comp).setVlrBigDecimal(new java.math.BigDecimal(rsLC.getString(((GuardaCampo) comp).getNomeCampo())));
								} 
								else if (((GuardaCampo) comp).getTipo() == JTextFieldPad.TP_DOUBLE) {
									((GuardaCampo) comp).setVlrDouble(new Double(rsLC.getDouble(((GuardaCampo) comp).getNomeCampo())));
								} 
								else if (((GuardaCampo) comp).getTipo() == JTextFieldPad.TP_DATE) {
									((GuardaCampo) comp).setVlrString(Funcoes.sqlDateToStrDate(rsLC.getDate(((GuardaCampo) comp).getNomeCampo())));
								} 
								else if (((GuardaCampo) comp).getTipo() == JTextFieldPad.TP_TIME) {((GuardaCampo) comp).setVlrString(Funcoes.sqlTimeToStrTime(rsLC.getTime(((GuardaCampo) comp).getNomeCampo())));
								}
							} 
							else
								((GuardaCampo) comp).limpa();
							if (((GuardaCampo) comp).ehFK())
								((GuardaCampo) comp).atualizaFK();
						} 
						else if (!bCamposCanc[i]) {
							((GuardaCampo) comp).limpa();
						}
					}
					bResultado = true;
				} 
				else {
					if (!this.bPodeIns) {
						limpaCampos(false);
						limpaDetalhes();
						Funcoes.mensagemInforma(cOwner,"Registro não foi encontrado!");
					}
					bResultado = false;
				}
				if ((bQueryCommit) && (!con.getAutoCommit()) && bPodeCommit)
					con.commit(); //MOD
			} catch (SQLException err) {
				//JOptionPane.showMessageDialog(null,"Erro ao carregar dados da
				// tabela: "+sTabela+"\n"+err.getMessage());
				Funcoes.mensagemErro(cOwner,
						"Erro ao carregar dados da tabela: " + sTabela + "\n"
								+ err.getMessage());
				err.printStackTrace();
				return false;
			}

			if (bResultado) {
				if (lcState != LCS_SELECT)
					setState(LCS_SELECT);
			} else {
				if (readOnly) {
					limpaCampos(bAutoLimpaPK);
				} else {
					if (lcState != LCS_INSERT && bPodeIns) {
						if (!mensInserir)
							insert(false);
						else if (Funcoes.mensagemConfirma(cOwner,
								"Registro não encontrado! Inserir?") == 0) {
							insert(false);
						}
					} else {
						bResultado = false;
					}
				}
			}
		}
		fireAfterCarrega(bResultado);
		return bResultado;
	}

	public void limpaTudo() {
		if (vCache != null)
			vCache.clear();
		if (getComponentCount() > 0)
			removeAll();
		if (lcMaster != null) {
			lcMaster.removeDetalhe(this);
		}
		lcMaster = null;
		if (vDescFK != null)
			vDescFK.clear();
		if (vTxtValor != null)
			vTxtValor.clear();
		sMascs = new String[1];
	}

	public void removeDetalhe(ListaCampos lc) {
		if (vLcDetalhe != null)
			vLcDetalhe.remove(lc);
	}

	public void limpaDetalhes() {
		if (vLcDetalhe != null)
			for (int i = 0; i < vLcDetalhe.size(); i++) {
				if (vLcDetalhe.elementAt(i) instanceof ListaCampos)
					((ListaCampos) vLcDetalhe.elementAt(i)).limpaCampos(true);
			}
	}

	public String getNovoCodigo() {
		String retorno = "1";
		iParamMax = 1;
		if (bAutoInc) {
			try {
				if (con == null)
					Funcoes.mensagemInforma(cOwner, "Não criou a conexão"); //JOptionPane.showMessageDialog(
				// null,
				// "Não
				// criou
				// a
				// conexão");
				sqlMax = con.prepareStatement(sSQLMax);
				if (bUsaME) {
					sqlMax.setInt(iParamMax, iCodEmp);
					iParamMax++;
				}
				if (bUsaME && !bTiraFI) {
					sqlMax.setInt(iParamMax, iCodFilial);
					iParamMax++;
				}
				if (bDetalhe) {
					montaPostMax(this);
				}

				rsMax = sqlMax.executeQuery();
				// con.commit();
				if (rsMax == null)
					Funcoes.mensagemInforma(cOwner, "RS NULL");//JOptionPane.showMessageDialog(
				// null, "RS
				// NULL");
				else {
					try {
						while (rsMax.next()) {
							retorno = rsMax.getString("MAX");
						}

						if (retorno.trim().length() <= 0) {
							retorno = "1";
						} else {
							retorno = "" + (Integer.parseInt(retorno) + 1);
						}
					} catch (Exception ex) {
						retorno = "1";
					}
				}
				//          rsMax.close();
				//          sqlMax.close();
				if ((bQueryCommit) && (!con.getAutoCommit()) && bPodeCommit)
					con.commit(); //MOD
			} catch (SQLException err) {
				Funcoes.mensagemErro(cOwner,
						"Erro do getNovoCodigo da Tabela: " + sTabela + "\n"
								+ err.getMessage());
				err.printStackTrace();
			}
		} else
			retorno = "";

		return retorno;
	}

	public String retPK() {
		Component comp = null;
		String retorno = "";
		for (int i = 0; i < getComponentCount(); i++) {
			comp = getComponent(i);
			if (((GuardaCampo) comp).ehPK()) {
				retorno = ((GuardaCampo) comp).getNomeCampo();
				break;
			}
		}
		return retorno;
	}

	public static int getMasterFilial(String sT) {
		int iRet = Aplicativo.tbObjetos.getUsoMe(sT) ? Aplicativo.iCodFilialMz
				: Aplicativo.iCodFilial;
		return iRet;
	}

	public void setConexao(Connection cn) {
		con = cn;
		if (bUsaME)
			iCodFilial = getMasterFilial(sTabela);
		System.out.println("[TABELA: " + sTabela + ", Filial F: "
				+ Aplicativo.iCodFilial + " Filial M: "
				+ Aplicativo.iCodFilialMz + "]");
		System.out.println("SELECT -> " + sSQLSelect);
		System.out.println("INSERT  -> " + sSQLInsert);
		System.out.println("DELETE  -> " + sSQLDelete);
		System.out.println("UPDATE  -> " + sSQLUpdate);
		System.out.println("TAB  -> " + sSQLTab);
		System.out.println("MAX  -> " + sSQLMax + "\n");
	}

	public void limpaCampos(boolean bLimpaPK) {
		Component comp = null;
		for (int i = 0; i < getComponentCount(); i++) {
			comp = getComponent(i);
			if (((GuardaCampo) comp).ehPK()) {
				if (bLimpaPK) {
					((GuardaCampo) comp).limpa();
				}
			} else {
				((GuardaCampo) comp).limpa();
			}
		}
	}

	public JTextFieldPad getCampo(String sNome) {
		JTextFieldPad retorno = null;
		Component comp = null;
		for (int i = 0; i < getComponentCount(); i++) {
			comp = getComponent(i);
			if ((((GuardaCampo) comp).getNomeCampo().toLowerCase().equals(sNome
					.toLowerCase()))
					&& (((GuardaCampo) comp).getCampo() != null)) {
				retorno = ((GuardaCampo) comp).getCampo();
				break;
			}
		}
		return retorno;

	}

	public boolean montaPostCircular5(ListaCampos lc) {
		Component comp = null;
		ListaCampos lcM = lc;
		if (lcM != null) {
			try {
				for (int i = 0; i < lcM.getComponentCount(); i++) {
					comp = lcM.getComponent(i);
					if (((GuardaCampo) comp).ehPK()) {
						if (((GuardaCampo) comp).ehNulo()) {
							System.out.println("Campo nulo : "
									+ ((GuardaCampo) comp).getNomeCampo());
							System.out.println("ListaCampos: "
									+ this.getNomeTabela());
							return false;
						}
						if (Funcoes.contaChar(sSQLSelect, '?') >= iParamC) {
							if (((GuardaCampo) comp).getTipo() == JTextFieldPad.TP_INTEGER) {
//								String sTeste = lcM.getNomeTabela();
//								System.out.println("TABELA NA POST5 " + sTeste);
//								String sTeste2 = ((GuardaCampo) comp).getNomeCampo();
								sqlLC.setInt(iParamC, ((GuardaCampo) comp).getVlrInteger().intValue());
								vCache.addElement(((GuardaCampo) comp).getVlrInteger());
								iParamC++;
							} else if (((GuardaCampo) comp).getTipo() == JTextFieldPad.TP_STRING) {
								sqlLC.setString(iParamC, ((GuardaCampo) comp)
										.getVlrString());
								vCache.addElement(((GuardaCampo) comp)
										.getVlrString());
								iParamC++;
							} else if (((GuardaCampo) comp).getTipo() == JTextFieldPad.TP_DATE) {
								sqlLC.setDate(iParamC, Funcoes
										.dateToSQLDate(((GuardaCampo) comp)
												.getVlrDate()));
								vCache.addElement(((GuardaCampo) comp)
										.getVlrDate());
								iParamC++;
							} else if (((GuardaCampo) comp).getTipo() == JTextFieldPad.TP_TIME) {
								sqlLC.setTime(iParamC, Funcoes
										.dateToSQLTime(((GuardaCampo) comp)
												.getVlrTime()));
								vCache.addElement(((GuardaCampo) comp)
										.getVlrTime());
								iParamC++;
							}

							if (((GuardaCampo) comp).ehFK()) {
								ListaCampos lcExt = ((GuardaCampo) comp)
										.getCampo().getTabelaExterna();
								if (lcExt != null) {
//									if (lcM.getNomeTabela().equals("PPESTRUFASE")) {
//										System.out.println("teste");
//									}
									if (lcExt.getUsaME() && lcExt.getUsaFI()) {
										if (!((GuardaCampo) comp)
												.getSoLeitura()) {
											if (((GuardaCampo) comp).ehNulo()) {
												if (Funcoes.contaChar(
														sSQLSelect, '?') >= iParamC)
													sqlLC.setNull(iParamC,
															Types.INTEGER);
												iParamC++;
												if (Funcoes.contaChar(
														sSQLSelect, '?') >= iParamC)
													sqlLC.setNull(iParamC,
															Types.INTEGER);
												iParamC++;
											} else {
												if (Funcoes.contaChar(
														sSQLSelect, '?') >= iParamC)
													sqlLC.setInt(iParamC,
															iCodEmp);
												iParamC++;
												if (Funcoes.contaChar(
														sSQLSelect, '?') >= iParamC)
													sqlLC.setInt(iParamC, lcExt
															.getCodFilial());
												iParamC++;
											}
										}
									}
								}
							}
						}
					}
				}
				montaPostCircular5(lcM.getMaster());
			} catch (Exception err) {
				err.printStackTrace();
			}
		}
		return true;
	}

	public void montaPostCircular(ListaCampos lc) {
		Component comp = null;
		ListaCampos lcM = lc.getMaster();
		if (lcM != null) {
			try {
				for (int iMaster = 0; iMaster < lcM.getComponentCount(); iMaster++) {
					comp = lcM.getComponent(iMaster);
					String nome = ((GuardaCampo) comp).getNomeCampo();
					if (((GuardaCampo) comp).ehPK()) {
						if (((GuardaCampo) comp).ehNulo()) {
							if (((GuardaCampo) comp).getTipo() == JTextFieldPad.TP_INTEGER) {
								sqlLC.setNull(iParamPost, Types.INTEGER);
							} else if (((GuardaCampo) comp).getTipo() == JTextFieldPad.TP_STRING) {
								sqlLC.setNull(iParamPost, Types.CHAR);
							} else if (((GuardaCampo) comp).getTipo() == JTextFieldPad.TP_DECIMAL) {
								sqlLC.setNull(iParamPost, Types.DECIMAL);
							} else if (((GuardaCampo) comp).getTipo() == JTextFieldPad.TP_NUMERIC) {
								sqlLC.setNull(iParamPost, Types.NUMERIC);
							} else if (((GuardaCampo) comp).getTipo() == JTextFieldPad.TP_DOUBLE) {
								sqlLC.setNull(iParamPost, Types.DOUBLE);
							} else if (((GuardaCampo) comp).getTipo() == JTextFieldPad.TP_DATE) {
								sqlLC.setNull(iParamPost, Types.DATE);
							} else if (((GuardaCampo) comp).getTipo() == JTextFieldPad.TP_TIME) {
								sqlLC.setNull(iParamPost, Types.TIME);
							} else if (((GuardaCampo) comp).getTipo() == JTextFieldPad.TP_BYTES) {
								if (((PainelImagem) ((GuardaCampo) comp)
										.getComponente()).foiAlterado()) {
									sqlLC.setNull(iParamPost, Types.BINARY);
								} else {
									sqlLC.setNull(iParamPost, Types.BLOB);
								}
							}
						} else {
							if (((GuardaCampo) comp).getTipo() == JTextFieldPad.TP_INTEGER) {
								sqlLC.setInt(iParamPost, ((GuardaCampo) comp)
										.getVlrInteger().intValue());
							} else if (((GuardaCampo) comp).getTipo() == JTextFieldPad.TP_STRING) {
								sqlLC.setString(iParamPost,
										((GuardaCampo) comp).getVlrString());
							} else if (((GuardaCampo) comp).getTipo() == JTextFieldPad.TP_DECIMAL) {
								sqlLC
										.setBigDecimal(iParamPost,
												((GuardaCampo) comp)
														.getVlrBigDecimal());
							} else if (((GuardaCampo) comp).getTipo() == JTextFieldPad.TP_NUMERIC) {
								sqlLC
										.setBigDecimal(iParamPost,
												((GuardaCampo) comp)
														.getVlrBigDecimal());
							} else if (((GuardaCampo) comp).getTipo() == JTextFieldPad.TP_DOUBLE) {
								sqlLC.setDouble(iParamPost,
										(((GuardaCampo) comp).getVlrDouble())
												.doubleValue());
							} else if (((GuardaCampo) comp).getTipo() == JTextFieldPad.TP_DATE) {
								sqlLC.setDate(iParamPost, Funcoes
										.dateToSQLDate(((GuardaCampo) comp)
												.getVlrDate()));
							} else if (((GuardaCampo) comp).getTipo() == JTextFieldPad.TP_TIME) {
								sqlLC.setTime(iParamPost, Funcoes
										.dateToSQLTime(((GuardaCampo) comp)
												.getVlrTime()));
							} else if (((GuardaCampo) comp).getTipo() == JTextFieldPad.TP_BYTES) {
								if (((PainelImagem) ((GuardaCampo) comp)
										.getComponente()).foiAlterado()) {
									sqlLC.setBinaryStream(iParamPost,
											((GuardaCampo) comp).getVlrBytes()
													.getInputStream(),
											((GuardaCampo) comp).getVlrBytes()
													.getTamanho());
								} else {
									sqlLC.setBytes(iParamPost,
											((GuardaCampo) comp).getVlrBytes()
													.getBytes());
								}
							}

						}

						iParamPost++;
						if (((GuardaCampo) comp).ehFK()) {
							String sSigla = ((GuardaCampo) comp).getCampo()
									.getTabelaExterna() != null ? ((GuardaCampo) comp)
									.getCampo().getTabelaExterna().getSigla()
									: ((GuardaCampo) comp).getCampo()
											.getListaCampos().getSigla();
							if (sSigla != null) {
								if (sSigla.length() > 0) {
									ListaCampos lcExt = ((GuardaCampo) comp)
											.getCampo().getTabelaExterna();
									sqlLC.setInt(iParamPost++, lcExt
											.getCodEmp());
									sqlLC.setInt(iParamPost++, lcExt
											.getCodFilial());
								}
							}
						}
					}
				}
				montaPostCircular(lcM);
			} catch (Exception err) {
				err.printStackTrace();
			}
		}

	}

	public boolean post() {
		boolean bRetorno = true;
		boolean bParam = false;
		iParamPost = 1;
		Component comp = null;
		fireBeforePost();
		if (bCancelPost) {
			bCancelPost = false;
			bRetorno = false;
		}
		boolean bParamMaster = true;
		if (bRetorno) {
			try {
				if (lcState == LCS_EDIT) {
					sqlLC = con.prepareStatement(sSQLUpdate);
				} else if (lcState == LCS_INSERT) {
					sqlLC = con.prepareStatement(sSQLInsert);
				}
				if ((lcState == LCS_EDIT) || (lcState == LCS_INSERT)) {
					for (int i = 0; i < getComponentCount(); i++) {
						comp = getComponent(i);
						if ((((GuardaCampo) comp).getRequerido()) && (((GuardaCampo) comp).getCampo() != null)) {
							if (((GuardaCampo) comp).getCampo().getText().trim().length() < 1) {
								if (((GuardaCampo) comp).ehFK()) {
									Funcoes.mensagemInforma(cOwner,"O campo \"" + ((GuardaCampo) comp).getLabel()+ " " + ((GuardaCampo) comp).getDescFK().getLabel()+ "\" é requerido!");
									((GuardaCampo) comp).getComponente().requestFocus();
									bRetorno = false;
									return false;
								}
								Funcoes.mensagemInforma(cOwner, "O campo \"" + ((GuardaCampo) comp).getLabel()+ "\" é requerido!");
								((GuardaCampo) comp).getComponente().requestFocus();
								bRetorno = false;
								return false;
							}
						}
						if (((GuardaCampo) comp).getCampo() != null) {
							if (((GuardaCampo) comp).getCampo().getMascara() == JTextFieldPad.MC_CNPJ) {
								if (!Funcoes.ValidaCNPJ(((GuardaCampo) comp).getVlrString())) {
									Funcoes.mensagemErro(cOwner,"CNPJ inválido!");
									((GuardaCampo) comp).getComponente().requestFocus();
									bRetorno = false;
									return false;
								}
							} 
							else if (((GuardaCampo) comp).getCampo().getMascara() == JTextFieldPad.MC_CPF) {
								if (!Funcoes.ValidaCPF(((GuardaCampo) comp).getCampo().getVlrString())) {
									Funcoes.mensagemErro(cOwner,"CPF inválido!");
									((GuardaCampo) comp).getCampo().requestFocus();
									bRetorno = false;
									return false;
								}
							}
						}
						if (lcState == LCS_INSERT) {
							if ((bUsaME) && (iParamPost == 1)) {
								sqlLC.setInt(iParamPost, iCodEmp);
								iParamPost++;
								if (!bTiraFI) {
									sqlLC.setInt(iParamPost, iCodFilial);
									iParamPost++;
								}
							}
							bParam = true;
							if ((bDetalhe) && (lcMaster != null) && (bParamMaster)) {
								montaPostCircular(this);
							}
							bParamMaster = false;
						} 
						else {
							if ((((GuardaCampo) comp).ehPK()) || (((GuardaCampo) comp).getSoLeitura()))
								bParam = false;
							else
								bParam = true;
						}
						if (bParam) {
							comp = getComponent(i);
							String snomex = ((GuardaCampo) comp).getNomeCampo();

							if (!((GuardaCampo) comp).getSoLeitura()) {
								if (((GuardaCampo) comp).ehNulo()) {
									if (((GuardaCampo) comp).getTipo() == JTextFieldPad.TP_INTEGER) {
										sqlLC.setNull(iParamPost,Types.INTEGER);
									} 
									else if (((GuardaCampo) comp).getTipo() == JTextFieldPad.TP_STRING) {
										sqlLC.setNull(iParamPost, Types.CHAR);
									} 
									else if (((GuardaCampo) comp).getTipo() == JTextFieldPad.TP_DECIMAL) {
										sqlLC.setNull(iParamPost,Types.DECIMAL);
									} 
									else if (((GuardaCampo) comp).getTipo() == JTextFieldPad.TP_NUMERIC) {
										sqlLC.setNull(iParamPost,Types.NUMERIC);
									} 
									else if (((GuardaCampo) comp).getTipo() == JTextFieldPad.TP_DOUBLE) {
										sqlLC.setNull(iParamPost, Types.DOUBLE);
									} 
									else if (((GuardaCampo) comp).getTipo() == JTextFieldPad.TP_DATE) {
										sqlLC.setNull(iParamPost, Types.DATE);
									} 
									else if (((GuardaCampo) comp).getTipo() == JTextFieldPad.TP_TIME) {
										sqlLC.setNull(iParamPost, Types.TIME);
									} 
									else if (((GuardaCampo) comp).getTipo() == JTextFieldPad.TP_BYTES) {
										if (((PainelImagem) ((GuardaCampo) comp).getComponente()).foiAlterado()) {
											sqlLC.setNull(iParamPost,Types.BINARY);
										} 
										else {
											sqlLC.setNull(iParamPost,Types.BLOB);
										}
									}
								} 
								else {
									if (((GuardaCampo) comp).getTipo() == JTextFieldPad.TP_INTEGER) {
										if (((GuardaCampo) comp).ehNulo())
											sqlLC.setNull(iParamPost,Types.INTEGER);
										else
											sqlLC.setInt(iParamPost,((GuardaCampo) comp).getVlrInteger().intValue());
									} 
									else if (((GuardaCampo) comp).getTipo() == JTextFieldPad.TP_STRING) {
										sqlLC.setString(iParamPost,((GuardaCampo) comp).getVlrString());
									} 
									else if (((GuardaCampo) comp).getTipo() == JTextFieldPad.TP_DECIMAL) {
										sqlLC.setBigDecimal(iParamPost,((GuardaCampo) comp).getVlrBigDecimal());
									} 
									else if (((GuardaCampo) comp).getTipo() == JTextFieldPad.TP_NUMERIC) {
										sqlLC.setBigDecimal(iParamPost,((GuardaCampo) comp).getVlrBigDecimal());
									} 
									else if (((GuardaCampo) comp).getTipo() == JTextFieldPad.TP_DOUBLE) {
										sqlLC.setDouble(iParamPost,(((GuardaCampo) comp).getVlrDouble()).doubleValue());
									} 
									else if (((GuardaCampo) comp).getTipo() == JTextFieldPad.TP_DATE) {
										sqlLC.setDate(iParamPost,Funcoes.dateToSQLDate(((GuardaCampo) comp).getVlrDate()));
									} 
									else if (((GuardaCampo) comp).getTipo() == JTextFieldPad.TP_TIME) {
										sqlLC.setTime(iParamPost,Funcoes.dateToSQLTime(((GuardaCampo) comp).getVlrTime()));
									} 
									else if (((GuardaCampo) comp).getTipo() == JTextFieldPad.TP_BYTES) {
										if (((PainelImagem) ((GuardaCampo) comp).getComponente()).foiAlterado()) {
											sqlLC.setBinaryStream(iParamPost,((GuardaCampo) comp).getVlrBytes().getInputStream(),((GuardaCampo) comp).getVlrBytes().getTamanho());
										} 
										else {
											sqlLC.setBytes(iParamPost,((GuardaCampo) comp).getVlrBytes().getBytes());
										}
									}
								}
								iParamPost++;
								if (((GuardaCampo) comp).ehFK()) {
									ListaCampos lcExt = ((GuardaCampo) comp).getCampo().getTabelaExterna();
									if (lcExt != null) {
										if (lcExt.getUsaME() && lcExt.getUsaFI()) {
											if (!((GuardaCampo) comp).getSoLeitura()) {
												if (((GuardaCampo) comp).ehNulo()) {
													sqlLC.setNull(iParamPost,Types.INTEGER);
													iParamPost++;
													sqlLC.setNull(iParamPost,Types.INTEGER);
												} 
												else {
													sqlLC.setInt(iParamPost,iCodEmp);
													iParamPost++;
													sqlLC.setInt(iParamPost,lcExt.getCodFilial());
												}
											}
											iParamPost++;
										}
									}
								}
							}
						}
					}
				}
				if (lcState == LCS_EDIT) {
					for (int i = 0; i < getComponentCount(); i++) {
						comp = getComponent(i);
						if ((((GuardaCampo) comp).getRequerido()) && (((GuardaCampo) comp).getCampo() != null)) {
							if (((GuardaCampo) comp).getCampo().getText().trim().length() < 1) {
								if (((GuardaCampo) comp).ehFK()) {
									Funcoes.mensagemInforma(cOwner,"O campo \""+ ((GuardaCampo) comp).getLabel()+ " "+ ((GuardaCampo) comp).getDescFK().getLabel()+ "\" é requerido!");
									((GuardaCampo) comp).getComponente().requestFocus();
									bRetorno = false;
									break;
								}
								Funcoes.mensagemInforma(cOwner, "O campo \""+ ((GuardaCampo) comp).getLabel()+ "\" é requerido ! ! !");
								((GuardaCampo) comp).getComponente().requestFocus();
								bRetorno = false;
								break;
							}
						}
						if ((bDetalhe) && (lcMaster != null) && (bParamMaster)) {
							for (int iMaster = 0; iMaster < lcMaster.getComponentCount(); iMaster++) {
								comp = lcMaster.getComponent(iMaster);
								if (((GuardaCampo) comp).ehPK()) {
									if (((GuardaCampo) comp).ehNulo()) {
										if (((GuardaCampo) comp).getTipo() == JTextFieldPad.TP_INTEGER) {
											sqlLC.setNull(iParamPost,Types.INTEGER);
										} 
										else if (((GuardaCampo) comp).getTipo() == JTextFieldPad.TP_STRING) {
											sqlLC.setNull(iParamPost,Types.CHAR);
										} 
										else if (((GuardaCampo) comp).getTipo() == JTextFieldPad.TP_DECIMAL) {
											sqlLC.setNull(iParamPost,Types.DECIMAL);
										} 
										else if (((GuardaCampo) comp).getTipo() == JTextFieldPad.TP_NUMERIC) {
											sqlLC.setNull(iParamPost,Types.NUMERIC);
										} 
										else if (((GuardaCampo) comp).getTipo() == JTextFieldPad.TP_DOUBLE) {
											sqlLC.setNull(iParamPost,Types.DOUBLE);
										} 
										else if (((GuardaCampo) comp).getTipo() == JTextFieldPad.TP_DATE) {
											sqlLC.setNull(iParamPost,Types.DATE);
										} 
										else if (((GuardaCampo) comp).getTipo() == JTextFieldPad.TP_TIME) {
											sqlLC.setNull(iParamPost,Types.TIME);
										} 
										else if (((GuardaCampo) comp).getTipo() == JTextFieldPad.TP_BYTES) {
											if (((PainelImagem) ((GuardaCampo) comp).getComponente()).foiAlterado()) {
												sqlLC.setNull(iParamPost,Types.BINARY);
											} 
											else {
												sqlLC.setNull(iParamPost,Types.BLOB);
											}
										}
									} 
									else {
										if (((GuardaCampo) comp).getTipo() == JTextFieldPad.TP_INTEGER) {
											sqlLC.setInt(iParamPost,((GuardaCampo) comp).getVlrInteger().intValue());
										} 
										else if (((GuardaCampo) comp).getTipo() == JTextFieldPad.TP_STRING) {
											sqlLC.setString(iParamPost,((GuardaCampo) comp).getVlrString());
										} 
										else if (((GuardaCampo) comp).getTipo() == JTextFieldPad.TP_DECIMAL) {
											sqlLC.setBigDecimal(iParamPost,((GuardaCampo) comp).getVlrBigDecimal());
										} 
										else if (((GuardaCampo) comp).getTipo() == JTextFieldPad.TP_NUMERIC) {
											sqlLC.setBigDecimal(iParamPost,((GuardaCampo) comp).getVlrBigDecimal());
										} 
										else if (((GuardaCampo) comp).getTipo() == JTextFieldPad.TP_DOUBLE) {
											sqlLC.setDouble(iParamPost,(((GuardaCampo) comp).getVlrDouble()).doubleValue());
										} 
										else if (((GuardaCampo) comp).getTipo() == JTextFieldPad.TP_DATE) {
											sqlLC.setDate(iParamPost,Funcoes.dateToSQLDate(((GuardaCampo) comp).getVlrDate()));
										} 
										else if (((GuardaCampo) comp).getTipo() == JTextFieldPad.TP_TIME) {
											sqlLC.setTime(iParamPost,Funcoes.dateToSQLTime(((GuardaCampo) comp).getVlrTime()));
										} 
										else if (((GuardaCampo) comp).getTipo() == JTextFieldPad.TP_BYTES) {
											if (((PainelImagem) ((GuardaCampo) comp).getComponente()).foiAlterado()) {
												sqlLC.setBinaryStream(iParamPost,((GuardaCampo) comp).getVlrBytes().getInputStream(),((GuardaCampo) comp).getVlrBytes().getTamanho());
											} 
											else {
												sqlLC.setBytes(iParamPost,((GuardaCampo) comp).getVlrBytes().getBytes());
											}
										}
									}
									iParamPost++;
								}
							}
						}
						bParamMaster = false;
						comp = getComponent(i);
						if (((GuardaCampo) comp).ehPK()) {
							if (((GuardaCampo) comp).getTipo() == JTextFieldPad.TP_INTEGER) {
								sqlLC.setInt(iParamPost, ((GuardaCampo) comp).getVlrInteger().intValue());
							} 
							else if (((GuardaCampo) comp).getTipo() == JTextFieldPad.TP_STRING) {
								sqlLC.setString(iParamPost,((GuardaCampo) comp).getVlrString());
							} 
							else if (((GuardaCampo) comp).getTipo() == JTextFieldPad.TP_DATE) {
								sqlLC.setDate(iParamPost, Funcoes.dateToSQLDate(((GuardaCampo) comp).getVlrDate()));
							} 
							else if (((GuardaCampo) comp).getTipo() == JTextFieldPad.TP_TIME) {
								sqlLC.setTime(iParamPost, Funcoes.dateToSQLTime(((GuardaCampo) comp).getVlrTime()));
							}
							iParamPost++;
							if (((GuardaCampo) comp).ehFK()) {
								ListaCampos lcExt = ((GuardaCampo) comp).getCampo().getTabelaExterna();
								if (lcExt != null) {
									if (lcExt.getUsaME() && lcExt.getUsaFI()) {
										if (!((GuardaCampo) comp).getSoLeitura()) {
											if (((GuardaCampo) comp).ehNulo()) {
												sqlLC.setNull(iParamPost,Types.INTEGER);
												iParamPost++;
												sqlLC.setNull(iParamPost,Types.INTEGER);
											} 
											else {
												sqlLC.setInt(iParamPost,iCodEmp);
												iParamPost++;
												sqlLC.setInt(iParamPost, lcExt.getCodFilial());
											}
										}
										iParamPost++;
									}
								}
							}
						}
					}
					if (bUsaME) {
						sqlLC.setInt(iParamPost, iCodEmp);
						iParamPost++;
					}
					if (bUsaME && !bTiraFI) {
						sqlLC.setInt(iParamPost, iCodFilial);
					}
				}
				sqlLC.executeUpdate();
				if (!con.getAutoCommit() && bPodeCommit)
					con.commit();
				if (bDetalhe) {
					if (lcState == LCS_EDIT) {
						carregaGridEdit(bRetorno);
					} else {
						carregaGridInsert(bRetorno);
					}
				}
				setState(LCS_SELECT);
				if (bQueryInsert)
					carregaDados();
				bRetorno = true;
			} 
			catch (SQLException err) {
				Funcoes.mensagemErro(cOwner, "Erro ao salvar dados da tabela: "+ sTabela + "\n" + err.getMessage());
				err.printStackTrace();
				return false;
			}
		}
		fireAfterPost(bRetorno);
		return bRetorno;
	}

	public boolean edit() {
		boolean bRetorno = true;
		fireBeforeEdit();
		if (bCancelEdit) {
			bCancelEdit = false;
			bRetorno = false;
		} else if (lcState == LCS_NONE)
			insert(bAutoInc);
		else if (lcState == LCS_SELECT)
			setState(LCS_EDIT);
		fireAfterEdit(bRetorno);
		return bRetorno;
	}

	public boolean insert(boolean bNovaPK) {
		boolean bInsert = true;
		fireBeforeInsert();
		if (bCancelInsert || !bPodeIns) {
			bCancelInsert = false;
			bInsert = false;
		}
		if (bInsert) {
			if (bMaster) {
				for (int i = 0; i < vLcDetalhe.size(); i++) {
					((ListaCampos) vLcDetalhe.elementAt(i)).getTab().limpa();
					((ListaCampos) vLcDetalhe.elementAt(i)).limpaCampos(true);
					String sNameDet = ((ListaCampos) vLcDetalhe.elementAt(i)).nvLC
							.getName();
					String sNameMaster = this.nvLC.getName();
					if (!((sNameDet == null) || (sNameMaster == null))) {
						if (!sNameDet.equals(sNameMaster))
							((ListaCampos) vLcDetalhe.elementAt(i))
									.setState(LCS_NONE);
					}
				}
			}
			boolean bPost = true;
			if ((lcState == LCS_EDIT) | (lcState == LCS_INSERT)) {
				bPost = post();
			}
			if (bPost) {
				JTextFieldPad CampoPK = getCampo(sPK);
				limpaCampos(bNovaPK);
				setState(LCS_INSERT);
				if ((CampoPK != null) && (bNovaPK)) {
					CampoPK.setText(getNovoCodigo());
				}
			}
		}
		fireAfterInsert(bInsert);
		return bInsert;
	}

	public void setConfirmaDelecao(boolean bConfirma) {
		bConfirmaDelecao = bConfirma;
	}

	public void montaDeleteCircular(ListaCampos lc) {
		Component comp = null;
		ListaCampos lcM = lc.getMaster();
		if (lcM != null) {
			try {
				for (int iMaster = 0; iMaster < lcM.getComponentCount(); iMaster++) {
					comp = lcM.getComponent(iMaster);
					if (((GuardaCampo) comp).ehPK()) {
						if (((GuardaCampo) comp).ehNulo()) {
							if (((GuardaCampo) comp).getTipo() == JTextFieldPad.TP_INTEGER) {
								sqlLC.setNull(iParamDelete,Types.INTEGER);
							} 
							else if (((GuardaCampo) comp).getTipo() == JTextFieldPad.TP_STRING) {
								sqlLC.setNull(iParamDelete,Types.CHAR);
							} 
							else if (((GuardaCampo) comp).getTipo() == JTextFieldPad.TP_DATE) {
								sqlLC.setNull(iParamDelete,Types.DATE);
							} 
							else if (((GuardaCampo) comp).getTipo() == JTextFieldPad.TP_TIME) {
								sqlLC.setNull(iParamDelete,Types.TIME);
							}
						} 
						else {
							if (((GuardaCampo) comp).getTipo() == JTextFieldPad.TP_INTEGER) {
								sqlLC.setInt(iParamDelete,((GuardaCampo) comp).getVlrInteger().intValue());
							} 
							else if (((GuardaCampo) comp).getTipo() == JTextFieldPad.TP_STRING) {
								sqlLC.setString(iParamDelete,((GuardaCampo) comp).getVlrString());
							} 
							else if (((GuardaCampo) comp).getTipo() == JTextFieldPad.TP_DATE) {
								sqlLC.setDate(iParamDelete,Funcoes.dateToSQLDate(((GuardaCampo) comp).getVlrDate()));
							} 
							else if (((GuardaCampo) comp).getTipo() == JTextFieldPad.TP_TIME) {
								sqlLC.setTime(iParamDelete,Funcoes.dateToSQLTime(((GuardaCampo) comp).getVlrTime()));
							}
						}
						iParamDelete++;
					}
				}
				montaDeleteCircular(lcM);
			} catch (Exception err) {
				err.printStackTrace();
			}
		}

	}

	public boolean delete() {
		iParamDelete = 1;
		boolean bRetorno = true;
		boolean bDeletar = true;
		Component comp = null;
		fireBeforeDelete();
		if (bCancelDelete || !bPodeExc) {
			bCancelDelete = false;
			bRetorno = false;
		}
		if (bRetorno) {
			if ((lcState == LCS_EDIT) | (lcState == LCS_SELECT)) {
				if (bConfirmaDelecao) {
					if (Funcoes.mensagemConfirma(cOwner, "Confirma exclusão?") != 0) {
						bDeletar = false;
					}
				}
				if (bDeletar) {
					boolean bParamMaster = true;
					try {
						sqlLC = con.prepareStatement(sSQLDelete);
						if (bUsaME) {
							sqlLC.setInt(iParamDelete, iCodEmp);
							iParamDelete++;
						}
						if (bUsaME && !bTiraFI) {
							sqlLC.setInt(iParamDelete, iCodFilial);
							iParamDelete++;
						}
						for (int i = 0; i < getComponentCount(); i++) {
							comp = getComponent(i);
							if ((bDetalhe) && (lcMaster != null) && (bParamMaster)) {								
								montaDeleteCircular(this);
							}
							bParamMaster = false;
							comp = getComponent(i);
							if (((GuardaCampo) comp).ehPK()) {
								System.out.println("CAMPO: " + ((GuardaCampo) comp).getNomeCampo() + " IPARAM: " + iParamDelete + " VALOR: " + ((GuardaCampo) comp).getCampo().getVlrInteger());
								if (((GuardaCampo) comp).getCampo().getTipo() == JTextFieldPad.TP_INTEGER) {
									sqlLC.setInt(iParamDelete, ((GuardaCampo) comp).getCampo().getVlrInteger().intValue());
								} 
								else if (((GuardaCampo) comp).getCampo().getTipo() == JTextFieldPad.TP_STRING) {
									sqlLC.setString(iParamDelete,((GuardaCampo) comp).getCampo().getVlrString());
								} 
								else if (((GuardaCampo) comp).getTipo() == JTextFieldPad.TP_DATE) {
									sqlLC.setDate(iParamDelete, Funcoes.dateToSQLDate(((GuardaCampo) comp).getVlrDate()));
								} 
								else if (((GuardaCampo) comp).getTipo() == JTextFieldPad.TP_TIME) {
									sqlLC.setTime(iParamDelete, Funcoes.dateToSQLTime(((GuardaCampo) comp).getVlrTime()));
								}
								iParamDelete++;
								if (((GuardaCampo) comp).ehFK()) {
									ListaCampos lcExt = ((GuardaCampo) comp).getCampo().getTabelaExterna();
									if (lcExt != null) {
										if (lcExt.getUsaME() && lcExt.getUsaFI()) {
											if (!((GuardaCampo) comp).getSoLeitura())
												System.out.println("FILIAL: " + ((GuardaCampo) comp) .getNomeCampo()+ " IPARAM: " + iParamDelete + " VALOR: " + lcExt.getCodFilial());
											if (((GuardaCampo) comp).ehNulo()) {
												sqlLC.setNull(iParamDelete,Types.INTEGER);
												iParamDelete++;
												sqlLC.setNull(iParamDelete,Types.INTEGER);
											} 
											else {
												sqlLC.setInt(iParamDelete, iCodEmp);
												iParamDelete++;
												sqlLC.setInt(iParamDelete, lcExt.getCodFilial());
											}
											iParamDelete++;
										}
									}
								}
							}
						}
						sqlLC.execute();
						if (!con.getAutoCommit() && bPodeCommit)
							con.commit();
						if (bDetalhe)
							carregaGridDelete(bRetorno);
						bRetorno = true;
						limpaCampos(true);
						setState(LCS_NONE);
					} 
					catch (SQLException err) {
						if (err.getErrorCode() == FB_FK_INVALIDA) {
							Funcoes.mensagemErro(cOwner,"O registro possui vínculos, não pode ser deletado!");
							bRetorno = false;
						} 
						else if (err.getErrorCode() == FB_PK_DUPLA) {
							Funcoes.mensagemErro(cOwner,"A chave de registro duplicada. Escolha outro código!");
							bRetorno = false;
						} 
						else {
							Funcoes.mensagemErro(cOwner,"Erro ao deletar registro na tabela " + sTabela + "\n" + err.getMessage());
							err.printStackTrace();
							bRetorno = false;
						}
					}
				}
			}
		}
		fireAfterDelete(bRetorno);
		return bRetorno;
	}

	public void cancLerCampo(int ind, boolean bVal) {
		bCamposCanc[ind] = bVal;
	}

	public boolean cancel(boolean carrega) {
		int iContaVal = 0;
		boolean bRetorno = true;
		Component comp = null;
		fireBeforeCancel();
		if (bCancelCancel) {
			bCancelCancel = false;
			bRetorno = false;
		} else if (vCache.size() < 1) {
			limpaCampos(true);
			setState(LCS_NONE);
			bRetorno = false;
		}
		if (bRetorno) {
			for (int i = 0; i < getComponentCount(); i++) {
				comp = getComponent(i);
				if (((GuardaCampo) comp).ehPK()) {
					if (vCache.elementAt(iContaVal) instanceof Integer)
						((GuardaCampo) comp).setVlrInteger((Integer) vCache
								.elementAt(iContaVal));
					else if (vCache.elementAt(iContaVal) instanceof String)
						((GuardaCampo) comp).setVlrString((String) vCache
								.elementAt(iContaVal));
					iContaVal++;
				}
			}
			if ((carrega) & (lcState != LCS_INSERT)) {
				setState(LCS_SELECT);
				carregaDados();
			} else if ((carrega) & (lcState == LCS_INSERT)) {
				limpaCampos(true);
				setState(LCS_NONE);
			}
		}
		fireAfterCancel(bRetorno);
		return bRetorno;
	}

	private void carregaGridInsert(boolean b) {
		Vector vVals = new Vector();
		int iContaDesc = 0;
		if ((b) && (tab != null)) {
			for (int i = 0; i < (getComponentCount() + iNumDescs); i++) {
				/*
				 * JOptionPane.showMessageDialog(null,"I
				 * :"+i+"\niContaDesc"+iContaDesc+ "\nNome: "+((GuardaCampo)
				 * getComponent(i-iContaDesc)).getNomeCampo()+ "\nValor:
				 * "+((GuardaCampo)
				 * getComponent(i-iContaDesc)).getCampo().getText());
				 */
				if ((vDescFK.size() > 0) && (vDescFK.elementAt(i) != null)) {
					vVals.addElement(((JTextFieldPad) vDescFK.elementAt(i))
							.getVlrString());
					iContaDesc++;
				} else if (((GuardaCampo) getComponent(i - iContaDesc))
						.getTipo() == JTextFieldPad.TP_BYTES) {
					vVals.addElement("# BINÁRIO #");
				} else if (((GuardaCampo) getComponent(i - iContaDesc))
						.getTipo() == JTextFieldPad.TP_INTEGER) {
					vVals
							.addElement(((GuardaCampo) getComponent(i
									- iContaDesc)).getVlrInteger());
				} else {
					vVals
							.addElement(((GuardaCampo) getComponent(i
									- iContaDesc)).getVlrString());
				}
			}
			tab.adicLinha(vVals);
		}
	}

	private void carregaGridEdit(boolean b) {
		int iContaDesc = 0;
		if ((b) && (tab != null)) {
			int iLin = getNumLinha();
			if (iLin >= 0) {
				for (int i = 0; i < (getComponentCount() + iNumDescs); i++) {
					/*
					 * JOptionPane.showMessageDialog(null,"I
					 * :"+i+"\niContaDesc"+iContaDesc+ "\nNome: "+((GuardaCampo)
					 * getComponent(i-iContaDesc)).getNomeCampo());
					 */
					if ((vDescFK.size() > 0) & (vDescFK.elementAt(i) != null)) {
						tab.setValor(((JTextFieldPad) vDescFK.elementAt(i))
								.getText(), iLin, i);
						iContaDesc++;
					} else if (((GuardaCampo) getComponent(i - iContaDesc))
							.getTipo() == JTextFieldPad.TP_BYTES) {
						tab.setValor("# BINÁRIO #", iLin, i);
					} else if (((GuardaCampo) getComponent(i - iContaDesc))
							.getTipo() == JTextFieldPad.TP_INTEGER) {
						tab.setValor(
								((GuardaCampo) getComponent(i - iContaDesc))
										.getVlrInteger(), iLin, i);
					} else {
						tab.setValor(
								((GuardaCampo) getComponent(i - iContaDesc))
										.getVlr(), iLin, i);
					}
				}
			}
		}
	}

	private void carregaGridDelete(boolean b) {
		if ((b) && (tab != null)) {
			int iLin = getNumLinha();
			tab.tiraLinha(iLin);
		}
	}

	public void cancelPost() {
		bCancelPost = true;
	}

	public void cancelInsert() {
		bCancelInsert = true;
	}

	public void cancelEdit() {
		bCancelEdit = true;
	}

	public void cancelDelete() {
		bCancelDelete = true;
	}

	public void cancelCarrega() {
		bCancelCarrega = true;
	}

	public void cancelCancel() {
		bCancelCancel = true;
	}

	public void addPostListener(PostListener pLis) {
		posLis = pLis;
	}

	public void addInsertListener(InsertListener iLis) {
		insLis = iLis;
	}

	public void addEditListener(EditListener eLis) {
		editLis = eLis;
	}

	public void addDeleteListener(DeleteListener dLis) {
		delLis = dLis;
	}

	public void addCancelListener(CancelListener cLis) {
		canLis = cLis;
	}

	public void addCarregaListener(CarregaListener cLis) {
		carLis = cLis;
	}

	private void fireBeforeInsert() {
		insLis.beforeInsert(new InsertEvent(this));
	}

	private void fireBeforeEdit() {
		editLis.beforeEdit(new EditEvent(this));
	}

	private void fireBeforeDelete() {
		delLis.beforeDelete(new DeleteEvent(this));
	}

	private void fireBeforeCancel() {
		canLis.beforeCancel(new CancelEvent(this));
	}

	private void fireBeforeCarrega() {
		carLis.beforeCarrega(new CarregaEvent(this));
	}

	private void fireBeforePost() {
		posLis.beforePost(new PostEvent(this));
	}

	private void fireAfterInsert(boolean b) {
		InsertEvent ievt = new InsertEvent(this);
		ievt.ok = b;
		insLis.afterInsert(ievt);
	}

	private void fireAfterEdit(boolean b) {
		EditEvent eevt = new EditEvent(this);
		eevt.ok = b;
		editLis.afterEdit(eevt);
	}

	private void fireAfterDelete(boolean b) {
		DeleteEvent devt = new DeleteEvent(this);
		devt.ok = b;
		delLis.afterDelete(devt);
	}

	private void fireAfterCancel(boolean b) {
		CancelEvent cevt = new CancelEvent(this);
		cevt.ok = b;
		canLis.afterCancel(cevt);
	}

	private void fireAfterPost(boolean b) {
		// Chama o Listener
		PostEvent pevt = new PostEvent(this);
		pevt.ok = b;
		posLis.afterPost(pevt);
	}

	private void fireAfterCarrega(boolean b) {
		if (b) {
			if (bMaster) {
				//        JOptionPane.showMessageDialog(null,"Master OK");
				for (int i = 0; i < vLcDetalhe.size(); i++) {
					//					String steste = ((ListaCampos)
					// vLcDetalhe.elementAt(i)).getNomeTabela();
					((ListaCampos) vLcDetalhe.elementAt(i)).carregaItens();
				}
			}
		}
		// Chama o listener:
		CarregaEvent cevt = new CarregaEvent(this);
		cevt.ok = b;
		carLis.afterCarrega(cevt);
	}

	public void mouseClicked(MouseEvent mevt) {
		if ((mevt.getSource() == tab) & (mevt.getClickCount() == 2)
				& (tab.getLinhaSel() >= 0)) {
			carregaItem(tab.getLinhaSel());
		}
	}

	public void beforePost(PostEvent pevt) {
	}

	public void beforeInsert(InsertEvent ievt) {
	}

	public void beforeEdit(EditEvent eevt) {
	}

	public void beforeDelete(DeleteEvent devt) {
	}

	public void beforeCancel(CancelEvent cevt) {
	}

	public void beforeCarrega(CarregaEvent cevt) {
	}

	public void afterInsert(InsertEvent ievt) {
	}

	public void afterEdit(EditEvent eevt) {
	}

	public void afterDelete(DeleteEvent devt) {
	}

	public void afterCancel(CancelEvent cevt) {
	}

	public void afterCarrega(CarregaEvent cevt) {
	}

	public void afterPost(PostEvent pevt) {
	}

	public void mouseEntered(MouseEvent mevt) {
	}

	public void mouseExited(MouseEvent mevt) {
	}

	public void mousePressed(MouseEvent mevt) {
	}

	public void mouseReleased(MouseEvent mevt) {
	}

	public void edit(EditEvent eevt) {
	}

	public Connection getConexao() {
		return con;
	}

	public String getNomeTabela() {
		return sTabela;
	}
}