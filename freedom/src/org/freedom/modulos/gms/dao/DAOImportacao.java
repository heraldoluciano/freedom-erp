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
			
			//Valor que será rateado
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
	
	
	
	public void geraCabecalhoImportacao(Integer codemp, Integer codfilial, Integer codimp) {
		
		int proxCodImp = 0;
		StringBuilder sql = new StringBuilder();
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {			
			proxCodImp = getProxCodImp( codemp, codfilial );
			
			
			//query de insert do cabeçalho da importação		
			sql.append( "insert into cpimportacao ( emmanut, codemp, codfilial, codimp ");
			sql.append( ", codempmi, codfilialmi, codmoeda, cotacaomoeda ");
			sql.append( ", codemppg, codfilialpg, codplanopag, codempfr, codfilialfr, codfor ");
			sql.append( ", invoice, di, manifesto, certorigem, lacre, prescarga, identhouse ");
			sql.append( ", dta, conheccarga, identcontainer, tipomanifesto, dtimp ");
			sql.append( ", dtemb, dtchegada, dtdesembdi, dtregdi, localemb, recintoaduaneiro ");
			sql.append( ", codpaisdesembdi, siglaufdesembdi, locdesembdi, obs, veiculo, pesobruto ");
			sql.append( ", pesoliquido, vlrfretemi, vlrfrete, vmlemi, vmldmi, vmle, vmld, vlrseguromi ");
			sql.append( ", vlrseguro, vlrii, vlripi, vlrpis, vlrcofins, vlrdireitosad, vlrthc, vlrthcmi ");
			sql.append( ", vlrtxsiscomex, vlrad, vlradmi, vlrbaseicms, vlricms, vlricmsdiferido, vlricmsdevido ");
			sql.append( ", vlricmscredpresum, vlricmsrecolhimento, vlrdespad ) ");

			sql.append( "select 'S', codemp, codfilial, ");
			sql.append( proxCodImp );
			sql.append( " codimp, codempmi, codfilialmi, codmoeda, cotacaomoeda ");
			sql.append( ", codemppg, codfilialpg, codplanopag, codempfr, codfilialfr, codfor ");
			sql.append( ", invoice, di, manifesto, certorigem, lacre, prescarga, identhouse ");
			sql.append( ", dta, conheccarga, identcontainer, tipomanifesto, dtimp ");
			sql.append( ", dtemb, dtchegada, dtdesembdi, dtregdi, localemb, recintoaduaneiro ");
			sql.append( ", codpaisdesembdi, siglaufdesembdi, locdesembdi, obs, veiculo, pesobruto ");
			sql.append( ", pesoliquido, vlrfretemi, vlrfrete, vmlemi, vmldmi, vmle, vmld, vlrseguromi ");
			sql.append( ", vlrseguro, vlrii, vlripi, vlrpis, vlrcofins, vlrdireitosad, vlrthc, vlrthcmi ");
			sql.append( ", vlrtxsiscomex, vlrad, vlradmi, vlrbaseicms, vlricms, vlricmsdiferido, vlricmsdevido ");
			sql.append( ", vlricmscredpresum, vlricmsrecolhimento, vlrdespad from cpimportacao i ");
			sql.append( "where codemp=? and codfilial=? and codimp=? ");
			
			ps = getConn().prepareStatement( sql.toString() );
			int param = 1;
			
			ps.setInt( param++, codemp );
			ps.setInt( param++, codfilial );
			ps.setInt( param++, codimp );
			ps.execute();
			
			
			geraItensImportacao( codemp, codfilial, codimp, proxCodImp );
			
		}catch (SQLException e) {
			Funcoes.mensagemErro( null, "Erro ao gerar cabeçalho de importação.", false, e );
			e.printStackTrace();
		}

	}
	
	public void geraItensImportacao(Integer codemp, Integer codfilial, Integer codimp, Integer novocodimp){
		
		StringBuilder sql = new StringBuilder();
		PreparedStatement ps = null;
		ResultSet rs = null; 

		try{
			// insert dos itens de importação
			sql.append( "insert into cpitimportacao ( emmanut, codemp, codfilial, codimp, coditimp, codemppd, codfilialpd, codprod ");
			sql.append( ", refprod, qtd, codempun, codfilialun, codunid, pesoliquido ");
			sql.append( ", pesobruto, precomi, preco, vmlemi, vmldmi, vmle, vmld ");
			sql.append( ", vlrfretemi, vlrfrete, vlrseguromi, vlrseguro, vlrthcmi ");
			sql.append( ", vlrthc, vlradmi, vlrad, aliqicmsimp, aliqicmsuf, percdifericms ");
			sql.append( ", perccredpresimp, aliqipi, aliqpis, aliqcofins, aliqii ");
			sql.append( ", vlrii, vlripi, vlrpis, vlrcofins, vlrbaseicms, vlricms ");
			sql.append( ", vlricmsdiferido, vlricmsdevido, vlricmscredpresum, vlricmsrecolhimento ");
			sql.append( ", vlritdespad, vlrtxsiscomex, vlrvmcv, codempcf, codfilialcf, codfisc ");
			sql.append( ", coditfisc, codncm, seqadic) ");
			
			sql.append( "select 'S', codemp, codfilial, ");
			sql.append( novocodimp );
			sql.append( " codimp,  coditimp, codemppd, codfilialpd, codprod ");
			sql.append( ", refprod, qtd, codempun, codfilialun, codunid, pesoliquido ");
			sql.append( ", pesobruto, precomi, preco, vmlemi, vmldmi, vmle, vmld ");
			sql.append( ", vlrfretemi, vlrfrete, vlrseguromi, vlrseguro, vlrthcmi ");
			sql.append( ", vlrthc, vlradmi, vlrad, aliqicmsimp, aliqicmsuf, percdifericms ");
			sql.append( ", perccredpresimp, aliqipi, aliqpis, aliqcofins, aliqii ");
			sql.append( ", vlrii, vlripi, vlrpis, vlrcofins, vlrbaseicms, vlricms ");
			sql.append( ", vlricmsdiferido, vlricmsdevido, vlricmscredpresum, vlricmsrecolhimento ");
			sql.append( ", vlritdespad, vlrtxsiscomex, vlrvmcv, codempcf, codfilialcf, codfisc ");
			sql.append( ", coditfisc, codncm, seqadic from cpitimportacao i ");
			sql.append( "where codemp=? and codfilial=? and codimp=? ");
			
			ps = getConn().prepareStatement( sql.toString() );
			int param = 1;
			
			ps.setInt( param++, codemp );
			ps.setInt( param++, codfilial );
			ps.setInt( param++, codimp );
			ps.execute();


		}catch (Exception e) {
			Funcoes.mensagemErro( null, "Erro ao gerar cabeçalho de importação.", false, e );
			e.printStackTrace();
		}

	}
	
	public Integer geraSeqId() throws SQLException{
		
		StringBuilder sql = new StringBuilder();
		PreparedStatement ps = null;
		ResultSet rs = null; 
		Integer id = 0;
		ps = getConn().prepareStatement( "select biseq from sgsequence_idsp(?)" );
		ps.setString( 1, "CPIMPOTACAOCOMPL" );
		
		rs = ps.executeQuery();
		if (rs.next()) {
			id = rs.getInt( "biseq" ); 
		}
		
		
		return 0;
	}
	
	
	
	public void geraDiferencaImportacao(Integer codemp, Integer codfilial, Integer codimp, Integer novocodimp){
		
		StringBuilder sql = new StringBuilder();
		PreparedStatement ps = null;
		ResultSet rs = null; 

		try{
		
			//insert dos itens de importação complementar - diferenças
			sql.append( "insert into cpitimportacao (codemp, codfilial, codimp, coditimp, codemppd, codfilialpd, codprod ");
			sql.append( ", refprod, qtd, codempun, codfilialun, codunid, pesoliquido ");
			sql.append( ", pesobruto, precomi, preco, vmlemi, vmldmi, vmle, vmld ");
			sql.append( ", vlrfretemi, vlrfrete, vlrseguromi, vlrseguro, vlrthcmi ");
			sql.append( ", vlrthc, vlradmi, vlrad, aliqicmsimp, aliqicmsuf, percdifericms ");
			sql.append( ", perccredpresimp, aliqipi, aliqpis, aliqcofins, aliqii ");
			sql.append( ", vlrii, vlripi, vlrpis, vlrcofins, vlrbaseicms, vlricms ");
			sql.append( ", vlricmsdiferido, vlricmsdevido, vlricmscredpresum, vlricmsrecolhimento ");
			sql.append( ", vlritdespad, vlrtxsiscomex, vlrvmcv, codempcf, codfilialcf, codfisc ");
			sql.append( ", coditfisc, codncm, seqadic) ");

			sql.append( "select ic.codemp, ic.codfilial, ");
			sql.append( novocodimp );
			sql.append( " codimp, ic.coditimp, ic.codemppd, ic.codfilialpd, ic.codprod  ");
			sql.append( ", ic.refprod, ic.qtd, ic.codempun, ic.codfilialun, ic.codunid, ic.pesoliquido, ic.pesobruto ");
			sql.append( ", coalesce(ic.precomi,0)-coalesce(io.precomi,0) precomi ");
			sql.append( ", coalesce(ic.preco,0)-coalesce(io.preco,0) preco ");
			sql.append( ", coalesce(ic.vmlemi,0)-coalesce(io.vmlemi,0) vmlemi ");
			sql.append( ", coalesce(ic.vmldmi,0)-coalesce(io.vmldmi,0) vmldmi ");
			sql.append( ", coalesce(ic.vmle,0)-coalesce(io.vmle,0) vmle ");
			sql.append( ", coalesce(ic.vmld,0)-coalesce(io.vmld,0) vmld ");
			sql.append( ", coalesce(ic.vlrfretemi,0)-coalesce(io.vlrfretemi,0) vlrfretemi ");
			sql.append( ", coalesce(ic.vlrfrete,0)-coalesce(io.vlrfrete,0) vlrfrete ");
			sql.append( ", coalesce(ic.vlrseguromi,0)-coalesce(io.vlrseguromi,0) vlrseguromi ");
			sql.append( ", coalesce(ic.vlrseguro,0)-coalesce(io.vlrseguro,0) vlrseguro ");
			sql.append( ", coalesce(ic.vlrthcmi,0)-coalesce(io.vlrthcmi,0) vlrthcmi ");
			sql.append( ", coalesce(ic.vlrthc,0)-coalesce(io.vlrthc,0) vlrthc ");
			sql.append( ", coalesce(ic.vlradmi,0)-coalesce(io.vlradmi,0) vlradmi ");
			sql.append( ", coalesce(ic.vlrad,0)-coalesce(io.vlrad,0) vlrad ");
			sql.append( ", ic.aliqicmsimp, ic.aliqicmsuf, ic.percdifericms, ic.perccredpresimp, ic.aliqipi, ic.aliqpis, ic.aliqcofins, ic.aliqii ");
			sql.append( ", coalesce(ic.vlrii,0)-coalesce(io.vlrii,0) vlrii ");
			sql.append( ", coalesce(ic.vlripi,0)-coalesce(io.vlripi,0) vlripi ");
			sql.append( ", coalesce(ic.vlrpis,0)-coalesce(io.vlrpis,0) vlrpis ");
			sql.append( ", coalesce(ic.vlrcofins,0)-coalesce(io.vlrcofins,0) vlrcofins ");
			sql.append( ", coalesce(ic.vlrbaseicms,0)-coalesce(io.vlrbaseicms,0) vlrbaseicms ");
			sql.append( ", coalesce(ic.vlricms,0)-coalesce(io.vlricms,0) vlricms ");
			sql.append( ", coalesce(ic.vlricmsdiferido,0)-coalesce(io.vlricmsdiferido,0) vlricmsdiferido ");
			sql.append( ", coalesce(ic.vlricmsdevido,0)-coalesce(io.vlricmsdevido,0) vlricmsdevido ");
			sql.append( ", coalesce(ic.vlricmscredpresum,0)-coalesce(io.vlricmscredpresum,0) vlricmscredpresum ");
			sql.append( ", coalesce(ic.vlricmsrecolhimento,0)-coalesce(io.vlricmsrecolhimento,0) vlricmsrecolhimento ");
			sql.append( ", coalesce(ic.vlritdespad,0)-coalesce(io.vlritdespad,0) vlritdespad ");
			sql.append( ", coalesce(ic.vlrtxsiscomex,0)-coalesce(io.vlrtxsiscomex,0) vlrtxsiscomex ");
			sql.append( ", coalesce(ic.vlrvmcv,0)-coalesce(io.vlrvmcv,0) vlrvmcv ");
			sql.append( ", ic.codempcf, ic.codfilialcf, ic.codfisc ");
			sql.append( ", ic.coditfisc, ic.codncm, ic.seqadic  ");
			sql.append( "from cpitimportacao io, cpitimportacao ic ");
			sql.append( "where io.codemp=? and io.codfilial=? and io.codimp=? ");
			sql.append( "and ic.codemp=? and ic.codfilial=? and ic.codimp=? ");
			sql.append( "and ic.coditimp=io.coditimp; ");
			
			
			ps = getConn().prepareStatement( sql.toString() );
			int param = 1;
			
			ps.setInt( param++, codemp );
			ps.setInt( param++, codfilial );
			ps.setInt( param++, codimp );
			ps.setInt( param++, codemp );
			ps.setInt( param++, codfilial );
			ps.setInt( param++, codimp );
			
			ps.execute();

			
		}catch (Exception e) {
			Funcoes.mensagemErro( null, "Erro ao gerar cabeçalho de importação.", false, e );
			e.printStackTrace();
		}

	}
		
	public Integer getProxCodImp( Integer codemp, Integer codfilial ) throws SQLException{
		StringBuilder sql = new StringBuilder();
		int codimp = 0;
		
		//query para buscar próxima importacao		
		sql.append( "select coalesce(max(codimp)+1,1) codimp from cpimportacao where codemp=? and codfilial=? ");
		PreparedStatement ps = getConn().prepareStatement( sql.toString() );
		int param = 1;
		
		ps.setInt( param++, codemp );
		ps.setInt( param++, codfilial );
		ResultSet rs = ps.executeQuery();
		
		if(rs.next()){
			codimp = rs.getInt( "codimp" );
		}

		return codimp;
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
