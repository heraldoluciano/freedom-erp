/**
 * @version 29/12/2003 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.fnc <BR>
 * Classe: @(#)FreedomFNC.java <BR>
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
 * Tela principal do módulo financeiro.
 * 
 */

package org.freedom.modulos.fnc;

import org.freedom.modulos.std.FBanco;
import org.freedom.modulos.std.FCentroCusto;
import org.freedom.modulos.std.FClasCli;
import org.freedom.modulos.std.FCliente;
import org.freedom.modulos.std.FConta;
import org.freedom.modulos.std.FEmpresa;
import org.freedom.modulos.std.FFornecedor;
import org.freedom.modulos.std.FImpressora;
import org.freedom.modulos.std.FLanca;
import org.freedom.modulos.std.FManutPag;
import org.freedom.modulos.std.FManutRec;
import org.freedom.modulos.std.FModBoleto;
import org.freedom.modulos.std.FMoeda;
import org.freedom.modulos.std.FPapel;
import org.freedom.modulos.std.FPlanejamento;
import org.freedom.modulos.std.FPlanoPag;
import org.freedom.modulos.std.FProcessaSL;
import org.freedom.modulos.std.FRBalancete;
import org.freedom.modulos.std.FRBalanceteGrafico;
import org.freedom.modulos.std.FRBoleto;
import org.freedom.modulos.std.FRCentroCusto;
import org.freedom.modulos.std.FRExtrato;
import org.freedom.modulos.std.FRFluxoCaixa;
import org.freedom.modulos.std.FRGraficoCC;
import org.freedom.modulos.std.FRInadimplentes;
import org.freedom.modulos.std.FRPagar;
import org.freedom.modulos.std.FRRazaoFin;
import org.freedom.modulos.std.FRReceber;
import org.freedom.modulos.std.FSetor;
import org.freedom.modulos.std.FTerminal;
import org.freedom.modulos.std.FTipoCli;
import org.freedom.modulos.std.FTipoCob;
import org.freedom.modulos.std.FTipoFor;
import org.freedom.modulos.std.FVendedor;
import org.freedom.telas.Aplicativo;


public class FreedomFNC extends Aplicativo {
  public FreedomFNC() {
	super("iconStandart32.gif","splashFNC.jpg","Freedom - Módulo financeiro",1,6);
	addOpcao(-1,TP_OPCAO_MENU,"Arquivo",'A',100000000,0,false);
	  addOpcao(100000000,TP_OPCAO_MENU,"Tabelas",'T',100100000,1,false);
	    addOpcao(100100000,TP_OPCAO_MENU,"Cliente",'C',100101000,2,false);
	      addOpcao(100101000,TP_OPCAO_ITEM,"Setor", 'S',100101010,3,true);
	      addOpcao(100101000,TP_OPCAO_ITEM,"Vendedor", 'V',100101020,3,true);
	      addOpcao(100101000,TP_OPCAO_ITEM,"Tipo de cliente...",'T',100101030,3,true);
	      addOpcao(100101000,TP_OPCAO_ITEM,"Classificação de cliente...", 'f', 100101040,3,true);
	      addOpcao(100101000,TP_OPCAO_ITEM,"Cliente...", 'C',100101050,3,true);
        addSeparador(100100000);
        addOpcao(100100000,TP_OPCAO_ITEM,"Moeda",'M',100102000,2,true);
	    addOpcao(100100000,TP_OPCAO_ITEM,"Banco",'B',100103000,2,true);
	    addOpcao(100100000,TP_OPCAO_ITEM,"Tipo de cobrança",'o',100104000,2,true);
	    addOpcao(100100000,TP_OPCAO_ITEM,"Plano de pagamento",'s',100105000,2,true);
	    addSeparador(100100000);	    
	    addOpcao(100100000,TP_OPCAO_ITEM,"Tipo de fornecedor",'e',100107000,2,true);  
	    addOpcao(100100000,TP_OPCAO_ITEM,"Fornecedor",'r',100108000,2,true);    
        
	addOpcao(100000000,TP_OPCAO_MENU,"Ferramentas",'F',100200000,1,false);
	addOpcao(100000000,TP_OPCAO_MENU,"Preferências",'P',100300000,1,false);
	  addOpcao(100300000,TP_OPCAO_ITEM,"Preferências gerais",'g',100310000,2,true);
	addSeparador(100100000);
	addOpcao(100000000,TP_OPCAO_MENU,"Configurações",'C',100400000,1,false);
	  addOpcao(100400000,TP_OPCAO_ITEM,"Terminal",'T',100410000,2,true);		  
	  addOpcao(100400000,TP_OPCAO_ITEM,"Impressora",'I',100420000,2,true);
	  addOpcao(100400000,TP_OPCAO_ITEM,"Papel",'P',100430000,2,true);
	  addSeparador(100400000);
	  addOpcao(100400000,TP_OPCAO_ITEM,"Empresa",'E',100440000,2,true);	  	  

  addOpcao(-1,TP_OPCAO_MENU,"Pagar",'P',400000000,0,false);
    addOpcao(400000000,TP_OPCAO_ITEM,"Manutenção",'M',400200000,1,true);
	addOpcao(400000000,TP_OPCAO_MENU,"Listagens",'s',400300000,1,false);				
	  addOpcao(400300000,TP_OPCAO_ITEM,"Pagar/Pagas",'P',400410000,2,true);

  addOpcao(-1,TP_OPCAO_MENU,"Receber",'R',500000000,0,false);
    addOpcao(500000000,TP_OPCAO_ITEM,"Manutenção",'M',500100000,1,true);
	addOpcao(500000000,TP_OPCAO_ITEM,"CNAB",'N',500200000,1,true);    
    addOpcao(500000000,TP_OPCAO_MENU,"Listagens",'s',500300000,1,false);				
  	  addOpcao(500300000,TP_OPCAO_ITEM,"Receber/Recebidas",'R',500310000,2,true);
	  addOpcao(500300000,TP_OPCAO_ITEM,"Inadimplentes",'I',500320000,2,true);

  addOpcao(-1,TP_OPCAO_MENU,"Financeiro",'F',600000000,0,false);
    addOpcao(600000000,TP_OPCAO_MENU,"Boleto",'B',600100000,1,false);
      addOpcao(600100000,TP_OPCAO_ITEM,"Modelo",'M',600110000,2,true);
      addOpcao(600100000,TP_OPCAO_ITEM,"Imprimir",'I',600120000,2,true);
    addOpcao(600000000,TP_OPCAO_ITEM,"Banco",'a',600200000,1,true);
    addOpcao(600000000,TP_OPCAO_ITEM,"Planejamento",'P',600300000,1,true);
    addOpcao(600000000,TP_OPCAO_ITEM,"Centro de custo",'C',600400000,1,true);    
	addOpcao(600000000,TP_OPCAO_ITEM,"Contas",'o',600500000,1,true);
	addOpcao(600000000,TP_OPCAO_ITEM,"Lançamentos",'L',600600000,1,true);
	addSeparador(600000000);
	addOpcao(600000000,TP_OPCAO_ITEM,"Tipo de crédito",'L',600700000,1,true);
	addOpcao(600000000,TP_OPCAO_ITEM,"Liberação de crédito",'i',600800000,1,true);
	addSeparador(600000000);
	addOpcao(600000000,TP_OPCAO_ITEM,"Reprocessa saldo",'R',600900000,1,true);
	addSeparador(600000000);
	
    addOpcao(600000000,TP_OPCAO_MENU,"Listagens",'s',601000000,1,false);				
  	  addOpcao(601000000,TP_OPCAO_ITEM,"Extrato",'E',601010000,2,true);
	  addOpcao(601000000,TP_OPCAO_ITEM,"Balancete",'B',601020000,2,true);
	  addOpcao(601000000,TP_OPCAO_ITEM,"Relatório financeiro por C.C.",'R',601030000,2,true);
      addOpcao(601000000,TP_OPCAO_ITEM,"Razão financeiro",'z',601040000,2,true);
      addOpcao(601000000,TP_OPCAO_ITEM,"Fluxo de caixa",'F',601050000,2,true);
      
	addOpcao(600000000,TP_OPCAO_MENU,"Gráficos",'G',601100000,1,false);
	  addOpcao(601100000,TP_OPCAO_ITEM,"Balancete Gráfico",'G',601110000,2,true);
	  addOpcao(601100000,TP_OPCAO_ITEM,"Gráfico financeiro por C.C",'f',601120000,2,true);
  
  addBotao("btProduto.gif","Cadastro de produtos",100120070);
  addBotao("barraUsuario.gif","Cliente",100101050);
  addBotao("barraVenda.gif","Venda",300100000);
  addBotao("barraCompra.gif","Compra",200100000); 
  addBotao("btContaPagar.gif","Contas a pagar",400200000);
  addBotao("btContaReceber.gif","Contas a receber",500100000);  
  addBotao("btLancamentoFin.gif","Lançamentos financeiros",600600000);
  addBotao("barraEstoque.gif","Consulta estoque",700300000);   

  ajustaMenu();
 	
  }
  
  public void execOpcao( int iOpcao ) {
    if (iOpcao==100101030) {
      if (telaPrincipal.temTela("TipoCli")==false) {
        FTipoCli tela = new FTipoCli();
        telaPrincipal.criatela("TipoCli",tela,con);
      } 
    }
    else if (iOpcao==100101050) {
      if (telaPrincipal.temTela("Clientes")==false) {
        FCliente tela = new FCliente();
        telaPrincipal.criatela("Clientes",tela,con);
      }
    }
    else if (iOpcao==100101010) {
      if (telaPrincipal.temTela("Setor")==false) {
        FSetor tela = new FSetor();
        telaPrincipal.criatela("Setor",tela,con);
      } 
    }
    else if (iOpcao==100102000) {
      if (telaPrincipal.temTela("Moeda")==false) {
        FMoeda tela = new FMoeda();
        telaPrincipal.criatela("Moeda",tela,con);
      } 
    }
    else if ( (iOpcao==100103000) || (iOpcao==1) ){
      if (telaPrincipal.temTela("Banco")==false) {
        FBanco tela = new FBanco();
        telaPrincipal.criatela("Banco",tela,con);
      } 
    }
    else if (iOpcao==100104000) {
      if (telaPrincipal.temTela("TipoCob")==false) {
        FTipoCob tela = new FTipoCob();
        telaPrincipal.criatela("TipoCob",tela,con);
      } 
    }
    else if (iOpcao==100105000) {
      if (telaPrincipal.temTela("PlanoPag")==false) {
        FPlanoPag tela = new FPlanoPag();
        telaPrincipal.criatela("PlanoPag",tela,con);
      } 
    }
    else if (iOpcao==100107000) {
      if (telaPrincipal.temTela("TipoFor")==false) {
        FTipoFor tela = new FTipoFor();
        telaPrincipal.criatela("TipoFor",tela,con);
      } 
    }
    else if (iOpcao==100108000) {
      if (telaPrincipal.temTela("Fornecedor")==false) {
        FFornecedor tela = new FFornecedor();
        telaPrincipal.criatela("Fornecedor",tela,con);
      } 
    }
    else if (iOpcao==100101020) {
      if (telaPrincipal.temTela("Vendedor")==false) {
        FVendedor tela = new FVendedor();
        telaPrincipal.criatela("Vendedor",tela,con);
      } 
    }
    else if (iOpcao==100430000) {
      if (telaPrincipal.temTela("Papeis")==false) {
        FPapel tela = new FPapel();
        telaPrincipal.criatela("Papeis",tela,con);
      }
    }
    else if (iOpcao==100420000) {
      if (telaPrincipal.temTela("Impressoras")==false) {
        FImpressora tela = new FImpressora();
        telaPrincipal.criatela("Impressoras",tela,con);
      }
    }
    else if (iOpcao==100410000) {
      if (telaPrincipal.temTela("Configurações")==false) {
        FTerminal tela = new FTerminal();
        telaPrincipal.criatela("Configurações",tela,con);
      }
    }
    else if (iOpcao==600500000) {
      if (telaPrincipal.temTela("Contas")==false) {
        FConta tela = new FConta();
        telaPrincipal.criatela("Contas",tela,con);
      }
    }
    else if (iOpcao==100101040) {
      if (telaPrincipal.temTela("Classifição de Clientes")==false) {
        FClasCli tela = new FClasCli();
        telaPrincipal.criatela("Classificação de Clientes",tela,con);
      }
    }
	else if (iOpcao==600200000){
	  if (telaPrincipal.temTela("Banco")==false) {
		FBanco tela = new FBanco();
		telaPrincipal.criatela("Banco",tela,con);
	  } 
	}
	else if (iOpcao==600110000) {
		if (telaPrincipal.temTela("Modelo de boleto")==false) {
			FModBoleto tela = new FModBoleto();
			telaPrincipal.criatela("Modelo de boleto",tela,con);
		} 
	}
	else if (iOpcao==600120000) {
		if (telaPrincipal.temTela("Boleto")==false) {
          FRBoleto tela = new FRBoleto();
          tela.setConexao(con);
  	      telaPrincipal.criatela("Boleto",tela);
		}
    }
	else if (iOpcao==600300000) {
      if (telaPrincipal.temTela("Planejamento")==false) {
        FPlanejamento tela = new FPlanejamento();
        tela.setConexao(con);
        telaPrincipal.criatela("Planejamento",tela);
      }
    }
	else if (iOpcao==600400000) {
	  if (telaPrincipal.temTela("Centro de Custos")==false) {
		FCentroCusto tela = new FCentroCusto();
		tela.setConexao(con);
		telaPrincipal.criatela("Centro de Custos",tela);
	  }
	} 
	else if (iOpcao==600900000) {
		if (telaPrincipal.temTela("Reprocessamento de saldos")==false) {
			FProcessaSL tela = new FProcessaSL();
			tela.setConexao(con);
			telaPrincipal.criatela("Reprocessamento de saldos",tela);
		}
	}  
	else if (iOpcao==100310000) {
      if (telaPrincipal.temTela("Pref. Gerais")==false) {
        FPrefereGeral tela = new FPrefereGeral();
        telaPrincipal.criatela("Pref. Garais",tela,con);
      }
    }
    else if (iOpcao==600600000){
      if (telaPrincipal.temTela("Lançamentos")==false){
        FLanca tela = new FLanca();
        tela.setConexao(con);
        telaPrincipal.criatela("Lançamentos",tela);
      }
    }
    else if (iOpcao==500310000) {
      if (telaPrincipal.temTela("Receber/Recebidas")==false) {
        FRReceber tela = new FRReceber();
        tela.setConexao(con);
        telaPrincipal.criatela("Receber/Recebidas",tela);
      }
    }
    else if (iOpcao==500320000) {
      if (telaPrincipal.temTela("Inadimplentes")==false) {
        FRInadimplentes tela = new FRInadimplentes();
        tela.setConexao(con);
        telaPrincipal.criatela("Inadimplentes",tela);
      }
    }
    else if (iOpcao==400410000) {
      if (telaPrincipal.temTela("Pagar/Pagas")==false) {
        FRPagar tela = new FRPagar();
        tela.setConexao(con);
        telaPrincipal.criatela("Pagar/Pagas",tela);
      }
    }
    else if (iOpcao==400200000) {
      if (telaPrincipal.temTela("Manutenção de contas a pagar")==false) {
        FManutPag tela = new FManutPag();
        tela.setConexao(con);
        telaPrincipal.criatela("Manutenção de contas a pagar",tela);
      }
    }
    else if (iOpcao==500100000) {
    	if (telaPrincipal.temTela("Manutenção de contas a receber")==false) {
    		FManutRec tela = new FManutRec();
    		tela.setConexao(con);
    		telaPrincipal.criatela("Manutenção de contas a receber",tela);
    	}
    }
    else if (iOpcao==601010000) {
      if (telaPrincipal.temTela("Extrato")==false) {
        FRExtrato tela = new FRExtrato();
        tela.setConexao(con);
        telaPrincipal.criatela("Extrato",tela);
      }
    }
    else if (iOpcao==601020000) {
      if (telaPrincipal.temTela("Balancete")==false) {
        FRBalancete tela = new FRBalancete();
        tela.setConexao(con);
        telaPrincipal.criatela("Balancete",tela);
      }
    }
	else if (iOpcao==601030000) {
	  if (telaPrincipal.temTela("Relatorio Financeiro por C.C.")==false) {
		FRCentroCusto tela = new FRCentroCusto();
		tela.setConexao(con);
		telaPrincipal.criatela("Relatorio Financeiro por C.C.",tela);
	  }
	}
	else if (iOpcao==601040000) {
	  if (telaPrincipal.temTela("Razão financeiro")==false) {
		FRRazaoFin tela = new FRRazaoFin();
		tela.setConexao(con);
		telaPrincipal.criatela("Razão financeiro",tela);
	  }
	}
	else if (iOpcao==601050000) {
		if (telaPrincipal.temTela("Fluxo de caixa")==false) {
			FRFluxoCaixa tela = new FRFluxoCaixa();
			tela.setConexao(con);
			telaPrincipal.criatela("Fluxo de caixa",tela);
		}
	}
	else if (iOpcao==601110000) {
	  if (telaPrincipal.temTela("Balancete Gráfico")==false) {
		FRBalanceteGrafico tela = new FRBalanceteGrafico();
		tela.setConexao(con);
		telaPrincipal.criatela("Balancete Gráfico",tela);
	  }
	}
	else if (iOpcao==601120000) {
	  if (telaPrincipal.temTela("Gráfico Financeiro por C.C")==false) {
		FRGraficoCC tela = new FRGraficoCC();		
		tela.setConexao(con);
		telaPrincipal.criatela("Gráfico Financeiro por C.C",tela);
	  }
	}	
    else if (iOpcao==100440000) {
       if (telaPrincipal.temTela("Empresa")==false) {
         FEmpresa tela = new FEmpresa();
         telaPrincipal.criatela("FEmpresa",tela,con);
       }
    }
    else if (iOpcao==1) {
    	atualizaMenus();
    }
  }

  public static void main(String sParams[]) {
    FreedomFNC freedom = new FreedomFNC();
    freedom.show();
  }
}