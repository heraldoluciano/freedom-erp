/**
 * @version 8/02/2007 <BR>
 * @author Setpoint Informática Ltda./Alex Rodrigues<BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.fnc <BR>
 * Classe:
 * @(#)FPrefereFBB.java <BR>
 * 
 * Este arquivo é parte do sistema Freedom-ERP, o Freedom-ERP é um software livre; você pode redistribui-lo e/ou <BR>
 * modifica-lo dentro dos termos da Licença Pública Geral GNU como publicada pela Fundação do Software Livre (FSF); <BR>
 * na versão 2 da Licença, ou (na sua opnião) qualquer versão. <BR>
 * Este programa é distribuido na esperança que possa ser  util, mas SEM NENHUMA GARANTIA; <BR>
 * sem uma garantia implicita de ADEQUAÇÂO a qualquer MERCADO ou APLICAÇÃO EM PARTICULAR. <BR>
 * Veja a Licença Pública Geral GNU para maiores detalhes. <BR>
 * Você deve ter recebido uma cópia da Licença Pública Geral GNU junto com este programa, se não, <BR>
 * de acordo com os termos da LPG-PC <BR>
 * <BR>
 * 
 * Tela de preferencias para Febraban
 * 
 */

package org.freedom.modulos.fnc;

import java.awt.BorderLayout;
import java.awt.Dimension;
import org.freedom.infra.model.jdbc.DbConnection;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import org.freedom.acao.CarregaEvent;
import org.freedom.acao.CarregaListener;
import org.freedom.acao.PostEvent;
import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JComboBoxPad;
import org.freedom.componentes.JPanelPad;
import org.freedom.componentes.JRadioGroup;
import org.freedom.componentes.JTabbedPanePad;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.Navegador;
import org.freedom.componentes.Tabela;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FTabDados;

public class FPrefereFBB extends FTabDados implements CarregaListener {

	private static final long serialVersionUID = 1L;
	
	private final JPanelPad panelGeral = new JPanelPad();
	
	private final JPanelPad panelSiacc = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );
	
	private final JPanelPad panelTabSiacc = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );
	
	private final JPanelPad panelCamposSiacc = new JPanelPad();
	
	private final JPanelPad panelNavSiacc = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );
	
	private final JTabbedPanePad tbCnab = new JTabbedPanePad();
	
	private final JPanelPad panelCnab = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );
	
	private final JPanelPad panelCnabManager = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );
	
	private final JPanelPad panelCnabGeral = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );
	
	private final JPanelPad panelCnabPref = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );
	
	private final JPanelPad panelTabCnab = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );
	
	private final JPanelPad panelCamposCnab = new JPanelPad();
	
	private final JPanelPad panelCamposPref = new JPanelPad( 300, 420 );
	
	private final JPanelPad panelNavCnab = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private final JTextFieldPad txtNomeEmp = new JTextFieldPad( JTextFieldPad.TP_STRING, 20, 0 );
	
	private final JTextFieldPad txtNomeEmpCnab = new JTextFieldPad( JTextFieldPad.TP_STRING, 30, 0 );
	
	private final JTextFieldPad txtTipoSiacc = new JTextFieldPad( JTextFieldPad.TP_STRING, 2, 0 );
	
	private final JTextFieldPad txtCodBancoSiacc = new JTextFieldPad( JTextFieldPad.TP_STRING, 4, 0 );

	private final JTextFieldFK txtNomeBancoSiacc = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );
	
	private final JTextFieldPad txtCodConvSiacc = new JTextFieldPad( JTextFieldPad.TP_STRING, 20, 0 );
	
	private final JTextFieldPad txtVersaoSiacc = new JTextFieldPad( JTextFieldPad.TP_STRING, 2, 0 );
	
	private final JTextFieldPad txtIdentServSiacc = new JTextFieldPad( JTextFieldPad.TP_STRING, 17, 0 );
	
	private final JTextFieldPad txtContaComprSiacc = new JTextFieldPad( JTextFieldPad.TP_STRING, 16, 0 );
		
	private final JTextFieldPad txtNroSeqSiacc = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	
	private JRadioGroup<?, ?> rgIdentAmbCliSiacc;
	
	private JRadioGroup<?, ?> rgIdentAmbBcoSiacc;
	
	private final JTextFieldPad txtTipoCnab = new JTextFieldPad( JTextFieldPad.TP_STRING, 2, 0 );
	
	private final JTextFieldPad txtCodBancoCnab = new JTextFieldPad( JTextFieldPad.TP_STRING, 4, 0 );

	private final JTextFieldFK txtNomeBancoCnab = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );
	
	private final JTextFieldPad txtCodConvCnab = new JTextFieldPad( JTextFieldPad.TP_STRING, 20, 0 );
	
	private final JTextFieldPad txtVersaoCnab = new JTextFieldPad( JTextFieldPad.TP_STRING, 2, 0 );
	
	private final JTextFieldPad txtIdentServCnab = new JTextFieldPad( JTextFieldPad.TP_STRING, 17, 0 );
	
	private final JTextFieldPad txtContaComprCnab = new JTextFieldPad( JTextFieldPad.TP_STRING, 16, 0 );
		
	private final JTextFieldPad txtNroSeqCnab = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private final JTextFieldPad txtNumContaCnab = new JTextFieldPad( JTextFieldPad.TP_STRING, 10, 0 ); 
	
	private final JTextFieldFK txtAgenciaCnab = new JTextFieldFK( JTextFieldFK.TP_STRING, 6, 0 );
	
	private final JTextFieldFK txtDescContaCnab = new JTextFieldFK(JTextFieldFK.TP_STRING, 50, 0);
	
	private final JTextFieldPad txtNumContaSiacc = new JTextFieldPad( JTextFieldPad.TP_STRING, 10, 0 ); 
	
	private final JTextFieldFK txtAgenciaSiacc = new JTextFieldFK( JTextFieldFK.TP_STRING, 6, 0 );
	
	private final JTextFieldFK txtDescContaSiacc = new JTextFieldFK(JTextFieldFK.TP_STRING, 50, 0);
	
	private final JTextFieldPad txtModalidadeCnab = new JTextFieldPad( JTextFieldPad.TP_STRING, 2, 0 );
	
	private final JTextFieldPad txtConvBol = new JTextFieldPad( JTextFieldPad.TP_STRING, 10, 0 );
	
	private JRadioGroup<?, ?> rgIdentAmbCliCnab;
	
	private JRadioGroup<?, ?> rgIdentAmbBcoCnab;

	private JComboBoxPad cbFormaCadastramento;
	
	private JComboBoxPad cbTipoDocumento;
	
	private JComboBoxPad cbEmissaoBloqueto;
	
	private JComboBoxPad cbDistribuicao;
	
	private JComboBoxPad cbEspecieTitulo;
	
	private JComboBoxPad cbJurosMora;
	
	private final JTextFieldPad txtVlrJuros = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, Aplicativo.casasDecFin );
	
	private JComboBoxPad cbDesconto;
	
	private final JTextFieldPad txtVlrDesconto = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, Aplicativo.casasDecFin );
	
	private JComboBoxPad cbProtesto;
	
	private final JTextFieldPad txtNumDiasProtesto = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 3, 0 );
	
	private JComboBoxPad cbDevolucao;
	
	private final JTextFieldPad txtNumDiasDevolucao = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 3, 0 );
	
	private JComboBoxPad cbAceite;

	private final ListaCampos lcSiacc = new ListaCampos( this, "BO" );

	private final ListaCampos lcCnab = new ListaCampos( this, "BO" );

	private final ListaCampos lcBancoSiacc = new ListaCampos( this, "BO" );

	private final ListaCampos lcBancoCnab = new ListaCampos( this, "BO" );
	
	private final ListaCampos lcContaCnab = new ListaCampos( this, "CA" );
	
	private final ListaCampos lcContaSiacc = new ListaCampos( this, "CA" );
	
	private final Navegador nvSiacc = new Navegador( true );
	
	private final Navegador nvCnab = new Navegador( true );
	
	private final Tabela tabSiacc = new Tabela();
	
	private final Tabela tabCnab = new Tabela();
	
	public static final String TP_SIACC = "01";
	
	public static final String TP_CNAB = "02";
	

	public FPrefereFBB() {

		setTitulo( "Preferências Gerais" );
		setAtribos( 50, 50, 405, 520 );
		
		montaRadioGrupos();
		montaComboBoxs();
		montaListaCampos();
		montaTela();
		
		lcSiacc.montaTab();
		lcCnab.montaTab();
		
		tabSiacc.setTamColuna( 40, 0 );
		tabSiacc.setTamColuna( 80, 1 );
		tabSiacc.setTamColuna( 150, 2 );
		tabSiacc.setTamColuna( 50, 3 );
		
		tabCnab.setTamColuna( 40, 0 );
		tabCnab.setTamColuna( 80, 1 );
		tabCnab.setTamColuna( 150, 2 );
		tabCnab.setTamColuna( 50, 3 );
		
		txtTipoSiacc.setVlrString( TP_SIACC );
		txtTipoCnab.setVlrString( TP_CNAB );
		
		lcCnab.addPostListener( this );
		lcSiacc.addPostListener( this );
		
		lcCampos.addCarregaListener( this );
		lcCnab.addCarregaListener( this );
		lcSiacc.addCarregaListener( this );
	}
	
	private void montaRadioGrupos() {
		
		Vector<String> vLabs0 = new Vector<String>();
		Vector<String> vVals0 = new Vector<String>();
		vLabs0.add( "Produção" );
		vLabs0.add( "Teste" );
		vVals0.add( "P" );
		vVals0.add( "T" );
		rgIdentAmbCliSiacc = new JRadioGroup<String, String>( 2, 1, vLabs0, vVals0 );
		
		Vector<String> vLabs1 = new Vector<String>();
		Vector<String> vVals1 = new Vector<String>();
		vLabs1.add( "Produção" );
		vLabs1.add( "Teste" );
		vVals1.add( "P" );
		vVals1.add( "T" );
		rgIdentAmbBcoSiacc = new JRadioGroup<String, String>( 2, 1, vLabs1, vVals1 );
		
		Vector<String> vLabs2 = new Vector<String>();
		Vector<String> vVals2 = new Vector<String>();
		vLabs2.add( "Produção" );
		vLabs2.add( "Teste" );
		vVals2.add( "P" );
		vVals2.add( "T" );
		rgIdentAmbCliCnab = new JRadioGroup<String, String>( 2, 1, vLabs2, vVals2 );
		
		Vector<String> vLabs3 = new Vector<String>();
		Vector<String> vVals3 = new Vector<String>();
		vLabs3.add( "Produção" );
		vLabs3.add( "Teste" );
		vVals3.add( "P" );
		vVals3.add( "T" );
		rgIdentAmbBcoCnab = new JRadioGroup<String, String>( 2, 1, vLabs3, vVals3 );
	}
	
	private void montaComboBoxs() {

		Vector<String> vLabs1 = new Vector<String>();
		Vector<Integer> vVals1 = new Vector<Integer>();
		vLabs1.addElement( "com cadastro" );
		vLabs1.addElement( "sem cadastro" );
		vVals1.addElement( 1 );
		vVals1.addElement( 2 );
		cbFormaCadastramento = new JComboBoxPad( vLabs1, vVals1, JComboBoxPad.TP_INTEGER, 1, 0 );

		Vector<String> vLabs2 = new Vector<String>();
		Vector<Integer> vVals2 = new Vector<Integer>();
		vLabs2.addElement( "tradicional" );
		vLabs2.addElement( "escritural" );
		vVals2.addElement( 1 );
		vVals2.addElement( 2 );
		cbTipoDocumento = new JComboBoxPad( vLabs2, vVals2, JComboBoxPad.TP_INTEGER, 1, 0 );

		Vector<String> vLabs3 = new Vector<String>();
		Vector<Integer> vVals3 = new Vector<Integer>();
		vLabs3.addElement( "Banco emite" );
		vLabs3.addElement( "Empresa emite" );
		vLabs3.addElement( "Banco pré-emite e empresa completa" );
		vLabs3.addElement( "Banco reemite" );
		vLabs3.addElement( "Banco não reemite" );
		vLabs3.addElement( "Combraça sem papel" );
		vVals3.addElement( 1 );
		vVals3.addElement( 2 );
		vVals3.addElement( 3 );
		vVals3.addElement( 4 );
		vVals3.addElement( 5 );
		vVals3.addElement( 6 );
		cbEmissaoBloqueto = new JComboBoxPad( vLabs3, vVals3, JComboBoxPad.TP_INTEGER, 1, 0 );

		Vector<String> vLabs4 = new Vector<String>();
		Vector<Integer> vVals4 = new Vector<Integer>();
		vLabs4.addElement( "Banco" );
		vLabs4.addElement( "Empresa" );
		vVals4.addElement( 1 );
		vVals4.addElement( 2 );
		cbDistribuicao = new JComboBoxPad( vLabs4, vVals4, JComboBoxPad.TP_INTEGER, 1, 0 );

		Vector<String> vLabs5 = new Vector<String>();
		Vector<Integer> vVals5 = new Vector<Integer>();
		vLabs5.addElement( "CH - Cheque" ); 
		vLabs5.addElement( "DM - Duplicata mercantíl" );
		vLabs5.addElement( "DMI - Duplicata mercantíl p/ indicação" );
		vLabs5.addElement( "DS - Duplicata de serviço" );
		vLabs5.addElement( "DSI - DUplicata de serviço p/ indicação" );
		vLabs5.addElement( "DR - Duplicata rural" );
		vLabs5.addElement( "LC - Letra de cambio" );
		vLabs5.addElement( "NCC - Nota de crédito comercial" );
		vLabs5.addElement( "NCE - Nota de crédito a exportação" );
		vLabs5.addElement( "NCI - Nota de crédito indústria" );
		vLabs5.addElement( "NCR - Nota de crédito rural" );
		vLabs5.addElement( "NP - Nota promissória" );
		vLabs5.addElement( "NPR - Nota promissória rural" );
		vLabs5.addElement( "TM - Triplicata mercantíl" );
		vLabs5.addElement( "TS - Triplicata de serviço" );
		vLabs5.addElement( "NS - Nota de seguro" );
		vLabs5.addElement( "RC - Recibo" );
		vLabs5.addElement( "FAT - Fatura" );
		vLabs5.addElement( "ND - Nota de débito" );
		vLabs5.addElement( "AP - Apolice de seguro" );
		vLabs5.addElement( "ME - Mensalidade escolar" );
		vLabs5.addElement( "PC - Parcela de consórcio" );
		vLabs5.addElement( "Outros" );
		vVals5.addElement( 1 );
		vVals5.addElement( 2 );
		vVals5.addElement( 3 );
		vVals5.addElement( 4 );
		vVals5.addElement( 5 );
		vVals5.addElement( 6 );
		vVals5.addElement( 7 );
		vVals5.addElement( 8 );
		vVals5.addElement( 9 );
		vVals5.addElement( 10 );
		vVals5.addElement( 11 );
		vVals5.addElement( 12 );
		vVals5.addElement( 13 );
		vVals5.addElement( 14 );
		vVals5.addElement( 15 );
		vVals5.addElement( 16 );
		vVals5.addElement( 17 );
		vVals5.addElement( 18 );
		vVals5.addElement( 19 );
		vVals5.addElement( 20 );
		vVals5.addElement( 21 );
		vVals5.addElement( 22 );
		vVals5.addElement( 99 );
		cbEspecieTitulo = new JComboBoxPad( vLabs5, vVals5, JComboBoxPad.TP_INTEGER, 2, 0 );

		Vector<String> vLabs6 = new Vector<String>();
		Vector<Integer> vVals6 = new Vector<Integer>();
		vLabs6.addElement( "Valor por dia" );
		vLabs6.addElement( "Taxa mensal" );
		vLabs6.addElement( "Isento" );
		vVals6.addElement( 1 );
		vVals6.addElement( 2 );
		vVals6.addElement( 3 );
		cbJurosMora = new JComboBoxPad( vLabs6, vVals6, JComboBoxPad.TP_INTEGER, 1, 0 );

		Vector<String> vLabs7 = new Vector<String>();
		Vector<Integer> vVals7 = new Vector<Integer>();
		vLabs7.addElement( "Sem desconto" );
		vLabs7.addElement( "Valor fixo até a data informada" );
		vLabs7.addElement( "Percentual até a data informada" );
		vLabs7.addElement( "Valor por antecipação por dia corrido" );
		vLabs7.addElement( "Valor por antecipação por dia util" );
		vLabs7.addElement( "Percentual sobre o valor nominal dia corrido" );
		vLabs7.addElement( "Percentual sobre o valor nominal dia util" );
		vVals7.addElement( 0 );
		vVals7.addElement( 1 );
		vVals7.addElement( 2 );
		vVals7.addElement( 3 );
		vVals7.addElement( 4 );
		vVals7.addElement( 5 );
		vVals7.addElement( 6 );
		cbDesconto = new JComboBoxPad( vLabs7, vVals7, JComboBoxPad.TP_INTEGER, 1, 0 );

		Vector<String> vLabs8 = new Vector<String>();
		Vector<Integer> vVals8 = new Vector<Integer>();
		vLabs8.addElement( "Dias corridos" );
		vLabs8.addElement( "Dias utéis" );
		vLabs8.addElement( "Não protestar" );
		vVals8.addElement( 1 );
		vVals8.addElement( 2 );
		vVals8.addElement( 3 );
		cbProtesto = new JComboBoxPad( vLabs8, vVals8, JComboBoxPad.TP_INTEGER, 1, 0 );

		Vector<String> vLabs9 = new Vector<String>();
		Vector<Integer> vVals9 = new Vector<Integer>();
		vLabs9.addElement( "Baixar / Devolver" );
		vLabs9.addElement( "Não baixar / Não devlover" );
		vVals9.addElement( 1 );
		vVals9.addElement( 2 );
		cbDevolucao = new JComboBoxPad( vLabs9, vVals9, JComboBoxPad.TP_INTEGER, 1, 0 );
		
		Vector<String> vLabs10 = new Vector<String>();
		Vector<String> vVals10 = new Vector<String>();
		vLabs10.addElement( "Sim" );
		vLabs10.addElement( "Não" );
		vVals10.addElement( "S" );
		vVals10.addElement( "N" );
		cbAceite = new JComboBoxPad( vLabs10, vVals10, JComboBoxPad.TP_STRING, 1, 0 );
	}
	
	private void montaListaCampos() {


		/**********************
		 *   FNBANCO  SIACC   *
		 **********************/
		lcBancoSiacc.add( new GuardaCampo( txtCodBancoSiacc, "CodBanco", "Cód.banco", ListaCampos.DB_PK, true ) );
		lcBancoSiacc.add( new GuardaCampo( txtNomeBancoSiacc, "NomeBanco", "Nome do Banco", ListaCampos.DB_SI, false ) );
		lcBancoSiacc.setDinWhereAdic( " CODBANCO=#N ", txtCodBancoSiacc );
		lcBancoSiacc.montaSql( false, "BANCO", "FN" );
		lcBancoSiacc.setQueryCommit( false );
		lcBancoSiacc.setReadOnly( true );
		txtCodBancoSiacc.setTabelaExterna( lcBancoSiacc );
		
		lcSiacc.setMaster( lcCampos );
		lcSiacc.setTabela( tabSiacc );
		
		lcCampos.adicDetalhe( lcSiacc );
		
		/**********************
		 *   FNBANCO   CNAB   *
		 **********************/
		lcBancoCnab.add( new GuardaCampo( txtCodBancoCnab, "CodBanco", "Cód.banco", ListaCampos.DB_PK, true ) );
		lcBancoCnab.add( new GuardaCampo( txtNomeBancoCnab, "NomeBanco", "Nome do Banco", ListaCampos.DB_SI, false ) );
		lcBancoCnab.setDinWhereAdic( " CODBANCO=#N ", txtCodBancoCnab );
		lcBancoCnab.montaSql( false, "BANCO", "FN" );
		lcBancoCnab.setQueryCommit( false );
		lcBancoCnab.setReadOnly( true );
		txtCodBancoCnab.setTabelaExterna( lcBancoCnab );
	
		/**********************
		 * FNCONTA CONTA CNAB *
		 **********************/
	    lcContaCnab.add(new GuardaCampo( txtNumContaCnab, "NumConta", "N° Conta", ListaCampos.DB_PK, false));
	    lcContaCnab.add(new GuardaCampo( txtAgenciaCnab, "AgenciaConta", "Agência", ListaCampos.DB_SI, false ));
	    lcContaCnab.add(new GuardaCampo( txtDescContaCnab, "DescConta", "Descrição da conta", ListaCampos.DB_SI, false));
	    lcContaCnab.montaSql(false, "CONTA", "FN");    
	    lcContaCnab.setQueryCommit(false);
	    lcContaCnab.setReadOnly(true);
	    txtNumContaCnab.setTabelaExterna(lcContaCnab);
	    
	    
	    /***********************
		 * FNCONTA CONTA SIACC *
		 ***********************/
	    lcContaSiacc.add(new GuardaCampo( txtNumContaSiacc, "NumConta", "N° Conta", ListaCampos.DB_PK, false));
	    lcContaSiacc.add(new GuardaCampo( txtAgenciaSiacc, "AgenciaConta", "Agência", ListaCampos.DB_SI, false ));
	    lcContaSiacc.add(new GuardaCampo( txtDescContaSiacc, "DescConta", "Descrição da conta", ListaCampos.DB_SI, false));
	    lcContaSiacc.montaSql(false, "CONTA", "FN");    
	    lcContaSiacc.setQueryCommit(false);
	    lcContaSiacc.setReadOnly(true);
	    txtNumContaSiacc.setTabelaExterna(lcContaSiacc);

		lcCnab.setMaster( lcCampos );
		lcCnab.setTabela( tabCnab );
		
		lcCampos.adicDetalhe( lcCnab );
	}
	
	private void montaTela() {

		/*****************
		 *     GERAL     *
		 *****************/
		
		lcCampos.setMensInserir( false );

		setPainel( panelGeral );
		adicTab( "Geral", panelGeral );
		adicCampo( txtNomeEmp, 7, 30, 250, 20, "NomeEmp", "Nome da empresa (siacc)", ListaCampos.DB_SI, true );
		adicCampo( txtNomeEmpCnab, 7, 70, 250, 20, "NomeEmpCnab", "Nome da empresa (cnab)", ListaCampos.DB_SI, true );
		
		nav.setAtivo( 0, false );
		lcCampos.setPodeExc( false );

		setListaCampos( false, "PREFERE6", "SG" );

		/*****************
		 *     SIACC     *
		 *****************/		
		setListaCampos( lcSiacc );
		setNavegador( nvSiacc );

		adicTab( "SIACC", panelSiacc );
		setPainel( panelCamposSiacc, panelSiacc );
		
		panelSiacc.add( panelTabSiacc, BorderLayout.NORTH );
		panelSiacc.add( panelCamposSiacc, BorderLayout.CENTER );
		panelSiacc.add( panelNavSiacc, BorderLayout.SOUTH );
		
		panelTabSiacc.setPreferredSize( new Dimension( 300, 100 ) );
		panelTabSiacc.setBorder( BorderFactory.createEtchedBorder() );
		panelTabSiacc.add( new JScrollPane( tabSiacc ), BorderLayout.CENTER );

		lcSiacc.add( new GuardaCampo( txtTipoSiacc, "TipoFebraban", "Tipo", ListaCampos.DB_PK, true ) );
		adicCampo( txtCodBancoSiacc, 7, 30, 100, 20, "CodBanco", "Cód.banco", ListaCampos.DB_PF, txtNomeBancoSiacc, true );
		adicDescFK( txtNomeBancoSiacc, 110, 30, 260, 20, "NomeBanco", "Nome do banco" );		
		adicCampo( txtCodConvSiacc, 7, 70, 140, 20, "CodConv", "Convênio", ListaCampos.DB_SI, false );
		adicCampo( txtVersaoSiacc, 150, 70, 50, 20, "VerLayout", "Versão", ListaCampos.DB_SI, false );
		adicCampo( txtIdentServSiacc, 203, 70, 100, 20, "IdentServ", "Ident. Serviço", ListaCampos.DB_SI, false );
		adicCampo( txtNroSeqSiacc, 306, 70, 63, 20, "NroSeq", "Sequência", ListaCampos.DB_SI, false );		
		adicCampo( txtNumContaSiacc, 7, 110, 80, 20, "NumConta", "Nº da conta", ListaCampos.DB_FK, true );
		adicDescFK( txtAgenciaSiacc, 90, 110, 57, 20, "AgenciaConta", "Agência" );
		adicDescFK( txtDescContaSiacc, 150, 110, 220, 20, "DescConta", "Descrição da conta" );
		adicCampo( txtContaComprSiacc, 7, 150, 140, 20, "ContaCompr", "Conta Compromisso", ListaCampos.DB_SI, false );		
		adicDB( rgIdentAmbCliSiacc, 7, 190, 178, 60, "IdentAmbCli", "Ambiente do cliente", false );
		adicDB( rgIdentAmbBcoSiacc, 193, 190, 178, 60, "IdentAmbBco", "Ambiente do banco", false );
		setListaCampos( false, "ITPREFERE6", "SG" );
		lcSiacc.setWhereAdic( " TIPOFEBRABAN='01' " );		
				
		panelNavSiacc.setPreferredSize( new Dimension( 300, 30 ) );
		panelNavSiacc.setBorder( BorderFactory.createEtchedBorder() );
		panelNavSiacc.add( nvSiacc, BorderLayout.WEST );
		
		
		/****************
		 *     CNAB     *
		 ****************/		
		setListaCampos( lcCnab );
		setNavegador( nvCnab );

		adicTab( "CNAB", panelCnab );
		
		panelCnab.add( panelCnabManager, BorderLayout.CENTER );
		
		panelTabCnab.setPreferredSize( new Dimension( 300, 100 ) );
		panelTabCnab.setBorder( BorderFactory.createEtchedBorder() );
		panelTabCnab.add( new JScrollPane( tabCnab ), BorderLayout.CENTER );		
		
		panelCnabManager.add( panelTabCnab, BorderLayout.NORTH );
		panelCnabManager.add( tbCnab, BorderLayout.CENTER );
		panelCnabManager.add( panelNavCnab, BorderLayout.SOUTH );
		
		tbCnab.setTabPlacement( SwingConstants.BOTTOM );
		
		/*** ABA GERAL ***/		
		
		tbCnab.add( "geral", panelCnabGeral );		
		panelCnabGeral.add( panelCamposCnab, BorderLayout.CENTER );
		setPainel( panelCamposCnab );

		lcCnab.add( new GuardaCampo( txtTipoCnab, "TipoFebraban", "Tipo", ListaCampos.DB_PK, true ) );
		adicCampo( txtCodBancoCnab, 7, 30, 100, 20, "CodBanco", "Cód.banco", ListaCampos.DB_PF, txtNomeBancoCnab, true );
		adicDescFK( txtNomeBancoCnab, 110, 30, 260, 20, "NomeBanco", "Nome do banco" );		
		adicCampo( txtCodConvCnab, 7, 70, 140, 20, "CodConv", "Convênio", ListaCampos.DB_SI, false );
		adicCampo( txtVersaoCnab, 150, 70, 50, 20, "VerLayout", "Versão", ListaCampos.DB_SI, false );
		adicCampo( txtIdentServCnab, 203, 70, 100, 20, "IdentServ", "Ident. Serviço", ListaCampos.DB_SI, false );
		adicCampo( txtNroSeqCnab, 306, 70, 63, 20, "NroSeq", "Sequência", ListaCampos.DB_SI, false );
		adicCampo( txtNumContaCnab, 7, 110, 80, 20, "NumConta", "Nº da conta", ListaCampos.DB_FK, true );
		adicDescFK( txtAgenciaCnab, 90, 110, 57, 20, "AgenciaConta", "Agência" );
		adicDescFK( txtDescContaCnab, 150, 110, 220, 20, "DescConta", "Descrição da conta" );
		adicCampo( txtContaComprCnab, 7, 150, 140, 20, "ContaCompr", "Conta Compromisso", ListaCampos.DB_SI, false );
		adicCampo( txtModalidadeCnab, 150, 150, 100, 20, "MdeCob", "Modalidade", ListaCampos.DB_SI, false );
		adicCampo( txtConvBol, 253, 150, 117, 20, "ConvCob", "Convênio boleto", ListaCampos.DB_SI, false );
		
		adicDB( rgIdentAmbCliCnab, 7, 190, 178, 60, "IdentAmbCli", "Ambiente do cliente", false );
		adicDB( rgIdentAmbBcoCnab, 193, 190, 178, 60, "IdentAmbBco", "Ambiente do banco", false );
		
		panelNavCnab.setPreferredSize( new Dimension( 300, 30 ) );
		panelNavCnab.setBorder( BorderFactory.createEtchedBorder() );
		panelNavCnab.add( nvCnab, BorderLayout.WEST );	
		
		/****************/
		
		/*** ABA PREF ***/

		tbCnab.add( "preferências", panelCnabPref );		
		panelCnabPref.add( new JScrollPane( panelCamposPref ), BorderLayout.CENTER );
		setPainel( panelCamposPref );
		
		adicDB( cbFormaCadastramento, 10, 20, 320, 20, "FORCADTIT", "Forma de cadastramento do titulo no banco", false );
		adicDB( cbTipoDocumento, 10, 60, 320, 20, "TIPODOC", "Tipo de documento", false );
		adicDB( cbEmissaoBloqueto, 10, 100, 320, 20, "IDENTEMITBOL", "Indentificação da emissão do bloqueto", false );
		adicDB( cbDistribuicao, 10, 140, 320, 20, "IDENTDISTBOL", "Indentificação da distribuição", false );
		adicDB( cbEspecieTitulo, 10, 180, 320, 20, "ESPECTIT", "Espécie do titulo", false );
		adicDB( cbJurosMora, 10, 220, 250, 20, "CODJUROS", "Indentificação para cobrança de juros", false );
		adicDB( txtVlrJuros, 270, 220, 60, 20, "VLRPERCJUROS", "Valor/%", false );
		adicDB( cbDesconto, 10, 260, 250, 20, "CODDESC", "Indentificação para consessão de desconto", false );
		adicDB( txtVlrDesconto, 270, 260, 60, 20, "VLRPERCDESC", "Valor/%", false );
		adicDB( cbProtesto, 10, 300, 250, 20, "CODPROT", "Instrução de protesto", false );
		adicDB( txtNumDiasProtesto, 270, 300, 60, 20, "DIASPROT", "Dias", false );
		adicDB( cbDevolucao, 10, 340, 250, 20, "CODBAIXADEV", "Código para devolução", false );
		adicDB( txtNumDiasDevolucao, 270, 340, 60, 20, "DIASBAIXADEV", "Dias", false );
		adicDB( cbAceite, 10, 380, 320, 20, "ACEITE", "Aceite", false );
		
		/****************/
		
		setListaCampos( false, "ITPREFERE6", "SG" );
		lcCnab.setWhereAdic( " TIPOFEBRABAN='02' " );
	}

	public void afterCarrega( CarregaEvent e ) { }

	public void beforeCarrega( CarregaEvent e ) {

		if ( e.getListaCampos() == lcCampos ) {

			txtTipoCnab.setVlrString( TP_CNAB );
			txtTipoSiacc.setVlrString( TP_SIACC );
		}
	}

	public void beforePost( PostEvent e ) {

		if ( e.getListaCampos() == lcSiacc ) {
			
			txtTipoSiacc.setVlrString( TP_SIACC );
		}
		else if ( e.getListaCampos() == lcCnab ) {
			
			txtTipoCnab.setVlrString( TP_CNAB );
		}
	}
	
	public void setConexao( DbConnection cn ) {

		super.setConexao( cn );
		
		lcSiacc.setConexao( cn );
		lcCnab.setConexao( cn );
		lcBancoSiacc.setConexao( cn );
		lcBancoCnab.setConexao( cn );
		lcContaCnab.setConexao( cn );
		lcContaSiacc.setConexao( cn );

		lcCampos.carregaDados();
	}
}
