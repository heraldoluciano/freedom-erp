/**
 * @version 23/02/2004 <BR>
 * @author Setpoint Informática Ltda./Anerson Sanchez <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)DLBuscaEstoq.java <BR>
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
 * Tela para busca de saldos de estoque em vários almoxarifados.
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

public class DLBuscaEstoq extends DLF3 implements TabelaSelListener {
   private String sCol = null;
   private Vector vValsProd = new Vector();
   private String sSQL = "";   
   private ListaCampos lcCampos = null;
   public DLBuscaEstoq(ListaCampos lc, Component cOrig,Connection con,String sCol) {
   	 super(cOrig);
   	 this.sCol = sCol;
   	 lcCampos = lc;
   	 setConexao(con);
   	 
   	 tab.adicColuna("Cd.Filial");
     tab.adicColuna("Nome da filial");    
     tab.adicColuna("Cd.Almox.");
   	 tab.adicColuna("Nome Almoxarifado");   	  
   	 tab.adicColuna("Saldo");
   	 tab.setTamColuna(60,0);
   	 tab.setTamColuna(150,1); 
   	 tab.setTamColuna(60,1);
   	 tab.setTamColuna(150,2);
   	 tab.setTamColuna(80,5);   	 	 
   	 tab.addTabelaSelListener(this); 

  	 setTitulo("Saldo do produto nos almoxarifados");
  	  
   }
   public Object getValor() {
    return oRetVal;
  }
   public boolean setValor(Object oVal,String sTipo) { 
     boolean bRet = false;
   	  sSQL = "SELECT A.CODFILIAL,F.NOMEFILIAL,A.CODALMOX,A.DESCALMOX FROM EQALMOX A, SGEMPRESA E, SGFILIAL F " +
   	  		 "WHERE E.CODEMP=A.CODEMP AND A.CODEMP=? AND A.CODFILIAL=? AND F.CODEMP=A.CODEMP AND F.CODFILIAL=A.CODFILIAL " +
   	  		 "AND (E.MULTIALMOXEMP='N' OR " +
   	  		 " EXISTS(SELECT CODALMOX FROM EQALMOXFILIAL AF WHERE AF.CODEMP=A.CODEMP AND AF.CODFILIAL=A.CODFILIAL " +
   	  		 " AND AF.CODALMOX=A.CODALMOX AND AF.CODEMPFL=? AND AF.CODFILIALAF=?))";
      System.out.println(sSQL);
      try {
      	PreparedStatement ps = con.prepareStatement(sSQL);
      	String sVal = oVal.toString();
      	ps.setInt(1,Aplicativo.iCodEmp);
      	ps.setInt(2,Aplicativo.iCodFilial);
      	ps.setInt(3,Aplicativo.iCodEmp);
      	ps.setInt(4,Aplicativo.iCodFilial);

        tab.limpa();
        tab.removeAll();
      	vValsProd.clear();

      	ResultSet rs = ps.executeQuery();
      	while (rs.next()) {
      	   bRet = true;
      	   tab.adicLinha( new Object[] {
      	      rs.getString(1) != null ? rs.getString(1) : "",
      		  rs.getString(2) != null ? rs.getString(2) : "",
      		  rs.getString(3) != null ? rs.getString(3) : "",
			  rs.getString(4) != null ? rs.getString(4) : "",
		      rs.getString(5) != null ? rs.getString(4) : "",
      	   });
/*
      	   if (sCol.toUpperCase().equals("REFPROD")) {
      	   	 oRetVal = rs.getString(1) != null ? rs.getString(1) : ""; 
   	 	   }
   	 	   else{
   	 		 oRetVal = rs.getString(2) != null ? rs.getString(2) : "";
   	 	   }
*/ 
      	}
      	rs.close();
      	ps.close();
      	if (!con.getAutoCommit())
      		con.commit();
      }
      catch (SQLException err) {
      	 Funcoes.mensagemErro(this,"Erro ao buscar filiais almoxarifados e saldos!\n"+err.getMessage());
      	 err.printStackTrace();
      }
      return bRet;
   }
   public void actionPerformed(ActionEvent evt) {
   	  super.actionPerformed(evt);
   }
   public void valorAlterado(TabelaSelEvent tsevt) {
   	 try {   	
   	 	if (tsevt.getTabela() == tab) {
   	 		if (tab.getNumLinhas() > 0) {
   	 		    Integer iCodEmpAx = new Integer(lcCampos.getCodEmp());   	 		    
   	 		    Integer iCodFilialAx = new Integer(Integer.parseInt(tab.getValueAt(tab.getLinhaSel(),0).toString()));
   	 		    Integer iCodAlmoxAx = new Integer(Integer.parseInt(tab.getValueAt(tab.getLinhaSel(),2).toString()));
   	 			lcCampos.getCampo("CODEMPAX").setVlrInteger(iCodEmpAx);
   	 			lcCampos.getCampo("CODFILIALAX").setVlrInteger(iCodFilialAx);
   	 		    lcCampos.getCampo("CODALMOX").setVlrInteger(iCodAlmoxAx);
   	 		}
       }   	  
   	 }
   	 catch(Exception e) {
   	 	e.printStackTrace();
   	 }
    }

public void setValor(Object oVal) {
	
}
};        
