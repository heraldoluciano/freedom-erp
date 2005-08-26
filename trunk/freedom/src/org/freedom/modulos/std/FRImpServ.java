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


	private JTextFieldPad txtDataini = new JTextFieldPad(JTextFieldPad.TP_DATE,
			10, 0);

	private JTextFieldPad txtDatafim = new JTextFieldPad(JTextFieldPad.TP_DATE,
			10, 0);

	private JCheckBoxPad cbVendas = new JCheckBoxPad("Só vendas?", "S", "N");

	private JRadioGroup rgFormato = null;

	double dTotMesBase = 0;

	double dTotMesISS = 0;

	double dTotMesPIS = 0;

	double dTotMesCOFINS = 0;

	double dTotMesIR = 0;

	double dTotMesCSocial = 0;

	double dTotMesLiq = 0;

	double dTotBase = 0;

	double dTotISS = 0;

	double dTotPIS = 0;

	double dTotCOFINS = 0;

	double dTotIR = 0;

	double dTotCSocial = 0;

	double dTotLiq = 0;

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

		rgFormato = new JRadioGroup(1, 2, new Object[] { "Detalhado",
				"Resumido" }, new Object[] { "D", "R" });
		rgFormato.setVlrString("D");

		adic(new JLabelPad("Período"), 7, 0, 250, 20);
		adic(txtDataini, 7, 20, 100, 20);
		adic(txtDatafim, 110, 20, 100, 20);
		adic(new JLabelPad("Formato:"), 7, 40, 250, 20);
		adic(rgFormato, 7, 60, 203, 30);
		adic(cbVendas, 7, 100, 100, 25);
	}

	public void imprimir(boolean bVisualizar) {
		String sSql = "";
		String sFiltros = "";
		ImprimeOS imp = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int iMesAnt = -1;
		int iLinPag = 0;

		try {

			if (txtDataini.getVlrString().length() < 10
					|| txtDatafim.getVlrString().length() < 10) {
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
						+ "WHERE V.CODEMP=? AND V.CODFILIAL=? AND "
						+ "V.DTEMITVENDA BETWEEN ? AND ?"
						+ " AND TM.CODTIPOMOV=V.CODTIPOMOV AND TM.CODEMP=V.CODEMPTM"
						+ " AND TM.CODFILIAL=V.CODFILIALTM AND C.CODEMP=V.CODEMPCL"
						+ " AND C.CODFILIAL=V.CODFILIALCL AND C.CODCLI=V.CODCLI "
						+ (cbVendas.getVlrString().equals("S") ? " AND (TM.TIPOMOV='VD' OR TM.TIPOMOV='SE') "
								: "") + "ORDER BY V.DTEMITVENDA, V.CODVENDA";
			} else {
				sSql = "SELECT EXTRACT(YEAR FROM V.DTEMITVENDA),"
						+ "EXTRACT(MONTH FROM V.DTEMITVENDA),SUM(V.VLRBASEISSVENDA),"
						+ "SUM(V.VLRISSVENDA),SUM(V.VLRPISVENDA),SUM(V.VLRCOFINSVENDA),"
						+ "SUM(V.VLRIRVENDA),SUM(V.VLRCSOCIALVENDA),SUM(V.VLRLIQVENDA) "
						+ "FROM VDVENDA V, EQTIPOMOV TM "
						+ "WHERE V.CODEMP=? AND V.CODFILIAL=? AND "
						+ "V.DTEMITVENDA BETWEEN ? AND ?"
						+ " AND TM.CODTIPOMOV=V.CODTIPOMOV AND TM.CODEMP=V.CODEMPTM"
						+ " AND TM.CODFILIAL=V.CODFILIALTM "
						+ (cbVendas.getVlrString().equals("S") ? " AND (TM.TIPOMOV='VD' OR TM.TIPOMOV='SE') "
								: "")
						+ "GROUP BY EXTRACT(MONTH FROM V.DTEMITVENDA),"
						+ "EXTRACT(YEAR FROM V.DTEMITVENDA) " + "ORDER BY 1,2";
			}

			//System.out.println(sSql);

			try {
				ps = con.prepareStatement(sSql);
				ps.setInt(1, Aplicativo.iCodEmp);
				ps.setInt(2, ListaCampos.getMasterFilial("VDVENDA"));
				ps.setDate(3, Funcoes.dateToSQLDate(txtDataini.getVlrDate()));
				ps.setDate(4, Funcoes.dateToSQLDate(txtDatafim.getVlrDate()));

				if (cbVendas.getVlrString().equals("S")) {
					sFiltros = "SOMENTE VENDAS";
				}
				rs = ps.executeQuery();

				imp.limpaPags();
				
				imp.setTitulo("Relatorio de Impostos/Serviços");
				imp.addSubTitulo("IMPOSTOS SOBRE SERVICOS");
				if (!sFiltros.equals("")) {
					imp.addSubTitulo(sFiltros);
				}

				if (rgFormato.getVlrString().equals("D")) {
					while (rs.next()) {
						if (imp.pRow() >= (iLinPag - 1)) {
							imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
							imp.say(imp.pRow() + 0, 0, "+"
									+ Funcoes.replicate("-", 134) + "+");
							imp.incPags();
							imp.eject();
						}
						if (imp.pRow() == 0) {
							imp.montaCab();														
							imp.impCab(136, true);

							imp.say(imp.pRow() + 0, 0, "" + imp.comprimido());
							imp.say(imp.pRow() + 0, 0, "|");
							imp.say(imp.pRow() + 0, 50, "PERIODO DE: "
									+ txtDataini.getVlrString() + " ATE: "
									+ txtDatafim.getVlrString());
							imp.say(imp.pRow() + 0, 135, "|");
							imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
							imp.say(imp.pRow() + 0, 0, "+"
									+ Funcoes.replicate("-", 133) + "+");
							imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
							imp.say(imp.pRow() + 0, 0, "|Pedido ");
							imp.say(imp.pRow() + 0, 10, "Doc ");
							imp.say(imp.pRow() + 0, 19, "Ser");
							imp.say(imp.pRow() + 0, 23, "Emissão ");
							imp.say(imp.pRow() + 0, 34,
									"Codigo e Razão do cliente");
							imp.say(imp.pRow() + 0, 60, "Base");
							imp.say(imp.pRow() + 0, 71, "ISS");
							imp.say(imp.pRow() + 0, 82, "PIS");
							imp.say(imp.pRow() + 0, 92, "Cofins");
							imp.say(imp.pRow() + 0, 103, "IR");
							imp.say(imp.pRow() + 0, 114, "C.SOCIAL");
							imp.say(imp.pRow() + 0, 125, "V.LIQ");
							imp.say(imp.pRow() + 0, 135, "|");
							imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
							imp.say(imp.pRow() + 0, 0, "+"
									+ Funcoes.replicate("-", 133) + "+");
						}
						if (iMesAnt != Funcoes.sqlDateToGregorianCalendar(
								rs.getDate("DtEmitVenda")).get(Calendar.MONTH)
								&& iMesAnt >= 0) {
							impTotMes(imp);
							imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
							imp.say(imp.pRow() + 0, 0, "|Pedido ");
							imp.say(imp.pRow() + 0, 10, "Doc ");
							imp.say(imp.pRow() + 0, 19, "Ser.");
							imp.say(imp.pRow() + 0, 23, "Emissão ");
							imp.say(imp.pRow() + 0, 34,
									"Codigo e Razão do cliente");
							imp.say(imp.pRow() + 0, 60, "Base");
							imp.say(imp.pRow() + 0, 71, "ISS");
							imp.say(imp.pRow() + 0, 82, "PIS");
							imp.say(imp.pRow() + 0, 92, "Cofins");
							imp.say(imp.pRow() + 0, 103, "IR");
							imp.say(imp.pRow() + 0, 114, "C.SOCIAL");
							imp.say(imp.pRow() + 0, 125, "V.LIQ");
							imp.say(imp.pRow() + 0, 135, "|");
							imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
							imp.say(imp.pRow() + 0, 0, "+"
									+ Funcoes.replicate("-", 134) + "+");
						}
						imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
						imp.say(imp.pRow() + 0, 0, "|"
								+ Funcoes.strZero(rs.getString("CodVenda"), 7));
						imp.say(imp.pRow() + 0, 10, Funcoes.strZero(rs
								.getString("DocVenda"), 7));
						imp.say(imp.pRow() + 0, 19, Funcoes.copy(rs
								.getString("Serie"), 4));
						imp.say(imp.pRow() + 0, 23, Funcoes.sqlDateToStrDate(rs
								.getDate("DtEmitVenda")));
						imp.say(imp.pRow() + 0, 34, Funcoes.copy(rs
								.getInt("CodCli")
								+ " - " + rs.getString("RazCli"), 25));
						imp.say(imp.pRow() + 0, 60, Funcoes
								.strDecimalToStrCurrency(10, 2, rs
										.getString("VlrBaseIssVenda")));
						dTotMesBase += rs.getDouble("VlrBaseIssVenda");
						imp.say(imp.pRow() + 0, 71, Funcoes
								.strDecimalToStrCurrency(10, 2, rs
										.getString("VlrIssVenda")));
						dTotMesISS += rs.getDouble("VlrIssVenda");
						imp.say(imp.pRow() + 0, 82, Funcoes
								.strDecimalToStrCurrency(10, 2, rs
										.getString("VlrPisVenda")));
						dTotMesPIS += rs.getDouble("VlrPisVenda");
						imp.say(imp.pRow() + 0, 92, Funcoes
								.strDecimalToStrCurrency(9, 2, rs
										.getString("VlrCofinsVenda")));
						dTotMesCOFINS += rs.getDouble("VlrCofinsVenda");
						imp.say(imp.pRow() + 0, 103, Funcoes
								.strDecimalToStrCurrency(10, 2, rs
										.getString("VlrIRVenda")));
						dTotMesIR += rs.getDouble("VlrIRVenda");
						imp.say(imp.pRow() + 0, 114, Funcoes
								.strDecimalToStrCurrency(10, 2, rs
										.getString("VlrCSocialVenda")));
						dTotMesCSocial += rs.getDouble("VlrCSocialVenda");
						imp.say(imp.pRow() + 0, 125, Funcoes
								.strDecimalToStrCurrency(10, 2, rs
										.getString("VlrLiqVenda")));
						dTotMesLiq += rs.getDouble("VlrLiqVenda");
						imp.say(imp.pRow() + 0, 135, "|");
						iMesAnt = Funcoes.sqlDateToGregorianCalendar(
								rs.getDate("DtEmitVenda")).get(Calendar.MONTH);
					}
				} else {
					
					while (rs.next()) {
						if (imp.pRow() >= (iLinPag - 1)) {
							imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
							imp.say(imp.pRow() + 0, 0, "+"
									+ Funcoes.replicate("-", 134) + "+");
							imp.incPags();
							imp.eject();
						}

						if (imp.pRow() == 0) {
							imp.montaCab();							
							imp.impCab(136, true);

							imp.say(imp.pRow() + 0, 0, "" + imp.comprimido());
							imp.say(imp.pRow() + 0, 0, "|");
							imp.say(imp.pRow() + 0, 50, "PERIODO DE: "
									+ txtDataini.getVlrString() + " ATE: "
									+ txtDatafim.getVlrString());
							imp.say(imp.pRow() + 0, 135, "|");
							imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
							imp.say(imp.pRow() + 0, 0, "|"
									+ Funcoes.replicate("-", 133) + "|");
							imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
							imp.say(imp.pRow() + 0, 0, "| Mes/Ano");
							imp.say(imp.pRow() + 0, 10, "| Base");
							imp.say(imp.pRow() + 0, 28, "| ISS");
							imp.say(imp.pRow() + 0, 46, "| PIS");
							imp.say(imp.pRow() + 0, 64, "| COFINS");
							imp.say(imp.pRow() + 0, 82, "| IR");
							imp.say(imp.pRow() + 0, 100, "| C.SOCIAL");
							imp.say(imp.pRow() + 0, 118, "| Tot. Venda.");
							imp.say(imp.pRow() + 0, 135, "|");
							imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
							imp.say(imp.pRow() + 0, 0, "|"
									+ Funcoes.replicate("-", 133) + "|");
						}
						imp.say(imp.pRow() + 1, 0, imp.comprimido());
						imp.say(imp.pRow() + 0, 0, "| "
								+ Funcoes.strZero(rs.getString(2), 2) + "/"
								+ Funcoes.strZero(rs.getString(1), 4));
						imp.say(imp.pRow() + 0, 10, "| "
								+ Funcoes.strDecimalToStrCurrency(15, 2, rs
										.getString(3)));
						imp.say(imp.pRow() + 0, 28, "| "
								+ Funcoes.strDecimalToStrCurrency(15, 2, rs
										.getString(4)));
						imp.say(imp.pRow() + 0, 46, "| "
								+ Funcoes.strDecimalToStrCurrency(15, 2, rs
										.getString(5)));
						imp.say(imp.pRow() + 0, 64, "| "
								+ Funcoes.strDecimalToStrCurrency(15, 2, rs
										.getString(6)));
						imp.say(imp.pRow() + 0, 82, "| "
								+ Funcoes.strDecimalToStrCurrency(15, 2, rs
										.getString(7)));
						imp.say(imp.pRow() + 0, 100, "| "
								+ Funcoes.strDecimalToStrCurrency(15, 2, rs
										.getString(8)));
						imp.say(imp.pRow() + 0, 118, "| "
								+ Funcoes.strDecimalToStrCurrency(15, 2, rs
										.getString(9)));
						imp.say(imp.pRow() + 0, 135, "|");
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
				Funcoes.mensagemErro(this, "Erro executando a consulta.\n"
						+ err.getMessage(),true,con,err);
				err.printStackTrace();
			}
			if (bVisualizar) {
				imp.preview(this);
			} else {
				imp.print();
			}
		} finally {
			sSql = null;
			sFiltros = null;
			imp = null;
			ps = null;
			rs = null;
		}

	}

	private void impTotMes(ImprimeOS imp) {
		imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
		imp.say(imp.pRow() + 0, 0, "+" + Funcoes.replicate("-", 133) + "+");
		imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
		imp.say(imp.pRow() + 0, 0, "|     TOTAIS DO MES -->");
		imp.say(imp.pRow() + 0, 57, "|"
				+ Funcoes.strDecimalToStrCurrency(10, 2, "" + dTotMesBase));
		imp.say(imp.pRow() + 0, 69, Funcoes.strDecimalToStrCurrency(10, 2, ""
				+ dTotMesISS));
		imp.say(imp.pRow() + 0, 80, Funcoes.strDecimalToStrCurrency(10, 2, ""
				+ dTotMesPIS));
		imp.say(imp.pRow() + 0, 90, Funcoes.strDecimalToStrCurrency(10, 2, ""
				+ dTotMesCOFINS));
		imp.say(imp.pRow() + 0, 101, Funcoes.strDecimalToStrCurrency(10, 2, ""
				+ dTotMesIR));
		imp.say(imp.pRow() + 0, 112, Funcoes.strDecimalToStrCurrency(10, 2, ""
				+ dTotMesCSocial));
		imp.say(imp.pRow() + 0, 123, Funcoes.strDecimalToStrCurrency(10, 2, ""
				+ dTotMesLiq));
		imp.say(imp.pRow() + 0, 135, "|");
		imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
		imp.say(imp.pRow() + 0, 0, "+" + Funcoes.replicate("-", 133) + "+");
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
		imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
		imp.say(imp.pRow() + 0, 0, "+" + Funcoes.replicate("-", 133) + "+");
		imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
		imp.say(imp.pRow() + 0, 0, "|     TOTAIS GERAIS -->");
		imp.say(imp.pRow() + 0, 57, "|"
				+ Funcoes.strDecimalToStrCurrency(10, 2, "" + dTotBase));
		imp.say(imp.pRow() + 0, 69, Funcoes.strDecimalToStrCurrency(10, 2, ""
				+ dTotISS));
		imp.say(imp.pRow() + 0, 80, Funcoes.strDecimalToStrCurrency(10, 2, ""
				+ dTotPIS));
		imp.say(imp.pRow() + 0, 90, Funcoes.strDecimalToStrCurrency(10, 2, ""
				+ dTotCOFINS));
		imp.say(imp.pRow() + 0, 101, Funcoes.strDecimalToStrCurrency(10, 2, ""
				+ dTotIR));
		imp.say(imp.pRow() + 0, 112, Funcoes.strDecimalToStrCurrency(10, 2, ""
				+ dTotCSocial));
		imp.say(imp.pRow() + 0, 123, Funcoes.strDecimalToStrCurrency(10, 2, ""
				+ dTotLiq));
		imp.say(imp.pRow() + 0, 135, "|");
		imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
		imp.say(imp.pRow() + 0, 0, "+" + Funcoes.replicate("-", 133) + "+");
	}
}