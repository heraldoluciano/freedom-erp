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

import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;

import net.sf.jasperreports.engine.JasperPrintManager;

import org.freedom.acao.CarregaEvent;
import org.freedom.acao.CarregaListener;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.functions.Funcoes;
import org.freedom.library.persistence.GuardaCampo;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.component.JLabelPad;
import org.freedom.library.swing.component.JRadioGroup;
import org.freedom.library.swing.component.JTextFieldFK;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.frame.Aplicativo;
import org.freedom.library.swing.frame.FPrinterJob;
import org.freedom.library.swing.frame.FRelatorio;
import org.freedom.modulos.crm.view.frame.crud.detail.FContrato;
import org.freedom.modulos.std.view.frame.crud.tabbed.FCliente;

public class FRCronograma extends FRelatorio implements CarregaListener{

	private static final long serialVersionUID = 1L;
	
	private JTextFieldPad txtDataini = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtDatafim = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );
	
	private JTextFieldPad txtCodCli = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	
	private JTextFieldFK txtCodCli2 = new JTextFieldFK( JTextFieldFK.TP_INTEGER, 8, 0 );
	
	private JTextFieldFK txtRazCli = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtStatus = new JTextFieldPad( JTextFieldPad.TP_STRING, 100, 0 );
	
	private JTextFieldPad txtCodContr = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	
	private JTextFieldFK txtDescContr = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );
	
	private JTextFieldFK txtContHSubContr = new JTextFieldFK( JTextFieldFK.TP_STRING, 2, 0 );
	
	private Vector<String> vLabsSaldo = new Vector<String>();
	private Vector<String> vValsSaldo = new Vector<String>();
	
	private JRadioGroup<String, String> rgSaldoHoras = null;

	
	private ListaCampos lcCli = new ListaCampos( this );
	
	private ListaCampos lcContr = new ListaCampos( this );
	
	public FRCronograma() {		
		setTitulo( "Cronograma Sintético" );
		setAtribos( 80, 80, 410	, 300 );
		
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
		adic( txtDataini, 38, 25, 95, 20 );		
		adic( txtDatafim, 178, 25, 95, 20 );
		
		adic( txtCodCli, 7, 80, 80, 20, "Cod.Cli" );
		adic( txtRazCli, 90, 80, 225, 20, "Razão social do cliente" );
		adic( txtCodContr, 7, 120, 80, 20, "Cod.Contr");
		adic( txtDescContr, 90, 120, 225, 20, "Descrição do Contrato" );
		
		Calendar cPeriodo = Calendar.getInstance();
		txtDatafim.setVlrDate( cPeriodo.getTime() );
	
		cPeriodo.set( Calendar.DAY_OF_MONTH, cPeriodo.get( Calendar.DAY_OF_MONTH ) - 30 );
		txtDataini.setVlrDate( cPeriodo.getTime() );
	}
	
	private void montaListaCampos() {
		
		//cliente
		lcCli.add( new GuardaCampo( txtCodCli, "CodCli", "Cód.cli.", ListaCampos.DB_PK, true ) );
		lcCli.add( new GuardaCampo( txtRazCli, "RazCli", "Razão social do cliente", ListaCampos.DB_SI, false ) );
		lcCli.montaSql( false, "CLIENTE", "VD" );
		lcCli.setReadOnly( true );
		txtCodCli.setTabelaExterna( lcCli, FCliente.class.getCanonicalName() );
		txtCodCli.setFK( true );
		txtCodCli.setNomeCampo( "CodCli" );
		
		// Contrato

		lcContr.add( new GuardaCampo( txtCodContr, "CodContr", "Cód.Contr.", ListaCampos.DB_PK, true ) );
		lcContr.add( new GuardaCampo( txtDescContr, "DescContr", "Descrição do contrato", ListaCampos.DB_SI, false ) );
		lcContr.add( new GuardaCampo( txtContHSubContr, "ContHSubContr", "Cont.HSubContr.", ListaCampos.DB_SI, false ) );
		lcContr.add( new GuardaCampo( txtCodCli2, "CodCli", "Cód.cli", ListaCampos.DB_SI, false ) );
		lcContr.setDinWhereAdic( "CodCli=#N", txtCodCli );
		lcContr.montaSql( false, "CONTRATO", "VD" );
		lcContr.setReadOnly( true );
	
		txtCodContr.setTabelaExterna( lcContr, FContrato.class.getCanonicalName() );
		txtCodContr.setFK( true );
		txtCodContr.setNomeCampo( "CodContr" );
		
		lcCli.addCarregaListener( this );
		lcContr.addCarregaListener( this );
		
		
	}

	public void imprimir( boolean bVisualizar ) {
		
		if ( txtDatafim.getVlrDate().before( txtDataini.getVlrDate() ) ) {
			Funcoes.mensagemInforma( this, "Data inicial maior que a data final!" );
			return;
		}
		
		String sCab = "";
		String Ordem = "";
		StringBuffer sql = new StringBuffer();
		StringBuffer sWhere= new StringBuffer();
		Blob fotoemp = null;

		try {
			PreparedStatement ps = con.prepareStatement( "SELECT FOTOEMP FROM SGEMPRESA WHERE CODEMP=?" );
			ps.setInt( 1, Aplicativo.iCodEmp );

			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				fotoemp = rs.getBlob( "FOTOEMP" );
			}
			rs.close();
			ps.close();
			con.commit();

		} catch (Exception e) {
			Funcoes.mensagemErro( this, "Erro carregando logotipo.\n" + e.getMessage() );
			e.printStackTrace();
		}	
		
		if( txtCodCli.getVlrInteger() > 0 ){
			sWhere.append( "ct.codemp=? and ct.codfilial=? and ct.codcontr=? and cl.codcli= " + txtCodCli.getVlrInteger() );
		} else {
			sWhere.append( "ct.codemp=? and ct.codfilial=? and ct.codcontr=? ");
		}
/*
		sql.append("select cast(it.indexitcontr as varchar(10))||");
		sql.append( "(case when sit.indexitcontr is null then '' else ");
		sql.append( "'.'||cast(sit.indexitcontr as varchar(10)) end )||'.'|| ");
		sql.append( " cast(ta.indextarefa as varchar(10))|| ");
		sql.append( "(case when sta.indextarefa is null then '' else ");
		sql.append( "'.'||cast(sta.indextarefa as varchar(10)) end ) indexrel, ");
		sql.append( "it.indexitcontr, ta.indextarefa, ta.tipotarefa, ta.codtarefa, ");
		sql.append( "cl.codcli, cl.razcli, ");
		sql.append( "ct.codcontr, it.coditcontr, it.descitcontr, ta.descdettarefa ");
		sql.append( " from vdcliente cl, vdcontrato ct ");
		sql.append( "left outer join vditcontrato it on ");
		sql.append( " it.codemp=ct.codemp and it.codfilial=ct.codfilial and it.codcontr=ct.codcontr ");
		sql.append( "left outer join crtarefa ta on ");
		sql.append( "ta.codempct=it.codemp and ta.codfilialct=it.codfilial and ");
		sql.append( "ta.codcontr=it.codcontr and ");
		sql.append( "ta.coditcontr=it.coditcontr ");
		sql.append( "left outer join vdcontrato sct on ");
		sql.append( "sct.codempsp=ct.codemp and sct.codfilialsp=ct.codfilial and ");
		sql.append( "sct.codcontrsp=ct.codcontr ");
		sql.append( "left outer join vditcontrato sit on ");
		sql.append( "sit.codemp=sct.codemp and sit.codfilial=sct.codfilial and ");
		sql.append( "	sit.codcontr=sct.codcontr ");
		sql.append( "left outer join crtarefa sta on ");
		sql.append( "sta.codempct=sit.codemp and sta.codfilialct=sit.codfilial and ");
		sql.append( "sta.codcontr=sit.codcontr and ");
		sql.append( "sta.coditcontr=sit.coditcontr " );
		sql.append( "	where  cl.codemp=ct.codempcl and cl.codfilial=ct.codfilialcl and ");
		sql.append( " cl.codcli=ct.codcli and ");
		sql.append( sWhere );
		sql.append( "order by 1,2 " );
	*/
		
		sql.append( " SELECT CT.INDICE, " );
		sql.append( "( CASE  " );
		sql.append( "WHEN IDX=1 AND TIPO='SC' THEN DESCCONTRSC " );
		sql.append( "WHEN IDX=1 AND TIPO='CT' THEN DESCCONTR " );
		sql.append( "WHEN IDX=2 THEN DESCITCONTR " );
		sql.append( "WHEN IDX=3 THEN DESCTAREFA " );
		sql.append( "WHEN IDX=4 THEN DESCTAREFAST " );
		sql.append( "END ) DESCRICAO, " );
		sql.append( "CT.TIPO, CT.IDX, CT.CODCONTR, CT.CODCONTRSC, CT.CODITCONTR, CT.CODTAREFA, CT.CODTAREFAST " );
		sql.append( "FROM VDCONTRATOVW01 CT " );
		sql.append(	"WHERE CT.CODEMPCT=? AND CT.CODFILIALCT=? AND CT.CODCONTR=? ");
		sql.append(	"ORDER BY  IDX01, IDX02, IDX03, IDX04, IDX05 " );


		PreparedStatement ps = null;
		ResultSet rs = null;

		try{
			ps = con.prepareStatement( sql.toString() );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "VDCONTRATO" ) );
			ps.setInt( 3, txtCodContr.getVlrInteger() );
			
			rs = ps.executeQuery();

		} catch (Exception err) {
			Funcoes.mensagemErro( this, "Erro consulta Cronograma Sintético\n" + err.getMessage(), true, con, err );
		}

		imprimiGrafico( bVisualizar, rs,  sCab, fotoemp );

	}

	private void imprimiGrafico( boolean bVisualizar, ResultSet rs, String sCab, Blob fotoemp) {
		String report = "layout/rel/REL_CRONOGRAMA_01.jasper";
		String label = "Cronograma Sintético";
		
	    HashMap<String, Object> hParam = new HashMap<String, Object>();
		hParam.put( "SUBREPORT_DIR", "org/freedom/layout/rel/" );
		hParam.put( "CODEMP", new Integer(Aplicativo.iCodEmp) );
		hParam.put( "CODFILIAL", new Integer(ListaCampos.getMasterFilial( "VDCONTRATO" )) );
		hParam.put( "CODCONTR", txtCodContr.getVlrInteger() );

		
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
				Funcoes.mensagemErro( this, "Erro na impressão do Cronograma Sintético!" + err.getMessage(), true, con, err );
			}
		}
	}

	public void setConexao( DbConnection cn ) {

		super.setConexao( cn );
		lcCli.setConexao( cn );
		lcContr.setConexao( cn );
	}

	public void afterCarrega( CarregaEvent cevt ) {
		if(cevt.getListaCampos() == lcContr ){
			if( txtCodContr.getVlrInteger() !=  txtCodCli2.getVlrInteger() ){
				txtCodCli.setVlrInteger( txtCodCli2.getVlrInteger() );
				lcCli.carregaDados();
			}	
		}
		
	}

	public void beforeCarrega( CarregaEvent cevt ) {

		// TODO Auto-generated method stub
		
	}

}
