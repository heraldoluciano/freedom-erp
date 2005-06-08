/**
 * @version 25/03/2004 <BR>
 * @author Setpoint Informática Ltda./Anderson Sanchez <BR>
 *
 * Projeto: Freedom <BR> 
 *  
 * Pacote: org.freedom.modulos.pcp <BR>  
 * Classe: @(#)FOP.java <BR>
 * 
 * Este programa é licenciado de acordo com a LPG-PC (Licença Pública Geral para Programas de Computador), <BR>
 * versão 2.1.0 ou qualquer versão posterior. <BR>
 * A LPG-PC deve acompanhar todas PUBLICAÇÕES, DISTRIBUIÇÕES e REPRODUÇÕES deste Programa. <BR>
 * Caso uma cópia da LPG-PC não esteja disponível junto com este Programa, você pode contatar <BR>
 * o LICENCIADOR ou então pegar uma cópia em: <BR>
 * Licença: http://www.lpg.adv.br/licencas/lpgpc.rtf <BR>
 * Para poder USAR, PUBLICAR, DISTRIBUIR, REPRODUZIR ou ALTERAR este Programa é preciso estar <BR>
 * de acordo com os termos da LPG-PC <BR> <BR>
 *
 * Tela de cadastro de ordens de produção.
 * 
 */ 

package org.freedom.modulos.pcp;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import org.freedom.componentes.JLabelPad;

import org.freedom.acao.CancelEvent;
import org.freedom.acao.CancelListener;
import org.freedom.acao.CarregaEvent;
import org.freedom.acao.CarregaListener;
import org.freedom.acao.InsertEvent;
import org.freedom.acao.InsertListener;
import org.freedom.acao.PostEvent;
import org.freedom.acao.PostListener;
import org.freedom.bmps.Icone;
import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.JPanelPad;
import org.freedom.funcoes.Funcoes;
import org.freedom.layout.LeiauteGR;
import org.freedom.modulos.std.DLBuscaProd;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FDetalhe;
import org.freedom.telas.FPrinterJob;

public class FOP extends FDetalhe implements PostListener,CancelListener,InsertListener,ActionListener,CarregaListener { 
  private int casasDec = Aplicativo.casasDec;
  private JPanelPad pinCab = new JPanelPad();
  private JPanelPad pinDet = new JPanelPad();
  private JTextFieldPad txtCodOP = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldPad txtCodProdEst = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldFK txtDescEst = new JTextFieldFK(JTextFieldPad.TP_STRING, 50, 0);
  private JTextFieldPad txtQtdEst = new JTextFieldPad(JTextFieldPad.TP_NUMERIC,15,2);
  private JTextFieldPad txtCodProdDet = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldPad txtRefProdDet = new JTextFieldPad(JTextFieldPad.TP_STRING,13,0);
  private JTextFieldPad txtRefProdEst = new JTextFieldPad(JTextFieldPad.TP_STRING,13,0);
  private JTextFieldFK txtDescProdDet = new JTextFieldFK(JTextFieldPad.TP_STRING, 50, 0);
  private JTextFieldPad txtDtFabProd = new JTextFieldPad(JTextFieldPad.TP_DATE,10,0);
  private JTextFieldPad txtQtdPrevProdOP = new JTextFieldPad(JTextFieldPad.TP_NUMERIC,15,2);
  private JTextFieldPad txtDtValidOP = new JTextFieldPad(JTextFieldPad.TP_DATE,10,0);
  private JTextFieldPad txtSeqItOp = new JTextFieldPad(JTextFieldPad.TP_INTEGER,5,0); 
  private JTextFieldPad txtQtdItOp = new JTextFieldPad(JTextFieldPad.TP_DECIMAL,15,2);
  private JTextFieldPad txtCodLoteProdDet = new JTextFieldPad(JTextFieldPad.TP_STRING,13,0);
  private JTextFieldFK txtDescLoteProdDet = new JTextFieldFK(JTextFieldPad.TP_DATE,10, 0);
  private JTextFieldPad txtCodLoteProdEst = new JTextFieldPad(JTextFieldPad.TP_STRING,13,0);
  private JTextFieldPad txtGeraRMAAut = new JTextFieldPad(JTextFieldPad.TP_STRING,1,0);
  private JTextFieldFK txtDescLoteProdEst = new JTextFieldFK(JTextFieldPad.TP_DATE,10, 0);
  private JTextFieldFK txtSldLiqProd = new JTextFieldFK(JTextFieldPad.TP_NUMERIC, 15, casasDec);
  private JTextFieldFK txtUsaLoteDet = new JTextFieldFK(JTextFieldPad.TP_STRING, 1, 0);
  private JTextFieldFK txtUsaLoteEst = new JTextFieldFK(JTextFieldPad.TP_STRING, 1, 0);
  private JTextFieldPad txtCodAlmoxEst = new JTextFieldPad(JTextFieldPad.TP_STRING, 8, 0);	
  private JTextFieldFK txtDescAlmoxEst = new JTextFieldFK(JTextFieldPad.TP_STRING, 50, 0);	
  private ListaCampos lcProdEstCod = new ListaCampos(this,"PD");
  private ListaCampos lcProdEstRef = new ListaCampos(this,"PD");
  private ListaCampos lcProdDetCod = new ListaCampos(this,"PD");
  private ListaCampos lcProdDetRef = new ListaCampos(this,"PD");
  private ListaCampos lcLoteProdDet = new ListaCampos(this, "LE");  
  private ListaCampos lcLoteProdEst = new ListaCampos(this, "LE");
  private JButton btFase = new JButton("Fases",Icone.novo("btFechaVenda.gif"));
  private JButton btRMA = new JButton("RMA",Icone.novo("btRma.gif"));
  private JButton btExecuta = new JButton("Finaliza",Icone.novo("btOP.gif"));
  private boolean bPrefs[] = null;
  private FPrinterJob dl = null;
  private ListaCampos lcTipoMov = new ListaCampos(this, "TM");
  private JTextFieldPad txtCodTpMov = new JTextFieldPad(JTextFieldPad.TP_STRING, 8, 0);
  private JTextFieldFK txtDescTipoMov = new JTextFieldFK(JTextFieldPad.TP_STRING, 40, 0);
  private Integer iCodTpMov = null;
  private JPanelPad pinBotCab = new JPanelPad(104,96);
  private ListaCampos lcAlmoxEst = new ListaCampos(this, "AX");
  
  public FOP () { }
  private void montaTela() {

  	setTitulo("Cadastro de Ordens de produção");
	setAtribos(15, 10, 640, 580);
	
  	setAltCab(170);
  	
	btFase.setToolTipText("Fases da produção");
	btRMA.setToolTipText("Gera ou exibe RMA.");
	btExecuta.setToolTipText("Processo de produção");
	
	pinCab.adic(pinBotCab,500,20,115,97);
	pinBotCab.adic(btFase,0,0,110,30); 
	pinBotCab.adic(btRMA,0,31,110,30);
	pinBotCab.adic(btExecuta,0,62,110,30);
  	
	lcAlmoxEst.add(new GuardaCampo(txtCodAlmoxEst, "CodAlmox", "Cod.almox.", ListaCampos.DB_PK,txtDescAlmoxEst, false));
	lcAlmoxEst.add(new GuardaCampo(txtDescAlmoxEst, "DescAlmox", "Descrição do almoxarifado;", ListaCampos.DB_SI, false));
	lcAlmoxEst.montaSql(false, "ALMOX", "EQ");
	lcAlmoxEst.setQueryCommit(false);
	lcAlmoxEst.setReadOnly(true);
	txtDescAlmoxEst.setSoLeitura(true);
	txtCodAlmoxEst.setTabelaExterna(lcAlmoxEst);	
	
	//FK de Lotes
	lcLoteProdEst.add(new GuardaCampo(txtCodLoteProdEst, "CodLote", "Lote",ListaCampos.DB_PK, txtDescLoteProdEst, false));
	lcLoteProdEst.add(new GuardaCampo(txtDescLoteProdEst, "VenctoLote", "Dt.vencto.",ListaCampos.DB_SI, false));
	lcLoteProdEst.add(new GuardaCampo(txtSldLiqProd, "SldLiqLote", "Saldo",ListaCampos.DB_SI, false));
	lcLoteProdEst.montaSql(false, "LOTE", "EQ");
	lcLoteProdEst.setQueryCommit(false);
	lcLoteProdEst.setReadOnly(true);
	txtCodLoteProdEst.setTabelaExterna(lcLoteProdEst);
  	
	lcTipoMov.add(new GuardaCampo(txtCodTpMov, "CodTipoMov",
			"Cód.tp.mov.", ListaCampos.DB_PK, false));
	lcTipoMov.add(new GuardaCampo(txtDescTipoMov, "DescTipoMov",
			"Descrição do tipo de movimento", ListaCampos.DB_SI, false));
	lcTipoMov.setWhereAdic("((ESTIPOMOV = 'E') AND TIPOMOV='OP' AND"
						 + " ( TUSUTIPOMOV='S' OR	EXISTS (SELECT * FROM EQTIPOMOVUSU TU "
					     + "WHERE TU.CODEMP=EQTIPOMOV.CODEMP AND TU.CODFILIAL=EQTIPOMOV.CODFILIAL AND "
					     + "TU.CODTIPOMOV=EQTIPOMOV.CODTIPOMOV AND TU.CODEMPUS="
					     + Aplicativo.iCodEmp + " AND " + "TU.CODFILIALUS="
					     + ListaCampos.getMasterFilial("SGUSUARIO")
					     + " AND TU.IDUSU='" + Aplicativo.strUsuario + "') ) "
					     + ")");
	lcTipoMov.montaSql(false, "TIPOMOV", "EQ");
	lcTipoMov.setReadOnly(true);
	txtCodTpMov.setTabelaExterna(lcTipoMov);
  	
  	lcProdEstCod.add(new GuardaCampo( txtCodProdEst, "Codprod", "Cód.prod.", ListaCampos.DB_PK, txtDescEst, true));
  	lcProdEstCod.add(new GuardaCampo( txtDescEst, "DescEst", "Descrição da estrutura", ListaCampos.DB_SI, false));
    lcProdEstCod.add(new GuardaCampo( txtRefProdEst, "refprod", "Referência", ListaCampos.DB_SI, false));  	
    lcProdEstCod.add(new GuardaCampo( txtQtdEst, "QtdEst", "Quantidade", ListaCampos.DB_SI,false));
    lcProdEstCod.setWhereAdic("ATIVOEST='S'");    						   
  	lcProdEstCod.montaSql(false, "ESTRUTURA", "PP");    
  	lcProdEstCod.setQueryCommit(false);
  	lcProdEstCod.setReadOnly(true);
  	txtCodProdEst.setTabelaExterna(lcProdEstCod);
  	txtCodProdEst.setNomeCampo("codprod");  	

    lcProdEstRef.add(new GuardaCampo( txtRefProdEst, "refprod", "Referência", ListaCampos.DB_PK, txtDescEst, true));
  	lcProdEstRef.add(new GuardaCampo( txtCodProdEst, "Codprod", "Cód.prod.", ListaCampos.DB_SI, true));
  	lcProdEstRef.add(new GuardaCampo( txtDescEst, "DescEst", "Descrição da estrutura", ListaCampos.DB_SI, false));  	
    lcProdEstRef.add(new GuardaCampo( txtQtdEst, "QtdEst", "Quantidade", ListaCampos.DB_SI, false));
    lcProdEstRef.setWhereAdic("ATIVOEST='S'");
  	lcProdEstRef.montaSql(false, "ESTRUTURA", "PP");    
  	lcProdEstRef.setQueryCommit(false);
  	lcProdEstRef.setReadOnly(true);
  	txtRefProdEst.setTabelaExterna(lcProdEstRef);
  	txtRefProdEst.setNomeCampo("refprod");

    setListaCampos(lcCampos);
    setPainel( pinCab, pnCliCab);
    
    adicCampo(txtCodOP, 7, 20, 70, 20,"CodOP","Nº OP.", ListaCampos.DB_PK, true);
	adicCampo(txtCodTpMov, 80, 20, 70, 20, "CodTipoMov", "Cód.Tp.Mov.",ListaCampos.DB_FK,txtDescTipoMov, true);
	adicDescFK(txtDescTipoMov, 153, 20, 340, 20, "DescTipoMov", "Cód.Tp.Mov.");

  	if (!bPrefs[0]) {  
  		adicCampo(txtCodProdEst, 7, 60, 70, 20,"CodProd","Cód.prod.", ListaCampos.DB_FK,txtDescEst, true);
//  		txtCodProdEst.setBuscaAdic(new DLBuscaProd(con,"CODPROD",lcProdEstCod.getWhereAdic()));
  		adicCampoInvisivel(txtRefProdEst,"RefProd","Ref.prod.", ListaCampos.DB_FK, null, true);
  	}
  	else {
  		adicCampo(txtRefProdEst, 7, 60, 70, 20,"refprod","Referência", ListaCampos.DB_FK, true);
  		adicCampoInvisivel(txtCodProdEst,"CodProd","Cód.prod.", ListaCampos.DB_FK, txtDescEst, true);
  	  	txtRefProdEst.setFK(true);
//  		txtRefProdEst.setBuscaAdic(new DLBuscaProd(con,"REFPROD",lcProdEstRef.getWhereAdic()));  	  	
  	}

  	adicDescFK(txtDescEst, 80, 60, 330, 20, "descprod", "Descrição da estrutura");

  	
  	adicCampo(txtQtdPrevProdOP,413,60,80,20,"qtdprevprodop","Quantidade",ListaCampos.DB_SI, true);

  	adicCampo(txtCodAlmoxEst,7,100,70,20,"codalmox","Cód.Almox.",ListaCampos.DB_FK,txtDescAlmoxEst,true);
  	adicDescFK(txtDescAlmoxEst, 80, 100, 180, 20, "descalmox", "Descrição do almoxarifado");
  	
  	adicCampo(txtDtFabProd,263,100,75,20,"dtfabrop","Dt.Fabric.",ListaCampos.DB_SI, true);  	
    adicCampo(txtCodLoteProdEst,341,100,75,20, "CodLote", "Lote", ListaCampos.DB_FK,txtDescLoteProdEst,false);
    
	adicDescFKInvisivel(txtDescLoteProdEst,"VenctoLote","Vencto.Lote");
	
	
    adicCampo(txtDtValidOP,419,100,75,20,"dtvalidpdop","Dt. validade",ListaCampos.DB_SI, false);
  	setListaCampos( true, "OP", "PP");  	
  	
    txtCodTpMov.setAtivo(false);
//    btFase.setEnabled(false);
  	
    lcCampos.addCancelListener(this);
  	lcCampos.addPostListener(this);
    lcCampos.addCarregaListener(this);
  	lcCampos.addInsertListener(this);

    lcDet.addCancelListener(this);
  	lcDet.addPostListener(this);
    lcDet.addCarregaListener(this);
  	lcDet.addInsertListener(this);

  	
    lcProdEstCod.addCarregaListener(this);
    lcProdEstRef.addCarregaListener(this);
    lcLoteProdEst.addCarregaListener(this);

    btFase.addActionListener(this);
    btRMA.addActionListener(this);
    btExecuta.addActionListener(this);
    btImp.addActionListener(this);
  	btPrevimp.addActionListener(this);     

  	montaDet();
  	
  	setImprimir(true);
  }
  
  private void montaDet(){
  	setAltDet(60);  	
  	pinDet = new JPanelPad(440,50);
  	setPainel( pinDet, pnDet);

  	txtCodLoteProdDet.setAtivo(false);
  	txtSeqItOp.setAtivo(false);
  	txtQtdItOp.setAtivo(false);
  	txtCodProdDet.setAtivo(false);
  	txtRefProdDet.setAtivo(false);
  	
	//FK de Lotes
	lcLoteProdDet.add(new GuardaCampo(txtCodLoteProdDet, "CodLote", "Lote",
			ListaCampos.DB_PK, txtDescLoteProdDet, false));
	lcLoteProdDet.add(new GuardaCampo(txtDescLoteProdDet, "VenctoLote", "Dt.vencto.",ListaCampos.DB_SI, false));
	lcLoteProdDet.add(new GuardaCampo(txtSldLiqProd, "SldLiqLote", "Saldo",ListaCampos.DB_SI, false));
	lcLoteProdDet.setDinWhereAdic("CODPROD=#N",txtCodProdDet);
	lcLoteProdDet.montaSql(false, "LOTE", "EQ");
	lcLoteProdDet.setQueryCommit(false);
	lcLoteProdDet.setReadOnly(true);
	txtCodLoteProdDet.setTabelaExterna(lcLoteProdDet);
	txtDescLoteProdDet.setListaCampos(lcLoteProdDet);
	txtDescLoteProdDet.setNomeCampo("VenctoLote");
	txtDescLoteProdDet.setLabel("Vencimento");

  	
  	lcProdDetCod.add(new GuardaCampo( txtCodProdDet, "Codprod", "Cód.prod.", ListaCampos.DB_PK, txtDescProdDet, true));
  	lcProdDetCod.add(new GuardaCampo( txtDescProdDet, "Descprod", "Descriçao do produto", ListaCampos.DB_SI, false));
    lcProdDetCod.add(new GuardaCampo( txtRefProdDet, "refprod", "referencia", ListaCampos.DB_SI, false));
    lcProdDetCod.add(new GuardaCampo( txtUsaLoteDet, "CLOTEPROD", "Usa Lote", ListaCampos.DB_SI,false));
  	lcProdDetCod.montaSql(false, "PRODUTO", "EQ");    
  	lcProdDetCod.setQueryCommit(false);
  	lcProdDetCod.setReadOnly(true);
  	txtCodProdDet.setTabelaExterna(lcProdDetCod);
  	txtCodProdDet.setNomeCampo("codprod");

  	lcProdDetRef.add(new GuardaCampo( txtRefProdDet, "refprod", "Cód.prod.", ListaCampos.DB_PK, txtDescProdDet, true));
  	lcProdDetRef.add(new GuardaCampo( txtDescProdDet, "Descprod", "Descriçao do produto", ListaCampos.DB_SI, false));
  	lcProdDetRef.add(new GuardaCampo( txtCodProdDet, "Codprod", "Cód.prod.", ListaCampos.DB_SI, true));
    lcProdDetRef.add(new GuardaCampo( txtUsaLoteDet, "CLOTEPROD", "Usa Lote", ListaCampos.DB_SI,false));
  	lcProdDetRef.montaSql(false, "PRODUTO", "EQ");    
  	lcProdDetRef.setQueryCommit(false);
  	lcProdDetRef.setReadOnly(true);
  	txtRefProdDet.setTabelaExterna(lcProdDetRef);
  	txtRefProdDet.setNomeCampo("refprod");
  	txtRefProdDet.setFK(true);
  	
  	setListaCampos(lcDet);
  	setNavegador(navRod);

  	adicCampo(txtSeqItOp,7,20,50,20,"seqitop","Seq.", ListaCampos.DB_PK, true);
  	if (!bPrefs[0]){
  		adicCampo(txtCodProdDet,60,20,70,20,"CodProd","Cód.prod.", ListaCampos.DB_PF, txtDescProdDet, true);
  	  	txtCodProdDet.setBuscaAdic(new DLBuscaProd(con,"CODPROD",lcProdDetCod.getWhereAdic()));
  	}
  	else {
  		adic(new JLabelPad("Referência"),60,0,70,20);
  		adic(txtRefProdDet,60,20,70,20);
  		adicCampoInvisivel(txtCodProdDet,"CodProd","Cód.prod.", ListaCampos.DB_PF, txtDescProdDet, true);
  		txtRefProdDet.setFK(true);
  	  	txtRefProdDet.setBuscaAdic(new DLBuscaProd(con,"REFPROD",lcProdDetRef.getWhereAdic()));
  	}
  	adicDescFK(txtDescProdDet,133,20,250,20,"descprod", "Descrição do produto");
  	adicCampo(txtCodLoteProdDet,386,20,90,20,"codlote","Lote", ListaCampos.DB_SI, false);
  	adicCampo(txtQtdItOp,479,20,90,20,"qtditop","Quantidade", ListaCampos.DB_SI, false);
  	adicCampoInvisivel(txtGeraRMAAut,"GERARMA","Gera Rma",ListaCampos.DB_SI,false);
  	setListaCampos( true, "ITOP", "PP");
  	lcDet.setQueryInsert(false);    

  	btFase.setEnabled(false);
  	btRMA.setEnabled(false);
  	btExecuta.setEnabled(false);
  	
  	//	navRod.setAtivo(4,false);
  	// 	navRod.setAtivo(5,false);
    
    montaTab();
    
    tab.setTamColuna(150,2);

  }
	private String buscaLote(ListaCampos lcProd, JTextFieldPad txtProd,boolean bSaldoPos) {
		String sRet = "";
		String sSQL = "SELECT MIN(L.CODLOTE) FROM EQLOTE L WHERE "
				+ "L.CODPROD=? AND L.CODFILIAL=? "+(bSaldoPos?"AND L.SLDLIQLOTE>0 ":"")
				+ "AND L.CODEMP=? AND L.VENCTOLOTE = "
				+ "( "
				+ "SELECT MIN(VENCTOLOTE) FROM EQLOTE LS WHERE LS.CODPROD=L.CODPROD "
				+ "AND LS.CODFILIAL=L.CODFILIAL AND LS.CODEMP=L.CODEMP AND LS.SLDLIQLOTE>0 "
				+ "AND VENCTOLOTE >= CAST('today' AS DATE)" + ")";
		try {
			PreparedStatement ps = con.prepareStatement(sSQL);
			ps.setInt(1, txtProd.getVlrInteger().intValue());
			ps.setInt(2, lcProd.getCodFilial());
			ps.setInt(3, Aplicativo.iCodEmp);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				String sCodLote = rs.getString(1);
				if (sCodLote != null) {
					sRet = sCodLote.trim();
				}
			}
			rs.close();
			ps.close();
		} catch (SQLException err) {
			Funcoes.mensagemErro(this, "Erro ao buscar lote!\n" + err);
		}
		return sRet;
	}
  
  private void abreFase() {
    if (fPrim.temTela("OP x Fases")==false) {
      FOPFase tela = new FOPFase(txtCodOP.getVlrInteger().intValue(),false);
      fPrim.criatela("OP x Fases",tela,con);
      tela.setConexao(con);
    }
  }
  
  public void executaOP(){
    if (fPrim.temTela("OP x Fases")==false) {
        FOPFase tela = new FOPFase(txtCodOP.getVlrInteger().intValue(),true);
        fPrim.criatela("OP x Fases",tela,con);
        tela.setConexao(con);
      }
  }
  
  private void buscaTipoMov(){
	if(txtCodTpMov.getVlrString().equals("")){
		String sSQL = "SELECT CODTIPOMOV FROM SGPREFERE5 WHERE CODEMP=? AND CODFILIAL=?";
		try {
			PreparedStatement ps = con.prepareStatement(sSQL);
			ps.setInt(1, Aplicativo.iCodEmp);
			ps.setInt(2, ListaCampos.getMasterFilial("SGPREFERE1"));
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				if(rs.getString(1)!=null) {				
					iCodTpMov = new Integer(rs.getInt(1));
				}
				else {
					iCodTpMov = new Integer(0);
					Funcoes.mensagemInforma(null,"Não existe um tipo de movimento padrão para OP definido nas preferências!");
				}
			}
			rs.close();
			ps.close();
		} 
		catch (SQLException err) {
			Funcoes.mensagemErro(this,"Erro ao buscar documento de preferências!\n" + err.getMessage());
		}
	}
  }
  public void geraRMA(){
	try {
		PreparedStatement ps = con.prepareStatement("SELECT GERARMA FROM PPITOP WHERE CODEMP=? AND CODFILIAL=? AND CODOP=? AND GERARMA='S'");
		ps.setInt(1, Aplicativo.iCodEmp);
		ps.setInt(2, ListaCampos.getMasterFilial("PPITOP"));
		ps.setInt(3,txtCodOP.getVlrInteger().intValue());
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			try{
				if(Funcoes.mensagemConfirma(null,"Confirma a geração de RMA para a OP:"+txtCodOP.getVlrString()+"?")==JOptionPane.YES_OPTION){
					PreparedStatement ps2 = null;
					ps2 = con.prepareStatement("EXECUTE PROCEDURE EQGERARMASP(?,?,?)");
					ps2.setInt(1,Aplicativo.iCodEmp);
					ps2.setInt(2,ListaCampos.getMasterFilial("PPOP"));
					ps2.setInt(3,txtCodOP.getVlrInteger().intValue());
					ps2.execute();
					con.commit();
					
					try{
						PreparedStatement ps3 = con.prepareStatement("SELECT CODRMA FROM EQRMA WHERE CODEMP=? AND CODFILIAL=? AND " +
																	 "CODEMPOF=CODEMP AND CODFILIALOF=? AND CODOP=?" );
						ps3.setInt(1, Aplicativo.iCodEmp);
						ps3.setInt(2, ListaCampos.getMasterFilial("PPITOP"));
						ps3.setInt(3, ListaCampos.getMasterFilial("PPOP"));
						ps3.setInt(4,txtCodOP.getVlrInteger().intValue());
						
						ResultSet rs2 = ps3.executeQuery();
						String sRma = "";
						while(rs2.next()) {
							sRma += rs2.getString(1)+" - ";
						}
						if (sRma.length()>0){
							Funcoes.mensagemInforma(this,"Foram geradas as seguintes RMA:\n"+sRma);															
						}
					}
					catch(Exception err){
						Funcoes.mensagemErro(this,"Erro ao buscar RMA criada",true,con,err);
						err.printStackTrace();
					}
					
				}
			}
			catch(Exception err){
				Funcoes.mensagemErro(this,"Erro ao criar RMA",true,con,err);
				err.printStackTrace();
			}
		}
		else {
			Funcoes.mensagemInforma(this,"Não há itens para gerar RMA.\n " +
					                     "Os itens não geram RMA automaticamente\n" +
					                     "ou o processo de geração de RMA já foi efetuado.");
		}
	}
	catch(Exception err){
		Funcoes.mensagemErro(this,"Erro ao consultar RMA",true,con,err);
		err.printStackTrace();
	}
  	
  }
  public void actionPerformed(ActionEvent evt) {
    super.actionPerformed(evt);    
	if (evt.getSource() == btImp) 
	  imprimir(false);
	else if (evt.getSource() == btPrevimp) 
	  imprimir(true);
    else if (evt.getSource() == btFase)
      abreFase();
    else if (evt.getSource() == btRMA)
        geraRMA();
    else if (evt.getSource() == btExecuta)
        executaOP();
    
    super.actionPerformed(evt);
  }
  
    
  public void beforeCarrega(CarregaEvent cevt) {
  	
  }
  
  
  
  public void setUsaLote(){
	String sSQL = "SELECT CLOTEPROD FROM EQPRODUTO WHERE CODEMP=? AND CODFILIAL=? AND CODPROD=?";
	try {
		PreparedStatement ps = con.prepareStatement(sSQL);
		ps.setInt(1, Aplicativo.iCodEmp);
		ps.setInt(2, ListaCampos.getMasterFilial("EQPRODUTO"));
		ps.setInt(3, txtCodProdEst.getVlrInteger().intValue());
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			txtUsaLoteEst.setVlrString(rs.getString(1));
		}
		rs.close();
		ps.close();
	}	
	catch (SQLException err) {
		Funcoes.mensagemErro(this,"Erro ao buscar obrigatoriedade de lote no produto!\n",true,con,err);
	}
  }
  public void afterCarrega(CarregaEvent cevt) {
    if (cevt.getListaCampos() == lcCampos) {
       btFase.setEnabled((lcCampos.getStatus() != ListaCampos.LCS_NONE) && (lcCampos.getStatus() != ListaCampos.LCS_INSERT));
       btRMA.setEnabled((lcCampos.getStatus() != ListaCampos.LCS_NONE) && (lcCampos.getStatus() != ListaCampos.LCS_INSERT));             
       btExecuta.setEnabled((lcCampos.getStatus() != ListaCampos.LCS_NONE) && (lcCampos.getStatus() != ListaCampos.LCS_INSERT));
    }
    if ((cevt.getListaCampos() == lcProdEstCod) || (cevt.getListaCampos() == lcProdEstRef)) {
       	setUsaLote();
    	if((txtCodLoteProdEst.getVlrString().equals("")) && (txtUsaLoteEst.getVlrString().equals("S"))){
    		
//    		txtCodLoteProdEst.setVisible(true);
    		txtCodLoteProdEst.setVlrString(buscaLote(lcProdEstCod,txtCodProdEst,false));
    		txtDtValidOP.setAtivo(false);
    	  	    	  	
    	  	lcLoteProdEst.setDinWhereAdic("CODPROD=#N AND (VENCTOLOTE >= #D)",txtCodProdEst);
    	  	lcLoteProdEst.setDinWhereAdic("", txtDtFabProd);
    	  	    	  	    		
    		lcLoteProdEst.carregaDados();
    	}
    	else if((txtUsaLoteEst.getVlrString().equals("N"))){
    		txtCodLoteProdEst.setAtivo(false);
//    		txtDescLoteProdEst.setAtivo(false);
    		txtDtValidOP.setAtivo(true);
    	}
    }
    if (cevt.getListaCampos() == lcLoteProdEst) {
		txtDtValidOP.setVlrDate(txtDescLoteProdEst.getVlrDate());    	
    }
  	if(cevt.getListaCampos() == lcDet) {
		if((txtCodLoteProdDet.getVlrString().equals("")) && (txtUsaLoteDet.getVlrString().equals("S"))){
			txtCodLoteProdDet.setAtivo(true);
			txtCodLoteProdDet.setVlrString(buscaLote(lcProdDetCod,txtCodProdDet,true));
			lcLoteProdDet.carregaDados();
		}
		else if((txtUsaLoteDet.getVlrString().equals("N"))){
			txtCodLoteProdDet.setAtivo(false);
		}
  	}  	
  }  
  
  public void afterPost(PostEvent pevt) { 	
  	if (lcCampos.getStatusAnt() == ListaCampos.LCS_INSERT) { 
  	  txtCodProdEst.setAtivo(false);
  	}
  	
  	btFase.setEnabled((lcCampos.getStatus() != ListaCampos.LCS_NONE) && (lcCampos.getStatus() != ListaCampos.LCS_INSERT));
  	
  }
  public void beforeCancel(CancelEvent cevt) {
  	txtCodProdEst.setAtivo(false);
  }
  public void afterInsert(InsertEvent ievt) {
  	if(ievt.getListaCampos()==lcCampos){
		buscaTipoMov();
  		txtCodTpMov.setVlrInteger(iCodTpMov);
		lcTipoMov.carregaDados();
		txtDtFabProd.setVlrDate(new Date());
	}			
  }
  public void beforeInsert(InsertEvent ievt) { }
  public void afterCancel(CancelEvent cevt) { }
  public void setConexao(Connection cn) {
  	super.setConexao(cn);
  	bPrefs=prefs(cn);
  	montaTela();
  	lcProdEstCod.setConexao(cn);
  	lcProdEstRef.setConexao(cn);
  	lcProdDetCod.setConexao(cn);
  	lcProdDetRef.setConexao(cn);
  	lcTipoMov.setConexao(cn);
  	lcLoteProdDet.setConexao(cn);
  	lcLoteProdEst.setConexao(cn);
  	lcAlmoxEst.setConexao(cn);
  	
  }
  private void imprimir(boolean bVisualizar) {
	Vector vParamOP = new Vector();
  	String sClassOP = "";
	String sSql = "SELECT CLASSOP FROM SGPREFERE5 WHERE CODEMP=? AND CODFILIAL=?";
	LeiauteGR leiOP = null;
	try {
		PreparedStatement ps = null;
		ResultSet rs = null;
		ps = con.prepareStatement(sSql);
		ps.setInt(1,Aplicativo.iCodEmp);
		ps.setInt(2,ListaCampos.getMasterFilial("SGPREFERE5"));
		rs = ps.executeQuery();

		if (rs.next()) {
			if (rs.getString("CLASSOP")!=null){
				sClassOP = rs.getString("CLASSOP").trim();              
			}
		} 
		rs.close();
		ps.close();
	}
	catch (SQLException err) {
		Funcoes.mensagemErro(this,"Erro ao carregar a tabela SGPREFERE5!\n"+err.getMessage(),true,con,err);
	}
	if (sClassOP.trim().equals("")) {          
		Funcoes.mensagemErro(this,"Não existe org.freedom.layout para ordem de produção. \n Cadastre o org.freedom.layout no documento de preferências do módulo de produção \n e tente novamente.");
	}
	else {
	  try {
		leiOP = (LeiauteGR)Class.forName("org.freedom.layout."+sClassOP).newInstance();      
		leiOP.setConexao(con);
		vParamOP.clear();
		vParamOP.addElement(txtCodOP.getText());
		leiOP.setParam(vParamOP);
		if (bVisualizar) { 
		  dl = new FPrinterJob(leiOP,this);
          dl.setVisible(true);
        }
		else
		  leiOP.imprimir(true);
	  }
	  catch (Exception err) {
		Funcoes.mensagemInforma(this,"Não foi possível carregar o leiaute de Ordem de produção!\n"+err.getMessage());
		err.printStackTrace();
	  }
	}
  }
  private boolean[] prefs(Connection con) {
  	boolean[] bRetorno = new boolean[1];
   String sSQL = "SELECT USAREFPROD FROM SGPREFERE1 WHERE CODEMP=? AND CODFILIAL=?";;;
  	PreparedStatement ps = null;
  	ResultSet rs = null;
  	try {
  		bRetorno[0] = false;
  		ps = con.prepareStatement(sSQL);
  		ps.setInt(1,Aplicativo.iCodEmp);
  		ps.setInt(2,ListaCampos.getMasterFilial("SGPREFERE1"));
  		rs = ps.executeQuery();
  		if (rs.next()) {
  			bRetorno[0]=rs.getString("UsaRefProd").trim().equals("S");
  		}
  		if (!con.getAutoCommit())
  			con.commit();
  	}
  	catch (SQLException err) {
  		Funcoes.mensagemErro(this,"Erro ao carregar a tabela PREFERE1!\n"+err.getMessage(),true,con,err);
  	}
  	return bRetorno;
  }
}
