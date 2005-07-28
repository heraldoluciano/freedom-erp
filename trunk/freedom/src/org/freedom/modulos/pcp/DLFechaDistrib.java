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
import java.math.BigDecimal;
import java.sql.Connection;
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.GregorianCalendar;

import javax.swing.JOptionPane;

import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JTextFieldFK;
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
  private JTextFieldFK txtDescLoteProdEst = new JTextFieldFK(JTextFieldPad.TP_DATE,10, 0);
  private JTextFieldFK txtSldLiqProd = new JTextFieldFK(JTextFieldPad.TP_NUMERIC, 15, Aplicativo.casasDec);
  private ListaCampos lcLoteProdEst = new ListaCampos(this, "LE");
  int iCodProd = 0;
  int iSeqEst = 0;
  String sCodLote = "";
  String sModLote = "";
  String sDtFabric = "";
  int iDiasVald = 0;
  
  public DLFechaDistrib(Component cOrig,int iSeqDist, int iCProd,String sDescProd, float ftQtdade) {
  	super(cOrig);
    setTitulo("Quantidade");
    setAtribos(310,220);
    
    iCodProd = iCProd;
    iSeqEst = iSeqDist;
    
    lcLoteProdEst.add(new GuardaCampo(txtLote, "CodLote", "Lote",ListaCampos.DB_PK, txtDescLoteProdEst, false));
	lcLoteProdEst.add(new GuardaCampo(txtDescLoteProdEst, "VenctoLote", "Dt.vencto.",ListaCampos.DB_SI, false));
	lcLoteProdEst.add(new GuardaCampo(txtSldLiqProd, "SldLiqLote", "Saldo",ListaCampos.DB_SI, false));
	lcLoteProdEst.montaSql(false, "LOTE", "EQ");
	lcLoteProdEst.setQueryCommit(false);
	lcLoteProdEst.setReadOnly(true);
	txtLote.setTabelaExterna(lcLoteProdEst);
   
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
    
    txtCodProd.setVlrInteger(new Integer(iCodProd));
    txtDescProd.setVlrString(sDescProd);
    txtSeqDist.setVlrInteger(new Integer(iSeqDist));
    txtQtdDist.setVlrBigDecimal(new BigDecimal(ftQtdade));
    
    txtCodProd.setAtivo(false);
    txtDescProd.setAtivo(false);
    txtSeqDist.setAtivo(false);
    txtLote.setAtivo(false);
  }
  
  public void setConexao(Connection cn) {
  	 super.setConexao(cn);
     if(getUsaLote().equals("S")){
    	txtLote.setAtivo(true);
     	gravaLote(true);
     }
     else
     	 txtLote.setAtivo(false);
  }
  
  public Object[] getValor() {
    Object[] oRetorno = new Object[2]; 
    oRetorno[0] = txtQtdDist.getVlrBigDecimal();
    oRetorno[1] = txtLote.getVlrString();
    return oRetorno;
  }
  

  private String buscaLote(int iCodProd,int iSeqEst,boolean bSaldoPos) {
	String sRet = "";
	String sSQL = "SELECT MIN(L.CODLOTE) FROM EQLOTE L WHERE "
				+ "L.CODPROD=? AND L.CODFILIAL=? "
				+(bSaldoPos?"AND L.SLDLIQLOTE>0 ":"")
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
  
  public String getUsaLote(){
	String sUsaLote = "";
	String sSQL = "SELECT CLOTEPROD FROM EQPRODUTO WHERE CODEMP=? AND CODFILIAL=? AND CODPROD=?";
	try {
		PreparedStatement ps = con.prepareStatement(sSQL);
		ps.setInt(1, Aplicativo.iCodEmp);
		ps.setInt(2, ListaCampos.getMasterFilial("EQPRODUTO"));
		ps.setInt(3, iCodProd);
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
  
  public boolean existeLote(){
  	boolean bRet = false;
	String sSQL = "SELECT CODLOTE FROM EQLOTE WHERE CODEMP=? AND CODFILIAL=? AND CODPROD=? AND CODLOTE=?";
	try {
		PreparedStatement ps = con.prepareStatement(sSQL);
		ps.setInt(1, Aplicativo.iCodEmp);
		ps.setInt(2, ListaCampos.getMasterFilial("EQLOTE"));
		ps.setInt(3, iCodProd);
		ps.setString(4, sCodLote);
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			bRet=true;
		}
		rs.close();
		ps.close();
	}	
	catch (SQLException err) {
		Funcoes.mensagemErro(this,"Erro ao buscar existencia do lote!\n",true,con,err);
	}
  	return bRet;  	
  }

  public void gravaLote(boolean bInsere){
  	String sSQL = "SELECT E.CODMODLOTE, M.TXAMODLOTE, E.NRODIASVALID"
  				+ " FROM PPESTRUTURA E, EQMODLOTE M"
				+ " WHERE E.CODEMP=? AND E.CODFILIAL=? AND E.CODPROD=? AND E.SEQEST=?"
				+ " AND E.CODEMPML=M.CODEMP AND E.CODFILIALML=M.CODFILIAL AND E.CODMODLOTE=M.CODMODLOTE";
  				
	  	try {
			PreparedStatement ps = con.prepareStatement(sSQL);
						
			ps.setInt(1, Aplicativo.iCodEmp);
			ps.setInt(2, ListaCampos.getMasterFilial("EQLOTE"));
			ps.setInt(3, iCodProd);
			ps.setInt(4, iSeqEst);
			
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				sCodLote = rs.getString(1);
				sModLote = rs.getString(2);
				iDiasVald = rs.getInt(3);
				
			}
			rs.close();
			ps.close();
	  	}
	  	catch (SQLException err) {
	  			Funcoes.mensagemErro(this, "Erro ao buscar lote!\n" + err);
		}
		   
	if(!(sCodLote.equals("")) && (getUsaLote().equals("S")) ){
		txtDtFabProd.setVlrDate(new Date());
		ObjetoModLote ObjMl = new ObjetoModLote();
		ObjMl.setTexto(sModLote);
		String sLote = ObjMl.getLote(new Integer(iCodProd),txtDtFabProd.getVlrDate(),con);  			
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(txtDtFabProd.getVlrDate());
		cal.add(GregorianCalendar.DAY_OF_YEAR,iDiasVald);
		Date dtVenctoLote = cal.getTime();	
		sCodLote = sLote;
		if((!existeLote()) && (bInsere)){
			
			txtLote.setVlrString(sLote);
			
			if(Funcoes.mensagemConfirma(null,"Deseja criar o lote "+sLote.trim()+" ?")==JOptionPane.YES_OPTION){
				String sSql = "INSERT INTO EQLOTE (CODEMP,CODFILIAL,CODPROD,CODLOTE,VENCTOLOTE) VALUES(?,?,?,?,?)";
				try {
					   PreparedStatement ps = con.prepareStatement(sSql); 
						   ps.setInt(1,Aplicativo.iCodEmp);
						   ps.setInt(2,ListaCampos.getMasterFilial("EQLOTE"));
						   ps.setInt(3,iCodProd);
						   ps.setString(4,sLote);
						   ps.setDate(5,Funcoes.dateToSQLDate(dtVenctoLote));
					   
					   if (ps.executeUpdate() == 0) {
						  Funcoes.mensagemInforma(this,"Não foi possível inserir registro na tabela de Lotes!");
					   }
					   if (!con.getAutoCommit())
					      con.commit();
				 }
				 catch (SQLException err) {
				 	Funcoes.mensagemErro(this,"Erro ao inserir registro na tabela de Lotes!\n"+err.getMessage(),true,con,err); 
				 }
		  	}
			else
				txtLote.setVlrString(buscaLote(iCodProd,iSeqEst,true));
		}
		else if (bInsere){
			txtLote.setVlrString(buscaLote(iCodProd,iSeqEst,true));
			Funcoes.mensagemInforma(this,"Lote já cadastrado para o produto!");
		}
	}
  }
}
