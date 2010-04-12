/**
 * @version 10/10/2003 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 * 
 *         Projeto: Freedom <BR>
 * 
 *         Pacote: org.freedom.modulos.tmk <BR>
 *         Classe:
 * @(#)FContato.java <BR>
 * 
 * Este arquivo é parte do sistema Freedom-ERP, o Freedom-ERP é um software livre; você pode redistribui-lo e/ou <BR>
 * modifica-lo dentro dos termos da Licença Pública Geral GNU como publicada pela Fundação do Software Livre (FSF); <BR>
 * na versão 2 da Licença, ou (na sua opnião) qualquer versão. <BR>
 * Este programa é distribuido na esperança que possa ser  util, mas SEM NENHUMA GARANTIA; <BR>
 * sem uma garantia implicita de ADEQUAÇÂO a qualquer MERCADO ou APLICAÇÃO EM PARTICULAR. <BR>
 * Veja a Licença Pública Geral GNU para maiores detalhes. <BR>
 * Você deve ter recebido uma cópia da Licença Pública Geral GNU junto com este programa, se não, <BR>
 *                   de acordo com os termos da LPG-PC <BR>
 * <BR>
 * 
 *                   Tela de cadastro de contatos.
 * 
 */

package org.freedom.modulos.crm;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.persistence.GuardaCampo;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.JButtonPad;
import org.freedom.library.swing.JPanelPad;
import org.freedom.library.swing.JRadioGroup;
import org.freedom.library.swing.JTextAreaPad;
import org.freedom.library.swing.JTextFieldFK;
import org.freedom.library.swing.JTextFieldPad;
import org.freedom.library.swing.frame.Aplicativo;
import org.freedom.library.swing.frame.FDetalhe;

import java.util.Vector;

import org.freedom.bmps.Icone;


public class FContrato extends FDetalhe implements ActionListener{

	

	private static final long serialVersionUID = 1L;

	private JPanelPad pinCab = new JPanelPad();

	private JPanelPad pinDet = new JPanelPad();

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

	private JTextFieldPad txtVlrProd = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 15, Aplicativo.casasDec  );

	private JTextFieldPad txtVlrExcedProd = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 15, Aplicativo.casasDec );
	
	private JTextFieldPad txtCodProd = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 10, 0 );
	
	private JTextFieldPad txtCodProdPE = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 10, 0 );
	
	private JTextFieldFK txtDescProd = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );
	
	private JTextFieldPad txtDescItContr = new JTextFieldPad( JTextFieldPad.TP_STRING, 80, 0 );
	
	private JTextFieldFK txtDescProdPE = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );
		
	private JTextAreaPad txaMinuta =  new JTextAreaPad( 32000 );
	
	private JButtonPad btMinuta = new JButtonPad( Icone.novo( "btObs.gif" ) );
	
	private JRadioGroup<?, ?> rgTipoCobContr = null;
	
	private JRadioGroup<?, ?> rgTipoContr = null;
	
	private ListaCampos lcCli = new ListaCampos( this, "CL" );
	
	private ListaCampos lcProduto = new ListaCampos( this, "PD" );
	
	private ListaCampos lcProdutoex = new ListaCampos( this, "PE" );
	
	private String sMinuta = "";

	public FContrato() {

		setTitulo( "Contratos" );
		setAltCab( 180 );
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
		vLabsTipo.addElement( "Contrato" );
		vLabsTipo.addElement( "Projeto" );
		rgTipoContr = new JRadioGroup<String, String>( 1, 2 , vLabsTipo, vValsTipo );
		rgTipoContr.setVlrString( "C" );

		setListaCampos( lcCampos );
		setPainel( pinCab, pnCliCab );
		adicCampo( txtCodContrato, 7, 20, 70, 20, "CodContr", "Cód.proj.", ListaCampos.DB_PK, true );
		adicCampo( txtDescContrato, 80, 20, 602, 20, "DescContr", "Descrição do projeto/contrato", ListaCampos.DB_SI, true );

		adicCampo( txtCodCli, 7, 60, 70, 20, "CodCli", "Cód.Cli", ListaCampos.DB_FK, txtNomeCli, true );
		adicDescFK( txtNomeCli, 80, 60, 320, 20, "RazCli", "Razão social do cliente" );
		
		adicCampo( txtDtInicioContr, 403, 60, 75, 20, "DtInicio", "Dt.inicio", ListaCampos.DB_SI, true );
		adicCampo( txtDtFimContr, 481, 60, 75, 20, "DtFim", "Dt.fim", ListaCampos.DB_SI, true );
		adicCampo( txtDiaVencCobr, 559, 60, 60, 20, "DiaVencContr", "Dia venc.", ListaCampos.DB_SI, true );
		adicCampo( txtDiaFechCobr, 622, 60, 60, 20, "DiaFechContr", "Dia fech.", ListaCampos.DB_SI, true );
		
		adicDB( rgTipoContr, 7, 100, 200, 30, "TpContr", "Tipo", true );
		adicDB( rgTipoCobContr, 210, 100, 437, 30, "TpCobContr", "Cobrança", true );

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
			
		adicCampo( txtQtdProd, 7, 105, 80, 20, "QtdItContr", "Quantidade", ListaCampos.DB_SI, true );
		adicCampo( txtVlrProd, 90, 105, 80, 20, "VlrItContr", "Valor", ListaCampos.DB_SI, true );
		adicCampo( txtVlrExcedProd, 173, 105, 80, 20, "VlrItContrExced", "Vlr.exced.", ListaCampos.DB_SI, true );	
		
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
		
		
	}
	
	private void montaListaCampos(){
		
		/*************
		 *  CLIENTE  *
		 * ***********/
		
		txtCodCli.setTabelaExterna( lcCli );
		txtCodCli.setFK( true );
		txtCodCli.setNomeCampo( "CodCli" );
		lcCli.add( new GuardaCampo( txtCodCli, "CodCli", "Cód.cli.", ListaCampos.DB_PK, false ) );
		lcCli.add( new GuardaCampo( txtNomeCli, "RazCli", "Razão social do cliente", ListaCampos.DB_SI, false ) );
		lcCli.montaSql( false, "CLIENTE", "VD" );
		
		/**********************
		 *  PRODUTO PRINCIPAL  *
		 * ********************/
		
		txtCodProd.setTabelaExterna( lcProduto );
		txtCodProd.setFK( true );
		txtCodProd.setNomeCampo( "CodProd" );
		lcProduto.add( new GuardaCampo( txtCodProd, "CodProd", "Cód.prod.", ListaCampos.DB_PK, false ) );
		lcProduto.add( new GuardaCampo( txtDescProd, "DescProd", "Descrição do produto", ListaCampos.DB_SI, false ) );
		lcProduto.montaSql( false, "PRODUTO", "EQ" );

		/**********************
		 *  PRODUTO EXCEDENTE  *
		 * ********************/
		
		txtCodProdPE.setTabelaExterna( lcProdutoex );
		txtCodProdPE.setFK( true );
		txtCodProdPE.setNomeCampo( "CodProdPE" );
		lcProdutoex.add( new GuardaCampo( txtCodProdPE, "CodProd", "Cód.prod.", ListaCampos.DB_PK, false ) );
		lcProdutoex.add( new GuardaCampo( txtDescProdPE, "DescProd", "Descrição do produto", ListaCampos.DB_SI, false ) );
		lcProdutoex.montaSql( false, "PRODUTO", "EQ" );

		
		
		
		btMinuta.addActionListener( this );
	}
	
	private void abreDLMinuta(){
		
		DLMinutaContr dl = new DLMinutaContr( txaMinuta.getVlrString() );
		dl.setVisible( true );
		
		if( dl.OK ){
			txaMinuta.setVlrString( dl.getValores() );
			
		}
	}
	
	@ Override
	public void actionPerformed( ActionEvent evt ) {

		super.actionPerformed( evt );
		
		if( evt.getSource() == btMinuta ){
			abreDLMinuta();
		}
	}

	public void setConexao( DbConnection con ) {
		super.setConexao( con );
		lcCli.setConexao( con );
		lcProduto.setConexao( con );		
		lcProdutoex.setConexao( con );		
	}
}
