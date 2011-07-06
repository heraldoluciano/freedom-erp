/**
 * @version 06/07/2011 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 * 
 *         Projeto: Freedom <BR>
 * 
 *         Pacote: org.freedom.modulos.crm.frame.utility <BR>
 *         Classe: @(#)FConsisteCRM.java <BR>
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
 *         Consistência e geração automatizada de lançamentos
 * 
 */

package org.freedom.modulos.crm.view.frame.utility;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.Timer;
import org.freedom.bmps.Icone;
import org.freedom.infra.functions.ConversionFunctions;
import org.freedom.infra.functions.StringFunctions;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.functions.Funcoes;
import org.freedom.library.persistence.GuardaCampo;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.component.JButtonPad;
import org.freedom.library.swing.component.JCheckBoxPad;
import org.freedom.library.swing.component.JLabelPad;
import org.freedom.library.swing.component.JPanelPad;
import org.freedom.library.swing.component.JTablePad;
import org.freedom.library.swing.component.JTextFieldFK;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.frame.Aplicativo;
import org.freedom.library.swing.frame.FFilho;
import org.freedom.modulos.std.view.dialog.utility.DLChecaLFSaida;

import com.sun.org.apache.bcel.internal.generic.LCMP;

public class FConsisteCRM extends FFilho implements ActionListener {

	private static final long serialVersionUID = 1L;

	private JPanelPad pinCliente = new JPanelPad( 600, 110 );

	private JPanelPad pnGrid = new JPanelPad( JPanelPad.TP_JPANEL, new GridLayout( 2, 1 ) );

	private JTextFieldPad txtDataini = new JTextFieldPad( JTextFieldPad.TP_DATE, 0, 10 );

	private JTextFieldPad txtDatafim = new JTextFieldPad( JTextFieldPad.TP_DATE, 0, 10 );

	private JCheckBoxPad cbEntrada = new JCheckBoxPad( "Entrada", "S", "N" );

	private JCheckBoxPad cbSaida = new JCheckBoxPad( "Saida", "S", "N" );

	private JTablePad tab1 = new JTablePad();

	private JScrollPane spnTab1 = new JScrollPane( tab1 );

	private JTablePad tab2 = new JTablePad();

	private JScrollPane spnTab2 = new JScrollPane( tab2 );

	private JProgressBar pbAnd = new JProgressBar();

	private JButtonPad btVisual = new JButtonPad( Icone.novo( "btPesquisa.gif" ) );

	private JButtonPad btChecar = new JButtonPad( Icone.novo( "btExecuta.gif" ) );

	private JButtonPad btGerar = new JButtonPad( Icone.novo( "btGerar.gif" ) );
	
	private ListaCampos lcAtend = new ListaCampos( this, "AE" );

	private JTextFieldPad txtCodAtend = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	
	private JTextFieldFK txtNomeAtend = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private ListaCampos lcEmpregado = new ListaCampos( this, "EP" );
	
	private JTextFieldPad txtMatempr = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtNomeempr = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );
	
	private ListaCampos lcTurno = new ListaCampos( this, "TO" );
	
	private JTextFieldPad txtCodTurno = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtDescTurno = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );
	
	private Timer tim = null;

	private int iAnd = 0;

	private int iTotCompras = 0;

	private int iTotVendas = 0;

	private JLabelPad lbAnd = new JLabelPad( "Aguardando:" );

	private enum EColExped {
		DTEXPED, HORASEXPED
	}

	public FConsisteCRM() {
		super( false );
		montaListaCampos();
		montaTela();
	}

	private void montaTela() {
		setTitulo( "Gerar Informações Fiscais" );
		setAtribos( 10, 10, 670, 500 );

		btVisual.setToolTipText( "Visualizar" );
		btChecar.setToolTipText( "Checar" );
		btGerar.setToolTipText( "Gerar" );

		btChecar.setEnabled( false );
		btGerar.setEnabled( false );

		Container c = getContentPane();
		c.setLayout( new BorderLayout() );

		adicBotaoSair();

		c.add( pinCliente, BorderLayout.NORTH );
		c.add( pnGrid, BorderLayout.CENTER );

		txtDataini.setRequerido( true );
		txtDatafim.setRequerido( true );

		txtMatempr.setAtivo( false );
		txtCodTurno.setAtivo( false );

		pinCliente.adic( new JLabelPad( "Início" ), 7, 0, 110, 25 );
		pinCliente.adic( txtDataini, 7, 20, 110, 20 );
		pinCliente.adic( new JLabelPad( "Fim" ), 120, 0, 107, 25 );
		pinCliente.adic( txtDatafim, 120, 20, 107, 20 );
		pinCliente.adic( new JLabelPad("Cód.atend."), 230, 0, 90, 25);
		pinCliente.adic( txtCodAtend, 230, 20, 90, 20 );
		pinCliente.adic( new JLabelPad("Nome do atendente"), 323, 0, 300, 25 );
		pinCliente.adic( txtNomeAtend, 323, 20, 300, 20 );
		pinCliente.adic( new JLabelPad("Matrícula"), 7, 45, 90, 25);
		pinCliente.adic( txtMatempr, 7, 65, 90, 20 );
		pinCliente.adic( new JLabelPad("Nome do colaborador"), 100, 45, 200, 25 );
		pinCliente.adic( txtNomeempr, 100, 65, 200, 20 );
		pinCliente.adic( new JLabelPad("Cód.turno"), 303, 45, 90, 25);
		pinCliente.adic( txtCodTurno, 303, 65, 90, 20 );
		pinCliente.adic( new JLabelPad("Descrição do turno"), 396, 45, 200, 25 );
		pinCliente.adic( txtDescTurno, 396, 65, 200, 20 );
		
		pinCliente.adic( btVisual, 7, 85, 30, 30 );
		pinCliente.adic( btChecar, 40, 85, 30, 30 );
		pinCliente.adic( btGerar, 73, 85, 30, 30 );
		//pinCliente.adic( cbEntrada, 7, 50, 150, 20 );
		//pinCliente.adic( cbSaida, 170, 50, 150, 20 );
		//pinCliente.adic( lbAnd, 7, 80, 110, 20 );
		//pinCliente.adic( pbAnd, 120, 80, 210, 20 );

		pnGrid.add( spnTab1 );
		pnGrid.add( spnTab2 );

		tab1.adicColuna( "Dt.exped." );
		tab1.adicColuna( "C. horária" );

		tab1.setTamColuna( 90, 0 );
		tab1.setTamColuna( 70, 1 );

		tab2.adicColuna( "Dt.emissão" );
		tab2.adicColuna( "Dt.saída" );

		tab2.setTamColuna( 100, 0 );
		tab2.setTamColuna( 100, 1 );

		colocaMes();
		btVisual.addActionListener( this );
		btChecar.addActionListener( this );
		btGerar.addActionListener( this );

		pbAnd.setStringPainted( true );
		
	}

	private void montaListaCampos() {

		txtCodAtend.setTabelaExterna( lcAtend, null );
		txtCodAtend.setFK( true );
		txtCodAtend.setNomeCampo( "CodAtend" );
		lcAtend.add( new GuardaCampo( txtCodAtend, "CodAtend", "Cód.atend.", ListaCampos.DB_PK, false ), "txtCodAtendx" );
		lcAtend.add( new GuardaCampo( txtNomeAtend, "NomeAtend", "Nome", ListaCampos.DB_SI, false ), "txtNomeAtendx" );
		lcAtend.add( new GuardaCampo( txtMatempr, "Matempr", "Matrícula", ListaCampos.DB_FK, false), "txtMatemprx" );
		lcAtend.montaSql( false, "ATENDENTE", "AT" );
		lcAtend.setReadOnly( true );

		txtMatempr.setTabelaExterna( lcEmpregado, null );
		txtMatempr.setFK( true );
		txtMatempr.setNomeCampo( "Matempr" );
		lcEmpregado.add( new GuardaCampo( txtMatempr, "Matempr", "Matrícula", ListaCampos.DB_PK, false ), "txtMatemprx" );
		lcEmpregado.add( new GuardaCampo( txtNomeempr, "Nomeempr", "Nome do colaborador", ListaCampos.DB_SI, false ), "txtNomeemprx" );
		lcEmpregado.add( new GuardaCampo( txtCodTurno, "Codturno", "Cód.turno", ListaCampos.DB_FK, false), "txtCodturnox" );
		lcEmpregado.montaSql( false, "EMPREGADO", "RH" );
		lcEmpregado.setReadOnly( true );
		
		txtCodTurno.setTabelaExterna( lcTurno, null );
		txtCodTurno.setFK( true );
		txtCodTurno.setNomeCampo( "CodTurno" );
		lcTurno.add( new GuardaCampo( txtCodTurno, "CodTurno", "Cód.turno", ListaCampos.DB_PK, false ), "txtCodTurnox" );
		lcTurno.add( new GuardaCampo( txtDescTurno, "DescTurno", "Descrição do turno", ListaCampos.DB_SI, false ), "txtDescTurnox" );
		lcTurno.montaSql( false, "TURNO", "RH" );
		lcTurno.setReadOnly( true );
		
	}

	public void setConexao( DbConnection cn ) {
		super.setConexao( cn );
		lcAtend.setConexao( cn );
		lcEmpregado.setConexao( cn );
		lcTurno.setConexao( cn );
	}
	
	private void colocaMes() {

		GregorianCalendar cData = new GregorianCalendar();
		GregorianCalendar cDataIni = new GregorianCalendar();
		GregorianCalendar cDataFim = new GregorianCalendar();
		cDataIni.set( Calendar.MONTH, cData.get( Calendar.MONTH ) - 1 );
		cDataIni.set( Calendar.DATE, 1 );
		cDataFim.set( Calendar.DATE, -1 );
		txtDataini.setVlrDate( cDataIni.getTime() );
		txtDatafim.setVlrDate( cDataFim.getTime() );

	}

	public void iniGerar() {

		Thread th = new Thread( new Runnable() {

			public void run() {

				gerar();
			}
		} );
		try {
			th.start();
		} catch ( Exception err ) {
			Funcoes.mensagemErro( this, "Não foi possível criar processo!\n" + err.getMessage(), true, con, err );
		}
	}

	private void visualizar() {

		StringBuffer sql = new StringBuffer();
		PreparedStatement ps = null;
		ResultSet rs = null;
		int totexped = 0;
		if ( !valida() ) {
			return;
		}
		try {
			sql.append("SELECT E.DTEXPED, E.HORASEXPED FROM RHEXPEDIENTE E ");
			sql.append("WHERE E.CODEMP=? AND E.CODFILIAL=? AND E.CODTURNO=? AND ");
			sql.append("E.DTEXPED BETWEEN ? AND ? ");
			sql.append("ORDER BY E.DTEXPED");
			
 		    ps = con.prepareStatement( sql.toString() );
 	
 		    ps.setInt( 1, Aplicativo.iCodEmp );
 		    ps.setInt( 2, ListaCampos.getMasterFilial( "RHEXPEDIENTE" ) );
 		    ps.setInt( 3, 1 );
			ps.setDate( 4, Funcoes.dateToSQLDate( txtDataini.getVlrDate() ) );
			ps.setDate( 5, Funcoes.dateToSQLDate( txtDatafim.getVlrDate() ) );
			rs = ps.executeQuery();
			tab1.limpa();
			while ( rs.next() ) {
				tab1.adicLinha();
				tab1.setValor( StringFunctions.sqlDateToStrDate( rs.getDate( EColExped.DTEXPED.toString() ) ), totexped, EColExped.DTEXPED.ordinal() );
				tab1.setValor( new Integer(rs.getInt( EColExped.HORASEXPED.toString() ) ), totexped, EColExped.HORASEXPED.ordinal() );
				totexped ++;
			}

		    rs.close();
		    ps.close();

			con.commit();

		} catch ( SQLException err ) {
			Funcoes.mensagemErro( this, "Erro ao realizar consulta!\n" + err.getMessage(), true, con, err );
			err.printStackTrace();
			return;
		}
		btGerar.setEnabled( false );

	}

	private void gerar() {

		if ( ( iTotVendas + iTotCompras ) <= 0 ) {
			btGerar.setEnabled( false );
			return;
		}
		int iQuant = 0;
		iAnd = 0;
		String sSql = "";
		try {
			if ( iTotCompras > 0 ) {
				sSql = "SELECT COUNT(*) FROM LFLIVROFISCAL WHERE TIPOLF='E' AND DTESLF BETWEEN ? AND ? AND CODEMP=? AND CODFILIAL=? ";
				PreparedStatement psA = con.prepareStatement( sSql );
				psA.setDate( 1, Funcoes.dateToSQLDate( txtDataini.getVlrDate() ) );
				psA.setDate( 2, Funcoes.dateToSQLDate( txtDatafim.getVlrDate() ) );
				psA.setInt( 3, Aplicativo.iCodEmp );
				psA.setInt( 4, ListaCampos.getMasterFilial( "LFLIVROFISCAL" ) );
				ResultSet rsA = psA.executeQuery();
				iQuant = 0;
				if ( rsA.next() ) {
					iQuant = rsA.getInt( 1 );
				}
				;
				// rsA.close();
				// psA.close();
				con.commit();
				if ( iQuant > 0 ) {
					sSql = "DELETE FROM LFLIVROFISCAL WHERE TIPOLF='E' AND DTESLF BETWEEN ? AND ? AND CODEMP=? AND CODFILIAL=?";
					PreparedStatement psB = con.prepareStatement( sSql );
					psB.setDate( 1, Funcoes.dateToSQLDate( txtDataini.getVlrDate() ) );
					psB.setDate( 2, Funcoes.dateToSQLDate( txtDatafim.getVlrDate() ) );
					psB.setInt( 3, Aplicativo.iCodEmp );
					psB.setInt( 4, ListaCampos.getMasterFilial( "LFLIVROFISCAL" ) );
					psB.executeUpdate();
					// psB.close();
					con.commit();
				}

			}
			// Livros fiscais de saída
			if ( iTotVendas > 0 ) {
				sSql = "SELECT COUNT(*) FROM LFLIVROFISCAL WHERE TIPOLF='S' AND DTEMITLF BETWEEN ? AND ? AND CODEMP=? AND CODFILIAL=?";
				PreparedStatement psC = con.prepareStatement( sSql );
				psC.setDate( 1, Funcoes.dateToSQLDate( txtDataini.getVlrDate() ) );
				psC.setDate( 2, Funcoes.dateToSQLDate( txtDatafim.getVlrDate() ) );
				psC.setInt( 3, Aplicativo.iCodEmp );
				psC.setInt( 4, ListaCampos.getMasterFilial( "LFLIVROFISCAL" ) );
				ResultSet rsC = psC.executeQuery();
				iQuant = 0;
				if ( rsC.next() ) {
					iQuant = rsC.getInt( 1 );
				}
				;
				// rsC.close();
				// psC.close();
				con.commit();
				if ( iQuant > 0 ) {
					sSql = "DELETE FROM LFLIVROFISCAL WHERE TIPOLF='S' AND DTEMITLF BETWEEN ? AND ?";
					PreparedStatement psD = con.prepareStatement( sSql );
					psD.setDate( 1, Funcoes.dateToSQLDate( txtDataini.getVlrDate() ) );
					psD.setDate( 2, Funcoes.dateToSQLDate( txtDatafim.getVlrDate() ) );
					psD.executeUpdate();
					// psD.close();
					con.commit();
				}
			}

		} catch ( SQLException err ) {
			Funcoes.mensagemErro( this, "Consulta não foi executada\n" + err.getMessage(), true, con, err );
			return;
		}

		tim = new Timer( 300, this );
		pbAnd.setMinimum( 0 );
		// System.out.println("V: "+iTotVendas+" | C: "+iTotCompras+"\n");
		pbAnd.setMaximum( iTotVendas + iTotCompras );
		tim.start();
		lbAnd.setText( "Gerando..." );

		sSql = "INSERT INTO LFLIVROFISCAL ( CODEMP,CODFILIAL,TIPOLF,ANOMESLF,CODLF,CODEMITLF,SERIELF," + "DOCINILF,DTEMITLF,DTESLF,CODNAT,DOCFIMLF,ESPECIELF,UFLF,VLRCONTABILLF," + "VLRBASEICMSLF,ALIQICMSLF,VLRICMSLF,VLRISENTASICMSLF,VLROUTRASICMSLF,"
				+ "VLRBASEIPILF,ALIQIPILF,VLRIPILF,VLRISENTASIPILF,VLROUTRASIPILF,CODMODNOTA," + "CODEMPET,CODFILIALET,CODEMPNT,CODFILIALNT,CODEMPMN,CODFILIALMN,SITUACAOLF," + "VLRBASEICMSSTLF,VLRICMSSTLF,VLRACESSORIASLF"
				+ ") VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

		PreparedStatement psI;

		for ( int i = 0; i < iTotCompras; i++ ) {
			try {
				psI = con.prepareStatement( sSql );
				psI.setInt( 1, Aplicativo.iCodEmp );
				psI.setInt( 2, ListaCampos.getMasterFilial( "LFLIVROFISCAL" ) );
				psI.setString( 3, "E" );
				psI.setString( 4, ( ( "" + tab1.getValor( i, 1 ) ).substring( 6, 10 ) + ( "" + tab1.getValor( i, 1 ) ).substring( 3, 5 ) ).trim() );
				psI.setInt( 5, i );
				psI.setInt( 6, ( tab1.getValor( i, 3 ) + "" ).equals( "" ) ? 0 : Integer.parseInt( ( tab1.getValor( i, 3 ) + "" ) ) );
				psI.setString( 7, tab1.getValor( i, 7 ) + "" );
				psI.setInt( 8, ( tab1.getValor( i, 8 ) + "" ).equals( "" ) ? 0 : Integer.parseInt( ( tab1.getValor( i, 8 ) + "" ) ) );
				psI.setDate( 9, Funcoes.strDateToSqlDate( tab1.getValor( i, 0 ) + "" ) );
				psI.setDate( 10, Funcoes.strDateToSqlDate( tab1.getValor( i, 1 ) + "" ) );
				psI.setString( 11, tab1.getValor( i, 2 ) + "" );
				psI.setInt( 12, ( tab1.getValor( i, 8 ) + "" ).equals( "" ) ? 0 : Integer.parseInt( ( tab1.getValor( i, 8 ) + "" ) ) );
				psI.setString( 13, Funcoes.adicionaEspacos( tab1.getValor( i, 5 ) + "", 3 ) );
				psI.setString( 14, tab1.getValor( i, 4 ) + "" );
				psI.setBigDecimal( 15, ConversionFunctions.stringCurrencyToBigDecimal( tab1.getValor( i, 11 ) + "" ) ); // VLRCONTABIL
				psI.setBigDecimal( 16, ConversionFunctions.stringCurrencyToBigDecimal( tab1.getValor( i, 12 ) + "" ) ); // VLRBASEICMS
				psI.setBigDecimal( 17, ConversionFunctions.stringCurrencyToBigDecimal( tab1.getValor( i, 9 ) + "" ) ); // ALIQICMS
				psI.setBigDecimal( 18, ConversionFunctions.stringCurrencyToBigDecimal( tab1.getValor( i, 13 ) + "" ) ); // VLRICMS
				psI.setBigDecimal( 19, ConversionFunctions.stringCurrencyToBigDecimal( tab1.getValor( i, 14 ) + "" ) ); // VLRISENTAS ICMS
				psI.setBigDecimal( 20, ConversionFunctions.stringCurrencyToBigDecimal( tab1.getValor( i, 15 ) + "" ) ); // VLROUTRAS ICMS
				psI.setBigDecimal( 21, ConversionFunctions.stringCurrencyToBigDecimal( tab1.getValor( i, 16 ) + "" ) ); // VLRBASEIPI
				psI.setBigDecimal( 22, ConversionFunctions.stringCurrencyToBigDecimal( tab1.getValor( i, 10 ) + "" ) ); // ALIQIPI
				psI.setBigDecimal( 23, ConversionFunctions.stringCurrencyToBigDecimal( tab1.getValor( i, 17 ) + "" ) ); // VLRIPI
				psI.setBigDecimal( 24, ConversionFunctions.stringCurrencyToBigDecimal( "0" ) ); // VLRISENTAS IPI
				psI.setBigDecimal( 25, ConversionFunctions.stringCurrencyToBigDecimal( "0" ) ); // VLRIOUTRAS IPI
				psI.setInt( 26, ( tab1.getValor( i, 6 ) + "" ).equals( "" ) ? 0 : Integer.parseInt( ( tab1.getValor( i, 6 ) + "" ) ) ); // MODELO DE NOTA FISCAL
				if ( ( ! ( tab1.getValor( i, 18 ) + "" ).equals( "" ) ) || ( ! ( tab1.getValor( i, 18 ) + "" ).equals( "0" ) ) )
					psI.setInt( 27, Integer.parseInt( tab1.getValor( i, 18 ) + "" ) ); // CODEMPET
				else
					psI.setNull( 27, Types.INTEGER );
				if ( ( ! ( tab1.getValor( i, 19 ) + "" ).equals( "" ) ) || ( ! ( tab1.getValor( i, 19 ) + "" ).equals( "0" ) ) )
					psI.setInt( 28, Integer.parseInt( tab1.getValor( i, 19 ) + "" ) ); // CODFILIALET
				else
					psI.setNull( 28, Types.INTEGER );
				if ( ( ! ( tab1.getValor( i, 20 ) + "" ).equals( "" ) ) || ( ! ( tab1.getValor( i, 20 ) + "" ).equals( "0" ) ) )
					psI.setInt( 29, Integer.parseInt( tab1.getValor( i, 20 ) + "" ) ); // CODEMPNT
				else
					psI.setNull( 29, Types.INTEGER );
				if ( ( ! ( tab1.getValor( i, 21 ) + "" ).equals( "" ) ) || ( ! ( tab1.getValor( i, 21 ) + "" ).equals( "0" ) ) )
					psI.setInt( 30, Integer.parseInt( tab1.getValor( i, 21 ) + "" ) ); // CODFILIALNT
				else
					psI.setNull( 30, Types.INTEGER );
				if ( ( ! ( tab1.getValor( i, 22 ) + "" ).equals( "" ) ) || ( ! ( tab1.getValor( i, 22 ) + "" ).equals( "0" ) ) )
					psI.setInt( 31, Integer.parseInt( tab1.getValor( i, 22 ) + "" ) ); // CODEMPMN
				else
					psI.setNull( 31, Types.INTEGER );
				if ( ( ! ( tab1.getValor( i, 23 ) + "" ).equals( "" ) ) || ( ! ( tab1.getValor( i, 23 ) + "" ).equals( "0" ) ) )
					psI.setInt( 32, Integer.parseInt( tab1.getValor( i, 23 ) + "" ) ); // CODFILIALMN
				else
					psI.setNull( 32, Types.INTEGER );

				if ( "X".equals( tab1.getValor( i, 24 ).toString().substring( 0, 1 ) ) ) { // SITUACAOLF
					psI.setString( 33, "S" ); // Status para documento cancelado no sintegra.
				}
				else {
					psI.setString( 33, "N" ); // Status para documento normal no sintegra.
				}

				psI.setBigDecimal( 34, ConversionFunctions.stringCurrencyToBigDecimal( "0" ) );

				psI.setBigDecimal( 35, ConversionFunctions.stringCurrencyToBigDecimal( "0" ) );

				psI.setBigDecimal( 36, ConversionFunctions.stringCurrencyToBigDecimal( "0" ) );

				psI.executeUpdate();

				con.commit();

			} catch ( SQLException err ) {
				Funcoes.mensagemErro( this, "Erro gerando livros fiscais de compras!\n" + err.getMessage(), true, con, err );
				err.printStackTrace();
				break;
			}
			iAnd++;
			// System.out.println("And1:"+iAnd+"\n");
		}
		;

		for ( int i = 0; i < iTotVendas; i++ ) {
			try {
				psI = con.prepareStatement( sSql );
				psI.setInt( 1, Aplicativo.iCodEmp );
				psI.setInt( 2, ListaCampos.getMasterFilial( "LFLIVROFISCAL" ) );
				psI.setString( 3, "S" );

				psI.executeUpdate();

				con.commit();
			} catch ( SQLException err ) {
				Funcoes.mensagemErro( this, "Erro gerando livros fiscais de compras!\n" + err.getMessage(), true, con, err );
				err.printStackTrace();
				break;
			}
			iAnd++;
			// System.out.println("And:"+iAnd+"\n");
		}
		;
		tim.stop();
		pbAnd.setValue( iAnd );
		pbAnd.updateUI();
		lbAnd.setText( "Pronto." );
		btGerar.setEnabled( false );
	}

	private boolean checar() {

		boolean bRetorno = false;
		int iTotErros = 0;
		DLChecaLFSaida dl = new DLChecaLFSaida();
		String sSql = "SELECT V1.CODVENDA,V1.SERIE," + "V1.DOCVENDA,V1.DTEMITVENDA " + "FROM VDVENDA V1,EQTIPOMOV TM " + " WHERE V1.DTEMITVENDA BETWEEN ? AND ? AND V1.CODEMP=? AND V1.CODFILIAL=? AND " + " TM.CODTIPOMOV=V1.CODTIPOMOV AND TM.CODEMP=V1.CODEMPTM AND "
				+ " TM.CODFILIAL=V1.CODFILIALTM AND " + " TM.FISCALTIPOMOV='S' AND " + " (SELECT COUNT(*) from VDVENDA V3 " + " WHERE V3.DOCVENDA=V1.DOCVENDA AND V3.CODEMP=V1.CODEMP AND V3.SERIE=V1.SERIE AND " + " V3.CODFILIAL=V1.CODFILIAL AND V3.TIPOVENDA=V1.TIPOVENDA AND "
				+ " V3.DTEMITVENDA BETWEEN ? AND ? AND V3.CODTIPOMOV=V1.CODTIPOMOV AND " + " ( V3.TIPOVENDA<>'E' OR V3.CODEMPCX=V1.CODEMPCX AND " + " V3.CODFILIALCX=V1.CODFILIALCX AND V3.CODCAIXA=V1.CODCAIXA ))>1 " + " ORDER BY V1.CODVENDA,V1.SERIE,V1.DOCVENDA";
		try {

			PreparedStatement psChec = con.prepareStatement( sSql );
			psChec.setDate( 1, Funcoes.dateToSQLDate( txtDataini.getVlrDate() ) );
			psChec.setDate( 2, Funcoes.dateToSQLDate( txtDatafim.getVlrDate() ) );
			psChec.setInt( 3, Aplicativo.iCodEmp );
			psChec.setInt( 4, ListaCampos.getMasterFilial( "VDVENDA" ) );
			psChec.setDate( 5, Funcoes.dateToSQLDate( txtDataini.getVlrDate() ) );
			psChec.setDate( 6, Funcoes.dateToSQLDate( txtDatafim.getVlrDate() ) );
			ResultSet rsChec = psChec.executeQuery();
			iTotErros = 0;
			dl.tab.limpa();
			while ( rsChec.next() ) {
				dl.tab.adicLinha();
				dl.tab.setValor( "" + rsChec.getInt( 1 ), iTotErros, 0 );
				dl.tab.setValor( rsChec.getString( 2 ), iTotErros, 1 );
				dl.tab.setValor( "" + rsChec.getInt( 3 ), iTotErros, 2 );
				dl.tab.setValor( StringFunctions.sqlDateToStrDate( rsChec.getDate( 4 ) ), iTotErros, 3 );
				dl.tab.setValor( "Numeração de NF. repetida", iTotErros, 4 );
				iTotErros++;
			}

			if ( iTotErros > 0 ) {
				btGerar.setEnabled( false );
				dl.setVisible( true );
			}
			else {
				bRetorno = true;
				btGerar.setEnabled( true );
			}

			// rsChec.close();
			// psChec.close();
			con.commit();

		} catch ( SQLException err ) {
			Funcoes.mensagemErro( this, "Erro ao realizar consulta!\n" + err.getMessage(), true, con, err );
			bRetorno = false;
		}

		return bRetorno;
	}

	private boolean valida() {

		if ( txtDatafim.getVlrDate().before( txtDataini.getVlrDate() ) ) {
			Funcoes.mensagemInforma( this, "Data final maior que a data inicial!" );
			return false;
		}

		return true;
	}

	public void actionPerformed( ActionEvent evt ) {

		if ( evt.getSource() == tim ) {
			// System.out.println("Atualizando\n");
			pbAnd.setValue( iAnd + 1 );
			pbAnd.updateUI();
		}
		else if ( evt.getSource() == btGerar ) {
			iniGerar();
		}
		else if ( evt.getSource() == btChecar ) {
			checar();
		}
		else if ( evt.getSource() == btVisual ) {
			visualizar();
		}
	}
}
