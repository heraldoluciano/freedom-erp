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
import java.util.GregorianCalendar;
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

public class FRVendasFisico extends FRelatorio {
	private static final long serialVersionUID = 1L;
	private JTextFieldPad txtDataini = new JTextFieldPad(JTextFieldPad.TP_DATE,10,0); 
	private JTextFieldPad txtDatafim = new JTextFieldPad(JTextFieldPad.TP_DATE,10,0);   
	private JTextFieldPad txtCodVend = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
	private JTextFieldFK txtDescVend = new JTextFieldFK(JTextFieldPad.TP_STRING,50,0);  
	private JLabelPad lbOrdem = new JLabelPad("Ordenar por:");  
	private JCheckBoxPad cbVendaCanc = new JCheckBoxPad("Mostrar Canceladas", "S", "N");
	private JRadioGroup rgFaturados = null;
	private JRadioGroup rgFinanceiro = null;
	private JRadioGroup rgOrdem = null;
	private Vector vLabsFat = new Vector();
	private Vector vValsFat = new Vector();
	private Vector vLabsFin = new Vector();
	private Vector vValsFin = new Vector();
	private Vector vLabs = new Vector();
	private Vector vVals = new Vector();
	private ListaCampos lcVend = new ListaCampos(this);
	  
	public FRVendasFisico() {
		setTitulo("Fechamento Fisico de Vendas");
		setAtribos(80,80,295,345);
		   
		GregorianCalendar cPeriodo = new GregorianCalendar();
		txtDatafim.setVlrDate(cPeriodo.getTime());
		cPeriodo.set(Calendar.DAY_OF_MONTH,cPeriodo.get(Calendar.DAY_OF_MONTH)-30);
		txtDataini.setVlrDate(cPeriodo.getTime());
		JLabelPad lbLinha = new JLabelPad();
		lbLinha.setBorder(BorderFactory.createEtchedBorder());
			
		vLabs.addElement("Código");
		vLabs.addElement("Descrição");
		vVals.addElement("C");
		vVals.addElement("D");
		rgOrdem = new JRadioGroup(1,2,vLabs,vVals);
		rgOrdem.setVlrString("D");
		
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
		
		lcVend.add(new GuardaCampo( txtCodVend, "CodVend", "Cód.comiss.", ListaCampos.DB_PK, false));
		lcVend.add(new GuardaCampo( txtDescVend, "NomeVend", "Nome do comissionado", ListaCampos.DB_SI, false));
		lcVend.montaSql(false, "VENDEDOR", "VD");    
		lcVend.setQueryCommit(false);
		lcVend.setReadOnly(true);
		txtCodVend.setNomeCampo("CodVend");
		txtCodVend.setFK(true);
		txtCodVend.setTabelaExterna(lcVend);
		
		adic(new JLabelPad("Periodo:"),7,5,100,20);
		adic(lbLinha,60,15,210,2);
		adic(new JLabelPad("De:"),7,30,30,20);
		adic(txtDataini,32,30,97,20);
		adic(new JLabelPad("Até:"),140,30,30,20);
		adic(txtDatafim,170,30,100,20);
		adic(new JLabelPad("Cód.comiss."),7,55,210,20);
		adic(txtCodVend,7,75,70,20);
		adic(new JLabelPad("Nome do comissionado"),80,55,210,20);
		adic(txtDescVend,80,75,190,20);
		adic(lbOrdem,7,105,80,15);
		adic(rgOrdem,7,125,263,30);	 	    
		adic(rgFaturados, 7, 165, 120, 70);
		adic(rgFinanceiro, 153, 165, 120, 70);
		adic(cbVendaCanc, 7, 245, 200, 20);
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
			if (rs.next())
				if (rs.getString("UsaRefProd").trim().equals("S"))
					bRetorno = true;
			if (!con.getAutoCommit())
				con.commit();
		} catch (SQLException err) {
			Funcoes.mensagemErro(this,"Erro ao carregar a tabela PREFERE1!\n"+err.getMessage(),true,con,err);
		} finally {
			sSQL = null;
			ps = null;
			rs = null;
		}
		return bRetorno;
	}
	  
	public void setConexao(Connection cn) {
		super.setConexao(cn);
		lcVend.setConexao(con);
	}
	  
	public void imprimir(boolean bVisualizar) {
		
		if (txtDatafim.getVlrDate().before(txtDataini.getVlrDate())) {
			Funcoes.mensagemInforma(this,"Data final maior que a data inicial!");
			return;
		}
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sSQL = null;
		String sWhere = "";
		String sWhere1 = "";
		String sWhere2 = "";
		String sWhere3 = "";
		String sCab = "";
		String sCodGrup = "";
		String sOrder = "";
		String sCodProd = "";	
		BigDecimal bTotalVendaGrupo = null;
		BigDecimal bTotalCustoGrupo = null;
		BigDecimal bTotalDescGrupo = null;
		BigDecimal bTotalLucroGrupo = null;
		BigDecimal bTotalVenda = null;
		BigDecimal bTotalCusto = null;
		BigDecimal bTotalDesc = null;
		BigDecimal bTotalLucro = null;
		BigDecimal bTotalMargem = null;
		BigDecimal bUnit = null;
		BigDecimal bTotCusto = null;
		BigDecimal bLucro = null;
		BigDecimal bTotLucro = null;
		BigDecimal bMargem = null;
		BigDecimal bTotalMargemGrupo = null;		 
		ImprimeOS imp = null;
		int linPag = 0;
		boolean bPrim = true;		
		 
		
		if (txtCodVend.getText().trim().length() > 0) {
			sWhere += " AND V.CODVEND = "+txtCodVend.getText().trim();
			sCab = "REPR.: "+txtCodVend.getVlrString()+" - "+txtDescVend.getText().trim();
			sWhere += " AND V.CODEMPVD="+Aplicativo.iCodEmp+" AND V.CODFILIALVD="+lcVend.getCodFilial();
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
				
		if (comRef()) 
			sCodProd = "REFPROD";
		else 
			sCodProd = "CODPROD";
		if (rgOrdem.getVlrString().equals("C")) 
			sOrder = "P."+sCodProd;
		else 
			sOrder = "P.DESCPROD";
		
		try {
			
			imp = new ImprimeOS("",con);
			linPag = imp.verifLinPag()-1;
			imp.limpaPags();
			imp.montaCab();   
			imp.setTitulo("Relatório Fisco de Vendas");
			imp.addSubTitulo("RELATORIO FISICO DE VENDAS   -   PERIODO DE :"+txtDataini.getVlrString()+" ATE: "+txtDatafim.getVlrString());
			if (sCab.length() > 0) 
				imp.addSubTitulo(sCab);			

			bTotalVendaGrupo = new BigDecimal("0");
			bTotalCustoGrupo = new BigDecimal("0");
			bTotalDescGrupo = new BigDecimal("0");
			bTotalLucroGrupo = new BigDecimal("0");
			bTotalVenda = new BigDecimal("0");
			bTotalCusto = new BigDecimal("0");
			bTotalDesc = new BigDecimal("0");
			bTotalLucro = new BigDecimal("0");
			bTotalMargem = new BigDecimal("0");
			bUnit = new BigDecimal("0");
			bTotCusto = new BigDecimal("0");
			bLucro = new BigDecimal("0");
			bTotLucro = new BigDecimal("0");
			bMargem = new BigDecimal("0");
			bTotalMargemGrupo = new BigDecimal("0");		
			
			sSQL = "SELECT SUBSTRING(P.CODGRUP FROM 1 FOR 4),P."+sCodProd+",P.DESCPROD,G.DESCGRUP,P.CUSTOMPMPROD,"
			     + "SUM(IT.QTDITVENDA),SUM(IT.VLRDESCITVENDA), SUM(IT.VLRLIQITVENDA),IT.CODITVENDA"
			     + " FROM VDVENDA V,VDITVENDA IT, EQPRODUTO P,EQGRUPO G,EQTIPOMOV TM "
			     + " WHERE V.DTEMITVENDA BETWEEN ? AND ? AND G.CODGRUP = P.CODGRUP AND IT.CODVENDA=V.CODVENDA"
			     + " AND TM.CODEMP=V.CODEMPTM AND TM.CODFILIAL=V.CODFILIALTM"
			     + sWhere + sWhere1 + sWhere2 + sWhere3 
			     + " AND TM.CODTIPOMOV=V.CODTIPOMOV AND P.CODPROD = IT.CODPROD"
			     + " AND (NOT IT.QTDITVENDA = 0)"
			     + " AND (V.FLAG IN "+Aplicativo.carregaFiltro(con,org.freedom.telas.Aplicativo.iCodEmp)+")"
			     + " AND TM.TIPOMOV IN ('VD','PV','VT','SE')"
			     + " GROUP BY 1," 
			     + "P."+sCodProd+",P.DESCPROD,G.DESCGRUP,IT.CODITVENDA, P.CUSTOMPMPROD" 
			     + " ORDER BY 1,"+sOrder;
			
			ps = con.prepareStatement(sSQL);
			ps.setDate(1,Funcoes.dateToSQLDate(txtDataini.getVlrDate()));
			ps.setDate(2,Funcoes.dateToSQLDate(txtDatafim.getVlrDate()));
			rs = ps.executeQuery();
			while ( rs.next() ) {
				if (imp.pRow()>=(linPag-1)) {
					imp.say(imp.pRow()+1, 0, imp.comprimido());
					imp.say(imp.pRow(), 0, "+" + Funcoes.replicate("-",133) + "+");
					imp.incPags();
					imp.eject();
				}
				if (imp.pRow()==0) {     	              
					imp.impCab(136, true);					 
					imp.say(imp.pRow(), 0, imp.comprimido());
					imp.say(imp.pRow(), 0, "|");
					imp.say(imp.pRow(), 135, "|");
					imp.say(imp.pRow()+1, 0, imp.comprimido());
					imp.say(imp.pRow(), 0, "|" + Funcoes.replicate("-",133) + "|");
					imp.say(imp.pRow()+1, 0, imp.comprimido());
					imp.say(imp.pRow(), 0, "|            |                              |");
					imp.say(imp.pRow(), 45, "             V E N D A           |");
					imp.say(imp.pRow(), 79, "        C U S T O        |");
					imp.say(imp.pRow(), 105, "         LUCRO ESTIMADO      |");
					imp.say(imp.pRow()+1, 0, imp.comprimido());
					imp.say(imp.pRow(), 0, "| Referência |");
					imp.say(imp.pRow(), 14, " Descrição                    |");
					imp.say(imp.pRow(), 45, " Qtd.  ");
					imp.say(imp.pRow(), 52, " Vlr. Unit. ");
					imp.say(imp.pRow(), 64, " Vlr. Total   |");
					imp.say(imp.pRow(), 79, " Vlr. Unit. ");
					imp.say(imp.pRow(), 91, " Vlr. Total  |");
					imp.say(imp.pRow(),105, " P\\ Unid. ");
					imp.say(imp.pRow(),115, " Vlr. Total ");
					imp.say(imp.pRow(),127, " Margem|");
					imp.say(imp.pRow()+1, 0, imp.comprimido());
					imp.say(imp.pRow(), 0, "|" + Funcoes.replicate("-",133) + "|");
				}
				imp.say(imp.pRow()+1, 0,imp.comprimido());
				if (!sCodGrup.equals(rs.getString(1).substring(0,4))) {
					imp.say(imp.pRow(), 0, "|" + Funcoes.replicate("-",133) + "|");
					imp.say(imp.pRow()+1, 0, imp.comprimido());
					if (!bPrim) {
						imp.say(imp.pRow(), 0, "|            |             Totais do Grupo: |");
						imp.say(imp.pRow(), 45, " VENDA: " + Funcoes.strDecimalToStrCurrency(24,2,""+bTotalVendaGrupo)+" |");
						imp.say(imp.pRow(), 80, " CUSTO: " + Funcoes.strDecimalToStrCurrency(16,2,""+bTotalCustoGrupo)+" |");
						
						bTotalMargemGrupo = bTotalVendaGrupo;
						if (bTotalMargemGrupo.doubleValue() != 0)
							bTotalMargemGrupo = bTotalCustoGrupo.divide(bTotalMargemGrupo,4,BigDecimal.ROUND_HALF_UP);
						
						bTotalMargemGrupo = bTotalMargemGrupo.multiply(new BigDecimal("100"));
						bTotalMargemGrupo = (new BigDecimal("100")).subtract(bTotalMargemGrupo);
						bTotalMargemGrupo = bTotalMargemGrupo.setScale(2);
						
						imp.say(imp.pRow(),105, " LUCRO: " + Funcoes.strDecimalToStrCurrency(13,2,""+bTotalLucroGrupo)+"  "+Funcoes.copy(""+bTotalMargemGrupo,0,6)+"|");
						imp.say(imp.pRow()+1, 0, imp.comprimido());
						imp.say(imp.pRow(), 0, "|" + Funcoes.replicate("-",133) + "|");
						imp.say(imp.pRow()+1, 0, imp.comprimido());
						
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
					imp.say(imp.pRow(), 0, "|");
					imp.say(imp.pRow(), (135-sGrup.length())/2, sGrup);
					imp.say(imp.pRow(), 135, "|");
					imp.say(imp.pRow()+1, 0, imp.comprimido());
					imp.say(imp.pRow(), 0, "|" + Funcoes.replicate("-",133) + "|");
					imp.say(imp.pRow()+1, 0, imp.comprimido());
					bPrim = false;
				}
				sCodGrup = rs.getString(1).substring(0,4);
				imp.say(imp.pRow(), 0, "|");
				imp.say(imp.pRow(), 3, Funcoes.copy(rs.getString(2),0,10)+" |");
				imp.say(imp.pRow(), 16, Funcoes.copy(rs.getString("DescProd"),0,28)+" |");
				imp.say(imp.pRow(), 47, Funcoes.copy(rs.getString(6),0,5)+" ");
				
				bUnit = new BigDecimal(rs.getString(8) == null ? "0.0" : rs.getString(8));
				if ((rs.getString(6) != null) && ((new BigDecimal(rs.getString(6))).doubleValue() != 0))
					bUnit = bUnit.divide(new BigDecimal(rs.getString(6)),2,BigDecimal.ROUND_HALF_UP); 
				
				imp.say(imp.pRow(), 54, Funcoes.strDecimalToStrCurrency(10,2,""+bUnit)+" ");
				imp.say(imp.pRow(), 66, Funcoes.strDecimalToStrCurrency(12,2,rs.getString(8))+" |");
				imp.say(imp.pRow(), 81, Funcoes.strDecimalToStrCurrency(10,2,rs.getString("CustoMPMProd"))+" ");
				
				bTotCusto = new BigDecimal(rs.getString("CustoMPMProd") == null ? "0.0" : rs.getString("CustoMPMProd"));
				bTotCusto = bTotCusto.multiply(new BigDecimal(rs.getString(6) == null ? "0.0" : rs.getString(6)));
				bLucro = new BigDecimal(""+bUnit);
				bLucro = bLucro.subtract(new BigDecimal(rs.getString("CustoMPMProd") == null ? "0.0" : rs.getString("CustoMPMProd")));
				bTotLucro = bLucro;
				bTotLucro = bTotLucro.multiply(new BigDecimal(rs.getString(6) == null ? "0.0" : rs.getString(6)));
				
				imp.say(imp.pRow(), 93, Funcoes.strDecimalToStrCurrency(11,2,""+bTotCusto)+" |");
				imp.say(imp.pRow(),107, Funcoes.strDecimalToStrCurrency(8,2,""+bLucro)+" ");
				imp.say(imp.pRow(),117, Funcoes.strDecimalToStrCurrency(10,2,""+bTotLucro)+" ");
				
				if ((rs.getString(8) != null) && ((new BigDecimal(rs.getString(8))).doubleValue() != 0))
					bMargem = bTotLucro.divide(new BigDecimal(rs.getString(8)),4,BigDecimal.ROUND_HALF_UP);
				else 
					bMargem = new BigDecimal(0);
				bMargem = bMargem.multiply(new BigDecimal("100"));
				bMargem = bMargem.setScale(2);
				
				imp.say(imp.pRow(),129, Funcoes.copy(""+bMargem,0,6)+"|");
				
				bTotalVendaGrupo = bTotalVendaGrupo.add(new BigDecimal(rs.getString(8) == null ? "0.0" : rs.getString(8)));
				bTotalCustoGrupo = bTotalCustoGrupo.add(bTotCusto);
				bTotalDescGrupo = bTotalDescGrupo.add(new BigDecimal(rs.getString(7) == null ? "0.0" : rs.getString(7)));
				bTotalLucroGrupo = bTotalLucroGrupo.add(bTotLucro);
			}
			imp.say(imp.pRow()+1, 0, imp.comprimido());
			imp.say(imp.pRow(), 0, "|" + Funcoes.replicate("-",133) + "|");
			imp.say(imp.pRow()+1, 0, imp.comprimido());
			imp.say(imp.pRow(), 0, "|            |             Totais do Grupo: |");
			imp.say(imp.pRow(), 45, " VENDA: " + Funcoes.strDecimalToStrCurrency(24,2,""+bTotalVendaGrupo)+" |");
			imp.say(imp.pRow(), 80, " CUSTO: " + Funcoes.strDecimalToStrCurrency(16,2,""+bTotalCustoGrupo)+" |");
			
			bTotalMargemGrupo = bTotalVendaGrupo;
			if (bTotalMargemGrupo.doubleValue() != 0)
				bTotalMargemGrupo = bTotalCustoGrupo.divide(bTotalMargemGrupo,4,BigDecimal.ROUND_HALF_UP);
			  
			bTotalMargemGrupo = bTotalMargemGrupo.multiply(new BigDecimal("100"));
			bTotalMargemGrupo = (new BigDecimal("100")).subtract(bTotalMargemGrupo);
			bTotalMargemGrupo = bTotalMargemGrupo.setScale(2);
			
			imp.say(imp.pRow(), 105, " LUCRO: " + Funcoes.strDecimalToStrCurrency(13,2,""+bTotalLucroGrupo)+"  "+Funcoes.copy(""+bTotalMargemGrupo,0,6)+"|");
			imp.say(imp.pRow()+1, 0, imp.comprimido());
			imp.say(imp.pRow(), 0, "|" + Funcoes.replicate("-",133) + "|");
			bTotalVenda = bTotalVenda.add(bTotalVendaGrupo);
			
			bTotalCusto = bTotalCusto.add(bTotalCustoGrupo);
			bTotalDesc = bTotalDesc.add(bTotalDescGrupo);
			bTotalLucro = bTotalLucro.add(bTotalLucroGrupo);
			
			imp.say(imp.pRow()+1, 0, imp.comprimido());
			imp.say(imp.pRow(), 0, "+" + Funcoes.replicate("=",133) + "+");
			imp.say(imp.pRow()+1, 0, imp.comprimido());
			imp.say(imp.pRow(), 0, "|");
			imp.say(imp.pRow(), 51, "R E S U M O  G E R A L");
			imp.say(imp.pRow(),135, "|");
			imp.say(imp.pRow()+1, 0, imp.comprimido());
			imp.say(imp.pRow(), 0, "|");
			imp.say(imp.pRow(), 30, "VENDA TOTAL: "+Funcoes.strDecimalToStrCurrency(20,2,""+bTotalVenda)+
							"   LUCRO ESTIMADO: "+Funcoes.strDecimalToStrCurrency(20,2,""+bTotalLucro));
			imp.say(imp.pRow(),135, "|");
			imp.say(imp.pRow()+1, 0, imp.comprimido());
			imp.say(imp.pRow(), 0, "|");
			
			if (bTotalVenda.doubleValue() != 0)
				bTotalMargem = bTotalLucro.divide(bTotalVenda,4,BigDecimal.ROUND_HALF_UP);
			bTotalMargem = bTotalMargem.multiply(new BigDecimal("100"));
			bTotalMargem = bTotalMargem.setScale(2);
			
			imp.say(imp.pRow(), 30, "CUSTO TOTAL: "+Funcoes.strDecimalToStrCurrency(20,2,""+bTotalCusto)+
							"   MARGEM MEDIA: "+Funcoes.copy(bTotalMargem+"%",0,6));
			imp.say(imp.pRow(),135, "|");
			imp.say(imp.pRow()+1, 0, imp.comprimido());
			imp.say(imp.pRow(), 0, "|");
			imp.say(imp.pRow(), 30, "DESCONTOS:   "+Funcoes.strDecimalToStrCurrency(20,2,""+bTotalDesc));
			imp.say(imp.pRow(),135, "|");
			imp.say(imp.pRow()+1, 0, imp.comprimido());
			imp.say(imp.pRow(), 0, "+" + Funcoes.replicate("=",133) + "+");      
			  
			imp.eject();      
			imp.fechaGravacao();
			  
			if (!con.getAutoCommit())
				con.commit();
		}  catch ( SQLException err ) {
			err.printStackTrace();
			Funcoes.mensagemErro(this,"Erro consulta ao relatório de vendas fisico!"+err.getMessage());      
		} finally {
			ps = null;
			rs = null;
			sWhere = null;
			sWhere1 = null;
			sWhere2 = null;
			sWhere3 = null;
			sCab= null;
			sCodGrup = null;
			sOrder = null;
			sCodProd = null;	
			bTotalVendaGrupo = null;
			bTotalCustoGrupo = null;
			bTotalDescGrupo = null;
			bTotalLucroGrupo = null;
			bTotalVenda = null;
			bTotalCusto = null;
			bTotalDesc = null;
			bTotalLucro = null;
			bTotalMargem = null;
			bUnit = null;
			bTotCusto = null;
			bLucro = null;
			bTotLucro = null;
			bMargem = null;
			bTotalMargemGrupo = null;
			System.gc();
		}
		    
		if (bVisualizar)
			imp.preview(this);
		else
			imp.print();	
	}
}
