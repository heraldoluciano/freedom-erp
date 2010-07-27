/**
 * @version 02/11/2003 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 * 
 *         Projeto: Freedom <BR>
 * 
 *         Pacote: org.freedom.modulos.std <BR>
 *         Classe: @(#)FTransp.java <BR>
 * 
 *         Este arquivo é parte do sistema Freedom-ERP, o Freedom-ERP é um software livre; você pode redistribui-lo e/ou <BR>
 *         modifica-lo dentro dos termos da Licença Pública Geral GNU como publicada pela Fundação do Software Livre (FSF); <BR>
 *         na versão 2 da Licença, ou (na sua opnião) qualquer versão. <BR>
 *         Este programa é distribuido na esperança que possa ser util, mas SEM NENHUMA GARANTIA; <BR>
 *         sem uma garantia implicita de ADEQUAÇÂO a qualquer MERCADO ou APLICAÇÃO EM PARTICULAR. <BR>
 *         Veja a Licença Pública Geral GNU para maiores detalhes. <BR>
 *         Você deve ter recebido uma cópia da Licença Pública Geral GNU junto com este programa, se não, <BR>
 *         de acordo com os termos da LPG-PC <BR>
 * <BR>
 * 
 *         Comentários sobre a classe...
 * 
 */

package org.freedom.modulos.std.view.frame.crud.tabbed;

import java.awt.event.ActionEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JasperPrintManager;
import org.freedom.acao.CarregaEvent;
import org.freedom.acao.CarregaListener;
import org.freedom.acao.InsertEvent;
import org.freedom.acao.InsertListener;
import org.freedom.acao.PostEvent;
import org.freedom.acao.PostListener;
import org.freedom.acao.RadioGroupEvent;
import org.freedom.acao.RadioGroupListener;
import org.freedom.bmps.Icone;
import org.freedom.business.webservice.WSCep;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.business.object.Endereco;
import org.freedom.library.functions.Funcoes;
import org.freedom.library.persistence.GuardaCampo;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.component.JButtonPad;
import org.freedom.library.swing.component.JPanelPad;
import org.freedom.library.swing.component.JRadioGroup;
import org.freedom.library.swing.component.JTextFieldFK;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.frame.Aplicativo;
import org.freedom.library.swing.frame.FPrinterJob;
import org.freedom.library.swing.frame.FTabDados;
import org.freedom.modulos.cfg.view.frame.crud.plain.FMunicipio;
import org.freedom.modulos.cfg.view.frame.crud.plain.FPais;
import org.freedom.modulos.cfg.view.frame.crud.plain.FUF;
import org.freedom.modulos.fnc.view.frame.crud.plain.FBanco;
import org.freedom.modulos.grh.view.frame.crud.plain.FCodGPS;
import org.freedom.modulos.std.view.dialog.utility.DLTranspFor;

public class FTransp extends FTabDados implements PostListener, RadioGroupListener, InsertListener, CarregaListener {

	private static final long serialVersionUID = 1L;

	private JTextFieldPad txtCodTran = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 5, 0 );

	private JTextFieldPad txtRazTran = new JTextFieldPad( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtNomeTran = new JTextFieldPad( JTextFieldPad.TP_STRING, 40, 0 );

	private JTextFieldPad txtEndTran = new JTextFieldPad( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtNumTran = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtCnpjTran = new JTextFieldPad( JTextFieldPad.TP_STRING, 14, 0 );

	private JTextFieldPad txtCpfTran = new JTextFieldPad( JTextFieldPad.TP_STRING, 11, 0 );

	private JTextFieldPad txtInscTran = new JTextFieldPad( JTextFieldPad.TP_STRING, 20, 0 );

	private JTextFieldPad txtNroPisTran = new JTextFieldPad( JTextFieldPad.TP_STRING, 20, 0 );

	private JTextFieldFK txtDDDMun = new JTextFieldFK( JTextFieldPad.TP_STRING, 4, 0 );

	private JTextFieldPad txtBairTran = new JTextFieldPad( JTextFieldPad.TP_STRING, 30, 0 );

	private JTextFieldPad txtComplTran = new JTextFieldPad( JTextFieldPad.TP_STRING, 20, 0 );

	private JTextFieldPad txtCidTran = new JTextFieldPad( JTextFieldPad.TP_STRING, 30, 0 );

	private JTextFieldPad txtConjugeTran = new JTextFieldPad( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtCepTran = new JTextFieldPad( JTextFieldPad.TP_STRING, 8, 0 );

	private JTextFieldPad txtFoneTran = new JTextFieldPad( JTextFieldPad.TP_STRING, 12, 0 );

	private JTextFieldPad txtDDDFoneTran = new JTextFieldPad( JTextFieldPad.TP_STRING, 4, 0 );

	private JTextFieldPad txtDDDFaxTran = new JTextFieldPad( JTextFieldPad.TP_STRING, 4, 0 );

	private JTextFieldPad txtFaxTran = new JTextFieldPad( JTextFieldPad.TP_STRING, 8, 0 );

	private JTextFieldPad txtDDDCelTran = new JTextFieldPad( JTextFieldPad.TP_STRING, 4, 0 );

	private JTextFieldPad txtCelTran = new JTextFieldPad( JTextFieldPad.TP_STRING, 8, 0 );

	private JTextFieldPad txtContTran = new JTextFieldPad( JTextFieldPad.TP_STRING, 40, 0 );

	private JTextFieldPad txtUFTran = new JTextFieldPad( JTextFieldPad.TP_STRING, 2, 0 );

	private Vector<String> vTipoTransp = new Vector<String>();

	private Vector<String> vTipoTranspVal = new Vector<String>();

	private JRadioGroup<?, ?> rgTipoTransp = null;

	private Map<String, Object> prefs = null;

	private ListaCampos lcUF = new ListaCampos( this );

	private ListaCampos lcMunic = new ListaCampos( this );

	private ListaCampos lcGPS = new ListaCampos( this );

	private ListaCampos lcBanco = new ListaCampos( this, "BO" );

	private ListaCampos lcPais = new ListaCampos( this );

	private ListaCampos lcForneced = new ListaCampos( this, "FR" );

	private JTextFieldPad txtCodPais = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtDescPais = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtSiglaUF = new JTextFieldPad( JTextFieldPad.TP_STRING, 2, 0 );

	private JTextFieldFK txtNomeUF = new JTextFieldFK( JTextFieldPad.TP_STRING, 80, 0 );

	private JTextFieldPad txtCodMunic = new JTextFieldPad( JTextFieldPad.TP_STRING, 7, 0 );

	private JTextFieldFK txtDescMun = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtCodGPS = new JTextFieldPad( JTextFieldPad.TP_STRING, 4, 0 );

	private JTextFieldFK txtDescGPS = new JTextFieldFK( JTextFieldPad.TP_STRING, 80, 0 );

	private JButtonPad btBuscaEnd = new JButtonPad( Icone.novo( "btBuscacep.gif" ) );

	private JTextFieldPad txtCodFor = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtRazFor = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtRgTran = new JTextFieldPad( JTextFieldPad.TP_STRING, 10, 0 );

	private JTextFieldPad txtSSPTran = new JTextFieldPad( JTextFieldPad.TP_STRING, 10, 0 );

	private final JPanelPad panelGeral = new JPanelPad();

	private final JPanelPad panelAutonomo = new JPanelPad();

	private JTextFieldPad txtCodBanco = new JTextFieldPad( JTextFieldPad.TP_STRING, 3, 0 );

	private JTextFieldFK txtNomeBanco = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtAgenciaTran = new JTextFieldPad( JTextFieldPad.TP_STRING, 10, 0 );

	private JTextFieldPad txtNumContaTran = new JTextFieldPad( JTextFieldPad.TP_STRING, 10, 0 );

	private JTextFieldPad txtPlacaTran = new JTextFieldPad( JTextFieldPad.TP_STRING, 7, 0 );

	private JTextFieldPad txtNroDependTran = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 3, 0 );

	public FTransp() {

		super();
		setTitulo( "Cadastro de Tranportadoras" );
		setAtribos( 50, 50, 580, 580 );

		lcCampos.addInsertListener( this );
		lcCampos.addPostListener( this );

		vTipoTransp.addElement( "Transportadora" );
		vTipoTransp.addElement( "Cliente" );
		vTipoTransp.addElement( "Fornecedor" );
		vTipoTransp.addElement( "Autônomo" );

		vTipoTranspVal.addElement( "T" );
		vTipoTranspVal.addElement( "C" );
		vTipoTranspVal.addElement( "F" );
		vTipoTranspVal.addElement( "A" );

		rgTipoTransp = new JRadioGroup<String, String>( 4, 1, vTipoTransp, vTipoTranspVal );
		rgTipoTransp.setVlrString( "T" );

		montaListaCampos();

		rgTipoTransp.addRadioGroupListener( this );
		btPrevimp.addActionListener( this );
		lcMunic.addCarregaListener( this );

		setImprimir( true );

	}

	private void montaTela() {

		adicTab( "Geral", panelGeral );
		setPainel( panelGeral );

		nav.setNavigation( true );

		adicCampo( txtCodTran, 7, 20, 70, 20, "CodTran", "Cód.Transp.", ListaCampos.DB_PK, true );

		adicCampo( txtRazTran, 80, 20, 322, 20, "RazTran", "Razão social da transportadora", ListaCampos.DB_SI, true );

		adicDB( rgTipoTransp, 407, 20, 144, 100, "TipoTran", "Tipo", true );

		adicCampo( txtNomeTran, 7, 60, 395, 20, "NomeTran", "Nome fantasia", ListaCampos.DB_SI, true );

		adicCampo( txtCnpjTran, 7, 100, 125, 20, "CnpjTran", "Cnpj", ListaCampos.DB_SI, false );

		adicCampo( txtInscTran, 135, 100, 125, 20, "InscTran", "Inscrição Estadual", ListaCampos.DB_SI, false );

		adicCampo( txtCepTran, 7, 140, 75, 20, "CepTran", "Cep", ListaCampos.DB_SI, false );

		adic( btBuscaEnd, 85, 140, 20, 20 );

		adicCampo( txtEndTran, 108, 140, 250, 20, "EndTran", "Endereço", ListaCampos.DB_SI, false );

		adicCampo( txtNumTran, 361, 140, 58, 20, "NumTran", "Número", ListaCampos.DB_SI, false );

		adicCampo( txtComplTran, 422, 140, 128, 20, "ComplTran", "Complemento", ListaCampos.DB_SI, false );

		adicCampo( txtBairTran, 7, 180, 253, 20, "BairTran", "Bairro", ListaCampos.DB_SI, false );

		adicCampo( txtContTran, 263, 180, 287, 20, "Conttran", "Contato", ListaCampos.DB_SI, false );

		adicCampo( txtDDDFoneTran, 7, 220, 50, 20, "DDDFoneTran", "DDD", ListaCampos.DB_SI, false );
		adicCampo( txtFoneTran, 60, 220, 110, 20, "FoneTran", "Telefone", ListaCampos.DB_SI, false );

		adicCampo( txtDDDFaxTran, 173, 220, 50, 20, "DDDFaxTran", "DDD", ListaCampos.DB_SI, false );
		adicCampo( txtFaxTran, 226, 220, 132, 20, "FaxTran", "Fax", ListaCampos.DB_SI, false );

		adicCampo( txtDDDCelTran, 361, 220, 58, 20, "DDDCelTran", "DDD", ListaCampos.DB_SI, false );

		adicCampo( txtCelTran, 422, 220, 128, 20, "Celtran", "Celular", ListaCampos.DB_SI, false );

		adicCampo( txtCodFor, 7, 260, 75, 20, "CodFor", "Cod.Forn.", ListaCampos.DB_FK, false );
		adicDescFK( txtRazFor, 85, 260, 464, 20, "RazFor", "Razão social do fornecedor" );

		adicCampo( txtCodBanco, 7, 300, 75, 20, "CodBanco", "Cód.banco", ListaCampos.DB_FK, txtNomeBanco, false );
		adicDescFK( txtNomeBanco, 85, 300, 272, 20, "NomeBanco", "Nome do banco" );

		adicCampo( txtAgenciaTran, 361, 300, 58, 20, "Agenciatran", "Agencia", ListaCampos.DB_SI, false );

		adicCampo( txtNumContaTran, 422, 300, 128, 20, "NumContaTran", "Nro. da conta", ListaCampos.DB_SI, false );

		if ( (Boolean) prefs.get( "USAIBGETRANSP" ) ) {

			adicCampo( txtCodPais, 7, 340, 75, 20, "CodPais", "Cod.país", ListaCampos.DB_FK, true );
			adicDescFK( txtDescPais, 85, 340, 464, 20, "DescPais", "Nome do país" );
			adicCampo( txtSiglaUF, 7, 380, 75, 20, "SiglaUf", "Sigla UF", ListaCampos.DB_FK, true );
			adicDescFK( txtNomeUF, 85, 380, 464, 20, "NomeUF", "Nome UF" );
			adicCampo( txtCodMunic, 7, 420, 75, 20, "CodMunic", "Cod.munic.", ListaCampos.DB_FK, false );
			adicDescFK( txtDescMun, 85, 420, 464, 20, "NomeMunic", "Nome do municipio" );

		}
		else {
			adicCampo( txtUFTran, 7, 340, 75, 20, "UFTran", "UF", ListaCampos.DB_SI, false );
			adicCampo( txtCidTran, 85, 340, 250, 20, "CidTran", "Cidade", ListaCampos.DB_SI, false );
		}

		/****************************************************************
		 * 
		 * Informações exclusivas para transportadores autônomos
		 * 
		 ****************************************************************/

		adicTab( "Autônomo", panelAutonomo );
		setPainel( panelAutonomo );

		adicCampo( txtCpfTran, 7, 20, 125, 20, "CpfTran", "CPF", ListaCampos.DB_SI, false );

		adicCampo( txtRgTran, 135, 20, 125, 20, "RgTran", "RG", ListaCampos.DB_SI, false );

		adicCampo( txtSSPTran, 263, 20, 60, 20, "SSPTran", "Org.exp.", ListaCampos.DB_SI, false );

		adicCampo( txtConjugeTran, 326, 20, 222, 20, "ConjugeTran", "Conjuge", ListaCampos.DB_SI, false );

		adicCampo( txtPlacaTran, 7, 60, 125, 20, "PlacaTran", "Placa do veículo", ListaCampos.DB_SI, false );

		adicCampo( txtNroDependTran, 135, 60, 125, 20, "NroDependTran", "Nro. de dependentes", ListaCampos.DB_SI, false );

		adicCampo( txtNroPisTran, 263, 60, 250, 20, "NroPisTran", "Cód.INSS/PIS/PASEP", ListaCampos.DB_SI, false );

		adicCampo( txtCodGPS, 7, 100, 75, 20, "CodGPS", "Cod.GPS.", ListaCampos.DB_FK, false );
		adicDescFK( txtDescGPS, 85, 100, 464, 20, "DescGPS", "Descrição do código de pagamento GPS/INSS" );

		/*******************************************************************
		 * 
		 * Fim das informações expecíficas para transportadores autônomos
		 * 
		 *******************************************************************/

		setListaCampos( true, "TRANSP", "VD" );

		lcCampos.setQueryInsert( false );

		if ( (Boolean) prefs.get( "BUSCACEP" ) ) {

			btBuscaEnd.setEnabled( true );

		}
		else {

			btBuscaEnd.setEnabled( false );

		}

		btBuscaEnd.addActionListener( this );
		btBuscaEnd.setToolTipText( "Busca Endereço a partir do CEP" );

		txtPlacaTran.setUpperCase( true );

		txtCnpjTran.setMascara( JTextFieldPad.MC_CNPJ );
		txtCpfTran.setMascara( JTextFieldPad.MC_CPF );
		txtCepTran.setMascara( JTextFieldPad.MC_CEP );
		txtFoneTran.setMascara( JTextFieldPad.MC_FONE );
		txtFaxTran.setMascara( JTextFieldPad.MC_FONE );
		txtCelTran.setMascara( JTextFieldPad.MC_FONE );
		txtPlacaTran.setMascara( JTextFieldPad.MC_PLACA );
		txtNroPisTran.setMascara( JTextFieldPad.MC_INSS );

	}

	private void montaListaCampos() {

		/***************
		 * PAÍS *
		 **************/

		lcPais.setUsaME( false );
		lcPais.add( new GuardaCampo( txtCodPais, "CodPais", "Cod.país.", ListaCampos.DB_PK, true ) );
		lcPais.add( new GuardaCampo( txtDescPais, "NomePais", "Nome", ListaCampos.DB_SI, false ) );
		lcPais.montaSql( false, "PAIS", "SG" );
		lcPais.setQueryCommit( false );
		lcPais.setReadOnly( true );
		txtCodPais.setTabelaExterna( lcPais, FPais.class.getCanonicalName() );

		/***************
		 * UF *
		 **************/

		lcUF.setUsaME( false );
		lcUF.add( new GuardaCampo( txtSiglaUF, "SiglaUf", "Sigla", ListaCampos.DB_PK, true ) );
		lcUF.add( new GuardaCampo( txtNomeUF, "NomeUf", "Nome", ListaCampos.DB_SI, false ) );
		lcMunic.setDinWhereAdic( "CODPAIS = #S", txtCodPais );
		lcUF.montaSql( false, "UF", "SG" );
		lcUF.setQueryCommit( false );
		lcUF.setReadOnly( true );
		txtSiglaUF.setTabelaExterna( lcUF, FUF.class.getCanonicalName() );

		/***************
		 * MUNICIPIO *
		 **************/

		lcMunic.setUsaME( false );
		lcMunic.add( new GuardaCampo( txtCodMunic, "CodMunic", "Cód.Muni", ListaCampos.DB_PK, true ) );
		lcMunic.add( new GuardaCampo( txtDescMun, "NomeMunic", "Nome Muni.", ListaCampos.DB_SI, false ) );
		lcMunic.add( new GuardaCampo( txtDDDMun, "DDDMunic", "DDD Munic.", ListaCampos.DB_SI, false ) );
		lcMunic.setDinWhereAdic( "SIGLAUF = #S", txtSiglaUF );
		lcMunic.montaSql( false, "MUNICIPIO", "SG" );
		lcMunic.setQueryCommit( false );
		lcMunic.setReadOnly( true );
		txtCodMunic.setTabelaExterna( lcMunic, FMunicipio.class.getCanonicalName() );

		/***************
		 * FORNECEDOR *
		 **************/

		lcForneced.setUsaME( true );
		lcForneced.add( new GuardaCampo( txtCodFor, "CodFor", "Cod.Forn.", ListaCampos.DB_PK, false ) );
		lcForneced.add( new GuardaCampo( txtRazFor, "RazFor", "Razão social do fornecedor", ListaCampos.DB_SI, false ) );
		lcForneced.montaSql( false, "FORNECED", "CP" );
		lcForneced.setQueryCommit( false );
		lcForneced.setReadOnly( true );
		txtCodFor.setTabelaExterna( lcForneced, FFornecedor.class.getCanonicalName() );

		/***************
		 * GPS *
		 **************/

		lcGPS.setUsaME( false );
		lcGPS.add( new GuardaCampo( txtCodGPS, "CodGPS", "Cód.GPS", ListaCampos.DB_PK, false ) );
		lcGPS.add( new GuardaCampo( txtDescGPS, "DescGPS", "Descrição do código de pagamento GPS/INSS", ListaCampos.DB_SI, false ) );
		lcGPS.montaSql( false, "CODGPS", "RH" );
		lcGPS.setQueryCommit( false );
		lcGPS.setReadOnly( true );
		txtCodGPS.setTabelaExterna( lcGPS, FCodGPS.class.getCanonicalName() );

		/***************
		 * BANCO *
		 **************/

		lcBanco.add( new GuardaCampo( txtCodBanco, "CodBanco", "Cód.banco", ListaCampos.DB_PK, false ) );
		lcBanco.add( new GuardaCampo( txtNomeBanco, "NomeBanco", "Nome do banco", ListaCampos.DB_SI, false ) );
		lcBanco.montaSql( false, "BANCO", "FN" );
		lcBanco.setQueryCommit( false );
		lcBanco.setReadOnly( true );
		txtCodBanco.setTabelaExterna( lcBanco, FBanco.class.getCanonicalName() );

	}

	public void beforePost( PostEvent pevt ) {

		if ( (Boolean) prefs.get( "USAIBGETRANSP" ) ) {
			txtUFTran.setVlrString( txtSiglaUF.getVlrString() );
		}

		if ( ( "".equals( txtCnpjTran.getVlrString().trim() ) ) && !"A".equals( rgTipoTransp.getVlrString() ) ) {
			pevt.cancela();
			Funcoes.mensagemInforma( this, "Campo CNPJ é requerido! ! !" );
			txtCnpjTran.requestFocus();
		}

		if ( ( "".equals( txtInscTran.getVlrString().trim() ) ) && ( !"".equals( txtCnpjTran.getVlrString().trim() ) ) ) {
			if ( Funcoes.mensagemConfirma( this, "Inscrição Estadual em branco! Inserir ISENTO?" ) == JOptionPane.OK_OPTION ) {
				txtInscTran.setVlrString( "ISENTO" );
			}
			pevt.cancela();
			txtInscTran.requestFocus();
			return;
		}
		else if ( txtInscTran.getText().trim().toUpperCase().compareTo( "ISENTO" ) == 0 )
			return;
		else if ( txtUFTran.getText().trim().length() < 2 ) {
			if ( (Boolean) prefs.get( "USAIBGETRANSP" ) ) {
				txtUFTran.setVlrString( txtSiglaUF.getVlrString() );
			}
			else {
				pevt.cancela();
				Funcoes.mensagemInforma( this, "Campo UF é requerido! ! !" );
				txtUFTran.requestFocus();
			}
		}
		else if ( ( !"".equals( txtInscTran.getVlrString().trim() ) ) && ( !Funcoes.validaIE( txtInscTran.getText(), txtUFTran.getText() ) ) ) {
			pevt.cancela();
			Funcoes.mensagemInforma( this, "Inscrição Estadual Inválida ! ! !" );
			txtInscTran.requestFocus();
		}

		txtInscTran.setVlrString( Funcoes.formataIE( txtInscTran.getVlrString(), txtUFTran.getVlrString() ) );

	}

	public void afterPost( PostEvent pevt ) {

	}

	public void setConexao( DbConnection cn ) {

		super.setConexao( cn );

		lcMunic.setConexao( cn );
		lcPais.setConexao( cn );
		lcUF.setConexao( cn );
		lcForneced.setConexao( cn );
		lcGPS.setConexao( cn );
		lcBanco.setConexao( cn );

		prefs = getPrefs();

		montaTela();
	}

	public void valorAlterado( RadioGroupEvent rgevt ) {

		if ( rgTipoTransp.getVlrString().equals( "C" ) ) {
			carregaCnpj();
		}
		else if ( rgTipoTransp.getVlrString().equals( "A" ) ) {

		}
	}

	public void carregaCnpj() {

		String sSQL = "SELECT CNPJFILIAL, INSCFILIAL, UFFILIAL FROM SGFILIAL WHERE CODEMP=? AND CODFILIAL=?";
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {

			ps = con.prepareStatement( sSQL );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, Aplicativo.iCodFilial );
			rs = ps.executeQuery();

			if ( rs.next() ) {
				txtCnpjTran.setVlrString( rs.getString( 1 ) );
				txtInscTran.setVlrString( rs.getString( 2 ) );
				txtUFTran.setVlrString( rs.getString( 3 ) );
				txtRazTran.setVlrString( "O MESMO" );
				txtNomeTran.setVlrString( "O MESMO" );
				txtEndTran.setVlrString( "O MESMO" );
				txtNumTran.setVlrString( "" );
				txtComplTran.setVlrString( "" );
				txtBairTran.setVlrString( "O MESMO" );
				txtCidTran.setVlrString( "O MESMO" );
				txtFoneTran.setVlrString( "" );
				txtFaxTran.setVlrString( "" );

			}

		} catch ( SQLException err ) {
			Funcoes.mensagemErro( this, "Erro ao carregar tabela SGFILIAL!\n" + err.getMessage(), true, con, err );
			return;
		}
	}

	public void afterInsert( InsertEvent ievt ) {

		rgTipoTransp.setVlrString( "T" );
	}

	public void beforeInsert( InsertEvent ievt ) {

	}

	public void imprimir( boolean bVisualizar ) {

		StringBuilder sSQL = new StringBuilder();
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sCab = "";
		DLTranspFor dl = new DLTranspFor();

		sSQL.append( "SELECT T.CODTRAN, T.NOMETRAN, T.ENDTRAN, T.CIDTRAN, T.NUMTRAN, " );
		sSQL.append( "T.BAIRTRAN, T.DDDFONETRAN, T.FONETRAN, T.FAXTRAN, T.CEPTRAN, " );
		sSQL.append( "T.CONTTRAN,  T.CNPJTRAN, T.UFTRAN, T.CPFTRAN, T.TIPOTRAN FROM VDTRANSP T ORDER BY " + dl.getValor() );

		try {

			dl.setVisible( true );
			if ( dl.OK == false ) {
				dl.dispose();
				return;
			}

			ps = con.prepareStatement( sSQL.toString() );
			rs = ps.executeQuery();

			dl.dispose();

			imprimeGrafico( rs, bVisualizar, sCab );

		} catch ( SQLException e ) {

			Funcoes.mensagemErro( this, "Erro ao buscar dados !\n" + e.getMessage() );
			e.printStackTrace();
		}
	}

	public void imprimeGrafico( final ResultSet rs, final boolean bVisualizar, final String sCab ) {

		HashMap<String, Object> hParam = new HashMap<String, Object>();

		hParam.put( "CODEMP", Aplicativo.iCodEmp );
		// hParam.put( "CODFILIAL", ListaCampos.getMasterFilial( "VDVENDA" ));
		hParam.put( "FILTROS", sCab );

		FPrinterJob dlGr = new FPrinterJob( "relatorios/FRTrans.jasper", "Transportadoras", null, rs, hParam, this );

		if ( bVisualizar ) {

			dlGr.setVisible( true );

		}
		else {
			try {

				JasperPrintManager.printReport( dlGr.getRelatorio(), true );

			} catch ( Exception err ) {

				Funcoes.mensagemErro( this, "Erro na impressão do relatório de Transportadoras!\n" + err.getMessage(), true, con, err );
			}
		}
	}

	private Map<String, Object> getPrefs() {

		Map<String, Object> retorno = new HashMap<String, Object>();
		StringBuilder sSQL = new StringBuilder();
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			sSQL.append( "SELECT USAIBGETRANSP, BUSCACEP FROM SGPREFERE1 WHERE CODEMP=? AND CODFILIAL=?" );

			try {

				ps = con.prepareStatement( sSQL.toString() );
				ps.setInt( 1, Aplicativo.iCodEmp );
				ps.setInt( 2, Aplicativo.iCodFilial );
				rs = ps.executeQuery();

				if ( rs.next() ) {

					retorno.put( "USAIBGETRANSP", new Boolean( "S".equals( rs.getString( "USAIBGETRANSP" ) ) ) );
					retorno.put( "BUSCACEP", new Boolean( "S".equals( rs.getString( "BUSCACEP" ) ) ) );
				}

				rs.close();
				ps.close();

				con.commit();

			} catch ( SQLException err ) {
				Funcoes.mensagemErro( this, "Erro ao verificar preferências!\n" + err.getMessage(), true, con, err );
				err.printStackTrace();
			}

		} finally {

			sSQL = null;
			ps = null;
			rs = null;
		}
		return retorno;
	}

	private void buscaEndereco() {

		if ( !"".equals( txtCepTran.getVlrString() ) ) {

			txtEndTran.setEnabled( false );
			txtComplTran.setEnabled( false );
			txtBairTran.setEnabled( false );
			txtCidTran.setEnabled( false );
			txtUFTran.setEnabled( false );

			txtCodPais.setEnabled( false );
			txtSiglaUF.setEnabled( false );
			txtCodMunic.setEnabled( false );
			txtDDDFoneTran.setEnabled( false );
			txtDDDFaxTran.setEnabled( false );
			txtDDDCelTran.setEnabled( false );

			Thread th = new Thread( new Runnable() {

				public void run() {

					try {
						WSCep cep = new WSCep();
						cep.setCon( con );
						cep.setCep( txtCepTran.getVlrString() );
						cep.busca();
						Endereco endereco = cep.getEndereco();

						txtEndTran.setVlrString( endereco.getTipo() + " " + endereco.getLogradouro() );
						txtComplTran.setVlrString( endereco.getComplemento() );
						txtBairTran.setVlrString( endereco.getBairro() );
						txtCidTran.setVlrString( endereco.getCidade() );
						txtUFTran.setVlrString( endereco.getSiglauf() );

						txtCodPais.setVlrInteger( endereco.getCodpais() );
						txtSiglaUF.setVlrString( endereco.getSiglauf() );
						txtCodMunic.setVlrString( endereco.getCodmunic() );

						lcPais.carregaDados();
						lcUF.carregaDados();
						lcMunic.carregaDados();

						txtNumTran.requestFocus();
					} catch ( Exception e ) {
						Funcoes.mensagemInforma( null, "Não foi encontrado o endereço para o CEP informado!" );
					} finally {
						txtEndTran.setEnabled( true );
						txtComplTran.setEnabled( true );
						txtBairTran.setEnabled( true );
						txtCidTran.setEnabled( true );
						txtUFTran.setEnabled( true );
						txtCodPais.setEnabled( true );
						txtSiglaUF.setEnabled( true );
						txtCodMunic.setEnabled( true );
						txtDDDFoneTran.setEnabled( true );
						txtDDDFaxTran.setEnabled( true );
						txtDDDCelTran.setEnabled( true );
					}
				}
			} );
			try {
				th.start();
			} catch ( Exception err ) {
				Funcoes.mensagemInforma( null, "Não foi encontrado o endereço para o CEP informado!" );
				txtCepTran.requestFocus();
			}
		}
		else {
			Funcoes.mensagemInforma( null, "Digite um CEP para busca!" );
			txtCepTran.requestFocus();
		}

	}

	@ Override
	public void actionPerformed( ActionEvent evt ) {

		if ( evt.getSource() == btPrevimp ) {

			imprimir( true );
		}
		else if ( evt.getSource() == btBuscaEnd ) {
			buscaEndereco();

		}

		super.actionPerformed( evt );

	}

	public void afterCarrega( CarregaEvent cevt ) {

		if ( cevt.getListaCampos() == lcMunic ) {
			if ( "".equals( txtDDDFoneTran.getVlrString() ) ) {
				txtDDDFoneTran.setVlrString( txtDDDMun.getVlrString() );
			}
			if ( "".equals( txtDDDFaxTran.getVlrString() ) ) {
				txtDDDFaxTran.setVlrString( txtDDDMun.getVlrString() );
			}
			if ( "".equals( txtDDDCelTran.getVlrString() ) ) {
				txtDDDCelTran.setVlrString( txtDDDMun.getVlrString() );
			}
		}

	}

	public void beforeCarrega( CarregaEvent cevt ) {

	}
}
