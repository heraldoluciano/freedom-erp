/**
 * @version 17/06/2005 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 * Pacote: org.freedom.componentes <BR>
 * Classe: @(#)Tabela.java <BR>
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
 * Objeto para guardar as informações necessárias para a criação e utilização de modelos de lote.
 */

package org.freedom.componentes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Vector;

import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;

public abstract class ObjetoModeloLote {
  private Vector vLabels = new Vector();
  private Vector vLabelsAdic = new Vector();
  private Vector vLabelsColunas = new Vector();
  private Vector vValores = new Vector();
  private Vector vValoresAdic = new Vector();
  private Vector vTams = new Vector();
  private Vector vTamsAdic = new Vector();
  private Vector vMascaras = new Vector();
  public static final String VLR_CODPROD = "#CODPROD#";
  public static final String VLR_DIA = "#DIA#";
  public static final String VLR_MES = "#MES#";
  public static final String VLR_ANO = "#ANO#";
  public static final String VLR_NPRODDIA = "#NPRODDIA#";
  
  private String sTexto = "";

  public ObjetoModeloLote() { 

  }
  
  public void adicOpcao(String sLabel,String sValor,Integer iTam){
      vLabels.addElement(sLabel);
      vValores.addElement(sValor);
      vTams.addElement(iTam);
  }

/**
 * @return Returns the vLabels.
 */
public Vector getLabels() {
    return vLabels;
}
/**
 * @return Returns the vLabels.
 */
public Vector getLabelsAdic() {
    return vLabelsAdic;
}
/**
 * @return Returns the vTams.
 */
public Vector getTams() {
    return vTams;
}
/**
 * @return Returns the vTamsAdic.
 */
public Vector getTamsAdic() {
    return vTamsAdic;
}

/**
 * @return Returns the vValores.
 */
public Vector getValores() {
    return vValores;
}
/**
 * @return Returns the vValoresAdic.
 */
public Vector getValoresAdic() {
    return vValoresAdic;
}
/**
 * @return Returns the vMascaras.
 */
public Vector getMascaras() {
    return vMascaras;
}
public Vector getLabelsColunas() {
    return vLabelsColunas;
}
public void setTexto(String sTexto){
    this.sTexto = sTexto;
    getAdic();    
}

public void getAdic(){
        vTamsAdic = new Vector();
        vLabelsAdic = new Vector ();
        vValoresAdic = new Vector();   

        for(int i2=0;vValores.size()>i2;i2++) {
            if((sTexto.indexOf(vValores.elementAt(i2).toString()))>(-1)){
                vTamsAdic.addElement(vTams.elementAt(i2).toString());
                vLabelsAdic.addElement(vLabels.elementAt(i2).toString());
                vValoresAdic.addElement(vValores.elementAt(i2).toString());
            }                                 
        }       
}


public String getLote(Integer iCodProd,Date dData,Connection con){
	String sRetorno = "";	
	sRetorno = sTexto;
	GregorianCalendar cal = new GregorianCalendar();
	cal.setTime(dData);
	    try {
  	        Vector vTamsAdic = getTamsAdic();
  	        Vector vValAdic = getValoresAdic();
  	        if (sRetorno != null) { 
  	            try {			    	    
  	                for(int i=0;vValAdic.size()>i;i++) {
  	                    String sValAdic = vValoresAdic.elementAt(i).toString();
  	                    String sFragmento = sRetorno.substring(sRetorno.indexOf("["+sValAdic));
  	                    String sCampo = "";
  	                    sFragmento = sFragmento.substring(0,("\\"+sFragmento).indexOf("]"));
  	                    int iTamAdic = Funcoes.contaChar(sFragmento,'-'); 
  	                    if(sValAdic.equals(VLR_CODPROD)){
  	                    	sCampo = iCodProd+"";
  	                    	if(sCampo.length()<iTamAdic){
  	                    		sCampo = Funcoes.strZero(sCampo,iTamAdic);
  	                    	}
  	                    	else if (sCampo.length()>iTamAdic){
  	                    		sCampo = sCampo.substring(0,iTamAdic);
  	                    	}
  	                    }
  	                    else if (sValAdic.equals(VLR_DIA)){
  	                    	sCampo = cal.get(Calendar.DAY_OF_MONTH)+"";
  	                    }	
  	                    else if (sValAdic.equals(VLR_MES)){
  	                    	sCampo = cal.get(Calendar.MONTH)+1+"";//Somei 1 pq estava retornando um a menos no mês
  	                    	if(sCampo.length()<iTamAdic){
  	                    		sCampo = Funcoes.strZero(sCampo,iTamAdic);
  	                    	}
  	                    	else if (sCampo.length()>iTamAdic){
  	                    		sCampo = sCampo.substring(0,iTamAdic);
  	                    	}
  	                    }
  	                    else if (sValAdic.equals(VLR_ANO)){
  	                    	sCampo = cal.get(Calendar.YEAR)+"";
  	                    	if(sCampo.length()>iTamAdic){
  	                    		sCampo = sCampo.substring(sCampo.length()-iTamAdic);
  	                    	}	
  	                    }	
  	                    else if (sValAdic.equals(VLR_NPRODDIA)){   	                    	
  	                    	try{
                    			String sSQL = "SELECT coalesce(count(1)+1,1) from ppop op where op.codemppd=? and op.codfilialpd=? and op.codprod=? and op.dtfabrop = ?";
                    			try {
                    				PreparedStatement ps = con.prepareStatement(sSQL);
                    				ps.setInt(1, Aplicativo.iCodEmp);
                    				ps.setInt(2, ListaCampos.getMasterFilial("PPOP"));
                    				ps.setInt(3, iCodProd.intValue());
                    				ps.setDate(4,Funcoes.dateToSQLDate(dData));
                    				ResultSet rs = ps.executeQuery();
                    				if (rs.next()) {
                    					sCampo = rs.getString(1);
                    				}
                    			}
                    		    catch (Exception err) {
                    		    	err.printStackTrace();
								}
	
  	                    	}
  	                    	catch(Exception err){
  	                    		err.printStackTrace();
  	                    	}
  	                    }
  	                    	
  	                    sRetorno = sRetorno.replaceAll("\\"+sFragmento,sCampo);	
  	              }
  	               
  	            }						
  	            catch (Exception err) {
  	            	err.printStackTrace();
  	            }
  	        }
  	        
  	    }
	    catch(Exception err) {
	    	err.printStackTrace();
	    }
	return sRetorno;
}
}