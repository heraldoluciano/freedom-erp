/**
 * @version 30/01/2004 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: projetos.freedom.gms <BR>
 * Classe: @(#)FAtribuicao.java <BR>
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
 * Atribuições das pessoas que compôem o fluxo da RMA.
 * 
 */

package org.freedom.modulos.gms;
import java.awt.event.ActionListener;

import org.freedom.componentes.JLabelPad;
import javax.swing.JScrollPane;

import org.freedom.acao.CarregaEvent;
import org.freedom.acao.CarregaListener;
import org.freedom.acao.CheckBoxEvent;
import org.freedom.acao.PostEvent;
import org.freedom.acao.PostListener;
import org.freedom.componentes.JCheckBoxPad;
import org.freedom.componentes.JTextAreaPad;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.telas.FDados;

public class FAtribuicao extends FDados implements ActionListener, PostListener, CarregaListener {
  private JTextFieldPad txtIdAtrib = new JTextFieldPad(JTextFieldPad.TP_STRING,10,0);
  private JTextFieldPad txtDescAtrib = new JTextFieldPad(JTextFieldPad.TP_STRING,50,0);
  private JTextFieldPad txtRmaAtrib = new JTextFieldPad(JTextFieldPad.TP_STRING,2,0);
  private JTextAreaPad txaObsAtrib = new JTextAreaPad(500);
  private JScrollPane spnObs = new JScrollPane(txaObsAtrib);
  private JCheckBoxPad cbReq = new JCheckBoxPad("Requisitante",new Integer(1),new Integer(0));
  private JCheckBoxPad cbGer = new JCheckBoxPad("Gerente",new Integer(2),new Integer(0));
  private JCheckBoxPad cbDir = new JCheckBoxPad("Diretor",new Integer(4),new Integer(0));
  private JCheckBoxPad cbAlm = new JCheckBoxPad("Almoxarife",new Integer(8),new Integer(0));
  public FAtribuicao () {
  	super();
    setTitulo("Cadastro de atribuições");
    setAtribos(50, 50, 340, 280);
    adicCampo(txtIdAtrib, 7, 20, 70, 20,"IdAtrib","Cód.atrib.", ListaCampos.DB_PK, true);
    adicCampo(txtDescAtrib, 80, 20, 230, 20,"DescAtrib","Descrição da atirbuição", ListaCampos.DB_SI, true);
    
    adicCampoInvisivel(txtRmaAtrib, "RmaAtrib","Atrib.", ListaCampos.DB_SI, true);
    adic(cbReq,7,40,150,20);
    adic(cbGer,160,40,150,20);
    adic(cbDir,7,60,150,20);
    adic(cbAlm,160,60,150,20);
    
    adicDBLiv(txaObsAtrib, "ObsAtrib","Obs.", false);
    adic(new JLabelPad("Observação"),7,80,303,20);
    adic(spnObs,7,100,303,100);
    
    setListaCampos( true, "ATRIBUICAO", "SG");
    btImp.addActionListener(this);
    btPrevimp.addActionListener(this);
    lcCampos.setQueryInsert(false);
    lcCampos.addCarregaListener(this);
    cbReq.setListaCampos(lcCampos);
    cbGer.setListaCampos(lcCampos);
    cbDir.setListaCampos(lcCampos);
    cbAlm.setListaCampos(lcCampos);
  }
  private void sendAtribRma() {
  	 int iSoma = 0;
 	 iSoma += cbReq.getVlrInteger().intValue();
 	 iSoma += cbGer.getVlrInteger().intValue();
 	 iSoma += cbDir.getVlrInteger().intValue();
 	 iSoma += cbAlm.getVlrInteger().intValue();
 	 txtRmaAtrib.setText(""+iSoma);
  }
  private void putAtribRma() {
  	int iSoma = txtRmaAtrib.getVlrInteger().intValue();
  	if ((iSoma-8) >= 0) {
  		cbAlm.setVlrInteger(new Integer(8));
  		iSoma-=8;
  	}
  	else
  		cbAlm.setVlrInteger(new Integer(0));
  	if ((iSoma-4) >= 0) {
  		cbDir.setVlrInteger(new Integer(4));
  		iSoma-=4;
  	}
  	else
  		cbDir.setVlrInteger(new Integer(0));
  	if ((iSoma-2) >= 0) {
  		cbGer.setVlrInteger(new Integer(2));
  		iSoma-=2;
  	}
  	else
  		cbGer.setVlrInteger(new Integer(0));
  	if ((iSoma-1) >= 0) {
  		cbReq.setVlrInteger(new Integer(1));
  		iSoma-=1;
  	}
  	else
  		cbReq.setVlrInteger(new Integer(0));
  }
  public void valorAlterado(CheckBoxEvent evt) {
     if (evt.getCheckBox() != null)
	   lcCampos.edit();
  }
  public void beforePost(PostEvent pevt) {
     if (pevt.getListaCampos() == lcCampos)
    	sendAtribRma();
  }
  public void afterCarrega(CarregaEvent cevt) {
  	if (cevt.getListaCampos() == lcCampos)
  		putAtribRma();
  }
  public void beforeCarrega(CarregaEvent cevt) { }
}
