/**
 * @version 12/05/2003 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 * Pacote: org.acao <BR>
 * Classe: @(#)JComboBoxEvent.java <BR>
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

package org.freedom.acao;
import org.freedom.componentes.JComboBoxPad;
public class JComboBoxEvent {
  private JComboBoxPad cBox = null;
  private int Indice = -1;
  public JComboBoxEvent (JComboBoxPad cb, int ind) {
    cBox = cb;
    Indice = ind;
  }
  public JComboBoxPad getComboBoxPad() {
    return cBox;
  }
  public int getIndice() {
    return Indice;
  }
}
