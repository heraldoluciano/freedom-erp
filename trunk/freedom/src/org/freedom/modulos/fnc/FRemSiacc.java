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
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.HashSet;
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

public class FRemSiacc extends FFilho implements ActionListener {

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

	private final JButton btCarrega = new JButton( "Buscar", Icone.novo( "btExecuta.gif" ) );

	private final JButton btExporta = new JButton( "Exportar", Icone.novo( "btSalvar.gif" ) );

	private final JButton btSelTudo = new JButton( Icone.novo( "btTudo.gif" ) );

	private final JButton btSelNada = new JButton( Icone.novo( "btNada.gif" ) );

	private final JLabel lbStatus = new JLabel();

	private final ListaCampos lcBanco = new ListaCampos( this );

	private FileWriter fileWriterSiacc = null;

	public FRemSiacc() {

		super( false );
		setTitulo( "Manutenção de contas a receber" );
		setAtribos( 10, 10, 780, 540 );

		Vector<String> vVals = new Vector<String>();
		Vector<String> vLabs = new Vector<String>();
		vVals.addElement( "E" );
		vVals.addElement( "V" );
		vLabs.addElement( "Emissão" );
		vLabs.addElement( "Vencimento" );
		rgData = new JRadioGroup( 2, 1, vLabs, vVals );

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

		tab.setColunaEditavel( COL_SEL, true );

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

		panelFiltros.setPreferredSize( new Dimension( 300, 120 ) );
		panelFiltros.adic( new JLabel( "Cód.banco" ), 7, 0, 90, 20 );
		panelFiltros.adic( txtCodBanco, 7, 20, 90, 20 );
		panelFiltros.adic( new JLabel( "Nome do banco" ), 100, 0, 300, 20 );
		panelFiltros.adic( txtNomeBanco, 100, 20, 300, 20 );

		JLabel bordaData = new JLabel();
		bordaData.setBorder( BorderFactory.createEtchedBorder() );

		panelFiltros.adic( new JLabel( "filtro:" ), 7, 40, 60, 20 );
		panelFiltros.adic( rgData, 7, 60, 120, 50 );
		panelFiltros.adic( new JLabel( "Período:" ), 130, 40, 80, 20 );
		panelFiltros.adic( txtDtIni, 145, 75, 100, 20 );
		panelFiltros.adic( new JLabel( "até", SwingConstants.CENTER ), 245, 75, 40, 20 );
		panelFiltros.adic( txtDtFim, 285, 75, 100, 20 );
		panelFiltros.adic( bordaData, 130, 60, 270, 50 );

		panelFiltros.adic( btCarrega, 500, 70, 150, 30 );

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

	private ResultSet executeQuery() throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sDtFiltro = "E".equals( rgData.getVlrString() ) ? "IR.DTITREC" : "IR.DTVENCITREC";
		StringBuilder sSQL = new StringBuilder();
		sSQL.append( "SELECT IR.CODREC, IR.NPARCITREC, R.DOCREC, R.CODCLI, C.RAZCLI, IR.DTITREC, IR.DTVENCITREC," );
		sSQL.append( "IR.VLRAPAGITREC, FC.AGENCIACLI, FC.IDENTCLI, COALESCE(FR.SITREMESSA,'00') SITREMESSA, ");
		sSQL.append( "FR.SITRETORNO, COALESCE(COALESCE(FR.STIPOFEBRABAN,FC.STIPOFEBRABAN),'02') STIPOFEBRABAN " );
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
		sSQL.append( "IR.CODEMPBO=? AND IR.CODFILIALBO=? AND IR.CODBANCO=? " );
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
				tab.setValor( rs.getString( "STIPOFEBRABAN") , i, COL_STIPOFEBRABAN );
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

	private void execExporta() {

		if ( consisteExporta() ) {
			
			lbStatus.setText( "     criando arquivo ..." );

			FileDialog fileDialogSiacc = null;
			fileDialogSiacc = new FileDialog( Aplicativo.telaPrincipal, "Exportar arquivo.", FileDialog.SAVE );
			fileDialogSiacc.setFile( "remessasiacc.txt" );
			fileDialogSiacc.setVisible( true );

			if ( fileDialogSiacc.getFile() == null ) {
				lbStatus.setText( "" );
				return;
			}

			String sFileName = fileDialogSiacc.getDirectory() + fileDialogSiacc.getFile();

			File fileSiacc = new File( sFileName );

			if ( fileSiacc.exists() ) {
				if ( Funcoes.mensagemConfirma( this, "Arquivo: '" + sFileName + "' já existe! Deseja sobrescrever?" ) != 0 ) {
					return;
				}
			}

			try {
				fileSiacc.createNewFile();
			} catch ( IOException err ) {
				Funcoes.mensagemErro( this, "Erro limpando arquivo: " + sFileName + "\n" + err.getMessage(), true, con, err );
				lbStatus.setText( "" );
				return;
			}

			try {
				fileWriterSiacc = new FileWriter( fileSiacc );
			} catch ( IOException ioError ) {
				Funcoes.mensagemErro( this, "Erro Criando o arquivo: " + sFileName + "\n" + ioError.getMessage() );
				lbStatus.setText( "" );
				return;
			}
			
			lbStatus.setText( "     gravando arquivo ..." );
			// motar layout...

		}
	}
	
	private boolean updateCliente(int codCli, String codBanco, String tipoFebraban, String agenciaCli, String identCli) {
		boolean retorno = false;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement("SELECT AGENCIACLI, IDENTCLI FROM FNFBNCLI WHERE CODEMP=? AND CODFILIAL=? AND " +
					"CODCLI=? AND CODEMPPF=? AND CODFILIALPF=? AND CODEMPBO=? AND CODFILIALBO=? AND " +
					"CODBANCO=? AND TIPOFEBRABAN=?");
			ps.setInt(1, Aplicativo.iCodEmp);
			ps.setInt(2, ListaCampos.getMasterFilial("VDCLIENTE"));
			ps.setInt(3, codCli);
			ps.setInt(4, Aplicativo.iCodEmp);
			ps.setInt(5, ListaCampos.getMasterFilial("SGITPREFERE6"));
			ps.setInt(6, Aplicativo.iCodEmp);
			ps.setInt(7, ListaCampos.getMasterFilial("FNBANCO"));
			ps.setString(8, codBanco);
			ps.setString(9, tipoFebraban);
			rs = ps.executeQuery();
			if (rs.next()) {
				if ( (!agenciaCli.equals(rs.getString("AGENCIACLI"))) || (!identCli.equals(rs.getString("IDENTCLI")))) {
					ps = con.prepareStatement("UPDATE FNFBNCLI SET AGENCIACLI=?, IDENTCLI=? WHERE CODEMP=? AND CODFILIAL=? AND " +
							"CODCLI=? AND CODEMPPF=? AND CODFILIALPF=? AND CODEMPBO=? AND CODFILIALBO=? AND " +
							"CODBANCO=? AND TIPOFEBRABAN=?");
					ps.setString(1, agenciaCli);
					ps.setString(2, identCli);
					ps.setInt(3, Aplicativo.iCodEmp);
					ps.setInt(4, ListaCampos.getMasterFilial("VDCLIENTE"));
					ps.setInt(5, codCli);
					ps.setInt(6, Aplicativo.iCodEmp);
					ps.setInt(7, ListaCampos.getMasterFilial("SGITPREFERE6"));
					ps.setInt(8, Aplicativo.iCodEmp);
					ps.setInt(9, ListaCampos.getMasterFilial("FNBANCO"));
					ps.setString(10, codBanco);
					ps.setString(11, tipoFebraban);
				}
			} else {
				ps = con.prepareStatement("INSERT INTO FNFBNCLI (AGENCIACLI, IDENTCLI, CODEMP, CODFILIAL, " +
						"CODCLI, CODEMPPF, CODFILIALPF, CODEMPBO, CODFILIALBO, CODBANCO, TIPOFEBRABAN) " +
						"VALUES (?,?,?,?,?,?,?,?,?,?,?)");
				ps.setString(1, agenciaCli);
				ps.setString(2, identCli);
				ps.setInt(3, Aplicativo.iCodEmp);
				ps.setInt(4, ListaCampos.getMasterFilial("VDCLIENTE"));
				ps.setInt(5, codCli);
				ps.setInt(6, Aplicativo.iCodEmp);
				ps.setInt(7, ListaCampos.getMasterFilial("SGITPREFERE6"));
				ps.setInt(8, Aplicativo.iCodEmp);
				ps.setInt(9, ListaCampos.getMasterFilial("FNBANCO"));
				ps.setString(10, codBanco);
				ps.setString(11, tipoFebraban);
				ps.executeUpdate();
			}
			if (!con.getAutoCommit()) 
				con.commit();
			//rs.close();
			
		} catch (SQLException e) {
			Funcoes.mensagemErro(this, "Erro atualizando cliente!\n" + e.getMessage());
		}
		
		return retorno;
	}

	private boolean updateReceber(int codRec, int nParcitrec, String codBanco, String tipoFebraban, 
			String stipoFebraban, String sitRemessa) {
		boolean retorno = false;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement("SELECT CODBANCO, TIPOFEBRABAN, STIPOFEBRABAN, SITREMESSA " +
					"FROM FNFBNREC WHERE CODEMP=? AND CODFILIAL=? AND CODREC=? AND NPARCITREC=?");
			ps.setInt(1, Aplicativo.iCodEmp);
			ps.setInt(2, ListaCampos.getMasterFilial("FNITRECEBER"));
			ps.setInt(3, codRec);
			ps.setInt(4, nParcitrec);
			rs = ps.executeQuery();
			if (rs.next()) {
				if ( (!codBanco.equals(rs.getString("CODBANCO"))) || (!tipoFebraban.equals(rs.getString("TIPOFEBRABAN"))) ||
				     (!stipoFebraban.equals(rs.getString("STIPOFEBRABAN"))) || (!sitRemessa.equals(rs.getString("SITREMESSA")))) {
					ps = con.prepareStatement("UPDATE FNFBNREC SET CODBANCO=?, TIPOFEBRABAN=?, STIPOFEBRABAN=?, " +
							"STIPOFEBRABAN=?, SITREMESSA=? " +
							"FROM FNFBNREC WHERE CODEMP=? AND CODFILIAL=? AND CODREC=? AND NPARCITREC=?");
 					ps.setString(1, codBanco);
					ps.setString(2, tipoFebraban);
					ps.setString(3, stipoFebraban);
					ps.setString(4, sitRemessa);
					ps.setInt(5, Aplicativo.iCodEmp);
					ps.setInt(6, ListaCampos.getMasterFilial("FNITRECEBER"));
					ps.setInt(7, codRec);
					ps.setInt(8, nParcitrec);
					ps.executeUpdate();
				}
			} else {
				ps = con.prepareStatement("INSERT INTO FNFBNREC (CODEMP, CODFILIAL, CODREC, NPARCITREC, " +
						"CODEMPPF, CODFILIALPF, CODEMPBO, CODFILIALBO, CODBANCO, TIPOFEBRABAN, STIPOFEBRABAN, " +
						"SITREMESSA) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) " +
						"FROM FNFBNREC WHERE CODEMP=? AND CODFILIAL=? AND CODREC=? AND NPARCITREC=?");
				ps.setInt(1, Aplicativo.iCodEmp);
				ps.setInt(2, ListaCampos.getMasterFilial("FNFBNREC"));
				ps.setInt(3,codRec);
				ps.setInt(4, nParcitrec);
				ps.setInt(5, Aplicativo.iCodEmp);
				ps.setInt(6, ListaCampos.getMasterFilial("SGITPREFERE6"));
				ps.setInt(7, Aplicativo.iCodEmp);
				ps.setInt(8, ListaCampos.getMasterFilial("FNBANCO"));
				ps.setString(9, codBanco);
				ps.setString(10, tipoFebraban);
				ps.setString(11, stipoFebraban);
				ps.setString(12, sitRemessa);
				ps.executeUpdate();
			}
			if (!con.getAutoCommit()) 
				con.commit();
			//rs.close();
			
		} catch (SQLException e) {
			Funcoes.mensagemErro(this, "Erro situação do contas a receber!\n" + e.getMessage());
		}
		
		return retorno;
	}

	private void gravaNoArquivo() {

		try {
			fileWriterSiacc.write( "dados" );
			fileWriterSiacc.flush();
		} catch ( IOException err ) {
			Funcoes.mensagemErro( this, "Erro grando no arquivo!\n" + err.getMessage(), true, con, err );
		}
	}

	private class StuffCli {
		private String[] stfArgs = null;
		private Integer codigo = null;
		private StuffCli(Integer codCli, String[] args) {
			//System.out.println(args.length);
			this.codigo = codCli;
			this.stfArgs = args;
		}
		public String[] getArgs() {
			return this.stfArgs;
		}
		public Integer getCodigo() {
			return this.codigo;
		}
		public boolean equals(Object obj) {
			if (obj instanceof StuffCli)
			   return codigo.equals( ((StuffCli) obj).getCodigo());
			else
			   return false;
		}
	}

	private class StuffRec {
		private String[] stfArgs = null;
		private Integer chave1 = null;
		private Integer chave2 = null;
		private StuffRec(Integer codRec, Integer nParcItRec, String[] args) {
			//System.out.println(args.length);
			this.chave1 = codRec;
			this.chave2 = nParcItRec;
			this.stfArgs = args;
		}
		public String[] getArgs() {
			return this.stfArgs;
		}
		public Integer getCodrec() {
			return this.chave1;
		}
		public Integer getNParcitrec() {
			return this.chave2;
		}
		public boolean equals(Object obj) {
			if (obj instanceof StuffRec)
			   return ( (chave1.equals( ((StuffRec) obj).getCodrec())) && 
					    (chave2.equals( ((StuffRec) obj).getNParcitrec())) );
			else
			   return false;
		}
	}
	
	private boolean consisteExporta() {

		boolean retorno = true;
		Vector vLinha = null;
		
		HashSet<StuffCli> hsCli = new HashSet<StuffCli>();
		HashSet<StuffRec> hsRec = new HashSet<StuffRec>();

		for ( int i = 0; i < tab.getNumLinhas(); i++ ) {

			vLinha = tab.getLinha( i );

			if ( (Boolean) vLinha.elementAt( COL_SEL ) ) { 
				if ( ( "".equals( (String) vLinha.elementAt( COL_AGENCIACLI ) ) ) || ( "".equals( (String) vLinha.elementAt( COL_IDENTCLI ) ) ) ) {
					if ( !completaTabela( i, (Integer) vLinha.elementAt( COL_CODCLI ), (String) vLinha.elementAt( COL_RAZCLI ), (String) vLinha.elementAt( COL_AGENCIACLI ), (String) vLinha.elementAt( COL_IDENTCLI ) ) ) {
						retorno = false;
						break;
					}
				}
				hsCli.add( new StuffCli( (Integer) vLinha.elementAt( COL_CODCLI ), 
						 new String[] {
						  txtCodBanco.getVlrString(), "01" ,
						  (String) vLinha.elementAt( COL_AGENCIACLI ),
						  (String) vLinha.elementAt( COL_IDENTCLI )
						  }));
				hsRec.add( new StuffRec( (Integer) vLinha.elementAt( COL_CODREC ),
						(Integer) vLinha.elementAt( COL_NRPARC ), /*String codBanco, String tipoFebraban, 
			String stipoFebraban, String sitRemessa*/
						 new String[] {
						  txtCodBanco.getVlrString(), "01" , "01", 
						  (String) vLinha.elementAt( COL_SITREM)
						  }));
			}
		}
		if (retorno) {
			retorno = persisteDados(hsCli, hsRec);
		}

		return retorno;
	}

	private boolean persisteDados(final HashSet<StuffCli> hsCli, final HashSet<StuffRec> hsRec) {
		boolean retorno = true;
		for (StuffCli stfCli: hsCli) {
			retorno = updateCliente(stfCli.getCodigo(), stfCli.getArgs()[0], 
					stfCli.getArgs()[1], stfCli.getArgs()[2], stfCli.getArgs()[3]);
			if (!retorno) {
				retorno = false;
				break;
			}
		}
		if (retorno) {
			for (StuffRec stfRec: hsRec) {
				retorno = updateReceber(stfRec.getCodrec(), stfRec.getNParcitrec(), stfRec.getArgs()[0], 
						stfRec.getArgs()[1], stfRec.getArgs()[2], stfRec.getArgs()[3]);
				if (!retorno) {
					retorno = false;
					break;
				}
			}
		}
		return retorno;
	}
	
	private boolean completaTabela( final int linha, final Integer codCli, final String razCli, final String agenciaCli, final String identCli ) {

		boolean retorno = true;

		Object[] valores = DLIdentCli.execIdentCli( this, codCli, razCli, agenciaCli, identCli );
		retorno = ( (Boolean) valores[ 0 ] ).booleanValue();

		if ( retorno ) {
			ajustaClientes( codCli, (String) valores[ 1 ], (String) valores[ 2 ] );
		}
		else {
			tab.setValor(false, linha, COL_SEL);
		}

		return retorno;
	}

	private void ajustaClientes( final Integer codCli, final String agenciaCli, final String identCli ) {

		for ( int i = 0; i < tab.getNumLinhas(); i++ ) {
			if ( ( (Boolean) tab.getValor( i, COL_SEL ) ).booleanValue() && codCli.equals( (Integer) tab.getValor( i, COL_CODCLI ) ) ) {
				tab.setValor( agenciaCli, i, COL_AGENCIACLI );
				tab.setValor( identCli, i, COL_IDENTCLI );
			}
		}
	}

	private void desmarcaClientes( final Integer codCli ) {

		for ( int i = 0; i < tab.getNumLinhas(); i++ ) {
			if ( ( (Boolean) tab.getValor( i, COL_SEL ) ).booleanValue() && codCli.equals( (Integer) tab.getValor( i, COL_CODCLI ) ) ) {
				tab.setValor( false, i, COL_SEL );
			}
		}
	}

	public void setConexao( Connection cn ) {

		super.setConexao( cn );
		lcBanco.setConexao( cn );
	}

}
