/**
 * @version 14/07/2003 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)DLPlanPrim.java <BR>
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
 * Tela de cadastro de planejamento financeiro (Nível 1).
 */

package org.freedom.modulos.std;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.util.Vector;

import javax.swing.JLabel;
import org.freedom.componentes.JTabbedPanePad;

import org.freedom.componentes.JRadioGroup;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.JPanelPad;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.FFDialogo;

public class DLPlanPrim extends FFDialogo {
  private JTextFieldPad txtCodCont = new JTextFieldPad(JTextFieldPad.TP_STRING,4,0);
  private JTextFieldPad txtDescCont = new JTextFieldPad(JTextFieldPad.TP_STRING,50,0);
  private JPanelPad pinCont = new JPanelPad(360,220);
  private Vector vVals = new Vector();
  private Vector vLabs = new Vector();
  private JRadioGroup rgTipo = null;
  private JTabbedPanePad ptb = new JTabbedPanePad();
  private JLabel lbCod = new JLabel("Código");
  private JLabel lbDesc = new JLabel("Descrição");
  public DLPlanPrim(Component cOrig,String sCod, String sDesc, String sTipo) {
  	super(cOrig);
    setTitulo("Planejamento financeiro (Nível 1)");
    setAtribos(360,220);
    Funcoes.setBordReq(txtDescCont);    
    txtCodCont.setBackground(Color.lightGray);
    txtCodCont.setFont(new Font("Dialog", Font.BOLD, 12));
    txtCodCont.setEditable(false);
    txtCodCont.setForeground(new Color(118, 89, 170));
    txtCodCont.setVlrString(sCod);
    vVals.addElement("B");
    vVals.addElement("R");
    vVals.addElement("D");
    vLabs.addElement("Caixa e Bancos");
    vLabs.addElement("Receita");
    vLabs.addElement("Despesa");
    rgTipo = new JRadioGroup(1,3,vLabs,vVals);
    rgTipo.setVlrString("D");
    pinCont.adic(lbCod,7,0,80,20);
    pinCont.adic(txtCodCont,7,20,100,20);
    pinCont.adic(lbDesc,110,0,150,20);
    pinCont.adic(txtDescCont,110,20,220,20);
    pinCont.adic(rgTipo,7,50,323,25);
    ptb.add("1º Nivel",pinCont);
    c.add(ptb, BorderLayout.CENTER);
    if (sDesc != null) {
      setTitulo("Edição de Conta de 1º Nivel");
      rgTipo.setVlrString(sTipo);
      txtDescCont.setVlrString(sDesc);
      txtDescCont.selectAll();
      rgTipo.setVlrString(sTipo);
      rgTipo.setAtivo(0,false);
      rgTipo.setAtivo(1,false);
      rgTipo.setAtivo(2,false);
    }
    txtDescCont.requestFocus();
  }
  public void actionPerformed(ActionEvent evt) {
    if (evt.getSource() == btOK) {
      if (txtDescCont.getText().trim().length() == 0) {
		Funcoes.mensagemInforma(this,"O campo descrição está em branco! ! !");
        txtDescCont.requestFocus();
        return;
      }
    }
    super.actionPerformed(evt);
  }
  public String[] getValores() {
    String[] sRetorno = {txtDescCont.getText(),rgTipo.getVlrString()};
    return sRetorno;
  }
}

