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
		ps.setInt( 1, clientefor.getCodemp() );
		ps.setInt( 2, clientefor.getCodfilial() );
		ps.setInt( 3, clientefor.getCodfor() );
		ps.setString( 4, clientefor.getRazfor() );
		ps.setInt( 5, clientefor.getCodemp() );
		ps.setInt( 6, clientefor.getCodfilial() ) ;
		ps.setInt( 7, clientefor.getCodtipofor() );
		ps.setInt( 8, clientefor.getCodemptf() );
		ps.setInt( 9, clientefor.getCodfilialtf() );
		ps.setInt( 10, clientefor.getCodemp() );
		ps.setInt( 11, clientefor.getCodfilial() );
		ps.setString( 12, clientefor.getNomefor() );
		ps.setString( 13, clientefor.getPessoafor() );
		ps.setString( 14, clientefor.getCnpjfor() );
		ps.setString( 15, clientefor.getCpffor() );
		ps.setString( 16, clientefor.getInscfor() );
		ps.setString( 17, clientefor.getEndfor() );
		ps.setInt( 18, clientefor.getNumfor() );
		ps.setString( 19, clientefor.getBairfor() );

		ps.setString( 20, clientefor.getCodmunic() );
		ps.setString( 21, clientefor.getSiglauf() );
		ps.setInt( 22, clientefor.getCodpais() );
		ps.setString( 23, clientefor.getRgfor() );
		ps.setString( 24, clientefor.getDddfonefor() );
		ps.setString( 25, clientefor.getFonefor() );
		ps.setString( 26, clientefor.getFaxfor() );
		ps.setString( 27, clientefor.getCelfor() );

		ps.executeUpdate();

		return codfor;
	}


}


