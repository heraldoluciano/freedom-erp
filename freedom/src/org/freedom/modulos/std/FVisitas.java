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
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

import javax.swing.BorderFactory;
import javax.swing.JButton;

import org.freedom.bmps.Icone;
import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JButtonPad;
import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JPanelPad;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.telas.FFilho;

public class FVisitas extends FFilho implements ActionListener{
	
	private JPanelPad pinMes1 = new JPanelPad();
	private JPanelPad pinMes2 = new JPanelPad();
	private JPanelPad pinMes3 = new JPanelPad();
	private JPanelPad pinMes4 = new JPanelPad();
	private JPanelPad pinMes5 = new JPanelPad();
	private JPanelPad pinMes6 = new JPanelPad();
	private JPanelPad pinMes7 = new JPanelPad();
	private JPanelPad pinMes8 = new JPanelPad();
	private JPanelPad pinMes9 = new JPanelPad();
	private JPanelPad pinMes10 = new JPanelPad();
	private JPanelPad pinMes11 = new JPanelPad();
	private JPanelPad pinMes12 = new JPanelPad();
	private JPanelPad pn = new JPanelPad(600,400);
	private JPanelPad pnRod = new JPanelPad(JPanelPad.TP_JPANEL,new BorderLayout());
	private JTextFieldPad txtCodCli = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
	private JTextFieldFK txtRazCli = new JTextFieldFK(JTextFieldPad.TP_STRING,50,0);
	private JTextFieldPad txtAno = new JTextFieldPad(JTextFieldPad.TP_INTEGER,4,0);
	private JTextFieldPad txtNumVisita1 = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
	private JTextFieldPad txtNumVisita2 = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
	private JTextFieldPad txtNumVisita3 = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
	private JTextFieldPad txtNumVisita4 = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
	private JTextFieldPad txtNumVisita5 = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
	private JTextFieldPad txtNumVisita6 = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
	private JTextFieldPad txtNumVisita7 = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
	private JTextFieldPad txtNumVisita8 = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
	private JTextFieldPad txtNumVisita9 = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
	private JTextFieldPad txtNumVisita10 = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
	private JTextFieldPad txtNumVisita11 = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
	private JTextFieldPad txtNumVisita12 = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
	private JTextFieldPad txtQtdNova1 = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
	private JTextFieldPad txtQtdNova2 = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
	private JTextFieldPad txtQtdNova3 = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
	private JTextFieldPad txtQtdNova4 = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
	private JTextFieldPad txtQtdNova5 = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
	private JTextFieldPad txtQtdNova6 = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
	private JTextFieldPad txtQtdNova7 = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
	private JTextFieldPad txtQtdNova8 = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
	private JTextFieldPad txtQtdNova9 = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
	private JTextFieldPad txtQtdNova10 = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
	private JTextFieldPad txtQtdNova11 = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
	private JTextFieldPad txtQtdNova12 = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
	private JButton btMudaQtd1 = new JButton(Icone.novo("btExecuta2.gif"));
	private JButton btMudaQtd2 = new JButton(Icone.novo("btExecuta2.gif"));
	private JButton btMudaQtd3 = new JButton(Icone.novo("btExecuta2.gif"));
	private JButton btMudaQtd4 = new JButton(Icone.novo("btExecuta2.gif"));
	private JButton btMudaQtd5 = new JButton(Icone.novo("btExecuta2.gif"));
	private JButton btMudaQtd6 = new JButton(Icone.novo("btExecuta2.gif"));
	private JButton btMudaQtd7 = new JButton(Icone.novo("btExecuta2.gif"));
	private JButton btMudaQtd8 = new JButton(Icone.novo("btExecuta2.gif"));
	private JButton btMudaQtd9 = new JButton(Icone.novo("btExecuta2.gif"));
	private JButton btMudaQtd10 = new JButton(Icone.novo("btExecuta2.gif"));
	private JButton btMudaQtd11 = new JButton(Icone.novo("btExecuta2.gif"));
	private JButton btMudaQtd12 = new JButton(Icone.novo("btExecuta2.gif"));
	private JButton btMudaTudo = new JButton("Alterar todos",Icone.novo("btExecuta.gif"));
	private JButton btSair = new JButton("Sair", Icone.novo("btSair.gif"));
	private ListaCampos lcCli = new ListaCampos(this, "");
	
 
  public FVisitas() {
	  super(true);
	  setTitulo("Distribuição");
	  setAtribos(50,50,660,520);
	  
	  Container c = getTela();
	  c.setLayout(new BorderLayout());
	  c.add(pn,BorderLayout.CENTER);
	  c.add(pnRod, BorderLayout.SOUTH);
	  
	  txtCodCli.setNomeCampo("CodOP");
	  txtCodCli.setFK(true);
		
	  lcCli.add(new GuardaCampo(txtCodCli, "CodCli", "Cód.cli.", ListaCampos.DB_PK, null, true));
	  lcCli.add(new GuardaCampo(txtRazCli, "RazCli", "Razão social", ListaCampos.DB_SI, null, false));
	  lcCli.setQueryCommit(false);
	  lcCli.setReadOnly(true);
	  txtRazCli.setSoLeitura(true);	  
	  txtCodCli.setTabelaExterna(lcCli);
	  lcCli.montaSql(false, "CLIENTE", "VD");
	  
	  pnRod.add(btSair, BorderLayout.EAST);
	  btSair.setPreferredSize(new Dimension(100, 30));
	  btSair.addActionListener(this);
	  
	  pn.adic(new JLabelPad("Cód.cli."),7,0,80,20);
	  pn.adic(txtCodCli,7,20,80,20);
	  pn.adic(new JLabelPad("Razão social"),90,0,250,20);
	  pn.adic(txtRazCli,90,20,250,20);
	  pn.adic(new JLabelPad("Ano"),343,0,80,20);
	  pn.adic(txtAno,343,20,80,20);
	  pn.adic(btMudaTudo,460,15,150,30);
	  
	  JLabelPad lbMes1 = new JLabelPad("   Janeiro");
	  lbMes1.setOpaque(true);
	  pn.adic(lbMes1,17,55,80,15);
	  pn.adic(pinMes1,7,60,200,80);
	  pinMes1.setBorder( BorderFactory.createEtchedBorder());	  
	  JLabelPad lbMes2 = new JLabelPad("   Fevereiro");
	  lbMes2.setOpaque(true);
	  pn.adic(lbMes2,223,55,80,15);
	  pn.adic(pinMes2,215,60,200,80);
	  pinMes2.setBorder( BorderFactory.createEtchedBorder());	  
	  JLabelPad lbMes3 = new JLabelPad("   Março");
	  lbMes3.setOpaque(true);
	  pn.adic(lbMes3,433,55,80,15);
	  pn.adic(pinMes3,423,60,200,80);
	  pinMes3.setBorder( BorderFactory.createEtchedBorder());	
	  
	  JLabelPad lbMes4 = new JLabelPad("   Abril");
	  lbMes4.setOpaque(true);
	  pn.adic(lbMes4,17,155,80,15);
	  pn.adic(pinMes4,7,160,200,80);
	  pinMes4.setBorder( BorderFactory.createEtchedBorder());	  
	  JLabelPad lbMes5 = new JLabelPad("   Maio");
	  lbMes5.setOpaque(true);
	  pn.adic(lbMes5,223,155,80,15);
	  pn.adic(pinMes5,215,160,200,80);
	  pinMes5.setBorder( BorderFactory.createEtchedBorder());	  
	  JLabelPad lbMes6 = new JLabelPad("   Junho");
	  lbMes6.setOpaque(true);
	  pn.adic(lbMes6,433,155,80,15);
	  pn.adic(pinMes6,423,160,200,80);
	  pinMes6.setBorder( BorderFactory.createEtchedBorder());	
	  
	  JLabelPad lbMes7 = new JLabelPad("   Julho");
	  lbMes7.setOpaque(true);
	  pn.adic(lbMes7,17,255,80,15);
	  pn.adic(pinMes7,7,260,200,80);
	  pinMes7.setBorder( BorderFactory.createEtchedBorder());	  
	  JLabelPad lbMes8 = new JLabelPad("   Agosto");
	  lbMes8.setOpaque(true);
	  pn.adic(lbMes8,223,255,80,15);
	  pn.adic(pinMes8,215,260,200,80);
	  pinMes8.setBorder( BorderFactory.createEtchedBorder());	  
	  JLabelPad lbMes9 = new JLabelPad("   Setembro");
	  lbMes9.setOpaque(true);
	  pn.adic(lbMes9,433,255,80,15);
	  pn.adic(pinMes9,423,260,200,80);
	  pinMes9.setBorder( BorderFactory.createEtchedBorder());	  
	  
	  JLabelPad lbMes10 = new JLabelPad("   Outubro");
	  lbMes10.setOpaque(true);
	  pn.adic(lbMes10,17,355,80,15);
	  pn.adic(pinMes10,7,360,200,80);
	  pinMes10.setBorder( BorderFactory.createEtchedBorder());	  
	  JLabelPad lbMes11 = new JLabelPad("   Novembro");
	  lbMes11.setOpaque(true);
	  pn.adic(lbMes11,223,355,80,15);
	  pn.adic(pinMes11,215,360,200,80);
	  pinMes11.setBorder( BorderFactory.createEtchedBorder());	  
	  JLabelPad lbMes12 = new JLabelPad("   Dezembro");
	  lbMes12.setOpaque(true);
	  pn.adic(lbMes12,433,355,80,15);
	  pn.adic(pinMes12,423,360,200,80);
	  pinMes12.setBorder( BorderFactory.createEtchedBorder());
	  
	 	  
	  pinMes1.adic(new JLabelPad("N. de visitas"),7,20,80,20);
	  pinMes1.adic(txtNumVisita1,7,40,80,20);
	  txtNumVisita1.setAtivo(false);
	  pinMes1.adic(new JLabelPad("nova qtd."),90,20,80,20);
	  pinMes1.adic(txtQtdNova1,90,40,80,20);
	  pinMes1.adic(btMudaQtd1,173,40,20,20);	  
	  pinMes2.adic(new JLabelPad("N. de visitas"),7,20,80,20);
	  pinMes2.adic(txtNumVisita2,7,40,80,20);
	  txtNumVisita2.setAtivo(false);
	  pinMes2.adic(new JLabelPad("nova qtd."),90,20,80,20);
	  pinMes2.adic(txtQtdNova2,90,40,80,20);
	  pinMes2.adic(btMudaQtd2,173,40,20,20);
	  pinMes3.adic(new JLabelPad("N. de visitas"),7,20,80,20);
	  pinMes3.adic(txtNumVisita3,7,40,80,20);
	  txtNumVisita3.setAtivo(false);
	  pinMes3.adic(new JLabelPad("nova qtd."),90,20,80,20);
	  pinMes3.adic(txtQtdNova3,90,40,80,20);
	  pinMes3.adic(btMudaQtd3,173,40,20,20);	 
	  
	  pinMes4.adic(new JLabelPad("N. de visitas"),7,20,80,20);
	  pinMes4.adic(txtNumVisita4,7,40,80,20);
	  txtNumVisita4.setAtivo(false);
	  pinMes4.adic(new JLabelPad("nova qtd."),90,20,80,20);
	  pinMes4.adic(txtQtdNova4,90,40,80,20);
	  pinMes4.adic(btMudaQtd4,173,40,20,20);	 
	  pinMes5.adic(new JLabelPad("N. de visitas"),7,20,80,20);
	  pinMes5.adic(txtNumVisita5,7,40,80,20);
	  txtNumVisita5.setAtivo(false);
	  pinMes5.adic(new JLabelPad("nova qtd."),90,20,80,20);
	  pinMes5.adic(txtQtdNova5,90,40,80,20);
	  pinMes5.adic(btMudaQtd5,173,40,20,20);	 
	  pinMes6.adic(new JLabelPad("N. de visitas"),7,20,80,20);
	  pinMes6.adic(txtNumVisita6,7,40,80,20);
	  txtNumVisita6.setAtivo(false);
	  pinMes6.adic(new JLabelPad("nova qtd."),90,20,80,20);
	  pinMes6.adic(txtQtdNova6,90,40,80,20);
	  pinMes6.adic(btMudaQtd6,173,40,20,20);	 
	  
	  pinMes7.adic(new JLabelPad("N. de visitas"),7,20,80,20);
	  pinMes7.adic(txtNumVisita7,7,40,80,20);
	  txtNumVisita7.setAtivo(false);
	  pinMes7.adic(new JLabelPad("nova qtd."),90,20,80,20);
	  pinMes7.adic(txtQtdNova7,90,40,80,20);
	  pinMes7.adic(btMudaQtd7,173,40,20,20);	 
	  pinMes8.adic(new JLabelPad("N. de visitas"),7,20,80,20);
	  pinMes8.adic(txtNumVisita8,7,40,80,20);
	  txtNumVisita8.setAtivo(false);
	  pinMes8.adic(new JLabelPad("nova qtd."),90,20,80,20);
	  pinMes8.adic(txtQtdNova8,90,40,80,20);
	  pinMes8.adic(btMudaQtd8,173,40,20,20);	 
	  pinMes9.adic(new JLabelPad("N. de visitas"),7,20,80,20);
	  pinMes9.adic(txtNumVisita9,7,40,80,20);
	  txtNumVisita9.setAtivo(false);
	  pinMes9.adic(new JLabelPad("nova qtd."),90,20,80,20);
	  pinMes9.adic(txtQtdNova9,90,40,80,20);
	  pinMes9.adic(btMudaQtd9,173,40,20,20);	 
	  
	  pinMes10.adic(new JLabelPad("N. de visitas"),7,20,80,20);
	  pinMes10.adic(txtNumVisita10,7,40,80,20);
	  txtNumVisita10.setAtivo(false);
	  pinMes10.adic(new JLabelPad("nova qtd."),90,20,80,20);
	  pinMes10.adic(txtQtdNova10,90,40,80,20);
	  pinMes10.adic(btMudaQtd10,173,40,20,20);	 
	  pinMes11.adic(new JLabelPad("N. de visitas"),7,20,80,20);
	  pinMes11.adic(txtNumVisita11,7,40,80,20);
	  txtNumVisita11.setAtivo(false);
	  pinMes11.adic(new JLabelPad("nova qtd."),90,20,80,20);
	  pinMes11.adic(txtQtdNova11,90,40,80,20);
	  pinMes11.adic(btMudaQtd11,173,40,20,20);	 
	  pinMes12.adic(new JLabelPad("N. de visitas"),7,20,80,20);
	  pinMes12.adic(txtNumVisita12,7,40,80,20);
	  txtNumVisita12.setAtivo(false);
	  pinMes12.adic(new JLabelPad("nova qtd."),90,20,80,20);
	  pinMes12.adic(txtQtdNova12,90,40,80,20);
	  pinMes12.adic(btMudaQtd12,173,40,20,20);	 
	 
	  
	
	 
  }
  public void actionPerformed(ActionEvent evt) {
	  if (evt.getSource() == btSair) {
			dispose();
	  }
  }
  public void setConexao(Connection cn) {
      super.setConexao(cn);
      lcCli.setConexao(cn);
  }
}