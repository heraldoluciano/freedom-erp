/**
 * @version 14/07/2003 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.std <BR>
 * Classe:
 * @(#)DLNovoRec.java <BR>
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
 */

package org.freedom.modulos.std;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import org.freedom.infra.model.jdbc.DbConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JPanelPad;
import javax.swing.JScrollPane;

import org.freedom.acao.CarregaEvent;
import org.freedom.acao.CarregaListener;
import org.freedom.acao.PostEvent;
import org.freedom.acao.PostListener;
import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.Navegador;
import org.freedom.componentes.Tabela;
import org.freedom.funcoes.Funcoes;
import org.freedom.modulos.fnc.DLRestrCli;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FFDialogo;

public class DLNovoRec extends FFDialogo implements CarregaListener, PostListener, FocusListener {

	private static final long serialVersionUID = 1L;

	private final JPanelPad pnRec = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private final JPanelPad pinCab = new JPanelPad( 580, 170 );

	private final JTextFieldPad txtCodCli = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private final JTextFieldFK txtDescCli = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private final JTextFieldPad txtCodPlanoPag = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private final JTextFieldFK txtDescPlanoPag = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private final JTextFieldPad txtCodTipoCob = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private final JTextFieldFK txtDescTipoCob = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );
	
	private final JTextFieldPad txtObrigCart = new JTextFieldPad( JTextFieldPad.TP_STRING, 1, 0 );

	private final JTextFieldPad txtCodBanco = new JTextFieldPad( JTextFieldPad.TP_STRING, 3, 0 );

	private final JTextFieldFK txtDescBanco = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private final JTextFieldPad txtCodCartCob = new JTextFieldPad( JTextFieldPad.TP_STRING, 2, 0 );

	private final JTextFieldFK txtDescCartCob = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private final JTextFieldPad txtCodTipoCobItRec = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private final JTextFieldFK txtDescTipoCobItRec = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private final JTextFieldPad txtCodBancoItRec = new JTextFieldPad( JTextFieldPad.TP_STRING, 3, 0 );

	private final JTextFieldFK txtDescBancoItRec = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private final JTextFieldPad txtCodCartCobItRec = new JTextFieldPad( JTextFieldPad.TP_STRING, 3, 0 );

	private final JTextFieldFK txtDescCartCobItRec = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private final JTextFieldPad txtCodRec = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private final JTextFieldPad txtNParcRec = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private final JTextFieldPad txtVlrParcItRec = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, 2 );

	private final JTextFieldPad txtVlrDescItRec = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, 2 );

	private final JTextFieldPad txtDtVencItRec = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private final JTextFieldPad txtVlrParcRec = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, 2 );

	private final JTextFieldPad txtDtEmisRec = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private final JTextFieldPad txtDocRec = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 10, 0 );

	private final JTextFieldPad txtObs = new JTextFieldPad( JTextFieldPad.TP_STRING, 250, 0 );

	private final JTextFieldPad txtStatus = new JTextFieldPad( JTextFieldPad.TP_STRING, 2, 0 );

	private final Tabela tabRec = new Tabela();

	private final JScrollPane spnTab = new JScrollPane( tabRec );

	private final ListaCampos lcReceber = new ListaCampos( this );

	private final ListaCampos lcItReceber = new ListaCampos( this );

	private final ListaCampos lcCli = new ListaCampos( this, "CL" );

	private final ListaCampos lcPlanoPag = new ListaCampos( this, "PG" );

	private final ListaCampos lcTipoCob = new ListaCampos( this, "TC" );

	private final ListaCampos lcBanco = new ListaCampos( this, "BO" );

	private final ListaCampos lcCartCob = new ListaCampos( this, "CB" );

	private final ListaCampos lcBancoItRec = new ListaCampos( this, "BO" );

	private final ListaCampos lcCartCobItRec = new ListaCampos( this, "CB" );

	private final ListaCampos lcTipoCobItRec = new ListaCampos( this, "TC" );

	private final Navegador navRec = new Navegador( false );

	private final Navegador navItRec = new Navegador( false );
	
	private Component owner = null; 
	

	public DLNovoRec( Component cOrig ) {

		super( cOrig );
		this.owner = cOrig;
		setTitulo( "Novo" );
		setAtribos( 600, 350 );

		lcItReceber.setMaster( lcReceber );
		lcReceber.adicDetalhe( lcItReceber );
		lcItReceber.setTabela( tabRec );

		navRec.setName( "Receber" );
		lcReceber.setNavegador( navRec );
		navItRec.setName( "itpagar" );
		lcItReceber.setNavegador( navItRec );

		montaListaCampos();		
		
		montaTela();

		lcItReceber.montaTab();
		tabRec.setTamColuna( 70, 4 );
		tabRec.setTamColuna( 200, 5 );
		tabRec.setTamColuna( 70, 6 );
		tabRec.setTamColuna( 250, 7 );
		tabRec.setTamColuna( 70, 8 );
		tabRec.setTamColuna( 250, 9 );

		// Adiciona o mouse listener para que possa editar os itens.
		tabRec.addMouseListener( new MouseAdapter() {
			public void mouseClicked( MouseEvent mevt ) {
				if ( ( mevt.getClickCount() == 2 ) & ( tabRec.getLinhaSel() >= 0 ) ) {					
					alteraRec();
				}
			}
		} );

		lcReceber.addPostListener( this );
		lcTipoCob.addCarregaListener( this );
		//lcCli.addCarregaListener( this );
		txtCodTipoCob.addFocusListener( this );
	
	}
	
	private void montaListaCampos() {
		
		/***************
		 *  FNCLIENTE  *
		 ***************/
		lcCli.add( new GuardaCampo( txtCodCli, "CodCli", "Cód.cli.", ListaCampos.DB_PK, false ) );
		lcCli.add( new GuardaCampo( txtDescCli, "RazCli", "Razão social do cliente", ListaCampos.DB_SI, false ) );
		lcCli.montaSql( false, "CLIENTE", "VD" );
		lcCli.setQueryCommit( false );
		lcCli.setReadOnly( true );
		txtCodCli.setTabelaExterna( lcCli );
		txtCodCli.setFK( true );
		txtCodCli.setNomeCampo( "CodCli" );

		/****************
		 *  FNPLANOPAG  *
		 ****************/
		lcPlanoPag.add( new GuardaCampo( txtCodPlanoPag, "CodPlanoPag", "Cód.p.pag.", ListaCampos.DB_PK, false ) );
		lcPlanoPag.add( new GuardaCampo( txtDescPlanoPag, "DescPlanoPag", "Descrição do plano de pagamento", ListaCampos.DB_SI, false ) );
		lcPlanoPag.montaSql( false, "PLANOPAG", "FN" );
		lcPlanoPag.setQueryCommit( false );
		lcPlanoPag.setReadOnly( true );
		txtCodPlanoPag.setTabelaExterna( lcPlanoPag );
		txtCodPlanoPag.setFK( true );
		txtCodPlanoPag.setNomeCampo( "CodPlanoPag" );

		/***************
		 *  FNTIPOCOB  *
		 ***************/
		lcTipoCob.add( new GuardaCampo( txtCodTipoCob, "CodTipoCob", "Cód.Tip.Cob", ListaCampos.DB_PK, false ) );
		lcTipoCob.add( new GuardaCampo( txtDescTipoCob, "DescTipoCob", "Descrição do tipo de cobrança", ListaCampos.DB_SI, false ) );
		lcTipoCob.add( new GuardaCampo( txtObrigCart, "ObrigCartCob", "Obriga cart.cob.", ListaCampos.DB_SI, false ) );
		lcTipoCob.montaSql( false, "TIPOCOB", "FN" );
		lcTipoCob.setQueryCommit( false );
		lcTipoCob.setReadOnly( true );
		txtCodTipoCob.setTabelaExterna( lcTipoCob );
		txtCodTipoCob.setFK( true );
		txtCodTipoCob.setNomeCampo( "CodTipoCob" );
		
		/***************
		 *  FNCARTCOB  *
		 ***************/
		lcCartCob.add( new GuardaCampo( txtCodCartCob, "CodCartCob", "Cód.Cart.Cob.", ListaCampos.DB_PK, false ) );
		//lcCartCob.add( new GuardaCampo( txtCodBanco, "CodBanco", "Cód.banco", ListaCampos.DB_PF, false ) );
		lcCartCob.add( new GuardaCampo( txtDescCartCob, "DescCartCob", "Descrição da carteira de cobrança", ListaCampos.DB_SI, false ) );
		lcCartCob.montaSql( false, "CARTCOB", "FN" );
		lcCartCob.setQueryCommit( false );
		lcCartCob.setReadOnly( true );
		txtCodCartCob.setTabelaExterna( lcCartCob );
		txtCodCartCob.setFK( true );
		txtCodCartCob.setNomeCampo( "CodCartCob" );	
		
		/*************
		 *  FNBANCO  *
		 *************/
		lcBanco.add( new GuardaCampo( txtCodBanco, "CodBanco", "Cód.banco", ListaCampos.DB_PK, false ) );
		lcBanco.add( new GuardaCampo( txtDescBanco, "NomeBanco", "Nome do banco", ListaCampos.DB_SI, false ) );
		lcBanco.montaSql( false, "BANCO", "FN" );
		lcBanco.setQueryCommit( false );
		lcBanco.setReadOnly( true );
		txtCodBanco.setTabelaExterna( lcBanco );
		txtCodBanco.setFK( true );
		txtCodBanco.setNomeCampo( "CodBanco" );	
		
		/***************
		 *  FNRECEBER  *
		 ***************/
		lcReceber.add( new GuardaCampo( txtCodRec, "CodRec", "Cód.rec.", ListaCampos.DB_PK, true ) );
		lcReceber.add( new GuardaCampo( txtCodCli, "CodCli", "Cód.cli.", ListaCampos.DB_FK, true ) );
		lcReceber.add( new GuardaCampo( txtCodPlanoPag, "CodPlanoPag", "Cód.p.pag.", ListaCampos.DB_FK, true ) );
		lcReceber.add( new GuardaCampo( txtCodBanco, "CodBanco", "Cód.banco", ListaCampos.DB_FK, false ) );
		lcReceber.add( new GuardaCampo( txtCodTipoCob, "CodTipoCob", "Cód.tp.cob.", ListaCampos.DB_FK, false ) );
		lcReceber.add( new GuardaCampo( txtVlrParcRec, "VlrParcRec", "Valor parc.", ListaCampos.DB_SI, true ) );
		lcReceber.add( new GuardaCampo( txtVlrParcRec, "VlrAPagRec", "Valor a rec.", ListaCampos.DB_SI, true ) );
		lcReceber.add( new GuardaCampo( txtVlrParcRec, "VlrRec", "Valor tot.", ListaCampos.DB_SI, true ) );
		lcReceber.add( new GuardaCampo( txtDtEmisRec, "DataRec", "Dt.emissão", ListaCampos.DB_SI, true ) );
		lcReceber.add( new GuardaCampo( txtDocRec, "DocRec", "N.doc.", ListaCampos.DB_SI, true ) );
		lcReceber.add( new GuardaCampo( txtObs, "ObsRec", "Obs.", ListaCampos.DB_SI, false ) );
		lcReceber.add( new GuardaCampo( txtStatus, "StatusRec", "Status", ListaCampos.DB_SI, false ) );
		lcReceber.montaSql( true, "RECEBER", "FN" );
		
		
		/************************
		 *  FNTIPOCOB - DETALHE *
		 ************************/	
		lcTipoCobItRec.add( new GuardaCampo( txtCodTipoCobItRec, "CodTipoCob", "Cód.Tip.Cob", ListaCampos.DB_PK, false ) );
		lcTipoCobItRec.add( new GuardaCampo( txtDescTipoCobItRec, "DescTipoCob", "Descrição tipo de cobrança", ListaCampos.DB_SI, false ) );
		lcTipoCobItRec.montaSql( false, "TIPOCOB", "FN" );
		lcTipoCobItRec.setQueryCommit( false );
		lcTipoCobItRec.setReadOnly( true );
		txtCodTipoCobItRec.setTabelaExterna( lcTipoCobItRec );
		txtCodTipoCobItRec.setFK( true );
		txtCodTipoCobItRec.setNomeCampo( "CodTipoCob" );
		txtDescTipoCobItRec.setTabelaExterna( lcTipoCobItRec );
		txtDescTipoCobItRec.setLabel( "Descrição do tipo de cobrança" );
		
		/***************
		 *  FNCARTCOB  *
		 ***************/
		txtCodCartCobItRec.setNomeCampo( "CodCartCob" );
		lcCartCobItRec.add( new GuardaCampo( txtCodCartCobItRec, "CodCartCob", "Cód.Cart.Cob.", ListaCampos.DB_PK, false ) );
		lcCartCobItRec.add( new GuardaCampo( txtDescCartCobItRec, "DescCartCob", "Descrição da carteira de cobrança", ListaCampos.DB_SI, false ) );
		lcCartCobItRec.setWhereAdicSubSel( "CODBANCO=master.CODBANCO" );
		lcCartCobItRec.montaSql( false, "CARTCOB", "FN" );
		lcCartCobItRec.setQueryCommit( false );
		lcCartCobItRec.setReadOnly( true );
		txtCodCartCobItRec.setTabelaExterna( lcCartCobItRec );
		txtCodCartCobItRec.setListaCampos( lcCartCobItRec );
		txtDescCartCobItRec.setListaCampos( lcCartCobItRec );
		txtDescCartCobItRec.setLabel( "Descrição da carteira de cobrança" );
		txtCodCartCobItRec.setFK( true );

		/**********************
		 *  FNBANCO - DETALE  *
		 **********************/
		lcBancoItRec.add( new GuardaCampo( txtCodBancoItRec, "CodBanco", "Cód.Tip.Cob", ListaCampos.DB_PK, false ) );
		lcBancoItRec.add( new GuardaCampo( txtDescBancoItRec, "NomeBanco", "Descrição tipo de cobrança", ListaCampos.DB_SI, false ) );
		lcBancoItRec.montaSql( false, "BANCO", "FN" );
		lcBancoItRec.setQueryCommit( false );
		lcBancoItRec.setReadOnly( true );
		txtCodBancoItRec.setTabelaExterna( lcBancoItRec );
		txtCodBancoItRec.setFK( true );
		txtCodBancoItRec.setNomeCampo( "CodBanco" );
		txtDescBancoItRec.setTabelaExterna( lcBancoItRec );
		txtDescBancoItRec.setLabel( "Descrição do Banco" );

		/*****************
		 *  FNITRECEBER  *
		 *****************/
		txtNParcRec.setNomeCampo( "NParcRec" );
		lcItReceber.add( new GuardaCampo( txtNParcRec, "NParcItRec", "N.parc.", ListaCampos.DB_PK, false ) );
		lcItReceber.add( new GuardaCampo( txtVlrParcItRec, "VlrParcItRec", "Valor tot.", ListaCampos.DB_SI, false ) );
		lcItReceber.add( new GuardaCampo( txtDtVencItRec, "DtVencItRec", "Dt.vencto.", ListaCampos.DB_SI, false ) );
		lcItReceber.add( new GuardaCampo( txtVlrDescItRec, "VlrDescItRec", "Valor desc.", ListaCampos.DB_SI, false ) );
		lcItReceber.add( new GuardaCampo( txtCodTipoCobItRec, "CodTipoCob", "Cód.Tipo.Cob", ListaCampos.DB_FK, txtDescTipoCobItRec, false ) );
		lcItReceber.add( new GuardaCampo( txtCodBancoItRec, "CodBanco", "Cód.Banco", ListaCampos.DB_FK, txtDescBancoItRec, false ) );
		lcItReceber.add( new GuardaCampo( txtCodCartCobItRec, "CodCartCob", "Cód.Cart.Cob.", ListaCampos.DB_FK, txtDescCartCobItRec, false ) );
		lcItReceber.montaSql( false, "ITRECEBER", "FN" );
		lcItReceber.setQueryCommit( false );
		txtNParcRec.setListaCampos( lcItReceber );
		txtVlrParcItRec.setListaCampos( lcItReceber );
		txtVlrDescItRec.setListaCampos( lcItReceber );
		txtDtVencItRec.setListaCampos( lcItReceber );
		txtCodTipoCobItRec.setListaCampos( lcItReceber );
		txtCodBancoItRec.setListaCampos( lcItReceber );
		txtCodCartCobItRec.setListaCampos( lcItReceber );
	}
	
	private void montaTela() {		

		c.add( pnRec );

		pnRec.add( pinCab, BorderLayout.NORTH );
		pnRec.add( spnTab, BorderLayout.CENTER );

		setPainel( pinCab );
		adic( new JLabelPad( "Cód.cli." ), 7, 0, 250, 20 );
		adic( txtCodCli, 7, 20, 80, 20 );
		adic( new JLabelPad( "Razão social do cliente" ), 90, 0, 250, 20 );
		adic( txtDescCli, 90, 20, 197, 20 );
		adic( new JLabelPad( "Cód.p.pag." ), 290, 0, 250, 20 );
		adic( txtCodPlanoPag, 290, 20, 80, 20 );
		adic( new JLabelPad( "Descrição do plano de pagto." ), 373, 0, 250, 20 );
		adic( txtDescPlanoPag, 373, 20, 200, 20 );
		
		adic( new JLabelPad( "Cod.Tip.Cob." ), 7, 40, 250, 20 );
		adic( txtCodTipoCob, 7, 60, 80, 20 );
		adic( new JLabelPad( "Descrição Tipo Cobrança" ), 90, 40, 200, 20 );
		adic( txtDescTipoCob, 90, 60, 197, 20 );
		adic( new JLabelPad( "Cód.banco" ), 290, 40, 250, 20 );
		adic( txtCodBanco, 290, 60, 80, 20 );
		adic( new JLabelPad( "Descriçao do banco" ), 373, 40, 250, 20 );
		adic( txtDescBanco, 373, 60, 200, 20 );
		
		adic( new JLabelPad( "Cod.Cart.Cob." ), 7, 80, 250, 20 );
		adic( txtCodCartCob, 7, 100, 80, 20 );
		adic( new JLabelPad( "Descrição da Carteira de Cob." ), 90, 80, 200, 20 );
		adic( txtDescCartCob, 90, 100, 197, 20 );
		
		adic( new JLabelPad( "Valor" ), 290, 80, 90, 20 );
		adic( txtVlrParcRec, 290, 100, 90, 20 );
		
		adic( new JLabelPad( "Data de Emissão" ), 383, 80, 100, 20 );
		adic( txtDtEmisRec, 383, 100, 100, 20 );
		adic( new JLabelPad( "Doc." ), 486, 80, 80, 20 );
		adic( txtDocRec, 486, 100, 85, 20 );
		
		adic( new JLabelPad( "Observações" ), 7, 120, 300, 20 );
		adic( txtObs, 7, 140, 565, 20 );
	}

	private void testaCodRec() { // Traz o verdadeiro número do codrec

		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			
			ps = con.prepareStatement( "SELECT * FROM SPGERANUM(?,?,?)" );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "FNRECEBER" ) );
			ps.setString( 3, "RC" );
			rs = ps.executeQuery();
			
			if ( rs.next() ) {
				txtCodRec.setVlrString( rs.getString( 1 ) );
			}
			
			rs.close();
			ps.close();
			con.commit();
		} catch ( SQLException err ) {
			Funcoes.mensagemErro( this, "Erro ao confirmar código da conta a receber!\n" + err.getMessage(), true, con, err );
		}
	}
	
	private void setCarteira() {
		
		if ( ( txtCodBanco.getVlrString() != null && txtCodBanco.getVlrString().trim().length()>0 ) 
				&& ( txtCodCartCob.getVlrString() != null && txtCodCartCob.getVlrString().trim().length()>0 ) ) {
			
			try {
				
				String sql = "UPDATE FNITRECEBER SET CODCARTCOB=?, CODEMPCB=?, CODFILIALCB=? WHERE CODEMP=? AND CODFILIAL=? AND CODREC=?";
				
				PreparedStatement ps = con.prepareStatement( sql );
				ps.setString( 1, txtCodCartCob.getVlrString() );
				ps.setInt( 2, Aplicativo.iCodEmp );
				ps.setInt( 3, ListaCampos.getMasterFilial( "FNITRECEBER" ) );
				ps.setInt( 4, Aplicativo.iCodEmp );
				ps.setInt( 5, ListaCampos.getMasterFilial( "FNRECEBER" ) );
				ps.setInt( 6, txtCodRec.getVlrInteger() );
				ps.executeUpdate();
				
				ps.close();
				
				con.commit();
				
				lcReceber.carregaDados();
			}
			catch ( Exception e ) {
				e.printStackTrace();
				Funcoes.mensagemErro( this, "Erro ao definir carteira de cobrança.\n" + e.getMessage() );
			}
		}
	}
	
	private void alteraRec() {
		
//		lcReceber.carregaDados();
//		lcItReceber.carregaDados();
		lcItReceber.edit();
		
		DLFechaParcela dl = new DLFechaParcela( this, con ); 
				
		Object[] valores = new Object[] {
				txtVlrParcItRec.getVlrBigDecimal(),
				txtDtVencItRec.getVlrDate(), 
				txtVlrDescItRec.getVlrBigDecimal(), 
				txtCodTipoCobItRec.getVlrInteger(), 
				txtCodBancoItRec.getVlrString(), 
				txtCodCartCobItRec.getVlrString()
		};
		
		try {
			
			dl.setValores( valores );
			dl.setVisible( true );
			
			if ( dl.OK ) {
				
				valores = dl.getValores();
				
				txtVlrParcItRec.setVlrBigDecimal( (BigDecimal) valores[ DLFechaParcela.EFields.VALOR.ordinal() ] );
				txtDtVencItRec.setVlrDate( (Date) valores[ DLFechaParcela.EFields.DATA.ordinal() ] );
				txtVlrDescItRec.setVlrBigDecimal( (BigDecimal) valores[ DLFechaParcela.EFields.DESCONTO.ordinal() ] );
				txtCodTipoCobItRec.setVlrString( (String) valores[ DLFechaParcela.EFields.TIPOCOB.ordinal() ] );
				txtCodBancoItRec.setVlrString( (String) dl.getValores()[ DLFechaParcela.EFields.BANCO.ordinal() ] );
				txtCodCartCobItRec.setVlrString( (String) dl.getValores()[ DLFechaParcela.EFields.CARTCOB.ordinal() ] );
				
				if ( lcItReceber.post() ) {
					// Atualiza lcReceber
					if ( lcReceber.getStatus() == ListaCampos.LCS_EDIT ) {
						lcReceber.post(); // Caso o lcReceber estaja como edit executa o post que atualiza
					}
					else {
						lcReceber.carregaDados(); // Caso não, atualiza
					}
				}
				dl.dispose();
			}
			else {
				dl.dispose();
				lcItReceber.cancel( true );
				lcReceber.carregaDados();
			}
		}
		catch ( Exception e ) {
			e.printStackTrace();
			Funcoes.mensagemErro( this, "Erro ao atualizar parcelas.\n" + e.getMessage() );
			lcItReceber.cancel( true );
			lcReceber.cancel( true );
		}
	}
	
	private boolean isValido() {
		
		if ( txtCodCli.getVlrString().trim().length() == 0 ) {
			Funcoes.mensagemErro( this, "Cód.cli. é requerido!" );
			txtCodCli.requestFocus();
			return false;
		}
		
		if ( txtCodPlanoPag.getVlrString().trim().length() == 0 ) {
			Funcoes.mensagemErro( this, "Cód.p.pag. é requerido!" );
			txtCodPlanoPag.requestFocus();
			return false;
		}
		if ( "S".equalsIgnoreCase( txtObrigCart.getVlrString() ) ) {
			if ( txtCodBanco.getVlrString().trim().length() == 0 ) {
				Funcoes.mensagemErro( this, "Cód.banco é requerido!" );
				txtCodBanco.requestFocus();
				return false;
			}
			else if ( txtCodCartCob.getVlrString().trim().length() == 0 ) {
				Funcoes.mensagemErro( this, "Cód.cart.cob. é requerido!" );
				txtCodCartCob.requestFocus();
				return false;
			}
		}
		if ( txtVlrParcRec.getVlrString().trim().length() == 0 ) {
			Funcoes.mensagemErro( this, "Valor é requerido!" );
			txtVlrParcRec.requestFocus();
			return false;
		}
		if ( txtDtEmisRec.getVlrString().length() < 10 ) {
			Funcoes.mensagemErro( this, "Data de emissão é requerido!" );
			txtDtEmisRec.requestFocus();
			return false;
		}
		if ( txtDocRec.getVlrString().trim().length() == 0 ) {
			Funcoes.mensagemErro( this, "Doc. é requerido!" );
			txtDocRec.requestFocus();
			return false;
		}
		return true;
	}
	
	public void focusGained( FocusEvent e ) { }

	public void focusLost( FocusEvent e ) {
		
		if ( e.getSource() == txtCodTipoCob ) { 
			
			lcTipoCob.carregaDados();
			
			if ( ! "S".equalsIgnoreCase( txtObrigCart.getVlrString() ) ) {
				txtCodBanco.setRequerido( false );
				txtCodCartCob.setRequerido( false );
			}
		}
	}

	public void afterCarrega( CarregaEvent e ) {
		
		if ( e.getListaCampos() == lcTipoCob
				&& "S".equalsIgnoreCase( txtObrigCart.getVlrString() ) ) {
			txtCodBanco.setRequerido( true );
			txtCodCartCob.setRequerido( true );
		} 
		/*else if (e.getListaCampos() == lcCli ) {
			mostraRestricao();
		}*/
	}

	private boolean mostraRestricao() {
	    return DLRestrCli.execRestrCli( this.owner, con, txtCodCli.getVlrInteger()  );	
	}
	
	public void beforeCarrega( CarregaEvent e ) { }
	
	public void beforePost( PostEvent evt ) {

		if ( ( evt.getListaCampos().equals( lcReceber ) ) & ( lcReceber.getStatus() == ListaCampos.LCS_INSERT ) ) {
			testaCodRec();
			txtStatus.setVlrString( "R1" );
		}
	}

	public void afterPost( PostEvent evt ) { }

	public void actionPerformed( ActionEvent evt ) {

		if ( evt.getSource() == btOK ) {
			if ( isValido() ) {				
				if ( lcReceber.getStatus() == ListaCampos.LCS_INSERT ) {
					if ( !mostraRestricao() ) {
						return;
					}
					if ( lcReceber.post() ) {
						setCarteira();
					}
				}
				else {
					super.actionPerformed( evt );
				}				
			}
		}
		else {
			super.actionPerformed( evt );
		}
	}

	public void setConexao( DbConnection cn ) {

		super.setConexao( cn );
		lcCli.setConexao( cn );
		lcPlanoPag.setConexao( cn );
		lcReceber.setConexao( cn );
		lcItReceber.setConexao( cn );
		lcTipoCob.setConexao( cn );
		lcBanco.setConexao( cn );
		lcCartCob.setConexao( cn );
		lcTipoCobItRec.setConexao( cn );
		lcBancoItRec.setConexao( cn );
		lcCartCobItRec.setConexao( cn );
		lcReceber.insert( true );
	}
}
