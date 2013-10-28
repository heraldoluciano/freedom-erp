/**
 * @version 24/10/2013 <BR>
 * @author Setpoint Tecnologia em Informática Ltda./Robson Sanchez <BR>
 * 
 *         Projeto: Freedom <BR>
 * 
 *         Pacote: org.freedom.modulos.std.dao <BR>
 *         Classe: * @(#)DAOTrocaRefprod.java <BR>
 * 
 *         Este arquivo é parte do sistema Freedom-ERP, o Freedom-ERP é um software livre; você pode redistribui-lo e/ou <BR>
 *         modifica-lo dentro dos termos da Licença Pública Geral GNU como publicada pela Fundação do Software Livre (FSF); <BR>
 *         na versão 2 da Licença, ou (na sua opnião) qualquer versão. <BR>
 *         Este programa é distribuido na esperança que possa ser util, mas SEM NENHUMA GARANTIA; <BR>
 *         sem uma garantia implicita de ADEQUAÇÂO a qualquer MERCADO ou APLICAÇÃO EM PARTICULAR. <BR>
 *         Veja a Licença Pública Geral GNU para maiores detalhes. <BR>
 *         Você deve ter recebido uma cópia da Licença Pública Geral GNU junto com este programa, se não, <BR>
 *         de acordo com os termos da LPG-PC <BR>
 * <BR>
 * 
 *         Classe responsável pela persistência dos dados, auxiliar a tela de troca de referência de produtos.
 * 
 */
package org.freedom.modulos.std.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import org.freedom.infra.dao.AbstractDAO;
import org.freedom.infra.model.jdbc.DbConnection;

public class DAOTrocaRefprod extends AbstractDAO {

	public enum DATABASE {
		FireBird
	}

	private enum QUERY_ORDER {
		First, Second
	}

	public enum SIT_LOG_TROCARP {
		OK, ER
	}

	private int update_count = 0;

	public DAOTrocaRefprod( DbConnection connection, Integer codemp, Integer codfilial ) {

		super( connection, codemp, codfilial );
	}

	public StringBuffer seekRefprod( String refprod ) throws Exception {

		StringBuffer found = new StringBuffer();
		StringBuilder sql = new StringBuilder();
		sql.append( "select codprod, descprod, refprod from eqproduto where codemp=? and codfilial=? and refprod=?" );
		try {
			PreparedStatement ps = getConn().prepareStatement( sql.toString() );
			int param = 1;
			ps.setInt( param++, getCodemp() );
			ps.setInt( param++, getCodfilial() );
			ps.setString( param++, refprod );
			ResultSet rs = ps.executeQuery();
			if ( rs.next() ) {
				found.append( "Referência está sendo utilizada em outro produto.\nCódigo: " );
				found.append( rs.getInt( "codprod" ) );
				found.append( " - " );
				found.append( "Descrição: " );
				found.append( rs.getString( "descprod" ).trim() );
			}
		} catch ( SQLException e ) {
			e.printStackTrace();
			getConn().rollback();
			throw new Exception( e.getMessage() );
		}
		return found;
	}

	public Vector<Table> selectTableChange() throws Exception {

		Vector<Table> result = new Vector<Table>();
		StringBuilder sql = getSqlTables( DATABASE.FireBird );
		try {
			PreparedStatement ps = getConn().prepareStatement( sql.toString() );
			ResultSet rs = ps.executeQuery();
			while ( rs.next() ) {
				result.addElement( new Table( rs.getString( "table_name" ), rs.getString( "field_name" ), "S".equals( rs.getString( "emmanut" ) ), "S".equals( rs.getString( "hcodemp" ) ), "S".equals( rs.getString( "hcodfilial" ) ) ) );
			}
			rs.close();
			ps.close();
			getConn().commit();
		} catch ( SQLException e ) {
			e.printStackTrace();
			getConn().rollback();
			throw new Exception( "Erro carregando tabelas !\n" + e.getMessage() );
		}
		return result;
	}

	public Vector<Change> selectValuesChange( int id ) throws Exception {

		Vector<Change> result = new Vector<Change>();
		StringBuilder sql = new StringBuilder();
		sql.append( "select itrf.id_it, itrf.codemppd, itrf.codfilialpd" );
		sql.append( ", itrf.codprod, itrf.refprodold, itrf.refprodnew " );
		sql.append( "from eqtrocarefprod trf, eqittrocarefprod itrf " );
		sql.append( "where itrf.id=trf.id and trf.id=? " );
		sql.append( "and itrf.situacao<>'OK' " );
		sql.append( "order by codprod, refprodold " );
		int param = 1;
		try {
			PreparedStatement ps = getConn().prepareStatement( sql.toString() );
			ps.setInt( param, id );
			ResultSet rs = ps.executeQuery();
			while ( rs.next() ) {
				result.add( new Change( rs.getInt( "id_it" ), rs.getInt( "codemppd" ), rs.getInt( "codemppd" ), rs.getInt( "codprod" ), rs.getString( "refprodold" ), rs.getString( "refprodnew" ) ) );
			}
			rs.close();
			ps.close();
			getConn().commit();
		} catch ( SQLException e ) {
			e.printStackTrace();
			getConn().rollback();
			throw new Exception( "Erro consultando produtos para a troca !\n" + e.getMessage() );
		}
		return result;
	}

	private void setParamChange( PreparedStatement ps, QUERY_ORDER qo, Change value, Table table ) throws SQLException {

		int param = 1;
		if ( qo == QUERY_ORDER.First ) {
			ps.setString( param++, value.refprodnew );
		}
		if ( table.getHcodemp() ) {
			ps.setInt( param++, getCodemp() );
		}
		if ( table.getHcodfilial() ) {
			ps.setInt( param++, table.getCodfilial() );
		}
		if ( qo == QUERY_ORDER.First ) {
			ps.setString( param++, value.refprodold );
		}
	}

	private StringBuilder getSqlChange( QUERY_ORDER qo, Change value, Table table ) {

		StringBuilder result = new StringBuilder();
		String sep = "";
		result.append( "update " );
		result.append( table.table_name );
		result.append( " set " );
		if ( qo == QUERY_ORDER.First ) {
			if ( table.emmanut ) {
				result.append( "emmanut='S', " );
			}
			result.append( table.field_name );
			result.append( "=? " );
		}
		else if ( qo == QUERY_ORDER.Second ) {
			result.append( "emmanut='N' " );
		}
		result.append( "where " );
		if ( table.getHcodemp() ) {
			result.append( "codemp=? " );
			sep = " and ";
		}
		if ( table.getHcodfilial() ) {
			result.append( sep );
			result.append( "codfilial=? " );
			sep = " and ";
		}
		if ( qo == QUERY_ORDER.First ) {
			result.append( sep );
			result.append( table.field_name );
			result.append( "=?" );
		}
		else if ( qo == QUERY_ORDER.Second ) {
			result.append( sep );
			result.append( "emmanut='S'" );
		}
		return result;
	}

	private StringBuilder getSqlUpdateProduto() {

		StringBuilder result = new StringBuilder();
		result.append( "update eqproduto set ativoprod=? " );
		result.append( "where codemp=? and codfilial=? and codprod=? " );
		return result;
	}

	private void setParamUpdateProduto( QUERY_ORDER qo, PreparedStatement ps, Change value ) throws SQLException {

		int param = 1;
		String ativoprod = "N";
		if ( qo == QUERY_ORDER.Second ) {
			ativoprod = "S";
		}
		ps.setString( param++, ativoprod );
		ps.setInt( param++, value.getCodemppd() );
		ps.setInt( param++, value.getCodfilialpd() );
		ps.setInt( param++, value.getCodprod() );
	}

	private void changeActivityProd( boolean inactive, Change value ) throws SQLException {

		StringBuilder sql = getSqlUpdateProduto();
		PreparedStatement ps = getConn().prepareStatement( sql.toString() );
		if ( inactive ) {
			setParamUpdateProduto( QUERY_ORDER.First, ps, value );
		}
		else {
			setParamUpdateProduto( QUERY_ORDER.Second, ps, value );
		}
		ps.executeUpdate();
		getConn().commit();
	}

	public void executeChange( Change value, Table table ) throws Exception {

		SIT_LOG_TROCARP situacao = SIT_LOG_TROCARP.OK;
		Exception err = null;
		try {
			changeActivityProd( true, value );
			StringBuilder sql = getSqlChange( QUERY_ORDER.First, value, table );
			PreparedStatement ps = getConn().prepareStatement( sql.toString() );
			setParamChange( ps, QUERY_ORDER.First, value, table );
			ps.executeUpdate();
			if ( table.getEmmanut() ) {
				sql = getSqlChange( QUERY_ORDER.Second, value, table );
				setParamChange( ps, QUERY_ORDER.Second, value, table );
				ps.executeUpdate();
			}
			getConn().commit();
			changeActivityProd( true, value );
		} catch ( SQLException e ) {
			e.printStackTrace();
			err = e;
			situacao = SIT_LOG_TROCARP.ER;
			getConn().rollback();
			throw e;
		} finally {
			executeLog( value, table, situacao, err );
		}
	}

	private StringBuilder getMensagemLog( Change value, Table table, SIT_LOG_TROCARP situacao, Exception e ) {

		StringBuilder result = new StringBuilder();
		result.append( "Tabela: " );
		result.append( table.getTable_name() );
		result.append( " Campo: " );
		result.append( table.getField_name() );
		result.append( "\nValor anterior: " );
		result.append( value.getRefprodold() );
		result.append( " Valor novo: " );
		result.append( value.getRefprodnew() );
		result.append( "\nSituacao: " );
		if ( situacao == SIT_LOG_TROCARP.OK ) {
			result.append( "SUCESSO !" );
		}
		else {
			result.append( "ERRO:\n" );
			if ( e != null ) {
				result.append( e.getMessage() );
			}
		}
		return result;
	}

	private void updateSitucaoItem( int id, SIT_LOG_TROCARP situacao ) throws SQLException {

		StringBuilder sql = new StringBuilder();
		sql.append( "update eqittrocarefprod set situacao=? where id_it=? " );
		PreparedStatement ps = getConn().prepareStatement( sql.toString() );
		int param = 1;
		ps.setString( param++, situacao.toString() );
		ps.setInt( param++, id );
		ps.executeUpdate();
		getConn().commit();
	}

	private void executeLog( Change value, Table table, SIT_LOG_TROCARP situacao, Exception e ) throws Exception {

		StringBuilder sql = getSqlInsertLog();
		StringBuilder mensagem = getMensagemLog( value, table, situacao, e );
		try {
			PreparedStatement ps = getConn().prepareStatement( sql.toString() );
			setParamInsertLog( ps, value, table, situacao, mensagem.toString() );
			ps.executeUpdate();
			getConn().commit();
		} catch ( SQLException err ) {
			err.printStackTrace();
			getConn().rollback();
			throw err;
		} finally {
			updateSitucaoItem( value.id_it, situacao );
		}

	}

	private void setParamInsertLog( PreparedStatement ps, Change value, Table table, SIT_LOG_TROCARP situacao, String mensagem ) throws SQLException {

		int param = 1;
		int id = gerarSeqId( table.table_name, false );
		ps.setInt( param++, id );
		ps.setInt( param++, value.getId_it() );
		ps.setString( param++, situacao.toString() );
		ps.setString( param++, table.getTable_name() );
		ps.setString( param++, mensagem );
	}

	private StringBuilder getSqlInsertLog() {

		StringBuilder result = new StringBuilder();
		result.append( "insert into eqittrocarplog " );
		result.append( "(id, id_it_troca, situacao, tabela, mensagem) " );
		result.append( "values (?, ?, ?, ?, ?) " );

		return result;
	}

	public ResultSet getResultSetRelatorio( Integer id ) throws SQLException {

		ResultSet result = null;
		StringBuilder sql = new StringBuilder();
		sql.append( "select trp.id, trp.motivo " );
		sql.append( "from eqtrocarefprod trp " );
		sql.append( "where trp.id=? " );
		PreparedStatement ps = getConn().prepareStatement( sql.toString() );
		result = ps.executeQuery();
		return result;
	}

	private StringBuilder getSqlTables( DATABASE db ) {

		StringBuilder result = new StringBuilder();
		if ( db == DATABASE.FireBird ) {
			result.append( "select rtrim(r1.rdb$relation_name) table_name" );
			result.append( ", rtrim(r1.rdb$field_name) field_name " );
			result.append( ", coalesce((select first 1 'S' from rdb$relation_fields r2 " );
			result.append( "where r2.rdb$relation_name=r1.rdb$relation_name " );
			result.append( "and r2.rdb$field_name='EMMANUT' ),'N') emmanut " );
			result.append( ", coalesce((select first 1 'S' from rdb$relation_fields r2 " );
			result.append( "where r2.rdb$relation_name=r1.rdb$relation_name " );
			result.append( "and r2.rdb$field_name='CODEMP' ),'N') hcodemp " );
			result.append( ", coalesce((select first 1 'S' from rdb$relation_fields r2 " );
			result.append( "where r2.rdb$relation_name=r1.rdb$relation_name " );
			result.append( "and r2.rdb$field_name='CODFILIAL' ),'N') hcodfilial " );
			result.append( "from rdb$relation_fields r1 " );
			result.append( "where r1.rdb$field_name like 'REFPROD%' " );
			result.append( "and r1.rdb$relation_name not in ('EQTROCAREFPROD','EQITTROCAREFPROD','EQITTROCARPLOG') " );
			result.append( "and r1.rdb$relation_name not like '%VW%' " );
			result.append( "order by r1.rdb$relation_name, r1.rdb$field_name" );
		}
		return result;
	}

	public class Change {

		Integer id_it;

		Integer codemppd;

		Integer codfilialpd;

		Integer codprod;

		String refprodold;

		String refprodnew;

		public Change( Integer id_it, Integer codemppd, Integer codfilialpd, Integer codprod, String refprodold, String refprodnew ) {

			setId_it( id_it );
			setCodemppd( codemppd );
			setCodfilialpd( codfilialpd );
			setCodprod( codprod );
			setRefprodold( refprodold );
			setRefprodnew( refprodnew );
		}

		public String getRefprodold() {

			return refprodold;
		}

		public String getRefprodnew() {

			return refprodnew;
		}

		public void setRefprodold( String refprodold ) {

			this.refprodold = refprodold;
		}

		public void setRefprodnew( String refprodnew ) {

			this.refprodnew = refprodnew;
		}

		public Integer getCodemppd() {

			return codemppd;
		}

		public Integer getCodfilialpd() {

			return codfilialpd;
		}

		public Integer getCodprod() {

			return codprod;
		}

		public void setCodemppd( Integer codemppd ) {

			this.codemppd = codemppd;
		}

		public void setCodfilialpd( Integer codfilialpd ) {

			this.codfilialpd = codfilialpd;
		}

		public void setCodprod( Integer codprod ) {

			this.codprod = codprod;
		}

		public Integer getId_it() {

			return id_it;
		}

		public void setId_it( Integer id_it ) {

			this.id_it = id_it;
		}
	}

	public class Table {

		String table_name = null;

		String field_name = null;

		Boolean emmanut = false;

		Boolean hcodemp = false;

		Boolean hcodfilial = false;

		Integer codfilial = null;

		public Table( String table_name, String field_name, Boolean emmanut, Boolean hcodemp, Boolean hcodfilial ) {

			setTable_name( table_name );
			setField_name( field_name );
			setEmmanut( emmanut );
			setHcodemp( hcodemp );
			setHcodfilial( hcodfilial );
		}

		public String getTable_name() {

			return table_name;
		}

		public String getField_name() {

			return field_name;
		}

		public void setTable_name( String table_name ) {

			this.table_name = table_name;
		}

		public void setField_name( String field_name ) {

			this.field_name = field_name;
		}

		public Boolean getEmmanut() {

			return emmanut;
		}

		public void setEmmanut( Boolean emmanut ) {

			this.emmanut = emmanut;
		}

		public Boolean getHcodemp() {

			return hcodemp;
		}

		public Boolean getHcodfilial() {

			return hcodfilial;
		}

		public void setHcodemp( Boolean hcodemp ) {

			this.hcodemp = hcodemp;
		}

		public void setHcodfilial( Boolean hcodfilial ) {

			this.hcodfilial = hcodfilial;
		}

		public Integer getCodfilial() {

			return codfilial;
		}

		public void setCodfilial( Integer codfilial ) {

			this.codfilial = codfilial;
		}
	}

}
