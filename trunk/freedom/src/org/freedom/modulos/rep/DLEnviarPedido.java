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

package org.freedom.modulos.rep;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.Multipart;
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

import org.freedom.bmps.Icone;
import org.freedom.componentes.JPanelPad;
import org.freedom.componentes.JTextAreaPad;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Funcoes;
import org.freedom.modulos.rep.RPPrefereGeral.EPrefere;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FFDialogo;

public class DLEnviarPedido extends FFDialogo {

	private static final long serialVersionUID = 1L;

	private JPanelPad panelRodape = null;

	private final JTextFieldPad txtFrom = new JTextFieldPad( JTextFieldPad.TP_STRING, 50, 0 );

	private final JTextFieldPad txtTo = new JTextFieldPad( JTextFieldPad.TP_STRING, 50, 0 );

	private final JTextFieldPad txtHost = new JTextFieldPad( JTextFieldPad.TP_STRING, 50, 0 );

	private final JTextFieldPad txtUser = new JTextFieldPad( JTextFieldPad.TP_STRING, 50, 0 );

	//private final JPasswordFieldPad txtPassword = new JPasswordFieldPad( 8 );

	private final JTextAreaPad txtMessage = new JTextAreaPad( 500 );

	private final JButton btEnviar = new JButton( "Enviar", Icone.novo( "btEnviarMail.gif" ) );

	private JasperPrint pedido = null;

	private List<Object> prefere = null;
	
	private String titulo = "";
	

	public DLEnviarPedido( final Component cOrig ) {

		super( cOrig );
		setTitulo( "Enviar Pedido" );
		setAtribos( 405, 290 );

		montaTela();
		
		btEnviar.addActionListener( this );
	}

	private void montaTela() {

		adic( new JLabel( "Para:" ), 10, 10, 370, 20 );
		adic( txtFrom, 10, 30, 370, 20 );
		adic( new JLabel( "Menssagem:" ), 10, 50, 370, 20 );
		adic( new JScrollPane( txtMessage ), 10, 70, 370, 100 );
		adic( new JLabel( "Usuario" ), 10, 170, 370, 20 );
		adic( txtUser, 10, 190, 370, 20 );
		/*adic( new JLabel( "Senha" ), 215, 170, 165, 20 );
		adic( txtPassword, 215, 190, 165, 20 );*/

		panelRodape = adicBotaoSair();

		btEnviar.setPreferredSize( new Dimension( 100, 26 ) );
		panelRodape.add( btEnviar, BorderLayout.WEST );
	}

	public void setPedido( final JasperPrint pedido ) {

		this.pedido = pedido;
	}
	
	public void preparar( final String titulo, final int codcli ) {
		
		this.titulo = titulo;
		txtHost.setVlrString( (String) prefere.get( EPrefere.SERVIDORSMTP.ordinal() ) );
		txtUser.setVlrString( (String) prefere.get( EPrefere.USUARIOSMTP.ordinal() ) );
		txtTo.setVlrString( getEmailEmp() );
		txtFrom.setVlrString( getEmailCli( codcli ) );
	}
	
	private String getEmailEmp() {
		
		String email = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuilder sSQL = new StringBuilder();
		
		try {
			
			sSQL.append( "SELECT EMAILFILIAL FROM SGFILIAL WHERE CODEMP=? AND CODFILIAL=?" );
			ps = con.prepareStatement( sSQL.toString() );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "SGFILIAL" ) );
			rs = ps.executeQuery();
			
			if ( rs.next() ) {
				
				email = rs.getString( "EMAILFILIAL" ) != null ? rs.getString( "EMAILFILIAL" ).trim() : null;
			}
			
			rs.close();
			ps.close();
			
			if ( !con.getAutoCommit() ) {
				con.commit();
			}
			
		} catch ( Exception e ) {
			Funcoes.mensagemErro( null, "Erro ao buscar email da filial!\n" + e.getMessage() );
			e.printStackTrace();
		}
		
		return email;
	}
	
	private String getEmailCli( final int codcli ) {
		
		String email = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuilder sSQL = new StringBuilder();
		
		try {
			
			sSQL.append( "SELECT EMAILCLI FROM RPCLIENTE WHERE CODEMP=? AND CODFILIAL=? AND CODCLI=?" );
			ps = con.prepareStatement( sSQL.toString() );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "SGFILIAL" ) );
			ps.setInt( 3, codcli );
			rs = ps.executeQuery();
			
			if ( rs.next() ) {
				
				email = rs.getString( "EMAILCLI" ) != null ? rs.getString( "EMAILCLI" ).trim() : "";
			}
			
			rs.close();
			ps.close();
			
			if ( !con.getAutoCommit() ) {
				con.commit();
			}
			
		} catch ( Exception e ) {
			Funcoes.mensagemErro( null, "Erro ao buscar email do cliente!\n" + e.getMessage() );
			e.printStackTrace();
		}
		
		return email;
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
			/*if ( txtPassword.getVlrString() == null || txtPassword.getVlrString().trim().length() == 0 ) {
				Funcoes.mensagemErro( this, "Senha não informada!" );
				break validar;
			}*/
			
			retorno = true;
		}
		
		return retorno;
	}

	private void enviar() {
		
		if ( validaEnviar() ) {
			
			try {
				
				setCursor( Cursor.getPredefinedCursor( Cursor.WAIT_CURSOR ) );
				
				Properties props = new Properties();
				props.put( "mail.smtp.host", txtHost.getVlrString() );
				
				Session session = Session.getDefaultInstance( props );
				
				// cria a mensagem
			    MimeMessage msg = new MimeMessage( session );
			    msg.setFrom( new InternetAddress( txtFrom.getVlrString() ) );
			    
			    InternetAddress[] address = {new InternetAddress( txtTo.getVlrString() ) };
			    msg.setRecipients( Message.RecipientType.TO, address );
			    msg.setSubject( titulo );
			    
			    // cria a primeira parte da mensagem
			    MimeBodyPart mbp1 = new MimeBodyPart();
			    mbp1.setText( txtMessage.getVlrString() );
			    
			    // cria a segunda parte da mensage
			    MimeBodyPart mbp2 = new MimeBodyPart();
			    
			    // cria o arquivo
			    String filename = Aplicativo.strTemp + "pedido" + Calendar.getInstance().getTimeInMillis() + ".pdf";
			    JasperExportManager.exportReportToPdfFile( pedido, filename );
			    File file = new File( filename );
			    
			    if ( ! file.exists() ) {
			    	return;
			    }

			    // anexa o arquivo na mensagem
			    FileDataSource fds = new FileDataSource( filename );
			    mbp2.setDataHandler( new DataHandler( fds ) );
			    mbp2.setFileName( fds.getName() );
			    
                // cria a Multipart
			    Multipart mp = new MimeMultipart();
			    mp.addBodyPart( mbp1 );
			    mp.addBodyPart( mbp2 );

			    // adiciona a Multipart na mensagem
			    msg.setContent(mp);

			    // configura a data: cabecalho
			    msg.setSentDate( new Date() );
			      
			    // envia a mensagem
			    Transport.send( msg );
			    
			    setCursor( Cursor.getDefaultCursor() );
			    
			    Funcoes.mensagemInforma( this, "E-mail enviado com sucesso." );

			}
			catch ( Exception e ) {
				Funcoes.mensagemErro( this, "Erro ao enviar pedido!\n" + e.getMessage(), true, con, e );
				e.printStackTrace();
			}
		}
	}

	@ Override
	public void actionPerformed( ActionEvent e ) {

		super.actionPerformed( e );
		
		if ( e.getSource() == btEnviar ) {
			
			enviar();
		}
	}

	@ Override
	public void setConexao( Connection cn ) {

		super.setConexao( cn );

		prefere = RPPrefereGeral.getPrefere( cn );
	}
}
