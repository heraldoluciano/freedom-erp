/**
 * @version 02/11/2003 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)FRVendasFisico.java <BR>
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
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.ImprimeOS;
import org.freedom.componentes.JRadioGroup;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FRelatorio;

public class FRVendasFisico extends FRelatorio {
  private JTextFieldPad txtDataini = new JTextFieldPad(); 
  private JTextFieldPad txtDatafim = new JTextFieldPad(); 
  
  private JTextFieldPad txtCodVend = new JTextFieldPad();
  private JTextFieldFK txtDescVend = new JTextFieldFK(); 
  
  private ListaCampos lcVend = new ListaCampos(this);
  
  
  
  private Connection con = null;
  private JRadioGroup rgOrdem = null;
  private JLabel lbOrdem = new JLabel("Ordenar por:");
  private Vector vLabs = new Vector();
  private Vector vVals = new Vector();
  public FRVendasFisico() {
    setTitulo("Fechamento Fisico de Vendas");
    setAtribos(80,80,295,240);
   
    txtDataini.setTipo(JTextFieldPad.TP_DATE,10,0);
    txtDatafim.setTipo(JTextFieldPad.TP_DATE,10,0);

	GregorianCalendar cPeriodo = new GregorianCalendar();
    txtDatafim.setVlrDate(cPeriodo.getTime());
	cPeriodo.set(Calendar.DAY_OF_MONTH,cPeriodo.get(Calendar.DAY_OF_MONTH)-30);
	txtDataini.setVlrDate(cPeriodo.getTime());
	JLabel lbLinha = new JLabel();
	lbLinha.setBorder(BorderFactory.createEtchedBorder());
	
	vLabs.addElement("Código");
	vLabs.addElement("Descrição");
	vVals.addElement("C");
	vVals.addElement("D");
	rgOrdem = new JRadioGroup(1,2,vLabs,vVals);
	rgOrdem.setVlrString("D");
	
	adic(new JLabel("Periodo:"),7,5,100,20);
	adic(lbLinha,60,15,210,2);
	adic(new JLabel("De:"),7,30,30,20);
	adic(txtDataini,32,30,97,20);
	adic(new JLabel("Até:"),140,30,30,20);
	adic(txtDatafim,170,30,100,20);
	adic(lbOrdem,7,105,80,15);
	adic(rgOrdem,7,125,263,30);
	
	
	    txtCodVend.setTipo(JTextFieldPad.TP_INTEGER,8,0);
	  	txtDescVend.setTipo(JTextFieldPad.TP_STRING,50,0);    

	  	lcVend.add(new GuardaCampo( txtCodVend, 7, 100, 80, 20, "CodVend", "Cód.repr.", true, false, null, JTextFieldPad.TP_INTEGER,false),"txtCodVendx");
	  	lcVend.add(new GuardaCampo( txtDescVend, 90, 100, 207, 20, "NomeVend", "Nome do representante", false, false, null, JTextFieldPad.TP_STRING,false),"txtDescVendx");
	  	lcVend.montaSql(false, "VENDEDOR", "VD");    
	  	lcVend.setQueryCommit(false);
	  	lcVend.setReadOnly(true);
	  	txtCodVend.setNomeCampo("CodVend");
		txtCodVend.setFK(true);
	  	txtCodVend.setTabelaExterna(lcVend);
	 
	  	adic(new JLabel("Cód.repr."),7,55,210,20);
		adic(txtCodVend,7,75,60,20);
		adic(new JLabel("Nome do representante"),70,55,210,20);
		adic(txtDescVend,70,75,200,20);
	
	
	
  }
  private boolean comRef() {
  	boolean bRetorno = false;
  	String sSQL = "SELECT USAREFPROD FROM SGPREFERE1 WHERE CODEMP=? AND CODFILIAL=?";
  	PreparedStatement ps = null;
  	ResultSet rs = null;
  	try {
  		ps = con.prepareStatement(sSQL);
  		ps.setInt(1,Aplicativo.iCodEmp);
  		ps.setInt(2,ListaCampos.getMasterFilial("SGPREFERE1"));
  		rs = ps.executeQuery();
  		if (rs.next()) {
  			if (rs.getString("UsaRefProd").trim().equals("S"))
  				bRetorno = true;
  		}
//      rs.close();
//      ps.close();
  		if (!con.getAutoCommit())
  			con.commit();
  	}
  	catch (SQLException err) {
  		Funcoes.mensagemErro(this,"Erro ao carregar a tabela PREFERE1!\n"+err.getMessage());
  	}
  	return bRetorno;
  }
  public void setConexao(Connection cn) {
    con = cn;
    lcVend.setConexao(con);
  }
  public void imprimir(boolean bVisualizar) {
  	
  	
  	 String sWhere = "";
	 String sCab="";
  	
  	
     if (txtDatafim.getVlrDate().before(txtDataini.getVlrDate())) {
		Funcoes.mensagemInforma(this,"Data final maior que a data inicial!");
        return;
     }

     if (txtCodVend.getText().trim().length() > 0) {
		sWhere += " AND V.CODVEND = "+txtCodVend.getText().trim();
		String sTmp = "REPR.: "+txtCodVend.getVlrString()+" - "+txtDescVend.getText().trim();
		sWhere += " AND V.CODEMPVD="+Aplicativo.iCodEmp+" AND V.CODFILIALVD="+lcVend.getCodFilial();
		sTmp = "|"+Funcoes.replicate(" ",68-(sTmp.length()/2))+sTmp;
		sCab += sTmp+Funcoes.replicate(" ",134-sTmp.length())+" |";
	}  
     
     
    ImprimeOS imp = new ImprimeOS("",con);
    int linPag = imp.verifLinPag()-1;
    boolean bPrim = true;
    
    BigDecimal bTotalVendaGrupo = new BigDecimal("0");
    BigDecimal bTotalCustoGrupo = new BigDecimal("0");
    BigDecimal bTotalDescGrupo = new BigDecimal("0");
    BigDecimal bTotalLucroGrupo = new BigDecimal("0");
    BigDecimal bTotalVenda = new BigDecimal("0");
    BigDecimal bTotalCusto = new BigDecimal("0");
    BigDecimal bTotalDesc = new BigDecimal("0");
    BigDecimal bTotalLucro = new BigDecimal("0");
    BigDecimal bTotalMargem = new BigDecimal("0");
    String sCodGrup = "";
    String sOrder = "";
    String sCodProd = "";
    imp.montaCab();
    String sDataini = "";
    String sDatafim = "";
    
    sDataini = txtDataini.getVlrString();
    sDatafim = txtDatafim.getVlrString();
    imp.setTitulo("Relatório Fisco de Vendas");
    if (comRef()) {
    	sCodProd = "REFPROD";
    }
    else {
    	sCodProd = "CODPROD";
    }
    if (rgOrdem.getVlrString().equals("C")) {
    	sOrder = "P."+sCodProd;
    }
    else {
    	sOrder = "P.DESCPROD";
    }
    String sSQL = "SELECT SUBSTRING(P.CODGRUP FROM 1 FOR 4),P."+sCodProd+",P.DESCPROD,G.DESCGRUP,"+
                          "P.CUSTOMPMPROD,SUM(IT.QTDITVENDA),SUM(IT.VLRDESCITVENDA),"+
                          "SUM(IT.VLRLIQITVENDA),IT.CODITVENDA FROM VDVENDA V,VDITVENDA IT,"+
                          "EQPRODUTO P,EQGRUPO G,EQTIPOMOV TM WHERE V.DTEMITVENDA BETWEEN ?"+
                          " AND ? AND G.CODGRUP = P.CODGRUP AND IT.CODVENDA=V.CODVENDA" +
                          " AND TM.CODEMP=V.CODEMPTM AND TM.CODFILIAL=V.CODFILIALTM" +
                          " AND TM.CODTIPOMOV=V.CODTIPOMOV AND P.CODPROD = IT.CODPROD" +
                          " AND (NOT IT.QTDITVENDA = 0)"+
                          " AND (V.FLAG IN "+Aplicativo.carregaFiltro(con,org.freedom.telas.Aplicativo.iCodEmp)+")"+
                          " AND (NOT SUBSTR(V.STATUSVENDA,1,1)='C') " +
                          " AND TM.TIPOMOV IN ('VD','PV','VT','SE')"+
                          ""+sWhere+" GROUP BY 1," +
                          "P."+sCodProd+",P.DESCPROD,G.DESCGRUP,IT.CODITVENDA, P.CUSTOMPMPROD" +
                          " ORDER BY 1,"+sOrder;
    System.out.println(sSQL);
    PreparedStatement ps = null;
    ResultSet rs = null;
    try {
      ps = con.prepareStatement(sSQL);
      ps.setDate(1,Funcoes.dateToSQLDate(txtDataini.getVlrDate()));
      ps.setDate(2,Funcoes.dateToSQLDate(txtDatafim.getVlrDate()));
      rs = ps.executeQuery();
      imp.limpaPags();
      
      while ( rs.next() ) {
      	System.out.println("GRUPO:"+rs.getString(1)+" DESC: "+rs.getString("DescProd"));
        if (imp.pRow()>=(linPag-1)) {
             imp.say(imp.pRow()+1,0,""+imp.comprimido());
             imp.say(imp.pRow()+0,0,"|"+Funcoes.replicate("-",134)+"|");
             imp.incPags();
             imp.eject();
        }
        if (imp.pRow()==0) {
          String sTitulo = "RELATORIO FISICO DE VENDAS   -   PERIODO DE :"+sDataini+" ATE: "+sDatafim;
          imp.say(imp.pRow()+1,0,""+imp.comprimido());
          imp.say(imp.pRow()+0,0,"+"+Funcoes.replicate("-",134)+"+");
          
      	  if (sCab.length() > 0) {
            imp.say(imp.pRow()+1,0,""+imp.comprimido());
      	  	imp.say(imp.pRow()+0,0,sCab);
      	  }
          
      
          
          imp.say(imp.pRow()+1,0,""+imp.comprimido());
          imp.say(imp.pRow()+0,0,"|   Emitido em :"+Funcoes.dateToStrDate(new Date()));
    	  
          
          imp.say(imp.pRow()+0,120,"Pagina : "+(imp.getNumPags()));
         
          imp.say(imp.pRow()+0,136,"|");
          imp.say(imp.pRow()+1,0,""+imp.comprimido());
          imp.say(imp.pRow()+0,0,"|");
          imp.say(imp.pRow()+0,(136-sTitulo.length())/2,sTitulo);
          imp.say(imp.pRow()+0,136,"|");
         
          
          imp.say(imp.pRow()+1,0,""+imp.comprimido());
          imp.say(imp.pRow()+0,0,"|");
          imp.say(imp.pRow()+0,136,"|");
          imp.say(imp.pRow()+1,0,""+imp.comprimido());
          imp.say(imp.pRow()+0,0,"|"+Funcoes.replicate("-",134)+"|");
          imp.say(imp.pRow()+1,0,""+imp.comprimido());
          imp.say(imp.pRow()+0,0,"|            |");
          imp.say(imp.pRow()+0,14,"                              |");
          imp.say(imp.pRow()+0,45,"             V E N D A           |");
          imp.say(imp.pRow()+0,79,"        C U S T O        |");
          imp.say(imp.pRow()+0,105,"         LUCRO ESTIMADO       |");
          imp.say(imp.pRow()+1,0,""+imp.comprimido());
          imp.say(imp.pRow()+0,0,"| Referência |");
          imp.say(imp.pRow()+0,14," Descrição                    |");
          imp.say(imp.pRow()+0,45," Qtd.  ");
          imp.say(imp.pRow()+0,52," Vlr. Unit. ");
          imp.say(imp.pRow()+0,64," Vlr. Total   |");
          imp.say(imp.pRow()+0,79," Vlr. Unit. ");
          imp.say(imp.pRow()+0,91," Vlr. Total  |");
          imp.say(imp.pRow()+0,105," P\\ Unid. ");
          imp.say(imp.pRow()+0,115," Vlr. Total ");
          imp.say(imp.pRow()+0,127," Margem |");
          imp.say(imp.pRow()+1,0,""+imp.comprimido());
          imp.say(imp.pRow()+0,0,"|"+Funcoes.replicate("-",134)+"|");
        }
        imp.say(imp.pRow()+1,0,""+imp.comprimido());
        if (!sCodGrup.equals(rs.getString(1).substring(0,4))) {
          imp.say(imp.pRow()+0,0,"|"+Funcoes.replicate("-",134)+"|");
          imp.say(imp.pRow()+1,0,""+imp.comprimido());
          if (!bPrim) {
            imp.say(imp.pRow()+0,0,"|            |");
            imp.say(imp.pRow()+0,14,"             Totais do Grupo: |");
            imp.say(imp.pRow()+0,45," VENDA: "+Funcoes.strDecimalToStrCurrency(24,2,""+bTotalVendaGrupo)+" |");
            imp.say(imp.pRow()+0,80," CUSTO: "+Funcoes.strDecimalToStrCurrency(16,2,""+bTotalCustoGrupo)+" |");
            BigDecimal bTotalMargemGrupo = bTotalVendaGrupo;
            if (bTotalMargemGrupo.doubleValue() != 0)
              bTotalMargemGrupo = bTotalCustoGrupo.divide(bTotalMargemGrupo,4,BigDecimal.ROUND_HALF_UP);
            bTotalMargemGrupo = bTotalMargemGrupo.multiply(new BigDecimal("100"));
            bTotalMargemGrupo = (new BigDecimal("100")).subtract(bTotalMargemGrupo);
            bTotalMargemGrupo = bTotalMargemGrupo.setScale(2);
            imp.say(imp.pRow()+0,105," LUCRO: "+Funcoes.strDecimalToStrCurrency(13,2,""+bTotalLucroGrupo)+"  "+Funcoes.copy(""+bTotalMargemGrupo,0,6)+" |");
            imp.say(imp.pRow()+1,0,""+imp.comprimido());
            imp.say(imp.pRow()+0,0,"|"+Funcoes.replicate("-",134)+"|");
            imp.say(imp.pRow()+1,0,""+imp.comprimido());
            bTotalVenda = bTotalVenda.add(bTotalVendaGrupo);
            bTotalCusto = bTotalCusto.add(bTotalCustoGrupo);
            bTotalDesc = bTotalDesc.add(bTotalDescGrupo);
            bTotalLucro = bTotalLucro.add(bTotalLucroGrupo);
            bTotalVendaGrupo = new BigDecimal("0");
            bTotalCustoGrupo = new BigDecimal("0");
            bTotalDescGrupo = new BigDecimal("0");
            bTotalLucroGrupo = new BigDecimal("0");
          }
          String sGrup = "GRUPO: "+rs.getString(1).substring(0,4)+" - "+rs.getString("DescGrup");
          imp.say(imp.pRow()+0,0,"|");
          imp.say(imp.pRow()+0,(136-sGrup.length())/2,sGrup);
          imp.say(imp.pRow()+0,136,"|");
          imp.say(imp.pRow()+1,0,""+imp.comprimido());
          imp.say(imp.pRow()+0,0,"|"+Funcoes.replicate("-",134)+"|");
          imp.say(imp.pRow()+1,0,""+imp.comprimido());
          bPrim = false;
        }
        sCodGrup = rs.getString(1).substring(0,4);
        imp.say(imp.pRow()+0,0,"|");
        imp.say(imp.pRow()+0,3,Funcoes.copy(rs.getString(2),0,10)+" |");
        imp.say(imp.pRow()+0,16,Funcoes.copy(rs.getString("DescProd"),0,28)+" |");
        imp.say(imp.pRow()+0,47,Funcoes.copy(rs.getString(6),0,5)+" ");
        BigDecimal bUnit = new BigDecimal(rs.getString(8) == null ? "0.0" : rs.getString(8));
        if ((rs.getString(6) != null) && ((new BigDecimal(rs.getString(6))).doubleValue() != 0))
          bUnit = bUnit.divide(new BigDecimal(rs.getString(6)),2,BigDecimal.ROUND_HALF_UP); 
        imp.say(imp.pRow()+0,54,Funcoes.strDecimalToStrCurrency(10,2,""+bUnit)+" ");
        imp.say(imp.pRow()+0,66,Funcoes.strDecimalToStrCurrency(12,2,rs.getString(8))+" |");
        imp.say(imp.pRow()+0,81,Funcoes.strDecimalToStrCurrency(10,2,rs.getString("CustoMPMProd"))+" ");
        BigDecimal bTotCusto = new BigDecimal(rs.getString("CustoMPMProd") == null ? "0.0" : rs.getString("CustoMPMProd"));
        bTotCusto = bTotCusto.multiply(new BigDecimal(rs.getString(6) == null ? "0.0" : rs.getString(6)));
        BigDecimal bLucro = new BigDecimal(""+bUnit);
        bLucro = bLucro.subtract(new BigDecimal(rs.getString("CustoMPMProd") == null ? "0.0" : rs.getString("CustoMPMProd")));
        BigDecimal bTotLucro = bLucro;
        bTotLucro = bTotLucro.multiply(new BigDecimal(rs.getString(6) == null ? "0.0" : rs.getString(6)));
        imp.say(imp.pRow()+0,93,Funcoes.strDecimalToStrCurrency(11,2,""+bTotCusto)+" |");
        imp.say(imp.pRow()+0,107,Funcoes.strDecimalToStrCurrency(8,2,""+bLucro)+" ");
        imp.say(imp.pRow()+0,117,Funcoes.strDecimalToStrCurrency(10,2,""+bTotLucro)+" ");
        BigDecimal bMargem = new BigDecimal("0");
        if ((rs.getString(8) != null) && ((new BigDecimal(rs.getString(8))).doubleValue() != 0))
           bMargem = bTotLucro.divide(new BigDecimal(rs.getString(8)),4,BigDecimal.ROUND_HALF_UP);
        else 
           bMargem = new BigDecimal(0);
        bMargem = bMargem.multiply(new BigDecimal("100"));
        bMargem = bMargem.setScale(2);
        imp.say(imp.pRow()+0,129,Funcoes.copy(""+bMargem,0,6)+" |");
        bTotalVendaGrupo = bTotalVendaGrupo.add(new BigDecimal(rs.getString(8) == null ? "0.0" : rs.getString(8)));
        bTotalCustoGrupo = bTotalCustoGrupo.add(bTotCusto);
        bTotalDescGrupo = bTotalDescGrupo.add(new BigDecimal(rs.getString(7) == null ? "0.0" : rs.getString(7)));
        bTotalLucroGrupo = bTotalLucroGrupo.add(bTotLucro);
      }
      imp.say(imp.pRow()+1,0,""+imp.comprimido());
      imp.say(imp.pRow()+0,0,"|"+Funcoes.replicate("-",134)+"|");
      imp.say(imp.pRow()+1,0,""+imp.comprimido());
      imp.say(imp.pRow()+0,0,"|            |");
      imp.say(imp.pRow()+0,14,"             Totais do Grupo: |");
      imp.say(imp.pRow()+0,45," VENDA: "+Funcoes.strDecimalToStrCurrency(24,2,""+bTotalVendaGrupo)+" |");
      imp.say(imp.pRow()+0,80," CUSTO: "+Funcoes.strDecimalToStrCurrency(16,2,""+bTotalCustoGrupo)+" |");
      BigDecimal bTotalMargemGrupo = bTotalVendaGrupo;
      if (bTotalMargemGrupo.doubleValue() != 0)
        bTotalMargemGrupo = bTotalCustoGrupo.divide(bTotalMargemGrupo,4,BigDecimal.ROUND_HALF_UP);
      bTotalMargemGrupo = bTotalMargemGrupo.multiply(new BigDecimal("100"));
      bTotalMargemGrupo = (new BigDecimal("100")).subtract(bTotalMargemGrupo);
      bTotalMargemGrupo = bTotalMargemGrupo.setScale(2);
      imp.say(imp.pRow()+0,105," LUCRO: "+Funcoes.strDecimalToStrCurrency(13,2,""+bTotalLucroGrupo)+"  "+Funcoes.copy(""+bTotalMargemGrupo,0,6)+" |");
      imp.say(imp.pRow()+1,0,""+imp.comprimido());
      imp.say(imp.pRow()+0,0,"|"+Funcoes.replicate("-",134)+"|");
      bTotalVenda = bTotalVenda.add(bTotalVendaGrupo);
      bTotalCusto = bTotalCusto.add(bTotalCustoGrupo);
      bTotalDesc = bTotalDesc.add(bTotalDescGrupo);
      bTotalLucro = bTotalLucro.add(bTotalLucroGrupo);
      imp.say(imp.pRow()+1,0,""+imp.comprimido());
      imp.say(imp.pRow(),0,"|"+Funcoes.replicate("-",134)+"|");
      imp.say(imp.pRow()+1,0,""+imp.comprimido());
      imp.say(imp.pRow()+0,0,"|");
      imp.say(imp.pRow()+0,51,"R E S U M O  G E R A L");
      imp.say(imp.pRow(),136,"|");
      imp.say(imp.pRow()+1,0,""+imp.comprimido());
      imp.say(imp.pRow()+0,0,"|");
      imp.say(imp.pRow()+0,30,"VENDA TOTAL: "+Funcoes.strDecimalToStrCurrency(20,2,""+bTotalVenda)+"   "+
                              "LUCRO ESTIMADO: "+Funcoes.strDecimalToStrCurrency(20,2,""+bTotalLucro));
      imp.say(imp.pRow(),136,"|");
      imp.say(imp.pRow()+1,0,""+imp.comprimido());
      imp.say(imp.pRow()+0,0,"|");
      if (bTotalVenda.doubleValue() != 0)
        bTotalMargem = bTotalLucro.divide(bTotalVenda,4,BigDecimal.ROUND_HALF_UP);
      bTotalMargem = bTotalMargem.multiply(new BigDecimal("100"));
      bTotalMargem = bTotalMargem.setScale(2);
      imp.say(imp.pRow()+0,30,"CUSTO TOTAL: "+Funcoes.strDecimalToStrCurrency(20,2,""+bTotalCusto)+"   "+
                              "MARGEM MEDIA: "+Funcoes.copy(bTotalMargem+"%",0,6));
      imp.say(imp.pRow(),136,"|");
      imp.say(imp.pRow()+1,0,""+imp.comprimido());
      imp.say(imp.pRow()+0,0,"|");
      imp.say(imp.pRow()+0,30,"DESCONTOS:   "+Funcoes.strDecimalToStrCurrency(20,2,""+bTotalDesc));
      imp.say(imp.pRow(),136,"|");
      imp.say(imp.pRow()+1,0,""+imp.comprimido());
      imp.say(imp.pRow()+0,0,"|"+Funcoes.replicate("-",134)+"|");
      
      
      imp.eject();
      
      imp.fechaGravacao();
      
//      rs.close();
//      ps.close();
      if (!con.getAutoCommit())
      	con.commit();
//      dl.dispose();
    }  
    catch ( SQLException err ) {
    	err.printStackTrace();
		Funcoes.mensagemErro(this,"Erro consulta ao relatório de vendas fisico!"+err.getMessage());      
    }
    
    if (bVisualizar) {
      imp.preview(this);
    }
    else {
      imp.print();
    }
  }
}
