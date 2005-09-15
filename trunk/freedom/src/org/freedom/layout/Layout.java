/**
 * @version 05/07/2005 <BR>
 * @author Setpoint Informática Ltda./Robson Sanchez <BR>
 *
 * Projeto: Freedom <BR>
 * Pacote: org.freedom.layout <BR>
 * Classe: @(#)Layout.java <BR>
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

package org.freedom.layout;

import org.freedom.componentes.ImprimeOS;
import org.freedom.componentes.NF;
import org.freedom.componentes.TabVector;
public class Layout extends Object {
	//public boolean bEntrada = false;
	protected TabVector cab = null;
	protected TabVector itens = null;
	protected TabVector parc = null;
	protected TabVector adic = null;
	protected TabVector frete = null;
  
public Layout() { }
	public boolean imprimir(NF nf, ImprimeOS imp) {
		cab = nf.getTabVector(NF.T_CAB);
		itens = nf.getTabVector(NF.T_ITENS);
		parc = nf.getTabVector(NF.T_PARC);
		adic = nf.getTabVector(NF.T_ADIC);
		frete = nf.getTabVector(NF.T_FRETE);
		return false;
	}
	
}
