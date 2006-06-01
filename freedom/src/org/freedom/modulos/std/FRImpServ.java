/**
 * @version 13/05/2004 <BR>
 * @author Setpoint Informática Ltda./Robson Sanchez <BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.std <BR>
 * Classe:
 * @(#)FRImpServ.java <BR>
 * 
 * Este programa é licenciado de acordo com a LPG-PC (Licença Pública Geral para
 * Programas de Computador), <BR>
 * versão 2.1.0 ou qualquer versão posterior. <BR>
 * A LPG-PC deve acompanhar todas PUBLICAÇÕES, DISTRIBUIÇÕES e REPRODUÇÕES deste
 * Programa. <BR>
 * Caso uma cópia da LPG-PC não esteja disponível junto com este Programa, você
 * pode contatar <BR>
 * o LICENCIADOR ou então pegar uma cópia em: <BR>
 * Licença: http://www.lpg.adv.br/licencas/lpgpc.rtf <BR>
 * Para poder USAR, PUBLICAR, DISTRIBUIR, REPRODUZIR ou ALTERAR este Programa é
 * preciso estar <BR>
 * de acordo com os termos da LPG-PC <BR>
 * <BR>
 * 
 * Relatório de impostos sobre serviços
 *  
 */

package org.freedom.modulos.std;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.freedom.componentes.JLabelPad;

import org.freedom.componentes.ImprimeOS;
import org.freedom.componentes.JCheckBoxPad;
import org.freedom.componentes.JRadioGroup;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FRelatorio;

public class FRImpServ extends FRelatorio {
	
	private static final long serialVersionUID = 1L;
	private JTextFieldPad txtDataini = new JTextFieldPad(JTextFieldPad.TP_DATE,10, 0);
	private JTextFieldPad txtDatafim = new JTextFieldPad(JTextFieldPad.TP_DATE,10, 0);
	private JCheckBoxPad cbVendas = new JCheckBoxPad("Só vendas?", "S", "N");
	private JRadioGroup rgFormato = null;
	private String linhaFina = Funcoes.replicate("-", 133);
	private String linhaLarga = Funcoes.replicate("=", 133);
	private double dTotMesBase = 0;
	private double dTotMesISS = 0;
	private double dTotMesPIS = 0;
	private double dTotMesCOFINS = 0;
	private double dTotMesIR = 0;
	private double dTotMesCSocial = 0;
	private double dTotMesLiq = 0;
	private double dTotBase = 0;
	private double dTotISS = 0;
	private double dTotPIS = 0;
	private double dTotCOFINS = 0;
	private double dTotIR = 0;
	private double dTotCSocial = 0;
	private double dTotLiq = 0;

	public FRImpServ() {
		setTitulo("Relatório de Impostos/Serviços");
		setAtribos(80, 80, 310, 210);

		GregorianCalendar cal = new GregorianCalendar();
		cal.add(Calendar.DATE, -30);
		txtDataini.setVlrDate(cal.getTime());
		cal.add(Calendar.DATE, 30);
		txtDatafim.setVlrDate(cal.getTime());
		txtDataini.setRequerido(true);
		txtDatafim.setRequerido(true);

		cbVendas.setVlrString("S");

		rgFormato = new JRadioGroup(1, 2, new Object[] { "Detalhado", "Resumido" }, new Object[] { "D", "R" });
		rgFormato.setVlrString("D");

		adic(new JLabelPad("Período"), 7, 0, 250, 20);
		adic(txtDataini, 7, 20, 100, 20);
		adic(txtDatafim, 110, 20, 100, 20);
		adic(new JLabelPad("Formato:"), 7, 40, 250, 20);
		adic(rgFormato, 7, 60, 203, 30);
		adic(cbVendas, 7, 100, 100, 25);
		
	}

	public void imprimir(boolean bVisualizar) {
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sSql = null;
		ImprimeOS imp = null;
		int iMesAnt = -1;
		int iLinPag = 0;

		try {

			if (txtDataini.getVlrString().length() < 10 || txtDatafim.getVlrString().length() < 10) {
				Funcoes.mensagemInforma(this, "Período inválido!");
				return;
			}
			
			imp = new ImprimeOS("", con);
			iLinPag = imp.verifLinPag() - 1;

			if (rgFormato.getVlrString().equals("D")) {
				sSql = "SELECT V.DTEMITVENDA,V.CODVENDA,V.DOCVENDA,V.SERIE,V.CODCLI,"
						+ "C.RAZCLI,V.VLRBASEISSVENDA,V.VLRISSVENDA,V.VLRPISVENDA,"
						+ "V.VLRCOFINSVENDA,V.VLRIRVENDA,V.VLRCSOCIALVENDA,V.VLRLIQVENDA "
						+ "FROM VDVENDA V,VDCLIENTE C, EQTIPOMOV TM "
						+ "WHERE V.CODEMP=? AND V.CODFILIAL=? "
						+ "AND V.DTEMITVENDA BETWEEN ? AND ? "
						+ "AND TM.CODTIPOMOV=V.CODTIPOMOV AND TM.CODEMP=V.CODEMPTM "
						+ "AND TM.CODFILIAL=V.CODFILIALTM AND C.CODEMP=V.CODEMPCL "
						+ "AND C.CODFILIAL=V.CODFILIALCL AND C.CODCLI=V.CODCLI "
						+ (cbVendas.getVlrString().equals("S") ? " AND (TM.TIPOMOV='VD' OR TM.TIPOMOV='SE') " : "") + "ORDER BY V.DTEMITVENDA, V.CODVENDA";
			} else {
				sSql = "SELECT EXTRACT(YEAR FROM V.DTEMITVENDA),"
						+ "EXTRACT(MONTH FROM V.DTEMITVENDA),SUM(V.VLRBASEISSVENDA),"
						+ "SUM(V.VLRISSVENDA),SUM(V.VLRPISVENDA),SUM(V.VLRCOFINSVENDA),"
						+ "SUM(V.VLRIRVENDA),SUM(V.VLRCSOCIALVENDA),SUM(V.VLRLIQVENDA) "
						+ "FROM VDVENDA V, EQTIPOMOV TM "
						+ "WHERE V.CODEMP=? AND V.CODFILIAL=? "
						+ "AND V.DTEMITVENDA BETWEEN ? AND ? "
						+ "AND TM.CODTIPOMOV=V.CODTIPOMOV AND TM.CODEMP=V.CODEMPTM "
						+ "AND TM.CODFILIAL=V.CODFILIALTM "
						+ (cbVendas.getVlrString().equals("S") ? " AND (TM.TIPOMOV='VD' OR TM.TIPOMOV='SE') " : "")
						+ "GROUP BY EXTRACT(MONTH FROM V.DTEMITVENDA), EXTRACT(YEAR FROM V.DTEMITVENDA) " 
						+ "ORDER BY 1,2";
			}

			try {
				ps = con.prepareStatement(sSql);
				ps.setInt(1, Aplicativo.iCodEmp);
				ps.setInt(2, ListaCampos.getMasterFilial("VDVENDA"));
				ps.setDate(3, Funcoes.dateToSQLDate(txtDataini.getVlrDate()));
				ps.setDate(4, Funcoes.dateToSQLDate(txtDatafim.getVlrDate()));
				rs = ps.executeQuery();

				imp.limpaPags();
				imp.montaCab();		
				imp.setTitulo("Relatorio de Impostos/Serviços");
				imp.addSubTitulo("IMPOSTOS SOBRE SERVICOS");
				if (cbVendas.getVlrString().equals("S"))
					imp.addSubTitulo("SOMENTE VENDAS");
				imp.addSubTitulo("PERIODO DE: " + txtDataini.getVlrString() + " ATE: " + txtDatafim.getVlrString());

				if (rgFormato.getVlrString().equals("D")) {
					while (rs.next()) {
						if (imp.pRow() >= (iLinPag - 1)) {
							imp.pulaLinha( 1, imp.comprimido());
							imp.say(  0, "+" + linhaFina + "+");
							imp.incPags();
							imp.eject();
						}
						if (imp.pRow() == 0) {												
							imp.impCab(136, true);
							imp.say(  0, imp.comprimido());
							imp.say(  0, 0, "|");
							imp.say(  0, 135, "|");
							imp.pulaLinha( 1, imp.comprimido());
							imp.say(  0, "+" + linhaFina + "+");
							imp.pulaLinha( 1, imp.comprimido());
							imp.say(  0, "|Pedido ");
							imp.say( 10, "Doc ");
							imp.say( 19, "Ser");
							imp.say( 23, "Emissão ");
							imp.say( 34, "Codigo e Razão do cliente");
							imp.say( 60, "Base");
							imp.say( 71, "ISS");
							imp.say( 82, "PIS");
							imp.say( 92, "Cofins");
							imp.say(103, "IR");
							imp.say(114, "C.SOCIAL");
							imp.say(125, "V.LIQ");
							imp.say(135, "|");
							imp.pulaLinha( 1, imp.comprimido());
							imp.say(  0, "+" + linhaFina + "+");
							
						}
						
						if (iMesAnt != Funcoes.sqlDateToGregorianCalendar( 
								rs.getDate("DtEmitVenda")).get(Calendar.MONTH) && iMesAnt >= 0) {
							
							impTotMes(imp);
							imp.pulaLinha( 1, imp.comprimido());
							imp.say(  0, "|Pedido ");
							imp.say( 10, "Doc ");
							imp.say( 19, "Ser.");
							imp.say( 23, "Emissão ");
							imp.say( 34, "Codigo e Razão do cliente");
							imp.say( 60, "Base");
							imp.say( 71, "ISS");
							imp.say( 82, "PIS");
							imp.say( 92, "Cofins");
							imp.say(103, "IR");
							imp.say(114, "C.SOCIAL");
							imp.say(125, "V.LIQ");
							imp.say(135, "|");
							imp.pulaLinha( 1, imp.comprimido());
							imp.say(  0, "+" + linhaFina + "+");
							
						}
						
						imp.pulaLinha( 1, imp.comprimido());
						imp.say(  0, "|" + Funcoes.strZero(rs.getString("CodVenda"), 7));
						imp.say( 10, Funcoes.strZero(rs.getString("DocVenda"), 7));
						imp.say( 19, Funcoes.copy(rs.getString("Serie"), 4));
						imp.say( 23, Funcoes.sqlDateToStrDate(rs.getDate("DtEmitVenda")));
						imp.say( 34, Funcoes.copy(rs.getInt("CodCli") + " - " + rs.getString("RazCli"), 25));
						imp.say( 60, Funcoes.strDecimalToStrCurrency(10, 2, rs.getString("VlrBaseIssVenda")));
						imp.say( 71, Funcoes.strDecimalToStrCurrency(10, 2, rs.getString("VlrIssVenda")));
						imp.say( 82, Funcoes.strDecimalToStrCurrency(10, 2, rs.getString("VlrPisVenda")));
						imp.say( 92, Funcoes.strDecimalToStrCurrency(9, 2, rs.getString("VlrCofinsVenda")));
						imp.say( 103, Funcoes.strDecimalToStrCurrency(10, 2, rs.getString("VlrIRVenda")));
						imp.say( 114, Funcoes.strDecimalToStrCurrency(10, 2, rs.getString("VlrCSocialVenda")));
						imp.say( 125, Funcoes.strDecimalToStrCurrency(10, 2, rs.getString("VlrLiqVenda")));
						imp.say( 135, "|");
						
						iMesAnt = Funcoes.sqlDateToGregorianCalendar(rs.getDate("DtEmitVenda")).get(Calendar.MONTH);
						
						dTotMesBase += rs.getDouble("VlrBaseIssVenda");
						dTotMesISS += rs.getDouble("VlrIssVenda");
						dTotMesPIS += rs.getDouble("VlrPisVenda");
						dTotMesCOFINS += rs.getDouble("VlrCofinsVenda");
						dTotMesIR += rs.getDouble("VlrIRVenda");
						dTotMesCSocial += rs.getDouble("VlrCSocialVenda");
						dTotMesLiq += rs.getDouble("VlrLiqVenda");
						
					}
					
				} else {
					
					while (rs.next()) {
						if (imp.pRow() >= (iLinPag - 1)) {
							imp.pulaLinha( 1, imp.comprimido());
							imp.say(  0, "+" + linhaFina + "+");
							imp.incPags();
							imp.eject();
						}
						if (imp.pRow() == 0) {			
							imp.impCab(136, true);
							imp.say(  0, imp.comprimido());
							imp.say(  0, "|");
							imp.say(135, "|");
							imp.pulaLinha( 1, imp.comprimido());
							imp.say(  0, "|" + linhaFina + "|");
							imp.pulaLinha( 1, imp.comprimido());
							imp.say(  0, "| Mes/Ano");
							imp.say( 10, "| Base");
							imp.say( 28, "| ISS");
							imp.say( 46, "| PIS");
							imp.say( 64, "| COFINS");
							imp.say( 82, "| IR");
							imp.say(100, "| C.SOCIAL");
							imp.say(118, "| Tot. Venda.");
							imp.say(135, "|");
							imp.pulaLinha( 1, imp.comprimido());
							imp.say(  0, "|" + linhaFina + "|");
							
						}
						
						imp.pulaLinha( 1, imp.comprimido());
						imp.say(  0, "| " + Funcoes.strZero(rs.getString(2), 2) + "/" + Funcoes.strZero(rs.getString(1), 4));
						imp.say( 10, "| " + Funcoes.strDecimalToStrCurrency(15, 2, rs.getString(3)));
						imp.say( 28, "| " + Funcoes.strDecimalToStrCurrency(15, 2, rs.getString(4)));
						imp.say( 46, "| " + Funcoes.strDecimalToStrCurrency(15, 2, rs.getString(5)));
						imp.say( 64, "| " + Funcoes.strDecimalToStrCurrency(15, 2, rs.getString(6)));
						imp.say( 82, "| " + Funcoes.strDecimalToStrCurrency(15, 2, rs.getString(7)));
						imp.say(100, "| " + Funcoes.strDecimalToStrCurrency(15, 2, rs.getString(8)));
						imp.say(118, "| " + Funcoes.strDecimalToStrCurrency(15, 2, rs.getString(9)));
						imp.say(135, "|");
					}
				}

				impTotMes(imp);
				impTotGeral(imp);

				imp.eject();
				imp.fechaGravacao();

				rs.close();
				ps.close();
				if (!con.getAutoCommit())
					con.commit();

			} catch (SQLException err) {
				Funcoes.mensagemErro(this, "Erro executando a consulta.\n" + err.getMessage(),true,con,err);
				err.printStackTrace();
			}
			
			if (bVisualizar)
				imp.preview(this);
			else
				imp.print();

		} finally {
			sSql = null;
			imp = null;
			ps = null;
			rs = null;
		}

	}

	private void impTotMes(ImprimeOS imp) {

		imp.pulaLinha( 1, imp.comprimido());
		imp.say(  0, "|" + Funcoes.replicate("-", 133) + "|");
		imp.pulaLinha( 1, imp.comprimido());
		imp.say(  0, "|     TOTAIS DO MES -->");
		imp.say( 57, "|" + Funcoes.strDecimalToStrCurrency(10, 2, String.valueOf( dTotMesBase )));
		imp.say( 69, Funcoes.strDecimalToStrCurrency(10, 2, String.valueOf( dTotMesISS )));
		imp.say( 80, Funcoes.strDecimalToStrCurrency(10, 2, String.valueOf( dTotMesPIS )));
		imp.say( 90, Funcoes.strDecimalToStrCurrency(10, 2, String.valueOf( dTotMesCOFINS )));
		imp.say(101, Funcoes.strDecimalToStrCurrency(10, 2, String.valueOf( dTotMesIR )));
		imp.say(112, Funcoes.strDecimalToStrCurrency(10, 2, String.valueOf( dTotMesCSocial )));
		imp.say(123, Funcoes.strDecimalToStrCurrency(10, 2, String.valueOf( dTotMesLiq )));
		imp.say(135, "|");
		imp.pulaLinha( 1, imp.comprimido());
		imp.say(  0, "|" + linhaFina + "|");
		
		dTotBase += dTotMesBase;
		dTotISS += dTotMesISS;
		dTotPIS += dTotMesPIS;
		dTotCOFINS += dTotMesCOFINS;
		dTotIR += dTotMesIR;
		dTotCSocial += dTotMesCSocial;
		dTotLiq += dTotMesLiq;
		dTotMesBase = 0;
		dTotMesISS = 0;
		dTotMesPIS = 0;
		dTotMesCOFINS = 0;
		dTotMesIR = 0;
		dTotMesCSocial = 0;
		dTotMesLiq = 0;
		
	}

	private void impTotGeral(ImprimeOS imp) throws SQLException {

		imp.pulaLinha( 1, imp.comprimido());
		imp.say(  0, "|" + linhaLarga + "|");
		imp.pulaLinha( 1, imp.comprimido());
		imp.say( 0, "|     TOTAIS GERAIS -->");
		imp.say( 57, "|" + Funcoes.strDecimalToStrCurrency(10, 2, String.valueOf( dTotBase )));
		imp.say( 69, Funcoes.strDecimalToStrCurrency(10, 2, String.valueOf( dTotISS )));
		imp.say( 80, Funcoes.strDecimalToStrCurrency(10, 2, String.valueOf( dTotPIS )));
		imp.say( 90, Funcoes.strDecimalToStrCurrency(10, 2, String.valueOf( dTotCOFINS )));
		imp.say(101, Funcoes.strDecimalToStrCurrency(10, 2, String.valueOf( dTotIR )));
		imp.say(112, Funcoes.strDecimalToStrCurrency(10, 2, String.valueOf( dTotCSocial )));
		imp.say(123, Funcoes.strDecimalToStrCurrency(10, 2, String.valueOf( dTotLiq )));
		imp.say(135, "|");
		imp.pulaLinha( 1, imp.comprimido());
		imp.say(  0, "+" + linhaLarga + "+");
		
	}
}