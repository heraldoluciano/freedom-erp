/**
 * @version 12/07/2005 <BR>
 * @author Setpoint Informática Ltda./Robson Sanchez <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.cfg <BR>
 * Classe: @(#)FFluxo.java <BR>
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
package org.freedom.modulos.cfg;

import java.awt.BorderLayout;
import java.awt.Container;
import java.util.Vector;

import org.freedom.componentes.JComboBoxPad;
import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JPanelPad;
import org.freedom.telas.FFilho;

public class FVisual extends FFilho {

	private JComboBoxPad cbLookAndFeel = null;
	private Vector vDescLookAndFeel = new Vector();
	private Vector vValLookAndFeel = new Vector();
	private JLabelPad lbLookAndFeel = new JLabelPad("Selecione o visual desejado");
	private JPanelPad pnCli = new JPanelPad(JPanelPad.TP_JPANEL,new BorderLayout());

	
	public FVisual(boolean comScroll) {
		super(true);
		setTitulo("Configuração de Visual");
		setAtribos(10,10,300,200);
		
		Container c = getContentPane();

		c.setLayout(new BorderLayout());
		
		c.add(pnCli, BorderLayout.CENTER);
  	    //setPainel( pinCab, pnCliCab);

		cbLookAndFeel = new JComboBoxPad(vDescLookAndFeel, vValLookAndFeel, JComboBoxPad.TP_STRING, 100, 0);
		pnCli.adic(lbLookAndFeel,30,30,100,20);
		pnCli.adic(cbLookAndFeel,60,60,100,20);
		
	}


}
