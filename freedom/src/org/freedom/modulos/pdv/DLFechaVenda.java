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
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Properties;

import javax.swing.BorderFactory;

import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.comutacao.Tef;
import org.freedom.drivers.JBemaFI32;
import org.freedom.funcoes.Funcoes;
import org.freedom.funcoes.Logger;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FDialogo;

public class DLFechaVenda extends FDialogo implements FocusListener {
	private JTextFieldPad txtCodPlanoPag = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
	private JTextFieldFK txtDescPlanoPag = new JTextFieldFK(JTextFieldPad.TP_STRING,50,0);
	private JTextFieldFK txtVlrCupom = new JTextFieldFK(JTextFieldPad.TP_DECIMAL,12,2);
	private JTextFieldPad txtVlrDinheiro = new JTextFieldPad(JTextFieldPad.TP_DECIMAL,12,2);
	private JTextFieldPad txtVlrCheque = new JTextFieldPad(JTextFieldPad.TP_DECIMAL,12,2);
	private JTextFieldPad txtVlrChequeElet = new JTextFieldPad(JTextFieldPad.TP_DECIMAL,12,2);
	private JTextFieldFK txtVlrPago = new JTextFieldFK(JTextFieldPad.TP_DECIMAL,12,2);
	private JTextFieldFK txtVlrTroco = new JTextFieldFK(JTextFieldPad.TP_DECIMAL,12,2);
	private ListaCampos lcPlanoPag = new ListaCampos(this,"PG");
	private JBemaFI32 bf = (FreedomPDV.bECFTerm ? new JBemaFI32() : null);
	private Tef tef = null;
	private int iCodVenda = 0;
	private int iNumCupom = 0;
	private Properties ppCompTef = null;
	Connection con = null;
	public DLFechaVenda(BigDecimal valCupom, int iCodVenda, int iNumCupom) {
		//super(Aplicativo.telaPrincipal);
		setTitulo("Fechamento de venda");
		setAtribos(330,275);
		
		this.iCodVenda = iCodVenda;
		this.iNumCupom = iNumCupom;
		
		txtVlrCupom.setVlrBigDecimal(valCupom);
		txtVlrChequeElet.setAtivo(false);
		
		
		lcPlanoPag.add(new GuardaCampo( txtCodPlanoPag, "CodPlanoPag", "Cód.p.pag.", ListaCampos.DB_PK, true));
		lcPlanoPag.add(new GuardaCampo( txtDescPlanoPag, "DescPlanoPag", "Descrição do plano de pagamento", ListaCampos.DB_SI,false));
		lcPlanoPag.montaSql(false, "PLANOPAG", "FN");
		lcPlanoPag.setReadOnly(true);
		txtCodPlanoPag.setTabelaExterna(lcPlanoPag);
		txtCodPlanoPag.setFK(true);
		
		adic(new JLabelPad("Cód.p.pag."),7,5,250,15);
		adic(txtCodPlanoPag,7,20,80,20);
		adic(new JLabelPad("Descrição da forma de pagamento"),90,5,250,15);
		adic(txtDescPlanoPag,90,20,200,20);
		
		JLabelPad lbLinha = new JLabelPad();
		lbLinha.setBorder(BorderFactory.createEtchedBorder());
		
		adic(lbLinha,7,50,300,2);
		
		adic(new JLabelPad("Valor total do cupom: "),7,60,150,20);
		adic(txtVlrCupom,160,60,100,20);
		adic(new JLabelPad("Valor em dinheiro: "),7,85,150,20);
		adic(txtVlrDinheiro,160,85,100,20);
		adic(new JLabelPad("Valor em cheque: "),7,110,150,20);
		adic(txtVlrCheque,160,110,100,20);
		adic(new JLabelPad("Valor em ch. elet./cartão: "),7,135,150,20);
		adic(txtVlrChequeElet,160,135,100,20);
		adic(new JLabelPad("Valor pago: "),7,160,150,20);
		adic(txtVlrPago,160,160,100,20);
		adic(new JLabelPad("Valor troco: "),7,185,150,20);
		adic(txtVlrTroco,160,185,100,20);
		
		
//Não pode commitar enquanto todo o processo tive OK:
		
		lcPlanoPag.setPodeCommit(false);
		
		txtVlrDinheiro.addFocusListener(this);
		txtVlrCheque.addFocusListener(this);
		txtVlrChequeElet.addFocusListener(this);
		
	}
	private Properties processaTef() {
		Properties retTef = tef.solicVenda(iNumCupom, txtVlrChequeElet.getVlrBigDecimal());
		
		if (retTef == null || !tef.validaTef(retTef))
			return null;
		
//		finalizaTEF(retTef);
		
		return retTef;
			
	}
	private boolean finalizaTEF(Properties retTef) {
	    boolean bRet = false;
	    //Voltar a utizar esta quando a rotina estiver funcionando!!
		if (/*bf.iniciaModoTEF(Aplicativo.strUsuario,FreedomPDV.bModoDemo)*/true) {
		    try {
		        if (!bf.abreComprovanteNaoFiscalVinculado(Aplicativo.strUsuario,"Cartao",txtVlrChequeElet.getVlrBigDecimal(),iNumCupom, FreedomPDV.bModoDemo))
                    throw new Exception("");
	            Object sLinhas[] = tef.retImpTef(retTef);
	            for (int i=0;i<sLinhas.length;i++) {
	                if (!bf.usaComprovanteNaoFiscalVinculado(Aplicativo.strUsuario,(String)sLinhas[i],FreedomPDV.bModoDemo))
	                    throw new Exception("");
	            }
		    
	            //Aguarda 5 segundo para imprimir o segundo comprovante:
		    
	            Thread.sleep(5000);
		    
	            for (int i=0;i<sLinhas.length;i++) {
	                if (!bf.usaComprovanteNaoFiscalVinculado(Aplicativo.strUsuario,(String)sLinhas[i],FreedomPDV.bModoDemo))
	                    throw new Exception("");
	            }
		        if (!bf.fechaComprovanteNaoFiscalVinculado(Aplicativo.strUsuario,FreedomPDV.bModoDemo))
                    throw new Exception("");
		    
		        bRet = true;
		    }
		    catch (Exception err) { }        
		    finally {
		        bf.finalizaModoTEF(Aplicativo.strUsuario,FreedomPDV.bModoDemo);
		    }
		}
		else {
		    Funcoes.mensagemInforma(null,"Não foi possível travar o teclado!!");
		    return false;
		}

		if (bRet)
		    bRet = tef.finalizaVenda(retTef);
		
		return bRet;

	}
	private boolean verifCaixa() {
		boolean bRetorno = false;
		int iRet = -1;
		try {
			String sSQL = "SELECT * FROM PVVERIFCAIXASP (?,?,?,?,?,?)";
			PreparedStatement ps = con.prepareStatement(sSQL);
			ps.setInt(1,Aplicativo.iNumEst);
			ps.setInt(2,Aplicativo.iCodEmp);
			ps.setInt(3,ListaCampos.getMasterFilial("PVCAIXA"));
			ps.setDate(4,Funcoes.dateToSQLDate(new Date()));
			ps.setInt(5,Aplicativo.iCodFilial);
			ps.setString(6,Aplicativo.strUsuario);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				iRet = rs.getInt(1);
			}
			rs.close();
			ps.close();
		}
		catch(SQLException err) {
			Funcoes.mensagemErro(this,"Erro ao verificar o caixa!!\n"+err.getMessage());
		}
		if (iRet != 4 && iRet != 2)
			Funcoes.mensagemErro(this,"Caixa não esta aberto!!");
		else
			bRetorno = true;
		return bRetorno;
	}
	private boolean gravaVenda() {
		boolean bRet = false;
		String sSQL = "UPDATE VDVENDA SET STATUSVENDA='V2' WHERE CODEMP=?" +
		" AND CODFILIAL=? AND CODVENDA=? AND TIPOVENDA='E'";
		try {
			PreparedStatement ps = con.prepareStatement(sSQL);
			ps.setInt(1,Aplicativo.iCodEmp);
			ps.setInt(2,ListaCampos.getMasterFilial("VDVENDA"));
			ps.setInt(3,iCodVenda);
			ps.executeUpdate();
			bRet = true;
		}
		catch (SQLException err) {
			Logger.gravaLogTxt("",Aplicativo.strUsuario,Logger.LGEB_BD,"Erro ao gravar a venda: "+err.getMessage());
		}
		return bRet;
	}
	private boolean finalizaVenda() {
	    boolean bRet = false;
		String sSQL = "UPDATE VDVENDA SET STATUSVENDA='V3' WHERE CODEMP=?" +
		" AND CODFILIAL=? AND CODVENDA=? AND TIPOVENDA='E'";
		try {
			PreparedStatement ps = con.prepareStatement(sSQL);
			ps.setInt(1,Aplicativo.iCodEmp);
			ps.setInt(2,ListaCampos.getMasterFilial("VDVENDA"));
			ps.setInt(3,iCodVenda);
			ps.executeUpdate();
			bRet = true;
		}
		catch (SQLException err) {
			Funcoes.mensagemErro(null,"Não foi possível finalizar a venda!");
			err.printStackTrace();
		}
		return bRet;
	}
	private boolean execFechamento() {
		boolean bRet = false;
		if (txtVlrPago.getVlrDouble().doubleValue() == 0) {
			Funcoes.mensagemInforma(this,"Digite o valor pago!");
			return false;
		}
		else if (txtCodPlanoPag.getVlrInteger().intValue() == 0) {
			Funcoes.mensagemInforma(this,"Digite o código da forma de pagamento!");
			return false;
		}
		else if (txtVlrPago.getVlrDouble().doubleValue() < txtVlrCupom.getVlrDouble().doubleValue()) {
			Funcoes.mensagemInforma(this,"Valor pago menor que o valor da venda!");
			return false;
		}
		else if (txtVlrChequeElet.getVlrDouble().doubleValue() > 0) {
		    if ((ppCompTef = processaTef()) == null) {
		        Funcoes.mensagemInforma(this,"Não foi possível concluir a TEF");
		        return false;
		    }
		    else if (txtVlrChequeElet.getVlrDouble().doubleValue() < txtVlrCupom.getVlrDouble().doubleValue())
		        return false;
		}
		else if (!gravaVenda()) 
			return false;
		try {
			
//			Fecha a venda:			

			String sSQL = "EXECUTE PROCEDURE PVFECHAVENDASP(?,?,?,?,?,?)";
			PreparedStatement ps = con.prepareStatement(sSQL);
			ps.setInt(1,Aplicativo.iCodEmp);
			ps.setInt(2,ListaCampos.getMasterFilial("PVMOVCAIXA"));
			ps.setBigDecimal(3,txtVlrPago.getVlrBigDecimal());
			ps.setInt(4,Aplicativo.iNumEst);
			ps.setDate(5,Funcoes.dateToSQLDate(new Date()));
			ps.setString(6,Aplicativo.strUsuario);
			ps.execute();
			ps.close();
			bRet = true;
		}
		catch (SQLException err) {
			Funcoes.mensagemErro(this,"Erro ao executar fechamento!\n"+err.getMessage());
			Logger.gravaLogTxt("",Aplicativo.strUsuario,Logger.LGEB_BD,"Erro ao executar fechamento.");
		}
		return bRet;
	}
	private boolean execTroco() {
		boolean bRet = false;
		
// Sangria para o troco:		
		
		try {
			String sSQL = "EXECUTE PROCEDURE PVSANGRIASP(?,?,?,?,?,?)";
			PreparedStatement ps = con.prepareStatement(sSQL);
			ps.setInt(1,Aplicativo.iCodEmp);
			ps.setInt(2,ListaCampos.getMasterFilial("PVMOVCAIXA"));
			ps.setBigDecimal(3,txtVlrTroco.getVlrBigDecimal());
			ps.setInt(4,Aplicativo.iNumEst);
			ps.setDate(5,Funcoes.dateToSQLDate(new Date()));
			ps.setString(6,Aplicativo.strUsuario);
			ps.execute();
			ps.close();
			bRet = true;
		}
		catch (SQLException err) {
			Funcoes.mensagemErro(this,"Erro ao executar o troco!\n"+err.getMessage());
			Logger.gravaLogTxt("",Aplicativo.strUsuario,Logger.LGEB_BD,"Erro ao executar o troco.");
		}
		return bRet;
	}
	private void recalcPago() {
		txtVlrPago.setVlrBigDecimal(
				txtVlrDinheiro.getVlrBigDecimal().add(
						txtVlrCheque.getVlrBigDecimal().add(
								txtVlrChequeElet.getVlrBigDecimal()
						)
				)
		);
		txtVlrTroco.setVlrBigDecimal(
				txtVlrPago.getVlrBigDecimal().subtract(
						txtVlrCupom.getVlrBigDecimal()
				)
		);
	}
	private int buscaPlanoPag() {
		int iRet = 0;
		String sSQL = "SELECT CODPLANOPAG FROM SGPREFERE4 WHERE " +
		"CODEMP=? AND CODFILIAL=?";
		try {
			PreparedStatement ps = con.prepareStatement(sSQL);
			ps.setInt(1,Aplicativo.iCodEmp);
			ps.setInt(2,Aplicativo.iCodFilial);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				iRet = rs.getInt("CodPlanoPag");
			}
			rs.close();
			ps.close();
		}
		catch(SQLException err) {
			Funcoes.mensagemErro(this,"Erro ao buscar o plano de pagemento.\n"+
					"Provavelmente não foram gravadas corretamente as preferências!\n"+err.getMessage());
			Logger.gravaLogTxt("",Aplicativo.strUsuario,Logger.LGEB_BD,"Erro ao buscar o plano de pagemento.");
		}
		return iRet;
	}
	public void actionPerformed(ActionEvent evt) {
	    boolean bRet = false;
		if (evt.getSource() == btOK) {
			if (execFechamento()) {
				if (FreedomPDV.bECFTerm) {
					if (bf.fechaCupomFiscal(Aplicativo.strUsuario,Funcoes.copy(txtDescPlanoPag.getVlrString(),16),"","",0.0,txtVlrPago.getVlrDouble().doubleValue(),"",FreedomPDV.bModoDemo)) {
						if (finalizaVenda()) {
						    btCancel.setEnabled(false);
						    
						    //Verifica se existe um comprovante de TEF para imprimir.
						    if (ppCompTef != null)
						        bRet = finalizaTEF(ppCompTef);
						    else
							    bRet = true;
						        
							if (bRet && txtVlrTroco.getVlrDouble().doubleValue() > 0) {
							    bRet = execTroco();
							}
						}
					}
				}
			}
			if (!bRet) {
			    return;
			}
		}
		
		super.actionPerformed(evt);
	}
	public void focusLost(FocusEvent fevt) {
		if (fevt.getSource() == txtVlrDinheiro ||
				fevt.getSource() == txtVlrCheque ||
				fevt.getSource() == txtVlrChequeElet) {
			recalcPago();
		}
	}
	public void focusGained(FocusEvent arg0) { }
	public void setTef(Tef tef) {
		this.tef = tef;
		txtVlrChequeElet.setAtivo(true);
	}
	public void setConexao(Connection cn) {
		con = cn;
		lcPlanoPag.setConexao(cn);
		txtCodPlanoPag.setVlrInteger(new Integer(buscaPlanoPag()));
		lcPlanoPag.carregaDados();
	}
}

