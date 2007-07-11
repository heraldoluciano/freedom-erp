/**
 * @version 07/2007 <BR>
 * @author Setpoint Informática Ltda./Alex Rodrigues<BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.std <BR>
 * Classe:
 * @(#)FRetSiacc.java <BR>
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
 * Tela de leitura do arquivo de retorno de Siacc.
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
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Funcoes;
import org.freedom.modulos.fnc.SiaccUtil.Reg;
import org.freedom.modulos.fnc.SiaccUtil.RegB;
import org.freedom.modulos.fnc.SiaccUtil.RegF;
import org.freedom.modulos.fnc.SiaccUtil.RegJ;
import org.freedom.telas.Aplicativo;


public class FRetSiacc extends FRetFBN {

	private static final long serialVersionUID = 1L;
	
	public FRetSiacc() {
		
		super();
	}

	/* (non-Javadoc)
	 * @see org.freedom.modulos.fnc.FRetFBN#execImportar()
	 */
	@Override
	protected boolean execImportar() {

		boolean retorno = true;
		FileReader fileReaderSiacc = null;
		ArrayList<SiaccUtil.Reg> list = new ArrayList<SiaccUtil.Reg>();

		setCursor( Cursor.getPredefinedCursor( Cursor.WAIT_CURSOR ) );

		if ( "".equals( txtCodBanco.getVlrString() ) ) {
			Funcoes.mensagemInforma( this, "Selecione o Banco!!" );
			txtCodBanco.requestFocus();
		}
		else {

			lbStatus.setText( "     Lendo do arquivo ..." );

			FileDialog fileDialogSiacc = null;
			fileDialogSiacc = new FileDialog( Aplicativo.telaPrincipal, "Importar arquivo." );
			fileDialogSiacc.setFile( "*.cmp" );
			fileDialogSiacc.setVisible( true );

			if ( fileDialogSiacc.getFile() == null ) {
				lbStatus.setText( "" );
				retorno = false;
			}
			else {

				String sFileName = fileDialogSiacc.getDirectory() + fileDialogSiacc.getFile();
				File fileSiacc = new File( sFileName );

				if ( fileSiacc.exists() ) {

					try {

						fileReaderSiacc = new FileReader( fileSiacc );

						if ( fileReaderSiacc == null ) {
							Funcoes.mensagemInforma( this, "Arquivo não encontrado" );
						}
						else {
							if ( leArquivo( fileReaderSiacc, list ) ) {

								if ( ! montaGrid( list ) ) {
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

	private boolean leArquivo( final FileReader fileReaderSiacc, final ArrayList<SiaccUtil.Reg> list ) throws IOException {

		boolean retorno = true;
		char tipo;
		String line = null;
		BufferedReader in = new BufferedReader( fileReaderSiacc );

		try {
			learquivo : while ( ( line = in.readLine() ) != null ) {

				tipo = line.charAt( 0 );
				switch ( tipo ) {
					case 'A' :
						list.add( new SiaccUtil().new RegA( line ) );
						break;
					case 'B' :
						list.add( new SiaccUtil().new RegB( line ) );
						break;
					case 'C' :
						list.add( new SiaccUtil().new RegC( line ) );
						break;
					case 'E' :
						list.add( new SiaccUtil().new RegE( line ) );
						break;
					case 'F' :
						list.add( new SiaccUtil().new RegF( line ) );
						break;
					case 'J' :
						list.add( new SiaccUtil().new RegJ( line ) );
						break;
					case 'H' :
						list.add( new SiaccUtil().new RegH( line ) );
						break;
					case 'X' :
						list.add( new SiaccUtil().new RegX( line ) );
						break;
					case 'Z' :
						list.add( new SiaccUtil().new RegZ( line ) );
						break;
					default :
						break learquivo;
				}
			}

			lbStatus.setText( "     Arquivo lido ..." );
		} catch ( ExceptionSiacc e ) {
			Funcoes.mensagemErro( this, "Erro lendo o arquivo!\n" + e.getMessage() );
			e.printStackTrace();
			retorno = false;
			lbStatus.setText( "" );
		}

		in.close();

		return retorno;
	}

	private boolean montaGrid( ArrayList<SiaccUtil.Reg> list ) {

		boolean retorno = true;
		int row = 0;

		if ( list != null ) {

			lbStatus.setText( "     Carregando tabela ..." );
			
			tab.limpa();

			List<Object> infocli = new ArrayList<Object>();
			List<RegB> regsB = null;
			String regJ = null;

			try {
				
				for ( Reg reg : list ) {

					if ( reg.getTiporeg() == 'F' ) {

						infocli.clear();

						if ( !setInfoCli( ( (RegF) reg ).getCodRec(), ( (RegF) reg ).getNparcItRec(), infocli ) ) {
							retorno = false;
							break;
						}
						if ( infocli.size() == EColInfoCli.values().length ) {

							tab.adicLinha();
							
							if ( "00".equals( ( (RegF) reg ).getCodRetorno() ) ) {
								
								tab.setValor( imgok, row, EColTab.STATUS.ordinal() );
								tab.setValor( new Boolean( Boolean.TRUE ), row, EColTab.SEL.ordinal() );
							}
							else {
								
								updateStatusRetorno( (RegF) reg );
								
								tab.setValor( imgcancel, row, EColTab.STATUS.ordinal() );
								tab.setValor( new Boolean( Boolean.FALSE ), row, EColTab.SEL.ordinal() );
							}
							
							tab.setValor( (String) infocli.get( EColInfoCli.RAZCLI.ordinal() ), row, EColTab.RAZCLI.ordinal() ); // Razão social do cliente
							tab.setValor( (Integer) infocli.get( EColInfoCli.CODCLI.ordinal() ), row, EColTab.CODCLI.ordinal() ); // Cód.cli.
							tab.setValor( (Integer) infocli.get( EColInfoCli.CODREC.ordinal() ), row, EColTab.CODREC.ordinal() ); // Cód.rec.
							tab.setValor( (String) infocli.get( EColInfoCli.DOCREC.ordinal() ), row, EColTab.DOCREC.ordinal() ); // Doc
							tab.setValor( (Integer) infocli.get( EColInfoCli.NPARCITREC.ordinal() ), row, EColTab.NRPARC.ordinal() ); // Nro.Parc.
							tab.setValor( Funcoes.bdToStr( (BigDecimal) infocli.get( EColInfoCli.VLRAPAGITREC.ordinal() ) ), row, EColTab.VLRAPAG.ordinal() ); // Valor
							tab.setValor( (Date) infocli.get( EColInfoCli.DTITREC.ordinal() ), row, EColTab.DTREC.ordinal() ); // Emissão
							tab.setValor( (Date) infocli.get( EColInfoCli.DTVENCITREC.ordinal() ), row, EColTab.DTVENC.ordinal() ); // Vencimento
							tab.setValor( Funcoes.bdToStr( (BigDecimal) ( (RegF) reg ).getValorDebCred() ), row, EColTab.VLRPAG.ordinal() ); // Valor pago
							tab.setValor( (Date) ( (RegF) reg ).getDataVenc(), row, EColTab.DTPAG.ordinal() ); // Data pgto.
							tab.setValor( (String) infocli.get( EColInfoCli.NUMCONTA.ordinal() ), row, EColTab.NUMCONTA.ordinal() ); // Conta
							tab.setValor( (String) infocli.get( EColInfoCli.CODPLAN.ordinal() ), row, EColTab.CODPLAN.ordinal() ); // Planejamento
							tab.setValor( Funcoes.bdToStr( new BigDecimal( 0 ) ), row, EColTab.VLRDESC.ordinal() ); // VLRDESC
							tab.setValor( Funcoes.bdToStr( new BigDecimal( 0 ) ), row, EColTab.VLRJUROS.ordinal() ); // VLRJUROS
							tab.setValor( "BAIXA AUTOMÁTICA SIACC", row, EColTab.OBS.ordinal() ); // HISTÓRICO
							tab.setValor( (String) infocli.get( EColInfoCli.TIPOFEBRABAN.ordinal() ), row, EColTab.TIPOFEBRABAN.ordinal() );
							tab.setValor( ( (RegF) reg ).getCodRetorno(), row, EColTab.CODRET.ordinal() ); // código retorno
							tab.setValor( getMenssagemRet( ( (RegF) reg ).getCodRetorno() ), row, EColTab.MENSSAGEM.ordinal() ); // Menssagem de erro
							
							row++;
						}
					}
					else if ( reg.getTiporeg() == 'B' ) {
						
						if ( regsB == null ) {
							regsB = new ArrayList<RegB>();
						}
						
						regsB.add( (RegB) reg );
					}
					else if ( reg.getTiporeg() == 'J' ) {
						
						regJ = ( (RegJ) reg ).getMenssagemInfo();
					}
				}

				if ( row > 0 ) {
					lbStatus.setText( "     Tabela carregada ..." );
				}
				else {
					lbStatus.setText( "     Informações do cliente não encontradas ..." );
				}
				
				montaDlRegB( regsB );
				
				if ( regJ != null && regJ.trim().length() > 0 ) {
					
					Funcoes.mensagemInforma( this, regJ );
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

	private void montaDlRegB( final List<RegB> regs ) {

		if ( regs != null ) {

			DLRegB dl = new DLRegB( this );
			
			if ( dl.montaGrid( regs, con ) ) {
			
				dl.setVisible( true );
			}
		}
	}
	
	private void updateStatusRetorno( final RegF registro ) {
		
		try {
			
			StringBuilder sql = new StringBuilder();
			sql.append( "UPDATE FNFBNREC SET SITRETORNO=?" );
			sql.append( "WHERE CODEMP=? AND CODFILIAL=? AND CODREC=? AND NPARCITREC=? " );
			
			PreparedStatement ps = con.prepareStatement( sql.toString() );
			ps.setString( 1, registro.getCodRetorno() );
			ps.setInt( 2, Aplicativo.iCodEmp );
			ps.setInt( 3, ListaCampos.getMasterFilial( "FNFBNREC" ) );
			ps.setInt( 4, registro.getCodRec() );
			ps.setInt( 5, registro.getNparcItRec() );
			ps.executeUpdate();			
			ps.close();
			
			if ( ! con.getAutoCommit() ) {
				con.commit();
			}
		}
		catch ( Exception e ) {
			Funcoes.mensagemErro( this, "Erro ao atualizar status do registro!\n" + e.getMessage(), true, con, e );
		}
	}

}
