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
import java.util.Vector;

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
import org.freedom.telas.FRelatorio;

public class FRListaPreco extends FRelatorio {
	private static final long serialVersionUID = 1L;

	private JPanelPad pinOpt = new JPanelPad(595,100);
	private JPanelPad pinPlan = new JPanelPad(595,450);
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
    private JCheckBoxPad cbAgrupar = new JCheckBoxPad("Agrupar","S","N");
	private JRadioGroup rgOrdem = null;
	private Vector vLabs = new Vector(2);
	private Vector vVals = new Vector(2);
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
		setAtribos(50,50,620,385);
		vLabs.addElement("Código");
		vLabs.addElement("Descrição");
		vVals.addElement("C");
		vVals.addElement("D");
		rgOrdem = new JRadioGroup(2,1,vLabs,vVals);
		rgOrdem.setVlrString("D");

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

		pnOpt.add(new JLabelPad("Opçoes do relatório"));
		adic(pnOpt,10,7,170,15);
		adic(pinOpt,5,15,595,100);
		pnPlan.add(new JLabelPad("Planos a serem impressos"));
		adic(pnPlan,10,125,200,15);
		adic(pinPlan,5,130,595,180);

		pinOpt.adic(new JLabelPad("Cód.gurpo"),7,10,200,20);
		pinOpt.adic(txtCodGrup,7,30,70,20);
		pinOpt.adic(new JLabelPad("Descrição do grupo"),80,10,200,20);
		pinOpt.adic(txtDescGrup,80,30,145,20);
		
		pinOpt.adic(new JLabelPad("Cód.marca"),7,50,200,20);
		pinOpt.adic(txtCodMarca,7,70,70,20);
		pinOpt.adic(new JLabelPad("Descrição da marca"),80,50,200,20);
		pinOpt.adic(txtDescMarca,80,70,145,20);
		
		pinOpt.adic(new JLabelPad( "Cód.c.cli."),230,10,280,20);
		pinOpt.adic(txtCodClasCli,230,30,70,20);
		pinOpt.adic(new JLabelPad( "Descrição da classf. do cliente"),303,10,280,20);
		pinOpt.adic(txtDescClasCli,303,30,170,20);
				
		pinOpt.adic(new JLabelPad("Cód.tab.pc."),230,50,280,20);
		pinOpt.adic(txtCodTabPreco,230,70,70,20);
		pinOpt.adic(new JLabelPad("Descrição da tabela de preço"),303,50,280,20);
		pinOpt.adic(txtDescTabPreco,303,70,170,20);		
		
		pinOpt.adic(new JLabelPad("Ordem"),480,10,100,20);
		pinOpt.adic(rgOrdem,480,30,100,60);
		pinPlan.adic(new JLabelPad("Cód.p.pag."),7,10,250,20);
		pinPlan.adic(txtCodPlanoPag1,7,30,80,20);
		pinPlan.adic(new JLabelPad("Descrição do plano de pgto. (1)"),90,10,250,20);
		pinPlan.adic(txtDescPlanoPag1,90,30,200,20);
		pinPlan.adic(new JLabelPad("Cód.p.pag."),300,10,250,20);
		pinPlan.adic(txtCodPlanoPag2,300,30,77,20);
		pinPlan.adic(new JLabelPad("Descrição do plano de pgto. (2)"),380,10,250,20);
		pinPlan.adic(txtDescPlanoPag2,380,30,200,20);
		pinPlan.adic(new JLabelPad("Cód.p.pag."),7,50,250,20);
		pinPlan.adic(txtCodPlanoPag3,7,70,80,20);
		pinPlan.adic(new JLabelPad("Descrição do plano de pgto. (3)"),90,50,250,20);
		pinPlan.adic(txtDescPlanoPag3,90,70,200,20);
		pinPlan.adic(new JLabelPad("Cód.p.pag."),300,50,250,20);
		pinPlan.adic(txtCodPlanoPag4,300,70,77,20);
		pinPlan.adic(new JLabelPad("Descrição do plano de pgto. (4)"),380,50,250,20);
		pinPlan.adic(txtDescPlanoPag4,380,70,200,20);
		pinPlan.adic(new JLabelPad("Cód.p.pag."),7,90,250,20);
		pinPlan.adic(txtCodPlanoPag5,7,110,80,20);
		pinPlan.adic(new JLabelPad("Descrição do plano de pgto. (5)"),90,90,250,20);
		pinPlan.adic(txtDescPlanoPag5,90,110,200,20);
		pinPlan.adic(new JLabelPad("Cód.p.pag."),300,90,250,20);
		pinPlan.adic(txtCodPlanoPag6,300,110,77,20);
		pinPlan.adic(new JLabelPad("Descrição do plano de pgto. (6)"),380,90,250,20);
		pinPlan.adic(txtDescPlanoPag6,380,110,200,20);
		pinPlan.adic(new JLabelPad("Cód.p.pag."),7,130,250,20);
		pinPlan.adic(txtCodPlanoPag7,7,150,77,20);
		pinPlan.adic(new JLabelPad("Descrição do plano de pgto. (7)"),90,130,250,20);
		pinPlan.adic(txtDescPlanoPag7,90,150,200,20);
        pinPlan.adic(cbAgrupar,300,150,200,20);        
        
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
	public void imprimir(boolean bVisualizar) {

		PreparedStatement ps = null;
		ResultSet rs = null;
		String sSQL = null;
		String sOrdem = rgOrdem.getVlrString();
		String sCab = "";
		String sWhere = "";
		String sOrdenado = null;
		String sSubGrupo = null;
        String sCodRel = null;
        String sAgrupar = cbAgrupar.getVlrString();
		String sCodprod = "";
		String sCodProdPrint = Funcoes.replicate(" ",12)+"|";
		String sDescProd = Funcoes.replicate(" ",39)+"|";
		String sCodunid = "";
		String sCodgrup = "";
		String sTextoImp = "";
		String sPrecopag1 = Funcoes.replicate(" ",9)+"|";
		String sPrecopag2 = Funcoes.replicate(" ",9)+"|";
		String sPrecopag3 = Funcoes.replicate(" ",9)+"|";
		String sPrecopag4 = Funcoes.replicate(" ",9)+"|";
		String sPrecopag5 = Funcoes.replicate(" ",9)+"|";
		String sPrecopag6 = Funcoes.replicate(" ",9)+"|";
		String sPrecopag7 = Funcoes.replicate(" ",9)+"|";
		ImprimeOS imp = new ImprimeOS("",con);
		int linPag = imp.verifLinPag()-1;
		int iContaItem = 0;

                        
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
		sOrdenado = "|"+Funcoes.replicate(" ",68-(sOrdenado.length()/2))+sOrdenado;
		sOrdenado += Funcoes.replicate(" ",133-sOrdenado.length())+" |";
		
		if (txtCodGrup.getText().trim().length() > 0) {
			String sTmp = "GRUPO: "+txtDescGrup.getText().trim();
			sCab += "\n"+imp.comprimido();
			sTmp = "|"+Funcoes.replicate(" ",68-(sTmp.length()/2))+sTmp;
			sCab += sTmp+Funcoes.replicate(" ",133-sTmp.length())+" |";
		}
		if (txtCodMarca.getText().trim().length() > 0) {
			sWhere += " AND P.CODMARCA = '"+txtCodMarca.getText().trim()+"'";
			String sTmp = "MARCA: "+txtDescMarca.getText().trim();
			sCab += "\n"+imp.comprimido();
			sTmp = "|"+Funcoes.replicate(" ",68-(sTmp.length()/2))+sTmp;
			sCab += sTmp+Funcoes.replicate(" ",133-sTmp.length())+" |";
		}

		if (txtCodClasCli.getText().trim().length() > 0) {
			sWhere += " AND PP.CODCLASCLI = '"+txtCodClasCli.getText().trim()+"'";
			String sTmp = "CLASS.CLIENTE: "+txtDescClasCli.getText().trim();
			sCab += "\n"+imp.comprimido();
			sTmp = "|"+Funcoes.replicate(" ",68-(sTmp.length()/2))+sTmp;
			sCab += sTmp+Funcoes.replicate(" ",133-sTmp.length())+" |";
		}
		
		if (txtCodTabPreco.getText().trim().length() > 0) {
			sWhere += " AND PP.CODTAB = '"+txtCodTabPreco.getText().trim()+"'";
			String sTmp = "TABELA: "+txtDescTabPreco.getText().trim();
			sCab += "\n"+imp.comprimido();
			sTmp = "|"+Funcoes.replicate(" ",68-(sTmp.length()/2))+sTmp;
			sCab += sTmp+Funcoes.replicate(" ",133-sTmp.length())+" |";
		}
		
		try {
			
			sSQL  = "SELECT G.DESCGRUP,P.CODGRUP,P.CODPROD,P.REFPROD,"+
					"P.DESCPROD,P.CODUNID,"+
					"PP.CODPLANOPAG,PG.DESCPLANOPAG,PP.PRECOPROD "+
					"FROM EQPRODUTO P, VDPRECOPROD PP, FNPLANOPAG PG, EQGRUPO G "+ 
					"WHERE P.CODPROD = PP.CODPROD "+
					"AND G.CODGRUP = P.CODGRUP "+
					"AND P.CODGRUP LIKE ? AND P.ATIVOPROD='S' "+
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
			
			imp.montaCab();
			imp.setTitulo("Lista de Preços");
			imp.limpaPags();
			
			while ( rs.next() ) {
				
            	if ((sCodprod.length() > 0) && (!sCodprod.equals(rs.getString("codprod")))) {
            		
					sTextoImp = sPrecopag1 + sPrecopag2 + sPrecopag3 + sPrecopag4 +
								sPrecopag5 + sPrecopag6 + sPrecopag7 + " " + Funcoes.copy(sCodunid,0,7)+"|";
					
					
					imp.pulaLinha( 1, imp.comprimido() );					
					imp.say(  0, "|" + Funcoes.copy(sCodProdPrint,0,12) );
					imp.say( 14, "|" + Funcoes.copy(sDescProd,0,39) );
					imp.say( 56, "|" + sTextoImp);
					
					if (!(imp.pRow()>=(linPag-1))) {
						imp.pulaLinha( 1, imp.comprimido() );
   						imp.say(  0, "|" + Funcoes.replicate("-",133) + "|" );
					}
					
					sTextoImp = "";
					sPrecopag1 = Funcoes.replicate(" ",9)+"|";
					sPrecopag2 = Funcoes.replicate(" ",9)+"|";
					sPrecopag3 = Funcoes.replicate(" ",9)+"|";
					sPrecopag4 = Funcoes.replicate(" ",9)+"|";
					sPrecopag5 = Funcoes.replicate(" ",9)+"|";
					sPrecopag6 = Funcoes.replicate(" ",9)+"|";
					sPrecopag7 = Funcoes.replicate(" ",9)+"|";
                }
            	
				if (imp.pRow()>=(linPag-1)) {
					imp.pulaLinha( 1, imp.comprimido() );
					imp.say(  0, "|" + Funcoes.replicate("-",133) + "|" );
					imp.incPags();
					imp.eject();
				}
				
				sCodgrup = rs.getString("codgrup");
				sCodProdPrint = rs.getString(sCodRel);
				sDescProd = rs.getString("descprod");
				sCodprod = rs.getString("codprod");

				if (imp.pRow()==0) {
					
					sSubGrupo = "SUBGRUPO: "+rs.getString("DescGrup").trim();
					sSubGrupo = "|" + Funcoes.replicate(" ",68-(sSubGrupo.length()/2)) + sSubGrupo;
					sSubGrupo += Funcoes.replicate(" ", 133-sSubGrupo.length()) + " |";

					imp.impCab(136, true);
					imp.say(  0, "|" + Funcoes.replicate("-",133) + "|" );
					imp.pulaLinha( 1, imp.comprimido() );
					imp.say(  0, "|" );
					imp.say(135, "|" );
					imp.pulaLinha( 1, imp.comprimido() );
					imp.say(  0, "|");
					imp.say( 62, "LISTA DE PREÇOS");
					imp.say(135, "|");
					imp.pulaLinha( 1, imp.comprimido() );
					imp.say(  0, "|");
					imp.say(135, "|");
					imp.pulaLinha( 1, imp.comprimido() );
					imp.say(  0, sOrdenado);
					
					if (sCab.length() > 0)
						imp.say(  0, sCab);
						
					imp.pulaLinha( 1, imp.comprimido() );						
					imp.say(  0, "|");
					imp.say(135, "|");
					imp.pulaLinha( 1, imp.comprimido() );
					imp.say(  0, "|" + Funcoes.replicate("-",133) + "|" );
					
                   	if (sAgrupar.equals("S")) {
                   		imp.pulaLinha( 1, imp.comprimido() );
				   		imp.say(  0, sSubGrupo);
				   		imp.pulaLinha( 1, imp.comprimido() );
				   		imp.say(  0, "|" + Funcoes.replicate("-",133) + "|" );
                    }
                   	
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
					imp.say(  0, "|" + Funcoes.replicate("-",133) + "|" );
				}
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
				sCodgrup = rs.getString("Codgrup");
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
			imp.say(  0, "+" + Funcoes.replicate("-",133) + "+" );

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

