/**
 * @author Setpoint Tecnologia em Informática Ltda./Robson Sachez <BR>
 * @versao 25/02/2014
 *  
 *         Projeto: Freedom <BR>
 * 
 *         Pacote: org.freedom.modulos.crm.dao <BR>
 *         Classe: @(#)DAOConsultaCli.java <BR>
 *         
 * 
 *                    Este arquivo é parte do sistema Freedom-ERP, o Freedom-ERP é um software livre; você pode redistribui-lo e/ou <BR>
 *                    modifica-lo dentro dos termos da Licença Pública Geral GNU como publicada pela Fundação do Software Livre (FSF); <BR>
 *                    na versão 2 da Licença, ou (na sua opnião) qualquer versão. <BR>
 *                    Este programa é distribuido na esperança que possa ser util, mas SEM NENHUMA GARANTIA; <BR>
 *                    sem uma garantia implicita de ADEQUAÇÂO a qualquer MERCADO ou APLICAÇÃO EM PARTICULAR. <BR>
 *                    Veja a Licença Pública Geral GNU para maiores detalhes. <BR>
 *                    Você deve ter recebido uma cópia da Licença Pública Geral GNU junto com este programa, se não, <BR>
 *                    de acordo com os termos da LPG-PC <BR>
 * <BR>
 * 
 *                    Classe com o objetivo de persistir os dados da class FConsultaCli
 * 
 */
package org.freedom.modulos.crm.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Vector;

import javax.swing.ImageIcon;

import org.freedom.bmps.Icone;
import org.freedom.infra.dao.AbstractDAO;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.functions.Funcoes;
import org.freedom.library.type.StringDireita;

public class DAOConsultaCli extends AbstractDAO {
	private ImageIcon imgVencida = Icone.novo( "clVencido.gif" );

	private ImageIcon imgAVencer = Icone.novo( "clNaoVencido.gif" );

	private ImageIcon imgPgEmAtraso = Icone.novo( "clPagoParcial.gif" );

	private ImageIcon imgPgEmDia = Icone.novo( "clPago.gif" );
	
	private ImageIcon imgCancelado = Icone.novo( "btRecusado.gif" );

	private ImageIcon imgPedido = Icone.novo( "clPriorBaixa.gif" );

	private ImageIcon imgFaturado = Icone.novo( "clPriorAlta.png" );
	
	private ImageIcon imgColuna = null;
	
	private ImageIcon imgVencimento = null;

	public static enum VENDAS {
		STATUSVENDA, STATUSPGTO, ATRASO, CODVENDA, NOTA, DATA, PAGAMENTO, VENDEDOR, VALOR_PRODUTOS, VALOR_DESCONTO, VALOR_ADICIONAL, FRETE, VALOR_LIQUIDO, TIPOVENDA;
	}
	public static enum CESTAS {
		SELECAO, CODCLI, RAZCLI, DATACESTA, QTDCESTA, VLRDESCCESTA, VLRLIQCESTA;
	}
	public static enum PRODVENDAS {
		STATUSVENDA, STATUSPGTO, CODVENDA, DOCVENDA, DTEMITVENDA, CODPROD, DESCPROD, QTDITVENDA, PRECOITVENDA, PERCDESCITVENDA
		, VLRDESCITVENDA, VLRLIQITVENDA, TIPOVENDA;
	}
	public static enum ITENSVENDA {
		CODITVENDA, CODPROD, DESCPROD, CODLOTE, QTDITVENDA, PRECOITVENDA, VLRDESCITVENDA, VLRFRETEITVENDA, VLRLIQITVENDA, TIPOVENDA;
	}
	public static enum ITENSCESTA {
		SELECAO, CODPROD, DESCPROD, QTDITCESTA, PRECOITCESTA, VLRDESCITCESTA, VLRLIQITCESTA;
	}
	public static enum RESULT_ULTVENDA{ 
		DTEMITVENDA, VLRLIQVENDA
	}
	public static enum RESULT_RECEBER{ 
		TOTAL_ABERTO, TOTAL_ATRASO, ATRASO_MEDIO
	}
	public static enum RESULT_RECEBER2{
		EMABERTO, PAGO, ATRASO
	}
	public DAOConsultaCli( DbConnection connection ) {
		super( connection );
	}
	public DAOConsultaCli( DbConnection connection, ImageIcon imgVencida, ImageIcon imgAVencer
			, ImageIcon imgPgEmAtraso, ImageIcon imgPgEmDia, ImageIcon imgCancelado
			, ImageIcon imgPedido, ImageIcon imgFaturado  ) {
		this( connection );
		setImgVencida( imgVencida );
		setImgAVencer( imgAVencer );
		setImgPgEmAtraso( imgPgEmAtraso );
		setImgPgEmDia( imgPgEmDia );
		setImgCancelado( imgCancelado );
		setImgPedido( imgPedido );
		setImgFaturado( imgFaturado );
	}
	public void commit() throws SQLException {
		getConn().commit();
	}

	public Object[] loadUltimaVenda(Integer codempvd, Integer codfilialvd
			, Integer codempcl, Integer codfilialcl, Integer codcli 
			, Date dtini, Date dtfim) throws Exception {
		Object[] result = new Object[RESULT_ULTVENDA.values().length];
		try {
			StringBuilder sql = new StringBuilder();
			sql.append( "select first 1 v.dtemitvenda, v.vlrliqvenda, v.codvenda " );
			sql.append( "from vdvenda v, vdcliente c " );
			sql.append( "where v.codemp=? and v.codfilial=? and v.tipovenda='V' and v.dtemitvenda between ? and ? " );
			sql.append( "and c.codemp=v.codempcl and c.codfilial=v.codfilialcl and c.codcli=v.codcli " );
			sql.append( "and c.codemp=? and c.codfilial=? and c.codcli=? " );
			sql.append( "order by v.dtemitvenda desc, v.docvenda desc" );
			PreparedStatement ps = getConn().prepareStatement( sql.toString() );
			int param = 1;
			ps.setInt( param++, codempvd );
			ps.setInt( param++, codfilialvd );
			ps.setDate( param++, Funcoes.dateToSQLDate( dtini ) );
			ps.setDate( param++, Funcoes.dateToSQLDate( dtfim ) );
			ps.setInt( param++, codempcl );
			ps.setInt( param++, codfilialcl );
			ps.setInt( param++, codcli );
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				result[RESULT_ULTVENDA.DTEMITVENDA.ordinal()] = rs.getDate( RESULT_ULTVENDA.DTEMITVENDA.name() );
				result[RESULT_ULTVENDA.VLRLIQVENDA.ordinal()] = rs.getBigDecimal( RESULT_ULTVENDA.VLRLIQVENDA.name() );
			}
		} catch (SQLException e) {
			getConn().rollback();
			throw new Exception(e.getMessage());
		}
		return result;
	}

	public Object[] loadReceber( Integer codemprc, Integer codfilialrc
			, Integer codempcl, Integer codfilialcl, Integer codcli 
			, Integer codemppd, Integer codfilialpd, Integer codprod
			, Date dtini, Date dtfim) throws Exception {

		Object[] result = new Object[RESULT_RECEBER.values().length];
		try {
			StringBuilder sql = new StringBuilder();
			sql.append( "select sum(ir.vlrapagitrec) total_aberto ");
			sql.append( ", sum(case when ir.dtvencitrec<cast('now' as date) then ir.vlrapagitrec else 0 end) total_atraso ");
			sql.append( ", avg(case when ir.dtvencitrec<coalesce(ir.dtliqitrec,cast('now' as date)) ");
			sql.append( "then coalesce(ir.dtliqitrec,cast('now' as date)) - ir.dtvencitrec else 0 end) atraso_medio ");
			//sql.append( ", min(datarec), max(datarec) " );
			sql.append( "from fnreceber rc, fnitreceber ir " );
			sql.append( "where rc.codemp=ir.codemp and rc.codfilial=ir.codfilial and rc.codrec=ir.codrec and " );
			sql.append( "ir.codemp=? and ir.codfilial=? and rc.codempcl=? and rc.codfilialcl=? and codcli=? " );
			sql.append( "and ir.dtvencitrec between ? and ? ");		
			PreparedStatement ps = getConn().prepareStatement( sql.toString() );
			int param = 1;
			ps.setInt( param++, codemprc );
			ps.setInt( param++, codfilialrc );
			ps.setInt( param++, codempcl );
			ps.setInt( param++, codfilialcl );
			ps.setInt( param++, codcli );
			ps.setDate( param++, Funcoes.dateToSQLDate( dtini) );
			ps.setDate( param++, Funcoes.dateToSQLDate( dtfim ) );
			ResultSet rs = ps.executeQuery();
			if ( rs.next() ) {
				//result[RESULT_RECEBER.TOTAL_VENDAS.ordinal()] = rs.getBigDecimal( RESULT_RECEBER.TOTAL_VENDAS.name() ); 
				result[RESULT_RECEBER.TOTAL_ABERTO.ordinal()] = rs.getBigDecimal( RESULT_RECEBER.TOTAL_ABERTO.name() ); 
				result[RESULT_RECEBER.TOTAL_ATRASO.ordinal()] = rs.getBigDecimal( RESULT_RECEBER.TOTAL_ATRASO.name() );
				result[RESULT_RECEBER.ATRASO_MEDIO.ordinal()] = rs.getBigDecimal( RESULT_RECEBER.ATRASO_MEDIO.name() );
			
			}
			rs.close();
			ps.close();
			getConn().commit();
		} catch (SQLException e) {
			getConn().rollback();
			throw new Exception(e.getMessage());
		}
		return result;
	}

	public Vector<Vector<Object>> loadItensVenda(Integer codempvd, Integer codfilialvd, String tipovenda, Integer codvenda
			, Integer codemppd, Integer codfilialpd, Integer codprod) throws Exception {
		Vector<Vector<Object>> result = new Vector<Vector<Object>>();
		try {
			StringBuilder sql = new StringBuilder();
			sql.append( "select i.coditvenda, i.codprod, coalesce(obsitvenda, p.descprod) descprod, coalesce(i.codlote,'') codlote " );
			sql.append( ", coalesce(i.qtditvenda,0) qtditvenda, coalesce(i.precoitvenda,0) precoitvenda ");
			sql.append( ", coalesce(i.vlrdescitvenda,0) vlrdescitvenda, coalesce(i.vlrfreteitvenda,0) vlrfreteitvenda ");
			sql.append( ", coalesce(i.vlrliqitvenda,0) vlrliqitvenda " );
			sql.append( "from vditvenda i, eqproduto p " );
			sql.append( "where i.codemp=? and i.codfilial=? and i.codvenda=? and i.tipovenda=? and " );
			sql.append( "p.codemp=i.codemppd and p.codfilial=i.codfilialpd and p.codprod=i.codprod " );
			if ( codprod.intValue() > 0 ) {
				sql.append( " and p.codprod=? " );
			}
			sql.append( "order by i.coditvenda" );
			PreparedStatement ps = getConn().prepareStatement( sql.toString() );
			int iparam = 1;
			ps.setInt( iparam++, codempvd );
			ps.setInt( iparam++, codfilialvd );
			ps.setInt( iparam++, codvenda );
			ps.setString( iparam++, tipovenda );
			if ( codprod.intValue() > 0 ) {
				ps.setInt( iparam++, codprod );
			}
			ResultSet rs = ps.executeQuery();
			while ( rs.next() ) {
				Vector<Object> row = new Vector<Object>();
				row.addElement( rs.getInt( "CODITVENDA" ) );
				row.addElement( rs.getInt( "CODPROD" ) );
				row.addElement( rs.getString( "DESCPROD" ) );
				row.addElement( rs.getString( "CODLOTE" ) );
				row.addElement( Funcoes.bdToStr( rs.getBigDecimal( "QTDITVENDA" ) ) );
				row.addElement( Funcoes.bdToStr( rs.getBigDecimal( "PRECOITVENDA" ) ) );
				row.addElement( Funcoes.bdToStr( rs.getBigDecimal( "VLRDESCITVENDA" ) ) );
				row.addElement( Funcoes.bdToStr( rs.getBigDecimal( "VLRFRETEITVENDA" ) ) );
				row.addElement( Funcoes.bdToStr( rs.getBigDecimal( "VLRLIQITVENDA" ) ) );
				row.addElement( tipovenda );
				result.addElement( row );
			}
			rs.close();
			ps.close();
			getConn().commit();
		} catch ( SQLException e ) {
			getConn().rollback();
			throw new Exception(e.getMessage());
		}
		return result;
	}
	
	public Vector<Vector<Object>> loadVendas( Integer codempvd, Integer codfilialvd
			, Integer codempcl, Integer codfilialcl, Integer codcli 
			, Integer codemppd, Integer codfilialpd, Integer codprod
			, Date dtini, Date dtfim) throws Exception {
		Vector<Vector<Object>> result = new Vector<Vector<Object>>();
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("select v.codvenda, v.docvenda, v.dtemitvenda, v.statusvenda, v.codplanopag" );
			sql.append(", p.descplanopag, v.codvend, vd.nomevend, coalesce(v.vlrprodvenda,0) vlrprodvenda, coalesce(v.vlrdescvenda,0) vlrdescvenda" );
			sql.append(", coalesce(v.vlradicvenda,0) vlradicvenda , coalesce(v.vlrfretevenda,0) vlrfretevenda, coalesce(v.vlrliqvenda,0) vlrliqvenda, v.tipovenda" );
			sql.append(" , coalesce((select vf.tipofretevd from vdfretevd vf where vf.codemp = v.codemp and vf.codfilial = v.codfilial and vf.tipovenda = v.tipovenda and vf.codvenda = v.codvenda), 'N')  as tipofrete ");
			sql.append( "from vdvenda v, fnplanopag p, vdvendedor vd " );
			sql.append( "where v.codemp=? and v.codfilial=? and v.tipovenda='V' and v.dtemitvenda between ? and ?" );
			//sql.append( "and substring( v.statusvenda from 1 for 1) not in ('D','C') ");
			sql.append( "and p.codemp=v.codemppg and p.codfilial=v.codfilialpg and p.codplanopag=v.codplanopag " );
			sql.append( "and vd.codemp=v.codempvd and vd.codfilial=v.codfilialvd and vd.codvend=v.codvend" );
			if ( codcli.intValue() > 0 ) {
				sql.append( " and v.codempcl=? and v.codfilialcl=? AND codcli=? " );
			}
			if ( codprod > 0 ) {
				sql.append( " and exists (" );
				sql.append( " select codvenda from vditvenda iv where " );
				sql.append( " iv.codemp=v.codemp and iv.codfilial=v.codfilial and iv.codvenda=v.codvenda and iv.tipovenda=v.tipovenda " );
				sql.append( " and iv.codemppd=? and iv.codfilialpd=? and iv.codprod=? ) " );
			}
			sql.append( "order by v.dtemitvenda desc, v.docvenda desc" );
			PreparedStatement ps = getConn().prepareStatement( sql.toString() );
			int iparam = 1;
			ps.setInt( iparam++, codempvd );
			ps.setInt( iparam++, codfilialvd );
			ps.setDate( iparam++, Funcoes.dateToSQLDate( dtini ) );
			ps.setDate( iparam++, Funcoes.dateToSQLDate( dtfim ) );
			if ( codcli.intValue() > 0 ) {
				ps.setInt( iparam++, codempcl );
				ps.setInt( iparam++, codfilialcl );
				ps.setInt( iparam++, codcli );
			}
			if ( codprod.intValue() > 0 ) {
				ps.setInt( iparam++, codemppd );
				ps.setInt( iparam++, codfilialpd );
				ps.setInt( iparam++, codprod );
			}
			ResultSet rs = ps.executeQuery();
			
			while ( rs.next() ) {
				Vector<Object> row = new Vector<Object>();
				String tipovenda = rs.getString( "TIPOVENDA" );
				Integer codvenda = rs.getInt( "CODVENDA" );
				String statusvenda = rs.getString( "STATUSVENDA" );
				java.sql.Date emaberto = null;
				Integer pago = null;
				Integer atraso = null;
				StringDireita stratraso = new StringDireita("");
				Object[] result_receber2 = loadReceber2( codempvd, codfilialvd, tipovenda, codvenda );
				if (result_receber2!=null) {
					pago = (Integer) result_receber2[RESULT_RECEBER2.PAGO.ordinal()];
					emaberto = (java.sql.Date) result_receber2[RESULT_RECEBER2.EMABERTO.ordinal()];
					atraso = (Integer) result_receber2[RESULT_RECEBER2.ATRASO.ordinal()];
					if ( (atraso!=null) && ( atraso.intValue()!=0) ) {
						stratraso = new StringDireita(atraso.toString());
					}
				}
				imgColuna = getImgStatus(statusvenda);
				imgVencimento = getImgVencimento(statusvenda, emaberto, pago, atraso);
				row.addElement( imgColuna);
				row.addElement( imgVencimento );
				row.addElement( stratraso );
				row.addElement( codvenda );
				row.addElement( rs.getString( "DOCVENDA" ) );
				row.addElement( Funcoes.dateToStrDate( rs.getDate( "DTEMITVENDA" ) ) );
				row.addElement( rs.getString( "DESCPLANOPAG" ) );
				row.addElement( rs.getString( "NOMEVEND" ) );
				row.addElement( Funcoes.bdToStr( rs.getBigDecimal( "VLRPRODVENDA" ) ) );
				row.addElement( Funcoes.bdToStr( rs.getBigDecimal( "VLRDESCVENDA" ) ) );
				row.addElement( Funcoes.bdToStr( rs.getBigDecimal( "VLRADICVENDA" ) ) );
				row.addElement( getTipoFrete(rs.getString( "TIPOFRETE" )) );
				row.addElement( Funcoes.bdToStr( rs.getBigDecimal( "VLRLIQVENDA" ) ) );
				row.addElement( tipovenda );
				result.addElement( row );
			}
			rs.close();
			ps.close();
			getConn().commit();
		} catch (SQLException e) {
			getConn().rollback();
			throw new Exception(e.getMessage());
		}
		return result;
	}
	
	private Object[] loadReceber2( Integer codempvd, Integer codfilialvd, String tipovenda, Integer codvenda) throws SQLException {
		Object[] result = null;
		StringBuilder sql = new StringBuilder();
		sql.append("select codemp, codfilial, codrec");
		sql.append(", (select first 1 ir.dtvencitrec ");
		sql.append(" from fnitreceber ir ");
		sql.append(" where ir.codemp=rc.codemp and ir.codfilial=rc.codfilial and ir.codrec=rc.codrec and ir.statusitrec = 'R1' ");
		sql.append(" order by ir.dtvencitrec) ");
		sql.append(" emaberto ");
		sql.append(", (select first 1 ir.dtvencitrec - ir.dtliqitrec ");
		sql.append(" from fnitreceber ir ");
		sql.append(" where ir.codemp=rc.codemp and ir.codfilial=rc.codfilial and ir.codrec=rc.codrec and ir.statusitrec = 'RP' ");
		sql.append(" order by ir.dtvencitrec) ");
		sql.append(" pago ");
		sql.append(", (select max( case when ir.dtvencitrec<coalesce(ir.dtliqitrec, cast('now' as date)) ");
		sql.append(" then coalesce(ir.dtliqitrec, cast('now' as date)) - ir.dtvencitrec else 0 end ) ");
		sql.append(" from fnitreceber ir ");
		sql.append(" where ir.codemp=rc.codemp and ir.codfilial=rc.codfilial and ir.codrec=rc.codrec ");
		sql.append(" ) ");
		sql.append(" atraso ");
		sql.append("from fnreceber rc ");
		sql.append("where rc.codempva=? and rc.codfilialva=? and rc.tipovenda=? and rc.codvenda=?");
		PreparedStatement ps = getConn().prepareStatement( sql.toString() );
		int param = 1;
		ps.setInt( param++, codempvd );
		ps.setInt( param++, codfilialvd );
		ps.setString( param++, tipovenda );
		ps.setInt( param++, codvenda );
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			result = new Object[RESULT_RECEBER2.values().length];
			result[RESULT_RECEBER2.EMABERTO.ordinal()] = rs.getDate( RESULT_RECEBER2.EMABERTO.name() );
			result[RESULT_RECEBER2.PAGO.ordinal()] = new Integer(rs.getInt( RESULT_RECEBER2.PAGO.name() ));
			result[RESULT_RECEBER2.ATRASO.ordinal()] = new Integer(rs.getInt( RESULT_RECEBER2.ATRASO.name() ));
			
		}
		return result;
	}

	public Vector<Object> loadProdItemVenda(Integer codempvd, Integer codfilialvd, Date dtemitvenda
			, Integer codempcl, Integer codfilialcl, Integer codcli 
			, Integer codemppd, Integer codfilialpd, Integer codprod) throws Exception {
		Vector<Object> row = new Vector<Object>();
		try {
			StringBuilder sql = new StringBuilder();
			/*
					STATUSVENDA, STATUSPGTO, CODVENDA, DOCVENDA, DTEMITVENDA, CODPROD, DESCPROD, QTDITVENDA, PRECOITVENDA, PERCDESCITVENDA
		, VLRDESCITVENDA, VLRLIQITVENDA, TIPOVENDA;*/
			sql.append("select first 1 vd.statusvenda, vd.codvenda, vd.docvenda, vd.dtemitvenda");
			sql.append(", pd.codprod, pd.descprod, iv.qtditvenda, iv.precoitvenda, iv.percdescitvenda ");
			sql.append(", iv.vlrdescitvenda, iv.vlrliqitvenda, vd.tipovenda ");
			sql.append("from vdvenda vd ");
			sql.append("inner join vditvenda iv ");
			sql.append("on iv.codemp=vd.codemp and iv.codfilial=vd.codfilial and iv.tipovenda=vd.tipovenda and iv.codvenda=vd.codvenda ");
			sql.append("and iv.codemppd=? and iv.codfilialpd=? and iv.codprod=? ");
			sql.append("inner join eqproduto pd ");
			sql.append("on pd.codemp=iv.codemppd and pd.codfilial=iv.codfilialpd and pd.codprod=iv.codprod ");
			sql.append("where vd.codemp=? and vd.codfilial=? and vd.tipovenda='V' and dtemitvenda=? ");
			sql.append("and substring(vd.statusvenda from 1 for 1) not in ('C','N') ");
			sql.append("and vd.codempcl=? and vd.codfilialcl=? and vd.codcli=? " );
			sql.append("order by vd.dtemitvenda desc" );
			PreparedStatement ps = getConn().prepareStatement( sql.toString() );
			int iparam = 1;
			ps.setInt( iparam++, codemppd );
			ps.setInt( iparam++, codfilialpd );
			ps.setInt( iparam++, codprod );
			ps.setInt( iparam++, codempvd );
			ps.setInt( iparam++, codfilialvd );
			ps.setDate( iparam++, Funcoes.dateToSQLDate( dtemitvenda ) );
			ps.setInt( iparam++, codempcl );
			ps.setInt( iparam++, codfilialcl );
			ps.setInt( iparam++, codcli );
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				String tipovenda = rs.getString( "TIPOVENDA" );
				Integer codvenda = rs.getInt( "CODVENDA" );
				String statusvenda = rs.getString( "STATUSVENDA" );
				java.sql.Date emaberto = null;
				Integer pago = null;
				Integer atraso = null;
				Object[] result_receber2 = loadReceber2( codempvd, codfilialvd, tipovenda, codvenda );
				if (result_receber2!=null) {
					pago = (Integer) result_receber2[RESULT_RECEBER2.PAGO.ordinal()];
					emaberto = (java.sql.Date) result_receber2[RESULT_RECEBER2.EMABERTO.ordinal()];
					atraso = (Integer) result_receber2[RESULT_RECEBER2.ATRASO.ordinal()];
				}
				imgColuna = getImgStatus(statusvenda);
				imgVencimento = getImgVencimento(statusvenda, emaberto, pago, atraso);
				row.addElement( imgColuna);
				row.addElement( imgVencimento );
				row.addElement( codvenda );
				row.addElement( rs.getString( "DOCVENDA" ) );
				row.addElement( Funcoes.dateToStrDate( rs.getDate( "DTEMITVENDA" ) ) );
				row.addElement( rs.getInt( "CODPROD" ) );
				row.addElement( rs.getString( "DESCPROD" ) );
				row.addElement( Funcoes.bdToStr( rs.getBigDecimal( "QTDITVENDA" ) ) );
				row.addElement( Funcoes.bdToStr( rs.getBigDecimal( "PRECOITVENDA" ) ) );
				row.addElement( Funcoes.bdToStr( rs.getBigDecimal( "PERCDESCITVENDA" ) ) );
				row.addElement( Funcoes.bdToStr( rs.getBigDecimal( "VLRDESCITVENDA" ) ) );
				row.addElement( Funcoes.bdToStr( rs.getBigDecimal( "VLRLIQITVENDA" ) ) );
				row.addElement( tipovenda );
			}
			rs.close();
			ps.close();
		} catch (SQLException e) {
			getConn().rollback();
			throw new Exception(e.getMessage());
		}
		return row;
 
	}
	
	public Vector<Vector<Object>> loadProdVendas( Integer codempvd, Integer codfilialvd
			, Integer codempcl, Integer codfilialcl, Integer codcli 
			, Integer codemppd, Integer codfilialpd, Integer codprod
			, Date dtini, Date dtfim) throws Exception {
		Vector<Vector<Object>> result = new Vector<Vector<Object>>();
		try {
			StringBuilder sql = new StringBuilder();
			/*
					STATUSVENDA, STATUSPGTO, CODVENDA, DOCVENDA, DTEMITVENDA, CODPROD, DESCPROD, QTDITVENDA, PRECOITVENDA, PERCDESCITVENDA
		, VLRDESCITVENDA, VLRLIQITVENDA, TIPOVENDA;*/
			sql.append("select pd.codprod, pd.descprod, max(vd.dtemitvenda) dtemitvenda ");
			sql.append("from vdvenda vd ");
			sql.append("inner join vditvenda iv ");
			sql.append("on iv.codemp=vd.codemp and iv.codfilial=vd.codfilial and iv.tipovenda=vd.tipovenda and iv.codvenda=vd.codvenda ");
			sql.append("inner join eqproduto pd ");
			sql.append("on pd.codemp=iv.codemppd and pd.codfilial=iv.codfilialpd and pd.codprod=iv.codprod ");
			sql.append("where vd.codemp=? and vd.codfilial=? and vd.dtemitvenda between ? and ? ");
			sql.append("and vd.tipovenda='V' and vd.codempcl=? and vd.codfilialcl=? and vd.codcli=? ");
			sql.append("and substring(vd.statusvenda from 1 for 1) not in ('C','N') ");
			sql.append("and pd.codemp=? and pd.codfilial=? ");
			if ( codprod > 0 ) {
				sql.append("and pd.codprod=? ");
			}
			sql.append("group by 1, 2 ");
			sql.append("order by 3 desc, 2 ");
			PreparedStatement ps = getConn().prepareStatement( sql.toString() );
			int iparam = 1;
			ps.setInt( iparam++, codempvd );
			ps.setInt( iparam++, codfilialvd );
			ps.setDate( iparam++, Funcoes.dateToSQLDate( dtini ) );
			ps.setDate( iparam++, Funcoes.dateToSQLDate( dtfim ) );
			ps.setInt( iparam++, codempcl );
			ps.setInt( iparam++, codfilialcl );
			ps.setInt( iparam++, codcli );
			ps.setInt( iparam++, codemppd );
			ps.setInt( iparam++, codfilialpd );
			if ( codprod.intValue() > 0 ) {
				ps.setInt( iparam++, codprod );
			}
			ResultSet rs = ps.executeQuery();
			while ( rs.next() ) {
				Integer codprodvd = rs.getInt( "CODPROD" );
				Date dtemitvenda = rs.getDate( "DTEMITVENDA" );
				Vector<Object> row = loadProdItemVenda( codempvd, codfilialvd, dtemitvenda, codempcl, codfilialcl, codcli, codemppd, codfilialpd, codprodvd );
				result.addElement( row );
			}
			rs.close();
			ps.close();
			getConn().commit();
		} catch (SQLException e) {
			getConn().rollback();
			throw new Exception(e.getMessage());
		}
		return result;
	}

	private ImageIcon getImgVencimento(String statusvenda, java.sql.Date emaberto, Integer pago, Integer atraso) {
		ImageIcon result = null;
		if ( "C".equals( statusvenda.substring( 0, 1 ) ) ) {
			result = imgPgEmDia;
		}
		else{
			if (emaberto!=null && (pago==null || pago.intValue()==0)) {
				if (atraso!=null && atraso.intValue()>0) {
					result = imgVencida;
				} else {
					result = imgAVencer;
				}
/*				Date vencimento = emaberto;
				Calendar hoje = Calendar.getInstance();
				if (hoje.after( vencimento )){
					result = imgVencida;
				}*/
			}
			else if ( pago!=null && emaberto!=null && pago.intValue()>0  ) {
				if (atraso!=null && atraso.intValue()>0) {
					result = imgPgEmAtraso;
				} else {
					result = imgPgEmDia;
				}
			}
			else if ( pago!=null && emaberto==null ) {
				if (atraso!=null && atraso.intValue()>0) {
					result = imgPgEmAtraso;
				} else {
					result = imgPgEmDia;
				}
			}
			else if ( emaberto==null && pago==null ) {
				if ( "P".equals( statusvenda.substring( 0, 1 ) ) ) {
					result = imgAVencer;
				}
				else{
					result = imgPgEmDia;
				}
			}
		}

		return result;
	}
	private ImageIcon getImgStatus(String statusvenda) {
		ImageIcon result = null;
		if ( "C".equals( statusvenda.substring( 0, 1 ) ) ) {
			result = imgCancelado;
		}
		else if ( "P".equals( statusvenda.substring( 0, 1 ) ) ) {
			result = imgPedido;
		}
		else {
			result = imgFaturado;
		}
		return result;
	}
	private String getTipoFrete (String cod){
		if ("C".equals( cod )){
			return "CIF";
		}
		else if ("F".equals( cod )){
			return "FOB";
		}
		else{
			return "";
		}
	}

	
	public ImageIcon getImgVencida() {
	
		return imgVencida;
	}

	
	public void setImgVencida( ImageIcon imgVencida ) {
	
		this.imgVencida = imgVencida;
	}

	
	public ImageIcon getImgAVencer() {
	
		return imgAVencer;
	}

	
	public void setImgAVencer( ImageIcon imgAVencer ) {
	
		this.imgAVencer = imgAVencer;
	}

	
	public ImageIcon getImgPgEmAtraso() {
	
		return imgPgEmAtraso;
	}

	
	public void setImgPgEmAtraso( ImageIcon imgPgEmAtraso ) {
	
		this.imgPgEmAtraso = imgPgEmAtraso;
	}

	
	public ImageIcon getImgPgEmDia() {
	
		return imgPgEmDia;
	}

	
	public void setImgPgEmDia( ImageIcon imgPgEmDia ) {
	
		this.imgPgEmDia = imgPgEmDia;
	}

	
	public ImageIcon getImgCancelado() {
	
		return imgCancelado;
	}

	
	public void setImgCancelado( ImageIcon imgCancelado ) {
	
		this.imgCancelado = imgCancelado;
	}

	
	public ImageIcon getImgPedido() {
	
		return imgPedido;
	}

	
	public void setImgPedido( ImageIcon imgPedido ) {
	
		this.imgPedido = imgPedido;
	}

	
	public ImageIcon getImgFaturado() {
	
		return imgFaturado;
	}

	
	public void setImgFaturado( ImageIcon imgFaturado ) {
	
		this.imgFaturado = imgFaturado;
	}

	
	public ImageIcon getImgColuna() {
	
		return imgColuna;
	}

	
	public void setImgColuna( ImageIcon imgColuna ) {
	
		this.imgColuna = imgColuna;
	}

	
	public ImageIcon getImgVencimento() {
	
		return imgVencimento;
	}

	
	public void setImgVencimento( ImageIcon imgVencimento ) {
	
		this.imgVencimento = imgVencimento;
	}

}
