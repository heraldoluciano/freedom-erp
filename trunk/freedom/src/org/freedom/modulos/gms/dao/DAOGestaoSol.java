package org.freedom.modulos.gms.dao;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Vector;

import javax.swing.ImageIcon;

import org.freedom.infra.dao.AbstractDAO;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.functions.Funcoes;
import org.freedom.library.swing.frame.Aplicativo;
import org.freedom.modulos.gms.business.object.GestaoSol.GRID_SOL;


public class DAOGestaoSol extends AbstractDAO {

	public DAOGestaoSol( DbConnection cn ) {
		super(cn);		
	}
	public Vector<Vector<Object>> loadSolicitacao(ImageIcon imgColuna, ImageIcon imgAprovada, Integer codprod, Date dataini, 
			Date datafim, Integer codempam, Integer codfilialam, Integer codalmox, Integer codempcc, Integer codfilialcc, 
			Integer anocc, String codcc, Integer codempuu, Integer codfilialuu, String idusu ) throws SQLException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Vector<Vector<Object >> result = new Vector<Vector<Object>>();
		Vector<Object> row = null;
		StringBuilder sql = null;
		String where = "";
		boolean usaOr = false;
		boolean usaWhere = false;
		boolean usuario = ( !idusu.trim().equals( "" ) );
		boolean almoxarifado = false;
		boolean CC = ( !codcc.trim().equals( "" ) );
		
		
		if ( where.trim().equals( "" ) ) {
			where = " (IT.SitAprovItSol ='AP' OR IT.SitAprovItSol ='AT') ";
		}
		else {
			where = where + " OR (IT.SitAprovItSol ='AP' OR IT.SitAprovItSol ='AT') ";
			usaOr = true;
		}
		usaWhere = true;
		
		if ( usaWhere && usaOr )
			where = " AND (" + where + ") ";
		else if ( usaWhere )
			where = " AND " + where;
		else
			where = " AND IT.SitItSol='PE' ";

		if ( codprod > 0 )
			where += " AND IT.CODPROD = '" + codprod + "' ";

		if ( almoxarifado )
			where += " AND IT.CODALMOX=? AND IT.CODEMPAM=? AND IT.CODFILIALAM=? ";

		if ( CC )
			where += " AND O.ANOCC=? AND O.CODCC=? AND O.CODEMPCC=? AND O.CODFILIALCC=? ";

		if ( usuario )
			where += " AND (O.CODEMPUU=? AND O.CODFILIALUU=? AND O.IDUSU=?) ";
		
		try{
			sql = new StringBuilder();
			sql.append( "SELECT IT.CODPROD,IT.REFPROD, PD.DESCPROD, " );
			sql.append( "IT.QTDITSOL, IT.QTDAPROVITSOL, IT.CODSOL, IT.CODITSOL, PD.SLDPROD " );
			sql.append( "FROM CPSOLICITACAO O, CPITSOLICITACAO IT, EQPRODUTO PD " );
			sql.append( "WHERE O.CODEMP=IT.CODEMP AND O.CODFILIAL=IT.CODFILIAL AND O.CODSOL=IT.CODSOL " );
			sql.append( "AND PD.CODEMP=IT.CODEMP AND PD.CODFILIAL=IT.CODFILIAL AND PD.CODPROD=IT.CODPROD " );
			sql.append( "AND ((IT.DTAPROVITSOL BETWEEN ? AND ?) OR  (O.DTEMITSOL BETWEEN ? AND ?)) " );
			sql.append( where );
			//sql.append( "GROUP BY IT.CODPROD,IT.REFPROD, PD.DESCPROD, PD.SLDPROD " );
			sql.append( "ORDER BY PD.DESCPROD, IT.CODPROD, IT.REFPROD, IT.CODSOL, IT.CODITSOL, PD.SLDPROD " );
			
			ps = getConn().prepareStatement( sql.toString() );
			System.out.println(sql.toString());
			
			int param = 1;
			ps.setDate( param++, Funcoes.dateToSQLDate( dataini ) );
			ps.setDate( param++, Funcoes.dateToSQLDate( datafim ) );
			ps.setDate( param++, Funcoes.dateToSQLDate( dataini ) );
			ps.setDate( param++, Funcoes.dateToSQLDate( datafim ) );
		
			if ( almoxarifado ) {
				ps.setInt( param++, codalmox );
				ps.setInt( param++, codempam );
				ps.setInt( param++, codfilialam );
			}

			if ( CC ) {
				ps.setInt( param++, anocc );
				ps.setString( param++, codcc );
				ps.setInt( param++, codempcc );
				ps.setInt( param++, codfilialcc );
			}

			if ( usuario ) {
				ps.setInt( param++, codempuu );
				ps.setInt( param++, codfilialuu );
				ps.setString( param++, idusu );
			}
			rs = ps.executeQuery();
			
			while( rs.next() ){
				imgColuna = imgAprovada;
				
				row = new Vector<Object>();
				row.addElement( imgColuna );
				row.addElement( new Boolean(false) );
				row.addElement( new Integer(rs.getInt( GRID_SOL.CODPROD.toString() ) ) );
				row.addElement( getString( rs.getString( GRID_SOL.REFPROD.toString() ) ) );
				row.addElement( getString( rs.getString( GRID_SOL.DESCPROD.toString() ) ) );
				row.addElement( new Integer(rs.getInt( GRID_SOL.QTDITSOL.toString() ) ) );
				row.addElement( new Integer(rs.getInt( GRID_SOL.QTDAPROVITSOL.toString() ) ) );
				row.addElement( new Integer(rs.getInt( GRID_SOL.CODSOL.toString() ) ) );
				row.addElement( new Integer(rs.getInt( GRID_SOL.CODITSOL.toString() ) ) );
				row.addElement( getBigDecimal( rs.getBigDecimal( GRID_SOL.SLDPROD.toString() ) ) );
				result.addElement( row );
			
			}
		} finally {
			ps = null;
			rs = null;
		}
		return result;		
	}
	
	public void criaCotacao( int codprod ) {
		
		
		String sSQLautoincrement = "SELECT coalesce(MAX(SS.CODSUMSOL) + 1, 1) AS CODSUMSOL FROM cpsumsol SS WHERE SS.CODEMP=? AND SS.CODFILIAL=?";

		String sSQLsumsol = "INSERT INTO CPSUMSOL " + "(CODEMP, CODFILIAL, CODSUMSOL, CODEMPPD, CODFILIALPD, CODPROD, REFPROD, QTDITSOL, QTDAPROVITSOL, SITITSOL, SITAPROVITSOL) " + "SELECT ?, ?, ?, " + "IT.CODEMPPD, IT.CODFILIALPD, IT.CODPROD,IT.REFPROD, "
				+ "SUM(IT.QTDITSOL), SUM(IT.QTDAPROVITSOL), 'PE', 'PE' " + "FROM CPSOLICITACAO O, CPITSOLICITACAO IT " + "WHERE O.CODEMP=IT.CODEMP AND O.CODFILIAL=IT.CODFILIAL AND O.CODSOL=IT.CODSOL " + "AND CODEMPPD = ? AND CODFILIALPD = ? AND CODPROD = ? "
				+ "group BY IT.CODEMPPD, IT.CODFILIALPD, IT.CODPROD, IT.REFPROD";

		String sSQLitsumsol = "INSERT INTO CPITSUMSOL " + "(CODEMP, CODFILIAL, CODSUMSOL, CODEMPSC, CODFILIALSC, CODSOL, CODITSOL) " + "SELECT ?, ?, ?, IT.CODEMP, IT.CODFILIAL, IT.CODSOL, IT.CODITSOL " + "FROM CPSOLICITACAO O, CPITSOLICITACAO IT "
				+ "WHERE O.CODEMP=IT.CODEMP AND O.CODFILIAL=IT.CODFILIAL AND O.CODSOL=IT.CODSOL " + "AND CODEMPPD = ? AND CODFILIALPD = ? AND CODPROD = ?";

		int codsumsol = 0;
		try {
			PreparedStatement ps = getConn().prepareStatement( sSQLautoincrement );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, Aplicativo.iCodFilial );

			ResultSet rs = ps.executeQuery();

			if ( rs.next() ) {
				codsumsol = rs.getInt( "CODSUMSOL" );
			}
		} catch ( SQLException err ) {
			err.printStackTrace();
		}

		try {
			PreparedStatement ps2 = getConn().prepareStatement( sSQLsumsol );

			ps2.setInt( 1, Aplicativo.iCodEmp );
			ps2.setInt( 2, Aplicativo.iCodFilial );
			ps2.setInt( 3, codsumsol );
			ps2.setInt( 4, Aplicativo.iCodEmp );
			ps2.setInt( 5, Aplicativo.iCodFilial );
			ps2.setInt( 6, codprod );

			ps2.execute();

			getConn().commit();
		} catch ( SQLException err ) {
			err.printStackTrace();
		}

		try {
			PreparedStatement ps3 = getConn().prepareStatement( sSQLitsumsol );

			ps3.setInt( 1, Aplicativo.iCodEmp );
			ps3.setInt( 2, Aplicativo.iCodFilial );
			ps3.setInt( 3, codsumsol );
			ps3.setInt( 4, Aplicativo.iCodEmp );
			ps3.setInt( 5, Aplicativo.iCodFilial );
			ps3.setInt( 6, codprod );

			ps3.execute();

			getConn().commit();
		} catch ( SQLException err ) {
			err.printStackTrace();
		}
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
	
}
