/**
 * @version 02/11/2003 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 * 
 *         Projeto: Freedom <BR>
 * 
 *         Pacote: org.freedom.modulos.std <BR>
 *         Classe:
 * @(#)FSintegra.java <BR>
 * 
 *                    Este arquivo é parte do sistema Freedom-ERP, o Freedom-ERP é um software livre; você pode redistribui-lo e/ou <BR>
 *                    modifica-lo dentro dos termos da Licença Pública Geral GNU como publicada pela Fundação do Software Livre (FSF); <BR>
 *                    na versão 2 da Licença, ou (na sua opnião) qualquer versão. <BR>
 *                    Este programa é distribuido na esperança que possa ser util, mas SEM NENHUMA GARANTIA; <BR>
 *                    sem uma garantia implicita de ADEQUAÇÂO a qualquer MERCADO ou APLICAÇÃO EM PARTICULAR. <BR>
 *                    Veja a Licença Pública Geral GNU para maiores detalhes. <BR>
 *                    Você deve ter recebido uma cópia da Licença Pública Geral GNU junto com este programa, se não, <BR>
 *                    de acordo com os termos da LPG-PC <BR>
 * <BR>
 * 
 *                    Comentários sobre a classe...
 * 
 */

package org.freedom.modulos.lvf.view.frame.utility;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Vector;

import org.freedom.bmps.Icone;
import org.freedom.infra.functions.StringFunctions;
import org.freedom.library.functions.Funcoes;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.component.JButtonPad;
import org.freedom.library.swing.component.JCheckBoxPad;
import org.freedom.library.swing.component.JLabelPad;
import org.freedom.library.swing.component.JPanelPad;
import org.freedom.library.swing.component.JRadioGroup;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.frame.Aplicativo;
import org.freedom.library.swing.frame.FFilho;

public class FSintegra extends FFilho implements ActionListener {

	protected static final String ISO88591 = "ISO8859_1";

	private static final long serialVersionUID = 1L;

	// private FileWriter fwSintegra;

	private FileOutputStream fosSintegra;

	private OutputStreamWriter oswSintegra;

	private String sFileName = "";

	private String sCnpjEmp = "";

	private String sInscEmp = "";

	private JPanelPad pinCliente = new JPanelPad( 700, 490 );

	private JTextFieldPad txtDataini = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JTextFieldPad txtDatafim = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private JCheckBoxPad cbEntrada = new JCheckBoxPad( "Entrada", "S", "N" );

	private JCheckBoxPad cbSaida = new JCheckBoxPad( "Saida", "S", "N" );

	private JCheckBoxPad cbInventario = new JCheckBoxPad( "Inventário", "S", "N" );

	private JCheckBoxPad cbConsumidor = new JCheckBoxPad( "NF Consumidor", "S", "N" );

	private JCheckBoxPad cbFrete = new JCheckBoxPad( "Frete", "S", "N" );

	private JButtonPad btGerar = new JButtonPad( Icone.novo( "btGerar.png" ) );

	private String CR = "" + ( (char) 13 ) + "" + ( (char) 10 );

	private JRadioGroup<?, ?> rgConvenio;

	private JRadioGroup<?, ?> rgNatoper;

	private JRadioGroup<?, ?> rgFinalidade;

	private Vector<String> vLabConvenio = new Vector<String>();

	private Vector<String> vValConvenio = new Vector<String>();

	private Vector<String> vLabNatoper = new Vector<String>();

	private Vector<String> vValNatoper = new Vector<String>();

	private Vector<String> vLabFinalidade = new Vector<String>();

	private Vector<String> vValFinalidade = new Vector<String>();

	private JLabelPad lbAnd = new JLabelPad();

	private int iCodEmp = Aplicativo.iCodEmp;

	private String sUsaRefProd = "N";

	private boolean contribipi = false;

	private Vector<String> vProd75 = new Vector<String>();

	StringBuffer buffer75 = new StringBuffer();

	public FSintegra() {

		super( false );
		setTitulo( "Exportar Sintegra" );
		setAtribos( 50, 20, 710, 470 );

		btGerar.setToolTipText( "Exporta arquivo" );

		Container c = getContentPane();
		c.setLayout( new BorderLayout() );

		adicBotaoSair();

		c.add( pinCliente, BorderLayout.CENTER );

		txtDataini.setTipo( JTextFieldPad.TP_DATE, 0, 10 );
		txtDatafim.setTipo( JTextFieldPad.TP_DATE, 0, 10 );
		txtDataini.setRequerido( true );
		txtDatafim.setRequerido( true );

		vLabConvenio.addElement( "Convênio 57/95" );
		vLabConvenio.addElement( "Convênio 69/02" );
		vLabConvenio.addElement( "Convênio 57/95 (a partir de Jan.2004)" );
		vValConvenio.addElement( "1" );
		vValConvenio.addElement( "2" );
		vValConvenio.addElement( "3" );

		rgConvenio = new JRadioGroup<String, String>( 3, 1, vLabConvenio, vValConvenio );
		rgConvenio.setVlrString( "3" );

		vLabNatoper.addElement( "Interestaduais - Somente operações sujeitas ao regime de substituição tributária" );
		vLabNatoper.addElement( "Interestaduais - Operações com ou sem substituição tributária" );
		vLabNatoper.addElement( "Totalidade das operações do informante" );
		vValNatoper.addElement( "1" );
		vValNatoper.addElement( "2" );
		vValNatoper.addElement( "3" );
		rgNatoper = new JRadioGroup<String, String>( 3, 3, vLabNatoper, vValNatoper );
		rgNatoper.setVlrString( "3" );

		vLabFinalidade.addElement( "Normal" );
		vLabFinalidade.addElement( "Retificação total de arquivo" );
		vLabFinalidade.addElement( "Retificação aditíva de arquivo" );
		vLabFinalidade.addElement( "Retificação corretiva de arquivo" );
		vLabFinalidade.addElement( "Desfaziamento" );
		vValFinalidade.addElement( "1" );
		vValFinalidade.addElement( "2" );
		vValFinalidade.addElement( "3" );
		vValFinalidade.addElement( "4" );
		vValFinalidade.addElement( "5" );
		rgFinalidade = new JRadioGroup<String, String>( 5, 5, vLabFinalidade, vValFinalidade );

		pinCliente.adic( new JLabelPad( "Inicio" ), 7, 0, 110, 25 );
		pinCliente.adic( txtDataini, 7, 20, 110, 20 );
		pinCliente.adic( new JLabelPad( "Fim" ), 120, 0, 107, 25 );
		pinCliente.adic( txtDatafim, 120, 20, 107, 20 );
		pinCliente.adic( btGerar, 296, 15, 30, 30 );
		pinCliente.adic( cbEntrada, 7, 50, 100, 20 );

		pinCliente.adic( cbSaida, 130, 50, 100, 20 );
		pinCliente.adic( cbConsumidor, 253, 50, 150, 20 );
		pinCliente.adic( cbInventario, 430, 50, 100, 20 );
		pinCliente.adic( cbFrete, 590, 50, 100, 20 );

		pinCliente.adic( rgConvenio, 7, 80, 680, 80 );
		pinCliente.adic( rgNatoper, 7, 170, 680, 80 );
		pinCliente.adic( rgFinalidade, 7, 260, 680, 110 );

		pinCliente.adic( lbAnd, 7, 380, 680, 20 );
		lbAnd.setForeground( Color.BLUE );
		lbAnd.setText( "Aguardando..." );
		colocaMes();
		btGerar.addActionListener( this );

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

	/*
	 * private void colocaTrimestre() { int iMesAtual = 0; GregorianCalendar cData = new GregorianCalendar(); GregorianCalendar cDataIni = new GregorianCalendar(); GregorianCalendar cDataFim = new GregorianCalendar(); iMesAtual = cData.get(Calendar.MONTH)+1; if (iMesAtual < 4) { cDataIni = new
	 * GregorianCalendar(cData.get(Calendar.YEAR)-1,10-1,1); cDataFim = new GregorianCalendar(cData.get(Calendar.YEAR)-1,12-1,31); } else if ((iMesAtual > 3) & (iMesAtual < 7)) { cDataIni = new GregorianCalendar(cData.get(Calendar.YEAR),1-1,1); cDataFim = new
	 * GregorianCalendar(cData.get(Calendar.YEAR),3-1,31); } else if ((iMesAtual > 6) & (iMesAtual < 10)) { cDataIni = new GregorianCalendar(cData.get(Calendar.YEAR),4-1,1); cDataFim = new GregorianCalendar(cData.get(Calendar.YEAR),6-1,30); } else if (iMesAtual > 9) { cDataIni = new
	 * GregorianCalendar(cData.get(Calendar.YEAR),7-1,1); cDataFim = new GregorianCalendar(cData.get(Calendar.YEAR),9-1,30); } txtDataini.setVlrDate(cDataIni.getTime()); txtDatafim.setVlrDate(cDataFim.getTime()); }
	 */

	public void iniGerar() {

		Thread th = new Thread( new Runnable() {

			public void run() {

				gerar();
			}
		} );

		try {
			th.start();
		} catch ( Exception err ) {
			Funcoes.mensagemInforma( this, "Não foi possível criar processo!\n" + err.getMessage() );
		}

	}

	private void gerar() {

		String sql = "";
		String serieecf = "";
		StringBuffer buffer = new StringBuffer();
		int iTot50 = 0;
		int iTot51 = 0;
		int iTot53 = 0;
		int iTot54 = 0;
		int iTot60 = 0;
		int iTot61 = 0;
		int iTot70 = 0;
		int iTot74 = 0;
		int iTot75 = 0;
		int iTotreg = 0;
		int iCodEmp = 0;

		iCodEmp = Aplicativo.iCodEmp;

		if ( !valida() ) {
			return;
		}

		if ( sFileName.trim().equals( "" ) ) {
			Funcoes.mensagemInforma( this, "Selecione o arquivo!" );
			return;
		}

		// System.setProperty(SUN_JNU_ENCODING, ISO88591);
		// System.setProperty(FILE_ENCODING, ISO88591);

		File fSintegra = new File( sFileName );

		if ( fSintegra.exists() ) {
			if ( Funcoes.mensagemConfirma( this, "Arquivo: '" + sFileName + "' já existe! Deseja sobrescrever?" ) != 0 ) {
				return;
			}
		}

		try {
			fSintegra.createNewFile();
		} catch ( IOException err ) {
			Funcoes.mensagemErro( this, "Erro limpando arquivo: " + sFileName + "\n" + err.getMessage(), true, con, err );
			return;
		}

		try {
			// fwSintegra = new FileWriter( fSintegra );
			fosSintegra = new FileOutputStream( fSintegra );
			oswSintegra = new OutputStreamWriter( fosSintegra, ISO88591 );

		} catch ( IOException ioError ) {
			Funcoes.mensagemErro( this, "Erro Criando o arquivo: " + sFileName + "\n" + ioError.getMessage() );
			return;
		}

		btGerar.setEnabled( false );

		// REGISTRO TIPO 1 HEADER DO ARQUIVO

		PreparedStatement ps;
		ResultSet rs;

		try {

			serieecf = getSerieECF();

			sql = "SELECT P.USAREFPROD,F.CONTRIBIPIFILIAL FROM SGPREFERE1 P, SGFILIAL F " + "WHERE P.CODEMP=? AND P.CODFILIAL=? AND F.CODEMP=P.CODEMP AND F.CODFILIAL=P.CODFILIAL ";
			ps = con.prepareStatement( sql );
			ps.setInt( 1, iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "SGPREFERE1" ) );
			rs = ps.executeQuery();
			if ( rs.next() ) {

				sUsaRefProd = rs.getString( "USAREFPROD" );
				contribipi = "S".equals( rs.getString( "CONTRIBIPIFILIAL" ) );

				if ( sUsaRefProd == null )
					sUsaRefProd = "N";
			}

			con.commit();

		} catch ( SQLException sqlErr ) {
			sqlErr.printStackTrace();
			Funcoes.mensagemErro( this, "Erro ao consultar preferencias.\n" + sqlErr.getMessage() );
		}

		geraRegistro10e11();

		iTot50 = geraRegistro50( serieecf ); // ICMS

		if ( contribipi ) {
			iTot51 = geraRegistro51( serieecf ); // IPI
		}

		iTot53 = geraRegistro53( serieecf ); // Substituição Tributária
		iTot54 = geraRegistro54( serieecf ); // Produto
		iTot60 = geraRegistro60(); // PDV
		iTot61 = geraRegistro61( serieecf ); // Saídas
		iTot70 = geraRegistro70(); // Conhecimentos de Frete;
		iTot75 = geraRegistro75(); // Código de produtos - Deve ser gerado antes, pois gera informações para uso no registro 74
		iTot74 = geraRegistro74(); // 

		iTotreg = iTot50 + +iTot51 + iTot53 + iTot54 + iTot60 + iTot61 + iTot70 + iTot74 + iTot75 + 3;

		if ( iTot50 > 0 )
			buffer.append( retorna90( buffer.toString(), "50", iTot50 ) );

		if ( iTot51 > 0 )
			buffer.append( retorna90( buffer.toString(), "51", iTot51 ) );

		if ( iTot53 > 0 )
			buffer.append( retorna90( buffer.toString(), "53", iTot53 ) );

		if ( iTot54 > 0 )
			buffer.append( retorna90( buffer.toString(), "54", iTot54 ) );

		if ( iTot60 > 0 )
			buffer.append( retorna90( buffer.toString(), "60", iTot60 ) );

		if ( iTot61 > 0 )
			buffer.append( retorna90( buffer.toString(), "61", iTot61 ) );

		if ( iTot70 > 0 )
			buffer.append( retorna90( buffer.toString(), "70", iTot70 ) );

		if ( iTot74 > 0 )
			buffer.append( retorna90( buffer.toString(), "74", iTot74 ) );

		if ( iTot75 > 0 )
			buffer.append( retorna90( buffer.toString(), "75", iTot75 ) );

		buffer.append( "99" );
		buffer.append( StringFunctions.strZero( String.valueOf( iTotreg ), 8 ) );
		buffer.append( StringFunctions.replicate( " ", 125 - buffer.length() ) + "1" + CR );
		gravaBuffer( buffer.toString() );

		try {
			oswSintegra.close();
		} catch ( IOException ioError ) {
			ioError.printStackTrace();
			Funcoes.mensagemInforma( this, "Fechando o arquivo: " + sFileName + "\n" + ioError.getMessage() );
		}

		Funcoes.mensagemInforma( this, "Arquivo exportado!" );
		lbAnd.setText( "Pronto." );
		btGerar.setEnabled( true );

	}

	private void geraRegistro10e11() {

		PreparedStatement ps;
		ResultSet rs;
		StringBuffer sql = new StringBuffer();
		StringBuffer buffer = new StringBuffer();
		String sConvenio = rgConvenio.getVlrString();
		String sNatoper = rgNatoper.getVlrString();
		String sFinalidade = rgFinalidade.getVlrString();

		try {

			sql.append( "SELECT F.CODEMP,F.CNPJFILIAL,F.INSCFILIAL,F.RAZFILIAL,M.NOMEMUNIC,F.SIGLAUF,F.FAXFILIAL," );
			sql.append( "F.BAIRFILIAL,F.ENDFILIAL,F.NUMFILIAL,E.NOMECONTEMP,F.FONEFILIAL,F.CEPFILIAL " );
			sql.append( "FROM SGFILIAL F,SGEMPRESA E, SGMUNICIPIO M WHERE " );
			sql.append( "E.CODEMP=F.CODEMP AND F.CODEMP=? AND F.CODFILIAL=? " );
			sql.append( "AND F.CODMUNIC=M.CODMUNIC AND F.CODPAIS=M.CODPAIS AND F.SIGLAUF=M.SIGLAUF " );

			ps = con.prepareStatement( sql.toString() );
			ps.setInt( 1, iCodEmp );
			ps.setInt( 2, Aplicativo.iCodFilial );
			rs = ps.executeQuery();

			if ( rs.next() ) {

				buffer.delete( 0, buffer.length() );

				sCnpjEmp = Funcoes.adicionaEspacos( rs.getString( "CNPJFILIAL" ), 14 );
				sInscEmp = Funcoes.adicionaEspacos( StringFunctions.clearString( rs.getString( "INSCFILIAL" ) ), 14 );

				buffer.append( "10" );
				buffer.append( Funcoes.adicionaEspacos( rs.getString( "CNPJFILIAL" ), 14 ) );
				buffer.append( Funcoes.adicionaEspacos( StringFunctions.clearString( rs.getString( "INSCFILIAL" ) ), 14 ) );
				buffer.append( Funcoes.adicionaEspacos( rs.getString( "RAZFILIAL" ), 35 ) );
				buffer.append( Funcoes.adicionaEspacos( rs.getString( "NOMEMUNIC" ), 30 ) );
				buffer.append( Funcoes.adicionaEspacos( rs.getString( "SIGLAUF" ), 2 ) );
				buffer.append( StringFunctions.strZero( rs.getString( "FAXFILIAL" ), 10 ) );
				buffer.append( Funcoes.dataAAAAMMDD( txtDataini.getVlrDate() ) );
				buffer.append( Funcoes.dataAAAAMMDD( txtDatafim.getVlrDate() ) );
				buffer.append( sConvenio );
				buffer.append( sNatoper );
				buffer.append( sFinalidade + CR );

				buffer.append( "11" );
				buffer.append( Funcoes.adicionaEspacos( rs.getString( "ENDFILIAL" ), 34 ) );
				buffer.append( StringFunctions.strZero( String.valueOf( rs.getInt( "NUMFILIAL" ) ), 5 ) );
				buffer.append( StringFunctions.replicate( " ", 22 ) );
				buffer.append( Funcoes.adicionaEspacos( rs.getString( "BAIRFILIAL" ), 15 ) );
				buffer.append( StringFunctions.strZero( rs.getString( "CEPFILIAL" ), 8 ) );
				buffer.append( Funcoes.adicionaEspacos( rs.getString( "NOMECONTEMP" ), 28 ) );
				buffer.append( StringFunctions.strZero( StringFunctions.clearString( rs.getString( "FONEFILIAL" ) ), 12 ) + CR );

				gravaBuffer( buffer.toString() );

			}
			rs.close();
			ps.close();
			con.commit();

		} catch ( Exception e ) {
			e.printStackTrace();
			Funcoes.mensagemErro( this, "Erro ao gerar registro 10 e 11!\n" + e.getMessage(), true, con, e );
		}

	}

	private int geraRegistro50( String serieecf ) {

		PreparedStatement ps;
		String cnpjcli = "";
		String insccli = "";
		String cnpjfor = "";
		String cpffor = "";
		String inscfor = "";

		ResultSet rs;
		StringBuffer sql = new StringBuffer();
		StringBuffer buffer = new StringBuffer();
		String sConvenio = rgConvenio.getVlrString();
		int cont = 0;

		try {

			if ( "S".equals( cbEntrada.getVlrString() ) ) {
				// REGISTRO 50 LIVROS FISCAIS DE ENTRADA
				sql.append( "select lf.tipolf,lf.anomeslf,lf.especielf,lf.docinilf," );
				sql.append( "lf.docfimlf,lf.serielf,lf.codemitlf,lf.uflf,lf.dtemitlf," );
				sql.append( "lf.dteslf," );
				sql.append( "sum(lf.vlrcontabillf) as vlrcontabillf," );
				sql.append( "sum(lf.vlrbaseicmslf) as vlrbaseicmslf," );
				sql.append( "lf.aliqicmslf," );
				sql.append( "sum(lf.vlricmslf) as vlricmslf," );
				sql.append( "sum(lf.vlrisentasicmslf) as vlrisentasicmslf," );
				sql.append( "sum(lf.vlroutrasicmslf) as vlroutrasicmslf," );
				sql.append( "lf.codnat,lf.codmodnota,f.cnpjfor,f.cpffor, f.inscfor,lf.situacaolf, f.pessoafor " );
				sql.append( "from lflivrofiscal lf,cpforneced f " );
				sql.append( "where lf.dteslf between ? and ? and " );
				sql.append( "lf.codemp=? and lf.codfilial=? and " );
				sql.append( "f.codfor=lf.codemitlf and f.codemp=lf.codempet and " );
				sql.append( "f.codfilial=lf.codfilialet and lf.tipolf='E' "); // emissão de nota de compra de fornecedor pessoa fisica... and f.pessoafor='j' " );

				// Incluído de acordo com o íten 11.1.3 do manual do sintegra.

				sql.append( "group by 1,2,3,4,5,6,7,8,9,10,13,17,18,19,20,21,22,23 " );
				sql.append( "order by lf.dteslf,lf.docinilf" );
				ps = con.prepareStatement( sql.toString() );
				ps.setDate( 1, Funcoes.dateToSQLDate( txtDataini.getVlrDate() ) );
				ps.setDate( 2, Funcoes.dateToSQLDate( txtDatafim.getVlrDate() ) );
				ps.setInt( 3, iCodEmp );
				ps.setInt( 4, ListaCampos.getMasterFilial( "LFLIVROFISCAL" ) );
				rs = ps.executeQuery();

				lbAnd.setText( "Gerando Entrada..." );

				while ( rs.next() ) {

					buffer.delete( 0, buffer.length() );

					cnpjfor = rs.getString( "CNPJFOR" );
					inscfor = rs.getString( "INSCFOR" );

					if ( ( inscfor == null ) || ( "ISENTA".equals( inscfor.trim() ) ) ) {
						inscfor = "ISENTO";
					}

					/* 01 */buffer.append( "50" );
					if("J".equals( rs.getString( "PESSOAFOR" ) )) {
						
						cnpjfor = rs.getString( "CNPJFOR" );
						
						/* 02 */buffer.append( Funcoes.adicionaEspacos( cnpjfor, 14 ) );
					
					}
					else {
						
						cpffor = rs.getString( "CPFFOR" );
						
						/* 02 */buffer.append( StringFunctions.strZero( cpffor, 14 ));
						
					}
				
					/* 03 */buffer.append( Funcoes.adicionaEspacos( StringFunctions.clearString( inscfor ), 14 ) );
					/* 04 */buffer.append( Funcoes.dataAAAAMMDD( Funcoes.sqlDateToDate( rs.getDate( "DTESLF" ) ) ) );
					/* 05 */buffer.append( Funcoes.adicionaEspacos( rs.getString( "UFLF" ), 2 ) );
					/* 06 */buffer.append( StringFunctions.strZero( rs.getInt( "CODMODNOTA" ) + "", 2 ) );
					/* 07 */buffer.append( Funcoes.adicionaEspacos( rs.getString( "SERIELF" ), 3 ) );
					/* 08 */buffer.append( StringFunctions.strZero( rs.getInt( "DOCINILF" ) + "", 6 ) );
					/* 09 */buffer.append( Funcoes.adicionaEspacos( rs.getString( "CODNAT" ), ( sConvenio.equals( "1" ) ? 3 : 4 ) ) );
					/* 10 */buffer.append( ( sConvenio.equals( "1" ) ? "" : "T" ) ); // Emitente da nota fiscal P-Própio ou T-Terceiros
					/* 11 */buffer.append( Funcoes.transValor( rs.getString( "VLRCONTABILLF" ), 13, 2, true ) );
					/* 12 */buffer.append( Funcoes.transValor( rs.getString( "VLRBASEICMSLF" ), 13, 2, true ) );
					/* 13 */buffer.append( Funcoes.transValor( rs.getString( "VLRICMSLF" ), 13, 2, true ) );
					/* 14 */buffer.append( Funcoes.transValor( rs.getString( "VLRISENTASICMSLF" ), 13, 2, true ) );
					/* 15 */buffer.append( Funcoes.transValor( rs.getString( "VLROUTRASICMSLF" ), 13, 2, true ) );
					/* 16 */buffer.append( Funcoes.transValor( rs.getString( "ALIQICMSLF" ), 4, 2, true ) );
					/* 17 */buffer.append( rs.getString( "SITUACAOLF" ) + CR );

					gravaBuffer( buffer.toString() );
					cont++;

				}

				rs.close();
				ps.close();

				con.commit();

			}

			if ( "S".equals( cbSaida.getVlrString() ) ) {

				// REGISTRO 50 LIVROS FISCAIS DE SAIDA

				sql.delete( 0, sql.length() );

				sql.append( "select lf.tipolf,lf.anomeslf,lf.especielf,lf.docinilf," );
				sql.append( "lf.docfimlf,lf.serielf,lf.codemitlf,lf.uflf,lf.dtemitlf," );
				sql.append( "lf.dteslf," );
				sql.append( "sum(lf.vlrcontabillf) as vlrcontabillf," );
				sql.append( "sum(lf.vlrbaseicmslf) as vlrbaseicmslf," );
				sql.append( "lf.aliqicmslf," );
				sql.append( "sum(lf.vlricmslf) as vlricmslf," );
				sql.append( "sum(lf.vlrisentasicmslf) as vlrisentasicmslf," );
				sql.append( "sum(lf.vlroutrasicmslf) as vlroutrasicmslf," );
				sql.append( "lf.codnat,lf.codmodnota,c.cnpjcli,c.insccli, c.pessoacli, c.cpfcli, lf.situacaolf " );
				sql.append( "from lflivrofiscal lf,vdcliente c " );
				sql.append( "where lf.dtemitlf between ? and ? " );
				sql.append( "and lf.codemp=? and lf.codfilial=? " );
				sql.append( "and c.codcli=lf.codemitlf and c.codemp=lf.codempet " );
				sql.append( "and c.codfilial=lf.codfilialet ");
				//c.pessoacli in ('J'" );
				//if ("S".equals( cbConsumidor.getVlrString() )) {
				//	sql.append(",'F'");
				//}
				//sql.append( ") ");
				sql.append( "and lf.tipolf='S' ");
				sql.append( "and lf.serielf<>? " );
				sql.append( "group by 1,2,3,4,5,6,7,8,9,10,13,17,18,19,20,21,22,23 " );
				sql.append( "order by lf.dtemitlf,lf.docinilf" );

				ps = con.prepareStatement( sql.toString() );
				ps.setDate( 1, Funcoes.dateToSQLDate( txtDataini.getVlrDate() ) );
				ps.setDate( 2, Funcoes.dateToSQLDate( txtDatafim.getVlrDate() ) );
				ps.setInt( 3, iCodEmp );
				ps.setInt( 4, ListaCampos.getMasterFilial( "LFLIVROFISCAL" ) );
				ps.setString( 5, serieecf );
				rs = ps.executeQuery();

				lbAnd.setText( "Gerando Saídas..." );

				while ( rs.next() ) {
					buffer.delete( 0, buffer.length() );
					if ( "F".equals( rs.getString( "PESSOACLI" ) ) ) {
						cnpjcli = StringFunctions.strZero( rs.getString( "CPFCLI" ), 14); // 
					}
					else {
						cnpjcli = rs.getString( "CNPJCLI" );
					}
					insccli = rs.getString( "INSCCLI" );
					if ( ( insccli == null ) || ( "ISENTA".equals( insccli ) ) ) {
						insccli = "ISENTO";
					}
					/* 01 */buffer.append( "50" );
					/* 02 */buffer.append( Funcoes.adicionaEspacos( cnpjcli, 14 ) );
					/* 03 */buffer.append( Funcoes.adicionaEspacos( StringFunctions.clearString( insccli ), 14 ) );
					/* 04 */buffer.append( Funcoes.dataAAAAMMDD( Funcoes.sqlDateToDate( rs.getDate( "DTEMITLF" ) ) ) );
					/* 05 */buffer.append( Funcoes.adicionaEspacos( rs.getString( "UFLF" ), 2 ) );
					/* 06 */buffer.append( StringFunctions.strZero( String.valueOf( rs.getInt( "CODMODNOTA" ) ), 2 ) );
					/* 07 */buffer.append( Funcoes.adicionaEspacos( rs.getString( "SERIELF" ), 3 ) );
					/* 07 buffer.append( "  " ); //SUBSERIE */
					/* 08 */buffer.append( StringFunctions.strZero( String.valueOf( rs.getInt( "DOCINILF" ) ), 6 ) );
					/* 09 */buffer.append( Funcoes.adicionaEspacos( rs.getString( "CODNAT" ), ( sConvenio.equals( "1" ) ? 3 : 4 ) ) );
					/* 10 */buffer.append( ( sConvenio.equals( "1" ) ? "" : "P" ) ); // Emitente da nota fiscal P-Própio ou T-Terceiros
					/* 11 */buffer.append( Funcoes.transValor( rs.getString( "VLRCONTABILLF" ), 13, 2, true ) );
					/* 12 */buffer.append( Funcoes.transValor( rs.getString( "VLRBASEICMSLF" ), 13, 2, true ) );
					/* 13 */buffer.append( Funcoes.transValor( rs.getString( "VLRICMSLF" ), 13, 2, true ) );
					/* 14 */buffer.append( Funcoes.transValor( rs.getString( "VLRISENTASICMSLF" ), 13, 2, true ) );
					/* 15 */buffer.append( Funcoes.transValor( rs.getString( "VLROUTRASICMSLF" ), 13, 2, true ) );
					/* 16 */buffer.append( Funcoes.transValor( rs.getString( "ALIQICMSLF" ), 4, 2, true ) );
					/* 17 */buffer.append( rs.getString( "SITUACAOLF" ) + CR );

					gravaBuffer( buffer.toString() );
					cont++;
				}

				rs.close();
				ps.close();

				con.commit();

			}

		} catch ( Exception e ) {
			e.printStackTrace();
			Funcoes.mensagemErro( this, "Erro ao gerar registro 50!\n" + e.getMessage(), true, con, e );
		}

		return cont;

	}

	/* *********************************************
	 * REGISTRO 51 LIVROS FISCAIS DE ENTRADA/SAIDA REFERENTES AO IPI*********************************************
	 */
	private int geraRegistro51( String serieecf ) {

		PreparedStatement ps;
		String cnpjcli = "";
		String insccli = "";
		String cnpjfor = "";
		String inscfor = "";
		ResultSet rs;
		StringBuffer sql = new StringBuffer();
		StringBuffer buffer = new StringBuffer();
		String sConvenio = rgConvenio.getVlrString();
		int cont = 0;

		try {

			if ( "S".equals( cbEntrada.getVlrString() ) ) {

				// LIVROS FISCAIS DE ENTRADA

				sql.append( "select lf.tipolf,lf.anomeslf,lf.especielf,lf.docinilf," );
				sql.append( "lf.docfimlf,lf.serielf,lf.codemitlf,lf.uflf,lf.dtemitlf," );
				sql.append( "lf.dteslf," );
				sql.append( "sum(lf.vlrcontabillf) as vlrcontabillf," );
				sql.append( "sum(lf.vlrbaseipilf) as vlrbaseipilf," );
				sql.append( "lf.aliqipilf," );
				sql.append( "sum(lf.vlripilf) as vlripilf," );
				sql.append( "sum(lf.vlrisentasipilf) as vlrisentasipilf," );
				sql.append( "sum(lf.vlroutrasipilf) as vlroutrasipilf," );
				sql.append( "lf.codnat,lf.codmodnota,f.cnpjfor,f.inscfor,lf.situacaolf " );
				sql.append( "from lflivrofiscal lf,cpforneced f " );
				sql.append( "where lf.dteslf between ? and ? and " );
				sql.append( "lf.codemp=? and lf.codfilial=? and " );
				sql.append( "f.codfor=lf.codemitlf and f.codemp=lf.codempet and " );
				sql.append( "f.codfilial=lf.codfilialet and lf.tipolf='E' and f.pessoafor='J' " );
				// incluído de acordo com o íten 11.1.3 do manual do sintegra.
				sql.append( "group by 1,2,3,4,5,6,7,8,9,10,13,17,18,19,20,21 " );
				sql.append( "order by lf.dteslf,lf.docinilf" );
				ps = con.prepareStatement( sql.toString() );
				ps.setDate( 1, Funcoes.dateToSQLDate( txtDataini.getVlrDate() ) );
				ps.setDate( 2, Funcoes.dateToSQLDate( txtDatafim.getVlrDate() ) );
				ps.setInt( 3, iCodEmp );
				ps.setInt( 4, ListaCampos.getMasterFilial( "LFLIVROFISCAL" ) );
				rs = ps.executeQuery();

				lbAnd.setText( "Gerando Entrada..." );

				while ( rs.next() ) {

					buffer.delete( 0, buffer.length() );

					cnpjfor = rs.getString( "CNPJFOR" );
					inscfor = rs.getString( "INSCFOR" );

					if ( ( inscfor == null ) || ( "ISENTA".equals( inscfor.trim() ) ) ) {
						inscfor = "ISENTO";
					}

					/* 01 */buffer.append( "51" );
					/* 02 */buffer.append( Funcoes.adicionaEspacos( cnpjfor, 14 ) );
					/* 03 */buffer.append( Funcoes.adicionaEspacos( StringFunctions.clearString( inscfor ), 14 ) );
					/* 04 */buffer.append( Funcoes.dataAAAAMMDD( Funcoes.sqlDateToDate( rs.getDate( "DTESLF" ) ) ) );
					/* 05 */buffer.append( Funcoes.adicionaEspacos( rs.getString( "UFLF" ), 2 ) );
					/* 06 */buffer.append( Funcoes.adicionaEspacos( rs.getString( "SERIELF" ), 3 ) );
					/* 07 */buffer.append( StringFunctions.strZero( rs.getInt( "DOCINILF" ) + "", 6 ) );
					/* 08 */buffer.append( Funcoes.adicionaEspacos( rs.getString( "CODNAT" ), ( sConvenio.equals( "1" ) ? 3 : 4 ) ) );
					/* 09 */buffer.append( Funcoes.transValor( rs.getString( "VLRCONTABILLF" ), 13, 2, true ) );
					/* 10 */buffer.append( Funcoes.transValor( rs.getString( "VLRIPILF" ), 13, 2, true ) );
					/* 11 */buffer.append( Funcoes.transValor( rs.getString( "VLRISENTASIPILF" ), 13, 2, true ) );
					/* 12 */buffer.append( Funcoes.transValor( rs.getString( "VLROUTRASIPILF" ), 13, 2, true ) );
					/* 13 */buffer.append( StringFunctions.replicate( " ", 20 ) );
					/* 14 */buffer.append( rs.getString( "SITUACAOLF" ) + CR );

					gravaBuffer( buffer.toString() );
					cont++;

				}

				rs.close();
				ps.close();

				con.commit();

			}

			if ( "S".equals( cbSaida.getVlrString() ) ) {

				// LIVROS FISCAIS DE SAIDA

				sql.delete( 0, sql.length() );
				sql.append( "select lf.tipolf,lf.anomeslf,lf.especielf,lf.docinilf," );
				sql.append( "lf.docfimlf,lf.serielf,lf.codemitlf,lf.uflf,lf.dtemitlf," );
				sql.append( "lf.dteslf," );
				sql.append( "sum(lf.vlrcontabillf) as vlrcontabillf," );
				sql.append( "sum(lf.vlrbaseipilf) as vlrbaseipilf," );
				sql.append( "lf.aliqipilf," );
				sql.append( "sum(lf.vlripilf) as vlripilf," );
				sql.append( "sum(lf.vlrisentasipilf) as vlrisentasipilf," );
				sql.append( "sum(lf.vlroutrasipilf) as vlroutrasipilf," );
				sql.append( "lf.codnat,lf.codmodnota,c.cnpjcli,c.insccli, c.pessoacli, c.cpfcli, lf.situacaolf " );
				sql.append( "from lflivrofiscal lf,vdcliente c " );
				sql.append( "where lf.dtemitlf between ? and ? " );
				sql.append( "and lf.codemp=? and lf.codfilial=? " );
				sql.append( "and c.codcli=lf.codemitlf and c.codemp=lf.codempet " );
				sql.append( "and c.codfilial=lf.codfilialet and c.pessoacli='J' " );
				sql.append( "and lf.tipolf='S' and lf.serielf<>? " );
				sql.append( "group by 1,2,3,4,5,6,7,8,9,10,13,17,18,19,20,21,22,23 " );
				sql.append( "order by lf.dtemitlf,lf.docinilf" );

				ps = con.prepareStatement( sql.toString() );
				ps.setDate( 1, Funcoes.dateToSQLDate( txtDataini.getVlrDate() ) );
				ps.setDate( 2, Funcoes.dateToSQLDate( txtDatafim.getVlrDate() ) );
				ps.setInt( 3, iCodEmp );
				ps.setInt( 4, ListaCampos.getMasterFilial( "LFLIVROFISCAL" ) );
				ps.setString( 5, serieecf );
				rs = ps.executeQuery();

				lbAnd.setText( "Gerando Saídas..." );

				while ( rs.next() ) {

					buffer.delete( 0, buffer.length() );
					if ( "F".equals( rs.getString( "PESSOACLI" ) ) ) {
						cnpjcli = StringFunctions.replicate( "0", 14 );// rs.getString( "CPFCLI" );
						insccli = StringFunctions.replicate( "0", 14 );
					}
					else {
						cnpjcli = rs.getString( "CNPJCLI" );
						insccli = rs.getString( "INSCCLI" );

						if ( ( insccli == null ) || ( "ISENTA".equals( insccli.trim() ) ) ) {
							insccli = "ISENTO";
						}

					}

					/* 01 */buffer.append( "51" );
					/* 02 */buffer.append( Funcoes.adicionaEspacos( cnpjcli, 14 ) );
					/* 03 */buffer.append( Funcoes.adicionaEspacos( StringFunctions.clearString( insccli ), 14 ) );
					/* 04 */buffer.append( Funcoes.dataAAAAMMDD( Funcoes.sqlDateToDate( rs.getDate( "DTEMITLF" ) ) ) );
					/* 05 */buffer.append( Funcoes.adicionaEspacos( rs.getString( "UFLF" ), 2 ) );
					/* 06 */buffer.append( Funcoes.adicionaEspacos( rs.getString( "SERIELF" ), 3 ) );
					/* 08 */buffer.append( StringFunctions.strZero( rs.getInt( "DOCINILF" ) + "", 6 ) );
					/* 09 */buffer.append( Funcoes.adicionaEspacos( rs.getString( "CODNAT" ), ( sConvenio.equals( "1" ) ? 3 : 4 ) ) );
					/* 10 */buffer.append( Funcoes.transValor( rs.getString( "VLRCONTABILLF" ), 13, 2, true ) );
					/* 11 */buffer.append( Funcoes.transValor( rs.getString( "VLRIPILF" ), 13, 2, true ) );
					/* 12 */buffer.append( Funcoes.transValor( rs.getString( "VLRISENTASIPILF" ), 13, 2, true ) );
					/* 13 */buffer.append( Funcoes.transValor( rs.getString( "VLROUTRASIPILF" ), 13, 2, true ) );
					/* 14 */buffer.append( StringFunctions.replicate( " ", 20 ) );
					/* 15 */buffer.append( rs.getString( "SITUACAOLF" ) + CR );

					gravaBuffer( buffer.toString() );
					cont++;
				}

				rs.close();
				ps.close();

				con.commit();

			}

		} catch ( Exception e ) {
			e.printStackTrace();
			Funcoes.mensagemErro( this, "Erro ao gerar registro 51!\n" + e.getMessage(), true, con, e );
		}

		return cont;

	}

	/* *********************************************
	 * REGISTRO 53 LIVROS FISCAIS DE ENTRADA/SAIDA REFERENTES A SUBSTITUIÇÃO TRIBUTÁRIA*********************************************
	 */
	private int geraRegistro53( String serieecf ) {

		PreparedStatement ps;
		String cnpjcli = "";
		String insccli = "";
		ResultSet rs;
		StringBuffer sql = new StringBuffer();
		StringBuffer buffer = new StringBuffer();
		String sConvenio = rgConvenio.getVlrString();
		int cont = 0;

		try {

			if ( "S".equals( cbSaida.getVlrString() ) ) {

				// LIVROS FISCAIS DE SAIDA

				sql.delete( 0, sql.length() );
				sql.append( "select lf.tipolf,lf.anomeslf,lf.especielf,lf.docinilf," );
				sql.append( "lf.docfimlf,lf.serielf,lf.codemitlf,lf.uflf,lf.dtemitlf," );
				sql.append( "lf.dteslf," );
				sql.append( "sum(lf.vlrcontabillf) as vlrcontabillf," );
				sql.append( "sum(lf.vlrbaseipilf) as vlrbaseipilf," );
				sql.append( "lf.aliqipilf," );
				sql.append( "sum(lf.vlripilf) as vlripilf," );
				sql.append( "sum(lf.vlrisentasipilf) as vlrisentasipilf," );
				sql.append( "sum(lf.vlroutrasipilf) as vlroutrasipilf," );
				sql.append( "lf.codnat,lf.codmodnota,c.cnpjcli,c.insccli, c.pessoacli, c.cpfcli, lf.situacaolf," );
				sql.append( "sum(lf.vlrbaseicmsstlf) as vlrbaseicmsstlf," );
				sql.append( "sum(lf.vlricmsstlf) as vlricmsstlf," );
				sql.append( "sum(lf.vlracessoriaslf) as vlracessoriaslf " );
				sql.append( "from lflivrofiscal lf,vdcliente c " );
				sql.append( "where lf.dtemitlf between ? and ? " );
				sql.append( "and lf.codemp=? and lf.codfilial=? " );
				sql.append( "and c.codcli=lf.codemitlf and c.codemp=lf.codempet " );
				sql.append( "and c.codfilial=lf.codfilialet and c.pessoacli='J' " );
				sql.append( "and lf.tipolf='S' and lf.serielf<>? " );
				sql.append( "and lf.vlricmsstlf>0 " );
				sql.append( "group by 1,2,3,4,5,6,7,8,9,10,13,17,18,19,20,21,22,23 " );
				sql.append( "order by lf.dtemitlf,lf.docinilf" );

				System.out.println("SQL REG53:" + sql.toString());
				
				ps = con.prepareStatement( sql.toString() );
				ps.setDate( 1, Funcoes.dateToSQLDate( txtDataini.getVlrDate() ) );
				ps.setDate( 2, Funcoes.dateToSQLDate( txtDatafim.getVlrDate() ) );
				ps.setInt( 3, iCodEmp );
				ps.setInt( 4, ListaCampos.getMasterFilial( "LFLIVROFISCAL" ) );
				ps.setString( 5, serieecf );
				rs = ps.executeQuery();

				lbAnd.setText( "Gerando Saídas..." );

				while ( rs.next() ) {

					buffer.delete( 0, buffer.length() );
					if ( "F".equals( rs.getString( "PESSOACLI" ) ) ) {
						cnpjcli = StringFunctions.replicate( "0", 14 );// rs.getString( "CPFCLI" );
						insccli = StringFunctions.replicate( "0", 14 );
					}
					else {
						cnpjcli = rs.getString( "CNPJCLI" );
						insccli = rs.getString( "INSCCLI" );

						if ( ( insccli == null ) || ( "ISENTA".equals( insccli.trim() ) ) ) {
							insccli = "ISENTO";
						}

					}

					/* 01 */buffer.append( "53" );
					/* 02 */buffer.append( Funcoes.adicionaEspacos( cnpjcli, 14 ) );
					/* 03 */buffer.append( Funcoes.adicionaEspacos( StringFunctions.clearString( insccli ), 14 ) );
					/* 04 */buffer.append( Funcoes.dataAAAAMMDD( Funcoes.sqlDateToDate( rs.getDate( "DTEMITLF" ) ) ) );
					/* 05 */buffer.append( Funcoes.adicionaEspacos( rs.getString( "UFLF" ), 2 ) );
					/* 06 */buffer.append( StringFunctions.strZero( String.valueOf( rs.getInt( "CODMODNOTA" ) ), 2 ) );
					/* 07 */buffer.append( Funcoes.adicionaEspacos( rs.getString( "SERIELF" ), 3 ) );
					/* 09 */buffer.append( StringFunctions.strZero( rs.getInt( "DOCINILF" ) + "", 6 ) );
					/* 10 */buffer.append( Funcoes.adicionaEspacos( rs.getString( "CODNAT" ), ( sConvenio.equals( "1" ) ? 3 : 4 ) ) );
					/* 10 */buffer.append( ( sConvenio.equals( "1" ) ? "" : "P" ) ); // Emitente da nota fiscal P-Própio ou T-Terceiros
					/* 11 */buffer.append( Funcoes.transValor( rs.getString( "VLRBASEICMSSTLF" ), 13, 2, true ) );
					/* 12 */buffer.append( Funcoes.transValor( rs.getString( "VLRICMSSTLF" ), 13, 2, true ) );
					/* 13 */buffer.append( Funcoes.transValor( rs.getString( "VLRACESSORIASLF" ), 13, 2, true ) );
					/* 14 */buffer.append( rs.getString( "SITUACAOLF" ) );
					/* 15 */buffer.append( StringFunctions.replicate( " ", 30 ) + CR );

					gravaBuffer( buffer.toString() );
					cont++;
				}

				rs.close();
				ps.close();

				con.commit();

			}

		} catch ( Exception e ) {
			e.printStackTrace();
			Funcoes.mensagemErro( this, "Erro ao gerar registro 50!\n" + e.getMessage(), true, con, e );
		}

		return cont;

	}

	/* *********************************************
	 * REGISTRO 70 LIVROS FISCAIS DE ENTRADA/SAIDA REFERENTES A CONHECIMENTOS DE FRETE*********************************************
	 */
	private int geraRegistro70() {

		PreparedStatement ps;
		String cnpj = "";
		String insc = "";
		ResultSet rs;
		StringBuffer sql = new StringBuffer();
		StringBuffer buffer = new StringBuffer();
		String sConvenio = rgConvenio.getVlrString();
		int cont = 0;

		try {

			if ( ( "S".equals( cbFrete.getVlrString() ) ) ) {

				sql.append( "select tr.cnpjtran, tr.insctran, fr.dtemitfrete, coalesce(tr.siglauf, tr.uftran) as uf," );
				sql.append( "tm.codmodnota,fr.serie,fr.docfrete,fr.codnat,fr.vlrfrete,fr.vlrbaseicmsfrete,fr.vlricmsfrete," );
				sql.append( "fr.tipofrete " );
				sql.append( "from lffrete fr, vdtransp tr, eqtipomov tm " );
				sql.append( "where " );
				sql.append( "tr.codemp=fr.codemptn and tr.codfilial=fr.codfilialtn and tr.codtran=fr.codtran " );
				sql.append( "and tm.codemp=fr.codemptm and tm.codfilial=fr.codfilialtm and tm.codtipomov=fr.codtipomov " );
				sql.append( "and fr.codemp=? and fr.codfilial=? and fr.dtemitfrete between ? and ? " );
				sql.append( "order by fr.dtemitfrete" );

				System.out.println( "Registro70:" + sql.toString() );

				ps = con.prepareStatement( sql.toString() );

				ps.setInt( 1, iCodEmp );
				ps.setInt( 2, ListaCampos.getMasterFilial( "LFFRETE" ) );
				ps.setDate( 3, Funcoes.dateToSQLDate( txtDataini.getVlrDate() ) );
				ps.setDate( 4, Funcoes.dateToSQLDate( txtDatafim.getVlrDate() ) );

				rs = ps.executeQuery();

				lbAnd.setText( "Gerando Conhecimentos de frete..." );

				while ( rs.next() ) {

					buffer.delete( 0, buffer.length() );

					cnpj = rs.getString( "CNPJTRAN" );
					insc = rs.getString( "INSCTRAN" );

					if ( ( insc == null ) || ( "ISENTA".equals( insc.trim() ) ) ) {
						insc = "ISENTO";
					}

					/* 01 */buffer.append( "70" );
					/* 02 */buffer.append( Funcoes.adicionaEspacos( cnpj, 14 ) );
					/* 03 */buffer.append( Funcoes.adicionaEspacos( StringFunctions.clearString( insc ), 14 ) );
					/* 04 */buffer.append( Funcoes.dataAAAAMMDD( Funcoes.sqlDateToDate( rs.getDate( "dtemitfrete" ) ) ) );
					/* 05 */buffer.append( Funcoes.adicionaEspacos( rs.getString( "UF" ), 2 ) );
					/* 06 */buffer.append( StringFunctions.strZero( String.valueOf( rs.getInt( "CODMODNOTA" ) ), 2 ) );

					/* 07 */buffer.append( Funcoes.adicionaEspacos( rs.getString( "SERIE" ), 3 ) );

					/* 09 */buffer.append( StringFunctions.strZero( rs.getInt( "DOCFRETE" ) + "", 6 ) );
					/* 10 */buffer.append( Funcoes.adicionaEspacos( rs.getString( "CODNAT" ), ( sConvenio.equals( "1" ) ? 3 : 4 ) ) );

					/* 11 */buffer.append( Funcoes.transValor( rs.getString( "VLRFRETE" ), 13, 2, true ) );
					/* 12 */buffer.append( Funcoes.transValor( rs.getString( "VLRBASEICMSFRETE" ), 14, 2, true ) );
					/* 13 */buffer.append( Funcoes.transValor( rs.getString( "VLRICMSFRETE" ), 14, 2, true ) );
					/* 14 */buffer.append( Funcoes.transValor( "0", 14, 2, true ) ); // Isentas
					/* 15 */buffer.append( Funcoes.transValor( "0", 14, 2, true ) ); // Outras
					/* 16 */buffer.append( Funcoes.adicionaEspacos( rs.getString( "TIPOFRETE" ), 1 ) );
					/* 17 */buffer.append( "N" + CR );

					gravaBuffer( buffer.toString() );
					cont++;

				}

				rs.close();
				ps.close();

				con.commit();
			}

		} catch ( Exception e ) {
			e.printStackTrace();
			Funcoes.mensagemErro( this, "Erro ao gerar registro 70!\n" + e.getMessage(), true, con, e );
		}

		return cont;

	}

	private String getSerieECF() throws SQLException {

		String serie = "";
		StringBuffer sql = new StringBuffer();
		ResultSet rs;
		PreparedStatement ps;
		sql.append( "SELECT TM.SERIE FROM SGPREFERE4 P, EQTIPOMOV TM " );
		sql.append( "WHERE P.CODEMP=? AND P.CODFILIAL=? AND  " );
		sql.append( "TM.CODEMP=P.CODEMPTM AND TM.CODFILIAL=P.CODFILIALTM AND  " );
		sql.append( "TM.CODTIPOMOV=P.CODTIPOMOV " );
		ps = con.prepareStatement( sql.toString() );
		ps.setInt( 1, Aplicativo.iCodEmp );
		ps.setInt( 2, ListaCampos.getMasterFilial( "SGPREFERE4" ) );
		rs = ps.executeQuery();
		if ( rs.next() ) {
			serie = rs.getString( "SERIE" );
		}
		rs.close();
		ps.close();
		con.commit();
		return serie;
	}

	private int geraRegistro54( String serieecf ) {

		PreparedStatement ps;
		ResultSet rs;
		StringBuffer sql = new StringBuffer();
		StringBuffer buffer = new StringBuffer();
		String sConvenio = rgConvenio.getVlrString();
		String cnpjcli = "";
		String sDocTmp = "";
		int iOrdem = 0;
		int cont = 0;

		try {

			if ( "S".equals( cbEntrada.getVlrString() ) ) {

				// REGISTRO 54 INFORMAÇÕES DOS ITENS DAS NOTAS FISCAIS DE ENTRADA

				sql.append( "select c.dtentcompra,c.doccompra,ic.coditcompra,c.codfor " );
				sql.append( ", f.uffor,f.cnpjfor,ic.codnat,ic.codprod,p.refprod " );
				sql.append( ", c.dtemitcompra,c.serie,tm.codmodnota,ic.percicmsitcompra " );
				sql.append( ", (case when ic.qtditcompracanc>0 then ic.qtditcompracanc else ic.qtditcompra end) qtditcompra");
				sql.append( ", ic.vlrliqitcompra,ic.vlrbaseicmsitcompra" );
				sql.append( ", ic.percicmsitcompra,ic.vlrbaseicmsitcompra,ic.vlripiitcompra " );
				sql.append( ", cf.origfisc, cf.codtrattrib, c.codcompra, f.cpffor, f.pessoafor " );
				sql.append( "from cpcompra c,cpforneced f,cpitcompra ic,eqtipomov tm,eqproduto p, lfitclfiscal cf " );
				sql.append( "where c.dtentcompra between ? and ? and c.codemp=? and c.codfilial=? " );
				sql.append( "and ic.codcompra=c.codcompra and  ic.codemp=c.codemp and ic.codfilial=c.codfilial " );
				sql.append( "and tm.codtipomov=c.codtipomov and tm.codemp=c.codemptm and tm.codfilial=c.codfilialtm " );
				sql.append( "and f.codfor=c.codfor and f.codemp=c.codempfr and f.codfilial=c.codfilialfr " );
				sql.append( "and p.codprod=ic.codprod and p.codemp=ic.codemppd and p.codfilial=ic.codfilialpd " );
				sql.append( "and cf.codfisc=p.codfisc and cf.codemp=p.codempfc and cf.codfilial=p.codfilialfc and cf.coditfisc=ic.coditfisc " );
				sql.append( "and tm.fiscaltipomov='S' "); // compras de pessoa fisica devem entrar... f.pessoafor='j' " +
				sql.append( "and p.tipoprod<>'O' "); 
				sql.append( "order by c.dtentcompra,c.doccompra,ic.coditcompra" );

				ps = con.prepareStatement( sql.toString() );
				ps.setDate( 1, Funcoes.dateToSQLDate( txtDataini.getVlrDate() ) );
				ps.setDate( 2, Funcoes.dateToSQLDate( txtDatafim.getVlrDate() ) );
				ps.setInt( 3, iCodEmp );
				ps.setInt( 4, ListaCampos.getMasterFilial( "CPCOMPRA" ) );
				rs = ps.executeQuery();

				lbAnd.setText( "Gerando Itens NF Entrada..." );

				sDocTmp = "";

				while ( rs.next() ) {
					if ( !sDocTmp.equals( "" + rs.getInt( "DOCCOMPRA" ) ) ) {
						iOrdem = 1;
					}

					/* 01 */buffer.append( "54" );
					if("J".equals( rs.getString( "PESSOAFOR" ) )) {
					/* 02 */buffer.append( Funcoes.adicionaEspacos( rs.getString( "CNPJFOR" ), 14 ) );
					}
					else {
					/* 02 */buffer.append( StringFunctions.strZero( rs.getString( "CPFFOR" ), 14 ) );
					}
					/* 03 */buffer.append( StringFunctions.strZero( String.valueOf( rs.getString( "CODMODNOTA" ) == null ? 0 : rs.getInt( "CODMODNOTA" ) ), 2 ) );
					/* 04 */buffer.append( Funcoes.adicionaEspacos( rs.getString( "SERIE" ), 3 ) );
					// buffer.append( "  "); //Subsérie
					/* 05 */buffer.append( StringFunctions.strZero( String.valueOf( rs.getInt( "DOCCOMPRA" ) ), 6 ) );
					/* 06 */buffer.append( Funcoes.adicionaEspacos( rs.getString( "CODNAT" ), ( sConvenio.equals( "1" ) ? 3 : 4 ) ) );
					/* 07 */buffer.append( Funcoes.copy( ( sConvenio.equals( "1" ) ? "" : ( rs.getString( "ORIGFISC" ).trim() + rs.getString( "CODTRATTRIB" ).trim() ) ), 3 ) );
					/* 08 */buffer.append( StringFunctions.strZero( String.valueOf( rs.getInt( "CODITCOMPRA" ) ), 3 ) );
					/* 09 */buffer.append( Funcoes.adicionaEspacos( rs.getString( ( sUsaRefProd.equals( "S" ) ? "REFPROD" : "CODPROD" ) ), 14 ) );
					/* 10 */buffer.append( Funcoes.transValor( rs.getString( "QTDITCOMPRA" ), ( sConvenio.equals( "1" ) ? 13 : 11 ), 3, true ) );
					/* 11 */buffer.append( Funcoes.transValor( rs.getString( "VLRLIQITCOMPRA" ), 12, 2, true ) );
					/* 12 */buffer.append( Funcoes.transValor( "0", 12, 2, true ) );
					/* 13 */buffer.append( Funcoes.transValor( rs.getString( "VLRBASEICMSITCOMPRA" ), 12, 2, true ) );
					/* 14 */buffer.append( Funcoes.transValor( "0", 12, 2, true ) );
					/* 15 */buffer.append( Funcoes.transValor( rs.getString( "VLRIPIITCOMPRA" ), 12, 2, true ) );
					/* 16 */buffer.append( Funcoes.transValor( rs.getString( "PERCICMSITCOMPRA" ), 4, 2, true ) + CR );

					gravaBuffer( buffer.toString() );
					buffer.delete( 0, buffer.length() );
					sDocTmp = String.valueOf( rs.getInt( "DOCCOMPRA" ) );
					iOrdem++;
					cont++;
				}

				rs.close();
				ps.close();

				con.commit();

			}

			if ( "S".equals( cbSaida.getVlrString() ) ) {

				// REGISTRO 54 INFORMAÇÕES DOS ITENS DAS NOTAS FISCAIS DE SAÍDA

				sql.delete( 0, sql.length() );
				sql.append( "select v.dtsaidavenda,v.docvenda,iv.coditvenda,v.codcli," );
				sql.append( "c.ufcli, c.pessoacli, c.cnpjcli, c.cpfcli, iv.codnat,iv.codprod,p.refprod," );
				sql.append( "v.dtemitvenda,v.serie,tm.codmodnota,iv.percicmsitvenda," );
				sql.append( "(case when iv.qtditvendacanc>0 then iv.qtditvendacanc else iv.qtditvenda end) qtditvenda,");
				sql.append( "iv.vlrliqitvenda,iv.vlrbaseicmsitvenda," );
				sql.append( "iv.percicmsitvenda,iv.vlrbaseicmsitvenda,iv.vlripiitvenda," );
				sql.append( "cf.origfisc,cf.codtrattrib " );
				sql.append( "from vdvenda v,vdcliente c,vditvenda iv,eqtipomov tm,eqproduto p,lfitclfiscal cf " );
				sql.append( "where v.dtemitvenda between ? and ? " );
				sql.append( "and v.codemp=? and v.codfilial=? and v.tipovenda='V' " );
				sql.append( "and iv.codemp=v.codemp and iv.codfilial=v.codfilial and iv.tipovenda=v.tipovenda " );
				sql.append( "and iv.codvenda=v.codvenda ");
				sql.append( "and tm.codemp=v.codemptm and tm.codfilial=v.codfilialtm and tm.codtipomov=v.codtipomov and tm.fiscaltipomov='S' " );
				sql.append( "and c.codemp=v.codempcl and c.codfilial=v.codfilialcl and c.codcli=v.codcli ");
				//and c.pessoacli in ('J'");
				//if ("S".equals( cbConsumidor.getVlrString() )) {
					//sql.append(",'F'");
				//}
				//sql.append(") ");
				sql.append( "and p.codemp=iv.codemppd and p.codfilial=iv.codfilialpd and p.codprod=iv.codprod ");
				sql.append( "and cf.codfisc=iv.codfisc and cf.codemp=iv.codempif and cf.codfilial=iv.codfilialif and cf.coditfisc=iv.coditfisc " );
				sql.append( " and p.tipoprod<>'O' ");
				sql.append( "order by v.dtemitvenda,v.docvenda,iv.coditvenda" );

				System.out.println("SQL REG54:" + sql.toString());
				
				ps = con.prepareStatement( sql.toString() );
				ps.setDate( 1, Funcoes.dateToSQLDate( txtDataini.getVlrDate() ) );
				ps.setDate( 2, Funcoes.dateToSQLDate( txtDatafim.getVlrDate() ) );
				ps.setInt( 3, iCodEmp );
				ps.setInt( 4, ListaCampos.getMasterFilial( "VDVENDA" ) );
				rs = ps.executeQuery();

				lbAnd.setText( "Gerando Itens NF Saída..." );

				sDocTmp = "";

				while ( rs.next() ) {

					if ( !sDocTmp.equals( String.valueOf( rs.getInt( "DOCVENDA" ) ) ) ) {
						iOrdem = 1;
					}

					buffer.delete( 0, buffer.length() );

					if ( "F".equals( rs.getString( "PESSOACLI" ) ) ) {
						cnpjcli = StringFunctions.replicate( rs.getString( "CPFCLI" ), 14);
					}
					else {
						cnpjcli = rs.getString( "CNPJCLI" );
					}

					/* 01 */buffer.append( "54" );
					/* 02 */buffer.append( Funcoes.adicionaEspacos( cnpjcli, 14 ) );
					/* 03 */buffer.append( StringFunctions.strZero( String.valueOf( rs.getString( "CODMODNOTA" ) == null ? 0 : rs.getInt( "CODMODNOTA" ) ), 2 ) );
					/* 04 */buffer.append( Funcoes.adicionaEspacos( rs.getString( "SERIE" ), 3 ) );
					/* 05 */buffer.append( ( sConvenio.equals( "1" ) ? StringFunctions.replicate( " ", 2 ) : "" ) ); // Sub serie
					/* 06 */buffer.append( StringFunctions.strZero( String.valueOf( rs.getInt( "DOCVENDA" ) ), 6 ) );
					/* 07 */buffer.append( Funcoes.adicionaEspacos( rs.getString( "CODNAT" ), ( sConvenio.equals( "1" ) ? 3 : 4 ) ) );
					/* 08 */buffer.append( Funcoes.copy( ( sConvenio.equals( "1" ) ? "" : rs.getString( "ORIGFISC" ).trim() + rs.getString( "CODTRATTRIB" ).trim() ), 3 ) );
					/* 09 */buffer.append( StringFunctions.strZero( String.valueOf( rs.getInt( "CODITVENDA" ) ), 3 ) );
					/* 10 */buffer.append( Funcoes.adicionaEspacos( rs.getString( ( sUsaRefProd.equals( "S" ) ? "REFPROD" : "CODPROD" ) ), 14 ) );
					/* 11 */buffer.append( Funcoes.transValor( rs.getString( "QTDITVENDA" ), ( sConvenio.equals( "1" ) ? 13 : 11 ), 3, true ) );
					/* 12 */buffer.append( Funcoes.transValor( rs.getString( "VLRLIQITVENDA" ), 12, 2, true ) );
					/* 13 */buffer.append( Funcoes.transValor( "0", 12, 2, true ) );
					/* 14 */buffer.append( Funcoes.transValor( rs.getString( "VLRBASEICMSITVENDA" ), 12, 2, true ) );
					/* 15 */buffer.append( Funcoes.transValor( "0", 12, 2, true ) );
					/* 16 */buffer.append( Funcoes.transValor( rs.getString( "VLRIPIITVENDA" ), 12, 2, true ) );
					/* 17 */buffer.append( Funcoes.transValor( rs.getString( "PERCICMSITVENDA" ), 4, 2, true ) + CR );

					gravaBuffer( buffer.toString() );
					iOrdem++;
					cont++;

					sDocTmp = String.valueOf( rs.getInt( "DOCVENDA" ) );

				}

				rs.close();
				ps.close();

				con.commit();

			}

		} catch ( Exception e ) {
			e.printStackTrace();
			Funcoes.mensagemErro( this, "Erro ao gerar registro 54!\n" + e.getMessage(), true, con, e );
		}

		return cont;

	}

	private int geraRegistro60() {

		PreparedStatement ps;
		ResultSet rs;
		StringBuffer sql = new StringBuffer();
		StringBuffer buffer = new StringBuffer();
		String sAliq = "";
		String sCampo = "";
		float fValor = 0;
		int cont = 0;
		int codvenda = 0;

		if ( "S".equals( cbSaida.getVlrString() ) ) {

			try {
				// REGISTRO 60 INFORMAÇÕES DOS ITENS DAS NOTAS FISCAIS DE SAÍDA POR ECF

				sql.delete( 0, sql.length() );

				sql.append( "select first 1 v.codvenda " );
				sql.append( "from vditvenda i, vdvenda v " );
				sql.append( "where i.codemp=? and i.codfilial=? and i.tipovenda='E' " );
				sql.append( "and v.codemp=i.codemp and v.codfilial=i.codfilial " );
				sql.append( "and v.codvenda=i.codvenda and v.tipovenda=i.tipovenda " );
				sql.append( "and v.dtemitvenda between ? and ? " );
				ps = con.prepareStatement( sql.toString() );
				ps.setInt( 1, iCodEmp );
				ps.setInt( 2, ListaCampos.getMasterFilial( "VDITVENDA" ) );
				ps.setDate( 3, Funcoes.dateToSQLDate( txtDataini.getVlrDate() ) );
				ps.setDate( 4, Funcoes.dateToSQLDate( txtDatafim.getVlrDate() ) );
				rs = ps.executeQuery();
				if ( rs.next() ) {
					codvenda = rs.getInt( "CODVENDA" );
				}
				rs.close();
				ps.close();
				con.commit();
				if ( codvenda == 0 ) {
					return codvenda;
				}

				sql.delete( 0, sql.length() );
				sql.append( "select l.dtlx, l.codcaixa, l.primcupomlx, l.ultcupomlx, l.numredlx, l.tgtotal,l.vlrcontabillx," );
				sql.append( "( select i.nserieimp from pvcaixa c, sgestacaoimp ei, sgimpressora i " );
				sql.append( "        where c.codemp=l.codemp and c.codfilial=l.codfilial and c.codcaixa=l.codcaixa " );
				sql.append( "        and ei.codemp=c.codempet and ei.codfilial=c.codfilialet and ei.codest=c.codest " );
				sql.append( "		 and i.codemp=ei.codempip and i.codfilial=ei.codfilialip and i.codimp=ei.codimp and i.tipoimp in (6,8) ) as numserieimp," );
				sql.append( "l.tsubstituicao,l.tisencao,l.tnincidencia," );
				sql.append( "l.aliq01,l.aliq02,l.aliq03,l.aliq04,l.aliq05,l.aliq06,l.aliq07,l.aliq08," );
				sql.append( "l.aliq09,l.aliq10,l.aliq11,l.aliq01,l.aliq12,l.aliq13,l.aliq14,l.aliq15,l.aliq16," );
				sql.append( "l.tt01,l.tt02,l.tt03,l.tt04,l.tt05,l.tt06,l.tt07,l.tt08," );
				sql.append( "l.tt09,l.tt10,l.tt11,l.tt12,l.tt13,l.tt14,l.tt15,l.tt16 " );
				sql.append( "from pvleiturax l " );
				sql.append( "where l.dtlx between ? and ? " );
				sql.append( "and l.codemp=? and l.codfilial=? " );
				sql.append( "order by l.dtlx" );

				ps = con.prepareStatement( sql.toString() );
				ps.setDate( 1, Funcoes.dateToSQLDate( txtDataini.getVlrDate() ) );
				ps.setDate( 2, Funcoes.dateToSQLDate( txtDatafim.getVlrDate() ) );
				ps.setInt( 3, iCodEmp );
				ps.setInt( 4, ListaCampos.getMasterFilial( "PVLEITURAX" ) );
				rs = ps.executeQuery();

				lbAnd.setText( "Gerando registro de ECF..." );

				while ( rs.next() ) {

					/* 01 */buffer.append( "60" );
					/* 02 */buffer.append( "M" );
					/* 03 */buffer.append( Funcoes.dataAAAAMMDD( Funcoes.sqlDateToDate( rs.getDate( "DTLX" ) ) ) );
					/* 04 */buffer.append( Funcoes.adicionaEspacos( rs.getString( "NUMSERIEIMP" ), 20 ) );
					/* 05 */buffer.append( StringFunctions.strZero( String.valueOf( rs.getInt( "CODCAIXA" ) ), 3 ) );
					/* 06 */buffer.append( "2D" ); // por se tratar de emição por ECF.
					/* 07 */buffer.append( StringFunctions.strZero( String.valueOf( rs.getInt( "PRIMCUPOMLX" ) ), 6 ) );
					/* 08 */buffer.append( StringFunctions.strZero( String.valueOf( rs.getInt( "ULTCUPOMLX" ) ), 6 ) );
					/* 09 */buffer.append( StringFunctions.strZero( String.valueOf( rs.getInt( "NUMREDLX" ) ), 6 ) );
					/* ?? */buffer.append( "000" );
					/* 10 */buffer.append( Funcoes.transValor( String.valueOf( rs.getInt( "VLRCONTABILLX" ) ), 16, 2, true ) );
					/* 11 */buffer.append( Funcoes.transValor( String.valueOf( rs.getInt( "TGTOTAL" ) ), 16, 2, true ) );
					/* 12 */buffer.append( StringFunctions.replicate( " ", 37 ) + CR );

					gravaBuffer( buffer.toString() );
					buffer.delete( 0, buffer.length() );
					cont++;

					// 19 é o número de aliquotas.
					for ( int i = 1; i <= 19; i++ ) {

						fValor = 0;

						if ( i <= 16 ) {
							sCampo = "ALIQ" + StringFunctions.strZero( String.valueOf( i ), 2 );
							sAliq = rs.getString( sCampo );
							sCampo = "TT" + StringFunctions.strZero( String.valueOf( i ), 2 );
							fValor = rs.getFloat( sCampo );
						}
						else {
							if ( i == 17 ) {
								sAliq = "F   ";
								sCampo = "TSUBSTITUICAO";
							}
							else if ( i == 18 ) {
								sAliq = "I   ";
								sCampo = "TISENCAO";
							}
							else if ( i == 19 ) {
								sAliq = "N   ";
								sCampo = "TNINCIDENCIA";
							}
							fValor = rs.getFloat( sCampo );
						}

						if ( ( sAliq == null ) || ( fValor == 0 ) )
							continue;

						/* 01 */buffer.append( "60" );
						/* 02 */buffer.append( "A" );
						/* 03 */buffer.append( Funcoes.dataAAAAMMDD( Funcoes.sqlDateToDate( rs.getDate( "DTLX" ) ) ) );
						/* 04 */buffer.append( Funcoes.adicionaEspacos( rs.getString( "NUMSERIEIMP" ), 20 ) );

						if ( i >= 16 )
							/* 05 */buffer.append( Funcoes.adicionaEspacos( sAliq, 4 ) );
						else
							/* 05 */buffer.append( Funcoes.transValor( sAliq, 4, 2, true ) );

						/* 06 */buffer.append( Funcoes.transValor( String.valueOf( fValor ), 12, 2, true ) );
						/* 07 */buffer.append( StringFunctions.replicate( " ", 79 ) + CR );

						gravaBuffer( buffer.toString() );
						buffer.delete( 0, buffer.length() );
						cont++;
					}
				}
				rs.close();
				ps.close();
				con.commit();

				sql.delete( 0, sql.length() );

				sql.append( "select extract (year from v.dtemitvenda) as ano, extract (month from v.dtemitvenda) as mes , " );
				sql.append( "i.codprod, i.qtditvenda, (i.qtditvenda * i.precoitvenda) as vlrbruto, i.vlrbaseicmsitvenda, " );
				sql.append( "i.tipofisc, i.percicmsitvenda " );
				sql.append( "from vditvenda i, vdvenda v " );
				sql.append( "where i.codemp=? and i.codfilial=? and i.tipovenda='E' " );
				sql.append( "and v.codemp=i.codemp and v.codfilial=i.codfilial " );
				sql.append( "and v.codvenda=i.codvenda and v.tipovenda=i.tipovenda " );
				sql.append( "and v.dtemitvenda between ? and ? " );
				sql.append( "group by 3,1,2,8,7,4,5,6 " );
				sql.append( "order by 3,1,2,8,7,4,5,6" );

				ps = con.prepareStatement( sql.toString() );
				ps.setInt( 1, iCodEmp );
				ps.setInt( 2, ListaCampos.getMasterFilial( "VDITVENDA" ) );
				ps.setDate( 3, Funcoes.dateToSQLDate( txtDataini.getVlrDate() ) );
				ps.setDate( 4, Funcoes.dateToSQLDate( txtDatafim.getVlrDate() ) );
				rs = ps.executeQuery();

				lbAnd.setText( "Gerando registro dos itens de ECF..." );

				while ( rs.next() ) {

					buffer.delete( 0, buffer.length() );

					/* 01 */buffer.append( "60" );
					/* 02 */buffer.append( "R" );
					/* 03 */buffer.append( StringFunctions.strZero( rs.getString( "MES" ), 2 ) );
					/* 03 */buffer.append( StringFunctions.strZero( rs.getString( "ANO" ), 4 ) );
					/* 04 */buffer.append( Funcoes.adicionaEspacos( rs.getString( "CODPROD" ).trim(), 14 ) );
					/* 05 */buffer.append( Funcoes.transValor( rs.getString( "QTDITVENDA" ), 13, 3, true ) );
					/* 06 */buffer.append( Funcoes.transValor( String.valueOf( rs.getBigDecimal( "VLRBRUTO" ).setScale( 2, BigDecimal.ROUND_HALF_UP ) ), 16, 2, true ) );
					/* 07 */buffer.append( Funcoes.transValor( rs.getString( "VLRBASEICMSITVENDA" ), 16, 2, true ) );

					if ( "TT".equals( rs.getString( "TIPOFISC" ).trim() ) ) {
						/* 08 */buffer.append( Funcoes.transValor( rs.getString( "PERCICMSITVENDA" ), 4, 2, true ) );
					}
					else {
						/* 08 */buffer.append( Funcoes.copy( rs.getString( "TIPOFISC" ), 1 ) + "   " );
					}

					/* 09 */buffer.append( StringFunctions.replicate( " ", 54 ) + CR );

					gravaBuffer( buffer.toString() );
					cont++;
				}
				rs.close();
				ps.close();
				con.commit();
			} catch ( Exception e ) {
				e.printStackTrace();
				Funcoes.mensagemErro( this, "Erro ao gerar registro 60!\n" + e.getMessage(), true, con, e );
			}

		}

		return cont;

	}

	private int geraRegistro61( String serieecf ) {

		PreparedStatement ps;
		ResultSet rs;
		StringBuffer sql = new StringBuffer();
		StringBuffer buffer = new StringBuffer();
		String sConvenio = rgConvenio.getVlrString();
		int cont = 0;

		try {

			if ( "S".equals( cbConsumidor.getVlrString() ) && ("1".equals( sConvenio ) 
					//|| "3".equals( sConvenio ) 
					) ) {

				// REGISTRO 61 LIVROS FISCAIS DE SAIDA

				sql.append( "select lf.tipolf,lf.anomeslf,lf.especielf,lf.docinilf," );
				sql.append( "lf.docfimlf,lf.serielf,lf.codemitlf,lf.uflf,lf.dtemitlf," );
				sql.append( "lf.dteslf,lf.vlrcontabillf,lf.vlrbaseicmslf,lf.aliqicmslf," );
				sql.append( "lf.vlricmslf,lf.vlrisentasicmslf,lf.vlroutrasicmslf," );
				sql.append( "lf.codnat,lf.codmodnota,c.cnpjcli,c.insccli " );
				sql.append( "from lflivrofiscal lf,vdcliente c " );
				sql.append( "where lf.dtemitlf between ? and ? and lf.codemp=? and lf.codfilial=? and " );
				sql.append( "c.codcli=lf.codemitlf and c.codemp=lf.codempet and c.codfilial=lf.codfilialet and " );
				sql.append( "lf.tipolf='S' and c.pessoacli='F' and lf.serielf<>? " );
				sql.append( "order by lf.dtemitlf" );

				System.out.println("SQL REG61:" + sql.toString());
				
				ps = con.prepareStatement( sql.toString() );
				ps.setDate( 1, Funcoes.dateToSQLDate( txtDataini.getVlrDate() ) );
				ps.setDate( 2, Funcoes.dateToSQLDate( txtDatafim.getVlrDate() ) );
				ps.setInt( 3, iCodEmp );
				ps.setInt( 4, ListaCampos.getMasterFilial( "LFLIVROFISCAL" ) );
				ps.setString( 5, serieecf );
				rs = ps.executeQuery();

				lbAnd.setText( "Gerando NF Saída (Consumidor)..." );

				while ( rs.next() ) {

					buffer.delete( 0, buffer.length() );

					buffer.append( "61" + StringFunctions.replicate( " ", 14 ) );
					buffer.append( StringFunctions.replicate( " ", 14 ) );
					buffer.append( Funcoes.dataAAAAMMDD( Funcoes.sqlDateToDate( rs.getDate( "DTEMITLF" ) ) ) );
					buffer.append( StringFunctions.strZero( rs.getInt( "CODMODNOTA" ) + "", 2 ) );
					buffer.append( Funcoes.adicionaEspacos( rs.getString( "SERIELF" ), 3 ) );
					buffer.append( StringFunctions.replicate( " ", 2 ) ); // Sub serie
					buffer.append( StringFunctions.strZero( rs.getInt( "DOCINILF" ) + "", 6 ) );
					buffer.append( StringFunctions.strZero( rs.getInt( "DOCFIMLF" ) + "", 6 ) );
					buffer.append( Funcoes.transValor( rs.getString( "VLRCONTABILLF" ), 13, 2, true ) );
					buffer.append( Funcoes.transValor( rs.getString( "VLRBASEICMSLF" ), 13, 2, true ) );
					buffer.append( Funcoes.transValor( rs.getString( "VLRICMSLF" ), 12, 2, true ) );
					buffer.append( Funcoes.transValor( rs.getString( "VLRISENTASICMSLF" ), 13, 2, true ) );
					buffer.append( Funcoes.transValor( rs.getString( "VLROUTRASICMSLF" ), 13, 2, true ) );
					buffer.append( Funcoes.transValor( rs.getString( "ALIQICMSLF" ), 4, 2, true ) + CR );

					gravaBuffer( buffer.toString() );
					cont++;
				}

				rs.close();
				ps.close();

				con.commit();

			}

		} catch ( Exception e ) {
			e.printStackTrace();
			Funcoes.mensagemErro( this, "Erro ao gerar registro 61!\n" + e.getMessage(), true, con, e );
		}

		return cont;

	}

	private int geraRegistro74() {

		PreparedStatement ps;
		ResultSet rs;
		StringBuilder sql = new StringBuilder();
		StringBuffer buffer = new StringBuffer();

		String sConvenio = rgConvenio.getVlrString();

		int iParam = 1;
		int cont = 0;

		try {

			if ( ( "S".equals( cbInventario.getVlrString() ) ) ) {

				// REGISTRO 74 INVENTARIO

				sql.append( "select p.codprod,p.refprod, p.saldo, p.vlrestoq, '1' codposse, f.cnpjfilial, f.inscfilial, f.siglauf " );
				sql.append( "from eqrelinvprodsp(?,?,?,null,null,null,?,null,null,null) p, sgfilial f, eqproduto pd " );
				sql.append( "left outer join lfitclfiscal cf on cf.codfisc=pd.codfisc and cf.codemp=pd.codempfc and cf.codfilial=pd.codfilialfc " );
				sql.append( "where p.saldo > 0 " );
				sql.append( "and f.codemp=? and f.codfilial=? " );
				sql.append( "and pd.codemp=? and pd.codfilial=? and pd.codprod=p.codprod and pd.ativoprod='S' and pd.tipoprod<>'O' and cf.geralfisc='S' and cf.ativoitfisc='S' " ); 
			
				// filtro para não incluir produtos do patrimônio. 
				sql.append( "and pd.tipoprod<>'O' " ); 

				sql.append( "order by " + ( sUsaRefProd.equals( "s" ) ? "p.refprod" : "p.codprod" ) );

				ps = con.prepareStatement( sql.toString() );

				ps.setInt( 1, Aplicativo.iCodEmp );
				ps.setInt( 2, ListaCampos.getMasterFilial( "EQPRODUTO" ) );
//				ps.setString( 3, "M" ); // Custo MPM
				ps.setString( 3, "P" ); // Custo PEPS
				ps.setDate( 4, Funcoes.dateToSQLDate( Funcoes.dateToSQLDate( txtDatafim.getVlrDate() ) ) );
				ps.setInt( 5, Aplicativo.iCodEmp );
				ps.setInt( 6, Aplicativo.iCodFilial );
				ps.setInt( 7, Aplicativo.iCodEmp );
				ps.setInt( 8, ListaCampos.getMasterFilial( "EQPRODUTO" ) );

				rs = ps.executeQuery();
				lbAnd.setText( "Gerando registro de inventário..." );

				while ( rs.next() ) {

					buffer.delete( 0, buffer.length() );

					btGerar.setEnabled( false );

					buffer.append( "74" );
					buffer.append( Funcoes.dataAAAAMMDD( txtDatafim.getVlrDate() ) );
					buffer.append( Funcoes.adicionaEspacos( rs.getString( ( sUsaRefProd.equals( "S" ) ? "REFPROD" : "CODPROD" ) ), 14 ) );
					buffer.append( Funcoes.transValor( rs.getString( "SALDO" ), 13, 3, true ) );
					buffer.append( Funcoes.transValor( rs.getString( "VLRESTOQ" ), 13, 2, true ) );
					buffer.append( rs.getString( "CODPOSSE" ) );
					buffer.append( "00000000000000" );
					buffer.append( StringFunctions.replicate( " ", 14 ) );
					buffer.append( rs.getString( "SIGLAUF" ) );
					buffer.append( StringFunctions.replicate( " ", 45 ) + CR );

					gravaBuffer( buffer.toString() );

					cont++;

				}

				gravaBuffer( buffer75.toString() ); // Gravando Buffer do registro 75 .

				rs.close();
				ps.close();
				con.commit();

			}

		} catch ( Exception e ) {
			e.printStackTrace();
			Funcoes.mensagemErro( this, "Erro ao gerar registro 74!\n" + e.getMessage(), true, con, e );
		}

		return cont;

	}

	private int geraRegistro75() {

		PreparedStatement ps;
		ResultSet rs;
		StringBuffer sql = new StringBuffer();
		StringBuffer buffer = new StringBuffer();
		final int COL_75_CODPROD = 1;
		final int COL_75_REFPROD = 2;
		final int COL_75_DESCPROD = 3;
		final int COL_75_PERCIPI = 4;
		final int COL_75_PERCICMS = 5;
		final int COL_75_CODUNID = 6;
		final int COL_75_ORIGFISC = 7;
		final int COL_75_CODTRATTRIB = 8;
		final int COL_75_CODNCM = 9;
		String sConvenio = rgConvenio.getVlrString();
		StringBuilder sqlentrada = new StringBuilder();
		StringBuilder sqlsaida = new StringBuilder();
		StringBuilder sqlconsumidor = new StringBuilder();
		StringBuilder sqlinventario = new StringBuilder();
		int iParam = 1;
		int cont = 0;

		try {

			if ( ( "S".equals( cbEntrada.getVlrString() ) ) || ( "S".equals( cbSaida.getVlrString() ) || "S".equals( cbConsumidor.getVlrString() ) || "S".equals( cbInventario.getVlrString() ) ) ) {
				// REGISTRO 75 TABELA DE PRODUTOS ENTRADAS, SAIDAS, CONSUMIDOR
				sqlentrada.delete( 0, sqlentrada.length() );
				sqlsaida.delete( 0, sqlsaida.length() );
				sqlconsumidor.delete( 0, sqlconsumidor.length() );
				sqlinventario.delete( 0, sqlinventario.length() );
				sql.delete( 0, sql.length() );
				if ( cbEntrada.getVlrString().equals( "S" ) ) {
					sqlentrada.append( "select ic.codprod,p.refprod,p.descprod,coalesce(cf.aliqipifisc,0)" ); 
					sqlentrada.append( ", coalesce(cf.aliqlfisc,0),p.codunid,cf.origfisc,cf.codtrattrib,p.codfisc " );
					sqlentrada.append( "from cpcompra c,cpitcompra ic,eqtipomov tm,eqproduto p,lfitclfiscal cf, cpforneced f " );
					sqlentrada.append( "where c.dtentcompra between ? and ? and c.codemp=? and c.codfilial=? " ); 
					sqlentrada.append( "and ic.codcompra=c.codcompra and ic.codemp=c.codemp and ic.codfilial=c.codfilial " );
					sqlentrada.append( "and tm.codtipomov=c.codtipomov and tm.codemp=c.codemptm and tm.codfilial=c.codfilialtm " );
					sqlentrada.append( "and p.codprod=ic.codprod and p.codemp=ic.codemppd and p.codfilial=ic.codfilialpd " ); 
					sqlentrada.append( "and cf.codfisc=p.codfisc and cf.codemp=p.codempfc and cf.codfilial=p.codfilialfc and cf.coditfisc=ic.coditfisc " );
					sqlentrada.append( "and f.codfor=c.codfor and f.codemp=c.codempfr and f.codfilial=c.codfilialfr " );
					sqlentrada.append( "and tm.fiscaltipomov='S' " );
					// filtro para não incluir produtos do patrimônio. 
					sqlentrada.append( "and p.tipoprod<>'O' " ); 
				}
				if ( cbSaida.getVlrString().equals( "S" ) ) {
					sqlsaida.append( "select iv.codprod,p.refprod,p.descprod,coalesce(cf.aliqipifisc, 0) " ); 
					sqlsaida.append( ", coalesce(cf.aliqlfisc, 0),p.codunid,cf.origfisc,cf.codtrattrib,p.codfisc " ); 
					sqlsaida.append( "from vdvenda v,vditvenda iv,eqtipomov tm,eqproduto p,vdcliente c,lfitclfiscal cf " );
					sqlsaida.append( "where v.dtemitvenda between ? and ? and v.codemp=? and v.codfilial=? " ); 
					sqlsaida.append( "and c.codcli=v.codcli and c.codemp=v.codempcl and c.codfilial=v.codfilialcl " ); 
					sqlsaida.append( "and iv.codvenda=v.codvenda and iv.tipovenda=v.tipovenda and iv.codemp=v.codemp " );
					sqlsaida.append( "and iv.codfilial=v.codfilial " );
					sqlsaida.append( "and tm.codtipomov=v.codtipomov and tm.codemp=v.codemptm " ); 
					sqlsaida.append( "and tm.codfilial=v.codfilialtm and tm.fiscaltipomov='S' " ); 
					sqlsaida.append( "and ( (c.pessoacli='J' and v.tipovenda='V') or (v.tipovenda='E') ) " );
					sqlsaida.append( "and p.codprod=iv.codprod and p.codemp=iv.codemppd and p.codfilial=iv.codfilialpd " ); 
					sqlsaida.append( "and cf.codfisc=p.codfisc and cf.codemp=p.codempfc and cf.codfilial=p.codfilialfc and cf.coditfisc=iv.coditfisc and tm.fiscaltipomov='S' " );
					// filtro para não incluir produtos do patrimônio. 
					sqlsaida.append( "and p.tipoprod<>'O' " );
					// Amarrado com o ítem padrão da classificação fiscal pois não deve repetir de acordo com as vendas as alíquotas
					// Informadas devem ser as correspondetes a vendas / ou compras dentro do estado.
				}
				if ( cbConsumidor.getVlrString().equals( "S" ) && ("1".equals( sConvenio ) || "3".equals( sConvenio ) ) ) { // IV.PERCICMSITVENDA
					sqlconsumidor.append( "select iv.codprod,p.refprod,p.descprod,coalesce(cf.aliqipifisc,0) " ); 
					sqlconsumidor.append( ", coalesce(cf.aliqlfisc,0),p.codunid,cf.origfisc,cf.codtrattrib,p.codfisc " ); 
					sqlconsumidor.append( "from vdvenda v,vditvenda iv,eqtipomov tm,eqproduto p,vdcliente c,lfitclfiscal cf " );
					sqlconsumidor.append( "where v.dtemitvenda between ? and ? and v.codemp=? and v.codfilial=? " ); 
					sqlconsumidor.append( "and c.codcli=v.codcli and c.codemp=v.codempcl and c.codfilial=v.codfilialcl "  );
					sqlconsumidor.append( "and c.pessoacli='F' and v.tipovenda='V' " );
					sqlconsumidor.append( "and iv.codvenda=v.codvenda and iv.tipovenda=v.tipovenda and iv.codemp=v.codemp " ); 
					sqlconsumidor.append( "and iv.codfilial=v.codfilial " );
					sqlconsumidor.append( "and tm.codtipomov=v.codtipomov and tm.codemp=v.codemptm " ); 
					sqlconsumidor.append( "and tm.codfilial=v.codfilialtm and tm.fiscaltipomov='S' " );
					sqlconsumidor.append( "and p.codprod=iv.codprod and p.codemp=iv.codemppd and p.codfilial=iv.codfilialpd " ); 
					sqlconsumidor.append( "and cf.codfisc=p.codfisc and cf.codemp=p.codempfc and cf.codfilial=p.codfilialfc and cf.coditfisc=iv.coditfisc and tm.fiscaltipomov='S' " );
					// filtro para não incluir produtos do patrimônio. 
					sqlconsumidor.append( "and p.tipoprod<>'O' " ); 
				}
				if ( cbInventario.getVlrString().equals( "S" ) ) {
					sqlinventario.append( "select p.codprod,p.refprod, p.descprod, coalesce(cf.aliqipifisc,0) ");
					sqlinventario.append(",coalesce(cf.aliqlfisc,0), pd.codunid,cf.origfisc,cf.codtrattrib,cf.codfisc " );
					sqlinventario.append( "from eqrelinvprodsp(?,?,?,null,null,null,?,null,null,null) p, lfitclfiscal cf, eqproduto pd " );
					sqlinventario.append( "where p.saldo > 0 " );
					sqlinventario.append( "and pd.codemp=? and pd.codfilial=? and pd.codprod=p.codprod and pd.ativoprod='S' " );
					sqlinventario.append( "and cf.codfisc=pd.codfisc and cf.codemp=pd.codempfc and cf.codfilial=pd.codfilialfc " );
					sqlinventario.append( "and cf.geralfisc='S' and cf.ativoitfisc='S' " );
					// filtro para não incluir produtos do patrimônio. 
					sqlinventario.append( "and pd.tipoprod<>'O' " );
				}	
				if ( sqlentrada.length()>0 )  {
					sql.append( sqlentrada.toString() );
				}
				if ( sqlsaida.length()>0 ) {
					sql.append( ( sql.length() > 0 ? " union " : "" ) );
					sql.append( sqlsaida.toString() );
				}
				if ( sqlconsumidor.length()>0 ) {
					sql.append( ( sql.length() > 0 ? " union " : "" ) );
					sql.append( sqlconsumidor.toString() );
				}
				if ( sqlinventario.length()>0 ) {
					sql.append( ( sql.length() > 0 ? " union " : "" ) );
					sql.append( sqlinventario.toString() );
				}
				sql.append( " group by 1,2,3,4,5,6,7,8,9 " );
				System.out.println( "reg75:" + sql.toString() );
				ps = con.prepareStatement( sql.toString() );
				iParam = 1;
				if ( sqlentrada.length()>0 ) {
					ps.setDate( iParam++, Funcoes.dateToSQLDate( txtDataini.getVlrDate() ) );
					ps.setDate( iParam++, Funcoes.dateToSQLDate( txtDatafim.getVlrDate() ) );
					ps.setInt( iParam++, iCodEmp );
					ps.setInt( iParam++, ListaCampos.getMasterFilial( "CPCOMPRA" ) );
				}
				if ( sqlsaida.length()>0 ) {
					ps.setDate( iParam++, Funcoes.dateToSQLDate( txtDataini.getVlrDate() ) );
					ps.setDate( iParam++, Funcoes.dateToSQLDate( txtDatafim.getVlrDate() ) );
					ps.setInt( iParam++, iCodEmp );
					ps.setInt( iParam++, ListaCampos.getMasterFilial( "VDVENDA" ) );
				}
				if ( sqlconsumidor.length()>0) {
					ps.setDate( iParam++, Funcoes.dateToSQLDate( txtDataini.getVlrDate() ) );
					ps.setDate( iParam++, Funcoes.dateToSQLDate( txtDatafim.getVlrDate() ) );
					ps.setInt( iParam++, iCodEmp );
					ps.setInt( iParam++, ListaCampos.getMasterFilial( "VDVENDA" ) );
				}
				if ( sqlinventario.length()>0 ) { 
					ps.setInt( iParam++, iCodEmp );
					ps.setInt( iParam++, ListaCampos.getMasterFilial( "EQPRODUTO" ) );
					ps.setString( iParam++, "P" );
					ps.setDate( iParam++, Funcoes.dateToSQLDate( txtDatafim.getVlrDate() ) );
					ps.setInt( iParam++, iCodEmp );
					ps.setInt( iParam++, ListaCampos.getMasterFilial( "EQPRODUTO" ) );
				}
				rs = ps.executeQuery();
				lbAnd.setText( "Gerando Tabela de Produtos de entradas e saídas..." );

				vProd75.clear();
				buffer75 = new StringBuffer();

				while ( rs.next() ) {

					buffer.delete( 0, buffer.length() );

					btGerar.setEnabled( false );

					buffer.append( "75" );
					buffer.append( Funcoes.dataAAAAMMDD( txtDataini.getVlrDate() ) );
					buffer.append( Funcoes.dataAAAAMMDD( txtDatafim.getVlrDate() ) );
					buffer.append( Funcoes.adicionaEspacos( rs.getString( ( sUsaRefProd.equals( "S" ) ? COL_75_REFPROD : COL_75_CODPROD ) ), 14 ) );
					buffer.append( Funcoes.copy( rs.getString( COL_75_CODNCM ), 8 ) );
					buffer.append( Funcoes.adicionaEspacos( rs.getString( COL_75_DESCPROD ), 53 ) );
					buffer.append( Funcoes.adicionaEspacos( rs.getString( COL_75_CODUNID ), 4 ) );// 97
					buffer.append( Funcoes.copy( rs.getString( COL_75_ORIGFISC ).trim() + rs.getString( COL_75_CODTRATTRIB ).trim(), 3 ) );
					buffer.append( Funcoes.transValor( rs.getString( COL_75_PERCIPI ), 4, 2, true ) );
					buffer.append( Funcoes.transValor( rs.getString( COL_75_PERCICMS ), 4, 2, true ) );
					buffer.append( StringFunctions.strZero( "0", 4 ) );
					buffer.append( Funcoes.transValor( "0", 14, 2, true ) + CR );

					// Caso não seja necessário a geração do registro 74 grava o buffer agora.
					// Do contrario as informações do registro 75 serão gravadas após o registro 74.

					if ( "N".equals( cbInventario.getVlrString() ) ) {
						gravaBuffer( buffer.toString() );
					}
					else {
						buffer75.append( buffer );
					}

					if ( !vProd75.contains( rs.getString( "CODPROD" ) ) ) {
						vProd75.addElement( rs.getString( "CODPROD" ) );
					}

					cont++;

				}

				rs.close();
				ps.close();
				con.commit();

			}

		} catch ( Exception e ) {
			e.printStackTrace();
			Funcoes.mensagemErro( this, "Erro ao gerar registro 75!\n" + e.getMessage(), true, con, e );
		}

		return cont;

	}

	private String retorna90( String bufferAnt, String sTipo, int iTot ) {

		StringBuffer sBuf = new StringBuffer();
		if ( "".equals( bufferAnt.trim() ) ) {
			sBuf.append( "90" );
			sBuf.append( sCnpjEmp );
			sBuf.append( sInscEmp );
		}
		sBuf.append( sTipo );
		sBuf.append( StringFunctions.strZero( String.valueOf( iTot ), 8 ) );

		return sBuf.toString();

	}

	private void gravaBuffer( String sBuf ) {

		try {

			oswSintegra.write( sBuf );
			oswSintegra.flush();
		} catch ( IOException err ) {
			Funcoes.mensagemErro( this, "Erro grando no arquivo: '" + sFileName + "\n" + err.getMessage(), true, con, err );
		}

	}

	private boolean valida() {

		if ( txtDatafim.getVlrDate().before( txtDataini.getVlrDate() ) ) {
			Funcoes.mensagemInforma( this, "Data final maior que a data inicial!" );
			return false;
		}
		else if ( ( cbEntrada.getVlrString() != "S" ) & ( cbSaida.getVlrString() != "S" ) & ( cbInventario.getVlrString() != "S" ) & ( cbFrete.getVlrString() != "S" ) ) {
			Funcoes.mensagemInforma( this, "Nenhuma operação foi selecionada!" );
			return false;
		}

		FileDialog fdSintegra = null;
		fdSintegra = new FileDialog( Aplicativo.telaPrincipal, "Salvar sintegra", FileDialog.SAVE );
		fdSintegra.setFile( "Receita.txt" );
		fdSintegra.setVisible( true );
		if ( fdSintegra.getFile() == null )
			return false;

		sFileName = fdSintegra.getDirectory() + fdSintegra.getFile();

		return true;

	}

	public void actionPerformed( ActionEvent evt ) {

		if ( evt.getSource() == btGerar ) {
			// iniGerar();
			gerar();
		}
	}

}
