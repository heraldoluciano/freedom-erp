/*
 * Created on 18/10/2004
 * Autor: robson 
 * Descrição: Classe mãe para telas de venda e orçamento
 */
package org.freedom.modulos.std;

import java.awt.Dimension;
import java.awt.Point;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import org.freedom.componentes.JTextAreaPad;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FDetalhe;
import org.freedom.telas.FObservacao;


/**
 * @author robson
 *
 * O Objetivo desta classe é de proporcionar a implementação do código comum
 * entre as telas de orçamento e de venda
 */
public abstract class FVD extends FDetalhe {

    public abstract int[] getParansPreco();
    
    public abstract void setParansPreco(BigDecimal bdPreco);
    
    public abstract Vector getParansDesconto();
    
    public FVD() {
    	super();
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
    /**
     * Mostra a observação do cliente
     * @param iCodCli codigo do cliente
     * @param location localização da FObsCliVend na tela
     * @param dimencao tamanho da FObsCliVend
     */
    public void mostraObsCli(int iCodCli, Point location, Dimension dimencao) {
    	String sObsCli = getObsCli(iCodCli);
		if (!sObsCli.equals("")) {						
			FObsCliVend.showVend((int)location.getX(),
								 (int)location.getY(),
								 (int)dimencao.getWidth(),
								 (int)dimencao.getHeight(),
								 sObsCli);
		}
    }
    /**
     * Busca a observação do cliente.
     * @param iCodCli codigo do cliente a pesquisar.
     * @return String contendo a observação do cliente.
     */
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
    /**
     * mostra uma FObsevacao contendo a descrição completa do produto,
     * quando clicado duas vezes sobre o JTextFieldFK do item.
     * @param txaObsIt JTextAreaPad.
     * @param iCodProd codigo do produto.
     * @param sDescProd descrição do produto.
     */
	protected void mostraTelaDecricao( JTextAreaPad txaObsIt, int iCodProd, String sDescProd ) {
		if (iCodProd == 0)
			return;
		String sDesc = txaObsIt.getVlrString();
		if (sDesc.equals(""))
			sDesc = buscaDescComp( iCodProd );
		if (sDesc.equals(""))
			sDesc = sDescProd;

		FObservacao obs = new FObservacao("Descrição completa", sDesc, 500);
		obs.setVisible(true);
		if (obs.OK) {
			txaObsIt.setVlrString(obs.getTexto());
			lcDet.edit();
		}
		obs.dispose();
	}
	/**
	 * Busca descrição completa do produto na tabela de produtos .
	 * @param iCodProd codigo do produto a pesquizar.
	 * @return String contendo a descrição completa do produto.
	 */
	private String buscaDescComp( int iCodProd) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sRet = "";
		String sSQL = "SELECT DESCCOMPPROD FROM EQPRODUTO WHERE CODPROD=?"
				+ " AND CODEMP=? AND CODFILIAL=?";
		try {
			ps = con.prepareStatement(sSQL);
			ps.setInt(1, iCodProd);
			ps.setInt(2, Aplicativo.iCodEmp);
			ps.setInt(3, ListaCampos.getMasterFilial( "EQPRODUTO" ));
			rs = ps.executeQuery();
			if (rs.next()) {
				sRet = rs.getString("DescCompProd");
			}

		} catch (SQLException err) {
			Funcoes.mensagemErro(this, "Erro ao buscar descrição completa!\n"
					+ err.getMessage(),true,con,err);
			err.printStackTrace();
		}
		return sRet != null ? sRet : "";
	}
    /**
     * Mostra a observação geral.
     * @param sTabela tabela a pesquisar.
     * @param iCod codigo da chave primaria.
     */
    public void mostraObs(String sTabela, int iCod) {
    	FObservacao obs = null;
    	PreparedStatement ps = null;
    	ResultSet rs = null;
    	String sSQLselect = null;
    	String sSQLupdate = null;
		try {
			try {
				if(sTabela.equals("VDVENDA")) {
					sSQLselect = "SELECT OBSVENDA FROM VDVENDA WHERE CODEMP=? AND CODFILIAL=? AND CODVENDA=? AND TIPOVENDA='V'";
					sSQLupdate = "UPDATE VDVENDA SET OBSVENDA=? WHERE CODEMP=? AND CODFILIAL=? AND CODVENDA=? AND TIPOVENDA='V'";
				}
				else if(sTabela.equals("VDORCAMENTO")) {
					sSQLselect = "SELECT OBSORC FROM VDORCAMENTO WHERE CODEMP=? AND CODFILIAL=? AND CODORC=?";
					sSQLupdate = "UPDATE VDORCAMENTO SET OBSORC=? WHERE CODEMP=? AND CODFILIAL=? AND CODORC=?";
				}
					
				ps = con.prepareStatement(sSQLselect);
				ps.setInt(1, Aplicativo.iCodEmp);
				ps.setInt(2, ListaCampos.getMasterFilial( sTabela ));
				ps.setInt(3, iCod);
				rs = ps.executeQuery();
				if (rs.next())
					obs = new FObservacao((rs.getString(1) != null ? rs
							.getString(1) : ""));
				else
					obs = new FObservacao("");

				if (!con.getAutoCommit())
					con.commit();
				
			} catch (SQLException err) {
				Funcoes.mensagemErro(this, "Erro ao carregar a observação!\n"
						+ err.getMessage(),true,con,err);
			}
			if (obs != null) {
				obs.setVisible(true);
				if (obs.OK) {
					try {
						ps = con.prepareStatement(sSQLupdate);
						ps.setString(1, obs.getTexto());
						ps.setInt(2, Aplicativo.iCodEmp);
						ps.setInt(3, ListaCampos.getMasterFilial( sTabela ));
						ps.setInt(4, iCod);
						ps.executeUpdate();
						if (!con.getAutoCommit())
							con.commit();
					} catch (SQLException err) {
						Funcoes.mensagemErro(this,
								"Erro ao inserir observação no orçamento!\n"
										+ err.getMessage(),true,con,err);
					}
				}
				obs.dispose();
			}
		} finally {
			ps = null;
	    	rs = null;
	    	sSQLselect = null;
	    	sSQLupdate = null;
		}
    	
    }
    /**
     * Verifica o proximo codigo na tabela para evitar
     * erros de chave primaria no banco de dados.
     * essa verificação depende da configuração do sistema.
     * @param sTabela tabela a verificar
     * @param campo campo da chave primaria
     */
    public void testaCodPK(String sTabela, JTextFieldPad campo) {
    	
    	PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement("SELECT * FROM SPGERANUM(?,?,?)");
			ps.setInt(1, Aplicativo.iCodEmp);
			if(sTabela.equals("VDVENDA")) {
				ps.setInt(2, ListaCampos.getMasterFilial("VDVENDA"));
				ps.setString(3, "VD");
			}
			else if(sTabela.equals("VDORCAMENTO")) {
				ps.setInt(2, ListaCampos.getMasterFilial("VDORCAMENTO"));
				ps.setString(3, "OC");
			}
			rs = ps.executeQuery();
			rs.next();
			campo.setVlrString(rs.getString(1));
			rs.close();
			ps.close();
			if (!con.getAutoCommit())
				con.commit();
		} catch (SQLException err) {
			Funcoes.mensagemErro(this, "Erro ao confirmar número do pedido!\n"
					+ err.getMessage(),true,con,err);
		} finally {
			ps = null;
			rs = null;
		}
    	
    }
    /**
     * Calcula o valor bruto do produto
     * @param arg0 preço do produto
     * @param arg1 quantidade do produto
     * @return valor do produto
     */
	protected BigDecimal calcVlrProd(BigDecimal arg0, BigDecimal arg1) {
		BigDecimal bdRetorno = arg0.multiply(arg1).divide(
				new BigDecimal("1"), Aplicativo.casasDecFin, BigDecimal.ROUND_HALF_UP);
		return bdRetorno;
	}
	/**
     * Calcula o valor total do produto
     * @param arg0 valor do produto
     * @param arg1 desconto
     * @return valor total do produto
     */
	protected BigDecimal calcVlrTotalProd(BigDecimal arg0, BigDecimal arg1) {
		BigDecimal bdRetorno = arg0.subtract(arg1)
				.divide(new BigDecimal("1"), Aplicativo.casasDecFin, BigDecimal.ROUND_HALF_UP);
		return bdRetorno;
	}
	/**
	 * Abre uma DLDescontItVenda, seta o desconto do item e a String dos descontos
	 */
	protected void mostraTelaDesconto() {
		
		Vector param = getParansDesconto();
		/*
		 * param
		 * 0 - descontos
		 * 1 - preço do item
		 * 2 - valor do desconto do item
		 * 3 - quantidade do item
		 */
		String sObsDesc = null;
		int iFim = 0;

		try {
			sObsDesc = ((JTextFieldPad)param.elementAt(0)).getVlrString();
			
			DLDescontItVenda dl = new DLDescontItVenda(
					this, ((JTextFieldPad)param.elementAt(1)).doubleValue(), parseDescs(sObsDesc));
			dl.setVisible(true);
			
			if (dl.OK) {
				((JTextFieldPad)param.elementAt(2)).setVlrBigDecimal(new BigDecimal(
								dl.getValor() * ((JTextFieldPad)param.elementAt(3)).doubleValue()));
				
				iFim = sObsDesc.indexOf("\n");
				if (iFim >= 0)
					sObsDesc = dl.getObs() + " " + sObsDesc.substring(iFim);
				else
					sObsDesc = dl.getObs() + " \n";
				((JTextFieldPad)param.elementAt(0)).setVlrString(sObsDesc);
			}
			
			dl.dispose();
		} finally {
			param = null;
			sObsDesc = null;
		}
		
	}
	/**
	 * Corverte para array a String com os descontos.
	 * @param arg0 String com os descontos.
	 * @return array dos descontos
	 */
	private String[] parseDescs(String arg0) {
		String[] sRet = new String[5];
		String sObs = arg0;
		int iFim = sObs.indexOf('\n');
		int iPos = 0;
		if (iFim > 0) {
			sObs = sObs.substring(0, iFim);
			if (sObs.indexOf("Desc.: ") == 0) {
				sObs = sObs.substring(7);
				iPos = sObs.indexOf('+');
				for (int i = 0; (iPos > 0) && (i < 5); i++) {
					sRet[i] = sObs.substring(0, iPos - 1);
					if (iPos != iFim)
						sObs = sObs.substring(iPos + 1);
					if (iPos == iFim)
						break;
					if ((iPos = sObs.indexOf('+')) == -1)
						iPos = iFim = sObs.length();
				}
			}
		}
		return sRet;
	}
    
}

