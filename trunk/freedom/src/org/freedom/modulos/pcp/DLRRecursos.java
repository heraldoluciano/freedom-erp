/**
 * @version 25/03/2004 <BR>
 * @author Setpoint Informática Ltda./Anderson Sanchez <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.pcp <BR>
 * Classe: @(#)DLRRecursos.java <BR>
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
 * Tela de dialogo para relação de recursos de produção...
 */

package org.freedom.modulos.pcp;
import java.awt.Component;
import java.util.Vector;

import org.freedom.componentes.JLabelPad;

import org.freedom.componentes.JRadioGroup;
import org.freedom.telas.FFDialogo;

public class DLRRecursos extends FFDialogo {
	private static final long serialVersionUID = 1L;
	
  private JRadioGroup rgOrdem = null;
  private JRadioGroup rgTipo = null;
  private JLabelPad lbOrdem = new JLabelPad("Ordenar por:");
  private JLabelPad lbTipo = new JLabelPad("Tipo de impressão:");
  private Vector vLabsOrd = new Vector();
  private Vector vValsOrd = new Vector();
  private Vector vLabsTipo = new Vector();
  private Vector vValsTipo = new Vector();

  public DLRRecursos(Component cOrig) {
  	super(cOrig);
    setTitulo("Ordem do Relatório");
    setAtribos(300,220);
    vLabsOrd.addElement("Código");
    vLabsOrd.addElement("Nome");
    vValsOrd.addElement("C");
    vValsOrd.addElement("N");
    rgOrdem = new JRadioGroup(1,2,vLabsOrd,vValsOrd);
    rgOrdem.setVlrString("N");
    
    vLabsTipo.addElement("Texto");
    vLabsTipo.addElement("Gráfica");
    vValsTipo.addElement("T");
    vValsTipo.addElement("G");
    rgTipo = new JRadioGroup(1,2,vLabsTipo,vValsTipo);
    rgTipo.setVlrString("G");

    adic(lbOrdem,7,5,80,15);
    adic(rgOrdem,7,25,275,30);
    
    adic(lbTipo,7,65,265,15);
    adic(rgTipo,7,85,275,30);

  }
  public Vector getValores() {
  	Vector vRet = new Vector();
	
  	if(rgOrdem.getVlrString().compareTo("C") == 0)
  		vRet.addElement("CODRECP");
  	else if (rgOrdem.getVlrString().compareTo("N") == 0 )
  		vRet.addElement("DESCRECP");
  	if(rgTipo.getVlrString().compareTo("G") == 0)
  		vRet.addElement("G");
  	else if (rgTipo.getVlrString().compareTo("T") == 0 )
  		vRet.addElement("T");
  	return vRet;
  }
}
