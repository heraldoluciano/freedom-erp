/**
 * @version 14/11/2000 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 * Pacote: org.freedom.telas <BR>
 * Classe: @(#)FZoom.java <BR>
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
 * Comentários para a classe...
 */

package org.freedom.telas;
import java.awt.Container;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JDialog;

import org.freedom.bmps.Icone;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.JPanelPad;
import org.freedom.componentes.PainelImagem;
public class FZoom extends JDialog implements KeyListener, ActionListener {
  private Image imZoom = null;
  private JPanel pnCab = new JPanel(new BorderLayout());  
  private JPanel pnCli = new JPanel(new GridLayout(1,1));  
  private JPanelPad pinCab = new JPanelPad(385,40);
  private JButton btZoom100 = new JButton(Icone.novo("btZoom100.gif"));
  private JButton btZoomIn = new JButton(Icone.novo("btZoomIn.gif"));
  private JButton btZoomPag = new JButton(Icone.novo("btZoomPag.gif"));
  private JTextFieldPad txtZoom = new JTextFieldPad();
  private JButton btMais = new JButton(Icone.novo("btZoomMais.gif"));
  private JButton btMenos = new JButton(Icone.novo("btZoomMenos.gif"));
  private JButton btSair = new JButton("Sair",Icone.novo("btSair.gif"));
  private PainelImagem pimImagem = null;
  private JScrollPane spnCli = null;
  public FZoom(Image im, int iTam) {
    imZoom = im;
    setTitle("Zoom");
    setSize( 400, 400);
    setModal(true);
    setLocationRelativeTo(this);  
    Container c = getContentPane();
    c.setLayout(new BorderLayout());
    c.setBackground(Color.white);
    c.add(pnCab,BorderLayout.NORTH);
    pnCab.setPreferredSize(new Dimension(200,40));
    btSair.setPreferredSize(new Dimension(80,30));
    pnCab.add(pinCab,BorderLayout.WEST);
    pinCab.adic(btZoom100,7,5,30,30);
    pinCab.adic(btZoomIn,40,5,30,30);
    pinCab.adic(btZoomPag,73,5,30,30);
    pinCab.adic(txtZoom,106,5,50,30);
    pinCab.adic(btMais,159,5,30,30);
    pinCab.adic(btMenos,192,5,30,30);
    pinCab.adic(btSair,285,5,100,30);
    c.add(pnCli,BorderLayout.CENTER);
    pimImagem = new PainelImagem(pnCli,iTam);
    pimImagem.setVlrBytes(imZoom);
    pimImagem.setZoom(100);
    spnCli = new JScrollPane(pimImagem);
    pnCli.add(spnCli);

    txtZoom.setTipo(JTextFieldPad.TP_INTEGER,3,0);
    txtZoom.setEnterSai(false);
    txtZoom.setVlrString(""+pimImagem.getZoom());

    btZoom100.addActionListener(this);
    btZoomIn.addActionListener(this);
    btZoomPag.addActionListener(this);
    btMais.addActionListener(this);
    btMenos.addActionListener(this);
    btSair.addActionListener(this);
    txtZoom.addKeyListener(this);

  }
  public void actionPerformed(ActionEvent evt) {
    if (evt.getSource() == btSair)
      dispose();
    else if (evt.getSource() == btZoom100) {
      pimImagem.setZoom(100);
      pimImagem.repaint();
    }
    else if (evt.getSource() == btZoomIn) {
      int iEnc = pimImagem.getPercEncaixa();
      pimImagem.setZoom(iEnc - (iEnc / 8));
      pimImagem.repaint();
    }
    else if (evt.getSource() == btZoomPag) {
      pimImagem.setZoom(pimImagem.getPercEncaixa());
      pimImagem.repaint();
    }
    else if (evt.getSource() == btMais) {
      if (pimImagem.getZoom() < 990) {
        pimImagem.setZoom(pimImagem.getZoom()+10);
        pimImagem.repaint();
      }
    }
    else if (evt.getSource() == btMenos) {
      if (pimImagem.getZoom() > 10) {
        pimImagem.setZoom(pimImagem.getZoom()-10);
        pimImagem.repaint();
      }
    }
    txtZoom.setVlrString(""+pimImagem.getZoom());
  }
  public void keyPressed(KeyEvent kevt) {
    if (kevt.getKeyCode() == KeyEvent.VK_ENTER) {
      if (txtZoom.getText().trim().length() == 0) {
        txtZoom.setVlrString(""+pimImagem.getZoom());
      }
      else {
        pimImagem.setZoom(txtZoom.getVlrInteger().intValue());
        pimImagem.repaint();
      }
    }
  }
  public void keyTyped(KeyEvent kevt) { }
  public void keyReleased(KeyEvent kevt) { }
}
       

