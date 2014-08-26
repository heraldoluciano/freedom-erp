package org.freedom.modulos.std.dao;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

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

	public enum COL_PREFS { USAPEDSEQ, AUTOFECHAVENDA, ADICORCOBSPED, ADICOBSORCPED, FATORCPARC, APROVORCFATPARC
		, SOLDTSAIDA, BLOQVDPORATRASO, NUMDIASBLOQVD, ALMOXPDORC, ESTOQALMOX, RATEIOESTBUSCAORC };

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
		
		sql.append( "insert into vditcontrato (codemp, codfilial, codcontr, coditcontr, descitcontr, codemppd, codfilialpd");
		sql.append( ", codprod, qtditcontr, vlritcontr, "); 
		sql.append( "codemppe, codfilialpe, codprodpe, vlritcontrexced, indexitcontr, acumuloitcontr, franquiaitcontr ");
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
			, Integer codfilialvd, String tipovenda, Integer codvenda, Date datasaida, Integer codfilialpf) throws SQLException {
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
		codvenda = vdadicvendaorcsp( codorc, codfilialvd, tipovenda, codvenda
				, Funcoes.dateToSQLDate( datasaida == null ? new Date() : datasaida )
				, codfilialpf );
		return codvenda;
	}

	public Integer vdadicvendaorcsp ( Integer codorc, Integer codfilialvd, String tipovenda, Integer codvenda
			, Date dtsaidavenda, Integer codfilialpf) throws SQLException {
		 Integer result = null;
		 Integer codtipomov=null;
		 String serie=null;
		 String statusvenda=null;
		 BigDecimal perccomvend=null;
		 Integer codvend=null;
		 Integer codcli=null;
		 Integer codplanopag=null;
		 Integer conta = null;
		 Integer codclcomis=null;
		 Integer codfilialcm=null;
		 BigDecimal vlrfreteorc=null;
		 Integer codtran=null;
		 String tipofrete=null;
		 String adicfrete=null;
		 Integer codfilialtm=null;
		 Integer codfilialse=null;
		 Integer codfilialcl=null;
		 Integer codfilialtn=null;
		 Integer codfilialpg=null;
		 StringBuilder sql = new StringBuilder();
		 sql.append("select count(p.tipoprod) conta from eqproduto p, vditorcamento i ");
		 sql.append("where p.codprod=i.codprod and p.codfilial=i.codfilialpd and p.codemp=i.codemppd ");
		 sql.append("and p.tipoprod='S' and i.codemp=? and i.codfilial=? and i.codorc=? ");
		 PreparedStatement ps = getConn().prepareStatement( sql.toString() );
		 int param = 1;
		 ps.setInt( param++, getCodemp() );
		 ps.setInt( param++, getCodfilial() );
		 ps.setInt( param++, codorc );
		 ResultSet rs = ps.executeQuery();
		 if (rs.next()) {
			 conta = rs.getInt( "conta" );
		 }
		 rs.close();
		 ps.close();
		 sql.delete( 0, sql.length() );
		 if (conta!=null && conta.intValue()>0) {
			 sql.append("select p1.codfilialt4, p1.codtipomov4 ");
			 sql.append(", tm.codfilialse, tm.serie ");
			 sql.append("from sgprefere1 p1 ");
			 sql.append("left outer join eqtipomov tm ");
			 sql.append("on tm.codemp=p1.codempt4 and tm.codfilial=p1.codfilialt4 and tm.codtipomov=p1.codtipomov4 ");
			 sql.append("where p1.codemp=? and p1.codfilial=?");
			 ps = getConn().prepareStatement( sql.toString() );
			 param = 1;
			 ps.setInt( param++, getCodemp() );
			 ps.setInt( param++, codfilialpf );
			 rs = ps.executeQuery();
			 if (rs.next()) {
				  codfilialtm = rs.getInt( "codfilialt4" );
				  codtipomov = rs.getInt( "codtipomov4" );
				  codfilialse = rs.getInt( "codfilialse" );
				  serie = rs.getString( "serie" );
			 }
			 rs.close();
			 ps.close();
			 sql.delete( 0, sql.length() );
		 }
		 if ((codtipomov==null) || (codtipomov==0)) {
			 sql.append("select p1.codfilialt3, p1.codtipomov3 ");
			 sql.append(", tm.codfilialse, tm.serie ");
			 sql.append("from sgprefere1 p1 ");
			 sql.append("left outer join eqtipomov tm ");
			 sql.append("on tm.codemp=p1.codempt3 and tm.codfilial=p1.codfilialt3 and tm.codtipomov=p1.codtipomov3 ");
			 sql.append("where p1.codemp=? and p1.codfilial=?");
			 ps = getConn().prepareStatement( sql.toString() );
			 param = 1;
			 ps.setInt( param++, getCodemp() );
			 ps.setInt( param++, codfilialpf );
			 rs = ps.executeQuery();
			 if (rs.next()) {
				  codfilialtm = rs.getInt( "codfilialt3" );
				  codtipomov = rs.getInt( "codtipomov3" );
				  codfilialse = rs.getInt( "codfilialse" );
				  serie = rs.getString( "serie" );
			 }
			 rs.close();
			 ps.close();
			 sql.delete( 0, sql.length() );
		 }
		 statusvenda = "P1";
		 sql.append( "select o.codfilialvd,o.codvend,o.codfilialcl,o.codcli,o.codfilialpg " );
		 sql.append( ", o.codplanopag,ve.perccomvend,o.codclcomis,o.codfilialcm ");
		 sql.append( ", coalesce(o.vlrfreteorc,0) vlrfreteorc, o.codemptn, o.codfilialtn, o.codtran ");
		 sql.append( ", o.tipofrete, o.adicfrete ");
		 sql.append( "from vdorcamento o, vdvendedor ve ");
		 sql.append( "where o.codemp=? and o.codfilial=? and o.codorc=? ");
		 sql.append( "and ve.codemp=o.codemp and ve.codfilial=o.codfilialvd ");
		 sql.append( "and ve.codvend=o.codvend ");
		 ps = getConn().prepareStatement( sql.toString() );
		 param = 1;
		 ps.setInt( param++, getCodemp() );
		 ps.setInt( param++, getCodfilial() );
		 ps.setInt( param++, codorc );
		 rs = ps.executeQuery();
		 if (rs.next()) {
			 codfilialvd = rs.getInt( "codfilialvd" );
			 codvend = rs.getInt( "codvend" );
			 codfilialcl = rs.getInt( "codfilialcl" );
			 codcli = rs.getInt( "codcli" );
			 codfilialpg = rs.getInt( "codfilialpg" );
			 codplanopag = rs.getInt( "codplanopag" );
			 perccomvend = rs.getBigDecimal( "perccomvend" );
			 codclcomis = rs.getInt( "codclcomis" );
			 codfilialcm = rs.getInt( "codfilialcm" );
			 vlrfreteorc = rs.getBigDecimal( "vlrfreteorc" );
			 codfilialtn = rs.getInt( "codfilialtn" );
			 codtran = rs.getInt( "codtran" );
			 tipofrete = rs.getString( "tipofrete" );
			 adicfrete = rs.getString( "adicfrete" );
		 }
		 rs.close();
		 ps.close();
		 sql.delete( 0, sql.length() );
		 sql.append( "insert into vdvenda ( ");
		 sql.append( "codemp,codfilial,codvenda,tipovenda,codempvd,codfilialvd,codvend,codempcl,codfilialcl,codcli, ");
		 sql.append( "codemppg,codfilialpg,codplanopag,codempse,codfilialse,serie,codemptm,codfilialtm,codtipomov, ");
		 sql.append( "dtsaidavenda,dtemitvenda,statusvenda,perccomisvenda, codempcm, codfilialcm, codclcomis ) ");
		 sql.append( "values ( ");
		 sql.append( "?, ?, ?, ?, ?, ?, ?, ?, ?, ? ");
		 sql.append( ", ?, ?, ?, ?, ?, ?, ?, ?, ? ");
		 sql.append( ", ?, cast('today' as date), ?, ?, ?, ?, ? ");
		 sql.append( ") ");
		 ps = getConn().prepareStatement( sql.toString() );
		 param = 1;
		 ps.setInt( param++, getCodemp() );
		 ps.setInt( param++, getCodfilial() );
		 ps.setInt( param++, codvenda );
		 ps.setString( param++, tipovenda );
		 ps.setInt( param++, getCodemp() );
		 ps.setInt( param++, codfilialvd );
		 ps.setInt( param++, codvend );
		 ps.setInt( param++, getCodemp() );
		 ps.setInt( param++, codfilialcl );
		 ps.setInt( param++, codcli );
		 ps.setInt( param++, getCodemp() );
		 ps.setInt( param++, codfilialpg );
		 ps.setInt( param++, codplanopag );
		 ps.setInt( param++, getCodemp() );
		 ps.setInt( param++, codfilialse );
		 ps.setString( param++, serie );
		 ps.setInt( param++, getCodemp() );
		 ps.setInt( param++, codfilialtm );
		 ps.setInt( param++, codtipomov );
		 ps.setDate( param++, Funcoes.dateToSQLDate( dtsaidavenda ) );
		 ps.setString( param++, statusvenda );
		 ps.setBigDecimal( param++, perccomvend );
		 ps.setInt( param++, getCodemp() );
		 ps.setInt( param++, codfilialcm );
		 ps.setInt( param++, codclcomis );
		 ps.executeUpdate();
		 ps.close();
		 sql.delete( 0, sql.length() );
		 if ( (codtran!=null && codtran!=0) || (vlrfreteorc!=null && vlrfreteorc.doubleValue()>0) )  {
			 sql.append( "insert into vdfretevd (codemp, codfilial, tipovenda, codvenda, codemptn ");
			 sql.append( ", codfilialtn, codtran, tipofretevd, vlrfretevd, adicfretevd ");
			 sql.append( ", placafretevd, vlrsegfretevd, pesobrutvd, pesoliqvd ");
			 sql.append( ", espfretevd, marcafretevd, qtdfretevd, uffretevd ");
			 sql.append( ") ");
			 sql.append( "values (?, ?, ?, ?, ? ");
			 sql.append( ", ?, ?, ?, ?, ? ");
			 sql.append( ", '***-***', 0, 0, 0 ");
			 sql.append( ", 'Volume', '*', 0, '**' ) ");
			 ps = getConn().prepareStatement( sql.toString() );
			 param = 1;
			 ps.setInt( param++, getCodemp() );
			 ps.setInt( param++, getCodfilial() );
			 ps.setString( param++, tipovenda );
			 ps.setInt( param++, codvenda );
			 ps.setInt( param++, getCodemp() );
			 ps.setInt( param++, codfilialtn );
			 ps.setInt( param++, codtran );
			 ps.setString( param++, tipofrete );
			 ps.setBigDecimal( param++, vlrfreteorc );
			 ps.setString( param++, adicfrete );
			 ps.executeUpdate();
			 ps.close();
			 sql.delete( 0, sql.length() );
		}
		result = codvenda;
		return result;
	}
	
	public void executaVDAdicItVendaORCSP(Integer codfilial, Integer codvenda, Integer codorc
			, Integer coditorc, Integer codfilialoc, Integer codempoc
			, String tipovenda, String tpagr, BigDecimal qtdprod, BigDecimal qtdafatitorc
			, BigDecimal desc, Integer codfilialnt, Integer codfilialtt, Integer codfilialme) throws SQLException {

		BigDecimal qtditvenda = BigDecimal.ZERO;
		qtditvenda = qtdafatitorc;	
		vdadicitvendaorc( codfilial, codvenda, codorc, coditorc, tipovenda, tpagr, qtditvenda
				, desc, codfilialnt, codfilialtt, codfilialme );
	}

	@ SuppressWarnings ( "null" )
	public void vdadicitvendaorc(Integer codfilial, Integer codvenda, Integer codorc, Integer coditorc
			, String tipovenda, String tpagrup, BigDecimal qtditvenda, BigDecimal vlrdescitvenda
			, Integer codfilialnt, Integer codfilialtt, Integer codfilialme
			) throws SQLException {
		Integer coditvenda=null;
		Integer codempax=null;
		Integer codfilialax=null;
		Integer codalmox=null;
		Integer codcli=null;
		Integer codfilialtm=null;
		Integer codtipomov=null;
		Integer codfilialcl=null;
		String codnat=null;
		String codlote=null;
		String tipofisc=null;
		String origfisc=null;
		String codtrattrib=null;
		//Integer codfilialtt=null;
		//Integer codfilialme=null;
		Integer codmens=null;
		BigDecimal percicmsitvenda=null;
		BigDecimal vlrbaseicmsitvenda=null;
		BigDecimal vlricmsitvenda=null;
		BigDecimal percipiitvenda=null;
		BigDecimal vlrbaseipiitvenda=null;
		BigDecimal vlripiitvenda=null;
		Integer codprod=null;
		Integer codfilialpd=null;
		BigDecimal vlrprecoitvenda=null;
		BigDecimal percdescitvenda=null;
		BigDecimal vlrliqitvenda=null;
		BigDecimal vlrproditvenda=null;
		String obsitorc=null;
		String ufcli=null;
		String ufflag=null;
		BigDecimal percred=null;
		String cloteprod=null;
		BigDecimal perccomisitvenda=null;
		String geracomis=null;
		BigDecimal vlrcomisitvenda=null;
		Integer codempif=null;
		Integer codfilialif=null;
		String codfisc=null;
		Integer coditfisc=null;
		BigDecimal percissitvenda=null;
		BigDecimal vlrbaseissitvenda=null;
		BigDecimal vlrissitvenda=null;
		BigDecimal vlrisentasitvenda=null;
		BigDecimal vlroutrasitvenda=null;
		String tipost=null;
		BigDecimal vlrbaseicmsstitvenda=null;
		BigDecimal vlricmsstitvenda=null;
		BigDecimal margemvlagritvenda=null;
		String refprod=null;
		String tipoprod=null;
		BigDecimal percicmsst=null;
		BigDecimal vlrbaseicmsbrutitvenda=null;
		String tpredicms=null;
		String redbaseicmsst=null;
		BigDecimal qtditorc=null;
		String calcstcm=null;
		BigDecimal aliqicmsstcm=null;
		BigDecimal vlricmsstcalc=null;
		final BigDecimal CEM = new BigDecimal(100);
		// Inicialização de variaveis
		calcstcm = "N";
		ufflag = "N";
		StringBuilder sql = new StringBuilder();
		sql.append("select vd.codfilialtm, vd.codtipomov from vdvenda vd ");
		sql.append("where codemp=? and codfilial=? and tipovenda=? and codvenda=?");
		PreparedStatement ps = getConn().prepareStatement( sql.toString() );
		int param = 1;
		ps.setInt(param++, getCodemp());
		ps.setInt( param++, getCodfilial() );
		ps.setString( param++, tipovenda );
		ps.setInt( param++, codvenda );
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			codfilialtm = rs.getInt( "codfilialtm" );
			codtipomov = rs.getInt( "codtipomov" );
		}
		rs.close();
		ps.close();
		sql.delete( 0, sql.length() );
		// Verifica se deve gerar comissão para a venda
		sql.append( "select geracomisvendaorc from sgprefere1 where codemp=? and codfilial=?" );
		ps = getConn().prepareStatement( sql.toString() );
		param = 1;
		ps.setInt(param++, getCodemp());
		ps.setInt( param++, getCodfilial() );
		rs = ps.executeQuery();
		if (rs.next()) {
			geracomis = rs.getString( "geracomisvendaorc" );
		}
		rs.close();
		ps.close();
		sql.delete( 0, sql.length() );
		// Busca sequencia de numeração do ítem de venda
		sql.append( "select coalesce(max(coditvenda),0)+1 coditvenda from vditvenda ");
		sql.append( "where codemp=? and codfilial=? and tipovenda=? and codvenda=?" );
		ps = getConn().prepareStatement( sql.toString() );
		param = 1;
		ps.setInt(param++, getCodemp());
		ps.setInt( param++, getCodfilial() );
		ps.setString( param++, tipovenda );
		ps.setInt( param++, codvenda );
		rs = ps.executeQuery();
		if (rs.next()) {
			coditvenda = rs.getInt( "coditvenda" );
		}
		rs.close();
		ps.close();
		sql.delete( 0, sql.length() );
		// Informações do Orcamento
		sql.append( "select oc.codcli, oc.codfilialcl, coalesce(cl.siglauf,cl.ufcli) siglauf ");
		sql.append( "from vdorcamento oc ");
		sql.append( "left outer join vdcliente cl ");
		sql.append( "on cl.codemp=oc.codempcl and cl.codfilial=oc.codfilialcl and cl.codcli=oc.codcli ");
		sql.append( "where oc.codemp=? and oc.codfilial=? and oc.codorc=?" );
		ps = getConn().prepareStatement( sql.toString() );
		param = 1;
		ps.setInt(param++, getCodemp());
		ps.setInt( param++, getCodfilial() );
		ps.setInt( param++, codorc );
		rs = ps.executeQuery();
		if (rs.next()) {
			codfilialcl = rs.getInt( "codfilialcl" );
			codcli = rs.getInt( "codcli" );
			ufcli = rs.getString( "siglauf" );
		}
		rs.close();
		ps.close();
		sql.delete( 0, sql.length() );
		sql.append( "select it.codemppd, it.codfilialpd,it.codprod,it.precoitorc,it.percdescitorc ");
		sql.append( ",it.vlrliqitorc,it.vlrproditorc,it.refprod,it.obsitorc ");
		sql.append( ",it.codempax,it.codfilialax,it.codalmox,it.codlote,pd.cloteprod ");
		sql.append( ",pd.comisprod,pd.tipoprod, it.perccomisitorc, it.vlrcomisitorc, it.qtditorc " );
		sql.append( "from vditorcamento it, eqproduto pd ");
		sql.append( "where it.codemp=? and it.codfilial=? and it.codorc=? and it.coditorc=? ");
		sql.append( "and pd.codemp=it.codemppd and pd.codfilial=it.codfilialpd and pd.codprod=it.codprod ");
		ps = getConn().prepareStatement( sql.toString() );
		param = 1;
		ps.setInt(param++, getCodemp());
		ps.setInt( param++, getCodfilial() );
		ps.setInt( param++, codorc );
		ps.setInt( param++, coditorc );
		rs = ps.executeQuery();
		if (rs.next()) {
			codfilialpd = rs.getInt( "codfilialpd" );
			codprod = rs.getInt( "codprod" );
			vlrprecoitvenda = rs.getBigDecimal( "precoitorc" );
			percdescitvenda = rs.getBigDecimal( "percdescitorc" );
			vlrliqitvenda = rs.getBigDecimal( "vlrliqitorc" );
			vlrproditvenda = rs.getBigDecimal( "vlrproditorc" );
			refprod = rs.getString( "refprod" );
			obsitorc = rs.getString( "obsitorc" );
			codfilialax = rs.getInt( "codfilialax" );
			codalmox = rs.getInt( "codalmox" );
			codlote = rs.getString( "codlote" );
			cloteprod = rs.getString( "cloteprod" );
			perccomisitvenda = rs.getBigDecimal( "perccomisitorc" );
			tipoprod = rs.getString( "tipoprod" );
			vlrcomisitvenda = rs.getBigDecimal( "vlrcomisitorc" );
			qtditorc = rs.getBigDecimal( "qtditorc" );
		}
		rs.close();
		ps.close();
		sql.delete( 0, sql.length() );
	    // Informações fiscais para a venda
	    // Busca informações fiscais para o ítem de venda (sem natureza da operação deve retornar apenas o coditfisc)
		sql.append( "select codfilialif,codfisc,coditfisc ");
		sql.append( "from lfbuscafiscalsp(?, ?, ?, ?, ?, ?, ?, ?, ?, ? , null, null, null, null, null)");
		ps = getConn().prepareStatement( sql.toString() );
		param = 1;
		ps.setInt( param++, getCodemp());
		ps.setInt( param++, codfilialpd );
		ps.setInt( param++, codprod );
		ps.setInt( param++, getCodemp());
		ps.setInt( param++, codfilialcl );
		ps.setInt( param++, codcli );
		ps.setInt( param++, getCodemp());
		ps.setInt( param++, codfilialtm );
		ps.setInt( param++, codtipomov );
		ps.setString( param++, tipovenda );
		rs = ps.executeQuery();
		if (rs.next()) {
			codfilialif = rs.getInt( "codfilialif" );
			codfisc = rs.getString( "codfisc" );
			coditfisc = rs.getInt( "coditfisc" );
		}
		rs.close();
		ps.close();
		sql.delete( 0, sql.length() );
		// Verifica se a venda é para outro estado
		sql.append( "select codnat from lfbuscanatsp(?, ?, ?, ?, ?, ?, ?, null, null, null, ?, ?, ?) ");
		ps = getConn().prepareStatement( sql.toString() );
		param = 1;
		ps.setInt( param++, codfilial);
		ps.setInt( param++, getCodemp());
		ps.setInt( param++, codfilialpd );
		ps.setInt( param++, codprod );
		ps.setInt( param++, getCodemp());
		ps.setInt( param++, codfilialcl );
		ps.setInt( param++, codcli );
		ps.setInt( param++, codfilialtm );
		ps.setInt( param++, codtipomov );
		ps.setInt( param++, coditfisc );
		rs = ps.executeQuery();
		if (rs.next()) {
			codnat = rs.getString( "codnat" );
		}
		rs.close();
		ps.close();
		sql.delete( 0, sql.length() );
		if (codnat==null) {
			new SQLException("Não foi possível encontrar a natureza da operação para o ítem "+codprod);
		}
		// Busca informações fiscais para o ítem de venda (já sabe o coditfisc)
		sql.append( "select tipofisc,redfisc,codtrattrib,origfisc,codmens,aliqfisc,codempif,codfilialif,codfisc,coditfisc,tipost ");
		sql.append( ", margemvlagr,aliqipifisc,aliqfiscintra,tpredicmsfisc,redbasest, aliqiss, coalesce(calcstcm,'N') calcstcm, aliqicmsstcm ");
		sql.append( " from lfbuscafiscalsp(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
		ps = getConn().prepareStatement( sql.toString() );
		param = 1;
		ps.setInt( param++, getCodemp());
		ps.setInt( param++, codfilialpd );
		ps.setInt( param++, codprod );
		ps.setInt( param++, getCodemp());
		ps.setInt( param++, codfilialcl );
		ps.setInt( param++, codcli );
		ps.setInt( param++, getCodemp());
		ps.setInt( param++, codfilialtm );
		ps.setInt( param++, codtipomov );
		ps.setString( param++, "VD" ); // tipo de busca VD / CP
		ps.setString( param++, codnat );
		if (codfisc==null) {
			ps.setNull( param++, Types.INTEGER );
			ps.setNull( param++, Types.INTEGER );
			ps.setNull( param++, Types.CHAR );
			ps.setNull( param++, Types.INTEGER );
		} else {
			ps.setInt( param++, getCodemp());
			ps.setInt( param++, codfilialif );
			ps.setString( param++, codfisc );
			ps.setInt( param++, coditfisc );
		}
		rs = ps.executeQuery();
		if (rs.next()) {
			tipofisc = rs.getString( "tipofisc" );
			percred = rs.getBigDecimal( "redfisc" );
			codtrattrib = rs.getString( "codtrattrib" );
			origfisc = rs.getString( "origfisc" );
			codmens = rs.getInt( "codmens" );
			percicmsitvenda = rs.getBigDecimal( "aliqfisc" );
			codfilialif = rs.getInt( "codfilialif" );
			codfisc = rs.getString( "codfisc" );
			coditfisc = rs.getInt( "coditfisc" );
			tipost = rs.getString( "tipost" );
			margemvlagritvenda = rs.getBigDecimal( "margemvlagr" );
			percipiitvenda = rs.getBigDecimal( "aliqipifisc" );
			percicmsst = rs.getBigDecimal( "aliqfiscintra" );
			tpredicms = rs.getString( "tpredicmsfisc" );
			redbaseicmsst = rs.getString( "redbasest" );
			percissitvenda = rs.getBigDecimal( "aliqiss" );
			calcstcm = rs.getString( "calcstcm" );
			aliqicmsstcm = rs.getBigDecimal( "aliqicmsstcm" );
		}
		rs.close();
		ps.close();
		sql.delete( 0, sql.length() );
		// Busca lote, caso seja necessário
	    if ("S".equals(cloteprod) && codlote==null) {
	    	sql.append( "select first 1 l.codlote from eqlote l ");
	    	sql.append( "where l.codemp=? and l.codfilial=? and l.codprod=? "); 
	    	sql.append( "and l.venctolote>cast('now' as date) and l.sldliqlote>=? ");
	    	sql.append( "order by l.venctolote ");
			ps = getConn().prepareStatement( sql.toString() );
			param = 1;
			ps.setInt(param++, getCodemp());
			ps.setInt( param++, getCodfilial() );
			ps.setInt( param++, codprod );
			ps.setBigDecimal( param++, qtditorc );
			rs = ps.executeQuery();
			if (rs.next()) {
				codlote = rs.getString( "codlote" );
			}
			rs.close();
			ps.close();
			sql.delete( 0, sql.length() );
	    }
		// Inicializando valores
	    vlrproditvenda = vlrprecoitvenda.multiply( qtditvenda );
//	    if ( qtditvenda.compareTo( qtditorc )!=0 ) {
	//    	vlrdescitvenda = vlrdescitvenda.divide( qtditorc ).multiply( qtditvenda ); 
//	    }
	    vlrliqitvenda = vlrproditvenda.subtract( vlrdescitvenda );
	    vlrbaseipiitvenda = BigDecimal.ZERO;
	    vlrbaseicmsitvenda = BigDecimal.ZERO;
	    vlricmsitvenda = BigDecimal.ZERO;
	    vlripiitvenda = BigDecimal.ZERO;
	    if (percicmsitvenda==null || percicmsitvenda.doubleValue()==0) {
	    	sql.append( "select coalesce(percicms,0) percicms from lfbuscaicmssp (?, ?, ?, ?)");
			ps = getConn().prepareStatement( sql.toString() );
			param = 1;
			ps.setString(param++, codnat);
			ps.setString( param++, ufcli );
			ps.setInt( param++, getCodemp() );
			ps.setInt( param++, getCodfilial() );
			rs = ps.executeQuery();
			if (rs.next()) {
				percicmsst = rs.getBigDecimal( "percicms" );
			}
			rs.close();
			ps.close();
			sql.delete( 0, sql.length() );
	    }
	    if (percred==null) {
	    	percred = BigDecimal.ZERO;
	    }
	    if (percred.doubleValue()>0) {
	        if ("B".equals( tpredicms ) ) {
	            vlrbaseicmsitvenda = (vlrproditvenda.subtract( vlrdescitvenda)).subtract( 
	            		(vlrproditvenda.subtract( vlrdescitvenda))).multiply( percred ).divide( CEM, 5, BigDecimal.ROUND_HALF_UP );
	            vlricmsitvenda = vlrbaseicmsitvenda.multiply(  percicmsitvenda.divide( CEM, 5, BigDecimal.ROUND_HALF_UP ) );
	        } else if( "V".equals( tpredicms ) ) {
	            vlrbaseicmsitvenda = vlrproditvenda.subtract( vlrdescitvenda );
	            vlricmsitvenda = vlrbaseicmsitvenda.multiply( percicmsitvenda.divide( CEM, 5, BigDecimal.ROUND_HALF_UP )
	            		.subtract( vlrbaseicmsitvenda.multiply( percicmsitvenda.divide( CEM, 5, BigDecimal.ROUND_HALF_UP )
	            				.multiply( percred.divide( CEM, 5, BigDecimal.ROUND_HALF_UP ) ) ) ) ) ;
	        }
	    } else {
	        vlrbaseicmsitvenda = vlrproditvenda.subtract( vlrdescitvenda );
	        vlricmsitvenda = vlrbaseicmsitvenda.multiply( percicmsitvenda.divide( CEM, 5, BigDecimal.ROUND_HALF_UP ) );
	    }
	    vlrbaseipiitvenda = vlrproditvenda.subtract( vlrdescitvenda );
	    vlrbaseicmsbrutitvenda = vlrproditvenda.subtract( vlrdescitvenda );
	    vlripiitvenda = vlrbaseipiitvenda.multiply( percipiitvenda.divide( CEM, 5, BigDecimal.ROUND_HALF_UP ) );
		// **** Calculo dos tributos ***
	    // Verifica se é um serviço (Calculo do ISS);			    
	    if ("S".equals( tipoprod ) ) {
		    // Carregando aliquota do ISS
		    // Bloco comentado, pois já buscou o percentual do iss através da procedure.
		   //     select percissfilial from sgfilial where codemp=:ICODEMP and codfilial=:ICODFILIAL
		   //     into PERCISSITVENDA;
		   //-- Calculando e computando base e valor do ISS;
		    if (percissitvenda !=null || percissitvenda.doubleValue() != 0) {
	            vlrbaseissitvenda = vlrliqitvenda;
	            vlrissitvenda = vlrbaseissitvenda.multiply( percissitvenda.divide( CEM, 5, BigDecimal.ROUND_HALF_UP) );
		    }
	    } else { //-- Se o item vendido não for SERVIÇO zera ISS
	        vlrbaseissitvenda = BigDecimal.ZERO;
	    }
	    //  Se produto for isento de ICMS
	    if ("II".equals( tipofisc )) {
	        vlrisentasitvenda = vlrliqitvenda;
	        vlrbaseicmsitvenda = BigDecimal.ZERO;
	        percicmsitvenda = BigDecimal.ZERO;
	        vlricmsitvenda = BigDecimal.ZERO;
	        vlroutrasitvenda = BigDecimal.ZERO;
	    } else if ("FF".equals( tipofisc )) { // Se produto for de Substituição Tributária
	        if ( "SI".equals( tipost ) ) { // Contribuinte Substituído
	            vlroutrasitvenda = vlrliqitvenda;
	            vlrbaseicmsitvenda = BigDecimal.ZERO;
	            percicmsitvenda = BigDecimal.ZERO;
	            vlricmsitvenda = BigDecimal.ZERO;
	            vlrisentasitvenda = BigDecimal.ZERO;
	        }
	        else if ( "SU".equals(tipost) ) { //- Contribuinte Substituto
	            if( (percicmsst == null) || (percicmsst.doubleValue()==0) ) {
	                sql.append("select coalesce(percicmsintra,0) percicmsst from lfbuscaicmssp (?, ?, ?, ?)");
	                ps = getConn().prepareStatement( sql.toString() );
	                ps.setString( param++, codnat );
	                ps.setString( param++, ufcli );
	                ps.setInt( param++, getCodemp() );
	                ps.setInt( param++, getCodfilial() );
	                rs = ps.executeQuery();
	                if (rs.next()) {
	                	percicmsst = rs.getBigDecimal( "percicmsst" );
	                }
	            }
	            // Calculo do ICMS ST para fora de mato grosso.
            	if (margemvlagritvenda==null) { margemvlagritvenda = BigDecimal.ZERO; }
            	if (vlrbaseicmsitvenda==null) { vlrbaseicmsitvenda = BigDecimal.ZERO; }
            	if (vlripiitvenda==null) { vlripiitvenda = BigDecimal.ZERO; }
            	if (vlrbaseicmsbrutitvenda==null) { vlrbaseicmsbrutitvenda = BigDecimal.ZERO; }
	            if ("N".equals( calcstcm )  ) {
	                if (percred.doubleValue()>0 && "S".equals( redbaseicmsst )) {
	                	// Quando há redução na base do icms st , deve usar o valor da base do icms proprio como parametro
	                    vlrbaseicmsstitvenda = margemvlagritvenda.add( CEM ).divide( CEM, 5, BigDecimal.ROUND_HALF_UP )
	                    		.multiply( vlrbaseicmsitvenda.add(vlripiitvenda) ) ;
	                }  else {
	                    // Quando não há redução na base do icms st deve usar o valor da base bruto (rem redução)
	                    vlrbaseicmsstitvenda = margemvlagritvenda.add( CEM ).divide( CEM, 5, BigDecimal.ROUND_HALF_UP )
	                    		.multiply( vlrbaseicmsbrutitvenda ).add(vlripiitvenda );
	                }
	                vlroutrasitvenda = BigDecimal.ZERO;
	                vlrisentasitvenda = BigDecimal.ZERO;
	                vlricmsstitvenda = vlrbaseicmsstitvenda.multiply( percicmsst)
	                		.divide( CEM, 5, BigDecimal.ROUND_HALF_UP ).subtract( vlricmsitvenda) ;
	            } 
	            // Calculo do ICMS ST para o mato grosso.
	            else {
	                if(percred.doubleValue()>0 && "S".equals( redbaseicmsst )) {
	                   vlricmsstcalc = BigDecimal.ZERO;
	                   // Quando há redução na base do icms st , deve usar o valor da base do icms proprio como parametro
	                   vlricmsstcalc = vlrbaseicmsitvenda.add( vlripiitvenda).multiply( aliqicmsstcm.divide( CEM, 5, BigDecimal.ROUND_HALF_UP ) );
	                   vlrbaseicmsstitvenda =  vlricmsitvenda.add(vlricmsstcalc).divide( percicmsst.divide( CEM, 5, BigDecimal.ROUND_HALF_UP ), 5, BigDecimal.ROUND_HALF_UP );
	                } else {
	                    // Quando não há redução na base do icms st deve usar o valor da base bruto (rem redução)
	                    vlricmsstcalc = ( vlrbaseicmsbrutitvenda.add( vlripiitvenda ).multiply( aliqicmsstcm.divide( CEM, 5, BigDecimal.ROUND_HALF_UP ) ) );
	                    vlrbaseicmsstitvenda = vlricmsitvenda.add( vlricmsstcalc ).divide( percicmsst.divide( CEM, 5, BigDecimal.ROUND_HALF_UP ) );
	                }
	                vlroutrasitvenda = BigDecimal.ZERO;
	                vlrisentasitvenda = BigDecimal.ZERO;
	                vlricmsstitvenda = vlrbaseicmsitvenda.add( vlripiitvenda ).multiply( aliqicmsstcm.divide( CEM, 5, BigDecimal.ROUND_HALF_UP ) );
	            }
	        }
	    }
	    // Se produto não for tributado e não isento
	    else if ("NN".equals(tipofisc ) ) {
	        vlroutrasitvenda = vlrliqitvenda;
	        vlrbaseicmsitvenda = BigDecimal.ZERO;
	        percicmsitvenda = BigDecimal.ZERO;
	        vlricmsitvenda = BigDecimal.ZERO;
	        vlrisentasitvenda = BigDecimal.ZERO;
	    }
	    // Se produto for tributado integralmente
	    else if ( "TT".equals( tipofisc ) ) {
	        vlroutrasitvenda = BigDecimal.ZERO;
	        vlrisentasitvenda = BigDecimal.ZERO;
	    }
	    // Inserindo dados na tabela de ítens de venda
	    if ( !"F".equals(tpagrup) ) {
	    	sql.append("insert into vditvenda (codemp,codfilial,codvenda,tipovenda,coditvenda,codempnt,codfilialnt,codnat,codemppd ");
	    	sql.append(", codfilialpd,codprod,codemple,codfilialle,codlote,qtditvenda,precoitvenda,percdescitvenda,vlrdescitvenda ");
	    	sql.append(", percicmsitvenda,vlrbaseicmsitvenda,vlricmsitvenda,percipiitvenda,vlrbaseipiitvenda,vlripiitvenda,vlrliqitvenda ");
	    	sql.append(", vlrproditvenda,refprod,origfisc,codemptt,codfilialtt,codtrattrib,tipofisc,codempme,codfilialme,codmens,obsitvenda ");
	    	sql.append(", codempax,codfilialax,codalmox,perccomisitvenda,vlrcomisitvenda,codempif,codfilialif,codfisc,coditfisc,percissitvenda ");
	    	sql.append(", vlrbaseissitvenda,vlrissitvenda,vlrisentasitvenda,vlroutrasitvenda,tipost,vlrbaseicmsstitvenda,vlricmsstitvenda ");
	    	sql.append(", margemvlagritvenda,vlrbaseicmsbrutitvenda, calcstcm) ");
	    	sql.append(" values ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ");
	    	sql.append(", ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ");
	    	sql.append(", ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ");
	    	sql.append(", ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ");
	    	sql.append(", ?, ?, ?, ?, ?) ");
	    	ps = getConn().prepareStatement( sql.toString() );
	    	param = 1;
	    	ps.setInt( param++, getCodemp() );
	    	ps.setInt( param++, codfilial );
	    	ps.setInt( param++, codvenda );
	    	ps.setString( param++, tipovenda );
	    	ps.setInt( param++, coditvenda );
	    	ps.setInt( param++, getCodemp() );
	    	ps.setInt( param++, codfilialnt );
	    	ps.setString( param++, codnat);
	    	ps.setInt( param++, getCodemp() );
	    	ps.setInt( param++, codfilialpd );
	    	ps.setInt( param++, codprod );
	    	ps.setInt( param++, getCodemp() );
	    	ps.setInt( param++, codfilialpd );
	    	ps.setString( param++, codlote);
	    	ps.setBigDecimal( param++, qtditvenda );
	    	ps.setBigDecimal( param++, vlrprecoitvenda );
	    	if (percdescitvenda==null) {
	    		ps.setNull( param++, Types.DECIMAL );
	    	} else {
	    		ps.setBigDecimal( param++, percdescitvenda );
	    	}
	    	if (vlrdescitvenda==null) {
	    		ps.setNull( param++, Types.DECIMAL );
	    	} else {
	    		ps.setBigDecimal( param++, vlrdescitvenda );
	    	}
	    	ps.setBigDecimal( param++, percicmsitvenda );
	    	ps.setBigDecimal( param++, vlrbaseicmsitvenda );
	    	ps.setBigDecimal( param++, vlricmsitvenda );
	    	ps.setBigDecimal( param++, percipiitvenda );
	    	ps.setBigDecimal( param++, vlrbaseipiitvenda  );
	    	ps.setBigDecimal( param++, vlripiitvenda );
	    	ps.setBigDecimal( param++, vlrliqitvenda );
	    	ps.setBigDecimal( param++, vlrproditvenda );
	    	ps.setString( param++, refprod );
	    	ps.setString( param++, origfisc );
	    	ps.setInt( param++, getCodemp() );
	    	ps.setInt( param++, codfilialtt );
	    	ps.setString( param++, codtrattrib );
	    	ps.setString( param++, tipofisc );
	    	if (codmens==null || codmens.intValue()==0 ) {
	    		ps.setNull( param++, Types.INTEGER );
	    		ps.setNull( param++, Types.INTEGER );
	    		ps.setNull( param++, Types.INTEGER );
	    	} else {
	    		ps.setInt( param++, getCodemp() );
	    		ps.setInt( param++, codfilialme );
	    		ps.setInt( param++, codmens );
	    	}
	    	ps.setString( param++, obsitorc );
	    	ps.setInt( param++, getCodemp() );
	    	ps.setInt( param++, codfilialax );
	    	ps.setInt( param++, codalmox );
	    	ps.setBigDecimal( param++, perccomisitvenda );
	    	ps.setBigDecimal( param++, vlrcomisitvenda );
	    	ps.setInt( param++, getCodemp() );
	    	ps.setInt( param++, codfilialif );
	    	ps.setString( param++, codfisc );
	    	ps.setInt( param++, coditfisc );
	    	ps.setBigDecimal( param++, percissitvenda );
	    	ps.setBigDecimal( param++, vlrbaseissitvenda  );
	    	ps.setBigDecimal( param++, vlrissitvenda  );
	    	ps.setBigDecimal( param++, vlrisentasitvenda );
	    	ps.setBigDecimal( param++, vlroutrasitvenda );
	    	ps.setString( param++, tipost );
	    	ps.setBigDecimal( param++, vlrbaseicmsstitvenda );
	    	ps.setBigDecimal( param++, vlricmsstitvenda );
	    	ps.setBigDecimal( param++, margemvlagritvenda );
	    	ps.setBigDecimal( param++, vlrbaseicmsbrutitvenda );
	    	ps.setString( param++, calcstcm );
	    	ps.executeUpdate();
	    	ps.close();
	    	sql.delete( 0, sql.length() );
	    }
		// Atualizando informações de vínculo
	    sql.append( "execute procedure vdupvendaorcsp(?, ?, ?, ?, ?, ?, ?, ?)" );
	    ps = getConn().prepareStatement( sql.toString() );
	    param = 1;
	    ps.setInt( param++, getCodemp() );
	    ps.setInt( param++, getCodfilial() );
	    ps.setInt( param++, codorc );
	    ps.setInt( param++, coditorc );
	    ps.setInt( param++, codfilial );
	    ps.setInt( param++, codvenda );
	    ps.setInt( param++, coditvenda );
	    ps.setString( param++, tipovenda );
	    ps.executeUpdate();
	    ps.close();
    	sql.delete( 0, sql.length() );
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
			if (codorc>0) {
				ps.setInt( param++, codorc );
			} else {
				ps.setInt( param++, codcli );
			}
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
		Boolean almoxpdorc = (Boolean) getPrefs().get( COL_PREFS.ALMOXPDORC.name() );
		Boolean estoqalmox = (Boolean) getPrefs().get( COL_PREFS.ESTOQALMOX.name() );
		Boolean rateioestbuscaorc = (Boolean) getPrefs().get( COL_PREFS.RATEIOESTBUSCAORC.name() );
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
			sql.append( "select it.codorc,it.coditorc,it.codprod,p.descprod " );
			sql.append( ", it.qtditorc,it.qtdfatitorc,it.qtdafatitorc,it.precoitorc,it.vlrdescitorc,it.vlrliqitorc " );
			sql.append( ", it.vlrproditorc, p.cloteprod, it.codlote, coalesce(ip.qtdfinalproditorc,0) qtdfinalproditorc");
			sql.append( ", ip.codop, it.codalmox ");
			sql.append( ", p.codalmox codalmoxpd ");
			sql.append( "from vditorcamento it ");
			sql.append( "inner join eqproduto p on " );
			sql.append( "p.codprod=it.codprod and p.codfilial=it.codfilialpd and p.codemp=it.codemppd ");
			sql.append( "inner join vdorcamento o on " );
			sql.append( "o.codemp=it.codemp and o.codfilial=it.codfilial and o.tipoorc=it.tipoorc and o.codorc=it.codorc ");
			sql.append( "left outer join ppopitorc ip on ");
			sql.append( "ip.codempoc=it.codemp and ip.codfilialoc=it.codfilial and ip.tipoorc=it.tipoorc and ip.codorc=it.codorc and ip.coditorc=it.coditorc ");
			sql.append( "where ");
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
				Integer codalmoxpd = rs.getInt( "codalmoxpd" );
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
				Vector<SaldoProd> listsaldo = getSaldoProd( almoxpdorc, estoqalmox
						, "S".equals( cloteprod ), codfilialax, codprod, codalmoxpd, qtditorc
						, precoitorc, vlrdescitorc, qtdafatitorc, rateioestbuscaorc );
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
			sql = new StringBuilder("select p1.usapedseq, p4.autofechavenda, p1.adicorcobsped, p1.adicobsorcped");
			sql.append( ", p1.fatorcparc, p1.aprovorcfatparc, p1.soldtsaida " );
			sql.append( ", p1.bloqvdporatraso, p1.numdiasbloqvd ");
			sql.append( ", coalesce(p1.almoxpdorc,'S') almoxpdorc, coalesce(p1.estoqalmox,'N') estoqalmox ");
			sql.append( ", coalesce(p1.rateioestbuscaorc,'N') rateioestbuscaorc ");
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
				result.put( COL_PREFS.ALMOXPDORC.name(), new Boolean("S".equals( rs.getString( COL_PREFS.ALMOXPDORC.name()))));
				result.put( COL_PREFS.ESTOQALMOX.name(), new Boolean("S".equals( rs.getString( COL_PREFS.ESTOQALMOX.name()))));
				result.put( COL_PREFS.RATEIOESTBUSCAORC.name(), new Boolean("S".equals( rs.getString( COL_PREFS.RATEIOESTBUSCAORC.name()))));
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

	private Vector<SaldoProd> getSaldoProd(Boolean almoxpdorc, Boolean estoqalmox, Boolean clote, Integer codfilialax
			, Integer codprod, Integer codalmox	, BigDecimal qtd, BigDecimal preco
			, BigDecimal vlrdesc, BigDecimal qtdafat, Boolean rateioestbuscaorc) throws SQLException {
		Vector<SaldoProd> result = new Vector<SaldoProd>();
		if (rateioestbuscaorc) {
			StringBuilder sql = new StringBuilder();
			if (clote) {
				sql.append( "select lt.venctolote, lt.codlote, " );
				if (estoqalmox) {
					sql.append( "sl.codalmox, sl.sldliqlote " );
				} else {
					sql.append( "lt.sldliqlote ");
				}
				sql.append( "saldo ");
				sql.append( "from ");
				if (estoqalmox) {
				   sql.append( "eqsaldolote sl, ");
				}
				sql.append( "eqlote lt ");
				sql.append( "where lt.codemp=? and lt.codfilial=? and lt.codprod=? ");
				if (estoqalmox) {
					sql.append( "and sl.codempax=? and sl.codfilialax=? ");
					if (almoxpdorc && codalmox!=null) {
						sql.append("and sl.codalmox=? ");
					}
					sql.append( "and sl.codemp=lt.codemp and sl.codfilial=lt.codfilial and sl.codprod=lt.codprod and sl.codlote=lt.codlote ");
					sql.append( "and sl.sldliqlote>0 ");
				} else {
					sql.append( "and lt.sldliqlote>0 ");
				}
				sql.append( "and lt.venctolote>cast('now' as date) ");
				sql.append( "order by lt.venctolote ");
			} else {
				sql.append( "select ");
				if (estoqalmox) {
					sql.append( "sl.codalmox, sl.sldliqprod ");
				} else {
					sql.append( "pd.sldliqprod ");
				}
				sql.append( "saldo ");
				sql.append( "from ");
				if (estoqalmox) {
				   sql.append( "eqsaldoprod sl, ");
				}
				sql.append( "eqproduto pd ");
				sql.append( "where pd.codemp=? and pd.codfilial=? and pd.codprod=? ");
				if (estoqalmox) {
					sql.append( "and sl.codemp=pd.codemp and sl.codfilial=pd.codfilial and sl.codprod=pd.codprod ");
					sql.append( "and sl.codempax=? and sl.codfilialax=? ");
					if (almoxpdorc && codalmox!=null) {
						sql.append("and sl.codalmox=? ");
					}
					sql.append( "and sl.sldliqprod>0 ");
					sql.append( "order sl.sldliqprod desc ");
				} else {
					sql.append( "and pd.sldliqprod>0 ");
				}
			}
			PreparedStatement ps = getConn().prepareStatement( sql.toString() );
			int param = 1;
			ps.setInt( param++, getCodemp() );
			ps.setInt( param++, getCodfilial() );
			ps.setInt( param++, codprod );
			if (estoqalmox) {
				ps.setInt( param++, getCodemp() );
				ps.setInt( param++, codfilialax );
				if (almoxpdorc && codalmox!=null) {
					ps.setInt( param++, codalmox );;
				}
			}
			ResultSet rs = ps.executeQuery();
			BigDecimal qtdafatcalc = qtdafat;
			while (rs.next()) {
				SaldoProd saldoprod = new SaldoProd();
				saldoprod.setCodemp( getCodemp() );
				saldoprod.setCodfilial( getCodfilial() );
				saldoprod.setCodfilialax( codfilialax );
				if (!estoqalmox) {
					saldoprod.setCodalmox( codalmox );
				} else {
					saldoprod.setCodalmox( rs.getInt( "codalmox" ));
				}
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
		}
		// Caso o ResultSet não retorne saldo do produto.
		if (result.size()==0) {
			SaldoProd saldoprod = new SaldoProd();
			saldoprod.setCodemp( getCodemp() );
			saldoprod.setCodfilial( getCodfilial() );
			saldoprod.setCodfilialax( codfilialax );
			saldoprod.setCodalmox( new Integer(0) );
			saldoprod.setCodprod( codprod );
			saldoprod.setVlrdesc( vlrdesc );
			saldoprod.setQtd( qtd );
			saldoprod.setPreco( preco );
			saldoprod.setSaldoprod( BigDecimal.ZERO );
			saldoprod.setQtdafat(qtdafat);
			saldoprod.setCodlote( "" );
			result.addElement( saldoprod );
		}
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
				BigDecimal vlrdescunit = vlrdesc.divide( qtd, 5, BigDecimal.ROUND_HALF_UP );
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
