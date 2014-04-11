/**
 * @version 14/03/2014 <BR>
 * @author Setpoint Informática Ltda./Robson Sanchez <BR>
 * 
 *         Projeto: Freedom <BR>
 * 
 *         Pacote: org.freedom.modulos.crm.view.dialog.utility <BR>
 *         Classe: @(#)DLConfirmItem.java <BR>
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
 *         Confirmação de item para orçamento
 * 
 */

package org.freedom.modulos.crm.view.dialog.utility;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.math.BigDecimal;

import org.freedom.library.swing.component.JTextFieldFK;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.dialog.FFDialogo;
import org.freedom.library.swing.frame.Aplicativo;
import org.freedom.modulos.std.orcamento.bean.Item;

public class DLConfirmItem extends FFDialogo implements FocusListener {

	private static final long serialVersionUID = 1L;

	private final JTextFieldFK txtCodprod = new JTextFieldFK( JTextFieldFK.TP_INTEGER, 10, 0 );
	
	private final JTextFieldFK txtDescprod = new JTextFieldFK( JTextFieldFK.TP_STRING, 100, 0);
	
	private final JTextFieldPad txtQtd = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 15, Aplicativo.casasDec );

	private final JTextFieldPad txtPreco = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 15, Aplicativo.casasDecPre );

	private final JTextFieldPad txtPercDesc = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 15, Aplicativo.casasDecPre );

	private final JTextFieldPad txtVlrDesc = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 15, Aplicativo.casasDecPre );

	private final JTextFieldFK txtVlrLiq = new JTextFieldFK( JTextFieldPad.TP_DECIMAL, 15, Aplicativo.casasDecPre );

	private Item result = null;

	private Integer codemp;
	
	private Integer codfilial;
	
	public DLConfirmItem(Component cOrig) {

		super(cOrig);
		setTitulo( "Confirmação de item" );
		setAtribos( 600, 250 );
		montaTela();
	
	}
	
	public void montaTela(){

		adic( txtCodprod, 7, 25, 70, 20, "Cód.prod." );
		adic( txtDescprod, 80, 25, 390, 20, "Descrição");
		adic( txtQtd, 7, 65, 90, 20, "Quantidade" );
		adic( txtPreco, 100, 65, 90, 20, "Preço");
		adic( txtPercDesc, 193, 65, 90, 20, "% Desconto");
		adic( txtVlrDesc, 286, 65, 90, 20, "Vlr.desconto");
		adic( txtVlrLiq, 379, 65, 90, 20, "Vlr.líquido");
		txtQtd.addFocusListener( this );
		txtPreco.addFocusListener( this );
		txtPercDesc.addFocusListener( this );
		txtVlrDesc.addFocusListener( this );
		
	}
	
	public void setValues(Item item) {
		setCodemp( item.getCodemp() );
		setCodfilial( item.getCodfilial() );
		txtCodprod.setVlrInteger( item.getCodprod() );
		txtDescprod.setVlrString( item.getDescprod() );
		txtQtd.setVlrBigDecimal( item.getQtd() );
		txtPreco.setVlrBigDecimal( item.getPreco() );
		txtPercDesc.setVlrBigDecimal( item.getPercdesc() );
		txtVlrDesc.setVlrBigDecimal( item.getVlrdesc() );
		txtVlrLiq.setVlrBigDecimal( item.getVlrliq() );
		
	}
	
	public void actionPerformed( ActionEvent evt ) {
		if (evt.getSource()==btOK) {
            result = new Item(getCodemp(), getCodfilial(), txtCodprod.getVlrInteger(), txtDescprod.getVlrString());
            result.setQtd( txtQtd.getVlrBigDecimal() );
            result.setPreco( txtPreco.getVlrBigDecimal() );
		} else if (evt.getSource()==btCancel) {
			result = null;
		}
		super.actionPerformed( evt );
	}

	
	public Item getResult() {
	
		return result;
	}

	
	public void setResult( Item result ) {
	
		this.result = result;
	}

	
	public Integer getCodemp() {
	
		return codemp;
	}

	
	public void setCodemp( Integer codemp ) {
	
		this.codemp = codemp;
	}

	
	public Integer getCodfilial() {
	
		return codfilial;
	}

	
	public void setCodfilial( Integer codfilial ) {
	
		this.codfilial = codfilial;
	}

	public void focusGained( FocusEvent e ) {

	}

	public void focusLost( FocusEvent e ) {
		calcVlrLiq();
		
	}

	private void calcVlrLiq() {
		BigDecimal qtd = txtQtd.getVlrBigDecimal();
		BigDecimal preco = txtPreco.getVlrBigDecimal();
		BigDecimal percdesc = txtPercDesc.getVlrBigDecimal();
		BigDecimal vlrdesc = txtVlrDesc.getVlrBigDecimal();
		BigDecimal vlrliq = txtVlrLiq.getVlrBigDecimal();
		
	}
}
