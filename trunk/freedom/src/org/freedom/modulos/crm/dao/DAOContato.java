package org.freedom.modulos.crm.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.freedom.infra.dao.AbstractDAO;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.frame.Aplicativo;


public class DAOContato extends AbstractDAO {
	private Object prefs[] = null;
	public enum CONT_PREFS{ USACTOSEQ, LAYOUTFICHAAVAL };
	
	
	public DAOContato( DbConnection cn) {

		super( cn );
	}
	
	public Integer testaCodPK( String sTabela ) {

		PreparedStatement ps = null;
		ResultSet rs = null;
		Integer retorno = new Integer( 0 );

		try {
			ps = getConn().prepareStatement( "SELECT ISEQ FROM SPGERANUM(?,?,?)" );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "TKCONTATO" ) );
			ps.setString( 3, "CO" );

			rs = ps.executeQuery();
			rs.next();

			retorno = new Integer( rs.getString( 1 ) );

			rs.close();
			ps.close();

			getConn().commit();

		} catch ( SQLException err ) {
			err.printStackTrace();
		} finally {
			ps = null;
			rs = null;
		}

		return retorno;

	}

	public StringBuilder getSqlFichaAval() {
		StringBuilder sql= new StringBuilder();
		sql.append(" select ct.codcontr, ct.dtinicio, ct.dtfim ");
		sql.append(", ct.codcli, cl.razcli ");
		sql.append(", cl.cpfcli, cl.agenciacli ");
		sql.append(", cl.ncontabcocli, itpf.codconv, pf.nomeemp ");
		sql.append("from vdcontrato ct ");
		sql.append("inner join vdcliente cl on ");
		sql.append("cl.codemp=ct.codempcl and cl.codfilial=ct.codfilialcl and cl.codcli=ct.codcli ");
		sql.append("inner join vdmodcontr mc on ");
		sql.append("mc.codemp=ct.codempmc and mc.codfilial=ct.codfilialmc and ");
		sql.append("mc.codmodcontr=ct.codmodcontr ");
		sql.append("left outer join vditcontrato ic on ");
		sql.append("ic.codemp=ct.codemp and ic.codfilial=ct.codfilial and ic.codcontr=ct.codcontr ");
		sql.append("left outer join sgitprefere6 itpf on ");
		sql.append("itpf.codempbo=mc.codempbo and itpf.codfilialbo=mc.codfilialbo and itpf.codbanco=mc.codbanco ");
		sql.append("and itpf.tipofebraban=(case when mc.tpmodcontr='S' then '01' else '00' end) ");
		sql.append("left outer join sgprefere6  pf on ");
		sql.append("pf.codemp=itpf.codemp and pf.codfilial=itpf.codfilial ");
		return sql;
	}
	
	public void setPrefs(Integer codemp, Integer codfilial) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuilder sql = null;
		
		prefs = new Object[ CONT_PREFS.values().length];
		
		try {
			sql = new StringBuilder("select p.usactoseq, p.layoutfichaaval " );
			sql.append( "from sgprefere3 p "); 
			sql.append( "where  p.codemp=? and p.codfilial=?" );
			
			ps = getConn().prepareStatement( sql.toString() );
			ps.setInt( 1, codemp );
			ps.setInt( 2, codfilial );
			rs = ps.executeQuery();
			if ( rs.next() ) {
				
				prefs[ CONT_PREFS.USACTOSEQ.ordinal() ] = rs.getString( CONT_PREFS.USACTOSEQ.toString() );
				prefs[ CONT_PREFS.LAYOUTFICHAAVAL.ordinal() ] = rs.getString( CONT_PREFS.LAYOUTFICHAAVAL.toString() );
				
			}
			rs.close();
			ps.close();
			getConn().commit();
		} finally {
			ps = null;
			rs = null;
			sql = null;
		}
	}
	
	public Object[] getPrefs() {
		return this.prefs;
	}

}
