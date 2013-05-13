package org.freedom.modulos.std.dao;

import java.awt.Color;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import org.freedom.infra.dao.AbstractDAO;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.business.exceptions.ExceptionCarregaDados;
import org.freedom.library.functions.Funcoes;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.component.JTablePad;
import org.freedom.library.swing.frame.Aplicativo;
import org.freedom.modulos.std.view.dialog.utility.DLBuscaOrc;
import org.freedom.modulos.std.view.dialog.utility.DLBuscaOrc.COL_PREFS;
import org.freedom.modulos.std.view.dialog.utility.DLBuscaOrc.GRID_ITENS;

public class DAOBuscaOrc extends AbstractDAO {


	private Vector<Object> vValidos = new Vector<Object>();


	public DAOBuscaOrc( DbConnection connection ) {

		super( connection );

	}


	public Vector<Vector<Object>> buscar(Integer codorc, Integer codcli, Integer codconv, String busca) throws ExceptionCarregaDados{

		PreparedStatement ps = null;
		ResultSet rs = null;
		String mensagemErro = "";
		String sql = null;
		String sWhere = null;
		Vector<Object> vVals = null;
		boolean bOrc = false;
		boolean bConv = false;
		int iCod = -1;
		Vector<Vector<Object>> vector = null;

		if (codorc.intValue() > 0) {
			iCod = codorc.intValue();
			sWhere = ", VDCLIENTE C WHERE O.CODORC = ? AND O.CODFILIAL = ? AND O.CODEMP = ? AND C.CODEMP=O.CODEMPCL AND C.CODFILIAL=O.CODFILIALCL AND C.CODCLI=O.CODCLI ";
			bOrc = true;
		}
		else {
			if (busca.equals("L") &&  codcli > 0) {
				iCod = codcli.intValue();
				if (iCod == 0) {
					mensagemErro = "Código do cliente inválido!";
					vector = null;
					throw new ExceptionCarregaDados(mensagemErro);
				}
				sWhere = ", VDCLIENTE C WHERE C.CODCLI=? AND C.CODFILIAL=? AND C.CODEMP=? AND O.CODCLI=C.CODCLI AND" +
						" O.CODFILIALCL=C.CODFILIAL AND O.CODEMPCL=C.CODEMP AND O.STATUSORC IN ('OL','FP','OP') ";

			}
			else if (busca.equals( "O" ) && codconv > 0) {
				iCod = codconv.intValue();
				if (iCod == 0) {
					mensagemErro = "Código do conveniado inválido!";
					vector = null;
					throw new ExceptionCarregaDados(mensagemErro);
				}
				sWhere = ", ATCONVENIADO C WHERE C.CODCONV=? AND C.CODFILIAL=? AND C.CODEMP=? AND O.CODCONV=C.CODCONV AND" +
						" O.CODFILIALCV=C.CODFILIAL AND O.CODEMPCV=C.CODEMP AND O.STATUSORC IN ('OL','FP') ";

				bConv = true;
			}
			else if (iCod == -1) {
				mensagemErro = "Número do orçamento inválido!";
				vector = null;
				throw new ExceptionCarregaDados(mensagemErro);
			}

		}

		try {

			sql = "SELECT O.CODORC," + ( bConv ? "O.CODCONV,C.NOMECONV," : "O.CODCLI,C.NOMECLI," ) 
					+ "(SELECT COUNT(IT.CODITORC) FROM VDITORCAMENTO IT WHERE IT.CODORC=O.CODORC "
					+ "AND IT.CODFILIAL=O.CODFILIAL AND IT.CODEMP=O.CODEMP),"
					+ "(SELECT COUNT(IT.CODITORC) FROM VDITORCAMENTO IT WHERE IT.CODORC=O.CODORC " 
					+ "AND IT.CODFILIAL=O.CODFILIAL AND IT.CODEMP=O.CODEMP " 
					+ "AND IT.ACEITEITORC='S' AND IT.APROVITORC='S'),"
					+ "(SELECT SUM(IT.VLRLIQITORC) FROM VDITORCAMENTO IT WHERE IT.CODORC=O.CODORC "
					+ "AND IT.CODFILIAL=O.CODFILIAL AND IT.CODEMP=O.CODEMP),"
					+ "(SELECT SUM(IT.VLRLIQITORC) FROM VDITORCAMENTO IT WHERE IT.CODORC=O.CODORC " 
					+ "AND IT.CODFILIAL=O.CODFILIAL AND IT.CODEMP=O.CODEMP "
					+ "AND IT.ACEITEITORC='S' AND IT.APROVITORC='S'), O.STATUSORC, COALESCE(O.OBSORC,'') OBSORC " 
					+ "FROM VDORCAMENTO O" 
					+ sWhere + " ORDER BY O.CODORC";

			ps = getConn().prepareStatement( sql );
			ps.setInt( 1, iCod );
			ps.setInt( 2, ListaCampos.getMasterFilial( bOrc ? "VDORCAMENTO" : ( bConv ? "ATCONVENIADO" : "VDCLIENTE" ) ) );
			ps.setInt( 3, Aplicativo.iCodEmp );
			rs = ps.executeQuery();
			vector =  new Vector<Vector<Object>>();

			while (rs.next()) {
				if (rs.getString(8).equals("OL") || rs.getString(8).equals("OP") || rs.getString(8).equals("FP")) {

					vVals = new Vector<Object>();
					vVals.addElement( new Boolean( true ) );
					vVals.addElement( new Integer( rs.getInt( "CodOrc" ) ) );
					vVals.addElement( new Integer( rs.getInt( 2 ) ) );
					vVals.addElement( rs.getString( 3 ).trim() );
					vVals.addElement( new Integer( rs.getInt( 4 ) ) );
					vVals.addElement( new Integer( rs.getInt( 5 ) ) );
					vVals.addElement( Funcoes.strDecimalToStrCurrencyd( 2, rs.getString( 6 ) != null ? rs.getString( 6 ) : "0" ) );
					vVals.addElement( Funcoes.strDecimalToStrCurrencyd( 2, rs.getString( 7 ) != null ? rs.getString( 7 ) : "0" ) );
					vVals.addElement( rs.getString( "OBSORC" ) );
					vector.add(vVals);

				}
				else {
					mensagemErro =  "ORÇAMENTO NÃO ESTÁ LIBERADO!";
					vector = null;
					throw new ExceptionCarregaDados(mensagemErro);
				}
			}

			rs.close();
			ps.close();
		} catch ( SQLException err ) {
			mensagemErro = "Erro ao buscar orçamentos!\n";
			vector = null;
			throw new ExceptionCarregaDados(mensagemErro);
		}

		ps = null;
		rs = null;
		sql = null;
		sWhere = null;
		vVals = null;

		return vector;
	}


	public Vector<Vector<Object>>  carregar( Vector<Vector<Object>> tabOrc, boolean aprovorcfatparc, String origem ) throws SQLException {

		Vector<Object> vVals = null;
		Vector<Vector<Object>> vector = new Vector<Vector<Object>>();

		Vector<String> vcodorcs = new Vector<String>();
		Vector<Vector<String>> vorcs = new Vector<Vector<String>>();
		vorcs.add( vcodorcs );

		int count = 0;

		for (int i = 0; i < tabOrc.size(); i++) {

			if (!((Boolean) tabOrc.elementAt(i).get(0)).booleanValue()) {
				continue;
			}

			vcodorcs.add(String.valueOf(tabOrc.elementAt(i).get(1)));
			count++;

			if (count == 1000) {
				vcodorcs = new Vector<String>();
				vorcs.add(vcodorcs);
				count = 0;
			}
		}


		for (Vector<String> v : vorcs) {

			String scodorcs = "";

			for (int i = 0; i < v.size(); i++) {
				if (scodorcs.length() > 0) {
					scodorcs += ",";
				}
				scodorcs += v.get( i );
			}

			StringBuilder sql = new StringBuilder();

			sql.append( "SELECT IT.CODORC,IT.CODITORC,IT.CODPROD,P.DESCPROD," );
			sql.append( "IT.QTDITORC,IT.QTDFATITORC,IT.QTDAFATITORC,IT.PRECOITORC,IT.VLRDESCITORC,IT.VLRLIQITORC," );
			sql.append( "IT.VLRPRODITORC, P.CLOTEPROD, IT.CODLOTE, coalesce(ip.qtdfinalproditorc,0) qtdfinalproditorc, ip.codop, it.codalmox ");

			sql.append( "FROM EQPRODUTO P, VDORCAMENTO O, VDITORCAMENTO IT  " );
			sql.append( "LEFT OUTER JOIN PPOPITORC IP ON IP.CODEMPOC=IT.CODEMP AND IP.CODFILIALOC=IT.CODFILIAL AND IP.TIPOORC=IT.TIPOORC AND IP.CODORC=IT.CODORC AND IP.CODITORC=IT.CODITORC ");

			sql.append( "WHERE O.CODEMP=IT.CODEMP AND O.CODFILIAL=IT.CODFILIAL AND O.TIPOORC=IT.TIPOORC AND O.CODORC=IT.CODORC AND ");
			sql.append( "P.CODPROD=IT.CODPROD AND P.CODFILIAL=IT.CODFILIALPD AND " );
			sql.append( "P.CODEMP=IT.CODEMPPD AND ");
			sql.append( "((IT.ACEITEITORC='S' AND IT.FATITORC IN ('N','P') AND IT.APROVITORC='S' AND IT.SITPRODITORC='NP') OR ");
			sql.append( "(IT.SITPRODITORC='PD' AND IT.APROVITORC='S' AND IT.FATITORC IN ('N','P') )) ");
			if (aprovorcfatparc) {
				sql.append( " AND O.STATUSORC NOT IN ('OV','FP') " ); 
			}
			sql.append( " AND IT.CODEMP=? AND IT.CODFILIAL=? AND IT.CODORC IN " );
			sql.append( "(" + scodorcs + ") " );

			//Caso a origem for a tela de Contrato busca apenas produtos com o tipo Serviço.
			if ("Contrato".equals( origem )) {
				sql.append( " AND P.TIPOPROD = 'S' " );
			}


			sql.append( " ORDER BY IT.CODORC,IT.CODITORC " );

			PreparedStatement ps = getConn().prepareStatement( sql.toString() );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "VDORCAMENTO" ) );
			ResultSet rs = ps.executeQuery();

			while ( rs.next() ) {
				// vVals = new Vector<Object>();
				
				vVals = new Vector<Object>();
				vVals.addElement( new Boolean( true ));
				vVals.addElement( new Integer( rs.getInt( "CodItOrc" )));
				vVals.addElement( new Integer( rs.getInt( "CodProd" )));
				vVals.addElement( rs.getString( "DescProd" ).trim());
				vVals.addElement( Funcoes.strDecimalToStrCurrencyd( Aplicativo.casasDec, rs.getString( DLBuscaOrc.GRID_ITENS.QTDITORC.toString() ) != null ? rs.getString( DLBuscaOrc.GRID_ITENS.QTDITORC.toString() ) : "0" ));
				vVals.addElement( Funcoes.strDecimalToStrCurrencyd( Aplicativo.casasDec, rs.getString( DLBuscaOrc.GRID_ITENS.QTDAFATITORC.toString()) != null ? rs.getString(DLBuscaOrc.GRID_ITENS.QTDAFATITORC.toString() ) : "0" ));
				vVals.addElement( Funcoes.strDecimalToStrCurrencyd( Aplicativo.casasDec, rs.getString( DLBuscaOrc.GRID_ITENS.QTDFATITORC.toString() ) != null ? rs.getString( DLBuscaOrc.GRID_ITENS.QTDFATITORC.toString() ) : "0" ));
				vVals.addElement( Funcoes.strDecimalToStrCurrencyd( Aplicativo.casasDec, rs.getString( DLBuscaOrc.GRID_ITENS.QTDFINALPRODITORC.toString() ) != null ? rs.getString( DLBuscaOrc.GRID_ITENS.QTDFINALPRODITORC.toString() ) : "0" ));
				vVals.addElement( Funcoes.strDecimalToStrCurrencyd( Aplicativo.casasDecPre, rs.getString( "PrecoItOrc" ) != null ? rs.getString( "PrecoItOrc" ) : "0" ));
				vVals.addElement( Funcoes.strDecimalToStrCurrencyd( Aplicativo.casasDecPre, rs.getString( "VlrDescItOrc" ) != null ? rs.getString( "VlrDescItOrc" ) : "0" ));
				vVals.addElement( Funcoes.strDecimalToStrCurrencyd( Aplicativo.casasDecPre, rs.getString( "VlrLiqItOrc" ) != null ? rs.getString( "VlrLiqItOrc" ) : "0" ));
				vVals.addElement( "");
				vVals.addElement( "");
				vVals.addElement( "0,00");
				vVals.addElement( rs.getInt( "CodOrc" ));
				// 	private enum GRID_ITENS { SEL, CODITORC, CODPROD, DESCPROD, QTD, QTDAFAT, QTDFAT, QTD_PROD, PRECO, DESC, VLRLIQ, TPAGR, PAI, VLRAGRP, CODORC, USALOTE, CODLOTE };	
				vVals.addElement( rs.getString( "CLOTEPROD" ));
				vVals.addElement( rs.getString( "CODLOTE" ) == null ? "" : rs.getString( "CODLOTE" ));
				vVals.addElement( rs.getString( "CODALMOX" ) == null ? "" : rs.getString( "CODALMOX" ));
				vVals.addElement( rs.getString( "CODOP" ) == null ? "" : rs.getString( "CODOP" ));

				vValidos.addElement( new int[] { rs.getInt( "CodOrc" ), rs.getInt( "CodItOrc" ) } );
				
				vector.add( vVals );
			}

			getConn().commit();
		}

		return vector;
	}


	public Map<String, Boolean> getPrefs() throws SQLException {

		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuilder sql = null;
		Map<String, Boolean> retorno = new HashMap<String, Boolean>();

		sql = new StringBuilder("SELECT P1.USAPEDSEQ, P4.AUTOFECHAVENDA, P1.ADICORCOBSPED, P1.ADICOBSORCPED, P1.FATORCPARC, P1.APROVORCFATPARC, P1.SOLDTSAIDA " );
		sql.append(  "FROM SGPREFERE1 P1, SGPREFERE4 P4 " );
		sql.append( "WHERE P1.CODEMP=? AND P1.CODFILIAL=? " );
		sql.append( "AND P4.CODEMP=P1.CODEMP AND P4.CODFILIAL=P4.CODFILIAL");

		ps = getConn().prepareStatement( sql.toString() );
		ps.setInt( 1, Aplicativo.iCodEmp );
		ps.setInt( 2, ListaCampos.getMasterFilial( "SGPREFERE1" ) );
		rs = ps.executeQuery();

		if ( rs.next() ) { 

			retorno.put( DLBuscaOrc.COL_PREFS.USAPEDSEQ.name(), "S".equals( rs.getString( DLBuscaOrc.COL_PREFS.USAPEDSEQ.name())));
			retorno.put( DLBuscaOrc.COL_PREFS.AUTOFECHAVENDA.name(), "S".equals( rs.getString( DLBuscaOrc.COL_PREFS.AUTOFECHAVENDA.name())));
			retorno.put( DLBuscaOrc.COL_PREFS.ADICORCOBSPED.name(), "S".equals( rs.getString( DLBuscaOrc.COL_PREFS.ADICORCOBSPED.name())));
			retorno.put( DLBuscaOrc.COL_PREFS.ADICOBSORCPED.name(), "S".equals( rs.getString( DLBuscaOrc.COL_PREFS.ADICOBSORCPED.name())));
			retorno.put( DLBuscaOrc.COL_PREFS.FATORCPARC.name(), "S".equals( rs.getString( DLBuscaOrc.COL_PREFS.FATORCPARC.name())));
			retorno.put( DLBuscaOrc.COL_PREFS.APROVORCFATPARC.name(), "S".equals( rs.getString( DLBuscaOrc.COL_PREFS.APROVORCFATPARC.name())));
			retorno.put( DLBuscaOrc.COL_PREFS.SOLDTSAIDA.name(), "S".equals( rs.getString( DLBuscaOrc.COL_PREFS.SOLDTSAIDA.name())));

		}

		rs.close();
		ps.close();

		return retorno;
	}



	public Vector<Object> getvValidos() {

		return vValidos;
	}


	private String getString( String value ){
		String result = null;

		if (value == null)
			result = "";
		else
			result = value;

		return result;
	}	


	private Integer getInteger( Integer value ) {
		Integer result = null;

		if (value == null)
			result = new Integer( 0 );
		else
			result = value;

		return result;
	}


	private BigDecimal getBigDecimal( BigDecimal value ) {
		BigDecimal result = null;

		if (value == null)
			result = BigDecimal.ZERO;
		else
			result = value;

		return result;
	}

}
