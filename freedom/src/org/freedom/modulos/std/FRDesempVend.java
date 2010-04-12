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

import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.GuardaCampo;
import org.freedom.library.ListaCampos;
import org.freedom.library.swing.JLabelPad;
import org.freedom.library.swing.JTextFieldFK;
import org.freedom.library.swing.JTextFieldPad;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.BorderFactory;
import javax.swing.SwingConstants;

import net.sf.jasperreports.engine.JasperPrintManager;

import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FPrinterJob;
import org.freedom.telas.FRelatorio;

public class FRDesempVend extends FRelatorio {

	private static final long serialVersionUID = 1L;

	private JTextFieldPad txtDataini = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtDatafim = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtCodVend = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtDescVend = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );
	
	private ListaCampos lcVend = new ListaCampos( this );

	public FRDesempVend() {

		super( false );
		setTitulo( "Desempenho por vendedor" );
		setAtribos( 80, 80, 330, 200 );
		
		montaListaCampos();
		montaTela();

		GregorianCalendar cPeriodo = new GregorianCalendar();
		txtDatafim.setVlrDate( cPeriodo.getTime() );
		cPeriodo.set( Calendar.DAY_OF_MONTH, cPeriodo.get( Calendar.DAY_OF_MONTH ) - 30 );
		txtDataini.setVlrDate( cPeriodo.getTime() );

	}
	
	private void montaListaCampos(){
		
		lcVend.add( new GuardaCampo( txtCodVend, "CodVend", "Cód.comiss.", ListaCampos.DB_PK, txtDescVend, false ) );
		lcVend.add( new GuardaCampo( txtDescVend, "NomeVend", "Descrição do comissionado", ListaCampos.DB_SI, false ) );
		lcVend.montaSql( false, "VENDEDOR", "VD" );
		lcVend.setReadOnly( true );
		txtCodVend.setPK( true );
		txtCodVend.setTabelaExterna( lcVend );
		txtCodVend.setListaCampos( lcVend );
		txtCodVend.setNomeCampo( "CodVend" );
	}

	private void montaTela() {

		JLabelPad lbLinha = new JLabelPad();
		lbLinha.setBorder( BorderFactory.createEtchedBorder() );
		JLabelPad lbPeriodo = new JLabelPad( "Período:", SwingConstants.CENTER );
		lbPeriodo.setOpaque( true );

		adic( lbPeriodo, 17, 10, 80, 20 );
		adic( lbLinha, 7, 20, 300, 45 );
		adic( txtDataini, 17, 35, 125, 20 );
		adic( new JLabelPad( "à", SwingConstants.CENTER ), 142, 35, 30, 20 );
		adic( txtDatafim, 172, 35, 125, 20 );
		adic( new JLabelPad("Cód.Vend"),7, 65, 60, 20 );
		adic( txtCodVend, 7, 85, 60, 20 );
		adic( new JLabelPad("Nome do vendedor"),70, 65, 200, 20 );
		adic( txtDescVend, 70, 85, 235, 20 );
	
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
		StringBuffer sWhere1 = new StringBuffer();
		

		try {
			
			if( txtCodVend.getVlrInteger().intValue() > 0 ) {
				
				sWhere1.append( ( "AND V.CODVEND= " + txtCodVend.getVlrString() ));
			}
			
			sSQL.append( "SELECT V.CODVEND, V.NOMEVEND, " );
			sSQL.append( "SUM((SELECT COUNT(*) FROM VDVENDA VD2 " );
			sSQL.append( "WHERE VD2.CODEMPVD=V.CODEMP AND VD2.CODFILIALVD=V.CODFILIAL AND " );
			sSQL.append( "VD2.CODVEND=V.CODVEND AND VD2.DTEMITVENDA BETWEEN ? AND ? )) TOTVENDCOMIS, " );
			sSQL.append( "SUM( VD.VLRLIQVENDA ) TOTVENDAS, " );
			sSQL.append( "SUM( VI.QTDITVENDA ) QTDITENS, " );
			sSQL.append( "SUM(  VD.VLRLIQVENDA )/SUM( VI.QTDITVENDA ) ITEMMEDIO " );
			sSQL.append( "FROM VDVENDEDOR V, VDVENDA VD, VDITVENDA VI " );
			sSQL.append( "WHERE V.CODEMP=? AND V.CODFILIAL=? AND V.CODEMP=VD.CODEMPVD AND " );
			sSQL.append( "V.CODFILIAL=VD.CODFILIALVD AND V.CODVEND=VD.CODVEND AND " );
			sSQL.append( "VD.CODVENDA=VI.CODVENDA AND VD.CODFILIAL=VI.CODFILIAL AND " );
			sSQL.append( "VD.CODVENDA=VI.CODVENDA AND " );
			sSQL.append( "VD.DTEMITVENDA BETWEEN  ? AND ? " );
			sSQL.append( sWhere1.toString() );
			sSQL.append( "GROUP BY 1,2 " );
		
						
			ps = con.prepareStatement( sSQL.toString() );
			ps.setDate( 1, Funcoes.dateToSQLDate( txtDataini.getVlrDate() ) );
			ps.setDate( 2, Funcoes.dateToSQLDate( txtDatafim.getVlrDate() ) );
			ps.setInt( 3, Aplicativo.iCodEmp );
			ps.setInt( 4, ListaCampos.getMasterFilial( "VDVENDEDOR" ));
			ps.setDate( 5, Funcoes.dateToSQLDate( txtDataini.getVlrDate() ) );
			ps.setDate( 6, Funcoes.dateToSQLDate( txtDatafim.getVlrDate() ) );
			
			rs = ps.executeQuery();

			imprimirGrafico( bVisualizar, rs, sCab.toString() );

			rs.close();
			ps.close();

			con.commit();

		} catch ( Exception err ) {
			Funcoes.mensagemErro( this, "Erro ao montar relatório de desempenho por vendedor!\n" + err.getMessage(), true, con, err );
			err.printStackTrace();
		}
	}

	public void imprimirGrafico( final boolean bVisualizar, final ResultSet rs, final String sCab ) {

		FPrinterJob dlGr = new FPrinterJob( "relatorios/FRDesempVend.jasper", "Desempenho por vendedor", sCab, rs, null, this );

		if ( bVisualizar ) {
			dlGr.setVisible( true );
		}
		else {
			try {
				JasperPrintManager.printReport( dlGr.getRelatorio(), true );
			} catch ( Exception err ) {
				Funcoes.mensagemErro( this, "Erro na impressão de relatório de desempenho por vendedor!" + err.getMessage(), true, con, err );
			}
		}
	}

	public void setConexao( DbConnection cn ) {

		super.setConexao( cn );
		lcVend.setConexao( cn );
	}
}
