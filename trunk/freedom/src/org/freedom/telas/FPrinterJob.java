/**
 * @version 14/11/2000 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 * Pacote: org.freedom.telas <BR>
 * Classe: @(#)FPrinterJob.java <BR>
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
import java.awt.BorderLayout;
import java.awt.Container;
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
import java.io.File;
import java.sql.ResultSet;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JRViewer;

import org.freedom.bmps.Icone;
import org.freedom.bmps.Imagem;
import org.freedom.componentes.ImprimeLayout;
import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JPanelPad;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.funcoes.Funcoes;

public class FPrinterJob extends FFilho implements ActionListener,KeyListener {
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
  private JasperPrint relJasper = null;
  private JPanelPad pinCab = new JPanelPad(232,45);
  private JScrollPane spn = new JScrollPane();
  private JButton btZoom100 = new JButton(Icone.novo("btZoom100.gif"));
  private JButton btZoomIn = new JButton(Icone.novo("btZoomIn.gif"));
  private JButton btZoomPag = new JButton(Icone.novo("btZoomPag.gif"));
  private JTextFieldPad txtZoom = new JTextFieldPad(JTextFieldPad.TP_INTEGER,3,0);
  private JButton btMais = new JButton(Icone.novo("btZoomMais.gif"));
  private JButton btMenos = new JButton(Icone.novo("btZoomMenos.gif"));
  private PageFormat pag = null;  
  private JPanelPad pinTools = new JPanelPad(47,45);
  private JButton btPdf = new JButton(Icone.novo("btPdf.gif"));    
  public String strTemp = ""; 
  private int iZoomAtual = 100;
  boolean bVisualiza = false;
  public FPrinterJob(ImprimeLayout impL,JInternalFrame ifOrig) {
  	super(false);
    impLay = impL;
    
    setTitulo("Visualizar Impressão Gráfica");
    
    setBounds(50,50,500,400);


	txtZoom.setTipo(JTextFieldPad.TP_INTEGER,3,0);
	txtZoom.setEnterSai(false);

    Container c = getContentPane();

	c.add(pnCab,BorderLayout.NORTH);
	btSair.setPreferredSize(new Dimension(80,30));
	pnCab.add(pinCab);
	pinCab.adic(btZoom100,7,5,30,30);
	pinCab.adic(btZoomIn,40,5,30,30);
	pinCab.adic(btZoomPag,73,5,30,30);
	pinCab.adic(txtZoom,106,5,50,30);
	pinCab.adic(btMais,159,5,30,30);
	pinCab.adic(btMenos,192,5,30,30);

	pinTools.adic(btPdf,7,5,30,30);
	btPdf.setToolTipText("Exporta para formato PDF");
	pnCab.add(pinTools);
	//monta a area de visualização:


    spn.setViewportView(impLay);
    pnCli.add(spn);   
       
    c.add(pnCli, BorderLayout.CENTER);
    
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
	
	btPdf.addActionListener(this);

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
	
	ifOrig.getDesktopPane().add(this);

	try {
	  setMaximum(true);
	}
	catch (Exception err) { 
	   err.printStackTrace();
	}  
	
  }
  public FPrinterJob(String sLayout,String sTituloRel,String sFiltros,ResultSet rs,JInternalFrame ifOrig) {
  	super(false);
    
    setTitulo(sTituloRel);
    
    setBounds(50,50,500,400);
	
	ifOrig.getDesktopPane().add(this);

	JRResultSetDataSource jrRS = new JRResultSetDataSource(rs);
	try{
		HashMap hParam = Aplicativo.empresa.getAll();
		
		hParam.put("USUARIO",Aplicativo.strUsuario);
		hParam.put("FILTROS",sFiltros);
		hParam.put("TITULO",sTituloRel);
		
		relJasper = JasperFillManager.fillReport(FPrinterJob.class.getResourceAsStream("/org/freedom/"+sLayout),hParam,jrRS);
		JRViewer viewer = new JRViewer(relJasper);
		this.setContentPane(viewer);
	}
	catch(JRException err){
		err.printStackTrace();
	}
	
	try {
	  setMaximum(true);
	}
	catch (Exception err) { 
	   err.printStackTrace();
	}  	
  }
  
  public JasperPrint getRelatorio(){
  	return this.relJasper;
  }
    
  public void actionPerformed(ActionEvent evt) {
    if (evt.getSource() == btSair)
		dispose();
	else if (evt.getSource() == btImp) { 
	  impLay.imprimir(false);
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
	else if (evt.getSource() == btPdf) {
	   exportaPdf();
	}
	txtZoom.setVlrString(""+iZoomAtual);
	  
	upContaPag(impLay.getPagAtual(),impLay.getNumPags());
  }
  private boolean exportaPdf() {
  	boolean bRetorno = false;
  	File fArq = Funcoes.buscaArq(this,"pdf");
    if (fArq==null)
        return false;
    impLay.gravaPdf(fArq.getPath());
  	return bRetorno;
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
  	  Funcoes.mensagemErro(this,"Não é poissível ajustar o zoom para 0%!");
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
