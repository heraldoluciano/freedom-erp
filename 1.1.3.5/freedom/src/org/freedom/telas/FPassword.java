/**
 * @version 06/02/2006 <BR>
 * @author Setpoint Informática Ltda./Alex Rodrigues <BR>
 * 
 * Projeto: Freedom <BR>
 * Pacote: org.freedom.telas <BR>
 * Classe:
 * @(#)FPassword.java <BR>
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
 * FFDialog para validação de senha.
 */
package org.freedom.telas;

import java.awt.Component;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JPasswordFieldPad;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.funcoes.Funcoes;

public class FPassword extends FFDialogo {

	public static final long serialVersionUID = 1L;

	/**
	 * Permissão para vender o produto abaixo do custo.
	 */
	public static final int BAIXO_CUSTO = 0;

	/**
	 * Permissão para abrir gaveta do PDV.
	 */
	public static final int ABRE_GAVETA = 1;

	/**
	 * Permissão para alterar as parcelas no fechamento da venda.
	 */
	public static final int ALT_PARC_VENDA = 2;

	/**
	 * Permissão para venda de produto com receita.
	 */
	public static final int APROV_RECEITA_PROD = 3;

	private JTextFieldPad txtUsu = new JTextFieldPad( JTextFieldPad.TP_STRING, 8, 0 );

	private JPasswordFieldPad txtPass = new JPasswordFieldPad( 8 );

	private String[] param = null;

	private int tipo = 0;

	
	private int[] log = null;

	/**
	 * 
	 * @param arg0
	 *            Component pai.
	 * @param arg1
	 *            Tipo da permissão.
	 * @param arg2
	 *            Parametros para o log(para o tipo BAIXO_CUSTO).
	 * @param arg3
	 *            Titulo.
	 * @param arg4
	 *            Conexão.
	 */
	public FPassword( Component arg0, int arg1, String[] arg2, String arg3, Connection arg4 ) {

		super( arg0 );
		tipo = arg1;
		setParam( arg2 );
		setTitulo( arg3 );
		setConexao( arg4 );
		montaTela();
	}

	public FPassword( Component arg0, int arg1, String arg2, Connection arg3 ) {

		this( arg0, arg1, null, arg2, arg3 );
	}

	private void montaTela() {

		setAtribos( 300, 140 );
		adic( new JLabelPad( "Usuário: " ), 7, 10, 100, 20 );
		adic( new JLabelPad( "Senha: " ), 7, 30, 100, 20 );
		adic( txtUsu, 110, 10, 150, 20 );
		adic( txtPass, 110, 30, 150, 20 );
		adic( new JLabelPad( "Senha: " ), 7, 30, 100, 20 );
		
		eUltimo();

		txtUsu.setVlrString( Aplicativo.strUsuario );
		setPrimeiroFoco( txtPass );
	}

	public void execShow() {

		setVisible( true );
		firstFocus();
	}

	public void ok() {

		boolean ret = false;

		switch ( tipo ) {
			case BAIXO_CUSTO :
				ret = getBaixoCusto();
				break;
			case ABRE_GAVETA :
				ret = getAbreGaveta();
				break;
			case ALT_PARC_VENDA :
				ret = getAltParcVenda();
				break;
			case APROV_RECEITA_PROD :
				ret = getAprovReceitaProd();
				break;
			default :
				break;
		}

		OK = ret;
		setVisible( false );
	}

	private boolean getBaixoCusto() {

		boolean ret = getPermissao( BAIXO_CUSTO );
		
		if ( ret ) {
			
			log = AplicativoPD.gravaLog( 
					txtUsu.getVlrString().toLowerCase().trim(),
					"PR", "LIB", "Liberação de " + param[ 0 ] + " abaixo do custo", 
					param[ 0 ] + " [" + param[ 1 ] + "], " + // codigo da tabela
					"Item: [" + param[ 2 ] + "], " + // codigo do item
					"Produto: [" + param[ 3 ] + "], " + // codigo do produto
					"Preço: [" + param[ 4 ] + "]" // preço do produto
					, con );
		}
		
		return ret;
	}

	private boolean getAbreGaveta() {

		return getPermissao( ABRE_GAVETA );
	}

	private boolean getAltParcVenda() {

		return getPermissao( ALT_PARC_VENDA );
	}

	private boolean getAprovReceitaProd() {

		return getPermissao( APROV_RECEITA_PROD );
	}

	private boolean getPermissao( int tipo ) {

		PreparedStatement ps = null;
		ResultSet rs = null;
		Properties props = null;
		String sIDUsu = null;
		StringBuffer sSQL = new StringBuffer();
		boolean[] permissoes = new boolean[ 4 ];
		
		try {
			
			props = new Properties();			
			sIDUsu = txtUsu.getVlrString().toLowerCase().trim();
			props.put( "user", sIDUsu );
			props.put( "password", txtPass.getVlrString() );
			
			if ( "".equals( sIDUsu ) || "".equals( txtPass.getVlrString().trim() ) ) {
				
				Funcoes.mensagemErro( this, "Campo em branco!" );
				return false;
			}
			
			DriverManager.getConnection( Aplicativo.strBanco, props ).close();

			sSQL.append( "SELECT BAIXOCUSTOUSU, ABREGAVETAUSU, ALTPARCVENDA, APROVRECEITA " );
			sSQL.append( "FROM SGUSUARIO " );
			sSQL.append( "WHERE IDUSU=? AND CODEMP=? AND CODFILIAL=?" );
			
			ps = con.prepareStatement( sSQL.toString() );
			ps.setString( 1, sIDUsu );
			ps.setInt( 2, Aplicativo.iCodEmp );
			ps.setInt( 3, Aplicativo.iCodFilial );
			
			rs = ps.executeQuery();
			
			if ( rs.next() ) {
				
				permissoes[ 0 ] = "S".equals( rs.getString( 1 ) );
				permissoes[ 1 ] = "S".equals( rs.getString( 2 ) );
				permissoes[ 2 ] = "S".equals( rs.getString( 3 ) );
				permissoes[ 3 ] = "S".equals( rs.getString( 4 ) );
			}
			
			if ( ! permissoes[ tipo ] ) {
			
				Funcoes.mensagemErro( this, "Ação não permitida para este usuário ! ! !" );
			}

		} catch ( SQLException sqle ) {
			if ( sqle.getErrorCode() == 335544472 ) {
			
				Funcoes.mensagemErro( this, "Nome do usuário ou senha inválidos ! ! !" );
			}
			else {
				
				Funcoes.mensagemErro( this, "Erro ao verificar senha.", true, con, sqle );
			}
			
			sqle.printStackTrace();
		} catch ( Exception e ) {
			e.printStackTrace();
		} finally {
			ps = null;
			rs = null;
			props = null;
			sIDUsu = null;
			sSQL = null;
		}

		return permissoes[ tipo ];
	}

	public String[] getLog() {

		return new String[] { String.valueOf( Aplicativo.iCodEmp ), String.valueOf( log[ 0 ] ), String.valueOf( log[ 1 ] ) };
	}

	public void setParam( String[] arg ) {

		param = arg;
	}

	public void setTitulo( String arg ) {

		if ( ! ( arg != null && arg.trim().length() > 0 ) ) {
		
			arg = "Permissão";
		}
		
		super.setTitulo( arg );
	}

}
