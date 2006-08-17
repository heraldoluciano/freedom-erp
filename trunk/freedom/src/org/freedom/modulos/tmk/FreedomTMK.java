/**
 * @version 19/09/2003 <BR>
 * @author Setpoint Informática Ltda./Robson Sanchez <BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.tmk <BR>
 * Classe:
 * @(#)FreedomTMK.java <BR>
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
 * Tela principal do módulo telemarketing.
 *  
 */

package org.freedom.modulos.tmk;

import org.freedom.funcoes.Funcoes;
import org.freedom.modulos.atd.FAtendente;
import org.freedom.modulos.atd.FTipoAtend;
import org.freedom.modulos.std.FClasCli;
import org.freedom.modulos.std.FCliente;
import org.freedom.modulos.std.FSetor;
import org.freedom.modulos.std.FTipoCli;
import org.freedom.modulos.std.FVendedor;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FPrincipalPD;

public class FreedomTMK extends Aplicativo {

	public FreedomTMK() {
		super("iconAtendimento32.gif", "splashTMK.jpg", 1, "Freedom", 7, "Telemarketing", null, new FPrincipalPD(null, "bgFreedomSTD.jpg"));
		addOpcao(-1, TP_OPCAO_MENU, "Arquivo", "", 'A', 100000000, 0, false,
				null);

		addOpcao(100000000, TP_OPCAO_MENU, "Tabelas", "", 'T', 100100000, 1,
				false, null);

		addOpcao(100100000, TP_OPCAO_MENU, "Contato", "", 'C', 1001010000, 2,
				false, null);
		addOpcao(1001010000, TP_OPCAO_ITEM, "Atividade", "Atividade", 'A',
				100101010, 3, true, FAtividade.class);
		addOpcao(1001010000, TP_OPCAO_ITEM, "Contato", "Contatos", 'C',
				100101020, 3, true, FContato.class);

		addOpcao(100100000, TP_OPCAO_MENU, "Atendente", "", 'A', 100102000, 2,
				false, null);
		addOpcao(100102000, TP_OPCAO_ITEM, "Tipo de Atendente",
				"Tipo de atendente", 'T', 100102010, 3, true, FTipoAtend.class);
		addOpcao(100102000, TP_OPCAO_ITEM, "Atendentes", "Atendente", 'A',
				100110020, 3, true, FAtendente.class);

		addOpcao(100100000, TP_OPCAO_MENU, "Cliente", "", 'C', 100104000, 2,
				false, null);
		addOpcao(100104000, TP_OPCAO_ITEM, "Setor", "Setor", 'S', 100104010, 3,
				true, FSetor.class);
		addOpcao(100104000, TP_OPCAO_ITEM, "Vendedor", "Vendedor", 'V',
				100104020, 3, true, FVendedor.class);
		addOpcao(100104000, TP_OPCAO_ITEM, "Tipos de cliente",
				"Tipo de cliente", 'T', 100104030, 3, true, FTipoCli.class);
		addOpcao(100104000, TP_OPCAO_ITEM, "Classificação de cliente",
				"Classificação do cliente", 'l', 100104040, 3, true,
				FClasCli.class);
		addSeparador(100104000);
		addOpcao(100104000, TP_OPCAO_ITEM, "Cliente", "Clientes", 'C',
				100104050, 3, true, FCliente.class);
		addSeparador(100104000);

		addOpcao(100000000, TP_OPCAO_MENU, "Preferências", "", 'P', 100200000,
				1, false, null);
		addOpcao(100200000, TP_OPCAO_ITEM, "Preferências Gerais...",
				"Pref. Gerais", 'G', 100201000, 2, true, FPrefere.class);
		addOpcao(100000000, TP_OPCAO_MENU, "Ferramentas", "", 'F', 100300000,
				1, false, null);
		addOpcao(100300000, TP_OPCAO_ITEM, "Importação de Contatos",
				"Importação de contatos", 'I', 100301000, 2, true,
				FImportaCto.class);
		addOpcao(100300000, TP_OPCAO_ITEM, "Cadastro de org.freedom.layout",
				"Cadastro de org.freedom.layout", 'C', 100302000, 2, true,
				FTipoImp.class);
		addOpcao(100300000, TP_OPCAO_ITEM, "Envio de e-mail aos contatos",
				"Envia e-mail", 'E', 100303000, 2, true, FEnviaMail.class);

		addOpcao(-1, TP_OPCAO_MENU, "Contatos", "", 'C', 200000000, 0, false,
				null);
		addOpcao(200000000, TP_OPCAO_ITEM, "Histórico", "Historico", 'H',
				200100000, 1, true, FHistorico.class);
		addOpcao(200000000, TP_OPCAO_ITEM, "Agenda", "Agenda", 'A', 200200000,
				1, true, FAgenda.class);
		addOpcao(200000000, TP_OPCAO_MENU, "Listagens", "", 'L', 200300000, 1,
				false, null);
		addOpcao(200300000, TP_OPCAO_ITEM, "Relatório diário",
				"Relatório diário", 'R', 200301000, 1, true, FRDiario.class);

		addBotao("btAtendimento.gif", "Atendimento", "Atendente", 100110020,
				FAtendente.class);

		ajustaMenu();
		
		  sNomeModulo = "Telemarkting";
		  sMailSuporte = "suporte@stpinf.com";
		  sNomeSis = "Freedom";
		  sEmpSis = "Setpoint Informática Ltda.";
		  vEquipeSis.add("Robson Sanchez - Supervisão / Analise");
		  vEquipeSis.add("Anderson Sanchez - Supervisão / Programação");
		  vEquipeSis.add("Alex Rodrigues - Programação");
		  vEquipeSis.add("Alexandre Marcondes - Programação");
		  vEquipeSis.add("Fernando Oliveira - Programação");
		  vEquipeSis.add("Moyzes Braz - Arte gráfica");
		  vEquipeSis.add("Leandro Oliveira - Testes / Suporte");
		
	}

	public static void main(String sParams[]) {
		try {
			Aplicativo.setLookAndFeel("freedom.ini");
			FreedomTMK fFreedomtmk = new FreedomTMK();
			fFreedomtmk.show();
		} catch (Throwable e) {
			Funcoes.criaTelaErro("Erro de execução");
			e.printStackTrace();
		}
	}
}