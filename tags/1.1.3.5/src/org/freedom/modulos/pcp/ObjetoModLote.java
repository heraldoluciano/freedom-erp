/*
 * Created on 17/06/2005
 * Autor: anderson 
 * Descrição:  
 */
package org.freedom.modulos.pcp;

import org.freedom.componentes.ObjetoModeloLote;

/**
 * @author anderson
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ObjetoModLote extends ObjetoModeloLote {

  public ObjetoModLote() { 	
      adicOpcao("Código do produto",VLR_CODPROD,new Integer(8));
      adicOpcao("Dia",VLR_DIA,new Integer(2));
      adicOpcao("Mês",VLR_MES,new Integer(2));
      adicOpcao("Ano",VLR_ANO,new Integer(4));
      adicOpcao("Número da produção no dia",VLR_NPRODDIA,new Integer(5));                  
  }
}
