/**
 * @version 12/08/2005 <BR>
 * @author Setpoint Informática Ltda./Alex Rodrigues <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)DLVisitas.java <BR>
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
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JScrollPane;

import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JPanelPad;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.Tabela;
import org.freedom.telas.FFDialogo;

public class DLVisitas extends FFDialogo implements MouseListener{
  private JTextFieldPad txtAno = new JTextFieldPad(JTextFieldPad.TP_INTEGER,4,0);
  private JTextFieldPad txtCodCli = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
  private JTextFieldFK txtRazCli = new JTextFieldFK(JTextFieldPad.TP_STRING,50,0);
  private JPanelPad pnTab = new JPanelPad(JPanelPad.TP_JPANEL,new BorderLayout());
  private JPanelPad pinCab = new JPanelPad(300,70);
  private JPanelPad pinRod = new JPanelPad(300,100);
  private Tabela tab = new Tabela();
  private JLabel lbMes = new JLabel();
  
  public DLVisitas(Component cOrig, Connection con) {
    super(cOrig);    
    setTitulo("Alteração de historico");
    setAtribos(450, 400);
    setConexao(con);
    
    c.add(pinCab, BorderLayout.NORTH);
    setPainel(pinCab);
    
    adic(new JLabelPad("Cód.cli."),7,0,80,20);
    adic(txtCodCli,7,20,80,20);
    adic(new JLabelPad("Razão social"),90,0,100,20);
    adic(txtRazCli,90,20,240,20);
    adic(new JLabelPad("Ano"),333,0,60,20);
    adic(txtAno,333,20,60,20);
    adic(lbMes,15,45,150,20);
    lbMes.setFont(new Font("Arial",Font.BOLD,14));
    lbMes.setForeground(Color.blue);
   
    
    JScrollPane spnTabRec = new JScrollPane(tab);
    pnTab.add(spnTabRec,BorderLayout.CENTER);
    c.add(pnTab, BorderLayout.CENTER);
    
    tab.adicColuna("");
    tab.adicColuna("não sei o que vai na tabela");
    
    tab.setTamColuna(20,0);
    tab.setTamColuna(250,1);
    
    tab.addMouseListener(this);
    
    pnTab.add(pinRod, BorderLayout.SOUTH);
    setPainel(pinRod);
    
  }
  
  public void setCampos(Vector args){
	  txtCodCli.setVlrInteger((Integer)args.elementAt(0));
	  txtRazCli.setVlrString((String)args.elementAt(1));
	  txtAno.setVlrInteger(((Integer)args.elementAt(2)));	    
	  lbMes.setText(getMes(((Integer)args.elementAt(3)).intValue()));
  }
  
  public void mouseEntered(MouseEvent e) { }
  public void mouseExited(MouseEvent e) { }
  public void mousePressed(MouseEvent e) { }
  public void mouseReleased(MouseEvent e) { } 

  public void mouseClicked(MouseEvent mevt) {
    if (mevt.getClickCount() == 2) {
    	//if (mevt.getSource() == tab && tab.getLinhaSel() >= 0)
    }
  }
  
  public String getMes(int mes){
	  String[] meses = new String[]{"Janeiro","Fevereiro","Março","Abril","Maio","Junho",
			  "Julho","Agosto","Setembro","Outubro","Novembro","Dezembro"};
	  String retorno = meses[mes-1];
	  return retorno;
  }
 
  public void actionPerformed(ActionEvent evt) {
	  super.actionPerformed(evt);
  }
}