/*
 * Created on 18/10/2004
 * Autor: robson 
 * Descrição: Classe mãe para telas de venda e orçamento
 */
package org.freedom.modulos.std;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FDetalhe;


/**
 * @author robson
 *
 * O Objetivo desta classe é de proporcionar a implementação do código comum
 * entre as telas de orçamento e de venda
 */
public abstract class FVD extends FDetalhe {

    public abstract int[] getParansPreco();
    
    public abstract void setParansPreco(BigDecimal bdPreco);
    
    public FVD() {
    	super();
    }
    public String getObsCli(int iCodCli) {
    	String sRetorno = "";
    	String sSQL = "SELECT OBSCLI FROM VDCLIENTE WHERE CODEMP=? AND CODFILIAL=? AND CODCLI=?";
    	PreparedStatement ps = null;
    	ResultSet rs = null;
    	try {
    		ps = con.prepareStatement(sSQL);
    		ps.setInt(1, Aplicativo.iCodEmp);
    		ps.setInt(2, ListaCampos.getMasterFilial("VDCLIENTE"));
    		ps.setInt(3, iCodCli);
    		rs = ps.executeQuery();
    		if (rs.next()) {
    			if (rs.getString("OBSCLI")!=null)
    				sRetorno = rs.getString("OBSCLI").trim();
    		}
    		rs.close();
    		ps.close();
    		if (!con.getAutoCommit())
    			con.commit();
    	}
    	catch (SQLException e) {
    		Funcoes.mensagemErro(null, "Erro carregando observações do cliente.\n"+e.getMessage());
    	}
    	finally {
    		rs = null;
    		ps = null;
    	}
    	return sRetorno;
    }
    public void buscaPreco() {
    	String sSQL = "SELECT PRECO FROM VDBUSCAPRECOSP(?,?,?,?,?,?,?,?,?,?,?,?)";
    	PreparedStatement ps = null;
    	ResultSet rs = null;
    	int[] iParans;
    	try {
    	  ps = con.prepareStatement(sSQL);
    	  
    	  iParans = getParansPreco();
    	  
    	  ps.setInt(1,iParans[0]);
    	  ps.setInt(2,iParans[1]);
    	  ps.setInt(3,iParans[2]);
    	  ps.setInt(4,iParans[3]);
    	  ps.setInt(5,iParans[4]);
    	  ps.setInt(6,iParans[5]);
    	  ps.setInt(7,iParans[6]);
    	  ps.setInt(8,iParans[7]);
    	  ps.setInt(9,iParans[8]);
    	  ps.setInt(10,iParans[9]);
    	  ps.setInt(11,iParans[10]);
    	  ps.setInt(12,iParans[11]);
    	  rs = ps.executeQuery();
    	  rs.next();
    	  setParansPreco(rs.getString(1) != null ? (new BigDecimal(rs.getString(1))) : (new BigDecimal("0")));
    	  rs.close();
    	  ps.close();
    	  if (!con.getAutoCommit())
    	      con.commit();

    	}
    	catch (SQLException err) {
    		Funcoes.mensagemErro(this,"Erro ao carregar o preço!\n"+err.getMessage(),true,con,err);
    	}
    	finally {
    	    sSQL = null;
    	    ps = null;
    	    rs = null;
    	}
    }
}

