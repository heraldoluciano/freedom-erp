/**
 * @version 14/03/2007 <BR>
 * @author Setpoint Informática Ltda./Alex Rodrigues<BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.std <BR>
 * Classe:
 * @(#)FRemessa.java <BR>
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
 * Tela de leitura do arquivo de retorno.
 * 
 */

package org.freedom.modulos.fnc;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

import org.freedom.bmps.Icone;
import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JPanelPad;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.Tabela;
import org.freedom.funcoes.Funcoes;
import org.freedom.modulos.fnc.SiaccUtil.Reg;
import org.freedom.modulos.fnc.SiaccUtil.RegF;
import org.freedom.modulos.std.DLBaixaRec;
import org.freedom.modulos.std.DLBaixaRec.EColRetBaixa;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FFilho;

public class FRetSiacc extends FFilho implements ActionListener, MouseListener, KeyListener {

	private static final long serialVersionUID = 1L;

	private enum EColTab {
		SEL, RAZCLI, CODCLI, CODREC, DOCREC, NRPARC, VLRAPAG, DTREC, DTVENC, VLRPAG, DTPAG, NUMCONTA, CODPLAN, VLRDESC, VLRJUROS, CODCC, OBS
	};

	private enum EColInfoCli {
		CODREC, NPARCITREC, NUMCONTA, CODPLAN, RAZCLI, VLRAPAGITREC, DOCREC, DTVENCITREC, DTITREC;
	}

	private JPanelPad panelRodape = null;

	private final JPanelPad panelRemessa = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private final JPanelPad panelFiltros = new JPanelPad();

	private final JPanelPad panelTabela = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private final JPanelPad panelFuncoes = new JPanelPad();

	private final JPanelPad panelStatus = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private final Tabela tab = new Tabela();

	private final JTextFieldPad txtCodBanco = new JTextFieldPad( JTextFieldPad.TP_STRING, 3, 0 );

	private final JTextFieldFK txtNomeBanco = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private final JButton btImporta = new JButton( "Importar", Icone.novo( "btSalvar.gif" ) );

	private final JButton btSelTudo = new JButton( Icone.novo( "btTudo.gif" ) );

	private final JButton btSelNada = new JButton( Icone.novo( "btNada.gif" ) );

	private final JButton btEdita = new JButton( Icone.novo( "btEditar.gif" ) );

	private final JButton btGerar = new JButton( "Aplicar baixa", Icone.novo( "btSalvar.gif" ) );

	private final JLabel lbStatus = new JLabel();

	private final ListaCampos lcBanco = new ListaCampos( this );
	

	public FRetSiacc() {

		super( false );
		setTitulo( "Leitura do arquivo de retorno" );
		setAtribos( 10, 10, 780, 540 );

		lcBanco.add( new GuardaCampo( txtCodBanco, "CodBanco", "Cód.banco", ListaCampos.DB_PK, true ) );
		lcBanco.add( new GuardaCampo( txtNomeBanco, "NomeBanco", "Nome do Banco", ListaCampos.DB_SI, false ) );
		lcBanco.montaSql( false, "BANCO", "FN" );
		lcBanco.setQueryCommit( false );
		lcBanco.setReadOnly( true );
		txtCodBanco.setNomeCampo( "CodBanco" );
		txtCodBanco.setTabelaExterna( lcBanco );
		txtCodBanco.setListaCampos( lcBanco );
		txtCodBanco.setFK( true );
		txtCodBanco.setRequerido( true );
		txtNomeBanco.setListaCampos( lcBanco );

		montaTela();

		tab.adicColuna( "" );
		tab.adicColuna( "Razão social do cliente" );
		tab.adicColuna( "Cód.cli." );
		tab.adicColuna( "Cód.rec." );
		tab.adicColuna( "Doc" );
		tab.adicColuna( "Nro.Parc." );
		tab.adicColuna( "Valor" );
		tab.adicColuna( "Emissão" );
		tab.adicColuna( "Vencimento" );
		tab.adicColuna( "Valor pago" );
		tab.adicColuna( "Data pgto." );
		tab.adicColuna( "Nro.conta" );
		tab.adicColuna( "Cód.planej." );
		tab.adicColuna( "Valor desc." );
		tab.adicColuna( "Valor juros" );
		tab.adicColuna( "Cód.c.c." );
		tab.adicColuna( "Histórico" );

		tab.setTamColuna( 20, EColTab.SEL.ordinal() );
		tab.setTamColuna( 150, EColTab.RAZCLI.ordinal() );
		tab.setTamColuna( 70, EColTab.CODCLI.ordinal() );
		tab.setTamColuna( 70, EColTab.CODREC.ordinal() );
		tab.setTamColuna( 80, EColTab.DOCREC.ordinal() );
		tab.setTamColuna( 70, EColTab.NRPARC.ordinal() );
		tab.setTamColuna( 70, EColTab.VLRAPAG.ordinal() );
		tab.setTamColuna( 70, EColTab.DTREC.ordinal() );
		tab.setTamColuna( 70, EColTab.DTVENC.ordinal() );
		tab.setTamColuna( 70, EColTab.VLRPAG.ordinal() );
		tab.setTamColuna( 70, EColTab.DTPAG.ordinal() );
		tab.setTamColuna( 70, EColTab.NUMCONTA.ordinal() );
		tab.setTamColuna( 70, EColTab.CODPLAN.ordinal() );
		tab.setTamColuna( 70, EColTab.VLRDESC.ordinal() );
		tab.setTamColuna( 70, EColTab.VLRJUROS.ordinal() );
		tab.setTamColuna( 70, EColTab.CODCC.ordinal() );
		tab.setTamColuna( 200, EColTab.OBS.ordinal() );

		tab.setColunaEditavel( EColTab.SEL.ordinal(), true );

		btSelTudo.addActionListener( this );
		btSelNada.addActionListener( this );
		btImporta.addActionListener( this );
		btEdita.addActionListener( this );
		btGerar.addActionListener( this );
		btImporta.addKeyListener( this );
		tab.addMouseListener( this );

		btSelTudo.setToolTipText( "Selecionar tudo" );
		btSelNada.setToolTipText( "Limpar seleção" );
		btEdita.setToolTipText( "Editar" );
		btGerar.setToolTipText( "Aplicar baixa" );
	}

	private void montaTela() {

		pnCliente.add( panelRemessa, BorderLayout.CENTER );

		panelRemessa.add( panelFiltros, BorderLayout.NORTH );
		panelRemessa.add( panelTabela, BorderLayout.CENTER );
		panelRemessa.add( panelStatus, BorderLayout.SOUTH );

		panelFiltros.setPreferredSize( new Dimension( 300, 70 ) );
		panelFiltros.adic( new JLabel( "Cód.banco" ), 7, 10, 90, 20 );
		panelFiltros.adic( txtCodBanco, 7, 30, 90, 20 );
		panelFiltros.adic( new JLabel( "Nome do banco" ), 100, 10, 300, 20 );
		panelFiltros.adic( txtNomeBanco, 100, 30, 300, 20 );
		panelFiltros.adic( btImporta, 500, 25, 150, 30 );

		panelTabela.add( new JScrollPane( tab ), BorderLayout.CENTER );
		panelTabela.add( panelFuncoes, BorderLayout.EAST );

		panelFuncoes.setPreferredSize( new Dimension( 45, 50 ) );
		panelFuncoes.adic( btSelTudo, 5, 5, 30, 30 );
		panelFuncoes.adic( btSelNada, 5, 40, 30, 30 );
		panelFuncoes.adic( btEdita, 5, 75, 30, 30 );

		lbStatus.setForeground( Color.BLUE );

		panelStatus.setPreferredSize( new Dimension( 600, 30 ) );
		panelStatus.add( lbStatus, BorderLayout.WEST );

		panelRodape = adicBotaoSair();
		panelRodape.setBorder( BorderFactory.createEtchedBorder() );
		panelRodape.setPreferredSize( new Dimension( 600, 32 ) );
		
		btGerar.setPreferredSize( new Dimension( 150, 32 ) );
		panelRodape.add( btGerar, BorderLayout.WEST );

	}

	private void selecionaTudo() {

		for ( int i = 0; i < tab.getNumLinhas(); i++ ) {
			tab.setValor( new Boolean( true ), i, 0 );
		}
	}

	private void selecionaNada() {

		for ( int i = 0; i < tab.getNumLinhas(); i++ ) {
			tab.setValor( new Boolean( false ), i, 0 );
		}
	}

	private boolean execImportar() {

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
			fileDialogSiacc.setFile( "*.txt" );
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
			learquivo : 
			while ( ( line = in.readLine() ) != null ) {

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
		int count = 0;

		if ( list != null ) {

			lbStatus.setText( "     Carregando tabela ..." );

			Object[] row = null;
			List<Object> infocli = new ArrayList<Object>();
			
			try {
				for ( Reg reg : list ) {

					if ( reg.getTiporeg() == 'F' ) {

						infocli.clear();
						
						if ( ! setInfoCli( ( (RegF) reg ).getCodRec(), ( (RegF) reg ).getNparcItRec(), infocli ) ) {
							retorno = false;
							break;
						}
						if ( infocli.size() == EColInfoCli.values().length ) {
							
							tab.adicLinha();
							tab.setValor( new Boolean( Boolean.FALSE ), count, 0 );
							tab.setValor( (String) infocli.get( EColInfoCli.RAZCLI.ordinal() ), count, EColTab.RAZCLI.ordinal() ); // Razão social do cliente
							tab.setValor( new Integer( ( (RegF) reg ).getIdentCliEmp().trim() ), count, EColTab.CODCLI.ordinal() ); // Cód.cli.
							tab.setValor( (Integer) infocli.get( EColInfoCli.CODREC.ordinal() ), count, EColTab.CODREC.ordinal() ); // Cód.rec.
							tab.setValor( (String) infocli.get( EColInfoCli.DOCREC.ordinal() ), count, EColTab.DOCREC.ordinal() ); // Doc
							tab.setValor( (Integer) infocli.get( EColInfoCli.NPARCITREC.ordinal() ), count, EColTab.NRPARC.ordinal() ); // Nro.Parc.
							tab.setValor( (BigDecimal) infocli.get( EColInfoCli.VLRAPAGITREC.ordinal() ), count, EColTab.VLRAPAG.ordinal() ); // Valor
							tab.setValor( (Date) infocli.get( EColInfoCli.DTITREC.ordinal() ), count, EColTab.DTREC.ordinal() ); // Emissão
							tab.setValor( (Date) infocli.get( EColInfoCli.DTVENCITREC.ordinal() ), count, EColTab.DTVENC.ordinal() ); // Vencimento
							tab.setValor( (BigDecimal) ( (RegF) reg ).getValorDebCred(), count, EColTab.VLRPAG.ordinal() ); // Valor pago
							tab.setValor( (Date) ( (RegF) reg ).getDataVenc(), count, EColTab.DTPAG.ordinal() ); // Data pgto.
							tab.setValor( (String) infocli.get( EColInfoCli.NUMCONTA.ordinal() ), count, EColTab.NUMCONTA.ordinal() ); // Conta
							tab.setValor( (String) infocli.get( EColInfoCli.CODPLAN.ordinal() ), count, EColTab.CODPLAN.ordinal() ); // Planejamento
							tab.setValor( new BigDecimal( 0 ), count, EColTab.VLRDESC.ordinal() ); // VLRDESC
							tab.setValor( new BigDecimal( 0 ), count, EColTab.VLRJUROS.ordinal() ); // VLRJUROS
							tab.setValor( "BAIXA AUTOMÁTICA SIACC", count, EColTab.OBS.ordinal() ); // HISTÓRICO

							count++;
						}
					}
				}
				
				if ( count > 0 ) {
					lbStatus.setText( "     Tabela carregada ..." );
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

	private Boolean setInfoCli( final Integer codrec, final Integer numparcrec, final List<Object> info ) {

		boolean retorno = true;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuilder sSQL = new StringBuilder();

		if ( codrec != null && numparcrec != null && info != null ) {

			try {

				sSQL.append( "SELECT R.CODREC, IR.NPARCITREC," );
				sSQL.append( "COALESCE(IR.NUMCONTA, FC.NUMCONTA) NUMCONTA," );
				sSQL.append( "COALESCE(IR.CODPLAN, FC.CODPLAN) CODPLAN, " );
				sSQL.append( "C.RAZCLI, IR.VLRAPAGITREC, R.DOCREC, IR.DTVENCITREC, IR.DTITREC " );
				sSQL.append( "FROM FNITRECEBER IR, VDCLIENTE C, FNRECEBER R " );
				sSQL.append( "LEFT OUTER JOIN FNFBNCLI FC ON " );
				sSQL.append( "FC.CODEMP=R.CODEMPCL AND FC.CODFILIAL=R.CODFILIALCL AND FC.CODCLI=R.CODCLI AND " );
				sSQL.append( "FC.CODEMPBO=? AND FC.CODFILIALBO=? AND FC.CODBANCO=? AND " );
				sSQL.append( "FC.TIPOFEBRABAN='1' " );
				sSQL.append( "WHERE IR.CODEMP=? AND IR.CODFILIAL=? AND IR.CODREC=? AND IR.NPARCITREC=? AND " );
				sSQL.append( "R.CODEMP=IR.CODEMP AND R.CODFILIAL=IR.CODFILIAL AND R.CODREC=IR.CODREC AND " );
				sSQL.append( "C.CODEMP=R.CODEMPCL AND C.CODFILIAL=R.CODFILIALCL AND C.CODCLI=R.CODCLI" );

				ps = con.prepareStatement( sSQL.toString() );
				ps.setInt( 1, Aplicativo.iCodEmp );
				ps.setInt( 2, ListaCampos.getMasterFilial( "FNFBNCLI" ) );
				ps.setInt( 3, txtCodBanco.getVlrInteger() );
				ps.setInt( 4, Aplicativo.iCodEmp );
				ps.setInt( 5, ListaCampos.getMasterFilial( "FNITRECEBER" ) );
				ps.setInt( 6, codrec );
				ps.setInt( 7, numparcrec );
				rs = ps.executeQuery();

				if ( rs.next() ) {
					
					info.add( EColInfoCli.CODREC.ordinal(), rs.getInt( EColInfoCli.CODREC.toString() ) );
					info.add( EColInfoCli.NPARCITREC.ordinal(), rs.getInt( EColInfoCli.NPARCITREC.toString() ) );
					info.add( EColInfoCli.NUMCONTA.ordinal(), rs.getString( EColInfoCli.NUMCONTA.toString() ) );
					info.add( EColInfoCli.CODPLAN.ordinal(), rs.getString( EColInfoCli.CODPLAN.toString() ) );
					info.add( EColInfoCli.RAZCLI.ordinal(), rs.getString( EColInfoCli.RAZCLI.toString() ) );
					info.add( EColInfoCli.VLRAPAGITREC.ordinal(), rs.getBigDecimal( EColInfoCli.VLRAPAGITREC.toString() ) );
					info.add( EColInfoCli.DOCREC.ordinal(), rs.getString( EColInfoCli.DOCREC.toString() ) );
					info.add( EColInfoCli.DTVENCITREC.ordinal(), Funcoes.sqlDateToDate( rs.getDate( EColInfoCli.DTVENCITREC.toString() ) ) );
					info.add( EColInfoCli.DTITREC.ordinal(), Funcoes.sqlDateToDate( rs.getDate( EColInfoCli.DTITREC.toString() ) ) );
				}
			} catch ( Exception e ) {
				Funcoes.mensagemErro( this, "Erro ao buscar informações do cliente!\n" + e.getMessage(), true, con, e );
				e.printStackTrace();
				retorno = false;
			}
		}
		
		return retorno;
	}

	private void edit() {

		DLBaixaRec dl = null;
		Object[] sVals = new Object[ 15 ];
		Object[] sRets = null;
		float vlrdesc = 0;
		float vlrjuros = 0;
		int iLin = tab.getLinhaSel();

		if ( iLin > -1 ) {
			
			iLin = tab.getLinhaSel();
			dl = new DLBaixaRec( this );
			dl.setConexao( con );

			sVals[ DLBaixaRec.EColBaixa.CODCLI.ordinal() ] = tab.getValor( iLin, EColTab.CODCLI.ordinal() );
			sVals[ DLBaixaRec.EColBaixa.RAZCLI.ordinal() ] = tab.getValor( iLin, EColTab.RAZCLI.ordinal() );
			sVals[ DLBaixaRec.EColBaixa.NUMCONTA.ordinal() ] = tab.getValor( iLin, EColTab.NUMCONTA.ordinal() ); 
			sVals[ DLBaixaRec.EColBaixa.CODPLAN.ordinal() ] = tab.getValor( iLin, EColTab.CODPLAN.ordinal() ); 
			sVals[ DLBaixaRec.EColBaixa.DOC.ordinal() ] = tab.getValor( iLin, EColTab.DOCREC.ordinal() ); 
			sVals[ DLBaixaRec.EColBaixa.DTEMIT.ordinal() ] = tab.getValor( iLin, EColTab.DTREC.ordinal() );
			sVals[ DLBaixaRec.EColBaixa.DTVENC.ordinal() ] = tab.getValor( iLin, EColTab.DTVENC.ordinal() );
			sVals[ DLBaixaRec.EColBaixa.VLRPARC.ordinal() ] = tab.getValor( iLin, EColTab.VLRPAG.ordinal() );
			sVals[ DLBaixaRec.EColBaixa.VLRAPAG.ordinal() ] = tab.getValor( iLin, EColTab.VLRAPAG.ordinal() ); 
			sVals[ DLBaixaRec.EColBaixa.VLRDESC.ordinal() ] = tab.getValor( iLin, EColTab.VLRDESC.ordinal() ); 
			sVals[ DLBaixaRec.EColBaixa.VLRJUROS.ordinal() ] = tab.getValor( iLin, EColTab.VLRJUROS.ordinal() ); 
			sVals[ DLBaixaRec.EColBaixa.VLRAPAG.ordinal() ] = tab.getValor( iLin, EColTab.VLRAPAG.ordinal() );
			sVals[ DLBaixaRec.EColBaixa.DTPGTO.ordinal() ] = tab.getValor( iLin, EColTab.DTPAG.ordinal() ); 
			sVals[ DLBaixaRec.EColBaixa.VLRPAGO.ordinal() ] = tab.getValor( iLin, EColTab.VLRPAG.ordinal() ); 
			sVals[ DLBaixaRec.EColBaixa.CODCC.ordinal() ] = tab.getValor( iLin, EColTab.CODCC.ordinal() ); 
			sVals[ DLBaixaRec.EColBaixa.OBS.ordinal() ] = String.valueOf( tab.getValor( iLin, EColTab.OBS.ordinal() ) ); 

			dl.setValores( sVals );
			dl.setVisible( true );
			
			if ( dl.OK ) {

				sRets = dl.getValores();
				
				loadAllRowsOfCli( (Integer) sVals[ DLBaixaRec.EColBaixa.CODCLI.ordinal() ], sRets );
				
				tab.setValor( new Boolean( Boolean.TRUE ), iLin, EColTab.SEL.ordinal() );
				tab.setValor( sRets[ EColRetBaixa.NUMCONTA.ordinal() ], iLin, EColTab.NUMCONTA.ordinal() );
				tab.setValor( sRets[ EColRetBaixa.CODPLAN.ordinal() ], iLin, EColTab.CODPLAN.ordinal() );
				tab.setValor( sRets[ EColRetBaixa.DOC.ordinal() ], iLin, EColTab.DOCREC.ordinal() );
				tab.setValor( sRets[ EColRetBaixa.DTPAGTO.ordinal() ], iLin, EColTab.DTPAG.ordinal() );
				tab.setValor( sRets[ EColRetBaixa.VLRPAGO.ordinal() ], iLin, EColTab.VLRPAG.ordinal() );
				tab.setValor( sRets[ EColRetBaixa.VLRDESC.ordinal() ], iLin, EColTab.VLRDESC.ordinal() );
				tab.setValor( sRets[ EColRetBaixa.VLRJUROS.ordinal() ], iLin, EColTab.VLRJUROS.ordinal() );
				tab.setValor( sRets[ EColRetBaixa.CODCC.ordinal() ], iLin, EColTab.CODCC.ordinal() );
				tab.setValor( sRets[ EColRetBaixa.OBS.ordinal() ], iLin, EColTab.OBS.ordinal() );

				/*
				 * sSQL.append( "UPDATE FNITRECEBER SET NUMCONTA=?,CODEMPCA=?,CODFILIALCA=?,CODPLAN=?,CODEMPPN=?,CODFILIALPN=?," ); 
				 * sSQL.append( "ANOCC=?,CODCC=?,CODEMPCC=?,CODFILIALCC=?,DOCLANCAITREC =?,VLRJUROSITREC=?," ); 
				 * sSQL.append( "VLRDESCITREC=?,DTVENCITREC=?,OBSITREC=?,CODEMPBO=?,CODFILIALBO=?,CODBANCO=?" ); 
				 * sSQL.append( "WHERE CODREC=? AND NPARCITREC=? AND CODEMP=? AND CODFILIAL=?" );
				 */

			}
		}
		else {
			Funcoes.mensagemInforma( this, "Selecione uma linha na lista!" );
		}
	}
	
	private void loadAllRowsOfCli( final int codcli, final Object[] vals ) {
		
		BigDecimal vlrpago = new BigDecimal(0);
		BigDecimal vlrdescjuros = new BigDecimal(0);
	
		if ( vals != null ) {
			
			for ( int row=0; row < tab.getRowCount(); row++ ) {
								
				if ( codcli == ( (Integer) tab.getValor( row, EColTab.CODCLI.ordinal() ) ) ) {

					vlrpago = (BigDecimal) tab.getValor( row, EColTab.VLRPAG.ordinal() );
					vlrdescjuros = vlrpago.subtract( 
							(BigDecimal) tab.getValor( row, EColTab.VLRAPAG.ordinal() ) ).setScale( Aplicativo.casasDecFin, BigDecimal.ROUND_HALF_UP );
					
					tab.setValor( vals[ EColRetBaixa.NUMCONTA.ordinal() ], row, EColTab.NUMCONTA.ordinal() );
					tab.setValor( vals[ EColRetBaixa.CODPLAN.ordinal() ], row, EColTab.CODPLAN.ordinal() );
					tab.setValor( vals[ EColRetBaixa.DOC.ordinal() ], row, EColTab.DOCREC.ordinal() );
					tab.setValor( vals[ EColRetBaixa.CODCC.ordinal() ], row, EColTab.CODCC.ordinal() );
					
					if ( vlrdescjuros.floatValue() > 0 ) {
						tab.setValor( vlrdescjuros, row, EColTab.VLRJUROS.ordinal() );
					}
					else {
						tab.setValor( vlrdescjuros, row, EColTab.VLRDESC.ordinal() );
					}
				}
			}
		}
	}

	public void actionPerformed( ActionEvent e ) {

		if ( e.getSource() == btSelTudo ) {
			selecionaTudo();
		}
		else if ( e.getSource() == btSelNada ) {
			selecionaNada();
		}
		else if ( e.getSource() == btImporta ) {
			execImportar();
		}
		else if ( e.getSource() == btEdita ) {
			edit();
		}
	}

	public void keyPressed( KeyEvent e ) {

		if ( e.getKeyCode() == KeyEvent.VK_ENTER && e.getSource() == btImporta ) {
			execImportar();
		}
	}

	public void keyReleased( KeyEvent e ) { }

	public void keyTyped( KeyEvent e ) { }

	public void mouseClicked( MouseEvent e ) {

		if ( e.getComponent() == tab ) {
			if ( e.getClickCount() == 2 ) {
				edit();
			}
		}
	}

	public void mouseEntered( MouseEvent e ) { }

	public void mouseExited( MouseEvent e ) { }

	public void mousePressed( MouseEvent e ) { }

	public void mouseReleased( MouseEvent e ) { }

	public void setConexao( Connection cn ) {

		super.setConexao( cn );
		lcBanco.setConexao( cn );
	}

}
