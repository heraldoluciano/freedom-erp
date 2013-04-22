/**
 * @version 22/04/2013 <BR>
 * @author Setpoint Informática Ltda./Bruno Nascimento<BR>
 *  
 *          Projeto: Freedom <BR>
 * 
 *          Pacote: org.freedom.modulos.pcp.dao <BR>
 *          Classe: @(#)DAOPull.java <BR>
 * 
 *                  Este arquivo é parte do sistema Freedom-ERP, o Freedom-ERP é um software livre; você pode redistribui-lo e/ou <BR>
 *                  modifica-lo dentro dos termos da Licença Pública Geral GNU como publicada pela Fundação do Software Livre (FSF); <BR>
 *                  na versão 2 da Licença, ou (na sua opnião) qualquer versão. <BR>
 *                  Este programa é distribuido na esperança que possa ser util, mas SEM NENHUMA GARANTIA; <BR>
 *                  sem uma garantia implicita de ADEQUAÇÂO a qualquer MERCADO ou APLICAÇÃO EM PARTICULAR. <BR>
 *                  Veja a Licença Pública Geral GNU para maiores detalhes. <BR>
 *                  Você deve ter recebido uma cópia da Licença Pública Geral GNU junto com este programa, se não, <BR>
 *                  de acordo com os termos da LPG-PC <BR>
 * <BR>
 * 
 *                  DAO que possui a responsabilidade sobre o Sistema de produção Puxada ( Pull System ).
 * 
 */


package org.freedom.modulos.pcp.dao;

import java.math.BigDecimal;
import java.sql.PreparedStatement;

import org.freedom.infra.dao.AbstractDAO;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.swing.frame.Aplicativo;


public class DAOPull extends AbstractDAO {

	public DAOPull( DbConnection cn) {
		super( cn );
	}
	
	/*private void montaGridAgrup(String agrupProd, String agrupCli, String agrupDataAprov, String agrupDataProd,
			int codempcl, int codfilialcl, int codcli, int codemppd, int codfilialpd, int codprod) {

		try {

			if ( "N".equals( agrupProd ) && "N".equals( agrupCli ) && "N".equals( agrupDataAprov ) && "N".equals( agrupDataProd ) ) {

			//	Funcoes.mensagemInforma( this, "Deve haver ao menos um critério de agrupamento!" );
				return;
			}

			StringBuilder sql = new StringBuilder();

			sql.append( "select " );

			sql.append( "pd.codemp codemppd, pd.codfilial codfilialpd, pd.codprod, pd.descprod, coalesce(sp.sldliqprod,0) qtdestoque, " );
			sql.append( "sum(coalesce(po.qtdaprod,0)) qtdaprod, sum(coalesce(op.qtdprevprodop,0)) qtdemprod, " );
			sql.append( "(select first 1 pe.seqest from ppestrutura pe where pe.codemp=pd.codemp and pe.codfilial=pd.codfilial and pe.codprod=pd.codprod) seqest " );

			int igroup = 8; // Numero padrão de campos

			if ("S".equals(agrupDataProd)) {
				sql.append( ",io.dtaprovitorc dataaprov " );
				igroup++; // Indica que foi adicionado um novo campo que deve fazer parte do groupby
			}

			if ("S".equals(agrupDataProd)) {
				sql.append( ",po.dtfabrop " );
				igroup++;// Indica que foi adicionado um novo campo que deve fazer parte do groupby
			}

			if ("S".equals(agrupCli ) ) {
				sql.append( ",cl.codemp codempcl, cl.codfilial codfilialcl, cl.codcli, cl.razcli " );
				igroup+=4;// Indica que foi adicionado um novo campo que deve fazer parte do group by
			}

			sql.append( "from vdorcamento oc left outer join vditorcamento io on " );
			sql.append( "io.codemp=oc.codemp and io.codfilial=oc.codfilial and io.codorc=oc.codorc and io.tipoorc=oc.tipoorc " );
			sql.append( "left outer join vdcliente cl on cl.codemp=oc.codempcl and cl.codfilial=oc.codfilialcl and cl.codcli=oc.codcli " );
			sql.append( "left outer join eqproduto pd on pd.codemp=io.codemppd and pd.codfilial=io.codfilialpd and pd.codprod=io.codprod " );
			sql.append( "left outer join ppop op on op.codemppd=pd.codemp and op.codfilial=pd.codfilial and op.codprod=pd.codprod and op.sitop in ('PE','BL') " );
			sql.append( "left outer join eqsaldoprod sp on sp.codemp=pd.codemp and sp.codfilial=pd.codfilial and sp.codprod=pd.codprod " );
			sql.append( "inner join ppprocessaoptmp po on " );
			sql.append( "po.codemp=io.codemp and po.codfilial=io.codfilial and po.codorc=io.codorc and po.tipoorc=io.tipoorc and po.coditorc=io.coditorc " );

			sql.append( "where oc.codemp=? and oc.codfilial=? and io.aprovitorc='S' and io.sitproditorc='PE' and pd.tipoprod='F' " );

			if (codprod > 0) {
				sql.append( " and io.codemppd=? and io.codfilialpd=? and io.codprod=? " );
			}
			if (codcli > 0) {
				sql.append( " and oc.codempcl=? and oc.codfilialcl=? and oc.codcli=? " );
			}

			if ("S".equals(agrupProd) || "S".equals(agrupCli) || "S".equals(agrupDataAprov) || "S".equals(agrupDataProd) ) {

				sql.append( "group by " );

				if ("S".equals(agrupProd)) {
					sql.append( "1, 2, 3, 4, 5 " );
				}

				if (igroup > 8) {
					for (int i = 8; i < igroup; i++) {
						sql.append( "," + ( i + 1 ) );
					}
				}

			}

			System.out.println( "SQL:" + sql.toString() );

			PreparedStatement ps = getConn().prepareStatement( sql.toString() );

			int iparam = 1;

			ps.setInt( iparam++, Aplicativo.iCodEmp );
			ps.setInt( iparam++, ListaCampos.getMasterFilial( "VDORCAMENTO" ) );

			if ( codprod > 0 ) {
				ps.setInt( iparam++, codemppd);
				ps.setInt( iparam++, codfilialpd );
				ps.setInt( iparam++, codprod);
			}
			if ( codcli > 0 ) {
				ps.setInt( iparam++, codempcl );
				ps.setInt( iparam++, codfilialcl );
				ps.setInt( iparam++, codcli );
			}

			ResultSet rs = ps.executeQuery();

			//tabAgrup.limpa();

			//int row = 0;

			// BigDecimal totqtdaprov = new BigDecimal(0);
			BigDecimal totqtdestoq = new BigDecimal( 0 );
			BigDecimal totqtdemprod = new BigDecimal( 0 );
			BigDecimal totqtdaprod = new BigDecimal( 0 );

			sql = new StringBuilder();
			sql.append( "select coalesce(sum(io2.qtdaprovitorc),0) qtdreservado from ppopitorc oo, vditorcamento io2 where " );
			sql.append( "oo.codempoc=io2.codemp and oo.codfilialoc=io2.codfilial " );
			sql.append( "and oo.codorc=io2.codorc and oo.coditorc=io2.coditorc and oo.tipoorc=io2.tipoorc " );
			sql.append( "and io2.codemp=? and io2.codfilial=? " );
			sql.append( "and io2.codemppd=? and io2.codfilialpd=? and io2.codprod=? " );
			sql.append( "and io2.sitproditorc='PD' and coalesce(io2.statusitorc,'PE')!='OV' " );

			ResultSet rs2 = null;

			PreparedStatement ps2 = null;
			
			ArrayList<Agrupamento> agrupamentos = new ArrayList<Agrupamento>();;

			while ( rs.next() ) {
				Agrupamento agrupamento = new Agrupamento();
				ps2 = getConn().prepareStatement( sql.toString() );

				ps2.setInt( 1, Aplicativo.iCodEmp );
				ps2.setInt( 2, ListaCampos.getMasterFilial( "VDORCAMENTO" ) );
				ps2.setInt( 3, Aplicativo.iCodEmp );
				ps2.setInt( 4, ListaCampos.getMasterFilial( "EQPRODUTO" ) );
				ps2.setInt( 5, rs.getInt( Agrupamento.AGRUPAMENTO.CODPROD.toString() ) );

				rs2 = ps2.executeQuery();

				BigDecimal qtdreservado = new BigDecimal( 0 );

				if ( rs2.next() ) {
					qtdreservado = rs2.getBigDecimal( Agrupamento.AGRUPAMENTO.QTDRESERVADO.toString() ).setScale( Aplicativo.casasDec );
				}

				agrupamento.setMarcacao( new Boolean( true ) );
				agrupamento.setCodemppd(rs.getInt(Agrupamento.AGRUPAMENTO.CODEMPPD.toString()));
				agrupamento.setCodfilialpd(rs.getInt(Agrupamento.AGRUPAMENTO.CODFILIALPD.toString()));
				agrupamento.setCodprod(rs.getInt(Agrupamento.AGRUPAMENTO.CODPROD.toString()));
				agrupamento.setSeqest(rs.getInt(Agrupamento.AGRUPAMENTO.SEQEST.toString()));
				agrupamento.setDescprod(rs.getString( Agrupamento.AGRUPAMENTO.DESCPROD.toString().trim()));

				if ( "S".equals( agrupDataAprov ) ) {
				//	tabAgrup.setColunaVisivel( 60, Agrupamento.AGRUPAMENTO.DATAAPROV.ordinal() );
				//	tabAgrup.setValor(  , row, Agrupamento.AGRUPAMENTO.DATAAPROV.ordinal() );
					agrupamento.setDataaprov( Funcoes.sqlDateToDate(rs.getDate( Agrupamento.AGRUPAMENTO.DATAAPROV.toString() ) ) );
				}
				else {
					//tabAgrup.setColunaInvisivel( Agrupamento.AGRUPAMENTO.DATAAPROV.ordinal() );
				}

				if ( "S".equals( agrupDataProd ) ) {
					//tabAgrup.setValor( Funcoes.dateToStrDate( rs.getDate( Agrupamento.AGRUPAMENTO.DTFABROP.toString() ) ), row, Agrupamento.AGRUPAMENTO.DTFABROP.ordinal() );
					agrupamento.setDtfabrop(Funcoes.sqlDateToDate(rs.getDate(Agrupamento.AGRUPAMENTO.DTFABROP.toString()))); 
				}
				else {
					//tabAgrup.setValor( Funcoes.dateToStrDate( new Date() ), row, AGRUPAMENTO.DTFABROP.ordinal() );
					agrupamento.setDtfabrop(new Date());
				}

				if ( "S".equals( agrupCli ) ) {
					agrupamento.setCodempcl(rs.getInt( Agrupamento.AGRUPAMENTO.CODEMPCL.toString()));
					agrupamento.setCodfilialcl(rs.getInt( Agrupamento.AGRUPAMENTO.CODFILIALCL.toString()));
					agrupamento.setCodcli(rs.getInt(Agrupamento.AGRUPAMENTO.CODCLI.toString()));
					agrupamento.setRazcli(rs.getString(Agrupamento.AGRUPAMENTO.RAZCLI.toString().trim()));
				}
				
				//tabAgrup.setValor( imgColuna, row, Agrupamento.AGRUPAMENTO.STATUS.ordinal() );
				agrupamento.setQtdestoque(rs.getBigDecimal(Agrupamento.AGRUPAMENTO.QTDESTOQUE.toString()).setScale(Aplicativo.casasDec));
				agrupamento.setQtdemprod(rs.getBigDecimal(Agrupamento.AGRUPAMENTO.QTDEMPROD.toString()).setScale(Aplicativo.casasDec));
				agrupamento.setQtdaprod( rs.getBigDecimal( Agrupamento.AGRUPAMENTO.QTDAPROD.toString() ).setScale( Aplicativo.casasDec ) );
				agrupamento.setQtdreservado(  rs.getBigDecimal( Agrupamento.AGRUPAMENTO.QTDRESERVADO.toString() ).setScale( Aplicativo.casasDec ) );
			}

		} catch ( Exception e ) {
			e.printStackTrace();
		}

	}*/


	public void deletaTabTemp() {

		StringBuilder sql = new StringBuilder( "" );
		PreparedStatement ps = null;

		try {

			sql.append( "delete from ppprocessaoptmp pt where pt.codempet=? and pt.codfilialet=? and pt.codest=?" );

			ps = getConn().prepareStatement( sql.toString() );

			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, Aplicativo.iCodFilial );
			ps.setInt( 3, Aplicativo.iNumEst );

			ps.execute();
			ps.close();

			getConn().commit();
		} catch ( Exception e ) {
			e.printStackTrace();
		}
	}

	public StringBuilder queryPPProcessaOpTmp()	{
		StringBuilder sql = new StringBuilder();
		sql.append( "insert into ppprocessaoptmp (codemp, codfilial, codorc, coditorc, tipoorc, dtfabrop, qtdaprod, codempet, codfilialet, codest) " );
		sql.append( "values( ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )" );

		return sql;
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


