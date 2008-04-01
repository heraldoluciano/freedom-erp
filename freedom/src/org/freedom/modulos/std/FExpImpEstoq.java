/**
 * @version 03/2008 <BR>
 * @author Setpoint Informática Ltda.<BR>
 * @author Alex Rodrigues
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.rep <BR>
 * Classe:
 * @(#)FExpImpEstoq.java <BR>
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
 * Formulário de exportação e importação de saldo de estoque.
 * 
 */

package org.freedom.modulos.std;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;

import org.freedom.acao.Processo;
import org.freedom.acao.RadioGroupEvent;
import org.freedom.acao.RadioGroupListener;
import org.freedom.bmps.Icone;
import org.freedom.componentes.JPanelPad;
import org.freedom.componentes.JRadioGroup;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.ProcessoSec;
import org.freedom.componentes.Tabela;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FFilho;

public class FExpImpEstoq extends FFilho implements ActionListener, RadioGroupListener {

	private static final long serialVersionUID = 1L;
	
	private static final int EXPORTAR = 0;
	
	private static final int IMPORTAR = 1;

	private final JPanelPad panelImportacao = new JPanelPad();

	private final JPanelPad panelRodape = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private final JPanelPad panelBotoes = new JPanelPad( JPanelPad.TP_JPANEL, new FlowLayout( FlowLayout.CENTER, 6, 4 ) );

	private final JPanelPad panelSair = new JPanelPad( JPanelPad.TP_JPANEL, new FlowLayout( FlowLayout.CENTER, 6, 4 ) );

	private final JTextFieldPad txtDiretorio = new JTextFieldPad( JTextFieldPad.TP_STRING, 100, 0 );
	
	private final JRadioGroup<String, Integer> rgOpcao;
	
	private final Tabela tabProdutos = new Tabela();

	private final JButton btBuscarProdutos = new JButton( "Buscar Produtos" );

	private final JButton btExeportar = new JButton( "Exportar" );

	private final JButton btImportar = new JButton( "Importar" );

	private final JButton btInventario = new JButton( "Executar Inventário" );

	private final JButton btDirtorio = new JButton( "..." );

	private final JButton btSair = new JButton( "Sair", Icone.novo( "btSair.gif" ) );

	private final JProgressBar status = new JProgressBar();
	
	private List<String> produtos = new ArrayList<String>();


	public FExpImpEstoq() {

		super( false );
		setAtribos( 100, 100, 520, 440 );
		
		Vector<String> labs = new Vector<String>();
		labs.add( "Exportar" );
		labs.add( "Importar" );
		Vector<Integer> vals = new Vector<Integer>();
		vals.add( EXPORTAR );
		vals.add( IMPORTAR );
		rgOpcao = new JRadioGroup<String, Integer>( 1, 2, labs, vals );

		btBuscarProdutos.addActionListener( this );
		btExeportar.addActionListener( this );
		btImportar.addActionListener( this );
		btInventario.addActionListener( this );
		btDirtorio.addActionListener( this );
		btSair.addActionListener( this );
		
		rgOpcao.addRadioGroupListener( this );
			
		status.setStringPainted( true );
		status.setString( "Selecione a local do arquivo e exportação ..." );

		montaTela();

		btBuscarProdutos.setEnabled( false );
		btExeportar.setEnabled( false );
		btImportar.setEnabled( false );
		btInventario.setEnabled( false );		

		btBuscarProdutos.setVisible( true );
		btExeportar.setVisible( true );
		btImportar.setVisible( false );
		btInventario.setVisible( false );
	}

	private void montaTela() {

		getContentPane().setLayout( new BorderLayout() );
		
		tabProdutos.adicColuna( "Código" );
		tabProdutos.adicColuna( "Descrição" );
		tabProdutos.adicColuna( "Preço/Custo" );
		tabProdutos.adicColuna( "Saldo" );
		
		tabProdutos.setTamColuna( 70, 0 );
		tabProdutos.setTamColuna( 245, 1 );
		tabProdutos.setTamColuna( 70, 0 );
		tabProdutos.setTamColuna( 70, 0 );		

		btExeportar.setPreferredSize( new Dimension( 120, 30 ) );
		btBuscarProdutos.setPreferredSize( new Dimension( 150, 30 ) );
		btImportar.setPreferredSize( new Dimension( 120, 30 ) );
		btInventario.setPreferredSize( new Dimension( 150, 30 ) );

		panelRodape.setPreferredSize( new Dimension( 100, 44 ) );
		panelRodape.setBorder( BorderFactory.createEtchedBorder() );
		
		panelImportacao.adic( rgOpcao, 10, 10, 484, 30 );
		panelImportacao.adic( new JLabel( "Local da arquivo" ), 10, 50, 350, 20 );
		panelImportacao.adic( txtDiretorio, 10, 70, 450, 20 );
		panelImportacao.adic( btDirtorio, 470, 68, 24, 24 );				
		panelImportacao.adic( new JScrollPane( tabProdutos ), 10, 110, 484, 200 );
		panelImportacao.adic( status, 10, 320, 484, 20 );

		panelBotoes.add( btBuscarProdutos );
		panelBotoes.add( btExeportar );		
		panelBotoes.add( btImportar );
		panelBotoes.add( btInventario );

		panelSair.add( btSair );

		panelRodape.add( panelBotoes, BorderLayout.WEST );
		panelRodape.add( panelSair, BorderLayout.EAST );

		getContentPane().add( panelImportacao, BorderLayout.CENTER );
		getContentPane().add( panelRodape, BorderLayout.SOUTH );
	}

	private void getDiretorio() {

		try {

			FileDialog fileDialog = new FileDialog( Aplicativo.telaPrincipal, "Selecionar diretorio." );
			fileDialog.setVisible( true );

			if ( fileDialog.getDirectory() != null ) {

				if ( EXPORTAR == rgOpcao.getVlrInteger() ) {
					txtDiretorio.setVlrString( fileDialog.getDirectory() );
					btBuscarProdutos.setEnabled( true );
					status.setString( "Buscar produtos para exportação ..." );
				}
				else if ( IMPORTAR == rgOpcao.getVlrInteger() ) {
					txtDiretorio.setVlrString( fileDialog.getDirectory() + fileDialog.getFile() );
					btImportar.setEnabled( true );
					status.setString( "Importar produtos do arquivo " + fileDialog.getFile() + " ..." );
				}
			}
			else {
				txtDiretorio.setVlrString( "" );
				btDirtorio.requestFocus();
			}
		} catch ( Exception e ) {
			e.printStackTrace();
		}
	}
	
	private void carregaProdutosExportacao() {
		
		produtos = new ArrayList<String>();
		
		try {
	
			status.setString( "Carregando produtos para exportação ..." );
			
			StringBuilder sql = new StringBuilder();
			sql.append( "SELECT P.CODPROD, P.DESCPROD, P.PRECOBASEPROD, P.SLDLIQPROD " );
			sql.append( "FROM EQPRODUTO P " );
			sql.append( "WHERE P.CODEMP=? AND CODFILIAL=? AND CLOTEPROD='N'" );
			sql.append( "ORDER BY P.CODPROD" );
			
			PreparedStatement ps = con.prepareStatement( sql.toString() );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "EQPRODUTO" ) );
			
			ResultSet rs = ps.executeQuery();
			
			StringBuilder tmp = new StringBuilder();
			tabProdutos.limpa();
			
			DecimalFormat df1 = new DecimalFormat( "00000000" );
			DecimalFormat df2 = new DecimalFormat( "00000.00" );
			
			while ( rs.next() ) {
				
				tmp.delete( 0, tmp.length() );
				tmp.append( df1.format( rs.getInt( "CODPROD" ) ) + "," );
				tmp.append( df2.format( rs.getBigDecimal( "PRECOBASEPROD" ) ).replace( ',', '.' ) + "," );
				tmp.append( Funcoes.copy( rs.getString( "DESCPROD" ).trim(), 30 ) + "," );
				tmp.append( df2.format( rs.getBigDecimal( "SLDLIQPROD" ) ).replace( ',', '.' ) );
				
				tabProdutos.adicLinha( new Object[] {
						rs.getInt( "CODPROD" ),
						rs.getString( "DESCPROD" ).trim(),
						rs.getBigDecimal( "PRECOBASEPROD" ).setScale( 2, BigDecimal.ROUND_HALF_UP ),
						rs.getBigDecimal( "SLDLIQPROD" ).setScale( 2, BigDecimal.ROUND_HALF_UP )
				});
				
				produtos.add( tmp.toString() );
			}
			
			status.setString( "Carregandos " + produtos.size() + " produtos para exportação..." );
			
			txtDiretorio.setEnabled( false );
			btDirtorio.setEnabled( false );
			btBuscarProdutos.setEnabled( false );
			btExeportar.setEnabled( true );
			
			rs.close();
			ps.close();
			
			if ( ! con.getAutoCommit() ) {
				con.commit();
			}
		} catch ( SQLException e ) {
			e.printStackTrace();
			Funcoes.mensagemErro( this, "Erro ao buscar produtos!\n" + e.getMessage(), true, con, e );
		}
	}

	private void exportar() {
		
		if ( produtos == null || produtos.size() == 0 ) {
			Funcoes.mensagemInforma( this, "Não a produtos a exportar!" );
			return;
		}
		
		try {
			
			File file = new File( txtDiretorio.getVlrString().trim() + "produtos.txt" );
			file.createNewFile();
			FileWriter fw = new FileWriter( file );
			BufferedWriter bw = new BufferedWriter( fw );
			
			int indice = 1;
			status.setString( "Exportando ..." );
			status.setMaximum( produtos.size() );
			
			for ( String linha : produtos ) {
				bw.write( linha + "\n" );
				status.setValue( indice++ );
			}
			
			bw.flush();
			bw.close();
			
			status.setString( "Concluido !" );
			status.setMaximum( 0 );
			
			btExeportar.setEnabled( false );
			
		} catch ( IOException e ) {
			e.printStackTrace();
			Funcoes.mensagemErro( this, "Erro ao exportar produtos!\n" + e.getMessage(), true, con, e );
		}		
	}
	
	private void importar() {
		
		try {			
			
			File file = new File( txtDiretorio.getVlrString().trim() );

			if ( file.exists() ) {

				FileReader fileReader = new FileReader( file );
				
				if ( fileReader == null ) {
					Funcoes.mensagemInforma( this, "Arquivo não encontrado" );
					return;
				}
				
				BufferedReader bufferedReader = new BufferedReader( fileReader );
				List<String> saldoImportacao = new ArrayList<String>();	
				String tmp = null;

				while ( ( tmp = bufferedReader.readLine() ) != null ) {					
					if ( tmp.length() >= 57 ) {					
						saldoImportacao.add( tmp );
					}
				}
				
				Object[] elementos = new Object[ 4 ];
				
				tabProdutos.limpa();
				
				status.setString( "Carregando saldos de produtos ..." );
				status.setMaximum( saldoImportacao.size() );
				int indice = 1;				
				Integer codprod = null;
				
				for ( String linha : saldoImportacao ) {
					
					codprod = Integer.parseInt( linha.substring( 0, 8 ) );
					
					elementos[ 0 ] = codprod;
					elementos[ 1 ] = linha.substring( 18, 48 );
					elementos[ 2 ] = getCusto( codprod );
					elementos[ 3 ] = new BigDecimal( linha.substring( 49, 57 ).trim() ).setScale( Aplicativo.casasDec, BigDecimal.ROUND_HALF_UP );
					
					tabProdutos.adicLinha( elementos );
					
					status.setValue( indice++ );
				}
				
				status.setString( "Saldos de produtos carragados ..." );

				txtDiretorio.setEnabled( false );
				btDirtorio.setEnabled( false );
				btImportar.setEnabled( false );
				btInventario.setEnabled( true );
			}
		} catch ( Exception e ) {
			e.printStackTrace();
			Funcoes.mensagemErro( this, "Erro ao importar arquivo!\n" + e.getMessage(), true, con, e );
		}
	}
	
	private BigDecimal getCusto( final Integer codprod ) throws Exception {
		
		BigDecimal custo = new BigDecimal( "0.00" );
		
		StringBuilder sql = new StringBuilder();
		sql.append( "SELECT NCUSTOMPMAX FROM EQPRODUTOSP01(?,?,?,?,?,?)" );
		
		PreparedStatement ps = con.prepareStatement( sql.toString() );
		ps.setInt( 1, Aplicativo.iCodEmp );
		ps.setInt( 2, ListaCampos.getMasterFilial( "EQPRODUTO" ) );
		ps.setInt( 3, codprod );
		ps.setInt( 4, Aplicativo.iCodEmp );
		ps.setInt( 5, ListaCampos.getMasterFilial( "EQALMOX" ) );
		ps.setInt( 6, getAlmoxarifado( codprod ) );
		
		ResultSet rs = ps.executeQuery();
		
		if ( rs.next() ) {
			custo = rs.getBigDecimal( "NCUSTOMPMAX" ) != null ? rs.getBigDecimal( "NCUSTOMPMAX" ) : custo;
		}
		
		rs.close();
		ps.close();
		
		if ( ! con.getAutoCommit() ) {
			con.commit();
		}
		
		return custo.setScale( Aplicativo.casasDecFin, BigDecimal.ROUND_HALF_UP );
	}
	
	private Integer getAlmoxarifado( final Integer codprod ) throws Exception {
		
		Integer almoxarifado = null;
		
		StringBuilder sql = new StringBuilder();
		sql.append( "SELECT CODALMOX FROM EQPRODUTO WHERE CODEMP=? AND CODFILIAL=? AND CODPROD=?" );
		
		PreparedStatement ps = con.prepareStatement( sql.toString() );
		ps.setInt( 1, Aplicativo.iCodEmp );
		ps.setInt( 2, ListaCampos.getMasterFilial( "EQPRODUTO" ) );
		ps.setInt( 3, codprod );
		
		ResultSet rs = ps.executeQuery();
		
		if ( rs.next() ) {
			almoxarifado = rs.getInt( "CODALMOX" );
		}
		
		rs.close();
		ps.close();
		
		if ( ! con.getAutoCommit() ) {
			con.commit();
		}
		
		return almoxarifado;
	}
	
	private void criarInventario() {
		
		try {
			
			// para evitar de dar o commit no meio do loop e o rollback de erro não adiantar.
			final boolean commit = con.getAutoCommit();			
			con.setAutoCommit( false );
			
			int tamanhoTabela = tabProdutos.getNumLinhas();
			
			status.setString( "Realizando inventarios ..." );
			status.setMaximum( tamanhoTabela );
			
			StringBuilder sql = new StringBuilder();
			sql.append( "INSERT INTO EQINVPROD " );
			sql.append( "(CODEMP, CODFILIAL, CODINVPROD, " );
			sql.append( "CODEMPPD, CODFILIALPD, CODPROD, " );
			sql.append( "CODEMPTM, CODFILIALTM, CODTIPOMOV, " );
			sql.append( "DATAINVP, QTDINVP, PRECOINVP, " );
			sql.append( "CODEMPAX, CODFILIALAX, CODALMOX) " );
			sql.append( "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) " );

			final Integer tipoMovimentoInventario = getTipoMovimentoInventario();
			final Date hoje = Funcoes.dateToSQLDate( Calendar.getInstance().getTime() );
			Integer codprod = null;
			BigDecimal quantidade = null;
			BigDecimal custo = null;
			
			Integer codInventario = null;
			Integer codAlmoarifado = null;
			
			PreparedStatement ps = null;
			
			for ( int i = 0; i < tamanhoTabela; i++ ) {
								
				codprod = (Integer) tabProdutos.getValor( i, 0 );
				quantidade = (BigDecimal) tabProdutos.getValor( i, 3 );
				custo = (BigDecimal) tabProdutos.getValor( i, 2 );
				
				codInventario = getCodInventario();
				codAlmoarifado = getAlmoxarifado( codprod );
				
				ps = con.prepareStatement( sql.toString() );
				ps.setInt( 1, Aplicativo.iCodEmp );
				ps.setInt( 2, ListaCampos.getMasterFilial( "EQINVPROD" ) );
				ps.setInt( 3, codInventario );
				ps.setInt( 4, Aplicativo.iCodEmp );
				ps.setInt( 5, ListaCampos.getMasterFilial( "EQPRODUTO" ) );
				ps.setInt( 6, codprod ); // código do produto.
				ps.setInt( 7, Aplicativo.iCodEmp );
				ps.setInt( 8, ListaCampos.getMasterFilial( "EQTIPOMOV" ) );
				ps.setInt( 9, tipoMovimentoInventario ); // código do tipo de movimento de inventário.
				ps.setDate( 10, hoje );
				ps.setBigDecimal( 11, quantidade );
				ps.setBigDecimal( 12, custo );
				ps.setInt( 13, Aplicativo.iCodEmp );
				ps.setInt( 14, ListaCampos.getMasterFilial( "EQALMOX" ) );
				ps.setInt( 15, codAlmoarifado );	
				ps.execute();
				
				status.setValue( i+1 );
			}
			
			status.setString( "Concluido !" );
			btInventario.setEnabled( false );
			
			con.setAutoCommit( commit );
			
			if ( ! con.getAutoCommit() ) {
				con.commit();
			}
			
		} catch ( Exception e ) {
			e.printStackTrace();
			Funcoes.mensagemErro( this, "Erro ao realizar inventáros!\n" + e.getMessage(), true, con, e );
			try {
				con.rollback();
				rgOpcao.setVlrInteger( IMPORTAR );
			} catch ( SQLException e1 ) {
				e1.printStackTrace();
			}
		}
	}
	
	private Integer getTipoMovimentoInventario() throws Exception {
		
		Integer tipoMovimentoInventario = null;
		
		StringBuilder sql = new StringBuilder();
		sql.append( "SELECT CODTIPOMOV6 FROM SGPREFERE1 WHERE CODEMP=? AND CODFILIAL=?" );
		
		PreparedStatement ps = con.prepareStatement( sql.toString() );
		ps.setInt( 1, Aplicativo.iCodEmp );
		ps.setInt( 2, ListaCampos.getMasterFilial( "SGPREFERE1" ) );
		
		ResultSet rs = ps.executeQuery();
		
		if ( rs.next() ) {
			tipoMovimentoInventario = rs.getInt( "CODTIPOMOV6" );
		}
		
		rs.close();
		ps.close();
		
		if ( ! con.getAutoCommit() ) {
			con.commit();
		}
		
		return tipoMovimentoInventario;
	}
	
	private Integer getCodInventario() throws Exception {
		
		Integer codInventario = null;
		
		StringBuilder sql = new StringBuilder();
		sql.append( "SELECT MAX(CODINVPROD)+1 FROM EQINVPROD WHERE CODEMP=? AND CODFILIAL=?" );
		
		PreparedStatement ps = con.prepareStatement( sql.toString() );
		ps.setInt( 1, Aplicativo.iCodEmp );
		ps.setInt( 2, ListaCampos.getMasterFilial( "EQINVPROD" ) );
		
		ResultSet rs = ps.executeQuery();
		
		if ( rs.next() ) {
			codInventario = rs.getInt( 1 );
		}
		
		rs.close();
		ps.close();
		
		if ( ! con.getAutoCommit() ) {
			con.commit();
		}
		
		return codInventario;
	}

	public void actionPerformed( ActionEvent evt ) {

		if ( evt.getSource() == btSair ) {
			dispose();
		}
		else if ( evt.getSource() == btDirtorio ) {
			getDiretorio();
		}
		else if ( evt.getSource() == btBuscarProdutos ) {
			carregaProdutosExportacao();
		}
		else if ( evt.getSource() == btExeportar ) {
			ProcessoSec pSec = new ProcessoSec( 500, new Processo() {
				public void run() {
					status.updateUI();
				}
			}, new Processo() {
				public void run() {
					exportar();
				}
			} );
			pSec.iniciar();
		}
		else if ( evt.getSource() == btImportar ) {
			ProcessoSec pSec = new ProcessoSec( 500, new Processo() {
				public void run() {
					status.updateUI();
				}
			}, new Processo() {
				public void run() {
					importar();
				}
			} );
			pSec.iniciar();
		}
		else if ( evt.getSource() == btInventario ) {
			ProcessoSec pSec = new ProcessoSec( 500, new Processo() {
				public void run() {
					status.updateUI();
				}
			}, new Processo() {
				public void run() {
					criarInventario();
				}
			} );
			pSec.iniciar();
		}
	}

	public void valorAlterado( RadioGroupEvent e ) {

		txtDiretorio.setVlrString( "" );
		txtDiretorio.setEnabled( true );
		btDirtorio.setEnabled( true );
		tabProdutos.limpa();
		btBuscarProdutos.setEnabled( false );
		btExeportar.setEnabled( false );
		btImportar.setEnabled( false );
		btInventario.setEnabled( false );

		if ( e.getIndice() == 0 ) {			
			status.setString( "Selecione a local do arquivo e exportação ..." );
			btBuscarProdutos.setVisible( true );
			btExeportar.setVisible( true );
			btImportar.setVisible( false );
			btInventario.setVisible( false );
		}
		else if ( e.getIndice() == 1 ) {			
			status.setString( "Selecione a local do arquivo e importação ..." );
			btBuscarProdutos.setVisible( false );
			btExeportar.setVisible( false );
			btImportar.setVisible( true );
			btInventario.setVisible( true );
		}
	}

	public void setConexao( Connection cn ) {

		super.setConexao( cn );
	}
}
