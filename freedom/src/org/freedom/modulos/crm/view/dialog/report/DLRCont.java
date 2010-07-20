/**
 * @version 10/10/2003 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 * 
 *         Projeto: Freedom <BR>
 * 
 *         Pacote: org.freedom.modulos.tmk <BR>
 *         Classe: @(#)DLRCont.java <BR>
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
 *         Comentários sobre a classe...
 * 
 */

package org.freedom.modulos.crm.view.dialog.report;

import java.awt.Component;
import java.awt.GridLayout;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.persistence.GuardaCampo;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.component.JCheckBoxPad;
import org.freedom.library.swing.component.JLabelPad;
import org.freedom.library.swing.component.JPanelPad;
import org.freedom.library.swing.component.JRadioGroup;
import org.freedom.library.swing.component.JTextFieldFK;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.dialog.FFDialogo;

import java.util.Vector;

public class DLRCont extends FFDialogo {

	private static final long serialVersionUID = 1L;

	private JRadioGroup<?, ?> rgOrdem = null;

	private JRadioGroup<?, ?> rgModo = null;

	private JPanelPad pnlbSelec = new JPanelPad( JPanelPad.TP_JPANEL, new GridLayout( 1, 1 ) );

	private JPanelPad pinSelec = new JPanelPad( 350, 70 );

	private JPanelPad pnlbPessoa = new JPanelPad( JPanelPad.TP_JPANEL, new GridLayout( 1, 1 ) );

	private JPanelPad pinPessoa = new JPanelPad( 450, 40 );

	private JTextFieldPad txtCid = new JTextFieldPad( JTextFieldPad.TP_STRING, 30, 0 );

	private JLabelPad lbSelec = new JLabelPad( " Selecão:" );

	private JLabelPad lbDe = new JLabelPad( "De:" );

	private JLabelPad lbA = new JLabelPad( "À:" );

	private JTextFieldPad txtDe = new JTextFieldPad( JTextFieldPad.TP_STRING, 40, 0 );

	private JTextFieldPad txtA = new JTextFieldPad( JTextFieldPad.TP_STRING, 40, 0 );

	private JLabelPad lbOrdem = new JLabelPad( "Ordenar por:" );

	private JLabelPad lbPessoa = new JLabelPad( " Selecionar pessoas:" );

	private JLabelPad lbCid = new JLabelPad( "Cidade" );

	private JLabelPad lbModo = new JLabelPad( "Modo do relatório:" );

	private JCheckBoxPad cbObs = new JCheckBoxPad( "Imprimir Observações ?", "S", "N" );

	private JCheckBoxPad cbFis = new JCheckBoxPad( "Física", "S", "N" );

	private JCheckBoxPad cbJur = new JCheckBoxPad( "Jurídica", "S", "N" );

	private Vector<String> vLabs = new Vector<String>();

	private Vector<String> vVals = new Vector<String>();

	private Vector<String> vLabsModo = new Vector<String>();

	private Vector<String> vValsModo = new Vector<String>();

	private JLabelPad lbSetor = new JLabelPad( "Cód.setor" );

	private JLabelPad lbDescSetor = new JLabelPad( "Descrição do setor" );

	private JTextFieldPad txtCodSetor = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtDescSetor = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private ListaCampos lcSetor = new ListaCampos( this );

	public DLRCont( Component cOrig, DbConnection cn ) {

		super( cOrig );
		setTitulo( "Relatório de Contatos" );
		setAtribos( 460, 385 );
		vLabs.addElement( "Código" );
		vLabs.addElement( "Nome" );
		vVals.addElement( "C" );
		vVals.addElement( "D" );
		rgOrdem = new JRadioGroup<String, String>( 1, 2, vLabs, vVals );
		rgOrdem.setVlrString( "D" );

		vLabsModo.addElement( "Resumido" );
		vLabsModo.addElement( "Completo" );
		vValsModo.addElement( "R" );
		vValsModo.addElement( "C" );
		rgModo = new JRadioGroup<String, String>( 1, 2, vLabsModo, vValsModo );
		rgModo.setVlrString( "R" );

		cbObs.setVlrString( "N" );
		cbFis.setVlrString( "N" );
		cbJur.setVlrString( "S" );

		lcSetor.add( new GuardaCampo( txtCodSetor, "CodSetor", "Código", ListaCampos.DB_PK, false ) );
		lcSetor.add( new GuardaCampo( txtDescSetor, "DescSetor", "Descrição", ListaCampos.DB_SI, false ) );
		lcSetor.montaSql( false, "SETOR", "VD" );
		lcSetor.setReadOnly( true );
		txtCodSetor.setTabelaExterna( lcSetor, null );
		txtCodSetor.setFK( true );
		txtCodSetor.setNomeCampo( "CodSetor" );

		pnlbSelec.add( lbSelec );
		adic( lbOrdem, 7, 5, 180, 20 );
		adic( rgOrdem, 7, 25, 240, 30 );
		adic( cbObs, 250, 35, 190, 20 );
		adic( pnlbSelec, 10, 63, 80, 15 );
		pinSelec.adic( lbDe, 7, 10, 30, 20 );
		pinSelec.adic( txtDe, 40, 15, 380, 20 );
		pinSelec.adic( lbA, 7, 40, 30, 20 );
		pinSelec.adic( txtA, 40, 40, 380, 20 );
		adic( pinSelec, 7, 70, 433, 70 );
		pnlbPessoa.add( lbPessoa );
		adic( pnlbPessoa, 10, 148, 170, 15 );
		pinPessoa.adic( cbFis, 7, 10, 93, 20 );
		pinPessoa.adic( cbJur, 145, 10, 100, 20 );
		adic( pinPessoa, 7, 155, 290, 40 );
		adic( lbCid, 300, 155, 140, 20 );
		adic( txtCid, 300, 175, 140, 20 );
		adic( lbModo, 7, 200, 170, 20 );
		adic( rgModo, 7, 220, 433, 30 );
		adic( lbSetor, 7, 255, 250, 20 );
		adic( txtCodSetor, 7, 275, 80, 20 );
		adic( lbDescSetor, 90, 255, 250, 20 );
		adic( txtDescSetor, 90, 275, 350, 20 );

		lcSetor.setConexao( cn );
	}

	public String[] getValores() {

		String[] sRetorno = new String[ 10 ];
		if ( rgOrdem.getVlrString().compareTo( "C" ) == 0 )
			sRetorno[ 0 ] = "CODCTO";
		else if ( rgOrdem.getVlrString().compareTo( "D" ) == 0 )
			sRetorno[ 0 ] = "NOMECTO";
		sRetorno[ 1 ] = cbObs.getVlrString();
		sRetorno[ 2 ] = txtDe.getText();
		sRetorno[ 3 ] = txtA.getText();
		sRetorno[ 4 ] = cbFis.getVlrString();
		sRetorno[ 5 ] = txtCid.getVlrString();
		sRetorno[ 6 ] = cbJur.getVlrString();
		sRetorno[ 7 ] = rgModo.getVlrString();
		sRetorno[ 8 ] = txtCodSetor.getText();
		sRetorno[ 9 ] = txtDescSetor.getText();
		return sRetorno;
	}
}
