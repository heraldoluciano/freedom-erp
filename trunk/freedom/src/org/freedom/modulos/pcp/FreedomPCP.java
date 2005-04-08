/**
 * @version 01/09/2003 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.pcp <BR>
 * Classe:
 * @(#)FreedomPCP.java <BR>
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
 * Tela principal do módulo de produção.
 *  
 */

package org.freedom.modulos.pcp;

import java.awt.event.ActionListener;

import org.freedom.funcoes.Funcoes;
import org.freedom.modulos.std.FAlmox;
import org.freedom.modulos.std.FCLFiscal;
import org.freedom.modulos.std.FGrupo;
import org.freedom.modulos.std.FMarca;
import org.freedom.modulos.std.FProduto;
import org.freedom.modulos.std.FUnidade;
import org.freedom.telas.Aplicativo;

public class FreedomPCP extends Aplicativo implements ActionListener {
	public FreedomPCP() {

		super(
				"iconAtendimento32.gif",
				"splashPCP.jpg",
				"FreedomPCP - Modulo de planejamento e controle de produção FREEDOM",
				1, 5, "freedom.ini", null);
		addOpcao(-1, TP_OPCAO_MENU, "Arquivo", "", 'A', 100000000, 0, false,
				null);
		addOpcao(100000000, TP_OPCAO_MENU, "Tabelas", "", 'T', 100100000, 1,
				false, null);
		addOpcao(100000000, TP_OPCAO_MENU, "Preferências", "", 'F', 110100000,
				1, false, null);
		addOpcao(100100000, TP_OPCAO_MENU, "Produtos", "", 'P', 100101000, 2,
				false, null);
		addOpcao(100101000, TP_OPCAO_ITEM, "Classificação fiscal",
				"Classificação Fiscal", 'l', 100120020, 3, true,
				FCLFiscal.class);
		addOpcao(100101000, TP_OPCAO_ITEM, "Almoxarifado", "Almoxarifado", 'x',
				100120030, 3, true, FAlmox.class);
		addOpcao(100101000, TP_OPCAO_ITEM, "Grupo", "Grupo", 'r', 100120040, 3,
				true, FGrupo.class);
		addOpcao(100101000, TP_OPCAO_ITEM, "Marca", "Marca", 'c', 100120050, 3,
				true, FMarca.class);
		addOpcao(100101000, TP_OPCAO_ITEM, "Unidade", "Unidade", 'U',
				100120060, 3, true, FUnidade.class);
		addOpcao(100101000, TP_OPCAO_ITEM, "Produto", "Produto", 'P',
				100120070, 3, true, FProduto.class);

		addSeparador(100100000);
		addOpcao(100100000, TP_OPCAO_ITEM, "Estrutura", "Estrutura de produto",
				'E', 100102000, 2, true, FEstrutura.class);
		addOpcao(100100000, TP_OPCAO_ITEM, "Tipos de recursos",
				"Tipos de recursos", 'T', 100103000, 2, true, FTipoRec.class);
		addOpcao(100100000, TP_OPCAO_ITEM, "Recursos de produção",
				"Recursos de produção", 'R', 100104000, 2, true,
				FRecursos.class);
		addOpcao(100100000, TP_OPCAO_ITEM, "Fases de produção",
				"Fases de produção", 'F', 100105000, 2, true, FFase.class);

		addOpcao(110100000, TP_OPCAO_ITEM, "Preferências gerais",
				"Preferências gerais", 'G', 110101000, 2, true,
				FPrefereProd.class);

		addOpcao(-1, TP_OPCAO_MENU, "Produção", "", 'P', 200000000, 0, false,
				null);
		addOpcao(200000000, TP_OPCAO_ITEM, "Ordens de produção",
				"Ordens de produção", 'O', 200100000, 1, true, FOP.class);

		addBotao("btEstProduto.gif", "Estrutura de produto",
				"Estrutura de produto", 100102000, FPrefereProd.class);
		addBotao("btProduto.gif", "Produtos", "Produto", 100101000,
				FProduto.class);

		ajustaMenu();
	}

	public static void main(String sParams[]) {
		try {
			FreedomPCP freedom = new FreedomPCP();
			freedom.show();
		} catch (Throwable e) {
			Funcoes.criaTelaErro("Erro de execução");
			e.printStackTrace();
		}
	}
}