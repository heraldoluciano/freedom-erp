/*
 * Created on 17/05/2005
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
import java.util.Vector;

import org.freedom.acao.PostEvent;
import org.freedom.acao.PostListener;
import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.ImprimeOS;
import org.freedom.componentes.JComboBoxPad;
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
public class FNCsaida extends FDados implements PostListener {
	
	  private JTextFieldPad txtCodBanco = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 4, 0);
	  private JTextFieldFK  txtBanco = new JTextFieldFK(JTextFieldPad.TP_STRING, 50, 0);
	  private JTextFieldPad txtNcheque = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);
	  private JTextFieldPad txtNSaida = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 3, 0);
	  private JTextFieldFK  txtValor = new JTextFieldFK(JTextFieldPad.TP_STRING, 50, 0);
	  private JTextFieldFK  txtData = new JTextFieldFK(JTextFieldPad.TP_DATE, 10, 0);
	  private JTextFieldFK  txtCodCliente = new JTextFieldFK(JTextFieldPad.TP_INTEGER, 7, 0);
	  private JTextFieldFK  txtCliente = new JTextFieldFK(JTextFieldPad.TP_STRING, 50, 0);
	  private JTextFieldPad  txtDesc = new JTextFieldPad(JTextFieldPad.TP_STRING,30,0);
	  
	  private JComboBoxPad cbTipoSaida = null;
	  
	  private Vector vStrTipo = new Vector();
	  private Vector vValsTipo = new Vector();
	  
	  
	  private ListaCampos lcTBanco = new ListaCampos(this,"TR");
	  private ListaCampos lcTCheque = new ListaCampos(this,"TR");
	 	
	  public FNCsaida () {
	    setTitulo("SAÍDA DE CHEQUE");
	    setAtribos( 200, 100, 350, 250);
	   
	    /// Prepara o combobox ////////////////////
	    vStrTipo.addElement("<Selecione>");           
	    vStrTipo.addElement("Depósito");           
	    vStrTipo.addElement("Perda");     
	    vStrTipo.addElement("Repasse");   
	    vStrTipo.addElement("Roubo");
	    
	    vValsTipo.addElement(new Integer(0));           
	    vValsTipo.addElement(new Integer(1));           
	    vValsTipo.addElement(new Integer(2));           
	    vValsTipo.addElement(new Integer(3));           
	    vValsTipo.addElement(new Integer(4));           
	       	    
	    cbTipoSaida = new JComboBoxPad(vStrTipo, vValsTipo, JComboBoxPad.TP_INTEGER, 4, 0); 
	    
	    ///////////////////////////////////////////
	    
	    lcTBanco.add(new GuardaCampo( txtCodBanco, "CodBanco", "Cód.Banco", ListaCampos.DB_PK, true));
	    lcTBanco.add(new GuardaCampo( txtBanco, "NomeBanco", "Banco", ListaCampos.DB_SI, false));
	    lcTBanco.montaSql(false, "BANCO", "FN");    
	    lcTBanco.setQueryCommit(false);
	    lcTBanco.setReadOnly(true);
	    txtCodBanco.setTabelaExterna(lcTBanco);
	    
	      
	    lcTCheque.add(new GuardaCampo( txtCodBanco, "CodBanco", "Cód.Banco", ListaCampos.DB_PK, true));
	    lcTCheque.add(new GuardaCampo( txtNcheque, "NCheque", "N.Cheque", ListaCampos.DB_PK, true));
	    lcTCheque.add(new GuardaCampo( txtValor, "Valor", "Valor", ListaCampos.DB_SI, false));
	    lcTCheque.add(new GuardaCampo( txtData, "predata", "data", ListaCampos.DB_SI, false));	
	    lcTCheque.add(new GuardaCampo( txtCodCliente, "CodCli", "Cod.Cli.", ListaCampos.DB_SI, false));		   		   
	    lcTCheque.montaSql(false, "CHEQUE", "SG");    
	    lcTCheque.setQueryCommit(false);
	    lcTCheque.setReadOnly(true);
	    txtNcheque.setTabelaExterna(lcTCheque);
	    
	    adicCampo(txtNSaida, 7, 20, 40, 20, "NSAIDA", "N.Saíd.",ListaCampos.DB_PK, true);
	    adicCampo(txtCodBanco, 51, 20, 70, 20, "CodBanco", "Cód.Banc.",ListaCampos.DB_PK, true);
	    adicDescFK(txtBanco, 125, 20, 204, 20, "NomeBanco", "Banco");
	    adicCampo(txtNcheque, 7, 60, 100, 20, "Ncheque", "N.Cheque",ListaCampos.DB_PK, true);
	    adicDescFK(txtValor, 110, 60, 100, 20, "Valor", "Valor");
	    adicDescFK(txtData , 213, 60, 100, 20, "predata", "Predatado Para");
	    adicDescFK(txtCodCliente, 7,100,100,20, "codcli", "codcli");
		adicDescFK(txtCliente, 110, 100, 219, 20, "Cliente", "Cliente"); 
		adicDB(cbTipoSaida,7,140,100,25,"TSAIDA", "Tipos de Saída",false);
		adicCampo(txtDesc, 110, 140, 219, 25, "DESCRICAO", "Descrição",ListaCampos.DB_SI, false);
		setListaCampos( false, "SCHEQUE", "SG");
		btImp.addActionListener(this);
	    btPrevimp.addActionListener(this); 
	    txtNcheque.addActionListener(this);
	    setImprimir(true);
    
	   }
	  
	  
	  public void beforePost(PostEvent pevt) {
	
	     if (txtNcheque.getText().trim().length() < 1) {
	       pevt.cancela();
	       Funcoes.mensagemInforma( this,"O Campo, Número do Cheque é requerido! ! !");
	       txtNcheque.requestFocus();
	      }
	    else if ((cbTipoSaida.getVlrInteger().intValue()<1 )|(cbTipoSaida.getVlrInteger().intValue()>4))
        {
	       	pevt.cancela();
        	Funcoes.mensagemInforma( this," O campo, Tipo de saída é requerido! !");
            txtNcheque.requestFocus();
        }
	   else if (pevt.getEstado()!=ListaCampos.LCS_EDIT){
	   
	   	 if (!ConfereSaida()){
	    	pevt.cancela();
	   	    txtNSaida.requestFocus();}
	   	else MudaStatus();   
	   }
	  else MudaStatus();  
	  }
	  
	  
	  public void actionPerformed(ActionEvent evt) {
	  	
	  	if (evt.getSource()== txtNcheque)
	  		mostracliente(); 
	  	
	  	 if (evt.getSource() == btPrevimp) {
	        imprimir(true);   	
	    }
	    else if (evt.getSource() == btImp) 
	      imprimir(false);
	   	        
	    super.actionPerformed(evt);
	  }

	  public void mostracliente(){
		if (txtCodCliente.getVlrInteger().intValue()>0)
		{
			String sSQL = "SELECT NOMECLI FROM VDCLIENTE WHERE CODCLI=?";
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
				Funcoes.mensagemErro(this, "Erro ao carregar a tabela de clientes!\n"
						+ err.getMessage());
				err.printStackTrace();}	
		}
	
	  }
	  	
	  
	  public boolean ConfereSaida(){
                		
	  	    boolean result = true; 
	  	   	  	
			String sSQL = "SELECT COUNT(*) AS SOMA FROM SGSCHEQUE WHERE CODBANCO=? AND NCHEQUE=?";
			try {
				 int iparam = 1;
				 int qt = 0;
				PreparedStatement ps = con.prepareStatement(sSQL);
				
				ps.setInt(iparam++,txtCodBanco.getVlrInteger().intValue());
				ps.setInt(iparam++,txtNcheque.getVlrInteger().intValue());
									
				ResultSet rs = ps.executeQuery();
				
				if (rs.next())
				qt= rs.getInt("SOMA") +1;
				
				if (txtNSaida.getVlrInteger().intValue()!=qt)
				{
					Funcoes.mensagemErro(this,"O número correto de saída do cheque é : "+qt );	
				    result = false;
				}
				ps.close();
				
				if (!con.getAutoCommit())
					con.commit();
				
			} catch (SQLException err) {
				Funcoes.mensagemErro(this, "Erro ao contar o número de saída de cheques!\n"
						+ err.getMessage());
				err.printStackTrace();}	
	   
			return result;
	  }
	  
	  private void MudaStatus(){
		 	
	  	String sSQL = "UPDATE SGCHEQUE SET STATUS=? WHERE CODBANCO=? AND NCHEQUE=?";
	  	
	  	try {
	  		int iparam = 1;
	  		int valor=0;
	  		
	  		if ((cbTipoSaida.getVlrInteger().intValue()==1 )||(cbTipoSaida.getVlrInteger().intValue()==3 ))
	  			valor=1;
	  		else if ((cbTipoSaida.getVlrInteger().intValue()!=1 )&&(cbTipoSaida.getVlrInteger().intValue()!=3 ))
	  		    valor=2;
	  		
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
	  
	  private void imprimir(boolean bVisualizar) {
		 
	        String ordena, ConsNome, TipoSaida;
	        int dif = 0;
	        int salto = 0;
	        
	        String sSQL = ""; 
	        int larg = 0;
	        
		    ImprimeOS imp = new ImprimeOS("",con);
		    int linPag = imp.verifLinPag()-1;
		    imp.montaCab();
		    imp.setTitulo("Relatório de SAÍDA DE CHEQUE");
		    
		    DLRSCheque dl = new DLRSCheque(this);
		    dl.setVisible(true);
		    	    
		    if (dl.OK == false) {
		      dl.dispose();
		      return;
		    }
		    if ( (dl.CompData())&&(dl.CompTipo()) ){	    
		    
		    ordena="sgscheque.DTINS";	
		    ordena=dl.getValor();	
		    ConsNome="'"+dl.getCNome()+"%'";
		    if ( !(dl.getTipo()==5) )
		    TipoSaida=" and sgscheque.TSAIDA="+dl.getTipo()+"";
		    else {
		    	TipoSaida = "";
	            salto = 8; 	    
		    }	    
		    
		    if (dl.getSValor().equals("P")){  	
		           sSQL =  " select vdcliente.Nomecli as nome, fnbanco.Nomebanco as banco, "
	                      +" sgcheque.NCHEQUE, sgcheque.predata, sgcheque.valor, "
	                      +" sgcheque.codbanco, sgcheque.codcli, sgscheque.NSAIDA ,sgscheque.NCHEQUE, sgscheque.TSAIDA, "
						  +" sgscheque.DESCRICAO, sgscheque.DTINS "
						  +" from vdcliente, fnbanco, sgcheque, sgscheque where "
						  +" vdcliente.CODCLI=sgcheque.CODCLI and "
						  +" fnbanco.codbanco=sgcheque.codbanco and UPPER(vdcliente.Nomecli) LIKE UPPER( "+ConsNome+" ) "
						  +" and sgcheque.NCHEQUE=sgscheque.NCHEQUE and sgcheque.CODBANCO=sgscheque.CODBANCO "+TipoSaida
						  +" and sgscheque.NSAIDA=1 and sgscheque.dtins BETWEEN ? AND ? ORDER BY "+ordena;
		    }
		    else  if (dl.getSValor().equals("T")){  	
		           sSQL =  " select vdcliente.Nomecli as nome, fnbanco.Nomebanco as banco, "
                    +" sgcheque.NCHEQUE, sgcheque.predata, sgcheque.valor, "
                    +" sgcheque.codbanco, sgcheque.codcli, sgscheque.NSAIDA ,sgscheque.NCHEQUE, sgscheque.TSAIDA, "
					  +" sgscheque.DESCRICAO, sgscheque.DTINS "
					  +" from vdcliente, fnbanco, sgcheque, sgscheque where "
					  +" vdcliente.CODCLI=sgcheque.CODCLI and "
					  +" fnbanco.codbanco=sgcheque.codbanco and UPPER(vdcliente.Nomecli) LIKE UPPER( "+ConsNome+" ) "
					  +" and sgcheque.NCHEQUE=sgscheque.NCHEQUE and sgcheque.CODBANCO=sgscheque.CODBANCO "+TipoSaida
					  +" and sgscheque.dtins BETWEEN ? AND ? ORDER BY "+ordena;
		        
		         larg = 6;  
		    }
		    
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
		            imp.impCab(136+larg, false);
		            imp.say(imp.pRow()+0,0,""+imp.normal());
		            imp.say(imp.pRow()+0,0,"");
		            
		            if (dl.getSValor().equals("T"))
		             imp.say(imp.pRow()+0,3,"Saída");	
		            
		            imp.say(imp.pRow()+0,3,"Data/Saída");
		            
		            if (dl.getTipo()==5){
		            imp.say(imp.pRow()+0,3,"Tipo/Saída");
		            dif = 30;
		            }
		            else{
		            imp.say(imp.pRow()+0,3,"Descricao");
		            dif = 0;
		            }
		            
		            imp.say(imp.pRow()+0,39-dif,"Cliente");
		            imp.say(imp.pRow()+0,90-dif,"N.Cheque");
		            imp.say(imp.pRow()+0,3,"Valor");
		            imp.say(imp.pRow()+0,19,"Banco");
		            imp.say(imp.pRow()+1,0,""+imp.normal());
		            imp.say(imp.pRow()+0,0,Funcoes.replicate("-",135+larg));
		         }
		         
		         imp.say(imp.pRow()+1,0,""+imp.normal());
		         
		         if (dl.getSValor().equals("T"))
		         imp.say(imp.pRow()+0,3, rs.getString("NSAIDA")+" Vez" );	
		         	
		         imp.say(imp.pRow()+0,3,Funcoes.dateToStrDate(rs.getDate("dtins")));
		         
		         if (dl.getTipo()==5)
		            imp.say(imp.pRow()+0,4,Funcoes.adicEspacosDireita(dl.SetTipo(rs.getInt("TSaida")),8));
		         else
		            imp.say(imp.pRow()+0,3,rs.getString("descricao"));     
		      
		         imp.say(imp.pRow()+0,2+salto, rs.getString("nome"));
		         imp.say(imp.pRow()+0,2, Funcoes.strZero(rs.getString("NCheque"),8));
		         imp.say(imp.pRow()+0,3,"R$ "+Funcoes.adicEspacosDireita(rs.getString("valor"),10));
		         imp.say(imp.pRow()+0,2,"| "+rs.getString("banco") );
		         if (imp.pRow()>=linPag) {
		            imp.incPags();
		            imp.eject();
		         }
		      }
		      
		      imp.say(imp.pRow()+1,0,""+imp.normal());
		      imp.say(imp.pRow()+0,0,Funcoes.replicate("=",135+larg));
		      
		      if (dl.getSValor().equals("P")){
		      imp.say(imp.pRow()+1,96-(salto*2+(salto/4)),"  Total --->");
		      imp.say(imp.pRow()+0,1,"    R$ "+Funcoes.strDecimalToStrCurrency(1,
					2, (total) + "") );}
		      
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
