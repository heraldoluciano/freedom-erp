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
import org.freedom.telas.FDetalhe;


public class FOPFase extends FDetalhe implements PostListener,CancelListener,InsertListener,ActionListener,CarregaListener {
  private JPanelPad pinCab = new JPanelPad();
  private JPanelPad pinDet = new JPanelPad();
  private JTextAreaPad txaObs = new JTextAreaPad();
  private JTextFieldPad txtCodOP = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldFK txtCodProd = new JTextFieldFK(JTextFieldPad.TP_STRING,50,0);
  private JTextFieldFK txtDescProd = new JTextFieldFK(JTextFieldPad.TP_STRING,50,0);
  private JTextFieldPad txtDtEmit = new JTextFieldPad(JTextFieldPad.TP_DATE,10,0);
  private JTextFieldPad txtDtValid = new JTextFieldPad(JTextFieldPad.TP_DATE,10,0);
  private JTextFieldPad txtDtFabProd = new JTextFieldPad(JTextFieldPad.TP_DATE,10,0);
  private JTextFieldPad txtQtdOP = new JTextFieldPad(JTextFieldPad.TP_DECIMAL,15,3);
  private JTextFieldPad txtCodFase = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldFK txtDescFase = new JTextFieldFK(JTextFieldPad.TP_STRING,50,0);
  private JTextFieldPad txtNumSeqOf = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldPad txtCodRec = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldFK txtDescRec = new JTextFieldFK(JTextFieldPad.TP_STRING,50,0);
  private JTextFieldPad txtTempoOf = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldPad txtDataIniProdFs = new JTextFieldPad(JTextFieldPad.TP_DATE,10,0); 
  private JTextFieldPad txtHIniProdFs = new JTextFieldPad(JTextFieldPad.TP_TIME,10,0);
  private JTextFieldPad txtDataFimProdFs = new JTextFieldPad(JTextFieldPad.TP_DATE,10,0); 
  private JTextFieldPad txtHFimProdFs = new JTextFieldPad(JTextFieldPad.TP_TIME,10,0);
  private JTextFieldPad txtSitFS = new JTextFieldPad(JTextFieldPad.TP_STRING,1,0);
  private ListaCampos lcProd = new ListaCampos(this,"PD");
  private ListaCampos lcFase = new ListaCampos(this,"FS");
  private ListaCampos lcRec = new ListaCampos(this,"RP");
  private int iCodOP;
  private boolean bExecuta = false;
  public FOPFase(int iCodOP,boolean bExecuta) {
    setTitulo("Fases da OP");
    setAtribos( 70, 40, 630, 470);
    setAltCab(130);
    
    this.iCodOP = iCodOP;
    this.bExecuta = bExecuta;
    
    txtCodOP.setAtivo(false);
    txtCodProd.setAtivo(false);
    txtDtEmit.setAtivo(false);
    txtDtValid.setAtivo(false);
    txtQtdOP.setAtivo(false);
    
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
    
    adicCampo(txtCodOP, 7, 20, 80, 20,"CodOP","Nº OP", ListaCampos.DB_PK, true);
    adicCampo(txtCodProd, 90, 20, 77, 20,"CodProd","Cód.prod.", ListaCampos.DB_FK, txtDescProd, true);
    adicDescFK(txtDescProd, 170, 20, 197, 20, "DescProd", "Descrição do produto");
    adicCampo(txtQtdOP, 370, 20, 100, 20,"QtdPrevProdOP","Quantidade", ListaCampos.DB_SI, true);
    adicCampo(txtDtEmit, 7, 60, 100, 20,"DtEmitOP","Emissão", ListaCampos.DB_SI, true);
    adicCampo(txtDtValid, 110, 60, 100, 20,"DtValidPDOP","Valid.prod.", ListaCampos.DB_SI, true);
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
    lcRec.montaSql(false, "RECURSO", "PP");
    lcRec.setQueryCommit(false);
    lcRec.setReadOnly(true);
    txtCodRec.setTabelaExterna(lcRec);
    txtDescRec.setListaCampos(lcRec);

    setAltDet(170);
    pinDet = new JPanelPad(590,180);
    setPainel( pinDet, pnDet);
    setListaCampos(lcDet);
    setNavegador(navRod);
    adicCampo(txtNumSeqOf, 7, 20, 40, 20,"SeqOf","Item",ListaCampos.DB_PK,true);
    adicCampo(txtCodFase, 50, 20, 77, 20,"CodFase","Cód.fase", ListaCampos.DB_FK, txtDescFase, true);
    adicDescFK(txtDescFase, 130, 20, 227, 20,"DescFase", "Descrição da fase");
    adicCampo(txtTempoOf, 360, 20, 100, 20,"TempoOf","Tempo (Seg.)",ListaCampos.DB_SI, true);
    adicCampo(txtCodRec, 7, 60, 60, 20,"CodRecP","Cód.rec.", ListaCampos.DB_FK, txtDescRec, true);
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
    lcDet.setQueryInsert(false);
    montaTab();
    
    lcCampos.setReadOnly(true);

    tab.setTamColuna(50,0);
    tab.setTamColuna(50,1);
    tab.setTamColuna(200,2);
    tab.setTamColuna(100,3);
    tab.setTamColuna(50,4);
    tab.setTamColuna(200,5);
    
    String sDtFabProd = txtDtFabProd.getVlrString();
    Date data = new Date();
    System.out.println("\n     sDtFabProd"+sDtFabProd+"\ndata de inicio: "+txtDataIniProdFs.getVlrString()+"    data de fabricação: "+txtDtFabProd.getVlrDate()+"\n\n");

    lcCampos.addCarregaListener(this);
    lcDet.addCarregaListener(this);
    lcFase.addCarregaListener(this);
    lcProd.addCarregaListener(this);
    lcRec.addCarregaListener(this);
     
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
    lcCampos.carregaDados();  
  }        
}
