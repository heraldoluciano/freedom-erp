/**
 * @version 25/03/2004 <BR>
 * @author Setpoint Informática Ltda./Anderson Sanchez <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.pcp <BR>
 * Classe: @(#)DLRRecursos.java <BR>
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
 * Tela de dialogo para relação de recursos de produção...
 */

package org.freedom.modulos.pcp;
import java.awt.Component;
import java.util.Vector;

import org.freedom.componentes.JLabelPad;

import org.freedom.componentes.JRadioGroup;
import org.freedom.telas.FFDialogo;

public class DLRRecursos extends FFDialogo {
  private JRadioGroup rgOrdem = null;
  private JLabelPad lbOrdem = new JLabelPad("Ordenar por:");
  private Vector vLabs = new Vector();
  private Vector vVals = new Vector();
  public DLRRecursos(Component cOrig) {
  	super(cOrig);
    setTitulo("Ordem do Relatório");
    setAtribos(300,140);
    vLabs.addElement("Código");
    vLabs.addElement("Nome");
    vVals.addElement("C");
    vVals.addElement("N");
    rgOrdem = new JRadioGroup(1,2,vLabs,vVals);
    rgOrdem.setVlrString("N");
    adic(lbOrdem,7,0,80,15);
    adic(rgOrdem,7,20,280,30);
  }
  public String getValor() {
    String sRetorno = "";
    if (rgOrdem.getVlrString().compareTo("C") == 0 )
      sRetorno = "CODRECP";
    else if (rgOrdem.getVlrString().compareTo("N") == 0 )
      sRetorno = "DESCRECP";
    return sRetorno;
  }
}
