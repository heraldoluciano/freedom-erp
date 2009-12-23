/**
 * @version 02/11/2003 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.std <BR>
 * Classe:
 * @(#)FRPagar.java <BR>
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
 * 
 */

package org.freedom.modulos.std;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.math.BigDecimal;
import org.freedom.infra.model.jdbc.DbConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.BorderFactory;
import org.freedom.componentes.JButtonPad;
import javax.swing.SwingConstants;

import net.sf.jasperreports.engine.JasperPrintManager;

import org.freedom.componentes.JLabelPad;

import org.freedom.bmps.Icone;
import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.ImprimeOS;
import org.freedom.componentes.JCheckBoxPad;
import org.freedom.componentes.JRadioGroup;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.AplicativoPD;
import org.freedom.telas.FPrinterJob;
import org.freedom.telas.FRelatorio;

public class FRPagar extends FRelatorio {

	private static final long serialVersionUID = 1L;

	private JTextFieldPad txtDataini = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtDatafim = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JRadioGroup<?, ?> cbFiltro = null;

	private JRadioGroup<?, ?> cbOrdem = null;
	
	private JRadioGroup<?, ?> rgTipoRel = null;

	private JTextFieldPad txtCodFor = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtRazFor = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private JTextFieldPad txtCodPlanoPag = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtDescPlanoPag = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private JCheckBoxPad cbObs = new JCheckBoxPad( "Imprimir observações?", "S", "N" );
	
	private JCheckBoxPad cbParPar = new JCheckBoxPad( "Imprimir pagamentos parciais?", "S", "N" );

	private ListaCampos lcFor = new ListaCampos( this );

	private ListaCampos lcPlanoPag = new ListaCampos( this );

	private JButtonPad btExp = new JButtonPad( Icone.novo( "btTXT.gif" ) );
	
	private ListaCampos lcBanco = new ListaCampos( this );
	
	private JTextFieldPad txtCodBanco = new JTextFieldPad( JTextFieldPad.TP_STRING, 3, 0 );

	private JTextFieldFK txtDescBanco = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	public FRPagar() {

		setTitulo( "Contas a Pagar" );
		setAtribos( 80, 80, 410, 450 );
		
		montaListaCampos();
		montaCheckBox();
		montaTela();

		Calendar cal = Calendar.getInstance();
		txtDatafim.setVlrDate( cal.getTime() );
		cal.set( Calendar.DAY_OF_MONTH, 1 );
		txtDataini.setVlrDate( cal.getTime() );
		
	}

	private void montaTela() {
		
		JLabelPad lbLinha = new JLabelPad();
		lbLinha.setBorder( BorderFactory.createEtchedBorder() );
		JLabelPad lbPeriodo = new JLabelPad( "Periodo:", SwingConstants.CENTER );
		lbPeriodo.setOpaque( true );

		adic( lbPeriodo, 17, 0, 80, 20 );
		adic( lbLinha, 7, 10, 360, 36 );
		
		adic( new JLabelPad( "De:", SwingConstants.CENTER ), 7, 20, 60, 20 );
		adic( txtDataini, 67, 20, 100, 20 );
		adic( new JLabelPad( "Até:", SwingConstants.CENTER ), 167, 20, 60, 20 );
		adic( txtDatafim, 227, 20, 100, 20 );
		adic( new JLabelPad( "Filtro:" ), 7, 45, 360, 20 );
		adic( cbFiltro, 7, 65, 360, 30 );
		adic( new JLabelPad( "Ordem:" ), 7, 95, 360, 20 );
		adic( cbOrdem, 7, 115, 360, 30 );	
		adic( rgTipoRel , 7, 150, 360, 30 );
		adic( new JLabelPad( "Cód.for." ), 7, 180, 80, 20 );
		adic( txtCodFor, 7, 200, 80, 20 );
		adic( new JLabelPad( "Razão social do fornecedor" ), 90, 180, 300, 20 );
		adic( txtRazFor, 90, 200, 277, 20 );
		
		adic( new JLabelPad( "Cód.pl.pag." ), 7, 223, 80, 20 );
		adic( txtCodPlanoPag, 7, 245, 80, 20 );		
		adic( new JLabelPad( "Descrição do plano de pagamento" ), 90, 223, 300, 20 );
		adic( txtDescPlanoPag, 90, 245, 277, 20 );

		adic( new JLabelPad( "Cód.Banco" ), 7, 263, 80, 20 );
		adic( txtCodBanco, 7, 285, 80, 20 );						
		adic( new JLabelPad( "Nome do banco" ), 90, 263, 300, 20 );
		adic( txtDescBanco, 90, 285, 277, 20 );
		
		adic( cbObs, 7, 320, 385, 20 );
		adic( cbParPar, 7, 340, 360, 20 );

		btExp.setToolTipText( "Exporta para aquivo no formato csv." );
		btExp.setPreferredSize( new Dimension( 40, 28 ) );
		pnBotoes.setPreferredSize( new Dimension( 120, 28 ) );
		pnBotoes.add( btExp );

		btExp.addActionListener( this );

	}
	
	public void montaListaCampos() {

		lcFor.add( new GuardaCampo( txtCodFor, "CodFor", "Cód.forn.", ListaCampos.DB_PK, false ) );
		lcFor.add( new GuardaCampo( txtRazFor, "RazFor", "Razão social do fornecedor", ListaCampos.DB_SI, false ) );
		lcFor.montaSql( false, "FORNECED", "CP" );
		lcFor.setReadOnly( true );
		txtCodFor.setTabelaExterna( lcFor );
		txtCodFor.setFK( true );
		txtCodFor.setNomeCampo( "CodFor" );

		lcPlanoPag.add( new GuardaCampo( txtCodPlanoPag, "CodPlanoPag", "Cód.pl.pag.", ListaCampos.DB_PK, false ) );
		lcPlanoPag.add( new GuardaCampo( txtDescPlanoPag, "DescPlanoPag", "Descrição do plano de pagamento", ListaCampos.DB_SI, false ) );
		lcPlanoPag.montaSql( false, "PLANOPAG", "FN" );
		lcPlanoPag.setReadOnly( true );
		txtCodPlanoPag.setTabelaExterna( lcPlanoPag );
		txtCodPlanoPag.setFK( true );
		txtCodPlanoPag.setNomeCampo( "CodPlanoPag" );
		
		lcBanco.add( new GuardaCampo( txtCodBanco, "CodBanco", "Cód.banco.", ListaCampos.DB_PK, false ) );
		lcBanco.add( new GuardaCampo( txtDescBanco, "NomeBanco", "Nome do banco", ListaCampos.DB_SI, false ) );
		lcBanco.montaSql( false, "BANCO", "FN" );
		lcBanco.setReadOnly( true );
		txtCodBanco.setTabelaExterna( lcBanco );
		txtCodBanco.setFK( true );
		txtCodBanco.setNomeCampo( "CodBanco" );
		
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
		vVals2.addElement("G");
		vVals2.addElement("T");
		vLabs2.addElement("Grafico");
		vLabs2.addElement("Texto");
		rgTipoRel = new JRadioGroup<String, String>(1, 2, vLabs2, vVals2 );
		rgTipoRel.setVlrString("G");
	}

	public void exportaTXT() {

		Vector<String> vLinhas = new Vector<String>();
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
				sVencto = rs.getString( "DTVENCITPAG" ) != null ? Funcoes.sqlDateToStrDate( rs.getDate( "DTVENCITPAG" ) ) : "";
				sDocPag = rs.getString( "DOCPAG" ) != null ? rs.getString( "DOCPAG" ).trim() : "";
				sNParcPag = rs.getString( "NPARCPAG" ) != null ? rs.getString( "NPARCPAG" ).trim() : "";
				sPedido = rs.getString( "CODCOMPRA" ) != null ? rs.getString( "CODCOMPRA" ).trim() : "";
				sDuplic = sDocPag + "/" + sNParcPag;
				sForneced = rs.getString( "RAZFOR" ) != null ? rs.getString( "RAZFOR" ).trim() : "";
				sDtCompra = rs.getString( "DTEMITCOMPRA" ) != null ? Funcoes.sqlDateToStrDate( rs.getDate( "DTEMITCOMPRA" ) ) : "";
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

		}
		else {
			Funcoes.mensagemInforma( this, "Não há informações para exportar!" );
		}
	}
	
	public ResultSet getResultSet() {

		PreparedStatement ps = null;
		ResultSet rs = null;
		String sFiltroPag = cbFiltro.getVlrString();
		
		StringBuilder sql = new StringBuilder();
		
		sql.append( "SELECT IT.DTVENCITPAG,IT.NPARCPAG,P.CODCOMPRA,P.CODFOR,F.RAZFOR," ); 
		sql.append( "IT.VLRPARCITPAG,IT.VLRPAGOITPAG,IT.VLRAPAGITPAG,IT.DTPAGOITPAG,"  );
		sql.append( "(SELECT C.STATUSCOMPRA FROM CPCOMPRA C WHERE C.FLAG IN " );
		sql.append( AplicativoPD.carregaFiltro( con, org.freedom.telas.Aplicativo.iCodEmp ) );
		sql.append( " AND C.CODEMP=P.CODEMPCP AND C.CODFILIAL=P.CODFILIALCP AND C.CODCOMPRA=P.CODCOMPRA),"  );
		sql.append( "P.DOCPAG,IT.OBSITPAG, " );
		sql.append( "(SELECT C.DTEMITCOMPRA FROM CPCOMPRA C WHERE C.FLAG IN " );
		sql.append( AplicativoPD.carregaFiltro( con, org.freedom.telas.Aplicativo.iCodEmp ) );
		sql.append( " AND C.CODEMP=P.CODEMPCP AND C.CODFILIAL=P.CODFILIALCP AND C.CODCOMPRA=P.CODCOMPRA) AS DTEMITCOMPRA "  );
		sql.append( "FROM FNITPAGAR IT,FNPAGAR P,CPFORNECED F, FNCONTA CT " );
		sql.append( "WHERE P.FLAG IN " + AplicativoPD.carregaFiltro( con, org.freedom.telas.Aplicativo.iCodEmp ) );
		sql.append( " AND IT.CODEMP = P.CODEMP AND IT.CODFILIAL=P.CODFILIAL "  );
		sql.append( " AND CT.CODEMP = IT.CODEMPCA AND CT.CODFILIAL=IT.CODFILIALCA AND CT.NUMCONTA=IT.NUMCONTA AND "  );
		
		if ("P".equals( cbOrdem.getVlrString() )) {
			sql.append( "IT.DTPAGOITPAG" );
		} 
		else if ("V".equals( cbOrdem.getVlrString() )) {
			sql.append( " IT.DTVENCITPAG " );
		} 
		else {
			sql.append( " IT.DTITPAG " );
		}
		
		sql.append( " BETWEEN ? AND ? AND IT.STATUSITPAG IN (?,?) AND P.CODPAG=IT.CODPAG AND " );
		sql.append( "F.CODEMP=P.CODEMPFR AND F.CODFILIAL=P.CODFILIALFR AND F.CODFOR=P.CODFOR "  );
		sql.append( "".equals( txtCodFor.getVlrString() ) ? "" : " AND P.CODFOR=" + txtCodFor.getVlrString() );
		sql.append( "".equals( txtCodPlanoPag.getVlrString() ) ? "" : " AND P.CODPLANOPAG=" + txtCodPlanoPag.getVlrString() );
		sql.append( " AND P.CODEMP=? AND P.CODFILIAL=? " );
		
		if(txtCodBanco.getVlrInteger()>0) {
			sql.append(  " AND COALESCE(CT.CODEMPBO,P.CODEMPBO)=? AND COALESCE(CT.CODFILIALBO,P.CODFILIALBO)=? AND COALESCE(CT.CODBANCO,P.CODBANCO)=? " );
		}

		sql.append( "ORDER BY " );
		
		if ("P".equals( cbOrdem.getVlrString() )) {
			sql.append( "IT.DTPAGOITPAG" );
		} 
		else if ("V".equals( cbOrdem.getVlrString() )) {
			sql.append( "IT.DTVENCITPAG" );
		} 
		else {
			sql.append( "IT.DTITPAG" );
		}
		
		sql.append( ", F.RAZFOR" );
		
		try {

			System.out.println( sql.toString() );
			
			ps = con.prepareStatement( sql.toString() );
			
			ps.setDate( 1, Funcoes.dateToSQLDate( txtDataini.getVlrDate() ) );
			ps.setDate( 2, Funcoes.dateToSQLDate( txtDatafim.getVlrDate() ) );
			if ( sFiltroPag.equals( "N" ) ) {
				ps.setString( 3, "P1" );
				ps.setString( 4, "P1" );
			}
			else if ( sFiltroPag.equals( "P" ) ) {
				ps.setString( 3, "PP" );
				ps.setString( 4, "PP" );
			}
			else if ( sFiltroPag.equals( "A" ) ) {
				ps.setString( 3, "P1" );
				ps.setString( 4, "PP" );
			}
			ps.setInt( 5, Aplicativo.iCodEmp );
			ps.setInt( 6, ListaCampos.getMasterFilial( "FNPAGAR" ) );
			
			
			if(txtCodBanco.getVlrInteger()>0) {
				ps.setInt( 7, lcBanco.getCodEmp() );
				ps.setInt( 8, lcBanco.getCodFilial() );
				ps.setInt( 9, txtCodBanco.getVlrInteger() );

			}

			

			rs = ps.executeQuery();

		} catch ( Exception e ) {
			e.printStackTrace();
		}

		return rs;
	}


	public void imprimir( boolean bVisualizar ) {

		PreparedStatement ps = null;
		String sFiltroPag = cbFiltro.getVlrString();
		String sCab = "";
		
		if ( sFiltroPag.equals( "N" ) ) {
			sCab = " RELATÓRIO DE CONTAS A PAGAR ";
		}
		else if ( sFiltroPag.equals( "P" ) ) {
			sCab = " RELATÓRIO DE CONTAS PAGAS ";
		}
		else if ( sFiltroPag.equals( "A" ) ) {
			sCab = " RELATÓRIO DE CONTAS A PAGAR / PAGAS ";
		}
		
		if ( txtDatafim.getVlrDate().before( txtDataini.getVlrDate() ) ) {
			Funcoes.mensagemInforma( this, "Data final maior que a data inicial!" );
			return ;
		}
		
		ResultSet rs = getResultSet();

		sCab += "   Periodo de: " + txtDataini.getVlrString() + "  Até:  " + txtDatafim.getVlrString();
		
		if("T".equals( rgTipoRel.getVlrString())){
			imprimiTexto( rs, bVisualizar, sCab );
		}
		else{
			imprimiGrafico( rs, bVisualizar, sCab ); 
		}
	}

	public void imprimiTexto(  ResultSet rs, boolean bVisualizar, String sCab ) {
		
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
			imp.addSubTitulo( "RELATORIO DE CONTAS " + sPag + "   -   PERIODO DE :" 
					+ txtDataini.getVlrString() + " ATE: " + txtDatafim.getVlrString() );

			while ( rs.next() ) {

				if ( imp.pRow() >= ( linPag - 1 ) ) {
					imp.pulaLinha( 1, imp.comprimido() );
					imp.say( 0, "+" + Funcoes.replicate( "-", 133 ) + "+" );
					imp.incPags();
					imp.eject();
				}

				if ( imp.pRow() == 0 ) {

					imp.impCab( 136, true );
					imp.say( 0, imp.comprimido() );
					imp.say( 0, "|" + Funcoes.replicate( "-", 133 ) + "|" );
					imp.pulaLinha( 1, imp.comprimido() );
					imp.say( 0, "| Vencto.    |" );
					imp.say( 15, " Fornecedor                               |" );
					imp.say( 59, " Doc.      |" );
					imp.say( 72, " Vlr. da Parc. |" );
					imp.say( 89, " Vlr Pago      |" );
					imp.say( 106, " Vlr Aberto   |" );
					imp.say( 122, " Data Pagto. |" );
					imp.pulaLinha( 1, imp.comprimido() );
					imp.say( 0, "|" + Funcoes.replicate( "-", 133 ) + "|" );

				}

				if ( ( !Funcoes.sqlDateToStrDate( rs.getDate( "DtVencItPag" ) ).equals( sDtVencItPag ) ) & ( bFimDia ) ) {

					imp.pulaLinha( 1, imp.comprimido() );
					imp.say( 0, "|" + Funcoes.replicate( "-", 133 ) + "|" );
					imp.pulaLinha( 1, imp.comprimido() );
					imp.say( 0, "|" );
					imp.say( 41, 
							"Totais do Dia-> | " 
							+ sDtVencItPag + " | " 
							+ Funcoes.strDecimalToStrCurrency( 14, 2, String.valueOf( bTotalDiaParc ) ) + " | " 
							+ Funcoes.strDecimalToStrCurrency( 14, 2, String.valueOf( bTotalDiaPago ) ) + " | "
							+ Funcoes.strDecimalToStrCurrency( 13, 2, String.valueOf( bTotalDiaApag ) ) + " | " );
					imp.say( imp.pRow(), 135, "|" );
					imp.pulaLinha( 1, imp.comprimido() );
					imp.say( 0, "|" + Funcoes.replicate( "-", 133 ) + "|" );

					bTotalDiaParc = new BigDecimal( "0" );
					bTotalDiaPago = new BigDecimal( "0" );
					bTotalDiaApag = new BigDecimal( "0" );
					bFimDia = false;

				}

				imp.pulaLinha( 1, imp.comprimido() );
				imp.say( 0, "|" );

				if ( !Funcoes.sqlDateToStrDate( rs.getDate( "DtVencItPag" ) ).equals( sDtVencItPag ) )
					imp.say( 3, Funcoes.sqlDateToStrDate( rs.getDate( "DtVencItPag" ) ) );

				imp.say( 14, "| " 
						+ Funcoes.copy( rs.getString( "CodFor" ), 0, 6 ) + "-" + Funcoes.copy( rs.getString( "RazFor" ), 0, 33 ) + " |" );

				sDtPago = Funcoes.copy( rs.getString( "DtPagoItPag" ) != null ? Funcoes.sqlDateToStrDate( rs.getDate( "DtPagoItPag" ) ) : " ", 0, 10 );

				imp.say( 61, 
						( Funcoes.copy( rs.getString( 10 ), 0, 1 ).equals( "P" ) 
								? Funcoes.copy( rs.getString( "CodCompra" ), 0, 6 ) 
										: Funcoes.copy( rs.getString( "DocPag" ), 0, 6 ) ) + "/" 
						+ Funcoes.copy( rs.getString( "NParcPag" ), 0, 2 ) + "| "
						+ Funcoes.strDecimalToStrCurrency( 14, 2, rs.getString( "VlrParcItPag" ) ) + " | " 
						+ Funcoes.strDecimalToStrCurrency( 14, 2, rs.getString( "VlrPagoItPag" ) ) + " | " 
						+ Funcoes.strDecimalToStrCurrency( 13, 2, rs.getString( "VlrApagItPag" ) ) + " |  " 
						+ sDtPago + "  |" );
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
				sDtVencItPag = Funcoes.sqlDateToStrDate( rs.getDate( "DtVencItPag" ) );

			}

			if ( bFimDia ) {
				imp.pulaLinha( 1, imp.comprimido() );
				imp.say( 0, "|" + Funcoes.replicate( "-", 133 ) + "|" );
				imp.pulaLinha( 1, imp.comprimido() );
				imp.say( 0, "|" );
				imp.say( 41, 
						"Totais do Dia-> | " 
						+ sDtVencItPag + " | " 
						+ Funcoes.strDecimalToStrCurrency( 14, 2, String.valueOf( bTotalDiaParc ) ) + " | " 
						+ Funcoes.strDecimalToStrCurrency( 14, 2, String.valueOf( bTotalDiaPago ) ) + " | "
						+ Funcoes.strDecimalToStrCurrency( 13, 2, String.valueOf( bTotalDiaApag ) ) + " | " );
				imp.say( 135, "|" );
			}

			imp.pulaLinha( 1, imp.comprimido() );
			imp.say( 0, "|" + Funcoes.replicate( "=", 133 ) + "|" );
			imp.pulaLinha( 1, imp.comprimido() );
			imp.say( 0, "|" );
			imp.say( 55, 
					"Totais Geral-> | " 
					+ Funcoes.strDecimalToStrCurrency( 14, 2, String.valueOf( bTotParc ) ) + " | " 
					+ Funcoes.strDecimalToStrCurrency( 14, 2, String.valueOf( bTotalPago ) ) + " | " 
					+ Funcoes.strDecimalToStrCurrency( 13, 2, String.valueOf( bTotalApag ) ) + " | " );
			imp.say( 135, "|" );
			imp.pulaLinha( 1, imp.comprimido() );
			imp.say( 0, "+" + Funcoes.replicate( "=", 133 ) + "+" );

			imp.eject();
			imp.fechaGravacao();

			rs.close();
			con.commit();

		} catch ( SQLException err ) {
			err.printStackTrace();
			Funcoes.mensagemErro( this, "Erro consulta tabela de preços!\n" + err.getMessage(), true, con, err );
		}

		if ( bVisualizar ) {
			imp.preview( this );
		}
		else {
			imp.print();
		}
	}
	
	private void imprimiGrafico( final ResultSet rs, final boolean bVisualizar,  final String sCab ) {

		FPrinterJob dlGr = null;
		HashMap<String, Object> hParam = new HashMap<String, Object>();

		hParam.put( "CODEMP", Aplicativo.iCodEmp );
		hParam.put( "CODFILIAL", ListaCampos.getMasterFilial( "CPCOMPRA" ) );
		hParam.put( "RAZAOEMP", Aplicativo.sEmpSis );
		hParam.put( "FILTROS", sCab );
		hParam.put( "DATAORDEM", cbOrdem.getVlrString() );

		dlGr = new FPrinterJob( "relatorios/FRPagar.jasper", "Relatório de Pagar/Pagas", sCab, rs, hParam, this );

		if ( bVisualizar ) {
			dlGr.setVisible( true );
		}
		else {
			try {
				JasperPrintManager.printReport( dlGr.getRelatorio(), true );
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
		
	}

}
