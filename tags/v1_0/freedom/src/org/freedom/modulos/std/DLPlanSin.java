/**
 * @version 14/07/2003 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)DLPlanSin.java <BR>
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
 * Tela de cadastro de planejamento financeiro (Contas sintéticas).
 */

package org.freedom.modulos.std;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.util.Vector;

import javax.swing.JLabel;

import org.freedom.componentes.JRadioGroup;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.FFDialogo;
public class DLPlanSin extends FFDialogo {
  private JTextFieldPad txtCodPai = new JTextFieldPad();
  private JTextFieldPad txtDescPai = new JTextFieldPad();
  private JTextFieldPad txtCodSin = new JTextFieldPad();
  private JTextFieldPad txtDescSin = new JTextFieldPad(50);
  private JLabel lbCodPai = new JLabel("Código");
  private JLabel lbDescPai = new JLabel("e descrição da origem");
  private JLabel lbCodSin = new JLabel("Códiogo");
  private JLabel lbDescSin = new JLabel("Descrição");
  private Vector vVals = new Vector();
  private Vector vLabs = new Vector();
  private JRadioGroup rgTipo = null; 
  private String sCodPai = "";
  private String sDesc = "";
  public DLPlanSin(Component cOrig, String sCodP, String sDescPai, String sCod, String sD, String sTipo) {
  	super(cOrig);
    setTitulo("Planejamento financeiro (Conta Sintética)");
    setAtribos(310,250);
    sCodPai = sCodP;
    sDesc = sD;
    cancText(txtCodPai);
    cancText(txtDescPai);
    cancText(txtCodSin);
    Funcoes.setBordReq(txtDescSin);    
    txtCodPai.setVlrString(sCodPai);
    txtDescPai.setVlrString(sDescPai);
    txtCodSin.setVlrString(sCod);
    vVals.addElement("B");
    vVals.addElement("C");
    vVals.addElement("D");
    vVals.addElement("R");
    vLabs.addElement("Bancos");
    vLabs.addElement("Caixa");
    vLabs.addElement("Despesas");
    vLabs.addElement("Receitas");
    rgTipo = new JRadioGroup(2,2,vLabs,vVals);
    setTipo(sTipo);    
    adic(lbCodPai,7,0,80,20);
    adic(txtCodPai,7,20,80,20);
    adic(lbDescPai,90,0,200,20);
    adic(txtDescPai,90,20,200,20);
    adic(lbCodSin,7,40,80,20);
    adic(txtCodSin,7,60,80,20);
    adic(lbDescSin,90,40,100,20);
    adic(txtDescSin,90,60,200,20);
    adic(rgTipo,7,90,283,60);
    if (sDesc != null) {
      setTitulo("Edição de Conta Sintética");
      txtDescSin.setVlrString(sDesc);
      txtDescSin.selectAll();
    }
    txtDescSin.requestFocus();
  }
  private void cancText(JTextFieldPad txt) {
    txt.setBackground(Color.lightGray);
    txt.setFont(new Font("Dialog", Font.BOLD, 12));
    txt.setEditable(false);
    txt.setForeground(new Color(118, 89, 170));
  }
  private void setTipo(String sTipo) {
    if ((sDesc == null) & (sCodPai.trim().length() == 1) & (sTipo.compareTo("B") == 0)) {
      rgTipo.setVlrString("B");
      rgTipo.setAtivo(0,true);
      rgTipo.setAtivo(1,true);
      rgTipo.setAtivo(2,false);
      rgTipo.setAtivo(3,false);
    }
    else {
      rgTipo.setVlrString(sTipo);
      rgTipo.setAtivo(0,false);
      rgTipo.setAtivo(1,false);
      rgTipo.setAtivo(2,false);
      rgTipo.setAtivo(3,false);
    }
  }
  public void actionPerformed(ActionEvent evt) {
    if (evt.getSource() == btOK) {
      if (txtDescSin.getText().trim().length() == 0) {
		Funcoes.mensagemInforma(this,"O campo descrição está em branco! ! !");
        txtDescSin.requestFocus();
        return;
      }
    }
    super.actionPerformed(evt);
  }
  public String[] getValores() {
    String[] sRetorno = {txtDescSin.getText(),rgTipo.getVlrString()};
    return sRetorno;
  }
}
