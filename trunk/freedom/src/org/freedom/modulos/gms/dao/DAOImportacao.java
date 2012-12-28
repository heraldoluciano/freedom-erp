package org.freedom.modulos.gms.dao;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import org.freedom.infra.dao.AbstractDAO;
import org.freedom.infra.functions.ConversionFunctions;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.functions.Funcoes;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.frame.Aplicativo;
import org.freedom.modulos.gms.view.frame.crud.detail.FImportacao;
import org.freedom.modulos.gms.view.frame.crud.detail.FImportacao.GRID_ADICAO;


public class DAOImportacao extends AbstractDAO {
		
	public DAOImportacao( DbConnection cn ) {
		super(cn);		
	}
	
	public PreparedStatement getStatementItensImportacao(String utilizatbcalcca) {

		StringBuilder sql = new StringBuilder();
		PreparedStatement ps = null;

		try {
			sql.append( "select " );

			sql.append( "ii.coditimp, ii.codemppd, ii.codfilialpd, ii.codprod, ii.refprod, ii.qtd, pd.codalmox, " );
			// Remoção do imposto de importação do valor do produto
			//sql.append( "(ii.vlrad + ii.vlrii + ii.vlripi + ii.vlrpis + ii.vlrcofins + ii.vlrtxsiscomex ) vlrliqitcompra, (ii.vlrad + ii.vlrii ) vlrproditcompra,   ( (ii.vlrad + ii.vlrii ) / qtd) precoitcompra, " );
			sql.append( "cast(cast(cast(ii.vlrad + ii.vlrii + ii.vlripi + ii.vlrpis + ii.vlrcofins + ii.vlrtxsiscomex ");
			sql.append( " + (case when lf.adicicmstotnota='S' then ii.vlricms else 0.00 end)" );
			sql.append( "  as decimal(15,4) ) / ii.qtd as decimal(15,4)) * ii.qtd as decimal(15,4))  vlrliqitcompra ");
			// valor total dos produtos com cálculo para evitar dízima periódica
			sql.append( ", cast(cast( cast(ii.vlrad as decimal(15,2)) / cast(ii.qtd as decimal(15,5)) as decimal(15,4)) * cast(ii.qtd as decimal(15,4)) as decimal(15,2)) vlrproditcompra ");
			// preço do ítem para evitar dízima periódica
			sql.append(" , cast(cast( cast(ii.vlrad as decimal(15,2)) / cast(ii.qtd as decimal(15,5)) as decimal(15,4)) * cast(ii.qtd as decimal(15,4)) as decimal(15,2)) / cast(ii.qtd as decimal(15,5) ) precoitcompra, " );
			
			// Depois da inserção de parâmetro para adicionar ICMS no total da nota ajustar a linha abaixo.
			//sql.append( "(ii.vlrad + ii.vlrii + ii.vlripi + ii.vlrpis + ii.vlrcofins + ii.vlrtxsiscomex + ii.vlricms ) vlrliqitcompra, (ii.vlrad) vlrproditcompra,   ( (ii.vlrad ) / qtd) precoitcompra, " );
			sql.append( "vlrbaseicms," );

			sql.append( "ii.aliqicmsuf, ii.vlricms - coalesce(ii.vlricmsdiferido,0) vlricmsitcompra, ii.vlrfrete vlrfreteitcompra, " );
			sql.append( "(ii.vlrad + ii.vlrii) vlrbaseipiitcompra, ii.aliqipi, ii.vlripi, ii.codfisc, ii.coditfisc, " );
			sql.append( "( select first 1 codadic from cpimportacaoadic where codemp=ii.codemp and codfilial=ii.codfilial and codimp=ii.codimp and codncm=ii.codncm ) nadicao, ii.seqadic, " );
			sql.append( "(ii.vlrpis + ii.vlrcofins + ii.vlrtxsiscomex) vlradicitcompra, " );
			sql.append( "ii.aliqpis		, ii.vlrpis/(ii.aliqpis/100.00) vlrbasepis			, ii.vlrpis, " );
			sql.append( "lf.codempsp	, lf.codfilialsp	, lf.codsittribpis	, lf.impsittribpis, " );
			sql.append( "ii.aliqcofins	, ii.vlrcofins/(ii.aliqcofins/100.00) vlrbasecofins	, ii.vlrcofins, " );
			sql.append( "lf.codempsc	, lf.codfilialsc	, lf.codsittribcof	, lf.impsittribcof, " );
			sql.append( "lf.modbcicms	, lf.redfisc		, lf.origfisc	, lf.codtrattrib, lf.codempsi, lf.codfilialsi, lf.codsittribipi, lf.impsittribipi, " );
			sql.append( "ii.vlrad vlrbaseii	, ii.aliqii		, ii.vlrii, " );
			sql.append( "ii.vlricmsdiferido	, ii.vlricmsrecolhimento , ii.vlricmscredpresum,   ");
			
			// Colocar valor presumido	
			if("S".equals( utilizatbcalcca )){
				sql.append("(select vlrcusto from lfcalccustosp01( lf.codempcc, lf.codfilialcc, lf.codcalc, ii.vlrad, ii.vlricms, ii.vlripi, ii.vlrpis, ii.vlrcofins, 0, 0, ii.vlrii, 0, ii.vlrtxsiscomex, ii.vlricmsdiferido, ii.vlricmscredpresum)) custoitcompra " );
			} else {
				sql.append("((ii.vlrad + ii.vlrii + ii.vlripi + ii.vlrpis + ii.vlrcofins + ii.vlrtxsiscomex ) - ii.vlripi - (ii.vlricms - coalesce(ii.vlricmsdiferido,0) )  ) custoitcompra" );
			}
			sql.append( ", lf.adicicmstotnota, ii.vlritdespad  from eqproduto pd, cpitimportacao ii " );

			sql.append( "left outer join lfitclfiscal lf on lf.codemp=ii.codempcf and lf.codfilial=ii.codfilialcf and lf.codfisc=ii.codfisc and lf.coditfisc=ii.coditfisc " );
			sql.append( "where " );
			sql.append( "pd.codemp=ii.codemppd and pd.codfilial=ii.codfilialpd and pd.codprod=ii.codprod and " );

			sql.append( "ii.codemp=? and ii.codfilial=? and ii.codimp=? " );

			ps = getConn().prepareStatement( sql.toString() );

		} catch ( Exception e ) {
			e.printStackTrace();
		}

		System.out.println( "Query de itens de importação:" + sql.toString() );

		return ps;

	}
	
	public PreparedStatement getStatementInsertCPItCompra() {

		StringBuilder sql = new StringBuilder();
		PreparedStatement ps = null;

		try {

			sql.append( "insert into cpitcompra (" );
			sql.append( "emmanut				, " );
			sql.append( "codemp					, codfilial			, codcompra			, coditcompra		, " );
			sql.append( "codemppd				, codfilialpd		, codprod			, refprod			, " );
			sql.append( "codempnt				, codfilialnt		, codnat			, " );
			sql.append( "codempax				, codfilialax		, codalmox			, " );
			sql.append( "qtditcompra			, precoitcompra		, vlrliqitcompra	, vlrproditcompra	, " );
			sql.append( "vlrbaseicmsitcompra	, percicmsitcompra	, vlricmsitcompra	, vlrfreteitcompra	, " );
			sql.append( "vlrbaseipiitcompra		, percipiitcompra	, vlripiitcompra	, " );
			sql.append( "codempif				, codfilialif		, codfisc			, coditfisc			, " );
			sql.append( "nadicao				, seqadic			, vlradicitcompra   , custoitcompra 	, " );
			sql.append( "vlriiItcompra			, adicicmstotnota   , vlritoutrasdespitcompra " );
			sql.append( ")" );
			sql.append( "values (" );
			sql.append( " ?						, " );
			sql.append( " ?						, ?					, ?					, ?					, " );
			sql.append( " ?						, ?					, ?					, ?					, " );
			sql.append( " ?						, ?					, ?										, " );
			sql.append( " ?						, ?					, ?										, " );
			sql.append( " ?						, ?					, ?					, ?					, " );
			sql.append( " ?						, ?					, ?					, ?					, " );
			sql.append( " ?						, ?					, ?										, " );
			sql.append( " ?						, ?					, ?					, ?					, " );
			sql.append( " ?						, ?					, ?					, ?				    , " );
			sql.append( " ? 					, ?                 , ? " );
			sql.append( ")" );

			ps = getConn().prepareStatement( sql.toString() );

		} catch ( Exception e ) {
			e.printStackTrace();
		}

		System.out.println( "Query de inserção itcompra:" + sql.toString() );

		return ps;

	}
	
	
	public PreparedStatement getStatementInsertLFItCompra() {

		StringBuilder sql = new StringBuilder();
		PreparedStatement ps = null;

		try {

			sql.append( "insert into lfitcompra (" );
			sql.append( "codemp					, codfilial			, codcompra			, coditcompra		, " );
			sql.append( "codempsp				, codfilialsp		, codsittribpis		, impsittribpis		, vlrbasepis	, aliqpis		, vlrpis	, " );
			sql.append( "codempsc				, codfilialsc		, codsittribcof		, impsittribcof		, vlrbasecofins	, aliqcofins	, vlrcofins , " );
			sql.append( "codempsi				, codfilialsi		, codsittribipi		, impsittribipi		, " );
			sql.append( "modbcicms				, aliqredbcicms		, origfisc			, codtrattrib		, vlrbaseii		, aliqii		, vlrii,	  " );
			sql.append( "vlricmsdiferido		, vlricmsdevido		, vlricmscredpresum																	  " );

			sql.append( ")" );
			sql.append( "values (" );
			sql.append( " ?						, ?					, ?					, ?					, " );
			sql.append( " ?						, ?					, ?					, ?					, ?				, ?				, ? 		, " );
			sql.append( " ?						, ?					, ?					, ?					, ?				, ?				, ?			, " );
			sql.append( " ?						, ?					, ?					, ?					, " );
			sql.append( " ?						, ?					, ?					, ?					, ?				, ?				, ?  		, " );
			sql.append( " ?						, ?					, ?																					  " );
			sql.append( ")" );

			ps = getConn().prepareStatement( sql.toString() );

		} catch ( Exception e ) {
			e.printStackTrace();
		}

		System.out.println( "Query de inserção de itens de tributação:" + sql.toString() );

		return ps;

	}
	
	
	public void execRateio(Integer codemp, Integer codfilial, Integer codimp, BigDecimal vlrfretemitot, BigDecimal vlrvmldmitot,
			BigDecimal pesoliq, BigDecimal pesoliqtot, BigDecimal vlrdespad, BigDecimal vlradmitot, BigDecimal vlrthcmitot) throws SQLException {
		
		StringBuilder sql = new StringBuilder();
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		//try {
			
		sql.append( "update cpitimportacao set ");

		// Rateando o Frete pelo peso bruto
		
		sql.append( "vlrfretemi=( ( ? * (pesoliquido) ) / ? )," );

		// Rateando o THC pelo valor + frete
		sql.append( "vlrthcmi=( ( ? * (vmldmi) ) / ? ) " );
		
		sql.append( "where codemp=? and codfilial=? and codimp=?" );

		BigDecimal pesobrutoitem = null;
		
		ps = getConn().prepareStatement( sql.toString() );
		
		ps.setBigDecimal( 1, vlrfretemitot );
		
		//Condição para evitar divisão por 0
		if(pesoliqtot.compareTo( new BigDecimal(0)) > 0 ){
			ps.setBigDecimal( 2, pesoliqtot );
		} else {
			if(pesoliq.compareTo( new BigDecimal(0)) > 0 ){
				ps.setBigDecimal( 2, pesoliq );
			} else {
				ps.setBigDecimal( 2, new BigDecimal(1) );	
			}
		}
		
		
		ps.setBigDecimal( 3, vlrthcmitot );
		
		//Condição para evitar divisão por 0
		if(vlrvmldmitot.compareTo( new BigDecimal(0)) > 0 ){
			ps.setBigDecimal( 4, vlrvmldmitot );
		} else {
			ps.setBigDecimal( 4, new BigDecimal(1) );
		}
		
		ps.setInt( 5, codemp );
		ps.setInt( 6, codfilial );
		ps.setInt( 7, codimp );
		
		ps.execute();
		
		if( vlrdespad.compareTo( new BigDecimal( 0 ) ) > 0 ){
			execRateioDespAD(codemp, codfilial, codimp, vlrfretemitot, vlrvmldmitot, vlrdespad, vlradmitot, vlrthcmitot);
		} else {
			zeraVlrDesp(codimp);	
		}
			
		getConn().commit();
	
	/*	}
		catch (Exception e) {
			Funcoes.mensagemErro( null, "Erro ao realizar o rateio do frete.", false, e );
			e.printStackTrace();
		}*/
	}
	
	private void execRateioDespAD( Integer codemp, Integer codfilial, Integer codimp, BigDecimal vlrfretemitot, BigDecimal vlrvmldmitot,
			 BigDecimal vlrdespad, BigDecimal vlradmitot, BigDecimal vlrthcmitot) throws SQLException {
		
		
		BigDecimal vlrTotDesp = BigDecimal.ZERO;
		BigDecimal diferenca = BigDecimal.ZERO;	

		//atualizaDespAd();
		atualizaDespAd( codimp, vlradmitot, 
				vlrfretemitot, vlrthcmitot, vlrdespad );
		
		
		vlrTotDesp = getTotalDespAd( codemp,  codfilial, codimp  );
		
		diferenca = vlrdespad.subtract( vlrTotDesp );
		
		if( (diferenca.compareTo( BigDecimal.ZERO ) > 0) || (diferenca.compareTo( BigDecimal.ZERO )< 0) ) {
			atualizaDiferenca( codemp, codfilial, codimp, diferenca );
		}
		
	}
	
	
	
	public void atualizaDespAd(Integer codimp, BigDecimal vlradmittot, 
			BigDecimal vlrfreteittot, BigDecimal vlrthcmittot, BigDecimal vlrdespad) throws SQLException{
		
		PreparedStatement ps = null;
		StringBuilder sql = new StringBuilder();
		try {
			sql.append( "update cpitimportacao it set it.VLRITDESPAD = ");
	
			//Rateando desp.Aduaneiras pelo valor do produto + frete + thc
			sql.append("(((it.vlradmi + it.vlrfretemi + it.vlrthcmi ) / (? + ? + ? )) * ?)" );
			
			sql.append(" where it.codemp=? and it.CODFILIAL=? and it.codimp=? ");
			
			ps = getConn().prepareStatement( sql.toString() );
			int param = 1;
		
			//Totalizadores utilizado na contato
			ps.setBigDecimal( param++, vlradmittot );
			ps.setBigDecimal( param++, vlrfreteittot );
			ps.setBigDecimal( param++, vlrthcmittot );
			
			//Valor que será reteado
			ps.setBigDecimal( param++, vlrdespad );
			ps.setInt( param++, Aplicativo.iCodEmp );
			ps.setInt( param++, ListaCampos.getMasterFilial( "CPIMPORTACAO" ) );
			ps.setInt( param++, codimp );
			ps.execute();
		} catch (SQLException e) {
			Funcoes.mensagemErro( null, "Erro ao ratear despesas da compra de importacao!\n" + e.getMessage(), true, getConn(), e );
		} finally {
			ps = null;
		}
	}
	
	
	
	public BigDecimal getTotalDespAd(Integer codemp, Integer codfilial, Integer codimp) {
		
		BigDecimal vlrTotDesp = BigDecimal.ZERO;
		PreparedStatement ps = null;
		ResultSet rs = null;

		StringBuilder sql = new StringBuilder();
		sql.append( "select  SUM(it.vlritdespad) vlrtotdespad from cpitimportacao it where it.codemp=? and it.codfilial=? and it.codimp=? ");
		
		try{
			ps = getConn().prepareStatement( sql.toString() );
			int param = 1;
			ps.setInt( param++, codemp );
			ps.setInt( param++, codfilial );
			ps.setInt( param++, codimp );
			
			rs = ps.executeQuery();
			
			if(rs.next()){
				vlrTotDesp = rs.getBigDecimal( "vlrtotdespad" );
			}
			rs.close();
			ps.close();
		
		} catch (SQLException e) {
			Funcoes.mensagemErro( null, "Erro ao buscar valor total das despesas da compra de importacao!\n" + e.getMessage(), true, getConn(), e );
		} finally {
			rs = null;
			ps = null;
		}
	
		return vlrTotDesp;
	
	}

	public void zeraVlrDesp(Integer codimp){
		PreparedStatement ps = null;
		StringBuilder sql = new StringBuilder();
		try {
			sql.append( "update cpitimportacao it set it.VLRITDESPAD = 0 ");
			sql.append(" where it.codemp=? and it.CODFILIAL=? and it.codimp=? ");
			
			ps = getConn().prepareStatement( sql.toString() );
			int param = 1;
		
			ps.setInt( param++, Aplicativo.iCodEmp );
			ps.setInt( param++, ListaCampos.getMasterFilial( "CPIMPORTACAO" ) );
			ps.setInt( param++, codimp );
			ps.execute();
		} catch (SQLException e) {
			Funcoes.mensagemErro( null, "Erro ao atualizar valor total das despesas da compra de importacao!\n" + e.getMessage(), true, getConn(), e );
		} finally {
			ps = null;
		}
	}
	
	public void rateioSiscomex(Vector<Vector<Object>> vector, Integer codimp ) {
		
		StringBuilder sql = new StringBuilder();
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			

			sql.append( "update cpitimportacao set ");

			// Rateando o Frete pelo valor aduaneiro
			sql.append( "vlrtxsiscomex=( ( ? * (vlrad) ) / ? )" );				
			sql.append( "where codemp=? and codfilial=? and codimp=? and codncm=?" );

			ps = getConn().prepareStatement( sql.toString() );
			
			for ( Object row : vector ) {
				Vector<Object> rowVect = (Vector<Object>) row;
				BigDecimal siscomex = ConversionFunctions.stringCurrencyToBigDecimal( rowVect.elementAt( FImportacao.GRID_ADICAO.VLRTXSISCOMEXADIC.ordinal() ).toString() ) ;
				BigDecimal vlradadic = ConversionFunctions.stringCurrencyToBigDecimal(  rowVect.elementAt( FImportacao.GRID_ADICAO.VLRADUANEIRO.ordinal()).toString() );
				String ncm = rowVect.elementAt( GRID_ADICAO.CODNCM.ordinal()).toString() ;
				
				ps.setBigDecimal( 1, siscomex );
				ps.setBigDecimal( 2, vlradadic );
	
				ps.setInt( 3, Aplicativo.iCodEmp );
				ps.setInt( 4, ListaCampos.getMasterFilial( "CPIMPORTACAO" ) );
				ps.setInt( 5, codimp );
				ps.setString( 6, ncm );
				
				ps.execute();
			
			}
			
			getConn().commit();
			
			Funcoes.mensagemInforma( null, "Valores rateados entre os ítens!" );
			//lcCampos.carregaDados();
			
		}
		catch (Exception e) {
			Funcoes.mensagemErro( null, "Erro ao realizar o rateio do frete.", false, e );
			e.printStackTrace();
		}
	}
	
	
	public void atualizaDiferenca(Integer codemp, Integer codfilial, Integer codimp, BigDecimal diferenca){
		
		PreparedStatement ps = null;
		StringBuilder sql = new StringBuilder();
		sql.append( "update cpitimportacao it set it.VLRITDESPAD = it.VLRITDESPAD + ? where it.codemp=? and it.CODFILIAL=? and it.codimp=? and ");
		sql.append( " it.coditimp=( select first 1 itm.coditimp from cpitimportacao itm where itm.codemp=it.codemp and itm.CODFILIAL=it.codfilial and itm.codimp=it.codimp order by itm.vlritdespad desc ) ");
		
		try{
			ps = getConn().prepareStatement( sql.toString() );
			int param = 1;
			ps.setBigDecimal( param++, diferenca );
			ps.setInt( param++, codemp );
			ps.setInt( param++, codfilial );
			ps.setInt( param++, codimp );
			ps.execute();

		} catch (SQLException e) {
			Funcoes.mensagemErro( null, "Erro ao atualizar diferança no maior termo da compra de importacao!\n" + e.getMessage(), true, getConn(), e );
		} finally {
			ps = null;
		}		
	}
	
	public ResultSet buscaAdicao(Integer codemp, Integer codfilial, Integer codimp){
		
		
		StringBuilder sql = new StringBuilder();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			
			sql.append( "select codncm, sum(vlrad) vlrad from cpitimportacao where codemp=? and codfilial=? and codimp=? group by codncm " );
			
			ps = getConn().prepareStatement( sql.toString() );
			
			ps.setInt( 1, codemp );
			ps.setInt( 2, codfilial);
			ps.setInt( 3, codimp );
			
			rs = ps.executeQuery();
		} catch (Exception e) {
			Funcoes.mensagemErro( null, "Erro ao buscar items para adição!", false, e );
			e.printStackTrace();
		}
		
		
		return rs;

	}
	
	public void excluiAdicoes(Integer codemp, Integer codfilial, Integer codimp) {
		
		StringBuilder sql = new StringBuilder();
		PreparedStatement ps = null;
		
		try {
			
			sql.append("delete from cpimportacaoadic where codemp=? and codfilial=? and codimp=? ");
			
			ps = getConn().prepareStatement( sql.toString() );
			
			ps.setInt( 1, codemp );
			ps.setInt( 2, codfilial );
			ps.setInt( 3, codimp );
			
			ps.execute();
			
			getConn().commit();
			
		}
		catch (Exception e) {
			Funcoes.mensagemErro( null, "Erro ao excluir adições!", false, e );
			e.printStackTrace();
		}
		
	}
	
	
	public Integer buscaClassificacaoFiscal(String codfisc, Integer codpais) {
		
		StringBuilder sql = new StringBuilder();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Integer coditfisc = 0;
		
		try {
			
			sql.append( "select coditfisc from lfitclfiscal " );
			sql.append( "where " );
			sql.append( "codemp=? and codfilial=? and codfisc=? and tipousoitfisc='CP' and codpais=?" );

			ps = getConn().prepareStatement( sql.toString() );
			
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "LFITCLFISCAL" ) );
			ps.setString( 3, codfisc );
			ps.setInt( 4, codpais );
			
			rs = ps.executeQuery();
			
			if(rs.next()){
				coditfisc = rs.getInt( "coditfisc" );
			}
			
		} catch (Exception e) {
			Funcoes.mensagemErro( null, "Erro ao buscar item da classificação fiscal.", false, e );
			e.printStackTrace();
		}
		
		return coditfisc;
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
