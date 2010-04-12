/**
 * @version 14/07/2003 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.std <BR>
 * Classe:
 * @(#)DLEditaRec.java <BR>
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

package org.freedom.modulos.fnc;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.persistence.GuardaCampo;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.JCheckBoxPad;
import org.freedom.library.swing.JLabelPad;
import org.freedom.library.swing.JTextFieldFK;
import org.freedom.library.swing.JTextFieldPad;
import org.freedom.library.swing.dialog.FFDialogo;
import org.freedom.library.swing.frame.Aplicativo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;


import org.freedom.acao.CarregaEvent;
import org.freedom.acao.CarregaListener;
import org.freedom.funcoes.Funcoes;

public class DLEditaRec extends FFDialogo implements CarregaListener {

	private static final long serialVersionUID = 1L;

	private JTextFieldPad txtCodCli = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 10, 0 );

	private JTextFieldFK txtRazCli = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtCodBanco = new JTextFieldPad( JTextFieldPad.TP_STRING, 3, 0 );

	private JTextFieldFK txtDescBanco = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private JTextFieldPad txtCodConta = new JTextFieldPad( JTextFieldPad.TP_STRING, 10, 0 );

	private JTextFieldFK txtDescConta = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtCodPlan = new JTextFieldPad( JTextFieldPad.TP_STRING, 13, 0 );

	private JTextFieldFK txtDescPlan = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtCodCC = new JTextFieldPad( JTextFieldPad.TP_STRING, 19, 0 );

	private JTextFieldPad txtAnoCC = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 4, 0 );

	private JTextFieldFK txtSiglaCC = new JTextFieldFK( JTextFieldPad.TP_STRING, 10, 0 );

	private JTextFieldFK txtDescCC = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtDoc = new JTextFieldPad( JTextFieldPad.TP_STRING, 10, 0 );

	private JTextFieldPad txtDtEmis = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtDtVenc = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );
	
	private JTextFieldPad txtDtPrev = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtVlrJuros = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, Aplicativo.casasDecFin );

	private JTextFieldPad txtVlrDesc = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, Aplicativo.casasDecFin );

	private JTextFieldPad txtVlrDev = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, Aplicativo.casasDecFin );

	private JTextFieldPad txtVlrParc = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, Aplicativo.casasDecFin );

	private JTextFieldPad txtObs = new JTextFieldPad( JTextFieldPad.TP_STRING, 250, 0 );
	
	private final JTextFieldPad txtCodTipoCob = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	
	private final JTextFieldFK txtDescTipoCob = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );
	
	private final JTextFieldPad txtCodCartCob = new JTextFieldPad( JTextFieldPad.TP_STRING, 2, 0 );
	
	private final JTextFieldFK txtDescCartCob = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private ListaCampos lcBanco = new ListaCampos( this );

	private ListaCampos lcConta = new ListaCampos( this );

	private ListaCampos lcPlan = new ListaCampos( this );

	private ListaCampos lcCC = new ListaCampos( this );
	
	private final ListaCampos lcCartCob = new ListaCampos( this, "CB" );
	
	private final ListaCampos lcTipoCob = new ListaCampos( this, "TC" );
	
	private JCheckBoxPad cbDescPont = new JCheckBoxPad( "Desconto pontualidade?", "S", "N" );
	
	public enum EColEdit{CODCLI, RAZCLI, NUMCONTA, CODPLAN, CODCC, DOC, DTEMIS, DTVENC,
		VLRJUROS, VLRDESC, VLRDEVOLUCAO, VLRPARC, OBS, CODBANCO, CODTPCOB, DESCTPCOB,CODCARTCOB, DESCCARTCOB, DESCPONT, DTPREV };

	public enum EColRet{ NUMCONTA, CODPLAN, CODCC, DOC, VLRJUROS, VLRDESC, VLRDEVOLUCAO,
		DTVENC, OBS, CODBANCO, CODTPCOB, DESCTPCOB, CODCARTCOB, DESCCARTCOB, DESCPONT, DTPREV };		

	public DLEditaRec( Component cOrig, final boolean bEdita ) {

		super( cOrig );
		setTitulo( bEdita?"Editar":"Visualizar" );
		setAtribos( 365, 550 );

		lcBanco.add( new GuardaCampo( txtCodBanco, "CodBanco", "Cód.banco", ListaCampos.DB_PK, false ) );
		lcBanco.add( new GuardaCampo( txtDescBanco, "NomeBanco", "Nome do banco", ListaCampos.DB_SI, false ) );
		lcBanco.montaSql( false, "BANCO", "FN" );
		lcBanco.setReadOnly( true );
		txtCodBanco.setTabelaExterna( lcBanco );
		txtCodBanco.setFK( true );
		txtCodBanco.setNomeCampo( "CodBanco" );

		/************************
         * CARTEIRA DE COBRANÇA *
         ************************/
		
		txtCodCartCob.setNomeCampo( "CodCartCob" );
		lcCartCob.add( new GuardaCampo( txtCodCartCob, "CodCartCob", "Cód.cart.cob", ListaCampos.DB_PK, false ) );
		lcCartCob.add( new GuardaCampo( txtCodBanco, "CodBanco", "Cód.banco", ListaCampos.DB_PK, false ) );
		lcCartCob.add( new GuardaCampo( txtDescCartCob, "DescCartCob", "Desc.Cart.Cob", ListaCampos.DB_SI, false ) );
		lcCartCob.setDinWhereAdic( "CODBANCO = #S", txtCodBanco );
		lcCartCob.montaSql( false, "CARTCOB", "FN" );
		lcCartCob.setQueryCommit( false );
		lcCartCob.setReadOnly( true );		
		txtCodCartCob.setTabelaExterna( lcCartCob );
		txtCodCartCob.setListaCampos( lcCartCob );
		txtDescCartCob.setListaCampos( lcCartCob );
		txtCodCartCob.setFK( true );
		
		lcConta.add( new GuardaCampo( txtCodConta, "NumConta", "Nº Conta", ListaCampos.DB_PK, false ) );
		lcConta.add( new GuardaCampo( txtDescConta, "DescConta", "Descrição da conta", ListaCampos.DB_SI, false ) );
		lcConta.montaSql( false, "CONTA", "FN" );
		lcConta.setReadOnly( true );
		txtCodConta.setTabelaExterna( lcConta );
		txtCodConta.setFK( true );
		txtCodConta.setNomeCampo( "NumConta" );
		txtDescConta.setTabelaExterna( lcConta );
		txtDescConta.setLabel( "Descrição da Conta" );		


		lcPlan.add( new GuardaCampo( txtCodPlan, "CodPlan", "Cód.plan.", ListaCampos.DB_PK, false ) );
		lcPlan.add( new GuardaCampo( txtDescPlan, "DescPlan", "Descrição do planejamento", ListaCampos.DB_SI, false ) );
		lcPlan.setWhereAdic( "TIPOPLAN = 'R' AND NIVELPLAN = 6" );
		lcPlan.montaSql( false, "PLANEJAMENTO", "FN" );
		lcPlan.setReadOnly( true );
		txtCodPlan.setTabelaExterna( lcPlan );
		txtCodPlan.setFK( true );
		txtCodPlan.setNomeCampo( "CodPlan" );

		lcCC.add( new GuardaCampo( txtCodCC, "CodCC", "Cód.c.c.", ListaCampos.DB_PK, false ) );
		lcCC.add( new GuardaCampo( txtSiglaCC, "SiglaCC", "Sigla c.c.", ListaCampos.DB_SI, false ) );
		lcCC.add( new GuardaCampo( txtDescCC, "DescCC", "Descrição do centro de custos", ListaCampos.DB_SI, false ) );
		lcCC.add( new GuardaCampo( txtAnoCC, "AnoCC", "Ano-Base", ListaCampos.DB_PK, false ) );
		lcCC.setReadOnly( true );
		lcCC.setQueryCommit( false );
		lcCC.setWhereAdic( "NIVELCC=10" );
		lcCC.montaSql( false, "CC", "FN" );
		txtCodCC.setTabelaExterna( lcCC );
		txtCodCC.setFK( true );
		txtCodCC.setNomeCampo( "CodCC" );
		txtAnoCC.setTabelaExterna( lcCC );
		txtAnoCC.setFK( true );
		txtAnoCC.setNomeCampo( "AnoCC" );
		
		txtCodTipoCob.setNomeCampo( "CodTipoCob" );
		lcTipoCob.add( new GuardaCampo( txtCodTipoCob, "CodTipoCob", "Cód.tp.cob.", ListaCampos.DB_PK, false ) );
		lcTipoCob.add( new GuardaCampo( txtDescTipoCob, "DescTipoCob", "Descrição do tipo de cobrança.", ListaCampos.DB_SI, false ) );
		lcTipoCob.montaSql( false, "TIPOCOB", "FN" );
		lcTipoCob.setQueryCommit( false );
		lcTipoCob.setReadOnly( true );
		txtCodTipoCob.setTabelaExterna( lcTipoCob );
		txtCodTipoCob.setListaCampos( lcTipoCob );
		txtDescTipoCob.setListaCampos( lcTipoCob );
		txtCodTipoCob.setFK( true );

		txtCodCli.setAtivo( false );
//		txtRazCli.setAtivo( false );
//		txtDescConta.setAtivo( false );
//		txtDescPlan.setAtivo( false );
		txtDtEmis.setAtivo( false );
		txtVlrParc.setAtivo( false );

		adic( new JLabelPad( "Cód.cli." ), 7, 0, 250, 20 );
		adic( txtCodCli, 7, 20, 80, 20 );
		adic( new JLabelPad( "Razão social do cliente" ), 90, 0, 250, 20 );
		adic( txtRazCli, 90, 20, 250, 20 );
		adic( new JLabelPad( "Cod.Tp.Cob" ), 7, 40, 80, 20);
		adic( txtCodTipoCob, 7, 60, 80, 20 );
		adic( new JLabelPad("Descrição do tipo de cobrança"), 90, 40, 250, 20 );
		adic(txtDescTipoCob, 90, 60, 250, 20 );
		adic( new JLabelPad( "Cód.banco" ), 7, 80, 250, 20 );
		adic( txtCodBanco, 7, 100, 80, 20 );
		adic( new JLabelPad( "Descrição do banco" ), 90, 80, 250, 20 );
		adic( txtDescBanco, 90, 100, 250, 20 );
		
		adic( new JLabelPad( "Cód.cart.cob." ), 7, 120, 250, 20 );
		adic( txtCodCartCob, 7, 140, 80, 20 );
		adic( new JLabelPad( "Descrição da carteira de cobrança" ), 90, 120, 250, 20 );
		adic( txtDescCartCob, 90, 140, 250, 20 );

		adic( new JLabelPad( "Nºconta" ), 7, 160, 250, 20 );
		adic( txtCodConta, 7, 180, 80, 20 );
		adic( new JLabelPad( "Descrição da conta" ), 90, 160, 250, 20 );
		adic( txtDescConta, 90, 180, 250, 20 );
		
		adic( new JLabelPad( "Cód.categoria" ), 7, 200, 110, 20 );
		adic( txtCodPlan, 7, 220, 110, 20 );
		adic( new JLabelPad( "Descrição da categoria" ), 120, 200, 220, 20 );
		adic( txtDescPlan, 120, 220, 220, 20 );
		
		adic( new JLabelPad( "Cód.c.c." ), 7, 240, 110, 20 );
		adic( txtCodCC, 7, 260, 110, 20 );
		adic( new JLabelPad( "Descrição do centro de custo" ), 120, 240, 220, 20 );
		adic( txtDescCC, 120, 260, 220, 20 );
		
		adic( new JLabelPad( "Doc." ), 7, 280, 110, 20 );
		adic( txtDoc, 7, 300, 110, 20 );
		
		adic( new JLabelPad( "Emissão" ), 120, 280, 72, 20 );		
		adic( txtDtEmis, 120, 300, 72, 20 );
		
		adic( new JLabelPad( "Vencimento" ), 195, 280, 71, 20 );
		adic( txtDtVenc, 195, 300, 71, 20 );
		
		adic( new JLabelPad( "Previsão" ), 269, 280, 71, 20 );
		adic( txtDtPrev, 269, 300, 71, 20 );
		
		adic( new JLabelPad( "Vlr.juros." ), 7, 320, 81, 20 );
		adic( txtVlrJuros, 7, 340, 81, 20 );
		adic( new JLabelPad( "Vlr.desc." ), 91, 320, 81, 20 );
		adic( txtVlrDesc, 91, 340, 81, 20 );
		adic( cbDescPont, 185, 340, 200, 20 );
		cbDescPont.setVlrString( "S" );
		adic( new JLabelPad( "Vlr.devolução" ), 7, 360, 81, 20 );
		adic( txtVlrDev, 7, 380, 81, 20 );
		adic( new JLabelPad( "Vlr.parcela" ), 91, 360, 81, 20 );
		adic( txtVlrParc, 91, 380, 81, 20 );
		
		adic( new JLabelPad( "Observações" ), 7, 400, 240, 20 );
		adic( txtObs, 7, 420, 333, 20 );

		lcCC.addCarregaListener( this );
		
		if(!bEdita) {
			txtAnoCC.setAtivo( bEdita );
			txtCodBanco.setAtivo( bEdita );
			txtCodCartCob.setAtivo( bEdita );
			txtCodCC.setAtivo( bEdita );
			txtCodCli.setAtivo( bEdita );
			txtCodConta.setAtivo( bEdita );
			txtCodPlan.setAtivo( bEdita );
			txtCodTipoCob.setAtivo( bEdita );
			txtDoc.setAtivo( bEdita );
			txtDtEmis.setAtivo( bEdita );
			txtDtVenc.setAtivo( bEdita );
			txtDtPrev.setAtivo( bEdita );
			txtObs.setAtivo( bEdita );
			txtSiglaCC.setAtivo( bEdita );
			txtVlrDesc.setAtivo( bEdita );
			txtVlrJuros.setAtivo( bEdita );
			txtVlrParc.setAtivo( bEdita );
			btOK.setVisible( bEdita );
		}	
		
		
		
	}

	public void setValores( Object[] sVals ) {

		txtCodCli.setVlrInteger( (Integer) sVals[ EColEdit.CODCLI.ordinal() ] );
		txtRazCli.setVlrString( (String) sVals[ EColEdit.RAZCLI.ordinal() ] );
		txtCodConta.setVlrString( (String) sVals[ EColEdit.NUMCONTA.ordinal() ] );
		txtCodPlan.setVlrString( (String) sVals[ EColEdit.CODPLAN.ordinal() ] );
		txtCodCC.setVlrString( (String) sVals[ EColEdit.CODCC.ordinal() ] );
		txtDoc.setVlrString( (String) sVals[ EColEdit.DOC.ordinal() ] );
		txtDtEmis.setVlrDate( (Date) sVals[ EColEdit.DTEMIS.ordinal() ] );
		txtDtVenc.setVlrDate( (Date) sVals[ EColEdit.DTVENC.ordinal() ] );
		txtDtPrev.setVlrDate( (Date) sVals[ EColEdit.DTPREV.ordinal() ] );
		txtVlrJuros.setVlrBigDecimal( (BigDecimal) sVals[ EColEdit.VLRJUROS.ordinal() ] );
		txtVlrDesc.setVlrBigDecimal( (BigDecimal) sVals[ EColEdit.VLRDESC.ordinal() ] );
		txtVlrDev.setVlrBigDecimal( (BigDecimal) sVals[ EColEdit.VLRDEVOLUCAO.ordinal()] );		
		txtVlrParc.setVlrBigDecimal( (BigDecimal) sVals[ EColEdit.VLRPARC.ordinal() ] );
		txtObs.setVlrString( (String) sVals[ EColEdit.OBS.ordinal() ] );
		txtCodBanco.setVlrString((String) sVals[ EColEdit.CODBANCO.ordinal() ] );
		txtCodTipoCob.setVlrString( ( String) sVals[ EColEdit.CODTPCOB.ordinal() ] );
		txtDescTipoCob.setVlrString( (String) sVals[ EColEdit.DESCTPCOB.ordinal()] );
		txtCodCartCob.setVlrString( ( String) sVals[ EColEdit.CODCARTCOB.ordinal() ] );
		txtDescCartCob.setVlrString( (String) sVals[ EColEdit.DESCCARTCOB.ordinal()] );		
		cbDescPont.setVlrString( (String) sVals[ EColEdit.DESCPONT.ordinal()] );
		
		lcConta.carregaDados();
		lcBanco.carregaDados();
		lcPlan.carregaDados();
		lcCC.carregaDados();
		
	}

	public Object[] getValores() {

		Object[] oRetorno = new Object[ EColRet.values().length ];
		oRetorno[ EColRet.NUMCONTA.ordinal() ] = txtCodConta.getVlrString();
		oRetorno[ EColRet.CODPLAN.ordinal() ] = txtCodPlan.getVlrString();
		oRetorno[ EColRet.CODCC.ordinal() ] = txtCodCC.getVlrString();
		oRetorno[ EColRet.DOC.ordinal() ] = txtDoc.getVlrString();
		oRetorno[ EColRet.VLRJUROS.ordinal() ] = txtVlrJuros.getVlrBigDecimal();
		oRetorno[ EColRet.VLRDESC.ordinal() ] = txtVlrDesc.getVlrBigDecimal();
		oRetorno[ EColRet.VLRDEVOLUCAO.ordinal() ] = txtVlrDev.getVlrBigDecimal();
		oRetorno[ EColRet.DTVENC.ordinal() ] = txtDtVenc.getVlrDate();
		oRetorno[ EColRet.DTPREV.ordinal() ] = txtDtPrev.getVlrDate();
		oRetorno[ EColRet.OBS.ordinal() ] = txtObs.getVlrString();
		oRetorno[ EColRet.CODBANCO.ordinal() ] = txtCodBanco.getVlrString();
		oRetorno[ EColRet.CODTPCOB.ordinal() ] = txtCodTipoCob.getVlrString();
		oRetorno[ EColRet.DESCTPCOB.ordinal() ] = txtDescTipoCob.getVlrString();
		oRetorno[ EColRet.CODCARTCOB.ordinal() ] = txtCodCartCob.getVlrString();
		oRetorno[ EColRet.DESCCARTCOB.ordinal() ] = txtDescCartCob.getVlrString();
		oRetorno[ EColRet.DESCPONT.ordinal() ] = cbDescPont.getVlrString();
		return oRetorno;
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
		
		try {
			
			PreparedStatement ps = con.prepareStatement( "SELECT ANOCENTROCUSTO FROM SGPREFERE1 WHERE CODEMP=? AND CODFILIAL=?" );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "SGPREFERE1" ) );
			
			ResultSet rs = ps.executeQuery();
			
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

	public void afterCarrega( CarregaEvent cevt ) { }

	public void setConexao( DbConnection cn ) {

		super.setConexao( cn );
		lcConta.setConexao( cn );
		lcConta.carregaDados();
		lcPlan.setConexao( cn );
		lcPlan.carregaDados();
		lcCC.setConexao( cn );
		lcCC.carregaDados();
		lcBanco.setConexao( cn );
		lcTipoCob.setConexao( cn );
		lcBanco.carregaDados();
		lcCartCob.setConexao( cn );
		lcCartCob.carregaDados();
		
	}
}
