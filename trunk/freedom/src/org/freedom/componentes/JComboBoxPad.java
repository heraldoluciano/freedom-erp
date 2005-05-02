/**
 * @version 04/06/2003 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 * Pacote: org.freedom.componentes <BR>
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
 * Campo do tipo combobox.
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
  private JComboBoxListener cbLis = this;
  private ListaCampos lcCombo = null;
  private boolean criando = true;
  private int tipo = -1;
  private int tam = 8;
  private int dec = 0;
  private boolean bZeroNull = false;
  /*public JComboBoxPad() {
    this(null,null);
    criando = false;
  }*/
  /*public JComboBoxPad(Vector label, Vector val) {
    if (val != null && label != null) {
      valores = val;
      labels = label;
      for (int i=0; i<label.size(); i++) {
        addItem(label.elementAt(i));
      }
    }
    addItem("");
    addItemListener(this);
    criando = false;
  }*/
  public void setZeroNulo() {
  	bZeroNull = true;
  }
  public JComboBoxPad(Vector label, Vector val, int tipo, int tam, int dec) {
    criando = true;
    if (val != null && label != null) {
      valores = val;
      for (int i=0; i<label.size(); i++) {
        addItem(label.elementAt(i));
      }
    }
    addItem("");
    addItemListener(this);
    this.tipo = tipo;
    this.tam = tam;
    this.dec = dec;
    criando = false;
  }
  public void setItens(Vector label, Vector val) {
  	criando = true;
    removeAllItems();
    valores = val;
    
    for (int i=0; i<label.size(); i++) {
      addItem(label.elementAt(i));
    }
    //addItem("");
    criando = false;
  }
  public void setAtivo(boolean bVal) {
  	setEnabled(bVal);
  }
  public void limpa() {
    setSelectedIndex(0);
  }
  public int getTipo() {
    return tipo;
  }
  public int getTam() {
  	return tam;
  }
  public int getDec() {
  	return dec;
  }
  public String getVlrString() {
  	int iInd = getSelectedIndex();
  	if (valores != null && iInd >= 0 && iInd < valores.size())
  		return (String) valores.elementAt(getSelectedIndex());
    return "";
  }
  public String getText() {
  	String retorno = "";
  	int iInd = getSelectedIndex();
  	if (valores != null && iInd >= 0 && iInd < valores.size())
      retorno = valores.elementAt(getSelectedIndex()).toString();
    return retorno;
  }
  public Integer getVlrInteger() {
    if (((Integer) valores.elementAt(getSelectedIndex())==new Integer(0)) && (bZeroNull))
    	return null;
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
  	 if ( (!criando) && (lcCombo!=null) ) {
  	 	if (lcCombo.getStatus()==ListaCampos.LCS_SELECT)
  	 		lcCombo.edit();
  	 }
  }
  public void itemStateChanged(ItemEvent itevt) {
  	if (itevt.getStateChange() == ItemEvent.SELECTED)
	  fireValorAlterado(getSelectedIndex());
  }
}
