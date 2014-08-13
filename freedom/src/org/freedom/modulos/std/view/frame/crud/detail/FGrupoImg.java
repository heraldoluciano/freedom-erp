/**
 * @version 13/08/2014 <BR>
 * @author Setpoint Informática Ltda./Robson Sanchez <BR>
 * 
 *         Projeto: Freedom <BR>
 * 
 *         Pacote: org.freedom.modulos.std.view.frame.crud.detail <BR>
 *         Classe: @(#)FGrupoImg.java <BR>
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
 *         Cadastro de imagens de grupo de produtos
 * 
 */

package org.freedom.modulos.std.view.frame.crud.detail;

import java.awt.event.ActionListener;

import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.persistence.GuardaCampo;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.component.JPanelPad;
import org.freedom.library.swing.component.JTextFieldFK;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.frame.FDetalhe;
import org.freedom.modulos.std.view.frame.crud.plain.FImagem;

public class FGrupoImg extends FDetalhe implements ActionListener {

	private static final long serialVersionUID = 1L;

	private JTextFieldFK txtCodGrup = new JTextFieldFK( JTextFieldPad.TP_STRING, 14, 0 );

	private JTextFieldFK txtDescGrup = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtCodImg = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtDescImg = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtSeqImg = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private ListaCampos lcImg = new ListaCampos( this, "IM" );

	private JPanelPad pinCab = new JPanelPad();

	private JPanelPad pinDet = new JPanelPad();

	public FGrupoImg() {

		setTitulo( "Imagens de grupo de produtos" );
		setAtribos( 50, 50, 450, 350 );

		setAltCab( 90 );
		pinCab = new JPanelPad( 420, 90 );
		setListaCampos( lcCampos );
		setPainel( pinCab, pnCliCab );

		lcImg.add( new GuardaCampo( txtCodImg, "CodImg", "Cód.img.", ListaCampos.DB_PK, true ) );
		lcImg.add( new GuardaCampo( txtDescImg, "DescImg", "Descrição da imagem", ListaCampos.DB_SI, false ) );
		lcImg.montaSql( false, "IMAGEM", "SG" );
		lcImg.setQueryCommit( false );
		lcImg.setReadOnly( true );
		txtCodImg.setTabelaExterna( lcImg, FImagem.class.getCanonicalName() );
		lcCampos.setReadOnly( true );
		adicCampo( txtCodGrup, 7, 20, 70, 20, "CodGrup", "Cód.grupo", ListaCampos.DB_PK, true );
		adicCampo( txtDescGrup, 80, 20, 230, 20, "DescGrup", "Descrição do grupo/sub-grupo", ListaCampos.DB_SI, true );
		setListaCampos( true, "GRUPO", "EQ" );
		setAltDet( 60 );
		setPainel( pinDet, pnDet );
		setListaCampos( lcDet );
		setNavegador( navRod );

		adicCampo( txtSeqImg, 7, 20, 40, 20, "SeqImg", "Item", ListaCampos.DB_PK, true );
		adicCampo( txtCodImg, 50, 20, 70, 20, "CodImg", "Cód.Img.", ListaCampos.DB_FK, txtDescImg, true );
		adicDescFK( txtDescImg, 123, 20, 230, 20, "DescImg", "Descrição da imagem" );
		setListaCampos( true, "GRUPOIMG", "EQ" );

		montaTab();
		tab.setTamColuna( 40, 0 );
		tab.setTamColuna( 45, 1 );
		tab.setTamColuna( 350, 2 );

		setImprimir( false );

	}

	public void setConexao( DbConnection cn ) {

		super.setConexao( cn );
		lcImg.setConexao( cn );
	}
	
	public void carregaGrupo(String codgrup) {
		lcCampos.setState( ListaCampos.LCS_NONE );
		txtCodGrup.setVlrString( codgrup );
		lcCampos.carregaDados();
	}

}
