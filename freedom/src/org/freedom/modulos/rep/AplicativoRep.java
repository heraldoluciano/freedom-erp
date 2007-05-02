package org.freedom.modulos.rep;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.AplicativoPD;
import org.freedom.telas.FPrincipal;

public class AplicativoRep extends AplicativoPD {

	public AplicativoRep( String sIcone, String sSplash, int iCodSis, String sDescSis, int iCodModu, String sDescModu, String sDirImagem, FPrincipal telaP, Class cLogin ) {

		super( sIcone, sSplash, iCodSis, sDescSis, iCodModu, sDescModu, sDirImagem, telaP, cLogin );

	}

	@ Override
	protected void carregaCasasDec() {
		
		try {

			PreparedStatement ps = con.prepareStatement( "SELECT CASASDEC,CASASDECFIN FROM SGPREFERE1 WHERE CODEMP=? AND CODFILIAL=?" );
			ps.setInt( 1, iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "SGPREFERE1" ) );
			ResultSet rs = ps.executeQuery();
			
			if ( rs.next() ) {
				casasDec = rs.getInt( "CASASDEC" );
				casasDecFin = rs.getInt( "CASASDECFIN" );
			}
			
			rs.close();
			ps.close();
			
			if ( !con.getAutoCommit() ) {
				con.commit();
			}
		} catch ( SQLException err ) {
			Funcoes.mensagemErro( null, "Não foi possível obter o número de casas decimais!\n" + err.getMessage(), true, con, err );
		} 
	}

	@ Override
	protected void carregaBuscaProd() {
		
		bBuscaProdSimilar = false;
		bBuscaCodProdGen = false;
	}
}
