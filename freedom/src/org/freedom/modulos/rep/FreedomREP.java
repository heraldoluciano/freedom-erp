/**
 * @version 02/2007 <BR>
 * @author Setpoint Informática Ltda.<BR>
 * @author Alex Rodrigues<BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.pcp <BR>
 * Classe:
 * @(#)FreedomREP.java <BR>
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
 * Tela principal do módulo de gestão para representações.
 * 
 */

package org.freedom.modulos.rep;

import java.awt.event.ActionListener;

import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FPrincipalPD;
import org.freedom.telas.LoginPD;

public class FreedomREP extends AplicativoRep implements ActionListener {

	public FreedomREP() {

		super( "iconRep32.gif", "splashREP.jpg", 1, "Freedom", 11, "Representações Comerciais", null, new FPrincipalPD( null, "bgFreedomSTD.jpg" ), LoginPD.class );
		
		addOpcao( -1, TP_OPCAO_MENU, "Arquivo", "", 'A', 100000000, 0, false, null );
			addOpcao( 100000000, TP_OPCAO_MENU, "Tabelas", "", 'T', 100100000, 1, false, null );
				addOpcao( 100100000, TP_OPCAO_ITEM, "Cliente", "Cliente", 'C', 100101000, 2, true, RPCliente.class );
				addOpcao( 100100000, TP_OPCAO_ITEM, "Tipo de cliente", "Tipo de cliente", 't', 100102000, 2, true, RPTipoCli.class );
				addOpcao( 100100000, TP_OPCAO_ITEM, "Contato", "Contato", 'o', 100103000, 2, true, RPContato.class );
				addOpcao( 100100000, TP_OPCAO_ITEM, "Vendedor", "Vendedor", 'V', 100104000, 2, true, RPVendedor.class );
				addSeparador( 100100000 );
				addOpcao( 100100000, TP_OPCAO_ITEM, "Fornecedor", "Fornecedor", 'F', 100105000, 2, true, RPFornecedor.class );
				addOpcao( 100100000, TP_OPCAO_ITEM, "Transportadoras", "Transportadoras", 'T', 100106000, 2, true, RPTransportadora.class );
				addSeparador( 100100000 );
				addOpcao( 100100000, TP_OPCAO_ITEM, "Grupos", "Grupos", 'G', 100107000, 2, true, RPGrupo.class );
				addOpcao( 100100000, TP_OPCAO_ITEM, "Unidades", "Unidades", 'U', 100108000, 2, true, RPUnidade.class );
				addOpcao( 100100000, TP_OPCAO_ITEM, "Produtos", "Produtos", 'P', 100109000, 2, true, RPProduto.class );
				addSeparador( 100100000 );
				addOpcao( 100100000, TP_OPCAO_ITEM, "Planos de pagamento", "Planos de pagamento", 'r', 100110000, 2, true, RPPlanoPag.class );
				addOpcao( 100100000, TP_OPCAO_ITEM, "Moeda", "Moeda", 'M', 100111000, 2, true, RPMoeda.class );
				addOpcao( 100100000, TP_OPCAO_ITEM, "Cotação", "Cotação", 'a', 100112000, 2, true, RPCotMoeda.class );
			addSeparador( 100000000 );
			addOpcao( 100000000, TP_OPCAO_ITEM, "Preferências", "Preferências", 'P', 100200000, 1, true, RPPrefereGeral.class );
			addSeparador( 100000000 );
			addOpcao( 100000000, TP_OPCAO_ITEM, "Estação de trabalho", "Estação de trabalho", 't', 100300000, 1, true, RPEstacao.class );
			addOpcao( 100000000, TP_OPCAO_ITEM, "Empresa", "Empresa", 'E', 100400000, 1, true, RPEmpresa.class );

		addOpcao( -1, TP_OPCAO_MENU, "Vendas", "", 'V', 200000000, 0, false, null );
			addOpcao( 200000000, TP_OPCAO_ITEM, "Pedidos", "Pedidos", 'P', 200100000, 1, true, RPPedido.class );
			addOpcao( 200000000, TP_OPCAO_ITEM, "Notas", "Notas", 'N', 200200000, 1, true, null );
			addSeparador( 200000000 );
			addOpcao( 200000000, TP_OPCAO_ITEM, "Consuta de pedidos", "Consuta de pedidos", 'C', 200300000, 1, true, RPConsPedido.class );

		addOpcao( -1, TP_OPCAO_MENU, "Pagar", "Pagar", 'P', 300000000, 0, false, null );
			addOpcao( 300000000, TP_OPCAO_ITEM, "Pagar", "Pagar", 'P', 300100000, 1, true, RPPagar.class );
		
		addOpcao( -1, TP_OPCAO_MENU, "Receber", "Receber", 'R', 400000000, 0, false, null );
			addOpcao( 400000000, TP_OPCAO_ITEM, "Receber", "Receber", 'R', 400100000, 1, true, RPReceber.class );
		
		addOpcao( -1, TP_OPCAO_MENU, "Relatorios", "", 'l', 500000000, 0, false, null );
			addOpcao( 500000000, TP_OPCAO_MENU, "Vendas", "", 'V', 500100000, 1, false, null );
			addOpcao( 500100000, TP_OPCAO_ITEM, "Pedidos", "Pedidos", 'P', 500101000, 2, true, RelPedido.class );
				addOpcao( 500100000, TP_OPCAO_ITEM, "Resumo Diario", "Resumo Diario", 'R', 500102000, 2, true, RelResumoDiario.class );
				addOpcao( 500100000, TP_OPCAO_ITEM, "Histórico de clientes", "Histórico de clientes", 'H', 500103000, 2, true, RelHistoricoCliente.class );
				addSeparador( 500100000 );
				addOpcao( 500100000, TP_OPCAO_ITEM, "Pedidos pendentes", "Pedidos pendentes", 'p', 500104000, 2, true, null );
				addOpcao( 500100000, TP_OPCAO_ITEM, "Relação de Notas", "Relação de Notas", 'N', 500105000, 2, true, null );
				addSeparador( 500100000 );
				addOpcao( 500100000, TP_OPCAO_ITEM, "Evolução de vendas", "Evolução de vendas", 'E', 500107000, 2, true, RelEvolucaoVendas.class );
			addOpcao( 500000000, TP_OPCAO_MENU, "Clientes", "", 'C', 500200000, 1, false, null );
				addOpcao( 500200000, TP_OPCAO_ITEM, "Clientes", "Clientes", 'C', 500201000, 2, true, RelCliente.class );
				addOpcao( 500200000, TP_OPCAO_ITEM, "Tipos de cliente", "Tipos de cliente", 'T', 500202000, 2, true, RelTipoCli.class );
				addOpcao( 500200000, TP_OPCAO_ITEM, "Contatos", "Contatos", 'o', 500203000, 2, true, RelContato.class );
				//addOpcao( 500200000, TP_OPCAO_ITEM, "Etiquetas", "Etiquetas", 'E', 500204000, 2, true, null );
			addOpcao( 500000000, TP_OPCAO_MENU, "Pagar", "", 'P', 500300000, 1, false, null );
				addOpcao( 500300000, TP_OPCAO_ITEM, "Pagar / Pagas", "Pagar / Pagas", 'C', 500301000, 2, true, null );
			addOpcao( 500000000, TP_OPCAO_MENU, "Receber", "", 'R', 500400000, 1, false, null );
				addOpcao( 500400000, TP_OPCAO_ITEM, "Receber / Recebidas", "Receber / Recebidas", 'C', 500401000, 2, true, null );
			addSeparador( 500000000 );
			addOpcao( 500000000, TP_OPCAO_ITEM, "Vendedores", "Vendedores", 'V', 500500000, 1, true, RelVendedor.class );
			addOpcao( 500000000, TP_OPCAO_ITEM, "Fornecedores", "Fornecedores", 'F', 500600000, 1, true, RelFornecedor.class );
			addOpcao( 500000000, TP_OPCAO_ITEM, "Grupos", "Grupos", 'G', 500700000, 1, true, RelGrupo.class );
			addOpcao( 500000000, TP_OPCAO_ITEM, "Produtos", "Produtos", 'P', 500800000, 1, true, RelProduto.class );
			
			addBotao( "btCliente.gif", "Cliente", "Cliente", 100101000, RPCliente.class );
			addBotao( "btAtendimento.gif", "Vendedor", "Vendedor", 100104000, RPVendedor.class );
			addBotao( "btForneced.gif", "Fornecedor", "Fornecedor", 100105000, RPFornecedor.class );
			addBotao( "btProduto.gif", "Produtos", "Produtos", 100108000, RPProduto.class );
			addBotao( "btOP.gif", "Pedidos", "Pedidos", 200100000, RPPedido.class );
			addBotao( "btSaida.gif", "Notas", "Notas", 200200000, null );
			addBotao( "btContaPagar.gif", "Pagar", "Pagar", 300100000, RPPagar.class );
			addBotao( "btContaReceber.gif", "Receber", "Receber", 400100000, RPReceber.class );
		
		ajustaMenu();

		sNomeModulo = "Representação";
		sMailSuporte = "suporte@stpinf.com";
		sNomeSis = "Freedom";
		sEmpSis = "Setpoint Informática Ltda.";
		vEquipeSis.add( "Robson Sanchez - Supervisão / Analise" );
		vEquipeSis.add( "Anderson Sanchez - Supervisão / Programação" );
		vEquipeSis.add( "Alex Rodrigues - Programação" );
		vEquipeSis.add( "Moyzes Braz - Arte gráfica" );
		vEquipeSis.add( "Reginaldo Garcia - Testes / Suporte" );

	}

	public static void main( String sParams[] ) {

		try {
			Aplicativo.setLookAndFeel( "freedom.ini" );
			FreedomREP freedom = new FreedomREP();
			freedom.show();
		} catch ( Throwable e ) {
			Funcoes.criaTelaErro( "Erro de execução" );
			e.printStackTrace();
		}
	}
}
