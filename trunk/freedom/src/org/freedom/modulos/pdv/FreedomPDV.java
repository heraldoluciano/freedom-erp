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
import org.freedom.modulos.std.FAlmox;
import org.freedom.modulos.std.FBanco;
import org.freedom.modulos.std.FCLFiscal;
import org.freedom.modulos.std.FClasCli;
import org.freedom.modulos.std.FCliente;
import org.freedom.modulos.std.FCpProd;
import org.freedom.modulos.std.FCredCli;
import org.freedom.modulos.std.FGrade;
import org.freedom.modulos.std.FGrupo;
import org.freedom.modulos.std.FManutPreco;
import org.freedom.modulos.std.FMarca;
import org.freedom.modulos.std.FModGrade;
import org.freedom.modulos.std.FMoeda;
import org.freedom.modulos.std.FPlanoPag;
import org.freedom.modulos.std.FProduto;
import org.freedom.modulos.std.FRListaPreco;
import org.freedom.modulos.std.FSimilar;
import org.freedom.modulos.std.FTabPreco;
import org.freedom.modulos.std.FTipoCli;
import org.freedom.modulos.std.FTipoFiscCli;
import org.freedom.modulos.std.FTratTrib;
import org.freedom.modulos.std.FUnidade;
import org.freedom.modulos.std.FVariantes;
import org.freedom.telas.Aplicativo;

public class FreedomPDV extends Aplicativo implements ActionListener {
	public static boolean bECFTerm = false;

	public static boolean bTEFTerm = false;

	public static boolean bModoDemo = true;

	public FreedomPDV() {
		super("iconConfiguracao32.gif", "splashPDV.jpg",
				"FreedomPDV - Ponto de Venda", 1, 3);
		addOpcao(-1, TP_OPCAO_MENU, "Arquivo", "", 'A', 100000000, 0, false,null);
			addOpcao(100000000, TP_OPCAO_MENU, "Tabelas", "", 'T', 100100000, 1, false, null);
				addOpcao(100100000,TP_OPCAO_MENU,"Cliente","",'C',100101000,2, false, null);
			      addOpcao(100101000,TP_OPCAO_ITEM,"Tipo de cliente...","TipoCli",'T',100101010,3, true, FTipoCli.class);
			      addOpcao(100101000,TP_OPCAO_ITEM,"Classificação de cliente...", "Classifição de Clientes", 'f',100101020,3, true, FClasCli.class);
			      addOpcao(100101000,TP_OPCAO_ITEM,"Cliente...", "Clientes",'C',100101030,3, true, FCliente.class);
				  addOpcao(100101000,TP_OPCAO_ITEM,"Tipo fiscal de cliente...","Tipo Fiscal de Cliente",'p',100101040,3, true, FTipoFiscCli.class);
				  addOpcao(100101000,TP_OPCAO_ITEM,"Crédito por cliente...","Crédito por cliente",'r',100101050,3, true, FCredCli.class);
			    		      
		        addSeparador(100100000);
		        addOpcao(100100000,TP_OPCAO_ITEM,"Moeda","Moeda",'M',100102000,2, true, FMoeda.class);
			    addOpcao(100100000,TP_OPCAO_ITEM,"Banco","Banco",'B',100103000,2, true, FBanco.class);
			    addOpcao(100100000,TP_OPCAO_ITEM,"Plano de pagamento","PlanoPag",'s',100115000,2, true, FPlanoPag.class);
			       
			    addSeparador(100100000);
			    addOpcao(100100000,TP_OPCAO_MENU,"Produto","",'u',100104000,2, false, null);
			       addOpcao(100104000,TP_OPCAO_ITEM,"Tratamento tributário","Tratamento Tributário",'t',100104010,3, true, FTratTrib.class);
			       addOpcao(100104000,TP_OPCAO_ITEM,"Classificação fiscal","Classificações",'l',100104020,3, true, FCLFiscal.class);
			       addOpcao(100104000,TP_OPCAO_ITEM,"Almoxarifado","Almoxarifado",'x',100104030,3, true, FAlmox.class);
			       addOpcao(100104000,TP_OPCAO_ITEM,"Grupo","Grupos",'r',100104040,3, true, FGrupo.class);
			       addOpcao(100104000,TP_OPCAO_ITEM,"Marca","Marcas",'c',100104050,3, true, FMarca.class);
			       addOpcao(100104000,TP_OPCAO_ITEM,"Unidade","Unidades",'U',100104060,3, true, FUnidade.class);
			       addOpcao(100104000,TP_OPCAO_ITEM,"Produto","Produtos",'P',100104070,3, true, FProduto.class);
			       addSeparador(100104000);
		           addOpcao(100104000,TP_OPCAO_ITEM,"Similaridade","Similar",'S',100104080,3, true, FSimilar.class);
		           addOpcao(100104000,TP_OPCAO_MENU,"Grade de produtos","",'G',100104090,3, false, null);        
		    	      addOpcao(100104090,TP_OPCAO_ITEM,"Variantes","Variantes",'V',100104091,4, true, FVariantes.class);
				      addOpcao(100104090,TP_OPCAO_ITEM,"Modelo","Modelo de Grade",'M',100104092,4, true, FModGrade.class);
			          addOpcao(100104090,TP_OPCAO_ITEM,"Grade","Grade",'r',100104093,4, true, FGrade.class);
			    addSeparador(100100000);
		        addOpcao(100100000,TP_OPCAO_MENU,"Preço","",'ç',100105000,2, false, null);
		           addOpcao(100105000,TP_OPCAO_ITEM,"Manutenção de Preços","Manutenção de Preços",'M',100105010,3, true, FManutPreco.class);
		           addOpcao(100105000,TP_OPCAO_ITEM,"Copia preço","Copia Precos",'i',100105020,3, true, FCpProd.class);
		           addOpcao(100105000,TP_OPCAO_ITEM,"Tabela de preço","Tabelas de Preços",'a',100105030,3, true, FTabPreco.class);
		           addOpcao(100105000,TP_OPCAO_ITEM,"Lista de preço","Lista de Preços",'l',100105040,3, true, FRListaPreco.class);
	    
            addOpcao(100000000, TP_OPCAO_MENU, "Preferências", "", 'P', 100200000,1, false, null);
			   addOpcao(100200000, TP_OPCAO_ITEM, "Preferências gerais","Prefere Geral", 'g', 100201000, 2, true, FPrefere.class);

		addOpcao(-1, TP_OPCAO_MENU, "PDV", "", 'P', 200000000, 1, false, null);
		addOpcao(200000000, TP_OPCAO_ITEM, "Venda", "Venda", 'V', 200100000, 2,	true, FVenda.class);		
		addOpcao(200000000, TP_OPCAO_ITEM, "Cancela venda", "Cancela Venda",'C', 200200000, 2, true, DLCancCupom.class);	
		addSeparador(200000000);
		addOpcao(200000000, TP_OPCAO_ITEM, "Suprimento", "Suprimento de caixa",'S', 200300000, 2, true, FSuprimento.class);	
		addOpcao(200000000, TP_OPCAO_ITEM, "Sangria", "Sangria", 'G',200400000, 2, true, FSangria.class);
		addSeparador(200000000);
		addOpcao(200000000, TP_OPCAO_ITEM, "Aliquota", "Inserir Aliquota", 'Q',200500000, 2, true, FAliquota.class);		
		addOpcao(200000000, TP_OPCAO_ITEM, "Ajusta moeda", "Grava Moeda", 'J',200600000, 2, true, FGravaMoeda.class);		
		addSeparador(200000000);
		addOpcao(200000000, TP_OPCAO_ITEM, "Ler memória fiscal", "Le Fiscal",'L', 200700000, 2, true, FLeFiscal.class);
		addOpcao(200000000, TP_OPCAO_ITEM, "Leitura X","Confirma impressão de leitura X ?", 'i', 110800000, 2, true, JBemaFI32.class);

		
		JButtonPad btVenda = addBotao("barraVenda.gif", "Venda", "", 200100000,	null);
		addBotao("btExcluir.gif", "Cancela venda", "Cancela Venda", 200200000,DLCancCupom.class);
		addBotao("barraFornecedor.gif", "Suprimento", "Suprimento de caixa",200300000, FSuprimento.class);
		addBotao("btPdvSangria.gif", "Sangria", "Sangria", 200400000,FSangria.class);
		addBotao("btPdvAliquota.gif", "Aliquota", "Inserir Aliquota",200500000, FAliquota.class);		
		addBotao("btPdvGravaMoeda.gif", "Ajusta moeda", "Grava Moeda",200600000, FGravaMoeda.class);		
		addBotao("btPdvLeituraXPq.gif", "Ler memória fiscal", "Le Fiscal",200700000, FLeFiscal.class);
		
		
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
				if (rs.getString("ECFCaixa") != null && rs.getString("ECFCaixa").equals("S"))
					bECFTerm = true;
				else
					bECFTerm = false;
				if (rs.getString("TEFCaixa") != null && rs.getString("TEFCaixa").equals("S"))
					bTEFTerm = true;
				else
					bTEFTerm = false;
				if (rs.getString(3) != null && rs.getString(3).equals("S"))
					bModoDemo = true;
				else
					bModoDemo = false;
			}
			rs.close();
			ps.close();
		} catch (Exception err) {
		    err.printStackTrace();
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