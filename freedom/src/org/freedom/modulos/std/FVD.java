/**
 * @version 18/10/2004 <BR>
 * @author Setpoint Informática Ltda./Robson Sanches/Alex Rodrigues <BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.std <BR>
 * Classe:
 * @(#)FVD.java <BR>
 * 
 * Este programa é licenciado de acordo com a LPG-PC (Licença Pública Geral para
 * Programas de Computador), <BR>
 * versão 2.1.0 ou qualquer versão posterior. <BR>
 * A LPG-PC deve acompanhar todas PUBLICAÇÕES, DISTRIBUIÇÕES e REPRODUÇÕES deste
 * Programa. <BR> 
 * Caso uma cópia da LPG-PC não esteja disponível junto com este Programa, você
 * pode contatar <BR>
 * o LICENCIADOR ou então pegar uma cópia em: <BR>
 * Licença: http://www.lpg.adv.br/licencas/lpgpc.rtf <BR>
 * Para poder USAR, PUBLICAR, DISTRIBUIR, REPRODUZIR ou ALTERAR este Programa é
 * preciso estar <BR>
 * de acordo com os termos da LPG-PC <BR>
 * <BR>
 * 
 * O Objetivo desta classe é de proporcionar a implementação do código comum
 * entre as telas de orçamento e de venda
 *  
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
import org.freedom.telas.FPassword;

public abstract class FVD extends FDetalhe {
	    
	protected int casasDec = Aplicativo.casasDec;	
	
	protected int casasDecFin = Aplicativo.casasDecFin;    
    /**
     * indica se pode recalcular os itens.
     * ajuda a evitar updates desnecessarios ou erroneos.
     */
    private boolean recalculaPreco = false;
    /** 
     * @return parametros para a procedure que busca o preço do produto.
     */
    public abstract int[] getParansPreco();
    /**
     * seta os campos com os valores do buscaPreço().
     */
    public abstract void setParansPreco(BigDecimal bdPreco);
    /**
     * seta os campos de log
     */
    public abstract void setLog(String[] args);
    /**
     * @return parametros para mostrar a dialog de desconto.
     */
    public abstract Vector getParansDesconto();
    /**
     * @return parametros para mostrar a dialog de dassword.
     */
    public abstract String[] getParansPass();
    /**
     * Costrutor padrão.
     *
     */
    public FVD() {
    	super();
    }   
    /**
     * Altera os valores dos itens quando a alteração no cabeçalho
     * da venda, se selecionado no preferencias pra recalcular os valores,
     * conforme a tabela de preços do produto
     * @param alteraTodos indica se altera todos os itens da venda
     * @param tabela Tabela a se alterar
     */
    protected void calcVlrItem(String tabela, boolean alteraTodos) {

        if( alteraTodos ) {
            String sSQLSelect = null;
            String sSQLUpdate = null;
        	PreparedStatement ps = null;
        	PreparedStatement ps2 = null;
        	ResultSet rs = null;
        	Vector vCodItem = new Vector();
        	Vector vQtdItem = new Vector();
        	Vector vDescProd = new Vector();
        	Vector vPercDescProd = new Vector();
        	Vector vCodProd = new Vector();
        	Vector vPrecoProd = new Vector();
        	BigDecimal bdBuscaPreco = null;
        	BigDecimal bdPrecoProd = null;
        	BigDecimal bdVlrBrutoProd = null;
        	BigDecimal bdVlrLiqProd = null;
        	BigDecimal bdQtdItem = null;
        	BigDecimal bdDescProd = null;
        	BigDecimal bdPercDescProd = null;
        	int[] iParans;
        	try {
          	  
        	    iParans = getParansPreco();
        	    
        	    if(tabela.equalsIgnoreCase("VDVENDA")) {
	        	    sSQLSelect = "SELECT CODITVENDA, CODPROD, PRECOITVENDA, QTDITVENDA, PERCDESCITVENDA, VLRDESCITVENDA FROM VDITVENDA " +
	        	    	   		  "WHERE CODEMP=? AND CODFILIAL=? AND CODVENDA=? AND TIPOVENDA='V'";
	        	    sSQLUpdate = "UPDATE VDITVENDA SET PRECOITVENDA=?, VLRPRODITVENDA=?, VLRLIQITVENDA=?, VLRDESCITVENDA=?  " +
	        	    			 "WHERE CODEMP=? AND CODFILIAL=? AND CODVENDA=? AND CODITVENDA=? AND TIPOVENDA='V'";
        	    }
        	    else if(tabela.equalsIgnoreCase("VDORCAMENTO")) {
	        	    sSQLSelect = "SELECT CODITORC, CODPROD, PRECOITORC, QTDITORC, PERCDESCITORC, VLRDESCITORC FROM VDITORCAMENTO " +
	 	    	   		   		  "WHERE CODEMP=? AND CODFILIAL=? AND CODORC=?";
	        	    sSQLUpdate = "UPDATE VDITORCAMENTO SET PRECOITORC=?, VLRPRODITORC=?, VLRLIQITORC=?, VLRDESCITORC=? " +
	    			 			 "WHERE CODEMP=? AND CODFILIAL=? AND CODORC=? AND CODITORC=?";
        	    }        	    
        	    
        	    // busca as informações do item.
        	    ps = con.prepareStatement(sSQLSelect);
        	    ps.setInt(1,iParans[13]);//código da empresa
        	    ps.setInt(2,iParans[14]);//código da filial
        	    ps.setInt(3,iParans[12]);//código da PK
        	    rs = ps.executeQuery();

        	    while(rs.next()) {
        	        vCodItem.addElement(new Integer(rs.getInt(1)));
        	        vCodProd.addElement(new Integer(rs.getInt(2)));
        	        vPrecoProd.addElement(new BigDecimal(rs.getFloat(3)));
        	        vQtdItem.addElement(new BigDecimal(rs.getFloat(4)));
        	        vPercDescProd.addElement(new BigDecimal(rs.getFloat(5)));
        	        vDescProd.addElement(new BigDecimal(rs.getFloat(6)));
        	    }        
        	    rs.close();
        	    ps.close();
        	    
        	    // percorre todos os itens
        	    for(int i=0; i<vCodItem.size(); i++) {
        	        iParans[0] = ((Integer)vCodProd.elementAt(i)).intValue();

        	        bdQtdItem = (BigDecimal)vQtdItem.elementAt(i);
        	        bdBuscaPreco = buscaPreco(iParans);
        	        bdPrecoProd = ((BigDecimal)vPrecoProd.elementAt(i)).setScale(Aplicativo.casasDecFin, BigDecimal.ROUND_HALF_UP);
        	        
        	        // se o preço for diferente altera.
        	        if(bdPrecoProd.floatValue()!=bdBuscaPreco.floatValue()) {

        	        	bdPercDescProd = ((BigDecimal)vPercDescProd.elementAt(i)).setScale(Aplicativo.casasDecFin, BigDecimal.ROUND_HALF_UP);        	        	
        	        	bdVlrBrutoProd = calcVlrProd(bdBuscaPreco,bdQtdItem);

        	        	if(bdPercDescProd.floatValue()>0)
        	        		bdDescProd = (bdVlrBrutoProd.multiply(bdPercDescProd).divide(new BigDecimal(100),Aplicativo.casasDecFin,BigDecimal.ROUND_HALF_UP));
        	        	else
        	        		bdDescProd = ((BigDecimal)vDescProd.elementAt(i)).setScale(Aplicativo.casasDecFin, BigDecimal.ROUND_HALF_UP);

            	        bdVlrLiqProd = calcVlrTotalProd(bdVlrBrutoProd,bdDescProd);
        	            
	        	        ps2 = con.prepareStatement(sSQLUpdate);
	        	        ps2.setBigDecimal(1,bdBuscaPreco);//preço do produto
	        	        ps2.setBigDecimal(2,bdVlrBrutoProd);//valor do produto
	        	        ps2.setBigDecimal(3,bdVlrLiqProd);//valor liquido do produto
	        	        ps2.setBigDecimal(4,bdDescProd);//valor do desconto do produto
	            	    ps2.setInt(5,iParans[13]);//código da empresa
	            	    ps2.setInt(6,iParans[14]);//código da filial
	            	    ps2.setInt(7,iParans[12]);//código da PK
	            	    ps2.setInt(8,((Integer)vCodItem.elementAt(i)).intValue());//código do item
	            	    ps2.executeUpdate();
        	        }
        	    }        	    
        	    
        	    calcVlrItem(null,false);
        	    
        	    if (!con.getAutoCommit())
        	        con.commit();

        	}
        	catch (SQLException err) {
        		Funcoes.mensagemErro(this,"Erro ao carregar o preço!\n"+err.getMessage(),true,con,err);
        		err.printStackTrace();
        	}
        	catch (Exception err) {
        		err.printStackTrace();
        	}
        	finally {
        	    sSQLSelect = null;
                sSQLUpdate = null;
            	ps = null;
            	ps2 = null;
            	rs = null;
            	vCodItem = null;
            	vQtdItem = null;
            	vDescProd = null;
            	vPercDescProd = null;
            	vCodProd = null;
            	vPrecoProd = null;
            	bdBuscaPreco = null;
            	bdPrecoProd = null;
            	bdVlrBrutoProd = null;
            	bdVlrLiqProd = null;
            	bdQtdItem = null;
            	bdDescProd = null;
            	bdPercDescProd = null;
            	iParans = null;
        	}
        }        
        else {
            setParansPreco(buscaPreco(getParansPreco()));
        }
    }    
    /**
     * Busca o preço do produto com a procedure VDBUSCAPRECOSP.
     * @param parametros array de int com os paramentros para a procedure 
     * @return preço do produto
     */
    private BigDecimal buscaPreco(int[] parametros) {
        BigDecimal retorno = null;
    	String sSQL = "SELECT PRECO FROM VDBUSCAPRECOSP(?,?,?,?,?,?,?,?,?,?,?,?)";
    	PreparedStatement ps = null;
    	ResultSet rs = null;
    	int[] iParans;
    	try {
    	  ps = con.prepareStatement(sSQL);
    	  
    	  iParans = parametros;
    	  
    	  ps.setInt(1,iParans[0]);//código do produto
    	  ps.setInt(2,iParans[1]);//código do cliente
    	  ps.setInt(3,iParans[2]);//código da empresa do cliente
    	  ps.setInt(4,iParans[3]);//código da filial do cliente
    	  ps.setInt(5,iParans[4]);//código do plano de pagamento
    	  ps.setInt(6,iParans[5]);//código da empresa do plano de pagamento
    	  ps.setInt(7,iParans[6]);//código da filial do plano de pagamento
    	  ps.setInt(8,iParans[7]);//código do tipo de movimento
    	  ps.setInt(9,iParans[8]);//código da empresa do tipo de movimento
    	  ps.setInt(10,iParans[9]);//código da filial do tipo de movimento
    	  ps.setInt(11,iParans[10]);//código da empresa
    	  ps.setInt(12,iParans[11]);//código da filial
    	  rs = ps.executeQuery();
    	  rs.next();
    	  retorno = rs.getString(1) != null ? (new BigDecimal(rs.getString(1))) : (new BigDecimal("0"));
    	  rs.close();
    	  ps.close();
    	  if (!con.getAutoCommit())
    	      con.commit();

    	}
    	catch (SQLException err) {
    		Funcoes.mensagemErro(this,"Erro ao buscar o preço!\n"+err.getMessage(),true,con,err);
    		err.printStackTrace();
    	}
    	finally {
    	    sSQL = null;
    	    ps = null;
    	    rs = null;
    	}
    	return retorno;
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
					obs = new FObservacao((rs.getString(1) != null ? rs.getString(1) : ""));
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
			err.printStackTrace();
		} finally {
			ps = null;
			rs = null;
		}
    	
    }
	protected boolean testaLucro(Object[] args) {
		boolean bRet = false;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sCampoCusto = null;
		String sVerifProd = null;
		String sSQL = null;
		try {
			sSQL = "SELECT P1.USALIQREL,P1.TIPOPRECOCUSTO,PD.VERIFPROD " 
				 + "FROM SGPREFERE1 P1, EQPRODUTO PD "
				 + "WHERE P1.CODEMP=? AND P1.CODFILIAL=? "
				 + "AND PD.CODEMP=? AND PD.CODFILIAL=? AND PD.CODPROD=?";
			ps = con.prepareStatement(sSQL);
			ps.setInt(1, Aplicativo.iCodEmp);
			ps.setInt(2, ListaCampos.getMasterFilial("SGPREFERE1"));
			ps.setInt(3, Aplicativo.iCodEmp);
			ps.setInt(4, ListaCampos.getMasterFilial("EQPRODUTO"));
			ps.setInt(5, ((Integer)args[0]).intValue());
			
			rs = ps.executeQuery();
			if (rs.next()) {
				if (rs.getString("USALIQREL") == null) {
						Funcoes.mensagemInforma(this,
								"Preencha opção de desconto em preferências!");
				} else {
					if (rs.getString("TIPOPRECOCUSTO").equals("M"))
						sCampoCusto = "NCUSTOMPM";
					else
						sCampoCusto = "NCUSTOPEPS";
				}
				if (rs.getString("VERIFPROD") != null)
					sVerifProd = rs.getString("VERIFPROD");
			}
			
			
			
			sSQL = "SELECT COUNT(*) "
				 + "FROM SGPREFERE1 PF, EQPRODUTO P, EQPRODUTOSP01(?,?,?,?,?,?) C "
				 + "WHERE PF.CODEMP=? AND PF.CODFILIAL=? AND "
				 + "P.CODEMP=? AND P.CODFILIAL=? AND P.CODPROD=? AND "
				 + "(((C." + sCampoCusto + "/100)*(100+PF.PERCPRECOCUSTO)) <= ? "
				 + "OR PERCPRECOCUSTO IS NULL OR TIPOPROD='S')";
			
			ps = con.prepareStatement(sSQL);
			ps.setInt(1, Aplicativo.iCodEmp);
			ps.setInt(2, ListaCampos.getMasterFilial("EQPRODUTO"));
			ps.setInt(3, ((Integer)args[0]).intValue());
			ps.setInt(4, Aplicativo.iCodEmp);
			ps.setInt(5, ListaCampos.getMasterFilial("EQALMOX"));
			ps.setInt(6, ((Integer)args[1]).intValue());
			ps.setInt(7, Aplicativo.iCodEmp);
			ps.setInt(8, ListaCampos.getMasterFilial("SGPREFERE1"));
			ps.setInt(9, Aplicativo.iCodEmp);
			ps.setInt(10, ListaCampos.getMasterFilial("EQPRODUTO"));
			ps.setInt(11, ((Integer)args[0]).intValue());
			ps.setBigDecimal(12, (BigDecimal)args[2]);
			rs = ps.executeQuery();
			if (rs.next())
				if (rs.getInt(1) == 1)
					bRet = true;
			rs.close();
			ps.close();
			
			if (!bRet && sVerifProd.equals("S"))
				bRet = mostraTelaPass(getParansPass());
			
		} catch (SQLException err) {
			Funcoes.mensagemErro(this, "Erro ao testar lucro!\n" + err.getMessage(),true,con,err);
			err.printStackTrace();
		} finally {
			ps = null;
			rs = null;
			sCampoCusto = null;
			sSQL = null;
		}

		return bRet;
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
		BigDecimal bdRetorno = arg0.subtract(arg1).divide(
		        new BigDecimal("1"), Aplicativo.casasDecFin, BigDecimal.ROUND_HALF_UP);
		return bdRetorno;
	}
	/**
	 * Abre uma DLDescontItVenda, seta o desconto do item e a String dos descontos
	 */
	protected void mostraTelaDesconto() {
		
		Vector param = getParansDesconto();
		/* param
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
	 * Monta uma dialog para confirmação de senha do usuario.
	 * A verificação é especifica para o tipo passado no construtor.
	 * @param args parametros para a cosulta.
	 * @return verdadeiro se a confirmado.
	 */
	private boolean mostraTelaPass(String[] args) {
		boolean retorno = false;
		
		FPassword fpw = new FPassword(this,FPassword.BAIXO_CUSTO, args, null, con);
		fpw.execShow();
		if(fpw.OK){
			setLog(fpw.getLog());			
			retorno = true;
		}
		fpw.dispose();
		
		return retorno;
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
	/**
	 * Define a variavel que controla se pode recalcular o preço do item.
	 * @param arg0 true para pode e, false para não pode
	 */
	protected void setReCalcPreco(boolean arg0) {
	    this.recalculaPreco = arg0;
	}
	/**
	 * Retorna a variavel que controla se pode recalcular o preço do item.
	 * @return true se pode e, false se não pode
	 */
	protected boolean podeReCalcPreco() {
	    return recalculaPreco;
	}
    
}

