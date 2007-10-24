/**
 * @version 24/10/2007 <BR>
 * @author Setpoint Informática Ltda./Reginaldo Garcia Heua <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)FRRazFor.java <BR>
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

package org.freedom.modulos.std;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.HashMap;

import javax.swing.BorderFactory;

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


public class FRRazFor extends FRelatorio {
	
	private static final long serialVersionUID = 1L;
	
	private JTextFieldPad txtDataini = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtDatafim = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtCodFor = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 10, 0 );

	private JTextFieldFK txtDescFor = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );
	
	private ListaCampos lcFor = new ListaCampos(this);
	
	public FRRazFor(){
		
		setTitulo( "Compras por Razão" );
		setAtribos( 50, 50, 340, 195 );
		
		montaTela();
		montaListaCampos();
	}

	private void montaTela(){
	
		JLabelPad lbLinha = new JLabelPad();
		lbLinha.setBorder(BorderFactory.createEtchedBorder());
		JLabelPad lbPeriodo = new JLabelPad("	     Periodo:");
		lbPeriodo.setOpaque(true);
		
		adic(lbPeriodo,7, 1, 80, 20 );
		adic(lbLinha,5,10,300,45);
		
		adic( new JLabelPad( "De:" ), 10, 25, 30, 20 );
		adic( txtDataini, 40, 25, 97, 20 );
		adic( new JLabelPad( "Até:" ), 140, 25, 37, 20 );
		adic( txtDatafim, 180, 25, 100, 20 );
		adic( new JLabelPad("Cód.For"), 7, 60, 100, 20 );
		adic( txtCodFor, 7, 80, 80, 20 );
		adic( new JLabelPad("Descrição do fornecedor"), 93, 60, 180, 20 );
		adic( txtDescFor, 93, 80, 210, 20 );
		
		Calendar cPeriodo = Calendar.getInstance();
		txtDatafim.setVlrDate( cPeriodo.getTime() );
		cPeriodo.set( Calendar.DAY_OF_MONTH, cPeriodo.get( Calendar.DAY_OF_MONTH ) - 30 );
		txtDataini.setVlrDate( cPeriodo.getTime() );
		
	}
	
	private void montaListaCampos(){
		
		lcFor.add(new GuardaCampo( txtCodFor, "CodFor", "Cód.for.", ListaCampos.DB_PK, false));
		lcFor.add(new GuardaCampo( txtDescFor, "RazFor", "Razão social do fornecedor", ListaCampos.DB_SI,false));
		txtCodFor.setTabelaExterna(lcFor);
		txtCodFor.setNomeCampo("CodFor");
		txtCodFor.setFK(true);
		lcFor.setReadOnly(true);
		lcFor.montaSql(false, "FORNECED", "CP");
		
	}
	
	@ Override
	public void imprimir( boolean bVisualizar ) {

		if ( txtDatafim.getVlrDate().before( txtDataini.getVlrDate() ) ) {
			Funcoes.mensagemInforma( this, "Data final maior que a data inicial!" );
			return;
		}
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sSQL = new StringBuffer();
		StringBuffer sWhere = new StringBuffer();
		StringBuffer sCab = new StringBuffer();
		
		try {
			
			if( txtCodFor.getVlrString() != null && txtCodFor.getVlrString().trim().length()>0 ){
				
				sCab.append( "FORNECEDOR - " + txtDescFor.getVlrString() );
				sWhere.append( "" );
			}

			sSQL.append( "SELECT F.CODFOR CODEMIT, F.RAZFOR RAZEMIT, " );
			sSQL.append( "CAST( ? AS DATE) DATA, 'A' TIPO, 0 DOC, " );
			sSQL.append( "(COALESCE( ( SELECT SUM(P.VLRPARCPAG) " );
			sSQL.append( "FROM FNPAGAR P " );
			sSQL.append( "WHERE P.CODEMP=? AND P.CODFILIAL=? AND " );
			sSQL.append( "P.CODEMPFR=F.CODEMP AND P.CODFILIALFR=F.CODFILIAL AND " );
			sSQL.append( "P.CODFOR=F.CODFOR AND " );
			sSQL.append( "P.DATAPAG < ? ),0) + " );
			sSQL.append( "COALESCE( ( SELECT SUM(L.VLRLANCA) " );
			sSQL.append( "FROM FNLANCA L, FNPAGAR P " );
			sSQL.append( "WHERE L.CODEMPPG=P.CODEMP AND L.CODFILIALPG=P.CODFILIAL AND " );
			sSQL.append( "L.CODPAG=P.CODPAG AND P.CODEMPFR=F.CODEMP AND P.CODFILIALFR=F.CODFILIAL AND " );
			sSQL.append( "P.CODFOR=F.CODFOR AND " );
			sSQL.append( "P.CODEMP=? AND P.CODFILIAL=? AND " );
			sSQL.append( "L.DATALANCA < ? ), 0) " );
			sSQL.append( ") VALOR " );
			sSQL.append( "FROM CPFORNECED F " );
			sSQL.append( "WHERE F.CODEMP=? AND F.CODFILIAL=? AND F.CODFOR=? AND " );
			sSQL.append( "( EXISTS (SELECT * FROM FNPAGAR P2 " );
			sSQL.append( "WHERE P2.CODEMP=? AND P2.CODFILIAL=? AND " );
			sSQL.append( "P2.CODEMPFR=F.CODEMP AND P2.CODFILIALFR=F.CODFILIAL AND " );
			sSQL.append( "P2.CODFOR=F.CODFOR AND DATAPAG BETWEEN ? AND ? )" );
			sSQL.append( "OR EXISTS (SELECT * FROM FNLANCA L2, FNPAGAR P2 " );
			sSQL.append( "WHERE L2.CODEMPPG=P2.CODEMP AND L2.CODFILIALPG=P2.CODFILIAL AND " );
			sSQL.append( "L2.CODPAG=P2.CODPAG AND F.CODEMP=P2.CODEMPFR AND " );
			sSQL.append( "F.CODFILIAL=P2.CODFILIALFR AND F.CODFOR=P2.CODFOR AND " );
			sSQL.append( "P2.CODEMP=? AND P2.CODFILIAL=? AND " );
			sSQL.append( "L2.DATALANCA BETWEEN ? AND ?" );
			sSQL.append( ") ) " );
			sSQL.append( "UNION " );
			sSQL.append( "SELECT P.CODFOR CODEMIT, F.RAZFOR RAZEMIT, " );
			sSQL.append( "P.DATAPAG DATA, 'C' TIPO, P.DOCPAG DOC, P.VLRPARCPAG VALOR " );
			sSQL.append( "FROM FNPAGAR P, CPFORNECED F " );
			sSQL.append( "WHERE F.CODEMP=P.CODEMPFR AND F.CODFILIAL=P.CODFILIALFR AND " );
			sSQL.append( "F.CODFOR=P.CODFOR AND " );
			sSQL.append( "P.CODEMP=? AND P.CODFILIAL=? AND F.CODFOR=? AND " );
			sSQL.append( "P.DATAPAG BETWEEN ? AND ? " );
			sSQL.append( "UNION " );
			sSQL.append( "SELECT P.CODFOR CODEMIT, F.RAZFOR RAZEMIT, " );
			sSQL.append( "L.DATALANCA DATA, 'P' TIPO, P.DOCPAG DOC, L.VLRLANCA " );
			sSQL.append( "FROM FNLANCA L, FNPAGAR P, CPFORNECED F " );
			sSQL.append( "WHERE L.CODEMPPG=P.CODEMP AND L.CODFILIALPG=P.CODFILIAL AND " );
			sSQL.append( "L.CODPAG=P.CODPAG AND F.CODEMP=P.CODEMPFR AND F.CODFILIAL=P.CODFILIALFR AND " );
			sSQL.append( "F.CODFOR=P.CODFOR AND F.CODFOR=? AND " );
			sSQL.append( "P.CODEMP=? AND P.CODFILIAL=? AND " );
			sSQL.append( "L.DATALANCA BETWEEN ? AND ? " );
			sSQL.append( "ORDER BY 1, 2, 3, 4, 5" );
			sSQL.append( sWhere );
		
			ps = con.prepareStatement( sSQL.toString() );
			ps.setDate( 1 , Funcoes.strDateToSqlDate( txtDataini.getVlrString() ));
			//ps.setInt(1, Aplicativo.iCodEmp);
			//ps.setInt(2, ListaCampos.getMasterFilial( "FNPAGAR" )); 
			//
			//ps.setDate(4, Funcoes.strDateToSqlDate( txtDatafim.getVlrString() ));
			rs = ps.executeQuery();
			
			imprimiGrafico( bVisualizar, rs, sCab);
			
			rs.close();
			ps.close();

			if ( !con.getAutoCommit() ) {
				con.commit();
			}
			
		} catch ( Exception err ) {
			
			err.printStackTrace();
			Funcoes.mensagemErro( this, "Erro ao buscar dados na tabela!\n" + err.getMessage(), true, con, err );
		}
	}
	
	private void imprimiGrafico( final boolean bVisualizar, final ResultSet rs, final StringBuffer sCab ) {
		
		FPrinterJob dlGr = null;
		HashMap<String, Object> hParam = new HashMap<String, Object>();
		
		hParam.put( "CODEMP", Aplicativo.iCodEmp );
		hParam.put( "CODFILIAL", ListaCampos.getMasterFilial( "FNPAGAR" ));
		hParam.put( "RAZAOEMP" , Aplicativo.sEmpSis );
		hParam.put( "FILTROS", sCab );

		dlGr = new FPrinterJob( "relatorios/FRRazFor.jasper", "Relatório por Razão", sCab.toString(), rs, hParam, this );
		
		if ( bVisualizar ) {
			dlGr.setVisible( true );
		}
		else {
			try {
				JasperPrintManager.printReport( dlGr.getRelatorio(), true );
			} catch ( Exception err ) {
				Funcoes.mensagemErro( this, "Erro na impressão de relatório por razão!" + err.getMessage(), true, con, err );
			}
		}
	}
	
	public void setConexao(Connection cn) {
		
		super.setConexao(cn);
		lcFor.setConexao( cn );
		
	}
}
