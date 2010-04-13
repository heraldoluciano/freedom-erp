/**
 * @version 02/11/2003 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 * 
 *         Projeto: Freedom <BR>
 * 
 *         Pacote: org.freedom.modulos.atd <BR>
 *         Classe: @(#)FAtendente.java <BR>
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

package org.freedom.modulos.atd.view.frame.crud.plain;

import java.awt.event.ActionEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.freedom.bmps.Icone;
import org.freedom.business.webservice.WSCep;
import org.freedom.funcoes.Funcoes;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.business.object.Endereco;
import org.freedom.library.persistence.GuardaCampo;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.JButtonPad;
import org.freedom.library.swing.JTextFieldFK;
import org.freedom.library.swing.JTextFieldPad;
import org.freedom.library.swing.frame.Aplicativo;
import org.freedom.library.swing.frame.FDados;

public class FAtendente extends FDados {

	private static final long serialVersionUID = 1L;

	private JTextFieldPad txtCodAtend = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtNomeAtend = new JTextFieldPad( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtRgAtend = new JTextFieldPad( JTextFieldPad.TP_STRING, 10, 0 );

	private JTextFieldPad txtCpfAtend = new JTextFieldPad( JTextFieldPad.TP_STRING, 11, 0 );

	private JTextFieldPad txtIdentificAtend = new JTextFieldPad( JTextFieldPad.TP_STRING, 18, 0 );

	private JTextFieldPad txtEndAtend = new JTextFieldPad( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtNumAtend = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtBairAtend = new JTextFieldPad( JTextFieldPad.TP_STRING, 30, 0 );

	private JTextFieldPad txtCidAtend = new JTextFieldPad( JTextFieldPad.TP_STRING, 30, 0 );

	private JTextFieldPad txtCepAtend = new JTextFieldPad( JTextFieldPad.TP_STRING, 8, 0 );

	private JTextFieldPad txtFoneAtend = new JTextFieldPad( JTextFieldPad.TP_STRING, 12, 0 );

	private JTextFieldPad txtCelAtend = new JTextFieldPad( JTextFieldPad.TP_STRING, 8, 0 );

	private JTextFieldPad txtFaxAtend = new JTextFieldPad( JTextFieldPad.TP_STRING, 8, 0 );

	private JTextFieldPad txtUFAtend = new JTextFieldPad( JTextFieldPad.TP_STRING, 2, 0 );

	private JTextFieldPad txtEmailAtend = new JTextFieldPad( JTextFieldPad.TP_STRING, 60, 0 );

	private JTextFieldPad txtCodTipoAtend = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtDescTipoAtend = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtIDUsu = new JTextFieldPad( JTextFieldPad.TP_STRING, 8, 0 );

	private JTextFieldFK txtNomeUsu = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtCodVend = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtDescVend = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private ListaCampos lcTipoAtend = new ListaCampos( this, "TA" );
	
	private JTextFieldPad txtMatEmpr = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	
	private JTextFieldFK txtNomeEmpr = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private ListaCampos lcUsu = new ListaCampos( this, "US" );

	private ListaCampos lcVend = new ListaCampos( this, "VE" );
	
	private ListaCampos lcEmpregado = new ListaCampos( this, "EP" );

	private JButtonPad btBuscaEnd = new JButtonPad( Icone.novo( "btBuscacep.gif" ) );

	private Map<String, Object> bPref = null;

	public FAtendente() {

		super();
		setTitulo( "Cadastro de Atendentes" );
		setAtribos( 50, 20, 490, 490 );

		lcTipoAtend.add( new GuardaCampo( txtCodTipoAtend, "CodTpAtend", "Cód.tp.atend.", ListaCampos.DB_PK, false ), "txtCodVendx" );
		lcTipoAtend.add( new GuardaCampo( txtDescTipoAtend, "DescTpAtend", "Descriçao do tipo de atendente", ListaCampos.DB_SI, false ), "txtCodVendx" );
		lcTipoAtend.montaSql( false, "TIPOATEND", "AT" );
		lcTipoAtend.setQueryCommit( false );
		lcTipoAtend.setReadOnly( true );
		txtCodTipoAtend.setTabelaExterna( lcTipoAtend );

		lcVend.add( new GuardaCampo( txtCodVend, "CodVend", "Cód.comis.", ListaCampos.DB_PK, false ) );
		lcVend.add( new GuardaCampo( txtDescVend, "NomeVend", "Nome do comissionado", ListaCampos.DB_SI, false ) );
		lcVend.montaSql( false, "VENDEDOR", "VD" );
		lcVend.setQueryCommit( false );
		lcVend.setReadOnly( true );
		txtCodVend.setTabelaExterna( lcVend );

		lcUsu.add( new GuardaCampo( txtIDUsu, "IdUsu", "ID", ListaCampos.DB_PK, false ) );
		lcUsu.add( new GuardaCampo( txtNomeUsu, "NomeUsu", "Nome do usuário", ListaCampos.DB_SI, false ) );
		lcUsu.montaSql( false, "USUARIO", "SG" );
		lcUsu.setQueryCommit( false );
		lcUsu.setReadOnly( true );
		txtIDUsu.setTabelaExterna( lcUsu );
				
		lcEmpregado.add( new GuardaCampo( txtMatEmpr, "MatEmpr", "Matrícula.", ListaCampos.DB_PK, false ) );
		lcEmpregado.add( new GuardaCampo( txtNomeEmpr, "NomeEmpr", "Nome do empregado", ListaCampos.DB_SI, false ) );
		lcEmpregado.montaSql( false, "EMPREGADO", "RH" );
		lcEmpregado.setQueryCommit( false );
		lcEmpregado.setReadOnly( true );
		txtMatEmpr.setTabelaExterna( lcEmpregado );


	}

	private void montaTela() {

		adicCampo( txtCodAtend, 7, 20, 80, 20, "CodAtend", "Cód.atend.", ListaCampos.DB_PK, true );
		adicCampo( txtNomeAtend, 90, 20, 370, 20, "NomeAtend", "Nome do atendente", ListaCampos.DB_SI, true );
		adicCampo( txtCpfAtend, 7, 60, 150, 20, "CpfAtend", "CPF", ListaCampos.DB_SI, false );
		adicCampo( txtIdentificAtend, 160, 60, 150, 20, "IdentificAtend", "Identificação", ListaCampos.DB_SI, false );
		adicCampo( txtRgAtend, 313, 60, 149, 20, "RgAtend", "RG", ListaCampos.DB_SI, false );
		adicCampo( txtCepAtend, 7, 100, 68, 20, "CepAtend", "Cep", ListaCampos.DB_SI, false );
		adic( btBuscaEnd, 78, 100, 20, 20 );
		adicCampo( txtEndAtend, 101, 100, 295, 20, "EndAtend", "Endereço", ListaCampos.DB_SI, false );
		adicCampo( txtNumAtend, 398, 100, 65, 20, "NumAtend", "Número", ListaCampos.DB_SI, false );
		adicCampo( txtBairAtend, 7, 140, 195, 20, "BairAtend", "Bairro", ListaCampos.DB_SI, false );
		adicCampo( txtCidAtend, 205, 140, 195, 20, "CidAtend", "Cidade", ListaCampos.DB_SI, false );
		adicCampo( txtUFAtend, 403, 140, 59, 20, "UFAtend", "UF", ListaCampos.DB_SI, false );
		adicCampo( txtFoneAtend, 7, 180, 150, 20, "FoneAtend", "Telefone", ListaCampos.DB_SI, false );
		adicCampo( txtFaxAtend, 160, 180, 150, 20, "FaxAtend", "Fax", ListaCampos.DB_SI, false );
		adicCampo( txtCelAtend, 313, 180, 149, 20, "CelAtend", "Cel", ListaCampos.DB_SI, false );
		adicCampo( txtCodTipoAtend, 7, 220, 100, 20, "CodTpAtend", "Cód.tp.atend.", ListaCampos.DB_FK, txtDescTipoAtend, true );
		adicDescFK( txtDescTipoAtend, 110, 220, 352, 20, "DescTpAtend", "Descrição do tipo de atendente" );
		adicCampo( txtIDUsu, 7, 260, 100, 20, "IdUsu", "ID", ListaCampos.DB_FK, txtNomeUsu, false );
		adicDescFK( txtNomeUsu, 110, 260, 352, 20, "NomeUsu", "Nome do usuário" );
		adicCampo( txtCodVend, 7, 300, 100, 20, "CodVend", "Cód.comis.", ListaCampos.DB_FK, txtDescVend, false );
		adicDescFK( txtDescVend, 110, 300, 352, 20, "NomeVend", "Nome do comissionado" );
		adicCampo( txtMatEmpr, 7, 340, 100, 20, "MatEmpr", "Matricula", ListaCampos.DB_FK, txtNomeEmpr, false );
		adicDescFK( txtNomeEmpr, 110, 340, 352, 20, "NomeEmpr", "Nome do empregado" );		
		adicCampo( txtEmailAtend, 7, 380, 455, 20, "EmailAtend", "E-Mail", ListaCampos.DB_SI, false );
		
		txtRgAtend.setMascara( JTextFieldPad.MC_RG );
		txtCepAtend.setMascara( JTextFieldPad.MC_CEP );
		txtFoneAtend.setMascara( JTextFieldPad.MC_FONEDDD );
		txtCelAtend.setMascara( JTextFieldPad.MC_FONE );
		txtFaxAtend.setMascara( JTextFieldPad.MC_FONE );
		setListaCampos( true, "ATENDENTE", "AT" );
		lcCampos.setQueryInsert( false );

		if ( (Boolean) bPref.get( "BUSCACEP" ) ) {
			btBuscaEnd.setEnabled( true );
		}
		else {
			btBuscaEnd.setEnabled( false );
		}

		btBuscaEnd.addActionListener( this );
		btBuscaEnd.setToolTipText( "Busca Endereço a partir do CEP" );

	}

	private Map<String, Object> getPrefere() {

		Map<String, Object> retorno = new HashMap<String, Object>();
		StringBuilder sSQL = new StringBuilder();
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			sSQL.append( "SELECT BUSCACEP " );
			sSQL.append( "FROM SGPREFERE1 P  " );
			sSQL.append( "WHERE P.CODEMP=? AND P.CODFILIAL=?" );

			try {

				ps = con.prepareStatement( sSQL.toString() );
				ps.setInt( 1, Aplicativo.iCodEmp );
				ps.setInt( 2, ListaCampos.getMasterFilial( "SGPREFERE1" ) );

				rs = ps.executeQuery();

				if ( rs.next() ) {

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

		if ( !"".equals( txtCepAtend.getVlrString() ) ) {

			txtEndAtend.setEnabled( false );
			// txtComplVend.setEnabled( false );
			txtBairAtend.setEnabled( false );
			txtCidAtend.setEnabled( false );
			txtUFAtend.setEnabled( false );
			// txtCodPais.setEnabled( false );
			// txtSiglaUF.setEnabled( false );
			// txtCodMun.setEnabled( false );
			// txtDDDFoneVend.setEnabled( false );
			// txtDDDFaxVend.setEnabled( false );
			// txtDDDCelVend.setEnabled( false );

			Thread th = new Thread( new Runnable() {

				public void run() {

					try {
						WSCep cep = new WSCep();
						cep.setCon( con );
						cep.setCep( txtCepAtend.getVlrString() );
						cep.busca();
						Endereco endereco = cep.getEndereco();

						txtEndAtend.setVlrString( endereco.getTipo() + " " + endereco.getLogradouro() );
						// txtComplVend.setVlrString( endereco.getComplemento() );
						txtBairAtend.setVlrString( endereco.getBairro() );
						txtCidAtend.setVlrString( endereco.getCidade() );
						txtUFAtend.setVlrString( endereco.getSiglauf() );
						// txtCodPais.setVlrInteger( endereco.getCodpais() );
						// txtSiglaUF.setVlrString( endereco.getSiglauf() );
						// txtCodMun.setVlrString( endereco.getCodmunic() );

						// lcPais.carregaDados();
						// lcUF.carregaDados();
						// lcMunic.carregaDados();

						txtNumAtend.requestFocus();
					} catch ( Exception e ) {
						Funcoes.mensagemInforma( null, "Não foi encontrado o endereço para o CEP informado!" );
					} finally {
						txtEndAtend.setEnabled( true );
						// txtComplAtend.setEnabled( true );
						txtBairAtend.setEnabled( true );
						txtCidAtend.setEnabled( true );
						txtUFAtend.setEnabled( true );
						// txtCodPais.setEnabled( true );
						// txtSiglaUF.setEnabled( true );
						// txtCodMun.setEnabled( true );
						// txtDDDFoneVend.setEnabled( true );
						// txtDDDFaxVend.setEnabled( true );
						// txtDDDCelVend.setEnabled( true );
					}
				}
			} );
			try {
				th.start();
			} catch ( Exception err ) {
				Funcoes.mensagemInforma( null, "Não foi encontrado o endereço para o CEP informado!" );
				txtCepAtend.requestFocus();
			}
		}
		else {
			Funcoes.mensagemInforma( null, "Digite um CEP para busca!" );
			txtCepAtend.requestFocus();
		}

	}

	public void actionPerformed( ActionEvent evt ) {

		if ( evt.getSource() == btBuscaEnd ) {

			buscaEndereco();
		}
		super.actionPerformed( evt );
	}

	public void setConexao( DbConnection cn ) {

		super.setConexao( cn );
		bPref = getPrefere();

		montaTela();

		lcTipoAtend.setConexao( cn );
		lcUsu.setConexao( cn );
		lcVend.setConexao( cn );
		lcEmpregado.setConexao( cn );
	}
}
