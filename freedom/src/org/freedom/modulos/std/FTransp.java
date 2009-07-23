/**
 * @version 02/11/2003 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 * 
 *         Projeto: Freedom <BR>
 * 
 *         Pacote: org.freedom.modulos.std <BR>
 *         Classe: @(#)FTransp.java <BR>
 * 
 * Este arquivo é parte do sistema Freedom-ERP, o Freedom-ERP é um software livre; você pode redistribui-lo e/ou <BR>
 * modifica-lo dentro dos termos da Licença Pública Geral GNU como publicada pela Fundação do Software Livre (FSF); <BR>
 * na versão 2 da Licença, ou (na sua opnião) qualquer versão. <BR>
 * Este programa é distribuido na esperança que possa ser  util, mas SEM NENHUMA GARANTIA; <BR>
 * sem uma garantia implicita de ADEQUAÇÂO a qualquer MERCADO ou APLICAÇÃO EM PARTICULAR. <BR>
 * Veja a Licença Pública Geral GNU para maiores detalhes. <BR>
 * Você deve ter recebido uma cópia da Licença Pública Geral GNU junto com este programa, se não, <BR>
 *         de acordo com os termos da LPG-PC <BR>
 * <BR>
 * 
 *         Comentários sobre a classe...
 * 
 */

package org.freedom.modulos.std;

import java.awt.event.ActionEvent;
import org.freedom.infra.model.jdbc.DbConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.swing.JButton;
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
import org.freedom.componentes.Endereco;
import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JRadioGroup;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FDados;
import org.freedom.telas.FPrinterJob;
import org.freedom.webservices.WSCep;

public class FTransp extends FDados implements PostListener, RadioGroupListener, InsertListener, CarregaListener {

	private static final long serialVersionUID = 1L;

	private JTextFieldPad txtCodTran = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 5, 0 );

	private JTextFieldPad txtRazTran = new JTextFieldPad( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtNomeTran = new JTextFieldPad( JTextFieldPad.TP_STRING, 40, 0 );

	private JTextFieldPad txtEndTran = new JTextFieldPad( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtNumTran = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtCnpjTran = new JTextFieldPad( JTextFieldPad.TP_STRING, 14, 0 );

	private JTextFieldPad txtCpfTran = new JTextFieldPad( JTextFieldPad.TP_STRING, 11, 0 );

	private JTextFieldPad txtInscTran = new JTextFieldPad( JTextFieldPad.TP_STRING, 20, 0 );

	private JTextFieldFK txtDDDMun = new JTextFieldFK( JTextFieldPad.TP_STRING, 4, 0 );
	
	private JTextFieldPad txtBairTran = new JTextFieldPad( JTextFieldPad.TP_STRING, 30, 0 );

	private JTextFieldPad txtComplTran = new JTextFieldPad( JTextFieldPad.TP_STRING, 20, 0 );

	private JTextFieldPad txtCidTran = new JTextFieldPad( JTextFieldPad.TP_STRING, 30, 0 );

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

	private ListaCampos lcPais = new ListaCampos( this );

	private ListaCampos lcForneced = new ListaCampos( this, "FR" );

	private JTextFieldPad txtCodPais = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtDescPais = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtSiglaUF = new JTextFieldPad( JTextFieldPad.TP_STRING, 2, 0 );

	private JTextFieldFK txtNomeUF = new JTextFieldFK( JTextFieldPad.TP_STRING, 80, 0 );

	private JTextFieldPad txtCodMun = new JTextFieldPad( JTextFieldPad.TP_STRING, 8, 0 );

	private JTextFieldFK txtDescMun = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JButton btBuscaEnd = new JButton( Icone.novo( "btBuscacep.gif" ) );

	private JTextFieldPad txtCodFor = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtRazFor = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	public FTransp() {

		super();
		setTitulo( "Cadastro de Tranportadoras" );
		setAtribos( 50, 50, 440, 570 );

		lcCampos.addInsertListener( this );
		lcCampos.addPostListener( this );

		vTipoTransp.addElement( "Transportadora" );
		vTipoTransp.addElement( "Cliente" );
		vTipoTransp.addElement( "Fornecedor" );
		vTipoTranspVal.addElement( "T" );
		vTipoTranspVal.addElement( "C" );
		vTipoTranspVal.addElement( "F" );
		rgTipoTransp = new JRadioGroup<String, String>( 3, 1, vTipoTransp, vTipoTranspVal );
		rgTipoTransp.setVlrString( "T" );

		montaListaCampos();

		rgTipoTransp.addRadioGroupListener( this );
		btPrevimp.addActionListener( this );
		lcMunic.addCarregaListener( this );

		setImprimir( true );

	}

	private void montaTela() {

		adicCampo( txtCodTran, 7, 20, 70, 20, "CodTran", "Cód.tran.", ListaCampos.DB_PK, true );
		adicCampo( txtRazTran, 80, 20, 205, 20, "RazTran", "Razão social da transportadora", ListaCampos.DB_SI, true );
		adicDB( rgTipoTransp, 290, 20, 129, 100, "TipoTran", "Tipo", true );
		adicCampo( txtNomeTran, 7, 60, 278, 20, "NomeTran", "Nome fantasia", ListaCampos.DB_SI, true );
		adicCampo( txtCnpjTran, 7, 100, 125, 20, "CnpjTran", "Cnpj", ListaCampos.DB_SI, false );
		adicCampo( txtInscTran, 135, 100, 150, 20, "InscTran", "Inscrição Estadual", ListaCampos.DB_SI, false );
		adicCampo( txtCpfTran, 7, 140, 160, 20, "CpfTran", "CPF", ListaCampos.DB_SI, false );
		adicCampo( txtCepTran, 7, 180, 75, 20, "CepTran", "Cep", ListaCampos.DB_SI, false );
		adic( btBuscaEnd, 85, 180, 20, 20 );
		adicCampo( txtEndTran, 108, 180, 250, 20, "EndTran", "Endereço", ListaCampos.DB_SI, false );
		adicCampo( txtNumTran, 361, 180, 58, 20, "NumTran", "Número", ListaCampos.DB_SI, false );
		adicCampo( txtComplTran, 163, 220, 120, 20, "ComplTran", "Complemento", ListaCampos.DB_SI, false );
		adicCampo( txtBairTran, 286, 220, 130, 20, "BairTran", "Bairro", ListaCampos.DB_SI, false );

		adicCampo( txtDDDFoneTran, 7, 260, 80, 20, "DDDFoneTran", "DDD", ListaCampos.DB_SI, false );
		adicCampo( txtFoneTran, 90, 260, 110, 20, "FoneTran", "Telefone", ListaCampos.DB_SI, false );
		adicCampo( txtDDDFaxTran, 203, 260, 80, 20, "DDDFaxTran", "DDD", ListaCampos.DB_SI, false );
		adicCampo( txtFaxTran, 286, 260, 134, 20, "FaxTran", "Fax", ListaCampos.DB_SI, false );
		adicCampo( txtContTran, 7, 300, 240, 20, "Conttran", "Contato", ListaCampos.DB_SI, false );
		adicCampo( txtDDDCelTran, 250, 300, 50, 20, "DDDCelTran", "DDD", ListaCampos.DB_SI, false );
		adicCampo( txtCelTran, 303, 300, 117, 20, "Celtran", "Celular", ListaCampos.DB_SI, false );

		adicCampo( txtCodFor, 7, 340, 70, 20, "CodFor", "Cod.Forn.", ListaCampos.DB_FK, false );
		adicDescFK( txtRazFor, 80, 340, 337, 20, "RazFor", "Razão social do fornecedor" );

		if ( (Boolean) prefs.get( "BUSCACEP" ) ) {
			btBuscaEnd.setEnabled( true );

		}
		else {
			btBuscaEnd.setEnabled( false );
		}

		btBuscaEnd.addActionListener( this );
		btBuscaEnd.setToolTipText( "Busca Endereço a partir do CEP" );

		if ( (Boolean) prefs.get( "USAIBGETRANSP" ) ) {

			adicCampo( txtCodPais, 7, 380, 70, 20, "CodPais", "Cod.país", ListaCampos.DB_FK, true );
			adicDescFK( txtDescPais, 80, 380, 337, 20, "DescPais", "Nome do país" );
			adicCampo( txtSiglaUF, 7, 420, 70, 20, "SiglaUf", "Sigla UF", ListaCampos.DB_FK, true );
			adicDescFK( txtNomeUF, 80, 420, 337, 20, "NomeUF", "Nome UF" );
			adicCampo( txtCodMun, 7, 460, 70, 20, "CodMunic", "Cod.munic.", ListaCampos.DB_FK, false );
			adicDescFK( txtDescMun, 80, 460, 337, 20, "NomeMunic", "Nome do municipio" );

		}
		else {
			adicCampo( txtCidTran, 7, 220, 100, 20, "CidTran", "Cidade", ListaCampos.DB_SI, false );
			adicCampo( txtUFTran, 110, 220, 50, 20, "UFTran", "UF", ListaCampos.DB_SI, false );
		}

		txtCnpjTran.setMascara( JTextFieldPad.MC_CNPJ );
		txtCpfTran.setMascara( JTextFieldPad.MC_CPF );
		txtCepTran.setMascara( JTextFieldPad.MC_CEP );
		txtFoneTran.setMascara( JTextFieldPad.MC_FONE );
		txtFaxTran.setMascara( JTextFieldPad.MC_FONE );
		txtCelTran.setMascara( JTextFieldPad.MC_FONE );
		setListaCampos( true, "TRANSP", "VD" );
		lcCampos.setQueryInsert( false );

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
		txtCodPais.setTabelaExterna( lcPais );

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
		txtSiglaUF.setTabelaExterna( lcUF );

		/***************
		 * MUNICIPIO *
		 **************/

		lcMunic.setUsaME( false );
		lcMunic.add( new GuardaCampo( txtCodMun, "CodMunic", "Cód.Muni", ListaCampos.DB_PK, true ) );
		lcMunic.add( new GuardaCampo( txtDescMun, "NomeMunic", "Nome Muni.", ListaCampos.DB_SI, false ) );
		lcMunic.add( new GuardaCampo( txtDDDMun, "DDDMunic", "DDD Munic.", ListaCampos.DB_SI, false ) );		
		lcMunic.setDinWhereAdic( "SIGLAUF = #S", txtSiglaUF );
		lcMunic.montaSql( false, "MUNICIPIO", "SG" );
		lcMunic.setQueryCommit( false );
		lcMunic.setReadOnly( true );
		txtCodMun.setTabelaExterna( lcMunic );

		/***************
		 * FORNECEDOR *
		 **************/

		lcForneced.setUsaME( true );
		lcForneced.add( new GuardaCampo( txtCodFor, "CodFor", "Cod.Forn.", ListaCampos.DB_PK, false ) );
		lcForneced.add( new GuardaCampo( txtRazFor, "RazFor", "Razão social do fornecedor", ListaCampos.DB_SI, false ) );
		lcForneced.montaSql( false, "FORNECED", "CP" );
		lcForneced.setQueryCommit( false );
		lcForneced.setReadOnly( true );
		txtCodFor.setTabelaExterna( lcForneced );

	}

	public void beforePost( PostEvent pevt ) {

		if ( ( "".equals( txtCnpjTran.getVlrString().trim() ) ) && ( "".equals( txtCpfTran.getVlrString().trim() ) ) ) {
			pevt.cancela();
			Funcoes.mensagemInforma( this, "Campo CNPJ é requerido! ! !" );
			txtCnpjTran.requestFocus();
		}

		if ( ( "".equals( txtInscTran.getVlrString().trim() ) ) && ( !"".equals( txtCnpjTran.getVlrString().trim() ) ) ) {
			if ( Funcoes.mensagemConfirma( this, "Inscrição Estadual em branco! Inserir ISENTA?" ) == JOptionPane.OK_OPTION ) {
				txtInscTran.setVlrString( "ISENTA" );
			}
			pevt.cancela();
			txtInscTran.requestFocus();
			return;
		}
		else if ( txtInscTran.getText().trim().toUpperCase().compareTo( "ISENTA" ) == 0 )
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
		else if ( ( !"".equals( txtInscTran.getVlrString().trim() ) ) && ( !Funcoes.vIE( txtInscTran.getText(), txtUFTran.getText() ) ) ) {
			pevt.cancela();
			Funcoes.mensagemInforma( this, "Inscrição Estadual Inválida ! ! !" );
			txtInscTran.requestFocus();
		}
		txtInscTran.setVlrString( Funcoes.sIEValida );
	}

	public void afterPost( PostEvent pevt ) {

	}

	public void setConexao( DbConnection cn ) {

		super.setConexao( cn );

		lcMunic.setConexao( cn );
		lcPais.setConexao( cn );
		lcUF.setConexao( cn );
		lcForneced.setConexao( cn );
		prefs = getPrefs();
		montaTela();
	}

	public void valorAlterado( RadioGroupEvent rgevt ) {

		if ( rgTipoTransp.getVlrString().equals( "C" ) )
			carregaCnpj();
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
			txtCodMun.setEnabled( false );
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
						txtCodMun.setVlrString( endereco.getCodmunic() );

						lcPais.carregaDados();
						lcUF.carregaDados();
						lcMunic.carregaDados();
						
						txtNumTran.requestFocus();
					} 
					catch ( Exception e ) {
						Funcoes.mensagemInforma( null, "Não foi encontrado o endereço para o CEP informado!" );
					} 
					finally {
						txtEndTran.setEnabled( true );
						txtComplTran.setEnabled( true );
						txtBairTran.setEnabled( true );
						txtCidTran.setEnabled( true );
						txtUFTran.setEnabled( true );
						txtCodPais.setEnabled( true );
						txtSiglaUF.setEnabled( true );
						txtCodMun.setEnabled( true );		
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

			super.actionPerformed( evt );
		}
		else if ( evt.getSource() == btSair ) {
			dispose();
		}
	}

	public void afterCarrega( CarregaEvent cevt ) {

		if ( cevt.getListaCampos() == lcMunic ) {
			if("".equals( txtDDDFoneTran.getVlrString())) {
				txtDDDFoneTran.setVlrString( txtDDDMun.getVlrString() );
			}
			if("".equals( txtDDDFaxTran.getVlrString())) {
				txtDDDFaxTran.setVlrString( txtDDDMun.getVlrString() );
			}
			if("".equals( txtDDDCelTran.getVlrString())) {
				txtDDDCelTran.setVlrString( txtDDDMun.getVlrString() );
			}
		}

		
	}

	public void beforeCarrega( CarregaEvent cevt ) {}
}
