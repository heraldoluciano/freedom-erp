/**
 * @version 23/11/2004 <BR>
 * @author Setpoint Informática Ltda./Robson Sanchez e Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)FPrefereGeral.java <BR>
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
 * Tela de cadastro das preferências do sistema. Esse cadastro é utilizado para parametrizar o sistema de
 * acordo com as necessidades específicas da empresa.
 * 
 */

package org.freedom.modulos.std;
import java.sql.Connection;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.border.EtchedBorder;

import org.freedom.acao.CheckBoxEvent;
import org.freedom.acao.CheckBoxListener;
import org.freedom.acao.PostEvent;
import org.freedom.acao.PostListener;
import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JCheckBoxPad;
import org.freedom.componentes.JRadioGroup;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.Painel;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.FTabDados;

public class FPrefereGeral extends FTabDados implements CheckBoxListener, PostListener {
	private Painel pinVenda = new Painel(690, 220);
	private Painel pinGeral = new Painel(330, 200);
	private Painel pinPreco = new Painel(330, 200);
	private Painel pinOrc = new Painel(330, 200);
	private Painel pinFin = new Painel();
	private Painel pinSVV = new Painel();
    private Painel pinDev = new Painel();
    private Painel pinEstoq = new Painel();
	private JTextFieldPad txtCodMoeda = new JTextFieldPad(JTextFieldPad.TP_STRING, 4, 0);
	private JTextFieldFK txtDescMoeda = new JTextFieldFK(JTextFieldPad.TP_STRING, 50, 0);	
	private JTextFieldPad txtCodTabJuros = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);
	private JTextFieldFK txtDescTabJuros = new JTextFieldFK(JTextFieldPad.TP_STRING, 50, 0);	
	private JTextFieldPad txtCodMarca = new JTextFieldPad(JTextFieldPad.TP_STRING, 6, 0);
	private JTextFieldFK txtDescMarca = new JTextFieldFK(JTextFieldPad.TP_STRING, 50, 0);
	private JTextFieldPad txtCodGrup = new JTextFieldPad(JTextFieldPad.TP_STRING, 14, 0);
	private JTextFieldFK txtDescGrup = new JTextFieldFK(JTextFieldPad.TP_STRING, 50, 0);	
    private JTextFieldPad txtCodFor = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);
    private JTextFieldFK txtDescFor = new JTextFieldFK(JTextFieldPad.TP_STRING, 50, 0);    
    private JTextFieldPad txtCodTipoFor = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);
    private JTextFieldFK txtDescTipoFor = new JTextFieldFK(JTextFieldPad.TP_STRING, 50, 0);    
	private JTextFieldPad txtCodTipoMov = new JTextFieldPad();
	private JTextFieldPad txtCodTipoMov2 = new JTextFieldPad();
	private JTextFieldPad txtCodTipoMov3 = new JTextFieldPad();
	private JTextFieldPad txtCodTipoMov4 = new JTextFieldPad();
    private JTextFieldPad txtCodTipoMov5 = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
    private JTextFieldPad txtCodTipoMov6 = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
	private JTextFieldPad txtCodTransp = new JTextFieldPad();
	private JTextFieldPad txtCasasDec = new JTextFieldPad();
	private JTextFieldPad txtPercPrecoCusto = new JTextFieldPad();
	private JTextFieldPad txtAnoCC = new JTextFieldPad();
	private JTextFieldPad txtDescClassOrc = new JTextFieldPad();
	private JTextFieldPad txtTitOrcTxt01 = new JTextFieldPad();
	private JTextFieldFK txtDescTipoMov = new JTextFieldFK();
	private JTextFieldFK txtDescTipoMov2 = new JTextFieldFK();
	private JTextFieldFK txtDescTipoMov3 = new JTextFieldFK();
	private JTextFieldFK txtDescTipoMov4 = new JTextFieldFK();
    private JTextFieldFK txtDescTipoMov5 = new JTextFieldFK(JTextFieldPad.TP_STRING, 50, 0);
    private JTextFieldFK txtDescTipoMov6 = new JTextFieldFK(JTextFieldPad.TP_STRING, 50, 0);
	private JTextFieldFK txtDescTransp = new JTextFieldFK();
	private JTextFieldPad txtCodPlanoPag = new JTextFieldPad();
	private JTextFieldPad txtCodTab = new JTextFieldPad();
	private JTextFieldPad txtCodClasCli = new JTextFieldPad();
    private JTextFieldFK txtDescPlanoPag = new JTextFieldFK();
	private JTextFieldFK txtDescTab = new JTextFieldFK();
	private JTextFieldFK txtDescClasCli = new JTextFieldFK();
	
	private JCheckBoxPad cbUsaRefProd = null;
	private JCheckBoxPad cbUsaPedSeq = null;
	private JCheckBoxPad cbUsaDescEspelho = null;
	private JCheckBoxPad cbUsaClasComis = null;
	private JCheckBoxPad cbTabFreteVd = null;
	
	private JCheckBoxPad cbTabAdicVd = null;
	private JCheckBoxPad cbTravaTMNFVD = null;
	private JCheckBoxPad cbLibGeral = null;
    private JCheckBoxPad cbJurosPosCalc = null;
	private JCheckBoxPad cbRgCliObrig = null;
	private JCheckBoxPad cbCliMesmoCnpj = null;
	private JCheckBoxPad cbCnpjObrigCli = null;
	private JCheckBoxPad cbEstLotNeg = null;
	private JCheckBoxPad cbEstNeg = null;
	private JCheckBoxPad cbNatVenda = null;
	private JCheckBoxPad cbComisPDupl = null;
	private JCheckBoxPad cbCustosSICMS = null;
	private JCheckBoxPad cbBloqVenda = null;
	private ListaCampos lcMoeda = new ListaCampos(this,"MO");
	private ListaCampos lcTabJuros = new ListaCampos(this,"TJ");
	private ListaCampos lcMarca = new ListaCampos(this,"MC");
	private ListaCampos lcGrupo = new ListaCampos(this,"GP");
    private ListaCampos lcTipoFor = new ListaCampos(this,"TF");
    private ListaCampos lcFor = new ListaCampos(this,"FR");
	private ListaCampos lcTipoMov = new ListaCampos(this,"TM");
	private ListaCampos lcTipoMov2 = new ListaCampos(this,"T2");
	private ListaCampos lcTipoMov3 = new ListaCampos(this,"T3");
	private ListaCampos lcTipoMov4 = new ListaCampos(this,"T4");
    private ListaCampos lcTipoMov5 = new ListaCampos(this,"T5");
    private ListaCampos lcTipoMov6 = new ListaCampos(this,"T6");
	private ListaCampos lcTransp = new ListaCampos (this,"TN");
	private ListaCampos lcPlanoPag = new ListaCampos(this,"PG");
	private ListaCampos lcTabPreco = new ListaCampos(this,"TB");
	private ListaCampos lcClasCli = new ListaCampos(this,"CE");
	
	public FPrefereGeral() {
		setTitulo("Preferências Gerais");
		setAtribos(40, 40, 690, 340);
		
		lcMoeda.add(new GuardaCampo(txtCodMoeda,7,100,80,20,"CodMoeda","Código",true,false,null,JTextFieldPad.TP_STRING,true),"txtCodUnidx");
		lcMoeda.add(new GuardaCampo(txtDescMoeda,90,100,207,20,"SingMoeda","Descrição",false,false,null,JTextFieldPad.TP_STRING,false),"txtDescUnidx");
		lcMoeda.montaSql(false, "MOEDA", "FN");
		lcMoeda.setQueryCommit(false);
		lcMoeda.setReadOnly(true);
		txtCodMoeda.setTabelaExterna(lcMoeda);
		
		lcTabJuros.add(new GuardaCampo(txtCodTabJuros,7,100,80,20,"CodTbj","Código",true,false,null,JTextFieldPad.TP_INTEGER,false),"txtCodUnidx");
		lcTabJuros.add(new GuardaCampo(txtDescTabJuros,90,100,207,20,"DescTbJ","Descrição",false,false,null,JTextFieldPad.TP_STRING,false),"txtDescUnidx");
		lcTabJuros.montaSql(false, "TBJUROS", "FN");
		lcTabJuros.setQueryCommit(false);
		lcTabJuros.setReadOnly(true);
		txtCodTabJuros.setTabelaExterna(lcTabJuros);
		
		lcMarca.add(new GuardaCampo(txtCodMarca,7,100,80,20,"CodMarca","Código",true,false,null,JTextFieldPad.TP_STRING,false),"txtCodUnidx");
		lcMarca.add(new GuardaCampo(txtDescMarca,90,100,207,20,"DescMarca","Descrição",false,false,null,JTextFieldPad.TP_STRING,false),"txtDescUnidx");
		lcMarca.montaSql(false, "MARCA", "EQ");
		lcMarca.setQueryCommit(false);
		lcMarca.setReadOnly(true);
		txtCodMarca.setTabelaExterna(lcMarca);

		lcGrupo.add(new GuardaCampo(txtCodGrup,7,100,80,20,"CodGrup","Código",true,false,null,JTextFieldPad.TP_STRING,false),"txtCodUnidx");
		lcGrupo.add(new GuardaCampo(txtDescGrup,90,100,207,20,"DescGrup","Descrição",false,false,null,JTextFieldPad.TP_STRING,false),"txtDescUnidx");
		lcGrupo.montaSql(false, "GRUPO", "EQ");
		lcGrupo.setQueryCommit(false);
		lcGrupo.setReadOnly(true);
		txtCodGrup.setTabelaExterna(lcGrupo);

        lcFor.add(new GuardaCampo(txtCodFor,7,100,80,20,"CodFor","Código",true,false,null,JTextFieldPad.TP_INTEGER,false));
        lcFor.add(new GuardaCampo(txtDescFor,90,100,207,20,"RazFor","Razão",false,false,null,JTextFieldPad.TP_STRING,false));
        lcFor.montaSql(false, "FORNECED", "CP");
        lcFor.setQueryCommit(false);
        lcFor.setReadOnly(true);
        txtCodFor.setTabelaExterna(lcFor);

        lcTipoFor.add(new GuardaCampo(txtCodTipoFor,7,100,80,20,"CodTipoFor","Código",true,false,null,JTextFieldPad.TP_INTEGER,false));
        lcTipoFor.add(new GuardaCampo(txtDescTipoFor,90,100,207,20,"DescTipoFor","Descrição",false,false,null,JTextFieldPad.TP_STRING,false));
        lcTipoFor.montaSql(false, "TIPOFOR", "CP");
        lcTipoFor.setQueryCommit(false);
        lcTipoFor.setReadOnly(true);
        txtCodTipoFor.setTabelaExterna(lcTipoFor);

		txtCodTipoMov.setTipo(JTextFieldPad.TP_INTEGER, 8, 0);
		txtDescTipoMov.setTipo(JTextFieldPad.TP_STRING, 40, 0);
		lcTipoMov.add(new GuardaCampo(txtCodTipoMov,7,100,80,20,"CodTipoMov","Código",true,false,null,JTextFieldPad.TP_INTEGER,true),"txtCodUnidx");
		lcTipoMov.add(new GuardaCampo(txtDescTipoMov,90,100,207,20,"DescTipoMov","Descrição",false,false,null,JTextFieldPad.TP_STRING,false),"txtDescUnidx");
		lcTipoMov.montaSql(false, "TIPOMOV", "EQ");
		lcTipoMov.setQueryCommit(false);
		lcTipoMov.setReadOnly(true);
		txtCodTipoMov.setTabelaExterna(lcTipoMov);

		txtCodTipoMov2.setTipo(JTextFieldPad.TP_INTEGER, 8, 0);
		txtDescTipoMov2.setTipo(JTextFieldPad.TP_STRING, 40, 0);
		txtDescClassOrc.setTipo(JTextFieldPad.TP_STRING, 20, 0);
		txtTitOrcTxt01.setTipo(JTextFieldPad.TP_STRING,20,0);
		lcTipoMov2.add(new GuardaCampo(txtCodTipoMov2,7,100,80,20,"CodTipoMov","Código",true,false,null,JTextFieldPad.TP_INTEGER,true),"txtCodUnidx");
		lcTipoMov2.add(new GuardaCampo(txtDescTipoMov2,90,100,207,20,"DescTipoMov","Descrição",false,false,null,JTextFieldPad.TP_STRING,false),"txtDescUnidx");
		lcTipoMov2.montaSql(false, "TIPOMOV", "EQ");
		lcTipoMov2.setQueryCommit(false);
		lcTipoMov2.setReadOnly(true);
		txtCodTipoMov2.setTabelaExterna(lcTipoMov2);

		txtCodTipoMov3.setTipo(JTextFieldPad.TP_INTEGER, 8, 0);
		txtDescTipoMov3.setTipo(JTextFieldPad.TP_STRING, 40, 0);
		lcTipoMov3.add(new GuardaCampo(txtCodTipoMov3,7,100,80,20,"CodTipoMov","Código",true,false,null,JTextFieldPad.TP_INTEGER,true),"txtCodUnidx");
		lcTipoMov3.add(new GuardaCampo(txtDescTipoMov3,90,100,207,20,"DescTipoMov","Descrição",false,false,null,JTextFieldPad.TP_STRING,false),"txtDescUnidx");
		lcTipoMov3.montaSql(false, "TIPOMOV", "EQ");
		lcTipoMov3.setQueryCommit(false);
		lcTipoMov3.setReadOnly(true);
		txtCodTipoMov3.setTabelaExterna(lcTipoMov3);

		txtCodTipoMov4.setTipo(JTextFieldPad.TP_INTEGER, 8, 0);
		txtDescTipoMov4.setTipo(JTextFieldPad.TP_STRING, 40, 0);
		lcTipoMov4.add(new GuardaCampo(txtCodTipoMov4,7,100,80,20,"CodTipoMov","Código",true,false,null,JTextFieldPad.TP_INTEGER,true),"txtCodUnidx");
		lcTipoMov4.add(new GuardaCampo(txtDescTipoMov4,90,100,207,20,"DescTipoMov","Descrição",false,false,null,JTextFieldPad.TP_STRING,false),"txtDescUnidx");
		lcTipoMov4.montaSql(false, "TIPOMOV", "EQ");
		lcTipoMov4.setQueryCommit(false);
		lcTipoMov4.setReadOnly(true);
		txtCodTipoMov4.setTabelaExterna(lcTipoMov4);
		
        lcTipoMov5.add(new GuardaCampo(txtCodTipoMov5,"CodTipoMov","Código",ListaCampos.DB_PK,false));
        lcTipoMov5.add(new GuardaCampo(txtDescTipoMov5,"DescTipoMov","Descrição",ListaCampos.DB_SI,false));
        lcTipoMov5.montaSql(false, "TIPOMOV", "EQ");
        lcTipoMov5.setQueryCommit(false);
        lcTipoMov5.setReadOnly(true);
        txtCodTipoMov5.setTabelaExterna(lcTipoMov5);

        lcTipoMov6.add(new GuardaCampo(txtCodTipoMov6,"CodTipoMov","Código",ListaCampos.DB_PK,false));
        lcTipoMov6.add(new GuardaCampo(txtDescTipoMov6,"DescTipoMov","Descrição",ListaCampos.DB_SI,false));
        lcTipoMov6.montaSql(false, "TIPOMOV", "EQ");
        lcTipoMov6.setWhereAdic(" ESTIPOMOV='I' ");
        lcTipoMov6.setQueryCommit(false);
        lcTipoMov6.setReadOnly(true);
        txtCodTipoMov6.setTabelaExterna(lcTipoMov6);
        
	    txtCodTransp.setNomeCampo("CodTran");
	    txtCodTransp.setTipo(JTextFieldPad.TP_INTEGER,8,0);
	    txtDescTransp.setTipo(JTextFieldPad.TP_STRING,40,0);
	    lcTransp.add(new GuardaCampo( txtCodTransp, 7, 100, 80, 20, "CodTran", "Código", true, false, null, JTextFieldPad.TP_INTEGER,false),"txtCodTranx");
	    lcTransp.add(new GuardaCampo( txtDescTransp, 90, 100, 207, 20, "RazTran", "Descrição", false, false, null, JTextFieldPad.TP_STRING,false),"txtDescTranx");
	    txtDescTransp.setListaCampos(lcTransp);
	    txtCodTransp.setTabelaExterna(lcTransp);
		txtCodTransp.setFK(true);
	    lcTransp.montaSql(false, "TRANSP", "VD");
	    lcTransp.setQueryCommit(false);
	    lcTransp.setReadOnly(true);
	    
	    txtCodPlanoPag.setNomeCampo("CodPlanoPag");
	    txtCodPlanoPag.setTipo(JTextFieldPad.TP_INTEGER,8,0);
	    txtDescPlanoPag.setTipo(JTextFieldPad.TP_STRING,40,0);
	    lcPlanoPag.add(new GuardaCampo( txtCodPlanoPag, 7, 100, 80, 20, "CodPlanoPag", "Cód.PlanoPag", true, false, null, JTextFieldPad.TP_INTEGER,false),"txtCodPlanoPagx");
	    lcPlanoPag.add(new GuardaCampo( txtDescPlanoPag, 90, 100, 207, 20, "DescPlanoPag", "Descrição", false, false, null, JTextFieldPad.TP_STRING,false),"txtDescPlanoPagx");
	    lcPlanoPag.montaSql(false,"PLANOPAG", "FN");
	    lcPlanoPag.setReadOnly(true);
	    txtCodPlanoPag.setTabelaExterna(lcPlanoPag);
	    txtCodPlanoPag.setFK(true);
	    txtDescPlanoPag.setListaCampos(lcPlanoPag);
	    
	    txtCodTab.setNomeCampo("CodTab");
	    txtCodTab.setTipo(JTextFieldPad.TP_INTEGER,8,0);
	    txtDescTab.setTipo(JTextFieldPad.TP_STRING,40,0);
	    lcTabPreco.add(new GuardaCampo( txtCodTab, 7, 100, 80, 20, "CodTab", "Cód.Tab.", true, false, null, JTextFieldPad.TP_INTEGER,false),"txtCodTabx");
	    lcTabPreco.add(new GuardaCampo( txtDescTab, 90, 100, 207, 20, "DescTab", "Descrição", false, false, null, JTextFieldPad.TP_STRING,false),"txtDescTabx");
	    lcTabPreco.montaSql(false,"TABPRECO", "VD");
	    lcTabPreco.setReadOnly(true);
	    txtCodTab.setTabelaExterna(lcTabPreco);
	    txtCodTab.setFK(true);
	    txtDescTab.setListaCampos(lcTabPreco);

	    txtCodClasCli.setNomeCampo("CodClasCli");
	    txtCodClasCli.setTipo(JTextFieldPad.TP_INTEGER,8,0);
	    txtDescClasCli.setTipo(JTextFieldPad.TP_STRING,40,0);
	    lcClasCli.add(new GuardaCampo( txtCodClasCli, 7, 100, 80, 20, "CodClasCli", "Cód.Clas.Cli.", true, false, null, JTextFieldPad.TP_INTEGER,false),"txtCodClasClix");
	    lcClasCli.add(new GuardaCampo( txtDescClasCli, 90, 100, 207, 20, "DescClasCli", "Descrição", false, false, null, JTextFieldPad.TP_STRING,false),"txtDescClasClix");
	    lcClasCli.montaSql(false,"CLASCLI", "VD");
	    lcClasCli.setReadOnly(true);
	    txtCodClasCli.setTabelaExterna(lcClasCli);
	    txtCodClasCli.setFK(true);
	    txtDescClasCli.setListaCampos(lcClasCli);
		
		cbUsaRefProd = new JCheckBoxPad("Usar referência?", "S", "N");
		cbUsaRefProd.setVlrString("N");
		cbUsaPedSeq = new JCheckBoxPad("Pedido sequencial?", "S", "N");
		cbUsaPedSeq.setVlrString("S");
		cbUsaDescEspelho = new JCheckBoxPad("Desconto no espelho?", "S", "N");
		cbUsaDescEspelho.setVlrString("N");
		cbUsaClasComis = new JCheckBoxPad("Class. comis. na venda?", "S", "N");
		cbUsaClasComis.setVlrString("N");
		cbEstLotNeg = new JCheckBoxPad("Permit. sld. lote neg.?","S","N");
		cbEstLotNeg.setVlrString("N");
		cbEstNeg = new JCheckBoxPad("Permit. saldo negativo?","S","N");
		cbEstNeg.setVlrString("N");
		cbBloqVenda = new JCheckBoxPad("Bloquear venda após impressão da NF?","S","N");
		cbBloqVenda.setVlrString("N");
		
		cbNatVenda= new JCheckBoxPad("Habil. campo CFOP ?","S","N");
		cbNatVenda.setVlrString("S");
		
		cbComisPDupl = new JCheckBoxPad("Calcula comissão com base nas duplicatas?","S","N");
		cbComisPDupl.setVlrString("S");
		
		
		cbTabFreteVd = new JCheckBoxPad("Aba frete na venda?", "S", "N");
		cbTabFreteVd.setVlrString("S");
		cbTabAdicVd = new JCheckBoxPad("Aba adic. na venda?","S", "N");
		cbTabAdicVd.setVlrString("N");
		cbTravaTMNFVD = new JCheckBoxPad("Travar tipo de Mov. NF na inserção da venda?","S","N");
		cbTravaTMNFVD.setVlrString("S");
		cbCustosSICMS = new JCheckBoxPad("Preço de custo sem ICMS?","S","N");
		cbCustosSICMS.setVlrString("S");
		
		
		Vector vLabs = new Vector();
		Vector vVals = new Vector();
		vLabs.addElement("Custo MPM");
		vLabs.addElement("Custo PEPS");
		vVals.addElement("M");
		vVals.addElement("P");
		JRadioGroup rgTipoPrecoCusto = new JRadioGroup(1, 2, vLabs, vVals);
		rgTipoPrecoCusto.setVlrString("M");

		cbRgCliObrig = new JCheckBoxPad("RG. do cliente obrigatório?", "S", "N");
		cbRgCliObrig.setVlrString("S");

		cbCliMesmoCnpj = new JCheckBoxPad("Permitir clientes com mesmo CNPJ ?", "S", "N");
		cbCliMesmoCnpj.setVlrString("N");

		cbCnpjObrigCli = new JCheckBoxPad("CNPJ Obrigatório para o cadastro de clientes ?", "S", "N");
		cbCnpjObrigCli.setVlrString("S");
		
		setPainel(pinGeral);
		adicTab("Geral", pinGeral);
		adicCampo(txtAnoCC,7,25,100,20,"AnoCentroCusto","Ano Base C.C.",JTextFieldPad.TP_INTEGER,5,0,false,false,null,true);
		adicDB(cbRgCliObrig, 110,25,180,20, "RgCliObrig", "",JTextFieldPad.TP_STRING,true);
		adicDB(cbCliMesmoCnpj, 7,50,250,20, "CliMesmoCnpj", "",JTextFieldPad.TP_STRING,true);
		adicDB(cbCnpjObrigCli, 7,70,300,20, "CnpjObrigCli", "",JTextFieldPad.TP_STRING,true);
		adicCampo(txtCasasDec, 7,110,100,20, "CasasDec", "Casas Decimais",JTextFieldPad.TP_INTEGER,2,0,false,false,null,true);
		
		setPainel(pinVenda);
		adicTab("Venda", pinVenda);
		adicCampo(txtCodTipoMov3,350,25,50,20,"CodTipoMov3","Codigo",JTextFieldPad.TP_INTEGER,5,0,false,true,txtDescTipoMov3,false);
		adicDescFK(txtDescTipoMov3,403,25,250,20,"DescTipoMov","e tipo de movimento para pedido.",JTextFieldPad.TP_STRING,50,0);
		adicCampo(txtCodTipoMov,350,75,50,20,"CodTipoMov","Codigo",JTextFieldPad.TP_INTEGER,5,0,false,true,txtDescTipoMov,true);
		adicDescFK(txtDescTipoMov,403,75,250,20,"DescTipoMov","e tipo de movimento para NF.",JTextFieldPad.TP_STRING,50,0);
		adicCampo(txtCodTipoMov4,350,126,50,20,"CodTipoMov4","Codigo",JTextFieldPad.TP_INTEGER,5,0,false,true,txtDescTipoMov4,true);
		adicDescFK(txtDescTipoMov4,403,126,250,20,"DescTipoMov","e tipo de movimento para pedido (serviço).",JTextFieldPad.TP_STRING,50,0);
		adicCampo(txtCodTransp,350,176,50,20,"CodTran","Codigo",JTextFieldPad.TP_INTEGER,5,0,false,true,txtDescTransp,true);
		adicDescFK(txtDescTransp,403,176,250,20,"RazTran","e nome da transp.padrao para venda",JTextFieldPad.TP_STRING,50,0);
		
		
		Vector vLabs2 = new Vector();
		Vector vVals2 = new Vector();
		vLabs2.addElement("Cliente/Setor");
		vLabs2.addElement("Vendedor/Setor");
		vLabs2.addElement("Ambos");
		vVals2.addElement("C");
		vVals2.addElement("V");
		vVals2.addElement("A");
		JRadioGroup rgSetorVenda = new  JRadioGroup(3, 1, vLabs2, vVals2);
		rgSetorVenda.setVlrString("C");
		 
		adicDB(rgSetorVenda, 7,25,150,80, "SetorVenda", "Distrib. dos setores",JTextFieldPad.TP_STRING,true);
 
		Vector vLabs1 = new Vector();
		Vector vVals1 = new Vector();
		vLabs1.addElement("Por Codigo");
		vLabs1.addElement("Por Descriçao");
		vLabs1.addElement("Por Marca");
		vVals1.addElement("C");
		vVals1.addElement("D");
		vVals1.addElement("M");
		JRadioGroup rgOrdNota = new  JRadioGroup(3, 1, vLabs1, vVals1);
	  	rgOrdNota.setVlrString("C");
		 
		adicDB(rgOrdNota, 177,25,150,80, "OrdNota", " Ordem de Emissão",JTextFieldPad.TP_STRING,true);
 
		adicDB(cbUsaRefProd,7,120,160,20,"UsaRefProd","",JTextFieldPad.TP_STRING,true);
		adicDB(cbUsaPedSeq,7,140,160,20,"UsaPedSeq","",JTextFieldPad.TP_STRING,true);
		adicDB(cbUsaDescEspelho,7,160,160,20,"UsaLiqRel","",JTextFieldPad.TP_STRING,true);
		adicDB(cbUsaClasComis,177,120,160,20,"UsaClasComis","",JTextFieldPad.TP_STRING,true);
		adicDB(cbTabFreteVd,177,140,160,20,"TabFreteVd","",JTextFieldPad.TP_STRING,true);
		adicDB(cbTabAdicVd,177,160,160,20,"TabAdicVd","",JTextFieldPad.TP_STRING,true);
		adicDB(cbTravaTMNFVD,7,180,300,20,"TravaTMNFVD","",JTextFieldPad.TP_STRING,true);
		adicDB(cbEstLotNeg,7,200,160,20,"EstLotNeg","",JTextFieldPad.TP_STRING,true);
		adicDB(cbEstNeg,177,200,160,20,"EstNeg","",JTextFieldPad.TP_STRING,true);
		adicDB(cbBloqVenda,350,200,300,20,"BloqVenda","",JTextFieldPad.TP_STRING,true);
		
		adicDB(cbNatVenda,7,220,160,20,"NatVenda","",JTextFieldPad.TP_STRING,true);
		adicDB(cbComisPDupl,177,220,300,20,"ComisPDupl","",JTextFieldPad.TP_STRING,true);
		
		setPainel(pinPreco);
		adicTab("Preços", pinPreco);
		adicDB(rgTipoPrecoCusto,7,25,280,30,"TipoPrecoCusto","Controle do preco sobre o custo:",JTextFieldPad.TP_STRING,false);
		adicCampo(txtPercPrecoCusto,7,75,100,20,"PercPrecoCusto","% Min. custo",JTextFieldPad.TP_DECIMAL,6,2,false,false,null,false);
		adicDB(cbCustosSICMS,7,100,280,20,"CustoSICMS","",JTextFieldPad.TP_STRING,true);
	    adicCampo(txtCodTab,307,25,80,20,"CodTab","Código",JTextFieldPad.TP_INTEGER,8,0,false,true,txtDescTab,false);
	    adicDescFK(txtDescTab,390,25,260,20,"DescTab","e descrição da tabela de preços",JTextFieldPad.TP_STRING,40,0);
	    adicCampo(txtCodPlanoPag,307,65,80,20,"CodPlanoPag","Código",JTextFieldPad.TP_INTEGER,8,0,false,true,txtDescPlanoPag,false);
	    adicDescFK(txtDescPlanoPag,390,65,260,20,"DescPlanoPag","e descrição do plano de pagamento",JTextFieldPad.TP_STRING,40,0);
	    adicCampo(txtCodClasCli,307,105,80,20,"CodClasCli","Código",JTextFieldPad.TP_INTEGER,8,0,false,true,txtDescClasCli,false);
	    adicDescFK(txtDescClasCli,390,105,260,20,"DescClasCli","e descrição da classificação dos clientes",JTextFieldPad.TP_STRING,40,0);

		setPainel(pinOrc);
		adicTab("Orçamento", pinOrc);
		adicCampo(txtCodTipoMov2,7,25,50,20,"CodTipoMov2","Codigo",JTextFieldPad.TP_INTEGER,5,0,false,true,txtDescTipoMov,true);
		adicDescFK(txtDescTipoMov2,60,25,250,20,"DescTipoMov","e tipo de movimento para orçamentos.",JTextFieldPad.TP_STRING,50,0);
		adicCampo(txtDescClassOrc,7,65,250,20,"ClassOrc","Classe padrão para orçamento.",JTextFieldPad.TP_STRING,20,0,false,false,null,false);
		adicCampo(txtTitOrcTxt01,7,105,250,20,"TitOrcTxt01","Título para campo TXT01",JTextFieldPad.TP_STRING,20,0,false,false,null,false);

		Vector vLabsTpValidOrc1 = new Vector();
		Vector vValsTpValidOrc1 = new Vector(); 
		vLabsTpValidOrc1.addElement("Data");
		vLabsTpValidOrc1.addElement("Nro. de dias");
		vValsTpValidOrc1.addElement("D");
		vValsTpValidOrc1.addElement("N");
		JRadioGroup rgTipoValidOrc = new  JRadioGroup(2, 1, vLabsTpValidOrc1, vValsTpValidOrc1);
		rgTipoValidOrc.setVlrString("D");
		
		adicDB(rgTipoValidOrc, 7,155,150,60, "tipovalidorc", "Valid. na impressão",JTextFieldPad.TP_STRING,true);
		
		
//Financeiro
	
		setPainel(pinFin);
		adicTab("Financeiro", pinFin);

		Vector vLabs3 = new Vector();
		Vector vVals3 = new Vector();
		vLabs3.addElement("Não Vericar");
		vLabs3.addElement("Aguardar Liberação");
		vLabs3.addElement("Liberar Crédito Pré-aprovado");
		vVals3.addElement("N");
		vVals3.addElement("A");
		vVals3.addElement("L");
		JRadioGroup rgLibCred = new  JRadioGroup(3, 1, vLabs3, vVals3);
		rgLibCred.setVlrString("N");
		 
		cbLibGeral = new JCheckBoxPad("Liberação de credito globalizada?", "S", "N");
		cbLibGeral.setVlrString("S");
        cbJurosPosCalc = new JCheckBoxPad("Juros pós-calculado?", "S", "N");
        cbJurosPosCalc.setVlrString("N");

		adicCampo(txtCodMoeda,7,20,50,20,"CodMoeda","Codigo",JTextFieldPad.TP_STRING,4,0,false,true,txtDescMoeda,true);
		adicDescFK(txtDescMoeda,60,20,250,20,"SingMoeda","e descrição da moeda corrente.",JTextFieldPad.TP_STRING,50,0);
		adicDB(rgLibCred, 7,60,310,80, "PrefCred", "Verificação de crédito",JTextFieldPad.TP_STRING,true);
		adicDB(cbLibGeral, 7,150,310,20, "LCredGlobal", "",JTextFieldPad.TP_STRING,true);
        JLabel lbLinha = new JLabel();
        lbLinha.setBorder(new EtchedBorder());
        adic(lbLinha,5,180,310,2);
        adicDB(cbJurosPosCalc, 5,185,310,20, "JurosPosCalc", "",JTextFieldPad.TP_STRING,true);
        adicCampo(txtCodTabJuros,7,205,50,20,"CodTbj","Codigo",JTextFieldPad.TP_INTEGER,8,0,false,true,txtDescTabJuros,false);
        adicDescFK(txtDescTabJuros,60,205,250,20,"DescTbj","e descrição da tabela de juros.",JTextFieldPad.TP_STRING,50,0);

//SVV
		
		setPainel(pinSVV);
		adicTab("SVV", pinSVV);

        adicCampo(txtCodFor,7,25,80,20,"CodFor","Codigo",JTextFieldPad.TP_INTEGER,8,0,false,true,txtDescFor,false);
        adicDescFK(txtDescFor,90,25,220,20,"DescFor","e razão do fornecedor.",JTextFieldPad.TP_STRING,50,0);
		adicCampo(txtCodMarca,7,65,80,20,"CodMarca","Codigo",JTextFieldPad.TP_STRING,6,0,false,true,txtDescMarca,false);
		adicDescFK(txtDescMarca,60,65,220,20,"DescMarca","e descrição da marca.",JTextFieldPad.TP_STRING,50,0);
		adicCampo(txtCodGrup,7,105,80,20,"CodGrup","Codigo",JTextFieldPad.TP_STRING,14,0,false,true,txtDescGrup,false);
		adicDescFK(txtDescGrup,90,105,220,20,"DescGrup","e descrição do grupo.",JTextFieldPad.TP_STRING,50,0);
		
//Devolução
        
        setPainel(pinDev);
        adicTab("Devolução", pinDev);

        adicCampo(txtCodTipoFor,7,25,50,20,"CodTipoFor","Codigo",JTextFieldPad.TP_INTEGER,8,0,false,true,txtDescTipoFor,false);
        adicDescFK(txtDescTipoFor,60,25,250,20,"DescTipoFor","e descrição do tipo de fornecedor",JTextFieldPad.TP_STRING,50,0);
        adicCampo(txtCodTipoMov5,7,65,50,20,"CodTipoMov5","Codigo",JTextFieldPad.TP_INTEGER,8,0,false,true,txtDescTipoMov,false);
        adicDescFK(txtDescTipoMov5,60,65,250,20,"DescTipoMov","e descrição do tipo de movimento",JTextFieldPad.TP_STRING,50,0);

// Estoque
        setPainel(pinEstoq);
        adicTab("Estoque", pinEstoq);

        adicCampo(txtCodTipoMov6,7,25,80,20,"CodTipoMov6","Cód.tp.mov.",JTextFieldPad.TP_INTEGER,8,0,false,true,txtDescTipoMov,false);
        adicDescFK(txtDescTipoMov6,90,25,250,20,"DescTipoMov","Descrição do tp. mov. para inventário",JTextFieldPad.TP_STRING,50,0);
        
        nav.setAtivo(0,false);
		lcCampos.setPodeExc(false);
		lcCampos.addPostListener(this);

		setListaCampos(false, "PREFERE1", "SG");
		
		txtCodTipoMov2.setNomeCampo("CodTipoMov");  //Acerto o nome para que o ListaCampos naum confunda com a FK.
		txtCodTipoMov3.setNomeCampo("CodTipoMov");  //Acerto o nome para que o ListaCampos naum confunda com a FK.
		txtCodTipoMov4.setNomeCampo("CodTipoMov");  //Acerto o nome para que o ListaCampos naum confunda com a FK.
        txtCodTipoMov5.setNomeCampo("CodTipoMov");  //Acerto o nome para que o ListaCampos naum confunda com a FK.
        txtCodTipoMov6.setNomeCampo("CodTipoMov");  //Acerto o nome para que o ListaCampos naum confunda com a FK.
        
        cbJurosPosCalc.addCheckBoxListener(this);
		
	}
	public void beforePost(PostEvent pevt) {
	    if (txtCasasDec.getVlrInteger().intValue()>3) {
	        Funcoes.mensagemErro(this,"Número de casas decimais acima do permitido!");
	        txtCasasDec.requestFocus();
	        pevt.cancela();
	    }
	}
    public void valorAlterado(CheckBoxEvent cevt) {
        if (cevt.getCheckBox() == cbJurosPosCalc && cbJurosPosCalc.getVlrString().equals("S"))
            txtCodTabJuros.setAtivo(false);
        else
            txtCodTabJuros.setAtivo(true);
    }
	public void execShow(Connection cn) {
		lcMoeda.setConexao(cn);
		lcTabJuros.setConexao(cn);
		lcMarca.setConexao(cn);
		lcGrupo.setConexao(cn);
        lcTipoFor.setConexao(cn);
        lcFor.setConexao(cn);
		lcTipoMov.setConexao(cn);
		lcTipoMov2.setConexao(cn);
		lcTipoMov3.setConexao(cn);
		lcTipoMov4.setConexao(cn);
        lcTipoMov5.setConexao(cn);
        lcTipoMov6.setConexao(cn);
		lcTransp.setConexao(cn);
		lcPlanoPag.setConexao(cn);
		lcClasCli.setConexao(cn);
		lcTabPreco.setConexao(cn);
		super.execShow(cn);
		lcCampos.carregaDados();
	}
	
}
