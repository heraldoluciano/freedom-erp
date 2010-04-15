package org.freedom.modulos.crm.business.object;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.freedom.infra.beans.Field;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.frame.Aplicativo;



public class Chamado {

	public enum Campos {
		CODEMP, CODFILIAL, CODCHAMADO, DESCCHAMADO, DETCHAMADO, OBSCHAMADO, CODEMPCL, CODFILIALCL,
		CODCLI, SOLICITANTE, PRIORIDADE, CODEMPTC, CODFILIALTC, CODTPCHAMADO, STATUS, DTCHAMADO,
		DTPREVISAO, QTDHORASPREVISAO, DTCONCLUSAO, DTINS, HINS, IDUSUINS, DTALT, HALT, IDUSUALT
	}
	
	Field codchamado = new Field( "codchamado", null, Types.INTEGER );
	Field deschamado = new Field( "descchamado", null, Types.CHAR );
	Field detchamado = new Field( "detchamado", null, Types.CHAR );
	Field obschamado = new Field( "obschamado", null, Types.CHAR );
	Field solicitante = new Field( "solicitante", null, Types.CHAR );
	Field prioridade = new Field( "prioridade", null, Types.INTEGER );
	Field codtpchamado = new Field( "codtpchamado", null, Types.INTEGER );
	Field status = new Field( "status", null, Types.CHAR );
	Field dtchamado = new Field( "dtchamado", null, Types.DATE );
	Field dtprevisao = new Field( "dtprevisao", null, Types.DATE );
	Field qtdhorasprevisao = new Field( "qtdhorasprevisao", null, Types.DECIMAL );
	Field dtconclusao = new Field( "dtconclusao", null, Types.DATE );
	
	private void montaComboTipoChamado(DbConnection con) {

		try {
			
			PreparedStatement ps = con.prepareStatement( "select codtpchamado, desctpchamado from attipoatendo where codemp=? and codfilial=? order by desctpchamado" );

			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "CRTIPOCHAMADO" ) );

			
			ResultSet rs = ps.executeQuery();
			
//			vValsTipo.clear();
			//			vLabsTipo.clear();

			//			vValsTipo.addElement( -1 );
			//			vLabsTipo.addElement( "<Selecione>" );
			
			//			while ( rs.next() ) {
			//vValsTipo.addElement( rs.getInt( "CodTpAtendo" ) );
			//				vLabsTipo.addElement( rs.getString( "DescTpAtendo" ) );
			//			}
			
			//			cbTipo.setItensGeneric( vLabsTipo, vValsTipo );
			
			//			rs.close();
			//			ps.close();
			
			con.commit();
			
		} catch ( SQLException e ) {
			e.printStackTrace();
//			Funcoes.mensagemErro( this, "Erro ao carregar os tipos de atendimento!\n" + e.getMessage(), true, con, e );
		} 
	}

	
}
