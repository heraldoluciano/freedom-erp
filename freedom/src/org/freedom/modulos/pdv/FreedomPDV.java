/**
 * @version 10/06/2003 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.pdv <BR>
 * Classe: @(#)FreedomPDV.java <BR>
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
 * Tela principal do múdulo ponto de venda.
 * 
 */

package org.freedom.modulos.pdv;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;

import javax.swing.JOptionPane;

import org.freedom.drivers.JBemaFI32;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;

public class FreedomPDV extends Aplicativo implements ActionListener{
	public static boolean bECFTerm = false;
	public static boolean bModoDemo = true;
	public FreedomPDV() {
		super("iconConfiguracao32.gif","splashPDV.jpg","FreedomPDV - Ponto de Venda",1,3);
		addOpcao(-1,TP_OPCAO_MENU,"Arquivo",'A',100000000,0,false);
		  addOpcao(100000000,TP_OPCAO_MENU,"Tabelas",'T',100100000,1,false);
		  addOpcao(100000000,TP_OPCAO_MENU,"Preferências",'P',100200000,1,false);
		    addOpcao(100200000,TP_OPCAO_ITEM,"Preferências gerais",'g',100210000,2,true);
		
		addOpcao(-1,TP_OPCAO_MENU,"PDV",'P',110000000,1,false);
		addOpcao(110000000,TP_OPCAO_ITEM,"Venda",'V',100101000,2,true);
		addBotao("barraVenda.gif","Venda",100101000);
		addOpcao(110000000,TP_OPCAO_ITEM,"Cancela venda",'C',110200000,2,true);
		addBotao("btExcluir.gif","Cancela venda",110200000);
	
		addSeparador(110000000);
			
		addOpcao(110000000,TP_OPCAO_ITEM,"Suprimento",'S',110300000,2,true);
		addBotao("barraFornecedor.gif","Suprimento",110300000);
		addOpcao(110000000,TP_OPCAO_ITEM,"Sangria",'G',110400000,2,true);
		addBotao("btPdvSangria.gif","Sangria",110400000);
		
		addSeparador(110000000);
		
		addOpcao(110000000,TP_OPCAO_ITEM,"Aliquota",'Q',110500000,2,true);
		addBotao("btPdvAliquota.gif","Aliquota",110500000);
		addOpcao(110000000,TP_OPCAO_ITEM,"Ajusta moeda",'J',110600000,2,true);
		addBotao("btPdvGravaMoeda.gif","Ajusta moeda",110600000);
		
		addSeparador(110000000);
		
		addOpcao(110000000,TP_OPCAO_ITEM,"Ler memória fiscal",'L',110700000,2,true);
		addOpcao(110000000,TP_OPCAO_ITEM,"Leitura X",'i',110800000,2,true);
		
		addBotao("btPdvLeituraXPq.gif","Ler memória fiscal",110700000);		   
	    if (abrecaixa()) {
		  execOpcao(100101000); // Opção de venda
		}
		else {
			killProg(5,"Caixa não foi aberto. A aplicação será fechada!");			
		}
    }
	public void execOpcao(int iOpcao) {
      if (iOpcao==100101000){
	    if (telaPrincipal.temTela("Venda")==false) {
		   FVenda tela = new FVenda();
		   //tela.setConexao(con);
		   tela.iniciaTela(con);
		   tela.setVisible(true);
		   tela.dispose();
	    } 
	  }
      else if (iOpcao==100210000){
        if (telaPrincipal.temTela("Prefere Geral")==false) {
		   FPrefere tela = new FPrefere();
		   telaPrincipal.criatela("Prefere Geral",tela,con);			  
        } 
	  }
	  else if (iOpcao==110200000){
		if (telaPrincipal.temTela("Cancela Venda")==false) {
		  DLCancCupom tela = new DLCancCupom(con);			  
		  tela.setVisible(true);
		  tela.dispose();
		} 
	  }
	  else if (iOpcao==110300000){
		if (telaPrincipal.temTela("Suprimento de caixa")==false){
		  FSuprimento tela = new FSuprimento();
		  tela.setConexao(con);
		  tela.setVisible(true);
		  tela.dispose();
		}
	  }
	  else if (iOpcao==110400000){
		if (telaPrincipal.temTela("Sangria")==false){
		  FSangria tela = new FSangria();
		  tela.setConexao(con);
		  tela.setVisible(true);
		  tela.dispose();
		}
	  }
	  else if (iOpcao==110500000){
		  if (telaPrincipal.temTela("Inserir Aliquota")==false) {
			 FAliquota tela = new FAliquota();			  
			 tela.setVisible(true);
			 tela.dispose();
		  } 
	  }
	  else if (iOpcao==110600000){
		  if (telaPrincipal.temTela("Grava Moeda")==false) {
			FGravaMoeda tela = new FGravaMoeda();
			tela.setConexao(con);
			tela.setVisible(true);
			tela.dispose();
		  } 
	  }
	  else if (iOpcao==110700000){
		  if (telaPrincipal.temTela("Le Fiscal")==false) {
			 FLeFiscal tela = new FLeFiscal();
			 telaPrincipal.criatela("Le Fiscal",tela,con);			  
		  } 
	  }
	  else if (iOpcao==110800000){
          if(Funcoes.mensagemConfirma(null, "Confirma impressão de leitura X ?") == JOptionPane.YES_OPTION) {
        	JBemaFI32 bf = (bECFTerm ? new JBemaFI32() : null);
          	bf.leituraX(Aplicativo.strUsuario,bModoDemo);
          }
	  }

	}
    private boolean abrecaixa() {
  	boolean bRetorno = false;
    int iRet = 0;
  	 try {
       PreparedStatement ps = con.prepareStatement("SELECT IRETORNO FROM PVVERIFCAIXASP(?,?,?,?,?,?)"); // caixa, emp, filial
       ps.setInt(1,iNumTerm);
       ps.setInt(2,iCodEmp);
       ps.setInt(3,iCodFilial);
       ps.setDate(4,Funcoes.dateToSQLDate(new Date()));
       ps.setInt(5,iCodFilialPad);
       ps.setString(6,strUsuario);
       ResultSet rs = ps.executeQuery();
       if (rs.next()) {
       	 iRet = rs.getInt(1);
       	 switch(iRet) {
       	 	case 0: { 
       	 	   bRetorno = pegaValorINI();
       	 	   break;
       	 	}
       	 	//case 1: { Reservado. 
       	 	case 2: { 
       	 		Funcoes.mensagemInforma(null,"Caixa já está aberto!"); 
       	 		bRetorno = true;
				break;
       	 	}
       	 	case 3: { 
       	 		killProg(3,"Já foi realizada leitura \"Z\" neste terminal hoje!");
				break;
       	 	}
       	 	case 4: {
       	 		killProg(4,"Caixa foi aberto com outro usuário!");
       	 		break;
       	 	}
       	 	default : {
       	 	    killProg(5,"Erro na ultima transacão de caixa.");
				break;
       	 	}
       	 }
       } 
       else
       {
          killProg(5,"Não foi possível abrir o caixa!");
       }
  	 }
  	 catch (Exception err) {
        killProg(6,"Erro abrir o caixa!\n"+err.getMessage());
  	 }
  	 String sSQL = "SELECT ECFCAIXA,MODODEMO FROM PVCAIXA WHERE CODCAIXA=?" +
  	 		               " AND CODFILIAL=? AND CODEMP=?";
	 try {
	 	PreparedStatement ps = con.prepareStatement(sSQL);
	 	ps.setInt(1,Aplicativo.iNumTerm);
	 	ps.setInt(2,Aplicativo.iCodFilial);
	 	ps.setInt(3,Aplicativo.iCodEmp);
	 	ResultSet rs = ps.executeQuery();
	 	if (rs.next()) {
	 		if (rs.getString("ECFCaixa").equals("S"))
	 			bECFTerm = true;
	 		else
	 			bECFTerm = false;
	 		if (rs.getString("ModoDemo").equals("S"))
	 			bModoDemo = true;
	 		else
	 			bModoDemo = false;
	 	}
	 	rs.close();
	 	ps.close();
	 }
  	 catch(Exception err) {
  	 	killProg(6,"Erro ao verificar o caixa!\n"+err.getMessage());
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
		FreedomPDV freedom = new FreedomPDV();
		freedom.show();
  }
}