package org.freedom.modulos.rep;

import org.freedom.telas.AplicativoPD;
import org.freedom.telas.FPrincipal;


public class AplicativoRep extends AplicativoPD {


	public AplicativoRep( String sIcone, String sSplash, int iCodSis, String sDescSis, int iCodModu, String sDescModu, String sDirImagem, FPrincipal telaP, Class cLogin ) {

		super( sIcone, sSplash, iCodSis, sDescSis, iCodModu, sDescModu, sDirImagem, telaP, cLogin );

	}

	@ Override
	protected void carregaCasasDec() {

		// Ignorado por consultar tabela de preferencias inexistente no banco do FreedomRep.
		//super.carregaCasasDec();
	}

	@ Override
	public String getDescEst() {

		// Sobreposto por ter sido definido a estação de trabalho para FreedoRep
		return "Estação de trabalho - Freedom-ERP";
	}

	@ Override
	public boolean getModoDemo() {

		// Sobreposto por ter sido definido a estação de trabalho para FreedoRep
		return true;
	}

}
