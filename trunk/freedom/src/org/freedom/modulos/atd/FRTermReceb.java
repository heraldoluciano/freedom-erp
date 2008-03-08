/**
 * @version 05/12/2007 <BR>
 * @author Setpoint Informática Ltda./Reginaldo Garcia Heua<BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.atd <BR>
 * Classe: @(#)FRTermReceb.java <BR>
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
package org.freedom.modulos.atd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import net.sf.jasperreports.engine.JasperPrintManager;

import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FPrinterJob;
import org.freedom.telas.FRelatorio;


public class FRTermReceb extends FRelatorio {

	private static final long serialVersionUID = 1L;
	
	private ListaCampos lcOrc = new ListaCampos(this, "");
	
	private JTextFieldPad txtCodOrc = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	
	private JTextFieldFK txtDtOrc = new JTextFieldFK( JTextFieldPad.TP_DATE, 10, 0 );
	
	private JTextFieldPad txtDtVencOrc = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );
	
	private JTextFieldPad txtCodCli = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	
	private JTextFieldPad txtCodConv = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	
	private JTextFieldPad txtCodPlanoPag = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	

	public FRTermReceb(){
		
		setTitulo( "Termo de Recebimento" );
		setAtribos( 30, 20, 400, 300 );
		
		montaTela();
		montaListaCampos();
		
	}
	
	private void montaTela(){
		
		adic( new JLabelPad("Num.Orc"), 7, 5, 70, 20 );
		adic( txtCodOrc, 7, 25, 70, 20 );
		adic( new JLabelPad("Dt.emissão"), 80, 5, 90, 20 );
		adic( txtDtOrc, 80, 25, 90, 20 );
		//adic( new JLabelPad("Item orç."), 173, 5, 70, 20 );
		//adic(txtCodItOrc, 173, 25, 70, 20 );
		//adic( new JLabelPad("Cod.Prod"), 7, 45, 70, 20 );
		//adic( txtCodProd, 7, 65, 70, 20 );
		//adic( new JLabelPad("Descrição do produto"), 80, 45, 290, 20 );
		//adic(txtDescProd, 80, 65, 290, 20 );
		
	}
	
	private void montaListaCampos(){
		
		lcOrc.add( new GuardaCampo( txtCodOrc, "CodOrc", "Cód.Orç.", ListaCampos.DB_PK, false ) );
		lcOrc.add( new GuardaCampo( txtCodCli, "CodCli", "Cód.cli.", ListaCampos.DB_SI, false ) );
		lcOrc.add( new GuardaCampo( txtCodConv, "CodConv", "Cód.conv.", ListaCampos.DB_SI, false ) );
		lcOrc.add( new GuardaCampo( txtDtOrc, "DtOrc", "Dt.emissão", ListaCampos.DB_SI, false ) );
		lcOrc.add( new GuardaCampo( txtDtVencOrc, "DtVencOrc", "Dt.vencto.", ListaCampos.DB_SI, false ) );
		lcOrc.add( new GuardaCampo( txtCodPlanoPag, "CodPlanoPag", "Cód.p.pag.", ListaCampos.DB_SI, false ) );
		lcOrc.montaSql( false, "ORCAMENTO", "VD" );
//		lcOrc.setQueryCommit( true );
		lcOrc.setReadOnly( true );
		txtCodOrc.setTabelaExterna(lcOrc);
		txtCodOrc.setFK( true );
		txtCodOrc.setNomeCampo( "CODORC" );
		
	}
	@ Override
	public void imprimir( boolean b ) {
		ResultSet rs = null;
		PreparedStatement ps = null;
		StringBuffer sql = new StringBuffer();
		HashMap<String, Object> hParam = new HashMap<String, Object>();
		if (txtCodOrc.getVlrInteger()==0) {
			Funcoes.mensagemInforma( this, "Selecione um orçamento!" );
			return;
		}
		
		
		
		sql.append("SELECT O.CODEMP, O.CODFILIAL, O.CODORC, CV.NOMECONV, CV.RGCONV, "); 
		sql.append("P2.CABTERMR01 , P2.CABTERMR02, P2.RODTERMR, IO.NUMAUTORIZORC, P.DESCPROD, "); 
		sql.append("E.FOTOEMP, O.DTORC, E.CIDEMP ");
		sql.append("FROM VDITORCAMENTO IO, ATCONVENIADO CV, SGEMPRESA E, EQPRODUTO P, VDORCAMENTO O ");
		sql.append("LEFT OUTER JOIN SGPREFERE2 P2 ON ");
		sql.append("P2.CODEMP=O.CODEMP AND P2.CODFILIAL=? ");
		sql.append("WHERE O.CODEMP=? AND O.CODFILIAL=? AND O.CODORC=? AND "); 
		sql.append("IO.CODEMP=O.CODEMP AND IO.CODFILIAL=O.CODFILIAL AND "); 
		sql.append("IO.CODORC=O.CODORC AND IO.SITENTITORC='N' AND ");
		sql.append("IO.SITTERMRITORC='E' AND CV.CODEMP=O.CODEMPCV AND "); 
		sql.append("CV.CODFILIAL=O.CODFILIALCV AND CV.CODCONV=O.CODCONV AND "); 
		sql.append("P.CODEMP=IO.CODEMPPD AND P.CODFILIAL=IO.CODFILIALPD AND P.CODPROD=IO.CODPROD AND "); 
		sql.append("E.CODEMP=P2.CODEMP");		
		
		hParam.put("CODEMP",Aplicativo.iCodEmp);
		hParam.put( "CODORC", txtCodOrc.getVlrInteger() );
		hParam.put( "DATAIMP", Funcoes.getDiaMes( txtDtOrc.getVlrDate() )+" DE "+
				Funcoes.getMesExtenso(txtDtOrc.getVlrDate())+" DE "+
				Funcoes.getAno( txtDtOrc.getVlrDate()) +".");
		
		
		/*sql.append( "SELECT O.CODEMP, O.CODFILIAL, O.CODORC, CV.NOMECONV, CV.RGCONV ");
		sql.append( "FROM VDORCAMENTO O, VDITORCAMENTO IO, ATCONVENIADO CV ");
		sql.append( "WHERE O.CODEMP=? AND O.CODFILIAL=? AND O.CODORC=? AND "); 
		sql.append( "IO.CODEMP=O.CODEMP AND IO.CODFILIAL=O.CODFILIAL AND "); 
		sql.append( "IO.CODORC=O.CODORC AND IO.SITENTITORC='N' AND ");
		sql.append( "IO.SITTERMRITORC='E' AND CV.CODEMP=O.CODEMPCV AND "); 
		sql.append( "CV.CODFILIAL=O.CODFILIALCV AND CV.CODCONV=O.CODCONV" ); */
		
		try {
			ps = con.prepareStatement( sql.toString() );
			ps.setInt( 1, ListaCampos.getMasterFilial( "SGPREFERE2" ));
			ps.setInt( 2, Aplicativo.iCodEmp );
			ps.setInt( 3, ListaCampos.getMasterFilial( "VDORCAMENTO" ));
			ps.setInt( 4, txtCodOrc.getVlrInteger());
			rs = ps.executeQuery();
		} catch (SQLException e) {
			Funcoes.mensagemErro( this, "Ocorreu um erro executando a consulta.\n"+e.getMessage() );
			return;
		}
		
		
		//FPrinterJob dlGr = new FPrinterJob( , , null, rs, param, this, false )
		//FPrinterJob dlGr = new FPrinterJob( "relatorios/FRComprasFor.jasper", "Relatório de Compras por fornecedor", "", rs, param, null );
		FPrinterJob dlGr = new FPrinterJob("relatorios/TermReceb.jasper","TERMO DE RECEBIMENTO","",this,hParam,con);
		if ( b ) {
			dlGr.setVisible( true );
		}
		else {
			try {
				JasperPrintManager.printReport( dlGr.getRelatorio(), true );
			} catch ( Exception err ) {
				Funcoes.mensagemErro( this, "Erro na impressão de relatório de vendas por cliente!" + err.getMessage(), true, con, err );
			}
		}

		
	}
	public void setConexao(Connection cn) {
		
		super.setConexao(cn);
		lcOrc.setConexao( cn );
	}

}
