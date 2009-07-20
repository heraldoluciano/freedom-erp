/**
 * @version 19/06/2004 <BR>
 * @author Setpoint Informática Ltda./Robson Sanchez <BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.std <BR>
 * Classe:
 * @(#)FRConfEstoq.java <BR>
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

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import org.freedom.componentes.JLabelPad;

import org.freedom.componentes.ImprimeOS;
import org.freedom.componentes.JCheckBoxPad;
import org.freedom.componentes.JRadioGroup;
import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FRelatorio;

public class FRConfEstoq extends FRelatorio {

	private static final long serialVersionUID = 1L;

	private Vector<String> vLabTipoRel = new Vector<String>();

	private Vector<String> vValTipoRel = new Vector<String>();

	private JRadioGroup<?, ?> rgTipoRel = null;

	private JCheckBoxPad cbTipoMovEstoq = null;

	private JCheckBoxPad cbAtivo = null;

	public FRConfEstoq() {

		setTitulo( "Relatório de Conferência de Estoque" );
		setAtribos( 80, 30, 350, 250 );

		vLabTipoRel.addElement( "Saldos de lotes" );
		vLabTipoRel.addElement( "Saldos de produtos" );
		vValTipoRel.addElement( "L" );
		vValTipoRel.addElement( "S" );

		rgTipoRel = new JRadioGroup<String, String>( 1, 3, vLabTipoRel, vValTipoRel );

		cbTipoMovEstoq = new JCheckBoxPad( "Apenas tipos de movimento c/ contr. de estoq.", "S", "N" );
		cbTipoMovEstoq.setVlrString( "S" );

		cbAtivo = new JCheckBoxPad( "Somente ativos.", "S", "N" );
		cbAtivo.setVlrString( "N" );

		adic( new JLabelPad( "Conferência" ), 7, 0, 250, 20 );
		adic( rgTipoRel, 7, 20, 300, 30 );
		adic( cbTipoMovEstoq, 7, 50, 300, 30 );
		adic( cbAtivo, 7, 80, 300, 30 );

	}

	public void imprimir( boolean bVisualizar ) {

		String sTipoMovEst = cbTipoMovEstoq.getVlrString();
		String sWhere = "";

		try {

			if ( sTipoMovEst.equals( "S" ) ) {
				sWhere = " AND TM.ESTOQTIPOMOV='S' ";
			}
			if ( rgTipoRel.getVlrString().equals( "L" ) ) {
				impLote( bVisualizar, sWhere );
			}

			else if ( rgTipoRel.getVlrString().equals( "S" ) ) {
				impProduto( bVisualizar, sWhere );
			}
		} finally {

			sTipoMovEst = null;
			sWhere = null;
		}
	}

	private void impProduto( boolean bVisualizar, String sWhere ) {

		String sSql = "";
		ImprimeOS imp = null;
		int linPag = 0;
		PreparedStatement ps = null;
		ResultSet rs = null;
		BigDecimal beSldCalc = new BigDecimal( 0 );
		BigDecimal beQtdDif = new BigDecimal( 0 );

		try {

			imp = new ImprimeOS( "", con );
			linPag = imp.verifLinPag() - 1;
			imp.setTitulo( "Relatorio de Conferência de Estoque por Produto" );

			sSql = "SELECT DESCPROD, CODPROD, REFPROD, SLDLIQPROD, QTDINVP, QTDITCOMPRA, " + "QTDFINALPRODOP, QTDEXPITRMA, QTDITVENDA, SLDMOVPROD, SLDLIQPRODAX " + "FROM EQCONFESTOQVW01 " + "WHERE CODEMP=? AND CODFILIAL=? AND " + ( cbAtivo.getVlrString().equals( "S" ) ? "ATIVOPROD='S' AND " : "" )
					+ "( ( ( QTDINVP + QTDITCOMPRA + QTDFINALPRODOP - QTDEXPITRMA - QTDITVENDA) <> SLDLIQPROD ) OR " + "( (QTDINVP + QTDITCOMPRA + QTDFINALPRODOP - QTDEXPITRMA - QTDITVENDA) <> SLDMOVPROD) OR "
					+ "( (QTDINVP + QTDITCOMPRA + QTDFINALPRODOP - QTDEXPITRMA - QTDITVENDA) <> SLDLIQPRODAX) OR " + "(SLDLIQPROD<>SLDMOVPROD) OR (SLDLIQPRODAX<>SLDMOVPROD) )";

			System.out.println( sSql );

			try {

				ps = con.prepareStatement( sSql );
				ps.setInt( 1, Aplicativo.iCodEmp );
				ps.setInt( 2, ListaCampos.getMasterFilial( "EQPRODUTO" ) );

				rs = ps.executeQuery();

				imp.limpaPags();

				while ( rs.next() ) {
					if ( imp.pRow() >= ( linPag - 1 ) ) {
						imp.say( imp.pRow() + 1, 0, "" + imp.comprimido() );
						imp.say( imp.pRow() + 0, 0, "+" + Funcoes.replicate( "-", 133 ) + "+" );
						imp.incPags();
						imp.eject();

					}
					if ( imp.pRow() == 0 ) {
						imp.montaCab();
						imp.setTitulo( "Relatorio de Comferencia de Estoque" );
						imp.addSubTitulo( "CONFERENCIA DE ESTOQUE CONSIDERANDO SALDOS POR PRODUTO" );
						imp.impCab( 136, true );

						imp.say( imp.pRow() + 0, 0, "" + imp.comprimido() );
						imp.say( imp.pRow() + 0, 1, "+" + Funcoes.replicate( "-", 133 ) + "+" );
						imp.say( imp.pRow() + 1, 0, "" + imp.comprimido() );
						imp.say( imp.pRow() + 0, 0, "| DESCRICAO DO PRODUTO" );
						imp.say( imp.pRow() + 0, 32, "| CODIGO" );
						imp.say( imp.pRow() + 0, 44, "| SALDO " );
						imp.say( imp.pRow() + 0, 54, "| QTD.IV." );
						imp.say( imp.pRow() + 0, 64, "| QTD.OP." );
						imp.say( imp.pRow() + 0, 74, "| QTD.CP." );
						imp.say( imp.pRow() + 0, 84, "| QTD.RM." );
						imp.say( imp.pRow() + 0, 94, "| QTD.VD." );
						imp.say( imp.pRow() + 0, 104, "| SLD.CA." );
						imp.say( imp.pRow() + 0, 114, "| SLD.MP." );
						imp.say( imp.pRow() + 0, 124, "| DIF.SD." );
						imp.say( imp.pRow() + 0, 135, "|" );
						imp.say( imp.pRow() + 1, 0, "" + imp.comprimido() );
						imp.say( imp.pRow() + 0, 1, "+" + Funcoes.replicate( "-", 133 ) + "+" );

					}

					beSldCalc = rs.getBigDecimal( "QTDINVP" ).add( rs.getBigDecimal( "QTDITCOMPRA" ) );
					beSldCalc = beSldCalc.add( rs.getBigDecimal( "QTDFINALPRODOP" ) );
					beSldCalc = beSldCalc.subtract( rs.getBigDecimal( "QTDEXPITRMA" ) );
					beSldCalc = beSldCalc.subtract( rs.getBigDecimal( "QTDITVENDA" ) );

					beQtdDif = beSldCalc.subtract( rs.getBigDecimal( "SLDLIQPROD" ) );

					if ( beQtdDif.doubleValue() == 0 ) {
						beQtdDif = rs.getBigDecimal( "SLDMOVPROD" ).subtract( rs.getBigDecimal( "SLDLIQPROD" ) );
					}

					imp.say( imp.pRow() + 1, 0, "" + imp.comprimido() );
					imp.say( imp.pRow() + 0, 0, "|" + Funcoes.adicionaEspacos( rs.getString( "DESCPROD" ), 30 ) );
					imp.say( imp.pRow() + 0, 32, "|" + Funcoes.adicionaEspacos( rs.getString( "CODPROD" ), 10 ) );
					imp.say( imp.pRow() + 0, 44, "|" + Funcoes.alinhaDir( Funcoes.bdToStr( rs.getBigDecimal( "SLDLIQPROD" ) ).toString(), 8 ) );
					imp.say( imp.pRow() + 0, 54, "|" + Funcoes.alinhaDir( Funcoes.bdToStr( rs.getBigDecimal( "QTDINVP" ) ).toString(), 8 ) );
					imp.say( imp.pRow() + 0, 64, "|" + Funcoes.alinhaDir( Funcoes.bdToStr( rs.getBigDecimal( "QTDFINALPRODOP" ) ).toString(), 8 ) );
					imp.say( imp.pRow() + 0, 74, "|" + Funcoes.alinhaDir( Funcoes.bdToStr( rs.getBigDecimal( "QTDITCOMPRA" ) ).toString(), 8 ) );
					imp.say( imp.pRow() + 0, 84, "|" + Funcoes.adicEspacosEsquerda( rs.getDouble( "QTDEXPITRMA" ) + "", 8 ) );
					imp.say( imp.pRow() + 0, 94, "|" + Funcoes.alinhaDir( Funcoes.bdToStr( rs.getBigDecimal( "QTDITVENDA" ) ).toString(), 8 ) );
					imp.say( imp.pRow() + 0, 104, "|" + Funcoes.alinhaDir( Funcoes.bdToStr( beSldCalc ).toString(), 8 ) );
					imp.say( imp.pRow() + 0, 114, "|" + Funcoes.alinhaDir( Funcoes.bdToStr( rs.getBigDecimal( "SLDMOVPROD" ) ).toString(), 8 ) );
					imp.say( imp.pRow() + 0, 124, "|" + Funcoes.alinhaDir( Funcoes.bdToStr( beQtdDif ).toString(), 8 ) );
					imp.say( imp.pRow() + 0, 135, "|" );

				}

				rs.close();
				ps.close();
				con.commit();

				// Fim da impressão do total por setor

				imp.say( imp.pRow() + 1, 0, "" + imp.comprimido() );
				imp.say( imp.pRow() + 0, 1, "+" + Funcoes.replicate( "-", 133 ) + "+" );

				imp.eject();
				imp.fechaGravacao();

			} catch ( SQLException err ) {
				Funcoes.mensagemErro( this, "Erro executando a consulta.\n" + err.getMessage(), true, con, err );
				err.printStackTrace();
			}
			if ( bVisualizar ) {
				imp.preview( this );
			}
			else {
				imp.print();
			}
		} finally {
			sSql = null;
			imp = null;
			ps = null;
			rs = null;
			beSldCalc = null;
			beQtdDif = null;
		}

	}

	private void impLote( boolean bVisualizar, String sWhere ) {

		String sSql = "";
		ImprimeOS imp = null;
		int linPag = 0;
		PreparedStatement ps = null;
		ResultSet rs = null;
		double deSldCalc = 0;
		double deQtdDif = 0;

		try {

			imp = new ImprimeOS( "", con );
			linPag = imp.verifLinPag() - 1;
			imp.setTitulo( "Relatorio de Conferência de Estoque por Lote" );

			sSql = "SELECT P.DESCPROD,P.CODPROD,L.CODLOTE,L.SLDLIQLOTE," + "(SELECT SUM(QTDINVP) FROM EQINVPROD IT WHERE IT.CODEMPPD=P.CODEMP AND " + "IT.CODFILIALPD=P.CODFILIAL AND IT.CODPROD=P.CODPROD AND IT.CODEMPLE=L.CODEMP AND "
					+ "IT.CODFILIALLE=L.CODFILIAL AND IT.CODLOTE=L.CODLOTE) QTDINVP, " + "(SELECT SUM(QTDITCOMPRA) FROM CPITCOMPRA IC, CPCOMPRA C, EQTIPOMOV TM " + " WHERE IC.CODEMPPD=P.CODEMP AND IC.CODFILIALPD=P.CODFILIAL AND IC.CODPROD=P.CODPROD AND "
					+ " IC.CODEMPLE=L.CODEMP AND IC.CODFILIALLE=L.CODFILIAL AND IC.CODLOTE=L.CODLOTE AND " + " C.CODCOMPRA=IC.CODCOMPRA AND C.CODEMP=IC.CODEMP AND C.CODFILIAL=IC.CODFILIAL AND " + " TM.CODTIPOMOV=C.CODTIPOMOV AND TM.CODEMP=C.CODEMPTM AND TM.CODFILIAL=C.CODFILIALTM " + sWhere
					+ ") QTDITCOMPRA, " + "(SELECT SUM(QTDFINALPRODOP) FROM PPOP O, EQTIPOMOV TM " + " WHERE O.CODEMPPD=P.CODEMP AND O.CODFILIALPD=P.CODFILIAL AND O.CODPROD=P.CODPROD AND " + " O.CODEMPLE=L.CODEMP AND O.CODFILIALLE=L.CODFILIAL AND O.CODLOTE=L.CODLOTE AND "
					+ " TM.CODTIPOMOV=O.CODTIPOMOV AND TM.CODEMP=O.CODEMPTM AND TM.CODFILIAL=O.CODFILIALTM " + sWhere + ") QTDFINALPRODOP, " + "(SELECT SUM(QTDEXPITRMA) FROM EQRMA R, EQITRMA IR, EQTIPOMOV TM "
					+ " WHERE IR.CODEMPPD=P.CODEMP AND IR.CODFILIALPD=P.CODFILIAL AND IR.CODPROD=P.CODPROD AND " + " R.CODRMA=IR.CODRMA AND R.CODEMP=IR.CODEMP AND R.CODFILIAL=IR.CODFILIAL AND " + " IR.CODEMPLE=L.CODEMP AND IR.CODFILIALLE=L.CODFILIAL AND IR.CODLOTE=L.CODLOTE AND "
					+ " TM.CODTIPOMOV=R.CODTIPOMOV AND TM.CODEMP=R.CODEMPTM AND TM.CODFILIAL=R.CODFILIALTM " + sWhere + ") QTDEXPITRMA, " + "(SELECT SUM(QTDITVENDA) FROM VDITVENDA IV, VDVENDA V, EQTIPOMOV TM "
					+ " WHERE IV.CODEMPPD=P.CODEMP AND IV.CODFILIALPD=P.CODFILIAL AND IV.CODPROD=P.CODPROD AND " + " IV.CODEMPLE=L.CODEMP AND IV.CODFILIALLE=L.CODFILIAL AND IV.CODLOTE=L.CODLOTE AND " + " V.CODVENDA=IV.CODVENDA AND V.TIPOVENDA=IV.TIPOVENDA AND V.CODEMP=IV.CODEMP AND "
					+ " V.CODFILIAL=IV.CODFILIAL AND (NOT SUBSTR(V.STATUSVENDA,1,1)='C') AND " + " TM.CODTIPOMOV=V.CODTIPOMOV AND TM.CODEMP=V.CODEMPTM AND " + " TM.CODFILIAL=V.CODFILIALTM " + sWhere + ") QTDITVENDA " + "FROM EQPRODUTO P,EQLOTE L WHERE P.cloteprod='S' "
					+ "AND L.CODEMP=P.CODEMP AND L.CODFILIAL=P.CODFILIAL AND L.CODPROD=P.CODPROD " + "AND P.CODEMP=? AND P.CODFILIAL=? " + ( cbAtivo.getVlrString().equals( "S" ) ? "AND P.ATIVOPROD='S'" : "" ) + "AND ( NOT L.SLDLIQLOTE=( "
					+ "( COALESCE( (SELECT SUM(QTDINVP) FROM EQINVPROD IT WHERE IT.CODEMPPD=P.CODEMP AND " + "IT.CODFILIALPD=P.CODFILIAL AND IT.CODPROD=P.CODPROD AND IT.CODEMPLE=L.CODEMP AND " + "IT.CODFILIALLE=L.CODFILIAL AND IT.CODLOTE=L.CODLOTE),0) ) + "
					+ "( COALESCE( (SELECT SUM(QTDITCOMPRA) FROM CPITCOMPRA IC, CPCOMPRA C, EQTIPOMOV TM " + " WHERE IC.CODEMPPD=P.CODEMP AND IC.CODFILIALPD=P.CODFILIAL AND IC.CODPROD=P.CODPROD AND " + " IC.CODEMPLE=L.CODEMP AND IC.CODFILIALLE=L.CODFILIAL AND IC.CODLOTE=L.CODLOTE AND "
					+ " C.CODCOMPRA=IC.CODCOMPRA AND C.CODEMP=IC.CODEMP AND C.CODFILIAL=IC.CODFILIAL AND " + " TM.CODTIPOMOV=C.CODTIPOMOV AND TM.CODEMP=C.CODEMPTM AND TM.CODFILIAL=C.CODFILIALTM " + sWhere + "),0) ) + " + "(COALESCE( (SELECT SUM(QTDFINALPRODOP) FROM PPOP O, EQTIPOMOV TM "
					+ " WHERE O.CODEMPPD=P.CODEMP AND O.CODFILIALPD=P.CODFILIAL AND O.CODPROD=P.CODPROD AND " + " O.CODEMPLE=L.CODEMP AND O.CODFILIALLE=L.CODFILIAL AND O.CODLOTE=L.CODLOTE AND " + " TM.CODTIPOMOV=O.CODTIPOMOV AND TM.CODEMP=O.CODEMPTM AND TM.CODFILIAL=O.CODFILIALTM " + sWhere
					+ "),0) ) - " + "(COALESCE( (SELECT SUM(QTDEXPITRMA) FROM EQRMA R, EQITRMA IR, EQTIPOMOV TM " + " WHERE IR.CODEMPPD=P.CODEMP AND IR.CODFILIALPD=P.CODFILIAL AND IR.CODPROD=P.CODPROD AND " + " R.CODRMA=IR.CODRMA AND R.CODEMP=IR.CODEMP AND R.CODFILIAL=IR.CODFILIAL AND "
					+ " IR.CODEMPLE=L.CODEMP AND IR.CODFILIALLE=L.CODFILIAL AND IR.CODLOTE=L.CODLOTE AND " + " TM.CODTIPOMOV=R.CODTIPOMOV AND TM.CODEMP=R.CODEMPTM AND TM.CODFILIAL=R.CODFILIALTM " + sWhere + "),0) ) - "
					+ "( COALESCE( (SELECT SUM(QTDITVENDA) FROM VDITVENDA IV, VDVENDA V, EQTIPOMOV TM " + " WHERE IV.CODEMPPD=P.CODEMP AND IV.CODFILIALPD=P.CODFILIAL AND IV.CODPROD=P.CODPROD AND " + " IV.CODEMPLE=L.CODEMP AND IV.CODFILIALLE=L.CODFILIAL AND IV.CODLOTE=L.CODLOTE AND "
					+ " V.CODVENDA=IV.CODVENDA AND V.TIPOVENDA=IV.TIPOVENDA AND V.CODEMP=IV.CODEMP AND " + " V.CODFILIAL=IV.CODFILIAL AND (NOT SUBSTR(V.STATUSVENDA,1,1)='C') AND " + " TM.CODTIPOMOV=V.CODTIPOMOV AND TM.CODEMP=V.CODEMPTM AND " + " TM.CODFILIAL=V.CODFILIALTM " + sWhere + "),0 ) ) "
					+ ")) ORDER BY P.DESCPROD,L.CODLOTE";
			// System.out.println(sSql);

			try {
				ps = con.prepareStatement( sSql );
				ps.setInt( 1, Aplicativo.iCodEmp );
				ps.setInt( 2, ListaCampos.getMasterFilial( "EQPRODUTO" ) );

				rs = ps.executeQuery();

				imp.limpaPags();

				while ( rs.next() ) {
					if ( imp.pRow() >= ( linPag - 1 ) ) {
						imp.say( imp.pRow() + 1, 0, "" + imp.comprimido() );
						imp.say( imp.pRow() + 0, 0, "+" + Funcoes.replicate( "-", 133 ) + "+" );
						imp.incPags();
						imp.eject();

					}
					if ( imp.pRow() == 0 ) {
						imp.montaCab();
						imp.setTitulo( "Relatorio de Comferencia de Estoque" );
						imp.addSubTitulo( "CONFERENCIA DE ESTOQUE CONSIDERANDO SALDOS POR LOTE" );
						imp.impCab( 136, true );

						imp.say( imp.pRow() + 0, 0, "" + imp.comprimido() );
						imp.say( imp.pRow() + 0, 0, "|" );
						imp.say( imp.pRow() + 0, 135, "|" );
						imp.say( imp.pRow() + 1, 0, "" + imp.comprimido() );
						imp.say( imp.pRow() + 0, 1, "+" + Funcoes.replicate( "-", 133 ) + "+" );
						imp.say( imp.pRow() + 1, 0, "" + imp.comprimido() );
						imp.say( imp.pRow() + 0, 0, "| DESCRICAO" );
						imp.say( imp.pRow() + 0, 35, "| CODIGO" );
						imp.say( imp.pRow() + 0, 47, "| LOTE" );
						imp.say( imp.pRow() + 0, 61, "| SALDO " );
						imp.say( imp.pRow() + 0, 70, "| QTD.IV." );
						imp.say( imp.pRow() + 0, 79, "| QTD.OP." );
						imp.say( imp.pRow() + 0, 88, "| QTD.CP." );
						imp.say( imp.pRow() + 0, 97, "| QTD.RM." );
						imp.say( imp.pRow() + 0, 106, "| QTD.VD." );
						imp.say( imp.pRow() + 0, 115, "| SLD.CA." );
						imp.say( imp.pRow() + 0, 124, "| DIF.SD." );
						imp.say( imp.pRow() + 0, 135, "|" );
						imp.say( imp.pRow() + 1, 0, "" + imp.comprimido() );
						imp.say( imp.pRow() + 0, 1, "+" + Funcoes.replicate( "-", 133 ) + "+" );

					}

					deSldCalc = rs.getDouble( "QTDINVP" ) + rs.getDouble( "QTDITCOMPRA" ) + rs.getDouble( "QTDFINALPRODOP" ) - rs.getDouble( "QTDEXPITRMA" ) - rs.getDouble( "QTDITVENDA" );
					deQtdDif = deSldCalc - rs.getDouble( "SLDLIQLOTE" );

					imp.say( imp.pRow() + 1, 0, "" + imp.comprimido() );
					imp.say( imp.pRow() + 0, 0, "|" + Funcoes.adicionaEspacos( rs.getString( "DESCPROD" ), 33 ) );
					imp.say( imp.pRow() + 0, 35, "|" + Funcoes.adicionaEspacos( rs.getString( "CODPROD" ), 10 ) );
					imp.say( imp.pRow() + 0, 47, "|" + Funcoes.adicionaEspacos( rs.getString( "CODLOTE" ), 13 ) );
					imp.say( imp.pRow() + 0, 61, "|" + Funcoes.adicEspacosEsquerda( rs.getDouble( "SLDLIQLOTE" ) + "", 8 ) );
					imp.say( imp.pRow() + 0, 70, "|" + Funcoes.adicEspacosEsquerda( rs.getDouble( "QTDINVP" ) + "", 8 ) );
					imp.say( imp.pRow() + 0, 79, "|" + Funcoes.adicEspacosEsquerda( rs.getDouble( "QTDFINALPRODOP" ) + "", 8 ) );
					imp.say( imp.pRow() + 0, 88, "|" + Funcoes.adicEspacosEsquerda( rs.getDouble( "QTDITCOMPRA" ) + "", 8 ) );
					imp.say( imp.pRow() + 0, 97, "|" + Funcoes.adicEspacosEsquerda( rs.getDouble( "QTDEXPITRMA" ) + "", 8 ) );
					imp.say( imp.pRow() + 0, 106, "|" + Funcoes.adicEspacosEsquerda( rs.getDouble( "QTDITVENDA" ) + "", 8 ) );
					imp.say( imp.pRow() + 0, 115, "|" + Funcoes.adicEspacosEsquerda( deSldCalc + "", 8 ) );
					imp.say( imp.pRow() + 0, 124, "|" + Funcoes.adicEspacosEsquerda( deQtdDif + "", 8 ) );
					imp.say( imp.pRow() + 0, 135, "|" );

				}

				rs.close();
				ps.close();
				con.commit();

				// Fim da impressão do total por setor

				imp.say( imp.pRow() + 1, 0, "" + imp.comprimido() );
				imp.say( imp.pRow() + 0, 1, "+" + Funcoes.replicate( "-", 133 ) + "+" );

				imp.eject();
				imp.fechaGravacao();

			} catch ( SQLException err ) {
				Funcoes.mensagemErro( this, "Erro executando a consulta.\n" + err.getMessage(), true, con, err );
				err.printStackTrace();
			}
			if ( bVisualizar ) {
				imp.preview( this );
			}
			else {
				imp.print();
			}
		} finally {
			sSql = null;
			imp = null;
			ps = null;
			rs = null;
			deSldCalc = 0;
			deQtdDif = 0;
		}

	}

	/*
	 * 
	 * @author robson
	 * 
	 * UPDATE EQLOTE P SET P.SLDLOTE= ((SELECT SUM(QTDINVP) FROM EQINVPROD IT WHERE IT.CODEMPPD=P.CODEMP AND IT.CODFILIALPD=P.CODFILIAL AND IT.CODPROD=P.CODPROD AND IT.CODEMPLE=P.CODEMP AND IT.CODFILIALLE=P.CODFILIAL AND IT.CODLOTE=P.CODLOTE ) + (SELECT SUM(QTDITCOMPRA) FROM CPITCOMPRA IC, CPCOMPRA
	 * C, EQTIPOMOV TM WHERE IC.CODEMPPD=P.CODEMP AND IC.CODFILIALPD=P.CODFILIAL AND IC.CODPROD=P.CODPROD AND IC.CODEMPLE=P.CODEMP AND IC.CODFILIALLE=P.CODFILIAL AND IC.CODLOTE=P.CODLOTE AND C.CODCOMPRA=IC.CODCOMPRA AND C.CODEMP=IC.CODEMP AND C.CODFILIAL=IC.CODFILIAL AND TM.CODTIPOMOV=C.CODTIPOMOV
	 * AND TM.CODEMP=C.CODEMPTM AND TM.CODFILIAL=C.CODFILIALTM AND TM.ESTOQTIPOMOV='S' ) - (SELECT SUM(QTDITVENDA) FROM VDITVENDA IV, VDVENDA V, EQTIPOMOV TM WHERE IV.CODEMPPD=P.CODEMP AND IV.CODFILIALPD=P.CODFILIAL AND IV.CODPROD=P.CODPROD AND IV.CODEMPLE=P.CODEMP AND IV.CODFILIALLE=P.CODFILIAL AND
	 * IV.CODLOTE=P.CODLOTE AND V.CODVENDA=IV.CODVENDA AND V.TIPOVENDA=IV.TIPOVENDA AND V.CODEMP=IV.CODEMP AND V.CODFILIAL=IV.CODFILIAL AND (NOT SUBSTR(V.STATUSVENDA,1,1)='C') AND TM.CODTIPOMOV=V.CODTIPOMOV AND TM.CODEMP=V.CODEMPTM AND TM.CODFILIAL=V.CODFILIALTM AND TM.ESTOQTIPOMOV='S') ) WHERE
	 * P.CODEMP=4 AND P.CODFILIAL=1 AND ( NOT P.SLDLIQLOTE=( ( COALESCE((SELECT SUM(QTDINVP) FROM EQINVPROD IT WHERE IT.CODEMPPD=P.CODEMP AND IT.CODFILIALPD=P.CODFILIAL AND IT.CODPROD=P.CODPROD AND IT.CODEMPLE=P.CODEMP AND IT.CODFILIALLE=P.CODFILIAL AND IT.CODLOTE=P.CODLOTE AND ),0 )) + (
	 * COALESCE((SELECT SUM(QTDITCOMPRA) FROM CPITCOMPRA IC, CPCOMPRA C, EQTIPOMOV TM WHERE IC.CODEMPPD=P.CODEMP AND IC.CODFILIALPD=P.CODFILIAL AND IC.CODPROD=P.CODPROD AND IC.CODEMPLE=P.CODEMP AND IC.CODFILIALLE=P.CODFILIAL AND IC.CODLOTE=P.CODLOTE AND C.CODCOMPRA=IC.CODCOMPRA AND
	 * C.CODEMP=IC.CODEMP AND C.CODFILIAL=IC.CODFILIAL AND TM.CODTIPOMOV=C.CODTIPOMOV AND TM.CODEMP=C.CODEMPTM AND TM.CODFILIAL=C.CODFILIALTM AND TM.ESTOQTIPOMOV='S'),0 )) - ( COALESCE((SELECT SUM(QTDITVENDA) FROM VDITVENDA IV, VDVENDA V, EQTIPOMOV TM WHERE IV.CODEMPPD=P.CODEMP AND
	 * IV.CODFILIALPD=P.CODFILIAL AND IV.CODPROD=P.CODPROD AND IV.CODEMPLE=P.CODEMP AND IV.CODFILIALLE=P.CODFILIAL AND IV.CODLOTE=P.CODLOTE AND V.CODVENDA=IV.CODVENDA AND V.TIPOVENDA=IV.TIPOVENDA AND V.CODEMP=IV.CODEMP AND V.CODFILIAL=IV.CODFILIAL AND (NOT SUBSTR(V.STATUSVENDA,1,1)='C') AND
	 * TM.CODTIPOMOV=V.CODTIPOMOV AND TM.CODEMP=V.CODEMPTM AND TM.CODFILIAL=V.CODFILIALTM AND TM.ESTOQTIPOMOV='S'),0)) )) ;
	 * 
	 * 
	 * 
	 *  * TODO To change the template for this generated type comment go to Window - Preferences - Java - Code Style - Code Templates
	 */

}
