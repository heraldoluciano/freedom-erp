/**
 * @version 01/03/2007 <BR>
 * @author Setpoint Informática Ltda./RobsonSanchez/Alex Rodrigues<BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.std <BR>
 * Classe:
 * @(#)FRemessa.java <BR>
 * 
 * Este programa é licenciado de acordo com a LPG-PC (Licença Pública Geral para Programas de Computador), <BR>
 * versão 2.1.0 ou qualquer versão posterior. <BR>
 * A LPG-PC deve acompanhar todas PUBLICAÇÕES, DISTRIBUIÇÕES e REPRODUÇÕES deste Programa. <BR>
 * Caso uma cópia da LPG-PC não esteja disponível junto com este Programa, você pode contatar <BR>
 * o LICENCIADOR ou então pegar uma cópia em: <BR>
 * Licença: http://www.lpg.adv.br/licencas/lpgpc.rtf <BR>
 * Para poder USAR, PUBLICAR, DISTRIBUIR, REPRODUZIR ou ALTERAR este Programa é preciso estar <BR>
 * de acordo com os termos da LPG-PC <BR>
 * <BR>
 * 
 * Tela de remessa de arquivo, contendo os dados dos clientes e recebimentos, para o banco selecionado.
 * 
 */

package org.freedom.modulos.fnc;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import org.freedom.bmps.Icone;
import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JPanelPad;
import org.freedom.componentes.JRadioGroup;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.Tabela;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FFilho;

public class FRemSiacc extends FFilho implements ActionListener, MouseListener {

	private static final long serialVersionUID = 1L;

	private static final int COL_SEL = 0;

	private static final int COL_RAZCLI = 1;

	private static final int COL_CODCLI = 2;

	private static final int COL_CODREC = 3;

	private static final int COL_DOCREC = 4;

	private static final int COL_NRPARC = 5;

	private static final int COL_VLRAPAG = 6;

	private static final int COL_DTREC = 7;

	private static final int COL_DTVENC = 8;

	private static final int COL_AGENCIACLI = 9;

	private static final int COL_IDENTCLI = 10;

	private static final int COL_SITREM = 11;

	private static final int COL_SITRET = 12;

	private static final int COL_STIPOFEBRABAN = 13;

	private static final int COL_TIPOREMCLI = 14;

	private static final String TIPO_FEBRABAN = "01";

	private JPanelPad panelRodape = null;

	private final JPanelPad panelRemessa = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private final JPanelPad panelFiltros = new JPanelPad();

	private final JPanelPad panelTabela = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private final JPanelPad panelFuncoes = new JPanelPad();

	private final JPanelPad panelStatus = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private final Tabela tab = new Tabela();

	private final JTextFieldPad txtCodBanco = new JTextFieldPad( JTextFieldPad.TP_STRING, 3, 0 );

	private final JTextFieldFK txtNomeBanco = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private final JTextFieldPad txtDtIni = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private final JTextFieldPad txtDtFim = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );

	private final JRadioGroup rgData;
	
	private final JRadioGroup rgSitRemessa;

	private final JButton btCarrega = new JButton( "Buscar", Icone.novo( "btExecuta.gif" ) );

	private final JButton btExporta = new JButton( "Exportar", Icone.novo( "btSalvar.gif" ) );

	private final JButton btSelTudo = new JButton( Icone.novo( "btTudo.gif" ) );

	private final JButton btSelNada = new JButton( Icone.novo( "btNada.gif" ) );

	private final JLabel lbStatus = new JLabel();

	private final ListaCampos lcBanco = new ListaCampos( this );
	
	private String tipoRem = "00";

	private Map<SiaccUtil.EPrefs, Object> prefs = new HashMap<SiaccUtil.EPrefs, Object>();
	
	private final Vector<String> vVals = new Vector<String>();
	
	private final Vector<String> vLabs = new Vector<String>();

	private final Vector<String> vValsRem = new Vector<String>();
	
	private final Vector<String> vLabsRem = new Vector<String>();
	
	public FRemSiacc() {

		super( false );
		setTitulo( "Manutenção de contas a receber" );
		setAtribos( 10, 10, 780, 540 );

		vVals.addElement( "E" );
		vVals.addElement( "V" );
		vLabs.addElement( "Emissão" );
		vLabs.addElement( "Vencimento" );
		rgData = new JRadioGroup( 2, 1, vLabs, vVals );
		
		vValsRem.addElement( "'00'" );
		vValsRem.addElement( "'01'" );
		vValsRem.addElement( "'00','01'" );
		vLabsRem.addElement( "Não exportados" );
		vLabsRem.addElement( "Exportados" );
		vLabsRem.addElement( "Ambos" );
		
		rgSitRemessa = new JRadioGroup(3, 1, vLabsRem, vValsRem);

		lcBanco.add( new GuardaCampo( txtCodBanco, "CodBanco", "Cód.banco", ListaCampos.DB_PK, true ) );
		lcBanco.add( new GuardaCampo( txtNomeBanco, "NomeBanco", "Nome do Banco", ListaCampos.DB_SI, false ) );
		lcBanco.montaSql( false, "BANCO", "FN" );
		lcBanco.setQueryCommit( false );
		lcBanco.setReadOnly( true );
		txtCodBanco.setNomeCampo( "CodBanco" );
		txtCodBanco.setTabelaExterna( lcBanco );
		txtCodBanco.setListaCampos( lcBanco );
		txtCodBanco.setFK( true );
		txtCodBanco.setRequerido( true );
		txtNomeBanco.setListaCampos( lcBanco );

		montaTela();

		tab.adicColuna( "Sel." );
		tab.adicColuna( "Razão social do cliente" );
		tab.adicColuna( "Cód.cli." );
		tab.adicColuna( "Cód.rec." );
		tab.adicColuna( "Doc" );
		tab.adicColuna( "Nro.Parc." );
		tab.adicColuna( "Valor" );
		tab.adicColuna( "Emissão" );
		tab.adicColuna( "Vencimento" );
		tab.adicColuna( "Agência" );
		tab.adicColuna( "Indentificação" );
		tab.adicColuna( "Sit. rem." );
		tab.adicColuna( "Sit. ret." );
		tab.adicColuna( "Subtipo" );
		tab.adicColuna( "Tp.r.cli." );

		tab.setTamColuna( 20, COL_SEL );
		tab.setTamColuna( 150, COL_RAZCLI );
		tab.setTamColuna( 70, COL_CODCLI );
		tab.setTamColuna( 70, COL_CODREC );
		tab.setTamColuna( 80, COL_DOCREC );
		tab.setTamColuna( 70, COL_NRPARC );
		tab.setTamColuna( 70, COL_VLRAPAG );
		tab.setTamColuna( 70, COL_DTREC );
		tab.setTamColuna( 70, COL_DTVENC );
		tab.setTamColuna( 100, COL_AGENCIACLI );
		tab.setTamColuna( 100, COL_IDENTCLI );
		tab.setTamColuna( 50, COL_SITREM );
		tab.setTamColuna( 50, COL_SITRET );
		tab.setTamColuna( 30, COL_STIPOFEBRABAN );
		tab.setTamColuna( 30, COL_TIPOREMCLI );

		tab.setColunaEditavel( COL_SEL, true );

		tab.addMouseListener( this );

		btCarrega.addActionListener( this );
		btSelTudo.addActionListener( this );
		btSelNada.addActionListener( this );
		btExporta.addActionListener( this );

		btSelTudo.setToolTipText( "Selecionar tudo" );
		btSelNada.setToolTipText( "Limpar seleção" );

		txtDtIni.setVlrDate( Calendar.getInstance().getTime() );
		txtDtFim.setVlrDate( Calendar.getInstance().getTime() );

	}

	

	private void montaTela() {
	
		pnCliente.add( panelRemessa, BorderLayout.CENTER );
	
		panelRemessa.add( panelFiltros, BorderLayout.NORTH );
		panelRemessa.add( panelTabela, BorderLayout.CENTER );
		panelRemessa.add( panelStatus, BorderLayout.SOUTH );
	
		panelFiltros.setPreferredSize( new Dimension( 300, 150 ) );
		panelFiltros.adic( new JLabel( "Cód.banco" ), 7, 0, 90, 20 );
		panelFiltros.adic( txtCodBanco, 7, 20, 90, 20 );
		panelFiltros.adic( new JLabel( "Nome do banco" ), 100, 0, 300, 20 );
		panelFiltros.adic( txtNomeBanco, 100, 20, 300, 20 );
	
		JLabel bordaData = new JLabel();
		bordaData.setBorder( BorderFactory.createEtchedBorder() );
	
		panelFiltros.adic( new JLabel( "filtro:" ), 7, 40, 60, 20 );
		panelFiltros.adic( rgSitRemessa, 7, 60, 140, 70 );
		panelFiltros.adic( new JLabel( "filtro:" ), 150, 40, 60, 20 );
		panelFiltros.adic( rgData, 150, 60, 120, 70 );
		panelFiltros.adic( new JLabel( "Período:" ), 273, 40, 80, 20 );
		panelFiltros.adic( txtDtIni, 280, 85, 100, 20 );
		panelFiltros.adic( new JLabel( "até", SwingConstants.CENTER ), 378, 85, 40, 20 );
		panelFiltros.adic( txtDtFim, 415, 85, 100, 20 );
		panelFiltros.adic( bordaData, 273, 60, 250, 70 );
	
		panelFiltros.adic( btCarrega, 540, 80, 150, 30 );
	
		panelTabela.add( new JScrollPane( tab ), BorderLayout.CENTER );
		panelTabela.add( panelFuncoes, BorderLayout.EAST );
	
		panelFuncoes.setPreferredSize( new Dimension( 45, 100 ) );
		panelFuncoes.adic( btSelTudo, 5, 5, 30, 30 );
		panelFuncoes.adic( btSelNada, 5, 40, 30, 30 );
	
		lbStatus.setForeground( Color.BLUE );
	
		panelStatus.setPreferredSize( new Dimension( 600, 30 ) );
		panelStatus.add( lbStatus, BorderLayout.WEST );
	
		panelRodape = adicBotaoSair();
		panelRodape.setBorder( BorderFactory.createEtchedBorder() );
		panelRodape.setPreferredSize( new Dimension( 600, 32 ) );
		btExporta.setPreferredSize( new Dimension( 150, 30 ) );
		panelRodape.add( btExporta, BorderLayout.WEST );
	
	}

	private boolean setPrefs() {
	
		boolean retorno = false;
		try {
			PreparedStatement ps = con.prepareStatement( "SELECT I.CODCONV, P.NOMEEMP, I.VERLAYOUT, " +
					"I.IDENTSERV, I.CONTACOMPR, " + "I.IDENTAMBCLI, I.IDENTAMBBCO, " +
					"I.NROSEQ FROM SGITPREFERE6 I, SGPREFERE6 P WHERE I.CODEMP=? AND " +
					"I.CODFILIAL=? AND I.CODEMPBO=? AND " +
					"I.CODFILIALBO=? AND I.CODBANCO=? AND I.TIPOFEBRABAN=? AND " + 
					"P.CODEMP=I.CODEMP AND P.CODFILIAL=I.CODFILIAL" );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "SGITPREFERE6" ) );
			ps.setInt( 3, Aplicativo.iCodEmp );
			ps.setInt( 4, ListaCampos.getMasterFilial( "FNBANCO" ) );
			ps.setString( 5, txtCodBanco.getVlrString() );
			ps.setString( 6, TIPO_FEBRABAN );
			ResultSet rs = ps.executeQuery();
			if ( rs.next() ) {
				prefs.put( SiaccUtil.EPrefs.CODCONV, rs.getString( SiaccUtil.EPrefs.CODCONV.toString() ) );
				prefs.put( SiaccUtil.EPrefs.NOMEEMP, rs.getString( SiaccUtil.EPrefs.NOMEEMP.toString() ) );
				prefs.put( SiaccUtil.EPrefs.VERLAYOUT, rs.getString( SiaccUtil.EPrefs.VERLAYOUT.toString() ) );
				prefs.put( SiaccUtil.EPrefs.CODBANCO, txtCodBanco.getVlrString() );
				prefs.put( SiaccUtil.EPrefs.NOMEBANCO, txtNomeBanco.getVlrString() );
				prefs.put( SiaccUtil.EPrefs.IDENTSERV, rs.getString( SiaccUtil.EPrefs.IDENTSERV.toString() ) );
				prefs.put( SiaccUtil.EPrefs.CONTACOMPR, rs.getString( SiaccUtil.EPrefs.CONTACOMPR.toString() ) );
				prefs.put( SiaccUtil.EPrefs.IDENTAMBCLI, rs.getString( SiaccUtil.EPrefs.IDENTAMBCLI.toString() ) );
				prefs.put( SiaccUtil.EPrefs.IDENTAMBBCO, rs.getString( SiaccUtil.EPrefs.IDENTAMBBCO.toString() ) );
				prefs.put( SiaccUtil.EPrefs.NROSEQ, new Integer(rs.getInt( SiaccUtil.EPrefs.NROSEQ.toString() )) );
				retorno = true;
			}
			else {
				retorno = false;
				Funcoes.mensagemInforma( null, "Ajuste os parâmetros antes de executar!" );
			}
			rs.close();
			ps.close();
			if ( !con.getAutoCommit() ) {
				con.commit();
			}
		} catch ( SQLException sqlError ) {
			retorno = false;
			Funcoes.mensagemErro( this, "Carregando parâmetros!\n" + sqlError.getMessage() );
			lbStatus.setText( "" );
		}
		return retorno;
	}

	private ResultSet executeQuery() throws SQLException {
	
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sDtFiltro = "E".equals( rgData.getVlrString() ) ? "IR.DTITREC" : "IR.DTVENCITREC";
		StringBuilder sSQL = new StringBuilder();
		tipoRem = rgSitRemessa.getVlrString();
		sSQL.append( "SELECT IR.CODREC, IR.NPARCITREC, R.DOCREC, R.CODCLI, C.RAZCLI, IR.DTITREC, IR.DTVENCITREC," );
		sSQL.append( "IR.VLRAPAGITREC, FC.AGENCIACLI, FC.IDENTCLI, COALESCE(FR.SITREMESSA,'00') SITREMESSA, " );
		sSQL.append( "FR.SITRETORNO, COALESCE(COALESCE(FR.STIPOFEBRABAN,FC.STIPOFEBRABAN),'02') STIPOFEBRABAN, " );
		sSQL.append( "COALESCE(FC.TIPOREMCLI,'B') TIPOREMCLI " );
		sSQL.append( "FROM VDCLIENTE C," );
		sSQL.append( "FNRECEBER R LEFT OUTER JOIN FNFBNCLI FC ON " );
		sSQL.append( "FC.CODEMP=R.CODEMPCL AND FC.CODFILIAL=R.CODFILIALCL AND FC.CODCLI=R.CODCLI ," );
		sSQL.append( "FNITRECEBER IR LEFT OUTER JOIN FNFBNREC FR ON " );
		sSQL.append( "FR.CODEMP=IR.CODEMP AND FR.CODFILIAL=IR.CODFILIAL AND " );
		sSQL.append( "FR.CODREC=IR.CODREC AND FR.NPARCITREC=IR.NPARCITREC AND " );
		sSQL.append( "FR.CODEMPBO=IR.CODEMPBO AND FR.CODFILIALBO=IR.CODFILIALBO AND FR.CODBANCO=IR.CODBANCO " );
		sSQL.append( "WHERE R.CODEMP=IR.CODEMP AND R.CODFILIAL=IR.CODFILIAL AND R.CODREC=IR.CODREC AND " );
		sSQL.append( "C.CODEMP=R.CODEMPCL AND C.CODFILIAL=R.CODFILIALCL AND C.CODCLI=R.CODCLI AND " );
		sSQL.append( sDtFiltro );
		sSQL.append( " BETWEEN ? AND ? AND IR.STATUSITREC IN ('R1','RL') AND " );
		sSQL.append( "IR.CODEMPBO=? AND IR.CODFILIALBO=? AND IR.CODBANCO=? AND " );
		sSQL.append( "( FR.SITREMESSA IS NULL OR FR.SITREMESSA IN ("+tipoRem+")) " );
		sSQL.append( "ORDER BY C.RAZCLI, R.CODREC, IR.NPARCITREC " );
		ps = con.prepareStatement( sSQL.toString() );
		ps.setDate( 1, Funcoes.dateToSQLDate( txtDtIni.getVlrDate() ) );
		ps.setDate( 2, Funcoes.dateToSQLDate( txtDtFim.getVlrDate() ) );
		ps.setInt( 3, Aplicativo.iCodEmp );
		ps.setInt( 4, ListaCampos.getMasterFilial( "FNITRECEBER" ) );
		ps.setInt( 5, txtCodBanco.getVlrInteger() );
		rs = ps.executeQuery();
		return rs;
	}

	private void carregaTab() {
	
		ResultSet rs;
	
		if ( txtCodBanco.getVlrString().trim().length() < 1 ) {
			Funcoes.mensagemErro( this, "O código do banco é obrigatorio!" );
			return;
		}
	
		try {
	
			tab.limpa();
	
			rs = executeQuery();
	
			int i = 0;
			for ( i = 0; rs.next(); i++ ) {
	
				tab.adicLinha();
				tab.setValor( new Boolean( true ), i, COL_SEL );
				tab.setValor( rs.getString( "RAZCLI" ), i, COL_RAZCLI );
				tab.setValor( new Integer( rs.getInt( "CODCLI" ) ), i, COL_CODCLI );
				tab.setValor( new Integer( rs.getInt( "CODREC" ) ), i, COL_CODREC );
				tab.setValor( rs.getString( "DOCREC" ), i, COL_DOCREC );
				tab.setValor( new Integer( rs.getInt( "NPARCITREC" ) ), i, COL_NRPARC );
				tab.setValor( rs.getBigDecimal( "VLRAPAGITREC" ), i, COL_VLRAPAG );
				tab.setValor( rs.getDate( "DTITREC" ), i, COL_DTREC );
				tab.setValor( rs.getDate( "DTVENCITREC" ), i, COL_DTVENC );
				tab.setValor( rs.getString( "AGENCIACLI" ), i, COL_AGENCIACLI );
				tab.setValor( rs.getString( "IDENTCLI" ), i, COL_IDENTCLI );
				tab.setValor( rs.getString( "SITREMESSA" ), i, COL_SITREM );
				tab.setValor( rs.getString( "SITRETORNO" ), i, COL_SITRET );
				tab.setValor( rs.getString( "STIPOFEBRABAN" ), i, COL_STIPOFEBRABAN );
				tab.setValor( rs.getString( "TIPOREMCLI" ), i, COL_TIPOREMCLI );
			}
	
			rs.close();
	
			if ( !con.getAutoCommit() ) {
				con.commit();
			}
	
			if ( i > 0 ) {
				lbStatus.setText( "     tabela carregada com " + i + " itens..." );
			}
			else {
				lbStatus.setText( "" );
			}
	
		} catch ( Exception e ) {
			Funcoes.mensagemErro( this, "Erro ao busca dados!\n" + e.getMessage() );
			e.printStackTrace();
			lbStatus.setText( "" );
		} finally {
			System.gc();
		}
	
	}

	private void selecionaTudo() {
	
		for ( int i = 0; i < tab.getNumLinhas(); i++ ) {
			tab.setValor( new Boolean( true ), i, 0 );
		}
	}

	private void selecionaNada() {
	
		for ( int i = 0; i < tab.getNumLinhas(); i++ ) {
			tab.setValor( new Boolean( false ), i, 0 );
		}
	}

	private boolean execExporta() {
	
		boolean retorno = false;
		String sFileName = null;
		File fileSiacc = null;
		FileWriter fw = null;
		BufferedWriter bw = null;
		HashSet<SiaccUtil.StuffCli> hsCli = new HashSet<SiaccUtil.StuffCli>();
		HashSet<SiaccUtil.StuffRec> hsRec = new HashSet<SiaccUtil.StuffRec>();
	
		if ( consisteExporta( hsCli, hsRec ) ) {
			
			retorno = setPrefs();
			
			if (retorno) {
				lbStatus.setText( "     criando arquivo ..." );
		
				FileDialog fileDialogSiacc = null;
				fileDialogSiacc = new FileDialog( Aplicativo.telaPrincipal, "Exportar arquivo.", FileDialog.SAVE );
				sFileName = "remessa"+prefs.get( SiaccUtil.EPrefs.NROSEQ )+".txt";
				fileDialogSiacc.setFile( sFileName );
				fileDialogSiacc.setVisible( true );
		
				if ( fileDialogSiacc.getFile() == null ) {
					lbStatus.setText( "" );
					return retorno;
				}
		
				sFileName = fileDialogSiacc.getDirectory() + fileDialogSiacc.getFile();
		
				fileSiacc = new File( sFileName );
		
				try {
					fileSiacc.createNewFile();
					fw = new FileWriter( fileSiacc );
					bw = new BufferedWriter( fw );
		
					lbStatus.setText( "     gravando arquivo ..." );
					retorno = gravaRemessa( bw, hsCli, hsRec, rgSitRemessa.getVlrString() );
				} catch ( IOException ioError ) {
					Funcoes.mensagemErro( this, "Erro Criando o arquivo!\n " + sFileName + "\n" + ioError.getMessage() );
					lbStatus.setText( "" );
					return retorno;
				}
				
				lbStatus.setText( "     pronto ..." );
				atualizaSitremessaExp(hsCli, hsRec);
			}
			
		}
		return retorno;
	}

	private void atualizaSitremessaExp(HashSet<SiaccUtil.StuffCli> hsCli, HashSet<SiaccUtil.StuffRec> hsRec) {
		setSitremessa(hsRec, "01");
		persisteDados( hsCli, hsRec );
		updatePrefere();
	}
	
	private void setSitremessa(HashSet<SiaccUtil.StuffRec> hsRec, final String sit) {
		for (SiaccUtil.StuffRec sr: hsRec) {
			sr.setSitremessa( sit );
		}
	}
	
	private boolean gravaRemessa( final BufferedWriter bw, HashSet<SiaccUtil.StuffCli> hsCli, 
			HashSet<SiaccUtil.StuffRec> hsRec, String sitRemessa ) {

		boolean retorno = false;
		Integer numReg = new Integer(0);
		Integer nroSeq = (Integer) prefs.get(SiaccUtil.EPrefs.NROSEQ); 
	
		try {
			
			ArrayList<SiaccUtil.Reg> list = new ArrayList<SiaccUtil.Reg>();
			list.add( new SiaccUtil().new RegA( '1', prefs, numReg++ ) );
			int numAgenda = 1;
			float vlrtotal = 0;
			SiaccUtil.RegE e = null;
			
			// Implementar no futuro (Registro de clientes não podem ser enviados com Registro E)
			
			/*
			for ( SiaccUtil.StuffCli c : hsCli ) {
				if ( "B".equals( c.getArgs()[ SiaccUtil.EColcli.TIPOREMCLI.ordinal() ] ) ) {
					list.add(  new  SiaccUtil().new RegB( 'B', c ) ) ;
					numReg++;
				}
			}
			for ( SiaccUtil.StuffCli c : hsCli ) {
				if ( "C".equals( c.getArgs()[ SiaccUtil.EColcli.TIPOREMCLI.ordinal() ] ) ) {
					list.add( new SiaccUtil().new RegC( 'C', c, numReg++ ) );
				}
			}
			for ( SiaccUtil.StuffCli c : hsCli ) {
				if ( "D".equals( c.getArgs()[ SiaccUtil.EColcli.TIPOREMCLI.ordinal() ] ) ) {
					list.add( new SiaccUtil().new RegD( 'D', c, numReg++ ) );
				}
			} */
			for ( SiaccUtil.StuffRec r : hsRec ) {
				if ( sitRemessa.indexOf(( r.getArgs()[ SiaccUtil.EColrec.SITREMESSA.ordinal() ] ))>-1 ) {
					e = new SiaccUtil().new RegE( 'E', r, numReg++, numAgenda );
					list.add( e );
					vlrtotal += e.getVlrParc();
					numAgenda++;
				}
			}
			
			list.add(new SiaccUtil().new RegZ(numReg, vlrtotal, numReg++));
			
			for ( SiaccUtil.Reg reg : list ) {
				bw.write( reg.toString() );
			}
			bw.flush();
			bw.close();
			prefs.put( SiaccUtil.EPrefs.NROSEQ, ++nroSeq ) ;
			
		} catch ( IOException ioError ) {
			Funcoes.mensagemErro( this, "Erro gravando no arquivo!\n" + ioError.getMessage() );
			lbStatus.setText( "" );
			retorno = false;
		}

		return retorno;
	}

	private boolean updateCliente( int codCli, String codBanco, String tipoFebraban, String stipoFebraban, String agenciaCli, String identCli, String tipoRemCli ) {

		boolean retorno = false;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement( "SELECT AGENCIACLI, IDENTCLI, STIPOFEBRABAN, TIPOREMCLI FROM FNFBNCLI " + "WHERE CODEMP=? AND CODFILIAL=? AND CODCLI=? AND CODEMPPF=? AND " + "CODFILIALPF=? AND CODEMPBO=? AND CODFILIALBO=? AND " + "CODBANCO=? AND TIPOFEBRABAN=?" );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "VDCLIENTE" ) );
			ps.setInt( 3, codCli );
			ps.setInt( 4, Aplicativo.iCodEmp );
			ps.setInt( 5, ListaCampos.getMasterFilial( "SGITPREFERE6" ) );
			ps.setInt( 6, Aplicativo.iCodEmp );
			ps.setInt( 7, ListaCampos.getMasterFilial( "FNBANCO" ) );
			ps.setString( 8, codBanco );
			ps.setString( 9, tipoFebraban );
			rs = ps.executeQuery();
			if ( rs.next() ) {
				if ( ( !agenciaCli.equals( rs.getString( "AGENCIACLI" ) ) ) || ( !identCli.equals( rs.getString( "IDENTCLI" ) ) ) || ( !stipoFebraban.equals( rs.getString( "STIPOFEBRABAN" ) ) ) || ( !tipoRemCli.equals( rs.getString( "TIPOREMCLI" ) ) ) ) {
					ps = con.prepareStatement( "UPDATE FNFBNCLI SET AGENCIACLI=?, IDENTCLI=?, STIPOFEBRABAN=?, TIPOREMCLI=? " + "WHERE CODEMP=? AND CODFILIAL=? AND " + "CODCLI=? AND CODEMPPF=? AND CODFILIALPF=? AND CODEMPBO=? AND CODFILIALBO=? AND " + "CODBANCO=? AND TIPOFEBRABAN=?" );
					ps.setString( 1, agenciaCli );
					ps.setString( 2, identCli );
					ps.setString( 3, stipoFebraban );
					ps.setString( 4, tipoRemCli );
					ps.setInt( 5, Aplicativo.iCodEmp );
					ps.setInt( 6, ListaCampos.getMasterFilial( "VDCLIENTE" ) );
					ps.setInt( 7, codCli );
					ps.setInt( 8, Aplicativo.iCodEmp );
					ps.setInt( 9, ListaCampos.getMasterFilial( "SGITPREFERE6" ) );
					ps.setInt( 10, Aplicativo.iCodEmp );
					ps.setInt( 11, ListaCampos.getMasterFilial( "FNBANCO" ) );
					ps.setString( 12, codBanco );
					ps.setString( 13, tipoFebraban );
					ps.executeUpdate();
				}
			}
			else {
				ps = con.prepareStatement( "INSERT INTO FNFBNCLI (AGENCIACLI, IDENTCLI, CODEMP, CODFILIAL, " + "CODCLI, CODEMPPF, CODFILIALPF, CODEMPBO, CODFILIALBO, CODBANCO, " + "TIPOFEBRABAN, STIPOFEBRABAN, TIPOREMCLI) " + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)" );
				ps.setString( 1, agenciaCli );
				ps.setString( 2, identCli );
				ps.setInt( 3, Aplicativo.iCodEmp );
				ps.setInt( 4, ListaCampos.getMasterFilial( "VDCLIENTE" ) );
				ps.setInt( 5, codCli );
				ps.setInt( 6, Aplicativo.iCodEmp );
				ps.setInt( 7, ListaCampos.getMasterFilial( "SGITPREFERE6" ) );
				ps.setInt( 8, Aplicativo.iCodEmp );
				ps.setInt( 9, ListaCampos.getMasterFilial( "FNBANCO" ) );
				ps.setString( 10, codBanco );
				ps.setString( 11, tipoFebraban );
				ps.setString( 12, stipoFebraban );
				ps.setString( 13, tipoRemCli );

				ps.executeUpdate();
			}
			if ( !con.getAutoCommit() )
				con.commit();
			// rs.close();
			retorno = true;
		} catch ( SQLException e ) {
			Funcoes.mensagemErro( this, "Erro atualizando cliente!\n" + e.getMessage() );
		}

		return retorno;
	}

	private boolean updateReceber( int codRec, int nParcitrec, String codBanco, String tipoFebraban, String stipoFebraban, String sitRemessa ) {

		boolean retorno = false;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement( "SELECT CODBANCO, TIPOFEBRABAN, STIPOFEBRABAN, SITREMESSA " + 
					"FROM FNFBNREC WHERE CODEMP=? AND CODFILIAL=? AND CODREC=? AND NPARCITREC=?" );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "FNITRECEBER" ) );
			ps.setInt( 3, codRec );
			ps.setInt( 4, nParcitrec );
			rs = ps.executeQuery();
			if ( rs.next() ) {
				if ( ( !codBanco.equals( rs.getString( "CODBANCO" ) ) ) || 
						( !tipoFebraban.equals( rs.getString( "TIPOFEBRABAN" ) ) ) || 
						( !stipoFebraban.equals( rs.getString( "STIPOFEBRABAN" ) ) ) || 
						( !sitRemessa.equals( rs.getString( "SITREMESSA" ) ) ) ) {
					ps = con.prepareStatement( "UPDATE FNFBNREC SET CODBANCO=?, TIPOFEBRABAN=?, STIPOFEBRABAN=?, " +
							"SITREMESSA=? " +
							"WHERE CODEMP=? AND CODFILIAL=? AND CODREC=? AND NPARCITREC=?" );
					ps.setString( 1, codBanco );
					ps.setString( 2, tipoFebraban );
					ps.setString( 3, stipoFebraban );
					ps.setString( 4, sitRemessa );
					ps.setInt( 5, Aplicativo.iCodEmp );
					ps.setInt( 6, ListaCampos.getMasterFilial( "FNITRECEBER" ) );
					ps.setInt( 7, codRec );
					ps.setInt( 8, nParcitrec );
					ps.executeUpdate();
				}
			}
			else {
				ps = con.prepareStatement( "INSERT INTO FNFBNREC (CODEMP, CODFILIAL, CODREC, NPARCITREC, " + "CODEMPPF, CODFILIALPF, CODEMPBO, CODFILIALBO, CODBANCO, TIPOFEBRABAN, STIPOFEBRABAN, " + "SITREMESSA) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)" );
				ps.setInt( 1, Aplicativo.iCodEmp );
				ps.setInt( 2, ListaCampos.getMasterFilial( "FNFBNREC" ) );
				ps.setInt( 3, codRec );
				ps.setInt( 4, nParcitrec );
				ps.setInt( 5, Aplicativo.iCodEmp );
				ps.setInt( 6, ListaCampos.getMasterFilial( "SGITPREFERE6" ) );
				ps.setInt( 7, Aplicativo.iCodEmp );
				ps.setInt( 8, ListaCampos.getMasterFilial( "FNBANCO" ) );
				ps.setString( 9, codBanco );
				ps.setString( 10, tipoFebraban );
				ps.setString( 11, stipoFebraban );
				ps.setString( 12, sitRemessa );
				ps.executeUpdate();
			}
			if ( !con.getAutoCommit() )
				con.commit();
			// rs.close();
			retorno = true;

		} catch ( SQLException e ) {
			Funcoes.mensagemErro( this, "Erro atualizando situação do contas a receber!\n" + e.getMessage() );
		}

		return retorno;
	}

	private boolean consisteExporta( HashSet<SiaccUtil.StuffCli> hsCli, HashSet<SiaccUtil.StuffRec> hsRec ) {

		boolean retorno = true;
		Vector vLinha = null;

		for ( int i = 0; i < tab.getNumLinhas(); i++ ) {

			vLinha = tab.getLinha( i );

			if ( (Boolean) vLinha.elementAt( COL_SEL ) ) {
				if ( ( "".equals( (String) vLinha.elementAt( COL_AGENCIACLI ) ) ) || ( "".equals( (String) vLinha.elementAt( COL_IDENTCLI ) ) ) ) {
					if ( !completaTabela( i, (Integer) vLinha.elementAt( COL_CODCLI ), (String) vLinha.elementAt( COL_RAZCLI ), (String) vLinha.elementAt( COL_AGENCIACLI ), (String) vLinha.elementAt( COL_IDENTCLI ), (String) vLinha.elementAt( COL_STIPOFEBRABAN ) ) ) {
						retorno = false;
						break;
					}
				}
				hsCli.add( new SiaccUtil().new StuffCli( (Integer) vLinha.elementAt( COL_CODCLI ), new String[] { txtCodBanco.getVlrString(), TIPO_FEBRABAN, (String) vLinha.elementAt( COL_STIPOFEBRABAN ), (String) vLinha.elementAt( COL_AGENCIACLI ), (String) vLinha.elementAt( COL_IDENTCLI ),
						(String) vLinha.elementAt( COL_TIPOREMCLI ) } ) );
				hsRec.add( new SiaccUtil().new StuffRec( (Integer) vLinha.elementAt( COL_CODREC ), (Integer) vLinha.elementAt( COL_NRPARC ),
				/*
				 * String codBanco, String tipoFebraban, String stipoFebraban, String sitRemessa {CODBANCO, TIPOFEBRABAN, STIPOFEBRABAN, SITREMESSA, CODCLI, AGENCIACLI, IDENTCLI, DTVENC, VLRPARC}
				 */
				new String[] { txtCodBanco.getVlrString(), TIPO_FEBRABAN, (String) vLinha.elementAt( COL_STIPOFEBRABAN ), 
						(String) vLinha.elementAt( COL_SITREM ), 
						String.valueOf( (Integer) vLinha.elementAt( COL_CODCLI ) ), 
						(String) vLinha.elementAt( COL_AGENCIACLI ),
						(String) vLinha.elementAt( COL_IDENTCLI ), 
						Funcoes.dataAAAAMMDD( (Date) vLinha.elementAt( COL_DTVENC ) ), 
						((BigDecimal) vLinha.elementAt( COL_VLRAPAG )).toString() } ) );
			}
		}
		if ( retorno ) {
			retorno = persisteDados( hsCli, hsRec );
		}

		return retorno;
	}

	private boolean persisteDados( final HashSet<SiaccUtil.StuffCli> hsCli, 
			final HashSet<SiaccUtil.StuffRec> hsRec ) {

		boolean retorno = true;
		for ( SiaccUtil.StuffCli stfCli : hsCli ) {
			retorno = updateCliente( stfCli.getCodigo(), stfCli.getArgs()[ SiaccUtil.EColcli.CODBANCO.ordinal() ], 
					stfCli.getArgs()[ SiaccUtil.EColcli.TIPOFEBRABAN.ordinal() ], stfCli.getArgs()[ SiaccUtil.EColcli.STIPOFEBRABAN.ordinal() ], 
					stfCli.getArgs()[ SiaccUtil.EColcli.AGENCIACLI.ordinal() ], stfCli.getArgs()[ SiaccUtil.EColcli.IDENTCLI
					.ordinal() ], stfCli.getArgs()[ SiaccUtil.EColcli.TIPOREMCLI.ordinal() ] );
			if ( !retorno ) {
				retorno = false;
				break;
			}
		}
		if ( retorno ) {
			for ( SiaccUtil.StuffRec stfRec : hsRec ) {
				retorno = updateReceber( stfRec.getCodrec(), stfRec.getNParcitrec(), 
						stfRec.getArgs()[ SiaccUtil.EColrec.CODBANCO.ordinal() ], 
						stfRec.getArgs()[ SiaccUtil.EColrec.TIPOFEBRABAN.ordinal() ], 
						stfRec.getArgs()[ SiaccUtil.EColrec.STIPOFEBRABAN.ordinal() ], 
						stfRec.getArgs()[ SiaccUtil.EColrec.SITREMESSA.ordinal() ] );
				if ( !retorno ) {
					retorno = false;
					break;
				}
			}
		}
		return retorno;
	}

	private boolean updatePrefere() {
		boolean retorno = true;
		
		try {
			PreparedStatement ps = con.prepareStatement( "UPDATE SGITPREFERE6 I SET NROSEQ=? " +
					"WHERE I.CODEMP=? AND I.CODFILIAL=? AND I.CODEMPBO=? AND " +
					"I.CODFILIALBO=? AND I.CODBANCO=? AND I.TIPOFEBRABAN=?");
			ps.setInt( 1, (Integer) prefs.get(SiaccUtil.EPrefs.NROSEQ) );
			ps.setInt( 2, Aplicativo.iCodEmp);
			ps.setInt( 3, ListaCampos.getMasterFilial( "SGITPREFERE6" ));
			ps.setInt( 4, Aplicativo.iCodEmp );
			ps.setInt( 5, ListaCampos.getMasterFilial( "FNBANCO" ) );
			ps.setString( 6, (String) prefs.get( SiaccUtil.EPrefs.CODBANCO ) );
			ps.setString( 7, TIPO_FEBRABAN );
			ps.executeUpdate();
			ps.close();
			if (!con.getAutoCommit()) {
				con.commit();
			}
		}
		catch (SQLException e) {
			retorno = false;
			Funcoes.mensagemErro( this, "Erro atualizando parâmetros!\n" + e.getMessage() );
		}

		return retorno;
	}
	
	private boolean completaTabela( final int linha, final Integer codCli, final String razCli, 
			final String agenciaCli, final String identCli, final String subTipo ) {

		boolean retorno = true;

		Object[] valores = DLIdentCli.execIdentCli( this, codCli, razCli, agenciaCli, identCli, subTipo );
		retorno = ( (Boolean) valores[ 0 ] ).booleanValue();

		if ( retorno ) {
			ajustaClientes( codCli, (String) valores[ 1 ], (String) valores[ 2 ], (String) valores[ 3 ] );
		}
		else {
			tab.setValor( false, linha, COL_SEL );
		}

		return retorno;
	}

	private void ajustaClientes( final Integer codCli, final String agenciaCli, final String identCli, final String subTipo ) {

		for ( int i = 0; i < tab.getNumLinhas(); i++ ) {
			if ( ( (Boolean) tab.getValor( i, COL_SEL ) ).booleanValue() && codCli.equals( (Integer) tab.getValor( i, COL_CODCLI ) ) ) {
				tab.setValor( agenciaCli, i, COL_AGENCIACLI );
				tab.setValor( identCli, i, COL_IDENTCLI );
				tab.setValor( subTipo, i, COL_STIPOFEBRABAN );
			}
		}
	}

	public void actionPerformed( ActionEvent evt ) {
	
		if ( evt.getSource() == btCarrega ) {
			lbStatus.setText( "      carregando tabela ..." );
			carregaTab();
		}
		else if ( evt.getSource() == btSelTudo ) {
			selecionaTudo();
		}
		else if ( evt.getSource() == btSelNada ) {
			selecionaNada();
		}
		else if ( evt.getSource() == btExporta ) {
			execExporta();
		}
	}

	public void mouseClicked( MouseEvent e ) {

		if ( e.getClickCount() == 2 && e.getSource() == tab && tab.getLinhaSel() > -1 ) {
			completaTabela( tab.getLinhaSel(), (Integer) tab.getValor( tab.getLinhaSel(), COL_CODCLI ), (String) tab.getValor( tab.getLinhaSel(), COL_RAZCLI ), (String) tab.getValor( tab.getLinhaSel(), COL_AGENCIACLI ), (String) tab.getValor( tab.getLinhaSel(), COL_IDENTCLI ), (String) tab
					.getValor( tab.getLinhaSel(), COL_STIPOFEBRABAN ) );
		}
	}

	public void mouseEntered( MouseEvent e ) {

	}

	public void mouseExited( MouseEvent e ) {

	}

	public void mousePressed( MouseEvent e ) {

	}

	public void mouseReleased( MouseEvent e ) {

	}

	public void setConexao( Connection cn ) {

		super.setConexao( cn );
		lcBanco.setConexao( cn );
	}

}
