/**
 * @version 10/06/2003 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.pdv <BR>
 * Classe:
 * @(#)FreedomPDV.java <BR>
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
 * Tela principal do múdulo ponto de venda.
 *  
 */

package org.freedom.modulos.pdv;

import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;

import org.freedom.componentes.JButtonPad;
import org.freedom.drivers.JBemaFI32;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;

public class FreedomPDV extends Aplicativo implements ActionListener {
	public static boolean bECFTerm = false;

	public static boolean bTEFTerm = false;

	public static boolean bModoDemo = true;

	public FreedomPDV() {
		super("iconConfiguracao32.gif", "splashPDV.jpg",
				"FreedomPDV - Ponto de Venda", 1, 3);
		addOpcao(-1, TP_OPCAO_MENU, "Arquivo", "", 'A', 100000000, 0, false,
				null);
		addOpcao(100000000, TP_OPCAO_MENU, "Tabelas", "", 'T', 100100000, 1,
				false, null);
		addOpcao(100000000, TP_OPCAO_MENU, "Preferências", "", 'P', 100200000,
				1, false, null);
		addOpcao(100200000, TP_OPCAO_ITEM, "Preferências gerais",
				"Prefere Geral", 'g', 100210000, 2, true, FPrefere.class);

		addOpcao(-1, TP_OPCAO_MENU, "PDV", "", 'P', 110000000, 1, false, null);
		addOpcao(110000000, TP_OPCAO_ITEM, "Venda", "Venda", 'V', 100101000, 2,
				true, FVenda.class);
		JButtonPad btVenda = addBotao("barraVenda.gif", "Venda", "", 100101000,
				null);
		addOpcao(110000000, TP_OPCAO_ITEM, "Cancela venda", "Cancela Venda",
				'C', 110200000, 2, true, DLCancCupom.class);
		addBotao("btExcluir.gif", "Cancela venda", "Cancela Venda", 110200000,
				DLCancCupom.class);

		addSeparador(110000000);

		addOpcao(110000000, TP_OPCAO_ITEM, "Suprimento", "Suprimento de caixa",
				'S', 110300000, 2, true, FSuprimento.class);
		addBotao("barraFornecedor.gif", "Suprimento", "Suprimento de caixa",
				110300000, FSuprimento.class);
		addOpcao(110000000, TP_OPCAO_ITEM, "Sangria", "Sangria", 'G',
				110400000, 2, true, FSangria.class);
		addBotao("btPdvSangria.gif", "Sangria", "Sangria", 110400000,
				FSangria.class);

		addSeparador(110000000);

		addOpcao(110000000, TP_OPCAO_ITEM, "Aliquota", "Inserir Aliquota", 'Q',
				110500000, 2, true, FAliquota.class);
		addBotao("btPdvAliquota.gif", "Aliquota", "Inserir Aliquota",
				110500000, FAliquota.class);
		addOpcao(110000000, TP_OPCAO_ITEM, "Ajusta moeda", "Grava Moeda", 'J',
				110600000, 2, true, FGravaMoeda.class);
		addBotao("btPdvGravaMoeda.gif", "Ajusta moeda", "Grava Moeda",
				110600000, FGravaMoeda.class);

		addSeparador(110000000);

		addOpcao(110000000, TP_OPCAO_ITEM, "Ler memória fiscal", "Le Fiscal",
				'L', 110700000, 2, true, FLeFiscal.class);
		addOpcao(110000000, TP_OPCAO_ITEM, "Leitura X",
				"Confirma impressão de leitura X ?", 'i', 110800000, 2, true,
				JBemaFI32.class);

		addBotao("btPdvLeituraXPq.gif", "Ler memória fiscal", "Le Fiscal",
				110700000, FLeFiscal.class);
		if (abrecaixa()) {
			btVenda.doClick();
		} else {
			killProg(5, "Caixa não foi aberto. A aplicação será fechada!");
		}
	}

	private boolean abrecaixa() {
		boolean bRetorno = false;
		int iRet = 0;
		try {
			PreparedStatement ps = con
					.prepareStatement("SELECT IRETORNO FROM PVVERIFCAIXASP(?,?,?,?,?,?)"); // caixa,
																						   // emp,
																						   // filial
			ps.setInt(1, iNumEst);
			ps.setInt(2, iCodEmp);
			ps.setInt(3, iCodFilial);
			ps.setDate(4, Funcoes.dateToSQLDate(new Date()));
			ps.setInt(5, iCodFilialPad);
			ps.setString(6, strUsuario);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				iRet = rs.getInt(1);
				switch (iRet) {
				case 0: {
					bRetorno = pegaValorINI();
					break;
				}
				//case 1: { Reservado.
				case 2: {
					Funcoes.mensagemInforma(null, "Caixa já está aberto!");
					bRetorno = true;
					break;
				}
				case 3: {
					killProg(3,
							"Já foi realizada leitura \"Z\" neste caixa hoje!");
					break;
				}
				case 4: {
					killProg(4, "Caixa foi aberto com outro usuário!");
					break;
				}
				default: {
					killProg(5, "Erro na ultima transacão de caixa.");
					break;
				}
				}
			} else {
				killProg(5, "Não foi possível abrir o caixa!");
			}
		} catch (Exception err) {
			killProg(6, "Erro abrir o caixa!\n" + err.getMessage());
		}
		String sSQL = "SELECT CX.ECFCAIXA,CX.TEFCAIXA,(SELECT MODODEMOEST FROM SGESTACAO EST"
				+ " WHERE EST.CODEMP=CX.CODEMPET AND EST.CODFILIAL=CX.CODFILIALET AND"
				+ " EST.CODEST=CX.CODEST) FROM PVCAIXA CX WHERE CODCAIXA=?"
				+ " AND CODFILIAL=? AND CODEMP=?";
		try {
			PreparedStatement ps = con.prepareStatement(sSQL);
			ps.setInt(1, Aplicativo.iNumEst);
			ps.setInt(2, Aplicativo.iCodFilial);
			ps.setInt(3, Aplicativo.iCodEmp);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				if (rs.getString("ECFCaixa").equals("S"))
					bECFTerm = true;
				else
					bECFTerm = false;
				if (rs.getString("TEFCaixa").equals("S"))
					bTEFTerm = true;
				else
					bTEFTerm = false;
				if (rs.getString(3).equals("S"))
					bModoDemo = true;
				else
					bModoDemo = false;
			}
			rs.close();
			ps.close();
		} catch (Exception err) {
			killProg(6, "Erro ao verificar o caixa!\n" + err.getMessage());
		}
		return bRetorno;
	}

	public boolean pegaValorINI() {
		boolean bRetorno = false;
		FAbreCaixa tela = new FAbreCaixa();
		tela.setConexao(con);
		tela.setVisible(true);
		bRetorno = tela.OK;
		return bRetorno;
	}

	public static void main(String sParams[]) {
		try {
			FreedomPDV freedom = new FreedomPDV();
			freedom.show();
		} catch (Throwable e) {
			Funcoes.criaTelaErro("Erro de execução");
			e.printStackTrace();
		}
	}
}