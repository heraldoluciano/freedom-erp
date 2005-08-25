/**
 * @version 14/07/2003 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)DLDescontItVenda.java <BR>
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

package org.freedom.modulos.pcp;
import java.awt.Component;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;

import javax.swing.JScrollPane;

import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JTextAreaPad;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.FFDialogo;

public class DLFinalizaOP extends FFDialogo implements FocusListener{
	
	private static final long serialVersionUID = 1L;
	
	private JTextFieldPad txtQtdPrevOP = new JTextFieldPad(JTextFieldPad.TP_DECIMAL,15,3);
	private JTextFieldPad txtQtdFinalOP = new JTextFieldPad(JTextFieldPad.TP_DECIMAL,15,3);
	private JTextAreaPad txaJustifcQtdProd = new JTextAreaPad();
	private JLabelPad lJustifcQtdProd = new JLabelPad("Justificativa");
    private double dVlr;
    private String sObs;
    boolean bJust = false;
	public DLFinalizaOP(Component cOrig,String sQtdPrevOp) {
		super(cOrig);
        txtQtdPrevOP.setVlrString(sQtdPrevOp);
        txtQtdFinalOP.setVlrString(sQtdPrevOp);
		setTitulo("Finalização da OP.");
		setAtribos(250,150);
		
		txtQtdPrevOP.setAtivo(false);
		adic(new JLabelPad("Qtd. prevista:"),7,5,100,20);
		adic(txtQtdPrevOP,7,25,110,20);
		adic(new JLabelPad("Qtd. produzida:"),120,5,100,20);
		adic(txtQtdFinalOP,120,25,110,20); 
		
		txaJustifcQtdProd.setVisible(false);
		lJustifcQtdProd.setVisible(false);
		
		adic(lJustifcQtdProd,7,45,300,20);
		adic(new JScrollPane(txaJustifcQtdProd),7,65,222,50);
		
		txtQtdFinalOP.addFocusListener(this);
		
	}
	public void focusLost(FocusEvent fevt){
		if (fevt.getSource() == txtQtdFinalOP){		
		    if((txtQtdPrevOP.getVlrDouble().doubleValue()!=txtQtdFinalOP.getVlrDouble().doubleValue()) && (txaJustifcQtdProd.getVlrString().equals(""))) {
		    	setSize(250,210);
		    	lJustifcQtdProd.setVisible(true);
		        txaJustifcQtdProd.setVisible(true);
		        setVisible(true);
		        txaJustifcQtdProd.requestFocus();	
		    }
		    else {
		    	lJustifcQtdProd.setVisible(false);
		        txaJustifcQtdProd.setVisible(false);
		        txaJustifcQtdProd.setVlrString("");
		    }		    
		}	
	}
	public void focusGained(FocusEvent fevt){
		
	}

	public double getValor() {
		return txtQtdFinalOP.getVlrDouble().doubleValue();
	}
    public String getObs() {
    	return txaJustifcQtdProd.getVlrString();
    }
    public void keyPressed(KeyEvent kevt) {
           super.keyPressed(kevt);
    }
    
    public void ok(){
    	if ((txtQtdPrevOP.getVlrDouble().doubleValue() != getValor()) &&(txaJustifcQtdProd.getVlrString().equals(""))){
	        Funcoes.mensagemErro(this,"Quantidade produzida difere da quantidade prevista.\nJustifique.");
    	    return;
    	}
    	else{
    	    super.ok();
	    }
    }
}
