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
 * Este programa é licenciado de acordo com a LPG-PC (Licença Pública Geral para Programas de Computador), <BR>
 * versão 2.1.0 ou qualquer versão posterior. <BR>
 * A LPG-PC deve acompanhar todas PUBLICAÇÕES, DISTRIBUIÇÕES e REPRODUÇÕES deste Programa. <BR>
 * Caso uma cópia da LPG-PC não esteja disponível junto com este Programa, você pode contatar <BR>
 * o LICENCIADOR ou então pegar uma cópia em: <BR>
 * Licença: http://www.lpg.adv.br/licencas/lpgpc.rtf <BR>
 * Para poder USAR, PUBLICAR, DISTRIBUIR, REPRODUZIR ou ALTERAR este Programa é preciso estar <BR>
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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.SwingConstants;

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
import org.freedom.telas.FRelatorio;

public class FRPagar extends FRelatorio {

	private static final long serialVersionUID = 1L;

	private JTextFieldPad txtDataini = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtDatafim = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JRadioGroup<?, ?> cbFiltro = null;

	private JRadioGroup<?, ?> cbOrderm = null;

	private JTextFieldPad txtCodFor = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtRazFor = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private JTextFieldPad txtCodPlanoPag = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtDescPlanoPag = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private JCheckBoxPad cbObs = new JCheckBoxPad( "Imprimir observações?", "S", "N" );

	private ListaCampos lcFor = new ListaCampos( this );

	private ListaCampos lcPlanoPag = new ListaCampos( this );

	private JButton btExp = new JButton( Icone.novo( "btTXT.gif" ) );

	public FRPagar() {

		setTitulo( "Contas a Pagar" );
		setAtribos( 80, 80, 410, 340 );
		
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
		adic( cbOrderm, 7, 115, 360, 30 );		
		adic( new JLabelPad( "Cód.for." ), 7, 150, 80, 20 );
		adic( txtCodFor, 7, 170, 80, 20 );
		adic( new JLabelPad( "Razão social do fornecedor" ), 90, 150, 300, 20 );
		adic( txtRazFor, 90, 170, 277, 20 );
		adic( new JLabelPad( "Cód.pl.pag." ), 7, 190, 80, 20 );
		adic( txtCodPlanoPag, 7, 210, 80, 20 );
		adic( new JLabelPad( "Descrição do plano de pagamento" ), 90, 190, 300, 20 );
		adic( txtDescPlanoPag, 90, 210, 277, 20 );
		adic( cbObs, 7, 235, 360, 20 );

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
		vVals1.addElement( "V" );
		vVals1.addElement( "P" );
		cbOrderm = new JRadioGroup<String, String>( 1, 2, vLabs1, vVals1 );
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
		sql.append( "FROM FNITPAGAR IT,FNPAGAR P,CPFORNECED F " );
		sql.append( "WHERE P.FLAG IN " + AplicativoPD.carregaFiltro( con, org.freedom.telas.Aplicativo.iCodEmp ) );
		sql.append( " AND IT.CODEMP = P.CODEMP AND IT.CODFILIAL=P.CODFILIAL AND "  );
		sql.append( "P".equals( cbFiltro.getVlrString() ) ? "IT.DTPAGOITPAG" : "IT.DTVENCITPAG" );
		sql.append( " BETWEEN ? AND ? AND IT.STATUSITPAG IN (?,?) AND P.CODPAG=IT.CODPAG AND " );
		sql.append( "F.CODEMP=P.CODEMPFR AND F.CODFILIAL=P.CODFILIALFR AND F.CODFOR=P.CODFOR "  );
		sql.append( "".equals( txtCodFor.getVlrString() ) ? "" : " AND P.CODFOR=" + txtCodFor.getVlrString() );
		sql.append( "".equals( txtCodPlanoPag.getVlrString() ) ? "" : " AND P.CODPLANOPAG=" + txtCodPlanoPag.getVlrString() );
		sql.append( " AND P.CODEMP=? AND P.CODFILIAL=? " );
		sql.append( "ORDER BY " + 
				( "V".equals( cbOrderm.getVlrString() ) ? "IT.DTVENCITPAG" : "IT.DTPAGOITPAG" ) + ",F.RAZFOR" );

		try {

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

			rs = ps.executeQuery();

		} catch ( Exception e ) {
			e.printStackTrace();
		}

		return rs;
	}

	public void imprimir( boolean bVisualizar ) {

		if ( txtDatafim.getVlrDate().before( txtDataini.getVlrDate() ) ) {
			Funcoes.mensagemInforma( this, "Data final maior que a data inicial!" );
			return;
		}

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

		ResultSet rs = getResultSet();

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
			if ( !con.getAutoCommit() ) {
				con.commit();
			}

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

	public void actionPerformed( ActionEvent evt ) {

		if ( evt.getSource() == btExp ) {
			exportaTXT();
		}
		else {
			super.actionPerformed( evt );
		}
	}

	public void setConexao( Connection cn ) {

		super.setConexao( cn );
		lcFor.setConexao( cn );
		lcPlanoPag.setConexao( cn );
	}

}
