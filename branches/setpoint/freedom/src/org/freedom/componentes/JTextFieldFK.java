/**
 * @version 23/01/2003 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 * Pacote: org.compo <BR>
 * Classe: @(#)JTextFieldFK.java <BR>
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
import java.awt.Font;

import org.freedom.componentes.JTextFieldPad;
public class JTextFieldFK extends JTextFieldPad {
  public JTextFieldFK () {
    setBackground(Color.lightGray);
    setFont(new Font("Dialog", Font.BOLD, 12));
    setEditable(false);
    setForeground(new Color(111, 106, 177));  //RGB do Java R:159,G:152,B:207
  }
  public JTextFieldFK (int iTipo, int iTam, int iDec) {
  	setTipo(iTipo,iTam,iDec);
    setBackground(Color.lightGray);
    setFont(new Font("Dialog", Font.BOLD, 12));
    setEditable(false);
    setForeground(new Color(111, 106, 177));  //RGB do Java R:159,G:152,B:207
  }
  public boolean isFocusable() {
    return (false);
  }
}
