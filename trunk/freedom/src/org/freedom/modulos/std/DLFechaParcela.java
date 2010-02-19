/**
 * @version 14/07/2003 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.std <BR>
 * Classe:
 * @(#)DLFechaRec.java <BR>
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

package org.freedom.modulos.std;

import java.awt.Component;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.math.BigDecimal;
import org.freedom.infra.model.jdbc.DbConnection;
import java.util.Date;

import org.freedom.acao.CarregaEvent;
import org.freedom.acao.CarregaListener;
import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JCheckBoxPad;
import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FFDialogo;

public class DLFechaParcela extends FFDialogo implements CarregaListener, FocusListener {

	private static final long serialVersionUID = 1L;

	private JTextFieldPad txtParcItRec = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 15, Aplicativo.casasDecFin );

	private JTextFieldPad txtDtVencItRec = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );
	
	private JTextFieldPad txtDtPrevItRec = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtVlrDescItRec = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, Aplicativo.casasDecFin );

	private JTextFieldPad txtCodTipoCob = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtDescTipoCob = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );
	
	private JTextFieldPad txtObrigCart = new JTextFieldPad( JTextFieldPad.TP_STRING, 1, 0 );

	private JTextFieldPad txtCodBanco = new JTextFieldPad( JTextFieldPad.TP_STRING, 3, 0 );

	private JTextFieldFK txtDescBanco = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtCodCartCob = new JTextFieldPad( JTextFieldPad.TP_STRING, 2, 0 );

	private JTextFieldFK txtDescCartCob = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private ListaCampos lcTipoCob = new ListaCampos( this, "CO" );

	private ListaCampos lcBanco = new ListaCampos( this, "BO" );

	private ListaCampos lcCartCob = new ListaCampos( this, "BO" );
	
	private JCheckBoxPad cbDescPont = new JCheckBoxPad( "Desconto pontualidade?", "S", "N" );
	

	public DLFechaParcela( Component cOrig, DbConnection cn ) {

		super( cOrig );
		setTitulo( "Parcela" );
		setAtribos( 100, 100, 345, 275 );
		setConexao( cn );

		montaListaCampos();
		montaTela();

		lcTipoCob.addCarregaListener( this );
		txtCodTipoCob.addFocusListener( this );
	}
	
	private void montaListaCampos() {
		
		/***************
		 *  FNTIPOCOB  *
		 ***************/
		lcTipoCob.add( new GuardaCampo( txtCodTipoCob, "CodTipoCob", "Cód.Tip.Cob", ListaCampos.DB_PK, false ) );
		lcTipoCob.add( new GuardaCampo( txtDescTipoCob, "DescTipoCob", "Descrição Tipo de Cobrança", ListaCampos.DB_SI, false ) );
		lcTipoCob.add( new GuardaCampo( txtObrigCart, "ObrigCartCob", "Obriga cart.cob.", ListaCampos.DB_SI, false ) );
		lcTipoCob.setQueryCommit( false );
		lcTipoCob.setReadOnly( true );
		lcTipoCob.montaSql( false, "TIPOCOB", "FN" );
		txtCodTipoCob.setTabelaExterna( lcTipoCob );
		txtCodTipoCob.setFK( true );
		txtCodTipoCob.setNomeCampo( "CodTipoCob" );

		/*************
		 *  FNBANCO  *
		 *************/
		lcBanco.add( new GuardaCampo( txtCodBanco, "CodBanco", "Cód.banco", ListaCampos.DB_PK, false ) );
		lcBanco.add( new GuardaCampo( txtDescBanco, "NomeBanco", "Nome do banco", ListaCampos.DB_SI, false ) );
		lcBanco.setQueryCommit( false );
		lcBanco.setReadOnly( true );
		lcBanco.montaSql( false, "BANCO", "FN" );
		txtCodBanco.setTabelaExterna( lcBanco );
		txtCodBanco.setFK( true );
		txtCodBanco.setNomeCampo( "CodBanco" );
		
		/***************
		 *  FNCARTCOB  *
		 ***************/
		lcCartCob.add( new GuardaCampo( txtCodCartCob, "CodCartCob", "Cód.Cart.Cob.", ListaCampos.DB_PK, txtDescCartCob, false ) );
		lcCartCob.add( new GuardaCampo( txtDescCartCob, "DescCartCob", "Descrição da carteira de cobrança", ListaCampos.DB_SI, false ) );
		//lcCartCob.add( new GuardaCampo( txtCodBanco, "CodBanco", "Cód.banco", ListaCampos.DB_FK, false ) );
		lcCartCob.setQueryCommit( false );
		lcCartCob.setReadOnly( true );
		lcCartCob.montaSql( false, "CARTCOB", "FN" );
		txtCodCartCob.setTabelaExterna( lcCartCob );
		txtCodCartCob.setFK( true );
		txtCodCartCob.setNomeCampo( "CodCartCob" );	
	}

	private void montaTela() {

		adic( new JLabelPad( "Valor" ), 7, 0, 80, 20 );
		adic( txtParcItRec, 7, 20, 80, 20 );
		
		adic( new JLabelPad( "Vencimento" ), 90, 0, 75, 20 );
		adic( txtDtVencItRec, 90, 20, 75, 20 );

		adic( new JLabelPad( "Previsão" ), 168, 0, 75, 20 );
		adic( txtDtPrevItRec, 168, 20, 75, 20 );
		
		adic( new JLabelPad( "Desconto" ), 246, 0, 75, 20 );
		adic( txtVlrDescItRec, 246, 20, 75, 20 );	
		
		adic( new JLabelPad( "Cód.Tp.Cob" ), 7, 40, 80, 20 );
		adic( txtCodTipoCob, 7, 60, 80, 20 );
		adic( new JLabelPad( "Descrição do tipo cobrança" ), 90, 40, 230, 20 );
		adic( txtDescTipoCob, 90, 60, 230, 20 );
		adic( new JLabelPad( "Cod.Banco" ), 7, 80, 80, 20 );
		adic( txtCodBanco, 7, 100, 80, 20 );
		adic( new JLabelPad( "Descrição do banco" ), 90, 80, 230, 20 );
		adic( txtDescBanco, 90, 100, 230, 20 );
		adic( new JLabelPad( "Cod.Cart.Cob." ), 7, 120, 80, 20 );
		adic( txtCodCartCob, 7, 140, 80, 20 );
		adic( new JLabelPad( "Descrição da carteira de cobrança" ), 90, 120, 230, 20 );
		adic( txtDescCartCob, 90, 140, 230, 20 );
		adic( cbDescPont, 7, 165, 200, 20 );
		cbDescPont.setVlrString( "S" );
	}
	
	public void setValores( Object[] args ) {
		
		if ( args != null ) {
			
			txtParcItRec.setVlrBigDecimal( (BigDecimal) args[ EFields.VALOR.ordinal() ] );
			txtDtVencItRec.setVlrDate( (Date) args[ EFields.DATA.ordinal() ] );
			
			txtDtPrevItRec.setVlrDate( (Date) args[ EFields.DATAPREV.ordinal() ] );			
			
			txtVlrDescItRec.setVlrBigDecimal( (BigDecimal) args[ EFields.DESCONTO.ordinal() ] );
			txtCodTipoCob.setVlrInteger( (Integer) args[ EFields.TIPOCOB.ordinal() ] );
			txtCodBanco.setVlrString( (String) args[ EFields.BANCO.ordinal() ] );
			txtCodCartCob.setVlrString( (String) args[ EFields.CARTCOB.ordinal() ] );
			cbDescPont.setVlrString( (String) args[ EFields.DESCPONT.ordinal() ] );

			if ( txtVlrDescItRec.getVlrBigDecimal() == null ) {
				txtVlrDescItRec.setAtivo( false );
			}
			
			lcTipoCob.carregaDados();
			String codbanco = txtCodBanco.getVlrString();
			lcCartCob.carregaDados();
			txtCodBanco.setVlrString( codbanco );
			lcBanco.carregaDados();
		}
	}

	public Object[] getValores() {

		Object[] oRetorno = new Object[ EFields.values().length ];
		oRetorno[ DLFechaParcela.EFields.VALOR.ordinal() ] = txtParcItRec.getVlrBigDecimal();
		oRetorno[ DLFechaParcela.EFields.DATA.ordinal() ] = txtDtVencItRec.getVlrDate();
		oRetorno[ DLFechaParcela.EFields.DESCONTO.ordinal() ] = txtVlrDescItRec.getVlrBigDecimal();
		oRetorno[ DLFechaParcela.EFields.TIPOCOB.ordinal() ] = txtCodTipoCob.getVlrInteger()!=0 ? String.valueOf( txtCodTipoCob.getVlrInteger() ) : "";
		oRetorno[ DLFechaParcela.EFields.BANCO.ordinal() ] = txtCodBanco.getVlrString();
		oRetorno[ DLFechaParcela.EFields.CARTCOB.ordinal() ] = txtCodCartCob.getVlrString();
		oRetorno[ DLFechaParcela.EFields.DESCPONT.ordinal() ] = cbDescPont.getVlrString();
//		oRetorno[ DLFechaParcela.EFields.CODCONTA.ordinal() ] = txt.getVlrString();
		oRetorno[ DLFechaParcela.EFields.DATAPREV.ordinal() ] = txtDtPrevItRec.getVlrDate();

		return oRetorno;
	}
	
	private boolean isValido() {
		
		if ( "S".equalsIgnoreCase( txtObrigCart.getVlrString() ) ) {
			if ( txtCodBanco.getVlrString().trim().length() == 0 ) {
				Funcoes.mensagemErro( this, "Cód.banco é requerido!" );
				txtCodBanco.requestFocus();
				return false;
			}
			else if ( txtCodCartCob.getVlrString().trim().length() == 0 ) {
				Funcoes.mensagemErro( this, "Cód.cart.cob. é requerido!" );
				txtCodCartCob.requestFocus();
				return false;
			}
		}
		
		return true;
	}
	
	@ Override
	public void ok() {

		if ( isValido() ) {
			super.ok();
		}
	}
			
	public void focusGained( FocusEvent e ) { }

	public void focusLost( FocusEvent e ) {
		
		if ( e.getSource() == txtCodTipoCob ) { 
			
			lcTipoCob.carregaDados();
			
			if ( ! "S".equalsIgnoreCase( txtObrigCart.getVlrString() ) ) {
				txtCodBanco.setRequerido( false );
				txtCodCartCob.setRequerido( false );
			}
		}
	}

	public void afterCarrega( CarregaEvent e ) {
		
		if ( e.getListaCampos() == lcTipoCob
				&& "S".equalsIgnoreCase( txtObrigCart.getVlrString() ) ) {
			txtCodBanco.setRequerido( true );
			txtCodCartCob.setRequerido( true );
		}
	}

	public void beforeCarrega( CarregaEvent e ) { }

	@ Override
	public void setConexao( DbConnection cn ) {

		super.setConexao( cn );
		lcTipoCob.setConexao( cn );
		lcBanco.setConexao( cn );
		lcCartCob.setConexao( cn );
	}

	public enum EFields {
		
		VALOR,
		DATA,
		DESCONTO,
		TIPOCOB,
		BANCO,
		CARTCOB,
		DESCPONT,
		DATAPREV
	}
}
