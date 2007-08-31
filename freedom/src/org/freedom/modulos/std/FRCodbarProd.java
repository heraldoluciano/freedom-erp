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
import java.sql.Connection;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

import org.freedom.acao.CarregaEvent;
import org.freedom.acao.CarregaListener;
import org.freedom.bmps.Icone;
import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JPanelPad;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.Tabela;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.FRelatorio;


public class FRCodbarProd extends FRelatorio implements ActionListener, CarregaListener, KeyListener {

	private static final long serialVersionUID = 1L;
	
	private JTextFieldPad txtCodProd = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 10, 0 );
	
	private JTextFieldFK txtDescProd = new JTextFieldFK ( JTextFieldPad.TP_STRING, 50, 0 );
	
	private JTextFieldPad txtRefProd = new JTextFieldPad( JTextFieldPad.TP_STRING, 50, 0 );
	
	private JTextFieldPad txtCodBarProd = new JTextFieldPad( JTextFieldPad.TP_STRING, 50, 0 );
	
	private JTextFieldPad txtQtdPod = new JTextFieldPad( JTextFieldPad.TP_STRING, 2, 0 );
	
	private JButton btExecuta = new JButton( Icone.novo( "btExecuta.gif" ) );
	
	private JButton btExcluir = new JButton( Icone.novo( "btCancelar.gif" ) );
	
	private JButton btExcluirTudo = new JButton( Icone.novo( "btNada.gif" ) );
	
	private Tabela tabGrid = new Tabela();
	
	private JScrollPane spnGrid = new JScrollPane( tabGrid );
	
	private ListaCampos lcProduto = new ListaCampos(this);
	 
	private JPanelPad pnCampos = new JPanelPad(600,65);
	
	private JPanelPad pnBotoesGrid = new JPanelPad(35,200);
	  
	private JPanelPad pnGrid = new JPanelPad(600, 200 );
	  
	private JComboBox cbSel = null;
	
	private enum EProduto {CODPROD, DESCPROD, QTDPROD };
	
	public FRCodbarProd(){
		
		super( true );
		setTitulo( "Etiquetas de código de barras" );
		setAtribos( 80, 30, 490, 380 );
		
		montaTela();
		montaListaCampos();
	}

	public void montaTela(){
		
		Container c = getContentPane();
		
	    c.add( pnCampos,BorderLayout.NORTH );
	    c.add( pnGrid, BorderLayout.CENTER );
	    c.add( pnBotoesGrid, BorderLayout.EAST ); 
	    c.add( spnGrid );
	
	    pnCampos.adic( new JLabelPad("Cód. Produto"), 07, 05, 100, 20 );
	    pnCampos.adic( txtCodProd, 07, 23, 80, 20 );
	    pnCampos.adic( new JLabelPad("Descrição do produto"), 93, 05, 200, 20 );
	    pnCampos.adic( txtDescProd, 93, 23, 280, 20 );
	    pnCampos.adic( new JLabelPad("qtd."), 375, 05, 30, 20 );
	    pnCampos.adic( txtQtdPod, 375, 23, 30, 20 );
	    pnCampos.adic( btExecuta, 410, 15, 30, 30 );
	    pnBotoesGrid.adic( btExcluir, 0, 0, 30, 30 );
	    pnBotoesGrid.adic( btExcluirTudo, 0, 30, 30, 30 );
 
		tabGrid.adicColuna( "Cód. prod" );
		tabGrid.adicColuna( "Descrição do produto" );
		tabGrid.adicColuna( "Qtd" );
		
		tabGrid.setTamColuna( 60, EProduto.CODPROD.ordinal()  );
		tabGrid.setTamColuna( 320, EProduto.DESCPROD.ordinal() );
		tabGrid.setTamColuna( 60, EProduto.QTDPROD.ordinal() );
	
		lcProduto.addCarregaListener( this );
		btExecuta.addActionListener( this );
		btExcluir.addActionListener( this );
		btExcluirTudo.addActionListener( this );
		txtQtdPod.addKeyListener( this );
		
		btExecuta.setToolTipText( "Executar" );
		btExcluir.setToolTipText( "Ecluir" );
		btExcluirTudo.setToolTipText( "Excluir tudo" );
		
	}
	public void montaListaCampos(){
		
		/***********
		 * Produto *
		 ***********/
		
		lcProduto.add( new GuardaCampo( txtCodProd, "CodProd", "Cód.produto", ListaCampos.DB_PK, true));
		lcProduto.add( new GuardaCampo( txtDescProd, "DescProd", "Descrição do produto", ListaCampos.DB_SI, false));
		lcProduto.add( new GuardaCampo( txtRefProd, "RefProd", "Ref. produto", ListaCampos.DB_SI, false));
		lcProduto.add( new GuardaCampo( txtCodBarProd, "CodBarProd", "Cód. Barras", ListaCampos.DB_SI, false));
		txtCodProd.setTabelaExterna(lcProduto);
		txtCodProd.setNomeCampo("CodProd");
		txtCodProd.setFK(true);
		lcProduto.setReadOnly(true);
		lcProduto.montaSql(false, "PRODUTO", "EQ");
		
	}
	
	public void adicLinha(){
		
		boolean verific = true;
		int qtd = txtQtdPod.getVlrInteger().intValue();
		int codprod = txtCodProd.getVlrInteger().intValue();
		int pos = -1;
		
		if (codprod==0) {
			Funcoes.mensagemInforma( this, "Produto não encontrado!" );
			txtCodProd.requestFocus();
			return;
		}
		for (int i=0; i<tabGrid.getNumLinhas(); i++) {
			if (codprod ==  ( (Integer) tabGrid.getValor( i, EProduto.CODPROD.ordinal() ) ).intValue() ) {
				pos = i;
				qtd += (Integer) tabGrid.getValor( i, EProduto.QTDPROD.ordinal() );
				break;
			}
		}
		
		if (pos == -1) {
			tabGrid.adicLinha();
			pos = tabGrid.getNumLinhas()-1;
		}
		tabGrid.setValor( codprod, pos,  EProduto.CODPROD.ordinal() );
		tabGrid.setValor( txtDescProd.getVlrString(), pos, EProduto.DESCPROD.ordinal() );
		tabGrid.setValor( qtd, pos, EProduto.QTDPROD.ordinal() );
		
	}
	
	public void excluiLinha(){
		
		if( tabGrid.getLinhaSel() != -1 ){
			
			tabGrid.delLinha( tabGrid.getLinhaSel() );
			
		}else{
			
			Funcoes.mensagemInforma( this, "Selecione uma linha na lista!" );
		}
	}
	
	public void excluiTudo(){
		
		tabGrid.limpa();
	}
	
	public void imprimir( boolean b ) {}
	
	public void setConexao(Connection cn) {
	  	
		super.setConexao(cn);
		lcProduto.setConexao( cn );
	
	}

	public void afterCarrega( CarregaEvent cevt ) {

		if( cevt.getListaCampos() == lcProduto ){
			txtQtdPod.setVlrString( "1" );
		}
	}

	@ Override
	public void keyPressed( KeyEvent kevt ) {

		super.keyPressed(kevt);
		
		if ( kevt.getSource() == txtQtdPod ){
			
			if( kevt.getKeyCode() == KeyEvent.VK_ENTER ){
				btExecuta.doClick();
			}
		}
	}

	public void actionPerformed( ActionEvent evt ) {

		super.actionPerformed(evt);
		
		if( evt.getSource() == btExecuta ){
			adicLinha();
			txtCodProd.requestFocus();
		}
		if( evt.getSource() == btExcluir ){
			excluiLinha();
		}
		if( evt.getSource() == btExcluirTudo ){
			excluiTudo();
			txtCodProd.requestFocus();
		}
	}

	public void beforeCarrega( CarregaEvent cevt ) {}
}
