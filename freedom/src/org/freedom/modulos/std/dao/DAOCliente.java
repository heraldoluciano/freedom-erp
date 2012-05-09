package org.freedom.modulos.std.dao;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.freedom.infra.dao.AbstractDAO;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.modulos.std.business.object.ClienteFor;
import org.freedom.modulos.std.business.object.ClienteFor.INSERE_CLI_FOR;
import org.freedom.modulos.std.business.object.ClienteFor.INSERE_FOR;


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

	public int insereFor(Integer codempfr, Integer codfilialfr, Integer codempcl, Integer codfilialcl, Integer codcli, 
			Integer codemptf, Integer codfilialtf, Integer codtipofor ) throws SQLException {

		StringBuilder sSQL = new StringBuilder();
		PreparedStatement ps = null;

		int codfor = getCodFor( codempfr, codfilialfr );
		
		ClienteFor clientefor = getClienteFor( codempcl, codfilialcl, codcli, codempfr, codfilialfr, codfor, codemptf, codfilialtf, codtipofor );

		sSQL.append( "INSERT INTO CPFORNECED " );
		sSQL.append( "( CODEMP, CODFILIAL, CODFOR, RAZFOR, CODEMPTF, CODFILIALTF, CODTIPOFOR, " );
		sSQL.append( " NOMEFOR, PESSOAFOR, CNPJFOR, CPFFOR, INSCFOR, ENDFOR, NUMFOR, BAIRFOR, CODMUNIC, SIGLAUF, CODPAIS, RGFOR, DDDFONEFOR, FONEFOR, FAXFOR, CELFOR ) " );
		sSQL.append( "VALUES( ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?, ? ) " );

		ps = getConn().prepareStatement( sSQL.toString() );
		ps.setInt( INSERE_FOR.CODEMP.ordinal(), clientefor.getCodemp() );
		ps.setInt( INSERE_FOR.CODFILIAL.ordinal(), clientefor.getCodfilial() );
		ps.setInt( INSERE_FOR.CODFOR.ordinal(), clientefor.getCodfor() );
		ps.setString( INSERE_FOR.RAZFOR.ordinal(), clientefor.getRazfor() );
		ps.setInt( INSERE_FOR.CODEMPTF.ordinal(), clientefor.getCodemp() );
		ps.setInt( INSERE_FOR.CODFILIALTF.ordinal(), clientefor.getCodfilial() ) ;
		ps.setInt( INSERE_FOR.CODTIPOFOR.ordinal(), clientefor.getCodtipofor() );
		ps.setString( INSERE_FOR.NOMEFOR.ordinal(), clientefor.getNomefor() );
		ps.setString( INSERE_FOR.PESSOAFOR.ordinal(), clientefor.getPessoafor() );
		ps.setString( INSERE_FOR.CNPJFOR.ordinal(), clientefor.getCnpjfor() );
		ps.setString( INSERE_FOR.CPFFOR.ordinal(), clientefor.getCpffor() );
		ps.setString( INSERE_FOR.INSCFOR.ordinal(), clientefor.getInscfor() );
		ps.setString( INSERE_FOR.ENDFOR.ordinal(), clientefor.getEndfor() );
		ps.setInt( INSERE_FOR.NUMFOR.ordinal(), clientefor.getNumfor() );
		ps.setString( INSERE_FOR.BAIRFOR.ordinal(), clientefor.getBairfor() );
		ps.setString( INSERE_FOR.CODMUNIC.ordinal(), clientefor.getCodmunic() );
		ps.setString( INSERE_FOR.SIGLAUF.ordinal(), clientefor.getSiglauf() );
		ps.setInt( INSERE_FOR.CODPAIS.ordinal(), clientefor.getCodpais() );
		ps.setString( INSERE_FOR.RGFOR.ordinal(), clientefor.getRgfor() );
		ps.setString( INSERE_FOR.DDDFONEFOR.ordinal(), clientefor.getDddfonefor() );
		ps.setString( INSERE_FOR.FONEFOR.ordinal(), clientefor.getFonefor() );
		ps.setString( INSERE_FOR.FAXFOR.ordinal(), clientefor.getFaxfor() );
		ps.setString( INSERE_FOR.CELFOR.ordinal(), clientefor.getCelfor() );

		ps.executeUpdate();
		
		return codfor;
		
	}
	
	public void insereCliFor(Integer codemp, Integer codfilial, Integer codcli ,Integer codempfr, Integer codfilialfr, Integer codfor ) throws SQLException{
		StringBuilder sSQL = new StringBuilder();
		PreparedStatement ps = null;
		
		sSQL.append( "INSERT INTO EQCLIFOR " );
		sSQL.append( "( CODEMP, CODFILIAL, CODCLI, CODFILIALFR, CODEMPFR, CODFOR ) " );
		sSQL.append( "VALUES( ?,?,?,?,?,? ) " );
		
		ps = getConn().prepareStatement( sSQL.toString() );
		
		ps.setInt( INSERE_CLI_FOR.CODEMP.ordinal(), codemp );
		ps.setInt( INSERE_CLI_FOR.CODFILIAL.ordinal(), codfilial );
		ps.setInt( INSERE_CLI_FOR.CODCLI.ordinal(), codcli );
		ps.setInt( INSERE_CLI_FOR.CODEMPFR.ordinal(), codempfr );
		ps.setInt( INSERE_CLI_FOR.CODFILIALFR.ordinal(), codfilialfr);
		ps.setInt( INSERE_CLI_FOR.CODFOR.ordinal(), codfor );

		ps.executeUpdate();
		
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
		sql.append(" FROM VDCLIENTE" );
		sql.append(" WHERE CODEMP=? AND CODFILIAL=? AND CODCLI=? ");
		
		PreparedStatement ps = getConn().prepareStatement( sql.toString() );
		ps.setInt( 1, codemp );
		ps.setInt( 2, codfilial );
		ps.setInt( 3, codcli );
		ResultSet rs = ps.executeQuery();
		if(rs.next()){

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
			result.setBairfor( rs.getString( "BAIRCLI" ) );
			result.setCodmunic( rs.getString("CODMUNIC") );
			result.setSiglauf( rs.getString( "SIGLAUF" ) );
			result.setCodpais( rs.getInt( "CODPAIS" ) );
			result.setRgfor( rs.getString( "RGCLI" ) );
			result.setDddfonefor( rs.getString( "DDDCLI" ) );
			result.setFonefor( rs.getString( "FONECLI" ) );
			result.setFaxfor( rs.getString( "FAXCLI" ) );
			result.setCelfor( rs.getString( "CELCLI" ) );
	
		}		
		return result;
	}
	
	
	public Map<String, Object> getPrefere(Integer codemp, Integer codfilial, String usu, Integer codemppf, Integer codfilialpf) throws SQLException {

		Map<String, Object> retorno = new HashMap<String, Object>();
		StringBuilder sSQL = new StringBuilder();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			sSQL.append( "SELECT P.SETORVENDA, P.RGCLIOBRIG, P.CLIMESMOCNPJ, P.CNPJOBRIGCLI," );
			sSQL.append( "P.CONSISTEIECLI, P.CONSISTCPFCLI, P.CONSISTEIEPF, " );
			sSQL.append( "(CASE WHEN P.USUATIVCLI='N' THEN 'S' " );
			sSQL.append( "WHEN P.USUATIVCLI='S' AND U.ATIVCLI='S' THEN 'S' " );
			sSQL.append( "ELSE 'N' " );
			sSQL.append( "END) HABATIVCLI, COALESCE (P.CODTIPOFOR,0) CODTIPOFOR, USAIBGECLI, USAIBGEFOR, USAIBGETRANSP, BUSCACEP " );
			sSQL.append( "FROM SGPREFERE1 P LEFT OUTER JOIN SGUSUARIO U " );
			sSQL.append( "ON U.CODEMP=? AND U.CODFILIAL=? AND U.IDUSU=? " );
			sSQL.append( "WHERE P.CODEMP=? AND P.CODFILIAL=?" );

			ps = getConn().prepareStatement( sSQL.toString() );
			ps.setInt( 1, codemp );
			ps.setInt( 2, codfilial );
			ps.setString( 3, usu );
			ps.setInt( 4, codemppf );
			ps.setInt( 5, codfilialpf );

			rs = ps.executeQuery();

			if ( rs.next() ) {

				retorno.put( "SETORVENDA", new Boolean( "CA".indexOf( rs.getString( "SetorVenda" ) ) >= 0 ) );
				retorno.put( "RGCLIOBRIG", new Boolean( "S".equals( rs.getString( "RGCLIOBRIG" ) ) ) );
				retorno.put( "CLIMESMOCNPJ", new Boolean( "S".equals( rs.getString( "CLIMESMOCNPJ" ) ) ) );
				retorno.put( "CLIMESMOCNPJ", new Boolean( "S".equals( rs.getString( "CLIMESMOCNPJ" ) ) ) );
				retorno.put( "CONSISTEIECLI", new Boolean( "S".equals( rs.getString( "CONSISTEIECLI" ) ) ) );
				retorno.put( "CONSISTCPFCLI", new Boolean( "S".equals( rs.getString( "CONSISTCPFCLI" ) ) ) );
				retorno.put( "HABATIVCLI", new Boolean( "S".equals( rs.getString( "HABATIVCLI" ) ) ) );
				retorno.put( "CODTIPOFOR", rs.getInt( "CODTIPOFOR" ) );
				retorno.put( "USAIBGECLI", new Boolean( "S".equals( rs.getString( "USAIBGECLI" ) ) ) );
				retorno.put( "USAIBGEFOR", new Boolean( "S".equals( rs.getString( "USAIBGEFOR" ) ) ) );
				retorno.put( "USAIBGETRANSP", new Boolean( "S".equals( rs.getString( "USAIBGETRANSP" ) ) ) );
				retorno.put( "BUSCACEP", new Boolean( "S".equals( rs.getString( "BUSCACEP" ) ) ) );
				retorno.put( "CONSISTEIEPF", new Boolean( "S".equals( rs.getString( "CONSISTEIEPF" ) ) ) );

			}

			rs.close();
			ps.close();

			getConn().commit();
	
		} finally {
			sSQL = null;
			ps = null;
			rs = null;
		}
		return retorno;
	}

}


