/**
 * @version 07/05/2003 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.atd <BR>
 * Classe:
 * @(#)FreedomATD.java <BR>
 * 
 * Este programa é licenciado de acordo com a LPG-PC (Licença Pública Geral para
 * Programas de Computador), <BR>
 * versão 2.1.0 ou qualquer versão posterior. <BR>
 * A LPG-PC deve acompanhar todas PUBLICAÇÕES, DISTRIBUIÇÕES e REPRODUÇÕES deste
 * Programa. <BR>
 * Caso uma cópia da LPG-PC não esteja disponível junto com este Programa, você
 * pode contatar <BR>
 * o LICENCIADOR ou então pegar uma cópia em: <BR>
 * Licença: http://www.lpg.adv.br/licencas/lpgpc.rtf <BR>
 * Para poder USAR, PUBLICAR, DISTRIBUIR, REPRODUZIR ou ALTERAR este Programa é
 * preciso estar <BR>
 * de acordo com os termos da LPG-PC <BR>
 * <BR>
 * 
 * Tela principal do módulo de atendimento.
 *  
 */

package org.freedom.modulos.atd;

import java.awt.event.ActionListener;

import org.freedom.funcoes.Funcoes;
import org.freedom.modulos.grh.FFuncao;
import org.freedom.modulos.std.FCliente;
import org.freedom.modulos.std.FOrcamento;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.AplicativoPD;
import org.freedom.telas.FPrincipalPD;
import org.freedom.telas.LoginPD;

public class FreedomATD extends AplicativoPD implements ActionListener {
	public FreedomATD() {
		super("iconAtendimento32.gif", "splashATD.jpg",  1, "Freedom" , 4, "Atendimento",  null,new FPrincipalPD(null, "bgFreedomSTD.jpg"),LoginPD.class);
 
		//Arquivo:
		addOpcao(-1, TP_OPCAO_MENU, "Arquivo", "", 'A', 100000000, 0, false,null);
		addOpcao(100000000, TP_OPCAO_MENU, "Tabelas", "", 'T', 100100000, 1,false, null);
		addOpcao(100100000, TP_OPCAO_MENU, "Conveniado", "", 'C', 100101000, 2,false, null);
		addOpcao(100101000, TP_OPCAO_ITEM, "Tipo de Conveniado","Tipo de conveniados", 'T', 100101010, 3, true, FTipoConv.class);
		addOpcao(100101000, TP_OPCAO_ITEM, "Atribuições", "Abribuições", 'A', 100101020, 3, true, FAtribuicao.class);
		addOpcao(100101000, TP_OPCAO_ITEM, "Encaminhador", "Encaminhador", 'E', 100101030, 3, true, FEncaminhador.class);
		addOpcao(100101000, TP_OPCAO_ITEM, "Funções", "Funções", 'F', 100101040, 3, true, FFuncao.class );
		addOpcao(100101000, TP_OPCAO_ITEM, "Conveniados", "Conveniados", 'C', 100101050, 3, true, FConveniado.class);
		addOpcao(101000000, TP_OPCAO_MENU, "Atendente", "", 'A', 100102000, 2, false, null);
		addOpcao(100102000, TP_OPCAO_ITEM, "Tipo de Atendente", "Tipo de atendentes", 'T', 100102010, 3, true, FTipoAtend.class);
		addOpcao(100102000, TP_OPCAO_ITEM, "Atendentes", "Atendentes", 'A', 100102020, 3, true, FAtendente.class);
		addOpcao(100100000, TP_OPCAO_MENU, "Atendimento", "", 't', 100103000, 2, false, null);
		addOpcao(100103000, TP_OPCAO_ITEM, "Setor de Atendimento", "Setor de Atendimento", 'S', 100103010, 3, true, FSetorAtend.class);
		addSeparador(100103000);
		addOpcao(100103000, TP_OPCAO_ITEM, "Atendente", "Atendente", 'e', 100103020, 1, true, FAtendente.class);
		addOpcao(100103000, TP_OPCAO_ITEM, "Tipo de Atendente", "Tipo de Atendente", 'i', 100103030, 1, true, FTipoAtend.class);
		addSeparador(100100000);
		addOpcao(100100000, TP_OPCAO_ITEM, "Clientes", "Clientes", 'l', 100104000, 2, true, FCliente.class);
		addSeparador(100100000);
		addOpcao(100000000, TP_OPCAO_MENU, "Preferências", "", 'P', 100200000, 1, false, null);
		addOpcao(100200000, TP_OPCAO_ITEM, "Preferências Gerais...", "Pref. Gerais", 'P', 100201000, 2, true, FPrefereAtend.class);

		//Atendimento:
		addOpcao(-1, TP_OPCAO_MENU, "Atendimento", "", 't', 110000000, 0, false, null);
		addOpcao(110000000, TP_OPCAO_ITEM, "Atendimento", "Atendimento", 'A', 110100000, 1, true, FAtendimento.class);
		addSeparador(110000000);
		addOpcao(110000000, TP_OPCAO_ITEM, "Tipos de Atendimento", "Tipo de atendimento", 'T', 110200000, 1, true, FTipoAtendo.class);

		//Orçamento:
		addOpcao(-1, TP_OPCAO_MENU, "Orçamento", "", 'O', 1200000000, 0, false, null);
		addOpcao(1200000000, TP_OPCAO_ITEM, "Orçamento Atendimento", "Orcamento Atendimento", 'O', 120100000, 1, true, FOrcamentoATD.class);
		addOpcao(1200000000, TP_OPCAO_ITEM, "Orçamento", "Orcamento", 'O', 120100001, 1, true, FOrcamento.class);
		addSeparador(1200000000);
		addOpcao(1200000000, TP_OPCAO_ITEM, "Pesquisa Orçamentos", "Pesquisa Orcamentos", 'P', 120200000, 1, true, FConsOrc.class);
		addOpcao(1200000000, TP_OPCAO_ITEM, "Aprova Orçamentos", "Aprova Orcamento", 'A', 120300000, 1, true, FAprovaOrc.class);
		addOpcao(1200000000, TP_OPCAO_ITEM, "Pesquisa Autorizações", "Pesquisa Autorização", 'e', 120400000, 1, true, FConsAutoriz.class);

		addBotao("btAtendimento.gif", "Atendimento", "Atendimento", 110100000, FAtendimento.class);
		addBotao("btOrcamento.gif", "Orçamento", "Orcamento", 120100000, FOrcamentoATD.class);			
		addBotao("btConsOrcamento.gif", "Pesquisa Orçamento", "Pesquisa Orcamentos", 120200000, FConsOrc.class);
		addBotao("barraConveniados.gif", "Conveniados", "Conveniados", 100101040, FConveniado.class);
		addBotao("btAprovaOrc.gif", "Aprovações de Orçamantos", "Aprova Orcamento", 120300000, FAprovaOrc.class);

		ajustaMenu();

		sNomeModulo = "Atendimento";
		sMailSuporte = "suporte@stpinf.com";
		sNomeSis = "Freedom";
		sEmpSis = "Setpoint Informática Ltda.";
		vEquipeSis.add("Robson Sanchez - Supervisão / Analise");
		vEquipeSis.add("Anderson Sanchez - Supervisão / Programação");
		vEquipeSis.add("Alex Rodrigues - Programação");
		vEquipeSis.add("Alexandre Marcondes - Programação");
		vEquipeSis.add("Fernando Oliveira - Programação");
		vEquipeSis.add("Moyzes Braz - Arte gráfica");
		vEquipeSis.add("Reginaldo Garcia - Testes / Suporte");		
		
	}

	public static void main(String sParams[]) {
		try {
			Aplicativo.setLookAndFeel("freedom.ini");
			FreedomATD freedomatd = new FreedomATD();
			freedomatd.show();
		} catch (Throwable e) {
			Funcoes.criaTelaErro("Erro de execução");
			e.printStackTrace();
		}
	}
}