/**
 * @version 06/05/2004 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.pcp <BR>
 * Classe: @(#)FOPFase.java <BR>
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
 * 
 */

package org.freedom.modulos.pcp;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;



import org.freedom.acao.CancelEvent;
import org.freedom.acao.CancelListener;
import org.freedom.acao.CarregaEvent;
import org.freedom.acao.CarregaListener;
import org.freedom.acao.InsertEvent;
import org.freedom.acao.InsertListener;
import org.freedom.acao.PostEvent;
import org.freedom.acao.PostListener;
import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JPanelPad;
import org.freedom.componentes.JTextAreaPad;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FDetalhe;


public class FOPFase extends FDetalhe implements PostListener,CancelListener,InsertListener,ActionListener,CarregaListener {
  private JPanelPad pinCab = new JPanelPad();
  private JPanelPad pinDet = new JPanelPad();
  private JTextAreaPad txaObs = new JTextAreaPad();
  private JTextFieldPad txtCodOP = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldPad txtSeqOP = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldFK txtCodProd = new JTextFieldFK(JTextFieldPad.TP_STRING,50,0);
  private JTextFieldFK txtDescProd = new JTextFieldFK(JTextFieldPad.TP_STRING,50,0);
  private JTextFieldPad txtDtEmit = new JTextFieldPad(JTextFieldPad.TP_DATE,10,0);
  private JTextFieldPad txtDtValid = new JTextFieldPad(JTextFieldPad.TP_DATE,10,0);
  private JTextFieldPad txtDtFabProd = new JTextFieldPad(JTextFieldPad.TP_DATE,10,0);
  private JTextFieldPad txtQtdPrevOP = new JTextFieldPad(JTextFieldPad.TP_DECIMAL,15,3);
  private JTextFieldPad txtQtdFinalOP = new JTextFieldPad(JTextFieldPad.TP_DECIMAL,15,3);
  private JTextFieldPad txtCodFase = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldFK txtDescFase = new JTextFieldFK(JTextFieldPad.TP_STRING,50,0);
  private JTextFieldPad txtJustificqtdprod = new JTextFieldPad(JTextFieldPad.TP_STRING,500,0);
  private JTextFieldPad txtNumSeqOf = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldPad txtCodRec = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
//  private JTextFieldPad txtCodTpRec = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldFK txtDescRec = new JTextFieldFK(JTextFieldPad.TP_STRING,50,0);
  private JTextFieldPad txtTempoOf = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldPad txtDataIniProdFs = new JTextFieldPad(JTextFieldPad.TP_DATE,10,0); 
  private JTextFieldPad txtHIniProdFs = new JTextFieldPad(JTextFieldPad.TP_TIME,8,0);
  private JTextFieldPad txtDataFimProdFs = new JTextFieldPad(JTextFieldPad.TP_DATE,10,0); 
  private JTextFieldPad txtHFimProdFs = new JTextFieldPad(JTextFieldPad.TP_TIME,8,0);
  private JTextFieldPad txtSitFS = new JTextFieldPad(JTextFieldPad.TP_STRING,2,0);
  private ListaCampos lcProd = new ListaCampos(this,"PD");
  private ListaCampos lcFase = new ListaCampos(this,"FS");
  private ListaCampos lcRec = new ListaCampos(this,"RP");
  private int iCodOP;
  private int iSeqOP;
  private boolean bExecuta = false;
  public FOPFase(int iCodOP,int iSeqOP,boolean bExecuta) {
    setTitulo("Fases da OP");
    if(bExecuta){
        setAtribos( 70, 40, 630, 470);
        setAltCab(130);
        setAltDet(170);
    }        
    else {
        setAtribos( 70, 40, 630, 470);
        setAltCab(130);
        setAltDet(100);
    }

    
       
    this.iCodOP = iCodOP;
    this.iSeqOP = iSeqOP;
    this.bExecuta = bExecuta;
    
    txtCodOP.setAtivo(false);
    txtCodProd.setAtivo(false);
    txtDtEmit.setAtivo(false);
    txtDtValid.setAtivo(false);
    txtQtdPrevOP.setAtivo(false);
    txtQtdFinalOP.setAtivo(false);
    txtTempoOf.setAtivo(false);
    txtSeqOP.setAtivo(false);
    
    if(bExecuta) {
    	txtCodFase.setAtivo(false);
		txtNumSeqOf.setAtivo(false);
		txtCodRec.setAtivo(false);
		txtTempoOf.setAtivo(false);
    }
    
    pinCab = new JPanelPad(500,90);
    setListaCampos(lcCampos);
    setPainel( pinCab, pnCliCab);
    
    lcProd.setUsaME(false);
    lcProd.add(new GuardaCampo( txtCodProd, "CodProd", "Cód.prod.", ListaCampos.DB_PK, true));
    lcProd.add(new GuardaCampo( txtDescProd, "DescProd", "Descrição do produto", ListaCampos.DB_SI, false));
    lcProd.setWhereAdic("TIPOPROD='F'");
    lcProd.montaSql(false, "PRODUTO", "EQ");
    lcProd.setQueryCommit(false);
    lcProd.setReadOnly(true);
    txtCodProd.setTabelaExterna(lcProd);
    txtDescProd.setListaCampos(lcProd);
    
    adicCampo(txtCodOP, 7, 20, 80, 20,"CodOP","Nº.OP", ListaCampos.DB_PK, true);
    adicCampo(txtSeqOP, 90, 20, 60, 20,"SeqOP","Seq.OP", ListaCampos.DB_PK, true);
    adicCampo(txtCodProd, 153, 20, 77, 20,"CodProd","Cód.prod.", ListaCampos.DB_FK, txtDescProd, true);
    adicDescFK(txtDescProd, 233, 20, 147, 20, "DescProd", "Descrição do produto");
    adicCampo(txtQtdPrevOP, 383, 20, 87, 20,"QtdPrevProdOP","Qtd.prevista", ListaCampos.DB_SI, true);
    adicCampo(txtQtdFinalOP, 473, 20, 100, 20,"QtdFinalProdOP","Qtd.produzida", ListaCampos.DB_SI, true);
    adicCampo(txtDtEmit, 7, 60, 100, 20,"DtEmitOP","Emissão", ListaCampos.DB_SI, true);
    adicCampo(txtDtValid, 110, 60, 100, 20,"DtValidPDOP","Valid.prod.", ListaCampos.DB_SI, true);
    adicCampoInvisivel(txtJustificqtdprod,"JUSTFICQTDPROD","Justificativa",ListaCampos.DB_SI,false);
    adicCampoInvisivel(txtDtFabProd,"dtfabrop","Dt.Fabric.",ListaCampos.DB_SI, true);
    
    setListaCampos( false, "OP", "PP");
    lcCampos.setQueryInsert(false);
        
    lcFase.add(new GuardaCampo( txtCodFase,"CodFase", "Cód.fase", ListaCampos.DB_PK, true));
    lcFase.add(new GuardaCampo( txtDescFase, "DescFase", "Descrição da fase", ListaCampos.DB_SI, false));
    lcFase.montaSql(false, "FASE", "PP");
    lcFase.setQueryCommit(false);
    lcFase.setReadOnly(true);
    txtCodFase.setTabelaExterna(lcFase);
    txtDescFase.setListaCampos(lcFase);
    
    lcRec.add(new GuardaCampo(txtCodRec,"CodRecP", "Cód.rec.", ListaCampos.DB_PK, true));
    lcRec.add(new GuardaCampo(txtDescRec, "DescRecP", "Descrição do recurso de produção", ListaCampos.DB_SI, false));
//    lcRec.add(new GuardaCampo(txtCodTpRec,"CodTpRec","Cód.Tp.Rec.",ListaCampos.DB_FK,false));
    lcRec.montaSql(false, "RECURSO", "PP");
    lcRec.setQueryCommit(false);
    lcRec.setReadOnly(true);
    txtCodRec.setTabelaExterna(lcRec);
    txtDescRec.setListaCampos(lcRec);
    
    pinDet = new JPanelPad(590,180);
    setPainel( pinDet, pnDet);
    setListaCampos(lcDet);
    setNavegador(navRod);
    adicCampo(txtNumSeqOf, 7, 20, 40, 20,"SeqOf","Item",ListaCampos.DB_PK,true);
    adicCampo(txtCodFase, 50, 20, 77, 20,"CodFase","Cód.fase", ListaCampos.DB_FK, txtDescFase, true);
    adicDescFK(txtDescFase, 130, 20, 227, 20,"DescFase", "Descrição da fase");
    adicCampo(txtTempoOf, 360, 20, 100, 20,"TempoOf","Tempo (Seg.)",ListaCampos.DB_SI, false);
    adicCampo(txtCodRec, 7, 60, 60, 20,"CodRecP","Cód.rec.", ListaCampos.DB_FK, txtDescRec, false);
    adicDescFK(txtDescRec, 70, 60, 200, 20, "DescRecP", "Descrição do recurso");

    if (bExecuta){
	    adicCampo(txtDataIniProdFs, 273, 60, 80, 20,"DataIniProdFs","Data ínicial", ListaCampos.DB_SI, false);
	    adicCampo(txtHIniProdFs, 356, 60, 80, 20,"HIniProdFs","Hora ínicial", ListaCampos.DB_SI, false);
	    adicCampo(txtDataFimProdFs, 439, 60, 80, 20,"DataFimProdFs","Data final", ListaCampos.DB_SI, false);
	    adicCampo(txtHFimProdFs, 522, 60, 80, 20,"HFimProdFs","Hora final", ListaCampos.DB_SI, false);
	    adicDBLiv(txaObs, 7, 100, 591, 52,"ObsFS", "Observações",false);
    }
    
    
    adicCampoInvisivel(txtSitFS,"SITFS", "Situação da fase", ListaCampos.DB_SI, false);
    setListaCampos( true, "OPFASE", "PP");
    lcDet.setQueryInsert(true);
    montaTab();
    
    lcCampos.setReadOnly(true);

    tab.setTamColuna(50,0);
    tab.setTamColuna(50,1);
    tab.setTamColuna(200,2);
    tab.setTamColuna(100,3);
    tab.setTamColuna(50,4);
    tab.setTamColuna(200,5);
    
    lcCampos.addCarregaListener(this);
    lcDet.addCarregaListener(this);
    lcFase.addCarregaListener(this);
    lcProd.addCarregaListener(this);
    lcRec.addCarregaListener(this);
    lcDet.addPostListener(this);
     
  }
  public void beforeCarrega(CarregaEvent cevt) { }
  public void afterCarrega(CarregaEvent cevt) {
  	if (cevt.getListaCampos() == lcDet) {
	  	if (!(txtDataIniProdFs.getVlrString().length() > 0) || (txtDataIniProdFs.getVlrString() == null)) {
	  		txtDataIniProdFs.setVlrDate(txtDtFabProd.getVlrDate());
	  		txtHIniProdFs.setVlrString(Funcoes.getTimeString(new Date()));
	  		txtDataFimProdFs.setVlrDate(new Date());
	  		txtHFimProdFs.setVlrString(Funcoes.getTimeString(new Date()));
	  	} 
	
	  	if (txtSitFS.getVlrString().equals("FN")){
	  		txtDataIniProdFs.setAtivo(false);
	  		txtHIniProdFs.setAtivo(false);
	  		txtDataFimProdFs.setAtivo(false);
	  		txtHFimProdFs.setAtivo(false);
	  		txaObs.setEnabled(false);    	
	  	}
	  	else {
	  		txtDataIniProdFs.setAtivo(true);
	  		txtHIniProdFs.setAtivo(true);
	  		txtDataFimProdFs.setAtivo(true);
	  		txtHFimProdFs.setAtivo(true);
	  		txaObs.setEnabled(true);    		  		
	  	}
	  	//lcRec.setWhereAdic("CODTIPOREC="+txtCodTpRec.getVlrString());
  	}
  }

  public boolean bFinalizaProcesso(){
  	boolean bRet = false;
  	String sSQL = "select ef.finalizaop from ppestrufase ef, ppopfase pf, ppop op where "+
  				  "ef.codemp = pf.codemp and ef.codfilial = pf.codfilial and "+
  				  "ef.codempfs = pf.codempfs and ef.codfilialfs = pf.codfilialfs and ef.codfase = pf.codfase "+
			  	  "and pf.codemp = op.codemp and pf.codfilial = op.codfilial and pf.codop = op.codop and pf.seqof = ? "+
  				  "and op.codemp=? and op.codfilial=? and op.codop=?";
  	
	try {
		PreparedStatement ps = con.prepareStatement(sSQL);
		ps.setInt(1,txtNumSeqOf.getVlrInteger().intValue()); 
		ps.setInt(2,Aplicativo.iCodEmp);
		ps.setInt(3,ListaCampos.getMasterFilial("PPOP"));
		ps.setInt(4,txtCodOP.getVlrInteger().intValue());		
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			String sFinaliza = rs.getString(1);
			if (sFinaliza != null) {
				if(sFinaliza.equals("S"))
					bRet = true;
			}
		}
		rs.close();
		ps.close();
	} catch (SQLException err) {
		Funcoes.mensagemErro(this, "Erro ao verificar se fase finaliza processo!\n",true,con,err);
	}
  	return bRet;
  }
  
  public void beforePost(PostEvent pevt) {
  	if(pevt.getListaCampos()==lcDet){
	  	if(bFinalizaProcesso() && (txtSitFS.getVlrString().equals("PE"))){
	  		DLFinalizaOP dl = new DLFinalizaOP(this,txtQtdPrevOP.getVlrString());
	  		dl.show();
	  		if (!dl.OK) {
	  			pevt.cancela(); 
	  		}
	  		else {
	  			txtQtdFinalOP.setVlrDouble(new Double(dl.getValor()));
	  			txtJustificqtdprod.setVlrString(dl.getObs());
	  			atualizaOP();
	  		}
			dl.dispose();
	  	}
  	}
  }

  public void atualizaOP(){
  	String sSQL = "UPDATE PPOP SET QtdFinalProdOP=?, JUSTFICQTDPROD=? WHERE CodOP=? AND CODEMP=? AND CODFILIAL=?";
  	try {
  	
	  	PreparedStatement ps = con.prepareStatement(sSQL);
	    ps.setDouble(1,txtQtdFinalOP.getVlrDouble().doubleValue());
	    ps.setString(2,txtJustificqtdprod.getVlrString());
	    ps.setInt(3,txtCodOP.getVlrInteger().intValue());
	    ps.setInt(4,Aplicativo.iCodEmp);
		ps.setInt(5,ListaCampos.getMasterFilial("PPOP"));
	    ps.executeUpdate();
	  	
  	}
  	catch(Exception err) {
  		err.printStackTrace();
		Funcoes.mensagemErro(this, "Erro ao atualizar quantidade produzida na OP.!\n",true,con,err);  		
  	}
  }
  public void afterPost(PostEvent pevt) { }
    
  public void beforeCancel(CancelEvent cevt) {}
  
  public void afterInsert(InsertEvent ievt) { }
  
  public void beforeInsert(InsertEvent ievt) { }
  
  public void afterCancel(CancelEvent cevt) { }
  
  public void setConexao(Connection cn) {
    super.setConexao(cn);
    lcProd.setConexao(cn);
    lcFase.setConexao(cn);
    lcRec.setConexao(cn);
    txtCodOP.setVlrInteger(new Integer(iCodOP));
    txtSeqOP.setVlrInteger(new Integer(iSeqOP));
    lcCampos.carregaDados();  
  }        
}
