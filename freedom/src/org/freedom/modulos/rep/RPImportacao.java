/**
 * @version 07/2007 <BR>
 * @author Setpoint Informática Ltda.<BR>
 * @author Alex Rodrigues
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.rep <BR>
 * Classe:
 * @(#)RPImportacao.java <BR>
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
 * Formulario de importação de dados do sistema Repwim para o modulo de representações do Freedom-ERP.
 * 
 */

package org.freedom.modulos.rep;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

import org.freedom.bmps.Icone;
import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JPanelPad;
import org.freedom.componentes.JPasswordFieldPad;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FFDialogo;
import org.freedom.telas.FFilho;

public class RPImportacao extends FFilho implements ActionListener {

	private static final long serialVersionUID = 1L;

	private final JPanelPad panelImportacao = new JPanelPad();

	private final JPanelPad panelRodape = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private final JPanelPad panelBotoes = new JPanelPad( JPanelPad.TP_JPANEL, new FlowLayout( FlowLayout.CENTER, 6, 4 ) );

	private final JPanelPad panelSair = new JPanelPad( JPanelPad.TP_JPANEL, new FlowLayout( FlowLayout.CENTER, 6, 4 ) );

	private final JTextFieldPad txtDiretorio = new JTextFieldPad( JTextFieldPad.TP_STRING, 100, 0 );

	private final JButton btConectar = new JButton( "Conectar" );

	private final JButton btImportar = new JButton( "Importar" );

	private final JButton btDirtorio = new JButton( "..." );

	private final JButton btSair = new JButton( "Sair", Icone.novo( "btSair.gif" ) );

	private final JLabel status = new JLabel( "Selecione a base de dados ..." );
	
	private Connection conexaoparadox = null;


	public RPImportacao() {

		super( false );
		setTitulo( "Importação de dados" );
		setAtribos( 100, 100, 420, 200 );

		montaTela();

		btConectar.addActionListener( this );
		btImportar.addActionListener( this );
		btDirtorio.addActionListener( this );
		btSair.addActionListener( this );

		btConectar.setEnabled( false );
		btImportar.setEnabled( false );
	}

	private void montaTela() {

		getContentPane().setLayout( new BorderLayout() );

		panelImportacao.adic( new JLabel( "Local da base de dados" ), 10, 20, 350, 20 );
		panelImportacao.adic( txtDiretorio, 10, 40, 350, 20 );
		panelImportacao.adic( btDirtorio, 370, 38, 24, 24 );

		panelImportacao.adic( status, 10, 90, 350, 20 );

		btConectar.setPreferredSize( new Dimension( 120, 30 ) );
		btImportar.setPreferredSize( new Dimension( 120, 30 ) );

		panelRodape.setPreferredSize( new Dimension( 100, 44 ) );
		panelRodape.setBorder( BorderFactory.createEtchedBorder() );

		panelBotoes.add( btConectar );
		panelBotoes.add( btImportar );

		panelSair.add( btSair );

		panelRodape.add( panelBotoes, BorderLayout.WEST );
		panelRodape.add( panelSair, BorderLayout.EAST );

		getContentPane().add( panelImportacao, BorderLayout.CENTER );
		getContentPane().add( panelRodape, BorderLayout.SOUTH );
	}

	private void getDiretorio() {

		try {

			FileDialog fileDialog = new FileDialog( Aplicativo.telaPrincipal, "Selecionar diretorio." );
			fileDialog.setVisible( true );

			if ( fileDialog.getDirectory() != null ) {

				txtDiretorio.setVlrString( fileDialog.getDirectory() );
				btConectar.setEnabled( true );
				btImportar.setEnabled( false );

				status.setText( "Conectar banco de dados ..." );
				btConectar.requestFocus();
			}
			else {

				txtDiretorio.setVlrString( "" );
				btConectar.setEnabled( false );
				btImportar.setEnabled( false );

				btDirtorio.requestFocus();
			}
		} catch ( Exception e ) {
			e.printStackTrace();
		}
	}
	
	private Connection getConexaoparadox() {	
		return conexaoparadox;
	}

	
	private void setConexaoparadox( Connection conexaoparadox ) {	
		this.conexaoparadox = conexaoparadox;
	}

	private void conectar() {

		try {

			Login login = new Login( this );
			login.setVisible( true );

			if ( login.OK ) {

				String user = login.getUser();
				String password = login.getPassword();

				try {

					Connection conparadox = null;
					Properties props = new Properties();

					Class.forName( "com.hxtt.sql.paradox.ParadoxDriver" );
					
					props.put( "user", user );
					props.put( "password", password );

					conparadox = DriverManager.getConnection( "jdbc:paradox:/" + txtDiretorio.getVlrString().trim(), props );
					conparadox.setAutoCommit( false );
					
					setConexaoparadox( conparadox );
					status.setText( "Conexão executada ..." );
					
					btImportar.setEnabled( true );
					btConectar.setEnabled( false );
					
				}
				catch ( ClassNotFoundException e ) {
					Funcoes.mensagemErro( this, "Driver não encontrado!\n" + e.getMessage() );
					e.printStackTrace();
				}
				catch ( java.sql.SQLException e ) {
					if ( e.getErrorCode() == 335544472 ) {
						Funcoes.mensagemErro( this, "Nome do usuário ou senha inválidos!" );
					}
					else {
						Funcoes.mensagemErro( this, "Não foi possível estabelecer conexão com o banco de dados.\n" + e.getMessage() );
					}
					e.printStackTrace();
				}

			}

			login.dispose();
		} catch ( Exception e ) {
			e.printStackTrace();
		}
	}
	
	private void importar() {
		
		int opt = Funcoes.mensagemConfirma( this, "Confirma importação dos dodos?" );
		
		if ( opt == JOptionPane.YES_OPTION ) {
			
			String msg = "";
			
			try {
			
				if ( ! importarVendedor() ) {
					msg = "dos Vendedores.";
				}
				else if ( ! importarFornecedor() ) {
					msg = "dos Fornecedores.";
				}
				else if ( ! importarProduto() ) {
					msg = "dos Produtos.";
				}
				else if ( ! importarTransportadora() ) {
					msg = "das Transportadoras.";
				}
				else if ( ! importarTipoCliente() ) {
					msg = "dos Tipos de Cliente.";
				}
				else if ( ! importarCliente() ) {
					msg = "dos Clientes.";
				} 
			}
			catch ( Exception e ) {
				status.setText( "" );
				e.printStackTrace();
				Funcoes.mensagemErro( this, "Erro ao importar dados " + msg + "\n" + e.getMessage() );
			}			
		}
	}
	
	private boolean executeGeneric( final String sql ) throws Exception {
		
		boolean retorno = true;
		
		try {
			
			Connection session = getConexaoparadox();
			List< String > dadosparadox = new ArrayList< String >();
			
			PreparedStatement ps = session.prepareStatement( sql );
			ResultSet rs = ps.executeQuery();
			
			while ( rs.next() ) {
				
				dadosparadox.add( rs.getString( 1 ) );
			}
			
			if ( ! session.getAutoCommit() ) {
				session.commit();
			}
			
			PreparedStatement psf = null;
			
			for ( String insert : dadosparadox ) {
				
				psf = con.prepareStatement( insert );
				psf.execute();
				
				if ( ! con.getAutoCommit() ) {
					con.commit();
				}
			}
		}
		catch ( Exception e ) {
			retorno = false;
			throw e;
		}
		
		return retorno;
	}
	
	private boolean importarGeneric( final String sql ) throws Exception {
		
		boolean retorno = true;
		
		try {
			
			Connection session = getConexaoparadox();
			List< String > dadosparadox = new ArrayList< String >();
			
			PreparedStatement ps = session.prepareStatement( sql );
			ResultSet rs = ps.executeQuery();
			
			while ( rs.next() ) {
				
				dadosparadox.add( rs.getString( 1 ) );
			}
			
			if ( ! session.getAutoCommit() ) {
				session.commit();
			}
			
			PreparedStatement psf = null;
			
			for ( String insert : dadosparadox ) {
				
				psf = con.prepareStatement( insert );
				psf.execute();
				
				if ( ! con.getAutoCommit() ) {
					con.commit();
				}
			}
		}
		catch ( Exception e ) {
			retorno = false;
			throw e;
		}
		
		return retorno;
	}
	
	private boolean importarVendedor() throws Exception {
		
		boolean retorno = false;		
		
		StringBuilder sql = new StringBuilder();
		
		sql.append( "SELECT 'INSERT INTO RPVENDEDOR " );
		sql.append( "( CODEMP,CODFILIAL,CODVEND,NOMEVEND,ENDVEND,CIDVEND,BAIRVEND," );
		sql.append( "CEPVEND,ESTVEND,DDDVEND,FONEVEND,FAXVEND,PERCCOMIS,EMAILVEND ) VALUES ( " );
		sql.append( Aplicativo.iCodEmp );
		sql.append( "," );
		sql.append( ListaCampos.getMasterFilial( "RRVENDEDOR" ) );
		sql.append( ",'||Codvend||','||char(39)||Nomevend||char(39)||','||" );
		sql.append( "char(39)||Endvend||char(39)||','||char(39)||Cidvend||char(39)||','||" );
		sql.append( "char(39)||Bairvend||char(39)||','||char(39)||Cepvend||char(39)||','||" );
		sql.append( "char(39)||Estvend||char(39)||','||'NULL'||','||char(39)||Fonevend||" );
		sql.append( "char(39)||','||char(39)||Faxvend||char(39)||','||COALESCE(Perccomvend,'NULL')||','||" );
		sql.append( "char(39)||Emailvend||char(39)||' )' FROM VENDEDOR" );
		
		status.setText( "Importando Vendedores..." );
		
		return importarGeneric( sql.toString() );
	}
	
	private boolean importarFornecedor() throws Exception {
		
		boolean retorno = false;
		
		StringBuilder sql = new StringBuilder();
		
		sql.append( "select 'INSERT INTO RPFORNECEDOR " );
		sql.append( "( CODEMP,CODFILIAL,CODFOR,RAZFOR,NOMEFOR,CNPJFOR,INSCFOR,ENDFOR,CIDFOR,ESTFOR," );
		sql.append( "CEPFOR,BAIRFOR,FONEFOR,FAXFOR,EMAILFOR,CODREPFOR ) VALUES ( " );
		sql.append( Aplicativo.iCodEmp );
		sql.append( "," );
		sql.append( ListaCampos.getMasterFilial( "RRFORNECEDOR" ) );
		sql.append( ",'||codfor||','||char(39)||razfor||char(39)||','||char(39)||nomefor||char(39)||','||" );
		sql.append( "char(39)||cgcfor||char(39)||','||char(39)||inscfor||char(39)||','||char(39)||endfor||" );
		sql.append( "char(39)||','||char(39)||cidfor||char(39)||','||char(39)||estfor||char(39)||','||" );
		sql.append( "char(39)||cepfor||char(39)||','||char(39)||bairfor||char(39)||','||char(39)||fonefor||" );
		sql.append( "char(39)||','||char(39)||faxfor||char(39)||','||char(39)||emailfor||char(39)||','||" );
		sql.append( "coalesce(codrepfor,'null')||')' from forneced" );
		
		status.setText( "Importando Fornecedores..." );
		
		return importarGeneric( sql.toString() );
	}
	
	private boolean importarProduto() throws Exception {
		
		boolean retorno = false;
		
		StringBuilder sql = new StringBuilder();
		
		
		
		sql.append( "SELECT 'INSERT INTO RPPRODUTO " );
		sql.append( "( CODEMP,CODFILIAL,CODPROD,DESCPROD,REFPROD,CODBARPROD,CODEMPGP,CODFILIALGP,CODGRUP,CODEMPUD,"  );
		sql.append( "CODFILIALUD,CODUNID,CODEMPFO,CODFILIALFO,CODFOR,REFPRODFOR,PESOLIQPROD,PESOBRUTPROD,COMISPROD,"  );
		sql.append( "PERCIPIPROD,PRECOPROD1,PRECOPROD2,PRECOPROD3 ) VALUES ( " );
		sql.append( Aplicativo.iCodEmp );
		sql.append( "," );
		sql.append( ListaCampos.getMasterFilial( "RRFORNECEDOR" ) );
		sql.append( ",'||CODPROD||','||CHAR(39)||DESCPROD||CHAR(39)||','||CHAR(39)||CODPROD||CHAR(39)||','||" );
		sql.append( "CHAR(39)||CODBARPROD||CHAR(39)||','||" );
		sql.append( Aplicativo.iCodEmp );
		sql.append( "||','||" );
		sql.append( ListaCampos.getMasterFilial( "RRPRODUTO" ) );
		sql.append( "||','||CHAR(39)||'0001'||CHAR(39)||','||" );
		sql.append( Aplicativo.iCodEmp );
		sql.append( "||','||" );
		sql.append( ListaCampos.getMasterFilial( "RRPRODUTO" ) );
		sql.append( "||','||CHAR(39)||'UN'||CHAR(39)||','||" );
		sql.append( Aplicativo.iCodEmp );
		sql.append( "||','||" );
		sql.append( ListaCampos.getMasterFilial( "RRPRODUTO" ) );
		sql.append( "||','||CODFOR||','||CHAR(39)||CODFORPROD||CHAR(39)||','||COALESCE(PESOPROD,'NULL')||','||COALESCE(PESOPROD,'NULL')||','||" );
		sql.append( "COALESCE(COMISPROD,'NULL')||','||COALESCE(PERCIPIPROD,'NULL')||','||COALESCE(PRECOPROD,'NULL')||','||" );
		sql.append( "COALESCE(PRECOPROD2,'NULL')||','||COALESCE(PRECOPROD3,'NULL')||' )' FROM PRODUTO" );
		
		status.setText( "Importando Produtos..." );
		
		return importarGeneric( sql.toString() );
	}
	
	private boolean importarTransportadora() throws Exception {
		
		boolean retorno = false;
		
		StringBuilder sql = new StringBuilder();
		
		sql.append( "select ' INSERT INTO RPTRANSP " );
		sql.append( "( CODEMP,CODFILIAL,CODTRAN,RAZTRAN,NOMETRAN,CNPJTRAN,INSCTRAN,ENDTRAN," );
		sql.append( "CIDTRAN,ESTTRAN,CEPTRAN,BAIRTRAN,FONETRAN,FAXTRAN ) VALUES ( " );
		sql.append( Aplicativo.iCodEmp );
		sql.append( "," );
		sql.append( ListaCampos.getMasterFilial( "RRTRANSP" ) );
		sql.append( ",'||codtransp||','||char(39)||nometransp||char(39)||','||char(39)||nometransp||" );
		sql.append( "char(39)||','||char(39)||cnpjtransp||char(39)||','||char(39)||insctransp||" );
		sql.append( "char(39)||','||char(39)||endtransp||char(39)||','||char(39)||cidtransp||" );
		sql.append( "char(39)||','||char(39)||esttransp||char(39)||','||char(39)||ceptransp||" );
		sql.append( "char(39)||','||char(39)||bairtransp||char(39)||','||char(39)||fonetransp||" );
		sql.append( "char(39)||','||char(39)||faxtransp||char(39)||')' from transp" );
		
		status.setText( "Importando Tramsportadoras..." );
		
		return importarGeneric( sql.toString() );
	}
	
	private boolean importarTipoCliente() throws Exception {
		
		boolean retorno = false;

		StringBuilder sql = new StringBuilder();
		
		sql.append( "SELECT 'INSERT INTO RPTIPOCLI ( CODEMP,CODFILIAL,CODTIPOCLI,DESCTIPOCLI,TIPOCLI ) VALUES ( " );
		sql.append( Aplicativo.iCodEmp );
		sql.append( "," );
		sql.append( ListaCampos.getMasterFilial( "RRTIPOCLI" ) );
		sql.append( ",'||CODTIPOCLI||','||char(39)||DESCTIPOCLI||char(39)||','||char(39)||TIPOCLI||char(39)||' )' FROM TIPOCLI" );
		
		status.setText( "Importando Tipos de Clientes..." );
		
		return importarGeneric( sql.toString() );
	}
	
	private boolean importarCliente() throws Exception {
		
		boolean retorno = false;
		
		StringBuilder sql = new StringBuilder();
		
		sql.append( "SELECT 'INSERT INTO RPCLIENTE ( CODEMP,CODFILIAL,CODCLI,RAZCLI,NOMECLI,CODEMPVO,CODFILIALVO,CODVEND," );
		sql.append( "CODEMPTC,CODFILIALTC,CODTIPOCLI,CNPJCLI,INSCCLI,ENDCLI,CIDCLI,ESTCLI,CEPCLI,BAIRCLI,FONECLI,FAXCLI," );
		sql.append( "EMAILCLI,ENDCOBCLI,BAIRCOBCLI,CIDCOBCLI,ESTCOBCLI,CEPCOBCLI,ENDENTCLI,BAIRENTCLI,CIDENTCLI,CEPENTCLI," );
		sql.append( "ESTENTCLI,INSCENTCLI,CNPJENTCLI,ATIVCLI ) VALUES ( " );
		sql.append( Aplicativo.iCodEmp );
		sql.append( "," );
		sql.append( ListaCampos.getMasterFilial( "RRCLIENTE" ) );
		sql.append( "'||','||CODCLI||','||char(39)||RAZCLI||char(39)||','||char(39)||NOMECLI||char(39)||','||'" );
		sql.append( Aplicativo.iCodEmp );
		sql.append( "'||','||" );
		sql.append( ListaCampos.getMasterFilial( "RRCLIENTE" ) );
		sql.append( "||','||CODVEND||','||'" );
		sql.append( Aplicativo.iCodEmp );
		sql.append( "'||','||" );
		sql.append( ListaCampos.getMasterFilial( "RRCLIENTE" ) );
		sql.append( "||','||CODTIPOCLI||','||char(39)||CGCCLI||char(39)||','||char(39)||INSCCLI||char(39)||','||char(39)||ENDCLI||" );
		sql.append( "char(39)||','||char(39)||CIDCLI||char(39)||','||char(39)||ESTCLI||char(39)||','||char(39)||CEPCLI||char(39)||','||" );
		sql.append( "char(39)||BAIRCLI||char(39)||','||char(39)||FONECLI||char(39)||','||char(39)||FAXCLI||char(39)||','||char(39)||" );
		sql.append( "EMAILCLI||char(39)||','||char(39)||ENDCOBCLI||char(39)||','||char(39)||BAIRCOBCLI||char(39)||','||char(39)||" );
		sql.append( "CIDCOBCLI||char(39)||','||char(39)||ESTCOBCLI||char(39)||','||char(39)||CEPCOBCLI||char(39)||','||char(39)||" );
		sql.append( "ENDENTCLI||char(39)||','||char(39)||BAIRENTCLI||char(39)||','||char(39)||CIDENTCLI||char(39)||','||char(39)||" );
		sql.append( "CEPENTCLI||char(39)||','||char(39)||ESTENTCLI||char(39)||','||char(39)||INSCENTCLI||char(39)||','||char(39)||" );
		sql.append( "CGCENTCLI||char(39)||','||char(39)||'S'||char(39)|| ')' FROM CLIENTE" );
		
		status.setText( "Importando Clientes..." );
		
		return importarGeneric( sql.toString() );
	}

	public void actionPerformed( ActionEvent evt ) {

		if ( evt.getSource() == btSair ) {
			dispose();
		}
		else if ( evt.getSource() == btConectar ) {
			conectar();
		}
		else if ( evt.getSource() == btImportar ) {
			
			importar();
		}
		else if ( evt.getSource() == btDirtorio ) {
			getDiretorio();
		}
	}

	public void setConexao( Connection cn ) {

		super.setConexao( cn );
	}

	private class Login extends FFDialogo {

		private static final long serialVersionUID = 1l;

		private final JTextFieldPad txtUser = new JTextFieldPad( JTextFieldPad.TP_STRING, 20, 0 );

		private final JPasswordFieldPad txtPassword = new JPasswordFieldPad( 20 );

		Login( final Component cOrig ) {

			super( cOrig );
			setTitulo( "Login" );
			setAtribos( 250, 160 );

			adic( new JLabelPad( "Usuário: ", SwingConstants.RIGHT ), 10, 15, 65, 20 );
			adic( txtUser, 80, 15, 130, 20 );
			adic( new JLabelPad( "Senha: ", SwingConstants.RIGHT ), 10, 40, 65, 20 );
			adic( txtPassword, 80, 40, 130, 20 );
		}

		String getUser() {

			return txtUser.getVlrString();
		}

		String getPassword() {

			return txtPassword.getVlrString();
		}
	}
}
