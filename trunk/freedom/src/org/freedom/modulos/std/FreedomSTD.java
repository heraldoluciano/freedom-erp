/**
 * @version 02/02/2000 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)Freedomstd.java <BR>
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
 * Tela principal do módulo standard.
 * 
 */

package org.freedom.modulos.std;

import org.freedom.telas.Aplicativo;

public class FreedomSTD extends Aplicativo {
  public FreedomSTD() {
	super("iconStandart32.gif","splashSTD.jpg","Sistema Integrado de Informações Gerenciais",1,1);
	addOpcao(-1,TP_OPCAO_MENU,"Arquivo",'A',100000000,0,false);
	  addOpcao(100000000,TP_OPCAO_MENU,"Tabelas",'T',100100000,1,false);
	    addOpcao(100100000,TP_OPCAO_MENU,"Cliente",'C',100101000,2,false);
	      addOpcao(100101000,TP_OPCAO_ITEM,"Tipo de cliente...",'T',100101010,3,true);
	      addOpcao(100101000,TP_OPCAO_ITEM,"Classificação de cliente...", 'f', 100101020,3,true);
	      addOpcao(100101000,TP_OPCAO_ITEM,"Cliente...", 'C',100101030,3,true);
		  addOpcao(100101000,TP_OPCAO_ITEM,"Tipo fiscal de cliente...",'p',100101040,3,true);
		  addOpcao(100101000,TP_OPCAO_ITEM,"Crédito por cliente...",'r',100101050,3,true);
	    addOpcao(100100000,TP_OPCAO_MENU,"Vendedor",'C',100102000,2,false);
	      addOpcao(100102000,TP_OPCAO_ITEM,"Setor", 'S',100102010,3,true);
	      addOpcao(100102000,TP_OPCAO_ITEM,"Vendedor", 'V',100102020,4,true);
	      addOpcao(100102000,TP_OPCAO_ITEM,"Classif. de Comissões", 'P',100102030,5,true);
	      
	      
        addSeparador(100100000);
        addOpcao(100100000,TP_OPCAO_ITEM,"Moeda",'M',100112000,2,true);
	    addOpcao(100100000,TP_OPCAO_ITEM,"Banco",'B',100113000,2,true);
	    addOpcao(100100000,TP_OPCAO_ITEM,"Tipo de cobrança",'o',100114000,2,true);
	    addOpcao(100100000,TP_OPCAO_ITEM,"Plano de pagamento",'s',100115000,2,true);
	    addOpcao(100100000,TP_OPCAO_ITEM,"Tipo de expositor",'x',100116000,2,true);	    
	    addSeparador(100100000);	    
	    addOpcao(100100000,TP_OPCAO_ITEM,"Transportadora",'p',100117000,2,true);  	    
	    addSeparador(100100000);	    
	    addOpcao(100100000,TP_OPCAO_ITEM,"Tipo de fornecedor",'e',100118000,2,true);  
	    addOpcao(100100000,TP_OPCAO_ITEM,"Fornecedor",'r',100119000,2,true);    
	    addSeparador(100100000);
        addOpcao(100100000,TP_OPCAO_ITEM,"Natureza de operação",'z',100120000,2,true);
	    addSeparador(100100000);
	    addOpcao(100100000,TP_OPCAO_MENU,"Produto",'u',100130000,2,false);
          addOpcao(100130000,TP_OPCAO_ITEM,"Tratamento tributário",'t',100130010,3,true);
          addOpcao(100130000,TP_OPCAO_ITEM,"Classificação fiscal",'l',100130020,3,true);
          addOpcao(100130000,TP_OPCAO_ITEM,"Almoxarifado",'x',100130030,3,true);
          addOpcao(100130000,TP_OPCAO_ITEM,"Grupo",'r',100130040,3,true);
          addOpcao(100130000,TP_OPCAO_ITEM,"Marca",'c',100130050,3,true);
          addOpcao(100130000,TP_OPCAO_ITEM,"Unidade",'U',100130060,3,true);
          addOpcao(100130000,TP_OPCAO_ITEM,"Produto",'P',100130070,3,true);
	      addSeparador(100130000);
          addOpcao(100130000,TP_OPCAO_ITEM,"Similaridade",'S',100130080,3,true);
          addOpcao(100130000,TP_OPCAO_MENU,"Grade de produtos",'G',100130090,3,false);        
	        addOpcao(100130090,TP_OPCAO_ITEM,"Variantes",'V',100130091,4,true);
	        addOpcao(100130090,TP_OPCAO_ITEM,"Modelo",'M',100130092,4,true);
            addOpcao(100130090,TP_OPCAO_ITEM,"Grade",'r',100130093,4,true);
	    addSeparador(100100000);
        addOpcao(100100000,TP_OPCAO_MENU,"Preço",'ç',100140000,2,false);
          addOpcao(100140000,TP_OPCAO_ITEM,"Manutenção de Preços",'M',100140010,3,true);
          addOpcao(100140000,TP_OPCAO_ITEM,"Copia preço",'i',100140020,3,true);
          addOpcao(100140000,TP_OPCAO_ITEM,"Tabela de preço",'a',100140030,3,true);
          addOpcao(100140000,TP_OPCAO_ITEM,"Lista de preço",'l',100140040,3,true);
            
	addOpcao(100000000,TP_OPCAO_MENU,"Ferramentas",'F',100200000,1,false);
	  addOpcao(100200000,TP_OPCAO_ITEM,"Alteração de numero de nota",'A',100210000,2,true);
	  addOpcao(100200000,TP_OPCAO_ITEM,"Exportar SVV",'E',100220000,2,true);
	  addOpcao(100200000,TP_OPCAO_MENU,"Etiquetas",'t',100230000,2,false);
	     addOpcao(100230000,TP_OPCAO_ITEM,"Modelo",'M',100230100,3,true);
	     addOpcao(100230000,TP_OPCAO_ITEM,"Imprimir",'I',100230200,3,true); // LOM
	  addOpcao(100200000,TP_OPCAO_ITEM,"Imp. tabelas de fornecedores",'I',100240000,2,true);
      addOpcao(100200000,TP_OPCAO_ITEM,"Ajuste do item do orçamento",'A',100250000,2,true);
      addOpcao(100200000,TP_OPCAO_MENU,"Bloqueios e desbloqueios",'B',100260000,2,false);
         addOpcao(100260000,TP_OPCAO_ITEM,"Compras",'C',100260100,3,true);
         addOpcao(100260000,TP_OPCAO_ITEM,"Vendas",'V',100260200,3,true);
	addOpcao(100000000,TP_OPCAO_MENU,"Preferências",'P',100300000,1,false);
	  addOpcao(100300000,TP_OPCAO_ITEM,"Preferências gerais",'g',100310000,2,true);
	  addOpcao(100300000,TP_OPCAO_ITEM,"Série de NFs",'N',100320000,2,true);
	  addOpcao(100300000,TP_OPCAO_ITEM,"Modelo de NFs",'M',100330000,2,true);
	addSeparador(100100000);
	addOpcao(100000000,TP_OPCAO_MENU,"Configurações",'C',100400000,1,false);
	  addOpcao(100400000,TP_OPCAO_ITEM,"Terminal",'T',100410000,2,true);		  
	  addOpcao(100400000,TP_OPCAO_ITEM,"Impressora",'I',100420000,2,true);
	  addOpcao(100400000,TP_OPCAO_ITEM,"Papel",'P',100430000,2,true);
	  addOpcao(100400000,TP_OPCAO_ITEM,"Estação",'E',100440000,2,true);// lom
	  addSeparador(100400000);
	  addOpcao(100400000,TP_OPCAO_ITEM,"Empresa",'E',100450000,2,true);	  	  

  addOpcao(-1,TP_OPCAO_MENU,"Entrada",'E',200000000,0,false);
    addOpcao(200000000,TP_OPCAO_ITEM,"Compra",'C',200100000,1,true);
	addOpcao(200000000,TP_OPCAO_MENU,"Listagens",'L',200200000,1,false);
	  addOpcao(200200000,TP_OPCAO_ITEM,"Compras por fornecedor",'f',200210000,2,true);

  addOpcao(-1,TP_OPCAO_MENU,"Saída",'S',300000000,0,false);
    addOpcao(300000000,TP_OPCAO_ITEM,"Venda",'V',300100000,1,true);
    addOpcao(300000000,TP_OPCAO_ITEM,"Cancela venda",'C',300200000,1,true);
    addOpcao(300000000,TP_OPCAO_ITEM,"Devolução de vendas",'D',300300000,1,true);
    addOpcao(300000000,TP_OPCAO_ITEM,"Lançamento de Frete",'L',300400000,1,true);
	addSeparador(300000000);
	addOpcao(300000000,TP_OPCAO_ITEM,"Aprova orçamento",'A',300500000,1,true);
	addOpcao(300000000,TP_OPCAO_ITEM,"Orçamento",'O',300600000,1,true);
	addOpcao(300000000,TP_OPCAO_ITEM,"Pesquisa Orçamento",'P',300700000,1,true);
	addSeparador(300000000);
	addOpcao(300000000,TP_OPCAO_ITEM,"Romaneio",'R',300800000,1,true);  		
	addOpcao(300000000,TP_OPCAO_ITEM,"Lançamento de expositores",'x',300900000,1,true);
	addOpcao(300000000,TP_OPCAO_MENU,"Listagens",'s',301000000,1,false);				
      addOpcao(301000000,TP_OPCAO_ITEM,"Resumo diário",'R',301010000,2,true);
	  addOpcao(301000000,TP_OPCAO_ITEM,"Vendas geral",'V',301020000,2,true);
	  addOpcao(301000000,TP_OPCAO_ITEM,"Vendas físico",'d',301030000,2,true);
	  addOpcao(301000000,TP_OPCAO_ITEM,"Vendas detalhado",'n',301040000,2,true);	        
	  addOpcao(301000000,TP_OPCAO_ITEM,"Vendas por ítem",'e',301050000,2,true);
	  addOpcao(301000000,TP_OPCAO_ITEM,"Média de vendas por ítem",'o',301060000,2,true);
	  addOpcao(301000000,TP_OPCAO_ITEM,"Ultimas Vendas por Cliente",'C',301070000,2,true);
	  addOpcao(301000000,TP_OPCAO_ITEM,"Vendas por Setor",'t',301080000,2,true);
   addOpcao(300000000,TP_OPCAO_MENU,"Gráficos",'G',301100000,1,false);	  
      addOpcao(301100000,TP_OPCAO_ITEM,"Evolução de vendas",'E',301110000,2,true);
   addSeparador(300000000);
   addOpcao(300000000,TP_OPCAO_MENU,"Consultas",'n',301200000,1,false);	  
      addOpcao(301200000,TP_OPCAO_ITEM,"Preços",'P',301210000,2,true);

  addOpcao(-1,TP_OPCAO_MENU,"Pagar",'P',400000000,0,false);
    addOpcao(400000000,TP_OPCAO_ITEM,"Comissão",'C',400100000,1,true);
    addOpcao(400000000,TP_OPCAO_ITEM,"Manutenção",'M',400200000,1,true);
	addOpcao(400000000,TP_OPCAO_MENU,"Listagens",'s',400300000,1,false);				
	  addOpcao(400300000,TP_OPCAO_ITEM,"Pagar/Pagas",'P',400410000,2,true);
	  addOpcao(400300000,TP_OPCAO_ITEM,"Comissões",'C',400420000,2,true);

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
	addOpcao(600000000,TP_OPCAO_ITEM,"Tabela de juros",'T',600900000,1,true);
	addSeparador(600000000);
	addOpcao(600000000,TP_OPCAO_ITEM,"Reprocessa saldo",'R',601000000,1,true);
	addSeparador(600000000);
    addOpcao(600000000,TP_OPCAO_MENU,"Listagens",'s',601100000,1,false);				
  	  addOpcao(601100000,TP_OPCAO_ITEM,"Extrato",'E',601110000,2,true);
	  addOpcao(601100000,TP_OPCAO_ITEM,"Balancete",'B',601120000,2,true);
	  addOpcao(601100000,TP_OPCAO_ITEM,"Relatório financeiro por C.C.",'R',601130000,2,true);
      addOpcao(601100000,TP_OPCAO_ITEM,"Razão financeiro",'z',601140000,2,true);
      addOpcao(601100000,TP_OPCAO_ITEM,"Fluxo de caixa",'F',601150000,2,true);
	addOpcao(600000000,TP_OPCAO_MENU,"Gráficos",'G',601200000,1,false);
	  addOpcao(601200000,TP_OPCAO_ITEM,"Balancete Gráfico",'G',601210000,2,true);
	  addOpcao(601200000,TP_OPCAO_ITEM,"Gráfico financeiro por C.C",'f',601220000,2,true);
  
  addOpcao(-1,TP_OPCAO_MENU,"Estoque",'E',700000000,0,false);
    addOpcao(700000000,TP_OPCAO_ITEM,"Kardex",'K',700100000,1,true);
	addOpcao(700000000,TP_OPCAO_ITEM,"Inventário",'I',700200000,1,true);
	addOpcao(700000000,TP_OPCAO_ITEM,"Consulta estoque",'C',700300000,1,true);
	addOpcao(700000000,TP_OPCAO_ITEM,"Consulta produto",'P',700400000,1,true);
	addOpcao(700000000,TP_OPCAO_ITEM,"Tipos de movimentos",'T',700500000,1,true);		
	addSeparador(700000000);
	addOpcao(700000000,TP_OPCAO_ITEM,"Reprocessa estoque",'R',700600000,1,true);
	addSeparador(700000000);
	addOpcao(700000000,TP_OPCAO_MENU,"Listagens",'L',700700000,1,false);
  	  addOpcao(700700000,TP_OPCAO_ITEM,"Estoque mínimo",'s',700701000,2,true);
  	  addOpcao(700700000,TP_OPCAO_ITEM,"Produtos/Movimentos",'P',700702000,2,true);
	  addOpcao(700700000,TP_OPCAO_ITEM,"Vencimentos de lote",'V',700703000,2,true);
	  addOpcao(700700000,TP_OPCAO_ITEM,"Saldos de lote",'l',700704000,2,true);
	  addOpcao(700700000,TP_OPCAO_ITEM,"Demanda",'D',700705000,2,true);
	  addOpcao(700700000,TP_OPCAO_ITEM,"Conferência",'C',700706000,2,true);
	  addOpcao(700700000,TP_OPCAO_ITEM,"Inventário PEPS",'I',700707000,2,true);
	  
  addOpcao(-1,TP_OPCAO_MENU,"Fiscal",'F',800000000,0,false);
    addOpcao(800000000,TP_OPCAO_ITEM,"Gerar",'G',800100000,1,true);
	addOpcao(800000000,TP_OPCAO_ITEM,"Regras fiscais",'R',800200000,1,true);
	addOpcao(800000000,TP_OPCAO_ITEM,"Sintegra",'S',800300000,1,true);
	addOpcao(800000000,TP_OPCAO_ITEM,"Tabela de Alíquotas",'T',800400000,1,true);
	addOpcao(800000000,TP_OPCAO_ITEM,"Mensagens",'M',800500000,1,true);
	addOpcao(800000000,TP_OPCAO_MENU,"Listagens",'L',800600000,1,false);
	  addOpcao(800600000,TP_OPCAO_ITEM,"ICMS sobre vendas",'I',800601000,2,true);
	  addOpcao(800600000,TP_OPCAO_ITEM,"Impostos sobre serviços",'S',800602000,2,true);
	  addOpcao(800600000,TP_OPCAO_ITEM,"Pis e cofins",'P',800603000,2,true);

  addBotao("btCliente.gif","Cliente",100101030);
  addBotao("btSaida.gif","Venda",300100000);
  addBotao("btEntrada.gif","Compra",200100000); 
  addBotao("btContaPagar.gif","Contas a pagar",400200000);
  addBotao("btContaReceber.gif","Contas a receber",500100000);  
  addBotao("btLancamentoFin.gif","Lançamentos financeiros",600600000);
  addBotao("btEstoque.gif","Consulta estoque",700300000);   
  addBotao("btProduto.gif","Cadastro de produtos",100130070);
  addBotao("btEstProduto.gif","Consulta produto",700400000);
  //addBotao("btEmprestimo.gif","Cadastro de similaridades",100120080);

  ajustaMenu();
 	
  }
  
  public void execOpcao( int iOpcao ) {
    if (iOpcao==100101010) {
      if (telaPrincipal.temTela("TipoCli")==false) {
        FTipoCli tela = new FTipoCli();
        telaPrincipal.criatela("TipoCli",tela,con);
      } 
    }
	else if (iOpcao==100101050) {
	  if (telaPrincipal.temTela("Crédito por cliente")==false) {
		FCredCli tela = new FCredCli();
		telaPrincipal.criatela("Crédito por cliente",tela,con);
	  } 
	}
	else if (iOpcao==100101040) {
	  if (telaPrincipal.temTela("Tipo Fiscal de Cliente")==false) {
		FTipoFiscCli tela = new FTipoFiscCli();
		telaPrincipal.criatela("Tipo Fiscal de Cliente",tela,con);
	  } 
	}
    else if (iOpcao==100101030) {
      if (telaPrincipal.temTela("Clientes")==false) {
        FCliente tela = new FCliente();
        telaPrincipal.criatela("Clientes",tela,con);
      }
    }
    else if (iOpcao==100102010) {
      if (telaPrincipal.temTela("Setor")==false) {
        FSetor tela = new FSetor();
        telaPrincipal.criatela("Setor",tela,con);
      } 
    }
    else if (iOpcao==100112000) {
      if (telaPrincipal.temTela("Moeda")==false) {
        FMoeda tela = new FMoeda();
        telaPrincipal.criatela("Moeda",tela,con);
      } 
    }
    else if ( (iOpcao==100113000) || (iOpcao==1) ){
      if (telaPrincipal.temTela("Banco")==false) {
        FBanco tela = new FBanco();
        telaPrincipal.criatela("Banco",tela,con);
      } 
    }
    else if (iOpcao==100114000) {
      if (telaPrincipal.temTela("TipoCob")==false) {
        FTipoCob tela = new FTipoCob();
        telaPrincipal.criatela("TipoCob",tela,con);
      } 
    }
    else if (iOpcao==100115000) {
      if (telaPrincipal.temTela("PlanoPag")==false) {
        FPlanoPag tela = new FPlanoPag();
        telaPrincipal.criatela("PlanoPag",tela,con);
      } 
    }
    else if (iOpcao==100118000) {
      if (telaPrincipal.temTela("TipoFor")==false) {
        FTipoFor tela = new FTipoFor();
        telaPrincipal.criatela("TipoFor",tela,con);
      } 
    }
    else if (iOpcao==100119000) {
      if (telaPrincipal.temTela("Fornecedor")==false) {
        FFornecedor tela = new FFornecedor();
        telaPrincipal.criatela("Fornecedor",tela,con);
      } 
    }
    else if (iOpcao==100102020) {
      if (telaPrincipal.temTela("Vendedor")==false) {
        FVendedor tela = new FVendedor();
        telaPrincipal.criatela("Vendedor",tela,con);
      } 
    }
    
    
    else if (iOpcao==100102030) {
        if (telaPrincipal.temTela("Classificação de Comissões")==false) {
          FCLComis tela = new FCLComis();
          telaPrincipal.criatela("Classificação de Comissões",tela,con);
        } 
      }
    
    
    else if (iOpcao==100117000) {
      if (telaPrincipal.temTela("Transportadora")==false) {
        FTransp tela = new FTransp();
        telaPrincipal.criatela("Transportadora",tela,con);
      } 
    }
    else if (iOpcao==100130050) {
      if (telaPrincipal.temTela("Marcas")==false) {
        FMarca tela = new FMarca();
        telaPrincipal.criatela("Marcas",tela,con);
      } 
    }
    else if (iOpcao==100130030) {
      if (telaPrincipal.temTela("Almoxarifado")==false) {
        FAlmox tela = new FAlmox();
        telaPrincipal.criatela("Almoxarifado",tela,con);
      }
    }
    else if (iOpcao==100130060) {
      if (telaPrincipal.temTela("Unidades")==false) {
        FUnidade tela = new FUnidade();
        telaPrincipal.criatela("Unidades",tela,con);
      }
    }
    else if (iOpcao==100120000) {
      if (telaPrincipal.temTela("Naturezas")==false) {
        FNatoPer tela = new FNatoPer();
        tela.setTelaPrim(telaPrincipal);
        telaPrincipal.criatela("Naturezas",tela,con);
      } 
    }
    else if (iOpcao==100130010) {
      if (telaPrincipal.temTela("Tratamento Tributário")==false) {
        FTratTrib tela = new FTratTrib();
        telaPrincipal.criatela("Tratamento Tributário",tela,con);
      } 
    }
    else if (iOpcao==100130020) {
      if (telaPrincipal.temTela("Classificações")==false) {
        FCLFiscal tela = new FCLFiscal();
        tela.setTelaPrim(telaPrincipal);
        telaPrincipal.criatela("Classificações",tela,con);
      } 
    }
    else if (iOpcao==100430000) {
      if (telaPrincipal.temTela("Papeis")==false) {
        FPapel tela = new FPapel();
        telaPrincipal.criatela("Papeis",tela,con);
      }
    }
    else if (iOpcao==100440000) {// LOM
        if (telaPrincipal.temTela("Estacao")==false) {// LOM
          FEstacao tela = new FEstacao();// LOM
          telaPrincipal.criatela("Estacao",tela,con);// LOM
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
    else if (iOpcao==100130070){
      if (telaPrincipal.temTela("Produtos")==false) {
        FProduto tela = new FProduto();
        telaPrincipal.criatela("Produtos",tela,con);
      }
    }
    else if (iOpcao==100130080) {
    	if (telaPrincipal.temTela("Similar")==false) {
    		FSimilar tela = new FSimilar();
    		telaPrincipal.criatela("Similar",tela,con);
    	}
    }
    else if (iOpcao==100130040) {
      if (telaPrincipal.temTela("Grupos")==false) {
        FGrupo tela = new FGrupo();
        tela.setConexao(con);
        telaPrincipal.criatela("Grupos",tela);
      }
    }
    else if (iOpcao==100101020) {
      if (telaPrincipal.temTela("Classifição de Clientes")==false) {
        FClasCli tela = new FClasCli();
        telaPrincipal.criatela("Classificação de Clientes",tela,con);
      }
    }
    else if (iOpcao==100140030) {
      if (telaPrincipal.temTela("Tabelas de Preços")==false) {
        FTabPreco tela = new FTabPreco();
        telaPrincipal.criatela("Tabelas de Preços",tela,con);
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
	else if (iOpcao==600800000) {
	  if (telaPrincipal.temTela("Liberação de crédito")==false) {
		FLiberaCredito tela = new FLiberaCredito();
		telaPrincipal.criatela("Liberação de crédito",tela,con);
	  }
	}  
	else if (iOpcao==600900000) {
		if (telaPrincipal.temTela("Tabelas de juros")==false) {
			FTabJuros tela = new FTabJuros();
			telaPrincipal.criatela("Tabelas de juros",tela,con);
		}
	}  
	else if (iOpcao==601000000) {
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
    else if (iOpcao==700200000) {
      if (telaPrincipal.temTela("Inventário")==false) {
        FInventario tela = new FInventario();
        telaPrincipal.criatela("Inventário",tela,con);
      } 
    }
    else if (iOpcao==700703000) {
      if (telaPrincipal.temTela("Vencimento Lote")==false) {
        FRVencLote tela = new FRVencLote();
        tela.setConexao(con);
        telaPrincipal.criatela("Vencimento Lote",tela);
      } 
    }
	else if (iOpcao==700704000) {
	  if (telaPrincipal.temTela("Saldos de Lote")==false) {
		FRSaldoLote tela = new FRSaldoLote();
		tela.setConexao(con);
		telaPrincipal.criatela("Saldos de Lote",tela);
	  } 
	}
    else if (iOpcao==100130091) {
      if (telaPrincipal.temTela("Variantes")==false) {
        FVariantes tela = new FVariantes();
        telaPrincipal.criatela("Variantes",tela,con);
      } 
    }
    else if (iOpcao==100130092) {
      if (telaPrincipal.temTela("Modelo de Grade")==false) {
        FModGrade tela = new FModGrade();
        telaPrincipal.criatela("Modelo de Grade",tela,con);
      } 
    }
    else if (iOpcao==100130093) {
      if (telaPrincipal.temTela("Grade")==false) {
        FGrade tela = new FGrade();
        tela.setConexao(con);
        telaPrincipal.criatela("Grade",tela);
      }
    }
    else if (iOpcao==100320000) {
      if (telaPrincipal.temTela("Serie NF")==false) {
        FSerie tela = new FSerie();
        telaPrincipal.criatela("Serie NF",tela,con);
      } 
    }
    else if (iOpcao==100330000) {
      if (telaPrincipal.temTela("Modelo NF")==false) {
        FModNota tela = new FModNota();
        telaPrincipal.criatela("Modelo NF",tela,con);
      } 
    }
    else if (iOpcao==700500000) {
      if (telaPrincipal.temTela("Tipo de Movimento")==false) {
        FTipoMov tela = new FTipoMov();
        telaPrincipal.criatela("Tipo de Movimento",tela,con);
      } 
    }
    else if (iOpcao==300100000) {
      if (telaPrincipal.temTela("Venda")==false) {
        FVenda tela = new FVenda();
        tela.setTelaPrim(telaPrincipal);
        telaPrincipal.criatela("Venda",tela,con);
      } 
    }
	else if (iOpcao==300500000) {
	  if (telaPrincipal.temTela("Aprova Orcamento")==false) {
	    FAprovaOrc tela = new FAprovaOrc();
	    tela.setConexao(con);
	    telaPrincipal.criatela("Aprova Orcamento",tela);
	  } 
	}
	else if (iOpcao==300600000) {
	  if (telaPrincipal.temTela("Orçamento")==false) {
		FOrcamento tela = new FOrcamento();
		telaPrincipal.criatela("Orçamento",tela,con);
	  } 
	}
	
	
	else if (iOpcao==300700000) {
		if (telaPrincipal.temTela("Pesquisa Orçamento")==false) {
			FConsOrc tela = new FConsOrc();
			tela.setConexao(con);
			tela.setTelaPrim(telaPrincipal);
			telaPrincipal.criatela("Pesquisa Orçamento",tela);
		} 
	}
		
    else if (iOpcao==200100000) {
      if (telaPrincipal.temTela("Compra")==false) {
        FCompra tela = new FCompra();
        telaPrincipal.criatela("Compra",tela,con);
      } 
    }
    else if (iOpcao==300800000) {
      if (telaPrincipal.temTela("Romaneio")==false) {
        FRomaneio tela = new FRomaneio();
        telaPrincipal.criatela("Romaneio",tela,con);
      } 
    }
    else if (iOpcao==300900000) {
      if (telaPrincipal.temTela("LancaExp")==false) {
        FLancaExp tela = new FLancaExp();
        telaPrincipal.criatela("LancaExp",tela,con);
      } 
    }
    else if (iOpcao==400100000) {
      if (telaPrincipal.temTela("Comissao")==false) {
        FManutComis tela = new FManutComis();
        tela.setConexao(con);
        telaPrincipal.criatela("Comissao",tela);
      }
    }
    else if (iOpcao==700100000) {
      if (telaPrincipal.temTela("Kardex")==false) {
        FKardex tela = new FKardex();
        tela.setConexao(con);
        telaPrincipal.criatela("Kardex",tela);
      }
    }
    else if (iOpcao==600600000){
      if (telaPrincipal.temTela("Lançamentos")==false){
        FLanca tela = new FLanca();
        tela.setConexao(con);
        telaPrincipal.criatela("Lançamentos",tela);
      }
    }
    else if (iOpcao==100116000) {
      if (telaPrincipal.temTela("Tipo de Expositor")==false) {
        FTipoExp tela = new FTipoExp();
        telaPrincipal.criatela("Tipo de Expositor",tela,con);
      } 
    }
    else if (iOpcao==100140020) {
      if (telaPrincipal.temTela("Copia Precos")==false) {
        FCpProd tela = new FCpProd();
        tela.setConexao(con);
        telaPrincipal.criatela("Copia Precos",tela);
      }
    }
    else if (iOpcao==100140010) {
      if (telaPrincipal.temTela("Manutenção de Preços")==false) {
        FManutPreco tela = new FManutPreco();
        tela.setConexao(con);
        telaPrincipal.criatela("Manutenção de Preços",tela);
      }
    }
    else if (iOpcao==100140040) {
      if (telaPrincipal.temTela("Lista de Preços")==false) {
        FRListaPreco tela = new FRListaPreco();
        tela.setConexao(con);
        telaPrincipal.criatela("Lista de Preços",tela);
      }
    }
    else if (iOpcao==301010000) {
      if (telaPrincipal.temTela("Resumo Diário")==false) {
        FRResumoDiario tela = new FRResumoDiario();
        tela.setConexao(con);
        telaPrincipal.criatela("Resumo Diário",tela);
      }
    }
    else if (iOpcao==301020000) {
      if (telaPrincipal.temTela("Vendas em Geral")==false) {
        FRVendasGeral tela = new FRVendasGeral();
        tela.setConexao(con);
        telaPrincipal.criatela("Vendas em Geral",tela);
      }
    }
    else if (iOpcao==200210000) {
      if (telaPrincipal.temTela("Compras por Fornecedor")==false) {
        FRComprasFor tela = new FRComprasFor();
        tela.setConexao(con);
        telaPrincipal.criatela("Compras por Fornecedor",tela);
      }
    }
    else if (iOpcao==301030000) {
      if (telaPrincipal.temTela("Físico de Vendas")==false) {
        FRVendasFisico tela = new FRVendasFisico();
        tela.setConexao(con);
        telaPrincipal.criatela("Físico de Vendas",tela);
      }
    }
    else if (iOpcao==700300000) {
    	if (telaPrincipal.temTela("Consulta")==false) {
    		FConsulta tela = new FConsulta();
    		tela.setConexao(con);
    		telaPrincipal.criatela("Consulta",tela);
    	}
    }
    else if (iOpcao==700400000) { 
    	if (telaPrincipal.temTela("Consulta produto")==false) {
    		FConsProd tela = new FConsProd();
    		tela.setTelaPrim(telaPrincipal);
    		telaPrincipal.criatela("Consulta produto",tela);    	
    		tela.setConexao(con);
    	}
    }    
    else if (iOpcao==800601000) {
      if (telaPrincipal.temTela("Icms Vendas e Compras")==false) {
        FRVendasIcms tela = new FRVendasIcms();
        tela.setConexao(con);
        telaPrincipal.criatela("Icms Vendas e Compras",tela);
      }
    }
    else if (iOpcao==800602000) {
        if (telaPrincipal.temTela("Impostos sobre serviços")==false) {
          FRImpServ tela = new FRImpServ();
          tela.setConexao(con);
          telaPrincipal.criatela("Impostos sobre Serviços",tela);
        }
      
    }
    else if (iOpcao==800603000) {
        if (telaPrincipal.temTela("Pis e cofins")==false) {
          FRPisCofins tela = new FRPisCofins();
          tela.setConexao(con);
          telaPrincipal.criatela("Pis e cofins",tela);
        }
	      
    }
    else if (iOpcao==800200000) {
      if (telaPrincipal.temTela("Regras Fiscais")==false) {
        FRegraFiscal tela = new FRegraFiscal();
        telaPrincipal.criatela("Regras Fiscais",tela,con);
      } 
    }
    else if (iOpcao==800100000) {
      if (telaPrincipal.temTela("Gera Fiscal")==false) {
        FGeraFiscal tela = new FGeraFiscal();
        tela.setConexao(con);
        telaPrincipal.criatela("Gera Fiscal",tela);
      }
    }
    else if (iOpcao==800300000) {
      if (telaPrincipal.temTela("Gera Arquivo Sintegra")==false) {
        FSintegra tela = new FSintegra();
        tela.setConexao(con);
        tela.setOrigem(telaPrincipal);
        telaPrincipal.criatela("Gera Arquivo Sintegra",tela);
      }
    }
	else if (iOpcao==800400000) {
	  if (telaPrincipal.temTela("Tabela de alíquotas")==false) {
		FTabICMS tela = new FTabICMS();
		telaPrincipal.criatela("Tabela de alíquotas",tela,con);
	  }
	}
	else if (iOpcao==800500000) {
	  if (telaPrincipal.temTela("Mensagens")==false) {
		FMensagem tela = new FMensagem();
		telaPrincipal.criatela("Mensagens",tela,con);
	  }
	}
    else if (iOpcao==301040000) {
      if (telaPrincipal.temTela("Vendas Detalhadas")==false) {
        FRVendasDet tela = new FRVendasDet();
        tela.setConexao(con);
        telaPrincipal.criatela("Vendas Detalhadas",tela);
      }
    }
    else if (iOpcao==301050000) {
      if (telaPrincipal.temTela("Vendas por Item")==false) {
        FRVendasItem tela = new FRVendasItem();
        tela.setConexao(con);
        telaPrincipal.criatela("Vendas por Item",tela);
      }
    }
    else if (iOpcao==301060000) {
      if (telaPrincipal.temTela("Media de vendas por item")==false) {
        FRMediaItem tela = new FRMediaItem();
        tela.setConexao(con);
        telaPrincipal.criatela("Media de vendas por item",tela);
      }
    }
    
    else if (iOpcao==301070000) {
    	if (telaPrincipal.temTela("Ultimas Vendas por Cliente")==false) {
    		FRUltimaVenda tela = new FRUltimaVenda();
    		tela.setConexao(con);
    		telaPrincipal.criatela("Ultimas Vendas por Cliente",tela);
    	}
    }

    else if (iOpcao==301080000) {
    	if (telaPrincipal.temTela("Vendas por Setor")==false) {
    		FRVendaSetor tela = new FRVendaSetor();
    		tela.setConexao(con);
    		telaPrincipal.criatela("Vendas por Setor",tela);
    	}
    }
    
	else if (iOpcao==301110000) {
	  if (telaPrincipal.temTela("Evolução de vendas")==false) {
		FREvoluVendas tela = new FREvoluVendas();
		tela.setConexao(con);
		telaPrincipal.criatela("Evolução de vendas",tela);
	  }
	}

	else if (iOpcao==301210000) {
		  if (telaPrincipal.temTela("Consulta de preços")==false) {
			FConsPreco tela = new FConsPreco();
			tela.setConexao(con);
			telaPrincipal.criatela("Consulta de preços",tela);
		  }
		}
	
    else if (iOpcao==500100000) {
      if (telaPrincipal.temTela("Manutenção de contas a receber")==false) {
        FManutRec tela = new FManutRec();
        tela.setConexao(con);
        telaPrincipal.criatela("Manutenção de contas a receber",tela);
      }
    }
    else if (iOpcao==700701000) {
      if (telaPrincipal.temTela("Estoque Mínimo")==false) {
        FREstoqueMin tela = new FREstoqueMin();
        tela.setConexao(con);
        telaPrincipal.criatela("Estoque Mínimo",tela);
      }
    }
	else if (iOpcao==700702000) {
	  if (telaPrincipal.temTela("Listagem de Produtos")==false) {
	    FRMovProd tela = new FRMovProd();
	    tela.setConexao(con);
		telaPrincipal.criatela("Listagem de Produtos",tela);
	  }     
    }
	else if (iOpcao==700600000) {
		if (telaPrincipal.temTela("Reprocessa estoque")==false) {
			FProcessaEQ tela = new FProcessaEQ();
			tela.setConexao(con);
			telaPrincipal.criatela("Reprocessa estoque",tela);
		}     
	}
	else if (iOpcao==300200000) {
      if (telaPrincipal.temTela("Cancelamento")==false) {
        FCancVenda tela = new FCancVenda();
        tela.setConexao(con);
        telaPrincipal.criatela("Cancelamento",tela);
      }
    }
    else if (iOpcao==300300000) {
      if (telaPrincipal.temTela("Devolução de vendas")==false) {
         FDevolucao tela = new FDevolucao(telaPrincipal.dpArea);
         tela.setConexao(con);
         tela.setTelaPrim(telaPrincipal);
         tela.setVisible(true);
         tela.dispose();
      }
    }
    
    else if (iOpcao==300400000) {
        if (telaPrincipal.temTela("Lançamento de Frete")==false) {
           FFrete tela = new FFrete();
           telaPrincipal.criatela("Lançamento de Frete",tela,con);
           
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
    else if (iOpcao==400420000) {
      if (telaPrincipal.temTela("Comissão")==false) {
        FRComissoes tela = new FRComissoes();
        tela.setConexao(con);
        telaPrincipal.criatela("Comissão",tela);
      }
    }
    else if (iOpcao==400200000) {
      if (telaPrincipal.temTela("Manutenção de contas a pagar")==false) {
        FManutPag tela = new FManutPag();
        tela.setConexao(con);
        telaPrincipal.criatela("Manutenção de contas a pagar",tela);
      }
    }
	else if (iOpcao==600700000) {
	  if (telaPrincipal.temTela("Tipo de crédito")==false) {
		FTipoCred tela = new FTipoCred();
		telaPrincipal.criatela("Tipo de crédito",tela,con);
	  }
	}
    else if (iOpcao==601110000) {
      if (telaPrincipal.temTela("Extrato")==false) {
        FRExtrato tela = new FRExtrato();
        tela.setConexao(con);
        telaPrincipal.criatela("Extrato",tela);
      }
    }
    else if (iOpcao==601120000) {
      if (telaPrincipal.temTela("Balancete")==false) {
        FRBalancete tela = new FRBalancete();
        tela.setConexao(con);
        telaPrincipal.criatela("Balancete",tela);
      }
    }
	else if (iOpcao==601130000) {
	  if (telaPrincipal.temTela("Relatorio Financeiro por C.C.")==false) {
		FRCentroCusto tela = new FRCentroCusto();
		tela.setConexao(con);
		telaPrincipal.criatela("Relatorio Financeiro por C.C.",tela);
	  }
	}
	else if (iOpcao==601140000) {
	  if (telaPrincipal.temTela("Razão financeiro")==false) {
		FRRazaoFin tela = new FRRazaoFin();
		tela.setConexao(con);
		telaPrincipal.criatela("Razão financeiro",tela);
	  }
	}
	else if (iOpcao==601150000) {
		if (telaPrincipal.temTela("Fluxo de caixa")==false) {
			FRFluxoCaixa tela = new FRFluxoCaixa();
			tela.setConexao(con);
			telaPrincipal.criatela("Fluxo de caixa",tela);
		}
	}
	else if (iOpcao==601210000) {
	  if (telaPrincipal.temTela("Balancete Gráfico")==false) {
		FRBalanceteGrafico tela = new FRBalanceteGrafico();
		tela.setConexao(con);
		telaPrincipal.criatela("Balancete Gráfico",tela);
	  }
	}
	else if (iOpcao==601220000) {
	  if (telaPrincipal.temTela("Gráfico Financeiro por C.C")==false) {
		FRGraficoCC tela = new FRGraficoCC();		
		tela.setConexao(con);
		telaPrincipal.criatela("Gráfico Financeiro por C.C",tela);
	  }
	}	
    else if (iOpcao==100450000) {
       if (telaPrincipal.temTela("Empresa")==false) {
         FEmpresa tela = new FEmpresa();
         telaPrincipal.criatela("FEmpresa",tela,con);
       }
    }
    else if (iOpcao==100210000) {
    	if (telaPrincipal.temTela("Alteração de doc")==false) {
    		FTrocaDoc tela = new FTrocaDoc();		
    		tela.setConexao(con);
    		telaPrincipal.criatela("Alteração de doc",tela);
    	}
    }	
    else if (iOpcao==100220000) {
    	if (telaPrincipal.temTela("Exportação SVV")==false) {
    		FSVV tela = new FSVV();		
    		tela.setConexao(con);
    		telaPrincipal.criatela("Exportação SVV",tela);
    	}
    }	
    else if (iOpcao==100230100) {
    	if (telaPrincipal.temTela("Modelo de etiquetas")==false) {
    		FModEtiqueta tela = new FModEtiqueta();		
    		telaPrincipal.criatela("Modelo de etiquetas",tela,con);
    	}
    }	
    else if (iOpcao==100230200) {                                      // LOM
    	if (telaPrincipal.temTela("Etiquetas")==false) {              // LOM
    		FREtiqueta tela = new FREtiqueta ();                       // LOM	
    		tela.setConexao(con);                                    // LOM
    		telaPrincipal.criatela("Etiquetas",tela);                // LOM
    	}
    }
     else if (iOpcao==100240000) {
    	if (telaPrincipal.temTela("Imp. tabelas de fornecedores")==false) {
    		FImpTabFor tela = new FImpTabFor();		
    		tela.setConexao(con);
    		telaPrincipal.criatela("Imp. tabelas de fornecedores",tela);
    	}
    }	
    else if (iOpcao==100250000) {
        if (telaPrincipal.temTela("Ajuste do item do orçamento")==false) {
            FStatusItOrc tela = new FStatusItOrc();     
            tela.setConexao(con);
            telaPrincipal.criatela("Ajuste do item do orçamento",tela);
        }
    }   
    else if (iOpcao==100260200) {
        if (telaPrincipal.temTela("Bloqueio e desbloqueio de vendas")==false) {
            FBloqVenda tela = new FBloqVenda();     
            tela.setConexao(con);
            telaPrincipal.criatela("Bloqueio e desbloqueio de vendas",tela);
        }
    }
    else if (iOpcao==700705000) {
    	if (telaPrincipal.temTela("Demanda")==false) {
    		FRDemanda tela = new FRDemanda();		
    		tela.setConexao(con);
    		telaPrincipal.criatela("Demanda",tela);
    	}
    }
    else if (iOpcao==700706000) {
    	if (telaPrincipal.temTela("Conferência de Estoque")==false) {
    		FRConfEstoq tela = new FRConfEstoq();		
    		tela.setConexao(con);
    		telaPrincipal.criatela("Conferência de Estoque",tela);
    	}
    }
    else if (iOpcao==700707000) {
    	if (telaPrincipal.temTela("Inventário PEPS")==false) {
    		FRInvPeps tela = new FRInvPeps();		
    		tela.setConexao(con);
    		telaPrincipal.criatela("Inventário PEPS",tela);
    	}
    }
    else if (iOpcao==1) {
    	atualizaMenus();
    }
  }

  public static void main(String sParams[]) {
    FreedomSTD freedom = new FreedomSTD();
    freedom.show();
  }
}