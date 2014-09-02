/**
 * @version 02/11/2003 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 * 
 *         Projeto: Freedom <BR>
 * 
 *         Pacote: org.freedom.modulos.std <BR>
 *         Classe:
 * @(#)FRPagar.java <BR>
 * 
 *                  Este arquivo é parte do sistema Freedom-ERP, o Freedom-ERP é um software livre; você pode redistribui-lo e/ou <BR>
 *                  modifica-lo dentro dos termos da Licença Pública Geral GNU como publicada pela Fundação do Software Livre (FSF); <BR>
 *                  na versão 2 da Licença, ou (na sua opnião) qualquer versão. <BR>
 *                  Este programa é distribuido na esperança que possa ser util, mas SEM NENHUMA GARANTIA; <BR>
 *                  sem uma garantia implicita de ADEQUAÇÂO a qualquer MERCADO ou APLICAÇÃO EM PARTICULAR. <BR>
 *                  Veja a Licença Pública Geral GNU para maiores detalhes. <BR>
 *                  Você deve ter recebido uma cópia da Licença Pública Geral GNU junto com este programa, se não, <BR>
 *                  de acordo com os termos da LPG-PC <BR>
 * <BR>
 * 
 *                  Comentários sobre a classe...
 * 
 */

package org.freedom.modulos.fnc.view.frame.report;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.SwingConstants;

import org.freedom.bmps.Icone;
import org.freedom.infra.functions.StringFunctions;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.component.ImprimeOS;
import org.freedom.library.functions.Funcoes;
import org.freedom.library.persistence.GuardaCampo;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.component.JButtonPad;
import org.freedom.library.swing.component.JCheckBoxPad;
import org.freedom.library.swing.component.JLabelPad;
import org.freedom.library.swing.component.JRadioGroup;
import org.freedom.library.swing.component.JTextFieldFK;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.frame.Aplicativo;
import org.freedom.library.swing.frame.AplicativoPD;
import org.freedom.library.swing.frame.FPrinterJob;
import org.freedom.library.swing.frame.FRelatorio;
import org.freedom.library.type.TYPE_PRINT;
import org.freedom.modulos.fnc.view.frame.crud.tabbed.FConta;

public class FRPagar extends FRelatorio {

	private static final long serialVersionUID = 1L;

	private JTextFieldPad txtDataini = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtDatafim = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtDatacor = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JRadioGroup<?, ?> cbFiltro = null;

	private JRadioGroup<?, ?> cbOrdem = null;

	private JRadioGroup<?, ?> rgTipoRel = null;

	private JTextFieldPad txtCodFor = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtRazFor = new JTextFieldFK( JTextFieldPad.TP_STRING, 60, 0 );

	private JTextFieldFK txtCNPJFor = new JTextFieldFK( JTextFieldPad.TP_STRING, 14, 0 );

	private JTextFieldPad txtCodPlanoPag = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtDescPlanoPag = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );
	
	private JTextFieldFK txtNomeBanco = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private JCheckBoxPad cbObs = new JCheckBoxPad( "Imprimir observações?", "S", "N" );

	private JCheckBoxPad cbParPar = new JCheckBoxPad( "Imprimir pagamentos parciais?", "S", "N" );

	private JCheckBoxPad cbAgrupFor = new JCheckBoxPad( "Agrupar fornecedores?", "S", "N" );

	private ListaCampos lcFor = new ListaCampos( this );

	private ListaCampos lcPlanoPag = new ListaCampos( this );

	private JButtonPad btExp = new JButtonPad( Icone.novo( "btTXT.png" ) );

	private ListaCampos lcBanco = new ListaCampos( this );

	private ListaCampos lcTipoCob = new ListaCampos( this );
	
	private ListaCampos lcConta = new ListaCampos( this );

	private JTextFieldPad txtCodBanco = new JTextFieldPad( JTextFieldPad.TP_STRING, 3, 0 );

	private JTextFieldPad txtCodTipoCob = new JTextFieldPad( JTextFieldPad.TP_STRING, 3, 0 );
	
	private JTextFieldPad txtNumConta = new JTextFieldPad( JTextFieldPad.TP_STRING, 10, 0 );

	private JTextFieldFK txtDescConta = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private JTextFieldFK txtDescCodCobrança = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private Calendar calAtual = Calendar.getInstance();

	public FRPagar() {

		setTitulo( "Contas a Pagar" );
		setAtribos( 80, 80, 410, 550 );

		montaListaCampos();
		montaCheckBox();
		montaTela();

		txtDatafim.setVlrDate( calAtual.getTime() );
		txtDatacor.setVlrDate( calAtual.getTime() );
		Calendar cal = Calendar.getInstance();
		cal.set( Calendar.DAY_OF_MONTH, 1 );
		txtDataini.setVlrDate( cal.getTime() );

	}

	private void montaTela() {

		JLabelPad lbLinha = new JLabelPad();
		lbLinha.setBorder( BorderFactory.createEtchedBorder() );
		JLabelPad lbPeriodo = new JLabelPad( "Periodo:", SwingConstants.CENTER );
		lbPeriodo.setOpaque( true );

		adic( lbPeriodo, 17, 0, 80, 20 );
		adic( lbLinha, 7, 10, 360, 56 );

		adic( new JLabelPad( "De:", SwingConstants.LEFT ), 14, 20, 30, 20 );
		adic( txtDataini, 14, 40, 100, 20 );
		adic( new JLabelPad( "Até:", SwingConstants.LEFT ), 132, 20, 30, 20 );
		adic( txtDatafim, 132, 40, 100, 20 );
		adic( new JLabelPad( "Correção para:", SwingConstants.LEFT ), 260, 20, 100, 20 );
		adic( txtDatacor, 260, 40, 100, 20 );
		adic( new JLabelPad( "Filtro:" ), 7, 65, 360, 20 );
		adic( cbFiltro, 7, 85, 360, 30 );
		adic( new JLabelPad( "Ordem:" ), 7, 115, 360, 20 );
		adic( cbOrdem, 7, 135, 360, 30 );
		adic( rgTipoRel, 7, 170, 360, 30 );
		adic( new JLabelPad( "Cód.for." ), 7, 200, 80, 20 );
		adic( txtCodFor, 7, 220, 80, 20 );
		adic( new JLabelPad( "Razão social do fornecedor" ), 90, 200, 300, 20 );
		adic( txtRazFor, 90, 220, 277, 20 );

		adic( new JLabelPad( "Cód.pl.pag." ), 7, 243, 80, 20 );
		adic( txtCodPlanoPag, 7, 265, 80, 20 );
		adic( new JLabelPad( "Descrição do plano de pagamento" ), 90, 243, 300, 20 );
		adic( txtDescPlanoPag, 90, 265, 277, 20 );

		adic( new JLabelPad( "Cód.Banco" ), 7, 283, 80, 20 );
		adic( txtCodBanco, 7, 305, 80, 20 );
		adic( new JLabelPad( "Nome do banco" ), 90, 283, 300, 20 );
		adic( txtNomeBanco, 90, 305, 277, 20 );

		//Filtro da conta

		adic( txtNumConta, 7, 343, 80, 20, "Num.Conta" );
		adic( txtDescConta, 90, 343, 277, 20, "Descrição da conta" );

		adic( new JLabelPad( "Cód.T.Cob." ), 7, 363, 80, 20 );
		adic( txtCodTipoCob, 7, 383, 80, 20 );
		adic( new JLabelPad( "Descrição do Tipo de cobrança" ), 90, 363, 300, 20 );
		adic( txtDescCodCobrança, 90, 383, 277, 20 );

		adic( cbObs, 7, 410, 385, 20 );
		adic( cbParPar, 7, 430, 360, 20 );
		adic( cbAgrupFor, 7, 450, 360, 20 );

		btExp.setToolTipText( "Exporta para arquivo no formato csv." );
		btExp.setPreferredSize( new Dimension( 40, 28 ) );
		pnBotoes.setPreferredSize( new Dimension( 120, 28 ) );
		pnBotoes.add( btExp );

		btExp.addActionListener( this );

	}

	public void montaListaCampos() {

		lcFor.add( new GuardaCampo( txtCodFor, "CodFor", "Cód.forn.", ListaCampos.DB_PK, false ) );
		lcFor.add( new GuardaCampo( txtRazFor, "RazFor", "Razão social do fornecedor", ListaCampos.DB_SI, false ) );
		lcFor.add( new GuardaCampo( txtCNPJFor, "CnpjFor", "CNPJ", ListaCampos.DB_SI, false ) );

		lcFor.montaSql( false, "FORNECED", "CP" );
		lcFor.setReadOnly( true );
		txtCodFor.setTabelaExterna( lcFor, null );
		txtCodFor.setFK( true );
		txtCodFor.setNomeCampo( "CodFor" );

		lcPlanoPag.add( new GuardaCampo( txtCodPlanoPag, "CodPlanoPag", "Cód.pl.pag.", ListaCampos.DB_PK, false ) );
		lcPlanoPag.add( new GuardaCampo( txtDescPlanoPag, "DescPlanoPag", "Descrição do plano de pagamento", ListaCampos.DB_SI, false ) );
		lcPlanoPag.montaSql( false, "PLANOPAG", "FN" );
		lcPlanoPag.setReadOnly( true );
		txtCodPlanoPag.setTabelaExterna( lcPlanoPag, null );
		txtCodPlanoPag.setFK( true );
		txtCodPlanoPag.setNomeCampo( "CodPlanoPag" );

		lcBanco.add( new GuardaCampo( txtCodBanco, "CodBanco", "Cód.banco.", ListaCampos.DB_PK, false ) );
		lcBanco.add( new GuardaCampo( txtNomeBanco, "NomeBanco", "Nome do banco", ListaCampos.DB_SI, false ) );
		lcBanco.montaSql( false, "BANCO", "FN" );
		lcBanco.setReadOnly( true );
		txtCodBanco.setTabelaExterna( lcBanco, null );
		txtCodBanco.setFK( true );
		txtCodBanco.setNomeCampo( "CodBanco" );

		lcTipoCob.add( new GuardaCampo( txtCodTipoCob, "CodTipoCob", "Cód.T.Cob.", ListaCampos.DB_PK, false ) );
		lcTipoCob.add( new GuardaCampo( txtDescCodCobrança, "DESCTIPOCOB", "Nome do Tipo de Cobrança.", ListaCampos.DB_SI, false ) );
		lcTipoCob.montaSql( false, "TIPOCOB", "FN" );
		lcTipoCob.setReadOnly( true );
		txtCodTipoCob.setTabelaExterna( lcTipoCob, null );
		txtCodTipoCob.setFK( true );
		txtCodTipoCob.setNomeCampo( "CodTipoCob" );
		
		lcConta.add( new GuardaCampo( txtNumConta, "numconta", "Num.Conta", ListaCampos.DB_PK, false ) );
		lcConta.add( new GuardaCampo( txtDescConta, "descconta", "Descrição da conta", ListaCampos.DB_SI, false ) );
		lcConta.montaSql( false, "CONTA", "FN" );
		lcConta.setReadOnly( true );
		txtNumConta.setTabelaExterna( lcConta, FConta.class.getCanonicalName() );
		txtNumConta.setFK( true );
		txtNumConta.setNomeCampo( "numconta" );


	}

	public void montaCheckBox() {

		Vector<String> vVals0 = new Vector<String>();
		Vector<String> vLabs0 = new Vector<String>();
		vLabs0.addElement( "Contas a pagar" );
		vLabs0.addElement( "Contas pagas" );
		vLabs0.addElement( "Ambas" );
		vVals0.addElement( "N" );
		vVals0.addElement( "P" );
		vVals0.addElement( "A" );
		cbFiltro = new JRadioGroup<String, String>( 1, 3, vLabs0, vVals0 );

		Vector<String> vVals1 = new Vector<String>();
		Vector<String> vLabs1 = new Vector<String>();
		vLabs1.addElement( "Vencimento" );
		vLabs1.addElement( "Pagamento" );
		vLabs1.addElement( "Emissão" );
		vVals1.addElement( "V" );
		vVals1.addElement( "P" );
		vVals1.addElement( "E" );
		cbOrdem = new JRadioGroup<String, String>( 1, 2, vLabs1, vVals1 );

		Vector<String> vVals2 = new Vector<String>();
		Vector<String> vLabs2 = new Vector<String>();
		vVals2.addElement( "G" );
		vVals2.addElement( "T" );
		vLabs2.addElement( "Grafico" );
		vLabs2.addElement( "Texto" );
		rgTipoRel = new JRadioGroup<String, String>( 1, 2, vLabs2, vVals2 );
		rgTipoRel.setVlrString( "G" );
	}

	public void exportaTXT() {

		Vector<String> vLinhas = new Vector<String>();
		try {
			ResultSet rs = getResultSet();
			String sVencto = null;
			String sDuplic = null;
			String sPedido = null;
			String sDtCompra = null;
			String sDocPag = null;
			String sNParcPag = null;
			String sForneced = null;
			String sObs = null;
			String sLinha = null;
			String sAtraso = null;
			try {
				vLinhas.addElement( "Vencimento;Duplicata;Pedido;Data da compra;Fornecedor;Parcela;Atraso;Observação" );
				while ( rs.next() ) {
					sVencto = rs.getString( "DTVENCITPAG" ) != null ? StringFunctions.sqlDateToStrDate( rs.getDate( "DTVENCITPAG" ) ) : "";
					sDocPag = rs.getString( "DOCPAG" ) != null ? rs.getString( "DOCPAG" ).trim() : "";
					sNParcPag = rs.getString( "NPARCPAG" ) != null ? rs.getString( "NPARCPAG" ).trim() : "";
					sPedido = rs.getString( "CODCOMPRA" ) != null ? rs.getString( "CODCOMPRA" ).trim() : "";
					sDuplic = sDocPag + "/" + sNParcPag;
					sForneced = rs.getString( "RAZFOR" ) != null ? rs.getString( "RAZFOR" ).trim() : "";
					sDtCompra = rs.getString( "DTEMITCOMPRA" ) != null ? StringFunctions.sqlDateToStrDate( rs.getDate( "DTEMITCOMPRA" ) ) : "";
					sObs = rs.getString( "OBSITPAG" ) != null ? rs.getString( "OBSITPAG" ).trim() : "";
					sAtraso = String.valueOf( Funcoes.getNumDias( Funcoes.sqlDateToDate( rs.getDate( "DTVENCITPAG" ) ), new Date() ) );
					sLinha = sVencto + ";" + sDuplic + ";" + sPedido + ";" + sDtCompra + ";" + sForneced + ";" + sNParcPag + ";" + sAtraso + ";" + sObs;
					vLinhas.addElement( sLinha );
				}
	
			} catch ( SQLException e ) {
				e.printStackTrace();
			}
			if ( vLinhas.size() > 1 ) {
				File fArq = Funcoes.buscaArq( this, "csv" );
				if ( fArq == null )
					return;
				try {
					PrintStream ps = new PrintStream( new FileOutputStream( fArq ) );
					for ( int i = 0; vLinhas.size() > i; ++i )
						ps.println( vLinhas.elementAt( i ).toString() );
	
					ps.flush();
					ps.close();
	
				} catch ( IOException err ) {
					Funcoes.mensagemErro( this, "Erro ao gravar o arquivo!\n" + err.getMessage(), true, con, err );
					err.printStackTrace();
				}
			} else {
				Funcoes.mensagemInforma( this, "Não há informações para exportar!" );
			}
		} catch (Exception err) {
			Funcoes.mensagemErro( this, err.getMessage() );
		}
	}

	public ResultSet getResultSet() throws Exception {

		PreparedStatement ps = null; 
		ResultSet rs = null;
		String filtropag = cbFiltro.getVlrString();
		int paramsql = 1;

		// Verifica se a data de correção é diferente da data atual
		// System.out.println(txtDatacor.getVlrDate());
		// System.out.println(calAtual.getTime());
		boolean correcao = ( !Funcoes.dataAAAAMMDD( txtDatacor.getVlrDate() ).equals( Funcoes.dataAAAAMMDD( calAtual.getTime() ) ) );

		StringBuilder sql = new StringBuilder();

		sql.append( "select it.codpag, it.dtvencitpag,it.nparcpag,p.codcompra"); 
		sql.append(",p.codfor,f.razfor,it.vlrparcitpag " );
		//if ( correcao ) {
			// Valor pago
			sql.append( ", coalesce(la.vlrsublanca,0) vlrpagoitpag " );
			// Valor a pagar
			/*sql.append( "coalesce(it.vlrparcitpag,0)-coalesce((select sum(vlrsublanca) from fnsublanca l where l.codemppg=it.codemp" );
			sql.append( " and l.codfilialpg=it.codfilial and l.codpag=it.codpag" );
			sql.append( " and l.nparcpag=it.nparcpag and l.datasublanca<=?),0) vlrapagitpag," );*/
			// Data de pagamento
			sql.append( ", la.datasublanca dtpagoitpag " );
		//}
		//else {
			//	sql.append( "IT.VLRPAGOITPAG,IT.VLRAPAGITPAG,IT.DTPAGOITPAG" );
			//sql.append( "coalesce(la.vlrsublanca, it.vlrpagoitpag) vlrpagoitpag");
			//sql.append( ",it.vlrapagitpag,coalesce(la.datasublanca,it.dtpagoitpag) dtpagoitpag " );
		//}
		sql.append( ", (select c.statuscompra from cpcompra c where c.flag in " );
		sql.append( AplicativoPD.carregaFiltro( con, org.freedom.library.swing.frame.Aplicativo.iCodEmp ) );
		sql.append( " and c.codemp=p.codempcp and c.codfilial=p.codfilialcp and c.codcompra=p.codcompra) statuscompra," );
		sql.append( "p.docpag,coalesce(la.histsublanca,it.obsitpag) obsitpag" );
		sql.append( ", (select c.dtemitcompra from cpcompra c where c.flag in " );
		sql.append( AplicativoPD.carregaFiltro( con, org.freedom.library.swing.frame.Aplicativo.iCodEmp ) );
		sql.append( " and c.codemp=p.codempcp and c.codfilial=p.codfilialcp and c.codcompra=p.codcompra) as dtemitcompra " );
		sql.append( " from fnpagar p ");
		sql.append( " inner join cpforneced f ");
		sql.append( " on f.codemp=p.codempfr and f.codfilial=p.codfilialfr and f.codfor=p.codfor " );
		sql.append( " inner join fnitpagar it ");
		sql.append( " on it.codemp = p.codemp and it.codfilial=p.codfilial and p.codpag=it.codpag " );
		if ( "P".equals( cbOrdem.getVlrString() ) ) {
			sql.append( " and it.dtpagoitpag" );
		}
		else if ( "V".equals( cbOrdem.getVlrString() ) ) {
			sql.append( " and it.dtvencitpag " );
		}
		else {
			sql.append( " and it.dtitpag " );
		}
		sql.append( " between ? and ? " );
		sql.append( " left outer join fnconta ct on " );
		sql.append( " ct.codemp = it.codempca and ct.codfilial=it.codfilialca and ct.numconta=it.numconta " );
		sql.append( " left outer join fnsublanca la on la.nparcpag = it.nparcpag and la.codpag=it.codpag ");
		sql.append( " and la.codfilialpg=it.codfilial and la.codemppg=it.codemp and la.datasublanca<=? " );
		sql.append( " where p.flag in " + AplicativoPD.carregaFiltro( con, org.freedom.library.swing.frame.Aplicativo.iCodEmp ) );
		sql.append( " and ");
		//if ( correcao ) {
			// data de correção diferente da data atual
			if ( "N".equals( filtropag ) ) {
				sql.append( "( it.statusitpag in (?,?,?) or " );
				sql.append( "( la.datasublanca is null ) )" );
			}
			else if ( "P".equals( filtropag ) ) {
				sql.append( "( it.statusitpag in (?,?,?) or " );
				sql.append( "( la.datasublanca is not null  ) )" );
			}
			else if ( "A".equals( filtropag ) ) {
				sql.append( "it.statusitpag in (?,?,?)" );
			}
		//}
		// Se a data de correção não for diferente data data atual, mantém a SQL original
		//else {
			//sql.append( "it.statusitpag in (?,?,?)" );
	//	}
		sql.append( "".equals( txtCodFor.getVlrString() ) ? "" : " and p.codfor=" + txtCodFor.getVlrString() );
		sql.append( "".equals( txtCodPlanoPag.getVlrString() ) ? "" : " and p.codplanopag=" + txtCodPlanoPag.getVlrString() );
		sql.append( " and p.codemp=? and p.codfilial=? " );
		if ( txtCodBanco.getVlrInteger() > 0 ) {
			sql.append( " and coalesce(ct.codempbo,p.codempbo)=? and coalesce(ct.codfilialbo,p.codfilialbo)=?" );
			sql.append( " and coalesce(ct.codbanco,p.codbanco)=? " );
		}
		if ( ! "".equals( txtNumConta.getVlrString() )  ) {
			sql.append( " and it.codempca=? and it.codfilialca=? and it.numconta=? " );
		}
		if ( txtCodTipoCob.getVlrInteger() > 0 ) {
			sql.append( " and it.codtipocob=? and it.codemptc=? and it.codfilialtc=? " );
		}
		sql.append( "order by " );
		if ("S".equals(cbAgrupFor.getVlrString())) {
			sql.append("f.razfor, f.codfor, ");
		}
		if ( "P".equals( cbOrdem.getVlrString() ) ) {
			sql.append( "it.dtpagoitpag" );
		}
		else if ( "V".equals( cbOrdem.getVlrString() ) ) {
			sql.append( "it.dtvencitpag" );
		}
		else {
			sql.append( "it.dtitpag" );
		}
		sql.append( ", f.razfor" );
		try {
			System.out.println( sql.toString() );
			ps = con.prepareStatement( sql.toString() );
			//if ( correcao ) {
/*				ps.setDate( paramsql++, Funcoes.dateToSQLDate( txtDatacor.getVlrDate() ) );
				ps.setDate( paramsql++, Funcoes.dateToSQLDate( txtDatacor.getVlrDate() ) );
				ps.setDate( paramsql++, Funcoes.dateToSQLDate( txtDatacor.getVlrDate() ) );*/
			//}
			ps.setDate( paramsql++, Funcoes.dateToSQLDate( txtDataini.getVlrDate() ) );
			ps.setDate( paramsql++, Funcoes.dateToSQLDate( txtDatafim.getVlrDate() ) );
			ps.setDate( paramsql++, Funcoes.dateToSQLDate( txtDatacor.getVlrDate() ) );
			if ( filtropag.equals( "N" ) ) {
				ps.setString( paramsql++, "P1" );
				ps.setString( paramsql++, "P1" );
				if( cbParPar.getVlrString().equals( "S" ) ) {
					ps.setString( paramsql++, "PL" );
				}
				else {
					ps.setString( paramsql++, "P1" );
				}
				//if ( correcao ) {
					//ps.setDate( paramsql++, Funcoes.dateToSQLDate( txtDatacor.getVlrDate() ) );
				//}
			}
			else if ( filtropag.equals( "P" ) ) {
				ps.setString( paramsql++, "PP" );
				ps.setString( paramsql++, "PP" );
				if( cbParPar.getVlrString().equals( "S" ) ) {
					ps.setString( paramsql++, "PL" );
				}
				else {
					ps.setString( paramsql++, "PP" );
				}
				//if ( correcao ) {
					//ps.setDate( paramsql++, Funcoes.dateToSQLDate( txtDatacor.getVlrDate() ) );
				//}
			}
			else if ( filtropag.equals( "A" ) ) {
				ps.setString( paramsql++, "P1" );
				ps.setString( paramsql++, "PP" );
				if( cbParPar.getVlrString().equals( "S" ) ) {
					ps.setString( paramsql++, "PL" );
				}
				else {
					ps.setString( paramsql++, "P1" );
				}
			}
			ps.setInt( paramsql++, Aplicativo.iCodEmp );
			ps.setInt( paramsql++, ListaCampos.getMasterFilial( "FNPAGAR" ) );
			if ( txtCodBanco.getVlrInteger() > 0 ) {
				ps.setInt( paramsql++, lcBanco.getCodEmp() );
				ps.setInt( paramsql++, lcBanco.getCodFilial() );
				ps.setInt( paramsql++, txtCodBanco.getVlrInteger() );
			}
			if ( ! "".equals( txtNumConta.getVlrString() )  ) {
				ps.setInt( paramsql++, lcConta.getCodEmp() );
				ps.setInt( paramsql++, lcConta.getCodFilial() );
				ps.setString( paramsql++, txtNumConta.getVlrString() );
			}
			if ( txtCodTipoCob.getVlrInteger() > 0 ) {
				ps.setInt( paramsql++, txtCodTipoCob.getVlrInteger() );
				ps.setInt( paramsql++, lcTipoCob.getCodEmp() );
				ps.setInt( paramsql++, lcTipoCob.getCodFilial() );
			}
			rs = ps.executeQuery();
		} catch ( SQLException err ) {
			String mensagemErro = err.getMessage();
			err.printStackTrace();
			try {
				con.rollback(); 
			} catch (SQLException errroll) {
				errroll.printStackTrace();
			}
			throw new Exception("Erro executando query !\n"+mensagemErro);
		}
		return rs;
	}

	public void imprimir( TYPE_PRINT bVisualizar ) {

		PreparedStatement ps = null;
		String sFiltroPag = cbFiltro.getVlrString();
		String sCab = "";

		if ( sFiltroPag.equals( "N" ) ) {
			sCab = "CONTAS A PAGAR ";
		}
		else if ( sFiltroPag.equals( "P" ) ) {
			sCab = "CONTAS PAGAS ";
		}
		else if ( sFiltroPag.equals( "A" ) ) {
			sCab = "CONTAS A PAGAR / PAGAS ";
		}
		
		if ( txtNumConta.getVlrString().length() > 0 ) {
			sCab = sCab + " - CONTA: " + txtNumConta.getVlrString() ;
		}

		if ( txtDatafim.getVlrDate().before( txtDataini.getVlrDate() ) ) {
			Funcoes.mensagemInforma( this, "Data final maior que a data inicial!" );
			return;
		}

		try {
			ResultSet rs = getResultSet();
			sCab += "  Periodo de: " + txtDataini.getVlrString() + "  Até:  " + txtDatafim.getVlrString() + "  Correção p/: " + txtDatacor.getVlrString();
			if ( txtCodBanco.getVlrString().length() > 0 ) {
				sCab += "\n Banco:" + txtCodBanco.getVlrString().trim() + "-" + txtNomeBanco.getVlrString().trim();
			}
			if ( "T".equals( rgTipoRel.getVlrString() ) ) {
				imprimiTexto( rs, bVisualizar, sCab );
			}
			else {
				imprimiGrafico( rs, bVisualizar, sCab );
		}
		} catch (Exception err) {
			Funcoes.mensagemErro( this, err.getMessage() );
		}
	}

	public void imprimiTexto( ResultSet rs, TYPE_PRINT bVisualizar, String sCab ) {

		ImprimeOS imp = new ImprimeOS( "", con );
		int linPag = imp.verifLinPag() - 1;
		boolean bFimDia = false;
		String sFiltroPag = cbFiltro.getVlrString();
		String sPag = null;
		String sDtVencItPag = "";
		String sDtPago = "";
		BigDecimal bTotalDiaParc = new BigDecimal( "0" );
		BigDecimal bTotalDiaPago = new BigDecimal( "0" );
		BigDecimal bTotalDiaApag = new BigDecimal( "0" );
		BigDecimal bTotParc = new BigDecimal( "0" );
		BigDecimal bTotalPago = new BigDecimal( "0" );
		BigDecimal bTotalApag = new BigDecimal( "0" );

		if ( sFiltroPag.equals( "N" ) ) {
			sPag = "A PAGAR";
		}
		else if ( sFiltroPag.equals( "P" ) ) {
			sPag = "PAGAS";
		}
		else if ( sFiltroPag.equals( "A" ) ) {
			sPag = "A PAGAR/PAGAS";
		}

		try {

			imp.limpaPags();
			imp.montaCab();
			imp.setTitulo( "Relatório de contas " + sPag );
			imp.addSubTitulo( "RELATORIO DE CONTAS " + sPag + "   -   PERIODO DE :" + txtDataini.getVlrString() + " ATE: " + txtDatafim.getVlrString() );

			while ( rs.next() ) {

				if ( imp.pRow() >= ( linPag - 1 ) ) {
					imp.pulaLinha( 1, imp.comprimido() );
					imp.say( 0, "+" + StringFunctions.replicate( "-", 133 ) + "+" );
					imp.incPags();
					imp.eject();
				}

				if ( imp.pRow() == 0 ) {

					imp.impCab( 136, true );
					imp.say( 0, imp.comprimido() );
					imp.say( 0, "|" + StringFunctions.replicate( "-", 133 ) + "|" );
					imp.pulaLinha( 1, imp.comprimido() );
					imp.say( 0, "| Vencto.    |" );
					imp.say( 15, " Fornecedor                               |" );
					imp.say( 59, " Doc.      |" );
					imp.say( 72, " Vlr. da Parc. |" );
					imp.say( 89, " Vlr Pago      |" );
					imp.say( 106, " Vlr Aberto   |" );
					imp.say( 122, " Data Pagto. |" );
					imp.pulaLinha( 1, imp.comprimido() );
					imp.say( 0, "|" + StringFunctions.replicate( "-", 133 ) + "|" );

				}

				if ( ( !StringFunctions.sqlDateToStrDate( rs.getDate( "DtVencItPag" ) ).equals( sDtVencItPag ) ) & ( bFimDia ) ) {

					imp.pulaLinha( 1, imp.comprimido() );
					imp.say( 0, "|" + StringFunctions.replicate( "-", 133 ) + "|" );
					imp.pulaLinha( 1, imp.comprimido() );
					imp.say( 0, "|" );
					imp.say( 41, "Totais do Dia-> | " + sDtVencItPag + " | " + Funcoes.strDecimalToStrCurrency( 14, 2, String.valueOf( bTotalDiaParc ) ) + " | " + Funcoes.strDecimalToStrCurrency( 14, 2, String.valueOf( bTotalDiaPago ) ) + " | "
							+ Funcoes.strDecimalToStrCurrency( 13, 2, String.valueOf( bTotalDiaApag ) ) + " | " );
					imp.say( imp.pRow(), 135, "|" );
					imp.pulaLinha( 1, imp.comprimido() );
					imp.say( 0, "|" + StringFunctions.replicate( "-", 133 ) + "|" );

					bTotalDiaParc = new BigDecimal( "0" );
					bTotalDiaPago = new BigDecimal( "0" );
					bTotalDiaApag = new BigDecimal( "0" );
					bFimDia = false;

				}

				imp.pulaLinha( 1, imp.comprimido() );
				imp.say( 0, "|" );

				if ( !StringFunctions.sqlDateToStrDate( rs.getDate( "DtVencItPag" ) ).equals( sDtVencItPag ) )
					imp.say( 3, StringFunctions.sqlDateToStrDate( rs.getDate( "DtVencItPag" ) ) );

				imp.say( 14, "| " + Funcoes.copy( rs.getString( "CodFor" ), 0, 6 ) + "-" + Funcoes.copy( rs.getString( "RazFor" ), 0, 33 ) + " |" );

				sDtPago = Funcoes.copy( rs.getString( "DtPagoItPag" ) != null ? StringFunctions.sqlDateToStrDate( rs.getDate( "DtPagoItPag" ) ) : " ", 0, 10 );

				imp.say( 61, ( Funcoes.copy( rs.getString( 10 ), 0, 1 ).equals( "P" ) ? Funcoes.copy( rs.getString( "CodCompra" ), 0, 6 ) : Funcoes.copy( rs.getString( "DocPag" ), 0, 6 ) ) + "/" + Funcoes.copy( rs.getString( "NParcPag" ), 0, 2 ) + "| "
						+ Funcoes.strDecimalToStrCurrency( 14, 2, rs.getString( "VlrParcItPag" ) ) + " | " + Funcoes.strDecimalToStrCurrency( 14, 2, rs.getString( "VlrPagoItPag" ) ) + " | " + Funcoes.strDecimalToStrCurrency( 13, 2, rs.getString( "VlrApagItPag" ) ) + " |  " + sDtPago + "  |" );
				if ( "S".equals( cbObs.getVlrString() ) & rs.getString( "ObsItPag" ) != null ) {
					imp.pulaLinha( 1, imp.comprimido() );
					imp.say( 0, "|   Obs: " + Funcoes.copy( rs.getString( "ObsItPag" ), 0, 50 ) );
					imp.say( 135, "|" );
				}
				if ( rs.getString( "VlrParcItPag" ) != null ) {
					bTotalDiaParc = bTotalDiaParc.add( new BigDecimal( rs.getString( "VlrParcItPag" ) ) );
					bTotParc = bTotParc.add( new BigDecimal( rs.getString( "VlrParcItPag" ) ) );
				}
				if ( rs.getString( "VlrPagoItPag" ) != null ) {
					bTotalDiaPago = bTotalDiaPago.add( new BigDecimal( rs.getString( "VlrPagoItPag" ) ) );
					bTotalPago = bTotalPago.add( new BigDecimal( rs.getString( "VlrPagoItPag" ) ) );
				}
				if ( rs.getString( "VlrApagItPag" ) != null ) {
					bTotalDiaApag = bTotalDiaApag.add( new BigDecimal( rs.getString( "VlrApagItPag" ) ) );
					bTotalApag = bTotalApag.add( new BigDecimal( rs.getString( "VlrApagItPag" ) ) );
				}

				bFimDia = true;
				sDtVencItPag = StringFunctions.sqlDateToStrDate( rs.getDate( "DtVencItPag" ) );

			}

			if ( bFimDia ) {
				imp.pulaLinha( 1, imp.comprimido() );
				imp.say( 0, "|" + StringFunctions.replicate( "-", 133 ) + "|" );
				imp.pulaLinha( 1, imp.comprimido() );
				imp.say( 0, "|" );
				imp.say( 41, "Totais do Dia-> | " + sDtVencItPag + " | " + Funcoes.strDecimalToStrCurrency( 14, 2, String.valueOf( bTotalDiaParc ) ) + " | " + Funcoes.strDecimalToStrCurrency( 14, 2, String.valueOf( bTotalDiaPago ) ) + " | "
						+ Funcoes.strDecimalToStrCurrency( 13, 2, String.valueOf( bTotalDiaApag ) ) + " | " );
				imp.say( 135, "|" );
			}

			imp.pulaLinha( 1, imp.comprimido() );
			imp.say( 0, "|" + StringFunctions.replicate( "=", 133 ) + "|" );
			imp.pulaLinha( 1, imp.comprimido() );
			imp.say( 0, "|" );
			imp.say( 55, "Totais Geral-> | " + Funcoes.strDecimalToStrCurrency( 14, 2, String.valueOf( bTotParc ) ) + " | " + Funcoes.strDecimalToStrCurrency( 14, 2, String.valueOf( bTotalPago ) ) + " | " + Funcoes.strDecimalToStrCurrency( 13, 2, String.valueOf( bTotalApag ) ) + " | " );
			imp.say( 135, "|" );
			imp.pulaLinha( 1, imp.comprimido() );
			imp.say( 0, "+" + StringFunctions.replicate( "=", 133 ) + "+" );

			imp.eject();
			imp.fechaGravacao();

			rs.close();
			con.commit();

		} catch ( SQLException err ) {
			err.printStackTrace();
			Funcoes.mensagemErro( this, "Erro consulta tabela de preços!\n" + err.getMessage(), true, con, err );
		}

		if ( bVisualizar==TYPE_PRINT.VIEW ) {
			imp.preview( this );
		}
		else {
			imp.print();
		}
	}

	private void imprimiGrafico( final ResultSet rs, final TYPE_PRINT bVisualizar, final String sCab ) {

		FPrinterJob dlGr = null;
		HashMap<String, Object> hParam = new HashMap<String, Object>();

		hParam.put( "CODEMP", Aplicativo.iCodEmp );
		hParam.put( "CODFILIAL", ListaCampos.getMasterFilial( "CPCOMPRA" ) );
		hParam.put( "RAZAOEMP", Aplicativo.empresa.toString() );
		hParam.put( "FILTROS", sCab );
		hParam.put( "DATAORDEM", cbOrdem.getVlrString() );
		hParam.put( "IMPOBS", new Boolean( "S".equals( cbObs.getVlrString() ) ) );
		hParam.put( "AGRUPFOR", new Boolean( "S".equals(cbAgrupFor.getVlrString() ) ) );

		dlGr = new FPrinterJob( "relatorios/FRPagar.jasper", "Relatório de Pagar/Pagas", sCab, rs, hParam, this );

		if ( bVisualizar==TYPE_PRINT.VIEW ) {
			dlGr.preview();
		}
		else {
			try {
				dlGr.print(true);
			} catch ( Exception err ) {
				Funcoes.mensagemErro( this, "Erro na impressão de relatório de Pagar/Pagas!" + err.getMessage(), true, con, err );
			}
		}
	}

	public void actionPerformed( ActionEvent evt ) {

		if ( evt.getSource() == btExp ) {
			exportaTXT();
		}
		else {
			super.actionPerformed( evt );
		}
	}

	public void setConexao( DbConnection cn ) {

		super.setConexao( cn );
		lcFor.setConexao( cn );
		lcPlanoPag.setConexao( cn );
		lcBanco.setConexao( cn );
		lcTipoCob.setConexao( cn );
		lcConta.setConexao( cn );

	}

}
