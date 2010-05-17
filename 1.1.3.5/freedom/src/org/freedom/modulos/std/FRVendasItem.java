/**
 * @version 02/11/2003 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.std <BR>
 * Classe:
 * @(#)FRVendasItem.java <BR>
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
import java.util.HashMap;
import java.util.Vector;

import javax.swing.BorderFactory;

import net.sf.jasperreports.engine.JasperPrintManager;

import org.freedom.componentes.JLabelPad;

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

public class FRVendasItem extends FRelatorio {

	private static final long serialVersionUID = 1L;

	private JTextFieldPad txtDataini = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtDatafim = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtCodVend = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 10, 0 );

	private JTextFieldFK txtDescVend = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private JTextFieldPad txtCodCli = new JTextFieldPad( JTextFieldPad.TP_STRING, 14, 0 );

	private JTextFieldFK txtRazCli = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private JTextFieldPad txtCodGrup = new JTextFieldPad( JTextFieldPad.TP_STRING, 14, 0 );

	private JTextFieldFK txtDescGrup = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private JTextFieldPad txtCodMarca = new JTextFieldPad( JTextFieldPad.TP_STRING, 6, 0 );

	private JTextFieldFK txtDescMarca = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private JTextFieldPad txtSiglaMarca = new JTextFieldPad( JTextFieldPad.TP_STRING, 20, 0 );

	private JCheckBoxPad cbListaFilial = new JCheckBoxPad( "Listar vendas das filiais ?", "S", "N" );

	private JCheckBoxPad cbVendaCanc = new JCheckBoxPad( "Mostrar Canceladas", "S", "N" );

	private JRadioGroup rgTipo = null;

	private JRadioGroup rgOrdem = null;

	private JRadioGroup rgFaturados = null;

	private JRadioGroup rgFinanceiro = null;

	private ListaCampos lcVend = new ListaCampos( this );

	private ListaCampos lcGrup = new ListaCampos( this );

	private ListaCampos lcCliente = new ListaCampos( this );

	private ListaCampos lcMarca = new ListaCampos( this );

	private boolean comref = false;

	public FRVendasItem() {

		setTitulo( "Vendas por Item" );
		setAtribos( 80, 80, 590, 350 );

		txtDescVend.setAtivo( false );
		txtDescGrup.setAtivo( false );
		txtDescMarca.setAtivo( false );
		txtRazCli.setAtivo( false );

		Vector vLabs = new Vector();
		Vector vVals = new Vector();

		vLabs.addElement( "Grafico" );
		vLabs.addElement( "Texto" );
		vVals.addElement( "G" );
		vVals.addElement( "T" );
		rgTipo = new JRadioGroup( 1, 2, vLabs, vVals );
		rgTipo.setVlrString( "T" );
		
		Vector vLabs1 = new Vector();
		Vector vVals1 = new Vector();

		vLabs1.addElement( "Faturado" );
		vLabs1.addElement( "Não Faturado" );
		vLabs1.addElement( "Ambos" );
		vVals1.addElement( "S" );
		vVals1.addElement( "N" );
		vVals1.addElement( "A" );
		rgFaturados = new JRadioGroup( 3, 1, vLabs1, vVals1 );
		rgFaturados.setVlrString( "S" );
		
		Vector vLabs2 = new Vector();
		Vector vVals2 = new Vector();

		vLabs2.addElement( "Financeiro" );
		vLabs2.addElement( "Não Finaceiro" );
		vLabs2.addElement( "Ambos" );
		vVals2.addElement( "S" );
		vVals2.addElement( "N" );
		vVals2.addElement( "A" );
		rgFinanceiro = new JRadioGroup( 3, 1, vLabs2, vVals2 );
		rgFinanceiro.setVlrString( "S" );
		
		Vector vLabs3 = new Vector();
		Vector vVals3 = new Vector();
		
		vLabs3.addElement( "Código" );
		vLabs3.addElement( "Descrição" );
		vVals3.addElement( "C" );
		vVals3.addElement( "D" );
		rgOrdem = new JRadioGroup( 1, 2, vLabs3, vVals3 );
		rgOrdem.setVlrString( "D" );
		
		lcGrup.add( new GuardaCampo( txtCodGrup, "CodGrup", "Cód.grupo", ListaCampos.DB_PK, false ) );
		lcGrup.add( new GuardaCampo( txtDescGrup, "DescGrup", "Descrição do grupo", ListaCampos.DB_SI, false ) );
		txtCodGrup.setTabelaExterna( lcGrup );
		txtCodGrup.setNomeCampo( "CodGrup" );
		txtCodGrup.setFK( true );
		lcGrup.setReadOnly( true );
		lcGrup.montaSql( false, "GRUPO", "EQ" );

		lcMarca.add( new GuardaCampo( txtCodMarca, "CodMarca", "Cód.marca", ListaCampos.DB_PK, false ) );
		lcMarca.add( new GuardaCampo( txtDescMarca, "DescMarca", "Descrição da marca", ListaCampos.DB_SI, false ) );
		lcMarca.add( new GuardaCampo( txtSiglaMarca, "SiglaMarca", "Sigla", ListaCampos.DB_SI, false ) );
		txtCodMarca.setTabelaExterna( lcMarca );
		txtCodMarca.setNomeCampo( "CodMarca" );
		txtCodMarca.setFK( true );
		lcMarca.setReadOnly( true );
		lcMarca.montaSql( false, "MARCA", "EQ" );

		lcVend.add( new GuardaCampo( txtCodVend, "CodVend", "Cód.comiss.", ListaCampos.DB_PK, false ) );
		lcVend.add( new GuardaCampo( txtDescVend, "NomeVend", "Nome do comissionado", ListaCampos.DB_SI, false ) );
		txtCodVend.setTabelaExterna( lcVend );
		txtCodVend.setNomeCampo( "CodVend" );
		txtCodVend.setFK( true );
		lcVend.setReadOnly( true );
		lcVend.montaSql( false, "VENDEDOR", "VD" );

		JLabelPad lbLinha = new JLabelPad();
		lbLinha.setBorder( BorderFactory.createEtchedBorder() );
		txtDataini.setVlrDate( new Date() );
		txtDatafim.setVlrDate( new Date() );

		lcCliente.add( new GuardaCampo( txtCodCli, "CodCli", "Cód.cli.", ListaCampos.DB_PK, false ) );
		lcCliente.add( new GuardaCampo( txtRazCli, "RazCli", "Razão social do cliente", ListaCampos.DB_SI, false ) );
		txtCodCli.setTabelaExterna( lcCliente );
		txtCodCli.setNomeCampo( "CodCli" );
		txtCodCli.setFK( true );
		lcCliente.setReadOnly( true );
		lcCliente.montaSql( false, "CLIENTE", "VD" );

		adic( new JLabelPad( "Periodo:" ), 7, 5, 100, 20 );
		adic( lbLinha, 7, 25, 273, 55 );
		adic( new JLabelPad( "De:" ), 15, 40, 30, 20 );
		adic( txtDataini, 45, 40, 90, 20 );
		adic( new JLabelPad( "Até:" ), 145, 40, 30, 20 );
		adic( txtDatafim, 180, 40, 90, 20 );		
		adic( new JLabelPad( "Ordenado por:" ), 295, 5, 180, 20 );
		adic( rgOrdem, 295, 25, 273, 30 );
		adic( rgTipo, 295, 65, 273, 30 );		
		adic( new JLabelPad( "Cód.comiss." ), 7, 100, 200, 20 );
		adic( txtCodVend, 7, 120, 70, 20 );
		adic( new JLabelPad( "Nome do comissionado" ), 80, 100, 200, 20 );
		adic( txtDescVend, 80, 120, 200, 20 );		
		adic( new JLabelPad( "Cód.grupo" ), 295, 100, 200, 20 );
		adic( txtCodGrup, 295, 120, 70, 20 );
		adic( new JLabelPad( "Descrição do grupo" ), 368, 100, 200, 20 );
		adic( txtDescGrup, 368, 120, 200, 20 );		
		adic( new JLabelPad( "Cód.cli." ), 7, 140, 200, 20 );
		adic( txtCodCli, 7, 160, 70, 20 );
		adic( new JLabelPad( "Razão social do cliente" ), 80, 140, 200, 20 );
		adic( txtRazCli, 80, 160, 200, 20 );		
		adic( new JLabelPad( "Cód.marca" ), 295, 140, 200, 20 );
		adic( txtCodMarca, 295, 160, 70, 20 );
		adic( new JLabelPad( "Descrição da marca" ), 368, 140, 200, 20 );
		adic( txtDescMarca, 368, 160, 200, 20 );			
		adic( rgFaturados, 7, 200, 125, 70 );
		adic( rgFinanceiro, 157, 200, 125, 70 );
		adic( cbListaFilial, 295, 205, 250, 20 );
		adic( cbVendaCanc, 295, 235, 200, 20 );

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
		StringBuffer sCab2 = new StringBuffer();
		String sWhere = "";
		String sWhere1 = null;
		String sWhere2 = null;
		String sWhere3 = "";
		String sOrdem = rgOrdem.getVlrString();
		String sOrdenado = "";
		int paran = 1;
		boolean listaFilial = false;

		try {

			if ( txtCodVend.getText().trim().length() > 0 ) {
				sWhere += " AND V.CODVEND=" + txtCodVend.getText().trim();
				sCab.append( "REPR.: " + txtDescVend.getText().trim() );
			}
			if ( txtCodGrup.getText().trim().length() > 0 ) {
				sWhere += " AND P.CODGRUP LIKE '" + txtCodGrup.getText().trim() + "%'";
				if ( sCab.length() > 0 ) {
					sCab.append( " - " );
				}
				sCab.append( "GRUPO: " + txtDescGrup.getText().trim() );
			}
			if ( txtCodMarca.getText().trim().length() > 0 ) {
				sWhere += " AND P.CODMARCA='" + txtCodMarca.getText().trim() + "'";
				if ( sCab.length() > 0 ) {
					sCab.append( " - " );
				}
				sCab.append( "MARCA: " + txtDescMarca.getText().trim() );
			}
			if ( txtCodCli.getText().trim().length() > 0 ) {
				if ( cbListaFilial.getVlrString().equals( "S" ) ) {
					sWhere += " AND (C.CODPESQ=" + txtCodCli.getText().trim() + " OR C.CODCLI=" + txtCodCli.getText().trim() + ")";
				}
				else {
					sWhere += " AND V.CODCLI=" + txtCodCli.getText().trim();
				}
				if ( sCab.length() > 0 ) {
					sCab.append( " - " );
				}
				sCab.append( "CLIENTE: " + txtRazCli.getText().trim() );
			}
	
			if ( rgFaturados.getVlrString().equals( "S" ) ) {
				sWhere1 = " AND TM.FISCALTIPOMOV='S' ";
				sCab2.append( "FATURADO" );
			}
			else if ( rgFaturados.getVlrString().equals( "N" ) ) {
				sWhere1 = " AND TM.FISCALTIPOMOV='N' ";
				if ( sCab2.length() > 0 ) {
					sCab2.append( " - " );
				}
				sCab2.append( "NAO FATURADO" );
			}
			else if ( rgFaturados.getVlrString().equals( "A" ) ) {
				sWhere1 = " AND TM.FISCALTIPOMOV IN ('S','N') ";
			}
	
			if ( rgFinanceiro.getVlrString().equals( "S" ) ) {
				sWhere2 = " AND TM.SOMAVDTIPOMOV='S' ";
				if ( sCab2.length() > 0 ) {
					sCab2.append( " - " );
				}
				sCab2.append( "FINANCEIRO" );
			}
			else if ( rgFinanceiro.getVlrString().equals( "N" ) ) {
				sWhere2 = " AND TM.SOMAVDTIPOMOV='N' ";
				if ( sCab2.length() > 0 ) {
					sCab2.append( " - " );
				}
				sCab2.append( "NAO FINANCEIRO" );
			}
			else if ( rgFinanceiro.getVlrString().equals( "A" ) ) {
				sWhere2 = " AND TM.SOMAVDTIPOMOV IN ('S','N') ";
			}
	
			if ( cbVendaCanc.getVlrString().equals( "N" ) ) {
				sWhere3 = " AND NOT SUBSTR(V.STATUSVENDA,1,1)='C' ";
			}
			
			if ( sOrdem.equals( "C" ) ) {
				sOrdem = comref ? "P.REFPROD" : "P.CODPROD";
				sOrdenado = "\nORDENADO POR " + ( comref ? "REFERENCIA" : "CODIGO" );
			}
			else {
				sOrdem = "P.DESCPROD";
				sOrdenado = "\nORDENADO POR DESCRICAO";
			}
	
			sCab2.append( sOrdenado );
	
			if ( cbListaFilial.getVlrString().equals( "S" ) && ( txtCodCli.getText().trim().length() > 0 ) ) {
				sSQL.append( "SELECT P.CODPROD,P.REFPROD,P.DESCPROD,P.CODUNID,SUM(IT.QTDITVENDA) AS QTDITVENDA,SUM(IT.VLRLIQITVENDA) AS VLRLIQITVENDA " );
				sSQL.append( "FROM VDVENDA V,EQTIPOMOV TM, VDCLIENTE C, VDITVENDA IT, EQPRODUTO P " );
				sSQL.append( "WHERE P.CODPROD=IT.CODPROD AND IT.CODVENDA=V.CODVENDA " );
				sSQL.append( sWhere );
				sSQL.append( sWhere1 );
				sSQL.append( sWhere2 );
				sSQL.append( sWhere3 );
				sSQL.append( "AND TM.CODEMP=V.CODEMPTM AND TM.CODFILIAL=V.CODFILIALTM AND TM.CODTIPOMOV=V.CODTIPOMOV " ); 
				sSQL.append( "AND TM.TIPOMOV IN ('VD','VE','PV','VT','SE') " );
				sSQL.append( "AND V.CODCLI=C.CODCLI AND V.CODEMPCL=C.CODEMP AND V.CODFILIALCL=C.CODFILIAL " ); 
				sSQL.append( "AND V.DTEMITVENDA BETWEEN ? AND ? " );
				sSQL.append( "AND V.FLAG IN " );
				sSQL.append( AplicativoPD.carregaFiltro( con, org.freedom.telas.Aplicativo.iCodEmp ) ); 
				sSQL.append( " GROUP BY " );
				sSQL.append( ( comref ? "P.REFPROD,P.CODPROD," : "P.CODPROD,P.REFPROD," ) ); 
				sSQL.append( "P.DESCPROD,P.CODUNID " );
				sSQL.append( "ORDER BY " + sOrdem );
				listaFilial = true;
			}
			else {
				sSQL.append( "SELECT P.CODPROD,P.REFPROD,P.DESCPROD,P.CODUNID,SUM(IT.QTDITVENDA) AS QTDITVENDA,SUM(IT.VLRLIQITVENDA) AS VLRLIQITVENDA " );
				sSQL.append( "FROM VDVENDA V,EQTIPOMOV TM,VDITVENDA IT, EQPRODUTO P " );
				sSQL.append( "WHERE P.CODEMP=? AND P.CODFILIAL=? " );
				sSQL.append( "AND IT.CODEMPPD=P.CODEMP AND IT.CODFILIALPD=P.CODFILIAL AND IT.CODPROD=P.CODPROD " );
				sSQL.append( "AND V.CODEMP=IT.CODEMP AND V.CODFILIAL=IT.CODFILIAL AND V.CODVENDA=IT.CODVENDA " );
				sSQL.append( "AND V.DTEMITVENDA BETWEEN ? AND ? AND V.FLAG IN ('S','N') " );
				sSQL.append( sWhere );
				sSQL.append( sWhere1 );
				sSQL.append( sWhere2 );
				sSQL.append( sWhere3 );
				sSQL.append( "AND TM.CODEMP=V.CODEMPTM AND TM.CODFILIAL=V.CODFILIALTM AND TM.CODTIPOMOV=V.CODTIPOMOV " );
				sSQL.append( "AND TM.TIPOMOV IN ('VD','VE','PV','VT','SE') " );
				sSQL.append( "GROUP BY " );
				sSQL.append( ( comref ? "P.REFPROD,P.CODPROD," : "P.CODPROD,P.REFPROD," ) ); 
				sSQL.append( "P.DESCPROD,P.CODUNID " );
				sSQL.append( "ORDER BY " + sOrdem );
			}

			ps = con.prepareStatement( sSQL.toString() );
			if ( !listaFilial ) {
				ps.setInt( paran++, Aplicativo.iCodEmp );
				ps.setInt( paran++, ListaCampos.getMasterFilial( "EQPRODUTO" ) );
			}
			ps.setDate( paran++, Funcoes.dateToSQLDate( txtDataini.getVlrDate() ) );
			ps.setDate( paran++, Funcoes.dateToSQLDate( txtDatafim.getVlrDate() ) );
			rs = ps.executeQuery();
			
			if ( "T".equals( rgTipo.getVlrString() ) ) {
				imprimirTexto( bVisualizar, rs, Funcoes.strToVectorSilabas( sCab.toString() + "\n" + sCab2.toString(), 130 ), comref );
			}
			else if ( "G".equals( rgTipo.getVlrString() ) ) {
				imprimirGrafico( bVisualizar, rs, sCab.toString() + "\n" + sCab2.toString(), comref );
			}
			
			rs.close();
			ps.close();
			
			if ( !con.getAutoCommit() ) {
				con.commit();
			}
		}catch ( Exception err ) {
			err.printStackTrace();
			Funcoes.mensagemErro( this, "Erro ao montar relatorio!\n" + err.getMessage(), true, con, err );
		} finally {
			System.gc();
		}
	}

	public void imprimirTexto( final boolean bVisualizar, final ResultSet rs, final Vector cab, final boolean bComRef ) {

		String sLinhaFina = Funcoes.replicate( "-", 133 );
		String sLinhaLarga = Funcoes.replicate( "=", 133 );
		ImprimeOS imp = new ImprimeOS( "", con );
		int linPag = imp.verifLinPag() - 1;
		BigDecimal bdQtd = new BigDecimal("0");
		BigDecimal bdVlr = new BigDecimal("0");
		
		try {

			imp.limpaPags();
			imp.montaCab();
			imp.setTitulo( "Relatório de Vendas por ítem" );
			imp.addSubTitulo( "RELATORIO DE VENDAS POR ITEM  -  PERIODO DE :" + txtDataini.getVlrString() + " Até: " + txtDatafim.getVlrString() );
			
			for ( int i=0; i < cab.size(); i++ ) {
				imp.addSubTitulo( (String) cab.elementAt( i ) );
			}
			
			while ( rs.next() ) {
				if ( imp.pRow() == linPag ) {
					imp.pulaLinha( 1, imp.comprimido() );
					imp.say( 0, "+" + sLinhaFina + "+" );
					imp.eject();
					imp.incPags();
				}
				if ( imp.pRow() == 0 ) {
					imp.impCab( 136, true );
					imp.pulaLinha( 0, imp.comprimido() );
					imp.say( 0, "|" );
					imp.say( 135, "|" );
					imp.pulaLinha( 1, imp.comprimido() );
					imp.say( 0, "|" + sLinhaFina + "|" );
					imp.pulaLinha( 1, imp.comprimido() );
					imp.say( 1, "| Cod.prod." );
					imp.say( 14, "| Desc.produto" );
					imp.say( 68, "| Unid. " );
					imp.say( 76, "|   Quantidade " );
					imp.say( 99, "|    Vlr.tot.item. " );
					imp.say( 135, "|" );
					imp.pulaLinha( 1, imp.comprimido() );
					imp.say( 0, "|" + sLinhaFina + "|" );
				}

				imp.pulaLinha( 1, imp.comprimido() );
				imp.say( 0, "|" );
				imp.say( 3, Funcoes.copy( rs.getString( 1 ), 0, 10 ) + " | " );
				imp.say( 17, Funcoes.copy( rs.getString( 3 ), 0, 50 ) + " | " );
				imp.say( 70, Funcoes.copy( rs.getString( 4 ), 0, 5 ) + " | " );
				imp.say( 86, Funcoes.strDecimalToStrCurrency( 10, 1, rs.getString( 5 ) ) );
				imp.say( 99, "|" );
				imp.say( 100, Funcoes.strDecimalToStrCurrency( 20, 2, rs.getString( 6 ) ) );
				imp.say( 135, "|" );
				
				bdQtd = bdQtd.add( rs.getBigDecimal( 5 ) );
				bdVlr = bdVlr.add( rs.getBigDecimal( 6 ) );
			}

			imp.pulaLinha( 1, imp.comprimido() );
			imp.say( 0, "|" + sLinhaLarga + "|" );
			imp.pulaLinha( 1, imp.comprimido() );
			imp.say( 0, "|" );
			imp.say( 30, "Quant. vendida -> " );
			imp.say( 50, Funcoes.copy( String.valueOf( bdQtd ), 6 ) );
			imp.say( 60, "Valor vendido -> " );
			imp.say( 78, Funcoes.strDecimalToStrCurrency( 15, 2, String.valueOf( bdVlr ) ) );
			imp.say( 135, "|" );
			imp.pulaLinha( 1, imp.comprimido() );
			imp.say( 0, "+" + sLinhaLarga + "+" );

			imp.eject();
			imp.fechaGravacao();

			if ( bVisualizar ) {
				imp.preview( this );
			}
			else {
				imp.print();
			}
			
		}catch ( Exception err ) {
			err.printStackTrace();
			Funcoes.mensagemErro( this, "Erro ao montar relatorio!\n" + err.getMessage(), true, con, err );
		}
	}

	public void imprimirGrafico( final boolean bVisualizar, final ResultSet rs, final String sCab, final boolean bComRef ) {

		HashMap hParam = new HashMap();
		hParam.put( "COMREF", bComRef ? "S" : "N" );
		
		FPrinterJob dlGr = new FPrinterJob( "relatorios/VendasItem.jasper", "Vendas por Item", sCab, rs, hParam, this );

		if ( bVisualizar ) {
			dlGr.setVisible( true );
		}
		else {
			try {
				JasperPrintManager.printReport( dlGr.getRelatorio(), true );
			} catch ( Exception err ) {
				Funcoes.mensagemErro( this, "Erro na impressão de relatório de vendas detalhadas!" + err.getMessage(), true, con, err );
			}
		}
	}
	
	private boolean comRef() {

		boolean bRetorno = false;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = con.prepareStatement( "SELECT USAREFPROD FROM SGPREFERE1 WHERE CODEMP=? AND CODFILIAL=?" );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "SGPREFERE1" ) );
			rs = ps.executeQuery();
			
			if ( rs.next() ) {
				bRetorno = "S".equals( rs.getString( "UsaRefProd" ) );
			}
			
			rs.close();
			ps.close();
			if ( !con.getAutoCommit() ) {
				con.commit();
			}
		} catch ( SQLException err ) {
			Funcoes.mensagemErro( this, "Erro ao carregar a tabela PREFERE1!\n" + err.getMessage(), true, con, err );
		}
		return bRetorno;
	}

	public void setConexao( Connection cn ) {

		super.setConexao( cn );
		lcVend.setConexao( cn );
		lcGrup.setConexao( cn );
		lcMarca.setConexao( cn );
		lcCliente.setConexao( cn );

		comref = comRef();
	}
}
