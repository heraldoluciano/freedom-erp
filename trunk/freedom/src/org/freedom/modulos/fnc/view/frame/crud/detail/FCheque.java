/*
 * Projeto: Freedom Pacote: org.freedom.modules.fnc Classe: @(#)FCheque.java
 * 
 * Este arquivo é parte do sistema Freedom-ERP, o Freedom-ERP é um software livre; você pode redistribui-lo e/ou <BR> modifica-lo dentro dos termos da Licença Pública Geral GNU como publicada pela Fundação do Software Livre (FSF); <BR> na versão 2 da Licença, ou (na sua opnião) qualquer versão. <BR>
 * Este programa é distribuido na esperança que possa ser util, mas SEM NENHUMA GARANTIA; <BR> sem uma garantia implicita de ADEQUAÇÂO a qualquer MERCADO ou APLICAÇÃO EM PARTICULAR. <BR> Veja a Licença Pública Geral GNU para maiores detalhes. <BR> Você deve ter recebido uma cópia da Licença Pública
 * Geral GNU junto com este programa, se não, <BR> escreva para a Fundação do Software Livre(FSF) Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA <BR> <BR>
 */

package org.freedom.modulos.fnc.view.frame.crud.detail;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

import javax.swing.SwingConstants;

import net.sf.jasperreports.engine.JasperPrintManager;

import org.freedom.acao.CarregaEvent;
import org.freedom.acao.CarregaListener;
import org.freedom.acao.DeleteEvent;
import org.freedom.acao.DeleteListener;
import org.freedom.acao.InsertEvent;
import org.freedom.acao.InsertListener;
import org.freedom.acao.JComboBoxEvent;
import org.freedom.acao.JComboBoxListener;
import org.freedom.acao.PostEvent;
import org.freedom.bmps.Icone;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.functions.Funcoes;
import org.freedom.library.persistence.GuardaCampo;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.component.JButtonPad;
import org.freedom.library.swing.component.JCheckBoxPad;
import org.freedom.library.swing.component.JComboBoxPad;
import org.freedom.library.swing.component.JLabelPad;
import org.freedom.library.swing.component.JPanelPad;
import org.freedom.library.swing.component.JTextAreaPad;
import org.freedom.library.swing.component.JTextFieldFK;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.component.Navegador;
import org.freedom.library.swing.frame.Aplicativo;
import org.freedom.library.swing.frame.FDetalhe;
import org.freedom.library.swing.frame.FPrinterJob;
import org.freedom.library.swing.util.SwingParams;
import org.freedom.modulos.fnc.business.object.Cheque;
import org.freedom.modulos.fnc.view.frame.crud.tabbed.FConta;

/**
 * Cadastro cheques.
 * 
 * @author Setpoint Informática Ltda./Anderson Sanchez
 * @version 25/08/2010
 */

public class FCheque extends FDetalhe implements CarregaListener, InsertListener, DeleteListener, JComboBoxListener {

	private static final long serialVersionUID = 1L;

	private static final Color GREEN = new Color( 45, 190, 60 );

	private static final Color YELLOW = new Color( 255, 204, 51 );

	private JPanelPad panelMaster = new JPanelPad();

	private JPanelPad panelDetalhe = new JPanelPad();

	private JTextFieldPad txtSeqCheq = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	
	private JTextFieldPad txtCodBanc = new JTextFieldPad( JTextFieldPad.TP_STRING, 3, 0 );

	private JTextFieldPad txtContaCheq = new JTextFieldPad( JTextFieldPad.TP_STRING, 10, 0 );
	
	private JTextFieldPad txtAgenciaCheq = new JTextFieldPad( JTextFieldPad.TP_STRING, 7, 0 );

	private JTextFieldFK txtDescConta = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtDtEmitCheq = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );
	
	private JTextFieldPad txtDtVenctoCheq = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );
	
	private JTextFieldPad txtDtCompCheq = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtNomeEmitCheq = new JTextFieldPad( JTextFieldPad.TP_STRING, 50, 0 );
	
	private JTextFieldPad txtNomeFavCheq = new JTextFieldPad( JTextFieldPad.TP_STRING, 50, 0 );
	
	private JTextFieldPad txtNumCheq = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	
	private JTextAreaPad txaHistCheq = new JTextAreaPad( 500 );

	private JButtonPad btCompletar = new JButtonPad( Icone.novo( "btOk.gif" ) );

	private JTextFieldFK txtCodPag = new JTextFieldFK( JTextFieldPad.TP_INTEGER, 8, 0 );
	
	private JTextFieldFK txtDocPag = new JTextFieldFK( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtNParcPag = new JTextFieldFK( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtVlrParcItRec = new JTextFieldFK( JTextFieldPad.TP_DECIMAL, 15, Aplicativo.casasDecFin );

	private JTextFieldFK txtVlrApagItRec = new JTextFieldFK( JTextFieldPad.TP_DECIMAL, 15, Aplicativo.casasDecFin );

	private JTextFieldFK txtVlrPagoItRec = new JTextFieldFK( JTextFieldPad.TP_DECIMAL, 15, Aplicativo.casasDecFin );
	
	private JTextFieldPad txtVlrCheq = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 15, Aplicativo.casasDecFin );

	private JTextFieldFK txtDtPagoItPag = new JTextFieldFK( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldFK txtDtVencItPag = new JTextFieldFK( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtStatusItPag = new JTextFieldPad( JTextFieldPad.TP_STRING, 2, 0 );
	
	private JTextFieldPad txtCnpjEmitCheq = new JTextFieldPad( JTextFieldPad.TP_STRING, 14, 0 );

	private JTextFieldPad txtCpfEmitCheq = new JTextFieldPad( JTextFieldPad.TP_STRING, 11, 0 );

	private JTextFieldPad txtCnpjFavCheq = new JTextFieldPad( JTextFieldPad.TP_STRING, 14, 0 );

	private JTextFieldPad txtCpfFavCheq = new JTextFieldPad( JTextFieldPad.TP_STRING, 11, 0 );
	
	private JTextFieldPad txtDDDEmitCheq = new JTextFieldPad( JTextFieldPad.TP_STRING, 4, 0 );

	private JTextFieldPad txtDDDFavCheq = new JTextFieldPad( JTextFieldPad.TP_STRING, 12, 0 );

	private JTextFieldPad txtFoneEmitCheq = new JTextFieldPad( JTextFieldPad.TP_STRING, 8, 0 );
	
	private JTextFieldPad txtFoneFavCheq = new JTextFieldPad( JTextFieldPad.TP_STRING, 8, 0 );
	
	private JCheckBoxPad cbPreDatCheq = new JCheckBoxPad( "Pré-datado", "Sim", "Não" );
	
	private JComboBoxPad cbTipoCheq = null;
	
	private JComboBoxPad cbSitCheq = null;

	private JLabelPad lbStatus = new JLabelPad( "", SwingConstants.CENTER );

	private ListaCampos lcConta = new ListaCampos( this, "" );

	private ListaCampos lcPlan = new ListaCampos( this, "PN" );

	private ListaCampos lcItPagar = new ListaCampos( this, "" );
	
	private ListaCampos lcPagar = new ListaCampos( this, "" );

	public FCheque() {

		super( false );

		nav.setNavigation( true );

		setTitulo( "Bordero" );
		setAtribos( 50, 50, 690, 490 );

		montaListaCampos();
		montaTela();
		montaCombos();

		lcDet.addCarregaListener( this );
		lcItPagar.addCarregaListener( this );

		lcDet.addInsertListener( this );
		lcDet.addDeleteListener( this );
		lcDet.addPostListener( this );

		btCompletar.addActionListener( this );
		btImp.addActionListener( this );
		btPrevimp.addActionListener( this );
		cbTipoCheq.addComboBoxListener( this );

		
	}
	
	private void montaCombos() {
		montaCbTipoCheque();
		montaCbSitCheque();
	}
	
	private void montaCbTipoCheque( ) {

		cbTipoCheq.limpa();

		cbTipoCheq.setItens( Cheque.getLabelsTipoCheq(), Cheque.getValoresTipoCheq() );

	}
	
	private void montaCbSitCheque( ) {

		cbSitCheq.limpa();

		cbSitCheq.setItens( Cheque.getLabelsSitCheq(), Cheque.getValoresSitCheq() );

	}


	private void montaListaCampos() {

		lcConta.setUsaME( false );
		lcConta.add( new GuardaCampo( txtContaCheq, "NumConta", "Nº Conta", ListaCampos.DB_PK, false ) );
		lcConta.add( new GuardaCampo( txtDescConta, "DescConta", "Descrição da conta", ListaCampos.DB_SI, false ) );
		lcConta.montaSql( false, "CONTA", "FN" );
		lcConta.setReadOnly( true );
		txtContaCheq.setTabelaExterna( lcConta, FConta.class.getCanonicalName() );
		txtContaCheq.setFK( true );

	

		
		// Itens de contas a pagar
		
		lcItPagar.add( new GuardaCampo( txtCodPag, "CodPag", "Cód.Pag.", ListaCampos.DB_PK, false ) );
		lcItPagar.add( new GuardaCampo( txtNParcPag, "NParcPag", "Cód.It.pag.", ListaCampos.DB_PK, false ) );
		lcItPagar.add( new GuardaCampo( txtDtVencItPag, "DtVencItPag", "Vencimento", ListaCampos.DB_SI, false ) );
		lcItPagar.add( new GuardaCampo( txtDtPagoItPag, "DtPagoItPag", "Dt.Pagto.", ListaCampos.DB_SI, false ) );
		lcItPagar.add( new GuardaCampo( txtVlrParcItRec, "VlrParcItPag", "Vlr.titulo", ListaCampos.DB_SI, false ) );
		lcItPagar.add( new GuardaCampo( txtVlrApagItRec, "VlrApagItPag", "Vlr.Aberto", ListaCampos.DB_SI, false ) );
		lcItPagar.add( new GuardaCampo( txtVlrPagoItRec, "VlrPagoItPag", "Vlr.Pago", ListaCampos.DB_SI, false ) );
		lcItPagar.add( new GuardaCampo( txtStatusItPag, "StatusItPag", "Status", ListaCampos.DB_SI, false ) );
		// lcItReceber.setWhereAdic( " NOT EXISTS (SELECT B.NPARCITREC FROM FNITBORDERO B " +
		// "WHERE B.CODEMPRC=IR.CODEMP AND B.CODFILIALRC=IR.CODFILIAL AND " +
		// "B.CODREC=IR.CODREC AND B.NPARCITREC=IR.NPARCITREC) " );
		lcItPagar.montaSql( false, "ITPAGAR", "FN" );
		lcItPagar.setQueryCommit( false );
		lcItPagar.setReadOnly( true );
		txtCodPag.setTabelaExterna( lcItPagar, null );
		txtCodPag.setFK( true );
		txtCodPag.setNomeCampo( "CodPag" );
		txtNParcPag.setTabelaExterna( lcItPagar, null );
		txtNParcPag.setFK( true );
		txtNParcPag.setNomeCampo( "NParcPag" );
		
		//Contas a pagar
		
		lcPagar.add( new GuardaCampo( txtCodPag, "CodPag", "Cód.Pag.", ListaCampos.DB_PK, txtDocPag, false ) );
		lcPagar.add( new GuardaCampo( txtDocPag, "DocPag", "Documento", ListaCampos.DB_SI, false ) );
		lcPagar.montaSql( false, "PAGAR", "FN" );
		lcPagar.setQueryCommit( false );
		lcPagar.setReadOnly( true );
		txtCodPag.setTabelaExterna( lcPagar, null );
		txtCodPag.setFK( true );
		txtCodPag.setNomeCampo( "CodPag" );
		
	}

	private void montaTela() {

		txtCpfEmitCheq.setMascara( JTextFieldPad.MC_CPF );
		txtCnpjEmitCheq.setMascara( JTextFieldPad.MC_CNPJ );
		
		txtCpfFavCheq.setMascara( JTextFieldPad.MC_CPF );
		txtCnpjFavCheq.setMascara( JTextFieldPad.MC_CNPJ );
		
		txtFoneEmitCheq.setMascara( JTextFieldPad.MC_FONE );
		txtFoneFavCheq.setMascara( JTextFieldPad.MC_FONE );
		
		cbTipoCheq = new JComboBoxPad( null, null, JComboBoxPad.TP_STRING, 2, 0 );
		cbSitCheq = new JComboBoxPad( null, null, JComboBoxPad.TP_STRING, 2, 0 );
		
		setAltCab( 260 );
		setListaCampos( lcCampos );
		setPainel( panelMaster, pnCliCab );

		JPanelPad pnInfoCheque = new JPanelPad();
		pnInfoCheque.setBorder( SwingParams.getPanelLabel( "Informações do cheque", Color.BLACK ) );
		
		JPanelPad pnDatas = new JPanelPad();
		pnDatas.setBorder( SwingParams.getPanelLabel( "Datas", Color.BLUE ) );
		
		JPanelPad pnEmitente = new JPanelPad();
		pnEmitente.setBorder( SwingParams.getPanelLabel( "Informações do emitente", Color.RED ) );

		JPanelPad pnFavorecido = new JPanelPad();
		pnFavorecido.setBorder( SwingParams.getPanelLabel( "Informações do favorecido", Color.BLUE ) );

		adic( pnInfoCheque, 5, 0, 663, 70 );
		adic( pnDatas, 563, 70, 105, 150 );
		adic( pnEmitente, 5, 70, 555, 74 );
		adic( pnFavorecido, 5, 144, 555, 74 );
		
		/** INFORMACOES DO CHEQUE **/
		setPainel( pnInfoCheque );
		
		adicCampo( txtSeqCheq, 7, 15, 40, 20, "seqcheq", "Seq.", ListaCampos.DB_PK, true );
		adicCampo( txtCodBanc, 50, 15, 40, 20, "codbanc", "Banco", ListaCampos.DB_SI, true );
		adicCampo( txtContaCheq, 93, 15, 70, 20, "Contacheq", "Nº Conta", ListaCampos.DB_FK, txtDescConta, true );
		adicCampo( txtNumCheq, 166, 15, 60, 20, "Numcheq", "Nro.Cheq.", ListaCampos.DB_SI, true );
		adicCampo( txtVlrCheq, 229, 15, 75, 20, "VlrCheq", "Valor", ListaCampos.DB_SI, true );		
		adicDB( cbTipoCheq, 307, 15, 125, 20, "tipocheq", "Tipo", false );		
		adicDB( cbSitCheq, 435, 15, 112, 20, "sitcheq", "Status", false );
		
		adicDB( cbPreDatCheq, 558, 15, 86, 20, "predatcheq", "", true );

		
		/** INFORMACOES DE DATAS **/
		setPainel( pnDatas );

		adicCampo( txtDtEmitCheq, 4, 15, 85, 20, "DtEmitCheq", "Emissão", ListaCampos.DB_SI, true );
		adicCampo( txtDtVenctoCheq, 4, 55, 85, 20, "DtVenctoCheq", "Vencimento", ListaCampos.DB_SI, true );
		adicCampo( txtDtCompCheq, 4, 95, 85, 20, "DtCompCheq", "Compensação", ListaCampos.DB_SI, true );		

		/** INFORMACOES DO EMITENTE **/
		setPainel( pnEmitente );
		
		adicCampo( txtNomeEmitCheq, 7, 15, 200, 20, "nomeemitcheq", "Nome", ListaCampos.DB_SI, true );
		adicCampo( txtCnpjEmitCheq, 210, 15, 120, 20, "cnpjemitcheq", "CNPJ", ListaCampos.DB_SI, false );
		adicCampo( txtCpfEmitCheq, 333, 15, 100, 20, "cpfemitcheq", "CPF", ListaCampos.DB_SI, false );
		adicCampo( txtDDDEmitCheq, 436, 15, 30, 20, "dddemitcheq", "DDD", ListaCampos.DB_SI, false );
		adicCampo( txtFoneEmitCheq, 469, 15, 68, 20, "foneemitcheq", "Fone", ListaCampos.DB_SI, false );
		
		
		/** INFORMACOES DO FAVORECIDO **/
		setPainel( pnFavorecido );
		
		adicCampo( txtNomeFavCheq, 7, 15, 200, 20, "nomeFavcheq", "Nome", ListaCampos.DB_SI, true );
		adicCampo( txtCnpjFavCheq, 210, 15, 120, 20, "cnpjFavcheq", "CNPJ", ListaCampos.DB_SI, false );
		adicCampo( txtCpfFavCheq, 333, 15, 100, 20, "cpfFavcheq", "CPF", ListaCampos.DB_SI, false );
		adicCampo( txtDDDFavCheq, 436, 15, 30, 20, "dddFavcheq", "DDD", ListaCampos.DB_SI, false );
		adicCampo( txtFoneFavCheq, 469, 15, 68, 20, "foneFavcheq", "Fone", ListaCampos.DB_SI, false );

		
		setPainel( panelMaster, pnCliCab );
		
//		adicDB( txaHistCheq, 7, 100, 539, 60, "histcheq", "Histórico", false );

		setListaCampos( true, "CHEQUE", "FN" ); 
		lcCampos.setQueryInsert( false );

		setAltDet( 60 );
		setPainel( panelDetalhe, pnDet );
		setListaCampos( lcDet );
		setNavegador( navRod );

		adicCampo( txtCodPag, 7, 20, 50, 20, "CodPag", "Cód.pag.", ListaCampos.DB_PK, false );
		adicDescFK( txtDocPag, 60, 20, 70, 20, "docpag", "Documento" );		
		adicCampo( txtNParcPag, 133, 20, 50, 20, "NParcPag", "Parcela", ListaCampos.DB_PF, false );
		
		adicDescFK( txtDtVencItPag, 186, 20, 75, 20, "DtVencItPag", "Vencimento" );
		adicDescFK( txtDtPagoItPag, 264, 20, 75, 20, "DtPagoItPag", "Dt.Pagto." );
		
		adicDescFK( txtVlrParcItRec, 342, 20, 70, 20, "VlrParcItRec", "Vlr.titulo" );
		adicDescFK( txtVlrPagoItRec, 415, 20, 70, 20, "VlrPagoItRec", "Vlr.Pago" );
		adicDescFK( txtVlrApagItRec, 488, 20, 70, 20, "VlrApagItRec", "Vlr.Aberto" );

		adic( lbStatus, 561, 20, 103, 20 );
		
		
		setListaCampos( true, "PAGCHEQ", "FN" );
		lcDet.setQueryInsert( false );

		montaTab();

		pnGImp.removeAll();
		pnGImp.setLayout( new GridLayout( 1, 3 ) );
		pnGImp.setPreferredSize( new Dimension( 93, 26 ) );
		pnGImp.add( btCompletar );
		pnGImp.add( btImp );
		pnGImp.add( btPrevimp );

		setImprimir( true );

		txtCodPag.setFK( true );
		navRod.setAtivo( Navegador.BT_EDITAR, false );

		lbStatus.setForeground( Color.WHITE );
		lbStatus.setFont( new Font( "Arial", Font.BOLD, 13 ) );
		lbStatus.setOpaque( true );
		lbStatus.setVisible( false );
	}

	private void showStatus() {

		lbStatus.setVisible( true );

		if ( "RB".equals( txtStatusItPag.getVlrString() ) ) {
			lbStatus.setVisible( false );
		}
		else if ( "CR".equals( txtStatusItPag.getVlrString() ) ) {
			lbStatus.setText( "Cancelada" );
			lbStatus.setBackground( Color.DARK_GRAY );
		}
		else if ( "RP".equals( txtStatusItPag.getVlrString() ) && txtVlrApagItRec.getVlrBigDecimal().doubleValue() == 0 ) {
			lbStatus.setText( "Recebida" );
			lbStatus.setBackground( GREEN );
		}
		else if ( txtVlrPagoItRec.getVlrBigDecimal().doubleValue() > 0 ) {
			lbStatus.setText( "Rec. parcial" );
			lbStatus.setBackground( Color.BLUE );
		}
		if ( txtDtVencItPag.getVlrDate() != null ) {
			if ( txtDtVencItPag.getVlrDate().before( Calendar.getInstance().getTime() ) ) {
				lbStatus.setText( "Vencida" );
				lbStatus.setBackground( Color.RED );
			}
			else if ( txtDtVencItPag.getVlrDate().after( Calendar.getInstance().getTime() ) ) {
				lbStatus.setText( "À vencer" );
				lbStatus.setBackground( YELLOW );
			}
		}
		else {
			lbStatus.setVisible( false );
		}
		
	}

	private void concluiBordero() {

		if ( txtSeqCheq.getVlrInteger() <= 0 ) {
			return;
		}

		try {
			PreparedStatement ps = con.prepareStatement( "UPDATE FNBORDERO SET STATUSBOR='BC' WHERE CODEMP=? AND CODFILIAL=? AND CODBOR=?" );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "FNBORDERO" ) );
			ps.setInt( 3, txtSeqCheq.getVlrInteger() );

			ps.executeUpdate();
			con.commit();
		} catch ( SQLException e ) {
			e.printStackTrace();
		}
	}

	private void imprimir( boolean visualizar ) {

		if ( txtSeqCheq.getVlrInteger() == 0 ) {
			return;
		}

		StringBuilder sql = new StringBuilder();
		sql.append( "SELECT CL.CPFCLI,CL.CNPJCLI,CL.FONECLI, B.CODBOR, B.NUMCONTA, C.DESCCONTA, B.NUMCONTABOR, C2.DESCCONTA DESCCONTABOR, B.DTBOR, B.OBSBOR," );
		sql.append( "IB.CODREC, IB.NPARCITREC, R.CODCLI, CL.RAZCLI, IR.VLRITREC, IR.DTVENCITREC,R.DOCREC,C.AGENCIACONTA " );
		sql.append( "FROM FNBORDERO B, FNITBORDERO IB, FNCONTA C, FNCONTA C2, " );
		sql.append( "FNITRECEBER IR, FNRECEBER R, VDCLIENTE CL " );
		sql.append( "WHERE B.CODEMP=? AND B.CODFILIAL=? AND B.CODBOR=? AND " );
		sql.append( "C.CODEMP=B.CODEMPCC AND C.CODFILIAL=B.CODFILIALCC AND C.NUMCONTA=B.NUMCONTA AND " );
		sql.append( "C2.CODEMP=B.CODEMPCB AND C2.CODFILIAL=B.CODFILIALCB AND C2.NUMCONTA=B.NUMCONTABOR AND " );
		sql.append( "IB.CODEMP=B.CODEMP AND IB.CODFILIAL=B.CODFILIAL AND IB.CODBOR=B.CODBOR AND " );
		sql.append( "IR.CODEMP=IB.CODEMPRC AND IR.CODFILIAL=IB.CODFILIALRC AND IR.CODREC=IB.CODREC AND IR.NPARCITREC=IB.NPARCITREC AND " );
		sql.append( "R.CODEMP=IR.CODEMP AND R.CODFILIAL=IR.CODFILIAL AND R.CODREC=IR.CODREC AND " );
		sql.append( "CL.CODEMP=R.CODEMPCL AND CL.CODFILIAL=R.CODFILIALCL AND CL.CODCLI=R.CODCLI " );
		sql.append( "ORDER BY IB.NPARCITREC" );

		ResultSet rs = null;

		try {
			PreparedStatement ps = con.prepareStatement( sql.toString() );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "FNBORDERO" ) );
			ps.setInt( 3, txtSeqCheq.getVlrInteger() );

			rs = ps.executeQuery();

			FPrinterJob dlGr = new FPrinterJob( "relatorios/FBordero.jasper", "Bordero de recebíveis", null, rs, null, this );

			if ( visualizar ) {
				dlGr.setVisible( true );
			}
			else {
				try {
					JasperPrintManager.printReport( dlGr.getRelatorio(), true );
				} catch ( Exception e ) {
					e.printStackTrace();
					Funcoes.mensagemErro( this, "Erro na impressão!" + e.getMessage(), true, con, e );
				}
			}

			con.commit();
		} catch ( SQLException e ) {
			e.printStackTrace();
			Funcoes.mensagemErro( this, "Erro na pesquisa!" + e.getMessage(), true, con, e );
		}
	}

	@ Override
	public void actionPerformed( ActionEvent e ) {

		if ( e.getSource() == btCompletar ) {
			concluiBordero();
		}
		else if ( e.getSource() == btImp ) {
			imprimir( false );
		}
		else if ( e.getSource() == btPrevimp ) {
			imprimir( true );
		}
		else {
			super.actionPerformed( e );
		}
	}

	public void beforeCarrega( CarregaEvent e ) {

	}

	public void afterCarrega( CarregaEvent e ) {

		showStatus();
		
		if(e.getListaCampos() == lcDet) {
			lcPagar.carregaDados();
		}
		
		
	}

	public void beforeInsert( InsertEvent e ) {

		lbStatus.setVisible( false );
	}

	public void afterInsert( InsertEvent e ) {

	}

	public void beforeDelete( DeleteEvent e ) {

	}

	public void afterDelete( DeleteEvent e ) {

		lbStatus.setVisible( false );
	}

	@ Override
	public void afterPost( PostEvent e ) {

		super.afterPost( e );
		showStatus();
	}

	public void setConexao( DbConnection con ) {

		super.setConexao( con );
		
		lcConta.setConexao( con );
		lcItPagar.setConexao( con );
		lcPagar.setConexao( con );
		
	}

	public void valorAlterado( JComboBoxEvent evt ) {

		// TODO Auto-generated method stub
		
	}
}
