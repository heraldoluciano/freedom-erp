/**
 * @version 02/11/2003 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.std <BR>
 * Classe:
 * @(#)FRResumoDiario.java <BR>
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

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Vector;

import javax.swing.BorderFactory;

import net.sf.jasperreports.engine.JasperPrintManager;

import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.ImprimeOS;
import org.freedom.componentes.JCheckBoxPad;
import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JRadioGroup;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.AplicativoPD;
import org.freedom.telas.FPrinterJob;
import org.freedom.telas.FRelatorio;

public class FRResumoDiario extends FRelatorio {

	private static final long serialVersionUID = 1L;

	private JTextFieldPad txtDataini = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtDatafim = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtCodVend = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtDescVend = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JCheckBoxPad cbVendaCanc = new JCheckBoxPad( "Mostrar Canceladas", "S", "N" );

	private JRadioGroup rgTipo = null;

	private JRadioGroup rgFaturados = null;

	private JRadioGroup rgFinanceiro = null;

	private JRadioGroup rgFormato = null;

	private ListaCampos lcVend = new ListaCampos( this );

	public FRResumoDiario() {

		setTitulo( "Resumo Diario" );
		setAtribos( 80, 80, 296, 380 );

		Vector vLabs = new Vector();
		Vector vVals = new Vector();

		vLabs.addElement( "Grafico" );
		vLabs.addElement( "Texto" );
		vVals.addElement( "G" );
		vVals.addElement( "T" );
		rgTipo = new JRadioGroup( 1, 2, vLabs, vVals );
		rgTipo.setVlrString( "D" );

		Vector vLabs1 = new Vector();
		Vector vVals1 = new Vector();

		vLabs1.addElement( "Detalhado" );
		vLabs1.addElement( "Resumido" );
		vVals1.addElement( "D" );
		vVals1.addElement( "R" );
		rgFormato = new JRadioGroup( 1, 2, vLabs1, vVals1 );
		rgFormato.setVlrString( "D" );

		Vector vLabs2 = new Vector();
		Vector vVals2 = new Vector();

		vLabs2.addElement( "Faturado" );
		vLabs2.addElement( "Não Faturado" );
		vLabs2.addElement( "Ambos" );
		vVals2.addElement( "S" );
		vVals2.addElement( "N" );
		vVals2.addElement( "A" );
		rgFaturados = new JRadioGroup( 3, 1, vLabs2, vVals2 );
		rgFaturados.setVlrString( "S" );

		Vector vLabs3 = new Vector();
		Vector vVals3 = new Vector();

		vLabs3.addElement( "Financeiro" );
		vLabs3.addElement( "Não Finaceiro" );
		vLabs3.addElement( "Ambos" );
		vVals3.addElement( "S" );
		vVals3.addElement( "N" );
		vVals3.addElement( "A" );
		rgFinanceiro = new JRadioGroup( 3, 1, vLabs3, vVals3 );
		rgFinanceiro.setVlrString( "S" );

		lcVend.add( new GuardaCampo( txtCodVend, "CodVend", "Cód.comiss.", ListaCampos.DB_PK, false ) );
		lcVend.add( new GuardaCampo( txtDescVend, "NomeVend", "Nome do comissionado", ListaCampos.DB_SI, false ) );
		lcVend.montaSql( false, "VENDEDOR", "VD" );
		lcVend.setQueryCommit( false );
		lcVend.setReadOnly( true );
		txtCodVend.setNomeCampo( "CodVend" );
		txtCodVend.setFK( true );
		txtCodVend.setTabelaExterna( lcVend );

		txtDataini.setVlrDate( new Date() );
		txtDatafim.setVlrDate( new Date() );
		JLabelPad lbLinha = new JLabelPad();
		lbLinha.setBorder( BorderFactory.createEtchedBorder() );

		adic( new JLabelPad( "Periodo:" ), 7, 5, 100, 20 );
		adic( lbLinha, 60, 15, 210, 2 );
		adic( new JLabelPad( "De:" ), 7, 30, 30, 20 );
		adic( txtDataini, 32, 30, 97, 20 );
		adic( new JLabelPad( "Até:" ), 140, 30, 30, 20 );
		adic( txtDatafim, 170, 30, 100, 20 );

		adic( new JLabelPad( "Cód.comiss." ), 7, 60, 210, 20 );
		adic( txtCodVend, 7, 80, 70, 20 );
		adic( new JLabelPad( "Nome do comissionado" ), 80, 60, 210, 20 );
		adic( txtDescVend, 80, 80, 190, 20 );

		adic( rgTipo, 7, 115, 265, 30 );
		adic( rgFormato, 7, 155, 265, 30 );
		adic( rgFaturados, 7, 200, 120, 70 );
		adic( rgFinanceiro, 153, 200, 120, 70 );
		adic( cbVendaCanc, 7, 280, 200, 20 );

	}

	public void imprimir( boolean bVisualizar ) {

		if ( txtDatafim.getVlrDate().before( txtDataini.getVlrDate() ) ) {
			Funcoes.mensagemInforma( this, "Data final maior que a data inicial!" );
			return;
		}

		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sSQL = new StringBuffer();
		StringBuffer sCab = new StringBuffer();
		StringBuffer sWhere = new StringBuffer();
		String sWhere1 = "";
		String sWhere2 = "";
		String sWhere3 = "";

		try {

			if ( rgFaturados.getVlrString().equals( "S" ) ) {
				sWhere1 = " AND TM.FISCALTIPOMOV='S' ";
				sCab.append( "FATURADO" );
			}
			else if ( rgFaturados.getVlrString().equals( "N" ) ) {
				sWhere1 = " AND TM.FISCALTIPOMOV='N' ";
				if ( sCab.length() > 0 ) {
					sCab.append( " - " );
				}
				sCab.append( "NAO FATURADO" );
			}
			else if ( rgFaturados.getVlrString().equals( "A" ) )
				sWhere1 = " AND TM.FISCALTIPOMOV IN ('S','N') ";

			if ( rgFinanceiro.getVlrString().equals( "S" ) ) {
				sWhere2 = " AND TM.SOMAVDTIPOMOV='S' ";
				if ( sCab.length() > 0 ) {
					sCab.append( " / " );
				}
				sCab.append( "FINANCEIRO" );
			}
			else if ( rgFinanceiro.getVlrString().equals( "N" ) ) {
				sWhere2 = " AND TM.SOMAVDTIPOMOV='N' ";
				if ( sCab.length() > 0 ) {
					sCab.append( " - " );
				}
				sCab.append( "NAO FINANCEIRO" );
			}
			else if ( rgFinanceiro.getVlrString().equals( "A" ) ) {
				sWhere2 = " AND TM.SOMAVDTIPOMOV IN ('S','N') ";
			}

			if ( cbVendaCanc.getVlrString().equals( "N" ) ) {
				sWhere3 = " AND NOT SUBSTR(V.STATUSVENDA,1,1)='C' ";
			}

			if ( txtCodVend.getText().trim().length() > 0 ) {
				sWhere.append( " AND V.CODVEND = " );
				sWhere.append( txtCodVend.getText().trim() );
				sWhere.append( " AND V.CODEMPVD=" );
				sWhere.append( Aplicativo.iCodEmp );
				sWhere.append( " AND V.CODFILIALVD=" );
				sWhere.append( lcVend.getCodFilial() );
				sCab.append( "\nCOMISS.: " + txtCodVend.getVlrString() + " - " + txtDescVend.getText().trim() );
			}

			if ( rgFormato.getVlrString().equals( "D" ) ) {
				sSQL.append( "SELECT V.DTEMITVENDA,V.CODTIPOMOV,V.CODVENDA,V.DOCVENDA,V.SERIE," );
				sSQL.append( "V.STATUSVENDA,V.VLRPRODVENDA,V.VLRLIQVENDA,V.CODCLI,C.RAZCLI," );
				sSQL.append( "V.CODPLANOPAG,P.DESCPLANOPAG,V.VLRCOMISVENDA,V.VLRDESCITVENDA " );
				sSQL.append( "FROM VDVENDA V,VDCLIENTE C,FNPLANOPAG P, EQTIPOMOV TM " );
				sSQL.append( "WHERE TM.CODTIPOMOV=V.CODTIPOMOV AND TM.CODEMP=V.CODEMPTM" ); 
				sSQL.append( " AND TM.CODFILIAL=V.CODFILIALTM AND C.CODCLI=V.CODCLI" );
				sSQL.append( " AND C.CODEMP=V.CODEMPCL AND C.CODFILIAL=V.CODFILIALCL" );
				sSQL.append( " AND V.DTEMITVENDA BETWEEN ? AND ? AND " );
				sSQL.append( "P.CODPLANOPAG=V.CODPLANOPAG AND V.FLAG IN " );
				sSQL.append( AplicativoPD.carregaFiltro( con, org.freedom.telas.Aplicativo.iCodEmp ) ); 
				sSQL.append( " AND V.CODEMP=? AND V.CODFILIAL=?" );
				sSQL.append( sWhere );
				sSQL.append( sWhere1 ); 
				sSQL.append( sWhere2 );
				sSQL.append( sWhere3 ); 
				sSQL.append( " ORDER BY V.DTEMITVENDA,V.DOCVENDA" );
			}
			else if ( rgFormato.getVlrString().equals( "R" ) ) {
				sSQL.append( "SELECT V.DTEMITVENDA,SUM(V.VLRLIQVENDA) AS VALOR " ); 
				sSQL.append( "FROM VDVENDA V, EQTIPOMOV TM " );
				sSQL.append( "WHERE V.DTEMITVENDA BETWEEN ? AND ? AND V.FLAG IN " ); 
				sSQL.append( AplicativoPD.carregaFiltro( con, org.freedom.telas.Aplicativo.iCodEmp ) ); 
				sSQL.append( " AND TM.CODEMP=V.CODEMPTM AND TM.CODFILIAL=V.CODFILIALTM" );
				sSQL.append( " AND TM.CODTIPOMOV=V.CODTIPOMOV" );
				sSQL.append( sWhere );
				sSQL.append( sWhere1 );
				sSQL.append( sWhere2 ); 
				sSQL.append( sWhere3 ); 
				sSQL.append( " AND V.CODEMP=? AND V.CODFILIAL=? " ); 
				sSQL.append( " GROUP BY DTEMITVENDA" );
				sSQL.append( " ORDER BY DTEMITVENDA" );
			}

			ps = con.prepareStatement( sSQL.toString() );
			ps.setDate( 1, Funcoes.dateToSQLDate( txtDataini.getVlrDate() ) );
			ps.setDate( 2, Funcoes.dateToSQLDate( txtDatafim.getVlrDate() ) );
			ps.setInt( 3, Aplicativo.iCodEmp );
			ps.setInt( 4, ListaCampos.getMasterFilial( "VDVENDA" ) );
			rs = ps.executeQuery();

			if ( "T".equals( rgTipo.getVlrString() ) ) {
				imprimirTexto( bVisualizar, rs, sCab.toString() );
			}
			else if ( "G".equals( rgTipo.getVlrString() ) ) {
				imprimirGrafico( bVisualizar, rs, sCab.toString() );
			}

		} catch ( SQLException err ) {
			Funcoes.mensagemErro( this, "Erro consulta tabela de vendas!\n" + err.getMessage(), true, con, err );
		} finally {
			System.gc();
		}
	}

	private void imprimirTexto( final boolean bVisualizar, final ResultSet rs, final String sCab ) {

		String sDtemitvenda = "";
		String sLinhaFina = Funcoes.replicate( "-", 133 );
		String sLinhaDupla = Funcoes.replicate( "=", 133 );
		BigDecimal bTotalDiaVal = new BigDecimal( "0" );
		BigDecimal bTotalDiaDesc = new BigDecimal( "0" );
		BigDecimal bTotalDiaLiq = new BigDecimal( "0" );
		BigDecimal bTotalVal = new BigDecimal( "0" );
		BigDecimal bTotalDesc = new BigDecimal( "0" );
		BigDecimal bTotalLiq = new BigDecimal( "0" );
		ImprimeOS imp = new ImprimeOS( "", con );
		int linPag = imp.verifLinPag() - 1;
		int iLinha = 1;
		int iCol = 0;
		boolean bFimDia = false;

		try {

			if ( rgFormato.getVlrString().equals( "D" ) ) {

				imp.montaCab();
				imp.setTitulo( "Resumo Diário de Vendas" );
				imp.addSubTitulo( "RESUMO DIARIO DE VENDAS   -   PERIODO DE :" + txtDataini.getVlrString() + " Até: " + txtDatafim.getVlrString() );

				if ( sCab.length() > 0 ) {
					imp.addSubTitulo( sCab );
				}

				imp.limpaPags();

				while ( rs.next() ) {
					if ( imp.pRow() >= ( linPag - 1 ) ) {
						imp.pulaLinha( 1, imp.comprimido() );
						imp.say( 0, "+" + sLinhaFina + "+" );
						imp.incPags();
						imp.eject();
					}
					if ( imp.pRow() == 0 ) {
						imp.impCab( 136, true );
						imp.pulaLinha( 0, imp.comprimido() );
						imp.say( 0, "|" );
						imp.say( 135, "|" );
						imp.pulaLinha( 1, imp.comprimido() );
						imp.say( 0, "|" + sLinhaFina + "|" );
						imp.pulaLinha( 1, imp.comprimido() );
						imp.say( 0, "| Dt. Emissao" );
						imp.say( 17, "NF./Ped." );
						imp.say( 31, "Cliente" );
						imp.say( 85, "|    Valor   Desconto " + "  Liquido    F.Pagto." );
						imp.say( 135, "|" );
						imp.pulaLinha( 1, imp.comprimido() );
						imp.say( 0, "|" + sLinhaFina + "|" );
					}
					if ( ( !Funcoes.sqlDateToStrDate( rs.getDate( "dtemitvenda" ) ).equals( sDtemitvenda ) ) & ( bFimDia ) ) {
						imp.pulaLinha( 1, imp.comprimido() );
						imp.say( 0, "|" + sLinhaFina + "|" );
						imp.pulaLinha( 1, imp.comprimido() );
						imp.say( 0, "|" );
						imp.say( 58, "Totais do Dia-> " + sDtemitvenda + " |" + Funcoes.strDecimalToStrCurrency( 10, 2, "" + bTotalDiaVal ) + Funcoes.strDecimalToStrCurrency( 10, 2, "" + bTotalDiaDesc ) + Funcoes.strDecimalToStrCurrency( 11, 2, "" + bTotalDiaLiq ) );
						imp.say( 135, "|" );
						imp.pulaLinha( 1, imp.comprimido() );
						imp.say( imp.pRow(), 0, "|" + sLinhaFina + "|" );
						bTotalDiaVal = new BigDecimal( "0" );
						bTotalDiaDesc = new BigDecimal( "0" );
						bTotalDiaLiq = new BigDecimal( "0" );
						bFimDia = false;
					}
					imp.pulaLinha( 1, imp.comprimido() );
					imp.say( 0, "|" );
					if ( !Funcoes.sqlDateToStrDate( rs.getDate( "dtemitvenda" ) ).equals( sDtemitvenda ) ) {
						imp.say( 3, Funcoes.sqlDateToStrDate( rs.getDate( "dtemitvenda" ) ) );
					}

					imp.say( 17, rs.getString( "StatusVenda" ).substring( 0, 1 ).equals( "P" ) ? "P-" + Funcoes.copy( rs.getString( "codvenda" ), 0, 6 ) : "V-" + Funcoes.copy( rs.getString( "docvenda" ), 0, 6 ) );
					imp.say( 31, Funcoes.copy( rs.getString( "codcli" ), 0, 8 ) + "-" + Funcoes.copy( rs.getString( "razcli" ), 0, 45 ) + "|" + Funcoes.strDecimalToStrCurrency( 10, 2, rs.getString( "vlrprodvenda" ) ) + Funcoes.strDecimalToStrCurrency( 10, 2, rs.getString( "vlrdescitvenda" ) )
							+ Funcoes.strDecimalToStrCurrency( 11, 2, rs.getString( "vlrliqvenda" ) ) + "  " + Funcoes.copy( rs.getString( "descplanopag" ), 0, 16 ) + "|" );
					if ( rs.getString( "VlrProdVenda" ) != null ) {
						bTotalDiaVal = bTotalDiaVal.add( new BigDecimal( rs.getString( "VlrProdVenda" ) ) );
						bTotalVal = bTotalVal.add( new BigDecimal( rs.getString( "VlrProdVenda" ) ) );
					}

					if ( rs.getString( "VlrDescitvenda" ) != null ) {
						bTotalDiaDesc = bTotalDiaDesc.add( new BigDecimal( rs.getString( "VlrDescitVenda" ) ) );
						bTotalDesc = bTotalDesc.add( new BigDecimal( rs.getString( "VlrDescitVenda" ) ) );
					}

					if ( rs.getString( "VlrLiqVenda" ) != null ) {
						bTotalDiaLiq = bTotalDiaLiq.add( new BigDecimal( rs.getString( "VlrLiqVenda" ) ) );
						bTotalLiq = bTotalLiq.add( new BigDecimal( rs.getString( "VlrLiqVenda" ) ) );
					}

					bFimDia = true;
					sDtemitvenda = Funcoes.sqlDateToStrDate( rs.getDate( "Dtemitvenda" ) );

				}

				if ( bFimDia ) {
					imp.pulaLinha( 1, imp.comprimido() );
					imp.say( 0, "|" + sLinhaFina + "|" );
					imp.pulaLinha( 1, imp.comprimido() );
					imp.say( 0, "|" );
					imp.say( 58, "Totais do Dia-> " + sDtemitvenda + " |" + Funcoes.strDecimalToStrCurrency( 10, 2, "" + bTotalDiaVal ) + Funcoes.strDecimalToStrCurrency( 10, 2, "" + bTotalDiaDesc ) + Funcoes.strDecimalToStrCurrency( 11, 2, "" + bTotalDiaLiq ) );
					imp.say( 135, "|" );
				}

				imp.pulaLinha( 1, imp.comprimido() );
				imp.say( 0, "|" + sLinhaDupla + "|" );
				imp.pulaLinha( 1, imp.comprimido() );
				imp.say( 0, "|" );
				imp.say( 69, "Totais Geral    |" + Funcoes.strDecimalToStrCurrency( 10, 2, "" + bTotalVal ) + Funcoes.strDecimalToStrCurrency( 10, 2, "" + bTotalDesc ) + Funcoes.strDecimalToStrCurrency( 11, 2, "" + bTotalLiq ) );
				imp.say( 135, "|" );
				imp.pulaLinha( 1, imp.comprimido() );
				imp.say( 0, "|" + sLinhaDupla + "|" );

			}
			else if ( rgFormato.getVlrString().equals( "R" ) ) {

				imp.montaCab();
				imp.setTitulo( "Resumo Diário de Vendas" );
				imp.addSubTitulo( "RESUMO DE TOTAL DE VENDAS - PERIODO DE :" + txtDataini.getVlrString() + " Até: " + txtDatafim.getVlrString() );

				if ( sCab.length() > 0 ) {
					imp.addSubTitulo( sCab );
				}

				while ( rs.next() ) {
					if ( imp.pRow() >= ( linPag - 1 ) ) {
						imp.pulaLinha( 1, imp.comprimido() );
						imp.say( 0, "+" + sLinhaFina + "+" );
						imp.incPags();
						imp.eject();
					}
					if ( imp.pRow() == 0 ) {
						imp.impCab( 136, true );
						imp.say( 0, "|" + sLinhaFina + "|" );
						imp.pulaLinha( 1, imp.comprimido() );
						imp.say( 0, "|  Data" );
						imp.say( 22, "Valor" );
						imp.say( 35, "|  Data" );
						imp.say( 57, "Valor" );
						imp.say( 70, "|  Data" );
						imp.say( 92, "Valor" );
						imp.say( 105, "|  Data" );
						imp.say( 126, "Valor" );
						imp.say( 135, "|" );
						imp.pulaLinha( 1, imp.comprimido() );
						imp.say( 0, "|" + sLinhaFina + "|" );
					}

					imp.pulaLinha( iLinha, imp.comprimido() );
					imp.say( iCol, "|  " + Funcoes.sqlDateToStrDate( rs.getDate( 1 ) ) );
					imp.say( iCol + 14, " " + Funcoes.strDecimalToStrCurrency( 15, 2, "" + rs.getString( 2 ) ) );

					if ( iCol == 0 ) {
						iLinha = 0;
						iCol = 35;
					}
					else if ( iCol == 35 )
						iCol = 70;
					else if ( iCol == 70 )
						iCol = 105;
					else {
						imp.say( 135, "|" );
						iCol = 0;
						iLinha = 1;
					}

					if ( rs.getString( 2 ) != null ) {
						bTotalDiaLiq = bTotalDiaLiq.add( new BigDecimal( rs.getString( 2 ) ) );
						bTotalLiq = bTotalLiq.add( new BigDecimal( rs.getString( 2 ) ) );
					}
				}
				if ( ( iCol < 105 ) && ( iLinha == 0 ) )
					imp.say( 135, "|" );

			}

			imp.pulaLinha( 1, imp.comprimido() );
			imp.say( 0, "+" + sLinhaDupla + "+" );
			imp.pulaLinha( 1, imp.comprimido() );
			imp.say( 0, "|" );
			imp.say( 85, "| Total Geral do Período   | " + Funcoes.strDecimalToStrCurrency( 11, 2, "" + bTotalLiq ) );
			imp.say( 135, "|" );
			imp.pulaLinha( 1, imp.comprimido() );
			imp.say( 0, "+" + sLinhaDupla + "+" );

			imp.eject();
			imp.fechaGravacao();

			if ( !con.getAutoCommit() ) {
				con.commit();
			}

			if ( bVisualizar ) {
				imp.preview( this );
			}
			else {
				imp.print();
			}
		} catch ( Exception e ) {
			e.printStackTrace();
			Funcoes.mensagemErro( this, "Erro ao montar o relatorio!\n" + e.getMessage(), true, con, e );
		}
	}

	public void imprimirGrafico( final boolean bVisualizar, final ResultSet rs, final String sCab ) {

		FPrinterJob dlGr = null;

		if ( "D".equals( rgFormato.getVlrString() ) ) {
			dlGr = new FPrinterJob( "relatorios/ResumoDiarioDetalhado.jasper", "Resumo de Vendas diario - detalhado", sCab, rs, null, this );
		}
		else if ( "R".equals( rgFormato.getVlrString() ) ) { 
			dlGr = new FPrinterJob( "relatorios/ResumoDiarioResumido.jasper", "Resumo de Vendas diario - detalhado", sCab, rs, null, this );
		}

		if ( bVisualizar ) {
			dlGr.setVisible( true );
		}
		else {
			try {
				JasperPrintManager.printReport( dlGr.getRelatorio(), true );
			} catch ( Exception err ) {
				Funcoes.mensagemErro( this, "Erro na impressão de relatório de resumo diario!" + err.getMessage(), true, con, err );
			}
		}
	}

	public void setConexao( Connection cn ) {

		super.setConexao( cn );
		lcVend.setConexao( con );
	}
}
