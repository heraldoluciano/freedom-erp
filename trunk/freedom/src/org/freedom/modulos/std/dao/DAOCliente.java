package org.freedom.modulos.std.dao;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.freedom.infra.dao.AbstractDAO;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.functions.Funcoes;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.frame.Aplicativo;
import org.freedom.modulos.std.business.object.ClienteFor;
import org.freedom.modulos.std.business.object.ClienteFor.INSERE_CLI_FOR;


public class DAOCliente extends AbstractDAO {
	
	public DAOCliente( DbConnection cn) {

		super( cn );

	}

	private String getString( String value ){
		String result = null;
		
		if (value == null){
			result = "";
		} else {
			result = value;
		}
		return result;
	}	
	
	private BigDecimal getBigDecimal( BigDecimal value ) {
		BigDecimal result = null;
		
		if (value == null){
			result = BigDecimal.ZERO;
		} else {
			result = value;
		}
		return result;
	}
	
	public int getCodFor(Integer codemp, Integer codfilial) throws SQLException	{

		int codigo = 0;
		StringBuilder sSQL = new StringBuilder();
		PreparedStatement ps = null;

		sSQL.append( "SELECT MAX( F.CODFOR ) FROM CPFORNECED F WHERE F.CODEMP=? AND F.CODFILIAL=?" );

		ps = getConn().prepareStatement( sSQL.toString() );
		ps.setInt( 1, codemp );
		ps.setInt( 2, codfilial );
		ResultSet rs = ps.executeQuery();

		if ( rs.next() ) {
			codigo = rs.getInt( 1 ) + 1;
		}

		return codigo;
	}

	public int inserirFor(ClienteFor clientefor) throws SQLException {

		int codfor = clientefor.getCodfor();
		
		StringBuilder sSQL = new StringBuilder();
		PreparedStatement ps = null;

		sSQL.append( "INSERT INTO CPFORNECED " );
		sSQL.append( "( CODEMP, CODFILIAL, CODFOR, RAZFOR, CODEMPTF, CODFILIALTF, CODTIPOFOR, CODEMPBO, CODFILIALBO, CODEMPHP, " );
		sSQL.append( "CODFILIALHP, NOMEFOR, PESSOAFOR, CNPJFOR, CPFFOR, INSCFOR, ENDFOR, NUMFOR, BAIRFOR, CODMUNIC, SIGLAUF, CODPAIS, RGFOR, DDDFONEFOR, FONEFOR, FAXFOR, CELFOR ) " );
		sSQL.append( "VALUES( ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?, ?,?,?,?,? ) " );

		ps = getConn().prepareStatement( sSQL.toString() );
		ps.setInt( INSERE_CLI_FOR.CODEMP.ordinal(), clientefor.getCodemp() );
		ps.setInt( INSERE_CLI_FOR.CODFILIAL.ordinal(), clientefor.getCodfilial() );
		ps.setInt( INSERE_CLI_FOR.CODFOR.ordinal(), clientefor.getCodfor() );
		ps.setString( INSERE_CLI_FOR.RAZFOR.ordinal(), clientefor.getRazfor() );
		ps.setInt( INSERE_CLI_FOR.CODEMP.ordinal(), clientefor.getCodemp() );
		ps.setInt( INSERE_CLI_FOR.CODFILIAL.ordinal(), clientefor.getCodfilial() ) ;
		ps.setInt( INSERE_CLI_FOR.CODTIPOFOR.ordinal(), clientefor.getCodtipofor() );
		ps.setInt( INSERE_CLI_FOR.CODEMPTF.ordinal(), clientefor.getCodemptf() );
		ps.setInt( INSERE_CLI_FOR.CODFILIALTF.ordinal(), clientefor.getCodfilialtf() );
		ps.setInt( INSERE_CLI_FOR.CODEMP.ordinal(), clientefor.getCodemp() );
		ps.setInt( INSERE_CLI_FOR.CODFILIAL.ordinal(), clientefor.getCodfilial() );
		ps.setString( INSERE_CLI_FOR.NOMEFOR.ordinal(), clientefor.getNomefor() );
		ps.setString( INSERE_CLI_FOR.PESSOAFOR.ordinal(), clientefor.getPessoafor() );
		ps.setString( INSERE_CLI_FOR.CNPJFOR.ordinal(), clientefor.getCnpjfor() );
		ps.setString( INSERE_CLI_FOR.CPFFOR.ordinal(), clientefor.getCpffor() );
		ps.setString( INSERE_CLI_FOR.INSCFOR.ordinal(), clientefor.getInscfor() );
		ps.setString( INSERE_CLI_FOR.ENDFOR.ordinal(), clientefor.getEndfor() );
		ps.setInt( INSERE_CLI_FOR.NUMFOR.ordinal(), clientefor.getNumfor() );
		ps.setString( INSERE_CLI_FOR.BAIRFOR.ordinal(), clientefor.getBairfor() );

		ps.setString( INSERE_CLI_FOR.CODMUNIC.ordinal(), clientefor.getCodmunic() );
		ps.setString( INSERE_CLI_FOR.SIGLAUF.ordinal(), clientefor.getSiglauf() );
		ps.setInt( INSERE_CLI_FOR.CODPAIS.ordinal(), clientefor.getCodpais() );
		ps.setString( INSERE_CLI_FOR.RGFOR.ordinal(), clientefor.getRgfor() );
		ps.setString( INSERE_CLI_FOR.DDDFONEFOR.ordinal(), clientefor.getDddfonefor() );
		ps.setString( INSERE_CLI_FOR.FONEFOR.ordinal(), clientefor.getFonefor() );
		ps.setString( INSERE_CLI_FOR.FAXFOR.ordinal(), clientefor.getFaxfor() );
		ps.setString( INSERE_CLI_FOR.CELFOR.ordinal(), clientefor.getCelfor() );

		ps.executeUpdate();
		
	    getConn().commit();

		return codfor;
		
	}
	
	public ClienteFor getClienteFor(Integer codemp, Integer codfilial, Integer codcli, 
			Integer codempfr, Integer codfilialfr, Integer codfor, 
			Integer codemptf, Integer codfilialtf, Integer codtipofor) throws SQLException {
		ClienteFor result = new ClienteFor();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT  RAZCLI");
		sql.append(", NOMECLI, PESSOACLI, CNPJCLI, CPFCLI, INSCCLI, ENDCLI");
		sql.append(", NUMCLI, BAIRCLI, CODMUNIC, SIGLAUF, CODPAIS, RGCLI");
		sql.append(", DDDCLI, FONECLI, FAXCLI, CELCLI");

		PreparedStatement ps = getConn().prepareStatement( sql.toString() );
		ps.setInt( 1, codemp );
		ps.setInt( 2, codfilial );
		ps.setInt( 3, codcli );
		ResultSet rs = ps.executeQuery();

		result.setCodemp( codempfr );
		result.setCodfilial( codfilialfr );
		result.setCodfor( codfor );
		result.setRazfor( rs.getString( "RAZCLI" ) );
		result.setCodemptf( codemptf );
		result.setCodfilialtf( codfilialtf );
		result.setCodtipofor( codtipofor );
		result.setNomefor( rs.getString( "NOMECLI" ) );
		result.setPessoafor( rs.getString( "PESSOACLI" ) );
		result.setCnpjfor( rs.getString( "CNPJCLI" ) );
		result.setCpffor( rs.getString( "CPFCLI" ) );
		result.setInscfor( rs.getString( "INSCCLI" ) );
		result.setEndfor( rs.getString( "ENDCLI" ) );
		result.setNumfor( rs.getInt( "NUMCLI" ) );
		
		
		/*
		 * 			
			ps.setString( 17, txtEndCli.getVlrString() );
			ps.setInt( 18, txtNumCli.getVlrInteger() );
			ps.setString( 19, txtBairCli.getVlrString() );

			ps.setString( 20, txtCodMunic.getVlrString() );
			ps.setString( 21, txtSiglaUF.getVlrString() );
			ps.setInt( 22, txtCodPais.getVlrInteger() );
			ps.setString( 23, txtRgCli.getVlrString() );
			ps.setString( 24, txtDDDCli.getVlrString() );
			ps.setString( 25, txtFoneCli.getVlrString() );
			ps.setString( 26, txtFaxCli.getVlrString() );
			ps.setString( 27, txtCelCli.getVlrString() );*/
		return result;
	}


}


