/**
 * @version 29/12/2003 <BR>
 * @author Setpoint Informática Ltda.
 * @author Reginaldo Garcia Heua. <BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.fnc <BR>
 * Classe:
 * @(#)FreedomFNC.java <BR>
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
 * 
 * Tela principal do módulo financeiro.
 * 
 */

package org.freedom.modulos.lvf;

import org.freedom.funcoes.Funcoes;
import org.freedom.modulos.std.FClasCli;
import org.freedom.modulos.std.FCliente;
import org.freedom.modulos.std.FCredCli;
import org.freedom.modulos.std.FEmpresa;
import org.freedom.modulos.std.FEstacao;
import org.freedom.modulos.std.FFornecedor;
import org.freedom.modulos.std.FGeraFiscal;
import org.freedom.modulos.std.FImpressora;
import org.freedom.modulos.std.FLanca;
import org.freedom.modulos.std.FManutPag;
import org.freedom.modulos.std.FManutRec;
import org.freedom.modulos.std.FMensagem;
import org.freedom.modulos.std.FMoeda;
import org.freedom.modulos.std.FPapel;
import org.freedom.modulos.std.FRImpServ;
import org.freedom.modulos.std.FRPisCofins;
import org.freedom.modulos.std.FRVendasIcms;
import org.freedom.modulos.std.FRegraFiscal;
import org.freedom.modulos.std.FSetor;
import org.freedom.modulos.std.FSintegra;
import org.freedom.modulos.std.FTabICMS;
import org.freedom.modulos.std.FTipoCli;
import org.freedom.modulos.std.FTipoFor;
import org.freedom.modulos.std.FVendedor;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.AplicativoPD;
import org.freedom.telas.FPrincipalPD;
import org.freedom.telas.LoginPD;

public class FreedomLVF extends AplicativoPD {

	public FreedomLVF() {

		super( "iconStandart32.gif", "splashFNC.jpg", 1, "Freedom", 10, "Livros Fiscais", null, new FPrincipalPD( null, "bgFreedomSTD.jpg" ), LoginPD.class );
		
		addOpcao( -1, TP_OPCAO_MENU, "Arquivo", "", 'A', 100000000, 0, false, null );
			addOpcao( 100000000, TP_OPCAO_MENU, "Tabelas", "", 'T', 100100000, 1, false, null );
				addOpcao( 100100000, TP_OPCAO_MENU, "Cliente", "", 'C', 100101000, 2, false, null );
					addOpcao( 100101000, TP_OPCAO_ITEM, "Setor", "Setor", 'S', 100101010, 3, true, FSetor.class );
					addOpcao( 100101000, TP_OPCAO_ITEM, "Comissionado", "Comissionado", 's', 100101020, 3, true, FVendedor.class );
					addOpcao( 100101000, TP_OPCAO_ITEM, "Tipo de cliente", "TipoCli", 'T', 100101030, 3, true, FTipoCli.class );
					addOpcao( 100101000, TP_OPCAO_ITEM, "Classificação de cliente", "Classifição de Clientes", 'f', 100101040, 3, true, FClasCli.class );
					addOpcao( 100101000, TP_OPCAO_ITEM, "Cliente", "Clientes", 'C', 100101050, 3, true, FCliente.class );
					addOpcao( 100101000, TP_OPCAO_ITEM, "Crédito por cliente", "Crédito por cliente", 'r', 100101060, 3, true, FCredCli.class );
				addSeparador( 100100000 );
				addOpcao( 100100000, TP_OPCAO_ITEM, "Moeda", "Moeda", 'M', 100102000, 2, true, FMoeda.class );
				addSeparador( 100100000 );
				addOpcao( 100100000, TP_OPCAO_ITEM, "Tipo de fornecedor", "TipoFor", 'i', 100107000, 2, true, FTipoFor.class );
				addOpcao( 100100000, TP_OPCAO_ITEM, "Fornecedor", "Fornecedor", 'F', 100108000, 2, true, FFornecedor.class );
				addSeparador( 100100000 );
			addOpcao( 100000000, TP_OPCAO_MENU, "Ferramentas", "", 'F', 100200000, 1, false, null );
			addOpcao( 100000000, TP_OPCAO_MENU, "Preferências", "", 'P', 100300000, 1, false, null );
			addOpcao( 100000000, TP_OPCAO_MENU, "Configurações", "", 'C', 100400000, 1, false, null );
				addOpcao( 100400000, TP_OPCAO_ITEM, "Estação de trabalho", "Estações de trabalho", 't', 100401000, 2, true, FEstacao.class );
				addOpcao( 100400000, TP_OPCAO_ITEM, "Impressora", "Impressoras", 'I', 100402000, 2, true, FImpressora.class );
				addOpcao( 100400000, TP_OPCAO_ITEM, "Papel", "Papeis", 'P', 100403000, 2, true, FPapel.class );
				addSeparador( 100400000 );
				addOpcao( 100400000, TP_OPCAO_ITEM, "Empresa", "Empresa", 'E', 100404000, 2, true, FEmpresa.class );
				
		addOpcao( -1, TP_OPCAO_MENU, "Fiscal", "", 'F', 200000000, 0, false, null );
			addOpcao( 200000000, TP_OPCAO_ITEM, "Gerar", "Gera Fiscal", 'G', 200100000, 1, true, FGeraFiscal.class );
			addOpcao( 200000000, TP_OPCAO_ITEM, "Regras fiscais", "Regras Fiscais", 'R', 200200000, 1, true, FRegraFiscal.class );
			addOpcao( 200000000, TP_OPCAO_ITEM, "Sintegra", "Gera Arquivo Sintegra", 'S', 200300000, 1, true, FSintegra.class );
			addOpcao( 200000000, TP_OPCAO_ITEM, "Tabela de Alíquotas", "Tabela de alíquotas", 'T', 200400000, 1, true, FTabICMS.class );
			addOpcao( 200000000, TP_OPCAO_ITEM, "Mensagens", "Mensagens", 'M', 200500000, 1, true, FMensagem.class );
			addOpcao( 200000000, TP_OPCAO_MENU, "Listagens", "", 'L', 200600000, 1, false, null );
			addOpcao( 200600000, TP_OPCAO_ITEM, "ICMS sobre vendas", "Icms Vendas e Compras", 'I', 200601000, 2, true, FRVendasIcms.class );
			addOpcao( 200600000, TP_OPCAO_ITEM, "Impostos sobre serviços", "Impostos sobre serviços", 'S', 200602000, 2, true, FRImpServ.class );
			addOpcao( 200600000, TP_OPCAO_ITEM, "Pis e cofins", "Pis e cofins", 'P', 200603000, 2, true, FRPisCofins.class );
			
		addOpcao( -1, TP_OPCAO_MENU, "Entrada", "", 'E', 300000000, 0, false, null );
			addOpcao( 300000000, TP_OPCAO_ITEM, "Registro de Entrada", "Registro de Entrada", 'E', 300100000, 1, true, FRRegitroEntrada.class );
		
		addOpcao( -1, TP_OPCAO_MENU, "Saida", "", 'S', 400000000, 0, false, null );
			addOpcao( 400000000, TP_OPCAO_ITEM, "Registro de Saida", "Registro de Saida", 'S', 400100000, 1, true, FRRegitroSaida.class );
		
		addBotao( "barraUsuario.gif", "Cliente", "Clientes", 100101050, FCliente.class );
		addBotao( "btContaPagar.gif", "Contas a pagar", "Manutenção de contas a pagar", 200100000, FManutPag.class );
		addBotao( "btContaReceber.gif", "Contas a receber", "Manutenção de contas a receber", 300100000, FManutRec.class );
		addBotao( "btLancamentoFin.gif", "Lançamentos financeiros", "Lançamentos", 400600000, FLanca.class );
		
		ajustaMenu();

		sNomeModulo = "Financeiro";
		sNomeSis = "Freedom";
		sMailSuporte = "suporte@stpinf.com";
		sEmpSis = "Setpoint Informática Ltda.";
		vEquipeSis.add( "Robson Sanchez - Supervisão / Analise" );
		vEquipeSis.add( "Anderson Sanchez - Supervisão / Programação" );
		vEquipeSis.add( "Sidnei Varanis - Supervisão" );
		vEquipeSis.add( "Alex Rodrigues - Programação" );
		vEquipeSis.add( "Alexandre Marcondes - Programação" );
		vEquipeSis.add( "Fernando Oliveira - Programação" );
		vEquipeSis.add( "Moyzes Braz - Arte gráfica" );
		vEquipeSis.add( "Reginaldo Garcia - Testes / Suporte" );

	}

	public static void main( String sParams[] ) {

		try {
			Aplicativo.setLookAndFeel( "freedom.ini" );
			FreedomLVF freedom = new FreedomLVF();
			freedom.show();
		} catch ( Throwable e ) {
			Funcoes.criaTelaErro( "Erro de execução\n\n\n" + e.getMessage() );
			e.printStackTrace();
		}
	}
}
