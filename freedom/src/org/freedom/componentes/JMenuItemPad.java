/**
 * @version 23/01/2003 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 * Pacote: org.freedom.componentes <BR>
 * Classe: @(#)JMenuItemPad.java <BR>
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
 * Comentários da classe.....
 */

package org.freedom.componentes;
import javax.swing.JMenuItem;
public class JMenuItemPad extends JMenuItem {

	private int iCodSys = 0;
	private int iCodMod = 0;
    private int iCodIt = 0;
    private int iCodNiv = 0;
  
	/**
	*  Construtor da classe JMenuItem(). <BR>
	*
	*/
  
	public JMenuItemPad () {
		this(0,0,0,0);
	}

	/**
	*  Construtor da classe JMenu(). <BR>
	*  Construtor que ja ajusta os paramatros basicos do JMenuPad.
	*
	*/
  
	public JMenuItemPad (int iCodSistema, int iCodModulo, int iCodItem, int iCodNivel) {
		iCodSys = iCodSistema;
		iCodMod = iCodModulo;
		iCodIt = iCodItem;
		iCodNiv = iCodNivel;
	}
	


	/**
	*  Ajusta o código do sistema. <BR>
	*  @param iCod - Código do sistema.
	*  @see #getCodSistema
	*
	*/

	public void setCodSistema(int iCod) {
		iCodSys = iCod;
	}

	/**
	*  Retorna o código do sistema. <BR>
	*  @return Código do sistema ou zero se o código não foi definido.
	*  @see #setCodSistema
	*
	*/


	public int getCodSistema() {
		return iCodSys;
	}

	/**
	*  Ajusta o código do módulo do sistema. <BR>
	*  @param iCod - Código do módulo.
	*  @see #getCodModulo
	*
	*/

	public void setCodModulo(int iCod) {
		iCodMod = iCod;
	}

	/**
	*  Retorna o código do módulo do sistema. <BR>
	*  @return Código do módulo ou zero se o código não foi definido.
	*  @see #setCodModulo
	*
	*/

	public int getCodModulo() {
		return iCodMod;
	}

	/**
	*  Ajusta o código do item. <BR>
	*  O código do item é composto da seguinte forma: <BR>
	*  8 casas decimais (caso casas da direita nao usadas colocar 0)<BR>
	*  e mais uma unidade para detalhamentos longos.
	*  agrupadas de dois em dois, ex: 19|26|03|17|8. <BR>
	*  <BR>
	*  No exemplo o código do menu principal é: 19<BR>
	*  submenu1: 26<BR>
	*  submenu2: 03<BR>
	*  submenu3: 17<BR>
	*  item: 8<BR>
	*  
	*  @param iItem - Código do item.
	*  @see #getCodItem
	*
	*/

	public void setCodItem(int iCod) {
		iCodIt = iCod;
	}

	/**
	*  Retorna o código do item. 
	*  @return Código do item ou zero se o código não foi definido.
	*  @see #setCodItem
	*
	*/

	public int getCodItem() {
		return iCodIt;
	}

	/**
	*  Ajusta o nível do item. <BR>
	*  O nível especifica que nível de detalhe que o<BR>
	*  menu se encontra.
	* 
	*  @param iNivel - Nível do menu pode ser: 1,2,3,4.
	* 
	*  @see #getCodNivel
	*
	*/

	public void setNivel(int iNivel) {
		iCodNiv = iNivel;
	}

	/**
	*  Retorna o nível do item. <BR>
	*  @return Nível.
	*  @see #setNivel
	*
	*/

	public int getCodNivel() {
		return iCodNiv;
	}
}
