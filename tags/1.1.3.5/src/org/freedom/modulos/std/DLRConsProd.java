/**
 * @version 14/07/2003 <BR>
 * @author Setpoint Informática Ltda./Marco Antonio Sanchez <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: projetos.freedom <BR>
 * Classe: @(#)DLRConsProd.java <BR>
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
import java.awt.Component;
import java.util.Vector;
import org.freedom.componentes.JLabelPad;

import org.freedom.componentes.JRadioGroup;
import org.freedom.telas.FFDialogo;




public class DLRConsProd extends FFDialogo {

	private static final long serialVersionUID = 1L;

  private JRadioGroup rgOrdem = null;

  private Vector vLabs = new Vector();
  private Vector vVals = new Vector();
  
  private JLabelPad lbOrdem = new JLabelPad("Listar:");
  
  public DLRConsProd(Component cOrig) {
  	super(cOrig);
    setTitulo("Relatório de Produtos  ");
    setAtribos(250,240);


    vLabs.addElement("Fornecedores");
    vLabs.addElement("Compras");
    vLabs.addElement("Vendas");
    vVals.addElement("F");
    vVals.addElement("C");
    vVals.addElement("V");
    
    rgOrdem = new JRadioGroup(3,1,vLabs,vVals);
    
    
    
    adic(lbOrdem,7,15,150,15);
    adic(rgOrdem,7,40,220,100);
 
  }

  
  public String getValores() {
    String sRetorno = "";
       
    sRetorno = rgOrdem.getVlrString();
    
	return sRetorno;

  }
  
}
