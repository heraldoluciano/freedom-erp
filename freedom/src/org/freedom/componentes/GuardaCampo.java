/**
 * @version 17/08/2000 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva/Robson Sanchez <BR>
 *
 * Projeto: Freedom <BR>
 * Pacote: org.freedom.componentes <BR>
 * Classe: @(#)GuardaCampo.java <BR>
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
import java.awt.Color;
import java.awt.Component;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Date;

import javax.swing.BorderFactory;

import org.freedom.funcoes.Funcoes;

public class GuardaCampo extends Component {
  private JTextFieldPad txtCampo = null; 
  private Component comp = null;
  private String sNome = "";
  private String sLabel = "";
  private JTextFieldFK txtDescFK= null;
  private boolean bPK = false;
  private boolean bFK = false;
  private int iTipo = -1;
  private boolean bRequerido = false;
  /*
   *  @deprecated GuardaCampo(Component c, int X, int Y, int Larg, int Alt, String nome, String Label, boolean pk, boolean fk, JTextFieldFK descFK, int tip, boolean req)
   */
  public  GuardaCampo(Component c, int X, int Y, int Larg, int Alt, String nome, String Label, boolean pk, 
  		boolean fk, JTextFieldFK descFK, int tip, boolean req)  {
      
    sNome = nome;          
    sLabel = Label;
    comp = c;
    iTipo = tip;
    bRequerido = req;
    if (comp instanceof JTextFieldPad) {
      txtCampo = (JTextFieldPad) comp;
//      txtCampo.setPKFK(pk,fk);
    }
    bPK = pk;
    bFK = fk;
    txtDescFK = descFK;
    setRequerido(req);
  }
  public GuardaCampo(Component c, String nome, String label, byte key, JTextFieldFK descFK, boolean req) {
 	 setGuardaCampo(c, nome, label, key, descFK, req );
  }

  
  public GuardaCampo(Component c, String nome, String label, byte key, boolean req) {
  	 setGuardaCampo(c, nome, label, key, null, req );
  }

  public void setGuardaCampo(Component c, String nome, String label, byte key, JTextFieldFK descFK, boolean req) {
    sNome = nome;          
    sLabel = label;
    comp = c;
    bRequerido = req;
    bPK = (key == ListaCampos.DB_PK) || (key == ListaCampos.DB_PF);
    bFK = (key == ListaCampos.DB_FK) || (key == ListaCampos.DB_PF);
    if (comp instanceof JTextFieldPad) {
      txtCampo = (JTextFieldPad) comp;
//      txtCampo.setPKFK(bPK,bFK);
        iTipo = txtCampo.getTipo();
    }
	else if  ( (comp instanceof JPasswordFieldPad) ||
			   (comp instanceof JTextAreaPad ) ) {
	    iTipo = JTextFieldPad.TP_STRING;
	}
	else if (comp instanceof JCheckBoxPad) {
		iTipo = ( (JCheckBoxPad) comp ).getTipo();
	}
	else if (comp instanceof JComboBoxPad) {
		iTipo = ( (JComboBoxPad) comp ).getTipo();
	}
	else if (comp instanceof JRadioGroup) {
		iTipo = ( (JRadioGroup) comp ).getTipo();
	}
	if (descFK==null)
       txtDescFK = new JTextFieldFK();
    else
       txtDescFK = descFK;
    setRequerido(req);
  }
  
  public String getLabel() {
    return sLabel;
  }
  
  public JTextFieldPad getDescFK() {
  	JTextFieldPad txtRet = new JTextFieldPad();
  	if (txtDescFK==null) {
  		for(int i=0;i<txtCampo.getTabelaExterna().getComponentCount();i++)
  			if (!((GuardaCampo)txtCampo.getTabelaExterna().getComponent(i)).bPK &&
  				 ((GuardaCampo)txtCampo.getTabelaExterna().getComponent(i)).getCampo() != null) {
  		      txtRet = ((GuardaCampo)txtCampo.getTabelaExterna().getComponent(i)).getCampo();
  			}
  	}
  	else
  		txtRet = txtDescFK;
    return txtRet;
  }
  
  public JTextFieldPad getCampo() {
	if (comp instanceof JTextAreaPad) {
		JTextFieldPad txtTmp = new JTextFieldPad(((JTextAreaPad)comp).iTipo,((JTextAreaPad)comp).iTamanho,((JTextAreaPad)comp).iDecimal);
		txtTmp.setText(((JTextAreaPad)comp).getText());
		return txtTmp;
	}
    return txtCampo;
  }
  
  public Component getComponente() {
    return comp;   
  }
  public void atualizaFK() {
    if ((bFK) & (comp instanceof JTextFieldPad)) {
      ((JTextFieldPad) comp).atualizaFK();
    }
  }
  public String strFormat(String sVal) {
  	String sRet = sVal;
    if (comp instanceof JTextFieldPad) {
    	sRet = Funcoes.setMascara(sVal,((JTextFieldPad)comp).getStrMascara());
    }
    return sRet;
  }
  public String getNomeCampo() {
     return sNome;
  }
  public int getTipo() {
    return iTipo; 
  }
  public String getTituloCampo() {
     return sLabel;
  }
  public int getTamanhoCampo() {
  	 return comp.getSize().width;
  }
  public boolean getSoLeitura() {
    boolean bRetorno = false;
    if (comp instanceof JTextFieldPad) {
      bRetorno = ((JTextFieldPad) comp).getSoLeitura();
    }
    return bRetorno;
  }
  public boolean ehNulo() {
    if (comp instanceof JTextFieldPad) {
      if (((JTextFieldPad) comp).getText().trim().length() == 0) {
        return true;
      }
    }
    else if (comp instanceof JTextAreaPad) {
      if (((JTextAreaPad) comp).getText().trim().length() == 0) 
        return true;
    }
    return false;
  }        
  private void setRequerido( boolean bReq ) {
    bRequerido = bReq;
    if (bRequerido) {
      if (comp instanceof JTextFieldPad) {
        ((JTextFieldPad) comp).setBorder(
          BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(1, 1, 1, 1, Color.red),
            BorderFactory.createEtchedBorder()
          )
        );
        ((JTextFieldPad) comp).setRequerido(bReq);
      }
      else if (comp instanceof JRadioGroup)
        ((JRadioGroup) comp).setBorder(
          BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(1, 1, 1, 1, Color.red),
            BorderFactory.createEtchedBorder()
          )
        );
      else if (comp instanceof JTextAreaPad)
        ((JTextAreaPad) comp).setBorder(
          BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(1, 1, 1, 1, Color.red),
            BorderFactory.createEtchedBorder()
          )
        );
      else if (comp instanceof JCheckBoxPad)
        ((JCheckBoxPad) comp).setBorder(
          BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(1, 1, 1, 1, Color.red),
            BorderFactory.createEtchedBorder()
          )
        );
      else if (comp instanceof JComboBoxPad)
        ((JComboBoxPad) comp).setBorder(
          BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(1, 1, 1, 1, Color.red),
            BorderFactory.createEtchedBorder()
          )
        );
      else if (comp instanceof PainelImagem)
        ((PainelImagem) comp).setBorder(
          BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(1, 1, 1, 1, Color.red),
            BorderFactory.createEtchedBorder()
          )
        );
    }
  }

  public boolean getRequerido() {
    return bRequerido;
  }

  public boolean ehPK() {
    return bPK;
  }

  public boolean ehFK() {
       return bFK;
  }
  public void limpa() {
    byte[] bVals = new byte[1];

    if ((bFK) && (txtCampo != null)) {
      txtCampo.getTabelaExterna().limpaCampos(false);
    }

    if (comp instanceof JTextFieldPad)
      ((JTextFieldPad)comp).setVlrString("");
    else if (comp instanceof JTextAreaPad)
      ((JTextAreaPad)comp).setVlrString("");
    else if (comp instanceof JTextFieldFK)
      ((JTextFieldFK)comp).setVlrString("");
    else if (comp instanceof JCheckBoxPad)
      ((JCheckBoxPad)comp).setSelected(false);
    else if (comp instanceof JComboBoxPad)
      ((JComboBoxPad)comp).limpa();
    else if (comp instanceof JRadioGroup)
      ((JRadioGroup)comp).novo();
	else if (comp instanceof JPasswordFieldPad)
	  ((JPasswordFieldPad)comp).setVlrString("");
    else if (comp instanceof PainelImagem) {
      ((PainelImagem)comp).setVlrBytes(bVals);
      ((PainelImagem)comp).repaint();
    }
//    System.out.println("Campo: "+sNome);

  }
  public String getVlr() {
    String sRetorno = "";
    if (comp instanceof JTextFieldPad)
      sRetorno = ((JTextFieldPad)comp).getText();
    else if (comp instanceof JTextAreaPad)
      sRetorno = ((JTextAreaPad)comp).getText();
    else if (comp instanceof JCheckBoxPad)
      sRetorno = ((JCheckBoxPad)comp).getVlrString();
    else if (comp instanceof JRadioGroup)
      sRetorno =  ((JRadioGroup)comp).getVlrString();
    else if (comp instanceof JComboBoxPad)
      sRetorno =  ((JComboBoxPad)comp).getVlrString();
	else if (comp instanceof JPasswordFieldPad)
	  sRetorno = ((JPasswordFieldPad)comp).getVlrString();
    return sRetorno;
  }
  public String getVlrString() {
    String sRetorno = "";
    if (comp instanceof JTextFieldPad)
      sRetorno = ((JTextFieldPad)comp).getVlrString();
    else if (comp instanceof JTextAreaPad)
      sRetorno = ((JTextAreaPad)comp).getVlrString();
    else if (comp instanceof JCheckBoxPad)
      sRetorno = ((JCheckBoxPad)comp).getVlrString();
    else if (comp instanceof JRadioGroup)
      sRetorno =  ((JRadioGroup)comp).getVlrString();
    else if (comp instanceof JComboBoxPad)
      sRetorno =  ((JComboBoxPad)comp).getVlrString();
	else if (comp instanceof JPasswordFieldPad)
	  sRetorno = ((JPasswordFieldPad)comp).getVlrString();
    return sRetorno;
  }
  public Integer getVlrInteger() {
    Integer iRetorno = new Integer(0);
    if (comp instanceof JTextFieldPad)
      iRetorno = ((JTextFieldPad)comp).getVlrInteger();
    else if (comp instanceof JCheckBoxPad)
      iRetorno = ((JCheckBoxPad)comp).getVlrInteger();
    else if (comp instanceof JComboBoxPad)
      iRetorno = ((JComboBoxPad)comp).getVlrInteger();
    else if (comp instanceof JRadioGroup)
      iRetorno = ((JRadioGroup)comp).getVlrInteger();
    return iRetorno;
  }
  public BigDecimal getVlrBigDecimal() {
    BigDecimal bigRetorno = new BigDecimal(0);
    if (comp instanceof JTextFieldPad)
      bigRetorno = ((JTextFieldPad)comp).getVlrBigDecimal();
    return bigRetorno;
  }
  public Double getVlrDouble() {
    Double dRetorno = new Double(0);
    if (comp instanceof JTextFieldPad)
      dRetorno = ((JTextFieldPad)comp).getVlrDouble();
    return dRetorno;
  }
  public Date getVlrDate() {
    Date dRetorno = new Date();
    if (comp instanceof JTextFieldPad)
      dRetorno = ((JTextFieldPad)comp).getVlrDate();
    return dRetorno;
  }
  public DadosImagem getVlrBytes() {
    DadosImagem diRetorno = null;
    if (comp instanceof PainelImagem)
      diRetorno = ((PainelImagem)comp).getVlrBytes();
    return diRetorno;
  }
  public void setVlrString(String val) {
    if (val == null) 
      val = "";
    if (comp instanceof JTextFieldPad)
      ((JTextFieldPad)comp).setVlrString(val);
    else if (comp instanceof JTextAreaPad)
      ((JTextAreaPad)comp).setVlrString(val);
    else if (comp instanceof JCheckBoxPad)
      ((JCheckBoxPad)comp).setVlrString(val);
    else if (comp instanceof JComboBoxPad)
      ((JComboBoxPad)comp).setVlrString(val);
    else if (comp instanceof JRadioGroup)
      ((JRadioGroup)comp).setVlrString(val);
	else if (comp instanceof JPasswordFieldPad)
	  ((JPasswordFieldPad)comp).setVlrString(val);
  }
  public void setVlrInteger(Integer val) {
    if (comp instanceof JTextFieldPad)
      ((JTextFieldPad)comp).setVlrInteger(val);
    else if (comp instanceof JCheckBoxPad)
      ((JCheckBoxPad)comp).setVlrInteger(val);
    else if (comp instanceof JComboBoxPad)
      ((JComboBoxPad)comp).setVlrInteger(val);
    else if (comp instanceof JRadioGroup)
      ((JRadioGroup)comp).setVlrInteger(val);
  }
  public void setVlrBigDecimal(BigDecimal val) {
    if (val == null) 
      ((JTextFieldPad)comp).setText("");
    else if (comp instanceof JTextFieldPad)
      ((JTextFieldPad)comp).setVlrBigDecimal(val);
  }
  public void setVlrDouble(Double val) {
    if (val == null) 
      ((JTextFieldPad)comp).setText("");
    else if (comp instanceof JTextFieldPad)
      ((JTextFieldPad)comp).setVlrDouble(val);
  }
  public void setVlrBytes(byte[] bVals) {
    if (bVals == null) 
      ((PainelImagem)comp).setVlrBytes(new byte[1]);
    else if (comp instanceof PainelImagem)
      ((PainelImagem)comp).setVlrBytes(bVals);
  }
  public void setVlrBytes(InputStream bVals) {
  	if (comp instanceof PainelImagem)
  		((PainelImagem)comp).setVlrBytes(bVals);
  }
}
