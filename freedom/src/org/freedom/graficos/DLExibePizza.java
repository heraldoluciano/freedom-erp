/**
 * @version 10/08/2003 <BR>
 * @author Setpoint Informática Ltda./Anderson Sanchez <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.relatorios <BR>
 * Classe: @(#)DLExibePizza.java <BR>
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
 * Tela de exibição de graficos, permite a utilização dos gráficos pizza 3D rotatórios.
 * 
 */

package org.freedom.graficos;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JPanelPad;

import org.freedom.telas.FFilho;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
public class DLExibePizza extends FFilho implements ActionListener {
  private JPanelPad pnCli = new JPanelPad(JPanelPad.TP_JPANEL,new GridLayout(1,1));  
  private JPanelPad pnCab = new JPanelPad(JPanelPad.TP_JPANEL,new FlowLayout(FlowLayout.CENTER));
  private JPanelPad pnRod = new JPanelPad(JPanelPad.TP_JPANEL,new FlowLayout(FlowLayout.CENTER));
  public DLExibePizza(JFreeChart ch,int larg,int alt,String sTitulo,String sVlr) {    
    setAtribos(0,0,larg,alt);
    setTitulo("Visualização de gráfico");
	ChartPanel chartPanel = new ChartPanel(ch);
    Container c = getContentPane();
    pnCli.add(chartPanel);      
	
	pnCab.setPreferredSize(new Dimension(100,20));
	pnCab.add(new JLabelPad(sTitulo));
	pnCab.setBackground(new Color(255,255,255));
	
	pnRod.setPreferredSize(new Dimension(100,20));
	pnRod.add(new JLabelPad(sVlr));
	pnRod.setBackground(new Color(255,255,255));
	
	c.add(pnCab, BorderLayout.NORTH);
    c.add(pnCli, BorderLayout.CENTER);
	c.add(pnRod, BorderLayout.SOUTH);

  }
  public void actionPerformed(ActionEvent evt) {}
}
