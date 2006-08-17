/**
 * @version 14/11/2000 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 * 
 * Projeto: Freedom <BR>
 * Pacote: org.freedom.telas <BR>
 * Classe:
 * @(#)FPrincipal.java <BR>
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
 * Comentários para a classe...
 */

package org.freedom.telas;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

import org.freedom.bmps.Icone;

public class FPrincipal2 extends FPrincipal implements ActionListener, MouseListener {
	private static final long serialVersionUID = 1L;
	
	public FPrincipal2(String sImgFundo) {
		super(null, sImgFundo);
	}

	public void inicializaTela() {
		setBgColor(new Color(255,255,255)); 
		addFundo();
	    addLinks(Icone.novo("lgSTP2.jpg"), Icone.novo("lgFreedom2.jpg"));
		adicBotoes();
		adicAgenda();
	}

}