/**
 * @version 07/01/2003 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.cfg <BR>
 * Classe: @(#)FreedomCFG.java <BR>
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
 * Tela principal do módulo configurador.
 * 
 */

package org.freedom.modulos.cfg;

import java.sql.Connection;

import org.freedom.telas.Aplicativo;



public class FreedomCFG extends Aplicativo {

  private Connection conIB = null;
  public FreedomCFG() {
	 super("iconConfiguracao32.gif","splashConf.jpg","FreedomCFG - Configuração do sitema FREEDOM",1,2);
      addOpcao(-1,TP_OPCAO_MENU,"Arquivo",'A',100000000,0,false);
      addOpcao(100000000,TP_OPCAO_MENU,"Tabelas",'T',100100000,1,false);
        addOpcao(100100000,TP_OPCAO_MENU,"Acesso ao sistema",'A',100101000,2,false);
          addOpcao(100101000,TP_OPCAO_ITEM,"Grupos",'G',100101010,3,true);
          addOpcao(100101000,TP_OPCAO_ITEM,"Usuarios",'U',100101020,3,true);
	      addOpcao(100101000,TP_OPCAO_ITEM,"Acesso",'A',100101030,3,true);
	      addOpcao(100101000,TP_OPCAO_ITEM,"Menu",'M',100101040,3,true);
	    addOpcao(100100000,TP_OPCAO_MENU,"Clientes/Conveniados",'C',100102000,2,false);
 	      addOpcao(100102000,TP_OPCAO_ITEM,"Grau de instrução",'G',100102010,3,true);
 	      addOpcao(100102000,TP_OPCAO_ITEM,"Paises",'P',100102020,3,true);
	    addOpcao(100100000,TP_OPCAO_MENU,"Objetos",'O',100103000,2,false);
	      addOpcao(100103000,TP_OPCAO_ITEM,"Tabela",'T',100103010,3,true);
	      addOpcao(100103000,TP_OPCAO_ITEM,"Objetos aux.",'O',100103020,3,true);
	    addOpcao(100100000,TP_OPCAO_MENU,"Fluxos",'F',100104000,2,false);
	      addOpcao(100104000,TP_OPCAO_ITEM,"Tarefas",'T',100104090,3,true);
	      addOpcao(100104000,TP_OPCAO_ITEM,"Processos",'P',100104100,3,true);
	      addOpcao(100104000,TP_OPCAO_ITEM,"Fluxos",'F',100104110,3,true);
      
	  addBotao("barraGrupo.gif", "Cadastro de Grupos",100101010); 
	  addBotao("barraUsuario.gif", "Cadastro de Usuarios",100101020); 
	  addBotao("barraAcesso.gif", "Controle de Acessos",100101030);
	  addBotao("btProcessos.gif", "Controle de processos",100110000);
	  addBotao("btFluxo.gif", "Controle de Fluxos",100111000);
	  
	  ajustaMenu();
	  
 	  conIB = conexaoIB(getParameter("driver"),getParameter("bancocfg")); // Inicia a variável de conexão com o banco interno do interbase
  }


  public void execOpcao(int iOpcao)  {
	if (iOpcao==100101010) {
	  if (telaPrincipal.temTela("Grupos")==false) {
		FGrupo tela = new FGrupo();
		telaPrincipal.criatela("Grupos",tela,con);
	  } 
	}
	else if (iOpcao==100101020){
	  if (telaPrincipal.temTela("Usuarios")==false) {
		FUsuario tela = new FUsuario();
		tela.setConexao(conIB);
		telaPrincipal.criatela("Usuarios",tela,con);
	  } 
	}
	else if (iOpcao==100101030){
	  if (telaPrincipal.temTela("Acesso Menu")==false) {
		FAcesso tela = new FAcesso();
		tela.setConexao(con);
		tela.show();
		tela.dispose();
	  } 
	}
	else if (iOpcao==100105000) {
			atualizaMenus();
	}
	else if (iOpcao==100101040) {
	  if (telaPrincipal.temTela("Menu X Objeto")==false) {
		FMenuObj tela = new FMenuObj();
		telaPrincipal.criatela("Menu X Objeto",tela,con);
	  } 
	}
/*	else if (iOpcao==100105000) {
		DLAtualiza tela = new DLAtualiza();
		tela.setConexao(con);
		tela.show();
	}*/
	else if (iOpcao==100102010) {
	  if (telaPrincipal.temTela("Grau de Instrução")==false) {
		FGrauInst tela = new FGrauInst();
		telaPrincipal.criatela("Grau de Instrução",tela,con);
	  }
	}
	else if (iOpcao==100102020) {
	  if (telaPrincipal.temTela("Paises")==false) {
		FPais tela = new FPais();
		telaPrincipal.criatela("Paises",tela,con);
	  }
	}
	else if (iOpcao==100103010) {
	  if (telaPrincipal.temTela("Tabelas auxiliares")==false) {
		FTabela tela = new FTabela();
		telaPrincipal.criatela("Tabelas auxiliares",tela,con);
	  }
	}    
	else if (iOpcao==100104110){
	  if (telaPrincipal.temTela("Cadastro de fluxos")==false) {
		FFluxo tela = new FFluxo();
		telaPrincipal.criatela("Cadastro de fluxos",tela,con);
	  }
	}    
	else if (iOpcao==100103020) {
	  if (telaPrincipal.temTela("Vinculo entre tabelas físicas e auxiliares")==false) {
		FObjetoTb tela = new FObjetoTb();
		telaPrincipal.criatela("Vinculo entre tabelas físicas e auxiliares",tela,con);
	  }
	}    
	else if (iOpcao==100104100){
		if (telaPrincipal.temTela("Processos")==false) {
		  FProcesso tela = new FProcesso();
		  tela.setTelaPrim(telaPrincipal);
		  telaPrincipal.criatela("Processos",tela,con);
		}
	}    
	else if (iOpcao==100104090){
		if (telaPrincipal.temTela("Tarefas")==false) {
			FTarefa tela = new FTarefa();
			telaPrincipal.criatela("Tarefas",tela,con);
		}
	}    
			    
		    
  }

  
  public static void main(String sParams[]) {
		FreedomCFG freedom = new FreedomCFG();
		freedom.show();
  }
}