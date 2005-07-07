/*
 * Created on 01/06/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package org.freedom.modulos.fnc;

/**
 * @author Robson
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

import java.awt.Component;
import java.util.Vector;

import org.freedom.componentes.JComboBoxPad;
import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JTextFieldPad;

import org.freedom.componentes.JRadioGroup;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.FFDialogo;

public class DLRRetorno extends FFDialogo {
  private JRadioGroup rgOrdem = null;
  private JTextFieldPad txtDataini = new JTextFieldPad(JTextFieldPad.TP_DATE,
			10, 0);
  private JTextFieldPad txtDatafim = new JTextFieldPad(JTextFieldPad.TP_DATE,
			10, 0);
  private JTextFieldPad txtNome = new JTextFieldPad(JTextFieldPad.TP_STRING,
		50, 0);

  private JComboBoxPad cbTipoSaida = null;
  
  private JLabelPad lbOrdem = new JLabelPad("Ordenar por:");
  private Vector vLabs = new Vector();
  private Vector vVals = new Vector();
  private Vector vStrTipo = new Vector();
  
  public DLRRetorno(Component cOrig) {
  	super(cOrig);
    setTitulo("Relatório de Retorno de Cheque");
    setAtribos(291,239);
    
    /// Prepara o combobox ////////////////////
    vStrTipo.addElement("<Selecione>");           
    vStrTipo.addElement("11");           
    vStrTipo.addElement("12");     
    vStrTipo.addElement("13");   
    vStrTipo.addElement("14");
    vStrTipo.addElement("20");           
    vStrTipo.addElement("21");     
    vStrTipo.addElement("22");   
    vStrTipo.addElement("23");
    vStrTipo.addElement("24");           
    vStrTipo.addElement("25");     
    vStrTipo.addElement("26");   
    vStrTipo.addElement("27");
    vStrTipo.addElement("28");           
    vStrTipo.addElement("29");     
    vStrTipo.addElement("30");   
    vStrTipo.addElement("31");
    vStrTipo.addElement("32");           
    vStrTipo.addElement("33");     
    vStrTipo.addElement("34");   
    vStrTipo.addElement("35");
    vStrTipo.addElement("36");           
    vStrTipo.addElement("37");     
    vStrTipo.addElement("40");   
    vStrTipo.addElement("41");
    vStrTipo.addElement("42");           
    vStrTipo.addElement("43");     
    vStrTipo.addElement("44");   
    vStrTipo.addElement("45");
    vStrTipo.addElement("46");
    vStrTipo.addElement("47");           
    vStrTipo.addElement("48");     
    vStrTipo.addElement("49"); 
    vStrTipo.addElement("Todos");
    
    cbTipoSaida = new JComboBoxPad(vStrTipo, vStrTipo, JComboBoxPad.TP_STRING, 10, 0); 
	   	
    adic(new JLabelPad("Periodo:"), 7, 5, 125, 20);
	adic(new JLabelPad("De:"), 7, 25, 35, 20);
	adic(txtDataini, 32, 25, 100, 20);
	adic(new JLabelPad("Até:"), 140, 25, 25, 20);
	adic(txtDatafim, 170, 25, 100, 20);
	
    vLabs.addElement("Data");
    vLabs.addElement("Nome");
    vVals.addElement("D");
    vVals.addElement("N");
    rgOrdem = new JRadioGroup(1,2,vLabs,vVals);
    rgOrdem.setVlrString("D");
    adic(lbOrdem,7,50,80,15);
    adic(rgOrdem,7,70,264,30);
    
    adic(new JLabelPad("Cod.Retorno"), 7, 100, 90, 20);
    adic(cbTipoSaida,7,120,100,25);
    adic(new JLabelPad("Cliente"), 111, 100, 75, 20);
	adic(txtNome, 111, 120, 160, 25);
	
  }
  
  public String getRetorno(){
  	return cbTipoSaida.getVlrString();
  }
  
    public String getCNome(){
  	return txtNome.getVlrString();
  }
  
  public JTextFieldPad GetDataini(){
   	return txtDataini;
  }
  
  public JTextFieldPad GetDatafim(){
   	return txtDatafim;
  }
  
  public boolean CompData(){
  	if (txtDatafim.getVlrDate().before(txtDataini.getVlrDate())){ 
		Funcoes.mensagemInforma(this,
				"Data final maior que a data inicial!");
		return false;}
  	else return true;
  }
  
  public boolean CompRetorno(){
  	if ((getRetorno().length()<1 )|(getRetorno().equals("<Selecione>")))
    { 
		Funcoes.mensagemInforma(this,
				"Escolha um Código de Retorno para a consulta! !");
		return false;}
  	else return true;
  }
  
  
  public String getValor() {
    String sRetorno = "";
    if (rgOrdem.getVlrString().compareTo("D") == 0 )
      sRetorno = "sgrcheque.DTINS";
    else if (rgOrdem.getVlrString().compareTo("N") == 0 )
      sRetorno = "vdcliente.NOMECLI";
    return sRetorno;
  }
  
}
