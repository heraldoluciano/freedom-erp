/**
 * @version 27/06/2003 <BR>
 * @author Setpoint Informática Ltda./Robson Sanchez <BR>
 *
 * Projeto: Freeedom <BR>
 *  
 * Pacote: org.freedom.modulos.cfg <BR>
 * Classe: @(#)DLAtualiza.java <BR>
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

package org.freedom.modulos.cfg;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import org.freedom.funcoes.Funcoes;
import org.freedom.telas.FFDialogo;


public class DLAtualiza extends FFDialogo {
   private Connection con = null;
   
   public DLAtualiza(Component cOrig) {
   	 super(cOrig);
	 setTitulo("Atualização do sistema");
	 setAtribos(450, 300);
   
   }
   public void setConexao(Connection cn) {
	 con = cn;
   }   

   public void actionPerformed(ActionEvent evt) {
	   if (evt.getSource() == btOK) {
	   	  try {
	   	  	 String sColumns = "";
	   	     Statement stmt = con.createStatement();
		     ResultSet rs = stmt.executeQuery("SELECT * FROM VDTIPOCLI");
			 ResultSetMetaData rsmd = rs.getMetaData();
			 int numberOfColumns = rsmd.getColumnCount();
			 for (int i=1; i<=numberOfColumns; i++) {
			 	sColumns = sColumns + rsmd.getColumnName(i) +", ";
			 }
			 Funcoes.mensagemErro(this,"Núm. de cols.: "+numberOfColumns);
			 Funcoes.mensagemErro(this,"Colunas: \n"+sColumns);
			 //boolean b = rsmd.isSearchable(1);
	   	  }
	   	  catch (SQLException e) {
	   	  	 Funcoes.mensagemErro(this,"Erro: "+e.getMessage());
	   	  }
	   	  
	   }
	   super.actionPerformed(evt);
   }
}