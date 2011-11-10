/**
 * @version 09/11/2011 <BR>
 * @author Setpoint Informática Ltda./Bruno Nascimento <BR>
 * 
 *         Projeto: Freedom <BR>
 * 
 *         Pacote: org.freedom.modulos.crm.view.frame.utility; <BR>
 *         Classe: @(#)FGestaoProj.java <BR>
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
 *         Tela para gestão de projetos.
 * 
 */

package org.freedom.modulos.crm.view.frame.utility;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Vector;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import org.freedom.acao.CarregaEvent;
import org.freedom.acao.CarregaListener;
import org.freedom.bmps.Icone;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.functions.Funcoes;
import org.freedom.library.persistence.GuardaCampo;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.component.JButtonPad;
import org.freedom.library.swing.component.JLabelPad;
import org.freedom.library.swing.component.JPanelPad;
import org.freedom.library.swing.component.JTabbedPanePad;
import org.freedom.library.swing.component.JTablePad;
import org.freedom.library.swing.component.JTextFieldFK;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.frame.Aplicativo;
import org.freedom.library.swing.frame.FFilho;
import org.freedom.library.swing.util.SwingParams;
import org.freedom.modulos.crm.business.component.Constant;
import org.freedom.modulos.crm.business.object.Contrato;
import org.freedom.modulos.crm.business.object.ContratoVW.EColContr;
import org.freedom.modulos.crm.dao.DAOGestaoProj;
import org.freedom.modulos.crm.view.frame.crud.detail.FContrato;

public class FGestaoProj extends FFilho implements CarregaListener, ActionListener {

	private static final long serialVersionUID = 1L;
	
	//Paineis
	private JPanelPad pnDetail = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );
	
	private JPanelPad pnContr = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );
	
	private JTabbedPanePad tabAbas = new JTabbedPanePad();
	
	private JPanelPad pinCab = new JPanelPad( 700, 150 );
	
	private JPanelPad pinNav = new JPanelPad (  JPanelPad.TP_JPANEL, new GridLayout( 1, 2 ) );
	
	//Geral
	private JTablePad tabContr = new JTablePad();
	
	private JScrollPane scpContr = new JScrollPane( tabContr );
	
	private JPanelPad pnGridAtd = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private JLabelPad lbStatus = new JLabelPad();
	
	private JLabelPad lbTpProj = new JLabelPad(); 
	
	private JTextFieldPad txtCodContr = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 4, 0 );

	private JTextFieldFK txtDescContr = new JTextFieldFK( JTextFieldFK.TP_STRING, 80, 0 );
	
	private JTextFieldFK txtCodCli = new JTextFieldFK( JTextFieldFK.TP_INTEGER, 5, 0 );

	private JTextFieldFK txtRazCli = new JTextFieldFK( JTextFieldFK.TP_STRING, 40, 0 );

	private JTextFieldFK txtDtInicio = new JTextFieldFK( JTextFieldFK.TP_DATE, 10, 0 );

	private JTextFieldFK txtDtFim = new JTextFieldFK( JTextFieldFK.TP_DATE, 10, 0 );
	
	private JTextFieldFK txtTpContr = new JTextFieldFK( JTextFieldFK.TP_STRING, 1, 0 );
	
	private JTextFieldFK txtSitContr = new JTextFieldFK( JTextFieldFK.TP_STRING, 2, 0 );
	
	//Botões

	private JButtonPad btNovoChamado = new JButtonPad( Icone.novo( "btNovo.gif" ) );

	private JButtonPad btEditar = new JButtonPad( Icone.novo( "btEditar.gif" ) );
	
	private JButtonPad btExcluir = new JButtonPad( Icone.novo( "btExcluir.gif" ) );

	private JButtonPad btSair = new JButtonPad( "Sair", Icone.novo( "btSair.gif" ) );

	private JButtonPad btImprimir = new JButtonPad( Icone.novo( "btPrevimp.gif" ) );
	
	private JButtonPad  btPrimeiro = new JButtonPad( Icone.novo( "btPrim.gif" ) );
	
	private JButtonPad 	btAnterior = new JButtonPad( Icone.novo( "btAnt.gif" ) );
	
	private JButtonPad  btProximo= new JButtonPad( Icone.novo( "btProx.gif" ) );
	
	private JButtonPad  btUltimo = new JButtonPad( Icone.novo( "btUlt.gif" ) );
	
	private JButtonPad  btGerar = new JButtonPad( Icone.novo( "btGerar.gif" ) );
	
	//Lista Campos
	
	private ListaCampos lcCliente = new ListaCampos( this, "CL" );
	
	private ListaCampos lcContrato = new ListaCampos( this );
	
	//DAOGestaoProj
	
	private DAOGestaoProj daogestao = null;
	
	public FGestaoProj() {

		super( false );

		setTitulo( "Gestão de Projetos" );
		setAtribos( 20, 20, 780, 650 );
		montaListaCampos();
		montaTela();
		carregaListener();

	}
	
	private void montaTela(){
		
		getTela().add( pnCliente , BorderLayout.CENTER );
		pnCliente.add( pinCab, BorderLayout.NORTH );
	
		
		// ***** Cabeçalho
		
		pinCab.adic( txtCodContr, 7, 20, 80, 20, "Cód.proj" );
		pinCab.adic( txtDescContr, 90, 20, 668, 20, "Descrição do contrato/projeto" );
		pinCab.adic( txtCodCli, 7,60 , 80, 20, "Cód.cli."  );
		pinCab.adic( txtRazCli, 90, 60, 502, 20, "Descrição do cliente" );
		pinCab.adic( txtDtInicio, 595, 60, 80, 20, "Dt.ini." );
		pinCab.adic( txtDtFim, 678, 60, 80, 20, "Dt.fin." );
		pinCab.adic( lbTpProj, 7, 90, 100, 20 );
		pinCab.adic( lbStatus, 110, 90, 100, 20 );
		pinCab.adic( btGerar, 213, 85, 30, 30 );

		// ***** Grid

		pnCliente.add( pnDetail, BorderLayout.CENTER );
		pnDetail.add( tabAbas );
		tabAbas.addTab( "Contratos", pnContr );
		// tabbedDetail.addTab( "Receber", panelReceber );
		// tabbedDetail.addTab( "Histórico", panelHistorico );

		// ***** Venda
		montaGridContr();
		
		
		pnContr.add( scpContr, BorderLayout.CENTER );
		
		// ***** Rodapé
		
		pnCliente.add( pnRodape, BorderLayout.SOUTH );
		adicNavegador();
		pnRodape.add( adicBotaoSair() );
		setNaoSalvo();
	}
	
	private void montaGridContr(){
		
		tabContr.adicColuna( "Indice" );
		tabContr.adicColuna( "Tipo" );
		tabContr.adicColuna( "Descrição" );
		tabContr.adicColuna( "Cód.Contr." );
		tabContr.adicColuna( "Cod.it.Contr." );
		tabContr.adicColuna( "Cód.Tarefa" );
		tabContr.adicColuna( "Cód.Sub.Tarefa" );
		
		tabContr.setTamColuna( 40, EColContr.INDICE.ordinal() );
		tabContr.setTamColuna( 40, EColContr.TIPO.ordinal() );
		tabContr.setTamColuna( 300, EColContr.DESCRICAO.ordinal() );
		tabContr.setTamColuna( 80, EColContr.CODCONTR.ordinal() );
		tabContr.setTamColuna( 80, EColContr.CODITCONTR.ordinal() );
		tabContr.setTamColuna( 80, EColContr.CODTAREFA.ordinal() );
		tabContr.setTamColuna( 80, EColContr.CODTAREFAST.ordinal() );
		
	}
	
	private void montaListaCampos(){
		
		
		/*************
		 * CLIENTE * *
		 **********/
		lcCliente.add( new GuardaCampo( txtCodCli, "CodCli", "Cód.cli.", ListaCampos.DB_PK, false ) );
		lcCliente.add( new GuardaCampo( txtRazCli, "RazCli", "Razão social do cliente", ListaCampos.DB_SI, false ) );
		txtCodCli.setTabelaExterna( lcCliente, null );
		txtCodCli.setNomeCampo( "CodCli" );
		txtCodCli.setFK( true );
		lcCliente.setReadOnly( true );
		lcCliente.montaSql( false, "CLIENTE", "VD" );
		
		/***************
		 * 	CONTRATO  * *
		 **************/

		lcContrato.add( new GuardaCampo( txtCodContr, "CodContr", "Cód.Contr.", ListaCampos.DB_PK, true ) );
		lcContrato.add( new GuardaCampo( txtDescContr, "DescContr", "Descrição do contrato", ListaCampos.DB_SI, false ) );
		lcContrato.add( new GuardaCampo( txtDtInicio, "DtInicio", "Dt.Ini.", ListaCampos.DB_SI, false ) );
		lcContrato.add( new GuardaCampo( txtDtFim, "DtFim", "Dt.Fim", ListaCampos.DB_SI, false ) );
		lcContrato.add( new GuardaCampo( txtCodCli, "CodCli", "Cód.Cli.", ListaCampos.DB_FK, txtRazCli, false ) );
		lcContrato.add( new GuardaCampo( txtTpContr, "TpContr", "Tp.Contr.", ListaCampos.DB_SI, false ) );
		lcContrato.add( new GuardaCampo( txtSitContr, "SitContr", "Sit.Contr.", ListaCampos.DB_SI, false ) );
		lcContrato.setDinWhereAdic( " TPCONTR NOT IN('S') ", txtTpContr);
		txtCodContr.setTabelaExterna( lcContrato, FContrato.class.getCanonicalName() );
		txtCodContr.setFK( true );
		lcContrato.setReadOnly( true );
		lcContrato.montaSql( false, "CONTRATO", "VD" );
		
	}
	
	private void setNaoSalvo(){
		
		lbStatus.setForeground( Color.WHITE );
		lbStatus.setBackground( Color.BLACK );
		lbStatus.setFont( SwingParams.getFontboldmed() );
		lbStatus.setHorizontalAlignment( SwingConstants.CENTER );
		lbStatus.setOpaque( true );
		lbStatus.setText( "NÃO SALVO" );
		
		lbTpProj.setForeground( Color.WHITE );
		lbTpProj.setBackground( Color.BLACK );
		lbTpProj.setFont( SwingParams.getFontboldmed() );
		lbTpProj.setHorizontalAlignment( SwingConstants.CENTER );
		lbTpProj.setOpaque( true );
		lbTpProj.setText( "NÃO SALVO" );
	}
	
	private void carregaListener(){
		lcContrato.addCarregaListener( this );
		btGerar.addActionListener( this );
	}

	private void setSitcontr() {
		String statusProj = txtSitContr.getVlrString().trim();
		Vector<Constant> listaSit = Contrato.getListSitproj();
		Constant item = null;
		for (int i=0; i<listaSit.size(); i++) {
			item = listaSit.elementAt( i );
			if (statusProj.equals( item.getValue())) {
				lbStatus.setBackground( item.getBgcolor() );
				lbStatus.setForeground( item.getFgcolor() );
				lbStatus.setText( item.getName() );
				break;
			}
		}
	}
	
	
	private void adicNavegador(){
	
		pnRodape.add( pinNav, BorderLayout.WEST );
		
		pinNav.setPreferredSize( new Dimension( 260, 30 ) );
		pinNav.add( btPrimeiro );
		pinNav.add( btAnterior );
		pinNav.add( btProximo );
		pinNav.add( btUltimo );
		pinNav.add( btNovoChamado );
		pinNav.add( btExcluir );
		pinNav.add( btEditar );
		pinNav.add( btImprimir );

		btNovoChamado.setToolTipText(  "Novo" );
		btExcluir.setToolTipText( "Excluir" );
		btEditar.setToolTipText( "Editar" );
		btImprimir.setToolTipText( "Imprimir" );
	}
	
	private void setTpProjcontr() {
		String statusProj = txtTpContr.getVlrString().trim();
		Vector<Constant> listaSit = Contrato.getListTpproj();
		Constant item = null;
		for (int i=0; i<listaSit.size(); i++) {
			item = listaSit.elementAt( i );
			if (statusProj.equals( item.getValue())) {
				lbTpProj.setBackground( item.getBgcolor() );
				lbTpProj.setForeground( item.getFgcolor() );
				lbTpProj.setText( item.getName() );
				break;
			}
		}
	}
	
	public void afterCarrega( CarregaEvent cevt ) {
		
		if (cevt.getListaCampos()== lcContrato) {
			if("".equals( txtCodContr.getText() ) ) {
				setNaoSalvo();
				
			} else {
				setSitcontr();
				setTpProjcontr();
				tabContr.limpa();
			}
		}
	}
	
	public void setConexao( DbConnection cn ) {
		
		super.setConexao( cn );
		lcCliente.setConexao( cn );
		lcContrato.setConexao( cn );
		
		daogestao = new DAOGestaoProj( cn );
	
	}

	public void beforeCarrega( CarregaEvent cevt ) {
		
	}
	private void loadContr(){
		try {
			Vector<Vector<Object>> datavector = daogestao.loadContr( Aplicativo.iCodEmp, ListaCampos.getMasterFilial( "VDCONTRATO" ), txtCodContr.getVlrInteger() );
			tabContr.limpa();
			
			for(Vector<Object> row : datavector){
				tabContr.adicLinha( row );
			}
			
		} catch ( SQLException err ) {
			Funcoes.mensagemErro( this, "Erro carregando preferências !\b" + err.getMessage() );
			err.printStackTrace();
		}
	}
	
	public void actionPerformed( ActionEvent e ) {

		if ( e.getSource() == btGerar ) {
			loadContr();
		}
	}
	
}
