package org.freedom.modulos.std.dao;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.swing.JOptionPane;

import org.freedom.infra.dao.AbstractDAO;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.business.exceptions.ExceptionCarregaDados;
import org.freedom.library.functions.Funcoes;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.frame.Aplicativo;
import org.freedom.modulos.gms.business.object.TipoMov;
import org.freedom.modulos.std.business.object.VDContrOrc;
import org.freedom.modulos.std.business.object.VDContrato;
import org.freedom.modulos.std.business.object.VDItContrato;
import org.freedom.modulos.std.view.dialog.utility.DLBuscaOrc;

public class DAOBuscaOrc extends AbstractDAO {


	private Vector<Object> vValidos = new Vector<Object>();

	private Map<String, Object> prefs = null;

	public enum COL_PREFS { USAPEDSEQ, AUTOFECHAVENDA, ADICORCOBSPED, ADICOBSORCPED, FATORCPARC, APROVORCFATPARC, SOLDTSAIDA, BLOQVDPORATRASO, NUMDIASBLOQVD };

	public DAOBuscaOrc( DbConnection connection ) {

		super( connection );

	}
	
	public DAOBuscaOrc( Integer codemp, Integer codfilial, DbConnection connection) {
		this( connection );
		setCodemp( codemp );
		setCodfilial( codfilial );
	}
	
	public void commit() throws SQLException {
		getConn().commit();
	}

	public int getCodcli(int codorc, int codemp, int codfilial) throws ExceptionCarregaDados {
		int result = -1;
		StringBuilder sql = new StringBuilder();
		sql.append("select codcli from vdorcamento where codemp=? and codfilial=? and tipoorc='O' and codorc=?");
		try {
			PreparedStatement ps = getConn().prepareStatement( sql.toString() );
			int param = 1;
			ps.setInt( param++, codemp );
			ps.setInt( param++, codfilial );
			ps.setInt( param++, codorc );
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				result = rs.getInt("codcli");
			}
			rs.close();
			rs.close();
			getConn().commit();
		} catch (SQLException err) {
			try {
				getConn().rollback();
			} catch (SQLException err2) {
				err2.printStackTrace();
			}
			throw new ExceptionCarregaDados( err.getMessage() );
		}
		return result;
	}
	
	public void insertVDContrOrc(VDContrOrc contrOrc) throws SQLException {
		
		
		PreparedStatement ps = null;
		StringBuilder sql = new StringBuilder();
		sql.append("insert into vdcontrorc (codemp, codfilial, codcontr, coditcontr, ");
		sql.append("codempor, codfilialor, tipoorc, codorc, coditorc ");
		sql.append(") values (?, ?, ?, ?, ?, ?, ?, ?, ?)");
		
		int param = 1;
		
		ps = getConn().prepareStatement( sql.toString() );
		ps.setInt( param++, contrOrc.getCodemp() );
		ps.setInt( param++, contrOrc.getCodFilial() );
		ps.setInt( param++, contrOrc.getCodContr() );
		ps.setInt( param++, contrOrc.getCodItContr() );
		ps.setInt( param++, contrOrc.getCodEmpOr() );
		ps.setInt( param++, contrOrc.getCodFilialOr() );
		ps.setString( param++, contrOrc.getTipoOrc() );
		ps.setInt( param++, contrOrc.getCodOrc() );
		ps.setInt( param++, contrOrc.getCodItOrc() );
		
		ps.execute();
		
	}
	
	
	public void insertVDContrato( VDContrato contrato ) throws SQLException {
		
		PreparedStatement ps = null;
		StringBuilder sql = new StringBuilder();
		sql.append("insert into vdcontrato (codemp, codfilial, codcontr, desccontr, codempcl, codfilialcl, codcli, dtinicio, dtfim, tpcobcontr, diavenccontr,");
		sql.append( "diafechcontr, indexcontr, tpcontr, dtprevfin, ativo ");
		sql.append(") values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
		
		int param = 1;
		ps = getConn().prepareStatement( sql.toString() );
		
		ps.setInt( param++, contrato.getCodEmp() );
		ps.setInt( param++, contrato.getCodFilial() );
		ps.setInt( param++, contrato.getCodContr() );
		ps.setString( param++, contrato.getDescContr() );
		ps.setInt( param++, contrato.getCodEmpCl() );
		ps.setInt( param++, contrato.getCodFilialCl() );
		ps.setInt( param++, contrato.getCodCli() );
		ps.setDate( param++, Funcoes.dateToSQLDate(contrato.getDtInicio()));
		ps.setDate( param++, Funcoes.dateToSQLDate(contrato.getDtFim()));
		ps.setString( param++, contrato.getTpCobContr() );
		ps.setInt( param++, contrato.getDiaVencContr() );
		ps.setInt( param++, contrato.getDiaFechContr() );
		ps.setInt( param++, contrato.getIndexContr() );
		ps.setString( param++, contrato.getTpcontr() );
		ps.setDate( param++, Funcoes.dateToSQLDate(contrato.getDtPrevFin()));
		ps.setString( param++, contrato.getAtivo() );
		
		ps.execute();
	}
	
	
	public void insertVDItContrato(VDItContrato itemContrato) throws SQLException {
		
		StringBuilder sql = new StringBuilder();
		
		sql.append( "insert into vditcontrato (codemp, codfilial, codcontr, coditcontr, descitcontr, codemppd, codfilialpd, codprod, qtditcontr, vlritcontr, "); 
		sql.append("codemppe, codfilialpe, codprodpe, vlritcontrexced, indexitcontr, acumuloitcontr, franquiaitcontr ");
		sql.append( ") values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,  ?, ?, ?) ");
		
		int param = 1;
		PreparedStatement ps = getConn().prepareStatement( sql.toString() );

		ps.setInt( param++, itemContrato.getCodEmp());
		ps.setInt( param++, itemContrato.getCodFilial());
		ps.setInt( param++, itemContrato.getCodContr());
		ps.setInt( param++, itemContrato.getCodItContr());
		ps.setString( param++, itemContrato.getDescItContr());
		ps.setInt( param++, itemContrato.getCodEmpPd());
		ps.setInt( param++, itemContrato.getCodFilialPd());
		ps.setInt( param++, itemContrato.getCodProd());
		ps.setBigDecimal( param++, itemContrato.getQtdItContr());
		ps.setBigDecimal( param++, itemContrato.getVlrItContr());
		ps.setInt( param++, itemContrato.getCodEmpPe());
		ps.setInt( param++, itemContrato.getCodFilialPe());
		ps.setInt( param++, itemContrato.getCodProdPe());
		ps.setBigDecimal( param++, itemContrato.getVlrItContrRexCed());
		ps.setInt( param++, itemContrato.getIndexItContr());
		ps.setInt( param++, itemContrato.getAcumuloItContr());
		ps.setString( param++, itemContrato.getFranquiaItContr());
		
		
		ps.execute();
	}

	
	public int getMaxCodContr(Integer codemp, Integer codfilial) throws SQLException {

		PreparedStatement ps = null;
		String sql = " select max(ct.codcontr) codcontr from vdcontrato ct where ct.codemp=? and ct.codfilial=?";
		int param = 1;
		ps = getConn().prepareStatement( sql.toString() );
		int codcontr = 0;
		
		
		ps.setInt( param++, codemp);
		ps.setInt( param++, codfilial);
		
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			codcontr = rs.getInt("codcontr") + 1;
		}
		
		return codcontr;
	}
	
	
	public void atualizaLoteItVenda( String codlote, int irow, int codorc, int coditorc ) throws SQLException {
		PreparedStatement ps = null;
		StringBuilder sql = new StringBuilder();
		int param = 1;
		sql.append( "update vditorcamento set codemple=?, codfilialle=?, codlote=? " );
		sql.append( "where codemp=? and codfilial=? and codorc=? and coditorc=?" );
		ps = getConn().prepareStatement( sql.toString() );
		ps.setInt( param++, Aplicativo.iCodEmp);
		ps.setInt( param++, ListaCampos.getMasterFilial("EQLOTE"));
		ps.setString( param++, codlote);
		ps.setInt( param++, Aplicativo.iCodEmp);
		ps.setInt( param++, ListaCampos.getMasterFilial("VDORCAMENTO"));
		ps.setInt( param++, (Integer) codorc);
		ps.setInt( param++, (Integer) coditorc);
		ps.execute();
		getConn().commit();
	}
	
	public int executaVDAdicVendaORCSP(Integer codemp,  Integer codfilialoc, Integer codorc
			, Integer codfilialvd, String tipovenda, Integer codvenda, Date datasaida) throws SQLException {
		StringBuilder sql = new StringBuilder();
		StringBuilder sqlseq = new StringBuilder();

		int icodvenda = 0;
		int param = 1;
		Boolean pedseq = (Boolean) getPrefs().get(COL_PREFS.USAPEDSEQ.name());
		if (pedseq) {
			sqlseq.append( "select iseq from spgeranum(?, ?, ?)");
			PreparedStatement psseq = getConn().prepareStatement( sqlseq.toString() );
			psseq.setInt( param++, codemp );
			psseq.setInt( param++, codfilialvd );
			psseq.setString( param++, "VD" );
			ResultSet rsseq = psseq.executeQuery();
			if (rsseq.next()) {
				codvenda = rsseq.getInt( "iseq" );
			}
			rsseq.close();
			psseq.close();
			getConn().commit();
		} else {
			if (codvenda==null || codvenda.intValue()==0) {
				sqlseq.append( "select max(codvenda)+1 codvenda from vdvenda where codemp=? and codfilial=? and tipovenda=?");
				PreparedStatement psseq = getConn().prepareStatement( sqlseq.toString() );
				psseq.setInt( param++, codemp );
				psseq.setInt( param++, codfilialvd );
				psseq.setString( param++, "VD" );
				ResultSet rsseq = psseq.executeQuery();
				if (rsseq.next()) {
					codvenda = rsseq.getInt( "codvenda" );
				} 
				rsseq.close();
				psseq.close();
				getConn().commit();
				if (codvenda==null || codvenda.intValue()==0 ) {
					codvenda = 1;
				}
			}
		}
		param = 1;
		sql.append( "select iret from vdadicvendaorcsp(?,?,?,?,?,?,?)" );
		PreparedStatement ps = getConn().prepareStatement( sql.toString() );
		ps.setInt( param++, codemp );
		ps.setInt( param++, codfilialoc );
		ps.setInt( param++, codorc);
		ps.setInt( param++, codfilialvd );
		ps.setString( param++, tipovenda );
		ps.setInt( param++, codvenda);
		ps.setDate( param++, Funcoes.dateToSQLDate( datasaida == null ? new Date() : datasaida ));
		ResultSet rs = ps.executeQuery();
		if ( rs.next() )
			icodvenda = rs.getInt( "iret" );
		rs.close();
		ps.close();
		return icodvenda;
	}

	
	public void executaVDAdicItVendaORCSP(Integer codfilial, Integer codvenda, Integer codorc, Integer coditorc, Integer codfilialoc, Integer codempoc, 
			String tipovenda, String tpagr, BigDecimal qtdprod, BigDecimal qtdafatitorc, BigDecimal desc) throws SQLException {

		String sql = "EXECUTE PROCEDURE VDADICITVENDAORCSP(?,?,?,?,?,?,?,?,?,?)";
		int param = 1;

		PreparedStatement ps = getConn().prepareStatement( sql );

		ps.setInt( param++, codfilial);
		ps.setInt( param++, codvenda );
		ps.setInt( param++, codorc);
		ps.setInt( param++, coditorc );
		ps.setInt( param++, codfilialoc);
		ps.setInt( param++, codempoc);

		ps.setString( param++, tipovenda );
		ps.setString( param++, tpagr);

		// Verificação dos excessos de produção

		if( qtdprod.compareTo( qtdafatitorc ) > 0 && 
				( Funcoes.mensagemConfirma( null,  

						"A quantidade produzida do ítem \n" + desc.toString().trim() + " \n" +
								"excede a quantidade solicitada pelo cliente.\n" +
								"Deseja faturar a quantidade produzida?\n\n" +
								"Quantidade solicitada: " + Funcoes.bdToStrd( qtdafatitorc ) + "\n" +
								"Quantidade produzida : " + Funcoes.bdToStrd( qtdprod ) + "\n\n"

						) == JOptionPane.YES_OPTION ) ) {

			ps.setBigDecimal( param++, qtdprod );

		}
		else {
			ps.setBigDecimal( param++, qtdafatitorc );	
		}


		ps.setBigDecimal( param++, desc );

		ps.execute();
		ps.close();
	}


	public void executaVDAtuDescVendaORCSP(Integer codemp, Integer codfilial, String tipovenda, Integer codvenda) throws SQLException {
		String sql = null;
		int param = 1;

		// Atualiza o desconto na venda de acordo com o desconto dado no orçamento.
		sql = "EXECUTE PROCEDURE VDATUDESCVENDAORCSP(?,?,?,?)";
		PreparedStatement ps = getConn().prepareStatement( sql );
		ps.setInt( param++, codemp);
		ps.setInt( param++, codfilial);
		ps.setString( param++, tipovenda);
		ps.setInt( param++, codvenda);

		ps.execute();
		ps.close();
	}


	public void atualizaObsPed( final StringBuffer obs, final int iCodVenda ) throws SQLException {

		String sql = null;
		int param = 1;

		sql = "UPDATE VDVENDA SET OBSVENDA=? WHERE " + "CODEMP=? AND CODFILIAL=? AND CODVENDA=?";

		PreparedStatement ps = getConn().prepareStatement( sql );
		String obsupdate = obs.toString().replace( "\n", " - " );

		ps.setString( param++, obsupdate.length() > 10000 ? obsupdate.substring( 0, 10000 ) : obsupdate );
		ps.setInt( param++, Aplicativo.iCodEmp );
		ps.setInt( param++, Aplicativo.iCodFilial );
		ps.setInt( param++, iCodVenda );

		ps.execute();


	}


	public String testaPgto(String tipomov, int codcli, int codempcl, int codfilialcl ) throws Exception {
		PreparedStatement ps = null;
		ResultSet rs = null;
		String result = "";
		try {
			// Se for devolução não deve verificar parcelas em aberto...
			if ( ( ! TipoMov.TM_DEVOLUCAO_VENDA.getValue().equals( tipomov ) ) && ( ! TipoMov.TM_DEVOLUCAO_REMESSA.getValue().equals( tipomov ) ) ) {
				StringBuilder sql = new StringBuilder( "select retorno from fnchecapgtosp(?,?,?,?,?)" );
				int numdiasbloqvd = (Integer) getPrefs().get( COL_PREFS.NUMDIASBLOQVD.name() );
				String bloqvdporatraso = (String) getPrefs().get( COL_PREFS.BLOQVDPORATRASO.name() );
				int param = 1;
				ps = getConn().prepareStatement( sql.toString() );
				ps.setInt( param++, codcli );
				ps.setInt( param++, codempcl );
				ps.setInt( param++, codfilialcl );
				ps.setString( param++, bloqvdporatraso );
				ps.setInt( param++, numdiasbloqvd );
				rs = ps.executeQuery();
				if ( rs.next() ) {
					result = rs.getString( "RETORNO" );
					if (result==null) {
						result = "";
					} else {
						result = result.trim();
						// Caso o retorno seja S (Pagamentos OK).
						if ("S".equals( result )) {
							result = ""; // Retornar condição em branco para evitar mensagem desenecessária
						}
					}
				}
				else {
					throw new Exception( "Não foi possível checar os pagamentos do cliente !" );
				}
				rs.close();
				ps.close();
				getConn().commit();
				if ( ( !"".equals( result ) ) && ( "N".equals( result.substring( 0, 1 ) ) ) ) {
					if (result.length()>2) {
						int numreg = Integer.parseInt(result.substring(2));
						if (numreg>0) {
							result = "Cliente possui "+numreg+" parcela(s) em atraso !!!";
						}
					}
				}
			}
		} catch ( SQLException err ) {
			String mensagemErr = err.getMessage();
			err.printStackTrace();
			try {
				getConn().rollback();
			} catch (SQLException err2) {
				err2.printStackTrace();
			}
			throw new Exception( "Não foi possível verificar os pagamentos do cliente !\n"+mensagemErr );
		}
		return result;
	}

	public Vector<Vector<Object>> buscar(Integer codorc, Integer codcli, Integer codconv, String busca, boolean proj) throws ExceptionCarregaDados{
		PreparedStatement ps = null;
		ResultSet rs = null;
		String mensagemErro = "";
		StringBuilder sql = new StringBuilder();
		StringBuilder where = new StringBuilder();
		Vector<Object> vVals = null;
		boolean orc = false;
		boolean conv = false;
		Vector<Vector<Object>> vector = null;
		if (codorc.intValue() > 0) {
			where.delete( 0, where.length() );
			where.append(", vdcliente c where o.codorc = ? and o.codfilial = ? and o.codemp = ? and c.codemp=o.codempcl and c.codfilial=o.codfilialcl and c.codcli=o.codcli ");
			orc = true;
		}
		else {
			if (busca.equals("L") &&  codcli > 0) {
				if (codcli.intValue() == 0) {
					mensagemErro = "Código do cliente inválido!";
					vector = null;
					throw new ExceptionCarregaDados(mensagemErro);
				}
				where.delete( 0, where.length() );
				where.append(", vdcliente c where c.codcli=? and c.codfilial=? and c.codemp=? and o.codcli=c.codcli ");
				where.append("and o.codfilialcl=c.codfilial and o.codempcl=c.codemp and o.statusorc in ('OL','FP','OP') ");

			}
			else if (busca.equals( "O" ) && codconv > 0) {
				if (codconv.intValue() == 0) {
					mensagemErro = "Código do conveniado inválido!";
					vector = null;
					throw new ExceptionCarregaDados(mensagemErro);
				}
				where.delete( 0, where.length() );
				where.append(", atconveniado c where c.codconv=? and c.codfilial=? and c.codemp=? and o.codconv=c.codconv and ");
				where.append(" o.codfilialcv=c.codfilial and o.codempcv=c.codemp and o.statusorc in ('OL','FP') ");

				orc = true;
			}
			else if (codorc.intValue() == -1) {
				mensagemErro = "Número do orçamento inválido!";
				vector = null;
				throw new ExceptionCarregaDados(mensagemErro);
			}

		}
		try {
			sql.append( "select o.codorc," + ( conv ? "o.codconv,c.nomeconv," : "o.codcli,c.nomecli," ));
			sql.append( "(select count(it.coditorc) from vditorcamento it where it.codorc=o.codorc ");
			sql.append( "and it.codfilial=o.codfilial and it.codemp=o.codemp)," );
			sql.append( "(select count(it.coditorc) from vditorcamento it where it.codorc=o.codorc " ); 
			sql.append( "and it.codfilial=o.codfilial and it.codemp=o.codemp " ); 
			sql.append( "and it.aceiteitorc='S' and it.aprovitorc='S')," );
			sql.append( "(select sum(it.vlrliqitorc) from vditorcamento it where it.codorc=o.codorc " );
			sql.append( "and it.codfilial=o.codfilial and it.codemp=o.codemp)," );
			sql.append( "(select sum(it.vlrliqitorc) from vditorcamento it where it.codorc=o.codorc " ); 
			sql.append( "and it.codfilial=o.codfilial and it.codemp=o.codemp " );
			sql.append( "and it.aceiteitorc='S' and it.aprovitorc='S'), o.statusorc, coalesce(o.obsorc,'') obsorc " ); 
			sql.append( "from vdorcamento o"  );
			sql.append( where );
			sql.append( " order by o.codorc" );
			ps = getConn().prepareStatement( sql.toString() );
			int param = 1;
			ps.setInt( param++, codorc );
			ps.setInt( param++, ListaCampos.getMasterFilial( orc ? "VDORCAMENTO" : ( conv ? "ATCONVENIADO" : "VDCLIENTE" ) ) );
			ps.setInt( param++, Aplicativo.iCodEmp );
			rs = ps.executeQuery();
			vector =  new Vector<Vector<Object>>();
			while (rs.next()) {
				String statusorc=rs.getString( "statusorc" );
				if ("OL".equals( statusorc ) || "OP".equals( statusorc ) || "FP".equals( statusorc ) || (proj && "OV".equals( statusorc ))) {
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
		where = null;
		vVals = null;
		return vector;
	}


	public Vector<Vector<Object>>  carregar( Integer codfilialax
			, Vector<Vector<Object>> tabOrc, boolean aprovorcfatparc, String origem ) throws SQLException {
		boolean proj = "Contrato".equalsIgnoreCase( origem );
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
			sql.append( "select it.codorc,it.coditorc,it.codprod,p.descprod," );
			sql.append( "it.qtditorc,it.qtdfatitorc,it.qtdafatitorc,it.precoitorc,it.vlrdescitorc,it.vlrliqitorc," );
			sql.append( "it.vlrproditorc, p.cloteprod, it.codlote, coalesce(ip.qtdfinalproditorc,0) qtdfinalproditorc, ip.codop, it.codalmox ");
			sql.append( "from eqproduto p, vdorcamento o, vditorcamento it  " );
			sql.append( "left outer join ppopitorc ip on ip.codempoc=it.codemp and ip.codfilialoc=it.codfilial and ip.tipoorc=it.tipoorc and ip.codorc=it.codorc and ip.coditorc=it.coditorc ");
			sql.append( "where o.codemp=it.codemp and o.codfilial=it.codfilial and o.tipoorc=it.tipoorc and o.codorc=it.codorc and ");
			sql.append( "p.codprod=it.codprod and p.codfilial=it.codfilialpd and " );
			sql.append( "p.codemp=it.codemppd and ");
			sql.append( "((it.aceiteitorc='S' ");
			if (!proj) {
				sql.append( "and it.fatitorc in ('N','P') ");
			}
			sql.append( "and it.aprovitorc='S' and it.sitproditorc='NP') or ");
			sql.append( "(it.sitproditorc='PD' and it.aprovitorc='S' and it.fatitorc in ('N','P') )) ");
			if ( aprovorcfatparc && !proj ) {
				sql.append( " and o.statusorc not in ('OV','FP') " ); 
			}
			sql.append( " and it.codemp=? and it.codfilial=? and it.codorc in " );
			sql.append( "(" + scodorcs + ") " );
			//Caso a origem for a tela de Contrato busca apenas produtos com o tipo Serviço.
			if ( proj ) {
				sql.append( " and p.tipoprod = 'S' " );
				sql.append( " and not exists( select * from vdcontrorc co where co.codempor=it.codemp and co.codfilialor=it.codfilial and co.tipoorc=it.tipoorc ");
				sql.append( " and co.codorc=it.codorc and co.coditorc=it.coditorc) " );
			}
			sql.append( " order by it.codorc,it.coditorc " );
			PreparedStatement ps = getConn().prepareStatement( sql.toString() );
			int param = 1;
			ps.setInt( param++, getCodemp() );
			ps.setInt( param++, getCodfilial() );
			ResultSet rs = ps.executeQuery();
			while ( rs.next() ) {
				// vVals = new Vector<Object>();
				boolean completasaldo = false;
				BigDecimal qtditorc = rs.getBigDecimal( DLBuscaOrc.GRID_ITENS.QTDITORC.toString() );
				if (qtditorc==null) {
					qtditorc = BigDecimal.ZERO;
				}
				BigDecimal qtdafatitorc = rs.getBigDecimal( DLBuscaOrc.GRID_ITENS.QTDAFATITORC.toString() );
				if (qtdafatitorc==null) {
					qtdafatitorc = BigDecimal.ZERO;
				}
				BigDecimal qtdfatitorc =  rs.getBigDecimal( DLBuscaOrc.GRID_ITENS.QTDFATITORC.toString() );
				if (qtdfatitorc==null) {
					qtdfatitorc = BigDecimal.ZERO;
				}
				BigDecimal qtdfinalproditorc = rs.getBigDecimal( DLBuscaOrc.GRID_ITENS.QTDFINALPRODITORC.toString() );
				if (qtdfinalproditorc==null) {
					qtdfinalproditorc = BigDecimal.ZERO;
				}
				BigDecimal precoitorc = rs.getBigDecimal( "precoitorc" );
				if (precoitorc==null) {
					precoitorc = BigDecimal.ZERO;
				}
				BigDecimal vlrdescitorc =  rs.getBigDecimal( "vlrdescitorc" );
				if (vlrdescitorc==null) {
					vlrdescitorc = BigDecimal.ZERO;
				}
				BigDecimal vlrliqitorc = rs.getBigDecimal( "vlrliqitorc" );
				if (vlrliqitorc==null) {
					vlrliqitorc = BigDecimal.ZERO;
				}
				String cloteprod = rs.getString( "cloteprod" );
				Integer codorc = new Integer( rs.getInt("codorc") );
				Integer coditorc = new Integer( rs.getInt( "coditorc" ));
				Integer codprod = new Integer( rs.getInt( "codprod" ));
				String descprod = rs.getString( "descprod" ).trim();
				String codop = rs.getString( "codop" );
				if (codop==null) {
					codop = "";
				}
				Vector<SaldoProd> listsaldo = getSaldoProd( "S".equals( cloteprod )
						, codfilialax, codprod, qtditorc
						, precoitorc, vlrdescitorc, qtdafatitorc );
				for (SaldoProd saldoprod: listsaldo) {
					String codlote = saldoprod.getCodlote();
					if (codlote==null) {
						codlote = "";
					}
					String codalmox = "";
					if (saldoprod.getCodalmox().intValue()>0) {
						codalmox = String.valueOf( saldoprod.getCodalmox() );
					}
					vVals = new Vector<Object>();
					// 	private enum GRID_ITENS { SEL, CODITORC, CODPROD, DESCPROD, QTD, QTDAFAT, QTDFAT, QTD_PROD, PRECO, DESC, VLRLIQ, TPAGR, PAI, VLRAGRP, CODORC, USALOTE, CODLOTE };	
					vVals.addElement( new Boolean( true ));
					vVals.addElement( coditorc );
					vVals.addElement( codprod );
					vVals.addElement( descprod );
					vVals.addElement( Funcoes.strDecimalToStrCurrencyd( Aplicativo.casasDec, String.valueOf( qtditorc) ) );
					vVals.addElement( Funcoes.strDecimalToStrCurrencyd( Aplicativo.casasDec, String.valueOf( saldoprod.getQtdafat() ) ) );
					vVals.addElement( Funcoes.strDecimalToStrCurrencyd( Aplicativo.casasDec, String.valueOf( qtdfatitorc ) ) );
					vVals.addElement( Funcoes.strDecimalToStrCurrencyd( Aplicativo.casasDec,  String.valueOf( qtdfinalproditorc ) ) );
					vVals.addElement( codalmox );
					vVals.addElement( codlote );
					vVals.addElement( Funcoes.strDecimalToStrCurrencyd( Aplicativo.casasDec,  String.valueOf( saldoprod.getSaldoprod() ) ) );
					vVals.addElement( Funcoes.strDecimalToStrCurrencyd( Aplicativo.casasDecPre, String.valueOf( precoitorc ) ) );
					vVals.addElement( Funcoes.strDecimalToStrCurrencyd( Aplicativo.casasDecPre, String.valueOf( saldoprod.getVlrDescCalc() ) ) );
					vVals.addElement( Funcoes.strDecimalToStrCurrencyd( Aplicativo.casasDecPre, String.valueOf( saldoprod.getVlrLiqCalc() ) ) );
					vVals.addElement( "");
					vVals.addElement( "");
					vVals.addElement( "0,00");
					vVals.addElement( codorc);
					vVals.addElement( cloteprod );
					vVals.addElement( codop );
					vValidos.addElement( new int[] { codorc, coditorc } );
					vector.add( vVals );
				}
			}
			getConn().commit();
		}
		return vector;
	}

	public Map<String, Object> getPrefs() throws SQLException {
		Map<String, Object> result = null;
		if (prefs==null) {
			PreparedStatement ps = null;
			ResultSet rs = null;
			StringBuilder sql = null;
			result = new HashMap<String, Object>();
			sql = new StringBuilder("select p1.usapedseq, p4.autofechavenda, p1.adicorcobsped, p1.adicobsorcped, p1.fatorcparc, p1.aprovorcfatparc, p1.soldtsaida " );
			sql.append( ", p1.bloqvdporatraso, p1.numdiasbloqvd ");
			sql.append( "from sgprefere1 p1, sgprefere4 p4 " );
			sql.append( "where p1.codemp=? and p1.codfilial=? " );
			sql.append( "and p4.codemp=p1.codemp and p4.codfilial=p4.codfilial");
			ps = getConn().prepareStatement( sql.toString() );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "SGPREFERE1" ) );
			rs = ps.executeQuery();
			if ( rs.next() ) { 
				result.put( COL_PREFS.USAPEDSEQ.name(), new Boolean("S".equals( rs.getString( COL_PREFS.USAPEDSEQ.name()))));
				result.put( COL_PREFS.AUTOFECHAVENDA.name(), new Boolean("S".equals( rs.getString( COL_PREFS.AUTOFECHAVENDA.name()))));
				result.put( COL_PREFS.ADICORCOBSPED.name(), new Boolean("S".equals( rs.getString( COL_PREFS.ADICORCOBSPED.name()))));
				result.put( COL_PREFS.ADICOBSORCPED.name(), new Boolean("S".equals( rs.getString( COL_PREFS.ADICOBSORCPED.name()))));
				result.put( COL_PREFS.FATORCPARC.name(), new Boolean("S".equals( rs.getString( COL_PREFS.FATORCPARC.name()))));
				result.put( COL_PREFS.APROVORCFATPARC.name(), new Boolean("S".equals( rs.getString( COL_PREFS.APROVORCFATPARC.name()))));
				result.put( COL_PREFS.SOLDTSAIDA.name(), new Boolean("S".equals( rs.getString( COL_PREFS.SOLDTSAIDA.name()))));
				if (rs.getString( COL_PREFS.BLOQVDPORATRASO.name())==null) {
					result.put( COL_PREFS.BLOQVDPORATRASO.name(), "N");
				} else {
					result.put( COL_PREFS.BLOQVDPORATRASO.name(), rs.getString( COL_PREFS.BLOQVDPORATRASO.name()));
				}
				result.put( COL_PREFS.NUMDIASBLOQVD.name(), new Integer(rs.getInt( COL_PREFS.NUMDIASBLOQVD.name())));
	
			}
			rs.close();
			ps.close();
			getConn().commit();
		}
		else {
			result = prefs;
		}
		return result;
	}

	private Vector<SaldoProd> getSaldoProd(Boolean clote, Integer codfilialax, Integer codprod
			, BigDecimal qtd, BigDecimal preco
			, BigDecimal vlrdesc, BigDecimal qtdafat) throws SQLException {
		Vector<SaldoProd> result = new Vector<SaldoProd>();
		StringBuilder sql = new StringBuilder();
		if (clote) {
			sql.append( "select sl.codalmox, sl.codlote, sl.sldliqlote saldo ");
			sql.append( "from eqsaldolote sl, eqlote lt ");
			sql.append( "where lt.codemp=? and lt.codfilial=? and lt.codprod=? and sl.codempax=? and sl.codfilialax=? ");
			sql.append( "and sl.sldliqlote>0 and lt.venctolote<cast('now' as date) ");
			sql.append( "order by lt.venctolote ");
		} else {
			sql.append( "select sl.codalmox, sl.sldliqprod saldo ");
			sql.append( "from eqsaldprod sl ");
			sql.append( "where sl.codemp=? and sl.codfilial=? and sl.codprod=? and sl.codempax=? and sl.codfilialax=? ");
			sql.append( "and sl.sldliqprod>0 ");
			sql.append( "order sl.sldliqprod desc ");
		}
		PreparedStatement ps = getConn().prepareStatement( sql.toString() );
		int param = 1;
		ps.setInt( param++, getCodemp() );
		ps.setInt( param++, getCodfilial() );
		ps.setInt( param++, codprod );
		ps.setInt( param++, getCodemp() );
		ps.setInt( param++, codfilialax );
		ResultSet rs = ps.executeQuery();
		BigDecimal qtdafatcalc = qtdafat;
		while (rs.next()) {
			SaldoProd saldoprod = new SaldoProd();
			saldoprod.setCodemp( getCodemp() );
			saldoprod.setCodfilial( getCodfilial() );
			saldoprod.setCodfilialax( codfilialax );
			saldoprod.setCodalmox( rs.getInt( "codalmox" ));
			saldoprod.setCodprod( codprod );
			saldoprod.setVlrdesc( vlrdesc );
			saldoprod.setQtd( qtd );
			saldoprod.setPreco( preco );
			BigDecimal saldo = rs.getBigDecimal( "saldo" );
			saldoprod.setSaldoprod( saldo );
			if (saldo.compareTo( qtdafatcalc )>=0) {
				saldoprod.setQtdafat( qtdafatcalc );
				qtdafatcalc = BigDecimal.ZERO;
			} else {
				saldoprod.setQtdafat( saldo );
				qtdafatcalc = qtdafatcalc.subtract( saldo );
			}
			if (clote) {
				saldoprod.setCodlote( rs.getString( "codlote" ) );
			}
			result.addElement( saldoprod );
			if (qtdafatcalc.compareTo( BigDecimal.ZERO )<=0) {
				break;
			}
		}
		rs.close();
		ps.close();
		return result;
	}
	
	public Vector<Object> getvValidos() {
		return vValidos;
	}

	public class SaldoProd {
		private Integer codemp;
		private Integer codfilial;
		private Integer codprod;
		private String codlote;
		private Integer codfilialax;
		private Integer codalmox;
		private BigDecimal saldoprod;
		private BigDecimal qtdafat;
		private BigDecimal preco;
		private BigDecimal vlrdesc;
		private BigDecimal qtd;
		
		public Integer getCodemp() {
		
			return codemp;
		}
		
		public void setCodemp( Integer codemp ) {
		
			this.codemp = codemp;
		}
		
		public Integer getCodfilial() {
		
			return codfilial;
		}
		
		public void setCodfilial( Integer codfilial ) {
		
			this.codfilial = codfilial;
		}
		
		public Integer getCodprod() {
		
			return codprod;
		}
		
		public void setCodprod( Integer codprod ) {
		
			this.codprod = codprod;
		}
		
		public Integer getCodfilialax() {
		
			return codfilialax;
		}
		
		public void setCodfilialax( Integer codfilialax ) {
		
			this.codfilialax = codfilialax;
		}
		
		public BigDecimal getSaldoprod() {
		
			return saldoprod;
		}
		
		public void setSaldoprod( BigDecimal saldoprod ) {
		
			this.saldoprod = saldoprod;
		}

		
		public BigDecimal getQtdafat() {
		
			return qtdafat;
		}

		
		public void setQtdafat( BigDecimal qtdafat ) {
		
			this.qtdafat = qtdafat;
		}

		
		public Integer getCodalmox() {
		
			return codalmox;
		}

		
		public void setCodalmox( Integer codalmox ) {
		
			this.codalmox = codalmox;
		}

		
		public void setCodlote( String codlote ) {
		
			this.codlote = codlote;
		}
		
		public String getCodlote() {
			return codlote;
		}

		
		public BigDecimal getPreco() {
		
			return preco;
		}

		
		public void setPreco( BigDecimal preco ) {
		
			this.preco = preco;
		}

		
		public BigDecimal getVlrdesc() {
		
			return vlrdesc;
		}

		
		public void setVlrdesc( BigDecimal vlrdesc ) {
		
			this.vlrdesc = vlrdesc;
		}

		
		public BigDecimal getQtd() {
		
			return qtd;
		}

		
		public void setQtd( BigDecimal qtd ) {
		
			this.qtd = qtd;
		}
		
		public BigDecimal getVlrDescCalc() {
			BigDecimal result = BigDecimal.ZERO;
			if (vlrdesc!=null && vlrdesc.compareTo( BigDecimal.ZERO )>0) {
				BigDecimal vlrdescunit = vlrdesc.divide( qtd );
				result = qtdafat.multiply( vlrdescunit );
			}
			return result;
		}
	
		public BigDecimal getVlrLiqCalc() {
			BigDecimal result = BigDecimal.ZERO;
			BigDecimal vlrdesccalc = getVlrDescCalc();
			BigDecimal vlrtotcalc = qtdafat.multiply( preco );
			result = vlrtotcalc.subtract( vlrdesccalc );
			return result;
		}
	}
}
