/**
 * @version 17/06/2005 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 * 
 * Projeto: Freedom <BR>
 * Pacote: org.freedom.componentes <BR>
 * Classe:
 * @(#)Tabela.java <BR>
 * 
 * Este programa é licenciado de acordo com a LPG-PC (Licença Pública Geral para Programas de Computador), <BR>
 * versão 2.1.0 ou qualquer versão posterior. <BR>
 * A LPG-PC deve acompanhar todas PUBLICAÇÕES, DISTRIBUIÇÕES e REPRODUÇÕES deste Programa. <BR>
 * Caso uma cópia da LPG-PC não esteja disponível junto com este Programa, você pode contatar <BR>
 * o LICENCIADOR ou então pegar uma cópia em: <BR>
 * Licença: http://www.lpg.adv.br/licencas/lpgpc.rtf <BR>
 * Para poder USAR, PUBLICAR, DISTRIBUIR, REPRODUZIR ou ALTERAR este Programa é preciso estar <BR>
 * de acordo com os termos da LPG-PC <BR>
 * <BR>
 * 
 * Objeto para guardar as informações necessárias para a criação e utilização de modelos de lote.
 */

package org.freedom.componentes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Vector;

import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;

public abstract class ObjetoModeloLote {

	private Vector<String> vLabels = new Vector<String>();

	private Vector<String> vLabelsAdic = new Vector<String>();

	private Vector<String> vLabelsColunas = new Vector<String>();

	private Vector<String> vValores = new Vector<String>();

	private Vector<String> vValoresAdic = new Vector<String>();

	private Vector<Integer> vTams = new Vector<Integer>();

	private Vector<String> vTamsAdic = new Vector<String>();

	private Vector<String> vMascaras = new Vector<String>();

	public static final String VLR_CODPROD 	 = "#CODPROD#";

	public static final String VLR_DIA 		 = "#DIA#";

	public static final String VLR_MES 		 = "#MES#";

	public static final String VLR_ANO 		 = "#ANO#";

	public static final String VLR_NPRODDIA  = "#NPRODDIA#";

	public static final String VLR_LOTEPRINC = "#LOTEPRINC#";

	private String sTexto = "";

	public ObjetoModeloLote() {

	}

	public void adicOpcao( String sLabel, String sValor, Integer iTam ) {

		vLabels.addElement( sLabel );
		vValores.addElement( sValor );
		vTams.addElement( iTam );
	}

	public Vector<String> getLabels() {
		return vLabels;
	}

	public Vector<String> getLabelsAdic() {
		return vLabelsAdic;
	}

	public Vector<Integer> getTams() {
		return vTams;
	}

	public Vector<String> getTamsAdic() {
		return vTamsAdic;
	}

	public Vector<String> getValores() {
		return vValores;
	}

	public Vector<String> getValoresAdic() {
		return vValoresAdic;
	}

	public Vector<String> getMascaras() {
		return vMascaras;
	}

	public Vector<String> getLabelsColunas() {
		return vLabelsColunas;
	}

	public void setTexto( String sTexto ) {

		if ( sTexto != null ) {
			this.sTexto = sTexto;
		}
		getAdic();
	}

	public void getAdic() {

		vTamsAdic = new Vector<String>();
		vLabelsAdic = new Vector<String>();
		vValoresAdic = new Vector<String>();

		for ( int i2 = 0; vValores.size() > i2; i2++ ) {
			if ( ( sTexto.indexOf( vValores.elementAt( i2 ).toString() ) ) > ( -1 ) ) {
				vTamsAdic.addElement( vTams.elementAt( i2 ).toString() );
				vLabelsAdic.addElement( vLabels.elementAt( i2 ).toString() );
				vValoresAdic.addElement( vValores.elementAt( i2 ).toString() );
			}
		}
	}
	
	public String getLote( final Integer iCodProd, final Date dData, final Connection con ) {
		return getLote( iCodProd, dData, con, null );
	}

	public String getLote( final Integer iCodProd, final Date dData, final Connection con, final String lotePrincipal ) {

		String sRetorno = sTexto;
		GregorianCalendar cal = new GregorianCalendar();

		try {

			cal.setTime( dData );
			Vector<String> vValAdic = getValoresAdic();

			if ( sRetorno != null ) {

				for ( int i = 0; vValAdic.size() > i; i++ ) {

					String sValAdic = vValoresAdic.elementAt( i ).toString();
					String sFragmento = sRetorno.substring( sRetorno.indexOf( "[" + sValAdic ) );
					String sCampo = "";
					sFragmento = sFragmento.substring( 0, ( "\\" + sFragmento ).indexOf( "]" ) );

					int iTamAdic = Funcoes.contaChar( sFragmento, '-' );

					if ( sValAdic.equals( VLR_CODPROD ) ) {
						
						sCampo = String.valueOf( iCodProd );
						if ( sCampo.length() < iTamAdic ) {
							sCampo = Funcoes.strZero( sCampo, iTamAdic );
						}
						else if ( sCampo.length() > iTamAdic ) {
							sCampo = sCampo.substring( 0, iTamAdic );
						}
					}
					else if ( sValAdic.equals( VLR_DIA ) ) {
						
						sCampo = String.valueOf( cal.get( Calendar.DAY_OF_MONTH ) );
						if ( sCampo.length() < iTamAdic ) {
							sCampo = Funcoes.strZero( sCampo, iTamAdic );
						}
						else if ( sCampo.length() > iTamAdic ) {
							sCampo = sCampo.substring( 0, iTamAdic );
						}
					}
					else if ( sValAdic.equals( VLR_MES ) ) {
						
						sCampo = String.valueOf( cal.get( Calendar.MONTH ) + 1 );
						if ( sCampo.length() < iTamAdic ) {
							sCampo = Funcoes.strZero( sCampo, iTamAdic );
						}
						else if ( sCampo.length() > iTamAdic ) {
							sCampo = sCampo.substring( 0, iTamAdic );
						}
					}
					else if ( sValAdic.equals( VLR_ANO ) ) {
						
						sCampo = String.valueOf( cal.get( Calendar.YEAR ) );
						if ( sCampo.length() > iTamAdic ) {
							sCampo = sCampo.substring( sCampo.length() - iTamAdic );
						}
					}
					else if ( sValAdic.equals( VLR_NPRODDIA ) ) {
						
						try {
							String sSQL = "SELECT coalesce(count(1)+1,1) from ppop op "
										+ "where op.codemppd=? and op.codfilialpd=? and op.codprod=? and op.dtfabrop = ?";
							PreparedStatement ps = con.prepareStatement( sSQL );
							ps.setInt( 1, Aplicativo.iCodEmp );
							ps.setInt( 2, ListaCampos.getMasterFilial( "PPOP" ) );
							ps.setInt( 3, iCodProd );
							ps.setDate( 4, Funcoes.dateToSQLDate( dData ) );
							ResultSet rs = ps.executeQuery();
							if ( rs.next() ) {
								sCampo = rs.getString( 1 );
							}
						}
						catch ( Exception err ) {
							err.printStackTrace();
						}
					}
					else if ( sValAdic.equals( VLR_LOTEPRINC ) && lotePrincipal != null ) {
						
						sCampo = lotePrincipal;
					}
					
					sRetorno = sRetorno.replaceAll( "\\" + sFragmento, sCampo );
				}
			}
		}
		catch ( Exception err ) {
			err.printStackTrace();
		}

		return sRetorno;
	}
}
