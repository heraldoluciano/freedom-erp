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
import java.sql.Types;
import java.util.Vector;

import org.freedom.acao.TabelaSelEvent;
import org.freedom.acao.TabelaSelListener;
import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.StringDireita;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.DLF3;

public class DLBuscaProd extends DLF3 implements TabelaSelListener {
   private Connection con = null;
   private String sCol = null;
   private Vector vValsProd = new Vector();
   private ListaCampos lcProd = new ListaCampos(this,"PD");
   private JTextFieldPad txtTmp = new JTextFieldPad(JTextFieldPad.TP_INTEGER,10,0);

   public DLBuscaProd(Component cOrig,Connection con,String sCol) {
   	 super(cOrig);
   	 this.sCol = sCol;
   	 this.con = con;
   	 
   	 lcProd.add(new GuardaCampo( txtCod, sCol, "Código", ListaCampos.DB_PK, false));
   	 lcProd.add(new GuardaCampo( txtDesc, "DescProd", "Descrição do produto", ListaCampos.DB_SI,false));
   	 lcProd.add(new GuardaCampo( txtTmp, "CodProd", "Cód.prod.", ListaCampos.DB_SI,false));
   	 txtCod.setTabelaExterna(lcProd);
   	 txtCod.setNomeCampo(sCol);
   	 txtCod.setFK(true);
   	 lcProd.setReadOnly(true);
   	 lcProd.montaSql(false, "PRODUTO", "EQ");
   	 lcProd.setConexao(con);
   	 
   	 tab.adicColuna("Cod.Forn.");
   	 tab.adicColuna("Fornecedor");
   	 tab.adicColuna("Custo");
   
   	 tab.setTamColuna(30,0);//orig....herdado
   	 tab.setTamColuna(100,1);//cod.sim...herdado
   	 tab.setTamColuna(65,2);
   	 tab.setTamColuna(250,3);
   	 tab.setTamColuna(80,4);
   	 
   	 tab.addTabelaSelListener(this);
   }
   public void setValor(Object oVal) {
   	  Vector vSims = new Vector();
      String sSQL = "SELECT FIRST 100 CORIG,SSIM,ICODFOR,SRAZFOR,NPRECO,SRET,IRET " +
      		                " FROM EQBUSCASIMILARSP(?,?,?,?)";
      try {
      	PreparedStatement ps = con.prepareStatement(sSQL);
      	String sVal = oVal.toString();
      	ps.setInt(1,Aplicativo.iCodEmp);
      	ps.setInt(2,ListaCampos.getMasterFilial("EQSIMILAR"));
      	if (sCol.toUpperCase().equals("REFPROD")) {
      	  ps.setNull(3,Types.INTEGER);
      	  ps.setString(4,sVal);
      	}
      	else {
      	  ps.setInt(3,Integer.parseInt(oVal.toString()));
      	  ps.setNull(4,Types.CHAR);
      	}
      	tab.limpa();
      	vValsProd.clear();
      	vSims.clear();
      	lcProd.limpaCampos(true);
      	ResultSet rs = ps.executeQuery();
      	while (rs.next()) {
      	   String sSim = rs.getString(2) != null ? rs.getString(2) : "";
      	   for(int i=0;i<vSims.size();i++)
      	   	  if (vSims.elementAt(i).equals(sSim))
      	   	  	break;
      	   if (!sSim.equals(""))
      	      vSims.add(sSim);
      	   vValsProd.addElement(rs.getString(6));
      	   tab.adicLinha( new Object[] {
      	      rs.getString(1) != null ? rs.getString(1) : "",
      		  rs.getString(2) != null ? rs.getString(2) : "",
      		  rs.getString(3) != null ? rs.getString(3) : "",
			  rs.getString(4) != null ? rs.getString(4) : "",
			  new StringDireita(rs.getString(5) != null ? Funcoes.strDecimalToStrCurrency(2,rs.getString(5)) : "")
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
   private void gravaProd() {
   	  String sSQL = "UPDATE CPTABFOR SET CODEMPPD=?,CODFILIALPD=?," +
   	  		                "CODPROD=? WHERE CODEMP=? AND CODFILIAL=?" +
   	  		                " AND CODFOR=? AND REFPRODTFOR=?";
   	  try {
   	  	PreparedStatement ps = con.prepareStatement(sSQL);
   	  	ps.setInt(1,Aplicativo.iCodEmp);
   	  	ps.setInt(2,lcProd.getCodFilial());
   	  	ps.setString(3,txtTmp.getVlrString());
   	  	ps.setInt(4,Aplicativo.iCodEmp);
   	  	ps.setInt(5,ListaCampos.getMasterFilial("CPFORNECED"));
   	  	ps.setString(6,tab.getValor(tab.getLinhaSel(),2)+"");
   	  	ps.setString(7,tab.getValor(tab.getLinhaSel(),1)+"");
   	  	ps.executeUpdate();
   	  	ps.close();
   	  	if (!con.getAutoCommit())
   	  		con.commit();
   	  }
   	  catch (SQLException err) {
   	  	 Funcoes.mensagemErro(this,"Erro ao gravar produto na tabela de fornecedores!\n"+err.getMessage());
   	  	 err.printStackTrace();
   	  }
   }
   public void actionPerformed(ActionEvent evt) {
   	  if (evt.getSource() == btSalvar) {
   	  	 if (!txtCod.getVlrString().equals("")) {
   	  	   gravaProd();
   	  	   btSalvar.setEnabled(false);
   	  	 }
   	  }
   	  super.actionPerformed(evt);
   }
   public void valorAlterado(TabelaSelEvent tsevt) {
   	  if (tsevt.getTabela() == tab) {
   	  	if (tab.getNumLinhas() > 0 && vValsProd.size() > tab.getLinhaSel()) {
   	  	  if (vValsProd.elementAt(tab.getLinhaSel()) == null) {
   	  	  	 txtCod.setAtivo(true);
   	  	  	 btSalvar.setEnabled(true);
   	  	  	 lcProd.limpaCampos(true);
   	  	  }
   	  	  else {
   	  	  	btSalvar.setEnabled(false);
   	  	  	txtCod.setAtivo(false);
   	  	  	txtCod.setVlrString(vValsProd.elementAt(tab.getLinhaSel())+"");
   	  	  }
   		  lcProd.carregaDados();
   	  	}
   	  }
   }
};        
