/**
 * @version 05/06/2000 <BR>
 * @author Setpoint Informática Ltda./Anderson Sanchez <BR>
 * 
 * Projeto: Freedom <BR>
 * Pacote: org.freedom.telas <BR>
 * Classe:
 * @(#)AplicativoPDV.java <BR>
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
 * Classe herdada de Aplicativo contendo funções específicas para ponto de venda.
 */
package org.freedom.telas;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;

import org.freedom.componentes.ListaCampos;
import org.freedom.ecf.app.ControllerECF;
import org.freedom.funcoes.Funcoes;
import org.freedom.modulos.pdv.FAbreCaixa;
import org.freedom.tef.app.ControllerTef;
import org.freedom.tef.driver.text.TextTefProperties;

public class AplicativoPDV extends AplicativoPD {

	public static boolean bECFTerm = false;

	public static boolean bTEFTerm = false;

	public static int iCodCaixa = 0;
	
	private static String ecfdriver = null;
	
	private static String portaECF = "COM1" ;
	
	private static String pluginVenda;
	
	private static ControllerTef controllerTef = null;


	public AplicativoPDV(String sIcone, String sSplash, int iCodSis, String sDescSis, int iCodModu, String sDescModu, String sDirImagem) {
	    super(sIcone, sSplash, iCodSis, sDescSis,iCodModu, sDescModu, sDirImagem,new FPrincipalPD(null, "bgFreedom2.jpg"),LoginPD.class);

	    pluginVenda = getParameter( "pluginVenda" );
	    
	    setPortaECF( getParameter( "portaecf" ) );	    
	    setEcfdriver( getParameter( "ecfdriver" ) );	
	}	

	public static void setPortaECF( String porta ) {
		portaECF = porta;
	}
	
	public static String getPortaECF() {
		return portaECF;
	}
	
	public static String getEcfdriver() {	
		return ecfdriver;
	}
	
	public static void setEcfdriver( String ecfdriver ) {	
		AplicativoPDV.ecfdriver = ecfdriver;
	}

    //isso aqui é temporario...
	public static boolean usaEcfDriver() {			
		return ecfdriver!=null;		
	}

	public synchronized static boolean pegaValorINI( final Connection con ) {

		FAbreCaixa tela = new FAbreCaixa();
		tela.setConexao( con );
		tela.setVisible( true );

		return tela.OK;
	}

	public static synchronized int abreCaixa( final Connection con, final ControllerECF ecf ) {

		int result = -1;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {

			ps = con.prepareStatement( "SELECT IRETORNO FROM PVVERIFCAIXASP(?,?,?,?,?,?)" ); 
			ps.setInt( 1, iCodCaixa );
			ps.setInt( 2, iCodEmp );
			ps.setInt( 3, iCodFilial );
			ps.setDate( 4, Funcoes.dateToSQLDate( new Date() ) );
			ps.setInt( 5, iCodFilialPad );
			ps.setString( 6, strUsuario );
			rs = ps.executeQuery();
			
			if ( rs.next() ) {
				
				result = rs.getInt( 1 );
				
				validacao : {
					if ( result == 0 ) {
						// verifica redução Z pois o caixa anterior pode não ter executado
						// e executado por engano para abertura do novo caixa.
						/*if ( !Aplicativo.bModoDemo && ecf.reducaoZExecutada() ) {
							result = 11;
						}
						else*/ if ( ! pegaValorINI( con ) ) {
							result = -1;
							break validacao;
						}
						else {
							break validacao;							
						}
					}
					// warnings
					if ( result == 1 ) { 
						Funcoes.mensagemInforma( null, "Caixa já está aberto!" );
						break validacao;
					}
					else if ( result == 2 ) {
						Funcoes.mensagemInforma( null, "Caixa anterior fechado sem redução \"Z\"." +
													 "\nA leitura da memória fiscal deverá ser feita pelo usuario." );
						break validacao;
					}
					else if ( result == 3 ) {
						Funcoes.mensagemInforma( null, "Caixa anterior não foi fechado!" +
								                    "\nO caixa deverá ser fechado sem a excução da redução \"Z\"." +
								                    "\nA leitura da memória fiscal deverá ser feita pelo usuario." );						
						break validacao;
					}
					// erros
					if ( result == 11 ) {
						killProg( 1, "Já foi realizada leitura \"Z\" neste caixa hoje!" );
						break validacao;
					}
					else if ( result == 12 ) {
						killProg( 2, "Caixa foi aberto com outro usuário!" );
						break validacao ;
					}
					else if ( result == 13 ) {
						killProg( 3, "Tentativa de abertura de caixa retroativo!" );
						break validacao;
					}
					else {
						killProg( 4, "Erro na ultima transacão de caixa." );
						break validacao;
					}					
				}
				
				rs.close();
				ps.close();
				
				if ( !con.getAutoCommit() ) {
					con.commit();
				}

			}
			else {
				killProg( 5, "Não foi possível abrir o caixa!" );
			}
			
		} catch ( Exception err ) {
			killProg( 6, "Erro abrir o caixa!\n" + err.getMessage() );
		}
		
		return result;
	}

	public synchronized static boolean caixaAberto( final Connection con ) {
		
		boolean result = false;
		
		try {
			
			PreparedStatement ps = con.prepareStatement( 
				"SELECT FIRST 1 DTAMOV FROM PVMOVCAIXA WHERE CODEMP=? AND CODFILIAL=? AND CODCAIXA=? ORDER BY DTAMOV DESC, NROMOV DESC" );
			
			ps.setInt( 1, iCodEmp );
			ps.setInt( 2, iCodFilial );
			ps.setInt( 3, iCodCaixa );
			ResultSet rs = ps.executeQuery();
			
			if ( rs.next() ) {
				
				Calendar hoje = Calendar.getInstance();
				Calendar diacaixa = Calendar.getInstance();
				diacaixa.setTime( Funcoes.sqlDateToDate( rs.getDate( "DTAMOV" ) ) );
				
				if ( hoje.get( Calendar.YEAR ) == diacaixa.get( Calendar.YEAR ) 
						&& hoje.get( Calendar.MONTH ) == diacaixa.get( Calendar.MONTH )
							&& hoje.get( Calendar.DAY_OF_MONTH ) == diacaixa.get( Calendar.DAY_OF_MONTH ) ) { 		
					result = true;
				}
			}
			
			rs.close();
			ps.close();
			
			if ( !con.getAutoCommit() ) {
				con.commit();
			}
			
		} catch ( SQLException err ) {
			Funcoes.mensagemErro( null, "Não foi possível buscar o saldo atual.\n" + err.getMessage(), true, con, err );
			err.printStackTrace();
		}
		
		return result;
	}

	public synchronized void setECF() {
		
		try {
			
			PreparedStatement ps = con.prepareStatement( 
					"SELECT CODCAIXA FROM PVCAIXA WHERE CODEMP=? AND CODFILIAL=? AND CODEST=?" );
			ps.setInt( 1, iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "PVCAIXA" ) );
			ps.setInt( 3, iNumEst );
			ResultSet rs = ps.executeQuery();
			
			if ( rs.next() ) {
				iCodCaixa = rs.getInt( "CODCAIXA" ); 
			}
			
			rs.close();
			ps.close();
			
			if ( !con.getAutoCommit() ) {
				con.commit();
			}
			
			ps = con.prepareStatement( 
					"SELECT CX.ECFCAIXA,CX.TEFCAIXA FROM PVCAIXA CX " +
					"WHERE CODCAIXA=? AND CODFILIAL=? AND CODEMP=?" );
			ps.setInt( 1, iCodCaixa );
			ps.setInt( 2, Aplicativo.iCodFilial );
			ps.setInt( 3, Aplicativo.iCodEmp );
			
			rs = ps.executeQuery();
			
			if ( rs.next() ) {
				
				bECFTerm = "S".equals( rs.getString( "ECFCaixa" ) );
				bTEFTerm = "S".equals( rs.getString( "TEFCaixa" ) );
			}
			
			rs.close();
			ps.close();
			
			if ( !con.getAutoCommit() ) {
				con.commit();
			}
			
		} catch ( Exception err ) {
			err.printStackTrace();
			killProg( 6, "Erro ao verificar o caixa!\n" + err.getMessage() );
		}

	}
	
	public static void setControllerTef( final ControllerTef controllerTef ) {
		AplicativoPDV.controllerTef = controllerTef;
	}
	
	public static ControllerTef getControllerTef() throws Exception {
		
		if ( controllerTef == null ) {
			controllerTef = new ControllerTef();
			
			final TextTefProperties textTefProperties = new TextTefProperties();
			textTefProperties.set( TextTefProperties.PATH_SEND, AplicativoPDV.tefSend );
			textTefProperties.set( TextTefProperties.PATH_RESPONSE, AplicativoPDV.tefRequest );
			
			controllerTef.initializeControllerTextTef( textTefProperties, new File( AplicativoPDV.tefFlags ), ControllerTef.TEF_TEXT );
		}

		return controllerTef;
	}

	public static String getPluginVenda() {		
		return pluginVenda;
	}

}
