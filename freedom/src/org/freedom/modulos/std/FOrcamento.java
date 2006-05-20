/**
 * @version 02/11/2003 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.std <BR>
 * Classe:
 * @(#)FOrcamento.java <BR>
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
 * Tela de orçamento, tela responsável pela inserção e edição de orçamentos por
 * cliente <BR>
 * diferente da tela de orçamento do atendimento que é por conveniado. <BR>
 *  
 */

package org.freedom.modulos.std;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import org.freedom.acao.CarregaEvent;
import org.freedom.acao.CarregaListener;
import org.freedom.acao.DeleteEvent;
import org.freedom.acao.DeleteListener;
import org.freedom.acao.InsertEvent;
import org.freedom.acao.InsertListener;
import org.freedom.acao.PostEvent;
import org.freedom.acao.PostListener;
import org.freedom.bmps.Icone;
import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.ImprimeOS;
import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JPanelPad;
import org.freedom.componentes.JTextAreaPad;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Funcoes;
import org.freedom.layout.LeiauteGR;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FPrinterJob;

public class FOrcamento extends FVD implements PostListener, CarregaListener,
		FocusListener, ActionListener, InsertListener, DeleteListener {
	private static final long serialVersionUID = 1L;

	private JPanelPad pinCab = new JPanelPad();
	private JPanelPad pinDet = new JPanelPad();
	private JPanelPad pinTot = new JPanelPad(200, 200);
	private JPanelPad pnTot = new JPanelPad(JPanelPad.TP_JPANEL,new GridLayout(1, 1));
	private JPanelPad pnCenter = new JPanelPad(JPanelPad.TP_JPANEL,new BorderLayout());
	private JButton btObs = new JButton(Icone.novo("btObs.gif"));
	private JButton btOrc = new JButton(Icone.novo("btImprimeOrc.gif"));
	private JButton btFechaOrc = new JButton(Icone.novo("btOk.gif"));
	private JButton btExp = new JButton(Icone.novo("btExportar.gif"));
	private JTextFieldPad txtCodOrc = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);
	private JTextFieldPad txtDtOrc = new JTextFieldPad(JTextFieldPad.TP_DATE,10, 0);
	private JTextFieldPad txtDtVencOrc = new JTextFieldPad(JTextFieldPad.TP_DATE, 10, 0);
	private JTextFieldPad txtCodPlanoPag = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);
	private JTextFieldPad txtCodCli = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);
	private JTextFieldPad txtEstCli = new JTextFieldPad(JTextFieldPad.TP_STRING, 2, 0);
	private JTextFieldPad txtCodItOrc = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);
	private JTextFieldPad txtQtdItOrc = new JTextFieldPad(JTextFieldPad.TP_NUMERIC, 15, 2);
	private JTextFieldPad txtCodProd = new JTextFieldPad(JTextFieldPad.TP_STRING, 13, 0);
	private JTextFieldPad txtRefProd = new JTextFieldPad(JTextFieldPad.TP_STRING, 13, 0);
	private JTextFieldPad txtCodBarras = new JTextFieldPad(JTextFieldPad.TP_STRING, 13, 0);
	private JTextFieldPad txtPrecoItOrc = new JTextFieldPad(JTextFieldPad.TP_NUMERIC, 15, 2);
	private JTextFieldPad txtPercDescItOrc = new JTextFieldPad(JTextFieldPad.TP_NUMERIC, 6, 2);
	private JTextFieldPad txtVlrDescItOrc = new JTextFieldPad(JTextFieldPad.TP_NUMERIC, 15, 2);
	private JTextFieldPad txtVlrLiqItOrc = new JTextFieldPad(JTextFieldPad.TP_NUMERIC, 15, 2);
	private JTextFieldPad txtVlrEdDescOrc = new JTextFieldPad(JTextFieldPad.TP_NUMERIC, 15, 2);
	private JTextFieldPad txtVlrEdAdicOrc = new JTextFieldPad(JTextFieldPad.TP_NUMERIC, 15, 2);
	private JTextFieldPad txtPercDescOrc = new JTextFieldPad(JTextFieldPad.TP_NUMERIC, 6, 2);
	private JTextFieldPad txtVlrDescOrc = new JTextFieldPad(JTextFieldPad.TP_NUMERIC, 15, 2);
	private JTextFieldPad txtPercAdicOrc = new JTextFieldPad(JTextFieldPad.TP_NUMERIC, 6, 2);
	private JTextFieldPad txtVlrAdicOrc = new JTextFieldPad(JTextFieldPad.TP_NUMERIC, 15, 2);
	private JTextFieldPad txtVlrLiqOrc = new JTextFieldPad(JTextFieldPad.TP_NUMERIC, 15, 2);
	private JTextFieldPad txtVlrProdItOrc = new JTextFieldPad(JTextFieldPad.TP_NUMERIC, 15, 2);
	private JTextFieldPad txtStrDescItOrc = new JTextFieldPad(JTextFieldPad.TP_STRING, 500, 0);
	private JTextFieldPad txtVlrProdOrc = new JTextFieldPad(JTextFieldPad.TP_NUMERIC, 15, 2);
	private JTextFieldPad txtStatusOrc = new JTextFieldPad(JTextFieldPad.TP_STRING, 2, 0);
	private JTextFieldPad txtCodTpCli = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);
	private JTextFieldPad txtCodVend = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);
	private JTextFieldPad txtCodClComiss = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);
	private JTextFieldPad txtPrazoEntOrc = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);
	private JTextFieldPad txtCodAlmoxItOrc = new JTextFieldPad(JTextFieldPad.TP_INTEGER,5, 0);
	private JTextFieldPad txtCodEmpLG = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);
	private JTextFieldPad txtCodFilialLG = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);
	private JTextFieldPad txtCodLog = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);
	private JTextFieldFK txtNomeVend = new JTextFieldFK(JTextFieldPad.TP_STRING, 50, 0);
	private JTextFieldFK txtRazCli = new JTextFieldFK(JTextFieldPad.TP_STRING,50, 0);	
	private JTextFieldFK txtNomeCli = new JTextFieldFK(JTextFieldPad.TP_STRING,50, 0);
	private JTextFieldFK txtDescProd = new JTextFieldFK(JTextFieldPad.TP_STRING, 50, 0);
	private JTextFieldFK txtDescPlanoPag = new JTextFieldFK(JTextFieldPad.TP_STRING, 40, 0);
	private JTextFieldFK txtDescTipoCli = new JTextFieldFK(JTextFieldPad.TP_STRING, 40, 0);	
	private JTextFieldFK txtDescAlmoxItOrc = new JTextFieldFK(JTextFieldPad.TP_STRING, 40, 0);
	private JTextFieldFK txtDescClComiss = new JTextFieldFK(JTextFieldPad.TP_STRING, 40, 0);	
	private JTextFieldFK txtSldLiqProd = new JTextFieldFK(JTextFieldPad.TP_NUMERIC, 15, casasDec);
	private JTextAreaPad txaObsItOrc = new JTextAreaPad(500);
	private ListaCampos lcCli = new ListaCampos(this, "CL");
	private ListaCampos lcProd = new ListaCampos(this, "PD");
	private ListaCampos lcProd2 = new ListaCampos(this, "PD");
	private ListaCampos lcOrc2 = new ListaCampos(this);
	private ListaCampos lcPlanoPag = new ListaCampos(this, "PG");
	private ListaCampos lcVend = new ListaCampos(this, "VD");
	private ListaCampos lcTipoCli = new ListaCampos(this, "TC");	
	private ListaCampos lcAlmox = new ListaCampos(this, "AX");	
	private ListaCampos lcClComiss = new ListaCampos(this, "CM");
	private Vector vParamOrc = new Vector();
	private String sOrdNota = "";	
	private String sModoNota = "";	
	private String oldStatusOrc = null;
	private BigDecimal bdVlrDescItAnt;
	private FPrinterJob dl = null;
	private Object[] oPrefs = null;	
	private boolean bDescComp = false;
	private boolean bCtrl = false;
	private int iCodCliAnt = 0;

	public FOrcamento() {
		setTitulo("Orçamento");
		setAtribos(15, 10, 769, 460);

		txtDescProd.setToolTipText("Clique aqui duas vezes para alterar a descrição.");
		txtDescProd.addMouseListener( new MouseAdapter() {
			public void mouseClicked(MouseEvent mevt) {
				if (mevt.getClickCount() == 2)
					mostraTelaDecricao(txaObsItOrc, txtCodProd.getVlrInteger().intValue(), txtDescProd.getVlrString());
			}
		});
		setImprimir(true);
	}

	private void montaOrcamento() {
		oPrefs = prefs(); //Carrega as preferências

		pnMaster.remove(2); //Remove o JPanelPad prédefinido da class FDados
		pnGImp.removeAll(); //Remove os botões de impressão para adicionar logo
		// embaixo
		pnGImp.setLayout(new GridLayout(1, 5)); //redimensiona o painel de
												// impressão
		pnGImp.setPreferredSize(new Dimension(210, 26));
		pnGImp.add(btPrevimp);
		pnGImp.add(btImp);
		pnGImp.add(btFechaOrc);
		pnGImp.add(btObs);//Agora o painel está maior
		pnGImp.add(btOrc);//Botão provisório para emissão de orçamento padrão

		pnTot.setPreferredSize(new Dimension(120, 200)); //JPanelPad de Totais
		pnTot.add(pinTot);
		pnCenter.add(pnTot, BorderLayout.EAST);
		pnCenter.add(spTab, BorderLayout.CENTER);

		JPanelPad pnLab = new JPanelPad(JPanelPad.TP_JPANEL, new GridLayout(1,1));
		pnLab.add(new JLabelPad(" Totais:")); //Label do painel de totais

		pnMaster.add(pnCenter, BorderLayout.CENTER);

		lcTipoCli.add(new GuardaCampo(txtCodTpCli, "CodTipoCli", "Cód.cli.",ListaCampos.DB_PK, false));
		lcTipoCli.add(new GuardaCampo(txtDescTipoCli, "DescTipoCli","Razão social do cliente", ListaCampos.DB_SI, false));
		txtCodTpCli.setTabelaExterna(lcTipoCli);
		txtDescTipoCli.setListaCampos(lcTipoCli);
		lcTipoCli.montaSql(false, "TIPOCli", "VD");
		lcTipoCli.setQueryCommit(false);
		lcTipoCli.setReadOnly(true);

		lcPlanoPag.add(new GuardaCampo(txtCodPlanoPag, "CodPlanoPag","Cód.p.pag.", ListaCampos.DB_PK, false));
		lcPlanoPag.add(new GuardaCampo(txtDescPlanoPag, "DescPlanoPag","Descrição do plano de pagamento", ListaCampos.DB_SI, false));
		txtCodPlanoPag.setTabelaExterna(lcPlanoPag);
		txtDescPlanoPag.setListaCampos(lcPlanoPag);
		lcPlanoPag.montaSql(false, "PLANOPAG", "FN");
		lcPlanoPag.setQueryCommit(false);
		lcPlanoPag.setReadOnly(true);

		lcVend.add(new GuardaCampo(txtCodVend, "CodVend", "Cód.comiss.",ListaCampos.DB_PK, false));
		lcVend.add(new GuardaCampo(txtNomeVend, "NomeVend","Nome do comissionado", ListaCampos.DB_SI, false));
		txtCodVend.setTabelaExterna(lcVend);
		txtNomeVend.setListaCampos(lcVend);
		lcVend.montaSql(false, "VENDEDOR", "VD");
		lcVend.setQueryCommit(false);
		lcVend.setReadOnly(true);
		
		lcClComiss.add(new GuardaCampo(txtCodClComiss, "CodClComis", "Cód.cl.comiss.",ListaCampos.DB_PK, false));
		lcClComiss.add(new GuardaCampo(txtDescClComiss, "DescClComis","Descrição da class. da comissão", ListaCampos.DB_SI, false));
		lcClComiss.montaSql(false, "CLCOMIS", "VD");
		lcClComiss.setQueryCommit(false);
		lcClComiss.setReadOnly(true);
		txtCodClComiss.setTabelaExterna(lcClComiss);

		//FK Cliente
		lcCli.add(new GuardaCampo(txtCodCli, "CodCli", "Cód.cli.",ListaCampos.DB_PK, false));
		lcCli.add(new GuardaCampo(txtRazCli, "RazCli","Razão social do cliente", ListaCampos.DB_SI, false));
		lcCli.add(new GuardaCampo(txtNomeCli, "NomeCli","Nome do cliente", ListaCampos.DB_SI, false));
		lcCli.add(new GuardaCampo(txtCodPlanoPag, "CodPlanoPag", "Cód.p.pag.",ListaCampos.DB_SI, false));
		lcCli.add(new GuardaCampo(txtCodVend, "CodVend", "Cód.comiss.",ListaCampos.DB_SI, false));
		lcCli.add(new GuardaCampo(txtEstCli, "UfCli", "UF", ListaCampos.DB_SI,false));
		lcCli.montaSql(false, "CLIENTE", "VD");
		lcCli.setQueryCommit(false);
		lcCli.setReadOnly(true);
		txtCodCli.setTabelaExterna(lcCli);
		txtNomeCli.setSize(250,20);
		
		//FK de Almoxarifado

		lcAlmox.add(new GuardaCampo(txtCodAlmoxItOrc, "codalmox", "Cod.Almox.",ListaCampos.DB_PK, false));
		lcAlmox.add(new GuardaCampo(txtDescAlmoxItOrc, "DescAlmox","Descrição do almoxarifado", ListaCampos.DB_SI, false));
		lcAlmox.montaSql(false, "ALMOX", "EQ");
		lcAlmox.setQueryCommit(false);
		lcAlmox.setReadOnly(true);
		txtCodAlmoxItOrc.setTabelaExterna(lcAlmox);
				
		//FK Produto
		lcProd.add(new GuardaCampo(txtCodProd, "CodProd", "Cód.prod.",ListaCampos.DB_PK, txtDescProd, false));
		lcProd.add(new GuardaCampo(txtDescProd, "DescProd","Descrição do produto", ListaCampos.DB_SI, false));
		lcProd.add(new GuardaCampo(txtRefProd, "RefProd","Referência do produto", ListaCampos.DB_SI, false));
		lcProd.add(new GuardaCampo(txtCodBarras, "CodBarProd","Código de barras", ListaCampos.DB_SI, false));
		lcProd.add(new GuardaCampo(txtSldLiqProd, "SldLiqProd", "Saldo",ListaCampos.DB_SI, false));
		lcProd.add(new GuardaCampo(txtCodAlmoxItOrc, "CodAlmox", "Cód.almox.",ListaCampos.DB_SI, txtDescAlmoxItOrc, false));
		lcProd.setWhereAdic("ATIVOPROD='S'");
		lcProd.montaSql(false, "PRODUTO", "EQ");
		lcProd.setQueryCommit(false);
		lcProd.setReadOnly(true);
		txtCodProd.setTabelaExterna(lcProd);

		//FK do produto (*Somente em caso de referências este listaCampos
		//Trabalha como gatilho para o listaCampos de produtos, assim
		//carregando o código do produto que será armazenado no Banco)
		lcProd2.add(new GuardaCampo(txtRefProd, "RefProd", "Ref.prod.",ListaCampos.DB_PK, txtDescProd, false));
		lcProd2.add(new GuardaCampo(txtDescProd, "DescProd","Descrição do produto", ListaCampos.DB_SI, false));
		lcProd2.add(new GuardaCampo(txtCodProd, "CodProd", "Cód.prod.",ListaCampos.DB_SI, false));
		lcProd2.add(new GuardaCampo(txtSldLiqProd, "SldLiqProd", "Saldo",ListaCampos.DB_SI, false));
		lcProd2.add(new GuardaCampo(txtCodAlmoxItOrc, "CodAlmox", "Cód.almox.",ListaCampos.DB_SI, txtDescAlmoxItOrc, false));
		txtRefProd.setNomeCampo("RefProd");
		txtRefProd.setListaCampos(lcDet);
		lcProd2.setWhereAdic("ATIVOPROD='S'");
		lcProd2.montaSql(false, "PRODUTO", "EQ");
		lcProd2.setQueryCommit(false);
		lcProd2.setReadOnly(true);
		txtRefProd.setTabelaExterna(lcProd2);

		//ListaCampos de Totais (É acionada pelo listaCampos de Orcamento)

		lcOrc2.add(new GuardaCampo(txtCodOrc, "CodOrc", "Cód.Orç.",ListaCampos.DB_PK, false));
		lcOrc2.add(new GuardaCampo(txtPercDescOrc, "PercDescOrc", "% desc.",ListaCampos.DB_SI, false));
		lcOrc2.add(new GuardaCampo(txtVlrDescOrc, "VlrDescOrc", "Vlr.desc.",ListaCampos.DB_SI, false));
		lcOrc2.add(new GuardaCampo(txtPercAdicOrc, "PercAdicOrc", "% adic.",ListaCampos.DB_SI, false));
		lcOrc2.add(new GuardaCampo(txtVlrAdicOrc, "VlrAdicOrc", "Vlr.adic.",ListaCampos.DB_SI, false));
		lcOrc2.add(new GuardaCampo(txtVlrLiqOrc, "VlrLiqOrc", "Vlr.total",ListaCampos.DB_SI, false));
		lcOrc2.add(new GuardaCampo(txtVlrProdOrc, "VlrProdOrc", "Vlr.parcial",ListaCampos.DB_SI, false));
		lcOrc2.montaSql(false, "ORCAMENTO", "VD");
		lcOrc2.setQueryCommit(false);
		lcOrc2.setReadOnly(true);

		//Coloca os comentário nos botões

		btFechaOrc.setToolTipText("Completar o Orçamento (F4)");
		btObs.setToolTipText("Observações (Ctrl + O)");
		btOrc.setToolTipText("Imprime orçamento padrão");

		//Desativa as os TextFields para que os usuários não fussem

		txtVlrDescOrc.setAtivo(false);
		txtVlrAdicOrc.setAtivo(false);
		txtVlrLiqOrc.setAtivo(false);

		//Adiciona os componentes na tela e no ListaCompos da orcamento
		pinCab = new JPanelPad(740, 180);
		setListaCampos(lcCampos);
		setAltCab(130);
		setPainel(pinCab, pnCliCab);
		adicCampo(txtCodOrc, 7, 20, 90, 20, "CodOrc", "Nº orçamento",ListaCampos.DB_PK, true);
		adicCampo(txtDtOrc, 100, 20, 87, 20, "DtOrc", "Data",ListaCampos.DB_SI, true);
		adicCampo(txtCodCli, 190, 20, 77, 20, "CodCli", "Cód.cli.",ListaCampos.DB_FK, txtRazCli, true);
		adicDescFK(txtRazCli, 270, 20, 277, 20, "RazCli","Razão social do cliente");
		adicCampo(txtDtVencOrc, 550, 20, 87, 20, "DtVencOrc", "Dt.valid.",ListaCampos.DB_SI, true);
		adicCampo(txtPrazoEntOrc, 640, 20, 100, 20, "PrazoEntOrc","Dias p/ entrega", ListaCampos.DB_SI, false);
		adicCampo(txtCodVend, 7, 60, 80, 20, "CodVend", "Cód.comiss.",ListaCampos.DB_FK, txtNomeVend, true);
		adicDescFK(txtNomeVend, 90, 60, 177, 20, "NomeVend","Nome do comissionado");
		adicDescFK(txtDescTipoCli, 270, 60, 147, 20, "DescTipoCli","Desc. do tipo de cliente");
		adicCampo(txtCodPlanoPag, 420, 60, 77, 20, "CodPlanoPag", "Cód.p.pg.",ListaCampos.DB_FK, txtDescPlanoPag, true);
		adicDescFK(txtDescPlanoPag, 500, 60, 240, 20, "DescPlanoPag","Descrição do plano de pagamento");
		adicCampoInvisivel(txtPercDescOrc, "PercDescOrc", "% desc.",ListaCampos.DB_SI, false);
		adicCampoInvisivel(txtVlrDescOrc, "VlrDescOrc", "Vlr.desc.",ListaCampos.DB_SI, false);
		adicCampoInvisivel(txtPercAdicOrc, "PercAdicOrc", "% adic.",ListaCampos.DB_SI, false);
		adicCampoInvisivel(txtVlrAdicOrc, "VlrAdicOrc", "Vlr.adic.",ListaCampos.DB_SI, false);
		
		/* porque usar estes campos?
		 * adicCampoInvisivel(txtVlrEdDescOrc, "VlrDescOrc", "Vlr.desc.",ListaCampos.DB_SI, false);
		 * adicCampoInvisivel(txtVlrEdAdicOrc, "VlrAdicOrc", "Vlr.adic.",ListaCampos.DB_SI, false);*/
		
		adicCampoInvisivel(txtStatusOrc, "StatusOrc", "Status",ListaCampos.DB_SI, false);
		adicCampoInvisivel(txtCodClComiss, "CodClComis", "Cód.cl.comiss.",ListaCampos.DB_FK, txtDescClComiss, false);
		setListaCampos(true, "ORCAMENTO", "VD");

		//pnRodape.add(btExp);
		btExp.setPreferredSize(new Dimension(30, 30));
		pnNavCab.add(btExp, BorderLayout.EAST);

		//adic(btExp, 633, 50, 30, 30);

		txtVlrLiqItOrc.setAtivo(false);

		//Adiciona os Listeners
		btFechaOrc.addActionListener(this);
		btObs.addActionListener(this);
		btOrc.addActionListener(this);
		btExp.addActionListener(this);
		btImp.addActionListener(this);
		btPrevimp.addActionListener(this);
		
		txtRefProd.addKeyListener(this);	
		
		txtPercDescItOrc.addFocusListener(this);
		txtVlrDescItOrc.addFocusListener(this);		
		txtQtdItOrc.addFocusListener(this);
		txtPrecoItOrc.addFocusListener(this);
		
		lcCampos.addCarregaListener(this);
		lcProd2.addCarregaListener(this);
		lcCli.addCarregaListener(this);
		lcProd.addCarregaListener(this);
		lcProd2.addCarregaListener(this);
		lcDet.addCarregaListener(this);
		lcPlanoPag.addCarregaListener(this);
		lcCli.addCarregaListener(this);
		
		lcCampos.addInsertListener(this);
		lcDet.addInsertListener(this);
		
		lcDet.addPostListener(this);
		lcCampos.addPostListener(this);
		
		lcDet.addDeleteListener(this);


	}

	//Função criada para montar a tela conforme a preferência do usuário:
	//com ou sem Referência sendo PK;
	private void montaDetalhe() {
		setAltDet(100);
		pinDet = new JPanelPad(740, 100);
		setPainel(pinDet, pnDet);
		setListaCampos(lcDet);
		setNavegador(navRod);
		adicCampo(txtCodItOrc, 7, 20, 30, 20, "CodItOrc", "Item",ListaCampos.DB_PK, true);
		if (((Boolean) oPrefs[0]).booleanValue()) {
			adicCampoInvisivel(txtCodProd, "CodProd", "Cód.prod.",ListaCampos.DB_FK, txtDescProd, false);
			adicCampoInvisivel(txtRefProd, "RefProd", "Ref.prod.",ListaCampos.DB_FK, false);
			adic(new JLabelPad("Referência"), 40, 0, 67, 20);
			adic(txtRefProd, 40, 20, 67, 20);
			txtRefProd.setFK(true);
			txtRefProd.setBuscaAdic(new DLBuscaProd(con, "REFPROD",lcProd2.getWhereAdic()));
		} else {
			adicCampo(txtCodProd, 40, 20, 67, 20, "CodProd", "Cód.prod.",ListaCampos.DB_FK, txtDescProd, true);
			txtCodProd.setBuscaAdic(new DLBuscaProd(con, "CODPROD",lcProd.getWhereAdic()));
		}
		
		txtQtdItOrc.setBuscaAdic(new DLBuscaEstoq(lcDet, lcAlmox,lcProd,con,"qtditvenda"));
		txtCodAlmoxItOrc.setAtivo(false);
		
		adicDescFK(txtDescProd, 110, 20, 230, 20, "DescProd","Descrição do produto");
		adicCampo(txtQtdItOrc, 343, 20, 45, 20, "QtdItOrc", "Qtd.", ListaCampos.DB_SI, true);				
		adicCampo(txtPrecoItOrc, 391, 20, 90, 20, "PrecoItOrc", "Preço", ListaCampos.DB_SI, true);
		adicCampo(txtPercDescItOrc, 484, 20, 60, 20, "PercDescItOrc","% desc.", ListaCampos.DB_SI, false);
		adicCampo(txtVlrDescItOrc, 547, 20, 90, 20, "VlrDescItOrc","Valor desc.", ListaCampos.DB_SI, false);
		adicCampoInvisivel(txtVlrProdItOrc, "VlrProdItOrc", "Valor bruto", ListaCampos.DB_SI, false);
		adicCampoInvisivel(txtStrDescItOrc, "StrDescItOrc", "Descontos", ListaCampos.DB_SI, false);
		adicCampo(txtVlrLiqItOrc, 640, 20, 100, 20, "VlrLiqItOrc", "Valor item", ListaCampos.DB_SI, false);
		adicCampo(txtCodAlmoxItOrc, 7, 60, 65, 20, "CodAlmox", "Cód.ax.", ListaCampos.DB_FK, txtDescAlmoxItOrc, false);
		adicDescFK(txtDescAlmoxItOrc, 75, 60, 185, 20, "DescAlmox","Descrição do almoxarifado");
		adicDescFK(txtSldLiqProd, 263, 60, 77, 20, "SldLiqProd", "Saldo");
		adicDBLiv(txaObsItOrc, "ObsItOrc", "Observação", false);
		adicCampoInvisivel(txtCodEmpLG, "CodEmpLG", "Emp.log.", ListaCampos.DB_SI, false);
		adicCampoInvisivel(txtCodFilialLG, "CodFilialLG", "Filial log.", ListaCampos.DB_SI, false);
		adicCampoInvisivel(txtCodLog, "CodLog", "Cód.log.", ListaCampos.DB_SI,false);
		pinTot.adic(new JLabelPad("Total desc."), 7, 0, 90, 20);
		pinTot.adic(txtVlrDescOrc, 7, 20, 100, 20);
		pinTot.adic(new JLabelPad("Total adic."), 7, 40, 90, 20);
		pinTot.adic(txtVlrAdicOrc, 7, 60, 100, 20);
		pinTot.adic(new JLabelPad("Total geral"), 7, 80, 90, 20);
		pinTot.adic(txtVlrLiqOrc, 7, 100, 100, 20);

		setListaCampos(true, "ITORCAMENTO", "VD");
		montaTab();

		tab.setAutoRol(true);

		tab.setTamColuna(30, 0);
		tab.setTamColuna(70, 1);
		tab.setTamColuna(230, 2);
		tab.setTamColuna(60, 3);
		tab.setTamColuna(70, 4);
		tab.setTamColuna(60, 5);
		tab.setTamColuna(70, 6);
		tab.setTamColuna(90, 7);
	}
	
	public void setLog(String[] args) {
		if(args != null) {
			txtCodEmpLG.setVlrString(args[0]);
			txtCodFilialLG.setVlrString(args[1]);
			txtCodLog.setVlrString(args[2]);
		}
	}

	public void setParansPreco(BigDecimal bdPreco) {
		txtPrecoItOrc.setVlrBigDecimal(bdPreco);
	}

	public Vector getParansDesconto(){
		Vector param = new Vector();
		param.addElement(txtStrDescItOrc);
		param.addElement(txtPrecoItOrc);
		param.addElement(txtVlrDescItOrc);
		param.addElement(txtQtdItOrc);
		return param;
	}
		
	public String[] getParansPass() {
		return new String[] {"orçamento",
				txtCodOrc.getVlrString().trim(),
				txtCodItOrc.getVlrString().trim(),
				txtCodProd.getVlrString().trim(),
				txtVlrProdItOrc.getVlrString().trim()};
	}

	public int[] getParansPreco() {
		int[] iRetorno = { txtCodProd.getVlrInteger().intValue(),
				txtCodCli.getVlrInteger().intValue(), 
				Aplicativo.iCodEmp,
				ListaCampos.getMasterFilial("VDCLIENTE"),
				txtCodPlanoPag.getVlrInteger().intValue(), 
				Aplicativo.iCodEmp,
				ListaCampos.getMasterFilial("FNPLANOPAG"),
				((Integer) oPrefs[3]).intValue(), 
				Aplicativo.iCodEmp,
				ListaCampos.getMasterFilial("EQTIPOMOV"), 
				Aplicativo.iCodEmp,
				Aplicativo.iCodFilial,
				txtCodOrc.getVlrInteger().intValue(),
				Aplicativo.iCodEmp,
				ListaCampos.getMasterFilial("VDORCAMENTO") };
		return iRetorno;
	
	}

	private int getPlanoPag() {
		int iRet = 0;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sSQL = null;
		try {
			sSQL = "SELECT CodPlanoPag FROM SGPREFERE4 WHERE "
				+ "CODEMP=? AND CODFILIAL=?";
			ps = con.prepareStatement(sSQL);
			ps.setInt(1, Aplicativo.iCodEmp);
			ps.setInt(2, Aplicativo.iCodFilial);
			rs = ps.executeQuery();
			if (rs.next()) {
				iRet = rs.getInt("CodPlanoPag");
			}
			rs.close();
			ps.close();
		} catch (SQLException err) {
			Funcoes.mensagemErro(this, "Erro ao buscar o plano de pagamento.\n"
					+"Provavelmente não foram gravadas corretamente as preferências!\n"
					+ err.getMessage(),true,con,err);
			err.printStackTrace();
		} finally {
			ps = null;
			rs = null;
			sSQL = null;
		}
		return iRet;
	}

	private int getCodCli() {
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sSQL = null;
		int iRet = 0;
		try {
			sSQL = "SELECT CODCLI FROM SGPREFERE4 WHERE "
				+ "CODEMP=? AND CODFILIAL=?";
			ps = con.prepareStatement(sSQL);
			ps.setInt(1, Aplicativo.iCodEmp);
			ps.setInt(2, Aplicativo.iCodFilial);
			rs = ps.executeQuery();
			if (rs.next()) {
				iRet = rs.getInt("CODCLI");
			}
			rs.close();
			ps.close();
		} catch (SQLException err) {
			Funcoes.mensagemErro(this, "Erro ao buscar o código do cliente.\n" +
					"Provavelmente não foram gravadas corretamente as preferências!\n"
						+ err.getMessage(),true,con,err);
			err.printStackTrace();
		} finally {
			ps = null;
			rs = null;
			sSQL = null;
		}
		return iRet;
	}

	private int getCodTipoCli() {
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sSQL = null;
		int iRet = 0;
		try {
			sSQL = "SELECT CODTIPOCLI FROM VDCLIENTE "
				 + "WHERE CODEMP=? AND CODFILIAL=? AND CODCLI=?";
			ps = con.prepareStatement(sSQL);
			ps.setInt(1, Aplicativo.iCodEmp);
			ps.setInt(2, ListaCampos.getMasterFilial("VDCLIENTE"));
			ps.setInt(3, txtCodCli.getVlrInteger().intValue());
			rs = ps.executeQuery();
			if (rs.next()) {
				iRet = rs.getInt("CODTIPOCLI");
			}
			rs.close();
			ps.close();
		} catch (SQLException err) {
			Funcoes.mensagemErro(this, "Erro ao buscar o código do tipo de cliente.\n"
						+ err.getMessage(),true,con,err);
			err.printStackTrace();
		} finally {
			ps = null;
			rs = null;
			sSQL = null;
		}
		return iRet;
	}

	private int getPrazo() {
		PreparedStatement ps = null;
		ResultSet rs = null;
		int iRet = 0;
		String sSQL = null;
		try {
			sSQL = "SELECT Prazo FROM SGPREFERE4 WHERE "
				+ "CODEMP=? AND CODFILIAL=?";
			ps = con.prepareStatement(sSQL);
			ps.setInt(1, Aplicativo.iCodEmp);
			ps.setInt(2, Aplicativo.iCodFilial);
			rs = ps.executeQuery();
			if (rs.next()) {
				iRet = rs.getInt("Prazo");
			}
			rs.close();
			ps.close();
		} catch (SQLException err) {
			Funcoes.mensagemErro(this, "Erro ao buscar o prazo.\n" +
			"Provavelmente não foram gravadas corretamente as preferências!\n"
					+ err.getMessage(),true,con,err);
			err.printStackTrace();
		} finally {
			ps = null;
			rs = null;
			sSQL = null;
		}
		return iRet;
	}

	private Date getVencimento() {
		PreparedStatement ps = null;
		ResultSet rs = null;
		GregorianCalendar clVenc = new GregorianCalendar();
		Date dtRet = null;
		String sSQL = null;
		int diasVenc = 0;
		try {
			sSQL = "SELECT DIASVENCORC FROM SGPREFERE4 WHERE "
				+ "CODEMP=? AND CODFILIAL=?";
			ps = con.prepareStatement(sSQL);
			ps.setInt(1, Aplicativo.iCodEmp);
			ps.setInt(2, Aplicativo.iCodFilial);
			rs = ps.executeQuery();
			if (rs.next()) {
				diasVenc = rs.getInt("DIASVENCORC");
				clVenc.add(Calendar.DATE, diasVenc);
				dtRet = clVenc.getTime();
			}
			rs.close();
			ps.close();
		} catch (SQLException err) {
			Funcoes.mensagemErro(this, "Erro ao buscar a data de vencimento.\n"
					+ err.getMessage(),true,con,err);
			err.printStackTrace();
		} finally {
			ps = null;
			rs = null;
			sSQL = null;
		}
		return dtRet;
	}

	private int getVendedor() {
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sSQL = null;
		int iRet = 0;
		try {
			sSQL = "SELECT CODVEND FROM VDCLIENTE "
				 + "WHERE CODEMP=? AND CODFILIAL=? AND CODCLI=?";
			ps = con.prepareStatement(sSQL);
			ps.setInt(1, Aplicativo.iCodEmp);
			ps.setInt(2, ListaCampos.getMasterFilial("VDCLIENTE"));
			ps.setInt(3, txtCodCli.getVlrInteger().intValue());
			rs = ps.executeQuery();
			if (rs.next()) {
				iRet = rs.getInt("CodVend");
				return iRet;
			}
			
			sSQL = "SELECT CODVEND FROM ATATENDENTE WHERE "
				+ "IDUSU=? AND CODEMPUS=? AND CODFILIALUS=?";
			ps = con.prepareStatement(sSQL);
			ps.setString(1, Aplicativo.strUsuario);
			ps.setInt(2, Aplicativo.iCodEmp);
			ps.setInt(3, Aplicativo.iCodFilialPad);
			rs = ps.executeQuery();
			if (rs.next()) {
				iRet = rs.getInt("CodVend");
			}
			
			rs.close();
			ps.close();
		} catch (SQLException err) {
			Funcoes.mensagemErro(this, "Erro ao buscar o comissionado.\n"
					+ "O usuário '" + Aplicativo.strUsuario
					+ "' é um comissionado?\n" + err.getMessage(),true,con,err);
			err.printStackTrace();
		} finally {
			ps = null;
			rs = null;
			sSQL = null;
		}
		return iRet;
	}

	private int getClComiss(int iCodVend) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sSQL = null;
		int iRet = 0;
		try {
			sSQL = "SELECT CODCLCOMIS FROM VDVENDEDOR "
				 + "WHERE CODEMP=? AND CODFILIAL=? AND CODVEND=?";
			ps = con.prepareStatement(sSQL);
			ps.setInt(1, Aplicativo.iCodEmp);
			ps.setInt(2, ListaCampos.getMasterFilial("VDCLIENTE"));
			ps.setInt(3, iCodVend);
			rs = ps.executeQuery();
			if (rs.next()) {
				iRet = rs.getInt("CODCLCOMIS");
				return iRet;
			}
		} catch (SQLException err) {
			Funcoes.mensagemErro(this, "Erro ao buscar a class. da comissão." + err.getMessage(),true,con,err);
			err.printStackTrace();
		} finally {
			ps = null;
			rs = null;
			sSQL = null;
		}
		return iRet;
	}

	private void calcDescIt() {
		if(txtPercDescItOrc.floatValue()>0) {
			txtVlrDescItOrc.setVlrBigDecimal(new BigDecimal(
					Funcoes.arredFloat(txtVlrProdItOrc.floatValue()
							* txtPercDescItOrc.floatValue() / 100,casasDecFin)));
			bdVlrDescItAnt = txtVlrDescItOrc.getVlrBigDecimal();
		} else if(txtVlrDescItOrc.floatValue() == 0) {
			txtPercDescItOrc.setVlrString("");
			bdVlrDescItAnt = txtVlrDescItOrc.getVlrBigDecimal();
		} 		
	}

	private void calcTot() {
		txtVlrLiqItOrc.setVlrBigDecimal(
				calcVlrTotalProd(txtVlrProdItOrc.getVlrBigDecimal(),txtVlrDescItOrc.getVlrBigDecimal()));
	}

	private void calcVlrProd() {		
		txtVlrProdItOrc.setVlrBigDecimal(
				calcVlrProd(txtPrecoItOrc.getVlrBigDecimal(),txtQtdItOrc.getVlrBigDecimal()));
	}

	private boolean testaLucro() {
		return super.testaLucro( new Object[] {
				txtCodProd.getVlrInteger(),
				txtCodAlmoxItOrc.getVlrInteger(),
				txtPrecoItOrc.getVlrBigDecimal(),
				});
	}
		
	private void mostraTelaDescont() {
		if ((lcDet.getStatus() == ListaCampos.LCS_INSERT) || (lcDet.getStatus() == ListaCampos.LCS_EDIT)) {
			txtPercDescItOrc.setVlrString("");
			txtVlrDescItOrc.setVlrString("");
			calcVlrProd();
			calcTot();
			mostraTelaDesconto();
			calcVlrProd();
			calcTot();
			txtVlrDescItOrc.requestFocus(true);
		}
	}
	
	private void fechaOrc() {
		Object[] oValores = null;
		DLCompOrc dl = new DLCompOrc(this, (txtVlrDescOrc.floatValue() > 0), 
				txtVlrProdOrc.getVlrBigDecimal(),
				txtPercDescOrc.getVlrBigDecimal(),
				txtVlrDescOrc.getVlrBigDecimal(),
				txtPercAdicOrc.getVlrBigDecimal(),
				txtVlrAdicOrc.getVlrBigDecimal(), 
				txtCodPlanoPag.getVlrInteger());
		try {
			dl.setConexao(con);
			dl.setVisible(true);
			if (dl.OK) {
				oValores = dl.getValores();
				dl.dispose();
			} else {
				dl.dispose();
			}
			if (oValores != null) {
				lcCampos.edit();
				
				txtPercDescOrc.setVlrBigDecimal((BigDecimal) oValores[0]);
				txtVlrDescOrc.setVlrBigDecimal((BigDecimal) oValores[1]);
				txtPercAdicOrc.setVlrBigDecimal((BigDecimal) oValores[2]);
				txtVlrAdicOrc.setVlrBigDecimal((BigDecimal) oValores[3]);
				
				if(oValores[3] != txtCodPlanoPag.getVlrInteger())
					txtCodPlanoPag.setVlrInteger((Integer)(oValores[4]));

				// pega o status antigo do orçamento;
				oldStatusOrc = txtStatusOrc.getVlrString().trim();
				// Ajusta o status para OC - orçamento completo.
				txtStatusOrc.setVlrString("OC");
				lcCampos.post();
				lcCampos.carregaDados();

				if (oValores[5].equals("S"))
					aprovar();
				if (oValores[6].equals("S"))
					imprimir(true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			oValores = null;
			dl = null;
		}
	}

	private void exportar() {
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sSQL = null;
		DLCopiaOrc dl = null;
					
		try {
			if (txtCodOrc.getVlrInteger().intValue() == 0 || lcCampos.getStatus() != ListaCampos.LCS_SELECT) {
				Funcoes.mensagemInforma(this,"Selecione um orçamento cadastrado antes!");
				return;
			}
			dl = new DLCopiaOrc(this);
			dl.setConexao(con);
			dl.setVisible(true);
			if (!dl.OK) {
				dl.dispose();
				return;
			}
			int[] iVals = dl.getValores();
			dl.dispose();
			
			sSQL = "SELECT IRET FROM VDCOPIAORCSP(?,?,?,?,?)";
			
			ps = con.prepareStatement(sSQL);
			ps.setInt(1, Aplicativo.iCodEmp);
			ps.setInt(2, lcCampos.getCodFilial());
			ps.setInt(3, txtCodOrc.getVlrInteger().intValue());
			ps.setInt(4, iVals[1]);
			ps.setInt(5, iVals[0]);
			rs = ps.executeQuery();
			if (rs.next()) {
				if (Funcoes.mensagemConfirma(this, "Orçamento '" + rs.getInt(1)
						+ "' criado com sucesso!\n"
						+ "Gostaria de edita-lo agora?") == JOptionPane.OK_OPTION) {
					txtCodOrc.setVlrInteger(new Integer(rs.getInt(1)));
					lcCampos.carregaDados();
				}
			}
			rs.close();
			ps.close();
			if (!con.getAutoCommit())
				con.commit();
		} catch (SQLException err) {
			Funcoes.mensagemErro(this, "Erro ao copiar o orçamento!\n"
					+ err.getMessage(),true,con,err);
			err.printStackTrace();
		} finally {
			ps = null;
			rs = null;
			sSQL = null;
		}
		dl.dispose();
	}
	

	public void aprovar() {
		PreparedStatement ps = null;
		String sSQL = null;
		String status = "OC";
		try {

			if (tab.getRowCount() <= 0) {
				Funcoes.mensagemInforma(this,"Não ha nenhum ítem para ser aprovado");
				return;
			}
			/* isso aqui é pra não deixar aprovar mais de uma vez...*/
			if (oldStatusOrc.equals("OL") || oldStatusOrc.equals("OV")) {
				if(oldStatusOrc.equals("OV"))
					Funcoes.mensagemInforma(this,"Orçamento já foi faturado.");
				lcCampos.edit();
				txtStatusOrc.setVlrString(oldStatusOrc);
				lcCampos.post();
				lcCampos.carregaDados();
				return;
			}
				
			sSQL = "UPDATE VDITORCAMENTO SET ACEITEITORC='S', APROVITORC='S', VENCAUTORIZORC=?, EMITITORC='N' "
				 + "WHERE CODEMP=? AND CODFILIAL=? AND CODITORC=? AND CODORC=?";
			
			try {
				ps = con.prepareStatement(sSQL);			
				for (int iLin=0;iLin<tab.getRowCount();iLin++) {
					ps.setDate(1,Funcoes.dateToSQLDate(txtDtVencOrc.getVlrDate()));
					ps.setInt(2,Aplicativo.iCodEmp);
					ps.setInt(3,ListaCampos.getMasterFilial("VDITORCAMENTO"));
					ps.setInt(4,Integer.parseInt(tab.getValor(iLin,0).toString()));
			    	ps.setInt(5,txtCodOrc.getVlrInteger().intValue()); 
			    
			    	ps.execute(); 
				}
				if (!con.getAutoCommit())
					con.commit();
				status = "OL";
			} catch (SQLException err) {
				Funcoes.mensagemErro(this,"Erro ao atualizar a tabela ITORCAMENTO!\n"+err.getMessage(),true,con,err);
			}
			
			try {
				sSQL = "UPDATE VDORCAMENTO SET STATUSORC=? WHERE "+
				 	   "CODEMP=? AND CODFILIAL=? AND CODORC=?";
					
				ps = con.prepareStatement(sSQL);
								
				ps.setString(1,status);
				ps.setInt(2,Aplicativo.iCodEmp);
				ps.setInt(3,ListaCampos.getMasterFilial("VDORCAMENTO"));
				ps.setInt(4,txtCodOrc.getVlrInteger().intValue()); 
				ps.execute();
				if (!con.getAutoCommit())
				  	con.commit();
			} catch (SQLException err) {
				Funcoes.mensagemErro(this,"Erro ao atualizar a tabela ORCAMENTO!\n"+err.getMessage(),true,con,err);
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			ps = null;
			sSQL = null;		
		}
	}	

	private synchronized void iniOrc() {
		txtCodCli.setVlrInteger(new Integer(getCodCli()));
		lcCli.carregaDados();
		txtCodTpCli.setVlrInteger(new Integer(getCodTipoCli()));
		lcTipoCli.carregaDados();
		txtCodPlanoPag.setVlrInteger(new Integer(getPlanoPag()));
		lcPlanoPag.carregaDados();
		txtCodVend.setVlrInteger(new Integer(getVendedor()));
		lcVend.carregaDados();
		lcProd.limpaCampos(true);
		lcProd2.limpaCampos(true);
		txtVlrAdicOrc.setVlrString("");
		txtVlrEdAdicOrc.setVlrString("");
		txtVlrEdDescOrc.setVlrString("");
		txtVlrLiqOrc.setVlrString("");
		txtVlrProdOrc.setVlrString("");
		txtDtOrc.setVlrDate(new Date());
		txtDtVencOrc.setVlrDate(getVencimento());
		txtPrazoEntOrc.setVlrInteger(new Integer(getPrazo()));
		tab.limpa();
		txtCodOrc.requestFocus();
	}

	private synchronized void iniItem() {
		lcDet.insert(true);
		txtCodItOrc.setVlrInteger(new Integer(1));
		if (((Boolean) oPrefs[0]).booleanValue())
			txtRefProd.requestFocus();
		else
			txtCodProd.requestFocus();
	}

	public void exec(int iCodOrc) {
		txtCodOrc.setVlrString(String.valueOf(iCodOrc));
		lcCampos.carregaDados();
	}

	public void show(){
		super.show();
		lcCampos.insert(true);
		iniOrc();
	}

	private void focusCodprod() {
		if (((Boolean)oPrefs[0]).booleanValue())
			txtRefProd.requestFocus();
		else
			txtCodProd.requestFocus();
	}

	private void imprimir(boolean bVisualizar) {
		String sOrdem = "";
		
		DLROrcamento dlo = new DLROrcamento(sOrdNota,sModoNota);
		dlo.setVisible(true);
		if (dlo.OK == false) {
			dlo.dispose();
			return;
		}
		if (dlo.getModo().equals("G")){			
			imprimiGrafico(bVisualizar);			
		}
		else if(dlo.getModo().equals("T")){
			sOrdem = dlo.getOrdem();
			imprimiTexto(bVisualizar, sOrdem);			
		}
	}
	
	public void imprimiGrafico(boolean bVisualizar){
		String sClassOrc = "";
		String sSql = "SELECT CLASSORC FROM SGPREFERE1 WHERE CODEMP=? AND CODFILIAL=?";
		LeiauteGR leiOrc = null;

		try {
			PreparedStatement ps = null;
			ResultSet rs = null;
			ps = con.prepareStatement(sSql);
			ps.setInt(1, Aplicativo.iCodEmp);
			ps.setInt(2, ListaCampos.getMasterFilial("SGPREFERE1"));
			rs = ps.executeQuery();

			if (rs.next()) {
				if (rs.getString("CLASSORC") != null) {
					sClassOrc = rs.getString("CLASSORC").trim();
				}
			}
			rs.close();
			ps.close();
		} catch (SQLException err) {
			Funcoes.mensagemErro(this,
					"Erro ao carregar a tabela SGPREFERE1!\n" + err.getMessage(),true,con,err);
		}
		if (sClassOrc.trim().equals("")) {
			ImprimeOrc imp = new ImprimeOrc(txtCodOrc.getVlrInteger().intValue());
			imp.setConexao(con);
			if (bVisualizar) {
				dl = new FPrinterJob(imp, this);
				dl.setVisible(true);
			} else
				imp.imprimir(true);
		} 
		else {
			try {
				leiOrc = (LeiauteGR) Class.forName("org.freedom.layout." + sClassOrc).newInstance();
				leiOrc.setConexao(con);
				vParamOrc.clear();
				vParamOrc.addElement(txtCodOrc.getText());
				vParamOrc.addElement(txtCodCli.getText());
				leiOrc.setParam(vParamOrc);
				if (bVisualizar){
					dl = new FPrinterJob(leiOrc,this);
				    dl.setVisible(true);
				}
				else
					leiOrc.imprimir(true);
			} catch (Exception err) {
				Funcoes.mensagemInforma(this,
						"Não foi possível carregar o leiaute de Orçamento!\n" + err.getMessage());
				err.printStackTrace();
			}
		}
	}
	
	public void imprimiTexto(boolean bVisualizar, String sOrdem){
		ImprimeOS imp = new ImprimeOS("", con);
		int iCodOrc = txtCodOrc.getVlrInteger().intValue();
		int linPag = imp.verifLinPag() - 1;
		Vector vDesc = null;
		Vector vObs = null;		
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sSQL = null;
		
		try {			
			sSQL = "SELECT O.CODORC, O.CODPLANOPAG, O.CODCLI, O.OBSORC, O.VLRLIQORC, O.PRAZOENTORC, C.RAZCLI,"
				+ " C.CONTCLI, C.CNPJCLI, C.CPFCLI, C.RGCLI, C.INSCCLI, C.SITECLI, C.EMAILCLI, C.ENDCLI, C.NUMCLI,"
				+ " C.BAIRCLI, C.CIDCLI, C.UFCLI, C.CEPCLI,C.DDDCLI, C.FONECLI, C.FAXCLI, I.CODITORC, I.CODPROD,"
				+ " I.QTDITORC, I.PRECOITORC, I.VLRPRODITORC, I.VLRDESCITORC, P.REFPROD, P.DESCPROD, P.CODUNID,"
				+ " PG.DESCPLANOPAG, I.OBSITORC, VEND.NOMEVEND, VEND.EMAILVEND,"
				+ " (SELECT FN.DESCFUNC FROM RHFUNCAO FN WHERE FN.CODEMP=VEND.CODEMPFU"
				+ " AND FN.CODFILIAL=VEND.CODFILIALFU AND FN.CODFUNC=VEND.CODFUNC)"
				+ " FROM VDORCAMENTO O, VDITORCAMENTO I, VDCLIENTE C, EQPRODUTO P, FNPLANOPAG PG, VDVENDEDOR VEND"
				+ " WHERE O.CODEMP=? AND O.CODFILIAL=? AND O.CODORC=?"
				+ " AND C.CODEMP=O.CODEMPCL AND C.CODFILIAL=O.CODFILIALCL AND C.CODCLI=O.CODCLI"
				+ " AND I.CODEMP=O.CODEMP AND I.CODFILIAL=O.CODFILIAL AND I.CODORC=O.CODORC AND I.TIPOORC=O.TIPOORC"
				+ " AND P.CODEMP=I.CODEMPPD AND P.CODFILIAL=I.CODFILIALPD AND P.CODPROD=I.CODPROD"
				+ " AND PG.CODEMP=O.CODEMPPG AND PG.CODFILIAL=O.CODFILIALPG AND PG.CODPLANOPAG=O.CODPLANOPAG"
				+ " AND VEND.CODEMP=O.CODEMPVD AND VEND.CODFILIAL=O.CODFILIALVD AND VEND.CODVEND=O.CODVEND"
				+ " ORDER BY P." + sOrdem + ",P.DESCPROD";
			
			imp.montaCab();
			imp.setTitulo("ORÇAMENTO");
			imp.limpaPags();
			ps = con.prepareStatement(sSQL);
			ps.setInt(1,Aplicativo.iCodEmp);
			ps.setInt(2,ListaCampos.getMasterFilial("VDORCAMENTO"));
			ps.setInt(3,iCodOrc);
			rs = ps.executeQuery();
			while (rs.next()) {
				
				vDesc = new Vector();
				if (bDescComp)
					vDesc = Funcoes.quebraLinha(Funcoes.stringToVector(rs.getString("ObsItOrc")==null?rs.getString("DescProd").trim():rs.getString("ObsItOrc").trim()),50);						
				else 
					vDesc = Funcoes.quebraLinha(Funcoes.stringToVector(rs.getString("DescProd").trim()),50);
				
				for (int i=0; i<vDesc.size(); i++) {
					if (imp.pRow() == 0) {
						imp.impCab(136, false);
						imp.say(imp.pRow() + 0, 0, "" + imp.comprimido());
						imp.say(imp.pRow() + 0, 1, "CLIENTE");
						imp.say(imp.pRow() + 0, 70, "ORÇAMENTO: "+(rs.getString("CodOrc")!=null ? rs.getString("CodOrc").trim() : ""));
						imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
						imp.say(imp.pRow() + 0, 1, (rs.getString("RazCli")!=null ? rs.getString("RazCli").trim() : "") + " - " + (rs.getString("CodCli")!=null ? rs.getString("CodCli").trim() : ""));
						imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
						imp.say(imp.pRow() + 0, 1, rs.getString("CpfCli") != null ? "CPF    : " + 
												Funcoes.setMascara(rs.getString("CpfCli"),"###.###.###-##") : "CNPJ   : " + 
												Funcoes.setMascara(rs.getString("CnpjCli"),"##.###.###/####-##"));
						imp.say(imp.pRow() + 0, 70, "CONTATO: "+ (rs.getString("ContCli")!=null ? rs.getString("ContCli").trim() : ""));
						imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
						imp.say(imp.pRow() + 0, 1, rs.getString("RgCli") != null ? "R.G.   : " + rs.getString("RgCli") : "I.E.   : " + rs.getString("InscCli"));//IE cliente
						imp.say(imp.pRow() + 0, 70,(rs.getString("EndCli")!=null ? rs.getString("EndCli").trim() : "") + (rs.getString("NumCli")!=null ? "  Nº: " + rs.getString("NumCli").trim() : ""));//rua e número do cliente
						imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
						imp.say(imp.pRow() + 0, 1, "SITE   : " + (rs.getString("SiteCli")!= null ? rs.getString("SiteCli").trim() : ""));
						imp.say(imp.pRow() + 0, 70,(rs.getString("BairCli")!=null ? rs.getString("BairCli").trim() : "") +
												(rs.getString("CidCli")!=null ? " - " + rs.getString("CidCli").trim() : "") +
												(rs.getString("UFCli")!=null ? " - " + rs.getString("UFCli").trim() : "") + 
												(rs.getString("CepCli")!=null ? " - " + rs.getString("CepCli").trim() : ""));//complemento do endereço do cliente
						imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
						imp.say(imp.pRow() + 0, 1, "E-MAIl : " + (rs.getString("EmailCli") != null ? rs.getString("EmailCli").trim() : ""));
						imp.say(imp.pRow() + 0, 70, "TEL: "+ (rs.getString("DDDCli")!=null?"("+rs.getString("DDDCli")+")":"")+ 
												(rs.getString("FoneCli")!=null?Funcoes.setMascara(rs.getString("FoneCli").trim(), "####-####"):"")+ " - FAX:" +
												(rs.getString("FaxCli") != null ? Funcoes.setMascara(rs.getString("FaxCli"),"####-####") : ""));
						imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
						imp.say(imp.pRow() + 0,0,Funcoes.replicate("-", 135));
						imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
						imp.say(imp.pRow() + 0, 55, "DADO(S) DO(S) PRODUTO(S)");
						imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
						imp.say(imp.pRow() + 0,0,Funcoes.replicate("-", 135));
						imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
						imp.say(imp.pRow() + 0, 1, "IT. " +
								"|   CÓDIGO   " +
								"|                    DESCRIÇÃO                     " +
								"|UN" +
								"|   QUANT.   " +
								"|    V.UNIT.   " +
								"|   V.DESCONTO   " +
								"|   V.TOTAL");
					}
					imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
					if (i==0) {
						imp.say(imp.pRow() + 0, 1, rs.getString("CodItOrc").trim());
						if (((Boolean) oPrefs[0]).booleanValue())
							imp.say(imp.pRow() + 0, 7, rs.getString("RefProd").trim());
						else
							imp.say(imp.pRow() + 0, 7, rs.getString("CodProd").trim());
					}
					imp.say(imp.pRow() + 0, 20,"" + vDesc.elementAt(i).toString());
					if (i==0) {
						imp.say(imp.pRow() + 0, 71, rs.getString("CodUnid").trim());
						imp.say(imp.pRow() + 0, 76, rs.getString("QtdItOrc"));
						imp.say(imp.pRow() + 0, 91, rs.getString("PrecoItOrc"));
						imp.say(imp.pRow() + 0, 105, rs.getString("VlrDescItOrc"));
						imp.say(imp.pRow() + 0, 121, rs.getString("VlrProdItOrc"));
					}
				}
			}
			imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
			imp.say(imp.pRow() + 0,0,Funcoes.replicate("-", 135));
			imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
			imp.say(imp.pRow() + 0, 86, "|    TOTAL PRODUTOS: " + rs.getString("VlrLiqOrc"));
			imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
			imp.say(imp.pRow() + 0,0,Funcoes.replicate("-", 135));
			imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
			imp.say(imp.pRow() + 0, 55, "INFORMAÇÕES COMPLEMENTARES");
			imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
			imp.say(imp.pRow() + 0,0,Funcoes.replicate("-", 135));
			imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
			imp.say(imp.pRow() + 0, 0, "PAGAMENTO.........:    " + rs.getString("CODPLANOPAG") + " - " + rs.getString("DESCPLANOPAG"));
			imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
			imp.say(imp.pRow() + 0, 0, "PRAZO DE ENTREGA..:    " + rs.getString("PrazoEntOrc"));
			imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
			imp.say(imp.pRow() + 0,0,Funcoes.replicate("-", 135));
			imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
			imp.say(imp.pRow() + 0, 62, "OBSERVACÃO");
			imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
          	
			vObs = Funcoes.quebraLinha(Funcoes.stringToVector(rs.getString("ObsOrc")),115);
          	
          	for (int i=0; i<vObs.size(); i++) {
          		imp.say(imp.pRow()+1,0,""+imp.comprimido());
                imp.say(imp.pRow()+0,20,vObs.elementAt(i).toString());
                if (imp.pRow()>=linPag) {
                    imp.incPags();
                    imp.eject();
                }
          	}
			
          	imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
			imp.say(imp.pRow() + 0, 0,Funcoes.replicate("-", 135));
			imp.say(imp.pRow() + 2, 0, "" + imp.comprimido());
			imp.say(imp.pRow() + 0, 5,Funcoes.replicate("-", 40));
			imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
			imp.say(imp.pRow() + 0, 5, rs.getString("NomeVend"));
			imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
			imp.say(imp.pRow() + 0, 5, rs.getString(37));
			imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
			imp.say(imp.pRow() + 0, 5, rs.getString("EmailVend"));
			
			imp.eject();
			imp.fechaGravacao();

			if (!con.getAutoCommit())
				con.commit();
			
		} catch (SQLException err) {
			Funcoes.mensagemErro(this, "Erro ao consultar a tabela de Venda!"
					+ err.getMessage(),true,con,err);
		} finally {
			vDesc = null;
			vObs = null;		
			ps = null;
			rs = null;
			sSQL = null;
		}

		if (bVisualizar) {
			imp.preview(this);
		} else {
			imp.print();
		}
	}

	private Object[] prefs() {
		Object[] oRetorno = new Object[9];
		String sSQL = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			sSQL = "SELECT P.USAREFPROD,P.USALIQREL,P.TIPOPRECOCUSTO,P.CODTIPOMOV2,"
			     + "P.ORDNOTA,P.DESCCOMPPED,P.USAORCSEQ,P.OBSCLIVEND,P.RECALCPCORC,P4.USABUSCAGENPROD "
				 + "FROM SGPREFERE1 P, SGPREFERE4 P4 "
				 + "WHERE P.CODEMP=? AND P.CODFILIAL=? "
				 + "AND P4.CODEMP=P.CODEMP AND P4.CODFILIAL=P.CODFILIAL";
			ps = con.prepareStatement(sSQL);
			ps.setInt(1, Aplicativo.iCodEmp);
			ps.setInt(2, ListaCampos.getMasterFilial("SGPREFERE1"));
			rs = ps.executeQuery();
			if (rs.next()) {
				if (rs.getString("UsaRefProd").trim().equals("S"))
					oRetorno[0] = new Boolean(true);
				else
					oRetorno[0] = new Boolean(false);
				if (rs.getString("UsaLiqRel") == null) {
					oRetorno[1] = new Boolean(false);
					Funcoes.mensagemInforma(this, "Preencha opção de desconto em preferências!");
				} else {
					if (rs.getString("UsaLiqRel").trim().equals("S"))
						oRetorno[1] = new Boolean(true);
					else
						oRetorno[1] = new Boolean(false);
				}
				if (rs.getString("TipoPrecoCusto").equals("M"))
					oRetorno[2] = new Boolean(true);
				else
					oRetorno[2] = new Boolean(false);
				if (rs.getString("CODTIPOMOV2") != null)
					oRetorno[3] = new Integer(rs.getInt("CODTIPOMOV2"));
				else
					oRetorno[3] = new Integer(0);
				if (rs.getString("DescCompPed") != null)
					bDescComp = true;
				else
					bDescComp = false;
				if(rs.getString("UsaOrcSeq").equals("S"))
					oRetorno[5] = new Boolean(true);
				else
					oRetorno[5] = new Boolean(false);
				if(rs.getString("ObsCliVend").equals("S"))
					oRetorno[6] = new Boolean(true);
				else
					oRetorno[6] = new Boolean(false);
				if(rs.getString("ReCalcPCOrc").equals("S"))
					oRetorno[7] = new Boolean(true);
				else
					oRetorno[7] = new Boolean(false);
				if(rs.getString("USABUSCAGENPROD").equals("S"))
					oRetorno[8] = new Boolean(true);
				else
					oRetorno[8] = new Boolean(false);
				
				sOrdNota = rs.getString("OrdNota");
				
			}
			rs.close();
			ps.close();
			
			sSQL = "SELECT IMPGRAFICA FROM SGESTACAOIMP WHERE CODEMP=? AND CODFILIAL=? AND IMPPAD='S' AND CODEST=?";
			ps = con.prepareStatement(sSQL);
			ps.setInt(1, Aplicativo.iCodEmp);
			ps.setInt(2, ListaCampos.getMasterFilial("SGESTACAOIMP"));
			ps.setInt(3, Aplicativo.iNumEst);
			rs = ps.executeQuery();
			if (rs.next()) {
				sModoNota = "G";//rs.getString("OrdNota");
				if ( (rs.getString("IMPGRAFICA")!=null) && (!rs.getString("IMPGRAFICA").equals("S"))){
					sModoNota = "T";
				}
			}
			rs.close();
			ps.close();
			if (!con.getAutoCommit())
				con.commit();
		} catch (SQLException err) {
			Funcoes.mensagemErro(this,
					"Erro ao carregar a tabela SGPREFERE1!\n" + err.getMessage(),true,con,err);
		} finally {
			sSQL = null;
			ps = null;
			rs = null;
		}

		return oRetorno;
	}

	public void focusGained(FocusEvent fevt) { }

	public void focusLost(FocusEvent fevt) {
		if(fevt.getSource() == txtPercDescItOrc) {
			if (txtPercDescItOrc.getText().trim().length() > 1) {
				calcDescIt();
				calcVlrProd();
				calcTot();
			}
		}
		else if(fevt.getSource() == txtVlrDescItOrc) {
			if( bdVlrDescItAnt != txtVlrDescItOrc.getVlrBigDecimal())
				if(txtPercDescItOrc.getText().trim().length() < 1)
					txtPercDescItOrc.setVlrString("");
			if( txtVlrDescItOrc.getVlrBigDecimal().floatValue() >= 0 ) { 
				calcDescIt();
				calcVlrProd();
				calcTot();						
			}
			
			if (lcDet.getStatus() == ListaCampos.LCS_INSERT) {
				lcDet.post();
				lcDet.limpaCampos(true);
				lcDet.setState(ListaCampos.LCS_NONE);
				lcDet.edit();
				focusCodprod();
			} else if (lcDet.getStatus() == ListaCampos.LCS_EDIT) {
				lcDet.post();
				txtCodItOrc.requestFocus();
			}
		}
		else if ((fevt.getSource() == txtQtdItOrc) || (fevt.getSource() == txtPrecoItOrc)) {
			calcVlrProd();
			calcTot();
		}
	}

	public void keyPressed(KeyEvent kevt) {
		if (kevt.getKeyCode() == KeyEvent.VK_CONTROL) {
			bCtrl = true;
		} else if (kevt.getKeyCode() == KeyEvent.VK_O) {
			if (bCtrl) {
				btObs.doClick();
			}
		} else if (kevt.getKeyCode() == KeyEvent.VK_F4) {
			btFechaOrc.doClick();
		} else if (kevt.getKeyCode() == KeyEvent.VK_F3) {
			if (kevt.getSource() == txtPercDescItOrc
					|| kevt.getSource() == txtVlrDescItOrc)
				mostraTelaDescont();
		} else if (kevt.getKeyCode() == KeyEvent.VK_ENTER) {
			if(kevt.getSource() == txtCodPlanoPag)
				if(lcCampos.getStatus() == ListaCampos.LCS_INSERT)
					lcCampos.post();
		}
		if (kevt.getSource() == txtRefProd)
			lcDet.edit();
		
		super.keyPressed(kevt);
	}

	public void keyTyped(KeyEvent kevt) {
		super.keyTyped(kevt);
	}

	public void keyReleased(KeyEvent kevt) {
		if (kevt.getKeyCode() == KeyEvent.VK_CONTROL)
			bCtrl = false;
		super.keyReleased(kevt);
	}

	public void actionPerformed(ActionEvent evt) {
		if (evt.getSource() == btFechaOrc) {
			fechaOrc();
		} else if (evt.getSource() == btPrevimp)
			imprimir(true);
		else if (evt.getSource() == btImp)
			imprimir(false);
		else if (evt.getSource() == btOrc) {
			ImprimeOrc imp = new ImprimeOrc(txtCodOrc.getVlrInteger().intValue());
			imp.setConexao(con);
			dl = new FPrinterJob(imp, this);
			dl.setVisible(true);
		} else if (evt.getSource() == btObs) {
			mostraObs( "VDORCAMENTO", txtCodOrc.getVlrInteger().intValue() );
		} else if (evt.getSource() == btExp)
			exportar();
		super.actionPerformed(evt);
	}

	public void beforeCarrega(CarregaEvent cevt) {
		if (cevt.getListaCampos() == lcProd2) {
			lcProd.edit();
		}
	}

	public void afterCarrega(CarregaEvent cevt) {
		if (cevt.getListaCampos() == lcDet) {
			lcOrc2.carregaDados();//Carrega os Totais
		} else if ((cevt.getListaCampos() == lcProd) || (cevt.getListaCampos() == lcProd2)) {
			if (lcDet.getStatus() == ListaCampos.LCS_INSERT) {
				calcVlrItem(null,false);
			}
			lcAlmox.carregaDados();
		} else if (cevt.getListaCampos() == lcCampos) {
			String s = txtCodOrc.getVlrString();
			lcOrc2.carregaDados();//Carrega os Totais
			txtCodOrc.setVlrString(s);
			s = null;
		} else if (cevt.getListaCampos() == lcCli ) {
			if ( ((Boolean)oPrefs[6]).booleanValue() ) {
				if(iCodCliAnt!=txtCodCli.getVlrInteger().intValue()){
					iCodCliAnt = txtCodCli.getVlrInteger().intValue();
					mostraObsCli(iCodCliAnt,
								 new Point( this.getX(),this.getY() + pinCab.getHeight() + pnCab.getHeight() + 10),
								 new Dimension( spTab.getWidth(), 150 ) );
				}
			}
			if(((Boolean) oPrefs[7]).booleanValue()){
			    setReCalcPreco(true);
			}
			txtCodTpCli.setVlrInteger(new Integer(getCodTipoCli()));
			lcTipoCli.carregaDados();
		} else if(cevt.getListaCampos() == lcPlanoPag) {
		    if(((Boolean) oPrefs[7]).booleanValue())
			    setReCalcPreco(true);
		}
	}
	
	public void beforePost(PostEvent evt) {
		if (evt.getListaCampos() == lcCampos) {
			if(lcCampos.getStatus() == ListaCampos.LCS_INSERT) {
				if(((Boolean) oPrefs[5]).booleanValue())
					txtCodOrc.setVlrInteger(testaCodPK("VDORCAMENTO"));			
				txtStatusOrc.setVlrString("*");
			}
			if(podeReCalcPreco())
			    calcVlrItem("VDORCAMENTO",true);
			txtCodClComiss.setVlrInteger(new Integer(getClComiss(txtCodVend.getVlrInteger().intValue())));
		} 
		else if (evt.getListaCampos() == lcDet) {
			if ((lcDet.getStatus() == ListaCampos.LCS_INSERT) || (lcDet.getStatus() == ListaCampos.LCS_EDIT)) {
				if (!testaLucro()) {
					Funcoes.mensagemInforma(this,"Não é permitido a venda deste produto abaixo do custo!!!");
					evt.cancela();
				}
			}
		}
    	setReCalcPreco(false);
	}

	public void afterPost(PostEvent pevt) {
		lcOrc2.carregaDados(); //Carrega os Totais
		if(pevt.getListaCampos() == lcCampos ) {
			if(lcDet.getStatus() == ListaCampos.LCS_NONE) {
				iniItem();
			}
		}
	}

	public void beforeDelete(DeleteEvent devt) { }

	public void afterDelete(DeleteEvent devt) {
		if (devt.getListaCampos() == lcDet)
			lcOrc2.carregaDados();
	}

	public void beforeInsert(InsertEvent ievt) { }

	public void afterInsert(InsertEvent ievt) {
		if (ievt.getListaCampos() == lcCampos)
			iniOrc();
		else if (ievt.getListaCampos() == lcDet)
			focusCodprod();
	}

	public void setConexao(Connection cn) {
		super.setConexao(cn);
		montaOrcamento();
		montaDetalhe();
		lcProd.setConexao(cn);
		lcProd2.setConexao(cn);
		lcOrc2.setConexao(cn);
		lcCli.setConexao(cn);
		lcPlanoPag.setConexao(cn);
		lcVend.setConexao(cn);
		lcTipoCli.setConexao(cn);
		lcAlmox.setConexao(cn);
		lcClComiss.setConexao(cn);
		
		if (((Boolean) oPrefs[8]).booleanValue()) {
			if (((Boolean) oPrefs[0]).booleanValue())
				txtRefProd.setBuscaGenProd( new DLCodProd( cn, null ) );
			else
				txtCodProd.setBuscaGenProd( new DLCodProd( cn, null ) );
		}
	}
}