/**
 * @version 03/10/2008 <BR>
 * @author Setpoint Informática Ltda./Felipe Daniel Elias <BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.grh <BR>
 * Classe:
 * @(#)FFuncao.java <BR>
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
 * Tela de cadastro de funções
 * 
 */

package org.freedom.modulos.grh;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import net.sf.jasperreports.engine.JasperPrintManager;

import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FDados;
import org.freedom.telas.FPrinterJob;

public class FBeneficio extends FDados implements ActionListener {

	private static final long serialVersionUID = 1L;

	private final JTextFieldPad txtCodBenef = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 5, 0 );

	private final JTextFieldPad txtDescBenef = new JTextFieldPad( JTextFieldPad.TP_STRING, 60, 0 );
	
	private final JTextFieldPad txtVlrBenef = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, 2 );
	

	public FBeneficio() {

		super();
		setTitulo( "Cadastro de Benefícios" );
		setAtribos( 50, 50, 470, 125 );
		
		montaTela();
		
		btImp.addActionListener( this );
		btPrevimp.addActionListener( this );
		
		setImprimir( true );
	}
	
	private void montaTela() {

		adicCampo( txtCodBenef, 7, 20, 70, 20, "CodBenef", "Cód.benef.", ListaCampos.DB_PK, true );
		adicCampo( txtDescBenef, 80, 20, 260, 20, "DescBenef", "Descrição do benefício", ListaCampos.DB_SI, true );
		adicCampo( txtVlrBenef, 345, 20, 100, 20, "ValorBenef", "Valor( R$ )", ListaCampos.DB_SI, false );
		setListaCampos( true, "BENEFICIO", "RH" );		
		lcCampos.setQueryInsert( false );
	}

	public void actionPerformed( ActionEvent evt ) {

		if ( evt.getSource() == btPrevimp ) {
			imprimir( true );
		}
		else if ( evt.getSource() == btImp ) {
			imprimir( false );
		}
		
		super.actionPerformed( evt );
	}

	private void imprimir( boolean bVisualizar ) {
		
		FPrinterJob dlGr = null;
		HashMap<String, Object> hParam = new HashMap<String, Object>();

		hParam.put( "CODEMP", Aplicativo.iCodEmp );
		hParam.put( "CODFILIAL", ListaCampos.getMasterFilial( "RHBENEFICIO" ) );

		dlGr = new FPrinterJob( "relatorios/grhFuncao.jasper", "Lista de Benefícios", "", this, hParam, con, null, false );

		if ( bVisualizar ) {
			dlGr.setVisible( true );
		}
		else {
			try {
				JasperPrintManager.printReport( dlGr.getRelatorio(), true );
			} catch ( Exception e ) {
				e.printStackTrace();
				Funcoes.mensagemErro( this, "Erro na geração do relátorio!" + e.getMessage(), true, con, e );
			}
		}
	}
}
