package org.freedom.modulos.std.dao;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.freedom.infra.dao.AbstractDAO;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.functions.Funcoes;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.frame.Aplicativo;
import org.freedom.modulos.std.business.object.CabecalhoVenda;
import org.freedom.modulos.std.business.object.ClienteFor;
import org.freedom.modulos.std.business.object.ItemVenda;
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


