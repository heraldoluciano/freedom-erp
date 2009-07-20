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
 * Este arquivo é parte do sistema Freedom-ERP, o Freedom-ERP é um software livre; você pode redistribui-lo e/ou <BR>
 * modifica-lo dentro dos termos da Licença Pública Geral GNU como publicada pela Fundação do Software Livre (FSF); <BR>
 * na versão 2 da Licença, ou (na sua opnião) qualquer versão. <BR>
 * Este programa é distribuido na esperança que possa ser  util, mas SEM NENHUMA GARANTIA; <BR>
 * sem uma garantia implicita de ADEQUAÇÂO a qualquer MERCADO ou APLICAÇÃO EM PARTICULAR. <BR>
 * Veja a Licença Pública Geral GNU para maiores detalhes. <BR>
 * Você deve ter recebido uma cópia da Licença Pública Geral GNU junto com este programa, se não, <BR>
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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Funcoes;
import org.freedom.modulos.fnc.CnabUtil.Receber;
import org.freedom.modulos.fnc.CnabUtil.Reg;
import org.freedom.modulos.fnc.CnabUtil.Reg1;
import org.freedom.modulos.fnc.CnabUtil.Reg3T;
import org.freedom.modulos.fnc.CnabUtil.Reg3U;
import org.freedom.modulos.fnc.CnabUtil.RegHeader;
import org.freedom.telas.Aplicativo;


public class FRetCnab extends FRetFBN {

	private static final long serialVersionUID = 1l;
	
	private final CnabUtil cnabutil = new CnabUtil();
	
	public FRetCnab() {
		
		super( FPrefereFBB.TP_CNAB );
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
			fileDialogCnab.setFile( "*.ret" );
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
									//Funcoes.mensagemInforma( this, "Nenhum registro de retorno encontrado." );
									lbStatus.setText( "     Nenhum registro de retorno encontrado." );
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
			 
			while ( ( line = in.readLine() ) != null ) {

				tipo = line.charAt( 7 );
				
				switch ( tipo ) {
					case '0' :
						list.add( cnabutil.new RegHeader( line ) );
						break;
					case '1' :
						Reg1 reg1 = cnabutil.new Reg1( line );
						list.add( reg1 );
						if ( reg1 == null 
								|| ! reg1.getCodBanco().trim().equals( txtCodBanco.getVlrString().trim() ) ) {
							Funcoes.mensagemErro( this, "Arquivo de retorno não refere-se ao banco selecionado!" );
							return false;
						}
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
								break; 
						}
						
						break;
					case '5' :
						list.add( cnabutil.new Reg5( line ) );
						break;
					default :
						break;
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

				RegHeader header = null;
				Reg3T reg3T = null;
				Reg3U reg3U = null;
				Receber rec = null;
				BigDecimal valorPago;
				Date dataPagamento;
				BigDecimal valorDesconto;
				BigDecimal valorJuros;
				
				for ( Reg reg : registros ) {
					
					if ( reg instanceof RegHeader  ) {		
						
						header = (RegHeader) reg;	
					}
					else if ( reg instanceof Reg3T  ) {		
						
						reg3T = (Reg3T) reg;	
						int[] chaveRec = getChaveReceber( reg3T );
						rec = findReceber( chaveRec[0], chaveRec[1] );	
					}
					else if ( reg instanceof Reg3U  ) {

						reg3U = (Reg3U) reg;
						
						if ( rec != null ) {
							
							tab.adicLinha();
							
							if ( reg3U.getDataEfetvCred() != null ) {								
								tab.setValor( imgok, row, EColTab.STATUS.ordinal() );
								tab.setValor( new Boolean( reg3U.getVlrLiqCred().floatValue() > 0.00 ), row, EColTab.SEL.ordinal() );
								//tab.setValor( getMenssagemRet( txtCodBanco.getVlrString(), reg3T.getCodRejeicoes(), FPrefereFBB.TP_CNAB ), row, EColTab.MENSSAGEM.ordinal() ); // Menssagem de erro
							}
							else {								
								tab.setValor( imgcancel, row, EColTab.STATUS.ordinal() );
								tab.setValor( new Boolean( Boolean.FALSE ), row, EColTab.SEL.ordinal() );
							}
							tab.setValor( rec.getRazcliente(), row, EColTab.RAZCLI.ordinal() ); // Razão social do cliente
							tab.setValor( rec.getCodcliente(), row, EColTab.CODCLI.ordinal() ); // Cód.cli.							
							tab.setValor( rec.getCodrec(), row, EColTab.CODREC.ordinal() ); // Cód.rec.							
							tab.setValor( rec.getDocrec(), row, EColTab.DOCREC.ordinal() ); // Doc
							tab.setValor( rec.getNrparcrec(), row, EColTab.NRPARC.ordinal() ); // Nro.Parc.							
							tab.setValor( Funcoes.bdToStr( rec.getValorApagar() ), row, EColTab.VLRAPAG.ordinal() ); // Valor
							tab.setValor( rec.getEmissao(), row, EColTab.DTREC.ordinal() ); // Emissão
							tab.setValor( rec.getVencimento(), row, EColTab.DTVENC.ordinal() ); // Vencimento
							tab.setValor( Funcoes.bdToStr( reg3U.getVlrPago() ), row, EColTab.VLRPAG.ordinal() ); // Valor pago
							tab.setValor( reg3U.getDataEfetvCred() != null ? Funcoes.dateToStrDate( reg3U.getDataEfetvCred() ) : "", row, EColTab.DTPAG.ordinal() ); // Data pgto.							
							tab.setValor( rec.getConta(), row, EColTab.NUMCONTA.ordinal() ); // Conta
							tab.setValor( rec.getPlanejamento(), row, EColTab.CODPLAN.ordinal() ); // Planejamento		
							tab.setValor( reg3U.getVlrDesc(), row, EColTab.VLRDESC.ordinal() ); // VLRDESC				
							tab.setValor( Funcoes.bdToStr( reg3U.getVlrJurosMulta()), row, EColTab.VLRJUROS.ordinal() ); // VLRJUROS
							tab.setValor( "BAIXA AUTOMÁTICA CNAB", row, EColTab.OBS.ordinal() ); // HISTÓRICO							
							tab.setValor( FPrefereFBB.TP_CNAB, row, EColTab.TIPOFEBRABAN.ordinal() );
							tab.setValor( reg3T.getCodRejeicoes(), row, EColTab.CODRET.ordinal() ); // código retorno
							//tab.setValor( getMenssagemRet( txtCodBanco.getVlrString(), reg3T.getCodRejeicoes().trim(), FPrefereFBB.TP_CNAB ), row, EColTab.MENSSAGEM.ordinal() ); // Menssagem de erro
							
							row++;
							rec = null;
						}
					}
				}

				if ( row > 0 ) {
					lbStatus.setText( "     Tabela carregada ..." );
				}
				else if ( header != null ) {
					lbStatus.setText( "     Arquivo lido ..." );
					String codigo = ( "53" + header.getOcorrencias().trim() + "00" ).substring( 0, 4 );
					String mensagem = getMenssagemRet( txtCodBanco.getVlrString(), codigo, FPrefereFBB.TP_CNAB );
					Funcoes.mensagemInforma( this, mensagem != null ? mensagem : "Mensagem do arquivo não identificada." );
					return false;
				}
				else {
					lbStatus.setText( "" );
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
	
	private Receber findReceber( int idocrec, int iparc ) {
		
		Receber receber = null;
		
		if ( idocrec == 0 && iparc == 0 ) {
			return receber;
		}
				
		try {
			
			StringBuilder sql = new StringBuilder();				
			sql.append( "SELECT " );
			sql.append( "  IR.CODREC, IR.NPARCITREC, R.DOCREC, IR.VLRAPAGITREC, IR.DTITREC, IR.DTVENCITREC," );
			sql.append( "  IR.NUMCONTA, IR.CODPLAN, IR.CODCC, R.CODCLI, CL.RAZCLI " );
			sql.append( "FROM " );
			sql.append( "  FNITRECEBER IR, FNRECEBER R, VDCLIENTE CL " );
			sql.append( "WHERE " );
			sql.append( "  IR.CODEMP=? AND IR.CODFILIAL=? AND IR.NPARCITREC=? AND " );
			sql.append( "  IR.CODEMP=R.CODEMP AND IR.CODFILIAL=R.CODFILIAL AND IR.CODREC=R.CODREC AND R.DOCREC=? AND " );
			sql.append( "  R.CODEMPCL=CL.CODEMP AND R.CODFILIALCL=CL.CODFILIAL AND R.CODCLI=CL.CODCLI " );
			
			try {
				PreparedStatement ps = con.prepareStatement( sql.toString() );
				ps.setInt( 1, Aplicativo.iCodEmp );
				ps.setInt( 2, ListaCampos.getMasterFilial( "FNITRECEBER" ) );
				ps.setInt( 3, iparc );
				ps.setInt( 4, idocrec );
				
				ResultSet rs = ps.executeQuery();
				
				if ( rs.next() ) {
					
					receber = cnabutil.new Receber();
					
					receber.setCodrec( rs.getInt( "CODREC" ) );
					receber.setNrparcrec( rs.getInt( "NPARCITREC" ) );
					receber.setDocrec( rs.getString( "DOCREC" ) + "/" + rs.getInt( "NPARCITREC" ) );
					receber.setValorApagar( rs.getBigDecimal( "VLRAPAGITREC" ) );
					receber.setEmissao( Funcoes.sqlDateToDate( rs.getDate( "DTITREC" ) ) );
					receber.setVencimento( Funcoes.sqlDateToDate( rs.getDate( "DTVENCITREC" ) ) );
					receber.setConta( rs.getString( "NUMCONTA" ) );
					receber.setPlanejamento( rs.getString( "CODPLAN" ) );
					receber.setCentrocusto( rs.getString( "CODCC" ));
					receber.setCodcliente( rs.getInt( "CODCLI" ) );
					receber.setRazcliente( rs.getString( "RAZCLI" ) );
				}

				rs.close();
				ps.close();
				
				con.commit();
			} catch ( SQLException e ) {
				Funcoes.mensagemErro( this, "Erro ao buscar dados do recebimento!\n" + e.getMessage(), true, con, e );
				e.printStackTrace();
			}
		} catch ( NumberFormatException e ) {
			Funcoes.mensagemErro( this, "Erro ao buscar dados do recebimento!\nNúmero do documento inválido!\n" + e.getMessage(), true, con, e );
			e.printStackTrace();
		}
		
		return receber;
	}
	
	private int[] getChaveReceber( final Reg3T reg3T ) {		

		int[] chave = new int[2]; 
			
		if ( reg3T != null ) {		
			
			String docrec = reg3T.getIdentTitEmp().trim();
			String tmp = docrec.length() >= 15 ? docrec.substring( 1, 15 ) : docrec.trim();			
			chave[0] = Integer.parseInt( tmp.substring( 0, tmp.length()-2 ) );
			chave[1] = Integer.parseInt( tmp.substring( tmp.length()-2 ) );
		}
		
		return chave;
	}
}
