package org.freedom.modulos.std.dao;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.freedom.infra.dao.AbstractDAO;
import org.freedom.infra.functions.ConversionFunctions;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.functions.Funcoes;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.frame.Aplicativo;
import org.freedom.modulos.fnc.view.dialog.utility.DLBaixaRec.BaixaRecBean;
import org.freedom.modulos.std.business.object.ConsultaReceber;

public class DAOMovimento extends AbstractDAO {

	private enum PARAM_INSERT_SL { NONE, CODEMP,CODFILIAL,CODLANCA,CODSUBLANCA,CODEMPCL,CODFILIALCL,CODCLI,
		CODEMPPN, CODFILIALPN, CODPLAN, CODEMPRC, CODFILIALRC, CODREC, NPARCITREC, CODEMPCC, CODFILIALCC, 
		ANOCC, CODCC, ORIGSUBLANCA, DTCOMPSUBLANCA, DATASUBLANCA, DTPREVSUBLANCA, VLRSUBLANCA, TIPOSUBLANCA
	}

	public DAOMovimento (DbConnection cn) {
		super(cn);
	}

	public Integer pesquisaDocRec(Integer docrec) {

		StringBuilder sql = new StringBuilder();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Integer ret = null;

		try {

			sql.append( "select codrec from fnreceber where codemp=? and codfilial=? and docrec=?" );

			ps = getConn().prepareStatement( sql.toString() );

			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "FNRECEBER" ) );
			ps.setInt( 3, docrec );

			rs = ps.executeQuery();

			if(rs.next()) {
				ret = rs.getInt( "codrec" );
			}

			rs.close();
			ps.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ps = null;
			rs = null;
		}

		return ret;
	}

	public Integer pesquisaPedidoRec(Integer codvenda) {

		StringBuilder sql = new StringBuilder();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Integer ret = null;

		try {
			sql.append( "select codrec from fnreceber where codemp=? and codfilial=? and codvenda=? and tipovenda='V'" );
			ps = getConn().prepareStatement( sql.toString() );

			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "FNRECEBER" ) );
			ps.setInt( 3, codvenda );

			rs = ps.executeQuery();

			if(rs.next()) {
				ret = rs.getInt( "codrec" );
			}
			rs.close();
			ps.close();

		}
		catch (Exception e) {
			e.printStackTrace();
		} finally {
			ps = null;
			rs = null;
		}
		return ret;
	}
	public ResultSet carregaGridConsulta(Integer codemp, Integer codfilial, Integer codempcl, Integer codfilialcl, Integer codcli) throws SQLException {
		StringBuilder sql = new StringBuilder();
		PreparedStatement ps = null;

		sql.append( "SELECT IT.DTVENCITREC,IT.STATUSITREC," );
		sql.append( "(SELECT SERIE FROM VDVENDA V " );
		sql.append( "WHERE V.CODEMP=R.CODEMPVA AND V.CODFILIAL=R.CODFILIALVA " );
		sql.append( "AND V.TIPOVENDA=R.TIPOVENDA AND V.CODVENDA=R.CODVENDA) SERIE," );
		sql.append( "R.DOCREC,R.CODVENDA,R.DATAREC,IT.VLRPARCITREC,IT.DTLIQITREC, IT.DTPAGOITREC,IT.VLRPAGOITREC," );
		sql.append( "(CASE WHEN IT.DTLIQITREC IS NULL THEN CAST('today' AS DATE)-IT.DTVENCITREC " );
		sql.append( "ELSE IT.DTLIQITREC - IT.DTVENCITREC END ) DIASATRASO, R.OBSREC," );
		sql.append( "IT.CODBANCO, (SELECT B.NOMEBANCO FROM FNBANCO B " );
		sql.append( "WHERE B.CODBANCO=IT.CODBANCO AND B.CODEMP=IT.CODEMPBO AND B.CODFILIAL=IT.CODFILIALBO) NOMEBANCO," );
		sql.append( "R.CODREC,IT.NPARCITREC,IT.VLRDESCITREC,IT.VLRJUROSITREC,R.TIPOVENDA,IT.VLRAPAGITREC, IT.VLRCANCITREC " );
		sql.append( "FROM FNRECEBER R,FNITRECEBER IT " );
		sql.append( "WHERE R.CODEMPCL=? AND R.CODFILIALCL=? AND R.CODCLI=? AND R.CODEMP=? AND R.CODFILIAL=? AND IT.CODREC=R.CODREC " );
		sql.append( "AND IT.CODEMP=R.CODEMP AND IT.CODFILIAL=R.CODFILIAL " );
		sql.append( "ORDER BY R.CODREC DESC,IT.NPARCITREC DESC" );

		ps = getConn().prepareStatement( sql.toString() );
		ps.setInt( 1, codempcl );
		ps.setInt( 2, codfilialcl );
		ps.setInt( 3, codcli );
		ps.setInt( 4, codemp );
		ps.setInt( 5, codfilial );

		return ps.executeQuery();
	}

	public ResultSet getResultSetManut( boolean bAplicFiltros, boolean bordero, boolean renegociveis, boolean validaPeriodo, String rgData, String rgVenc
			, String cbRecebidas, String cbCanceladas, String cbEmBordero, String cbRenegociado, String cbAReceber, String cbEmRenegociacao,
			String cbRecParcial, Integer codCliFiltro, Integer codRecManut, Integer seqNossoNumero, Date dIniManut, Date dFimManut) throws SQLException {

		ResultSet rs = null;

		StringBuffer sql = new StringBuffer();
		StringBuffer sWhereManut = new StringBuffer();
		StringBuffer sWhereStatus = new StringBuffer();

		if ( !validaPeriodo ) {
			return null;
		}

		if ( bAplicFiltros ) {

			sWhereManut.append( " AND " );

			if ( "V".equals(rgData) ) {
				sWhereManut.append( "IR.DTVENCITREC" );
			}
			else if ( "E".equals(rgData) ) {
				sWhereManut.append( "IR.DTITREC" );
			}
			else {
				sWhereManut.append( "COALESCE(IR.DTPREVITREC,IR.DTVENCITREC)" );
			}

			// sWhereManut.append( rgData.getVlrString().equals( "V" ) ? "IR.DTVENCITREC" : "IR.DTITREC" );
			sWhereManut.append( " BETWEEN ? AND ? AND R.CODEMP=? AND R.CODFILIAL=?" );

			if ( "S".equals( cbRecebidas ) || "S".equals( cbAReceber) || "S".equals( cbRecParcial) || 
					"S".equals( cbCanceladas) || "S".equals( cbEmBordero)  || "S".equals( cbRenegociado ) || 
					"S".equals( cbEmRenegociacao) ) {

				boolean bStatus = false;

				if ( "S".equals( cbRecebidas) && !renegociveis) {
					sWhereStatus.append( "IR.STATUSITREC='RP'" );
					bStatus = true;
				}
				if ( "S".equals( cbAReceber ) ) {
					sWhereStatus.append( bStatus ? " OR IR.STATUSITREC='R1' " : " IR.STATUSITREC='R1' " );
					bStatus = true;
				}
				if ( "S".equals( cbRecParcial ) ) {
					sWhereStatus.append( bStatus ? " OR IR.STATUSITREC='RL' " : " IR.STATUSITREC='RL' " );
					bStatus = true;
				}
				if ( "S".equals( cbCanceladas) && !renegociveis) {
					sWhereStatus.append( bStatus ? " OR IR.STATUSITREC='CR'" : " IR.STATUSITREC='CR' " );
					bStatus = true;
				}
				if ( "S".equals( cbEmBordero ) ) {
					sWhereStatus.append( bStatus ? " OR IR.STATUSITREC='RB'" : " IR.STATUSITREC='RB' " );
					bStatus = true;
				}
				if ( "S".equals( cbRenegociado) && !renegociveis) {
					sWhereStatus.append( bStatus ? " OR IR.STATUSITREC='RN'" : " IR.STATUSITREC='RN' " );
					bStatus = true;
				}
				if ( "S".equals( cbEmRenegociacao) && !renegociveis) {
					sWhereStatus.append( bStatus ? " OR IR.STATUSITREC='RR'" : " IR.STATUSITREC='RR' " );
					bStatus = true;
				}

				sWhereManut.append( " AND (" );
				sWhereManut.append( sWhereStatus );
				sWhereManut.append( ")" );
			}
			else {
				Funcoes.mensagemInforma( null, "Você deve selecionar ao menos um filtro de status!" );
				return null;
			}

			if ( !"TT".equals( rgVenc ) ) {

				sWhereManut.append( " AND IR.DTVENCITREC" );

				if ( rgVenc.equals( "VE" ) ) {
					sWhereManut.append( " <'" );
					sWhereManut.append( Funcoes.dateToStrDB( Calendar.getInstance().getTime() ) );
					sWhereManut.append( "'" );
				}
				else {
					sWhereManut.append( " >='" );
					sWhereManut.append( Funcoes.dateToStrDB( Calendar.getInstance().getTime() ) );
					sWhereManut.append( "'" );
				}
			}
			if ( codCliFiltro > 0 ) {
				sWhereManut.append( " AND R.CODCLI=" );
				sWhereManut.append( codCliFiltro );
			}

			if ( bordero ) {
				sWhereManut.append( " AND NOT EXISTS (SELECT B.NPARCITREC FROM FNITBORDERO B " );
				sWhereManut.append( "WHERE B.CODEMPRC=IR.CODEMP AND B.CODFILIALRC=IR.CODFILIAL AND " );
				sWhereManut.append( "B.CODREC=IR.CODREC AND B.NPARCITREC=IR.NPARCITREC) " );
			}

			if( renegociveis ){
				sWhereManut.append( " AND NOT EXISTS (SELECT B.NPARCITREC FROM FNITRENEGREC B " );
				sWhereManut.append( "WHERE B.CODEMPIR=IR.CODEMP AND B.CODFILIALIR=IR.CODFILIAL AND " );
				sWhereManut.append( "B.CODREC=IR.CODREC AND B.NPARCITREC=IR.NPARCITREC) " );
			}
		}
		else {
			if (codRecManut > 0) {
				sWhereManut.append( " AND R.CODREC=? ");
			}
			else {
				sWhereManut.append( " AND " );

				if ("V".equals(rgData)) {
					sWhereManut.append( "IR.DTVENCITREC" );
				}
				else if ("E".equals(rgData)) {
					sWhereManut.append( "IR.DTITREC" );
				}
				else {
					sWhereManut.append( "COALESCE(IR.DTPREVITREC,IR.DTVENCITREC)" );
				}

				sWhereManut.append( " BETWEEN ? AND ? " );

			}
			sWhereManut.append( " AND R.CODEMP=? AND R.CODFILIAL=? " );
		}

		sql.append( "SELECT IR.DTVENCITREC,IR.DTPREVITREC,IR.STATUSITREC,R.CODCLI,C.RAZCLI,R.CODREC,IR.DOCLANCAITREC," );
		sql.append( "R.CODVENDA,IR.VLRPARCITREC, IR.DTLIQITREC, IR.DTPAGOITREC,IR.VLRPAGOITREC,IR.VLRAPAGITREC,IR.NUMCONTA," );
		sql.append( "IR.VLRDESCITREC,IR.CODPLAN,IR.CODCC,IR.OBSITREC,IR.NPARCITREC,IR.VLRJUROSITREC," );
		sql.append( "IR.DTITREC,IR.CODBANCO,IR.CODCARTCOB, " );
		sql.append( "(SELECT C.DESCCONTA FROM FNCONTA C " );
		sql.append( "WHERE C.NUMCONTA=IR.NUMCONTA " );
		sql.append( "AND C.CODEMP=IR.CODEMPCA AND C.CODFILIAL=IR.CODFILIALCA) DESCCONTA," );
		sql.append( "(SELECT P.DESCPLAN FROM FNPLANEJAMENTO P " );
		sql.append( "WHERE P.CODPLAN=IR.CODPLAN " );
		sql.append( "AND P.CODEMP=IR.CODEMPPN AND P.CODFILIAL=IR.CODFILIALPN) DESCPLAN," );
		sql.append( "(SELECT CC.DESCCC FROM FNCC CC " );
		sql.append( "WHERE CC.CODCC=IR.CODCC " );
		sql.append( "AND CC.CODEMP=IR.CODEMPCC AND CC.CODFILIAL=IR.CODFILIALCC AND CC.ANOCC=IR.ANOCC) DESCCC," );
		sql.append( "(SELECT VD.DOCVENDA FROM VDVENDA VD " );
		sql.append( "WHERE VD.TIPOVENDA=R.TIPOVENDA AND VD.CODVENDA=R.CODVENDA AND " );
		sql.append( " VD.CODEMP=R.CODEMPVA AND VD.CODFILIAL=R.CODFILIALVA) DOCVENDA," );
		sql.append( "IR.CODTIPOCOB, " );
		sql.append( "(SELECT TP.DESCTIPOCOB FROM FNTIPOCOB TP " );
		sql.append( "WHERE TP.CODEMP=IR.CODEMPTC " );
		sql.append( "AND TP.CODFILIAL=IR.CODFILIALTC AND TP.CODTIPOCOB=IR.CODTIPOCOB) DESCTIPOCOB, " );
		sql.append( "(SELECT BO.NOMEBANCO FROM FNBANCO BO WHERE BO.CODBANCO=IR.CODBANCO " );
		sql.append( "AND BO.CODEMP=IR.CODEMPBO AND BO.CODFILIAL=IR.CODFILIALBO) NOMEBANCO," );
		sql.append( "(SELECT CB.DESCCARTCOB FROM FNCARTCOB CB WHERE CB.CODBANCO=IR.CODBANCO " );
		sql.append( "AND CB.CODEMP=IR.CODEMPBO AND CB.CODFILIAL=IR.CODFILIALBO AND CB.CODCARTCOB=IR.CODCARTCOB) DESCCARTCOB, " );
		sql.append( "R.DOCREC, IR.VLRDEVITREC, IR.DESCPONT, IR.VLRCANCITREC, IR.SEQNOSSONUMERO, " );

		sql.append( "(SELECT FIRST 1 ITR.CODATENDO FROM ATATENDIMENTOITREC ITR " );
		sql.append( "WHERE ITR.CODEMPIR=IR.CODEMP AND ITR.CODFILIALIR=IR.CODFILIAL " );
		sql.append( "AND ITR.CODREC=IR.CODREC AND ITR.NPARCITREC=IR.NPARCITREC ) AS ATEND, " );

		sql.append( "SN.CORSINAL, IR.MULTIBAIXA ");

		sql.append( "FROM FNRECEBER R, VDCLIENTE C, FNITRECEBER IR " );

		sql.append( "LEFT OUTER JOIN FNSINAL SN ON SN.CODEMP=IR.CODEMPSN AND SN.CODFILIAL=IR.CODFILIALSN AND SN.CODSINAL=IR.CODSINAL ");

		sql.append( "WHERE IR.CODEMP=R.CODEMP AND IR.CODFILIAL=R.CODFILIAL AND R.CODREC=IR.CODREC AND " );
		sql.append( "C.CODCLI=R.CODCLI AND C.CODEMP=R.CODEMPCL AND C.CODFILIAL=R.CODFILIALCL " );
		sql.append( sWhereManut );

		if (seqNossoNumero>0) {
			sql.append( "and ir.seqnossonumero="  + seqNossoNumero );
		}

		sql.append( " ORDER BY IR.DTVENCITREC,IR.STATUSITREC,IR.CODREC,IR.NPARCITREC" );

		PreparedStatement ps = getConn().prepareStatement( sql.toString() );

		if ( bAplicFiltros ) {
			ps.setDate( 1, Funcoes.dateToSQLDate( dIniManut ) );
			ps.setDate( 2, Funcoes.dateToSQLDate( dFimManut ) );
			ps.setInt( 3, Aplicativo.iCodEmp );
			ps.setInt( 4, ListaCampos.getMasterFilial( "FNRECEBER" ) );
		}
		else {
			int iparam = 1;
			if (codRecManut>0) {
				ps.setInt( iparam++, codRecManut );
			}
			else
			{
				ps.setDate( iparam++, Funcoes.dateToSQLDate( dIniManut ) );
				ps.setDate( iparam++, Funcoes.dateToSQLDate( dFimManut ) );
			}
			ps.setInt( iparam++, Aplicativo.iCodEmp );
			ps.setInt( iparam++, ListaCampos.getMasterFilial( "FNRECEBER" ) );
		}

		rs = ps.executeQuery();

		return rs;
	}

	public ConsultaReceber buscaConsultaReceber(Integer codemp, Integer codfilial, Integer codempcl, Integer codfilialcl, Integer codcli) throws SQLException {
		ConsultaReceber consulta = new ConsultaReceber();
		StringBuilder sql = new StringBuilder();

		//PreparedStatement para a primeira query
		PreparedStatement ps = null;
		ResultSet rs = null;
		//PreparedStatement para a segunda query
		PreparedStatement psmax = null;
		ResultSet rsmax = null;
		//PreparedStatement para a terceira query
		PreparedStatement pssum = null;
		ResultSet rssum = null;

		// Busca totais ...
		sql.append( "select coalesce(sum(ir.vlritrec),0) vlritrec, coalesce(sum(ir.vlrpagoitrec),0) vlrpagoitrec, coalesce(sum(ir.vlrparcitrec),0) vlrparcitrec, ");
		sql.append( "coalesce(sum(ir.vlrapagitrec),0) vlrapagitrec, min(datarec) dataprim, max(datarec) datault " );
		sql.append( "from fnreceber rc, fnitreceber ir " );
		sql.append( "where rc.codemp=ir.codemp and rc.codfilial=ir.codfilial and rc.codrec=ir.codrec and " );
		sql.append( "ir.CODEMP=? AND ir.CODFILIAL=? AND rc.CODEMPCL=? and rc.codfilialcl=? and CODCLI=? " );

		ps = getConn().prepareStatement( sql.toString() );
		ps.setInt( 1, codemp );
		ps.setInt( 2, codfilial );
		ps.setInt( 3, codempcl );
		ps.setInt( 4, codfilialcl );
		ps.setInt( 5, codcli );

		rs = ps.executeQuery();

		if ( rs.next() ) {

			/*		
			txtVlrTotVendLiq.setVlrBigDecimal( rs.getBigDecimal( "vlritrec" ) );
			txtVlrTotPago.setVlrBigDecimal( rs.getBigDecimal( "vlrpagoitrec" ) );
			txtVlrTotVendBrut.setVlrBigDecimal( rs.getBigDecimal( "vlrparcitrec" ) );
			txtVlrTotAberto.setVlrBigDecimal( rs.getBigDecimal( "vlrapagitrec" ) );
			txtPrimCompr.setVlrString( rs.getDate( "dataprim" ) );
			txtUltCompr.setVlrString( rs.getDate( "datault" ) != null ? StringFunctions.sqlDateToStrDate( rs.getDate( "datault" ) ) : "" );
			 */

			consulta.setVlrtotvendliq( rs.getBigDecimal( "vlritrec" ) );
			consulta.setVlrtotpago( rs.getBigDecimal( "vlrpagoitrec" ) );
			consulta.setVlrtotvendbrut( rs.getBigDecimal( "vlrparcitrec" ) );
			consulta.setVlrtotaberto( rs.getBigDecimal( "vlrapagitrec" ) );
			consulta.setPrimcompra( rs.getDate( "dataprim" )  );
			consulta.setUltcompra( rs.getDate( "datault" )  );
		}

		rs.close();
		ps.close();

		getConn().commit();

		// Busca a maior fatura ...
		sql.delete( 0, sql.length() );
		sql.append( "SELECT MAX(VLRREC) VLRREC,DATAREC " );
		sql.append( "FROM FNRECEBER " );
		sql.append( "WHERE CODEMP=? AND CODFILIAL=? AND CODCLI=? " );
		sql.append( "GROUP BY DATAREC " );
		sql.append( "ORDER BY 1 DESC" );

		psmax = getConn().prepareStatement( sql.toString() );
		psmax.setInt( 1, codemp );
		psmax.setInt( 2, codfilial );
		psmax.setInt( 3, codcli );

		rsmax = psmax.executeQuery();

		if ( rsmax.next() ) {
			/*
			txtVlrMaxFat.setVlrString( Funcoes.strDecimalToStrCurrency( 15, Aplicativo.casasDecFin, rs1.getString( 1 ) ) );
			txtDataMaxFat.setVlrString( StringFunctions.sqlDateToStrDate( rs1.getDate( "DATAREC" ) ) );
			 */
			consulta.setVlrmaxfat( rsmax.getBigDecimal( "VLRREC" ) );
			consulta.setDatamaxfat( rsmax.getDate( "DATAREC" ) );
		}


		rsmax.close();
		psmax.close();

		getConn().commit();

		// Busca o maior acumulo ...
		sql.delete( 0, sql.length() );
		sql.append( "SELECT EXTRACT(MONTH FROM DATAREC), SUM(VLRREC), EXTRACT(YEAR FROM DATAREC) " );
		sql.append( "FROM FNRECEBER " );
		sql.append( "WHERE CODEMP=? AND CODFILIAL=? AND CODCLI=? " );
		sql.append( "GROUP BY 1, 3 " );
		sql.append( "ORDER BY 2 DESC" );

		pssum = getConn().prepareStatement( sql.toString() );
		pssum.setInt( 1, codemp );
		pssum.setInt( 2, codfilial );
		pssum.setInt( 3, codcli );

		rssum = pssum.executeQuery();

		if ( rssum.next() ) {
			/*
			txtDataMaxAcum.setVlrString( Funcoes.strMes( rs2.getInt( 1 ) ) + " de " + rs2.getInt( 3 ) );
			txtVlrMaxAcum.setVlrString( Funcoes.strDecimalToStrCurrency( 15, Aplicativo.casasDecFin, rs2.getString( 2 ) ) );
			 */

			consulta.setDatamaxacum( Funcoes.strMes( rssum.getInt( 1 ) ) + " de " + rssum.getInt( 3 ) );
			consulta.setVlrmaxacum( new BigDecimal( rssum.getString( 2 ) ) );
		}

		rssum.close();
		pssum.close();

		getConn().commit();

		return consulta;
	}

	public void execCancItemRec( int codrec, int nparcitrec, String obs ) throws SQLException {
		PreparedStatement ps = null;
		StringBuilder sql = new StringBuilder( "UPDATE FNITRECEBER SET STATUSITREC='CR', OBSITREC=? " );
		sql.append( "WHERE CODEMP=? AND CODFILIAL=? AND CODREC=? AND NPARCITREC=? " );
		int param = 1;
		ps = getConn().prepareStatement( sql.toString() );
		ps.setString( param++, obs );
		ps.setInt( param++, Aplicativo.iCodEmp );
		ps.setInt( param++, ListaCampos.getMasterFilial( "FNITRECEBER" ) );
		ps.setInt( param++, codrec );
		ps.setInt( param++, nparcitrec );
		ps.executeUpdate();
		ps.close();
		getConn().commit();


		ps.close();
	}

	public void setAltUsuItRec(Integer codrec, Integer nparcitrec, String altusuitrec ) throws SQLException{

		PreparedStatement ps = getConn().prepareStatement( 
				"update fnitreceber set altusuitrec=? , emmanut=? where codemp=? and codfilial=? and codrec=? and nparcitrec=?" );
		ps.setString( 1, altusuitrec );
		ps.setString( 2, "S" );
		ps.setInt( 3, Aplicativo.iCodEmp );
		ps.setInt( 4, ListaCampos.getMasterFilial( "FNITRECEBER" ) );
		ps.setInt( 5, codrec);
		ps.setInt( 6, nparcitrec );
		ps.executeUpdate();
		ps.close();

		ps = getConn().prepareStatement( 
				"update fnitreceber set emmanut=? where codemp=? and codfilial=? and codrec=? and nparcitrec=?" );
		ps.setString( 1, "N" );
		ps.setInt( 2, Aplicativo.iCodEmp );
		ps.setInt( 3, ListaCampos.getMasterFilial( "FNITRECEBER" ) );
		ps.setInt( 4, codrec);
		ps.setInt( 5, nparcitrec );
		ps.executeUpdate();
		ps.close();
	}

	public String[] getPlanejamentoContaRec( int iCodRec ) throws SQLException {

		String[] retorno = new String[ 4 ];


		StringBuffer sSQL = new StringBuffer();
		sSQL.append( " SELECT V.CODPLANOPAG, P.CODPLAN, P.NUMCONTA, P.CODCC" );
		sSQL.append( " FROM VDVENDA V, FNPLANOPAG P, FNRECEBER R" );
		sSQL.append( " WHERE V.CODEMPPG=P.CODEMP AND V.CODFILIALPG=P.CODFILIAL AND V.CODPLANOPAG=P.CODPLANOPAG" );
		sSQL.append( " AND V.CODEMP=R.CODEMPVD AND V.CODFILIAL=R.CODFILIALVD AND V.CODVENDA=R.CODVENDA AND V.TIPOVENDA=R.TIPOVENDA" );
		sSQL.append( " AND R.CODEMP=? AND R.CODFILIAL=? AND R.CODREC=?" );

		PreparedStatement ps = getConn().prepareStatement( sSQL.toString() );
		ps.setInt( 1, Aplicativo.iCodEmp );
		ps.setInt( 2, ListaCampos.getMasterFilial( "FNRECEBER" ) );
		ps.setInt( 3, iCodRec );

		ResultSet rs = ps.executeQuery();

		if ( rs.next() ) {
			for ( int i = 0; i < retorno.length; i++ ) {
				retorno[ i ] = rs.getString( i + 1 ) == null ? "" : rs.getString( i + 1 );
			}
		}
		ps.close();
		getConn().commit();

		return retorno;
	}

	public void updateItReceber(BaixaRecBean baixaRecBean, int ianocc, int icodrec, int inparcitrec) throws SQLException {
		StringBuilder sql = new StringBuilder();
		PreparedStatement ps = null;

		sql.append( "UPDATE FNITRECEBER SET NUMCONTA=?,CODEMPCA=?,CODFILIALCA=?,CODPLAN=?,CODEMPPN=?,CODFILIALPN=?," );
		sql.append( "ANOCC=?,CODCC=?,CODEMPCC=?,CODFILIALCC=?,DOCLANCAITREC=?,DTPAGOITREC=?,VLRPAGOITREC=VLRPAGOITREC+?," );
		sql.append( "VLRDESCITREC=?,VLRJUROSITREC=?,OBSITREC=?,STATUSITREC='RP', ALTUSUITREC=? " );
		sql.append( "WHERE CODREC=? AND NPARCITREC=? AND CODEMP=? AND CODFILIAL=?" );
		int param = 1;
		ps = getConn().prepareStatement( sql.toString() );
		ps.setString( param++, baixaRecBean.getConta() );
		ps.setInt( param++, Aplicativo.iCodEmp );
		ps.setInt( param++, ListaCampos.getMasterFilial( "FNCONTA" ) );
		ps.setString( param++, baixaRecBean.getPlanejamento() );
		ps.setInt( param++, Aplicativo.iCodEmp );
		ps.setInt( param++, ListaCampos.getMasterFilial( "FNPLANEJAMENTO" ) );

		if ( baixaRecBean.getCentroCusto() == null || "".equals( baixaRecBean.getCentroCusto().trim() ) ) {
			ps.setNull( param++, Types.INTEGER );
			ps.setNull( param++, Types.CHAR );
			ps.setNull( param++, Types.INTEGER );
			ps.setNull( param++, Types.INTEGER );
		}
		else {
			ps.setInt( param++, ianocc );
			ps.setString( param++, baixaRecBean.getCentroCusto() );
			ps.setInt( param++, Aplicativo.iCodEmp );
			ps.setInt( param++, ListaCampos.getMasterFilial( "FNCC" ) );
		}

		ps.setString( param++, baixaRecBean.getDocumento() );
		ps.setDate( param++, Funcoes.dateToSQLDate( baixaRecBean.getDataPagamento() ) );
		ps.setBigDecimal( param++, baixaRecBean.getValorPago() );
		ps.setBigDecimal( param++, baixaRecBean.getValorDesconto() );
		ps.setBigDecimal( param++, baixaRecBean.getValorJuros() );
		ps.setString( param++, baixaRecBean.getObservacao() );
		ps.setString( param++, "S" );
		ps.setInt( param++, icodrec );
		ps.setInt( param++, inparcitrec );
		ps.setInt( param++, Aplicativo.iCodEmp );
		ps.setInt( param++, ListaCampos.getMasterFilial( "FNRECEBER" ) );
		ps.executeUpdate();
		ps.close();

		setAltUsuItRec( icodrec, inparcitrec, "N" );
		getConn().commit();
	}

	public void geraSublanca(Integer codrec, Integer nparcrec, Integer codlanca, Integer codsublanca, String codplan, Integer codcli, 
			String codcc, String dtitrec, Date datasublanca, Date dtprevsublanca, BigDecimal vlrsublanca, String tiposublanca, Integer iAnoCC ) throws SQLException{
		PreparedStatement ps = null;
		StringBuilder sqlSubLanca = new StringBuilder();
		sqlSubLanca.append( "INSERT INTO FNSUBLANCA (CODEMP,CODFILIAL,CODLANCA,CODSUBLANCA,CODEMPCL,CODFILIALCL,CODCLI,CODEMPPN,CODFILIALPN,CODPLAN,");
		sqlSubLanca.append( "CODEMPRC, CODFILIALRC, CODREC, NPARCITREC, ");
		sqlSubLanca.append( "CODEMPCC, CODFILIALCC,ANOCC, CODCC, ORIGSUBLANCA, DTCOMPSUBLANCA, DATASUBLANCA,DTPREVSUBLANCA,VLRSUBLANCA, TIPOSUBLANCA) ");
		sqlSubLanca.append( "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ");


		ps = getConn().prepareStatement( sqlSubLanca.toString() );

		ps.setInt( PARAM_INSERT_SL.CODEMP.ordinal(), Aplicativo.iCodEmp );
		ps.setInt( PARAM_INSERT_SL.CODFILIAL.ordinal(), ListaCampos.getMasterFilial( "FNSUBLANCA" ) );
		ps.setInt( PARAM_INSERT_SL.CODLANCA.ordinal(), codlanca );
		ps.setInt( PARAM_INSERT_SL.CODSUBLANCA.ordinal(), codsublanca );

		ps.setInt( PARAM_INSERT_SL.CODEMPCL.ordinal(), Aplicativo.iCodEmp );
		ps.setInt( PARAM_INSERT_SL.CODFILIALCL.ordinal(),  ListaCampos.getMasterFilial( "VDCLIENTE" ));
		ps.setInt( PARAM_INSERT_SL.CODCLI.ordinal(), codcli );

		ps.setInt( PARAM_INSERT_SL.CODEMPPN.ordinal(), Aplicativo.iCodEmp );
		ps.setInt( PARAM_INSERT_SL.CODFILIALPN.ordinal(), ListaCampos.getMasterFilial( "FNPLANEJAMENTO" ) );
		ps.setString( PARAM_INSERT_SL.CODPLAN.ordinal(), codplan );
		ps.setInt( PARAM_INSERT_SL.CODEMPRC.ordinal(), Aplicativo.iCodEmp );
		ps.setInt( PARAM_INSERT_SL.CODFILIALRC.ordinal(), ListaCampos.getMasterFilial( "FNITRECEBER" ) );
		ps.setInt( PARAM_INSERT_SL.CODREC.ordinal(), codrec );
		ps.setInt( PARAM_INSERT_SL.NPARCITREC.ordinal(), nparcrec );


		if ( "".equals( codcc ) ) {
			ps.setNull( PARAM_INSERT_SL.CODEMPCC.ordinal(), Types.INTEGER );
			ps.setNull( PARAM_INSERT_SL.CODFILIALCC.ordinal(), Types.INTEGER );
			ps.setNull( PARAM_INSERT_SL.ANOCC.ordinal(), Types.CHAR );
			ps.setNull( PARAM_INSERT_SL.CODCC.ordinal(), Types.INTEGER );
		} else {
			ps.setInt( PARAM_INSERT_SL.CODEMPCC.ordinal(), Aplicativo.iCodEmp );
			ps.setInt( PARAM_INSERT_SL.CODFILIALCC.ordinal(), ListaCampos.getMasterFilial( "FNCC" ) );
			ps.setInt( PARAM_INSERT_SL.ANOCC.ordinal(), iAnoCC );
			ps.setString( PARAM_INSERT_SL.CODCC.ordinal(), codcc );
		}
		ps.setString( PARAM_INSERT_SL.ORIGSUBLANCA.ordinal(), "S" );

		ps.setDate( PARAM_INSERT_SL.DTCOMPSUBLANCA.ordinal(), Funcoes.dateToSQLDate( 
				ConversionFunctions.strDateToDate( dtitrec ) )  ) ;

		ps.setDate( PARAM_INSERT_SL.DATASUBLANCA.ordinal(), Funcoes.dateToSQLDate( datasublanca ) );
		ps.setDate( PARAM_INSERT_SL.DTPREVSUBLANCA.ordinal(), Funcoes.dateToSQLDate( datasublanca ) );
		ps.setBigDecimal( PARAM_INSERT_SL.VLRSUBLANCA.ordinal(), vlrsublanca );
		ps.setString( PARAM_INSERT_SL.TIPOSUBLANCA.ordinal(), tiposublanca );

		ps.executeUpdate();
	}

	public void excluirRenegociacao(Integer codemp, Integer codfilial, Integer codrec) throws SQLException {
		StringBuilder sqlDelete = new StringBuilder();

		sqlDelete.append( "delete from fnreceber ");
		sqlDelete.append( "where codemp = ? and codfilial = ? " );
		sqlDelete.append( "and codrec = ?" );

		PreparedStatement ps = getConn().prepareStatement( sqlDelete.toString() );
		ps.setInt( 1, codemp );
		ps.setInt( 2, codfilial );
		ps.setInt( 3, codrec );

		ps.executeUpdate();
		getConn().commit();
	}


	public Integer geraSeqId (String tabela) throws SQLException{

		StringBuilder sql = new StringBuilder();
		PreparedStatement ps = null;
		ResultSet rs = null; 
		Integer id = 0;
		ps = getConn().prepareStatement( "select biseq from sgsequence_idsp(?)" );
		ps.setString( 1, tabela );

		rs = ps.executeQuery();
		if (rs.next()) {
			id = rs.getInt( "biseq" ); 
		}
		return id;
	}

	public Map<String, Object> getPrefereRec() throws SQLException {

		PreparedStatement ps = null;
		ResultSet rs = null;
		Integer anocc = null;
		Integer codhistrec = null;
		String codplandc = null;
		String codplanjr = null;

		Map<String, Object> retorno = new HashMap<String, Object>();
		ps = getConn().prepareStatement( "SELECT ANOCENTROCUSTO,CODHISTREC, CODPLANJR, CODPLANDC FROM SGPREFERE1 WHERE CODEMP=? AND CODFILIAL=?" );
		ps.setInt( 1, Aplicativo.iCodEmp );
		ps.setInt( 2, ListaCampos.getMasterFilial( "SGPREFERE1" ) );

		rs = ps.executeQuery();

		if ( rs.next() ) {
			anocc = rs.getInt( "ANOCENTROCUSTO" );
			codhistrec = rs.getInt( "CODHISTREC" );
			codplanjr = rs.getString( "CODPLANJR" );
			codplandc = rs.getString( "CODPLANDC" );
		}

		retorno.put( "codhistrec", codhistrec );
		retorno.put( "anocc", anocc );
		retorno.put( "codplanjr", getString( codplanjr ) );
		retorno.put( "codplandc", getString( codplandc ) );
		rs.close();
		ps.close();

		getConn().commit();
		return retorno;
	}

	private String getString (String value) {
		String result = null;

		if (value == null){
			result = "";
		} else {
			result = value;
		}
		return result;
	}	

	private Integer getInteger (Integer value) {
		Integer result = null;

		if (value == null) {
			result = new Integer(0);
		} else {
			result = value;
		}
		return result;
	}

	private BigDecimal getBigDecimal (BigDecimal value) {
		BigDecimal result = null;

		if (value == null) {
			result = BigDecimal.ZERO;
		} else {
			result = value;
		}
		return result;
	}

}