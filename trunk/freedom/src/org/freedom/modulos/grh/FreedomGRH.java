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
		
		addOpcao(-1,TP_OPCAO_MENU,"Arquivo",'A',100000000,0,false);
			addOpcao(100000000,TP_OPCAO_MENU,"Cadastros",'T',100100000,1,false);		    	
	    		addOpcao(100100000,TP_OPCAO_ITEM,"Empregados", 'E',100101000,2,true);		
	    		addOpcao(100100000,TP_OPCAO_ITEM,"Turnos", 'R',100102000,2,true);
	    		addOpcao(100100000,TP_OPCAO_ITEM,"Funçao", 'F',100103000,2,true);
	    		addOpcao(100100000,TP_OPCAO_ITEM,"Departamento", 'D',100104000,2,true);
		        	    				
	    		
	    		addBotao("barraConveniados.gif","Empregados",100101000);
	    			    		
	    		
	    ajustaMenu();
    }

    public void execOpcao(int iOpcao) {
        if (iOpcao==100101000) {
            if (telaPrincipal.temTela("Empregados")==false) {
              FEmpregado tela = new FEmpregado();
              telaPrincipal.criatela("Empregados",tela,con);
            }
        }
        else if (iOpcao==100102000) {
            if (telaPrincipal.temTela("Turnos")==false) {
//              FTurnos tela = new FTurnos();
//              telaPrincipal.criatela("Turnos",tela,con);
            } 
        }  
        else if (iOpcao==100103000) {
            if (telaPrincipal.temTela("Função")==false) {
              FFuncao tela = new FFuncao();
              telaPrincipal.criatela("Função",tela,con);
            } 
        }  
        else if (iOpcao==100104000) {
            if (telaPrincipal.temTela("Departamento")==false) {
              FDepto tela = new FDepto();
              telaPrincipal.criatela("Departamento",tela,con);
            } 
        }  
    } 
    public static void main(String sParams[]) {
		FreedomGRH freedom = new FreedomGRH();
		freedom.show();
    }
}
