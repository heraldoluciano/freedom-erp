/**
 * @version 19/12/2003 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)FRBoleto.java <BR>
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

package org.freedom.modulos.std;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;


import javax.swing.JLabel;

import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.ImprimeOS;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FRelatorio;

public class FREtiqueta extends FRelatorio {
  private JTextFieldPad txtCodModEtiq = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldFK txtDescModEtiq = new JTextFieldFK(JTextFieldPad.TP_STRING,50,0);
  private JTextFieldPad txtCodSetor = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0); 
  private JTextFieldFK txtDescSetor = new JTextFieldFK(JTextFieldPad.TP_STRING,50,0);
  private JTextFieldPad txtCodTipo = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0); 
  private JTextFieldFK txtDescTipo = new JTextFieldFK(JTextFieldPad.TP_STRING,50,0);
  private JTextFieldPad txtCodpapel = new JTextFieldPad(JTextFieldPad.TP_INTEGER,10,0);  
  private JTextFieldFK txtDescpapel = new JTextFieldFK(JTextFieldPad.TP_STRING,50,0); 
  private ListaCampos lcModEtiq = new ListaCampos(this);
  private ListaCampos lcSetor = new ListaCampos(this);
  private ListaCampos lcTipo = new ListaCampos(this);
  private Connection con = null;
  public FREtiqueta() {
     setTitulo("Impressão de etiquetas");
     setAtribos(80,80,480,240);
               
     lcModEtiq.add(new GuardaCampo( txtCodModEtiq, 7, 100, 80, 20, "CodModEtiq", "C.ModEtiq", true, false, null, JTextFieldPad.TP_INTEGER,true),"txtCodConta");
     lcModEtiq.add(new GuardaCampo( txtDescModEtiq, 90, 100, 207, 20, "DescModEtiq", "Descrição", false, false, null, JTextFieldPad.TP_STRING,false),"txtDescConta");
     lcModEtiq.setReadOnly(true);
     lcModEtiq.montaSql(false, "MODETIQUETA", "SG");
     txtCodModEtiq.setTabelaExterna(lcModEtiq);
     txtCodModEtiq.setFK(true);
     txtCodModEtiq.setNomeCampo("CodModEtiq");
    
     lcSetor.add(new GuardaCampo( txtCodSetor, 7, 100, 80, 20, "CodSetor", "C.Setor", true, false, null, JTextFieldPad.TP_INTEGER,false),"txtCodConta");
     lcSetor.add(new GuardaCampo( txtDescSetor, 90, 100, 207, 20, "DescSetor", "Descrição", false, false, null, JTextFieldPad.TP_STRING,false),"txtDescConta");
     lcSetor.setReadOnly(true);
     lcSetor.montaSql(false, "SETOR", "VD");
     txtCodSetor.setTabelaExterna(lcSetor);
     txtCodSetor.setFK(true);
     txtCodSetor.setNomeCampo("CodSetor");
     
     lcTipo.add(new GuardaCampo( txtCodTipo, 7, 100, 80, 20, "CodTipoCli", "C.Tipo", true, false, null, JTextFieldPad.TP_INTEGER,false),"txtCodConta");
     lcTipo.add(new GuardaCampo( txtDescTipo, 90, 100, 207, 20, "DescTipoCli", "Descrição", false, false, null, JTextFieldPad.TP_STRING,false),"txtDescConta");
     lcTipo.setReadOnly(true);
     lcTipo.montaSql(false, "TIPOCLI", "VD");
     txtCodTipo.setTabelaExterna(lcTipo);
     txtCodTipo.setFK(true);
     txtCodTipo.setNomeCampo("CodTipoCli");
              
     adic(new JLabel("Código e descrição do setor"),7,5,280,20);
     adic(txtCodSetor,7,25,80,20);
     adic(txtDescSetor,90,25,200,20);
     adic(new JLabel("Código e descrição do tipo de cliente"),7,45,280,20);
     adic(txtCodTipo,7,65,80,20);
     adic(txtDescTipo,90,65,200,20);
     adic(new JLabel("Código e descrição do modelo"),7,85,280,20);
     adic(txtCodModEtiq,7,105,80,20);
     adic(txtDescModEtiq,90,105,200,20);
  }   
  
  public void setConexao(Connection cn) {
    con = cn;
    lcModEtiq.setConexao(cn);
    lcSetor.setConexao(cn);
    lcTipo.setConexao(cn);
  }

  public void imprimir(boolean bVisualizar) {
	PreparedStatement ps = null;
	ResultSet rs = null;
	int iNColModEtiq = 0;
	String sTxa = "";
	String sSQL = "";
	String sWhere = "";
	String sSep = "";
	String sLinha = "";
	Vector vVal = null;
	Vector vCol = null;
	ImprimeOS imp = null;
    try {
  	    if (txtCodModEtiq.getVlrString().equals("")) {
  	       Funcoes.mensagemInforma(this,"Código do modelo em branco!");
  	       return;
  	    }
  	    imp = new ImprimeOS("",con);
  	    imp.verifLinPag();
  	    imp.setTitulo("Etiquetas");
  	  	     	  
  	    sSQL = "SELECT TXAMODETIQ,NCOLMODETIQ FROM SGMODETIQUETA" +
  	      " WHERE CODEMP=? AND CODFILIAL=? AND CODMODETIQ=?";
  	    
  	    try {
  	    	ps = con.prepareStatement(sSQL);
  	    	ps.setInt(1,Aplicativo.iCodEmp);
  	    	ps.setInt(2,lcModEtiq.getCodFilial());
  	    	ps.setInt(3,txtCodModEtiq.getVlrInteger().intValue());
  	    	rs = ps.executeQuery();
  	    	if (rs.next()) { 
  	    		sTxa = rs.getString("TXAMODETIQ");
  	    		iNColModEtiq = rs.getInt("NCOLMODETIQ");
  	    	}
  	    	rs.close();
  	    	ps.close();
  	    	if (!con.getAutoCommit()) 
  	    		con.commit();
  	    }
  	    catch (SQLException e) {
  	    	Funcoes.mensagemErro(this,"Erro carregando modelo de etiqueta!\n"+e.getMessage());
  	    	return;
  	    }
  	    if (sTxa!=null) {
  	    	sWhere = "";
  	    	sSep = " WHERE ";
  	    	if (!txtCodSetor.getVlrString().equals("")) {
  	    		sWhere = sSep+" C.CODSETOR="+txtCodSetor.getVlrInteger().intValue();
  	    		sWhere += " AND C.CODEMPSR="+Aplicativo.iCodEmp;
  	    		sWhere += " AND C.CODFILIALSR="+lcSetor.getCodFilial();
  	    		sSep = " AND ";
  	    	}
  	    	if (!txtCodTipo.getVlrString().equals("")) {
  	    		sWhere += sSep+" CODTIPOCLI="+txtCodTipo.getVlrInteger().intValue();
  	    		sWhere += " AND C.CODEMPTC="+Aplicativo.iCodEmp;
  	    		sWhere += " AND C.CODFILIALTC="+lcTipo.getCodFilial();
  	    	}
  	    
  	     //   sWhere = " WHERE C.CODCLI IN (1741,1172,780)"; // tirar depois este clientes
  	          sWhere = " WHERE C.CODCLI IN (1741,1172)"; // tirar depois este clientes
    	    
  	    	sSQL = "SELECT C.CODCLI,C.RAZCLI,C.NOMECLI,C.CPFCLI,C.CNPJCLI,C.ENDCLI,C.NUMCLI," +
  	                "C.COMPLCLI,C.CEPCLI,C.BAIRCLI,C.CIDCLI,C.UFCLI FROM VDCLIENTE C" +sWhere;
  	    	try {
  	    		ps = con.prepareStatement(sSQL);
  	    		rs = ps.executeQuery();
  	    		vCol = new Vector();
  	    		while ( rs.next() ) {
  	    			vVal = aplicCampos(rs,sTxa); 
  	    			if (vVal != null){ 
                		vCol.addElement(vVal);
                	}
  	    			if (vCol.size()==iNColModEtiq) {
  	    				impCol(imp,vVal,vCol);
  	    				vCol = new Vector();
  	    			}
  	    			
/*	    				String[] sLinhas = sVal.split("\n"); */

  	    		}
  	    		if (vCol.size()<iNColModEtiq) {
    				impCol(imp,vVal,vCol);
  	    		}
  	    		rs.close();
  	    		ps.close();
  	    		if (!con.getAutoCommit()) 
  	    			con.commit();
  	    		
  	    	}
  	    	catch ( SQLException err ) {
  	    		Funcoes.mensagemErro(this,"Erro ao consultar informãoes!"+err.getMessage());
  	    		err.printStackTrace();      
  	    	}
  	    	imp.eject();
  	    	imp.fechaGravacao();
  	    	if (bVisualizar) {
  	    		imp.preview(this);
  	    	}
  	    	else {
  	    		imp.print();
  	    	}
  	    }
  	}
  	finally {
  		ps = null;
  		rs = null;
  		sSQL = null;
  		sWhere = null;
  		sSep = null;
  		vVal = null;
  		sLinha = null;
  	}
	
  }
  private void impCol(ImprimeOS imp, Vector vVal, Vector vCol) {
  	Vector vLinha = new Vector();
  	String Aux, Aux1;// TESTE
  	int Tam = 0; //teste	
  	  	try {
  		for (int i=0; i<vCol.size(); i++) {
  			vVal = (Vector) vCol.elementAt(i);
  			for (int i2=0; i2<vVal.size();i2++) {
  			   if (vLinha.size()<=i2){ 
			     	Aux = String.valueOf(vVal.elementAt(i2));// TESTE
		  			if (Aux.length()> 33){// TESTE
		  			    Tam = (Aux.length()- 33); // TESTE
		  				Tam =(Aux.length()- (Tam + 1));
		  			    Aux = Tiracp(Aux,Tam);// TESTE
                        vVal.setElementAt(Aux,i2); // TESTE 
				  		//vVal.setElementAt(Aux,i2); // TESTE 
			  		    //Aux1 = Funcoes.trimFinal(Aux);// TESTE
		  			   // vVal.setElementAt(Aux1,i2); 
		  			    }// teste
		  			else{// teste
		  			  	 Tam = (33  - Aux.length()); // TESTE
		  			  	 Tam = Tam + Aux.length(); // teste
					     Aux1 = Funcoes.adicionaEspacos(Aux,Tam);// TESTE
					     vVal.setElementAt(Aux1,i2); // TESTE 
					     Tam = 0; // TESTE
		  			     }// TESTE
					  vLinha.addElement((String) vVal.elementAt(i2));
			    }// teste
  				else 
  					vLinha.setElementAt( vLinha.elementAt(i2)+""+vVal.elementAt(i2),i2);   
  			   }
  		}
			
  		for(int i=0;i<vLinha.size();i++) {
  			imp.say(imp.pRow()+1,0,vLinha.elementAt(i).toString());
  		}
  	}
  	finally {
  		vLinha = null;
  	}
  	
  }
private Vector aplicCampos(ResultSet rs, String sTxa ) {
  	String sCampo = "";
  	Vector vRet = null;
  	try {
// Estes '\\' que  aparecem por ai..são para anular caracteres especiais de "expressão regular".
		if (sTxa != null) {
			try {
						
		    	try {	
			   	     sCampo = Funcoes.copy(rs.getString("CodCli"),0,8);
				     sTxa = sTxa.replaceAll("\\[CODCLI]",sCampo); 
				
				     sCampo = Funcoes.copy(rs.getString("RazCli"),0,50);
				     sTxa = sTxa.replaceAll("\\[_____________RAZAO____DO____CLIENTE_____________]",Funcoes.copy(sCampo,0,50)); // LOM
				
				     sCampo = Funcoes.copy(rs.getString("NomeCli"),0,50 ); 
				     sTxa = sTxa.replaceAll("\\[_____________NOME_____DO____CLIENTE_____________]",Funcoes.copy(sCampo,0,50)); // LOM
				
				     sCampo = Funcoes.copy(rs.getString("CpfCli"),11); 
				     sCampo = Funcoes.setMascara(sCampo,"###.###.###-##");
				     sTxa = sTxa.replaceAll("\\[CPF/CNPJ_ CLIENT]",sCampo); 
				
				     sCampo = Funcoes.copy(rs.getString("CnpjCli"),14); 
				     sCampo = Funcoes.setMascara(sCampo,"###.###.###-##");
				     sTxa = sTxa.replaceAll("\\[CPF/CNPJ_ CLIENT]",sCampo); 
				
				     sCampo= Funcoes.copy(rs.getString("EndCli"),0,50);
				     sTxa = sTxa.replaceAll("\\[____________ENDERECO____DO____CLIENTE___________]",sCampo); 
			         }
				
			catch(Exception e) {
			    	System.out.println("End: "+sCampo);
				}
					
			sCampo = Funcoes.copy(rs.getString("NumCli"),0,8); 
			sTxa = sTxa.replaceAll("\\[NUMERO]",sCampo); 
			
			sCampo = Funcoes.copy(rs.getString("ComplCli"),0,30); 
			sTxa = sTxa.replaceAll("\\[____COMPLEMENTO___]",sCampo); 
			
			sCampo = Funcoes.copy(rs.getString("CepCli"),8); 
			sCampo = Funcoes.setMascara(sCampo,"#####-###");
			sTxa = sTxa.replaceAll("\\[__CEP__]",sCampo); 
			
			sCampo = Funcoes.copy(rs.getString("BairCli"),0,30); 
			sTxa = sTxa.replaceAll("\\[___________BAIRRO___________]",sCampo); 
			
			sCampo = Funcoes.copy(rs.getString("CidCli"),0,30); 
			sTxa = sTxa.replaceAll("\\[___________CIDADE___________]",sCampo); 
		
			sCampo = Funcoes.copy(rs.getString("UfCli"),0,2); 
			sTxa = sTxa.replaceAll("\\[UF]",Funcoes.copy(sCampo,0,2)); 
			}
			catch (SQLException e) {
				Funcoes.mensagemErro(this,"Erro na troca de dados!\n"+e.getMessage());
			}
  		}
		vRet = Funcoes.stringToVector(sTxa);
       	}
  	finally {
  		sCampo = null;
  	}
  	return vRet;
  }
public static String Tiracp(String sVal, int Tam) {// teste
	 char[] cVal = sVal.toCharArray();// teste
	    String sRetorno = sVal;// teste
	    for (int i=sVal.length()-1;i>=0;i--) {// teste
	      if (i <= Tam) {// teste
	      	sRetorno = sVal.substring(0,i+1); // teste
	        break;// teste
	      }// teste
	    }// teste
	    return  sRetorno;// teste
	  }
}
