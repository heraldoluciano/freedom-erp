/**
 * @version 16/03/2006 <BR>
 * @author Setpoint Informática Ltda./Alex Rodrigues <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)DLCodProd.java <BR>
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
 * Tela para busca de produto pelo código de barras
 */

package org.freedom.modulos.std;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.DLF3;

public class DLCodProd extends DLF3 {

	private static final long serialVersionUID = 1L;
	private int iCodProd = 0;
	private boolean bFilCodProd = false;
	private boolean bFilRefProd = false;
	private boolean bFilCodBar = false;
	private boolean bFilCodFab = false;
	
	public DLCodProd(Connection con) {
	   	 		 
		setAtribos( 575, 260);			 
		setConexao(con);
		 
		tab.adicColuna("Cód.prod.");
		tab.adicColuna("Ref.prod.");
		tab.adicColuna("Cód.bar.prod.");
		tab.adicColuna("Cód.fab.prod.");
		tab.adicColuna("Descrição do produto");    
		tab.adicColuna("lote");
		tab.adicColuna("Validade");   	  
		tab.adicColuna("Saldo");   	
		tab.adicColuna("Cód.amox.");
		tab.setTamColuna(80,0);
		tab.setTamColuna(80,1);
		tab.setTamColuna(80,2);
		tab.setTamColuna(80,3);
		tab.setTamColuna(160,4);
		tab.setTamColuna(80,5); 
		tab.setTamColuna(80,6);
		tab.setTamColuna(80,7);
		tab.setTamColuna(80,8); 
		
		setTitulo("Saldo do produto nos almoxarifados");
		tab.addKeyListener(this);
		
		getPrefere();
		
	}
	
	public boolean buscaCodProd(String valor) {

		PreparedStatement ps = null;
		ResultSet rs = null;
		String sSQL = null;
		String sWhere = "";
		boolean usaOR = false;
		boolean adicCodProd = false;
		
		if(valor == null || valor.trim().length() <= 0)
			return false;		
		
		try{
			
			tab.limpa();
			iCodProd = 0;
			
			if(bFilCodProd) {
				try{
					int val = Integer.parseInt(valor);
					if(val < Integer.MAX_VALUE && val < Integer.MIN_VALUE ) {
						sWhere = "AND P.CODPROD=? ";
						adicCodProd = true;
					}
				} catch (NumberFormatException e) {
					System.out.println(e.getMessage());
				}
			}
			if(bFilRefProd)
				if(sWhere.length()>0) {
					sWhere += " OR (P.REFPROD=?) ";
					usaOR = true;
				}
				else
					sWhere = "AND (P.REFPROD=?) ";
			if(bFilCodBar)
				if(sWhere.length()>0) {
					sWhere += " OR (P.CODBARPROD=?) ";
					usaOR = true;
				}
				else
					sWhere = "AND (P.CODBARPROD=?) ";
			if(bFilCodFab)
				if(sWhere.length()>0) {
					sWhere += " OR (P.CODFABPROD=?) ";
					usaOR = true;
				}
				else
					sWhere = "AND (P.CODFABPROD=?) ";
			if(usaOR)
				sWhere = " AND (" + sWhere.substring(4,sWhere.length()) +") ";
			
					
				
			sSQL =  "SELECT P.CODPROD, P.REFPROD, P.CODBARPROD, P.CODFABPROD, P.DESCPROD, " +
					"L.CODLOTE, L.VENCTOLOTE, L.SLDLOTE, A.CODALMOX " +
					"FROM EQPRODUTO P, EQLOTE L, EQALMOX A " +
					"WHERE P.CODEMP=? AND P.CODFILIAL=? " +
					sWhere +
					"AND L.CODEMP=P.CODEMP AND L.CODFILIAL=P.CODFILIAL AND L.CODPROD=P.CODPROD " +
					"AND A.CODEMP=P.CODEMPAX AND A.CODFILIAL=P.CODFILIALAX AND A.CODALMOX=P.CODALMOX " +
					"AND L.VENCTOLOTE = ( SELECT MIN(VENCTOLOTE) " +
					                     "FROM EQLOTE LS " +
					                     "WHERE LS.CODPROD=L.CODPROD AND LS.CODFILIAL=L.CODFILIAL " +
					                     "AND LS.CODEMP=L.CODEMP AND LS.SLDLIQLOTE>0 " +
					                     "AND VENCTOLOTE >= CAST('today' AS DATE) ) ";
			
			ps = con.prepareStatement(sSQL);
			int iparam = 1;
			ps.setInt(iparam++, Aplicativo.iCodEmp);
			ps.setInt(iparam++, ListaCampos.getMasterFilial("EQPRODUTO"));
			if(adicCodProd)
				ps.setString(iparam++, valor);
			if(bFilRefProd)
				ps.setString(iparam++, valor);
			if(bFilCodBar)
				ps.setString(iparam++, valor);
			if(bFilCodFab)
				ps.setString(iparam++, valor);
			
			rs = ps.executeQuery();
			
			int ilinha = 0;
			while(rs.next()) {
				tab.adicLinha( new Object[]{(rs.getString("CODPROD") != null ? rs.getString("CODPROD") : ""),
											(rs.getString("REFPROD") != null ? rs.getString("REFPROD") : ""),
											(rs.getString("CODBARPROD") != null ? rs.getString("CODBARPROD") : ""),
											(rs.getString("CODFABPROD") != null ? rs.getString("CODFABPROD") : ""),
											(rs.getString("DESCPROD") != null ? rs.getString("DESCPROD") : ""),
											(rs.getString("CODLOTE") != null ? rs.getString("CODLOTE") : ""),
											(rs.getString("VENCTOLOTE") != null ? rs.getString("VENCTOLOTE") : ""),
											(rs.getString("SLDLOTE") != null ? rs.getString("SLDLOTE") : ""),
											(rs.getString("CODALMOX") != null ? rs.getString("CODALMOX") : "")});
				ilinha++;
			}
			
			if(ilinha <= 0) {
				Funcoes.mensagemErro(this, "Código Invalido!");
				return false;
			}
			else if(ilinha == 1)
				iCodProd = Integer.parseInt((String)tab.getValor(0,0));
			else {
				tab.setLinhaSel(1);
				setVisible(true);
				iCodProd = Integer.parseInt((String)tab.getValor(tab.getLinhaSel(),0));
			}
			
		} catch (SQLException e) {
			Funcoes.mensagemErro(this, "Erro ao buscar produtos por código de barras!\n"+
					e.getMessage(), true, con, e);
			e.printStackTrace();
			return false;
		} finally {
			ps = null;
			rs = null;
			sSQL = null;			
		}
		
		return true;
		
	}

	public int getCodProd() {
		return iCodProd;
	}
	
	private void getPrefere() {
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sSQL = null;
		
		try{
			
			sSQL =  "SELECT FILBUSCGENPROD, FILBUSCGENREF, FILBUSCGENCODBAR, FILBUSCGENCODFAB " +
					"FROM SGPREFERE1 " +
					"WHERE CODEMP=? AND CODFILIAL=?";
			
			ps = con.prepareStatement(sSQL);
			ps.setInt(1, Aplicativo.iCodEmp);
			ps.setInt(2, ListaCampos.getMasterFilial("SGPREFERE1"));
			
			rs = ps.executeQuery();
			
			while(rs.next()) {
				bFilCodProd = (rs.getString(1)!=null && rs.getString(1).equals("S")) ? true : false;
				bFilRefProd = (rs.getString(2)!=null && rs.getString(2).equals("S")) ? true : false;
				bFilCodBar  = (rs.getString(3)!=null && rs.getString(3).equals("S")) ? true : false;
				bFilCodFab  = (rs.getString(4)!=null && rs.getString(4).equals("S")) ? true : false;
			}
			
		} catch (SQLException e) {
			Funcoes.mensagemErro(this, "Erro ao buscar filtros!\n"+
					e.getMessage(), true, con, e);
			e.printStackTrace();
		} finally {
			ps = null;
			rs = null;
			sSQL = null;			
		}
		
	}
	/*
    public void ok() {
    	iCodProd = null;
    	if (tab.getNumLinhas() > 0 && tab.getLinhaSel() >= 0) {
    		iCodProd = new Integer((String)tab.getValor(tab.getLinhaSel(),0));
    		super.ok();
    	} else {
    		Funcoes.mensagemInforma(this, "Nenhum produto foi selecionado.");
    		iCodProd = null;
    	}
    }*/
	
}        
