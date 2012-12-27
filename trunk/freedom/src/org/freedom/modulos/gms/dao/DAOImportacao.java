package org.freedom.modulos.gms.dao;

import java.math.BigDecimal;
import java.sql.PreparedStatement;

import org.freedom.infra.dao.AbstractDAO;
import org.freedom.infra.model.jdbc.DbConnection;


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
