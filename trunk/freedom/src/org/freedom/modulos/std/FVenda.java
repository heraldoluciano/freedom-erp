/**
 * @version 02/11/2003 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.std <BR>
 * Classe:
 * @(#)FVenda.java <BR>
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
 * Comentários sobre a classe...
 *  
 */

package org.freedom.modulos.std;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JTextField;

import org.freedom.acao.CarregaEvent;
import org.freedom.acao.CarregaListener;
import org.freedom.acao.DeleteEvent;
import org.freedom.acao.DeleteListener;
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
import org.freedom.componentes.JTabbedPanePad;
import org.freedom.componentes.JTextAreaPad;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Funcoes;
import org.freedom.layout.Layout;
import org.freedom.layout.Leiaute;
import org.freedom.layout.NFSaida;
import org.freedom.telas.Aplicativo;

public class FVenda extends FVD implements PostListener, CarregaListener,
		FocusListener, ActionListener, InsertListener, DeleteListener {
	
	private static final long serialVersionUID = 1L;	
	private JPanelPad pinCabVenda = new JPanelPad();
	private JPanelPad pinCabComis = new JPanelPad();
	private JPanelPad pinCabFiscal = new JPanelPad();
	private JPanelPad pinDet = new JPanelPad();
	private JPanelPad pinTot = new JPanelPad(200, 200);
	private JPanelPad pnTot = new JPanelPad(JPanelPad.TP_JPANEL,new GridLayout(1, 1));
	private JPanelPad pnCenter = new JPanelPad(JPanelPad.TP_JPANEL,new BorderLayout());
	private JButton btObs = new JButton(Icone.novo("btObs.gif"));
	private JButton btFechaVenda = new JButton(Icone.novo("btOk.gif"));
	private JButton btConsPgto = new JButton(Icone.novo("btConsPgto.gif"));
	private JButton btAdicOrc = new JButton("Busca Orçamento", Icone.novo("btOrcVenda.gif"));
	private JTextFieldPad txtCodVenda = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);
	private JTextFieldPad txtCodTipoMov = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);
	private JTextFieldPad txtCodSerie = new JTextFieldPad(JTextFieldPad.TP_STRING, 4, 0);
	private JTextFieldPad txtTipoVenda = new JTextFieldPad(JTextFieldPad.TP_STRING, 1, 0);	
	private JTextFieldPad txtDocVenda = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);
	private JTextFieldPad txtCodTratTrib = new JTextFieldPad(JTextFieldPad.TP_STRING, 2, 0);
	private JTextFieldPad txtTipoMov = new JTextFieldPad(JTextFieldPad.TP_STRING, 2, 0);
	private JTextFieldPad txtESTipoMov = new JTextFieldPad(JTextFieldPad.TP_STRING, 1, 0);
	private JTextFieldPad txtDtEmitVenda = new JTextFieldPad(JTextFieldPad.TP_DATE, 10, 0);
	private JTextFieldPad txtDtSaidaVenda = new JTextFieldPad(JTextFieldPad.TP_DATE, 10, 0);
	private JTextFieldPad txtCodCli = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);	
	private JTextFieldPad txtNomeCli = new JTextFieldPad(JTextFieldPad.TP_STRING, 50, 0);
	private JTextFieldPad txtCodVend = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);
	private JTextFieldPad txtCodClComis = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);
	private JTextFieldPad txtCodPlanoPag = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);	
	private JTextFieldPad txtPedCliVenda = new JTextFieldPad(JTextFieldPad.TP_STRING,10,0);
	private JTextFieldFK txtDescClComis = new JTextFieldFK(JTextFieldPad.TP_STRING, 40, 0);
	private JTextFieldPad txtPercComisVenda = new JTextFieldPad(JTextFieldPad.TP_NUMERIC, 7, casasDecFin);
	private JTextFieldPad txtCodItVenda = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);
	private JTextFieldPad txtQtdItVenda = new JTextFieldPad(JTextFieldPad.TP_NUMERIC, 15, casasDec);
	private JTextFieldPad txtCodProd = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 10, 0);
	private JTextFieldPad txtRefProd = new JTextFieldPad(JTextFieldPad.TP_STRING, 13, 0);
	private JTextFieldPad txtCLoteProd = new JTextFieldPad(JTextFieldPad.TP_STRING, 1, 0);
	private JTextFieldPad txtVerifProd = new JTextFieldPad(JTextFieldPad.TP_STRING, 1, 0);
	private JTextFieldPad txtPrecoItVenda = new JTextFieldPad(JTextFieldPad.TP_NUMERIC, 15, casasDecFin);
	private JTextFieldPad txtPercDescItVenda = new JTextFieldPad(JTextFieldPad.TP_NUMERIC, 7, casasDecFin);
	private JTextFieldPad txtVlrDescItVenda = new JTextFieldPad(JTextFieldPad.TP_NUMERIC, 15, casasDecFin);
	private JTextFieldPad txtVlrComisItVenda = new JTextFieldPad(JTextFieldPad.TP_NUMERIC, 15, casasDecFin);
	private JTextFieldPad txtPercComItVenda = new JTextFieldPad(JTextFieldPad.TP_DECIMAL, 6, casasDecFin);
	private JTextFieldPad txtCodNat = new JTextFieldPad(JTextFieldPad.TP_STRING, 4, 0);
	private JTextFieldFK txtSldLiqProd = new JTextFieldFK(JTextFieldPad.TP_NUMERIC, 15, casasDec);
	private JTextFieldPad txtPercICMSItVenda = new JTextFieldPad(JTextFieldPad.TP_NUMERIC, 7, casasDecFin);
	private JTextFieldPad txtVlrICMSItVenda = new JTextFieldPad(JTextFieldPad.TP_NUMERIC, 15, casasDecFin);
	private JTextFieldPad txtVlrLiqItVenda = new JTextFieldPad(JTextFieldPad.TP_NUMERIC, 15, casasDecFin);
	private JTextFieldPad txtEstCli = new JTextFieldPad(JTextFieldPad.TP_STRING, 2, 0);
	private JTextFieldPad txtClasComis = new JTextFieldPad(JTextFieldPad.TP_STRING, 1, 0);
	private JTextFieldPad txtCodMens = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);
	private JTextFieldPad txtCodLote = new JTextFieldPad(JTextFieldPad.TP_STRING, 13, 0);
	private JTextFieldPad txtCodFisc = new JTextFieldPad(JTextFieldPad.TP_STRING, 13, 0);
	private JTextFieldPad txtTipoFisc = new JTextFieldPad(JTextFieldPad.TP_STRING, 2, 0);
	private JTextFieldPad txtRedFisc = new JTextFieldPad(JTextFieldPad.TP_NUMERIC, 6, 2);
	private JTextFieldPad txtVlrFreteVenda = new JTextFieldPad(JTextFieldPad.TP_NUMERIC, 15, casasDecFin);
	private JTextFieldPad txtVlrComisVenda = new JTextFieldPad(JTextFieldPad.TP_NUMERIC, 15, casasDecFin);
	private JTextFieldPad txtMedComisVenda = new JTextFieldPad(JTextFieldPad.TP_NUMERIC, 9, casasDecFin);
	private JTextFieldPad txtVlrICMSVenda = new JTextFieldPad(JTextFieldPad.TP_NUMERIC, 15, casasDecFin);
	private JTextFieldPad txtVlrIPIVenda = new JTextFieldPad(JTextFieldPad.TP_NUMERIC, 15, casasDecFin);
	private JTextFieldPad txtVlrPisVenda = new JTextFieldPad(JTextFieldPad.TP_NUMERIC, 15, casasDecFin);
	private JTextFieldPad txtVlrCofinsVenda = new JTextFieldPad(JTextFieldPad.TP_NUMERIC, 15, casasDecFin);
	private JTextFieldPad txtVlrIRVenda = new JTextFieldPad(JTextFieldPad.TP_NUMERIC, 15, casasDecFin);
	private JTextFieldPad txtVlrCSocialVenda = new JTextFieldPad(JTextFieldPad.TP_NUMERIC, 15, casasDecFin);
	private JTextFieldPad txtVlrProdVenda = new JTextFieldPad(JTextFieldPad.TP_NUMERIC, 15, casasDecFin);
	private JTextFieldPad txtVlrDescVenda = new JTextFieldPad(JTextFieldPad.TP_NUMERIC, 15, casasDecFin);
	private JTextFieldPad txtVlrLiqVenda = new JTextFieldPad(JTextFieldPad.TP_NUMERIC, 15, casasDecFin);
	private JTextFieldPad txtVlrProdItVenda = new JTextFieldPad(JTextFieldPad.TP_NUMERIC, 15, casasDecFin);
	private JTextFieldPad txtBaseIPIItVenda = new JTextFieldPad(JTextFieldPad.TP_NUMERIC, 15, casasDec);
	private JTextFieldPad txtStrDescItVenda = new JTextFieldPad(JTextFieldPad.TP_STRING, 50, 0);
	private JTextAreaPad txaObsItVenda = new JTextAreaPad(500);
	private JTextFieldPad txtBaseICMSItVenda = new JTextFieldPad(JTextFieldPad.TP_NUMERIC, 15, casasDecFin);
	private JTextFieldPad txtAliqFisc = new JTextFieldPad(JTextFieldPad.TP_DECIMAL, 9, casasDecFin);
	private JTextFieldPad txtAliqIPIItVenda = new JTextFieldPad(JTextFieldPad.TP_DECIMAL, 6, casasDecFin);
	private JTextFieldPad txtVlrIPIItVenda = new JTextFieldPad(JTextFieldPad.TP_NUMERIC, 15, casasDecFin);
	private JTextFieldPad txtAliqIPIFisc = new JTextFieldPad(JTextFieldPad.TP_DECIMAL, 6, casasDecFin);
	private JTextFieldPad txtVlrBrutVenda = new JTextFieldPad(JTextFieldPad.TP_NUMERIC, 15, casasDecFin);
	private JTextFieldPad txtStatusVenda = new JTextFieldPad(JTextFieldPad.TP_STRING, 2, 0);
	private JTextFieldPad txtOrigFisc = new JTextFieldPad(JTextFieldPad.TP_STRING, 1, 0);
	private JTextFieldPad txtCodEmpLG = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);
	private JTextFieldPad txtCodFilialLG = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);
	private JTextFieldPad txtCodLog = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);
	private JTextFieldFK txtDescTipoMov = new JTextFieldFK(JTextFieldPad.TP_STRING, 40, 0);
	private JTextFieldFK txtDescCli = new JTextFieldFK(JTextFieldPad.TP_STRING,50, 0);
	private JTextFieldFK txtDescVend = new JTextFieldFK(JTextFieldPad.TP_STRING, 40, 0);
	private JTextFieldFK txtDescPlanoPag = new JTextFieldFK(JTextFieldPad.TP_STRING, 40, 0);
	private JTextFieldFK txtDescProd = new JTextFieldFK(JTextFieldPad.TP_STRING, 50, 0);
	private JTextFieldFK txtDescNat = new JTextFieldFK(JTextFieldPad.TP_STRING,40, 0);
	private JTextFieldFK txtDescLote = new JTextFieldFK(JTextFieldPad.TP_DATE,10, 0);
	private JTextFieldFK txtDescFisc = new JTextFieldFK(JTextFieldPad.TP_STRING, 50, 0);
	private JTextField txtFiscalTipoMov1 = new JTextField();
	private JTextField txtFiscalTipoMov2 = new JTextField();	
	private JTextFieldPad txtCodAlmoxItVenda = new JTextFieldPad(JTextFieldPad.TP_INTEGER,5, 0);
	private JLabelPad lbStatus = new JLabelPad();
	private JCheckBoxPad chbImpPedTipoMov = new JCheckBoxPad("Imp.ped.", "S","N");
	private JCheckBoxPad chbImpNfTipoMov = new JCheckBoxPad("Imp.NF", "S", "N");
	private JCheckBoxPad chbImpBolTipoMov = new JCheckBoxPad("Imp.bol.?", "S","N");
	private JCheckBoxPad chbReImpNfTipoMov = new JCheckBoxPad("Reimp.NF?", "S","N");
	private ListaCampos lcTratTrib = new ListaCampos(this, "TT");
	private ListaCampos lcTipoMov = new ListaCampos(this, "TM");
	private ListaCampos lcCli = new ListaCampos(this, "CL");
	private ListaCampos lcVendedor = new ListaCampos(this, "VD");
	private ListaCampos lcPlanoPag = new ListaCampos(this, "PG");
	private ListaCampos lcSerie = new ListaCampos(this, "SE");
	private ListaCampos lcProd = new ListaCampos(this, "PD");
	private ListaCampos lcProd2 = new ListaCampos(this, "PD");
	private ListaCampos lcNat = new ListaCampos(this, "NT");
	private ListaCampos lcLote = new ListaCampos(this, "LE");
	private ListaCampos lcClComis = new ListaCampos(this, "CM");
	private ListaCampos lcFisc = new ListaCampos(this);
	private ListaCampos lcVenda2 = new ListaCampos(this);	
	private ListaCampos lcAlmox = new ListaCampos(this,"AX");
	private JTabbedPanePad tpnCab = new JTabbedPanePad();
	private JButton btAltComis = new JButton(Icone.novo("btEditar.gif"));
	private JTextFieldPad txtUltCamp = new JTextFieldPad(JTextFieldPad.TP_DECIMAL,15,2);
	private boolean[] bPrefs = null;
	private boolean bCtrl = false;
	private String sOrdNota = "";
	private int iCodCliAnt = 0;

	public FVenda() {
		setTitulo("Venda");
		setAtribos(15, 10, 775, 460);

		pnCliCab.add(tpnCab);
		//pinCabVenda.setFirstFocus(txtCodVenda);
		tpnCab.addTab("Venda", pinCabVenda);
		tpnCab.addTab("Comissão", pinCabComis);
		tpnCab.addTab("Fiscal", pinCabFiscal);

		btAdicOrc.setPreferredSize(new Dimension(180, 0));
		pnNavCab.add(btAdicOrc, BorderLayout.EAST);

		pnMaster.remove(2); //Remove o JPanelPad predefinido da class FDados
		pnGImp.removeAll(); //Remove os botões de impressão para adicionar logo
							// embaixo
		pnGImp.setLayout(new GridLayout(1, 4)); //redimensiona o painel de
												// impressão
		pnGImp.setPreferredSize(new Dimension(280, 26));
		pnGImp.add(btPrevimp);
		pnGImp.add(btImp);
		pnGImp.add(btFechaVenda);
		pnGImp.add(btConsPgto);
		pnGImp.add(btObs);//Agora o painel está maior

		pnTot.setPreferredSize(new Dimension(110, 200)); //JPanelPad de Totais
		pnTot.add(pinTot);
		pnCenter.add(pnTot, BorderLayout.EAST);
		pnCenter.add(spTab, BorderLayout.CENTER);

		JPanelPad pnLab = new JPanelPad(JPanelPad.TP_JPANEL, new GridLayout(1,1));
		pnLab.add(new JLabelPad(" Totais:")); //Label do painel de totais

		pnMaster.add(pnCenter, BorderLayout.CENTER);

		//FK Cliente
		lcCli.add(new GuardaCampo(txtCodCli, "CodCli", "Cód.cli.",ListaCampos.DB_PK, false));
		lcCli.add(new GuardaCampo(txtDescCli, "RazCli","Razão social do cliente", ListaCampos.DB_SI, false));
		lcCli.add(new GuardaCampo(txtNomeCli, "NomeCli","Nome do cliente", ListaCampos.DB_SI, false));txtNomeCli.setSize(197,20);
		lcCli.add(new GuardaCampo(txtCodPlanoPag, "CodPlanoPag", "Cód.p.pg.",ListaCampos.DB_SI, false));
		lcCli.add(new GuardaCampo(txtCodVend, "CodVend", "Cód.comiss.",ListaCampos.DB_SI, false));
		lcCli.add(new GuardaCampo(txtEstCli, "UfCli", "UF", ListaCampos.DB_SI,false));
		//lcCli.setWhereAdic("ATIVOCLI='S'");
		lcCli.montaSql(false, "CLIENTE", "VD");
		lcCli.setQueryCommit(false);
		lcCli.setReadOnly(true);
		txtCodCli.setTabelaExterna(lcCli);

		//FK Vendedor
		lcVendedor.add(new GuardaCampo(txtCodVend, "CodVend", "Cód.Venda",ListaCampos.DB_PK, false));
		lcVendedor.add(new GuardaCampo(txtDescVend, "NomeVend","Nome do comissionado", ListaCampos.DB_SI, false));
		lcVendedor.add(new GuardaCampo(txtCodClComis, "CodClComis", "Cód.c.comis.",ListaCampos.DB_SI, false));
		lcVendedor.add(new GuardaCampo(txtPercComisVenda, "PercComVend","% Comis.", ListaCampos.DB_SI, false));
		lcVendedor.montaSql(false, "VENDEDOR", "VD");
		lcVendedor.setQueryCommit(false);
		lcVendedor.setReadOnly(true);
		txtCodVend.setTabelaExterna(lcVendedor);

		//FK Plano de Pagamento
		lcPlanoPag.add(new GuardaCampo(txtCodPlanoPag, "CodPlanoPag","Cód.p.pg.", ListaCampos.DB_PK, false));
		lcPlanoPag.add(new GuardaCampo(txtDescPlanoPag, "DescPlanoPag","Descrição plano de pagamento", ListaCampos.DB_SI, false));
		lcPlanoPag.montaSql(false, "PLANOPAG", "FN");
		lcPlanoPag.setQueryCommit(false);
		lcPlanoPag.setReadOnly(true);
		txtCodPlanoPag.setTabelaExterna(lcPlanoPag);

		//FK Série
		lcSerie.add(new GuardaCampo(txtCodSerie, "Serie", "Série",ListaCampos.DB_PK, false));
		lcSerie.add(new GuardaCampo(txtDocVenda, "DocSerie", "Doc. atual",ListaCampos.DB_SI, false));
		lcSerie.montaSql(false, "SERIE", "LF");
		lcSerie.setQueryCommit(false);
		lcSerie.setReadOnly(true);
		txtCodSerie.setTabelaExterna(lcSerie);

		//FK de Lotes
		lcLote.add(new GuardaCampo(txtCodLote, "CodLote", "Lote",ListaCampos.DB_PK, txtDescLote, false));
		lcLote.add(new GuardaCampo(txtDescLote, "VenctoLote", "Dt.vencto.",ListaCampos.DB_SI, false));
		lcLote.add(new GuardaCampo(txtSldLiqProd, "SldLiqLote", "Saldo",	ListaCampos.DB_SI, false));
		lcLote.setDinWhereAdic("CODPROD=#N AND (VENCTOLOTE >= #D OR #S IN('DV','PE'))",txtCodProd);
		lcLote.setDinWhereAdic("", txtDtSaidaVenda);
		lcLote.setDinWhereAdic("", txtTipoMov);
		lcLote.montaSql(false, "LOTE", "EQ");
		lcLote.setQueryCommit(false);
		lcLote.setReadOnly(true);
		txtCodLote.setTabelaExterna(lcLote);
		txtDescLote.setListaCampos(lcLote);
		txtDescLote.setNomeCampo("VenctoLote");
		txtDescLote.setLabel("Vencimento");

		//FK de Classificação Fiscal (É acionada também quando o listaCampos de
		// produtos é acionado)

		lcFisc.add(new GuardaCampo(txtCodFisc, "CodFisc", "Cód.fisc.",ListaCampos.DB_PK, txtDescFisc, false));
		lcFisc.add(new GuardaCampo(txtDescFisc, "DescFisc", "Descrição fiscal",ListaCampos.DB_SI, false));
		lcFisc.add(new GuardaCampo(txtAliqIPIFisc, "AliqIPIFisc", "% IPI",ListaCampos.DB_SI, false));
		lcFisc.add(new GuardaCampo(txtAliqFisc, "AliqFisc", "% ICMS",ListaCampos.DB_SI, false));
		lcFisc.montaSql(false, "CLFISCAL", "LF");
		lcFisc.setQueryCommit(false);
		lcFisc.setReadOnly(true);
		txtCodFisc.setTabelaExterna(lcFisc);
		txtDescFisc.setListaCampos(lcFisc);

		//FK de Natureza de Operação (É acionada também quando o listaCampos de
		// Classificação Fiscal é acionado)

		lcNat.add(new GuardaCampo(txtCodNat, "CodNat", "CFOP",ListaCampos.DB_PK, false));
		lcNat.add(new GuardaCampo(txtDescNat, "DescNat", "Descrição da CFOP",ListaCampos.DB_SI, false));
		lcNat.montaSql(false, "NATOPER", "LF");
		lcNat.setQueryCommit(false);
		lcNat.setReadOnly(true);
		txtCodNat.setTabelaExterna(lcNat);
		txtDescNat.setListaCampos(lcNat);

		//FK de Almoxarifado

		lcAlmox.add(new GuardaCampo(txtCodAlmoxItVenda, "codalmox", "Cod.Almox.",ListaCampos.DB_PK, false));
		lcAlmox.montaSql(false, "ALMOX", "EQ");
		lcAlmox.setQueryCommit(false);
		lcAlmox.setReadOnly(true);
		txtCodAlmoxItVenda.setTabelaExterna(lcAlmox);

		
		//FK de Tratamento Tributário (É acionada também quando o listaCampos
		// de Tratamento tributário é acionado)

		lcTratTrib.add(new GuardaCampo(txtCodTratTrib, "CodTratTrib",
				"Cód.tr.trib.", ListaCampos.DB_PK, false));
		lcTratTrib.montaSql(false, "TRATTRIB", "LF");
		lcTratTrib.setQueryCommit(false);
		lcTratTrib.setReadOnly(true);
		txtCodTratTrib.setTabelaExterna(lcTratTrib);

		//ListaCampos de Totais (É acionada pelo listaCampos de Venda)

		lcVenda2.add(new GuardaCampo(txtCodVenda, "CodVenda", "N.pedido",ListaCampos.DB_PK, false));
		//lcVenda2.add(new GuardaCampo(txtTipoVenda, "TipoVenda", "Tp.Venda",ListaCampos.DB_PK, false));
		lcVenda2.add(new GuardaCampo(txtVlrFreteVenda, "VlrFreteVenda","Vlr. frete", ListaCampos.DB_SI, false));
		lcVenda2.add(new GuardaCampo(txtVlrComisVenda, "VlrComisVenda","Vlr. comis.", ListaCampos.DB_SI, false));
		lcVenda2.add(new GuardaCampo(txtMedComisVenda, "PercMComisVenda","Med. comis.", ListaCampos.DB_SI, false));
		lcVenda2.add(new GuardaCampo(txtVlrICMSVenda, "VlrICMSVenda","Vlr. ICMS", ListaCampos.DB_SI, false));
		lcVenda2.add(new GuardaCampo(txtVlrIPIVenda, "VlrIPIVenda", "Vlr. IPI",ListaCampos.DB_SI, false));
		lcVenda2.add(new GuardaCampo(txtVlrPisVenda, "VlrPisVenda", "Vlr. PIS",ListaCampos.DB_SI, false));
		lcVenda2.add(new GuardaCampo(txtVlrCofinsVenda, "VlrCofinsVenda","Vlr. COFINS", ListaCampos.DB_SI, false));
		lcVenda2.add(new GuardaCampo(txtVlrIRVenda, "VlrIRVenda", "Vlr. I.R.",ListaCampos.DB_SI, false));
		lcVenda2.add(new GuardaCampo(txtVlrCSocialVenda, "VlrCSocialVenda","Vlr. c.social.", ListaCampos.DB_SI, false));
		lcVenda2.add(new GuardaCampo(txtVlrProdVenda, "VlrProdVenda","Vlr. prod.", ListaCampos.DB_SI, false));
		lcVenda2.add(new GuardaCampo(txtVlrDescVenda, "VlrDescItVenda","Vlr. desc.", ListaCampos.DB_SI, false));
		lcVenda2.add(new GuardaCampo(txtVlrLiqVenda, "VlrLiqVenda","Vlr. liq.", ListaCampos.DB_SI, false));
		lcVenda2.add(new GuardaCampo(txtVlrBrutVenda, "VlrProdVenda","Vlr. prod.", ListaCampos.DB_SI, false));
		lcVenda2.setWhereAdic("TIPOVENDA='V'");
		lcVenda2.montaSql(false, "VENDA", "VD");
		lcVenda2.setQueryCommit(false);
		lcVenda2.setReadOnly(true);

		//lc para trazer classificacao da comissao

		lcClComis.add(new GuardaCampo(txtCodClComis, "CodClComis","Cód.c.comis.", ListaCampos.DB_PK, false));
		lcClComis.add(new GuardaCampo(txtDescClComis, "DescClComis","Descrição da classificação da comissão", ListaCampos.DB_SI,false));
		lcClComis.montaSql(false, "CLCOMIS", "VD");
		lcClComis.setQueryCommit(false);
		lcClComis.setReadOnly(true);
		txtCodClComis.setTabelaExterna(lcClComis);

		//Coloca os comentrio nos botões

		btFechaVenda.setToolTipText("Fechar a venda (F4)");
		btConsPgto.setToolTipText("Consulta pagamentos (F5)");
		btObs.setToolTipText("Observações (Ctrl + O)");

		//Desativa as os TextFields para que os usuários não possam mexer

		txtCodSerie.setAtivo(false);
		txtDocVenda.setAtivo(false);
		txtVlrFreteVenda.setAtivo(false);
		txtVlrComisVenda.setAtivo(false);
		txtMedComisVenda.setAtivo(false);
		txtVlrICMSVenda.setAtivo(false);
		txtVlrIPIVenda.setAtivo(false);
		txtVlrPisVenda.setAtivo(false);
		txtVlrCofinsVenda.setAtivo(false);
		txtVlrIRVenda.setAtivo(false);
		txtVlrCSocialVenda.setAtivo(false);
		txtVlrProdVenda.setAtivo(false);
		txtVlrDescVenda.setAtivo(false);
		txtVlrLiqVenda.setAtivo(false);
		//Desativa as os TextFields para que os usuários não possam mexer

		txtBaseICMSItVenda.setAtivo(false);
		txtVlrICMSItVenda.setAtivo(false);
		txtVlrLiqItVenda.setAtivo(false);
		txtAliqIPIItVenda.setAtivo(false);
		
		//Adiciona os Listeners

		txtDescProd.setToolTipText("Clique aqui duas vezes para alterar a descrição.");
		txtDescProd.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent mevt) {
				if (mevt.getClickCount() == 2)
					mostraTelaDecricao(txaObsItVenda, txtCodProd.getVlrInteger().intValue(), txtDescProd.getVlrString());
			}
		});

		btFechaVenda.addActionListener(this);
		btConsPgto.addActionListener(this);
		btObs.addActionListener(this);
		btAdicOrc.addActionListener(this);

		txtPercDescItVenda.addFocusListener(this);
		txtPercComItVenda.addFocusListener(this);
		txtVlrDescItVenda.addFocusListener(this);
		txtVlrProdItVenda.addFocusListener(this);
		txtQtdItVenda.addFocusListener(this);
		txtCodNat.addFocusListener(this);
		txtPrecoItVenda.addFocusListener(this);
		txtPercICMSItVenda.addFocusListener(this);
		txtAliqIPIItVenda.addFocusListener(this);
		lcCampos.addCarregaListener(this);
		lcVendedor.addCarregaListener(this);
		lcCli.addCarregaListener(this);
		lcFisc.addCarregaListener(this);
		lcProd.addCarregaListener(this);
		lcProd2.addCarregaListener(this);
		lcNat.addCarregaListener(this);
		lcVenda2.addCarregaListener(this);
		lcDet.addCarregaListener(this);
		lcPlanoPag.addCarregaListener(this);
		lcCampos.addPostListener(this);
		lcDet.addPostListener(this);
		lcCampos.addInsertListener(this);
		lcDet.addInsertListener(this);
		lcDet.addDeleteListener(this);

		btImp.addActionListener(this);
		btPrevimp.addActionListener(this);
		btAltComis.addActionListener(this);
		
		lbStatus.setForeground(Color.WHITE);
		lbStatus.setFont( new Font("Arial", Font.BOLD, 13));
		lbStatus.setOpaque(true);
		lbStatus.setVisible(false);
		
	    setImprimir(true);
	}

	//Função criada para montar a tela conforme a preferência do usuário:
	//com ou sem Referência de PK;
	private void montaTela() {
		bPrefs = prefs(); //Carrega as preferências

		//FK Produto

		lcProd.add(new GuardaCampo(txtCodProd, "codprod", "Cód.prod.",ListaCampos.DB_PK, false));
		lcProd.add(new GuardaCampo(txtDescProd, "DescProd","Descrição do produtos", ListaCampos.DB_SI, false));
		lcProd.add(new GuardaCampo(txtRefProd, "RefProd", "Ref.prod.",ListaCampos.DB_SI, false));
		lcProd.add(new GuardaCampo(txtCLoteProd, "CLoteProd", "C/Lote",ListaCampos.DB_SI, false));
		lcProd.add(new GuardaCampo(txtCodFisc, "CodFisc", "Cód.fisc.",ListaCampos.DB_FK, false));
		lcProd.add(new GuardaCampo(txtPercComItVenda, "ComisProd", "% Comis.",ListaCampos.DB_SI, false));
		lcProd.add(new GuardaCampo(txtSldLiqProd, "SldLiqProd", "Saldo",ListaCampos.DB_SI, false));
		lcProd.add(new GuardaCampo(txtVerifProd, "VerifProd", "Verif. custo",ListaCampos.DB_SI, false));
		lcProd.setWhereAdic("ATIVOPROD='S' AND TIPOPROD IN ('P','S','F'" + (bPrefs[8] ? ",'M'" : "") + ")");
		lcProd.montaSql(false, "PRODUTO", "EQ");
		lcProd.setQueryCommit(false);
		lcProd.setReadOnly(true);
		txtCodProd.setTabelaExterna(lcProd);
		//FK do produto (*Somente em caso de referências este listaCampos
		//Trabalha como gatilho para o listaCampos de produtos, assim
		//carregando o código do produto que será armazenado no Banco)
		lcProd2.add(new GuardaCampo(txtRefProd, "RefProd", "Ref.prod.",ListaCampos.DB_PK, false));
		lcProd2.add(new GuardaCampo(txtDescProd, "DescProd","Descrição do produto", ListaCampos.DB_SI, false));
		lcProd2.add(new GuardaCampo(txtCodProd, "CodProd", "Cód.prod.",ListaCampos.DB_SI, false));
		lcProd2.add(new GuardaCampo(txtCLoteProd, "CLoteProd", "C/Lote",ListaCampos.DB_SI, false));
		lcProd2.add(new GuardaCampo(txtCodFisc, "CodFisc", "Cód.fisc.",ListaCampos.DB_FK, false));
		lcProd2.add(new GuardaCampo(txtPercComItVenda, "ComisProd", "% comis.",ListaCampos.DB_SI, false));
		lcProd2.add(new GuardaCampo(txtSldLiqProd, "SldLiqProd", "Saldo",ListaCampos.DB_SI, false));
		lcProd2.add(new GuardaCampo(txtVerifProd, "VerifProd", "Verif. custo",ListaCampos.DB_SI, false));
		txtRefProd.setNomeCampo("RefProd");
		txtRefProd.setListaCampos(lcDet);
		lcProd2.setWhereAdic("ATIVOPROD='S' AND TIPOPROD IN ('P','S','F'" + (bPrefs[8] ? ",'M'" : "") + ")");
		lcProd2.montaSql(false, "PRODUTO", "EQ");
		lcProd2.setQueryCommit(false);
		lcProd2.setReadOnly(true);
		txtRefProd.setTabelaExterna(lcProd2);

		//FK Tipo de movimentos
		lcTipoMov.add(new GuardaCampo(txtCodTipoMov, "CodTipoMov","Cód.tp.mov.", ListaCampos.DB_PK, false));
		lcTipoMov.add(new GuardaCampo(txtDescTipoMov, "DescTipoMov","Descrição do tipo de movimento", ListaCampos.DB_SI, false));
		lcTipoMov.add(new GuardaCampo(txtCodSerie, "Serie", "Série",ListaCampos.DB_FK, false));
		lcTipoMov.add(new GuardaCampo(txtTipoMov, "TipoMov", "Tipo mov.",ListaCampos.DB_SI, false));
		lcTipoMov.add(new GuardaCampo(txtESTipoMov, "ESTipoMov", "E/S",ListaCampos.DB_SI, false));
		lcTipoMov.add(new GuardaCampo(chbImpPedTipoMov, "ImpPedTipoMov","Imp.ped.", ListaCampos.DB_SI, false));
		lcTipoMov.add(new GuardaCampo(chbImpNfTipoMov, "ImpNfTipoMov","Imp.NF", ListaCampos.DB_SI, false));
		lcTipoMov.add(new GuardaCampo(chbImpBolTipoMov, "ImpBolTipoMov","Imp.bol.", ListaCampos.DB_SI, false));
		lcTipoMov.add(new GuardaCampo(chbReImpNfTipoMov, "ReImpNfTipoMov","Reimp.NF", ListaCampos.DB_SI, false));
		/*
		 * SELECT CODTIPOMOV, DESCTIPOMOV FROM EQTIPOMOV WHERE ( TUSUTIPOMOV='S'
		 * OR EXISTS (SELECT * FROM EQTIPOMOVUSU TU WHERE
		 * TU.CODEMP=EQTIPOMOV.CODEMP AND TU.CODFILIAL=EQTIPOMOV.CODFILIAL AND
		 * TU.CODTIPOMOV=EQTIPOMOV.CODTIPOMOV AND TU.CODEMPUS=4 AND
		 * TU.CODFILIALUS=1 AND TU.IDUSU='sysdba') ) ORDER BY 1
		 */
		lcTipoMov.setWhereAdic("( "
						+ "(ESTIPOMOV = 'S' OR TIPOMOV IN ('PV','DV')) AND "
						+ " ( TUSUTIPOMOV='S' OR	EXISTS (SELECT * FROM EQTIPOMOVUSU TU "
						+ "WHERE TU.CODEMP=EQTIPOMOV.CODEMP AND TU.CODFILIAL=EQTIPOMOV.CODFILIAL AND "
						+ "TU.CODTIPOMOV=EQTIPOMOV.CODTIPOMOV AND TU.CODEMPUS="
						+ Aplicativo.iCodEmp + " AND " + "TU.CODFILIALUS="
						+ ListaCampos.getMasterFilial("SGUSUARIO")
						+ " AND TU.IDUSU='" + Aplicativo.strUsuario + "') ) "
						+ ")");
		if (bPrefs[5]) {
			txtFiscalTipoMov1.setText("S");
			txtFiscalTipoMov2.setText("N");
			lcTipoMov.setDinWhereAdic("FISCALTIPOMOV IN(#S,#S)",txtFiscalTipoMov1);
			lcTipoMov.setDinWhereAdic("", txtFiscalTipoMov2);
		}
		lcTipoMov.montaSql(false, "TIPOMOV", "EQ");
		lcTipoMov.setQueryCommit(false);
		lcTipoMov.setReadOnly(true);
		txtCodTipoMov.setTabelaExterna(lcTipoMov);

		//Adiciona os componentes na tela e no ListaCompos da venda
		setListaCampos(lcCampos);
		setAltCab(160);
		setPainel(pinCabVenda);
		adicCampo(txtCodVenda, 7, 20, 90, 20, "CodVenda", "N. pedido",ListaCampos.DB_PK, true);
		//adicCampoInvisivel(txtTipoVenda,"tipovenda","Tp.Venda",ListaCampos.DB_PK,true);
		adicCampo(txtCodTipoMov, 100, 20, 77, 20, "CodTipoMov", "Cód.tp.mov.",ListaCampos.DB_FK, txtDescTipoMov, true);
		adicDescFK(txtDescTipoMov, 180, 20, 197, 20, "DescTipoMov",	"Descrição do tipo de movimento");
		adicCampo(txtCodSerie, 380, 20, 77, 20, "Serie", "Série",ListaCampos.DB_FK, false);
		adicCampo(txtDocVenda, 460, 20, 77, 20, "DocVenda", "N doc.",ListaCampos.DB_SI, false);
		adicCampo(txtDtEmitVenda, 540, 20, 97, 20, "DtEmitVenda", "Data emis.",ListaCampos.DB_SI, true);
		adicCampo(txtDtSaidaVenda, 640, 20, 97, 20, "DtSaidaVenda","Data saída", ListaCampos.DB_SI, true);
		adicCampo(txtCodCli, 7, 60, 80, 20, "CodCli", "Cód. cli.",ListaCampos.DB_FK, txtDescCli, true);
		adicDescFK(txtDescCli, 90, 60, 197, 20, "RazCli","Razão social do cliente");
		adicCampo(txtCodPlanoPag, 290, 60, 77, 20, "CodPlanoPag", "Cód.p.pag.",ListaCampos.DB_FK, txtDescPlanoPag, true);
		adicDescFK(txtDescPlanoPag, 370, 60, 197, 20, "DescPlanoPag","Descrição do plano de pag.");
		adicCampo(txtPedCliVenda, 570, 60, 75, 20, "PedCliVenda", "N.ped.cli.",ListaCampos.DB_SI, false);
		adic(lbStatus, 649, 60, 95, 20);
		setPainel(pinCabComis);
		adicCampo(txtCodVend, 7, 20, 80, 20, "CodVend", "Cód.comiss.",ListaCampos.DB_FK, txtDescVend, true);
		adicDescFK(txtDescVend, 90, 20, 197, 20, "NomeVend","Nome do comissionado");
		if (bPrefs[4]) {
			adicCampo(txtCodClComis, 290, 20, 80, 20, "CodClComis","Cód.c.comis.", ListaCampos.DB_FK, txtDescClComis, true);
			adicDescFK(txtDescClComis, 373, 20, 260, 20, "DescClComis","Descrição da class. de comis.");

			adicCampo(txtPercComisVenda, 640, 20, 57, 20, "PercComisVenda","% comis.", ListaCampos.DB_SI, true);
			adic(new JLabelPad("Vlr. comis."), 7, 40, 100, 20);
			adic(txtVlrComisVenda, 7, 60, 100, 20);
			adic(new JLabelPad("% M. comis."), 110, 40, 100, 20);
			adic(txtMedComisVenda, 110, 60, 80, 20);
			adic(btAltComis, 200, 50, 30, 30);
		} else {
			adicCampo(txtPercComisVenda, 290, 20, 57, 20, "PercComisVenda","% comis.", ListaCampos.DB_SI, true);
		}
		adicCampoInvisivel(txtStatusVenda, "StatusVenda", "Sit.",ListaCampos.DB_SI, false);
		setPainel(pinCabFiscal);
		adicCampo(txtVlrIPIVenda, 7, 20, 80, 20, "VlrIPIVenda", "Vlr. IPI",ListaCampos.DB_SI, false);
		adicCampo(txtVlrICMSVenda, 7, 60, 80, 20, "VlrICMSVenda", "Vlr. ICMS",ListaCampos.DB_SI, false);
		adicCampo(txtVlrPisVenda, 90, 20, 77, 20, "VlrPisVenda", "Vlr. PIS",ListaCampos.DB_SI, false);
		adicCampo(txtVlrCofinsVenda, 90, 60, 77, 20, "VlrCofinsVenda","Vlr. PIS", ListaCampos.DB_SI, false);
		adicCampo(txtVlrIRVenda, 170, 20, 80, 20, "VlrIRVenda", "Vlr. I.R.",ListaCampos.DB_SI, false);
		adicCampo(txtVlrCSocialVenda, 170, 60, 80, 20, "VlrCSocialVenda","Vlr. c. social", ListaCampos.DB_SI, false);
		lcCampos.setWhereAdic("TIPOVENDA='V'");
		setListaCampos(true, "VENDA", "VD");

		setAltDet(100);
		pinDet = new JPanelPad(740, 100);
		setPainel(pinDet, pnDet);
		setListaCampos(lcDet);
		setNavegador(navRod);
		adicCampo(txtCodItVenda, 7, 20, 30, 20, "CodItVenda", "Item",ListaCampos.DB_PK, true);

		if (bPrefs[6])
			txtCodNat.setAtivo(true);
		else
			txtCodNat.setAtivo(false);

		if (bPrefs[0]) {
			                        
			txtRefProd.setBuscaAdic(new DLBuscaProd(con, "REFPROD",lcProd2.getWhereAdic()));
			adicCampoInvisivel(txtCodProd, "CodProd", "Cód.prod.",ListaCampos.DB_FK, txtDescProd, false);
			adicCampoInvisivel(txtRefProd, "RefProd", "Ref.prod.", ListaCampos.DB_SI, false);
			adic(new JLabelPad("Ref. prod."), 40, 0, 60, 20);
			adic(txtRefProd, 40, 20, 60, 20);
			txtRefProd.setFK(true);
		} else {
			txtCodProd.setBuscaAdic(new DLBuscaProd(con, "CODPROD",lcProd.getWhereAdic()));
			adicCampo(txtCodProd, 40, 20, 60, 20, "CodProd", "Cód.prod.",ListaCampos.DB_FK, txtDescProd, true);
		}
		adicDescFK(txtDescProd, 103, 20, 200, 20, "DescProd","Descrição do produto");
		adicCampo(txtCodLote, 306, 20, 67, 20, "CodLote", "Lote",ListaCampos.DB_FK, txtDescLote, false);
		adicCampo(txtQtdItVenda, 376, 20, 67, 20, "QtdItVenda", "Qtd.",ListaCampos.DB_SI, true);
		
		txtQtdItVenda.setBuscaAdic(new DLBuscaEstoq(lcDet, lcAlmox,lcProd,con,"qtditvenda"));
				
		adicCampo(txtPrecoItVenda, 446, 20, 67, 20, "PrecoItVenda", "Preço",ListaCampos.DB_SI, true);
		adicCampo(txtPercDescItVenda, 516, 20, 47, 20, "PercDescItVenda","% desc.", ListaCampos.DB_SI, false);
		adicCampo(txtVlrDescItVenda, 566, 20, 67, 20, "VlrDescItVenda","V. desc.", ListaCampos.DB_SI, false);
		adicCampo(txtPercComItVenda, 636, 20, 45, 20, "PercComisItVenda","% com.", ListaCampos.DB_SI, false);
		adicCampo(txtVlrComisItVenda, 684, 20, 50, 20, "VlrComisItVenda","V. com.", ListaCampos.DB_SI, false);

		adicCampo(txtCodNat, 7, 60, 50, 20, "CodNat", "CFOP",ListaCampos.DB_FK, txtDescNat, true);

		adicDescFK(txtDescNat, 60, 60, 167, 20, "DescNat", "Descrição da CFOP");
		//txtCodAlmoxItVenda.setSoLeitura(true);
		txtCodAlmoxItVenda.setAtivo(false);
		adicCampo(txtCodAlmoxItVenda, 230, 60, 47,20, "codalmox", "Cod.ax",	ListaCampos.DB_FK, false);
		// colocar aqui o campo de saldo
		adicDescFK(txtSldLiqProd, 280, 60, 67, 20, "SldLiqProd", "Saldo");
		adicCampo(txtBaseICMSItVenda, 350, 60, 67, 20, "VlrBaseICMSItVenda","B. ICMS", ListaCampos.DB_SI, false);
		adicCampo(txtPercICMSItVenda, 420, 60, 57, 20, "PercICMSItVenda","% ICMS", ListaCampos.DB_SI, true);
		adicCampo(txtVlrICMSItVenda, 480, 60, 67, 20, "VlrICMSItVenda","V. ICMS", ListaCampos.DB_SI, false);
		adicCampoInvisivel(txtBaseIPIItVenda, "VlrBaseIPIItVenda", "B. IPI",ListaCampos.DB_SI, false);
		adicCampo(txtAliqIPIItVenda, 550, 60, 47, 20, "PercIPIItVenda", "% IPI",ListaCampos.DB_SI, false);
		
		if(bPrefs[12]){
			txtAliqIPIItVenda.setAtivo(true);
			txtVlrIPIItVenda.setAtivo(true);
		}
		else{
			txtAliqIPIItVenda.setAtivo(false);
			txtVlrIPIItVenda.setAtivo(false);
		}
		
		adicCampo(txtVlrIPIItVenda, 600, 60, 67, 20, "VlrIPIItVenda", "V. IPI",ListaCampos.DB_SI, false);
		adicCampoInvisivel(txtVlrProdItVenda, "VlrProdItVenda", "V. bruto",ListaCampos.DB_SI, false);
		adicCampoInvisivel(txtStrDescItVenda, "StrDescItVenda", "Descontos",ListaCampos.DB_SI, false);
		adicDBLiv(txaObsItVenda, "ObsItVenda", "Observação", false);
		adicCampoInvisivel(txtOrigFisc, "OrigFisc", "Origem",ListaCampos.DB_SI, false);
		adicCampoInvisivel(txtCodTratTrib, "CodTratTrib", "Cód.tr.trib.",ListaCampos.DB_FK, false);
		adicCampoInvisivel(txtTipoFisc, "TipoFisc", "Tipo fisc.",ListaCampos.DB_SI, false);
		adicCampoInvisivel(txtCodMens, "CodMens", "Cód.mens.",ListaCampos.DB_SI, false);
		adicCampo(txtVlrLiqItVenda, 670, 60, 65, 20, "VlrLiqItVenda","Vlr.item", ListaCampos.DB_SI, false);
		adicCampoInvisivel(txtCodEmpLG, "CodEmpLG", "Emp.log.",ListaCampos.DB_SI, false);
		adicCampoInvisivel(txtCodFilialLG, "CodFilialLG", "Filial log.",ListaCampos.DB_SI, false);
		adicCampoInvisivel(txtCodLog, "CodLog", "Cód.log.", ListaCampos.DB_SI,false);
		pinTot.adic(new JLabelPad("Vlr.prod."), 7, 0, 90, 20);
		pinTot.adic(txtVlrProdVenda, 7, 20, 90, 20);
		pinTot.adic(new JLabelPad("Vlr.desc."), 7, 40, 90, 20);
		pinTot.adic(txtVlrDescVenda, 7, 60, 90, 20);
		pinTot.adic(new JLabelPad("Vlr.liq."), 7, 80, 90, 20);
		pinTot.adic(txtVlrLiqVenda, 7, 100, 90, 20);
		txtCodNat.setStrMascara("#.###");
		lcDet.setWhereAdic("TIPOVENDA='V'");
		setListaCampos(true, "ITVENDA", "VD");

		int iIniRef = 3;
		if (bPrefs[0])
			iIniRef = 4;

		montaTab();

		tab.setTamColuna(30, 0);
		tab.setTamColuna(70, 1);
		tab.setTamColuna(230, 2);
		tab.setTamColuna(70, iIniRef);
		tab.setTamColuna(80, iIniRef + 1);
		tab.setTamColuna(70, iIniRef + 3);
		tab.setTamColuna(70, iIniRef + 4);
		tab.setTamColuna(60, iIniRef + 5);
		tab.setTamColuna(70, iIniRef + 6);
		tab.setTamColuna(60, iIniRef + 7);
		tab.setTamColuna(70, iIniRef + 8);
		tab.setTamColuna(200, iIniRef + 9);
		tab.setTamColuna(70, iIniRef + 10);
		tab.setTamColuna(60, iIniRef + 11);
		tab.setTamColuna(70, iIniRef + 12);
		tab.setTamColuna(70, iIniRef + 13);
		tab.setTamColuna(60, iIniRef + 14);
		tab.setTamColuna(70, iIniRef + 15);
		tab.setTamColuna(80, iIniRef + 16);
		tab.setTamColuna(90, iIniRef + 17);
		tab.setAutoRol(true);
	}

	private void buscaICMS() {
		if (txtAliqFisc.floatValue() > 0) {
			txtPercICMSItVenda.setVlrBigDecimal(txtAliqFisc.getVlrBigDecimal());
			return; //Ele cai fora porque se existe um valor no CLFISCAL ele
					// nem busca a Aliq. por Natureza da operaçao.
		}
		String sSQL = "SELECT PERCICMS FROM LFBUSCAICMSSP(?,?,?,?)";
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement(sSQL);
			ps.setString(1, txtCodNat.getVlrString());
			ps.setString(2, txtEstCli.getVlrString());
			ps.setInt(3, Aplicativo.iCodEmp);
			ps.setInt(4, Aplicativo.iCodFilialMz);
			rs = ps.executeQuery();
			if (rs.next()) {
				txtPercICMSItVenda.setVlrBigDecimal(new BigDecimal(rs.getString(1)));
			}
			calcImpostos(true);
		} catch (SQLException err) {
			Funcoes.mensagemErro(this, "Erro ao buscar percentual de ICMS!\n"
					+ err.getMessage(),true,con,err);
		} finally {
			ps = null;
			rs = null;
			sSQL = null;
		}
	}

	/**
	 * Busca de lote. Busca do lote mais proximo da data de venda.
	 *  
	 */
	private void buscaLote() {
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sSQL = null;
		try {
			sSQL = "SELECT MIN(L.CODLOTE) FROM EQLOTE L WHERE "
				+ "L.CODPROD=? AND L.CODFILIAL=? "+(bPrefs[13]?"AND L.SLDLIQLOTE>0 ":" ")
				+ "AND L.CODEMP=? AND L.VENCTOLOTE = "
				+ "( "
				+ "SELECT MIN(VENCTOLOTE) FROM EQLOTE LS WHERE LS.CODPROD=L.CODPROD "
				+ "AND LS.CODFILIAL=L.CODFILIAL AND LS.CODEMP=L.CODEMP "+(bPrefs[13]?"AND LS.SLDLIQLOTE>0 ":" ")
				+ "AND VENCTOLOTE >= CAST('today' AS DATE)" + ")";
			ps = con.prepareStatement(sSQL);
			ps.setInt(1, txtCodProd.getVlrInteger().intValue());
			ps.setInt(2, lcProd.getCodFilial());
			ps.setInt(3, Aplicativo.iCodEmp);
			rs = ps.executeQuery();
			if (rs.next()) {
				String sCodLote = rs.getString(1);
				if (sCodLote != null) {
					txtCodLote.setVlrString(sCodLote.trim());
					lcLote.carregaDados();
				}
			}
			rs.close();
			ps.close();
		} catch (SQLException err) {
			Funcoes.mensagemErro(this, "Erro ao buscar lote!\n" + err);
		} finally {
			ps = null;
			rs = null;
			sSQL = null;
		}
	}

	/**
	 * Busca da Natureza de Operação . Busca a natureza de operação através da
	 * tabela de regras fiscais.
	 *  
	 */
	private void buscaNat() {
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sSQL = "SELECT CODNAT FROM LFBUSCANATSP (?,?,?,?,?,?,?,?,?,?,?,?)";
		try {
			ps = con.prepareStatement(sSQL);
			ps.setInt(1, Aplicativo.iCodFilial);
			ps.setInt(2, Aplicativo.iCodEmp);
			ps.setInt(3, lcProd.getCodFilial());
			ps.setInt(4, txtCodProd.getVlrInteger().intValue());
			ps.setInt(5, Aplicativo.iCodEmp);
			ps.setInt(6, lcCli.getCodFilial());
			ps.setInt(7, txtCodCli.getVlrInteger().intValue());
			ps.setNull(8, Types.INTEGER);
			ps.setNull(9, Types.INTEGER);
			ps.setNull(10, Types.INTEGER);
			ps.setInt(11, lcTipoMov.getCodFilial());
			ps.setInt(12, txtCodTipoMov.getVlrInteger().intValue());
			rs = ps.executeQuery();
			if (rs.next()) {
				txtCodNat.setVlrString(rs.getString("CODNAT"));
				lcNat.carregaDados();
			}
			rs.close();
			ps.close();
		} catch (SQLException err) {
			Funcoes.mensagemErro(this, "Erro ao buscar natureza da operação!\n"
					+ err.getMessage(),true,con,err);
		} finally {
			ps = null;
			rs = null;
			sSQL = null;
		}
	}

	private void buscaTratTrib() {
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sSQL = "SELECT ORIGFISC,CODTRATTRIB,REDFISC,TIPOFISC,CODMENS,ALIQFISC,ALIQIPIFISC"
				+ " FROM LFBUSCAFISCALSP(?,?,?,?,?,?,?)";
		try {
			ps = con.prepareStatement(sSQL);
			ps.setInt(1, Aplicativo.iCodFilial);
			ps.setInt(2, Aplicativo.iCodEmp);
			ps.setInt(3, lcProd.getCodFilial());
			ps.setInt(4, txtCodProd.getVlrInteger().intValue());
			ps.setInt(5, Aplicativo.iCodEmp);
			ps.setInt(6, lcCli.getCodFilial());
			ps.setInt(7, txtCodCli.getVlrInteger().intValue());
			rs = ps.executeQuery();
			if (rs.next()) {
				txtOrigFisc.setVlrString(rs.getString("ORIGFISC"));
				txtCodTratTrib.setVlrString(rs.getString("CODTRATTRIB"));
				txtRedFisc.setVlrBigDecimal(new BigDecimal(rs.getString("REDFISC") != null ? rs.getString("REDFISC"): "0"));
				txtTipoFisc.setVlrString(rs.getString("TIPOFISC"));
				txtCodMens.setVlrString(rs.getString("CODMENS"));
				txtAliqFisc.setVlrString(rs.getString("ALIQFISC"));
				txtAliqIPIFisc.setVlrBigDecimal(new BigDecimal(rs.getString("ALIQIPIFISC") != null ? rs.getString("ALIQIPIFISC"): "0"));
			}
			rs.close();
			ps.close();
		} catch (SQLException err) {
			Funcoes.mensagemErro(this,
					"Erro ao buscar tratamento tributário!\n" + err.getMessage(),true,con,err);
		} finally {
			ps = null;
			rs = null;
			sSQL = null;
		}
	}
	
	private void calcDescIt() {
		if(txtPercDescItVenda.floatValue()!=0) {
			txtVlrDescItVenda.setVlrBigDecimal(new BigDecimal(
					Funcoes.arredFloat(txtVlrProdItVenda.floatValue()
							* txtPercDescItVenda.floatValue() / 100,casasDecFin)));
		} 
	}
	
	private void calcComisIt() {
		if(txtPercComItVenda.floatValue()!=0) {
			txtVlrComisItVenda.setVlrBigDecimal(new BigDecimal(Funcoes.arredFloat(
					(txtVlrProdItVenda.floatValue() - txtVlrDescItVenda.floatValue())
						* txtPercComItVenda.floatValue()/ 100
						* txtPercComisVenda.floatValue()/ 100, casasDecFin)));
		}
	}

	private void calcImpostos(boolean bBuscaBase) {
		float fRed = 0;
		float fVlrProd = 0;
		float fBaseIPI = 0;
		float fBaseICMS = 0;
		float fICMS = 0;
		float fIPI = 0;
		try {
			fRed = txtRedFisc.getVlrBigDecimal() != null ? txtRedFisc.floatValue() : 0;
			fVlrProd = calcVlrTotalProd(txtVlrProdItVenda.getVlrBigDecimal(),txtVlrDescItVenda.getVlrBigDecimal()).floatValue();

			if (bBuscaBase)
				fBaseICMS = 0;
			else
				fBaseICMS = txtBaseICMSItVenda.floatValue();

			//	  System.out.println("Red: "+bRed);
			//	  System.out.println("VlrProd: "+bVlrProd);
			txtPercICMSItVenda.setAtivo(true);
			txtVlrICMSItVenda.setAtivo(true);
			txtBaseICMSItVenda.setAtivo(true);
			if (txtTipoFisc.getText().trim().equals("II")) {
				txtPercICMSItVenda.setVlrBigDecimal(new BigDecimal("0"));
				txtVlrICMSItVenda.setVlrBigDecimal(new BigDecimal("0"));
				if (bBuscaBase)
					txtBaseICMSItVenda.setVlrBigDecimal(new BigDecimal("0"));
				txtPercICMSItVenda.setAtivo(false);
				txtVlrICMSItVenda.setAtivo(false);
				txtBaseICMSItVenda.setAtivo(false);
				if (txtCodNat.getAtivo())
					txtUltCamp = txtCodNat;
				else if (txtPercComItVenda.getAtivo())
					txtUltCamp = txtPercComItVenda;
				else
					txtUltCamp = txtVlrComisItVenda;
			} else if (txtTipoFisc.getText().trim().equals("FF")) {
				txtPercICMSItVenda.setVlrBigDecimal(new BigDecimal("0"));
				txtVlrICMSItVenda.setVlrBigDecimal(new BigDecimal("0"));
				if (bBuscaBase)
					txtBaseICMSItVenda.setVlrBigDecimal(new BigDecimal("0"));
				txtPercICMSItVenda.setAtivo(false);
				txtVlrICMSItVenda.setAtivo(false);
				txtBaseICMSItVenda.setAtivo(false);
				txtUltCamp = txtCodNat;
			} else if (txtTipoFisc.getText().trim().equals("NN")) {
				txtPercICMSItVenda.setVlrBigDecimal(new BigDecimal("0"));
				txtVlrICMSItVenda.setVlrBigDecimal(new BigDecimal("0"));
				if (bBuscaBase)
					txtBaseICMSItVenda.setVlrBigDecimal(new BigDecimal("0"));
				txtPercICMSItVenda.setAtivo(false);
				txtVlrICMSItVenda.setAtivo(false);
				txtBaseICMSItVenda.setAtivo(false);
				txtUltCamp = txtCodNat;
			} else if (txtTipoFisc.getText().trim().equals("TT")) {
				if (fVlrProd > 0) {
					if (bBuscaBase)
						fBaseICMS = Funcoes.arredFloat(fVlrProd - fVlrProd * fRed / 100, casasDecFin);
					fBaseIPI = fVlrProd;
					fICMS = Funcoes.arredFloat(fBaseICMS * txtPercICMSItVenda.floatValue() / 100, casasDecFin);
					fIPI = Funcoes.arredFloat(fBaseIPI * txtAliqIPIItVenda.floatValue() / 100, casasDecFin);
				}
				txtVlrICMSItVenda.setVlrBigDecimal(new BigDecimal(fICMS));
				txtBaseICMSItVenda.setVlrBigDecimal(new BigDecimal(fBaseICMS));
				txtVlrLiqItVenda.setVlrBigDecimal(new BigDecimal(fVlrProd));
				txtVlrIPIItVenda.setVlrBigDecimal(new BigDecimal(fIPI));
				txtBaseIPIItVenda.setVlrBigDecimal(new BigDecimal(fBaseIPI));
				txtAliqIPIItVenda.setVlrBigDecimal(txtAliqIPIFisc.getVlrBigDecimal());
				txtUltCamp = txtVlrICMSItVenda;
			}
			txtVlrLiqItVenda.setVlrBigDecimal(new BigDecimal(fVlrProd));
		} finally {
			fRed = 0;
			fVlrProd = 0;
			fBaseIPI = 0;
			fBaseICMS = 0;
			fICMS = 0;
			fIPI = 0;
		}
	}

	private void calcVlrProd() {
		txtVlrProdItVenda.setVlrBigDecimal(
				calcVlrProd(txtPrecoItVenda.getVlrBigDecimal(),txtQtdItVenda.getVlrBigDecimal()));
	}
	
	public void setLog(String[] args) {
		if(args != null) {
			txtCodEmpLG.setVlrString(args[0]);
			txtCodFilialLG.setVlrString(args[1]);
			txtCodLog.setVlrString(args[2]);
		}
	}
	
	public Vector getParansDesconto(){
		Vector param = new Vector();
		param.addElement(txtStrDescItVenda);
		param.addElement(txtPrecoItVenda);
		param.addElement(txtVlrDescItVenda);
		param.addElement(txtQtdItVenda);
		return param;
	}
	
	public String[] getParansPass() {
		return new String[] {"venda",
				txtCodVenda.getVlrInteger().intValue()+"",
				txtCodItVenda.getVlrInteger().intValue()+"",
				txtCodProd.getVlrInteger().intValue()+"",
				txtVlrProdItVenda.getVlrInteger().intValue()+""};
	}

	private void mostraTelaDescont() {
		if ((lcDet.getStatus() == ListaCampos.LCS_INSERT) || (lcDet.getStatus() == ListaCampos.LCS_EDIT)) {
			txtVlrDescItVenda.setAtivo(true);
			txtVlrDescItVenda.setVlrString("");
			txtPercDescItVenda.setAtivo(false);
			txtPercDescItVenda.setVlrString("");
			calcVlrProd();
			calcImpostos(true);
			mostraTelaDesconto();
			calcVlrProd();
			calcImpostos(true);
			txtVlrDescItVenda.requestFocus(true);
		}
	}

	public int[] getParansPreco() {
		int[] iRetorno = { txtCodProd.getVlrInteger().intValue(),
				txtCodCli.getVlrInteger().intValue(), 
				Aplicativo.iCodEmp,
				ListaCampos.getMasterFilial("VDCLIENTE"),
				txtCodPlanoPag.getVlrInteger().intValue(), 
				Aplicativo.iCodEmp,
				ListaCampos.getMasterFilial("FNPLANOPAG"),
				txtCodTipoMov.getVlrInteger().intValue(), 
				Aplicativo.iCodEmp,
				ListaCampos.getMasterFilial("EQTIPOMOV"), 
				Aplicativo.iCodEmp,
				Aplicativo.iCodFilial,
				txtCodVenda.getVlrInteger().intValue(),
				Aplicativo.iCodEmp,
				ListaCampos.getMasterFilial("VDVENDA") };
		return iRetorno;
	}

	public void setParansPreco(BigDecimal bdPreco) {
		txtPrecoItVenda.setVlrBigDecimal(bdPreco);
	}
	
	private String getLayoutPedido() {
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sSQL = null;		
		String retorno = null;
		
		try {
			
			sSQL = "SELECT P.CLASSNOTAPAPEL "
				 + "FROM SGPAPEL P, SGIMPRESSORA I, SGESTACAOIMP EI, SGESTACAO E "
				 + "WHERE P.CODPAPEL=I.CODPAPEL AND P.CODEMP=I.CODEMPPL AND P.CODFILIAL=I.CODFILIALPL "
				 + "AND I.CODIMP=EI.CODIMP AND I.CODEMP=EI.CODEMPIP AND I.CODFILIAL=EI.CODFILIALIP AND EI.TIPOUSOIMP='PD' "
				 + "AND EI.CODEST=E.CODEST AND EI.CODEMP=E.CODEMP AND EI.CODFILIAL=E.CODFILIAL "
				 + "AND E.CODEMP=? AND E.CODFILIAL=? AND E.CODEST=?";
			ps = con.prepareStatement(sSQL);
			ps.setInt(1, Aplicativo.iCodEmp);
			ps.setInt(2, ListaCampos.getMasterFilial("SGPAPEL"));
			ps.setInt(3, Aplicativo.iNumEst);
			rs = ps.executeQuery();
			
			if(rs.next()) {
				retorno = rs.getString(1).trim();
			}
			
			if(!con.getAutoCommit())
				con.commit();
			
		} catch (SQLException e) {
			Funcoes.mensagemErro(this,"Erro ao buscar layout de pedido.\n"+e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ps = null;
			rs = null;
			sSQL = null;
		}
		
		return retorno;
	}

	private boolean testaPgto() {
		boolean bRetorno = true;
		String sSQL = "SELECT * FROM FNCHECAPGTOSP(?,?,?)";
		try {
			PreparedStatement ps = con.prepareStatement(sSQL);
			ps.setInt(1, txtCodCli.getVlrInteger().intValue());
			ps.setInt(2, Aplicativo.iCodEmp);
			ps.setInt(3, Aplicativo.iCodFilial);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				if (rs.getString(1).trim().equals("N")) {
					bRetorno = false;
				}
			} 
			else {
				Funcoes.mensagemErro(this,"Não foi possível checar os pagamentos do cliente!");
			}
			//      rs.close();
			//      ps.close();
			if (!con.getAutoCommit())
				con.commit();
		} catch (SQLException err) {
			Funcoes.mensagemErro(this,
					"Não foi possível verificar os pagamentos do cliente!\n"
							+ err.getMessage(),true,con,err);
		}
		return bRetorno;
	}

	private boolean testaLucro() {
		return super.testaLucro( new Object[] {
				txtCodProd.getVlrInteger(),
				txtCodAlmoxItVenda.getVlrInteger(),
				txtPrecoItVenda.getVlrBigDecimal(),
				});
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
			bRetorno = txtCodLote.mostraDLF2FK();
		} else {
			bRetorno = true;
		}
		return bRetorno;
	}

	private void bloqvenda() {
		PreparedStatement ps = null;
		String sSql = null;
		String sTipoVenda = null;
		int iCodVenda = 0;
		try {
			iCodVenda = txtCodVenda.getVlrInteger().intValue();
			if (iCodVenda != 0) {
				sTipoVenda = "V";
				sSql = "EXECUTE PROCEDURE VDBLOQVENDASP(?,?,?,?,?)";
				ps = con.prepareStatement(sSql);
				ps.setInt(1, Aplicativo.iCodEmp);
				ps.setInt(2, ListaCampos.getMasterFilial("VDVENDA"));
				ps.setString(3, sTipoVenda);
				ps.setInt(4, iCodVenda);
				ps.setString(5, "S");
				ps.executeUpdate();
				ps.close();
				if (!con.getAutoCommit())
					con.commit();
			}
		} catch (SQLException err) {
			Funcoes.mensagemErro(this, "Erro bloqueando a venda!\n"
					+ err.getMessage(),true,con,err);
		} finally {
			ps = null;
			sSql = null;
		}
	}
	
	private boolean verificaBloq() {
		boolean retorno = false;
		ResultSet rs = null;
		PreparedStatement ps = null;
		String sSql = null;
		String sTipoVenda = null;
		int iCodVenda = 0;
		try {
			iCodVenda = txtCodVenda.getVlrInteger().intValue();
			if (iCodVenda != 0) {
				sTipoVenda = "V";
				sSql = "SELECT BLOQVENDA FROM VDVENDA WHERE CODEMP=? AND CODFILIAL=? AND CODVENDA=? AND TIPOVENDA=?";
				ps = con.prepareStatement(sSql);
				ps.setInt(1, Aplicativo.iCodEmp);
				ps.setInt(2, ListaCampos.getMasterFilial("VDVENDA"));
				ps.setInt(3, iCodVenda);
				ps.setString(4, sTipoVenda);
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
			Funcoes.mensagemErro(this, "Erro bloqueando a venda!\n"
					+ err.getMessage(),true,con,err);
		} finally {
			rs = null;
			ps = null;
			sSql = null;
		}
		return retorno;
	}

	private void abreAdicOrc() {
		if (!Aplicativo.telaPrincipal.temTela("Busca orçamento")) {
			FAdicOrc tela = new FAdicOrc(this,"V");
			Aplicativo.telaPrincipal.criatela("Orcamento", tela, con);
		}
	}

	private void altComisVend() {
		if (lcCampos.getStatus() != ListaCampos.LCS_SELECT) {
			return;
		}
		DLAltComisVend dl = new DLAltComisVend(this, txtCodVenda
				.getVlrInteger().intValue(), txtMedComisVenda
				.getVlrBigDecimal(), con);
		dl.setVisible(true);
		dl.dispose();
		lcCampos.carregaDados();
	}

	private void imprimir(boolean bVisualizar, int iCodVenda) {
		PreparedStatement ps = null;
		PreparedStatement psRec = null;
		PreparedStatement psInfoAdic = null;
		ResultSet rs = null;
		ResultSet rsRec = null;
		ResultSet rsInfoAdic = null;
		String sSQL = null;
		String sSQLRec = null;
		String sSQLInfoAdic = null;
		String sDiasPE = null;
		ImprimeOS imp = new ImprimeOS("", con, "PD", true);
		DLRPedido dl = null; 
		GregorianCalendar cal = null;
		Date dtHoje = null;
		Vector vDesc = null;
		Vector vObs = null;
		Object layNF = null;
		boolean bImpOK = false;	
		int linPag = imp.verifLinPag("PD") - 1;
		int iDiasPE = 0;
				
		try {
			
			dl = new DLRPedido(sOrdNota, false);
			dl.setVisible(true);
			
			if (dl.OK == false) {
				dl.dispose();
				return;
			}
			
			sSQL = "SELECT (SELECT COUNT(IC.CODITVENDA) FROM VDITVENDA IC WHERE IC.CODVENDA=V.CODVENDA AND IC.CODEMP=V.CODEMP AND IC.CODFILIAL=V.CODFILIAL AND IC.TIPOVENDA=V.TIPOVENDA),"
					+ "(SELECT L.CODLOTE FROM EQLOTE L WHERE L.CODPROD=I.CODPROD AND L.CODLOTE=I.CODLOTE),"
					+ "(SELECT L.VENCTOLOTE FROM EQLOTE L WHERE L.CODPROD=I.CODPROD AND L.CODLOTE=I.CODLOTE),"
					+ "V.CODVENDA,V.CODCLI,C.RAZCLI,C.CNPJCLI,C.CPFCLI,V.DTEMITVENDA,C.ENDCLI,C.NUMCLI,C.COMPLCLI,"
					+ "C.BAIRCLI,C.CEPCLI,V.OBSVENDA,V.DTSAIDAVENDA,C.CIDCLI,C.UFCLI,C.FONECLI,C.FONECLI,"
					+ "C.FAXCLI,C.INSCCLI,C.RGCLI,I.CODPROD,P.REFPROD,P.CODBARPROD,P.DESCPROD,P.CODUNID,I.PERCISSITVENDA,"
					+ "I.QTDITVENDA,I.PRECOITVENDA,I.VLRPRODITVENDA,I.CODNAT,I.PERCICMSITVENDA,"
					+ "I.PERCIPIITVENDA,VLRIPIITVENDA,V.VLRBASEICMSVENDA,V.VLRICMSVENDA,V.VLRPRODVENDA,"
					+ "V.VLRFRETEVENDA,V.VLRDESCVENDA,V.VLRDESCITVENDA,V.VLRADICVENDA,V.VLRIPIVENDA,"
					+ "V.VLRLIQVENDA,V.CODVEND,VEND.NOMEVEND,V.CODPLANOPAG,PG.DESCPLANOPAG,"
					+ "(SELECT T.RAZTRAN FROM VDTRANSP T, VDFRETEVD F WHERE T.CODEMP=F.CODEMPTN AND "
					+ "T.CODFILIAL=F.CODFILIALTN AND T.CODTRAN=F.CODTRAN AND F.CODEMP=V.CODEMP AND " 
					+ "F.CODFILIAL=V.CODFILIAL AND F.TIPOVENDA=V.TIPOVENDA AND F.CODVENDA=V.CODVENDA),"
					+ "(SELECT F.TIPOFRETEVD FROM VDFRETEVD F WHERE F.CODEMP=V.CODEMP AND " 
					+ "F.CODFILIAL=V.CODFILIAL AND F.TIPOVENDA=V.TIPOVENDA AND F.CODVENDA=V.CODVENDA),"
					+ "I.VLRLIQITVENDA,P.DESCAUXPROD,C.DDDCLI,C.EMAILCLI,I.CODITVENDA,"
					+ "(SELECT PE.DIASPE FROM VDPRAZOENT PE WHERE PE.CODEMP=P.CODEMPPE AND PE.CODFILIAL=P.CODFILIALPE AND PE.CODPE=P.CODPE),"
					+ "C.SITECLI,I.OBSITVENDA,VEND.EMAILVEND,"
					+ "(SELECT FN.DESCFUNC FROM RHFUNCAO FN WHERE FN.CODEMP=VEND.CODEMPFU AND "
					+ "FN.CODFILIAL=VEND.CODFILIALFU AND FN.CODFUNC=VEND.CODFUNC), "
					+ "V.PEDCLIVENDA,C.CONTCLI,P.CODFISC,FC.DESCFISC,V.DOCVENDA,C.OBSCLI "
					+ "FROM VDVENDA V,VDCLIENTE C,VDITVENDA I,EQPRODUTO P,VDVENDEDOR VEND,FNPLANOPAG PG,LFCLFISCAL FC "
					+ "WHERE V.CODEMP=? AND V.CODFILIAL=? AND V.TIPOVENDA='V' AND V.CODVENDA=? AND "
					+ "C.CODEMP=V.CODEMPCL AND C.CODFILIAL=V.CODFILIALCL AND C.CODCLI=V.CODCLI AND "
					+ "I.CODEMP=V.CODEMP AND I.CODFILIAL=V.CODFILIAL AND I.TIPOVENDA=V.TIPOVENDA AND "
					+ "I.CODVENDA=V.CODVENDA AND P.CODEMP=I.CODEMPPD AND P.CODFILIAL=I.CODFILIALPD AND "
					+ "P.CODPROD=I.CODPROD AND VEND.CODEMP=V.CODEMPVD AND VEND.CODFILIAL=V.CODFILIALVD AND " 
					+ "P.CODFISC=FC.CODFISC AND P.CODEMPFC=FC.CODEMP AND P.CODFILIALFC=FC.CODFILIAL AND "
					+ "VEND.CODVEND=V.CODVEND AND PG.CODEMP=V.CODEMPPG AND PG.CODFILIAL=V.CODFILIALPG AND "
					+ "PG.CODPLANOPAG=V.CODPLANOPAG "
					+ "ORDER BY P." + dl.getValor() + ",P.DESCPROD";

			sSQLRec = "SELECT I.DTVENCITREC,I.VLRPARCITREC,I.NPARCITREC FROM FNRECEBER R, FNITRECEBER I WHERE R.CODVENDA="
					+ iCodVenda + " AND I.CODREC=R.CODREC ORDER BY I.DTVENCITREC";
			
			sSQLInfoAdic = "SELECT CODAUXV,CPFCLIAUXV,NOMECLIAUXV,CIDCLIAUXV,UFCLIAUXV "
					+ "FROM VDAUXVENDA WHERE CODEMP=? AND CODFILIAL=? AND CODVENDA=?";
				

			dl.dispose();
			
			if(bPrefs[16]) {
				try {
					layNF = Class.forName(
							"org.freedom.layout." + getLayoutPedido()).newInstance();
				} catch (Exception err) {
					Funcoes.mensagemInforma(this,
							"Não foi possível carregar o layout de Nota Fiscal!\n"
									+ err.getMessage());
				}
				if (layNF != null) {
					if (layNF instanceof Leiaute) {
						
						ps = con.prepareStatement(sSQL);
						ps.setInt(1,Aplicativo.iCodEmp);
						ps.setInt(2,ListaCampos.getMasterFilial("VDVENDA"));
						ps.setInt(3,iCodVenda);
						rs = ps.executeQuery();
						
						psRec = con.prepareStatement(sSQLRec);
						rsRec = psRec.executeQuery();
						
						psInfoAdic = con.prepareStatement(sSQLInfoAdic);
						psInfoAdic.setInt(1, Aplicativo.iCodEmp);
						psInfoAdic.setInt(2, Aplicativo.iCodFilial);
						psInfoAdic.setInt(3, iCodVenda);
						rsInfoAdic = psInfoAdic.executeQuery();
						
						bImpOK = ((Leiaute) layNF).imprimir(rs, rsRec, rsInfoAdic, imp);		
						if (bImpOK) {
							if (bVisualizar) 
								imp.preview(this);
							else 
								imp.print();
						}
						imp.fechaPreview();
					}
				}
			}
			else {
				ps = con.prepareStatement(sSQL);
				ps.setInt(1,Aplicativo.iCodEmp);
				ps.setInt(2,ListaCampos.getMasterFilial("VDVENDA"));
				ps.setInt(3,iCodVenda);
				rs = ps.executeQuery();
				
				psRec = con.prepareStatement(sSQLRec);
				rsRec = psRec.executeQuery();
				
				psInfoAdic = con.prepareStatement(sSQLInfoAdic);
				psInfoAdic.setInt(1, Aplicativo.iCodEmp);
				psInfoAdic.setInt(2, Aplicativo.iCodFilial);
				psInfoAdic.setInt(3, iCodVenda);
				rsInfoAdic = psInfoAdic.executeQuery();
				
				imp.limpaPags();
				imp.montaCab();
				imp.setTitulo("Relatório de Pedidos");
				
				while (rs.next()) {
					
					vDesc = new Vector();
					if (bPrefs[9])
						vDesc = Funcoes.quebraLinha(Funcoes.stringToVector(rs.getString("ObsItVenda")==null?rs.getString("DescProd").trim():rs.getString("ObsItVenda").trim()),40);						
					else 
						vDesc = Funcoes.quebraLinha(Funcoes.stringToVector(rs.getString("DescProd").trim()),50);
					
					for (int i=0; i<vDesc.size(); i++) {
						if (imp.pRow() == 0) {
							imp.impCab(136, false);
							imp.say(imp.pRow() + 0, 0, "" + imp.comprimido());
							imp.say(imp.pRow() + 0, 1, "CLIENTE");
							imp.say(imp.pRow() + 0, 70, "PEDIDO N.: "+ rs.getString("CodVenda").trim());
							imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
							imp.say(imp.pRow() + 0, 1, (rs.getString("RazCli")!=null ? rs.getString("RazCli").trim() : "") + " - " + rs.getString("CodCli").trim());//nome cliente
							imp.say(imp.pRow() + 0, 70, "PEDIDO CLIENTE: "+(rs.getString("PEDCLIVENDA")==null?"":rs.getString("PEDCLIVENDA")));
							imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
							imp.say(imp.pRow() + 0, 1, rs.getString("CpfCli") != null ? "CPF    : " + Funcoes
										.setMascara(rs.getString("CpfCli"),"###.###.###-##") : "CNPJ   : " + Funcoes
											.setMascara(rs.getString("CnpjCli"),"##.###.###/####-##"));//CNJP cliente
							imp.say(imp.pRow() + 0, 70, "CONTATO: "+ (rs.getString("ContCli")!=null ? rs.getString("ContCli").trim():""));
							imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
							imp.say(imp.pRow() + 0, 1, (rs.getString("RgCli") != null ? "R.G.   : " + rs.getString("RgCli") : "I.E.   : " + (rs.getString("InscCli")!=null ? rs.getString("InscCli"):"")));//IE cliente
							imp.say(imp.pRow() + 0, 70,(rs.getString("EndCli")!=null ? rs.getString("EndCli").trim()+ " N°:" + (rs.getString("NumCli")!=null ? rs.getString("NumCli"):""):""));//rua e número do cliente
							imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
							imp.say(imp.pRow() + 0, 1, "SITE   : " + (rs.getString("SiteCli")!=null?rs.getString("SiteCli").trim():""));
							imp.say(imp.pRow() + 0, 70,(rs.getString("BairCli")!=null?rs.getString("BairCli").trim():"")
									+(rs.getString("CidCli")!=null?" - "+ rs.getString("CidCli").trim():"")
									+(rs.getString("UFCli")!=null?" - "+rs.getString("UFCli").trim():"")
									+(rs.getString("CEPCli")!=null?" - "+rs.getString("CEPCli").trim():""));//complemento do endereço do cliente
							imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
							imp.say(imp.pRow() + 0, 1, "E-MAIl : " + (rs.getString("EmailCli")!=null?rs.getString("EmailCli").trim():""));
							imp.say(imp.pRow() + 0, 70, "TEL: "+ (rs.getString("DDDCli")!=null?Funcoes.setMascara(rs.getString("DDDCli"), "(####)"):"")+ 
												(rs.getString("FoneCli")!=null?Funcoes.setMascara(rs.getString("FoneCli").trim(), "####-####"):"")+ " - FAX:" +
													(rs.getString("FaxCli") != null ? Funcoes.setMascara(rs.getString("FaxCli"),"####-####") : ""));
							imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
							imp.say(imp.pRow() + 0,0,Funcoes.replicate("-", 135));
							imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
							imp.say(imp.pRow() + 0, 55, "DADO(S) DO(S) PRODUTO(S)");
							imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
							imp.say(imp.pRow() + 0,0,Funcoes.replicate("-", 135));
							imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
							imp.say(imp.pRow() + 0, 1, "IT.|  CÓDIGO  |                 DESCRIÇÃO               |     LOTE     |UN|   QUANT.   |    V.UNIT.  |    V.TOTAL    |  IPI%  |  ICMS% ");
						}
						imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
						if (i==0) {
							imp.say(imp.pRow() + 0, 1, rs.getString("CodItVenda").trim());
							imp.say(imp.pRow() + 0, 6, rs.getString("RefProd").trim());
						}
						imp.say(imp.pRow() + 0, 17,"" + vDesc.elementAt(i).toString());
						if (i==0) {
							imp.say(imp.pRow() + 0, 59, (rs.getString(2)!=null ? rs.getString(2).trim() : ""));
							imp.say(imp.pRow() + 0, 74, rs.getString("CodUnid").trim());
							imp.say(imp.pRow() + 0, 79, rs.getString("QtdItVenda"));
							imp.say(imp.pRow() + 0, 87, Funcoes.strDecimalToStrCurrency(13,2,""+(new BigDecimal(rs.getString("VlrLiqItVenda"))).divide(new BigDecimal(rs.getDouble("QtdItVenda")),2,
											BigDecimal.ROUND_HALF_UP)));
							imp.say(imp.pRow() + 0, 106, rs.getString("VlrLiqItVenda"));
							imp.say(imp.pRow() + 0, 122, rs.getString("PercIPIItVenda"));
							imp.say(imp.pRow() + 0, 130, rs.getString("PercICMSItVenda"));
						}
					}
					if(iDiasPE < rs.getInt(57))
						iDiasPE = rs.getInt(57);
				}
				imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
				imp.say(imp.pRow() + 0, 0, Funcoes.replicate("-", 135));
				imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
				imp.say(imp.pRow() + 0, 4, "TOTAL IPI: " + rs.getString("VlrIPIVenda"));
				imp.say(imp.pRow() + 0, 44, "|    TOTAL ICMS: " + rs.getString("VlrICMSVenda"));
				imp.say(imp.pRow() + 0, 84, "|    TOTAL PRODUTOS: " + rs.getString("VlrLiqVenda"));
				imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
				imp.say(imp.pRow() + 0, 0, Funcoes.replicate("-", 135));
				imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
				imp.say(imp.pRow() + 0, 55, "INFORMAÇÕES COMPLEMENTARES");
				imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
				imp.say(imp.pRow() + 0, 0, Funcoes.replicate("-", 135));
				imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
				imp.say(imp.pRow() + 0, 0, "PAGAMENTO.........:    " + rs.getString("CODPLANOPAG") + " - " + rs.getString("DESCPLANOPAG"));
				imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
				imp.say(imp.pRow() + 0, 0, "FRETE.............:    " + (rs.getString(51)!=null ? (rs.getString(51).equals("C") ? "POR CONTA DA EMPRESA " : "POR CONTA DO CLIENTE ") : ""));			
				imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
				imp.say(imp.pRow() + 0, 0, "TRANSPORTADORA....:    " + (rs.getString(50)!=null ? rs.getString(50) : ""));
				imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
							
				if(bPrefs[14]){
					dtHoje = new Date();
					cal = new GregorianCalendar();
					cal.setTime(dtHoje);
					if(iDiasPE > 0){
						cal.add(GregorianCalendar.DAY_OF_YEAR,iDiasPE);
						sDiasPE = Funcoes.dateToStrDate(cal.getTime());
					}
					else
						sDiasPE = "";

					imp.say(imp.pRow() + 0, 0, "DATA DE ENTREGA...:    " + sDiasPE);
				}
				else{
					sDiasPE = (iDiasPE > 0 ? iDiasPE + " dias" : "");
					imp.say(imp.pRow() + 0, 0, "PRAZO DE ENTREGA..:    " + sDiasPE );
				}
				
				imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
				imp.say(imp.pRow() + 0, 0, Funcoes.replicate("-", 135));
				imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
				imp.say(imp.pRow() + 0, 62, "OBSERVACÃO");
				imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
	          	vObs = Funcoes.quebraLinha(Funcoes.stringToVector(rs.getString("ObsVenda")),115);
	          	for (int i=0; i<vObs.size(); i++) {
	                imp.say(imp.pRow()+1,0,""+imp.comprimido());
	                imp.say(imp.pRow()+0,20,vObs.elementAt(i).toString());
	                if (imp.pRow()>=linPag) {
	                    imp.incPags();
	                    imp.eject();
	                }
	          	}
				imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
				imp.say(imp.pRow() + 0, 0,Funcoes.replicate("-", 135));
				imp.say(imp.pRow() + 2, 0, "" + imp.comprimido());
				imp.say(imp.pRow() + 0, 5,Funcoes.replicate("-", 40));
				imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
				imp.say(imp.pRow() + 0, 5, (rs.getString("NomeVend")!=null ? rs.getString("NomeVend") : ""));
				imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
				imp.say(imp.pRow() + 0, 5, (rs.getString(61)!=null ? rs.getString(61) : ""));
				imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
				imp.say(imp.pRow() + 0, 5, (rs.getString("EmailVend")!=null ? rs.getString("EmailVend") : ""));
				
				imp.eject();
				imp.fechaGravacao();

				if (bVisualizar) 
					imp.preview(this);
				else 
					imp.print();
			}			
			
			if (!con.getAutoCommit())
				con.commit();
			
		} catch (SQLException err) {
			Funcoes.mensagemErro(this, "Erro ao consultar a tabela de Venda!"
					+ err.getMessage(),true,con,err);
			err.printStackTrace();
		} catch (Exception err) {
			Funcoes.mensagemErro(this, "Erro ao montar impressão!");
			err.printStackTrace();
		}
	}

	private void emitNota(String sTipo) {
		PreparedStatement ps = null;
		PreparedStatement psRec = null;
		PreparedStatement psInfoAdic = null;
		ResultSet rs = null;
		ResultSet rsRec = null;
		ResultSet rsInfoAdic = null;
		Object layNF = null;
		String sSQL = null;
		String sSQLRec = null;
		String sSQLInfoAdic = null;
		Vector parans = null;
		NFSaida nf = null;
		boolean bImpOK = false;
		int iCodVenda = txtCodVenda.getVlrInteger().intValue();
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
			layNF = Class.forName(
					"org.freedom.layout." + imp.getClassNota()).newInstance();
		} catch (Exception err) {
			Funcoes.mensagemInforma(this,
					"Não foi possível carregar o layout de Nota Fiscal!\n"
							+ err.getMessage());
		}
		try {
			if (layNF != null) {
				if (layNF instanceof Layout) {
					parans = new Vector();
					parans.addElement(new Integer(Aplicativo.iCodEmp));
					parans.addElement(new Integer(ListaCampos.getMasterFilial("VDVENDA")));
					parans.addElement(new Integer(iCodVenda));
					nf = new NFSaida(casasDec);
					nf.carregaTabelas(con, parans);
					bImpOK = ((Layout) layNF).imprimir(nf, imp);
				}
				else if (layNF instanceof Leiaute) {
					sSQL = "SELECT (SELECT COUNT(IC.CODITVENDA) FROM VDITVENDA IC WHERE IC.CODVENDA=V.CODVENDA AND IC.CODEMP=V.CODEMP AND IC.CODFILIAL=V.CODFILIAL AND IC.TIPOVENDA=V.TIPOVENDA),"
						+ "(SELECT L.CODLOTE FROM EQLOTE L WHERE L.CODPROD=I.CODPROD AND L.CODLOTE=I.CODLOTE),"
						+ "(SELECT L.VENCTOLOTE FROM EQLOTE L WHERE L.CODPROD=I.CODPROD AND L.CODLOTE=I.CODLOTE),"
						+ "(SELECT M.MENS FROM LFMENSAGEM M WHERE M.CODMENS=I.CODMENS"
						+ " AND M.CODFILIAL=I.CODFILIALME AND M.CODEMP=I.CODEMPME),"
						+ "(SELECT M.MENS FROM LFMENSAGEM M WHERE M.CODMENS=CL.CODMENS"
						+ " AND M.CODFILIAL=CL.CODFILIALME AND M.CODEMP=CL.CODEMPME),"
						+ "(SELECT S.DESCSETOR FROM VDSETOR S WHERE S.CODSETOR=C.CODSETOR"
						+ " AND S.CODFILIAL=C.CODFILIALSR AND S.CODEMP=C.CODEMPSR),"
						+ "(SELECT B.NOMEBANCO FROM FNBANCO B WHERE B.CODEMP=V.CODEMPBO"
						+ " AND B.CODFILIAL=V.CODFILIALBO AND B.CODBANCO=V.CODBANCO),"
						+ "(SELECT P.SIGLAPAIS FROM SGPAIS P WHERE P.CODPAIS=C.CODPAIS),"
						+ "V.DOCVENDA,V.CODVENDA,V.CODCLI,C.RAZCLI,C.CNPJCLI,C.CPFCLI,V.DTEMITVENDA,C.ENDCLI,"
						+ "C.BAIRCLI,C.CEPCLI,V.OBSVENDA,V.DTSAIDAVENDA,C.CIDCLI,C.UFCLI,C.FONECLI,C.FONECLI,C.NUMCLI,C.COMPLCLI,"
						+ "C.FAXCLI,C.INSCCLI,C.RGCLI,I.CODPROD,P.REFPROD,P.CODBARPROD,P.DESCPROD, P.CODUNID,N.CODNAT,"
						+ "I.VLRLIQITVENDA,N.DESCNAT,I.QTDITVENDA,I.PRECOITVENDA,I.VLRPRODITVENDA,I.CODNAT,I.PERCICMSITVENDA,"
						+ "I.PERCISSITVENDA,PERCIPIITVENDA,VLRIPIITVENDA,V.VLRBASEICMSVENDA,V.VLRICMSVENDA,V.VLRPRODVENDA,"
						+ "V.VLRISSVENDA,V.VLRFRETEVENDA,V.VLRDESCVENDA,V.VLRDESCITVENDA,V.VLRADICVENDA,V.VLRIPIVENDA,"
						+ "V.VLRBASEISSVENDA,V.VLRLIQVENDA,V.CODVEND,VEND.NOMEVEND,V.CODPLANOPAG,PG.DESCPLANOPAG,F.CODTRAN,"
						+ "T.RAZTRAN,F.TIPOFRETEVD,F.PLACAFRETEVD,F.UFFRETEVD,T.TIPOTRAN,T.CNPJTRAN,T.ENDTRAN,T.NUMTRAN,T.CIDTRAN,"
						+ "T.UFTRAN,T.INSCTRAN,F.QTDFRETEVD,F.ESPFRETEVD,F.MARCAFRETEVD,F.PESOBRUTVD,"
						+ "F.PESOLIQVD, I.ORIGFISC, I.CODTRATTRIB, FL.CNPJFILIAl,FL.INSCFILIAL,FL.ENDFILIAL,"
						+ "FL.NUMFILIAL,FL.COMPLFILIAL,FL.BAIRFILIAL,FL.CEPFILIAL,FL.CIDFILIAL,FL.UFFILIAL,FL.FONEFILIAL,"
						+ "FL.FAXFILIAL,C.ENDCOB, C.NUMCOB, C.COMPLCOB,C.CEPCOB, C.CIDCOB, P.TIPOPROD, C.INCRACLI, V.CODBANCO,"
						+ "P.CODFISC, C.ENDENT, C.NUMENT, C.COMPLENT,C.CIDENT,C.UFENT,C.BAIRENT,C.NOMECLI,I.OBSITVENDA,"
						+ "V.VLRPISVENDA,V.VLRCOFINSVENDA,V.VLRIRVENDA,V.VLRCSOCIALVENDA,V.CODCLCOMIS,V.PERCCOMISVENDA,"
						+ "V.PERCMCOMISVENDA, N.IMPDTSAIDANAT,P.DESCAUXPROD, C.DDDCLI "
						+ " FROM VDVENDA V, VDCLIENTE C, VDITVENDA I, EQPRODUTO P, VDVENDEDOR VEND, FNPLANOPAG PG,"
						+ " VDFRETEVD F, VDTRANSP T, LFNATOPER N, SGFILIAL FL, LFCLFISCAL CL WHERE V.TIPOVENDA='V' AND V.CODVENDA="
						+ iCodVenda
						+ " AND V.CODEMP=?"
						+ " AND V.CODFILIAL=? AND FL.CODEMP=V.CODEMP AND FL.CODFILIAL=V.CODFILIAL" 
						+ " AND C.CODCLI=V.CODCLI AND C.CODEMP=V.CODEMPCL AND C.CODFILIAL=V.CODFILIALCL " 
						+ " AND I.CODVENDA=V.CODVENDA AND I.CODEMP=V.CODEMP AND I.CODFILIAL=V.CODFILIAL AND I.TIPOVENDA=V.TIPOVENDA "
						+ " AND P.CODPROD=I.CODPROD AND VEND.CODVEND=V.CODVEND AND PG.CODPLANOPAG=V.CODPLANOPAG AND F.CODVENDA=V.CODVENDA"
						+ " AND T.CODTRAN=F.CODTRAN AND N.CODNAT=I.CODNAT AND CL.CODFISC = P.CODFISC AND CL.CODFILIAL=P.CODFILIAL"
						+ " AND CL.CODEMP = P.CODEMP ORDER BY P."
						+ dl.getValor()
						+ ",P.DESCPROD";
					sSQLRec = "SELECT I.DTVENCITREC,I.VLRPARCITREC,I.NPARCITREC FROM FNRECEBER R, FNITRECEBER I WHERE R.CODVENDA="
						+ iCodVenda + " AND I.CODREC=R.CODREC ORDER BY I.DTVENCITREC";
					sSQLInfoAdic = "SELECT CODAUXV,CPFCLIAUXV,NOMECLIAUXV,CIDCLIAUXV,UFCLIAUXV "
						+ "FROM VDAUXVENDA WHERE CODEMP=? AND CODFILIAL=? AND CODVENDA=?";
					psRec = con.prepareStatement(sSQLRec);
					rsRec = psRec.executeQuery();
					psInfoAdic = con.prepareStatement(sSQLInfoAdic);
					psInfoAdic.setInt(1, Aplicativo.iCodEmp);
					psInfoAdic.setInt(2, Aplicativo.iCodFilial);
					psInfoAdic.setInt(3, txtCodVenda.getVlrInteger().intValue());
					rsInfoAdic = psInfoAdic.executeQuery();
					ps = con.prepareStatement(sSQL);
					ps.setInt(1, Aplicativo.iCodEmp);
					ps.setInt(2, ListaCampos.getMasterFilial("VDVENDA"));
					rs = ps.executeQuery();
					bImpOK = ((Leiaute) layNF).imprimir(rs, rsRec, rsInfoAdic, imp);
					if (!con.getAutoCommit())
						con.commit();
				}
			}
		} 
		catch (SQLException err) {
			Funcoes.mensagemErro(this, "Erro ao consultar tabela de Venda!"
					+ err.getMessage(),true,con,err);
		}
		dl.dispose();
		if (bImpOK)
			imp.preview(this);
		imp.fechaPreview();

		//    imp.print();
	}
	
/*	private void imprimir2(boolean bVisualizar, int iCodVenda) {
		ImprimeOS imp = new ImprimeOS("", con);
		int linPag = imp.verifLinPag() - 1;
		int iPares = 0;
		String sStrLote = "";
		imp.montaCab();
		imp.setTitulo("Relatório de Pedidos");
		DLRPedido dl = new DLRPedido(sOrdNota);
		dl.setVisible(true);
		if (dl.OK == false) {
			dl.dispose();
			return;
		}
		String sSQL = "SELECT (SELECT COUNT(IC.CODITVENDA) FROM VDITVENDA IC WHERE IC.CODVENDA=V.CODVENDA AND IC.CODEMP=V.CODEMP AND IC.CODFILIAL=V.CODFILIAL AND IC.TIPOVENDA=V.TIPOVENDA ),"
				+ "(SELECT L.CODLOTE FROM EQLOTE L WHERE L.CODPROD=I.CODPROD AND L.CODLOTE=I.CODLOTE),"
				+ "(SELECT L.VENCTOLOTE FROM EQLOTE L WHERE L.CODPROD=I.CODPROD AND L.CODLOTE=I.CODLOTE),"
				+ "V.CODVENDA,V.CODCLI,C.RAZCLI,C.CNPJCLI,C.CPFCLI,V.DTEMITVENDA,C.ENDCLI,C.NUMCLI,C.COMPLCLI,"
				+ "C.BAIRCLI,C.CEPCLI,V.OBSVENDA,V.DTSAIDAVENDA,C.CIDCLI,C.UFCLI,C.FONECLI,C.FONECLI,"
				+ "C.FAXCLI,C.INSCCLI,C.RGCLI,I.CODPROD,P.REFPROD,P.CODBARPROD,P.DESCPROD,P.CODUNID,I.PERCISSITVENDA,"
				+ "I.QTDITVENDA,I.PRECOITVENDA,I.VLRPRODITVENDA,I.CODNAT,I.PERCICMSITVENDA,"
				+ "PERCIPIITVENDA,VLRIPIITVENDA,V.VLRBASEICMSVENDA,V.VLRICMSVENDA,V.VLRPRODVENDA,"
				+ "V.VLRFRETEVENDA,V.VLRDESCVENDA,V.VLRDESCITVENDA,V.VLRADICVENDA,V.VLRIPIVENDA,"
				+ "V.VLRLIQVENDA,V.CODVEND,VEND.NOMEVEND,V.CODPLANOPAG,PG.DESCPLANOPAG,F.CODTRAN,"
				+ "T.RAZTRAN,F.TIPOFRETEVD,F.PLACAFRETEVD,F.UFFRETEVD,T.CNPJTRAN,T.ENDTRAN,T.NUMTRAN,T.CIDTRAN,"
				+ "T.UFTRAN,T.INSCTRAN,F.QTDFRETEVD,F.ESPFRETEVD,F.MARCAFRETEVD,F.PESOBRUTVD,"
				+ "F.PESOLIQVD,I.VLRLIQITVENDA,P.DESCAUXPROD,C.DDDCLI,P.CODFISC,FC.DESCFISC " 
				+ "FROM VDVENDA V, VDCLIENTE C,VDITVENDA I, EQPRODUTO P,VDVENDEDOR VEND, FNPLANOPAG PG,"
				+ "VDFRETEVD F, VDTRANSP T, LFCLFISCAL FC " 
				+ "WHERE V.CODVENDA=? " 
				+ " AND C.CODCLI=V.CODCLI AND C.CODEMP=V.CODEMPCL AND C.CODFILIAL=V.CODFILIALCL " 
				+ " AND I.CODVENDA=V.CODVENDA AND I.CODEMP=V.CODEMP AND I.CODFILIAL=V.CODFILIAL AND I.TIPOVENDA=V.TIPOVENDA "
				+ " AND P.CODPROD=I.CODPROD AND VEND.CODVEND=V.CODVEND"
				+ " AND PG.CODPLANOPAG=V.CODPLANOPAG AND F.CODVENDA=V.CODVENDA AND T.CODTRAN=F.CODTRAN"
				+ " AND FC.CODEMP=P.CODEMPFC AND FC.CODFILIAL=P.CODFILIALFC AND FC.CODFISC=P.CODFISC "
				+ " ORDER BY P." + dl.getValor() + ",P.DESCPROD";

		PreparedStatement ps = null;
		ResultSet rs = null;
		int iItImp = 0;
		int iMaxItem = 0;
		try {
			imp.limpaPags();
			iMaxItem = linPag - 22;
			ps = con.prepareStatement(sSQL);
			ps.setInt(1,iCodVenda);
			rs = ps.executeQuery();
			while (rs.next()) {
				if (imp.pRow() == 0) {
					imp.impCab(136, false);
					imp.say(imp.pRow() + 1, 0, "" + imp.normal());

					imp.say(imp.pRow() + 0, 4, "PEDIDO DE VENDA No.: ");
					imp.say(imp.pRow() + 0, 25, rs.getString("CodVenda"));
					imp.say(imp.pRow() + 1, 0, "" + imp.normal());
					imp.say(imp.pRow() + 0, 0, "");
					imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
					imp.say(imp.pRow() + 0, 62, "DESTINATARIO");
					imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
					imp.say(imp.pRow() + 0, 0, "");
					imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
					imp.say(imp.pRow() + 0, 0, "");
					imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
					imp.say(imp.pRow() + 0, 4, "[ Nome/Razao Social ]");
					imp.say(imp.pRow() + 0, 76,rs.getString("CpfCli") != null ? "[ CPF ]": "[ CNPJ ]");
					imp.say(imp.pRow() + 0, 96, "[ Data de Emissão ]");
					imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
					imp.say(imp.pRow() + 0, 4, rs.getString("CodCli") + " - "+ rs.getString("RazCli"));
					imp.say(imp.pRow() + 0, 76,rs.getString("CpfCli") != null ? Funcoes.setMascara(rs.getString("CpfCli"),"###.###.###-##") : Funcoes
									.setMascara(rs.getString("CnpjCli"),"##.###.###/####-##"));
					imp.say(imp.pRow() + 0, 100, Funcoes.sqlDateToStrDate(rs.getDate("DtEmitVenda")));
					imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
					imp.say(imp.pRow() + 0, 4, "[ Endereco ]");
					imp.say(imp.pRow() + 0, 55, "[ Bairro ]");
					imp.say(imp.pRow() + 0, 86, "[ CEP ]");
					imp.say(imp.pRow() + 0, 96, "[ Data de Saída ]");
					imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
					imp.say(imp.pRow() + 0,4,Funcoes.copy(rs.getString("EndCli"), 0, 30).trim()+ ", "
											+ (rs.getString("NumCli") != null ? Funcoes.copy(rs.getString("NumCli"),0, 6).trim(): "").trim()+ " - "
											+ (rs.getString("ComplCli") != null ? Funcoes.copy(rs.getString("ComplCli"),0, 9).trim(): "").trim());
					imp.say(imp.pRow() + 0, 55, rs.getString("BairCli"));
					imp.say(imp.pRow() + 0, 86, Funcoes.setMascara(rs.getString("CepCli"), "#####-###"));
					imp.say(imp.pRow() + 0, 100, Funcoes.sqlDateToStrDate(rs.getDate("DtSaidaVenda")));
					imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
					imp.say(imp.pRow() + 0, 4, "[ Municipio ]");
					imp.say(imp.pRow() + 0, 39, "[ UF ]");
					imp.say(imp.pRow() + 0, 46, "[ Fone/Fax ]");
					imp.say(imp.pRow() + 0, 76,rs.getString("RgCli") != null ? "[ RG ]": "[ Insc. Est. ]");
					imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
					imp.say(imp.pRow() + 0, 4, rs.getString("CidCli"));
					imp.say(imp.pRow() + 0, 39, rs.getString("UfCli"));
					imp.say(imp.pRow() + 0, 46,(rs.getString("DDDCli")!=null?Funcoes.setMascara(rs.getString("DDDCli"), "(####)"):"")+ 
							(rs.getString("FoneCli")!=null?Funcoes.setMascara(rs.getString("FoneCli").trim(), "####-####"):"")
							+ " - "+ (rs.getString("FaxCli") != null ? Funcoes.setMascara(rs.getString("FaxCli"),"####-####") : ""));
					imp.say(imp.pRow() + 0, 76,rs.getString("RgCli") != null ? rs.getString("RgCli") : rs.getString("InscCli"));
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
				if ((rs.getDate(3) != null) && (rs.getString(2) != null)) {sStrLote = " - L:"+ rs.getString(2).trim()
							+ ", VC:"+ Funcoes.sqlDateToStrDate(rs.getDate(3)).substring(3);
				}
				imp.say(imp.pRow() + 0, 18, Funcoes.copy(rs.getString("DescProd").trim(), 0, 37 - sStrLote.length())+ sStrLote);
				imp.say(imp.pRow() + 0, 56, rs.getString("CodUnid"));
				imp.say(imp.pRow() + 0, 65, "" + rs.getDouble("QtdItVenda"));
				if (bPrefs[2]) {
					//                 System.out.println(rs.getDouble("VlrLiqItVenda")+"/"+rs.getDouble("QtdItVenda")+"="+((new
					// BigDecimal(rs.getDouble("VlrLiqItVenda"))).divide(new
					// BigDecimal(rs.getDouble("QtdItVenda")),2,BigDecimal.ROUND_HALF_UP)));
					imp.say(imp.pRow() + 0, 72, Funcoes.strDecimalToStrCurrency(14, 2, ""+ (new BigDecimal(Funcoes.arredDouble(rs
											.getDouble("VlrLiqItVenda")/ rs.getDouble("QtdItVenda"),casasDec)))));
					imp.say(imp.pRow() + 0, 87, Funcoes.strDecimalToStrCurrency(14, 2, rs.getString("VlrLiqItVenda")));
				} else {
					imp.say(imp.pRow() + 0, 72, Funcoes.strDecimalToStrCurrency(14, 2, rs.getString("PrecoItVenda")));
					imp.say(imp.pRow() + 0, 87, Funcoes.strDecimalToStrCurrency(14, 2, rs.getString("VlrProdItVenda")));
				}
				imp.say(imp.pRow() + 0, 102, ""+ rs.getDouble("PercICMSItVenda"));
				imp.say(imp.pRow() + 0, 108, ""+ rs.getDouble("PercIPIItVenda"));
				imp.say(imp.pRow() + 0, 114, Funcoes.strDecimalToStrCurrency(14, 2, rs.getString("VlrIPIItVenda")));
				imp.say(imp.pRow() + 0, 129, Funcoes.setMascara(rs.getString("CodNat"), "#.###"));
				if (Funcoes.copy(rs.getString("CodUnid"), 0, 3).equals("PAR"))
					iPares += (int) rs.getDouble("QtdItVenda");
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
						imp.say(imp.pRow() + 0, 4, Funcoes.strDecimalToStrCurrency(14, 2, rs.getString("VlrBaseICMSVenda")));
						imp.say(imp.pRow() + 0, 29, Funcoes.strDecimalToStrCurrency(14, 2, rs.getString("VlrICMSVenda")));
						if (bPrefs[2])
							imp.say(imp.pRow() + 0, 104, Funcoes.strDecimalToStrCurrency(14, 2, rs.getString("VlrLiqVenda")));
						else
							imp.say(imp.pRow() + 0, 104, Funcoes.strDecimalToStrCurrency(14, 2, rs.getString("VlrProdVenda")));
						imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
						imp.say(imp.pRow() + 0, 4, "[ Valor do Frete ]");
						imp.say(imp.pRow() + 0, 29, "[ Valor do Desconto ]");
						imp.say(imp.pRow() + 0, 54, "[ Outras Despesas ]");
						imp.say(imp.pRow() + 0, 79, "[ Valor do IPI ]");
						imp.say(imp.pRow() + 0, 104, "[ VALOR TOTAL ]");
						imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
						imp.say(imp.pRow() + 0, 4, Funcoes.strDecimalToStrCurrency(14, 2, rs.getString("VlrFreteVenda")));
						if (!bPrefs[2]) {
							imp.say(imp.pRow() + 0,29,Funcoes.strDecimalToStrCurrency(14,2,rs.getDouble("VlrDescVenda") == 0 ? rs
																	.getString("VlrDescItVenda"): rs.getString("VlrDescVenda")));
							imp.say(imp.pRow() + 0, 64, Funcoes.strDecimalToStrCurrency(14, 2, rs.getString("VlrAdicVenda")));
							imp.say(imp.pRow() + 0, 79, Funcoes.strDecimalToStrCurrency(14, 2, rs.getString("VlrIPIVenda")));
						}
						imp.say(imp.pRow() + 0, 104, Funcoes.strDecimalToStrCurrency(14, 2, rs.getString("VlrLiqVenda")));
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
					imp.say(imp.pRow() + 0, 50,
							"TRANSPORTADOR / VOLUMES TRANSPORTADOS");
					imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
					imp.say(imp.pRow() + 0, 0, "");
					imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
					imp.say(imp.pRow() + 0, 4, "[ Nome / Razão Social ]");
					imp.say(imp.pRow() + 0, 60, "[ Frete ]");
					imp.say(imp.pRow() + 0, 72, "[ Placa ]");
					imp.say(imp.pRow() + 0, 85, "[ UF ]");
					imp.say(imp.pRow() + 0, 95, "[ CNPJ ]");
					imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
					imp.say(imp.pRow() + 0, 4, rs.getString("RazTran"));
					imp.say(imp.pRow() + 0, 60, rs.getString("TipoFreteVD")
							.equals("C") ? "CIF" : "FOB");
					imp.say(imp.pRow() + 0, 72, rs.getString("PlacaFreteVD"));
					imp.say(imp.pRow() + 0, 85, rs.getString("UfFreteVD"));
					imp.say(imp.pRow() + 0, 95, Funcoes.setMascara(rs
							.getString("CnpjTran") != null ? rs
							.getString("CnpjTran") : "", "##.###.###/####-##"));
					imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
					imp.say(imp.pRow() + 0, 4, "[ Endereço ]");
					imp.say(imp.pRow() + 0, 60, "[ Municipio ]");
					imp.say(imp.pRow() + 0, 91, "[ UF ]");
					imp.say(imp.pRow() + 0, 100, "[ Insc. Est. ]");
					imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
					imp.say(imp.pRow() + 0, 4, Funcoes.copy(rs.getString("EndTran"), 0, 42)+ ", "
							+ Funcoes.copy(rs.getString("NumTran"), 0, 6));
					imp.say(imp.pRow() + 0, 60, rs.getString("CidTran"));
					imp.say(imp.pRow() + 0, 91, rs.getString("UfTran"));
					imp.say(imp.pRow() + 0, 100, rs.getString("InscTran"));
					imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
					imp.say(imp.pRow() + 0, 4, "[ Quantidade ]");
					imp.say(imp.pRow() + 0, 24, "[ Espécie ]");
					imp.say(imp.pRow() + 0, 49, "[ Marca ]");
					imp.say(imp.pRow() + 0, 79, "[ PesoBrut ]");
					imp.say(imp.pRow() + 0, 99, "[ PesoLiq ]");
					imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
					imp.say(imp.pRow() + 0, 4, rs.getString("QtdFreteVD"));
					imp.say(imp.pRow() + 0, 24, rs.getString("EspFreteVD"));
					imp.say(imp.pRow() + 0, 49, rs.getString("MarcaFreteVD"));
					imp.say(imp.pRow() + 0, 79, rs.getString("PesoBrutVD"));
					imp.say(imp.pRow() + 0, 99, rs.getString("PesoLiqVD"));
					imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
					imp.say(imp.pRow() + 0, 0, "");
					imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
					imp.say(imp.pRow() + 0, 0, "");
					imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
					imp.say(imp.pRow() + 0, 3, "OBSERVAÇÕES");
					imp.say(imp.pRow() + 0, 60, "DADOS ADICIONAIS");
					imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
					imp.say(imp.pRow() + 0, 0, Funcoes.copy(rs.getString("ObsVenda"), 0, 40));
					imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
					imp.say(imp.pRow() + 0, 63, iPares > 0 ? "PARES: "
							+ Funcoes.strZero("" + iPares, 4) : "");
					imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
					imp.say(imp.pRow() + 0, 53, "ENTREGA PREVISTA : "
							+ Funcoes.sqlDateToStrDate(rs.getDate("DtSaidaVenda")));
					imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
					imp.say(imp.pRow() + 0, (125 - rs.getString("NomeVend")
							.trim().length()) / 2, "REPRES. : "+ rs.getString("NomeVend"));
					imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
					imp.say(imp.pRow() + 0, (116 - rs.getString("DescPlanoPag")
							.trim().length()) / 2, "FORMA DE PAGAMENTO : "+ rs.getString("DescPlanoPag"));
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
			Funcoes.mensagemErro(this, "Erro ao consultar a tabela de Venda!"
					+ err.getMessage(),true,con,err);
		}

		if (bVisualizar) {
			imp.preview(this);
		} else {
			imp.print();
		}
	}*/

	private synchronized void focusIni() {
		tpnCab.requestFocus(true);
	}

	private void focusCodprod() {
		if (bPrefs[0])
			txtRefProd.requestFocus();
		else
			txtCodProd.requestFocus();
	}

	public void exec(int iCodVenda) {
		txtCodVenda.setVlrString(iCodVenda + "");
		lcCampos.carregaDados();
	}

	private boolean[] prefs() {
		boolean[] bRetorno = new boolean[17];
		String sSQL = "SELECT USAREFPROD,USAPEDSEQ,USALIQREL,TIPOPRECOCUSTO,ORDNOTA," +
			"USACLASCOMIS,TRAVATMNFVD,NATVENDA,IPIVENDA,BLOQVENDA, VENDAMATPRIM, DESCCOMPPED, " +
			"TAMDESCPROD, OBSCLIVEND, CONTESTOQ, DIASPEDT, RECALCPCVENDA, USALAYOUTPED " + 
			"FROM SGPREFERE1 WHERE CODEMP=? AND CODFILIAL=?";
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement(sSQL);
			ps.setInt(1, Aplicativo.iCodEmp);
			ps.setInt(2, ListaCampos.getMasterFilial("SGPREFERE1"));
			rs = ps.executeQuery();
			if (rs.next()) {
				if (rs.getString("UsaRefProd").trim().equals("S"))
					bRetorno[0] = true;
				if (rs.getString("UsaPedSeq").trim().equals("S"))
					bRetorno[1] = true;
				if (rs.getString("UsaLiqRel") == null) {
					Funcoes.mensagemInforma(this,
							"Preencha opção de desconto em preferências!");
				} else {
					if (rs.getString("UsaLiqRel").trim().equals("S"))
						bRetorno[2] = true;

					sOrdNota = rs.getString("OrdNota");

					if (rs.getString("TipoPrecoCusto").equals("M"))
						bRetorno[3] = true;
					if (rs.getString("UsaClasComis").trim().equals("S"))
						bRetorno[4] = true;
				}
				bRetorno[5] = true;
				if (rs.getString("TravaTmNfVd") != null) {
					if (rs.getString("TravaTmNfVd").equals("N"))
						bRetorno[5] = false;
				}
				bRetorno[6] = true;
				if (rs.getString("NatVenda") != null) {
					if (rs.getString("NatVenda").trim().equals("N"))
						bRetorno[6] = false;
				}
				bRetorno[7] = false;
				if (rs.getString("BloqVenda") != null) {
					if (rs.getString("BloqVenda").trim().equals("S"))
						bRetorno[7] = true;
				}
				bRetorno[8] = false;
				if (rs.getString("VendaMatPrim") != null) {
					if (rs.getString("VendaMatPrim").trim().equals("S"))
						bRetorno[8] = true;
				}
				bRetorno[9] = false;
				if (rs.getString("DescCompPed") != null) {
					if (rs.getString("DescCompPed").trim().equals("S"))
						bRetorno[9] = true;
				}
				bRetorno[10] = false;
				if (rs.getString("TAMDESCPROD") != null) {
					if (rs.getString("TAMDESCPROD").trim().equals("100"))
						bRetorno[10] = true;
				}
				bRetorno[11] = false;
				if (rs.getString("OBSCLIVEND") != null) {
					if (rs.getString("OBSCLIVEND").trim().equals("S"))
						bRetorno[11] = true;
				}
				bRetorno[12] = true;
				if (rs.getString("IPIVenda") != null) {
					if (rs.getString("IPIVenda").trim().equals("N"))
						bRetorno[12] = false;
				}
				bRetorno[13] = true;
				if (rs.getString("CONTESTOQ") != null) {
					if (rs.getString("CONTESTOQ").trim().equals("N"))
						bRetorno[13] = false;
				}
				bRetorno[14] = false;
				if (rs.getString("DIASPEDT") != null) {
					if (rs.getString("DIASPEDT").trim().equals("S"))
						bRetorno[14] = true;
				}
				bRetorno[15] = false;
				if (rs.getString("RECALCPCVENDA") != null) {
					if (rs.getString("RECALCPCVENDA").trim().equals("S"))
						bRetorno[15] = true;
				}
				bRetorno[16] = false;
				if (rs.getString("USALAYOUTPED") != null) {
					if (rs.getString("USALAYOUTPED").trim().equals("S"))
						bRetorno[16] = true;
				}

			}
			rs.close();
			ps.close();
			if (!con.getAutoCommit())
				con.commit();
		} catch (SQLException err) {
			Funcoes.mensagemErro(this, "Erro ao carregar a tabela PREFERE1!\n"
					+ err.getMessage(),true,con,err);
		}
		finally {
			rs = null;
			ps = null;
		}
		return bRetorno;
	}
	
	public void focusGained(FocusEvent fevt) { }

	public void focusLost(FocusEvent fevt) {
		if (fevt.getSource() == txtPercDescItVenda) {
			if (txtPercDescItVenda.getText().trim().length() < 1) {
				txtVlrDescItVenda.setAtivo(true);
			} else {
				calcDescIt();
				calcVlrProd();
				calcImpostos(true);
				txtVlrDescItVenda.setAtivo(false);
			}
		} else if (fevt.getSource() == txtPercComItVenda) {
			if (txtPercComItVenda.getText().trim().length() < 1) {
				txtVlrComisItVenda.setAtivo(true);
			} else {
				calcComisIt();
				calcVlrProd();
				calcImpostos(true);
				txtVlrComisItVenda.setAtivo(false);
			}
		} else if (fevt.getSource() == txtVlrDescItVenda) {
			if (txtVlrDescItVenda.getText().trim().length() < 1) {
				txtPercDescItVenda.setAtivo(true);
			} else if (txtVlrDescItVenda.getAtivo()) {
				txtPercDescItVenda.setAtivo(false);
			}
		} else if (fevt.getSource() == txtVlrComisItVenda) {
			if (txtVlrComisItVenda.getText().trim().length() < 1) {
				txtPercComItVenda.setAtivo(true);
			} else if (txtVlrComisItVenda.getAtivo()) {
				txtPercComItVenda.setAtivo(false);
			}
		} else if ((fevt.getSource() == txtQtdItVenda)
				| (fevt.getSource() == txtPrecoItVenda)
				| (fevt.getSource() == txtCodNat)) {
			calcVlrProd();
			calcImpostos(true);
		} else if ((fevt.getSource() == txtPercICMSItVenda)
				| (fevt.getSource() == txtAliqIPIItVenda)) {
			calcImpostos(false);
		}
	}

	public void beforeCarrega(CarregaEvent cevt) {
		if (cevt.getListaCampos() == lcProd2)
			lcProd.edit();
		if (lcCampos.getStatus() != ListaCampos.LCS_INSERT) { //Cancela os
															  // auto-incrementos
															  // que sobrepõem o
															  // que está
															  // guardado na
															  // tabela venda
			if (cevt.getListaCampos() == lcVendedor) {
				lcVendedor.cancLerCampo(2, true); //Comissão do vendedor;
			} else if (cevt.getListaCampos() == lcCli) {
				lcCli.cancLerCampo(2, true); //Código de Pagamento
				lcCli.cancLerCampo(3, true); //Código do Vendador
			}
		} else {
			if (cevt.getListaCampos() == lcVendedor) {//Ativa auto-incrementos
				lcVendedor.cancLerCampo(2, false); //Comissão do vendedor;
			} else if (cevt.getListaCampos() == lcCli) {
				lcCli.cancLerCampo(2, false); //Código do Pagamento
				lcCli.cancLerCampo(3, false); //Código do Vendedor
			}
		}
		if (lcDet.getStatus() != ListaCampos.LCS_INSERT) {
			if (cevt.getListaCampos() == lcProd) {
				lcProd.cancLerCampo(5, true); //Código da Classificação Fiscal
			}
		} else {
			if (cevt.getListaCampos() == lcProd) {
				lcProd.cancLerCampo(5, false); //Código da Classificação Fiscal
			}
		}
		if (cevt.getListaCampos() == lcCampos) {
			if (bPrefs[5]) {
				txtFiscalTipoMov1.setText("S");
				txtFiscalTipoMov2.setText("N");
			}
			if (bPrefs[11]) 
				iCodCliAnt = txtCodCli.getVlrInteger().intValue();
		}

	}

	public void afterCarrega(CarregaEvent cevt) {
		try {
			if ((cevt.getListaCampos() == lcProd)
					|| (cevt.getListaCampos() == lcProd2)) {
				if (txtCLoteProd.getText().trim().equals("N")) {
					txtCodLote.setAtivo(false);//Desativa o Cógigo do lote por o
											   // produto não possuir lote
				} else if (txtCLoteProd.getText().trim().equals("S")) {
					txtCodLote.setAtivo(true);//Ativa o Cógigo do Lote pois o
											  // produto tem lote
					if (lcDet.getStatus() == ListaCampos.LCS_INSERT)
						buscaLote();
				}
				if (lcDet.getStatus() == ListaCampos.LCS_INSERT) {
					calcVlrItem(null,false);
				}
			} else if ((cevt.getListaCampos() == lcFisc)
					&& (lcDet.getStatus() == ListaCampos.LCS_INSERT)) {
				buscaNat();
				buscaTratTrib();
			} else if (cevt.getListaCampos() == lcNat) {
				if ((cevt.ok) & (lcDet.getStatus() == ListaCampos.LCS_INSERT)) {
					buscaICMS();
				}
			} else if (cevt.getListaCampos() == lcDet) {
				lcVenda2.carregaDados();//Carrega os Totais
			} else if (cevt.getListaCampos() == lcCampos) {
				String s = txtCodVenda.getVlrString();
				lcVenda2.carregaDados();//Carrega os Totais
				txtCodVenda.setVlrString(s);
			} else if (cevt.getListaCampos() == lcVenda2) {
				txtPercComisVenda.setAtivo(txtVlrComisVenda.floatValue() == 0);
			} else if (cevt.getListaCampos() == lcCli ) {
				if ( (bPrefs[11]) ) {
					if(iCodCliAnt!=txtCodCli.getVlrInteger().intValue()){
						iCodCliAnt = txtCodCli.getVlrInteger().intValue();
						mostraObsCli(iCodCliAnt,
									 new Point( this.getX(),this.getY() + tpnCab.getHeight() + pnCab.getHeight()),
									 new Dimension( spTab.getWidth(), spTab.getHeight() ) );
					}
				}
				if(bPrefs[15]) {
				    setReCalcPreco(true);
				}
			} else if(cevt.getListaCampos() == lcPlanoPag) {
				if(bPrefs[15]) {
				    setReCalcPreco(true);
				}
			}
			
			if (txtStatusVenda.getVlrString().trim().length()>0 && 
					txtStatusVenda.getVlrString().substring(0,1).equals("C")){
				lbStatus.setText("  CANCELADA");
				lbStatus.setBackground(Color.RED);
				lbStatus.setVisible(true);
			}
			else if (txtStatusVenda.getVlrString().trim().length()>0 && 
					txtStatusVenda.getVlrString().substring(0,1).equals("V")){
				lbStatus.setText("   IMPRESSA");
				lbStatus.setBackground(Color.BLUE);
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
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void beforePost(PostEvent pevt) {
		PreparedStatement psTipoMov = null;
		ResultSet rsTipoMov = null;

		if(pevt.getListaCampos() == lcCampos) {
		    if(podeReCalcPreco() && lcDet.getStatus() == ListaCampos.LCS_READ_ONLY) {
			    calcVlrItem("VDVENDA",true);
			    lcDet.carregaDados();
			    calcImpostos(true);
			    lcDet.edit();
			    lcDet.post();
		    }
		    setReCalcPreco(false);
		}
		if ((pevt.getListaCampos() == lcCampos)
				&& (lcCampos.getStatus() == ListaCampos.LCS_INSERT)) {
			if (txtESTipoMov.getVlrString().equals("E")) {
				if (Funcoes.mensagemConfirma(this,
						"Este movimento irá realizar entradas no estoque.\n"
								+ "Deseja continuar?\n") != 0) {
					pevt.cancela();
					return;
				}
			}
			if (!testaPgto()) {
				if (Funcoes.mensagemConfirma(this,
						"Cliente com duplicatas em aberto! Continuar?") != 0) {
					pevt.cancela();
					return;
				}
			}
			if (bPrefs[5]) {
				try {
					psTipoMov = con.prepareStatement("SELECT CODTIPOMOV,DESCTIPOMOV FROM EQTIPOMOV WHERE "
									+ "CODEMP=? AND CODFILIAL=? AND CODTIPOMOV=? AND FISCALTIPOMOV='N'");
					psTipoMov.setInt(1, Aplicativo.iCodEmp);
					psTipoMov.setInt(2, ListaCampos.getMasterFilial("EQTIPOMOV"));
					psTipoMov.setInt(3, txtCodTipoMov.getVlrInteger().intValue());
					rsTipoMov = psTipoMov.executeQuery();
					if (rsTipoMov.next()) {
						if (rsTipoMov.getInt("CODTIPOMOV") != txtCodTipoMov.getVlrInteger().intValue()) {
							Funcoes.mensagemInforma(this,"Tipo de movimento não permitido na inserção!");
							pevt.cancela();
							return;
						}
					} 
					else {
						Funcoes.mensagemInforma(this,
								"Tipo de movimento não permitido na inserção!");
						pevt.cancela();
						return;
					}
					if (!con.getAutoCommit())
						con.commit();
					rsTipoMov.close();
					psTipoMov.close();
				} 
				catch (SQLException err) {
					Funcoes.mensagemErro(this,"Erro ao pesquisar tipo de movimento!\n" + err.getMessage(),true,con,err);
					pevt.cancela();
				}
			}
			if (bPrefs[1])
				txtCodVenda.setVlrInteger(testaCodPK("VDVENDA"));
			txtStatusVenda.setVlrString("*");
		} 
		else if (pevt.getListaCampos() == lcDet) {
			if ((lcDet.getStatus() == ListaCampos.LCS_INSERT) || (lcDet.getStatus() == ListaCampos.LCS_EDIT)) {
				//txtRefProd.setVlrString(txtRefProd.getText()); // ?
				if (txtCLoteProd.getVlrString().equals("S")) {
					if (!testaCodLote()) {
						pevt.cancela();
					}
				}
				if(txtCodProd.getVlrInteger().intValue()>0) {
					if (!testaLucro()) {
						Funcoes.mensagemInforma(this,"Não é permitido a venda deste produto abaixo do custo!!!");
						pevt.cancela();
					}
				}
				calcDescIt();
			    calcComisIt();
			}
		}
		txtTipoVenda.setVlrString("V");
	}
	
	public void afterPost(PostEvent pevt) {
		lcVenda2.carregaDados(); //Carrega os Totais
		if (pevt.getListaCampos() == lcCampos) {
			if (bPrefs[5]) {
				txtFiscalTipoMov1.setText("S");
				txtFiscalTipoMov2.setText("N");
			}
		}		
	}

	public void beforeInsert(InsertEvent ievt) {
	}

	public void afterInsert(InsertEvent ievt) {
		if (ievt.getListaCampos() == lcCampos) {
			if (bPrefs[5]) {
				txtFiscalTipoMov1.setText("N");
				txtFiscalTipoMov2.setText("N");
			}
			txtDtSaidaVenda.setVlrDate(new Date());
			txtDtEmitVenda.setVlrDate(new Date());
		} else if (ievt.getListaCampos() == lcDet) {
			focusCodprod();
		}
	}

	public void beforeDelete(DeleteEvent devt) {
	}

	public void afterDelete(DeleteEvent devt) {
		if (devt.getListaCampos() == lcDet)
			lcVenda2.carregaDados();
	}

	public void keyPressed(KeyEvent kevt) {
		if (kevt.getKeyCode() == KeyEvent.VK_CONTROL) {
			bCtrl = true;
		} else if (kevt.getKeyCode() == KeyEvent.VK_O) {
			if (bCtrl) {
				btObs.doClick();
			}
		} else if (kevt.getKeyCode() == KeyEvent.VK_ENTER) {
			if (kevt.getSource() == txtPercComisVenda
					|| (kevt.getSource() == txtClasComis && !txtPercComisVenda
							.getAtivo())) {//Como estes são os ultimos campos
										   // do painel de venda executa-se o
										   // post no venda e pula para o campo
										   // adequado no item.
				if (lcCampos.getStatus() == ListaCampos.LCS_INSERT) {
					focusIni();
					focusCodprod();
					lcCampos.post();
					lcDet.edit();
				} else if (lcCampos.getStatus() == ListaCampos.LCS_EDIT) {
					lcCampos.post();
					focusCodprod();
				}
			} else if (kevt.getSource() == txtPedCliVenda) {//Como este é o
															// ultimo campo da
															// aba de venda
															// então abre a tab
															// comissao.
				tpnCab.setSelectedIndex(1);
				tpnCab.doLayout();
				txtCodVend.requestFocus();
			} else if (kevt.getSource() == txtUltCamp) {//Como este é o ultimo
														// campo do painel de
														// itvenda executa-se o
														// post no itvenda e
														// pula para o campo
														// adequado no item.
				if (lcDet.getStatus() == ListaCampos.LCS_INSERT) {
					lcDet.post();
					lcDet.limpaCampos(true);
					lcDet.setState(ListaCampos.LCS_NONE);
					lcDet.edit();
					focusCodprod();
				} else if (lcDet.getStatus() == ListaCampos.LCS_EDIT) {
					lcDet.post();
					txtCodItVenda.requestFocus();
				}
			} else if (kevt.getSource() == txtCodNat) {//Talvez este possa ser
													   // o ultimo campo por
													   // isso o teste.
				if (!txtBaseICMSItVenda.getAtivo()) {
					if (lcDet.getStatus() == ListaCampos.LCS_INSERT) {
						lcDet.post();
						lcDet.limpaCampos(true);
						lcDet.setState(ListaCampos.LCS_NONE);
						lcDet.edit();
						focusCodprod();
					} else if (lcDet.getStatus() == ListaCampos.LCS_EDIT) {
						lcDet.post();
						txtCodItVenda.requestFocus();
					}
				}
			}
		} else if (kevt.getKeyCode() == KeyEvent.VK_F3
				&& kevt.getSource() == txtVlrDescItVenda) {
			mostraTelaDescont();
		} else if (kevt.getKeyCode() == KeyEvent.VK_F4) {
			btFechaVenda.doClick();
		} else if (kevt.getKeyCode() == KeyEvent.VK_F5) {
			btConsPgto.doClick();
		}
		if (kevt.getSource() == txtRefProd)
			lcDet.edit();
		super.keyPressed(kevt);
	}

	public void keyTyped(KeyEvent kevt) {
		super.keyTyped(kevt);
	}

	public void keyReleased(KeyEvent kevt) {
		if (kevt.getKeyCode() == KeyEvent.VK_CONTROL)
			bCtrl = false;
		super.keyReleased(kevt);
	}

	public void actionPerformed(ActionEvent evt) {
		String[] sValores = null;
		if (evt.getSource() == btFechaVenda) {
			DLFechaVenda dl = new DLFechaVenda(con,
					txtCodVenda.getVlrInteger(), this, chbImpPedTipoMov.getVlrString(), chbImpNfTipoMov.getVlrString(),
					chbImpBolTipoMov.getVlrString(), chbReImpNfTipoMov.getVlrString());
			dl.setVisible(true);
			if (dl.OK) {
				sValores = dl.getValores();
				dl.dispose();
			} else {
				dl.dispose();
			}
			lcCampos.carregaDados();
			if (sValores != null) {

				//Ordem dos parâmetros decrescente por que uma tela abre na
				// frente da outra.

				if (sValores[5].equals("S") && !sValores[6].equals("")) {
					FRBoleto fBol = new FRBoleto(this);
					fBol.setConexao(con);
					fBol.txtCodModBol.setVlrInteger(new Integer(sValores[6]));
					fBol.txtCodVenda.setVlrInteger(txtCodVenda.getVlrInteger());
					fBol.imprimir(true);
				}
				if ((sValores[4].equals("S")) || (sValores[7].equals("S"))) {
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
						Funcoes.mensagemErro(this,
								"Não existe nota para o tipo de movimento: '"
										+ txtTipoMov.getVlrString() + "'");
						return;
					}
					if (sValores[7].equals("N"))
						txtStatusVenda.setVlrString("V4");
				} else if (sValores[3].equals("S")) {
					imprimir(true, txtCodVenda.getVlrInteger().intValue());
				}
				if (sValores[7].equals("N")) {
					lcCampos.edit();
					lcCampos.post();
				}
				if ((sValores[4].equals("S")) && (bPrefs[7])) {
					bloqvenda();
				}
			}
			
			tpnCab.setSelectedIndex(0);
			txtCodVenda.requestFocus();
			
		} else if (evt.getSource() == btConsPgto) {
			DLConsultaPgto dl = new DLConsultaPgto(this, con, txtCodCli
					.getVlrInteger().intValue());
			dl.setVisible(true);
			dl.dispose();
		} else if (evt.getSource() == btPrevimp)
			imprimir(true, txtCodVenda.getVlrInteger().intValue());
		else if (evt.getSource() == btImp)
			imprimir(false, txtCodVenda.getVlrInteger().intValue());
		else if (evt.getSource() == btObs) {
			mostraObs( "VDVENDA", txtCodVenda.getVlrInteger().intValue() );
		} else if (evt.getSource() == btAdicOrc) {
			abreAdicOrc();
		} else if (evt.getSource() == btAltComis) {
			altComisVend();
		}
		super.actionPerformed(evt);
	}

	public void setConexao(Connection cn) {
		super.setConexao(cn);
		montaTela();
		lcTratTrib.setConexao(cn);
		lcTipoMov.setConexao(cn);
		lcCli.setConexao(cn);
		lcVendedor.setConexao(cn);
		lcPlanoPag.setConexao(cn);
		lcSerie.setConexao(cn);
		lcProd.setConexao(cn);
		lcProd2.setConexao(cn);
		lcNat.setConexao(cn);
		lcAlmox.setConexao(cn);
		lcLote.setConexao(cn);
		lcFisc.setConexao(cn);
		lcVenda2.setConexao(cn);
		lcClComis.setConexao(cn);
	}
}