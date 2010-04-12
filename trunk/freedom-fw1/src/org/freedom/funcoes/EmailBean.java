package org.freedom.funcoes;

import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.ListaCampos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.freedom.telas.Aplicativo;

public class EmailBean {
	
	private String assunto;
	private String host;
	private int porta;
	private String usuario;
	private String senha;
	private String de;
	private String para;
	private String autentica;
	private String ssl;
	private String emailresp;
	private String corpo;
	private String formato;
	private String charset;
	private String assinatura;
	
	
	public String getAssinatura() {
	
		return assinatura;
	}

	
	public void setAssinatura( String assinatura ) {
	
		this.assinatura = assinatura;
	}

	public String getFormato() {	
		return formato;
	}
	
	public void setFormato( String formato ) {	
		this.formato = formato;
	}
	
	
	public String getCharset() {
	
		return charset;
	}

	
	public void setCharset( String charset ) {
	
		this.charset = charset;
	}

	public EmailBean() { }
	
	public String getCorpo() {
		return corpo;
	}
	
	public void setCorpo(String corpo) {
		this.corpo = corpo;
	}
	
	public String getEmailResp() {
		return emailresp;
	}
	
	public void setEmailResp(String emailresp) {
		this.emailresp = emailresp;
	}		
	
	public String getAutentica() {	
		return autentica;
	}
	
	public void setAutentica( String autentica ) {	
		this.autentica = autentica;
	}

	public String getDe() {		
		return de;
	}
	
	public void setDe( String from ) {		
		this.de = from;
	}
	
	public String getHost() {		
		return host;
	}
	
	public void setHost( String host ) {		
		this.host = host;
	}
	
	public String getSenha() {		
		return senha;
	}
	
	public void setSenha( String passord ) {		
		this.senha = passord;
	}
	
	public int getPorta() {		
		return porta;
	}
	
	public void setPorta( int port ) {		
		this.porta = port;
	}	
	
	public String getSsl() {	
		return ssl;
	}
	
	public void setSsl( String ssl ) {	
		this.ssl = ssl;
	}

	public String getAssunto() {		
		return assunto;
	}
	
	public void setAssunto( String titulo ) {		
		this.assunto = titulo;
	}
	
	public String getPara() {		
		return para;
	}
	
	public void setPara( String to ) {		
		this.para = to;
	}
	
	public String getUsuario() {		
		return usuario;
	}
	
	public void setUsuario( String user ) {		
		this.usuario = user;
	}
	
	public EmailBean getClone() {
				
		EmailBean clone = new EmailBean();
		
		clone.setAssunto( getAssunto() );
		clone.setHost( getHost() );
		clone.setPorta( getPorta() );
		clone.setUsuario( getUsuario() );
		clone.setSenha( getSenha() );
		clone.setDe( getDe() );
		clone.setEmailResp( getEmailResp() );
		clone.setPara( getPara() );
		clone.setFormato( getFormato() );
		clone.setAutentica( getAutentica() );
		clone.setSsl( getSsl() );
		clone.setCorpo( getCorpo() );
		clone.setAssinatura( getAssinatura() );
		
		return clone;
	}

	public String getEmailEmp( final DbConnection con ) {

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

			con.commit();

		} catch ( Exception e ) {
			Funcoes.mensagemErro( null, "Erro ao buscar email da filial!\n" + e.getMessage() );
			e.printStackTrace();
		}

		return email;
	}

	public static String getEmailCli( final int codcli, final DbConnection con ) {

		String email = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuilder sSQL = new StringBuilder();

		try {

			sSQL.append( "SELECT EMAILCLI FROM VDCLIENTE WHERE CODEMP=? AND CODFILIAL=? AND CODCLI=?" );
			ps = con.prepareStatement( sSQL.toString() );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "VDCLIENTE" ) );
			ps.setInt( 3, codcli );
			rs = ps.executeQuery();

			if ( rs.next() ) {

				email = rs.getString( "EMAILCLI" ) != null ? rs.getString( "EMAILCLI" ).trim() : "";
			}

			rs.close();
			ps.close();

			con.commit();

		} catch ( Exception e ) {
			Funcoes.mensagemErro( null, "Erro ao buscar email do cliente!\n" + e.getMessage() );
			e.printStackTrace();
		}

		return email;
	}
			
}
