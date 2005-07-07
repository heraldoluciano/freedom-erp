/*
 * Created on 09/06/2005
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

import org.freedom.componentes.ImprimeOS;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.FRelatorio;


public class FRChequeAberto extends FRelatorio {
  private JTextFieldPad txtDataini = new JTextFieldPad(JTextFieldPad.TP_DATE,10,0); 
  private JTextFieldPad txtDatafim = new JTextFieldPad(JTextFieldPad.TP_DATE,10,0); 
  private JTextFieldPad txtCliente = new JTextFieldPad(JTextFieldPad.TP_STRING,50,0);
 
  
  public FRChequeAberto() {
    setTitulo("CHEQUES EM ABERTO");
    setAtribos(160,80,285,180);


    txtDataini.setVlrDate(new Date());
    txtDatafim.setVlrDate(new Date());
    
    JLabelPad lbLinha = new JLabelPad();
    lbLinha.setBorder(BorderFactory.createEtchedBorder());

    adic(new JLabelPad("Periodo:"),7,5,100,20);
    adic(lbLinha,60,15,210,2);
    adic(new JLabelPad("De:"),7,30,30,20);
    adic(txtDataini,32,30,97,20);
    adic(new JLabelPad("Até:"),135,30,30,20);
    adic(txtDatafim,165,30,97,20);
    adic(new JLabelPad("Cliente"),7,55,300,20);
    adic(txtCliente,7,75,212,20);
    
    
  }
  public void setConexao(Connection cn) {
    super.setConexao(cn);
  }
  
  public void actionPerformed(ActionEvent evt) {
      super.actionPerformed(evt);  	  	
  }  
  
  public ResultSet getResultSet(){

    String NomeCli = "";        
    
  	NomeCli = "'"+txtCliente.getVlrString()+"%'";
  		
  	String sSQL = "select vdcliente.Nomecli as nome, fnbanco.Nomebanco as banco, "
  		+" sgcheque.NCHEQUE, sgcheque.Dtins as data, sgcheque.valor, sgcheque.predata "
        +" from vdcliente, fnbanco, sgcheque where "
        +" vdcliente.CODCLI=sgcheque.CODCLI and fnbanco.codbanco=sgcheque.codbanco and "
		+" sgcheque.status=0 and UPPER(vdcliente.Nomecli) LIKE UPPER( "+NomeCli+" )"
		+" and sgcheque.dtins BETWEEN ? AND ? ORDER BY sgcheque.DTINS ";

  	PreparedStatement ps = null;
  	ResultSet rs = null;
    try {
    	ps = con.prepareStatement(sSQL);
    	ps.setDate(1,Funcoes.dateToSQLDate(txtDataini.getVlrDate()));
    	ps.setDate(2,Funcoes.dateToSQLDate(txtDatafim.getVlrDate()));
    	
    	    	
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
     
    String sPag = "";
       
    String sDataini = "";
    String sDatafim = "";
    
    sDataini = txtDataini.getVlrString();
    sDatafim = txtDatafim.getVlrString();
       
    ResultSet rs = getResultSet(); 
   
    try {
 
      imp.limpaPags();
      double total = 0.00;
     
      while ( rs.next() ) {
      	
      	 total += rs.getFloat("valor");
     
      	if (imp.pRow()>=(linPag-1)) {
            imp.say(imp.pRow()+1,0,""+imp.comprimido());
            imp.say(imp.pRow()+0,0,"|"+Funcoes.replicate("-",107)+"|");
            imp.incPags();
            imp.eject();
        }
      	if (imp.pRow()==0) {
        	imp.montaCab();
        	imp.setTitulo("Relatório de cheques em aberto"+sPag);
        	imp.addSubTitulo("RELATÓRIO DE CHEQUES EM ABERTO "+sPag+"   -   PERIODO DE :"+sDataini+" ATE: "+sDatafim);
        	imp.impCab(110, true);
            imp.say(imp.pRow()+0,0,""+imp.comprimido());
            imp.say(imp.pRow()+0,0,"|"+Funcoes.replicate("-",107)+"|");
      	
      	
      	         
           imp.say(imp.pRow()+1,0,""+imp.comprimido());
           imp.say(imp.pRow()+0,0,Funcoes.replicate("=",108));
                             
           imp.say(imp.pRow()+2,0,""+imp.comprimido());
      	}           
           imp.say(imp.pRow()+0,2,"Data/Entrada");
           imp.say(imp.pRow()+0,16,"Cliente");
           imp.say(imp.pRow()+0,67,"N.Cheque");
           imp.say(imp.pRow()+0,3,"Para");
           imp.say(imp.pRow()+0,15,"Valor");
           imp.say(imp.pRow()+0,11,"Banco");
           imp.say(imp.pRow()+1,0,""+imp.comprimido());
           imp.say(imp.pRow()+0,0,Funcoes.replicate("-",109));          
      		
        imp.say(imp.pRow()+1,0,""+imp.normal());
      	
        imp.say(imp.pRow()+0,2,Funcoes.dateToStrDate(rs.getDate("Data")));
        imp.say(imp.pRow()+0,5,rs.getString("nome"));
        imp.say(imp.pRow()+0,2, Funcoes.strZero(rs.getString("NCheque"),8));
        imp.say(imp.pRow()+0,3,Funcoes.dateToStrDate(rs.getDate("predata")));
        imp.say(imp.pRow()+0,3,"R$ "+Funcoes.adicEspacosDireita(rs.getString("valor"),10));
        imp.say(imp.pRow()+0,2,"| "+rs.getString("banco"));
           
        if (imp.pRow()>=linPag) {
           imp.incPags();
           imp.eject();
        }
     }
     
     imp.say(imp.pRow()+1,0,""+imp.normal());
     imp.say(imp.pRow()+0,0,Funcoes.replicate("=",109));
     imp.say(imp.pRow()+1,73,"  Total --->");
     imp.say(imp.pRow()+0,1,"    R$ "+Funcoes.strDecimalToStrCurrency(1,
			2, (total) + "") );
     imp.eject(); 
     
      
      imp.fechaGravacao();

      if (!con.getAutoCommit())
      	con.commit();
      
    }  
    catch ( SQLException err ) {
		Funcoes.mensagemErro(this,"Erro consulta tabela de Cheques!"+err.getMessage());      
    }
    
    if (bVisualizar) {
      imp.preview(this);
    }
    else {
      imp.print();
    }        
  }
}
