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
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import java.util.Vector;

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
import org.freedom.modulos.std.DLEditaRec;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FFilho;

public class FRetSiacc extends FFilho implements ActionListener, MouseListener {

	private static final long serialVersionUID = 1L;

	private enum EColTab {
		SEL, RAZCLI, CODCLI, CODREC, DOCREC, NRPARC, VLRAPAG, 
		DTREC, DTVENC, VLRPAG, DTPAG, CODCON, CODPLAN  
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

	private final JButton btCarrega = new JButton( "Buscar", Icone.novo( "btExecuta.gif" ) );

	private final JButton btImporta = new JButton( "Importar", Icone.novo( "btSalvar.gif" ) );

	private final JButton btSelTudo = new JButton( Icone.novo( "btTudo.gif" ) );

	private final JButton btSelNada = new JButton( Icone.novo( "btNada.gif" ) );
	
	private final JButton btEdita = new JButton( Icone.novo( "btEditar.gif" ) );
	
	private final JButton btGerar = new JButton( Icone.novo( "btGerar.gif" ) );

	private final JLabel lbStatus = new JLabel();
	
	
/*	private Vector vNumContas = new Vector();
	
	private Vector vCodPlans = new Vector();
	
	private Vector vDtEmiss = new Vector();
	
	private Vector vCodBOs = new Vector();
	
	private Vector vCodCCs = new Vector(); */
	
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
		tab.adicColuna( "Conta" );
		tab.adicColuna( "Planejamento" );

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
		tab.setTamColuna( 70, EColTab.CODCON.ordinal() );
		tab.setTamColuna( 70, EColTab.CODPLAN.ordinal() );

		tab.setColunaEditavel( EColTab.SEL.ordinal() , true );

		btCarrega.addActionListener( this );
		btSelTudo.addActionListener( this );
		btSelNada.addActionListener( this );
		btImporta.addActionListener( this );
		btEdita.addActionListener( this );
		btGerar.addActionListener( this );
		tab.addMouseListener( this ); 

		btSelTudo.setToolTipText( "Selecionar tudo" );
		btSelNada.setToolTipText( "Limpar seleção" );
		btEdita.setToolTipText( "Editar" );
		btGerar.setToolTipText( "Gerar" );

	}

	private void montaTela() {

		pnCliente.add( panelRemessa, BorderLayout.CENTER );

		panelRemessa.add( panelFiltros, BorderLayout.NORTH );
		panelRemessa.add( panelTabela, BorderLayout.CENTER );
		panelRemessa.add( panelStatus, BorderLayout.SOUTH );

		panelFiltros.setPreferredSize( new Dimension( 300, 110 ) );
		panelFiltros.adic( new JLabel( "Cód.banco" ), 7, 0, 90, 20 );
		panelFiltros.adic( txtCodBanco, 7, 20, 90, 20 );
		panelFiltros.adic( new JLabel( "Nome do banco" ), 100, 0, 300, 20 );
		panelFiltros.adic( txtNomeBanco, 100, 20, 300, 20 );
		panelFiltros.adic( btImporta, 500, 20, 150, 30 );
		panelFiltros.adic( btCarrega, 500, 60, 150, 30 );
		

		panelTabela.add( new JScrollPane( tab ), BorderLayout.CENTER );
		panelTabela.add( panelFuncoes, BorderLayout.EAST );

		panelFuncoes.setPreferredSize( new Dimension( 45, 100 ) );
		panelFuncoes.adic( btSelTudo, 5, 5, 30, 30 );
		panelFuncoes.adic( btSelNada, 5, 40, 30, 30 );
		panelFuncoes.adic( btEdita, 5, 75, 30, 30 );

		lbStatus.setForeground( Color.BLUE );

		panelStatus.setPreferredSize( new Dimension( 600, 30 ) );
		panelStatus.add( lbStatus, BorderLayout.WEST );

		panelRodape = adicBotaoSair();
		panelRodape.setBorder( BorderFactory.createEtchedBorder() );
		panelRodape.setPreferredSize( new Dimension( 600, 32 ) );
		panelFuncoes.adic( btGerar, 5, 110, 30, 30 );
		
	}

	private void carregaTab() {

		// ler dados do arquivo.

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
		
		if ("".equals( txtCodBanco.getVlrString())){
			Funcoes.mensagemInforma( this, "Selecione o Banco!!" );
			txtCodBanco.requestFocus();			
		}
		else{
			
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
								
								lbStatus.setText( "     Arquivo lido ..." );
								
								if ( montaGrid( list ) ) {

									lbStatus.setText( "     Tabela carregada ..." );
								}
								else {
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
		return retorno;
	}
		
	private boolean leArquivo( final FileReader fileReaderSiacc, final ArrayList<SiaccUtil.Reg> list ) throws IOException {

		boolean retorno = true;
		String line = null;
		char tipo;
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
		} catch ( ExceptionSiacc e ) {
			Funcoes.mensagemErro( this, "Erro lendo o arquivo!\n" + e.getMessage() );
			e.printStackTrace();
			retorno = false;
		}
		
		in.close();
		
		return retorno;
	}

	private boolean montaGrid( ArrayList<SiaccUtil.Reg> list ) {
		
		boolean retorno = true;
		Integer count = new Integer( 0 );
		
		if ( list != null ) {			

			lbStatus.setText( "     Carregando tabela ..." );
			
			Object[] row = null;
			List<Object> infocli = new ArrayList<Object>();

			//lbStatus.setText( "     Arquivo lido ..." );		
			try {
				for( Reg reg : list ) {
					
					if ( reg.getTiporeg() == 'F' ) {
						
						if ( ! setInfoCli( ((RegF) reg).getCodRec(), ((RegF) reg).getNparcItRec(), infocli ) ) {
							retorno = false;
							break;
						}
						if ( infocli.size() == EColInfoCli.values().length ) {
							tab.adicLinha();
							tab.setValor( new Boolean( Boolean.FALSE ), count, 0 );
							tab.setValor( (String) infocli.get( EColInfoCli.RAZCLI.ordinal() ), count, EColTab.RAZCLI.ordinal() ); // Razão social do cliente
							tab.setValor( new Integer( ((RegF) reg).getIdentCliEmp().trim() ), count, EColTab.CODCLI.ordinal() ); // Cód.cli.
							tab.setValor( (Integer) infocli.get( EColInfoCli.CODREC.ordinal() ), count, EColTab.CODREC.ordinal() ); // Cód.rec.
							tab.setValor( (String) infocli.get( EColInfoCli.DOCREC.ordinal() ), count, EColTab.DOCREC.ordinal() ); // Doc
							tab.setValor( (Integer) infocli.get( EColInfoCli.NPARCITREC.ordinal() ), count, EColTab.NRPARC.ordinal() ); // Nro.Parc.
							tab.setValor( (BigDecimal) infocli.get( EColInfoCli.VLRAPAGITREC.ordinal() ), count, EColTab.VLRPAG.ordinal() ); // Valor
							tab.setValor( (Date) infocli.get( EColInfoCli.DTITREC.ordinal() ), count, EColTab.DTREC.ordinal() ); // Emissão
							tab.setValor( (Date) infocli.get( EColInfoCli.DTVENCITREC.ordinal() ), count, EColTab.DTVENC.ordinal() ); // Vencimento
							tab.setValor( (BigDecimal) ((RegF) reg).getValorDebCred(), count, EColTab.VLRPAG.ordinal() ); // Valor pago
							tab.setValor( (Date) ((RegF) reg).getDataVenc(), count, EColTab.DTVENC.ordinal() ); // Data pgto.
							tab.setValor( (String) infocli.get( EColInfoCli.NUMCONTA.ordinal() ), count, EColTab.CODCON.ordinal() ); // Conta
							tab.setValor( (String) infocli.get( EColInfoCli.CODPLAN.ordinal() ), count, EColTab.CODPLAN.ordinal() ); // Planejamento
							count++;
						}
					}
				}
			} catch ( Exception e ) {
				retorno = false;
				Funcoes.mensagemErro(this, "Erro no carregamento da tabela!\n" + e.getMessage());
				e.printStackTrace();
			}
		}
		else {
			retorno = false;
		}
		
		return retorno;
	}
	
	private Boolean setInfoCli( final Integer codrec, final Integer numparcrec, final List<Object> info ) {
		
		Boolean retorno = Boolean.TRUE;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuilder sSQL = new StringBuilder();
		
		if ( codrec != null && numparcrec != null && info != null ) {
			
			try {
				
				sSQL.append( "SELECT R.CODREC, IR.NPARCITREC," ); 
				sSQL.append( "COALESCE(IR.NUMCONTA, FC.NUMCONTA) NUMCONTA," );
				sSQL.append( "COALESCE(IR.CODPLAN, FC.CODPLAN) CODPLAN, " );
				sSQL.append( "C.RAZCLI, IR.VLRAPAGITREC, R.DOCREC, IR.DTVENCITREC, IR.DTITREC ");
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
				ps.setInt( 6, codrec);
				ps.setInt( 7, numparcrec);
				rs = ps.executeQuery();
				
				if( rs.next() ) {
					info.add( EColInfoCli.CODREC.ordinal(), rs.getInt( EColInfoCli.CODREC.toString() ) );
					info.add( EColInfoCli.NPARCITREC.ordinal(), rs.getInt( EColInfoCli.NPARCITREC.toString() ) );
					info.add( EColInfoCli.NUMCONTA.ordinal(), rs.getString( EColInfoCli.NUMCONTA .toString() ) );
					info.add( EColInfoCli.CODPLAN.ordinal(), rs.getString( EColInfoCli.CODPLAN.toString() ) );
					info.add( EColInfoCli.RAZCLI.ordinal(), rs.getString( EColInfoCli.RAZCLI.toString() ) );
					info.add( EColInfoCli.VLRAPAGITREC.ordinal(), rs.getBigDecimal( EColInfoCli.VLRAPAGITREC.toString() ) );
					info.add( EColInfoCli.DOCREC.ordinal(), rs.getString( EColInfoCli.DOCREC.toString() ) );
					info.add( EColInfoCli.DTVENCITREC.ordinal(), Funcoes.sqlDateToDate( rs.getDate( EColInfoCli.DTVENCITREC.toString() )) );
					info.add( EColInfoCli.DTITREC.ordinal(), Funcoes.sqlDateToDate( rs.getDate( EColInfoCli.DTITREC.toString() ) ));
				}
				
			} catch ( Exception e ) {
				Funcoes.mensagemErro( this, "Erro ao buscar informações do cliente!\n" + e.getMessage(), true, con, e );
				e.printStackTrace();
				retorno = Boolean.TRUE;
			}
		}
		
		return retorno;
	}
	
	private void edit(){
		
		DLBaixaRec dl = null; 
		Object[] sVals = new Object[ 13 ];
		Object[] sRets = null;
		StringBuffer sSQL = new StringBuffer();
		PreparedStatement ps = null;
		float vlrdesc = 0;
		float vlrjuros = 0;
		
		

		if( tab.getLinhaSel() > -1 ){
			
			dl = new DLBaixaRec(this);
			dl.setConexao( con );
			int iLin = tab.getLinhaSel();
			
			
			sVals [ DLEditaRec.EColEdit.CODCLI.ordinal() ] = tab.getValor( iLin, EColTab.CODCLI.ordinal()  );
			sVals [ DLEditaRec.EColEdit.RAZCLI.ordinal() ] = tab.getValor( iLin, EColTab.RAZCLI.ordinal() );
			sVals [ DLEditaRec.EColEdit.NUMCONTA.ordinal() ] = tab.getValor( iLin, EColTab.CODCON.ordinal() );
			sVals [ DLEditaRec.EColEdit.CODPLAN.ordinal() ] =  tab.getValor( iLin, EColTab.CODPLAN.ordinal());
			sVals [ DLEditaRec.EColEdit.CODCC.ordinal() ] = "";
			sVals [ DLEditaRec.EColEdit.DOC.ordinal() ] = tab.getValor( iLin, EColTab.DOCREC.ordinal() );
			sVals [ DLEditaRec.EColEdit.DTEMIS.ordinal() ] = tab.getValor( iLin, EColTab.DTREC.ordinal() );
			sVals [ DLEditaRec.EColEdit.DTVENC.ordinal() ] = tab.getValor( iLin, EColTab.DTVENC.ordinal()  );
			sVals [ DLEditaRec.EColEdit.VLRJUROS.ordinal() ] = new BigDecimal(0);
			sVals [ DLEditaRec.EColEdit.VLRDESC.ordinal() ] = new BigDecimal(0);
			sVals [ DLEditaRec.EColEdit.VLRPARC.ordinal() ] = tab.getValor( iLin, EColTab.VLRPAG.ordinal() );
			sVals [ DLEditaRec.EColEdit.OBS.ordinal() ] = String.valueOf( tab.getValor( iLin, EColTab.RAZCLI.ordinal() ) );
			sVals [ DLEditaRec.EColEdit.CODBANCO.ordinal() ] = txtCodBanco.getVlrString();
			
			dl.setValores( sVals );
			dl.setVisible( true );	
		}
		
		else{
			Funcoes.mensagemInforma( this, "Selecione uma linha na lista!" );
		}
		
		if(dl.OK){
			
			sRets = dl.getValores();
			
			sSQL.append( "UPDATE FNITRECEBER SET NUMCONTA=?,CODEMPCA=?,CODFILIALCA=?,CODPLAN=?,CODEMPPN=?,CODFILIALPN=?," );
			sSQL.append( "ANOCC=?,CODCC=?,CODEMPCC=?,CODFILIALCC=?,DOCLANCAITREC =?,VLRJUROSITREC=?," );
			sSQL.append( "VLRDESCITREC=?,DTVENCITREC=?,OBSITREC=?,CODEMPBO=?,CODFILIALBO=?,CODBANCO=?" );
			sSQL.append( "WHERE CODREC=? AND NPARCITREC=? AND CODEMP=? AND CODFILIAL=?" );
			
		}
	}
	
	public void actionPerformed( ActionEvent evt ) {

		if ( evt.getSource() == btCarrega ) {
			lbStatus.setText( "      carregando tabela ..." );
			carregaTab();
		}
		else if ( evt.getSource() == btSelTudo ) {
			selecionaTudo();
		}
		else if ( evt.getSource() == btSelNada ) {
			selecionaNada();
		}
		else if ( evt.getSource() == btImporta ) {
			execImportar();
		}
		else if ( evt.getSource() == btEdita ){
			edit();
		}
	}

	public void setConexao( Connection cn ) {

		super.setConexao( cn );
		lcBanco.setConexao( cn );
		
	}

	public void mouseClicked( MouseEvent e ) {
		
		if(e.getComponent() == tab ){
			
			if( e.getClickCount() == 2 ){
			
				edit();
				
			}
		}
	}

	public void mouseEntered( MouseEvent e ) {}

	public void mouseExited( MouseEvent e ) {}

	public void mousePressed( MouseEvent e ) {}

	public void mouseReleased( MouseEvent e ) {}

}
