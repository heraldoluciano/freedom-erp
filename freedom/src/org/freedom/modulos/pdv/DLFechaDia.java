package org.freedom.modulos.pdv;

/**
 * @version 30/06/2004 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.pdv <BR>
 * Classe: @(#)FFechaVenda.java <BR>
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
 * Comentários sobre a classe...
 * 
 */

import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import org.freedom.componentes.JCheckBoxPad;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.drivers.JBemaFI32;
import org.freedom.funcoes.Funcoes;
import org.freedom.funcoes.Logger;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FDialogo;

public class DLFechaDia extends FDialogo {
	private JTextFieldFK txtDataHora = new JTextFieldFK(JTextFieldPad.TP_STRING,16,0);
	private JTextFieldFK txtVlrCaixa = new JTextFieldFK(JTextFieldPad.TP_DECIMAL,12,2);
	private JCheckBoxPad cbReducaoZ = new JCheckBoxPad("Deseja executar a redução Z?","S","N");
	private JBemaFI32 bf = (FreedomPDV.bECFTerm ? new JBemaFI32() : null);
	private Connection con = null;
	public DLFechaDia() {
		setTitulo("Fechamento de caixa");
		setAtribos(310,150);
		
		adic(new JLabel("Data e Hora: "),7,10,110,20);
		adic(txtDataHora,7,30,110,20);
		adic(new JLabel("Saldo do caixa: "),120,10,120,20);
		adic(txtVlrCaixa,120,30,120,20);
		adic(cbReducaoZ,7,60,280,20);

		txtDataHora.setVlrString((new SimpleDateFormat("dd/MM/yyyy HH:mm")).format(new Date()));
		
	}
	private void buscaSaldoDia() {
		String sSQL = "SELECT FIRST 1 VLRSLDMOV FROM PVMOVCAIXA WHERE CODEMP=? AND CODFILIAL=?" +
					  " AND CODCAIXA=? AND DTAMOV=? ORDER BY NROMOV DESC";
		try {
			PreparedStatement ps = con.prepareStatement(sSQL);
			ps.setInt(1,Aplicativo.iCodEmp);
			ps.setInt(2,Aplicativo.iCodFilial);
			ps.setInt(3,Aplicativo.iNumEst);
			ps.setDate(4,Funcoes.dateToSQLDate(new Date()));
			ResultSet rs = ps.executeQuery();
			if (rs.next())
				txtVlrCaixa.setVlrBigDecimal(rs.getBigDecimal("VLRSLDMOV"));
			rs.close();
			ps.close();
			if (!con.getAutoCommit())
				con.commit();
		}
		catch (SQLException err) {
			Funcoes.mensagemErro(null,"Não foi possível buscar o saldo atual.\n"+err.getMessage());
			err.printStackTrace();
		}
	}
	
	private boolean execFechamento(boolean bReduz) {
		boolean bRet = false;
		try {
			
//			Fecha o caixa:			

			String sSQL = "EXECUTE PROCEDURE PVFECHACAIXASP(?,?,?,?,?,?,?)";
			PreparedStatement ps = con.prepareStatement(sSQL);
			ps.setInt(1,Aplicativo.iCodEmp);
			ps.setInt(2,ListaCampos.getMasterFilial("PVMOVCAIXA"));
			ps.setInt(3,Aplicativo.iNumEst);
			ps.setDate(4,Funcoes.dateToSQLDate(new Date()));
			ps.setString(5,bReduz ? "S" : "N");
			ps.setInt(6,Aplicativo.iCodFilial);
			ps.setString(7,Aplicativo.strUsuario);
			ps.execute();
			ps.close();
	      	if (!con.getAutoCommit())
	      		con.commit();
			bRet = true;
		}
		catch (SQLException err) {
			Funcoes.mensagemErro(null,"Erro ao executar fechamento do caixa!\n"+err.getMessage());
			Logger.gravaLogTxt("",Aplicativo.strUsuario,Logger.LGEB_BD,"Erro ao executar fechamento do caixa.");
		}
		return bRet;
	}
	private boolean execSangria() {
		boolean bRet = false;
		
// Sangria para o troco:		
		
		try {
			String sSQL = "EXECUTE PROCEDURE PVSANGRIASP(?,?,?,?,?,?)";
			PreparedStatement ps = con.prepareStatement(sSQL);
			ps.setInt(1,Aplicativo.iCodEmp);
			ps.setInt(2,ListaCampos.getMasterFilial("PVMOVCAIXA"));
			ps.setBigDecimal(3,txtVlrCaixa.getVlrBigDecimal());
			ps.setInt(4,Aplicativo.iNumEst);
			ps.setDate(5,Funcoes.dateToSQLDate(new Date()));
			ps.setString(6,Aplicativo.strUsuario);
			ps.execute();
			ps.close();
			if (!con.getAutoCommit())
				con.commit();
			bRet = true;
		}
		catch (SQLException err) {
			Funcoes.mensagemErro(null,"Erro ao executar o troco!\n"+err.getMessage());
			Logger.gravaLogTxt("",Aplicativo.strUsuario,Logger.LGEB_BD,"Erro ao executar o troco.");
		}
		return bRet;
	}	
	private void fechaCaixa(boolean bReduz) {
		if (txtVlrCaixa.getVlrDouble().doubleValue() > 0)
			if (execSangria() && FreedomPDV.bECFTerm) {
				if (!bf.sangria(Aplicativo.strUsuario,txtVlrCaixa.getVlrBigDecimal(),FreedomPDV.bModoDemo)) {
					Funcoes.mensagemErro(null,"Erro ao executar a sangria!");
					return;
				}
			}
		if (execFechamento(bReduz)) {
			if (FreedomPDV.bECFTerm && bReduz)
				if (!bf.reducaoZ(Aplicativo.strUsuario,FreedomPDV.bModoDemo)) {
					Funcoes.mensagemErro(null,"Erro ao executar a redução Z!");
					return;
				}
		}
	}
	public void actionPerformed(ActionEvent evt) {
		if (evt.getSource() == btOK) {
			if (Funcoes.mensagemConfirma(null,"Confirma fechamento?") == JOptionPane.YES_OPTION) {
				if (cbReducaoZ.getVlrString().equals("S")) {
					if (Funcoes.mensagemConfirma(null,"Atenção!\n"+
                            					  "Se for executada a 'Redução Z''\n"+
												  "o caixa será fechado em definitivo!\n"+
                            					  "Deseja executar assim mesmo?") == JOptionPane.YES_OPTION) {
						fechaCaixa(true);
					}
					else
						return;
				}
				else {
					fechaCaixa(false);
				}
			}
			else 
				return;
		}
		super.actionPerformed(evt);
	}
	public void setConexao(Connection cn) {
		con = cn;
		buscaSaldoDia();
	}
}
