/**
 * @version 14/11/2000 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 * Pacote: org.freedom.images <BR>
 * Classe: @(#)Icone.java <BR>
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

package org.freedom.bmps;
import java.awt.Image;
import java.net.URL;

import javax.swing.ImageIcon;
public class Icone {
  public Icone () { }
  public static ImageIcon novo(String nome) {
  	ImageIcon retorno = null;
  	nome = "/org/freedom/images/"+nome;
    URL url = Icone.class.getResource(nome);
    if (url == null) {
    	System.err.println("Não foi possível carregar a imagem: '"+nome+"'");
    }
    else {
        Image img = java.awt.Toolkit.getDefaultToolkit().getImage(url);
        retorno = new ImageIcon(img);
    }
    return retorno;
  }
}
/**
 * @version 14/11/2000 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *         Projeto: Freedom <BR>
 *         Pacote: bmps <BR>
 *         Classe:
 * @(#)Icone.java <BR>
 *                Este programa é licenciado de acordo com a LPG-PC (Licença
 *                Pública Geral para Programas de Computador), <BR>
 *                versão 2.1.0 ou qualquer versão posterior. <BR>
 *                A LPG-PC deve acompanhar todas PUBLICAÇÕES, DISTRIBUIÇÕES e
 *                REPRODUÇÕES deste Programa. <BR>
 *                Caso uma cópia da LPG-PC não esteja disponível junto com este
 *                Programa, você pode contatar <BR>
 *                o LICENCIADOR ou então pegar uma cópia em: <BR>
 *                Licença: http://www.lpg.adv.br/licencas/lpgpc.rtf <BR>
 *                Para poder USAR, PUBLICAR, DISTRIBUIR, REPRODUZIR ou ALTERAR
 *                este Programa é preciso estar <BR>
 *                de acordo com os termos da LPG-PC <BR>
 *                <BR>
 *                Comentários para a classe...
 */

package org.freedom.bmps;

import java.net.URL;

import javax.swing.ImageIcon;

public class Icone {

	public Icone() {}

	public static ImageIcon novo(String nome) {
		ImageIcon retorno = null;
		URL url = Icone.class.getResource("/org/freedom/images/" + nome);

		if (url == null)
			System.out.println("Não foi possível carregar a imagem: '" + nome + "'");
		else
			retorno = new ImageIcon(java.awt.Toolkit.getDefaultToolkit()
					.getImage(url));

		return retorno;
	}
}