/**
 * @version 14/07/2003 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)FCompra.java <BR>
 * 
 * Este programa é licenciado de acordo com a LPG-PC (Licença Pública Geral para Programas de Computador), <BR>
 * versão 2.1.0 ou qualquer versão posterior. <BR>
 * A LPG-PC deve acompanhar todas PUBLICAÇÕES, DISTRIBUIÇÕES e REPRODUÇÕES deste Programa. <BR>
 * Caso uma cópia da LPG-PC não esteja disponível junto com este Programa, você pode contatar <BR>
 * o LICENCIADOR ou então pegar uma cópia em: <BR>
 * Licença: http://www.lpg.adv.br/licencas/lpgpc.rtf <BR>
 * Para poder USAR, PUBLICAR, DISTRIBUIR, REPRODUZIR ou ALTERAR este Programa é preciso estar <BR>
 * de acordo com os termos da LPG-PC <BR> <BR>
 *
 * Tela para cadastro de notas fiscais de compra.
 */

package org.freedom.modulos.std;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.freedom.acao.CarregaEvent;
import org.freedom.acao.CarregaListener;
import org.freedom.acao.InsertEvent;
import org.freedom.acao.InsertListener;
import org.freedom.acao.PostEvent;
import org.freedom.acao.PostListener;
import org.freedom.bmps.Icone;
import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.ImprimeOS;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.Painel;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FDetalhe;

public class FCompra extends FDetalhe implements PostListener, CarregaListener, FocusListener, ActionListener, InsertListener {
  private int casasDec = Aplicativo.casasDec;
  private Painel pinCab = new Painel();
  private Painel pinDet = new Painel();
  private Painel pinTot = new Painel(200, 200);
  private JPanel pnTot = new JPanel(new GridLayout(1, 1));
  private JPanel pnCenter = new JPanel(new BorderLayout());
  private JButton btFechaCompra = new JButton(Icone.novo("btOk.gif"));
  private JTextFieldPad txtCodCompra = new JTextFieldPad();
  private JTextFieldPad txtCodTipoMov = new JTextFieldPad();
  private JTextFieldPad txtDtEmitCompra = new JTextFieldPad();
  private JTextFieldPad txtDtEntCompra = new JTextFieldPad();
  private JTextFieldPad txtCodFor = new JTextFieldPad();
  private JTextFieldPad txtCodPlanoPag = new JTextFieldPad();
  private JTextFieldPad txtCodItCompra = new JTextFieldPad();
  private JTextFieldPad txtQtdItCompra = new JTextFieldPad();
  private JTextFieldPad txtCodProd = new JTextFieldPad();
  private JTextFieldPad txtRefProd = new JTextFieldPad();
  private JTextFieldPad txtCLoteProd = new JTextFieldPad();
  private JTextFieldPad txtPrecoItCompra = new JTextFieldPad();
  private JTextFieldPad txtPercDescItCompra = new JTextFieldPad();
  private JTextFieldPad txtVlrDescItCompra = new JTextFieldPad();
  private JTextFieldPad txtPercComItCompra = new JTextFieldPad();
  private JTextFieldPad txtCodNat = new JTextFieldPad();
  private JTextFieldPad txtBaseICMSItCompra = new JTextFieldPad();
  private JTextFieldPad txtPercICMSItCompra = new JTextFieldPad();
  private JTextFieldPad txtVlrICMSItCompra = new JTextFieldPad();
  private JTextFieldPad txtVlrLiqItCompra = new JTextFieldPad();
  private JTextFieldPad txtCodLote = new JTextFieldPad();
  private JTextFieldPad txtCodFisc = new JTextFieldPad();
  private JTextFieldPad txtTipoFisc = new JTextFieldPad();
  private JTextFieldPad txtRedFisc = new JTextFieldPad();
  private JTextFieldPad txtVlrIPICompra = new JTextFieldPad();
  private JTextFieldPad txtVlrDescCompra = new JTextFieldPad();
  private JTextFieldPad txtVlrLiqCompra = new JTextFieldPad();
  private JTextFieldPad txtVlrProdItCompra = new JTextFieldPad();
  private JTextFieldPad txtBaseIPIItCompra = new JTextFieldPad();
  private JTextFieldPad txtAliqIPIItCompra = new JTextFieldPad();
  private JTextFieldPad txtVlrIPIItCompra = new JTextFieldPad();
  private JTextFieldPad txtCustoItCompra = new JTextFieldPad();
  private JTextFieldPad txtAliqIPIFisc = new JTextFieldPad();
  private JTextFieldPad txtVlrBrutCompra = new JTextFieldPad();
  private JTextFieldPad txtSerieCompra = new JTextFieldPad();
  private JTextFieldPad txtDocCompra = new JTextFieldPad();
  private JTextFieldPad txtStatusCompra = new JTextFieldPad();
  private JTextFieldFK txtDescTipoMov = new JTextFieldFK();
  private JTextFieldFK txtDescFor = new JTextFieldFK();
  private JTextFieldFK txtEstFor = new JTextFieldFK();
  private JTextFieldFK txtDescPlanoPag = new JTextFieldFK();
  private JTextFieldFK txtDescProd = new JTextFieldFK();
  private JTextFieldFK txtDescNat = new JTextFieldFK();
  private JTextFieldFK txtDescLote = new JTextFieldFK();
  private JTextFieldPad txtCodBarProd = new JTextFieldPad();
  private JTextFieldPad txtCodFabProd = new JTextFieldPad();
  private JTextFieldFK txtDescFisc = new JTextFieldFK();

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
  private Connection con = null;
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

    JPanel pnLab = new JPanel(new GridLayout(1, 1));
    pnLab.add(new JLabel(" Totais:"));

    pnMaster.add(pnCenter, BorderLayout.CENTER);

    txtCodTipoMov.setTipo(JTextFieldPad.TP_INTEGER, 8, 0);
    txtDescTipoMov.setTipo(JTextFieldPad.TP_STRING, 40, 0);
    lcTipoMov.add(new GuardaCampo(txtCodTipoMov, 7, 100, 80, 20, "CodTipoMov", "Cód.tp.mov.", true, false, null, JTextFieldPad.TP_INTEGER, false), "txtCodTipoMovx");
    lcTipoMov.add(new GuardaCampo(txtDescTipoMov, 90, 100, 207, 20, "DescTipoMov", "Descrição do tipo de movimento", false, false, null, JTextFieldPad.TP_STRING, false), "txtDescTipoMovx");
    lcTipoMov.setWhereAdic("ESTIPOMOV = 'E'");
    lcTipoMov.montaSql(false, "TIPOMOV", "EQ");
    lcTipoMov.setQueryCommit(false);
    lcTipoMov.setReadOnly(true);
    txtCodTipoMov.setTabelaExterna(lcTipoMov);

    txtSerieCompra.setTipo(JTextFieldPad.TP_STRING, 4, 0);
    txtDocCompra.setTipo(JTextFieldPad.TP_INTEGER, 8, 0);
    lcSerie.add(new GuardaCampo(txtSerieCompra, 7, 100, 80, 20, "Serie", "Série", true, false, null, JTextFieldPad.TP_STRING, false), "txtCodForx");
    lcSerie.add(new GuardaCampo(txtDocCompra, 90, 100, 207, 20, "DocSerie", "Doc", false, false, null, JTextFieldPad.TP_INTEGER, false), "txtDescForx");
    lcSerie.montaSql(false, "SERIE", "LF");
    lcSerie.setQueryCommit(false);
    lcSerie.setReadOnly(true);
    txtSerieCompra.setTabelaExterna(lcSerie);

    txtCodFor.setTipo(JTextFieldPad.TP_INTEGER, 8, 0);
    txtDescFor.setTipo(JTextFieldPad.TP_STRING, 50, 0);
    txtEstFor.setTipo(JTextFieldPad.TP_STRING, 2, 0);
    lcFor.add(new GuardaCampo(txtCodFor, 7, 100, 80, 20, "CodFor", "Cód.for.", true, false, null, JTextFieldPad.TP_INTEGER, false), "txtCodForx");
    lcFor.add(new GuardaCampo(txtDescFor, 90, 100, 207, 20, "RazFor", "Razão social do fornecedor", false, false, null, JTextFieldPad.TP_STRING, false), "txtDescForx");
    lcFor.add(new GuardaCampo(txtEstFor, 90, 100, 207, 20, "UFFor", "UF", false, false, null, JTextFieldPad.TP_STRING, false), "txtDescForx");
    lcFor.montaSql(false, "FORNECED", "CP");
    lcFor.setQueryCommit(false);
    lcFor.setReadOnly(true);
    txtCodFor.setTabelaExterna(lcFor);

    txtCodPlanoPag.setTipo(JTextFieldPad.TP_INTEGER, 8, 0);
    txtDescPlanoPag.setTipo(JTextFieldPad.TP_STRING, 40, 0);
    lcPlanoPag.add(new GuardaCampo(txtCodPlanoPag, 7, 100, 80, 20, "CodPlanoPag", "Cód.p.pag.", true, false, null, JTextFieldPad.TP_INTEGER, false), "txtCodPlanoPagx");
    lcPlanoPag.add(new GuardaCampo(txtDescPlanoPag, 90, 100, 207, 20, "DescPlanoPag", "Descrição do plano de pagamento", false, false, null, JTextFieldPad.TP_STRING, false), "txtDescPlanoPagx");
    lcPlanoPag.montaSql(false, "PLANOPAG", "FN");
    lcPlanoPag.setQueryCommit(false);
    lcPlanoPag.setReadOnly(true);
    txtCodPlanoPag.setTabelaExterna(lcPlanoPag);

    txtDescFisc.setTipo(JTextFieldPad.TP_STRING, 50, 0);
    txtTipoFisc.setTipo(JTextFieldPad.TP_STRING, 2, 0);
    txtRedFisc.setTipo(JTextFieldPad.TP_DECIMAL, 6, 2);
    txtAliqIPIFisc.setTipo(JTextFieldPad.TP_DECIMAL, 6, 2);
    lcFisc.add(new GuardaCampo(txtCodFisc, 7, 100, 80, 20, "CodFisc", "Código", true, false, txtDescFisc, JTextFieldPad.TP_STRING, false), "txtCodFiscx");
    lcFisc.add(new GuardaCampo(txtDescFisc, 90, 100, 207, 20, "DescFisc", "Descrição", false, false, null, JTextFieldPad.TP_STRING, false), "txtDescFiscx");
    lcFisc.add(new GuardaCampo(txtTipoFisc, 90, 100, 207, 20, "TipoFisc", "Tipo", false, false, null, JTextFieldPad.TP_STRING, false), "txtTipoFiscx");
    lcFisc.add(new GuardaCampo(txtRedFisc, 90, 100, 207, 20, "RedFisc", "Redução", false, false, null, JTextFieldPad.TP_DECIMAL, false), "txtRedFiscx");
    lcFisc.add(new GuardaCampo(txtAliqIPIFisc, 90, 100, 207, 20, "AliqIPIFisc", "% IPI", false, false, null, JTextFieldPad.TP_DECIMAL, false), "txtAliqIPIFiscx");
    lcFisc.montaSql(false, "CLFISCAL", "LF");
    lcFisc.setQueryCommit(false);
    lcFisc.setReadOnly(true);
    txtCodFisc.setTabelaExterna(lcFisc);
    txtDescFisc.setListaCampos(lcFisc);

    txtCodProd.setTipo(JTextFieldPad.TP_INTEGER, 8, 0);
    txtRefProd.setTipo(JTextFieldPad.TP_STRING, 13, 0);
    txtDescProd.setTipo(JTextFieldPad.TP_STRING, 50, 0);
    txtCLoteProd.setTipo(JTextFieldPad.TP_STRING, 1, 0);
    txtCodFisc.setTipo(JTextFieldPad.TP_STRING, 13, 0);
    txtCodBarProd.setTipo(JTextFieldPad.TP_STRING, 13, 0);
    txtCodFabProd.setTipo(JTextFieldPad.TP_STRING, 13, 0);
    txtPercComItCompra.setTipo(JTextFieldPad.TP_DECIMAL, 6, 2);
    lcProd.add(new GuardaCampo(txtCodProd, 7, 100, 80, 20, "CodProd", "Cód.prod.", true, false, txtDescProd, JTextFieldPad.TP_INTEGER, false), "txtCodProdx");
    lcProd.add(new GuardaCampo(txtDescProd, 90, 100, 207, 20, "DescProd", "Descrição do produto", false, false, null, JTextFieldPad.TP_STRING, false), "txtDescProdx");
    lcProd.add(new GuardaCampo(txtRefProd, 90, 100, 207, 20, "RefProd", "Referência", false, false, null, JTextFieldPad.TP_STRING, false), "txtDescProdx");
    lcProd.add(new GuardaCampo(txtCLoteProd, 90, 100, 207, 20, "CLoteProd", "C/Lote", false, false, null, JTextFieldPad.TP_STRING, false), "txtDescProdx");
    lcProd.add(new GuardaCampo(txtCodFisc, 90, 100, 207, 20, "CodFisc", "Cod.Fiscal", false, true, null, JTextFieldPad.TP_STRING, false), "txtCodfiscx");
    lcProd.add(new GuardaCampo(txtCodBarProd, 90, 100, 207, 20, "CodBarProd", "Cod.Barra", false, false, null, JTextFieldPad.TP_STRING, false), "txtCodBarProdx");
    lcProd.add(new GuardaCampo(txtCodFabProd, 90, 100, 207, 20, "CodFabProd", "Cod.Fabricante", false, false, null, JTextFieldPad.TP_STRING, false), "txtCodFabProdx");

    lcProd.setWhereAdic("ATIVOPROD='S'");
    lcProd.montaSql(false, "PRODUTO", "EQ");
    lcProd.setQueryCommit(false);
    lcProd.setReadOnly(true);
    txtCodProd.setTabelaExterna(lcProd);

    lcProd2.add(new GuardaCampo(txtRefProd, 90, 100, 207, 20, "RefProd", "Referência", true, false, txtDescProd, JTextFieldPad.TP_STRING, false), "txtDescProdx");
    lcProd2.add(new GuardaCampo(txtDescProd, 90, 100, 207, 20, "DescProd", "Descrição", false, false, null, JTextFieldPad.TP_STRING, false), "txtDescProdx");
    lcProd2.add(new GuardaCampo(txtCodProd, 7, 100, 80, 20, "CodProd", "Cód.rod.", false, false, null, JTextFieldPad.TP_INTEGER, false), "txtCodProdx");
    lcProd2.add(new GuardaCampo(txtCLoteProd, 90, 100, 207, 20, "CLoteProd", "C/Lote", false, false, null, JTextFieldPad.TP_STRING, false), "txtDescProdx");
    lcProd2.add(new GuardaCampo(txtCodFisc, 90, 100, 207, 20, "CodFisc", "CodFisc", false, true, null, JTextFieldPad.TP_STRING, false), "txtCodfiscx");
    lcProd2.add(new GuardaCampo(txtCodBarProd, 90, 100, 207, 20, "CodBarProd", "Cod.Barra", false, false, null, JTextFieldPad.TP_STRING, false), "txtCodBarProdx");
    lcProd2.add(new GuardaCampo(txtCodFabProd, 90, 100, 207, 20, "CodFabProd", "Cod.Fabricante", false, false, null, JTextFieldPad.TP_STRING, false), "txtCodFabProdx");

    txtRefProd.setNomeCampo("RefProd");
    txtRefProd.setListaCampos(lcDet);
    lcProd2.setWhereAdic("ATIVOPROD='S'");
    lcProd2.montaSql(false, "PRODUTO", "EQ");
    lcProd2.setQueryCommit(false);
    lcProd2.setReadOnly(true);
    txtRefProd.setTabelaExterna(lcProd2);

    txtCodLote.setTipo(JTextFieldPad.TP_STRING, 13, 0);
    txtDescLote.setTipo(JTextFieldPad.TP_DATE, 10, 0);
    lcLote.add(new GuardaCampo(txtCodLote, 7, 100, 80, 20, "CodLote", "Código", true, false, txtDescLote, JTextFieldPad.TP_STRING, false), "txtCodLotex");
    lcLote.add(new GuardaCampo(txtCodProd, 7, 100, 80, 20, "CodProd", "Código do Produto", true, false, txtDescLote, JTextFieldPad.TP_INTEGER, false), "txtCodLotex");
    lcLote.add(new GuardaCampo(txtDescLote, 90, 100, 207, 20, "VenctoLote", "Vencimento", false, false, null, JTextFieldPad.TP_DATE, false), "txtDescLotex");
    //    lcLote.setDinWhereAdic("CODPROD=#N",txtCodProd);
    lcLote.setAutoLimpaPK(false);
    lcLote.montaSql(false, "LOTE", "EQ");
    lcLote.setQueryCommit(false);
    lcLote.setReadOnly(true);
    txtCodLote.setTabelaExterna(lcLote);
    txtDescLote.setListaCampos(lcLote);
    txtDescLote.setNomeCampo("VenctoLote");
    txtDescLote.setLabel("Vencimento");

    txtCodNat.setTipo(JTextFieldPad.TP_STRING, 4, 0);
    txtDescNat.setTipo(JTextFieldPad.TP_STRING, 40, 0);
    lcNat.add(new GuardaCampo(txtCodNat, 7, 100, 80, 20, "CodNat", "CFOP", true, false, txtDescNat, JTextFieldPad.TP_STRING, false), "txtCodNatx");
    lcNat.add(new GuardaCampo(txtDescNat, 90, 100, 207, 20, "DescNat", "Descrição da CFOP", false, false, null, JTextFieldPad.TP_STRING, false), "txtDescNatx");
    lcNat.montaSql(false, "NATOPER", "LF");
    lcNat.setQueryCommit(false);
    lcNat.setReadOnly(true);
    txtCodNat.setTabelaExterna(lcNat);
    txtDescNat.setListaCampos(lcNat);

    txtCodCompra.setTipo(JTextFieldPad.TP_INTEGER, 8, 0);
    txtVlrIPICompra.setTipo(JTextFieldPad.TP_DECIMAL, 15, casasDec);
    txtVlrDescCompra.setTipo(JTextFieldPad.TP_DECIMAL, 15, casasDec);
    txtVlrLiqCompra.setTipo(JTextFieldPad.TP_DECIMAL, 15, casasDec);
    txtVlrBrutCompra.setTipo(JTextFieldPad.TP_DECIMAL, 15, casasDec);
    lcCompra2.add(new GuardaCampo(txtCodCompra, 7, 100, 80, 20, "CodCompra", "Código", true, false, null, JTextFieldPad.TP_INTEGER, false), "txtCodComprax");
    lcCompra2.add(new GuardaCampo(txtVlrIPICompra, 7, 100, 80, 20, "VlrIPICompra", "IPI", false, false, null, JTextFieldPad.TP_DECIMAL, false), "txtVlrIPIComprax");
    lcCompra2.add(new GuardaCampo(txtVlrDescCompra, 7, 100, 80, 20, "VlrDescItCompra", "Desconto", false, false, null, JTextFieldPad.TP_DECIMAL, false), "txtVlrDescComprax");
    lcCompra2.add(new GuardaCampo(txtVlrLiqCompra, 7, 100, 80, 20, "VlrLiqCompra", "Geral", false, false, null, JTextFieldPad.TP_DECIMAL, false), "txtVlrLiqComprax");
    lcCompra2.add(new GuardaCampo(txtVlrBrutCompra, 7, 100, 80, 20, "VlrProdCompra", "Geral", false, false, null, JTextFieldPad.TP_DECIMAL, false), "txtVlrBrutComprax");
    lcCompra2.montaSql(false, "COMPRA", "CP");
    lcCompra2.setQueryCommit(false);
    lcCompra2.setReadOnly(true);

    btFechaCompra.setToolTipText("Fechar a Compra (F4)");

    txtVlrIPICompra.setAtivo(false);
    txtVlrDescCompra.setAtivo(false);
    txtVlrLiqCompra.setAtivo(false);

    pinCab = new Painel(740, 130);
    setListaCampos(lcCampos);
    setAltCab(130);
    setPainel(pinCab, pnCliCab);
    adicCampo(txtCodCompra, 7, 20, 100, 20, "CodCompra", "Nº Compra", JTextFieldPad.TP_INTEGER, 8, 0, true, false, null, true);
    adicCampo(txtCodTipoMov, 110, 20, 77, 20, "CodTipoMov", "Cód.tp.mov.", JTextFieldPad.TP_INTEGER, 8, 0, false, true, txtDescTipoMov, true);
    adicDescFK(txtDescTipoMov, 190, 20, 207, 20, "DescTipoMov", "Descrição do tipo de movimento", JTextFieldPad.TP_STRING, 40, 0);
    adicCampo(txtSerieCompra, 400, 20, 77, 20, "Serie", "Série", JTextFieldPad.TP_STRING, 4, 0, false, true, null, true);
    adicCampo(txtDocCompra, 480, 20, 77, 20, "DocCompra", "Doc", JTextFieldPad.TP_INTEGER, 8, 0, false, false, null, true);
    adicCampo(txtDtEmitCompra, 560, 20, 100, 20, "DtEmitCompra", "Data emissão", JTextFieldPad.TP_DATE, 10, 0, false, false, null, true);
    adicCampo(txtDtEntCompra, 7, 60, 100, 20, "DtEntCompra", "Data entrada", JTextFieldPad.TP_DATE, 10, 0, false, false, null, true);
    adicCampo(txtCodFor, 110, 60, 77, 20, "CodFor", "Cód.for.", JTextFieldPad.TP_INTEGER, 8, 0, false, true, txtDescFor, true);
    adicDescFK(txtDescFor, 190, 60, 207, 20, "RazFor", "Razão social do fornecedor", JTextFieldPad.TP_STRING, 50, 0);
    adicCampo(txtCodPlanoPag, 400, 60, 77, 20, "CodPlanoPag", "Cód.p.pag.", JTextFieldPad.TP_INTEGER, 8, 0, false, true, txtDescPlanoPag, true);
    adicDescFK(txtDescPlanoPag, 480, 60, 180, 20, "DescPlanoPag", "Descrição do plano de pag.", JTextFieldPad.TP_STRING, 40, 0);
    adicCampoInvisivel(txtStatusCompra, "StatusCompra", "Status", JTextFieldPad.TP_STRING, 2, 0, false, false, null, false);
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

    btImp.addActionListener(this);
    btPrevimp.addActionListener(this);

  }
  private void montaDetalhe() {
    setAltDet(100);
    pinDet = new Painel(740, 100);
    setPainel(pinDet, pnDet);
    setListaCampos(lcDet);
    setNavegador(navRod);
    adicCampo(txtCodItCompra, 7, 20, 30, 20, "CodItCompra", "Item", JTextFieldPad.TP_INTEGER, 8, 0, true, false, null, true);
    if (comRef()) {
      adicCampoInvisivel(txtCodProd, "CodProd", "Cód. Produto", JTextFieldPad.TP_INTEGER, 8, 0, false, true, txtDescProd, false);
      adicCampoInvisivel(txtRefProd, "RefProd", "Referência", JTextFieldPad.TP_STRING, 13, 0, false, true, null, false);
      adic(new JLabel("Referência"), 40, 0, 67, 20);
      adic(txtRefProd, 40, 20, 67, 20);
    }
    else {
      adicCampo(txtCodProd, 40, 20, 67, 20, "CodProd", "Cód.prod.", JTextFieldPad.TP_INTEGER, 8, 0, false, true, txtDescProd, false);
    }
    txtCustoItCompra.setSoLeitura(true);
    adicDescFK(txtDescProd, 110, 20, 197, 20, "DescProd", "Descrição do produto", JTextFieldPad.TP_STRING, 50, 0);
    adicCampo(txtCodLote, 310, 20, 67, 20, "CodLote", "Lote", JTextFieldPad.TP_STRING, 13, 0, false, true, txtDescLote, false);
    adicCampo(txtQtdItCompra, 380, 20, 67, 20, "QtdItCompra", "Qtd.", JTextFieldPad.TP_DECIMAL, 15, casasDec, false, false, null, true);
    adicCampo(txtPrecoItCompra, 450, 20, 67, 20, "PrecoItCompra", "Preço", JTextFieldPad.TP_DECIMAL, 15, casasDec, false, false, null, true);
    adicCampo(txtPercDescItCompra, 520, 20, 57, 20, "PercDescItCompra", "% Desc.", JTextFieldPad.TP_DECIMAL, 6, casasDec, false, false, null, false);
    adicCampo(txtVlrDescItCompra, 580, 20, 67, 20, "VlrDescItCompra", "V. Desc.", JTextFieldPad.TP_DECIMAL, 15, casasDec, false, false, null, false);
    adicCampo(txtCustoItCompra, 650, 20, 85, 20, "CustoItCompra", "Custo Estoq.", JTextFieldPad.TP_DECIMAL, 15, casasDec, false, false, null, false);
    adicCampo(txtCodNat, 7, 60, 67, 20, "CodNat", "CFOP", JTextFieldPad.TP_STRING, 4, 0, false, true, txtDescNat, true);
    adicDescFK(txtDescNat, 80, 60, 197, 20, "DescNat", "Descrição da CFOP", JTextFieldPad.TP_STRING, 40, 0);
    adicCampo(txtBaseICMSItCompra, 280, 60, 67, 20, "VlrBaseICMSItCompra", "B. ICMS", JTextFieldPad.TP_DECIMAL, 15, casasDec, false, false, null, false);
    adicCampo(txtPercICMSItCompra, 350, 60, 57, 20, "PercICMSItCompra", "% ICMS", JTextFieldPad.TP_DECIMAL, 6, 2, false, false, null, true);
    adicCampo(txtVlrICMSItCompra, 410, 60, 67, 20, "VlrICMSItCompra", "V. ICMS", JTextFieldPad.TP_DECIMAL, 6, casasDec, false, false, null, false);
    adicCampoInvisivel(txtBaseIPIItCompra, "VlrBaseIPIItCompra", "B. IPI", JTextFieldPad.TP_DECIMAL, 15, casasDec, false, false, null, false);
    adicCampoInvisivel(txtAliqIPIItCompra, "PercIPIItCompra", "% IPI", JTextFieldPad.TP_DECIMAL, 6, casasDec, false, false, null, false);
    adicCampo(txtVlrIPIItCompra, 480, 60, 67, 20, "VlrIPIItCompra", "V. IPI", JTextFieldPad.TP_DECIMAL, 15, casasDec, false, false, null, false);
    adicCampoInvisivel(txtVlrProdItCompra, "VlrProdItCompra", "V. Bruto", JTextFieldPad.TP_DECIMAL, 15, casasDec, false, false, null, false);
    adicCampo(txtVlrLiqItCompra, 550, 60, 100, 20, "VlrLiqItCompra", "Valor Item", JTextFieldPad.TP_DECIMAL, 15, casasDec, false, false, null, false);
    pinTot.adic(new JLabel("Tot. IPI"), 7, 0, 120, 20);
    pinTot.adic(txtVlrIPICompra, 7, 20, 120, 20);
    pinTot.adic(new JLabel("Tot. Desc."), 7, 40, 120, 20);
    pinTot.adic(txtVlrDescCompra, 7, 60, 120, 20);
    pinTot.adic(new JLabel("Total Geral"), 7, 80, 120, 20);
    pinTot.adic(txtVlrLiqCompra, 7, 100, 120, 20);
    txtCodNat.setStrMascara("#.###");

    txtRefProd.addKeyListener(new KeyAdapter() {
      public void keyPressed(KeyEvent kevt) {
        lcDet.edit();
      }
    });

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
    double deVlrProd = Funcoes.arredDouble( 
        txtVlrProdItCompra.doubleValue()-
        txtVlrDescItCompra.doubleValue()+
        txtVlrIPIItCompra.doubleValue(),
        casasDec);
    txtVlrLiqItCompra.setVlrBigDecimal(new BigDecimal(deVlrProd));
  }
  private void calcImpostos(boolean bCalcBase) {
    double deRed = txtRedFisc.doubleValue();
    double deVlrProd = Funcoes.arredDouble(txtVlrProdItCompra.doubleValue()-txtVlrDescItCompra.doubleValue(),casasDec);
    double deBaseICMS = Funcoes.arredDouble(txtBaseICMSItCompra.doubleValue(),casasDec);
    double deBaseIPI = txtBaseIPIItCompra.doubleValue();
    double deICMS = 0;
    //    System.out.println("Red: "+bRed);
    //    System.out.println("VlrProd: "+bVlrProd);
    if (deVlrProd > 0) {
      if (bCalcBase) {
        deBaseICMS = Funcoes.arredDouble(deVlrProd - (deVlrProd * deRed / 100), casasDec);
        deBaseIPI = deVlrProd;
      }
      deICMS = Funcoes.arredDouble(deBaseICMS * txtPercICMSItCompra.doubleValue()/100, casasDec);
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
    txtVlrProdItCompra.setVlrBigDecimal( new BigDecimal(Funcoes.arredDouble(dePreco*deQtd,casasDec)));
    //    System.out.println("VlrProdTot: "+txtVlrProdItCompra.getVlrBigDecimal());
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
    }
    catch (SQLException err) {
      Funcoes.mensagemErro(this, "Erro ao confirmar código da Compra!\n"
          + err.getMessage());
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
    }
    catch (SQLException err) {
      Funcoes.mensagemErro(this, "Erro ao buscar natureza da operação!\n" + err);
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
    }
    catch (SQLException err) {
      Funcoes.mensagemErro(this, "Erro ao consultar a tabela EQLOTE!\n"
          + err.getMessage());
    }
    if (!bValido) {
      DLLote dl = new DLLote(this, txtCodLote.getText(), txtCodProd.getText(), txtDescProd.getText(), con);
      dl.setVisible(true);
      if (dl.OK) {
        bRetorno = true;
        txtCodLote.setVlrString(dl.getValor());
        lcLote.carregaDados();
      }
      dl.dispose();
    }
    else {
      bRetorno = true;
    }
    return bRetorno;
  }
  public void focusGained(FocusEvent fevt) {}
  public void focusLost(FocusEvent fevt) {
    if (fevt.getSource() == txtPercDescItCompra) {
      if (txtPercDescItCompra.getText().trim().length() < 1) {
        txtVlrDescItCompra.setAtivo(true);
      }
      else {
        txtVlrDescItCompra.setVlrBigDecimal(
                new BigDecimal(Funcoes.arredDouble(txtVlrProdItCompra.doubleValue()*
                      txtPercDescItCompra.doubleValue()/100,casasDec))
                );
        calcVlrProd();
        calcImpostos(true);
        txtVlrDescItCompra.setAtivo(false);
      }
    }
    else if (fevt.getSource() == txtVlrIPIItCompra) {
      adicIPI();
    }
    else if (fevt.getSource() == txtVlrDescItCompra) {
      if (txtVlrDescItCompra.getText().trim().length() < 1) {
        txtPercDescItCompra.setAtivo(true);
      }
      else if (txtVlrDescItCompra.getAtivo()) {
        txtPercDescItCompra.setAtivo(false);
      }
    }
    else if ((fevt.getSource() == txtQtdItCompra)
        || (fevt.getSource() == txtPrecoItCompra)
        || (fevt.getSource() == txtCodNat)) {
      calcVlrProd();
      calcImpostos(true);
    }
    else if (fevt.getSource() == txtPercICMSItCompra) {
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
        txtPercICMSItCompra.setVlrBigDecimal(new BigDecimal(rs.getString(1)));
      }
      calcImpostos(true);
    }
    catch (SQLException err) {
      Funcoes.mensagemErro(this, "Erro ao buscar percentual de ICMS!\n"
          + err.getMessage());
    }
  }
  public void beforeCarrega(CarregaEvent cevt) {
    if (lcDet.getStatus() != ListaCampos.LCS_INSERT) {
      if (cevt.getListaCampos() == lcProd) {
        lcProd.cancLerCampo(4, true); //Código da Classificação Fiscal
      }
    }
    else {
      if (cevt.getListaCampos() == lcProd) {
        lcProd.cancLerCampo(4, false); //Código da Classificação Fiscal
      }
    }
    if (cevt.getListaCampos() == lcLote) {
      if (txtCodLote.getText().trim().length() == 0) {
        cevt.cancela(); //Cancela o carregaDados do lcLote para não zerar o
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
        txtCodLote.setAtivo(false);//Desativa o Cógigo do lote por o produto
                                   // não possuir lote
      }
      else if (txtCLoteProd.getText().trim().equals("S")) {
        txtCodLote.setAtivo(true);//Ativa o Cógigo do Lote pois o produto tem
                                  // lote
      }
    }
    else if ((cevt.getListaCampos() == lcFisc)
        && (lcDet.getStatus() == ListaCampos.LCS_INSERT)) {
      buscaNat();
    }
    else if (cevt.getListaCampos() == lcDet) {
      String s = txtCodCompra.getText();
      lcCompra2.carregaDados(); //Carrega os Totais
      txtCodCompra.setVlrString(s);
    }
    else if (cevt.getListaCampos() == lcCampos) {
      String s = txtCodCompra.getText();
      lcCompra2.carregaDados(); //Carrega os Totais
      txtCodCompra.setVlrString(s);
    }
    else if (cevt.getListaCampos() == lcSerie) {
      if (lcCampos.getStatus() == ListaCampos.LCS_INSERT)
        txtDocCompra.setVlrInteger(new Integer(txtDocCompra.getVlrInteger().intValue() + 1));
    }
    else if (cevt.getListaCampos() == lcNat) {
      if ((cevt.ok) & (lcDet.getStatus() == ListaCampos.LCS_INSERT)) {
        buscaICMS();
      }
    }
  }
  public void keyPressed(KeyEvent kevt) {
    if (kevt.getKeyCode() == KeyEvent.VK_ENTER) {
      if (kevt.getSource() == txtCodPlanoPag) {//Talvez este possa ser o ultimo
                                               // campo do itvenda.
        if (lcCampos.getStatus() == ListaCampos.LCS_INSERT) {
          lcCampos.post();
          lcDet.insert(true);
          txtRefProd.requestFocus();
        }
        else if (lcDet.getStatus() == ListaCampos.LCS_EDIT) {
          lcCampos.post();
          txtCodItCompra.requestFocus();
        }
      }
      else if (kevt.getSource() == txtVlrLiqItCompra) {//Talvez este possa ser
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
        }
        else if (lcDet.getStatus() == ListaCampos.LCS_EDIT) {
          lcDet.post();
          txtCodItCompra.requestFocus();
        }
      }
    }
    else if (kevt.getKeyCode() == KeyEvent.VK_F4) {
      btFechaCompra.doClick();
    }
    super.keyPressed(kevt);
  }
  public void actionPerformed(ActionEvent evt) {
    String[] sValores = null;
    if (evt.getSource() == btFechaCompra) {
      DLFechaCompra dl = new DLFechaCompra(con, txtCodCompra.getVlrInteger(), this);
      dl.setVisible(true);
      if (dl.OK) {
        sValores = dl.getValores();
        dl.dispose();
      }
      else {
        dl.dispose();
      }
      lcCampos.carregaDados();
      if (sValores != null) {
        lcCampos.edit();
        if (sValores[3].equals("S")) {
          imprimir(true, txtCodCompra.getVlrInteger().intValue());
          //          if (JOptionPane.showConfirmDialog(null, "Pedido OK?", "Freedom",
          // JOptionPane.YES_NO_OPTION)==0 ) {
          //            txtStatusCompra.setVlrString("P4");
          //          }
        }
        lcCampos.post();
      }
    }
    else if (evt.getSource() == btPrevimp)
      imprimir(true, txtCodCompra.getVlrInteger().intValue());
    else if (evt.getSource() == btImp)
      imprimir(false, txtCodCompra.getVlrInteger().intValue());
    super.actionPerformed(evt);
  }
  private void imprimir(boolean bVisualizar, int iCodCompra) {
    ImprimeOS imp = new ImprimeOS("", con);
    DLRPedido dl = new DLRPedido(sOrdNota);
    dl.setVisible(true);
    if (dl.OK == false) {
      dl.dispose();
      return;
    }
    imp.verifLinPag();
    imp.setTitulo("Relatório de Pedidos de Compras");
    String sSQL = "SELECT (SELECT COUNT(IC.CODITCOMPRA) FROM CPITCOMPRA IC WHERE IC.CODCOMPRA=C.CODCOMPRA" +
    		" AND IC.CODEMP=C.CODEMP AND IC.CODFILIAL=C.CODFILIAL),"
        + "C.CODCOMPRA,C.CODFOR,F.RAZFOR,F.CNPJFOR,F.CPFFOR,C.DTEMITCOMPRA,F.ENDFOR,"
        + "F.BAIRFOR,F.CEPFOR,C.DTENTCOMPRA,F.CIDFOR,F.UFFOR,F.FONEFOR,"
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
        + " ORDER BY C.CODCOMPRA,P."
        + dl.getValor() + ";";

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
          imp.say(imp.pRow() + 0, 76, rs.getString("CpfFor") != null ? "[ CPF ]" : "[ CNPJ ]");
          imp.say(imp.pRow() + 0, 96, "[ Data de Emissão ]");
          imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
          imp.say(imp.pRow() + 0, 4, rs.getString("CodFor") + " - "
              + rs.getString("RazFor"));
          imp.say(imp.pRow() + 0, 76, rs.getString("CpfFor") != null ? Funcoes.setMascara(rs.getString("CpfFor"), "###.###.###-##") : Funcoes.setMascara(rs.getString("CnpjFor"), "##.###.###/####-##"));
          imp.say(imp.pRow() + 0, 100, Funcoes.sqlDateToStrDate(rs.getDate("DtEmitCompra")));
          imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
          imp.say(imp.pRow() + 0, 4, "[ Endereco ]");
          imp.say(imp.pRow() + 0, 55, "[ Bairro ]");
          imp.say(imp.pRow() + 0, 86, "[ CEP ]");
          imp.say(imp.pRow() + 0, 96, "[ Data de Saída ]");
          imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
          imp.say(imp.pRow() + 0, 4, rs.getString("EndFor"));
          imp.say(imp.pRow() + 0, 55, rs.getString("BairFor"));
          imp.say(imp.pRow() + 0, 86, Funcoes.setMascara(rs.getString("CepFor"), "#####-###"));
          imp.say(imp.pRow() + 0, 100, Funcoes.sqlDateToStrDate(rs.getDate("DtEntCompra")));
          imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
          imp.say(imp.pRow() + 0, 4, "[ Municipio ]");
          imp.say(imp.pRow() + 0, 39, "[ UF ]");
          imp.say(imp.pRow() + 0, 46, "[ Fone/Fax ]");
          imp.say(imp.pRow() + 0, 76, rs.getString("RgFor") != null ? "[ RG ]" : "[ Insc. Est. ]");
          imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
          imp.say(imp.pRow() + 0, 4, rs.getString("CidFor"));
          imp.say(imp.pRow() + 0, 39, rs.getString("UfFor"));
          imp.say(imp.pRow() + 0, 46, Funcoes.setMascara(rs.getString("FoneFor"), "(####)####-####")
              + " - " + Funcoes.setMascara(rs.getString("FaxFor"), "####-####"));
          imp.say(imp.pRow() + 0, 76, rs.getString("RgFor") != null ? rs.getString("RgFor") : rs.getString("CnpjFor"));
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
        imp.say(imp.pRow() + 0, 18, rs.getString("DescProd").substring(0, 39));
        imp.say(imp.pRow() + 0, 56, rs.getString("CodUnid"));
        imp.say(imp.pRow() + 0, 65, "" + rs.getDouble("QtdItCompra"));
        imp.say(imp.pRow() + 0, 72, Funcoes.strDecimalToStrCurrency(14, 2, rs.getString("PrecoItCompra")));
        imp.say(imp.pRow() + 0, 87, Funcoes.strDecimalToStrCurrency(14, 2, rs.getString("VlrProdItCompra")));
        imp.say(imp.pRow() + 0, 102, "" + rs.getDouble("PercICMSItCompra"));
        imp.say(imp.pRow() + 0, 108, "" + rs.getDouble("PercIPIItCompra"));
        imp.say(imp.pRow() + 0, 114, Funcoes.strDecimalToStrCurrency(14, 2, rs.getString("VlrIPIItCompra")));
        imp.say(imp.pRow() + 0, 129, Funcoes.setMascara(rs.getString("CodNat"), "#.###"));
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
            imp.say(imp.pRow() + 0, 4, Funcoes.strDecimalToStrCurrency(14, 2, rs.getString("VlrBaseICMSCompra")));
            imp.say(imp.pRow() + 0, 29, Funcoes.strDecimalToStrCurrency(14, 2, rs.getString("VlrICMSCompra")));
            imp.say(imp.pRow() + 0, 104, Funcoes.strDecimalToStrCurrency(14, 2, rs.getString("VlrProdCompra")));
            imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
            imp.say(imp.pRow() + 0, 4, "[ Valor do Frete ]");
            imp.say(imp.pRow() + 0, 29, "[ Valor do Desconto ]");
            imp.say(imp.pRow() + 0, 54, "[ Outras Despesas ]");
            imp.say(imp.pRow() + 0, 79, "[ Valor do IPI ]");
            imp.say(imp.pRow() + 0, 104, "[ VALOR TOTAL ]");
            imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
            imp.say(imp.pRow() + 0, 29, Funcoes.strDecimalToStrCurrency(14, 2, rs.getString("VlrDescCompra") == null ? rs.getString("VlrDescItCompra") : rs.getString("VlrDescCompra")));
            imp.say(imp.pRow() + 0, 64, Funcoes.strDecimalToStrCurrency(14, 2, rs.getString("VlrAdicCompra")));
            imp.say(imp.pRow() + 0, 79, Funcoes.strDecimalToStrCurrency(14, 2, rs.getString("VlrIPICompra")));
            imp.say(imp.pRow() + 0, 104, Funcoes.strDecimalToStrCurrency(14, 2, rs.getString("VlrLiqCompra")));
            iItImp = 0;
          }
          else {
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
          imp.say(imp.pRow() + 0, (116 - rs.getString("DescPlanoPag").trim().length()) / 2, "FORMA DE PAGAMENTO : "
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
    }
    catch (SQLException err) {
      Funcoes.mensagemErro(this, "Erro ao consultar a tabela de Compra!"
          + err.getMessage());
    }

    if (bVisualizar) {
      imp.preview(this);
    }
    else {
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
      //      rs.close();
      //      ps.close();
      if (!con.getAutoCommit())
        con.commit();

    }
    catch (SQLException err) {
      Funcoes.mensagemErro(this, "Erro ao carregar a tabela PREFERE1!\n"
          + err.getMessage());
    }
    return bRetorno;
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
  public void beforeInsert(InsertEvent ievt) {}
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
  public void execShow(Connection cn) {
    con = cn;
    montaDetalhe();
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
    super.execShow(cn);
  }
}