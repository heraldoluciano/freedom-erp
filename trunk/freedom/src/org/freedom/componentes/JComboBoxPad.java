/**
 * @version 04/06/2003 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 * Pacote: org.compo <BR>
 * Classe: @(#)JComboBoxPad.java <BR>
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
 * Comentários da classe.....
 */

package org.freedom.componentes;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Vector;

import javax.swing.JComboBox;

import org.freedom.acao.JComboBoxEvent;
import org.freedom.acao.JComboBoxListener;
public class JComboBoxPad extends JComboBox implements JComboBoxListener, ItemListener {
  public static final int TP_NONE = -1;
  public static final int TP_STRING = 0;
  public static final int TP_INTEGER = 4;
  private Vector valores = new Vector();
  private Vector labels = new Vector();
  private JComboBoxListener cbLis = this;
  private ListaCampos lcCombo = null;
  public int Tipo = -1;
  public JComboBoxPad() {
    this(null,null);
  }
  public JComboBoxPad(Vector label, Vector val) {
    if (val != null && label != null) {
      valores = val;
      labels = label;
      for (int i=0; i<label.size(); i++) {
        addItem(label.elementAt(i));
      }
    }
    addItem("");
    addItemListener(this);
/*    addMouseListener(new MouseAdapter() {
      public void mouseClicked(MouseEvent mevt) {
        fireValorAlterado(getSelectedIndex());
      	if (lcCombo != null) {
      	  lcCombo.edit();
      	}
      }
    }); */
  }
/*  private void setTipo() {
    if (valores.elementAt(0) instanceof Integer)
      Tipo = TP_INTEGER;
    else if (valores.elementAt(0) instanceof String)
      Tipo = TP_STRING;
  } */
  public void setItens(Vector label, Vector val) {
    removeAllItems();
    valores = val;
    labels = label;
    
    for (int i=0; i<label.size(); i++) {
      addItem(label.elementAt(i));
    }
    addItem("");
  }
  public void setAtivo(boolean bVal) {
  	setEnabled(bVal);
  }
  public void limpa() {
    setSelectedIndex(labels.size());
  }
  public int getTipo() {
    return Tipo;
  }
  public String getVlrString() {
  	int iInd = getSelectedIndex();
  	if (valores != null && iInd >= 0 && iInd < valores.size())
      return (String) valores.elementAt(getSelectedIndex());
    return "";
  }
  public Integer getVlrInteger() {
  	try {
      return (Integer) valores.elementAt(getSelectedIndex());
  	}
  	catch(Exception err) {
      return new Integer(0);
  	}
  }
  public void setVlrString(String val) {
    for (int i=0; i<valores.size(); i++) {
      if (valores.elementAt(i).equals(val)) {
        setSelectedIndex(i);
        fireValorAlterado(i);
        break;
      }
    }
  }
  public void setVlrInteger(Integer val) {
    for (int i=0; i<valores.size(); i++) {
      if (valores.elementAt(i).equals(val)) {
        setSelectedIndex(i);
        fireValorAlterado(i);
        break;
      }
    }
  }
  public void setListaCampos(ListaCampos lc) {
    lcCombo = lc;
  }
  public void addComboBoxListener(JComboBoxListener cb) {
    cbLis = cb;
  }
  private void fireValorAlterado(int ind) {
    cbLis.valorAlterado(new JComboBoxEvent(this, ind));    
  }
  public void valorAlterado(JComboBoxEvent cbevt) {
  }
  public void itemStateChanged(ItemEvent itevt) {
  	if (itevt.getStateChange() == ItemEvent.SELECTED)
	  fireValorAlterado(getSelectedIndex());
  }
}
