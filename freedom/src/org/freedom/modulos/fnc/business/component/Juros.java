package org.freedom.modulos.fnc.business.component;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import org.freedom.library.functions.Funcoes;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.frame.Aplicativo;


public class Juros {


	
	public static BigDecimal aplicaJuros(Date dtvenc, BigDecimal vlrparc) {

		PreparedStatement ps = null;
		ResultSet rs = null;
		GregorianCalendar cal = null;
		GregorianCalendar calVenc = null;
		StringBuilder sql = new StringBuilder();
		BigDecimal vlrjuros = new BigDecimal(0);

		try {
			sql.append( "select first 1 p.codtbj, t.tipotbj, it.percittbj " );
			sql.append( "from " );		
			sql.append( "sgprefere1 p, fntbjuros t, fnittbjuros it ");
			sql.append( "where ");
			sql.append( "t.codemp=p.codemptj and t.codfilial=p.codfilialtj and t.codtbj=p.codtbj ");
			sql.append( "and it.codemp=t.codemp and it.codfilial=t.codfilial and it.codtbj=t.codtbj ");
			sql.append( "and it.anoittbj <= ? and it.mesittbj <= ? ");
			sql.append( "and p.codemp=? and p.codfilial=? ");
			sql.append( "order by it.anoittbj desc, itmesittbj desc ");

			
			ps = Aplicativo.getInstace().con.prepareStatement( sql.toString() );
			cal = new GregorianCalendar();
			calVenc = new GregorianCalendar();
			calVenc.setTime( dtvenc );
			
			ps.setInt( 1, cal.get( Calendar.YEAR ) );
			ps.setInt( 2, cal.get( Calendar.MONTH ) + 1 );
			ps.setInt( 3, Aplicativo.iCodEmp );
			ps.setInt( 4, Aplicativo.iCodFilial );
			rs = ps.executeQuery();

			if ( rs.next() ) {
				switch ( rs.getString( "TipoTBJ" ).toCharArray()[ 0 ] ) {
					case 'D' :
						vlrjuros = vlrparc.multiply( new BigDecimal( ( Funcoes.getNumDiasAbs( dtvenc, new Date() ) ) * rs.getDouble( "PERCITTBJ" ) ) )
								.divide( new BigDecimal( 100 ), 2, BigDecimal.ROUND_HALF_UP ) ;
						break;
					case 'M' :
						vlrjuros = vlrparc.multiply( new BigDecimal( ( Funcoes.getNumDiasAbs( dtvenc, new Date() ) ) * rs.getDouble( "PERCITTBJ" ) ) )
								.divide( new BigDecimal( 100 * 30 ), 2, BigDecimal.ROUND_HALF_UP ) ;
						break;
					case 'B' :
						vlrjuros = vlrparc.multiply( new BigDecimal( ( Funcoes.getNumDiasAbs( dtvenc, new Date() ) ) * rs.getDouble( "PERCITTBJ" ) ) )
								.divide( new BigDecimal( 100 * 60 ), 2, BigDecimal.ROUND_HALF_UP ) ;
						break;
					case 'T' :
						vlrjuros = vlrparc.multiply( new BigDecimal( ( Funcoes.getNumDiasAbs( dtvenc, new Date() ) ) * rs.getDouble( "PERCITTBJ" ) ) )
								.divide( new BigDecimal( 100 * 90 ), 2, BigDecimal.ROUND_HALF_UP ) ;
						break;
					case 'S' :
						vlrjuros = vlrparc.multiply( new BigDecimal( ( Funcoes.getNumDiasAbs( dtvenc, new Date() ) ) * rs.getDouble( "PERCITTBJ" ) ) )
								.divide( new BigDecimal( 100 * 182 ), 2, BigDecimal.ROUND_HALF_UP );
						break;
					case 'A' :
						vlrjuros = vlrparc.multiply( new BigDecimal( ( Funcoes.getNumDiasAbs( dtvenc, new Date() ) ) * rs.getDouble( "PERCITTBJ" ) ) )
								.divide( new BigDecimal( 100 * 365 ), 2, BigDecimal.ROUND_HALF_UP ) ;
						break;
				}
			}
			rs.close();
			ps.close();
			Aplicativo.getInstace().con.commit();
		} 
		catch ( SQLException err ) {
			Funcoes.mensagemErro( null, "Erro ao buscar juros do sistema!\n" + err.getMessage(), true, null, err );
			err.printStackTrace();
		} 
		finally {
			ps = null;
			rs = null;
			cal = null;
			calVenc = null;
			sql = null;
		}
		return vlrjuros;
	}

	public static boolean getJurosPosCalc() {

		boolean bRet = false;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sSQL = "SELECT JUROSPOSCALC FROM SGPREFERE1 WHERE CODEMP=? AND CODFILIAL=?";
		try {
			ps = Aplicativo.getInstace().con.prepareStatement( sSQL );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "SGPREFERE1" ) );
			rs = ps.executeQuery();
			if ( rs.next() ) {
				bRet = rs.getString( "JUROSPOSCALC" ) != null && rs.getString( "JUROSPOSCALC" ).equals( "S" );
			}
			rs.close();
			ps.close();
			Aplicativo.getInstace().con.commit();
		} 
		catch ( SQLException err ) {
			Funcoes.mensagemErro( null, "Erro ao buscar juros pos calculados.\n" + err.getMessage(), true, null, err );
		} 
		finally {
			ps = null;
			rs = null;
			sSQL = null;
		}
		return bRet;
	}
	
}
