/**
 * @version 23/02/2004 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)DLBuscaProd.java <BR>
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
 */

package org.freedom.modulos.std;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import org.freedom.acao.TabelaSelEvent;
import org.freedom.acao.TabelaSelListener;
import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.DLF3;

public class DLBuscaProd extends DLF3 implements TabelaSelListener {
   private String sCol = null;
   private Vector vValsProd = new Vector();

   public DLBuscaProd(Component cOrig,Connection con,String sCol) {
   	 super(cOrig);
   	 this.sCol = sCol;
   	 setConexao(con);
   	 
   	 tab.adicColuna("Descrição");
   	 tab.adicColuna("Saldo");   	  
   	 tab.setTamColuna(120,0);//código.
   	 tab.setTamColuna(100,1);//Referencia.
   	 tab.setTamColuna(200,2);
   	 tab.setTamColuna(80,5);
   	 
   	 tab.addTabelaSelListener(this);
   }
   public void setValor(Object oVal) {

      String sSQL = "SELECT ALT.CODPROD,ALT.REFPROD,PROD.DESCPROD,PROD.SLDPROD FROM eqcodaltprod ALT, EQPRODUTO PROD "+
		" WHERE PROD.CODEMP = ALT.CODEMP AND PROD.CODFILIAL=ALT.codfilial AND PROD.CODPROD = ALT.CODPROD " +
		" AND ALT.CODEMP=? AND ALT.CODFILIAL = ? AND ALT.CODALTPROD = ?";
      System.out.println(sSQL);
      try {
      	PreparedStatement ps = con.prepareStatement(sSQL);
      	String sVal = oVal.toString();
     	ps.setInt(1,Aplicativo.iCodEmp);
      	ps.setInt(2,ListaCampos.getMasterFilial("EQCODALTPROD"));
      	ps.setString(3,sVal);
      	tab.limpa();
      	vValsProd.clear();

      	ResultSet rs = ps.executeQuery();
      	while (rs.next()) {
      	   tab.adicLinha( new Object[] {
      	      rs.getString(1) != null ? rs.getString(1) : "",
      		  rs.getString(2) != null ? rs.getString(2) : "",
      		  rs.getString(3) != null ? rs.getString(3) : "",
			  rs.getString(4) != null ? rs.getString(4) : "",
      	   });
      	}
      	rs.close();
      	ps.close();
      	if (!con.getAutoCommit())
      		con.commit();
      }
      catch (SQLException err) {
      	 Funcoes.mensagemErro(this,"Erro ao buscar código auxiliar!\n"+err.getMessage());
      	 err.printStackTrace();
      }
   }
   public void actionPerformed(ActionEvent evt) {
   	  super.actionPerformed(evt);
   }
   public void valorAlterado(TabelaSelEvent tsevt) {
   	  if (tsevt.getTabela() == tab) {
      	if (sCol.toUpperCase().equals("REFPROD")) {
      	   	oRetVal = tab.getValueAt(tab.getLinhaSel(),1); 
      	}
       	else {
      	   	oRetVal = tab.getValueAt(tab.getLinhaSel(),0);
       	}
   	  	if (tab.getNumLinhas() > 0 && vValsProd.size() > tab.getLinhaSel()) {
   	  	  if (vValsProd.elementAt(tab.getLinhaSel()) == null) {
//   	  	  	 txtCod.setAtivo(true);
//   	  	  	 btSalvar.setEnabled(true);
//   	  	  	 lcProd.limpaCampos(true);
   	  	  }
   	  	  else {
//   	  	  	btSalvar.setEnabled(false);
//   	  	  	txtCod.setAtivo(false);
   	  	  	//txtCod.setVlrString(vValsProd.elementAt(tab.getLinhaSel())+"");
   	  	  }
//   		  lcProd.carregaDados();
   	  	}
   	  }
   }
};        
