/**
 * @version 19/11/2009 <BR>
 * @author Setpoint Informática Ltda./Anderson Sanchez<BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)FRGiroEstoque.java <BR>
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
 * Classe para filtros e carregamento de relatório de contagem de estoque.
 * 
 */

package org.freedom.modulos.std;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.BorderFactory;

import net.sf.jasperreports.engine.JasperPrintManager;

import org.freedom.funcoes.Funcoes;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.GuardaCampo;
import org.freedom.library.JCheckBoxPad;
import org.freedom.library.JLabelPad;
import org.freedom.library.JRadioGroup;
import org.freedom.library.JTextFieldFK;
import org.freedom.library.JTextFieldPad;
import org.freedom.library.ListaCampos;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FPrinterJob;
import org.freedom.telas.FRelatorio;

public class FRContaEstoque extends FRelatorio {
	private static final long serialVersionUID = 1L;

	private JTextFieldPad txtDataini = new JTextFieldPad(JTextFieldPad.TP_DATE,10,0);
	private JTextFieldPad txtCodGrup = new JTextFieldPad(JTextFieldPad.TP_STRING,14,0);
	private JTextFieldPad txtDescGrup = new JTextFieldFK(JTextFieldPad.TP_STRING,40,0);
	
	private JTextFieldPad txtCodTabPreco = new JTextFieldPad (JTextFieldPad.TP_INTEGER,6,0);
	private JTextFieldPad txtDescTabPreco = new JTextFieldFK (JTextFieldPad.TP_STRING,40,0);
		
	private JLabelPad lbCodGrup = new JLabelPad("Cód.grupo");
	private JLabelPad lbDescGrup = new JLabelPad("Descrição do grupo");
	
	private JLabelPad lbCodTabPreco = new JLabelPad("Cód.Tab.");
	private JLabelPad lbDescTabPreco = new JLabelPad("Descrição da tabela de preços");
	
	private JLabelPad lbCodPlanoPag1 = new JLabelPad("Cód.Pag.");
	private JLabelPad lbDescPlanoPag1 = new JLabelPad("Descrição do Plano de pagamento");
	
	private JTextFieldPad txtCodPlanoPag1 = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
	private JTextFieldPad txtDescPlanoPag1 = new JTextFieldFK(JTextFieldPad.TP_STRING,40,0);
	
	private JRadioGroup<?, ?> rgOrdem = null;
	private Vector<String> vLabs = new Vector<String>(2);
	private Vector<String> vVals = new Vector<String>(2);
	
	private ListaCampos lcGrup = new ListaCampos(this);
	private ListaCampos lcTabPreco = new ListaCampos(this);
	private ListaCampos lcPlanoPag1 = new ListaCampos(this);
	
	private JCheckBoxPad cbSemEstoq = new JCheckBoxPad("Imprimir produtos sem saldo?","S","N");
	private JCheckBoxPad cbComSaldo = new JCheckBoxPad("Imprimir saldo dos produtos?","S","N");	
	
    private JCheckBoxPad cbPrecoFracionado = new JCheckBoxPad("Preço fracionado","S","N");
	
	
	public FRContaEstoque() {
		
		setTitulo("Relatório de Giro de estoque");
		
		setAtribos( 140, 40, 350, 390 );
		vLabs.addElement("Código");
		vLabs.addElement("Descrição"); 

		vVals.addElement("C");
		vVals.addElement("D");

		rgOrdem = new JRadioGroup<String, String>(1,2,vLabs,vVals);
		rgOrdem.setVlrString("D");
        
		JLabelPad lbLinha = new JLabelPad();
		lbLinha.setBorder( BorderFactory.createTitledBorder( "Posição do dia:") );
		
		adic( lbLinha, 7, 0, 300, 65 );
		adic( txtDataini, 17, 25, 75, 20 );

		txtDataini.setVlrDate( new Date() );

		lcGrup.add(new GuardaCampo( txtCodGrup, "CodGrup", "Cód.grupo", ListaCampos.DB_PK, false));
		lcGrup.add(new GuardaCampo( txtDescGrup, "DescGrup", "Descrição do grupo", ListaCampos.DB_SI, false));
		lcGrup.montaSql(false, "GRUPO", "EQ");
		lcGrup.setReadOnly(true);
		txtCodGrup.setTabelaExterna(lcGrup);
		txtCodGrup.setFK(true);
		txtCodGrup.setNomeCampo("CodGrup");
		
		lcTabPreco.add(new GuardaCampo( txtCodTabPreco, "CodTab", "Cód.tab.pc.", ListaCampos.DB_PK, false));
		lcTabPreco.add(new GuardaCampo( txtDescTabPreco, "DescTab", "Descrição da tabela de preço", ListaCampos.DB_SI, false));
		txtCodTabPreco.setTabelaExterna(lcTabPreco);
		txtCodTabPreco.setNomeCampo("CodTab");
		txtCodTabPreco.setFK(true);
		lcTabPreco.setReadOnly(true);
		lcTabPreco.montaSql(false, "TABPRECO", "VD");
		
		lcPlanoPag1.add(new GuardaCampo( txtCodPlanoPag1, "CodPlanoPag", "Cód.p.pag.", ListaCampos.DB_PK, false));
		lcPlanoPag1.add(new GuardaCampo( txtDescPlanoPag1, "DescPlanoPag", "Descrição do plano de pagamento", ListaCampos.DB_SI, false));
		lcPlanoPag1.montaSql(false, "PLANOPAG", "FN");
		lcPlanoPag1.setReadOnly(true);
		txtCodPlanoPag1.setTabelaExterna(lcPlanoPag1);
		txtCodPlanoPag1.setFK(true);
		txtCodPlanoPag1.setNomeCampo("CodPlanoPag");

    
		adic(lbCodGrup,7,70,250,20);
		adic(txtCodGrup,7,90,80,20);	
		
		adic(lbDescGrup,90,70,250,20);
		adic(txtDescGrup,90,90,216,20);
		
		adic(lbCodTabPreco,7,110,250,20);
		adic(txtCodTabPreco,7,130,80,20);	
		
		adic(lbDescTabPreco,90,110,250,20);
		adic(txtDescTabPreco,90,130,216,20);
		
		adic(lbCodPlanoPag1,7,150,250,20);
		adic(txtCodPlanoPag1,7,170,80,20);	
		
		adic(lbDescPlanoPag1,90,150,250,20);
		adic(txtDescPlanoPag1,90,170,216,20);

		adic(rgOrdem,7,210,300,30);   
		
	    adic(cbSemEstoq,7,250,250,20);
	    adic(cbComSaldo,7,270,250,20);
	    adic(cbPrecoFracionado,7,290,250,20);
	    
	    cbSemEstoq.setVlrString("N");
	    cbComSaldo.setVlrString("S");
	    cbPrecoFracionado.setVlrString("S");
	    
		txtCodTabPreco.setVlrInteger( 1 );
		txtCodPlanoPag1.setVlrInteger( 1 );
		
	}

	public void imprimir( boolean bVisualizar ) {

		StringBuilder sql = new StringBuilder();
		StringBuilder status = new StringBuilder();
		StringBuilder filtros = new StringBuilder();
		
		try {

			
			
			sql.append( "select cu.codprod, cu.codfabprod, cu.refprod, cu.codbarprod, cu.descprod, cu.sldprod ");
			
			if(txtCodTabPreco.getVlrInteger()>0) {
				
				if("S".equals( cbPrecoFracionado.getVlrString())){
	    			sql.append( " ,PP.PRECOPROD/coalesce(P.QTDEMBALAGEM,1) AS PRECOPROD " );
	    		}
	    		else {
	    			sql.append( " ,PP.PRECOPROD " );
	    		}
				
				sql.append( ", 'S' imppreco ");
				
				sql.append( "from eqrelpepssp(?,?,?,null,null,null,?,?,?,null,null,null,null) cu, vdprecoprod pp, eqproduto p ");
				sql.append( "where cu.ativoprod='S' and pp.codemp=? and pp.codfilial=? and pp.codprod=cu.codprod and " );
				
				sql.append( "pp.codemptb=? and pp.codfilialtb=? and pp.codtab=? and ");
				
				if(txtCodPlanoPag1.getVlrInteger()>0) {
					sql.append( "pp.codemppg=? and pp.codfilialpg=? and pp.codplanopag=? and ");
				}
				
				sql.append( "p.codemp=pp.codemp and p.codfilial=pp.codfilial and p.codprod=pp.codprod ");

			}
			else {
				sql.append( ",0 as precoprod, 'N' imppreco ");
				sql.append( "from eqrelpepssp(?,?,?,null,null,null,?,?,?,null,null,null,null) cu ");
				sql.append( "where cu.ativoprod='S' ");
			}
			
			
			if("N".equals( cbSemEstoq.getVlrString() )) {
				sql.append(" and cu.sldprod>0" );
			}
						
			if("C".equals(rgOrdem.getVlrString())) {
				sql.append("order by cu.codprod ");
			}
			
			if("D".equals(rgOrdem.getVlrString())) {
				sql.append("order by cu.descprod ");
			}
			
			PreparedStatement ps = con.prepareStatement( sql.toString() );
			
			int iparam = 1;
			
			ps.setInt( iparam++, Aplicativo.iCodEmp );
			ps.setInt( iparam++, Aplicativo.iCodFilial );
			ps.setDate( iparam++, Funcoes.dateToSQLDate( txtDataini.getVlrDate() ) );

			if(txtCodGrup.getVlrInteger()>0) {
				ps.setInt( iparam++, lcGrup.getCodEmp() );
				ps.setInt( iparam++, lcGrup.getCodFilial() );
				ps.setString( iparam++, txtCodGrup.getVlrString() );
			}
			else {
				ps.setNull(iparam++,Types.INTEGER);			
	  			ps.setNull(iparam++,Types.SMALLINT);
	  			ps.setNull(iparam++,Types.CHAR);				
			}
			
			if(txtCodTabPreco.getVlrInteger()>0) {
				
				ps.setInt( iparam++, Aplicativo.iCodEmp );
				ps.setInt( iparam++, Aplicativo.iCodFilial );
				
				ps.setInt( iparam++, lcTabPreco.getCodEmp() );
				ps.setInt( iparam++, lcTabPreco.getCodFilial() );
				ps.setInt( iparam++, txtCodTabPreco.getVlrInteger() );
				
				if(txtCodPlanoPag1.getVlrInteger()>0) {
					ps.setInt( iparam++, lcPlanoPag1.getCodEmp() );
					ps.setInt( iparam++, lcPlanoPag1.getCodFilial() );
					ps.setInt( iparam++, txtCodPlanoPag1.getVlrInteger() );
				}
			}
			

			ResultSet rs = ps.executeQuery();

			HashMap<String, Object> hParam = new HashMap<String, Object>();
			hParam.put( "CODEMP", Aplicativo.iCodEmp );
			hParam.put( "CODFILIAL", Aplicativo.iCodFilial );
			hParam.put( "DATA", txtDataini.getVlrDate() ); 
			hParam.put( "SUBREPORT_DIR", "org/freedom/layout/rel"); 
			hParam.put( "COM_SALDO",cbComSaldo.getVlrString() );

			FPrinterJob dlGr = new FPrinterJob( "layout/rel/REL_CONTA_ESTOQUE.jasper", "Relatório de Contagem de estoque", "", rs, hParam, this );

			if ( bVisualizar ) {
				dlGr.setVisible( true );
			}
			else {
				JasperPrintManager.printReport( dlGr.getRelatorio(), true );
			}

		} catch ( Exception err ) {
			Funcoes.mensagemErro( this, "Erro consultar giro do estoque!\n" + err.getMessage(), true, con, err );
			err.printStackTrace();
		}
	}

	public void setConexao( DbConnection cn ) {		
		
		super.setConexao( cn );
		
		lcGrup.setConexao( cn );
		lcTabPreco.setConexao( cn );
		lcPlanoPag1.setConexao( cn );
		
		lcPlanoPag1.carregaDados();
		lcTabPreco.carregaDados();

		
	}
	
}
