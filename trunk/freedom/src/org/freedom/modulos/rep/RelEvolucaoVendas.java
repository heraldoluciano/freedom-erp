/**
 * @version 05/2007 <BR>
 * @author Setpoint Informática Ltda.<BR>
 * @author Alex Rodrigues<BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.rep <BR>
 * Classe:
 * @(#)RelEvolcaoVendas.java <BR>
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
 * Relatorio de evolução de vendas, em gráficos pizza e em barras.
 * 
 */

package org.freedom.modulos.rep;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import net.sf.jasperreports.engine.JasperPrintManager;

import org.freedom.bmps.Icone;
import org.freedom.componentes.JRadioGroup;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FPrinterJob;
import org.freedom.telas.FRelatorio;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

public class RelEvolucaoVendas extends FRelatorio {

	private static final long serialVersionUID = 1;

	private final JTextFieldPad txtDtIni = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private final JTextFieldPad txtDtFim = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JRadioGroup rgModo;

	public RelEvolucaoVendas() {

		super();
		setTitulo( "Relatorio de Evolução de Vendas" );
		setAtribos( 100, 50, 325, 260 );

		montaRadioGrupos();
		montaTela();

		Calendar cal = Calendar.getInstance();
		txtDtFim.setVlrDate( cal.getTime() );
		cal.set( cal.get( Calendar.YEAR ), cal.get( Calendar.MONTH ) - 1, cal.get( Calendar.DATE ) );
		txtDtIni.setVlrDate( cal.getTime() );
	}

	private void montaRadioGrupos() {

		Vector<String> labs2 = new Vector<String>();
		labs2.add( "Pizza" );
		labs2.add( "Barras" );
		Vector<String> vals2 = new Vector<String>();
		vals2.add( "P" );
		vals2.add( "B" );
		rgModo = new JRadioGroup( 2, 1, labs2, vals2 );
	}

	private void montaTela() {

		adic( new JLabel( "Modo do relatório :" ), 10, 10, 200, 20 );
		adic( new JLabel( Icone.novo( "graficoPizza.gif" ) ), 10, 40, 30, 30 );
		adic( new JLabel( Icone.novo( "graficoBarra.gif" ) ), 10, 70, 30, 30 );
		adic( rgModo, 25, 35, 275, 70 );

		JLabel periodo = new JLabel( "Periodo", SwingConstants.CENTER );
		periodo.setOpaque( true );
		adic( periodo, 25, 110, 60, 20 );

		JLabel borda = new JLabel();
		borda.setBorder( BorderFactory.createEtchedBorder() );
		adic( borda, 10, 130, 290, 45 );

		adic( txtDtIni, 25, 145, 110, 20 );
		adic( new JLabel( "até", SwingConstants.CENTER ), 135, 145, 40, 20 );
		adic( txtDtFim, 175, 145, 110, 20 );
	}

	@ Override
	public void imprimir( boolean visualizar ) {

		if ( txtDtIni.getVlrDate() != null && txtDtFim.getVlrDate() != null ) {
			if ( txtDtFim.getVlrDate().before( txtDtIni.getVlrDate() ) ) {
				Funcoes.mensagemInforma( this, "Data final inferior a inicial!" );
				return;
			}
		}

		try {

			String relatorio = "B".equals( rgModo.getVlrString() ) ? "rpevolucaovendasbar.jasper" : "rpevolucaovendaspizza.jasper";

			Date dtini = txtDtIni.getVlrDate();
			Date dtfim = txtDtFim.getVlrDate();

			StringBuilder sql = new StringBuilder();

			sql.append( "SELECT SUM(P.VLRLIQPED) AS VALOR, " );
			sql.append( "( CASE EXTRACT(MONTH FROM P.DATAPED) " );
			sql.append( "WHEN 1 THEN 'Janeiro' " );
			sql.append( "WHEN 2 THEN 'Fevereiro' " );
			sql.append( "WHEN 3 THEN 'Março' " );
			sql.append( "WHEN 4 THEN 'Abril' " );
			sql.append( "WHEN 5 THEN 'Maio' " );
			sql.append( "WHEN 6 THEN 'Junho' " );
			sql.append( "WHEN 7 THEN 'Julho' " );
			sql.append( "WHEN 8 THEN 'Agosto' " );
			sql.append( "WHEN 9 THEN 'Setembro' " );
			sql.append( "WHEN 10 THEN 'Outubro' " );
			sql.append( "WHEN 11 THEN 'Novembro' " );
			sql.append( "WHEN 12 THEN 'Dezembro' END ) AS MES " );
			sql.append( "FROM RPPEDIDO P " );
			sql.append( "WHERE P.CODEMP=? AND P.CODFILIAL=? " );
			sql.append( "AND P.DATAPED BETWEEN ? AND ? " );
			sql.append( "GROUP BY 2 " );

			PreparedStatement ps = con.prepareStatement( sql.toString() );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "RPPEDIDO" ) );
			ps.setDate( 3, Funcoes.dateToSQLDate( dtini ) );
			ps.setDate( 4, Funcoes.dateToSQLDate( dtfim ) );
			ResultSet rs = ps.executeQuery();

			HashMap<String, Object> hParam = new HashMap<String, Object>();

			hParam.put( "CODEMP", Aplicativo.iCodEmp );
			hParam.put( "CODFILIAL", ListaCampos.getMasterFilial( "RPPEDIDO" ) );
			hParam.put( "SUBREPORT_DIR", RelEvolucaoVendas.class.getResource( "relatorios/" ).getPath() );
			hParam.put( "REPORT_CONNECTION", con );
			hParam.put( "GRAFICO", getGrafico( rs, rgModo.getVlrString() ) );

			FPrinterJob dlGr = new FPrinterJob( "modulos/rep/relatorios/" + relatorio, "EVOLUÇÃO DE VENDAS", null, this, hParam, con );

			if ( visualizar ) {
				dlGr.setVisible( true );
			}
			else {
				JasperPrintManager.printReport( dlGr.getRelatorio(), true );
			}

		} catch ( Exception e ) {
			Funcoes.mensagemErro( this, "Erro ao montar relatorio!\n" + e.getMessage() );
			e.printStackTrace();
		}

	}

	private Image getGrafico( final ResultSet rs, final String modo ) {

		Image grafico = null;

		try {

			BufferedImage bufferedimage = null;
			DefaultPieDataset pizza = new DefaultPieDataset();
			DefaultCategoryDataset barra = new DefaultCategoryDataset();
			JFreeChart jfreechart = null;

			if ( "P".equals( modo ) ) {

				while ( rs.next() ) {

					pizza.setValue( rs.getString( "MES" ), rs.getDouble( "VALOR" ) );
				}

				jfreechart = ChartFactory.createPieChart3D( "", pizza, true, false, false );
			}
			else if ( "B".equals( modo ) ) {

				while ( rs.next() ) {

					barra.setValue( 
							rs.getDouble( "VALOR" ), 
							rs.getString( "MES" ) + "(" + Funcoes.strDecimalToStrCurrency( 12, 2, rs.getString( "VALOR" ) ) + ")", 
							"" );
				}

				jfreechart = ChartFactory.createBarChart3D( "", "Meses", "Valores", barra, PlotOrientation.VERTICAL, true, false, false );
			}

			jfreechart.setBackgroundPaint( new Color( 255, 255, 255 ) );
			
			Plot plot = jfreechart.getPlot();
			plot.setForegroundAlpha( 0.6f );

			bufferedimage = jfreechart.createBufferedImage( 770, 432 );
			grafico = bufferedimage.getScaledInstance( 770, 432, Image.SCALE_SMOOTH );

		} catch ( Exception e ) {
			Funcoes.mensagemErro( this, "Erro ao montar grafico!\n" + e.getMessage() );
			e.printStackTrace();
		}

		return grafico;
	}

	public void setConexao( Connection cn ) {

		super.setConexao( cn );
	}

}
