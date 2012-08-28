/**
 * @version 28/08/2012 <BR>
 * @author Setpoint Informática Ltda./Sérgio Murilo Macedo<BR>
 * 
 *         Projeto: Freedom <BR>
 * 
 *         Pacote: org.freedom.modulos.std.view.frame.crud.detail <BR>
 *         Classe: @(#)FCalcCusto.java <BR>
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
 *         Cadastro de custo fórmula para custo de aquisição.
 * 
 */

package org.freedom.modulos.std.view.frame.crud.detail;

import java.util.Vector;

import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.persistence.GuardaCampo;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.component.JComboBoxPad;
import org.freedom.library.swing.component.JPanelPad;
import org.freedom.library.swing.component.JTextFieldFK;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.frame.FDetalhe;
import org.freedom.modulos.gms.view.frame.crud.tabbed.FProduto;
import org.freedom.modulos.std.view.dialog.utility.DLBuscaProd;

public class FCalcCusto extends FDetalhe {

	private static final long serialVersionUID = 1L;

	private JPanelPad pinCab = new JPanelPad();

	private JPanelPad pinDet = new JPanelPad();

	private JTextFieldPad txtCodCalc = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtDescCalc = new JTextFieldPad( JTextFieldPad.TP_STRING, 40, 0 );
	
	private Vector<String> vValsSigla = new Vector<String>();

	private Vector<String> vLabsSigla = new Vector<String>();
	
	private JComboBoxPad cbSiglaCalc = new JComboBoxPad( vLabsSigla, vValsSigla, JComboBoxPad.TP_STRING, 10, 0 );

	public FCalcCusto() {

		setTitulo( "Cadastro de Modelos da Grade" );
		setAtribos( 50, 20, 620, 400 );
		setAltCab( 120 );
		pinCab = new JPanelPad( 590, 80 );
		setListaCampos( lcCampos );
		setPainel( pinCab, pnCliCab );
		
		adicCampo( txtCodCalc, 7, 20, 70, 20, "CodModG", "Cód.mod.g.", ListaCampos.DB_PK, true );
		adicCampo( txtDescCalc, 80, 20, 197, 20, "DescModG", "Descrição do modelo de grade", ListaCampos.DB_SI, true );
		
		setListaCampos( true, "CALCCUSTO", "LF" );
		setAltDet( 120 );
		pinDet = new JPanelPad( 590, 110 );
		setPainel( pinDet, pnDet );
		setListaCampos( lcDet );
		setNavegador( navRod );

		
	/*	adicCampo( txtCodItModG, 7, 20, 70, 20, "CodItModG", "Item", ListaCampos.DB_PK, true );
		adicCampo( txtCodVarG, 80, 20, 77, 20, "CodVarG", "Cód.var.g.", ListaCampos.DB_FK, true );
		adicDescFK( txtDescVarG, 160, 20, 197, 20, "DescVarG", "Descrição da variante" );
		adicCampo( txtDescItModG, 360, 20, 200, 20, "DescItModG", "Descrição", ListaCampos.DB_SI, true );
		adicCampo( txtRefItModG, 7, 60, 87, 20, "RefItModG", "Ref.inicial", ListaCampos.DB_SI, true );
		adicCampo( txtCodFabItModG, 100, 60, 87, 20, "CodFabItModG", "Cód.fab.inic.", ListaCampos.DB_SI, true );
		adicCampo( txtCodBarItModG, 190, 60, 100, 20, "CodBarItModG", "Cód.bar.inic.", ListaCampos.DB_SI, true );*/
		setListaCampos( true, "ITMODGRADE", "EQ" );
		montaTab();
	}

	public void setConexao( DbConnection cn ) {

		super.setConexao( cn );
		//lcProd.setConexao( cn );
		//lcVarG.setConexao( cn );
		//txtCodProd.setBuscaAdic( new DLBuscaProd( con, "CODPROD", lcProd.getWhereAdic() ) );
	}
}
