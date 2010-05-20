package org.freedom.modulos.fnc.business.component;

import java.util.Date;

import org.freedom.library.functions.Funcoes;

public class ValidadeOrdemServico {

	public boolean retornaValidade( Date dtValidade, Date dtEntrada, boolean evnt, boolean status ) {

		if ( dtValidade != null && dtEntrada != null && evnt == true && status == true ) {

			if ( Funcoes.getNumDias( dtEntrada, dtValidade ) >= 0 ) {

				return true;
			}

		}

		return false;

	}

}
