/**
 * @version 01/08/2008 <BR>
 * @author Setpoint Informática Ltda.
 * @author Reginaldo Garcia Heua <BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.std <BR>
 * Classe:
 * @(#)FMetodoAnalitico.java <BR>
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
 * Tela para cadastro de tipos de clientes.
 * 
 */

package org.freedom.modulos.pcp;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.sql.Connection;

import javax.swing.JScrollPane;

import org.freedom.componentes.JPanelPad;
import org.freedom.componentes.JTextAreaPad;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.Navegador;
import org.freedom.componentes.PainelImagem;
import org.freedom.componentes.Tabela;
import org.freedom.telas.FTabDados;


public class FMetodoAnalitico extends FTabDados {

	private static final long serialVersionUID = 1L;
	
	private JTextFieldPad txtCodMtAnalise = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 5, 0 );
	
	private JTextFieldPad txtDescMtAnalise = new JTextFieldPad( JTextFieldPad.TP_STRING, 80, 0 );
	
	private JTextFieldPad txtTituloMet = new JTextFieldPad( JTextFieldPad.TP_STRING, 100, 0 );
	
	private JTextFieldPad txtCodFoto = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtDescFoto = new JTextFieldPad( JTextFieldPad.TP_STRING, 40, 0 );
		
	private JPanelPad pinGeral = new JPanelPad( 330, 200 );
	
	private JPanelPad pinMaterial = new JPanelPad( new BorderLayout() );
	
	private JPanelPad pinReagentes = new JPanelPad( new BorderLayout() );
	
	private JPanelPad pinProced = new JPanelPad( new BorderLayout() );
	
	private JPanelPad pinCaract = new JPanelPad( new BorderLayout() );
	
	private JPanelPad pinAbas = new JPanelPad( new BorderLayout() );
	
	private JPanelPad pinCampos = new JPanelPad( 200, 100 );
	
	private JPanelPad pnFoto = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );
	
	private JPanelPad pinRodFoto = new JPanelPad( 650, 170 );
	
	private JTextAreaPad txaMaterial = new JTextAreaPad();

	private JTextAreaPad txaReagente = new JTextAreaPad();
	
	private JTextAreaPad txaProced = new JTextAreaPad();
	
	private Navegador navFoto = new Navegador( true );
	
	private ListaCampos lcFoto = new ListaCampos( this );
	
	private Tabela tabFoto = new Tabela();

	private JScrollPane spnFoto = new JScrollPane( tabFoto );
	
	private PainelImagem imFotoProd = new PainelImagem( 65000 );
	
	
	public FMetodoAnalitico(){
		
		setTitulo( "Métodos Analíticos" );
		setAtribos( 50, 50, 550, 500 );
		
		lcFoto.setMaster( lcCampos );
		lcCampos.adicDetalhe( lcFoto );
		lcFoto.setTabela( tabFoto );
		
		montaTela();
		
	}
	
	private void montaTela(){

		pnCliente.removeAll();		
		setPainel( pinCampos );
		
		adicCampo( txtCodMtAnalise, 7, 20, 70, 20, "CodMtAnalise", "Cód.Método", ListaCampos.DB_PK, true );
		adicCampo( txtDescMtAnalise, 80, 20, 260, 20, "DescMtAnalise", "Descrição do método analítico", ListaCampos.DB_SI, true );		
		adicCampo( txtTituloMet, 7, 60, 335, 20, "TituloAnalise", "Titulo", ListaCampos.DB_SI, true );		
	
		
		pinAbas.setPreferredSize( new Dimension( 200, 300 ) );
		pnCliente.add( pinCampos, BorderLayout.NORTH );
		
		/****************
		 *   Material   *
		 ****************/		

		adicTab( "Material", pinMaterial );
		adicDBLiv( txaMaterial, "MatAnalise", "Material", false );
		pinMaterial.add( txaMaterial );
		
		/****************
		 *   Reagentes  *
		 ****************/
		
	    adicTab( "Reagentes", pinReagentes );
	    adicDBLiv( txaReagente, "ReagAnalise", "Reagentes", false );
	    pinReagentes.add( txaReagente );
		
		/*******************
		 *  Procedimentos  *
		 *******************/
		
		adicTab( "Procedimentos", pinProced );
		adicDBLiv( txaProced, "ProcAnalise", "Procedimento", false );
		pinProced.add( txaProced );
				
		setListaCampos( true, "METODOANALISE", "PP" );
		
		/********************
		 * Caracteristicas  *
		 ********************/
		
		setPainel( pinRodFoto, pnFoto );
		adicTab( "Cracteristica", pnFoto );
		setListaCampos( lcFoto );
		setNavegador( navFoto );
		pnFoto.add( pinRodFoto, BorderLayout.SOUTH );
		pnFoto.add( spnFoto, BorderLayout.CENTER );
		pinRodFoto.adic( navFoto, 0, 140, 270, 25 );
		
		adicCampo( txtCodFoto, 7, 20, 70, 20, "CodFotoMTan", "Nº foto", ListaCampos.DB_PK, true );
		adicCampo( txtDescFoto, 80, 20, 250, 20, "DescFotoMtan", "Descrição da foto", ListaCampos.DB_SI, true );
		adicDB( imFotoProd, 350, 20, 150, 140, "FotoMTan", "Foto", true );		
		
		setListaCampos( true, "FOTOMTAN", "PP" );
		lcFoto.setQueryInsert( false );
		lcFoto.setQueryCommit( false );
		lcFoto.montaTab();
		
		tabFoto.setColunaInvisivel( 2 );
		tabFoto.setTamColuna( 350, 1 );
		
		}
	
	public void setConexao( Connection con ){
		
		super.setConexao( con );
		lcFoto.setConexao( con );
		
	}
}
