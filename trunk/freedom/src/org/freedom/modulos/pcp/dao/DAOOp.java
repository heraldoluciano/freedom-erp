/**
 * @version 14/10/2013 <BR>
 * @author Setpoint Tecnologia em Informática Ltda./<BR>
 * 
 *         Projeto: Freedom <BR>
 * 
 *         Pacote: org.freedom.modulos.pcp.dao <BR>
 *         Classe: @(#)DAOOp.java <BR>
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
 * 
 * 
 */

package org.freedom.modulos.pcp.dao;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;

import javax.swing.JOptionPane;

import org.freedom.infra.dao.AbstractDAO;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.functions.Funcoes;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.frame.Aplicativo;
import org.freedom.modulos.pcp.business.object.ModLote;

public class DAOOp extends AbstractDAO {

	public DAOOp( DbConnection cn ) throws Exception {

		super( cn );
		loadPrefere();
	}

	private HashMap<String, Object> prefere = null;

	private void loadPrefere() throws Exception {

		prefere = new HashMap<String, Object>();
		boolean[] bRetorno = new boolean[ 1 ];
		StringBuffer sql = new StringBuffer();
		PreparedStatement ps = null;
		ResultSet rs = null;

		sql.append( "SELECT P1.USAREFPROD, P5.RATAUTO, coalesce(prodetapas,'S') prodetapas " );
		sql.append( ", coalesce(P5.VALIDAQTDOP,'N') VALIDAQTDOP, coalesce(P5.VALIDAFASEOP,'N') VALIDAFASE" );
		sql.append( ", coalesce(P5.EDITQTDOP, 'S') EDITQTDOP, coalesce(P5.OPSEQ,'N') OPSEQ " );
		sql.append( "FROM SGPREFERE1 P1,SGPREFERE5 P5 " );
		sql.append( "WHERE P1.CODEMP=? AND P1.CODFILIAL=? " );
		sql.append( "AND P5.CODEMP=? AND P5.CODFILIAL=?" );

		bRetorno[ 0 ] = false;
		ps = getConn().prepareStatement( sql.toString() );
		ps.setInt( 1, Aplicativo.iCodEmp );
		ps.setInt( 2, ListaCampos.getMasterFilial( "SGPREFERE1" ) );
		ps.setInt( 3, Aplicativo.iCodEmp );
		ps.setInt( 4, ListaCampos.getMasterFilial( "SGPREFERE5" ) );

		rs = ps.executeQuery();

		if ( rs.next() ) {
			prefere.put( "USAREFPROD", new Boolean( rs.getString( "USAREFPROD" ).trim().equals( "S" ) ) );
			prefere.put( "RATAUTO", new Boolean( rs.getString( "RATAUTO" ).trim().equals( "S" ) ) );
			prefere.put( "PRODETAPAS", new Boolean( rs.getString( "prodetapas" ).trim().equals( "S" ) ) );
			prefere.put( "VALIDAQTDOP", new Boolean( rs.getString( "VALIDAQTDOP" ).trim().equals( "S" ) ) );
			prefere.put( "VALIDAFASE", new Boolean( rs.getString( "VALIDAFASE" ).trim().equals( "S" ) ) );
			prefere.put( "EDITQTDOP", new Boolean( rs.getString( "EDITQTDOP" ).trim().equals( "S" ) ) );
			prefere.put( "OPSEQ", new Boolean( rs.getString( "OPSEQ" ).trim().equals( "S" ) ) );
		}
		else {
			prefere.put( "USAREFPROD", new Boolean( false ) );
			prefere.put( "RATAUTO", new Boolean( false ) );
			prefere.put( "PRODETAPAS", new Boolean( true ) );
			prefere.put( "VALIDAQTDOP", new Boolean( false ) );
			prefere.put( "VALIDAFASE", new Boolean( false ) );
			prefere.put( "EDITQTDOP", new Boolean( true ) );
			prefere.put( "OPSEQ", new Boolean( false ) );
			throw new Exception( "Não foram encontradas preferências para o módulo PCP!" );
		}
		rs.close();
		ps.close();
		getConn().commit();

	}

	// Busca Numero de ops relacioadas
	public int getQtdOPS( Integer codemp, Integer codfilial, Integer codop, Integer seqop ) throws Exception {

		int ret = 0;
		StringBuffer sql = new StringBuffer();
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			sql.append( "select count(*) from ppop opr " );
			sql.append( "where opr.codemp=? and opr.codfilial=? and opr.codop=? " );
			sql.append( "and opr.seqop<>?" );

			ps = getConn().prepareStatement( sql.toString() );

			int param = 1;
			ps.setInt( param++, codemp );
			ps.setInt( param++, codfilial );
			ps.setInt( param++, codop );
			ps.setInt( param++, seqop );

			rs = ps.executeQuery();

			if ( rs.next() ) {
				ret = rs.getInt( 1 );
			}

			getConn().commit();

		} catch ( Exception e ) {
			getConn().rollback();
			throw new Exception( "Erro ao buscar O.P's. relacionadas!" );
		}
		return ret;
	}

	private HashMap<String, BigDecimal> getValoresFSC( Integer codprodest, String refprodest ) throws Exception {

		String result = null;
		PreparedStatement ps;
		ResultSet rs;

		StringBuilder sql = null;
		HashMap<String, BigDecimal> valores = new HashMap<String, BigDecimal>();

		try {

			sql = new StringBuilder( "" );
			sql.append( "select pd.NroPlanos , pd.QtdPorPlano, pd.fatorfsc from eqproduto pd " );
			sql.append( "where pd.codemp=? and pd.codfilial=? " );

			if ( codprodest > 0 ) {
				sql.append( "and pd.codprod=? " );
			}
			else {
				sql.append( "and pd.refprod=? " );
			}

			ps = getConn().prepareStatement( sql.toString() );
			int param = 1;
			ps.setInt( param++, Aplicativo.iCodEmp );
			ps.setInt( param++, ListaCampos.getMasterFilial( "EQPRODUTO" ) );
			if ( codprodest > 0 ) {
				ps.setInt( param++, codprodest );
			}
			else {
				ps.setString( param++, refprodest );
			}
			rs = ps.executeQuery();

			if ( rs.next() ) {
				valores.put( "NROPLANOS", rs.getBigDecimal( "NROPLANOS" ) );
				valores.put( "QTDPORPLANO", rs.getBigDecimal( "QTDPORPLANO" ) );
				valores.put( "FATORFSC", rs.getBigDecimal( "FATORFSC" ) );
			}

			rs.close();
			ps.close();
			getConn().commit();
		} catch ( SQLException e ) {
			e.printStackTrace();
			getConn().rollback();
			throw new Exception( "Não foi possível carregar valores para conversão FSC !\n" + e.getMessage() );
		}
		return valores;
	}

	public String validaQuantidade( Integer codprodest, String refprodest, BigDecimal qtdsugprodop ) throws Exception {

		String result = "";
		HashMap<String, BigDecimal> valores = getValoresFSC( codprodest, refprodest );
		BigDecimal nroPlanos = valores.get( "NROPLANOS" );
		BigDecimal qtdPlanos = valores.get( "QTDPORPLANO" );
		BigDecimal fatorfsc = valores.get( "FATORFSC" );
		BigDecimal qtdMinimaEtiquetas = qtdPlanos.divide( nroPlanos );
		BigDecimal quantidadeOP = qtdsugprodop.multiply( fatorfsc );
		if ( quantidadeOP.remainder( qtdMinimaEtiquetas ).compareTo( new BigDecimal( 0 ) ) != 0 ) {
			BigDecimal valor = quantidadeOP.divide( qtdMinimaEtiquetas );
			BigDecimal qtdMinimaOP = new BigDecimal( valor.intValue() ).multiply( qtdMinimaEtiquetas );
			BigDecimal qtdASeguirOP = qtdMinimaOP.add( qtdMinimaEtiquetas );
			// .multiply( new BigDecimal(fatorfsc.intValue())
			result = "Quantidade inválida!!!\nQuantidade Sugerida:\nMenor: " + qtdMinimaOP + "\nMaior: " + qtdASeguirOP;
		}
		return result;
	}

	public String getExpedirRMA( Integer codemp, Integer codfilial, Integer codprodest, Integer seqest ) throws Exception {

		StringBuilder sql = new StringBuilder();
		PreparedStatement ps = null;
		ResultSet rs = null;
		String expedirrma = null;
		try {
			sql.append( "select e.expedirrma from ppestrutura e where e.codemp=? and e.codfilial=? and e.codprod=? and e.seqest=? " );
			ps = getConn().prepareStatement( sql.toString() );
			int param = 1;
			ps.setInt( param++, codemp );
			ps.setInt( param++, codfilial );
			ps.setInt( param++, codprodest );
			ps.setInt( param++, seqest );
			rs = ps.executeQuery();
			if ( rs.next() ) {
				expedirrma = rs.getString( "expedirrma" );
			}
		} catch ( Exception e ) {
			getConn().rollback();
			e.printStackTrace();
		}
		return expedirrma;
	}

	public Integer testaCodPK() throws Exception {

		PreparedStatement ps = null;
		ResultSet rs = null;
		Integer retorno = new Integer( 0 );

		try {
			ps = getConn().prepareStatement( "SELECT ISEQ FROM SPGERANUM(?,?,?)" );
			ps.setInt( 1, Aplicativo.iCodEmp );

			ps.setInt( 2, ListaCampos.getMasterFilial( "PPOP" ) );
			ps.setString( 3, "OP" );

			rs = ps.executeQuery();
			rs.next();

			retorno = new Integer( rs.getString( "ISEQ" ) );

			rs.close();
			ps.close();

			getConn().commit();

		} catch ( SQLException err ) {
			err.printStackTrace();
			getConn().rollback();
			throw new Exception( "Erro ao confirmar número da OP!\n" + err.getMessage() );
		} finally {
			ps = null;
			rs = null;
		}

		return retorno;

	}

	public void insereItOpDinamica( Integer codemp, Integer codfilial, Integer codop, Integer seqop, Integer codfase, Integer codprod, String refprod, BigDecimal qtditop, String gerarma ) throws Exception {

		StringBuilder sql = new StringBuilder();
		PreparedStatement ps = null;

		try {
			sql.append( "insert into ppitop (" );
			sql.append( "codemp, codfilial, codop, seqop, seqitop, " );
			sql.append( "codempfs, codfilialfs, codfase, " );
			sql.append( "codemppd, codfilialpd, codprod, refprod, " );
			sql.append( "qtditop, gerarma" );
			sql.append( ") values ( " );
			sql.append( "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )" );

			ps = getConn().prepareStatement( sql.toString() );

			Integer iparam = 1;

			ps.setInt( iparam++, codemp );
			ps.setInt( iparam++, codfilial );
			ps.setInt( iparam++, codop );
			ps.setInt( iparam++, seqop );
			ps.setInt( iparam++, getSeqItOP( codemp, codfilial, codop, seqop ) );
			ps.setInt( iparam++, codemp );
			ps.setInt( iparam++, codfilial );
			ps.setInt( iparam++, codfase );
			ps.setInt( iparam++, codemp );
			ps.setInt( iparam++, codfilial );
			ps.setInt( iparam++, codprod );
			ps.setString( iparam++, refprod );
			ps.setBigDecimal( iparam++, qtditop );
			ps.setString( iparam++, gerarma );
			ps.execute();

			getConn().commit();

		} catch ( Exception e ) {
			getConn().rollback();
			e.printStackTrace();
			throw e;
		}
	}

	public Integer getSeqItOP( Integer codemp, Integer codfilial, Integer codop, Integer seqop ) {

		Integer ret = 1;
		StringBuilder sql = new StringBuilder();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			sql.append( "select coalesce(max(io.seqitop),0) + 1 from ppitop io " );
			sql.append( "where io.codemp=? and io.codfilial=? and io.codop=? and io.seqop=? " );
			ps = getConn().prepareStatement( sql.toString() );
			int param = 1;
			ps.setInt( param++, codemp );
			ps.setInt( param++, codfilial );
			ps.setInt( param++, codop );
			ps.setInt( param++, seqop );
			rs = ps.executeQuery();
			if ( rs.next() ) {
				ret = rs.getInt( 1 );
			}
			getConn().commit();
		} catch ( Exception e ) {
			e.printStackTrace();
		}
		return ret;
	}

	public BigDecimal getQtdSubProd( Integer codop, Integer seqop ) throws Exception {

		BigDecimal result = BigDecimal.ZERO;
		StringBuilder sql = new StringBuilder( "select sum(sp.qtditsp) qtditsp from ppopsubprod sp " );
		sql.append( "where sp.codemp=? and sp.codfilial=? and sp.codop=? and sp.seqop=?" );
		PreparedStatement ps;
		ResultSet rs;
		try {
			ps = getConn().prepareStatement( sql.toString() );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "PPOPSUBPROD" ) );
			ps.setInt( 3, codop );
			ps.setInt( 4, seqop );
			rs = ps.executeQuery();
			if ( rs.next() ) {
				result = getBigDecimal( rs.getBigDecimal( "qtditsp" ) );
			}
			rs.close();
			ps.close();
			getConn().commit();
		} catch ( SQLException e ) {
			e.printStackTrace();
			getConn().rollback();
			throw new Exception( "Não foi possível carregar qtd. de sub-produtos !\n" + e.getMessage() );
		}
		return result;
	}

	public String getCodUnid( Integer codemp, Integer codfilial, Integer codprodest, String refprodest ) throws Exception {

		String result = null;
		StringBuilder sql = new StringBuilder( "select pd.codunid from eqproduto pd " );
		sql.append( "where pd.codemp=? and pd.codfilial=? " );
		if ( codprodest > 0 ) {
			sql.append( "and pd.codprod=? " );
		}
		else {
			sql.append( "and pd.refprod=? " );
		}
		PreparedStatement ps;
		ResultSet rs;
		try {
			ps = getConn().prepareStatement( sql.toString() );
			int param = 1;

			ps.setInt( param++, codemp );
			ps.setInt( param++, codfilial );
			if ( codprodest > 0 ) {
				ps.setInt( param++, codprodest );
			}
			else {
				ps.setString( param++, refprodest );
			}
			rs = ps.executeQuery();
			if ( rs.next() ) {
				result = rs.getString( "codunid" );
			}
			rs.close();
			ps.close();
			getConn().commit();
		} catch ( SQLException e ) {
			getConn().rollback();
			e.printStackTrace();
			throw new Exception( "Não foi possível carregar unidade !\n" + e.getMessage() );
		}
		return result;
	}

	public Object[] gravaLote( boolean bInsere, String sCodModLote, String sUsaLoteEst, String sModLote, int iCodProd, Date dtFabProd, int iNroDiasValid, String sCodLote ) throws Exception {

		Object[] retorno = null;
		ModLote objMl = null;
		try {
			if ( ! ( sCodModLote.equals( "" ) ) ) {
				if ( sCodLote == null ) {
					objMl = new ModLote();
					objMl.setTexto( sModLote );
					sCodLote = objMl.getLote( new Integer( iCodProd ), dtFabProd, getConn() );
				}
				GregorianCalendar cal = new GregorianCalendar();
				cal.setTime( dtFabProd );
				cal.add( GregorianCalendar.DAY_OF_YEAR, iNroDiasValid );
				Date dtVenctoLote = cal.getTime();
				retorno = new Object[ 3 ];
				retorno[ 0 ] = sCodLote;
				retorno[ 1 ] = dtVenctoLote;
				retorno[ 2 ] = new Boolean( false );
				try {
					if ( ( !existeLote( iCodProd, sCodLote ) ) && ( bInsere ) ) {
						// Futuramente a mensagem deverá ser removida 
						if ( Funcoes.mensagemConfirma( null, "Deseja criar o lote " + sCodLote.trim() + " ?" ) == JOptionPane.YES_OPTION ) {
							PreparedStatement ps = null;
							String sSql = "INSERT INTO EQLOTE (CODEMP,CODFILIAL,CODPROD,CODLOTE,DINILOTE,VENCTOLOTE) VALUES(?,?,?,?,?,?)";
							try {
								ps = getConn().prepareStatement( sSql );
								ps.setInt( 1, Aplicativo.iCodEmp );
								ps.setInt( 2, ListaCampos.getMasterFilial( "EQLOTE" ) );
								ps.setInt( 3, iCodProd );
								ps.setString( 4, sCodLote );
								ps.setDate( 5, Funcoes.dateToSQLDate( dtFabProd ) );
								ps.setDate( 6, Funcoes.dateToSQLDate( dtVenctoLote ) );
								if ( ps.executeUpdate() == 0 )
									throw new Exception( "Não foi possível inserir registro na tabela de Lotes!" );
								getConn().commit();
								retorno[ 2 ] = new Boolean( true );
							} catch ( SQLException err ) {
								getConn().rollback();
								throw new Exception( "Erro ao inserir registro na tabela de Lotes!\n" + err.getMessage() );
							} finally {
								ps = null;
								sSql = null;
							}
						}
					}
					else if ( bInsere )
						throw new Exception( "Lote já cadastrado para o produto!" );
				} catch ( Exception err ) {
					throw err;
				}
			}
		} finally {
			sCodLote = null;
			objMl = null;
		}
		return retorno;
	}

	public boolean existeLote( int iCodProd, String sCodLote ) throws Exception {

		boolean bRet = false;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sSQL = "SELECT CODLOTE FROM EQLOTE WHERE CODEMP=? AND CODFILIAL=? AND CODPROD=? AND CODLOTE=?";
		try {
			ps = getConn().prepareStatement( sSQL );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "EQLOTE" ) );
			ps.setInt( 3, iCodProd );
			ps.setString( 4, sCodLote );
			rs = ps.executeQuery();
			if ( rs.next() )
				bRet = true;
			rs.close();
			ps.close();
			getConn().commit();
		} catch ( SQLException err ) {
			getConn().rollback();
			err.printStackTrace();
			throw new Exception( "Erro ao buscar existencia do lote!\n" + err.getMessage() );
		} finally {
			ps = null;
			rs = null;
			sSQL = null;
		}
		return bRet;
	}

	public HashMap<String, Object> getPrefere() {

		return prefere;
	}

	public void setPrefere( HashMap<String, Object> prefere ) {

		this.prefere = prefere;
	}

}
