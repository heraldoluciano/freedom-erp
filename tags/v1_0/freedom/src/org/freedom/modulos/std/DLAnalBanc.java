/**
 * @version 14/07/2003 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)DLAnalBanc.java <BR>
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
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.sql.Connection;
import java.util.Date;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JRadioGroup;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.Painel;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.FFDialogo;
public class DLAnalBanc extends FFDialogo implements FocusListener{
  private JTextFieldPad txtCodPai = new JTextFieldPad();
  private JTextFieldPad txtDescPai = new JTextFieldPad();
  private JTextFieldPad txtCodAnal = new JTextFieldPad();
  private JTextFieldPad txtDescAnal = new JTextFieldPad(50);
  private JTextFieldPad txtAgCont = new JTextFieldPad();
  private JTextFieldPad txtNumCont = new JTextFieldPad();
  private JTextFieldPad txtDescCont = new JTextFieldPad();
  private JTextFieldPad txtCodBanc = new JTextFieldPad(JTextFieldPad.TP_STRING,3,0);
  private JTextFieldFK txtDescBanc = new JTextFieldFK(JTextFieldPad.TP_STRING,40,0);
  private JTextFieldPad txtDataCont = new JTextFieldPad();
  private JTextFieldPad txtCodMoeda = new JTextFieldPad();
  private JTextFieldFK txtDescMoeda = new JTextFieldFK();
  private JTabbedPane tbp = new JTabbedPane();
  private Painel pinGeral = new Painel(370,240);
  private Painel pinDet = new Painel(370,240);
  private JLabel lbCodPai = new JLabel("Código");
  private JLabel lbDescPai = new JLabel("e descrição da origem");
  private JLabel lbCodAnal = new JLabel("Códiogo");
  private JLabel lbDescAnal = new JLabel("Descrição");
  private JLabel lbAgCont = new JLabel("Agência");
  private JLabel lbNumCont = new JLabel("Número");
  private JLabel lbDescCont = new JLabel("Descrição");
  private JLabel lbCodBanc = new JLabel("Código");
  private JLabel lbDescBanc = new JLabel("e nome do banco");
  private JLabel lbDataCont = new JLabel("Data Inicial");
  private JLabel lbCodMoeda = new JLabel("Código");
  private JLabel lbDescMoeda = new JLabel("e descrição da moeda");
  private Vector vVals = new Vector();
  private Vector vLabs = new Vector();
  private JRadioGroup rgTipo = null; 
  private Vector vValsCont = new Vector();
  private Vector vLabsCont = new Vector();
  private JRadioGroup rgTipoCont = null; 
  private ListaCampos lcBanc = new ListaCampos(this,"BO");
  private ListaCampos lcMoeda = new ListaCampos(this,"MA");
  public DLAnalBanc(Component cOrig, String sCodPai, String sDescPai, String sCod, String sDesc, String sTipo,String[] ContVals) {
  	super(cOrig);
    setTitulo("Nova Conta Analítica");
    setAtribos(380,305);
//Monta a tab Geral:
    cancText(txtCodPai); //Por ser análitica são desabilitados estes campos:
    cancText(txtDescPai); 
    cancText(txtCodAnal); 
    txtCodPai.setVlrString(sCodPai); //Eles Receber valor padrão:
    txtDescPai.setVlrString(sDescPai); 
    txtCodAnal.setVlrString(sCod); 
    vVals.addElement("B");  //Aqui é montado o JRadioGroup do tipo de conta:
    vVals.addElement("C");
    vVals.addElement("D");
    vVals.addElement("R");
    vLabs.addElement("Bancos");
    vLabs.addElement("Caixa");
    vLabs.addElement("Despesas");
    vLabs.addElement("Receitas");
    rgTipo = new JRadioGroup(2,2,vLabs,vVals);
    rgTipo.setVlrString(sTipo);
    rgTipo.setAtivo(0,false);
    rgTipo.setAtivo(1,false);
    rgTipo.setAtivo(2,false);
    rgTipo.setAtivo(3,false);
    lcMoeda.add(new GuardaCampo( txtCodMoeda, 7, 100, 80, 20, "CodMoeda", "Código", true, false, null, JTextFieldPad.TP_STRING,true),"txtCodMoeda");
    lcMoeda.add(new GuardaCampo( txtDescMoeda, 90, 100, 207, 20, "SingMoeda", "Descrição", false, false, null, JTextFieldPad.TP_STRING,false),"txtDescMoeda");
    lcMoeda.montaSql(false, "MOEDA", "FN");    
    lcMoeda.setReadOnly(true);
    txtCodMoeda.setTabelaExterna(lcMoeda); //Tabela da Foreign Key
    txtCodMoeda.setFK(true);
    txtCodMoeda.setNomeCampo("CodMoeda");
    vValsCont.addElement("B"); //Aqui é montado o JRadioGroup do tipo de entrada:
    vValsCont.addElement("C");
    vLabsCont.addElement("Bancos");
    vLabsCont.addElement("Caixa");
    rgTipoCont = new JRadioGroup(1,2,vLabsCont,vValsCont);
    Funcoes.setBordReq(txtDescAnal);
    Funcoes.setBordReq(txtNumCont);    
    Funcoes.setBordReq(txtDescCont);    
    Funcoes.setBordReq(txtDataCont);    
    Funcoes.setBordReq(txtCodMoeda);    
    pinGeral.adic(lbCodPai,7,0,80,20);//São adicionados os componentes:
    pinGeral.adic(txtCodPai,7,20,80,20);
    pinGeral.adic(lbDescPai,90,0,200,20);
    pinGeral.adic(txtDescPai,90,20,240,20);
    pinGeral.adic(lbCodAnal,7,40,100,20);
    pinGeral.adic(txtCodAnal,7,60,110,20);
    pinGeral.adic(lbDescAnal,120,40,110,20);
    pinGeral.adic(txtDescAnal,120,60,210,20);
    pinGeral.adic(rgTipo,7,90,323,60);
    pinDet.adic(lbAgCont,7,0,80,20);
    pinDet.adic(txtAgCont,7,20,80,20);
    pinDet.adic(lbNumCont,90,0,240,20);
    pinDet.adic(txtNumCont,90,20,240,20);
    pinDet.adic(lbDescCont,7,40,323,20);
    pinDet.adic(txtDescCont,7,60,323,20);
    pinDet.adic(lbCodBanc,7,80,80,20);
    pinDet.adic(txtCodBanc,7,100,80,20);
    pinDet.adic(lbDescBanc,90,80,240,20);
    pinDet.adic(txtDescBanc,90,100,240,20);
    pinDet.adic(lbDataCont,7,120,90,20);
    pinDet.adic(txtDataCont,7,140,90,20);
    pinDet.adic(rgTipoCont,100,130,223,30);
    pinDet.adic(lbCodMoeda,7,160,80,20);
    pinDet.adic(txtCodMoeda,7,180,80,20);
    pinDet.adic(lbDescMoeda,90,160,240,20);
    pinDet.adic(txtDescMoeda,90,180,240,20);
// Monta a tab Detalhe:   
	lcBanc.add(new GuardaCampo( txtCodBanc, 7, 100, 80, 20, "CodBanco", "Código", true, false, null, JTextFieldPad.TP_STRING,false),"txtCodBanco");
	lcBanc.add(new GuardaCampo( txtDescBanc, 90, 100, 207, 20, "NomeBanco", "Descrição", false, false, null, JTextFieldPad.TP_STRING,false),"txtDescBanco");
	lcBanc.montaSql(false, "BANCO", "FN");    
	lcBanc.setReadOnly(true);
	txtCodBanc.setTabelaExterna(lcBanc);
	txtCodBanc.setFK(true);
	txtCodBanc.setNomeCampo("CodBanco");
	//Se for Novo:
    if (sTipo.compareTo("B") == 0) {//Para cada entrada muda-se os Valores e Campos abilitados:
      txtAgCont.setTipo(JTextFieldPad.TP_STRING,6,0);
      txtNumCont.setTipo(JTextFieldPad.TP_STRING,10,0);
      txtDescCont.setTipo(JTextFieldPad.TP_STRING,50,0);
      txtDataCont.setTipo(JTextFieldPad.TP_DATE,10,0);
      txtCodMoeda.setTipo(JTextFieldPad.TP_STRING,4,0);
      txtDescMoeda.setTipo(JTextFieldPad.TP_STRING,40,0);
      rgTipoCont.setVlrString(sTipo);
      rgTipoCont.setAtivo(0,false);
      rgTipoCont.setAtivo(1,false);
      
    }
    if (sTipo.compareTo("C") == 0) {
      cancText(txtAgCont);
      txtNumCont.setTipo(JTextFieldPad.TP_STRING,10,0);
      txtDescCont.setTipo(JTextFieldPad.TP_STRING,40,0);
      txtDataCont.setTipo(JTextFieldPad.TP_DATE,10,0);
      txtCodMoeda.setTipo(JTextFieldPad.TP_STRING,4,0);
      txtCodBanc.setEnabled(false);
      rgTipoCont.setVlrString(sTipo);
      rgTipoCont.setAtivo(0,false);
      rgTipoCont.setAtivo(1,false);
    }
    //Se for Editar:
    if (sDesc != null) {
      setTitulo("Edição de Conta Analítica");
      txtDescAnal.setVlrString(sDesc);
      txtDescAnal.selectAll();
      txtDescAnal.requestFocus();
      txtAgCont.setVlrString(ContVals[0]);
      txtNumCont.setVlrString(ContVals[1]);
      txtDescCont.setVlrString(ContVals[2]);
      txtCodBanc.setVlrString(ContVals[3]);
      txtDataCont.setVlrString(ContVals[4]);
      txtCodMoeda.setVlrString(ContVals[5]);
    }
    txtDescAnal.addFocusListener(this);
    txtDescAnal.requestFocus();
    tbp.add("Geral",pinGeral);
    tbp.add("Detalhe",pinDet);
    c.add(tbp,BorderLayout.CENTER);
  }
  public void focusGained(FocusEvent fevt) { }
  public void focusLost(FocusEvent fevt) {//Copia a descrição o planejamento para a descrição da conta:
    if (txtDescCont.getText().length() == 0)
      txtDescCont.setVlrString(txtDescAnal.getText());
  }
  public void setConexao(Connection cn) {//Seta as devidas conexões nos listacampos de Foreign Key
    lcBanc.setConexao(cn);
    lcMoeda.setConexao(cn);
  }
  private void cancText(JTextField txt) {//Desabilita TextFields
    txt.setBackground(Color.lightGray);
    txt.setFont(new Font("Dialog", Font.BOLD, 12));
    txt.setEditable(false);
    txt.setForeground(new Color(118, 89, 170));
  }
  public void actionPerformed(ActionEvent evt) {
    if (evt.getSource() == btOK) { //Valida os valores:
      if (txtDescAnal.getText().trim().length() == 0) {
        Funcoes.mensagemInforma(this,"O campo descrição está em branco! ! !");
        txtDescAnal.requestFocus();
        return;
      }
      else if (txtNumCont.getText().trim().length() == 0) {
        Funcoes.mensagemInforma(this,"O campo número da conta está em branco! ! !");
        txtNumCont.requestFocus();
        return;
      }
      else if (txtDescCont.getText().trim().length() == 0) {
        Funcoes.mensagemInforma(this,"O campo descrição da conta está em branco! ! !");
        txtDescCont.requestFocus();
        return;
      }
      else if (txtDataCont.getText().trim().length() == 0) {
        Funcoes.mensagemInforma(this,"O campo data da conta está em branco! ! !");
        txtDataCont.requestFocus();
        return;
      }
      else if (txtCodMoeda.getText().trim().length() == 0) {
        Funcoes.mensagemInforma(this,"O campo código da moeda está em branco! ! !");
        txtCodMoeda.requestFocus();
        return;
      }
    }
    super.actionPerformed(evt);
  }
  public String[] getValores() { //Devolve os valores a Tela de planejamento:
    String[] sVals = new String[7];
    sVals[0] = txtDescAnal.getText();
    sVals[1] = txtAgCont.getText();
    sVals[2] = txtNumCont.getText();
    sVals[3] = txtDescCont.getText();
    sVals[4] = txtCodBanc.getText();
    sVals[5] = Funcoes.dateToStrDate(txtDataCont.getVlrDate());
    sVals[6] = txtCodMoeda.getText();
    return sVals;
  }
  public Date getData() {
    return txtDataCont.getVlrDate();
  }
}

