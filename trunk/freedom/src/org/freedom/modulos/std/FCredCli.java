/**
 * @version 21/11/2003 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)FCredCli.java <BR>
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
 * Tela ficha cadastral e crédito de cliente.
 * 
 */

package org.freedom.modulos.std;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import org.freedom.acao.CarregaEvent;
import org.freedom.acao.CarregaListener;
import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.Navegador;
import org.freedom.componentes.Painel;
import org.freedom.componentes.Tabela;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FTabDados;

public class FCredCli extends FTabDados	implements ActionListener, CarregaListener {
  private Painel pinGeral = new Painel(600, 200);
  private JPanel pnFicha = new JPanel(new BorderLayout());
  private Painel pinFicha = new Painel(600, 200);
  private Painel pinRodFicha = new Painel(600,29);

  private JPanel pnCheque = new JPanel(new BorderLayout());
  private Painel pinCheque = new Painel(600, 200);
  private Painel pinRodCheque = new Painel(600,29);

  private JTextFieldPad txtCodCli = new JTextFieldPad(JTextFieldPad.TP_INTEGER,5,0);
  private JTextFieldFK txtDataCli = new JTextFieldFK(JTextFieldPad.TP_DATE,10,0);
  private JTextFieldPad txtCodTipoCli = new JTextFieldPad(JTextFieldPad.TP_INTEGER,5,0);
  private JTextFieldFK txtDescTipoCli = new JTextFieldFK(JTextFieldPad.TP_STRING,50,0);
  private JTextFieldPad txtCodTpCred = new JTextFieldPad(JTextFieldPad.TP_INTEGER,5,0);
  private JTextFieldFK txtDescTpCred = new JTextFieldFK(JTextFieldPad.TP_STRING,50,0);
  private JTextFieldFK txtVlrTpCred = new JTextFieldFK(JTextFieldPad.TP_DECIMAL,15,2);
  private JTextFieldPad txtDtIniTr = new JTextFieldPad(JTextFieldPad.TP_DATE,10,0);
  private JTextFieldPad txtDtVencto = new JTextFieldPad(JTextFieldPad.TP_DATE,10,0);
  private JTextFieldPad txtRazCli = new JTextFieldPad(JTextFieldPad.TP_STRING, 50, 0);
  private JTextFieldPad txtNomeCli = new JTextFieldPad(JTextFieldPad.TP_STRING, 40, 0);
  private JTextFieldPad txtCnpjCli = new JTextFieldPad(JTextFieldPad.TP_STRING, 14, 0);
  private JTextFieldPad txtInscCli = new JTextFieldPad(JTextFieldPad.TP_STRING, 15, 0);
  private JTextFieldPad txtCpfCli = new JTextFieldPad(JTextFieldPad.TP_STRING, 11, 0);
  private JTextFieldPad txtRgCli = new JTextFieldPad(JTextFieldPad.TP_STRING, 10, 0);
  private JTextFieldPad txtEndCli = new JTextFieldPad(JTextFieldPad.TP_STRING, 50, 0); 
  private JTextFieldPad txtNumCli = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);
  private JTextFieldPad txtComplCli = new JTextFieldPad(JTextFieldPad.TP_STRING, 20, 0);
  private JTextFieldPad txtBairCli = new JTextFieldPad(JTextFieldPad.TP_STRING, 30, 0);
  private JTextFieldPad txtCidCli = new JTextFieldPad(JTextFieldPad.TP_STRING, 30, 0);
  private JTextFieldPad txtUFCli = new JTextFieldPad(JTextFieldPad.TP_STRING, 2, 0);
  private JTextFieldPad txtCepCli = new JTextFieldPad(JTextFieldPad.TP_STRING, 8, 0);
  private JTextFieldPad txtFoneCli = new JTextFieldPad(JTextFieldPad.TP_STRING, 12, 0);
  private JTextFieldPad txtRamalCli = new JTextFieldPad(JTextFieldPad.TP_STRING, 8, 0);
  private JTextFieldPad txtFaxCli = new JTextFieldPad(JTextFieldPad.TP_STRING, 8, 0);
  private JTextFieldPad txtCelCli = new JTextFieldPad(JTextFieldPad.TP_STRING, 8, 0);
  private JTextFieldPad txtNatCli = new JTextFieldPad(JTextFieldPad.TP_STRING, 30, 0);
  private JTextFieldPad txtUFNatCli = new JTextFieldPad(JTextFieldPad.TP_STRING, 2, 0);
  private JTextFieldPad txtTempoResCli = new JTextFieldPad(JTextFieldPad.TP_STRING, 20, 0);
  private JTextFieldPad txtDDDCli = new JTextFieldPad(JTextFieldPad.TP_STRING, 4, 0);
  private JTextFieldPad txtDDDFaxCli = new JTextFieldPad(JTextFieldPad.TP_STRING, 4, 0);
  private JTextFieldPad txtDDDCelCli = new JTextFieldPad(JTextFieldPad.TP_STRING, 4, 0);
  private JTextFieldPad txtPaiCli = new JTextFieldPad(JTextFieldPad.TP_STRING, 50, 0);
  private JTextFieldPad txtRgPaiCli = new JTextFieldPad(JTextFieldPad.TP_STRING, 10, 0);
  private JTextFieldPad txtSSPPaiCli = new JTextFieldPad(JTextFieldPad.TP_STRING, 10, 0);
  private JTextFieldPad txtMaeCli = new JTextFieldPad(JTextFieldPad.TP_STRING, 50, 0);
  private JTextFieldPad txtRgMaeCli = new JTextFieldPad(JTextFieldPad.TP_STRING, 10, 0);
  private JTextFieldPad txtSSPMaeCli = new JTextFieldPad(JTextFieldPad.TP_STRING, 10, 0);
  private JTextFieldPad txtEmpTrabCli = new JTextFieldPad(JTextFieldPad.TP_STRING, 10, 0);
  private Tabela tabFicha = new Tabela();
  private JLabel lbNatCli = null;
  private JLabel lbUfNatCli = null;
  private JLabel lbTempoResCli = null;
  private JLabel lbPaiCli = null;
  private JLabel lbMaeCli = null;
  private JLabel lbRgPaiCli = null;
  private JLabel lbSSPPaiCli = null;
  private JLabel lbSSPMaeCli = null;
  private JLabel lbRgMaeCli = null;
   
  private ListaCampos lcTipoCred = new ListaCampos(this,"TR");
  private ListaCampos lcTipoCli = new ListaCampos(this,"TI");
  private ListaCampos lcFicha = new ListaCampos(this,"CC");
  private Navegador navFicha = new Navegador(false);
  private JTabbedPane tbp = null;
  
  private boolean bFisTipoCli = false;
  private boolean bJurTipoCli = false;
  private boolean bCheqTipoCli = false;
  private boolean bFilTipoCli = false;
  private boolean bLocTrabTipoCli = false;
  private boolean bRefComlTipoCli = false;
  private boolean bBancTipoCli = false;
  private boolean bRefPesTipoCli = false;
  private boolean bConjTipoCli = false;
  private boolean bVeicTipoCli = false;
  private boolean bImovTipoCli = false;
  private boolean bTerraTipoCli = false;
  private boolean bPesAutTipoCli = false;
  private boolean bAvalTipoCli = false;
  private boolean bSocioTipoCli = false;

  
  public FCredCli() {
    setTitulo("Ficha cadastral/Crédito por cliente");
    setAtribos(50, 10, 600, 520);

    lcFicha.setMaster(lcCampos);
    lcCampos.adicDetalhe(lcFicha);
    lcFicha.setTabela(tabFicha);
   
    navFicha.btNovo.setVisible(false);
	navFicha.btExcluir.setVisible(false);
    
    lcTipoCli.add(new GuardaCampo( txtCodTipoCli, "CodTipoCli", "Cód.tp.cli.", ListaCampos.DB_PK, true));
    lcTipoCli.add(new GuardaCampo( txtDescTipoCli, "DescTipoCli", "Descrição do tipo de cliente", ListaCampos.DB_SI, false));
    lcTipoCli.montaSql(false, "TIPOCLI", "VD");    
    lcTipoCli.setQueryCommit(false);
    lcTipoCli.setReadOnly(true);
    txtCodTipoCli.setTabelaExterna(lcTipoCli);

    lcTipoCred.add(new GuardaCampo( txtCodTpCred, "CodTpCred", "Cód.tp.cred.", ListaCampos.DB_PK, true));
    lcTipoCred.add(new GuardaCampo( txtDescTpCred, "DescTpCred", "Descrição do tipo de crédito", ListaCampos.DB_SI, false));
    lcTipoCred.add(new GuardaCampo( txtVlrTpCred, "VlrTpCred", "Valor", ListaCampos.DB_SI, false));
    lcTipoCred.montaSql(false, "TIPOCRED", "FN");    
    lcTipoCred.setQueryCommit(false);
    lcTipoCred.setReadOnly(true);
    txtCodTpCred.setTabelaExterna(lcTipoCred);

	setPainel(pinGeral);
	
	lcCampos.addCarregaListener(this);
	
	adicTab("Crédito", pinGeral);
    adicCampo(txtCodCli, 7, 20, 70, 20,"CodCli","Cód.cli.", ListaCampos.DB_PK, true);
    adicCampo(txtRazCli, 80, 20, 257, 20,"RazCli","Razão social do cliente", ListaCampos.DB_SI, false);
	adicCampoInvisivel(txtCodTipoCli, "CodTipoCli","Cód.tp.cli", ListaCampos.DB_FK, txtDescTipoCli,false);
    adicDescFK(txtDescTipoCli, 340, 20, 130, 20, "DescTipoCli", "Desc. tipo de cliente");
    adicCampo(txtDataCli, 473, 20, 95, 20,"DataCli","Cadastro", ListaCampos.DB_SI, false);

    adicCampo(txtCodTpCred, 7, 60, 70, 20,"CodTpCred","Cód.tp.cred", ListaCampos.DB_FK, txtDescTpCred, true);
    adicDescFK(txtDescTpCred, 80, 60, 212, 20, "DescTpCred", "Descrição do crédito");
    adicDescFK(txtVlrTpCred, 295, 60, 107, 20, "VlrTpCred", "Valor");
    
    adicCampo(txtDtIniTr, 405 , 60, 80, 20,"DtIniTr","Dt.ab.créd.", ListaCampos.DB_SI, true);
    adicCampo(txtDtVencto, 488, 60, 80, 20,"DtVenctoTr","Vencimento", ListaCampos.DB_SI,true);
	
  	adicCampo(txtEndCli, 7, 100, 330, 20, "EndCli", "Endereço", ListaCampos.DB_SI, false);
  	adicCampo(txtNumCli, 340, 100, 77, 20, "NumCli", "Num.", ListaCampos.DB_SI, false);
  	adicCampo(txtComplCli, 420, 100, 149, 20, "ComplCli", "Compl.", ListaCampos.DB_SI, false);

  	adicCampo(txtBairCli, 7, 140, 210, 20, "BairCli", "Bairro", ListaCampos.DB_SI, false);
  	adicCampo(txtCidCli, 220, 140, 210, 20, "CidCli", "Cidade", ListaCampos.DB_SI, false);
  	adicCampo(txtCepCli, 433, 140, 80, 20, "CepCli", "Cep", ListaCampos.DB_SI, false);
  	adicCampo(txtUFCli, 516, 140, 52, 20, "UFCli", "UF", ListaCampos.DB_SI, false);
  	
  	adicCampo(txtDDDCli, 7, 180, 40, 20, "DDDCli", "DDD", ListaCampos.DB_SI, false);
  	adicCampo(txtFoneCli, 50, 180, 97, 20, "FoneCli", "Telefone", ListaCampos.DB_SI, false);
  	adicCampo(txtRamalCli, 150, 180, 47, 20, "RamalCli", "Ramal", ListaCampos.DB_SI, false); 	

  	adicCampo(txtDDDFaxCli, 200, 180, 37, 20, "DDDFaxCli", "DDD", ListaCampos.DB_SI, false);
  	adicCampo(txtFaxCli, 240, 180, 97, 20, "FaxCli", "Fax", ListaCampos.DB_SI, false);

  	adicCampo(txtDDDCelCli, 340, 180,37, 20, "DDDCelCli", "DDD", ListaCampos.DB_SI, false);
  	adicCampo(txtCelCli, 380, 180, 100, 20, "CelCli", "Celular",ListaCampos.DB_SI, false);
		 	
  	lbNatCli = adicCampo(txtNatCli, 7, 220, 200, 20, "NatCli", "Naturalidade",ListaCampos.DB_SI, false);
  	lbUfNatCli = adicCampo(txtUFNatCli, 210, 220, 52, 20, "UfNatCli", "Uf Natur.",ListaCampos.DB_SI, false);
  	lbTempoResCli = adicCampo(txtTempoResCli, 265, 220, 160, 20, "TempoResCli", "Tempo de residência.",ListaCampos.DB_SI, false);
	
	setListaCampos( true, "CLIENTE", "VD");
	lcCampos.setPodeIns(false);
	lcCampos.setPodeExc(false);
    lcCampos.setQueryInsert(false);
    
	setPainel(pinFicha);
	tbp = adicTab("Adicionais", pnFicha);
    
	setListaCampos(lcFicha);
    setNavegador(navFicha);

    pnFicha.add(pinRodFicha, BorderLayout.SOUTH);
    pnFicha.add(pinFicha, BorderLayout.CENTER);
 
    pinRodFicha.adic(navFicha,0,0,150,25);
   
    lbPaiCli = adicCampo(txtPaiCli, 7, 20, 315, 20, "PaiCli", "Nome do pai", ListaCampos.DB_SI, false);
    lbRgPaiCli = adicCampo(txtRgPaiCli, 325, 20, 130, 20, "RgPaiCli", "Rg", ListaCampos.DB_SI, false);
    lbSSPPaiCli = adicCampo(txtSSPPaiCli, 458, 20, 100, 20, "SSPPaiCli", "SSP", ListaCampos.DB_SI, false);
    
    lbMaeCli = adicCampo(txtMaeCli, 7, 60, 315, 20, "MaeCli", "Nome da mãe", ListaCampos.DB_SI, false);
    lbRgMaeCli = adicCampo(txtRgMaeCli, 325, 60, 130, 20, "RgMaeCli", "Rg", ListaCampos.DB_SI, false);    
    lbSSPMaeCli = adicCampo(txtSSPMaeCli, 458, 60, 100, 20, "SSPMaeCli", "SSP", ListaCampos.DB_SI, false);    

  
    setListaCampos( false, "CLICOMPL", "VD");

    lcFicha.setQueryInsert(false);
	lcFicha.setQueryCommit(false);
    lcFicha.montaTab();    
/*
    pnCheque.add(pinRodCheque, BorderLayout.SOUTH);
    pnCheque.add(pinCheque, BorderLayout.CENTER);
 
    pinRodFicha.adic(navFicha,0,0,150,25);
*/
    
    
    
    
    habCampos();
	
    
  }
  
  private void habCampos() {

  	//Pessoa Física
  	txtNatCli.setVisible(bFisTipoCli);
  	txtUFNatCli.setVisible(bFisTipoCli);
  	txtTempoResCli.setVisible(bFisTipoCli);
  	lbNatCli.setVisible(bFisTipoCli);
  	lbUfNatCli.setVisible(bFisTipoCli);
  	lbTempoResCli.setVisible(bFisTipoCli);

    //Filiação
  
    lbPaiCli.setVisible(bFilTipoCli);
    lbMaeCli.setVisible(bFilTipoCli);
    lbRgPaiCli.setVisible(bFilTipoCli);
    lbSSPPaiCli.setVisible(bFilTipoCli);
    lbSSPMaeCli.setVisible(bFilTipoCli);
    lbRgMaeCli.setVisible(bFilTipoCli);
    txtPaiCli.setVisible(bFilTipoCli);
    txtMaeCli.setVisible(bFilTipoCli);
    txtRgPaiCli.setVisible(bFilTipoCli);
    txtRgMaeCli.setVisible(bFilTipoCli);
    txtSSPPaiCli.setVisible(bFilTipoCli);
    txtSSPMaeCli.setVisible(bFilTipoCli);
    
    
    
  }
  
  
  
  public void afterCarrega(CarregaEvent cevt) {
  
  	if (txtDtIniTr.getVlrString().equals("")){
  		txtDtIniTr.setVlrDate(new Date());
  	}
  	buscaAdicionais();
  }
  
  private void buscaAdicionais(){
	String sSQL = null;
  	ResultSet rs = null;
  	PreparedStatement ps = null;
  	boolean bReturn = false;
  	
	try {
		
		sSQL = "SELECT TC.FISTIPOCLI,TC.JURTIPOCLI,TC.CHEQTIPOCLI,TC.FILTIPOCLI,TC.LOCTRABTIPOCLI,TC.REFCOMLTIPOCLI,TC.BANCTIPOCLI," +
				"TC.REFPESTIPOCLI,TC.CONJTIPOCLI,TC.VEICTIPOCLI,TC.IMOVTIPOCLI,TC.TERRATIPOCLI,TC.PESAUTCPTIPOCLI,TC.AVALTIPOCLI," +
				"TC.SOCIOTIPOCLI FROM VDTIPOCLI TC,VDCLIENTE CLI WHERE TC.CODEMP=? AND TC.CODFILIAL=? AND CLI.CODCLI=? AND " +
				"CLI.CODEMPTC=TC.CODEMP AND CLI.CODFILIALTC=TC.CODFILIAL AND TC.CODTIPOCLI=CLI.CODTIPOCLI";

		System.out.println(sSQL);
		
		ps = con.prepareStatement(sSQL);
		ps.setInt(1,Aplicativo.iCodEmp);
		ps.setInt(2,ListaCampos.getMasterFilial("VDCLIENTE"));
		ps.setInt(3,txtCodCli.getVlrInteger().intValue());

		rs = ps.executeQuery();
		if (rs.next()) {
			bFisTipoCli = rs.getString(1).equals("S")?true:false;
			bJurTipoCli = rs.getString(2).equals("S")?true:false;
			bCheqTipoCli = rs.getString(3).equals("S")?true:false;
			bFilTipoCli = rs.getString(4).equals("S")?true:false;
			bLocTrabTipoCli = rs.getString(5).equals("S")?true:false;
			bRefComlTipoCli = rs.getString(6).equals("S")?true:false;
			bBancTipoCli = rs.getString(7).equals("S")?true:false;
			bRefPesTipoCli = rs.getString(8).equals("S")?true:false;
			bConjTipoCli = rs.getString(9).equals("S")?true:false;
			bVeicTipoCli = rs.getString(10).equals("S")?true:false;
			bImovTipoCli = rs.getString(11).equals("S")?true:false;
			bTerraTipoCli = rs.getString(12).equals("S")?true:false;
			bPesAutTipoCli = rs.getString(13).equals("S")?true:false;
			bAvalTipoCli = rs.getString(14).equals("S")?true:false;
			bSocioTipoCli = rs.getString(15).equals("S")?true:false;
		}
		rs.close();
		ps.close();
		if (!con.getAutoCommit())
			con.commit();
	}
	catch (SQLException e) {
		Funcoes.mensagemErro(this,"Não foi possível carregar pessoa cliente!\n"+e.getMessage());
	}
	finally {
		rs = null;
		ps = null;
		sSQL = null;
	}  	
    habCampos();
  }
  
  public void beforeCarrega(CarregaEvent cevt) {}
  
  public void execShow(Connection cn) {
    lcTipoCli.setConexao(cn);
    lcTipoCred.setConexao(cn);
    super.execShow(cn);
  }        
}
