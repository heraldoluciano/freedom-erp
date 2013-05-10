package org.freedom.modulos.std.dao;

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
import org.freedom.library.swing.frame.Aplicativo;
import org.freedom.modulos.std.view.dialog.utility.DLBuscaOrc;

public class DAOBuscaOrc extends AbstractDAO {

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
