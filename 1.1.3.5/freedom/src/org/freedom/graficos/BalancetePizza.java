/**
 * @version 10/08/2003 <BR>
 * @author Setpoint Informática Ltda./Anderson Sanchez <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.relatorios <BR>
 * Classe: @(#)BalancetePizza.java <BR>
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
 * Gráfico de balancete financeiro no formato de pizza 3D.
 * 
 */

package org.freedom.graficos;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.Timer;

import org.freedom.funcoes.Funcoes;
import org.freedom.layout.LeiauteGR;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.util.Rotation;

public class BalancetePizza extends LeiauteGR {
	private static final long serialVersionUID = 1L;
	private Connection con = null;
	private Font fnTopEmp = new Font("Arial",Font.BOLD,11);
	private Font fnCabEmp = new Font("Arial",Font.PLAIN,8);
	private Font fnCabEmpNeg = new Font("Arial",Font.BOLD,8);
	private Font fnLabel = new Font("Arial",Font.BOLD,10);
	private ResultSet rs = null;
	private String sTitulo1 = "";
	private String sTitulo2 = "";	
	private String sVlrLabel = "";
	private JFreeChart chart = null; 
	private boolean bGirar = false;
	Vector vParamOrc = new Vector();
	public void montaG() {
		impRaz(false);
		montaCabEmp(con);	
		montaRel();
	}	
	private JFreeChart createChart(DefaultPieDataset dataset) {    
	  	  
	  JFreeChart chart = ChartFactory.createPieChart3D("",dataset,true,false,false);		
	  
	  chart.setBackgroundPaint(new Color(255, 255, 255));
	  PiePlot3D plot = (PiePlot3D) chart.getPlot();		
	  plot.setForegroundAlpha(0.60f);
	  if (bGirar) {
		plot.setStartAngle(270);
		plot.setDirection(Rotation.ANTICLOCKWISE);
		plot.setInteriorGap(0.33);	  	
		Rotator rotator = new Rotator(plot);
		rotator.start();
	  }	  
	  plot.setLabelFont(fnLabel);
	  //plot.setSectionLabelType(PiePlot.PERCENT_LABELS);
      plot.setOutlineStroke(null);

      return chart;
	}
		
	private void montaRel() {
	  imprimeRodape(false);
	  DefaultPieDataset data = new DefaultPieDataset();
      Vector vData = new Vector();
      double dVlrOutros = 0.0;      
      double dVlrTotal = 0.0;
      double dValor = 0.0;
      double dValorPerc = 0.0;
      String sLabel = "";
      try {	    
        while (rs.next()) {
            Vector vLinha = new Vector();
            vLinha.addElement(rs.getString(2).trim());
            vLinha.addElement(new Double (rs.getDouble(4))); 
			vData.addElement(vLinha);
			dVlrTotal += rs.getDouble(4);		    
        }
	  } 
	  catch (SQLException e) {
		Funcoes.mensagemInforma(this,"Erro na consulta de valores!\n"+e.getMessage());
	  }
    
	  for (int i2=0;vData.size()>i2;i2++){
	    dValor = ((Double) ((Vector) vData.elementAt(i2)).elementAt(1)).doubleValue();	
	    dValorPerc = (dValor*100)/dVlrTotal;	
	    if (dValorPerc<3.0) {
	      dVlrOutros += dValor;		  	
	    }
	    else {
		  sLabel = ((String) ((Vector) vData.elementAt(i2)).elementAt(0));
		  sLabel = sLabel + " ("+		
	               Funcoes.strDecimalToStrCurrency(14,2,dValor+"")+" ) ";
	  	  data.setValue(sLabel,dValor);	  		  	
	    } 			
	  }
      if (dVlrOutros>0.0) 
	    data.setValue("Outros valores ("+Funcoes.strDecimalToStrCurrency(14,2,dVlrOutros+"")+" ) ",dVlrOutros);

	  chart = createChart(data);	  		    			  
	  
	  setBordaRel();
	  
	  int iY = 35;
	  
	  drawLinha(0,iY,0,0,AL_LL);	          
      
      iY += 14;
       
	  setFonte(fnTopEmp);
	  drawTexto(sTitulo1,0,iY,getFontMetrics(fnCabEmp).stringWidth("  "+sTitulo1+"  "),AL_CEN);
	  setFonte(fnCabEmpNeg);

	  iY += 6;
	  
	  drawLinha(0,iY,0,0,AL_LL);

      iY +=14;
	  
	  setFonte(fnTopEmp);
	  drawTexto(sTitulo2,0,iY,getFontMetrics(fnCabEmp).stringWidth("  "+sTitulo2+"  "),AL_CEN);
	  setFonte(fnCabEmpNeg);

      iY += 6;

	  drawLinha(0,iY,0,0,AL_LL);

      iY += 50;
	  
	  drawGrafico(chart,15,iY,550,400);

	  iY += 12;
	  
	  setFonte(fnTopEmp);
	  sVlrLabel = "Valor total:"+Funcoes.strDecimalToStrCurrency(14,2,dVlrTotal+""); 
	  drawTexto(sVlrLabel,0,iY,getFontMetrics(fnCabEmp).stringWidth("  "+sVlrLabel+"  "),AL_CEN);
	  
	  termPagina();
	  finaliza();

    }

	public void setParam(Vector vParam) {
		vParamOrc = vParam;
	}
	public JFreeChart getGrafico() {
		return chart;
	}
	public void setConsulta(ResultSet rs2) {
		rs = rs2;
	}
	public void setTitulo(String sTit1,String sTit2) {
		sTitulo1 = sTit1;
		sTitulo2 = sTit2;
	}
	public void setConexao(Connection cn) {
	  con = cn;
	}
	public void setGirar(boolean bGir) {
	  bGirar = bGir;
	}	
	public String getVlrLabel() {
	  return sVlrLabel;
	}	
	class Rotator extends Timer implements ActionListener {
		private static final long serialVersionUID = 1L;
		private PiePlot3D plot;
		private int angle = 270;
		Rotator(PiePlot3D plot) {
			super(100, null);
			this.plot = plot;
			addActionListener(this);
		}
		public void actionPerformed(ActionEvent event) {
			this.plot.setStartAngle(angle);
			this.angle = this.angle + 2;
			if (this.angle == 360) {
				this.angle = 0;
			}
		}
	}
}
