/**
 * @version 05/06/2000 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 * Pacote: org.freedom.telas <BR>
 * Classe: @(#)FAndamento.java <BR>
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

package org.freedom.telas;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;

import org.freedom.componentes.Painel;

public class FAndamento extends JFrame {
  private Painel pin = new Painel(310,150);
  private JProgressBar pb = new JProgressBar();
  public FAndamento(String sLabel,int iMin, int iMax) {
    setBounds(100,100,310,150);
    pb.setStringPainted(true);
    pb.setMaximum(iMax);
    pb.setMinimum(iMin);
    setTitle("Andamento");
    getContentPane().setLayout(new GridLayout(1,1));
    pin.adic(new JLabel(sLabel),7,20,200,20);
    pin.adic(pb,7,60,280,20);
    getContentPane().add(pin);
  }
  public void atualiza(int iVal) {
    pb.setValue(iVal);
    pb.updateUI();
  }
}
