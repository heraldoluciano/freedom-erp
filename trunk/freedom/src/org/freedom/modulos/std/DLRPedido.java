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
import javax.swing.JLabel;

import org.freedom.componentes.JRadioGroup;
import org.freedom.telas.FDialogo;

import java.util.Vector;
public class DLRPedido extends FDialogo {
  private JRadioGroup rgOrdem = null;
  private JLabel lbOrdem = new JLabel("Ordenar por:");
  private Vector vLabs = new Vector();
  private Vector vVals = new Vector();
  public DLRPedido(String OrdNota) {
    setTitulo("Ordem do Relatório");
    setAtribos(350,120);
    vLabs.addElement("Código");
    vLabs.addElement("Descrição");
    vLabs.addElement("Marca");
    vVals.addElement("C");
    vVals.addElement("D");
    vVals.addElement("M");
    rgOrdem = new JRadioGroup(1,2,vLabs,vVals);
    rgOrdem.setVlrString(OrdNota);
    adic(lbOrdem,7,0,80,15);
    adic(rgOrdem,7,20,330,30);
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
