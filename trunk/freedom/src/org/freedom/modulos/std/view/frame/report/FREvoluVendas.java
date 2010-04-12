/**
 * @version 08/12/2000 <BR>
 * @author Setpoint Informática Ltda./Anderson Sanchez <BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.std <BR>
 * Classe:
 * @(#)FREvoluVendas.java <BR>
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
 * Tela de opções para o gráfico de evolução de vendas.
 * 
 */

package org.freedom.modulos.std.view.frame.report;

import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.layout.graphics.EvoluVendasBarras;
import org.freedom.layout.graphics.EvoluVendasLinha;
import org.freedom.library.persistence.GuardaCampo;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.JCheckBoxPad;
import org.freedom.library.swing.JLabelPad;
import org.freedom.library.swing.JRadioGroup;
import org.freedom.library.swing.JTextFieldFK;
import org.freedom.library.swing.JTextFieldPad;
import org.freedom.library.swing.frame.Aplicativo;
import org.freedom.library.swing.frame.FPrinterJob;
import org.freedom.library.swing.frame.FRelatorio;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Calendar;
import java.util.Vector;

import javax.swing.BorderFactory;

import org.freedom.bmps.Icone;
import org.freedom.funcoes.Funcoes;

public class FREvoluVendas extends FRelatorio {

	private static final long serialVersionUID = 1L;

	private JTextFieldPad txtDataini = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtDatafim = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private FPrinterJob dl = null;

	private JRadioGroup<?, ?> rgGrafico = null;

	private Vector<String> vLabs = new Vector<String>( 2 );

	private Vector<String> vVals = new Vector<String>( 2 );

	private JTextFieldPad txtCodTipoCli = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtDescTipoCli = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private ListaCampos lcTipoCli = new ListaCampos( this, "TI" );

	private JTextFieldPad txtCodCli = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtRazCli = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private ListaCampos lcCli = new ListaCampos( this, "TI" );

	private JCheckBoxPad cbVendas = new JCheckBoxPad( "Só vendas?", "S", "N" );

	public FREvoluVendas() {

		setTitulo( "Evolução de vendas" );
		setAtribos( 80, 80, 330, 380 );

		lcTipoCli.add( new GuardaCampo( txtCodTipoCli, "CodTipoCli", "Cód.tp.cli.", ListaCampos.DB_PK, false ) );
		lcTipoCli.add( new GuardaCampo( txtDescTipoCli, "DescTipoCli", "Descrição do tipo do cliente", ListaCampos.DB_SI, false ) );
		lcTipoCli.montaSql( false, "TIPOCLI", "VD" );
		lcTipoCli.setQueryCommit( false );
		lcTipoCli.setReadOnly( true );
		txtCodTipoCli.setTabelaExterna( lcTipoCli );
		txtCodTipoCli.setFK( true );
		txtCodTipoCli.setNomeCampo( "CodTipoCli" );

		lcCli.add( new GuardaCampo( txtCodCli, "CodCli", "Cód.cli.", ListaCampos.DB_PK, false ) );
		lcCli.add( new GuardaCampo( txtRazCli, "RazCli", "Razão social do cliente", ListaCampos.DB_SI, false ) );
		lcCli.montaSql( false, "CLIENTE", "VD" );
		lcCli.setQueryCommit( false );
		lcCli.setReadOnly( true );
		txtCodCli.setTabelaExterna( lcCli );
		txtCodCli.setFK( true );
		txtCodCli.setNomeCampo( "CodCli" );

		Calendar cPeriodo = Calendar.getInstance();
		txtDatafim.setVlrDate( cPeriodo.getTime() );
		cPeriodo.set( Calendar.DAY_OF_MONTH, 1 );
		cPeriodo.set( Calendar.MONTH, 0 );
		txtDataini.setVlrDate( cPeriodo.getTime() );
		cPeriodo.set( Calendar.DAY_OF_MONTH, 31 );
		cPeriodo.set( Calendar.MONTH, 11 );
		txtDatafim.setVlrDate( cPeriodo.getTime() );

		adic( new JLabelPad( "Período:" ), 7, 5, 120, 20 );
		adic( new JLabelPad( "De:" ), 7, 25, 30, 20 );
		adic( txtDataini, 40, 25, 97, 20 );
		adic( new JLabelPad( "A:" ), 140, 25, 17, 20 );
		adic( txtDatafim, 160, 25, 100, 20 );

		adic( new JLabelPad( "Cód.cli." ), 7, 45, 300, 20 );
		adic( txtCodCli, 7, 65, 60, 20 );
		adic( new JLabelPad( "Razão social do cliente" ), 70, 45, 300, 20 );
		adic( txtRazCli, 70, 65, 200, 20 );

		adic( new JLabelPad( "Cód.tp.cli." ), 7, 85, 300, 20 );
		adic( txtCodTipoCli, 7, 105, 60, 20 );
		adic( new JLabelPad( "Descrição do tipo de cliente" ), 70, 85, 300, 20 );
		adic( txtDescTipoCli, 70, 105, 200, 20 );

		cbVendas.setVlrString( "S" );
		adic( cbVendas, 5, 130, 265, 25 );

		adic( new JLabelPad( "Tipo de gráfico:" ), 7, 160, 220, 20 );
		adic( new JLabelPad( Icone.novo( "graficoBarra.png" ) ), 7, 190, 30, 30 );
		adic( new JLabelPad( Icone.novo( "graficoLinha.gif" ) ), 7, 230, 30, 30 );

		vLabs.addElement( "Barras 3D" );
		vLabs.addElement( "Linha horizontal" );
		vVals.addElement( "B" );
		vVals.addElement( "L" );
		rgGrafico = new JRadioGroup<String, String>( 2, 1, vLabs, vVals );
		rgGrafico.setVlrString( "B" );
		rgGrafico.setBorder( BorderFactory.createEmptyBorder() );
		adic( rgGrafico, 42, 193, 200, 82 );

	}

	public void setConexao( DbConnection cn ) {

		super.setConexao( cn );
		lcTipoCli.setConexao( con );
		lcCli.setConexao( con );
	}

	private ResultSet buscaValores() {

		PreparedStatement ps = null;
		ResultSet rs = null;
		
		java.sql.Date dataIni = Funcoes.dateToSQLDate( txtDataini.getVlrDate() );
		java.sql.Date dataFim = Funcoes.dateToSQLDate( txtDatafim.getVlrDate() );
		
		try {
			
			ps = con.prepareStatement( "SELECT * FROM VDEVOLUVENDAS(?,?,?,?,?,?,?)" );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, Aplicativo.iCodFilialMz );
			ps.setDate( 3, dataIni );
			ps.setDate( 4, dataFim );
			
			if ( ! "".equals( txtCodTipoCli.getVlrString() ) && "".equals( txtCodCli.getVlrString() ) ) {
				
				ps.setInt( 5, Integer.parseInt( txtCodTipoCli.getVlrString() ) );
				ps.setNull( 6, Types.INTEGER );
			}
			else {
				
				ps.setNull( 5, Types.INTEGER );
				
				if ( "".equals( txtCodTipoCli.getVlrString() ) &&  ! "".equals( txtCodCli.getVlrString() ) ) {
					
					ps.setInt( 6, Integer.parseInt( txtCodCli.getVlrString() ) );
				}
				else if ( ! "".equals( txtCodTipoCli.getVlrString() ) && ! "".equals( txtCodCli.getVlrString() ) ) {
					
					ps.setInt( 6, Integer.parseInt( txtCodCli.getVlrString() ) );
					Funcoes.mensagemInforma( this, "O filtro por tipo de cliente será desconsiderado..." );
				}
				else {
				
					ps.setNull( 6, Types.INTEGER );
				}
			}
			
			ps.setString( 7, cbVendas.getVlrString() );

			rs = ps.executeQuery();
			
		} catch ( SQLException err ) {
			err.printStackTrace();
			Funcoes.mensagemErro( this, "Erro ao buscar valores das vendas!\n" + err.getMessage(), true, con, err );
		}
		
		return rs;
	}

	public void imprimir( boolean bVisualizar ) {

		if ( txtDatafim.getVlrDate().before( txtDataini.getVlrDate() ) ) {
			
			Funcoes.mensagemInforma( this, "Data final maior que a data inicial!" );
			return;
		}
		else if ( Funcoes.contaMeses( txtDataini.getVlrDate(), txtDatafim.getVlrDate() ) < 2 ) {
			
			Funcoes.mensagemInforma( this, "Período inferior a 2 meses!" );
			return;
		}

		try {

			if ( "B".equals( rgGrafico.getVlrString() ) ) {
				
				EvoluVendasBarras evVendas = new EvoluVendasBarras();
				evVendas.setConexao( con );
				evVendas.setConsulta( buscaValores() );
				dl = new FPrinterJob( evVendas, this );
				dl.setVisible( true );
			}
			else if ( "L".equals( rgGrafico.getVlrString() ) ) {
				
				EvoluVendasLinha evVendas = new EvoluVendasLinha();
				evVendas.setConexao( con );
				evVendas.setConsulta( buscaValores() );
				dl = new FPrinterJob( evVendas, this );
				dl.setVisible( true );
			}
		} catch ( Exception err ) {
			Funcoes.mensagemInforma( this, "Não foi possível carregar relatório!\n" + err.getMessage() );
			err.printStackTrace();
		}

	}
}
