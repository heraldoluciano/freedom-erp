/**
 * @version 16/50/2007 <BR>
 * @author Setpoint Informática Ltda./Alex Rodrigues <BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.std <BR>
 * Classe:
 * @(#)FHistPad.java <BR>
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
 * Tela para cadastro de historicos padrão.
 * 
 */

package org.freedom.modulos.crm.view.frame.crud.plain;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;

import org.freedom.acao.CarregaEvent;
import org.freedom.acao.CarregaListener;
import org.freedom.acao.InsertEvent;
import org.freedom.acao.InsertListener;
import org.freedom.acao.JComboBoxEvent;
import org.freedom.acao.JComboBoxListener;
import org.freedom.bmps.Icone;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.persistence.GuardaCampo;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.component.JComboBoxPad;
import org.freedom.library.swing.component.JLabelPad;
import org.freedom.library.swing.component.JPanelPad;
import org.freedom.library.swing.component.JTextAreaPad;
import org.freedom.library.swing.component.JTextFieldFK;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.frame.Aplicativo;
import org.freedom.library.swing.frame.FDados;
import org.freedom.modulos.crm.business.object.Chamado;
import org.freedom.modulos.crm.business.object.Prioridade;

public class FChamado extends FDados implements ActionListener, JComboBoxListener, InsertListener, CarregaListener {

	private static final long serialVersionUID = 1L;

	private JTextFieldPad txtCodChamado = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtDescChamado = new JTextFieldPad( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtCodCli = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	
	private JTextFieldFK txtRazCli = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );
	
	private JTextFieldPad txtNomeCli = new JTextFieldPad( JTextFieldPad.TP_STRING, 50, 0 );
	
	private JTextFieldPad txtContCli = new JTextFieldPad( JTextFieldPad.TP_STRING, 40, 0 );
	
	private JTextFieldPad txtSolicitante = new JTextFieldPad( JTextFieldPad.TP_STRING, 50, 0 );
	
	private JTextFieldPad txtDtChamado = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );
	
	private JTextFieldPad txtDtPrevisao = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );
	
	private JTextFieldPad txtDtConclusao = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );
	
	private JTextFieldPad txtQtdHorasPrev = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 4, Aplicativo.casasDec );

	private JTextFieldPad txtStatus = new JTextFieldPad( JTextFieldPad.TP_STRING, 2, 0 );
	
	private JTextFieldPad txtCodTpChamado = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 4, 0 );
	
	private JTextFieldPad txtCodAtend = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	
	private JTextFieldFK txtNomeAtend = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );
	
	private final JTextAreaPad txaDetChamado = new JTextAreaPad( 1000 );
	
	private final JTextAreaPad txaObsChamado = new JTextAreaPad( 1000 );
	
	private final JScrollPane spnDetChamado = new JScrollPane( txaDetChamado );
	
	private final JScrollPane spnObsChamado = new JScrollPane( txaObsChamado );
	
	private JPanelPad panelGeral = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );
	
	private JPanelPad panelCabecalho = new JPanelPad( 700, 180 );
	
	private final JPanelPad pinCab = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );
	
	private JPanelPad panelDetalhamento = new JPanelPad( JPanelPad.TP_JPANEL, new GridLayout( 2, 1 ) );
	
	private JPanelPad panelTxa = new JPanelPad( JPanelPad.TP_JPANEL, new GridLayout( 2, 1 ) );
	
	private JComboBoxPad cbTpChamado = new JComboBoxPad( null, null, JComboBoxPad.TP_INTEGER, 4, 0 );	
	
	private JComboBoxPad cbStatus = new JComboBoxPad( null, null, JComboBoxPad.TP_STRING, 2, 0 );
	
	private JComboBoxPad cbPrioridade = new JComboBoxPad( null, null, JComboBoxPad.TP_INTEGER, 4, 0 );
	
	private JLabelPad lbBanner = new JLabelPad( Icone.novo( "bannerChamado.png" ) );
	
	private ListaCampos lcCli = new ListaCampos( this, "CL" );
	
	private ListaCampos lcTipoChamado = new ListaCampos( this, "TC" );
	
	private ListaCampos lcAtend = new ListaCampos( this, "AE" );

	public FChamado() {

		super();
		setTitulo( "Cadastro de chamados" );
		setAtribos( 50, 50, 640, 540 );
		montaListaCampos();
		montaCombos();
		montaTela();
				
		cbTpChamado.addComboBoxListener( this );
		cbStatus.addComboBoxListener( this );
		
		lcCampos.addInsertListener( this );
		lcCli.addCarregaListener( this );
		
	}
	
	private void montaTela() {

		pinCab.add( lbBanner, BorderLayout.NORTH );
		pinCab.add( panelGeral, BorderLayout.CENTER );
		panelGeral.add( panelCabecalho, BorderLayout.NORTH );
		
		setPainel( panelCabecalho );
				
		adicCampo( txtCodChamado, 7, 20, 80, 20, "CodChamado", "Cód.Chamado", ListaCampos.DB_PK, true );
		adicCampo( txtDescChamado, 90, 20, 340, 20, "DescChamado", "Descrição do chamado", ListaCampos.DB_SI, true );

		adicCampoInvisivel( txtCodTpChamado, "CodTpChamado", "" ,  ListaCampos.DB_FK, false );
		
		adicDB( cbTpChamado, 433, 20, 180, 20, "CodTpChamado", "Tipo de chamado", false );
		
		adicCampo( txtCodCli, 7, 60, 80, 20, "CodCli", "Cód.Cli.", ListaCampos.DB_FK, txtRazCli, true );
		adicDescFK( txtRazCli, 90, 60, 340, 20, "RazCli", "Razão social do cliente" );
		adicCampo( txtSolicitante, 433, 60, 180, 20, "Solicitante", "Solicitante", ListaCampos.DB_SI, true );
		
		adicCampo( txtDtChamado, 7, 100, 80, 20, "DtChamado", "Dt.Abertura", ListaCampos.DB_SI, true );
		adicCampo( txtDtPrevisao, 90, 100, 80, 20, "DtPrevisao", "Dt.Previsão", ListaCampos.DB_SI, true );
		adicCampo( txtQtdHorasPrev, 173, 100, 60, 20, "QtdHorasPrevisao", "Qtd.Prev.", ListaCampos.DB_SI, true );

		adicDB( cbPrioridade, 236, 100, 110, 20, "prioridade", "Prioridade", false );
		
		adicCampo( txtDtConclusao, 349, 100, 80, 20, "DtConclusao", "Dt.Conclusão", ListaCampos.DB_SI, false );
		
		adicDB( cbStatus, 433, 100, 180, 20, "Status", "Status", false );

		adicCampo( txtCodAtend, 7, 140, 80, 20, "CodAtend", "Cód.Atend.", ListaCampos.DB_FK, txtNomeAtend, false );
		adicDescFK( txtNomeAtend, 90, 140, 250, 20, "NomeAtend", "Nome do atendente designado" );
		
		txtDtConclusao.setEditable( false );
		
		adicDBLiv( txaDetChamado, "DetChamado", "Detalhamamento", false );		
		adicDBLiv( txaObsChamado, "ObsChamado", "Observações", false );
		
		setListaCampos( true, "CHAMADO", "CR" );

		panelGeral.add( panelDetalhamento, BorderLayout.CENTER );
		panelDetalhamento.add(spnDetChamado);
		panelDetalhamento.add(spnObsChamado);
		
		spnDetChamado.setBorder( BorderFactory.createTitledBorder( "Detalhamento" ) );
		spnObsChamado.setBorder( BorderFactory.createTitledBorder( "Observações" ) );
		
		this.add( pinCab );
		
	}
	
	private void montaListaCampos() {
		// FK Cliente
		lcCli.add( new GuardaCampo( txtCodCli, "CodCli", "Cód.cli.", ListaCampos.DB_PK, false ) );
		lcCli.add( new GuardaCampo( txtRazCli, "RazCli", "Razão social do cliente", ListaCampos.DB_SI, false ) );
		lcCli.add( new GuardaCampo( txtNomeCli, "NomeCli", "Nome do cliente", ListaCampos.DB_SI, false ) );
		lcCli.add( new GuardaCampo( txtContCli, "ContCli", "Contato", ListaCampos.DB_SI, false ) );

		lcCli.montaSql( false, "CLIENTE", "VD" );
		lcCli.setQueryCommit( false );
		lcCli.setReadOnly( true );
		txtCodCli.setTabelaExterna( lcCli );
		
		// FK Tipo de chamado
		lcTipoChamado.add( new GuardaCampo( txtCodTpChamado, "CodTpChamado", "Cód.Tp.Chamado", ListaCampos.DB_PK, false ) );

		lcTipoChamado.montaSql( false, "TIPOCHAMADO", "CR" );
		lcTipoChamado.setQueryCommit( false );
		lcTipoChamado.setReadOnly( true );
		txtCodTpChamado.setTabelaExterna( lcTipoChamado );
		txtCodTpChamado.setFK( true );

		// FK para Atendente/Técnico designado
		txtCodAtend.setTabelaExterna( lcAtend );
		txtCodAtend.setFK( true );
		txtCodAtend.setNomeCampo( "CodAtend" );
		lcAtend.add( new GuardaCampo( txtCodAtend, "CodAtend", "Cód.atend.", ListaCampos.DB_PK, false ), "txtCodVendx" );
		lcAtend.add( new GuardaCampo( txtNomeAtend, "NomeAtend", "Nome", ListaCampos.DB_SI, false ), "txtCodVendx" );
		lcAtend.montaSql( false, "ATENDENTE", "AT" );
		lcAtend.setReadOnly( true );


	}
	
	private void montaCombos() {
		
		cbTpChamado.setAutoSelect( "codtpchamado", "desctpchamado", "crtipochamado" );		
		cbTpChamado.carregaValores();
		
		cbPrioridade.setItens( Prioridade.getLabels( ), Prioridade.getValores( ) );
		
		cbStatus.setItens( Chamado.getLabels( ), Chamado.getValores( ) );
		
	}
	
	public void exec( int codchamado ) {
		txtCodChamado.setVlrInteger( codchamado );
		lcCampos.carregaDados();
	}

	public void setConexao( DbConnection cn ) {

		super.setConexao( cn );
	
		lcCli.setConexao( cn );
		lcTipoChamado.setConexao( cn );
		lcAtend.setConexao( cn );
	
	}
	
	public void valorAlterado( JComboBoxEvent evt ) {

		if ( evt.getComboBoxPad() == cbTpChamado ) {
			if ( cbTpChamado.getVlrInteger() > 0 ) {
				txtCodTpChamado.setVlrInteger( cbTpChamado.getVlrInteger() );
				lcTipoChamado.carregaDados();
				
			}
			else {
				txtCodTpChamado.setVlrInteger( null );
			}
		}
		else if ( evt.getComboBoxPad() == cbStatus ) {
			if(cbStatus.getVlrString().equals( Chamado.STATUS_CONCLUIDO.getValue() ) && txtDtConclusao.getVlrDate()==null ) {
				txtDtConclusao.setVlrDate( new Date() );
				txtDtConclusao.setEditable( true );
			}
		}
		
	}
	
	public void beforeInsert( InsertEvent ievt ) {
		
	}

	public void afterInsert( InsertEvent ievt ) {

		txtDtChamado.setVlrDate( new Date() );
		txtDtPrevisao.setVlrDate( new Date() );
		txtQtdHorasPrev.setVlrInteger( new Integer(1) );
		cbStatus.setVlrString( "PE" );
		
		
	}

	public void afterCarrega( CarregaEvent cevt ) {

		if(cevt.getListaCampos()==lcCli && lcCampos.getStatus()==ListaCampos.LCS_INSERT) {
			
			txtSolicitante.setVlrString( txtContCli.getVlrString() );
			
		}
		
	}

	public void beforeCarrega( CarregaEvent cevt ) {

		// TODO Auto-generated method stub
		
	}

	
	

}
