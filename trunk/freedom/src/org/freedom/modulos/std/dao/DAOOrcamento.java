package org.freedom.modulos.std.dao;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Vector;

import org.freedom.infra.dao.AbstractDAO;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.functions.Funcoes;
import org.freedom.library.swing.frame.Aplicativo;
import org.freedom.modulos.std.business.component.Orcamento;
import org.freedom.modulos.std.business.component.Orcamento.OrcVenda;

public class DAOOrcamento extends AbstractDAO {
	
	public DAOOrcamento( DbConnection cn) {

		super( cn );

	}
	
	public DAOOrcamento( DbConnection cn, Integer codemp, Integer codfilial) {
		this( cn );
		setCodemp( codemp );
		setCodfilial( codfilial );
	}
	
	public Integer getClcomiss(Integer codfilial, Integer codvend) throws SQLException {
		Integer result = null;
		PreparedStatement ps = getConn().prepareStatement( "select codclcomis from vdvendedor where codemp=? and codfilial=? and codvend=?" );
		int param = 1;
		ps.setInt( param++, getCodemp() );
		ps.setInt( param++, codfilial );
		ps.setInt( param++, codvend );
		ResultSet rs = ps.executeQuery();
		if ( rs.next() ) {
			result = rs.getInt( "codclcomis" );
		}
		rs.close();
		ps.close();
		getConn().commit();
		return result;
	}
	
	
	public Object[] getPrefere(Integer codfilialp1, Integer codfilialei, Integer numest) throws Exception {

		Object[] result = new Object[ Orcamento.PrefOrc.values().length ];
		StringBuffer sql = new StringBuffer();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			sql.append( "select p1.usarefprod, p1.usaliqrel, p1.tipoprecocusto, coalesce(p1.codtipomov2, 0) codtipomov2 , p1.contestoq " );
			sql.append( ",p1.ordnota, p1.desccompped, p1.usaorcseq, p1.obsclivend, p1.recalcpcorc,coalesce(p1.titorctxt01,'') titorctxt01 " );
			sql.append( ",p1.vendamatprim, p1.tabtransporc, p1.visualizalucr, p1.classorc, coalesce(p1.descorc,'Orçamento') descorc " );
			sql.append( ",p4.usaloteorc, p4.usabuscagenprod, coalesce(p4.diasvencorc,0) diasvencorc, coalesce(p4.codcli,0) codcli " );
			sql.append( ",coalesce(p4.codplanopag,0) codplanopag, coalesce(p4.prazo,0) prazo " );
			sql.append( ",fi.contribipifilial, p1.tipocustoluc, habvlrtotitorc, p1.comissaodesconto, p1.vdprodqqclas " );
			sql.append( ", p1.bloqdesccomporc, p1.bloqprecoorc, p1.bloqcomissorc, p1.permitimporcantap, p1.bloqeditorcaposap ");
			sql.append(", p3.codmodelor, p1.replicaorc, p1.sqlreplicaorc ");
			sql.append( "from sgprefere1 p1,  sgprefere3 p3, sgprefere4 p4, sgfilial fi " );
			sql.append( "where p1.codemp=? and p1.codfilial=? and " );
			sql.append( "p3.codemp=p1.codemp and p3.codfilial=p1.codfilial and " );
			sql.append( "p4.codemp=p1.codemp and p4.codfilial=p1.codfilial and " );
			sql.append( "fi.codemp=p1.codemp and fi.codfilial=p1.codfilial " );

			ps = getConn().prepareStatement( sql.toString() );
			int param = 1;
			ps.setInt( param++, getCodemp() );
			ps.setInt( param++, codfilialp1 );

			rs = ps.executeQuery();

			if ( rs.next() ) {

				if ( rs.getString( "USALIQREL" ) == null ) {
					result[ Orcamento.PrefOrc.USALIQREL.ordinal() ] = new Boolean( false );
					throw new Exception("Preecha a opção de desconto em preferências !");
				}
				else {
					result[ Orcamento.PrefOrc.USALIQREL.ordinal() ] = new Boolean( rs.getString( "UsaLiqRel" ).trim().equals( "S" ) );
				}

				result[ Orcamento.PrefOrc.USAREFPROD.ordinal() ] = new Boolean( rs.getString( "UsaRefProd" ).trim().equals( "S" ) );
				result[ Orcamento.PrefOrc.TIPOPRECOCUSTO.ordinal() ] = new Boolean( rs.getString( "TipoPrecoCusto" ).equals( "M" ) );
				result[ Orcamento.PrefOrc.CODTIPOMOV2.ordinal() ] = new Integer( rs.getInt( "CODTIPOMOV2" ) );
				result[ Orcamento.PrefOrc.DESCCOMPPED.ordinal() ] = new Boolean( rs.getString( "DescCompPed" ).equals( "S" ) );
				result[ Orcamento.PrefOrc.USAORCSEQ.ordinal() ] = new Boolean( rs.getString( "UsaOrcSeq" ).equals( "S" ) );
				result[ Orcamento.PrefOrc.OBSCLIVEND.ordinal() ] = new Boolean( rs.getString( "ObsCliVend" ).equals( "S" ) );
				result[ Orcamento.PrefOrc.RECALCPCORC.ordinal() ] = new Boolean( rs.getString( "ReCalcPCOrc" ).equals( "S" ) );
				result[ Orcamento.PrefOrc.USABUSCAGENPROD.ordinal() ] = new Boolean( rs.getString( "USABUSCAGENPROD" ).equals( "S" ) );
				result[ Orcamento.PrefOrc.USALOTEORC.ordinal() ] = new Boolean( rs.getString( "USALOTEORC" ).equals( "S" ) );
				result[ Orcamento.PrefOrc.CONTESTOQ.ordinal() ] = new Boolean( rs.getString( "CONTESTOQ" ).equals( "S" ) );
				result[ Orcamento.PrefOrc.TITORCTXT01.ordinal() ] = rs.getString( "TitOrcTxt01" );
				result[ Orcamento.PrefOrc.VENDAMATPRIM.ordinal() ] = "S".equals( rs.getString( "VendaMatPrim" ) );
				result[ Orcamento.PrefOrc.VISUALIZALUCR.ordinal() ] = "S".equals( rs.getString( "VISUALIZALUCR" ) );
				result[ Orcamento.PrefOrc.DIASVENCORC.ordinal() ] = rs.getInt( "DIASVENCORC" );
				result[ Orcamento.PrefOrc.CODCLI.ordinal() ] = new Integer(rs.getInt( "CODCLI" ));
				result[ Orcamento.PrefOrc.CODPLANOPAG.ordinal() ] = rs.getString( "CODPLANOPAG" );
				result[ Orcamento.PrefOrc.PRAZO.ordinal() ] = rs.getString( "PRAZO" );
				result[ Orcamento.PrefOrc.CLASSORC.ordinal() ] = rs.getString( "CLASSORC" );
				result[ Orcamento.PrefOrc.DESCORC.ordinal() ] = rs.getString( "DESCORC" );
				result[ Orcamento.PrefOrc.ORDNOTA.ordinal() ] = rs.getString( "ORDNOTA" );
				result[ Orcamento.PrefOrc.CONTRIBIPI.ordinal() ] = rs.getString( "CONTRIBIPIFILIAL" );
				result[ Orcamento.PrefOrc.ABATRANSP.ordinal() ] = rs.getString( "TABTRANSPORC" );
				result[ Orcamento.PrefOrc.TIPOCUSTO.ordinal() ] = rs.getString( "TIPOCUSTOLUC" );
				result[ Orcamento.PrefOrc.HABVLRTOTITORC.ordinal() ] = new Boolean( rs.getString( "HabVlrTotItOrc" ).equals( "S" ) );
				result[ Orcamento.PrefOrc.COMISSAODESCONTO.ordinal() ] = "S".equals( rs.getString( "COMISSAODESCONTO" ) );
				result[ Orcamento.PrefOrc.VDPRODQQCLAS.ordinal() ] = "S".equals( rs.getString( Orcamento.PrefOrc.VDPRODQQCLAS.toString() ) );
				result[ Orcamento.PrefOrc.BLOQDESCCOMPORC.ordinal() ] = "S".equals( rs.getString( Orcamento.PrefOrc.BLOQDESCCOMPORC.toString() ) );
				result[ Orcamento.PrefOrc.BLOQPRECOORC.ordinal() ] = "S".equals( rs.getString( Orcamento.PrefOrc.BLOQPRECOORC.toString() ) );
				result[ Orcamento.PrefOrc.BLOQCOMISSORC.ordinal() ] = "S".equals( rs.getString( Orcamento.PrefOrc.BLOQCOMISSORC.toString() ) );
				result[ Orcamento.PrefOrc.PERMITIMPORCANTAP.ordinal() ] = "S".equals( rs.getString( Orcamento.PrefOrc.PERMITIMPORCANTAP.toString() ) );
				result[ Orcamento.PrefOrc.BLOQEDITORCAPOSAP.ordinal() ] = "S".equals( rs.getString( Orcamento.PrefOrc.BLOQEDITORCAPOSAP.toString() ) );
				result[ Orcamento.PrefOrc.CODMODELOR.ordinal() ] = rs.getInt(  Orcamento.PrefOrc.CODMODELOR.toString() ) ;
				result[ Orcamento.PrefOrc.REPLICAORC.ordinal() ] = "S".equals( rs.getString( Orcamento.PrefOrc.REPLICAORC.toString() ) );
				result[ Orcamento.PrefOrc.SQLREPLICAORC.ordinal() ] = rs.getString( Orcamento.PrefOrc.SQLREPLICAORC.toString() );
			}

			rs.close();
			ps.close();
			getConn().commit();

			sql.delete( 0, sql.length() );
			sql.append( "select impgrafica from sgestacaoimp where codemp=? and codfilial=? and imppad='S' and codest=?");
			ps = getConn().prepareStatement( sql.toString() );
			param = 1;
			ps.setInt( param++, getCodemp() );
			ps.setInt( param++, codfilialei );
			ps.setInt( param++, numest );
			rs = ps.executeQuery();

			if ( rs.next() ) {
				if ( ( rs.getString( "impgrafica" ) != null ) && ( !rs.getString( "impgrafica" ).equals( "S" ) ) ) {
					result[ Orcamento.PrefOrc.SMODONOTA.ordinal() ] = "T";
				}
				else {
					result[ Orcamento.PrefOrc.SMODONOTA.ordinal() ] = "G";
				}
			}
			rs.close();
			ps.close();
			getConn().commit();
		} catch ( SQLException err ) {
			err.printStackTrace();
			try {
				getConn().rollback();
			} catch ( SQLException errcomm) {
				errcomm.printStackTrace();
			}
			throw new Exception("Erro ao carregar tabela de preferências!\n" + err.getMessage());
		} finally {
			ps = null;
			rs = null;
		}
		return result;
	}

	public boolean testaCodlote(Integer codfilialle, Integer codprod, String codlote) throws Exception {

		boolean result = false;
		try {
			PreparedStatement ps = 
					getConn().prepareStatement( "select sldliqlote from eqlote where codlote=? and codprod=? and codemp=? and codfilial=?" );
			int param = 1;
			ps.setString( param++, codlote );
			ps.setInt( param++, codprod );
			ps.setInt( param++, getCodemp() );
			ps.setInt( param++, codfilialle );
			ResultSet rs = ps.executeQuery();
			if ( rs.next() ) {
				if ( rs.getFloat( 1 ) > 0.0f ) {
					result = true;
				} else {
					throw new Exception("LOTE SEM SALDO!" );
				}
			}
			// else { Funcoes.mensagemErro( this, "Cód.lote é requerido." ); }

			rs.close();
			ps.close();
			getConn().commit();
		} catch ( SQLException err ) {
			err.printStackTrace();
			try {
				getConn().rollback();
			} catch (SQLException errroll) {
				errroll.printStackTrace();
			}
			throw new Exception("Erro ao consultar a tabela de lotes (EQLOTE)!\n"+err.getMessage());
		}
		return result;
	}

	public Integer getCodtipocli(Integer codfilialcl, Integer codcli) throws Exception {
		Integer result = null;
		if (codcli!=null) {
			try {
				PreparedStatement ps = getConn().prepareStatement( "select codtipocli from vdcliente where codemp=? and codfilial=? and codcli=?" );
				int param = 1;
				ps.setInt( param++, getCodemp());
				ps.setInt( param++, codfilialcl );
				ps.setInt( param++, codcli );
				ResultSet rs = ps.executeQuery();
				if ( rs.next() ) {
					result = rs.getInt( "codtipocli" );
				}
				rs.close();
				ps.close();
				getConn().commit();
			} catch ( SQLException err ) {
				try {
					getConn().rollback();
				} catch (SQLException errrow) {
					errrow.printStackTrace();
				}
				err.printStackTrace();
				throw new Exception("Erro ao carregar tipo de cliente !\n"+err.getMessage());
			}
		}
		return result;

	}
	
	public Integer getVendedor(Integer codfilialcl, Integer codcli, Integer codfilialus, String idusu) throws Exception {

		StringBuilder sql = new StringBuilder();
		Integer result = null;

		try {

			sql.append( "select codvend from vdcliente where codemp=? and codfilial=? and codcli=?");
			PreparedStatement ps = getConn().prepareStatement( sql.toString() );
			int param = 1;
			ps.setInt( param++, getCodemp() );
			ps.setInt( param++, codfilialcl );
			ps.setInt( param++, codcli );
			ResultSet rs = ps.executeQuery();
			if ( rs.next() ) {
				result = new Integer(rs.getInt( "codvend" ));
			}
			rs.close();
			ps.close();
			getConn().commit();
			if (result==null) {
				sql.delete( 0, sql.length() );
				sql.append( "select codvend from atatendente where idusu=? and codempus=? and codfilialus=?" );
				ps = getConn().prepareStatement( sql.toString() );
				param = 1;
				ps.setString( param++, idusu );
				ps.setInt( param++, getCodemp() );
				ps.setInt( param++, codfilialus );
				rs = ps.executeQuery();
	
				if ( rs.next() ) {
					result = rs.getInt( "codvend" );
				}
	
				rs.close();
				ps.close();
				getConn().commit();
			}
		} catch ( SQLException err ) {
			err.printStackTrace();
			try {
				getConn().rollback();
			} catch (SQLException errroll) {
				errroll.printStackTrace();
			}
			throw new Exception( "Erro ao buscar o comissionado. O usuário '" + Aplicativo.getUsuario().getIdusu() + "' é um comissionado?\n" + err.getMessage() );
		}
		return result;
	}

	public Integer getCodTran(Integer codorc, Integer codfilialp1) throws Exception {

		Integer result = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuilder sql = new StringBuilder();

		try {

			sql.append( "select c.codtran from vdcliente c, vdorcamento v ");
			sql.append( "where c.codcli=v.codcli and c.codemp=v.codempcl " );
			sql.append( "and v.codorc=?  and v.codemp=? and v.codfilial=?" );

			ps = getConn().prepareStatement( sql.toString() );
			int param = 1;
			ps.setInt( param++, codorc );
			ps.setInt( param++, getCodemp() );
			ps.setInt( param++, getCodfilial() );

			rs = ps.executeQuery();

			if ( rs.next() ) {
				result = rs.getInt( "codtran" );
			}
			rs.close();
			ps.close();
			getConn().commit();

			if ( result==null || result.intValue() == 0 ) {

				sql.delete( 0, sql.length() );
				sql.append( "select codtran from sgprefere1 where codemp=? and codfilial=? " );
				param = 1;
				ps = getConn().prepareStatement( sql.toString() );
				ps.setInt( param++, getCodemp() );
				ps.setInt( param++, codfilialp1 );

				rs = ps.executeQuery();

				if ( rs.next() ) {
					result = rs.getInt( "codtran" );
				}
			}

			rs.close();
			ps.close();
			getConn().commit();
		} catch ( SQLException err ) {
			err.printStackTrace();
			try {
				getConn().rollback();
			} catch (SQLException errroll) {
				errroll.printStackTrace();
			}
			throw new Exception("Erro ao buscar transportador !\n"+err.getMessage());
		} finally {
			ps = null;
			rs = null;
			sql = null;
		}

		return result;
	}
	public Integer copiaOrcamento(Integer codorc, int[] vals) throws Exception {

		Integer result = null;

		try {
			PreparedStatement ps = getConn().prepareStatement( "select iret from vdcopiaorcsp(?,?,?,?,?)" );
			int param = 1;
			ps.setInt( param++, getCodemp() );
			ps.setInt( param++, getCodfilial() );
			ps.setInt( param++, codorc );
			ps.setInt( param++, vals[ 1 ] );
			ps.setInt( param++, vals[ 0 ] );
			ResultSet rs = ps.executeQuery();
			if ( rs.next() ) {
				result = rs.getInt( "iret" );
			}
			rs.close();
			ps.close();
			getConn().commit();

		} catch ( SQLException err ) {
			try {
				err.printStackTrace();
				getConn().rollback();
			} catch (SQLException errroll) {
				errroll.printStackTrace();
			}
			throw new Exception("Erro ao copiar o orçamento!\n" + err.getMessage());
		}

		return result;
	}

	public BigDecimal carregaComisIt(Object[] prefs, Integer codfilialrc, BigDecimal percdescitorc) throws Exception {
		BigDecimal result = null;
		if ( !(Boolean) prefs[ Orcamento.PrefOrc.COMISSAODESCONTO.ordinal() ] )
			return result;
		StringBuilder sql = new StringBuilder();
		sql.append( "select first 1 desconto, comissao from vdregcomisdesc " );
		sql.append( "where codemp=? and codfilial=? and desconto");

		/**
		 * A ideia inicial era um sql unico com union e unico resultado porém não consegui aplicar um order by
		 * que trabalha-se da forma a qual seria necessária
		 */

		PreparedStatement ps = null;
		ResultSet rs = null;
		BigDecimal vlrcomissao = new BigDecimal(0);

		try {

			StringBuilder sqlexecute = new StringBuilder(sql);
			sqlexecute.append( "=?" );
			ps = getConn().prepareStatement( sqlexecute.toString() );
			int param = 1;
			ps.setInt( param++, getCodemp() );
			ps.setInt( param++, getCodfilial() );
			ps.setBigDecimal( param++, percdescitorc );

			rs = ps.executeQuery();
			if( rs.next() ){
				vlrcomissao = rs.getBigDecimal( "comissao" );
			} else {
				rs.close();
				ps.close();
				getConn().commit();
				sqlexecute = new StringBuilder(sql);
				sqlexecute.append( " < ? " );
				sqlexecute.append( "order by desconto desc " );
				ps = getConn().prepareStatement( sqlexecute.toString() );
				param = 1;
				ps.setInt( param++, getCodemp() );
				ps.setInt( param++, getCodfilial() );
				ps.setBigDecimal( param++, percdescitorc );
				rs = ps.executeQuery();
				if(rs.next()){
					vlrcomissao =  rs.getBigDecimal( "comissao" );
				}
				rs.close();
				ps.close();
				getConn().commit();
			}

			result = vlrcomissao;
		} catch ( SQLException err ) {
			err.printStackTrace();
			try {
				getConn().rollback();
			} catch (SQLException errroll) {
				errroll.printStackTrace();
			}
			throw new Exception("Erro carregando comissão do item!\n"+err.getMessage());
		}
		return result;
	}

	public Vector<Vector<Object>> carregaPedidos(Integer codorc) throws Exception {
		Vector<Vector<Object>> result = new Vector<Vector<Object>>();

		try {

			StringBuilder sql = new StringBuilder();
			sql.append( "select v.codvenda, v.docvenda, v.serie, v.codcli, c.razcli, v.dtemitvenda, v.dtsaidavenda, v.codplanopag, p.descplanopag," );
			sql.append( "it.coditvenda, it.qtditvenda, it.precoitvenda, it.vlrliqitvenda, v.tipovenda " );
			sql.append( "from vdvendaorc vo, vdvenda v, vdcliente c, fnplanopag p, vditvenda it " );
			sql.append( "where vo.codempor=? and vo.codfilialor=? and vo.codorc=? and " );
			sql.append( "it.codemp=vo.codemp and it.codfilial=vo.codfilial and " );
			sql.append( "it.codvenda=vo.codvenda and it.tipovenda=vo.tipovenda and it.coditvenda=vo.coditvenda and " );
			sql.append( "v.codemp=it.codemp and v.codfilial=it.codfilial and v.codvenda=it.codvenda and v.tipovenda=it.tipovenda and " );
			sql.append( "c.codemp=v.codempcl and c.codfilial=v.codfilialcl and c.codcli=v.codcli and " );
			sql.append( "p.codemp=v.codemppg and p.codfilial=v.codfilialpg and p.codplanopag=v.codplanopag " );
			sql.append( "order by vo.codvenda, vo.coditvenda" );
			PreparedStatement ps = getConn().prepareStatement( sql.toString() );
			int param = 1;
			ps.setInt( param++, getCodemp() );
			ps.setInt( param++, getCodfilial() );
			ps.setInt( param++, codorc );
			ResultSet rs = ps.executeQuery();
			while ( rs.next() ) {
				Vector<Object> row = new Vector<Object>();
				setValue(rs.getInt( "codvenda" ), row, OrcVenda.CODVENDA.ordinal() ); 
				setValue(rs.getString( "docvenda" ), row, OrcVenda.DOCVENDA.ordinal() );
				setValue(rs.getString( "serie" ), row, OrcVenda.SERIE.ordinal() );
				setValue(rs.getInt( "codcli" ), row, OrcVenda.CODCLI.ordinal() );
				setValue(rs.getString( "razcli" ), row, OrcVenda.RAZCLI.ordinal() );
				setValue(Funcoes.sqlDateToDate( rs.getDate( "dtemitvenda" ) ), row, OrcVenda.DTEMISSAO.ordinal() );
				setValue(Funcoes.sqlDateToDate( rs.getDate( "dtsaidavenda" ) ), row, OrcVenda.DTSAIDA.ordinal() );
				setValue(rs.getInt( "codplanopag" ), row, OrcVenda.CODPAG.ordinal() );
				setValue(rs.getString( "descplanopag" ), row, OrcVenda.DESCPAG.ordinal() );
				setValue(rs.getInt( "coditvenda" ), row, OrcVenda.CODITVENDA.ordinal() );
				setValue(rs.getBigDecimal( "qtditvenda" ), row, OrcVenda.QTDITVENDA.ordinal() );
				setValue(rs.getBigDecimal( "precoitvenda" ), row, OrcVenda.PRECOITVENDA.ordinal() );
				setValue(rs.getBigDecimal( "vlrliqitvenda" ), row, OrcVenda.VLRLIQITVENDA.ordinal() );
				setValue(rs.getString( "tipovenda" ), row, OrcVenda.TIPOVENDA.ordinal() );
				result.addElement( row );
			}
			rs.close();
			ps.close();
			getConn().commit();
		} catch ( SQLException err ) {
			err.printStackTrace();
			try {
				getConn().rollback();
			} catch (SQLException errroll) {
				errroll.printStackTrace();
			}
			throw new Exception( "Erro ao consultar pedidos!\n" + err.getMessage() );
		}
		return result;
		
	}

	public HashMap<String, Object> getPermissaoUsu(Integer codfilialus, String idusu) throws Exception{
		HashMap<String, Object> result = new HashMap<String, Object>();
		try {
			StringBuilder sql = new StringBuilder();
			sql.append( "select visualizalucr from sgusuario where codemp=? and codfilial=? and idusu=?" );
			PreparedStatement ps = getConn().prepareStatement( sql.toString() );
			int param = 1;
			ps.setInt( param++, getCodemp() );
			ps.setInt( param++, codfilialus );
			ps.setString( param++, idusu );
			ResultSet rs = ps.executeQuery();
			if ( rs.next() ) {
				result.put( "VISUALIZALUCR", rs.getString( "visualizalucr" ) );
			}
			rs.close();
			ps.close();
			getConn().commit();

		} catch ( SQLException err ) {
			err.printStackTrace();
			try {
				getConn().rollback();
			} catch (SQLException errroll) {
				errroll.printStackTrace();
			}
			throw new Exception("Erro carregando permissões de lucratividade!\n" + err.getMessage());
		}
		return result;
	}
}
