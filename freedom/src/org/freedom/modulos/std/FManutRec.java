/**
 * @version 11/02/2002 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)FManutRec.java <BR>
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
 * Comentários sobre a classe...
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
import java.math.BigDecimal;
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
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;

import org.freedom.acao.CarregaEvent;
import org.freedom.acao.CarregaListener;
import org.freedom.bmps.Icone;
import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JRadioGroup;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.Painel;
import org.freedom.componentes.Tabela;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FFilho;

public class FManutRec extends FFilho implements ActionListener,KeyListener,CarregaListener {
  private JPanel pnLegenda = new JPanel(new GridLayout(0,4));
  private JPanel pnTabConsulta = new JPanel(new BorderLayout());
  private Painel pinBotoesConsulta = new Painel(40,100);
  private JPanel pnRod = new JPanel(new BorderLayout());
  private Painel pinConsulta = new Painel(500,140);
  private JPanel pnConsulta = new JPanel(new BorderLayout());
  private JTabbedPane tpn = new JTabbedPane();
  private Tabela tabConsulta = new Tabela();
  private JScrollPane spnConsulta = new JScrollPane(tabConsulta);
  private JPanel pnTabBaixa = new JPanel(new BorderLayout());
  private Painel pinBotoesBaixa = new Painel(40,100);
  private Painel pinBaixa = new Painel(500,140);
  private JPanel pnBaixa = new JPanel(new BorderLayout());
 
  private Tabela tabBaixa = new Tabela();
  private JScrollPane spnBaixa = new JScrollPane(tabBaixa);
  private JPanel pnTabManut = new JPanel(new BorderLayout());
  private Painel pinBotoesManut = new Painel(40,130);
  private Painel pinManut = new Painel(500,155);
  private JPanel pnManut = new JPanel(new BorderLayout());
 
  private Tabela tabManut = new Tabela();
  private JScrollPane spnManut = new JScrollPane(tabManut);
  private JTextFieldPad txtCodCli = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldPad txtCodCliFiltro = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldPad txtPrimCompr = new JTextFieldPad();
  private JTextFieldPad txtUltCompr = new JTextFieldPad();
  private JTextFieldPad txtDataMaxFat = new JTextFieldPad(JTextFieldPad.TP_DATE,10,0);
  private JTextFieldPad txtVlrMaxFat = new JTextFieldPad(JTextFieldPad.TP_DECIMAL,15,2);
  private JTextFieldPad txtVlrTotCompr = new JTextFieldPad(JTextFieldPad.TP_DECIMAL,15,2);
  private JTextFieldPad txtVlrTotPago = new JTextFieldPad(JTextFieldPad.TP_DECIMAL,15,2);
  private JTextFieldPad txtVlrTotAberto = new JTextFieldPad(JTextFieldPad.TP_DECIMAL,15,2);
  private JTextFieldPad txtDataMaxAcum = new JTextFieldPad(JTextFieldPad.TP_DATE,10,0);
  private JTextFieldPad txtVlrMaxAcum = new JTextFieldPad(JTextFieldPad.TP_DECIMAL,15,2);
  private JTextFieldPad txtCodRecBaixa = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldPad txtCodRecManut = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldFK txtDocManut = new JTextFieldFK(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldFK txtPedidoManut = new JTextFieldFK(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldFK txtCodCliManut = new JTextFieldFK(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldFK txtRazCliManut = new JTextFieldFK(JTextFieldPad.TP_STRING,50,0);
  private JTextFieldFK txtDtEmitManut= new JTextFieldFK(JTextFieldPad.TP_DATE,10,0);
  private JTextFieldPad txtDoc = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldPad txtSerie = new JTextFieldPad(JTextFieldPad.TP_STRING,4,0);
  private JTextFieldPad txtCodVendaBaixa = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldPad txtCodCliBaixa = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldPad txtDtEmisBaixa = new JTextFieldPad(JTextFieldPad.TP_DATE,10,0);
  private JTextFieldPad txtCodBancoBaixa = new JTextFieldPad(JTextFieldPad.TP_STRING,3,0);
  private JTextFieldPad txtTotRecBaixa = new JTextFieldPad(JTextFieldPad.TP_DECIMAL,15,2);
  private JTextFieldPad txtTotAbertoBaixa = new JTextFieldPad(JTextFieldPad.TP_DECIMAL,15,2);
  private JTextFieldPad txtTotPagoBaixa = new JTextFieldPad(JTextFieldPad.TP_DECIMAL,15,2);
  private JTextFieldPad txtJurosBaixa = new JTextFieldPad(JTextFieldPad.TP_DECIMAL,15,2);
  private JTextFieldPad txtDatainiManut = new JTextFieldPad(JTextFieldPad.TP_DATE,10,0);
  private JTextFieldPad txtDatafimManut = new JTextFieldPad(JTextFieldPad.TP_DATE,10,0);
  private JTextFieldFK txtRazCli = new JTextFieldFK(JTextFieldPad.TP_STRING,40,0);
  private JTextFieldFK txtRazCliFiltro = new JTextFieldFK(JTextFieldPad.TP_STRING,40,0);
  private JTextFieldFK txtRazCliBaixa = new JTextFieldFK(JTextFieldPad.TP_STRING,40,0);
  private JTextFieldFK txtDescBancoBaixa = new JTextFieldFK(JTextFieldPad.TP_STRING,40,0);
  private JButton btBaixaConsulta = new JButton(Icone.novo("btOk.gif"));
  private JButton btBaixaManut = new JButton(Icone.novo("btOk.gif"));
  private JButton btEditManut = new JButton(Icone.novo("btEditar.gif"));
  private JButton btNovoManut = new JButton(Icone.novo("btNovo.gif"));
  private JButton btExcluirManut = new JButton(Icone.novo("btExcluir.gif"));
  private JButton btEstManut = new JButton(Icone.novo("btCancelar.gif"));
  private JButton btExecManut = new JButton(Icone.novo("btExecuta.gif"));
  private JButton btConsBaixa = new JButton(Icone.novo("btConsBaixa.gif"));
  private JButton btBaixa = new JButton(Icone.novo("btOk.gif"));
  private JButton btSair = new JButton("Sair",Icone.novo("btSair.gif"));
  private JButton btConsVenda = new JButton(Icone.novo("btSaida.gif"));
  
  private ListaCampos lcCli = new ListaCampos(this);
  private ListaCampos lcCliBaixa = new ListaCampos(this);
  private ListaCampos lcCliFiltro = new ListaCampos(this);  
  private ListaCampos lcCliManut = new ListaCampos(this,"CL");  
  private ListaCampos lcVendaBaixa = new ListaCampos(this);
  private ListaCampos lcRecManut = new ListaCampos(this);
  private ListaCampos lcRecBaixa = new ListaCampos(this);
  private ListaCampos lcBancoBaixa = new ListaCampos(this);
  private Vector vCodRec = new Vector();
  private Vector vNParcItRec = new Vector();
  private Vector vNParcBaixa = new Vector();
  private Date dIniManut = null;
  private Date dFimManut = null;
  private Vector vNumContas = new Vector();
  private Vector vCodPlans = new Vector();
  private Vector vCodCCs = new Vector();
  private Vector vCodBOs = new Vector();
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
  boolean bBuscaAtual = true;
  int iCodRec = 0;
  int iNParcItRec = 0;
  int iAnoCC = 0;
  public FManutRec() {
    setTitulo("Manutenção de contas a receber");
	setAtribos(20,20,750,450);    
    Container c = getContentPane();    
    c.setLayout(new BorderLayout());
    c.add(pnRod,BorderLayout.SOUTH);
    c.add(tpn,BorderLayout.CENTER);
    btSair.setPreferredSize(new Dimension(100,30));
    
    pnLegenda.add(new JLabel("Vencido",imgVencido,SwingConstants.CENTER));
	pnLegenda.add(new JLabel("Parcial",imgPagoParcial,SwingConstants.CENTER));
	pnLegenda.add(new JLabel("Pago",imgPago,SwingConstants.CENTER));
	pnLegenda.add(new JLabel("À vencer",imgNaoVencido,SwingConstants.CENTER));

    pnRod.setBorder(BorderFactory.createEtchedBorder());
    pnRod.setPreferredSize(new Dimension(500,32));

    pnRod.add(btSair,BorderLayout.EAST);
	pnRod.add(pnLegenda,BorderLayout.WEST);

    btSair.addActionListener(this);
    //Consulta:    
    
    lcCli.add(new GuardaCampo( txtCodCli, "CodCli", "Cód.cli.", ListaCampos.DB_PK, false));
    lcCli.add(new GuardaCampo( txtRazCli, "RazCli", "Razão social do clliente", ListaCampos.DB_SI, false));
    lcCli.montaSql(false, "CLIENTE", "VD");
    lcCli.setQueryCommit(false);
    lcCli.setReadOnly(true);
    txtCodCli.setTabelaExterna(lcCli);
    txtCodCli.setFK(true);
    txtCodCli.setNomeCampo("CodCli");

	
    txtPrimCompr.setAtivo(false);
    txtUltCompr.setAtivo(false);
    txtDataMaxFat.setAtivo(false);
    txtVlrMaxFat.setAtivo(false);
    txtVlrTotCompr.setAtivo(false);
    txtVlrTotPago.setAtivo(false);
    txtVlrTotAberto.setAtivo(false);
    txtDataMaxAcum.setAtivo(false);
    txtVlrMaxAcum.setAtivo(false);
    
    txtCodCli.setRequerido(true);
    txtCodRecManut.setRequerido(true);
    
    tpn.addTab("Consulta",pnConsulta);
    
    btBaixaConsulta.setToolTipText("Baixa");  
        
    pnConsulta.add(pinConsulta,BorderLayout.NORTH);
    
    pnTabConsulta.add(pinBotoesConsulta,BorderLayout.EAST);
    
    pnTabConsulta.add(spnConsulta,BorderLayout.CENTER);
      
    pnConsulta.add(pnTabConsulta,BorderLayout.CENTER);
    
    pinConsulta.adic(new JLabel("Cód.cli."),7,0,250,20);
    pinConsulta.adic(txtCodCli,7,20,80,20);
    pinConsulta.adic(new JLabel("Razão social do cliente"),90,0,250,20);
    pinConsulta.adic(txtRazCli,90,20,217,20);
    pinConsulta.adic(new JLabel("P. compra"),310,0,97,20);
    pinConsulta.adic(txtPrimCompr,310,20,97,20);
    pinConsulta.adic(new JLabel("U. compra"),410,0,100,20);
    pinConsulta.adic(txtUltCompr,410,20,100,20);
    pinConsulta.adic(new JLabel("Data"),7,40,200,20);
    pinConsulta.adic(txtDataMaxFat,7,60,100,20);
    pinConsulta.adic(new JLabel("Valor da maior fatura"),110,40,210,20);
    pinConsulta.adic(txtVlrMaxFat,110,60,147,20);
    pinConsulta.adic(new JLabel("Data"),260,40,200,20);
    pinConsulta.adic(txtDataMaxAcum,260,60,97,20);
    pinConsulta.adic(new JLabel("Valor do maior acumulo"),360,40,200,20);
    pinConsulta.adic(txtVlrMaxAcum,360,60,150,20);
    pinConsulta.adic(new JLabel("Total de compras"),7,80,150,20);
    pinConsulta.adic(txtVlrTotCompr,7,100,150,20);
    pinConsulta.adic(new JLabel("Total pago"),160,80,97,20);
    pinConsulta.adic(txtVlrTotPago,160,100,97,20);
    pinConsulta.adic(new JLabel("Total em aberto"),260,80,117,20);
    pinConsulta.adic(txtVlrTotAberto,260,100,117,20);
    pinConsulta.adic(btConsVenda,380,90,30,30);
    
    btConsVenda.addActionListener(this);
    
    
    pinBotoesConsulta.adic(btBaixaConsulta,3,10,30,30);
    
    tabConsulta.adicColuna("Vencimento");//0
    tabConsulta.adicColuna("Doc.");//1
    tabConsulta.adicColuna("Dt. venda.");//2
    tabConsulta.adicColuna("Valor");//3
    tabConsulta.adicColuna("Desc.fin");//4
    tabConsulta.adicColuna("Valor pago.");//5
    tabConsulta.adicColuna("Data pagamento");//6
    tabConsulta.adicColuna("Atraso");//7
    tabConsulta.adicColuna("Juros");//8
    tabConsulta.adicColuna("Série");//9
    tabConsulta.adicColuna("Cód.venda");//10
    tabConsulta.adicColuna("Banco");//11
    tabConsulta.adicColuna("Observações");//12
    
    tabConsulta.setTamColuna(90,0);//venc
    tabConsulta.setTamColuna(80,1);//doc
    tabConsulta.setTamColuna(90,2);//data venda
    tabConsulta.setTamColuna(100,3);//valor
    tabConsulta.setTamColuna(100,4);//Desc.Fin
    tabConsulta.setTamColuna(100,5);//valor pago
    tabConsulta.setTamColuna(120,6);//data pagamento
    tabConsulta.setTamColuna(60,7);//atraso
    tabConsulta.setTamColuna(100,8);//juros
    tabConsulta.setTamColuna(50,9);//serie
    tabConsulta.setTamColuna(80,10);//codvenda
    tabConsulta.setTamColuna(200,11);//banco        
    tabConsulta.setTamColuna(200,12);//observ

    
    //Baixa:    

    lcVendaBaixa.add(new GuardaCampo( txtCodVendaBaixa, "CodVenda", "Cód.venda", ListaCampos.DB_PK, false));
    lcVendaBaixa.add(new GuardaCampo( txtSerie, "Serie", "Série", ListaCampos.DB_SI, false));
    lcVendaBaixa.montaSql(false, "VENDA", "VD");
    lcVendaBaixa.setQueryCommit(false);
    lcVendaBaixa.setReadOnly(true);
    txtCodVendaBaixa.setTabelaExterna(lcVendaBaixa);
    txtCodVendaBaixa.setFK(true);
    txtCodVendaBaixa.setNomeCampo("CodVenda");

    lcCliBaixa.add(new GuardaCampo( txtCodCliBaixa, "CodCli", "Cód.cli", ListaCampos.DB_PK, false));
    lcCliBaixa.add(new GuardaCampo( txtRazCliBaixa, "RazCli", "Razão social do cliente", ListaCampos.DB_SI, false));
    lcCliBaixa.montaSql(false, "CLIENTE", "VD");
    lcCliBaixa.setQueryCommit(false);
    lcCliBaixa.setReadOnly(true);
    txtCodCliBaixa.setTabelaExterna(lcCliBaixa);
    txtCodCliBaixa.setFK(true);
    txtCodCliBaixa.setNomeCampo("CodCli");

    lcBancoBaixa.add(new GuardaCampo( txtCodBancoBaixa, "CodBanco", "Cód.banco", ListaCampos.DB_PK, false));
    lcBancoBaixa.add(new GuardaCampo( txtDescBancoBaixa, "NomeBanco", "Nome do banco", ListaCampos.DB_SI, false));
    lcBancoBaixa.montaSql(false, "BANCO", "FN");
    lcBancoBaixa.setQueryCommit(false);
    lcBancoBaixa.setReadOnly(true);
    txtCodBancoBaixa.setTabelaExterna(lcBancoBaixa);
    txtCodBancoBaixa.setFK(true);
    txtCodBancoBaixa.setNomeCampo("CodBanco");
    
    lcRecBaixa.add(new GuardaCampo( txtCodRecBaixa, "CodRec", "Cód.rec.", ListaCampos.DB_PK, false));
    lcRecBaixa.add(new GuardaCampo( txtCodVendaBaixa,"CodVenda", "Cód.venda", ListaCampos.DB_FK, false));
    lcRecBaixa.add(new GuardaCampo( txtDoc, "DocRec", "Doc", ListaCampos.DB_SI, false));
    lcRecBaixa.add(new GuardaCampo( txtTotRecBaixa, "VlrRec", "Tot.rec.", ListaCampos.DB_SI, false));
    lcRecBaixa.add(new GuardaCampo( txtCodCliBaixa, "CodCli", "Cód.cli.", ListaCampos.DB_FK, false));
    lcRecBaixa.add(new GuardaCampo( txtDtEmisBaixa, "DataRec", "Data emissão", ListaCampos.DB_SI, false));
    lcRecBaixa.add(new GuardaCampo( txtCodBancoBaixa, "CodBanco", "Cód.banco", ListaCampos.DB_FK, false));
    lcRecBaixa.add(new GuardaCampo( txtTotAbertoBaixa, "VlrApagRec", "Total aberto", ListaCampos.DB_SI, false));
    lcRecBaixa.add(new GuardaCampo( txtTotPagoBaixa, "VlrPagoRec", "Total pago", ListaCampos.DB_SI, false));
    lcRecBaixa.add(new GuardaCampo( txtJurosBaixa, "VlrJurosRec", "Total juros", ListaCampos.DB_SI, false));
    lcRecBaixa.montaSql(false,"RECEBER", "FN");
    lcRecBaixa.setQueryCommit(false);
    lcRecBaixa.setReadOnly(true);
    txtCodRecBaixa.setTabelaExterna(lcRecBaixa);
    txtCodRecBaixa.setFK(true);
    txtCodRecBaixa.setNomeCampo("CodRec");

    txtDoc.setAtivo(false);
    txtCodVendaBaixa.setAtivo(false);
    txtSerie.setAtivo(false);
    txtCodCliBaixa.setAtivo(false);
    txtDtEmisBaixa.setAtivo(false);
    txtCodBancoBaixa.setAtivo(false);
    txtTotRecBaixa.setAtivo(false);
    txtTotAbertoBaixa.setAtivo(false);
    txtTotPagoBaixa.setAtivo(false);
    txtJurosBaixa.setAtivo(false);
    
    txtCodRecBaixa.setRequerido(true);
    
    tpn.addTab("Baixa",pnBaixa);
    
    btBaixa.setToolTipText("Baixa");  

    pnBaixa.add(pinBaixa,BorderLayout.NORTH);
    
    pnTabBaixa.add(pinBotoesBaixa,BorderLayout.EAST);
    pnTabBaixa.add(spnBaixa,BorderLayout.CENTER);
    
    pnBaixa.add(pnTabBaixa,BorderLayout.CENTER);
    
    pinBaixa.adic(new JLabel("Cód.rec"),7,0,80,20);
    pinBaixa.adic(txtCodRecBaixa,7,20,80,20);
    pinBaixa.adic(new JLabel("Doc."),90,0,77,20);
    pinBaixa.adic(txtDoc,90,20,77,20);
    pinBaixa.adic(new JLabel(" -"),170,20,7,20);
    pinBaixa.adic(new JLabel("Série"),180,0,50,20);
    pinBaixa.adic(txtSerie,180,20,50,20);
    pinBaixa.adic(new JLabel("Pedido"),240,0,77,20);
    pinBaixa.adic(txtCodVendaBaixa,240,20,77,20);
    pinBaixa.adic(new JLabel("Cód.cli."),7,40,250,20);
    pinBaixa.adic(txtCodCliBaixa,7,60,80,20);
    pinBaixa.adic(new JLabel("Descrição do cliente"),90,40,250,20);
    pinBaixa.adic(txtRazCliBaixa,90,60,207,20);
    pinBaixa.adic(new JLabel("Cód.banco"),300,40,250,20);
    pinBaixa.adic(txtCodBancoBaixa,300,60,77,20);
    pinBaixa.adic(new JLabel("Descrição do banco"),380,40,250,20);
    pinBaixa.adic(txtDescBancoBaixa,380,60,150,20);
    pinBaixa.adic(new JLabel("Data de emissão"),7,80,100,20);
    pinBaixa.adic(txtDtEmisBaixa,7,100,120,20);
    pinBaixa.adic(new JLabel("Total a pagar"),130,80,97,20);
    pinBaixa.adic(txtTotRecBaixa,130,100,97,20);
    pinBaixa.adic(new JLabel("Total pago"),230,80,97,20);
    pinBaixa.adic(txtTotPagoBaixa,230,100,97,20);
    pinBaixa.adic(new JLabel("Total em aberto"),330,80,107,20);
    pinBaixa.adic(txtTotAbertoBaixa,330,100,107,20);
    pinBaixa.adic(new JLabel("Juros"),440,80,80,20);
    pinBaixa.adic(txtJurosBaixa,440,100,90,20);
    pinBaixa.adic(btConsBaixa,540,90,30,30);

    pinBotoesBaixa.adic(btBaixa,3,10,30,30);
    
    tabBaixa.adicColuna("Vencimento"); //0
    tabBaixa.adicColuna("Status"); //1
    tabBaixa.adicColuna("Nº Parcelas"); //2
    tabBaixa.adicColuna("Doc."); //3
    tabBaixa.adicColuna("Pedido"); //4
    tabBaixa.adicColuna("Valor parcela"); //5
    tabBaixa.adicColuna("Data Pagamento"); //6
    tabBaixa.adicColuna("Valor pago"); //7
    tabBaixa.adicColuna("Valor desc."); //8
    tabBaixa.adicColuna("Valor juros"); //9
    tabBaixa.adicColuna("Valor aberto"); //10
    tabBaixa.adicColuna("Conta"); //11
    tabBaixa.adicColuna("Categoria"); //12
	tabBaixa.adicColuna("Centro de custo"); //13
    tabBaixa.adicColuna("Observação"); //14
    
    tabBaixa.setTamColuna(100,0);
    tabBaixa.setTamColuna(50,1);
    tabBaixa.setTamColuna(120,2);
    tabBaixa.setTamColuna(70,3);
    tabBaixa.setTamColuna(70,4);
    tabBaixa.setTamColuna(100,5);
    tabBaixa.setTamColuna(120,6);
    tabBaixa.setTamColuna(100,7);
    tabBaixa.setTamColuna(100,8);
    tabBaixa.setTamColuna(100,9);
    tabBaixa.setTamColuna(100,10);
    tabBaixa.setTamColuna(120,11);
    tabBaixa.setTamColuna(120,12);
	tabBaixa.setTamColuna(120,13);
    tabBaixa.setTamColuna(200,14);
    
//Manutenção    

    tpn.addTab("Manutenção",pnManut);
    
    btBaixaManut.setToolTipText("Baixar");  
    btEditManut.setToolTipText("Editar");  
    btEditManut.setToolTipText("Editar");  
    btExecManut.setToolTipText("Listar");
	btExcluirManut.setToolTipText("Excluir");    

    pnManut.add(pinManut,BorderLayout.NORTH);
    
    pnTabManut.add(pinBotoesManut,BorderLayout.EAST);
    pnTabManut.add(spnManut,BorderLayout.CENTER);
    
    pnManut.add(pnTabManut,BorderLayout.CENTER);
    

    lcRecManut.add(new GuardaCampo( txtCodRecManut, "CodRec", "Cód.rec", ListaCampos.DB_PK, false));
    lcRecManut.add(new GuardaCampo( txtDocManut, "DocRec", "Doc.rec", ListaCampos.DB_SI, false));
    lcRecManut.add(new GuardaCampo( txtPedidoManut, "CodVenda", "Pedido", ListaCampos.DB_SI, false));
    lcRecManut.add(new GuardaCampo( txtCodCliManut, "CodCli", "Cod.cli", ListaCampos.DB_FK, false));
    lcRecManut.add(new GuardaCampo( txtDtEmitManut, "DataRec", "Data emissão", ListaCampos.DB_SI, false));
    lcRecManut.montaSql(false,"RECEBER", "FN");
    lcRecManut.setQueryCommit(false);
    lcRecManut.setReadOnly(true);
    txtCodRecManut.setTabelaExterna(lcRecManut);
    txtCodRecManut.setFK(true);
    txtCodRecManut.setNomeCampo("CodRec");

    txtDatainiManut.setVlrDate(new Date());
    txtDatafimManut.setVlrDate(new Date());
    
	pinManut.adic(new JLabel("Periodo"),7,0,200,20);
	pinManut.adic(txtDatainiManut,7,20,100,20);
	pinManut.adic(new JLabel("até"),113,20,27,20);
	pinManut.adic(txtDatafimManut,140,20,100,20);
	pinManut.adic(btExecManut,690,55,30,30);

	
	pinManut.adic(new JLabel("Cód.cli."),7,45,250,20);
	pinManut.adic(txtCodCliFiltro,7,65,50,20);
	pinManut.adic(new JLabel("Razão social do cliente"),90,45,250,20);
	pinManut.adic(txtRazCliFiltro,60,65,180,20);

	vValsData.addElement("V");
	vValsData.addElement("E");
	vLabsData.addElement("Vencimento");
	vLabsData.addElement("Emissão");

	rgData = new JRadioGroup(2,1,vLabsData,vValsData);
	rgData.setVlrString("V");
	pinManut.adic(new JLabel("Filtrar por:"),247,0,115,20);
	pinManut.adic(rgData,247,20,115,65);

	vValsVenc.addElement("VE");
	vValsVenc.addElement("AV");
	vValsVenc.addElement("TT");
	vLabsVenc.addElement("Vencidas");
	vLabsVenc.addElement("À vencer");
	vLabsVenc.addElement("Ambas");	
	
	rgVenc = new JRadioGroup(3,2,vLabsVenc,vValsVenc);
	rgVenc.setVlrString("TT");
	pinManut.adic(new JLabel("Filtrar por:"),365,0,150,20);
	pinManut.adic(rgVenc,365,20,115,65);
        
	vValsPg.addElement("RP"); // Pago
	vValsPg.addElement("R1"); // Em aberto
	vValsPg.addElement("RL"); // Pagamento parcial este flag não existe é montado na hora com o valor a pagar 
	vValsPg.addElement("TT"); // Todos os Status	
	vLabsPg.addElement("Recebidas"); 
	vLabsPg.addElement("À receber");
	vLabsPg.addElement("Rec. parcial");	
	vLabsPg.addElement("Todas");
	
	rgPg = new JRadioGroup(3,2,vLabsPg,vValsPg);
	rgPg.setVlrString("TT");
	pinManut.adic(new JLabel("Filtrar por:"),488,0,190,20);
	pinManut.adic(rgPg,488,20,192,65);

	lcCliFiltro.add(new GuardaCampo( txtCodCliFiltro, "CodCli", "Cód.cli.", ListaCampos.DB_PK, false));
	lcCliFiltro.add(new GuardaCampo( txtRazCliFiltro, "RazCli", "Razão social do cliente", ListaCampos.DB_SI, false));
	lcCliFiltro.montaSql(false, "CLIENTE", "VD");
	lcCliFiltro.setQueryCommit(false);
	lcCliFiltro.setReadOnly(true);
	txtCodCliFiltro.setTabelaExterna(lcCliFiltro);
	txtCodCliFiltro.setFK(true);
	txtCodCliFiltro.setNomeCampo("CodCli");

    lcCliManut.add(new GuardaCampo( txtCodCliManut, "CodCli", "Cód.cli", ListaCampos.DB_PK, false));
    lcCliManut.add(new GuardaCampo( txtRazCliManut, "RazCli", "Razão social do cliente", ListaCampos.DB_SI, false));
    lcCliManut.montaSql(false,"CLIENTE", "VD");
    lcCliManut.setQueryCommit(false);
    lcCliManut.setReadOnly(true);
    txtCodCliManut.setTabelaExterna(lcCliManut);
    txtCodCliManut.setFK(true);
    txtCodCliManut.setNomeCampo("CodCli");
    
    JLabel lbLinha = new JLabel();
    lbLinha.setBorder(BorderFactory.createEtchedBorder());
    pinManut.adic(lbLinha,5,95,720,2);

	pinManut.adic(new JLabel("Cod.rec."),7,100,80,20);
	pinManut.adic(txtCodRecManut,7,120,80,20);
	pinManut.adic(new JLabel("Doc."),90,100,77,20);
	pinManut.adic(txtDocManut,90,120,77,20);
	pinManut.adic(new JLabel("Pedido"),170,100,77,20);
	pinManut.adic(txtPedidoManut,170,120,77,20);
	pinManut.adic(new JLabel("Cód.cli."),250,100,300,20);
	pinManut.adic(txtCodCliManut,250,120,77,20);
	pinManut.adic(new JLabel("Razão social do cliente"),350,100,300,20);
	pinManut.adic(txtRazCliManut,330,120,247,20);
	pinManut.adic(new JLabel("Data emissão "),580,100,100,20);
	pinManut.adic(txtDtEmitManut,580,120,100,20);

	pinBotoesManut.adic(btBaixaManut,3,10,30,30);
    pinBotoesManut.adic(btEditManut,3,40,30,30);
    pinBotoesManut.adic(btNovoManut,3,70,30,30);
    pinBotoesManut.adic(btEstManut,3,100,30,30);
	pinBotoesManut.adic(btExcluirManut,3,130,30,30);
    
    tabManut.adicColuna(""); //0
    tabManut.adicColuna("Data filtro"); //1
    tabManut.adicColuna("Status"); //2
    tabManut.adicColuna("Cód.cli."); //3
    tabManut.adicColuna("Razão social do cliente"); //4
    tabManut.adicColuna("Cód.rec."); //5
    tabManut.adicColuna("Nº parcela"); //6
    tabManut.adicColuna("Doc. lanca"); //7
    tabManut.adicColuna("Doc. venda"); //8
    tabManut.adicColuna("Valor parc."); //9
    tabManut.adicColuna("Data pagamento"); //10
    tabManut.adicColuna("Valor.pago"); //11
    tabManut.adicColuna("Valor desconto"); //12
    tabManut.adicColuna("Valor juros"); //13
    tabManut.adicColuna("Valor aberto"); //14
    tabManut.adicColuna("Conta"); //15
    tabManut.adicColuna("Categoria"); //16
	tabManut.adicColuna("Centro de custo"); //17
    tabManut.adicColuna("Banco"); //18
    tabManut.adicColuna("Observação"); //19

	tabManut.setTamColuna(0,0);
	tabManut.setTamColuna(100,1);
	tabManut.setTamColuna(60,2);
	tabManut.setTamColuna(80,3);
	tabManut.setTamColuna(200,4);
	tabManut.setTamColuna(80,5);
	tabManut.setTamColuna(80,6);
	tabManut.setTamColuna(80,7);
	tabManut.setTamColuna(80,8);
	tabManut.setTamColuna(90,9);
	tabManut.setTamColuna(100,10);
	tabManut.setTamColuna(100,11);
	tabManut.setTamColuna(100,12);
	tabManut.setTamColuna(100,13);
	tabManut.setTamColuna(100,14);
	tabManut.setTamColuna(100,15);
	tabManut.setTamColuna(130,16);
	tabManut.setTamColuna(230,17);
	tabManut.setTamColuna(240,18);
	tabManut.setTamColuna(260,19);

    lcRecBaixa.addCarregaListener(this);
    lcRecManut.addCarregaListener(this);
    txtCodCli.addKeyListener(this);
    txtCodCliBaixa.addKeyListener(this);
    btBaixa.addActionListener(this);
    btBaixaConsulta.addActionListener(this);
    btBaixaManut.addActionListener(this);
    btEditManut.addActionListener(this);
    btNovoManut.addActionListener(this);
	btExcluirManut.addActionListener(this);
    btExecManut.addActionListener(this);
    btEstManut.addActionListener(this);
    btBaixa.addActionListener(this);
    btConsBaixa.addActionListener(this);
  }
  private void carregaConsulta() {
    limpaConsulta();
    tabConsulta.limpa();
    String sSQL = "SELECT R.CODCLI,SUM(R.VLRPARCREC),SUM(R.VLRPAGOREC),"+
                  "SUM(R.VLRAPAGREC),MIN(R.DATAREC),MAX(R.DATAREC)" +
                  " FROM FNRECEBER R WHERE R.CODCLI=? AND R.CODEMP=?" +
                  " AND R.CODFILIAL=? GROUP BY R.CODCLI";
/*    String sSQL = "SELECT R.CODCLI,SUM(R.VLRPARCREC),SUM(R.VLRPAGOREC),"+
                  "SUM(R.VLRAPAGREC),(SELECT MIN(R1.DATAREC) "+
                  "FROM FNRECEBER R1 WHERE R1.CODCLI=R.CODCLI" +
                  " AND R1.CODEMPCL=R.CODEMP AND R1.CODFILIALCL=R.CODFILIAL),"+
                  "(SELECT MAX(R2.DATAREC) FROM FNRECEBER R2 "+
                  "WHERE R2.CODCLI=R.CODCLI AND R2.CODEMP=R.CODEMP" +
                  " AND R2.CODFILIAL=R.CODFILIAL)" +
                  " FROM FNRECEBER R "+
                  "WHERE R.CODCLI=? AND R.CODEMP=? AND R.CODFILIAL=?" +
                  " GROUP BY R.CODCLI";*/
    try {
      PreparedStatement ps = con.prepareStatement(sSQL);
      ps.setInt(1,txtCodCli.getVlrInteger().intValue());
	  ps.setInt(2,Aplicativo.iCodEmp);
	  ps.setInt(3,ListaCampos.getMasterFilial("FNRECEBER"));
      ResultSet rs = ps.executeQuery();
      if (rs.next()) {
        txtVlrTotCompr.setVlrString(Funcoes.strDecimalToStrCurrency(15,2,rs.getString(2)));
        txtVlrTotPago.setVlrString(Funcoes.strDecimalToStrCurrency(15,2,rs.getString(3)));
        txtVlrTotAberto.setVlrString(Funcoes.strDecimalToStrCurrency(15,2,rs.getString(4)));
        txtPrimCompr.setVlrString(rs.getDate(5) != null ? Funcoes.sqlDateToStrDate(rs.getDate(5)) : "");
        txtUltCompr.setVlrString(rs.getDate(6) != null ? Funcoes.sqlDateToStrDate(rs.getDate(6)) : "");
        rs.close();
        ps.close();
        if (!con.getAutoCommit())
        	con.commit();
        carregaGridConsulta();
      }
    }
    catch (SQLException err) {
      Funcoes.mensagemErro(this,"Erro ao carregar a consulta!\n"+err.getMessage());
      err.printStackTrace();
    }
  }
  private void carregaGridConsulta() {
    String sSQL = "SELECT IT.DTVENCITREC,V.SERIE,R.DOCREC,V.CODVENDA,"+
                  "R.DATAREC,IT.VLRPARCITREC,IT.DTPAGOITREC,IT.VLRPAGOITREC,"+
                  "(CASE WHEN IT.DTPAGOITREC IS NULL THEN CAST('today' AS DATE)-IT.DTVENCITREC " +
				  "ELSE IT.DTPAGOITREC - IT.DTVENCITREC "+
				  " END ) ATRASO,"+
                  "R.OBSREC,(SELECT B.NOMEBANCO FROM FNBANCO B WHERE B.CODBANCO = IT.CODBANCO" +
                  " AND B.CODEMP=IT.CODEMPBO AND B.CODFILIAL=IT.CODFILIALBO) AS NOMEBANCO,"+
                  "R.CODREC,IT.NPARCITREC,IT.VLRDESCITREC,IT.VLRJUROSITREC FROM FNRECEBER R, VDVENDA V,FNITRECEBER IT "+
                  "WHERE R.CODCLI=? AND R.CODEMP=? AND R.CODFILIAL=? AND V.CODVENDA=R.CODVENDA" +
                  " AND V.CODEMP=R.CODEMPVA AND V.CODFILIAL=R.CODFILIALVA" +
                  " AND IT.CODREC = R.CODREC AND IT.CODEMP=R.CODEMP AND IT.CODFILIAL=R.CODFILIAL "+
                  "ORDER BY R.CODREC,IT.NPARCITREC";  
    try {
      vCodRec = new Vector();
      vNParcItRec = new Vector();
      PreparedStatement ps = con.prepareStatement(sSQL);
      ps.setInt(1,txtCodCli.getVlrInteger().intValue());
	  ps.setInt(2,Aplicativo.iCodEmp);
	  ps.setInt(3,ListaCampos.getMasterFilial("FNRECEBER"));
      ResultSet rs = ps.executeQuery();
      for (int i=0; rs.next(); i++) {
        tabConsulta.adicLinha();
        tabConsulta.setValor((rs.getDate("DtVencItRec") != null ? Funcoes.sqlDateToStrDate(rs.getDate("DtVencItRec")) : ""),i,0);
        tabConsulta.setValor((rs.getString("DocRec") != null ? rs.getString("DocRec") : ""),i,1);
        tabConsulta.setValor((rs.getDate("DataRec") != null ? Funcoes.sqlDateToStrDate(rs.getDate("DataRec")) : ""),i,2);
        tabConsulta.setValor(Funcoes.strDecimalToStrCurrency(15,2,rs.getString("VlrParcItRec")),i,3);
        tabConsulta.setValor(Funcoes.strDecimalToStrCurrency(15,2,rs.getString("VlrDescItRec")),i,4);
        tabConsulta.setValor(Funcoes.strDecimalToStrCurrency(15,2,rs.getString("VlrPagoItRec")),i,5);
        tabConsulta.setValor((rs.getDate("DtPagoItRec") != null ? Funcoes.sqlDateToStrDate(rs.getDate("DtPagoItRec")) : ""),i,6);
        tabConsulta.setValor(new Integer(rs.getInt(9)),i,7);
        tabConsulta.setValor(Funcoes.strDecimalToStrCurrency(15,2,rs.getString("VlrJurosItRec")),i,8);
        tabConsulta.setValor((rs.getString("Serie") != null ? rs.getString("Serie") : ""),i,9);
        tabConsulta.setValor(""+rs.getInt("CodVenda"),i,10);
        tabConsulta.setValor(rs.getString(11) != null ? rs.getString(11) : "",i,11);
        tabConsulta.setValor(rs.getString("ObsRec") != null ? rs.getString("ObsRec") : "",i,12);
        vCodRec.addElement(rs.getString("CodRec"));        
        vNParcItRec.addElement(rs.getString("NParcItRec"));        
      }
      rs.close();
      ps.close();
      if (!con.getAutoCommit())
      	con.commit();
    }
    catch(SQLException err) {
       Funcoes.mensagemErro(this,"Erro ao montar a tabela de consulta!\n"+err.getMessage());
       err.printStackTrace();
    }
  }
 private void carregaGridBaixa() {
    vNParcBaixa.clear();
    vNumContas.clear();
    vCodPlans.clear();
	vCodCCs.clear();
    tabBaixa.limpa();
    String sSQL = "SELECT IR.DTVENCITREC,IR.STATUSITREC,"+
                  "R.CODREC,IR.DOCLANCAITREC,"+
                  "R.CODVENDA,IR.VLRPARCITREC,IR.DTPAGOITREC,"+
                  "IR.VLRPAGOITREC,IR.VLRAPAGITREC,IR.NUMCONTA,"+
                  "IR.VLRDESCITREC,(SELECT C.DESCCONTA FROM FNCONTA C "+
                  "WHERE C.NUMCONTA = IR.NUMCONTA AND C.CODEMP=IR.CODEMPCA" +
                  " AND C.CODFILIAL=IR.CODFILIALCA),IR.CODPLAN,"+
                  "(SELECT P.DESCPLAN FROM FNPLANEJAMENTO P "+
                  "WHERE P.CODPLAN = IR.CODPLAN AND P.CODEMP=IR.CODEMPPN " +
                  "AND P.CODFILIAL=IR.CODFILIALPN),IR.CODCC,(SELECT CC.DESCCC " +
                  "FROM FNCC CC WHERE CC.CODCC = IR.CODCC" +
                  " AND CC.CODEMP=IR.CODEMPCC AND CC.CODFILIAL=IR.CODFILIALCC),IR.OBSITREC,"+
                  "IR.NPARCITREC,IR.VLRJUROSITREC,"+
                  "IR.DTITREC, V.DOCVENDA FROM FNITRECEBER IR,FNRECEBER R, VDVENDA V "+
                  "WHERE IR.CODREC=R.CODREC AND V.CODVENDA=R.CODVENDA " +
                  " AND V.CODEMP=R.CODEMPVD AND V.CODFILIAL=R.CODFILIALVD " +
                  " AND R.CODREC = ? AND R.CODEMP=? AND R.CODFILIAL=? "+
                  "ORDER BY IR.DTVENCITREC,IR.STATUSITREC";
      try {
        PreparedStatement ps = con.prepareStatement(sSQL);
        ps.setInt(1,txtCodRecBaixa.getVlrInteger().intValue());
		ps.setInt(2,Aplicativo.iCodEmp);
		ps.setInt(3,ListaCampos.getMasterFilial("FNRECEBER"));
        ResultSet rs = ps.executeQuery();
        for (int i=0; rs.next(); i++) { 
          tabBaixa.adicLinha();
          tabBaixa.setValor((rs.getDate("DtVencItRec") != null ? Funcoes.sqlDateToStrDate(rs.getDate("DtVencItRec")) : ""),i,0);
          tabBaixa.setValor(rs.getString("StatusItRec"),i,1);
          tabBaixa.setValor(rs.getString("NParcItRec"),i,2);
          tabBaixa.setValor((rs.getString("DocLancaItRec") != null ? rs.getString("DocLancaItRec") : rs.getString("DocVenda")),i,3);
          tabBaixa.setValor(""+rs.getInt("CodVenda"),i,4);
          tabBaixa.setValor(Funcoes.strDecimalToStrCurrency(15,2,rs.getString("VlrParcItRec")),i,5);
          tabBaixa.setValor((rs.getDate("DtPagoItRec") != null ? Funcoes.sqlDateToStrDate(rs.getDate("DtPagoItRec")) : ""),i,6);
          tabBaixa.setValor(Funcoes.strDecimalToStrCurrency(15,2,rs.getString("VlrPagoItRec")),i,7);
          tabBaixa.setValor(Funcoes.strDecimalToStrCurrency(15,2,rs.getString("VlrDescItRec")),i,8);
          tabBaixa.setValor(Funcoes.strDecimalToStrCurrency(15,2,rs.getString("VlrJurosItRec")),i,9);
          tabBaixa.setValor(Funcoes.strDecimalToStrCurrency(15,2,rs.getString("VlrApagItRec")),i,10);
          tabBaixa.setValor(rs.getString(12) != null ? rs.getString(12) : "",i,11);
          tabBaixa.setValor(rs.getString(14) != null ? rs.getString(14) : "",i,12);
		  tabBaixa.setValor(rs.getString(16) != null ? rs.getString(16) : "",i,13);
		  tabBaixa.setValor(rs.getString("ObsItRec") != null ? rs.getString("ObsItRec") : "",i,14);
          vNParcBaixa.addElement(rs.getString("NParcItRec"));
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
  private void carregaGridManut(boolean bAplicFiltros) {
  	String sWhereManut = "";
    tabManut.limpa();
    vNumContas.clear();
    vCodPlans.clear();
    vCodCCs.clear();
    vCodBOs.clear();
    vDtEmiss.clear();
    if (bAplicFiltros) {
      if (!validaPeriodo())
      	return;
	  sWhereManut = " AND "+(rgData.getVlrString().equals("V")?"IR.DTVENCITREC":"IR.DTITREC")+
					   " BETWEEN ? AND ? AND R.CODEMP=? AND R.CODFILIAL=?";
	  if (!rgPg.getVlrString().equals("TT")){
		  sWhereManut += " AND IR.STATUSITREC='"+rgPg.getVlrString()+"'";
	  }
	  					   
	  if (!rgVenc.getVlrString().equals("TT")){
	    sWhereManut += " AND IR.DTVENCITREC";
		if (rgVenc.getVlrString().equals("VE"))
		  sWhereManut += " <'"+Funcoes.dateToStrDB(new Date())+"'";
		else
		  sWhereManut += " >='"+Funcoes.dateToStrDB(new Date())+"'";			
	  }					   
	  if (!txtCodCliFiltro.getVlrString().trim().equals("")) {
		  sWhereManut += " AND R.CODCLI="+txtCodCliFiltro.getVlrString();
	  }
    }
    else
      sWhereManut = " AND R.CODREC=? AND R.CODEMP=? AND R.CODFILIAL=? ";
            
    String sSQL = "SELECT IR.DTVENCITREC,IR.STATUSITREC,"+
                  "R.CODCLI,C.RAZCLI,R.CODREC,IR.DOCLANCAITREC,"+
                  "R.CODVENDA,IR.VLRPARCITREC,IR.DTPAGOITREC,"+
                  "IR.VLRPAGOITREC,IR.VLRAPAGITREC,IR.NUMCONTA,"+
                  "IR.VLRDESCITREC,(SELECT C.DESCCONTA FROM FNCONTA C "+
                  "WHERE C.NUMCONTA = IR.NUMCONTA AND C.CODEMP=IR.CODEMPCA " +
                  "AND C.CODEMP=IR.CODEMPCA),IR.CODPLAN,"+
                  "(SELECT P.DESCPLAN FROM FNPLANEJAMENTO P "+
                  "WHERE P.CODPLAN = IR.CODPLAN AND P.CODEMP=IR.CODEMPPN " +
                  "AND P.CODFILIAL=IR.CODFILIALPN),IR.CODCC," +
                  "(SELECT CC.DESCCC FROM FNCC CC WHERE CC.CODCC = IR.CODCC" +
                  " AND CC.CODEMP=IR.CODEMPCC AND CC.CODFILIAL=IR.CODFILIALCC),IR.OBSITREC,"+
                  "IR.NPARCITREC,IR.VLRJUROSITREC,(SELECT VD.DOCVENDA "+
                  "FROM VDVENDA VD WHERE VD.CODVENDA=R.CODVENDA " +
                  "AND VD.CODEMP=R.CODEMPVA AND VD.CODFILIAL=R.CODFILIALVA),"+
                  "IR.DTITREC,(SELECT BO.NOMEBANCO FROM FNBANCO BO WHERE " +
                  "BO.CODBANCO = IR.CODBANCO AND BO.CODEMP=IR.CODEMPBO AND " +
                  "BO.CODFILIAL=IR.CODFILIALBO),IR.CODBANCO FROM FNITRECEBER IR," +
                  "FNRECEBER R, VDCLIENTE C WHERE R.CODREC=IR.CODREC AND "+
			      "C.CODCLI=R.CODCLI "+
				  sWhereManut+
                  "ORDER BY IR.DTVENCITREC,IR.STATUSITREC";
    try {
      PreparedStatement ps = con.prepareStatement(sSQL);
      if (bAplicFiltros) { 
        ps.setDate(1,Funcoes.dateToSQLDate(dIniManut));
        ps.setDate(2,Funcoes.dateToSQLDate(dFimManut));
	    ps.setInt(3,Aplicativo.iCodEmp);
		ps.setInt(4,ListaCampos.getMasterFilial("FNRECEBER"));
      }
      else {
        ps.setInt(1,txtCodRecManut.getVlrInteger().intValue());
  	    ps.setInt(2,Aplicativo.iCodEmp);
	    ps.setInt(3,ListaCampos.getMasterFilial("FNRECEBER"));
      }
      ResultSet rs = ps.executeQuery();
	  double bdVlrAReceber = 0.0;
	  double bdVlrParc =  0.0;
      for (int i=0; rs.next(); i++) { 
        tabManut.adicLinha();

		bdVlrAReceber = Funcoes.strDecimalToBigDecimal(2,rs.getString("VlrApagItRec")).doubleValue();
	    bdVlrParc = Funcoes.strDecimalToBigDecimal(2,rs.getString("VlrParcItRec")).doubleValue();
          
		if ( rs.getString("StatusItRec").equals("RP") )
		  imgColuna = imgPago;
	    else if ( rs.getString("StatusItRec").equals("RL") )
		  imgColuna = imgPagoParcial;
		else if (rs.getDate("DtVencItRec").before(new Date())) 
		  imgColuna = imgVencido;	          
	    else if (rs.getDate("DtVencItRec").after(new Date()))
		  imgColuna = imgNaoVencido;     
     
		tabManut.setValor(imgColuna,i,0);
          
        tabManut.setValor((rs.getDate("DtVencItRec") != null ? Funcoes.sqlDateToStrDate(rs.getDate("DtVencItRec")) : ""),i,1);
        tabManut.setValor(rs.getString("StatusItRec"),i,2);
        tabManut.setValor(rs.getString("CodCli"),i,3);
        tabManut.setValor(rs.getString("RazCli"),i,4);
        tabManut.setValor(rs.getString("CodRec"),i,5);
        tabManut.setValor(rs.getString("NParcItRec"),i,6);
        tabManut.setValor((rs.getString("DocLancaItRec") != null ? rs.getString("DocLancaItRec") : ""),i,7);
        tabManut.setValor(Funcoes.copy(rs.getString(22),0,10).trim(),i,8);
        tabManut.setValor(Funcoes.strDecimalToStrCurrency(15,2,rs.getString("VlrParcItRec")),i,9);
        tabManut.setValor((rs.getDate("DtPagoItRec") != null ? Funcoes.sqlDateToStrDate(rs.getDate("DtPagoItRec")) : ""),i,10);
        tabManut.setValor(Funcoes.strDecimalToStrCurrency(15,2,rs.getString("VlrPagoItRec")),i,11);
        tabManut.setValor(Funcoes.strDecimalToStrCurrency(15,2,rs.getString("VlrDescItRec")),i,12);
        tabManut.setValor(Funcoes.strDecimalToStrCurrency(15,2,rs.getString("VlrJurosItRec")),i,13);
        tabManut.setValor(Funcoes.strDecimalToStrCurrency(15,2,rs.getString("VlrApagItRec")),i,14);
        tabManut.setValor(rs.getString(14) != null ? rs.getString(14) : "",i,15);
        tabManut.setValor(rs.getString(16) != null ? rs.getString(16) : "",i,16);
		tabManut.setValor(rs.getString(18) != null ? rs.getString(18) : "",i,17);
        tabManut.setValor(rs.getString(24) != null ? rs.getString(24) : "",i,18);
		tabManut.setValor(rs.getString("ObsItRec") != null ? rs.getString("ObsItRec") : "",i,19);
        vNumContas.addElement(rs.getString("NumConta") != null ? rs.getString("NumConta") : "");
        vCodPlans.addElement(rs.getString("CodPlan") != null ? rs.getString("CodPlan") : "");
        vCodCCs.addElement(rs.getString("CodCC") != null ? rs.getString("CodCC") : "");
        vCodBOs.addElement(rs.getString("CodBanco") != null ? rs.getString("CodBanco") : "");
        vDtEmiss.addElement(rs.getDate("DtItRec") != null ? Funcoes.sqlDateToStrDate(rs.getDate("DtItRec")) : "");
      }
      rs.close();
      ps.close();
      if (!con.getAutoCommit())
      	con.commit();
    }
    catch(SQLException err) {
       Funcoes.mensagemErro(this,"Erro ao montar a tabela de baixa!\n"+err.getMessage());
    }
  }
  private void baixaConsulta() {
    if (tabConsulta.getLinhaSel() != -1) {
      txtCodRecBaixa.setVlrString((String)vCodRec.elementAt(tabConsulta.getLinhaSel()));
      int iNParc = (new Integer((String)vNParcItRec.elementAt(tabConsulta.getLinhaSel()))).intValue();
      lcRecBaixa.carregaDados();
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
    if ((cOrig == 'M') & (tabManut.getLinhaSel() > -1)) {
      int iLin = tabManut.getLinhaSel();
	  if (iLin < 0)
        return;
      iCodRec = Integer.parseInt((String)tabManut.getValor(iLin,5));
      iNParcItRec = Integer.parseInt(""+tabManut.getValor(iLin,6));
      String[] sVals = new String[15];
      String[] sRets = null;
      DLBaixaRec dl = new DLBaixaRec(this);
      sVals[0] = ""+tabManut.getValor(iLin,3);
      sVals[1] = ""+tabManut.getValor(iLin,4);
      sVals[2] = ""+vNumContas.elementAt(iLin);
      sVals[3] = ""+vCodPlans.elementAt(iLin);
      sVals[4] = ""+(tabManut.getValor(iLin,7).equals("") ? tabManut.getValor(iLin,8) : tabManut.getValor(iLin,7));
      sVals[5] = ""+vDtEmiss.elementAt(iLin);
      sVals[6] = ""+tabManut.getValor(iLin,1);
      sVals[7] = ""+tabManut.getValor(iLin,9);
      sVals[8] = ""+tabManut.getValor(iLin,12);
      sVals[9] = ""+tabManut.getValor(iLin,13);
      sVals[10] = ""+tabManut.getValor(iLin,14);
	  sVals[13] = ""+vCodCCs.elementAt(iLin);
      if (((String)tabManut.getValor(iLin,10)).trim().equals("")) {
         sVals[11] = Funcoes.dateToStrDate(new Date());
         sVals[12] = ""+tabManut.getValor(iLin,11);
      }
      else {
         sVals[11] = ""+tabManut.getValor(iLin,10);
         sVals[12] = ""+tabManut.getValor(iLin,11);
      }
      if (((String)tabManut.getValor(iLin,19)).trim().equals(""))
         sVals[14] = "RECEBIMENTO REF. AO PED.: "+tabManut.getValor(iLin,8);
      else
         sVals[14] = ""+tabManut.getValor(iLin,19);
      
      dl.setValores(sVals);
      dl.setConexao(con);
      dl.setVisible(true);
      if (dl.OK) {
        sRets = dl.getValores();
        String sSQL = "UPDATE FNITRECEBER SET NUMCONTA=?,CODEMPCA=?,CODFILIALCA=?,CODPLAN=?,CODEMPPN=?,CODFILIALPN=?,"+
                      "DOCLANCAITREC=?,DTPAGOITREC=?,VLRPAGOITREC=VLRPAGOITREC+?,VLRDESCITREC=?,VLRJUROSITREC=?,ANOCC=?," +
                      "CODCC=?,CODEMPCC=?,CODFILIALCC=?,OBSITREC=?,STATUSITREC='RP' WHERE CODREC=? AND NPARCITREC=? AND " +
                      "CODEMP=? AND CODFILIAL=?";
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
          ps.setBigDecimal(10,Funcoes.strCurrencyToBigDecimal(sRets[5]));
          ps.setBigDecimal(11,Funcoes.strCurrencyToBigDecimal(sRets[6]));
          if (!sRets[7].trim().equals("")) {
		    ps.setInt(12,iAnoCC);
		    ps.setString(13,sRets[7]);
		    ps.setInt(14,Aplicativo.iCodEmp);
		    ps.setInt(15,ListaCampos.getMasterFilial("FNCC"));
		  }
          else {
          	ps.setNull(12,Types.INTEGER);
          	ps.setNull(13,Types.CHAR);
          	ps.setNull(14,Types.INTEGER);
          	ps.setNull(15,Types.INTEGER);
          }
          System.out.println("Observacoes");
          System.out.println(sRets[8]); 
          ps.setString(16,sRets[8]);
          ps.setInt(17,iCodRec);
          ps.setInt(18,iNParcItRec);
		  ps.setInt(19,Aplicativo.iCodEmp);
		  ps.setInt(20,ListaCampos.getMasterFilial("FNRECEBER"));
          ps.executeUpdate();
          if (!con.getAutoCommit())
          	con.commit();
        }
        catch(SQLException err) {
          Funcoes.mensagemErro(this,"Erro ao baixar parcela!\n"+err.getMessage());
        }
      }
      dl.dispose();
      carregaGridManut(bBuscaAtual);
    }
    else if ((cOrig == 'B') & (tabBaixa.getLinhaSel() > -1)) {
      int iLin = tabBaixa.getLinhaSel();
      iCodRec = txtCodRecBaixa.getVlrInteger().intValue();
      iNParcItRec = Integer.parseInt(""+tabBaixa.getValor(iLin,2));
      String[] sVals = new String[15];
      String[] sRets = null;
      DLBaixaRec dl = new DLBaixaRec(this);
      sVals[0] = ""+txtCodCliBaixa.getVlrString();
      sVals[1] = ""+txtRazCliBaixa.getVlrString();
      sVals[2] = ""+vNumContas.elementAt(iLin);
      sVals[3] = ""+vCodPlans.elementAt(iLin);
      sVals[4] = ""+tabBaixa.getValor(iLin,3);
      sVals[5] = ""+txtDtEmisBaixa.getVlrString();
      sVals[6] = ""+tabBaixa.getValor(iLin,0);
      sVals[7] = ""+tabBaixa.getValor(iLin,5);
      sVals[8] = ""+tabBaixa.getValor(iLin,8);
      sVals[9] = ""+tabBaixa.getValor(iLin,9);
      sVals[10] = ""+tabBaixa.getValor(iLin,10);
	  sVals[13] = ""+vCodCCs.elementAt(iLin);
      if (((String)tabBaixa.getValor(iLin,6)).trim().equals("")) {
        sVals[11] = Funcoes.dateToStrDate(new Date());
        sVals[12] = ""+tabBaixa.getValor(iLin,7);
      }
      else {
        sVals[11] = ""+tabBaixa.getValor(iLin,6);
        sVals[12] = ""+tabBaixa.getValor(iLin,7);
      }
      if (((String)tabBaixa.getValor(iLin,14)).trim().equals(""))
        sVals[14] = "RECEBIMENTO REF. AO PED.: "+txtCodVendaBaixa.getVlrString();
      else
 		sVals[14] = ""+tabBaixa.getValor(iLin,14);
      
      dl.setValores(sVals);
      dl.setConexao(con);
      dl.setVisible(true);
      if (dl.OK) {
        sRets = dl.getValores();
        String sSQL = "UPDATE FNITRECEBER SET NUMCONTA=?,CODEMPCA=?,CODFILIALCA=?,CODPLAN=?,CODEMPPN=?,CODFILIALPN=?,"+
                      "ANOCC=?,CODCC=?,CODEMPCC=?,CODFILIALCC=?,DOCLANCAITREC=?,DTPAGOITREC=?,VLRPAGOITREC=VLRPAGOITREC+?,"+
                      "VLRDESCITREC=?,VLRJUROSITREC=?,OBSITREC=?,STATUSITREC='RP' WHERE CODREC=? AND NPARCITREC=? AND " +
                      "CODEMP=? AND CODFILIAL=?";
        try {
          PreparedStatement ps = con.prepareStatement(sSQL);
		  ps.setString(1,sRets[0]);
		  ps.setInt(2,Aplicativo.iCodEmp);
		  ps.setInt(3,ListaCampos.getMasterFilial("FNCONTA"));
		  ps.setString(4,sRets[1]);
		  ps.setInt(5,Aplicativo.iCodEmp);
		  ps.setInt(6,ListaCampos.getMasterFilial("FNPLANEJAMENTO"));
		  if (!sRets[7].trim().equals("")) {
		    ps.setInt(7,iAnoCC);
		    ps.setString(8,sRets[7]);
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
          ps.setBigDecimal(14,Funcoes.strCurrencyToBigDecimal(sRets[5]));
          ps.setBigDecimal(15,Funcoes.strCurrencyToBigDecimal(sRets[6]));
          ps.setString(16,sRets[8]);
          ps.setInt(17,iCodRec);
          ps.setInt(18,iNParcItRec);
		  ps.setInt(19,Aplicativo.iCodEmp);
		  ps.setInt(20,ListaCampos.getMasterFilial("FNRECEBER"));
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
 private void novo() { 
    DLNovoRec dl = new DLNovoRec(this);
    dl.setConexao(con);
    dl.setVisible(true);
    dl.dispose();
    carregaGridManut(bBuscaAtual);
  }
 private void editar() {
 	String sStatusItRec = null;
 	if (tabManut.getLinhaSel() > -1) {
 		sStatusItRec = ""+tabManut.getValor(tabManut.getLinhaSel(),2);
 		if ( (sStatusItRec.equals("R1")) || (sStatusItRec.equals("RL"))) { 
 			int iLin = tabManut.getLinhaSel();
 			iCodRec = Integer.parseInt((String)tabManut.getValor(iLin,5));
 			iNParcItRec = Integer.parseInt(""+tabManut.getValor(iLin,6));
 			String[] sVals = new String[13];
 			String[] sRets = null;
 			DLEditaRec dl = new DLEditaRec(this);
 			sVals[0] = ""+tabManut.getValor(iLin,3);
 			sVals[1] = ""+tabManut.getValor(iLin,4);
 			sVals[2] = ""+vNumContas.elementAt(iLin);
 			sVals[3] = ""+vCodPlans.elementAt(iLin);
 			sVals[4] = ""+vCodCCs.elementAt(iLin);
 			if (((String)tabManut.getValor(iLin,7)).trim().equals("")) {
 				sVals[5] = ""+tabManut.getValor(iLin,8);
 			}
 			else {
 				sVals[5] = ""+tabManut.getValor(iLin,7);
 			}
 			sVals[6] = ""+vDtEmiss.elementAt(iLin);
 			sVals[7] = ""+tabManut.getValor(iLin,1);
 			sVals[8] = ""+tabManut.getValor(iLin,13);
 			sVals[9] = ""+tabManut.getValor(iLin,12);
 			sVals[10] = ""+tabManut.getValor(iLin,14);
 			if (((String)tabManut.getValor(iLin,19)).trim().equals("")) {
 				sVals[11] = "PAGAMENTO REF. A VENDA: "+tabManut.getValor(iLin,8);
 			}
 			else {
 				sVals[11] = ""+tabManut.getValor(iLin,19);
 			}
            sVals[12] = ""+vCodBOs.elementAt(iLin);
 			dl.setValores(sVals);
 			dl.setConexao(con);
 			dl.setVisible(true);
 			if (dl.OK) {
 				sRets = dl.getValores();
                String sSQL = "UPDATE FNITRECEBER SET NUMCONTA=?,CODEMPCA=?,CODFILIALCA=?,CODPLAN=?,CODEMPPN=?,CODFILIALPN=?,"+
                "ANOCC=?,CODCC=?,CODEMPCC=?,CODFILIALCC=?,DOCLANCAITREC =?,VLRJUROSITREC=?,"+
                "VLRDESCITREC=?,DTVENCITREC=?,OBSITREC=?,CODEMPBO=?,CODFILIALBO=?,CODBANCO=?"+
                " WHERE CODREC=? AND NPARCITREC=? AND CODEMP=? AND CODFILIAL=?";
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
 						ps.setDate(14,Funcoes.strDateToSqlDate(sRets[6]));
 					else
 						ps.setNull(14,Types.DECIMAL);
 					System.out.println("Observacoes");
 					System.out.println(sRets[7]); // Observações
 					if (!sRets[7].trim().equals(""))
 						ps.setString(15,sRets[7]);
 					else
 						ps.setNull(15,Types.CHAR);
                    if (!sRets[8].trim().equals("")) {
                        ps.setInt(16,Aplicativo.iCodEmp);
                        ps.setInt(17,ListaCampos.getMasterFilial("FNBANCO"));
                        ps.setString(18,sRets[8]);
                    }
                    else {
                         ps.setNull(16,Types.INTEGER);
                         ps.setNull(17,Types.INTEGER);
                         ps.setNull(18,Types.CHAR);
                    }
 					ps.setInt(19,iCodRec);
 					ps.setInt(20,iNParcItRec);
 					ps.setInt(21,Aplicativo.iCodEmp);
 					ps.setInt(22,ListaCampos.getMasterFilial("FNRECEBER"));
 					ps.executeUpdate();
 				}
 				catch(SQLException err) {
 					Funcoes.mensagemErro(this,"Erro ao editar parcela!\n"+err.getMessage());
 					err.printStackTrace();
 				}
 			}
 			dl.dispose();
 			carregaGridManut(bBuscaAtual);
 		}
 	}
 }
 private void excluir() { 
	if (tabManut.getLinhaSel() > -1) {
	  if (((""+tabManut.getValor(tabManut.getLinhaSel(),2)).equals("R1")) && 
		  ((""+tabManut.getValor(tabManut.getLinhaSel(),8)).equals(""))) {
		if (Funcoes.mensagemConfirma(this, "Deseja realmente excluir esta conta e todas as suas parcelas?")==0) {
		  String sSQL = "DELETE FROM FNRECEBER WHERE CODREC=? AND CODEMP=? AND CODFILIAL=?";
		  try {
			PreparedStatement ps = con.prepareStatement(sSQL);
			ps.setInt(1,Integer.parseInt(""+tabManut.getValor(tabManut.getLinhaSel(),5)));
			ps.setInt(2,Aplicativo.iCodEmp);
			ps.setInt(3,ListaCampos.getMasterFilial("FNRECEBER"));
			ps.executeUpdate();
			if (!con.getAutoCommit())
				con.commit();
			carregaGridManut(bBuscaAtual);
		  }
		  catch(SQLException err) {
			Funcoes.mensagemErro(this,"Erro ao excluir parcela!\n"+err.getMessage());
		  }
		}
	  }   
	}
  }
  private void estorno() {
    if (tabManut.getLinhaSel() > -1) {
      if ((""+tabManut.getValor(tabManut.getLinhaSel(),2)).equals("RP")) { 
        int iLin = tabManut.getLinhaSel();
        iCodRec = Integer.parseInt((String)tabManut.getValor(iLin,5));
        iNParcItRec = Integer.parseInt(""+tabManut.getValor(iLin,6));
        String sSQL = "UPDATE FNITRECEBER SET STATUSITREC='R1' WHERE CODREC=? AND NPARCITREC=? AND CODEMP=? AND CODFILIAL=?";
        try {
          PreparedStatement ps = con.prepareStatement(sSQL);
          ps.setInt(1,iCodRec);
          ps.setInt(2,iNParcItRec);
		  ps.setInt(3,Aplicativo.iCodEmp);
		  ps.setInt(4,ListaCampos.getMasterFilial("FNRECEBER"));
          ps.executeUpdate();
          if (!con.getAutoCommit())
          	con.commit();
        }
        catch(SQLException err) {
          Funcoes.mensagemErro(this,"Erro ao estornar registro!\n"+err.getMessage());
        }
        carregaGridManut(bBuscaAtual);
      }
    }
  }
  
  
  private void consBaixa() {
  	int iLin = tabBaixa.getLinhaSel();
  	if (iLin < 0)
  		return;
  	DLConsultaBaixa dl = new DLConsultaBaixa(this,con,txtCodRecBaixa.getVlrInteger().intValue(),Integer.parseInt(tabBaixa.getValor(iLin,2).toString()));
  	dl.setValores(new BigDecimal[] {
  			Funcoes.strCurrencyToBigDecimal(tabBaixa.getValor(iLin,5).toString()),
  			Funcoes.strCurrencyToBigDecimal(tabBaixa.getValor(iLin,7).toString()),
  			Funcoes.strCurrencyToBigDecimal(tabBaixa.getValor(iLin,8).toString()),
  			Funcoes.strCurrencyToBigDecimal(tabBaixa.getValor(iLin,9).toString()),
  			Funcoes.strCurrencyToBigDecimal(tabBaixa.getValor(iLin,10).toString())
  	});
  	dl.setVisible(true);
  	dl.dispose();
  }
  
  
  public void afterCarrega(CarregaEvent cevt) {
  	if (cevt.getListaCampos() == lcRecBaixa) {
      tabBaixa.limpa();
      carregaGridBaixa();   
  	}
  	else if (cevt.getListaCampos() == lcRecManut) {
      bBuscaAtual = false;
      carregaGridManut(bBuscaAtual);
  	}

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
      bBuscaAtual = true;
      carregaGridManut(bBuscaAtual);
    }
    else if (evt.getSource() == btEstManut) {
      estorno();
    }
    else if (evt.getSource() == btConsBaixa) {
 	  consBaixa();
 	}
    else if (evt.getSource() == btConsVenda) {
    	 //DLConsultaVenda dl = new DLConsultaVenda(this,con,tabConsulta.getValor(iLin,3));
    	   int iLin = tabConsulta.getLinhaSel();
           if (iLin >= 0) {
             int iCodVenda = Integer.parseInt((String)tabConsulta.getValor(iLin,10));
              DLConsultaVenda dl = new DLConsultaVenda(this,con,iCodVenda);
          
              dl.setVisible(true);
        	  dl.dispose();
           }
 	 
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
    con = cn;
    lcCli.setConexao(cn);
    lcCliBaixa.setConexao(cn);
    lcCliFiltro.setConexao(cn);
    lcCliManut.setConexao(cn);
    lcVendaBaixa.setConexao(cn);
    lcBancoBaixa.setConexao(cn);
    lcRecBaixa.setConexao(cn);
    lcRecManut.setConexao(cn);
	iAnoCC = buscaAnoBaseCC();
  }
  public void keyReleased(KeyEvent kevt) { }
  public void keyTyped(KeyEvent kevt) { }
  public void beforeCarrega(CarregaEvent cevt) {  }
}


