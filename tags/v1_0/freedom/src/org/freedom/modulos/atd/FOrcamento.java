/**
 * @version 02/11/2003 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.atd <BR>
 * Classe: @(#)FOrcamento.java <BR>
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

package org.freedom.modulos.atd;
import java.awt.BorderLayout;
import java.awt.Dimension;
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
import java.util.Date;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.freedom.layout.LeiauteGR;
import org.freedom.acao.CarregaEvent;
import org.freedom.acao.CarregaListener;
import org.freedom.acao.DeleteEvent;
import org.freedom.acao.DeleteListener;
import org.freedom.acao.InsertEvent;
import org.freedom.acao.InsertListener;
import org.freedom.acao.PostEvent;
import org.freedom.acao.PostListener;
import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.Painel;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FDetalhe;
import org.freedom.telas.FObservacao;
import org.freedom.telas.FPrinterJob;
import org.freedom.bmps.Icone;
public class FOrcamento extends FDetalhe implements PostListener,CarregaListener,FocusListener,ActionListener,InsertListener, DeleteListener {
  private Painel pinCab = new Painel();
  private Painel pinDet = new Painel();
  private Painel pinTot = new Painel(200,200);
  private JPanel pnTot = new JPanel(new GridLayout(1,1));
  private JPanel pnCenter = new JPanel(new BorderLayout());
  
  private JButton btObs = new JButton(Icone.novo("btObs.gif"));
  private JButton btOrc = new JButton(Icone.novo("btImprimeOrc.gif"));
  private JButton btOrcTst = new JButton(Icone.novo("btFisio.gif"));
  private JButton btOrcTst2 = new JButton(Icone.novo("btEmprestimo.gif"));
  
  private JButton btFechaOrc = new JButton(Icone.novo("btOk.gif"));
  private JTextFieldPad txtCodOrc = new JTextFieldPad();
  private JTextFieldPad txtDtOrc = new JTextFieldPad();
  private JTextFieldPad txtDtVencOrc = new JTextFieldPad();
  private JTextFieldPad txtCodPlanoPag = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldPad txtCodAtend = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldPad txtCodConv = new JTextFieldPad();
  private JTextFieldPad txtCodItOrc = new JTextFieldPad();
  private JTextFieldPad txtQtdItOrc = new JTextFieldPad();
  private JTextFieldPad txtCodProd = new JTextFieldPad();
  private JTextFieldPad txtRefProd = new JTextFieldPad();
  private JTextFieldPad txtCodBarras = new JTextFieldPad(JTextFieldPad.TP_STRING,13,0);
  private JTextFieldPad txtPrecoItOrc = new JTextFieldPad();
  private JTextFieldPad txtPercDescItOrc = new JTextFieldPad();
  private JTextFieldPad txtVlrDescItOrc = new JTextFieldPad();
  private JTextFieldPad txtVlrLiqItOrc = new JTextFieldPad();
  private JTextFieldPad txtVlrEdDescOrc = new JTextFieldPad();
  private JTextFieldPad txtVlrEdAdicOrc = new JTextFieldPad();
  private JTextFieldPad txtVlrDescOrc = new JTextFieldPad();
  private JTextFieldPad txtVlrAdicOrc = new JTextFieldPad();
  private JTextFieldPad txtVlrLiqOrc = new JTextFieldPad();
  private JTextFieldPad txtVlrProdItOrc = new JTextFieldPad();
  private JTextFieldPad txtObsItOrc = new JTextFieldPad();
  private JTextFieldPad txtVlrProdOrc = new JTextFieldPad();
  private JTextFieldPad txtStatusOrc = new JTextFieldPad();
  private JTextFieldPad txtCodTpConv = new JTextFieldPad();
  private JTextFieldPad txtCodCli = new JTextFieldPad();
  private JTextFieldPad txtTxt01 = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldPad txtCodVend = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldPad txtCodEnc = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldFK txtNomeVend = new JTextFieldFK(JTextFieldPad.TP_STRING,50,0);
  private JTextFieldFK txtDescConv = new JTextFieldFK();
  private JTextFieldFK txtDescProd = new JTextFieldFK();
  private JTextFieldFK txtDescPlanoPag = new JTextFieldFK(JTextFieldPad.TP_STRING,40,0);
  private JTextFieldFK txtDescAtend = new JTextFieldFK(JTextFieldPad.TP_STRING,40,0);
  private JTextFieldFK txtDescTipoConv = new JTextFieldFK(JTextFieldPad.TP_STRING,40,0);
  private JTextFieldFK txtRazCli = new JTextFieldFK(JTextFieldPad.TP_STRING,40,0);
  private JTextFieldFK txtNomeEnc = new JTextFieldFK(JTextFieldPad.TP_STRING,50,0);
  private ListaCampos lcConv = new ListaCampos(this,"CV");
  private ListaCampos lcProd = new ListaCampos(this,"PD");
  private ListaCampos lcProd2 = new ListaCampos(this,"PD");
  private ListaCampos lcOrc2 = new ListaCampos(this);
  private ListaCampos lcPlanoPag = new ListaCampos(this,"PG");
  private ListaCampos lcAtend = new ListaCampos(this,"AE");
  private ListaCampos lcVend = new ListaCampos(this,"VD");
  private ListaCampos lcTipoConv = new ListaCampos(this,"TC");
  private ListaCampos lcCliente = new ListaCampos(this, "CL");
  private ListaCampos lcEnc = new ListaCampos(this,"EC");
  private Connection con = null;
  private FPrinterJob dl = null;
  Object[] oPrefs = null;
  boolean bCtrl = false;
  Vector vParamOrc = new Vector();
  public FOrcamento() {
    setTitulo("Orçamento");
    setAtribos(15,8,685,430);
  }
  
  private void montaOrcamento() {
	oPrefs = prefs(); //Carrega as preferências
    if (!oPrefs[3].equals("")) { 
	   txtTxt01 = new JTextFieldPad();
	   txtTxt01.setTipo(JTextFieldPad.TP_STRING,20,0);
	}

	pnMaster.remove(2);  //Remove o Painel prédefinido da class FDados
	pnGImp.removeAll(); //Remove os botões de impressão para adicionar logo embaixo
	pnGImp.setLayout(new GridLayout(1,3)); //redimensiona o painel de impressão
	pnGImp.setPreferredSize(new Dimension( 210, 26));
	pnGImp.add(btPrevimp);
	pnGImp.add(btImp);
	pnGImp.add(btFechaOrc);
	pnGImp.add(btObs);//Agora o painel está maior
	pnGImp.add(btOrc);//Botão provisório para emissão de orçamento padrão
	pnGImp.add(btOrcTst);//Botão para teste de laudo fisioterapia
	pnGImp.add(btOrcTst2);//Outro botão de teste para contrato

	pnTot.setPreferredSize(new Dimension(120,200)); //Painel de Totais
	pnTot.add(pinTot);
	pnCenter.add(pnTot,BorderLayout.EAST);
	pnCenter.add(spTab,BorderLayout.CENTER);

	JPanel pnLab = new JPanel(new GridLayout(1,1));
	pnLab.add(new JLabel(" Totais:"));    //Label do painel de totais

	pnMaster.add(pnCenter,BorderLayout.CENTER);
    
	lcAtend.add(new GuardaCampo( txtCodAtend, 7, 100, 80, 20, "CodAtend", "Código", true, false, null, JTextFieldPad.TP_INTEGER,false),"txtCodAtendx");
	lcAtend.add(new GuardaCampo( txtDescAtend, 90, 100, 207, 20, "NomeAtend", "Nome", false, false, null, JTextFieldPad.TP_STRING,false),"txtDescAtendx");
	txtCodAtend.setTabelaExterna(lcAtend);
	txtDescAtend.setListaCampos(lcAtend);
	lcAtend.montaSql(false, "ATENDENTE", "AT");
	lcAtend.setQueryCommit(false);
	lcAtend.setReadOnly(true);

	lcTipoConv.add(new GuardaCampo( txtCodTpConv, 7, 100, 80, 20, "CodTpConv", "Código", true, false, null, JTextFieldPad.TP_INTEGER,false),"txtCodTpConv");
	lcTipoConv.add(new GuardaCampo( txtDescTipoConv, 400, 80, 150, 20, "DescTpConv", "Nome", false, false, null, JTextFieldPad.TP_STRING,false),"txtDescTpConv");
	txtCodTpConv.setTabelaExterna(lcTipoConv);
	txtDescTipoConv.setListaCampos(lcTipoConv);
	lcTipoConv.montaSql(false, "TIPOCONV", "AT");
	lcTipoConv.setQueryCommit(false);
	lcTipoConv.setReadOnly(true);

	
	lcCliente.add(new GuardaCampo( txtCodCli, 7, 100, 80, 20, "CodCli", "Código", true, false, null, JTextFieldPad.TP_INTEGER,false),"txtCodCli");
	lcCliente.add(new GuardaCampo( txtRazCli, 400, 80, 150, 20, "RazCli", "Nome", false, false, null, JTextFieldPad.TP_STRING,false),"txtRazCli");
	txtCodCli.setTabelaExterna(lcCliente);
	txtRazCli.setListaCampos(lcCliente);
	lcCliente.montaSql(false, "CLIENTE", "VD");
	lcCliente.setQueryCommit(false);
	lcCliente.setReadOnly(true);
	
	
	lcEnc.add(new GuardaCampo( txtCodEnc, 7, 100, 80, 20, "CodEnc", "Código", true, false, null, JTextFieldPad.TP_INTEGER,false));
	lcEnc.add(new GuardaCampo( txtNomeEnc, 400, 80, 150, 20, "NomeEnc", "Nome", false, false, null, JTextFieldPad.TP_STRING,false));
	txtCodEnc.setTabelaExterna(lcEnc);
	txtNomeEnc.setListaCampos(lcEnc);
	txtCodEnc.setNomeCampo("CodEnc");
	lcEnc.montaSql(false, "ENCAMINHADOR", "AT");
	lcEnc.setQueryCommit(false);
	lcEnc.setReadOnly(true);
	
		
	lcPlanoPag.add(new GuardaCampo( txtCodPlanoPag, 7, 100, 80, 20, "CodPlanoPag", "Código", true, false, null, JTextFieldPad.TP_INTEGER,false),"txtCodPlanoPagx");
	lcPlanoPag.add(new GuardaCampo( txtDescPlanoPag, 90, 100, 207, 20, "DescPlanoPag", "Descrição", false, false, null, JTextFieldPad.TP_STRING,false),"txtDescPlanoPagx");
	txtCodPlanoPag.setTabelaExterna(lcPlanoPag);
	txtDescPlanoPag.setListaCampos(lcPlanoPag);
	lcPlanoPag.montaSql(false, "PLANOPAG", "FN");
	lcPlanoPag.setQueryCommit(false);
	lcPlanoPag.setReadOnly(true);

	lcVend.add(new GuardaCampo( txtCodVend, 7, 100, 80, 20, "CodVend", "Código", true, false, null, JTextFieldPad.TP_INTEGER,false));
	lcVend.add(new GuardaCampo( txtNomeVend, 90, 100, 207, 20, "NomeVend", "Nome", false, false, null, JTextFieldPad.TP_STRING,false));
	txtCodVend.setTabelaExterna(lcVend);
	txtNomeVend.setListaCampos(lcVend);
	lcVend.montaSql(false, "VENDEDOR", "VD");
	lcVend.setQueryCommit(false);
	lcVend.setReadOnly(true);
    
	//FK Conveniado
	txtCodConv.setTipo(JTextFieldPad.TP_INTEGER,8,0);
	txtDescConv.setTipo(JTextFieldPad.TP_STRING,50,0);    
	lcConv.add(new GuardaCampo( txtCodConv, 7, 100, 80, 20, "CodConv", "Código", true, false, null, JTextFieldPad.TP_INTEGER,false),"txtCodConv");
	lcConv.add(new GuardaCampo( txtDescConv, 90, 100, 207, 20, "NomeConv", "Nome", false, false, null, JTextFieldPad.TP_STRING,false),"txtDescClix");
	lcConv.add(new GuardaCampo( txtCodTpConv, 7,140,100,20,"CodTpConv","Tipo de Conveniado",false,false,null, JTextFieldPad.TP_INTEGER,false),"txtCodTpConv");
	lcConv.add(new GuardaCampo( txtCodEnc, 7, 100, 80, 20, "CodEnc", "Encaminhador", false, true, null, JTextFieldPad.TP_INTEGER,false),"txtCodEnc");
	lcConv.montaSql(false, "CONVENIADO","AT");    
	lcConv.setQueryCommit(false);
	lcConv.setReadOnly(true);
	txtCodConv.setTabelaExterna(lcConv);
	//FK Produto
	txtCodProd.setTipo(JTextFieldPad.TP_INTEGER,8,0);
	txtRefProd.setTipo(JTextFieldPad.TP_STRING,13,0);    
	txtDescProd.setTipo(JTextFieldPad.TP_STRING,50,0);    
	lcProd.add(new GuardaCampo( txtCodProd, 7, 100, 80, 20, "CodProd", "Código", true, false, txtDescProd, JTextFieldPad.TP_INTEGER,false),"txtCodProdx");
	lcProd.add(new GuardaCampo( txtDescProd, 90, 100, 207, 20, "DescProd", "Descrição", false, false, null, JTextFieldPad.TP_STRING,false),"txtDescProdx");
	lcProd.add(new GuardaCampo( txtRefProd, 90, 100, 207, 20, "RefProd", "Referência", false, false, null, JTextFieldPad.TP_STRING,false),"txtDescProdx");
	lcProd.add(new GuardaCampo( txtCodBarras, 7, 100, 80, 20, "CodBarProd", "Código de Barras", false, false, null, JTextFieldPad.TP_STRING,false),"txtCodProdx");
	lcProd.setWhereAdic("ATIVOPROD='S'");
	lcProd.montaSql(false, "PRODUTO", "EQ");
	lcProd.setQueryCommit(false);
	lcProd.setReadOnly(true);
	txtCodProd.setTabelaExterna(lcProd);
    
	//FK do produto (*Somente em caso de referências este listaCampos 
	  //Trabalha como gatilho para o listaCampos de produtos, assim
	  //carregando o código do produto que será armazenado no Banco)
	lcProd2.add(new GuardaCampo( txtRefProd, 90, 100, 207, 20, "RefProd", "Referência", true, false, txtDescProd, JTextFieldPad.TP_STRING,false),"txtDescProdx");
	lcProd2.add(new GuardaCampo( txtDescProd, 90, 100, 207, 20, "DescProd", "Descrição", false, false, null, JTextFieldPad.TP_STRING,false),"txtDescProdx");
	lcProd2.add(new GuardaCampo( txtCodProd, 7, 100, 80, 20, "CodProd", "Código", false, false, null, JTextFieldPad.TP_INTEGER,false),"txtCodProdx");
	txtRefProd.setNomeCampo("RefProd");
	txtRefProd.setListaCampos(lcDet);
	lcProd2.setWhereAdic("ATIVOPROD='S'");
	lcProd2.montaSql(false, "PRODUTO", "EQ");
	lcProd2.setQueryCommit(false);
	lcProd2.setReadOnly(true);
	txtRefProd.setTabelaExterna(lcProd2);
    
    
	//ListaCampos de Totais (É acionada pelo listaCampos de Orcamento)
    
	txtCodOrc.setTipo(JTextFieldPad.TP_INTEGER,8,0);
	txtVlrDescOrc.setTipo(JTextFieldPad.TP_DECIMAL,15,2);
	txtVlrAdicOrc.setTipo(JTextFieldPad.TP_DECIMAL,15,2);
	txtVlrLiqOrc.setTipo(JTextFieldPad.TP_DECIMAL,15,2);
	txtVlrProdOrc.setTipo(JTextFieldPad.TP_DECIMAL,15,2);
	lcOrc2.add(new GuardaCampo( txtCodOrc, 7, 100, 80, 20, "CodOrc", "Código", true, false, null, JTextFieldPad.TP_INTEGER,false),"txtCodOrc");
	lcOrc2.add(new GuardaCampo( txtVlrDescOrc, 7, 100, 80, 20, "VlrDescOrc", "Desconto", false, false, null, JTextFieldPad.TP_DECIMAL,false),"txtCodVendx");
	lcOrc2.add(new GuardaCampo( txtVlrAdicOrc, 7, 100, 80, 20, "VlrAdicOrc", "Adicional", false, false, null, JTextFieldPad.TP_DECIMAL,false),"txtCodVendx");
	lcOrc2.add(new GuardaCampo( txtVlrLiqOrc, 7, 100, 80, 20, "VlrLiqOrc", "Total", false, false, null, JTextFieldPad.TP_DECIMAL,false),"txtCodVendx");
	lcOrc2.add(new GuardaCampo( txtVlrProdOrc, 7, 100, 80, 20, "VlrProdOrc", "Parcial", false, false, null, JTextFieldPad.TP_DECIMAL,false),"txtCodVendx");
	lcOrc2.montaSql(false, "ORCAMENTO", "VD");
	lcOrc2.setQueryCommit(false);
	lcOrc2.setReadOnly(true);
    
	//Coloca os comentário nos botões
    
	btFechaOrc.setToolTipText("Completar o Orçamento (F4)");
	btObs.setToolTipText("Observações (Ctrl + O)");
	btOrc.setToolTipText("Imprime orçamento padrão");
	btOrcTst.setToolTipText("Imprime orçamento assinado");
	btOrcTst2.setToolTipText("Imprime contrato de locação");
    
	//Desativa as os TextFields para que os usuários não fussem 
    
	txtVlrDescOrc.setAtivo(false);
	txtVlrAdicOrc.setAtivo(false);
	txtVlrLiqOrc.setAtivo(false);
    
	//Adiciona os componentes na tela e no ListaCompos da orcamento
    pinCab = new Painel(740,180);
	setListaCampos(lcCampos);
    setAltCab(170);
	setPainel( pinCab, pnCliCab);
	adicCampo(txtCodOrc, 7, 20, 90, 20,"CodOrc","N. Orcamento",JTextFieldPad.TP_INTEGER,8,0,true,false,null,true);
	adicCampo(txtCodConv, 100, 20, 87, 20,"CodConv","Código",JTextFieldPad.TP_INTEGER,8,0,false,true,txtDescConv,true);
	adicDescFK(txtDescConv, 190, 20, 247, 20, "NomeConv", "e nome do conveniado", JTextFieldPad.TP_STRING, 50, 0);
	adicCampo(txtDtOrc, 440, 20, 107, 20,"DtOrc","Data",JTextFieldPad.TP_DATE,10,0,false,false,null,true);
	adicCampo(txtDtVencOrc, 550, 20, 110, 20,"DtVencOrc","Data de Validade",JTextFieldPad.TP_DATE,10,0,false,false,null,true);
	adicCampo(txtCodVend, 7, 60, 90, 20,"CodVend","Código",JTextFieldPad.TP_INTEGER,8,0,false,true,txtNomeVend,true);
	adicDescFK(txtNomeVend,100, 60, 250,20,"NomeVend","e nome do vendedor",JTextFieldPad.TP_STRING, 50, 0 );
	adicDescFK(txtDescTipoConv,456, 60, 205,20,"DescTpConv","Tipo de conveniado",JTextFieldPad.TP_STRING, 50, 0 );
	adicDescFK(txtRazCli,7,100,345,20,"RazCli","Cliente ",JTextFieldPad.TP_STRING,50,0);
    adicDescFK(txtNomeEnc,355,100,305,20,"NomeEnc","Org.Encaminhador ",JTextFieldPad.TP_STRING,50,0);
    	
	
	if (!oPrefs[3].equals(""))
	   adicCampo(txtTxt01, 353, 60, 100, 20,"Txt01",oPrefs[3].toString().trim(),JTextFieldPad.TP_STRING,20,0,false,false,null,false);

	adicCampoInvisivel(txtCodPlanoPag,"CodPlanoPag","Plano Pag.",JTextFieldPad.TP_INTEGER,8,0,false,true,txtDescPlanoPag,false);
	adicCampoInvisivel(txtCodAtend,"CodAtend","Plano Atendente.",JTextFieldPad.TP_INTEGER,8,0,false,true,txtDescAtend,false);
	adicCampoInvisivel(txtVlrEdDescOrc,"VlrDescOrc","Desc.",JTextFieldPad.TP_INTEGER,15,2,false,false,null,false);
	adicCampoInvisivel(txtVlrEdAdicOrc,"VlrAdicOrc","Adic.",JTextFieldPad.TP_INTEGER,15,2,false,false,null,false);
	adicCampoInvisivel(txtStatusOrc,"StatusOrc","Status",JTextFieldPad.TP_STRING,2,0,false,false,null,false);
	adicCampoInvisivel(txtCodTpConv,"CodTpConv","Cód. Tipo Conv.",JTextFieldPad.TP_INTEGER,8,0,false,true,txtDescTipoConv,false);	
	adicCampoInvisivel(txtCodCli,"CodCli","Cod.Cliente",JTextFieldPad.TP_INTEGER,8,0,false,true,txtRazCli,false);
	setListaCampos( true, "ORCAMENTO", "VD");
    
	txtVlrLiqItOrc.setAtivo(false);
    
	//Adiciona os Listeners
	btFechaOrc.addActionListener(this);
	btObs.addActionListener(this);
	btOrc.addActionListener(this);
	btOrcTst.addActionListener(this);
	btOrcTst2.addActionListener(this);
    
	txtRefProd.addKeyListener(this);

	txtPercDescItOrc.addFocusListener(this);
	txtVlrDescItOrc.addFocusListener(this);
	txtQtdItOrc.addFocusListener(this);
	txtPrecoItOrc.addFocusListener(this);
	lcCampos.addPostListener(this);
	lcCampos.addCarregaListener(this);
	lcProd2.addCarregaListener(this);
	lcDet.addPostListener(this);
	lcDet.addCarregaListener(this);
	lcCampos.addInsertListener(this);
	lcDet.addDeleteListener(this);

	btImp.addActionListener(this);
	btPrevimp.addActionListener(this);
  	
  }
  //Função criada para montar a tela conforme a preferência do usuário:
  //com ou sem Referência sendo PK;
  private void montaDetalhe() {
    setAltDet(60);
    pinDet = new Painel(740,100);
    setPainel( pinDet, pnDet);
    setListaCampos(lcDet);
    setNavegador(navRod);
    adicCampo(txtCodItOrc, 7, 20, 30, 20,"CodItOrc","Item",JTextFieldPad.TP_INTEGER,8,0,true,false,null,true);
    if (oPrefs[0]==Boolean.TRUE) {
      adicCampoInvisivel(txtCodProd,"CodProd","Código",JTextFieldPad.TP_INTEGER,8,0,false,true,txtDescProd,false);
      adicCampoInvisivel(txtRefProd,"RefProd","Referência",JTextFieldPad.TP_STRING,13,0,false,true,null,false);
      adic(new JLabel("Referência"), 40, 0, 67, 20);
      adic(txtRefProd, 40, 20, 67, 20);
    }
    else {
      adicCampo(txtCodProd, 40, 20, 67, 20,"CodProd","Código",JTextFieldPad.TP_INTEGER,8,0,false,true,txtDescProd,true);
    }
    adicDescFK(txtDescProd, 110, 20, 227, 20, "DescProd", "e descrição do produto", JTextFieldPad.TP_STRING, 50, 0);
    adicCampo(txtQtdItOrc, 340, 20, 47, 20,"QtdItOrc","Qtd.",JTextFieldPad.TP_DECIMAL,15,2,false,false,null,true);
    adicCampo(txtPrecoItOrc, 390, 20, 67, 20,"PrecoItOrc","Preço",JTextFieldPad.TP_DECIMAL,15,2,false,false,null,true);
    adicCampo(txtPercDescItOrc, 460, 20, 57, 20,"PercDescItOrc","% Desc.",JTextFieldPad.TP_DECIMAL,6,2,false,false,null,false);
    adicCampo(txtVlrDescItOrc, 520, 20, 57, 20,"VlrDescItOrc","V. Desc.",JTextFieldPad.TP_DECIMAL,15,2,false,false,null,false);
    adicCampoInvisivel(txtVlrProdItOrc,"VlrProdItOrc","V. Bruto",JTextFieldPad.TP_DECIMAL,15,2,false,false,null,false);
	adicCampoInvisivel(txtObsItOrc,"ObsItOrc","Observação",JTextFieldPad.TP_STRING,50,0,false,false,null,false);
    adicCampo(txtVlrLiqItOrc, 580, 20, 80, 20,"VlrLiqItOrc","Valor Item",JTextFieldPad.TP_DECIMAL,15,2,false,false,null,false);
    pinTot.adic(new JLabel("Tot. Desc."),7,0,90,20);
    pinTot.adic(txtVlrDescOrc,7,20,100,20);
	pinTot.adic(new JLabel("Tot. Adic."),7,40,90,20);
	pinTot.adic(txtVlrAdicOrc,7,60,100,20);
    pinTot.adic(new JLabel("Total Geral"),7,80,90,20);
    pinTot.adic(txtVlrLiqOrc,7,100,100,20);

    setListaCampos( true, "ITORCAMENTO", "VD");
    montaTab();
    
    tab.setAutoRol(true);

    tab.setTamColuna(30,0);
    tab.setTamColuna(70,1);
    tab.setTamColuna(230,2);
    tab.setTamColuna(60,3);
    tab.setTamColuna(70,4);
    tab.setTamColuna(60,5);
    tab.setTamColuna(70,6);
    tab.setTamColuna(90,7);
  }
  private void calcTot() { 
	BigDecimal bVlrProd = txtVlrProdItOrc.getVlrBigDecimal().subtract(
	txtVlrDescItOrc.getVlrBigDecimal()).divide(
	  new BigDecimal("1"),3,BigDecimal.ROUND_HALF_UP);
	  txtVlrLiqItOrc.setVlrBigDecimal(bVlrProd);
  }
  private void calcVlrProd() {
    BigDecimal bPreco = txtPrecoItOrc.getVlrBigDecimal();
    BigDecimal bQtd = txtQtdItOrc.getVlrBigDecimal();
    txtVlrProdItOrc.setVlrBigDecimal(bPreco.multiply(bQtd).divide(new BigDecimal("1"),3,BigDecimal.ROUND_HALF_UP));
  }
  private void mostraTelaDescont() {
          String sObsDesc = "";
          int iFim = 0;
          if ((lcDet.getStatus() == ListaCampos.LCS_INSERT) || 
              (lcDet.getStatus() == ListaCampos.LCS_EDIT)) {
                      txtVlrDescItOrc.setAtivo(true);
                      txtPercDescItOrc.setAtivo(false);
                      txtPercDescItOrc.setVlrString("");
                      txtVlrDescItOrc.setVlrString("");
                      calcVlrProd();
                      calcTot();
                      DLDescontItOrc dl = new DLDescontItOrc(this,txtPrecoItOrc.getVlrDouble().doubleValue(),parseDescs());
                      dl.setVisible(true);
                      txtVlrDescItOrc.setAtivo(true);
                      txtPercDescItOrc.setAtivo(false);
                      txtPercDescItOrc.setVlrString("");
                      if(dl.OK) { 
                              txtVlrDescItOrc.setVlrBigDecimal(new BigDecimal(dl.getValor()*txtQtdItOrc.getVlrDouble().doubleValue()));
                              sObsDesc = txtObsItOrc.getText();
                              iFim = sObsDesc.indexOf("\n");
                              if (iFim >= 0) 
                                      sObsDesc = dl.getObs()+" "+sObsDesc.substring(iFim);
                              else
                                      sObsDesc = dl.getObs()+" \n";
                              txtObsItOrc.setVlrString(sObsDesc);
                      }
                      dl.dispose();
                      calcVlrProd();
                      calcTot();
                      txtVlrDescItOrc.requestFocus(true);
              }
  }
                      
  private String[] parseDescs() {
          String[] sRet = new String[5];
          String sObs = txtObsItOrc.getText();
          int iFim = sObs.indexOf('\n');
          int iPos = 0;
  //        System.out.println("1 :"+sObs);
          if (iFim > 0) {
                  sObs = sObs.substring(0,iFim);
    //              System.out.println("2 :"+sObs);
                  if (sObs.indexOf("Desc.: ") == 0) {
                          sObs = sObs.substring(7);
      //                    System.out.println("3 :"+sObs);
                          iPos = sObs.indexOf('+');
                          for (int i=0;(iPos > 0) && (i < 5);i++) {
                                  sRet[i] = sObs.substring(0,iPos-1);
                                  if (iPos != iFim)
                                          sObs = sObs.substring(iPos+1);
        //                          System.out.println("4 :"+sObs);
                                  if (iPos == iFim)
                                          break;
                                  if ((iPos = sObs.indexOf('+')) == -1)
                                        iPos = iFim = sObs.length();
                          }
                  }
          }
          return sRet;
  }
                            
  private void testaCodOrc() { //Traz o verdadeiro número do codorcamento através do generator do banco
    PreparedStatement ps = null;
    ResultSet rs = null;
    try {
      ps = con.prepareStatement("SELECT * FROM SPGERANUM(?,?,?)");
      ps.setInt(1,Aplicativo.iCodEmp);
      ps.setInt(2,ListaCampos.getMasterFilial("VDORCAMENTO"));
      ps.setString(3,"OC");
      rs = ps.executeQuery();
      rs.next();
      txtCodOrc.setVlrString(rs.getString(1));
//      rs.close();
//      ps.close();
      if (!con.getAutoCommit())
      	con.commit();
    }
    catch (SQLException err) {
		Funcoes.mensagemErro(this,"Erro ao confirmar código da orcamento!\n"+err.getMessage());
    }
  }
  public void focusGained(FocusEvent fevt) { }
  public void focusLost(FocusEvent fevt) {
    if (fevt.getSource() == txtPercDescItOrc) {
      if (txtPercDescItOrc.getText().trim().length() < 1) {
        txtVlrDescItOrc.setAtivo(true);
      }
      else {
        txtVlrDescItOrc.setVlrBigDecimal(
          txtVlrProdItOrc.getVlrBigDecimal().multiply(
          txtPercDescItOrc.getVlrBigDecimal()).divide(
          new BigDecimal("100"),3,BigDecimal.ROUND_HALF_UP)
        );
        calcVlrProd();
        calcTot();
        txtVlrDescItOrc.setAtivo(false);
      }
    }
    else if (fevt.getSource() == txtVlrDescItOrc) {
      if (txtVlrDescItOrc.getText().trim().length() < 1) {
        txtPercDescItOrc.setAtivo(true);
      }
      else if (txtVlrDescItOrc.getAtivo()) {
        txtPercDescItOrc.setAtivo(false);
      }
    }
    else if ((fevt.getSource() == txtQtdItOrc) |
             (fevt.getSource() == txtPrecoItOrc)) {
       calcVlrProd();
	   calcTot();
    }
  }
  public void beforeCarrega(CarregaEvent cevt) {
    if (cevt.getListaCampos() == lcProd2)
      lcProd.edit();
  }
  public void afterPost(PostEvent pevt) {
    lcOrc2.carregaDados(); //Carrega os Totais
  }
  public void afterCarrega(CarregaEvent cevt) { 
    if (cevt.getListaCampos() == lcDet) {
      lcOrc2.carregaDados();//Carrega os Totais
    }
	else if (cevt.getListaCampos() == lcProd2 && lcDet.getStatus() == ListaCampos.LCS_INSERT) {
	  buscaPreco(); 
	}
    else if (cevt.getListaCampos() == lcCampos) {
      String s = txtCodOrc.getVlrString();
      lcOrc2.carregaDados();//Carrega os Totais
      txtCodOrc.setVlrString(s);
    }
  }
  public void keyPressed(KeyEvent kevt) {
    if (kevt.getKeyCode() == KeyEvent.VK_CONTROL) {
      bCtrl = true;
    }
    else if (kevt.getKeyCode() == KeyEvent.VK_O) {
      if (bCtrl) {
        btObs.doClick();
      }
    }
    else if (kevt.getKeyCode() == KeyEvent.VK_F4) {
      btFechaOrc.doClick();
    }
    else if (kevt.getKeyCode() == KeyEvent.VK_F6) {
      mostraTelaDescont();
    }
    if (kevt.getSource() == txtRefProd)
      lcDet.edit();
    super.keyPressed(kevt);
  }
  public void actionPerformed(ActionEvent evt) {
    Object[] bValores = null;
    if (evt.getSource() == btFechaOrc) {
      DLCompOrc dl = 
        new DLCompOrc(
          this,
          (txtVlrDescItOrc.getVlrBigDecimal().intValue() > 0),
          txtVlrProdOrc.getVlrBigDecimal(),
		  txtVlrEdDescOrc.getVlrBigDecimal(),
		  txtVlrEdAdicOrc.getVlrBigDecimal(),
          txtCodPlanoPag, txtDescPlanoPag);
        
/*
  Verifica se o orçamento foi gerado por um atendimento
  e adiciona a PK para ser preenchida na tela de complemento.
*/
      if (txtStatusOrc.getVlrString().equals("OA") || txtCodAtend.getVlrInteger().intValue() > 0)
        dl.setFKAtend(txtCodAtend,txtDescAtend);
        
      dl.setVisible(true);
      if (dl.OK) {
        bValores = dl.getValores();
        dl.dispose();
      }
      else {
        dl.dispose();
      }
      if (bValores != null) {
      	lcCampos.edit();
      	txtVlrEdDescOrc.setVlrBigDecimal((BigDecimal)bValores[0]);
		txtVlrEdAdicOrc.setVlrBigDecimal((BigDecimal)bValores[1]);
        
// Ajusta o status para OC - orçamento completo.        
		txtStatusOrc.setVlrString("OC");
		lcCampos.post();
		lcCampos.carregaDados();
		
		if (bValores[2].equals("S")) {
          imprimir(true);
        }
      }
    }
    else if (evt.getSource() == btPrevimp) 
      imprimir(true);
    else if (evt.getSource() == btImp) 
      imprimir(false);
	else if (evt.getSource() == btOrc) { 
	  ImprimeOrc imp = new ImprimeOrc(txtCodOrc.getVlrInteger().intValue() );
      imp.setConexao(con);
      dl = new FPrinterJob(imp,this);
      dl.setVisible(true);
	}
	else if (evt.getSource() == btOrcTst) { 
		LeiauteGR leiOrc = null;
		try {
		  leiOrc = (LeiauteGR)Class.forName("org.freedom.layout."+"LaudoAprSusFisio").newInstance();      
		  leiOrc.setConexao(con);
		  vParamOrc.clear();
		  vParamOrc.addElement(txtCodOrc.getText());
		  vParamOrc.addElement(txtCodConv.getText());
		  leiOrc.setParam(vParamOrc);
		  
		  dl = new FPrinterJob(leiOrc,this);
		} 
		catch (Exception err) {
		  Funcoes.mensagemInforma(this,"Não foi possível carregar o leiaute de Orçamento Fisio.!\n"+err.getMessage());
		  err.printStackTrace();
		}
	}
	else if (evt.getSource() == btOrcTst2) { 
		LeiauteGR leiOrc = null;
		try {
		  leiOrc = (LeiauteGR)Class.forName("org.freedom.layout."+"ContratoAluguelApr").newInstance();      
		  leiOrc.setConexao(con);
		  vParamOrc.clear();
		  vParamOrc.addElement(txtCodOrc.getText());
		  vParamOrc.addElement(txtCodConv.getText());
		  leiOrc.setParam(vParamOrc);
		  
		  dl = new FPrinterJob(leiOrc,this);
		} 
		catch (Exception err) {
		  Funcoes.mensagemInforma(this,"Não foi possível carregar o leiaute de Contrato de locação!\n"+err.getMessage());
		  err.printStackTrace();
		}
	}	
	

    else if (evt.getSource() == btObs) {
      FObservacao obs = null;
      try {
        PreparedStatement ps = con.prepareStatement("SELECT OBSORC FROM VDORCAMENTO WHERE CODORC=?");
        ps.setInt(1,txtCodOrc.getVlrInteger().intValue());
        ResultSet rs = ps.executeQuery();
        if (rs.next())
          obs = new FObservacao((rs.getString("ObsOrc") != null ? rs.getString("ObsOrc") : ""));
        else 
          obs = new FObservacao("");
//        rs.close();
//        ps.close();
        if (!con.getAutoCommit())
        	con.commit();
      }
      catch (SQLException err) {
		Funcoes.mensagemErro(this,"Erro ao carregar a observação!\n"+err.getMessage());
      }
      if (obs != null) {
      	obs.setSize(400,200);
        obs.show();
        if (obs.OK) {
          try {
            PreparedStatement ps = con.prepareStatement("UPDATE VDORCAMENTO SET OBSORC=? WHERE CODORC=?");
            ps.setString(1,obs.getTexto());
            ps.setInt(2,txtCodOrc.getVlrInteger().intValue());
            ps.executeUpdate();
            if (!con.getAutoCommit())
            	con.commit();
          }
          catch(SQLException err) {
			Funcoes.mensagemErro(this,"Erro ao inserir observação no orçamento!\n"+err.getMessage());
          }
        }
        obs.dispose();
      }
    }
    super.actionPerformed(evt);
  }
  
  private void imprimir(boolean bVisualizar) {
      String sSql = "SELECT CLASSTPCONV FROM ATTIPOCONV WHERE CODEMP=? AND CODFILIAL=? AND CODTPCONV=?";
	  String sClassOrc = "";
      LeiauteGR leiOrc = null;
      try {
      	PreparedStatement ps = con.prepareStatement(sSql);
		ps.setInt(1,Aplicativo.iCodEmp);
		ps.setInt(2,ListaCampos.getMasterFilial("ATTIPOCONV"));
	    ps.setInt(3,txtCodTpConv.getVlrInteger().intValue());
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
		  if (rs.getString("CLASSTPCONV")!=null) {
		    sClassOrc = rs.getString("CLASSTPCONV").trim();
		  }
        }
        else {
			sSql = "SELECT CLASSORC FROM SGPREFERE1 WHERE CODEMP=? AND CODFILIAL=?";
			sClassOrc = "";
			PreparedStatement ps2 = null;
			ResultSet rs2 = null;
			leiOrc = null;
			try {
			  ps2 = con.prepareStatement(sSql);
			  ps2.setInt(1,Aplicativo.iCodEmp);
			  ps2.setInt(2,ListaCampos.getMasterFilial("SGPREFERE1"));
			  rs2 = ps.executeQuery();

			  if (rs2.next()) {
				if (rs2.getString("CLASSORC")!=null){
				  sClassOrc = rs2.getString("CLASSORC").trim();              
				  rs2.close();
				  ps2.close();
				}
			  }
			}		 
			catch (SQLException err) {
			  Funcoes.mensagemErro(this,"Erro ao carregar a tabela PREFERE1!\n"+err.getMessage());
			  err.printStackTrace();
			}        	     	        	
        }
        rs.close();
        ps.close();
      }  
  	  catch (SQLException err) {
	    Funcoes.mensagemErro(this,"Erro ao carregar a tabela ATTPCONV!\n"+err.getMessage());
	    err.printStackTrace();
	  }

	  if (sClassOrc.trim().equals("")) {          
	    ImprimeOrc imp = new ImprimeOrc(txtCodOrc.getVlrInteger().intValue() );
	    imp.setConexao(con);
		if (bVisualizar) { 
		  dl = new FPrinterJob(imp,this);
		  dl.setVisible(true);
		}
		else
		  imp.imprimir(true);
      }
  	  else {
	    try {
  	      leiOrc = (LeiauteGR)Class.forName("org.freedom.layout."+sClassOrc).newInstance();      
          leiOrc.setConexao(con);
          vParamOrc.clear();
          vParamOrc.addElement(txtCodOrc.getText());
          vParamOrc.addElement(txtCodConv.getText());
          leiOrc.setParam(vParamOrc);
		  if (bVisualizar) { 
		    dl = new FPrinterJob(leiOrc,this);
		    dl.setVisible(true);
		  }
		  else
		    leiOrc.imprimir(true);
	    }
	    catch (Exception err) {
		  Funcoes.mensagemInforma(this,"Não foi possível carregar o leiaute de Orçamento!\n"+err.getMessage());
		  err.printStackTrace();
	    }
  	  }

//	  imp.setConexao(con);
  }
  
  private void buscaPreco() {
	String sSQL = "SELECT PRECO FROM ATBUSCAPRECOSP(?,?,?,?,?,?,?,?,?)";
	PreparedStatement ps = null;
	ResultSet rs = null;
	try {
	  ps = con.prepareStatement(sSQL);
	  ps.setInt(1,txtCodProd.getVlrInteger().intValue());
	  ps.setInt(2,txtCodConv.getVlrInteger().intValue());
	  ps.setInt(3,Aplicativo.iCodEmp);
	  ps.setInt(4,lcConv.getCodFilial());
	  ps.setInt(5,txtCodPlanoPag.getVlrInteger().intValue());
	  ps.setInt(6,Aplicativo.iCodEmp);
	  ps.setInt(7,lcPlanoPag.getCodFilial());
	  ps.setInt(8,Aplicativo.iCodEmp);
	  ps.setInt(9,Aplicativo.iCodFilial);
	  rs = ps.executeQuery();
	  rs.next();
	  txtPrecoItOrc.setVlrBigDecimal(rs.getString(1) != null ? (new BigDecimal(rs.getString(1))) : (new BigDecimal("0")));
	}
	catch (SQLException err) {
		Funcoes.mensagemErro(this,"Erro ao carregar o preço!\n"+err.getMessage());
	}
  }
  private Object[] prefs() {
    Object[] bRetorno = new Object[4];
    String sSQL = "SELECT USAREFPROD,USALIQREL,TIPOPRECOCUSTO,TITORCTXT01 FROM SGPREFERE1 WHERE CODEMP=? AND CODFILIAL=?";
    PreparedStatement ps = null;
    ResultSet rs = null;
    try {
      ps = con.prepareStatement(sSQL);
	  ps.setInt(1,Aplicativo.iCodEmp);
	  ps.setInt(2,ListaCampos.getMasterFilial("SGPREFERE1"));
      rs = ps.executeQuery();
      if (rs.next()) {
        if (rs.getString("UsaRefProd").trim().equals("S"))
          bRetorno[0] = new Boolean(true);
        else 
		  bRetorno[0] = new Boolean(false);
        if (rs.getString("UsaLiqRel")==null) {
			bRetorno[1] = new Boolean(false);
			Funcoes.mensagemInforma(this,"Preencha opção de desconto em preferências!");
        }
        else {
           if (rs.getString("UsaLiqRel").trim().equals("S"))
              bRetorno[1] = new Boolean(true);
        }
	    if (rs.getString("TipoPrecoCusto").equals("M"))
	       bRetorno[2] = new Boolean(true);
	    else
		   bRetorno[2] = new Boolean(false);
	    bRetorno[3] = rs.getString("TitOrcTxt01");
	    if (bRetorno[3]==null)
	       bRetorno[3] = "";
      }
//      rs.close();
//      ps.close();
      if (!con.getAutoCommit())
      	 con.commit();
    }
    catch (SQLException err) {
		Funcoes.mensagemErro(this,"Erro ao carregar a tabela SGPREFERE1!\n"+err.getMessage());
    }
    return bRetorno;
  }
  public void keyTyped(KeyEvent kevt) { 
    super.keyTyped(kevt);
  }
  public void keyReleased(KeyEvent kevt) { 
    if (kevt.getKeyCode() == KeyEvent.VK_CONTROL)
      bCtrl = false;
    super.keyReleased(kevt);
  }
  public void beforePost(PostEvent evt) { 
    if ((evt.getListaCampos() == lcCampos) && (lcCampos.getStatus() == ListaCampos.LCS_INSERT)) {
      testaCodOrc();
      txtStatusOrc.setVlrString("*");
    }
  }
  public void beforeDelete(DeleteEvent devt) { }
  public void beforeInsert(InsertEvent ievt) { }
  public void afterInsert(InsertEvent ievt) {
  	if (ievt.getListaCampos() == lcCampos) { 
      txtDtOrc.setVlrDate(new Date());
      txtDtVencOrc.setVlrDate(new Date());
	}
  }
  public void afterDelete(DeleteEvent devt) {
	if (devt.getListaCampos() == lcDet)
	  lcOrc2.carregaDados();
  }
  public void exec(int iCodOrc) {
  	txtCodOrc.setVlrString(iCodOrc+"");
  	lcCampos.carregaDados();
  }
  public void execShow(Connection cn) {
    con = cn;
    montaOrcamento();
    montaDetalhe();
    lcProd.setConexao(cn);
    lcProd2.setConexao(cn);
    lcOrc2.setConexao(cn);
    lcConv.setConexao(cn);
	lcPlanoPag.setConexao(cn);
	lcAtend.setConexao(cn);
	lcVend.setConexao(cn);
	lcTipoConv.setConexao(cn);
	lcCliente.setConexao(cn);
	lcEnc.setConexao(cn);
    super.execShow(cn);
  }
}