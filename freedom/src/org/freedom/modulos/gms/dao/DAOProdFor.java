package org.freedom.modulos.gms.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Vector;

import org.freedom.infra.dao.AbstractDAO;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.functions.Funcoes;
import org.freedom.modulos.crm.business.object.Atendimento;
import org.freedom.modulos.crm.business.object.Atendimento.PROC_IU;
import org.freedom.modulos.crm.business.object.ProdFor.EColProdFor;


public class DAOProdFor extends AbstractDAO {

	public DAOProdFor( DbConnection connection ) {
		super( connection );
		
	}
	
	public Vector<Vector<Object>> loadProdFor(Integer codemp, Integer codfilial, Date dataini, Date datafim ) throws SQLException{
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuilder sql = null;
		Vector<Vector<Object >> result = new Vector<Vector<Object>>();
		
		Vector<Object> row = null;
		try{
			
		sql = new StringBuilder("Select ");
			sql.append( "p.descprod,  p.codprod, f.razfor, f.codfor " );
			sql.append( "from eqproduto p, cpforneced f, cpcompra c, cpitcompra ic " );
			sql.append( "where p.codemp=? and p.codfilial=? and ic.codemppd=p.codemp and " );
			sql.append( "ic.codfilialpd=p.codfilial and ic.codprod=p.codprod and " );
			sql.append( "c.codemp=ic.codemp and c.codfilial=ic.codfilial and " );
			sql.append( "c.codcompra=ic.codcompra and f.codemp=c.codempfr and " );
			sql.append( "c.codemp=ic.codemp and c.codfilial=ic.codfilial and " );
			sql.append( "c.codcompra=ic.codcompra and f.codemp=c.codempfr and " );
			sql.append( "f.codfilial=c.codfilialfr and f.codfor=c.codfor and c.dtemitcompra between ? and ? " );
			sql.append( "and not exists " );
			sql.append( "(select * from cpprodfor pf where pf.codemp=f.codemp  and pf.codfilial=f.codfilial " );
			sql.append( "and pf.codfor=f.codfor and pf.codemppd=p.codemp and pf.codfilialpd=p.codfilial and pf.codprod=p.codprod)" );
			sql.append( "group by p.descprod, p.codprod, f.razfor, f.codfor " );
			sql.append( " order by p.descprod, p.codprod, f.razfor, f.codfor " );
			
			ps = getConn().prepareStatement( sql.toString() );
			ps.setInt( 1, codemp );
			ps.setInt( 2, codfilial );
			ps.setDate( 3, Funcoes.dateToSQLDate( dataini ) );
			ps.setDate( 4, Funcoes.dateToSQLDate( datafim ) );
			rs = ps.executeQuery();
	
			while( rs.next() ){
			
				row = new Vector<Object>();
				
				row.addElement( getString(  rs.getString( EColProdFor.DESCPROD.toString() ) ) );
				row.addElement( new Integer( rs.getInt( EColProdFor.CODPROD.toString() ) ) );
				row.addElement( getString(  rs.getString( EColProdFor.RAZFOR.toString() ) ) );
				row.addElement( new Integer( rs.getInt( EColProdFor.CODFOR.toString() ) ) );
				
				result.addElement( row );
			}
			rs.close();
			ps.close();
			getConn().commit();
			
		} finally {
			ps = null;
			rs = null;
		}
		return result;	
	}
	
	
	
	public void insert( Integer codemp, Integer codfilial, Date dataini, Date datafim ) throws SQLException {
		
		StringBuilder sql = new StringBuilder();

		sql.append( "insert into cpprodfor ( codemp , codfilial, codfor, codemppd, codfilialpd, codprod ) " );
		sql.append( "select f.codemp, f.codfilial, f.codfor ,p.codemp , p.codfilial, p.codprod " );
		sql.append( "from eqproduto p, cpforneced f, cpcompra c, cpitcompra ic where p.codemp=? " );
		sql.append( "and p.codfilial=?  and ic.codemppd=p.codemp and " );
		sql.append( "ic.codfilialpd=p.codfilial and ic.codprod=p.codprod " );
		sql.append( "and c.codemp=ic.codemp and c.codfilial=ic.codfilial and " );
		sql.append( "c.codcompra=ic.codcompra and f.codemp=c.codempfr and " );
		sql.append( "f.codfilial=c.codfilialfr and f.codfor=c.codfor and c.dtemitcompra between ? and ? " );
		sql.append( "and not exists (select * from cpprodfor pf " );
		sql.append( "where pf.codemp=f.codemp and pf.codfilial=f.codfilial " );
		sql.append( "and pf.codfor=f.codfor and pf.codemppd=p.codemp and " );
		sql.append( "pf.codfilialpd=p.codfilial and pf.codprod=p.codprod) " );
				
		PreparedStatement ps = getConn().prepareStatement( sql.toString() );
		ps.setInt( 1, codemp );
		ps.setInt( 2, codfilial );
		ps.setDate( 3, Funcoes.dateToSQLDate( dataini ) );
		ps.setDate( 4, Funcoes.dateToSQLDate( datafim ) );
	
	
	ps.execute();
	ps.close();

	getConn().commit();

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

}
