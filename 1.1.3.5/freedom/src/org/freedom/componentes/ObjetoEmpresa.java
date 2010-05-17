/**
 * @version 01/02/2001 <BR>
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
 * Objeto para guardar as informações referentes a empresa conectada.
 */

package org.freedom.componentes;

import java.io.IOException;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;

import javax.swing.ImageIcon;

import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;

public class ObjetoEmpresa {
  private HashMap hValores = new HashMap();
  public ObjetoEmpresa(Connection con){
    carregaObjeto(con);
  }
  
  private void carregaObjeto(Connection con){
    PreparedStatement ps = null;
    ResultSet rs = null;
    String sSQL = null;
    
    sSQL = "SELECT RAZEMP,FONEEMP,FAXEMP,EMAILEMP,FOTOEMP FROM SGEMPRESA WHERE CODEMP=?";
    try {
        ps = con.prepareStatement(sSQL);
        ps.setInt(1, Aplicativo.iCodEmp);
        rs = ps.executeQuery();
        
        if(rs.next()){
        	hValores.put("RAZAOEMP",rs.getString("RAZEMP"));
        	hValores.put("FONEEMP",rs.getString("FONEEMP"));
        	hValores.put("FAXEMP",rs.getString("FAXEMP"));
        	
  		  	byte[] bVals = new byte[650000]; 
  		  	Blob bVal = rs.getBlob("FotoEmp");
  		  	if (bVal != null) {
  		  		try {
  		  			bVal.getBinaryStream().read(bVals,0,bVals.length);  		  		    
  		  			hValores.put("LOGOEMP",((ImageIcon)new ImageIcon(bVals)).getImage());
  		  		}  		  		
  		  		catch(IOException err) {
  		  			Funcoes.mensagemErro(null,"Erro ao recuperar dados!\n"+err.getMessage());
  		  			err.printStackTrace();
  		  		}  		  		
  		  	}
  		  	else{
  		  		hValores.put("LOGOEMP",null);
  		  	}
        	
        }    
    }
    catch(Exception err){
      	err.printStackTrace();
    }
  }
  
  public HashMap getAll(){
  	return hValores;
  }
  
  
}