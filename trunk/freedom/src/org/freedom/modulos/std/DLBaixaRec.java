/**
 * @version 14/07/2003 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)DLBaixaRec.java <BR>
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
 */

package org.freedom.modulos.std;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.freedom.componentes.JLabelPad;

import org.freedom.acao.CarregaEvent;
import org.freedom.acao.CarregaListener;
import org.freedom.acao.EditEvent;
import org.freedom.acao.EditListener;
import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FFDialogo;

public class DLBaixaRec extends FFDialogo implements CarregaListener, FocusListener, EditListener {
	
	private static final long serialVersionUID = 1L;
	private JTextFieldPad txtCodCli = new JTextFieldPad(JTextFieldPad.TP_INTEGER,10,0);
	private JTextFieldPad txtRazCli = new JTextFieldPad(JTextFieldPad.TP_STRING,50,0);
	private JTextFieldPad txtCodConta = new JTextFieldPad(JTextFieldPad.TP_STRING,10,0);
	private JTextFieldPad txtDescConta = new JTextFieldPad(JTextFieldPad.TP_STRING,50,0);
	private JTextFieldPad txtCodPlan = new JTextFieldPad(JTextFieldPad.TP_STRING,13,0);
	private JTextFieldPad txtDescPlan = new JTextFieldPad(JTextFieldPad.TP_STRING,50,0);
	//private JTextFieldPad txtCodBanco = new JTextFieldPad(JTextFieldPad.TP_STRING,3,0);
	//private JTextFieldFK txtDescBanco = new JTextFieldFK(JTextFieldPad.TP_STRING,40,0);
	private JTextFieldPad txtCodCC = new JTextFieldPad(JTextFieldPad.TP_STRING,19,0);
	private JTextFieldPad txtAnoCC = new JTextFieldPad(JTextFieldPad.TP_INTEGER,10,0);
	private JTextFieldFK  txtSiglaCC = new JTextFieldFK(JTextFieldPad.TP_STRING,10,0);
	private JTextFieldFK  txtDescCC = new JTextFieldFK(JTextFieldPad.TP_STRING,50,0);
	private JTextFieldPad txtDoc = new JTextFieldPad(JTextFieldPad.TP_STRING,10,0);
	private JTextFieldPad txtDtEmis = new JTextFieldPad(JTextFieldPad.TP_DATE,10,0);
	private JTextFieldPad txtVlrParc = new JTextFieldPad(JTextFieldPad.TP_NUMERIC,15,2);
	private JTextFieldPad txtPercDesc = new JTextFieldPad(JTextFieldPad.TP_NUMERIC,9,2);
	private JTextFieldPad txtVlrDesc = new JTextFieldPad(JTextFieldPad.TP_NUMERIC,15,2);
	private JTextFieldPad txtPercJuros = new JTextFieldPad(JTextFieldPad.TP_NUMERIC,9,2);
	private JTextFieldPad txtVlrJuros = new JTextFieldPad(JTextFieldPad.TP_NUMERIC,15,2);
	private JTextFieldPad txtVlrAberto = new JTextFieldPad(JTextFieldPad.TP_NUMERIC,15,2);
	private JTextFieldPad txtDtVenc = new JTextFieldPad(JTextFieldPad.TP_DATE,10,0);
	private JTextFieldPad txtDtPagto = new JTextFieldPad(JTextFieldPad.TP_DATE,10,0);
	private JTextFieldPad txtVlrPago = new JTextFieldPad(JTextFieldPad.TP_NUMERIC,15,2);
	private JTextFieldPad txtVlr = new JTextFieldPad(JTextFieldPad.TP_NUMERIC,15,2);
	private JTextFieldPad txtObs = new JTextFieldPad(JTextFieldPad.TP_STRING,250,0);
	private ListaCampos lcConta = new ListaCampos(this);
	private ListaCampos lcPlan = new ListaCampos(this);
	private ListaCampos lcCC = new ListaCampos(this);
	boolean bJurosPosCalc = false;
	public enum EColBaixa{ CODCLI, RAZCLI, NUMCONTA, CODPLAN, DOC, DTEMIT, DTVENC,
		VLRPARC, VLRAPAG, VLRDESC, VLRJUROS, DTPGTO, VLRPAGO, CODCC, OBS };
	public enum EColRetBaixa{ NUMCONTA, CODPLAN, DOC, DTPAGTO, VLRPAGO, 
		VLRDESC, VLRJUROS, CODCC, OBS };
	
	public DLBaixaRec(Component cOrig) {
		super(cOrig);
		setTitulo("Baixa");
		setAtribos(380,450);    
		
		txtCodPlan.setRequerido(true);
		txtCodConta.setRequerido(true);
		txtDoc.setRequerido(true);
		txtDtPagto.setRequerido(true);
		txtVlr.setRequerido(true);
		txtObs.setRequerido(true);
		
		
		lcConta.add(new GuardaCampo( txtCodConta, "NumConta", "Nº Conta", ListaCampos.DB_PK, false));
		lcConta.add(new GuardaCampo( txtDescConta, "DescConta", "Descrição da conta", ListaCampos.DB_SI,false));
		lcConta.montaSql(false, "CONTA", "FN");
		lcConta.setReadOnly(true);
		txtCodConta.setTabelaExterna(lcConta);
		txtCodConta.setFK(true);
		txtCodConta.setNomeCampo("NumConta");
		
		lcPlan.add(new GuardaCampo( txtCodPlan, "CodPlan", "Cód.plan.", ListaCampos.DB_PK,false));
		lcPlan.add(new GuardaCampo( txtDescPlan, "DescPlan", "Descrição", ListaCampos.DB_SI,false));
		lcPlan.setWhereAdic("TIPOPLAN = 'R' AND NIVELPLAN = 6");
		lcPlan.montaSql(false, "PLANEJAMENTO", "FN");
		lcPlan.setReadOnly(true);
		txtCodPlan.setTabelaExterna(lcPlan);
		txtCodPlan.setFK(true);
		txtCodPlan.setNomeCampo("CodPlan");
		
		lcCC.add(new GuardaCampo( txtCodCC, "CodCC", "Código", ListaCampos.DB_PK,false));
		lcCC.add(new GuardaCampo( txtSiglaCC, "SiglaCC", "Sigla", ListaCampos.DB_SI,false));
		lcCC.add(new GuardaCampo( txtDescCC, "DescCC", "Descrição", ListaCampos.DB_SI,false));
		lcCC.add(new GuardaCampo( txtAnoCC, "AnoCC", "Ano-Base", ListaCampos.DB_PK, false));
		lcCC.setReadOnly(true);
		lcCC.setQueryCommit(false);
		lcCC.setWhereAdic("NIVELCC=10");
		lcCC.montaSql(false, "CC", "FN");    
		txtCodCC.setTabelaExterna(lcCC);
		txtCodCC.setFK(true);
		txtCodCC.setNomeCampo("CodCC");
		txtAnoCC.setTabelaExterna(lcCC);
		txtAnoCC.setFK(true);
		txtAnoCC.setNomeCampo("AnoCC");
		
		txtCodCli.setAtivo(false);
		txtRazCli.setAtivo(false);
		txtDescConta.setAtivo(false);
		txtDescPlan.setAtivo(false);
		txtDtEmis.setAtivo(false);
		txtDtVenc.setAtivo(false);
		txtVlrAberto.setAtivo(false);
		
		adic(new JLabelPad("Cód.cli."),7,0,80,20);
		adic(txtCodCli,7,20,80,20);
		adic(new JLabelPad("Razão social do cliente"),90,0,200,20);
		adic(txtRazCli,90,20,250,20);
		adic(new JLabelPad("Número"),7,40,80,20);
		adic(txtCodConta,7,60,80,20);
		adic(new JLabelPad("Descrição da conta"),90,40,200,20);
		adic(txtDescConta,90,60,250,20);
		adic(new JLabelPad("Cód.ctg."),7,80,80,20);
		adic(txtCodPlan,7,100,100,20);
		adic(new JLabelPad("Descrição da categoria"),110,80,200,20);
		adic(txtDescPlan,110,100,230,20);
		adic(new JLabelPad("Cód.c.c."),7,120,100,20);
		adic(txtCodCC,7,140,100,20);
		adic(new JLabelPad("Descrição do centro de custo"),110,120,200,20);
		adic(txtDescCC,110,140,230,20);
		adic(new JLabelPad("Doc."),7,160,110,20);
		adic(txtDoc,7,180,110,20);
		adic(new JLabelPad("Emissão"),120,160,107,20);
		adic(txtDtEmis,120,180,107,20);
		adic(new JLabelPad("Vencimento"),230,160,110,20);
		adic(txtDtVenc,230,180,110,20);
		adic(new JLabelPad("% Desc."),7,200,60,20);
		adic(txtPercDesc,7,220,60,20);
		adic(new JLabelPad("Vlr. Desc."),70,200,107,20);
		adic(txtVlrDesc,70,220,107,20);
		//A label vai no set conexao...    
		adic(txtPercJuros,180,220,57,20);
		adic(new JLabelPad("Vlr. Juros."),240,200,100,20);
		adic(txtVlrJuros,240,220,100,20);
		adic(new JLabelPad("Vlr. Aberto."),7,240,110,20);
		adic(txtVlrAberto,7,260,110,20);
		adic(new JLabelPad("Dt. Pagto."),120,240,107,20);
		adic(txtDtPagto,120,260,107,20);
		adic(new JLabelPad("Vlr. Pago"),230,240,110,20);
		adic(txtVlr,230,260,110,20);
		adic(new JLabelPad("Observações"),7,280,200,20);
		adic(txtObs,7,300,333,20);
		    
		lcCC.addCarregaListener(this);
		txtVlr.addFocusListener(this);
		txtPercDesc.addFocusListener(this);
		txtVlrDesc.addFocusListener(this);
		txtVlrDesc.addEditListener(this);
		txtPercJuros.addFocusListener(this);
		txtVlrJuros.addFocusListener(this);
		txtVlrJuros.addEditListener(this);
	}

	private void calcDesc() {
		if (txtPercDesc.getVlrDouble().doubleValue() != 0) {
			txtVlrDesc.setVlrBigDecimal(txtPercDesc.getVlrBigDecimal()
					.multiply(txtVlrParc.getVlrBigDecimal())
						.divide(new BigDecimal(100),2,BigDecimal.ROUND_HALF_UP));
		}
		atualizaAberto();
	}
	
	private void calcJuros() {
		if (!bJurosPosCalc) {
			if (txtPercJuros.getVlrDouble().doubleValue() != 0)
				txtVlrJuros.setVlrBigDecimal(txtPercJuros.getVlrBigDecimal().multiply(txtVlrParc.getVlrBigDecimal())
						.divide(new BigDecimal(100),2,BigDecimal.ROUND_HALF_UP));
		}
		else 
			txtVlrJuros.setVlrBigDecimal(txtPercJuros.getVlrBigDecimal().multiply(txtVlrParc.getVlrBigDecimal())
					.divide(new BigDecimal(100),2,BigDecimal.ROUND_HALF_UP)
						.multiply(new BigDecimal(Funcoes.getNumDiasAbs(txtDtVenc.getVlrDate(),new Date()))));
		atualizaAberto();
	}
	
	private void atualizaPagto() {
		if (txtVlr.getVlrBigDecimal().compareTo(txtVlrAberto.getVlrBigDecimal()) > 0) {
			txtVlrJuros.setVlrBigDecimal(txtVlrJuros.getVlrBigDecimal()
					.add(txtVlr.getVlrBigDecimal().subtract(txtVlrAberto.getVlrBigDecimal())));
			atualizaAberto();
		}
	}

	private void atualizaAberto() {
		txtVlrAberto.setVlrBigDecimal(txtVlrParc.getVlrBigDecimal().subtract(txtVlrDesc.getVlrBigDecimal())
				.add(txtVlrJuros.getVlrBigDecimal()).subtract(txtVlrPago.getVlrBigDecimal()));
	}
	
	private void aplicaJuros() {
		PreparedStatement ps = null;
		ResultSet rs = null;
		GregorianCalendar cal = null;
		GregorianCalendar calVenc = null;
		String sSQL = null;
		
		try {
			sSQL = "SELECT FIRST 1 P.CODTBJ,T.TIPOTBJ,IT.PERCITTBJ FROM " +
		       "SGPREFERE1 P, FNTBJUROS T, FNITTBJUROS IT WHERE " +
		       "T.CODEMP=P.CODEMPTJ AND " +
		       "T.CODFILIAL=P.CODFILIALTJ AND " +
		       "T.CODTBJ=P.CODTBJ AND " +
		       "IT.CODEMP=T.CODEMP AND " +
		       "IT.CODFILIAL=T.CODFILIAL AND " +
		       "IT.CODTBJ=T.CODTBJ AND " +
		       "IT.ANOITTBJ <= ? AND IT.MESITTBJ <= ? AND " +
			   "P.CODEMP=? AND P.CODFILIAL=? " +
			   "ORDER BY IT.ANOITTBJ DESC, IT.MESITTBJ DESC "; 
			
			ps = con.prepareStatement(sSQL);
			cal = new GregorianCalendar();
			calVenc = new GregorianCalendar();
			calVenc.setTime(txtDtVenc.getVlrDate());
			ps.setInt(1,cal.get(Calendar.YEAR));
			ps.setInt(2,cal.get(Calendar.MONTH)+1);
			ps.setInt(3,Aplicativo.iCodEmp);
			ps.setInt(4,Aplicativo.iCodFilial);
			rs = ps.executeQuery();
			if (rs.next()) {
				switch(rs.getString("TipoTBJ").toCharArray()[0]) {
					case 'D':
						txtVlrJuros.setVlrBigDecimal(txtVlrParc.getVlrBigDecimal().multiply(new BigDecimal(
								(Funcoes.getNumDiasAbs(txtDtVenc.getVlrDate(),new Date()))*rs.getDouble(3)))
									.divide(new BigDecimal(100),2,BigDecimal.ROUND_HALF_UP));
					break;
					case 'M':
						txtVlrJuros.setVlrBigDecimal(txtVlrParc.getVlrBigDecimal().multiply(new BigDecimal(
								(Funcoes.getNumDiasAbs(txtDtVenc.getVlrDate(),new Date()))*rs.getDouble(3)))
									.divide(new BigDecimal(100*30),2,BigDecimal.ROUND_HALF_UP));
						break;
					case 'B':
						txtVlrJuros.setVlrBigDecimal(txtVlrParc.getVlrBigDecimal().multiply(new BigDecimal(
								(Funcoes.getNumDiasAbs(txtDtVenc.getVlrDate(),new Date()))*rs.getDouble(3)))
									.divide(new BigDecimal(100*60),2,BigDecimal.ROUND_HALF_UP));
						break;
					case 'T':
						txtVlrJuros.setVlrBigDecimal(txtVlrParc.getVlrBigDecimal().multiply(new BigDecimal(
								(Funcoes.getNumDiasAbs(txtDtVenc.getVlrDate(),new Date()))*rs.getDouble(3)))
									.divide(new BigDecimal(100*90),2,BigDecimal.ROUND_HALF_UP));
						break;
					case 'S':
						txtVlrJuros.setVlrBigDecimal(txtVlrParc.getVlrBigDecimal().multiply(new BigDecimal(
								(Funcoes.getNumDiasAbs(txtDtVenc.getVlrDate(),new Date()))*rs.getDouble(3)))
									.divide(new BigDecimal(100*182),2,BigDecimal.ROUND_HALF_UP));
						break;
					case 'A':
						txtVlrJuros.setVlrBigDecimal(txtVlrParc.getVlrBigDecimal().multiply(new BigDecimal(
								(Funcoes.getNumDiasAbs(txtDtVenc.getVlrDate(),new Date()))*rs.getDouble(3)))
									.divide(new BigDecimal(100*365),2,BigDecimal.ROUND_HALF_UP));
						break;
				}
			}
			rs.close();
			ps.close();
			if(!con.getAutoCommit())
				con.commit();
		} catch (SQLException err) {
			Funcoes.mensagemErro(this,"Erro ao buscar juros do sistema!\n"+err.getMessage(),true,con,err);
			err.printStackTrace();
		} finally {
			ps = null;
			rs = null;
			cal = null;
			calVenc = null;
			sSQL = null;
		}
	}

	private boolean getJurosPosCalc() {
		boolean bRet = false;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sSQL = "SELECT JUROSPOSCALC FROM SGPREFERE1 WHERE CODEMP=? AND CODFILIAL=?";
		try {
			ps = con.prepareStatement(sSQL);
			ps.setInt(1,Aplicativo.iCodEmp);
			ps.setInt(2,ListaCampos.getMasterFilial("SGPREFERE1"));
			rs = ps.executeQuery();
			if (rs.next())
				bRet = rs.getString("JUROSPOSCALC") != null && rs.getString("JUROSPOSCALC").equals("S");
			rs.close();
			ps.close();
			if(!con.getAutoCommit())
				con.commit();
		} catch (SQLException err) {
			Funcoes.mensagemErro(this,"Erro ao buscar o ano-base para o centro de custo.\n"+
					err.getMessage(),true,con,err);
		} finally {
			ps = null;
			rs = null;
			sSQL = null;
		}
		return bRet;
	}
	
	private int getAnoBaseCC() {
		int iRet = 0;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sSQL = "SELECT ANOCENTROCUSTO FROM SGPREFERE1 WHERE CODEMP=? AND CODFILIAL=?";
		try {
			ps = con.prepareStatement(sSQL);
			ps.setInt(1,Aplicativo.iCodEmp);
			ps.setInt(2,ListaCampos.getMasterFilial("SGPREFERE1"));
			rs = ps.executeQuery();
			if (rs.next())
				iRet = rs.getInt("ANOCENTROCUSTO");
			rs.close();
			ps.close();
			if(!con.getAutoCommit())
				con.commit();
		} catch (SQLException err) {
			Funcoes.mensagemErro(this,"Erro ao buscar o ano-base para o centro de custo.\n"+
					err.getMessage(),true,con,err);
		} finally {
			ps = null;
			rs = null;
			sSQL = null;
		}
		return iRet;
	}
	
	public Object[] getValores() {
		Object[] sRetorno = new Object[9];
		sRetorno[EColRetBaixa.NUMCONTA.ordinal()] = txtCodConta.getVlrString();
		sRetorno[EColRetBaixa.CODPLAN.ordinal()] = txtCodPlan.getVlrString();
		sRetorno[EColRetBaixa.DOC.ordinal()] = txtDoc.getVlrString();
		sRetorno[EColRetBaixa.DTPAGTO.ordinal()] = (java.util.Date) txtDtPagto.getVlrDate();
		sRetorno[EColRetBaixa.VLRPAGO.ordinal()] = txtVlr.getVlrBigDecimal();
		sRetorno[EColRetBaixa.VLRDESC.ordinal()] = txtVlrDesc.getVlrBigDecimal();
		sRetorno[EColRetBaixa.VLRJUROS.ordinal()] = txtVlrJuros.getVlrBigDecimal();
		sRetorno[EColRetBaixa.CODCC.ordinal()] = txtCodCC.getVlrString();
		sRetorno[EColRetBaixa.OBS.ordinal()] = txtObs.getVlrString();
		return sRetorno;
	}

	public void setValores(Object[] sVals) {
		
		txtCodCli.setVlrInteger( (Integer) sVals[ EColBaixa.CODCLI.ordinal() ] );
		txtRazCli.setVlrString( (String) sVals[ EColBaixa.RAZCLI.ordinal() ] );
		txtCodConta.setVlrString( (String) sVals[ EColBaixa.NUMCONTA.ordinal() ] );
		txtCodPlan.setVlrString( (String) sVals[ EColBaixa.CODPLAN.ordinal() ] );
		txtDoc.setVlrString( (String) sVals[ EColBaixa.DOC.ordinal() ] );
		txtDtEmis.setVlrDate( (Date) sVals[ EColBaixa.DTEMIT.ordinal() ] );
		txtDtVenc.setVlrDate( (Date) sVals[ EColBaixa.DTVENC.ordinal() ] );
		txtVlrParc.setVlrBigDecimal( (BigDecimal) sVals[ EColBaixa.VLRPARC.ordinal() ] );
		txtVlr.setVlrBigDecimal( (BigDecimal) sVals[EColBaixa.VLRAPAG.ordinal()]);
		txtVlrDesc.setVlrBigDecimal( (BigDecimal) sVals[ EColBaixa.VLRDESC.ordinal() ] );
		txtVlrJuros.setVlrBigDecimal( (BigDecimal) sVals[ EColBaixa.VLRJUROS.ordinal() ] );
		txtVlrAberto.setVlrBigDecimal( (BigDecimal) sVals[EColBaixa.VLRAPAG.ordinal()]);
		txtDtPagto.setVlrDate( (Date) sVals[EColBaixa.DTPGTO.ordinal()]);
		txtVlrPago.setVlrBigDecimal((BigDecimal) sVals[EColBaixa.VLRPAGO.ordinal()]);
		txtCodCC.setVlrString( (String) sVals[EColBaixa.CODCC.ordinal() ] );
		txtObs.setVlrString( (String) sVals[ EColBaixa.OBS.ordinal() ] );
		
	}

	public void setConexao(Connection cn) {
		super.setConexao(cn);
	    //lcBanco.setConexao(cn);
		lcConta.setConexao(cn);
		lcConta.carregaDados();
		lcPlan.setConexao(cn);
		lcPlan.carregaDados();
		lcCC.setConexao(cn);
		lcCC.carregaDados();
		if (!(bJurosPosCalc = getJurosPosCalc()) && txtVlrJuros.getVlrBigDecimal().doubleValue() == 0) {
			adic(new JLabelPad("% Juros."),180,200,57,20);
			aplicaJuros();
		} else
			adic(new JLabelPad("% Dia."),180,200,57,20);	
	}
	
	public void actionPerformed(ActionEvent evt) {
		if (evt.getSource() == btOK) {
			if (txtCodConta.getVlrString().length() < 1) {
				Funcoes.mensagemInforma(this,"Número da conta é requerido!");
			} else if (txtCodPlan.getVlrString().length() < 13) {
				Funcoes.mensagemInforma(this,"Código da categoria é requerido!");
			} else if (txtDtPagto.getVlrString().length() < 10) {
				Funcoes.mensagemInforma(this,"Data do pagamento é requerido!");
			} else if (txtVlr.getVlrString().length() < 4) {
				Funcoes.mensagemInforma(this,"Valor pago é requerido!");
			} else if (txtVlr.floatValue() <= 0) {
				Funcoes.mensagemInforma(this,"Valor pago deve ser maior que zero!");
			} else {
				super.actionPerformed(evt);
			}
		} else
			super.actionPerformed(evt);
	}

	public void focusGained(FocusEvent fevt) { }

	public void focusLost(FocusEvent fevt) {
		if (fevt.getSource() == txtPercDesc)
			calcDesc();
		else if (fevt.getSource() == txtPercJuros)
			calcJuros();
		else if (fevt.getSource() == txtVlrDesc)
			atualizaAberto();
		else if (fevt.getSource() == txtVlrJuros)
			atualizaAberto();
		else if (fevt.getSource() == txtVlr)
			atualizaPagto();
	}

	public void beforeCarrega(CarregaEvent cevt) {
		if (cevt.getListaCampos() == lcCC && txtAnoCC.getVlrInteger().intValue() == 0)
			txtAnoCC.setVlrInteger(new Integer(getAnoBaseCC()));
	}
	  
	public void afterCarrega(CarregaEvent cevt) { }

	public void edit(EditEvent eevt) {
		if (eevt.getSource() == txtVlrDesc)
			txtPercDesc.setVlrBigDecimal( new BigDecimal(0) );
		else if (eevt.getSource() == txtVlrJuros)
			txtPercJuros.setVlrBigDecimal( new BigDecimal(0) );
	}
	
	public void beforeEdit(EditEvent eevt) { }
	
	public void afterEdit(EditEvent eevt) { }

}
