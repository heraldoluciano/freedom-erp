/*
 * Created on 26/05/2005
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

public class DLRSCheque extends FFDialogo {
  private static final long serialVersionUID = 1L;	
  private JRadioGroup rgOrdem = null;
  private JRadioGroup rgSaida = null;
  private JTextFieldPad txtDataini = new JTextFieldPad(JTextFieldPad.TP_DATE,
			10, 0);
  private JTextFieldPad txtDatafim = new JTextFieldPad(JTextFieldPad.TP_DATE,
			10, 0);
  private JTextFieldPad txtNome = new JTextFieldPad(JTextFieldPad.TP_STRING,
		50, 0);

  private JComboBoxPad cbTipoSaida = null;
  
  private JLabelPad lbOrdem = new JLabelPad("Ordenar por:");
  private JLabelPad lbSaida = new JLabelPad("Visualizar saída:");
  private Vector vLabs = new Vector();
  private Vector vVals = new Vector();
  private Vector vLabs2 = new Vector();
  private Vector vVals2 = new Vector();
  private Vector vStrTipo = new Vector();
  
  public DLRSCheque(Component cOrig) {
  	super(cOrig);
    setTitulo("Relatório de Saída de Cheque");
    setAtribos(291,292);
    
    /// Prepara o combobox ////////////////////
    vStrTipo.addElement("<Selecione>");           
    vStrTipo.addElement("Depositado");           
    vStrTipo.addElement("Perdido");     
    vStrTipo.addElement("Repassado");   
    vStrTipo.addElement("Roubado");
    vStrTipo.addElement("Todos");
    
    cbTipoSaida = new JComboBoxPad(vStrTipo, vStrTipo, JComboBoxPad.TP_STRING, 10, 0); 
	   	
    
    vLabs2.addElement("A primeira");
    vLabs2.addElement("Todas");
    vVals2.addElement("P");
    vVals2.addElement("T");
    rgSaida = new JRadioGroup(1,2,vLabs2,vVals2);
    rgSaida.setVlrString("P");
    adic(lbSaida,7,5,150,15);
    adic(rgSaida,7,25,264,30);  
    
    adic(new JLabelPad("Periodo:"), 7, 55, 125, 20);
	adic(new JLabelPad("De:"), 7, 75, 35, 20);
	adic(txtDataini, 32, 75, 100, 20);
	adic(new JLabelPad("Até:"), 140, 75, 25, 20);
	adic(txtDatafim, 170, 75, 100, 20);
	
    vLabs.addElement("Data");
    vLabs.addElement("Nome");
    vVals.addElement("D");
    vVals.addElement("N");
    rgOrdem = new JRadioGroup(1,2,vLabs,vVals);
    rgOrdem.setVlrString("D");
    adic(lbOrdem,7,100,80,15);
    adic(rgOrdem,7,120,264,30);
    
    adic(new JLabelPad("Tipo de Saída"), 7, 150, 90, 20);
    adic(cbTipoSaida,7,170,100,25);
    adic(new JLabelPad("Cliente"), 111, 150, 75, 20);
	adic(txtNome, 111, 170, 160, 25);
	
  }
  
  public String getTipoS(){
  	return cbTipoSaida.getVlrString();
  }
  
  public int getTipo(){
  	
  	int Result = 0;
  	
  	if (cbTipoSaida.getVlrString().equals("Depositado"))
  		Result = 1;
  	else if(cbTipoSaida.getVlrString().equals("Perdido"))
  		Result = 2;
  	else if(cbTipoSaida.getVlrString().equals("Repassado"))
  		Result = 3;
  	else if(cbTipoSaida.getVlrString().equals("Roubado"))
  		Result = 4;
  	else if(cbTipoSaida.getVlrString().equals("Todos"))
  		Result = 5;
  
  	return Result;
  }
  
  public String SetTipo(int valor){
  	
  	String Result = null;
  	
  	if (valor==1)
  		Result = "Depósito";
  	else if (valor==2)
  		Result = "Perda";
  	else  if (valor==3)
  		Result = "Repasse";
  	else  if (valor==4)
  		Result = "Roubo";
   
  	return Result;
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
  
  public boolean CompTipo(){
  	if ((getTipoS().length()<1 )|(getTipoS().equals("<Selecione>")))
    { 
		Funcoes.mensagemInforma(this,
				"Escolha um tipo de saída para a consulta! !");
		return false;}
  	else return true;
  }
  
  
  public String getValor() {
    String sRetorno = "";
    if (rgOrdem.getVlrString().compareTo("D") == 0 )
      sRetorno = "sgscheque.DTINS";
    else if (rgOrdem.getVlrString().compareTo("N") == 0 )
      sRetorno = "vdcliente.NOMECLI";
    return sRetorno;
  }
 
  public String getSValor() {
    String sRetorno = "";
    if (rgSaida.getVlrString().compareTo("P") == 0 )
      sRetorno = "P";
    else if (rgSaida.getVlrString().compareTo("T") == 0 )
      sRetorno = "T";
    return sRetorno;
  } 
  
}
