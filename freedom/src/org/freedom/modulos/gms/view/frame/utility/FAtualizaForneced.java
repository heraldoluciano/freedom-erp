/**
 * @version 10/01/2012 <BR>
 * @author Setpoint Informática Ltda./Bruno Nascimento <BR>
 * 
 *         Projeto: Freedom <BR>
 * 
 *         Pacote: org.freedom.modulos.gms<BR>
 *         Classe: @(#)FAtualizaForneced.java <BR>
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
 *         Tela com o objetivo de atualizar vinculos de fornecedores com produtos.
 * 
 */
package org.freedom.modulos.gms.view.frame.utility;

import java.awt.BorderLayout;
import java.awt.Container;
import java.util.Calendar;
import javax.swing.JScrollPane;
import org.freedom.bmps.Icone;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.persistence.GuardaCampo;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.component.JButtonPad;
import org.freedom.library.swing.component.JPanelPad;
import org.freedom.library.swing.component.JTablePad;
import org.freedom.library.swing.component.JTextFieldFK;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.dialog.FFDialogo;
import org.freedom.modulos.gms.view.frame.crud.tabbed.FProduto;
import org.freedom.modulos.std.view.frame.crud.tabbed.FFornecedor;

public class FAtualizaForneced extends FFDialogo {

	private static final long serialVersionUID = 1L;
	
	private JPanelPad pinForneced = new JPanelPad( 600, 100);
	
    private JTablePad tabForneced = new JTablePad();
	
	private JScrollPane scpForneced = new JScrollPane( tabForneced );
	 
	private JTextFieldPad txtDataini = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );
	
	private JTextFieldPad txtDatafim = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );
	
	private JTextFieldPad txtCodFor = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	
	private JTextFieldFK txtRazFor = new JTextFieldFK( JTextFieldFK.TP_STRING, 50, 0 );
	
	private JTextFieldPad txtCodProd = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	
	private JTextFieldFK txtDescProd = new JTextFieldFK( JTextFieldFK.TP_STRING, 100, 0 );
	
	private JButtonPad btGerar = new JButtonPad( Icone.novo( "btExecuta.gif" ) );
	
	private ListaCampos lcProd = new ListaCampos( this );
	
	private ListaCampos lcFor = new ListaCampos( this );
	
	public FAtualizaForneced() {
		setTitulo( "Atualiza Fornecedor/Produto" );
		setAtribos( 600, 430 );
		
		montaListaCampos();
		montaTela();
	}
	
	private void montaListaCampos(){
		
		lcProd.add( new GuardaCampo( txtCodProd, "CodProd", "Cód.prod", ListaCampos.DB_PK, true ) );
		lcProd.add( new GuardaCampo( txtDescProd, "DescProd", "Descrição do produto", ListaCampos.DB_SI, false ) );
		txtCodProd.setNomeCampo( "CodProd" );
		txtCodProd.setFK( true );
		lcProd.setReadOnly( true );
		lcProd.montaSql( false, "PRODUTO", "EQ" );
		txtCodProd.setTabelaExterna( lcProd, FProduto.class.getCanonicalName() );
		
		lcFor.add( new GuardaCampo( txtCodFor, "CodFor", "Cód.for.", ListaCampos.DB_PK, false ) );
		lcFor.add( new GuardaCampo( txtRazFor, "RazFor", "Razão social do fornecedor", ListaCampos.DB_SI, false ) );
		txtCodFor.setNomeCampo( "CodFor" );
		txtCodFor.setFK(true);
		lcFor.setReadOnly( true );
		lcFor.montaSql( false, "FORNECED", "CP" );
		txtCodFor.setTabelaExterna( lcFor, FFornecedor.class.getCanonicalName() );
	}
	
	
	private void montaTela(){
		
		Container c = getContentPane();
		c.setLayout( new BorderLayout() );
		c.add( pinForneced, BorderLayout.NORTH );
		c.add( scpForneced, BorderLayout.CENTER );
		
		pinForneced.adic( txtDataini, 7, 20, 80, 20, "Data Inícial" );
		pinForneced.adic( txtDatafim, 90, 20, 80, 20, "Data Final" );
		pinForneced.adic( txtCodFor, 173, 20, 80, 20, "Cód.for." ); 
		pinForneced.adic( txtRazFor, 256, 20, 300, 20, "Razão do fornecedor" ); 
		pinForneced.adic( txtCodProd, 7, 60, 80, 20, "Cód.prod." ); 
		pinForneced.adic( txtDescProd, 90, 60, 440, 20, "Descrição do produto" ); 
		pinForneced.adic( btGerar, 533, 55, 30, 30 ); 
		
		Calendar cPeriodo = Calendar.getInstance();
		txtDatafim.setVlrDate( cPeriodo.getTime() );
		cPeriodo.set( Calendar.DAY_OF_MONTH, cPeriodo.get( Calendar.DAY_OF_MONTH ) - 30 );
		txtDataini.setVlrDate( cPeriodo.getTime() );
		
		adicBotaoSair();
	}
	

	public void setConexao( DbConnection cn ) {

		super.setConexao( cn );
		lcProd.setConexao( cn );
		lcFor.setConexao( cn );
	}
	

}
