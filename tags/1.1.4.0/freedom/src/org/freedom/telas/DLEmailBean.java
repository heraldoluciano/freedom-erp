/**
 * @version 05/2007 <BR>
 * @author Setpoint Informática Ltda.<BR>
 * @author Alex Rodrigues
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.rep <BR>
 * Classe:
 * @(#)DLEnviaPedido.java <BR>
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
 * Formulário para envio do pedido por e-mail.
 */

package org.freedom.telas;

import javax.swing.JLabel;

import org.freedom.componentes.JCheckBoxPad;
import org.freedom.componentes.JPanelPad;
import org.freedom.componentes.JPasswordFieldPad;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.funcoes.EmailBean;
import org.freedom.funcoes.Funcoes;

public class DLEmailBean extends FFDialogo {

	private static final long serialVersionUID = 1L;

	private JPanelPad panelRodape = null;

	private final JTextFieldPad txtHost = new JTextFieldPad( JTextFieldPad.TP_STRING, 50, 0 );

	private final JTextFieldPad txtPort = new JTextFieldPad( JTextFieldPad.TP_STRING, 50, 0 );

	private final JTextFieldPad txtFrom = new JTextFieldPad( JTextFieldPad.TP_STRING, 50, 0 );

	private final JTextFieldPad txtUser = new JTextFieldPad( JTextFieldPad.TP_STRING, 50, 0 );

	private final JPasswordFieldPad txtPassword = new JPasswordFieldPad( 20 );
	
	private final JCheckBoxPad cbAutenticaSMTP = new JCheckBoxPad( "Autenticar ?", "S", "N" );
	
	private final JCheckBoxPad cbSSLSMTP = new JCheckBoxPad( "Usa SSL ?", "S", "N" );
	
	private EmailBean mail = null;

	public DLEmailBean( EmailBean mail ) {

		super();
		setTitulo( "Configuraçôes de envio" );
		setAtribos( 405, 320 );
		setResizable( false );

		montaTela();
		
		this.mail = mail;
		
		if ( mail != null) {
			try {
				txtHost.setVlrString( mail.getHost() );
				txtPort.setVlrInteger( mail.getPorta() );
				txtFrom.setVlrString( mail.getDe() );
				txtUser.setVlrString( mail.getUsuario() );
				txtPassword.setVlrString( mail.getSenha() );
				cbAutenticaSMTP.setVlrString( mail.getAutentica() );
				cbSSLSMTP.setVlrString( mail.getSsl() );
			} catch (Exception e) {
				Funcoes.mensagemErro( null, "Não foi possível carregar as todas as informações para envio de email!\n" );
			}
		}
	}

	private void montaTela() {

		adic( new JLabel( "Servidor SMTP:" ), 10, 10, 300, 20 );
		adic( txtHost, 10, 30, 300, 20 );
		adic( new JLabel( "Porta" ), 320, 10, 60, 20 );
		adic( txtPort, 320, 30, 60, 20 );
		adic( new JLabel( "Conta de e-mail:" ), 10, 50, 370, 20 );
		adic( txtFrom, 10, 70, 370, 20 );
		adic( new JLabel( "Usuario SMTP:" ), 10, 90, 370, 20 );
		adic( txtUser, 10, 110, 370, 20 );
		adic( new JLabel( "Senha SMTP:" ), 10, 130, 370, 20 );
		adic( txtPassword, 10, 150, 370, 20 );
		adic( cbAutenticaSMTP, 10, 180, 370, 20 );
		adic( cbSSLSMTP, 10, 200, 370, 20 );
	}
	
	public EmailBean getEmailBean() {
		
		return mail;
	}

	@Override
	public void ok() {
		
		if ( mail == null ) {		

			mail = new EmailBean();
		}

		mail.setHost( txtHost.getVlrString() );
		mail.setPorta( txtPort.getVlrInteger() );
		mail.setDe( txtFrom.getVlrString() );
		mail.setUsuario( txtUser.getVlrString() );
		mail.setSenha( txtPassword.getVlrString() );
		mail.setAutentica( cbAutenticaSMTP.getVlrString() );
		mail.setSsl( cbSSLSMTP.getVlrString() );
		
		super.ok();
	}

	@Override
	public void cancel() {

		mail = null;
		
		super.cancel();
	}	
}
