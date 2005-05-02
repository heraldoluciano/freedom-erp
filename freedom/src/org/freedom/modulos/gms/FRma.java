/**
 * @version 14/07/2003 <BR>
 * @author Setpoint Informática Ltda./Anderson Sanchez <BR>
 *         Projeto: Freedom <BR>
 *         Pacote: org.freedom.modulos.gms <BR>
 *         Classe:
 * @(#)FRma.java <BR>
 * Este programa é licenciado de acordo com a LPG-PC (Licença
 * Pública Geral para Programas de Computador), <BR>
 * versão 2.1.0 ou qualquer versão posterior. <BR>
 * A LPG-PC deve acompanhar todas PUBLICAÇÕES, DISTRIBUIÇÕES e
 * REPRODUÇÕES deste Programa. <BR>
 * Caso uma cópia da LPG-PC não esteja disponível junto com
 * este Programa, você pode contatar <BR>
 * o LICENCIADOR ou então pegar uma cópia em: <BR>
 * Licença: http://www.lpg.adv.br/licencas/lpgpc.rtf <BR>
 * Para poder USAR, PUBLICAR, DISTRIBUIR, REPRODUZIR ou ALTERAR
 * este Programa é preciso estar <BR>
 * de acordo com os termos da LPG-PC <BR>
 * <BR>
 * Tela para cadastro de Requisições de material ao almoxarifado.
 */

package org.freedom.modulos.gms;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import javax.swing.JScrollPane;

import org.freedom.acao.CarregaEvent;
import org.freedom.acao.CarregaListener;
import org.freedom.acao.InsertEvent;
import org.freedom.acao.InsertListener;
import org.freedom.acao.PostEvent;
import org.freedom.acao.PostListener;
import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.ImprimeOS;
import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JPanelPad;
import org.freedom.componentes.JTextAreaPad;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Funcoes;
import org.freedom.modulos.std.DLBuscaEstoq;
import org.freedom.modulos.std.DLBuscaProd;
import org.freedom.modulos.std.DLRPedido;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FDetalhe;

public class FRma extends FDetalhe implements PostListener,
		CarregaListener, FocusListener, ActionListener, InsertListener {

	private int casasDec = Aplicativo.casasDec;
	private JPanelPad pinCab = new JPanelPad();
	private JPanelPad pinDet = new JPanelPad();
//	private JButton btLimpaRma = new JButton("Limpa Autorização da Rma", null);
//	private JButton btLimpaItem = new JButton("Limpa autorização do item", null);
//	private JButton btStatusRma = new JButton("Solicitação pendente", null);
//	private JButton btStatusItem = new JButton("Item pendente", null);
	private JTextFieldPad txtCodRma = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);
	private JTextFieldPad txtCodItRma = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);
	private JTextFieldPad txtQtdItRma = new JTextFieldPad(JTextFieldPad.TP_DECIMAL, 15, casasDec);
	private JTextFieldPad txtCustoMPMProd = new JTextFieldPad(JTextFieldPad.TP_DECIMAL, 15, casasDec);
	private JTextFieldPad txtQtdItAprovRma = new JTextFieldPad(JTextFieldPad.TP_DECIMAL, 15, casasDec);
	private JTextFieldPad txtPrecoItRma = new JTextFieldPad(JTextFieldPad.TP_DECIMAL, 15, casasDec);
//	private JTextFieldPad txtCodUsu = new JTextFieldPad(JTextFieldPad.TP_STRING,13, 0);
	private JTextFieldPad txtCodProd = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 10, 0);
	private JTextFieldPad txtRefProd = new JTextFieldPad(JTextFieldPad.TP_STRING,13, 0);
	private JTextFieldPad txtCodCC = new JTextFieldPad(JTextFieldPad.TP_STRING,	19, 0);
	private JTextFieldFK txtDescCC = new JTextFieldFK(JTextFieldPad.TP_STRING,50, 0);
	private JTextFieldPad txtAnoCC = new JTextFieldPad(JTextFieldPad.TP_INTEGER,10, 0);
	private JTextFieldFK txtDescProd = new JTextFieldFK(JTextFieldPad.TP_STRING,50, 0);
	private JTextFieldPad txtCodAlmox = new JTextFieldPad(JTextFieldPad.TP_STRING, 8, 0);
	private JTextFieldPad txtCodTpMov = new JTextFieldPad(JTextFieldPad.TP_STRING, 8, 0);
	private JTextFieldFK txtDescAlmox = new JTextFieldFK(JTextFieldPad.TP_STRING, 50, 0);	
	private JTextFieldPad txtIDUsu = new JTextFieldPad(JTextFieldPad.TP_STRING,8,0);
	private JTextFieldPad txtNomeUsu = new JTextFieldPad(JTextFieldPad.TP_STRING,40,0);
	private JTextFieldPad txtCodCCUsu = new JTextFieldPad(JTextFieldPad.TP_STRING,19,0);	
	private JTextFieldPad txtDtaReqRma = new JTextFieldPad(JTextFieldPad.TP_DATE,10,0);
	private JTextFieldFK txtDescTipoMov = new JTextFieldFK(JTextFieldPad.TP_STRING, 40, 0);
	private JTextAreaPad txaMotivoRma = new JTextAreaPad();
	private JScrollPane spnMotivo = new JScrollPane(txaMotivoRma);
//	private JTextFieldPad txtSitRma = new JTextFieldPad(JTextFieldPad.TP_STRING, 2, 0);
//	private JTextFieldPad txtSitItRma = new JTextFieldPad(JTextFieldPad.TP_STRING, 2, 0);
	private ListaCampos lcAlmox = new ListaCampos(this, "AX");
	private ListaCampos lcProd = new ListaCampos(this, "PD");
	private ListaCampos lcProd2 = new ListaCampos(this, "PD");
	private ListaCampos lcCC = new ListaCampos(this, "CC");
	private ListaCampos lcUsu = new ListaCampos(this,"UU");
	private ListaCampos lcTipoMov = new ListaCampos(this, "TM");
	String sOrdRMA = "";
	Integer anoCC = null;
	String codCC = null;

	boolean[] bPrefs = null;

	public FRma() {

		setAtribos(15, 10, 760, 580);

		pnMaster.remove(2);
		pnGImp.removeAll();
		pnGImp.setLayout(new GridLayout(1, 3));
		pnGImp.setPreferredSize(new Dimension(220, 26));
		pnGImp.add(btPrevimp);
		pnGImp.add(btImp);

		pnMaster.add(spTab, BorderLayout.CENTER);

		lcTipoMov.add(new GuardaCampo(txtCodTpMov, "CodTipoMov",
				"Cód.tp.mov.", ListaCampos.DB_PK, false));
		lcTipoMov.add(new GuardaCampo(txtDescTipoMov, "DescTipoMov",
				"Descrição do tipo de movimento", ListaCampos.DB_SI, false));
		lcTipoMov.setWhereAdic("((ESTIPOMOV = 'S') AND TIPOMOV='RM' AND"
							 + " ( TUSUTIPOMOV='S' OR	EXISTS (SELECT * FROM EQTIPOMOVUSU TU "
						     + "WHERE TU.CODEMP=EQTIPOMOV.CODEMP AND TU.CODFILIAL=EQTIPOMOV.CODFILIAL AND "
						     + "TU.CODTIPOMOV=EQTIPOMOV.CODTIPOMOV AND TU.CODEMPUS="
						     + Aplicativo.iCodEmp + " AND " + "TU.CODFILIALUS="
						     + ListaCampos.getMasterFilial("SGUSUARIO")
						     + " AND TU.IDUSU='" + Aplicativo.strUsuario + "') ) "
						     + ")");
		lcTipoMov.montaSql(false, "TIPOMOV", "EQ");
		lcTipoMov.setQueryCommit(false);
		lcTipoMov.setReadOnly(true);
		txtCodTpMov.setTabelaExterna(lcTipoMov);

		lcProd.add(new GuardaCampo(txtCodProd, "CodProd", "Cód.prod.",ListaCampos.DB_PK, false));
		lcProd.add(new GuardaCampo(txtDescProd, "DescProd", "Descrição do produto",	ListaCampos.DB_SI, false));
		lcProd.add(new GuardaCampo(txtRefProd, "RefProd", "Referência",	ListaCampos.DB_SI, false));
		lcProd.add(new GuardaCampo(txtCustoMPMProd, "CustoMPMProd", "Custo MPM",	ListaCampos.DB_SI, false));

		lcProd.setWhereAdic("ATIVOPROD='S' AND TIPOPROD='C'");
		lcProd.montaSql(false, "PRODUTO", "EQ");
		lcProd.setReadOnly(true);
		txtCodProd.setTabelaExterna(lcProd);

		lcProd2.add(new GuardaCampo(txtRefProd, "RefProd", "Referência",ListaCampos.DB_PK, false));
		lcProd2.add(new GuardaCampo(txtDescProd, "DescProd", "Descrição",ListaCampos.DB_SI, false));
		lcProd2.add(new GuardaCampo(txtCodProd, "CodProd", "Cód.rod.",ListaCampos.DB_SI, false));
		lcProd2.add(new GuardaCampo(txtCustoMPMProd, "CustoMPMProd", "Custo MPM",	ListaCampos.DB_SI, false));

		txtRefProd.setNomeCampo("RefProd");
		txtRefProd.setListaCampos(lcDet);
		lcProd2.setWhereAdic("ATIVOPROD='S' AND TIPOPROD='C'");
		lcProd2.montaSql(false, "PRODUTO", "EQ");
		lcProd2.setQueryCommit(false);
		lcProd2.setReadOnly(true);
		txtRefProd.setTabelaExterna(lcProd2);

		lcAlmox.add(new GuardaCampo(txtCodAlmox, "CodAlmox", "Cod.almox.", ListaCampos.DB_PK,txtDescAlmox, false));
		lcAlmox.add(new GuardaCampo(txtDescAlmox, "DescAlmox", "Descrição do almoxarifado;", ListaCampos.DB_SI, false));
		lcAlmox.montaSql(false, "ALMOX", "EQ");
		lcAlmox.setQueryCommit(false);
		lcAlmox.setReadOnly(true);
		txtDescAlmox.setSoLeitura(true);
		txtCodAlmox.setTabelaExterna(lcAlmox);

		lcCC.add(new GuardaCampo(txtCodCC, "CodCC", "Cód.c.c.", ListaCampos.DB_PK, false));
		lcCC.add(new GuardaCampo(txtAnoCC, "AnoCC", "Ano c.c.", ListaCampos.DB_PK, false));
		lcCC.add(new GuardaCampo(txtDescCC, "DescCC", "Descrição do centro de custo", ListaCampos.DB_SI, false));
		lcCC.montaSql(false, "CC", "FN");
		lcCC.setQueryCommit(false);
		lcCC.setReadOnly(true);
		txtCodCC.setTabelaExterna(lcCC);
		txtAnoCC.setTabelaExterna(lcCC);

		lcUsu.add(new GuardaCampo(txtIDUsu,"idusu","Id.Usu.",ListaCampos.DB_PK,false));
	    lcUsu.add(new GuardaCampo(txtNomeUsu,"nomeusu","Nome do usuário",ListaCampos.DB_SI,false));
	    lcUsu.add(new GuardaCampo(txtCodCCUsu,"codcc","C.Custo Usuário",ListaCampos.DB_SI,false));
		lcUsu.montaSql(false, "USUARIO", "SG");
		lcUsu.setQueryCommit(false);
		lcUsu.setReadOnly(true);
		txtIDUsu.setTabelaExterna(lcUsu);
//		txtIDUsu.setEnabled(false);
				
		pinCab = new JPanelPad(740, 180);
		setListaCampos(lcCampos);
		setAltCab(220);
		setPainel(pinCab, pnCliCab);

		adicCampo(txtCodRma, 7, 20, 110, 20, "CodRma", "Cód.Rma",ListaCampos.DB_PK, true);
		adicCampo(txtCodTpMov, 120, 20, 70, 20, "CodTipoMov", "Cód.Tp.Mov.",ListaCampos.DB_FK,txtDescTipoMov, true);
		adicDescFK(txtDescTipoMov, 193, 20, 250, 20, "DescTipoMov", "Cód.Tp.Mov.");
		adicCampo(txtIDUsu, 446, 20, 120, 20, "IdUsu", "Id do usuário",ListaCampos.DB_FK, true);						
		adicCampo(txtDtaReqRma,569, 20, 80, 20, "DtaReqRma", "Data da Rma",ListaCampos.DB_SI, true);
		adicCampo(txtCodCC, 7, 60, 110, 20, "CodCC", "Cód.CC.",ListaCampos.DB_FK,txtDescCC, true);		
		adicCampo(txtAnoCC, 120, 60, 70, 20, "AnoCC", "Ano CC.",ListaCampos.DB_FK, true);		
		adicDescFK(txtDescCC, 193, 60, 250, 20, "DescCC", "Descrição do centro de custos");	
		adicDBLiv(txaMotivoRma, "MotivoRma", "Observações", false);
		adic(new JLabelPad("Observações"), 7, 80, 100, 20);
		adic(spnMotivo, 7, 100, 727, 77);

		txtIDUsu.setNaoEditavel(true);
		txtDtaReqRma.setNaoEditavel(true);
		txtCodCC.setNaoEditavel(true);
		txtAnoCC.setNaoEditavel(true);

		
		setListaCampos(true, "RMA", "EQ");
		lcCampos.setQueryInsert(false);

		txtQtdItRma.addFocusListener(this);
		lcCampos.addPostListener(this);
		lcCampos.addCarregaListener(this);
		lcProd.addCarregaListener(this);
		lcProd2.addCarregaListener(this);
		lcDet.addPostListener(this);
		lcDet.addCarregaListener(this);
		lcDet.addInsertListener(this);
		lcCampos.addInsertListener(this);

		btImp.addActionListener(this);
		btPrevimp.addActionListener(this);

	}

	private void montaDetalhe() {
		setAltDet(97);
		pinDet = new JPanelPad(740, 97);
		setPainel(pinDet, pnDet);
		setListaCampos(lcDet);
		setNavegador(navRod);

		adicCampo(txtCodItRma, 7, 20, 30, 20, "CodItRma", "Item",ListaCampos.DB_PK, true);
		if (comRef()) {
			adicCampo(txtRefProd,40, 20, 87, 20, "RefProd", "Referência",	ListaCampos.DB_FK,txtDescProd, true);
			adicCampoInvisivel(txtCodProd, "CodProd", "Cód.prod.", ListaCampos.DB_SI, false);
		  	txtRefProd.setBuscaAdic(new DLBuscaProd(con,"REFPROD"));
		  	txtQtdItRma.setBuscaAdic(new DLBuscaEstoq(lcDet, lcAlmox,lcProd2,con,"qtditrma"));
		} 
		else {
			adicCampo(txtCodProd, 40, 20, 87, 20, "CodProd", "Cód.prod.",ListaCampos.DB_FK, txtDescProd, true);
			adicCampoInvisivel(txtRefProd,"RefProd", "Referência",	ListaCampos.DB_SI, false);
			txtCodProd.setBuscaAdic(new DLBuscaProd(con,"CODPROD"));
			txtQtdItRma.setBuscaAdic(new DLBuscaEstoq(lcDet, lcAlmox,lcProd,con,"qtditrma"));
		}

		
		adicDescFK(txtDescProd, 130, 20, 197, 20, "DescProd","Descrição do produto");

		adicCampo(txtQtdItRma, 330, 20, 67, 20, "QtdItRma", "Qtd.solic.",ListaCampos.DB_SI, true);

		txtQtdItAprovRma.setSoLeitura(true);
//		txtQtdItAprovRma.setNaoEditavel(true);
		
		adicCampo(txtQtdItAprovRma, 400, 20, 77, 20, "QtdAprovItRma", "Qtd.aprov.",	ListaCampos.DB_SI, false);		
		adicCampo(txtPrecoItRma,480,20,80,20,"PrecoItRma", "Preço",ListaCampos.DB_SI, true);
				
		txtCodAlmox.setNaoEditavel(true);
		txtPrecoItRma.setNaoEditavel(true);
		
//		adicCampoInvisivel(txtCodAlmox,"CodAlmox", "Cód.Almox.",ListaCampos.DB_FK,txtDescAlmox, false);
		adicCampoInvisivel(txtCodAlmox,"CodAlmox", "Cód.Almox.",ListaCampos.DB_FK,txtDescAlmox, false);
		adicDescFKInvisivel(txtDescAlmox, "DescAlmox","Descrição do almoxarifado");		 
		
		txtRefProd.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent kevt) {
				lcDet.edit();
			}
		});

		setListaCampos(true, "ITRMA", "EQ");
		lcDet.setQueryInsert(false);
		montaTab();

		tab.setTamColuna(30, 0);
		tab.setTamColuna(70, 1);
		tab.setTamColuna(250, 2);
		tab.setTamColuna(70, 3);
		tab.setTamColuna(70, 4);
		tab.setTamColuna(70, 5);
		tab.setTamColuna(70, 6);
		tab.setTamColuna(70, 7);
		tab.setTamColuna(250, 8);
	}

	public void focusGained(FocusEvent fevt) {}

	public void focusLost(FocusEvent fevt) {}

	public void beforeCarrega(CarregaEvent cevt) {}

	public void afterPost(PostEvent pevt) {}

	public void afterCarrega(CarregaEvent cevt) {
		if((cevt.getListaCampos() == lcProd)||(cevt.getListaCampos() == lcProd2)) {
			txtPrecoItRma.setVlrDouble(txtCustoMPMProd.getVlrDouble());
		}
	}

	public boolean[] prefs() {
		boolean[] bRet = {false};
		String sSQL = "SELECT USAREFPROD FROM SGPREFERE1 WHERE CODEMP=? AND CODFILIAL=?";
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement(sSQL);
			ps.setInt(1, Aplicativo.iCodEmp);
			ps.setInt(2, ListaCampos.getMasterFilial("SGPREFERE1"));
			rs = ps.executeQuery();
			if (rs.next()) {
				if (rs.getString("UsaRefProd").trim().equals("S"))
					bRet[0] = true;
			}
			if (!con.getAutoCommit())
				con.commit();

		} catch (SQLException err) {
			Funcoes.mensagemErro(this, "Erro ao carregar a tabela PREFERE1!\n"
					+ err.getMessage());
		}
		finally {
			rs = null;
			ps = null;
			sSQL = null;
		}
		return bRet;
	}
	public void actionPerformed(ActionEvent evt) {
		String[] sValores = null;
		if (evt.getSource() == btPrevimp)
			imprimir(true, txtCodRma.getVlrInteger().intValue());
		else if (evt.getSource() == btImp)
			imprimir(false, txtCodRma.getVlrInteger().intValue());
		super.actionPerformed(evt);
	}

	private void imprimir(boolean bVisualizar, int iCodSol) {
		ImprimeOS imp = new ImprimeOS("", con);
		DLRPedido dl = new DLRPedido(sOrdRMA);
		dl.setVisible(true);
		if (dl.OK == false) {
			dl.dispose();
			return;
		}
		imp.verifLinPag();
		imp.montaCab();
		imp.setTitulo("Impressão de RMA");
		String sSQL = "SELECT (SELECT COUNT(IT.CODITRMA) FROM EQITRMA IT WHERE IT.CODEMP=R.CODEMP AND IT.CODFILIAL = R.CODFILIAL AND IT.CODRMA=R.CODRMA),"
				+ "R.CODRMA,R.DTINS,R.SITRMA,R.MOTIVORMA,"
				+ "I.CODPROD, I.QTDITRMA, I.QTDAPROVITRMA,I.SITITRMA, P.REFPROD,P.DESCPROD, P.CODUNID,"
				+ "A.CODALMOX, A.DESCALMOX, CC.CODCC, CC.ANOCC"
				+ " FROM EQRMA R, EQITRMA I, EQALMOX A, FNCC CC, EQPRODUTO P"
				+ " WHERE R.CODEMP=? AND R.CODFILIAL=? AND R.CODRMA=?"
				+ " AND I.CODEMP=R.CODEMP AND I.CODFILIAL=R.CODFILIAL AND I.CODRMA=R.CODRMA"
				+ " AND P.CODEMP=I.CODEMPPD AND P.CODFILIAL=I.CODFILIALPD AND P.CODPROD=I.CODPROD"
				+ " AND I.CODEMP=R.CODEMP AND I.CODFILIAL=R.CODFILIAL "
				+ " AND CC.CODEMP=R.CODEMPCC AND CC.CODFILIAL=R.CODFILIALCC AND CC.CODCC=R.CODCC"
				+ " ORDER BY R.CODRMA,P."
				+ dl.getValor()
				+ ";";

		PreparedStatement ps = null;
		ResultSet rs = null;
		
		
		int iItImp = 0;
		int iMaxItem = 0;
		try {
			
						
			ps = con.prepareStatement(sSQL);
			
			ps.setInt(1,lcCampos.getCodEmp());
			ps.setInt(2,lcCampos.getCodFilial());
			ps.setInt(3,txtCodRma.getVlrInteger().intValue());


		
			rs = ps.executeQuery();
			imp.limpaPags();
			iMaxItem = imp.verifLinPag() - 23;
			while (rs.next()) {
				if (imp.pRow() == 0) {
					imp.impCab(136, false);
					imp.say(imp.pRow() + 1, 0, "" + imp.normal());
					imp.say(imp.pRow() + 0, 4, "REQUISIÇÃO DE MATERIAL No.: ");
					imp.say(imp.pRow() + 0, 25, rs.getString("CODRMA"));
					imp.say(imp.pRow() + 1, 0, "" + imp.normal());
					imp.say(imp.pRow() + 0, 0, "");
					imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
					imp.say(imp.pRow() + 0, 96, "[ Data de emissão ]");
					imp.say(imp.pRow() + 0, 100, Funcoes.sqlDateToStrDate(rs.getDate("DTINS")));
					imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
					imp.say(imp.pRow() + 0, 0, "");
					imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
					imp.say(imp.pRow() + 0, 57, "DADOS DO(S) PRODUTO(S)");
					imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
					imp.say(imp.pRow() + 0, 0, "");
					imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
					imp.say(imp.pRow() + 0, 4, "Referencia");
					imp.say(imp.pRow() + 0, 18, "Descrição dos produtos");
					imp.say(imp.pRow() + 0, 60, "Qtd.req.");
					imp.say(imp.pRow() + 0, 75, "Qtd.aprov.");
					imp.say(imp.pRow() + 0, 90, "Sit.item");
//					imp.say(imp.pRow() + 0, 110, "Sit.compra");
					imp.say(imp.pRow() + 0, 130, "Sit.aprov.");
					imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
					imp.say(imp.pRow() + 0, 0, "");
				}
				imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
				imp.say(imp.pRow() + 0, 4, rs.getString("RefProd"));
				imp.say(imp.pRow() + 0, 18, rs.getString("DescProd").substring(0, 39));
				imp.say(imp.pRow() + 0, 60, "" + rs.getDouble("QTDITRMA"));
				imp.say(imp.pRow() + 0, 75, "" + rs.getDouble("QTDAPROVITRMA"));
				if (rs.getString("SITITRMA").equalsIgnoreCase("PE"))
					imp.say(imp.pRow() + 0, 90, "PENDENTE");
				if (rs.getString("SITITRMA").equalsIgnoreCase("SC"))
					imp.say(imp.pRow() + 0, 90, "CONCLUÍDO");
				if (rs.getString("SITITRMA").equalsIgnoreCase("SA"))
					imp.say(imp.pRow() + 0, 90, "CANCELADO");
//				if (rs.getString("SITCOMPITRMA").equalsIgnoreCase("PE"))
	//				imp.say(imp.pRow() + 0, 110, "PENDENTE");
//				if (rs.getString("SITCOMPITSOL").equalsIgnoreCase("CP"))
	//				imp.say(imp.pRow() + 0, 110, "COMPRA PARCIAL");
//				if (rs.getString("SITCOMPITSOL").equalsIgnoreCase("CT"))
	//				imp.say(imp.pRow() + 0, 110, "COMPRA TOTAL");
//				if (rs.getString("SITAPROVITSOL").equalsIgnoreCase("PE"))
	//				imp.say(imp.pRow() + 0, 130, "PENDENTE");
		//		if (rs.getString("SITAPROVITSOL").equalsIgnoreCase("AP"))
			//		imp.say(imp.pRow() + 0, 130, "APROVAÇÂO PARCIAL");
//				if (rs.getString("SITAPROVITSOL").equalsIgnoreCase("AT"))
	//				imp.say(imp.pRow() + 0, 130, "APROVAÇÂO TOTAL");
		//		if (rs.getString("SITAPROVITSOL").equalsIgnoreCase("NA"))
			//		imp.say(imp.pRow() + 0, 130, "NÃO APROVADA");
				imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
				iItImp++;
				if ((imp.pRow() >= iMaxItem) | (iItImp == rs.getInt(1))) {
					if ((iItImp == rs.getInt(1))) {
						int iRow = imp.pRow();
						for (int i = 0; i < (iMaxItem - iRow); i++) {
							imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
							imp.say(imp.pRow() + 0, 0, "");
						}
					}
					imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
					imp.say(imp.pRow() + 0, 0, "");
					imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
					imp.say(imp.pRow() + 0, 0, "");
					imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
					imp.say(imp.pRow() + 0, 60, "DADOS ADICIONAIS");
					imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
					imp.say(imp.pRow() + 0, 0, "");
					imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
					imp.say(imp.pRow() + 0, 0, "");
					imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
					if (rs.getString("SITRMA").equalsIgnoreCase("PE"))
						imp.say(imp.pRow() + 0, (116 - "Pendente".length()) / 2,
								"SITUAÇÂO : PENDENTE");
					if (rs.getString("SITRMA").equalsIgnoreCase("SC"))
						imp.say(imp.pRow() + 0, (116 - "Pendente".length()) / 2,
								"SITUAÇÂO : CONCLUÍDA");
					if (rs.getString("SITRMA").equalsIgnoreCase("SA"))
						imp.say(imp.pRow() + 0, (116 - "Pendente".length()) / 2,
								"SITUAÇÂO : CANCELADA");
					imp.say(imp.pRow() + 2, 0, "" + imp.comprimido());
					imp.say(imp.pRow() + 0, 3, "MOTIVO : " + rs.getString("MOTIVORMA"));
					imp.eject();
				}
			}
			imp.fechaGravacao();

			if (!con.getAutoCommit())
				con.commit();
			dl.dispose();
		} catch (SQLException err) {
			Funcoes.mensagemErro(this, "Erro ao consultar a tabela de Compra!"
					+ err.getMessage());
		}

		if (bVisualizar) {
			imp.preview(this);
		} else {
			imp.print();
		}
	}

	private boolean comRef() {
		return bPrefs[0];
	}

	public void keyTyped(KeyEvent kevt) {
		super.keyTyped(kevt);
	}

	public void keyReleased(KeyEvent kevt) {
		super.keyReleased(kevt);
	}

	public void beforePost(PostEvent pevt) {
	}

	public void beforeInsert(InsertEvent ievt) {
	}

	public void afterInsert(InsertEvent ievt) {
		if (ievt.getListaCampos() == lcCampos) {
			txtAnoCC.setVlrInteger(anoCC);
			txtCodCC.setVlrString(codCC);
			lcCC.carregaDados();
			txtIDUsu.setVlrString(Aplicativo.strUsuario);
			txtDtaReqRma.setVlrDate(new Date());
		}			
	}

	public void exec(int iCodCompra) {
//		txtCodSolicitacao.setVlrString(iCodCompra + "");
		lcCampos.carregaDados();
	}

	public void execDev(int iCodFor, int iCodTipoMov, String sSerie, int iDoc) {
		lcCampos.insert(true);
	}

	private int buscaVlrPadrao() {
		int iRet = 0;
		String sSQL = "SELECT ANOCENTROCUSTO FROM SGPREFERE1 WHERE CODEMP=? AND CODFILIAL=?";
		try {
			PreparedStatement ps = con.prepareStatement(sSQL);
			ps.setInt(1, Aplicativo.iCodEmp);
			ps.setInt(2, ListaCampos.getMasterFilial("SGPREFERE1"));
			ResultSet rs = ps.executeQuery();
			if (rs.next())
				iRet = rs.getInt("ANOCENTROCUSTO");
			rs.close();
			ps.close();
		} 
		catch (SQLException err) {
			Funcoes.mensagemErro(this,"Erro ao buscar o ano-base para o centro de custo.\n" + err.getMessage());
		}
		return iRet;
	}

	public void setConexao(Connection cn) {
		super.setConexao(cn); // tem que setar a conexão principal para verificar preferências
		bPrefs = prefs();
		montaDetalhe();
		
		lcTipoMov.setConexao(cn);
		lcProd.setConexao(cn);
		lcProd2.setConexao(cn);
		lcCC.setConexao(cn);
		lcCC.setWhereAdic("NIVELCC=10 AND ANOCC=" + buscaVlrPadrao());
		lcAlmox.setConexao(cn);
		lcUsu.setConexao(cn);
		String sSQL = "SELECT anoCC, codCC, codAlmox, aprovCPSolicitacaoUsu FROM SGUSUARIO WHERE CODEMP=? AND CODFILIAL=? AND IDUsu=?";
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement(sSQL);
			ps.setInt(1, Aplicativo.iCodEmp);
			ps.setInt(2, ListaCampos.getMasterFilial("SGPREFERE1"));
			ps.setString(3, Aplicativo.strUsuario);
			rs = ps.executeQuery();
			if (rs.next()) {
				anoCC = new Integer(rs.getInt("anoCC"));
				if (anoCC.intValue() == 0)
					anoCC = new Integer(buscaVlrPadrao());
				codCC = rs.getString("codCC");

			}

			if (!con.getAutoCommit())
				con.commit();
		} catch (SQLException err) {
			Funcoes.mensagemErro(this, "Erro ao carregar a tabela PREFERE1!\n"
					+ err.getMessage());
		}
	}
}