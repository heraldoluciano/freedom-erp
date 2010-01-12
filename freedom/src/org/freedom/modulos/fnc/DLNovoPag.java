/**
 * @version 14/07/2003 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.std <BR>
 * Classe:
 * @(#)DLNovoPag.java <BR>
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

package org.freedom.modulos.fnc;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.modulos.std.DLFechaPag;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import javax.swing.JScrollPane;

import org.freedom.acao.PostEvent;
import org.freedom.acao.PostListener;
import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JPanelPad;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.Navegador;
import org.freedom.componentes.Tabela;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FFDialogo;

public class DLNovoPag extends FFDialogo implements PostListener {

	private static final long serialVersionUID = 1L;

	private JPanelPad pnPag = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private JPanelPad pinCab = new JPanelPad( 580, 170 );

	private JTextFieldPad txtCodFor = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	
	private JTextFieldFK txtCNPJFor = new JTextFieldFK( JTextFieldPad.TP_STRING, 14, 0 );

	private JTextFieldFK txtDescFor = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtCodPlanoPag = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtDescPlanoPag = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private final JTextFieldPad txtCodTipoCob = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private final JTextFieldFK txtDescTipoCob = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );
	
	private JTextFieldPad txtCodBanco = new JTextFieldPad( JTextFieldPad.TP_STRING, 3, 0 );

	private JTextFieldFK txtDescBanco = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private JTextFieldPad txtCodPag = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtNParcPag = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtVlrParcItPag = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, 2 );

	private JTextFieldPad txtDtVencItPag = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtVlrParcPag = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, 2 );

	private JTextFieldPad txtDtEmisPag = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtDocPag = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 10, 0 );

	private JTextFieldPad txtObs = new JTextFieldPad( JTextFieldPad.TP_STRING, 50, 0 );
	
	private final JTextFieldPad txtCodTipoCobItPag = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private final JTextFieldFK txtDescTipoCobItPag = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );
	
	private JTextFieldPad txtCodConta = new JTextFieldPad( JTextFieldPad.TP_STRING, 10, 0 );

	private JTextFieldFK txtDescConta = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private Tabela tabPag = new Tabela();

	private JScrollPane spnTab = new JScrollPane( tabPag );

	private ListaCampos lcPagar = new ListaCampos( this );

	private final ListaCampos lcTipoCob = new ListaCampos( this, "TC" );
	
	private ListaCampos lcItPagar = new ListaCampos( this );

	private ListaCampos lcFor = new ListaCampos( this, "FR" );

	private ListaCampos lcPlanoPag = new ListaCampos( this, "PG" );

	private ListaCampos lcBanco = new ListaCampos( this, "BO" );

	private Navegador navPagar = new Navegador( false );

	private Navegador navItPagar = new Navegador( false );
	
	private final ListaCampos lcTipoCobItPag = new ListaCampos( this, "TC" );
	
	private ListaCampos lcConta = new ListaCampos( this, "CA" );
	
	public DLNovoPag( Component cOrig ) {

		super( cOrig );
		setTitulo( "Novo" );
		setAtribos( 600, 320 );

		montaListaCampos();
		montaTela();
		
		lcPagar.addPostListener( this );
	}

	private void montaListaCampos() {
		
		lcItPagar.setMaster( lcPagar );
		lcPagar.adicDetalhe( lcItPagar );
		lcItPagar.setTabela( tabPag );
		navPagar.setName( "Pagar" );
		lcPagar.setNavegador( navPagar );

		navItPagar.setName( "itpagar" );
		lcItPagar.setNavegador( navItPagar );

		lcFor.add( new GuardaCampo( txtCodFor, "CodFor", "Cód.for.", ListaCampos.DB_PK, false ) );
		lcFor.add( new GuardaCampo( txtDescFor, "RazFor", "Razão social do fornecedor", ListaCampos.DB_SI, false ) );
		lcFor.add( new GuardaCampo( txtCNPJFor, "CnpjFor", "CNPJ", ListaCampos.DB_SI, false ) );
		
		lcFor.montaSql( false, "FORNECED", "CP" );
		lcFor.setQueryCommit( false );
		lcFor.setReadOnly( true );
		txtCodFor.setTabelaExterna( lcFor );
		txtCodFor.setFK( true );
		txtCodFor.setNomeCampo( "CodFor" );

		lcPlanoPag.add( new GuardaCampo( txtCodPlanoPag, "CodPlanoPag", "Cód.p.pg.", ListaCampos.DB_PK, false ) );
		lcPlanoPag.add( new GuardaCampo( txtDescPlanoPag, "DescPlanoPag", "Descrição do plano de pagamento", ListaCampos.DB_SI, false ) );
		lcPlanoPag.montaSql( false, "PLANOPAG", "FN" );
		lcPlanoPag.setQueryCommit( false );
		lcPlanoPag.setReadOnly( true );
		txtCodPlanoPag.setTabelaExterna( lcPlanoPag );
		txtCodPlanoPag.setFK( true );
		txtCodPlanoPag.setNomeCampo( "CodPlanoPag" );

		lcBanco.add( new GuardaCampo( txtCodBanco, "CodBanco", "Cód.banco", ListaCampos.DB_PK, false ) );
		lcBanco.add( new GuardaCampo( txtDescBanco, "NomeBanco", "Nome do banco", ListaCampos.DB_SI, false ) );
		lcBanco.montaSql( false, "BANCO", "FN" );
		lcBanco.setQueryCommit( false );
		lcBanco.setReadOnly( true );
		txtCodBanco.setTabelaExterna( lcBanco );
		txtCodBanco.setFK( true );
		txtCodBanco.setNomeCampo( "CodBanco" );
		
		/**********************
		 *  FNCONTA 		  *
		 **********************/
		lcConta.add( new GuardaCampo( txtCodConta, "NumConta", "Nº Conta", ListaCampos.DB_PK, false ) );
		lcConta.add( new GuardaCampo( txtDescConta, "DescConta", "Descrição da conta", ListaCampos.DB_SI, false ) );
		lcConta.montaSql( false, "CONTA", "FN" );
		lcConta.setQueryCommit( false );		
		lcConta.setReadOnly( true );		
		txtCodConta.setTabelaExterna( lcConta );		
		txtCodConta.setFK( true );		
		txtCodConta.setNomeCampo( "NumConta" );
		txtDescConta.setTabelaExterna( lcConta );
		txtDescConta.setLabel( "Descrição da Conta" );		

		/***************
		 *  FNTIPOCOB  *
		 ***************/
		lcTipoCob.add( new GuardaCampo( txtCodTipoCob, "CodTipoCob", "Cód.Tip.Cob", ListaCampos.DB_PK, false ) );
		lcTipoCob.add( new GuardaCampo( txtDescTipoCob, "DescTipoCob", "Descrição do tipo de cobrança", ListaCampos.DB_SI, false ) );		
		lcTipoCob.montaSql( false, "TIPOCOB", "FN" );
		lcTipoCob.setQueryCommit( false );
		lcTipoCob.setReadOnly( true );
		txtCodTipoCob.setTabelaExterna( lcTipoCob );
		txtCodTipoCob.setFK( true );
		txtCodTipoCob.setNomeCampo( "CodTipoCob" );
		
	}
	
	private void montaTela() {			
		
		lcPagar.add( new GuardaCampo( txtCodPag, "CodPag", "Cód.pag.", ListaCampos.DB_PK, true ) );
		lcPagar.add( new GuardaCampo( txtCodFor, "CodFor", "Cód.for.", ListaCampos.DB_FK, true ) );
		lcPagar.add( new GuardaCampo( txtCodPlanoPag, "CodPlanoPag", "Cód.p.pg.", ListaCampos.DB_FK, true ) );
		lcPagar.add( new GuardaCampo( txtCodBanco, "CodBanco", "Cód.banco", ListaCampos.DB_FK, false ) );
		lcPagar.add( new GuardaCampo( txtCodTipoCob, "CodTipoCob", "Cód.tp.cob.", ListaCampos.DB_FK, false ) );
		lcPagar.add( new GuardaCampo( txtVlrParcPag, "VlrParcPag", "Valor da parc.", ListaCampos.DB_SI, false ) );
		lcPagar.add( new GuardaCampo( txtDtEmisPag, "DataPag", "Dt.emissão", ListaCampos.DB_SI, true ) );
		lcPagar.add( new GuardaCampo( txtDocPag, "DocPag", "N.documento", ListaCampos.DB_SI, true ) );
		lcPagar.add( new GuardaCampo( txtObs, "ObsPag", "Obs.", ListaCampos.DB_SI, false ) );		
		lcPagar.add( new GuardaCampo( txtCodConta, "NumConta", "Cód.Conta", ListaCampos.DB_FK, txtDescConta, false ) );
		
		lcPagar.montaSql( true, "PAGAR", "FN" );

		txtNParcPag.setNomeCampo( "NParcPag" );
		lcItPagar.add( new GuardaCampo( txtNParcPag, "NParcPag", "N.parc.", ListaCampos.DB_PK, false ) );
		lcItPagar.add( new GuardaCampo( txtVlrParcItPag, "VlrParcItPag", "Valor tot.", ListaCampos.DB_SI, false ) );
		lcItPagar.add( new GuardaCampo( txtDtVencItPag, "DtVencItPag", "Dt.vencto.", ListaCampos.DB_SI, false ) );
		lcItPagar.montaSql( false, "ITPAGAR", "FN" );
		lcItPagar.setQueryCommit( false );
		txtNParcPag.setListaCampos( lcItPagar );
		txtVlrParcItPag.setListaCampos( lcItPagar );
		txtDtVencItPag.setListaCampos( lcItPagar );	
		
		lcItPagar.montaTab();
		tabPag.addMouseListener( new HandlerMouseListenerPagamento() );
		
		c.add( pnPag );

		pnPag.add( pinCab, BorderLayout.NORTH );
		pnPag.add( spnTab, BorderLayout.CENTER );

		setPainel( pinCab );
		
		adic( new JLabelPad( "Cód.for." ), 7, 0, 250, 20 );
		adic( txtCodFor, 7, 20, 80, 20 );
		adic( new JLabelPad( "Razão social do fornecedor" ), 90, 0, 250, 20 );
		adic( txtDescFor, 90, 20, 197, 20 );
		adic( new JLabelPad( "Cód.p.pag." ), 290, 0, 250, 20 );
		adic( txtCodPlanoPag, 290, 20, 80, 20 );
		adic( new JLabelPad( "Descrição do plano de pagto." ), 373, 0, 250, 20 );
		adic( txtDescPlanoPag, 373, 20, 200, 20 );
		
		adic( new JLabelPad( "Cód.Tip.Cob." ), 7, 40, 250, 20 );
		adic( txtCodTipoCob, 7, 60, 80, 20 );
		adic( new JLabelPad( "Descrição Tipo Cobrança" ), 90, 40, 200, 20 );
		adic( txtDescTipoCob, 90, 60, 197, 20 );
		
		adic( new JLabelPad( "Cód.banco" ), 290, 40, 250, 20 );
		adic( txtCodBanco, 290, 60, 80, 20 );
		adic( txtDescBanco, 373, 60, 200, 20 );

		adic( new JLabelPad( "Nº Conta" ), 7, 80, 250, 20 );
		adic( txtCodConta, 7, 100, 80, 20 );
		adic( new JLabelPad( "Descrição da conta" ), 90, 80, 200, 20 );
		adic( txtDescConta, 90, 100, 197, 20 );
		
		adic( new JLabelPad( " Valor" ), 290, 80, 80, 20 );
		adic( txtVlrParcPag, 290, 100, 80, 20 );
		
		adic( new JLabelPad( "Dt.Emissão" ), 373, 80, 80, 20 );
		adic( txtDtEmisPag, 373, 100, 80, 20 );
		
		adic( new JLabelPad( "Doc." ), 456, 80, 117, 20 );
		adic( txtDocPag, 456, 100, 117, 20 );
		
		adic( new JLabelPad( "Observações" ), 7, 120, 300, 20 );
		adic( txtObs, 7, 140, 565, 20 );
	}
	
	public void setValues( Object[] values ) {

		txtCodFor.setVlrInteger( values[0] != null ? (Integer) values[0] : 0 );
		txtCodPlanoPag.setVlrInteger( values[1] != null ? (Integer) values[1] : 0 );
		txtCodBanco.setVlrString( values[2] != null ? (String) values[2] : "" );
		txtCodTipoCob.setVlrInteger( values[3] != null ? (Integer) values[3] : 0 );
		txtVlrParcPag.setVlrBigDecimal( values[4] != null ? (BigDecimal) values[4] : new BigDecimal( "0.00" ) );
		txtDtEmisPag.setVlrDate( values[5] != null ? (Date) values[5] : new Date() );
		txtDocPag.setVlrString( values[6] != null ? (String) values[6] : "" );
		txtObs.setVlrString( values[7] != null ? (String) values[7] : "" );

		lcTipoCob.carregaDados();
		lcBanco.carregaDados();
		lcPlanoPag.carregaDados();
		lcFor.carregaDados();
	}
	
	public int getCodigoPagamento() {
		
		return lcPagar.getStatus() == ListaCampos.LCS_SELECT ? txtCodPag.getVlrInteger() : 0;
	}

	private void testaCodPag() { 
		
		try {
			
			PreparedStatement ps = con.prepareStatement( "SELECT * FROM SPGERANUM(?,?,?)" );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "FNPAGAR" ) );
			ps.setString( 3, "PA" );
			
			ResultSet rs = ps.executeQuery();
			
			if ( rs.next() ) {
				txtCodPag.setVlrString( rs.getString( 1 ) );
			}
		
			rs.close();
			ps.close();
			
			con.commit();
		} catch ( SQLException err ) {
			err.printStackTrace();
			Funcoes.mensagemErro( this, "Erro ao confirmar código da venda!\n" + err.getMessage(), true, con, err );
		}
	}

	public void beforePost( PostEvent e ) {
		if ( e.getListaCampos().equals( lcPagar ) && lcPagar.getStatus() == ListaCampos.LCS_INSERT ) {			
			testaCodPag();
		}
	}

	public void afterPost( PostEvent e ) { }

	public void actionPerformed( ActionEvent e ) {

		if ( e.getSource() == btOK ) {
			
			if ( txtDtEmisPag.getVlrString().length() < 10 ) {				
				Funcoes.mensagemInforma( this, "Data de emissão é requerido!" );
			}
			else {				
				if ( lcPagar.getStatus() == ListaCampos.LCS_INSERT ) {					
					lcPagar.post();
				}
				else {					
					super.actionPerformed( e );
				}
			}
		}
		else {			
			super.actionPerformed( e );
		}
	}

	public void setConexao( DbConnection cn ) {

		super.setConexao( cn );
		lcFor.setConexao( cn );
		lcPlanoPag.setConexao( cn );
		lcConta.setConexao( cn );
		lcTipoCob.setConexao( cn );
		lcPagar.setConexao( cn );
		lcItPagar.setConexao( cn );
		lcBanco.setConexao( cn );
		lcPagar.insert( true );
		
	}
	
	private class HandlerMouseListenerPagamento extends MouseAdapter {
		
		public void mouseClicked( MouseEvent mevt ) {
			
			if ( mevt.getClickCount() == 2 && tabPag.getLinhaSel() >= 0 ) {							
				lcItPagar.edit();							
				DLFechaPag dl = new DLFechaPag( DLNovoPag.this, txtVlrParcItPag.getVlrBigDecimal(), txtDtVencItPag.getVlrDate() );							
				dl.setVisible( true );							
				if ( dl.OK ) {								
					txtVlrParcItPag.setVlrBigDecimal( (BigDecimal) dl.getValores()[ 0 ] );
					txtDtVencItPag.setVlrDate( (Date) dl.getValores()[ 1 ] );
					lcItPagar.post();								
					// Atualiza lcPagar
					if ( lcPagar.getStatus() == ListaCampos.LCS_EDIT ) {									
						lcPagar.post(); // Caso o lcPagar estaja como edit executa o post que atualiza
					}
					else {								
						lcPagar.carregaDados(); // Caso não, atualiza
					}
				}
				else {								
					dl.dispose();
					lcItPagar.cancel( false );
				}							
				dl.dispose();
			}
		}
	}
}
