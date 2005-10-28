/**
 * @version 24/03/2004 <BR>
 * @author Setpoint Informática Ltda./Robson Sanchez <BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.std <BR>
 * Classe:
 * @(#)FRVendaSetor.java <BR>
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
import java.util.GregorianCalendar;
import java.util.Vector;

import org.freedom.acao.RadioGroupEvent;
import org.freedom.acao.RadioGroupListener;
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

public class FRVendaSetor extends FRelatorio implements RadioGroupListener {
	private static final long serialVersionUID = 1L;

	private final int POS_CODSETOR = 0;
	private final int POS_MES = 1;
	private final int POS_CODGRUP = 2;
	private final int POS_SIGLAGRUP = 3;
	private final int POS_CODVEND = 4;
	private final int POS_VALOR = 5;
	private final int POS_TOTSETOR = 6;
	private final int TAM_GRUPO = 14;
	private final int NUM_COLUNAS = 9;
	private JTextFieldPad txtDataini = new JTextFieldPad(JTextFieldPad.TP_DATE,10, 0);
	private JTextFieldPad txtDatafim = new JTextFieldPad(JTextFieldPad.TP_DATE,10, 0);
	private JTextFieldPad txtCodMarca = new JTextFieldPad(JTextFieldPad.TP_STRING, 6, 0);
	private JTextFieldFK txtDescMarca = new JTextFieldFK(JTextFieldPad.TP_STRING, 40, 0);
	private JTextFieldPad txtCodGrup1 = new JTextFieldPad(JTextFieldPad.TP_STRING, TAM_GRUPO, 0);
	private JTextFieldFK txtDescGrup1 = new JTextFieldFK(JTextFieldPad.TP_STRING, 40, 0);
	private JTextFieldPad txtCodGrup2 = new JTextFieldPad(JTextFieldPad.TP_STRING, TAM_GRUPO, 0);
	private JTextFieldFK txtDescGrup2 = new JTextFieldFK(JTextFieldPad.TP_STRING, 40, 0);
	private JTextFieldFK txtSiglaMarca = new JTextFieldFK(JTextFieldPad.TP_STRING, 20, 0);
	private JTextFieldPad txtCodSetor = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);
	private JTextFieldFK txtDescSetor = new JTextFieldFK(JTextFieldPad.TP_STRING, 40, 0);
	private JTextFieldPad txtCodVend = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);
	private JTextFieldFK txtNomeVend = new JTextFieldFK(JTextFieldPad.TP_STRING, 40, 0);
	private JTextFieldPad txtCodCli = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);
	private JTextFieldFK txtRazCli = new JTextFieldFK(JTextFieldPad.TP_STRING,40, 0);
	private JCheckBoxPad cbMovEstoque = new JCheckBoxPad("Só com mov.estoque?", "S", "N");
	private JCheckBoxPad cbCliPrinc = new JCheckBoxPad("Mostrar no cliente principal?", "S", "N");
	private JLabelPad lbCodMarca = new JLabelPad("Cód.marca");
	private JLabelPad lbDescCodMarca = new JLabelPad("Descrição da marca");
	private JLabelPad lbCodGrup1 = new JLabelPad("Cód.grupo/somar");
	private JLabelPad lbDescCodGrup1 = new JLabelPad("Descrição do grupo/somar");
	private JLabelPad lbCodGrup2 = new JLabelPad("Cód.grupo/subtrair");
	private JLabelPad lbDescCodGrup2 = new JLabelPad("Descrição do grupo/subtrair");
	private JLabelPad lbCodSetor = new JLabelPad("Cód.setor");
	private JLabelPad lbDescCodSetor = new JLabelPad("Descrição do setor");
	private JLabelPad lbCodVend = new JLabelPad("Cód.comiss.");
	private JLabelPad lbDescCodVend = new JLabelPad("Nome do comissionado");
	private ListaCampos lcGrup1 = new ListaCampos(this);
	private ListaCampos lcGrup2 = new ListaCampos(this);
	private ListaCampos lcMarca = new ListaCampos(this);
	private ListaCampos lcSetor = new ListaCampos(this);
	private ListaCampos lcVendedor = new ListaCampos(this);
	private ListaCampos lcCliente = new ListaCampos(this); 
	private JRadioGroup rgFaturados = null;
	private JRadioGroup rgFinanceiro = null;
	private Vector vLabsFat = new Vector();
	private Vector vValsFat = new Vector();
	private Vector vLabsFin = new Vector();
	private Vector vValsFin = new Vector();
	private JRadioGroup rgTipoRel = null;
	private JRadioGroup rgOrdemRel = null;
	private Vector vLabTipoRel = new Vector();
	private Vector vValTipoRel = new Vector();
	private Vector vLabOrdemRel = new Vector();
	private Vector vValOrdemRel = new Vector();

	public FRVendaSetor() {
		setTitulo("Relatório de Vendas por Setor");
		setAtribos(80, 0, 480, 490);

		GregorianCalendar cal = new GregorianCalendar();
		cal.add(Calendar.DATE, -30);
		txtDataini.setVlrDate(cal.getTime());
		cal.add(Calendar.DATE, 30);
		txtDatafim.setVlrDate(cal.getTime());
		txtDataini.setRequerido(true);
		txtDatafim.setRequerido(true);

		cbMovEstoque.setVlrString("S");
		cbCliPrinc.setVlrString("S");
		
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

		vLabTipoRel.addElement("Vendedor");
		vLabTipoRel.addElement("Produto");
		vLabTipoRel.addElement("Cliente");
		vValTipoRel.addElement("V");
		vValTipoRel.addElement("P");
		vValTipoRel.addElement("C");
		rgTipoRel = new JRadioGroup(1, 3, vLabTipoRel, vValTipoRel);
		rgTipoRel.addRadioGroupListener(this);

		vLabOrdemRel.addElement("Valor");
		vLabOrdemRel.addElement("Razão social");
		vLabOrdemRel.addElement("Cód.cli.");
		vValOrdemRel.addElement("V");
		vValOrdemRel.addElement("R");
		vValOrdemRel.addElement("C");
		rgOrdemRel = new JRadioGroup(3, 1, vLabOrdemRel, vValOrdemRel);

		lcMarca.add(new GuardaCampo(txtCodMarca, "CodMarca", "Cód.marca",ListaCampos.DB_PK, false));
		lcMarca.add(new GuardaCampo(txtDescMarca, "DescMarca","Descrição da marca", ListaCampos.DB_SI, false));
		lcMarca.add(new GuardaCampo(txtSiglaMarca, "SiglaMarca", "Sigla",ListaCampos.DB_SI, false), "txtSiglaMarca");
		lcMarca.montaSql(false, "MARCA", "EQ");
		lcMarca.setReadOnly(true);
		txtCodMarca.setTabelaExterna(lcMarca);
		txtCodMarca.setFK(true);
		txtCodMarca.setNomeCampo("CodMarca");

		lcGrup1.add(new GuardaCampo(txtCodGrup1, "CodGrup", "Cód.grupo",ListaCampos.DB_PK, false));
		lcGrup1.add(new GuardaCampo(txtDescGrup1, "DescGrup","Descrição do gurpo", ListaCampos.DB_SI, false));
		lcGrup1.montaSql(false, "GRUPO", "EQ");
		lcGrup1.setReadOnly(true);
		txtCodGrup1.setTabelaExterna(lcGrup1);
		txtCodGrup1.setFK(true);
		txtCodGrup1.setNomeCampo("CodGrup");

		lcGrup2.add(new GuardaCampo(txtCodGrup2, "CodGrup", "Cód.grupo",ListaCampos.DB_PK, false));
		lcGrup2.add(new GuardaCampo(txtDescGrup2, "DescGrup","Descrição do grupo", ListaCampos.DB_SI, false));
		lcGrup2.montaSql(false, "GRUPO", "EQ");
		lcGrup2.setReadOnly(true);
		txtCodGrup2.setTabelaExterna(lcGrup2);
		txtCodGrup2.setFK(true);
		txtCodGrup2.setNomeCampo("CodGrup");

		lcSetor.add(new GuardaCampo(txtCodSetor, "CodSetor", "Cód.setor",ListaCampos.DB_PK, false));
		lcSetor.add(new GuardaCampo(txtDescSetor, "DescSetor","Descrição do setor", ListaCampos.DB_SI, false));
		lcSetor.montaSql(false, "SETOR", "VD");
		lcSetor.setReadOnly(true);
		txtCodSetor.setTabelaExterna(lcSetor);
		txtCodSetor.setFK(true);
		txtCodSetor.setNomeCampo("CodSetor");

		lcVendedor.add(new GuardaCampo(txtCodVend, "CodVend", "Cód.comiss.",ListaCampos.DB_PK, false));
		lcVendedor.add(new GuardaCampo(txtNomeVend, "NomeVend","Nome do comissionado", ListaCampos.DB_SI, false));
		lcVendedor.montaSql(false, "VENDEDOR", "VD");
		lcVendedor.setReadOnly(true);
		txtCodVend.setTabelaExterna(lcVendedor);
		txtCodVend.setFK(true);
		txtCodVend.setNomeCampo("CodVend");

		lcCliente.add(new GuardaCampo(txtCodCli, "CodCli", "Cód.cli.",ListaCampos.DB_PK, false));
		lcCliente.add(new GuardaCampo(txtRazCli, "RazCli","Razão social do cliente", ListaCampos.DB_SI, false));
		txtCodCli.setTabelaExterna(lcCliente);
		txtCodCli.setNomeCampo("CodCli");
		txtCodCli.setFK(true);
		lcCliente.setReadOnly(true);
		lcCliente.montaSql(false, "CLIENTE", "VD");

		adic(new JLabelPad("Formato de impressão"), 7, 0, 200, 20);
		adic(rgTipoRel, 7, 20, 281, 30);
		adic(new JLabelPad("Ordem"), 295, 0, 70, 20);
		adic(rgOrdemRel, 295, 20, 157, 77);
		adic(new JLabelPad("Período"), 7, 50, 100, 20);
		adic(txtDataini, 7, 70, 120, 20);
		adic(new JLabelPad("Até"), 138, 70, 40, 20);
		adic(txtDatafim, 168, 70, 120, 20);
		adic(lbCodMarca, 7, 90, 250, 20);
		adic(txtCodMarca, 7, 110, 120, 20);
		adic(lbDescCodMarca, 130, 90, 250, 20);
		adic(txtDescMarca, 130, 110, 327, 20);
		adic(lbCodGrup1, 7, 130, 250, 20);
		adic(txtCodGrup1, 7, 150, 120, 20);
		adic(lbDescCodGrup1, 130, 130, 250, 20);
		adic(txtDescGrup1, 130, 150, 327, 20);
		adic(lbCodGrup2, 7, 170, 250, 20);
		adic(txtCodGrup2, 7, 190, 120, 20);
		adic(lbDescCodGrup2, 130, 170, 250, 20);
		adic(txtDescGrup2, 130, 190, 327, 20);
		adic(lbCodSetor, 7, 210, 250, 20);
		adic(txtCodSetor, 7, 230, 120, 20);
		adic(lbDescCodSetor, 130, 210, 250, 20);
		adic(txtDescSetor, 130, 230, 327, 20);
		adic(lbCodVend, 7, 250, 250, 20);
		adic(txtCodVend, 7, 270, 120, 20);
		adic(lbDescCodVend, 130, 250, 250, 20);
		adic(txtNomeVend, 130, 270, 327, 20);
		adic(new JLabelPad("Cód.cli."), 7, 290, 200, 20);
		adic(txtCodCli, 7, 310, 120, 20);
		adic(new JLabelPad("Razão social do cliente"), 130, 290, 200, 20);
		adic(txtRazCli, 130, 310, 327, 20);
		adic(rgFaturados, 7, 340, 120, 70);
		adic(rgFinanceiro, 140, 340, 120, 70);
		adic(cbMovEstoque, 270, 340, 200, 25);
		adic(cbCliPrinc, 270, 360, 300, 25);

	}

	public void imprimir(boolean bVisualizar) {
		if (txtDataini.getVlrString().length() < 10
				|| txtDatafim.getVlrString().length() < 10) {
			Funcoes.mensagemInforma(this, "Período inválido!");
			return;
		}

		if (rgTipoRel.getVlrString().equals("V"))
			impVendedor(bVisualizar);
		else if (rgTipoRel.getVlrString().equals("P"))
			impProduto(bVisualizar);
		else if (rgTipoRel.getVlrString().equals("C"))
			impCliente(bVisualizar);

	}

	public void valorAlterado(RadioGroupEvent rge) {
		String sTipoRel = null;
		try {
			sTipoRel = rge.getRadioButton().getText();
			if (sTipoRel.equalsIgnoreCase("CLIENTE"))
				rgOrdemRel.setAtivo(true);
			else
				rgOrdemRel.setAtivo(false);
		} finally {
			sTipoRel = null;
		}
	}

	private void impVendedor(boolean bVisualizar) {
		String sSql = "";
		String sWhere = "";
		String sWhere1 = "";
	  	String sWhere2 = "";
	  	String sCab = "";
		String sFrom = "";
		String sCodMarca = "";
		String sCodGrup1 = "";
		String sCodGrup2 = "";
		String sCodSetor = "";
		String sCodSetorAnt = "";
		String sCodGrupAnt = "";
		String sCodGrup = "";
		String sMesAnt = "";
		String sSiglaGroup = "";
		String sMes = "";
		String sFiltros1 = "";
		String sFiltros2 = "";
		String sWhereTM = "";
		ImprimeOS imp = null;
		int linPag = 0;
		int iParam = 1;
		int iCol = 0;
		int iPosCol = 0;
		int iPosColAnt = 0;
		int iPos = 0;
		int iLinsSetor = 0;
		int iCodSetor = 0;
		int iCodCli = 0;
		int iCodVend = 0;
		int iTotPassadas = 0;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Vector vItens = null;
		Vector vItem = null;
		Vector vTotSetor = null;
		Vector vCols = null;
		double deValor = 0;
		double deTotal1 = 0;
		double deTotalGeral = 0;

		try {

			imp = new ImprimeOS("", con);
			linPag = imp.verifLinPag() - 1;

			sCodMarca = txtCodMarca.getVlrString().trim();
			sCodGrup1 = txtCodGrup1.getVlrString().trim();
			sCodGrup2 = txtCodGrup2.getVlrString().trim();
			iCodSetor = txtCodSetor.getVlrInteger().intValue();
			iCodVend = txtCodVend.getVlrInteger().intValue();
			iCodCli = txtCodCli.getVlrInteger().intValue();
			
			if(rgFaturados.getVlrString().equals("S")){
				sWhere1 = " AND TM.FISCALTIPOMOV='S' ";
				sCab += " - SO FATURADO";
			}
			else if(rgFaturados.getVlrString().equals("N")){
				sWhere1 = " AND TM.FISCALTIPOMOV='N' ";
				sCab += " - NAO FATURADO";
			}
			else if(rgFaturados.getVlrString().equals("A")){
				sWhere1 = " AND TM.FISCALTIPOMOV IN ('S','N') ";
			}	
			if(rgFinanceiro.getVlrString().equals("S")){
				sWhere2 = " AND TM.SOMAVDTIPOMOV='S' ";
				sCab += " - SO FINANCEIRO";
			}
			else if(rgFinanceiro.getVlrString().equals("N")){
				sWhere2 = " AND TM.SOMAVDTIPOMOV='N' ";
				sCab += " - NAO FINANCEIRO";
			}
			else if(rgFinanceiro.getVlrString().equals("A")){
				sWhere2 = " AND TM.SOMAVDTIPOMOV IN ('S','N') ";
			}

			if(cbMovEstoque.getVlrString().equals("S")) {
				sFiltros1 += (!sFiltros1.equals("") ? " / " : "") + "SO MOV.ESTOQUE";
				sWhereTM += (cbMovEstoque.getVlrString().equals("S") ? " AND TM.ESTOQTIPOMOV='S' " : "");
			}
		
			if (!sCodMarca.equals("")) {
				sWhere += "AND P.CODEMPMC=? AND P.CODFILIALMC=? AND P.CODMARCA=? ";
				sFiltros1 += (!sFiltros1.equals("") ? " / " : "") + "M.: "
						+ txtDescMarca.getText().trim();
			}
			if (!sCodGrup1.equals("")) {
				sWhere += "AND G.CODEMP=? AND G.CODFILIAL=? AND G.CODGRUP LIKE ? ";
				sFiltros1 += (!sFiltros1.equals("") ? " / " : "") + "G.: "
						+ txtDescGrup1.getText().trim();
			}
			if (!sCodGrup2.equals("")) {
				sWhere += "AND ( NOT P.CODGRUP=? ) ";
				sFiltros1 += (!sFiltros1.equals("") ? " / " : "")
						+ " EXCL. G.: " + txtDescGrup2.getText().trim();
			}
			if (iCodSetor != 0) {
				sWhere += "AND VD.CODSETOR=? ";
				sFiltros2 += (!sFiltros2.equals("") ? " / " : "") + " SETOR: "
						+ iCodSetor + "-" + txtDescSetor.getVlrString().trim();
			}
			if (iCodVend != 0) {
				sWhere += "AND VD.CODVEND=? ";
				sFiltros2 += (!sFiltros2.equals("") ? " / " : "") + " REPR.: "
						+ iCodVend + "-" + txtNomeVend.getVlrString().trim();
			}
			if (iCodCli != 0) {
				if (cbCliPrinc.getVlrString().equals("S")) {
					sFiltros2 += (!sFiltros2.equals("") ? " / " : "")
							+ "AGRUP. CLI. PRINC.";
				}
				if (cbCliPrinc.getVlrString().equals("S")) {
					sWhere += "AND C2.CODCLI=V.CODCLI AND C2.CODEMP=V.CODEMPCL AND C2.CODFILIAL=V.CODFILIALCL AND "
							+ "C2.CODPESQ=C1.CODPESQ AND C2.CODEMPPQ=C1.CODEMPPQ AND C2.CODFILIALPQ=C1.CODFILIALPQ AND "
							+ "C1.CODCLI=? ";
					sFrom = ",VDCLIENTE C1, VDCLIENTE C2 ";
				} else {
					sWhere += "AND V.CODCLI=? ";
				}
				sFiltros2 += (!sFiltros2.equals("") ? " / " : "") + " CLI.: "
						+ iCodCli + "-"
						+ Funcoes.copy(txtRazCli.getVlrString(), 30);
			}

			sSql = "SELECT VD.CODSETOR,"
					+ "CAST(SUBSTR(CAST(V.DTEMITVENDA AS CHAR(10)),1,7) AS CHAR(7)) ANO_MES,"
					+ "G.CODGRUP,G.SIGLAGRUP,V.CODVEND,SUM(VLRLIQITVENDA) VLRVENDA "
					+ "FROM VDVENDA V, VDITVENDA IV, VDVENDEDOR VD, EQPRODUTO P, EQGRUPO G, EQTIPOMOV TM "
					+ sFrom
					+ "WHERE V.CODEMP=? AND V.CODFILIAL=? AND "
					+ "V.DTEMITVENDA BETWEEN ? AND ? AND "
					+ "IV.CODEMP=V.CODEMP AND IV.CODFILIAL=V.CODFILIAL AND "
					+ "IV.CODVENDA=V.CODVENDA AND IV.TIPOVENDA=V.TIPOVENDA AND "
					+ "VD.CODEMP=V.CODEMPVD AND VD.CODFILIAL=V.CODFILIALVD AND "
					+ "VD.CODVEND=V.CODVEND AND VD.CODSETOR IS NOT NULL AND "
					+ "P.CODEMP=IV.CODEMPPD AND P.CODFILIAL=IV.CODFILIALPD AND "
					+ "P.CODPROD=IV.CODPROD AND G.CODEMP=P.CODEMPGP AND "
					+ "G.CODFILIAL=P.CODFILIALGP AND "
					+ "TM.CODEMP=V.CODEMPTM AND TM.CODFILIAL=V.CODFILIALTM AND "
					+ "TM.CODTIPOMOV=V.CODTIPOMOV AND ( NOT SUBSTR(V.STATUSVENDA,1,1)='C' ) "
					+ sWhereTM + sWhere1 + sWhere2
					+ (sCodGrup1.equals("") ? " AND P.CODGRUP=G.CODGRUP " : " AND SUBSTR(P.CODGRUP,1," + sCodGrup1.length() + ")=G.CODGRUP ") + sWhere
					+ "GROUP BY 1,2,3,4,5" + "ORDER BY 1,2,3,4,5";

			//System.out.println(sSql);

			try {
				ps = con.prepareStatement(sSql);
				ps.setInt(1, Aplicativo.iCodEmp);
				ps.setInt(2, ListaCampos.getMasterFilial("VDVENDA"));
				ps.setDate(3, Funcoes.dateToSQLDate(txtDataini.getVlrDate()));
				ps.setDate(4, Funcoes.dateToSQLDate(txtDatafim.getVlrDate()));
				iParam = 5;
				if (!sCodMarca.equals("")) {
					ps.setInt(iParam, Aplicativo.iCodEmp);
					ps.setInt(iParam + 1, ListaCampos
							.getMasterFilial("EQMARCA"));
					ps.setString(iParam + 2, sCodMarca);
					iParam += 3;
				}
				if (!sCodGrup1.equals("")) {
					ps.setInt(iParam, Aplicativo.iCodEmp);
					ps.setInt(iParam + 1, ListaCampos
							.getMasterFilial("EQGRUPO"));
					ps.setString(iParam + 2, sCodGrup1
							+ (sCodGrup1.length() < TAM_GRUPO ? "%" : ""));
					iParam += 3;
				}
				if (!sCodGrup2.equals("")) {
					ps.setString(iParam, sCodGrup2);
					iParam += 1;
				}
				if (iCodSetor != 0) {
					ps.setInt(iParam, iCodSetor);
					iParam += 1;
				}
				if (iCodVend != 0) {
					ps.setInt(iParam, iCodVend);
					iParam += 1;
				}
				if (iCodCli != 0) {
					ps.setInt(iParam, iCodCli);
					iParam += 1;
				}
				rs = ps.executeQuery();
				vItens = new Vector();
				sCodSetorAnt = "";
				sCodSetor = "";
				sCodGrupAnt = "";
				sCodGrup = "";
				sMes = "";
				sMesAnt = "";
				
				imp.setTitulo("Relatorio de Vendas por Setor");
				imp.addSubTitulo("VENDAS POR SETOR");

				if (!sFiltros1.equals("")) {
					imp.addSubTitulo(sFiltros1);
				}
				if (!sFiltros2.equals("")) {
					imp.addSubTitulo(sFiltros2);
				}
				imp.addSubTitulo("PERIODO DE: "
						+ txtDataini.getVlrString() + " ATE: "
						+ txtDatafim.getVlrString());

				while (rs.next()) {
					vItem = new Vector();
					sCodSetor = "" + rs.getInt(1);
					sMes = rs.getString(2);
					sCodGrup = rs.getString(3);
					if ((!sCodSetorAnt.equals(""))
							&& ((!sCodSetorAnt.equals(sCodSetor))
									|| (!sCodGrupAnt.equals(sCodGrup)) || (!sMesAnt
									.equals(sMes))))
						deTotal1 = 0;
					sSiglaGroup = rs.getString(4);
					if (sSiglaGroup == null)
						sSiglaGroup = "";
					vItem.addElement(sCodSetor); // 0 - Setor
					vItem.addElement(sMes); // 1 - Mês
					vItem.addElement(sCodGrup); // 2 - Cód. Grupo
					vItem.addElement(sSiglaGroup); // 3 - Sigla Grupo
					vItem.addElement("" + rs.getInt(5)); // 4 - Cód. Vendedor
					deValor = rs.getDouble(6);
					deTotal1 += deValor;
					deTotalGeral += deValor;
					vItem.addElement(new Double(deValor)); // 5 - Valor
					vItem.addElement(new Double(deTotal1)); // 6 - Total do
															// setor
					vItens.addElement(vItem);
					sCodSetorAnt = sCodSetor;
					sMesAnt = sMes;
					sCodGrupAnt = sCodGrup;
				}

				rs.close();
				ps.close();
				if (!con.getAutoCommit())
					con.commit();

				imp.limpaPags();
				vTotSetor = new Vector();

				sCodSetorAnt = "";
				sCodSetor = "";
				sMesAnt = "";
				sMes = "";
				sCodGrupAnt = "";
				sCodGrup = "";
				vCols = new Vector();

				imp.montaCab();

				for (int i = 0; i < vItens.size(); i++) {
					vItem = (Vector) vItens.elementAt(i);

					if ((!sCodSetor.equals(vItem.elementAt(POS_CODSETOR)
							.toString()))
							|| (!sMes.equals(vItem.elementAt(POS_MES)
									.toString()))
							|| (!sCodGrup.equals(vItem.elementAt(POS_CODGRUP)
									.toString()))) {
						if (!sCodSetorAnt.equals("")) {
							for (int iConta = iPosCol; iConta < NUM_COLUNAS; iConta++) {
								imp.say(imp.pRow() + 0, 21 + (iConta * 11), "|" + Funcoes.replicate(" ", 10));
							}
							imp.say(imp.pRow() + 0, 123, "|" + Funcoes.strDecimalToStrCurrency(11, 2,
											((Vector) vItens.elementAt(i - 1))
													.elementAt(POS_TOTSETOR)
													.toString()));
							imp.say(imp.pRow() + 0, 135, "|");
							imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
							imp.say(imp.pRow() + 0, 0, "+" + Funcoes.replicate("-", 133) + "+");
							iLinsSetor++;
							if (imp.pRow() >= (linPag - 1)) {
								imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
								imp.incPags();
								imp.eject();
							}
						}
						if (imp.pRow() == 0) {
							imp.impCab(136, true); // mexi aqui também
							imp.say(imp.pRow() + 0, 0, "+"
									+ Funcoes.replicate("-", 133) + "+");
							iCol = 0;
							iPosCol = 0;
						}

						//imp.impCab(136, true); mexi aqui

						if (!sCodSetor.equals(vItem.elementAt(POS_CODSETOR)
								.toString())) {
							if (!sCodSetorAnt.equals("")) {
								if (iLinsSetor > 1) {
									
									iTotPassadas = getPassadas(vCols.size());
									for (int iPassada = 0; iPassada < iTotPassadas; iPassada++) {
										imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
										imp.say(imp.pRow() + 0, 0, (iPassada == 0 ? "| SUBTOTAL" : "|        "));
										imp.say(imp.pRow() + 0, 21, getTotSetor(vTotSetor, iTotPassadas, iPassada));
										imp.say(imp.pRow() + 0, 135, "|");
										imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
										imp.say(imp.pRow() + 0, 0, "+" + Funcoes.replicate("-", 133) + "+");
									}
								}
								iLinsSetor = 0;
							}
							sCodSetor = vItem.elementAt(POS_CODSETOR)
									.toString();
							vCols = getVendedores(sCodSetor, vItens);
							vTotSetor = initTotSetor(vCols);
							iTotPassadas = getPassadas(vCols.size());

							for (int iPassada = 0; iPassada < iTotPassadas; iPassada++) {
								if (iPassada == 0) {
									imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
									imp.say(imp.pRow() + 0, 0, "|");
									imp.say(imp.pRow() + 0, 70, "SETOR: " + sCodSetor);
									imp.say(imp.pRow() + 0, 135, "|");
								}
								imp.say(imp.pRow() + 1, 0, ""
										+ imp.comprimido());
								if (iPassada == 0) {
									imp.say(imp.pRow() + 0, 0, "+" + Funcoes.replicate("-", 133) + "+");
									imp.say(imp.pRow() + 1, 0, "|MES");
									imp.say(imp.pRow() + 0, 9, "|GRUPO");
								} else {
									imp.say(imp.pRow() + 0, 0, "|");
								}
								imp.say(imp.pRow() + 0, 21, getColSetor(vCols, iTotPassadas, iPassada));
								imp.say(imp.pRow() + 0, 135, "|");
							}
							imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
							imp.say(imp.pRow() + 0, 0, "+" + Funcoes.replicate("-", 133) + "+");
							iLinsSetor = 0;
						}
						sCodSetor = vItem.elementAt(POS_CODSETOR).toString();
						sMes = vItem.elementAt(POS_MES).toString();
						sCodGrup = vItem.elementAt(POS_CODGRUP).toString();
						sCodSetorAnt = sCodSetor;
						sCodGrupAnt = sCodGrup;
						sMesAnt = sMes;
						iCol = 0;
						iPosCol = 0;
					}

					if (iCol == 0) {
						imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
						imp.say(imp.pRow() + 0, 0, "|" + vItem.elementAt(POS_MES).toString());
						imp.say(imp.pRow() + 0, 9, "| "	+ (vItem.elementAt(POS_SIGLAGRUP).equals("") ? 
								Funcoes.copy(vItem.elementAt(POS_CODGRUP).toString(), 10): 
									vItem.elementAt(POS_SIGLAGRUP).toString()));
					}

					iPosColAnt = iPosCol;
					iCol = posVendedor(vItem.elementAt(POS_CODVEND).toString(),
							vCols);
					iPosCol = getPosCol(iCol);
					if ((iCol >= NUM_COLUNAS) && (iPosCol < iPosColAnt)) {
						// Para fechar as colunas na linha atual
						for (int iAjusta = iPosColAnt; iAjusta < NUM_COLUNAS; iAjusta++) {
							imp.say(imp.pRow() + 0, 21 + (iAjusta * 11), "|"
									+ Funcoes.replicate(" ", 10));
						}
						iPosColAnt = 0;
						imp.say(imp.pRow() + 0, 124, "|");
						imp.say(imp.pRow() + 0, 135, "|");
						imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
						imp.say(imp.pRow() + 0, 0, "|");
					}
					for (int iAjusta = iPosColAnt; iAjusta < iPosCol; iAjusta++) {
						imp.say(imp.pRow() + 0, 21 + (iAjusta * 11), "|" + Funcoes.replicate(" ", 10));
					}
					//  				sTemp = ""+vItem.elementAt(POS_VALOR);
					imp.say(imp.pRow() + 0, 21 + (iPosCol * 11), "|" + Funcoes.strDecimalToStrCurrency(10, 2, vItem
									.elementAt(POS_VALOR) + ""));
					vTotSetor = adicValorSetor(iCol, (Double) vItem.elementAt(POS_VALOR), vTotSetor);
					//  				imp.say(imp.pRow()+0,136,"|");
					iCol++;
					iPosCol++;
					iPos = i;
					//  				bVlrTotal += v
				}
				// Imprime o total do setor após a impressão do relatório, caso
				// não tenha sido impresso
				if (iPos < vItens.size()) {
					for (int iConta = iCol; iConta < vCols.size(); iConta++) {
						iPosColAnt = iPosCol;
						iPosCol = getPosCol(iConta);
						if ((iConta >= NUM_COLUNAS) && (iPosCol < iPosColAnt)) {
							imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
							imp.say(imp.pRow() + 0, 0, "|");
						}
						imp.say(imp.pRow() + 0, 21 + (iPosCol * 11), "|" + Funcoes.replicate(" ", 10));
						iPosCol++;
					}
					for (int iConta = iPosCol; iConta < NUM_COLUNAS; iConta++) {
						imp.say(imp.pRow() + 0, 21 + (iConta * 11), "|" + Funcoes.replicate(" ", 10));
					}
					imp.say(imp.pRow() + 0, 124, "|" + Funcoes .strDecimalToStrCurrency(11, 2,
											((Vector) vItens.elementAt(iPos))
													.elementAt(POS_TOTSETOR)
													.toString()));
					imp.say(imp.pRow() + 0, 135, "|");
					iLinsSetor++;
					iCol = 0;
				}

				if (!sCodSetorAnt.equals("")) {
					if (iLinsSetor > 1) {
						imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
						imp.say(imp.pRow() + 0, 0, "+" + Funcoes.replicate("=", 133) + "+");
						iTotPassadas = getPassadas(vTotSetor.size());
						for (int iPassada = 0; iPassada < iTotPassadas; iPassada++) {
							imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
							imp.say(imp.pRow() + 0, 0, (iPassada == 0 ? "| SUBTOTAL" : "|        "));
							imp.say(imp.pRow() + 0, 21, getTotSetor(vTotSetor, iTotPassadas, iPassada));
							imp.say(imp.pRow() + 0, 135, "|");
						}
					}
					// Total Geral
					imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
					imp.say(imp.pRow() + 0, 0, "+" + Funcoes.replicate("=", 133) + "+");
					imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
					imp.say(imp.pRow() + 0, 0, "| TOTAL");
					imp.say(imp.pRow() + 0, 123, "|" + Funcoes.strDecimalToStrCurrency(11, 2, deTotalGeral + ""));
					imp.say(imp.pRow() + 0, 135, "|");

				}
				// Fim da impressão do total por setor

				imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
				imp.say(imp.pRow() + 0, 0, "+" + Funcoes.replicate("=", 133)
						+ "+");
				imp.eject();
				imp.fechaGravacao();

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
			sWhere = null;
			sSql = null;
			sCodMarca = null;
			sCodSetor = null;
			sCodSetorAnt = null;
			sCodGrup1 = null;
			sCodGrup2 = null;
			sCodGrupAnt = null;
			sFiltros1 = null;
			sFiltros2 = null;
			sFrom = null;
			iCodSetor = 0;
			iCodVend = 0;
			iCodCli = 0;
			iTotPassadas = 0;
			iPosCol = 0;
			iPosColAnt = 0;
			sCodGrup = null;
			sSiglaGroup = null;
//			sTipoMov = null;
			sWhereTM = null;
			sMesAnt = null;
			sMes = null;
			imp = null;
			ps = null;
			rs = null;
			vItens = null;
			vItem = null;
			vCols = null;
			deTotal1 = 0;
			deTotalGeral = 0;
			deValor = 0;
		}

	}

	private int getPassadas(int iTam) {
		int iRetorno = 0;
		iRetorno = iTam / 9;
		if ((iTam % 9) > 0)
			iRetorno++;
		return iRetorno;
	}

	private void impProduto(boolean bVisualizar) {
		String sSql = "";
		String sWhere = "";
	  	String sWhere1 = "";
	  	String sWhere2 = "";
		String sCab = "";
		String sFrom = "";
		String sCodMarca = "";
		String sCodGrup1 = "";
		String sCodGrup2 = "";
		String sFiltros1 = "";
		String sFiltros2 = "";
		String sWhereTM = "";
		int iCodSetor = 0;
		int iCodVend = 0;
		int iCodCli = 0;
		ImprimeOS imp = null;
		int linPag = 0;
		int iParam = 0;
		PreparedStatement ps = null;
		ResultSet rs = null;
		double deVlrTotal = 0;
		double deQtdTotal = 0;

		try {

			imp = new ImprimeOS("", con);
			linPag = imp.verifLinPag() - 1;
			imp.setTitulo("Relatorio de Vendas por Setor x Produto");
			sFiltros1 = "";
			sFiltros2 = "";
			
			if(rgFaturados.getVlrString().equals("S")){
				sWhere1 = " AND TM.FISCALTIPOMOV='S' ";
				sCab += " - SO FATURADO";
			}
			else if(rgFaturados.getVlrString().equals("N")){
				sWhere1 = " AND TM.FISCALTIPOMOV='N' ";
				sCab += " - NAO FATURADO";
			}
			else if(rgFaturados.getVlrString().equals("A")){
				sWhere1 = " AND TM.FISCALTIPOMOV IN ('S','N') ";
			}	
			if(rgFinanceiro.getVlrString().equals("S")){
				sWhere2 = " AND TM.SOMAVDTIPOMOV='S' ";
				sCab += " - SO FINANCEIRO";
			}
			else if(rgFinanceiro.getVlrString().equals("N")){
				sWhere2 = " AND TM.SOMAVDTIPOMOV='N' ";
				sCab += " - NAO FINANCEIRO";
			}
			else if(rgFinanceiro.getVlrString().equals("A")){
				sWhere2 = " AND TM.SOMAVDTIPOMOV IN ('S','N') ";
			}

			if(cbMovEstoque.getVlrString().equals("S")) {
				sFiltros1 += (!sFiltros1.equals("") ? " / " : "") + "SO MOV.ESTOQUE";
				sWhereTM += (cbMovEstoque.getVlrString().equals("S") ? " AND TM.ESTOQTIPOMOV='S' " : "");
			}

			sCodMarca = txtCodMarca.getVlrString().trim();
			sCodGrup1 = txtCodGrup1.getVlrString().trim();
			sCodGrup2 = txtCodGrup2.getVlrString().trim();
			iCodSetor = txtCodSetor.getVlrInteger().intValue();
			iCodVend = txtCodVend.getVlrInteger().intValue();
			iCodCli = txtCodCli.getVlrInteger().intValue();
			if (!sCodMarca.equals("")) {
				sWhere += "AND P.CODEMPMC=? AND P.CODFILIALMC=? AND P.CODMARCA=? ";
				sFiltros1 += (!sFiltros1.equals("") ? " / " : "") + "M.: "
						+ txtDescMarca.getText().trim();
			}
			if (!sCodGrup1.equals("")) {
				sWhere += "AND G.CODEMP=? AND G.CODFILIAL=? AND G.CODGRUP LIKE ? ";
				sFiltros1 += (!sFiltros1.equals("") ? " / " : "") + "G.: "
						+ txtDescGrup1.getText().trim();
			}
			if (!sCodGrup2.equals("")) {
				sWhere += "AND ( NOT P.CODGRUP=? ) ";
				sFiltros1 += (!sFiltros1.equals("") ? " / " : "")
						+ " EXCL. G.: " + txtDescGrup2.getText().trim();
			}
			if (iCodSetor != 0) {
				sWhere += "AND VD.CODSETOR=? ";
				sFiltros2 += (!sFiltros2.equals("") ? " / " : "") + " SETOR: "
						+ iCodSetor + "-" + txtDescSetor.getVlrString().trim();
			}
			if (iCodVend != 0) {
				sWhere += "AND V.CODVEND=? ";
				sFiltros2 += (!sFiltros2.equals("") ? " / " : "") + " REPR.: "
						+ iCodVend + "-" + txtNomeVend.getVlrString().trim();
			}
			if (iCodCli != 0) {
				if (iCodCli != 0) {
					if (cbCliPrinc.getVlrString().equals("S")) {
						sFiltros2 += (!sFiltros2.equals("") ? " / " : "")
								+ "AGRUP. CLI. PRINC.";
					}
					if (cbCliPrinc.getVlrString().equals("S")) {
						sWhere += "AND C2.CODCLI=V.CODCLI AND C2.CODEMP=V.CODEMPCL AND C2.CODFILIAL=V.CODFILIALCL AND "
								+ "C2.CODPESQ=C1.CODPESQ AND C2.CODEMPPQ=C1.CODEMPPQ AND C2.CODFILIALPQ=C1.CODFILIALPQ AND "
								+ "C1.CODCLI=? ";
						sFrom = ",VDCLIENTE C1, VDCLIENTE C2 ";
					} else {
						sWhere += "AND V.CODCLI=? ";
					}
					sFiltros2 += (!sFiltros2.equals("") ? " / " : "")
							+ " CLI.: " + iCodCli + "-"
							+ Funcoes.copy(txtRazCli.getVlrString(), 30);
				}
			}

			sSql = "SELECT P.DESCPROD,P.CODPROD,P.REFPROD,SUM(IV.QTDITVENDA) QTDVENDA ,SUM(IV.VLRLIQITVENDA) VLRVENDA "
					+ "FROM VDVENDA V, VDITVENDA IV, VDVENDEDOR VD, EQPRODUTO P, EQGRUPO G, EQTIPOMOV TM "
					+ sFrom
					+ "WHERE V.CODEMP=? AND V.CODFILIAL=? AND "
					+ "V.DTEMITVENDA BETWEEN ? AND ? AND "
					+ "IV.CODEMP=V.CODEMP AND IV.CODFILIAL=V.CODFILIAL AND "
					+ "IV.CODVENDA=V.CODVENDA AND IV.TIPOVENDA=V.TIPOVENDA AND "
					+ "VD.CODEMP=V.CODEMPVD AND VD.CODFILIAL=V.CODFILIALVD AND "
					+ "VD.CODVEND=V.CODVEND AND VD.CODSETOR IS NOT NULL AND "
					+ "P.CODEMP=IV.CODEMPPD AND P.CODFILIAL=IV.CODFILIALPD AND "
					+ "P.CODPROD=IV.CODPROD AND G.CODEMP=P.CODEMPGP AND "
					+ "G.CODFILIAL=P.CODFILIALGP AND "
					+ "TM.CODEMP=V.CODEMPTM AND TM.CODFILIAL=V.CODFILIALTM AND "
					+ "TM.CODTIPOMOV=V.CODTIPOMOV AND ( NOT SUBSTR(V.STATUSVENDA,1,1)='C' ) "
					+ sWhereTM + sWhere1 + sWhere2
					+ (sCodGrup1.equals("") ? " AND P.CODGRUP=G.CODGRUP " : " AND SUBSTR(P.CODGRUP,1," + sCodGrup1.length() + ")=G.CODGRUP ")
					+ sWhere
					+ "GROUP BY 1,2,3" + "ORDER BY 1,2,3";

			//  		System.out.println(sSql);

			try {
				ps = con.prepareStatement(sSql);
				ps.setInt(1, Aplicativo.iCodEmp);
				ps.setInt(2, ListaCampos.getMasterFilial("VDVENDA"));
				ps.setDate(3, Funcoes.dateToSQLDate(txtDataini.getVlrDate()));
				ps.setDate(4, Funcoes.dateToSQLDate(txtDatafim.getVlrDate()));
				iParam = 5;
				if (!sCodMarca.equals("")) {
					ps.setInt(iParam, Aplicativo.iCodEmp);
					ps.setInt(iParam + 1, ListaCampos
							.getMasterFilial("EQMARCA"));
					ps.setString(iParam + 2, sCodMarca);
					iParam += 3;
				}
				if (!sCodGrup1.equals("")) {
					ps.setInt(iParam, Aplicativo.iCodEmp);
					ps.setInt(iParam + 1, ListaCampos
							.getMasterFilial("EQGRUPO"));
					ps.setString(iParam + 2, sCodGrup1
							+ (sCodGrup1.length() < TAM_GRUPO ? "%" : ""));
					iParam += 3;
				}
				if (!sCodGrup2.equals("")) {
					ps.setString(iParam, sCodGrup2);
					iParam += 1;
				}
				if (iCodSetor != 0) {
					ps.setInt(iParam, iCodSetor);
					iParam += 1;
				}
				if (iCodVend != 0) {
					ps.setInt(iParam, iCodVend);
					iParam += 1;
				}
				if (iCodCli != 0) {
					ps.setInt(iParam, iCodCli);
					iParam += 1;
				}

				rs = ps.executeQuery();

				imp.limpaPags();

				imp.montaCab();
				
				while (rs.next()) {
					if (imp.pRow() >= (linPag - 1)) {
						imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
						imp.say(imp.pRow() + 0, 0, "+"
								+ Funcoes.replicate("-", 133) + "+");
						imp.incPags();
						imp.eject();

					}
					if (imp.pRow() == 0) {
						imp.impCab(136, true);
						if (!sFiltros1.equals("")) {
							imp.say(imp.pRow() + 0, 1, "|");
							imp.say(imp.pRow() + 0,
									68 - (sFiltros1.length() / 2), sFiltros1);
							imp.say(imp.pRow() + 0, 136, "|");
							imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
						}
						if (!sFiltros2.equals("")) {
							imp.say(imp.pRow() + 0, 1, "|");
							imp.say(imp.pRow() + 0,
									68 - (sFiltros2.length() / 2), sFiltros2);
							imp.say(imp.pRow() + 0, 136, "|");
							imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
						}
						imp.say(imp.pRow() + 0, 1, "|");
						imp.say(imp.pRow() + 0, 50, "PERIODO DE: "
								+ txtDataini.getVlrString() + " ATE: "
								+ txtDatafim.getVlrString());
						imp.say(imp.pRow() + 0, 136, "|");
						imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
						imp.say(imp.pRow() + 0, 1, "+"
								+ Funcoes.replicate("-", 133) + "+");
						imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
						imp.say(imp.pRow() + 0, 1, "| DESCRICAO DO PRODUTO");
						imp.say(imp.pRow() + 0, 55, "| CODIGO");
						imp.say(imp.pRow() + 0, 67, "| QUANTIDADE");
						imp.say(imp.pRow() + 0, 81, "|     VALOR");
						imp.say(imp.pRow() + 0, 99, "|");
						imp.say(imp.pRow() + 0, 136, "|");
						imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
						imp.say(imp.pRow() + 0, 1, "+"
								+ Funcoes.replicate("-", 133) + "+");

					}

					imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
					imp.say(imp.pRow() + 0, 1, "|");
					imp.say(imp.pRow() + 0, 4, Funcoes.adicionaEspacos(rs
							.getString(1), 50)
							+ " |");
					imp.say(imp.pRow() + 0, 56, Funcoes.adicEspacosEsquerda(rs
							.getString(2), 10)
							+ " |");
					imp.say(imp.pRow() + 0, 70, Funcoes.adicEspacosEsquerda(rs
							.getDouble(4)
							+ "", 10)
							+ " |");
					imp.say(imp.pRow() + 0, 83, Funcoes
							.strDecimalToStrCurrency(15, 2, rs.getString(5))
							+ " |");
					imp.say(imp.pRow() + 0, 136, "|");
					deQtdTotal += rs.getDouble(4);
					deVlrTotal += rs.getDouble(5);

				}

				rs.close();
				ps.close();
				if (!con.getAutoCommit())
					con.commit();

				// Fim da impressão do total por setor

				imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
				imp.say(imp.pRow() + 0, 1, "+" + Funcoes.replicate("-", 133)
						+ "+");
				imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
				imp.say(imp.pRow() + 0, 1, "| TOTAL");
				imp.say(imp.pRow() + 0, 67, "| "
						+ Funcoes.strDecimalToStrCurrency(11, 2, deQtdTotal
								+ ""));
				imp.say(imp.pRow() + 0, 81, "| "
						+ Funcoes.strDecimalToStrCurrency(15, 2, deVlrTotal
								+ "") + " |");
				imp.say(imp.pRow() + 0, 136, "|");

				imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
				imp.say(imp.pRow() + 0, 1, "+" + Funcoes.replicate("-", 133)
						+ "+");

				imp.eject();
				imp.fechaGravacao();

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
			sWhere = null;
			sSql = null;
			sFrom = null;
			sCodMarca = null;
			sCodGrup1 = null;
			sCodGrup2 = null;
			iCodSetor = 0;
			iCodVend = 0;
			iCodCli = 0;
			sFiltros1 = null;
			sFiltros2 = null;
			sWhereTM = null;
			imp = null;
			ps = null;
			rs = null;
			deVlrTotal = 0;
			deQtdTotal = 0;
		}

	}

	private void impCliente(boolean bVisualizar) {
		String sSql = "";
		String sWhere = "";
	  	String sWhere1 = "";
	  	String sWhere2 = "";
		String sWhereTM = "";
		String sCab = "";
		String sCab1 = "";
		String sCodMarca = "";
		String sCodGrup1 = "";
		String sOrdemRel = "";
		String sOrderBy = "";
		String sDescOrdemRel = "";
		String sCodGrup2 = "";
		String sFiltros1 = "";
		String sFiltros2 = "";
		String sCodTipoCli = "";
		String sDescTipoCli = "";
		String sCodTipoCliAnt = "";
		String sDescTipoCliAnt = "";
//		String sTipoMov = "";

		int iCodSetor = 0;
		int iCodCli = 0;
		int iCodVend = 0;
		ImprimeOS imp = null;
		int linPag = 0;
		int iParam = 0;
		PreparedStatement ps = null;
		ResultSet rs = null;
		double deVlrTotal = 0;
		double deQtdTotal = 0;
		double deVlrSubTotal = 0;
		double deQtdSubTotal = 0;

		try {

			imp = new ImprimeOS("", con);
			linPag = imp.verifLinPag() - 1;
			imp.setTitulo("Relatorio de Vendas por Setor x Clientes");
			sFiltros1 = "";
			sFiltros2 = "";

			if(rgFaturados.getVlrString().equals("S")){
				sWhere1 = " AND TM.FISCALTIPOMOV='S' ";
				sCab1 += " - SO FATURADO";
			}
			else if(rgFaturados.getVlrString().equals("N")){
				sWhere1 = " AND TM.FISCALTIPOMOV='N' ";
				sCab1 += " - NAO FATURADO";
			}
			else if(rgFaturados.getVlrString().equals("A")){
				sWhere1 = " AND TM.FISCALTIPOMOV IN ('S','N') ";
			}	
			if(rgFinanceiro.getVlrString().equals("S")){
				sWhere2 = " AND TM.SOMAVDTIPOMOV='S' ";
				sCab1 += " - SO FINANCEIRO";
			}
			else if(rgFinanceiro.getVlrString().equals("N")){
				sWhere2 = " AND TM.SOMAVDTIPOMOV='N' ";
				sCab1 += " - NAO FINANCEIRO";
			}
			else if(rgFinanceiro.getVlrString().equals("A")){
				sWhere2 = " AND TM.SOMAVDTIPOMOV IN ('S','N') ";
			}

			if(cbMovEstoque.getVlrString().equals("S")) {
				sFiltros1 += (!sFiltros1.equals("") ? " / " : "") + "SO MOV.ESTOQUE";
				sWhereTM += (cbMovEstoque.getVlrString().equals("S") ? " AND TM.ESTOQTIPOMOV='S' " : "");
			}

			if (cbCliPrinc.getVlrString().equals("S")) {
				sFiltros2 += (!sFiltros2.equals("") ? " / " : "")
						+ "ADIC. CLIENTES PRINCIPAIS";
			}
			
			sCodMarca = txtCodMarca.getVlrString().trim();
			sCodGrup1 = txtCodGrup1.getVlrString().trim();
			sCodGrup2 = txtCodGrup2.getVlrString().trim();
			iCodSetor = txtCodSetor.getVlrInteger().intValue();
			iCodVend = txtCodVend.getVlrInteger().intValue();
			iCodCli = txtCodCli.getVlrInteger().intValue();
			sOrdemRel = rgOrdemRel.getVlrString();
			
			if (!sCodMarca.equals("")) {
				sWhere += "AND P.CODEMPMC=? AND P.CODFILIALMC=? AND P.CODMARCA=? ";
				sFiltros1 += (!sFiltros1.equals("") ? " / " : "") + "M.: "
						+ txtDescMarca.getText().trim();
			}
			if (!sCodGrup1.equals("")) {
				sWhere += "AND G.CODEMP=? AND G.CODFILIAL=? AND G.CODGRUP LIKE ? ";
				sFiltros1 += (!sFiltros1.equals("") ? " / " : "") + "G.: "
						+ txtDescGrup1.getText().trim();
			}
			if (!sCodGrup2.equals("")) {
				sWhere += "AND ( NOT P.CODGRUP=? ) ";
				sFiltros1 += (!sFiltros1.equals("") ? " / " : "")
						+ " EXCL. G.: " + txtDescGrup2.getText().trim();
			}
			if (iCodSetor != 0) {
				sWhere += "AND VD.CODSETOR=? ";
				sFiltros2 += (!sFiltros2.equals("") ? " / " : "") + " SETOR: "
						+ iCodSetor + "-" + txtDescSetor.getVlrString().trim();
			}
			if (iCodVend != 0) {
				sWhere += "AND V.CODVEND=? ";
				sFiltros2 += (!sFiltros2.equals("") ? " / " : "") + " REPR.: "
						+ iCodVend + "-" + txtNomeVend.getVlrString().trim();
			}
			if (iCodCli != 0) {
				sWhere += "AND C2.CODCLI=? ";
				sFiltros2 += (!sFiltros2.equals("") ? " / " : "") + " CLI.: "
						+ iCodCli + "-"
						+ Funcoes.copy(txtRazCli.getVlrString(), 30);
			}

			if (sOrdemRel.equals("V")) {
				sOrderBy = "1,2,6 desc";
				sDescOrdemRel = "Valor";
			} else if (sOrdemRel.equals("R")) {
				sOrderBy = "1,2,3,4";
				sDescOrdemRel = "Razão social";
			} else if (sOrdemRel.equals("C")) {
				sOrderBy = "1,2,4";
				sDescOrdemRel = "Código do cliente";
			}
			sSql = "SELECT C2.CODTIPOCLI,TI.DESCTIPOCLI,C2.RAZCLI,C2.CODCLI,"
					+ "SUM(IV.QTDITVENDA) QTDVENDA, SUM(IV.VLRLIQITVENDA) VLRVENDA "
					+ "FROM VDVENDA V, VDITVENDA IV, VDVENDEDOR VD, EQPRODUTO P, EQGRUPO G, "
					+ "EQTIPOMOV TM, VDTIPOCLI TI, VDCLIENTE C, VDCLIENTE C2 "
					+ "WHERE V.CODEMP=? AND V.CODFILIAL=? AND "
					+ "V.DTEMITVENDA BETWEEN ? AND ? AND "
					+ "V.CODEMPCL=C.CODEMP AND V.CODFILIALCL=C.CODFILIAL AND V.CODCLI=C.CODCLI AND "
					+ (cbCliPrinc.getVlrString().equals("S") ? "C2.CODEMP=C.CODEMPPQ AND C2.CODFILIAL=C.CODFILIALPQ AND C2.CODCLI=C.CODPESQ AND ": "C2.CODEMP=C.CODEMP AND C2.CODFILIAL=C.CODFILIAL AND C2.CODCLI=C.CODCLI AND ")
					+ "TI.CODEMP=C2.CODEMPTI AND TI.CODFILIAL=C2.CODFILIALTI AND "
					+ "TI.CODTIPOCLI=C2.CODTIPOCLI AND "
					+ "IV.CODEMP=V.CODEMP AND IV.CODFILIAL=V.CODFILIAL AND "
					+ "IV.CODVENDA=V.CODVENDA AND IV.TIPOVENDA=V.TIPOVENDA AND "
					+ "VD.CODEMP=V.CODEMPVD AND VD.CODFILIAL=V.CODFILIALVD AND "
					+ "VD.CODVEND=V.CODVEND AND VD.CODSETOR IS NOT NULL AND "
					+ "P.CODEMP=IV.CODEMPPD AND P.CODFILIAL=IV.CODFILIALPD AND "
					+ "P.CODPROD=IV.CODPROD AND G.CODEMP=P.CODEMPGP AND "
					+ "G.CODFILIAL=P.CODFILIALGP AND "
					+ "TM.CODEMP=V.CODEMPTM AND TM.CODFILIAL=V.CODFILIALTM AND "
					+ "TM.CODTIPOMOV=V.CODTIPOMOV AND ( NOT SUBSTR(V.STATUSVENDA,1,1)='C' ) "
					+ sWhereTM + sWhere1 + sWhere2
					+ (sCodGrup1.equals("") ? " AND P.CODGRUP=G.CODGRUP " : " AND SUBSTR(P.CODGRUP,1," + sCodGrup1.length() + ")=G.CODGRUP ") + sWhere
					+ "GROUP BY 1,2,3,4 " + "ORDER BY " + sOrderBy;

			//System.out.println(sSql);

			try {
				ps = con.prepareStatement(sSql);
				ps.setInt(1, Aplicativo.iCodEmp);
				ps.setInt(2, ListaCampos.getMasterFilial("VDVENDA"));
				ps.setDate(3, Funcoes.dateToSQLDate(txtDataini.getVlrDate()));
				ps.setDate(4, Funcoes.dateToSQLDate(txtDatafim.getVlrDate()));
				iParam = 5;
				if (!sCodMarca.equals("")) {
					ps.setInt(iParam, Aplicativo.iCodEmp);
					ps.setInt(iParam + 1, ListaCampos
							.getMasterFilial("EQMARCA"));
					ps.setString(iParam + 2, sCodMarca);
					iParam += 3;
				}
				if (!sCodGrup1.equals("")) {
					ps.setInt(iParam, Aplicativo.iCodEmp);
					ps.setInt(iParam + 1, ListaCampos
							.getMasterFilial("EQGRUPO"));
					ps.setString(iParam + 2, sCodGrup1
							+ (sCodGrup1.length() < TAM_GRUPO ? "%" : ""));
					iParam += 3;
				}
				if (!sCodGrup2.equals("")) {
					ps.setString(iParam, sCodGrup2);
					iParam += 1;
				}
				if (iCodSetor != 0) {
					ps.setInt(iParam, iCodSetor);
					iParam += 1;
				}
				if (iCodVend != 0) {
					ps.setInt(iParam, iCodVend);
					iParam += 1;
				}
				if (iCodCli != 0) {
					ps.setInt(iParam, iCodCli);
					iParam += 1;
				}

				rs = ps.executeQuery();

				imp.limpaPags();

				imp.montaCab();
				
				while (rs.next()) {
					sCodTipoCli = rs.getString(1);
					sDescTipoCli = rs.getString(2);
					if (imp.pRow() >= (linPag - 1)) {
						imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
						imp.say(imp.pRow() + 0, 0, "+"
								+ Funcoes.replicate("-", 134) + "+");
						imp.incPags();
						imp.eject();
					}
					if (imp.pRow() == 0) {
						imp.impCab(136, true);
						if (!sFiltros1.equals("")) {
							imp.say(imp.pRow() + 0, 1, "|");
							imp.say(imp.pRow() + 0,
									68 - (sFiltros1.length() / 2), sFiltros1);
							imp.say(imp.pRow() + 0, 136, "|");
							imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
						}
						if (!sFiltros2.equals("")) {
							imp.say(imp.pRow() + 0, 1, "|");
							imp.say(imp.pRow() + 0,
									68 - (sFiltros2.length() / 2), sFiltros2);
							imp.say(imp.pRow() + 0, 136, "|");
							imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
						}
						imp.say(imp.pRow() + 0, 1, "|");
						imp.say(imp.pRow() + 0, 40, "PERIODO DE: "
								+ txtDataini.getVlrString() + " ATE: "
								+ txtDatafim.getVlrString() + " - Ordem: "
								+ sDescOrdemRel);
						imp.say(imp.pRow() + 0, 136, "|");
						imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
						imp.say(imp.pRow() + 0, 1, "+"
								+ Funcoes.replicate("-", 133) + "+");
						imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
						imp.say(imp.pRow() + 0, 1, "| RAZAO SOCIAL");
						imp.say(imp.pRow() + 0, 55, "| CODIGO");
						imp.say(imp.pRow() + 0, 67, "| QUANTIDADE");
						imp.say(imp.pRow() + 0, 81, "|     VALOR");
						imp.say(imp.pRow() + 0, 99, "|");
						imp.say(imp.pRow() + 0, 136, "|");
						imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
						imp.say(imp.pRow() + 0, 1, "+"
								+ Funcoes.replicate("-", 133) + "+");
					}
					if (!sCodTipoCli.equals(sCodTipoCliAnt)) {
						if (!sCodTipoCliAnt.equals("")) {
							sCab = "SUB-TOTAL " + sDescTipoCliAnt.trim() + ":";
							imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
							imp.say(imp.pRow() + 0, 1, "+"
									+ Funcoes.replicate("-", 133) + "+");
							imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
							imp.say(imp.pRow() + 0, 1, "|");
							imp.say(imp.pRow() + 0, 3, sCab);
							imp.say(imp.pRow() + 0, 68, "| "
									+ Funcoes.strDecimalToStrCurrency(10, 2,
											deQtdSubTotal + ""));
							imp.say(imp.pRow() + 0, 81, "| "
									+ Funcoes.strDecimalToStrCurrency(15, 2,
											deVlrSubTotal + "") + " |");
							imp.say(imp.pRow() + 0, 136, "|");
							imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
							imp.say(imp.pRow() + 0, 1, "+"
									+ Funcoes.replicate("-", 133) + "+");
							deVlrSubTotal = 0;
							deQtdSubTotal = 0;
						}
						sCab = sCodTipoCli + " - " + sDescTipoCli.trim();
						imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
						imp.say(imp.pRow() + 0, 1, "|");
						imp.say(imp.pRow() + 0, ((136 - sCab.length()) / 2),
								sCab);
						imp.say(imp.pRow() + 0, 136, "|");
						imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
						imp.say(imp.pRow() + 0, 1, "+"
								+ Funcoes.replicate("-", 133) + "+");
					}

					imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
					imp.say(imp.pRow() + 0, 1, "|");
					imp.say(imp.pRow() + 0, 4, Funcoes.adicionaEspacos(rs
							.getString(3), 50)
							+ " |");
					imp.say(imp.pRow() + 0, 56, Funcoes.adicEspacosEsquerda(rs
							.getString(4), 10)
							+ " |");
					imp.say(imp.pRow() + 0, 70, Funcoes.adicEspacosEsquerda(rs
							.getDouble(5)
							+ "", 10)
							+ " |");
					imp.say(imp.pRow() + 0, 83, Funcoes
							.strDecimalToStrCurrency(15, 2, rs.getString(6))
							+ " |");
					/*
					 * System.out.println(Funcoes.adicionaEspacos(rs.getString(3),50)+
					 * "-"+Funcoes.adicEspacosEsquerda(rs.getString(4),10)+"-"+
					 * Funcoes.adicEspacosEsquerda(rs.getDouble(5)+"",10)+"-"+
					 * Funcoes.strDecimalToStrCurrency(15,2,rs.getString(6)));
					 */
					imp.say(imp.pRow() + 0, 136, "|");
					deQtdTotal += rs.getDouble(5);
					deVlrTotal += rs.getDouble(6);
					deQtdSubTotal += rs.getDouble(5);
					deVlrSubTotal += rs.getDouble(6);
					sCodTipoCliAnt = sCodTipoCli;
					sDescTipoCliAnt = sDescTipoCli;
				}

				rs.close();
				ps.close();
				if (!con.getAutoCommit())
					con.commit();

				sCab = "SUB-TOTAL " + sDescTipoCliAnt.trim() + ":";
				imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
				imp.say(imp.pRow() + 0, 1, "+" + Funcoes.replicate("=", 133)
						+ "+");
				imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
				imp.say(imp.pRow() + 0, 1, "|");
				imp.say(imp.pRow() + 0, 3, sCab);
				imp.say(imp.pRow() + 0, 68, "| "
						+ Funcoes.strDecimalToStrCurrency(10, 2, deQtdSubTotal
								+ ""));
				imp.say(imp.pRow() + 0, 81, "| "
						+ Funcoes.strDecimalToStrCurrency(15, 2, deVlrSubTotal
								+ "") + " |");
				imp.say(imp.pRow() + 0, 136, "|");

				imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
				imp.say(imp.pRow() + 0, 1, "+" + Funcoes.replicate("=", 133)
						+ "+");
				imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
				imp.say(imp.pRow() + 0, 1, "| TOTAL");
				imp.say(imp.pRow() + 0, 68, "| "
						+ Funcoes.strDecimalToStrCurrency(10, 2, deQtdTotal
								+ ""));
				imp.say(imp.pRow() + 0, 81, "| "
						+ Funcoes.strDecimalToStrCurrency(15, 2, deVlrTotal
								+ "") + " |");
				imp.say(imp.pRow() + 0, 136, "|");

				imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
				imp.say(imp.pRow() + 0, 1, "+" + Funcoes.replicate("=", 133)
						+ "+");

				imp.eject();
				imp.fechaGravacao();

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
			sWhere = null;
			sSql = null;
			sCodMarca = null;
			sCodGrup1 = null;
			sCodGrup2 = null;
			sOrdemRel = null;
			sOrderBy = null;
			sDescOrdemRel = null;
			iCodSetor = 0;
			iCodVend = 0;
			iCodCli = 0;
			sCodTipoCli = null;
			sDescTipoCli = null;
			sCodTipoCliAnt = null;
			sDescTipoCliAnt = null;
			sWhereTM = null;
			sCab = null;
			sFiltros1 = null;
			sFiltros2 = null;
			imp = null;
			ps = null;
			rs = null;
			deVlrTotal = 0;
			deQtdTotal = 0;
			deVlrSubTotal = 0;
			deQtdSubTotal = 0;
		}

	}

	private int getPosCol(int iColSel) {
		int iRetorno = 0;
		if (iColSel < NUM_COLUNAS) // Verifica se a coluna selecionada é menor
								   // que o número de colunas total
			iRetorno = iColSel;
		else
			// caso contrário a retorna o resto
			iRetorno = iColSel % NUM_COLUNAS;
		return iRetorno;
	}

	private String getColSetor(Vector vCols, int iTotPassadas, int iPassada) {
		String sRetorno = "";
		int iCols = 0;
		int iColunas = NUM_COLUNAS;
		try {
			if ((iTotPassadas - 1) == iPassada) {
				iColunas = vCols.size() % NUM_COLUNAS;
				if (iColunas == 0)
					iColunas = NUM_COLUNAS;
			}
			for (int i = 0; i < iColunas; i++) {
				sRetorno += "| "
						+ Funcoes.adicionaEspacos(vCols.elementAt(
								i + (iPassada * NUM_COLUNAS)).toString(),
								NUM_COLUNAS);
				iCols = i;
			}
			for (int i = iCols; i < (NUM_COLUNAS - 1); i++) {
				sRetorno += "|" + Funcoes.replicate(" ", 10);
			}
			sRetorno += Funcoes.replicate(" ", 103 - sRetorno.length())
					+ (iPassada == 0 ? "| TOTAL" : "|      ");
		} finally {
			vCols = null;
		}
		return sRetorno;
	}

	private String getTotSetor(Vector vTotSetor, int iTotPassadas, int iPassada) {
		//estou aqui
		String sRetorno = "";
		double deTotal = 0;
		Double deTemp = null;
		int iCols = 0;
		int iColunas = NUM_COLUNAS;
		try {
			if ((iTotPassadas - 1) == iPassada) {
				iColunas = vTotSetor.size() % NUM_COLUNAS;
				if (iColunas == 0)
					iColunas = NUM_COLUNAS;
			} 
			for (int i = 0; i < iColunas; i++) {
				deTemp = (Double) vTotSetor.elementAt(i
						+ (iPassada * NUM_COLUNAS));
				if (deTemp == null)
					deTemp = new Double(0);
				deTotal += deTemp.doubleValue();
				sRetorno += "|"
						+ Funcoes.strDecimalToStrCurrency(10, 2, deTemp
								.toString());
				iCols = i;
			}
			for (int i = iCols; i < (NUM_COLUNAS - 1); i++) {
				sRetorno += "|" + Funcoes.replicate(" ", 10);
			}
			sRetorno += Funcoes.replicate(" ", 103 - sRetorno.length())
					+ "|"
					+ ((iTotPassadas - 1) == iPassada ? Funcoes
							.strDecimalToStrCurrency(11, 2, deTotal + "")
							: Funcoes.replicate(" ", 11)); 
		} finally {
			vTotSetor = null;
			deTemp = null;
			deTotal = 0;
		}
		return sRetorno;
	}

	private int posVendedor(String sCodVend, Vector vCols) {
		int iRetorno = -1;
		if (vCols != null) {
			for (int i = 0; i < vCols.size(); i++) {
				if (sCodVend.equals(vCols.elementAt(i).toString())) {
					iRetorno = i;
					break;
				}
			}
		}
		return iRetorno;
	}

	private Vector initTotSetor(Vector vCols) {
		Vector vRetorno = new Vector();
		for (int i = 0; i < vCols.size(); i++) {
			vRetorno.addElement(new Double(0));
		}
		return vRetorno;
	}

	private Vector adicValorSetor(int iPos, Double deValor, Vector vTotSetor) {
		Double dlTemp = null;
		double dTemp = 0;
		try {
			if ((vTotSetor != null) && (deValor != null)) {
				if (iPos < vTotSetor.size()) {
					dlTemp = (Double) vTotSetor.elementAt(iPos);
					if (dlTemp != null) {
						dTemp = dlTemp.doubleValue() + deValor.doubleValue();
						dlTemp = new Double(dTemp);
						vTotSetor.setElementAt(dlTemp, iPos);
					}
				}
			}
		} finally {
			dlTemp = null;
			dTemp = 0;
		}
		return vTotSetor;
	}

	private Vector getVendedores(String sCodSetor, Vector vItens) {
		Vector vRetorno = new Vector();
		String sCodVend = "";
		boolean bInicio = false;
		try {
			for (int i = 0; i < vItens.size(); i++) {
				if (((Vector) vItens.elementAt(i)).elementAt(POS_CODSETOR)
						.toString().equals(sCodSetor)) {
					bInicio = true;
					sCodVend = new String(((Vector) vItens.elementAt(i))
							.elementAt(POS_CODVEND).toString());
					if (vRetorno.indexOf(sCodVend) == -1)
						vRetorno.addElement(sCodVend);
				} else {
					if (bInicio)
						break;
				}
			}

			vRetorno = Funcoes.ordenaVector(vRetorno, 8);
		} finally {
			sCodVend = null;
		}
		return vRetorno;
	}

	public void setConexao(Connection cn) {
		super.setConexao(cn);
		lcMarca.setConexao(cn);
		lcGrup1.setConexao(cn);
		lcGrup2.setConexao(cn);
		lcSetor.setConexao(cn);
		lcVendedor.setConexao(cn);
		lcCliente.setConexao(cn);
	}

}