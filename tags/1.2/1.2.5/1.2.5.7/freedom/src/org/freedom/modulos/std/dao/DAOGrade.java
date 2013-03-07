package org.freedom.modulos.std.dao;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JProgressBar;

import org.freedom.infra.dao.AbstractDAO;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.swing.component.JTablePad;
import org.freedom.modulos.std.business.object.UpdateVenda;
import org.freedom.modulos.std.view.frame.crud.special.FGrade.TAB_GRADE;


public class DAOGrade extends AbstractDAO {
	
	public DAOGrade( DbConnection cn) {

		super( cn );

	}
	
	public String executeProcedure ( int codemppd, int codfilialpd, int codprod, int codempmg, int codfilialmg, int codmodg
			, JTablePad tab, JProgressBar pbGrade) throws SQLException {

		/*    codemppd integer,
    codfilialpd integer,
    codprod integer,
    descprod varchar(100),
    descauxprod varchar(40),
    refprod varchar(20),
    codfabprod char(15),
    codbarprod char(13),
    codempmg integer,
    codfilialmg smallint,
    codmodg integer)
*/
		StringBuilder sql =  new StringBuilder("EXECUTE PROCEDURE EQADICPRODUTOSP(?,?,?,?,?,?,?,?,?,?,?,?)");
		PreparedStatement ps = null;
		String erros = "";
		for ( int i = 0; i < tab.getNumLinhas(); i++ ) {
			ps = getConn().prepareStatement( sql.toString() );
			if ( ( (Boolean) tab.getValor( i, TAB_GRADE.ADICPROD.ordinal() ) ).booleanValue() ) {
				int param = 1;
				ps.setInt( param++, codemppd );
				ps.setInt( param++, codfilialpd );
				ps.setInt( param++, codprod );
				ps.setString( param++, ( (String) tab.getValor( i, TAB_GRADE.DESCPROD.ordinal() ) ).trim() );
				ps.setString( param++, "" );
				ps.setString( param++, ( (String) tab.getValor( i, TAB_GRADE.REFPROD.ordinal() ) ).trim() );
				ps.setString( param++, ( (String) tab.getValor( i, TAB_GRADE.CODFABPROD.ordinal() ) ).trim() );
				ps.setString( param++, ( (String) tab.getValor( i, TAB_GRADE.CODBARPROD.ordinal() ) ).trim() );
				ps.setInt( param++, codempmg);
				ps.setInt( param++, codfilialmg );
				ps.setInt( param++, codmodg );
				ps.setString( param++, ( (String) tab.getValor( i, TAB_GRADE.DESCCOMPL.ordinal() ) ).trim() );
				
				try {
					ps.execute();
				} catch ( SQLException exception ) {
					erros = erros + "Desc.:" + tab.getValor( i, 1 ) + " Ref.:" + tab.getValor( i, 2 ) + "\n" + exception.getMessage() + "\n";
				}
				pbGrade.setValue( i + 1 );
				getConn().commit();
			}
			getConn().commit();
		}
		return erros;
	}

	public ResultSet getMontaTab( Integer codemp, Integer codfilial, Integer codmodg ) throws SQLException {

		StringBuilder sql = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		UpdateVenda valores = null;

		sql = new StringBuilder();
		sql.append("SELECT M.CODPROD,I.CODMODG,I.CODITMODG,I.CODVARG,V.DESCVARG,");
		sql.append("I.DESCITMODG,I.REFITMODG,I.CODFABITMODG,I.CODBARITMODG, M.DESCCOMPPRODMODG, I.DESCCOMPITMODG ");
		sql.append("FROM EQITMODGRADE I, EQVARGRADE V, EQMODGRADE M WHERE ");
		sql.append("M.CODEMP = ? AND M.CODFILIAL = ? AND I.CODMODG=?");
		sql.append(" AND V.CODVARG = I.CODVARG AND M.CODMODG=I.CODMODG ");
		sql.append("ORDER BY I.CODMODG,I.CODITMODG,I.CODVARG ");
		ps = getConn().prepareStatement( sql.toString() );
		int param = 1;

		ps.setInt( param++, codemp );
		ps.setInt( param++, codfilial );
		ps.setInt( param++, codmodg );

		rs = ps.executeQuery();

		return rs;
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
	
	private Integer getInteger( Integer value ) {
		Integer result = null;
		
		if (value == null){
			result = new Integer( 0 );
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


