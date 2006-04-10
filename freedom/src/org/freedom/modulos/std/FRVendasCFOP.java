/**
 * @version 02/11/2003 <BR>
 * @author Setpoint Informática Ltda./ Alex Rodrigues <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)FRVendasCFOP.java <BR>
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
import java.util.Date;
import java.util.Vector;

import javax.swing.BorderFactory;

import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.ImprimeOS;
import org.freedom.componentes.JCheckBoxPad;
import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JRadioGroup;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FRelatorio;

public class FRVendasCFOP extends FRelatorio{
	
	private static final long serialVersionUID = 1L;
	private JTextFieldPad txtDataini = new JTextFieldPad(JTextFieldPad.TP_DATE,10,0); 
	private JTextFieldPad txtDatafim = new JTextFieldPad(JTextFieldPad.TP_DATE,10,0);   
	private JTextFieldPad txtCodCFOP = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
	private JTextFieldFK txtDescCFOP = new JTextFieldFK(JTextFieldPad.TP_STRING,50,0);
	private JTextFieldPad txtCodTipoMov = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
	private JTextFieldFK txtDescTipoMov = new JTextFieldFK(JTextFieldPad.TP_STRING,50,0);
	private JCheckBoxPad cbVendaCanc = new JCheckBoxPad("Mostrar Canceladas", "S", "N");
	private JRadioGroup rgFaturados = null;
	private JRadioGroup rgFinanceiro = null;
	private Vector vLabsFat = new Vector();
	private Vector vValsFat = new Vector();
	private Vector vLabsFin = new Vector();
	private Vector vValsFin = new Vector();
	private ListaCampos lcCFOP = new ListaCampos(this,"NT");
	private ListaCampos lcMov = new ListaCampos(this,"TM");
	private BigDecimal bTotalCFOP;
    private BigDecimal bTotalGeral;
  
	public FRVendasCFOP() {
	    setTitulo("Vendas em Geral");
	    setAtribos(80,80,295,340);
	    

	    vLabsFat.addElement("Faturado");
		vLabsFat.addElement("Não Faturado");
		vLabsFat.addElement("Ambos");
		vValsFat.addElement("S");
		vValsFat.addElement("N");
		vValsFat.addElement("A");
		rgFaturados = new JRadioGroup(3, 1, vLabsFat, vValsFat);
		rgFaturados.setVlrString("S");
		
		vLabsFin.addElement("Financeiro");
		vLabsFin.addElement("Não Finaceiro");
		vLabsFin.addElement("Ambos");
		vValsFin.addElement("S");
		vValsFin.addElement("N");
		vValsFin.addElement("A");
		rgFinanceiro = new JRadioGroup(3, 1, vLabsFin, vValsFin);
		rgFinanceiro.setVlrString("S");
		
		lcCFOP.add(new GuardaCampo(txtCodCFOP, "CodNat", "CFOP",ListaCampos.DB_PK, false));
		lcCFOP.add(new GuardaCampo(txtDescCFOP, "DescNat", "Descrição da CFOP",ListaCampos.DB_SI, false));
		lcCFOP.montaSql(false, "NATOPER", "LF");
		lcCFOP.setQueryCommit(false);
		lcCFOP.setReadOnly(true);
		txtCodCFOP.setNomeCampo("CodNat");
		txtCodCFOP.setFK(true);
		txtCodCFOP.setTabelaExterna(lcCFOP);
		
		lcMov.add(new GuardaCampo( txtCodTipoMov, "CodTipoMov", "Cód.tp.mov.",  ListaCampos.DB_PK, false));
		lcMov.add(new GuardaCampo( txtDescTipoMov, "DescTipoMov", "Descrição do tipo de movimento",  ListaCampos.DB_SI, false));
		lcMov.montaSql(false, "TIPOMOV", "EQ");    
		lcMov.setQueryCommit(false);
		lcMov.setReadOnly(true);
		txtCodTipoMov.setNomeCampo("CodTipoMov");
		txtCodTipoMov.setFK(true);
		txtCodTipoMov.setTabelaExterna(lcMov);
		
	    
	    txtDataini.setVlrDate(new Date());
	    txtDatafim.setVlrDate(new Date());
	    JLabelPad lbLinha = new JLabelPad();
	    lbLinha.setBorder(BorderFactory.createEtchedBorder());	
	    
	    adic(new JLabelPad("Periodo:"),7,5,100,20);
	    adic(lbLinha,60,15,210,2);
	    adic(new JLabelPad("De:"),7,30,30,20);
	    adic(txtDataini,32,30,97,20);
	    adic(new JLabelPad("Até:"),140,30,30,20);
	    adic(txtDatafim,170,30,100,20);
	  	adic(new JLabelPad("Cód.CFOP"),7,60,210,20);
		adic(txtCodCFOP,7,80,70,20);
		adic(new JLabelPad("Descrição da CFOP"),80,60,210,20);
		adic(txtDescCFOP,80,80,190,20);
		adic(new JLabelPad("Cód.tp.mov."),7,100,210,20);
		adic(txtCodTipoMov,7,120,70,20);
		adic(new JLabelPad("Descrição do tipo de movimento"),80,100,210,20);
		adic(txtDescTipoMov,80,120,190,20);
		adic(rgFaturados, 7, 160, 120, 70);
		adic(rgFinanceiro, 153, 160, 120, 70);
		adic(cbVendaCanc, 7, 240, 200, 20);
	
	}
  
	public void setConexao(Connection cn) {
	    super.setConexao(cn);
	    lcCFOP.setConexao(con);
	    lcMov.setConexao(con);
	}
  
	public void imprimir(boolean bVisualizar) {

		bTotalCFOP = new BigDecimal("0");
	    bTotalGeral = new BigDecimal("0");
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	  	String sWhere = "";
	  	String sWhere1 = "";
	  	String sWhere2 = "";
	  	String sWhere3 = "";
		String sCab = "";
		String sCab1 = "";
	    String sDataini = "";
	    String sDatafim = "";
	    String sCFOP = "";
		 
		if (txtDatafim.getVlrDate().before(txtDataini.getVlrDate())) {
			Funcoes.mensagemInforma(this,"Data final maior que a data inicial!");
			return;
	    }
		
		if(txtCodCFOP.getVlrInteger().intValue() > 0)
			sWhere += " AND I.CODNAT="+txtCodCFOP.getVlrInteger().intValue();
		
		if(txtCodTipoMov.getVlrInteger().intValue() > 0){
			sWhere += " AND V.CODTIPOMOV="+txtCodTipoMov.getVlrInteger().intValue();
			sCab1 = "FILTRADO POR TIPO DE MOVIMENTO - " + txtDescTipoMov.getVlrString();
		}
		 		
		if(rgFaturados.getVlrString().equals("S")) {
			sWhere1 = " AND TM.FISCALTIPOMOV='S' ";
			sCab += " - SO FATURADO";
		} else if(rgFaturados.getVlrString().equals("N")) {
			sWhere1 = " AND TM.FISCALTIPOMOV='N' ";
			sCab += " - NAO FATURADO";
		} else if(rgFaturados.getVlrString().equals("A"))
			sWhere1 = " AND TM.FISCALTIPOMOV IN ('S','N') ";

		if(rgFinanceiro.getVlrString().equals("S")) {
			sWhere2 = " AND TM.SOMAVDTIPOMOV='S' ";
			sCab += " - SO FINANCEIRO";
		} else if(rgFinanceiro.getVlrString().equals("N")) {
			sWhere2 = " AND TM.SOMAVDTIPOMOV='N' ";
			sCab += " - NAO FINANCEIRO";
		} else if(rgFinanceiro.getVlrString().equals("A"))
			sWhere2 = " AND TM.SOMAVDTIPOMOV IN ('S','N') ";

		if(cbVendaCanc.getVlrString().equals("N"))
			sWhere3 = " AND NOT SUBSTR(V.STATUSVENDA,1,1)='C' ";
	  	
	    ImprimeOS imp = new ImprimeOS("",con);
	    int linPag = imp.verifLinPag()-1;
	    imp.verifLinPag();
	    	   
	    sDataini = txtDataini.getVlrString();
	    sDatafim = txtDatafim.getVlrString();	    
	    
	    String sSQL = "SELECT V.CODVENDA, V.DOCVENDA, V.DTEMITVENDA, V.DTSAIDAVENDA, "
	    		    + "I.CODNAT, NT.DESCNAT, V.CODCLI, C.RAZCLI, SUM(I.VLRLIQITVENDA)  "
	    		    + "FROM VDVENDA V,VDITVENDA I,VDCLIENTE C, EQTIPOMOV TM, LFNATOPER NT "
	    		    + "WHERE V.CODEMP=? AND V.CODFILIAL=? AND V.TIPOVENDA='V' "
	    		    + "AND I.CODEMP=V.CODEMP AND I.CODFILIAL=V.CODFILIAL AND I.CODVENDA=V.CODVENDA "
	    		    + "AND C.CODEMP=V.CODEMPCL AND C.CODFILIAL=V.CODFILIALCL AND C.CODCLI=V.CODCLI "
	    		    + "AND TM.CODEMP=V.CODEMPTM AND TM.CODFILIAL=V.CODFILIALTM AND TM.CODTIPOMOV=V.CODTIPOMOV "
	    		    + "AND NT.CODEMP=I.CODEMPNT AND NT.CODFILIAL=I.CODFILIALNT AND NT.CODNAT=I.CODNAT "
	    		    + sWhere + sWhere1 + sWhere2 + sWhere3
	    		    + "AND V.DTEMITVENDA BETWEEN ? AND ? "
	    		    + "GROUP BY V.CODVENDA, V.DOCVENDA, V.DTEMITVENDA, V.DTSAIDAVENDA, "
	    		    + "I.CODNAT, NT.DESCNAT, V.CODCLI, C.RAZCLI "
	    		    + "ORDER BY I.CODNAT, V.DOCVENDA, V.CODVENDA ";
	                  
	    try {
		      ps = con.prepareStatement(sSQL);
		      ps.setInt(1,Aplicativo.iCodEmp);
		      ps.setInt(2,Aplicativo.iCodFilial);
		      ps.setDate(3,Funcoes.dateToSQLDate(txtDataini.getVlrDate()));
		      ps.setDate(4,Funcoes.dateToSQLDate(txtDatafim.getVlrDate()));
		      rs = ps.executeQuery();
		      imp.limpaPags();
		      
		      imp.setTitulo("Relatório de Vendas por CFOP");
		  	  imp.addSubTitulo("RELATORIO DE VENDAS POR CFOP");
		  	  imp.addSubTitulo("PERIODO DE :"+sDataini+" Até: "+sDatafim);
		  	  if (sCab1.length() > 0) {
		    	  imp.addSubTitulo(sCab1);
		      }
		      if (sCab.length() > 0) {
		    	  imp.addSubTitulo(sCab);
		      }
	      
		      while ( rs.next() ) {
		    	  if (imp.pRow()>=(linPag-1)) {
			          imp.say(imp.pRow()+1,0,""+imp.comprimido());
			          imp.say(imp.pRow()+0,0,"+"+Funcoes.replicate("-",133)+"+");
			          imp.incPags();
			          imp.eject();
		    	  }
		    	  if (imp.pRow()==0) {
		    		  imp.montaCab(); 
		    		  imp.impCab(136, true);
		                     
		    		  imp.say(imp.pRow()+0,0,""+imp.comprimido());
		    		  imp.say(imp.pRow()+0,0,"|"+Funcoes.replicate("-",133)+"|");   
		    		  imp.say(imp.pRow()+1,0,""+imp.comprimido());
		    		  imp.say(imp.pRow()+0,0,"| NF");
		    		  imp.say(imp.pRow()+0,14,"| EMISSÃO");
		    		  imp.say(imp.pRow()+0,27,"| SAIDA");
		    		  imp.say(imp.pRow()+0,40,"| PEDIDO");
		    		  imp.say(imp.pRow()+0,50,"| COD.CLI.");
		    		  imp.say(imp.pRow()+0,61,"| NOME DO CLIENTE");
		    		  imp.say(imp.pRow()+0,114,"|      VLR. TOTAL");
		    		  imp.say(imp.pRow()+0,135,"|");
		    		  imp.say(imp.pRow()+1,0,""+imp.comprimido());
		    		  imp.say(imp.pRow()+0,0,"|"+Funcoes.replicate("-",133)+"|");  
		    	  }
		         
		    	  if (!rs.getString("CODNAT").equals(sCFOP)) {
		    		  subTotal(imp,rs);		    		  
		    		  sCFOP = rs.getString("CODNAT");
		    		  imp.say(imp.pRow()+1,0,""+imp.comprimido());
		    		  imp.say(imp.pRow()+0,0,"|"+Funcoes.replicate("=",133)+"|");
		    		  imp.say(imp.pRow()+1,0,""+imp.comprimido());
		    		  imp.say(imp.pRow()+0,0,"|");
		    		  imp.say(imp.pRow()+0,7,""+sCFOP+" - "+rs.getString("DESCNAT"));
		    		  imp.say(imp.pRow()+0,135,"|");
		    		  imp.say(imp.pRow()+1,0,""+imp.comprimido());
		    		  imp.say(imp.pRow()+0,0,"|"+Funcoes.replicate("-",133)+"|");
		    	  }
		    	  
		    	  imp.say(imp.pRow()+1,0,""+imp.comprimido());		
		    	  imp.say(imp.pRow()+0,0,"| "+Funcoes.copy(rs.getString("DocVenda"),0,10));
		    	  imp.say(imp.pRow()+0,14,"| "+Funcoes.sqlDateToStrDate(rs.getDate("DtEmitVenda")));
		    	  imp.say(imp.pRow()+0,27,"| "+Funcoes.sqlDateToStrDate(rs.getDate("DtSaidaVenda")));
		    	  imp.say(imp.pRow()+0,40,"| "+rs.getInt("CodVenda"));
		    	  imp.say(imp.pRow()+0,50,"| "+rs.getInt("CodCli"));
		    	  imp.say(imp.pRow()+0,61,"| "+Funcoes.copy(rs.getString("RazCli"),0,50));
		    	  imp.say(imp.pRow()+0,114,"| "+Funcoes.strDecimalToStrCurrency(15,2,""+rs.getFloat(9)));
		    	  imp.say(imp.pRow()+0,135,"|");
		    	  
		    	  if (rs.getString(9) != null) {
		    		  bTotalCFOP = bTotalCFOP.add(new BigDecimal(rs.getString(9)));
		          }
		                         
	      	  }

    		  subTotal(imp,rs);
	          imp.say(imp.pRow()+1,0,""+imp.comprimido());
		      imp.say(imp.pRow()+0,0,"+"+Funcoes.replicate("=",133)+"+");
	          imp.say(imp.pRow()+1,0,""+imp.comprimido());
		      imp.say(imp.pRow()+0,0,"|");
		      imp.say(imp.pRow()+0,96,"TOTAL GERAL       |"+" "+ Funcoes.strDecimalToStrCurrency(15,2,"" +bTotalGeral));
		      imp.say(imp.pRow()+0,135,"|");
	          imp.say(imp.pRow()+1,0,""+imp.comprimido());
		      imp.say(imp.pRow()+0,0,"+"+Funcoes.replicate("=",133)+"+");
	      
		      imp.eject();	      
		      imp.fechaGravacao();
		      
		      if (!con.getAutoCommit())
	      			con.commit();
	      	
	    }   catch ( SQLException err ) {
			Funcoes.mensagemErro(this,"Erro consulta tabela de venda!\n"+err.getMessage(),true,con,err);      
	    } finally{
	    	ps = null;
		    rs = null;
		  	sWhere = null;
		  	sWhere1 = null;
		  	sWhere2 = null;
		  	sWhere3 = null;
			sCab = null;
			sCab1 = null;
		    sDataini = null;
		    sDatafim = null;
		    sCFOP = null;
	    }
	    
	    if (bVisualizar) 
	    	imp.preview(this);
	    else 
	    	imp.print();
	}
	
	private void subTotal(ImprimeOS imp, ResultSet rs){
		try{
			if(bTotalCFOP.floatValue()!=0f){
				imp.say(imp.pRow()+1,0,""+imp.comprimido());
				imp.say(imp.pRow()+0,0,"|"+Funcoes.replicate("-",133)+"|");
	  		  	imp.say(imp.pRow()+1,0,""+imp.comprimido());
	  		  	imp.say(imp.pRow()+0,0,"|");
	  		  	imp.say(imp.pRow()+0,96,"SubTotal          | "+
	  				  Funcoes.strDecimalToStrCurrency(15,2,"" +bTotalCFOP));
	  		  	imp.say(imp.pRow()+0,135,"|");
	  		  
	  		  	if (rs.getString(9) != null) {
		    		  bTotalGeral = bTotalGeral.add(bTotalCFOP);
		        }
	  		  
	  		  	bTotalCFOP = new BigDecimal("0");
			}
		} catch(SQLException err){
			Funcoes.mensagemErro(this,"Erro consulta tabela de venda!\n"+err.getMessage(),true,con,err); 
		}
	}	
}
