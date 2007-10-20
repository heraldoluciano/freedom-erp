/**
 * @version 07/2007 <BR>
 * @author Setpoint Informática Ltda. / Alex Rodrigues<BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.std <BR>
 * Classe:
 * @(#)FRetCnab.java <BR>
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
 * Tela de retorno de arquivo de CNAB.
 * 
 */

package org.freedom.modulos.fnc;

import java.awt.Cursor;
import java.awt.FileDialog;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

import org.freedom.funcoes.Funcoes;
import org.freedom.modulos.fnc.CnabUtil.Receber;
import org.freedom.modulos.fnc.CnabUtil.Reg;
import org.freedom.modulos.fnc.CnabUtil.Reg3P;
import org.freedom.modulos.fnc.CnabUtil.Reg3Q;
import org.freedom.modulos.fnc.CnabUtil.Reg3U;
import org.freedom.telas.Aplicativo;


public class FRetCnab extends FRetFBN {

	private static final long serialVersionUID = 1l;
	
	private final CnabUtil cnabutil = new CnabUtil();
	
	public FRetCnab() {
		
		super();
	}

	/* (non-Javadoc)
	 * @see org.freedom.modulos.fnc.FRetFBN#execImportar()
	 */
	@Override
	protected boolean execImportar() {

		boolean retorno = true;
		ArrayList<Reg> registros = new ArrayList<Reg>();

		setCursor( Cursor.getPredefinedCursor( Cursor.WAIT_CURSOR ) );

		if ( "".equals( txtCodBanco.getVlrString() ) ) {
			Funcoes.mensagemInforma( this, "Selecione o Banco!!" );
			txtCodBanco.requestFocus();
		}
		else {

			lbStatus.setText( "     Lendo do arquivo ..." );

			FileDialog fileDialogCnab = null;
			fileDialogCnab = new FileDialog( Aplicativo.telaPrincipal, "Importar arquivo." );
			//fileDialogSiacc.setFile( "*.cmp" );
			fileDialogCnab.setVisible( true );

			if ( fileDialogCnab.getFile() == null ) {
				lbStatus.setText( "" );
				retorno = false;
			}
			else {

				String sFileName = fileDialogCnab.getDirectory() + fileDialogCnab.getFile();
				File fileCnab = new File( sFileName );

				if ( fileCnab.exists() ) {

					try {

						FileReader fileReaderCnab = new FileReader( fileCnab );

						if ( fileReaderCnab == null ) {
							Funcoes.mensagemInforma( this, "Arquivo não encontrado" );
						}
						else {
							if ( leArquivo( fileReaderCnab, registros ) ) {

								if ( ! montaGrid( registros ) ) {
									Funcoes.mensagemInforma( this, "Nenhum registro de retorno encontrado." );
									lbStatus.setText( "" );
									retorno = false;
								}
							}
						}
					} catch ( IOException ioError ) {
						Funcoes.mensagemErro( this, "Erro ao ler o arquivo: " + sFileName + "\n" + ioError.getMessage() );
						lbStatus.setText( "" );
						retorno = false;
					}
				}
				else {
					Funcoes.mensagemErro( this, "Arquivo " + sFileName + " não existe!" );
					retorno = false;
				}
			}
		}

		setCursor( Cursor.getPredefinedCursor( Cursor.DEFAULT_CURSOR ) );
		
		return retorno;
	}	
	
	private boolean leArquivo( final FileReader fileReaderCnab, final ArrayList<Reg> list ) throws IOException {

		boolean retorno = true;
		char tipo;
		char seguimento;
		String line = null;
		BufferedReader in = new BufferedReader( fileReaderCnab );

		try {
			
			learquivo : while ( ( line = in.readLine() ) != null ) {

				tipo = line.charAt( 7 );
				
				switch ( tipo ) {
					case '1' :
						list.add( cnabutil.new Reg1( line ) );
						break;
					case '3' :
						
						seguimento = line.charAt( 13 );
						
						switch ( seguimento ) {
							case 'P' :
								list.add( cnabutil.new Reg3P( line ) );
								break;
							case 'Q' :
								list.add( cnabutil.new Reg3Q( line ) );
								break;
							case 'R' :
								list.add( cnabutil.new Reg3R( line ) );
								break;
							case 'S' :
								list.add( cnabutil.new Reg3S( line ) );
								break;
							case 'T' :
								list.add( cnabutil.new Reg3T( line ) );
								break;
							case 'U' :
								list.add( cnabutil.new Reg3U( line ) );
								break;
							default :
								break learquivo;
						}
						
						break;
					case '5' :
						list.add( cnabutil.new Reg5( line ) );
						break;
					default :
						break learquivo;
				}
			}

			lbStatus.setText( "     Arquivo lido ..." );
		} catch ( ExceptionCnab e ) {
			Funcoes.mensagemErro( this, "Erro lendo o arquivo!\n" + e.getMessage() );
			e.printStackTrace();
			retorno = false;
			lbStatus.setText( "" );
		}

		in.close();

		return retorno;
	}

	private boolean montaGrid( ArrayList<Reg> registros ) {

		boolean retorno = true;
		int row = 0;

		if ( registros != null ) {

			lbStatus.setText( "     Carregando tabela ..." );
			
			tab.limpa();

			try {

				String regJ = null;
				Reg3P reg3P = null;
				Reg3Q reg3Q = null;
				Reg3U reg3U = null;
				Receber rec = null;
				BigDecimal valorPago;
				Date dataPagamento;
				BigDecimal valorDesconto;
				BigDecimal valorJuros;
				
				for ( Reg reg : registros ) {

					if ( reg instanceof Reg3P  ) {

						reg3P = (Reg3P) reg;
						rec = reg3P.getIdentTitEmp();						
					}
					else if ( reg instanceof Reg3Q ) {
						
						reg3Q = (Reg3Q) reg;
					}
					else if ( reg instanceof Reg3U ) {
						
						reg3U = (Reg3U) reg;
						
						tab.adicLinha();
						tab.setValor( reg3Q.getRazCli(), row, EColTab.RAZCLI.ordinal() ); // Razão social do cliente
						//tab.setValor( reg3Q.get, row, EColTab.CODCLI.ordinal() ); // Cód.cli.							
						tab.setValor( rec.getCodrec(), row, EColTab.CODREC.ordinal() ); // Cód.rec.							
						tab.setValor( rec.getDocrec(), row, EColTab.DOCREC.ordinal() ); // Doc
						tab.setValor( rec.getNrparcrec(), row, EColTab.NRPARC.ordinal() ); // Nro.Parc.							
						tab.setValor( Funcoes.bdToStr( rec.getValorApagar() ), row, EColTab.VLRAPAG.ordinal() ); // Valor
						tab.setValor( rec.getEmissao(), row, EColTab.DTREC.ordinal() ); // Emissão
						tab.setValor( rec.getVencimento(), row, EColTab.DTVENC.ordinal() ); // Vencimento
						
						tab.setValor( reg3U.getVlrPago(), row, EColTab.VLRPAG.ordinal() ); // Valor pago
						tab.setValor( reg3U.getDataOcorr(), row, EColTab.DTPAG.ordinal() ); // Data pgto.
						
						tab.setValor( rec.getConta(), row, EColTab.NUMCONTA.ordinal() ); // Conta
						tab.setValor( rec.getPlanejamento(), row, EColTab.CODPLAN.ordinal() ); // Planejamento
						
						tab.setValor( reg3U.getVlrDesc(), row, EColTab.VLRDESC.ordinal() ); // VLRDESC
						tab.setValor( reg3U.getVlrJurosMulta(), row, EColTab.VLRJUROS.ordinal() ); // VLRJUROS
						
						tab.setValor( "BAIXA AUTOMÁTICA CNAB", row, EColTab.OBS.ordinal() ); // HISTÓRICO
						
						//tab.setValor( (String) infocli.get( EColInfoCli.TIPOFEBRABAN.ordinal() ), row, EColTab.TIPOFEBRABAN.ordinal() );
						//tab.setValor( ( (RegF) reg ).getCodRetorno(), row, EColTab.CODRET.ordinal() ); // código retorno
						//tab.setValor( getMenssagemRet( ( (RegF) reg ).getCodRetorno() ), row, EColTab.MENSSAGEM.ordinal() ); // Menssagem de erro*/
						
						row++;
					}
				}

				if ( row > 0 ) {
					lbStatus.setText( "     Tabela carregada ..." );
				}
				else {
					lbStatus.setText( "     Informações do cliente não encontradas ..." );
				}
				
			} catch ( Exception e ) {
				retorno = false;
				Funcoes.mensagemErro( this, "Erro no carregamento da tabela!\n" + e.getMessage() );
				e.printStackTrace();
				lbStatus.setText( "" );
			}
		}
		else {
			retorno = false;
		}

		return retorno;
	}
}
