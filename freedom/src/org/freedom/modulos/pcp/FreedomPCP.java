/**
 * @version 01/09/2003 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.pcp <BR>
 * Classe: @(#)FreedomPCP.java <BR>
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
 * Tela principal do módulo de produção.
 * 
 */

package org.freedom.modulos.pcp;
import java.awt.event.ActionListener;

import org.freedom.modulos.std.FAlmox;
import org.freedom.modulos.std.FCLFiscal;
import org.freedom.modulos.std.FGrupo;
import org.freedom.modulos.std.FMarca;
import org.freedom.modulos.std.FProduto;
import org.freedom.modulos.std.FUnidade;
import org.freedom.telas.Aplicativo;


public class FreedomPCP extends Aplicativo implements ActionListener {
  public FreedomPCP() {
		
	super("iconAtendimento32.gif","splashPCP.jpg","FreedomPCP - Modulo de planejamento e controle de produção FREEDOM",1,5);
	addOpcao(-1,TP_OPCAO_MENU,"Arquivo","",'A',100000000,0, false, null);
	addOpcao(100000000,TP_OPCAO_MENU,"Tabelas","",'T',100100000,1, false, null);
	addOpcao(100000000,TP_OPCAO_MENU,"Preferências","",'F',110100000,1, false, null);
	 
	addOpcao(100100000,TP_OPCAO_MENU,"Produtos","",'P',100101000,2, false, null);
	addOpcao(100101000,TP_OPCAO_ITEM,"Classificação fiscal","",'l',100120020,3, true, null);
    addOpcao(100101000,TP_OPCAO_ITEM,"Almoxarifado","",'x',100120030,3, true, null);
    addOpcao(100101000,TP_OPCAO_ITEM,"Grupo","",'r',100120040,3, true, null);
    addOpcao(100101000,TP_OPCAO_ITEM,"Marca","",'c',100120050,3, true, null);
    addOpcao(100101000,TP_OPCAO_ITEM,"Unidade","",'U',100120060,3, true, null);
    addOpcao(100101000,TP_OPCAO_ITEM,"Produto","",'P',100120070,3, true, null);
	addBotao("btProduto.gif","Produtos",100101000);
	addSeparador(100100000);
	addOpcao(100100000,TP_OPCAO_ITEM,"Estrutura","",'E',100102000,2, true, null);
	addOpcao(100100000,TP_OPCAO_ITEM,"Tipos de recursos","",'T',100103000,2, true, null);
	addOpcao(100100000,TP_OPCAO_ITEM,"Recursos de produção","",'R',100104000,2, true, null);
	addOpcao(100100000,TP_OPCAO_ITEM,"Fases de produção","",'F',100105000,2, true, null);

	addBotao("btEstProduto.gif","Estrutura de produto",100102000);
		
	addOpcao(110100000,TP_OPCAO_ITEM,"Preferências gerais","",'G',110101000,2, true, null);

	addOpcao(-1,TP_OPCAO_MENU,"Produção","",'P',200000000,0, false, null);
	addOpcao(200000000,TP_OPCAO_ITEM,"Ordens de produção","",'O',200100000,1, true, null);
	
	ajustaMenu();
  }
  public void execOpcao(int iOpcao) {
	if (iOpcao==110101000){
	  if (telaPrincipal.temTela("Preferências gerais")==false) {
        FPrefereProd tela = new FPrefereProd();
	    telaPrincipal.criatela("Preferências gerais",tela,con);
	  }
	}
	else if (iOpcao==100102000){
	  if (telaPrincipal.temTela("Estrutura de produto")==false) {
	    FEstrutura tela = new FEstrutura();
	    tela.setTelaPrim(telaPrincipal); 
	    telaPrincipal.criatela("Estrutura de produto",tela,con);
	  }
	}
	else if (iOpcao==100120070){
	  if (telaPrincipal.temTela("Produto")==false) {
	    FProduto tela = new FProduto();
	    telaPrincipal.criatela("Produto",tela,con);
	  }
	}
	else if (iOpcao==100120060){
		  if (telaPrincipal.temTela("Unidade")==false) {
		    FUnidade tela = new FUnidade();
		    telaPrincipal.criatela("Unidade",tela,con);
	  }
    }
	else if (iOpcao==100120050){
		  if (telaPrincipal.temTela("Marca")==false) {
		    FMarca tela = new FMarca();
		    telaPrincipal.criatela("Marca",tela,con);
	  }
    }
	else if (iOpcao==100120040){
		  if (telaPrincipal.temTela("Grupo")==false) {
		    FGrupo tela = new FGrupo();
		    //tela.setConexao(con);
		    telaPrincipal.criatela("Grupo",tela,con);
	  }
    }
	else if (iOpcao==100120030){
		  if (telaPrincipal.temTela("Almoxarifado")==false) {
		    FAlmox tela = new FAlmox();
		    telaPrincipal.criatela("Almoxarifado",tela,con);
	  }
  }
	else if (iOpcao==100120020){
		  if (telaPrincipal.temTela("Classificação Fiscal")==false) {
		    FCLFiscal tela = new FCLFiscal();
            tela.setTelaPrim(telaPrincipal);
		    telaPrincipal.criatela("Classificação Fiscal",tela,con);
	  }
  }
	else if (iOpcao==100103000){
		if (telaPrincipal.temTela("Tipos de recursos")==false) {
			FTipoRec tela = new FTipoRec();
			telaPrincipal.criatela("Tipos de recursos",tela,con);
		}
	}	
	else if (iOpcao==100104000){
		if (telaPrincipal.temTela("Recursos de produção")==false) {
			FRecursos tela = new FRecursos();
			telaPrincipal.criatela("Recursos de produção",tela,con);
		}
	}
	else if (iOpcao==100105000){
		if (telaPrincipal.temTela("Fases de produção")==false) {
			FFase tela = new FFase();
			telaPrincipal.criatela("Fases de produção",tela,con);
		}
	}
	else if (iOpcao==200100000){
		if (telaPrincipal.temTela("Ordens de produção")==false) {
			FOP tela = new FOP();
            tela.setTelaPrim(telaPrincipal);
			telaPrincipal.criatela("Ordens de produção",tela,con);
		}
	}
	
	
  }
  public static void main(String sParams[]) {
		FreedomPCP freedom = new FreedomPCP();
		freedom.show();
  }
}