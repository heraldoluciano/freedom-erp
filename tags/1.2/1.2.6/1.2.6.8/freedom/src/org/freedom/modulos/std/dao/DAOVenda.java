package org.freedom.modulos.std.dao;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.freedom.infra.dao.AbstractDAO;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.functions.Funcoes;
import org.freedom.modulos.std.business.component.Venda.POS_PREFS;
import org.freedom.modulos.std.business.object.CabecalhoVenda;
import org.freedom.modulos.std.business.object.ItemVenda;
import org.freedom.modulos.std.business.object.UpdateVenda;
import org.freedom.modulos.std.business.object.VdItVendaItVenda;


public class DAOVenda extends AbstractDAO {
	
	public DAOVenda( DbConnection cn) {

		super( cn );

	}

	public DAOVenda( DbConnection cn, Integer codemp, Integer codfilial) {
		this( cn );
		setCodemp( codemp );
		setCodfilial( codfilial );
	}
	
	public CabecalhoVenda getCabecalhoVenda (Integer codemp, Integer codfilial, Integer codvenda) throws Exception {
		
		StringBuilder sql = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		CabecalhoVenda cabecalho = null;
		
		try{
	
			sql = new StringBuilder();
			sql.append( "SELECT VD.CODTIPOMOV, VD.CODCLI, VD.CODPLANOPAG, VD.CODVEND, VD.CODCLCOMIS " );
			sql.append( "FROM VDVENDA VD " );
			sql.append( "WHERE CODEMP=? AND CODFILIAL=? AND CODVENDA=? " );
			ps = getConn().prepareStatement( sql.toString() );
			int param = 1;
			
			ps.setInt( param++, codemp );
			ps.setInt( param++, codfilial );
			ps.setInt( param++, codvenda );
			
			rs = ps.executeQuery();
			
			if (rs.next()) {
				
				cabecalho = new CabecalhoVenda();
				cabecalho.setCodtipomov( rs.getInt( "CODTIPOMOV" ) );
				cabecalho.setCodcli( rs.getInt( "CODCLI" ) );
				cabecalho.setCodplanopag( rs.getInt("CODPLANOPAG") );
				cabecalho.setCodvend( rs.getInt( "CODVEND" ) );
				cabecalho.setCodclcomis( rs.getInt( "CODCLCOMIS" ) );
				
			}
			
		}catch (SQLException e) {
			Funcoes.mensagemErro( null, "Não foi possivel encontrar Venda anterior!" );
			e.printStackTrace();
			throw e;
		}
	
		return cabecalho;
	}
	
	public ArrayList<ItemVenda> getItensVenda (Integer codemp, Integer codfilial, Integer codvenda) {
		ArrayList<ItemVenda> itens = new ArrayList<ItemVenda>();
		
		StringBuilder sql = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		CabecalhoVenda cabecalho = null;
		
		try{
	
			sql = new StringBuilder();
			sql.append( "SELECT IV.CODPROD, IV.REFPROD, IV.PRECOITVENDA, IV.QTDITVENDA, IV.PERCDESCITVENDA, IV.VLRDESCITVENDA, IV.CODLOTE " );
			sql.append( "FROM VDITVENDA IV " );
			sql.append( "WHERE IV.CODEMP=? AND IV.CODFILIAL=? AND IV.CODVENDA=? " );
			sql.append( "ORDER BY IV.CODITVENDA ");
			ps = getConn().prepareStatement( sql.toString() );
			int param = 1;
			
			ps.setInt( param++, codemp );
			ps.setInt( param++, codfilial );
			ps.setInt( param++, codvenda );
			
			rs = ps.executeQuery();
			
			while (rs.next()) {
				
				ItemVenda item = new ItemVenda();
				item.setCodprod(  rs.getInt( "CODPROD" ) );
				item.setRefProd( getString( rs.getString( "REFPROD" ) ));
				item.setPrecoprod( getBigDecimal( rs.getBigDecimal( "PRECOITVENDA") ) );
				item.setQtdprod( getBigDecimal( rs.getBigDecimal( "QTDITVENDA" ) ) );
				//item.setPercdesc( getBigDecimal(  rs.getBigDecimal( "PERCDESCITVENDA" ) ) );
				item.setVlrdesc( getBigDecimal(  rs.getBigDecimal( "VLRDESCITVENDA" ) ) );
				item.setCodlote( getString(  rs.getString( "CODLOTE" ) ) );
				
				itens.add( item );
			}
			
		}catch (SQLException e) {
			Funcoes.mensagemErro( null, "Não foi possivel encontrar os itens da venda anterior!" );
			e.printStackTrace();
		}
		return itens;
	}
	
	public boolean insereItvendaItvenda(int codempvo, int codfilialvo, String tipovendavo, int codvendavo, int codvenda, int coditvenda, BigDecimal qtditvenda ) throws SQLException {
		boolean result = false;
		StringBuilder sql = new StringBuilder("insert into vditvendaitvenda ");
		sql.append(" (id, codemp, codfilial, tipovenda, codvenda, coditvenda, codempvo, codfilialvo, tipovendavo, codvendavo, coditvendavo, qtditvenda ) ");
		sql.append(" values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
		int param = 1;
		int id = geraSeqId("VDITVENDAITVENDA");
		PreparedStatement ps = getConn().prepareStatement( sql.toString() );
		
		ps.setInt( param++, id);
		ps.setInt( param++, codempvo );  // codemp nova venda o mesmo da original
		ps.setInt( param++, codfilialvo ); // codfilial nova venda  o mesmo da original
		ps.setString( param++, tipovendavo ); // tipo da nova venda o mesmo da original
		ps.setInt( param++, codvenda ); // codvenda da nova venda recebido como parãmetro 
		ps.setInt( param++, coditvenda ); // coditvenda nova venda sequencial 
		ps.setInt( param++, codempvo );  // codemp  venda original
		ps.setInt( param++, codfilialvo ); // codfilial venda original
		ps.setString( param++, tipovendavo ); // codfilial venda original
		ps.setInt( param++, codvendavo ); // codvenda original ;
		ps.setInt( param++, coditvenda ); // coditvenda original ;
		ps.setBigDecimal( param++, getBigDecimal( qtditvenda ));
		
		result = ps.execute();
		ps.close();
		       
		return result;
	}
	
	
	public VdItVendaItVenda getAmarracao(Integer codemp, Integer codfilial, String tipovenda,  Integer codvenda){
		
		StringBuilder sql = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		VdItVendaItVenda vendaitvenda = null;
		
		try{
	
			sql = new StringBuilder();
			sql.append( "SELECT VD.CODEMP, VD.CODFILIAL, VD.TIPOVENDA, VD.CODVENDA, " );
			sql.append( "VD.CODEMPVO, VD.CODFILIALVO, VD.TIPOVENDAVO, VD.CODVENDAVO " );
			sql.append( "FROM vditvendaitvenda VD " );
			sql.append( "WHERE VD.CODEMP=? AND VD.CODFILIAL=? AND VD.TIPOVENDA=? AND VD.CODVENDA=? " );
			ps = getConn().prepareStatement( sql.toString() );
			int param = 1;
			
			ps.setInt( param++, codemp );
			ps.setInt( param++, codfilial );
			ps.setString( param++, tipovenda);
			ps.setInt( param++, codvenda );
			rs = ps.executeQuery();
			
			if (rs.next()) {
				
				vendaitvenda = new VdItVendaItVenda();
				vendaitvenda.setCodemp( rs.getInt( "CODEMP" ) );
				vendaitvenda.setCodfilial( rs.getInt( "CODFILIAL" ) );
				vendaitvenda.setTipovenda( rs.getString( "TIPOVENDA" ) );
				vendaitvenda.setCodvenda( rs.getInt( "CODVENDA" ) );
				vendaitvenda.setCodempvo( rs.getInt( "CODEMPVO" ) );
				vendaitvenda.setCodfilialvo( rs.getInt( "CODFILIALVO" ) );
				vendaitvenda.setTipovendavo( rs.getString( "TIPOVENDAVO" ) );
				vendaitvenda.setCodvendavo( rs.getInt( "CODVENDAVO" ) );
			}
			
			rs.close();
			ps.close();
			
		}catch (SQLException e) {
			Funcoes.mensagemErro( null, "Não foi possivel encontrar Venda anterior!" );
			e.printStackTrace();
		}
	
		
		
		return vendaitvenda;
		
	}
	
	
	public UpdateVenda getValoresCabecalhoVenda( int codemp, int codfilial, String tipovenda, int codvenda ) {
		
		StringBuilder sql = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		UpdateVenda valores = null;

		try{
			sql = new StringBuilder();
			sql.append( "select v.vlrprodvenda  ");
			sql.append( ", v.vlrliqvenda ");
			sql.append( ", v.vlradicvenda ");
			sql.append( ", v.vlrdescvenda ");
			sql.append( ", v.vlrdescitvenda ");
			sql.append( "from  vdvenda v ");
			sql.append( "where v.codemp=? and v.codfilial=? "); 
			sql.append( "and v.tipovenda=? and v.codvenda=? ");
			ps = getConn().prepareStatement( sql.toString() );
			int param = 1;
			
			ps.setInt( param++, codemp );
			ps.setInt( param++, codfilial );
			ps.setString( param++, tipovenda );
			ps.setInt( param++, codvenda);
			
			rs = ps.executeQuery();
			
			if(rs.next()){
				valores = new UpdateVenda();
				valores.setVlradicvenda( rs.getBigDecimal( "vlradicvenda" ) );
				valores.setVlrdescvenda( rs.getBigDecimal( "vlrdescvenda" ) );
				valores.setVlrliqprodvenda( rs.getBigDecimal( "vlrliqvenda" ) );
				valores.setVlrprodvenda( rs.getBigDecimal( "vlrprodvenda" ) );
				valores.setVlrdescitvenda( rs.getBigDecimal("vlrdescitvenda") );
			}
			
			getConn().commit();
		
		} catch (SQLException e) {
			Funcoes.mensagemErro( null, "Erro ao realizar update da nota complementar" );
			e.printStackTrace();
		}
		
		
		return valores;
	}

	
	
	public void updateNotaComplementar( int codempvo, int codfilialvo, String tipovendavo, int codvendavo, int codvenda ) {
	
		StringBuilder sql = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try{
			sql = new StringBuilder();
			sql.append( "select v1.coditvenda coditvenda ");
			sql.append( ", coalesce(v1.qtditvenda,0)-coalesce(v2.qtditvenda,0) qtditvenda ");
			sql.append( ", coalesce(v1.precoitvenda,0)-coalesce(v2.precoitvenda,0) precoitvenda ");
			sql.append( ", 0 vlrdescitvenda ");
			sql.append( ", coalesce(v1.vlrbaseicmsitvenda,0)-coalesce(v2.vlrbaseicmsitvenda,0) vlrbaseicmsitvenda ");
			sql.append( ", coalesce(v1.vlricmsitvenda,0)-coalesce(v2.vlricmsitvenda,0) vlricmsitvenda ");
			sql.append( ", coalesce(v1.vlrbaseipiitvenda,0)-coalesce(v2.vlrbaseipiitvenda,0) vlrbaseipiitvenda ");
			sql.append( ", coalesce(v1.vlripiitvenda,0)-coalesce(v2.vlripiitvenda,0) vlripiitvenda ");
			sql.append( ", coalesce(v1.vlrliqitvenda,0)-coalesce(v2.vlrliqitvenda,0) vlrliqitvenda ");
			sql.append( ", 0 vlrcomisitvenda ");
			sql.append( ", coalesce(v1.vlradicitvenda,0)-coalesce(v2.vlradicitvenda,0) vlradicitvenda ");
			sql.append( ", coalesce(v1.vlrissitvenda,0)-coalesce(v2.vlrissitvenda,0) vlrissitvenda ");
			sql.append( ", coalesce(v1.vlrfreteitvenda,0)-coalesce(v2.vlrfreteitvenda,0) vlrfreteitvenda "); 
			sql.append( ", coalesce(v1.vlrproditvenda,0)-coalesce(v2.vlrproditvenda,0) vlrproditvenda ");
			sql.append( ", coalesce(v1.vlrisentasitvenda,0)-coalesce(v2.vlrisentasitvenda,0) vlrisentasitvenda ");
			sql.append( ", coalesce(v1.vlroutrasitvenda,0)-coalesce(v2.vlroutrasitvenda,0) vlroutrasitvenda ");
			sql.append( ", coalesce(v1.vlrbaseissitvenda,0)-coalesce(v2.vlrbaseissitvenda,0) vlrbaseissitvenda ");
			sql.append( ", coalesce(v1.vlrbaseicmsbrutitvenda,0)-coalesce(v2.vlrbaseicmsbrutitvenda,0) vlrbaseicmsbrutitvenda ");
			sql.append( ", coalesce(v1.vlrbaseicmsstitvenda,0)-coalesce(v2.vlrbaseicmsstitvenda,0) vlrbaseicmsstitvenda ");
			sql.append( ", coalesce(v1.vlricmsstitvenda,0)-coalesce(v2.vlricmsstitvenda,0) vlricmsstitvenda ");
			sql.append( ", coalesce(v1.vlrbasecomisitvenda,0)-coalesce(v2.vlrbasecomisitvenda,0) vlrbasecomisitvenda ");
			sql.append( "from vditvendaitvenda vv,  vditvenda v1, vditvenda v2 ");
			sql.append( "where v1.codemp=vv.codemp and v1.codfilial=vv.codfilial "); 
			sql.append( "and v1.tipovenda=vv.tipovenda and v1.codvenda=vv.codvenda and v1.coditvenda=vv.coditvenda ");
			sql.append( "and v2.codemp=vv.codempvo and v2.codfilial=vv.codfilialvo "); 
			sql.append( "and v2.tipovenda=vv.tipovendavo and v2.codvenda=vv.codvendavo and v2.coditvenda=vv.coditvendavo ");
			sql.append( "and vv.codemp=? and vv.codfilial=? and vv.tipovenda=? and vv.codvenda=? "); 
			sql.append( "and vv.codempvo=? and vv.codfilialvo=? and vv.tipovendavo=? and vv.codvendavo=? ");
			sql.append( "order by vv.codvenda, vv.coditvenda ");
			ps = getConn().prepareStatement( sql.toString() );
			int param = 1;
			
			ps.setInt( param++, codempvo );
			ps.setInt( param++, codfilialvo );
			ps.setString( param++, tipovendavo );
			ps.setInt( param++, codvenda);
			ps.setInt( param++, codempvo );
			ps.setInt( param++, codfilialvo );
			ps.setString( param++, tipovendavo );
			ps.setInt( param++, codvendavo);
			
			rs = ps.executeQuery();
			
			while(rs.next()){
				sql = new StringBuilder();
				sql.append( "update vditvenda v1 set ");  
				sql.append( "v1.qtditvenda=? ");
				sql.append( ", v1.precoitvenda=? ");
				sql.append( ", v1.vlrdescitvenda=? ");
				sql.append( ", v1.vlrbaseicmsitvenda=? ");  
				sql.append( ", v1.vlricmsitvenda=? ");  
				sql.append( ", v1.vlrbaseipiitvenda=? ");  
				sql.append( ", v1.vlripiitvenda=? ");  
				sql.append( ", v1.vlrliqitvenda=? ");  
				sql.append( ", v1.vlrcomisitvenda=? ");  
				sql.append( ", v1.vlradicitvenda=? ");  
				sql.append( ", v1.vlrissitvenda=? ");  
				sql.append( ", v1.vlrfreteitvenda=? ");  
				sql.append( ", v1.vlrproditvenda=? ");  
				sql.append( ", v1.vlrisentasitvenda=? ");  
				sql.append( ", v1.vlroutrasitvenda=? ");  
				sql.append( ", v1.vlrbaseissitvenda=? ");  
				sql.append( ", v1.vlrbaseicmsbrutitvenda=? ");  
				sql.append( ", v1.vlrbaseicmsstitvenda=? ");  
				sql.append( ", v1.vlricmsstitvenda=? ");  
				sql.append( ", v1.vlrbasecomisitvenda=? ");  
				sql.append( "where v1.codemp=? and v1.codfilial=? ");   
				sql.append( "and v1.tipovenda=? and v1.codvenda=? and v1.coditvenda=? ");
				PreparedStatement ps2 = getConn().prepareStatement( sql.toString() );
				int param2 = 1;
				
				ps2.setBigDecimal( param2++, rs.getBigDecimal( "qtditvenda" ) );
				ps2.setBigDecimal( param2++, rs.getBigDecimal( "precoitvenda" ) );
				ps2.setBigDecimal( param2++, rs.getBigDecimal( "vlrdescitvenda" ) );
				ps2.setBigDecimal( param2++, rs.getBigDecimal( "vlrbaseicmsitvenda" ) );
				ps2.setBigDecimal( param2++, rs.getBigDecimal( "vlricmsitvenda" ) );
				ps2.setBigDecimal( param2++, rs.getBigDecimal( "vlrbaseipiitvenda" ) );
				ps2.setBigDecimal( param2++, rs.getBigDecimal( "vlripiitvenda" ) );
				ps2.setBigDecimal( param2++, rs.getBigDecimal( "vlrliqitvenda" ) );
				ps2.setBigDecimal( param2++, rs.getBigDecimal( "vlrcomisitvenda" ) );
				ps2.setBigDecimal( param2++, rs.getBigDecimal( "vlradicitvenda" ) );
				ps2.setBigDecimal( param2++, rs.getBigDecimal( "vlrissitvenda" ) );
				ps2.setBigDecimal( param2++, rs.getBigDecimal( "vlrfreteitvenda" ) );
				ps2.setBigDecimal( param2++, rs.getBigDecimal( "vlrproditvenda" ) );
				ps2.setBigDecimal( param2++, rs.getBigDecimal( "vlrisentasitvenda" ) );
				ps2.setBigDecimal( param2++, rs.getBigDecimal( "vlroutrasitvenda" ) );
				ps2.setBigDecimal( param2++, rs.getBigDecimal( "vlrbaseissitvenda" ) );
				ps2.setBigDecimal( param2++, rs.getBigDecimal( "vlrbaseicmsbrutitvenda" ) );
				ps2.setBigDecimal( param2++, rs.getBigDecimal( "vlrbaseicmsstitvenda" ) );
				ps2.setBigDecimal( param2++, rs.getBigDecimal( "vlricmsstitvenda" ) );
				ps2.setBigDecimal( param2++, rs.getBigDecimal( "vlrbasecomisitvenda" ) );
				
				
				ps2.setInt( param2++, codempvo );
				ps2.setInt( param2++, codfilialvo );
				ps2.setString( param2++, tipovendavo );
				ps2.setInt( param2++, codvenda);
				ps2.setInt( param2++, rs.getInt( "coditvenda" )  );
				ps2.execute();
				
			}
			
			/*sql.delete( 0, sql.length() );
			sql.append( "update vdvenda set vlrdescvenda=0, vlrdescitvenda=0 ");
			sql.append( "where codemp=? and codfilial=? and tipovenda=? and codvenda=?");
			PreparedStatement ps3 = getConn().prepareStatement( sql.toString() );
			param = 1;
			ps3.setInt( param++, codempvo );
			ps3.setInt( param++, codfilialvo );
			ps3.setString( param++, tipovendavo );
			ps3.setInt( param++, codvenda );
			ps3.executeUpdate();
*/
			getConn().commit();
		
		} catch (SQLException e) {
			Funcoes.mensagemErro( null, "Erro ao realizar update da nota complementar" );
			e.printStackTrace();
		}
	}

	
	public Integer geraSeqId(String tabela) throws SQLException{
		
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
	
	public Object[] getPrefs(Integer codfilialpf1) throws Exception {

		Object[] result = new Object[ POS_PREFS.values().length ];
		StringBuffer sql = new StringBuffer();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			sql.append( "select p1.usarefprod, p1.usapedseq, p1.usaliqrel, p1.tipoprecocusto, p1.ordnota, p1.usaprecozero " );
			sql.append( ", p1.usaclascomis, p1.travatmnfvd, p1.natvenda, p1.ipivenda, p1.bloqvenda, p1.vendamatprim, p1.desccompped " );
			sql.append( ", p1.tamdescprod, p1.obsclivend, p1.contestoq, p1.diaspedt, p1.recalcpcvenda, p1.usalayoutped " );
			sql.append( ", p1.icmsvenda, p1.multicomis, p1.tipoprefcred, p1.tipoclassped, p1.vendapatrim, p1.visualizalucr " );
			sql.append( ", p1.infcpdevolucao, p1.infvdremessa, p1.tipocustoluc, p1.buscacodprodgen, p1.codplanopagsv " );
			sql.append( ", p1.comissaodesconto, p8.codtipomovds, p1.vendaconsum, p1.obsitvendaped, p1.bloqseqivd, p1.localserv ");
			sql.append( ", p1.vdprodqqclas, p1.consistendentvd, p1.bloqdesccompvd, p1.bloqprecovd, p1.bloqcomissvd ");
			sql.append( ", p1.bloqpedvd, p1.soldtsaida, coalesce(p1.proceminfe,'3') proceminfe, coalesce(p1.ambientenfe,'2') ambientenfe " );
			sql.append( ", f.cnpjfilial, f.siglauf, coalesce(p1.tipoemissaonfe,'1') tipoemissaonfe, coalesce(p1.bloqnfevdautoriz,'S') bloqnfevdautoriz " );
			sql.append( "from sgprefere1 p1 ");
			sql.append( "inner join sgfilial f ");
			sql.append( "on f.codemp=p1.codemp and f.codfilial=p1.codfilial ");
			sql.append( "left outer join sgprefere8 p8 on " );
			sql.append( "p1.codemp=p8.codemp and p1.codfilial=p8.codfilial " );
			sql.append( "where p1.codemp=? and p1.codfilial=? " );
			ps = getConn().prepareStatement( sql.toString() );
			int param = 1;
			ps.setInt( param++, getCodemp() );
			ps.setInt( param++, codfilialpf1 );
			rs = ps.executeQuery();
			if ( rs.next() ) {
				result[ POS_PREFS.USAREFPROD.ordinal() ] = "S".equals( rs.getString( "USAREFPROD" ) );
				result[ POS_PREFS.USAPEDSEQ.ordinal() ] = "S".equals( rs.getString( "USAPEDSEQ" ) );
				if ( rs.getString( "UsaLiqRel" ) == null ) {
					throw new Exception( "Preencha opção de desconto em preferências!" );
				}
				else {
					result[ POS_PREFS.USALIQREL.ordinal() ] = "S".equals( rs.getString( "UsaLiqRel" ) );
					result[ POS_PREFS.ORDNOTA.ordinal() ] = rs.getString( "OrdNota" );
					result[ POS_PREFS.TIPOPRECOCUSTO.ordinal() ] = "S".equals( rs.getString( "TipoPrecoCusto" ) );
					result[ POS_PREFS.USACLASCOMIS.ordinal() ] = "S".equals( rs.getString( "UsaClasComis" ) );
				}
				result[ POS_PREFS.TRAVATMNFVD.ordinal() ] = "S".equals( rs.getString( "TravaTmNfVd" ) );
				result[ POS_PREFS.NATVENDA.ordinal() ] = "S".equals( rs.getString( "NatVenda" ) );
				result[ POS_PREFS.BLOQVENDA.ordinal() ] = "S".equals( rs.getString( "BloqVenda" ) );
				result[ POS_PREFS.VENDAMATPRIM.ordinal() ] = "S".equals( rs.getString( "VendaMatPrim" ) );
				result[ POS_PREFS.DESCCOMPPED.ordinal() ] = "S".equals( rs.getString( "DescCompPed" ) );
				result[ POS_PREFS.TAMDESCPROD.ordinal() ] = "S".equals( rs.getString( "TAMDESCPROD" ) );
				result[ POS_PREFS.OBSCLIVEND.ordinal() ] = "S".equals( rs.getString( "OBSCLIVEND" ) );
				result[ POS_PREFS.IPIVENDA.ordinal() ] = "S".equals( rs.getString( "IPIVenda" ) );
				result[ POS_PREFS.CONTESTOQ.ordinal() ] = "S".equals( rs.getString( "CONTESTOQ" ) );
				result[ POS_PREFS.DIASPEDT.ordinal() ] = "S".equals( rs.getString( "DIASPEDT" ) );
				result[ POS_PREFS.RECALCCPVENDA.ordinal() ] = "S".equals( rs.getString( "RECALCPCVENDA" ) );
				result[ POS_PREFS.USALAYOUTPED.ordinal() ] = "S".equals( rs.getString( "USALAYOUTPED" ) );
				result[ POS_PREFS.ICMSVENDA.ordinal() ] = "S".equals( rs.getString( "ICMSVENDA" ) );
				result[ POS_PREFS.USAPRECOZERO.ordinal() ] = "S".equals( rs.getString( "USAPRECOZERO" ) );
				result[ POS_PREFS.MULTICOMIS.ordinal() ] = "S".equals( rs.getString( "MULTICOMIS" ) );
				result[ POS_PREFS.CONS_CRED_FECHA.ordinal() ] = ( "FV".equals( rs.getString( "TIPOPREFCRED" ) ) || "AB".equals( rs.getString( "TIPOPREFCRED" ) ) );
				result[ POS_PREFS.CONS_CRED_ITEM.ordinal() ] = ( "II".equals( rs.getString( "TIPOPREFCRED" ) ) || "AB".equals( rs.getString( "TIPOPREFCRED" ) ) );
				result[ POS_PREFS.CLASPED.ordinal() ] = rs.getString( "TIPOCLASSPED" );				
				result[ POS_PREFS.VENDAIMOBILIZADO.ordinal() ] = "S".equals( rs.getString( "VENDAPATRIM" ) );
				result[ POS_PREFS.VISUALIZALUCR.ordinal() ] = "S".equals( rs.getString( "VISUALIZALUCR" ) );
				result[ POS_PREFS.INFCPDEVOLUCAO.ordinal() ] = "S".equals( rs.getString( "INFCPDEVOLUCAO" ) );
				result[ POS_PREFS.INFVDREMESSA.ordinal() ] = "S".equals( rs.getString( "INFVDREMESSA" ) );
				result[ POS_PREFS.TIPOCUSTO.ordinal() ] = rs.getString( "TIPOCUSTOLUC" );
				result[ POS_PREFS.BUSCACODPRODGEN.ordinal() ] = "S".equals( rs.getString( "BUSCACODPRODGEN" ) );
				result[ POS_PREFS.CODPLANOPAGSV.ordinal() ] = rs.getInt( "CODPLANOPAGSV" );
				result[ POS_PREFS.CODTIPOMOVDS.ordinal() ] = rs.getInt( "CODTIPOMOVDS" );
				result[ POS_PREFS.COMISSAODESCONTO.ordinal() ] = "S".equals( rs.getString( "COMISSAODESCONTO" ) );
				result[ POS_PREFS.VENDAMATCONSUM.ordinal()] = "S".equals( rs.getString( "VENDACONSUM" ) );
				result[ POS_PREFS.OBSITVENDAPED.ordinal()] = "S".equals( rs.getString(POS_PREFS.OBSITVENDAPED.toString() ) );
				result[ POS_PREFS.BLOQSEQIVD.ordinal()] = "S".equals( rs.getString( "BLOQSEQIVD" ) );
				result[ POS_PREFS.VDPRODQQCLAS.ordinal()] = "S".equals( rs.getString( POS_PREFS.VDPRODQQCLAS.toString() ) );
				result[ POS_PREFS.CONSISTENDENTVD.ordinal()] = "S".equals( rs.getString( POS_PREFS.CONSISTENDENTVD.toString() ) );
				result[ POS_PREFS.BLOQDESCCOMPVD.ordinal()] = "S".equals( rs.getString( POS_PREFS.BLOQDESCCOMPVD.toString() ) );
				result[ POS_PREFS.BLOQPRECOVD.ordinal()] = "S".equals( rs.getString( POS_PREFS.BLOQPRECOVD.toString() ) );
				result[ POS_PREFS.BLOQCOMISSVD.ordinal()] = "S".equals( rs.getString( POS_PREFS.BLOQCOMISSVD.toString() ) );
				result[ POS_PREFS.BLOQPEDVD.ordinal()] = "S".equals( rs.getString( POS_PREFS.BLOQPEDVD.toString() ) );
				result[ POS_PREFS.SOLDTSAIDA.ordinal()] = "S".equals( rs.getString( POS_PREFS.SOLDTSAIDA.toString() ) );
				result[ POS_PREFS.PROCEMINFE.ordinal()] = rs.getString( POS_PREFS.PROCEMINFE.toString() ); 
				result[ POS_PREFS.AMBIENTENFE.ordinal()] = rs.getString( POS_PREFS.AMBIENTENFE.toString() ); 
				result[ POS_PREFS.CNPJFILIAL.ordinal()] = rs.getString( POS_PREFS.CNPJFILIAL.toString() );
				result[ POS_PREFS.SIGLAUF.ordinal()] = rs.getString( POS_PREFS.SIGLAUF.toString() );
				result[ POS_PREFS.TIPOEMISSAONFE.ordinal()] = rs.getString( POS_PREFS.TIPOEMISSAONFE.toString() );
				result[ POS_PREFS.BLOQNFEVDAUTORIZ.ordinal()] = rs.getString( POS_PREFS.BLOQNFEVDAUTORIZ.toString() );
				result[ POS_PREFS.LOCALSERV.ordinal() ] = rs.getString( "LOCALSERV" );
			}
			rs.close();
			ps.close();
			getConn().commit();
		} catch ( SQLException err ) {
			err.printStackTrace();
			try {
				getConn().rollback();
			} catch (SQLException errroll) {
				errroll.printStackTrace();
			}
			throw new Exception( "Erro ao carregar a tabela preferências!\n" + err.getMessage() );
		} finally {
			rs = null;
			ps = null;
		}
		return result;
	}

}