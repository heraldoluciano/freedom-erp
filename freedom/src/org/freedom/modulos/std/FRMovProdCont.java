package org.freedom.modulos.std;


import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.SwingConstants;

import net.sf.jasperreports.engine.JasperPrintManager;

import org.freedom.componentes.JLabelPad;
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
	
	public FRMovProdCont(){
		
		setTitulo(" Relatório de Movimentação Produto Controlado ");
		setAtribos( 50, 50, 345, 150 );
		
		montaTela();
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

	public void imprimir( boolean b ) {

		String sCab = "";

		sCab = "Relatório de Movimentação de Produto" + "\n" + "Policia federal CPQ-DPF" ;
		
		imprimiGrafico( true,  sCab );
	}

}
