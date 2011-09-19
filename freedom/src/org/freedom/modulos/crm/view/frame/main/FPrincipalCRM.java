package org.freedom.modulos.crm.view.frame.main;

import java.sql.SQLException;

import org.freedom.library.functions.Funcoes;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.frame.Aplicativo;
import org.freedom.library.swing.frame.FPrincipalPD;
import org.freedom.modulos.crm.view.dialog.utility.DLRegBatida;
import org.freedom.modulos.gpe.business.object.Batida;
import org.freedom.modulos.gpe.dao.DAOBatida;


public class FPrincipalCRM extends FPrincipalPD {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FPrincipalCRM( String sDirImagem, String sImgFundo ) {

		super( sDirImagem, sImgFundo );
	}

	@ Override
	public void fecharJanela() {

		super.fecharJanela();
	}

	@ Override
	public void inicializaTela() {
		super.inicializaTela();

	}

	@ Override
	public void remConFilial() {
		super.remConFilial();
	}
	
	@Override
	public void windowOpen() {
		super.windowOpen();
		Batida result = carregaPonto("A"); 
		if ( (result!=null) && ("S".equals(result.getCarregaponto())) ){
		    showPonto(result);
		}
	}

	public Batida carregaPonto(String aftela) {
		Batida result = null;
		DAOBatida daobatida = new DAOBatida( con );
		try {
			daobatida.setPrefs( Aplicativo.iCodEmp, ListaCampos.getMasterFilial( "SGPREFERE3" ) );
			result = daobatida.carregaPonto( Aplicativo.iCodEmp, ListaCampos.getMasterFilial( "SGUSUARIO" ), Aplicativo.strUsuario, aftela );
		} catch ( SQLException e ) {
			Funcoes.mensagemErro( this, "Erro carregando preferências!\n" + e.getMessage() );
			e.printStackTrace();
		}
		return result;
	}
	
	public void showPonto(Batida batida) {
		if ( (batida!=null) && ("S".equals(batida.getCarregaponto())) ) {
			DLRegBatida dlbatida = new DLRegBatida();
			dlbatida.setConexao( con );
			dlbatida.setValores( batida );
			dlbatida.setVisible( true );
		}
	}
	
}
