/**
 * @version 14/07/2003 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)DLRProduto.java <BR>
 * 
 * Este arquivo é parte do sistema Freedom-ERP, o Freedom-ERP é um software livre; você pode redistribui-lo e/ou <BR>
 * modifica-lo dentro dos termos da Licença Pública Geral GNU como publicada pela Fundação do Software Livre (FSF); <BR>
 * na versão 2 da Licença, ou (na sua opnião) qualquer versão. <BR>
 * Este programa é distribuido na esperança que possa ser  util, mas SEM NENHUMA GARANTIA; <BR>
 * sem uma garantia implicita de ADEQUAÇÂO a qualquer MERCADO ou APLICAÇÃO EM PARTICULAR. <BR>
 * Veja a Licença Pública Geral GNU para maiores detalhes. <BR>
 * Você deve ter recebido uma cópia da Licença Pública Geral GNU junto com este programa, se não, <BR>
 * escreva para a Fundação do Software Livre(FSF) Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA <BR> <BR>
 *
 * Comentários sobre a classe...
 */

package org.freedom.modulos.pcp;
import java.util.Vector;

import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JRadioGroup;
import org.freedom.telas.FFDialogo;
public class DLREstrutura extends FFDialogo {
	private static final long serialVersionUID = 1L;
	
  private JRadioGroup<?, ?> rgOrdem = null;
  private JRadioGroup<?, ?> rgModo = null;
  private JRadioGroup<?, ?> rgEstrut = null;
  private JLabelPad lbModo = new JLabelPad("Modo do Relatório:");
  private JLabelPad lbEstrut = new JLabelPad("Estrutura:");
  private JLabelPad lbOrdem = new JLabelPad("Ordenado por:");
  private Vector<String> vLabs = new Vector<String>();
  private Vector<String> vVals = new Vector<String>();
  private Vector<String> vLabsModo = new Vector<String>();
  private Vector<String> vValsModo = new Vector<String>();
  private Vector<String> vLabsEstrut = new Vector<String>();
  private Vector<String> vValsEstrut = new Vector<String>();
  
  public DLREstrutura() {	
    setTitulo("Relatório de Estrutura de Produtos");
    setAtribos(295,280);
    
    vLabsEstrut.addElement("Atual");
    vLabsEstrut.addElement("Todas");
    vValsEstrut.addElement("A");
    vValsEstrut.addElement("T");
    rgEstrut = new JRadioGroup<String, String>(1,2,vLabsEstrut,vValsEstrut);
    rgEstrut.setVlrString("T");
    
    vLabsModo.addElement("Resumido");
    vLabsModo.addElement("Completo");
    vValsModo.addElement("R");
    vValsModo.addElement("C");
    rgModo = new JRadioGroup<String, String>(1,2,vLabsModo,vValsModo);
    rgModo.setVlrString("C");
    
    vLabs.addElement("Código");
    vLabs.addElement("Descrição");
    vVals.addElement("C");
    vVals.addElement("D");
    rgOrdem = new JRadioGroup<String, String>(1,2,vLabs,vVals);
    rgOrdem.setVlrString("D");

    adic(lbEstrut,10,10,70,20);
    adic(rgEstrut,10,30,260,30);
    adic(lbModo,10,70,150,20);
    adic(rgModo,10,90,260,30);
    adic(lbOrdem,10,130,150,20);
    adic(rgOrdem,10,150,260,30);
    
  }
  public String[] getValores() {
    String[] sRetorno = new String[3];
    if (rgOrdem.getVlrString().compareTo("C") == 0 )
      sRetorno[0] = "E.CODPROD";
    else if (rgOrdem.getVlrString().compareTo("D") == 0 )
    sRetorno[0] = "PD.DESCPROD";
    sRetorno[1] = rgModo.getVlrString();
    sRetorno[2] = rgEstrut.getVlrString();
    
    return sRetorno;
  }
}
