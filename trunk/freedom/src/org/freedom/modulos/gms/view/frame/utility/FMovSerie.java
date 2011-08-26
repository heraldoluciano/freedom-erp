package org.freedom.modulos.gms.view.frame.utility;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JScrollPane;

import net.sf.jasperreports.engine.JasperPrintManager;

import org.freedom.bmps.Icone;
import org.freedom.infra.functions.StringFunctions;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.functions.Funcoes;
import org.freedom.library.persistence.GuardaCampo;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.component.JButtonPad;
import org.freedom.library.swing.component.JPanelPad;
import org.freedom.library.swing.component.JTablePad;
import org.freedom.library.swing.component.JTextFieldFK;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.frame.Aplicativo;
import org.freedom.library.swing.frame.FPrinterJob;
import org.freedom.library.swing.frame.FRelatorio;
import org.freedom.modulos.gms.business.object.TipoMov;

/**
 * Acompanhamento de Numero de Series.
 * 
 * @version 1.0 21/04/2011
 * @author ffrizzo
 * 
 */
public class FMovSerie extends FRelatorio {

	private static final long serialVersionUID = 1L;

	private Container cTela = null;

	private JPanelPad pnCli = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private JPanelPad pinCab = new JPanelPad( 560, 70 );

	private JTablePad tab = new JTablePad();

	private JScrollPane spnTab = new JScrollPane( tab );

	private JTextFieldPad txtDataini = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtDatafim = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtCodProd = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtDescProd = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );

	private JTextFieldPad txtNumSerie = new JTextFieldPad( JTextFieldPad.TP_STRING, 30, 0 );

	private JTextFieldPad txtRefProd = new JTextFieldPad( JTextFieldPad.TP_STRING, 20, 0 );

	private JButtonPad btExec = new JButtonPad( Icone.novo( "btExecuta.gif" ) );

	private ListaCampos lcProd = new ListaCampos( this );
	
	private ListaCampos lcProd2 = new ListaCampos( this );

	private ListaCampos lcMovSerie = new ListaCampos( this );

	private PreparedStatement ps;

	private ResultSet rs;
	
	private HashMap<String, Object> prefere = null;
	
	private final ImageIcon img_mov_saida = Icone.novo( "mov_saida.png" );

	private final ImageIcon img_mov_entrada = Icone.novo( "mov_entrada.png" );
	
	private final ImageIcon img_mov_nula = Icone.novo( "mov_nula.png" );
	
	private ImageIcon img_mov = null;
	
	public FMovSerie() {

		setTitulo( "Mov. Numero de Série" );

		setAtribos( 10, 10, 750, 400 );

		txtCodProd.setRequerido( false );
		txtDataini.setRequerido( true );
		txtDatafim.setRequerido( true );

		cTela = getTela();
		cTela.add( pnCli, BorderLayout.CENTER );
		pnCli.add( pinCab, BorderLayout.NORTH );
		pnCli.add( spnTab, BorderLayout.CENTER );

		setPainel( pinCab );

		adic( txtDataini, 7, 25, 90, 20, "Data inicial" );
		adic( txtDatafim, 100, 25, 100, 20, "Data final" );

		adic( txtNumSerie, 203, 25, 120, 20, "Numero de Série" );
		
		adic( txtDescProd, 399, 25, 260, 20, "Descrição do produto" );

		adic( btExec, 675, 15, 30, 30 );

		btExec.setToolTipText( "Executa consulta." );
		btExec.addActionListener( this );

		Calendar cPeriodo = Calendar.getInstance();
		txtDatafim.setVlrDate( cPeriodo.getTime() );
		cPeriodo.set( Calendar.YEAR, cPeriodo.get( Calendar.YEAR ) - 1 );
		txtDataini.setVlrDate( cPeriodo.getTime() );

		tab.adicColuna( "Data" );
		tab.adicColuna( "Tipo" );
		tab.adicColuna( "Cliente/Fornecedor" );
		tab.adicColuna( "Doc" );
		tab.adicColuna( "E/S" );
		tab.adicColuna( "Num Série" );
		tab.adicColuna( "Produto" );
		
		tab.setTamColuna( 75	, 0 );
		tab.setTamColuna( 65	, 1 );
		tab.setTamColuna( 200	, 2 );
		tab.setTamColuna( 70	, 3 );
		tab.setTamColuna( 30	, 4 );
		tab.setTamColuna( 80	, 5 );
		tab.setTamColuna( 200	, 6 );
		
		tab.setRowHeight( 21 );

		this.montaListaCampos();
		
		txtNumSerie.addKeyListener( this );
		txtRefProd.addKeyListener( this );
		txtCodProd.addKeyListener( this );
		
	}
	
	private HashMap<String, Object> getPrefere( DbConnection con ) {

		HashMap<String, Object> retorno = new HashMap<String, Object>();
		boolean[] bRetorno = new boolean[ 1 ];
		StringBuffer sql = new StringBuffer();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {

			sql.append( "SELECT P1.USAREFPROD FROM SGPREFERE1 P1 " );
			sql.append( "WHERE P1.CODEMP=? AND P1.CODFILIAL=? " );

			bRetorno[ 0 ] = false;
			ps = con.prepareStatement( sql.toString() );

			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "SGPREFERE1" ) );

			rs = ps.executeQuery();

			if ( rs.next() ) {
				retorno.put( "USAREFPROD", new Boolean( rs.getString( "USAREFPROD" ).trim().equals( "S" ) ) );
			}
			else {
				retorno.put( "USAREFPROD", new Boolean( false ) );
			}

			rs.close();
			ps.close();

			con.commit();

		} catch ( SQLException err ) {
			err.printStackTrace();
			Funcoes.mensagemErro( this, "Erro ao carregar a tabela PREFERE1!\n" + err.getMessage(), true, con, err );
		} finally {
			ps = null;
			rs = null;
			sql = null;
		}
		return retorno;
	}

	private void montaListaCampos() {

		lcProd.add( new GuardaCampo( txtCodProd, "CodProd", "Cód.prod.", ListaCampos.DB_PK, false ) );
		lcProd.add( new GuardaCampo( txtRefProd, "RefProd", "Referência do produto", ListaCampos.DB_SI, false ) );
		lcProd.add( new GuardaCampo( txtDescProd, "DescProd", "Descrição do produto", ListaCampos.DB_SI, false ) );
		txtCodProd.setTabelaExterna( lcProd, null );
		txtCodProd.setNomeCampo( "CodProd" );
		txtCodProd.setFK( true );
		lcProd.setReadOnly( true );
		lcProd.montaSql( false, "PRODUTO", "EQ" );
		
		lcProd2.add( new GuardaCampo( txtRefProd, "RefProd", "Referência do produto", ListaCampos.DB_PK, false ) );
		lcProd2.add( new GuardaCampo( txtCodProd, "CodProd", "Cód.prod.", ListaCampos.DB_SI, false ) );
		
		lcProd2.add( new GuardaCampo( txtDescProd, "DescProd", "Descrição do produto", ListaCampos.DB_SI, false ) );
		txtRefProd.setTabelaExterna( lcProd2, null );
		txtRefProd.setNomeCampo( "RefProd" );
		txtRefProd.setFK( true );
		lcProd2.setReadOnly( true );
		lcProd2.montaSql( false, "PRODUTO", "EQ" );
		
		

	}

	private void executar() {

		try {
			ResultSet rs = this.getResultSet();

			tab.limpa();
			int iLinha = 0;

			while ( rs.next() ) {

				tab.adicLinha();

				String tipoMovSerie = rs.getString( "TIPOMOVSERIE" ).trim();
				String razcli = "";

				if ( "1".equals( tipoMovSerie ) ) {
					tipoMovSerie = "Entrada";
					img_mov = img_mov_entrada;
				}
				else if ( "0".equals( tipoMovSerie ) ) {
					tipoMovSerie = "Sem Movimento";
					img_mov = img_mov_nula;
				}
				else if ( "-1".equals( tipoMovSerie ) ) {
					tipoMovSerie = "Saída";
					img_mov = img_mov_saida;
				}

				tab.setValor( StringFunctions.sqlDateToStrDate( rs.getDate( "DTMOVSERIE" ) ), iLinha, 0 );

				String tipomov = rs.getString( "tipomov" );
				
				if ( rs.getInt( "ticket" ) > 0 ) {
					tipomov = "Coleta";
					razcli = rs.getString( "razcli_coleta" );
					
				}
				else if ( rs.getInt( "codvenda" ) > 0 ) {
					tipomov = "Venda";
					razcli = rs.getString( "razcli_venda" );
					
				}
				else if ( rs.getInt( "codcompra" ) > 0 ) {
					tipomov = "Compra";
					razcli = rs.getString( "razfor_comp" );
					
				}
				else {

					tipomov = TipoMov.getDescTipo( tipomov );

				}

				tab.setValor( tipomov, iLinha, 1 );
				
				tab.setValor( razcli, iLinha, 2 );

				tab.setValor( rs.getInt( "docmovserie" ), iLinha, 3 );

				tab.setValor( img_mov, iLinha, 4 );
				
				tab.setValor( rs.getString( "NUMSERIE" ), iLinha, 5 );
				
				tab.setValor( rs.getString( "descprod" ), iLinha, 6 );
				

				iLinha++;
			}

			rs.close();
			ps.close();
			con.commit();

		} catch ( SQLException err ) {
			err.printStackTrace();
			Funcoes.mensagemErro( this, "Erro ao carrregar a tabela MOVSERIE !\n" + err.getMessage(), true, con, err );
		}
	}

	@ Override
	public void imprimir( boolean bVisualizar ) {

		try {
			rs = this.getResultSet();

			StringBuilder filtros = new StringBuilder();
			filtros.append( "De " + Funcoes.dateToStrDate( txtDataini.getVlrDate() ) + " " );
			filtros.append( "Até " + Funcoes.dateToStrDate( txtDatafim.getVlrDate() ) );

			FPrinterJob dlGr = null;
			HashMap<String, Object> hParam = new HashMap<String, Object>();

			hParam.put( "CODEMP", Aplicativo.iCodEmp );
			hParam.put( "CODFILIAL", ListaCampos.getMasterFilial( "CPCOMPRA" ) );
			hParam.put( "RAZAOEMP", Aplicativo.empresa.toString() );

			dlGr = new FPrinterJob( "relatorios/RelMovNumSerie.jasper", "Relatório acompanhamento de numero de Série", filtros.toString(), rs, hParam, this );

			if ( bVisualizar ) {
				dlGr.setVisible( true );
			}
			else {
				try {
					JasperPrintManager.printReport( dlGr.getRelatorio(), true );
				} catch ( Exception err ) {
					Funcoes.mensagemErro( this, "Erro na impressão de relatório Compras Geral!" + err.getMessage(), true, con, err );
				}
			}

			rs.close();
			ps.close();
			con.commit();
		} catch ( SQLException err ) {
			Funcoes.mensagemErro( this, "Erro ao carrregar a tabela MOVSERIE !\n" + err.getMessage(), true, con, err );
		}
	}

	public ResultSet getResultSet() throws SQLException {

		// Validando a data do filtro
		if ( txtDatafim.getVlrDate().before( txtDataini.getVlrDate() ) ) {
			Funcoes.mensagemInforma( this, "Data final maior que a data inicial!" );
			return null;
		}

		Integer codProd = txtCodProd.getVlrInteger().intValue();
		String numSerie = txtNumSerie.getVlrString();

		StringBuilder sql = new StringBuilder();

		sql.append( "select p.descprod, ms.codprod, ms.numserie, ms.tipomovserie, " );
		//sql.append( "ms.dtmovserie, ms.docmovserie, tm.tipomov, ms.ticket, ms.codvenda, ms.codcompra, " );
		sql.append( "ms.dtmovserie, vd.docvenda as docmovserie, tm.tipomov, ms.ticket, ms.codvenda, ms.codcompra, " );
		
		sql.append( "clr.razcli razcli_coleta, clv.razcli razcli_venda, frc.razfor razfor_comp " );
		
		sql.append( "from eqmovserie ms " );

		sql.append( "left outer join eqproduto p on " );
		sql.append( "(p.codemp = ms.codemppd and p.codfilial = ms.codfilialpd and p.codprod = ms.codprod) " );

		sql.append( "left outer join eqtipomov tm on " );
		sql.append( "(tm.codemp = ms.codemptm and tm.codfilial = ms.codfilialtm and tm.codtipomov = ms.codtipomov) " );

		sql.append( "left outer join eqrecmerc rc on " );
		sql.append( "(rc.codemp = ms.codemprc and rc.codfilial = ms.codfilialrc and rc.ticket = ms.ticket) " );

		sql.append( "left outer join vdcliente clr on " );
		sql.append( "(clr.codemp=rc.codempcl and clr.codfilial=rc.codfilialcl and clr.codcli=rc.codcli) " );
		
		sql.append( "left outer join vdvenda vd on " );
		sql.append( "(vd.codemp = ms.codempvd and vd.codfilial = ms.codfilialvd and vd.codvenda = ms.codvenda and vd.tipovenda=ms.tipovenda) " );

		sql.append( "left outer join vdcliente clv on " ); 
		sql.append( "(clv.codemp=vd.codempcl and clv.codfilial=vd.codfilialcl and clv.codcli=vd.codcli) " );

		sql.append( "left outer join cpcompra cp on " );
		sql.append( "(cp.codemp = ms.codempcp and cp.codfilial=ms.codfilialcp and cp.codcompra=ms.codcompra ) " );

		sql.append( "left outer join cpforneced frc on " ); 
		sql.append( "(frc.codemp=cp.codempfr and frc.codfilial=cp.codfilialfr and frc.codfor=cp.codfor) " );
		
		
		sql.append( "where ms.codemp = ? and ms.codfilial = ? " );

		if ( txtCodProd.getVlrInteger() > 0 ) {
			sql.append( " and ms.codemppd = ? and ms.codfilialpd = ? and ms.codprod = ? " );
		}

		if ( numSerie != null && !"".equals( numSerie.trim() ) ) {
			sql.append( " and ms.numserie = ?" );
		}

		sql.append( " and dtmovserie between ? and ? " );

		sql.append( " order by ms.dtmovserie " );

		ps = con.prepareStatement( sql.toString() );

		int iparam = 1;

		ps.setInt( iparam++, Aplicativo.iCodEmp );

		ps.setInt( iparam++, ListaCampos.getMasterFilial( "EQMOVSERIE" ) );

		if ( txtCodProd.getVlrInteger() > 0 ) {

			ps.setInt( iparam++, Aplicativo.iCodEmp );
			ps.setInt( iparam++, ListaCampos.getMasterFilial( "EQMOVSERIE" ) );
			ps.setInt( iparam++, codProd );

		}

		if ( numSerie != null && !"".equals( numSerie.trim() ) ) {
			ps.setString( iparam++, numSerie );
		}

		ps.setDate( iparam++, Funcoes.dateToSQLDate( txtDataini.getVlrDate() ) );
		ps.setDate( iparam++, Funcoes.dateToSQLDate( txtDatafim.getVlrDate() ) );

		rs = ps.executeQuery();

		return rs;

	}

	public void setConexao( DbConnection cn ) {

		super.setConexao( cn );
		
		prefere = getPrefere( cn );
		
		lcProd.setConexao( cn );
		lcProd2.setConexao( cn );
		
		if ( (Boolean) prefere.get( "USAREFPROD" ) ) {
			adic( txtRefProd, 326, 25, 70, 20, "Referência" );
		}
		else {
			adic( txtCodProd, 326, 25, 70, 20, "Cód.prod." );
		}
		
	}

	@ Override
	public void actionPerformed( ActionEvent evt ) {

		if ( evt.getSource() == btExec ) {
			this.executar();
		}
		else {
			super.actionPerformed( evt );
		}
	}
	
	public void keyPressed( KeyEvent kevt ) {

		if ( kevt.getSource() == txtNumSerie && (! txtNumSerie.getVlrString().equals( "" ))  && kevt.getKeyChar()==KeyEvent.VK_ENTER ) {
			
			btExec.doClick();
			
		}
		else if ( (kevt.getSource() == txtRefProd || kevt.getSource() == txtCodProd) && ( txtCodProd.getVlrInteger()>0 )  && kevt.getKeyChar()==KeyEvent.VK_ENTER ) {
		
			btExec.doClick();
			
		}
		
		
	}
	

}
