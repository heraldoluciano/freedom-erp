/**
 * @version 02/11/2003 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)FRVendasDet.java <BR>
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

public class FRVendasDet extends FRelatorio {
	private static final long serialVersionUID = 1L;

	private JTextFieldPad txtDataini = new JTextFieldPad(JTextFieldPad.TP_DATE,10,0); 
	private JTextFieldPad txtDatafim = new JTextFieldPad(JTextFieldPad.TP_DATE,10,0); 
	private JTextFieldPad txtCodCli = new JTextFieldPad(JTextFieldPad.TP_STRING,8,0);
	private JTextFieldFK txtRazCli = new JTextFieldFK(JTextFieldPad.TP_STRING,50,0);
	private JTextFieldPad txtCodProd =new JTextFieldPad(JTextFieldPad.TP_STRING,8,0);
	private JTextFieldFK txtDescProd = new JTextFieldFK(JTextFieldPad.TP_STRING,50,0);
	private JCheckBoxPad cbVendaCanc = new JCheckBoxPad("Mostrar Canceladas", "S", "N");
	private JRadioGroup rgFaturados = null;
	private JRadioGroup rgFinanceiro = null;
	private Vector vLabsFat = new Vector();
	private Vector vValsFat = new Vector();
	private Vector vLabsFin = new Vector();
	private Vector vValsFin = new Vector();
	private ListaCampos lcCliente = new ListaCampos(this);
	private ListaCampos lcProd = new ListaCampos(this);
  
	public FRVendasDet() {
		setTitulo("Vendas Detalhadas");
		setAtribos(80,80,295,330);
		
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
			
		lcCliente.add(new GuardaCampo( txtCodCli, "CodCli", "Cód.cli.", ListaCampos.DB_PK, false));
		lcCliente.add(new GuardaCampo( txtRazCli, "RazCli", "Razão social do cliente", ListaCampos.DB_SI, false));
		txtCodCli.setTabelaExterna(lcCliente);
		txtCodCli.setNomeCampo("CodCli");
		txtCodCli.setFK(true);
		lcCliente.setReadOnly(true);
		lcCliente.montaSql(false, "CLIENTE", "VD");
			
		lcProd.add(new GuardaCampo( txtCodProd, "CodProd", "Cód.prod.", ListaCampos.DB_PK, false));
		lcProd.add(new GuardaCampo( txtDescProd, "DescProd", "Descrição do produto", ListaCampos.DB_SI, false));
		txtCodProd.setTabelaExterna(lcProd);
		txtCodProd.setNomeCampo("CodProd");
		txtCodProd.setFK(true);
		lcProd.setReadOnly(true);
		lcProd.montaSql(false, "PRODUTO", "EQ");
			
		txtDataini.setRequerido(true);
		txtDatafim.setRequerido(true);
		
		GregorianCalendar cal = new GregorianCalendar();
		txtDatafim.setVlrDate(cal.getTime());
		cal.roll(Calendar.MONTH,-1);
		txtDataini.setVlrDate(cal.getTime());
		    
		JLabelPad lbLinha = new JLabelPad();
		lbLinha.setBorder(BorderFactory.createEtchedBorder());
		    
		adic(new JLabelPad("Periodo:"),7,5,100,20);
		adic(lbLinha,60,15,210,2);
		adic(new JLabelPad("De:"),7,30,30,20);
		adic(txtDataini,32,30,97,20);
		adic(new JLabelPad("Até:"),140,30,30,20);
		adic(txtDatafim,170,30,100,20);
		
		adic(new JLabelPad("Cód.cli."),7,60,80,20);
		adic(txtCodCli,7,80,80,20);
		adic(new JLabelPad("Razão social do cliente"),90,60,200,20);
		adic(txtRazCli,90,80,183,20);
		adic(new JLabelPad("Cód.prod"),7,100,80,20);
		adic(txtCodProd,7,120,80,20);
		adic(new JLabelPad("Descrição do produto"),90,100,200,20);
		adic(txtDescProd,90,120,183,20);
		
		adic(rgFaturados, 7, 150, 120, 70);
		adic(rgFinanceiro, 153, 150, 120, 70);
		adic(cbVendaCanc, 7, 230, 200, 20);
			
	}
  
	public void imprimir(boolean bVisualizar) {
		
		if (txtDatafim.getVlrDate().before(txtDataini.getVlrDate())) {
			Funcoes.mensagemInforma(this, "Data final maior que a data inicial!");
			return;
		}

		PreparedStatement ps = null;
		ResultSet rs = null;
		String sSQL = null;
		String sCab = "";
		String sWhere1 = null;
		String sWhere2 = null;
		String sWhere3 = "";
		String sWhere4 = "";
		String sWhere5 = "";
		String sLinFina = Funcoes.replicate("-", 133);
		String sLinLarga = Funcoes.replicate("=", 133);
		BigDecimal bVlrDesc = new BigDecimal("0");
		BigDecimal bVlrLiq = new BigDecimal("0");	
		ImprimeOS imp = null;
		int linPag = 0;
		int iCodVendaAnt = 0;	
		boolean bComRef = comRef();
					
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
		if(txtCodCli.getVlrInteger().intValue() > 0)
			sWhere4 = " AND C.CODCLI=" + txtCodCli.getVlrInteger().intValue() + " ";		
		if(txtCodProd.getVlrInteger().intValue() > 0)
			sWhere5 = " AND IT.CODPROD=" + txtCodProd.getVlrInteger().intValue() + " ";		
		
		try {
			
			imp = new ImprimeOS("", con);
			linPag = imp.verifLinPag()-1;
			imp.montaCab();
			imp.setTitulo("Relatório de Vendas Detalhado");
			imp.addSubTitulo("RELATORIO DE VENDAS DETALHADO   -   PERIODO DE :"+ txtDataini.getVlrString() + " Até: " + txtDatafim.getVlrString() + sCab);
			imp.limpaPags();

			sSQL =  "SELECT (SELECT VO.CODORC " +
					        "FROM VDVENDAORC VO " +
					        "WHERE VO.CODEMP=IT.CODEMP AND VO.CODFILIAL=IT.CODFILIAL " +
					        "AND VO.CODVENDA=IT.CODVENDA AND VO.CODITVENDA=IT.CODITVENDA AND VO.TIPOVENDA=IT.TIPOVENDA), " +
					"V.CODVENDA,V.DOCVENDA,V.DTEMITVENDA,V.DTSAIDAVENDA,PP.DESCPLANOPAG,V.CODCLI," +
					"C.RAZCLI,V.VLRDESCVENDA,V.VLRLIQVENDA,IT.CODPROD,IT.REFPROD,P.DESCPROD," +
					"IT.QTDITVENDA,IT.PRECOITVENDA,IT.VLRDESCITVENDA,IT.VLRLIQITVENDA " +
					"FROM VDVENDA V, FNPLANOPAG PP, VDCLIENTE C, VDITVENDA IT, EQPRODUTO P , EQTIPOMOV TM " +
					"WHERE V.DTEMITVENDA BETWEEN ? AND ? AND V.CODEMP=? AND V.CODFILIAL=? " +
					"AND PP.CODEMP=V.CODEMPPG AND PP.CODFILIAL=V.CODFILIAL AND PP.CODPLANOPAG=V.CODPLANOPAG " +
					"AND C.CODEMP=V.CODEMPCL AND C.CODFILIAL=V.CODFILIALCL AND C.CODCLI=V.CODCLI " +
					"AND TM.CODEMP=V.CODEMPTM AND TM.CODFILIAL=V.CODFILIALTM AND TM.CODTIPOMOV=V.CODTIPOMOV " +
					"AND IT.CODEMP=V.CODEMP AND IT.CODFILIAL=V.CODFILIAL AND IT.CODVENDA=V.CODVENDA AND IT.TIPOVENDA=V.TIPOVENDA " +
					"AND P.CODEMP=IT.CODEMPPD AND P.CODFILIAL=IT.CODFILIALPD AND P.CODPROD=IT.CODPROD " +
				    sWhere1 + sWhere2 + sWhere3 + sWhere4 + sWhere5 +			  
				    "ORDER BY V.CODVENDA,IT.CODITVENDA,V.DTEMITVENDA";
			
			ps = con.prepareStatement(sSQL);
			ps.setDate(1, Funcoes.dateToSQLDate(txtDataini.getVlrDate()));
			ps.setDate(2, Funcoes.dateToSQLDate(txtDatafim.getVlrDate()));
			ps.setInt(3, Aplicativo.iCodEmp);
			ps.setInt(4, ListaCampos.getMasterFilial("VDVENDA"));
			rs = ps.executeQuery();		
			while (rs.next()) {
				if (imp.pRow()>=(linPag-1)) {
					imp.pulaLinha( 1, imp.comprimido());
					imp.say(  0, "+" + sLinFina + "+");
					imp.incPags();
					imp.eject();
				}
				if (imp.pRow() == 0) {            	
					imp.impCab(136, true);    
					imp.pulaLinha( 0, imp.comprimido());
					imp.say(  0, "|" + sLinFina + "|");
				}
				if (iCodVendaAnt != rs.getInt("CodVenda")) {
					
					if (iCodVendaAnt != 0) {
						
						imp.pulaLinha( 1, imp.comprimido());
						imp.say(  0, "|" + sLinLarga + "|");
						imp.pulaLinha( 1, imp.comprimido());
						imp.say(  0, "|");
						imp.say( 64, " Totais da venda: ");
						imp.say( 94, "| " + Funcoes.strDecimalToStrCurrency(12,2, String.valueOf(bVlrDesc)));
						imp.say(109, "| " + Funcoes.strDecimalToStrCurrency(12,2, String.valueOf(bVlrLiq)));
						imp.say(124, "|");
						imp.say(135, "|");
						imp.pulaLinha( 1, imp.comprimido());
						imp.say(  0, "|" + sLinLarga + "|");
					
					}
					
					imp.pulaLinha( 1, imp.comprimido());
					imp.say(  0, "| Pedido: ");
					imp.say( 10, Funcoes.strZero(rs.getString("CodVenda"), 8));
					imp.say( 25, "Doc: ");
					imp.say( 30, Funcoes.strZero(rs.getString("DocVenda"), 8));
					imp.say( 45, "Emissão: ");
					imp.say( 53, Funcoes.sqlDateToStrDate(rs.getDate("DtEmitVenda")));
					imp.say( 68, "Saida: ");
					imp.say( 75, Funcoes.sqlDateToStrDate(rs.getDate("DtSaidaVenda")));
					imp.say( 90, "Plano Pagto.: ");
					imp.say(104, Funcoes.copy(rs.getString("DescPlanoPag"), 30));
					imp.say(135, "|");
					imp.pulaLinha( 1, imp.comprimido());
					imp.say(  0, "| Cliente: ");
					imp.say( 11, rs.getInt("CodCli") + " - " + rs.getString("RazCli"));
					imp.say(135, "|");
					imp.pulaLinha( 1, imp.comprimido());
					imp.say(  0, "|" + sLinFina + "|");
					imp.pulaLinha( 1, imp.comprimido());
					imp.say(  0, "| Cod/Ref");
					imp.say( 16, "| Descrição");
					imp.say( 69, "| Quant.");
					imp.say( 79, "| Preco");
					imp.say( 94, "| Vlr.Desc.");
					imp.say(109, "| Vlr.Liq.");
					imp.say(124, "| Orcam.");
					imp.say(135, "|");
					imp.pulaLinha( 1, imp.comprimido());
					imp.say(imp.pRow(), 0, "|" + sLinFina + "|");
					bVlrDesc = rs.getBigDecimal("VlrDescVenda");
					bVlrLiq = rs.getBigDecimal("VlrLiqVenda");
					
				}
				
				imp.pulaLinha( 1, imp.comprimido());
				imp.say(  0, "| " + (bComRef ? rs.getString("RefProd") : rs.getString("CodProd")));
				imp.say( 16, "| " + rs.getString("DescProd"));
				imp.say( 69, "| " + rs.getBigDecimal("QtdItVenda").setScale(1,BigDecimal.ROUND_HALF_UP));
				imp.say( 79, "| " + Funcoes.strDecimalToStrCurrency(12,2, String.valueOf(rs.getFloat("PrecoItVenda"))));
				imp.say( 94, "| " + Funcoes.strDecimalToStrCurrency(12,2, String.valueOf(rs.getFloat("VlrDescItVenda"))));
				imp.say(109, "| " + Funcoes.strDecimalToStrCurrency(12,2, String.valueOf(rs.getFloat("VlrLiqItVenda"))));
				imp.say(124, "| " + (rs.getString(1) != null ? rs.getString(1) : ""));
				imp.say(135, "|");			
					
				iCodVendaAnt = rs.getInt("CodVenda");	
				
			}
			
			imp.eject();
			imp.fechaGravacao();
			
			if (!con.getAutoCommit())
				con.commit();
		} catch (SQLException err) {
			Funcoes.mensagemErro(this, "Erro consulta tabela de preços!"+ err.getMessage(),true,con,err);
			err.printStackTrace();
		} finally {		
			ps = null;
			rs = null;
			sSQL = null;
			sCab = null;
			sWhere1 = null;
			sWhere2 = null;
			sWhere3 = null;
			sWhere4 = null;
			sWhere5 = null;	
			bVlrDesc = null;
			bVlrLiq = null;	
			System.gc();
		}		
		if (bVisualizar) 
			imp.preview(this);
		else 
			imp.print();
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
			if (!con.getAutoCommit())
			  	con.commit();
		} catch (SQLException err) {
			Funcoes.mensagemErro(this,"Erro ao carregar a tabela PREFERE1!\n"+err.getMessage(),true,con,err);
		} finally {
			ps = null;
			rs = null;
			sSQL = null;
		}
		return bRetorno;
	}
  
  	public void setConexao(Connection cn) {
  		super.setConexao(cn);
  		lcProd.setConexao(cn);
  		lcCliente.setConexao(cn);  		
  	}
}
