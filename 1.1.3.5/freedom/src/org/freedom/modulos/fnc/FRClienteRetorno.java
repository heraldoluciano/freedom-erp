/*
 * Created on 07/06/2005
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
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import javax.swing.BorderFactory;
import org.freedom.componentes.JLabelPad;

import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.ImprimeOS;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.FRelatorio;


public class FRClienteRetorno extends FRelatorio {
  private static final long serialVersionUID = 1L;	
  private JTextFieldPad txtDataini = new JTextFieldPad(JTextFieldPad.TP_DATE,10,0); 
  private JTextFieldPad txtDatafim = new JTextFieldPad(JTextFieldPad.TP_DATE,10,0); 
  private JTextFieldPad txtCodCliente = new JTextFieldPad(JTextFieldPad.TP_INTEGER,4,0);
  private JTextFieldFK  txtCliente = new JTextFieldFK(JTextFieldPad.TP_STRING, 50, 0);
  
  private ListaCampos lcTCliente = new ListaCampos(this,"TR");
  
  public FRClienteRetorno() {
    setTitulo("CLIENTE X RETORNO");
    setAtribos(160,80,285,180);

    txtDataini.setVlrDate(new Date());
    txtDatafim.setVlrDate(new Date());
    
    lcTCliente.add(new GuardaCampo( txtCodCliente, "Codcli", "Cód.Cli", ListaCampos.DB_PK, true));
    lcTCliente.add(new GuardaCampo( txtCliente, "Nomecli", "Cliente", ListaCampos.DB_SI, false));
    lcTCliente.montaSql(false, "CLIENTE", "VD");    
    lcTCliente.setQueryCommit(false);
    lcTCliente.setReadOnly(true);
    txtCodCliente.setTabelaExterna(lcTCliente);
    
    JLabelPad lbLinha = new JLabelPad();
    lbLinha.setBorder(BorderFactory.createEtchedBorder());

    adic(new JLabelPad("Periodo"),7,5,100,20);
    adic(lbLinha,60,15,210,2);
    adic(new JLabelPad("De:"),7,30,30,20);
    adic(txtDataini,32,30,97,20);
    adic(new JLabelPad("Até:"),135,30,30,20);
    adic(txtDatafim,165,30,97,20);
    adic(new JLabelPad("Cód.Cliente"),7,55,100,20);
    adic(txtCodCliente,7,75,80,20);
    adic(new JLabelPad("Cliente"),91,55,100,20);
    adic(txtCliente,91,75,170,20);
      
  }
  
  public void setConexao(Connection cn) {
    super.setConexao(cn);
    lcTCliente.setConexao(cn);
  }

  public void actionPerformed(ActionEvent evt) {
      super.actionPerformed(evt);  	  	
  }
  

  public ResultSet getQtCheque(){
  		
  	String sSQL = " select count(NCHEQUE)as soma, codcli from sgcheque "
  	  	         +" group by codcli"; 
  	
  	PreparedStatement ps = null;
  	ResultSet rs = null;
   
  	try {
    	ps = con.prepareStatement(sSQL);    	
    	    	
    	rs = ps.executeQuery();   	
	      
    }
    catch(SQLException e) {
    	e.printStackTrace();
    }
    
  	return rs;
  }
 
  public ResultSet getResultSet(){
 		
  	String sSQL = "  select vdcliente.Nomecli as nome, vdcliente.codcli, "
  	    +"  sgcheque.NCHEQUE, sgcheque.valor, sgcheque.codbanco, sgcheque.codcli,"
	    +"  sgrcheque.NCHEQUE, sgrcheque.NSAIDA, fnbanco.nomebanco, sgrcheque.codigor, "
	    +"  fnbanco.codbanco from vdcliente, sgcheque, sgrcheque, fnbanco where "
	    +"  vdcliente.CODCLI=sgcheque.CODCLI and fnbanco.codbanco=sgcheque.codbanco and "
		+"  vdcliente.Codcli = ? and sgrcheque.codigor<>99"
		+"  and sgcheque.NCHEQUE=sgrcheque.NCHEQUE and sgcheque.CODBANCO=sgrcheque.CODBANCO and sgrcheque.NSAIDA=1 and "
		+"  sgrcheque.dtins BETWEEN ? AND ? ORDER BY sgrcheque.DTINS";
  		 
  	
  	PreparedStatement ps = null;
  	ResultSet rs = null;
    try { 
    	ps = con.prepareStatement(sSQL);
        ps.setInt(1,txtCodCliente.getVlrInteger().intValue());
    	ps.setDate(2,Funcoes.dateToSQLDate(txtDataini.getVlrDate()));
    	ps.setDate(3,Funcoes.dateToSQLDate(txtDatafim.getVlrDate()));
    	
    	    	
    	rs = ps.executeQuery();
    }
    catch(SQLException e) {
    	e.printStackTrace();
    }
  	
  	return rs;
  }
  
  public void imprimir(boolean bVisualizar) {

    if (txtDatafim.getVlrDate().before(txtDataini.getVlrDate())) {
		Funcoes.mensagemInforma(this,"Data final maior que a data inicial!");
      return;
    }

    ImprimeOS imp = new ImprimeOS("",con);
    int linPag = imp.verifLinPag()-1;
    int codcli = 0;
    int ncheque = 0;
    int ContRetorno = 0;
    double perc = 0.00;
    int qt = 0;
    
    boolean pass = false;
    
    String sPag = "";
       
    String sDataini = "";
    String sDatafim = "";
    
    sDataini = txtDataini.getVlrString();
    sDatafim = txtDatafim.getVlrString();
       
    ResultSet s = getResultSet(); 
    
    try{
       	
       	while (s.next())
       	 	if (ncheque != s.getInt("NCHEQUE"))
       	  	{
       	  	  ncheque=s.getInt("NCHEQUE");
       	  	  ContRetorno+=1;
       	  	}
       	s.close();
       } catch ( SQLException err ) {
		Funcoes.mensagemErro(this,"Erro ao contar a quantidade de Cheque!\n"+err.getMessage()); 
       } 	
  
  ResultSet rs = getResultSet();      
 
    try {
 
      imp.limpaPags();
      
      
      while ( rs.next() ) {
      	
      	          ResultSet rs2 = getQtCheque();
      	          
      	             try{
      	             	
      	             	while (rs2.next())
      	                    if (rs2.getInt("CODCLI")==rs.getInt("CODCLI"))
      	                    	qt = rs2.getInt("soma");
      	             	
      	             } catch ( SQLException err ) {
      	      		Funcoes.mensagemErro(this,"Erro ao contar as entrada de Cheque!\n"+err.getMessage()); 
      	             }
      	             
      	             
      	if (codcli != rs.getInt("CODCLI")){
      		pass = true;
      		codcli = rs.getInt("CODCLI");
      	}
      	else pass = false;
      	
      	if (imp.pRow()>=(linPag-1)) {
            imp.say(imp.pRow()+1,0,""+imp.comprimido());
            imp.say(imp.pRow()+0,0,"|"+Funcoes.replicate("-",89)+"|");
            imp.incPags();
            imp.eject();
        }
      	if (imp.pRow()==0) {
        	imp.montaCab();
        	imp.setTitulo("Relatório de cliente x retorno"+sPag);
        	imp.addSubTitulo("RELATÓRIO DE CLIENTE X RETORNO "+sPag+"   -   PERIODO DE :"+sDataini+" ATE: "+sDatafim);
        	imp.impCab(91, true);
            imp.say(imp.pRow()+0,0,""+imp.comprimido());
            imp.say(imp.pRow()+0,0,"|"+Funcoes.replicate("-",88)+"|");
      	}
      	
      	if (pass==true){	
         
           imp.say(imp.pRow()+1,0,""+imp.comprimido());
           imp.say(imp.pRow()+0,0,Funcoes.replicate("=",90));
           imp.say(imp.pRow()+1,0,""+imp.comprimido());
           
           imp.say(imp.pRow()+0,0," Cliente:");
           imp.say(imp.pRow()+0,2,rs.getString("NOMECLI"));
           imp.say(imp.pRow()+0,2," Cheques Emitido:");
           imp.say(imp.pRow()+0,2,(qt)+"");
           imp.say(imp.pRow()+1,0,""+imp.comprimido());
           imp.say(imp.pRow()+0,74," Cheques Retornado:"); 
           imp.say(imp.pRow()+0,2,(ContRetorno)+"" );
           imp.say(imp.pRow()+1,0,""+imp.comprimido());
           
           perc = (ContRetorno*100)/qt;
           
           imp.say(imp.pRow()+0,70," Percentual de Retorno:");
           imp.say(imp.pRow()+0,2,(perc)+" %" );
         
           imp.say(imp.pRow()+1,0,""+imp.comprimido());
           
           imp.say(imp.pRow()+0,2,"Cheques Retornado");
           imp.say(imp.pRow()+0,5,"Valor");
           imp.say(imp.pRow()+0,8,"Banco");
           imp.say(imp.pRow()+1,0,""+imp.comprimido());
           imp.say(imp.pRow()+0,0,Funcoes.replicate("-",90));          
      	  }
      	
           imp.say(imp.pRow()+1,0,""+imp.normal());
           imp.say(imp.pRow()+0,5,Funcoes.strZero(rs.getString("NCheque"),8));
           imp.say(imp.pRow()+0,23,"R$ "+rs.getString("valor"));
           imp.say(imp.pRow()+0,5,rs.getString("nomebanco"));
     
        if (imp.pRow()>=linPag) {
           imp.incPags();
           imp.eject();
        }
     }
     imp.say(imp.pRow()+1,0,""+imp.normal());
     imp.say(imp.pRow()+0,0,Funcoes.replicate("=",90));
      
      imp.eject();
      
      imp.fechaGravacao();

      if (!con.getAutoCommit())
      	con.commit();
      
    }  
    catch ( SQLException err ) {
		Funcoes.mensagemErro(this,"Erro consulta tabela de Cheques!\n"+err.getMessage());      
    }
    
    if (bVisualizar) {
      imp.preview(this);
    }
    else {
      imp.print();
    }        
  }
}
