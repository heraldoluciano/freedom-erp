/**
 * @version 15/07/2003 <BR>
 * @author Setpoint Informática Ltda./Anderson Sanchez <BR>
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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.drivers.JBemaFI32;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FDialogo;


public class FSuprimento extends FDialogo {
	private JTextFieldFK txtData = new JTextFieldFK();
	private JTextFieldPad txtValor = new JTextFieldPad();
	private JTextFieldFK txtDataAnt = new JTextFieldFK();
	private JTextFieldFK txtSldAnt = new JTextFieldFK();
	private JTextFieldFK txtStatusAnt = new JTextFieldFK();
	private JTextFieldFK txtUsuarioAnt = new JTextFieldFK();
	private JTextFieldFK txtUsuarioAtual = new JTextFieldFK();
	private JBemaFI32 bf = (FreedomPDV.bECFTerm ? new JBemaFI32() : null);
	private Connection con = null;
	public FSuprimento() {
		setTitulo("Suprimento de caixa");
		setAtribos(345,255);
		
		txtData.setTipo(JTextFieldPad.TP_DATE,10,0);		
		txtValor.setTipo(JTextFieldPad.TP_DECIMAL,10,2);
		txtDataAnt.setTipo(JTextFieldPad.TP_DATE,10,0);
		txtSldAnt.setTipo(JTextFieldPad.TP_STRING,10,2);
		txtStatusAnt.setTipo(JTextFieldPad.TP_STRING,10,0);
		txtUsuarioAnt.setTipo(JTextFieldPad.TP_STRING,40,0);
		txtUsuarioAtual.setTipo(JTextFieldPad.TP_STRING,40,0);
		
		txtData.setVlrDate(Calendar.getInstance().getTime());
		txtValor.setVlrBigDecimal(new BigDecimal(0));
		txtUsuarioAtual.setVlrString(Aplicativo.strUsuario);

		txtValor.setHorizontalAlignment(SwingConstants.RIGHT);
		txtSldAnt.setHorizontalAlignment(SwingConstants.RIGHT);
					
		adic(new JLabel("Última operação"),7,5,95,20);
		adic(txtDataAnt,7,25,90,20);
		adic(new JLabel("Saldo final"),104,5,80,20);
		adic(txtSldAnt,104,25,80,20);
		adic(new JLabel("Status do caixa"),191,5,130,20);
		adic(txtStatusAnt,191,25,130,20);
		
		adic(new JLabel("Último operador"),7,45,150,20);
		adic(txtUsuarioAnt,7,65,150,20);
		
		adic(new JLabel("Operador atual"),7,100,150,20);
		adic(txtUsuarioAtual,7,120,150,20);		
		adic(new JLabel("Data"),164,100,90,20);
		adic(txtData,164,120,90,20);
		
		adic(new JLabel("Valor do suprimento"),7,140,120,20);
		adic(txtValor,7,160,120,20);
		
	}	
	private void executaQuery() {
		try	 {
		  
		  PreparedStatement ps = con.prepareStatement("SELECT * FROM PVRETMOVCAIXASP(?,?,?,?)");
		  ps.setInt(1,Aplicativo.iNumEst);
		  System.out.println("caixa: "+Aplicativo.iNumEst);
		  
		  ps.setInt(3,ListaCampos.getMasterFilial("PVMOVCAIXA"));
		  ps.setInt(2,Aplicativo.iCodEmp);
		  ps.setDate(4,Funcoes.dateToSQLDate(new Date()));		  		  
		  ResultSet rs = ps.executeQuery();
		  if (rs.next()) {
		    if (rs.getDate(1)==null) {  
		      Funcoes.mensagemErro(this,"Caixa não está aberto!");
		    }
		    else {
		     txtDataAnt.setVlrDate(rs.getDate(1));
		  
		  
			 txtSldAnt.setVlrString(Funcoes.strDecimalToStrCurrency(10,2,rs.getString(3)));
		     txtUsuarioAnt.setVlrString(rs.getString(4));
		     
		     txtStatusAnt.setVlrString(JBemaFI32.transStatus(rs.getString(2).toCharArray()[0]));	  
		    
		    } 
		  }	
		  if (!con.getAutoCommit())
		  	con.commit();
		  rs.close();
		}		
		  catch(SQLException err){
			Funcoes.mensagemErro(this, "Ocorreu erro na consulta de movimento de caixa!\n"+err.getMessage());	
		  }		
			 			
	}
		
	private void executaSuprimento() {
		 if (!FreedomPDV.bECFTerm || bf.suprimento(Aplicativo.strUsuario,txtValor.getVlrBigDecimal(),"Dinheiro",FreedomPDV.bModoDemo)) {
		  try {
			PreparedStatement ps = con.prepareStatement("EXECUTE PROCEDURE PVSUPRIMENTOSP(?,?,?,?,?,?)");
					
			ps.setInt(1,Aplicativo.iCodEmp);
			ps.setInt(2,ListaCampos.getMasterFilial("PVMOVCAIXA"));
			ps.setBigDecimal(3,txtValor.getVlrBigDecimal());
			ps.setInt(4,Aplicativo.iNumEst);
			ps.setDate(5,Funcoes.dateToSQLDate(new Date()));
            ps.setString(6,Aplicativo.strUsuario);
      	
			
			//ps.setDate(5,Funcoes.dateToSQLDate(new Date()));
			ps.execute();
			ps.close();
			if (!con.getAutoCommit())
				con.commit();
		  }
		  catch (SQLException err) {
			Funcoes.mensagemErro(this,"Erro ao executar suprimento!\n"+err.getMessage());
		  }
		  if (FreedomPDV.bECFTerm)
		    bf.abreGaveta(Aplicativo.strUsuario,FreedomPDV.bModoDemo);
		}		  
	}

	public void actionPerformed(ActionEvent evt) {
		if (evt.getSource() == btOK) {
			executaSuprimento();
		}
		super.actionPerformed(evt);
	}
	public void setConexao(Connection cn) {
		con = cn;
		executaQuery();
	}


}
