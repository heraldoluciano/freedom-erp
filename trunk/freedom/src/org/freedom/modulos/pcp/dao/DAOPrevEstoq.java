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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Vector;

import org.freedom.infra.dao.AbstractDAO;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.business.exceptions.ExceptionCarregaDados;
import org.freedom.library.functions.Funcoes;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.frame.Aplicativo;

public class DAOPrevEstoq extends AbstractDAO {

	public DAOPrevEstoq( DbConnection cn) {
		super( cn );
	}

	public boolean comRef() throws SQLException {

		boolean bRetorno = false;
		String sSQL = "SELECT USAREFPROD FROM SGPREFERE1 WHERE CODEMP=? AND CODFILIAL=?";
		PreparedStatement ps = null;
		ResultSet rs = null;


		ps = Aplicativo.getInstace().getConexao().prepareStatement( sSQL );

		ps.setInt( 1, Aplicativo.iCodEmp );
		ps.setInt( 2, ListaCampos.getMasterFilial( "SGPREFERE1" ) );

		rs = ps.executeQuery();

		if ( rs.next() )
			if ( rs.getString( "UsaRefProd" ).trim().equals( "S" ) )
				bRetorno = true;

		ps.close();
		rs.close();
		return bRetorno;
	}
	
	public Vector<Vector<Object>> carregar(Integer codemp, Integer codfilial, Date dataini, Date datafim, String prodSemMovimento ) throws ExceptionCarregaDados{

		PreparedStatement ps = null;
		ResultSet rs = null;
		String mensagemErro = "";
		StringBuilder sql = null;
		String sWhere = null;
		Vector<Object> vVals = null;
		boolean bOrc = false;
		boolean bConv = false;
		int iCod = -1;
		Vector<Vector<Object>> vector = null;
		int param = 1;
		try {

			sql = new StringBuilder();
			
			sql.append("select pd.codemp, pd.codfilial, pd.codprod, pd.refprod, pd.descprod ");
			sql.append(", sum(iv.qtditvenda) / 150 mediavendas ");
			sql.append(", pd.prazorepo, pd.precobaseprod, pd.sldprod, pd.qtdminprod, pd.qtdmaxprod ");
			sql.append("from eqproduto pd ");
			sql.append("inner join vditvenda iv on ");
			sql.append("iv.codemppd=pd.codemp and iv.codfilialpd=pd.codfilial and iv.codprod=pd.codprod ");
			if ("S".equals(prodSemMovimento))
				sql.append("left outer join vdvenda v on ");
			else
				sql.append("inner join vdvenda v on ");
			
			sql.append("v.codemp=iv.codemp and v.codfilial=iv.codfilial and v.tipovenda=iv.tipovenda and v.codvenda=iv.codvenda ");
			sql.append("and v.dtemitvenda between ? and ? ");
			sql.append("where pd.codemp=? and pd.codfilial=? ");
			sql.append("group by pd.codemp, pd.codfilial, pd.codprod, pd.refprod, pd.descprod ");
			sql.append(", pd.prazorepo, pd.precobaseprod, pd.sldprod, pd.qtdminprod, pd.qtdmaxprod ");
			
			ps = getConn().prepareStatement( sql.toString() );
			ps.setDate( param++, Funcoes.dateToSQLDate(dataini));
			ps.setDate( param++, Funcoes.dateToSQLDate(datafim));
			ps.setInt( param++, codemp );
			ps.setInt( param++, codfilial);
			rs = ps.executeQuery();
			
			vector =  new Vector<Vector<Object>>();

			while (rs.next()) {
					vVals = new Vector<Object>();
					vVals.addElement(new Boolean( true ) );
					vVals.addElement(getInteger(rs.getInt("codprod")));
					vVals.addElement(getString(rs.getString("refprod")));
					vVals.addElement(getString(rs.getString("descprod")));
					vVals.addElement(getBigDecimal(rs.getBigDecimal("mediavendas")));
					vVals.addElement(getInteger(rs.getInt("prazorepo")));
					vVals.addElement(getBigDecimal(rs.getBigDecimal("precobaseprod")));
					vVals.addElement(getBigDecimal(rs.getBigDecimal("sldprod")));
					vVals.addElement(getBigDecimal(rs.getBigDecimal("qtdminprod")));
					vVals.addElement(getBigDecimal(rs.getBigDecimal("qtdmaxprod")));
					vVals.addElement(new BigDecimal(0));
					vVals.addElement(new BigDecimal(0));
					vVals.addElement(new BigDecimal(0));
					vector.add(vVals);
			}

			rs.close();
			ps.close();
		} catch ( SQLException err ) {
			mensagemErro = "Erro ao carregar previsão de estoque!\n";
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

	
}


