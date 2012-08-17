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
import java.util.Vector;

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

public class DLRCont extends FFDialogo {
	
	public static enum VALORES{ ORDEM, OBSERVACAO, DE, A, FISICA, CIDADE, JURIDICA, MODO, SETOR, DESCSETOR, TIPOIMP } 

	private static final long serialVersionUID = 1L;

	private JRadioGroup<?, ?> rgOrdem = null;

	private JRadioGroup<?, ?> rgModo = null;
	
	private JRadioGroup<?, ?> rgTipoImp = null;

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
	
	private JLabelPad lbTipoImp = new JLabelPad( "Relatório:" );

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
	
	private Vector<String> vLabsTipoImp = new Vector<String>();

	private Vector<String> vValsTipoImp = new Vector<String>();

	private JTextFieldPad txtCodSetor = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtDescSetor = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );
	
	private JTextFieldPad txtTipoCli = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtDescTipoCli = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );
	
	private JTextFieldPad txtCodSiglaUF = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtDescSiglaUF = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );
	
	private JTextFieldPad txtCodMunicipio = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtDescMunicipio = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );
	
	private JTextFieldPad txtCodGrupo = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtDescGrupo = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private JTextFieldPad txtCodAtividade = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtDescAtividade = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );
	
	private JTextFieldPad txtCodOrigem = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtDescOrigem = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );
	
	private JTextFieldPad txtEdificio = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 30, 0 );

	private ListaCampos lcSetor = new ListaCampos( this );

	public DLRCont( Component cOrig, DbConnection cn ) {

		super( cOrig );
		setTitulo( "Relatório de Contatos" );
		setAtribos( 480, 690 );
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
		
		

		vLabsTipoImp.addElement( "Gráfico" );
		vLabsTipoImp.addElement( "Texto" );
		vValsTipoImp.addElement( "G" );
		vValsTipoImp.addElement( "T" );
		rgTipoImp = new JRadioGroup<String, String>( 1, 2, vLabsTipoImp, vValsTipoImp );
		rgTipoImp.setVlrString( "G" );

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
		adic( rgOrdem, 7, 25, 220, 30 );
		adic( lbTipoImp, 230, 5, 180, 20 );
		adic( rgTipoImp, 230, 25, 220, 30 );
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
		adic( txtCodSetor, 7, 275, 80, 20, "Cód.setor" );
		adic( txtDescSetor, 90, 275, 350, 20, "Descrição do setor" );
		adic( txtTipoCli, 7, 315, 80, 20, "Cód.Cliente" );
		adic( txtDescTipoCli, 90, 315, 350, 20, "Descrição do tipo de cliente" );
		adic( txtCodSiglaUF, 7, 355, 80, 20, "Cód.Sigla UF" );
		adic( txtDescSiglaUF, 90, 355, 350, 20, "Sigla UF" );
		adic( txtCodMunicipio, 7, 395, 80, 20, "Cód.Município" );
		adic( txtDescMunicipio, 90, 395, 350, 20, "Município" );
		adic( txtCodGrupo, 7, 435, 80, 20, "Cód.Grupo" );
		adic( txtDescGrupo, 90, 435, 350, 20, "Descrição do Grupo" );
		adic( txtCodAtividade, 7, 475, 80, 20, "Cód.Atividade" );
		adic( txtDescAtividade, 90, 475, 350, 20, "Descrição da Atividade" );
		adic( txtCodOrigem, 7, 515, 80, 20, "Cód.Origem" );
		adic( txtDescOrigem, 90, 515, 350, 20, "Descrição da Origem" );
		adic( txtEdificio, 7, 555, 433, 20, "Edifício" );
		adic( cbObs, 7, 575, 220, 30 );
		

		lcSetor.setConexao( cn );
	}

	public String[] getValores() {

		String[] sRetorno = new String[ 11 ];
		if ( rgOrdem.getVlrString().compareTo( "C" ) == 0 )
			sRetorno[ VALORES.ORDEM.ordinal() ] = "CODCTO";
		else if ( rgOrdem.getVlrString().compareTo( "D" ) == 0 )
			sRetorno[ VALORES.ORDEM.ordinal() ] = "NOMECTO";
		sRetorno[ VALORES.OBSERVACAO.ordinal() ] = cbObs.getVlrString();
		sRetorno[ VALORES.DE.ordinal() ] = txtDe.getText();
		sRetorno[ VALORES.A.ordinal() ] = txtA.getText();
		sRetorno[ VALORES.FISICA.ordinal() ] = cbFis.getVlrString();
		sRetorno[ VALORES.CIDADE.ordinal() ] = txtCid.getVlrString();
		sRetorno[ VALORES.JURIDICA.ordinal() ] = cbJur.getVlrString();
		sRetorno[ VALORES.MODO.ordinal() ] = rgModo.getVlrString();
		sRetorno[ VALORES.SETOR.ordinal() ] = txtCodSetor.getText();
		sRetorno[ VALORES.DESCSETOR.ordinal() ] = txtDescSetor.getText();
		sRetorno[ VALORES.TIPOIMP.ordinal() ] = rgTipoImp.getVlrString();
		
		
		
		return sRetorno;
	}
}
