package org.freedom.modulos.std;


import java.sql.Connection;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.SwingConstants;

import net.sf.jasperreports.engine.JasperPrintManager;

import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FPrinterJob;
import org.freedom.telas.FRelatorio;

public class FRMovProdCont extends FRelatorio {
	
	private static final long serialVersionUID = 1L;
	
	private JTextFieldPad txtDataini = new JTextFieldPad(JTextFieldPad.TP_DATE,10,0);
	
	private JTextFieldPad txtDatafim = new JTextFieldPad(JTextFieldPad.TP_DATE,10,0);
	
	private JTextFieldPad txtCodProd = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 10, 0 );

	private JTextFieldFK txtDescProd = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );
	
	private ListaCampos lcProduto = new ListaCampos( this );
	
	public FRMovProdCont(){
		
		setTitulo(" Relatório de Movimentação Produto Controlado ");
		setAtribos( 50, 50, 345, 200 );
		
		montaTela();
		montaListaCampos();
	}
	private void montaTela(){
		
		JLabelPad lbLinha = new JLabelPad();
		lbLinha.setBorder( BorderFactory.createEtchedBorder() );
		JLabelPad lbPeriodo = new JLabelPad( "Periodo:" , SwingConstants.CENTER );
		lbPeriodo.setOpaque( true );
		
		adic( lbPeriodo,7, 1, 80, 20 );
		adic( lbLinha,5, 10, 300, 45 );
		
		adic( new JLabelPad("De:"), 10, 25, 30, 20 );
		adic( txtDataini, 40, 25, 97, 20 );
		adic( new JLabelPad("Até:"), 152, 25, 37, 20 );
		adic( txtDatafim, 190 ,25, 100, 20 );
		adic( new JLabelPad("Cód.Prod"), 5, 55, 70, 20 );
		adic( txtCodProd, 5, 75, 70, 20 );
		adic( new JLabelPad("Descrição do produto"), 78, 55, 200, 20 );
		adic( txtDescProd, 78, 75, 225, 20 );
	}

	public void imprimiGrafico(  final boolean bVisualizar,  String sCab ) {

		FPrinterJob dlGr = null;
		HashMap<String, Object> hParam = new HashMap<String, Object>();
		

		hParam.put( "CODEMP", Aplicativo.iCodEmp );
		hParam.put( "CODFILIAL", ListaCampos.getMasterFilial( "CPCOMPRA" ) );
		hParam.put( "RAZAOEMP", Aplicativo.sEmpSis );
		hParam.put( "FILTROS", sCab );
		hParam.put( "DATAINI", txtDataini.getVlrDate()  );
		hParam.put( "DATAFIM", txtDatafim.getVlrDate() );
		hParam.put( "CODPROD", txtCodProd.getVlrInteger() );
		
		dlGr = new FPrinterJob( "relatorios/MovProdContr.jasper", "Relatório de Movimentação Produto Controlado", sCab, this, hParam, con ); 

		if ( bVisualizar ) {
			dlGr.setVisible( true );
		}
		else {
			try {
				JasperPrintManager.printReport( dlGr.getRelatorio(), true );
			} catch ( Exception err ) {
				Funcoes.mensagemErro( this, "Erro na impressão de relatório Compras Geral!" + err.getMessage(), true, con, err );
			}
		}
	}

	private void montaListaCampos(){
		
		lcProduto.add( new GuardaCampo( txtCodProd, "CodProd", "Cód.produto", ListaCampos.DB_PK, true ) );
		lcProduto.add( new GuardaCampo( txtDescProd, "DescProd", "Descrição do produto", ListaCampos.DB_SI, false ) );
		txtCodProd.setTabelaExterna( lcProduto );
		txtCodProd.setNomeCampo( "CodProd" );
		txtCodProd.setFK( true );
		lcProduto.setReadOnly( true );
		lcProduto.montaSql( false, "PRODUTO", "EQ" );
	}
	
	public void imprimir( boolean b ) {

		String sCab = "";

		sCab = "Relatório de Movimentação de Produto" + "\n" + "Policia federal CPQ-DPF" ;
		
		imprimiGrafico( b,  sCab );
	}

	public void setConexao(Connection cn) {
		
		super.setConexao( cn );
		lcProduto.setConexao( cn );
	}
}
