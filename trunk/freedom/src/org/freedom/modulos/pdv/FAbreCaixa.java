/**
 * @version 10/06/2003 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.pdv <BR>
 * Classe: @(#)FAbreCaixa.java <BR>
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

package org.freedom.modulos.pdv;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JLabel;

import org.freedom.componentes.JTextFieldPad;
import org.freedom.drivers.JBemaFI32;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FDialogo;

public class FAbreCaixa extends FDialogo {
	private JTextFieldPad txtData = new JTextFieldPad();
	private JTextFieldPad txtValor = new JTextFieldPad();
	private JBemaFI32 bf = (FreedomPDV.bECFTerm ? new JBemaFI32() : null);
	private Connection con = null;
	public FAbreCaixa() {
		setTitulo("Abrir Caixa");
		setAtribos(250,110);
		
		txtData.setTipo(JTextFieldPad.TP_DATE,10,0);
		txtValor.setTipo(JTextFieldPad.TP_DECIMAL,10,2);
		
		txtData.setVlrDate(Calendar.getInstance().getTime());
		txtData.setAtivo(false);
		txtValor.setVlrBigDecimal(new BigDecimal(0));
		
		adic(new JLabel("Data"),7,5,80,15);
		adic(txtData,7,20,80,20);
		adic(new JLabel("Valor"),90,5,100,15);
		adic(txtValor,90,20,100,20);
		
		
	}
	private void dbAbrirCaixa() {
	  System.out.println("Modo demo PDV: "+FreedomPDV.bModoDemo);
	  if (!FreedomPDV.bECFTerm || bf.leituraX(Aplicativo.strUsuario,FreedomPDV.bModoDemo)) {
       if (!FreedomPDV.bECFTerm || bf.suprimento(Aplicativo.strUsuario,txtValor.getVlrBigDecimal(),"Dinheiro",FreedomPDV.bModoDemo)) {
	      try {
			PreparedStatement ps = con.prepareStatement("EXECUTE PROCEDURE PVABRECAIXASP(?,?,?,?,?,?,?)");
			ps.setInt(1,Aplicativo.iNumTerm);
			ps.setInt(2,Aplicativo.iCodFilial);
			ps.setInt(3,Aplicativo.iCodEmp);
			ps.setBigDecimal(4,txtValor.getVlrBigDecimal());
			ps.setDate(5,Funcoes.dateToSQLDate(new Date()));
			ps.setInt(6,Aplicativo.iCodFilialPad);
			ps.setString(7,Aplicativo.strUsuario);
			ps.execute();
			ps.close();
			if (!con.getAutoCommit())
				con.commit();
	      }
	      catch (SQLException err) {
			Funcoes.mensagemErro(this,"Erro ao abrir o caixa!\n"+err.getMessage());
	      }
	      if (FreedomPDV.bECFTerm)
	      	 bf.abreGaveta(Aplicativo.strUsuario,FreedomPDV.bModoDemo);
	    }
	  }
	}
	public void actionPerformed(ActionEvent evt) {
		if (evt.getSource() == btOK) {
			dbAbrirCaixa();
		}
		super.actionPerformed(evt);
	}
	public void setConexao(Connection cn) {
		con = cn;
	}
}
