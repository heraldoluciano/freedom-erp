/**
 * @version 02/11/2003 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 * 
 *         Projeto: Freedom <BR>
 * 
 *         Pacote: org.freedom.modulos.std <BR>
 *         Classe:
 * @(#)FRMediaItem.java <BR>
 * 
 *                      Este programa é licenciado de acordo com a LPG-PC (Licença Pública Geral para Programas de Computador), <BR>
 *                      modifica-lo dentro dos termos da Licença Pública Geral GNU como publicada pela Fundação do Software Livre (FSF); <BR>
 *                      A LPG-PC deve acompanhar todas PUBLICAÇÕES, DISTRIBUIÇÕES e REPRODUÇÕES deste Programa. <BR>
 *                      Caso uma cópia da LPG-PC não esteja disponível junto com este Programa, você pode contatar <BR>
 *                      sem uma garantia implicita de ADEQUAÇÂO a qualquer MERCADO ou APLICAÇÃO EM PARTICULAR. <BR>
 *                      Veja a Licença Pública Geral GNU para maiores detalhes. <BR>
 *                      Para poder USAR, PUBLICAR, DISTRIBUIR, REPRODUZIR ou ALTERAR este Programa é preciso estar <BR>
 *                      de acordo com os termos da LPG-PC <BR>
 * <BR>
 * 
 *                      Comentários sobre a classe...
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

import net.sf.jasperreports.engine.JasperPrintManager;

import org.freedom.infra.functions.StringFunctions;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.component.ImprimeOS;
import org.freedom.library.functions.Funcoes;
import org.freedom.library.persistence.GuardaCampo;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.component.JCheckBoxPad;
import org.freedom.library.swing.component.JLabelPad;
import org.freedom.library.swing.component.JRadioGroup;
import org.freedom.library.swing.component.JTextFieldFK;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.frame.Aplicativo;
import org.freedom.library.swing.frame.AplicativoPD;
import org.freedom.library.swing.frame.FPrinterJob;
import org.freedom.library.swing.frame.FRelatorio;
import org.freedom.library.type.TYPE_PRINT;

public class FRMediaItem extends FRelatorio implements FocusListener {

	private static final long serialVersionUID = 1L;

	private JTextFieldPad txtAnoini = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 4, 0 );

	private JTextFieldPad txtMesini = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 2, 0 );

	private JTextFieldPad txtAnofim = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 4, 0 );

	private JTextFieldPad txtMesfim = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 2, 0 );

	private JTextFieldPad txtCodGrup = new JTextFieldPad( JTextFieldPad.TP_STRING, 14, 0 );

	private JTextFieldFK txtDescGrup = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private JTextFieldPad txtCodMarca = new JTextFieldPad( JTextFieldPad.TP_STRING, 6, 0 );

	private JTextFieldFK txtDescMarca = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private JTextFieldPad txtSiglaMarca = new JTextFieldPad( JTextFieldPad.TP_STRING, 20, 0 );

	private JTextFieldPad txtCodProd = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 10, 0 );

	private JTextFieldFK txtDescProd = new JTextFieldFK( JTextFieldPad.TP_STRING, 100, 0 );
	
	private JTextFieldFK txtRefprod = new JTextFieldFK( JTextFieldPad.TP_STRING, 20 , 0 );

	private JTextFieldPad txtCodVend = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 10, 0 );

	private JTextFieldFK txtDescVend = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private JTextFieldPad txtDataini = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtDatafim = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

 	private JRadioGroup<?, ?> rgTipoimp = null;

	private JLabelPad lbOrdem = new JLabelPad( "Ordenar por:" );

	private JCheckBoxPad cbVendaCanc = new JCheckBoxPad( "Mostrar Canceladas", "S", "N" );

	private JRadioGroup<?, ?> rgOrdem = null;

	private JRadioGroup<?, ?> rgFaturados = null;

	private JRadioGroup<?, ?> rgFinanceiro = null;

	private Vector<String> vLabs = new Vector<String>();

	private Vector<String> vVals = new Vector<String>();

	private Vector<String> vLabsFat = new Vector<String>();

	private Vector<String> vValsFat = new Vector<String>();

	private Vector<String> vLabsFin = new Vector<String>();

	private Vector<String> vValsFin = new Vector<String>();

	private Vector<String> vLabsTipoimp = new Vector<String>();

	private Vector<String> vValsTipoimp = new Vector<String>();

	private ListaCampos lcGrup = new ListaCampos( this );

	private ListaCampos lcMarca = new ListaCampos( this );

	private ListaCampos lcProd = new ListaCampos( this );

	private ListaCampos lcVend = new ListaCampos( this );

	private JRadioGroup<?, ?> rgEmitidos = null;
	
	private Vector<String> vLabsEmit = new Vector<String>();

	private Vector<String> vValsEmit = new Vector<String>();
	
	public FRMediaItem() {

		setTitulo( "Média de vendas por item" );
		setAtribos( 80, 80, 335, 600 );
		lcGrup.add( new GuardaCampo( txtCodGrup, "CodGrup", "Cód.grupo", ListaCampos.DB_PK, false ) );
		lcGrup.add( new GuardaCampo( txtDescGrup, "DescGrup", "Descrição do grupo", ListaCampos.DB_SI, false ) );
		txtCodGrup.setTabelaExterna( lcGrup, null );
		txtCodGrup.setNomeCampo( "CodGrup" );
		txtCodGrup.setFK( true );
		lcGrup.setReadOnly( true );
		lcGrup.montaSql( false, "GRUPO", "EQ" );

		lcMarca.add( new GuardaCampo( txtCodMarca, "CodMarca", "Cód.marca", ListaCampos.DB_PK, false ) );
		lcMarca.add( new GuardaCampo( txtDescMarca, "DescMarca", "Descrição da marca", ListaCampos.DB_SI, false ) );
		lcMarca.add( new GuardaCampo( txtSiglaMarca, "SiglaMarca", "Sigla", ListaCampos.DB_SI, false ) );
		txtCodMarca.setTabelaExterna( lcMarca, null );
		txtCodMarca.setNomeCampo( "CodMarca" );
		txtCodMarca.setFK( true );
		lcMarca.setReadOnly( true );
		lcMarca.montaSql( false, "MARCA", "EQ" );

		lcProd.add( new GuardaCampo( txtCodProd, "CodProd", "Cód.prod.", ListaCampos.DB_PK, false ) );
		lcProd.add( new GuardaCampo( txtDescProd, "DescProd", "Descrição do produto", ListaCampos.DB_SI, false ) );
		lcProd.add( new GuardaCampo( txtRefprod, "RefProd", "Ref.prod.", ListaCampos.DB_SI, false ) );
		txtCodProd.setTabelaExterna( lcProd, null );
		txtCodProd.setNomeCampo( "CodProd" );
		txtCodProd.setFK( true );
		lcProd.setReadOnly( true );
		lcProd.montaSql( false, "PRODUTO", "EQ" );

		lcVend.add( new GuardaCampo( txtCodVend, "CodVend", "Cód.comiss.", ListaCampos.DB_PK, false ) );
		lcVend.add( new GuardaCampo( txtDescVend, "NomeVend", "Nome do comissionado", ListaCampos.DB_SI, false ) );
		txtCodVend.setTabelaExterna( lcVend, null );
		txtCodVend.setNomeCampo( "CodVend" );
		txtCodVend.setFK( true );
		lcVend.setReadOnly( true );
		lcVend.montaSql( false, "VENDEDOR", "VD" );


		vLabs.addElement( "Código" );
		vLabs.addElement( "Descrição" );
		vVals.addElement( "C" );
		vVals.addElement( "D" );
		rgOrdem = new JRadioGroup<String, String>( 1, 2, vLabs, vVals );
		rgOrdem.setVlrString( "D" );

		vLabsTipoimp.addElement( "Texto" );
		vLabsTipoimp.addElement( "Gráfico" );
		vValsTipoimp.addElement( "T" );
		vValsTipoimp.addElement( "G" );
		rgTipoimp = new JRadioGroup<String, String>( 2, 1, vLabsTipoimp, vValsTipoimp );
		rgTipoimp.setVlrString( "G" );

		vLabsFat.addElement( "Faturado" );
		vLabsFat.addElement( "Não Faturado" );
		vLabsFat.addElement( "Ambos" );
		vValsFat.addElement( "S" );
		vValsFat.addElement( "N" );
		vValsFat.addElement( "A" );
		rgFaturados = new JRadioGroup<String, String>( 3, 1, vLabsFat, vValsFat );
		rgFaturados.setVlrString( "S" );

		vLabsFin.addElement( "Financeiro" );
		vLabsFin.addElement( "Não Financeiro" );
		vLabsFin.addElement( "Ambos" );
		vValsFin.addElement( "S" );
		vValsFin.addElement( "N" );
		vValsFin.addElement( "A" );
		rgFinanceiro = new JRadioGroup<String, String>( 3, 1, vLabsFin, vValsFin );
		rgFinanceiro.setVlrString( "S" );
		
		vLabsEmit.addElement( "Emitidos" );
		vLabsEmit.addElement( "Não emitidos" );
		vLabsEmit.addElement( "Ambos" );
		vValsEmit.addElement( "S" );
		vValsEmit.addElement( "N" );
		vValsEmit.addElement( "A" );
		rgEmitidos = new JRadioGroup<String, String>( 3, 1, vLabsEmit, vValsEmit );
		rgEmitidos.setVlrString( "A" );


		JLabelPad lbLinha = new JLabelPad();
		lbLinha.setBorder( BorderFactory.createEtchedBorder() );
		JLabelPad lbLinha2 = new JLabelPad();
		lbLinha2.setBorder( BorderFactory.createEtchedBorder() );
		JLabelPad lbLinha3 = new JLabelPad();
		lbLinha3.setBorder( BorderFactory.createEtchedBorder() );
		JLabelPad lbLinha4 = new JLabelPad();
		lbLinha4.setBorder( BorderFactory.createEtchedBorder() );

		txtDataini.setVlrDate( new Date() );
		txtDatafim.setVlrDate( new Date() );
		adic( new JLabelPad( "Periodo:" ), 7, 5, 100, 20 );
		adic( lbLinha, 60, 15, 210, 2 );
		adic( new JLabelPad( "Mês" ), 10, 25, 40, 20 );
		adic( txtMesini, 10, 45, 40, 20 );
		adic( new JLabelPad( "/" ), 54, 45, 5, 20 );
		adic( new JLabelPad( "Ano" ), 64, 25, 40, 20 );
		adic( txtAnoini, 64, 45, 60, 20 );
		adic( new JLabelPad( "Mês" ), 140, 25, 40, 20 );
		adic( txtMesfim, 140, 45, 40, 20 );
		adic( new JLabelPad( "/" ), 184, 45, 5, 20 );
		adic( new JLabelPad( "Ano" ), 194, 25, 40, 20 );
		adic( txtAnofim, 194, 45, 60, 20 );
		adic( new JLabelPad( "De:" ), 10, 70, 97, 20 );
		adic( txtDataini, 32, 70, 97, 20 );
		adic( new JLabelPad( "Até:" ), 140, 70, 100, 20 );
		adic( txtDatafim, 170, 70, 100, 20 );
		adic( lbLinha3, 7, 117, 273, 2 );
		adic( new JLabelPad( "Cód.grupo" ), 7, 125, 240, 20 );
		adic( txtCodGrup, 7, 145, 90, 20 );
		adic( new JLabelPad( "Descrição do grupo" ), 100, 125, 240, 20 );
		adic( txtDescGrup, 100, 145, 180, 20 );
		adic( new JLabelPad( "Cód.marca" ), 7, 165, 240, 20 );
		adic( txtCodMarca, 7, 185, 90, 20 );
		adic( new JLabelPad( "Descrição da Marca" ), 100, 165, 240, 20 );
		adic( txtDescMarca, 100, 185, 180, 20 );
		adic( new JLabelPad( "Cód.prod." ), 7, 205, 240, 20 );
		adic( txtCodProd, 7, 225, 90, 20 );
		adic( new JLabelPad( "Descrição do produto" ), 100, 205, 240, 20 );
		adic( txtDescProd, 100, 225, 180, 20 );
		
		adic( new JLabelPad( "Cód.comiss." ), 7, 245, 200, 20 );
		adic( txtCodVend, 7, 265, 70, 20 );
		adic( new JLabelPad( "Nome do comissionado" ), 80, 245, 200, 20 );
		adic( txtDescVend, 80, 265, 200, 20 );
		adic( lbOrdem, 7, 290, 80, 15 );
		adic( rgOrdem, 7, 315, 273, 30 );
		adic( rgFaturados, 		  7,	355, 	120, 	75 );
		adic( rgFinanceiro, 	158, 	355, 	120, 	75 );		
		adic( rgEmitidos,		  7,	435, 	120, 	70 );
//		adic( new JLabelPad("Tipo de impressão"), 158, 435, 120, 20);
		//adic( rgTipoimp, 158, 455, 120, 50);
		adic( cbVendaCanc, 7, 505, 200, 20 );
		
		setParamIni();
		
		txtAnoini.addFocusListener( this );
		txtMesini.addFocusListener( this );
		txtAnofim.addFocusListener( this );
		txtMesfim.addFocusListener( this );
		txtDataini.setAtivo( false );
		txtDatafim.setAtivo( false );
		btExportXLS.setEnabled( true );

	}

	private boolean comRef() {

		boolean bRetorno = false;
		String sSQL = "SELECT USAREFPROD FROM SGPREFERE1 WHERE CODEMP=? AND CODFILIAL=?";
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement( sSQL );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "SGPREFERE1" ) );
			rs = ps.executeQuery();
			if ( rs.next() ) {
				if ( rs.getString( "UsaRefProd" ).trim().equals( "S" ) )
					bRetorno = true;
			}
			con.commit();
		} catch ( SQLException err ) {
			Funcoes.mensagemErro( this, "Erro ao carregar a tabela PREFERE1!\n" + err.getMessage(), true, con, err );
		}
		return bRetorno;
	}

	public void imprimir( TYPE_PRINT visualizar ) {
		if ( txtDatafim.getVlrDate().before( txtDataini.getVlrDate() ) ) {
			Funcoes.mensagemInforma( this, "Data final menor que a data inicial!" );
			return;
		}
		Vector<String> meses = Funcoes.getMeses( txtDataini.getVlrDate(), txtDatafim.getVlrDate() );
		if ( meses.size()>12 && visualizar!=TYPE_PRINT.EXPORT ) {
			Funcoes.mensagemInforma( this
					, "Não é permitido a visualização ou impressão do relatório com período superior a 12 meses!\n" );
			return;
		}

		try {

			StringBuilder filtros = new StringBuilder();
			filtros.append( "Período de ");
			filtros.append( txtDataini.getVlrString());
			filtros.append( " até " );
			filtros.append( txtDatafim.getVlrString() );

			int codemp = Aplicativo.iCodEmp;
			int codfilialmp = ListaCampos.getMasterFilial( "EQMOVPROD" );
			int codfilialpd = ListaCampos.getMasterFilial( "EQPRODUTO" );
			int codfilialvd = ListaCampos.getMasterFilial( "VDVENDA" );
			int codfilialva = ListaCampos.getMasterFilial( "VDVENDEDOR" );
			int codfilialgp = ListaCampos.getMasterFilial( "EQGRUPO" );
			int codfilialmc = ListaCampos.getMasterFilial( "EQMARCA" );
			int codvend = txtCodVend.getVlrInteger();
			Date dataini = txtDataini.getVlrDate();
			Date datafim = txtDatafim.getVlrDate();
			String codgrup = txtCodGrup.getVlrString();
			String codmarca = txtCodMarca.getVlrString();
			int codprod = txtCodProd.getVlrInteger();
			String faturado = rgFaturados.getVlrString();
			String financeiro = rgFinanceiro.getVlrString();
			String cancelado = cbVendaCanc.getVlrString();
			String ordem = rgOrdem.getVlrString();
			ResultSet rs = null;
			if (visualizar==TYPE_PRINT.EXPORT || "G".equals( rgTipoimp.getVlrString() )) {
				rs = getResultSetReport( codemp, codfilialmp, codfilialpd, codprod, codfilialvd, dataini, datafim, codfilialgp
						, codgrup, codfilialmc, codmarca, codfilialva, codvend, faturado, financeiro
						, cancelado, ordem, filtros, meses, visualizar );
			}

			if (visualizar==TYPE_PRINT.EXPORT) {
				if (btExportXLS.execute(rs, getTitle())) {
					Funcoes.mensagemInforma( this, "Arquivo exportado com sucesso !" );
				}
			} else {
				if ("G".equals(rgTipoimp.getVlrString())) {
					imprimirGrafico( visualizar, rs, filtros, meses );
				} else {
					imprimirTexto( codemp, codfilialmp, codfilialpd, codprod, codfilialvd, dataini, datafim, codfilialgp
							, codgrup, codfilialmc, codmarca, codfilialva, codvend, faturado, financeiro
							, cancelado, ordem, filtros, meses, visualizar );
				}
			}
			rs.close();
			//ps.close();
			con.commit();

		} catch ( SQLException err ) {
			Funcoes.mensagemErro( this, "Erro consulta tabela de vendas!\n" + err.getMessage(), true, con, err );
		} finally {
			System.gc();
		}
	}

	private void imprimirGrafico( final TYPE_PRINT bVisualizar, final ResultSet rs, final StringBuilder filtros, Vector<String> meses ) {

		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put( "MESES", meses );
		
		FPrinterJob dlGr = null;

		dlGr = new FPrinterJob( "relatorios/mediavendasitem.jasper", "Média de vendas por item", filtros.toString(), rs, params, this );

		if ( bVisualizar == TYPE_PRINT.VIEW ) {
			dlGr.setVisible( true );
		}
		else {
			try {
				JasperPrintManager.printReport( dlGr.getRelatorio(), true );
			} catch ( Exception err ) {
				Funcoes.mensagemErro( this, "Erro na impressão de relatório!\n" + err.getMessage(), true, con, err );
			}
		}
	}

	private ResultSet getResultSetReport(Integer codemp, Integer codfilialmp, Integer codfilialpd, Integer codprod
			, Integer codfilialvd, Date dataini, Date datafim 
			, Integer codfilialgp, String codgrup
			, Integer codfilialmc, String codmarca
			, Integer codfilialva, Integer codvend
			, String faturado, String financeiro, String cancelado
			, String ordem, StringBuilder filtros, Vector<String> meses, TYPE_PRINT visualizar  ) throws SQLException {

		ResultSet result = null;
		StringBuilder sql = new StringBuilder();
		sql.append( "select p.codemp, p.codfilial, p.codprod, p.refprod, p.descprod, p.sldliqprod " );
		sql.append( ", (select first 1 dtmovprod from eqmovprod mp, eqtipomov tmm " );
		sql.append( " where mp.codemp=? and mp.codfilial=? " );
		sql.append( " and mp.codemppd=p.codemp and mp.codfilialpd=p.codfilial " );
		sql.append( " and mp.codprod=p.codprod and mp.tipomovprod='E' and mp.codcompra is not null " );
		sql.append( " and tmm.codemp=mp.codemptm and tmm.codfilial=mp.codfilialtm and tmm.codtipomov=mp.codtipomov ");
		sql.append( " and tmm.somavdtipomov='S' ");
		sql.append( " order by mp.dtmovprod desc, mp.codmovprod desc " );
		sql.append( " ) dtultcpprod " );
		sql.append( " , (select first 1 qtdmovprod from eqmovprod mp, eqtipomov tmm " );
		sql.append( " where mp.codemp=? and mp.codfilial=? " );
		sql.append( " and mp.codemppd=p.codemp and mp.codfilialpd=p.codfilial " );
		sql.append( " and mp.codprod=p.codprod and mp.tipomovprod='E' and mp.codcompra is not null " );
		sql.append( " and tmm.codemp=mp.codemptm and tmm.codfilial=mp.codfilialtm and tmm.codtipomov=mp.codtipomov ");
		sql.append( " and tmm.somavdtipomov='S' ");
		sql.append( "  order by mp.dtmovprod desc, mp.codmovprod desc " );
		sql.append( " ) qtdultcpprod " );
		for ( int i = 0; i < 12; i++ ) {
			if (i<meses.size()) {
				String anomes = meses.elementAt( i );
				String ano = anomes.substring( 0, 4 );
				String mes = anomes.substring( 4 );
				sql.append( ", sum((case when extract(month from v.dtemitvenda)=" );
				sql.append( mes );
				sql.append( " and extract(year from v.dtemitvenda)=" );
				sql.append( ano );
				sql.append( " then iv.qtditvenda else 0 end)) qtd" );
				if (visualizar==TYPE_PRINT.EXPORT) {
					sql.append(mes);
					//sql.append("_");
					sql.append(ano);
				} else {
					sql.append( i + 1 );
				}
			} else {
				sql.append(", cast(null as decimal(15,5)) qtd");
				sql.append( i + 1 );
			}
		}
		sql.append( ", sum(iv.qtditvenda) subtotal ");
		sql.append( ", sum(iv.qtditvenda) / ");
		sql.append(meses.size());
		sql.append(" qtdmedia ");
		sql.append( " from eqproduto p ");
		sql.append( " inner join vditvenda iv on ");
		sql.append( " iv.codemppd=p.codemp and iv.codfilialpd=p.codfilial and iv.codprod=p.codprod ");
		sql.append( " inner join vdvenda v on ");
		sql.append( " v.codemp=iv.codemp and v.codfilial=iv.codfilial and v.tipovenda=iv.tipovenda and v.codvenda=iv.codvenda ");
		sql.append( " inner join eqtipomov tm on ");
		sql.append( " tm.codemp=v.codemptm and tm.codfilial=v.codfilialtm and tm.codtipomov=v.codtipomov ");
		sql.append( " inner join fnplanopag pp on ");
		sql.append( " pp.codemp=v.codemppg and pp.codfilial=v.codfilialpg and pp.codplanopag=v.codplanopag ");
		sql.append( " where p.codemp=? and p.codfilial=? ");
		if ( codprod!=0 ) {
			sql.append( " and p.codprod=? ");
			filtros.append( ", cód.produto: " );
			filtros.append( codprod );
		}
		sql.append( " and v.codemp=? and v.codfilial=? and v.dtemitvenda between ? and ? ");
		sql.append( " and v.flag in ");
		sql.append( AplicativoPD.carregaFiltro( con, org.freedom.library.swing.frame.Aplicativo.iCodEmp ) );
		if ( "S".equalsIgnoreCase( faturado ) ) {
			sql.append( " and tm.fiscaltipomov='S' " );
			filtros.append( ", faturados" );
		}
		else if ( "N".equalsIgnoreCase( faturado ) ) {
			sql.append( " and tm.fiscaltipomov='N' " );
			filtros.append( ", não faturados" );
		}
		if ( "S".equalsIgnoreCase( financeiro ) ) {
			sql.append( " and tm.somavdtipomov='S' and pp.parcplanopag>0 " );
			filtros.append( ", financeiros" );
		}
		else if ( "N".equalsIgnoreCase( financeiro ) ) {
			sql.append( " and tm.somavdtipomov='N' " );
			filtros.append( ", não financeiros" );
		}
		if ("N".equalsIgnoreCase( cancelado )) {
			sql.append( " and substring(v.statusvenda from 1 for 1) not in ('C','N') ");
		}
		if (!"".equals( codgrup.trim() ) ) {
			sql.append( " and p.codempgp=? and p.codfilialgp=? and p.codgrup like ? ");
			filtros.append( ", cód.grupo: " );
			filtros.append( codgrup );
		}
		if (!"".equals( codmarca.trim() ) ) {
			sql.append( " and p.codempmc=? and p.codfilialmc=? and p.codmarca = ? ");
			filtros.append( ", cód.marca: " );
			filtros.append( codmarca );
		}
		if ( codvend!=0 ) {
			sql.append( " and v.codempvd=? and c.codfilialvd=? and c.codvend=? ");
			filtros.append( ", cód.comissioando: " );
			filtros.append( codvend );
		}
		sql.append( " group by p.codemp, p.codfilial, p.codprod, p.refprod, p.descprod, p.sldliqprod ");
		sql.append( " order by ");
		if ("D".equals(ordem)) {
			sql.append(" p.descprod, p.codprod ");
		} else {
			sql.append(" p.codprod ");
		}
		PreparedStatement ps = con.prepareStatement( sql.toString() );
		int param = 1;
		ps.setInt( param++, codemp );
		ps.setInt( param++, codfilialmp );
		ps.setInt( param++, codemp );
		ps.setInt( param++, codfilialmp );
		ps.setInt( param++, codemp );
		ps.setInt( param++, codfilialpd );
		if (codprod!=0) {
			ps.setInt( param++, codprod );
		}
		ps.setInt( param++, codemp );
		ps.setInt( param++, codfilialvd );
		ps.setDate( param++, Funcoes.dateToSQLDate( dataini ) );
		ps.setDate( param++, Funcoes.dateToSQLDate( datafim ) );
		if (!"".equals( codgrup.trim() ) ) {
			ps.setInt( param++, codemp );
			ps.setInt( param++, codfilialgp );
			ps.setString( param++, codgrup.trim()+"%" );
		}
		if (!"".equals( codmarca.trim() ) ) {
			ps.setInt( param++, codemp );
			ps.setInt( param++, codfilialmc );
			ps.setString( param++, codmarca.trim()+"%" );
		}
		if ( codvend!=0 ) {
			ps.setInt( param++, codemp );
			ps.setInt( param++, codfilialva );
			ps.setInt( param++, codvend );
		}

		ResultSet rs = ps.executeQuery();

		result = ps.executeQuery();
		return result;
	}

	public void imprimirTexto( Integer codemp, Integer codfilialmp, Integer codfilialpd, Integer codprod
			, Integer codfilialvd, Date dataini, Date datafim 
			, Integer codfilialgp, String codgrup
			, Integer codfilialmc, String codmarca
			, Integer codfilialva, Integer codvend
			, String faturado, String financeiro, String cancelado
			, String ordem, StringBuilder filtros, Vector<String> meses, TYPE_PRINT visualizar ) throws SQLException {

		ImprimeOS imp = new ImprimeOS( "", con );
		int linPag = imp.verifLinPag() - 1;
		String fcodprod = "CODPROD"; 
		if ( comRef() ) {
			fcodprod = "REFPROD";
		}
		int pos = 0;
		int numitens = 0;
		int nummeses = 12;
		double[] qtd = new double[nummeses]; 
		imp.limpaPags();
		imp.setTitulo( "Relatório de media de vendas por item" );
		imp.addSubTitulo( "RELATORIO DE MEDIAS DE VENDAS POR ITEM" );
		if ( filtros.length() > 0 ) {
			imp.addSubTitulo( filtros.toString() );
		}
		ResultSet rs = getResultSetReport( codemp, codfilialmp, codfilialpd, codprod, codfilialvd, dataini, datafim, codfilialgp
				, codgrup, codfilialmc, codmarca, codfilialva, codvend, faturado, financeiro
				, cancelado, ordem, filtros, meses, visualizar );

		while ( rs.next() ) {
			if ( imp.pRow() == 0 ) {
				imp.montaCab();
				imp.impCab( 136, true );
				imp.say( imp.pRow() + 0, 0, "" + imp.comprimido() );
				imp.say( imp.pRow() + 0, 0, "|" + StringFunctions.replicate( "-", 133 ) + "|" );
				imp.say( imp.pRow() + 1, 0, "" + imp.comprimido() );
				imp.say( imp.pRow() + 0, 0, "| Cod. Prod     " );
				imp.say( imp.pRow() + 0, 16, "  Desc. Produto                            " );
				imp.say( imp.pRow() + 0, 59, "| Estoque  " );
				imp.say( imp.pRow() + 0, 70, "| Dt.Ult.Cp. " );
				imp.say( imp.pRow() + 0, 83, "| Q.Ult.Cp |" );
				imp.say( imp.pRow() + 0, 135, "|" );
				imp.say( imp.pRow() + 1, 0, "|" + StringFunctions.replicate( "-", 133 ) + "|" );
/*				imp.say( imp.pRow() + 1, 0, sSubCab );
				imp.say( imp.pRow() + 0, 135, "|" );
				imp.say( imp.pRow() + 1, 0, "" + imp.comprimido() );
				imp.say( imp.pRow() + 0, 0, "|" + StringFunctions.replicate( "-", 133 ) + "|" );
				*/
			}
			imp.say( imp.pRow() + 1, 0, "" + imp.comprimido() );
			imp.say( imp.pRow() + 0, 0, "| " + Funcoes.copy( rs.getString( fcodprod ), 0, 13 ) + " " );
			imp.say( imp.pRow() + 0, 16, "  " + Funcoes.copy( rs.getString( "descprod" ), 0, 40 ) + " " );
			imp.say( imp.pRow() + 0, 59, "| " + Funcoes.strDecimalToStrCurrency( 8, 0, rs.getString( "sldliqprod" ) ) + " " );
			if ( rs.getDate( "dtultcpprod" ) != null ) {
				imp.say( imp.pRow() + 0, 70, "| " + StringFunctions.sqlDateToStrDate( rs.getDate( "dtultcpprod" ) ) + " " );
			}
			else {
				imp.say( imp.pRow() + 0, 70, "|    N/C   " );
			}
			
			imp.say( imp.pRow() + 0, 83, "| " + Funcoes.strDecimalToStrCurrency( 8, 0, "" + rs.getDouble( "qtdultcpprod" ) ) + "  " );
			imp.say( imp.pRow() + 0, 135, "|" );
			imp.say( imp.pRow() + 1, 0, "|" + StringFunctions.replicate( "-", 133 ) + "|" );
			imp.say( imp.pRow() + 1, 0, "" + imp.comprimido() );
			
			double dSomaItem = 0;
			double dMediaItem = 0;

			pos = 0;
			
			for ( int i = 0; i < nummeses; i++ ) {
				
				imp.say( imp.pRow() + 0, pos, "| " + Funcoes.strDecimalToStrCurrency( 7, 0, rs.getString( "qtd" + (i+1) ) != null ? rs.getString( "qtd" + (i+1) ) : "0" ) + " " );
				qtd[ i ] += rs.getDouble( "qtd" + (i+1) );
				dSomaItem += rs.getDouble( "qtd" + (i+1) );
				pos += 10;
				
			}
			
			imp.say( imp.pRow() + 0, pos, " | " + Funcoes.strDecimalToStrCurrency( 7, 0, rs.getString( "qtdmedia" ) ) );
			imp.say( imp.pRow() + 0, 94, "|" );
			imp.say( imp.pRow() + 0, 135, "|" );
			imp.say( imp.pRow() + 1, 0, "" + imp.comprimido() );
			imp.say( imp.pRow() + 0, 0, "|" + StringFunctions.replicate( "-", 133 ) + "|" );
			
			if ( imp.pRow() >= ( linPag - 1 ) ) {
				imp.incPags();
				imp.eject();
			}
			
			numitens++;
		}
		imp.say( imp.pRow() + 1, 0, "" + imp.comprimido() );
		imp.say( imp.pRow() + 0, 0, "+" + StringFunctions.replicate( "=", 133 ) + "+" );
		imp.say( imp.pRow() + 1, 0, "" + imp.comprimido() );
		imp.say( imp.pRow() + 0, 0, "| T:" );

		pos = 4;
		for ( int i = 0; i < nummeses; i++ ) {
			BigDecimal bVal = new BigDecimal( qtd[ i ] );
			bVal = bVal.setScale( 1 );
			imp.say( imp.pRow() + 0, pos, "| " + Funcoes.strDecimalToStrCurrency( 7, 0, "" + bVal ) );
			pos += 10;
		}

		imp.say( imp.pRow() + 0, 135, "|" );
		imp.say( imp.pRow() + 1, 0, "" + imp.comprimido() );
		imp.say( imp.pRow() + 0, 0, "+" + StringFunctions.replicate( "=", 133 ) + "+" );

		imp.eject();

		imp.fechaGravacao();

		con.commit();


		if ( visualizar==TYPE_PRINT.VIEW ) {
			imp.preview( this );
		}
		else {
			imp.print();
		}
	}

	public void setConexao( DbConnection cn ) {

		super.setConexao( cn );
		lcGrup.setConexao( con );
		lcMarca.setConexao( con );
		lcVend.setConexao( con );
		lcProd.setConexao( con );

	}
	
	public void focusGained( FocusEvent e ) {

	}

	private void setDataini() {
		
		txtDataini.setVlrDate( Funcoes.encodeDate( txtAnoini.getVlrInteger(), txtMesini.getVlrInteger(), 1 ) );
	}

	private void setParamIni() {
		
		Calendar cal = Calendar.getInstance();
		cal.add( Calendar.MONTH, -1 );
		Date datafim = cal.getTime();
		cal.add( Calendar.MONTH, +1 );
		cal.add( Calendar.YEAR, -1 );
		Date dataini = cal.getTime();
		txtMesfim.setVlrInteger( Funcoes.getMes( datafim ) );
		txtAnofim.setVlrInteger( Funcoes.getAno( datafim ) );
		txtMesini.setVlrInteger( Funcoes.getMes( dataini ) );
		txtAnoini.setVlrInteger( Funcoes.getAno( dataini ) );
		setDataini();
		setDatafim();
	}
	
	private void setDatafim() {
		
		Calendar cal = Calendar.getInstance();
		cal.setTime( Funcoes.encodeDate( txtAnofim.getVlrInteger(), txtMesfim.getVlrInteger()+1, 1 ) );
		cal.add( Calendar.DAY_OF_MONTH, -1 );
		txtDatafim.setVlrDate( cal.getTime() );
	}

	public void focusLost( FocusEvent e ) {

		if ( e.getSource()==txtAnoini || e.getSource()==txtMesini ) {
			setDataini();
		} else if ( e.getSource()==txtAnofim || e.getSource()==txtMesfim ) {
			setDatafim();
		}
		
	}

}
