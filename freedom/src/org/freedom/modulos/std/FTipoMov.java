/**
 * @version 02/11/2003 <BR>
 * @author Setpoint Informática Ltda. Robson Sanchez e Fernando Oliveira da Silva <BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.std <BR>
 * Classe:
 * @(#)FTipoMov.java <BR>
 * 
 * Este arquivo é parte do sistema Freedom-ERP, o Freedom-ERP é um software livre; você pode redistribui-lo e/ou <BR>
 * modifica-lo dentro dos termos da Licença Pública Geral GNU como publicada pela Fundação do Software Livre (FSF); <BR>
 * na versão 2 da Licença, ou (na sua opnião) qualquer versão. <BR>
 * Este programa é distribuido na esperança que possa ser  util, mas SEM NENHUMA GARANTIA; <BR>
 * sem uma garantia implicita de ADEQUAÇÂO a qualquer MERCADO ou APLICAÇÃO EM PARTICULAR. <BR>
 * Veja a Licença Pública Geral GNU para maiores detalhes. <BR>
 * Você deve ter recebido uma cópia da Licença Pública Geral GNU junto com este programa, se não, <BR>
 * de acordo com os termos da LPG-PC <BR>
 * <BR>
 * 
 * Comentários sobre a classe...
 * 
 */

package org.freedom.modulos.std;

import java.awt.BorderLayout;
import java.awt.Dimension;
import org.freedom.infra.model.jdbc.DbConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JScrollPane;

import org.freedom.acao.CheckBoxEvent;
import org.freedom.acao.CheckBoxListener;
import org.freedom.acao.InsertEvent;
import org.freedom.acao.InsertListener;
import org.freedom.acao.PostEvent;
import org.freedom.acao.RadioGroupEvent;
import org.freedom.acao.RadioGroupListener;
import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JCheckBoxPad;
import org.freedom.componentes.JComboBoxPad;
import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JPanelPad;
import org.freedom.componentes.JRadioGroup;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.Navegador;
import org.freedom.componentes.Tabela;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FTabDados;

public class FTipoMov extends FTabDados implements RadioGroupListener, CheckBoxListener, InsertListener {

	private static final long serialVersionUID = 1L;

	private JPanelPad pinCamposGeral = new JPanelPad( 430, 460 );

	private JPanelPad pinCamposRestricoes = new JPanelPad( 430, 460 );

	private JPanelPad pinInfoPadImp = new JPanelPad( 300, 150 );

	private JPanelPad pinNavRestricoes = new JPanelPad( 680, 30 );

	private JPanelPad pinGeral = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private JPanelPad pnRestricoes = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private JPanelPad pinDetRestricoes = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private Tabela tbRestricoes = new Tabela();

	private JScrollPane spnRestricoes = new JScrollPane( tbRestricoes );

	private JTextFieldPad txtCodTipoMov = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtCodTipoMov2 = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtDescTipoMov = new JTextFieldPad( JTextFieldPad.TP_STRING, 40, 0 );

	private JTextFieldPad txtCodModNota = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtCodSerie = new JTextFieldPad( JTextFieldPad.TP_STRING, 4, 0 );

	private JTextFieldPad txtCodTab = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtEspecieTipomov = new JTextFieldPad( JTextFieldPad.TP_STRING, 4, 9 );

	private JTextFieldPad txtIDUsu = new JTextFieldPad( JTextFieldPad.TP_STRING, 8, 0 );

	private JTextFieldFK txtDescModNota = new JTextFieldFK( JTextFieldPad.TP_STRING, 30, 0 );

	private JTextFieldFK txtDescSerie = new JTextFieldFK( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtDescTab = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private JTextFieldFK txtDescTipoMov2 = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private JTextFieldFK txtNomeUsu = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtCodRegraComis = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtDescRegraComis = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );
	
	private JTextFieldPad txtCodTran = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtDescTran = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JLabelPad lbInfoPadImp = new JLabelPad( "   Padrões para fechamento de venda" );

	private JRadioGroup<?, ?> rgESTipoMov = null;

	private JRadioGroup<?, ?> rgTipoFrete = null;

	private JComboBoxPad cbTipoMov = null;

	private JCheckBoxPad chbRestritoTipoMov = new JCheckBoxPad( "Permitir todos os usuários?", "S", "N" );

	private JCheckBoxPad chbFiscalTipoMov = new JCheckBoxPad( "Sim", "S", "N" );

	private JCheckBoxPad chbEstoqTipoMov = new JCheckBoxPad( "Sim", "S", "N" );

	private JCheckBoxPad chbSomaTipoMov = new JCheckBoxPad( "Sim", "S", "N" );

	private JCheckBoxPad chbImpPedTipoMov = new JCheckBoxPad( "Sim", "S", "N" );

	private JCheckBoxPad chbImpNfTipoMov = new JCheckBoxPad( "Sim", "S", "N" );

	private JCheckBoxPad chbImpBolTipoMov = new JCheckBoxPad( "Sim", "S", "N" );

	private JCheckBoxPad chbImpRecTipoMov = new JCheckBoxPad( "Sim", "S", "N" );

	private JCheckBoxPad chbReImpNfTipoMov = new JCheckBoxPad( "Sim", "S", "N" );

	private JCheckBoxPad chbSeqNfTipoMov = new JCheckBoxPad( "Sim", "S", "N" );

	private JCheckBoxPad chbVlrMFinTipoMov = new JCheckBoxPad( "Permitir valor das parcelas menor que o total?", "S", "N" );

	private JCheckBoxPad chbMComisTipoMov = new JCheckBoxPad( "Múltiplos comissionados ?", "S", "N" );
	
	private Navegador navRestricoes = new Navegador( true );

	private ListaCampos lcRestricoes = new ListaCampos( this, "" );

	private ListaCampos lcUsu = new ListaCampos( this, "US" );

	private ListaCampos lcModNota = new ListaCampos( this, "MN" );

	private ListaCampos lcSerie = new ListaCampos( this, "SE" );

	private ListaCampos lcTab = new ListaCampos( this, "TB" );

	private ListaCampos lcTipoMov = new ListaCampos( this, "TM" );

	private ListaCampos lcTran = new ListaCampos( this, "TN" );
	
	private ListaCampos lcRegraComis = new ListaCampos( this, "RC" );

	private Vector<String> vVals = new Vector<String>();

	private Vector<String> vLabs = new Vector<String>();

	private boolean[] bPrefs = null;

	public FTipoMov() {

		super();
		setTitulo( "Cadastro de Tipos de Movimento" );
		setAtribos( 50, 40, 720, 450 );

		lcRestricoes.setMaster( lcCampos );
		lcCampos.adicDetalhe( lcRestricoes );
		lcRestricoes.setTabela( tbRestricoes );

		lcModNota.add( new GuardaCampo( txtCodModNota, "CodModNota", "Cód.mod.nota", ListaCampos.DB_PK, false ) );
		lcModNota.add( new GuardaCampo( txtDescModNota, "DescModNota", "Descrição do modelo de nota", ListaCampos.DB_SI, false ) );
		lcModNota.montaSql( false, "MODNOTA", "LF" );
		lcModNota.setQueryCommit( false );
		lcModNota.setReadOnly( true );
		txtCodModNota.setTabelaExterna( lcModNota );

		lcSerie.add( new GuardaCampo( txtCodSerie, "Serie", "Cód.serie", ListaCampos.DB_PK, false ) );
		lcSerie.add( new GuardaCampo( txtDescSerie, "DocSerie", "Nº. doc", ListaCampos.DB_SI, false ) );
		lcSerie.montaSql( false, "SERIE", "LF" );
		lcSerie.setQueryCommit( false );
		lcSerie.setReadOnly( true );
		txtCodSerie.setTabelaExterna( lcSerie );

		lcTab.add( new GuardaCampo( txtCodTab, "CodTab", "Cód.tb.pc.", ListaCampos.DB_PK, false ) );
		lcTab.add( new GuardaCampo( txtDescTab, "DescTab", "Descrição da tabela de preço", ListaCampos.DB_SI, false ) );
		lcTab.montaSql( false, "TABPRECO", "VD" );
		lcTab.setQueryCommit( false );
		lcTab.setReadOnly( true );
		txtCodTab.setTabelaExterna( lcTab );

		lcTipoMov.add( new GuardaCampo( txtCodTipoMov2, "CodTipoMov", "Cód.tp.mov.", ListaCampos.DB_PK, false ) );
		lcTipoMov.add( new GuardaCampo( txtDescTipoMov2, "DescTipoMov", "Descrição do tipo de movimento", ListaCampos.DB_SI, false ) );
		lcTipoMov.montaSql( false, "TIPOMOV", "EQ" );
		lcTipoMov.setQueryCommit( false );
		lcTipoMov.setReadOnly( true );
		txtCodTipoMov2.setTabelaExterna( lcTipoMov );

		lcUsu.add( new GuardaCampo( txtIDUsu, "IDUsu", "ID", ListaCampos.DB_PK, txtNomeUsu, false ) );
		lcUsu.add( new GuardaCampo( txtNomeUsu, "NomeUsu", "Nome nome do usuário", ListaCampos.DB_SI, false ) );
		lcUsu.montaSql( false, "USUARIO", "SG" );
		lcUsu.setQueryCommit( false );
		lcUsu.setReadOnly( true );
		txtIDUsu.setFK( true );
		txtIDUsu.setNomeCampo( "IDUsu" );
		txtIDUsu.setTabelaExterna( lcUsu );

		lcRegraComis.add( new GuardaCampo( txtCodRegraComis, "CodRegrComis", "Cód.rg.comis.", ListaCampos.DB_PK, txtDescRegraComis, false ) );
		lcRegraComis.add( new GuardaCampo( txtDescRegraComis, "DescRegrComis", "Descrição da regra do comissionado", ListaCampos.DB_SI, false ) );
		lcRegraComis.montaSql( false, "REGRACOMIS", "VD" );
		lcRegraComis.setQueryCommit( false );
		lcRegraComis.setReadOnly( true );
		txtCodRegraComis.setFK( true );
		txtCodRegraComis.setNomeCampo( "IDUsu" );
		txtCodRegraComis.setTabelaExterna( lcRegraComis );
		
		lcTran.add( new GuardaCampo( txtCodTran, "CodTran", "Cód.tran.", ListaCampos.DB_PK, false ) );
		lcTran.add( new GuardaCampo( txtDescTran, "RazTran", "Descrição da transportadora", ListaCampos.DB_SI, false ) );
		lcTran.montaSql( false, "TRANSP", "VD" );
		lcTran.setQueryCommit( false );
		lcTran.setReadOnly( true );
		txtCodTran.setTabelaExterna( lcTran );

		cbTipoMov = new JComboBoxPad( vLabs, vVals, JComboBoxPad.TP_STRING, 2, 0 );
		
		Vector<String> vValsES = new Vector<String>();
		Vector<String> vLabsES = new Vector<String>();

		vLabsES.addElement( "Entrada" );
		vLabsES.addElement( "Saída" );
		vLabsES.addElement( "Inventário" );
		vValsES.addElement( "E" );
		vValsES.addElement( "S" );
		vValsES.addElement( "I" );
		rgESTipoMov = new JRadioGroup<String, String>( 1, 3, vLabsES, vValsES );
		rgESTipoMov.addRadioGroupListener( this );

		Vector<String> vValsTF = new Vector<String>();
		Vector<String> vLabsTF = new Vector<String>();
		vLabsTF.addElement( "CIF" );
		vLabsTF.addElement( "FOB" );
		vValsTF.addElement( "C" );
		vValsTF.addElement( "F" );
		rgTipoFrete = new JRadioGroup<String, String>( 2, 1, vLabsTF, vValsTF );
		rgTipoFrete.setAtivo( false );

		montaCbTipoMov( "E" );

		txtCodTipoMov2.setNomeCampo( "CodTipoMov" );

		pinGeral.setPreferredSize( new Dimension( 430, 560 ) );
		pinGeral.add( pinCamposGeral, BorderLayout.CENTER );

		setPainel( pinCamposGeral );

		adicTab( "Geral", pinCamposGeral );

		adicCampo( txtCodTipoMov, 7, 20, 80, 20, "CodTipoMov", "Cód.tp.mov.", ListaCampos.DB_PK, true );
		adicCampo( txtDescTipoMov, 90, 20, 250, 20, "DescTipoMov", "Descrição do tipo de movimento", ListaCampos.DB_SI, true );
		adicCampo( txtCodModNota, 343, 20, 80, 20, "CodModNota", "Cód.mod.nota", ListaCampos.DB_FK, true );
		adicDescFK( txtDescModNota, 426, 20, 250, 20, "DescModNota", "Descrição do modelo de nota" );
		adicCampo( txtCodSerie, 7, 60, 80, 20, "Serie", "Série", ListaCampos.DB_FK, txtDescSerie, true );
		adicDescFK( txtDescSerie, 90, 60, 250, 20, "DocSerie", "Documento atual" );
		adicCampo( txtCodTab, 343, 60, 80, 20, "CodTab", "Cód.tp.pc.", ListaCampos.DB_FK, txtDescTab, false );
		adicDescFK( txtDescTab, 426, 60, 250, 20, "DescTab", "Descrição da tab. de preços" );
		adicCampo( txtCodTipoMov2, 7, 100, 80, 20, "CodTipoMovTM", "Cód.mov.nf.", ListaCampos.DB_FK, txtDescTipoMov2, false );
		adicDescFK( txtDescTipoMov2, 90, 100, 250, 20, "DescTipoMov", "Descrição do movimento para nota." );
		adicDB( rgESTipoMov, 343, 100, 333, 30, "ESTipoMov", "Fluxo", true );
		adicDB( rgTipoFrete, 7, 140, 90, 50, "CTipoFrete", "Tipo frete", false );
		adicDB( chbFiscalTipoMov, 107, 160, 60, 20, "FiscalTipoMov", "Fiscal", true );
		adicDB( chbEstoqTipoMov, 178, 160, 60, 20, "EstoqTipoMov", "Estoque", true );
		adicDB( chbSomaTipoMov, 268, 160, 70, 20, "SomaVdTipoMov", "Financeiro", true );
		adicDB( cbTipoMov, 343, 155, 250, 30, "TipoMov", "Tipo de movimento", true );
		adicCampo( txtEspecieTipomov, 596, 160, 80, 24, "EspecieTipomov", "Espécie", ListaCampos.DB_SI, true );
		adicDB( chbImpPedTipoMov, 16, 230, 55, 20, "ImpPedTipoMov", "Imp.ped.", true );
		adicDB( chbImpNfTipoMov, 74, 230, 55, 20, "ImpNfTipoMov", "Imp.NF", true );
		adicDB( chbImpBolTipoMov, 132, 230, 55, 20, "ImpBolTipoMov", "Imp.bol.", true );
		adicDB( chbImpRecTipoMov, 190, 230, 55, 20, "ImpRecTipoMov", "Imp.rec.", true );
		adicDB( chbReImpNfTipoMov, 248, 230, 55, 20, "ReImpNfTipoMov", "Reimp.NF", true );
		adicDB( chbSeqNfTipoMov, 306, 230, 70, 20, "SeqNfTipoMov", "Aloca Nº.NF", true );
		adicDB( chbVlrMFinTipoMov, 390, 200, 300, 20, "VlrMFinTipoMov", "", true );
		adicDB( chbRestritoTipoMov, 390, 220, 240, 20, "TUSUTIPOMOV", "", true );
		adicDB( chbMComisTipoMov, 390, 240, 240, 20, "MComisTipoMov", "", true );
		
		adicCampo( txtCodRegraComis, 7, 280, 80, 20, "CodRegrComis", "Cód.rg.comis.", ListaCampos.DB_FK, false );
		adicDescFK( txtDescRegraComis, 90, 280, 250, 20, "DescRegrComis", "Descrição da regra de comissionado" );
		
		adicCampo( txtCodTran, 7, 320, 80, 20, "CodTran", "Cód.tran.", ListaCampos.DB_FK, false );
		adicDescFK( txtDescTran, 90, 320, 250, 20, "DescTran", "Descrição da transportadora" );
		
		txtCodRegraComis.setAtivo( false );

		lbInfoPadImp.setOpaque( true );
		adic( lbInfoPadImp, 15, 193, 230, 15 );
		adic( pinInfoPadImp, 7, 200, 380, 60 );
		chbRestritoTipoMov.addCheckBoxListener( this );
		chbMComisTipoMov.addCheckBoxListener( this );

		// pinLbPadImp.adic(lbInfoPadImp, 0, 0, 220, 15);
		// pinLbPadImp.tiraBorda();

		setListaCampos( true, "TIPOMOV", "EQ" );
		lcCampos.setQueryInsert( false );

		setPainel( pinDetRestricoes, pnRestricoes );

		pinDetRestricoes.setPreferredSize( new Dimension( 430, 80 ) );
		pinDetRestricoes.add( pinNavRestricoes, BorderLayout.SOUTH );
		pinDetRestricoes.add( pinCamposRestricoes, BorderLayout.CENTER );

		setListaCampos( lcRestricoes );
		setNavegador( navRestricoes );

		pnRestricoes.add( pinDetRestricoes, BorderLayout.SOUTH );
		pnRestricoes.add( spnRestricoes, BorderLayout.CENTER );
		pinNavRestricoes.adic( navRestricoes, 0, 0, 270, 25 );

		setPainel( pinCamposRestricoes );

		adicCampo( txtIDUsu, 7, 20, 80, 20, "IdUsu", "Id", ListaCampos.DB_PF, txtNomeUsu, true );
		adicDescFK( txtNomeUsu, 90, 20, 250, 20, "NomeUsu", " e nome do usuário" );

		setListaCampos( true, "TIPOMOVUSU", "EQ" );
		lcRestricoes.setQueryInsert( false );
		lcRestricoes.setQueryCommit( false );
		lcRestricoes.montaTab();

		txtCodTipoMov.setTabelaExterna( lcRestricoes );

		tbRestricoes.setTamColuna( 80, 0 );
		tbRestricoes.setTamColuna( 280, 1 );
		lcCampos.addInsertListener( this );

	}

	public void beforeInsert( InsertEvent ievt ) {

	}

	public void afterInsert( InsertEvent ievt ) {

		if ( !bPrefs[ 0 ] ) {
			chbEstoqTipoMov.setVlrString( "N" );
		}
		if ( bPrefs[ 1 ] ) {
			chbMComisTipoMov.setVlrString( "S" );
		} else {
			chbMComisTipoMov.setVlrString( "N" );
		}

	}

	public void beforePost( PostEvent pevt ) {

		if ( txtCodRegraComis.getAtivo() && txtCodRegraComis.getVlrInteger()==0 ) {
			Funcoes.mensagemErro( this, "Campo Cód.rg.comis. é requerido!" );
			pevt.cancela();
		}
		
		super.beforePost(pevt);
	}

	private void montaCbTipoMov( String ES ) {

		cbTipoMov.limpa();
		vLabs.clear();
		vVals.clear();
		vLabs.addElement( "<--Selecione-->" );
		vVals.addElement( "" );
		if ( ES.equals( "E" ) ) {
			vLabs.addElement( "Orçamento (compra)" );
			vVals.addElement( "OC" );
			vLabs.addElement( "Pedido (compra)" );
			vVals.addElement( "PC" );
			vLabs.addElement( "Compra" );
			vVals.addElement( "CP" );
			vLabs.addElement( "Cancelamento" );
			vVals.addElement( "CC" );
			vLabs.addElement( "Ordem de produção" );
			vVals.addElement( "OP" );
			vLabs.addElement( "Devolução" );
			vVals.addElement( "DV" );		
			vLabs.addElement( "Conhecimento de frete" );
			vVals.addElement( "CF" );

		}
		else if ( ES.equals( "S" ) ) {
			vLabs.addElement( "Orçamento (venda)" );
			vVals.addElement( "OV" );
			vLabs.addElement( "Pedido (venda)" );
			vVals.addElement( "PV" );
			vLabs.addElement( "Venda" );
			vVals.addElement( "VD" );
			vLabs.addElement( "Serviço" );
			vVals.addElement( "SE" );
			vLabs.addElement( "Venda - ECF" );
			vVals.addElement( "VE" );
			vLabs.addElement( "Venda - televendas" );
			vVals.addElement( "VT" );
			vLabs.addElement( "Bonificação" );
			vVals.addElement( "BN" );
			vLabs.addElement( "Devolução" );
			vVals.addElement( "DV" );
			vLabs.addElement( "Transferência" );
			vVals.addElement( "TR" );
			vLabs.addElement( "Perda" );
			vVals.addElement( "PE" );
			vLabs.addElement( "Consignação - saída" );
			vVals.addElement( "CS" );
			vLabs.addElement( "Consignação - devolução" );
			vVals.addElement( "CE" );
			vLabs.addElement( "Cancelamento" );
			vVals.addElement( "CC" );
			vLabs.addElement( "Requisição de material" );
			vVals.addElement( "RM" );
			vLabs.addElement( "NF Complementar" );
			vVals.addElement( "CO" );
		}
		else if ( ES.equals( "I" ) ) {
			vLabs.addElement( "Inventário" );
			vVals.addElement( "IV" );
		}
		cbTipoMov.setItens( vLabs, vVals );

	}

	public void setConexao( DbConnection cn ) {

		super.setConexao( cn );
		lcTipoMov.setConexao( cn );
		lcModNota.setConexao( cn );
		lcSerie.setConexao( cn );
		lcTab.setConexao( cn );
		lcRestricoes.setConexao( cn );
		lcUsu.setConexao( cn );
		lcRegraComis.setConexao( cn );
		lcTran.setConexao( cn );
		bPrefs = prefs();
		chbEstoqTipoMov.setEnabled( bPrefs[ 0 ] ); // Habilita controle de estoque de acordo com o preferências
	}

	public void valorAlterado( RadioGroupEvent evt ) {

		if ( rgESTipoMov.getVlrString().equals( "S" ) )
			rgTipoFrete.setAtivo( true );
		else
			rgTipoFrete.setAtivo( false );
		montaCbTipoMov( rgESTipoMov.getVlrString() );
	}

	public void valorAlterado( CheckBoxEvent evt ) {

		if ( evt.getCheckBox() == chbRestritoTipoMov ) {
			if ( evt.getCheckBox().isSelected() ) {
				removeTab( "Restrições de Usuário", pnRestricoes );
			}
			else {
				adicTab( "Restrições de Usuário", pnRestricoes );
			}
		}
		else if ( evt.getCheckBox() == chbMComisTipoMov ) {
			
			if ( bPrefs[1] ) {
				chbMComisTipoMov.setEnabled( true );
				txtCodRegraComis.setRequerido( evt.getCheckBox().isSelected() );
				txtCodRegraComis.setAtivo( evt.getCheckBox().isSelected() );
				if ( ! evt.getCheckBox().isSelected()  ) {
					txtCodRegraComis.setVlrInteger( 0 );
					lcRegraComis.carregaDados();
				}
			}
			else {
				chbMComisTipoMov.setSelected( false );
				chbMComisTipoMov.setEnabled( false );
			}
		}

	}

	private boolean[] prefs() {

		boolean[] bRetorno = new boolean[ 2 ];
		String sSQL = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			sSQL = "SELECT CONTESTOQ, MULTICOMIS FROM SGPREFERE1 WHERE CODEMP=? AND CODFILIAL=?";
			ps = con.prepareStatement( sSQL );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "SGPREFERE1" ) );
			rs = ps.executeQuery();
			bRetorno[ 0 ] = true;
			if ( rs.next() ) {
				bRetorno[ 0 ] = true;
				if ( "S".equals(rs.getString( "CONTESTOQ" )) ) {
					bRetorno[ 0 ] = true;
				} else {
					bRetorno[ 0 ] = false;
				}
				if ( "S".equals(rs.getString( "MULTICOMIS" )) ) {
					bRetorno[ 1 ] = true;
				} else {
					bRetorno[ 1 ] = false;
				}
			}
			rs.close();
			ps.close();
			con.commit();
		} catch ( SQLException err ) {
			Funcoes.mensagemErro( this, "Erro ao carregar a tabela PREFERE1!\n" + err.getMessage(), true, con, err );
		} finally {
			rs = null;
			ps = null;
		}
		return bRetorno;
	}

}
