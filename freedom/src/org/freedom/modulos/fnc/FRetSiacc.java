/**
 * @version 14/03/2007 <BR>
 * @author Setpoint Informática Ltda./Alex Rodrigues<BR>
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
 * Tela de leitura do arquivo de retorno.
 * 
 */

package org.freedom.modulos.fnc;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

import org.freedom.bmps.Icone;
import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JPanelPad;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.Tabela;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FFilho;

public class FRetSiacc extends FFilho implements ActionListener {

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

	private JPanelPad panelRodape = null;

	private final JPanelPad panelRemessa = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private final JPanelPad panelFiltros = new JPanelPad();

	private final JPanelPad panelTabela = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private final JPanelPad panelFuncoes = new JPanelPad();

	private final JPanelPad panelStatus = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private final Tabela tab = new Tabela();

	private final JTextFieldPad txtCodBanco = new JTextFieldPad( JTextFieldPad.TP_STRING, 3, 0 );

	private final JTextFieldFK txtNomeBanco = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private final JButton btCarrega = new JButton( "Buscar", Icone.novo( "btExecuta.gif" ) );

	private final JButton btImporta = new JButton( "Importar", Icone.novo( "btSalvar.gif" ) );

	private final JButton btSelTudo = new JButton( Icone.novo( "btTudo.gif" ) );

	private final JButton btSelNada = new JButton( Icone.novo( "btNada.gif" ) );

	private final JLabel lbStatus = new JLabel();

	private final ListaCampos lcBanco = new ListaCampos( this );

	private FileReader fileReadesSiacc = null;

	public FRetSiacc() {

		super( false );
		setTitulo( "Leitura do arquivo de retorno" );
		setAtribos( 10, 10, 780, 540 );

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

		tab.adicColuna( "" );
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

		tab.setColunaEditavel( COL_SEL, true );

		btCarrega.addActionListener( this );
		btSelTudo.addActionListener( this );
		btSelNada.addActionListener( this );
		btImporta.addActionListener( this );

		btSelTudo.setToolTipText( "Selecionar tudo" );
		btSelNada.setToolTipText( "Limpar seleção" );

	}

	private void montaTela() {

		pnCliente.add( panelRemessa, BorderLayout.CENTER );

		panelRemessa.add( panelFiltros, BorderLayout.NORTH );
		panelRemessa.add( panelTabela, BorderLayout.CENTER );
		panelRemessa.add( panelStatus, BorderLayout.SOUTH );

		panelFiltros.setPreferredSize( new Dimension( 300, 110 ) );
		panelFiltros.adic( new JLabel( "Cód.banco" ), 7, 0, 90, 20 );
		panelFiltros.adic( txtCodBanco, 7, 20, 90, 20 );
		panelFiltros.adic( new JLabel( "Nome do banco" ), 100, 0, 300, 20 );
		panelFiltros.adic( txtNomeBanco, 100, 20, 300, 20 );
		panelFiltros.adic( btImporta, 500, 20, 150, 30 );
		panelFiltros.adic( btCarrega, 500, 60, 150, 30 );

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
	}

	private void carregaTab() {

		// ler dados do arquivo.

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

	private void execImportar() {
		
		if ("".equals( txtCodBanco.getVlrString())){
			Funcoes.mensagemInforma( this, "Selecione o Banco!!" );
			txtCodBanco.requestFocus();
			
		}
		else{
			
			lbStatus.setText( "     lendo do arquivo ..." );

			FileDialog fileDialogSiacc = null;
			fileDialogSiacc = new FileDialog( Aplicativo.telaPrincipal, "Impportar arquivo." );
			fileDialogSiacc.setVisible( true );

			if ( fileDialogSiacc.getFile() == null ) {
				lbStatus.setText( "" );
				return;
			}
			
			String sFileName = fileDialogSiacc.getDirectory() + fileDialogSiacc.getFile();

			File fileSiacc = new File( sFileName );

			try {

				fileReadesSiacc = new FileReader( fileSiacc );
				if ( fileReadesSiacc == null ) {
					Funcoes.mensagemInforma( this, "Arquivo não encontrado" );
				}
				else {
					leArquivo();
				}

			} catch ( IOException ioError ) {
				Funcoes.mensagemErro( this, "Erro ao ler o arquivo: " + sFileName + "\n" + ioError.getMessage() );
				lbStatus.setText( "" );
				return;
			
			}
		}
	}
		
	private void leArquivo() throws IOException {

		ArrayList<Object> list = new ArrayList<Object>();
		String row = null;
		String log = "";
		char tipo;
		BufferedReader in = new BufferedReader( fileReadesSiacc );

		while ( ( row = in.readLine() ) != null ) {

			tipo = row.charAt( 0 );
			switch ( tipo ) {
				case 'A' :
					list.add( (new SiaccUtil().new RegA('A', row)) );
					break;
				case 'B' :
					list.add( getRegistroB( row ) );
					break;
				case 'C' :
					list.add( getRegistroC( row ) );
					break;
				default :
					break;
			}
			
			log += row+"\n";
		}

		in.close();
	}
	
	private RegA getRegistroA( final String arg ) {
		
		RegA rega = new RegA();
		
		rega.A01 = arg.substring( 0, 1 );
		rega.A02 = arg.substring( 1, 2 ).trim().length() > 0 ? new Integer( arg.substring( 1, 2 ).trim() ) : null;
		rega.A03 = arg.substring( 2, 22 );
		rega.A04 = arg.substring( 22, 42 );
		rega.A05 = arg.substring( 42, 45 ).trim().length() > 0 ? new Integer( arg.substring( 42, 45 ).trim() ) : null;
		rega.A06 = arg.substring( 45, 65 );
		rega.A07 = arg.substring( 65, 73 ).trim().length() > 0 ? new Integer( arg.substring( 65, 73 ).trim() ) : null;
		rega.A08 = arg.substring( 73, 79 ).trim().length() > 0 ? new Integer( arg.substring( 73, 79 ).trim() ) : null;
		rega.A09 = arg.substring( 79, 81 ).trim().length() > 0 ? new Integer( arg.substring( 79, 81 ).trim() ) : null;
		rega.A10 = arg.substring( 81, 98 );
		rega.A11 = arg.substring( 98, 114 );
		rega.A12 = arg.substring( 114, 115 );
		rega.A13 = arg.substring( 115, 116 );
		rega.A14 = arg.substring( 116, 143 );
		rega.A15 = arg.substring( 143, 149 ).trim().length() > 0 ?  new Integer( arg.substring( 143, 149 ).trim() ) : null;
		rega.A16 = arg.substring( 149 );
		
		return rega;
	}
	
	private RegB getRegistroB( final String arg ) {
		
		RegB regb = new RegB();
		
		regb.B01 = arg.substring( 0, 1 );
		regb.B02 = arg.substring( 1, 26 );
		regb.B03 = arg.substring( 26, 30 ).trim().length() > 0 ? new Integer( arg.substring( 26, 30 ).trim() ) : null;
		regb.B04 = arg.substring( 30, 44 );
		regb.B05 = arg.substring(  44, 52  ).trim().length() > 0 ? new Integer( arg.substring( 44, 52 ).trim() ) : null;
		regb.B06 = arg.substring( 52, 148 );
		regb.B07 = arg.substring( 148, 149 ).trim().length() > 0 ? new Integer( arg.substring( 148, 149 ).trim() ) : null;
		regb.B08 = arg.substring( 149 ).trim().length() > 0 ? new Integer( arg.substring( 149 ).trim() ) : null;
		
		return regb;
	}
	
	private RegC getRegistroC( final String arg ) {
		
		RegC regc = new RegC();
		
		regc.C01 = arg.substring( 0, 1 );
		regc.C02 = arg.substring( 1, 26 );
		regc.C03 = arg.substring( 26, 30 ).trim().length() > 0 ? new Integer( arg.substring( 26, 30 ) ) : null;
		regc.C04 = arg.substring( 30, 44 );
		regc.C05 = arg.substring( 44, 84 );
		regc.C06 = arg.substring( 84, 124 );
		regc.C07 = arg.substring( 124, 143 );
		regc.C08 = arg.substring( 143, 149 ).trim().length() > 0 ? new Integer( arg.substring( 143, 149 ) ) : null;
		regc.C09 = arg.substring( 149 ).trim().length() > 0 ? new Integer( arg.substring( 149 ) ) : null;
		
		return regc;
	}
	
	private class RegA {
		
		String A01 = null;
		Integer A02 = null;
		String A03 = null;
		String A04 = null;
		Integer A05 = null;
		String A06 = null;
		Integer A07 = null;
		Integer A08 = null;
		Integer A09 = null;
		String A10 = null;
		String A11 = null;
		String A12 = null;
		String A13 = null;
		String A14 = null;
		Integer A15 = null;
		String A16 = null;		
		
		public RegA(){ }
	}
	
	private class RegB {
		
		String B01 = null;
		String B02 = null;
		Integer B03 = null;
		String B04 = null;
		Integer B05 = null;
		String B06 = null;
		Integer B07 = null;
		Integer B08 = null;	
		
		public RegB(){ }
	}
	
	private class RegC {
		
		String C01 = null;
		String C02 = null;
		Integer C03 = null;
		String C04 = null;
		String C05 = null;
		String C06 = null;
		String C07 = null;
		Integer C08 = null;
		Integer C09 = null;	
		
		public RegC(){ }
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
		else if ( evt.getSource() == btImporta ) {
			execImportar();
		}
	}

	public void setConexao( Connection cn ) {

		super.setConexao( cn );
		lcBanco.setConexao( cn );
	}

}
