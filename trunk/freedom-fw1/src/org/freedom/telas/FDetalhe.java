/**
 * @version 05/06/2000 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 * 
 * Projeto: Freedom <BR>
 * Pacote: org.freedom.telas <BR>
 * Classe:
 * @(#)FDetalhe.java <BR>
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
 * Comentários da classe.....
 */

package org.freedom.telas;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JScrollPane;

import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.JPanelPad;
import org.freedom.library.ListaCampos;
import org.freedom.library.Navegador;
import org.freedom.library.Tabela;

public class FDetalhe extends FDados {

	private static final long serialVersionUID = 1L;

	public static boolean comScroll = false;

	private GridLayout glRod = new GridLayout( 1, 1 );

	private GridLayout glCab = new GridLayout( 1, 1 );

	private GridLayout glNavCab = new GridLayout( 1, 1 );

	private BorderLayout blMaster = new BorderLayout();

	private BorderLayout blCab = new BorderLayout();

	private BorderLayout blNavCab = new BorderLayout();

	public JPanelPad pnMaster = new JPanelPad( JPanelPad.TP_JPANEL, blMaster );

	public JPanelPad pnCab = new JPanelPad( JPanelPad.TP_JPANEL, blCab );

	private JPanelPad pnBordCab = new JPanelPad( JPanelPad.TP_JPANEL, glCab );

	public JPanelPad pnDet = new JPanelPad( JPanelPad.TP_JPANEL, new GridLayout( 1, 1 ) );

	private JPanelPad pnBordDet = new JPanelPad( JPanelPad.TP_JPANEL, glRod );

	public JPanelPad pnNavCab = new JPanelPad( JPanelPad.TP_JPANEL, blNavCab );

	private JPanelPad pnBordNavCab = new JPanelPad( JPanelPad.TP_JPANEL, glNavCab );

	public JPanelPad pnCliCab = new JPanelPad( JPanelPad.TP_JPANEL, new GridLayout( 1, 1 ) );

	public Navegador navRod = new Navegador( true );

	public ListaCampos lcDet = new ListaCampos( this );

	public Tabela tab = new Tabela();

	public JScrollPane spTab = new JScrollPane( tab );

	boolean bSetAreaCab = true;

	boolean bSetAreaDet = true;
	
	public FDetalhe() {
		this( true );
	}

	public FDetalhe( boolean scroll ) {

		super( scroll );
		pnRodape.remove( nav );
		pnRodape.add( navRod, BorderLayout.WEST );

		pnCliente.remove( pinDados );
		pnCliente.add( pnMaster, BorderLayout.CENTER );

		// Reconstruiu o FDados
		// ********************************************
		// Agora irá construir o FDetalhe
		nav.setName( "Mestre" );
		navRod.setName( "Detalhe" );
		pnNavCab.add( nav, BorderLayout.WEST );

		pnBordNavCab.setPreferredSize( new Dimension( 500, 30 ) );
		pnBordNavCab.setBorder( br );
		pnBordNavCab.add( pnNavCab );

		pnCab.add( pnCliCab, BorderLayout.CENTER );
		pnCab.add( pnBordNavCab, BorderLayout.SOUTH );

		pnBordCab.setPreferredSize( new Dimension( 500, 100 ) );
		pnBordCab.setBorder( br );
		pnBordCab.add( pnCab );

		pnBordDet.setPreferredSize( new Dimension( 500, 50 ) );
		pnBordDet.setBorder( br );
		pnBordDet.add( pnDet );

		pnMaster.add( pnBordCab, BorderLayout.NORTH );
		pnMaster.add( pnBordDet, BorderLayout.SOUTH );
		pnMaster.add( spTab, BorderLayout.CENTER );
		lcDet.setMaster( lcCampos );
		lcCampos.adicDetalhe( lcDet );
		lcDet.setTabela( tab );
	}

	public void montaTab() {

		lcDet.montaTab();
	}

	public void setAltDet( int Alt ) {

		pnBordDet.setPreferredSize( new Dimension( 500, Alt ) );
	}

	public void setAltCab( int Alt ) {

		pnBordCab.setPreferredSize( new Dimension( 500, Alt ) );
	}

	public void setConexao( DbConnection cn ) {

		super.setConexao( cn );
		lcDet.setConexao( cn );
	}
}
