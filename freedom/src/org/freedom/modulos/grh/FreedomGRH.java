/**
 * @version 30/01/2004 <BR>
 * @author Setpoint Informática Ltda./Anderson Sanchez <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.grh <BR>
 * Classe: @(#)FreedomGRH.java <BR>
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
 * Tela principal para o módulo de gestão de recursos humanos.
 * 
 */

package org.freedom.modulos.grh;

import org.freedom.telas.Aplicativo;

public class FreedomGRH extends Aplicativo {

	public FreedomGRH() {
		super("iconAtendimento32.gif","splashGRH.jpg","FreedomGMS - Módulo de gerenciamento de mateirais e serviços",1,8);      		
		
		addOpcao(-1,TP_OPCAO_MENU,"Arquivo","",'A',100000000,0, false, null);
			addOpcao(100000000,TP_OPCAO_MENU,"Cadastros","",'T',100100000,1, false, null);		    	
	    		addOpcao(100100000,TP_OPCAO_ITEM,"Empregados", "Empregados",'E',100101000,2, true, FEmpregado.class );		
	    		addOpcao(100100000,TP_OPCAO_ITEM,"Turnos", "Turnos",'R',100102000,2, true, FTurnos.class);
	    		addOpcao(100100000,TP_OPCAO_ITEM,"Funçao", "Função",'F',100103000,2, true, FFuncao.class);
	    		addOpcao(100100000,TP_OPCAO_ITEM,"Departamento", "Departamento",'D',100104000,2, true, FDepto.class);
		        	    				
	    		
	    		addBotao("barraConveniados.gif","Empregados",100101000);
	    			    		
	    		
	    ajustaMenu();
    }

	public static void main(String sParams[]) {
		FreedomGRH freedom = new FreedomGRH();
		freedom.show();
    }
}