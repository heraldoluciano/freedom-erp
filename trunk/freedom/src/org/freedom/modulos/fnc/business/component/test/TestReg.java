package org.freedom.modulos.fnc.business.component.test;

import org.freedom.modulos.fnc.business.component.cnab.Reg3Q;
import org.freedom.modulos.fnc.library.business.compoent.FbnUtil.ETipo;


public class TestReg {

	public static void main( String[] args ) {

		Reg3Q reg = new Reg3Q();
		reg.setRazCli( "Setpoint Informática Ltda" );
		System.out.print( reg.format( reg.getRazCli(), ETipo.X, 30, 0 , true, true) );
	}

}
