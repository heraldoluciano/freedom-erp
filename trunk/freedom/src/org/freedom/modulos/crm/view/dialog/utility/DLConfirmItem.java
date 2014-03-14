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

import java.awt.event.ActionEvent;
import org.freedom.library.swing.component.JTextFieldFK;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.dialog.FFDialogo;
import org.freedom.modulos.std.orcamento.bean.Item;

public class DLConfirmItem extends FFDialogo {

	private static final long serialVersionUID = 1L;

	private final JTextFieldFK txtCodprod = new JTextFieldFK( JTextFieldFK.TP_INTEGER, 10, 0 );
	
	private final JTextFieldFK txtDescprod = new JTextFieldFK( JTextFieldFK.TP_STRING, 100, 0);
	
	private final JTextFieldFK txtQtd = new JTextFieldFK( JTextFieldFK.TP_DECIMAL, 15, 5 );

	private final JTextFieldPad txtPreco = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 15, 5 );
	
	private Item result = null;

	private Integer codemp;
	
	private Integer codfilial;
	
	public DLConfirmItem() {

		super();
		setTitulo( "Confirmação de item" );
		setAtribos( 50, 50, 450, 200 );
		montaTela();
	
	}
	
	public void montaTela(){

		adic( txtCodprod, 7, 25, 70, 20, "Cód.prod." );
		adic( txtDescprod, 80, 25, 300, 20, "Descrição");
		adic( txtQtd, 7, 65, 90, 20, "Quantidade" );
		adic( txtPreco, 100, 65, 90, 20, "Preço");
		
	}
	
	public void setValues(Item item) {
		setCodemp( item.getCodemp() );
		setCodfilial( item.getCodfilial() );
		txtCodprod.setVlrInteger( item.getCodprod() );
		txtDescprod.setVlrString( item.getDescprod() );
		txtQtd.setVlrBigDecimal( item.getQtd() );
		txtPreco.setVlrBigDecimal( item.getPreco() );
		
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

}
