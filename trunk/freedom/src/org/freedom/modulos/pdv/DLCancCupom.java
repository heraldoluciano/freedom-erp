/**
 * @version 16/07/2003 <BR>
 * @author Setpoint Informática Ltda./Anderson Sanchez <BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.pdv <BR>
 * Classe:
 * @(#)DLCancCupom.java <BR>
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
 * Comentários sobre a classe...
 * 
 */

package org.freedom.modulos.pdv;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Properties;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import org.freedom.bmps.Icone;
import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JCheckBoxPad;
import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JPanelPad;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.Tabela;
import org.freedom.comutacao.Tef;
import org.freedom.drivers.ECFDriver;
//import org.freedom.drivers.JBemaFI32;
import org.freedom.funcoes.Funcoes;
import org.freedom.funcoes.Logger;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.AplicativoPDV;
import org.freedom.telas.FDialogo;

public class DLCancCupom extends FDialogo implements ActionListener, MouseListener, KeyListener {

	private static final long serialVersionUID = 1L;

	private JPanelPad pinCab = new JPanelPad( 400, 90 );

	private JPanelPad pnCli = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private JPanelPad pnLegenda = new JPanelPad( JPanelPad.TP_JPANEL, new GridLayout( 0, 2 ) );

	private JTextFieldPad txtVenda = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtNota = new JTextFieldFK( JTextFieldPad.TP_STRING, 10, 0 );

	private JTextFieldFK txtSerie = new JTextFieldFK( JTextFieldPad.TP_STRING, 10, 0 );

	private JTextFieldFK txtData = new JTextFieldFK( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldFK txtValor = new JTextFieldFK( JTextFieldPad.TP_DOUBLE, 10, 2 );

	private Tabela tab = new Tabela();

	private JScrollPane spnTab = new JScrollPane( tab );

	private JButton btCanc = new JButton( "Cancelar", Icone.novo( "btExcluir.gif" ) );

	private JButton btExec = new JButton( Icone.novo( "btExecuta.gif" ) );

	private JCheckBoxPad cbInteira = new JCheckBoxPad( "Cancelar venda inteira", "S", "N" );

	private ListaCampos lcVenda = new ListaCampos( this, "VD" );

	private ECFDriver ecf = new ECFDriver( ! AplicativoPDV.usaEcfDriver() );

	private Tef tef = null;

	private Properties ppCompTef;

	private ImageIcon imgCanc = Icone.novo( "clVencido.gif" );

	private ImageIcon imgPago = Icone.novo( "clPago.gif" );

	private ImageIcon imgColuna = imgPago;

	private boolean bCancCupom = false;

	private String iCancItem = "";

	public DLCancCupom() {

		Funcoes.strDecimalToStrCurrency( 2, Funcoes.transValorInv( "15" ) + "" );
		setTitulo( "Cancela Venda" );
		setAtribos( 100, 150, 715, 300 );

		txtVenda.setPK( true );
		lcVenda.add( new GuardaCampo( txtVenda, "CodVenda", "Código", ListaCampos.DB_PK, false ) );
		lcVenda.add( new GuardaCampo( txtNota, "DocVenda", "Nota", ListaCampos.DB_SI, false ) );
		lcVenda.add( new GuardaCampo( txtSerie, "Serie", "Série", ListaCampos.DB_SI, false ) );
		lcVenda.add( new GuardaCampo( txtData, "DtEmitVenda", "Data", ListaCampos.DB_SI, false ) );
		lcVenda.add( new GuardaCampo( txtValor, "VlrLiqVenda", "Valor", ListaCampos.DB_SI, false ) );
		lcVenda.montaSql( false, "VENDA", "VD" );
		lcVenda.setWhereAdic( "TIPOVENDA='E'" );
		lcVenda.setReadOnly( true );
		txtVenda.setListaCampos( lcVenda );
		txtVenda.setNomeCampo( "CodVenda" );

		setPanel( pnCli );
		pnCli.add( spnTab, BorderLayout.CENTER );
		pnCli.add( pinCab, BorderLayout.NORTH );

		pnLegenda.add( new JLabelPad( "Não Cancelar", imgPago, SwingConstants.CENTER ) );
		pnLegenda.add( new JLabelPad( "Cancelar", imgCanc, SwingConstants.CENTER ) );

		pnRodape.add( pnLegenda, BorderLayout.WEST );

		pinCab.adic( new JLabelPad( "Cód da Venda" ), 7, 5, 80, 20 );
		pinCab.adic( txtVenda, 7, 25, 80, 20 );
		pinCab.adic( new JLabelPad( "Nota" ), 90, 5, 80, 20 );
		pinCab.adic( txtNota, 90, 25, 80, 20 );
		pinCab.adic( new JLabelPad( "Série" ), 173, 5, 30, 20 );
		pinCab.adic( txtSerie, 173, 25, 50, 20 );
		pinCab.adic( new JLabelPad( "Data" ), 226, 5, 80, 20 );
		pinCab.adic( txtData, 226, 25, 80, 20 );
		pinCab.adic( new JLabelPad( "Valor total" ), 309, 5, 120, 20 );
		pinCab.adic( txtValor, 309, 25, 120, 20 );
		pinCab.adic( btExec, 445, 15, 30, 30 );
		pinCab.adic( cbInteira, 7, 55, 200, 20 );
		pinCab.adic( btCanc, 540, 50, 120, 30 );

		tab.adicColuna( "" );
		tab.adicColuna( "Item" );
		tab.adicColuna( "Descrição" );
		tab.adicColuna( "Qtd" );
		tab.adicColuna( "Base ICMS" );
		tab.adicColuna( "Vlr. ICMS" );
		tab.adicColuna( "Preço" );
		tab.adicColuna( "Total" );
		tab.adicColuna( "Status" );

		tab.setTamColuna( 10, 0 );
		tab.setTamColuna( 28, 1 );
		tab.setTamColuna( 180, 2 );
		tab.setTamColuna( 60, 3 );
		tab.setTamColuna( 90, 4 );
		tab.setTamColuna( 90, 5 );
		tab.setTamColuna( 90, 6 );
		tab.setTamColuna( 100, 7 );
		tab.setTamColuna( 50, 8 );

		tab.addMouseListener( this );
		tab.addKeyListener( this );

		setToFrameLayout();

		btCanc.setToolTipText( "Cancelar Agora." );
		btCanc.addActionListener( this );
		btExec.setToolTipText( "Lista itens da venda" );
		btExec.addActionListener( this );

		cbInteira.setVlrString( "N" );

	}

	private Properties processaTef() {

		PreparedStatement ps = null;
		ResultSet rs = null;
		String sSQL = null;
		String sNSU = null;
		String sRede = null;
		Date dTrans = null;
		BigDecimal bigVlr = null;

		try {
			
			sSQL = "SELECT NSUTEF,REDETEF,DTTRANSTEF,VLRTEF " + 
				   "FROM VDTEF " + 
				   "WHERE CODEMP=? AND CODFILIAL=? AND CODVENDA=? AND TIPOVENDA='E'";

			ps = con.prepareStatement( sSQL );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "VDTEF" ) );
			ps.setInt( 3, txtVenda.getVlrInteger().intValue() );
			rs = ps.executeQuery();
			if ( rs.next() ) {
				sNSU = rs.getString( "NSUTEF" );
				sRede = rs.getString( "REDETEF" );
				dTrans = Funcoes.sqlDateToDate( rs.getDate( "DTTRANSTEF" ) );
				bigVlr = rs.getBigDecimal( "VLRTEF" );
			}

			rs.close();
			ps.close();
			
			if ( !con.getAutoCommit() ) {
				con.commit();
			}
			
		} catch ( SQLException err ) {
			err.printStackTrace();
			Logger.gravaLogTxt( "", Aplicativo.strUsuario, Logger.LGEB_BD, "Erro ao buscar tef vinculado no banco: " + err.getMessage() );
		}

		if ( sNSU == null ) {
			return null;
		}

		if ( tef == null && AplicativoPDV.bTEFTerm ) {
			tef = new Tef( Aplicativo.strTefEnv, Aplicativo.strTefRet );
		}

		Properties retTef = tef.solicCancelamento( sNSU.trim(), sRede.trim(), dTrans, bigVlr );

		if ( retTef == null || !tef.validaTef( retTef ) ) {
			return null;
		}

		return retTef;

	}

	private boolean cancVenda() {

		boolean bRet = false;
		PreparedStatement ps = null;

		if ( isVendaComTef() ) {
			if ( ( ppCompTef = processaTef() ) == null ) {
				Funcoes.mensagemInforma( this, "Não foi possível processar o cancelamento de TEF" );
				return false;
			}
		}
		// Primeiro estorna o pagamento:
		String sSQL = "UPDATE FNITRECEBER IR SET IR.STATUSITREC='R1' " + 
					  "WHERE IR.CODEMP=? AND IR.CODFILIAL=? " + 
					  "AND IR.CODREC IN ( SELECT R.CODREC " + 
					  " FROM FNRECEBER R " + 
					  " WHERE R.CODEMP=IR.CODEMP AND R.CODFILIAL=IR.CODFILIAL " + 
					  " AND R.CODEMPVA=? AND R.CODFILIALVA=? " +
					  " AND R.CODVENDA=? AND R.TIPOVENDA='E' )";
		try {

			ps = con.prepareStatement( sSQL );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "FNRECEBER" ) );
			ps.setInt( 3, Aplicativo.iCodEmp );
			ps.setInt( 4, ListaCampos.getMasterFilial( "VDVENDA" ) );
			ps.setInt( 5, txtVenda.getVlrInteger().intValue() );
			ps.executeUpdate();

			ps.close();
			
			if ( !con.getAutoCommit() ) {
				con.commit();
			}

			bRet = true;

		} catch ( SQLException err ) {
			err.printStackTrace();
			Logger.gravaLogTxt( "", Aplicativo.strUsuario, Logger.LGEB_BD, "Erro estornar o pagamento: " + err.getMessage() );
		}

		if ( bRet ) {
			
			sSQL = "UPDATE VDVENDA SET STATUSVENDA='CV' " + "WHERE CODEMP=? AND CODFILIAL=? AND CODVENDA=? AND TIPOVENDA='E'";
			
			try {

				ps = con.prepareStatement( sSQL );
				ps.setInt( 1, Aplicativo.iCodEmp );
				ps.setInt( 2, ListaCampos.getMasterFilial( "VDVENDA" ) );
				ps.setInt( 3, txtVenda.getVlrInteger().intValue() );
				ps.executeUpdate();

				ps.close();
				
				if ( !con.getAutoCommit() ) {
					con.commit();
				}

				bRet = true;

			} catch ( SQLException err ) {
				err.printStackTrace();
				Logger.gravaLogTxt( "", Aplicativo.strUsuario, Logger.LGEB_BD, "Erro cancelar o cupom: " + err.getMessage() );
			}
		}

		return bRet;

	}

	private boolean cancItem( int iItem ) {

		boolean bRet = false;
		PreparedStatement ps = null;
		String sSQL = null;
		
		try {

			sSQL = "UPDATE VDITVENDA SET CANCITVENDA='S' WHERE CODEMP=? AND CODFILIAL=? AND CODVENDA=? AND CODITVENDA=? AND TIPOVENDA='E'";

			ps = con.prepareStatement( sSQL );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "VDVENDA" ) );
			ps.setInt( 3, txtVenda.getVlrInteger().intValue() );
			ps.setInt( 4, iItem );
			ps.executeUpdate();

			ps.close();
			
			if ( !con.getAutoCommit() ) {
				con.commit();
			}

			bRet = true;
		} catch ( SQLException err ) {
			err.printStackTrace();
			Logger.gravaLogTxt( "", Aplicativo.strUsuario, Logger.LGEB_BD, "Erro cancelar o item " + err.getMessage() );
		}

		return bRet;

	}

	private void executaCanc() {

		if ( cbInteira.getVlrString().equals( "S" ) ) {
			if ( Funcoes.mensagemConfirma( null, "Deseja realmente cancelar o cupom?" ) == JOptionPane.YES_OPTION ) {
				if ( tab.getNumLinhas() <= 0 ) {
					Funcoes.mensagemErro( null, "Não a mais itens na venda\nEla não pode ser cancelada!" );
					return;
				}
				if ( cancVenda() ) {
					if ( AplicativoPDV.bECFTerm ) {
						if ( ecf.cancelaCupom() ) {
							bCancCupom = ppCompTef == null || finalizaTEF( ppCompTef );
							btOK.doClick();
						}
					}
				}
			}
		}
		else {
			for ( int i = 0; i < tab.getNumLinhas(); i++ ) {
				if ( ( (ImageIcon) tab.getValor( i, 0 ) ) == imgCanc ) {
					
					int iItem = Integer.parseInt( (String) tab.getValor( i, 1 ) );
					
					if ( Funcoes.mensagemConfirma( null, "Deseja realmente cancelar o item " + iItem + "?" ) == JOptionPane.YES_OPTION ) {
						if ( cancItem( iItem ) ) {
							if ( AplicativoPDV.bModoDemo || ecf != null ) {
								
								if ( AplicativoPDV.bECFTerm ) {
									
									ecf.cancelaItemGenerico( iItem );									
								}
								
								iCancItem += "," + iItem;
								btOK.doClick();
							}
						}
						else {
							Funcoes.mensagemErro( null, "Não foi possível cancelar o item." );
						}
					}
				}
			}
		}
		
	}

	private void carregaTabela() {

		PreparedStatement ps = null;
		ResultSet rs = null;
		String sSQL = null;
		int iRow = 0;
		
		try {
			
			sSQL = "SELECT IT.CODITVENDA,P.DESCPROD,IT.QTDITVENDA," + 
				   "IT.VLRBASEICMSITVENDA,IT.VLRICMSITVENDA,IT.VLRPRODITVENDA " + 
				   "FROM VDITVENDA IT, EQPRODUTO P " + 
				   "WHERE IT.CODEMP=? AND IT.CODFILIAL=? AND IT.CODVENDA=? AND IT.TIPOVENDA='E' " +
				   "AND P.CODEMP=IT.CODEMPPD AND P.CODFILIAL=IT.CODFILIALPD AND P.CODPROD=IT.CODPROD " + 
				   "AND (NOT IT.CANCITVENDA='S' OR IT.CANCITVENDA IS NULL) " + 
				   "ORDER BY CODITVENDA";

			ps = con.prepareStatement( sSQL );
			ps = con.prepareStatement( sSQL );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "VDITVENDA" ) );
			ps.setInt( 3, txtVenda.getVlrInteger().intValue() );
			rs = ps.executeQuery();			

			tab.limpa();
			imgColuna = imgPago;

			while ( rs.next() ) {
				tab.adicLinha();
				tab.setValor( imgColuna, iRow, 0 );
				tab.setValor( String.valueOf( rs.getInt( "CODITVENDA" ) ), iRow, 1 );
				tab.setValor( rs.getString( "DESCPROD" ), iRow, 2 );
				tab.setValor( Funcoes.strDecimalToStrCurrency( 10, 2, String.valueOf( rs.getDouble( "QTDITVENDA" ) ) ), iRow, 3 );
				tab.setValor( Funcoes.strDecimalToStrCurrency( 15, 2, String.valueOf( rs.getDouble( "VLRBASEICMSITVENDA" ) ) ), iRow, 4 );
				tab.setValor( Funcoes.strDecimalToStrCurrency( 15, 2, String.valueOf( rs.getDouble( "VLRICMSITVENDA" ) ) ), iRow, 5 );
				tab.setValor( Funcoes.strDecimalToStrCurrency( 20, 2, String.valueOf( rs.getDouble( "VLRPRODITVENDA" ) ) ), iRow, 6 );
				tab.setValor( Funcoes.strDecimalToStrCurrency( 23, 2, String.valueOf( rs.getDouble( "VLRPRODITVENDA" ) * rs.getDouble( "QTDITVENDA" ) ) ), iRow, 7 );
				iRow++;
			}
			
			rs.close();
			ps.close();
			
			if ( !con.getAutoCommit() ) {
				con.commit();
			}

		} catch ( SQLException err ) {
			err.printStackTrace();
			Funcoes.mensagemErro( this, "Erro carregar ítens da venda!\n" + err.getMessage(), true, con, err );
		} finally {
			ps = null;
			rs = null;
			sSQL = null;
		}

	}

	private void marcaItem( int iItem ) {

		//carregaTabela();
		
		if ( (ImageIcon) tab.getValor( iItem, 0 ) == imgCanc ) {
			imgColuna = imgPago;
		}
		else {
			imgColuna = imgCanc;
		}
		
		tab.setValor( imgColuna, iItem, 0 );
		
	}

	private boolean finalizaTEF( Properties retTef ) {

		boolean bRet = false;
		Object sLinhas[] = tef.retImpTef( retTef );
		String sComprovante = "";

		// verifica se ha linhas a serem impressas, caso contrário sai sem
		// imprimir nada.
		if ( sLinhas.length == 0 ) {
			return true;
		}

		for ( int i = 0; i < sLinhas.length; i++ ) {
			sComprovante += sLinhas[ i ] + "\n";
		}

		while ( ! bRet ) {
			if ( ! ecf.relatorioGerencialTef( sComprovante ) ) {
				bRet = false;
			}
			else {
				if ( ! ecf.fechaRelatorioGerencial() ) {
					bRet = false;
				}
				else {
					bRet = true;
				}
			}
		}
		
		tef.confirmaCNF( retTef );
		
		return bRet;

	}

	private boolean isVendaComTef() {

		PreparedStatement ps = null;
		ResultSet rs = null;
		String sSQL = null;
		int iRet = 0;
		
		try {

			sSQL = "SELECT COUNT(*) FROM VDTEF WHERE CODEMP=? AND CODFILIAL=? AND CODVENDA=? AND TIPOVENDA='E'";

			ps = con.prepareStatement( sSQL );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "VDTEF" ) );
			ps.setInt( 3, txtVenda.getVlrInteger().intValue() );
			rs = ps.executeQuery();
			
			if ( rs.next() ) {
				iRet = rs.getInt( 1 );
			}

			rs.close();
			ps.close();
			
			if ( !con.getAutoCommit() ) {
				con.commit();
			}
			
		} catch ( SQLException err ) {
			err.printStackTrace();
			Logger.gravaLogTxt( "", Aplicativo.strUsuario, Logger.LGEB_BD, "Erro ao verificar tef vinculado no banco: " + err.getMessage() );
		} finally {
			ps = null;
			rs = null;
			sSQL = null;
		}

		return iRet > 0;

	}

	public boolean isCancCupom() {

		return bCancCupom;
	}

	public int[] getCancItem() {
		
		String[] tmp = new String[]{"-1"};
		
		try {
			tmp = iCancItem.substring( 1 ).split( "," );
		} catch ( Exception e ) {
			// ignora o erro
		}
		
		int[] ret = new int[ tmp.length ];
		
		for ( int i=0; i < ret.length; i++ ) {
			
			ret[ i ] = Integer.parseInt( tmp[ i ] );
			
		}

		return ret;
	}

	public void setVenda( int iCodVenda ) {

		txtVenda.setVlrInteger( new Integer( iCodVenda ) );
	}

	public void actionPerformed( ActionEvent evt ) {

		if ( evt.getSource() == btCanc ) {
			executaCanc();
			carregaTabela();
		}
		else if ( evt.getSource() == btExec ) {
			carregaTabela();
		}

		super.actionPerformed( evt );
	}

	public void mouseEntered( MouseEvent mevt ) {

	}

	public void mouseExited( MouseEvent mevt ) {

	}

	public void mousePressed( MouseEvent mevt ) {

	}

	public void mouseReleased( MouseEvent mevt ) {

	}

	public void mouseClicked( MouseEvent mevt ) {

		if ( mevt.getClickCount() == 2 && tab.getLinhaSel() >= 0 ) {
			marcaItem( tab.getLinhaSel() );
		}

	}

	public void keyTyped( KeyEvent kevt ) {

	}

	public void keyReleased( KeyEvent kevt ) {

	}

	public void keyPressed( KeyEvent kevt ) {

		if ( ( kevt.getKeyCode() == KeyEvent.VK_ENTER ) || ( kevt.getKeyCode() == KeyEvent.VK_SPACE ) ) {
			if ( ( kevt.getSource() == tab ) && ( tab.getLinhaSel() >= 0 ) ) {
				marcaItem( tab.getLinhaSel() );
			}
		}

	}

	public void setConexao( Connection cn ) {

		lcVenda.setConexao( cn );
		super.setConexao( cn );

		if ( txtVenda.getVlrInteger().intValue() != 0 ) {
			txtVenda.setAtivo( false );
		}

		lcVenda.carregaDados();
		carregaTabela();

	}

}
