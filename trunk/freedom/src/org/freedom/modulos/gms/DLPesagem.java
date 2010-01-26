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

	private JTextFieldPad txtPeso1 = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 15, 2 );
	
	private JTextFieldPad txtPeso2 = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 15, 2 );

	private JTextFieldPad txtData = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );
	
	private JTextFieldPad txtHora = new JTextFieldPad( JTextFieldPad.TP_TIME, 8, 0 );
	
	private JLabelPad lbpeso1 = new JLabelPad(( "Peso 1" ));
	
	private JLabelPad lbpeso2 = new JLabelPad(( "Peso 2" ));	

	public DLPesagem( Component cOrig, String tipoprocrecmerc ) {

		super( cOrig );
		int irow = 0; // Variavel para salto na posição do campo de data e hora
		
		setTitulo( "Pesagem" );
		
		ajustaCampos();
		carregaPadroes();
		txtPeso1.setVlrBigDecimal( new BigDecimal( 0 ) );
		
		adic( lbpeso1 , 7, 0, 252, 20 );
		adic( txtPeso1, 7, 20, 252, 50 );

		if( FTipoRecMerc.DESCARREGAMENTO.equals( tipoprocrecmerc ) ) {
			
			adic( lbpeso2 , 7, 80, 252, 20 );
			adic( txtPeso2, 7, 100, 252, 50 );
			
			irow = 80;
		}

		
		setAtribos( 280, 250 + irow );		
		
		adic( new JLabelPad( "Data" ), 7, 80 + irow, 135, 20 );
		adic( txtData, 7, 100 + irow, 135, 50 );
		
		adic( new JLabelPad( "Hora" ), 147, 80 + irow, 110, 20 );
		adic( txtHora, 147, 100 + irow, 110, 50 );
		
	}
	
	private void ajustaCampos() {
		
		txtData.setAtivo( false );
		txtHora.setAtivo( false );
		
		txtPeso1.setFont( SwingParams.getFontboldextra(20) );
		txtData.setFont( SwingParams.getFontboldextra(10) );
		txtHora.setFont( SwingParams.getFontboldextra(10) );
		
	}
	
	private void carregaPadroes() {
		
		Date dataatual = new Date();
		txtData.setVlrDate( dataatual );
		txtHora.setVlrTime( dataatual );		
		
	}

	public BigDecimal getPeso1() {
		return txtPeso1.getVlrBigDecimal();
	}

	public BigDecimal getPeso2() {
		return txtPeso2.getVlrBigDecimal();
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
