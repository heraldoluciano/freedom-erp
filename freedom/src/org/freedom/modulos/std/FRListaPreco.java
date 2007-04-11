/**
 * @version 08/12/2000 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)FRListaProd.java <BR>
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
import java.awt.GridLayout;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Vector;

import net.sf.jasperreports.engine.JasperPrintManager;

import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.ImprimeOS;
import org.freedom.componentes.JCheckBoxPad;
import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JPanelPad;
import org.freedom.componentes.JRadioGroup;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FPrinterJob;
import org.freedom.telas.FRelatorio;

public class FRListaPreco extends FRelatorio {
	private static final long serialVersionUID = 1L;

	private JPanelPad pinTipo = new JPanelPad(595,60);
	private JPanelPad pinOpt = new JPanelPad(595,100);
	private JPanelPad pinPlan = new JPanelPad(595,450);
	private JPanelPad pnTipo = new JPanelPad(JPanelPad.TP_JPANEL,new GridLayout(1,1));
	private JPanelPad pnOpt = new JPanelPad(JPanelPad.TP_JPANEL,new GridLayout(1,1));
	private JPanelPad pnPlan = new JPanelPad(JPanelPad.TP_JPANEL,new GridLayout(1,1));
	private JTextFieldPad txtCodGrup = new JTextFieldPad(JTextFieldPad.TP_STRING,14,0);
	private JTextFieldPad txtCodMarca = new JTextFieldPad(JTextFieldPad.TP_STRING,6,0);
	private JTextFieldPad txtCodClasCli = new JTextFieldPad (JTextFieldPad.TP_INTEGER,6,0);
	private JTextFieldPad txtCodTabPreco = new JTextFieldPad (JTextFieldPad.TP_INTEGER,6,0);
	private JTextFieldPad txtCodPlanoPag1 = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
	private JTextFieldPad txtCodPlanoPag2 = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
	private JTextFieldPad txtCodPlanoPag3 = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
	private JTextFieldPad txtCodPlanoPag4 = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
	private JTextFieldPad txtCodPlanoPag5 = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
	private JTextFieldPad txtCodPlanoPag6 = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
	private JTextFieldPad txtCodPlanoPag7 = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
	private JTextFieldPad txtDescGrup = new JTextFieldFK(JTextFieldPad.TP_STRING,40,0);
	private JTextFieldPad txtDescClasCli = new JTextFieldFK (JTextFieldPad.TP_STRING,40,0);
	private JTextFieldPad txtDescTabPreco = new JTextFieldFK (JTextFieldPad.TP_STRING,40,0);
	private JTextFieldPad txtDescMarca = new JTextFieldFK(JTextFieldPad.TP_STRING,40,0);
	private JTextFieldPad txtSiglaMarca = new JTextFieldFK(JTextFieldPad.TP_STRING,20,0);
	private JTextFieldPad txtDescPlanoPag1 = new JTextFieldFK(JTextFieldPad.TP_STRING,40,0);
	private JTextFieldPad txtDescPlanoPag2 = new JTextFieldFK(JTextFieldPad.TP_STRING,40,0);
	private JTextFieldPad txtDescPlanoPag3 = new JTextFieldFK(JTextFieldPad.TP_STRING,40,0);
	private JTextFieldPad txtDescPlanoPag4 = new JTextFieldFK(JTextFieldPad.TP_STRING,40,0);
	private JTextFieldPad txtDescPlanoPag5 = new JTextFieldFK(JTextFieldPad.TP_STRING,40,0);
	private JTextFieldPad txtDescPlanoPag6 = new JTextFieldFK(JTextFieldPad.TP_STRING,40,0);
	private JTextFieldPad txtDescPlanoPag7 = new JTextFieldFK(JTextFieldPad.TP_STRING,40,0);
    private JCheckBoxPad cbAgrupar = new JCheckBoxPad("Agrupar por grupo?","S","N");
	private JRadioGroup rgTipo = null;
	private JRadioGroup rgOrdem = null;
	private Vector vLabs = new Vector(2);
	private Vector vVals = new Vector(2);
	private Vector vLabs2 = new Vector(2);
	private Vector vVals2 = new Vector(2);
	private ListaCampos lcGrup = new ListaCampos(this);
	private ListaCampos lcMarca = new ListaCampos(this);
	private ListaCampos lcClassCli = new ListaCampos(this);
	private ListaCampos lcTabPreco = new ListaCampos(this);
	private ListaCampos lcPlanoPag1 = new ListaCampos(this);
	private ListaCampos lcPlanoPag2 = new ListaCampos(this);
	private ListaCampos lcPlanoPag3 = new ListaCampos(this);
	private ListaCampos lcPlanoPag4 = new ListaCampos(this);
	private ListaCampos lcPlanoPag5 = new ListaCampos(this);
	private ListaCampos lcPlanoPag6 = new ListaCampos(this);
	private ListaCampos lcPlanoPag7 = new ListaCampos(this);
        
	public FRListaPreco() {
		setTitulo("Lista de Preços");
		setAtribos(50,50,620,470);
		
		vLabs.addElement("Código");
		vLabs.addElement("Descrição");
		vVals.addElement("C");
		vVals.addElement("D");
		rgOrdem = new JRadioGroup(1,2,vLabs,vVals);
		rgOrdem.setVlrString("D");
		
		
		vLabs2.addElement("Gráfico");
		vLabs2.addElement("Texto");
		vVals2.addElement("G");
		vVals2.addElement("T");
		rgTipo = new JRadioGroup(1,2,vLabs2,vVals2);
		rgTipo.setVlrString("T");

		lcGrup.add(new GuardaCampo( txtCodGrup, "CodGrup", "Cód.grupo", ListaCampos.DB_PK, false));
		lcGrup.add(new GuardaCampo( txtDescGrup, "DescGrup", "Descrição do grupo", ListaCampos.DB_SI, false));
		lcGrup.montaSql(false, "GRUPO", "EQ");
		lcGrup.setReadOnly(true);
		txtCodGrup.setTabelaExterna(lcGrup);
		txtCodGrup.setFK(true);
		txtCodGrup.setNomeCampo("CodGrup");

		lcMarca.add(new GuardaCampo( txtCodMarca, "CodMarca", "Cód.marca", ListaCampos.DB_PK, false));
		lcMarca.add(new GuardaCampo( txtDescMarca, "DescMarca", "Descrição da marca", ListaCampos.DB_SI, false));
		lcMarca.add(new GuardaCampo( txtSiglaMarca, "SiglaMarca", "Sigla", ListaCampos.DB_SI, false));
		txtCodMarca.setTabelaExterna(lcMarca);
		txtCodMarca.setNomeCampo("CodMarca");
		txtCodMarca.setFK(true);
		lcMarca.setReadOnly(true);
		lcMarca.montaSql(false, "MARCA", "EQ");
		
  
		lcClassCli.add(new GuardaCampo( txtCodClasCli, "CodClasCli", "Cód.c.cli.", ListaCampos.DB_PK, false));
		lcClassCli.add(new GuardaCampo( txtDescClasCli, "DescClasCli", "Descrição da classificação do cliente", ListaCampos.DB_SI, false));
		txtCodClasCli.setTabelaExterna(lcClassCli);
		txtCodClasCli.setNomeCampo("CodClasCli");
		txtCodClasCli.setFK(true);
		lcClassCli.setReadOnly(true);
		lcClassCli.montaSql(false, "CLASCLI", "VD");
		
				
		lcTabPreco.add(new GuardaCampo( txtCodTabPreco, "CodTab", "Cód.tab.pc.", ListaCampos.DB_PK, false));
		lcTabPreco.add(new GuardaCampo( txtDescTabPreco, "DescTab", "Descrição da tabela de preço", ListaCampos.DB_SI, false));
		txtCodTabPreco.setTabelaExterna(lcTabPreco);
		txtCodTabPreco.setNomeCampo("CodTab");
		txtCodTabPreco.setFK(true);
		lcTabPreco.setReadOnly(true);
		lcTabPreco.montaSql(false, "TABPRECO", "VD");
		

		lcPlanoPag1.add(new GuardaCampo( txtCodPlanoPag1, "CodPlanoPag", "Cód.p.pag.", ListaCampos.DB_PK, false));
		lcPlanoPag1.add(new GuardaCampo( txtDescPlanoPag1, "DescPlanoPag", "Descrição do plano de pagamento", ListaCampos.DB_SI, false));
		lcPlanoPag1.montaSql(false, "PLANOPAG", "FN");
		lcPlanoPag1.setReadOnly(true);
		txtCodPlanoPag1.setTabelaExterna(lcPlanoPag1);
		txtCodPlanoPag1.setFK(true);
		txtCodPlanoPag1.setNomeCampo("CodPlanoPag");

		lcPlanoPag2.add(new GuardaCampo( txtCodPlanoPag2, "CodPlanoPag", "Cód.p.pag.", ListaCampos.DB_PK, false));
		lcPlanoPag2.add(new GuardaCampo( txtDescPlanoPag2, "DescPlanoPag", "Descrição do plano de pagamento", ListaCampos.DB_SI, false));
		lcPlanoPag2.montaSql(false, "PLANOPAG", "FN");
		lcPlanoPag2.setReadOnly(true);
		txtCodPlanoPag2.setTabelaExterna(lcPlanoPag2);
		txtCodPlanoPag2.setFK(true);
		txtCodPlanoPag2.setNomeCampo("CodPlanoPag");

		lcPlanoPag3.add(new GuardaCampo( txtCodPlanoPag3, "CodPlanoPag", "Cód.p.pag.", ListaCampos.DB_PK, false));
		lcPlanoPag3.add(new GuardaCampo( txtDescPlanoPag3, "DescPlanoPag", "Descrição do plano de pagamento", ListaCampos.DB_SI, false));
		lcPlanoPag3.montaSql(false, "PLANOPAG", "FN");
		lcPlanoPag3.setReadOnly(true);
		txtCodPlanoPag3.setTabelaExterna(lcPlanoPag3);
		txtCodPlanoPag3.setFK(true);
		txtCodPlanoPag3.setNomeCampo("CodPlanoPag");

		lcPlanoPag4.add(new GuardaCampo( txtCodPlanoPag4, "CodPlanoPag", "Cód.p.pag.", ListaCampos.DB_PK, false));
		lcPlanoPag4.add(new GuardaCampo( txtDescPlanoPag4, "DescPlanoPag", "Descrição do plano de pagamento", ListaCampos.DB_SI, false));
		lcPlanoPag4.montaSql(false, "PLANOPAG", "FN");
		lcPlanoPag4.setReadOnly(true);
		txtCodPlanoPag4.setTabelaExterna(lcPlanoPag4);
		txtCodPlanoPag4.setFK(true);
		txtCodPlanoPag4.setNomeCampo("CodPlanoPag");

		lcPlanoPag5.add(new GuardaCampo( txtCodPlanoPag5, "CodPlanoPag", "Cód.p.pag.", ListaCampos.DB_PK, false));
		lcPlanoPag5.add(new GuardaCampo( txtDescPlanoPag5, "DescPlanoPag", "Descrição do plano de pagamento", ListaCampos.DB_SI, false));
		lcPlanoPag5.montaSql(false, "PLANOPAG", "FN");
		lcPlanoPag5.setReadOnly(true);
		txtCodPlanoPag5.setTabelaExterna(lcPlanoPag5);
		txtCodPlanoPag5.setFK(true);
		txtCodPlanoPag5.setNomeCampo("CodPlanoPag");

		lcPlanoPag6.add(new GuardaCampo( txtCodPlanoPag6, "CodPlanoPag", "Cód.p.pag.", ListaCampos.DB_PK, false));
		lcPlanoPag6.add(new GuardaCampo( txtDescPlanoPag6, "DescPlanoPag", "Descrição do plano de pagamento", ListaCampos.DB_SI, false));
		lcPlanoPag6.montaSql(false, "PLANOPAG", "FN");
		lcPlanoPag6.setReadOnly(true);
		txtCodPlanoPag6.setTabelaExterna(lcPlanoPag6);
		txtCodPlanoPag6.setFK(true);
		txtCodPlanoPag6.setNomeCampo("CodPlanoPag");

		lcPlanoPag7.add(new GuardaCampo( txtCodPlanoPag7, "CodPlanoPag", "Cód.p.pag.", ListaCampos.DB_PK, false));
		lcPlanoPag7.add(new GuardaCampo( txtDescPlanoPag7, "DescPlanoPag", "Descrição do plano de pagamento", ListaCampos.DB_SI, false));
		lcPlanoPag7.montaSql(false, "PLANOPAG", "FN");
		lcPlanoPag7.setReadOnly(true);
		txtCodPlanoPag7.setTabelaExterna(lcPlanoPag7);
		txtCodPlanoPag7.setFK(true);
		txtCodPlanoPag7.setNomeCampo("CodPlanoPag");
		
		pnTipo.add(new JLabelPad("     Tipo do relatorio"));
		adic(pnTipo,10,5,150,20);
		adic(pinTipo,5,15,595,65);

		pinTipo.adic(new JLabelPad("Tipo"),20,5,100,15);
		pinTipo.adic(rgTipo,20,22,270,30);
		pinTipo.adic(new JLabelPad("Ordem"),300,5,100,15);
		pinTipo.adic(rgOrdem,300,22,270,30);

		pnOpt.add(new JLabelPad("    Opçôes de filtros"));
		adic(pnOpt,10,85,150,20);
		adic(pinOpt,5,95,595,100);

		pinOpt.adic(new JLabelPad("Cód.gurpo"),7,10,80,20);
		pinOpt.adic(txtCodGrup,7,30,80,20);
		pinOpt.adic(new JLabelPad("Descrição do grupo"),90,10,200,20);
		pinOpt.adic(txtDescGrup,90,30,200,20);		
		pinOpt.adic(new JLabelPad("Cód.marca"),7,50,80,20);
		pinOpt.adic(txtCodMarca,7,70,80,20);
		pinOpt.adic(new JLabelPad("Descrição da marca"),90,50,200,20);
		pinOpt.adic(txtDescMarca,90,70,200,20);		
		pinOpt.adic(new JLabelPad( "Cód.c.cli."),300,10,80,20);
		pinOpt.adic(txtCodClasCli,300,30,80,20);
		pinOpt.adic(new JLabelPad( "Descrição da classf. do cliente"),383,10,200,20);
		pinOpt.adic(txtDescClasCli,383,30,200,20);				
		pinOpt.adic(new JLabelPad("Cód.tab.pc."),300,50,80,20);
		pinOpt.adic(txtCodTabPreco,300,70,80,20);
		pinOpt.adic(new JLabelPad("Descrição da tabela de preço"),383,50,200,20);
		pinOpt.adic(txtDescTabPreco,383,70,200,20);		

		pnPlan.add(new JLabelPad("    Planos de pagamento"));
		adic(pnPlan,10,200,185,20);
		adic(pinPlan,5,210,595,180);
		
		pinPlan.adic(new JLabelPad("Cód.p.pag."),7,10,250,20);
		pinPlan.adic(txtCodPlanoPag1,7,30,80,20);
		pinPlan.adic(new JLabelPad("Descrição do plano de pgto. (1)"),90,10,250,20);
		pinPlan.adic(txtDescPlanoPag1,90,30,200,20);
		pinPlan.adic(new JLabelPad("Cód.p.pag."),300,10,250,20);
		pinPlan.adic(txtCodPlanoPag2,300,30,80,20);
		pinPlan.adic(new JLabelPad("Descrição do plano de pgto. (2)"),380,10,250,20);
		pinPlan.adic(txtDescPlanoPag2,383,30,200,20);
		pinPlan.adic(new JLabelPad("Cód.p.pag."),7,50,250,20);
		pinPlan.adic(txtCodPlanoPag3,7,70,80,20);
		pinPlan.adic(new JLabelPad("Descrição do plano de pgto. (3)"),90,50,250,20);
		pinPlan.adic(txtDescPlanoPag3,90,70,200,20);
		pinPlan.adic(new JLabelPad("Cód.p.pag."),300,50,250,20);
		pinPlan.adic(txtCodPlanoPag4,300,70,80,20);
		pinPlan.adic(new JLabelPad("Descrição do plano de pgto. (4)"),380,50,250,20);
		pinPlan.adic(txtDescPlanoPag4,383,70,200,20);
		pinPlan.adic(new JLabelPad("Cód.p.pag."),7,90,250,20);
		pinPlan.adic(txtCodPlanoPag5,7,110,80,20);
		pinPlan.adic(new JLabelPad("Descrição do plano de pgto. (5)"),90,90,250,20);
		pinPlan.adic(txtDescPlanoPag5,90,110,200,20);
		pinPlan.adic(new JLabelPad("Cód.p.pag."),300,90,250,20);
		pinPlan.adic(txtCodPlanoPag6,300,110,80,20);
		pinPlan.adic(new JLabelPad("Descrição do plano de pgto. (6)"),380,90,250,20);
		pinPlan.adic(txtDescPlanoPag6,383,110,200,20);
		pinPlan.adic(new JLabelPad("Cód.p.pag."),7,130,250,20);
		pinPlan.adic(txtCodPlanoPag7,7,150,80,20);
		pinPlan.adic(new JLabelPad("Descrição do plano de pgto. (7)"),90,130,250,20);
		pinPlan.adic(txtDescPlanoPag7,90,150,200,20);
        pinPlan.adic(cbAgrupar,310,150,200,20);        
        
	}	

	public void imprimir(boolean bVisualizar) {
		if(rgTipo.getVlrString().equals("G")) {
			if( txtCodPlanoPag2.getVlrInteger().intValue() > 0 || 
					txtCodPlanoPag3.getVlrInteger().intValue() > 0 || 
					txtCodPlanoPag4.getVlrInteger().intValue() > 0 || 
					txtCodPlanoPag5.getVlrInteger().intValue() > 0 || 
					txtCodPlanoPag6.getVlrInteger().intValue() > 0 || 
					txtCodPlanoPag7.getVlrInteger().intValue() > 0 )
				Funcoes.mensagemInforma( this, "Somente o plano de pagamento (1) sera mostrado." );
			imprimeGrafico(bVisualizar);
		}
		else
			imprimeTexto(bVisualizar);
	}
	
	private void imprimeTexto(boolean bVisualizar) {

		PreparedStatement ps = null;
		ResultSet rs = null;
		String sSQL = null;
		String sOrdem = rgOrdem.getVlrString();
		String sWhere = "";
		String sOrdenado = null;
		String sSubGrupo = null;
        String sCodRel = null;
        String sAgrupar = cbAgrupar.getVlrString();
		String sCodprod = "";
		String sCodProdPrint = Funcoes.replicate(" ",12)+"|";
		String sDescProd = Funcoes.replicate(" ",39)+"|";
		String sGrupoPrint = "";
		String sCodunid = "";
		//String sCodgrup = "";
		String sTextoImp = "";
		String linhaFina = Funcoes.replicate("-",133);
		String space = Funcoes.replicate(" ",9);
		String sPrecopag1 = space+"|";
		String sPrecopag2 = space+"|";
		String sPrecopag3 = space+"|";
		String sPrecopag4 = space+"|";
		String sPrecopag5 = space+"|";
		String sPrecopag6 = space+"|";
		String sPrecopag7 = space+"|";
		String sCodgrup = "";
		String sCodgrupAnt = "X";
		ImprimeOS imp = new ImprimeOS("",con);
		int linPag = imp.verifLinPag()-1;
		int iContaItem = 0;

		imp.montaCab();
		imp.setTitulo("Lista de Preços");
		imp.addSubTitulo("LISTA DE PREÇOS");
		imp.limpaPags();		
		
        if (comRef()) 
        	sCodRel = "REFPROD";
        else
        	sCodRel = "CODPROD";

		if (sOrdem.equals("C")) {
			sOrdem = (sAgrupar.equals("S")?"P.CODGRUP,":"")+"P."+sCodRel;
			sOrdenado = "ORDENADO POR "+(sCodRel.equals("CODPROD") ? "CODIGO" : "REFERENCIA");
		}
		else {
			sOrdem = (sAgrupar.equals("S")?"P.CODGRUP,":"")+"P.DESCPROD";
			sOrdenado = "ORDENADO POR DESCRICAO";
		}
		
		sOrdem = sOrdem + ",PP.CODPLANOPAG";
		imp.addSubTitulo(sOrdenado);		
		
		if (txtCodGrup.getText().trim().length() > 0) {
			imp.addSubTitulo("GRUPO: "+txtDescGrup.getText().trim());
		}
		if (txtCodMarca.getText().trim().length() > 0) {
			sWhere += " AND P.CODMARCA = '"+txtCodMarca.getText().trim()+"'";
			imp.addSubTitulo("MARCA: "+txtDescMarca.getText().trim());
		}

		if (txtCodClasCli.getText().trim().length() > 0) {
			sWhere += " AND PP.CODCLASCLI = '"+txtCodClasCli.getText().trim()+"'";
			imp.addSubTitulo("CLASS.CLIENTE: "+txtDescClasCli.getText().trim());
		}
		
		if (txtCodTabPreco.getText().trim().length() > 0) {
			sWhere += " AND PP.CODTAB = '"+txtCodTabPreco.getText().trim()+"'";
			imp.addSubTitulo("TABELA: "+txtDescTabPreco.getText().trim());
		}
		
		try {
			
			sSQL  = "SELECT G.DESCGRUP,P.CODGRUP,P.CODPROD,P.REFPROD,P.DESCPROD,P.CODUNID,"+
					"PP.CODPLANOPAG,PG.DESCPLANOPAG,PP.PRECOPROD "+
					"FROM EQPRODUTO P, VDPRECOPROD PP, FNPLANOPAG PG, EQGRUPO G "+ 
					"WHERE P.CODPROD=PP.CODPROD " +
					"AND P.CODEMP=PP.CODEMP " +
					"AND P.CODFILIAL=PP.CODFILIAL "+
					"AND G.CODGRUP = P.CODGRUP " +
					"AND G.CODEMP=P.CODEMPGP " +
					"AND G.CODFILIAL=P.CODFILIALGP "+
					"AND P.CODGRUP LIKE ? AND P.ATIVOPROD='S' " +
					"AND PG.CODEMP=PP.CODEMPPG " +
					"AND PG.CODFILIAL=PP.CODFILIALPG "+
					"AND PG.CODPLANOPAG = PP.CODPLANOPAG "+
					"AND PP.CODPLANOPAG IN (?,?,?,?,?,?,?)"+sWhere+
					" ORDER BY "+sOrdem; 
			
			ps = con.prepareStatement(sSQL);
			ps.setString(1,txtCodGrup.getVlrString().trim().length() < 14 ? txtCodGrup.getVlrString().trim()+"%":txtCodGrup.getVlrString().trim());
			ps.setInt(2,txtCodPlanoPag1.getVlrInteger().intValue());
			ps.setInt(3,txtCodPlanoPag2.getVlrInteger().intValue());
			ps.setInt(4,txtCodPlanoPag3.getVlrInteger().intValue());
			ps.setInt(5,txtCodPlanoPag4.getVlrInteger().intValue());
			ps.setInt(6,txtCodPlanoPag5.getVlrInteger().intValue());
			ps.setInt(7,txtCodPlanoPag6.getVlrInteger().intValue());
			ps.setInt(8,txtCodPlanoPag7.getVlrInteger().intValue());
			rs = ps.executeQuery();
						
			while ( rs.next() ) {

				sCodprod = rs.getString(sCodRel);
				if (sGrupoPrint.equals("")) {
					sCodgrupAnt = rs.getString("codgrup");
					sGrupoPrint = rs.getString("DescGrup").trim()+"/"+sCodgrupAnt;
				}
				
				if (imp.pRow()>=(linPag-1)) {	
                   	imp.pulaLinha( 1, imp.comprimido() );
					imp.say(  0, "|" + linhaFina + "|" );
					imp.incPags();
					imp.eject();
				}

				if (imp.pRow()==0) {				

					imp.impCab(136, true);						
					imp.say(  0, imp.comprimido() );	
					imp.say(  0, "|" + linhaFina + "|" );
					
                   	imp.pulaLinha( 1, imp.comprimido() );
					imp.say(  0, "| Codigo");
					imp.say( 14, "| Desc.");
					imp.say( 56, "| " + Funcoes.copy(txtDescPlanoPag1.getVlrString(),0,8) +
								 "| " + Funcoes.copy(txtDescPlanoPag2.getVlrString(),0,8) +
								 "| " + Funcoes.copy(txtDescPlanoPag3.getVlrString(),0,8) +
								 "| " + Funcoes.copy(txtDescPlanoPag4.getVlrString(),0,8) +
								 "| " + Funcoes.copy(txtDescPlanoPag5.getVlrString(),0,8) +
								 "| " + Funcoes.copy(txtDescPlanoPag6.getVlrString(),0,8) +
								 "| " + Funcoes.copy(txtDescPlanoPag7.getVlrString(),0,8) +
								 "|Unidade");
					imp.say(135, "|");
					imp.pulaLinha( 1, imp.comprimido() );
					imp.say(  0, "|" + linhaFina + "|" );
					
				}

               	if ( (sAgrupar.equals("S")) && (!sCodgrup.equals(sCodgrupAnt)) ) {

					sSubGrupo = "SUBGRUPO: "+sGrupoPrint;
					sSubGrupo = "|" + Funcoes.replicate(" ",68-(sSubGrupo.length()/2)) + sSubGrupo;
					sSubGrupo += Funcoes.replicate(" ", 133-sSubGrupo.length()) + " |";
					
               		imp.pulaLinha( 1, imp.comprimido() );
			   		imp.say(  0, sSubGrupo);
			   		imp.pulaLinha( 1, imp.comprimido() );
			   		imp.say(  0, "|" + linhaFina + "|" );
			   		sCodgrup = rs.getString( "CODGRUP" );
                }
				
            	if ((sCodprod.length() > 0) && (!sCodprod.equals(sCodProdPrint))) {
            		
					sTextoImp = sPrecopag1 + sPrecopag2 + sPrecopag3 + sPrecopag4 +
								sPrecopag5 + sPrecopag6 + sPrecopag7 + " " + Funcoes.copy(sCodunid,0,7)+"|";					
					
					imp.pulaLinha( 1, imp.comprimido() );					
					imp.say(  0, "|" + Funcoes.copy(sCodProdPrint,0,12) );
					imp.say( 14, "|" + Funcoes.copy(sDescProd,0,39) );
					imp.say( 56, "|" + sTextoImp);
					
					if (!(imp.pRow()>=(linPag-1))) {
						imp.pulaLinha( 1, imp.comprimido() );
   						imp.say(  0, "|" + linhaFina + "|" );
					}
					
					sTextoImp = "";
					sPrecopag1 = space+"|";
					sPrecopag2 = space+"|";
					sPrecopag3 = space+"|";
					sPrecopag4 = space+"|";
					sPrecopag5 = space+"|";
					sPrecopag6 = space+"|";
					sPrecopag7 = space+"|";
					
                }
				
				//sCodgrup = rs.getString("codgrup");
				sCodProdPrint = rs.getString(sCodRel);
				sDescProd = rs.getString("descprod");
				sCodgrupAnt = rs.getString("codgrup");
				sGrupoPrint = rs.getString("DescGrup").trim()+"/"+sCodgrupAnt;
				
				if (rs.getString("Codplanopag").equals(txtCodPlanoPag1.getVlrString()))
					sPrecopag1 = Funcoes.strDecimalToStrCurrency(9,2,rs.getString("PrecoProd")) + "|";
				else if (rs.getString("Codplanopag").equals(txtCodPlanoPag2.getVlrString()))
					sPrecopag2 = Funcoes.strDecimalToStrCurrency(9,2,rs.getString("PrecoProd")) + "|";
				
				if (rs.getString("Codplanopag").equals(txtCodPlanoPag3.getVlrString()))
					sPrecopag3 = Funcoes.strDecimalToStrCurrency(9,2,rs.getString("PrecoProd")) + "|";
				
				if (rs.getString("Codplanopag").equals(txtCodPlanoPag4.getVlrString()))
					sPrecopag4 = Funcoes.strDecimalToStrCurrency(9,2,rs.getString("PrecoProd")) + "|";
				
				if (rs.getString("Codplanopag").equals(txtCodPlanoPag5.getVlrString()))
					sPrecopag5 = Funcoes.strDecimalToStrCurrency(9,2,rs.getString("PrecoProd")) + "|";
				
				if (rs.getString("Codplanopag").equals(txtCodPlanoPag6.getVlrString()))
					sPrecopag6 = Funcoes.strDecimalToStrCurrency(9,2,rs.getString("PrecoProd")) + "|";
				
				if (rs.getString("Codplanopag").equals(txtCodPlanoPag7.getVlrString()))
					sPrecopag7 = Funcoes.strDecimalToStrCurrency(9,2,rs.getString("PrecoProd")) + "|";
				
				sCodunid = rs.getString("Codunid");
				sCodprod = rs.getString("Codprod");
				//sCodgrup = rs.getString("Codgrup");
			    iContaItem++;
			}	

            if (sCodprod.length() > 0) {
				sTextoImp = sPrecopag1 + sPrecopag2 + sPrecopag3 + sPrecopag4 +
							sPrecopag5 + sPrecopag6 + sPrecopag7 + " " + Funcoes.copy(sCodunid,0,7) + "|";
				
				imp.pulaLinha( 1, imp.comprimido() );
				imp.say(  0, "|" + Funcoes.copy(sCodProdPrint,0,12) );
				imp.say( 14, "|" + Funcoes.copy(sDescProd,0,39) );
				imp.say( 56, "|" );
				imp.say( 57, sTextoImp ); 
            }
            
            imp.pulaLinha( 1, imp.comprimido() );
			imp.say(  0, "+" + linhaFina + "+" );

			imp.eject();
				
			imp.fechaGravacao();
			
			if (!con.getAutoCommit())
				con.commit();
			
		} catch ( SQLException err ) {
			err.printStackTrace();
			Funcoes.mensagemErro(this,"Erro consulta tabela de preços!\n"+err.getMessage(),true,con,err);
        }
		
		if (bVisualizar)
			imp.preview(this);
		else
			imp.print();
	}
	
	private void imprimeGrafico(boolean bVisualizar) {

    	PreparedStatement ps = null;
    	ResultSet rs = null;
    	String sSQL = null;
    	String sWhere = "";
		String sOrdem = rgOrdem.getVlrString();
    	String sCodRel = null;
    	HashMap hParam = new HashMap();
		FPrinterJob dlGr = null;
		   	
    	try {
    		
        	hParam.put("RAZAOEMP",Aplicativo.sRazFilial);
        	hParam.put("DESCPLANOPAG",txtDescPlanoPag1.getVlrString().trim());
        	hParam.put("DESCGRUPO",txtDescGrup.getVlrString().trim());
        	hParam.put("DESCMARCA",txtDescMarca.getVlrString().trim());
        	hParam.put("DESCCLASCLI",txtDescClasCli.getVlrString().trim());
        	hParam.put("DESCTABPRECO",txtDescTabPreco.getVlrString().trim());
        	
        	if (comRef()) 
            	sCodRel = "REFPROD";
            else
            	sCodRel = "CODPROD";

    		if (sOrdem.equals("C")) 
    			sOrdem = (cbAgrupar.getVlrString().equals("S")?"P.CODGRUP,":"")+"P."+sCodRel;
    		else 
    			sOrdem = (cbAgrupar.getVlrString().equals("S")?"P.CODGRUP,":"")+"P.DESCPROD";
        	
        	if(txtCodPlanoPag1.getVlrInteger().intValue() > 0) {
        		sWhere += " AND PP.CODPLANOPAG=" + txtCodPlanoPag1.getVlrInteger().intValue();
        	}
        	if(txtCodTabPreco.getVlrInteger().intValue() > 0) {
        		sWhere += " AND PP.CODTAB=" + txtCodTabPreco.getVlrInteger().intValue();
        	}
        	if(txtCodMarca.getVlrString().trim().length() > 0) {
        		sWhere += " AND P.CODMARCA='" + txtCodMarca.getVlrString().trim() +"'";
        	}
        	if(txtCodClasCli.getVlrInteger().intValue() > 0) {
        		sWhere += " AND PP.CODCLASCLI=" + txtCodClasCli.getVlrInteger().intValue();
        	}
        	if(txtCodGrup.getVlrString().trim().length() > 0) {
        		sWhere += " AND P.CODGRUP LIKE '" + txtCodGrup.getVlrString().trim() + "%'";
        	}
    		
    		sSQL = "SELECT PP.CODPROD, P.DESCPROD, P.REFPROD, P.CODBARPROD, P.CODUNID, PP.PRECOPROD " +
				   "FROM EQPRODUTO P, VDPRECOPROD PP " +
				   "WHERE P.CODEMP=PP.CODEMP AND P.CODFILIAL=PP.CODFILIAL AND P.CODPROD=PP.CODPROD " +
				   "AND PP.CODEMP=? AND PP.CODFILIAL=? " + sWhere +
				   " ORDER BY " + sOrdem;
    		ps = con.prepareStatement(sSQL);
    		ps.setInt(1, Aplicativo.iCodEmp);
    		ps.setInt(2, ListaCampos.getMasterFilial("VDPRECOPROD"));
    		rs = ps.executeQuery();
    		
    		dlGr = new FPrinterJob("relatorios/listapreco.jasper","LISTA DE PREÇOS","",rs,hParam,this);
    				
    		if(bVisualizar)
    			dlGr.setVisible(true);  
    		else	
    			JasperPrintManager.printReport(dlGr.getRelatorio(),true);
    		
    	} catch ( Exception e ) {
    		e.printStackTrace();
    		Funcoes.mensagemErro(this,"Erro na impressão da lista de preço!\n" + e.getMessage(),true,con,e);
		} finally {
			ps = null;
	    	rs = null;
	    	sSQL = null;
	    	hParam = null;
			dlGr = null;
			System.gc();
		}
    	
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
			err.printStackTrace();
			Funcoes.mensagemErro(this,"Erro ao carregar a tabela PREFERE1!\n"+err.getMessage(),true,con,err);
		}
		return bRetorno;
	}
	
	public void setConexao(Connection cn) {
		super.setConexao(cn);
		lcGrup.setConexao(cn);
		lcMarca.setConexao(cn);
		lcTabPreco.setConexao(cn);
		lcClassCli.setConexao(cn);
		lcPlanoPag1.setConexao(cn);
		lcPlanoPag2.setConexao(cn);
		lcPlanoPag3.setConexao(cn);
		lcPlanoPag4.setConexao(cn);
		lcPlanoPag5.setConexao(cn);
		lcPlanoPag6.setConexao(cn);
		lcPlanoPag7.setConexao(cn);
	}
}

