/**
 * @version 01/02/2001 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 * Pacote: org.freedom.componentes <BR>
 * Classe: @(#)Tabela.java <BR>
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
 * Objeto para guardar as informações necessárias para a criação e utilização de modelos de etiquetas.
 */

package org.freedom.componentes;

import java.util.Vector;

public abstract class ObjetoEtiqueta {
  private Vector vLabel = new Vector();
  private Vector vValor = new Vector();
  private Vector vNomeCampo = new Vector();
  private Vector vTam = new Vector();
  private Vector vMascara = new Vector();

  public ObjetoEtiqueta() { 

  }
  
  public void adicOpcao(String sLabel,String sValor,String sNomeCampo,Integer iTam,String sMascara){
      vLabel.addElement(sLabel);
      vValor.addElement(sValor);
      vNomeCampo.addElement(sNomeCampo);
      vTam.addElement(iTam);
      vMascara.addElement(sMascara);
  }

/**
 * @return Returns the vLabel.
 */
public Vector getLabel() {
    return vLabel;
}
/**
 * @return Returns the vNomeCampo.
 */
public Vector getNomeCampo() {
    return vNomeCampo;
}
/**
 * @return Returns the vTam.
 */
public Vector getTam() {
    return vTam;
}
/**
 * @return Returns the vValor.
 */
public Vector getValor() {
    return vValor;
}
/**
 * @return Returns the vMascara.
 */
public Vector getMascara() {
    return vMascara;
}
}