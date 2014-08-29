/**
 * @version 02/11/2003 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 * 
 *         Projeto: Freedom <BR>
 * 
 *         Pacote: org.freedom.modulos.std <BR>
 *         Classe:
 * @(#)FRReceber.java <BR>
 * 
 *                    Este arquivo é parte do sistema Freedom-ERP, o Freedom-ERP é um software livre; você pode redistribui-lo e/ou <BR>
 *                    modifica-lo dentro dos termos da Licença Pública Geral GNU como publicada pela Fundação do Software Livre (FSF); <BR>
 *                    na versão 2 da Licença, ou (na sua opnião) qualquer versão. <BR>
 *                    Este programa é distribuido na esperança que possa ser util, mas SEM NENHUMA GARANTIA; <BR>
 *                    sem uma garantia implicita de ADEQUAÇÂO a qualquer MERCADO ou APLICAÇÃO EM PARTICULAR. <BR>
 *                    Veja a Licença Pública Geral GNU para maiores detalhes. <BR>
 *                    Você deve ter recebido uma cópia da Licença Pública Geral GNU junto com este programa, se não, <BR>
 *                    de acordo com os termos da LPG-PC <BR>
 * <BR>
 * 
 *                    Comentários sobre a classe...
 * 
 */

package org.freedom.modulos.fnc.view.frame.report;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.SwingConstants;

import org.freedom.acao.RadioGroupEvent;
import org.freedom.acao.RadioGroupListener;
import org.freedom.infra.functions.StringFunctions;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.component.ImprimeOS;
import org.freedom.library.functions.Funcoes;
import org.freedom.library.persistence.GuardaCampo;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.component.JCheckBoxPad;
import org.freedom.library.swing.component.JLabelPad;
import org.freedom.library.swing.component.JRadioGroup;
import org.freedom.library.swing.component.JTextFieldFK;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.frame.Aplicativo;
import org.freedom.library.swing.frame.AplicativoPD;
import org.freedom.library.swing.frame.FPrinterJob;
import org.freedom.library.swing.frame.FRelatorio;
import org.freedom.library.type.TYPE_PRINT;
import org.freedom.modulos.fnc.view.frame.crud.tabbed.FConta;

public class FRReceber extends FRelatorio implements RadioGroupListener {

	private static final long serialVersionUID = 1L;

	private JTextFieldPad txtDataini = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtDatafim = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );
	
	private JTextFieldPad txtDatacor = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtCodCli = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtCnpjCli = new JTextFieldPad( JTextFieldPad.TP_STRING, 14, 0 );

	private JTextFieldFK txtRazCli = new JTextFieldFK( JTextFieldPad.TP_STRING, 60, 0 );

	private JTextFieldPad txtCodSetor = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtDescSetor = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private JTextFieldPad txtCodVend = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtNomeVend = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private JTextFieldPad txtCodBanco = new JTextFieldPad( JTextFieldPad.TP_STRING, 3, 0 );

	private JTextFieldFK txtDescBanco = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private JTextFieldPad txtCodTpCob = new JTextFieldPad( JTextFieldPad.TP_STRING, 3, 0 );

	private JTextFieldFK txtDescTpCob = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private JTextFieldPad txtCodPlanoPag = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtDescPlanoPag = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private JCheckBoxPad cbObs = new JCheckBoxPad( "Imprimir observações?", "S", "N" );

	private JCheckBoxPad cbImpTotDia = new JCheckBoxPad( "Imprimir totalizador diário?", "S", "N" );

	private JCheckBoxPad cbRecPar = new JCheckBoxPad( "Imprimir recebimentos parciais?", "S", "N" );

	// private JCheckBoxPad cbAgrupCli = new JCheckBoxPad( "Agrupar por cliente?", "S", "N" );

	private JTextFieldPad txtCodTipoCli = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtDescTipoCli = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private JRadioGroup<String, String> rgModo = null;

	private JRadioGroup<String, String> rgTipoRel = null;

	private JRadioGroup<String, String> rgOrdem = null;

	private JRadioGroup<String, String> rgOrdem2 = null;

	private ListaCampos lcCli = new ListaCampos( this );

	private ListaCampos lcTipoCli = new ListaCampos( this );

	private ListaCampos lcSetor = new ListaCampos( this );

	private ListaCampos lcVendedor = new ListaCampos( this );

	private ListaCampos lcBanco = new ListaCampos( this );

	private ListaCampos lcPlanoPag = new ListaCampos( this );

	private ListaCampos lcTipoCob = new ListaCampos( this );
	
	private ListaCampos lcConta = new ListaCampos( this );
	
	private JTextFieldPad txtNumConta = new JTextFieldPad( JTextFieldPad.TP_STRING, 10, 0 );

	private JTextFieldFK txtDescConta = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private Calendar calAtual = Calendar.getInstance();
	
	private boolean bPref = false;

	public FRReceber() {

		super( false );
		setTitulo( "Receber/Recebidas" );
		setAtribos( 40, 50, 680, 470 );
		txtDatafim.setVlrDate( calAtual.getTime() );
		txtDatacor.setVlrDate( calAtual.getTime() );
		Calendar cal = Calendar.getInstance();
		cal.set( Calendar.DAY_OF_MONTH, 1 );
		txtDataini.setVlrDate( cal.getTime() );
		cbObs.setVlrString( "S" );
		cbImpTotDia.setVlrString( "S" );
		montaListaCampos();
		montaRadioGroups();
		montaTela();
		btExportXLS.setEnabled( true );

	}

	private void montaListaCampos() {

		lcCli.add( new GuardaCampo( txtCodCli, "CodCli", "Cód.cli.", ListaCampos.DB_PK, false ) );
		lcCli.add( new GuardaCampo( txtRazCli, "RazCli", "Razão social do cliente", ListaCampos.DB_SI, false ) );
		lcCli.add( new GuardaCampo( txtCnpjCli, "CnpjCli", "CNPJ", ListaCampos.DB_SI, false ) );

		lcCli.montaSql( false, "CLIENTE", "VD" );
		lcCli.setReadOnly( true );
		txtCodCli.setTabelaExterna( lcCli, null );
		txtCodCli.setFK( true );
		txtCodCli.setNomeCampo( "CodCli" );

		lcSetor.add( new GuardaCampo( txtCodSetor, "CodSetor", "Cód.setor", ListaCampos.DB_PK, false ) );
		lcSetor.add( new GuardaCampo( txtDescSetor, "DescSetor", "Descrição do setor", ListaCampos.DB_SI, false ) );
		lcSetor.montaSql( false, "SETOR", "VD" );
		lcSetor.setReadOnly( true );
		txtCodSetor.setTabelaExterna( lcSetor, null );
		txtCodSetor.setFK( true );
		txtCodSetor.setNomeCampo( "CodSetor" );

		lcVendedor.add( new GuardaCampo( txtCodVend, "CodVend", "Cód.comiss.", ListaCampos.DB_PK, false ) );
		lcVendedor.add( new GuardaCampo( txtNomeVend, "NomeVend", "Nome do comissionado", ListaCampos.DB_SI, false ) );
		lcVendedor.montaSql( false, "VENDEDOR", "VD" );
		lcVendedor.setReadOnly( true );
		txtCodVend.setTabelaExterna( lcVendedor, null );
		txtCodVend.setFK( true );
		txtCodVend.setNomeCampo( "CodVend" );

		lcBanco.add( new GuardaCampo( txtCodBanco, "CodBanco", "Cód.banco.", ListaCampos.DB_PK, false ) );
		lcBanco.add( new GuardaCampo( txtDescBanco, "NomeBanco", "Nome do banco", ListaCampos.DB_SI, false ) );
		lcBanco.montaSql( false, "BANCO", "FN" );
		lcBanco.setReadOnly( true );
		txtCodBanco.setTabelaExterna( lcBanco, null );
		txtCodBanco.setFK( true );
		txtCodBanco.setNomeCampo( "CodBanco" );

		lcPlanoPag.add( new GuardaCampo( txtCodPlanoPag, "CodPlanoPag", "Cód.pl.pag.", ListaCampos.DB_PK, false ) );
		lcPlanoPag.add( new GuardaCampo( txtDescPlanoPag, "DescPlanoPag", "Descrição do plano de pagamento", ListaCampos.DB_SI, false ) );
		lcPlanoPag.montaSql( false, "PLANOPAG", "FN" );
		lcPlanoPag.setReadOnly( true );
		txtCodPlanoPag.setTabelaExterna( lcPlanoPag, null );
		txtCodPlanoPag.setFK( true );
		txtCodPlanoPag.setNomeCampo( "CodPlanoPag" );

		txtCodTpCob.setNomeCampo( "CodTipoCob" );
		lcTipoCob.add( new GuardaCampo( txtCodTpCob, "CodTipoCob", "Cód.tp.cob.", ListaCampos.DB_PK, false ) );
		lcTipoCob.add( new GuardaCampo( txtDescTpCob, "DescTipoCob", "Descrição do tipo de cobrança.", ListaCampos.DB_SI, false ) );
		lcTipoCob.montaSql( false, "TIPOCOB", "FN" );
		lcTipoCob.setQueryCommit( false );
		lcTipoCob.setReadOnly( true );
		txtCodTpCob.setTabelaExterna( lcTipoCob, null );
		txtCodTpCob.setListaCampos( lcTipoCob );
		txtDescTpCob.setListaCampos( lcTipoCob );
		txtCodTpCob.setFK( true );

		lcTipoCli.add( new GuardaCampo( txtCodTipoCli, "CodTipoCli", "Cód.tp.cli.", ListaCampos.DB_PK, false ) );
		lcTipoCli.add( new GuardaCampo( txtDescTipoCli, "DescTipoCli", "Descrição do tipo de cliente", ListaCampos.DB_SI, false ) );
		lcTipoCli.montaSql( false, "TIPOCLI", "VD" );
		lcTipoCli.setReadOnly( true );
		txtCodTipoCli.setTabelaExterna( lcTipoCli, null );
		txtCodTipoCli.setFK( true );
		txtCodTipoCli.setNomeCampo( "CodTipoCli" );
		
		lcConta.add( new GuardaCampo( txtNumConta, "numconta", "Num.Conta", ListaCampos.DB_PK, false ) );
		lcConta.add( new GuardaCampo( txtDescConta, "descconta", "Descrição da conta", ListaCampos.DB_SI, false ) );
		lcConta.montaSql( false, "CONTA", "FN" );
		lcConta.setReadOnly( true );
		txtNumConta.setTabelaExterna( lcConta, FConta.class.getCanonicalName() );
		txtNumConta.setFK( true );
		txtNumConta.setNomeCampo( "numconta" );


	}

	private void montaRadioGroups() {

		Vector<String> vVals = new Vector<String>();
		Vector<String> vLabs = new Vector<String>();
		vVals.addElement( "G" );
		vVals.addElement( "T" );
		vLabs.addElement( "Grafico" );
		vLabs.addElement( "Texto" );
		rgModo = new JRadioGroup<String, String>( 1, 2, vLabs, vVals );
		rgModo.setVlrString( "G" );

		Vector<String> vVals0 = new Vector<String>();
		Vector<String> vLabs0 = new Vector<String>();
		vLabs0.addElement( "Contas a receber" );
		vLabs0.addElement( "Contas recebidas" );
		vLabs0.addElement( "Ambas as contas" );
		vVals0.addElement( "R" );
		vVals0.addElement( "P" );
		vVals0.addElement( "A" );
		rgTipoRel = new JRadioGroup<String, String>( 1, 3, vLabs0, vVals0 );
		rgTipoRel.addRadioGroupListener( this );

		Vector<String> vVals1 = new Vector<String>();
		Vector<String> vLabs1 = new Vector<String>();
		vLabs1.addElement( "Emissão" );
		vLabs1.addElement( "Vencimento" );
		vLabs1.addElement( "Pagamento" );
		vVals1.addElement( "E" );
		vVals1.addElement( "V" );
		vVals1.addElement( "P" );
		rgOrdem = new JRadioGroup<String, String>( 1, 3, vLabs1, vVals1 );
		rgOrdem.setVlrString( "V" );

		Vector<String> vVals2 = new Vector<String>();
		Vector<String> vLabs2 = new Vector<String>();
		vLabs2.addElement( "Razão Social" );
		vLabs2.addElement( "Documento" );
		vVals2.addElement( "R" );
		vVals2.addElement( "D" );
		rgOrdem2 = new JRadioGroup<String, String>( 1, 2, vLabs2, vVals2 );
		rgOrdem2.setVlrString( "R" );
	}

	private void montaTela() {

		cbRecPar.setVlrString( "S" );
		JLabelPad lbLinha = new JLabelPad();
		lbLinha.setBorder( BorderFactory.createEtchedBorder() );
		adic( new JLabelPad( "Período:" ), 7, 0, 80, 20 );
		adic( lbLinha, 7, 20, 330, 50 );
		adic( new JLabelPad( "De:", SwingConstants.LEFT ), 17, 20, 30, 20 );
		adic( txtDataini, 17, 40, 100, 20 );
		adic( new JLabelPad( "Até:", SwingConstants.LEFT ), 120, 20, 30, 20 );
		adic( txtDatafim, 120, 40, 100, 20 );
		adic( new JLabelPad( "Correção para:", SwingConstants.LEFT ), 223, 20, 100, 20 );
		adic( txtDatacor, 223, 40, 100, 20 );
		adic( new JLabelPad( "Exibição:" ), 340, 0, 150, 20 );
		adic( rgModo, 340, 20, 150, 50 );
		adic( new JLabelPad( "Modo:" ), 7, 70, 410, 20 );
		adic( rgTipoRel, 7, 90, 410, 30 );
		adic( cbObs, 420, 115, 180, 20 );
		adic( cbImpTotDia, 420, 150, 180, 20 );
		adic( cbRecPar, 420, 185, 250, 20 );
		// adic( cbAgrupCli, 420, 90, 250, 20 );
		adic( new JLabelPad( "Primeira ordem:" ), 7, 120, 390, 20 );
		adic( rgOrdem, 7, 140, 410, 30 );
		adic( new JLabelPad( "Segunda ordem:" ), 7, 170, 390, 20 );
		adic( rgOrdem2, 7, 190, 410, 30 );
		adic( new JLabelPad( "Cód.cli." ), 7, 230, 80, 20 );
		adic( txtCodCli, 7, 250, 80, 20 );
		adic( new JLabelPad( "Razão social do cliente" ), 90, 230, 210, 20 );
		adic( txtRazCli, 90, 250, 210, 20 );
		adic( new JLabelPad( "Cód.setor" ), 7, 270, 80, 20 );
		adic( txtCodSetor, 7, 290, 80, 20 );
		adic( new JLabelPad( "Descrição do setor" ), 90, 270, 210, 20 );
		adic( txtDescSetor, 90, 290, 210, 20 );
		adic( new JLabelPad( "Cód.comis." ), 7, 310, 80, 20 );
		adic( txtCodVend, 7, 330, 80, 20 );
		adic( new JLabelPad( "Nome do comissionado" ), 90, 310, 210, 20 );
		adic( txtNomeVend, 90, 330, 210, 20 );
		adic( new JLabelPad( "Cod.Tp.Cob" ), 303, 230, 80, 20 );
		adic( txtCodTpCob, 303, 250, 80, 20 );
		adic( new JLabelPad( "Descrição do Tipo de Cobrança" ), 386, 230, 220, 20 );
		adic( txtDescTpCob, 386, 250, 220, 20 );
		adic( txtCodBanco, 303, 290, 80, 20, "Cód.banco" );
		adic( txtDescBanco, 386, 290, 220, 20, "Descrição do banco" );
		adic( txtNumConta, 303, 330, 80, 20, "Num.Conta" );
		adic( txtDescConta, 386, 330, 220, 20, "Descrição da conta" );
		adic( txtCodPlanoPag, 303, 370, 80, 20, "Cód.pl.pag." );
		adic( txtDescPlanoPag, 386, 370, 220, 20, "Descrição do plano de pagamento" );
		adic( txtCodTipoCli, 7, 370, 80, 20, "Cód.Tipo.Cli" );
		adic( txtDescTipoCli, 90, 370, 210, 20, "Descrição do tipo de cliente" );

	}

	public void imprimir( TYPE_PRINT bVisualizar ) {

		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuilder sql = new StringBuilder();
		StringBuilder where = new StringBuilder();
		StringBuilder from = new StringBuilder(" ");
		StringBuilder filtro = new StringBuilder("");
		String tiporel = null;
		String titrel = null;
		String titrel1 = null;
		String dttotal = "";
		String dtpago = "";
		String obs = "";
		String imptotdia = "";
		String codbanco = null;
		String numconta = null;
		String codtpcob = null;
		String codplanopag = null;
		String campototal = null;
		String campoordem = null;
		String campoordem2 = null;
		String recparc = cbRecPar.getVlrString();
		int codcli = 0;
		int codsetor = 0;
		int codvend = 0;
		int param = 0;
		if ( txtDatafim.getVlrDate().before( txtDataini.getVlrDate() ) ) {
			Funcoes.mensagemInforma( this, "Data final maior que a data inicial!" );
			return;
		}
		if ( ( ( rgTipoRel.getVlrString().equals( "R" ) ) || ( rgTipoRel.getVlrString().equals( "A" ) ) ) && ( rgOrdem.getVlrString().equals( "P" ) ) ) {
			Funcoes.mensagemInforma( this, "Não pode ser ordenado por data de pagamento!" );
			return;
		}
		boolean bcorrecao = ( !Funcoes.dataAAAAMMDD( txtDatacor.getVlrDate() ).equals( Funcoes.dataAAAAMMDD( calAtual.getTime() ) ) );
		tiporel = rgTipoRel.getVlrString();
		if ( tiporel.equals( "R" ) ) {
			titrel = "A RECEBER";
		}
		else if ( tiporel.equals( "P" ) ) {
			titrel = "RECEBIDAS";
		}
		else if ( tiporel.equals( "A" ) ) {
			titrel = "A RECEBER/RECEBIDAS";
		}
		if ( rgOrdem.getVlrString().equals( "P" ) ) {
			titrel1 = "PAGAMENTO";
			campoordem = "it.dtpagoitrec";
			campototal = "dtpagoitrec";
		}
		else if ( rgOrdem.getVlrString().equals( "E" ) ) {
			titrel1 = "EMISSÂO";
			campoordem = "it.dtitrec";
			campototal = "dtitrec";
		}
		else {
			titrel1 = "VENCIMENTO";
			campoordem = "it.dtvencitrec";
			campototal = "dtvencitrec";
		}
		if ( rgOrdem2.getVlrString().equals( "R" ) ) {
			campoordem2 = "c.razcli";
		}
		else {
			campoordem2 = "r.docrec";
		}
		codcli = txtCodCli.getVlrInteger().intValue();
		codsetor = txtCodSetor.getVlrInteger().intValue();
		codvend = txtCodVend.getVlrInteger().intValue();
		codbanco = txtCodBanco.getVlrString();
		numconta = txtNumConta.getVlrString();
		codplanopag = txtCodPlanoPag.getVlrString();
		codtpcob = txtCodTpCob.getVlrString();

		if ( codcli != 0 ) {
			where.append( " AND R.CODEMPCL=? AND R.CODFILIALCL=? AND R.CODCLI=? " );
			filtro.append( "Cli.: " + codcli + " - " + Funcoes.copy( txtRazCli.getVlrString(), 30 ).trim() );
		}
		if ( codsetor != 0 ) {
			if ( bPref ) {
				where.append( " AND C.CODEMPSR=? AND C.CODFILIALSR=? AND C.CODSETOR=?" );
			}
			else {
				where.append( " AND VD.CODEMPSE=? AND VD.CODFILIALSE=? AND VD.CODSETOR=? ");
//				sWhere.append( " AND VD.CODEMPSE=? AND VD.CODFILIALSE=? AND VD.CODSETOR=? AND  VD.CODEMP=R.CODEMPVD AND VD.CODFILIAL=R.CODFILIALVD AND VD.CODVEND=R.CODVEND " );
//				sFrom = ",VDVENDEDOR VD ";
			}
			if (filtro.length()!=0)	
				filtro.append(" / ");
			filtro.append( "Setor: " + codsetor + " - " + Funcoes.copy( txtDescSetor.getVlrString(), 30 ).trim() );
		}
		if ( codvend != 0 ) {
			where.append( " and r.codempvd=? and r.codfilialvd=? and r.codvend=? " );
			if (filtro.length()!=0) 
				filtro.append(" / ");
			filtro.append( "Repr.: " + codvend + " - " + Funcoes.copy( txtNomeVend.getVlrString(), 30 ).trim() );
		}
		if ( codbanco.length() > 0 ) {
			where.append( " and it.codempbo=? and it.codfilialbo=? and it.codbanco=? " );
			if (filtro.length()!=0) 
				filtro.append(" / ");
			filtro.append( "Banco.: " + codbanco + " - " + Funcoes.copy( txtCodBanco.getVlrString(), 30 ).trim() );
		}
		if ( numconta.length() > 0 ) {
			where.append( " and it.codempca=? and it.codfilialca=? and it.numconta=? " );
			if (filtro.length()!=0) 
				filtro.append(" / ");
			filtro.append( "Conta.: " + numconta + " - " + Funcoes.copy( txtDescConta.getVlrString(), 30 ).trim() );
		}
		if ( codplanopag.length() > 0 ) {
			where.append( " and r.codemppg=? and r.codfilialpg=? and r.codplanopag=? " );
			if (filtro.length()!=0) 
				filtro.append(" / ");
			filtro.append( "Plano.p.: " + codplanopag + " - " + Funcoes.copy( txtCodPlanoPag.getVlrString(), 30 ).trim() );
		}
		if ( codtpcob.length() > 0 ) {
			where.append( "and it.codemptc=? and it.codfilialtc=? and it.codtipocob=? " );
			if (filtro.length()!=0) 
				filtro.append(" / ");
			filtro.append( "Tipo cobr.: " + codtpcob + " - " + Funcoes.copy( txtCodTpCob.getVlrString(), 30 ).trim() );
		}
		if ( txtCodTipoCli.getVlrInteger() > 0 ) {
			where.append( "and c.codempti=? and c.codfilialti=? and c.codtipocli=? " );
			if (filtro.length()!=0) 
				filtro.append(" / ");
			filtro.append( "Tipo Cli.: " + txtCodTipoCli.getVlrString() + " - " + Funcoes.copy( txtDescTipoCli.getVlrString(), 30 ).trim() );
		}
		sql.append( "select it.nparcitrec, it.codrec, it.dtitrec, it.dtvencitrec,it.nparcitrec,r.codvenda,r.codcli,c.razcli, it.dtpagoitrec, r.docrec, " );
		sql.append( "coalesce(l.histsublanca,it.obsitrec) obsitrec, v.statusvenda, it.vlrparcitrec");
		sql.append( ", (case when coalesce(it.descpont,'n')='s' ");
		sql.append( " and it.dtliqitrec is null and (cast('today' as date)-it.dtvencitrec)>0 then it.vlritrec+coalesce(it.vlrdescitrec,0) " );
		sql.append( "else it.vlritrec end ) vlritrec "); 
		sql.append( ", (case when coalesce(it.descpont,'n')='s' ");
		sql.append( " and it.dtliqitrec is null and (cast('today' as date)-it.dtvencitrec)>0 then it.vlrapagitrec+coalesce(it.vlrdescitrec,0) " );
		sql.append( "else it.vlrapagitrec end) vlrapagitrec, coalesce(l.vlrsublanca*-1 , it.vlrpagoitrec) vlrpagoitrec, ");
		sql.append( "it.vlrpagoitrec as vlrpagoitrectot, l.datasublanca datalanca from fnitreceber it " );
		sql.append( "left outer join fnreceber r on (it.codemp = r.codemp and it.codfilial = r.codfilial and it.codrec = r.codrec) " );
		sql.append( "left outer join vdcliente c on (c.codemp = r.codemp and c.codfilial = r.codfilial and c.codcli = r.codcli) " );
		sql.append( "left outer join vdvenda v on (v.codemp = r.codemp and v.codfilial = r.codfilialva and v.codvenda = r.codvenda and v.tipovenda = r.tipovenda ) " );
		sql.append( "left outer join fnsublanca l on (l.codemp = r.codemp and l.codfilial = it.codfilial and l.codrec = it.codrec and l.nparcitrec = it.nparcitrec and l.codsublanca<>0)" );
		if(codsetor != 0 && !bPref ){
			sql.append( "left outer join vdvendedor vd on(vd.codemp = r.codempvd and vd.codfilial = r.codfilialvd and vd.codvend = r.codvend )" );
		}
		sql.append( "where r.codemp = ? and r.codfilial = ? and "+ campoordem +" between ? and ? " );
		sql.append( "and it.statusitrec in (?,?,?) " );
		sql.append( "and r.flag in " + AplicativoPD.carregaFiltro( con, org.freedom.library.swing.frame.Aplicativo.iCodEmp ) + " ");
		sql.append( where );
		sql.append( " order by " + campoordem + " ," + campoordem2 );
		try {
			param = 1;
			ps = con.prepareStatement( sql.toString() );
			ps.setInt( param++, Aplicativo.iCodEmp );
			ps.setInt( param++, ListaCampos.getMasterFilial( "FNRECEBER" ) );
			ps.setDate( param++, Funcoes.dateToSQLDate( txtDataini.getVlrDate() ) );
			ps.setDate( param++, Funcoes.dateToSQLDate( txtDatafim.getVlrDate() ) );

			StringBuilder sCab = new StringBuilder();
			sCab.append( "CONTAS ");
			sCab.append( titrel ); 
			sCab.append( " DE :" );
			sCab.append( txtDataini.getVlrString() );
			sCab.append( " ATE: " );
			sCab.append( txtDatafim.getVlrString() );
			sCab.append( " POR: " );
			sCab.append( titrel1 );
			sCab.append( "CORREÇÃO PARA: " );
			sCab.append(txtDatacor.getVlrString());
			if ( numconta.length() > 0 ) {
				sCab.append( " - CONTA: " + numconta );
			}
			if ( tiporel.equals( "R" ) ) {
				ps.setString( param++, "R1" );
				if ("S".equals( recparc )) {
					ps.setString( param++, "RL" );
					ps.setString( param++, "RL" );
				} else {
					ps.setString( param++, "R1" );
					ps.setString( param++, "R1" );
				}
			}
			else if ( tiporel.equals( "P" ) ) {
				ps.setString( param++, "RP" );
				if ("S".equals( recparc )) {
					ps.setString( param++, "RL" );
					ps.setString( param++, "RL" );
				} else {
					ps.setString( param++, "RP" );
					ps.setString( param++, "RP" );
				}
			}
			else if ( tiporel.equals( "A" ) ) {
				ps.setString( param++, "R1" );
				ps.setString( param++, "RL" );
				ps.setString( param++, "RP" );
			}
			if ( codcli != 0 ) {
				ps.setInt( param++, Aplicativo.iCodEmp );
				ps.setInt( param++, ListaCampos.getMasterFilial( "VDCLIENTE" ) );
				ps.setInt( param++, codcli );
			}
			if ( codsetor != 0 ) {
				ps.setInt( param++, Aplicativo.iCodEmp );
				ps.setInt( param++, ListaCampos.getMasterFilial( "VDSETOR" ) );
				ps.setInt( param++, codsetor );
			}
			if ( codvend != 0 ) {
				ps.setInt( param++, Aplicativo.iCodEmp );
				ps.setInt( param++, ListaCampos.getMasterFilial( "VDVENDEDOR" ) );
				ps.setInt( param++, codvend );
			}
			if ( codbanco.length() > 0 ) {
				ps.setInt( param++, Aplicativo.iCodEmp );
				ps.setInt( param++, ListaCampos.getMasterFilial( "FNBANCO" ) );
				ps.setString( param++, codbanco );
			}
			if ( numconta.length() > 0 ) {
				ps.setInt( param++, Aplicativo.iCodEmp );
				ps.setInt( param++, ListaCampos.getMasterFilial( "FNCONTA" ) );
				ps.setString( param++, numconta );
			}
			if ( codplanopag.length() > 0 ) {
				ps.setInt( param++, Aplicativo.iCodEmp );
				ps.setInt( param++, ListaCampos.getMasterFilial( "FNPLANOPAG" ) );
				ps.setString( param++, codplanopag );
			}
			if ( codtpcob.length() > 0 ) {
				ps.setInt( param++, Aplicativo.iCodEmp );
				ps.setInt( param++, ListaCampos.getMasterFilial( "FNITRECEBER" ) );
				ps.setString( param++, codtpcob );
			}
			if ( txtCodTipoCli.getVlrInteger() > 0 ) {
				ps.setInt( param++, lcTipoCli.getCodEmp() );
				ps.setInt( param++, lcTipoCli.getCodFilial() );
				ps.setInt( param++, txtCodTipoCli.getVlrInteger() );
			}
			rs = ps.executeQuery();
			if (bVisualizar==TYPE_PRINT.EXPORT) {
				if (btExportXLS.execute(rs, getTitle())) {
					Funcoes.mensagemInforma( this, "Arquivo exportado com sucesso !" );
				}
				try {
					rs.close();
					con.commit();
				} catch ( SQLException e ) {
					e.printStackTrace();
				}
			} else {
				if ( "G".equals( rgModo.getVlrString() ) ) {
					imprimirGrafico( bVisualizar, rs, sCab.toString() );
				}
				else {
					imprimirTexto( bVisualizar, rs, sCab.toString(), campototal );
				}
			}
			rs.close();
			ps.close();
			con.commit();
		} catch ( SQLException err ) {
			Funcoes.mensagemErro( this, "Erro consulta tabela de contas a receber!\n" + err.getMessage(), true, con, err );
			err.printStackTrace();
		}
	}

	private void imprimirGrafico( final TYPE_PRINT bVisualizar, final ResultSet rs, final String sCab ) {

		FPrinterJob dlGr = null;
		HashMap<String, Object> hParam = new HashMap<String, Object>();

		hParam.put( "CODEMP", Aplicativo.iCodEmp );
		hParam.put( "CODFILIAL", ListaCampos.getMasterFilial( "FNPAGAR" ) );
		hParam.put( "RAZAOEMP", Aplicativo.empresa.toString() );
		hParam.put( "FILTROS", sCab );
		hParam.put( "TOTDIARIO", cbImpTotDia.getVlrString() );

		
		dlGr = new FPrinterJob( "relatorios/ReceberRecebidas.jasper", "Relatório de contas", sCab, rs, hParam, this );

		if ( bVisualizar==TYPE_PRINT.VIEW ) {
			dlGr.preview();
		}
		else {
			try {
				dlGr.print(true);
			} catch ( Exception err ) {
				Funcoes.mensagemErro( this, "Erro na impressão de relatório por razão!" + err.getMessage(), true, con, err );
			}
		}
	}

	private void imprimirTexto( TYPE_PRINT bVisualizar, ResultSet rs, String sCab, String sCampoTotal ) {

		String sDtTotal = "";
		String sDtPago = "";
		Vector<String> vObs = null;
		ImprimeOS imp = null;
		int linPag = 0;
		double deTotalDiaParc = 0;
		double deTotalDiaPago = 0;
		double deTotalDiaApag = 0;
		double deTotParc = 0;
		double deTotalPago = 0;
		double deTotalApag = 0;
		boolean bFimDia = false;
		int codrec = -1;
		int nparcitrec = -1;

		try {

			imp = new ImprimeOS( "", con );
			linPag = imp.verifLinPag() - 1;
			imp.limpaPags();
			imp.montaCab();
			imp.setTitulo( "Relatório de contas" );
			imp.addSubTitulo( sCab );

			while ( rs.next() ) {
				if ( imp.pRow() >= ( linPag - 1 ) ) {
					imp.pulaLinha( 1, imp.comprimido() );
					imp.say( 0, "+" + StringFunctions.replicate( "-", 133 ) + "+" );
					imp.incPags();
					imp.eject();
				}

				if ( imp.pRow() == 0 ) {
					imp.impCab( 136, true );
					imp.say( 0, imp.comprimido() );
					imp.say( 0, "|" + StringFunctions.replicate( "-", 133 ) + "|" );
					imp.pulaLinha( 1, imp.comprimido() );
					imp.say( 0, "| Vencto.    |" );
					imp.say( 15, " Cliente                                  |" );
					imp.say( 59, " Doc.      |" );
					imp.say( 72, " Vlr. da Parc. |" );
					imp.say( 89, " Vlr Recebido  |" );
					imp.say( 106, " Vlr Aberto   |" );
					imp.say( 122, " Data Receb. |" );
					imp.pulaLinha( 1, imp.comprimido() );
					imp.say( 0, "|" + StringFunctions.replicate( "-", 133 ) + "|" );
				}

				if ( ( !StringFunctions.sqlDateToStrDate( rs.getDate( sCampoTotal ) ).equals( sDtTotal ) ) && ( bFimDia ) && ( cbImpTotDia.getVlrString().equals( "S" ) ) ) {
					imp.pulaLinha( 1, imp.comprimido() );
					imp.say( 0, "|" + StringFunctions.replicate( "-", 133 ) + "|" );
					imp.pulaLinha( 1, imp.comprimido() );
					imp.say( 0, "|" );
					imp.say( 41, "Totais do Dia-> | " + sDtTotal + " | " + Funcoes.strDecimalToStrCurrency( 14, 2, String.valueOf( deTotalDiaParc ) ) + " | " + Funcoes.strDecimalToStrCurrency( 14, 2, String.valueOf( deTotalDiaPago ) ) + " | "
							+ Funcoes.strDecimalToStrCurrency( 13, 2, String.valueOf( deTotalDiaApag ) ) + " | " );
					imp.say( 135, "|" );
					imp.pulaLinha( 1, imp.comprimido() );
					imp.say( 0, "|" + StringFunctions.replicate( "-", 133 ) + "|" );
					deTotalDiaParc = 0;
					deTotalDiaPago = 0;
					deTotalDiaApag = 0;
					bFimDia = false;
				}

				imp.pulaLinha( 1, imp.comprimido() );
				imp.say( 0, "|" );

				if ( ( !"V".equals( rgOrdem.getVlrString() ) ) || ( !StringFunctions.sqlDateToStrDate( rs.getDate( sCampoTotal ) ).equals( sDtTotal ) ) ) {
					imp.say( 3, StringFunctions.sqlDateToStrDate( rs.getDate( "DtVencItRec" ) ) );
				}

				imp.say( 14, "| " + Funcoes.copy( rs.getString( "CodCli" ), 0, 6 ) + "-" + Funcoes.copy( rs.getString( "RazCli" ), 0, 33 ) + " |" );

				if ( rs.getString( "DtPagoItRec" ) != null ) {
					sDtPago = StringFunctions.sqlDateToStrDate( rs.getDate( "DtPagoItRec" ) );
				}
				else {
					sDtPago = " ";
				}

				sDtPago = Funcoes.copy( sDtPago, 0, 10 );

				imp.say( 61, ( Funcoes.copy( rs.getString( 10 ), 0, 1 ).equals( "P" ) ? Funcoes.copy( rs.getString( "CodVenda" ), 0, 6 ) : Funcoes.copy( rs.getString( "DocRec" ), 0, 6 ) ) + "/" + 
						Funcoes.copy( rs.getString( "NParcItRec" ), 0, 2 ) + "| " +
						( (codrec!=rs.getInt( "CODREC" ) ) || (nparcitrec!=rs.getInt( "NPARCITREC" )) ?
						Funcoes.strDecimalToStrCurrency( 14, 2, rs.getString( "VlrItRec" ) ) : StringFunctions.replicate( " ", 14 ))
						+ " | " + Funcoes.strDecimalToStrCurrency( 14, 2, rs.getString( "VlrPagoItRec" ) ) + 
						" | " +
						( (codrec!=rs.getInt( "CODREC" ) ) || (nparcitrec!=rs.getInt( "NPARCITREC" )) ? 
						Funcoes.strDecimalToStrCurrency( 13, 2, rs.getString( "VlrApagItRec" ) ) : StringFunctions.replicate( " ", 13 ) )
						+ " | " + " " + sDtPago + "  |" );
				if ( cbObs.getVlrString().equals( "S" ) ) {
					if ( rs.getString( "OBSITREC" ) != null ) {
						vObs = getObs( rs.getString( "OBSITREC" ), 108 );
						for ( int i = 0; i < vObs.size(); i++ ) {
							imp.pulaLinha( 1, imp.comprimido() );
							imp.say( 0, "|" );
							imp.say( 16, ( i == 0 ? "OBS.: " : "      " ) + vObs.elementAt( i ).toString() );
							imp.say( 135, "|" );
						}
					}
				}
				if ( (codrec!=rs.getInt( "CODREC" ) ) || (nparcitrec!=rs.getInt( "NPARCITREC" )) ) {
					if ( rs.getString( "VlrItRec" ) != null ) {
						deTotalDiaParc += rs.getDouble( "VlrItRec" );
						deTotParc += rs.getDouble( "VlrItRec" );
					}
					if ( rs.getString( "VlrApagItRec" ) != null ) {
						deTotalDiaApag += rs.getDouble( "VlrApagItRec" );
						deTotalApag += rs.getDouble( "VlrApagItRec" );
					}
				}
				if ( rs.getString( "VlrPagoItRec" ) != null ) {
					deTotalDiaPago += rs.getDouble( "VlrPagoItRec" );
					deTotalPago += rs.getDouble( "VlrPagoItRec" );
				}
				codrec = rs.getInt( "CODREC" );
				nparcitrec = rs.getInt( "NPARCITREC" );

				bFimDia = true;
				sDtTotal = StringFunctions.sqlDateToStrDate( rs.getDate( sCampoTotal ) );
			}

			if ( ( bFimDia ) && ( cbImpTotDia.getVlrString().equals( "S" ) ) ) {
				imp.pulaLinha( 1, imp.comprimido() );
				imp.say( 0, "|" + StringFunctions.replicate( "-", 133 ) + "|" );
				imp.pulaLinha( 1, imp.comprimido() );
				imp.say( 0, "|" );
				imp.say( 41, "Totais do Dia-> | " + sDtTotal + " | " + Funcoes.strDecimalToStrCurrency( 14, 2, String.valueOf( deTotalDiaParc ) ) + " | " + Funcoes.strDecimalToStrCurrency( 14, 2, String.valueOf( deTotalDiaPago ) ) + " | "
						+ Funcoes.strDecimalToStrCurrency( 13, 2, String.valueOf( deTotalDiaApag ) ) );
				imp.say( 135, "|" );
			}

			imp.pulaLinha( 1, imp.comprimido() );
			imp.say( 0, "|" + StringFunctions.replicate( "=", 133 ) + "|" );
			imp.pulaLinha( 1, imp.comprimido() );
			imp.say( 0, "|" );
			imp.say( 55, "Totais Geral-> | " + Funcoes.strDecimalToStrCurrency( 14, 2, String.valueOf( deTotParc ) ) + " | " + Funcoes.strDecimalToStrCurrency( 14, 2, String.valueOf( deTotalPago ) ) + " | " + Funcoes.strDecimalToStrCurrency( 13, 2, String.valueOf( deTotalApag ) ) );
			imp.say( 135, "|" );
			imp.pulaLinha( 1, imp.comprimido() );
			imp.say( 0, "+" + StringFunctions.replicate( "=", 133 ) + "+" );

			imp.eject();
			imp.fechaGravacao();

			if ( bVisualizar==TYPE_PRINT.VIEW ) {
				imp.preview( this );
			}
			else {
				imp.print();
			}
		} catch ( SQLException e ) {
			e.printStackTrace();
		}
	}

	private Vector<String> getObs( String sObs, int iTam ) {

		Vector<String> vRetorno = null;
		boolean bFim = false;
		try {
			sObs = sObs.replaceAll( ( (char) 13 ) + "", " " );
			sObs = sObs.replaceAll( ( (char) 10 ) + "", "" );
			sObs = sObs.trim();
			vRetorno = new Vector<String>();
			if ( !sObs.equals( "" ) ) {
				while ( !bFim ) {
					if ( sObs.length() <= iTam ) {
						vRetorno.addElement( sObs );
						bFim = true;
					}
					else {
						vRetorno.addElement( sObs.substring( 0, iTam ) );
						sObs = sObs.substring( iTam );
					}
				}
			}
		} finally {
			sObs = null;
			bFim = false;
		}
		return vRetorno;
	}

	private boolean getPrefere() {

		boolean retorno = false;

		try {

			PreparedStatement ps = con.prepareStatement( "SELECT SETORVENDA FROM SGPREFERE1 WHERE CODEMP=? AND CODFILIAL=?" );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, Aplicativo.iCodFilial );
			ResultSet rs = ps.executeQuery();

			if ( rs.next() ) {
				retorno = rs.getString( "SETORVENDA" ) != null && "CA".indexOf( rs.getString( "SETORVENDA" ) ) >= 0;
			}

			rs.close();
			ps.close();

			con.commit();
		} catch ( SQLException err ) {
			Funcoes.mensagemErro( null, "Erro ao verificar preferências!\n" + err.getMessage(), true, con, err );
			err.printStackTrace();
		}

		return retorno;
	}

	public void setConexao( DbConnection cn ) {

		super.setConexao( cn );
		lcCli.setConexao( cn );
		lcSetor.setConexao( cn );
		lcVendedor.setConexao( cn );
		lcBanco.setConexao( cn );
		lcPlanoPag.setConexao( cn );
		lcTipoCob.setConexao( cn );
		lcTipoCli.setConexao( cn );
		lcConta.setConexao( cn );
		bPref = getPrefere();
	}

	public void valorAlterado( RadioGroupEvent evt ) {

		if ( evt.getIndice() == 1 ) {
			rgOrdem.setVlrString( "P" );
		}
		else {
			rgOrdem.setVlrString( "V" );
		}
	}
}
