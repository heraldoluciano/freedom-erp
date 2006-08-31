/**
 * @version 08/12/2000 <BR>
 * @author Setpoint Informática Ltda./Alex Rodrigues <BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.std <BR>
 * Classe:
 * @(#)FRVendasCli <BR>
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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Vector;

import org.freedom.componentes.ImprimeOS;
import org.freedom.componentes.JCheckBoxPad;
import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JRadioGroup;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FRelatorio;

public class FRVendasCli extends FRelatorio {

	private static final long serialVersionUID = 1L;

	private JTextFieldPad txtDataini = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtDatafim = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JCheckBoxPad cbListaFilial = new JCheckBoxPad( "Listar vendas das filiais ?", "S", "N" );

	private JCheckBoxPad cbDesc = new JCheckBoxPad( "Ordenar decrescente ?", "S", "N" );

	private JRadioGroup rgOrdem = null;

	private JRadioGroup rgFaturados = null;

	private JRadioGroup rgFinanceiro = null;

	private Vector vLabsOrd = new Vector();

	private Vector vValsOrd = new Vector();

	private Vector vLabsFat = new Vector();

	private Vector vValsFat = new Vector();

	private Vector vLabsFin = new Vector();

	private Vector vValsFin = new Vector();

	public FRVendasCli() {

		setTitulo( "Vendas por Cliente" );
		setAtribos( 80, 80, 290, 310 );

		GregorianCalendar cPeriodo = new GregorianCalendar();
		txtDatafim.setVlrDate( cPeriodo.getTime() );
		cPeriodo.set( Calendar.DAY_OF_MONTH, cPeriodo.get( Calendar.DAY_OF_MONTH ) - 30 );
		txtDataini.setVlrDate( cPeriodo.getTime() );

		vLabsOrd.addElement( "Código" );
		vLabsOrd.addElement( "Razão" );
		vLabsOrd.addElement( "Valor" );
		vValsOrd.addElement( "C" );
		vValsOrd.addElement( "R" );
		vValsOrd.addElement( "V" );
		rgOrdem = new JRadioGroup( 1, 3, vLabsOrd, vValsOrd );
		rgOrdem.setVlrString( "V" );

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

		cbDesc.setVlrString( "S" );

		adic( new JLabelPad( "Periodo:" ), 7, 5, 120, 20 );
		adic( new JLabelPad( "De:" ), 7, 27, 30, 20 );
		adic( txtDataini, 37, 27, 90, 20 );
		adic( new JLabelPad( "Até:" ), 140, 27, 30, 20 );
		adic( txtDatafim, 175, 27, 90, 20 );
		adic( cbListaFilial, 7, 55, 200, 20 );
		adic( new JLabelPad( "Ordem" ), 7, 80, 50, 20 );
		adic( rgOrdem, 7, 100, 261, 30 );
		adic( cbDesc, 7, 135, 250, 20 );
		adic( rgFaturados, 7, 165, 120, 70 );
		adic( rgFinanceiro, 148, 165, 120, 70 );

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
		String sOrdem = "";
		String sWhere1 = null;
		String sWhere2 = null;
		String sLinhaFina = Funcoes.replicate( "-", 133 );
		ImprimeOS imp = new ImprimeOS( "", con );
		int linPag = imp.verifLinPag() - 1;
		int count = 1;

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
		else if ( rgFaturados.getVlrString().equals( "A" ) ) {
			sWhere1 = " AND TM.FISCALTIPOMOV IN ('S','N') ";
		}

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

		if ( rgOrdem.getVlrString().equals( "C" ) ) {
			sOrdem = "S".equals( cbDesc.getVlrString() ) ? "V.CODCLI DESC, C.RAZCLI DESC, C.FONECLI DESC" : "V.CODCLI, C.RAZCLI, C.FONECLI" ;
		}
		else if ( rgOrdem.getVlrString().equals( "R" ) ) {
			sOrdem = "S".equals( cbDesc.getVlrString() ) ? "C.RAZCLI DESC, V.CODCLI DESC, C.FONECLI DESC" : "C.RAZCLI, V.CODCLI, C.FONECLI";
		}
		else if ( rgOrdem.getVlrString().equals( "V" ) ) {
			sOrdem = "S".equals( cbDesc.getVlrString() ) ? "5 DESC" : "5" ;
		}

		try {

			linPag = imp.verifLinPag() - 1;
			imp.limpaPags();
			imp.montaCab();
			imp.setTitulo( "Relatório de Vendas por Cliente" );
			imp.addSubTitulo( "VENDAS  -  PERIODO DE :" + txtDataini.getVlrString() + " ATE: " + txtDatafim.getVlrString() );
			imp.addSubTitulo( sCab.toString() );

			sSQL.append( "SELECT V.CODCLI, C.RAZCLI, C.DDDCLI, C.FONECLI, SUM(V.VLRLIQVENDA) " );
			sSQL.append( "FROM VDVENDA V, VDCLIENTE C, EQTIPOMOV TM " );
			sSQL.append( "WHERE V.CODEMP=? AND V.CODFILIAL=? " );
			sSQL.append( "AND V.CODEMPCL=C.CODEMP AND V.CODFILIALCL=C.CODFILIAL AND V.CODCLI=C.CODCLI " );
			sSQL.append( "AND NOT SUBSTR(V.STATUSVENDA,1,1)='C' " );
			sSQL.append( "AND V.DTEMITVENDA BETWEEN ? AND ? " );
			sSQL.append( sWhere1 );
			sSQL.append( sWhere2 );
			sSQL.append( " GROUP BY V.CODCLI, C.RAZCLI, C.DDDCLI, C.FONECLI" );
			sSQL.append( " ORDER BY " + sOrdem );

			ps = con.prepareStatement( sSQL.toString() );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "VDCLIENTE" ) );
			ps.setDate( 3, Funcoes.dateToSQLDate( txtDataini.getVlrDate() ) );
			ps.setDate( 4, Funcoes.dateToSQLDate( txtDatafim.getVlrDate() ) );
			rs = ps.executeQuery();

			while ( rs.next() ) {

				if ( imp.pRow() == linPag ) {

					imp.pulaLinha( 1, imp.comprimido() );
					imp.say( 0, "+" + sLinhaFina + "+" );
					imp.eject();
					imp.incPags();

				}

				if ( imp.pRow() == 0 ) {

					imp.impCab( 136, true );
					imp.say( 0, imp.comprimido() );
					imp.say( imp.pRow(), 0, "|" + sLinhaFina + "|" );
					imp.pulaLinha( 1, imp.comprimido() );
					imp.say( 0, "|" );
					imp.say( 10, "|  Cod.cli" );
					imp.say( 23, "|  Razao Social" );
					imp.say( 75, "|  Telefone" );
					imp.say( 95, "|  Valor Total" );
					imp.say( 135, "|" );
					imp.pulaLinha( 1, imp.comprimido() );
					imp.say( 0, "|" + sLinhaFina + "|" );

				}

				imp.pulaLinha( 1, imp.comprimido() );
				imp.say( 0, "| " + Funcoes.alinhaDir( count++, 6 ) );
				imp.say( 10, "| " + Funcoes.alinhaDir( rs.getInt( "CODCLI" ), 10 ) );
				imp.say( 23, "| " + rs.getString( "RAZCLI" ) );
				imp.say( 75, "| " + ( rs.getString( "DDDCli" ) != null ? "(" + rs.getString( "DDDCli" ) + ")" : "" ) + ( rs.getString( "FoneCli" ) != null ? Funcoes.setMascara( rs.getString( "FoneCli" ).trim(), "####-####" ) : "" ) );
				imp.say( 95, "| " + Funcoes.strDecimalToStrCurrency( 18, 2, rs.getString( 5 ) ) );
				imp.say( 135, "|" );

			}

			imp.pulaLinha( 1, imp.comprimido() );
			imp.say( 0, "+" + sLinhaFina + "+" );

			imp.eject();

			imp.fechaGravacao();

		} catch ( SQLException err ) {
			Funcoes.mensagemErro( this, "Erro na consulta ao relatório de vendas!\n" + err.getMessage(), true, con, err );
			err.printStackTrace();
		} finally {
			ps = null;
			rs = null;
			sCab = null;
			sOrdem = null;
			sWhere1 = null;
			sWhere2 = null;
			sSQL = null;
			System.gc();
		}

		if ( bVisualizar )
			imp.preview( this );
		else
			imp.print();
	}

	public void setConexao( Connection cn ) {

		super.setConexao( cn );
	}
}
