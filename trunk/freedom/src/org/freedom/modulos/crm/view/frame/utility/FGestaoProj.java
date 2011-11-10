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
import java.util.Vector;

import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import org.freedom.acao.CarregaEvent;
import org.freedom.acao.CarregaListener;
import org.freedom.bmps.Icone;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.persistence.GuardaCampo;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.component.JButtonPad;
import org.freedom.library.swing.component.JLabelPad;
import org.freedom.library.swing.component.JPanelPad;
import org.freedom.library.swing.component.JTabbedPanePad;
import org.freedom.library.swing.component.JTablePad;
import org.freedom.library.swing.component.JTextFieldFK;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.frame.FFilho;
import org.freedom.library.swing.util.SwingParams;
import org.freedom.modulos.crm.business.component.Constant;
import org.freedom.modulos.crm.business.object.Contrato;
import org.freedom.modulos.crm.view.frame.crud.detail.FContrato;

public class FGestaoProj extends FFilho implements CarregaListener{

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
	
	private JButtonPad  btUltimo = new JButtonPad( Icone.novo( "btUlt.gif" ) );;
	
	//Lista Campos
	
	private ListaCampos lcCliente = new ListaCampos( this, "CL" );
	
	private ListaCampos lcContrato = new ListaCampos( this );
	
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

		// ***** Grid

		pnCliente.add( pnDetail, BorderLayout.CENTER );
		pnDetail.add( tabAbas );
		tabAbas.addTab( "Contratos", pnContr );
		// tabbedDetail.addTab( "Receber", panelReceber );
		// tabbedDetail.addTab( "Histórico", panelHistorico );

		// ***** Venda
		
		tabContr.adicColuna( "Item" );
		tabContr.adicColuna( "Projeto" );
		tabContr.adicColuna( "Descrição do projeto" );
		tabContr.adicColuna( "Lote" );
		tabContr.adicColuna( "Qtd." );
		tabContr.adicColuna( "Preço" );
		tabContr.adicColuna( "V.Desc." );
		tabContr.adicColuna( "V.Frete" );
		tabContr.adicColuna( "V.líq." );
		tabContr.adicColuna( "TipoVenda" );
		
		pnContr.add( scpContr, BorderLayout.CENTER );
		
		// ***** Rodapé
		
		pnCliente.add( pnRodape, BorderLayout.SOUTH );
		adicNavegador();
		pnRodape.add( adicBotaoSair() );
	
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
	
	private void carregaListener(){
		lcContrato.addCarregaListener( this );
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
			setSitcontr();
			setTpProjcontr();
		}
	}
	
	public void setConexao( DbConnection cn ) {
		
		super.setConexao( cn );
		lcCliente.setConexao( cn );
		lcContrato.setConexao( cn );
	}

	public void beforeCarrega( CarregaEvent cevt ) {
		
	}
	
}
