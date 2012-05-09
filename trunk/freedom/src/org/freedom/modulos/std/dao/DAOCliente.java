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
	
	public int getCodFor() throws SQLException	{

		int codigo = 0;
		StringBuilder sSQL = new StringBuilder();
		PreparedStatement ps = null;

		sSQL.append( "SELECT MAX( F.CODFOR ) FROM CPFORNECED F WHERE F.CODEMP=? AND F.CODFILIAL=?" );

		ps = getConn().prepareStatement( sSQL.toString() );
		ps.setInt( 1, Aplicativo.iCodEmp );
		ps.setInt( 2, ListaCampos.getMasterFilial( "CPFORNECED" ) );
		ResultSet rs = ps.executeQuery();

		if ( rs.next() ) {
			codigo = rs.getInt( 1 ) + 1;
		}

		return codigo;
	}

	public int inserirFor(ClienteFor clientefor) throws SQLException {

		int codfor = getCodFor();
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

		return codfor;
	}


}


