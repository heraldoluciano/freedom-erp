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
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import javax.swing.ImageIcon;
import org.freedom.acao.TabelaSelEvent;
import org.freedom.acao.TabelaSelListener;
import org.freedom.bmps.Icone;
import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.DLF3;

public class DLBuscaEstoq extends DLF3 implements TabelaSelListener {
   private String sSQL = "";   
   private ListaCampos lcItens = null;
   private ListaCampos lcAlmox = null;
   private ListaCampos lcProd = null;
   Integer iCodAlmox = new Integer(0);
   boolean bRet = false;
   private ImageIcon imgBaixa = Icone.novo("clPago.gif");
   private ImageIcon imgVisualiza = Icone.novo("clVencido.gif");
   private ImageIcon imgPadrao = Icone.novo("clPagoParcial.gif");
   private ImageIcon imgColuna = null;
   public int iPadrao = 0;
   Vector vLinhasProibidas = new Vector();
   public DLBuscaEstoq(ListaCampos lc1, ListaCampos lc2, ListaCampos lc3, Component cOrig,Connection con,String sCol) {
   	 super(cOrig);

   	 imgBaixa.setDescription("S");
   	 imgVisualiza.setDescription("N");
   	 imgPadrao.setDescription("P");
   	 
     setAtribos( 575, 260);
   	 
     lcItens = lc1;
   	 lcAlmox = lc2;
   	 lcProd = lc3;
   	 
   	 setConexao(con);
   	 
   	 tab.adicColuna("Cd.Filial");
     tab.adicColuna("Nome da filial");    
     tab.adicColuna("Cd.Almox.");
   	 tab.adicColuna("Nome Almoxarifado");   	  
   	 tab.adicColuna("Saldo");   	
   	 tab.adicColuna("");
   	 tab.setTamColuna(60,0);
   	 tab.setTamColuna(160,1); 
   	 tab.setTamColuna(60,2);
   	 tab.setTamColuna(160,3);
   	 tab.setTamColuna(90,4);   	 	 
   	 tab.setTamColuna(20,5);
   	 tab.addTabelaSelListener(this); 

  	 setTitulo("Saldo do produto nos almoxarifados");
 	 tab.addKeyListener(this);
   }
   public int getLinhaPadrao(){
   	return iPadrao;
   }
   public Object getValor() {
	if(lcAlmox!=null) {   	 		 
		if (lcAlmox.getCampo("codalmox")!=null && this.OK)
			if (iCodAlmox!=null) 			
				lcAlmox.getCampo("codalmox").setVlrInteger(iCodAlmox);
		}
		else {
		  	System.out.println("Lista Campos nulo no busca Estoq!!!!");
		}
	bRet = false;
	iCodAlmox = null;
   	return oRetVal;
  }
   public boolean setValor(Object oVal,String sTipo) { 
     bRet = false;

     
      sSQL = "SELECT A.CODFILIAL,F.NOMEFILIAL,A.CODALMOX,A.DESCALMOX, "+
      		"(SELECT SP.SLDLIQPROD FROM EQSALDOPROD SP WHERE "+
      		"SP.CODEMPAX=A.CODEMP AND SP.CODFILIALAX=A.CODFILIAL AND "+
      		 "SP.CODALMOX=A.CODALMOX AND "+
      		 "SP.CODEMP=? AND SP.CODFILIAL=? AND SP.CODPROD=?), "+
      		"(SELECT AF.BAIXAESTOQAF FROM EQALMOXFILIAL AF "+
      		"WHERE AF.CODEMP=A.CODEMP AND AF.CODFILIAL=A.CODFILIAL AND "+
      		"AF.CODALMOX=A.CODALMOX AND AF.CODEMPAF=F.CODEMP AND "+
      		"AF.CODFILIALAF=F.CODFILIAL), PD.CODALMOX "+
      		"FROM EQPRODUTO PD, EQALMOX A, SGEMPRESA E, SGFILIAL F " +
      		"WHERE PD.CODEMP=A.CODEMP AND PD.CODFILIAL=A.CODFILIAL AND PD.CODPROD=? AND " +
      		"E.CODEMP=A.CODEMP AND A.CODEMP=? AND A.CODFILIAL=? AND "+
      		 "F.CODEMP=A.CODEMP AND F.CODFILIAL=A.CODFILIAL AND "+
      		 "(E.MULTIALMOXEMP='N' OR EXISTS(SELECT CODALMOX FROM EQALMOXFILIAL AF "+ 
      		 "WHERE AF.CODEMP=A.CODEMP AND AF.CODFILIAL=A.CODFILIAL "+
      		"AND AF.CODALMOX=A.CODALMOX AND AF.CODEMPAF=? AND AF.CODFILIALAF=?))";
;
  
      try {
      	PreparedStatement ps = con.prepareStatement(sSQL);

      	ps.setInt(1,lcProd.getCodEmp());
      	ps.setInt(2,lcProd.getCodFilial());
      	ps.setInt(3,(lcProd.getCampo("codprod").getVlrInteger()).intValue());
      	ps.setInt(4,(lcProd.getCampo("codprod").getVlrInteger()).intValue());
      	ps.setInt(5,Aplicativo.iCodEmp);
      	ps.setInt(6,Aplicativo.iCodFilial);
      	ps.setInt(7,Aplicativo.iCodEmp);
      	ps.setInt(8,Aplicativo.iCodFilial);

        tab.limpa();

      	ResultSet rs = ps.executeQuery();
      	int iCont = 0;
      	while (rs.next()) {
      		if (iCont ==0)
      			if(lcAlmox!=null) {   	 		  
   	 		    	if (lcAlmox.getCampo("codalmox")!=null)
   	 		    		lcAlmox.getCampo("codalmox").setVlrInteger(new Integer(rs.getInt(3)));   	 		    	      	   
      			}
      	   String sImgColuna = rs.getString(6);
      	   if (sImgColuna!=null){
      	   	if (sImgColuna.equals("S"))
      	   		imgColuna = imgBaixa;
      	   	else {
      	   		imgColuna = imgVisualiza;
      	   	}
      	   }
      	   else
      	   	  imgColuna = imgBaixa;
      	         	   
      	   if(rs.getString(3).equals(rs.getString(7))) {
      	   	 imgColuna = imgPadrao;
      	   	 iPadrao = iCont;
      	   }
      	   
      	   tab.adicLinha( new Object[] {
      	      rs.getString(1) != null ? rs.getString(1) : "",
      		  rs.getString(2) != null ? rs.getString(2).trim() : "",
      		  rs.getString(3) != null ? rs.getString(3) : "",
			  rs.getString(4) != null ? rs.getString(4).trim() : "",
			  rs.getString(5) != null ? Funcoes.strDecimalToStrCurrency(13,2,rs.getString(5)) : "",
		      imgColuna,
      	   });
      	         	   	
      	   if (iCont>0) {
      	   		bRet = true;
      	   }
      	   else
      	   		bRet = false;
      	   iCont++;
      	}
      	      	
      	rs.close();
      	ps.close();
      	if (!con.getAutoCommit())
      		con.commit();
      	
      	if((bRet) && (lcItens.getStatus() == 2)) {
      		tab.requestFocus();
      		tab.setLinhaSel(iPadrao); 
          	setVisible(true);      		
      	}
      	
      }
      catch (SQLException err) {
      	 Funcoes.mensagemErro(this,"Erro ao buscar filiais almoxarifados e saldos!\n"+err.getMessage());
      	 err.printStackTrace();
      }      
      return bRet;
   }
   
   
   public void actionPerformed(ActionEvent evt) {
      buscaValores();
   	  super.actionPerformed(evt);
   }
   public synchronized void valorAlterado(TabelaSelEvent tsevt) {       
   	try {   	
   	 	if (tsevt.getTabela() == tab) {
   	 	    buscaValores();
   	 	}   	  
   	 }   	 
   	 catch(Exception e) {
   	 	e.printStackTrace();
   	 }

    }

public void setValor(Object oVal) {}

public void keyPressed(KeyEvent kevt) {
    if ( kevt.getSource() == tab && kevt.getKeyCode() == KeyEvent.VK_ENTER) {        
      if (tab.getNumLinhas() > 0 && btOK.isEnabled()) { 
      	btOK.doClick();
      }
      else if (!btOK.isEnabled()) {
        if (tab.getLinhaSel()==tab.getNumLinhas()-1)
            tab.setLinhaSel(tab.getNumLinhas()-2);
        else
        	tab.setLinhaSel(tab.getLinhaSel()-1);
      }
    }
    else if (kevt.getKeyCode() == KeyEvent.VK_ESCAPE)
    	btCancel.doClick();

}    
public void ok(){
    iCodAlmox = new Integer(Integer.parseInt(tab.getValueAt(tab.getLinhaSel(),2).toString()));
    super.ok();
}

public void buscaValores(){
	if (tab.getNumLinhas() > 0) {
		if (bRet) {			
			if(this.isVisible()){
				if (((ImageIcon)tab.getValueAt(tab.getLinhaSel(),5)).getDescription().equals("N")) 
					btOK.setEnabled(false);
				else	
					btOK.setEnabled(true);
			}
		 }
	 }    
}



}        
