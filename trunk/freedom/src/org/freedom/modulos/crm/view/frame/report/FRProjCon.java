/**
 * @version 20/08/2011 <BR>
 * @author Setpoint Informática Ltda.<BR>
 * 
 *         Projeto: Freedom <BR>
 * 
 *         Pacote: org.freedom.modulos.tmk <BR>
 *         Classe: @(#)FRDiario.java <BR>
 * 
 *         Este arquivo é parte do sistema Freedom-ERP, o Freedom-ERP é um software livre; você pode redistribui-lo e/ou <BR>
 *         modifica-lo dentro dos termos da Licença Pública Geral GNU como publicada pela Fundação do Software Livre (FSF); <BR>
 *         na versão 2 da Licença, ou (na sua opnião) qualquer versão. <BR>
 *         Este programa é distribuido na esperança que possa ser util, mas SEM NENHUMA GARANTIA; <BR>
 *         sem uma garantia implicita de ADEQUAÇÂO a qualquer MERCADO ou APLICAÇÃO EM PARTICULAR. <BR>
 *         Veja a Licença Pública Geral GNU para maiores detalhes. <BR>
 *         Você deve ter recebido uma cópia da Licença Pública Geral GNU junto com este programa, se não, <BR>
 *         de acordo com os termos da LPG-PC <BR>
 * <BR>
 * 
 *         Relatório diário de ligações.
 * 
 */

package org.freedom.modulos.crm.view.frame.report;

import java.awt.Component;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.functions.Funcoes;
import org.freedom.library.persistence.GuardaCampo;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.component.JCheckBoxPad;
import org.freedom.library.swing.component.JLabelPad;
import org.freedom.library.swing.component.JRadioGroup;
import org.freedom.library.swing.component.JTextFieldFK;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.frame.Aplicativo;
import org.freedom.library.swing.frame.FPrinterJob;
import org.freedom.library.swing.frame.FRelatorio;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.SwingConstants;

import net.sf.jasperreports.engine.JasperPrintManager;

public class FRProjCon extends FRelatorio {

	private static final long serialVersionUID = 1L;

	private JRadioGroup<?, ?> rgSitCon = null;
	
	private Vector<String> vLabsSitCon = new Vector<String>();
	private Vector<String> vValsSitCon = new Vector<String>();
	
	private JCheckBoxPad cbCobMensa = new JCheckBoxPad( "Cobrança Mensal", "S", "N" );
	private JCheckBoxPad cbCobBimes = new JCheckBoxPad( "Cobrança Bimenstral", "S", "N" );
	private JCheckBoxPad cbCobAnual = new JCheckBoxPad( "Cobrança Anual", "S", "N" );
	private JCheckBoxPad cbCobEspor = new JCheckBoxPad( "Cobrança Esporádica", "S", "N" );
	
	private JCheckBoxPad cbReceb = new JCheckBoxPad( "Recebível", "S", "N" );

	public FRProjCon() {

		setTitulo( "Relatório de Projetos / Contratos" );
		setAtribos( 80, 80, 350, 275 );
		
		vLabsSitCon.addElement( "Ativo" );
		vLabsSitCon.addElement( "Inativo" );
		vLabsSitCon.addElement( "Ambos" );
		vValsSitCon.addElement( "A" );
		vValsSitCon.addElement( "I" );
		vValsSitCon.addElement( "S" );
		
		rgSitCon = new JRadioGroup<String, String>( 3, 1, vLabsSitCon, vValsSitCon );
		rgSitCon.setVlrString( "A" );
		
		adic( cbCobMensa, 30, 20, 180, 20 );
		adic( cbCobBimes, 30, 40, 180, 20 );
		adic( cbCobAnual, 30, 60, 180, 20 );
		adic( cbCobEspor, 30, 80, 180, 20 );
		
		adic( rgSitCon, 30, 105, 180, 60 );
		
		adic( cbReceb, 30, 170, 180, 20 );
		
	}

	public void imprimir( boolean bVisualizar ) {

		StringBuilder sql = new StringBuilder();
		PreparedStatement ps = null;
		ResultSet rs = null;
		int iparam = 1;
		
		sql.append( "SELECT CT.DESCCONTR, CT.CODCONTR, CT.CODCLI, CL.RAZCLI, ");
		sql.append( "SUM(I.QTDITCONTR) QTDITCONTR, SUM(I.VLRITCONTR) VLRITCONTR, SUM(I.QTDITCONTR*I.VLRITCONTR) TOTITCONTR ");
		sql.append( "FROM VDCONTRATO CT, VDITCONTRATO I, VDCLIENTE CL ");
		sql.append( "WHERE I.CODEMP=CT.CODEMP AND I.CODFILIAL=CT.CODFILIAL AND I.CODCONTR=CT.CODCONTR AND ");
		sql.append( "CT.CODEMP=? AND CT.CODFILIAL=? AND CL.CODEMP=CT.CODEMPCL AND CL.CODFILIAL=CT.CODFILIALCL AND CL.CODCLI=CT.CODCLI AND ");
		
		if ( rgSitCon.getVlrString().equals( 'A' )){
			sql.append( "CT.ATIVO='S' AND ");
		} else if ( rgSitCon.getVlrString().equals( 'I' ) ) {
			sql.append( "CT.ATIVO='N' AND ");
		} else {
			sql.append( "CT.ATIVO IN ('S','N') AND " );
		}
		
		sql.append( "CT.TPCOBCONTR IN (" ); 
		
		if ( cbCobMensa.getVlrString().equals( "S" ) ) {
			sql.append( "'ME'" );
		}
		if ( cbCobBimes.getVlrString().equals( "S" ) && cbCobMensa.getVlrString().equals( "S" ) ) {
			sql.append( ",'BI'" );
		} else if ( cbCobBimes.getVlrString().equals( "S" ) ) { sql.append( "'BI'" ); }
		if ( cbCobAnual.getVlrString().equals( "S" ) && ( cbCobBimes.getVlrString().equals( "S" ) || cbCobMensa.getVlrString().equals( "S" ) ) ) {
			sql.append( ",'AN'" );
		} else if ( cbCobAnual.getVlrString().equals( "S" ) ) { sql.append( "'AN'" ); }
		if ( cbCobEspor.getVlrString().equals( "S" ) && ( cbCobBimes.getVlrString().equals( "S" ) || cbCobMensa.getVlrString().equals( "S" ) || cbCobAnual.getVlrString().equals( "S" ) ) ) {
			sql.append( ",'ES'" );
		} else if ( cbCobAnual.getVlrString().equals( "S" ) ) { sql.append( "'ES'" ); }
		
		sql.append( ") AND " );
		
		if ( cbReceb.getVlrString().equals( "S" ) ) {
			sql.append( "CT.RECEBCONTR='S' ");
		} else {
			sql.append( "CT.RECEBCONTR='N' ");
		}
		
		sql.append( "GROUP BY CT.DESCCONTR, CT.CODCONTR, CT.CODCLI, CL.RAZCLI " );
		sql.append( "ORDER BY CL.RAZCLI, CT.CODCONTR, CT.DESCCONTR, CT.CODCONTR" );

		try {

			ps = con.prepareStatement( sql.toString() );
			ps.setInt( iparam++, Aplicativo.iCodEmp );
			ps.setInt( iparam++, ListaCampos.getMasterFilial( "VDCONTRATO" ) );
			
			rs = ps.executeQuery();

		} catch ( SQLException err ) {

			err.printStackTrace();
			Funcoes.mensagemErro( this, " Erro na consulta!" );
		}

		imprimiGrafico( rs, bVisualizar );

	}

	private void imprimiGrafico( final ResultSet rs, final boolean bVisualizar ) {

		FPrinterJob dlGr = null;
		HashMap<String, Object> hParam = new HashMap<String, Object>();

		hParam.put( "CODEMP", Aplicativo.iCodEmp );
		hParam.put( "CODFILIAL", ListaCampos.getMasterFilial( "CPCOMPRA" ) );
		hParam.put( "RAZAOEMP", Aplicativo.empresa.toString() );
		hParam.put( "SUBREPORT_DIR", "org/freedom/relatorios/" );

		dlGr = new FPrinterJob( "layout/rel/REL_PROJ_CRONT_01.jasper", "Relatório de projetos/contratos", "", rs, hParam, this );

		if ( bVisualizar ) {
			dlGr.setVisible( true );
		}
		else {
			try {
				JasperPrintManager.printReport( dlGr.getRelatorio(), true );
			} catch ( Exception err ) {
				Funcoes.mensagemErro( this, "Erro na impressão de relatório de projetos/contratos!" + err.getMessage(), true, con, err );
			}
		}
	}

	public void setConexao( DbConnection cn ) {

		super.setConexao( cn );
	}

}
