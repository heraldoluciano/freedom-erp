/**
 * @version 29/08/2011 <BR>
 * @author Setpoint Informática Ltda./Bruno Nascimento <BR>
 * 
 *         Projeto: Freedom <BR>
 * 
 *         Pacote: org.freedom.modulos.std <BR>
 *         Classe: @(#)DLEditQtd.java <BR>
 * 
 *         Este arquivo é parte do sistema Freedom-ERP, o Freedom-ERP é um software livre; você pode redistribui-lo e/ou <BR>
 *         modifica-lo dentro dos termos da Licença Pública Geral GNU como publicada pela Fundação do Software Livre (FSF); <BR>
 *         na versão 2 da Licença, ou (na sua opnião) qualquer versão. <BR>
 *         Este programa é distribuido na esperança que possa ser util, mas SEM NENHUMA GARANTIA; <BR>
 *         sem uma garantia implicita de ADEQUAÇÂO a qualquer MERCADO ou APLICAÇÃO EM PARTICULAR. <BR>
 *         Veja a Licença Pública Geral GNU para maiores detalhes. <BR>
 *         Você deve ter recebido uma cópia da Licença Pública Geral GNU junto com este programa, se não, <BR>
 *         escreva para a Fundação do Software Livre(FSF) Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA <BR>
 * <BR>
 * 
 *         Classe que permite a edição da quantidade no Orçamento.
 */

package org.freedom.modulos.std.view.dialog.utility;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.component.JLabelPad;
import org.freedom.library.swing.component.JPanelPad;
import org.freedom.library.swing.component.JTextFieldFK;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.dialog.FFDialogo;

public class DLEditQtd extends FFDialogo {
	
	private static final long serialVersionUID = 1L;
	
	private JTextFieldPad txtItem = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	
	private JTextFieldFK txtCodProd = new JTextFieldFK( JTextFieldPad.TP_INTEGER, 8, 0 );
	
	private JTextFieldFK txtDescProd = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );
	
	private JTextFieldFK  txtQtd = new JTextFieldFK( JTextFieldPad.TP_DECIMAL, 15, 2 );
	
	private JTextFieldFK txtQtdAFat = new JTextFieldFK( JTextFieldPad.TP_DECIMAL, 15, 2 );
	
	private JTextFieldPad txtQtdFat = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 15, 2 );

	private JLabelPad lbItem= new JLabelPad( "Item" );
	
	private JLabelPad lbCodProd = new JLabelPad( "Cód.Prod" );

	private JLabelPad lbDescProd = new JLabelPad( "Descrição do produto" );

	private JLabelPad lbQtd = new JLabelPad( "Qtd" );

	private JLabelPad lbQtdAFat = new JLabelPad( "Qtd A Fat" );
	
	private JLabelPad lbQtdFat = new JLabelPad( "Qtd Fat" );
	
	private JPanelPad panelGeral = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );
	
	private JPanelPad pinCab = new JPanelPad( 0, 80 );

	private ListaCampos lcItCompra = new ListaCampos( this );
	
	private boolean[] prefs;
	
	private enum ITENS { CODPROD, QTDITORC, QTDAFATITORC, QTDFATITORC };
	
	public DLEditQtd() {

		super();
		
		setTitulo( "Editar quantidade", this.getClass().getName() );
		setAtribos( 500, 200 );
		setResizable( true );
		
		montaTela();
	
	}
	
	public void montaTela(){

		
		setPanel( panelGeral );
		panelGeral.add( pinCab );
			

		pinCab.adic( lbItem, 7, 5, 80, 20 );
		pinCab.adic( txtItem, 7, 25, 80, 20 );
		pinCab.adic( lbCodProd, 90, 5, 80, 20 );
		pinCab.adic( txtCodProd, 90, 25, 80, 20 );
		pinCab.adic( lbDescProd, 173, 5, 250, 20 );
		pinCab.adic( txtDescProd, 173, 25, 250, 20 );
		
		pinCab.adic( lbQtd, 7, 65, 80, 20 );
		pinCab.adic( txtQtd, 7, 85, 80, 20 );
		pinCab.adic( lbQtdAFat, 175, 65, 80, 20 );
		pinCab.adic( txtQtdAFat, 175, 85, 80, 20 );
		pinCab.adic( lbQtdFat, 343, 65, 80, 20 );
		pinCab.adic( txtQtdFat, 343, 85, 80, 20 );
	}

	

	public void actionPerformed( ActionEvent evt ) {

		super.actionPerformed( evt );
	}
	
	private boolean[] getPrefs() {
		
		return prefs;
		
	}

	public void setConexao( DbConnection cn ) {

		super.setConexao( cn );
		montaTela();
		prefs = getPrefs();
	}
}
