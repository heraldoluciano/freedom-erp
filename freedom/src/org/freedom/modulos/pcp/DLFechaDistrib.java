/**
 * @version 14/07/2003 <BR>
 * @author Setpoint Informática Ltda./Alex Rodrigues <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)DLFechaDistrib.java <BR>
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

package org.freedom.modulos.pcp;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.JOptionPane;

import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FFDialogo;

public class DLFechaDistrib extends FFDialogo {
  private JTextFieldPad txtQtdDist = new JTextFieldPad(JTextFieldPad.TP_DECIMAL,15,Aplicativo.casasDec);
  private JTextFieldPad txtLote = new JTextFieldPad(JTextFieldPad.TP_STRING,13,0);
  private JTextFieldPad txtSeqDist = new JTextFieldPad(JTextFieldPad.TP_INTEGER,10,0);
  private JTextFieldPad txtCodProd = new JTextFieldPad(JTextFieldPad.TP_INTEGER,15,0);
  private JTextFieldPad txtDescProd = new JTextFieldPad(JTextFieldPad.TP_STRING,50,0);
  private JTextFieldPad txtDtFabProd = new JTextFieldPad(JTextFieldPad.TP_DATE,10,0);
  private JTextFieldPad txtDiasValid = new JTextFieldPad(JTextFieldPad.TP_DATE,10,0);
 
   
  public DLFechaDistrib(Component cOrig,int iSeqDist, int iCodProd,String sDescProd, float ftQtdade) {
  	super(cOrig);
    setTitulo("Quantidade");
    setAtribos(310,220);
    
   
    adic(new JLabelPad("Cód.Prod"),7,10,80,20);
    adic(txtCodProd,7,30,80,20);
    adic(new JLabelPad("Descrição da estrutura"),90,10,180,20);
    adic(txtDescProd,90,30,190,20);
    adic(new JLabelPad("Seq.dist."),7,50,80,20);
    adic(txtSeqDist,7,70,80,20);
    adic(new JLabelPad("Quantidade"),90,50,90,20);
    adic(txtQtdDist,90,70,90,20);
    adic(new JLabelPad("Lote"),183,50,80,20);
    adic(txtLote,183,70,97,20);
    adic(new JLabelPad("Validade"),183,90,80,20);
    adic(txtDiasValid,183,110,97,20);
    
    txtCodProd.setVlrInteger(new Integer(iCodProd));
    txtDescProd.setVlrString(sDescProd);
    txtSeqDist.setVlrInteger(new Integer(iSeqDist));
    txtQtdDist.setVlrBigDecimal(new BigDecimal(ftQtdade));
    
    txtCodProd.setAtivo(false);
    txtDescProd.setAtivo(false);
    txtSeqDist.setAtivo(false);
    txtLote.setAtivo(false);
    txtDiasValid.setAtivo(false);
    btOK.addActionListener(this);
  }
  
  public void setConexao(Connection cn) {
  	 super.setConexao(cn);
     if(getUsaLote().equals("S")){
    	txtLote.setAtivo(true);
    	if(getUsaModLote())
    		setModLote();
    	else
    		txtLote.setVlrString(buscaLote(txtCodProd.getVlrInteger().intValue(),txtSeqDist.getVlrInteger().intValue(),true));
     }
     else
     	 txtLote.setAtivo(false);
  }

  public void setModLote() {
	  Object[] modLote = null;
	  try {
		  modLote = getModLote(txtCodProd.getVlrInteger().intValue(),txtSeqDist.getVlrInteger().intValue());
		  txtLote.setVlrString((String) modLote[0]);
		  txtDtFabProd.setVlrDate((Date) modLote[1]);
	  	  txtDiasValid.setVlrDate((Date) modLote[2]);
	  }
	  finally {
		  modLote = null;
	  }
  }
  public Object[] getValor() {
    Object[] oRetorno = new Object[3]; 
    oRetorno[0] = txtQtdDist.getVlrBigDecimal();
    oRetorno[1] = txtLote.getVlrString();
    oRetorno[2] = txtDiasValid.getVlrString();
    return oRetorno;
  }
  

  private String buscaLote(int iCodProd,int iSeqEst,boolean bSaldoPos) {
	String sRet = "";
	String sSQL = "SELECT MIN(L.CODLOTE) FROM EQLOTE L WHERE "
				+ "L.CODPROD=? AND L.CODFILIAL=? "
				+ (bSaldoPos?"AND L.SLDLIQLOTE>0 ":"")
				+ "AND L.CODEMP=? AND L.VENCTOLOTE = "
				+ "(SELECT MIN(VENCTOLOTE) FROM EQLOTE LS WHERE LS.CODPROD=L.CODPROD "
				+ "AND LS.CODFILIAL=L.CODFILIAL AND LS.CODEMP=L.CODEMP AND LS.SLDLIQLOTE>0 "
				+ "AND VENCTOLOTE >= CAST('today' AS DATE)" + ")";
	try {
		System.out.println(sSQL);
		PreparedStatement ps = con.prepareStatement(sSQL);
		
		ps.setInt(1, iCodProd);
		ps.setInt(2, ListaCampos.getMasterFilial("EQLOTE"));
		ps.setInt(3, Aplicativo.iCodEmp);
						
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			String sCodLote = rs.getString(1);
			if (sCodLote != null) {
				sRet = sCodLote.trim();
			}
		}
		rs.close();
		ps.close();
	} catch (SQLException err) {
		Funcoes.mensagemErro(this, "Erro ao buscar lote!\n" + err);
	}
	return sRet;
  }
  
  public boolean getUsaModLote(){
	  boolean bRetorno = false;
	  String sCodModLote = null;
	  String sSQL = "SELECT CODMODLOTE FROM PPESTRUTURA WHERE CODEMP=? AND CODFILIAL=? AND CODPROD=? AND SEQEST=?";
		try {
			PreparedStatement ps = con.prepareStatement(sSQL);
			ps.setInt(1, Aplicativo.iCodEmp);
			ps.setInt(2, ListaCampos.getMasterFilial("PPESTRUTURA"));
			ps.setInt(3, txtCodProd.getVlrInteger().intValue());
			ps.setInt(4, txtSeqDist.getVlrInteger().intValue());
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				sCodModLote = rs.getString(1);
			}
			rs.close();
			ps.close();
			
			if(sCodModLote!=null)
				bRetorno = true;
			else
				bRetorno = false;
		}	
		catch (SQLException err) {
			Funcoes.mensagemErro(this,"Erro ao buscar modelo de lote para estrutura!\n",true,con,err);
		}
		finally{
			sCodModLote = null;
			sSQL = null;
		}
	  return bRetorno;
  }
  
  public String getUsaLote(){
	String sUsaLote = "";
	String sSQL = "SELECT CLOTEPROD FROM EQPRODUTO WHERE CODEMP=? AND CODFILIAL=? AND CODPROD=?";
	try {
		PreparedStatement ps = con.prepareStatement(sSQL);
		ps.setInt(1, Aplicativo.iCodEmp);
		ps.setInt(2, ListaCampos.getMasterFilial("EQPRODUTO"));
		ps.setInt(3, txtCodProd.getVlrInteger().intValue());
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			sUsaLote = rs.getString(1);
		}
		rs.close();
		ps.close();
	}	
	catch (SQLException err) {
		Funcoes.mensagemErro(this,"Erro ao buscar obrigatoriedade de lote no produto!\n",true,con,err);
	}
	return sUsaLote;
  }
  
  public boolean existeLote(int iCodProd, String sCodLote){
	return FOP.existeLote(con, iCodProd, sCodLote);
  }

  public Object[] getModLote(int iCodProd, int iSeqEst){
	Object[] lote = null;
	int iDiasValid = 0;
	Date dtVenctoLote = null;//data de vencimento(data de fabricação + dias de validade
	Date dtFabProd = null;//data de fabricação
	String sModLote = null;//modelo do lote
	String sCodLote = null;//codigo do lote
	String sSQL = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	ObjetoModLote ObjMl = null;
	GregorianCalendar cal = null;
	try {
	  	sSQL = "SELECT E.CODMODLOTE, M.TXAMODLOTE, E.NRODIASVALID"
	  				+ " FROM PPESTRUTURA E, EQMODLOTE M"
					+ " WHERE E.CODEMP=? AND E.CODFILIAL=? AND E.CODPROD=? AND E.SEQEST=?"
					+ " AND E.CODEMPML=M.CODEMP AND E.CODFILIALML=M.CODFILIAL AND E.CODMODLOTE=M.CODMODLOTE";
	  				
		try {
			ps = con.prepareStatement(sSQL);
			ps.setInt(1, Aplicativo.iCodEmp);
			ps.setInt(2, ListaCampos.getMasterFilial("EQLOTE"));
			ps.setInt(3, iCodProd);
			ps.setInt(4, iSeqEst);
			
			rs = ps.executeQuery();
			if (rs.next()) {
				sCodLote = rs.getString(1);
				sModLote = rs.getString(2);
				iDiasValid = rs.getInt(3);
				
			}
			rs.close();
			ps.close();
			if (!con.getAutoCommit())
				con.commit();
		}
		catch (SQLException err) {
			Funcoes.mensagemErro(this, "Erro ao buscar lote!\n" + err);
		}
			   
		if(!(sCodLote.equals("")) && (getUsaLote().equals("S")) ){
			dtFabProd = new Date();
			ObjMl = new ObjetoModLote();
			ObjMl.setTexto(sModLote);
			sCodLote = ObjMl.getLote(new Integer(iCodProd),dtFabProd,con);  			
			cal = new GregorianCalendar();
			cal.setTime(dtFabProd);
			cal.add(GregorianCalendar.DAY_OF_YEAR,iDiasValid);
			dtVenctoLote = cal.getTime();
			lote = new Object[5];
			lote[0] = sCodLote;
			lote[1] = dtFabProd;
			lote[2] = dtVenctoLote;
			lote[3] = sModLote;
			lote[4] = new Integer(iDiasValid);
		}
	}
	finally {
		iDiasValid = 0;
		dtFabProd = null;
		dtVenctoLote = null;
		sModLote = null;
		sCodLote = null;
		sSQL = null;
		ps = null;
		rs = null;
		ObjMl = null;
		cal = null;
	}
	return lote;
  }
  public boolean gravaLote(){
	  boolean bret = false;
	  int iCodProd = txtCodProd.getVlrInteger().intValue();
	  int iSeqDist = txtSeqDist.getVlrInteger().intValue();
	  String sCodLote = txtLote.getVlrString();
	  Object lote[] = null;
	  Object retorno[] = null;
	  try {
		  lote =  getModLote(iCodProd, iSeqDist);
		  if (lote!=null) {
			  if((!existeLote(iCodProd, sCodLote))){			
					txtLote.setVlrString(sCodLote);
					txtDiasValid.setVlrDate((Date) lote[2]);
					retorno = FOP.gravaLote(con, true, (String) lote[3], getUsaLote(), (String) lote[3], iCodProd, 
							(Date) lote[1], ((Integer) lote[4]).intValue(), sCodLote );
					
					bret = ((Boolean) retorno[2]).booleanValue();
		  	  }
			  else if(Funcoes.mensagemConfirma(null,"Lote já cadastrado para o produto!\nDeseja usa-lo?")==JOptionPane.YES_OPTION){	
				    bret = true;
			  }
			  else
				  bret = false;
		  }
	  }
	  finally {
		iCodProd = 0;
		iSeqDist = 0;
		sCodLote = null;
		lote = null;
	  }
	  return bret;
  }
  
}
