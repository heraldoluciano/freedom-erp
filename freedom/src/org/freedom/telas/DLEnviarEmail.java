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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.io.File;
import java.sql.Connection;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;

import org.freedom.acao.Processo;
import org.freedom.bmps.Icone;
import org.freedom.componentes.JPanelPad;
import org.freedom.componentes.JPasswordFieldPad;
import org.freedom.componentes.JTextAreaPad;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ProcessoSec;
import org.freedom.funcoes.EmailBean;
import org.freedom.funcoes.Funcoes;
import org.freedom.modulos.rep.RPPrefereGeral;

public class DLEnviarEmail extends FFDialogo {

	private static final long serialVersionUID = 1L;

	private JPanelPad panelRodape = null;

	private final JTextFieldPad txtHost = new JTextFieldPad( JTextFieldPad.TP_STRING, 100, 0 );

	private final JTextFieldPad txtPort = new JTextFieldPad( JTextFieldPad.TP_STRING, 100, 0 );

	private final JTextFieldPad txtFrom = new JTextFieldPad( JTextFieldPad.TP_STRING, 100, 0 );

	private final JTextFieldPad txtTo = new JTextFieldPad( JTextFieldPad.TP_STRING, 100, 0 );
		
	private final JTextFieldPad txtAssunto = new JTextFieldPad( JTextFieldPad.TP_STRING, 120, 0 );

	private final JTextFieldPad txtUser = new JTextFieldPad( JTextFieldPad.TP_STRING, 50, 0 );

	private final JPasswordFieldPad txtPassword = new JPasswordFieldPad( 30 );

	private final JTextAreaPad txtMessage = new JTextAreaPad( 1000 );

	private final JButton btEnviar = new JButton( "Enviar", Icone.novo( "btEnviarMail.gif" ) );

	private final JLabel status = new JLabel();

	private JasperPrint report = null;
	
	private EmailBean mail = null;
	
	private boolean preparado = false;
	
	private boolean comCopia = false;
	

	public DLEnviarEmail( final Component cOrig, final EmailBean mail ) {

		super( cOrig );
		setTitulo( "Enviar por e-mail" );
		setAtribos( 405, 350 );
		setResizable( false );
		
		this.mail = mail;

		montaTela();
		
		btEnviar.addActionListener( this );
	}

	private void montaTela() {

		adic( new JLabel( "Para:" ), 10, 10, 370, 20 );
		adic( txtTo, 10, 30, 370, 20 );
		adic( new JLabel( "Assunto:" ), 10, 50, 370, 20 );
		adic( txtAssunto, 10, 70, 370, 20 );
		adic( new JLabel( "Mensagem:" ), 10, 90, 370, 20 );
		adic( new JScrollPane( txtMessage ), 10, 110, 370, 100 );
		adic( new JLabel( "Usuario" ), 10, 210, 185, 20 );
		adic( txtUser, 10, 230, 185, 20 );
		adic( new JLabel( "Senha" ), 200, 210, 180, 20 );
		adic( txtPassword, 200, 230, 180, 20 );
		adic( status, 10, 260, 370, 20 );

		status.setForeground( Color.BLUE );

		panelRodape = adicBotaoSair();

		btEnviar.setPreferredSize( new Dimension( 100, 26 ) );
		panelRodape.add( btEnviar, BorderLayout.WEST );
	}

	public void setReport( final JasperPrint report ) {

		this.report = report;
	}

	private void setStatus( final String msg ) {

		status.setText( msg != null ? msg : "" );
	}

	public void preparar() {
		
		while ( ! validaEmailBean( mail ) ) {
			
			mail = getEmailBean( mail );
			
			if ( mail == null ) {
				break;
			}
		}

		if ( mail != null ) {
			
			txtAssunto.setVlrString( mail.getAssunto() );		
			txtHost.setVlrString( mail.getHost() );
			txtPort.setVlrInteger( mail.getPorta() );
			txtUser.setVlrString( mail.getUsuario() );
			txtPassword.setVlrString( mail.getSenha() );
			txtFrom.setVlrString( mail.getDe() );
			txtTo.setVlrString( mail.getPara() );
			
			preparado = true;
		}
	}
	
	private EmailBean getEmailBean( EmailBean mail ) {
		
		DLEmailBean dlemail = new DLEmailBean( mail );
		dlemail.setVisible( true );
		mail = dlemail.getEmailBean();
		
		if ( mail != null ) {
			
			Aplicativo.getInstace().updateEmailBean( mail );
		}
		
		return mail;
	}
	
	private boolean validaEmailBean( EmailBean mail ) {
		
		boolean ok = false;
		
		if ( mail != null ) {
			
			String host = mail.getHost();
			String from = mail.getDe();
			String autentica = mail.getAutentica();
			String ssl = mail.getSsl();
			int porta = mail.getPorta();
			
			if ( ( host != null && host.trim().length() > 0 ) 
					&& ( from != null && from.trim().length() > 0 )
						&& ( autentica != null && autentica.trim().length() > 0 ) 
							&& ( ssl != null && ssl.trim().length() > 0 )
								&& ( porta > 0 ) ) {
				ok = true;
			}			
		}
		
		return ok;
	}
	
	public boolean preparado() {
		return preparado;
	}

	private boolean validaEnviar() {

		boolean retorno = false;

		validar : {

			if ( txtHost.getVlrString() == null || txtHost.getVlrString().trim().length() == 0 ) {
				Funcoes.mensagemErro( this, "Servidor SMTP inválido!\nVerifique as preferências do sistema." );
				dispose();
				break validar;
			}
			if ( txtTo.getVlrString() == null || txtTo.getVlrString().trim().length() == 0 ) {
				Funcoes.mensagemErro( this, "Email da filial inválido!\nVerifique o cadastro da filial." );
				dispose();
				break validar;
			}
			if ( txtFrom.getVlrString() == null || txtFrom.getVlrString().trim().length() == 0 ) {
				Funcoes.mensagemErro( this, "E-mail não informado!" );
				break validar;
			}
			if ( txtUser.getVlrString() == null || txtUser.getVlrString().trim().length() == 0 ) {
				Funcoes.mensagemErro( this, "Usuário não informado!" );
				break validar;
			}
			if ( txtPassword.getVlrString() == null || txtPassword.getVlrString().trim().length() == 0 ) {
				Funcoes.mensagemErro( this, "Senha não informada!" );
				break validar;
			}

			retorno = true;
		}

		return retorno;
	}

	private void enviar() {

		boolean enviado = false;
		
		if ( validaEnviar() ) {
			
			DLLoading loading = new DLLoading();

			try {
				
				if ( "S".equals( mail.getAutentica() ) ) {
					loading.start();
					enviado = enviarAutenticado();
				}
				else {
					loading.start();
					enviado = enviarNaoAutenticado();
				}

			} catch ( Exception e ) {
				loading.stop();
				Funcoes.mensagemErro( this, "Erro ao enviar pedido!\n" + e.getMessage(), true, con, e );
				e.printStackTrace();
			} finally {
				loading.stop();
				if (enviado) {
					Funcoes.mensagemInforma( this, "E-mail enviado com sucesso." );
				}
			}

			setStatus( null );
		}
	}

	private boolean enviarAutenticado() throws Exception {

		boolean retorno = false;
		
		Properties props = new Properties();

		String socketFactory = "javax.net.SocketFactory";
		
		if ( "S".equals( mail.getSsl() ) ) {
			socketFactory = "javax.net.ssl.SSLSocketFactory";	
		}
				
		props.put( "mail.transport.protocol", "smtp" );
		props.put( "mail.smtp.host", txtHost.getVlrString() );	
		props.put( "mail.smtp.port", txtPort.getVlrString() );		
		props.put( "mail.smtp.auth", "true" );
		props.put( "mail.smtp.starttls.enable", "true" );
		props.put( "mail.smtp.socketFactory.class", socketFactory );
		props.put( "mail.smtp.quitwait", "false" );


		Authenticator authenticator = new SMTPAuthenticator( txtUser.getVlrString(), txtPassword.getVlrString().trim() );
		
		Session session = Session.getInstance( props, authenticator );

		MimeMessage msg = getMessage( session );

		if ( msg != null ) {
			setStatus( "Enviando e-mail..." );
			Transport.send( msg );
			retorno = true;
		}
		
		return retorno;
		
	}

	private boolean enviarNaoAutenticado() throws Exception {

		boolean retorno = false;
		Properties props = new Properties();
		props.put( "mail.transport.protocol", "smtp" );
		props.put( "mail.smtp.host", txtHost.getVlrString() );

		Session session = Session.getInstance( props, null );	
		
		MimeMessage msg = getMessage( session );	

		if ( msg != null ) {
			setStatus( "Enviando e-mail..." );
			Transport.send( msg );
			retorno = true;
		}
		return retorno;
	}
	
	private MimeMessage getMessage( final Session session ) throws Exception {
		
		MimeMessage msg = new MimeMessage( session );
		msg.setFrom( new InternetAddress( txtFrom.getVlrString() ) );

		InternetAddress[] address = null;
		
		if ( comCopia ) {
			address = new InternetAddress[] { new InternetAddress( txtTo.getVlrString() ), new InternetAddress( txtFrom.getVlrString() ) };
		}
		else {
			address = new InternetAddress[] { new InternetAddress( txtTo.getVlrString() ) };
		}
		
		msg.setRecipients( Message.RecipientType.TO, address );
		msg.setSubject( txtAssunto.getVlrString() );

		MimeBodyPart mbp1 = new MimeBodyPart();
		mbp1.setText( txtMessage.getVlrString() );


		setStatus( "Criando arquivo de anexo em " + Aplicativo.strTemp );

		String filename = Aplicativo.strTemp + Calendar.getInstance().getTimeInMillis() + ".pdf";
		JasperExportManager.exportReportToPdfFile( report, filename );
		File file = new File( filename );
		if ( !file.exists() ) {
			Funcoes.mensagemErro( this, "Anexo não foi criado.\nVerifique o parametro 'temp' no arquivo de parametros." );
			return null;
		}
		setStatus( "Anexando arquivo..." );

		FileDataSource fds = new FileDataSource( filename );
		MimeBodyPart mbp2 = new MimeBodyPart();
		mbp2.setDataHandler( new DataHandler( fds ) );
		mbp2.setFileName( fds.getName() );

		Multipart mp = new MimeMultipart();
		mp.addBodyPart( mbp1 );
		mp.addBodyPart( mbp2 );

		msg.setContent( mp );
		msg.setSentDate( Calendar.getInstance().getTime() );
		
		return msg;
	}

	@ Override
	public void actionPerformed( ActionEvent e ) {

		super.actionPerformed( e );

		if ( e.getSource() == btEnviar ) {

			ProcessoSec pSec = new ProcessoSec( 500, new Processo() {

				public void run() {

					status.updateUI();
				}
			}, new Processo() {

				public void run() {

					enviar();
				}
			} );

			pSec.iniciar();
		}
	}
	
	@Override
	public void setConexao( Connection conn ) {
		
		super.setConexao( conn );
		
		if ( con != null ) {

			List<Object> prefere = RPPrefereGeral.getPrefere( con );
			comCopia = "S".equals( prefere.get( RPPrefereGeral.EPrefere.ENVIACOPIA.ordinal() ) );	
		}		
	}

	class SMTPAuthenticator extends Authenticator {

		private final String username;

		private final String password;

		SMTPAuthenticator( String username, String password ) {

			this.username = username;
			this.password = password;
		}

		public PasswordAuthentication getPasswordAuthentication() {

			return new PasswordAuthentication( username, password );
		}
	}
}
