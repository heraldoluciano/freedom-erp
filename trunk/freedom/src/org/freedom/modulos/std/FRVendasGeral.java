/**
 * @version 02/11/2003 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.std <BR>
 * Classe:
 * @(#)FRVendasGeral.java <BR>
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
import org.freedom.telas.FRelatorio;

public class FRVendasGeral extends FRelatorio {

	private static final long serialVersionUID = 1L;

	private JTextFieldPad txtDataini = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtDatafim = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtCodVend = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtDescVend = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JCheckBoxPad cbVendaCanc = new JCheckBoxPad( "Mostrar Canceladas", "S", "N" );

	private JRadioGroup rgFaturados = null;

	private JRadioGroup rgFinanceiro = null;

	private Vector vLabsFat = new Vector();

	private Vector vValsFat = new Vector();

	private Vector vLabsFin = new Vector();

	private Vector vValsFin = new Vector();

	private ListaCampos lcVend = new ListaCampos( this );

	public FRVendasGeral() {

		setTitulo( "Vendas em Geral" );
		setAtribos( 80, 80, 295, 300 );

		txtDataini.setVlrDate( new Date() );
		txtDatafim.setVlrDate( new Date() );
		JLabelPad lbLinha = new JLabelPad();
		lbLinha.setBorder( BorderFactory.createEtchedBorder() );

		vLabsFat.addElement( "Faturado" );
		vLabsFat.addElement( "Não Faturado" );
		vLabsFat.addElement( "Ambos" );
		vValsFat.addElement( "S" );
		vValsFat.addElement( "N" );
		vValsFat.addElement( "A" );
		rgFaturados = new JRadioGroup( 3, 1, vLabsFat, vValsFat );
		rgFaturados.setVlrString( "S" );

		vLabsFin.addElement( "Financeiro" );
		vLabsFin.addElement( "Não Finaceiro" );
		vLabsFin.addElement( "Ambos" );
		vValsFin.addElement( "S" );
		vValsFin.addElement( "N" );
		vValsFin.addElement( "A" );
		rgFinanceiro = new JRadioGroup( 3, 1, vLabsFin, vValsFin );
		rgFinanceiro.setVlrString( "S" );

		lcVend.add( new GuardaCampo( txtCodVend, "CodVend", "Cód.comiss.", ListaCampos.DB_PK, false ) );
		lcVend.add( new GuardaCampo( txtDescVend, "NomeVend", "Nome do comissionado", ListaCampos.DB_SI, false ) );
		lcVend.montaSql( false, "VENDEDOR", "VD" );
		lcVend.setQueryCommit( false );
		lcVend.setReadOnly( true );
		txtCodVend.setNomeCampo( "CodVend" );
		txtCodVend.setFK( true );
		txtCodVend.setTabelaExterna( lcVend );

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
		adic( rgFaturados, 7, 120, 120, 70 );
		adic( rgFinanceiro, 153, 120, 120, 70 );
		adic( cbVendaCanc, 7, 200, 200, 20 );
	}

	public void setConexao( Connection cn ) {

		super.setConexao( cn );
		lcVend.setConexao( con );
	}

	public void imprimir( boolean bVisualizar ) {

		if ( txtDatafim.getVlrDate().before( txtDataini.getVlrDate() ) ) {
			Funcoes.mensagemInforma( this, "Data final maior que a data inicial!" );
			return;
		}

		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sSQL = new StringBuffer();
		String sWhere = "";
		String sWhere1 = "";
		String sWhere2 = "";
		String sWhere3 = "";
		String sCab = "";
		String sDatasaidavenda = null;
		BigDecimal bTotalVal = null;
		BigDecimal bTotalDesc = null;
		BigDecimal bTotalLiq = null;
		ImprimeOS imp = null;
		int linPag = 0;

		if ( txtCodVend.getText().trim().length() > 0 ) {
			sWhere += " AND V.CODVEND = " + txtCodVend.getText().trim();
			sCab = "REPR.: " + txtCodVend.getVlrString() + " - " + txtDescVend.getText().trim();
			sWhere += " AND V.CODEMPVD=" + Aplicativo.iCodEmp + " AND V.CODFILIALVD=" + lcVend.getCodFilial();
		}

		if ( rgFaturados.getVlrString().equals( "S" ) ) {
			sWhere1 = " AND TM.FISCALTIPOMOV='S' ";
			sCab = "SO FATURADO";
		}
		else if ( rgFaturados.getVlrString().equals( "N" ) ) {
			sWhere1 = " AND TM.FISCALTIPOMOV='N' ";
			sCab = "NAO FATURADO";
		}
		else if ( rgFaturados.getVlrString().equals( "A" ) ) {
			sWhere1 = " AND TM.FISCALTIPOMOV IN ('S','N') ";
		}

		if ( rgFinanceiro.getVlrString().equals( "S" ) ) {
			sWhere2 = " AND TM.SOMAVDTIPOMOV='S' ";
			sCab += sCab.length() > 0 ? " - SO FINANCEIRO" : "SO FINANCEIRO";
		}
		else if ( rgFinanceiro.getVlrString().equals( "N" ) ) {
			sWhere2 = " AND TM.SOMAVDTIPOMOV='N' ";
			sCab += sCab.length() > 0 ? " - NAO FINANCEIRO" : "NAO FINANCEIRO";
		}
		else if ( rgFinanceiro.getVlrString().equals( "A" ) ) {
			sWhere2 = " AND TM.SOMAVDTIPOMOV IN ('S','N') ";
		}

		if ( cbVendaCanc.getVlrString().equals( "N" ) )
			sWhere3 = " AND NOT SUBSTR(V.STATUSVENDA,1,1)='C' ";

		try {

			imp = new ImprimeOS( "", con );
			linPag = imp.verifLinPag() - 1;
			imp.verifLinPag();
			imp.montaCab();
			imp.setTitulo( "Relatório de Vendas Geral" );
			imp.addSubTitulo( "RELATORIO DE VENDAS GERAL   -   PERIODO DE :" + txtDataini.getVlrString() + " Até: " + txtDatafim.getVlrString() );
			imp.limpaPags();
			if ( sCab.length() > 0 ) {
				imp.addSubTitulo( sCab );
			}

			bTotalVal = new BigDecimal( "0" );
			bTotalDesc = new BigDecimal( "0" );
			bTotalLiq = new BigDecimal( "0" );

			sSQL.append( "SELECT V.DTSAIDAVENDA,V.CODVENDA,V.SERIE,V.STATUSVENDA,V.DOCVENDA," );
			sSQL.append( "V.DTEMITVENDA,V.VLRPRODVENDA,V.VLRLIQVENDA,V.CODPLANOPAG,P.DESCPLANOPAG," );
			sSQL.append( "V.VLRCOMISVENDA,V.VLRDESCVENDA,V.VLRDESCITVENDA,V.CODCLI,C.RAZCLI " );
			sSQL.append( "FROM VDVENDA V,VDCLIENTE C,FNPLANOPAG P, EQTIPOMOV TM " );
			sSQL.append( "WHERE V.CODEMP=? AND V.CODFILIAL=? " );
			sSQL.append( "AND C.CODEMP=V.CODEMPCL AND C.CODFILIAL=V.CODFILIALCL AND C.CODCLI=V.CODCLI " );
			sSQL.append( "AND P.CODEMP=V.CODEMPPG AND P.CODFILIAL=V.CODFILIALPG AND P.CODPLANOPAG=V.CODPLANOPAG " );
			sSQL.append( "AND TM.CODEMP=V.CODEMPTM AND TM.CODFILIAL=V.CODFILIALTM AND TM.CODTIPOMOV=V.CODTIPOMOV " );
			sSQL.append( "AND TM.TIPOMOV IN ('VD','PV','VT','SE') " );
			sSQL.append( sWhere );
			sSQL.append( sWhere1 );
			sSQL.append( sWhere2 );
			sSQL.append( sWhere3 );
			sSQL.append( "AND V.DTSAIDAVENDA>=? AND V.DTSAIDAVENDA<=? AND V.FLAG IN " + Aplicativo.carregaFiltro( con, org.freedom.telas.Aplicativo.iCodEmp ) );
			sSQL.append( "ORDER BY V.DTSAIDAVENDA,V.DOCVENDA " );

			ps = con.prepareStatement( sSQL.toString() );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "VDVENDA" ) );
			ps.setDate( 3, Funcoes.dateToSQLDate( txtDataini.getVlrDate() ) );
			ps.setDate( 4, Funcoes.dateToSQLDate( txtDatafim.getVlrDate() ) );
			rs = ps.executeQuery();
			while ( rs.next() ) {
				if ( imp.pRow() >= ( linPag - 1 ) ) {
					imp.say( imp.pRow() + 1, 0, imp.comprimido() );
					imp.say( imp.pRow(), 0, "+" + Funcoes.replicate( "-", 133 ) + "+" );
					imp.incPags();
					imp.eject();
				}
				if ( imp.pRow() == 0 ) {
					imp.impCab( 136, true );
					imp.say( imp.pRow(), 0, imp.comprimido() );
					imp.say( imp.pRow(), 0, "|" + Funcoes.replicate( "-", 133 ) + "|" );
					imp.say( imp.pRow() + 1, 0, imp.comprimido() );
					imp.say( imp.pRow(), 0, "|Dt.Saida" );
					imp.say( imp.pRow(), 12, "| NF./Ped." );
					imp.say( imp.pRow(), 27, "| Cod.Cli." );
					imp.say( imp.pRow(), 36, "| Cliente" );
					imp.say( imp.pRow(), 80, "|   Valor" );
					imp.say( imp.pRow(), 93, "| Desconto" );
					imp.say( imp.pRow(), 106, "|  Liquido" );
					imp.say( imp.pRow(), 119, "| F.Pagto." );
					imp.say( imp.pRow(), 135, "|" );
					imp.say( imp.pRow() + 1, 0, imp.comprimido() );
					imp.say( imp.pRow(), 0, "|" + Funcoes.replicate( "-", 133 ) + "|" );
				}

				imp.say( imp.pRow() + 1, 0, imp.comprimido() );
				if ( !rs.getString( "dtsaidavenda" ).equals( sDatasaidavenda ) )
					imp.say( imp.pRow(), 0, "|" + Funcoes.sqlDateToStrDate( rs.getDate( "dtsaidavenda" ) ) );
				else imp.say( imp.pRow(), 0, "|" );

				if ( Funcoes.copy( rs.getString( "statusvenda" ), 0, 1 ) == "P" )
					imp.say( imp.pRow(), 12, "|" + Funcoes.copy( rs.getString( "codvenda" ), 0, 13 ) );
				else {
					imp.say( imp.pRow(), 12, "| " + Funcoes.copy( rs.getString( "docvenda" ), 0, 13 ) );
					imp.say( imp.pRow(), 26, "| " + Funcoes.copy( rs.getString( "CodCli" ), 0, 8 ) );
					imp.say( imp.pRow(), 36, "| " + Funcoes.copy( rs.getString( "razcli" ), 0, 40 ) );
					imp.say( imp.pRow(), 80, "| " + Funcoes.strDecimalToStrCurrency( 10, 2, rs.getString( "vlrprodvenda" ) ) );
					imp.say( imp.pRow(), 93, "| " + ( rs.getDouble( "VlrDescVenda" ) > 0 ? Funcoes.strDecimalToStrCurrency( 10, 2, rs.getString( "vlrdescvenda" ) ) : Funcoes.strDecimalToStrCurrency( 10, 2, rs.getString( "vlrdescitvenda" ) ) ) );
					imp.say( imp.pRow(), 106, "| " + Funcoes.strDecimalToStrCurrency( 10, 2, rs.getString( "vlrliqvenda" ) ) );
					imp.say( imp.pRow(), 119, "| " + Funcoes.copy( rs.getString( "descplanopag" ), 0, 13 ) );
					imp.say( imp.pRow(), 135, "|" );
				}

				if ( rs.getString( "VlrProdVenda" ) != null )
					bTotalVal = bTotalVal.add( new BigDecimal( rs.getString( "VlrProdVenda" ) ) );
				if ( rs.getString( "VlrDescvenda" ) != null )
					bTotalDesc = bTotalDesc.add( new BigDecimal( rs.getDouble( "VlrDescVenda" ) > 0 ? rs.getDouble( "VlrDescVenda" ) : rs.getDouble( "VlrDescItVenda" ) ) );
				if ( rs.getString( "VlrLiqVenda" ) != null )
					bTotalLiq = bTotalLiq.add( new BigDecimal( rs.getString( "VlrLiqVenda" ) ) );

				sDatasaidavenda = rs.getString( "dtsaidavenda" );
			}

			imp.say( imp.pRow() + 1, 0, imp.comprimido() );
			imp.say( imp.pRow(), 0, "+" + Funcoes.replicate( "=", 133 ) + "+" );
			imp.say( imp.pRow() + 1, 0, imp.comprimido() );
			imp.say( imp.pRow(), 0, "|" );
			imp.say( imp.pRow(), 64, " Total Geral    | " + Funcoes.strDecimalToStrCurrency( 10, 2, "" + bTotalVal ) + " |" + Funcoes.strDecimalToStrCurrency( 10, 2, "" + bTotalDesc ) + " |" + Funcoes.strDecimalToStrCurrency( 11, 2, "" + bTotalLiq ) + "  |" );
			imp.say( imp.pRow(), 135, "|" );
			imp.say( imp.pRow() + 1, 0, imp.comprimido() );
			imp.say( imp.pRow(), 0, "+" + Funcoes.replicate( "=", 133 ) + "+" );

			imp.eject();
			imp.fechaGravacao();

			if ( !con.getAutoCommit() )
				con.commit();
		} catch ( SQLException err ) {
			Funcoes.mensagemErro( this, "Erro consulta tabela de preços!\n" + err.getMessage(), true, con, err );
		} finally {
			ps = null;
			rs = null;
			sWhere = null;
			sWhere1 = null;
			sWhere2 = null;
			sWhere3 = null;
			sCab = null;
			sDatasaidavenda = null;
			bTotalVal = null;
			bTotalDesc = null;
			bTotalLiq = null;
			System.gc();
		}

		if ( bVisualizar )
			imp.preview( this );
		else imp.print();
	}
}
