/**
 * @version 17/06/2005 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 * Pacote: org.freedom.componentes <BR>
 * Classe: @(#)Tabela.java <BR>
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
 * Objeto para guardar as informações necessárias para a criação e utilização de modelos de lote.
 */

package org.freedom.componentes;

import java.util.Vector;

import org.freedom.funcoes.Funcoes;

public abstract class ObjetoModeloLote {
  private Vector vLabels = new Vector();
  private Vector vLabelsAdic = new Vector();
  private Vector vLabelsColunas = new Vector();
  private Vector vValores = new Vector();
  private Vector vValoresAdic = new Vector();
  private Vector vTams = new Vector();
  private Vector vTamsAdic = new Vector();
  private Vector vMascaras = new Vector();
  private String sTexto = "";

  public ObjetoModeloLote() { 

  }
  
  public void adicOpcao(String sLabel,String sValor,Integer iTam){
      vLabels.addElement(sLabel);
      vValores.addElement(sValor);
      vTams.addElement(iTam);
  }

/**
 * @return Returns the vLabels.
 */
public Vector getLabels() {
    return vLabels;
}
/**
 * @return Returns the vLabels.
 */
public Vector getLabelsAdic() {
    return vLabelsAdic;
}
/**
 * @return Returns the vTams.
 */
public Vector getTams() {
    return vTams;
}
/**
 * @return Returns the vTamsAdic.
 */
public Vector getTamsAdic() {
    return vTamsAdic;
}

/**
 * @return Returns the vValores.
 */
public Vector getValores() {
    return vValores;
}
/**
 * @return Returns the vValoresAdic.
 */
public Vector getValoresAdic() {
    return vValoresAdic;
}
/**
 * @return Returns the vMascaras.
 */
public Vector getMascaras() {
    return vMascaras;
}
public Vector getLabelsColunas() {
    return vLabelsColunas;
}
public void setTexto(String sTexto){
    this.sTexto = sTexto;
    getAdic();    
}
public int getNumLinEtiq(){
    int iRet = 0;
    try {
       iRet = Funcoes.stringToVector(sTexto).size();   
    }
    catch(Exception e) {
        e.printStackTrace();
    }    
    return iRet;
}
public void getAdic(){
        vTamsAdic = new Vector();
        vLabelsAdic = new Vector ();
        vValoresAdic = new Vector();   

        for(int i2=0;vValores.size()>i2;i2++) {
            if((sTexto.indexOf(vValores.elementAt(i2).toString()))>(-1)){
                vTamsAdic.addElement(vTams.elementAt(i2).toString());
                vLabelsAdic.addElement(vLabels.elementAt(i2).toString());
                vValoresAdic.addElement(vValores.elementAt(i2).toString());
            }                                 
        }       
}
public String getLote(Integer iCodProd){
	String sLote = "";
	
	
	
	
	
	
	return sLote;
}
}