/**
 * @version 14/07/2003 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)FCompICMS.java <BR>
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
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import org.freedom.componentes.Painel;
import org.freedom.telas.FFilho;
import org.freedom.bmps.Icone;
public class FCompICMS extends FFilho implements ActionListener {
  private Painel pinCab = new Painel(400,100);
  private Painel pinRod = new Painel(400,120);
  private JPanel pnRod = new JPanel(new BorderLayout());
  private JButton btSair = new JButton(Icone.novo("btSair.gif"));
  public FCompICMS() {
    setTitulo("Compara ICMS");
    setAtribos(50,50,400,400);
    
    Container c = getContentPane();
    c.setLayout(new BorderLayout());
    c.add(pinCab,BorderLayout.NORTH);
    
    btSair.setPreferredSize(new Dimension(100,30));
    pnRod.setPreferredSize(new Dimension(400,30));

    pnRod.add(pinRod,BorderLayout.NORTH);    
    pnRod.add(btSair,BorderLayout.EAST);
    c.add(pnRod,BorderLayout.SOUTH);
    
  }
  public void actionPerformed(ActionEvent evt) {
  }
}



