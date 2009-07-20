/**
 * @version 25/09/2003 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.pcp <BR>
 * Classe: @(#)FPrefereProd.java <BR>
 * 
 * Este arquivo é parte do sistema Freedom-ERP, o Freedom-ERP é um software livre; você pode redistribui-lo e/ou <BR>
 * modifica-lo dentro dos termos da Licença Pública Geral GNU como publicada pela Fundação do Software Livre (FSF); <BR>
 * na versão 2 da Licença, ou (na sua opnião) qualquer versão. <BR>
 * Este programa é distribuido na esperança que possa ser  util, mas SEM NENHUMA GARANTIA; <BR>
 * sem uma garantia implicita de ADEQUAÇÂO a qualquer MERCADO ou APLICAÇÃO EM PARTICULAR. <BR>
 * Veja a Licença Pública Geral GNU para maiores detalhes. <BR>
 * Você deve ter recebido uma cópia da Licença Pública Geral GNU junto com este programa, se não, <BR>
 * escreva para a Fundação do Software Livre(FSF) Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA <BR> <BR>
 *
 * Comentários sobre a classe...
 * 
 */ 

package org.freedom.modulos.pcp;
import org.freedom.infra.model.jdbc.DbConnection;
import java.util.Vector;

import javax.swing.SwingConstants;

import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JCheckBoxPad;
import org.freedom.componentes.JComboBoxPad;
import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JPanelPad;
import org.freedom.componentes.JRadioGroup;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.PainelImagem;
import org.freedom.telas.FTabDados;

public class FPrefereProd extends FTabDados {
	
	private static final long serialVersionUID = 1L;

	private final JPanelPad pinGeral = new JPanelPad();
	
	private final JPanelPad pinAss = new JPanelPad( 470, 300 );

	private final JTextFieldPad txtClass = new JTextFieldPad( JTextFieldPad.TP_STRING, 20, 0 );

	private final JTextFieldPad txtNomeResp = new JTextFieldPad( JTextFieldPad.TP_STRING, 30, 0 );

	private final JTextFieldPad txtIdentProfResp = new JTextFieldPad( JTextFieldPad.TP_STRING, 30, 0 );

	private final JTextFieldPad txtCargoResp = new JTextFieldPad( JTextFieldPad.TP_STRING, 30, 0 );

	private final JTextFieldPad txtCodTipoMov = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private final JTextFieldFK txtDescTipoMov = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );
	
	private final JTextFieldPad txtNDiaMes = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 5, 0 );
	
	private JRadioGroup<?, ?> rgNomeRelAnal = null;

	private JComboBoxPad cbSitRMAOP = null;
	
	private final JCheckBoxPad cbBaixaRmaAprov = new JCheckBoxPad( "Baixar o estoque de RMA aprovada ?", "S", "N" );
	
	private final JCheckBoxPad cbAuto = new JCheckBoxPad( "Automatizar rateio de itens/lote?", "S", "N" );
	
	private final JCheckBoxPad cbExcluiRma = new JCheckBoxPad( "Permite excluir RMA de outro usuário?", "S", "N" );
	
	private final PainelImagem imgAssOrc = new PainelImagem( 65000 );

	private final ListaCampos lcTipoMov = new ListaCampos( this, "TM" );
	
	
	public FPrefereProd() {

		super();
		setTitulo( "Preferências de Produção" );
		setAtribos( 50, 50, 550, 390 );
		
		montaListaCampos();
		montaTela();
		
	}
	
	private void montaTela(){
	
		Vector<String> vLabs = new Vector<String>();
		Vector<String> vVals = new Vector<String>();

		vLabs.addElement( "<--Selecione-->" );
		vLabs.addElement( "Pendente" );
		vLabs.addElement( "Aprovada" );
		
		vVals.addElement( "" );
		vVals.addElement( "PE" );
		vVals.addElement( "AF" );

		cbSitRMAOP = new JComboBoxPad( vLabs, vVals, JComboBoxPad.TP_STRING, 2, 0 );

		
		Vector<String> vNomeRelLab = new Vector<String>();
		Vector<String> vNomeRelVal = new Vector<String>();
		
		vNomeRelLab.addElement( "Responsáve técnico da Produção." );
		vNomeRelLab.addElement( "Usuário que lançou a análise." );
		vNomeRelVal.addElement( "R" );
		vNomeRelVal.addElement( "U" );
		rgNomeRelAnal = new JRadioGroup<String, String>( 2, 1, vNomeRelLab, vNomeRelVal );

		adicTab( "Geral", pinGeral );

		JPanelPad pinRespon = new JPanelPad();
		JLabelPad lbRespon = new JLabelPad( "Reponsável técnico da Produção", SwingConstants.CENTER );
		lbRespon.setOpaque( true );
		
		setPainel( pinRespon );
		
		adicCampo( txtNomeResp, 7, 30, 230, 20, "NOMERESP", "Nome do reponsável", ListaCampos.DB_SI, false );
		adicCampo( txtIdentProfResp, 7, 70, 230, 20, "IDENTPROFRESP", "Indent.prof.", ListaCampos.DB_SI, false );
		adicCampo( txtCargoResp, 7, 110, 230, 20, "CARGORESP", "Cargo", ListaCampos.DB_SI, false );

		JPanelPad pinOp = new JPanelPad();
		JLabelPad lbOP = new JLabelPad( "Informações padrão para OP.", SwingConstants.CENTER );
		lbOP.setOpaque( true );
		
		setPainel( pinOp );

		adicCampo( txtClass, 7, 30, 230, 20, "CLASSOP", "Classe padrão para O.P.", ListaCampos.DB_SI, false );
		adicCampo( txtCodTipoMov, 7, 70, 50, 20, "CODTIPOMOV", "Cd.TM.", ListaCampos.DB_FK, txtDescTipoMov, true );
		adicDescFK( txtDescTipoMov, 60, 70, 175, 20, "DESCTIPOMOV", "Descrição do tipo de mov." );
		adicDB( cbSitRMAOP, 7, 110, 230, 20, "SITRMAOP", "Situação padrão para RMA", false );
		
		setPainel( pinGeral );
			
		adic( lbRespon, 12, 10, 200, 20 );
		adic( pinRespon, 7, 20, 250, 150 );
		adic( lbOP, 272, 10, 200, 20 );
		adic( pinOp, 267, 20, 250, 150 );		
		adic( new JLabelPad( "N° meses p/ descarte C.P" ), 10, 175, 150, 20 );
		adicCampo( txtNDiaMes, 10, 195, 180, 20, "MESESDESCCP", "", ListaCampos.DB_SI, false );
		adicDB( cbBaixaRmaAprov, 10, 220, 250, 20, "BAIXARMAAPROV", "", false );
		adicDB( cbAuto, 10, 240, 250, 20, "RATAUTO", "", false );
		adicDB( cbExcluiRma, 10, 260, 250, 20, "APAGARMAOP", "", false );
		adicDB( rgNomeRelAnal, 267, 195, 250, 60, "NomeRelAnal", "Nome no relatório de Análises", false );
		
		setPainel( pinAss );
		adicTab( "Assinatura", pinAss );
		adicDB( imgAssOrc, 15, 30, 340, 85, "ImgAssResp", "Assinatura do responsável técnico ( 340 pixel X 85 pixel )", true );
		setListaCampos( false, "PREFERE5", "SG" );
		
		nav.setAtivo( 0, false );
		nav.setAtivo( 1, false );		
	}
	
	private void montaListaCampos(){
		
		lcTipoMov.add( new GuardaCampo( txtCodTipoMov, "CodTipoMov", "Cód.tp.mov.", ListaCampos.DB_PK, false ) );
		lcTipoMov.add( new GuardaCampo( txtDescTipoMov, "DescTipoMov", "Descrição do tipo de movimento", ListaCampos.DB_SI, false ) );
		lcTipoMov.montaSql( false, "TIPOMOV", "EQ" );
		lcTipoMov.setWhereAdic( " TIPOMOV='OP' " );
		lcTipoMov.setQueryCommit( false );
		lcTipoMov.setReadOnly( true );
		txtCodTipoMov.setTabelaExterna( lcTipoMov );
		txtCodTipoMov.setFK( true );
		lcCampos.setMensInserir( false );		
	}

	public void setConexao( DbConnection cn ) {

		super.setConexao( cn );
		lcTipoMov.setConexao( cn );
		lcCampos.carregaDados();		
	}
}
