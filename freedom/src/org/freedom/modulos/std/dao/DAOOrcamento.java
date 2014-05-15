package org.freedom.modulos.std.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.freedom.infra.dao.AbstractDAO;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.swing.frame.Aplicativo;
import org.freedom.modulos.std.business.component.Orcamento;

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
		ps.setInt( 1, getCodemp() );
		ps.setInt( 2, codfilial );
		ps.setInt( 3, codvend );
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
			ps.setInt( 3, numest );
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

	public Integer getCodtipocli(Integer codfilialcl, Integer codcli) throws Exception {
		Integer result = null;

		try {
			PreparedStatement ps = getConn().prepareStatement( "select codtipocli from vdcliente where codemp=? and codfilial=? and codcli=?" );
			int param = 1;
			ps.setInt( param++, getCodemp());
			ps.setInt( param++, codcli );
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


}
