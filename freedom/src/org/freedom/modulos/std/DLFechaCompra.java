/**
 * @version 14/07/2003 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)DLFechaCompra.java <BR>
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
 */

package org.freedom.modulos.std;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JCheckBoxPad;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.Painel;
import org.freedom.componentes.Tabela;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FFDialogo;

public class DLFechaCompra extends FFDialogo implements FocusListener {
  private JTabbedPane tpn = new JTabbedPane();
  private Painel pinFecha = new Painel(420,300);
  private JPanel pnPagar = new JPanel(new BorderLayout());
  private JTextFieldPad txtCodCompra = new JTextFieldPad();
  private JTextFieldPad txtVlrDescItCompra = new JTextFieldPad();
  private JTextFieldPad txtPercDescCompra = new JTextFieldPad();
  private JTextFieldPad txtVlrDescCompra = new JTextFieldPad();
  private JTextFieldPad txtPercAdicCompra = new JTextFieldPad();
  private JTextFieldPad txtVlrLiqCompra = new JTextFieldPad();
  private JTextFieldPad txtVlrAdicCompra = new JTextFieldPad();
  private JTextFieldPad txtVlrProdCompra = new JTextFieldPad();
  private JTextFieldPad txtVlrFreteCompra = new JTextFieldPad();
  private JTextFieldPad txtVlrICMSCompra = new JTextFieldPad();
  private JTextFieldPad txtVlrIPICompra = new JTextFieldPad();
  private JTextFieldPad txtCodPlanoPag = new JTextFieldPad();
  private JTextFieldPad txtCodPag = new JTextFieldPad();
  private JTextFieldPad txtCodBanco = new JTextFieldPad();
  private JTextFieldPad txtNParcPag = new JTextFieldPad();
  private JTextFieldPad txtVlrParcItPag = new JTextFieldPad();
  private JTextFieldPad txtVlrParcPag = new JTextFieldPad();
  private JTextFieldPad txtDtVencItPag = new JTextFieldPad();  
  private JTextFieldPad txtStatusCompra = new JTextFieldPad();
  private JTextFieldFK txtDescPlanoPag = new JTextFieldFK();
  private JTextFieldFK txtDescBanco = new JTextFieldFK();
  private JLabel lbPercDescCompra = new JLabel("% Desc.");
  private JLabel lbVlrDescCompra = new JLabel("V Desc.");
  private JLabel lbVlrFreteCompra = new JLabel("V Frete.");
  private JLabel lbPercAdicCompra = new JLabel("% Adic.");
  private JLabel lbVlrAdicCompra = new JLabel("V Adic.");
  private JLabel lbCodPlanoPag = new JLabel("Código e Desc. do plano de pagto.");
  private JCheckBoxPad cbImpPed = new JCheckBoxPad("Imprime Pedido?","S","N");
  private JCheckBoxPad cbImpNot = new JCheckBoxPad("Entrada de Nota?","S","N");
  private ListaCampos lcCompra = new ListaCampos(this);
  private ListaCampos lcPlanoPag = new ListaCampos(this,"PG");
  private ListaCampos lcPagar = new ListaCampos(this);
  private ListaCampos lcBanco = new ListaCampos(this,"BO");
  private ListaCampos lcItPagar = new ListaCampos(this);
  private Connection con = null;
  private Tabela tabPag = new Tabela();
  private int iCodCompraFecha = 0;
  private boolean bPodeSair = false;
  public DLFechaCompra(Connection cn, Integer iCodCompra,Component cOrig) {
  	super(cOrig);
    con = cn;
    iCodCompraFecha = iCodCompra.intValue();
    setTitulo("Fechar Compra");
    setAtribos(440,350);

    lcItPagar.setMaster(lcPagar);
    lcPagar.adicDetalhe(lcItPagar);
    lcItPagar.setTabela(tabPag);

    c.add(tpn);
    
    tpn.add("Fechamento",pinFecha);
    tpn.add("Pagar",pnPagar);

    txtCodPlanoPag.setNomeCampo("CodPlanoPag");
    txtCodPlanoPag.setTipo(JTextFieldPad.TP_INTEGER,8,0);
    txtDescPlanoPag.setTipo(JTextFieldPad.TP_STRING,40,0);
    lcPlanoPag.add(new GuardaCampo( txtCodPlanoPag, 7, 100, 80, 20, "CodPlanoPag", "Código", true, false, null, JTextFieldPad.TP_INTEGER,false),"txtCodPlanoPagx");
    lcPlanoPag.add(new GuardaCampo( txtDescPlanoPag, 90, 100, 207, 20, "DescPlanoPag", "Descrição", false, false, null, JTextFieldPad.TP_STRING,false),"txtDescPlanoPagx");
    txtCodPlanoPag.setTabelaExterna(lcPlanoPag);	txtCodPlanoPag.setFK(true);
    txtDescPlanoPag.setListaCampos(lcPlanoPag);
    lcPlanoPag.montaSql(false, "PLANOPAG", "FN");
    lcPlanoPag.setQueryCommit(false);
    lcPlanoPag.setReadOnly(true);
    lcPlanoPag.setConexao(cn);

    txtCodBanco.setNomeCampo("CodBanco");
    txtCodBanco.setTipo(JTextFieldPad.TP_STRING,3,0);
    txtDescBanco.setTipo(JTextFieldPad.TP_STRING,40,0);
    lcBanco.add(new GuardaCampo( txtCodBanco, 7, 100, 80, 20, "CodBanco", "Código", true, false, null, JTextFieldPad.TP_STRING,false),"txtCodBancox");
    lcBanco.add(new GuardaCampo( txtDescBanco, 90, 100, 207, 20, "NomeBanco", "Descrição", false, false, null, JTextFieldPad.TP_STRING,false),"txtDescBancox");
    txtDescBanco.setListaCampos(lcBanco);
    lcBanco.montaSql(false, "BANCO", "FN");
    lcBanco.setQueryCommit(false);
    lcBanco.setReadOnly(true);
    lcBanco.setConexao(cn);
    txtCodBanco.setTabelaExterna(lcBanco);
	txtCodBanco.setFK(true);

    txtCodCompra.setTipo(JTextFieldPad.TP_INTEGER,8,0);
    txtCodCompra.setNomeCampo("CodCompra");
    txtCodPlanoPag.setTabelaExterna(lcPlanoPag);
    txtVlrDescItCompra.setTipo(JTextFieldPad.TP_DECIMAL,15,2);
    txtVlrProdCompra.setTipo(JTextFieldPad.TP_DECIMAL,15,2);
    txtVlrLiqCompra.setTipo(JTextFieldPad.TP_DECIMAL,15,2);
    txtVlrICMSCompra.setTipo(JTextFieldPad.TP_DECIMAL,15,2);
    txtVlrIPICompra.setTipo(JTextFieldPad.TP_DECIMAL,15,2);
    txtVlrAdicCompra.setTipo(JTextFieldPad.TP_DECIMAL,15,2);
    txtVlrDescCompra.setTipo(JTextFieldPad.TP_DECIMAL,15,2);
    txtVlrFreteCompra.setTipo(JTextFieldPad.TP_DECIMAL,15,2);
    txtStatusCompra.setTipo(JTextFieldPad.TP_STRING,2,0);
    lcCompra.add(new GuardaCampo( txtCodCompra, 7, 100, 80, 20, "CodCompra", "Código", true, false, null, JTextFieldPad.TP_INTEGER,false),"txtCodPlanoPagx");
    lcCompra.add(new GuardaCampo( txtCodPlanoPag, 7, 100, 80, 20, "CodPlanoPag", "CodPlan", false, true, txtDescPlanoPag, JTextFieldPad.TP_INTEGER,false),"txtCodPlanoPagx");
    lcCompra.add(new GuardaCampo( txtVlrLiqCompra, 7, 100, 80, 20, "VlrLiqCompra", "V. Compra", false, false, null, JTextFieldPad.TP_DECIMAL,false),"txtCodPlanoPagx");
    lcCompra.add(new GuardaCampo( txtVlrICMSCompra, 7, 100, 80, 20, "VlrICMSCompra", "V. ICMS", false, false, null, JTextFieldPad.TP_DECIMAL,false),"txtCodPlanoPagx");
    lcCompra.add(new GuardaCampo( txtVlrIPICompra, 7, 100, 80, 20, "VlrIPICompra", "V. IPI", false, false, null, JTextFieldPad.TP_DECIMAL,false),"txtCodPlanoPagx");
    lcCompra.add(new GuardaCampo( txtVlrDescItCompra, 7, 100, 80, 20, "VlrDescItCompra", "% Desc It.", false, false, null, JTextFieldPad.TP_DECIMAL,false),"txtCodPlanoPagx");
    lcCompra.add(new GuardaCampo( txtVlrDescCompra, 7, 100, 80, 20, "VlrDescCompra", "% Desc It.", false, false, null, JTextFieldPad.TP_DECIMAL,false),"txtCodPlanoPagx");
    lcCompra.add(new GuardaCampo( txtVlrAdicCompra, 7, 100, 80, 20, "VlrAdicCompra", "V. Adic.", false, false, null, JTextFieldPad.TP_DECIMAL,false),"txtCodPlanoPagx");
    lcCompra.add(new GuardaCampo( txtVlrProdCompra, 7, 100, 80, 20, "VlrProdCompra", "V. Prod.", false, false, null, JTextFieldPad.TP_DECIMAL,false),"txtCodPlanoPagx");
    lcCompra.add(new GuardaCampo( txtVlrFreteCompra, 7, 100, 80, 20, "VlrFreteCompra", "V. Prod.", false, false, null, JTextFieldPad.TP_DECIMAL,false),"txtCodPlanoPagx");
    lcCompra.add(new GuardaCampo( txtStatusCompra, 7, 100, 80, 20, "StatusCompra", "Status", false, false, null, JTextFieldPad.TP_STRING,false),"txtCodPlanoPagx");
    lcCompra.add(new GuardaCampo( txtCodBanco, 7, 100, 80, 20, "CodBanco", "CodBanco", false, true, txtDescBanco, JTextFieldPad.TP_STRING,false),"txtCodPlanoPagx");
    lcCompra.montaSql(false, "COMPRA", "CP");
    lcCompra.setConexao(cn);
    txtVlrLiqCompra.setListaCampos(lcCompra);
    txtVlrICMSCompra.setListaCampos(lcCompra);
    txtVlrIPICompra.setListaCampos(lcCompra);
    txtVlrAdicCompra.setListaCampos(lcCompra);
    txtPercAdicCompra.setListaCampos(lcCompra);
    txtVlrDescCompra.setListaCampos(lcCompra);
    txtVlrFreteCompra.setListaCampos(lcCompra);
    txtPercDescCompra.setListaCampos(lcCompra);
    txtStatusCompra.setListaCampos(lcCompra);
    txtCodPlanoPag.setListaCampos(lcCompra);

    Painel pinTopPag = new Painel(400,60);
    pinTopPag.setPreferredSize(new Dimension(400,60));
    pnPagar.add(pinTopPag,BorderLayout.NORTH);
    JScrollPane spnTabPag = new JScrollPane(tabPag);
    pnPagar.add(spnTabPag,BorderLayout.CENTER);

    txtVlrParcPag.setAtivo(false);

    pinTopPag.adic(new JLabel("Valor Tot."),7,0,130,20);
    pinTopPag.adic(txtVlrParcPag,7,20,130,20);

    txtCodPag.setNomeCampo("CodPag");
    txtCodPag.setTipo(JTextFieldPad.TP_INTEGER,8,0);
    txtVlrParcPag.setTipo(JTextFieldPad.TP_DECIMAL,15,2);
    lcPagar.add(new GuardaCampo( txtCodPag, 7, 100, 80, 20, "CodPag", "Código", true, false, null, JTextFieldPad.TP_INTEGER,false),"txtCodPlanoPagx");
    lcPagar.add(new GuardaCampo( txtVlrParcPag, 7, 100, 80, 20, "VlrParcPag", "Valor Tot.", false, false, null, JTextFieldPad.TP_DECIMAL,false),"txtCodPlanoPagx");
    lcPagar.montaSql(false, "PAGAR", "FN");
    lcPagar.setConexao(cn);
    txtCodPag.setListaCampos(lcPagar);
    txtVlrParcPag.setListaCampos(lcPagar);
    txtCodBanco.setListaCampos(lcPagar);

    txtNParcPag.setNomeCampo("NParcPag");
    txtNParcPag.setTipo(JTextFieldPad.TP_INTEGER,8,0);
    txtVlrParcItPag.setTipo(JTextFieldPad.TP_DECIMAL,15,2);
    txtDtVencItPag.setTipo(JTextFieldPad.TP_DATE,10,0);
    lcItPagar.add(new GuardaCampo( txtNParcPag, 7, 100, 80, 20, "NParcPag", "Código", true, false, null, JTextFieldPad.TP_INTEGER,false),"txtCodPlanoPagx");
    lcItPagar.add(new GuardaCampo( txtVlrParcItPag, 7, 100, 80, 20, "VlrParcItPag", "Valor Tot.", false, false, null, JTextFieldPad.TP_DECIMAL,false),"txtCodPlanoPagx");
    lcItPagar.add(new GuardaCampo( txtDtVencItPag, 7, 100, 80, 20, "DtVencItPag", "Valor Tot.", false, false, null, JTextFieldPad.TP_DATE,false),"txtCodPlanoPagx");
    lcItPagar.montaSql(false, "ITPAGAR", "FN");
    lcItPagar.setConexao(cn);
    txtNParcPag.setListaCampos(lcItPagar);
    txtVlrParcItPag.setListaCampos(lcItPagar);
    txtDtVencItPag.setListaCampos(lcItPagar);
    
    lcItPagar.montaTab();
    tabPag.addMouseListener(
      new MouseAdapter() {
        public void mouseClicked(MouseEvent mevt) {
          if ((mevt.getClickCount() == 2) && (tabPag.getLinhaSel() >= 0)) {
             lcItPagar.edit();
             DLFechaPag dl = new DLFechaPag(DLFechaCompra.this,txtVlrParcItPag.getVlrBigDecimal(),txtDtVencItPag.getVlrDate());
             dl.setVisible(true);
            if (dl.OK) {
              txtVlrParcItPag.setVlrBigDecimal((BigDecimal)dl.getValores()[0]);
              txtDtVencItPag.setVlrDate((Date)dl.getValores()[1]);
              lcItPagar.post();
              //Atualiza lcPagar              
              if (lcPagar.getStatus() == ListaCampos.LCS_EDIT) 
                lcPagar.post(); // Caso o lcPagar estaja como edit executa o post que atualiza
              else 
                lcPagar.carregaDados(); //Caso não, atualiza
            }
            else {
              dl.dispose();
              lcItPagar.cancel(false);
            }
            dl.dispose();
          }
        }
      }
    );

    txtCodCompra.setVlrInteger(iCodCompra);
    lcCompra.carregaDados();

    txtPercDescCompra.setTipo(JTextFieldPad.TP_DECIMAL,6,2);
    txtPercAdicCompra.setTipo(JTextFieldPad.TP_DECIMAL,6,2);
    
    setPainel(pinFecha);
    adic(lbCodPlanoPag,7,0,270,20);
    adic(txtCodPlanoPag,7,20,80,20);
    adic(txtDescPlanoPag,90,20,270,20);
    adic(lbPercDescCompra,7,40,100,20);
    adic(txtPercDescCompra,7,60,100,20);
    adic(lbVlrDescCompra,110,40,97,20);
    adic(txtVlrDescCompra,110,60,97,20);
    adic(lbPercAdicCompra,210,40,97,20);
    adic(txtPercAdicCompra,210,60,97,20);
    adic(lbVlrAdicCompra,310,40,100,20);
    adic(txtVlrAdicCompra,310,60,100,20);
    adic(lbVlrFreteCompra,7,80,100,20);
    adic(txtVlrFreteCompra,7,100,100,20);
    adic(new JLabel("V. Compra"),110,80,100,20);
    adic(txtVlrLiqCompra,110,100,97,20);
    adic(new JLabel("V. ICMS"),210,80,100,20);
    adic(txtVlrICMSCompra,210,100,97,20);
    adic(new JLabel("V. IPI"),310,80,100,20);
    adic(txtVlrIPICompra,310,100,100,20);
    adic(new JLabel("Código e Descrição do Banco"),7,120,250,20);
    adic(txtCodBanco,7,140,80,20);
    adic(txtDescBanco,90,140,200,20);
    adic(cbImpPed,7,170,200,20);
    adic(cbImpNot,7,190,200,20);

    if (txtVlrDescItCompra.getVlrString().length() > 0) {
      txtPercDescCompra.setAtivo(false);
      txtVlrDescCompra.setAtivo(false);
    }
    
    tpn.setEnabledAt(1,false);

    txtPercDescCompra.addFocusListener(this);
    txtVlrDescCompra.addFocusListener(this);
    txtPercAdicCompra.addFocusListener(this);
    txtVlrAdicCompra.addFocusListener(this);

    lcCompra.edit();
  }
  private int trazCodPag() {
    int iRetorno = 0;
    String sSQL = "SELECT CODPAG FROM FNPAGAR WHERE CODCOMPRA=? AND CODEMPCP=? AND CODFILIALCP=?";
    PreparedStatement ps = null;
    ResultSet rs = null;
    try {
      ps = con.prepareStatement(sSQL);
      ps.setInt(1,iCodCompraFecha);
      ps.setInt(2,Aplicativo.iCodEmp);
      ps.setInt(3,ListaCampos.getMasterFilial("CPCOMPRA"));
      rs = ps.executeQuery();
      if (rs.next()) {
        iRetorno = rs.getInt("CodPag");
      }
//      rs.close();
//      ps.close();
      if (!con.getAutoCommit())
      	con.commit();
    }
    catch (SQLException err) {
		Funcoes.mensagemErro(this,"Erro ao buscar o código da conta a Pagar!\n"+err.getMessage());
    }
    return iRetorno;
  }
  public void actionPerformed(ActionEvent evt) {
    if (evt.getSource() == btCancel) {
      super.actionPerformed(evt);
    }
    else if (bPodeSair) {
      if (evt.getSource() == btOK) {
        if (lcPagar.getStatus() == ListaCampos.LCS_EDIT) {
          lcPagar.post();
        }
        if (cbImpPed.getVlrString().trim().equals("S")) {
          lcCompra.edit();
          txtStatusCompra.setVlrString("P3");
          if (!lcCompra.post()) {
            cbImpPed.setVlrString("N");
          }
        }
        if (cbImpNot.getVlrString().trim().equals("S")) {
          lcCompra.edit();
          txtStatusCompra.setVlrString("C3");
          if (!lcCompra.post()) {
            cbImpNot.setVlrString("N");
          }
        }
      }
      super.actionPerformed(evt);
    }
    if (evt.getSource() == btOK) {
      if (tpn.getSelectedIndex() == 0) {
        lcCompra.edit();
        if (txtStatusCompra.getVlrString().trim().equals("P1")) 
          txtStatusCompra.setVlrString("P2");
        if (txtStatusCompra.getVlrString().trim().equals("V1"))
          txtStatusCompra.setVlrString("C2");
        lcCompra.post();
        tpn.setEnabledAt(1,true);
        tpn.setSelectedIndex(1);
        int iCodPag = trazCodPag();
        if (iCodPag > 0) {
          txtCodPag.setVlrInteger(new Integer(iCodPag));
          lcPagar.carregaDados();
          lcPagar.carregaDados();
        }
        bPodeSair = true;
      }
    }
  }
  public String[] getValores() {
    String[] sRetorno = new String[6];
    sRetorno[0] = txtCodPlanoPag.getVlrString();
    sRetorno[1] = txtVlrDescCompra.getVlrString();
    sRetorno[2] = txtVlrAdicCompra.getVlrString();
    sRetorno[3] = cbImpPed.getVlrString();
    sRetorno[4] = cbImpNot.getVlrString();
    sRetorno[5] = txtVlrLiqCompra.getVlrString();
    return sRetorno;
  }
  public void focusLost(FocusEvent fevt) {
    if (fevt.getSource() == txtPercDescCompra) {
      if (txtPercDescCompra.getText().trim().length() < 1) {
        txtVlrDescCompra.setAtivo(true);
      }
      else {
        txtVlrDescCompra.setVlrBigDecimal(
          txtVlrProdCompra.getVlrBigDecimal().multiply(
          txtPercDescCompra.getVlrBigDecimal()).divide(
          new BigDecimal("100"),2,BigDecimal.ROUND_HALF_UP)
        );
        txtVlrDescCompra.setAtivo(false);
      }
    }
    if (fevt.getSource() == txtPercAdicCompra) {
      if (txtPercAdicCompra.getText().trim().length() < 1) {
        txtVlrAdicCompra.setAtivo(true);
      }
      else {
        txtVlrAdicCompra.setVlrBigDecimal(
          txtVlrProdCompra.getVlrBigDecimal().multiply(
          txtPercAdicCompra.getVlrBigDecimal()).divide(
          new BigDecimal("100"),2,BigDecimal.ROUND_HALF_UP)
        );
        txtVlrAdicCompra.setAtivo(false);
      }
    }
  }
  public void focusGained(FocusEvent fevt) { }
}
