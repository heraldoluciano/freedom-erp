/**
 * @version 23/10/2013 <BR>
 * @author Setpoint Tecnologia em Informática Ltda./Robson Sanchez<BR>
 * 
 *         Projeto: Freedom <BR>
 * 
 *         Pacote: org.freedom.modulos.std.view.frame.utility <BR>
 *         Classe: @(#)FTrocaRefprod.java <BR>
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
 *         Classe responsável pela substituição de referência dos produtos.
 */

package org.freedom.modulos.std.view.frame.utility;

import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.persistence.GuardaCampo;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.component.JPanelPad;
import org.freedom.library.swing.component.JTextFieldFK;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.frame.FDetalhe;
import org.freedom.modulos.gms.view.frame.crud.tabbed.FProduto;

public class FTrocaRefprod extends FDetalhe {
	private static final long serialVersionUID = 1L;

	private JTextFieldPad txtId = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 10, 0 );

	private JTextFieldPad txtCodemp = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtCodfilial = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtCodprod = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 10, 0 );

	private JTextFieldPad txtRefprodold = new JTextFieldPad( JTextFieldPad.TP_STRING, 20, 0 );

	private JTextFieldPad txtRefprodnew = new JTextFieldPad( JTextFieldPad.TP_STRING, 20, 0 );

	private JTextFieldPad txtMotivo = new JTextFieldPad( JTextFieldPad.TP_STRING, 80, 0 );

	private JTextFieldFK txtDescprod = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtId_it = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 10, 0 );

	private ListaCampos lcProd = new ListaCampos( this, "PD" );

	private JPanelPad pinCab = new JPanelPad();

	private JPanelPad pinDet = new JPanelPad();

	public FTrocaRefprod() {

		setTitulo( "Cadastro Fluxos" );
		setAtribos( 50, 50, 450, 350 );

		setAltCab( 90 );
		pinCab = new JPanelPad( 420, 90 );
		lcCampos.setUsaFI( false );
		lcCampos.setUsaME( false );
		lcDet.setUsaFI( false );
		lcDet.setUsaME( false );
		
		setListaCampos( lcCampos );
		setPainel( pinCab, pnCliCab );

		lcProd.add( new GuardaCampo( txtCodprod, "codprod", "Cód.prod.", ListaCampos.DB_PK, true ) );
		lcProd.add( new GuardaCampo( txtRefprodold, "refprod", "Referência", ListaCampos.DB_SI, false ) );
		lcProd.add( new GuardaCampo( txtDescprod, "descprod", "Descrição do produto", ListaCampos.DB_SI, false ) );
		lcProd.montaSql( false, "PRODUTO", "EQ" );
		lcProd.setQueryCommit( false );
		lcProd.setReadOnly( true );
		txtCodprod.setTabelaExterna( lcProd, FProduto.class.getCanonicalName() );

		adicCampo( txtId, 7, 20, 70, 20, "id", "ID.", ListaCampos.DB_PK, true );
		adicCampo( txtMotivo, 80, 20, 230, 20, "motivo", "Motivo", ListaCampos.DB_SI, true );
		adicCampoInvisivel( txtCodemp, "codemp", "cód.emp.", ListaCampos.DB_SI , true );
		adicCampoInvisivel( txtCodfilial, "codfilial", "cód.filial.", ListaCampos.DB_SI , true );
		setListaCampos( true, "TROCAREFPROD", "EQ" );

		setAltDet( 60 );
		setPainel( pinDet, pnDet );
		setListaCampos( lcDet );
		setNavegador( navRod );

		adicCampo( txtId_it, 7, 20, 40, 20, "id", "ID.", ListaCampos.DB_PK, true );
		adicCampo( txtCodprod, 50, 20, 70, 20, "Codprod", "Cód.prod.", ListaCampos.DB_FK, txtDescprod, true );
		adicDescFK( txtDescprod, 123, 20, 230, 20, "Descprod", "Descrição do produto" );
		setListaCampos( true, "ITTROCAREFPROD", "EQ" );

		montaTab();
		tab.setTamColuna( 40, 0 );
		tab.setTamColuna( 45, 1 );
		tab.setTamColuna( 350, 2 );
	}

	public void setConexao( DbConnection cn ) {
		super.setConexao( cn );
		lcProd.setConexao( cn );
	}

}
