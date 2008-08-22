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

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JLabelPad;
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
	
	private JTextFieldPad txtFonteMet = new JTextFieldPad( JTextFieldPad.TP_STRING, 100, 0 );
	
	private JTextFieldPad txtCodFoto = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 10, 0 );
	
	private JTextFieldPad txtDtAlt = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );
	
	private JTextFieldPad txtHAlt = new JTextFieldPad( JTextFieldPad.TP_TIME, 10, 0 );
	
	private JTextFieldPad txtIdAlt = new JTextFieldPad( JTextFieldPad.TP_STRING, 128, 0 );

	private JTextAreaPad txtDescFoto = new JTextAreaPad();
		
	private JPanelPad pinGeral = new JPanelPad( 330, 260 );
	
	private JPanelPad pinMaterial = new JPanelPad( new BorderLayout() );
	
	private JPanelPad pinReagentes = new JPanelPad( new BorderLayout() );
	
	private JPanelPad pinProced = new JPanelPad( new BorderLayout() );
	
	private JPanelPad pinCaract = new JPanelPad( new BorderLayout() );
	
	private JPanelPad pinAbas = new JPanelPad( new BorderLayout() );
	
	private JPanelPad pinCampos = new JPanelPad( 200, 140 );
	
	private JPanelPad pnFoto = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );
	
	private JPanelPad pinRodFoto = new JPanelPad( 650, 170 );
	
	private JTextAreaPad txaMaterial = new JTextAreaPad();
	
	private JScrollPane spnMaterial = new JScrollPane( txaMaterial );

	private JTextAreaPad txaReagente = new JTextAreaPad();
	
	private JScrollPane spnReagente = new JScrollPane( txaReagente );
	
	private JTextAreaPad txaProced = new JTextAreaPad();
	
	private JScrollPane spnProced = new JScrollPane( txaProced );
	
	private Navegador navFoto = new Navegador( true );
	
	private ListaCampos lcFoto = new ListaCampos( this );
	
	private Tabela tabFoto = new Tabela();

	private JScrollPane spnFoto = new JScrollPane( tabFoto );
	
	private PainelImagem imFotoProd = new PainelImagem( 65000 );
	
	
	public FMetodoAnalitico(){
		
		setTitulo( "Métodos Analíticos" );
		setAtribos( 50, 50, 700, 600 );
		
		lcFoto.setMaster( lcCampos );
		lcCampos.adicDetalhe( lcFoto );
		lcFoto.setTabela( tabFoto );
		
		montaTela();
		
	}
	
	private void montaTela(){

		pnCliente.removeAll();		
		setPainel( pinCampos );
		
		adicCampo( txtCodMtAnalise, 7, 20, 70, 20, "CodMtAnalise", "Cód.Método", ListaCampos.DB_PK, true );
		adicCampo( txtDescMtAnalise, 80, 20, 595, 20, "DescMtAnalise", "Descrição do método analítico", ListaCampos.DB_SI, true );		
		adicCampo( txtTituloMet, 7, 60, 400, 20, "TituloAnalise", "Titulo", ListaCampos.DB_SI, true );		
		adicCampo( txtFonteMet, 7, 100, 400, 20, "FonteMtAnalise", "Fonte", ListaCampos.DB_SI, true );	
		adic( new JLabelPad("Data"), 420, 75, 80, 20 );
		adicCampo(  txtDtAlt, 420, 95, 80, 20, "DtAlt", "Data", ListaCampos.DB_SI, false );
		adic( new JLabelPad("Hora"), 507, 75, 80, 20 );
		adicCampo( txtHAlt, 507, 95, 80, 20, "HAlt", "Hora", ListaCampos.DB_SI, false );
		adic( new JLabelPad("Usuário"), 590, 75, 80, 20 );
		adicCampo( txtIdAlt, 590, 95, 80, 20, "IdUsuAlt", "Usuário", ListaCampos.DB_SI, false );
		
		JLabelPad lbLinha = new JLabelPad();
		lbLinha.setBorder(BorderFactory.createEtchedBorder());
		JLabelPad lbPeriodo = new JLabelPad( "Última revisão:" , SwingConstants.CENTER );
		lbPeriodo.setOpaque( true );
		
		adic( lbPeriodo,415, 55, 100, 20 );
		adic( lbLinha,413, 64, 265, 60 );
		
		txtDtAlt.setSoLeitura( true );
		txtHAlt.setSoLeitura( true );
		txtIdAlt.setSoLeitura( true );
		
		
		pinAbas.setPreferredSize( new Dimension( 300, 300 ) );
		pnCliente.add( pinCampos, BorderLayout.NORTH );
		
		/****************
		 *   Material   *
		 ****************/		

		adicTab( "Material", pinMaterial );
		adicDBLiv( txaMaterial, "MatAnalise", "Material", false );
		pinMaterial.add( spnMaterial );
		
		/****************
		 *   Reagentes  *
		 ****************/
		
	    adicTab( "Reagentes", pinReagentes );
	    adicDBLiv( txaReagente, "ReagAnalise", "Reagentes", false );
	    pinReagentes.add( spnReagente );
		
		/*******************
		 *  Procedimentos  *
		 *******************/
		
		adicTab( "Procedimentos", pinProced );
		adicDBLiv( txaProced, "ProcAnalise", "Procedimento", false );
		pinProced.add( spnProced );
				
		setListaCampos( true, "METODOANALISE", "PP" );
		
		/********************
		 * Caracteristicas  *
		 ********************/
		
		setPainel( pinRodFoto, pnFoto );
		adicTab( "Caracteristica", pnFoto );
		setListaCampos( lcFoto );
		setNavegador( navFoto );
		pnFoto.add( pinRodFoto, BorderLayout.SOUTH );
		pnFoto.add( spnFoto, BorderLayout.CENTER );
		pinRodFoto.adic( navFoto, 0, 140, 270, 25 );
		
		adicCampo( txtCodFoto, 7, 20, 70, 20, "CodFotoMTan", "Nº foto", ListaCampos.DB_PK, true );
		adicDB( txtDescFoto, 7, 60, 480, 70, "DescFotoMtan", "Descrição da foto", true );
		adicDB( imFotoProd, 500, 20, 150, 140, "FotoMTan", "Foto", true );	
		tabFoto.setTamColuna( 500, 1 );
		
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
