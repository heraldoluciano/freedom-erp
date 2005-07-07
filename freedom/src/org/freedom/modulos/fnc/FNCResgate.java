/*
 * Created on 03/06/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package org.freedom.modulos.fnc;


import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.freedom.acao.PostEvent;
import org.freedom.acao.PostListener;
import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.ImprimeOS;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.FDados;


/**
 * @author Robson
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class FNCResgate extends FDados implements PostListener{
	
	 
	  private JTextFieldPad txtCodBanco = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 3, 0);
	  private JTextFieldFK  txtBanco = new JTextFieldFK(JTextFieldPad.TP_STRING, 50, 0);
	  private JTextFieldPad txtNcheque = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);
	  private JTextFieldFK  txtValor = new JTextFieldFK(JTextFieldPad.TP_STRING, 50, 0);
	  private JTextFieldFK  txtData = new JTextFieldFK(JTextFieldPad.TP_DATE, 10, 0);
	  private JTextFieldFK  txtCodCliente = new JTextFieldFK(JTextFieldPad.TP_INTEGER, 7, 0);
	  private JTextFieldFK  txtCliente = new JTextFieldFK(JTextFieldPad.TP_STRING, 50, 0);
	  private JTextFieldPad txtDescricao = new JTextFieldPad(JTextFieldPad.TP_STRING, 35,0);
	  
	  private ListaCampos lcTBanco = new ListaCampos(this,"MB");
	  private ListaCampos lcTCheque = new ListaCampos(this,"MB");
	 	
	  public FNCResgate () {
	    setTitulo("RESGATE DE CHEQUE");
	    setAtribos( 200, 60, 350, 236);
	             
	    
	    lcTBanco.add(new GuardaCampo( txtCodBanco, "CodBanco", "Cód.Banco", ListaCampos.DB_PK,null, true));
	    lcTBanco.add(new GuardaCampo( txtBanco, "NomeBanco", "Banco", ListaCampos.DB_SI,null, false));
	    lcTBanco.montaSql(false, "BANCO", "FN");    
	    lcTBanco.setQueryCommit(false);
	    lcTBanco.setReadOnly(true);
	    txtCodBanco.setTabelaExterna(lcTBanco);
	    
	      
	    lcTCheque.add(new GuardaCampo( txtCodBanco, "CodBanco", "Cód.Banco", ListaCampos.DB_PK,null, true));
	    lcTCheque.add(new GuardaCampo( txtNcheque, "NCheque", "N.Cheque", ListaCampos.DB_PK, true));
	    lcTCheque.add(new GuardaCampo( txtData, "DTINS", "Data/Entrada", ListaCampos.DB_SI, false));	   
	    lcTCheque.montaSql(false, "CHEQUE", "SG");    
	    lcTCheque.setQueryCommit(false);
	    lcTCheque.setReadOnly(true);
	    txtNcheque.setTabelaExterna(lcTCheque);
	       	   
	    adicCampo(txtCodBanco, 7, 20, 100, 20, "CodBanco", "Cód.Banc.",ListaCampos.DB_PK,null, true);
	    adicDescFK(txtBanco, 110, 20, 219, 20, "NomeBanco", "Banco");
	    adicCampo(txtNcheque, 7, 60, 100, 20, "Ncheque", "N.Cheque",ListaCampos.DB_PK, true);
	    adicDescFK(txtValor, 110, 60, 100, 20, "Valor", "Valor");
	    adicDescFK(txtData , 213, 60, 100, 20, "Datas", "Data/Entrada");
	    adicDescFK(txtCodCliente, 7,100,100,20, "codcli", "Cod.Cli");
		adicDescFK(txtCliente, 110, 100, 219, 20, "Cliente", "Cliente"); 
		adicCampo(txtDescricao, 7, 140, 220, 20, "DESCRICAO", "Descrição", ListaCampos.DB_SI, false);
	  	setListaCampos( false, "RESCHEQUE", "SG");
		btImp.addActionListener(this);
	    btPrevimp.addActionListener(this); 
	    txtNcheque.addActionListener(this);  
	    setImprimir(true);  
	   }
	  
	  
	  public void beforePost(PostEvent pevt) {
	
	     if (txtNcheque.getText().trim().length() < 1) {
	       pevt.cancela();
	       Funcoes.mensagemInforma( this,"O Campo, Número do Cheque é requerido! ! ");
	       txtNcheque.requestFocus();
	      }
	    else MudaStatus(); 
	   
	  }
	
	private void MudaStatus(){
	 	
  	String sSQL = "UPDATE SGCHEQUE SET STATUS=? WHERE CODBANCO=? AND NCHEQUE=?";
  	
  	try {
  		int iparam = 1;
  		int valor=1;
  		
  		PreparedStatement ps = con.prepareStatement(sSQL);
  		
  		ps.setInt(iparam++,valor);
  		ps.setInt(iparam++,txtCodBanco.getVlrInteger().intValue());
  		ps.setInt(iparam++,txtNcheque.getVlrInteger().intValue());
				
  		ps.execute();

  		if (!con.getAutoCommit())
  			con.commit();

  	} catch (SQLException err) {
  		Funcoes.mensagemErro(this, "Erro ao atualizar o status do cheque!\n"
	+ err.getMessage());
  		err.printStackTrace();}	

}  
	  
	  public void actionPerformed(ActionEvent evt) {

	  	 if (evt.getSource()== txtNcheque)
	  		mostracampos();
	  	
	  	 if (evt.getSource() == btPrevimp) {
	        imprimir(true);   	
	    }
	    else if (evt.getSource() == btImp) 
	      imprimir(false);
	   	        
	    super.actionPerformed(evt);
	  }
	  

	  public void mostracampos(){
		if (txtNcheque.getVlrString().length()>0 )
		{
			String sSQL = "SELECT CODCLI, VALOR FROM SGCHEQUE WHERE CODBANCO=? AND NCHEQUE=? ";
			try {
				PreparedStatement ps = con.prepareStatement(sSQL);
				ps.setInt(1,txtCodBanco.getVlrInteger().intValue());
				ps.setInt(2,txtNcheque.getVlrInteger().intValue());
				
				ResultSet rs = ps.executeQuery();
				
				if (rs.next()){
				 txtCodCliente.setVlrString(rs.getString("CODCLI"));
				 txtValor.setVlrString(rs.getString("VALOR"));
				}
				
				ps.close();
				
				if (!con.getAutoCommit())
					con.commit();
				
			} catch (SQLException err) {
				Funcoes.mensagemErro(this, "Erro ao carregar a tabela de Entrada de Cheque!\n"
						+ err.getMessage());
				err.printStackTrace();}	
		}
		if (txtCodCliente.getVlrInteger().intValue() > 0)
		{
			String sSQL = "SELECT NOMECLI FROM VDCLIENTE WHERE CODCLI=? ";
			try {
				PreparedStatement ps = con.prepareStatement(sSQL);
				ps.setInt(1,txtCodCliente.getVlrInteger().intValue());
			
				ResultSet rs = ps.executeQuery();
				
				if (rs.next())
					txtCliente.setVlrString(rs.getString("NOMECLI"));
				
				ps.close();
				
				if (!con.getAutoCommit())
					con.commit();
				
			} catch (SQLException err) {
				Funcoes.mensagemErro(this, "Erro ao carregar a tabela de Entrada de Cheque!\n"
						+ err.getMessage());
				err.printStackTrace();}	
		}
	
	  }
	    	  
	  private void imprimir(boolean bVisualizar) {
		 
	        String ordena, ConsNome, CodigoR;
	        
		    ImprimeOS imp = new ImprimeOS("",con);
		    int linPag = imp.verifLinPag()-1;
		    imp.montaCab();
		    imp.setTitulo("Relatório de RESAGTE DE CHEQUE");
		    
		    DLRResgate dl = new DLRResgate(this);
		    dl.setVisible(true);
		    	    
		    if (dl.OK == false) {
		      dl.dispose();
		      return;
		    }
		    if (dl.CompData()){	    
		    
		    ordena="sgrescheque.DTINS";	
		    ordena=dl.getValor();	
		    ConsNome="'"+dl.getCNome()+"%'";
		    		    
		    String sSQL =  " select vdcliente.Nomecli as nome, fnbanco.Nomebanco as banco, "
	                      +" sgcheque.NCHEQUE, sgcheque.valor,  "
	                      +" sgcheque.codbanco, sgcheque.codcli, sgrescheque.NCHEQUE, "
						  +" sgrescheque.CODBANCO, sgrescheque.DTINS, sgrescheque.DESCRICAO "
						  +" from vdcliente, fnbanco, sgcheque, sgrescheque where "
						  +" vdcliente.CODCLI=sgcheque.CODCLI and "
						  +" fnbanco.codbanco=sgcheque.codbanco and UPPER(vdcliente.Nomecli) LIKE UPPER( "+ConsNome+" ) "
						  +" and sgcheque.NCHEQUE=sgrescheque.NCHEQUE and sgcheque.CODBANCO=sgrescheque.CODBANCO "
						  +" and sgrescheque.dtins BETWEEN ? AND ? ORDER BY "+ordena;
			
		    PreparedStatement ps = null;
		    ResultSet rs = null;
		    try {
		    	int iparam = 1;
		    	double total = 0.00;
		    	
		      ps = con.prepareStatement(sSQL);
		      
		      ps.setDate(iparam++, Funcoes.dateToSQLDate( dl.GetDataini().getVlrDate()) );
	          ps.setDate(iparam++, Funcoes.dateToSQLDate( dl.GetDatafim().getVlrDate()) );
	          
	            
		      rs = ps.executeQuery();
		      imp.limpaPags();
		      
		      	      
		      while ( rs.next() ) {
		      	
		      total += rs.getFloat("valor");
		      
		         if (imp.pRow()==0) {
		            imp.impCab(141, false);
		            imp.say(imp.pRow()+0,0,""+imp.normal());
		            imp.say(imp.pRow()+0,0,"");
		            imp.say(imp.pRow()+0,2,"Data/Resg.");
		            imp.say(imp.pRow()+0,2,"Descrição");
		            imp.say(imp.pRow()+0,38,"Cliente");
		            imp.say(imp.pRow()+0,89,"N.Cheque");
		            imp.say(imp.pRow()+0,3,"Valor");
		            imp.say(imp.pRow()+0,18,"Banco");
		            imp.say(imp.pRow()+1,0,""+imp.normal());
		            imp.say(imp.pRow()+0,0,Funcoes.replicate("-",140));
		         }
		         imp.say(imp.pRow()+1,0,""+imp.normal());
		         imp.say(imp.pRow()+0,2,Funcoes.dateToStrDate(rs.getDate("dtins")));
		         imp.say(imp.pRow()+0,2,Funcoes.adicEspacosDireita(rs.getString("DESCRICAO"),5));
		         imp.say(imp.pRow()+0,2 , rs.getString("nome"));
		         imp.say(imp.pRow()+0,2, Funcoes.strZero(rs.getString("NCheque"),8));
		         imp.say(imp.pRow()+0,3,"R$ "+Funcoes.adicEspacosDireita(rs.getString("valor"),10));
		         imp.say(imp.pRow()+0,2,"| "+rs.getString("banco") );
		         if (imp.pRow()>=linPag) {
		            imp.incPags();
		            imp.eject();
		         }
		      }
		      
		      imp.say(imp.pRow()+1,0,""+imp.normal());
		      imp.say(imp.pRow()+0,0,Funcoes.replicate("=",140));
		      imp.say(imp.pRow()+1,94,"  Total --->");
		      imp.say(imp.pRow()+0,1,"    R$ "+Funcoes.strDecimalToStrCurrency(0,
					2, (total) + "") );
		      imp.eject();
		 
		      
		      imp.fechaGravacao();
		      
		
		      if (!con.getAutoCommit())
		      	con.commit();
		      dl.dispose();
		      
		      rs.close();
		      ps.close();
		    }  
		    catch ( SQLException err ) {
				Funcoes.mensagemErro(this,"Erro na consulta, na tabela de cheques "+err.getMessage());      
		    }
		    
		    if (bVisualizar) {
		      imp.preview(this);
		    }
		    else {
		      imp.print();
		    }}
		  }
	  
	  public void setConexao(Connection cn) {
	  	super.setConexao(cn); 	
	  	lcTBanco.setConexao(cn);
	  	lcTCheque.setConexao(cn);  	
	  }
  
		  
}
