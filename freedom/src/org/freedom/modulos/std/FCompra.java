/**
 * @version 14/07/2003 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.std <BR>
 * Classe:
 * @(#)FCompra.java <BR>
 * 
 * Este programa é licenciado de acordo com a LPG-PC (Licença Pública Geral para
 * Programas de Computador), <BR>
 * versão 2.1.0 ou qualquer versão posterior. <BR>
 * A LPG-PC deve acompanhar todas PUBLICAÇÕES, DISTRIBUIÇÕES e REPRODUÇÕES deste
 * Programa. <BR>
 * Caso uma cópia da LPG-PC não esteja disponível junto com este Programa, você
 * pode contatar <BR>
 * o LICENCIADOR ou então pegar uma cópia em: <BR>
 * Licença: http://www.lpg.adv.br/licencas/lpgpc.rtf <BR>
 * Para poder USAR, PUBLICAR, DISTRIBUIR, REPRODUZIR ou ALTERAR este Programa é
 * preciso estar <BR>
 * de acordo com os termos da LPG-PC <BR>
 * <BR>
 * 
 * Tela para cadastro de notas fiscais de compra.
 */

package org.freedom.modulos.std;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;
import java.util.Vector;

import javax.swing.JButton;

import org.freedom.acao.CarregaEvent;
import org.freedom.acao.CarregaListener;
import org.freedom.acao.InsertEvent;
import org.freedom.acao.InsertListener;
import org.freedom.acao.PostEvent;
import org.freedom.acao.PostListener;
import org.freedom.bmps.Icone;
import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.ImprimeOS;
import org.freedom.componentes.JCheckBoxPad;
import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JPanelPad;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Funcoes;
import org.freedom.layout.Layout;
import org.freedom.layout.Leiaute;
import org.freedom.layout.NFEntrada;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FDetalhe;

public class FCompra extends FDetalhe implements PostListener, CarregaListener,
		FocusListener, ActionListener, InsertListener {
	private static final long serialVersionUID = 1L;

	
	private int casasDec = Aplicativo.casasDec;
	private int casasDecFin = Aplicativo.casasDecFin;
	private JPanelPad pinCab = new JPanelPad();
	private JPanelPad pinDet = new JPanelPad();
	private JPanelPad pinTot = new JPanelPad(200, 200);
	private JPanelPad pnTot = new JPanelPad(JPanelPad.TP_JPANEL,new GridLayout(1, 1));
	private JPanelPad pnCenter = new JPanelPad(JPanelPad.TP_JPANEL,new BorderLayout());
	private JButton btFechaCompra = new JButton(Icone.novo("btOk.gif"));
	private JTextFieldPad txtCodCompra = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);
	private JTextFieldPad txtCodTipoMov = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);
	private JTextFieldPad txtDtEmitCompra = new JTextFieldPad(JTextFieldPad.TP_DATE, 10, 0);
	private JTextFieldPad txtDtEntCompra = new JTextFieldPad(JTextFieldPad.TP_DATE, 10, 0);
	private JTextFieldPad txtCodFor = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);
	private JTextFieldPad txtCodPlanoPag = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);
	private JTextFieldPad txtCodItCompra = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);
	private JTextFieldPad txtQtdItCompra = new JTextFieldPad(JTextFieldPad.TP_NUMERIC, 15, casasDec);
	private JTextFieldPad txtCodProd = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 10, 0);
	private JTextFieldPad txtRefProd = new JTextFieldPad(JTextFieldPad.TP_STRING, 13, 0);
	private JTextFieldPad txtCLoteProd = new JTextFieldPad(JTextFieldPad.TP_STRING, 1, 0);
	private JTextFieldPad txtPrecoItCompra = new JTextFieldPad(JTextFieldPad.TP_NUMERIC, 15, casasDecFin);
	private JTextFieldPad txtPercDescItCompra = new JTextFieldPad(JTextFieldPad.TP_NUMERIC, 6, 2);
	private JTextFieldPad txtVlrDescItCompra = new JTextFieldPad(JTextFieldPad.TP_NUMERIC, 15, casasDecFin);
	private JTextFieldPad txtPercComItCompra = new JTextFieldPad(JTextFieldPad.TP_NUMERIC, 6, 2);
	private JTextFieldPad txtCodNat = new JTextFieldPad(JTextFieldPad.TP_STRING, 5, 0);
	private JTextFieldPad txtBaseICMSItCompra = new JTextFieldPad(JTextFieldPad.TP_NUMERIC, 15, casasDecFin);
	private JTextFieldPad txtPercICMSItCompra = new JTextFieldPad(JTextFieldPad.TP_NUMERIC, 15, casasDecFin);
	private JTextFieldPad txtVlrICMSItCompra = new JTextFieldPad(JTextFieldPad.TP_NUMERIC, 15, casasDecFin);
	private JTextFieldPad txtVlrLiqItCompra = new JTextFieldPad(JTextFieldPad.TP_NUMERIC, 15, casasDecFin);
	private JTextFieldPad txtCodLote = new JTextFieldPad(JTextFieldPad.TP_STRING, 13, 0);
	private JTextFieldPad txtCodFisc = new JTextFieldPad(JTextFieldPad.TP_STRING, 13, 0);
	private JTextFieldPad txtTipoFisc = new JTextFieldPad(JTextFieldPad.TP_STRING, 2, 0);
	private JTextFieldPad txtRedFisc = new JTextFieldPad(JTextFieldPad.TP_NUMERIC, 6, 2);
	private JTextFieldPad txtVlrIPICompra = new JTextFieldPad(JTextFieldPad.TP_NUMERIC, 15, casasDecFin);
	private JTextFieldPad txtVlrDescCompra = new JTextFieldPad(JTextFieldPad.TP_NUMERIC, 15, casasDecFin);
	private JTextFieldPad txtVlrLiqCompra = new JTextFieldPad(JTextFieldPad.TP_NUMERIC, 15, casasDecFin);
	private JTextFieldPad txtVlrProdItCompra = new JTextFieldPad(JTextFieldPad.TP_NUMERIC, 15, casasDecFin);
	private JTextFieldPad txtBaseIPIItCompra = new JTextFieldPad(JTextFieldPad.TP_NUMERIC, 15, casasDecFin);
	private JTextFieldPad txtAliqIPIItCompra = new JTextFieldPad(JTextFieldPad.TP_NUMERIC, 6, 2);
	private JTextFieldPad txtVlrIPIItCompra = new JTextFieldPad(JTextFieldPad.TP_NUMERIC, 15, casasDecFin);
	private JTextFieldPad txtCustoItCompra = new JTextFieldPad(JTextFieldPad.TP_NUMERIC, 15, casasDecFin);
	private JTextFieldPad txtAliqIPIFisc = new JTextFieldPad(JTextFieldPad.TP_NUMERIC, 6, 2);
	private JTextFieldPad txtVlrBrutCompra = new JTextFieldPad(JTextFieldPad.TP_NUMERIC, 15, casasDecFin);
	private JTextFieldPad txtSerieCompra = new JTextFieldPad(JTextFieldPad.TP_STRING, 4, 0);
	private JTextFieldPad txtDocSerie = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0); // Tem que ter esse campo para não gerar N.de documento automático
	private JTextFieldPad txtDocCompra = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);
	private JTextFieldPad txtStatusCompra = new JTextFieldPad(JTextFieldPad.TP_STRING, 2, 0);
	private JTextFieldFK txtDescTipoMov = new JTextFieldFK(JTextFieldPad.TP_STRING, 40, 0);	
	private JTextFieldPad txtTipoMov = new JTextFieldPad(JTextFieldPad.TP_STRING, 2, 0);
	private JTextFieldFK txtDescFor = new JTextFieldFK(JTextFieldPad.TP_STRING,50, 0);
	private JTextFieldFK txtEstFor = new JTextFieldFK(JTextFieldPad.TP_STRING,2, 0);
	private JTextFieldFK txtDescPlanoPag = new JTextFieldFK(JTextFieldPad.TP_STRING, 40, 0);
	private JTextFieldFK txtDescProd = new JTextFieldFK(JTextFieldPad.TP_STRING, 50, 0);
	private JTextFieldFK txtDescNat = new JTextFieldFK(JTextFieldPad.TP_STRING,40, 0);
	private JTextFieldFK txtDescLote = new JTextFieldFK(JTextFieldPad.TP_DATE,10, 0);
	private JTextFieldPad txtCodBarProd = new JTextFieldPad( JTextFieldPad.TP_STRING, 13, 0);
	private JTextFieldPad txtCodFabProd = new JTextFieldPad( JTextFieldPad.TP_STRING, 13, 0);
	private JTextFieldFK txtDescFisc = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0);	
	private JTextFieldPad txtCodAlmoxItCompra = new JTextFieldPad( JTextFieldPad.TP_INTEGER,5, 0);	
	private JLabelPad lbStatus = new JLabelPad();
	private JCheckBoxPad cbSeqNfTipoMov = new JCheckBoxPad("Aloc.NF","S","N");
	private ListaCampos lcTipoMov = new ListaCampos(this, "TM");
	private ListaCampos lcSerie = new ListaCampos(this, "SE");
	private ListaCampos lcFor = new ListaCampos(this, "FR");
	private ListaCampos lcPlanoPag = new ListaCampos(this, "PG");
	private ListaCampos lcProd = new ListaCampos(this, "PD");
	private ListaCampos lcProd2 = new ListaCampos(this, "PD");
	private ListaCampos lcNat = new ListaCampos(this, "NT");
	private ListaCampos lcLote = new ListaCampos(this, "LE");
	private ListaCampos lcFisc = new ListaCampos(this);
	private ListaCampos lcCompra2 = new ListaCampos(this);
	private ListaCampos lcAlmox = new ListaCampos(this,"AX");
	String sOrdNota = "";

	public FCompra() {
		setTitulo("Compra");
		setAtribos(15, 10, 760, 430);

		pnMaster.remove(2);
		pnGImp.removeAll();
		pnGImp.setLayout(new GridLayout(1, 3));
		pnGImp.setPreferredSize(new Dimension(220, 26));
		pnGImp.add(btPrevimp);
		pnGImp.add(btImp);
		pnGImp.add(btFechaCompra);

		pnTot.setPreferredSize(new Dimension(140, 200));
		pnTot.add(pinTot);
		pnCenter.add(pnTot, BorderLayout.EAST);
		pnCenter.add(spTab, BorderLayout.CENTER);

		JPanelPad pnLab = new JPanelPad(JPanelPad.TP_JPANEL, new GridLayout(1,1));
		pnLab.add(new JLabelPad(" Totais:"));

		pnMaster.add(pnCenter, BorderLayout.CENTER);

		lcTipoMov.add(new GuardaCampo(txtCodTipoMov, "CodTipoMov","Cód.tp.mov.", ListaCampos.DB_PK, false));
		lcTipoMov.add(new GuardaCampo(txtDescTipoMov, "DescTipoMov","Descrição do tipo de movimento", ListaCampos.DB_SI, false));
		lcTipoMov.add(new GuardaCampo(txtTipoMov, "TipoMov", "Tipo mov.",ListaCampos.DB_SI, false));
		lcTipoMov.add(new GuardaCampo(cbSeqNfTipoMov, "SeqNfTipomov", "Aloc.NF", ListaCampos.DB_SI, true));
		lcTipoMov.setWhereAdic("((ESTIPOMOV = 'E') AND"
						+ " ( TUSUTIPOMOV='S' OR	EXISTS (SELECT * FROM EQTIPOMOVUSU TU "
						+ "WHERE TU.CODEMP=EQTIPOMOV.CODEMP AND TU.CODFILIAL=EQTIPOMOV.CODFILIAL AND "
						+ "TU.CODTIPOMOV=EQTIPOMOV.CODTIPOMOV AND TU.CODEMPUS="
						+ Aplicativo.iCodEmp + " AND " + "TU.CODFILIALUS="
						+ ListaCampos.getMasterFilial("SGUSUARIO")
						+ " AND TU.IDUSU='" + Aplicativo.strUsuario + "') ) "
						+ ")");
		lcTipoMov.montaSql(false, "TIPOMOV", "EQ");
		lcTipoMov.setQueryCommit(false);
		lcTipoMov.setReadOnly(true);
		txtCodTipoMov.setTabelaExterna(lcTipoMov);

		lcSerie.add(new GuardaCampo(txtSerieCompra, "Serie", "Série",ListaCampos.DB_PK, false));
		lcSerie.add(new GuardaCampo(txtDocSerie, "DocSerie", "Doc",ListaCampos.DB_SI, false));
		lcSerie.montaSql(false, "SERIE", "LF");
		lcSerie.setQueryCommit(false);
		lcSerie.setReadOnly(true);
		txtSerieCompra.setTabelaExterna(lcSerie);

		lcFor.add(new GuardaCampo(txtCodFor, "CodFor", "Cód.for.",ListaCampos.DB_PK, false));
		lcFor.add(new GuardaCampo(txtDescFor, "RazFor","Razão social do fornecedor", ListaCampos.DB_SI, false));
		lcFor.add(new GuardaCampo(txtEstFor, "UFFor", "UF", ListaCampos.DB_SI,false));
		lcFor.montaSql(false, "FORNECED", "CP");
		lcFor.setQueryCommit(false);
		lcFor.setReadOnly(true);
		txtCodFor.setTabelaExterna(lcFor);

		lcPlanoPag.add(new GuardaCampo(txtCodPlanoPag, "CodPlanoPag","Cód.p.pag.", ListaCampos.DB_PK, false));
		lcPlanoPag.add(new GuardaCampo(txtDescPlanoPag, "DescPlanoPag","Descrição do plano de pagamento", ListaCampos.DB_SI, false));
		lcPlanoPag.montaSql(false, "PLANOPAG", "FN");
		lcPlanoPag.setQueryCommit(false);
		lcPlanoPag.setReadOnly(true);
		txtCodPlanoPag.setTabelaExterna(lcPlanoPag);

		lcFisc.add(new GuardaCampo(txtCodFisc, "CodFisc", "Código",ListaCampos.DB_PK, false));
		lcFisc.add(new GuardaCampo(txtDescFisc, "DescFisc", "Descrição",ListaCampos.DB_SI, false));
		lcFisc.add(new GuardaCampo(txtTipoFisc, "TipoFisc", "Tipo",ListaCampos.DB_SI, false));
		lcFisc.add(new GuardaCampo(txtRedFisc, "RedFisc", "Redução",ListaCampos.DB_SI, false));
		lcFisc.add(new GuardaCampo(txtAliqIPIFisc, "AliqIPIFisc", "% IPI",ListaCampos.DB_SI, false));
		lcFisc.montaSql(false, "CLFISCAL", "LF");
		lcFisc.setQueryCommit(false);
		lcFisc.setReadOnly(true);
		txtCodFisc.setTabelaExterna(lcFisc);
		txtDescFisc.setListaCampos(lcFisc);

		lcProd.add(new GuardaCampo(txtCodProd, "codprod", "Cód.prod.",ListaCampos.DB_PK, false));
		lcProd.add(new GuardaCampo(txtDescProd, "DescProd","Descrição do produto", ListaCampos.DB_SI, false));
		lcProd.add(new GuardaCampo(txtRefProd, "RefProd", "Referência",ListaCampos.DB_SI, false));
		lcProd.add(new GuardaCampo(txtCLoteProd, "CLoteProd", "C/Lote",ListaCampos.DB_SI, false));
		lcProd.add(new GuardaCampo(txtCodFisc, "CodFisc", "Cod.Fiscal",ListaCampos.DB_FK, false));
		lcProd.add(new GuardaCampo(txtCodBarProd, "CodBarProd", "Cod.Barra",ListaCampos.DB_SI, false));
		lcProd.add(new GuardaCampo(txtCodFabProd, "CodFabProd","Cod.Fabricante", ListaCampos.DB_SI, false));

		lcProd.setWhereAdic("ATIVOPROD='S'");
		lcProd.montaSql(false, "PRODUTO", "EQ");
		lcProd.setQueryCommit(false);
		lcProd.setReadOnly(true);
		txtCodProd.setTabelaExterna(lcProd);

		lcProd2.add(new GuardaCampo(txtRefProd, "RefProd", "Referência",ListaCampos.DB_PK, false));
		lcProd2.add(new GuardaCampo(txtDescProd, "DescProd", "Descrição",ListaCampos.DB_SI, false));
		lcProd2.add(new GuardaCampo(txtCodProd, "codprod", "Cód.rod.",ListaCampos.DB_SI, false));
		lcProd2.add(new GuardaCampo(txtCLoteProd, "CLoteProd", "C/Lote",ListaCampos.DB_SI, false));
		lcProd2.add(new GuardaCampo(txtCodFisc, "CodFisc", "CodFisc",ListaCampos.DB_FK, false));
		lcProd2.add(new GuardaCampo(txtCodBarProd, "CodBarProd", "Cod.Barra",ListaCampos.DB_SI, false));
		lcProd2.add(new GuardaCampo(txtCodFabProd, "CodFabProd","Cod.Fabricante", ListaCampos.DB_SI, false));

		txtRefProd.setNomeCampo("RefProd");
		txtRefProd.setListaCampos(lcDet);
		lcProd2.setWhereAdic("ATIVOPROD='S'");
		lcProd2.montaSql(false, "PRODUTO", "EQ");
		lcProd2.setQueryCommit(false);
		lcProd2.setReadOnly(true);
		txtRefProd.setTabelaExterna(lcProd2);

		lcLote.add(new GuardaCampo(txtCodLote, "CodLote", "Cód.lote",ListaCampos.DB_PK, false));
		lcLote.add(new GuardaCampo(txtCodProd, "CodProd", "Cód.prod.",ListaCampos.DB_PK, false));
		lcLote.add(new GuardaCampo(txtDescLote, "VenctoLote", "Vencimento",ListaCampos.DB_SI, false));
		lcLote.setDinWhereAdic("CODPROD=#N",txtCodProd);
		lcLote.setAutoLimpaPK(false);
		lcLote.montaSql(false, "LOTE", "EQ");
		lcLote.setQueryCommit(false);
		lcLote.setReadOnly(true);
		txtCodLote.setTabelaExterna(lcLote);
		txtDescLote.setListaCampos(lcLote);
		txtDescLote.setNomeCampo("VenctoLote");
		txtDescLote.setLabel("Vencimento");

		//FK de Almoxarifado

		lcAlmox.add(new GuardaCampo(txtCodAlmoxItCompra, "codalmox", "Cod.Almox.",ListaCampos.DB_PK, false));
		lcAlmox.montaSql(false, "ALMOX", "EQ");
		lcAlmox.setQueryCommit(false);
		lcAlmox.setReadOnly(true);
		txtCodAlmoxItCompra.setTabelaExterna(lcAlmox);
		
		lcNat.add(new GuardaCampo(txtCodNat, "CodNat", "CFOP",ListaCampos.DB_PK, false));
		lcNat.add(new GuardaCampo(txtDescNat, "DescNat", "Descrição da CFOP",ListaCampos.DB_SI, false));
		lcNat.montaSql(false, "NATOPER", "LF");
		lcNat.setQueryCommit(false);
		lcNat.setReadOnly(true);
		txtCodNat.setTabelaExterna(lcNat);
		txtDescNat.setListaCampos(lcNat);

		lcCompra2.add(new GuardaCampo(txtCodCompra, "CodCompra", "Código",ListaCampos.DB_PK, false));
		lcCompra2.add(new GuardaCampo(txtVlrIPICompra, "VlrIPICompra", "IPI",ListaCampos.DB_SI, false));
		lcCompra2.add(new GuardaCampo(txtVlrDescCompra, "VlrDescItCompra","Desconto", ListaCampos.DB_SI, false));
		lcCompra2.add(new GuardaCampo(txtVlrLiqCompra, "VlrLiqCompra", "Geral",ListaCampos.DB_SI, false));
		lcCompra2.add(new GuardaCampo(txtVlrBrutCompra, "VlrProdCompra","Geral", ListaCampos.DB_SI, false));
		lcCompra2.montaSql(false, "COMPRA", "CP");
		lcCompra2.setQueryCommit(false);
		lcCompra2.setReadOnly(true);

		btFechaCompra.setToolTipText("Fechar a Compra (F4)");

		txtVlrIPICompra.setAtivo(false);
		txtVlrDescCompra.setAtivo(false);
		txtVlrLiqCompra.setAtivo(false);

		pinCab = new JPanelPad(740, 130);
		setListaCampos(lcCampos);
		setAltCab(130);
		setPainel(pinCab, pnCliCab);
		adicCampo(txtCodCompra, 7, 20, 100, 20, "CodCompra", "Nº Compra",ListaCampos.DB_PK, true);
		adicCampo(txtCodTipoMov, 110, 20, 77, 20, "CodTipoMov", "Cód.tp.mov.",ListaCampos.DB_FK, txtDescTipoMov, true);
		adicDescFK(txtDescTipoMov, 190, 20, 207, 20, "DescTipoMov","Descrição do tipo de movimento");
		adicCampo(txtSerieCompra, 400, 20, 77, 20, "Serie", "Série",ListaCampos.DB_FK, true);
		adicCampo(txtDocCompra, 480, 20, 77, 20, "DocCompra", "Doc",ListaCampos.DB_SI, true);
		adicCampo(txtDtEmitCompra, 560, 20, 85, 20, "DtEmitCompra","Dt.emissão", ListaCampos.DB_SI, true);
		adicCampo(txtDtEntCompra, 648, 20, 85, 20, "DtEntCompra", "Dt.entrada",ListaCampos.DB_SI, true);
		adicCampo(txtCodFor, 7, 60, 80, 20, "CodFor", "Cód.for.",ListaCampos.DB_FK, txtDescFor, true);
		adicDescFK(txtDescFor, 90, 60, 210, 20, "RazFor","Razão social do fornecedor");
		adicCampo(txtCodPlanoPag, 303, 60, 80, 20, "CodPlanoPag", "Cód.p.pag.",ListaCampos.DB_FK, txtDescPlanoPag, true);
		adicDescFK(txtDescPlanoPag, 386, 60, 245, 20, "DescPlanoPag","Descrição do plano de pagto.");
		adicCampoInvisivel(txtStatusCompra, "StatusCompra", "Status",ListaCampos.DB_SI, false);
		adic(lbStatus, 638, 60, 95, 20);
		//    lcCampos.setWhereAdic("FLAG IN "+
		//    projetos.freedom.Freedom.carregaFiltro(con,org.freedom.telas.Aplicativo.strCodEmp));
		setListaCampos(true, "COMPRA", "CP");
		lcCampos.setQueryInsert(false);

		btFechaCompra.addActionListener(this);

		txtPercDescItCompra.addFocusListener(this);
		txtPercComItCompra.addFocusListener(this);
		txtVlrDescItCompra.addFocusListener(this);
		txtQtdItCompra.addFocusListener(this);
		txtCodNat.addFocusListener(this);
		txtPrecoItCompra.addFocusListener(this);
		txtPercICMSItCompra.addFocusListener(this);
		txtVlrIPIItCompra.addFocusListener(this);
		lcCampos.addPostListener(this);
		lcCampos.addCarregaListener(this);
		lcFor.addCarregaListener(this);
		lcSerie.addCarregaListener(this);
		lcProd.addCarregaListener(this);
		lcFisc.addCarregaListener(this);
		lcNat.addCarregaListener(this);
		lcLote.addCarregaListener(this);
		lcDet.addPostListener(this);
		lcDet.addCarregaListener(this);
		lcCampos.addInsertListener(this);
		lcTipoMov.addCarregaListener(this);

		btImp.addActionListener(this);
		btPrevimp.addActionListener(this);
		
		lbStatus.setForeground(Color.WHITE);
		lbStatus.setFont( new Font("Arial", Font.BOLD, 13));
		lbStatus.setOpaque(true);
		lbStatus.setVisible(false);
		
	    setImprimir(true);
	}

	private void montaDetalhe() {
		setAltDet(100);
		pinDet = new JPanelPad(740, 100);
		setPainel(pinDet, pnDet);
		setListaCampos(lcDet);
		setNavegador(navRod);
		adicCampo(txtCodItCompra, 7, 20, 30, 20, "CodItCompra", "N.item",ListaCampos.DB_PK, true);
		if (comRef()) {
			txtRefProd.setBuscaAdic(new DLBuscaProd(con, "REFPROD",lcProd2.getWhereAdic()));
			adicCampoInvisivel(txtCodProd, "CodProd", "Cód.prod.",ListaCampos.DB_FK, txtDescProd, false);
			adicCampoInvisivel(txtRefProd, "RefProd", "Referência",ListaCampos.DB_FK, false);

			adic(new JLabelPad("Referência"), 40, 0, 67, 20);
			adic(txtRefProd, 40, 20, 67, 20);
			txtRefProd.setFK(true);
		} else {
			txtCodProd.setBuscaAdic(new DLBuscaProd(con, "CODPROD",lcProd.getWhereAdic()));
			adicCampo(txtCodProd, 40, 20, 67, 20, "CodProd", "Cód.prod.",ListaCampos.DB_FK, txtDescProd, false);
		}
		txtCustoItCompra.setSoLeitura(true);
		adicDescFK(txtDescProd, 110, 20, 197, 20, "DescProd","Descrição do produto");
		adicCampo(txtCodLote, 310, 20, 67, 20, "CodLote", "Lote",ListaCampos.DB_FK, txtDescLote, false);
		adicCampo(txtQtdItCompra, 380, 20, 67, 20, "qtditcompra", "Qtd.",ListaCampos.DB_SI, true);

		adicCampoInvisivel(txtCodAlmoxItCompra, "codalmox", "Cod.Almox",ListaCampos.DB_FK, false);
		
		txtQtdItCompra.setBuscaAdic(new DLBuscaEstoq(lcDet, lcAlmox,lcProd,con,"qtditcompra"));		
				
		adicCampo(txtPrecoItCompra, 450, 20, 67, 20, "PrecoItCompra", "Preço",ListaCampos.DB_SI, true);
		adicCampo(txtPercDescItCompra, 520, 20, 57, 20, "PercDescItCompra","% Desc.", ListaCampos.DB_SI, false);
		adicCampo(txtVlrDescItCompra, 580, 20, 67, 20, "VlrDescItCompra","V. Desc.", ListaCampos.DB_SI, false);
		adicCampo(txtCustoItCompra, 650, 20, 85, 20, "CustoItCompra","Custo Estoq.", ListaCampos.DB_SI, false);
		adicCampo(txtCodNat, 7, 60, 67, 20, "CodNat", "CFOP",ListaCampos.DB_FK, txtDescNat, true);
		adicDescFK(txtDescNat, 80, 60, 197, 20, "DescNat", "Descrição da CFOP");
		adicCampo(txtBaseICMSItCompra, 280, 60, 67, 20, "VlrBaseICMSItCompra","B. ICMS", ListaCampos.DB_SI, false);
		adicCampo(txtPercICMSItCompra, 350, 60, 57, 20, "PercICMSItCompra","% ICMS", ListaCampos.DB_SI, true);
		adicCampo(txtVlrICMSItCompra, 410, 60, 67, 20, "VlrICMSItCompra","V. ICMS", ListaCampos.DB_SI, false);
		adicCampoInvisivel(txtBaseIPIItCompra, "VlrBaseIPIItCompra", "B. IPI",ListaCampos.DB_SI, false);
		adicCampoInvisivel(txtAliqIPIItCompra, "PercIPIItCompra", "% IPI",ListaCampos.DB_SI, false);
		adicCampo(txtVlrIPIItCompra, 480, 60, 67, 20, "VlrIPIItCompra","V. IPI", ListaCampos.DB_SI, false);
		adicCampoInvisivel(txtVlrProdItCompra, "VlrProdItCompra", "V. Bruto",ListaCampos.DB_SI, false);
		adicCampo(txtVlrLiqItCompra, 550, 60, 100, 20, "VlrLiqItCompra","Valor Item", ListaCampos.DB_SI, false);
		pinTot.adic(new JLabelPad("Tot. IPI"), 7, 0, 120, 20);
		pinTot.adic(txtVlrIPICompra, 7, 20, 120, 20);
		pinTot.adic(new JLabelPad("Tot. Desc."), 7, 40, 120, 20);
		pinTot.adic(txtVlrDescCompra, 7, 60, 120, 20);
		pinTot.adic(new JLabelPad("Total Geral"), 7, 80, 120, 20);
		pinTot.adic(txtVlrLiqCompra, 7, 100, 120, 20);
		txtCodNat.setStrMascara("#.###");
		/*
		 * txtRefProd.addKeyListener(new KeyAdapter() { public void
		 * keyPressed(KeyEvent kevt) { lcDet.edit(); } });
		 */
		setListaCampos(true, "ITCOMPRA", "CP");
		lcDet.setQueryInsert(false);
		
		montaTab();

		tab.setTamColuna(30, 0);
		tab.setTamColuna(70, 1);
		tab.setTamColuna(230, 2);
		tab.setTamColuna(70, 3);
		tab.setTamColuna(80, 4);
		tab.setTamColuna(70, 6);
		tab.setTamColuna(70, 7);
		tab.setTamColuna(60, 8);
		tab.setTamColuna(70, 9);
		tab.setTamColuna(60, 10);
		tab.setTamColuna(70, 11);
		tab.setTamColuna(200, 12);
		tab.setTamColuna(70, 13);
		tab.setTamColuna(60, 14);
		tab.setTamColuna(70, 15);
		tab.setTamColuna(70, 16);
		tab.setTamColuna(60, 17);
		tab.setTamColuna(70, 18);
		tab.setTamColuna(80, 19);
		tab.setTamColuna(90, 20);
	}

	private void adicIPI() {
		double deVlrProd = Funcoes.arredDouble(txtVlrProdItCompra.doubleValue()
				- txtVlrDescItCompra.doubleValue()
				+ txtVlrIPIItCompra.doubleValue(), casasDecFin);
		txtVlrLiqItCompra.setVlrBigDecimal(new BigDecimal(deVlrProd));
	}

	private void calcImpostos(boolean bCalcBase) {
		double deRed = txtRedFisc.doubleValue();
		double deVlrProd = Funcoes.arredDouble(txtVlrProdItCompra.doubleValue()
				- txtVlrDescItCompra.doubleValue(), casasDecFin);
		double deBaseICMS = Funcoes.arredDouble(txtBaseICMSItCompra
				.doubleValue(), casasDecFin);
		double deBaseIPI = txtBaseIPIItCompra.doubleValue();
		double deICMS = 0;
		//    System.out.println("Red: "+bRed);
		//    System.out.println("VlrProd: "+bVlrProd);
		if (deVlrProd > 0) {
			if (bCalcBase) {
				deBaseICMS = Funcoes.arredDouble(deVlrProd
						- (deVlrProd * deRed / 100), casasDecFin);
				deBaseIPI = deVlrProd;
			}
			deICMS = Funcoes.arredDouble(deBaseICMS
					* txtPercICMSItCompra.doubleValue() / 100, casasDecFin);
		}
		txtVlrICMSItCompra.setVlrBigDecimal(new BigDecimal(deICMS));
		if (bCalcBase) {
			txtBaseICMSItCompra.setVlrBigDecimal(new BigDecimal(deBaseICMS));
			txtBaseIPIItCompra.setVlrBigDecimal(new BigDecimal(deBaseIPI));
		}
		txtVlrLiqItCompra.setVlrBigDecimal(new BigDecimal(deVlrProd));
		txtAliqIPIItCompra.setVlrBigDecimal(txtAliqIPIFisc.getVlrBigDecimal());
	}

	private void calcVlrProd() {
		double dePreco = txtPrecoItCompra.doubleValue();
		double deQtd = txtQtdItCompra.doubleValue();
		txtVlrProdItCompra.setVlrBigDecimal(new BigDecimal(Funcoes.arredDouble(
				dePreco * deQtd, casasDecFin)));
		//    System.out.println("VlrProdTot:
		// "+txtVlrProdItCompra.getVlrBigDecimal());
	}

	private void testaCodCompra() { //Traz o verdadeiro número do codCompra
		// através do generator do banco
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement("SELECT * FROM SPGERANUM(?,?,?)");
			ps.setInt(1, Aplicativo.iCodEmp);
			ps.setInt(2, Aplicativo.iCodFilial);
			ps.setString(3, "CP");
			rs = ps.executeQuery();
			rs.next();
			txtCodCompra.setVlrString(rs.getString(1));
			//      rs.close();
			//      ps.close();
			if (!con.getAutoCommit())
				con.commit();
		} catch (SQLException err) {
			Funcoes.mensagemErro(this, "Erro ao confirmar código da Compra!\n"
					+ err.getMessage(),true,con,err);
		}
	}

	/**
	 * Busca da Natureza de Operação . Busca a natureza de operação através da
	 * tabela de regras fiscais.
	 */
	private void buscaNat() {
		String sSQL = "SELECT CODNAT FROM LFBUSCANATSP (?,?,?,?,?,?,?,?,?,?,?,?)";
		try {
			PreparedStatement ps = con.prepareStatement(sSQL);
			ps.setInt(1, Aplicativo.iCodFilial);
			ps.setInt(2, Aplicativo.iCodEmp);
			ps.setInt(3, lcProd.getCodFilial());
			ps.setInt(4, txtCodProd.getVlrInteger().intValue());
			ps.setNull(5, Types.INTEGER);
			ps.setNull(6, Types.INTEGER);
			ps.setNull(7, Types.INTEGER);
			ps.setInt(8, Aplicativo.iCodEmp);
			ps.setInt(9, lcFor.getCodFilial());
			ps.setInt(10, txtCodFor.getVlrInteger().intValue());
			ps.setInt(11, lcTipoMov.getCodFilial());
			ps.setInt(12, txtCodTipoMov.getVlrInteger().intValue());
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				txtCodNat.setVlrString(rs.getString("CODNAT"));
			}
			rs.close();
			ps.close();
		} catch (SQLException err) {
			Funcoes.mensagemErro(this, "Erro ao buscar natureza da operação!\n"
					+ err.getMessage(),true,con,err);
		}
	}

	public boolean testaCodLote() {
		boolean bRetorno = false;
		boolean bValido = false;
		String sSQL = "SELECT COUNT(*) FROM EQLOTE WHERE CODLOTE=? AND CODPROD=?"
				+ " AND CODEMP=? AND CODFILIAL=?";
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement(sSQL);
			ps.setString(1, txtCodLote.getText().trim());
			ps.setInt(2, txtCodProd.getVlrInteger().intValue());
			ps.setInt(3, Aplicativo.iCodEmp);
			ps.setInt(4, lcLote.getCodFilial());
			rs = ps.executeQuery();
			if (rs.next()) {
				if (rs.getInt(1) > 0) {
					bValido = true;
				}
			}
			rs.close();
			ps.close();
			if (!con.getAutoCommit())
				con.commit();
		} catch (SQLException err) {
			Funcoes.mensagemErro(this, "Erro ao consultar a tabela EQLOTE!\n"
					+ err.getMessage(),true,con,err);
		}
		if (!bValido) {
			DLLote dl = new DLLote(this, txtCodLote.getText(), txtCodProd
					.getText(), txtDescProd.getText(), con);
			dl.setVisible(true);
			if (dl.OK) {
				bRetorno = true;
				txtCodLote.setVlrString(dl.getValor());
				lcLote.carregaDados();
			}
			dl.dispose();
		} else {
			bRetorno = true;
		}
		return bRetorno;
	}

	public void focusGained(FocusEvent fevt) {
	}

	public void focusLost(FocusEvent fevt) {
		if (fevt.getSource() == txtPercDescItCompra) {
			if (txtPercDescItCompra.getText().trim().length() < 1) {
				txtVlrDescItCompra.setAtivo(true);
			} else {
				txtVlrDescItCompra.setVlrBigDecimal(new BigDecimal(Funcoes
						.arredDouble(txtVlrProdItCompra.doubleValue()
								* txtPercDescItCompra.doubleValue() / 100,
								casasDecFin)));
				calcVlrProd();
				calcImpostos(true);
				txtVlrDescItCompra.setAtivo(false);
			}
		} else if (fevt.getSource() == txtVlrIPIItCompra) {
			adicIPI();
		} else if (fevt.getSource() == txtVlrDescItCompra) {
			if (txtVlrDescItCompra.getText().trim().length() < 1) {
				txtPercDescItCompra.setAtivo(true);
			} else if (txtVlrDescItCompra.getAtivo()) {
				txtPercDescItCompra.setAtivo(false);
			}
		} else if ((fevt.getSource() == txtQtdItCompra)
				|| (fevt.getSource() == txtPrecoItCompra)
				|| (fevt.getSource() == txtCodNat)) {
			calcVlrProd();
			calcImpostos(true);
		} else if (fevt.getSource() == txtPercICMSItCompra) {
			calcVlrProd();
			calcImpostos(false);
		}
	}

	/**
	 * Busca de icms. Busca a percentagem de ICMS conforme a regra fiscal.
	 */
	private void buscaICMS() {
		String sSQL = "SELECT PERCICMS FROM LFBUSCAICMSSP(?,?,?,?)";
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement(sSQL);
			ps.setString(1, txtCodNat.getVlrString());
			ps.setString(2, txtEstFor.getVlrString());
			ps.setInt(3, Aplicativo.iCodEmp);
			ps.setInt(4, Aplicativo.iCodFilialMz);
			rs = ps.executeQuery();
			if (rs.next()) {
				txtPercICMSItCompra.setVlrBigDecimal(new BigDecimal(rs
						.getString(1)));
			}
			calcImpostos(true);
		} catch (SQLException err) {
			Funcoes.mensagemErro(this, "Erro ao buscar percentual de ICMS!\n"
					+ err.getMessage(),true,con,err);
		}
	}

	public void beforeCarrega(CarregaEvent cevt) {
		if (lcDet.getStatus() != ListaCampos.LCS_INSERT) {
			if (cevt.getListaCampos() == lcProd) {
				lcProd.cancLerCampo(4, true); //Código da Classificação Fiscal
			}
		} else {
			if (cevt.getListaCampos() == lcProd) {
				lcProd.cancLerCampo(4, false); //Código da Classificação Fiscal
			}
		}
		if (cevt.getListaCampos() == lcLote) {
			if (txtCodLote.getText().trim().length() == 0) {
				cevt.cancela(); //Cancela o carregaDados do lcLote para não
								// zerar o
				// codprod.
			}
		}
	}

	public void afterPost(PostEvent pevt) {
		String s = txtCodCompra.getText();
		lcCompra2.carregaDados(); //Carrega os Totais
		txtCodCompra.setVlrString(s);
	}

	public void afterCarrega(CarregaEvent cevt) {
		if (cevt.getListaCampos() == lcProd) {
			if (txtCLoteProd.getText().trim().equals("N")) {
				txtCodLote.setAtivo(false);//Desativa o Cógigo do lote por o
										   // produto
				// não possuir lote
			} else if (txtCLoteProd.getText().trim().equals("S")) {
				txtCodLote.setAtivo(true);//Ativa o Cógigo do Lote pois o
										  // produto tem
				// lote
			}
		} else if ((cevt.getListaCampos() == lcFisc)
				&& (lcDet.getStatus() == ListaCampos.LCS_INSERT)) {
			buscaNat();
		} else if (cevt.getListaCampos() == lcDet) {
			String s = txtCodCompra.getText();
			lcCompra2.carregaDados(); //Carrega os Totais
			txtCodCompra.setVlrString(s);
		} else if (cevt.getListaCampos() == lcCampos) {
			String s = txtCodCompra.getText();
			lcCompra2.carregaDados(); //Carrega os Totais
			txtCodCompra.setVlrString(s);
		} else if (cevt.getListaCampos() == lcSerie) {
			if ( (lcCampos.getStatus() == ListaCampos.LCS_INSERT) && (cbSeqNfTipoMov.getVlrString().equals("S")) )
				txtDocCompra.setVlrInteger(new Integer(txtDocSerie
						.getVlrInteger().intValue() + 1));
		} else if (cevt.getListaCampos() == lcNat) {
			if ((cevt.ok) & (lcDet.getStatus() == ListaCampos.LCS_INSERT)) {
				buscaICMS();
			}
		} else if (cevt.getListaCampos() == lcTipoMov ) {
			if (cbSeqNfTipoMov.getVlrString().equals("S"))
				txtDocCompra.setAtivo(false);
			else
				txtDocCompra.setAtivo(true);
		}
		/*
		if (txtStatusCompra.getVlrString().trim().length()>0 && 
				txtStatusCompra.getVlrString().substring(0,1).equals("C")){
			lbStatus.setText("  CANCELADA");
			lbStatus.setBackground(Color.RED);
			lbStatus.setVisible(true);
		}
		else 
			*/
			if (txtStatusCompra.getVlrString().trim().length()>0 && 
				(txtStatusCompra.getVlrString().trim().equals("C2")
						|| txtStatusCompra.getVlrString().trim().equals("C3"))){
			lbStatus.setText("NOTA RECEBIDA");
			lbStatus.setBackground(Color.GREEN);
			lbStatus.setVisible(true);
		}
		else if (verificaBloq()){
			lbStatus.setText("  BLOQUEADA");
			lbStatus.setBackground(Color.BLUE);
			lbStatus.setVisible(true);
		}
		else {
			lbStatus.setText("");
			lbStatus.setVisible( false );
		}
		
	}

	public void keyPressed(KeyEvent kevt) {
		if (kevt.getKeyCode() == KeyEvent.VK_ENTER) {
			if (kevt.getSource() == txtCodPlanoPag) {//Talvez este possa ser o
													 // ultimo
				// campo do itvenda.
				if (lcCampos.getStatus() == ListaCampos.LCS_INSERT) {
					lcCampos.post();
					lcDet.insert(true);
					txtRefProd.requestFocus();
				} else if (lcDet.getStatus() == ListaCampos.LCS_EDIT) {
					lcCampos.post();
					txtCodItCompra.requestFocus();
				}
			} else if (kevt.getSource() == txtVlrLiqItCompra) {//Talvez este
															   // possa ser
				// o ultimo campo do
				// itvenda.
				if (lcDet.getStatus() == ListaCampos.LCS_INSERT) {
					lcDet.post();
					lcDet.limpaCampos(true);
					lcDet.setState(ListaCampos.LCS_NONE);
					if (comRef())
						txtRefProd.requestFocus();
					else
						txtCodProd.requestFocus();
				} else if (lcDet.getStatus() == ListaCampos.LCS_EDIT) {
					lcDet.post();
					txtCodItCompra.requestFocus();
				}
			}
		} else if (kevt.getKeyCode() == KeyEvent.VK_F4) {
			btFechaCompra.doClick();
		}
		if (kevt.getSource() == txtRefProd)
			lcDet.edit();

		super.keyPressed(kevt);
	}

	public void actionPerformed(ActionEvent evt) {
		String[] sValores = null;
		if (evt.getSource() == btFechaCompra) {
			DLFechaCompra dl = new DLFechaCompra(con, txtCodCompra .getVlrInteger(), this);
			dl.setVisible(true);
			if (dl.OK) {
				sValores = dl.getValores();
				dl.dispose();
			} else {
				dl.dispose();
			}
			lcCampos.carregaDados();
			if (sValores != null) {
				lcCampos.edit();
				if (sValores[4].equals("S")){
					if (txtTipoMov.getVlrString().equals("VD")
							|| txtTipoMov.getVlrString().equals("VT")
							|| txtTipoMov.getVlrString().equals("TR")
							|| txtTipoMov.getVlrString().equals("CS")
							|| txtTipoMov.getVlrString().equals("CE")
							|| txtTipoMov.getVlrString().equals("PE")
							|| txtTipoMov.getVlrString().equals("DV")
							|| txtTipoMov.getVlrString().equals("BN"))
						emitNota("NF");
					else if (txtTipoMov.getVlrString().equals("SE"))
						emitNota("NS");
					else {
						Funcoes.mensagemErro(this, "Não existe nota para o tipo de movimento: '" + txtTipoMov.getVlrString() + "'");
						return;
					}
				}
				else if (sValores[3].equals("S")) {
					imprimir(true, txtCodCompra.getVlrInteger().intValue());
				}
				
				lcCampos.post();
				
				if(podeBloq()) {
					bloqCompra();
				}
			}
		} else if (evt.getSource() == btPrevimp)
			imprimir(true, txtCodCompra.getVlrInteger().intValue());
		else if (evt.getSource() == btImp)
			imprimir(false, txtCodCompra.getVlrInteger().intValue());
		super.actionPerformed(evt);
	}
	
	private void emitNota(String tipo){		
		Object layNF = null;		
		Vector parans = null;
		NFEntrada nf = null;
		String sTipo = tipo;
		boolean bImpOK = false;
		int iCodCompra = txtCodCompra.getVlrInteger().intValue();
		ImprimeOS imp = new ImprimeOS("", con, sTipo, true);
		imp.verifLinPag(sTipo);
		imp.setTitulo("Nota Fiscal");
		DLRPedido dl = new DLRPedido(sOrdNota, false);
		dl.setVisible(true);
		if (dl.OK == false) {
			dl.dispose();
			return;
		}
		try {
			layNF = Class.forName("org.freedom.layout." + imp.getClassNota()).newInstance();
		} catch (Exception err) {
			Funcoes.mensagemInforma(this,"Não foi possível carregar o leiaute de Nota Fiscal!\n"+ err.getMessage());
		}
		try {
			if (layNF != null) {
				if (layNF instanceof Layout) {
					parans = new Vector();
					parans.addElement(new Integer(Aplicativo.iCodEmp));
					parans.addElement(new Integer(ListaCampos.getMasterFilial("CPCOMPRA")));
					parans.addElement(new Integer(iCodCompra));
					nf = new NFEntrada(casasDec);
					nf.carregaTabelas(con, parans);
					bImpOK = ((Layout) layNF).imprimir(nf, imp);
				}
				else if (layNF instanceof Leiaute) {
					Funcoes.mensagemInforma(this,"O layout de Nota Fiscal\nnão se aplica para nota de entrada ");
				}
			}
		}
		catch (Exception err) {
			Funcoes.mensagemErro(this, "Erro ao emitir nota de Compra\n!"
					+ err.getMessage(),true,con,err);
		}
		dl.dispose();
		if (bImpOK)
			imp.preview(this);
		imp.fechaPreview();		
	}

	private void imprimir(boolean bVisualizar, int iCodCompra) {
		ImprimeOS imp = new ImprimeOS("", con);
		DLRPedido dl = new DLRPedido(sOrdNota, false);
		dl.setVisible(true);
		if (dl.OK == false) {
			dl.dispose();
			return;
		}
		imp.verifLinPag();

		String sSQL = "SELECT (SELECT COUNT(IC.CODITCOMPRA) FROM CPITCOMPRA IC WHERE IC.CODCOMPRA=C.CODCOMPRA"
				+ " AND IC.CODEMP=C.CODEMP AND IC.CODFILIAL=C.CODFILIAL),"
				+ "C.CODCOMPRA,C.CODFOR,F.RAZFOR,F.CNPJFOR,F.CPFFOR,C.DTEMITCOMPRA,F.ENDFOR,"
				+ "F.BAIRFOR,F.CEPFOR,C.DTENTCOMPRA,F.CIDFOR,F.UFFOR,F.FONEFOR,F.DDDFONEFOR,"
				+ "F.FAXFOR,F.INSCFOR,F.RGFOR,I.CODPROD,P.REFPROD,P.DESCPROD,P.CODUNID,"
				+ "I.QTDITCOMPRA,I.PRECOITCOMPRA,I.VLRPRODITCOMPRA,I.CODNAT,I.PERCICMSITCOMPRA,"
				+ "PERCIPIITCOMPRA,VLRIPIITCOMPRA,C.VLRBASEICMSCOMPRA,C.VLRICMSCOMPRA,C.VLRPRODCOMPRA,"
				+ "C.VLRDESCCOMPRA,C.VLRDESCITCOMPRA,C.VLRADICCOMPRA,C.VLRIPICOMPRA,"
				+ "C.VLRLIQCOMPRA,C.CODPLANOPAG,PG.DESCPLANOPAG"
				+ " FROM CPCOMPRA C, CPFORNECED F,CPITCOMPRA I, EQPRODUTO P, FNPLANOPAG PG"
				+ " WHERE C.CODCOMPRA="
				+ iCodCompra
				+ " AND F.CODFOR=C.CODFOR"
				+ " AND I.CODCOMPRA=C.CODCOMPRA AND P.CODPROD=I.CODPROD"
				+ " AND PG.CODPLANOPAG=C.CODPLANOPAG"
				+ " ORDER BY C.CODCOMPRA,P." + dl.getValor() + ";";

		PreparedStatement ps = null;
		ResultSet rs = null;
		int iItImp = 0;
		int iMaxItem = 0;
		try {
			ps = con.prepareStatement(sSQL);
			rs = ps.executeQuery();
			imp.limpaPags();
			iMaxItem = imp.verifLinPag() - 23;
			while (rs.next()) {
				if (imp.pRow() == 0) {
					imp.montaCab();
					imp.setTitulo("Relatório de Pedidos de Compras");
					imp.impCab(136, false);

					imp.say(imp.pRow() + 1, 0, "" + imp.normal());
					imp.say(imp.pRow() + 0, 4, "PEDIDO DE COMPRA No.: ");
					imp.say(imp.pRow() + 0, 25, rs.getString("CodCompra"));
					imp.say(imp.pRow() + 1, 0, "" + imp.normal());
					imp.say(imp.pRow() + 0, 0, "");
					imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
					imp.say(imp.pRow() + 0, 62, "FORNECEDOR");
					imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
					imp.say(imp.pRow() + 0, 0, "");
					imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
					imp.say(imp.pRow() + 0, 0, "");
					imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
					imp.say(imp.pRow() + 0, 4, "[ Nome/Razao Social ]");
					imp.say(imp.pRow() + 0, 76,
							rs.getString("CpfFor") != null ? "[ CPF ]"
									: "[ CNPJ ]");
					imp.say(imp.pRow() + 0, 96, "[ Data de Emissão ]");
					imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
					imp.say(imp.pRow() + 0, 4, rs.getString("CodFor") + " - "
							+ rs.getString("RazFor"));
					imp.say(imp.pRow() + 0, 76,
							rs.getString("CpfFor") != null ? Funcoes
									.setMascara(rs.getString("CpfFor"),
											"###.###.###-##") : Funcoes
									.setMascara(rs.getString("CnpjFor"),
											"##.###.###/####-##"));
					imp.say(imp.pRow() + 0, 100, Funcoes.sqlDateToStrDate(rs
							.getDate("DtEmitCompra")));
					imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
					imp.say(imp.pRow() + 0, 4, "[ Endereco ]");
					imp.say(imp.pRow() + 0, 55, "[ Bairro ]");
					imp.say(imp.pRow() + 0, 86, "[ CEP ]");
					imp.say(imp.pRow() + 0, 96, "[ Data de Saída ]");
					imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
					imp.say(imp.pRow() + 0, 4, rs.getString("EndFor"));
					imp.say(imp.pRow() + 0, 55, rs.getString("BairFor"));
					imp.say(imp.pRow() + 0, 86, Funcoes.setMascara(rs
							.getString("CepFor"), "#####-###"));
					imp.say(imp.pRow() + 0, 100, Funcoes.sqlDateToStrDate(rs
							.getDate("DtEntCompra")));
					imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
					imp.say(imp.pRow() + 0, 4, "[ Municipio ]");
					imp.say(imp.pRow() + 0, 39, "[ UF ]");
					imp.say(imp.pRow() + 0, 46, "[ Fone/Fax ]");
					imp.say(imp.pRow() + 0, 76,
							rs.getString("RgFor") != null ? "[ RG ]"
									: "[ Insc. Est. ]");
					imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
					imp.say(imp.pRow() + 0, 4, rs.getString("CidFor"));
					imp.say(imp.pRow() + 0, 39, rs.getString("UfFor"));
					imp.say(imp.pRow() + 0, 46, (rs.getString("DDDFONEFOR") != null ? "("+rs.getString("DDDFONEFOR")+")" : "")+
								(rs.getString("FoneFor") != null ? Funcoes.setMascara(rs.getString("FoneFor").trim(),"####-####") : "").trim()
							+ " - "
							+ Funcoes.setMascara(rs.getString("FaxFor"),
									"####-####"));
					imp.say(imp.pRow() + 0, 76,
							rs.getString("RgFor") != null ? rs
									.getString("RgFor") : rs
									.getString("CnpjFor"));
					imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
					imp.say(imp.pRow() + 0, 0, "");
					imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
					imp.say(imp.pRow() + 0, 0, "");
					imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
					imp.say(imp.pRow() + 0, 57, "DADOS DO(S) PRODUTO(S)");
					imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
					imp.say(imp.pRow() + 0, 0, "");
					imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
					imp.say(imp.pRow() + 0, 4, "Referencia");
					imp.say(imp.pRow() + 0, 18, "Descrição dos Produtos");
					imp.say(imp.pRow() + 0, 56, "Unidade");
					imp.say(imp.pRow() + 0, 65, "Quant.");
					imp.say(imp.pRow() + 0, 72, "Valor Unit.");
					imp.say(imp.pRow() + 0, 87, "Valor Total");
					imp.say(imp.pRow() + 0, 102, "ICM%");
					imp.say(imp.pRow() + 0, 108, "IPI%");
					imp.say(imp.pRow() + 0, 114, "Valor do IPI");
					imp.say(imp.pRow() + 0, 129, "Nat.");
					imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
					imp.say(imp.pRow() + 0, 0, "");
				}
				imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
				imp.say(imp.pRow() + 0, 4, rs.getString("RefProd"));
				imp.say(imp.pRow() + 0, 18, rs.getString("DescProd").substring(
						0, 39));
				imp.say(imp.pRow() + 0, 56, rs.getString("CodUnid"));
				imp.say(imp.pRow() + 0, 65, "" + rs.getDouble("QtdItCompra"));
				imp.say(imp.pRow() + 0, 72, Funcoes.strDecimalToStrCurrency(14,
						2, rs.getString("PrecoItCompra")));
				imp.say(imp.pRow() + 0, 87, Funcoes.strDecimalToStrCurrency(14,
						2, rs.getString("VlrProdItCompra")));
				imp.say(imp.pRow() + 0, 102, ""
						+ rs.getDouble("PercICMSItCompra"));
				imp.say(imp.pRow() + 0, 108, ""
						+ rs.getDouble("PercIPIItCompra"));
				imp.say(imp.pRow() + 0, 114, Funcoes.strDecimalToStrCurrency(
						14, 2, rs.getString("VlrIPIItCompra")));
				imp.say(imp.pRow() + 0, 129, Funcoes.setMascara(rs
						.getString("CodNat"), "#.###"));
				iItImp++;
				if ((imp.pRow() >= iMaxItem) | (iItImp == rs.getInt(1))) {
					if ((iItImp == rs.getInt(1))) {
						int iRow = imp.pRow();
						for (int i = 0; i < (iMaxItem - iRow); i++) {
							imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
							imp.say(imp.pRow() + 0, 0, "");
						}
					}
					if (rs.getInt(1) == iItImp) {
						imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
						imp.say(imp.pRow() + 0, 0, "");
						imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
						imp.say(imp.pRow() + 0, 56, "CALCULO DO(S) IMPOSTO(S)");
						imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
						imp.say(imp.pRow() + 0, 0, "");
						imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
						imp.say(imp.pRow() + 0, 4, "[ Base de Calculo ICMS ]");
						imp.say(imp.pRow() + 0, 29, "[ Valor do ICMS ]");
						imp.say(imp.pRow() + 0, 54, "[ B. Calc. ICMS Subst. ]");
						imp.say(imp.pRow() + 0, 79, "[ Valor do ICMS Subst. ]");
						imp.say(imp.pRow() + 0, 104, "[ Valor dos Produtos ]");
						imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
						imp.say(imp.pRow() + 0, 4, Funcoes
								.strDecimalToStrCurrency(14, 2, rs
										.getString("VlrBaseICMSCompra")));
						imp.say(imp.pRow() + 0, 29, Funcoes
								.strDecimalToStrCurrency(14, 2, rs
										.getString("VlrICMSCompra")));
						imp.say(imp.pRow() + 0, 104, Funcoes
								.strDecimalToStrCurrency(14, 2, rs
										.getString("VlrProdCompra")));
						imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
						imp.say(imp.pRow() + 0, 4, "[ Valor do Frete ]");
						imp.say(imp.pRow() + 0, 29, "[ Valor do Desconto ]");
						imp.say(imp.pRow() + 0, 54, "[ Outras Despesas ]");
						imp.say(imp.pRow() + 0, 79, "[ Valor do IPI ]");
						imp.say(imp.pRow() + 0, 104, "[ VALOR TOTAL ]");
						imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
						imp
								.say(
										imp.pRow() + 0,
										29,
										Funcoes
												.strDecimalToStrCurrency(
														14,
														2,
														rs
																.getString("VlrDescCompra") == null ? rs
																.getString("VlrDescItCompra")
																: rs
																		.getString("VlrDescCompra")));
						imp.say(imp.pRow() + 0, 64, Funcoes
								.strDecimalToStrCurrency(14, 2, rs
										.getString("VlrAdicCompra")));
						imp.say(imp.pRow() + 0, 79, Funcoes
								.strDecimalToStrCurrency(14, 2, rs
										.getString("VlrIPICompra")));
						imp.say(imp.pRow() + 0, 104, Funcoes
								.strDecimalToStrCurrency(14, 2, rs
										.getString("VlrLiqCompra")));
						iItImp = 0;
					} else {
						imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
						imp.say(imp.pRow() + 0, 0, "");
						imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
						imp.say(imp.pRow() + 0, 56, "CALCULO DO(S) IMPOSTO(S)");
						imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
						imp.say(imp.pRow() + 0, 0, "");
						imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
						imp.say(imp.pRow() + 0, 4, "[ Base de Calculo ICMS ]");
						imp.say(imp.pRow() + 0, 29, "[ Valor do ICMS ]");
						imp.say(imp.pRow() + 0, 54, "[ B. Calc. ICMS Subst. ]");
						imp.say(imp.pRow() + 0, 79, "[ Valor do ICMS Subst. ]");
						imp.say(imp.pRow() + 0, 104, "[ Valor dos Produtos ]");
						imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
						imp.say(imp.pRow() + 0, 4, "***************");
						imp.say(imp.pRow() + 0, 29, "***************");
						imp.say(imp.pRow() + 0, 104, "***************");
						imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
						imp.say(imp.pRow() + 0, 4, "[ Valor do Frete ]");
						imp.say(imp.pRow() + 0, 29, "[ Valor do Desconto ]");
						imp.say(imp.pRow() + 0, 54, "[ Outras Despesas ]");
						imp.say(imp.pRow() + 0, 79, "[ Valor do IPI ]");
						imp.say(imp.pRow() + 0, 104, "[ VALOR TOTAL ]");
						imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
						imp.say(imp.pRow() + 0, 4, "***************");
						imp.say(imp.pRow() + 0, 29, "***************");
						imp.say(imp.pRow() + 0, 54, "***************");
						imp.say(imp.pRow() + 0, 79, "***************");
						imp.say(imp.pRow() + 0, 104, "***************");
						imp.incPags();
					}
					imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
					imp.say(imp.pRow() + 0, 0, "");
					imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
					imp.say(imp.pRow() + 0, 0, "");
					imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
					imp.say(imp.pRow() + 0, 60, "DADOS ADICIONAIS");
					imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
					imp.say(imp.pRow() + 0, 0, "");
					imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
					imp.say(imp.pRow() + 0, 0, "");
					imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
					imp.say(imp.pRow() + 0, (116 - rs.getString("DescPlanoPag")
							.trim().length()) / 2, "FORMA DE PAGAMENTO : "
							+ rs.getString("DescPlanoPag"));
					imp.eject();
				}
			}
			imp.fechaGravacao();

			//      rs.close();
			//      ps.close();
			if (!con.getAutoCommit())
				con.commit();
			dl.dispose();
		} catch (SQLException err) {
			Funcoes.mensagemErro(this, "Erro ao consultar a tabela de Compra!"
					+ err.getMessage(),true,con,err);
		}

		if (bVisualizar) {
			imp.preview(this);
		} else {
			imp.print();
		}
	}

	private boolean comRef() {
		boolean bRetorno = false;
		String sSQL = "SELECT USAREFPROD, ORDNOTA FROM SGPREFERE1 WHERE CODEMP=? AND CODFILIAL=?";
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement(sSQL);
			ps.setInt(1, Aplicativo.iCodEmp);
			ps.setInt(2, ListaCampos.getMasterFilial("SGPREFERE1"));
			rs = ps.executeQuery();
			if (rs.next()) {
				if (rs.getString("UsaRefProd").trim().equals("S"))
					bRetorno = true;
				sOrdNota = rs.getString("OrdNota");
			}
			if (!con.getAutoCommit())
				con.commit();

		} catch (SQLException err) {
			Funcoes.mensagemErro(this, "Erro ao carregar a tabela PREFERE1!\n"
					+ err.getMessage(),true,con,err);
		} finally {
			sSQL = null;
			ps = null;
			rs = null;
		}
		return bRetorno;
	}	
	

	private boolean verificaBloq() {
		boolean retorno = false;
		ResultSet rs = null;
		PreparedStatement ps = null;
		String sSQL = null;
		int iCodCompra = 0;
		try {
			iCodCompra = txtCodCompra.getVlrInteger().intValue();
			if (iCodCompra != 0) {
				sSQL = "SELECT BLOQCOMPRA FROM CPCOMPRA WHERE CODEMP=? AND CODFILIAL=? AND CODCOMPRA=? ";
				ps = con.prepareStatement(sSQL);
				ps.setInt(1, Aplicativo.iCodEmp);
				ps.setInt(2, ListaCampos.getMasterFilial("CPCOMPRA"));
				ps.setInt(3, iCodCompra);
				rs = ps.executeQuery();
				
				if(rs.next()){
					if(rs.getString(1).equals("S"))
						retorno = true;
				}					
					
				ps.close();
				if (!con.getAutoCommit())
					con.commit();
			}
		} catch (SQLException err) {
			Funcoes.mensagemErro(this, "Erro verificando bloqueido da compra!\n"
					+ err.getMessage(),true,con,err);
		} finally {
			rs = null;
			ps = null;
			sSQL = null;
		}
		return retorno;
	}

	private boolean podeBloq() {
		boolean bRetorno = false;
		String sSQL = "SELECT BLOQCOMPRA FROM SGPREFERE1 WHERE CODEMP=? AND CODFILIAL=?";
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement(sSQL);
			ps.setInt(1, Aplicativo.iCodEmp);
			ps.setInt(2, ListaCampos.getMasterFilial("SGPREFERE1"));
			rs = ps.executeQuery();
			if (rs.next()) {
				if (rs.getString("BLOQCOMPRA").trim().equals("S"))
					bRetorno = true;
			}
			if (!con.getAutoCommit())
				con.commit();

		} catch (SQLException err) {
			err.printStackTrace();
		} finally {
			sSQL = null;
			ps = null;
			rs = null;
		}
		return bRetorno;
	}
	
	private void bloqCompra() {
		PreparedStatement ps = null;
		String sSQL = null;
		int iCodCompra = 0;
		try {
			iCodCompra = txtCodCompra.getVlrInteger().intValue();
			if (iCodCompra != 0) {
				sSQL = "EXECUTE PROCEDURE CPBLOQCOMPRASP(?,?,?,?)";
	            ps = con.prepareStatement(sSQL);
	            ps.setInt(1,Aplicativo.iCodEmp);
	            ps.setInt(2,ListaCampos.getMasterFilial("CPCOMPRA"));
	            ps.setInt(3,iCodCompra);
	            ps.setString(4,"S");
	            ps.executeUpdate();
	            ps.close();
	            if (!con.getAutoCommit())
	                con.commit();
			}
		} catch (SQLException err) {
			Funcoes.mensagemErro(this, "Erro bloqueando a compra!\n"
					+ err.getMessage(),true,con,err);
		} finally {
			ps = null;
			sSQL = null;
		}
	}

	public void keyTyped(KeyEvent kevt) {
		super.keyTyped(kevt);
	}

	public void keyReleased(KeyEvent kevt) {
		super.keyReleased(kevt);
	}

	public void beforePost(PostEvent pevt) {
		if (pevt.getListaCampos() == lcDet) {
			txtRefProd.setVlrString(txtRefProd.getText()); // ?
			if (txtCLoteProd.getVlrString().equals("S")) {
				if (!testaCodLote()) {
					pevt.cancela();
				}
			}
		}
		if (lcCampos.getStatus() == ListaCampos.LCS_INSERT) {
			testaCodCompra();
			txtStatusCompra.setVlrString("*");
		}
	}

	public void beforeInsert(InsertEvent ievt) {
	}

	public void afterInsert(InsertEvent ievt) {
		txtDtEntCompra.setVlrDate(new Date());
		txtDtEmitCompra.setVlrDate(new Date());
	}

	public void exec(int iCodCompra) {
		txtCodCompra.setVlrString(iCodCompra + "");
		lcCampos.carregaDados();
	}

	public void execDev(int iCodFor, int iCodTipoMov, String sSerie, int iDoc) {
		lcCampos.insert(true);
		txtCodFor.setVlrString(iCodFor + "");
		lcFor.carregaDados();
		txtCodTipoMov.setVlrString(iCodTipoMov + "");
		lcTipoMov.carregaDados();
		txtSerieCompra.setVlrString(sSerie);
		txtDocCompra.setVlrString(iDoc + "");
	}

	public void setConexao(Connection cn) {
		super.setConexao(cn);
		lcTipoMov.setConexao(cn);
		lcSerie.setConexao(cn);
		lcFor.setConexao(cn);
		lcPlanoPag.setConexao(cn);
		lcProd.setConexao(cn);
		lcProd2.setConexao(cn);
		lcNat.setConexao(cn);
		lcLote.setConexao(cn);
		lcFisc.setConexao(cn);
		lcCompra2.setConexao(cn);
		lcAlmox.setConexao(cn);
		montaDetalhe();
	}
}