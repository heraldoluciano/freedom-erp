package org.freedom.modulos.crm.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.ImageIcon;

import org.freedom.infra.dao.AbstractDAO;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.functions.Funcoes;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.frame.Aplicativo;
import org.freedom.modulos.crm.business.object.Atendimento;
import org.freedom.modulos.crm.business.object.Campanha;
import org.freedom.modulos.crm.business.object.Campanha.EColCampanha;
import org.freedom.modulos.crm.business.object.ContratoVW.EColContr;


public class DAOCampanha extends AbstractDAO {

	public DAOCampanha( DbConnection connection ) {

		super( connection );

	}
	
	public void  carregaTabCont( Vector<Vector<Object>> datavector, String tipocto, 
			Integer codempca, Integer codfilialca,
			Integer codempco,Integer codfilialco, 
			Integer codempcl, Integer codfilialcl, 
			String emailvalido, Vector<String> vCampFiltroPart, Vector<String> vCampFiltroNPart, ImageIcon imagem )throws SQLException{

		StringBuffer sql = null;
		Vector<Object> row = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int param = 1;

		try {
			sql = new StringBuffer("select co.codemp, co.codfilial, co.tipocto, co.codcto, ");
			sql.append( "co.nomecto, co.contcto, co.emailcto ");
			sql.append( "from tkcontclivw01 co ");
			sql.append( "where ( ( co.tipocto='O' and co.codemp=? and co.codfilial=? ) or ");
			sql.append( "( co.tipocto='C' and co.codemp=? and co.codfilial=? ) ) "	);
			if ( "S".equals( emailvalido ) ) {
				sql.append("and co.emailcto is not null ");
			}
			if (vCampFiltroPart.size()>0) {
				String sIN = Funcoes.vectorToString( vCampFiltroPart, "','" );
				sIN = "'" + sIN + "'";
				sql.append( " and exists (select * from tkcampanhacto cc " );
				sql.append( " where cc.codemp=" );
				sql.append( codempca );
				sql.append( " and cc.codfilial=" );
				sql.append( codfilialca );
				sql.append( " and ( (co.tipocto='O' and cc.codempco=co.codemp and cc.codfilialco=co.codfilial and cc.codcto=co.codcto ) or  " );
				sql.append( " and ( (co.tipocto='C' and cc.codempcl=co.codemp and cc.codfilialcl=co.codfilial and cc.codcto=co.codcto ) )  " );
				sql.append( " and cc.codcamp in (" + sIN + ")) " );				
			}
			if (vCampFiltroNPart.size()>0) {
				String sIN = Funcoes.vectorToString( vCampFiltroPart, "','" );
				sIN = "'" + sIN + "'";
				sql.append( " and not exists (select * from tkcampanhacto cc " );
				sql.append( " where cc.codemp=" );
				sql.append( codempca );
				sql.append( " and cc.codfilial=" );
				sql.append( codfilialca );
				sql.append( " and ( (co.tipocto='O' and cc.codempco=co.codemp and cc.codfilialco=co.codfilial and cc.codcto=co.codcto ) or  " );
				sql.append( " and ( (co.tipocto='C' and cc.codempcl=co.codemp and cc.codfilialcl=co.codfilial and cc.codcto=co.codcto ) )  " );
				sql.append( " and cc.codcamp in (" + sIN + ")) " );				
			}
			ps = getConn().prepareStatement( sql.toString() );
			ps.setInt( param++, codempco );
			ps.setInt( param++, codfilialco );
			ps.setInt( param++, codempcl );
			ps.setInt( param++, codfilialcl );
			rs = ps.executeQuery();
			datavector = new Vector<Vector<Object>>();
			while( rs.next() ){
				row = new Vector<Object>();
				row.addElement( new Boolean( false ) );
				row.addElement( "" );
				row.addElement( rs.getInt( EColCampanha.CODEMP.toString() ) );
				row.addElement( rs.getInt( EColCampanha.CODFILIAL.toString() ) );
				row.addElement( rs.getString( EColCampanha.TIPOCTO.toString() ) );
				row.addElement( rs.getInt( EColCampanha.CODCTO.toString() ) );
				row.addElement( rs.getString( EColCampanha.NOMECTO.toString() ) );
				row.addElement( rs.getString( EColCampanha.CONTCTO.toString() ) );
				row.addElement(	rs.getString( EColCampanha.EMAILCTO.toString() ) );
				row.addElement( imagem );
				datavector.addElement( row );
			}
			rs.close();
			ps.close();
			getConn().commit();
		} finally {
			ps = null;
			rs = null;
			sql = null;
		}
	}
	
}
