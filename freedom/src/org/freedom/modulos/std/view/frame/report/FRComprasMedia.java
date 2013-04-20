/**
 * @version 08/12/2000 <BR>
 * @author Setpoint Informática Ltda./Alex Rodrigues <BR>
 * 
 *         Projeto: Freedom <BR>
 * 
 *         Pacote: org.freedom.modulos.std <BR>
 *         Classe: @(#)FRCompras.java <BR>
 * 
 *         Este arquivo é parte do sistema Freedom-ERP, o Freedom-ERP é um software livre; você pode redistribui-lo e/ou <BR>
 *         modifica-lo dentro dos termos da Licença Pública Geral GNU como publicada pela Fundação do Software Livre (FSF); <BR>
 *         na versão 2 da Licença, ou (na sua opnião) qualquer versão. <BR>
 *         Este programa é distribuido na esperança que possa ser util, mas SEM NENHUMA GARANTIA; <BR>
 *         sem uma garantia implicita de ADEQUAÇÂO a qualquer MERCADO ou APLICAÇÃO EM PARTICULAR. <BR>
 *         Veja a Licença Pública Geral GNU para maiores detalhes. <BR>
 *         Você deve ter recebido uma cópia da Licença Pública Geral GNU junto com este programa, se não, <BR>
 *         escreva para a Fundação do Software Livre(FSF) Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA <BR>
 * <BR>
 * 
 *         Comentários sobre a classe...
 * 
 */

package org.freedom.modulos.std.view.frame.report;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.math.BigDecimal;
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

import org.freedom.infra.functions.StringFunctions;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.component.ImprimeOS;
import org.freedom.library.functions.Funcoes;
import org.freedom.library.persistence.GuardaCampo;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.component.JLabelPad;
import org.freedom.library.swing.component.JRadioGroup;
import org.freedom.library.swing.component.JTextFieldFK;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.frame.Aplicativo;
import org.freedom.library.swing.frame.FPrinterJob;
import org.freedom.library.swing.frame.FRelatorio;
import org.freedom.library.type.TYPE_PRINT;

public class FRComprasMedia extends FRelatorio implements FocusListener {

	private static final long serialVersionUID = 1L;

	private JTextFieldPad txtDataini = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtDatafim = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtCodFor = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 10, 0 );

	private JTextFieldFK txtDescFor = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private JTextFieldPad txtCodPlanoPag = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 10, 0 );

	private JTextFieldFK txtDescPlanoPag = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private ListaCampos lcFor = new ListaCampos( this );

	private ListaCampos lcPlanoPag = new ListaCampos( this );

	private Vector<String> vPesqLab = new Vector<String>();

	private Vector<String> vPesqVal = new Vector<String>();
	
	private Vector<String> vLabs1 = new Vector<String>();

	private Vector<String> vVals1 = new Vector<String>();

	private Vector<String> vLabs2 = new Vector<String>();

	private Vector<String> vVals2 = new Vector<String>();
	
	private Vector<String> vLabs3 = new Vector<String>();
	
	private Vector<String> vVals3 = new Vector<String>();

	private Vector<String> vLabs4 = new Vector<String>();
	
	private Vector<String> vVals4 = new Vector<String>();

	private JRadioGroup<?, ?> rgTipoRel = null;
	
	private JRadioGroup<?, ?> rgFin = null;
	
	private JRadioGroup<?, ?> rgFiscal = null;

	public FRComprasMedia() {

		setTitulo( "Média de compras por item" );
		setAtribos( 50, 50, 390, 450 );

		lcFor.add( new GuardaCampo( txtCodFor, "CodFor", "Cód.for.", ListaCampos.DB_PK, false ) );
		lcFor.add( new GuardaCampo( txtDescFor, "RazFor", "Razão social do fornecedor", ListaCampos.DB_SI, false ) );
		txtCodFor.setTabelaExterna( lcFor, null );
		txtCodFor.setNomeCampo( "CodFor" );
		txtCodFor.setFK( true );
		lcFor.setReadOnly( true );
		lcFor.montaSql( false, "FORNECED", "CP" );

		lcPlanoPag.add( new GuardaCampo( txtCodPlanoPag, "CodPlanoPag", "Cód.plano.pag.", ListaCampos.DB_PK, false ) );
		lcPlanoPag.add( new GuardaCampo( txtDescPlanoPag, "DescPlanoPag", "Descrição do plano de pagamento", ListaCampos.DB_SI, false ) );
		txtCodPlanoPag.setTabelaExterna( lcPlanoPag, null );
		txtCodPlanoPag.setNomeCampo( "CodPlanoPag" );
		txtCodPlanoPag.setFK( true );
		lcPlanoPag.setReadOnly( true );
		lcPlanoPag.montaSql( false, "PLANOPAG", "FN" );

		vPesqLab.addElement( "Por data emissão" );
		vPesqLab.addElement( "Por data entrada" );
		vPesqVal.addElement( "E" );
		vPesqVal.addElement( "D" );

		rgTipoRel = new JRadioGroup<String, String>( 1, 2, vPesqLab, vPesqVal );
		rgTipoRel.setVlrString( "E" );
		
		vLabs3.addElement( "Financieiro" );
		vLabs3.addElement( "Não financeiro" );
		vLabs3.addElement( "Ambos" );
		vVals3.addElement( "F" );
		vVals3.addElement( "N" );
		vVals3.addElement( "A" );

		rgFin = new JRadioGroup<String, String>( 1, 2,  vLabs3, vVals3 );
		rgFin.setVlrString( "A" );
		

		vLabs4.addElement( "Fiscal" );
		vLabs4.addElement( "Não fiscal" );
		vLabs4.addElement( "Ambos" );
		vVals4.addElement( "F" );
		vVals4.addElement( "N" );
		vVals4.addElement( "A" );

		rgFiscal = new JRadioGroup<String, String>( 1, 2,  vLabs4, vVals4 );
		rgFiscal.setVlrString( "A" );
		
		JLabelPad lbLinha = new JLabelPad();
		lbLinha.setBorder( BorderFactory.createEtchedBorder() );
		JLabelPad lbPeriodo = new JLabelPad( "Período:", SwingConstants.CENTER );
		lbPeriodo.setOpaque( true );

		adic( lbPeriodo, 7, 1, 80, 20 );
		adic( lbLinha, 5, 10, 340, 45 );

		adic( new JLabelPad( "De:" ), 10, 25, 30, 20 );
		adic( txtDataini, 40, 25, 97, 20 );
		adic( new JLabelPad( "Até:" ), 152, 25, 37, 20 );
		adic( txtDatafim, 190, 25, 100, 20 );
		
		adic( rgTipoRel, 7, 65, 340, 30 );
		
		adic( new JLabelPad( "Cód.for." ), 7, 100, 80, 20 );
		adic( txtCodFor, 7, 120, 80, 20 );
		adic( new JLabelPad( "Descrição do fornecedor" ), 90, 100, 240, 20 );
		adic( txtDescFor, 90, 120, 255, 20 );
		adic( new JLabelPad( "Cód.pl.pag." ), 7, 140, 80, 20 );
		adic( txtCodPlanoPag, 7, 160, 80, 20 );
		adic( new JLabelPad( "Descrição do plano de pagamento" ), 90, 140, 240, 20 );
		adic( txtDescPlanoPag, 90, 160, 255, 20 );
		
		adic( rgFin, 7, 270, 340, 30 );

		adic( rgFiscal, 7, 310, 340, 30 );

		txtDataini.setAtivo( false );

		Calendar cPeriodo = Calendar.getInstance();
		txtDatafim.setVlrDate( cPeriodo.getTime() );
		cPeriodo.set( Calendar.DAY_OF_MONTH, cPeriodo.get( Calendar.DAY_OF_MONTH ) - 30 );
		txtDataini.setVlrDate( getDataini( txtDatafim.getVlrDate() ) );
		txtDatafim.addFocusListener( this );
	}

	public void setConexao( DbConnection cn ) {

		super.setConexao( cn );
		lcFor.setConexao( cn );
		lcPlanoPag.setConexao( cn );
	}

	public void imprimir( TYPE_PRINT bVisualizar ) {

		if ( txtDatafim.getVlrDate().before( txtDataini.getVlrDate() ) ) {
			Funcoes.mensagemInforma( this, "Data final maior que a data inicial!" );
			return;
		}
		
		imprimirGrafico( bVisualizar );

	}
	
	public void imprimirGrafico( TYPE_PRINT bVisualizar ) {

		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuilder sql = new StringBuilder();
		StringBuilder tipo = new StringBuilder();
		StringBuilder cab = new StringBuilder();

		int iparam = 1;
		
		sql.append("select pd.codprod, pd.refprod, pd.descprod,  ");

		/*if ( txtCodFor.getVlrInteger().intValue() > 0 ) {
			sWhere.append( " AND C.CODFOR = ?" + txtCodFor.getVlrInteger().intValue() );
			sCab.append( "FORNECEDOR : " + txtDescFor.getVlrString() );
		}
		if ( txtCodPlanoPag.getVlrInteger().intValue() > 0 ) {
			sWhere.append( " AND C.CODPLANOPAG = " + txtCodPlanoPag.getVlrInteger().intValue() );
			sCab.append( "PLANO DE PAGAMENTO: " + txtDescPlanoPag.getVlrString() );

		}

		String fin = null;
		if ("F".equals( rgFin.getVlrString() ) ) {
			sCab.append( " (Somente financeiros) " );
			fin = "AND TM.SomaVdTipoMov='S' ";
		} else if ("N".equals( rgFin.getVlrString())) {
			sCab.append( " (Não financeiros) ");
			fin = "AND TM.SomaVdTipoMov<>'S' ";
		}

		String fiscal = null;
		if ("F".equals( rgFiscal.getVlrString() ) ) {
			sCab.append( " (Somente fiscais) " );
			fiscal = "AND TM.FiscalTipomov='S' ";
		} else if ("N".equals( rgFiscal.getVlrString())) {
			sCab.append( " (Não fiscais) ");
			fiscal = "AND TM.FiscalTipomov<>'S' ";
		}

		if("E".equals( rgTipoRel.getVlrString()))
		{
			sTipo.append( "AND C.DTEMITCOMPRA BETWEEN ? AND ?" );
			sCab.append( " (Ordenado por Data de Emissão)" );
		} else	{
			sTipo.append( "AND C.DTENTCOMPRA BETWEEN ? AND ?" );
			sCab.append( " (Ordenado por Data de Entrada)" );
		}

		sSQL.append( "SELECT C.CODCOMPRA, C.DOCCOMPRA, C.DTEMITCOMPRA, C.DTENTCOMPRA, C.VLRLIQCOMPRA, " );
		sSQL.append( "F.NOMEFOR, PG.DESCPLANOPAG, " );
		sSQL.append( "IT.CODITCOMPRA, IT.CODPROD, PD.DESCPROD, IT.CODLOTE, IT.QTDITCOMPRA, " );
		sSQL.append( "IT.VLRLIQITCOMPRA, IT.PERCDESCITCOMPRA, IT.VLRDESCITCOMPRA, " ); 
		sSQL.append( "PD.CODFABPROD " );
		sSQL.append( "FROM CPCOMPRA C, CPITCOMPRA IT, CPFORNECED F, FNPLANOPAG PG, EQPRODUTO PD, EQTIPOMOV TM " );
		sSQL.append( "WHERE C.CODEMP=? AND C.CODFILIAL=? " );
		sSQL.append( "AND C.CODEMPFR=F.CODEMP AND C.CODFILIALFR=F.CODFILIAL AND C.CODFOR=F.CODFOR " );
		sSQL.append( "AND C.CODEMPPG=PG.CODEMP AND C.CODFILIALPG=PG.CODFILIAL AND C.CODPLANOPAG=PG.CODPLANOPAG " );
		sSQL.append( "AND C.CODEMP=IT.CODEMP AND C.CODFILIAL=IT.CODFILIAL AND C.CODCOMPRA=IT.CODCOMPRA " );
		sSQL.append( "AND IT.CODEMPPD=PD.CODEMP AND IT.CODFILIALPD=PD.CODFILIAL AND IT.CODPROD=PD.CODPROD " );
		sSQL.append( "AND TM.CODEMP=C.CODEMPTM AND TM.CODFILIAL=C.CODFILIALTM AND TM.CODTIPOMOV=C.CODTIPOMOV " );
		if ( fin != null ) {
			sSQL.append( fin );
		}
		if ( fiscal != null ) {
			sSQL.append( fiscal );
		}
		
		sSQL.append( sTipo );
		sSQL.append( sWhere );
		sSQL.append( " ORDER BY C.CODCOMPRA, IT.CODITCOMPRA" );

		try {

			ps = con.prepareStatement( sSQL.toString() );
			ps.setInt( iparam++, Aplicativo.iCodEmp );
			ps.setInt( iparam++, ListaCampos.getMasterFilial( "CPCOMPRA" ) );
			ps.setDate( iparam++, Funcoes.strDateToSqlDate( txtDataini.getVlrString() ) );
			ps.setDate( iparam++, Funcoes.strDateToSqlDate( txtDatafim.getVlrString() ) );
			rs = ps.executeQuery();

		} catch ( SQLException err ) {

			err.printStackTrace();
			Funcoes.mensagemErro( this, " Erro na consulta da tabela de compras" );
		}

		imprimiGrafico( rs, bVisualizar, sCab.toString() );*/
	}


	
	private void imprimiGrafico( final ResultSet rs, final TYPE_PRINT bVisualizar, final String sCab ) {

		FPrinterJob dlGr = null;
		HashMap<String, Object> hParam = new HashMap<String, Object>();

		hParam.put( "CODEMP", Aplicativo.iCodEmp );
		hParam.put( "CODFILIAL", ListaCampos.getMasterFilial( "CPCOMPRA" ) );
		hParam.put( "RAZAOEMP", Aplicativo.empresa.toString() );
		hParam.put( "FILTROS", sCab );

		dlGr = new FPrinterJob( "relatorios/compras_media.jasper", "Relatório de média de compras por item", sCab, rs, hParam, this );

		if ( bVisualizar==TYPE_PRINT.VIEW ) {
			dlGr.setVisible( true );
		}
		else {
			try {
				JasperPrintManager.printReport( dlGr.getRelatorio(), true );
			} catch ( Exception err ) {
				Funcoes.mensagemErro( this, "Erro na impressão de relatório média de compras por item!" + err.getMessage(), true, con, err );
			}
		}
	}

	public void focusGained( FocusEvent e ) {

	}

	public void focusLost( FocusEvent e ) {
		if (e.getSource()==txtDatafim) {
			txtDataini.setVlrDate( getDataini(txtDatafim.getVlrDate()) );
		}
		
	}
	
	private Date getDataini(Date dtfim) {
		Calendar result = Calendar.getInstance();
		result.setTime( dtfim );
		int dia = result.get( Calendar.DAY_OF_MONTH );
		int mes = result.get( Calendar.MONTH );
		int ano = result.get( Calendar.YEAR );
		if ( mes ==11) {
			mes = 0;
		} else {
			mes ++;
			ano --;
		}
		dia = 1;
		result.set( ano, mes, dia, 0, 0, 0 );
		return result.getTime();
	}
}
