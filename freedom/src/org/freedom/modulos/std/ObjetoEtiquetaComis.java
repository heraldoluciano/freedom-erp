/**
 * @version 16/09/2008 <BR>
 * @author Setpoint Informática Ltda./Reginaldo Garcia Heua <BR>
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

/**
 * @author anderson
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ObjetoEtiquetaComis extends ObjetoEtiqueta {

  public ObjetoEtiquetaComis() { 	
	  
      adicOpcao("Código do comissionado","#CODIGO_COMIS#","CODCOMIS",new Integer(8),null,"Cód.Comis.");
      adicOpcao("Nome do comissionado","#NOME_COMIS#","NOMECOMIS",new Integer(50),null,"Nome comis.");      
      adicOpcao("CPF ou CNPJ do cliente","#CNPJ#","CNPJCLI",new Integer(18),"##.###.###/####-##","Cnpj");
      adicOpcao("Endereço do cliente","#ENDEREÇO#","ENDCLI",new Integer(50),null,"Endereço");
      adicOpcao("Endereço de cobrança","#ENDCOB#","(COALESCE(ENDCOB,ENDCLI))",new Integer(50),null,"Endereço");
      adicOpcao("Endereço de entrega","#ENDENT#","(COALESCE(ENDENT,ENDCLI))",new Integer(50),null,"Endereço ent.");
      adicOpcao("Complemento","#COMPLCLI#","COMPLCLI",new Integer(50),null,"Complemento");
      adicOpcao("Complemento end cob.","#COMPLCOB#","CASE WHEN ENDCOB IS NULL THEN COMPLCLI ELSE COMPLCOB END AS COMPLEMENTOC",new Integer(20),null,"Complemento cob.");
      adicOpcao("Complemento end ent.","#COMPLENT#","CASE WHEN ENDENT IS NULL THEN COMPLCLI ELSE COMPLENT END AS COMPLEMENTOE",new Integer(20),null,"Complemento cob.");
      adicOpcao("Número","#NUMERO#","NUMCLI",new Integer(20),null,"Nro.");
      adicOpcao("Número end. cob.","#NUMCOB#","CASE WHEN ENDCOB IS NULL THEN NUMCLI ELSE NUMCOB END AS NUMEROC",new Integer(20),null,"Nro.");
      adicOpcao("Número end. ent.","#NUMENT#","CASE WHEN ENDENT IS NULL THEN NUMCLI ELSE NUMENT END AS NUMEROE",new Integer(20),null,"Nro.");
      adicOpcao("CEP","#CEP#","CEPCLI",new Integer(9),"#####-###","Cep");
      adicOpcao("CEP cob.","#CEPCOB#","CASE WHEN ENDCOB IS NULL THEN CEPCLI ELSE CEPCOB END AS CEPC",new Integer(20),null,"CEP cob.");
      adicOpcao("CEP ent.","#CEPENT#","CASE WHEN ENDENT IS NULL THEN CEPCLI ELSE CEPENT END AS CEPE",new Integer(20),null,"CEP ent.");
      adicOpcao("Bairro do cliente","#BAIRRO#","BAIRCLI",new Integer(30),null,"Bairro");
      adicOpcao("Bairro end. cob.","#BAIRCOB#","CASE WHEN ENDCOB IS NULL THEN BAIRCLI ELSE BAIRCOB END AS BAIRC",new Integer(20),null,"Bairro cob.");
      adicOpcao("Bairro end. ent.","#BAIRENT#","CASE WHEN ENDENT IS NULL THEN BAIRCLI ELSE BAIRENT END AS BAIRE",new Integer(20),null,"Bairro ent.");
      adicOpcao("Cidade do cliente","#CIDADE#","CIDCLI",new Integer(30),null,"Cidade");
      adicOpcao("Cidade end. cob.","#CIDCOB#","CASE WHEN ENDCOB IS NULL THEN CIDCLI ELSE CIDCOB END AS CIDC",new Integer(20),null,"Cidade cob.");
      adicOpcao("Cidade end. ent.","#CIDENT#","CASE WHEN ENDENT IS NULL THEN CIDCLI ELSE CIDENT END AS CIDE",new Integer(20),null,"Cidade ent.");
      adicOpcao("UF do cliente","#UF#","UFCLI",new Integer(2),null,"UF");
      adicOpcao("UF end. cob.","#UFCOB#","CASE WHEN ENDCOB IS NULL THEN UFCLI ELSE UFCOB END AS UFC",new Integer(20),null,"UF cob.");
      adicOpcao("UF end. ent.","#UFENT#","CASE WHEN ENDENT IS NULL THEN UFCLI ELSE UFENT END AS UFE",new Integer(20),null,"UF ent.");      
      adicOpcao("Contato do cliente","#CONTATO#","CONTCLI",new Integer(40),null,"Contato");
      
  }
}
