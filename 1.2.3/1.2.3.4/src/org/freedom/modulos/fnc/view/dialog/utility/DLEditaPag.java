/**
 * @version 14/07/2003 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 * 
 *         Projeto: Freedom <BR>
 * 
 *         Pacote: org.freedom.modulos.std <BR>
 *         Classe:
 * @(#)DLEditaPag.java <BR>
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
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.functions.Funcoes;
import org.freedom.library.persistence.GuardaCampo;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.component.JLabelPad;
import org.freedom.library.swing.component.JTextFieldFK;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.dialog.FFDialogo;
import org.freedom.library.swing.frame.Aplicativo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.freedom.acao.CarregaEvent;
import org.freedom.acao.CarregaListener;

public class DLEditaPag extends FFDialogo implements CarregaListener {

	private static final long serialVersionUID = 1L;

	private final JTextFieldPad txtCodFor = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 10, 0 );

	private final JTextFieldPad txtRazFor = new JTextFieldPad( JTextFieldPad.TP_STRING, 40, 0 );

	private final JTextFieldPad txtCodConta = new JTextFieldPad( JTextFieldPad.TP_STRING, 10, 0 );

	private final JTextFieldPad txtDescConta = new JTextFieldPad( JTextFieldPad.TP_STRING, 50, 0 );

	private final JTextFieldPad txtCodPlan = new JTextFieldPad( JTextFieldPad.TP_STRING, 13, 0 );

	private final JTextFieldPad txtDescPlan = new JTextFieldPad( JTextFieldPad.TP_STRING, 50, 0 );

	private final JTextFieldPad txtCodCC = new JTextFieldPad( JTextFieldPad.TP_STRING, 19, 0 );

	private final JTextFieldPad txtAnoCC = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 4, 0 );

	private final JTextFieldFK txtSiglaCC = new JTextFieldFK( JTextFieldPad.TP_STRING, 10, 0 );

	private final JTextFieldFK txtDescCC = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private final JTextFieldPad txtDoc = new JTextFieldPad( JTextFieldPad.TP_STRING, 10, 0 );

	private final JTextFieldPad txtDtEmis = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private final JTextFieldPad txtDtVenc = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private final JTextFieldPad txtVlrParc = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, 2 );

	private final JTextFieldPad txtVlrJuros = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, 2 );

	private final JTextFieldPad txtVlrDesc = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, 2 );

	private final JTextFieldPad txtVlrDev = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, Aplicativo.casasDecFin );

	private final JTextFieldPad txtVlrAdic = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, 2 );

	private final JTextFieldPad txtObs = new JTextFieldPad( JTextFieldPad.TP_STRING, 250, 0 );

	private final JTextFieldPad txtCodTipoCob = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private final JTextFieldFK txtDescTipoCob = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private final ListaCampos lcConta = new ListaCampos( this );

	private final ListaCampos lcPlan = new ListaCampos( this );

	private final ListaCampos lcCC = new ListaCampos( this );

	private final ListaCampos lcTipoCob = new ListaCampos( this, "TC" );

	public DLEditaPag( Component cOrig, boolean edita ) {

		super( cOrig );
		setTitulo( "Editar" );
		setAtribos( 360, 420 );

		montaListaCampos();
		montaTela();
	}

	private void montaListaCampos() {

		lcConta.add( new GuardaCampo( txtCodConta, "NumConta", "Nº Conta", ListaCampos.DB_PK, false ) );
		lcConta.add( new GuardaCampo( txtDescConta, "DescConta", "Descrição da conta", ListaCampos.DB_SI, false ) );
		lcConta.montaSql( false, "CONTA", "FN" );
		lcConta.setReadOnly( true );
		txtCodConta.setTabelaExterna( lcConta, null );
		txtCodConta.setFK( true );
		txtCodConta.setNomeCampo( "NumConta" );

		lcPlan.add( new GuardaCampo( txtCodPlan, "CodPlan", "Cód.plan.", ListaCampos.DB_PK, false ) );
		lcPlan.add( new GuardaCampo( txtDescPlan, "DescPlan", "Descrição do planejamento", ListaCampos.DB_SI, false ) );
		lcPlan.setWhereAdic( "TIPOPLAN = 'D' AND NIVELPLAN = 6" );
		lcPlan.montaSql( false, "PLANEJAMENTO", "FN" );
		lcPlan.setReadOnly( true );
		txtCodPlan.setTabelaExterna( lcPlan, null );
		txtCodPlan.setFK( true );
		txtCodPlan.setNomeCampo( "CodPlan" );

		lcCC.add( new GuardaCampo( txtCodCC, "CodCC", "Cód.c.c.", ListaCampos.DB_PK, false ) );
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

		lcCC.addCarregaListener( this );

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

		txtCodFor.setAtivo( false );
		txtRazFor.setAtivo( false );
		txtDescConta.setAtivo( false );
		txtDescPlan.setAtivo( false );
		txtDtEmis.setAtivo( false );

		adic( new JLabelPad( "Cód.for." ), 7, 0, 250, 20 );
		adic( txtCodFor, 7, 20, 80, 20 );
		adic( new JLabelPad( "Razão social do fornecedor" ), 90, 0, 250, 20 );
		adic( txtRazFor, 90, 20, 250, 20 );
		adic( new JLabelPad( "Nº conta" ), 7, 40, 250, 20 );
		adic( txtCodConta, 7, 60, 80, 20 );
		adic( new JLabelPad( "Descrição da conta" ), 90, 40, 250, 20 );
		adic( txtDescConta, 90, 60, 250, 20 );
		adic( new JLabelPad( "Cód.catg." ), 7, 80, 250, 20 );
		adic( txtCodPlan, 7, 100, 100, 20 );
		adic( new JLabelPad( "Descrição da categoria" ), 110, 80, 250, 20 );
		adic( txtDescPlan, 110, 100, 230, 20 );
		adic( new JLabelPad( "Cód.c.c." ), 7, 120, 250, 20 );
		adic( txtCodCC, 7, 140, 100, 20 );
		adic( new JLabelPad( "Descrição do centro de custo" ), 110, 120, 250, 20 );
		adic( txtDescCC, 110, 140, 230, 20 );
		adic( new JLabelPad( "Cod.Tp.Cob" ), 7, 160, 80, 20 );
		adic( txtCodTipoCob, 7, 180, 80, 20 );
		adic( new JLabelPad( "Descrição do tipo de cobrança" ), 90, 160, 250, 20 );
		adic( txtDescTipoCob, 90, 180, 250, 20 );

		adic( new JLabelPad( "Doc." ), 7, 200, 81, 20 );
		adic( txtDoc, 7, 220, 81, 20 );
		adic( new JLabelPad( "Emissão" ), 91, 200, 81, 20 );
		adic( txtDtEmis, 91, 220, 81, 20 );
		adic( new JLabelPad( "Vencimento" ), 175, 200, 81, 20 );
		adic( txtDtVenc, 175, 220, 81, 20 );
		adic( new JLabelPad( "Vlr.parcela" ), 259, 200, 81, 20 );
		adic( txtVlrParc, 259, 220, 81, 20 );

		adic( new JLabelPad( "Vlr.desc." ), 7, 240, 81, 20 );
		adic( txtVlrDesc, 7, 260, 81, 20 );
		adic( new JLabelPad( "Vlr.juros." ), 91, 240, 81, 20 );
		adic( txtVlrJuros, 91, 260, 81, 20 );
		adic( new JLabelPad( "Vlr.devolução" ), 175, 240, 81, 20 );
		adic( txtVlrDev, 175, 260, 81, 20 );
		adic( new JLabelPad( "Vlr.adicional" ), 259, 240, 81, 20 );
		adic( txtVlrAdic, 259, 260, 81, 20 );

		adic( new JLabelPad( "Observações" ), 7, 280, 200, 20 );
		adic( txtObs, 7, 300, 333, 20 );
	}

	public void setValores( String[] sVals, boolean bLancaUsu ) {

		txtCodFor.setVlrString( sVals[ 0 ] );
		txtRazFor.setVlrString( sVals[ 1 ] );
		txtCodConta.setVlrString( sVals[ 2 ] );
		txtCodPlan.setVlrString( sVals[ 3 ] );
		txtCodCC.setVlrString( sVals[ 4 ] );
		txtDoc.setVlrString( sVals[ 5 ] );
		txtDtEmis.setVlrString( sVals[ 6 ] );
		txtDtVenc.setVlrString( sVals[ 7 ] );
		txtVlrParc.setVlrString( sVals[ 8 ] );
		txtVlrJuros.setVlrString( sVals[ 9 ] );
		txtVlrDesc.setVlrString( sVals[ 10 ] );
		txtVlrAdic.setVlrString( sVals[ 11 ] );
		txtObs.setVlrString( sVals[ 12 ] );
		txtCodTipoCob.setVlrString( sVals[ 13 ] );
		txtVlrDev.setVlrString( sVals[ 14 ] );
		txtVlrParc.setAtivo( bLancaUsu );
	}

	public String[] getValores() {

		String[] sRetorno = new String[ 12 ];
		sRetorno[ 0 ] = txtCodConta.getVlrString();
		sRetorno[ 1 ] = txtCodPlan.getVlrString();
		sRetorno[ 2 ] = txtCodCC.getVlrString();
		sRetorno[ 3 ] = txtDoc.getVlrString();
		sRetorno[ 4 ] = txtVlrParc.getVlrString();
		sRetorno[ 5 ] = txtVlrJuros.getVlrString();
		sRetorno[ 6 ] = txtVlrAdic.getVlrString();
		sRetorno[ 7 ] = txtVlrDesc.getVlrString();
		sRetorno[ 8 ] = txtDtVenc.getVlrString();
		sRetorno[ 9 ] = txtObs.getVlrString();
		sRetorno[ 10 ] = txtCodTipoCob.getVlrString();
		sRetorno[ 11 ] = txtVlrDev.getVlrString();
		return sRetorno;
	}

	public void actionPerformed( ActionEvent evt ) {

		if ( evt.getSource() == btOK && txtDtVenc.getVlrString().length() < 10 ) {
			Funcoes.mensagemInforma( this, "Data do vencimento é requerido!" );
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

			ps = con.prepareStatement( "SELECT ANOCENTROCUSTO FROM SGPREFERE1 WHERE CODEMP=? AND CODFILIAL=?" );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "SGPREFERE1" ) );

			rs = ps.executeQuery();

			if ( rs.next() ) {
				iRet = rs.getInt( "ANOCENTROCUSTO" );
			}

			rs.close();
			ps.close();

			con.commit();
		} catch ( SQLException err ) {
			err.printStackTrace();
			Funcoes.mensagemErro( this, "Erro ao buscar o ano-base para o centro de custo.\n" + err.getMessage(), true, con, err );
		}

		return iRet;
	}

	public void beforeCarrega( CarregaEvent cevt ) {

		if ( cevt.getListaCampos() == lcCC && txtAnoCC.getVlrInteger().intValue() == 0 ) {
			txtAnoCC.setVlrInteger( new Integer( buscaAnoBaseCC() ) );
		}
	}

	public void afterCarrega( CarregaEvent cevt ) {

	}

	public void setConexao( DbConnection cn ) {

		super.setConexao( cn );
		lcConta.setConexao( cn );
		lcConta.carregaDados();
		lcPlan.setConexao( cn );
		lcPlan.carregaDados();
		lcTipoCob.setConexao( cn );
		lcTipoCob.carregaDados();
		lcCC.setConexao( cn );
		lcCC.carregaDados();
	}
}
