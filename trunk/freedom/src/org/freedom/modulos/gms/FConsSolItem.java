/**
 * @version 02/08/2003 <BR>
 * @author Setpoint Informática Ltda./Alex Rodrigues <BR>
 *         Projeto: Freedom <BR>
 *         Pacote: org.freedom.modulos.std <BR>
 *         Classe:
 * @(#)FConsOrc.java <BR>
 *                   Este programa é licenciado de acordo com a LPG-PC (Licença
 *                   Pública Geral para Programas de Computador), <BR>
 *                   versão 2.1.0 ou qualquer versão posterior. <BR>
 *                   A LPG-PC deve acompanhar todas PUBLICAÇÕES, DISTRIBUIÇÕES e
 *                   REPRODUÇÕES deste Programa. <BR>
 *                   Caso uma cópia da LPG-PC não esteja disponível junto com
 *                   este Programa, você pode contatar <BR>
 *                   o LICENCIADOR ou então pegar uma cópia em: <BR>
 *                   Licença: http://www.lpg.adv.br/licencas/lpgpc.rtf <BR>
 *                   Para poder USAR, PUBLICAR, DISTRIBUIR, REPRODUZIR ou
 *                   ALTERAR este Programa é preciso estar <BR>
 *                   de acordo com os termos da LPG-PC <BR>
 *                   <BR>
 *                   Formulário de consulta de RMA.
 */

package org.freedom.modulos.gms;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import org.freedom.bmps.Icone;
import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.ImprimeOS;
import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JPanelPad;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.Tabela;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FFilho;

public class FConsSolItem extends FFilho implements ActionListener {
	private static final long serialVersionUID = 1L;
	private JPanelPad pinCab = new JPanelPad(0, 145);
	private JPanelPad pnCli = new JPanelPad(JPanelPad.TP_JPANEL,new BorderLayout());
	private JPanelPad pnRod = new JPanelPad(JPanelPad.TP_JPANEL,new BorderLayout());
	private JPanelPad pnLegenda = new JPanelPad(JPanelPad.TP_JPANEL,new GridLayout(0,5));
	private JTextFieldPad txtDtIni = new JTextFieldPad(JTextFieldPad.TP_DATE, 10,0);
	private JTextFieldPad txtDtFim = new JTextFieldPad(JTextFieldPad.TP_DATE, 10,0);
	private JTextFieldPad txtCodUsu = new JTextFieldPad(JTextFieldPad.TP_STRING, 8, 0);
	private JTextFieldFK txtNomeUsu = new JTextFieldFK(JTextFieldPad.TP_STRING, 50, 0);
	private JTextFieldPad txtCodCC = new JTextFieldPad(JTextFieldPad.TP_STRING, 19, 0);
	private JTextFieldPad txtAnoCC = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 4, 0);
	private JTextFieldFK txtDescCC = new JTextFieldFK(JTextFieldPad.TP_STRING, 50, 0);
	private JTextFieldPad txtCodAlmoxarife = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 10, 0);
	private JTextFieldFK txtDescAlmoxarife = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0);
	private JTextFieldPad txtCodProd = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);
	private JTextFieldFK txtDescProd = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0);
	private JTextFieldPad txtRefProd = new JTextFieldPad(JTextFieldPad.TP_STRING, 13, 0);
	private Tabela tab = new Tabela();
	private ImageIcon imgCancelada = Icone.novo("clVencido.gif");
	private ImageIcon imgExpedida = Icone.novo("clPago.gif");
	private ImageIcon imgAprovada = Icone.novo("clPagoParcial.gif");
	private ImageIcon imgPendente = Icone.novo("clNaoVencido.gif");
	private ImageIcon imgColuna = null;
	private JButton btCalc = new JButton(Icone.novo("btExecuta.gif"));
	private JButton btBusca = new JButton("Buscar", Icone.novo("btPesquisa.gif"));
	private JButton btPrevimp = new JButton("Imprimir", Icone.novo("btPrevimp.gif"));
	private	JButton btSair = new JButton("Sair", Icone.novo("btSair.gif"));
	private JScrollPane spnTab = new JScrollPane(tab);
	private ListaCampos lcAlmox = new ListaCampos(this, "AM");
	private ListaCampos lcUsuario = new ListaCampos(this, "");
	private ListaCampos lcCC = new ListaCampos(this, "CC");
	private ListaCampos lcProd = new ListaCampos(this, "PD");
	boolean bAprovaParcial = false;
	boolean bExpede = false;
	boolean bAprova = false;
	private Vector vSitSol = new Vector();
	public FConsSolItem() {
		super(false);
		setTitulo("Pesquisa Itens de Solicitações de Compra");
		setAtribos(10, 10, 555, 480);

		btCalc.setToolTipText("Criar cotação sumarizada");
		btCalc.addActionListener(this);
		
		txtDtIni.setRequerido(true);
		txtDtFim.setRequerido(true);

		txtCodAlmoxarife.setNomeCampo("CodAlmox");
		txtCodAlmoxarife.setFK(true);
		
		lcAlmox.add(new GuardaCampo(txtCodAlmoxarife, "CodAlmox", "Cód.almox.", ListaCampos.DB_PK, null, false));
		lcAlmox.add(new GuardaCampo(txtDescAlmoxarife, "DescAlmox", "Desc.almox;", ListaCampos.DB_SI, null, false));
		lcAlmox.setQueryCommit(false);
		lcAlmox.setReadOnly(true);

		txtDescAlmoxarife.setSoLeitura(true);
		txtCodAlmoxarife.setTabelaExterna(lcAlmox);
		lcAlmox.montaSql(false, "ALMOX", "EQ");
		
		txtCodProd.setNomeCampo("CodProd");
		txtCodProd.setFK(true);
		
		lcProd.add(new GuardaCampo(txtCodProd, "CodProd", "Cód.prod.", ListaCampos.DB_PK, null, false));
		lcProd.add(new GuardaCampo(txtDescProd, "DescProd", "Descrição do produto", ListaCampos.DB_SI, null, false));
		lcProd.add(new GuardaCampo(txtRefProd, "RefProd", "Referência", ListaCampos.DB_SI, null, false));
		lcProd.setQueryCommit(false);
		lcProd.setReadOnly(true);

		txtDescProd.setSoLeitura(true);
		txtRefProd.setSoLeitura(true);
		txtCodProd.setTabelaExterna(lcProd);
		lcProd.montaSql(false, "PRODUTO", "EQ");
				
		txtCodUsu.setNomeCampo("IDUSU");
		txtCodUsu.setFK(true);

		lcUsuario.add(new GuardaCampo(txtCodUsu, "IDUSU", "ID usuario", ListaCampos.DB_PK, null, false));
		lcUsuario.add(new GuardaCampo(txtNomeUsu, "NOMEUSU", "Nome do usuario", ListaCampos.DB_SI, null, false));
		lcUsuario.setQueryCommit(false);
		lcUsuario.setReadOnly(true);

		txtNomeUsu.setSoLeitura(true);
		txtCodUsu.setTabelaExterna(lcUsuario);
		lcUsuario.montaSql(false, "USUARIO", "SG");

		lcCC.add(new GuardaCampo(txtCodCC, "CodCC", "Cód.cc.", ListaCampos.DB_PK, false));
		lcCC.add(new GuardaCampo(txtAnoCC, "AnoCC", "Ano.cc.", ListaCampos.DB_PK, false));
		lcCC.add(new GuardaCampo(txtDescCC, "DescCC", "Descrição do centro de custo", ListaCampos.DB_SI, false));
		lcCC.setReadOnly(true);
		lcCC.setQueryCommit(false);
		lcCC.montaSql(false, "CC", "FN");
		txtCodCC.setTabelaExterna(lcCC);
		txtCodCC.setFK(true);
		txtCodCC.setNomeCampo("CodCC");
		
		Container c = getTela();
		c.add(pnRod, BorderLayout.SOUTH);
		c.add(pnCli, BorderLayout.CENTER);
		pnCli.add(pinCab, BorderLayout.NORTH);
		pnCli.add(spnTab, BorderLayout.CENTER);

		btSair.setPreferredSize(new Dimension(100, 30));
		
		pnLegenda.add(new JLabelPad("Cancelada",imgCancelada,SwingConstants.CENTER));
		pnLegenda.add(new JLabelPad("Aprovada",imgAprovada,SwingConstants.CENTER));
		pnLegenda.add(new JLabelPad("Em Cotação",imgExpedida,SwingConstants.CENTER));
		pnLegenda.add(new JLabelPad("Pendente",imgPendente,SwingConstants.CENTER));
		pnLegenda.add(btCalc);
		
		pnRod.add(pnLegenda,BorderLayout.WEST);
		pnRod.add(btSair, BorderLayout.EAST);
		
		pinCab.adic(new JLabelPad("Período:"), 7, 5, 50, 20);
		pinCab.adic(txtDtIni, 7, 25, 95, 20);
		pinCab.adic(new JLabelPad("Até"), 111, 25, 27, 20);
		pinCab.adic(txtDtFim, 139, 25, 95, 20);

		pinCab.adic(new JLabelPad("Cód.usu."), 237, 5, 70, 20);
		pinCab.adic(txtCodUsu, 237, 25, 80, 20);
		pinCab.adic(new JLabelPad("Nome do usuário"), 320, 5, 153, 20);
		pinCab.adic(txtNomeUsu, 320, 25, 163, 20);
		
		pinCab.adic(new JLabelPad("Cód.prod."), 7, 45, 80, 20);
		pinCab.adic(txtCodProd, 7, 65, 80, 20);
		pinCab.adic(new JLabelPad("Descrição do produto"), 90, 45, 200, 20);
		pinCab.adic(txtDescProd, 90, 65, 200, 20);

		pinCab.adic(new JLabelPad("Cód.c.c."), 7, 85, 70, 20);
		pinCab.adic(txtCodCC, 7, 105, 140, 20);
		pinCab.adic(new JLabelPad("Centro de custo"), 150, 85, 410, 20);
		pinCab.adic(txtDescCC, 150, 105, 180, 20);

		pinCab.adic(btBusca, 352, 57, 130, 30);
		pinCab.adic(btPrevimp, 352, 93, 130, 30);

		txtDtIni.setVlrDate(new Date());
		txtDtFim.setVlrDate(new Date());

		tab.adicColuna("");//0
		tab.adicColuna("Cotar");//1		
		tab.adicColuna("Cód.sol.");//2
		tab.adicColuna("Cód.prod.");//3
		tab.adicColuna("Descrição do produto");//4
		tab.adicColuna("Aprov.");//5
		tab.adicColuna("Comp.");//6
		tab.adicColuna("Dt. requisição");//7
		tab.adicColuna("Qt. requerida");//8
		tab.adicColuna("Dt. aprovação");//9
		tab.adicColuna("Qt. aprovada");//10
		tab.adicColuna("Saldo");//11
		
		tab.setTamColuna(12, 0);
		tab.setTamColuna(35, 1);
		tab.setTamColuna(70, 2);
		tab.setTamColuna(70, 3);
		tab.setTamColuna(150, 4);
		tab.setTamColuna(40, 5);
		tab.setTamColuna(40, 6);
		tab.setTamColuna(90, 7);
		tab.setTamColuna(90, 8);
		tab.setTamColuna(90, 9);
		tab.setTamColuna(90, 10);
		tab.setTamColuna(90, 11);

		tab.setColunaEditavel(1, true);
		
		btBusca.addActionListener(this);
		btPrevimp.addActionListener(this);

		tab.addMouseListener(new MouseAdapter() {

			public void mouseClicked(MouseEvent mevt) {
				if (mevt.getSource() == tab && mevt.getClickCount() == 2)
					abreRma();
			}
		});
		btSair.addActionListener(this);

	}

	private void habCampos(){
		getAprova();
		if(!bExpede){
			if(bAprova){
			  if(bAprovaParcial){
			  	txtCodCC.setVlrString(Aplicativo.strCodCCUsu);
				txtAnoCC.setVlrString(Aplicativo.strAnoCCUsu);
				txtCodCC.setNaoEditavel(true);
			  	lcUsuario.setWhereAdic("CODCC='"+Aplicativo.strCodCCUsu+"' AND ANOCC="+Aplicativo.strAnoCCUsu);
			  }
			  else {
			  	txtCodCC.setNaoEditavel(false);
			  	
			  }
			  txtCodUsu.setNaoEditavel(false);
			}
			else {
			  txtCodUsu.setVlrString(Aplicativo.strUsuario);		  
			  txtCodCC.setVlrString(Aplicativo.strCodCCUsu);
			  txtAnoCC.setVlrString(Aplicativo.strAnoCCUsu);
	
			  txtCodUsu.setNaoEditavel(true);
			  txtCodCC.setNaoEditavel(true);
			  lcUsuario.carregaDados();
			  lcCC.carregaDados();
			}
		}		
	}
	
	/**
	 * Carrega os valores para a tabela de consulta. Este método é executado após
	 * carregar o ListaCampos da tabela.
	 */
	private void carregaTabela() {
		String where = "";
		boolean usaOr = false;
		boolean usaWhere = false;
		boolean usuario = (!txtCodUsu.getVlrString().trim().equals(""));
		boolean almoxarifado = false;
		boolean CC = (!txtCodCC.getVlrString().trim().equals(""));
		String sCodProd = txtCodProd.getVlrString();

		
		if (where.trim().equals("")) {
			where = " (SitAprovItSol ='AP' OR SitAprovItSol ='AT')";
		} 
		else {
			where = where + " OR (SitAprovItSol ='AP' OR SitAprovItSol ='AT')";
			usaOr = true;
		}
		usaWhere = true;
		if (usaWhere && usaOr)
			where = " AND (" + where + ")";
		else if (usaWhere)
			where = " AND " + where;
		else
			where = " AND SitItSol='PE'";
		
		if (sCodProd.length() > 0) 
			where += " AND IT.CODPROD = '" + sCodProd + "'";
		
		if (almoxarifado)
			where += " AND IT.CODALMOX=? AND IT.CODEMPAM=? AND IT.CODFILIALAM=? ";

		if (CC)
			where += " AND O.ANOCC=? AND O.CODCC=? AND O.CODEMPCC=? AND O.CODFILIALCC=? ";

		if (usuario)
			where += " AND (O.IDUSU=?) ";

		String sSQL = "SELECT O.CODSOL, IT.CODPROD,IT.REFPROD,PD.DESCPROD,IT.SITITSOL,"
				+ "IT.SITAPROVITSOL,IT.SITCOMPITSOL,IT.DTINS,IT.DTAPROVITSOL,"
				+ "IT.QTDITSOL,IT.QTDAPROVITSOL,PD.SLDPROD "
				+ "FROM CPSOLICITACAO O, CPITSOLICITACAO IT, EQPRODUTO PD "
				+ "WHERE O.CODEMP=IT.CODEMP AND O.CODFILIAL=IT.CODFILIAL AND O.CODSOL=IT.CODSOL "
				+ "AND PD.CODEMP=IT.CODEMP AND PD.CODFILIAL=IT.CODFILIAL AND PD.CODPROD=IT.CODPROD "
				+ "AND ((IT.DTAPROVITSOL BETWEEN ? AND ?) OR  (O.DTEMITSOL BETWEEN ? AND ?)) " + where
				+ " ORDER BY PD.DESCPROD, IT.CODPROD, IT.QTDAPROVITSOL";

		try {
			PreparedStatement ps = con.prepareStatement(sSQL);
			int param = 1;
			ps.setDate(param++, Funcoes.dateToSQLDate(txtDtIni.getVlrDate()));
			ps.setDate(param++, Funcoes.dateToSQLDate(txtDtFim.getVlrDate()));
			ps.setDate(param++, Funcoes.dateToSQLDate(txtDtIni.getVlrDate()));
			ps.setDate(param++, Funcoes.dateToSQLDate(txtDtFim.getVlrDate()));

			if (almoxarifado) {
				ps.setInt(param++, txtCodAlmoxarife.getVlrInteger().intValue());
				ps.setInt(param++, Aplicativo.iCodEmp);
				ps.setInt(param++, Aplicativo.iCodFilial);
			}

			if (CC) {
				ps.setInt(param++, txtAnoCC.getVlrInteger().intValue());
				ps.setString(param++, txtCodCC.getVlrString());
				ps.setInt(param++, Aplicativo.iCodEmp);
				ps.setInt(param++, Aplicativo.iCodFilial);
			}

			if (usuario) {
				ps.setString(param++, txtCodUsu.getVlrString());
			}

			ResultSet rs = ps.executeQuery();
			
			int iLin = 0;

			tab.limpa();
			vSitSol = new Vector();
			while (rs.next()) {
				tab.adicLinha();
				
				String sitSol = rs.getString(5);
				String sitAprovSol = rs.getString(6);
				String sitExpSol = rs.getString(7);
				
				if (sitSol.equalsIgnoreCase("PE")) {
					imgColuna = imgPendente;
				} 
				else if (sitSol.equalsIgnoreCase("CA")) {
					imgColuna = imgCancelada;
				} 
				else if (sitExpSol.equals("EP") || sitExpSol.equals("ET")) {
					imgColuna = imgExpedida;
				} 
				else if (sitAprovSol.equals("AP") || sitAprovSol.equals("AT")) {
					imgColuna = imgAprovada;
				}

				tab.setValor(imgColuna, iLin, 0);//SitItSol
				tab.setValor(new Boolean(false), iLin, 1);
				tab.setValor(new Integer(rs.getInt(1)), iLin, 2);//CodSol
				tab.setValor(rs.getString(2) == null ? "" : rs.getString(2) + "",iLin, 3);//CodProd 
				tab.setValor(rs.getString(3) == null ? "" : rs.getString(4).trim() + "",iLin, 4);//DescProd
				tab.setValor(rs.getString(5) == null ? "" : rs.getString(5) + "",iLin, 5);//SitAprov
				tab.setValor(rs.getString(6) == null ? "" : rs.getString(6) + "",iLin, 6);//SitExp
				tab.setValor(rs.getString(8) == null ? "" : Funcoes.sqlDateToStrDate(rs.getDate(8))+ "", iLin, 7);//Dt Req
				tab.setValor(rs.getString(9) == null ? "" : Funcoes.sqlDateToStrDate(rs.getDate(9))+ "", iLin, 9);//Dt Aprov
				tab.setValor(rs.getString(10) == null ? "" : rs.getString(10) + "",iLin, 8);//Qtd Req
				tab.setValor(rs.getString(11) == null ? "" : rs.getString(11) + "",iLin, 10);//Qtd Aprov
				tab.setValor(rs.getString(12) == null ? "" : rs.getString(12) + "",iLin, 11);//Saldo Prod

				iLin++;
				
			}

			if (!con.getAutoCommit())
				con.commit();
		} catch (SQLException err) {
			Funcoes.mensagemErro(this, "Erro ao carregar a tabela CPSOLICITACAO!\n"
					+ err.getMessage(),true,con,err);
			err.printStackTrace();
		}
	}

	private void imprimir(boolean bVisualizar) {
		ImprimeOS imp = new ImprimeOS("", con);
		int linPag = imp.verifLinPag() - 1;
		BigDecimal bTotalLiq = new BigDecimal("0");
		boolean bImpCot = false;

		/*
		 * bImpCot = Funcoes.mensagemConfirma(this, "Deseja imprimir informações de
		 * cotações de preço?") == 0 ? true : false;
		 */

		

		try {
			imp.limpaPags();
			for (int iLin = 0; iLin < tab.getNumLinhas(); iLin++) {
				if (imp.pRow() == 0) {
					imp.montaCab();
					imp.setTitulo("Relatório de Requisições de material");
					imp.addSubTitulo("Relatório de Requisições de material");
					imp.impCab(136, true);
					//	imp.say(imp.pRow()+1,0,""+imp.comprimido());
					imp.say(imp.pRow() + 0, 0, "| Rma.");
					imp.say(imp.pRow() + 0, 15, "| Emissão");
					imp.say(imp.pRow() + 0, 29, "| Situação");
					imp.say(imp.pRow() + 0, 45, "| Motivo.");
					imp.say(imp.pRow() + 0, 135, "|");
					imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());

					if (bImpCot) {
						imp.say(imp.pRow() + 0, 0, "| Nro. Pedido");
						imp.say(imp.pRow() + 0, 15, "| Nro. Nota");
						imp.say(imp.pRow() + 0, 29, "| Data Fat.");
						imp.say(imp.pRow() + 0, 41, "| ");
						imp.say(imp.pRow() + 0, 56, "| ");
						imp.say(imp.pRow() + 0, 87, "| Vlr. Item Fat.");
						imp.say(imp.pRow() + 0, 105, "| ");
						imp.say(imp.pRow() + 0, 124, "| ");
						imp.say(imp.pRow() + 0, 135, "|");
						imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());

					}

					imp.say(imp.pRow() + 0, 0,"|"+Funcoes.replicate("-", 133)+"|");

				}

				imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
				imp.say(imp.pRow() + 0, 0, "|" + tab.getValor(iLin, 1));
				imp.say(imp.pRow() + 0, 15, "| " + tab.getValor(iLin, 2));
				imp.say(imp.pRow() + 0, 29, "| " + vSitSol.elementAt(iLin).toString());
				String sMotivo = ""+tab.getValor(iLin, 3);
				imp.say(imp.pRow() + 0, 45, "| " + sMotivo.substring(0, sMotivo.length()>89?89:sMotivo.length()).trim());
				imp.say(imp.pRow() + 0, 135, "| ");

				if (bImpCot) {
					imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
					imp.say(imp.pRow() + 0, 2, "|" + tab.getValor(iLin, 2));
					imp.say(imp.pRow() + 0, 15, "|" + tab.getValor(iLin, 3));
					imp.say(imp.pRow() + 0, 29, "|");
					imp.say(imp.pRow() + 0, 41, "|");
					imp.say(imp.pRow() + 0, 56, "|");
					imp.say(imp.pRow() + 0, 87, "|" + tab.getValor(iLin, 12));
					imp.say(imp.pRow() + 0, 105, "|");
					imp.say(imp.pRow() + 0, 124, "|");
					imp.say(imp.pRow() + 0, 135, "|");
				}

				
				if (tab.getValor(iLin, 9) != null) {
					bTotalLiq = bTotalLiq.add(new BigDecimal(Funcoes
							.strCurrencyToDouble("" + tab.getValor(iLin, 9))));
				}

				if (imp.pRow() >= linPag) {
					imp.incPags();
					imp.eject();
				}
			}

			imp.say(imp.pRow() + 1, 0,"+"+Funcoes.replicate("-", 133)+"+");
			imp.eject();

			imp.fechaGravacao();

			if (!con.getAutoCommit())
				con.commit();

		} catch (SQLException err) {
			Funcoes.mensagemErro(this, "Erro consulta tabela de orçamentos!\n"
					+ err.getMessage(),true,con,err);
		}

		if (bVisualizar) {
			imp.preview(this);
		} else {
			imp.print();
		}
	}

	private void abreRma() {
		int iRma = ((Integer) tab.getValor(tab.getLinhaSel(), 1)).intValue();
		if (fPrim.temTela("Requisição de material") == false) {
			FRma tela = new FRma();
			fPrim.criatela("Requisição de material", tela, con);
			tela.exec(iRma);
		}
	}

    private void getAprova() {
		String sSQL = "SELECT ANOCC,CODCC,CODEMPCC,CODFILIALCC,APROVRMAUSU,ALMOXARIFEUSU " +
				      "FROM SGUSUARIO WHERE CODEMP=? AND CODFILIAL=? " +
				      "AND IDUSU=?";
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			
			ps = con.prepareStatement(sSQL);
			ps.setInt(1, Aplicativo.iCodEmp);
			ps.setInt(2, ListaCampos.getMasterFilial("SGUSUARIO"));
			ps.setString(3, Aplicativo.strUsuario);
			rs = ps.executeQuery();
			if (rs.next()) {
				String sAprova = rs.getString("APROVRMAUSU");
				String sExpede = rs.getString("ALMOXARIFEUSU");
				if(sAprova!=null){
					if(!sAprova.equals("ND")) {
						if(sAprova.equals("TD"))						
							bAprova = true;
						else if( (Aplicativo.strCodCCUsu.equals(rs.getString("CODCC"))) &&
								 (Aplicativo.iCodEmp==rs.getInt("CODEMPCC")) &&
								 (ListaCampos.getMasterFilial("FNCC")==rs.getInt("CODFILIALCC")) &&
								 (sAprova.equals("CC"))	) { 
							bAprova = true;	
							bAprovaParcial = true;
						}						
					}
				}
				if(sExpede!=null){
					if(sExpede.equals("S")){
						bExpede = true;
					}
					else {
						bExpede = false;
					}
				}
			}
			if (!con.getAutoCommit())
				con.commit();

		} catch (SQLException err) {
			Funcoes.mensagemErro(this, "Erro ao carregar a tabela PREFERE1!\n"
					+ err.getMessage(),true,con,err);
		}
    }
  
	
	public void actionPerformed(ActionEvent evt) {
		if (evt.getSource() == btSair) {
			dispose();
		}
		if (evt.getSource() == btBusca) {
			if (txtDtIni.getVlrString().length() < 10)
				Funcoes.mensagemInforma(this, "Digite a data inicial!");
			else if (txtDtFim.getVlrString().length() < 10)
				Funcoes.mensagemInforma(this, "Digite a data final!");
			else
				carregaTabela();
			if (evt.getSource() == btPrevimp) {
				imprimir(true);
			}
		}
		if (evt.getSource() == btPrevimp) {
			imprimir(true);
		}

	}

	public void setConexao(Connection cn) {
		super.setConexao(cn);
		lcAlmox.setConexao(cn);
		lcProd.setConexao(cn);
		lcUsuario.setConexao(cn);
		lcCC.setConexao(cn);
		habCampos();
	}
}