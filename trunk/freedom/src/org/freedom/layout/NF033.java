/**
 * @version 22/05/2006 <BR>
 * @author Setpoint Informática Ltda./Alex Rodrigues <BR>
 * 
 * Projeto: Freedom <BR>
 * Pacote: layout <BR>
 * Classe:
 * @(#)NFIswara.java <BR>
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
 * Layout da nota fiscal para a empresa Iswara Ltda.
 */

package org.freedom.layout;

import java.util.Vector;

import org.freedom.componentes.ImprimeOS;
import org.freedom.componentes.NF;
import org.freedom.funcoes.Funcoes;

public class NF033 extends Layout {

	public boolean imprimir( NF nf, ImprimeOS imp ) {

		boolean retorno = super.imprimir( nf, imp );
		boolean bFat = true;
		boolean bNat = true;
		final int MAXLINE = 36;
		int iItImp = 0;
		int iNumNota = 0;
		int iContaFrete = 0;
		int indexDescFisc = 0;
		int indexObs = 0;
		int iLinPag = imp.verifLinPag("NF");
		String sTemp = null;
		String sDescFisc = "";
		String sDescProd = "";
		String sObsVenda = "";
		String[] sValsCli = new String[ 4 ];
		String[] sNat = new String[ 2 ];
		String[] sVencs = new String[ 6 ];
		String[] sVals = new String[ 6 ];
		String[] sDuplics = new String[ 6 ];
		Vector vDescFisc = new Vector();
		Vector vDescProd = new Vector();

		try {

			if ( cab.next() ) {
				iNumNota = cab.getInt( NF.C_DOC );
				sObsVenda = ( cab.getString( NF.C_PEDEMIT ).trim().length() > 0 ? "Ped.cli. : " + cab.getString( NF.C_PEDEMIT ).trim() + " - " : "" ) 
							+ cab.getString( NF.C_OBSPED ).replace( "\n", "" );
			}
			for ( int i = 0; i < 6; i++ ) {
					if ( bFat ) {
						if ( parc.next() ) {
							sDuplics[ i ] = Funcoes.strZero( String.valueOf( iNumNota ), 6 ) + " / " + parc.getInt( NF.C_NPARCITREC );
							sVencs[ i ] = ( parc.getDate( NF.C_DTVENCTO ) != null ? Funcoes.dateToStrDate( parc.getDate( NF.C_DTVENCTO ) ) : "" );
							sVals[ i ] = Funcoes.strDecimalToStrCurrency( 12, 2, String.valueOf( parc.getFloat( NF.C_VLRPARC ) ) );
						}
						else {
							bFat = false;
							sDuplics[ i ] = "";
							sVencs[ i ] = "";
							sVals[ i ] = "";
						}
					}
					else {
						bFat = false;
						sDuplics[ i ] = "";
						sVencs[ i ] = "";
						sVals[ i ] = "";
					}
			}

			imp.limpaPags();

			while ( itens.next() ) {

				if ( bNat ) {
					sNat[ 0 ] = Funcoes.copy( itens.getString( NF.C_DESCNAT ).trim(), 32 );
					sNat[ 1 ] = Funcoes.setMascara( itens.getString( NF.C_CODNAT ), "#.###" );
					bNat = false;
				}

				if ( adic.next() ) {
					sValsCli[ 0 ] = ! "".equals( adic.getString( NF.C_CPFEMITAUX ) ) ? adic.getString( NF.C_CPFEMITAUX ) : cab.getString( NF.C_CPFEMIT );
					sValsCli[ 1 ] = ! "".equals( adic.getString( NF.C_NOMEEMITAUX ) ) ? adic.getString( NF.C_NOMEEMITAUX ) : cab.getString( NF.C_RAZEMIT );
					sValsCli[ 2 ] = ! "".equals( adic.getString( NF.C_CIDEMITAUX ) ) ? adic.getString( NF.C_CIDEMITAUX ) : cab.getString( NF.C_CIDEMIT );
					sValsCli[ 3 ] = ! "".equals( adic.getString( NF.C_UFEMITAUX ) ) ? adic.getString( NF.C_UFEMITAUX ) : cab.getString( NF.C_UFEMIT );
				}
				else {
					sValsCli[ 0 ] = cab.getString( NF.C_CPFEMIT );
					sValsCli[ 1 ] = cab.getString( NF.C_RAZEMIT );
					sValsCli[ 2 ] = cab.getString( NF.C_CIDEMIT );
					sValsCli[ 3 ] = cab.getString( NF.C_UFEMIT );
				}

				if ( imp.pRow() == 0 ) {

					// Imprime cabeçalho da nota ...

					imp.pulaLinha( 2, imp.comprimido() );

					if ( nf.getTipoNF() == NF.TPNF_ENTRADA ) {
						imp.say( 103, "X" );
					}
					else {
						imp.say( 88, "X" );
					}

					imp.say( 130, Funcoes.strZero( String.valueOf( iNumNota ), 6 ) );
					imp.pulaLinha( 5, imp.comprimido() );
					imp.say( 2, sNat[ 0 ] );
					imp.say( 48, sNat[ 1 ] );
					imp.pulaLinha( 3, imp.comprimido() );
					imp.say( 2, sValsCli[ 1 ] );
					imp.say( 89, sValsCli[ 0 ].equals( "" ) ? Funcoes.setMascara( cab.getString( NF.C_CNPJEMIT ), "##.###.###/####-##" ) : Funcoes.setMascara( sValsCli[ 0 ], "###.###.###-##" ) );
					imp.say( 124, ( cab.getDate( NF.C_DTEMITPED ) != null ? Funcoes.dateToStrDate( cab.getDate( NF.C_DTEMITPED ) ) : "" ) );
					imp.pulaLinha( 2, imp.comprimido() );
					imp.say( 2, Funcoes.copy( cab.getString( NF.C_ENDEMIT ), 0, 50 ).trim() + ", " + Funcoes.copy( cab.getString( NF.C_NUMEMIT ), 0, 6 ).trim() + " - " + Funcoes.copy( cab.getString( NF.C_COMPLEMIT ), 0, 9 ).trim() );
					imp.say( 65, Funcoes.copy( cab.getString( NF.C_BAIREMIT ), 0, 23 ) );
					imp.say( 102, Funcoes.setMascara( cab.getString( NF.C_CEPEMIT ), "#####-###" ) );

					if ( !itens.getString( NF.C_IMPDTSAIDA ).equals( "N" ) ) {
						imp.say( 124, ( cab.getDate( NF.C_DTSAIDA ) != null ? Funcoes.dateToStrDate( cab.getDate( NF.C_DTSAIDA ) ) : "" ) );
					}

					imp.pulaLinha( 2, imp.comprimido() );
					imp.say( 2, sValsCli[ 2 ] );
					imp.say( 58, ( !cab.getString( NF.C_DDDEMIT ).equals( "" ) ? "(" + cab.getString( NF.C_DDDEMIT ) + ")" : "" ) + ( !cab.getString( NF.C_FONEEMIT ).equals( "" ) ? Funcoes.setMascara( cab.getString( NF.C_FONEEMIT ).trim(), "####-####" ) : "" ).trim() );
					imp.say( 82, sValsCli[ 3 ] );
					imp.say( 89, !cab.getString( NF.C_RGEMIT ).equals( "" ) ? cab.getString( NF.C_RGEMIT ) : cab.getString( NF.C_INSCEMIT ) );

					// Fim do cabeçalho ...

					// Imprime dados da fatura ...

					imp.pulaLinha( 3, imp.comprimido() );
					imp.say( 4, sDuplics[ 0 ] );
					imp.say( 20, sVals[ 0 ] );
					imp.say( 36, sVencs[ 0 ] );
					imp.say( 50, sDuplics[ 1 ] );
					imp.say( 65, sVals[ 1 ] );
					imp.say( 80, sVencs[ 1 ] );
					imp.say( 94, sDuplics[ 2 ] );
					imp.say( 110, sVals[ 2 ] );
					imp.say( 125, sVencs[ 2 ] );
					imp.pulaLinha( 2, imp.comprimido() );
					imp.say( 4, sDuplics[ 3 ] );
					imp.say( 20, sVals[ 3 ] );
					imp.say( 36, sVencs[ 3 ] );
					imp.say( 50, sDuplics[ 4 ] );
					imp.say( 65, sVals[ 4 ] );
					imp.say( 80, sVencs[ 4 ] );
					imp.say( 94, sDuplics[ 5 ] );
					imp.say( 110, sVals[ 5 ] );
					imp.say( 125, sVencs[ 5 ] );
					imp.pulaLinha( 4, imp.comprimido() );

					// Fim dos dados da fatura ...

				}

				// Monta a menssagem fiscal ...

				sTemp = itens.getString( NF.C_DESCFISC ).trim();
				if ( sDescFisc.indexOf( sTemp ) == -1 ) {
					sDescFisc += sTemp;
				}	
				sTemp = itens.getString( NF.C_DESCFISC2 ).trim();
				if ( sDescFisc.indexOf( sTemp ) == -1 ) {
					sDescFisc += sTemp;
				}

				// Fim da menssagem fiscal ...
				
				// Monta descrição do produto ...
				
				sDescProd = itens.getString( NF.C_OBSITPED ).trim().length() > 0 ? itens.getString( NF.C_OBSITPED ).trim() : itens.getString( NF.C_DESCPROD ).trim();
				vDescProd = Funcoes.strToVectorSilabas( sDescProd, 50 );

				// Imprime os dados do item no corpo da nota ...
				
				for ( int i=0; i < vDescProd.size(); i++ ) {
					
					imp.pulaLinha( 1, imp.comprimido() );
					
					imp.say( 2, (String) vDescProd.get( i ) );
					
					if ( i == 0 ) {

						imp.say( 55, itens.getString( NF.C_CODFISC ) );
						imp.say( 67, Funcoes.copy( itens.getString( NF.C_ORIGFISC ), 1 ) + Funcoes.copy( itens.getString( NF.C_CODTRATTRIB ), 2 ) );
						imp.say( 73, Funcoes.copy( itens.getString( NF.C_CODUNID ), 4 ) );
						imp.say( 77, Funcoes.strDecimalToStrCurrency( 9, 2, String.valueOf( itens.getFloat( NF.C_QTDITPED ) ) ) );
						imp.say( 89, Funcoes.strDecimalToStrCurrency( 12, 2, String.valueOf( itens.getFloat( NF.C_VLRPRODITPED ) / itens.getFloat( NF.C_QTDITPED ) ) ) );
						imp.say( 103, Funcoes.strDecimalToStrCurrency( 13, 2, String.valueOf( itens.getFloat( NF.C_VLRPRODITPED ) ) ) );
						imp.say( 119, ( (int) itens.getFloat( NF.C_PERCICMSITPED ) ) + "%" );
						imp.say( 124, ( (int) itens.getFloat( NF.C_PERCIPIITPED ) ) + "%" );
						imp.say( 128, Funcoes.strDecimalToStrCurrency( 6, 2, String.valueOf( itens.getFloat( NF.C_VLRIPIITPED ) ) ) );
					}					
				}

				// Fim da impressão do item ...

				iItImp++;
				if ( iItImp == itens.getInt( NF.C_CONTAITENS ) || imp.pRow() == MAXLINE  ) {

					if ( iItImp == itens.getInt( NF.C_CONTAITENS ) ) {
						imp.pulaLinha( MAXLINE - imp.pRow(), imp.comprimido() );
					}
					
					// Imprime observação da nota

					imp.pulaLinha( 1, imp.comprimido() );
					imp.say( 2, Funcoes.copy( sObsVenda, 134 ) );

					// Imprime totais ...

					if ( iContaFrete == 0 ) {
						frete.next();
						iContaFrete++;
					}

					if ( iItImp == itens.getInt( NF.C_CONTAITENS ) ) {

						imp.pulaLinha( 3, imp.comprimido() );
						imp.say( 4, Funcoes.strDecimalToStrCurrency( 20, 2, String.valueOf( itens.getFloat( NF.C_VLRBASEICMSPED ) ) ) );
						imp.say( 32, Funcoes.strDecimalToStrCurrency( 20, 2, String.valueOf( itens.getFloat( NF.C_VLRICMSPED ) ) ) );
						imp.say( 114, Funcoes.strDecimalToStrCurrency( 20, 2, String.valueOf( itens.getFloat( NF.C_VLRPRODPED ) ) ) );
						imp.pulaLinha( 2, imp.comprimido() );
						imp.say( 4, Funcoes.strDecimalToStrCurrency( 20, 2, String.valueOf( frete.getFloat( NF.C_VLRFRETEPED ) ) ) );
						imp.say( 58, Funcoes.strDecimalToStrCurrency( 20, 2, String.valueOf( itens.getFloat( NF.C_VLRADICPED ) ) ) );
						imp.say( 87, Funcoes.strDecimalToStrCurrency( 20, 2, String.valueOf( itens.getFloat( NF.C_VLRIPIPED ) ) ) );
						imp.say( 114, Funcoes.strDecimalToStrCurrency( 20, 2, String.valueOf( itens.getFloat( NF.C_VLRLIQPED ) ) ) );
						iItImp = 0;

					}
					else {

						imp.pulaLinha( 3, imp.comprimido() );
						imp.say( 4, "********************" );
						imp.say( 32, "********************" );
						imp.say( 114, "********************" );
						imp.pulaLinha( 2, imp.comprimido() );
						imp.say( 4, "********************" );
						imp.say( 58, "********************" );
						imp.say( 87, "********************" );
						imp.say( 114, "********************" );

					}

					// Fim da impressão dos totais ...

					// Imprime informações do frete ...

					imp.pulaLinha( 3, imp.comprimido() );
					imp.say( 2, frete.getString( NF.C_RAZTRANSP ) );
					imp.say( 83, frete.getString( NF.C_TIPOFRETE ).equals( "C" ) ? "1" : "2" );
					imp.say( 90, frete.getString( NF.C_PLACAFRETE ) );
					imp.say( 105, frete.getString( NF.C_UFFRETE ) );

					if ( frete.getString( NF.C_TIPOTRANSP ).equals( "C" ) ) {
						imp.say( 116, Funcoes.setMascara( cab.getString( NF.C_CNPJEMIT ), "##.###.###/####-##" ) );
					}
					else {
						imp.say( 116, Funcoes.setMascara( frete.getString( NF.C_CNPJTRANSP ), "##.###.###/####-##" ) );
					}

					imp.pulaLinha( 2, imp.comprimido() );
					imp.say( 2, frete.getString( NF.C_ENDTRANSP ).trim() + ", " + frete.getInt( NF.C_NUMTRANSP ) );
					imp.say( 64, frete.getString( NF.C_CIDTRANSP ) );
					imp.say( 105, frete.getString( NF.C_UFTRANSP ) );

					if ( frete.getString( NF.C_TIPOTRANSP ).equals( "C" ) ) {
						imp.say( 116, cab.getString( NF.C_INSCEMIT ) );
					}
					else {
						imp.say( 116, frete.getString( NF.C_INSCTRANSP ) );
					}

					imp.pulaLinha( 2, imp.comprimido() );
					imp.say( 2, Funcoes.strDecimalToStrCurrency( 10, 0, String.valueOf( frete.getString( NF.C_QTDFRETE ) ) ) );
					imp.say( 19, Funcoes.copy( frete.getString( NF.C_ESPFRETE ), 17 ) );
					imp.say( 64, Funcoes.copy( frete.getString( NF.C_MARCAFRETE ), 18 ) );
					imp.say( 85, Funcoes.copy( frete.getString( NF.C_CONHECFRETEPED ), 20 ) );
					imp.say( 112, Funcoes.strDecimalToStrCurrency( 10, 2, String.valueOf( frete.getFloat( NF.C_PESOBRUTO ) ) ) );
					imp.say( 125, Funcoes.strDecimalToStrCurrency( 10, 2, String.valueOf( frete.getString( NF.C_PESOLIQ ) ) ) );
					imp.pulaLinha( 2, imp.comprimido() );
					
					// Fim da impressão do frete ...

					// Imprime observação e classificações fiscais ...

					vDescFisc = Funcoes.strToVectorSilabas( sDescFisc, 75 );

					int aux = 0;
					for ( int i = indexObs; i < 6; i++ ) {
						if ( aux < vDescFisc.size() ) {
							imp.pulaLinha( 1, imp.comprimido() );
							if ( vDescFisc.size() > 0 && indexDescFisc < vDescFisc.size() ) {
								imp.say( 2, (String) vDescFisc.elementAt( indexDescFisc++ ) );
							}
						}
						else {
							imp.pulaLinha( 1, imp.comprimido() );
						}
					}

					// Fim da observação ...

					imp.pulaLinha( 5, imp.comprimido() );
					imp.say( 130, Funcoes.strZero( String.valueOf( iNumNota ), 6 ) );
					
					imp.pulaLinha( iLinPag - imp.pRow(), imp.comprimido());
				}
			}

			imp.fechaGravacao();
			retorno = true;

		} catch ( Exception err ) {
			Funcoes.mensagemErro( null, "Erro ao montar nota \n" + err.getMessage() );
			err.printStackTrace();
		} finally {
			System.gc();
		}

		return retorno;

	}
}
