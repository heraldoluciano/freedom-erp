/**
 * @version 14/07/2003 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freeedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)DLPlanAnal.java <BR>
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
 * Tela de cadastro de planejamento financeiro (Contas analíticas).
 */

package org.freedom.modulos.std;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.util.Vector;

import org.freedom.componentes.JLabelPad;

import org.freedom.componentes.JRadioGroup;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.FFDialogo;

public class DLPlanAnal extends FFDialogo {

	private static final long serialVersionUID = 1L;

  private JTextFieldPad txtCodPai = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldPad txtDescPai = new JTextFieldPad(JTextFieldPad.TP_STRING,50,0);
  private JTextFieldPad txtCodAnal = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldPad txtDescAnal = new JTextFieldPad(JTextFieldPad.TP_STRING,50,0);
  private JLabelPad lbCodPai = new JLabelPad("Cód.origem");
  private JLabelPad lbDescPai = new JLabelPad("Descrição da origem");
  private JLabelPad lbCodAnal = new JLabelPad("Código");
  private JLabelPad lbDescAnal = new JLabelPad("Descrição");
  private JLabelPad lbFinPlan = new JLabelPad("Finalidade");
  
  private Vector vValsTipoPlan = new Vector();
  private Vector vLabsTipoPlan = new Vector();
  private Vector vValsFinPlan = new Vector();
  private Vector vLabsFinPlan = new Vector();
  private JRadioGroup rgTipoPlan = null; 
  private JRadioGroup rgFinPlan = null; 
  public DLPlanAnal(Component cOrig,String sCodPai, String sDescPai, String sCod, String sDesc, String sTipo, String sFin) {
  	super(cOrig);
  	setTitulo("Planejamento financeiro (Conta Analítica)");
    setAtribos(430,410);
    cancText(txtCodPai);
    cancText(txtDescPai);
    cancText(txtCodAnal);
    Funcoes.setBordReq(txtDescAnal);    
    txtCodPai.setVlrString(sCodPai);
    txtDescPai.setVlrString(sDescPai);
    txtCodAnal.setVlrString(sCod);
    vValsTipoPlan.addElement("B");
    vValsTipoPlan.addElement("C");
    vValsTipoPlan.addElement("D");
    vValsTipoPlan.addElement("R");
    vLabsTipoPlan.addElement("Bancos");
    vLabsTipoPlan.addElement("Caixa");
    vLabsTipoPlan.addElement("Despesas");
    vLabsTipoPlan.addElement("Receitas");
    rgTipoPlan = new JRadioGroup(2,2,vLabsTipoPlan,vValsTipoPlan);
    rgTipoPlan.setVlrString(sTipo);
    rgTipoPlan.setAtivo(0,false);
    rgTipoPlan.setAtivo(1,false);
    rgTipoPlan.setAtivo(2,false);
    rgTipoPlan.setAtivo(3,false);

    vValsFinPlan.addElement("RV");
    vValsFinPlan.addElement("OR");
    vValsFinPlan.addElement("ER");
    vValsFinPlan.addElement("CF");
    vValsFinPlan.addElement("CV");
    vValsFinPlan.addElement("II");
    vValsFinPlan.addElement("RF");
    vValsFinPlan.addElement("DF");
    vValsFinPlan.addElement("CS");
    vValsFinPlan.addElement("IR");
    vValsFinPlan.addElement("OO");
    vLabsFinPlan.addElement("RV - Receitas sobre vendas");
    vLabsFinPlan.addElement("OR - Outras receitas");
    vLabsFinPlan.addElement("ER - Estorno de receitas");
    vLabsFinPlan.addElement("CF - Custo fixo");
    vLabsFinPlan.addElement("CV - Custo variável");
    vLabsFinPlan.addElement("II - Investimentos");
    vLabsFinPlan.addElement("RF - Receitas financeiras");
    vLabsFinPlan.addElement("DF - Despesas financeiras");
    vLabsFinPlan.addElement("CS - Contribuição social");
    vLabsFinPlan.addElement("IR - Imposto de renda");
    vLabsFinPlan.addElement("OO - Outros");
    rgFinPlan = new JRadioGroup(6,2,vLabsFinPlan,vValsFinPlan);
    if (sFin.trim().equals(""))
      rgFinPlan.setVlrString(sFin);
    adic(lbCodPai,7,0,80,20);
    adic(txtCodPai,7,20,80,20);
    adic(lbDescPai,90,0,300,20);
    adic(txtDescPai,90,20,300,20);
    adic(lbCodAnal,7,40,110,20);
    adic(txtCodAnal,7,60,110,20);
    adic(lbDescAnal,120,40,270,20);
    adic(txtDescAnal,120,60,270,20);
    adic(rgTipoPlan,7,90,383,60);
    adic(lbFinPlan,7,155,270,20);
    adic(rgFinPlan,7,175,383,130);
    if (sDesc != null) {
      setTitulo("Edição de Conta Analítica");
      txtDescAnal.setVlrString(sDesc);
      txtDescAnal.selectAll();
    }
    txtDescAnal.requestFocus();
  }
  private void cancText(JTextFieldPad txt) {
    txt.setBackground(Color.lightGray);
    txt.setFont(new Font("Dialog", Font.BOLD, 12));
    txt.setEditable(false);
    txt.setForeground(new Color(118, 89, 170));
  }
  public void actionPerformed(ActionEvent evt) {
    if (evt.getSource() == btOK) {
      if (txtDescAnal.getText().trim().length() == 0) {
		Funcoes.mensagemErro(this,"O campo descrição está em branco!");
        txtDescAnal.requestFocus();
        return;
      }
    }
    super.actionPerformed(evt);
  }
  public String[] getValores() {
  	String[] sRet = new String[2];  
	sRet[0]	= txtDescAnal.getVlrString();
	sRet[1] = rgFinPlan.getVlrString();
    return sRet;
  }
}