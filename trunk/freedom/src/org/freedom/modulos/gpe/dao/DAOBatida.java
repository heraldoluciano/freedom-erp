/**
 * @version 17/09/2011 <BR>
 * @author Setpoint Informática Ltda./Robson Sanchez <BR>
 * 
 *         Projeto: Freedom <BR>
 * 
 *         Pacote: org.freedom.modulos.gpe.dao <BR>
 *         Classe:
 * @(#)DAOBatida.java <BR>
 * 
 *                Este arquivo é parte do sistema Freedom-ERP, o Freedom-ERP é um software livre; você pode redistribui-lo e/ou <BR>
 *                modifica-lo dentro dos termos da Licença Pública Geral GNU como publicada pela Fundação do Software Livre (FSF); <BR>
 *                na versão 2 da Licença, ou (na sua opnião) qualquer versão. <BR>
 *                Este programa é distribuido na esperança que possa ser util, mas SEM NENHUMA GARANTIA; <BR>
 *                sem uma garantia implicita de ADEQUAÇÂO a qualquer MERCADO ou APLICAÇÃO EM PARTICULAR. <BR>
 *                Veja a Licença Pública Geral GNU para maiores detalhes. <BR>
 *                Você deve ter recebido uma cópia da Licença Pública Geral GNU junto com este programa, se não, <BR>
 *                de acordo com os termos da LPG-PC <BR>
 * <BR>
 * 
 *              Classe de acesso a dados para entidade PEBATIDA
 * 
 */
package org.freedom.modulos.gpe.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.freedom.infra.dao.AbstractDAO;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.functions.Funcoes;
import org.freedom.modulos.gpe.business.object.Batida;
import org.freedom.modulos.gpe.business.object.Batida.COL_PROC;
import org.freedom.modulos.gpe.business.object.Batida.PARAM_PROC_CARREGA;
import org.freedom.modulos.gpe.business.object.Batida.PARAM_PROC_INSERE;
import org.freedom.modulos.gpe.business.object.Batida.PREFS;


public class DAOBatida extends AbstractDAO {
	private Object prefs[] = null;
	
	public DAOBatida( DbConnection connection ) {

		super( connection );
		
	}

	public void setPrefs(Integer codemp, Integer codfilial) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuilder sql = null;
		
		prefs = new Object[ Batida.PREFS.values().length];
		
		try {
			sql = new StringBuilder("select lancapontoaf  " );
			sql.append( "from sgprefere3 p " );
			sql.append( "where  p.codemp=? and p.codfilial=?" );
			
			ps = getConn().prepareStatement( sql.toString() );
			ps.setInt( 1, codemp );
			ps.setInt( 2, codfilial );
			rs = ps.executeQuery();
			if ( rs.next() ) {
				prefs[ PREFS.LANCAPONTOAF.ordinal() ] = rs.getString( PREFS.LANCAPONTOAF.toString());
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
	
	public Batida carregaPonto(Integer codemp, Integer codfilial, String idusu, String aftela) throws SQLException {
		// aftela = tela de abertura (A) ou tela de fechamento (F)
		Batida result = new Batida();
		result.setCarregaponto( (String) prefs[ PREFS.LANCAPONTOAF.ordinal()] );
		if ( "S".equals( result.getCarregaponto() ) ) { // Verifica se o sistema está configurado para carregar tela de ponto
		   result.setCodempus( codemp );
		   result.setCodfiliaus( codfilial );
		   result.setIdusu( idusu );
		   result.setAftela( aftela );
		   result = executeProcCarregaPonto(result);
		} 
		return result;
	}
	
	private Batida executeProcCarregaPonto(Batida result) throws SQLException {
		StringBuilder sql = new StringBuilder();
		/*
		 * CARREGAPONTO, DATAPONTO, HORAPONTO, CODEMPAE, 
		CODFILIALAE, CODEMPEP, CODFILIALEP, MATEMPR
		 */
		sql.append("select carregaponto, dataponto, horaponto, codempae, ");
		sql.append("codfilialae, codempep, codatend, codfilialep, matempr ");
		sql.append("from crcarregapontosp(?, ?, ?, ?)");
		PreparedStatement ps = getConn().prepareStatement( sql.toString() );
		ps.setInt( PARAM_PROC_CARREGA.CODEMP.ordinal(), result.getCodempus() );
		ps.setInt( PARAM_PROC_CARREGA.CODFILIAL.ordinal(), result.getCodfilialus() );
		ps.setString( PARAM_PROC_CARREGA.IDUSU.ordinal(), result.getIdusu() );
		ps.setString( PARAM_PROC_CARREGA.AFTELA.ordinal(), result.getAftela() );
		ResultSet rs = ps.executeQuery();
		/*	public static enum COL_PROC {CARREGAPONTO, DATAPONTO, HORAPONTO, CODEMPAE, 
		CODFILIALAE, CODEMPEP, CODFILIALEP, MATEMPR };*/
		if (rs.next()) {
			result.setCarregaponto( rs.getString(COL_PROC.CARREGAPONTO.toString()) );
			result.setDataponto( Funcoes.sqlDateToDate( rs.getDate(COL_PROC.DATAPONTO.toString())) );
			result.setHoraponto( Funcoes.sqlTimeToStrTime( rs.getTime( COL_PROC.HORAPONTO.toString()) ) );
			result.setCodempae( rs.getInt(COL_PROC.CODEMPAE.toString()) );
			result.setCodfilialae( rs.getInt(COL_PROC.CODFILIALAE.toString()) );
			result.setCodatend( rs.getInt(COL_PROC.CODATEND.toString()) );
			result.setCodempep( rs.getInt(COL_PROC.CODEMPEP.toString()) );
			result.setCodfilialep( rs.getInt(COL_PROC.CODFILIALEP.toString()) );
			result.setMatempr( rs.getInt(COL_PROC.MATEMPR.toString()) );
		}
		rs.close();
		ps.close();
		getConn().commit();
		return result;
	}
	
	public boolean executeProcInsereBatida(Batida batida) throws SQLException {
		boolean result = true;
		StringBuilder sql = new StringBuilder();
//NONE, CODEMP, CODFILIAL, DTBAT, HBAT, CODEMPEP, CODFILIALEP, MATEMPR
		sql.append("execute procedure crinserebatidasp(?, ?, ?, ?, ?, ?, ?) ");
		PreparedStatement ps = getConn().prepareStatement( sql.toString() );
		ps.setInt( PARAM_PROC_INSERE.CODEMP.ordinal(), batida.getCodemp() );
		ps.setInt( PARAM_PROC_INSERE.CODFILIAL.ordinal(), batida.getCodfilial() );
		ps.setDate( PARAM_PROC_INSERE.DTBAT.ordinal(), Funcoes.dateToSQLDate( batida.getDataponto() ) );
		ps.setTime( PARAM_PROC_INSERE.HBAT.ordinal(), Funcoes.strTimeTosqlTime( batida.getHoraponto()) );
		ps.setInt( PARAM_PROC_INSERE.CODEMPEP.ordinal(), batida.getCodempep() );
		ps.setInt( PARAM_PROC_INSERE.CODFILIALEP.ordinal(), batida.getCodfilialep() );
		ps.setInt( PARAM_PROC_INSERE.MATEMPR.ordinal(), batida.getMatempr() );
		result = ps.execute();
		ps.close();
		getConn().commit();
		return result;
	}
	
	
}
