package org.freedom.modulos.std.dao;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.freedom.infra.dao.AbstractDAO;
import org.freedom.infra.functions.ConversionFunctions;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.functions.Funcoes;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.frame.Aplicativo;
import org.freedom.modulos.fnc.view.frame.utility.FManutRec;

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

	/*	private ResultSet getResultSetManut( boolean bAplicFiltros ) throws SQLException {

		return getResultSetManut( bAplicFiltros, false, false );
	}

	private ResultSet getResultSetManut( boolean bAplicFiltros, boolean bordero, boolean renegociveis, boolean validaPeriodo, String rgData, String rgVenc
			, String cbRecebidas, String cbCanceladas, String cbEmBordero, String cbRenegociado, String cbAReceber, String cbEmRenegociacao,
			String cbRecParcial) throws SQLException {

		ResultSet rs = null;

		StringBuffer sSQL = new StringBuffer();
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
			if ( !"".equals( txtCodCliFiltro.getVlrString().trim() ) ) {
				sWhereManut.append( " AND R.CODCLI=" );
				sWhereManut.append( txtCodCliFiltro.getVlrString() );
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
			if(txtCodRecManut.getVlrInteger()>0) {
				sWhereManut.append( " AND R.CODREC=? ");
			}
			else {
				sWhereManut.append( " AND " );

				if ( "V".equals( rgData ) ) {
					sWhereManut.append( "IR.DTVENCITREC" );
				}
				else if ( "E".equals( rgData ) ) {
					sWhereManut.append( "IR.DTITREC" );
				}
				else {
					sWhereManut.append( "COALESCE(IR.DTPREVITREC,IR.DTVENCITREC)" );
				}

				sWhereManut.append( " BETWEEN ? AND ? " );

			}
			sWhereManut.append( " AND R.CODEMP=? AND R.CODFILIAL=? " );
		}

		sSQL.append( "SELECT IR.DTVENCITREC,IR.DTPREVITREC,IR.STATUSITREC,R.CODCLI,C.RAZCLI,R.CODREC,IR.DOCLANCAITREC," );
		sSQL.append( "R.CODVENDA,IR.VLRPARCITREC, IR.DTLIQITREC, IR.DTPAGOITREC,IR.VLRPAGOITREC,IR.VLRAPAGITREC,IR.NUMCONTA," );
		sSQL.append( "IR.VLRDESCITREC,IR.CODPLAN,IR.CODCC,IR.OBSITREC,IR.NPARCITREC,IR.VLRJUROSITREC," );
		sSQL.append( "IR.DTITREC,IR.CODBANCO,IR.CODCARTCOB, " );
		sSQL.append( "(SELECT C.DESCCONTA FROM FNCONTA C " );
		sSQL.append( "WHERE C.NUMCONTA=IR.NUMCONTA " );
		sSQL.append( "AND C.CODEMP=IR.CODEMPCA AND C.CODFILIAL=IR.CODFILIALCA) DESCCONTA," );
		sSQL.append( "(SELECT P.DESCPLAN FROM FNPLANEJAMENTO P " );
		sSQL.append( "WHERE P.CODPLAN=IR.CODPLAN " );
		sSQL.append( "AND P.CODEMP=IR.CODEMPPN AND P.CODFILIAL=IR.CODFILIALPN) DESCPLAN," );
		sSQL.append( "(SELECT CC.DESCCC FROM FNCC CC " );
		sSQL.append( "WHERE CC.CODCC=IR.CODCC " );
		sSQL.append( "AND CC.CODEMP=IR.CODEMPCC AND CC.CODFILIAL=IR.CODFILIALCC AND CC.ANOCC=IR.ANOCC) DESCCC," );
		sSQL.append( "(SELECT VD.DOCVENDA FROM VDVENDA VD " );
		sSQL.append( "WHERE VD.TIPOVENDA=R.TIPOVENDA AND VD.CODVENDA=R.CODVENDA AND " );
		sSQL.append( " VD.CODEMP=R.CODEMPVA AND VD.CODFILIAL=R.CODFILIALVA) DOCVENDA," );
		sSQL.append( "IR.CODTIPOCOB, " );
		sSQL.append( "(SELECT TP.DESCTIPOCOB FROM FNTIPOCOB TP " );
		sSQL.append( "WHERE TP.CODEMP=IR.CODEMPTC " );
		sSQL.append( "AND TP.CODFILIAL=IR.CODFILIALTC AND TP.CODTIPOCOB=IR.CODTIPOCOB) DESCTIPOCOB, " );
		sSQL.append( "(SELECT BO.NOMEBANCO FROM FNBANCO BO WHERE BO.CODBANCO=IR.CODBANCO " );
		sSQL.append( "AND BO.CODEMP=IR.CODEMPBO AND BO.CODFILIAL=IR.CODFILIALBO) NOMEBANCO," );
		sSQL.append( "(SELECT CB.DESCCARTCOB FROM FNCARTCOB CB WHERE CB.CODBANCO=IR.CODBANCO " );
		sSQL.append( "AND CB.CODEMP=IR.CODEMPBO AND CB.CODFILIAL=IR.CODFILIALBO AND CB.CODCARTCOB=IR.CODCARTCOB) DESCCARTCOB, " );
		sSQL.append( "R.DOCREC, IR.VLRDEVITREC, IR.DESCPONT, IR.VLRCANCITREC, IR.SEQNOSSONUMERO, " );

		sSQL.append( "(SELECT FIRST 1 ITR.CODATENDO FROM ATATENDIMENTOITREC ITR " );
		sSQL.append( "WHERE ITR.CODEMPIR=IR.CODEMP AND ITR.CODFILIALIR=IR.CODFILIAL " );
		sSQL.append( "AND ITR.CODREC=IR.CODREC AND ITR.NPARCITREC=IR.NPARCITREC ) AS ATEND, " );

		sSQL.append( "SN.CORSINAL, IR.MULTIBAIXA ");

		sSQL.append( "FROM FNRECEBER R, VDCLIENTE C, FNITRECEBER IR " );

		sSQL.append( "LEFT OUTER JOIN FNSINAL SN ON SN.CODEMP=IR.CODEMPSN AND SN.CODFILIAL=IR.CODFILIALSN AND SN.CODSINAL=IR.CODSINAL ");

		sSQL.append( "WHERE IR.CODEMP=R.CODEMP AND IR.CODFILIAL=R.CODFILIAL AND R.CODREC=IR.CODREC AND " );
		sSQL.append( "C.CODCLI=R.CODCLI AND C.CODEMP=R.CODEMPCL AND C.CODFILIAL=R.CODFILIALCL " );
		sSQL.append( sWhereManut );

		if(txtSeqNossoNumero.getVlrInteger()>0){
			sSQL.append( "and ir.seqnossonumero="  + txtSeqNossoNumero.getVlrString() );
		}

		sSQL.append( " ORDER BY IR.DTVENCITREC,IR.STATUSITREC,IR.CODREC,IR.NPARCITREC" );

		PreparedStatement ps = con.prepareStatement( sSQL.toString() );

		if ( bAplicFiltros ) {
			ps.setDate( 1, Funcoes.dateToSQLDate( dIniManut ) );
			ps.setDate( 2, Funcoes.dateToSQLDate( dFimManut ) );
			ps.setInt( 3, Aplicativo.iCodEmp );
			ps.setInt( 4, ListaCampos.getMasterFilial( "FNRECEBER" ) );
		}
		else {
			int iparam = 1;
			if(txtCodRecManut.getVlrInteger()>0) {
				ps.setInt( iparam++, txtCodRecManut.getVlrInteger().intValue() );
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
	 */

	public void execCancItemRec( int codrec, int nparcitrec, String obs ) throws SQLException {
		PreparedStatement ps = null;
		StringBuilder sql = new StringBuilder( "UPDATE FNITRECEBER SET STATUSITREC='CR', OBSITREC=? " );
		sql.append( "WHERE CODEMP=? AND CODFILIAL=? AND CODREC=? AND NPARCITREC=? " );

		ps = getConn().prepareStatement( sql.toString() );
		ps.setString( 1, obs );
		ps.setInt( 2, Aplicativo.iCodEmp );
		ps.setInt( 3, ListaCampos.getMasterFilial( "FNITRECEBER" ) );
		ps.setInt( 4, codrec );
		ps.setInt( 5, nparcitrec );
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


