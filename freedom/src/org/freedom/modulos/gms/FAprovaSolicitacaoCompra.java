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

package org.freedom.modulos.gms;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

import org.freedom.acao.CarregaEvent;
import org.freedom.acao.CarregaListener;
import org.freedom.acao.InsertEvent;
import org.freedom.acao.InsertListener;
import org.freedom.acao.PostEvent;
import org.freedom.acao.PostListener;
import org.freedom.bmps.Icone;
import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.ImprimeOS;
import org.freedom.componentes.JRadioGroup;
import org.freedom.componentes.JTextAreaPad;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.Painel;
import org.freedom.funcoes.Funcoes;
import org.freedom.modulos.std.DLRPedido;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FDetalhe;

public class FAprovaSolicitacaoCompra extends FDetalhe 
implements PostListener, CarregaListener, FocusListener, ActionListener, InsertListener {
  private int casasDec = Aplicativo.casasDec;
  private Painel pinCab = new Painel();
  private Painel pinDet = new Painel();
  private ImageIcon imgOk = Icone.novo("btOk.gif");
  private ImageIcon imgCancel = Icone.novo("btExcluir.gif");
  private JButton btAprovaCompra = new JButton("Solicitação Pendente", null);
  private JButton btAprovaItem = new JButton("Item Pendente", null);
  private JButton btCancelaItem = new JButton("Cancelar Item", imgCancel);
  private JTextFieldPad txtCodSolicitacao = new JTextFieldPad();
  private JTextFieldPad txtStatusSolicitacao = new JTextFieldPad();
  private JTextFieldPad txtSituaçãoItAprov = new JTextFieldPad();
  private JTextFieldPad txtSituaçãoItComp = new JTextFieldPad();
  private JTextFieldPad txtSituaçãoIt = new JTextFieldPad();
  private JTextFieldPad txtDtEmitSolicitacao = new JTextFieldPad();
  private JTextFieldPad txtCodItSolicitacao = new JTextFieldPad();
  private JTextFieldPad txtQtdItSolicitado = new JTextFieldPad();
  private JTextFieldPad txtQtdItAprovado = new JTextFieldPad();
  private JTextFieldPad txtCodUsu = new JTextFieldPad();
  private JTextFieldFK txtNomeUsu = new JTextFieldFK();
  private JTextFieldPad txtCodProd = new JTextFieldPad();
  private JTextFieldPad txtRefProd = new JTextFieldPad();
  private JTextFieldPad txtCodCC = new JTextFieldPad(JTextFieldPad.TP_STRING,19,0);
  private JTextFieldFK txtDescCC = new JTextFieldFK();
  private JTextFieldPad txtAnoCC = new JTextFieldPad(JTextFieldPad.TP_STRING,19,0);
  private JTextFieldPad txtOrigSolicitacao = new JTextFieldPad();
  private JTextFieldFK txtDescProd = new JTextFieldFK();
  private JTextFieldPad txtCodAlmoxarife = new JTextFieldPad();
  private JTextFieldFK txtDescAlmoxarife = new JTextFieldFK();
  private JTextAreaPad txaMotivoSolicitacao = new JTextAreaPad();
  private JScrollPane spnMotivo = new JScrollPane(txaMotivoSolicitacao);

  private ListaCampos lcAlmox = new ListaCampos(this,"AM");
  private ListaCampos lcProd = new ListaCampos(this, "PD");
  private ListaCampos lcUsuario = new ListaCampos(this, "");
  private ListaCampos lcCC = new ListaCampos(this, "");
  private Connection con = null;
  String sOrdNota = "";
  Integer anoCC = null;
  Integer codCC = null;
  Integer codAlmox = null;
  String aprovSolicitacaoCompra = "";

  public FAprovaSolicitacaoCompra() {
    setTitulo("Solicitação de Compra");
    setAtribos(15, 10, 760, 580);

    pnMaster.remove(2);
    pnGImp.removeAll();
    pnGImp.setLayout(new GridLayout(1, 3));
    pnGImp.setPreferredSize(new Dimension(220, 26));
    pnGImp.add(btPrevimp);
    pnGImp.add(btImp);

    pnMaster.add(spTab, BorderLayout.CENTER);

    txtCodProd.setTipo(JTextFieldPad.TP_INTEGER, 8, 0);
    txtRefProd.setTipo(JTextFieldPad.TP_STRING, 13, 0);
    txtDescProd.setTipo(JTextFieldPad.TP_STRING, 50, 0);
    lcProd.add(new GuardaCampo(txtCodProd, 7, 100, 80, 20, "CodProd", "Código", true, false, txtDescProd, JTextFieldPad.TP_INTEGER, false), "txtCodProdx");
    lcProd.add(new GuardaCampo(txtDescProd, 90, 100, 207, 20, "DescProd", "Descrição", false, false, null, JTextFieldPad.TP_STRING, false), "txtDescProdx");
    lcProd.add(new GuardaCampo(txtRefProd, 90, 100, 207, 20, "RefProd", "Referência", false, false, null, JTextFieldPad.TP_STRING, false), "txtDescProdx");

    lcProd.setWhereAdic("ATIVOPROD='S'");
    lcProd.montaSql(false, "PRODUTO", "EQ");
    lcProd.setQueryCommit(false);
    lcProd.setReadOnly(true);

    txtCodProd.setTabelaExterna(lcProd);
    txtRefProd.setNomeCampo("RefProd");
    txtRefProd.setListaCampos(lcDet);
    txtRefProd.setTabelaExterna(lcProd);
    txtCodSolicitacao.setTipo(JTextFieldPad.TP_INTEGER, 8, 0);

    lcAlmox.add(new GuardaCampo(txtCodAlmoxarife, 7, 100, 80, 20, "codAlmox", "Cod. Almox.", true, false, txtDescAlmoxarife, JTextFieldPad.TP_STRING,false),"txtCodAlmoxarifex");
    lcAlmox.add(new GuardaCampo(txtDescAlmoxarife, 7, 100, 180, 20, "DescAlmox", "Desc. Almox;", false, false, null, JTextFieldPad.TP_STRING,false),"txtDescAlmoxarifex");
    lcAlmox.montaSql(false, "ALMOX", "EQ");    
    lcAlmox.setQueryCommit(false);
    lcAlmox.setReadOnly(true);  
    txtDescAlmoxarife.setSoLeitura(true);
    txtCodAlmoxarife.setTabelaExterna(lcAlmox);
   
    txtCodUsu.setNomeCampo("IdUsuItSol");
    txtNomeUsu.setTipo(JTextFieldPad.TP_STRING,50,0);
    lcUsuario.add(new GuardaCampo( txtCodUsu, 7, 100, 80, 20, "IDUSU", "ID Usuario", true, false, txtNomeUsu, JTextFieldPad.TP_STRING,false),"txtCodVendx");
    lcUsuario.add(new GuardaCampo( txtNomeUsu, 7, 100, 180, 20, "NOMEUSU", "Nome", false, false, null, JTextFieldPad.TP_STRING,false),"txtCodVendx");
    lcUsuario.montaSql(false, "USUARIO", "SG");    
    lcUsuario.setQueryCommit(false);
    lcUsuario.setReadOnly(true);
    txtCodUsu.setTabelaExterna(lcUsuario);

    lcCC.add(new GuardaCampo(txtCodCC, 7, 100, 80, 20, "codCC", "Código", true, false, txtDescCC, JTextFieldPad.TP_STRING,false),"txtCodVendx");
    lcCC.add(new GuardaCampo(txtDescCC, 7, 100, 180, 20, "DescCC", "Descriçao", false, false, null, JTextFieldPad.TP_STRING,false),"txtCodVendx");
    lcCC.montaSql(false, "CC", "FN");    
    lcCC.setQueryCommit(false);
    lcCC.setReadOnly(true);
    txtCodCC.setTabelaExterna(lcCC);    
    
    pinCab = new Painel(740, 180);
    setListaCampos(lcCampos);
    setAltCab(180);
    setPainel(pinCab, pnCliCab);

    txtDtEmitSolicitacao.setSoLeitura(true);
    txaMotivoSolicitacao.setEditable(false);
    txaMotivoSolicitacao.setEnabled(false);
    adicCampo(txtCodSolicitacao, 7, 20, 100, 20, "CodSol", "N. Solicit.", JTextFieldPad.TP_INTEGER, 8, 0, true, false, null, true);
    adicCampo(txtDtEmitSolicitacao, 110, 20, 100, 20, "DtEmitSol", "Data Solicit.", JTextFieldPad.TP_DATE, 10, 0, false, false, null, false);
    adicCampoInvisivel(txtStatusSolicitacao, "SitSol", "Situação", JTextFieldPad.TP_STRING, 2, 0, false, false, null, false);
    adic(btAprovaCompra, 500, 15, 230, 30);
    btAprovaCompra.setEnabled(false);
    adicDBLiv(txaMotivoSolicitacao, "MotivoSol", "Motivo",JTextFieldPad.TP_STRING, false);
    adic(new JLabel("Motivo"),7,40,100,20);
    adic(spnMotivo, 7, 60, 727, 77);
    adicCampoInvisivel(txtOrigSolicitacao, "OrigSol", "Origem", JTextFieldPad.TP_STRING, 2, 0, false, false, null, false);
    setListaCampos(true, "SOLICITACAO", "CP");
    lcCampos.setQueryInsert(false);	      
    
    txtQtdItSolicitado.addFocusListener(this);
    lcCampos.addPostListener(this);
    lcCampos.addCarregaListener(this);
    lcProd.addCarregaListener(this);
    lcDet.addPostListener(this);
    lcDet.addCarregaListener(this);
    lcDet.addInsertListener(this);
    lcCampos.addInsertListener(this);
    btAprovaCompra.addActionListener(this);
    btAprovaItem.addActionListener(this);
    btCancelaItem.addActionListener(this);

    btImp.addActionListener(this);
    btPrevimp.addActionListener(this);

  }
  private void montaDetalhe() {
    setAltDet(195);
    pinDet = new Painel(740, 195);
    setPainel(pinDet, pnDet);
    setListaCampos(lcDet);
    setNavegador(navRod);
   
    txtCodProd.setSoLeitura(true);
    adicCampo(txtCodItSolicitacao, 7, 20, 30, 20, "CodItSol", "Item", JTextFieldPad.TP_INTEGER, 8, 0, true, false, null, true);
    if (comRef()) {
      adicCampoInvisivel(txtCodProd, "CodProd", "Cód. Produto", JTextFieldPad.TP_INTEGER, 8, 0, false, true, txtDescProd, false);
      adicCampoInvisivel(txtRefProd, "RefProd", "Referência", JTextFieldPad.TP_STRING, 13, 0, false, true, null, false);
      adic(new JLabel("Referência"), 40, 0, 67, 20);
      txtRefProd.setSoLeitura(true);
      adic(txtRefProd, 40, 20, 67, 20);
    }
    else {
      adicCampo(txtCodProd, 40, 20, 87, 20, "CodProd", "Cód. Produto", JTextFieldPad.TP_INTEGER, 8, 0, false, true, txtDescProd, false);
      adicCampoInvisivel(txtRefProd, "RefProd", "Referência", JTextFieldPad.TP_STRING, 13, 0, false, true, null, false);
    }
   
    txtCodUsu.setSoLeitura(true);
    txtCodCC.setSoLeitura(true);
    adicCampo(txtCodCC, 7, 60, 42, 20, "codCC", "Cód.", JTextFieldPad.TP_STRING, 13, 0, false, true, txtDescCC, false);
    adicDescFK(txtDescCC, 52, 60, 182, 20, "DescCC", "e desc. do Centro de Custos", JTextFieldPad.TP_STRING, 50, 0);
    adicCampoInvisivel(txtAnoCC, "anoCC", "Ano do Centro de Custos", JTextFieldPad.TP_STRING, 13, 0, false, false, null, false);
    adicCampo(txtCodUsu, 237, 60, 47, 20, "IdUsuItSol", "Cód.", JTextFieldPad.TP_STRING, 13, 0, false, true, txtNomeUsu, false);
    adicDescFK(txtNomeUsu, 287, 60, 197, 20, "NomeUsu", " e nome do Usuário Solicitante", JTextFieldPad.TP_STRING, 50, 0);
    adic(btAprovaItem, 487, 55, 253, 30);
    btAprovaItem.setEnabled(false);
    adic(btCancelaItem, 615, 55, 125, 30);
    btCancelaItem.setVisible(false);
    btCancelaItem.setEnabled(false);
	
    txtCodAlmoxarife.setSoLeitura(true);
    adicCampo(txtCodAlmoxarife, 480, 20, 67, 20, "codAlmox", "Código", JTextFieldPad.TP_INTEGER, 8, 0, false, true, txtDescAlmoxarife, false);
    adicDescFK(txtDescAlmoxarife, 550, 20, 182, 20, "DescAlmox", "e desc. do Almox.", JTextFieldPad.TP_STRING, 50, 0);
    
    txtQtdItSolicitado.setSoLeitura(true);
    adicDescFK(txtDescProd, 130, 20, 197, 20, "DescProd", "e descrição do produto", JTextFieldPad.TP_STRING, 50, 0);
    adicCampo(txtQtdItSolicitado, 330, 20, 67, 20, "QtdItSol", "Qtd. Solic.", JTextFieldPad.TP_DECIMAL, 15, casasDec, false, false, null, false);
    adicCampo(txtQtdItAprovado, 400, 20, 77, 20, "QtdAprovItSol", "Qtd. Aprov.", JTextFieldPad.TP_DECIMAL, 15, casasDec, false, false, null, false);
    adicCampoInvisivel(txtSituaçãoIt, "SitItSol", "Sit. do Item", JTextFieldPad.TP_STRING, 13, 0, false, false, null, false);
    adicCampoInvisivel(txtSituaçãoItComp, "SitCompItSol", "Sit. da Compra", JTextFieldPad.TP_STRING, 13, 0, false, false, null, false);
    adicCampoInvisivel(txtSituaçãoItAprov, "SitAprovItSol", "Sit. da Aprovação", JTextFieldPad.TP_STRING, 13, 0, false, false, null, false);
    
    setListaCampos(true, "ITSOLICITACAO", "CP");
    lcDet.setQueryInsert(false);
    montaTab();

    tab.setTamColuna(30, 0);
    tab.setTamColuna(80, 1);
    tab.setTamColuna(230, 2);
    tab.setTamColuna(70, 3);
    tab.setTamColuna(70, 4);
    tab.setTamColuna(70, 5);
    tab.setTamColuna(70, 6);
    tab.setTamColuna(70, 7);
    tab.setTamColuna(70, 8);
    tab.setTamColuna(70, 9);
    tab.setTamColuna(70, 10);
  }
  private void testaCodSol() { //Traz o verdadeiro número do codCompra
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
      txtCodSolicitacao.setVlrString(rs.getString(1));
      if (!con.getAutoCommit())
        con.commit();
    }
    catch (SQLException err) {
      Funcoes.mensagemErro(this, "Erro ao confirmar código da Compra!\n"
          + err.getMessage());
    }
  }
  public void focusGained(FocusEvent fevt) {}
  public void focusLost(FocusEvent fevt) {}
  public void beforeCarrega(CarregaEvent cevt) {
  	
  }
  public void afterPost(PostEvent pevt) {}
  public void afterCarrega(CarregaEvent cevt) {
    if (cevt.getListaCampos() == lcDet) {
    	if (txtSituaçãoIt.getVlrString().equalsIgnoreCase("PE")) {
    		if (aprovSolicitacaoCompra.equalsIgnoreCase("TD") || 
    				(aprovSolicitacaoCompra.equalsIgnoreCase("CC") && 
    				txtCodCC.getVlrString().equals(codCC) && 
    				txtAnoCC.getVlrString().equals(anoCC))) {
    			btAprovaItem.setEnabled(true);
    			btAprovaItem.setIcon(imgOk);
    			btAprovaItem.setText("Aprovar Item");
    			btAprovaItem.setSize(125, 30);
    			btCancelaItem.setEnabled(true);
    			btCancelaItem.setVisible(true);
    		} else {
    			btCancelaItem.setEnabled(false);
    			btCancelaItem.setVisible(false);
    			btAprovaItem.setSize(253, 30);
    			btAprovaItem.setEnabled(false);
    			btAprovaItem.setIcon(null);
    			btAprovaItem.setText("Item Pendente");    			
    		}
    		if (txtQtdItAprovado.getVlrDouble().doubleValue() == 0) {
        		txtQtdItAprovado.setVlrDouble(txtQtdItSolicitado.getVlrDouble());
        	}    		
    	} else if (txtSituaçãoIt.getVlrString().equalsIgnoreCase("CA")) {
			btCancelaItem.setEnabled(false);
			btCancelaItem.setVisible(false);
			btAprovaItem.setSize(253, 30);
			btAprovaItem.setEnabled(false);
    		btAprovaItem.setIcon(null);
    		btAprovaItem.setText("Item Cancelado");
    	} else if (txtSituaçãoIt.getVlrString().equalsIgnoreCase("SC")) {
			btCancelaItem.setEnabled(false);
			btCancelaItem.setVisible(false);
			btAprovaItem.setSize(253, 30);
    		btAprovaItem.setEnabled(false);
    		btAprovaItem.setIcon(null);
    		if (txtSituaçãoItAprov.getVlrString().equalsIgnoreCase("AT") &&
    				txtSituaçãoItComp.getVlrString().equalsIgnoreCase("CT"))
    			btAprovaItem.setText("Aprovado Totalmente");
    		else if (txtSituaçãoItAprov.getVlrString().equalsIgnoreCase("AP") &&
    				txtSituaçãoItComp.getVlrString().equalsIgnoreCase("CP"))
    			btAprovaItem.setText("Aprovado Parcialmente");
    		else
    			btAprovaItem.setText("Item Concluído");
    	}
    }
    else if (cevt.getListaCampos() == lcCampos) {
    	if (txtStatusSolicitacao.getVlrString().equalsIgnoreCase("PE")) {
    		if (aprovSolicitacaoCompra.equalsIgnoreCase("TD")) {
    			btAprovaCompra.setEnabled(true);
    			btAprovaCompra.setIcon(imgCancel);
    			btAprovaCompra.setText("Cancelar Solicitação");
    		} else {
    			btAprovaCompra.setEnabled(false);
    			btAprovaCompra.setIcon(null);
    			btAprovaCompra.setText("Solicitação Pendente");    			
    		}
    	} else if (txtStatusSolicitacao.getVlrString().equalsIgnoreCase("CA")) {
    		btAprovaCompra.setEnabled(false);
    		btAprovaCompra.setIcon(null);
    		btAprovaCompra.setText("Solicitação Cancelada");
    	} else if (txtStatusSolicitacao.getVlrString().equalsIgnoreCase("SC")) {
    		btAprovaCompra.setEnabled(false);
    		btAprovaCompra.setIcon(null);
    		btAprovaCompra.setText("Solicitação Concluída");
    	}
    }
  }
  public void keyPressed(KeyEvent kevt) {
    if (kevt.getKeyCode() == KeyEvent.VK_ENTER) {
      if (kevt.getSource() == txtQtdItAprovado) {//Talvez este possa ser
                                                       // o ultimo campo do
                                                       // itvenda.
      	btAprovaItem.doClick();
        if (lcDet.getStatus() == ListaCampos.LCS_EDIT) {
          lcDet.post();
          txtCodItSolicitacao.requestFocus();
        }
      }
    }
    super.keyPressed(kevt);
  }
  public void actionPerformed(ActionEvent evt) {
    String[] sValores = null;
    if (evt.getSource() == btPrevimp)
      imprimir(true, txtCodSolicitacao.getVlrInteger().intValue());
    else if (evt.getSource() == btImp)
      imprimir(false, txtCodSolicitacao.getVlrInteger().intValue());
    else if (evt.getSource() == btAprovaCompra) {
    	if (lcDet.getStatus() != ListaCampos.LCS_EDIT)
    		lcDet.edit();
    	txtStatusSolicitacao.setVlrString("CA");
		btAprovaCompra.setEnabled(false);
		btAprovaCompra.setIcon(null);
		btAprovaCompra.setText("Solicitação Cancelada");    	
    } else if (evt.getSource() == btAprovaItem) {
    	if (lcDet.getStatus() != ListaCampos.LCS_EDIT)
    		lcDet.edit();
		btCancelaItem.setEnabled(false);
		btCancelaItem.setVisible(false);
		btAprovaItem.setSize(253, 30);    	
		btAprovaItem.setEnabled(false);
		btAprovaItem.setIcon(null);
    	if (txtQtdItAprovado.getVlrDouble().doubleValue() == 
    		txtQtdItSolicitado.getVlrDouble().doubleValue()) {
    		txtSituaçãoItAprov.setVlrString("AT");
    		txtSituaçãoItComp.setVlrString("CT");
        	txtSituaçãoIt.setVlrString("SC");
    		btAprovaItem.setText("Aprovação Total");    	    	    	
    	} else if (txtQtdItAprovado.getVlrDouble().doubleValue() > 0) {
    		txtSituaçãoItAprov.setVlrString("AP");
    		txtSituaçãoItComp.setVlrString("CP");
        	txtSituaçãoIt.setVlrString("SC");
    		btAprovaItem.setText("Aprovação Parcial");    	    	    	    		
    	} else if (txtQtdItAprovado.getVlrDouble().doubleValue() == 0) {
        	txtSituaçãoItAprov.setVlrString("NA");
        	txtSituaçãoItComp.setVlrString("PE");
        	txtSituaçãoIt.setVlrString("CA");
    		btAprovaItem.setText("Solicitação Cancelada");    	    	    		
    	}
    } else if (evt.getSource() == btCancelaItem) {
    	if (lcDet.getStatus() != ListaCampos.LCS_EDIT)
    		lcDet.edit();
    	txtSituaçãoItAprov.setVlrString("NA");
    	txtSituaçãoItComp.setVlrString("PE");
    	txtSituaçãoIt.setVlrString("CA");
		btCancelaItem.setEnabled(false);
		btCancelaItem.setVisible(false);
		btAprovaItem.setSize(253, 30);    	
		btAprovaCompra.setEnabled(false);
		btAprovaCompra.setIcon(null);
		btAprovaCompra.setText("Solicitação Cancelada"); 
		txtQtdItAprovado.setVlrDouble(new Double(0));
    }
    super.actionPerformed(evt);
  }
  private void imprimir(boolean bVisualizar, int iCodSol) {
    ImprimeOS imp = new ImprimeOS("", con);
    DLRPedido dl = new DLRPedido(sOrdNota);
    dl.show();
    if (dl.OK == false) {
      dl.dispose();
      return;
    }
    imp.verifLinPag();
    imp.setTitulo("Relatório de Solicitação de Compras");
    String sSQL = "SELECT (SELECT COUNT(IC.CODITSOL) FROM CPITSOLICITACAO IC WHERE IC.CODSOL=S.CODSOL),"
        + "S.CODSOL,S.DTEMITSOL,S.SITSOL,S.MOTIVOSOL," 
		+ "I.CODPROD, I.QTDITSOL, I.QTDAPROVITSOL,I.SITAPROVITSOL, I.SITCOMPITSOL, I.SITITSOL,"
		+ "P.REFPROD,P.DESCPROD, P.CODUNID,"
        + "A.CODALMOX, A.DESCALMOX, CC.CODCC, CC.ANOCC"
        + " FROM CPSOLICITACAO S, CPITSOLICITACAO I, EQALMOX A, FNCC CC, EQPRODUTO P"
        + " WHERE S.CODSOL=" + iCodSol
        + " AND I.CODSOL=S.CODSOL" 
		+ " AND P.CODPROD=I.CODPROD"
		+ " AND I.CODALMOX=I.CODALMOX"
		+ " AND CC.CODCC=I.CODCC"
        + " ORDER BY S.CODSOL,P."
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
          imp.say(imp.pRow() + 0, 4, "SOLICITAÇÂO DE COMPRA No.: ");
          imp.say(imp.pRow() + 0, 25, rs.getString("CODSOL"));
          imp.say(imp.pRow() + 1, 0, "" + imp.normal());
          imp.say(imp.pRow() + 0, 0, "");
          imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
          imp.say(imp.pRow() + 0, 96, "[ Data de Emissão ]");
          imp.say(imp.pRow() + 0, 100, Funcoes.sqlDateToStrDate(rs.getDate("DTEMITSOL")));
          imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
          imp.say(imp.pRow() + 0, 0, "");
          imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
          imp.say(imp.pRow() + 0, 57, "DADOS DO(S) PRODUTO(S)");
          imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
          imp.say(imp.pRow() + 0, 0, "");
          imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
          imp.say(imp.pRow() + 0, 4, "Referencia");
          imp.say(imp.pRow() + 0, 18, "Descrição dos Produtos");
          imp.say(imp.pRow() + 0, 60, "Qtd. Sol.");
          imp.say(imp.pRow() + 0, 75, "Qtd. Aprov.");
          imp.say(imp.pRow() + 0, 90, "Sit. Item");
          imp.say(imp.pRow() + 0, 110, "Sit. Compra");
          imp.say(imp.pRow() + 0, 130, "Sit. Aprov.");
          imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
          imp.say(imp.pRow() + 0, 0, "");
        }
        imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
        imp.say(imp.pRow() + 0, 4, rs.getString("RefProd"));
        imp.say(imp.pRow() + 0, 18, rs.getString("DescProd").substring(0, 39));
        imp.say(imp.pRow() + 0, 60, "" + rs.getDouble("QTDITSOL"));
        imp.say(imp.pRow() + 0, 75, "" + rs.getDouble("QTDAPROVITSOL"));
        if (rs.getString("SITITSOL").equalsIgnoreCase("PE")) 
        	imp.say(imp.pRow() + 0, 90, "PENDENTE");
        if (rs.getString("SITITSOL").equalsIgnoreCase("SC")) 
        	imp.say(imp.pRow() + 0, 90, "CONCLUÍDO");
        if (rs.getString("SITITSOL").equalsIgnoreCase("SA")) 
        	imp.say(imp.pRow() + 0, 90, "CANCELADO");
        if (rs.getString("SITCOMPITSOL").equalsIgnoreCase("PE")) 
        	imp.say(imp.pRow() + 0, 110, "PENDENTE");
        if (rs.getString("SITCOMPITSOL").equalsIgnoreCase("CP")) 
        	imp.say(imp.pRow() + 0, 110, "COMPRA PARCIAL");
        if (rs.getString("SITCOMPITSOL").equalsIgnoreCase("CT")) 
        	imp.say(imp.pRow() + 0, 110, "COMPRA TOTAL");
        if (rs.getString("SITAPROVITSOL").equalsIgnoreCase("PE")) 
        	imp.say(imp.pRow() + 0, 130, "PENDENTE");
        if (rs.getString("SITAPROVITSOL").equalsIgnoreCase("AP")) 
        	imp.say(imp.pRow() + 0, 130, "APROVAÇÂO PARCIAL");
        if (rs.getString("SITAPROVITSOL").equalsIgnoreCase("AT")) 
        	imp.say(imp.pRow() + 0, 130, "APROVAÇÂO TOTAL");
        if (rs.getString("SITAPROVITSOL").equalsIgnoreCase("NA")) 
        	imp.say(imp.pRow() + 0, 130, "NÃO APROVADA");
        imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
        iItImp++;
        if ((imp.pRow() >= iMaxItem) | (iItImp == rs.getInt(1))) {
          if ((iItImp == rs.getInt(1))) {
            int iRow = imp.pRow();
            for (int i = 0; i < (iMaxItem - iRow); i++) {
              imp.say(imp.pRow() + 1, 0, "" + imp.comprimido());
              imp.say(imp.pRow() + 0, 0, "");
            }
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
          if (rs.getString("SITSOL").equalsIgnoreCase("PE")) 
          	imp.say(imp.pRow() + 0, (116 - "Pendente".length()) / 2, "SITUAÇÂO : PENDENTE");
          if (rs.getString("SITSOL").equalsIgnoreCase("SC")) 
          	imp.say(imp.pRow() + 0, (116 - "Pendente".length()) / 2, "SITUAÇÂO : CONCLUÍDA");
          if (rs.getString("SITSOL").equalsIgnoreCase("SA")) 
          	imp.say(imp.pRow() + 0, (116 - "Pendente".length()) / 2, "SITUAÇÂO : CANCELADA");
          imp.say(imp.pRow() + 2, 0, "" + imp.comprimido());
        	imp.say(imp.pRow() + 0, 3, "MOTIVO : " + rs.getString("MOTIVOSOL"));
          imp.eject();
        }
      }
      imp.fechaGravacao();

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
      txtRefProd.setVlrString(lcProd.getCampo("RefProd").getText()); // ?
    }
  }
  public void beforeInsert(InsertEvent ievt) {}
  public void afterInsert(InsertEvent ievt) {
   	txtAnoCC.setVlrInteger(anoCC);
   	txtCodCC.setVlrInteger(codCC);
   	txtCodAlmoxarife.setVlrInteger(codAlmox);
   	txtCodAlmoxarife.atualizaFK();
   	txtCodUsu.setVlrString(Aplicativo.strUsuario);
    txtDtEmitSolicitacao.setVlrDate(new Date());
  }
  public void exec(int iCodCompra) {
    txtCodSolicitacao.setVlrString(iCodCompra + "");
    lcCampos.carregaDados();
  }
  public void execDev(int iCodFor, int iCodTipoMov, String sSerie, int iDoc) {
    lcCampos.insert(true);
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
  public void execShow(Connection cn) {
    con = cn;
    montaDetalhe();
    lcProd.setConexao(cn);
	lcAlmox.setConexao(cn);  
	lcUsuario.setConexao(cn);  
	lcCC.setConexao(cn);  
	
	String sSQL = "SELECT anoCC, codCC, codAlmox, aprovCPSolicitacaoUsu FROM SGUSUARIO WHERE CODEMP=? AND CODFILIAL=? AND IDUsu=?";
	PreparedStatement ps = null;
	ResultSet rs = null;
	try {
		ps = con.prepareStatement(sSQL);
		ps.setInt(1, Aplicativo.iCodEmp);
		ps.setInt(2, ListaCampos.getMasterFilial("SGPREFERE1"));
		ps.setString(3, Aplicativo.strUsuario);
		rs = ps.executeQuery();
		if (rs.next()) {
			anoCC = new Integer(rs.getInt("anoCC"));
			if (anoCC.intValue() == 0)
				anoCC = new Integer(buscaAnoBaseCC());
			codCC = new Integer(rs.getInt("codCC"));
			codAlmox = new Integer(rs.getInt("codAlmox"));
			aprovSolicitacaoCompra = rs.getString("aprovCPSolicitacaoUsu");
		}
		
		if (!con.getAutoCommit())
		  con.commit();	
	}
	catch (SQLException err) {
	  Funcoes.mensagemErro(this, "Erro ao carregar a tabela PREFERE1!\n"
	      + err.getMessage());
	}	
	
    super.execShow(cn);
  }
}