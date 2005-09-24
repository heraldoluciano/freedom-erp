/**
 * @version 24/03/2004 <BR>
 * @author Setpoint Informática Ltda./Anderson Sanchez <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)FRGerContas.java <BR>
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
 * Tela de opções para o relatório de gerenciamento de contas.
 * 
 */

package org.freedom.modulos.std;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Vector;

import net.sf.jasperreports.engine.JasperPrintManager;

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
import org.freedom.telas.FPrinterJob;
import org.freedom.telas.FRelatorio;

public class FRGerContas extends FRelatorio  {

  private static final long serialVersionUID = 1L;
  private JTextFieldPad txtAno = new JTextFieldPad(JTextFieldPad.TP_INTEGER,4,0);
  private JTextFieldPad txtCodSetor = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldFK txtDescSetor = new JTextFieldFK(JTextFieldPad.TP_STRING,40,0);
  private JTextFieldPad txtCodVend = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldFK txtNomeVend = new JTextFieldFK(JTextFieldPad.TP_STRING,40,0);
  private JCheckBoxPad cbVendas = new JCheckBoxPad("Só vendas?","S","N");
  private JCheckBoxPad cbCliPrinc = new JCheckBoxPad("Mostrar no cliente principal?","S","N");
  private JCheckBoxPad cbIncluiPed = new JCheckBoxPad("Incluir pedidos não faturados?","S","N");
  private JLabelPad lbCodSetor = new JLabelPad("Cód.setor");
  private JLabelPad lbDescSetor = new JLabelPad("Descrição do setor");
  private JLabelPad lbCodVend = new JLabelPad("Cód.comiss.");
  private JLabelPad lbDescVend = new JLabelPad("Nome do comissionado");
  private ListaCampos lcSetor = new ListaCampos(this);
  private ListaCampos lcVendedor = new ListaCampos(this);
  private Vector vLabOrdemRel = new Vector();
  private Vector vValOrdemRel = new Vector();
  private JRadioGroup rgOrdemRel = null;
  private final int TAM_GRUPO = 14;
  private JTextFieldPad txtCodGrup1 = new JTextFieldPad(JTextFieldPad.TP_STRING, TAM_GRUPO, 0);
  private JTextFieldFK txtDescGrup1 = new JTextFieldFK(JTextFieldPad.TP_STRING, 40, 0);
  private JTextFieldPad txtCodGrup2 = new JTextFieldPad(JTextFieldPad.TP_STRING, TAM_GRUPO, 0);
  private JTextFieldFK txtDescGrup2 = new JTextFieldFK(JTextFieldPad.TP_STRING, 40, 0);
  private ListaCampos lcGrup1 = new ListaCampos(this);
  private ListaCampos lcGrup2 = new ListaCampos(this);

  public FRGerContas() {
    setTitulo("Gerenciamento de contas");
    setAtribos(80,0,425,310);

    txtAno.setRequerido(true);
    txtAno.setVlrInteger(new Integer((new GregorianCalendar()).get(Calendar.YEAR)));

    cbVendas.setVlrString("S");
    cbCliPrinc.setVlrString("S");
    cbIncluiPed.setVlrString("N");        
    
    vLabOrdemRel.addElement("Valor");
    vLabOrdemRel.addElement("Razão social");
    vLabOrdemRel.addElement("Cód.cli.");
    vLabOrdemRel.addElement("Cidade");
    vLabOrdemRel.addElement("Categoria");
    vLabOrdemRel.addElement("Classificação");
    
    vValOrdemRel.addElement("V");
    vValOrdemRel.addElement("R");
    vValOrdemRel.addElement("C");
    vValOrdemRel.addElement("D");
    vValOrdemRel.addElement("T");
    vValOrdemRel.addElement("S");
        
    rgOrdemRel = new JRadioGroup(5,1,vLabOrdemRel,vValOrdemRel);
                
	lcGrup1.add(new GuardaCampo(txtCodGrup1, "CodGrup", "Cód.grupo",
			ListaCampos.DB_PK, false));
	lcGrup1.add(new GuardaCampo(txtDescGrup1, "DescGrup",
			"Descrição do gurpo", ListaCampos.DB_SI, false));
	lcGrup1.montaSql(false, "GRUPO", "EQ");
	lcGrup1.setReadOnly(true);
	txtCodGrup1.setTabelaExterna(lcGrup1);
	txtCodGrup1.setFK(true);
	txtCodGrup1.setNomeCampo("CodGrup");

	lcGrup2.add(new GuardaCampo(txtCodGrup2, "CodGrup", "Cód.grupo",
			ListaCampos.DB_PK, false));
	lcGrup2.add(new GuardaCampo(txtDescGrup2, "DescGrup",
			"Descrição do grupo", ListaCampos.DB_SI, false));
	lcGrup2.montaSql(false, "GRUPO", "EQ");
	lcGrup2.setReadOnly(true);
	txtCodGrup2.setTabelaExterna(lcGrup2);
	txtCodGrup2.setFK(true);
	txtCodGrup2.setNomeCampo("CodGrup");

    
    lcSetor.add(new GuardaCampo( txtCodSetor, "CodSetor","Cód.setor", ListaCampos.DB_PK, false ));
    lcSetor.add(new GuardaCampo( txtDescSetor, "DescSetor","Descrição do setor", ListaCampos.DB_SI, false ));
    lcSetor.montaSql(false,"SETOR","VD");
    lcSetor.setReadOnly(true);
    txtCodSetor.setTabelaExterna(lcSetor);
    txtCodSetor.setFK(true);
    txtCodSetor.setNomeCampo("CodSetor");

    lcVendedor.add(new GuardaCampo( txtCodVend, "CodVend","Cód.comiss.", ListaCampos.DB_PK, false ));
    lcVendedor.add(new GuardaCampo( txtNomeVend, "NomeVend","Nome do comissionado", ListaCampos.DB_SI, false ));
    lcVendedor.montaSql(false,"VENDEDOR","VD");
    lcVendedor.setReadOnly(true);
    txtCodVend.setTabelaExterna(lcVendedor);
    txtCodVend.setFK(true);
    txtCodVend.setNomeCampo("CodVend");
    
    adic(new JLabelPad("Ordem"),280,0,80,20);
    adic(rgOrdemRel,280,20,160,120);
    adic(new JLabelPad("Ano"),7,0,250,20);
    adic(txtAno,7,20,100,20);
//    adic(txtDatafim,110,20,100,20);
    
    adic(lbCodSetor,7,45,250,20);
    adic(txtCodSetor,7,65,70,20);
    adic(lbDescSetor,80,45,250,20);
    adic(txtDescSetor,80,65,190,20);
    adic(lbCodVend,7,90,250,20);
    adic(txtCodVend,7,110,70,20);
    adic(lbDescVend,80,90,250,20);
    adic(txtNomeVend,80,110,190,20);
    
    adic(cbVendas,7,180,100,25);
    adic(cbCliPrinc,110,180,250,25);
    adic(cbIncluiPed,7,205,295,25);
    
  }


	private ResultSet rodaQuery() {
		String sSql = "";
		String sWhere = "";
		String sWhereTM = "";
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
		String sCab = "";

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
/*		
			
			if (cbFaturados.getVlrString().equals("S")) {
				sFiltros1 = "SO FATURADOS";
				sWhereTM += (cbFaturados.getVlrString().equals("S") ? " AND TM.FISCALTIPOMOV='S' " : "");
			}
	*/			
//			if(cbFinanceiro.getVlrString().equals("S")) {
	//			sFiltros1 += (!sFiltros1.equals("") ? " / " : "") + "SO FINANCEIRO";	
//				sWhereTM += (cbFinanceiro.getVlrString().equals("S") ? " AND TM.SOMAVDTIPOMOV='S' " : "");
			sWhereTM += " AND TM.SOMAVDTIPOMOV='S' " ;
//			}
/*
			if(cbMovEstoque.getVlrString().equals("S")) {
				sFiltros1 += (!sFiltros1.equals("") ? " / " : "") + "SO MOV.ESTOQUE";
				sWhereTM += (cbMovEstoque.getVlrString().equals("S") ? " AND TM.ESTOQTIPOMOV='S' " : "");
			}
*/
			if (cbCliPrinc.getVlrString().equals("S")) {
				sFiltros2 += (!sFiltros2.equals("") ? " / " : "")
						+ "ADIC. CLIENTES PRINCIPAIS";
			}
			
//			sCodMarca = txtCodMarca.getVlrString().trim();
			sCodGrup1 = txtCodGrup1.getVlrString().trim();
			sCodGrup2 = txtCodGrup2.getVlrString().trim();
//			iCodSetor = txtCodSetor.getVlrInteger().intValue();
			iCodVend = txtCodVend.getVlrInteger().intValue();
//			iCodCli = txtCodCli.getVlrInteger().intValue();
			sOrdemRel = rgOrdemRel.getVlrString();
	/*		if (!sCodMarca.equals("")) {
				sWhere += "AND P.CODEMPMC=? AND P.CODFILIALMC=? AND P.CODMARCA=? ";
				sFiltros1 += (!sFiltros1.equals("") ? " / " : "") + "M.: "
						+ txtDescMarca.getText().trim();
			}*/
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
		/*	if (iCodSetor != 0) {
				sWhere += "AND VD.CODSETOR=? ";
				sFiltros2 += (!sFiltros2.equals("") ? " / " : "") + " SETOR: "
						+ iCodSetor + "-" + txtDescSetor.getVlrString().trim();
			}*/
			if (iCodVend != 0) {
				sWhere += "AND V.CODVEND=? ";
				sFiltros2 += (!sFiltros2.equals("") ? " / " : "") + " REPR.: "
						+ iCodVend + "-" + txtNomeVend.getVlrString().trim();
			}
		/*	if (iCodCli != 0) {
				sWhere += "AND C2.CODCLI=? ";
				sFiltros2 += (!sFiltros2.equals("") ? " / " : "") + " CLI.: "
						+ iCodCli + "-"
						+ Funcoes.copy(txtRazCli.getVlrString(), 30);
			}*/

		    vValOrdemRel.addElement("V");
		    vValOrdemRel.addElement("R");
		    vValOrdemRel.addElement("C");
		    vValOrdemRel.addElement("D");
		    vValOrdemRel.addElement("T");
		    vValOrdemRel.addElement("S");

			
			if (sOrdemRel.equals("V")) {
				sOrderBy = "6,2";
			} 
			else if (sOrdemRel.equals("R")) {
				sOrderBy = "2,6,3";
			} 
			else if (sOrdemRel.equals("C")) {
				sOrderBy = "1,2,6";
			}
			else if (sOrdemRel.equals("D")) {
				sOrderBy = "3,6,2";
			}
			else if (sOrdemRel.equals("T")) {
				sOrderBy = "4,6,2";
			}
			else if (sOrdemRel.equals("S")) {
				sOrderBy = "5,6,2";
			}

			
				
			sSql = "SELECT C2.CODCLI,C2.RAZCLI,C2.CIDCLI,TI.SIGLATIPOCLI,CLA.SIGLACLASCLI,"
				   	+ "  SUM((SELECT (CAST((COALESCE(SUM(1),0)) AS INTEGER)) FROM TKHISTORICO HIS WHERE HIS.CODEMPCL=C2.CODEMP AND HIS.CODFILIALCL=C2.CODFILIAL AND HIS.CODCLI=C2.CODCLI AND HIS.TIPOHISTTK='V' AND EXTRACT(YEAR FROM HIS.DATAHISTTK)="+txtAno.getVlrInteger()+" AND EXTRACT(MONTH FROM HIS.DATAHISTTK) =1)) AS JAN, "
				   	+ "  SUM((SELECT (CAST((COALESCE(SUM(1),0)) AS INTEGER)) FROM TKHISTORICO HIS WHERE HIS.CODEMPCL=C2.CODEMP AND HIS.CODFILIALCL=C2.CODFILIAL AND HIS.CODCLI=C2.CODCLI AND HIS.TIPOHISTTK='V' AND EXTRACT(YEAR FROM HIS.DATAHISTTK)="+txtAno.getVlrInteger()+" AND EXTRACT(MONTH FROM HIS.DATAHISTTK) =2)) AS FEV, "
					+ "  SUM((SELECT (CAST((COALESCE(SUM(1),0)) AS INTEGER)) FROM TKHISTORICO HIS WHERE HIS.CODEMPCL=C2.CODEMP AND HIS.CODFILIALCL=C2.CODFILIAL AND HIS.CODCLI=C2.CODCLI AND HIS.TIPOHISTTK='V' AND EXTRACT(YEAR FROM HIS.DATAHISTTK)="+txtAno.getVlrInteger()+" AND EXTRACT(MONTH FROM HIS.DATAHISTTK) =3)) AS MAR, "
					+ "  SUM((SELECT (CAST((COALESCE(SUM(1),0)) AS INTEGER)) FROM TKHISTORICO HIS WHERE HIS.CODEMPCL=C2.CODEMP AND HIS.CODFILIALCL=C2.CODFILIAL AND HIS.CODCLI=C2.CODCLI AND HIS.TIPOHISTTK='V' AND EXTRACT(YEAR FROM HIS.DATAHISTTK)="+txtAno.getVlrInteger()+" AND EXTRACT(MONTH FROM HIS.DATAHISTTK) =4)) AS ABR, "
					+ "  SUM((SELECT (CAST((COALESCE(SUM(1),0)) AS INTEGER)) FROM TKHISTORICO HIS WHERE HIS.CODEMPCL=C2.CODEMP AND HIS.CODFILIALCL=C2.CODFILIAL AND HIS.CODCLI=C2.CODCLI AND HIS.TIPOHISTTK='V' AND EXTRACT(YEAR FROM HIS.DATAHISTTK)="+txtAno.getVlrInteger()+" AND EXTRACT(MONTH FROM HIS.DATAHISTTK) =5)) AS MAI, "
					+ "  SUM((SELECT (CAST((COALESCE(SUM(1),0)) AS INTEGER)) FROM TKHISTORICO HIS WHERE HIS.CODEMPCL=C2.CODEMP AND HIS.CODFILIALCL=C2.CODFILIAL AND HIS.CODCLI=C2.CODCLI AND HIS.TIPOHISTTK='V' AND EXTRACT(YEAR FROM HIS.DATAHISTTK)="+txtAno.getVlrInteger()+" AND EXTRACT(MONTH FROM HIS.DATAHISTTK) =6)) AS JUN, "
					+ "  SUM((SELECT (CAST((COALESCE(SUM(1),0)) AS INTEGER)) FROM TKHISTORICO HIS WHERE HIS.CODEMPCL=C2.CODEMP AND HIS.CODFILIALCL=C2.CODFILIAL AND HIS.CODCLI=C2.CODCLI AND HIS.TIPOHISTTK='V' AND EXTRACT(YEAR FROM HIS.DATAHISTTK)="+txtAno.getVlrInteger()+" AND EXTRACT(MONTH FROM HIS.DATAHISTTK) =7)) AS JUL, "
					+ "  SUM((SELECT (CAST((COALESCE(SUM(1),0)) AS INTEGER)) FROM TKHISTORICO HIS WHERE HIS.CODEMPCL=C2.CODEMP AND HIS.CODFILIALCL=C2.CODFILIAL AND HIS.CODCLI=C2.CODCLI AND HIS.TIPOHISTTK='V' AND EXTRACT(YEAR FROM HIS.DATAHISTTK)="+txtAno.getVlrInteger()+" AND EXTRACT(MONTH FROM HIS.DATAHISTTK) =8)) AS AGO, "
					+ "  SUM((SELECT (CAST((COALESCE(SUM(1),0)) AS INTEGER)) FROM TKHISTORICO HIS WHERE HIS.CODEMPCL=C2.CODEMP AND HIS.CODFILIALCL=C2.CODFILIAL AND HIS.CODCLI=C2.CODCLI AND HIS.TIPOHISTTK='V' AND EXTRACT(YEAR FROM HIS.DATAHISTTK)="+txtAno.getVlrInteger()+" AND EXTRACT(MONTH FROM HIS.DATAHISTTK) =9)) AS SETE, "
					+ "  SUM((SELECT (CAST((COALESCE(SUM(1),0)) AS INTEGER)) FROM TKHISTORICO HIS WHERE HIS.CODEMPCL=C2.CODEMP AND HIS.CODFILIALCL=C2.CODFILIAL AND HIS.CODCLI=C2.CODCLI AND HIS.TIPOHISTTK='V' AND EXTRACT(YEAR FROM HIS.DATAHISTTK)="+txtAno.getVlrInteger()+" AND EXTRACT(MONTH FROM HIS.DATAHISTTK) =10)) AS OUT, "
					+ "  SUM((SELECT (CAST((COALESCE(SUM(1),0)) AS INTEGER)) FROM TKHISTORICO HIS WHERE HIS.CODEMPCL=C2.CODEMP AND HIS.CODFILIALCL=C2.CODFILIAL AND HIS.CODCLI=C2.CODCLI AND HIS.TIPOHISTTK='V' AND EXTRACT(YEAR FROM HIS.DATAHISTTK)="+txtAno.getVlrInteger()+" AND EXTRACT(MONTH FROM HIS.DATAHISTTK) =11)) AS NOV, "
					+ "  SUM((SELECT (CAST((COALESCE(SUM(1),0)) AS INTEGER)) FROM TKHISTORICO HIS WHERE HIS.CODEMPCL=C2.CODEMP AND HIS.CODFILIALCL=C2.CODFILIAL AND HIS.CODCLI=C2.CODCLI AND HIS.TIPOHISTTK='V' AND EXTRACT(YEAR FROM HIS.DATAHISTTK)="+txtAno.getVlrInteger()+" AND EXTRACT(MONTH FROM HIS.DATAHISTTK) =12)) AS DEZ, "
					//Vendas no ano
					+ "SUM((COALESCE(IV.VLRLIQITVENDA,0))) AS VENDASATUAL," 					
					//Vendas no ano anterior					
					+ "SUM((SELECT SUM(COALESCE(IV.VLRLIQITVENDA,0)) FROM VDVENDA V, VDITVENDA IV, VDVENDEDOR VD, EQPRODUTO P, EQGRUPO G, "
					+ "EQTIPOMOV TM, VDTIPOCLI TI, VDCLIENTE C, VDCLIENTE C2 "
					+ "WHERE V.CODEMP=? AND V.CODFILIAL=? AND "
					+ "EXTRACT(YEAR FROM V.DTEMITVENDA)="+txtAno.getVlrInteger()  
					+ " AND V.CODEMPCL=C.CODEMP AND V.CODFILIALCL=C.CODFILIAL AND V.CODCLI=C.CODCLI AND "
					+ (cbCliPrinc.getVlrString().equals("S") ? "C2.CODEMP=C.CODEMPPQ AND C2.CODFILIAL=C.CODFILIALPQ AND C2.CODCLI=C.CODPESQ AND "
							: "C2.CODEMP=C.CODEMP AND C2.CODFILIAL=C.CODFILIAL AND C2.CODCLI=C.CODCLI AND ")
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
					+ "TM.CODTIPOMOV=V.CODTIPOMOV AND ( NOT SUBSTR(V.STATUSVENDA,1,1)='C' ))) AS VENDASANTERIOR, "						
					//meta estimada para ano seguinte					
					+"SUM((SELECT COALESCE(CM.VLRMETAVEND,0) FROM VDCLIMETAVEND CM WHERE CM.CODEMP=C2.CODEMP AND CM.CODFILIAL=C2.CODFILIAL AND CM.CODCLI=C2.CODCLI AND ANOMETAVEND=("+txtAno.getVlrInteger()+"+1))) AS VENDASMETA "					
					
					//From principal
					
					+ "FROM VDVENDA V, VDITVENDA IV, VDVENDEDOR VD, EQPRODUTO P, EQGRUPO G, "
					+ "EQTIPOMOV TM, VDTIPOCLI TI, VDCLIENTE C, VDCLIENTE C2, VDCLASCLI CLA "

					//Where principal
					
					+ "WHERE V.CODEMP=? AND V.CODFILIAL=? AND "
					+ " CLA.CODEMP=C2.CODEMPCC AND CLA.CODFILIAL=C2.CODFILIALCC AND CLA.CODCLASCLI=C2.CODCLASCLI AND "					
					+ "EXTRACT(YEAR FROM V.DTEMITVENDA)="+txtAno.getVlrInteger() 
					+ " AND V.CODEMPCL=C.CODEMP AND V.CODFILIALCL=C.CODFILIAL AND V.CODCLI=C.CODCLI AND "
					+ (cbCliPrinc.getVlrString().equals("S") ? "C2.CODEMP=C.CODEMPPQ AND C2.CODFILIAL=C.CODFILIALPQ AND C2.CODCLI=C.CODPESQ AND "
							: "C2.CODEMP=C.CODEMP AND C2.CODFILIAL=C.CODFILIAL AND C2.CODCLI=C.CODCLI AND ")
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
					+ sWhereTM
					+ (sCodGrup1.equals("") ? " AND P.CODGRUP=G.CODGRUP "
							: " AND SUBSTR(P.CODGRUP,1," + sCodGrup1.length()
									+ ")=G.CODGRUP ") + sWhere
					+ "GROUP BY 1,2,3,4,5 " + "ORDER BY " + sOrderBy;
								
			
			System.out.println(sSql);

			try {
				ps = con.prepareStatement(sSql);
				ps.setInt(1, Aplicativo.iCodEmp);
				ps.setInt(2, ListaCampos.getMasterFilial("VDVENDA"));
				ps.setInt(3, Aplicativo.iCodEmp);
				ps.setInt(4, ListaCampos.getMasterFilial("VDVENDA"));
				
				iParam = 5;
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
				if (iCodVend != 0) {
					ps.setInt(iParam, iCodVend);
					iParam += 1;
				}
				if (iCodCli != 0) {
					ps.setInt(iParam, iCodCli);
					iParam += 1;
				}

				rs = ps.executeQuery();


			} catch (SQLException err) {
				Funcoes.mensagemErro(this, "Erro executando a consulta.\n"
						+ err.getMessage(),true,con,err);
				err.printStackTrace();
			}			
		} 
		finally {
			sWhere = null;
			sSql = null;
			sCodGrup1 = null;
			sCodGrup2 = null;
			sOrdemRel = null;
			sOrderBy = null;
			sDescOrdemRel = null;
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
			deVlrTotal = 0;
			deQtdTotal = 0;
			deVlrSubTotal = 0;
			deQtdSubTotal = 0;
		}
		return rs;

	}
	
  public void imprimir(boolean bVisualizar) {
	    
	FPrinterJob dlGr = null;
	HashMap hParam = new HashMap();
//	hParam.put("ANO",txtAno.getVlrInteger());
//	hParam.put("CODEMPVEND",new Integer(lcVendedor.getCodEmp()));
//	hParam.put("CODFILIALVEND",new Integer(lcVendedor.getCodFilial()));
//	hParam.put("CODVEND",txtCodVend.getVlrInteger());
//	hParam.put("ORDEM",rgOrdemRel.getVlrString());
	/*	
	String sSql = "SELECT CLI.CODCLI,CLI.RAZCLI,CLI.CIDCLI,TC.SIGLATIPOCLI,CLA.SIGLACLASCLI, " +
	"  (SELECT (CAST((COALESCE(SUM(1),0)) AS INTEGER)) FROM TKHISTORICO HIS WHERE HIS.CODEMPCL=CLI.CODEMP AND HIS.CODFILIALCL=CLI.CODFILIAL AND HIS.CODCLI=CLI.CODCLI AND HIS.TIPOHISTTK='V' AND EXTRACT(YEAR FROM HIS.DATAHISTTK)="+txtAno.getVlrInteger()+" AND EXTRACT(MONTH FROM HIS.DATAHISTTK) =1) AS JAN, "+
	"  (SELECT (CAST((COALESCE(SUM(1),0)) AS INTEGER)) FROM TKHISTORICO HIS WHERE HIS.CODEMPCL=CLI.CODEMP AND HIS.CODFILIALCL=CLI.CODFILIAL AND HIS.CODCLI=CLI.CODCLI AND HIS.TIPOHISTTK='V' AND EXTRACT(YEAR FROM HIS.DATAHISTTK)="+txtAno.getVlrInteger()+" AND EXTRACT(MONTH FROM HIS.DATAHISTTK) =2) AS FEV, "+
	"  (SELECT (CAST((COALESCE(SUM(1),0)) AS INTEGER)) FROM TKHISTORICO HIS WHERE HIS.CODEMPCL=CLI.CODEMP AND HIS.CODFILIALCL=CLI.CODFILIAL AND HIS.CODCLI=CLI.CODCLI AND HIS.TIPOHISTTK='V' AND EXTRACT(YEAR FROM HIS.DATAHISTTK)="+txtAno.getVlrInteger()+" AND EXTRACT(MONTH FROM HIS.DATAHISTTK) =3) AS MAR, "+
	"  (SELECT (CAST((COALESCE(SUM(1),0)) AS INTEGER)) FROM TKHISTORICO HIS WHERE HIS.CODEMPCL=CLI.CODEMP AND HIS.CODFILIALCL=CLI.CODFILIAL AND HIS.CODCLI=CLI.CODCLI AND HIS.TIPOHISTTK='V' AND EXTRACT(YEAR FROM HIS.DATAHISTTK)="+txtAno.getVlrInteger()+" AND EXTRACT(MONTH FROM HIS.DATAHISTTK) =4) AS ABR, "+
	"  (SELECT (CAST((COALESCE(SUM(1),0)) AS INTEGER)) FROM TKHISTORICO HIS WHERE HIS.CODEMPCL=CLI.CODEMP AND HIS.CODFILIALCL=CLI.CODFILIAL AND HIS.CODCLI=CLI.CODCLI AND HIS.TIPOHISTTK='V' AND EXTRACT(YEAR FROM HIS.DATAHISTTK)="+txtAno.getVlrInteger()+" AND EXTRACT(MONTH FROM HIS.DATAHISTTK) =5) AS MAI, "+
	"  (SELECT (CAST((COALESCE(SUM(1),0)) AS INTEGER)) FROM TKHISTORICO HIS WHERE HIS.CODEMPCL=CLI.CODEMP AND HIS.CODFILIALCL=CLI.CODFILIAL AND HIS.CODCLI=CLI.CODCLI AND HIS.TIPOHISTTK='V' AND EXTRACT(YEAR FROM HIS.DATAHISTTK)="+txtAno.getVlrInteger()+" AND EXTRACT(MONTH FROM HIS.DATAHISTTK) =6) AS JUN, "+
	"  (SELECT (CAST((COALESCE(SUM(1),0)) AS INTEGER)) FROM TKHISTORICO HIS WHERE HIS.CODEMPCL=CLI.CODEMP AND HIS.CODFILIALCL=CLI.CODFILIAL AND HIS.CODCLI=CLI.CODCLI AND HIS.TIPOHISTTK='V' AND EXTRACT(YEAR FROM HIS.DATAHISTTK)="+txtAno.getVlrInteger()+" AND EXTRACT(MONTH FROM HIS.DATAHISTTK) =7) AS JUL, "+
	"  (SELECT (CAST((COALESCE(SUM(1),0)) AS INTEGER)) FROM TKHISTORICO HIS WHERE HIS.CODEMPCL=CLI.CODEMP AND HIS.CODFILIALCL=CLI.CODFILIAL AND HIS.CODCLI=CLI.CODCLI AND HIS.TIPOHISTTK='V' AND EXTRACT(YEAR FROM HIS.DATAHISTTK)="+txtAno.getVlrInteger()+" AND EXTRACT(MONTH FROM HIS.DATAHISTTK) =8) AS AGO, "+
	"  (SELECT (CAST((COALESCE(SUM(1),0)) AS INTEGER)) FROM TKHISTORICO HIS WHERE HIS.CODEMPCL=CLI.CODEMP AND HIS.CODFILIALCL=CLI.CODFILIAL AND HIS.CODCLI=CLI.CODCLI AND HIS.TIPOHISTTK='V' AND EXTRACT(YEAR FROM HIS.DATAHISTTK)="+txtAno.getVlrInteger()+" AND EXTRACT(MONTH FROM HIS.DATAHISTTK) =9) AS SETE, "+
	"  (SELECT (CAST((COALESCE(SUM(1),0)) AS INTEGER)) FROM TKHISTORICO HIS WHERE HIS.CODEMPCL=CLI.CODEMP AND HIS.CODFILIALCL=CLI.CODFILIAL AND HIS.CODCLI=CLI.CODCLI AND HIS.TIPOHISTTK='V' AND EXTRACT(YEAR FROM HIS.DATAHISTTK)="+txtAno.getVlrInteger()+" AND EXTRACT(MONTH FROM HIS.DATAHISTTK) =10) AS OUT, "+
	"  (SELECT (CAST((COALESCE(SUM(1),0)) AS INTEGER)) FROM TKHISTORICO HIS WHERE HIS.CODEMPCL=CLI.CODEMP AND HIS.CODFILIALCL=CLI.CODFILIAL AND HIS.CODCLI=CLI.CODCLI AND HIS.TIPOHISTTK='V' AND EXTRACT(YEAR FROM HIS.DATAHISTTK)="+txtAno.getVlrInteger()+" AND EXTRACT(MONTH FROM HIS.DATAHISTTK) =11) AS NOV, "+
	"  (SELECT (CAST((COALESCE(SUM(1),0)) AS INTEGER)) FROM TKHISTORICO HIS WHERE HIS.CODEMPCL=CLI.CODEMP AND HIS.CODFILIALCL=CLI.CODFILIAL AND HIS.CODCLI=CLI.CODCLI AND HIS.TIPOHISTTK='V' AND EXTRACT(YEAR FROM HIS.DATAHISTTK)="+txtAno.getVlrInteger()+" AND EXTRACT(MONTH FROM HIS.DATAHISTTK) =12) AS DEZ, "+
	"  (SELECT COALESCE(SUM(IV.VLRLIQITVENDA),0) FROM VDVENDA V, VDITVENDA IV, EQTIPOMOV TM  "+
	"	WHERE V.CODCLI=CLI.CODCLI and V.CODEMPCL=CLI.CODEMP AND V.CODFILIALCL=CLI.CODFILIAL  "+
	"        AND EXTRACT(YEAR FROM V.DTEMITVENDA)="+txtAno.getVlrInteger()+"  "+
	"	AND IV.CODEMP=V.CODEMP AND IV.CODFILIAL=V.CODFILIAL AND IV.CODVENDA=V.CODVENDA AND IV.TIPOVENDA=V.TIPOVENDA "+
	"        AND TM.CODEMP=V.CODEMPTM AND TM.CODFILIAL=V.CODFILIALTM AND TM.CODTIPOMOV=V.CODTIPOMOV "+
	"        AND TM.SOMAVDTIPOMOV='S') AS VENDASATUAL, "+
	"  (SELECT COALESCE(SUM(IV.VLRLIQITVENDA),0) FROM VDVENDA V, VDITVENDA IV, EQTIPOMOV TM "+ 
	"	WHERE V.CODCLI=CLI.CODCLI and V.CODEMPCL=CLI.CODEMP AND V.CODFILIALCL=CLI.CODFILIAL  "+
	"        AND EXTRACT(YEAR FROM V.DTEMITVENDA)=("+txtAno.getVlrInteger()+"-1)  "+
	"	AND IV.CODEMP=V.CODEMP AND IV.CODFILIAL=V.CODFILIAL AND IV.CODVENDA=V.CODVENDA AND IV.TIPOVENDA=V.TIPOVENDA "+
	"        AND TM.CODEMP=V.CODEMPTM AND TM.CODFILIAL=V.CODFILIALTM AND TM.CODTIPOMOV=V.CODTIPOMOV "+
	"        AND TM.SOMAVDTIPOMOV='S') AS VENDASANTERIOR, " +
	"  (SELECT COALESCE(CM.VLRMETAVEND,0) FROM VDCLIMETAVEND CM WHERE CM.CODEMP=CLI.CODEMP AND CM.CODFILIAL=CLI.CODFILIAL AND CM.CODCLI=CLI.CODCLI AND ANOMETAVEND=("+txtAno.getVlrInteger()+"+1)) AS VENDASMETA " + 
	"FROM VDCLIENTE CLI, VDCLASCLI CLA, VDTIPOCLI TC WHERE CLA.CODEMP=CLI.CODEMPCC AND CLA.CODFILIAL=CLI.CODFILIALCC AND CLA.CODCLASCLI=CLI.CODCLASCLI "+
	"AND TC.CODEMP=CLI.CODEMPTC AND TC.CODFILIAL=CLI.CODFILIALTC AND TC.CODTIPOCLI=CLI.CODTIPOCLI "+
	"AND CLI.CODVEND=? AND CLI.CODEMPVD=? AND CODFILIALVD=? "+ 
	"ORDER BY "+rgOrdemRel.getVlrString(); 
*/
	dlGr = new FPrinterJob("relatorios/gercontas.jasper","Gerenciamento de contas","",rodaQuery(),this);	
	
	
	
	
//	dlGr = new FPrinterJob("relatorios/gercontas.jasper","Gerenciamento de contas","",this,hParam,con); 
			
			
	if(bVisualizar)
		dlGr.setVisible(true);  
	else{			
		try {
			JasperPrintManager.printReport(dlGr.getRelatorio(),true);
		}
		catch(Exception err){
			Funcoes.mensagemErro(this,"Erro na impressão de recursos de produção!"+err.getMessage(),true,con,err);
		}
	}
	
	
	
  }

  public void setConexao(Connection cn) {
    super.setConexao(cn);
    lcSetor.setConexao(cn);
    lcVendedor.setConexao(cn);
  }
  
  
  
}
