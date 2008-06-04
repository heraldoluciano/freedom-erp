/**
 * @version 04/06/2002 <BR>
 * @author Setpoint Informática Ltda./Reginaldo Garcia Heua <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)FRLancCategoria.java <BR>
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


import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import net.sf.jasperreports.engine.JasperPrintManager;

import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FPrinterJob;
import org.freedom.telas.FRelatorio;


public class FRLancCategoria extends FRelatorio implements ActionListener{
	
	private static final long serialVersionUID = 1L;

	private JTextFieldPad txtDataini = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtDatafim = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );
	
	public FRLancCategoria(){
		
		setTitulo( "Lançamentos por categoria" );
		setAtribos( 80, 80, 350, 150 );
		
		montaTela();
	}
	
	private void montaTela(){
		
		JLabel periodo = new JLabel( "Período", SwingConstants.CENTER );
		periodo.setOpaque( true );
		adic( periodo, 25, 10, 80, 20 );
		JLabel borda = new JLabel();
		borda.setBorder( BorderFactory.createEtchedBorder() );
		adic( borda, 7, 20, 296, 45 );
		adic( txtDataini, 25, 35, 110, 20 );
		adic( new JLabel( "até", SwingConstants.CENTER ), 135, 35, 40, 20 );
		adic( txtDatafim, 175, 35, 110, 20 );
		
		GregorianCalendar cPeriodo = new GregorianCalendar();
		txtDatafim.setVlrDate( cPeriodo.getTime() );
		cPeriodo.set( Calendar.DAY_OF_MONTH, cPeriodo.get( Calendar.DAY_OF_MONTH ) - 30 );
		txtDataini.setVlrDate( cPeriodo.getTime() );
	}

	public void imprimir( boolean bVisualizar ){
		
		StringBuilder sSQL = new StringBuilder();
		ResultSet rs = null;
		
		
		if ( txtDatafim.getVlrDate().before( txtDataini.getVlrDate() ) ) {
			
			Funcoes.mensagemInforma( this, "Data final maior que a data inicial!" );
			return;
		}
		
		sSQL.append( " SELECT SL.CODPLAN, PL.DESCPLAN, SL.DATASUBLANCA, SL.HISTSUBLANCA, L.DOCLANCA, SL.VLRSUBLANCA,CC.DESCCC, C.DESCCONTA " );
		sSQL.append( "FROM FNSUBLANCA SL " );
		sSQL.append( "LEFT OUTER JOIN FNPLANEJAMENTO PL ON PL.CODEMP=SL.CODEMPPN AND PL.CODFILIAL=SL.CODFILIALPN AND PL.CODPLAN=SL.CODPLAN " );
		sSQL.append( "LEFT OUTER JOIN FNLANCA L ON L.CODEMP=SL.CODEMP AND L.CODFILIAL=SL.CODFILIAL AND L.CODLANCA=SL.CODLANCA " );
		sSQL.append( "LEFT OUTER JOIN FNCC CC ON CC.CODEMP=SL.CODEMPCC AND CC.CODFILIAL=SL.CODFILIALCC AND CC.CODCC=SL.CODCC AND CC.ANOCC=SL.ANOCC " );
		sSQL.append( "LEFT OUTER JOIN FNCONTA C ON PL.CODEMP=SL.CODEMPPN AND PL.CODFILIAL=SL.CODFILIALPN AND PL.CODPLAN=SL.CODPLAN AND " );
		sSQL.append( "C.CODEMPPN=PL.CODEMP AND C.CODFILIALPN=PL.CODFILIAL AND C.CODPLAN=PL.CODPLAN " );
		sSQL.append( "WHERE SL.CODEMP=? AND SL.CODFILIAL=? AND SL.DATASUBLANCA BETWEEN ? AND ? " );
		sSQL.append( "ORDER BY SL.CODPLAN, SL.DATASUBLANCA" );
		
		try {
			
			PreparedStatement ps = con.prepareStatement( sSQL.toString() );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "FNSUBLANCA" ) );
			ps.setDate( 3, Funcoes.dateToSQLDate( txtDataini.getVlrDate() ) );
			ps.setDate( 4, Funcoes.dateToSQLDate( txtDatafim.getVlrDate() ) );
			
			rs = ps.executeQuery();
			
		} catch ( Exception e ) {
			
			e.printStackTrace();
			Funcoes.mensagemErro( this, "Erro ao buscar dados " + e.getMessage());
		}
		
		imprimiGrafico( rs, bVisualizar, "" );
	}
		private void imprimiGrafico( final ResultSet rs, final boolean bVisualizar,  final String sCab ) {

			FPrinterJob dlGr = null;
			HashMap<String, Object> hParam = new HashMap<String, Object>();

			hParam.put( "CODEMP", Aplicativo.iCodEmp );
			hParam.put( "CODFILIAL", ListaCampos.getMasterFilial( "FNCONTA" ) );
			hParam.put( "RAZAOEMP", Aplicativo.sEmpSis );
			hParam.put( "FILTROS", sCab );

			dlGr = new FPrinterJob( "relatorios/FRLancamentos.jasper", "Lançamentos por categoria", sCab, rs, hParam, this );

			if ( bVisualizar ) {
				dlGr.setVisible( true );
			}
			
			else {
				
				try {
					JasperPrintManager.printReport( dlGr.getRelatorio(), true );
				} catch ( Exception err ) {
					Funcoes.mensagemErro( this, "erro na impressão do relatório!" + err.getMessage(), true, con, err );
				}
			}
		}
	}
