/**
 * @version 23/11/2004 <BR>
 * @author Setpoint Informática Ltda./Robson Sanchez e Fernando Oliveira da
 *         Silva <BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.std <BR>
 * Classe:
 * @(#)FPrefereGeral.java <BR>
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
 * Tela de cadastro das preferências do sistema. Esse cadastro é utilizado para
 * parametrizar o sistema de acordo com as necessidades específicas da empresa.
 *  
 */

package org.freedom.modulos.std;

import java.sql.Connection;
import java.util.Vector;

import javax.swing.BorderFactory;

import org.freedom.acao.CarregaEvent;
import org.freedom.acao.CarregaListener;
import org.freedom.acao.CheckBoxEvent;
import org.freedom.acao.CheckBoxListener;
import org.freedom.acao.EditEvent;
import org.freedom.acao.EditListener;
import org.freedom.acao.InsertEvent;
import org.freedom.acao.InsertListener;
import org.freedom.acao.PostEvent;
import org.freedom.acao.PostListener;
import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JCheckBoxPad;
import org.freedom.componentes.JComboBoxPad;
import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JPanelPad;
import org.freedom.componentes.JPasswordFieldPad;
import org.freedom.componentes.JRadioGroup;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.Navegador;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.FTabDados;

public class FPrefereGeral extends FTabDados implements CheckBoxListener,
		PostListener, EditListener, InsertListener, CarregaListener {
	private static final long serialVersionUID = 1L;

	private JPanelPad pinVenda = new JPanelPad(690, 220);
	private JPanelPad pinGeral = new JPanelPad(330, 200);
	private JPanelPad pinPreco = new JPanelPad(330, 200);
	private JPanelPad pinOrc = new JPanelPad(330, 200);
	private JPanelPad pinFin = new JPanelPad();
	private JPanelPad pinSVV = new JPanelPad();
	private JPanelPad pinDev = new JPanelPad();
	private JPanelPad pinEstoq = new JPanelPad();
	private JPanelPad pinEmail = new JPanelPad();
	private JPanelPad pinSmtp = new JPanelPad();
	private JPanelPad pinProd = new JPanelPad();
	private JPanelPad pinOpcoesVenda = new JPanelPad();
	private JPanelPad pinOpcoesGeral = new JPanelPad();

	private JTextFieldPad txtCodMoeda = new JTextFieldPad(JTextFieldPad.TP_STRING, 4, 0);
	private JTextFieldFK txtDescMoeda = new JTextFieldFK(JTextFieldPad.TP_STRING, 50, 0);
	private JTextFieldPad txtCodTabJuros = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);
	private JTextFieldFK txtDescTabJuros = new JTextFieldFK(JTextFieldPad.TP_STRING, 50, 0);
	private JTextFieldPad txtCodMarca = new JTextFieldPad(JTextFieldPad.TP_STRING, 6, 0);
	private JTextFieldFK txtDescMarca = new JTextFieldFK(JTextFieldPad.TP_STRING, 50, 0);
	private JTextFieldPad txtCodGrup = new JTextFieldPad(JTextFieldPad.TP_STRING, 14, 0);
	private JTextFieldFK txtDescGrup = new JTextFieldFK(JTextFieldPad.TP_STRING, 50, 0);
	private JTextFieldPad txtCodFor = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);
	private JTextFieldFK txtDescFor = new JTextFieldFK(JTextFieldPad.TP_STRING,50, 0);
	private JTextFieldPad txtCodTipoFor = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);
	private JTextFieldFK txtDescTipoFor = new JTextFieldFK(JTextFieldPad.TP_STRING, 50, 0);
	private JTextFieldPad txtCodTipoMov = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);
	private JTextFieldPad txtCodTipoMov2 = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);
	private JTextFieldPad txtCodTipoMov3 = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);
	private JTextFieldPad txtCodTipoMov4 = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);
	private JTextFieldPad txtCodTipoMov5 = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);
	private JTextFieldPad txtCodTipoMov6 = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);
	private JTextFieldPad txtCodTipoMov7 = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);
	private JTextFieldPad txtCodTipoMov8 = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);
	private JTextFieldPad txtCodTransp = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);
	private JTextFieldPad txtCasasDec = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 1, 0);
	private JTextFieldPad txtCasasDecFin = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 1, 0);
	private JTextFieldPad txtPercPrecoCusto = new JTextFieldPad(JTextFieldPad.TP_NUMERIC, 6, 2);
	private JTextFieldPad txtAnoCC = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 4, 0);
	private JTextFieldPad txtDescClassOrc = new JTextFieldPad(JTextFieldPad.TP_STRING, 20, 0);
	private JTextFieldPad txtTitOrcTxt01 = new JTextFieldPad(JTextFieldPad.TP_STRING, 20, 0);
	private JTextFieldFK txtDescTipoMov = new JTextFieldFK(JTextFieldPad.TP_STRING, 40, 0);
	private JTextFieldFK txtDescTipoMov2 = new JTextFieldFK(JTextFieldPad.TP_STRING, 40, 0);
	private JTextFieldFK txtDescTipoMov3 = new JTextFieldFK(JTextFieldPad.TP_STRING, 40, 0);
	private JTextFieldFK txtDescTipoMov4 = new JTextFieldFK(JTextFieldPad.TP_STRING, 40, 0);
	private JTextFieldFK txtDescTipoMov5 = new JTextFieldFK(JTextFieldPad.TP_STRING, 50, 0);
	private JTextFieldFK txtDescTipoMov6 = new JTextFieldFK(JTextFieldPad.TP_STRING, 50, 0);
	private JTextFieldFK txtDescTipoMov7 = new JTextFieldFK(JTextFieldPad.TP_STRING, 50, 0);
	private JTextFieldFK txtDescTipoMov8 = new JTextFieldFK(JTextFieldPad.TP_STRING, 50, 0);	
	private JTextFieldFK txtDescTransp = new JTextFieldFK(JTextFieldPad.TP_STRING, 40, 0);
	private JTextFieldPad txtCodPlanoPag = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);
	private JTextFieldPad txtCodPlanoPag2 = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);
	private JTextFieldPad txtPrazo = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);
	private JTextFieldPad txtCodTab = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);
	private JTextFieldPad txtCodClasCli = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);
	private JTextFieldPad txtCodCli = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);
	private JTextFieldFK txtDescPlanoPag = new JTextFieldFK(JTextFieldPad.TP_STRING, 40, 0);
	private JTextFieldFK txtDescPlanoPag2 = new JTextFieldFK(JTextFieldPad.TP_STRING, 40, 0);
	private JTextFieldFK txtDescTab = new JTextFieldFK(JTextFieldPad.TP_STRING,40, 0);
	private JTextFieldFK txtDescClasCli = new JTextFieldFK(JTextFieldPad.TP_STRING, 40, 0);
	private JTextFieldFK txtDescCli = new JTextFieldFK(JTextFieldPad.TP_STRING,50, 0);
	private JTextFieldPad txtSmtpMail = new JTextFieldPad(JTextFieldPad.TP_STRING, 40 , 0);
	private JTextFieldPad txtUserMail = new JTextFieldPad(JTextFieldPad.TP_STRING, 40 , 0);
	private JTextFieldPad txtDiasVencOrc = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8 , 0);
	private JPasswordFieldPad txpPassMail = new JPasswordFieldPad(16);
	private final String opcoes = "    Opções";
	private JLabelPad lbVendOpcoes = new JLabelPad(opcoes);
	private JLabelPad lbGeralOpcoes = new JLabelPad(opcoes);
	private JLabelPad lbOrcOpcoes = new JLabelPad(opcoes);
	private JLabelPad lbEstOpcoes = new JLabelPad(opcoes);
	private JLabelPad lbProdOpcoes = new JLabelPad(opcoes);
	private JLabelPad lbFinOpcoes = new JLabelPad(opcoes);
	private JLabelPad lbPrçOpcoes = new JLabelPad(opcoes);
	private JLabelPad lbOrcCont = new JLabelPad();
	private JLabelPad lbEstCont = new JLabelPad();
	private JLabelPad lbProdCont = new JLabelPad();
	private JLabelPad lbFinCont = new JLabelPad();
	private JLabelPad lbPrçCont = new JLabelPad();
	private JComboBoxPad cbTamDescProd = null;
	private JRadioGroup rgTipoValidOrc = null;
	private JRadioGroup rgTipoPrecoCusto = null;
	private JRadioGroup rgSetorVenda = null;
	private JRadioGroup rgOrdNota = null;
	private JCheckBoxPad cbUsaRefProd = null;
	private JCheckBoxPad cbUsaPedSeq = null;
	private JCheckBoxPad cbUsaOrcSeq = null;
	private JCheckBoxPad cbUsaDescEspelho = null;
	private JCheckBoxPad cbUsaClasComis = null;
	private JCheckBoxPad cbTabFreteVd = null;
	private JCheckBoxPad cbVendaMatPrim = null;
	private JCheckBoxPad cbTabAdicVd = null;
	private JCheckBoxPad cbTravaTMNFVD = null;
	private JCheckBoxPad cbLibGeral = null;
	private JCheckBoxPad cbJurosPosCalc = null;
	private JCheckBoxPad cbRgCliObrig = null;
	private JCheckBoxPad cbCliMesmoCnpj = null;
	private JCheckBoxPad cbCnpjCliObrig = null;
	private JCheckBoxPad cbCnpjForObrig = null;
	private JCheckBoxPad cbInscEstForObrig = null;
	private JCheckBoxPad cbEstLotNeg = null;
	private JCheckBoxPad cbEstNeg = null;
	private JCheckBoxPad cbEstNegGrupo = null;
	private JCheckBoxPad cbNatVenda = null;
	private JCheckBoxPad cbIPIVenda = null;
	private JCheckBoxPad cbComisPDupl = null;
	private JCheckBoxPad cbCustosSICMS = null;
	private JCheckBoxPad cbBloqVenda = null;
	private JCheckBoxPad cbBloqCompra = null;
	private JCheckBoxPad cbPepsProd = null;
	private JCheckBoxPad cbBuscaProdSimilar = null;
	private JCheckBoxPad cbMultiAlmox = null;
	private JCheckBoxPad cbPrazoEnt = null;
	private JCheckBoxPad cbDiasPEData = null;
	private JCheckBoxPad cbDescCompl = null;
	private JCheckBoxPad cbObsCliVend = null;
	private JCheckBoxPad cbContEstoq = null;
	private JCheckBoxPad cbReCalcVenda = null;
	private JCheckBoxPad cbReCalcOrc = null;
	private JCheckBoxPad cbAprovOrc = null;
	private JCheckBoxPad cbLayoutPed = null;
	private JCheckBoxPad cbVerifAltParVenda = null;
	private ListaCampos lcMoeda = new ListaCampos(this, "MO");
	private ListaCampos lcTabJuros = new ListaCampos(this, "TJ");
	private ListaCampos lcMarca = new ListaCampos(this, "MC");
	private ListaCampos lcGrupo = new ListaCampos(this, "GP");
	private ListaCampos lcTipoFor = new ListaCampos(this, "TF");
	private ListaCampos lcFor = new ListaCampos(this, "FR");
	private ListaCampos lcTipoMov = new ListaCampos(this, "TM");
	private ListaCampos lcTipoMov2 = new ListaCampos(this, "T2");
	private ListaCampos lcTipoMov3 = new ListaCampos(this, "T3");
	private ListaCampos lcTipoMov4 = new ListaCampos(this, "T4");
	private ListaCampos lcTipoMov5 = new ListaCampos(this, "T5");
	private ListaCampos lcTipoMov6 = new ListaCampos(this, "T6");
	private ListaCampos lcTipoMov7 = new ListaCampos(this, "TM");
	private ListaCampos lcTipoMov8 = new ListaCampos(this, "T8");
	private ListaCampos lcTransp = new ListaCampos(this, "TN");
	private ListaCampos lcPlanoPag = new ListaCampos(this, "PG");
	private ListaCampos lcPlanoPag2 = new ListaCampos(this, "PP");
	private ListaCampos lcTabPreco = new ListaCampos(this, "TB");
	private ListaCampos lcClasCli = new ListaCampos(this, "CE");
	private ListaCampos lcCli = new ListaCampos(this, "CL");
	private ListaCampos lcPDV = new ListaCampos(this, "");
	private ListaCampos lcPrefere3 = new ListaCampos(this, "P3");


	public FPrefereGeral() {
		super();
		setTitulo("Preferências Gerais");
		setAtribos(40, 40, 760, 460);

		lcCampos.setMensInserir(false);
		lcPrefere3.setMensInserir(false);
		lcPDV.setMensInserir(false);
		
		lcMoeda.add(new GuardaCampo(txtCodMoeda, "CodMoeda", "Cód.moeda",ListaCampos.DB_PK, true));
		lcMoeda.add(new GuardaCampo(txtDescMoeda, "SingMoeda","Descrição da moeda", ListaCampos.DB_SI, false));
		lcMoeda.montaSql(false, "MOEDA", "FN");
		lcMoeda.setQueryCommit(false);
		lcMoeda.setReadOnly(true);
		txtCodMoeda.setTabelaExterna(lcMoeda);

		lcTabJuros.add(new GuardaCampo(txtCodTabJuros, "CodTbj", "Cód.tb.jur.",ListaCampos.DB_PK, false));
		lcTabJuros.add(new GuardaCampo(txtDescTabJuros, "DescTbJ","Descrição da tabela de juros", ListaCampos.DB_SI, false));
		lcTabJuros.montaSql(false, "TBJUROS", "FN");
		lcTabJuros.setQueryCommit(false);
		lcTabJuros.setReadOnly(true);
		txtCodTabJuros.setTabelaExterna(lcTabJuros);

		lcMarca.add(new GuardaCampo(txtCodMarca, "CodMarca", "Cód.marca",ListaCampos.DB_PK, false));
		lcMarca.add(new GuardaCampo(txtDescMarca, "DescMarca","Descrição da marca", ListaCampos.DB_SI, false));
		lcMarca.montaSql(false, "MARCA", "EQ");
		lcMarca.setQueryCommit(false);
		lcMarca.setReadOnly(true);
		txtCodMarca.setTabelaExterna(lcMarca);

		lcGrupo.add(new GuardaCampo(txtCodGrup, "CodGrup", "Cód.grupo",ListaCampos.DB_PK, false));
		lcGrupo.add(new GuardaCampo(txtDescGrup, "DescGrup","Descrição do grupo", ListaCampos.DB_SI, false));
		lcGrupo.montaSql(false, "GRUPO", "EQ");
		lcGrupo.setQueryCommit(false);
		lcGrupo.setReadOnly(true);
		txtCodGrup.setTabelaExterna(lcGrupo);

		lcFor.add(new GuardaCampo(txtCodFor, "CodFor", "Cód.for.",ListaCampos.DB_PK, false));
		lcFor.add(new GuardaCampo(txtDescFor, "RazFor","Razão social do fornecedor", ListaCampos.DB_SI, false));
		lcFor.montaSql(false, "FORNECED", "CP");
		lcFor.setQueryCommit(false);
		lcFor.setReadOnly(true);
		txtCodFor.setTabelaExterna(lcFor);

		lcTipoFor.add(new GuardaCampo(txtCodTipoFor, "CodTipoFor","Cód.tp.for.", ListaCampos.DB_PK, false));
		lcTipoFor.add(new GuardaCampo(txtDescTipoFor, "DescTipoFor","Descrição do tipo de fornecedor", ListaCampos.DB_SI, false));
		lcTipoFor.montaSql(false, "TIPOFOR", "CP");
		lcTipoFor.setQueryCommit(false);
		lcTipoFor.setReadOnly(true);
		txtCodTipoFor.setTabelaExterna(lcTipoFor);

		lcTipoMov.add(new GuardaCampo(txtCodTipoMov, "CodTipoMov","Cód.tp.mov.", ListaCampos.DB_PK, false));
		lcTipoMov.add(new GuardaCampo(txtDescTipoMov, "DescTipoMov","Descrição do tipo de movimento", ListaCampos.DB_SI, false));
		lcTipoMov.montaSql(false, "TIPOMOV", "EQ");
		lcTipoMov.setQueryCommit(false);
		lcTipoMov.setReadOnly(true);
		txtCodTipoMov.setTabelaExterna(lcTipoMov);

		lcTipoMov2.add(new GuardaCampo(txtCodTipoMov2, "CodTipoMov","Cód.tp.mov.", ListaCampos.DB_PK, false));
		lcTipoMov2.add(new GuardaCampo(txtDescTipoMov2, "DescTipoMov","Descrição do tipo de movimento", ListaCampos.DB_SI, false));
		lcTipoMov2.montaSql(false, "TIPOMOV", "EQ");
		lcTipoMov2.setQueryCommit(false);
		lcTipoMov2.setReadOnly(true);
		txtCodTipoMov2.setTabelaExterna(lcTipoMov2);

		lcTipoMov3.add(new GuardaCampo(txtCodTipoMov3, "CodTipoMov","Cód.tp.mov.", ListaCampos.DB_PK, false));
		lcTipoMov3.add(new GuardaCampo(txtDescTipoMov3, "DescTipoMov","Descrição do tipo de movimento", ListaCampos.DB_SI, false));
		lcTipoMov3.montaSql(false, "TIPOMOV", "EQ");
		lcTipoMov3.setQueryCommit(false);
		lcTipoMov3.setReadOnly(true);
		txtCodTipoMov3.setTabelaExterna(lcTipoMov3);

		lcTipoMov4.add(new GuardaCampo(txtCodTipoMov4, "CodTipoMov","Cód.tp.mov.", ListaCampos.DB_PK, false));
		lcTipoMov4.add(new GuardaCampo(txtDescTipoMov4, "DescTipoMov","Descrição do tipo de movimento", ListaCampos.DB_SI, false));
		lcTipoMov4.montaSql(false, "TIPOMOV", "EQ");
		lcTipoMov4.setQueryCommit(false);
		lcTipoMov4.setReadOnly(true);
		txtCodTipoMov4.setTabelaExterna(lcTipoMov4);

		lcTipoMov5.add(new GuardaCampo(txtCodTipoMov5, "CodTipoMov","Cód.tp.mov.", ListaCampos.DB_PK, false));
		lcTipoMov5.add(new GuardaCampo(txtDescTipoMov5, "DescTipoMov","Descrição do tipo de movimento", ListaCampos.DB_SI, false));
		lcTipoMov5.montaSql(false, "TIPOMOV", "EQ");
		lcTipoMov5.setQueryCommit(false);
		lcTipoMov5.setReadOnly(true);
		txtCodTipoMov5.setTabelaExterna(lcTipoMov5);

		lcTipoMov6.add(new GuardaCampo(txtCodTipoMov6, "CodTipoMov","Cód.tp.mov.", ListaCampos.DB_PK, false));
		lcTipoMov6.add(new GuardaCampo(txtDescTipoMov6, "DescTipoMov","Descrição do tipo de movimento", ListaCampos.DB_SI, false));
		lcTipoMov6.montaSql(false, "TIPOMOV", "EQ");
		lcTipoMov6.setWhereAdic(" ESTIPOMOV='I' ");
		lcTipoMov6.setQueryCommit(false);
		lcTipoMov6.setReadOnly(true);
		txtCodTipoMov6.setTabelaExterna(lcTipoMov6);

		lcTipoMov7.add(new GuardaCampo(txtCodTipoMov7, "CodTipoMov","Cód.tp.mov.", ListaCampos.DB_PK, false));
		lcTipoMov7.add(new GuardaCampo(txtDescTipoMov7, "DescTipoMov","Descrição do tipo de movimento", ListaCampos.DB_SI, false));
		lcTipoMov7.montaSql(false, "TIPOMOV", "EQ");
		lcTipoMov7.setWhereAdic(" ESTIPOMOV='I' ");
		lcTipoMov7.setQueryCommit(false);
		lcTipoMov7.setReadOnly(true);
		txtCodTipoMov7.setTabelaExterna(lcTipoMov7);

		lcTipoMov8.add(new GuardaCampo(txtCodTipoMov8, "CodTipoMov","Cód.tp.mov.", ListaCampos.DB_PK, false));
		lcTipoMov8.add(new GuardaCampo(txtDescTipoMov8, "DescTipoMov","Descrição do tipo de movimento", ListaCampos.DB_SI, false));
		lcTipoMov8.montaSql(false, "TIPOMOV", "EQ");
		lcTipoMov8.setWhereAdic(" TIPOMOV='RM' ");
		lcTipoMov8.setQueryCommit(false);
		lcTipoMov8.setReadOnly(true);
		txtCodTipoMov8.setTabelaExterna(lcTipoMov8);
		txtCodTipoMov8.setFK(true);
		
		txtCodTransp.setNomeCampo("CodTran");
		lcTransp.add(new GuardaCampo(txtCodTransp, "CodTran", "Cód.tran.",ListaCampos.DB_PK, false));
		lcTransp.add(new GuardaCampo(txtDescTransp, "RazTran","Nome do transportador", ListaCampos.DB_SI, false));
		txtDescTransp.setListaCampos(lcTransp);
		txtCodTransp.setTabelaExterna(lcTransp);
		txtCodTransp.setFK(true);
		lcTransp.montaSql(false, "TRANSP", "VD");
		lcTransp.setQueryCommit(false);
		lcTransp.setReadOnly(true);

		txtCodPlanoPag.setNomeCampo("CodPlanoPag");
		lcPlanoPag.add(new GuardaCampo(txtCodPlanoPag, "CodPlanoPag","Cód.p.pag", ListaCampos.DB_PK, false));
		lcPlanoPag.add(new GuardaCampo(txtDescPlanoPag, "DescPlanoPag","Descrição do plano de pagamento", ListaCampos.DB_SI, false));
		lcPlanoPag.montaSql(false, "PLANOPAG", "FN");
		lcPlanoPag.setReadOnly(true);
		txtCodPlanoPag.setTabelaExterna(lcPlanoPag);
		txtCodPlanoPag.setFK(true);
		txtDescPlanoPag.setListaCampos(lcPlanoPag);

		txtCodPlanoPag2.setNomeCampo("CodPlanoPag");
		lcPlanoPag2.add(new GuardaCampo(txtCodPlanoPag2, "CodPlanoPag","Cód.p.pag.", ListaCampos.DB_PK, false));
		lcPlanoPag2.add(new GuardaCampo(txtDescPlanoPag2, "DescPlanoPag","Descrição do plano de pagamento", ListaCampos.DB_SI, false));
		lcPlanoPag2.montaSql(false, "PLANOPAG", "FN");
		txtCodPlanoPag2.setTabelaExterna(lcPlanoPag2);
		txtCodPlanoPag2.setFK(true);
		lcPlanoPag2.setReadOnly(true);
		txtDescPlanoPag2.setListaCampos(lcPlanoPag2);

		txtCodTab.setNomeCampo("CodTab");
		lcTabPreco.add(new GuardaCampo(txtCodTab, "CodTab", "Cód.tab.pç.",ListaCampos.DB_PK, false));
		lcTabPreco.add(new GuardaCampo(txtDescTab, "DescTab","Descrição da tabela de preço", ListaCampos.DB_SI, false));
		lcTabPreco.montaSql(false, "TABPRECO", "VD");
		lcTabPreco.setReadOnly(true);
		txtCodTab.setTabelaExterna(lcTabPreco);
		txtCodTab.setFK(true);
		txtDescTab.setListaCampos(lcTabPreco);

		txtCodCli.setNomeCampo("CodCli");
		lcCli.add(new GuardaCampo(txtCodCli, "CodCli", "Cód.cli.",ListaCampos.DB_PK, false));
		lcCli.add(new GuardaCampo(txtDescCli, "NomeCli", "Nome do cliente",ListaCampos.DB_SI, false));
		lcCli.montaSql(false, "CLIENTE", "VD");
		lcCli.setReadOnly(true);
		txtCodCli.setTabelaExterna(lcCli);
		txtCodCli.setFK(true);
		txtDescCli.setListaCampos(lcCli);

		txtCodClasCli.setNomeCampo("CodClasCli");
		lcClasCli.add(new GuardaCampo(txtCodClasCli, "CodClasCli","Cód.c.cli.", ListaCampos.DB_PK, false));
		lcClasCli.add(new GuardaCampo(txtDescClasCli, "DescClasCli","Descrição da classificação do cliente", ListaCampos.DB_SI,false));
		lcClasCli.montaSql(false, "CLASCLI", "VD");
		lcClasCli.setReadOnly(true);
		txtCodClasCli.setTabelaExterna(lcClasCli);
		txtCodClasCli.setFK(true);
		txtDescClasCli.setListaCampos(lcClasCli);

		cbUsaRefProd = new JCheckBoxPad("Usar referência?", "S", "N");
		cbUsaRefProd.setVlrString("N");
		cbUsaPedSeq = new JCheckBoxPad("Pedido sequencial?", "S", "N");
		cbUsaPedSeq.setVlrString("S");
		cbUsaOrcSeq = new JCheckBoxPad("Orçamento sequencial?", "S", "N");
		cbUsaOrcSeq.setVlrString("S");
		cbUsaDescEspelho = new JCheckBoxPad("Desconto no espelho?", "S", "N");
		cbUsaDescEspelho.setVlrString("N");
		cbUsaClasComis = new JCheckBoxPad("Class. comis. na venda?", "S", "N");
		cbUsaClasComis.setVlrString("N");
		cbEstLotNeg = new JCheckBoxPad("Permit. sld. lote neg.?", "S", "N");
		cbEstLotNeg.setVlrString("N");
		cbEstNeg = new JCheckBoxPad("Permit. saldo negativo?", "S", "N");
		cbEstNeg.setVlrString("N");				
		cbEstNegGrupo = new JCheckBoxPad("Controle de saldo negativo por grupo?", "S", "N");
		cbEstNegGrupo.setVlrString("N");
		cbBloqVenda = new JCheckBoxPad("Bloquear venda após impressão da NF?","S", "N");
		cbBloqVenda.setVlrString("N");
		cbBloqCompra = new JCheckBoxPad("Bloquear compra após finalizar?","S", "N");
		cbBloqCompra.setVlrString("N");
		cbNatVenda = new JCheckBoxPad("Habil. campo CFOP ?", "S", "N");
		cbNatVenda.setVlrString("S");		
		cbIPIVenda = new JCheckBoxPad("Habil. campo IPI ?", "S", "N");
		cbIPIVenda.setVlrString("S");
		cbComisPDupl = new JCheckBoxPad("Calcula comissão com base nas duplicatas?", "S", "N");
		cbComisPDupl.setVlrString("S");		
		cbObsCliVend = new JCheckBoxPad("Mostrar observações do cliente na venda e orçamento?","S","N");
		cbObsCliVend.setVlrString("N");
		cbTabFreteVd = new JCheckBoxPad("Aba frete na venda?", "S", "N");
		cbTabFreteVd.setVlrString("S");
		cbTabAdicVd = new JCheckBoxPad("Aba adic. na venda?", "S", "N");
		cbTabAdicVd.setVlrString("N");
		cbTravaTMNFVD = new JCheckBoxPad("Travar tipo de Mov. NF na inserção da venda?", "S", "N");
		cbTravaTMNFVD.setVlrString("S");
		cbCustosSICMS = new JCheckBoxPad("Preço de custo sem ICMS?", "S", "N");
		cbCustosSICMS.setVlrString("S");
		cbVendaMatPrim = new JCheckBoxPad("Permitir venda de matéria prima?","S", "N");
		cbVendaMatPrim.setVlrString("N");
		cbPrazoEnt = new JCheckBoxPad("Prazo de entrega na venda?", "S", "N");
		cbPrazoEnt.setVlrString("S");
		cbDiasPEData = new JCheckBoxPad("Data de entrega no pedido?", "S", "N");
		cbDiasPEData.setVlrString("N");
		cbDescCompl = new JCheckBoxPad("Usar descrição completa do produto para Orçamento e Pedido?", "S", "N");
		cbDescCompl.setVlrString("N");
		cbReCalcVenda = new JCheckBoxPad("Recalcular preço na venda?", "S", "N");
		cbReCalcVenda.setVlrString("N");
		cbReCalcOrc = new JCheckBoxPad("Recalcular preço no orçamento?", "S", "N");
		cbReCalcOrc.setVlrString("N");
		cbAprovOrc = new JCheckBoxPad("Permitir aprovação do orçamento na tela de cadastro?", "S", "N");
		cbAprovOrc.setVlrString("N");
		cbRgCliObrig = new JCheckBoxPad("RG. do cliente obrigatório?", "S", "N");
		cbRgCliObrig.setVlrString("S");
		cbCliMesmoCnpj = new JCheckBoxPad("Permitir clientes com mesmo CNPJ ?","S", "N");
		cbCliMesmoCnpj.setVlrString("N");
		cbCnpjCliObrig = new JCheckBoxPad("CNPJ obrigatório para o cadastro de clientes ?", "S", "N");
		cbCnpjCliObrig.setVlrString("S");
		cbCnpjForObrig = new JCheckBoxPad("CNPJ obrigatório para o cadastro de fornecedores ?", "S", "N");
		cbCnpjForObrig.setVlrString("S");
		cbInscEstForObrig = new JCheckBoxPad("Inscrição estadual obrigatória para o cadastro de fornecedores ?","S", "N");
		cbInscEstForObrig.setVlrString("S");
		cbLayoutPed = new JCheckBoxPad("Usar layout personalizado para pedido?","S", "N");
		cbLayoutPed.setVlrString("N");
		cbMultiAlmox = new JCheckBoxPad("Multi almoxarifados?","S","N");
		cbMultiAlmox.setVlrString("N");
		cbContEstoq = new JCheckBoxPad("Controla estoque?","S","N");
		cbContEstoq.setVlrString("N");
		cbPepsProd = new JCheckBoxPad("Exibe custo PEPS no cadastro de produtos?", "S", "N");
		cbPepsProd.setVlrString("N");
		cbBuscaProdSimilar = new JCheckBoxPad("Busca automática de produtos similares?", "S", "N");
		cbBuscaProdSimilar.setVlrString("N");
		cbLibGeral = new JCheckBoxPad("Liberação de credito globalizada?", "S","N");
		cbLibGeral.setVlrString("S");
		cbJurosPosCalc = new JCheckBoxPad("Juros pós-calculado?", "S", "N");
		cbJurosPosCalc.setVlrString("N");
		cbVerifAltParVenda = new JCheckBoxPad("Verificar usuario para alterar parcelas?", "S", "N");
		cbVerifAltParVenda.setVlrString("N");

		Vector vLabs = new Vector();
		Vector vVals = new Vector();
		vLabs.addElement("Custo MPM");
		vLabs.addElement("Custo PEPS");
		vVals.addElement("M");
		vVals.addElement("P");
		rgTipoPrecoCusto = new JRadioGroup(1, 2, vLabs, vVals);
		rgTipoPrecoCusto.setVlrString("M");		

		Vector vLabsTpValidOrc1 = new Vector();
		Vector vValsTpValidOrc1 = new Vector();
		vLabsTpValidOrc1.addElement("Data");
		vLabsTpValidOrc1.addElement("Nro. de dias");
		vValsTpValidOrc1.addElement("D");
		vValsTpValidOrc1.addElement("N");
		rgTipoValidOrc = new JRadioGroup(1, 2, vLabsTpValidOrc1,vValsTpValidOrc1);
		rgTipoValidOrc.setVlrString("D");
		
		Vector vLabs2 = new Vector();
		Vector vVals2 = new Vector();
		vLabs2.addElement("Cliente/Setor");
		vLabs2.addElement("Comissionado/Setor");
		vLabs2.addElement("Ambos");
		vVals2.addElement("C");
		vVals2.addElement("V");
		vVals2.addElement("A");
		rgSetorVenda = new JRadioGroup(3, 1, vLabs2, vVals2);
		rgSetorVenda.setVlrString("C");
		
		Vector vLabs1 = new Vector();
		Vector vVals1 = new Vector();
		vLabs1.addElement("Por Codigo");
		vLabs1.addElement("Por Descriçao");
		vLabs1.addElement("Por Marca");
		vVals1.addElement("C");
		vVals1.addElement("D");
		vVals1.addElement("M");
		rgOrdNota = new JRadioGroup(3, 1, vLabs1, vVals1);
		rgOrdNota.setVlrString("C");

		Vector vValsTipo = new Vector();
		Vector vLabsTipo = new Vector();
		vLabsTipo.addElement("<--Selecione-->");
		vLabsTipo.addElement("50 caracteres");
		vLabsTipo.addElement("100 caracteres");		
		vValsTipo.addElement(new Integer(0));
		vValsTipo.addElement(new Integer(50));
		vValsTipo.addElement(new Integer(100));		
		cbTamDescProd = new JComboBoxPad(vLabsTipo, vValsTipo, JComboBoxPad.TP_INTEGER, 4, 0);
		
		Vector vLabs3 = new Vector();
		Vector vVals3 = new Vector();
		vLabs3.addElement("Não vericar");
		vLabs3.addElement("Aguardar liberação");
		vLabs3.addElement("Liberar crédito pré-aprovado");
		vVals3.addElement("N");
		vVals3.addElement("A");
		vVals3.addElement("L");
		JRadioGroup rgLibCred = new JRadioGroup(3, 1, vLabs3, vVals3);
		rgLibCred.setVlrString("N");
		
		// Geral
		
		setPainel(pinGeral);
		adicTab("Geral", pinGeral);
		adicCampo(txtAnoCC, 7, 25, 100, 20, "AnoCentroCusto", "Ano Base C.C.",ListaCampos.DB_SI, true);
		adic(new JLabelPad("Casas Decimais"),7,60,150,20);
		adicCampo(txtCasasDecFin, 7, 100, 100, 20, "CasasDecFin", "p/ Financeiro",ListaCampos.DB_SI, true);
		adicCampo(txtCasasDec, 7, 140, 100, 20, "CasasDec", "Demais",ListaCampos.DB_SI, true);
		lbGeralOpcoes.setOpaque(true);
		adic(lbGeralOpcoes,170,5,90,20);
		adic(pinOpcoesGeral,160,15,560,145);
		setPainel(pinOpcoesGeral);		
		adicDB(cbRgCliObrig,7, 20, 180, 20, "RgCliObrig", "", true);
		adicDB(cbCliMesmoCnpj, 7, 40, 250, 20, "CliMesmoCnpj", "", true);
		adicDB(cbCnpjCliObrig, 7, 60, 300, 20, "CnpjObrigCli", "", true);
		adicDB(cbCnpjForObrig, 7, 80, 400, 20, "CnpjForObrig", "", true);
		adicDB(cbInscEstForObrig, 7, 100, 400, 20, "InscEstForObrig", "", true);
		
		// Venda
		
		setPainel(pinVenda);
		adicTab("Venda", pinVenda);
		
		adicDB(rgSetorVenda, 7, 25, 160, 80, "SetorVenda","Distrib. dos setores", true);
		adicDB(rgOrdNota, 177, 25, 160, 80, "OrdNota", " Ordem de Emissão",	true);
		
		adicCampo(txtCodTipoMov3, 7, 130, 75, 20, "CodTipoMov3", "Cód.tp.mov",ListaCampos.DB_FK, txtDescTipoMov3, false);
		adicDescFK(txtDescTipoMov3, 85, 130, 250, 20, "DescTipoMov","Tipo de movimento para pedido.");
		adicCampo(txtCodTipoMov, 7, 180, 75, 20, "CodTipoMov", "Cód.tp.mov.",ListaCampos.DB_FK, txtDescTipoMov, false);
		adicDescFK(txtDescTipoMov, 85, 180, 250, 20, "DescTipoMov","Tipo de movimento para NF.");
		adicCampo(txtCodTipoMov4, 7, 230, 75, 20, "CodTipoMov4","Cód.tp.mov.", ListaCampos.DB_FK, txtDescTipoMov4, false);
		adicDescFK(txtDescTipoMov4, 85, 230, 250, 20, "DescTipoMov","Tipo de movimento para pedido (serviço).");
		adicCampo(txtCodTransp,7, 280, 75, 20, "CodTran", "Cód.tran.",ListaCampos.DB_FK, txtDescTransp, false);
		adicDescFK(txtDescTransp, 85, 280, 250, 20, "RazTran","Razão social da transp.padrao para venda");
		
		lbVendOpcoes.setOpaque(true);
		adic(lbVendOpcoes,357,5,70,20);
		adic(pinOpcoesVenda,348,15,380,340);
		setPainel(pinOpcoesVenda);

		adicDB(cbUsaRefProd, 5, 10, 160, 20, "UsaRefProd", "", true);
		adicDB(cbUsaPedSeq, 5, 30, 160, 20, "UsaPedSeq", "", true);
		adicDB(cbEstNeg, 5, 50, 160, 20, "EstNeg", "", true);	
		adicDB(cbEstLotNeg, 5, 70, 160, 20, "EstLotNeg", "", true);
		adicDB(cbPrazoEnt, 5, 90, 200, 20, "UsaTabPE", "", true);
		adicDB(cbDiasPEData, 5, 110, 200, 20, "DIASPEDT", "", true);
		adicDB(cbReCalcVenda, 5, 130, 250, 20, "ReCalcPCVenda", "", true);	
		adicDB(cbVendaMatPrim, 5, 150, 300, 20, "VendaMatPrim", "", true);
		adicDB(cbTravaTMNFVD, 5, 170, 300, 20, "TravaTMNFVD", "", true);
		adicDB(cbBloqVenda, 5, 190, 300, 20, "BloqVenda", "", true);
		adicDB(cbComisPDupl, 5, 210, 300, 20, "ComisPDupl", "", true);
		adicDB(cbEstNegGrupo, 5, 230, 250, 20, "EstNegGrup", "", true);
		adicDB(cbLayoutPed, 5, 250, 300, 20, "UsaLayoutPed", "", true);
		adicDB(cbObsCliVend, 5, 270,350,20, "ObsCliVend", "", true);
		adicDB(cbVerifAltParVenda, 5, 290, 350, 20, "VerifAltParcVenda", "", true);
		
		adicDB(cbUsaClasComis, 205, 10, 160, 20, "UsaClasComis", "", true);
		adicDB(cbTabFreteVd, 205, 30, 160, 20, "TabFreteVd", "", true);
		adicDB(cbTabAdicVd, 205, 50, 160, 20, "TabAdicVd", "", true);
		adicDB(cbUsaDescEspelho, 205, 70, 160, 20, "UsaLiqRel", "", true);	
		adicDB(cbIPIVenda, 205, 90, 160, 20, "IPIVenda", "", true);
		adicDB(cbNatVenda, 205, 110, 160, 20, "NatVenda", "", true);

		// Preço

		setPainel(pinPreco);
		adicTab("Preços", pinPreco);
		
		lbPrçCont.setBorder( BorderFactory.createEtchedBorder(1));
		lbPrçOpcoes.setOpaque(true);
		
		adicCampo(txtCodTab, 10, 25, 77, 20, "CodTab", "Cód.tab.pc.",ListaCampos.DB_FK, txtDescTab, false);
		adicDescFK(txtDescTab, 90, 25, 260, 20, "DescTab","Descrição da tabela de preços");
		adicCampo(txtCodPlanoPag, 10, 65, 77, 20, "CodPlanoPag", "Cód.p.pag.",ListaCampos.DB_FK, txtDescPlanoPag, false);
		adicDescFK(txtDescPlanoPag, 90, 65, 260, 20, "DescPlanoPag","Descrição do plano de pagamento");
		adicCampo(txtCodClasCli, 10, 105, 77, 20, "CodClasCli", "Cód.c.cli",ListaCampos.DB_FK, txtDescClasCli, false);
		adicDescFK(txtDescClasCli, 90, 105, 260, 20, "DescClasCli","Descrição da classificação dos clientes");
		
		adic(lbPrçOpcoes, 370, 5, 70, 20);
		adic(lbPrçCont, 360, 15, 360, 140);
		adicDB(rgTipoPrecoCusto, 370, 45, 280, 30, "TipoPrecoCusto","Controle do preco sobre o custo:", false);
		adicCampo(txtPercPrecoCusto, 370, 95, 100, 20, "PercPrecoCusto","% Min. custo", ListaCampos.DB_SI, true);
		adicDB(cbCustosSICMS, 370, 120, 280, 20, "CustoSICMS", "", true);

		// Orçamento
		
		setPainel(pinOrc);
		adicTab("Orçamento & PDV", pinOrc);
		adicCampo(txtCodTipoMov2, 7, 25, 80, 20, "CodTipoMov2", "Cod.tp.mov.",ListaCampos.DB_FK, txtDescTipoMov, false);
		adicDescFK(txtDescTipoMov2, 90, 25, 230, 20, "DescTipoMov","Tipo de movimento para orçamentos.");
		adicCampo(txtDescClassOrc, 330, 25, 250, 20, "ClassOrc","Classe padrão para orçamento.", ListaCampos.DB_SI, false);
		adicCampo(txtTitOrcTxt01, 330, 65, 250, 20, "TitOrcTxt01","Título para campo TXT01", ListaCampos.DB_SI, false);
		adicDB(rgTipoValidOrc, 460, 230, 250, 30, "tipovalidorc","Validade na impressão", true);
		adicDB(cbUsaOrcSeq, 10, 235,160, 20, "UsaOrcSeq", "", true);
		adicDB(cbReCalcOrc, 10, 255, 250, 20, "ReCalcPCOrc", "", true);

		//Financeiro

		setPainel(pinFin);
		adicTab("Financeiro", pinFin);
		
		lbFinCont.setBorder( BorderFactory.createEtchedBorder(1));
		lbFinOpcoes.setOpaque(true);

		adicCampo(txtCodMoeda, 10, 20, 77, 20, "CodMoeda", "Cód.moeda",ListaCampos.DB_FK, txtDescMoeda, true);
		adicDescFK(txtDescMoeda, 90, 20, 230, 20, "SingMoeda","Descrição da moeda corrente.");	
		adicDB(rgLibCred, 10, 60, 310, 80, "PrefCred", "Verificação de crédito",true);

		adic(lbFinOpcoes, 20, 150, 70, 20);
		adic(lbFinCont, 10, 160, 400, 120);
		adicDB(cbLibGeral, 20, 175, 310, 20, "LCredGlobal", "", true);
		adicDB(cbJurosPosCalc, 20, 200, 310, 20, "JurosPosCalc", "", true);
		adicCampo(txtCodTabJuros, 20, 240, 70, 20, "CodTbj", "Cód.tab.jr.",ListaCampos.DB_FK, txtDescTabJuros, false);
		adicDescFK(txtDescTabJuros, 93, 240, 250, 20, "DescTbj","Descrição da tabela de juros.");

		//SVV

		setPainel(pinSVV);
		adicTab("SVV", pinSVV);

		adicCampo(txtCodFor, 7, 25, 80, 20, "CodFor", "Cód.for.",ListaCampos.DB_FK, txtDescFor, false);
		adicDescFK(txtDescFor, 90, 25, 220, 20, "DescFor","Razão social do fornecedor");
		adicCampo(txtCodMarca, 7, 65, 80, 20, "CodMarca", "Cód.marca",ListaCampos.DB_FK, txtDescMarca, false);
		adicDescFK(txtDescMarca, 90, 65, 220, 20, "DescMarca","Descrição da marca.");
		adicCampo(txtCodGrup, 7, 105, 80, 20, "CodGrup", "Cód.grupo",ListaCampos.DB_FK, txtDescGrup, false);
		adicDescFK(txtDescGrup, 90, 105, 220, 20, "DescGrup","Descrição do grupo.");

		//Devolução

		setPainel(pinDev);
		adicTab("Devolução", pinDev);

		adicCampo(txtCodTipoFor, 7, 25, 80, 20, "CodTipoFor", "Cód.tp.for.",ListaCampos.DB_FK, txtDescTipoFor, false);
		adicDescFK(txtDescTipoFor, 90, 25, 220, 20, "DescTipoFor","Descrição do tipo de fornecedor");
		adicCampo(txtCodTipoMov5, 7, 65, 80, 20, "CodTipoMov5", "Cód.tp.mov.",ListaCampos.DB_FK, txtDescTipoMov, false);
		adicDescFK(txtDescTipoMov5, 90, 65, 220, 20, "DescTipoMov","Descrição do tipo de movimento");

		// Produto
		setPainel(pinProd);
		adicTab("Produto", pinProd);
		
		lbProdCont.setBorder( BorderFactory.createEtchedBorder(1));
		lbProdOpcoes.setOpaque(true);

		adic(lbProdOpcoes, 20, 15, 70, 20);
		adic(lbProdCont, 10, 25, 450, 150);
		adicDB(cbPepsProd, 15, 40, 310, 20, "PepsProd", "", false);
		adicDB(cbBuscaProdSimilar, 15, 60, 310, 20, "BuscaProdSimilar", "",false);
		adicDB(cbDescCompl, 15, 80, 500, 20, "DescCompPed", "", true);
		adicDB(cbTamDescProd, 20, 140, 300, 20, "TamDescProd", "Tamanho da descrição do produto", false);

		// Estoque
		setPainel(pinEstoq);
		adicTab("Estoque", pinEstoq);
		
		lbEstCont.setBorder( BorderFactory.createEtchedBorder(1));
		lbEstOpcoes.setOpaque(true);

		adicCampo(txtCodTipoMov6, 7, 25, 80, 20, "CodTipoMov6", "Cód.tp.mov.",ListaCampos.DB_FK, txtDescTipoMov, false);
		adicDescFK(txtDescTipoMov6, 90, 25, 250, 20, "DescTipoMov","Descrição do tp. mov. para inventário");
		adicCampo(txtCodTipoMov8, 7, 75, 80, 20, "CodTipoMov8", "Cód.tp.mov.",ListaCampos.DB_FK, txtDescTipoMov8, false);
		adicDescFK(txtDescTipoMov8, 90, 75, 250, 20, "DescTipoMov","Descrição do tp. mov. para RMA");
		
		adic(lbEstOpcoes, 380, 5, 70, 20);
		adic(lbEstCont, 370, 15, 340, 85);
		adicDB(cbContEstoq, 380, 35, 250, 20,"ContEstoq","",true);
		adicDB(cbMultiAlmox, 380, 55, 250, 20,"MultiAlmox","",true);
		adicDB(cbBloqCompra, 380, 75, 300, 20, "BloqCompra", "", true);

		
		nav.setAtivo(0, false);
		lcCampos.setPodeExc(false);
		lcCampos.addPostListener(this);

		setListaCampos(false, "PREFERE1", "SG");

		txtCodTipoMov2.setNomeCampo("CodTipoMov"); //Acerto o nome para que o
		// ListaCampos naum confunda
		// com a FK.
		txtCodTipoMov3.setNomeCampo("CodTipoMov"); //Acerto o nome para que o
		// ListaCampos naum confunda
		// com a FK.
		txtCodTipoMov4.setNomeCampo("CodTipoMov"); //Acerto o nome para que o
		// ListaCampos naum confunda
		// com a FK.
		txtCodTipoMov5.setNomeCampo("CodTipoMov"); //Acerto o nome para que o
		// ListaCampos naum confunda
		// com a FK.
		txtCodTipoMov6.setNomeCampo("CodTipoMov"); //Acerto o nome para que o
		// ListaCampos naum confunda
		// com a FK.
		txtCodTipoMov8.setNomeCampo("CodTipoMov"); //Acerto o nome para que o
		// ListaCampos naum confunda
		// com a FK.
		

		//lcSeq.adicDetalhe(lcPDV);
		//lcPDV.setMaster(lcSeq);

		setListaCampos(lcPDV);
		setNavegador(new Navegador(false));
		
		// Orçamento e PDV
		
		setPainel(pinOrc);
		
		lbOrcCont.setBorder( BorderFactory.createEtchedBorder(1));
		lbOrcOpcoes.setOpaque(true);

		adicCampo(txtCodTipoMov7, 7, 65, 80, 20, "CodTipoMov", "Cód.tp.mov.",ListaCampos.DB_FK, txtDescTipoMov7, false);
		adicDescFK(txtDescTipoMov7, 90, 65, 230, 20, "DescTipoMov","Descrição do tipo de movimento");
		adicCampo(txtCodPlanoPag2, 7, 105, 80, 20, "CodPlanoPag","Cód.p.pag.", ListaCampos.DB_FK, txtDescPlanoPag2, false);
		adicDescFK(txtDescPlanoPag2, 90, 105, 230, 20, "DescPlanoPag","Descrição do plano de pagamento");
		adicCampo(txtCodCli, 7, 145, 80, 20, "CodCli", "Cód.cli.",ListaCampos.DB_FK, txtDescCli, false);
		adicDescFK(txtDescCli, 90, 145, 230, 20, "NomeCli", "Nome do cliente");
		adicCampo(txtPrazo, 330, 105, 250, 20, "Prazo","Prazo de Entrega do Orçamento", ListaCampos.DB_SI, false);
		adicCampo(txtDiasVencOrc, 330, 145, 250, 20, "DiasVencOrc", "Dias p/ vencimento do orçamento",ListaCampos.DB_SI, false);
		adic(lbOrcOpcoes, 17, 190, 70, 20);
		adic(lbOrcCont, 7, 200, 720, 120);
		adicDB(cbAprovOrc, 10, 215, 350, 20, "AprovOrc", "", true);
		setListaCampos(false, "PREFERE4", "SG");

		//Email 

		setListaCampos(lcPrefere3);
		setPainel(pinEmail);
		adicTab("Mail", pinEmail);
		JLabelPad lbServer = new JLabelPad("   Configurações para envio de email");
		lbServer.setOpaque(true);
		adic(lbServer,15,10,230,15);
		adic(pinSmtp,10,15,320,160);
		setPainel(pinSmtp);
		adicCampo(txtSmtpMail,10,30,150,20,"SmtpMail","Servidor SMTP", ListaCampos.DB_SI, false);
		adicCampo(txtUserMail,10,70,150,20,"UserMail","Usuario SMTP", ListaCampos.DB_SI,false);
		adicCampo(txpPassMail,10,110,150,20,"PassMail","Senha SMTP",ListaCampos.DB_SI,false);
		setListaCampos(false, "PREFERE3", "SG");		

		// fim da adicão de abas
				
		lcCampos.addCarregaListener(this);
		lcPDV.addInsertListener(this);
		lcPDV.addEditListener(this);
		lcPrefere3.addInsertListener(this);
		lcPrefere3.addEditListener(this);
		cbEstNegGrupo.addCheckBoxListener(this);
		cbJurosPosCalc.addCheckBoxListener(this);
		
	}



	public void beforePost(PostEvent pevt) {
		if (txtCasasDec.getVlrInteger().intValue() > 5) {
			Funcoes.mensagemErro(this,
					"Número de casas decimais acima do permitido!");
			txtCasasDec.requestFocus();
			pevt.cancela();
		}
		if (txtCasasDecFin.getVlrInteger().intValue() > 5) {
			Funcoes.mensagemErro(this,
					"Número de casas decimais acima do permitido!");
			txtCasasDecFin.requestFocus();
			pevt.cancela();
		}
	}

	public void afterPost(PostEvent pevt) {
		if (pevt.getListaCampos() == lcCampos) {
			if (lcPDV.getStatus() == ListaCampos.LCS_INSERT || lcPDV.getStatus() == ListaCampos.LCS_EDIT) {
				lcPDV.post();
			}
			if (lcPrefere3.getStatus() == ListaCampos.LCS_INSERT || lcPrefere3.getStatus() == ListaCampos.LCS_EDIT) {
			    lcPrefere3.post();
			}
		}
	}

	public void afterEdit(EditEvent eevt) {
		if (eevt.getListaCampos() == lcPDV) {
			if (eevt.getListaCampos().getStatus() == ListaCampos.LCS_EDIT) {
				lcCampos.edit();
			}
		}
	}

	public void beforeEdit(EditEvent eevt) {  }

	public void edit(EditEvent eevt) { }

	public void afterInsert(InsertEvent ievt) {
		if (ievt.getListaCampos() == lcPDV) {
			if (ievt.getListaCampos().getStatus() == ListaCampos.LCS_INSERT) {
				lcCampos.edit();
			}
		}
		if (ievt.getListaCampos() == lcPrefere3) {
			if (ievt.getListaCampos().getStatus() == ListaCampos.LCS_INSERT) {
				lcCampos.edit();
			}
		}		
	}

	public void beforeInsert(InsertEvent ievt) { }

	public void valorAlterado(CheckBoxEvent cevt) {
		if (cevt.getCheckBox() == cbJurosPosCalc && cbJurosPosCalc.getVlrString().equals("S"))
			txtCodTabJuros.setAtivo(false);
		else
			txtCodTabJuros.setAtivo(true);
		if(cevt.getCheckBox() == cbEstNegGrupo){
			if(cbEstNegGrupo.getVlrString().equals("S")){
				cbEstNeg.setVlrString("N");
				cbEstNeg.setEnabled(false);
				cbEstLotNeg.setVlrString("N");
				cbEstLotNeg.setEnabled(false);
			}
			else {
				cbEstNeg.setEnabled(true);
				cbEstLotNeg.setEnabled(true);
			}
		}
	}

	public void setConexao(Connection cn) {
		super.setConexao(cn);
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
		lcTipoMov7.setConexao(cn);
		lcTipoMov8.setConexao(cn);
		lcTransp.setConexao(cn);
		lcPlanoPag.setConexao(cn);
		lcPlanoPag2.setConexao(cn);
		lcClasCli.setConexao(cn);
		lcTabPreco.setConexao(cn);
		lcCli.setConexao(cn);
		lcPDV.setConexao(cn);
		lcPrefere3.setConexao(cn);
		lcCampos.carregaDados();
		
	}

	public void afterCarrega(CarregaEvent cevt) {
		if (cevt.getListaCampos() == lcCampos) {
			if (!(lcPDV.getStatus() == ListaCampos.LCS_EDIT ||
					lcPDV.getStatus() == ListaCampos.LCS_INSERT))
			lcPDV.carregaDados();
			
			if (!(lcPrefere3.getStatus() == ListaCampos.LCS_EDIT ||
					lcPrefere3.getStatus() == ListaCampos.LCS_INSERT))
			lcPrefere3.carregaDados();			
		}

	}

	public void beforeCarrega(CarregaEvent cevt) {

	}
}