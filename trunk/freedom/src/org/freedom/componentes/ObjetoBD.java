/**
 * @version 23/01/2003 <BR>
 * @author Setpoint Informática Ltda./Robson Sanchez <BR>
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

public class ObjetoBD {
  private String sIDObj = "";
  private String sDescObj = "";
  private String sTipoObj = "";
  private String sComentObj = "";
  private String sUsomeObj = "";
  
  public ObjetoBD(String sIDObj,  
         String sDescObj, String sTipoObj,String sComentObj,
         String sUsomeObj ) {
  	this.sIDObj = sIDObj;
  	this.sDescObj = sDescObj;
  	this.sComentObj = sComentObj;
  	this.sUsomeObj = sUsomeObj;
  	this.sTipoObj = sTipoObj;
  	
  }

  public String getIDObj() {
    return this.sIDObj;
  }  
  
  
  public String getDescObj() {
    return this.sDescObj;
  }

  public String getTipoObj() {
    return this.sTipoObj;
  }
  
  public String setComentObj() {
    return this.sComentObj;
  }

  public String getUsomeObj() {
    return this.sUsomeObj;
  }
  
  public void setIDObj(String sIDObj) {
    this.sIDObj = sIDObj;
  }  
  
  public void setDescObj(String sDescObj ) {
    this.sDescObj = sDescObj;
  }
  
  public void setTipoObj(String sTipoObj ) {
    this.sTipoObj = sTipoObj;
  }
  
  public void setComentObj(String sComentObj) {
    this.sComentObj = sComentObj;
  }

  public void setUsomeObj(String sUsomeObj) {
    this.sUsomeObj = sUsomeObj;
  }
  

}
