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

	private int inserirFor() throws SQLException {

		int codfor = getCodFor();
		StringBuilder sSQL = new StringBuilder();
		PreparedStatement ps = null;

		sSQL.append( "INSERT INTO CPFORNECED " );
		sSQL.append( "( CODEMP, CODFILIAL, CODFOR, RAZFOR, CODEMPTF, CODFILIALTF, CODTIPOFOR, CODEMPBO, CODFILIALBO, CODEMPHP, " );
		sSQL.append( "CODFILIALHP, NOMEFOR, PESSOAFOR, CNPJFOR, CPFFOR, INSCFOR, ENDFOR, NUMFOR, BAIRFOR, CODMUNIC, SIGLAUF, CODPAIS, RGFOR, DDDFONEFOR, FONEFOR, FAXFOR, CELFOR ) " );
		sSQL.append( "VALUES( ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?, ?,?,?,?,? ) " );

		ps = getConn().prepareStatement( sSQL.toString() );
		ps.setInt( 1, Aplicativo.iCodEmp );
		ps.setInt( 2, ListaCampos.getMasterFilial( "CPFORNECED" ) );
		ps.setInt( 3, codfor );
		/*ps.setString( 4, txtRazCli.getVlrString() );
		ps.setInt( 5, Aplicativo.iCodEmp );
		ps.setInt( 6, ListaCampos.getMasterFilial( "CPFORNECED" ) );
		ps.setInt( 7, (Integer) bPref.get( "CODTIPOFOR" ) );
		ps.setInt( 8, Aplicativo.iCodEmp );
		ps.setInt( 9, ListaCampos.getMasterFilial( "CPFORNECED" ) );
		ps.setInt( 10, Aplicativo.iCodEmp );
		ps.setInt( 11, ListaCampos.getMasterFilial( "CPFORNECED" ) );
		ps.setString( 12, txtNomeCli.getVlrString() );
		ps.setString( 13, rgPessoa.getVlrString() );
		ps.setString( 14, txtCnpjCli.getVlrString() );
		ps.setString( 15, txtCpfCli.getVlrString() );
		ps.setString( 16, txtInscCli.getVlrString() );
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
		ps.setString( 27, txtCelCli.getVlrString() );
*/
		ps.executeUpdate();

		return codfor;
	}


}


