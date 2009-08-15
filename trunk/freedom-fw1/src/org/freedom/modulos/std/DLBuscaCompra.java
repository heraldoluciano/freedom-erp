/**
 * @version 15/08/2009 <BR>
 * @author Setpoint Informática Ltda./Anderson Sanchez <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)DLBuscaCompra.java <BR>
 * 
 * Este arquivo é parte do sistema Freedom-ERP, o Freedom-ERP é um software livre; você pode redistribui-lo e/ou <BR>
 * modifica-lo dentro dos termos da Licença Pública Geral GNU como publicada pela Fundação do Software Livre (FSF); <BR>
 * na versão 2 da Licença, ou (na sua opnião) qualquer versão. <BR>
 * Este programa é distribuido na esperança que possa ser  util, mas SEM NENHUMA GARANTIA; <BR>
 * sem uma garantia implicita de ADEQUAÇÂO a qualquer MERCADO ou APLICAÇÃO EM PARTICULAR. <BR>
 * Veja a Licença Pública Geral GNU para maiores detalhes. <BR>
 * Você deve ter recebido uma cópia da Licença Pública Geral GNU junto com este programa, se não, <BR>
 * escreva para a Fundação do Software Livre(FSF) Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA <BR> <BR>
 *
 * Tela para busca de compras para referenciar à devoluções.
 */

package org.freedom.modulos.std;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JScrollPane;

import org.freedom.acao.TabelaSelEvent;
import org.freedom.acao.TabelaSelListener;
import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.Tabela;
import org.freedom.funcoes.Funcoes;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.telas.FFDialogo;

public class DLBuscaCompra extends FFDialogo implements TabelaSelListener {
    public Tabela tab = new Tabela();
	private JScrollPane spnCentro = new JScrollPane(tab); 
	private static final long serialVersionUID = 1L;   
	private Integer coditcompra = new Integer(0);
	private Integer codcompra = new Integer(0);
	private Integer codvenda = new Integer(0);	
	private Integer codfor = null;
	private Integer codempfr = null;
	private Integer codfilialfr = null;
	boolean bRet = false;
	public int iPadrao = 0;
	private ListaCampos lcItens = null; 
	private ListaCampos lcProd = null;
	
	public DLBuscaCompra(ListaCampos lcItens, ListaCampos lcProd, Integer codvenda, DbConnection con ) {
		setTitulo("Pesquisa auxiliar");
		setAtribos( 550, 260);
		setResizable(true);
		    
		setConexao( con );
		
		this.lcItens = lcItens;	
		this.lcProd = lcProd;
		this.codvenda = codvenda;

		setAtribos( 575, 260);
			
		c.add( spnCentro, BorderLayout.CENTER);
		
		tab.adicColuna("Doc");
		tab.adicColuna("Data");    
		tab.adicColuna("Cód.Cp.");
		tab.adicColuna("Cód.It.Cp.");
		tab.adicColuna("Qtd.");   	  
		tab.adicColuna("Preço");   	
		tab.adicColuna("Qtd.Dev.");
		tab.setTamColuna(60,0);
		tab.setTamColuna(75,1);
		tab.setTamColuna(75,1);
		tab.setTamColuna(50,3);
		tab.setTamColuna(70,4);
		tab.setTamColuna(70,5);   	 	 
		tab.setTamColuna(70,6);
//		tab.addTabelaSelListener(this); 
		
		setTitulo("Itens de compra");
//		tab.addKeyListener(this);
		
		buscaForneced();
		buscaCompras();
		
	}
	
	public int getLinhaPadrao(){
		return iPadrao;
	}
	
	public HashMap<String,Integer> getValor() {
		HashMap<String, Integer> ret = new HashMap<String, Integer>();
		try {
			ret.put("codcompra", codcompra );
			ret.put("coditcompra", coditcompra );		
		}
		catch (Exception e) {
			Funcoes.mensagemErro( null, "Erro ao selecionar ítem de compra!");
		}
		finally {
			bRet = false;
			codcompra = null;
			coditcompra = null;
		}
		return ret;
	}
	
//	public void setValor(Object oVal) {}
	
	public boolean buscaCompras() {
		PreparedStatement ps = null;
		ResultSet rs = null;
		bRet = false;
		StringBuilder sql = new StringBuilder();
		     
		sql.append( "select cp.doccompra, cp.dtemitcompra, ic.codcompra, ic.coditcompra, ic.qtditcompra, ic.precoitcompra, ");
		sql.append( "coalesce((select sum(dv.qtddev) from cpdevolucao dv where dv.codemp=ic.codemp "); 
		sql.append( "and dv.codfilial=ic.codfilial and dv.codcompra=ic.codcompra and dv.coditcompra=ic.coditcompra),0) qtddev ");
		sql.append( "from ");
		sql.append( "cpitcompra ic ");
		sql.append( "left outer join cpcompra cp on ");
		sql.append( "cp.codemp=ic.codemp and cp.codfilial=ic.codfilial and cp.codcompra=ic.codcompra ");
		sql.append( "where ");
		sql.append( "cp.codempfr=? and cp.codfilialfr=? and cp.codfor=? and ");
		sql.append( "ic.codemppd=? and ic.codfilialpd=? and ic.codprod=? ");
 
		try {
			ps = con.prepareStatement(sql.toString());
			
			ps.setInt(1, codempfr );
			ps.setInt(2, codfilialfr );
			ps.setInt(3, codfor.intValue() );
			ps.setInt(4, lcProd.getCodEmp() );
			ps.setInt(5, lcProd.getCodFilial() );
			ps.setInt(6, (lcProd.getCampo("codprod").getVlrInteger()).intValue() );
						
			tab.limpa();
			
			rs = ps.executeQuery();
			
			int irow = 0;
			int icol = 0;
			
			while (rs.next()) {
			
				tab.adicLinha();
				tab.setValor(rs.getString("doccompra") != null ? rs.getString("doccompra") : "", irow , icol++);
				tab.setValor(rs.getString("dtemitcompra") != null ? rs.getString("dtemitcompra").trim() : "", irow , icol++);
				tab.setValor(rs.getString("codcompra") != null ? rs.getString("codcompra") : "", irow , icol++);
				tab.setValor(rs.getString("coditcompra") != null ? rs.getString("coditcompra") : "", irow , icol++);
				tab.setValor(rs.getString("qtditcompra") != null ? Funcoes.strDecimalToStrCurrency(13,2,rs.getString("qtditcompra")) : "", irow , icol++);
				tab.setValor(rs.getString("precoitcompra") != null ? Funcoes.strDecimalToStrCurrency(13,2,rs.getString("precoitcompra")) : "", irow , icol++);
				tab.setValor(rs.getString("qtddev") != null ? Funcoes.strDecimalToStrCurrency(13,2,rs.getString("qtddev")) : "", irow , icol++);
				
				irow ++;
				icol = 0; 
			}
			 	      	
			rs.close();
			ps.close();
			con.commit();
			/*
			if((bRet) && (lcItens.getStatus() == 2)) {
				tab.requestFocus();
				tab.setLinhaSel(iPadrao); 
				setVisible(true);      		
			}			 
			*/
		} 
		catch (SQLException err) {
			Funcoes.mensagemErro(this,"Erro ao buscar filiais almoxarifados e saldos!\n"+err.getMessage(),true,con,err);
			err.printStackTrace();
		} 
		finally {
			ps = null;
			rs = null;

		}
		return bRet;
	}
	   
	public void actionPerformed(ActionEvent evt) {
		getValores();
		super.actionPerformed(evt);
	}
	
	public synchronized void valorAlterado(TabelaSelEvent tsevt) {       
		try {   	
			if (tsevt.getTabela() == tab)
				getValores();
		} catch(Exception e) {
			e.printStackTrace();
		}	
	}
	
	public void keyPressed(KeyEvent kevt) {
		if ( kevt.getSource() == tab && kevt.getKeyCode() == KeyEvent.VK_ENTER) {        
			if (tab.getNumLinhas() > 0 && btOK.isEnabled()) 
				btOK.doClick();
			else if (!btOK.isEnabled()) {
				if (tab.getLinhaSel()==tab.getNumLinhas()-1)
					tab.setLinhaSel(tab.getNumLinhas()-2);
				else
					tab.setLinhaSel(tab.getLinhaSel()-1);
			}
		} else if (kevt.getKeyCode() == KeyEvent.VK_ESCAPE)
			btCancel.doClick();
	}    
	
	public void ok(){		
		codcompra =  new Integer(Integer.parseInt(tab.getValueAt(tab.getLinhaSel(),2).toString()));
		coditcompra = new Integer(Integer.parseInt(tab.getValueAt(tab.getLinhaSel(),3).toString()));
		super.ok();
	}
	
	public void getValores(){
		if (tab.getNumLinhas() > 0) {
			if (bRet) {			
				if(this.isVisible()){
					if (((ImageIcon)tab.getValueAt(tab.getLinhaSel(),5)).getDescription().equals("N")) 
						btOK.setEnabled(false);
					else	
						btOK.setEnabled(true);
				}
			}
		}    
	}
	
	private void buscaForneced() {
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuilder sql = new StringBuilder();
	     
		sql.append( "select cf.codempfr, cf.codfilialfr, cf.codfor " );
		sql.append( "from vdvenda vd left outer join eqclifor cf on " );
		sql.append( "cf.codemp=vd.codempcl and cf.codfilial=vd.codfilialcl and cf.codcli=cf.codcli " );
		sql.append( "where vd.codemp=? and vd.codfilial=? and vd.codvenda=? and vd.tipovenda='V' "); 
 
		try {
			ps = con.prepareStatement(sql.toString());
			
			ps.setInt(1, lcItens.getCodEmp());
			ps.setInt(2, lcItens.getCodFilial());
			ps.setInt(3, codvenda.intValue() );
			
			rs = ps.executeQuery();
			
			if (rs.next()) {
				codempfr = rs.getInt("codempfr");
				codfilialfr = rs.getInt("codfilialfr");
				codfor = rs.getInt("codfor");
			}
			else {
				Funcoes.mensagemInforma( null, "Não existe fornecedore vinculado ao cliente.");
			}
			  	      	
			rs.close();
			ps.close();
			con.commit();
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

}        
