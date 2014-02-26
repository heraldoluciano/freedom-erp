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
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

import javax.swing.ImageIcon;

import org.freedom.bmps.Icone;
import org.freedom.infra.dao.AbstractDAO;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.functions.Funcoes;

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
		STATUSPGTO, CODVENDA, NOTA, DATA, PAGAMENTO, VENDEDOR, VALOR_PRODUTOS, VALOR_DESCONTO, VALOR_ADICIONAL, FRETE, VALOR_LIQUIDO, TIPOVENDA, STATUS;
	}
	public static enum ITEMVENDAS {
		ITEM, CODPROD, DESCPROD, LOTE, QUANTIDADE, PRECO, DESCONTO, FRETE, TOTAL, TIPOVENDA;
	}
	public static enum RESULT_ULTVENDAS{ 
		DTEMITVENDA, VLRLIQVENDA
	}
	public static enum RESULT_RECEBER{ 
		TOTAL, TOTAL_ABERTO
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
		Object[] result = new Object[RESULT_ULTVENDAS.values().length];
		try {
			StringBuilder sql = new StringBuilder();
			sql.append( "select first 1 v.dtemitvenda, v.vlrliqvenda, v.codvenda " );
			sql.append( "from vdvenda v, vdcliente c " );
			sql.append( "where v.codemp=? and v.codfilial=? and v.tipovenda='V' and v.dtemitvenda between ? and ? " );
			sql.append( "and c.codemp=v.codempcl and c.codfilial=v.codfilialcl and c.codcli=v.codcli " );
			sql.append( "and c.codemp=? and c.codfilial=? and c.codcli=? " );
			sql.append( "order by v.dtemitvenda desc" );
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
				result[RESULT_ULTVENDAS.DTEMITVENDA.ordinal()] = rs.getDate( RESULT_ULTVENDAS.DTEMITVENDA.name() );
				result[RESULT_ULTVENDAS.VLRLIQVENDA.ordinal()] = rs.getBigDecimal( RESULT_ULTVENDAS.VLRLIQVENDA.name() );
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
			sql.append( "select sum(ir.vlritrec) total, sum(ir.vlrapagitrec) total_aberto, min(datarec), max(datarec) " );
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
				result[RESULT_RECEBER.TOTAL.ordinal()] = rs.getBigDecimal( RESULT_RECEBER.TOTAL.name() ); 
				result[RESULT_RECEBER.TOTAL_ABERTO.ordinal()] = rs.getBigDecimal( RESULT_RECEBER.TOTAL_ABERTO.name() ); 
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
			sql.append( "select v.codvenda, v.docvenda, v.dtemitvenda, v.statusvenda, v.codplanopag," );
			sql.append( "p.descplanopag, v.codvend, vd.nomevend, coalesce(v.vlrprodvenda,0) vlrprodvenda, coalesce(v.vlrdescvenda,0) vlrdescvenda," );
			sql.append( "coalesce(v.vlradicvenda,0) vlradicvenda , coalesce(v.vlrfretevenda,0) vlrfretevenda, coalesce(v.vlrliqvenda,0) vlrliqvenda, v.tipovenda, " );
			sql.append(" (select first 1 fi.dtvencitrec ");
			sql.append("       from fnitreceber fi ");
			sql.append("      inner join fnreceber fr on fr.codrec = fi.codrec and fr.codfilial = fi.codfilial   and fr.codemp = fi.codemp ");
			sql.append("      inner join vdvenda va on fr.tipovenda = va.tipovenda and fr.codfilialva = va.codfilial and fr.codempva = va.codemp and fr.codvenda = va.codvenda ");
			sql.append("      where fi.statusitrec = 'R1' ");
			sql.append("        and fr.tipovenda = 'V' and fr.codfilialva = v.codfilial and fr.codempva = v.codemp and fr.codvenda = v.codvenda ");
			sql.append("      order by fi.dtvencitrec) ");
			sql.append(" emaberto, ");
			sql.append(" (select first 1 fi.dtvencitrec - fi.dtliqitrec ");
			sql.append("       from fnitreceber fi ");
			sql.append("      inner join fnreceber fr on fr.codrec = fi.codrec       and fr.codfilial = fi.codfilial   and fr.codemp = fi.codemp ");
			sql.append("      inner join vdvenda va   on fr.tipovenda = va.tipovenda and fr.codfilialva = va.codfilial and fr.codempva = va.codemp and fr.codvenda = va.codvenda ");
			sql.append("      where fi.statusitrec = 'RP' ");
			sql.append("        and fr.tipovenda = 'V' and fr.codfilialva = v.codfilial and fr.codempva = v.codemp and fr.codvenda = v.codvenda ");
			sql.append("      order by fi.dtvencitrec) ");
			sql.append(" pago, ");
			sql.append(" coalesce((select vf.tipofretevd from vdfretevd vf where vf.codemp = v.codemp and vf.codfilial = v.codfilial and vf.tipovenda = v.tipovenda and vf.codvenda = v.codvenda), 'N')  as tipofrete ");
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
			sql.append( "order by v.dtemitvenda desc" );
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
				if ( "C".equals( rs.getString( "STATUSVENDA" ).substring( 0, 1 ) ) ) {
					imgColuna = imgCancelado;
				}
				else if ( "P".equals( rs.getString( "STATUSVENDA" ).substring( 0, 1 ) ) ) {
					imgColuna = imgPedido;
				}
				else {
					imgColuna = imgFaturado;
				}
				if ( "C".equals( rs.getString( "STATUSVENDA" ).substring( 0, 1 ) ) ) {
					imgVencimento = imgVencida;
				}
				else{
					if (rs.getDate( "EMABERTO" )!=null ) {
						imgVencimento = imgAVencer;
						Date vencimento = rs.getDate( "EMABERTO" );
						Calendar hoje = Calendar.getInstance();
						if (hoje.after( vencimento )){
							imgVencimento = imgVencida;
						}
					}
					else if ( rs.getString( "PAGO" )!=null ) {
						imgVencimento = imgPgEmDia;
						int diferenca = rs.getInt( "PAGO" );
						if (diferenca < 0){
							imgVencimento = imgPgEmAtraso;
						}
					}
					else if ( rs.getDate( "EMABERTO" )==null && rs.getString( "PAGO" )==null ) {
						if ( "P".equals( rs.getString( "STATUSVENDA" ).substring( 0, 1 ) ) ) {
							imgVencimento = imgAVencer;
						}
						else{
							imgVencimento = imgPgEmDia;
						}
					}
				}
				row.addElement( imgVencimento );
				row.addElement( rs.getInt( "CODVENDA" ) );
				row.addElement( rs.getString( "DOCVENDA" ) );
				row.addElement( Funcoes.dateToStrDate( rs.getDate( "DTEMITVENDA" ) ) );
				row.addElement( rs.getString( "DESCPLANOPAG" ) );
				row.addElement( rs.getString( "NOMEVEND" ) );
				row.addElement( Funcoes.bdToStr( rs.getBigDecimal( "VLRPRODVENDA" ) ) );
				row.addElement( Funcoes.bdToStr( rs.getBigDecimal( "VLRDESCVENDA" ) ) );
				row.addElement( Funcoes.bdToStr( rs.getBigDecimal( "VLRADICVENDA" ) ) );
				row.addElement( getTipoFrete(rs.getString( "TIPOFRETE" )) );
				row.addElement( Funcoes.bdToStr( rs.getBigDecimal( "VLRLIQVENDA" ) ) );
				row.addElement( rs.getString( "TIPOVENDA" ) );
				row.addElement( imgColuna);
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
