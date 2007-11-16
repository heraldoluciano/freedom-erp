/**
 * @version 23/08/2007 <BR>
 * @author Setpoint Informática Ltda./Reginaldo Garcia Heua <BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.std <BR>
 * Classe:
 * @(#)FRCodbarProd.java <BR>
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
 */
package org.freedom.modulos.std;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;

import net.sf.jasperreports.engine.JasperPrintManager;

import org.freedom.acao.CarregaEvent;
import org.freedom.acao.CarregaListener;
import org.freedom.bmps.Icone;
import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.ImprimeOS;
import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JPanelPad;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.Tabela;
import org.freedom.funcoes.EtiquetaPPLA;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FPrinterJob;
import org.freedom.telas.FRelatorio;

public class FRCodbarProd extends FRelatorio implements ActionListener, CarregaListener, KeyListener {

	private static final long serialVersionUID = 1L;

	private JTextFieldPad txtCodProd = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 10, 0 );

	private JTextFieldFK txtDescProd = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtRefProd = new JTextFieldPad( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtCodBarProd = new JTextFieldPad( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtQtdPod = new JTextFieldPad( JTextFieldPad.TP_STRING, 2, 0 );

	private JButton btExecuta = new JButton( Icone.novo( "btExecuta.gif" ) );

	private JButton btSelectCompra = new JButton( Icone.novo( "btPesquisa.gif" ) );

	private JButton btExcluir = new JButton( Icone.novo( "btCancelar.gif" ) );

	private JButton btExcluirTudo = new JButton( Icone.novo( "btNada.gif" ) );

	private Tabela tabGrid = new Tabela();

	private JScrollPane spnGrid = new JScrollPane( tabGrid );

	private ListaCampos lcProduto = new ListaCampos( this );

	private JPanelPad pnCampos = new JPanelPad( 600, 95 );

	private JPanelPad pnBotoesGrid = new JPanelPad( 35, 200 );

	private JPanelPad pnGrid = new JPanelPad( 600, 200 );

	private JComboBox cbSel = null;

	public FRCodbarProd() {

		super( true );
		setTitulo( "Etiquetas de código de barras" );
		setAtribos( 80, 30, 520, 380 );

		montaTela();
		montaListaCampos();
	}

	private void montaListaCampos() {

		/*
		 * Produto
		 */

		lcProduto.add( new GuardaCampo( txtCodProd, "CodProd", "Cód.produto", ListaCampos.DB_PK, true ) );
		lcProduto.add( new GuardaCampo( txtDescProd, "DescProd", "Descrição do produto", ListaCampos.DB_SI, false ) );
		lcProduto.add( new GuardaCampo( txtRefProd, "RefProd", "Ref. produto", ListaCampos.DB_SI, false ) );
		lcProduto.add( new GuardaCampo( txtCodBarProd, "CodBarProd", "Cód. Barras", ListaCampos.DB_SI, false ) );
		txtCodProd.setTabelaExterna( lcProduto );
		txtCodProd.setNomeCampo( "CodProd" );
		txtCodProd.setFK( true );
		lcProduto.setReadOnly( true );
		lcProduto.montaSql( false, "PRODUTO", "EQ" );

	}

	private void montaTela() {

		Container c = getContentPane();

		c.add( pnCampos, BorderLayout.NORTH );
		c.add( pnGrid, BorderLayout.CENTER );
		c.add( pnBotoesGrid, BorderLayout.EAST );
		c.add( spnGrid );

		pnCampos.adic( new JLabelPad( "Cód. Produto" ), 07, 10, 100, 20 );
		pnCampos.adic( txtCodProd, 07, 30, 80, 20 );
		pnCampos.adic( new JLabelPad( "Descrição do produto" ), 93, 10, 200, 20 );
		pnCampos.adic( txtDescProd, 93, 30, 280, 20 );
		pnCampos.adic( new JLabelPad( "qtd." ), 375, 10, 60, 20 );
		pnCampos.adic( txtQtdPod, 375, 30, 60, 20 );
		pnCampos.adic( btExecuta, 445, 20, 50, 30 );
		pnCampos.adic( btSelectCompra, 445, 55, 50, 30 );
		pnBotoesGrid.adic( btExcluir, 0, 0, 30, 30 );
		pnBotoesGrid.adic( btExcluirTudo, 0, 30, 30, 30 );

		tabGrid.adicColuna( "Cód. prod" );
		tabGrid.adicColuna( "Descrição do produto" );
		tabGrid.adicColuna( "Qtd" );

		tabGrid.setTamColuna( 60, EProduto.CODPROD.ordinal() );
		tabGrid.setTamColuna( 330, EProduto.DESCPROD.ordinal() );
		tabGrid.setTamColuna( 60, EProduto.QTDPROD.ordinal() );

		lcProduto.addCarregaListener( this );

		btExecuta.addActionListener( this );
		btSelectCompra.addActionListener( this );
		btExcluir.addActionListener( this );
		btExcluirTudo.addActionListener( this );
		txtQtdPod.addKeyListener( this );

		btExecuta.setToolTipText( "Executar" );
		btSelectCompra.setToolTipText( "Selecionar produtos da compra" );
		btExcluir.setToolTipText( "Ecluir" );
		btExcluirTudo.setToolTipText( "Excluir tudo" );

	}

	private void adicLinha( BigDecimal qtd, int codprod, String descprod ) {

		int pos = -1;

		if ( codprod == 0 ) {

			Funcoes.mensagemInforma( this, "Produto não encontrado!" );
			txtCodProd.requestFocus();
			return;
		}

		for ( int i = 0; i < tabGrid.getNumLinhas(); i++ ) {

			if ( codprod == ( (Integer) tabGrid.getValor( i, EProduto.CODPROD.ordinal() ) ).intValue() ) {
				pos = i;
				qtd = qtd.add( (BigDecimal) tabGrid.getValor( i, EProduto.QTDPROD.ordinal() ) );
				break;
			}
		}

		if ( pos == -1 ) {

			tabGrid.adicLinha();
			pos = tabGrid.getNumLinhas() - 1;
		}

		tabGrid.setValor( codprod, pos, EProduto.CODPROD.ordinal() );
		tabGrid.setValor( descprod, pos, EProduto.DESCPROD.ordinal() );
		tabGrid.setValor( qtd, pos, EProduto.QTDPROD.ordinal() );

	}

	private void excluiLinha() {

		if ( tabGrid.getLinhaSel() != -1 ) {

			tabGrid.delLinha( tabGrid.getLinhaSel() );
		}
		else {

			Funcoes.mensagemInforma( this, "Selecione uma linha na lista!" );
		}
	}

	private void excluiTudo() {

		tabGrid.limpa();
	}

	private void selectCompra() {

		DLEtiqCompra dl = new DLEtiqCompra( this );
		dl.setConexao( con );

		dl.setVisible( true );

		int codcompra = dl.getCompra();

		if ( dl.OK && codcompra > 0 ) {

			// fazer select dos itens da compra e colocar na tabela com o
			// adiclinha( quantidade, codigo do produto, descrição do produto).

			StringBuilder sql = new StringBuilder();
			sql.append( "SELECT IT.CODPROD, IT.QTDITCOMPRA, PD.DESCPROD " );
			sql.append( "FROM CPITCOMPRA IT, EQPRODUTO PD, CPCOMPRA C " );
			sql.append( "WHERE C.CODEMP=? AND C.CODFILIAL=? AND C.CODCOMPRA=? AND " );
			sql.append( "IT.CODEMP=C.CODEMP AND IT.CODFILIAL=C.CODFILIAL AND IT.CODCOMPRA=C.CODCOMPRA AND " );
			sql.append( "PD.CODEMP=IT.CODEMPPD AND PD.CODFILIAL=IT.CODFILIALPD AND PD.CODPROD=IT.CODPROD " );

			try {

				PreparedStatement ps = con.prepareStatement( sql.toString() );
				ps.setInt( 1, Aplicativo.iCodEmp );
				ps.setInt( 2, ListaCampos.getMasterFilial( "CPITCOMPRA" ) );
				ps.setInt( 3, codcompra );

				ResultSet rs = ps.executeQuery();

				while ( rs.next() ) {

					adicLinha( rs.getBigDecimal( "QTDITCOMPRA" ).setScale( 0, BigDecimal.ROUND_HALF_UP ), rs.getInt( "CODPROD" ), rs.getString( "DESCPROD" ) );
				}

				rs.close();
				ps.close();

				if ( !con.getAutoCommit() ) {
					con.commit();
				}
			} catch ( SQLException e ) {
				e.printStackTrace();
				Funcoes.mensagemErro( this, "Erro ao carregar itens da compra!\n" + e.getMessage(), true, con, e );
			}
		}

		dl.dispose();
	}

	private int getNrConexao() {

		int conexao = -1;

		StringBuilder sql = new StringBuilder();

		sql.append( "SELECT CURRENT_CONNECTION FROM SGEMPRESA E WHERE E.CODEMP=?" );

		try {

			PreparedStatement ps = con.prepareStatement( sql.toString() );
			ps.setInt( 1, Aplicativo.iCodEmp );

			ResultSet rs = ps.executeQuery();

			if ( rs.next() ) {
				conexao = rs.getInt( "CURRENT_CONNECTION" );
			}

			rs.close();
			ps.close();

			if ( !con.getAutoCommit() ) {
				con.commit();
			}

		} catch ( SQLException e ) {
			e.printStackTrace();
			Funcoes.mensagemErro( this, "Erro ao buscar número da conexão!\n" + e.getMessage(), true, con, e );
		}

		return conexao;
	}

	private boolean removeEtiquetas() {

		boolean retorno = false;

		StringBuilder sql = new StringBuilder();

		sql.append( "DELETE FROM EQETIQPROD E WHERE E.NRCONEXAO=?" );

		try {

			PreparedStatement ps = con.prepareStatement( sql.toString() );
			ps.setInt( 1, getNrConexao() );

			int exec = ps.executeUpdate();

			ps.close();

			if ( !con.getAutoCommit() ) {
				con.commit();
			}

			if ( exec > -1 ) {
				retorno = true;
			}

		} catch ( SQLException e ) {
			e.printStackTrace();
			Funcoes.mensagemErro( this, "Erro ao limpar tabela temporaria de etiquetas!\n" + e.getMessage(), true, con, e );
		}

		return retorno;
	}

	private boolean persistEtiquetas() {

		boolean retorno = false;

		int conexao = getNrConexao();

		StringBuilder sSql = new StringBuilder();

		sSql.append( "INSERT INTO EQETIQPROD " );
		sSql.append( "( NRCONEXAO, CODEMP, CODFILIAL, CODPROD ) " );
		sSql.append( "VALUES " );
		sSql.append( "( ?, ?, ?, ? )" );

		String sql = sSql.toString();

		int codprod = 0;
		int quantidade = 0;

		etiquetas : {

			for ( int i = 0; i < tabGrid.getNumLinhas(); i++ ) {

				codprod = (Integer) tabGrid.getValor( i, EProduto.CODPROD.ordinal() );
				quantidade = ( (BigDecimal) tabGrid.getValor( i, EProduto.QTDPROD.ordinal() ) ).intValue();

				for ( int j = 0; j < quantidade; j++ ) {
					if ( !insetEtiqueta( conexao, codprod, sql ) ) {
						break etiquetas;
					}
				}
			}

			retorno = true;
		}

		return retorno;
	}

	private boolean insetEtiqueta( int conexao, int codprod, String sql ) {

		boolean retorno = true;

		try {

			PreparedStatement ps = con.prepareStatement( sql.toString() );
			ps.setInt( 1, conexao );
			ps.setInt( 2, Aplicativo.iCodEmp );
			ps.setInt( 3, ListaCampos.getMasterFilial( "EQETIQPROD" ) );
			ps.setInt( 4, codprod );

			ps.execute();

			if ( !con.getAutoCommit() ) {
				con.commit();
			}
		} catch ( SQLException e ) {
			retorno = false;
			e.printStackTrace();
			Funcoes.mensagemErro( this, "Erro ao relacionar etiquetas!\n" + e.getMessage(), true, con, e );
		}

		return retorno;
	}
	
	private ResultSet getEtiquetas() {
		
		ResultSet rs = null;
		try {
			StringBuffer sSQL = new StringBuffer();
			sSQL.append( "SELECT E.CODPROD, P.DESCPROD, P.CODBARPROD, P.PRECOBASEPROD " );
			sSQL.append( "FROM EQETIQPROD E, EQPRODUTO P " );
			sSQL.append( "WHERE E.NRCONEXAO=? AND " );
			sSQL.append( "P.CODEMP=E.CODEMP AND P.CODFILIAL=E.CODFILIAL AND P.CODPROD=E.CODPROD " );

			PreparedStatement ps = con.prepareStatement( sSQL.toString() );
			ps.setInt( 1, getNrConexao() );

			rs = ps.executeQuery();
		} catch ( SQLException e ) {
			e.printStackTrace();
		}
		
		return rs;
	}

	private Object[] montaEtiquetas() {

		Object[] buffer = new Object[ 3 ];
		StringBuilder bufferImprimir = new StringBuilder();
		ImprimeOS imp = new ImprimeOS( "", con );

		try {

			buffer[ 0 ] = imp;
			buffer[ 1 ] = bufferImprimir;
			
			ResultSet rs = getEtiquetas(); 

			EtiquetaPPLA etiqueta;

			while ( rs.next() ) {

				etiqueta = new EtiquetaPPLA();

				etiqueta.printString( 100, 50, rs.getString( "DESCPROD" ) );

				bufferImprimir.append( etiqueta.command() );
			}

		} catch ( Exception e ) {
			e.printStackTrace();
			buffer = null;
		}

		return buffer;
	}

	public void imprimir( boolean bVisualizar ) {
		
		if ( removeEtiquetas() ) {

			if ( persistEtiquetas() ) {

				// visualização.
				if ( bVisualizar ) {

					FPrinterJob dlGr = null;
					dlGr = new FPrinterJob( "relatorios/FRCodBarProd.jasper", "Etiquetas", null, getEtiquetas(), null, this );
					dlGr.setVisible( true );
				}
				// impressão.
				else {
					
					if ( true ) {
						try {
							FPrinterJob dlGr = null;
							dlGr = new FPrinterJob( "relatorios/FRCodBarProd.jasper", "Etiquetas", null, getEtiquetas(), null, this );
							JasperPrintManager.printReport( dlGr.getRelatorio(), true );
						} catch ( Exception err ) {
							Funcoes.mensagemErro( this, "Erro na impressão de Etiquetas!" + err.getMessage(), true, con, err );
						}	
					}
					// impressora de etiquetas
					else {

						ImprimeOS imp = new ImprimeOS( "", con );
						Object[] etiquetas = montaEtiquetas();
						//imp.gravaTexto( etiquetas[ 1 ].toString() );
						//imp.fechaGravacao();	
						//imp.preview( this );					
						//imp.print();
					}
				}
			}
		}
	}

	public void setConexao( Connection cn ) {

		super.setConexao( cn );
		lcProduto.setConexao( cn );

	}

	public void afterCarrega( CarregaEvent cevt ) {

		if ( cevt.getListaCampos() == lcProduto ) {
			txtQtdPod.setVlrString( "1" );
		}
	}

	@ Override
	public void keyPressed( KeyEvent kevt ) {

		super.keyPressed( kevt );

		if ( kevt.getSource() == txtQtdPod ) {

			if ( kevt.getKeyCode() == KeyEvent.VK_ENTER ) {
				btExecuta.doClick();
			}
		}
	}

	public void actionPerformed( ActionEvent evt ) {

		super.actionPerformed( evt );

		if ( evt.getSource() == btExecuta ) {
			adicLinha( new BigDecimal( txtQtdPod.getVlrString() ), txtCodProd.getVlrInteger().intValue(), txtDescProd.getVlrString() );
			txtCodProd.requestFocus();
		}
		else if ( evt.getSource() == btSelectCompra ) {
			selectCompra();
		}
		else if ( evt.getSource() == btExcluir ) {
			excluiLinha();
		}
		else if ( evt.getSource() == btExcluirTudo ) {
			excluiTudo();
			txtCodProd.requestFocus();
		}
	}

	public void beforeCarrega( CarregaEvent cevt ) {

	}

	private enum EProduto {

		CODPROD, DESCPROD, QTDPROD
	}
}
