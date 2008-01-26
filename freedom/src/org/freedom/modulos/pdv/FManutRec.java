/**
 * @version 25/02/2008 <BR>
 * @author Setpoint Informática Ltda./Reginaldo Garcia Heua <BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.pdv <BR>
 * Classe:
 * @(#)FManutRec.java <BR>
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
 * Tela de fechamento de venda no PDV.
 * 
 */

package org.freedom.modulos.pdv;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Calendar;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JScrollPane;

import org.freedom.acao.CarregaEvent;
import org.freedom.acao.CarregaListener;
import org.freedom.bmps.Icone;
import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JPanelPad;
import org.freedom.componentes.JTabbedPanePad;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.Tabela;
import org.freedom.ecf.app.ControllerECF;
import org.freedom.funcoes.Funcoes;
import org.freedom.modulos.std.DLBaixaRec;
import org.freedom.modulos.std.DLBaixaRec.EColBaixa;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.AplicativoPDV;
import org.freedom.telas.FFDialogo;

public class FManutRec extends FFDialogo implements ActionListener, CarregaListener {

	private static final long serialVersionUID = 1L;
	
	private final ControllerECF ecf;
	
	private Tabela tabBaixa = new Tabela();

	private JScrollPane spnBaixa = new JScrollPane( tabBaixa );
	
	private JTabbedPanePad tpn = new JTabbedPanePad();
	
	private JPanelPad pinBaixa = new JPanelPad( 500, 140 );
	
	private JPanelPad pnTabBaixa = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );
	
	private JPanelPad pinBotoesBaixa = new JPanelPad( 40, 100 );
	
	private JPanelPad pnBaixa = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );
	
	private JPanelPad pnRodape = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );
	
	private JPanelPad pnBotoes = new JPanelPad(  JPanelPad.TP_JPANEL, new BorderLayout() );
	
	private JPanelPad pnBtOk = new JPanelPad(  JPanelPad.TP_JPANEL, new BorderLayout() );
	
	private JPanelPad pnBtCancel = new JPanelPad(  JPanelPad.TP_JPANEL, new BorderLayout() );
	
	private JTextFieldPad txtCodCli = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	
	private JTextFieldFK txtRazCli = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );
	
	private JTextFieldPad txtCodRecBaixa = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	
	private JTextFieldPad txtDoc = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	
	private JTextFieldPad txtSerie = new JTextFieldPad( JTextFieldPad.TP_STRING, 4, 0 );
	
	private JTextFieldPad txtCodVendaBaixa = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	
	private JTextFieldPad txtCodCliBaixa = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	
	private JTextFieldFK txtRazCliBaixa = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );
	
	private JTextFieldFK txtDescBancoBaixa = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );
	
	private JTextFieldPad txtDtEmisBaixa = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );
	
	private JTextFieldPad txtTotRecBaixa = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 15, 2 );
	
	private JTextFieldPad txtTotPagoBaixa = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 15, 2 );
	
	private JTextFieldPad txtTotAbertoBaixa = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 15, 2 );
	
	private JTextFieldPad txtJurosBaixa = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 15, 2 );
	
	private JTextFieldPad txtCodBancoBaixa = new JTextFieldPad( JTextFieldPad.TP_STRING, 3, 0 );
	
	private JTextFieldPad txtPrimCompr = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );
	
	private JTextFieldPad txtUltCompr = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );
	
	private JTextFieldPad txtDataMaxFat = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtVlrMaxFat = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 15, 2 );

	private JTextFieldPad txtVlrTotCompr = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 15, 2 );

	private JTextFieldPad txtVlrTotPago = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 15, 2 );

	private JTextFieldPad txtVlrTotAberto = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 15, 2 );

	private JTextFieldPad txtDataMaxAcum = new JTextFieldPad( JTextFieldPad.TP_STRING, 30, 0 );

	private JTextFieldPad txtVlrMaxAcum = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 15, 2 );
	
	private JButton btCarregaBaixas = new JButton( Icone.novo( "btConsBaixa.gif" ) );
	
	private ListaCampos lcCli = new ListaCampos( this );
	
	private ListaCampos lcVendaBaixa = new ListaCampos( this );

	private ListaCampos lcRecManut = new ListaCampos( this );

	private ListaCampos lcRecBaixa = new ListaCampos( this );

	private ListaCampos lcBancoBaixa = new ListaCampos( this, "BC" );
	
	private ListaCampos lcCliBaixa = new ListaCampos( this );
	
	private ImageIcon imgVencido = Icone.novo( "clVencido.gif" );

	private ImageIcon imgPago = Icone.novo( "clPago.gif" );

	private ImageIcon imgPagoParcial = Icone.novo( "clPagoParcial.gif" );

	private ImageIcon imgNaoVencido = Icone.novo( "clNaoVencido.gif" );
	
	private JButton btBaixa = new JButton( Icone.novo( "btOk.gif" ) );
	
	private JButton btApaga = new JButton( Icone.novo( "btNada.gif" ) );
	
	private ImageIcon imgColuna = null;
	
	int iAnoCC = 0;
	
	
	private enum EColTabBaixa {
		IMGSTATUS, DTVENC, CODREC, NPARCITREC, DOC, CODVENDA, VLRPARC, 
		DTPAGTO, VLRPAGO, VLRDESC, VLRJUROS, VLRAPAG, NUMCONTA, 
		DESCCONTA, CODPLAN, DESCPLAN, CODCC, DESCCC, OBS
	};
	
	public FManutRec(){
		
		super( Aplicativo.telaPrincipal );
		setTitulo( "Receber" );
		setAtribos( 792, 480 );
		
		ecf = new ControllerECF( 
				AplicativoPDV.getEcfdriver(), 
				AplicativoPDV.getPortaECF(), 
				AplicativoPDV.bModoDemo );
		
		montaTela();
		
		montaListaCampos();
	}
	
	private void montaTela(){
		
		Container c = getContentPane();
		c.setLayout( new BorderLayout() );
		c.add( pnRodape, BorderLayout.SOUTH );
		c.add( tpn, BorderLayout.CENTER );
		
		tpn.addTab( "Baixa", pnBaixa );

		pnBaixa.add( pinBaixa, BorderLayout.NORTH );

		pnTabBaixa.add( pinBotoesBaixa, BorderLayout.EAST );
		pnTabBaixa.add( spnBaixa, BorderLayout.CENTER );
		pnTabBaixa.add( pnBotoes, BorderLayout.SOUTH );
		pnBotoes.add( pnBtOk, BorderLayout.EAST );
		pnBtOk.add( pnBtCancel, BorderLayout.EAST );
		
		pnBtOk.add( btOK );
		pnBtCancel.add( btCancel );

		pnBaixa.add( pnTabBaixa, BorderLayout.CENTER );
		
		txtDoc.setAtivo( false );
		txtCodVendaBaixa.setAtivo( false );
		txtSerie.setAtivo( false );
		//txtCodCliBaixa.setAtivo( false );
		txtDtEmisBaixa.setAtivo( false );
		txtCodBancoBaixa.setAtivo( false );
		txtTotRecBaixa.setAtivo( false );
		txtTotAbertoBaixa.setAtivo( false );
		txtTotPagoBaixa.setAtivo( false );
		txtJurosBaixa.setAtivo( false );
		
		btCarregaBaixas.addActionListener( this );
		lcRecBaixa.addCarregaListener( this );
		lcCli.addCarregaListener( this );
		btBaixa.addActionListener( this );
		btApaga.addActionListener( this );
		lcCliBaixa.addCarregaListener( this );
		
		pinBotoesBaixa.adic( btBaixa, 3, 10, 30, 30 );
		pinBaixa.adic( btApaga, 540, 90, 30, 30 );
		
		pinBaixa.adic( new JLabelPad( "Cód.rec" ), 300, 0, 80, 20 );
		pinBaixa.adic( txtCodRecBaixa, 300, 20, 80, 20 );
		pinBaixa.adic( new JLabelPad( "Doc." ), 387, 0, 77, 20 );
		pinBaixa.adic( txtDoc, 387, 20, 77, 20 );
		//pinBaixa.adic( new JLabelPad( " -" ), 467, 20, 7, 20 );
		pinBaixa.adic( new JLabelPad( "Série" ), 467, 0, 50, 20 );
		pinBaixa.adic( txtSerie, 467, 20, 50, 20 );
		pinBaixa.adic( new JLabelPad( "Pedido" ), 520, 0, 77, 20 );
		pinBaixa.adic( txtCodVendaBaixa, 520, 20, 77, 20 );
		pinBaixa.adic( new JLabelPad( "Cód.cli." ), 7, 0, 250, 20 );
		pinBaixa.adic( txtCodCliBaixa, 7, 20, 80, 20 );
		pinBaixa.adic( new JLabelPad( "Descrição do cliente" ), 90, 0, 250, 20 );
		pinBaixa.adic( txtRazCliBaixa, 90, 20, 207, 20 );
		pinBaixa.adic( new JLabelPad( "Cód.banco" ), 7, 40, 250, 20 );
		pinBaixa.adic( txtCodBancoBaixa, 7, 60, 77, 20 );
		pinBaixa.adic( new JLabelPad( "Descrição do banco" ), 90, 40, 250, 20 );
		pinBaixa.adic( txtDescBancoBaixa, 90, 60, 207, 20 );
		pinBaixa.adic( new JLabelPad( "Data de emissão" ), 7, 80, 100, 20 );
		pinBaixa.adic( txtDtEmisBaixa, 7, 100, 120, 20 );
		pinBaixa.adic( new JLabelPad( "Total a pagar" ), 130, 80, 97, 20 );
		pinBaixa.adic( txtTotRecBaixa, 130, 100, 97, 20 );
		pinBaixa.adic( new JLabelPad( "Total pago" ), 230, 80, 97, 20 );
		pinBaixa.adic( txtTotPagoBaixa, 230, 100, 97, 20 );
		pinBaixa.adic( new JLabelPad( "Total em aberto" ), 330, 80, 107, 20 );
		pinBaixa.adic( txtTotAbertoBaixa, 330, 100, 107, 20 );
		pinBaixa.adic( new JLabelPad( "Juros" ), 440, 80, 80, 20 );
		pinBaixa.adic( txtJurosBaixa, 440, 100, 90, 20 );
		
		tabBaixa.adicColuna( "" );// 0
		tabBaixa.adicColuna( "Vencimento" ); // 1
		tabBaixa.adicColuna( "Cód.rec." ); // 2
		tabBaixa.adicColuna( "Nro.parc." ); // 3
		tabBaixa.adicColuna( "Doc." ); // 4
		tabBaixa.adicColuna( "Pedido" ); // 5
		tabBaixa.adicColuna( "Valor parcela" ); // 6
		tabBaixa.adicColuna( "Data Pagamento" ); // 7
		tabBaixa.adicColuna( "Valor pago" ); // 8
		tabBaixa.adicColuna( "Valor desc." ); // 9
		tabBaixa.adicColuna( "Valor juros" ); // 10
		tabBaixa.adicColuna( "Valor aberto" ); // 11
		tabBaixa.adicColuna( "Nro.Conta" ); // 12
		tabBaixa.adicColuna( "Descrição conta" ); // 13
		tabBaixa.adicColuna( "Cód.planej." ); // 14
		tabBaixa.adicColuna( "Descrição planej." ); // 15
		tabBaixa.adicColuna( "Cód.c.c." ); // 16
		tabBaixa.adicColuna( "Descrição c.c." ); // 17
		tabBaixa.adicColuna( "Observação" ); // 18

		tabBaixa.setTamColuna( 0, EColTabBaixa.IMGSTATUS.ordinal() );
		tabBaixa.setTamColuna( 100, EColTabBaixa.DTVENC.ordinal() );
		tabBaixa.setTamColuna( 100, EColTabBaixa.CODREC.ordinal() );
		tabBaixa.setTamColuna( 100, EColTabBaixa.NPARCITREC.ordinal() );
		tabBaixa.setTamColuna( 70, EColTabBaixa.DOC.ordinal() );
		tabBaixa.setTamColuna( 70, EColTabBaixa.CODVENDA.ordinal() );
		tabBaixa.setTamColuna( 100, EColTabBaixa.VLRPARC.ordinal() );
		tabBaixa.setTamColuna( 120, EColTabBaixa.DTPAGTO.ordinal() );
		tabBaixa.setTamColuna( 100, EColTabBaixa.VLRPAGO.ordinal() );
		tabBaixa.setTamColuna( 100, EColTabBaixa.VLRDESC.ordinal() );
		tabBaixa.setTamColuna( 100, EColTabBaixa.VLRJUROS.ordinal() );
		tabBaixa.setTamColuna( 100, EColTabBaixa.VLRAPAG.ordinal() );
		tabBaixa.setTamColuna( 80, EColTabBaixa.NUMCONTA.ordinal() );
		tabBaixa.setTamColuna( 180, EColTabBaixa.DESCCONTA.ordinal() );
		tabBaixa.setTamColuna( 80, EColTabBaixa.CODPLAN.ordinal() );
		tabBaixa.setTamColuna( 180, EColTabBaixa.DESCPLAN.ordinal() );
		tabBaixa.setTamColuna( 80, EColTabBaixa.CODCC.ordinal() );
		tabBaixa.setTamColuna( 180, EColTabBaixa.DESCCC.ordinal() );
		tabBaixa.setTamColuna( 200, EColTabBaixa.OBS.ordinal() );
	}
	
	private void montaListaCampos(){
		
		lcCli.add( new GuardaCampo( txtCodCli, "CodCli", "Cód.cli.", ListaCampos.DB_PK, false ) );
		lcCli.add( new GuardaCampo( txtRazCli, "RazCli", "Razão social do clliente", ListaCampos.DB_SI, false ) );
		lcCli.montaSql( false, "CLIENTE", "VD" );
		lcCli.setQueryCommit( false );
		lcCli.setReadOnly( true );
		txtCodCli.setTabelaExterna( lcCli );
		txtCodCli.setFK( true );
		txtCodCli.setNomeCampo( "CodCli" );
		
		
		lcVendaBaixa.add( new GuardaCampo( txtCodVendaBaixa, "CodVenda", "Cód.venda", ListaCampos.DB_PK, false ) );
		lcVendaBaixa.add( new GuardaCampo( txtSerie, "Serie", "Série", ListaCampos.DB_SI, false ) );
		lcVendaBaixa.montaSql( false, "VENDA", "VD" );
		lcVendaBaixa.setQueryCommit( false );
		lcVendaBaixa.setReadOnly( true );
		txtCodVendaBaixa.setTabelaExterna( lcVendaBaixa );
		txtCodVendaBaixa.setFK( true );
		txtCodVendaBaixa.setNomeCampo( "CodVenda" );

		lcCliBaixa.add( new GuardaCampo( txtCodCliBaixa, "CodCli", "Cód.cli", ListaCampos.DB_PK, false ) );
		lcCliBaixa.add( new GuardaCampo( txtRazCliBaixa, "RazCli", "Razão social do cliente", ListaCampos.DB_SI, false ) );
		lcCliBaixa.montaSql( false, "CLIENTE", "VD" );
		lcCliBaixa.setQueryCommit( false );
		lcCliBaixa.setReadOnly( true );
		txtCodCliBaixa.setTabelaExterna( lcCliBaixa );
		txtCodCliBaixa.setFK( true );
		txtCodCliBaixa.setNomeCampo( "CodCli" );

		lcBancoBaixa.add( new GuardaCampo( txtCodBancoBaixa, "CodBanco", "Cód.banco", ListaCampos.DB_PK, false ) );
		lcBancoBaixa.add( new GuardaCampo( txtDescBancoBaixa, "NomeBanco", "Nome do banco", ListaCampos.DB_SI, false ) );
		lcBancoBaixa.montaSql( false, "BANCO", "FN" );
		lcBancoBaixa.setQueryCommit( false );
		lcBancoBaixa.setReadOnly( true );
		txtCodBancoBaixa.setTabelaExterna( lcBancoBaixa );
		txtCodBancoBaixa.setFK( true );
		txtCodBancoBaixa.setNomeCampo( "CodBanco" );

		lcRecBaixa.add( new GuardaCampo( txtCodRecBaixa, "CodRec", "Cód.rec.", ListaCampos.DB_PK, false ) );
		lcRecBaixa.add( new GuardaCampo( txtCodVendaBaixa, "CodVenda", "Cód.venda", ListaCampos.DB_FK, false ) );
		lcRecBaixa.add( new GuardaCampo( txtDoc, "DocRec", "Doc", ListaCampos.DB_SI, false ) );
		lcRecBaixa.add( new GuardaCampo( txtTotRecBaixa, "VlrRec", "Tot.rec.", ListaCampos.DB_SI, false ) );
		lcRecBaixa.add( new GuardaCampo( txtCodCliBaixa, "CodCli", "Cód.cli.", ListaCampos.DB_FK, false ) );
		lcRecBaixa.add( new GuardaCampo( txtDtEmisBaixa, "DataRec", "Data emissão", ListaCampos.DB_SI, false ) );
		lcRecBaixa.add( new GuardaCampo( txtCodBancoBaixa, "CodBanco", "Cód.banco", ListaCampos.DB_FK, false ) );
		lcRecBaixa.add( new GuardaCampo( txtTotAbertoBaixa, "VlrApagRec", "Total aberto", ListaCampos.DB_SI, false ) );
		lcRecBaixa.add( new GuardaCampo( txtTotPagoBaixa, "VlrPagoRec", "Total pago", ListaCampos.DB_SI, false ) );
		lcRecBaixa.add( new GuardaCampo( txtJurosBaixa, "VlrJurosRec", "Total juros", ListaCampos.DB_SI, false ) );
		lcRecBaixa.montaSql( false, "RECEBER", "FN" );
		lcRecBaixa.setQueryCommit( false );
		lcRecBaixa.setReadOnly( true );
		txtCodRecBaixa.setTabelaExterna( lcRecBaixa );
		txtCodRecBaixa.setFK( true );
		txtCodRecBaixa.setNomeCampo( "CodRec" );

		lcCli.add( new GuardaCampo( txtCodCli, "CodCli", "Cód.cli.", ListaCampos.DB_PK, false ) );
		lcCli.add( new GuardaCampo( txtRazCli, "RazCli", "Razão social do clliente", ListaCampos.DB_SI, false ) );
		lcCli.montaSql( false, "CLIENTE", "VD" );
		lcCli.setQueryCommit( false );
		lcCli.setReadOnly( true );
		txtCodCli.setTabelaExterna( lcCli );
		txtCodCli.setFK( true );
		txtCodCli.setNomeCampo( "CodCli" );
	}
	private void limpaConsulta() {

		txtDoc.setVlrString( "" );
		txtSerie.setVlrString( "" );
		txtCodVendaBaixa.setVlrString( "" );
		txtDtEmisBaixa.setVlrString( "" );
		txtTotAbertoBaixa.setVlrString( "" );
		txtTotPagoBaixa.setVlrString( "" );
		txtTotRecBaixa.setVlrString( "" );
		txtCodRecBaixa.setVlrString( "" );
		txtDescBancoBaixa.setVlrString( "" );
		txtCodCliBaixa.setVlrString( "" );
		txtRazCliBaixa.setVlrString( "" );
		txtJurosBaixa.setVlrString( "" );
		
		txtCodRecBaixa.setSoLeitura( false );
		txtCodCliBaixa.setSoLeitura( false );
		
		tabBaixa.limpa();
		
		txtCodCliBaixa.requestFocus();
	}
	
	private void carregaGridBaixa() {

		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sSQL = new StringBuffer();
		String sCodBanco = null;
		float bdVlrAReceber = 0.0f;
		float bdVlrPago = 0.0f;

		try {

			//vNParcBaixa.clear();
			tabBaixa.limpa();

			sSQL.append( "SELECT IR.DTVENCITREC,IR.STATUSITREC,R.CODREC,IR.DOCLANCAITREC," );
			sSQL.append( "R.CODVENDA,IR.VLRPARCITREC,IR.DTPAGOITREC,IR.VLRPAGOITREC," );
			sSQL.append( "IR.VLRAPAGITREC,IR.NUMCONTA,IR.VLRDESCITREC," );
			sSQL.append( "(SELECT C.DESCCONTA FROM FNCONTA C " );
			sSQL.append( "WHERE C.NUMCONTA=IR.NUMCONTA " );
			sSQL.append( "AND C.CODEMP=IR.CODEMPCA AND C.CODFILIAL=IR.CODFILIALCA) DESCCONTA,IR.CODPLAN," );
			sSQL.append( "(SELECT P.DESCPLAN FROM FNPLANEJAMENTO P " );
			sSQL.append( "WHERE P.CODPLAN=IR.CODPLAN AND P.CODEMP=IR.CODEMPPN AND P.CODFILIAL=IR.CODFILIALPN) DESCPLAN," );
			sSQL.append( "IR.CODCC," );
			sSQL.append( "(SELECT CC.DESCCC FROM FNCC CC " );
			sSQL.append( "WHERE CC.CODCC=IR.CODCC AND CC.CODEMP=IR.CODEMPCC AND CC.CODFILIAL=IR.CODFILIALCC) DESCCC," );
			sSQL.append( "IR.OBSITREC,IR.NPARCITREC,IR.VLRJUROSITREC,IR.DTITREC," );
			sSQL.append( "(SELECT V.DOCVENDA FROM VDVENDA V " );
			sSQL.append( "WHERE V.CODEMP=R.CODEMPVA " );
			sSQL.append( "AND V.CODFILIAL=R.CODFILIALVA AND V.TIPOVENDA=R.TIPOVENDA AND V.CODVENDA=R.CODVENDA) DOCVENDA," );
			sSQL.append( "IR.CODBANCO " );
			sSQL.append( "FROM FNITRECEBER IR,FNRECEBER R  " );
			sSQL.append( "WHERE IR.CODREC=R.CODREC AND R.CODREC=? AND R.CODEMP=? AND R.CODFILIAL=? " );
			sSQL.append( "ORDER BY IR.DTVENCITREC,IR.STATUSITREC" );

			ps = con.prepareStatement( sSQL.toString() );
			ps.setInt( 1, txtCodRecBaixa.getVlrInteger().intValue() );
			ps.setInt( 2, Aplicativo.iCodEmp );
			ps.setInt( 3, ListaCampos.getMasterFilial( "FNRECEBER" ) );
			rs = ps.executeQuery();

			for ( int i = 0; rs.next(); i++ ) {

				bdVlrAReceber = Funcoes.strDecimalToBigDecimal( 2, rs.getString( "VlrApagItRec" ) ).floatValue();
				bdVlrPago = Funcoes.strDecimalToBigDecimal( 2, rs.getString( "VlrPagoItRec" ) ).floatValue();

				if ( rs.getString( "StatusItRec" ).equals( "RP" ) && bdVlrAReceber == 0.0f ) {
					imgColuna = imgPago;
				}
				else if ( bdVlrPago > 0 ) {
					imgColuna = imgPagoParcial;
				}
				else if ( rs.getDate( "DtVencItRec" ).before( Calendar.getInstance().getTime() ) ) {
					imgColuna = imgVencido;
				}
				else if ( rs.getDate( "DtVencItRec" ).after( Calendar.getInstance().getTime() ) ) {
					imgColuna = imgNaoVencido;
				}

				tabBaixa.adicLinha();
				tabBaixa.setValor( imgColuna, i, EColTabBaixa.IMGSTATUS.ordinal() );
				tabBaixa.setValor( Funcoes.sqlDateToStrDate( rs.getDate( "DTVENCITREC" ) ), i, EColTabBaixa.DTVENC.ordinal() );
				tabBaixa.setValor( rs.getInt( "CODREC" ), i, EColTabBaixa.CODREC.ordinal() );
				tabBaixa.setValor( rs.getInt( "NPARCITREC" ), i, EColTabBaixa.NPARCITREC.ordinal() );
				tabBaixa.setValor( ( rs.getString( "DOCLANCAITREC" ) != null ? rs.getString( "DOCLANCAITREC" ) : String.valueOf( rs.getInt( "DOCVENDA" ) ) ), i, EColTabBaixa.DOC.ordinal() );
				tabBaixa.setValor( rs.getInt( "CODVENDA" ), i, EColTabBaixa.CODVENDA.ordinal() );
				tabBaixa.setValor( Funcoes.bdToStr( rs.getBigDecimal( "VLRPARCITREC" ) ), i, EColTabBaixa.VLRPARC.ordinal() );
				tabBaixa.setValor( Funcoes.sqlDateToStrDate( rs.getDate( "DTPAGOITREC" ) ), i, EColTabBaixa.DTPAGTO.ordinal() );
				tabBaixa.setValor( Funcoes.bdToStr( rs.getBigDecimal( "VLRPAGOITREC" ) ), i, EColTabBaixa.VLRPAGO.ordinal() );
				tabBaixa.setValor( Funcoes.bdToStr( rs.getBigDecimal( "VLRDESCITREC" ) ), i, EColTabBaixa.VLRDESC.ordinal() );
				tabBaixa.setValor( Funcoes.bdToStr( rs.getBigDecimal( "VLRJUROSITREC" ) ), i, EColTabBaixa.VLRJUROS.ordinal() );
				tabBaixa.setValor( Funcoes.bdToStr( rs.getBigDecimal( "VLRAPAGITREC" ) ), i, EColTabBaixa.VLRAPAG.ordinal() );
				tabBaixa.setValor( rs.getString( "NUMCONTA" ) != null ? rs.getString( "NUMCONTA" ) : "", i, EColTabBaixa.NUMCONTA.ordinal() );
				tabBaixa.setValor( rs.getString( "DESCCONTA" ) != null ? rs.getString( "DESCCONTA" ) : "", i, EColTabBaixa.DESCCONTA.ordinal() );
				tabBaixa.setValor( rs.getString( "CODCC" ) != null ? rs.getString( "CODCC" ) : "", i, EColTabBaixa.CODCC.ordinal() );
				tabBaixa.setValor( rs.getString( "DESCCC" ) != null ? rs.getString( "DESCCC" ) : "", i, EColTabBaixa.DESCCC.ordinal() );
				tabBaixa.setValor( rs.getInt( "NPARCITREC" ), i, EColTabBaixa.NPARCITREC.ordinal() );
				tabBaixa.setValor( rs.getString( "OBSITREC" ) != null ? rs.getString( "OBSITREC" ) : "", i, EColTabBaixa.OBS.ordinal() );
				sCodBanco = rs.getString( "CODBANCO" );

			}

			txtCodBancoBaixa.setVlrString( sCodBanco );
			lcBancoBaixa.carregaDados();

			if ( !con.getAutoCommit() ) {
				con.commit();
			}
		} catch ( SQLException err ) {
			err.printStackTrace();
			Funcoes.mensagemErro( this, "Erro ao montar a tabela de baixa!\n" + err.getMessage(), true, con, err );
		} finally {
			ps = null;
			rs = null;
			sSQL = null;
		}
	}
	private void baixar( char cOrig ) {

		PreparedStatement ps = null;
		StringBuffer sSQL = new StringBuffer();
		Object[] sVals = null;
		Object[] sRets = null;
		DLBaixaRec dl = null;
		ImageIcon imgStatusAt = null;
		int iCodRec = 0;
		int iNParcItRec = 0;


			 
		if ( ( cOrig == 'B' ) & ( tabBaixa.getLinhaSel() > -1 ) ) {

			imgStatusAt = ( (ImageIcon) tabBaixa.getValor( tabBaixa.getLinhaSel(), 0 ) );
			
			if ( imgStatusAt == imgPago ) {

				Funcoes.mensagemInforma( this, "Parcela já foi baixada!" );
					return;
				}

				
			int iLin = tabBaixa.getLinhaSel();

			iCodRec = (Integer) tabBaixa.getValor( iLin, EColTabBaixa.CODREC.ordinal() );
			iNParcItRec = (Integer) tabBaixa.getValor( iLin, EColTabBaixa.NPARCITREC.ordinal() );

			sVals = new Object[ EColBaixa.values().length ];
			// DLBaixaRec.EColBaixa.values().length
			dl = new DLBaixaRec( this );

			sVals[ EColBaixa.CODCLI.ordinal() ] = txtCodCliBaixa.getVlrInteger(); // Codcli
			sVals[ EColBaixa.RAZCLI.ordinal() ] = txtRazCliBaixa.getVlrString(); // Razcli
			sVals[ EColBaixa.NUMCONTA.ordinal() ] = tabBaixa.getValor( iLin, EColTabBaixa.NUMCONTA.ordinal() ); // NumConta
			sVals[ EColBaixa.CODPLAN.ordinal() ] = tabBaixa.getValor( iLin, EColTabBaixa.CODPLAN.ordinal() ); // Codplan
			sVals[ EColBaixa.DOC.ordinal() ] = tabBaixa.getValor( iLin, EColTabBaixa.DOC.ordinal() ); // Doc
			sVals[ EColBaixa.DTEMIT.ordinal() ] = txtDtEmisBaixa.getVlrDate(); // Data emissão
			sVals[ EColBaixa.DTVENC.ordinal() ] = Funcoes.strDateToDate( (String) tabBaixa.getValor( iLin, EColTabBaixa.DTVENC.ordinal() ) ); // Vencimento
			sVals[ EColBaixa.VLRPARC.ordinal() ] = Funcoes.strToBd( tabBaixa.getValor( iLin, EColTabBaixa.VLRPARC.ordinal() ) ); // Vlrparc
			sVals[ EColBaixa.VLRDESC.ordinal() ] = Funcoes.strToBd( tabBaixa.getValor( iLin, EColTabBaixa.VLRDESC.ordinal() ) ); // Vlrdesc
			sVals[ EColBaixa.VLRJUROS.ordinal() ] = Funcoes.strToBd( tabBaixa.getValor( iLin, EColTabBaixa.VLRJUROS.ordinal() ) ); // Vlrjuros
			sVals[ EColBaixa.VLRAPAG.ordinal() ] = Funcoes.strToBd( tabBaixa.getValor( iLin, EColTabBaixa.VLRAPAG.ordinal() ) ); // Vlraberto
			sVals[ EColBaixa.CODCC.ordinal() ] = tabBaixa.getValor( iLin, EColTabBaixa.CODCC.ordinal() ); // Codcc

			if ( "".equals( tabBaixa.getValor( iLin, EColTabBaixa.DTPAGTO.ordinal() ) ) ) { // Data de pagamento branco
				sVals[ EColBaixa.DTPGTO.ordinal() ] = Funcoes.dateToStrDate( new Date() ); // Data pagto
				sVals[ EColBaixa.VLRPAGO.ordinal() ] = Funcoes.strToBd( tabBaixa.getValor( iLin, EColTabBaixa.VLRPAGO.ordinal() ) ); // valor pago
			}
			else {
				sVals[ EColBaixa.DTPGTO.ordinal() ] = (String) tabBaixa.getValor( iLin, EColTabBaixa.DTPAGTO.ordinal() ); // Data pagto
				sVals[ EColBaixa.VLRPAGO.ordinal() ] = Funcoes.strToBd( tabBaixa.getValor( iLin, EColTabBaixa.VLRPAGO.ordinal() ) ); // valor pago
			}
			if ( "".equals( ( (String) tabBaixa.getValor( iLin, EColTabBaixa.OBS.ordinal() ) ).trim() ) ) {
				sVals[ EColBaixa.OBS.ordinal() ] = "RECEBIMENTO REF. AO PED.: " + txtCodVendaBaixa.getVlrString(); // histórico
			}
			else {
				sVals[ EColBaixa.OBS.ordinal() ] = tabBaixa.getValor( iLin, EColTabBaixa.OBS.ordinal() ); // histórico
			}

			dl.setValores( sVals );
			dl.setConexao( con );
			dl.setVisible( true );

				if ( dl.OK ) {

				sRets = dl.getValores();

				sSQL.append( "UPDATE FNITRECEBER SET NUMCONTA=?,CODEMPCA=?,CODFILIALCA=?,CODPLAN=?,CODEMPPN=?,CODFILIALPN=?," );
				sSQL.append( "ANOCC=?,CODCC=?,CODEMPCC=?,CODFILIALCC=?,DOCLANCAITREC=?,DTPAGOITREC=?,VLRPAGOITREC=VLRPAGOITREC+?," );
				sSQL.append( "VLRDESCITREC=?,VLRJUROSITREC=?,OBSITREC=?,STATUSITREC='RP' " );
				sSQL.append( "WHERE CODREC=? AND NPARCITREC=? AND CODEMP=? AND CODFILIAL=?" );

				try {

					ps = con.prepareStatement( sSQL.toString() );
					ps.setString( 1, (String) sRets[ 0 ] );
					ps.setInt( 2, Aplicativo.iCodEmp );
					ps.setInt( 3, ListaCampos.getMasterFilial( "FNCONTA" ) );
					ps.setString( 4, (String) sRets[ 1 ] );
					ps.setInt( 5, Aplicativo.iCodEmp );
					ps.setInt( 6, ListaCampos.getMasterFilial( "FNPLANEJAMENTO" ) );

					if ( "".equals( ( (String) sRets[ 7 ] ).trim() ) ) {
						ps.setNull( 7, Types.INTEGER );
						ps.setNull( 8, Types.CHAR );
						ps.setNull( 9, Types.INTEGER );
						ps.setNull( 10, Types.INTEGER );
					}
					else {
						ps.setInt( 7, iAnoCC );
						ps.setString( 8, (String) sRets[ 7 ] );
						ps.setInt( 9, Aplicativo.iCodEmp );
						ps.setInt( 10, ListaCampos.getMasterFilial( "FNCC" ) );
					}

					ps.setString( 11, (String) sRets[ 2 ] );
					ps.setDate( 12, Funcoes.dateToSQLDate( (java.util.Date) sRets[ 3 ] ) );
					ps.setBigDecimal( 13, (BigDecimal) sRets[ 4 ] );
					ps.setBigDecimal( 14, (BigDecimal) sRets[ 5 ] );
					ps.setBigDecimal( 15, (BigDecimal) sRets[ 6 ] );
					ps.setString( 16, (String) sRets[ 8 ] );
					ps.setInt( 17, iCodRec );
					ps.setInt( 18, iNParcItRec );
					ps.setInt( 19, Aplicativo.iCodEmp );
					ps.setInt( 20, ListaCampos.getMasterFilial( "FNRECEBER" ) );

					ps.executeUpdate();

					if ( !con.getAutoCommit() ) {
						con.commit();
					}
				} catch ( SQLException err ) {
					Funcoes.mensagemErro( this, "Erro ao baixar parcela!\n" + err.getMessage(), true, con, err );
				}
			}

			carregaGridBaixa();

			dl.dispose();
		}
	} 
	

	public void actionPerformed( ActionEvent evt ) {

		if ( evt.getSource() == btBaixa ) {
			baixar( 'B' );
		}else if( evt.getSource() == btApaga ){
			limpaConsulta();
		}
		
		super.actionPerformed(evt);
	}
	
	public void setConexao( Connection cn ) {

		super.setConexao( cn );
		lcCli.setConexao( cn );
		lcCliBaixa.setConexao( cn );
		lcVendaBaixa.setConexao( cn );
		lcBancoBaixa.setConexao( cn );
		lcRecBaixa.setConexao( cn );
		lcRecManut.setConexao( cn );
	}

	public void beforeCarrega( CarregaEvent cevt ) {

		
	}
	
	public void afterCarrega( CarregaEvent cevt ) {

		if ( cevt.getListaCampos() == lcRecBaixa ) {

			tabBaixa.limpa();
			carregaGridBaixa();
			txtCodCliBaixa.setSoLeitura( true );
			
			
		}else if ( cevt.getListaCampos() == lcCliBaixa ) {
			
			txtCodRecBaixa.setSoLeitura( true );
		}
	}

}
