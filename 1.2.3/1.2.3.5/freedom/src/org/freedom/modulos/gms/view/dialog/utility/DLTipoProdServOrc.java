/**
 * @version 21/08/2010 <BR>
 * @author Setpoint Informática Ltda./Anderson Sanchez <BR>
 * 
 *         Projeto: Freedom <BR>
 * 
 *         Pacote: org.freedom.modulos.std <BR>
 *         Classe:
 * @(#)DLTipoOrcamento.java <BR>
 * 
 *                 Este arquivo é parte do sistema Freedom-ERP, o Freedom-ERP é um software livre; você pode redistribui-lo e/ou <BR>
 *                 modifica-lo dentro dos termos da Licença Pública Geral GNU como publicada pela Fundação do Software Livre (FSF); <BR>
 *                 na versão 2 da Licença, ou (na sua opnião) qualquer versão. <BR>
 *                 Este programa é distribuido na esperança que possa ser util, mas SEM NENHUMA GARANTIA; <BR>
 *                 sem uma garantia implicita de ADEQUAÇÂO a qualquer MERCADO ou APLICAÇÃO EM PARTICULAR. <BR>
 *                 Veja a Licença Pública Geral GNU para maiores detalhes. <BR>
 *                 Você deve ter recebido uma cópia da Licença Pública Geral GNU junto com este programa, se não, <BR>
 *                 de acordo com os termos da LPG-PC <BR>
 * <BR>
 * 
 * Dialog para seleção dos tipos de produtos deve ser gerados em orçamento de ordem de seviço.
 */

package org.freedom.modulos.gms.view.dialog.utility;

import java.awt.Component;
import java.awt.event.ActionEvent;
import org.freedom.library.swing.component.JCheckBoxPad;
import org.freedom.library.swing.component.JLabelPad;
import org.freedom.library.swing.dialog.FFDialogo;
import org.freedom.library.swing.frame.Aplicativo;

public class DLTipoProdServOrc extends FFDialogo {

	private static final long serialVersionUID = 1L;

	public static final short COMPONENTES = 0;
	
	public static final short SERVICOS = 1;
	
	public static final short NOVOS = 2;
	
	private JCheckBoxPad cbComponentes = new JCheckBoxPad( "Componentes?", "S", "N" );
	
	private JCheckBoxPad cbServicos = new JCheckBoxPad( "Serviços?", "S", "N" );
	
	private JCheckBoxPad cbNovos = new JCheckBoxPad( "Produtos novos?", "S", "N" );
	
	private JLabelPad lbTitulo = new JLabelPad( "Selecione os tipos de produto/serviço que devem ser orçados." );

	public DLTipoProdServOrc( Component orig ) {

		super( orig );

		setConexao( Aplicativo.getInstace().con );
	
		setTitulo( "Tipo de produto/serviço" );
		setAtribos( 380, 270 );

		adic( lbTitulo, 7, 0, 353, 60 );
		
		adic( cbComponentes, 7, 60, 200, 20 );
		adic( cbServicos, 7, 100, 200, 20 );
		adic( cbNovos, 7, 140, 200, 20 );

		cbComponentes.setVlrString( "S" );
		cbServicos.setVlrString( "S" );
		
	}

	public String getComponentes() {
		return cbComponentes.getVlrString();
	}
	
	public String getServicos() {
		return cbServicos.getVlrString();
	}

	public String getNovos() {
		return cbNovos.getVlrString();
	}

	public void actionPerformed( ActionEvent evt ) {

		if ( evt.getSource() == btOK ) {
			super.actionPerformed( evt );
		}
		else if ( evt.getSource() == btCancel ) {
			super.actionPerformed( evt );
		}
	}
}
