/**
 * @version 24/10/2013 <BR>
 * @author Setpoint Tecnologia em Informática Ltda./Robson Sanchez <BR>
 * 
 *         Projeto: Freedom <BR>
 * 
 *         Pacote: org.freedom.modulos.std.dao <BR>
 *         Classe: * @(#)DAOTrocaRefprod.java <BR>
 * 
 *                     Este arquivo é parte do sistema Freedom-ERP, o Freedom-ERP é um software livre; você pode redistribui-lo e/ou <BR>
 *                     modifica-lo dentro dos termos da Licença Pública Geral GNU como publicada pela Fundação do Software Livre (FSF); <BR>
 *                     na versão 2 da Licença, ou (na sua opnião) qualquer versão. <BR>
 *                     Este programa é distribuido na esperança que possa ser util, mas SEM NENHUMA GARANTIA; <BR>
 *                     sem uma garantia implicita de ADEQUAÇÂO a qualquer MERCADO ou APLICAÇÃO EM PARTICULAR. <BR>
 *                     Veja a Licença Pública Geral GNU para maiores detalhes. <BR>
 *                     Você deve ter recebido uma cópia da Licença Pública Geral GNU junto com este programa, se não, <BR>
 *                     de acordo com os termos da LPG-PC <BR>
 * <BR>
 * 
 *                     Classe responsável pela persistência dos dados, auxiliar a tela de troca de referência de produtos.
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

	public enum DATABASE {FireBird}
	
	public DAOTrocaRefprod( DbConnection connection, Integer codemp, Integer codfilial ) {
		super( connection, codemp, codfilial );
	}

	public StringBuffer seekRefprod(String refprod) throws Exception {
		StringBuffer found = new StringBuffer();
		StringBuilder sql = new StringBuilder();
		sql.append("select codprod, descprod, refprod from eqproduto where codemp=? and codfilial=? and refprod=?");
		try {
			PreparedStatement ps = getConn().prepareStatement( sql.toString() );
			int param = 1;
			ps.setInt( param++, getCodemp() );
			ps.setInt( param++, getCodfilial() );
			ps.setString( param++, refprod );
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				found.append("Referência está sendo utilizada em outro produto.\nCódigo: ");
				found.append( rs.getInt( "codprod" ) );
				found.append(" - ");
				found.append("Descrição: ");
				found.append(rs.getString( "descprod" ).trim());
			}
		} catch (SQLException e) {
			getConn().rollback();
			throw new Exception(e.getMessage());
		}
		return found;
	}
	
	public Integer gerarSeqId (String tabela) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null; 
		Integer id = 0;
		ps = getConn().prepareStatement( "select biseq from sgsequence_idsp(?)" );
		int param = 1;
		ps.setString( param, tabela.toLowerCase() );
		rs = ps.executeQuery();
		if (rs.next()) {
			id = rs.getInt( "biseq" ); 
		}
		rs.close();
		ps.close();
		getConn().commit();
		return id;
	}

	public Vector<Table> selectTableChange() throws Exception {
		Vector<Table> result = new Vector<Table>();
		StringBuilder sql = getSqlTables(DATABASE.FireBird);
		try {
			PreparedStatement ps = getConn().prepareStatement( sql.toString() );
			ResultSet rs = ps.executeQuery();
			while ( rs.next() ) {
				result.addElement( new Table(rs.getString("table_name"), rs.getString("field_name")) );
			}
			rs.close();
			ps.close();
			getConn().commit();
		} catch (SQLException e) {
			getConn().rollback();
			e.printStackTrace();
			throw new Exception("Erro carregando tabelas !\n"+e.getMessage());
		}
		return result;
	}
	
	private StringBuilder getSqlTables(DATABASE db) {
		StringBuilder result = new StringBuilder();
		if (db==DATABASE.FireBird) {
			result.append("select rdb$relation_name table_name, rdb$filed_name field_name ");
			result.append("from rdb$relation_fields ");
			result.append("where rdb$field_name like 'REFPROD%' ");
			result.append("and rdb$relation_name not in ('EQTROCAREFPROD','EQITTROCAREFPROD','EQITTROCARPLOG')");
			result.append("order by rdb$relation_name, rdb$field_name");
		}
		return result;
	}
	
	public class Table {

		String table_name = null;
		String field_name = null;
		public Table(String table_name, String field_name) {
			setTable_name( table_name );
			setField_name( field_name );
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
	}
}
