/**
 * @version 14/07/2003 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)FCLFiscal.java <BR>
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
 * Comentários sobre a classe...
 */

package org.freedom.modulos.std;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JButton;

import org.freedom.acao.CarregaEvent;
import org.freedom.acao.CarregaListener;
import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.ImprimeOS;
import org.freedom.componentes.JComboBoxPad;
import org.freedom.componentes.JRadioGroup;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FDados;
import org.freedom.telas.FPrincipal;

public class FCLFiscal extends FDados implements CarregaListener {
  private JTextFieldPad txtCodFisc = new JTextFieldPad(13);
  private JTextFieldPad txtDescFisc = new JTextFieldPad(50);
  private JTextFieldPad txtCodRegra = new JTextFieldPad(5);
  private JTextFieldFK txtDescRegra = new JTextFieldFK();
  private JTextFieldPad txtCodTratTrib = new JTextFieldPad(5);
  private JTextFieldFK txtDescTratTrib = new JTextFieldFK();
  private JTextFieldPad txtCodMens = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldFK txtDescMens = new JTextFieldFK(JTextFieldPad.TP_STRING,10000,0);
  private JTextFieldPad txtAliqFisc = new JTextFieldPad(8);
  private JTextFieldPad txtAliqlFisc = new JTextFieldPad(8);
  private JTextFieldPad txtAliqIPIFisc = new JTextFieldPad(8);
  private JTextFieldPad txtRedFisc = new JTextFieldPad(50);
  private JButton btItClFiscal = new JButton("Exceções");
  private ListaCampos lcRegraFiscal = new ListaCampos(this,"RA");
  private ListaCampos lcTratTrib = new ListaCampos(this,"TT");
  private ListaCampos lcMens = new ListaCampos(this,"ME");
  private JRadioGroup rgTipoFisc = null;
  private Vector vTipoVals = new Vector();
  private Vector vTipoLabs = new Vector();
  private JRadioGroup rgSitPis = null;
  private JRadioGroup rgSitCofins = null;
  private Vector vSitPisVals = new Vector();
  private Vector vSitPisLabs = new Vector();
  private Vector vSitCofinsVals = new Vector();
  private Vector vSitCofinsLabs = new Vector();
  private JComboBoxPad cbOrig = null;
  private Vector vValsOrig = new Vector();
  private Vector vLabsOrig = new Vector();
  private FPrincipal fPrim;
  public FCLFiscal () {
    setTitulo("Cadastro de Classificações Fiscais");
    setAtribos( 0, 0, 420, 470);

    txtCodRegra.setTipo(JTextFieldPad.TP_STRING,5,0);
    txtDescRegra.setTipo(JTextFieldPad.TP_STRING,40,0);    
    lcRegraFiscal.add(new GuardaCampo( txtCodRegra, 7, 100, 80, 20, "CodRegra", "Cód.reg.fisc.", true, false, null, JTextFieldPad.TP_STRING,true),"txtCodRegrax");
    lcRegraFiscal.add(new GuardaCampo( txtDescRegra, 90, 100, 207, 20, "DescRegra", "Descrição da regra fiscal", false, false, null, JTextFieldPad.TP_STRING,false),"txtDescRegrax");
    lcRegraFiscal.montaSql(false, "REGRAFISCAL", "LF");
    lcRegraFiscal.setQueryCommit(false);
    lcRegraFiscal.setReadOnly(true);
    txtCodRegra.setTabelaExterna(lcRegraFiscal);
    
    txtCodTratTrib.setTipo(JTextFieldPad.TP_STRING,2,0);
    txtDescTratTrib.setTipo(JTextFieldPad.TP_STRING,60,0);    

    lcTratTrib.add(new GuardaCampo( txtCodTratTrib, 7, 100, 80, 20, "CodTratTrib", "Cód.t.trib.", true, false, null, JTextFieldPad.TP_STRING,true),"txtCodRegrax");
    lcTratTrib.add(new GuardaCampo( txtDescTratTrib, 90, 100, 207, 20, "DescTratTrib", "Descrição do tratamento tributario", false, false, null, JTextFieldPad.TP_STRING,false),"txtDescRegrax");
    lcTratTrib.montaSql(false, "TRATTRIB", "LF");
    lcTratTrib.setQueryCommit(false);
    lcTratTrib.setReadOnly(true);
    txtCodTratTrib.setTabelaExterna(lcTratTrib);

	lcMens.add(new GuardaCampo( txtCodMens, 7, 100, 80, 20, "CodMens", "Cód.mens.", true, false, null, JTextFieldPad.TP_INTEGER,false),"txtCodRegrax");
	lcMens.add(new GuardaCampo( txtDescMens, 90, 100, 207, 20, "Mens", "Mensagem", false, false, null, JTextFieldPad.TP_STRING,false),"txtDescRegrax");
	lcMens.montaSql(false, "MENSAGEM", "LF");
	lcMens.setQueryCommit(false);
	lcMens.setReadOnly(true);
	txtCodMens.setTabelaExterna(lcMens);

    vTipoLabs.addElement("Isento");
    vTipoLabs.addElement("Subst. Trib.");
    vTipoLabs.addElement("Não inside");
    vTipoLabs.addElement("Trib. Integral");
    vTipoVals.addElement("II");
    vTipoVals.addElement("FF");
    vTipoVals.addElement("NN");
    vTipoVals.addElement("TT");
    rgTipoFisc = new JRadioGroup( 4, 1, vTipoLabs, vTipoVals);

    vSitPisLabs.addElement("Tributado");
    vSitPisLabs.addElement("Isento");
    vSitPisLabs.addElement("Sub. trib.");
    vSitPisVals.addElement("T");
    vSitPisVals.addElement("I");
    vSitPisVals.addElement("S");
    rgSitPis = new JRadioGroup(3,1,vSitPisLabs, vSitPisVals);
    
    vSitCofinsLabs.addElement("Tributado");
    vSitCofinsLabs.addElement("Isento");
    vSitCofinsLabs.addElement("Sub. trib.");
    vSitCofinsVals.addElement("T");
    vSitCofinsVals.addElement("I");
    vSitCofinsVals.addElement("S");
    rgSitCofins = new JRadioGroup(3,1,vSitCofinsLabs, vSitCofinsVals);
    
    vLabsOrig.addElement("Nacional");
    vLabsOrig.addElement("Estrangeira - Importação direta");
    vLabsOrig.addElement("Estrangeira - Adquirida no mercado interno");
    vValsOrig.addElement("0");
    vValsOrig.addElement("1");
    vValsOrig.addElement("2");
    cbOrig = new JComboBoxPad(vLabsOrig,vValsOrig);
    

    adicCampo(txtCodFisc, 7, 20, 94, 20, "CodFisc", "Cód.c.fisc.", JTextFieldPad.TP_STRING, 13, 0, true, false, null, true);
    adicCampo(txtDescFisc, 104, 20, 290, 20, "DescFisc", "Descrição da classificação fiscal", JTextFieldPad.TP_STRING, 50, 0, false, false, null, true);
    adicCampo(txtCodRegra, 7, 60, 94, 20, "CodRegra", "Cód.reg.fisc.", JTextFieldPad.TP_STRING, 5, 0, false, true, txtDescRegra, true);
    adicDescFK(txtDescRegra, 104, 60, 290, 20, "DescRegra", "Descrição da regra fiscal", JTextFieldPad.TP_STRING, 50, 0);
    adicDB(cbOrig, 7, 100, 387, 30, "OrigFisc", "Origem",JTextFieldPad.TP_STRING,true);
    adicCampo(txtCodTratTrib, 7, 150, 94, 20, "CodTratTrib", "Cód.t.trib.", JTextFieldPad.TP_STRING, 2, 0, false, true, txtDescTratTrib, true);
    adicDescFK(txtDescTratTrib, 104, 150, 290, 20, "DescTratTrib", "Descrição da tratamento tributário", JTextFieldPad.TP_STRING, 60, 0);
    adicCampo(txtAliqFisc, 7, 190, 94, 20, "AliqFisc", "Aliq. Fiscal", JTextFieldPad.TP_DECIMAL, 6, 2, false, false, null, false);
    adicCampo(txtAliqlFisc, 104, 190, 94, 20, "AliqlFisc", "Aliq. Liv. Fiscal", JTextFieldPad.TP_DECIMAL, 6, 2, false, false, null, false);
    adicCampo(txtAliqIPIFisc, 201, 190, 94, 20, "AliqIPIFisc", "Aliq. de IPI", JTextFieldPad.TP_DECIMAL, 6, 2, false, false, null, false);
    adicCampo(txtRedFisc, 298, 190, 94, 20, "RedFisc", "Redução Fiscal", JTextFieldPad.TP_DECIMAL, 6, 2, false, false, null, false);
	adicCampo(txtCodMens, 7, 230, 94, 20, "CodMens", "Cód.mens.", JTextFieldPad.TP_INTEGER, 8, 0, false, true, txtDescMens, false);
	adicDescFK(txtDescMens, 104, 230, 290, 20, "Mens", "Mensagem", JTextFieldPad.TP_STRING, 10000, 0);
    adicDB(rgTipoFisc, 7, 270, 120, 100, "TipoFisc", "Situação do ICMS:",JTextFieldPad.TP_STRING,true);
    adicDB(rgSitPis, 130, 270, 120, 80, "SitPisFisc", "Situação do PIS:",JTextFieldPad.TP_STRING,true);
    adicDB(rgSitCofins, 253, 270, 120, 80, "SitCofinsFisc", "Situação do COFINS:",JTextFieldPad.TP_STRING,true);
    rgTipoFisc.setVlrString("TT");
    rgSitPis.setVlrString("T");
    rgSitCofins.setVlrString("T");
    
	adic(btItClFiscal,283,360,90,30);

    setListaCampos(false, "CLFISCAL", "LF");
    lcCampos.setQueryInsert(false);
    
    lcCampos.addCarregaListener(this);
    
    btImp.addActionListener(this);
    btPrevimp.addActionListener(this);
    lcCampos.setQueryInsert(false);    
    
    btItClFiscal.addActionListener(this);    
  }
  public void verifItens() {
  	boolean bAtivo = true;
  	String sSQL = "SELECT COUNT(*) FROM LFITCLFISCAL" +
  		          " WHERE CODEMP=? AND CODFILIAL=? AND CODFISC=?";
    try {   		          
	  PreparedStatement ps = con.prepareStatement(sSQL);
	  ps.setInt(1,Aplicativo.iCodEmp);
	  ps.setInt(2,lcCampos.getCodFilial());
	  ps.setString(3,txtCodFisc.getVlrString());
	  ResultSet rs = ps.executeQuery();
	  if (rs.next() && rs.getInt(1) > 0)
		bAtivo = false;
		
	  cbOrig.setAtivo(bAtivo);
	  txtCodTratTrib.setAtivo(bAtivo);	
	  txtRedFisc.setAtivo(bAtivo);	
	  rgTipoFisc.setAtivo(bAtivo);
	
	  rs.close();
	  ps.close();
    }
	catch(SQLException err) {
		Funcoes.mensagemErro(this,"Erro ao verificar itens!\n"+err.getMessage());
		err.printStackTrace();
    }
	
  }
  public void beforeCarrega(CarregaEvent cevt) { }
  public void afterCarrega(CarregaEvent cevt) {
	if (cevt.getListaCampos() == lcCampos)
		verifItens();
  }
  public void actionPerformed(ActionEvent evt) {
    if (evt.getSource() == btItClFiscal && lcCampos.getStatus() == ListaCampos.LCS_SELECT) {
	  abreItClFiscal();    	
    }
    if (evt.getSource() == btPrevimp) {
        imprimir(true);
    }
    else if (evt.getSource() == btImp) 
      imprimir(false);
	super.actionPerformed(evt);
  }
  
  
  private void imprimir(boolean bVisualizar) {
    ImprimeOS imp = new ImprimeOS("",con);
    int linPag = imp.verifLinPag()-1;
    imp.montaCab();
    imp.setTitulo("Relatório de Classificação fiscal dos produtos");
    DLRClasFiscal dl = new DLRClasFiscal(this);
    String sRets[];
  //  String sOrdem;
    String sCodFiscAnt = "";
	
    dl.setVisible(true);
	if (dl.OK) {
		sRets = dl.getValores();
	//	if (sRets[1].compareTo("C") == 0 )
	  //      sOrdem = "CODFISC";
		//else
		  //sOrdem = "DESCFISC";
		
	}
    else {
	  dl.dispose();
	  return;
	}
	String sSQL = "SELECT F.CODFISC,F.DESCFISC, F.TIPOFISC, F.ALIQFISC, "+
	              "F.REDFISC, F.ALIQIPIFISC, F.CODREGRA, F.ORIGFISC, F.CODTRATTRIB, "+
				  "F.CODMENS, FI.CODFISC, FI.CODITFISC,FI.ORIGFISC, FI.TIPOFISC, FI.REDFISC, "+
				  "FI.CODTRATTRIB, FI.NOUFITFISC, FI.CODFISCCLI, FI.ALIQLFISC,  FI.ALIQFISC,FI.CODMENS, "+
				  "(SELECT TP.DESCFISCCLI FROM LFTIPOFISCCLI TP WHERE TP.CODFISCCLI=FI.CODFISCCLI AND " +
				  " TP.CODEMP=FI.CODEMPFC AND TP.CODFILIAL=FI.CODFILIALFC) FROM LFCLFISCAL F LEFT OUTER JOIN  LFITCLFISCAL FI ON F.CODFISC=FI.CODFISC "+
				  "AND F.CODEMP=FI.CODEMP AND F.CODFILIAL=FI.CODFILIAL ORDER BY F."+
                  (sRets[1].equals("C") ? "CODFISC" : "DESCFISC");
    PreparedStatement ps = null;
    ResultSet rs = null;
    try {
      ps = con.prepareStatement(sSQL);
      rs = ps.executeQuery();
      imp.limpaPags();
      while ( rs.next() ) {
      	
        if (!sRets[0].equals("S") ){
      
          if (imp.pRow()==0) {
            imp.impCab(136);
            imp.say(imp.pRow()+0,0,""+imp.comprimido());
            imp.say(imp.pRow()+0,0,"");
            imp.say(imp.pRow()+0,0,"|");
            imp.say(imp.pRow()+0,3,"Código");
            imp.say(imp.pRow()+0,18,"|");
            imp.say(imp.pRow()+0,20,"Descrição");
            imp.say(imp.pRow()+0,71,"|");
            imp.say(imp.pRow()+0,72,"T.T");
            imp.say(imp.pRow()+0,76,"|");
            imp.say(imp.pRow()+0,78,"ICMS.");
            imp.say(imp.pRow()+0,84,"|");
            imp.say(imp.pRow()+0,85,"RED.");
            imp.say(imp.pRow()+0,90,"|");
            imp.say(imp.pRow()+0,91,"IPI");
            imp.say(imp.pRow()+0,98,"|");
            imp.say(imp.pRow()+0,99,"REGRA");
            imp.say(imp.pRow()+0,105,"|");
            imp.say(imp.pRow()+0,107,"Mens.");
            imp.say(imp.pRow()+0,114,"|");         
            imp.say(imp.pRow()+0,116,"T.F.CLIENTE");
            imp.say(imp.pRow()+0,128,"|");
            imp.say(imp.pRow()+0,130,"EST");    
            imp.say(imp.pRow()+0,136,"|");         
            
            imp.say(imp.pRow()+1,0,""+imp.comprimido());
            imp.say(imp.pRow()+0,0,Funcoes.replicate("-",136));
          }
          imp.say(imp.pRow()+1,0,""+imp.comprimido());
          imp.say(imp.pRow()+0,0,"|");    
          imp.say(imp.pRow()+0,3,rs.getString("CodFisc"));
          imp.say(imp.pRow()+0,18,"|");
          imp.say(imp.pRow()+0,20,rs.getString("DescFisc"));
          imp.say(imp.pRow()+0,71,"|");
          imp.say(imp.pRow()+0,73,rs.getString("OrigFisc"));
          imp.say(imp.pRow()+0,74,rs.getString("CodTratTrib"));
          imp.say(imp.pRow()+0,76,"|");
          imp.say(imp.pRow()+0,78,rs.getString("AliqFisc"));
          imp.say(imp.pRow()+0,84,"|");
          imp.say(imp.pRow()+0,85,rs.getString("RedFisc"));
          imp.say(imp.pRow()+0,90,"|");
          imp.say(imp.pRow()+0,91,rs.getString("AliqIPIFisc"));
          imp.say(imp.pRow()+0,98,"|");
          imp.say(imp.pRow()+0,99,rs.getString("CodRegra"));
          imp.say(imp.pRow()+0,105,"|");
          imp.say(imp.pRow()+0,108,rs.getString("CodMens"));
          imp.say(imp.pRow()+0,114,"|"); 
          //imp.say(imp.pRow()+0,116,rs.getString(22));
          imp.say(imp.pRow()+0,128,"|");
          imp.say(imp.pRow()+0,131,rs.getString("NOUFITFISC"));
          imp.say(imp.pRow()+0,136,"|");
        }
        else {
         
          if (imp.pRow()==0) {
            imp.impCab(136);
            imp.say(imp.pRow()+0,0,""+imp.comprimido());
            imp.say(imp.pRow()+0,0,"");
            imp.say(imp.pRow()+0,0,"|");
            imp.say(imp.pRow()+0,3,"Código");
            imp.say(imp.pRow()+0,18,"|");
            imp.say(imp.pRow()+0,20,"Descrição");
            imp.say(imp.pRow()+0,71,"|");
            imp.say(imp.pRow()+0,72,"T.T");
            imp.say(imp.pRow()+0,76,"|");
            imp.say(imp.pRow()+0,78,"ICMS.");
            imp.say(imp.pRow()+0,84,"|");
            imp.say(imp.pRow()+0,85,"RED.");
            imp.say(imp.pRow()+0,90,"|");
            imp.say(imp.pRow()+0,91,"IPI");
            imp.say(imp.pRow()+0,98,"|");
            imp.say(imp.pRow()+0,99,"REGRA");
            imp.say(imp.pRow()+0,105,"|");
            imp.say(imp.pRow()+0,107,"Mens.");
            imp.say(imp.pRow()+0,114,"|"); 
            imp.say(imp.pRow()+0,116,"T.F.CLIENTE");
            imp.say(imp.pRow()+0,128,"|");
            imp.say(imp.pRow()+0,130,"EST.");
            imp.say(imp.pRow()+0,136,"|");         
            
            imp.say(imp.pRow()+1,0,""+imp.comprimido());
            imp.say(imp.pRow()+0,0,"+"+Funcoes.replicate("-",134)+"+");
          }
          if ( (!sCodFiscAnt.equals("")) && (!sCodFiscAnt.equals(rs.getString("CodFisc")))) {
         	 imp.say(imp.pRow()+1,0,""+imp.comprimido());
         	 imp.say(imp.pRow()+0,0,"|"+Funcoes.replicate("-",134)+"|");
          }
          
          if ( !sCodFiscAnt.equals(rs.getString("CodFisc"))) {
          	imp.say(imp.pRow()+1,0,""+imp.comprimido());
          	imp.say(imp.pRow()+0,0,"|");    
          	imp.say(imp.pRow()+0,3,rs.getString("CodFisc"));
          	imp.say(imp.pRow()+0,18,"|");
          	imp.say(imp.pRow()+0,20,rs.getString("DescFisc"));
          	imp.say(imp.pRow()+0,71,"|");
          	imp.say(imp.pRow()+0,73,rs.getString("OrigFisc"));
          	imp.say(imp.pRow()+0,74,rs.getString("CodTratTrib"));
          	imp.say(imp.pRow()+0,76,"|");
          	imp.say(imp.pRow()+0,78,rs.getString("AliqFisc"));
          	imp.say(imp.pRow()+0,84,"|");
          	imp.say(imp.pRow()+0,85,rs.getString("RedFisc"));
          	imp.say(imp.pRow()+0,90,"|");
          	imp.say(imp.pRow()+0,91,rs.getString("AliqIPIFisc"));
          	imp.say(imp.pRow()+0,98,"|");
          	imp.say(imp.pRow()+0,99,rs.getString("CodRegra"));
          	imp.say(imp.pRow()+0,105,"|");
          	imp.say(imp.pRow()+0,108,rs.getString("CodMens"));
          	imp.say(imp.pRow()+0,114,"|");   
            //imp.say(imp.pRow()+0,116,Funcoes.copy(rs.getString(22),10));
          	imp.say(imp.pRow()+0,128,"|");
          	imp.say(imp.pRow()+0,131,rs.getString("NOUFITFISC"));
          	imp.say(imp.pRow()+0,136,"|");
          }
       
          
          if (rs.getString(11)!=null){
          	  imp.say(imp.pRow()+1,0,""+imp.comprimido());  
          	  imp.say(imp.pRow()+0,0,"|");
          	  imp.say(imp.pRow()+0,3,"Exceção:");
              imp.say(imp.pRow()+0,18,"|");
              imp.say(imp.pRow()+0,71,"|");
              imp.say(imp.pRow()+0,73,rs.getString(13));
              imp.say(imp.pRow()+0,74,rs.getString(16));
              imp.say(imp.pRow()+0,76,"|");
              imp.say(imp.pRow()+0,78,rs.getString(19));
              imp.say(imp.pRow()+0,84,"|");
              imp.say(imp.pRow()+0,85,rs.getString(15));
              imp.say(imp.pRow()+0,90,"|");
              imp.say(imp.pRow()+0,91,rs.getString(20));
              imp.say(imp.pRow()+0,98,"|");
              imp.say(imp.pRow()+0,105,"|");
              imp.say(imp.pRow()+0,108,rs.getString(21));
              imp.say(imp.pRow()+0,114,"|");
              imp.say(imp.pRow()+0,115,Funcoes.copy(rs.getString(22),10));
              imp.say(imp.pRow()+0,128,"|");
              imp.say(imp.pRow()+0,131,rs.getString(17));
              imp.say(imp.pRow()+0,136,"|");
          }
          
          sCodFiscAnt = rs.getString("CodFisc");
        }
        if (imp.pRow()>=linPag) {
        	imp.say(imp.pRow()+1,0,""+imp.comprimido());
        	imp.say(imp.pRow()+0,0,"+"+Funcoes.replicate("-",134)+"+");
        	imp.incPags();
            imp.eject();
        }
      }
         
     
      
      imp.say(imp.pRow()+1,0,""+imp.comprimido());
      imp.say(imp.pRow()+0,0,Funcoes.replicate("=",136));
      imp.eject();
      
      imp.fechaGravacao();
      
//      rs.close();
//      ps.close();
      if (!con.getAutoCommit())
      	con.commit();
      dl.dispose();
    } 
    catch ( SQLException err ) {
		Funcoes.mensagemErro(this,"Erro consulta tabela de classificacao fiscal do produto!"+err.getMessage());      
    }
    
    if (bVisualizar) {
      imp.preview(this);
    }
    else {
      imp.print();
    }
  }
  
  
  
  
  private void abreItClFiscal() {
	  if (!fPrim.temTela("Item CLFISCAL")) {
		FItCLFiscal tela = new FItCLFiscal();
		fPrim.criatela("Item CLFISCAL",tela,con);
		tela.exec(txtCodFisc.getVlrString());
	  } 
  }
  public void setTelaPrim(FPrincipal fP) {
	  fPrim = fP;
  }
  public void execShow(Connection cn) {
    lcRegraFiscal.setConexao(cn);      
	lcTratTrib.setConexao(cn);      
	lcMens.setConexao(cn);      
    super.execShow(cn);
  }
}
