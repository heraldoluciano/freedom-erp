/**
 * @version 11/02/2002 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)FManutPag.java <BR>
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
 * Tela de manutenção de contas a pagar.
 * 
 */

package org.freedom.modulos.std;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JPanelPad;
import javax.swing.JScrollPane;
import org.freedom.componentes.JTabbedPanePad;
import javax.swing.SwingConstants;

import org.freedom.acao.CarregaEvent;
import org.freedom.acao.CarregaListener;
import org.freedom.bmps.Icone;
import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JRadioGroup;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.Tabela;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FFilho;

public class FManutPag extends FFilho implements ActionListener,KeyListener,CarregaListener {
  private JPanelPad pnLegenda = new JPanelPad(JPanelPad.TP_JPANEL,new GridLayout(0,4));
  private JPanelPad pnTabConsulta = new JPanelPad(JPanelPad.TP_JPANEL,new BorderLayout());
  private JPanelPad pinBotoesConsulta = new JPanelPad(40,100);
  private JPanelPad pnRod = new JPanelPad(JPanelPad.TP_JPANEL,new BorderLayout());
  private JPanelPad pinConsulta = new JPanelPad(500,140);
  private JPanelPad pnConsulta = new JPanelPad(JPanelPad.TP_JPANEL,new BorderLayout());
  private JTabbedPanePad tpn = new JTabbedPanePad();
  private Tabela tabConsulta = new Tabela();
  private JScrollPane spnConsulta = new JScrollPane(tabConsulta);
  private JPanelPad pnTabBaixa = new JPanelPad(JPanelPad.TP_JPANEL,new BorderLayout());
  private JPanelPad pinBotoesBaixa = new JPanelPad(40,100);
  private JPanelPad pinBaixa = new JPanelPad(500,140);
  private JPanelPad pnBaixa = new JPanelPad(JPanelPad.TP_JPANEL,new BorderLayout());
  private Tabela tabBaixa = new Tabela();
  private JScrollPane spnBaixa = new JScrollPane(tabBaixa);
  private JPanelPad pnTabManut = new JPanelPad(JPanelPad.TP_JPANEL,new BorderLayout());
  private JPanelPad pinBotoesManut = new JPanelPad(40,180);
  private JPanelPad pinManut = new JPanelPad(500,100);
  private JPanelPad pnManut = new JPanelPad(JPanelPad.TP_JPANEL,new BorderLayout());
  private Tabela tabManut = new Tabela();
  private JScrollPane spnManut = new JScrollPane(tabManut);
  private JTextFieldPad txtCodFor = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldPad txtCodForManut = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldPad txtPrimCompr = new JTextFieldPad();
  private JTextFieldPad txtUltCompr = new JTextFieldPad();
  private JTextFieldPad txtDataMaxFat = new JTextFieldPad(JTextFieldPad.TP_DATE,10,0);
  private JTextFieldPad txtVlrMaxFat = new JTextFieldPad(JTextFieldPad.TP_DECIMAL,15,2);
  private JTextFieldPad txtVlrTotCompr = new JTextFieldPad(JTextFieldPad.TP_DECIMAL,15,2);
  private JTextFieldPad txtVlrTotPago = new JTextFieldPad(JTextFieldPad.TP_DECIMAL,15,2);
  private JTextFieldPad txtVlrTotAberto = new JTextFieldPad(JTextFieldPad.TP_DECIMAL,15,2);
  private JTextFieldPad txtDataMaxAcum = new JTextFieldPad(JTextFieldPad.TP_DATE,10,0);
  private JTextFieldPad txtVlrMaxAcum = new JTextFieldPad(JTextFieldPad.TP_DECIMAL,15,2);
  private JTextFieldPad txtCodPagBaixa = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldPad txtDoc = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldPad txtSerie = new JTextFieldPad(JTextFieldPad.TP_STRING,4,0);
  private JTextFieldPad txtCodCompraBaixa = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldPad txtCodForBaixa = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldPad txtDtEmisBaixa = new JTextFieldPad(JTextFieldPad.TP_DATE,10,0);
  private JTextFieldPad txtCodBancoBaixa = new JTextFieldPad(JTextFieldPad.TP_STRING,3,0);
  private JTextFieldPad txtTotPagBaixa = new JTextFieldPad(JTextFieldPad.TP_DECIMAL,15,2);
  private JTextFieldPad txtTotAbertoBaixa = new JTextFieldPad(JTextFieldPad.TP_DECIMAL,15,2);
  private JTextFieldPad txtTotPagoBaixa = new JTextFieldPad(JTextFieldPad.TP_DECIMAL,15,2);
  private JTextFieldPad txtJurosBaixa = new JTextFieldPad(JTextFieldPad.TP_DECIMAL,15,2);
  private JTextFieldPad txtDatainiManut = new JTextFieldPad(JTextFieldPad.TP_DATE,10,0);
  private JTextFieldPad txtDatafimManut = new JTextFieldPad(JTextFieldPad.TP_DATE,10,0);
  private JTextFieldFK txtRazFor = new JTextFieldFK(JTextFieldPad.TP_STRING,40,0);
  private JTextFieldFK txtRazForManut = new JTextFieldFK(JTextFieldPad.TP_STRING,40,0);
  private JTextFieldFK txtRazForBaixa = new JTextFieldFK(JTextFieldPad.TP_STRING,40,0);
  private JTextFieldFK txtDescBancoBaixa = new JTextFieldFK(JTextFieldPad.TP_STRING,40,0);
  private JButton btBaixaConsulta = new JButton(Icone.novo("btOk.gif"));
  private JButton btBaixaManut = new JButton(Icone.novo("btOk.gif"));
  private JButton btEditManut = new JButton(Icone.novo("btEditar.gif"));
  private JButton btNovoManut = new JButton(Icone.novo("btNovo.gif"));
  private JButton btExcluirManut = new JButton(Icone.novo("btExcluir.gif"));
  private JButton btEstManut = new JButton(Icone.novo("btCancelar.gif"));
  private JButton btExecManut = new JButton(Icone.novo("btExecuta.gif"));
  private JButton btBaixa = new JButton(Icone.novo("btOk.gif"));
  private JButton btSair = new JButton("Sair",Icone.novo("btSair.gif"));
  private ListaCampos lcFor = new ListaCampos(this);
  private ListaCampos lcForBaixa = new ListaCampos(this);
  private ListaCampos lcForManut = new ListaCampos(this);
  private ListaCampos lcCompraBaixa = new ListaCampos(this);
  private ListaCampos lcPagBaixa = new ListaCampos(this);
  private ListaCampos lcBancoBaixa = new ListaCampos(this);
  private Vector vCodPag = new Vector();
  private Vector vNParcPag = new Vector();
  private Vector vNParcBaixa = new Vector();  
  private Date dIniManut = null;
  private Date dFimManut = null;
  private Vector vNumContas = new Vector();
  private Vector vCodPlans = new Vector();
  private Vector vCodCCs = new Vector();
  private Vector vDtEmiss = new Vector();
  private JRadioGroup rgData = null;
  private Vector vValsData = new Vector();
  private Vector vLabsData = new Vector();
  private JRadioGroup rgVenc = null;
  private Vector vValsVenc = new Vector();
  private Vector vLabsVenc = new Vector();  
  private JRadioGroup rgPg = null;
  private Vector vValsPg = new Vector();
  private Vector vLabsPg = new Vector();  
  private ImageIcon imgVencido = Icone.novo("clVencido.gif");
  private ImageIcon imgPago = Icone.novo("clPago.gif");
  private ImageIcon imgPagoParcial = Icone.novo("clPagoParcial.gif");
  private ImageIcon imgNaoVencido = Icone.novo("clNaoVencido.gif");    
  private ImageIcon imgColuna = null;
  
  int iCodPag = 0;
  int iNParcPag = 0;
  int iAnoCC = 0;
  public FManutPag() {
    setTitulo("Manutenção de contas a pagar");
    setAtribos(20,20,740,390);
    
    Container c = getContentPane();    
    c.setLayout(new BorderLayout());
    c.add(pnRod,BorderLayout.SOUTH);
    c.add(tpn,BorderLayout.CENTER);
    btSair.setPreferredSize(new Dimension(100,30));
    
	pnLegenda.add(new JLabelPad("Vencido",imgVencido,SwingConstants.CENTER));
	pnLegenda.add(new JLabelPad("Parcial",imgPagoParcial,SwingConstants.CENTER));
	pnLegenda.add(new JLabelPad("Pago",imgPago,SwingConstants.CENTER));
	pnLegenda.add(new JLabelPad("À vencer",imgNaoVencido,SwingConstants.CENTER));

	pnRod.setBorder(BorderFactory.createEtchedBorder());
	pnRod.setPreferredSize(new Dimension(500,32));

	pnRod.add(btSair,BorderLayout.EAST);
	pnRod.add(pnLegenda,BorderLayout.WEST);

    btSair.addActionListener(this);
    //Consulta:    

    lcFor.add(new GuardaCampo( txtCodFor, "CodFor", "Cód.for.", ListaCampos.DB_PK, false));
    lcFor.add(new GuardaCampo( txtRazFor, "RazFor", "Descrição do fornecedor", ListaCampos.DB_SI, false));
    lcFor.montaSql(false, "FORNECED", "CP");
    lcFor.setQueryCommit(false);
    lcFor.setReadOnly(true);
    txtCodFor.setTabelaExterna(lcFor);
    txtCodFor.setFK(true);
    txtCodFor.setNomeCampo("CodFor");

    txtPrimCompr.setAtivo(false);
    txtUltCompr.setAtivo(false);
    txtDataMaxFat.setAtivo(false);
    txtVlrMaxFat.setAtivo(false);
    txtVlrTotCompr.setAtivo(false);
    txtVlrTotPago.setAtivo(false);
    txtVlrTotAberto.setAtivo(false);
    txtDataMaxAcum.setAtivo(false);
    txtVlrMaxAcum.setAtivo(false);
    
    Funcoes.setBordReq(txtCodFor);
    
    tpn.addTab("Consulta",pnConsulta);
    
    btBaixaConsulta.setToolTipText("Baixa");  
        
    pnConsulta.add(pinConsulta,BorderLayout.NORTH);    
    pnTabConsulta.add(pinBotoesConsulta,BorderLayout.EAST);
    pnTabConsulta.add(spnConsulta,BorderLayout.CENTER);    
    pnConsulta.add(pnTabConsulta,BorderLayout.CENTER);
    
    pinConsulta.adic(new JLabelPad("Cód.for."),7,0,250,20);
    pinConsulta.adic(txtCodFor,7,20,80,20);
    pinConsulta.adic(new JLabelPad("Descrição do fornecedor"),90,0,250,20);
    pinConsulta.adic(txtRazFor,90,20,197,20);
    pinConsulta.adic(new JLabelPad("C. compra"),290,0,97,20);
    pinConsulta.adic(txtPrimCompr,290,20,97,20);
    pinConsulta.adic(new JLabelPad("U. compra"),390,0,100,20);
    pinConsulta.adic(txtUltCompr,390,20,120,20);
    pinConsulta.adic(new JLabelPad("Data"),7,40,200,20);
    pinConsulta.adic(txtDataMaxFat,7,60,100,20);
    pinConsulta.adic(new JLabelPad("Valor da maior fatura"),110,40,200,20);
    pinConsulta.adic(txtVlrMaxFat,110,60,137,20);
    pinConsulta.adic(new JLabelPad("Data"),250,40,200,20);
    pinConsulta.adic(txtDataMaxAcum,250,60,97,20);
    pinConsulta.adic(new JLabelPad("Valor do maior acumulo"),350,40,200,20);
    pinConsulta.adic(txtVlrMaxAcum,350,60,160,20);
    pinConsulta.adic(new JLabelPad("Total de compras"),7,80,150,20);
    pinConsulta.adic(txtVlrTotCompr,7,100,150,20);
    pinConsulta.adic(new JLabelPad("Total pago"),160,80,97,20);
    pinConsulta.adic(txtVlrTotPago,160,100,97,20);
    pinConsulta.adic(new JLabelPad("Total em aberto"),260,80,117,20);
    pinConsulta.adic(txtVlrTotAberto,260,100,117,20);    
    pinBotoesConsulta.adic(btBaixaConsulta,5,10,30,30);
    
    tabConsulta.adicColuna("Vencimento");
    tabConsulta.adicColuna("Série");
    tabConsulta.adicColuna("Doc.");
    tabConsulta.adicColuna("Cód. compra");
    tabConsulta.adicColuna("Data da compra");
    tabConsulta.adicColuna("Valor");
    tabConsulta.adicColuna("Data pagamento");
    tabConsulta.adicColuna("Valor pago");
    tabConsulta.adicColuna("Atraso");
    tabConsulta.adicColuna("Observações");
    tabConsulta.adicColuna("Banco");
    
    tabConsulta.setTamColuna(90,0);
    tabConsulta.setTamColuna(50,1);
    tabConsulta.setTamColuna(50,2);
    tabConsulta.setTamColuna(90,3);
    tabConsulta.setTamColuna(110,4);
    tabConsulta.setTamColuna(90,5);
    tabConsulta.setTamColuna(110,6);
    tabConsulta.setTamColuna(100,7);
    tabConsulta.setTamColuna(60,8);
    tabConsulta.setTamColuna(100,9);
    tabConsulta.setTamColuna(80,10);
    
    //Baixa:    


    lcCompraBaixa.add(new GuardaCampo( txtCodCompraBaixa, "CodCompra", "Cód.compra", ListaCampos.DB_PK, false));
    lcCompraBaixa.add(new GuardaCampo( txtSerie, "Serie", "Série", ListaCampos.DB_SI, false));
    lcCompraBaixa.montaSql(false, "COMPRA", "CP");
    lcCompraBaixa.setQueryCommit(false);
    lcCompraBaixa.setReadOnly(true);

    txtCodCompraBaixa.setTabelaExterna(lcCompraBaixa);
    txtCodCompraBaixa.setFK(true);
    txtCodCompraBaixa.setNomeCampo("CodCompra");

    lcForBaixa.add(new GuardaCampo( txtCodForBaixa, "CodFor", "Cód.for", ListaCampos.DB_PK, false));
    lcForBaixa.add(new GuardaCampo( txtRazForBaixa, "RazFor", "Razão social do fornecedor", ListaCampos.DB_SI, false));
    lcForBaixa.montaSql(false, "FORNECED", "CP");
    lcForBaixa.setQueryCommit(false);
    lcForBaixa.setReadOnly(true);

    txtCodForBaixa.setTabelaExterna(lcForBaixa);
    txtCodForBaixa.setFK(true);
    txtCodForBaixa.setNomeCampo("CodFor");


    lcBancoBaixa.add(new GuardaCampo( txtCodBancoBaixa, "CodBanco", "Cód.banco", ListaCampos.DB_PK, false));
    lcBancoBaixa.add(new GuardaCampo( txtDescBancoBaixa, "NomeBanco", "Nome banco", ListaCampos.DB_SI, false));
    lcBancoBaixa.montaSql(false, "BANCO", "FN");
    lcBancoBaixa.setQueryCommit(false);
    lcBancoBaixa.setReadOnly(true);

    txtCodBancoBaixa.setTabelaExterna(lcBancoBaixa);
    txtCodBancoBaixa.setFK(true);
    txtCodBancoBaixa.setNomeCampo("CodBanco");

    lcPagBaixa.add(new GuardaCampo( txtCodPagBaixa, "CodPag", "Cód.pag", ListaCampos.DB_PK, false));
    lcPagBaixa.add(new GuardaCampo( txtCodCompraBaixa, "CodCompra", "Cód.compra", ListaCampos.DB_FK, false));
    lcPagBaixa.add(new GuardaCampo( txtDoc, "DocPag", "Doc.", ListaCampos.DB_SI, false));
    lcPagBaixa.add(new GuardaCampo( txtTotPagBaixa, "VlrPag", "Total pag.", ListaCampos.DB_SI, false));
    lcPagBaixa.add(new GuardaCampo( txtCodForBaixa, "CodFor", "Cód.for", ListaCampos.DB_FK, false));
    lcPagBaixa.add(new GuardaCampo( txtDtEmisBaixa, "DataPag", "Data emis.", ListaCampos.DB_SI, false));
    lcPagBaixa.add(new GuardaCampo( txtCodBancoBaixa, "CodBanco", "Cód.banco", ListaCampos.DB_FK, false));
    lcPagBaixa.add(new GuardaCampo( txtTotAbertoBaixa, "VlrApagPag", "Total aberto", ListaCampos.DB_SI, false));
    lcPagBaixa.add(new GuardaCampo( txtTotPagoBaixa, "VlrPagoPag", "Total pago", ListaCampos.DB_SI, false));
    lcPagBaixa.add(new GuardaCampo( txtJurosBaixa, "VlrJurosPag", "Total juros", ListaCampos.DB_SI, false));
    lcPagBaixa.montaSql(false,"PAGAR", "FN");
    lcPagBaixa.setQueryCommit(false);
    lcPagBaixa.setReadOnly(true);

    txtCodPagBaixa.setTabelaExterna(lcPagBaixa);
    txtCodPagBaixa.setFK(true);
    txtCodPagBaixa.setNomeCampo("CodPag");
    txtDoc.setAtivo(false);
    txtCodCompraBaixa.setAtivo(false);
    txtSerie.setAtivo(false);
    txtCodForBaixa.setAtivo(false);
    txtDtEmisBaixa.setAtivo(false);
    txtCodBancoBaixa.setAtivo(false);
    txtTotPagBaixa.setAtivo(false);
    txtTotAbertoBaixa.setAtivo(false);
    txtTotPagoBaixa.setAtivo(false);
    txtJurosBaixa.setAtivo(false);
    
    Funcoes.setBordReq(txtCodPagBaixa);
    
    tpn.addTab("Baixa",pnBaixa);
    
    btBaixa.setToolTipText("Baixa");  

    pnBaixa.add(pinBaixa,BorderLayout.NORTH);    
    pnTabBaixa.add(pinBotoesBaixa,BorderLayout.EAST);
    pnTabBaixa.add(spnBaixa,BorderLayout.CENTER);    
    pnBaixa.add(pnTabBaixa,BorderLayout.CENTER);
    
    pinBaixa.adic(new JLabelPad("Cód.pag"),7,0,80,20);
    pinBaixa.adic(txtCodPagBaixa,7,20,80,20);
    pinBaixa.adic(new JLabelPad("Doc."),90,0,77,20);
    pinBaixa.adic(txtDoc,90,20,77,20);
    pinBaixa.adic(new JLabelPad(" -"),170,20,7,20);
    pinBaixa.adic(new JLabelPad("Série"),180,0,50,20);
    pinBaixa.adic(txtSerie,180,20,50,20);
    pinBaixa.adic(new JLabelPad("Pedido"),240,0,77,20);
    pinBaixa.adic(txtCodCompraBaixa,240,20,77,20);
    pinBaixa.adic(new JLabelPad("Cód.for."),7,40,250,20);
    pinBaixa.adic(txtCodForBaixa,7,60,80,20);
    pinBaixa.adic(new JLabelPad("Descrição do fornecedor"),90,40,250,20);
    pinBaixa.adic(txtRazForBaixa,90,60,197,20);
    pinBaixa.adic(new JLabelPad("Cód.banco"),290,40,250,20);
    pinBaixa.adic(txtCodBancoBaixa,290,60,67,20);
    pinBaixa.adic(new JLabelPad("Descrição do banco"),360,40,250,20);
    pinBaixa.adic(txtDescBancoBaixa,360,60,150,20);
    pinBaixa.adic(new JLabelPad("Data de emis."),7,80,100,20);
    pinBaixa.adic(txtDtEmisBaixa,7,100,100,20);
    pinBaixa.adic(new JLabelPad("Total a pagar"),110,80,97,20);
    pinBaixa.adic(txtTotPagBaixa,110,100,97,20);
    pinBaixa.adic(new JLabelPad("Total pago"),210,80,97,20);
    pinBaixa.adic(txtTotPagoBaixa,210,100,97,20);
    pinBaixa.adic(new JLabelPad("Total em aberto"),310,80,107,20);
    pinBaixa.adic(txtTotAbertoBaixa,310,100,107,20);
    pinBaixa.adic(new JLabelPad("Juros"),420,80,80,20);
    pinBaixa.adic(txtJurosBaixa,420,100,90,20);

    pinBotoesBaixa.adic(btBaixa,5,10,30,30);
    
    tabBaixa.adicColuna("Vencimento");
    tabBaixa.adicColuna("Status");
    tabBaixa.adicColuna("Nº de parcelas");
    tabBaixa.adicColuna("Doc.");
    tabBaixa.adicColuna("Pedido");
    tabBaixa.adicColuna("Valor da parcela");
    tabBaixa.adicColuna("Data pagamento");
    tabBaixa.adicColuna("Valor pago");
    tabBaixa.adicColuna("Valor desconto");
    tabBaixa.adicColuna("Valor juros");
    tabBaixa.adicColuna("Valor aberto");
    tabBaixa.adicColuna("Conta");
    tabBaixa.adicColuna("Categoria");
	tabBaixa.adicColuna("Centro de custo");
    tabBaixa.adicColuna("Observação");    
    tabBaixa.setTamColuna(110,0);
    tabBaixa.setTamColuna(50,1);
    tabBaixa.setTamColuna(120,2);
    tabBaixa.setTamColuna(50,3);
    tabBaixa.setTamColuna(70,4);
    tabBaixa.setTamColuna(140,5);
    tabBaixa.setTamColuna(110,6);
    tabBaixa.setTamColuna(90,7);
    tabBaixa.setTamColuna(110,8);
    tabBaixa.setTamColuna(100,9);
    tabBaixa.setTamColuna(100,10);
    tabBaixa.setTamColuna(80,11);
    tabBaixa.setTamColuna(100,12);
	tabBaixa.setTamColuna(120,13);
    tabBaixa.setTamColuna(100,14);
    
//Manutenção    

    tpn.addTab("Manutenção",pnManut);
    
    btBaixaManut.setToolTipText("Baixa");  
    btEditManut.setToolTipText("Editar");  
    btNovoManut.setToolTipText("Novo");  
    btExcluirManut.setToolTipText("Excluir");  
    btExecManut.setToolTipText("Listar");  

    pnManut.add(pinManut,BorderLayout.NORTH);    
    pnTabManut.add(pinBotoesManut,BorderLayout.EAST);
    pnTabManut.add(spnManut,BorderLayout.CENTER);    
    pnManut.add(pnTabManut,BorderLayout.CENTER);
    
    txtDatainiManut.setVlrDate(new Date());
    txtDatafimManut.setVlrDate(new Date());
    
    pinManut.adic(new JLabelPad("Periodo"),7,0,200,20);
    pinManut.adic(txtDatainiManut,7,20,100,20);
    pinManut.adic(new JLabelPad("até"),113,20,27,20);
    pinManut.adic(txtDatafimManut,140,20,100,20);
    pinManut.adic(btExecManut,690,55,30,30);

	pinManut.adic(new JLabelPad("Cód.for."),7,45,250,20);
	pinManut.adic(txtCodForManut,7,65,50,20);
	pinManut.adic(new JLabelPad("Descrição do fornecedor"),60,45,250,20);
	pinManut.adic(txtRazForManut,60,65,180,20);

	vValsData.addElement("V");
	vValsData.addElement("E");
	vLabsData.addElement("Vencimento");
	vLabsData.addElement("Emissão");

	rgData = new JRadioGroup(2,1,vLabsData,vValsData);
	rgData.setVlrString("V");
	pinManut.adic(new JLabelPad("Filtrar por:"),247,0,115,20);
	pinManut.adic(rgData,247,20,115,65);

	vValsVenc.addElement("VE");
	vValsVenc.addElement("AV");
	vValsVenc.addElement("TT");
	vLabsVenc.addElement("Vencidas");
	vLabsVenc.addElement("À vencer");
	vLabsVenc.addElement("Ambas");	
	
	rgVenc = new JRadioGroup(3,2,vLabsVenc,vValsVenc);
	rgVenc.setVlrString("TT");
	pinManut.adic(new JLabelPad("Filtrar por:"),365,0,150,20);
	pinManut.adic(rgVenc,365,20,115,65);
        
	vValsPg.addElement("PP"); // Pago
	vValsPg.addElement("P1"); // Em aberto
	vValsPg.addElement("P3"); // Pagamento parcial este flag não existe é montado na hora com o valor a pagar 
	vValsPg.addElement("TT"); // Todos os Status	
	vLabsPg.addElement("Pagas"); 
	vLabsPg.addElement("À pagar");
	vLabsPg.addElement("Pg. parcial");	
	vLabsPg.addElement("Todas");
	
	rgPg = new JRadioGroup(3,2,vLabsPg,vValsPg);
	rgPg.setVlrString("TT");
	pinManut.adic(new JLabelPad("Filtrar por:"),488,0,190,20);
	pinManut.adic(rgPg,488,20,190,65);

	lcForManut.add(new GuardaCampo( txtCodForManut, "CodFor", "Cód.for.", ListaCampos.DB_PK, false));
	lcForManut.add(new GuardaCampo( txtRazForManut, "RazFor", "Razão social do fornecedor", ListaCampos.DB_SI, false));
	lcForManut.montaSql(false, "FORNECED", "CP");
	lcForManut.setQueryCommit(false);
	lcForManut.setReadOnly(true);

	txtCodForManut.setTabelaExterna(lcForManut);
	txtCodForManut.setFK(true);
	txtCodForManut.setNomeCampo("CodFor");

    pinBotoesManut.adic(btBaixaManut,5,10,30,30);
    pinBotoesManut.adic(btEditManut,5,40,30,30);
    pinBotoesManut.adic(btNovoManut,5,70,30,30);
    pinBotoesManut.adic(btEstManut,5,100,30,30);
    pinBotoesManut.adic(btExcluirManut,5,130,30,30);
    
	tabManut.adicColuna(""); //0
	tabManut.adicColuna("Data filtro"); //1    	
    tabManut.adicColuna("Status"); //2
    tabManut.adicColuna("Cód.for."); //3
    tabManut.adicColuna("Razão social do fornecedor"); //4
    tabManut.adicColuna("Cód.pag."); //5
    tabManut.adicColuna("Nº parc."); //6
    tabManut.adicColuna("Doc. lanca"); //7
    tabManut.adicColuna("Doc. compra"); //8
    tabManut.adicColuna("Valor parcelamento"); //9
    tabManut.adicColuna("Data pagto."); //10
    tabManut.adicColuna("Valor pago"); //11
    tabManut.adicColuna("Valor desc."); //12
    tabManut.adicColuna("Valor juros"); //13
    tabManut.adicColuna("Valor adic"); //14
    tabManut.adicColuna("Valro aberto"); //15
    tabManut.adicColuna("Conta"); //16
    tabManut.adicColuna("Categoria"); //17
	tabManut.adicColuna("Centro de custo"); //18
    tabManut.adicColuna("Observação"); //19

	tabManut.setTamColuna(0,0);
    tabManut.setTamColuna(80,1);
    tabManut.setTamColuna(50,2);
    tabManut.setTamColuna(65,3);
    tabManut.setTamColuna(200,4);
    tabManut.setTamColuna(70,5);
    tabManut.setTamColuna(60,6);
    tabManut.setTamColuna(75,7);
    tabManut.setTamColuna(90,8);
    tabManut.setTamColuna(140,9);
    tabManut.setTamColuna(100,10);
    tabManut.setTamColuna(100,11);
    tabManut.setTamColuna(100,12);
    tabManut.setTamColuna(100,13);
    tabManut.setTamColuna(100,14);
    tabManut.setTamColuna(100,15);
    tabManut.setTamColuna(130,16);
    tabManut.setTamColuna(210,17);
	tabManut.setTamColuna(220,18);
    tabManut.setTamColuna(260,19);

    lcPagBaixa.addCarregaListener(this);
    txtCodFor.addKeyListener(this);
    txtCodForBaixa.addKeyListener(this);
    btBaixa.addActionListener(this);
    btBaixaConsulta.addActionListener(this);
    btBaixaManut.addActionListener(this);
    btEditManut.addActionListener(this);
    btNovoManut.addActionListener(this);
    btExcluirManut.addActionListener(this);
    btExecManut.addActionListener(this);
    btEstManut.addActionListener(this);
  }
  private void carregaConsulta() {
    limpaConsulta();
    tabConsulta.limpa();
    String sSQL = "SELECT P.CODFOR,SUM(P.VLRPARCPAG),SUM(P.VLRPAGOPAG),"+
                  "SUM(P.VLRAPAGPAG),MIN(P.DATAPAG),MAX(P.DATAPAG) FROM FNPAGAR P "+
                  "WHERE P.CODEMP=? AND P.CODFILIAL=? AND P.CODFOR=? GROUP BY P.CODFOR";
/*    String sSQL = "SELECT P.CODFOR,SUM(P.VLRPARCPAG),SUM(P.VLRPAGOPAG),"+
                  "SUM(P.VLRAPAGPAG),(SELECT MIN(P1.DATAPAG) "+
                  "FROM FNPAGAR P1 WHERE P1.CODFOR=P.CODFOR AND P1.CODEMP=P.CODEMP AND P1.CODFILIAL=P.CODFILIAL),"+
                  "(SELECT MAX(P2.DATAPAG) FROM FNPAGAR P2 "+
                  "WHERE P2.CODFOR=P.CODFOR AND P2.CODEMP=P.CODEMP AND P2.CODFILIAL=P.CODFILIAL) FROM FNPAGAR P "+
                  "WHERE P.CODEMP=? AND P.CODFILIAL=? AND P.CODFOR=? GROUP BY P.CODFOR"; */
    try {
      PreparedStatement ps = con.prepareStatement(sSQL);
	  ps.setInt(1,Aplicativo.iCodEmp);
	  ps.setInt(2,ListaCampos.getMasterFilial("FNPAGAR"));
      ps.setInt(3,txtCodFor.getVlrInteger().intValue());
      ResultSet rs = ps.executeQuery();
      if (rs.next()) {
        txtVlrTotCompr.setVlrString(Funcoes.strDecimalToStrCurrency(15,2,rs.getString(2)));
        txtVlrTotPago.setVlrString(Funcoes.strDecimalToStrCurrency(15,2,rs.getString(3)));
        txtVlrTotAberto.setVlrString(Funcoes.strDecimalToStrCurrency(15,2,rs.getString(4)));
        txtPrimCompr.setVlrString(rs.getDate(5) != null ? Funcoes.sqlDateToStrDate(rs.getDate(5)) : "");
        txtUltCompr.setVlrString(rs.getDate(6) != null ? Funcoes.sqlDateToStrDate(rs.getDate(6)) : "");
//        rs.close();
//        ps.close();
        if (!con.getAutoCommit())
        	con.commit();
        carregaGridConsulta();
      }
    }
    catch (SQLException err) {
		Funcoes.mensagemErro(this,"Erro ao carregar a consulta!\n"+err.getMessage());
    }
  }
  private void carregaGridConsulta() {
    String sSQL = "SELECT IT.DTVENCITPAG,"+
                  "(SELECT C.SERIE FROM CPCOMPRA C WHERE C.CODCOMPRA=P.CODCOMPRA" +
                  " AND C.CODEMP=P.CODEMPCP AND C.CODFILIAL=P.CODFILIALCP)"+
                  ",P.DOCPAG,P.CODCOMPRA,"+
                  "P.DATAPAG,IT.VLRPARCITPAG,IT.DTPAGOITPAG,IT.VLRPAGOITPAG,"+
                  "(CASE WHEN IT.DTPAGOITPAG IS NULL THEN CAST('today' AS DATE)-IT.DTVENCITPAG " +
				  "ELSE IT.DTPAGOITPAG - IT.DTVENCITPAG "+
				  " END ) ATRASO,"+
                  "P.OBSPAG,(SELECT B.NOMEBANCO FROM FNBANCO B "+
                  "WHERE B.CODBANCO = P.CODBANCO AND B.CODEMP=P.CODEMPBO" +
                  " AND B.CODFILIAL=P.CODFILIALBO) AS NOMEBANCO,"+
                  "P.CODPAG,IT.NPARCPAG FROM FNPAGAR P,FNITPAGAR IT "+
                  "WHERE P.CODFOR=? AND P.CODEMP=? AND P.CODFILIAL=?" +
                  " AND IT.CODPAG = P.CODPAG AND IT.CODEMP=P.CODEMP" +
                  " AND IT.CODFILIAL=P.CODFILIAL ORDER BY P.CODPAG,IT.NPARCPAG";  
    try {
      vCodPag = new Vector();
      vNParcPag = new Vector();
      PreparedStatement ps = con.prepareStatement(sSQL);
      ps.setInt(1,txtCodFor.getVlrInteger().intValue());
	  ps.setInt(2,Aplicativo.iCodEmp);
	  ps.setInt(3,ListaCampos.getMasterFilial("FNPAGAR"));
      ResultSet rs = ps.executeQuery();
      for (int i=0; rs.next(); i++) {
        tabConsulta.adicLinha();
        tabConsulta.setValor((rs.getDate("DtVencItPag") != null ? Funcoes.sqlDateToStrDate(rs.getDate("DtVencItPag")) : ""),i,0);
        tabConsulta.setValor((rs.getString(2) != null ? rs.getString(2) : ""),i,1);
        tabConsulta.setValor((rs.getString("DocPag") != null ? rs.getString("DocPag") : ""),i,2);
        tabConsulta.setValor(""+rs.getInt("CodCompra"),i,3);
        tabConsulta.setValor((rs.getDate("DataPag") != null ? Funcoes.sqlDateToStrDate(rs.getDate("DataPag")) : ""),i,4);
        tabConsulta.setValor(Funcoes.strDecimalToStrCurrency(15,2,rs.getString("VlrParcItPag")),i,5);
        tabConsulta.setValor((rs.getDate("DtPagoItPag") != null ? Funcoes.sqlDateToStrDate(rs.getDate("DtPagoItPag")) : ""),i,6);
        tabConsulta.setValor(Funcoes.strDecimalToStrCurrency(15,2,rs.getString("VlrPagoItPag")),i,7);
        tabConsulta.setValor(new Integer(rs.getInt(9)),i,8);
        tabConsulta.setValor(rs.getString("ObsPag") != null ? rs.getString("ObsPag") : "",i,9);
        tabConsulta.setValor(rs.getString(11) != null ? rs.getString(11) : "",i,10);
        vCodPag.addElement(rs.getString("CodPag"));        
        vNParcPag.addElement(rs.getString("NParcPag"));        
      }
//      rs.close();
//      ps.close();
      if (!con.getAutoCommit())
      	con.commit();
    }
    catch(SQLException err) {
		Funcoes.mensagemErro(this,"Erro ao montar a tabela de consulta!\n"+err.getMessage());
    }
  }
  private void carregaGridBaixa() {
    vNParcBaixa = new Vector();
    vNumContas = new Vector();
    vCodPlans = new Vector();
	vCodCCs = new Vector();
    tabBaixa.limpa();
    String sSQL = "SELECT IT.DTVENCITPAG,IT.STATUSITPAG,"+
                  "P.CODPAG,IT.DOCLANCAITPAG,"+
                  "P.CODCOMPRA,IT.VLRPARCITPAG,IT.DTPAGOITPAG,"+
                  "IT.VLRPAGOITPAG,IT.VLRAPAGITPAG,IT.NUMCONTA,"+
                  "IT.VLRDESCITPAG,(SELECT C.DESCCONTA FROM FNCONTA C "+
                  "WHERE C.NUMCONTA = IT.NUMCONTA AND C.CODEMP=IT.CODEMPCA" +
                  " AND C.CODFILIAL=IT.CODFILIALCA),IT.CODPLAN,"+
                  "(SELECT PL.DESCPLAN FROM FNPLANEJAMENTO PL "+
                  "WHERE PL.CODPLAN = IT.CODPLAN AND PL.CODEMP=IT.CODEMPPN" +
                  " AND PL.CODFILIAL=IT.CODFILIALPN),IT.CODCC,(SELECT CC.DESCCC " +
                  "FROM FNCC CC WHERE CC.CODCC = IT.CODCC" +
                  " AND CC.CODEMP=IT.CODEMPCC AND CC.CODFILIAL=IT.CODFILIALCC),"+
                  "IT.OBSITPAG,IT.NPARCPAG,IT.VLRJUROSITPAG,"+
                  "IT.DTITPAG FROM FNITPAGAR IT,FNPAGAR P "+
                  "WHERE IT.CODPAG=P.CODPAG AND P.CODPAG = ?" +
                  " AND P.CODEMP=? AND P.CODFILIAL=?" +
                  " AND IT.CODEMP=P.CODEMP AND IT.CODFILIAL=P.CODFILIAL"+
                  " ORDER BY IT.DTVENCITPAG,IT.STATUSITPAG";
      try {
        PreparedStatement ps = con.prepareStatement(sSQL);
        ps.setInt(1,txtCodPagBaixa.getVlrInteger().intValue());
		ps.setInt(2,Aplicativo.iCodEmp);
		ps.setInt(3,ListaCampos.getMasterFilial("FNPAGAR"));
        ResultSet rs = ps.executeQuery();
        for (int i=0; rs.next(); i++) { 
          tabBaixa.adicLinha();
          tabBaixa.setValor((rs.getDate("DtVencItPag") != null ? Funcoes.sqlDateToStrDate(rs.getDate("DtVencItPag")) : ""),i,0);
          tabBaixa.setValor(rs.getString("StatusItPag"),i,1);
          tabBaixa.setValor(rs.getString("NParcPag"),i,2);
          tabBaixa.setValor((rs.getString("DocLancaItPag") != null ? rs.getString("DocLancaItPag") : ""),i,3);
          tabBaixa.setValor(""+rs.getInt("CodCompra"),i,4);
          tabBaixa.setValor(Funcoes.strDecimalToStrCurrency(15,2,rs.getString("VlrParcItPag")),i,5);
          tabBaixa.setValor((rs.getDate("DtPagoItPag") != null ? Funcoes.sqlDateToStrDate(rs.getDate("DtPagoItPag")) : ""),i,6);
          tabBaixa.setValor(Funcoes.strDecimalToStrCurrency(15,2,rs.getString("VlrPagoItPag")),i,7);
          tabBaixa.setValor(Funcoes.strDecimalToStrCurrency(15,2,rs.getString("VlrDescItPag")),i,8);
          tabBaixa.setValor(Funcoes.strDecimalToStrCurrency(15,2,rs.getString("VlrJurosItPag")),i,9);
          tabBaixa.setValor(Funcoes.strDecimalToStrCurrency(15,2,rs.getString("VlrApagItPag")),i,10);
          tabBaixa.setValor(rs.getString(12) != null ? rs.getString(12) : "",i,11);
          tabBaixa.setValor(rs.getString(14) != null ? rs.getString(14) : "",i,12);
		  tabBaixa.setValor(rs.getString(16) != null ? rs.getString(16) : "",i,13);
          tabBaixa.setValor(rs.getString("ObsItPag") != null ? rs.getString("ObsItPag") : "",i,14);
          vNParcBaixa.addElement(rs.getString("NParcPag"));
          vNumContas.addElement(rs.getString("NumConta") != null ? rs.getString("NumConta") : "");
          vCodPlans.addElement(rs.getString("CodPlan") != null ? rs.getString("CodPlan") : "");
		  vCodCCs.addElement(rs.getString("CodCC") != null ? rs.getString("CodCC") : "");
        }
//      rs.close();
//      ps.close();
        if (!con.getAutoCommit())
        	con.commit();
    }
    catch(SQLException err) {
		Funcoes.mensagemErro(this,"Erro ao montar a tabela de baixa!\n"+err.getMessage());
    }
  }
  private boolean validaPeriodo() {
    boolean bRetorno = false;
    if (txtDatainiManut.getText().trim().length() < 10) {
		Funcoes.mensagemInforma(this,"Data inicial inválida!");
    }
    else if (txtDatafimManut.getText().trim().length() < 10) {
		Funcoes.mensagemInforma(this,"Data final inválida!");
    }
    else if (txtDatafimManut.getVlrDate().before(txtDatainiManut.getVlrDate())) {
		Funcoes.mensagemInforma(this,"Data inicial maior que a data final!");
    }
    else {
      dIniManut = txtDatainiManut.getVlrDate();
      dFimManut = txtDatafimManut.getVlrDate();
      bRetorno = true;
    }
    return bRetorno;
  }
  private void carregaGridManut() {
    tabManut.limpa();
    if (validaPeriodo()) {
      vNumContas = new Vector();
      vCodPlans = new Vector();
	  vCodCCs = new Vector();
      vDtEmiss = new Vector();
      String sWhereManut = " AND "+(rgData.getVlrString().equals("V")?"IT.DTVENCITPAG":"IT.DTITPAG")+
						   " BETWEEN ? AND ? AND P.CODEMP=? AND P.CODFILIAL=?";
	  if (!rgPg.getVlrString().equals("TT")){
	  	if ( (rgPg.getVlrString().equals("P1")) || (rgPg.getVlrString().equals("PP")) )
	  	  sWhereManut += " AND IT.STATUSITPAG='"+rgPg.getVlrString()+"'";
	  	else {
		  sWhereManut += " AND IT.STATUSITPAG='PP' AND IT.VLRAPAGITPAG>0 ";
	  	}
		
	  }
	  					   
	  if (!rgVenc.getVlrString().equals("TT")){
		sWhereManut += " AND IT.DTVENCITPAG";
		if (rgVenc.getVlrString().equals("VE"))
		  sWhereManut += " <'"+Funcoes.dateToStrDB(new Date())+"'";
		else
		  sWhereManut += " >='"+Funcoes.dateToStrDB(new Date())+"'";			
	  }					   
	  if (!txtCodForManut.getText().trim().equals("")) {
	  	sWhereManut += " AND P.CODFOR="+txtCodForManut.getText().trim();
	  }

      String sSQL = "SELECT IT.DTITPAG,IT.DTVENCITPAG,IT.STATUSITPAG,"+
                    "P.CODFOR,F.RAZFOR,P.CODPAG,IT.DOCLANCAITPAG,"+
                    "P.CODCOMPRA,IT.VLRPARCITPAG,IT.DTPAGOITPAG,"+
                    "IT.VLRPAGOITPAG,IT.VLRAPAGITPAG,IT.NUMCONTA,"+
                    "IT.VLRDESCITPAG,(SELECT C.DESCCONTA FROM FNCONTA C "+
                    "WHERE C.NUMCONTA = IT.NUMCONTA AND C.CODEMP=IT.CODEMPCA" +
                    " AND C.CODFILIAL=IT.CODFILIALCA),IT.CODPLAN,"+
                    "(SELECT PL.DESCPLAN FROM FNPLANEJAMENTO PL "+
                    "WHERE PL.CODPLAN = IT.CODPLAN AND PL.CODEMP=IT.CODEMPPN" +
                    " AND PL.CODFILIAL=IT.CODFILIALPN),IT.CODCC," +
                    "(SELECT CC.DESCCC FROM FNCC CC WHERE CC.CODCC = IT.CODCC" +
                    " AND CC.CODEMP=IT.CODEMPCC AND CC.CODFILIAL=IT.CODFILIALCC),"+
                    "IT.OBSITPAG,IT.NPARCPAG,IT.VLRJUROSITPAG,(SELECT CO.DOCCOMPRA "+
                    "FROM CPCOMPRA CO WHERE CO.CODCOMPRA=P.CODCOMPRA" +
                    " AND CO.CODEMP=P.CODEMPCP AND CO.CODFILIAL=P.CODFILIALCP),"+
                    "IT.DTITPAG,IT.VLRADICITPAG FROM FNITPAGAR IT,FNPAGAR P,"+
                    "CPFORNECED F WHERE P.CODPAG=IT.CODPAG"+
                    " AND F.CODFOR=P.CODFOR"+
                    sWhereManut+
                    " AND IT.CODEMP=P.CODEMP AND IT.CODFILIAL=P.CODFILIAL"+
                    " ORDER BY IT.DTVENCITPAG,IT.STATUSITPAG";      
      
      try {
        PreparedStatement ps = con.prepareStatement(sSQL);
        ps.setDate(1,Funcoes.dateToSQLDate(dIniManut));
        ps.setDate(2,Funcoes.dateToSQLDate(dFimManut));
		ps.setInt(3,Aplicativo.iCodEmp);
		ps.setInt(4,ListaCampos.getMasterFilial("FNPAGAR"));
        ResultSet rs = ps.executeQuery();
        double bdVlrAPagar = 0.0;
        double bdVlrParc =  0.0;
        for (int i=0; rs.next(); i++) { 
          tabManut.adicLinha();
          bdVlrAPagar = Funcoes.strDecimalToBigDecimal(2,rs.getString("VlrApagItPag")).doubleValue();
          bdVlrParc = Funcoes.strDecimalToBigDecimal(2,rs.getString("VlrParcItPag")).doubleValue();
                    
          if ( (rs.getString("StatusItPag").equals("PP") && (bdVlrAPagar==0.0) ))
          	  imgColuna = imgPago;
          else if ( (bdVlrAPagar>0.0) && (bdVlrAPagar<bdVlrParc) )
          	  imgColuna = imgPagoParcial;
          else if (rs.getDate("DtVencItPag").before(new Date())) 
			imgColuna = imgVencido;	          
          else if (rs.getDate("DtVencItPag").after(new Date()))
          	imgColuna = imgNaoVencido;
          
          tabManut.setValor(imgColuna,i,0);
          tabManut.setValor((rs.getDate("DtVencItPag") != null ? Funcoes.sqlDateToStrDate(rs.getDate("DtVencItPag")) : ""),i,1);
          tabManut.setValor(rs.getString("StatusItPag"),i,2);
          tabManut.setValor(rs.getString("CodFor"),i,3);
          tabManut.setValor(rs.getString("RazFor"),i,4);
          tabManut.setValor(rs.getString("CodPag"),i,5);
          tabManut.setValor(rs.getString("NParcPag"),i,6);
          tabManut.setValor((rs.getString("DocLancaItPag") != null ? rs.getString("DocLancaItPag") : ""),i,7);
          tabManut.setValor(Funcoes.copy(rs.getString(23),0,10).trim(),i,8);
          tabManut.setValor(Funcoes.strDecimalToStrCurrency(15,2,rs.getString("VlrParcItPag")),i,9);
          tabManut.setValor((rs.getDate("DtPagoItPag") != null ? Funcoes.sqlDateToStrDate(rs.getDate("DtPagoItPag")) : ""),i,10);
          tabManut.setValor(Funcoes.strDecimalToStrCurrency(15,2,rs.getString("VlrPagoItPag")),i,11);
          tabManut.setValor(Funcoes.strDecimalToStrCurrency(15,2,rs.getString("VlrDescItPag")),i,12);
          tabManut.setValor(Funcoes.strDecimalToStrCurrency(15,2,rs.getString("VlrJurosItPag")),i,13);
          tabManut.setValor(Funcoes.strDecimalToStrCurrency(15,2,rs.getString("VlrAdicItPag")),i,14);
          tabManut.setValor(Funcoes.strDecimalToStrCurrency(15,2,rs.getString("VlrApagItPag")),i,15);
          tabManut.setValor(rs.getString(13) != null ? rs.getString(13) : "",i,16);
          tabManut.setValor(rs.getString(17) != null ? rs.getString(17) : "",i,17);
		  tabManut.setValor(rs.getString(19) != null ? rs.getString(19) : "",i,18);
          tabManut.setValor(rs.getString("ObsItPag") != null ? rs.getString("ObsItPag") : "",i,19);
          vNumContas.addElement(rs.getString("NumConta") != null ? rs.getString("NumConta") : "");
          vCodPlans.addElement(rs.getString("CodPlan") != null ? rs.getString("CodPlan") : "");
		  vCodCCs.addElement(rs.getString("CodCC") != null ? rs.getString("CodCC") : "");
          vDtEmiss.addElement(rs.getDate("DtItPag") != null ? Funcoes.sqlDateToStrDate(rs.getDate("DtItPag")) : "");
        }
//        rs.close();
//        ps.close();
        if (!con.getAutoCommit())
        	con.commit();
      }
      catch(SQLException err) {
		Funcoes.mensagemErro(this,"Erro ao montar a tabela de manutenção!\n"+err.getMessage());
      }
    }
  }
  private void baixaConsulta() {
    if (tabConsulta.getLinhaSel() != -1) {
      txtCodPagBaixa.setVlrString((String)vCodPag.elementAt(tabConsulta.getLinhaSel()));
      int iNParc = (new Integer((String)vNParcPag.elementAt(tabConsulta.getLinhaSel()))).intValue();
      lcPagBaixa.carregaDados();
      tpn.setSelectedIndex(1);
      btBaixa.requestFocus();
      for (int i=0; i<vNParcBaixa.size(); i++) {
        if (iNParc == (new Integer((String)vNParcBaixa.elementAt(i))).intValue()) {
          tabBaixa.setLinhaSel(i);
          break;
        }
      }
    }
  }
  private void limpaConsulta() {
    txtPrimCompr.setVlrString("");
    txtUltCompr.setVlrString("");
    txtDataMaxFat.setVlrString("");
    txtVlrMaxFat.setVlrString("");
    txtVlrTotCompr.setVlrString("");
    txtVlrTotPago.setVlrString("");
    txtVlrTotAberto.setVlrString("");
    txtDataMaxAcum.setVlrString("");
    txtVlrMaxAcum.setVlrString("");
  }
  private void baixar(char cOrig) { 
    if ((cOrig == 'M') & (tabManut.getLinhaSel() > -1)) { //Quando a função eh chamada da tab MANUTENÇÂO
      int iLin = tabManut.getLinhaSel();
	  if (iLin < 0)
		return;
      iCodPag = Integer.parseInt((String)tabManut.getValor(iLin,5));
      iNParcPag = Integer.parseInt(""+tabManut.getValor(iLin,6));
      String[] sVals = new String[12];
      String[] sRets = null;
      DLBaixaPag dl = new DLBaixaPag(this);
      sVals[0] = ""+tabManut.getValor(iLin,3);
      sVals[1] = ""+tabManut.getValor(iLin,4);
      sVals[2] = ""+vNumContas.elementAt(iLin);
      sVals[3] = ""+vCodPlans.elementAt(iLin);
      sVals[4] = ""+tabManut.getValor(iLin,7);
      sVals[5] = ""+vDtEmiss.elementAt(iLin);
      sVals[6] = ""+tabManut.getValor(iLin,1);
      sVals[7] = ""+tabManut.getValor(iLin,9);
	  sVals[10] = ""+vCodCCs.elementAt(iLin);
      if (((String)tabManut.getValor(iLin,11)).trim().equals("")) {//Para verificar c jah esta pago testa se a data de pgto esta setada. 
         sVals[11] = "PAGAMENTO REF. A COMPRA: "+tabManut.getValor(iLin,19);
         sVals[8] = Funcoes.dateToStrDate(new Date());
         sVals[9] = ""+tabManut.getValor(iLin,11);
      }
      else {
		 sVals[11] = ""+tabManut.getValor(iLin,19);
         sVals[8] = ""+tabManut.getValor(iLin,10);
         sVals[9] = ""+tabManut.getValor(iLin,11);
      }
      dl.setValores(sVals);
      dl.setConexao(con);
      dl.setVisible(true);
      if (dl.OK) {
        sRets = dl.getValores();
        String sSQL = "UPDATE FNITPAGAR SET NUMCONTA=?,CODEMPCA=?,CODFILIALCA=?,CODPLAN=?,CODEMPPN=?,CODFILIALPN=?,"+
                      "DOCLANCAITPAG=?,DTPAGOITPAG=?,VLRPAGOITPAG=?,ANOCC=?,CODCC=?,CODEMPCC=?,CODFILIALCC=?," +
                      "OBSITPAG=?,STATUSITPAG='PP' WHERE CODPAG=? AND NPARCPAG=? AND CODEMP=? AND CODFILIAL=?";
        try {
          PreparedStatement ps = con.prepareStatement(sSQL);
          ps.setString(1,sRets[0]);
		  ps.setInt(2,Aplicativo.iCodEmp);
		  ps.setInt(3,ListaCampos.getMasterFilial("FNCONTA"));
		  ps.setString(4,sRets[1]);
		  ps.setInt(5,Aplicativo.iCodEmp);
		  ps.setInt(6,ListaCampos.getMasterFilial("FNPLANEJAMENTO"));
          ps.setString(7,sRets[2]);
          ps.setDate(8,Funcoes.strDateToSqlDate(sRets[3]));
          ps.setBigDecimal(9,Funcoes.strCurrencyToBigDecimal(sRets[4]));
		  if (!sRets[5].trim().equals("")) {
		    ps.setInt(10,iAnoCC);
		    ps.setString(11,sRets[5]);
		    ps.setInt(12,Aplicativo.iCodEmp);
		    ps.setInt(13,ListaCampos.getMasterFilial("FNCC"));
		  }
		  else {
		  	ps.setNull(10,Types.INTEGER);
		  	ps.setNull(11,Types.CHAR);
		  	ps.setNull(12,Types.INTEGER);
		  	ps.setNull(13,Types.INTEGER);
		  }
		  ps.setString(14,sRets[6]);
          ps.setInt(15,iCodPag);
          ps.setInt(16,iNParcPag);
		  ps.setInt(17,Aplicativo.iCodEmp);
		  ps.setInt(18,ListaCampos.getMasterFilial("FNPAGAR"));
          ps.executeUpdate();
          if (!con.getAutoCommit())
          	con.commit();
        }
        catch(SQLException err) {
			Funcoes.mensagemErro(this,"Erro ao baixar parcela!\n"+err.getMessage());
        }
      }
      dl.dispose();
      carregaGridManut();
    }
    else if ((cOrig == 'B') & (tabBaixa.getLinhaSel() > -1)) { //Quando a função eh chamada da tab BAIXAR
      int iLin = tabBaixa.getLinhaSel();
      iCodPag = txtCodPagBaixa.getVlrInteger().intValue();
      iNParcPag = Integer.parseInt(""+tabBaixa.getValor(iLin,2));
      String[] sVals = new String[12];
      String[] sRets = null;
      DLBaixaPag dl = new DLBaixaPag(this);
      sVals[0] = ""+txtCodForBaixa.getVlrString();
      sVals[1] = ""+txtRazForBaixa.getVlrString();
      sVals[2] = ""+vNumContas.elementAt(iLin);
      sVals[3] = ""+vCodPlans.elementAt(iLin);
      sVals[4] = ""+txtDoc.getVlrString();
      sVals[5] = ""+txtDtEmisBaixa.getVlrString();
      sVals[6] = ""+tabBaixa.getValor(iLin,0);
      sVals[7] = ""+tabBaixa.getValor(iLin,5);
	  sVals[10] = ""+vCodCCs.elementAt(iLin);
      if (((String)tabBaixa.getValor(iLin,6)).trim().equals("")) {
        sVals[11] = "PAGAMENTO REF. A COMPRA: "+txtCodCompraBaixa.getVlrString();
        sVals[8] = Funcoes.dateToStrDate(new Date());
        sVals[9] = ""+tabBaixa.getValor(iLin,5);
      }
      else {
         sVals[11] = ""+tabBaixa.getValor(iLin,13);
         sVals[8] = ""+tabBaixa.getValor(iLin,6);
         sVals[9] = ""+tabBaixa.getValor(iLin,7);
      }
      dl.setValores(sVals);
      dl.setConexao(con);
      dl.setVisible(true);
      if (dl.OK) {
        sRets = dl.getValores();
        String sSQL = "UPDATE FNITPAGAR SET NUMCONTA=?,CODEMPCA=?,CODFILIALCA=?,CODPLAN=?,CODEMPPN=?,CODFILIALPN=?,"+
                      "ANOCC=?,CODCC=?,CODEMPCC=?,CODFILIALCC=?,DOCLANCAITPAG =?,DTPAGOITPAG=?,VLRPAGOITPAG=?,"+
                      "OBSITPAG=?,STATUSITPAG='PP' WHERE CODPAG=? AND NPARCPAG=? AND CODEMP=? AND CODFILIAL=?";
        try {
          PreparedStatement ps = con.prepareStatement(sSQL);
		  ps.setString(1,sRets[0]);
		  ps.setInt(2,Aplicativo.iCodEmp);
		  ps.setInt(3,ListaCampos.getMasterFilial("FNCONTA"));
		  ps.setString(4,sRets[1]);
		  ps.setInt(5,Aplicativo.iCodEmp);
		  ps.setInt(6,ListaCampos.getMasterFilial("FNPLANEJAMENTO"));
		  if (!sRets[5].trim().equals("")) {
		    ps.setInt(7,iAnoCC);
		    ps.setString(8,sRets[5]);
		    ps.setInt(9,Aplicativo.iCodEmp);
		    ps.setInt(10,ListaCampos.getMasterFilial("FNCC"));
		  }
		  else {
		  	ps.setNull(7,Types.INTEGER);
		  	ps.setNull(8,Types.CHAR);
		  	ps.setNull(9,Types.INTEGER);
		  	ps.setNull(10,Types.INTEGER);
		  }
          ps.setString(11,sRets[2]);
          ps.setDate(12,Funcoes.strDateToSqlDate(sRets[3]));
          ps.setBigDecimal(13,Funcoes.strCurrencyToBigDecimal(sRets[4]));
          ps.setString(14,sRets[6]);
          ps.setInt(15,iCodPag);
          ps.setInt(16,iNParcPag);
		  ps.setInt(17,Aplicativo.iCodEmp);
		  ps.setInt(18,ListaCampos.getMasterFilial("FNPAGAR"));
          ps.executeUpdate();
          if (!con.getAutoCommit())
          	con.commit();
        }
        catch(SQLException err) {
			Funcoes.mensagemErro(this,"Erro ao baixar parcela!\n"+err.getMessage());
        }
      }
      carregaGridBaixa();
      dl.dispose();
    }
  }
  private void editar() { 
    if (tabManut.getLinhaSel() > -1) {
      if ((""+tabManut.getValor(tabManut.getLinhaSel(),2)).equals("P1")) { 
        int iLin = tabManut.getLinhaSel();
        iCodPag = Integer.parseInt((String)tabManut.getValor(iLin,5));
        iNParcPag = Integer.parseInt(""+tabManut.getValor(iLin,6));
        String[] sVals = new String[13];
        String[] sRets = null;
        DLEditaPag dl = new DLEditaPag(this);
        sVals[0] = ""+tabManut.getValor(iLin,3);
        sVals[1] = ""+tabManut.getValor(iLin,4);
        sVals[2] = ""+vNumContas.elementAt(iLin);
        sVals[3] = ""+vCodPlans.elementAt(iLin);
		sVals[4] = ""+vCodCCs.elementAt(iLin);
        sVals[5] = ""+tabManut.getValor(iLin,5);
        sVals[6] = ""+vDtEmiss.elementAt(iLin);
        sVals[7] = ""+tabManut.getValor(iLin,1);
        sVals[8] = ""+tabManut.getValor(iLin,9);
        sVals[9] = ""+tabManut.getValor(iLin,13);
        sVals[10] = ""+tabManut.getValor(iLin,12);
        sVals[11] = ""+tabManut.getValor(iLin,14);
        if (((String)tabManut.getValor(iLin,10)).trim().equals("")) {
        	sVals[12] = "PAGAMENTO REF. A COMPRA: "+tabManut.getValor(iLin,8);
        }
        else {
          	sVals[12] = ""+tabManut.getValor(iLin,18);
        }
        //SE o doccompra estiver em branco getvalor(8) quer dizer que o lançamento foi feito pelo usuário.
        dl.setValores(sVals,tabManut.getValor(iLin,8).toString().trim().equals(""));
        dl.setConexao(con);
        dl.setVisible(true);
        if (dl.OK) {
          sRets = dl.getValores();
          String sSQL = "UPDATE FNITPAGAR SET NUMCONTA=?,CODEMPCA=?,CODFILIALCA=?,CODPLAN=?,CODEMPPN=?,CODFILIALPN=?,"+
                        "ANOCC=?,CODCC=?,CODEMPCC=?,CODFILIALCC=?,DOCLANCAITPAG =?,VLRPARCITPAG=?,VLRJUROSITPAG=?," +
                        "VLRADICITPAG =?,VLRDESCITPAG=?,DTVENCITPAG=?,OBSITPAG=?"+
                        " WHERE CODPAG=? AND NPARCPAG=? AND CODEMP=? AND CODFILIAL=?";
          try {
            PreparedStatement ps = con.prepareStatement(sSQL);
			if (!sRets[0].trim().equals("")) {
				ps.setString(1,sRets[0]);
				ps.setInt(2,Aplicativo.iCodEmp);
				ps.setInt(3,ListaCampos.getMasterFilial("FNCONTA"));
			}
			else {
				ps.setNull(1,Types.CHAR);
				ps.setNull(2,Types.INTEGER);
				ps.setNull(3,Types.INTEGER);
			}
			if (!sRets[1].trim().equals("")) {
				ps.setString(4,sRets[1]);
				ps.setInt(5,Aplicativo.iCodEmp);
				ps.setInt(6,ListaCampos.getMasterFilial("FNPLANEJAMENTO"));
			}
			else {
				ps.setNull(4,Types.CHAR);
				ps.setNull(5,Types.INTEGER);
				ps.setNull(6,Types.INTEGER);
			}
			if (!sRets[2].trim().equals("")) {
				ps.setInt(7,iAnoCC);
				ps.setString(8,sRets[2]);
				ps.setInt(9,Aplicativo.iCodEmp);
				ps.setInt(10,ListaCampos.getMasterFilial("FNCC"));
			}
			else {
				ps.setNull(7,Types.INTEGER);
				ps.setNull(8,Types.CHAR);
				ps.setNull(9,Types.INTEGER);
				ps.setNull(10,Types.INTEGER);
			}
			if (!sRets[3].trim().equals(""))
              ps.setString(11,sRets[3]);
			else
			  ps.setNull(11,Types.CHAR);
		    if (!sRets[4].trim().equals(""))
              ps.setBigDecimal(12,Funcoes.strCurrencyToBigDecimal(sRets[4]));
		    else
		      ps.setNull(12,Types.DECIMAL);
		    if (!sRets[5].trim().equals(""))
	          ps.setBigDecimal(13,Funcoes.strCurrencyToBigDecimal(sRets[5]));
  	        else
			  ps.setNull(13,Types.DECIMAL);
		    if (!sRets[6].trim().equals(""))
              ps.setBigDecimal(14,Funcoes.strCurrencyToBigDecimal(sRets[6]));
		    else
		    	ps.setNull(14,Types.DECIMAL);
		    if (!sRets[7].trim().equals(""))
              ps.setBigDecimal(15,Funcoes.strCurrencyToBigDecimal(sRets[7]));
		    else
		      ps.setNull(15,Types.DECIMAL);
		    if (!sRets[8].trim().equals(""))
              ps.setDate(16,Funcoes.strDateToSqlDate(sRets[8]));
		    else
		      ps.setNull(16,Types.DECIMAL);
		    if (!sRets[9].trim().equals(""))
              ps.setString(17,sRets[9]);
		    else
		      ps.setNull(17,Types.CHAR);
		    ps.setInt(18,iCodPag);
            ps.setInt(19,iNParcPag);
			ps.setInt(20,Aplicativo.iCodEmp);
			ps.setInt(21,ListaCampos.getMasterFilial("FNPAGAR"));
            ps.executeUpdate();
            if (!con.getAutoCommit())
            	con.commit();
          }
          catch(SQLException err) {
			Funcoes.mensagemErro(this,"Erro ao editar parcela!\n"+err.getMessage());
			err.printStackTrace();
          }
        }
        dl.dispose();
        carregaGridManut();
      }
    }
  }
  private void novo() { 
    DLNovoPag dl = new DLNovoPag(this);
    dl.setConexao(con);
    dl.setVisible(true);
    dl.dispose();
    carregaGridManut();
  }
  private void excluir() { 
  	if (tabManut.getLinhaSel() > -1) {
      if (((""+tabManut.getValor(tabManut.getLinhaSel(),2)).equals("P1")) && 
          ((""+tabManut.getValor(tabManut.getLinhaSel(),7)).equals(""))) {
        if (Funcoes.mensagemConfirma(this, "Deseja realmente excluir esta conta e todas as suas parcelas?")==0) {
          String sSQL = "DELETE FROM FNPAGAR WHERE CODPAG=? AND CODEMP=? AND CODFILIAL=?";
          try {
            PreparedStatement ps = con.prepareStatement(sSQL);
            ps.setInt(1,Integer.parseInt(""+tabManut.getValor(tabManut.getLinhaSel(),5)));
			ps.setInt(2,Aplicativo.iCodEmp);
			ps.setInt(3,ListaCampos.getMasterFilial("FNPAGAR"));
            ps.executeUpdate();
            if (!con.getAutoCommit())
            	con.commit();
            carregaGridManut();
          }
          catch(SQLException err) {
			Funcoes.mensagemErro(this,"Erro ao excluir parcela!\n"+err.getMessage());
          }
        }
      }
      else {
      		Funcoes.mensagemErro(this,"Documento não pode ser excluído pois já possui lançamentos financeiros.");
      }
    }

  		
  }
  private void estorno() {
    if (tabManut.getLinhaSel() > -1) {
      if ((""+tabManut.getValor(tabManut.getLinhaSel(),2)).equals("PP")) { 
        int iLin = tabManut.getLinhaSel();
        iCodPag = Integer.parseInt((String)tabManut.getValor(iLin,5));
        iNParcPag = Integer.parseInt(""+tabManut.getValor(iLin,6));
        String sSQL = "UPDATE FNITPAGAR SET STATUSITPAG='P1' WHERE CODPAG=? AND NPARCPAG=? AND CODEMP=? AND CODFILIAL=?";
        try {
          PreparedStatement ps = con.prepareStatement(sSQL);
          ps.setInt(1,iCodPag);
          ps.setInt(2,iNParcPag);
		  ps.setInt(3,Aplicativo.iCodEmp);
		  ps.setInt(4,ListaCampos.getMasterFilial("FNPAGAR"));
          ps.executeUpdate();
          if (!con.getAutoCommit())
          	con.commit();
        }
        catch(SQLException err) {
			Funcoes.mensagemErro(this,"Erro ao estornar registro!\n"+err.getMessage());
        }
        carregaGridManut();
      }
    }
  }
  public void afterCarrega(CarregaEvent cevt) {
    tabBaixa.limpa();
    carregaGridBaixa();   
  }
  public void keyPressed(KeyEvent kevt) { 
    if (kevt.getKeyCode() == KeyEvent.VK_ENTER) {
      carregaConsulta();
    }
  }
  public void actionPerformed(ActionEvent evt) {
    if (evt.getSource() == btSair) {
      dispose();
    }
    else if (evt.getSource() == btBaixaConsulta) {
      baixaConsulta();
    }
    else if (evt.getSource() == btBaixa) {
      baixar('B');
    }
    else if (evt.getSource() == btBaixaManut) {
      baixar('M');
    }
    else if (evt.getSource() == btEditManut) {
      editar();
    }
    else if (evt.getSource() == btNovoManut) {
      novo();
    }
    else if (evt.getSource() == btExcluirManut) {
      excluir();
    }
    else if (evt.getSource() == btExecManut) {
      carregaGridManut();
    }
    else if (evt.getSource() == btEstManut) {
      estorno();
    }
  }
  private int buscaAnoBaseCC() {
	int iRet = 0;
	String sSQL = "SELECT ANOCENTROCUSTO FROM SGPREFERE1 WHERE CODEMP=? AND CODFILIAL=?";
	try {
		PreparedStatement ps = con.prepareStatement(sSQL);
		ps.setInt(1,Aplicativo.iCodEmp);
		ps.setInt(2,ListaCampos.getMasterFilial("SGPREFERE1"));
		ResultSet rs = ps.executeQuery();
		if (rs.next())
			iRet = rs.getInt("ANOCENTROCUSTO");
		rs.close();
		ps.close();
	}
	catch (SQLException err) {
		Funcoes.mensagemErro(this,"Erro ao buscar o ano-base para o centro de custo.\n"+err.getMessage());
	}
	return iRet;
  }
  public void setConexao(Connection cn) {
  	super.setConexao(cn);
    lcFor.setConexao(cn);
    lcForBaixa.setConexao(cn);
    lcForManut.setConexao(cn);
    lcCompraBaixa.setConexao(cn);
    lcBancoBaixa.setConexao(cn);
    lcPagBaixa.setConexao(cn);
    iAnoCC = buscaAnoBaseCC();
  }
  public void keyReleased(KeyEvent kevt) { }
  public void keyTyped(KeyEvent kevt) { }
  public void beforeCarrega(CarregaEvent cevt) {  }
}


