/**
 * @version 06/09/2005 <BR>
 * @author Setpoint Informática Ltda./Robson Sanchez <BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.std <BR>
 * Classe:
 * @(#)FObsCliVend.java <BR>
 * 
 * Este programa é licenciado de acordo com a LPG-PC (Licença Pública Geral para
 * Programas de Computador), <BR>
 * versão 2.1.0 ou qualquer versão posterior. <BR>
 * A LPG-PC deve acompanhar todas PUBLICAÇÕES, DISTRIBUIÇÕES e REPRODUÇÕES deste
 * Programa. <BR> 
 * Caso uma cópia da LPG-PC não esteja disponível junto com este Programa, você
 * pode contatar <BR>
 * o LICENCIADOR ou então pegar uma cópia em: <BR>
 * Licença: http://www.lpg.adv.br/licencas/lpgpc.rtf <BR>
 * Para poder USAR, PUBLICAR, DISTRIBUIR, REPRODUZIR ou ALTERAR este Programa é
 * preciso estar <BR>
 * de acordo com os termos da LPG-PC <BR>
 * <BR>
 * 
 * Comentários sobre a classe...
 *  
 */

package org.freedom.modulos.std;

import java.awt.BorderLayout;
import java.awt.ScrollPane;

import javax.swing.JScrollPane;

import org.freedom.componentes.JTextAreaPad;
import org.freedom.telas.FFDialogo;

public class FObsCliVend extends FFDialogo {
	public JTextAreaPad txaObs = new JTextAreaPad();
	private JScrollPane spObs = new JScrollPane(txaObs);
	public FObsCliVend() {
		super();
		txaObs.setEditable(false);
		c.add(spObs, BorderLayout.CENTER);
		btOK.requestFocus();
	}
	public static void showVend(int x, int y, int larg, int alt, String sObsCli) {
		FObsCliVend tela = new FObsCliVend();
		tela.setAtribos(x, y, larg, alt + 50 );
		tela.txaObs.setText(sObsCli);
		tela.show();
		
	}
	
}
