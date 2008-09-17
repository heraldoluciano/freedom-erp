/**
 * @version 16/09/2008 <BR>
 * @author Setpoint Informática Ltda.
 * @author Reginaldo Garcia Heua <BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.std <BR>
 * Classe:
 * @(#)ObjetoEtiquetaComis.java <BR>
 * 
 * Este programa é licenciado de acordo com a LPG-PC (Licença Pública Geral para Programas de Computador), <BR>
 * versão 2.1.0 ou qualquer versão posterior. <BR>
 * A LPG-PC deve acompanhar todas PUBLICAÇÕES, DISTRIBUIÇÕES e REPRODUÇÕES deste Programa. <BR>
 * Caso uma cópia da LPG-PC não esteja disponível junto com este Programa, você pode contatar <BR>
 * o LICENCIADOR ou então pegar uma cópia em: <BR>
 * Licença: http://www.lpg.adv.br/licencas/lpgpc.rtf <BR>
 * Para poder USAR, PUBLICAR, DISTRIBUIR, REPRODUZIR ou ALTERAR este Programa é preciso estar <BR>
 * de acordo com os termos da LPG-PC <BR>
 * <BR>
 */
package org.freedom.modulos.std;

import org.freedom.componentes.ObjetoEtiqueta;

public class ObjetoEtiquetaComis extends ObjetoEtiqueta {

  public ObjetoEtiquetaComis() { 	
	  
      adicOpcao("Código do vendedor","#CODIGO_VEND#","CODVEND",new Integer(8),null,"Cód.Vend.");
      adicOpcao("Nome do vendedor","#NOME#","NOMEVEND",new Integer(40),null,"Nome Vend.");      
      adicOpcao("Endereço do Vendedor","#ENDEREÇO#","ENDVEND",new Integer(50),null,"Endereço");
      adicOpcao("Número","#NUMERO#","NUMVEND",new Integer(20),null,"Nro.");
      adicOpcao("Complemento","#COMPL_VEND#","COMPLCLI",new Integer(50),null,"Complemento");
      adicOpcao("Bairro do vendedor","#BAIR_VEND#","BAIVEND",new Integer(30),null,"Bairro");
      adicOpcao("Cidade do vendedor","#CIDADE_VEND#","CIDVEND",new Integer(30),null,"Cidade");
      adicOpcao("CEP","#CEP_VEND#","CEPVEND",new Integer(9),"#####-###","Cep");
      adicOpcao("UF do Vendedor","#UF#","UFVEND",new Integer(2),null,"UF");
      adicOpcao("Telefone","#FONE_VEND#","FONDEVEND",new Integer(12),"####-####","Fone");    
      adicOpcao("Email","#EMAIL_VEND","EMAILVEND",new Integer(50),null,"Email"); 
      
      setNometabela( "VDVENDEDOR" );
      
  }
}
