/**
 * @version 08/12/2000 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)FRExtrato.java <BR>
 * 
 * Este programa é licenciado de acordo com a LPG-PC (Licença Pública Geral para Programas de Computador), <BR>
 * versão 2.1.0 ou qualquer versão posterior. <BR>
 * A LPG-PC deve acompanhar todas PUBLICAÇÕES, DISTRIBUIÇÕES e REPRODUÇÕES deste Programa. <BR>
 * Caso uma cópia da LPG-PC não esteja disponível junto com este Programa, você pode contatar <BR>
 * o LICENCIADOR ou então pegar uma cópia em: <BR>
 * Licença: http://www.lpg.adv.br/licencas/lpgpc.rtf <BR>
 * Para poder USAR, PUBLICAR, DISTRIBUIR, REPRODUZIR ou ALTERAR este Programa é preciso estar <BR>
 * de acordo com os termos da LPG-PC <BR> <BR>
 *
 * Comentários sobre a classe...
 * 
 */

package org.freedom.modulos.std;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.ImprimeOS;
import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.AplicativoPD;
import org.freedom.telas.FRelatorio;

public class FRExtrato extends FRelatorio {

	private static final long serialVersionUID = 1L;

	private JTextFieldPad txtCodConta = new JTextFieldPad( JTextFieldPad.TP_STRING, 10, 0 );

	private JTextFieldPad txtDataini = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtDatafim = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldFK txtDescConta = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private ListaCampos lcConta = new ListaCampos( this );

	public FRExtrato() {

		setTitulo( "Extrato" );
		setAtribos( 80, 80, 350, 200 );

		txtCodConta.setRequerido( true );
		lcConta.add( new GuardaCampo( txtCodConta, "NumConta", "Cód.conta", ListaCampos.DB_PK, false ) );
		lcConta.add( new GuardaCampo( txtDescConta, "DescConta", "Descrição da conta", ListaCampos.DB_SI, false ) );
		lcConta.montaSql( false, "CONTA", "FN" );
		lcConta.setReadOnly( true );
		txtCodConta.setTabelaExterna( lcConta );
		txtCodConta.setFK( true );
		txtCodConta.setNomeCampo( "NumConta" );

		JLabel periodo = new JLabel( "Período", SwingConstants.CENTER );
		periodo.setOpaque( true );
		adic( periodo, 25, 10, 80, 20 );
		JLabel borda = new JLabel();
		borda.setBorder( BorderFactory.createEtchedBorder() );
		adic( borda, 7, 20, 296, 45 );
		adic( txtDataini, 25, 35, 110, 20 );
		adic( new JLabel( "até", SwingConstants.CENTER ), 135, 35, 40, 20 );
		adic( txtDatafim, 175, 35, 110, 20 );
		
		adic( new JLabelPad( "Nº conta" ), 7, 75, 80, 20 );		
		adic( txtCodConta, 7, 95, 90, 20 );
		adic( new JLabelPad( "Descrição da conta" ), 100, 75, 200, 20 );
		adic( txtDescConta, 100, 95, 200, 20 );

		GregorianCalendar cPeriodo = new GregorianCalendar();
		txtDatafim.setVlrDate( cPeriodo.getTime() );
		cPeriodo.set( Calendar.DAY_OF_MONTH, cPeriodo.get( Calendar.DAY_OF_MONTH ) - 30 );
		txtDataini.setVlrDate( cPeriodo.getTime() );
	}

	public void setConexao( Connection cn ) {

		super.setConexao( cn );
		lcConta.setConexao( cn );
	}

	public void imprimir( boolean bVisualizar ) {

		if ( txtDatafim.getVlrDate().before( txtDataini.getVlrDate() ) ) {
			Funcoes.mensagemInforma( this, "Data final maior que a data inicial!" );
			return;
		}
		else if ( txtCodConta.getVlrString().equals( "" ) ) {
			Funcoes.mensagemInforma( this, "Número da conta é requerido!" );
			return;
		}
		
		String sCodConta = txtCodConta.getVlrString();
		String sDataLanca = "";
		String sConta = "";
		String linhafina = Funcoes.replicate( "-", 133 );
		BigDecimal bTotal = new BigDecimal( "0" );
		BigDecimal bSaldo = new BigDecimal( "0" );
		BigDecimal bSaldoLinha = new BigDecimal( "0" );
		BigDecimal bVlrDeb = new BigDecimal( "0" );
		BigDecimal bVlrCred = new BigDecimal( "0" );
		BigDecimal bVlrTotDeb = new BigDecimal( "0" );
		BigDecimal bVlrTotCred = new BigDecimal( "0" );
		BigDecimal bAnt = buscaSaldoAnt();
		ImprimeOS imp = new ImprimeOS( "", con );
		boolean bPrim = true;
		int linPag = imp.verifLinPag() - 1;


		try {

			imp.setTitulo( "Extrato Bancário" );
			StringBuilder sSQL = new StringBuilder();   
				
			sSQL.append( "SELECT S.DATASL,L.HISTBLANCA,L.DOCLANCA,SL.VLRSUBLANCA,S.SALDOSL FROM FNSALDOLANCA S," ); 
			sSQL.append( "FNLANCA L,FNCONTA C, FNSUBLANCA SL WHERE L.FLAG IN " );
			sSQL.append( AplicativoPD.carregaFiltro( con, org.freedom.telas.Aplicativo.iCodEmp ) ); 
			sSQL.append( " AND C.CODEMP=? AND C.CODFILIAL=? AND C.NUMCONTA=? " );
			sSQL.append( "AND L.CODEMP=? AND L.CODFILIAL=? AND L.CODLANCA=SL.CODLANCA " );
			sSQL.append( "AND S.CODPLAN=SL.CODPLAN AND S.CODEMP=SL.CODEMPPN AND S.CODFILIAL=SL.CODFILIALPN " ); 
			sSQL.append( "AND SL.DATASUBLANCA BETWEEN ? AND ? AND S.DATASL=SL.DATASUBLANCA " );
			sSQL.append( "AND SL.CODPLAN=C.CODPLAN AND SL.CODEMPPN=C.CODEMPPN AND SL.CODFILIALPN=C.CODFILIALPN " ); 
			sSQL.append( "AND SL.CODEMP=? AND SL.CODFILIAL=? ORDER BY S.DATASL,L.CODLANCA" );
			
			PreparedStatement ps = con.prepareStatement( sSQL.toString() );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "FNCONTA" ) );
			ps.setString( 3, sCodConta );
			ps.setInt( 4, Aplicativo.iCodEmp );
			ps.setInt( 5, ListaCampos.getMasterFilial( "FNLANCA" ) );
			ps.setDate( 6, Funcoes.dateToSQLDate( txtDataini.getVlrDate() ) );
			ps.setDate( 7, Funcoes.dateToSQLDate( txtDatafim.getVlrDate() ) );
			ps.setInt( 8, Aplicativo.iCodEmp );
			ps.setInt( 9, ListaCampos.getMasterFilial( "FNSUBLANCA" ) );
			
			ResultSet rs = ps.executeQuery();
			
			imp.limpaPags();
			imp.setTitulo( "Extrato Bancário" );
			imp.addSubTitulo( "EXTRATO BANCÁRIO" );

			sConta = "CONTA: " + sCodConta + " - " + txtDescConta.getVlrString();
			
			imp.addSubTitulo( sConta );

			while ( rs.next() ) {
				
				if ( ! bPrim ) {
					if ( ! ( sDataLanca.equals( rs.getString( "DataSL" ) ) ) ) {
						bTotal = new BigDecimal( rs.getString( "SaldoSL" ) );
					}
				}
				else {
					bTotal = new BigDecimal( rs.getString( "SaldoSL" ) );
				}
				
				if ( imp.pRow() == linPag ) {
					imp.eject();
					imp.incPags();
				}
				if ( imp.pRow() == 0 ) {
					
					imp.montaCab();
					imp.impCab( 136, true );

					imp.say( 0, imp.comprimido() );
					imp.say( 0, "|" + linhafina + "|" );
					imp.pulaLinha( 1, imp.comprimido() );
					imp.say( 0, "| Data" );
					imp.say( 14, "| Historico" );
					imp.say( 69, "| Doc" );
					imp.say( 82, "| Débito" );
					imp.say( 100, "| Crédito" );
					imp.say( 118, "| Saldo" );
					imp.say( 135, "|" );
					imp.pulaLinha( 1, imp.comprimido() );
					imp.say( 0, "|" + linhafina + "|" );
					
					if ( bPrim ) {
						
						imp.pulaLinha( 1, imp.comprimido() );
						imp.say( 0, "|" );
						imp.say( 103, "Saldo Anterior: " + Funcoes.strDecimalToStrCurrency( 15, Aplicativo.casasDecFin, bAnt.toString() ) );
						imp.say( 135, "|" );
						imp.pulaLinha( 1, imp.comprimido() );
						imp.say( 0, "|" + linhafina + "|" );
						bSaldoLinha = new BigDecimal( bAnt.toString() );
					}
				}
				bPrim = false;
				bSaldo = new BigDecimal( rs.getBigDecimal( "SaldoSL" ).toString());
				bAnt = bSaldo;
				sDataLanca = rs.getString( "DataSL" );
				bSaldoLinha = bSaldoLinha.add( rs.getBigDecimal( "VlrSubLanca" ) );

				if ( rs.getFloat( "VlrSubLanca" ) < 0 ) {
					bVlrDeb = new BigDecimal( rs.getBigDecimal( "VlrSubLanca" ).toString() ).abs();
					bVlrCred = new BigDecimal( "0.00" );
					bVlrTotDeb = bVlrTotDeb.add( new BigDecimal( bVlrDeb.toString() ) );
				}
				else {
					bVlrCred = new BigDecimal( rs.getBigDecimal( "VlrSubLanca" ).toString() );
					bVlrDeb = new BigDecimal( "0.00" );
					bVlrTotCred = bVlrTotCred.add( new BigDecimal( bVlrCred.toString() ) );
				}

				imp.pulaLinha( 1, imp.comprimido() );
				imp.say( 0, "| " + Funcoes.sqlDateToStrDate( rs.getDate( "DataSL" ) ) );
				imp.say( 14, "| " + Funcoes.copy( rs.getString( "HistBLanca" ), 0, 50 ) );
				imp.say( 69, "| " + Funcoes.alinhaDir( rs.getString( "DocLanca" ), 10 ) );
				imp.say( 82, "|" );
				if ( bVlrDeb.floatValue() != 0 ) {
					imp.say( imp.pRow() + 0, 84, Funcoes.strDecimalToStrCurrency( 15, Aplicativo.casasDecFin, bVlrDeb.toString() ) );
				}
				imp.say( 100, "|" );
				if ( bVlrCred.floatValue() != 0 ) {
					imp.say( 0, 102, Funcoes.strDecimalToStrCurrency( 15, Aplicativo.casasDecFin, bVlrCred.toString() ) );
				}
				imp.say( 118, "|" + Funcoes.strDecimalToStrCurrency( 15, Aplicativo.casasDecFin, bSaldoLinha.toString() ) );
				imp.say( 135, "|" );
			}
			imp.pulaLinha( 1, imp.comprimido() );
			imp.say( 0, "|" + linhafina + "|" );
			imp.pulaLinha( 1, imp.comprimido() );
			imp.say( 0, "|" );
			imp.say( 82, "|" );
			imp.say( 84, Funcoes.strDecimalToStrCurrency( 15, Aplicativo.casasDecFin, bVlrTotDeb.toString() ) );
			imp.say( 100, "|" );
			imp.say( 102, Funcoes.strDecimalToStrCurrency( 15, Aplicativo.casasDecFin, bVlrTotCred.toString() ) );
			imp.say( 118, "|" );
			imp.say( 119, Funcoes.strDecimalToStrCurrency( 15, Aplicativo.casasDecFin, bTotal.toString() ) );
			imp.say( 135, "|" );
			imp.pulaLinha( 1, imp.comprimido() );
			imp.say( 0, "+" + linhafina + "+" );

			imp.eject();
			imp.fechaGravacao();

			if ( ! con.getAutoCommit() ) {
				con.commit();
			}
		} catch ( Exception e ) {
			e.printStackTrace();
			Funcoes.mensagemErro( this, "Erro consulta tabela de preços!\n" + e.getMessage(), true, con, e );
		}

		if ( bVisualizar ) {
			imp.preview( this );
		}
		else {
			imp.print();
		}
	}

	private BigDecimal buscaSaldoAnt() {

		BigDecimal bigRetorno = new BigDecimal( "0.00" );
		StringBuilder sSQL = new StringBuilder(); 
			
		sSQL.append( "SELECT S.SALDOSL FROM FNSALDOLANCA S, FNCONTA C " );
		sSQL.append( "WHERE C.NUMCONTA=? AND C.CODEMP=? AND C.CODFILIAL=? " ); 
		sSQL.append( "AND S.CODEMP=C.CODEMPPN AND S.CODFILIAL=C.CODFILIALPN " );
		sSQL.append( "AND S.CODPLAN=C.CODPLAN AND S.DATASL=" );
		sSQL.append( "(SELECT MAX(S1.DATASL) FROM FNSALDOLANCA S1 " );
		sSQL.append( "WHERE S1.DATASL < ? AND S1.CODPLAN=S.CODPLAN " );
		sSQL.append( "AND S1.CODEMP=S.CODEMP AND S1.CODFILIAL=S.CODFILIAL)" );
		
		try {
			
			PreparedStatement ps = con.prepareStatement( sSQL.toString() );
			ps.setString( 1, txtCodConta.getVlrString() );
			ps.setInt( 2, Aplicativo.iCodEmp );
			ps.setInt( 3, ListaCampos.getMasterFilial( "FNCONTA" ) );
			ps.setDate( 4, Funcoes.dateToSQLDate( txtDataini.getVlrDate() ) );
			ResultSet rs = ps.executeQuery();
			if ( rs.next() ) {
				bigRetorno = new BigDecimal( rs.getString( "SaldoSL" ) );
			}
			rs.close();
			ps.close();
			if ( ! con.getAutoCommit() ) {
				con.commit();
			}
		} catch ( Exception e ) {
			e.printStackTrace();
			Funcoes.mensagemErro( this, "Erro ao buscar saldo anterior!\n" + e.getMessage(), true, con, e );
		}
		return bigRetorno;
	}
}
