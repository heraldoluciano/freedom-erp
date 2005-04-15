/**
 * @version 28/07/2003 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.atd <BR>
 * Classe: @(#)DLDescontItOrc.java <BR>
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
 * 
 */

package org.freedom.modulos.atd;
import java.awt.Component;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;

import org.freedom.componentes.JLabelPad;

import org.freedom.componentes.JTextFieldPad;
import org.freedom.telas.FFDialogo;

public class DLDescontItOrc extends FFDialogo {
	private JTextFieldPad txtDesc1 = new JTextFieldPad(JTextFieldPad.TP_DECIMAL,4,1);
	private JTextFieldPad txtDesc2 = new JTextFieldPad(JTextFieldPad.TP_DECIMAL,4,1);
	private JTextFieldPad txtDesc3 = new JTextFieldPad(JTextFieldPad.TP_DECIMAL,4,1);
	private JTextFieldPad txtDesc4 = new JTextFieldPad(JTextFieldPad.TP_DECIMAL,4,1);
	private JTextFieldPad txtDesc5 = new JTextFieldPad(JTextFieldPad.TP_DECIMAL,4,1);
	private JTextFieldPad txtVlrDescTot = new JTextFieldPad(JTextFieldPad.TP_DECIMAL,8,2);
	private JTextFieldPad txtVlrTot = new JTextFieldPad(JTextFieldPad.TP_DECIMAL,15,2);
    private double dVlr;
    private String sObs;
	public DLDescontItOrc(Component cOrig, double dVal,String[] sVals) {
		super(cOrig);
        dVlr = dVal;
		setTitulo("Descontos:");
		setAtribos(235,175);
                
                txtVlrDescTot.setAtivo(false);
                txtVlrTot.setAtivo(false);
                
                txtVlrDescTot.setVlrBigDecimal(new BigDecimal(0));
                txtVlrTot.setVlrBigDecimal(new BigDecimal(dVlr));
                
		adic(new JLabelPad("1 :"),7,10,20,20);
		adic(txtDesc1,30,10,77,20);
		adic(new JLabelPad("Valor Desc.:"),110,20,100,20);
		adic(new JLabelPad("2 :"),7,30,20,20);
		adic(txtDesc2,30,30,77,20);
		adic(txtVlrDescTot,110,40,100,20);
		adic(new JLabelPad("3 :"),7,50,20,20);
		adic(txtDesc3,30,50,77,20);
		adic(new JLabelPad("Preço do Item:"),110,60,100,20);
		adic(new JLabelPad("4 :"),7,70,20,20);
		adic(txtDesc4,30,70,77,20);
		adic(txtVlrTot,110,80,100,20);
		adic(new JLabelPad("5 :"),7,90,20,20);
		adic(txtDesc5,30,90,77,20);
                txtDesc1.setText(sVals[0]);
                txtDesc2.setVlrString(sVals[1]);
                txtDesc3.setVlrString(sVals[2]);
                txtDesc4.setVlrString(sVals[3]);
                txtDesc5.setVlrString(sVals[4]);
                calc();
	}
        private void calc() {
                double dVlrTot = dVlr;
                double dVlrDescTot = 0;
                double dVlrTmpDesc = 0;
                double[] dSet = new double[5];
                String sSep = "";
                String sVal = "";
                sObs = "";
                dSet[0] = txtDesc1.getVlrDouble().doubleValue();
                dSet[1] = txtDesc2.getVlrDouble().doubleValue();
                dSet[2] = txtDesc3.getVlrDouble().doubleValue();
                dSet[3] = txtDesc4.getVlrDouble().doubleValue();
                dSet[4] = txtDesc5.getVlrDouble().doubleValue();
                for (int i=0; i<5;i++) {
                        if (dSet[i] != 0) {
                                dVlrTmpDesc = dVlrTot*(dSet[i]/100);
                                dVlrTot -= dVlrTmpDesc;
                                dVlrDescTot += dVlrTmpDesc;
                                sVal = (dSet[i] - dSet[i]) > 0.0 ? ""+dSet[i] : ""+(int)dSet[i];
                                sObs += sSep + sVal;
                                sSep = " + ";
                        }
                }
                txtVlrDescTot.setVlrBigDecimal(new BigDecimal(dVlrDescTot));
                txtVlrTot.setVlrBigDecimal(new BigDecimal(dVlrTot));
        }
	public double getValor() {
		return txtVlrDescTot.getVlrDouble().doubleValue();
	}
        public String getObs() {
                return "Desc.: "+sObs;
        }
        public void keyPressed(KeyEvent kevt) {
                if (kevt.getKeyCode() == KeyEvent.VK_ENTER) {
                        if (kevt.getSource() == txtDesc1) 
                                txtDesc2.requestFocus();
                        else if (kevt.getSource() == txtDesc2)
                                txtDesc3.requestFocus();
                        else if (kevt.getSource() == txtDesc3)
                                txtDesc4.requestFocus();
                        else if (kevt.getSource() == txtDesc4)
                                txtDesc5.requestFocus();
                        else if (kevt.getSource() == txtDesc5)
                                btOK.requestFocus();
                        calc();
                }
                super.keyPressed(kevt);
        }
}
