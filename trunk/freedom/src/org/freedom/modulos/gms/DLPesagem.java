/**
 * @version 14/07/2003 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.std <BR>
 * Classe:
 * @(#)DLBaixaRec.java <BR>
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
 */

package org.freedom.modulos.gms;


import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.math.BigDecimal;
import java.util.Date;

import org.freedom.acao.CarregaEvent;
import org.freedom.acao.CarregaListener;
import org.freedom.acao.EditEvent;
import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.funcoes.Funcoes;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.telas.FFDialogo;
import org.freedom.telas.SwingParams;

public class DLPesagem extends FFDialogo implements CarregaListener, FocusListener  {

	private static final long serialVersionUID = 1L;

	private JTextFieldPad txtPeso = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 15, 2 );

	private JTextFieldPad txtData = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );
	
	private JTextFieldPad txtHora = new JTextFieldPad( JTextFieldPad.TP_TIME, 8, 0 );

	public DLPesagem( Component cOrig ) {

		super( cOrig );
		
		setTitulo( "Pesagem" );
		setAtribos( 350, 250 );

		ajustaCampos();
		carregaPadroes();
		txtPeso.setVlrBigDecimal( new BigDecimal( 0 ) );
		
		adic( new JLabelPad( "Peso" ), 7, 0, 322, 20 );
		adic( txtPeso, 7, 20, 322, 50 );
		
		adic( new JLabelPad( "Data" ), 7, 80, 140, 20 );
		adic( txtData, 7, 100, 140, 50 );
		
		adic( new JLabelPad( "Hora" ), 150, 80, 120, 20 );
		adic( txtHora, 150, 100, 120, 50 );
		
	}
	
	private void ajustaCampos() {
		
		txtData.setAtivo( false );
		txtHora.setAtivo( false );
		
		txtPeso.setFont( SwingParams.getFontboldextra(20) );
		txtData.setFont( SwingParams.getFontboldextra(10) );
		txtHora.setFont( SwingParams.getFontboldextra(10) );
		
	}
	
	private void carregaPadroes() {
		
		Date dataatual = new Date();
		txtData.setVlrDate( dataatual );
		txtHora.setVlrTime( dataatual );		
		
	}

	public BigDecimal getPeso() {
		return txtPeso.getVlrBigDecimal();
	}
	
	public Date getData() {
		return txtData.getVlrDate();
	}
	
	public String getHora() {
		return Funcoes.dateToStrTime( txtHora.getVlrDate() );
	}
	
	public void actionPerformed( ActionEvent evt ) {

		if ( evt.getSource() == btOK ) {
				carregaPadroes();
				super.actionPerformed( evt );			
		}
		else {
			super.actionPerformed( evt );
		}
	}

	public void focusGained( FocusEvent fevt ) { }

	public void focusLost( FocusEvent fevt ) {

	}

	public void beforeCarrega( CarregaEvent cevt ) {

	}

	public void afterCarrega( CarregaEvent cevt ) {

	}

	public void beforeEdit( EditEvent eevt ) { }

	public void afterEdit( EditEvent eevt ) { }

	public void setConexao( DbConnection cn ) {
	
		super.setConexao( cn );
	}

}
