/**
 * @version 14/07/2003 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)DLRPedido.java <BR>
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
import org.freedom.componentes.JLabelPad;

import org.freedom.componentes.JRadioGroup;
import org.freedom.telas.FFDialogo;

import java.util.Vector;

import javax.swing.JCheckBox;
public class DLRPedido extends FFDialogo {

  private static final long serialVersionUID = 1L;
  private JRadioGroup rgOrdem = null;
  private JLabelPad lbOrdem = new JLabelPad("Ordenar por:");
  private JCheckBox cbxResumido = new JCheckBox("Relatório Resumido"); 
  private Vector vLabs = new Vector();
  private Vector vVals = new Vector();
  public DLRPedido(String OrdNota, boolean RelResumido) {
    setTitulo("Ordem do Relatório");
    setAtribos(350,160);
    vLabs.addElement("Código");
    vLabs.addElement("Descrição");
    vLabs.addElement("Marca");
    vVals.addElement("C");
    vVals.addElement("D");
    vVals.addElement("M");
    rgOrdem = new JRadioGroup(1,2,vLabs,vVals);
    rgOrdem.setVlrString(OrdNota);
    if (RelResumido)
    	adic(cbxResumido, 7, 55, 180, 15);
    adic(lbOrdem,7,0,80,15);
    adic(rgOrdem,7,20,330,30);
  }
  
  public boolean ehResumido() {
	  return cbxResumido.isSelected();
  }
  
  public String getValor() {
    String sRetorno = "";
    if (rgOrdem.getVlrString().compareTo("C") == 0 )
      sRetorno = "REFPROD";
    else if (rgOrdem.getVlrString().compareTo("D") == 0 )
      sRetorno = "DESCPROD";
    else if (rgOrdem.getVlrString().compareTo("M") == 0 )
      sRetorno = "CODMARCA";
    return sRetorno;
  }
}
