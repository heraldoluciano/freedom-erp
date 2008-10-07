/**
 * @version 02/11/2003 <BR>
 * @author Setpoint Informática Ltda./Anderson Sanchez <BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.grh <BR>
 * Classe:
 * @(#)FEmpregado.java <BR>
 * 
 * Este programa é licenciado de acordo com a LPG-PC (Licença Pública Geral para Programas de Computador), <BR>
 * versão 2.1.0 ou qualquer versão posterior. <BR>
 * A LPG-PC deve acompanhar todas PUBLICAÇÕES, DISTRIBUIÇÕES e REPRODUÇÕES deste Programa. <BR>
 * Caso uma cópia da LPG-PC não esteja disponível junto com este Programa, você pode contatar <BR>
 * o LICENCIADOR ou então pegar uma cópia em: <BR>
 * Licença: http://www.lpg.adv.br/licencas/lpgpc.rtf <BR>
 * Para poder USAR, PUBLICAR, DISTRIBUIR, REPRODUZIR ou ALTERAR este Programa é preciso estar <BR>
 * de acordo com os termos da LPG-PC <BR>
 * <BR>
 * 
 * Tela de cadastro de empregados.
 * 
 */

package org.freedom.modulos.grh;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Vector;
import javax.swing.JScrollPane;
import net.sf.jasperreports.engine.JasperPrintManager;
import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JComboBoxPad;
import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JPanelPad;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.Navegador;
import org.freedom.componentes.PainelImagem;
import org.freedom.componentes.Tabela;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FPrinterJob;
import org.freedom.telas.FTabDados;

public class FEmpregado extends FTabDados {

	private static final long serialVersionUID = 1L;

	private final JTextFieldPad txtCod = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 5, 0 );

	private final JTextFieldPad txtCodFuncao = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 5, 0 );

	private final JTextFieldPad txtCodTurno = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 5, 0 );

	private final JTextFieldPad txtCodDepto = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 5, 0 );

	private final JTextFieldFK txtDescFuncao = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private final JTextFieldFK txtDescTurno = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private final JTextFieldFK txtDescDepto = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private final JTextFieldPad txtDesc = new JTextFieldPad( JTextFieldPad.TP_STRING, 50, 0 );

	private final JTextFieldPad txtApelido = new JTextFieldPad( JTextFieldPad.TP_STRING, 30, 0 );

	private final JTextFieldPad txtEndEmpr = new JTextFieldPad( JTextFieldPad.TP_STRING, 50, 0 );

	private final JTextFieldPad txtDataNasc = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private final JTextFieldFK txtIdade = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private final JTextFieldPad txtNumEmpr = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 6, 0 );

	private final JTextFieldPad txtCidEmpr = new JTextFieldPad( JTextFieldPad.TP_STRING, 30, 0 );

	private final JTextFieldPad txtBairEmpr = new JTextFieldPad( JTextFieldPad.TP_STRING, 30, 0 );

	private final JTextFieldPad txtCepEmpr = new JTextFieldPad( JTextFieldPad.TP_STRING, 8, 0 );

	private final JTextFieldPad txtFoneEmpr = new JTextFieldPad( JTextFieldPad.TP_STRING, 12, 0 );

	private final JTextFieldPad txtDtAdmissao = new JTextFieldPad( JTextFieldPad.TP_DATE, 12, 0 );

	private final JTextFieldPad txtCtps = new JTextFieldPad( JTextFieldPad.TP_STRING, 20, 0 );

	private final JTextFieldPad txtSerie = new JTextFieldPad( JTextFieldPad.TP_STRING, 6, 0 );

	private final JTextFieldPad txtUfCtps = new JTextFieldPad( JTextFieldPad.TP_STRING, 2, 0 );

	private final JTextFieldPad txtCertifExercito = new JTextFieldPad( JTextFieldPad.TP_STRING, 20, 0 );

	private final JTextFieldPad txtPisPasep = new JTextFieldPad( JTextFieldPad.TP_STRING, 20, 0 );

	private final JTextFieldPad txtRg = new JTextFieldPad( JTextFieldPad.TP_STRING, 10, 0 );

	private final JTextFieldPad txtOrgEmiss = new JTextFieldPad( JTextFieldPad.TP_STRING, 10, 0 );

	private final JTextFieldPad txtUfExpedRg = new JTextFieldPad( JTextFieldPad.TP_STRING, 2, 0 );

	private final JTextFieldPad txtDtExpRg = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private final JTextFieldPad txtCpfEmpr = new JTextFieldPad( JTextFieldPad.TP_STRING, 11, 0 );

	private final JTextFieldPad txtTituloEleit = new JTextFieldPad( JTextFieldPad.TP_STRING, 15, 0 );

	private final JTextFieldPad txtZonaEleit = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 5, 0 );

	private final JTextFieldPad txtSecaoEleit = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 5, 0 );

	private final JTextFieldPad txtCnh = new JTextFieldPad( JTextFieldPad.TP_STRING, 20, 0 );

	private final JTextFieldPad txtNomeMae = new JTextFieldPad( JTextFieldPad.TP_STRING, 80, 0 );

	private final JTextFieldPad txtNomePai = new JTextFieldPad( JTextFieldPad.TP_STRING, 80, 0 );

	private final JTextFieldPad txtComplemento = new JTextFieldPad( JTextFieldPad.TP_STRING, 40, 0 );

	private final JTextFieldPad txtUfEmpregado = new JTextFieldPad( JTextFieldPad.TP_STRING, 2, 0 );

	private final JTextFieldPad txtDtDemissao = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private final JTextFieldPad txtEmail = new JTextFieldPad( JTextFieldPad.TP_STRING, 40, 0 );

	private final JTextFieldPad txtDddEmpr = new JTextFieldPad( JTextFieldPad.TP_STRING, 4, 0 );

	private final JTextFieldPad txtFoneEmpr2 = new JTextFieldPad( JTextFieldPad.TP_STRING, 12, 0 );

	private final JTextFieldPad txtCelEmpr = new JTextFieldPad( JTextFieldPad.TP_STRING, 12, 0 );

	//private final JTextFieldPad txtSalarioIni = new JTextFieldPad( JTextFieldPad.TP_STRING, 12, 0 );// FALTA CAMPO NA TABELA

	//private final JTextFieldPad txtSalarioAtual = new JTextFieldPad( JTextFieldPad.TP_STRING, 12, 0 );// FALTA CAMPO NA TABELA

	//private final JTextFieldPad txtDtAlteracao = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );// FALTA CAMPO NA TABELA

	private final JTextFieldPad txtVlrSalario = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, 2 );

	private final JTextFieldPad txtDtVigor = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private final JTextFieldPad txtCodBenef = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 10, 0 );

	private final JTextFieldPad txtSeqSal = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 5, 0 );

	private final JTextFieldFK txtDescBenef = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private final JTextFieldFK txtCbo = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private final JTextFieldFK txtVlrBenef = new JTextFieldFK( JTextFieldPad.TP_STRING, 15, 2 );

	private final JPanelPad panelEmpregados = new JPanelPad();

	private final JPanelPad panelSalario = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private final JPanelPad panelBeneficios = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private PainelImagem fotoEmpr = new PainelImagem( 65000 );

	private final ListaCampos lcFuncao = new ListaCampos( this, "FO" );

	private final ListaCampos lcTurno = new ListaCampos( this, "TO" );

	private final ListaCampos lcDepto = new ListaCampos( this, "DP" );

	private ListaCampos lcBenef = new ListaCampos( this, "BN" );

	private ListaCampos lcEmpSal = new ListaCampos( this );

	private ListaCampos lcEmpBenef = new ListaCampos( this );

	private Tabela tabSal = new Tabela();

	private Tabela tabBenef = new Tabela();

	private JScrollPane spnTabSal = new JScrollPane( tabSal );

	private Navegador navSal = new Navegador( true );

	private JPanelPad pinSal = new JPanelPad( 0, 80 );

	private JScrollPane spnTabBenef = new JScrollPane( tabBenef );

	private Navegador navBenef = new Navegador( true );

	private JPanelPad pinBen = new JPanelPad( 0, 80 );

	private JComboBoxPad cbStatus = null;

	private JComboBoxPad cbSexo = null;

	public FEmpregado() {

		super();
		setTitulo( "Cadastro de Empregados" );
		setAtribos( 50, 50, 520, 640 );

		lcEmpSal.setMaster( lcCampos );
		lcCampos.adicDetalhe( lcEmpSal );
		lcEmpSal.setTabela( tabSal );

		lcEmpBenef.setMaster( lcCampos );
		lcCampos.adicDetalhe( lcEmpBenef );
		lcEmpBenef.setTabela( tabBenef );

		montaListaCampos();

		montaTela();

		btImp.addActionListener( this );
		btPrevimp.addActionListener( this );

		setImprimir( true );

	}

	private void montaListaCampos() {

		lcFuncao.add( new GuardaCampo( txtCodFuncao, "CodFunc", "Cód.Func.", ListaCampos.DB_PK, true ) );
		lcFuncao.add( new GuardaCampo( txtDescFuncao, "DescFunc", "Descrição da função", ListaCampos.DB_SI, false ) );
		lcFuncao.add( new GuardaCampo( txtCbo, "CboFunc", "CBO", ListaCampos.DB_SI, false ) );
		lcFuncao.montaSql( false, "FUNCAO", "RH" );
		lcFuncao.setQueryCommit( false );
		lcFuncao.setReadOnly( true );
		txtCodFuncao.setTabelaExterna( lcFuncao );

		lcTurno.add( new GuardaCampo( txtCodTurno, "CodTurno", "Cód. turno", ListaCampos.DB_PK, true ) );
		lcTurno.add( new GuardaCampo( txtDescTurno, "DescTurno", "Descrição do turno", ListaCampos.DB_SI, false ) );
		lcTurno.montaSql( false, "TURNO", "RH" );
		lcTurno.setQueryCommit( false );
		lcTurno.setReadOnly( true );
		txtCodTurno.setTabelaExterna( lcTurno );

		lcDepto.add( new GuardaCampo( txtCodDepto, "CodDep", "Cód.Depto.", ListaCampos.DB_PK, true ) );
		lcDepto.add( new GuardaCampo( txtDescDepto, "DescDep", "Descrição do departamento", ListaCampos.DB_SI, false ) );
		lcDepto.montaSql( false, "DEPTO", "RH" );
		lcDepto.setQueryCommit( false );
		lcDepto.setReadOnly( true );
		txtCodDepto.setTabelaExterna( lcDepto );
	}

	private void montaTela() {

		Vector<String> vVals = new Vector<String>();
		vVals.addElement( "AD" );
		vVals.addElement( "DE" );
		vVals.addElement( "EF" );
		vVals.addElement( "LM" );
		vVals.addElement( "AI" );
		vVals.addElement( "AP" );
		Vector<String> vLabs = new Vector<String>();
		vLabs.addElement( "Admitido" );
		vLabs.addElement( "Demitido" );
		vLabs.addElement( "Em férias" );
		vLabs.addElement( "Licença maternidade" );
		vLabs.addElement( "Afastamento INSS" );
		vLabs.addElement( "Aposentado" );
		Vector<String> vLabs2 = new Vector<String>();
		vLabs2.addElement( "Masculino" );
		vLabs2.addElement( "Feminino" );
		Vector<String> vVals2 = new Vector<String>();
		vVals2.addElement( "M" );
		vVals2.addElement( "F" );

		cbStatus = new JComboBoxPad( vLabs, vVals, JComboBoxPad.TP_STRING, 2, 0 );
		cbSexo = new JComboBoxPad( vLabs2, vVals2, JComboBoxPad.TP_STRING, 2, 0 );

		/***********
		 * Geral   *
		 ***********/

		adicTab( "Geral", panelEmpregados );
		setPainel( panelEmpregados );

		adicCampo( txtCod, 7, 20, 80, 20, "MatEmpr", "Matricula", ListaCampos.DB_PK, true );
		adicCampo( txtDesc, 90, 20, 280, 20, "NomeEmpr", "Nome do empregado", ListaCampos.DB_SI, true );
		adicDB( fotoEmpr, 380, 20, 100, 133, "FotoEmpr", "Foto ( 3 x 4 )", false );
		adicCampo( txtApelido, 7, 60, 100, 20, "ApelidoEmpr", "Apelido", ListaCampos.DB_SI, true );
		adicDB( cbSexo, 110, 60, 120, 20, "SexoEmpr", "Sexo", true );
		adicCampo( txtDataNasc, 235, 60, 85, 20, "DtNascEmpr", "Data de nasc.", ListaCampos.DB_SI, false );
		adic( new JLabelPad( "Idade" ), 325, 40, 45, 20 );
		adic( txtIdade, 325, 60, 45, 20 );
		adicCampo( txtCtps, 7, 100, 120, 20, "CtpsEmpr", "Ctps", ListaCampos.DB_SI, false );
		adicCampo( txtSerie, 130, 100, 80, 20, "SerieCtpsEmpr", "Série", ListaCampos.DB_SI, false );
		adicCampo( txtUfCtps, 213, 100, 30, 20, "UfCtpsEmpr", "UF", ListaCampos.DB_SI, false );
		adicCampo( txtCertifExercito, 245, 100, 125, 20, "CertReservEmpr", "Cert. exército", ListaCampos.DB_SI, false );
		adicCampo( txtPisPasep, 7, 140, 110, 20, "PisPasepEmpr", "PIS/PASEP", ListaCampos.DB_SI, false );
		adicCampo( txtRg, 120, 140, 85, 20, "RgEmpr", "Rg", ListaCampos.DB_SI, false );
		adicCampo( txtOrgEmiss, 208, 140, 62, 20, "OrgExpRhEmpr", "Org.Emis.", ListaCampos.DB_SI, false );
		adicCampo( txtUfExpedRg, 272, 140, 30, 20, "UfRgEmpr", "UF", ListaCampos.DB_SI, false );
		adicCampo( txtDtExpRg, 305, 140, 65, 20, "DtExpRgEmpr", "Data", ListaCampos.DB_SI, false );
		adicCampo( txtCpfEmpr, 7, 180, 85, 20, "CpfEmpr", "Cpf", ListaCampos.DB_SI, false );
		adicCampo( txtTituloEleit, 95, 180, 110, 20, "TitEleitEmpr", "Titulo Eleitoral", ListaCampos.DB_SI, false );
		adicCampo( txtZonaEleit, 208, 180, 60, 20, "ZonaEleitEmpr", "Zona", ListaCampos.DB_SI, false );
		adicCampo( txtSecaoEleit, 270, 180, 60, 20, "SecaoEleitEmpr", "Seção", ListaCampos.DB_SI, false );
		adicCampo( txtCnh, 335, 180, 150, 20, "CnhEmpr", "CNH", ListaCampos.DB_SI, false );
		adicCampo( txtNomeMae, 7, 220, 230, 20, "MaeEmpr", "Nome da mãe", ListaCampos.DB_SI, false );
		adicCampo( txtNomePai, 245, 220, 240, 20, "PaiEmpr", "Nome do pai", ListaCampos.DB_SI, false );
		adicCampo( txtEndEmpr, 7, 260, 280, 20, "EndEmpr", "Endereço", ListaCampos.DB_SI, false );
		adicCampo( txtNumEmpr, 290, 260, 80, 20, "NumEmpr", "Número", ListaCampos.DB_SI, false );
		adicCampo( txtComplemento, 375, 260, 110, 20, "ComplEndEmpr", "Complemento", ListaCampos.DB_SI, false );
		adicCampo( txtBairEmpr, 7, 300, 180, 20, "BairEmpr", "Bairro", ListaCampos.DB_SI, false );
		adicCampo( txtCidEmpr, 190, 300, 160, 20, "CidEmpr", "Cidade", ListaCampos.DB_SI, false );
		adicCampo( txtUfEmpregado, 353, 300, 30, 20, "UfEmpr", "UF", ListaCampos.DB_SI, false );
		adicCampo( txtCepEmpr, 386, 300, 100, 20, "CepEmpr", "Cep", ListaCampos.DB_SI, false );
		adicCampo( txtDtDemissao, 7, 340, 90, 20, "DtDemissaoEmpr", "Data Demissão", ListaCampos.DB_SI, false );
		adicDB( cbStatus, 100, 340, 100, 20, "StatusEmpr", "Status", true );
		adicCampo( txtEmail, 205, 340, 283, 20, "EmailEmpr", "E-mail", ListaCampos.DB_SI, false );
		adicCampo( txtDddEmpr, 7, 380, 50, 20, "DddEmpr", "DDD", ListaCampos.DB_SI, false );
		adicCampo( txtFoneEmpr, 60, 380, 130, 20, "FoneEmpr", "Telefone 1", ListaCampos.DB_SI, false );
		adicCampo( txtFoneEmpr2, 195, 380, 130, 20, "Fone2Empr", "Telefone 2", ListaCampos.DB_SI, false );
		adicCampo( txtCelEmpr, 330, 380, 160, 20, "CelEmpr", "Celular", ListaCampos.DB_SI, false );
		adicCampo( txtCodTurno, 7, 420, 60, 20, "CodTurno", "Cód.Turn.", ListaCampos.DB_FK, true );
		adicDescFK( txtDescTurno, 71, 420, 420, 20, "DescTurno", "Descrição do turno" );
		adicCampo( txtCodFuncao, 7, 460, 60, 20, "CodFunc", "Cód.Func.", ListaCampos.DB_FK, true );
		adicDescFK( txtDescFuncao, 71, 460, 340, 20, "DescFunc", "Descrição da função" );
		adicDescFK( txtCbo, 415, 460, 75, 20, "CboFunc", "CBO" );
		adicCampo( txtCodDepto, 7, 500, 60, 20, "CodDep", "Cód.Dept.", ListaCampos.DB_FK, true );
		adicDescFK( txtDescDepto, 71, 500, 340, 20, "DescDepto", "Descrição do departamento" );
		adicCampo( txtDtAdmissao, 415, 500, 75, 20, "DtAdmissao", "Data admis.", ListaCampos.DB_SI, true );

		txtCepEmpr.setMascara( JTextFieldPad.MC_CEP );
		txtFoneEmpr.setMascara( JTextFieldPad.MC_FONE );
		txtFoneEmpr2.setMascara( JTextFieldPad.MC_FONE );
		txtCelEmpr.setMascara( JTextFieldPad.MC_FONE );
		txtCpfEmpr.setMascara( JTextFieldPad.MC_CPF );
		txtRg.setMascara( JTextFieldPad.MC_RG );

		setListaCampos( true, "EMPREGADO", "RH" );
		lcCampos.setQueryInsert( false );

		/***********
		 * Salário *
		 ***********/

		setPainel( pinSal, panelSalario );
		adicTab( "Salário", panelSalario );
		setListaCampos( lcEmpSal );

		navSal.setAtivo( 4, true );

		setNavegador( navSal );
		panelSalario.add( pinSal, BorderLayout.SOUTH );
		panelSalario.add( spnTabSal, BorderLayout.CENTER );

		pinSal.adic( navSal, 0, 50, 270, 25 );

		adicCampoInvisivel( txtSeqSal, "SeqSal", "Seq.", ListaCampos.DB_PK, false );
		adicCampo( txtVlrSalario, 7, 20, 90, 20, "ValorSal", "Salário", ListaCampos.DB_SI, false );
		adicCampo( txtDtVigor, 100, 20, 90, 20, "DtVigor", "Data.vigor", ListaCampos.DB_SI, true );
		lcEmpSal.montaTab();
		lcEmpSal.setQueryInsert( false );
		lcEmpSal.setQueryCommit( false );
		setListaCampos( true, "EMPREGADOSAL", "RH" );

		/**************
		 * Benefícios *
		 **************/

		setPainel( pinBen, panelBeneficios );
		adicTab( "Benefícios", panelBeneficios );
		setListaCampos( lcEmpBenef );

		navSal.setAtivo( 4, true );

		setNavegador( navBenef );
		panelBeneficios.add( pinBen, BorderLayout.SOUTH );
		panelBeneficios.add( spnTabBenef, BorderLayout.CENTER );

		pinBen.adic( navBenef, 0, 50, 270, 25 );

		/*****************
		 * LC Beneficios *
		 *****************/

		lcBenef.add( new GuardaCampo( txtCodBenef, "CodBenef", "Cód.benef", ListaCampos.DB_PK, false ) );
		lcBenef.add( new GuardaCampo( txtDescBenef, "DescBenef", "Descrição do benefício", ListaCampos.DB_SI, false ) );
		lcBenef.add( new GuardaCampo( txtVlrBenef, "ValorBenef", "Valor( R$ )", ListaCampos.DB_SI, false ) );
		lcBenef.montaSql( false, "BENEFICIO", "RH" );
		lcBenef.setQueryCommit( false );
		lcBenef.setReadOnly( true );
		txtCodBenef.setTabelaExterna( lcBenef );

		adicCampo( txtCodBenef, 7, 20, 60, 20, "CodBenef", "Cód.Benef", ListaCampos.DB_PF, true );
		adicDescFK( txtDescBenef, 70, 20, 250, 20, "DescBenef", "Descrição do benefício" );
		adicDescFK( txtVlrBenef, 325, 20, 80, 20, "ValorBenef", "Valor( R$ )" );
		lcEmpBenef.montaTab();
		lcEmpBenef.setQueryInsert( false );
		lcEmpBenef.setQueryCommit( false );
		setListaCampos( true, "EMPREGADOBENEF", "RH" );

	}

	public void setConexao( Connection cn ) {

		super.setConexao( cn );
		lcFuncao.setConexao( cn );
		lcTurno.setConexao( cn );
		lcDepto.setConexao( cn );
		lcEmpSal.setConexao( cn );
		lcBenef.setConexao( cn );
		lcEmpBenef.setConexao( cn );
	}

	public void actionPerformed( ActionEvent evt ) {

		if ( evt.getSource() == btPrevimp ) {
			imprimir( true );
		}
		else if ( evt.getSource() == btImp ) {
			imprimir( false );
		}

		super.actionPerformed( evt );
	}

	private void imprimir( boolean bVisualizar ) {

		FPrinterJob dlGr = null;
		HashMap<String, Object> hParam = new HashMap<String, Object>();

		hParam.put( "CODEMP", Aplicativo.iCodEmp );
		hParam.put( "CODFILIAL", ListaCampos.getMasterFilial( "RHEMPREGADO" ) );

		dlGr = new FPrinterJob( "relatorios/grhFuncao.jasper", "Relatório Resumido", "", this, hParam, con, null, false );

		if ( bVisualizar ) {
			dlGr.setVisible( true );
		}
		else {
			try {
				JasperPrintManager.printReport( dlGr.getRelatorio(), true );
			} catch ( Exception e ) {
				e.printStackTrace();
				Funcoes.mensagemErro( this, "Erro na geração do relátorio!" + e.getMessage(), true, con, e );
			}
		}
	}
}
