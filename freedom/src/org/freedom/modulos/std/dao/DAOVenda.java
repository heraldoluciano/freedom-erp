package org.freedom.modulos.std.dao;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import org.freedom.infra.dao.AbstractDAO;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.functions.Funcoes;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.frame.Aplicativo;
import org.freedom.modulos.gms.view.frame.crud.plain.FBuscaCpCompl.enum_itcompra;
import org.freedom.modulos.std.business.object.CabecalhoVenda;
import org.freedom.modulos.std.business.object.ClienteFor;
import org.freedom.modulos.std.business.object.ItemVenda;
import org.freedom.modulos.std.business.object.UpdateVenda;
import org.freedom.modulos.std.business.object.VdItVendaItVenda;
import org.freedom.modulos.std.business.object.ClienteFor.INSERE_CLI_FOR;
import org.freedom.modulos.std.business.object.ClienteFor.INSERE_FOR;

import com.sun.xml.internal.bind.v2.schemagen.xmlschema.List;


public class DAOVenda extends AbstractDAO {
	
	public DAOVenda( DbConnection cn) {

		super( cn );

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
			sql.append( "SELECT IV.CODPROD, IV.REFPROD, IV.PRECOITVENDA, IV.QTDITVENDA, IV.PERCDESCITVENDA, IV.CODLOTE " );
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
				item.setPercprod( getBigDecimal(  rs.getBigDecimal( "PERCDESCITVENDA" ) ) );
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
			}
			
			getConn().commit();
		
		} catch (SQLException e) {
			Funcoes.mensagemErro( null, "Erro ao realizar update da nota complementarss" );
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
			sql.append( ", v1.qtditvenda-v2.qtditvenda qtditvenda ");
			sql.append( ", v1.precoitvenda-v2.precoitvenda precoitvenda ");
			sql.append( ", v1.vlrdescitvenda-v2.vlrdescitvenda vlrdescitvenda ");
			sql.append( ", v1.vlrbaseicmsitvenda-v2.vlrbaseicmsitvenda vlrbaseicmsitvenda ");
			sql.append( ", v1.vlricmsitvenda-v2.vlricmsitvenda vlricmsitvenda ");
			sql.append( ", v1.vlrbaseipiitvenda-v2.vlrbaseipiitvenda vlrbaseipiitvenda ");
			sql.append( ", v1.vlripiitvenda-v2.vlripiitvenda vlripiitvenda ");
			sql.append( ", v1.vlrliqitvenda-v2.vlrliqitvenda vlrliqitvenda ");
			sql.append( ", v1.vlrcomisitvenda-v2.vlrcomisitvenda vlrcomisitvenda ");
			sql.append( ", v1.vlradicitvenda-v2.vlradicitvenda vlradicitvenda ");
			sql.append( ", v1.vlrissitvenda-v2.vlrissitvenda vlrissitvenda ");
			sql.append( ", v1.vlrfreteitvenda-v2.vlrfreteitvenda vlrfreteitvenda "); 
			sql.append( ", v1.vlrproditvenda-v2.vlrproditvenda vlrproditvenda ");
			sql.append( ", v1.vlrisentasitvenda-v2.vlrisentasitvenda vlrisentasitvenda ");
			sql.append( ", v1.vlroutrasitvenda-v2.vlroutrasitvenda vlroutrasitvenda ");
			sql.append( ", v1.vlrbaseissitvenda-v2.vlrbaseissitvenda vlrbaseissitvenda ");
			sql.append( ", v1.vlrbaseicmsbrutitvenda-v2.vlrbaseicmsbrutitvenda vlrbaseicmsbrutitvenda ");
			sql.append( ", v1.vlrbaseicmsstitvenda-v2.vlrbaseicmsstitvenda vlrbaseicmsstitvenda ");
			sql.append( ", v1.vlricmsstitvenda-v2.vlricmsstitvenda vlricmsstitvenda ");
			sql.append( ", v1.vlrbasecomisitvenda-v2.vlrbasecomisitvenda vlrbasecomisitvenda ");
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
	
	
	private String getString( String value ){
		String result = null;
		
		if (value == null){
			result = "";
		} else {
			result = value;
		}
		return result;
	}	
	
	private Integer getInteger( Integer value ) {
		Integer result = null;
		
		if (value == null){
			result = new Integer( 0 );
		} else {
			result = value;
		}
		return result;
	}
	
	private BigDecimal getBigDecimal( BigDecimal value ) {
		BigDecimal result = null;
		
		if (value == null){
			result = BigDecimal.ZERO;
		} else {
			result = value;
		}
		return result;
	}

}


