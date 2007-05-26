/**
 * @version 05/2007 <BR>
 * @author Setpoint Informática Ltda.<BR>
 * @author Alex Rodrigues<BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.lvf <BR>
 * Classe:
 * @(#)FRRegistroSaida.java <BR>
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
 * Comentario sobre a classe.
 * 
 */

package org.freedom.modulos.lvf;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;
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

public class FRRegitroSaida extends FRelatorio {

	private static final long serialVersionUID = 1;

	private final JTextFieldPad txtDtIni = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private final JTextFieldPad txtDtFim = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private final JTextFieldPad txtPaginaIncial = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	public FRRegitroSaida() {

		super();
		setTitulo( "Registro de Saida" );		
		setAtribos( 50, 50, 295, 170 );
		
		montaTela();

		Calendar cal = Calendar.getInstance();
		txtDtFim.setVlrDate( cal.getTime() );
		cal.set( Calendar.MONTH, cal.get( Calendar.MONTH ) - 1 );
		txtDtIni.setVlrDate( cal.getTime() );
		
		txtPaginaIncial.setRequerido( true );
	}
	
	private void montaTela() {
		
		JLabel bordaData = new JLabel();
		bordaData.setBorder( BorderFactory.createEtchedBorder() );
		JLabel periodo = new JLabel( "Periodo", SwingConstants.CENTER );
		periodo.setOpaque( true );
	
		adic( periodo, 20, 0, 80, 20 );
		adic( txtDtIni, 20, 20, 100, 20 );
		adic( new JLabel( "até", SwingConstants.CENTER ), 120, 20, 40, 20 );
		adic( txtDtFim, 160, 20, 100, 20 );
		adic( bordaData, 10, 10, 260, 40 );
		
		adic( new JLabel( "Página Inicial" ), 10, 50, 100, 20 );
		adic( txtPaginaIncial, 10, 70, 100, 20 );
	}

	@ Override
	public void imprimir( boolean visualizar ) {

		if ( txtDtIni.getVlrDate() != null && txtDtFim.getVlrDate() != null ) {
			if ( txtDtFim.getVlrDate().before( txtDtIni.getVlrDate() ) ) {
				Funcoes.mensagemInforma( this, "Data final inferior a inicial!" );
				txtDtIni.requestFocus();
				return;
			}
		}
		
		if ( txtPaginaIncial.getVlrInteger() < 1 ) {
			Funcoes.mensagemInforma( this, "Páginal incial não informada!" );
			txtPaginaIncial.requestFocus();
			return;
		}
		
		try {
			
			StringBuilder sql = new StringBuilder();
			
			sql.append( "SELECT L.CODLF, L.TIPOLF, L.ANOMESLF, L.ESPECIELF, L.DOCINILF, L.SERIELF," );
			sql.append( "L.CODNAT, L.DTESLF, L.DTEMITLF, L.VLRCONTABILLF, L.VLRBASEICMSLF," );
			sql.append( "L.ALIQICMSLF, L.VLRICMSLF, L.VLRISENTASICMSLF, L.VLROUTRASICMSLF," );
			sql.append( "L.VLRBASEIPILF, L.ALIQIPILF, L.VLRIPILF, L.VLRISENTASIPILF," );
			sql.append( "L.VLROUTRASIPILF, L.CODEMITLF, L.UFLF, L.DOCFIMLF, L.OBSLF " );
			sql.append( "FROM LFLIVROFISCAL L " );
			sql.append( "WHERE L.CODEMP=? AND L.CODFILIAL=? AND L.TIPOLF='E' " );
			sql.append( "AND L.DTEMITLF BETWEEN ? AND ? " );
			sql.append( "ORDER BY L.DTEMITLF, L.DOCINILF, L.SERIELF, L.CODEMITLF, L.CODNAT " );
			
			PreparedStatement ps = con.prepareStatement( sql.toString() );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "LFLIVROFISCAL" ) );
			ps.setDate( 3, Funcoes.dateToSQLDate( txtDtIni.getVlrDate() ) );
			ps.setDate( 4, Funcoes.dateToSQLDate( txtDtFim.getVlrDate() ) );
			ResultSet rs = ps.executeQuery();
			
			HashMap<String,Object> hParam = new HashMap<String, Object>();

			hParam.put( "CODEMP", Aplicativo.iCodEmp );
			//hParam.put( "SUBREPORT_DIR", "/opt/freedom/reports/" );
			hParam.put( "REPORT_CONNECTION", con );
			
			FPrinterJob dlGr = new FPrinterJob( "relatorios/RegistroSaida.jasper", "REGISTRO DE SAIDAS", null, rs, hParam, this );

			if ( visualizar ) {
				dlGr.setVisible( true );
			}
			else {
				JasperPrintManager.printReport( dlGr.getRelatorio(), true );
			}
			
			dispose();
		} catch ( Exception e ) {
			Funcoes.mensagemErro( this, "Erro ao montar relatorio!\n" + e.getMessage() );
			e.printStackTrace();
		}

	}

}
