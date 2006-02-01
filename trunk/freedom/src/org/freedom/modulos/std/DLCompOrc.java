/**
 * @version 02/11/2003 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)DLCompOrc.java <BR>
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

package org.freedom.modulos.std;
import java.awt.Component;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JCheckBoxPad;
import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FFDialogo;

public class DLCompOrc extends FFDialogo implements FocusListener {
	
	private static final long serialVersionUID = 1L;
	
	private JTextFieldPad txtPercDescOrc = new JTextFieldPad(JTextFieldPad.TP_DECIMAL,6,2);
	private JTextFieldPad txtVlrDescOrc = new JTextFieldPad(JTextFieldPad.TP_DECIMAL,15,2);
	private JTextFieldPad txtPercAdicOrc = new JTextFieldPad(JTextFieldPad.TP_DECIMAL,6,2);
	private JTextFieldPad txtVlrAdicOrc = new JTextFieldPad(JTextFieldPad.TP_DECIMAL,15,2);
	private JTextFieldPad txtCodPlanoPag = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);
	private JTextFieldFK txtDescPlanoPag = new JTextFieldFK(JTextFieldPad.TP_STRING, 40, 0);
	private JTextFieldPad txtAtend = null;
	private JLabelPad lbPercDescOrc = new JLabelPad("% Desc.");
	private JLabelPad lbVlrDescOrc = new JLabelPad("V Desc.");
	private JLabelPad lbPercAdicOrc = new JLabelPad("% Adic.");
	private JLabelPad lbVlrAdicOrc = new JLabelPad("V Adic.");
	private JLabelPad lbCodPlanoPag = new JLabelPad("Código e Desc. do plano de pagto.");
	private JCheckBoxPad cbImpOrc = new JCheckBoxPad("Imprime Orçamento?","S","N");
	private JCheckBoxPad cbAprovOrc = new JCheckBoxPad("Aprova Orçamento?","S","N");
	private ListaCampos lcPlanoPag = new ListaCampos(this, "PG");
	private BigDecimal bValProd;
	private boolean bTestaAtend = false;
	
	public DLCompOrc(Component cOrig,boolean bDIt,BigDecimal bVP, BigDecimal bVD, BigDecimal bVA, Integer iCodPlanoPag) {
		super(cOrig);    
		bValProd = bVP;
		setTitulo("Completar Orçamento");
		setAtribos(380,240);
		
		txtCodPlanoPag.setVlrInteger(iCodPlanoPag);
		txtVlrDescOrc.setVlrBigDecimal(bVD);
		txtVlrAdicOrc.setVlrBigDecimal(bVA);
		
		if (bDIt) {
			txtPercDescOrc.setAtivo(false);
			txtVlrDescOrc.setAtivo(false);
		}
	}
	
	public void montaTela(){
	  	   
		lcPlanoPag.add(new GuardaCampo(txtCodPlanoPag, "CodPlanoPag","Cód.p.pag.", ListaCampos.DB_PK,txtDescPlanoPag,true));
		lcPlanoPag.add(new GuardaCampo(txtDescPlanoPag, "DescPlanoPag","Descrição do plano de pagamento", ListaCampos.DB_SI, false));
		lcPlanoPag.montaSql(false, "PLANOPAG", "FN");
		lcPlanoPag.setReadOnly(true);
		txtCodPlanoPag.setTabelaExterna(lcPlanoPag);
		txtCodPlanoPag.setFK(true);
		txtCodPlanoPag.setNomeCampo("CodPlanoPag");
		
		adic(lbCodPlanoPag,7,0,270,20);
		adic(txtCodPlanoPag,7,20,80,20);
		adic(txtDescPlanoPag,90,20,260,20);
		adic(lbPercDescOrc,7,40,80,20);
		adic(txtPercDescOrc,7,60,80,20);
		adic(lbVlrDescOrc,90,40,87,20);
		adic(txtVlrDescOrc,90,60,87,20);
		adic(lbPercAdicOrc,180,40,77,20);
		adic(txtPercAdicOrc,180,60,77,20);
		adic(lbVlrAdicOrc,260,40,90,20);
		adic(txtVlrAdicOrc,260,60,90,20);
		adic(cbImpOrc,7,100,150,20);
		
		if(podeAprova()) {
			adic(cbAprovOrc,7,120,150,20);
		}
		
		txtPercDescOrc.addFocusListener(this);
		txtVlrDescOrc.addFocusListener(this);
		txtPercAdicOrc.addFocusListener(this);
		txtVlrAdicOrc.addFocusListener(this);
		
		cbImpOrc.setVlrString("N");
		
	}
	
	public void setFKAtend(JTextFieldPad txtCodAtend,JTextFieldFK txtDescAtend) {
		txtCodAtend.setRequerido(true);
		  	
		adic(new JLabelPad("Código e descrição do atendente."),7,40,270,20);
		adic(txtCodAtend,7,60,80,20);
		adic(txtDescAtend,90,60,260,20);
		
		//Aumenta mais 40 pxs a janela.
		
		Rectangle tamAnt = getBounds();
		tamAnt.height += 40; 
		setBounds(tamAnt);	
		
		//Move todos os demais componentes 40pxs abaixo.
		
		lbPercDescOrc.setBounds(7,80,80,20);
		txtPercDescOrc.setBounds(7,100,80,20);
		lbVlrDescOrc.setBounds(90,80,87,20);
		txtVlrDescOrc.setBounds(90,100,87,20);
		lbPercAdicOrc.setBounds(180,80,77,20);
		txtPercAdicOrc.setBounds(180,100,77,20);
		lbVlrAdicOrc.setBounds(260,80,90,20);
		txtVlrAdicOrc.setBounds(260,100,90,20);
		cbImpOrc.setBounds(7,140,150,20);
		
		txtAtend = txtCodAtend;
		bTestaAtend = true;
	}
	
	public void actionPerformed(ActionEvent evt) {
		if (evt.getSource() == btOK) {
			if (txtCodPlanoPag.getVlrInteger().intValue() == 0) {
				Funcoes.mensagemInforma(this,"O campo 'Código do plano de pagamento' é requerido!");
				txtCodPlanoPag.requestFocus();
				return;
			}
			else if (bTestaAtend) {
				if (txtAtend.getVlrInteger().intValue() == 0) {
					Funcoes.mensagemInforma(this,"O campo 'Código do atendente' é requerido!");
					txtAtend.requestFocus();
					return;
				}
			}
		}
		super.actionPerformed(evt);
	}
	
	public Object[] getValores() {
		Object[] bRetorno = new Object[5];
		bRetorno[0] = txtVlrDescOrc.getVlrBigDecimal();
		bRetorno[1] = txtVlrAdicOrc.getVlrBigDecimal();
		bRetorno[2] = cbImpOrc.getVlrString();
		bRetorno[3] = txtCodPlanoPag.getVlrInteger();
		bRetorno[4] = cbAprovOrc.getVlrString();
		return bRetorno;
	}
	
	public void focusLost(FocusEvent fevt) {
		if (fevt.getSource() == txtPercDescOrc) {
			if (txtPercDescOrc.getText().trim().length() < 1) {
				txtVlrDescOrc.setAtivo(true);
			}
			else {
				txtVlrDescOrc.setVlrBigDecimal(
					bValProd.multiply(
							txtPercDescOrc.getVlrBigDecimal()).divide(
									new BigDecimal("100"),3,BigDecimal.ROUND_HALF_UP));
				txtVlrDescOrc.setAtivo(false);
			}
		}
		if (fevt.getSource() == txtPercAdicOrc) {
			if (txtPercAdicOrc.getText().trim().length() < 1) {
				txtVlrAdicOrc.setAtivo(true);
			}
			else {
				txtVlrAdicOrc.setVlrBigDecimal(
					bValProd.multiply(
							txtPercAdicOrc.getVlrBigDecimal()).divide(
									new BigDecimal("100"),3,BigDecimal.ROUND_HALF_UP));
				txtVlrAdicOrc.setAtivo(false);
			}
		}
	}
	
	public void setConexao(Connection cn) {    
		super.setConexao(cn);
		lcPlanoPag.setConexao(cn);
		montaTela();
		lcPlanoPag.carregaDados();	
	}
	
	public void focusGained(FocusEvent fevt) { }
	
	private boolean podeAprova() {
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sSQL = null;
		String sAprovOrc = null;
		boolean bRet = false;
		try {
			sSQL = "SELECT APROVORC FROM SGPREFERE4 WHERE "
				 + "CODEMP=? AND CODFILIAL=?";
			ps = con.prepareStatement(sSQL);
			ps.setInt(1, Aplicativo.iCodEmp);
			ps.setInt(2, Aplicativo.iCodFilial);
			rs = ps.executeQuery();
			
			if (rs.next()) {
				sAprovOrc = rs.getString("APROVORC");
				if(sAprovOrc.equals("S"))
					bRet = true;
			}
			
			rs.close();
			ps.close();
		} catch (SQLException err) {
			err.printStackTrace();
		} finally {
			ps = null;
			rs = null;
			sSQL = null;
			sAprovOrc = null;
		}
		return bRet;
	}
}
