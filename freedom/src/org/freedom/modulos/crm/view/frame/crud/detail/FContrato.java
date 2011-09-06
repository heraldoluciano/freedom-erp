/**
 * @version 10/10/2003 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 * 
 *         Projeto: Freedom <BR>
 * 
 *         Pacote: org.freedom.modulos.crm.view.frame.crud.detail <BR>
 *         Classe:
 * @(#)FContato.java <BR>
 * 
 *                   Este arquivo é parte do sistema Freedom-ERP, o Freedom-ERP é um software livre; você pode redistribui-lo e/ou <BR>
 *                   modifica-lo dentro dos termos da Licença Pública Geral GNU como publicada pela Fundação do Software Livre (FSF); <BR>
 *                   na versão 2 da Licença, ou (na sua opnião) qualquer versão. <BR>
 *                   Este programa é distribuido na esperança que possa ser util, mas SEM NENHUMA GARANTIA; <BR>
 *                   sem uma garantia implicita de ADEQUAÇÂO a qualquer MERCADO ou APLICAÇÃO EM PARTICULAR. <BR>
 *                   Veja a Licença Pública Geral GNU para maiores detalhes. <BR>
 *                   Você deve ter recebido uma cópia da Licença Pública Geral GNU junto com este programa, se não, <BR>
 *                   de acordo com os termos da LPG-PC <BR>
 * <BR>
 * 
 *                   Tela de cadastro de contatos.
 * 
 */

package org.freedom.modulos.crm.view.frame.crud.detail;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import org.freedom.acao.InsertEvent;
import org.freedom.acao.InsertListener;
import org.freedom.acao.RadioGroupEvent;
import org.freedom.acao.RadioGroupListener;
import org.freedom.bmps.Icone;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.persistence.GuardaCampo;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.component.JButtonPad;
import org.freedom.library.swing.component.JCheckBoxPad;
import org.freedom.library.swing.component.JPanelPad;
import org.freedom.library.swing.component.JRadioGroup;
import org.freedom.library.swing.component.JTextAreaPad;
import org.freedom.library.swing.component.JTextFieldFK;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.frame.Aplicativo;
import org.freedom.library.swing.frame.FDetalhe;
import org.freedom.modulos.crm.view.dialog.utility.DLMinutaContr;
import org.freedom.modulos.gms.view.frame.crud.tabbed.FProduto;
import org.freedom.modulos.std.view.frame.crud.tabbed.FCliente;

public class FContrato extends FDetalhe implements ActionListener, InsertListener, RadioGroupListener {

	private static final long serialVersionUID = 1L;

	private JPanelPad pinCab = new JPanelPad();

	private JPanelPad pinDet = new JPanelPad();
	
	private JCheckBoxPad cbAtivo = new JCheckBoxPad( "Ativo", "S", "N" );

	private JCheckBoxPad cbReceb = new JCheckBoxPad( "Recebível", "S", "N" );
	
	private JTextFieldPad txtCodContrato = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 4, 0 );

	private JTextFieldPad txtDescContrato = new JTextFieldPad( JTextFieldPad.TP_STRING, 80, 0 );

	private JTextFieldPad txtMinuta = new JTextFieldPad( JTextFieldPad.TP_STRING, 32000, 0 );

	private JTextFieldPad txtDtInicioContr = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtDtFimContr = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtDiaVencCobr = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 2, 0 );

	private JTextFieldPad txtDiaFechCobr = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 2, 0 );

	private JTextFieldPad txtTipoCobr = new JTextFieldPad( JTextFieldPad.TP_STRING, 2, 0 );

	private JTextFieldPad txtCodCli = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 5, 0 );

	private JTextFieldFK txtNomeCli = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private JTextFieldPad txtCodItContrato = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 4, 0 );

	private JTextFieldPad txtQtdProd = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 15, Aplicativo.casasDec );

	private JTextFieldPad txtVlrProd = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 15, Aplicativo.casasDec );

	private JTextFieldPad txtVlrExcedProd = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 15, Aplicativo.casasDec );
	
	private JTextFieldPad txtAcumuloItContr = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 5, 0 );

	private JTextFieldPad txtCodProd = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 10, 0 );

	private JTextFieldPad txtCodProdPE = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 10, 0 );

	private JTextFieldFK txtDescProd = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtDescItContr = new JTextFieldPad( JTextFieldPad.TP_STRING, 80, 0 );

	private JTextFieldFK txtDescProdPE = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );
	
	private JTextFieldPad txtCodContratoPai = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 4, 0 );

	private JTextFieldFK txtDescContratoPai = new JTextFieldFK( JTextFieldPad.TP_STRING, 80, 0 );

	private JTextAreaPad txaMinuta = new JTextAreaPad( 32000 );

	private JButtonPad btMinuta = new JButtonPad( Icone.novo( "btObs.gif" ) );

	private JRadioGroup<?, ?> rgTipoCobContr = null;

	private JRadioGroup<?, ?> rgTipoContr = null;

	private ListaCampos lcCli = new ListaCampos( this, "CL" );

	private ListaCampos lcProduto = new ListaCampos( this, "PD" );

	private ListaCampos lcProdutoex = new ListaCampos( this, "PE" );
	
	private ListaCampos lcSuperProjeto = new ListaCampos( this, "SP" );

	private String sMinuta = "";
	
	private JTextFieldPad txtKeyLic = new JTextFieldPad(JTextFieldPad.TP_STRING, 500, 0); 

	public FContrato() {

		setTitulo( "Contratos" );

		nav.setNavigation( true );

		setAltCab( 220 );
		setAtribos( 50, 50, 715, 550 );
		pinCab = new JPanelPad( 500, 50 );

		montaListaCampos();

		Vector<String> vValsTipoCob = new Vector<String>();
		Vector<String> vLabsTipoCob = new Vector<String>();
		vValsTipoCob.addElement( "ME" );
		vValsTipoCob.addElement( "BI" );
		vValsTipoCob.addElement( "AN" );
		vValsTipoCob.addElement( "ES" );
		vLabsTipoCob.addElement( "Mensal" );
		vLabsTipoCob.addElement( "Bimestral" );
		vLabsTipoCob.addElement( "Anual" );
		vLabsTipoCob.addElement( "Esporádico" );
		rgTipoCobContr = new JRadioGroup<String, String>( 1, 4, vLabsTipoCob, vValsTipoCob );
		rgTipoCobContr.setVlrString( "AN" );

		Vector<String> vValsTipo = new Vector<String>();
		Vector<String> vLabsTipo = new Vector<String>();
		vValsTipo.addElement( "C" );
		vValsTipo.addElement( "P" );
		vValsTipo.addElement( "S" );
		vLabsTipo.addElement( "Contrato" );
		vLabsTipo.addElement( "Projeto" );
		vLabsTipo.addElement( "Sub-projeto" );
		rgTipoContr = new JRadioGroup<String, String>( 1, 3, vLabsTipo, vValsTipo );
		rgTipoContr.setVlrString( "C" );
		rgTipoContr.addRadioGroupListener( this );

		setListaCampos( lcCampos );
		setPainel( pinCab, pnCliCab );
		adicCampo( txtCodContrato, 7, 20, 70, 20, "CodContr", "Cód.proj.", ListaCampos.DB_PK, true );
		adicCampo( txtDescContrato, 80, 20, 502, 20, "DescContr", "Descrição do projeto/contrato", ListaCampos.DB_SI, true );
		adicDB( cbReceb, 585, 20, 100, 20, "RecebContr", "", true);

		adicCampo( txtCodCli, 7, 60, 70, 20, "CodCli", "Cód.Cli", ListaCampos.DB_FK, txtNomeCli, true );
		adicDescFK( txtNomeCli, 80, 60, 320, 20, "RazCli", "Razão social do cliente" );

		adicCampo( txtDtInicioContr, 403, 60, 75, 20, "DtInicio", "Dt.inicio", ListaCampos.DB_SI, true );
		adicCampo( txtDtFimContr, 481, 60, 75, 20, "DtFim", "Dt.fim", ListaCampos.DB_SI, true );
		adicCampo( txtDiaVencCobr, 559, 60, 60, 20, "DiaVencContr", "Dia venc.", ListaCampos.DB_SI, true );
		adicCampo( txtDiaFechCobr, 622, 60, 60, 20, "DiaFechContr", "Dia fech.", ListaCampos.DB_SI, true );

		adicDB( rgTipoCobContr	, 7	    , 100, 395, 30, "TpCobContr", "Cobrança", true );
		adicDB( cbAtivo			, 590	, 100, 60, 30, "Ativo", "", true );
		adicDB( rgTipoContr		, 7		, 150, 310, 30, "TpContr", "Tipo", true );
		
		adicCampo ( txtCodContratoPai,  320, 150,  70, 20, "CodContrSp", "Cód.Proj.", ListaCampos.DB_FK, txtDescContratoPai, false);
		adicDescFK( txtDescContratoPai, 393, 150, 289, 20, "DescContr", "Descrição do projeto principal" );
		txtCodContratoPai.setNomeCampo( "CodContr" ); 
		txtCodContratoPai.setEnabled( false );
		txtDescContratoPai.setEnabled( false );
		
		adic( btMinuta, 652, 100, 30, 30 );

		adicDBInvisivel( txaMinuta, "MinutaContr", "Minuta do cotrato", false );

		setListaCampos( true, "CONTRATO", "VD" );
		lcCampos.setQueryInsert( false );

		txaMinuta.setVisible( false );

		setAltDet( 140 );
		pinDet = new JPanelPad( 600, 80 );
		setPainel( pinDet, pnDet );
		setListaCampos( lcDet );
		setNavegador( navRod );
		adicCampo( txtCodItContrato, 7, 25, 60, 20, "CodItContr", "Item", ListaCampos.DB_PK, true );
		adicCampo( txtDescItContr, 70, 25, 612, 20, "DescItContr", "Descrição do item de contrato", ListaCampos.DB_SI, true );
		adicCampo( txtCodProd, 7, 65, 60, 20, "CodProd", "Cód.prod.", ListaCampos.DB_FK, txtDescProd, true );
		adicDescFK( txtDescProd, 70, 65, 270, 20, "DescProd", "Descrição do produto/serviço" );
		adicCampo( txtCodProdPE, 343, 65, 60, 20, "CodProdPE", "Cód.prod.", ListaCampos.DB_FK, txtDescProdPE, true );
		adicDescFK( txtDescProdPE, 406, 65, 277, 20, "DescProdPE", "Descrição do produto/serviço excedente" );

		adicCampo( txtQtdProd, 7, 105, 100, 20, "QtdItContr", "Quantidade", ListaCampos.DB_SI, true );
		adicCampo( txtVlrProd, 110, 105, 100, 20, "VlrItContr", "Valor normal", ListaCampos.DB_SI, true );
		adicCampo( txtVlrExcedProd, 213, 105, 100, 20, "VlrItContrExced", "Valor excedente", ListaCampos.DB_SI, true );	
		adicCampo( txtAcumuloItContr, 316, 105, 100, 20, "AcumuloItContr", "Meses/Acumulo", ListaCampos.DB_SI, true );
		adicCampo( txtKeyLic, 420, 105, 260, 20, "KeyLic", "Chave de licenciamento do produto", ListaCampos.DB_SI, false);

		setListaCampos( true, "ITCONTRATO", "VD" );
		lcDet.setQueryInsert( false );
		montaTab();
		btImp.addActionListener( this );
		btPrevimp.addActionListener( this ); 
	
		tab.setTamColuna( 40, 0 );
		tab.setTamColuna( 420, 1 );
		tab.setColunaInvisivel( 2 ); 
		tab.setColunaInvisivel( 3 );
		tab.setColunaInvisivel( 4 );
		tab.setColunaInvisivel( 5 ); 

		lcCampos.addInsertListener( this );
	}

	private void montaListaCampos() {

		/*************
		 * CLIENTE * *
		 **********/

		txtCodCli.setTabelaExterna( lcCli, FCliente.class.getCanonicalName() );
		txtCodCli.setFK( true );
		txtCodCli.setNomeCampo( "CodCli" );
		lcCli.add( new GuardaCampo( txtCodCli, "CodCli", "Cód.cli.", ListaCampos.DB_PK, false ) );
		lcCli.add( new GuardaCampo( txtNomeCli, "RazCli", "Razão social do cliente", ListaCampos.DB_SI, false ) );
		lcCli.montaSql( false, "CLIENTE", "VD" );

		/**********************
		 * PRODUTO PRINCIPAL * *
		 *******************/

		txtCodProd.setTabelaExterna( lcProduto, FProduto.class.getCanonicalName() );
		txtCodProd.setFK( true );
		txtCodProd.setNomeCampo( "CodProd" );
		lcProduto.add( new GuardaCampo( txtCodProd, "CodProd", "Cód.prod.", ListaCampos.DB_PK, false ) );
		lcProduto.add( new GuardaCampo( txtDescProd, "DescProd", "Descrição do produto", ListaCampos.DB_SI, false ) );
		lcProduto.montaSql( false, "PRODUTO", "EQ" );

		/**********************
		 * PRODUTO EXCEDENTE * *
		 *******************/

		txtCodProdPE.setTabelaExterna( lcProdutoex, FProduto.class.getCanonicalName() );
		txtCodProdPE.setFK( true );
		txtCodProdPE.setNomeCampo( "CodProdPE" );
		lcProdutoex.add( new GuardaCampo( txtCodProdPE, "CodProd", "Cód.prod.", ListaCampos.DB_PK, false ) );
		lcProdutoex.add( new GuardaCampo( txtDescProdPE, "DescProd", "Descrição do produto", ListaCampos.DB_SI, false ) );
		lcProdutoex.montaSql( false, "PRODUTO", "EQ" );
		
		/**********************
		 * CONTRATO PAI   * *
		 *******************/
		//txtCodContratoPai.setFK( true );
		lcSuperProjeto.setQueryCommit( false );
		lcSuperProjeto.setReadOnly( true );
		lcSuperProjeto.add( new GuardaCampo( txtCodContratoPai, "CodContr", "Cód.Contr.", ListaCampos.DB_PK, txtDescContratoPai, false ) );
		lcSuperProjeto.add( new GuardaCampo( txtDescContratoPai, "DescContr", "Descrição do contrato", ListaCampos.DB_SI, false ) );
		lcSuperProjeto.montaSql( false, "CONTRATO", "VD" );
		txtCodContratoPai.setTabelaExterna( lcSuperProjeto, FContrato.class.getCanonicalName() );
		txtCodContratoPai.setFK( true );
		//txtCodContratoPai.setNomeCampo( "CodContr" );
		

		btMinuta.addActionListener( this );
	}

	private void abreDLMinuta() {

		DLMinutaContr dl = new DLMinutaContr( txaMinuta.getVlrString() );
		dl.setVisible( true );

		if ( dl.OK ) {
			txaMinuta.setVlrString( dl.getValores() );

		}
	}

	@ Override
	public void actionPerformed( ActionEvent evt ) {

		super.actionPerformed( evt );

		if ( evt.getSource() == btMinuta ) {
			abreDLMinuta();
		}
	}

	public void setConexao( DbConnection con ) {

		super.setConexao( con );
		lcCli.setConexao( con );
		lcProduto.setConexao( con );
		lcProdutoex.setConexao( con );
		lcSuperProjeto.setConexao( con );
	}

	public void afterInsert( InsertEvent ievt ) {

		if (ievt.getListaCampos()==lcCampos) {
			cbReceb.setVlrString( "S" );
		}
		
	}

	public void beforeInsert( InsertEvent ievt ) {

		
	}
	
	public void valorAlterado(RadioGroupEvent evt){
		if (evt.getSource() == rgTipoContr){
			visualizarSuperProjeto("S".equals( rgTipoContr.getVlrString()));
		}
	}
	
	private void visualizarSuperProjeto(boolean flag){
		this.txtCodContratoPai.setEnabled( flag );
		this.txtDescContratoPai.setEnabled( flag );
		if (!flag){
			this.txtCodContratoPai.setVlrString( "" );
			this.txtDescContratoPai.setVlrString( "" );
		}
	}
}
