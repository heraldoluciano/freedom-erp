/**
 * @version 19/09/2003 <BR>
 * @author Setpoint Informática Ltda./Robson Sanchez <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.tmk <BR>
 * Classe: @(#)FreedomTMK.java <BR>
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
 * Tela principal do módulo telemarketing.
 * 
 */

package org.freedom.modulos.tmk;

import org.freedom.modulos.atd.FAtendente;
import org.freedom.modulos.atd.FTipoAtend;
import org.freedom.modulos.std.FClasCli;
import org.freedom.modulos.std.FCliente;
import org.freedom.modulos.std.FSetor;
import org.freedom.modulos.std.FTipoCli;
import org.freedom.modulos.std.FVendedor;
import org.freedom.telas.Aplicativo;


public class FreedomTMK extends Aplicativo {

	public FreedomTMK() {
		super("iconAtendimento32.gif","splashTMK.jpg","FreedomTMK - Módulo de telemarketing FREEDOM",1,7);
		addOpcao(-1, TP_OPCAO_MENU, "Arquivo", 'A' ,100000000, 0, false);
		
		  addOpcao(100000000,TP_OPCAO_MENU,"Tabelas",'T',100100000,1,false);
		  
		    addOpcao(100100000,TP_OPCAO_MENU,"Contato",'C',1001010000,2,false);		   
		      addOpcao(1001010000,TP_OPCAO_ITEM,"Atividade",'A',100101010,3,true);
		      addOpcao(1001010000,TP_OPCAO_ITEM,"Contato",'C',100101020,3,true);
		       
		    addOpcao(100100000,TP_OPCAO_MENU,"Atendente",'A',100102000,2,false);
  			  addOpcao(100102000,TP_OPCAO_ITEM,"Tipo de Atendente",'T',100102010,3,true);
			  addOpcao(100102000,TP_OPCAO_ITEM,"Atendentes",'A',100110020,3,true);
		      
		    addOpcao(100100000,TP_OPCAO_MENU,"Cliente",'C',100104000,2,false);
			  addOpcao(100104000,TP_OPCAO_ITEM,"Setor", 'S', 100104010,3,true);
			  addOpcao(100104000,TP_OPCAO_ITEM,"Vendedor", 'V', 100104020,3,true);
			  addOpcao(100104000,TP_OPCAO_ITEM,"Tipos de cliente", 'T', 100104030,3,true);
			  addOpcao(100104000,TP_OPCAO_ITEM,"Classificação de cliente", 'l', 100104040,3,true);
		      addSeparador(100104000);
			  addOpcao(100104000,TP_OPCAO_ITEM,"Cliente", 'C', 100104050,3,true);
			  addSeparador(100104000);
		
		  addOpcao(100000000, TP_OPCAO_MENU, "Preferências", 'P',100200000, 1, false);
		    addOpcao(100200000, TP_OPCAO_ITEM, "Preferências Gerais...", 'G', 100201000,2, true );
		    addOpcao(100000000, TP_OPCAO_MENU, "Ferramentas",'F',100300000, 1, false);
		    addOpcao(100300000, TP_OPCAO_ITEM, "Importação de Contatos",'I',100301000,2,true);
		    addOpcao(100300000, TP_OPCAO_ITEM, "Cadastro de layout",'C',100302000,2,true);
		    addOpcao(100300000, TP_OPCAO_ITEM, "Envio de e-mail aos contatos",'E',100303000,2,true);
		
	    addOpcao(-1,TP_OPCAO_MENU,"Contatos",'C',200000000,0,false);
	      addOpcao(200000000,TP_OPCAO_ITEM,"Histórico",'H',200100000,1,true);
	      addOpcao(200000000,TP_OPCAO_ITEM,"Agenda",'A',200200000,1,true);
	      addOpcao(200000000,TP_OPCAO_MENU,"Listagens",'L',200300000,1,false);
	      addOpcao(200300000,TP_OPCAO_ITEM,"Relatório diário",'R',200301000,1,true);
	      
		addBotao("btAtendimento.gif","Atendimento",100110020);
	      
	    ajustaMenu();
    }

    public void execOpcao(int iOpcao) {
        if (iOpcao==100102010) {
			if (telaPrincipal.temTela("Tipo de atendente")==false) {
			  FTipoAtend tela = new FTipoAtend();
			  telaPrincipal.criatela("Tipo de atendente",tela,con);
			}
		}
		else if (iOpcao==100110020) {
			if (telaPrincipal.temTela("Atendente")==false) {
			  FAtendente tela = new FAtendente();
			  telaPrincipal.criatela("Atendente",tela,con);
			}
		}
		else if (iOpcao==100104010) {
			if (telaPrincipal.temTela("Setor")==false) {
			  FSetor tela = new FSetor();
			  telaPrincipal.criatela("Setor",tela,con);
			}
		}
		else if (iOpcao==100104020) {
			if (telaPrincipal.temTela("Vendedor")==false) {
			  FVendedor tela = new FVendedor();
			  telaPrincipal.criatela("Vendedor",tela,con);
			}
		}
		else if (iOpcao==100104030) {
			if (telaPrincipal.temTela("Tipo de cliente")==false) {
			  FTipoCli tela = new FTipoCli();
			  telaPrincipal.criatela("Tipo de cliente",tela,con);
			}
		}
		else if (iOpcao==100104040) {
			if (telaPrincipal.temTela("Classificação do cliente")==false) {
			  FClasCli tela = new FClasCli();
			  telaPrincipal.criatela("Classificação do cliente",tela,con);
			}
		}
		else if (iOpcao==100104050) {
			if (telaPrincipal.temTela("Clientes")==false) {
			  FCliente tela = new FCliente();
			  telaPrincipal.criatela("Clientes",tela,con);
			}
		}
		else if (iOpcao==100101010) {
			if (telaPrincipal.temTela("Atividade")==false) {
		  	    FAtividade tela = new FAtividade();
				telaPrincipal.criatela("Atividade",tela,con);
			 }
   		}
		else if (iOpcao==100101020) {
			if (telaPrincipal.temTela("Contatos")==false) {
				FContato tela = new FContato();
				tela.setTelaPrim(telaPrincipal);
				telaPrincipal.criatela("Contatos",tela,con);
			 }
		}
		else if (iOpcao==100301000) {
			if (telaPrincipal.temTela("Importação de contatos")==false) {
			  FImportaCto tela = new FImportaCto();
			  tela.setConexao(con);
			  telaPrincipal.criatela("Importação de contatos",tela);
			}
		}
		else if (iOpcao==100302000) {
			if (telaPrincipal.temTela("Cadastro de layout")==false) {
				FTipoImp tela = new FTipoImp();
				telaPrincipal.criatela("Cadastro de layout",tela,con);
			 }
		}
		else if (iOpcao==100303000) {
			if (telaPrincipal.temTela("Envia e-mail")==false) {
			  FEnviaMail tela = new FEnviaMail();
			  tela.setConexao(con);
			  tela.setTelaPrim(telaPrincipal);
			  telaPrincipal.criatela("Envia e-mail",tela);
			}
		}
		else if (iOpcao == 100201000) {
		  if (telaPrincipal.temTela("Pref. Gerais")==false) {
			FPrefere tela = new FPrefere();
			telaPrincipal.criatela("Pref. Garais",tela,con);
		  }
		}
		else if (iOpcao==200100000) {
			if (telaPrincipal.temTela("Historico")==false) {
				FHistorico tela = new FHistorico();
				tela.setConexao(con);
				telaPrincipal.criatela("Historico",tela);
			} 
		}
		else if (iOpcao==200200000) {
			if (telaPrincipal.temTela("Agenda")==false) {
				FAgenda tela = new FAgenda();
				tela.setConexao(con);
				telaPrincipal.criatela("Agenda",tela);
			} 
		}
		else if (iOpcao==200301000) {
			if (telaPrincipal.temTela("Relatório diário")==false) {
				FRDiario tela = new FRDiario();
				tela.setConexao(con);
				telaPrincipal.criatela("Relatório diário",tela);
			} 
		}
        } 
    public static void main(String sParams[]) {
		FreedomTMK fFreedomtmk = new FreedomTMK();
		fFreedomtmk.show();
    }
}
