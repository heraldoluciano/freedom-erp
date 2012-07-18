package org.freedom.modulos.crm.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.freedom.infra.dao.AbstractDAO;
import org.freedom.infra.model.jdbc.DbConnection;


public class DAOFicha extends AbstractDAO {
	
	public static enum FichaOrc {
		CODORC, CODCLI, RAZCLI, DTEMISSAO, DTVENC, CODPAG, DESCPAG, CODITORC, QTDITORC, PRECOITORC, TIPOORC;
	}

	private Object prefs[] = null;
	public enum CONT_PREFS{ USACTOSEQ, LAYOUTFICHAAVAL, CODPLANOPAG };
	
	
	public DAOFicha( DbConnection cn) {

		super( cn );
	}
	
	public StringBuilder getSqlFichaAval() {
		StringBuilder sql= new StringBuilder();
		
		sql.append("select f.razfilial, f.dddfilial, f.fonefilial ");
		sql.append(", f.endfilial, f.numfilial, f.siglauf siglauff ");
		sql.append(", f.bairfilial, f.cnpjfilial,f.emailfilial " );
		sql.append(", m.nomemunic nomemunicf ");
		sql.append(", c.razcto, c.endcto, c.numcto, c.baircto ");
		sql.append(", c.siglauf siglaufc, c.cpfcto, c.dddcto ");
		sql.append(", c.fonecto, c.cnpjcto, c.celcto ");
		sql.append(", c.contcto, mc.nomemunic nomemunicc, c.pessoacto, c.emailcto ");
		sql.append(", fa.seqfichaaval, fa.codmotaval, fa.dtfichaaval ");
		sql.append(", fa.localfichaaval, fa.finalifichaaval, fa.predentrfichaaval, fa.andarfichaaval ");
		sql.append(", fa.cobertfichaaval, fa.estrutfichaaval, fa.ocupadofichaaval, fa.mobilfichaaval, fa.janelafichaaval ");
		sql.append(", fa.sacadafichaaval, fa.outrosfichaaval, fa.obsfichaaval, fa.ocupadofichaaval ");
		sql.append("from sgfilial f ");
		sql.append("left outer join sgmunicipio m on ");
		sql.append("m.codmunic=f.codmunic and m.codpais=f.codpais ");
		sql.append("and m.siglauf=f.siglauf ");
		sql.append("inner join tkcontato c on ");
		sql.append("c.codemp=? and c.codfilial=? and c.codcto=? ");
		sql.append("left outer join sgmunicipio mc on ");
		sql.append("mc.codmunic=c.codmunic and mc.codpais=c.codpais ");
		sql.append("and mc.siglauf=c.siglauf ");
		sql.append("left outer join crfichaaval fa on ");
		sql.append("fa.codemp=? and fa.codfilial=? and fa.seqfichaaval=? ");
		sql.append("and fa.codempco = c.codemp and fa.codfilialco= c.codfilial and fa.codcto=c.codcto ");
		sql.append("left outer join critfichaaval itfa on ");
		sql.append("itfa.codemp = fa.codemp and itfa.codfilial= fa.codfilial and itfa.seqfichaaval= fa.seqfichaaval ");
		sql.append("where f.codemp=? and f.codfilial=? ");
 		
		return sql;
	}
	
	public Integer buscaCliente(int codemp, int codfilial, int codcto) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuilder sql = null;
		Integer codcli = null;
		
		try {
			sql = new StringBuilder();
			sql.append( "select c.codcli from tkcontcli c where c.codempcto=? and c.codfilialcto =? and c.codcto=?" );
			
			ps = getConn().prepareStatement( sql.toString() );
			int param = 1;
			ps.setInt( param++, codemp );
			ps.setInt( param++, codfilial );
			ps.setInt( param++, codcto );
						
			rs = ps.executeQuery();
			
			if(rs.next()){
				codcli = rs.getInt( "CODCLI" );
			}
			
		} finally {
			rs = null;
			ps = null;
			sql = null;
		}
		
		return codcli;
	}
	
	
	
	
	public ResultSet carregaOrcamentos(Integer codemp, Integer codfilial, Integer seqfichaaval) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs =null;

		try {
			StringBuilder sql = new StringBuilder();
			
			sql.append(" select o.codorc, o.codcli, c.razcli, o.dtorc, o.dtvencorc, o.codplanopag, p.descplanopag, ");
			sql.append(" it.coditorc, it.qtditorc, it.precoitorc, o.tipoorc ");
			sql.append(" from crfichaorc fo, vdorcamento o, vdcliente c, fnplanopag p, vditorcamento it ");
			sql.append(" where fo.codemp=? and fo.codfilial=? and fo.seqfichaaval=? and ");
			sql.append(" it.codemp=fo.codemp and it.codfilial=fo.codfilial and ");
			sql.append(" it.codorc=fo.codorc and it.tipoorc=fo.tipoorc and it.coditorc= fo.coditorc and ");
			sql.append(" o.codemp=it.codemp and o.codfilial=it.codfilial and o.codorc=it.codorc and o.tipoorc=it.tipoorc and ");
			sql.append(" c.codemp=o.codempcl and c.codfilial=o.codfilialcl and c.codcli=o.codcli and ");
			sql.append(" p.codemp=o.codemppg and p.codfilial=o.codfilialpg and p.codplanopag=o.codplanopag ");
			sql.append(" order by fo.codorc, fo.coditorc ");
			
			ps = getConn().prepareStatement( sql.toString() );
			ps.setInt( 1, codemp );
			ps.setInt( 2, codfilial );
			ps.setInt( 3, seqfichaaval );

			rs = ps.executeQuery();
		}finally{
			ps.close();
		}
			return rs;
		}
	
	
	public void setPrefs(Integer codemp, Integer codfilial) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuilder sql = null;
		
		prefs = new Object[ CONT_PREFS.values().length];
		
		try {
			sql = new StringBuilder("select p.usactoseq, p.layoutfichaaval, pf.codplanopag " );
			sql.append( "from sgprefere3 p , sgprefere4 pf "); 
			sql.append( "where  p.codemp=? and p.codfilial=? and pf.codemp=p.codemp and pf.codfilial=p.codfilial" );
			
			ps = getConn().prepareStatement( sql.toString() );
			ps.setInt( 1, codemp );
			ps.setInt( 2, codfilial );
			rs = ps.executeQuery();
			if ( rs.next() ) {
				
				prefs[ CONT_PREFS.USACTOSEQ.ordinal() ] = rs.getString( CONT_PREFS.USACTOSEQ.toString() );
				prefs[ CONT_PREFS.LAYOUTFICHAAVAL.ordinal() ] = rs.getString( CONT_PREFS.LAYOUTFICHAAVAL.toString() );
				prefs[ CONT_PREFS.CODPLANOPAG.ordinal() ] = rs.getString( CONT_PREFS.CODPLANOPAG.toString() );
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
