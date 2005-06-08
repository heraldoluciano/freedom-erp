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
import java.awt.event.KeyEvent;

import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.telas.FFDialogo;

public class DLFinalizaOP extends FFDialogo {
	private JTextFieldPad txtQtdPrevOP = new JTextFieldPad(JTextFieldPad.TP_DECIMAL,15,3);
	private JTextFieldPad txtQtdFinalOP = new JTextFieldPad(JTextFieldPad.TP_DECIMAL,15,3);
    private double dVlr;
    private String sObs;
	public DLFinalizaOP(Component cOrig,String sQtdPrevOp) {
		super(cOrig);
        txtQtdPrevOP.setVlrString(sQtdPrevOp);
		setTitulo("Finalização da OP.");
		setAtribos(300,200);
		
		txtQtdPrevOP.setAtivo(false);
		adic(new JLabelPad("Quantidade prevista"),7,5,150,20);
		adic(txtQtdPrevOP,7,25,77,20);
		adic(new JLabelPad("Quantidade produzida:"),7,50,150,20);
		adic(txtQtdFinalOP,7,70,77,20);
		
	}

	public double getValor() {
		return txtQtdFinalOP.getVlrDouble().doubleValue();
	}
    public String getObs() {
    	return "";
    }
    public void keyPressed(KeyEvent kevt) {
           super.keyPressed(kevt);
    }
}
