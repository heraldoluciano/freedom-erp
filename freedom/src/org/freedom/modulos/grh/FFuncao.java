/**
 * @version 02/11/2003 <BR>
 * @author Setpoint Informática Ltda./Alex Rodrigues <BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.grh <BR>
 * Classe:
 * @(#)FFuncao.java <BR>
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
 * Tela de cadastro de funções
 * 
 */

package org.freedom.modulos.grh;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import net.sf.jasperreports.engine.JasperPrintManager;

import org.freedom.funcoes.Funcoes;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.JTextFieldPad;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FDados;
import org.freedom.telas.FPrinterJob;

public class FFuncao extends FDados implements ActionListener {

	private static final long serialVersionUID = 1L;

	private final JTextFieldPad txtCodFunc = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 5, 0 );

	private final JTextFieldPad txtDescFunc = new JTextFieldPad( JTextFieldPad.TP_STRING, 40, 0 );
	
	private final JTextFieldPad txtCbo = new JTextFieldPad( JTextFieldPad.TP_STRING, 10, 0 );
	

	public FFuncao() {

		super();
		setTitulo( "Cadastro de funções" );
		setAtribos( 50, 50, 430, 125 );
		
		montaTela();
		
		btImp.addActionListener( this );
		btPrevimp.addActionListener( this );
		
		setImprimir( true );
	}
	
	private void montaTela() {

		adicCampo( txtCodFunc, 7, 20, 70, 20, "CodFunc", "Cód.func.", ListaCampos.DB_PK, true );
		adicCampo( txtDescFunc, 80, 20, 250, 20, "DescFunc", "Descrição da função", ListaCampos.DB_SI, true );
		adicCampo( txtCbo, 335, 20, 70, 20, "CboFunc", "Cbo", ListaCampos.DB_SI, false );
		setListaCampos( true, "FUNCAO", "RH" );		
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
		hParam.put( "CODFILIAL", ListaCampos.getMasterFilial( "RHFUNCAO" ) );

		dlGr = new FPrinterJob( "relatorios/grhFuncao.jasper", "Lista de Funções", "", this, hParam, con, null, false );

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
