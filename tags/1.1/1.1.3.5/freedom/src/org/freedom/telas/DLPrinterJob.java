/**
 * @version 05/06/2000 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 * Pacote: org.freedom.telas <BR>
 * Classe: @(#)DLPrinterJob.java <BR>
 * 
 * Este programa � licenciado de acordo com a LPG-PC (Licen�a P�blica Geral para Programas de Computador), <BR>
 * vers�o 2.1.0 ou qualquer vers�o posterior. <BR>
 * A LPG-PC deve acompanhar todas PUBLICA��ES, DISTRIBUI��ES e REPRODU��ES deste Programa. <BR>
 * Caso uma c�pia da LPG-PC n�o esteja dispon�vel junto com este Programa, voc� pode contatar <BR>
 * o LICENCIADOR ou ent�o pegar uma c�pia em: <BR>
 * Licen�a: http://www.lpg.adv.br/licencas/lpgpc.rtf <BR>
 * Para poder USAR, PUBLICAR, DISTRIBUIR, REPRODUZIR ou ALTERAR este Programa � preciso estar <BR>
 * de acordo com os termos da LPG-PC <BR> <BR>
 *
 * Coment�rios da classe.....
 */

package org.freedom.telas;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.print.PageFormat;

import javax.swing.JButton;
import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JPanelPad;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import org.freedom.bmps.Icone;
import org.freedom.bmps.Imagem;
import org.freedom.componentes.ImprimeLayout;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.funcoes.Funcoes;

public class DLPrinterJob extends FFDialogo implements ActionListener,KeyListener {private static final long serialVersionUID = 1L;


  private JPanelPad pnCli = new JPanelPad(JPanelPad.TP_JPANEL,new GridLayout(1,1));
  private JPanelPad pnCab = new JPanelPad(JPanelPad.TP_JPANEL,new FlowLayout(FlowLayout.CENTER, 0, 0));  
  private ImprimeLayout impLay = new ImprimeLayout();
  private JButton btSair = new JButton("Sair",Icone.novo("btSair.gif"));
  private JButton btImp = new JButton("Imprimir",Icone.novo("btImprime.gif"));
  private JButton btProx = new JButton(Icone.novo("btProx.gif"));
  private JButton btAnt = new JButton(Icone.novo("btAnt.gif"));
  private JButton btPrim = new JButton(Icone.novo("btPrim.gif"));
  private JButton btUlt = new JButton(Icone.novo("btUlt.gif"));
  private JLabelPad lbPag = new JLabelPad("1 de 1");

  private JPanelPad pinCab = new JPanelPad(232,45);
  private JScrollPane spn = new JScrollPane();
  private JButton btZoom100 = new JButton(Icone.novo("btZoom100.gif"));
  private JButton btZoomIn = new JButton(Icone.novo("btZoomIn.gif"));
  private JButton btZoomPag = new JButton(Icone.novo("btZoomPag.gif"));
  private JTextFieldPad txtZoom = new JTextFieldPad(JTextFieldPad.TP_INTEGER,3,0);
  private JButton btMais = new JButton(Icone.novo("btZoomMais.gif"));
  private JButton btMenos = new JButton(Icone.novo("btZoomMenos.gif"));
  private PageFormat pag = null;
  
  public  String strTemp = ""; 
  private int iZoomAtual = 100;
  boolean bVisualiza = false;
  public DLPrinterJob(ImprimeLayout impL,Component cOrig) {
  	super(cOrig);        
    impLay = impL;
    
    setTitulo("Visualizar Impress�o Gr�fica");
	setAtribos(pnPai.getSize().width,pnPai.getSize().height);

	txtZoom.setEnterSai(false);

	c.add(pnCab,BorderLayout.NORTH);
	btSair.setPreferredSize(new Dimension(80,30));
	pnCab.add(pinCab);
	pinCab.adic(btZoom100,7,5,30,30);
	pinCab.adic(btZoomIn,40,5,30,30);
	pinCab.adic(btZoomPag,73,5,30,30);
	pinCab.adic(txtZoom,106,5,50,30);
	pinCab.adic(btMais,159,5,30,30);
	pinCab.adic(btMenos,192,5,30,30);

	//monta a area de visualiza��o:


    spn.setViewportView(impLay);
    pnCli.add(spn);   
       
    c.add(pnCli, BorderLayout.CENTER);
    
//desmonta tela antiga de dialogo:

    c.remove(0);

//monta tela atual:
      
    JPanelPad pnRod = new JPanelPad(JPanelPad.TP_JPANEL,new BorderLayout());
    JPanelPad pnFCenter = new JPanelPad(JPanelPad.TP_JPANEL,new FlowLayout(FlowLayout.CENTER,0,0));

    JPanelPad pnDir = new JPanelPad(JPanelPad.TP_JPANEL,new GridLayout(1,2));
    pnDir.add(btImp);
    pnDir.add(btSair);
    pnRod.add(pnDir,BorderLayout.EAST);

    JPanelPad pnCenter = new JPanelPad(JPanelPad.TP_JPANEL,new GridLayout(1,5));
    
    pnFCenter.add(pnCenter);

    pnCenter.add(btPrim);
    pnCenter.add(btAnt);
    pnCenter.add(lbPag);
    pnCenter.add(btProx);
    pnCenter.add(btUlt);
    pnRod.add(pnFCenter,BorderLayout.CENTER);
	
	c.add(pnRod, BorderLayout.SOUTH);
      
//	Configura os Listeners e Componentes  
        
    lbPag.setHorizontalAlignment(SwingConstants.CENTER);
	btSair.addActionListener(this);
	btImp.addActionListener(this);
	btProx.addActionListener(this);
	btAnt.addActionListener(this);
	btPrim.addActionListener(this);
	btUlt.addActionListener(this);

	btZoom100.addActionListener(this);
	btZoomIn.addActionListener(this);
	btZoomPag.addActionListener(this);
	btMais.addActionListener(this);
	btMenos.addActionListener(this);
	btSair.addActionListener(this);
	txtZoom.addKeyListener(this);

	impLay.montaG();
	impLay.addMouseListener(
	  new MouseAdapter() {
		public void mouseClicked(MouseEvent mevt) {
		  if (mevt.getButton() == 1) {
			if (iZoomAtual < 990) {
			  setZoom(iZoomAtual+10);
			  impLay.repaint();
			}
		  }
		  else {
			if (iZoomAtual > 10) {
			  setZoom(iZoomAtual-10);
			  impLay.repaint();
			}
		  }	  
		  txtZoom.setVlrString(""+iZoomAtual);		
		}
	  } 
	);		 

	impLay.setCursor(getToolkit().createCustomCursor(Imagem.novo("curZoom.gif"), new Point(5,5), "Zoom"));
					
	upContaPag(impLay.getPagAtual(),impLay.getNumPags());
	pag = impLay.getPFPad();	
	txtZoom.setVlrString("100");
	
	setVisible(true);
  }
  
  public void actionPerformed(ActionEvent evt) {
    if (evt.getSource() == btSair)
		setVisible(false);
    else if (evt.getSource() == btImp) { 
	  OK = impLay.imprimir(false);
    }   
	else if (evt.getSource() == btAnt) 
	  impLay.toPagina(impLay.getPagAtual()-1);
	else if (evt.getSource() == btProx)
	  impLay.toPagina(impLay.getPagAtual()+1);
	else if (evt.getSource() == btPrim)
	  impLay.toPagina(1);
	else if (evt.getSource() == btUlt)
	  impLay.toPagina(impLay.getNumPags());
	else if (evt.getSource() == btZoom100) {
	  setZoom(100);
	  impLay.repaint();
	}
	else if (evt.getSource() == btZoomIn) {
	  setZoom(getPercEncaixaY());
	  impLay.repaint();
	}
	else if (evt.getSource() == btZoomPag) {
	  setZoom(getPercEncaixaX());
	  impLay.repaint();
	}
	else if (evt.getSource() == btMais) {
	  if (iZoomAtual < 990) {
		setZoom(iZoomAtual+10);
		impLay.repaint();
	  }
	}
	else if (evt.getSource() == btMenos) {
	  if (iZoomAtual > 10) {
		setZoom(iZoomAtual-10);
		impLay.repaint();
	  }
	}
	txtZoom.setVlrString(""+iZoomAtual);
	  
	upContaPag(impLay.getPagAtual(),impLay.getNumPags());
  }
  public void keyPressed(KeyEvent kevt) {
	if (kevt.getKeyCode() == KeyEvent.VK_ENTER) {
	  if (txtZoom.getText().trim().length() == 0) {
		txtZoom.setVlrString(""+iZoomAtual);
	  }
	  else {
		setZoom(txtZoom.getVlrInteger().intValue());
		pnCli.updateUI();
		impLay.repaint();
	  }
	}
  }
  
  private void setZoom(int iVal) {
  	if (iVal == 0) {
  	  Funcoes.mensagemErro(this,"N�o � poiss�vel ajustar o zoom para 0%!");
  	  return;
	}
  	
//  	double dX = (double)iVal/100.0;  
  	double dX = iVal/100.0;  
//	double dY = (double)iVal/100.0;
	double dY = iVal/100.0;
	Dimension dAnt = new Dimension((int)pag.getWidth(),(int)pag.getHeight());
    impLay.setEscala(dX,dY);
    dAnt.setSize(dAnt.getWidth()*dX,dAnt.getHeight()*dY);
    impLay.setPreferredSize(dAnt);
    impLay.revalidate();
    iZoomAtual = iVal;
  }
  private int getPercEncaixaX() {
  	double dLargPag = pag.getWidth();
	double dLarg = impLay.getVisibleRect().getWidth();
  	double dFat = (dLarg / dLargPag); 
  	return (int)(dFat*100.0); 	 
  }
  
  private int getPercEncaixaY() {
	double dAltPag = pag.getHeight();
	double dAlt = impLay.getVisibleRect().getHeight();
	double dFat = (dAlt / dAltPag); 
	return (int)(dFat*100.0); 	 
  }

  public void upContaPag(int Atual, int Num) {
    lbPag.setText(Atual+" de "+Num);
  }
  public void keyTyped(KeyEvent kevt) { }
  public void keyReleased(KeyEvent kevt) { }
}