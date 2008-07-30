/**
 * @version 29/07/2008 <BR>
 * @author Setpoint Informática Ltda.
 * @author Reginaldo Garcia Heua 
 * <BR> * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.std <BR>
 * Classe:
 * @(#)DLMultiComiss.java <BR>
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
 */
package org.freedom.modulos.std;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JScrollPane;

import org.freedom.acao.PostEvent;
import org.freedom.acao.PostListener;
import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JPanelPad;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.Navegador;
import org.freedom.componentes.Tabela;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FFDialogo;

public class DLMultiComiss extends FFDialogo implements MouseListener, PostListener {

	private static final long serialVersionUID = 1L;
	
	private JPanelPad pinCab = new JPanelPad( 400, 50 );
	
	private JPanelPad pnComiss = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );
	
	private Tabela tabComiss = new Tabela();

	private JScrollPane spnComiss = new JScrollPane( tabComiss );
	
	private ListaCampos lcVendedor = new ListaCampos( this,"VD" );
	
	private ListaCampos lcVendaComis = new ListaCampos( this,"" );
	
	private JTextFieldPad txtCodVend = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	
	private JTextFieldPad txtCodVenda = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	
	private JTextFieldPad txtTipoVenda = new JTextFieldPad( JTextFieldPad.TP_STRING, 1, 0 );
	
	private JTextFieldPad txtSeqVenda = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 5, 0 );
	
	private JTextFieldFK txtDescVend = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );
	
	private JTextFieldPad txtCodClComis = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	
	private JTextFieldPad txtPercComisVenda = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 7, Aplicativo.casasDecFin );
	
	private JPanelPad pnBot = new JPanelPad( new FlowLayout( FlowLayout.LEFT, 5, 5 ) );
	
	private JPanelPad pnBotSair = new JPanelPad( new FlowLayout( FlowLayout.LEFT, 5, 5 ) );
	
	private Navegador nvRodape = new Navegador( false );
	
	private Integer codvenda = null;
	
	
	private enum eComiss {
	
		SEQ, DESCTPCOMIS, CODVEND, DESCVEND, PERCCOMISS, CODVENDA, TIPOVENDA  ;
	};

	public DLMultiComiss( Connection con, int codvenda ){
	
		setAtribos( 600, 350 );
		setTitulo( "Multi-Comissionados" );
		
		this.codvenda = codvenda;
		
		montaListaCampos();
		setConexao( con );
		montaTela();
		montaTab();		
	
		
		tabComiss.addMouseListener( this );
	}
	
	private void montaTela(){
		
		pinCab.setPreferredSize( new Dimension( 400, 50 ) );
		spnComiss.setPreferredSize( new Dimension( 400, 180 ) );
		pnComiss.add( spnComiss, BorderLayout.NORTH );
		pnComiss.add( pinCab, BorderLayout.CENTER );		
		c.add( pnComiss, BorderLayout.CENTER );
		
		pnRodape.removeAll();
		pnRodape.add( pnBot, BorderLayout.WEST );
		
		tabComiss.adicColuna( "seq." );
		tabComiss.adicColuna( "Tipo de comissionado" );
		tabComiss.adicColuna( "Cod.Vend" );
		tabComiss.adicColuna( "Vendedor" );
		tabComiss.adicColuna( "% Comis." );
		tabComiss.adicColuna( "Cód.Venda" );
		tabComiss.adicColuna( "Tipo Venda" );
		
		
//		tabComiss.setTamColuna( 50, eComiss.SEQ.ordinal() );
		tabComiss.setTamColuna( 200, eComiss.DESCTPCOMIS.ordinal() );
		tabComiss.setTamColuna( 200, eComiss.DESCVEND.ordinal() );
		tabComiss.setTamColuna( 70, eComiss.PERCCOMISS.ordinal() );
	
		tabComiss.setColunaInvisivel( eComiss.SEQ.ordinal() );
		tabComiss.setColunaInvisivel( eComiss.CODVENDA.ordinal() );
		tabComiss.setColunaInvisivel( eComiss.TIPOVENDA.ordinal() );
		
		setPainel( pinCab );
		
		adic( new JLabelPad("Cód.Vend"), 7, 5, 70, 20 );
		adic( txtCodVend, 7, 25, 70, 20 );
		adic( new JLabelPad("Nome do vendedor"), 80, 5, 250, 20 );
		adic( txtDescVend, 80, 25, 250, 20 );
		adic( new JLabelPad("% comis."), 333, 5, 70, 20 );
		adic( txtPercComisVenda, 333, 25, 70, 20 );
		
		pnBot.tiraBorda();
		
		pnBotSair.add( btOK );
		pnRodape.add( pnBotSair, BorderLayout.EAST );
		
		pnBot.add( nvRodape );
		nvRodape.setListaCampos( lcVendaComis );
		
		
		
	}
	
	private void montaListaCampos(){
		
		lcVendedor.add( new GuardaCampo( txtCodVend, "CodVend", "Cód.Vend.", ListaCampos.DB_PK, true ) );
		lcVendedor.add( new GuardaCampo( txtDescVend, "NomeVend", "Nome do comissionado", ListaCampos.DB_SI, false ) );
		lcVendedor.setWhereAdic( "ATIVOCOMIS='S'" );
		lcVendedor.montaSql( false, "VENDEDOR", "VD" );
		lcVendedor.setQueryCommit( false );
		lcVendedor.setReadOnly( true );
		txtCodVend.setTabelaExterna( lcVendedor );
			
		lcVendaComis.add( new GuardaCampo( txtCodVenda, "CodVenda", "Cód.Venda", ListaCampos.DB_PK, false ) );
		lcVendaComis.add( new GuardaCampo( txtTipoVenda, "TipoVenda", "Tipo", ListaCampos.DB_PK, false ) );
		lcVendaComis.add( new GuardaCampo( txtSeqVenda, "SeqVc", "seq.", ListaCampos.DB_PK, false ) );
		lcVendaComis.add( new GuardaCampo( txtCodVend, "CodVend", "Cód.Vend.", ListaCampos.DB_FK, false ) );
		lcVendaComis.add( new GuardaCampo( txtPercComisVenda, "Percvc", "%.Comiss.", ListaCampos.DB_SI, true ) );
		lcVendaComis.montaSql( false, "VENDACOMIS", "VD" );
		lcVendaComis.setQueryCommit( false );
		lcVendaComis.setReadOnly( false );
		
		lcVendaComis.setNavegador( nvRodape );
		
		lcVendaComis.addPostListener( this );
		
	}
	
	private void setVlrCampos(){

		try {
			
			if( !"".equals( tabComiss.getValor( tabComiss.getLinhaSel(), eComiss.CODVEND.ordinal() ).toString())){			
				txtCodVend.setVlrInteger( new Integer( tabComiss.getValor( tabComiss.getLinhaSel(), eComiss.CODVEND.ordinal() ).toString()));
			}	
		
			txtCodVenda.setVlrInteger( new Integer( tabComiss.getValor( tabComiss.getLinhaSel(), eComiss.CODVENDA.ordinal()).toString()));
			txtTipoVenda.setVlrString( tabComiss.getValor( tabComiss.getLinhaSel(), eComiss.TIPOVENDA.ordinal() ).toString());
			txtSeqVenda.setVlrInteger( new Integer( tabComiss.getValor( tabComiss.getLinhaSel(), eComiss.SEQ.ordinal() ).toString()) );
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void montaTab(){
		
		StringBuffer sql = new StringBuffer();
		ResultSet rs = null;
		PreparedStatement ps = null;
		
		try {

			sql.append( "SELECT VC.SEQVC, VC.CODVEND, TV.DESCTIPOVEND, VC.CODVEND, VE.NOMEVEND, VC.PERCVC, VC.CODVENDA, VC.TIPOVENDA " );
			sql.append( "FROM VDITREGRACOMIS RC, VDTIPOVEND TV, VDVENDACOMIS VC " );
			sql.append( "LEFT OUTER JOIN VDVENDEDOR VE ON " );
			sql.append( "VE.CODEMP=VC.CODEMPVD AND VE.CODFILIAL=VC.CODFILIALVD AND VC.CODVEND=VE.CODVEND " );
			sql.append( "WHERE " );
			sql.append( "RC.CODEMP=VC.CODEMPRC AND RC.CODFILIAL=VC.CODFILIALRC AND RC.CODREGRCOMIS=VC.CODREGRCOMIS " );
			sql.append( "AND RC.SEQITRC=VC.SEQITRC " );
			sql.append( "AND TV.CODEMP=RC.CODEMPTV AND TV.CODFILIAL=RC.CODFILIALTV AND TV.CODTIPOVEND=RC.CODTIPOVEND " );
			sql.append( "AND VC.CODEMP=? AND VC.CODFILIAL=? AND VC.TIPOVENDA='V' AND VC.CODVENDA=?" );
			
			ps = con.prepareStatement( sql.toString() );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "VDREGRACOMIS" ) );
			ps.setInt( 3, codvenda );
			
			rs = ps.executeQuery();
			
			int i = 0;

			tabComiss.limpa();
			
			while( rs.next() ){
				
				tabComiss.adicLinha();
				
				tabComiss.setValor( rs.getString( "SEQVC" ) != null ?  rs.getString( "SEQVC" ) : "", i, eComiss.SEQ.ordinal() );
				tabComiss.setValor( rs.getString( "DESCTIPOVEND" ) != null ? rs.getString( "DESCTIPOVEND" ) : "" , i, eComiss.DESCTPCOMIS.ordinal() );
				tabComiss.setValor( rs.getString( "CODVEND" ) != null ? rs.getString( "CODVEND" ) : ""  , i, eComiss.CODVEND.ordinal() );
				tabComiss.setValor( rs.getString( "NOMEVEND" ) != null ? rs.getString( "NOMEVEND" ) : "", i, eComiss.DESCVEND.ordinal() );
				tabComiss.setValor( rs.getString( "PERCVC" ) != null ? rs.getString( "PERCVC" ) : "", i, eComiss.PERCCOMISS.ordinal() );
				tabComiss.setValor( rs.getString( "CODVENDA" ) != null ? rs.getString( "CODVENDA" ) : ""  , i, eComiss.CODVENDA.ordinal() );
				tabComiss.setValor( rs.getString( "TIPOVENDA" ) != null ? rs.getString( "TIPOVENDA" ) : ""  , i, eComiss.TIPOVENDA.ordinal() );
				
				i++;
			}
			
			rs.close();
			ps.close();

			if ( !con.getAutoCommit() ) {
				con.commit();
			}
		} 
		catch ( SQLException e ) {
			
			e.printStackTrace();
			Funcoes.mensagemErro( this, "Erro ao montar tabela!", true, con, e );
			
		}
	}
	public void setConexao( Connection con ){		
		
		super.setConexao( con );
		lcVendedor.setConexao( con );
		lcVendaComis.setConexao( con );
		
	}

	public void mouseClicked( MouseEvent mevt ) {

		if ( mevt.getClickCount() == 2 ) {
			if ( mevt.getSource() == tabComiss && tabComiss.getLinhaSel() >= 0 ) {	
				setVlrCampos();
				lcVendaComis.carregaDados();
				txtCodVend.requestFocus();
			}
		}
	}

	public void mouseEntered( MouseEvent e ) {}

	public void mouseExited( MouseEvent e ) {}

	public void mousePressed( MouseEvent e ) {}

	public void mouseReleased( MouseEvent e ) {}

	public void afterPost( PostEvent pevt ) {
		if ( pevt.getListaCampos() == lcVendaComis ) {
			montaTab();
		}
	}

	public void beforePost( PostEvent pevt ) {

		// TODO Auto-generated method stub
		
	}
}
