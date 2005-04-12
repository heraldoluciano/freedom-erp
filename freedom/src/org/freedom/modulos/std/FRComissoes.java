/**
 * @version 08/12/2000 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.std <BR>
 * Classe:
 * @(#)FRComissoes.java <BR>
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
 * Comentários sobre a classe...
 *  
 */

package org.freedom.modulos.std;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Vector;

import org.freedom.componentes.JLabelPad;

import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.ImprimeOS;
import org.freedom.componentes.JCheckBoxPad;
import org.freedom.componentes.JRadioGroup;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FRelatorio;

public class FRComissoes extends FRelatorio {
	private Vector vVals = new Vector();

	private Vector vLabs = new Vector();

	private JRadioGroup rgEmitRel = null;

	private JTextFieldPad txtCodVend = new JTextFieldPad(
			JTextFieldPad.TP_INTEGER, 8, 0);

	private JTextFieldPad txtDataini = new JTextFieldPad(JTextFieldPad.TP_DATE,
			10, 0);

	private JTextFieldPad txtDatafim = new JTextFieldPad(JTextFieldPad.TP_DATE,
			10, 0);

	private JTextFieldFK txtDescVend = new JTextFieldFK(
			JTextFieldPad.TP_STRING, 40, 0);

	private JCheckBoxPad cbNLiberada = new JCheckBoxPad("Não Liber.", "S", "N");

	private JCheckBoxPad cbLiberada = new JCheckBoxPad("Liberadas", "S", "N");

	private JCheckBoxPad cbPaga = new JCheckBoxPad("Pagas", "S", "N");

	private ListaCampos lcVend = new ListaCampos(this);

	public FRComissoes() {
		setTitulo("Comissões");
		setAtribos(80, 80, 330, 210);

		Funcoes.setBordReq(txtCodVend);

		vVals.addElement("E");
		vVals.addElement("V");
		vVals.addElement("P");
		vLabs.addElement("Emissão");
		vLabs.addElement("Vencimento");
		vLabs.addElement("Pagto. comissão");
		rgEmitRel = new JRadioGroup(3, 2, vLabs, vVals);
		rgEmitRel.setVlrString("E");
		rgEmitRel.setAtivo(0, true);
		rgEmitRel.setAtivo(1, true);

		lcVend.add(new GuardaCampo(txtCodVend, "CodVend", "Cód.comiss.",
				ListaCampos.DB_PK, false));
		lcVend.add(new GuardaCampo(txtDescVend, "NomeVend",
				"Nome do comissionado", ListaCampos.DB_SI, false));
		lcVend.montaSql(false, "VENDEDOR", "VD");
		lcVend.setReadOnly(true);
		txtCodVend.setTabelaExterna(lcVend);
		txtCodVend.setFK(true);
		txtCodVend.setNomeCampo("CodVend");

		adic(new JLabelPad("Periodo:"), 7, 5, 120, 20);
		adic(new JLabelPad("De:"), 7, 27, 30, 20);
		adic(txtDataini, 40, 27, 97, 20);
		adic(new JLabelPad("Até:"), 7, 47, 30, 20);
		adic(txtDatafim, 40, 47, 97, 20);
		adic(rgEmitRel, 150, 7, 130, 65);

		adic(new JLabelPad("Cód.comiss."), 7, 67, 250, 20);
		adic(txtCodVend, 7, 87, 80, 20);
		adic(new JLabelPad("Nome do comissionado"), 90, 67, 250, 20);
		adic(txtDescVend, 90, 87, 200, 20);
		adic(cbNLiberada, 7, 107, 100, 20);
		adic(cbLiberada, 110, 107, 97, 20);
		adic(cbPaga, 210, 107, 100, 20);

		Calendar cPeriodo = Calendar.getInstance();
		txtDatafim.setVlrDate(cPeriodo.getTime());
		cPeriodo.set(Calendar.DAY_OF_MONTH,
				cPeriodo.get(Calendar.DAY_OF_MONTH) - 30);
		txtDataini.setVlrDate(cPeriodo.getTime());
	}

	public void setConexao(Connection cn) {
		super.setConexao(cn);
		lcVend.setConexao(cn);
	}

	public void imprimir(boolean bVisualizar) {
		ImprimeOS imp = null;
		try {
			String sEmitRel = "";
			if (txtDatafim.getVlrDate().before(txtDataini.getVlrDate())) {
				Funcoes.mensagemInforma(this,
						"Data final maior que a data inicial!");
				return;
			} else if (txtCodVend.getVlrString().equals("")) {
				Funcoes.mensagemInforma(this,
						"Código do comissionado é requerido!");
				return;
			}

			imp = new ImprimeOS("", con);
			impRel(bVisualizar, imp);
		} finally {
			imp = null;
		}
	}

	public void impRel(boolean bVisualizar, ImprimeOS imp) {
		String sEmitRel = "";
		String sFiltro = "";
		String sDataini = "";
		String sDatafim = "";
		String sNLiberada = "";
		String sLiberada = "";
		String sPaga = "";
		String sDataFiltro = "";
		String sTitDataFiltro = "";
		PreparedStatement ps = null;
		ResultSet rs = null;
		int linPag = imp.verifLinPag() - 1;
		int iCodVend = txtCodVend.getVlrInteger().intValue();
		double dePercComi = 0;
		double deVlrVenda = 0;
		double deVlrComi = 0;
		double deVlrPago = 0;
		double deVlrAPag = 0;
		try {

			sEmitRel = rgEmitRel.getVlrString();

			sNLiberada = cbNLiberada.getVlrString();
			sLiberada = cbLiberada.getVlrString();
			sPaga = cbPaga.getVlrString();

			imp.montaCab();

			sDataini = txtDataini.getVlrString();
			sDatafim = txtDatafim.getVlrString();
			if (sEmitRel.equals("E")) {
				sDataFiltro = "C.DATACOMI";
				sTitDataFiltro = "Emissão";
			} else if (sEmitRel.equals("V")) {
				sDataFiltro = "C.DTVENCCOMI";
				sTitDataFiltro = "Vencimento";
			} else if (sEmitRel.equals("P")) {
				sDataFiltro = "C.DTPAGTOCOMI";
				sTitDataFiltro = "Pagto. Comissão";
			}

			imp.setTitulo("Relatório de Comissões");
			String sSQL = "SELECT C.DATACOMI,V.CODVENDA,V.DOCVENDA,C.STATUSCOMI,"
					+ "CL.CODCLI,CL.RAZCLI,C.VLRVENDACOMI,P.DESCPLANOPAG,"
					+ "C.VLRCOMI,C.DTVENCCOMI,R.DOCREC,IR.NPARCITREC,"
					+ "C.TIPOCOMI,C.VLRAPAGCOMI, C.VLRPAGOCOMI,C.DTPAGTOCOMI,"
					+ "IR.VLRPARCITREC "
					+ "FROM FNRECEBER R, FNITRECEBER IR, VDVENDA V, VDCOMISSAO C,"
					+ "VDCLIENTE CL, FNPLANOPAG P WHERE V.FLAG IN "
					+ Aplicativo.carregaFiltro(con,
							org.freedom.telas.Aplicativo.iCodEmp)
					+ " AND R.CODEMPVD = ? AND R.CODFILIALVD = ? AND R.CODVEND = ?"
					+ " AND C.CODEMP = ? AND C.CODFILIAL = ? "
					+ " AND R.CODEMP = C.CODEMPRC AND R.CODFILIAL = C.CODFILIALRC "
					+ " AND R.CODREC = C.CODREC AND V.CODEMP = R.CODEMPVA "
					+ " AND IR.CODEMP = C.CODEMPRC AND IR.CODFILIAL = C.CODFILIALRC "
					+ " AND IR.CODREC = C.CODREC AND IR.NPARCITREC = C.NPARCITREC "
					+ " AND V.CODFILIAL = R.CODFILIALVA AND V.TIPOVENDA = R.TIPOVENDA "
					+ " AND V.CODVENDA = R.CODVENDA AND CL.CODEMP = R.CODEMPCL "
					+ " AND CL.CODFILIAL = R.CODFILIALCL AND CL.CODCLI = R.CODCLI "
					+ " AND P.CODEMP = V.CODEMPPG AND P.CODFILIAL = V.CODFILIALPG "
					+ " AND P.CODPLANOPAG = V.CODPLANOPAG "
					+ " AND "
					+ sDataFiltro
					+ " BETWEEN ? AND ? AND C.STATUSCOMI IN (?,?,?)"
					+ " ORDER BY " + sDataFiltro + ",V.CODVENDA";
			//System.out.println(sSQL);
			try {
				ps = con.prepareStatement(sSQL);
				ps.setInt(1, Aplicativo.iCodEmp);
				ps.setInt(2, ListaCampos.getMasterFilial("VDVENDEDOR"));
				ps.setInt(3, iCodVend);
				ps.setInt(4, Aplicativo.iCodEmp);
				ps.setInt(5, ListaCampos.getMasterFilial("VDCOMISSAO"));
				ps.setDate(6, Funcoes.dateToSQLDate(txtDataini.getVlrDate()));
				ps.setDate(7, Funcoes.dateToSQLDate(txtDatafim.getVlrDate()));

				if (sNLiberada.equals("S")) {
					ps.setString(8, "C1");
					sFiltro = "NÃO LIBERADAS";
				} else {
					ps.setString(8, "XX");
				}
				if (sLiberada.equals("S")) {
					ps.setString(9, "C2");
					sFiltro += (sFiltro.equals("") ? "" : " - ") + "LIBERADAS";
				} else {
					ps.setString(9, "XX");
				}
				if (sPaga.equals("S")) {
					ps.setString(10, "CP");
					sFiltro += (sFiltro.equals("") ? "" : " - ") + "PAGAS";
				} else {
					ps.setString(10, "XX");
				}

				rs = ps.executeQuery();
				imp.limpaPags();

				boolean hasData = false;
				
				while (rs.next()) {
					hasData=true;
					if (imp.pRow() >= (linPag - 1)) {
						imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
						imp.say(imp.pRow() + 0, 0, "|"
								+ Funcoes.replicate("-", 133) + "|");
						imp.incPags();
						imp.eject();
					}

					if (imp.pRow() == 0) {
						imp.addSubTitulo("RELATORIO DE COMISSOES(" + sTitDataFiltro
								+ ") - PERIODO DE " + sDataini + " ATE " + sDatafim);
						imp.impCab(136, true);
						
						String sVendedor = "COMISSIONADO: " + iCodVend + " - "
								+ txtDescVend.getVlrString();
						imp.say(imp.pRow() + 0, 0, "|"
								+ Funcoes.replicate("=", 133) + "|");
						imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
						imp.say(imp.pRow() + 0, 0, "|");
						imp.say(imp.pRow() + 0, (135 - sVendedor.length()) / 2,
								sVendedor);
						imp.say(imp.pRow() + 0, 135, "|");
						imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
						imp.say(imp.pRow() + 0, 0, "|"
								+ Funcoes.replicate("=", 133) + "|");
						imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
						imp.say(imp.pRow() + 0, 0, "|"
								+ Funcoes.replicate("-", 133) + "|");
						imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
						imp.say(imp.pRow() + 0, 0, "| CLIENTE");
						imp.say(imp.pRow() + 0, 26, "|DUPLIC.");
						imp.say(imp.pRow() + 0, 37, "|PEDIDO");
						imp.say(imp.pRow() + 0, 46, "|L");
						imp.say(imp.pRow() + 0, 48, "|ST");
						imp.say(imp.pRow() + 0, 51, "| EMISSAO");
						imp.say(imp.pRow() + 0, 62, "| VENCTO.");
						imp.say(imp.pRow() + 0, 73, "| VLR.PARC.A");
						imp.say(imp.pRow() + 0, 86, "|VLR.COMI.");
						imp.say(imp.pRow() + 0, 97, "|  %");
						imp.say(imp.pRow() + 0, 104, "| VLR.PAGO");
						imp.say(imp.pRow() + 0, 114, "|VLR.A PG.");
						imp.say(imp.pRow() + 0, 124, "| DT.PGTO.");
						imp.say(imp.pRow() + 0, 135, "|");
						imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
						imp.say(imp.pRow() + 0, 0, "|"
								+ Funcoes.replicate("-", 133) + "|");

					}
					if (rs.getDouble("VLRVENDACOMI") != 0)
						dePercComi = rs.getDouble("VLRCOMI") * 100
								/ rs.getDouble("VLRPARCITREC");
					else
						dePercComi = 0;
					imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
					imp.say(imp.pRow() + 0, 0, "|"
							+ Funcoes.adicionaEspacos(rs.getString("RAZCLI"),
									24));
					imp.say(imp.pRow() + 0, 26, "|"
							+ Funcoes.adicEspacosEsquerda(rs
									.getString("DOCREC")
									+ "-" + rs.getString("NPARCITREC"), 10));
					imp.say(imp.pRow() + 0, 37, "|"
							+ Funcoes.adicEspacosEsquerda(rs.getInt("CODVENDA")
									+ "", 8));
					imp.say(imp.pRow() + 0, 46, "|" + rs.getString("TIPOCOMI"));
					imp.say(imp.pRow() + 0, 48, "|"
							+ rs.getString("STATUSCOMI"));
					imp.say(imp.pRow() + 0, 51, "|"
							+ Funcoes.dateToStrDate(rs.getDate("DATACOMI")));
					imp.say(imp.pRow() + 0, 62, "|"
							+ Funcoes.dateToStrDate(rs.getDate("DTVENCCOMI")));
					imp.say(imp.pRow() + 0, 73, "|"
							+ Funcoes.strDecimalToStrCurrency(12, 2, rs
									.getString("VLRPARCITREC")));
					imp.say(imp.pRow() + 0, 86, "|"
							+ Funcoes.strDecimalToStrCurrency(9, 2, rs
									.getString("VLRCOMI")));
					imp.say(imp.pRow() + 0, 97, "|"
							+ Funcoes.strDecimalToStrCurrency(5, 2, dePercComi
									+ ""));
					imp.say(imp.pRow() + 0, 104, "|"
							+ Funcoes.strDecimalToStrCurrency(9, 2, rs
									.getString("VLRPAGOCOMI")));
					imp.say(imp.pRow() + 0, 114, "|"
							+ Funcoes.strDecimalToStrCurrency(9, 2, rs
									.getString("VLRAPAGCOMI")));
					imp.say(imp.pRow() + 0, 124, "|"
							+ (rs.getDate("DTPAGTOCOMI") == null ? "" : Funcoes
									.dateToStrDate(rs.getDate("DTPAGTOCOMI"))));
					imp.say(imp.pRow() + 0, 135, "|");
					deVlrVenda += rs.getDouble("VLRVENDACOMI");
					deVlrComi += rs.getDouble("VLRCOMI");
					deVlrPago += rs.getDouble("VLRPAGOCOMI");
					deVlrAPag += rs.getDouble("VLRAPAGCOMI");

				}

				imp.say(imp.pRow() + ((hasData) ? 1 : 0), 0, "" + imp.comprimido());
				imp.say(imp.pRow() + 0, 0, "|" + Funcoes.replicate("=", 133)
						+ "|");
				imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
				imp.say(imp.pRow() + 0, 0, "| TOTAL ->");
				imp.say(imp.pRow() + 0, 26, "|");
				imp.say(imp.pRow() + 0, 73, "|"
						+ Funcoes.strDecimalToStrCurrency(12, 2, ""
								+ deVlrVenda));
				imp
						.say(imp.pRow() + 0, 86, "|"
								+ Funcoes.strDecimalToStrCurrency(9, 2, ""
										+ deVlrComi));
				imp.say(imp.pRow() + 0, 97, "|");
				imp
						.say(imp.pRow() + 0, 104, "|"
								+ Funcoes.strDecimalToStrCurrency(9, 2, ""
										+ deVlrPago));
				imp
						.say(imp.pRow() + 0, 114, "|"
								+ Funcoes.strDecimalToStrCurrency(9, 2, ""
										+ deVlrAPag));
				imp.say(imp.pRow(), 124, "|");
				imp.say(imp.pRow(), 135, "|");
				imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
				imp.say(imp.pRow() + 0, 0, "+" + Funcoes.replicate("=", 133)
						+ "+");

				imp.eject();

				imp.fechaGravacao();

				rs.close();
				ps.close();
				if (!con.getAutoCommit())
					con.commit();
				//      	dl.dispose();
			} catch (SQLException err) {
				Funcoes.mensagemErro(this,
						"Erro consulta tabela de commissões!"
								+ err.getMessage());
			}

			if (bVisualizar) {
				imp.preview(this);
			} else {
				imp.print();
			}
		} finally {
			sEmitRel = null;
			sFiltro = null;
			sDataini = null;
			sDatafim = null;
			sNLiberada = null;
			sLiberada = null;
			sPaga = null;
			sDataFiltro = null;
			sTitDataFiltro = null;
			ps = null;
			rs = null;
			linPag = 0;
			iCodVend = 0;
			dePercComi = 0;
			deVlrVenda = 0;
			deVlrComi = 0;
			deVlrPago = 0;
			deVlrAPag = 0;
		}
	}

}