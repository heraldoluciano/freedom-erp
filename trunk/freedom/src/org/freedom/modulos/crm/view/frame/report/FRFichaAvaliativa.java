/**
 * @version 28/09/2011 <BR>
 * @author Setpoint Informática Ltda. / Bruno Nascimento<BR>
 * 
 *         Projeto: Freedom <BR>
 * 
 *         Pacote: org.freedom.modulos.tmk <BR>
 *         Classe: @(#)FRCronograma.java <BR>
 * 
 *         Este arquivo é parte do sistema Freedom-ERP, o Freedom-ERP é um software livre; você pode redistribui-lo e/ou <BR>
 *         modifica-lo dentro dos termos da Licença Pública Geral GNU como publicada pela Fundação do Software Livre (FSF); <BR>
 *         na versão 2 da Licença, ou (na sua opnião) qualquer versão. <BR>
 *         Este programa é distribuido na esperança que possa ser util, mas SEM NENHUMA GARANTIA; <BR>
 *         sem uma garantia implicita de ADEQUAÇÂO a qualquer MERCADO ou APLICAÇÃO EM PARTICULAR. <BR>
 *         Veja a Licença Pública Geral GNU para maiores detalhes. <BR>
 *         Você deve ter recebido uma cópia da Licença Pública Geral GNU junto com este programa, se não, <BR>
 *         de acordo com os termos da LPG-PC <BR>
 * <BR>
 * 
 *         Relatório Cronograma Sintético.
 * 
 */

package org.freedom.modulos.crm.view.frame.report;

import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.SwingConstants;

import org.freedom.acao.CarregaEvent;
import org.freedom.acao.CarregaListener;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.functions.Funcoes;
import org.freedom.library.persistence.GuardaCampo;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.component.JCheckBoxPad;
import org.freedom.library.swing.component.JLabelPad;
import org.freedom.library.swing.component.JTextFieldFK;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.frame.FRelatorio;
import org.freedom.modulos.crm.view.frame.crud.plain.FMotivoAval;
import org.freedom.modulos.crm.view.frame.crud.tabbed.FContato;

public class FRFichaAvaliativa extends FRelatorio implements CarregaListener{

	private static final long serialVersionUID = 1L;
	
	private JTextFieldPad txtDataini = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtDatafim = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );
	
	
	/** FICHA AVALIATIVA
	 * 
	 */
	
	private JTextFieldPad txtCodCto = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtRazCto = new JTextFieldFK( JTextFieldFK.TP_STRING, 50, 0 );
	
	private JTextFieldPad txtCodSetor = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	
	private JTextFieldPad txtCodTipoCli = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 10, 0 );
	
	private JCheckBoxPad cbFinaliCriFichaAval = new JCheckBoxPad( " Criança ?", "S", "N" );
	
	private JCheckBoxPad cbFinaliAniFichaAval = new JCheckBoxPad( " Animal ?", "S", "N" );
	
	private JCheckBoxPad cbFinaliOutFichaAval = new JCheckBoxPad( " Outros ?", "S", "N" );
	
	private JCheckBoxPad cbCobertFichaAval = new JCheckBoxPad( " INDICA SE É COBERTURA ?", "S", "N" );
	
	private JCheckBoxPad cbEstrutFichaAval = new JCheckBoxPad( "HÁ NECESSIDADE DE ESTRUTURA ?", "S", "N" );
	
	private JCheckBoxPad cbOcupadoFichaAval = new JCheckBoxPad( "IMÓVEL OCUPADO ?", "S", "N" );
	
	private JCheckBoxPad cbSacadaFichaAval = new JCheckBoxPad( "SACADAS ?", "S", "N" );
	
	private JTextFieldPad txtCodMotAval= new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtDescMotAval = new JTextFieldFK( JTextFieldFK.TP_STRING, 50, 0 );
	
	private ListaCampos lcContato = new ListaCampos( this );
	
	private ListaCampos lcMotAval = new ListaCampos( this );
	
	public FRFichaAvaliativa() {
		
		setTitulo( "Ações realizadas" );
		setAtribos( 80, 80, 410	, 340 );
		
		montaListaCampos();
		montaTela();
		
	}
	
	private void montaTela(){
		JLabelPad lbLinha = new JLabelPad();
		lbLinha.setBorder( BorderFactory.createEtchedBorder() );
		JLabelPad lbPeriodo = new JLabelPad( "Período:", SwingConstants.CENTER );
		lbPeriodo.setOpaque( true );

		adic( lbPeriodo, 7, 1, 80, 20 );
		adic( lbLinha, 5, 10, 300, 45 );
		
		adic( new JLabelPad( "De:" ), 15, 25, 25, 20 );
		adic( txtDataini, 42, 25, 95, 20 );
		adic( new JLabelPad( "Até:" ), 148, 25, 25, 20 );
		adic( txtDatafim, 178, 25, 95, 20 );
		adic( txtCodCto, 7, 80, 80, 20, "Cod.Contato" );
		adic( txtRazCto, 90, 80, 225, 20, "Nome do contato" );
		adic( txtCodMotAval, 7, 120, 80, 20, "Cod.Mot.Aval.");
		adic( txtDescMotAval, 90, 120, 225, 20, "Descrição do motivo da avaliação." );
		adic( cbCobertFichaAval, 7, 150, 225, 20, "" );
		adic( cbEstrutFichaAval, 7, 175, 225, 20, "" );
		adic( cbOcupadoFichaAval, 7, 200, 225, 20, "" );
		adic( cbSacadaFichaAval, 7, 225, 225, 20, "" );
				
		Calendar cPeriodo = Calendar.getInstance();
		txtDatafim.setVlrDate( cPeriodo.getTime() );
	
		cPeriodo.set( Calendar.DAY_OF_MONTH, cPeriodo.get( Calendar.DAY_OF_MONTH ) - 30 );
		txtDataini.setVlrDate( cPeriodo.getTime() );
	}
	
	private void montaListaCampos() {
		
		
		/**********************
		 * Contato * *
		 *******************/

		txtCodCto.setTabelaExterna( lcContato, FContato.class.getCanonicalName());
		txtCodCto.setFK( true );
		txtCodCto.setNomeCampo( "Codcto" );
		lcContato.add( new GuardaCampo( txtCodCto, "CodCto", "Cód.Contato", ListaCampos.DB_PK, false ) );
		lcContato.add( new GuardaCampo( txtRazCto, "RazCto", "Razão do contato.", ListaCampos.DB_SI, false ) );
		lcContato.add( new GuardaCampo( txtCodSetor, "CodSetor", "Cód.setor", ListaCampos.DB_SI, false ) );
		lcContato.add( new GuardaCampo( txtCodTipoCli, "CodTipoCli", "Cód.Tipo Cli.", ListaCampos.DB_SI, false ) );
		lcContato.montaSql( false, "CONTATO", "TK" );
		lcContato.setReadOnly( true );
		lcContato.setQueryCommit( false );
		
		
		/**********************
		 * Motivo Aval. * *
		 *******************/

		txtCodMotAval.setTabelaExterna( lcMotAval, FMotivoAval.class.getCanonicalName());
		txtCodMotAval.setFK( true );
		txtCodMotAval.setNomeCampo( "MotAval" );
		lcMotAval.add( new GuardaCampo( txtCodMotAval, "CodMotAval", "Cód.Motivo", ListaCampos.DB_PK, false ) );
		lcMotAval.add( new GuardaCampo( txtDescMotAval, "DescMotAval", "Descrição do motivo da avaliação.", ListaCampos.DB_SI, false ) );
		lcMotAval.montaSql( false, "MotivoAval", "CR" );
		lcMotAval.setReadOnly( true );
		lcMotAval.setQueryCommit( false );
	}

	public void imprimir( boolean bVisualizar ) {
		
		if ( txtDatafim.getVlrDate().before( txtDataini.getVlrDate() ) ) {
			Funcoes.mensagemInforma( this, "Data inicial maior que a data final!" );
			return;
		}
		
		try{
		

		} catch (Exception err) {
			Funcoes.mensagemErro( this, "Erro consulta Relatório Ações Realizadas\n" + err.getMessage(), true, con, err );
		}

		//imprimiGrafico( bVisualizar, rs,  sCab, sTitle, fotoemp, totgeral, totcob );

	}
/*
	private void imprimiGrafico( boolean bVisualizar, ResultSet rs, String sCab, String sTitle, Blob fotoemp, BigDecimal totgeral, BigDecimal totcob) {
		String report = "layout/rel/REL_ACOES_REALIZADAS.jasper";
		String label = "Relatório de Ações realizadas";
		
	    HashMap<String, Object> hParam = new HashMap<String, Object>();
		hParam.put( "SUBREPORT_DIR", "org/freedom/layout/rel/" );
		hParam.put( "CODEMP", new Integer(Aplicativo.iCodEmp) );
		hParam.put( "CODFILIAL", new Integer(ListaCampos.getMasterFilial( "VDCONTRATO" )) );
		hParam.put( "CODCONTR", txtCodContr.getVlrInteger() );
		hParam.put( "TITULO", sTitle );
		hParam.put( "totgeral", totgeral );
		hParam.put( "totcob", totcob );
		
	    try {
			hParam.put( "LOGOEMP",  new ImageIcon(fotoemp.getBytes(1, ( int ) fotoemp.length())).getImage() );
	
		} catch ( SQLException e ) {
			Funcoes.mensagemErro( this, "Erro carregando logotipo !\n" + e.getMessage()  );
			e.printStackTrace();
		}
		
		FPrinterJob dlGr = new FPrinterJob( report, label, sCab, rs, hParam , this );

		if ( bVisualizar ) {
			dlGr.setVisible( true );
		} else {
			try {
				JasperPrintManager.printReport( dlGr.getRelatorio(), true );
			} catch ( Exception err ) {
				Funcoes.mensagemErro( this, "Erro na impressão do relatório de Ações realizadas!" + err.getMessage(), true, con, err );
			}
		}
	}
	*/

	public void setConexao( DbConnection cn ) {

		super.setConexao( cn );
		lcContato.setConexao( cn );
		lcMotAval.setConexao( cn );
		
		
	}

	public void afterCarrega( CarregaEvent cevt ) {
		
	}

	public void beforeCarrega( CarregaEvent cevt ) {

	}

}
