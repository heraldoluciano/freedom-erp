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
 * Comentários sobre a classe...
 */

package org.freedom.modulos.std;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.freedom.componentes.JLabelPad;

import org.freedom.acao.CarregaEvent;
import org.freedom.acao.CarregaListener;
import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FFDialogo;

public class DLEditaRec extends FFDialogo implements CarregaListener {

	private static final long serialVersionUID = 1L;

	private JTextFieldPad txtCodCli = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 10, 0 );

	private JTextFieldPad txtRazCli = new JTextFieldPad( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtCodBanco = new JTextFieldPad( JTextFieldPad.TP_STRING, 3, 0 );

	private JTextFieldFK txtDescBanco = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private JTextFieldPad txtCodConta = new JTextFieldPad( JTextFieldPad.TP_STRING, 10, 0 );

	private JTextFieldPad txtDescConta = new JTextFieldPad( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtCodPlan = new JTextFieldPad( JTextFieldPad.TP_STRING, 13, 0 );

	private JTextFieldPad txtDescPlan = new JTextFieldPad( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtCodCC = new JTextFieldPad( JTextFieldPad.TP_STRING, 19, 0 );

	private JTextFieldPad txtAnoCC = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 4, 0 );

	private JTextFieldFK txtSiglaCC = new JTextFieldFK( JTextFieldPad.TP_STRING, 10, 0 );

	private JTextFieldFK txtDescCC = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private JTextFieldPad txtDoc = new JTextFieldPad( JTextFieldPad.TP_STRING, 10, 0 );

	private JTextFieldPad txtDtEmis = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtDtVenc = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtVlrJuros = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, 2 );

	private JTextFieldPad txtVlrDesc = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, 2 );

	private JTextFieldPad txtVlrParc = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, 2 );

	private JTextFieldPad txtObs = new JTextFieldPad( JTextFieldPad.TP_STRING, 250, 0 );

	private ListaCampos lcBanco = new ListaCampos( this );

	private ListaCampos lcConta = new ListaCampos( this );

	private ListaCampos lcPlan = new ListaCampos( this );

	private ListaCampos lcCC = new ListaCampos( this );
	
	public enum EColEdit{CODCLI, RAZCLI, NUMCONTA, CODPLANO, CODCC, DOC, DTEMIS, DTVENC,
		VLRJUROS, VLRDESC, VLRPARC, OBS, CODBANCO};

	public DLEditaRec( Component cOrig ) {

		super( cOrig );
		setTitulo( "Editar" );
		setAtribos( 360, 450 );

		lcBanco.add( new GuardaCampo( txtCodBanco, "CodBanco", "Cód.banco", ListaCampos.DB_PK, false ) );
		lcBanco.add( new GuardaCampo( txtDescBanco, "NomeBanco", "Nome do banco", ListaCampos.DB_SI, false ) );
		lcBanco.montaSql( false, "BANCO", "FN" );
		lcBanco.setReadOnly( true );
		txtCodBanco.setTabelaExterna( lcBanco );
		txtCodBanco.setFK( true );
		txtCodBanco.setNomeCampo( "CodBanco" );

		lcConta.add( new GuardaCampo( txtCodConta, "NumConta", "Nº Conta", ListaCampos.DB_PK, false ) );
		lcConta.add( new GuardaCampo( txtDescConta, "DescConta", "Descrição da conta", ListaCampos.DB_SI, false ) );
		lcConta.montaSql( false, "CONTA", "FN" );
		lcConta.setReadOnly( true );
		txtCodConta.setTabelaExterna( lcConta );
		txtCodConta.setFK( true );
		txtCodConta.setNomeCampo( "NumConta" );

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

		txtCodCli.setAtivo( false );
		txtRazCli.setAtivo( false );
		txtDescConta.setAtivo( false );
		txtDescPlan.setAtivo( false );
		txtDtEmis.setAtivo( false );
		txtVlrParc.setAtivo( false );

		adic( new JLabelPad( "Cód.cli." ), 7, 0, 250, 20 );
		adic( txtCodCli, 7, 20, 80, 20 );
		adic( new JLabelPad( "Razão social do cliente" ), 90, 0, 250, 20 );
		adic( txtRazCli, 90, 20, 200, 20 );
		adic( new JLabelPad( "Cód.banco" ), 7, 40, 250, 20 );
		adic( txtCodBanco, 7, 60, 80, 20 );
		adic( new JLabelPad( "Descrição do banco" ), 90, 40, 250, 20 );
		adic( txtDescBanco, 90, 60, 200, 20 );
		adic( new JLabelPad( "Nºconta" ), 7, 80, 250, 20 );
		adic( txtCodConta, 7, 100, 80, 20 );
		adic( new JLabelPad( "Descrição da conta" ), 90, 80, 250, 20 );
		adic( txtDescConta, 90, 100, 200, 20 );
		adic( new JLabelPad( "Cód.catg." ), 7, 120, 250, 20 );
		adic( txtCodPlan, 7, 140, 100, 20 );
		adic( new JLabelPad( "Descrição da categoria" ), 110, 120, 250, 20 );
		adic( txtDescPlan, 110, 140, 200, 20 );
		adic( new JLabelPad( "Cód.c.c." ), 7, 160, 250, 20 );
		adic( txtCodCC, 7, 180, 100, 20 );
		adic( new JLabelPad( "Descrição do centro de custo" ), 110, 160, 250, 20 );
		adic( txtDescCC, 110, 180, 200, 20 );
		adic( new JLabelPad( "Doc." ), 7, 200, 110, 20 );
		adic( txtDoc, 7, 220, 110, 20 );
		adic( new JLabelPad( "Emissão" ), 120, 200, 107, 20 );
		adic( txtDtEmis, 120, 220, 107, 20 );
		adic( new JLabelPad( "Vencimento" ), 230, 200, 110, 20 );
		adic( txtDtVenc, 230, 220, 110, 20 );
		adic( new JLabelPad( "Vlr.juros." ), 7, 240, 110, 20 );
		adic( txtVlrJuros, 7, 260, 110, 20 );
		adic( new JLabelPad( "Vlr.desc." ), 120, 240, 107, 20 );
		adic( txtVlrDesc, 120, 260, 107, 20 );
		adic( new JLabelPad( "Vlr.parcela" ), 230, 240, 110, 20 );
		adic( txtVlrParc, 230, 260, 110, 20 );
		adic( new JLabelPad( "Observações" ), 7, 280, 240, 20 );
		adic( txtObs, 7, 300, 333, 20 );

		lcCC.addCarregaListener( this );
	}

	public void setValores( Object[] sVals ) {

		txtCodCli.setVlrInteger( (Integer) sVals[ EColEdit.CODCLI.ordinal() ] );
		txtRazCli.setVlrString( (String) sVals[ EColEdit.RAZCLI.ordinal() ] );
		txtCodConta.setVlrString( (String) sVals[ EColEdit.NUMCONTA.ordinal() ] );
		txtCodPlan.setVlrString( (String) sVals[ EColEdit.CODPLANO.ordinal() ] );
		txtCodCC.setVlrString( (String) sVals[ EColEdit.CODCC.ordinal() ] );
		txtDoc.setVlrString( (String) sVals[ EColEdit.DOC.ordinal() ] );
		txtDtEmis.setVlrDate( (Date)sVals[ EColEdit.DTEMIS.ordinal() ] );
		txtDtVenc.setVlrDate( (Date) sVals[ EColEdit.DTVENC.ordinal() ] );
		txtVlrJuros.setVlrBigDecimal( (BigDecimal) sVals[ EColEdit.VLRJUROS.ordinal() ] );
		txtVlrDesc.setVlrBigDecimal( (BigDecimal) sVals[ EColEdit.VLRDESC.ordinal() ] );
		txtVlrParc.setVlrBigDecimal( (BigDecimal) sVals[ EColEdit.VLRPARC.ordinal() ] );
		txtObs.setVlrString( (String) sVals[ EColEdit.OBS.ordinal() ] );
		txtCodBanco.setVlrString((String) sVals[ EColEdit.CODBANCO.ordinal() ] );
	}

	public String[] getValores() {

		String[] sRetorno = new String[ 9 ];
		sRetorno[ 0 ] = txtCodConta.getVlrString();
		sRetorno[ 1 ] = txtCodPlan.getVlrString();
		sRetorno[ 2 ] = txtCodCC.getVlrString();
		sRetorno[ 3 ] = txtDoc.getVlrString();
		sRetorno[ 4 ] = txtVlrJuros.getVlrString();
		sRetorno[ 5 ] = txtVlrDesc.getVlrString();
		sRetorno[ 6 ] = txtDtVenc.getVlrString();
		sRetorno[ 7 ] = txtObs.getVlrString();
		sRetorno[ 8 ] = txtCodBanco.getVlrString();
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
			
			if ( !con.getAutoCommit() ) {
				con.commit();
			}
		} catch ( SQLException err ) {
			err.printStackTrace();
			Funcoes.mensagemErro( this, "Erro ao buscar o ano-base para o centro de custo.\n" + err.getMessage(), true, con, err );
		} finally {
			rs = null;
			ps = null;
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

	public void setConexao( Connection cn ) {

		super.setConexao( cn );
		lcConta.setConexao( cn );
		lcConta.carregaDados();
		lcPlan.setConexao( cn );
		lcPlan.carregaDados();
		lcCC.setConexao( cn );
		lcCC.carregaDados();
		lcBanco.setConexao( cn );
		lcBanco.carregaDados();
	}
}
