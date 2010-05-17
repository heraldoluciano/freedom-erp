/**
 * @version 24/02/2004 <BR>
 * @author Setpoint Informática Ltda./Robson Sanchez <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)DLRSimilar.java <BR>
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
public class DLRSimilar extends FFDialogo {

	private static final long serialVersionUID = 1L;

  private JRadioGroup rgOrdem = null;
  private JLabelPad lbOrdem = new JLabelPad("Ordenar por:");
  private Vector vLabs = new Vector();
  private Vector vVals = new Vector();
  public DLRSimilar() {
    setTitulo("Ordem do Relatório");
    setAtribos(300,150);
    vLabs.addElement("Cód.prod.");
    vLabs.addElement("Ref.prod.");
    vLabs.addElement("Cód.sim.");
    vLabs.addElement("Desc.prod.");
    vVals.addElement("C");
    vVals.addElement("R");
    vVals.addElement("S");
    vVals.addElement("D");
    rgOrdem = new JRadioGroup(2,2,vLabs,vVals);
    rgOrdem.setVlrString("D");
    adic(lbOrdem,7,0,80,15);
    adic(rgOrdem,7,20,280,60);
  }
  public String getValor() {
    String sRetorno = "";
    if (rgOrdem.getVlrString().equals("C"))
      sRetorno = "S.CODPROD";
    else if (rgOrdem.getVlrString().equals("R"))
    	sRetorno = "P.REFPROD";
    else if (rgOrdem.getVlrString().equals("S"))
    	sRetorno = "S.REFPRODSIM";
    else if (rgOrdem.getVlrString().equals("D"))
    	sRetorno = "P.DESCPROD";
    return sRetorno;
  }
}
