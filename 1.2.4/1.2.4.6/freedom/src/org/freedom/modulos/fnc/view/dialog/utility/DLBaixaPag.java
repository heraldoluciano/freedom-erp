/**
 * @version 14/07/2003 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 * 
 *         Projeto: Freedom <BR>
 * 
 *         Pacote: org.freedom.modulos.std <BR>
 *         Classe:
 * @(#)DLBaixaPag.java <BR>
 * 
 *                     Este arquivo é parte do sistema Freedom-ERP, o Freedom-ERP é um software livre; você pode redistribui-lo e/ou <BR>
 *                     modifica-lo dentro dos termos da Licença Pública Geral GNU como publicada pela Fundação do Software Livre (FSF); <BR>
 *                     na versão 2 da Licença, ou (na sua opnião) qualquer versão. <BR>
 *                     Este programa é distribuido na esperança que possa ser util, mas SEM NENHUMA GARANTIA; <BR>
 *                     sem uma garantia implicita de ADEQUAÇÂO a qualquer MERCADO ou APLICAÇÃO EM PARTICULAR. <BR>
 *                     Veja a Licença Pública Geral GNU para maiores detalhes. <BR>
 *                     Você deve ter recebido uma cópia da Licença Pública Geral GNU junto com este programa, se não, <BR>
 *                     de acordo com os termos da LPG-PC <BR>
 * <BR>
 * 
 *                     Comentários sobre a classe...
 */

package org.freedom.modulos.fnc.view.dialog.utility;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.freedom.acao.CarregaEvent;
import org.freedom.acao.CarregaListener;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.business.object.Historico;
import org.freedom.library.functions.Funcoes;
import org.freedom.library.persistence.GuardaCampo;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.component.JTextFieldFK;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.dialog.FFDialogo;
import org.freedom.library.swing.frame.Aplicativo;
import org.freedom.modulos.fnc.library.swing.component.JTextFieldPlan;
import org.freedom.modulos.std.view.frame.crud.special.FPlanejamento;

public class DLBaixaPag extends FFDialogo implements CarregaListener {

	private static final long serialVersionUID = 1L;

	private final JTextFieldPad txtCodFor = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 10, 0 );

	private final JTextFieldPad txtRazFor = new JTextFieldPad( JTextFieldPad.TP_STRING, 50, 0 );

	private final JTextFieldPad txtCodConta = new JTextFieldPad( JTextFieldPad.TP_STRING, 10, 0 );

	private final JTextFieldPad txtDescConta = new JTextFieldPad( JTextFieldPad.TP_STRING, 50, 0 );

	private final JTextFieldPlan txtCodPlan = new JTextFieldPlan( JTextFieldPad.TP_STRING, 13, 0 );
	
	private final JTextFieldPad txtCodRedPlan = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private final JTextFieldPad txtDescPlan = new JTextFieldPad( JTextFieldPad.TP_STRING, 50, 0 );

	private final JTextFieldPad txtCodCC = new JTextFieldPad( JTextFieldPad.TP_STRING, 19, 0 );

	private final JTextFieldPad txtAnoCC = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 4, 0 );

	private final JTextFieldFK txtSiglaCC = new JTextFieldFK( JTextFieldPad.TP_STRING, 10, 0 );

	private final JTextFieldFK txtDescCC = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private final JTextFieldPad txtDoc = new JTextFieldPad( JTextFieldPad.TP_STRING, 10, 0 );

	private final JTextFieldPad txtDtEmis = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private final JTextFieldPad txtDtVenc = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private final JTextFieldPad txtVlrParc = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 15, Aplicativo.casasDecFin );

	private final JTextFieldPad txtDtPagto = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private final JTextFieldPad txtVlrPago = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 15, Aplicativo.casasDecFin );

	private final JTextFieldPad txtObs = new JTextFieldPad( JTextFieldPad.TP_STRING, 250, 0 );

	private final JTextFieldPad txtCodTipoCob = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private final JTextFieldFK txtDescTipoCob = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private final ListaCampos lcConta = new ListaCampos( this );

	private final ListaCampos lcPlan = new ListaCampos( this );

	private final ListaCampos lcCC = new ListaCampos( this );

	private final ListaCampos lcTipoCob = new ListaCampos( this, "TC" );
	
	private boolean multiBaixa;
	
	private boolean categoriaRequerida = true;
	
	private Integer codhistpag = null;

	public DLBaixaPag( Component cOrig ) {

		super( cOrig );
		setTitulo( "Baixa" );
		setAtribos( 360, 420 );
		
		montaListaCampos();
		montaTela();
	}
	
	public DLBaixaPag(Component cOrig, boolean multibaixa, boolean categoriaRequerida){
		super( cOrig );
		setTitulo( "Baixa" );
		setAtribos( 360, 420 );
		
		this.multiBaixa = multibaixa;
		this.categoriaRequerida = categoriaRequerida;
		
		montaListaCampos();
		montaTela();
	}
	

	private void montaListaCampos() {

		lcConta.add( new GuardaCampo( txtCodConta, "NumConta", "Nº Conta", ListaCampos.DB_PK, false ) );
		lcConta.add( new GuardaCampo( txtDescConta, "DescConta", "Descrição", ListaCampos.DB_SI, false ) );
		lcConta.montaSql( false, "CONTA", "FN" );
		lcConta.setReadOnly( true );
		txtCodConta.setTabelaExterna( lcConta, null );
		txtCodConta.setFK( true );
		txtCodConta.setNomeCampo( "NumConta" );

		lcCC.add( new GuardaCampo( txtCodCC, "CodCC", "Código", ListaCampos.DB_PK, false ) );
		lcCC.add( new GuardaCampo( txtSiglaCC, "SiglaCC", "Sigla", ListaCampos.DB_SI, false ) );
		lcCC.add( new GuardaCampo( txtDescCC, "DescCC", "Descrição", ListaCampos.DB_SI, false ) );
		lcCC.add( new GuardaCampo( txtAnoCC, "AnoCC", "Ano-Base", ListaCampos.DB_PK, false ) );
		lcCC.setReadOnly( true );
		lcCC.setQueryCommit( false );
		lcCC.setWhereAdic( "NIVELCC=10" );
		lcCC.montaSql( false, "CC", "FN" );
		txtCodCC.setTabelaExterna( lcCC, null );
		txtCodCC.setFK( true );
		txtCodCC.setNomeCampo( "CodCC" );
		txtAnoCC.setTabelaExterna( lcCC, null );
		txtAnoCC.setFK( true );
		txtAnoCC.setNomeCampo( "AnoCC" );

		lcPlan.add( new GuardaCampo( txtCodPlan, "CodPlan", "Cód. Plan.", ListaCampos.DB_PK, false ) );
		lcPlan.add( new GuardaCampo( txtDescPlan, "DescPlan", "Descrição", ListaCampos.DB_SI, false ) );
		lcPlan.add( new GuardaCampo( txtCodRedPlan, "CodRedPlan", "Cód.Reduz.", ListaCampos.DB_SI, false ) );
		
		lcPlan.setWhereAdic( "TIPOPLAN = 'D' AND NIVELPLAN = 6" );
		lcPlan.montaSql( false, "PLANEJAMENTO", "FN" );
		lcPlan.setReadOnly( true );
		txtCodPlan.setTabelaExterna( lcPlan, FPlanejamento.class.getCanonicalName() );
		txtCodPlan.setFK( true );
		txtCodPlan.setNomeCampo( "CodPlan" );
		txtCodPlan.setRequerido( categoriaRequerida );

		txtCodTipoCob.setNomeCampo( "CodTipoCob" );
		lcTipoCob.add( new GuardaCampo( txtCodTipoCob, "CodTipoCob", "Cód.tp.cob.", ListaCampos.DB_PK, false ) );
		lcTipoCob.add( new GuardaCampo( txtDescTipoCob, "DescTipoCob", "Descrição do tipo de cobrança.", ListaCampos.DB_SI, false ) );
		lcTipoCob.montaSql( false, "TIPOCOB", "FN" );
		lcTipoCob.setQueryCommit( false );
		lcTipoCob.setReadOnly( true );
		txtCodTipoCob.setTabelaExterna( lcTipoCob, null );
		txtCodTipoCob.setListaCampos( lcTipoCob );
		txtDescTipoCob.setListaCampos( lcTipoCob );
		txtCodTipoCob.setFK( true );
	}

	private void montaTela() {
		
		if(categoriaRequerida) {
			Funcoes.setBordReq( txtCodPlan );
		}
		
		Funcoes.setBordReq( txtCodConta );
		Funcoes.setBordReq( txtDoc );
		Funcoes.setBordReq( txtDtPagto );
		Funcoes.setBordReq( txtVlrPago );
		Funcoes.setBordReq( txtObs );

		txtCodFor.setAtivo( false );
		txtRazFor.setAtivo( false );
		txtDescConta.setAtivo( false );
		txtDescPlan.setAtivo( false );
		txtDtEmis.setAtivo( false );
		txtDtVenc.setAtivo( false );
		txtVlrParc.setAtivo( false );

		adic( txtCodFor			, 7		, 20	, 80	, 20, "Cód.for." );
		adic( txtRazFor			, 90	, 20	, 250	, 20, "Razão do fornecedor" );
		adic( txtCodConta		, 7		, 60	, 80	, 20, "Cód.conta" );
		adic( txtDescConta		, 90	, 60	, 250	, 20, "Descrição da conta" );
		adic( txtCodPlan		, 7		, 100	, 100	, 20, "Cód.categoria" );
		adic( txtDescPlan		, 110	, 100	, 230	, 20, "Descrição da categoria" );
		adic( txtCodCC			, 7		, 140	, 100	, 20, "Cód.C.C." );
		adic( txtDescCC			, 110	, 140	, 230	, 20, "Descrição do centro de custo" );

		adic( txtCodTipoCob		, 7		, 180	, 80	, 20, "Cod.Tp.Cob" );
		adic( txtDescTipoCob	, 90	, 180	, 250	, 20, "Descrição do tipo de cobrança" );

		adic( txtDoc			, 7		, 220	, 110	, 20, "Doc." );
		adic( txtDtEmis			, 120	, 220	, 107	, 20, "Emissão" );
		adic( txtDtVenc			, 230	, 220	, 110	, 20, "Vencimento" );
		adic( txtVlrParc		, 7		, 260	, 110	, 20, "Vlr. Parc." );
		adic( txtDtPagto		, 120	, 260	, 107	, 20, "Dt. Pagto." );
		adic( txtVlrPago		, 230	, 260	, 110	, 20, "Vlr. Pago" );
		adic( txtObs			, 7		, 300	, 333	, 20, "Observações"  );

		lcCC.addCarregaListener( this );

	}

	public void setValores( String[] sVals ) {

		if(multiBaixa){
			txtVlrPago.setAtivo( false );
			txtRazFor.setVlrString( "PGTOS MULTIPLOS" );
		}
		else{
			txtCodFor.setVlrString( sVals[ 0 ] );
			txtRazFor.setVlrString( sVals[ 1 ] );
			txtCodConta.setVlrString( sVals[ 2 ] );
			txtCodPlan.setVlrString( sVals[ 3 ] );
			txtDoc.setVlrString( sVals[ 4 ] );
			txtDtEmis.setVlrString( sVals[ 5 ] );
			txtDtVenc.setVlrString( sVals[ 6 ] );
			txtObs.setVlrString( sVals[ 12 ] );
			txtCodCC.setVlrString( sVals[ 10 ] );
			txtCodTipoCob.setVlrString( sVals[ 11 ] );
		}
		
		txtVlrParc.setVlrString( sVals[ 7 ] );
		txtDtPagto.setVlrString( sVals[ 8 ] );
		txtVlrPago.setVlrString( sVals[ 9 ] );
	}

	public String[] getValores() {

		String[] sRetorno = new String[ 8 ];

		sRetorno[ 0 ] = txtCodConta.getVlrString();
		sRetorno[ 1 ] = txtCodPlan.getVlrString();
		sRetorno[ 2 ] = txtDoc.getVlrString();
		sRetorno[ 3 ] = txtDtPagto.getVlrString();
		sRetorno[ 4 ] = txtVlrPago.getVlrString();
		sRetorno[ 5 ] = txtCodCC.getVlrString();
		sRetorno[ 6 ] = txtCodTipoCob.getVlrString();
		sRetorno[ 7 ] = txtObs.getVlrString();

		return sRetorno;

	}

	public void actionPerformed( ActionEvent evt ) {

		if ( evt.getSource() == btOK ) {

			if ( txtCodConta.getVlrString().length() < 1  ) {
				Funcoes.mensagemInforma( this, "Número da conta é requerido!" );
			}
			else if ( txtCodPlan.getVlrString().length() < 13 && txtCodPlan.isRequerido() ) {
				Funcoes.mensagemInforma( this, "Código da categoria é requerido!" );
			}
			else if ( txtDtPagto.getVlrString().length() < 10 ) {
				Funcoes.mensagemInforma( this, "Data do pagamento é requerido!" );
			}
			else if ( txtVlrPago.getVlrString().length() < 4 ) {
				Funcoes.mensagemInforma( this, "Valor pago é requerido!" );
			}
			else if ( txtVlrPago.getVlrDouble().doubleValue() <= 0.0 ) {
				Funcoes.mensagemInforma( this, "Valor pago deve ser maior que zero!" );
			}
			else {
				super.actionPerformed( evt );
			}

			
			if(txtObs.getVlrString()==null || txtObs.getVlrString().trim().equals( "" )) {
			
				Historico historico = null;
				
				if ( codhistpag!=null  && codhistpag != 0 ) {
					historico = new Historico( codhistpag, con );
				}
				else {
					historico = new Historico();
					historico.setHistoricocodificado( DLNovoPag.HISTORICO_PADRAO );
				}
	
				historico.setData( txtDtEmis.getVlrDate() );
				historico.setDocumento( txtDoc.getVlrString() );
				historico.setPortador( txtRazFor.getVlrString() );
				historico.setValor( txtVlrParc.getVlrBigDecimal() );
//				historico.setHistoricoant( txtObs.getVlrString() );
				
				txtObs.setVlrString( historico.getHistoricodecodificado() );
				
			}
			
		}
		else {
			super.actionPerformed( evt );
		}

	}

	private int buscaAnoBaseCC() {

		int iRet = 0;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			ps = con.prepareStatement( "SELECT ANOCENTROCUSTO, CODHISTPAG FROM SGPREFERE1 WHERE CODEMP=? AND CODFILIAL=?" );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "SGPREFERE1" ) );

			rs = ps.executeQuery();

			if ( rs.next() ) {
				iRet = rs.getInt( "ANOCENTROCUSTO" );
				codhistpag = rs.getInt( "CODHISTPAG" );
			}

			rs.close();
			ps.close();

			con.commit();
		} catch ( SQLException err ) {
			err.printStackTrace();
			Funcoes.mensagemErro( this, "Erro ao buscar o ano-base para o centro de custo.\n" + err.getMessage(), true, con, err );
		} finally {
			ps = null;
			rs = null;
		}

		return iRet;
	}

	public void beforeCarrega( CarregaEvent cevt ) {
		if ( cevt.getListaCampos() == lcCC && txtAnoCC.getVlrInteger().intValue() == 0 ) {
			txtAnoCC.setVlrInteger( new Integer( buscaAnoBaseCC() ) );
		}
	}

	public void afterCarrega( CarregaEvent cevt ) { }

	public void setConexao( DbConnection cn ) {

		super.setConexao( cn );
		lcConta.setConexao( cn );
		lcConta.carregaDados();
		lcPlan.setConexao( cn );
		lcPlan.carregaDados();
		lcCC.setConexao( cn );
		lcCC.carregaDados();
		lcTipoCob.setConexao( cn );
		lcTipoCob.carregaDados();
	}

}
