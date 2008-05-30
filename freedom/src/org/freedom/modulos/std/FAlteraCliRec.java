/**
 * @version 29/05/2008 <BR>
 * @author Setpoint Informática Ltda./Reginaldo Garcia Heua <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)FAlteraCliRec.java <BR>
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
 */
package org.freedom.modulos.std;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.SwingConstants;


import org.freedom.acao.RadioGroupEvent;
import org.freedom.acao.RadioGroupListener;
import org.freedom.bmps.Icone;
import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JPanelPad;
import org.freedom.componentes.JRadioGroup;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FFilho;

public class FAlteraCliRec extends FFilho implements ActionListener {

	private static final long serialVersionUID = 1L;

	private JPanelPad pinCli = new JPanelPad( 350, 100 );

	private JPanelPad pnRod = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private JTextFieldPad txtCodVenda = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	
	private JTextFieldFK txtSerie = new JTextFieldFK( JTextFieldPad.TP_STRING, 4, 0 );

	private JTextFieldFK txtVlrLiqVenda = new JTextFieldFK( JTextFieldPad.TP_DECIMAL, 15, 2 );

	private JTextFieldFK txtStatusVenda = new JTextFieldFK( JTextFieldPad.TP_STRING, 2, 0 );
	
	private JTextFieldFK txtDoc = new JTextFieldFK( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldPad txtCodCli = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private JTextFieldFK txtRazCli = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );
	
	private ListaCampos lcVenda = new ListaCampos( this );
	
	private ListaCampos lcCli = new ListaCampos( this );
	
	private JButton btTrocaDoc = new JButton(Icone.novo("btTrocaNumero.gif"));

	private Vector<String> vLabs1 = new Vector<String>();
	
	private Vector<String> vVals1 = new Vector<String>();
	
	private JRadioGroup<?, ?> rgTipo = null;

	
	public FAlteraCliRec() {

		super( false );
		setTitulo( "Alteração de cliente/receber" );
		setAtribos( 50, 50, 370, 230 );
		
		montaTela();
		montaListaCampos();
		btTrocaDoc.addActionListener( this );
		
	}
		private void montaTela() {

		Container c = getContentPane();
		c.setLayout( new BorderLayout() );

		pnRod.setPreferredSize( new Dimension( 350, 30 ) );
		pnRod.add( adicBotaoSair(), BorderLayout.EAST );

		c.add( pnRod, BorderLayout.SOUTH );
		c.add( pinCli, BorderLayout.CENTER );

		vLabs1.addElement( "Venda" );
		vLabs1.addElement( "ECF" );
		vVals1.addElement( "V" );
		vVals1.addElement( "E" );

		rgTipo = new JRadioGroup<String, String>( 1, 2, vLabs1, vVals1 );
		rgTipo.setVlrString( "E" );

		pinCli.adic( rgTipo, 7, 7, 330, 30 );
		pinCli.adic( new JLabelPad( "Pedido:" ), 7, 40, 80, 20 );
		pinCli.adic( txtCodVenda, 7, 60, 80, 20 );
		pinCli.adic( new JLabelPad( "Série" ), 90, 40, 67, 20 );
		pinCli.adic( txtSerie, 90, 60, 67, 20 );
		pinCli.adic( new JLabelPad( "Valor" ), 160, 40, 100, 20 );
		pinCli.adic( txtVlrLiqVenda, 160, 60, 100, 20 );
		pinCli.adic( new JLabelPad( "Doc." ), 263, 40, 73, 20 );
		pinCli.adic( txtDoc, 263, 60, 75, 20 );
		pinCli.adic( new JLabelPad( "Cód.Cli" ), 7, 80, 80, 20 );
		pinCli.adic( txtCodCli, 7, 100, 80, 20 );
		pinCli.adic( new JLabelPad( "Razão social do cliente" ), 90, 80, 245, 20 );
		pinCli.adic( txtRazCli, 90, 100, 250, 20 );
		pinCli.adic( btTrocaDoc, 310, 130, 30, 30 );

	}
	
	private void montaListaCampos() {

		/*************
		 *  Pedido   *
		 *************/
		
		lcVenda.add( new GuardaCampo(rgTipo, "TipoVenda", "Tipo venda", ListaCampos.DB_PK, true) );
		lcVenda.add( new GuardaCampo( txtCodVenda, "CodVenda", "Cód.venda", ListaCampos.DB_PK, true ) );
		lcVenda.add(new GuardaCampo( txtDoc, "DocVenda", "Documento", ListaCampos.DB_SI, false));
		lcVenda.add( new GuardaCampo( txtSerie, "Serie", "Série", ListaCampos.DB_SI, false ) );
		lcVenda.add( new GuardaCampo( txtVlrLiqVenda, "VlrLiqVenda", "V. liq.", ListaCampos.DB_SI, false ) );
		lcVenda.add( new GuardaCampo( txtStatusVenda, "StatusVenda", "Status", ListaCampos.DB_SI, false ) );
		lcVenda.add( new GuardaCampo( txtCodCli, "CodCli", "Cód.Cli", ListaCampos.DB_FK, false ) );				
		lcVenda.montaSql( false, "VENDA", "VD" );
		lcVenda.setReadOnly( true );
		txtCodVenda.setTabelaExterna( lcVenda );
		txtCodVenda.setFK( true );
		txtCodVenda.setNomeCampo( "CodVenda" );
		
		/*************
		 * Cliente   * 
		*************/
		
		lcCli.add( new GuardaCampo( txtCodCli, "CodCli", "Cód.cli.", ListaCampos.DB_PK, false ) );
		lcCli.add( new GuardaCampo( txtRazCli, "RazCli", "Razão social do cliente", ListaCampos.DB_SI, false ) );
		txtCodCli.setTabelaExterna( lcCli );
		txtCodCli.setNomeCampo( "CodCli" );
		txtCodCli.setFK( true );
		lcCli.setReadOnly( true );
		lcCli.montaSql( false, "CLIENTE", "VD" );
	}
	
	private void alteraEManut( String sManut ) throws SQLException {
		
		PreparedStatement ps = null;
		StringBuilder sSQL = new StringBuilder();

		sSQL.append( "UPDATE VDVENDA SET EMMANUT=? WHERE CODEMP=? AND CODFILIAL=? AND TIPOVENDA=? AND CODVENDA=?" );

		ps = con.prepareStatement( sSQL.toString() );
		ps.setString( 1, sManut );
		ps.setInt( 2, Aplicativo.iCodEmp );
		ps.setInt( 3, ListaCampos.getMasterFilial( "VDVENDA" ) );
		ps.setString( 4, rgTipo.getVlrString() );
		ps.setInt( 5, txtCodVenda.getVlrInteger() );
		ps.executeUpdate();

		if ( !con.getAutoCommit() ) {
			con.commit();
		}
	
	}
	
	private void alteraVenda( int codCli, String tipoVenda,  int codVenda ) throws SQLException {
	
		PreparedStatement ps = null;
		StringBuilder sSQL = new StringBuilder();
		
		sSQL.append( "UPDATE VDVENDA SET CODEMPCL=?, CODFILIALCL=?, CODCLI=? WHERE CODEMP=? AND CODFILIAL=? AND TIPOVENDA=? AND CODVENDA=?" );
		
		ps = con.prepareStatement( sSQL.toString() );
		ps.setInt( 1, Aplicativo.iCodEmp );
		ps.setInt( 2, ListaCampos.getMasterFilial( "VDCLIENTE" ) );
		ps.setInt( 3, codCli );
		ps.setInt( 4, Aplicativo.iCodEmp );
		ps.setInt( 5, ListaCampos.getMasterFilial( "VDVENDA" ) );
		ps.setString( 6, tipoVenda );
		ps.setInt( 7, codVenda );
		ps.executeUpdate();

		if ( !con.getAutoCommit() ) {
			con.commit();
		}
	}
	
	private void alteraReceber( int codCli,  String tipoVenda,  int codVenda ) throws SQLException {
		
		PreparedStatement ps = null;
		StringBuilder sSQL = new StringBuilder();
		
		sSQL.append( "UPDATE FNRECEBER SET CODEMPCL=?, CODFILIALCL=?, CODCLI=? WHERE CODEMPVA=? AND CODFILIALVA=? AND TIPOVENDA=? AND CODVENDA=?" );
		ps = con.prepareStatement( sSQL.toString() );
		ps.setInt( 1, Aplicativo.iCodEmp );
		ps.setInt( 2, ListaCampos.getMasterFilial( "VDCLIENTE" ) );
		ps.setInt( 3, codCli );
		ps.setInt( 4, Aplicativo.iCodEmp );
		ps.setInt( 5, ListaCampos.getMasterFilial( "VDVENDA" ) );
		ps.setString( 6, tipoVenda );
		ps.setInt( 7, codVenda );
		ps.executeUpdate();

		if ( !con.getAutoCommit() ) {
			con.commit();
		}
	
	}
	private void alteraCli(){

		if ( txtCodVenda.getVlrInteger().intValue() == 0  ) {

			Funcoes.mensagemInforma( this, "Informe um pedido!" );
			txtCodVenda.requestFocus();
			return;
		}
		
		if( txtCodCli.getVlrInteger().intValue() == 0 ){
			
			Funcoes.mensagemInforma( this, "Informe o cliente!" );
			txtCodCli.requestFocus();
			return;
		}
		
		try {
			
			alteraEManut( "S" );
			alteraVenda( txtCodCli.getVlrInteger().intValue(), rgTipo.getVlrString(), txtCodVenda.getVlrInteger().intValue() );
			alteraReceber(  txtCodCli.getVlrInteger().intValue(), rgTipo.getVlrString(), txtCodVenda.getVlrInteger().intValue() );
			alteraEManut( "N" );
			
			
			Funcoes.mensagemInforma( this, "Cliente alterado com sucesso!" );
			
		} catch ( SQLException e ) {
		
			e.printStackTrace();
			Funcoes.mensagemErro( this, "Erro ao alterar cliente "  + e.getMessage() );
			
			try {
				if ( !con.getAutoCommit() ) {
					
					con.rollback();
				}
			
			} catch ( SQLException err ) {
			
				err.printStackTrace();
				Funcoes.mensagemErro( this, "Erro na alteração! "  + e.getMessage() );
			}
		}
	}
	
	public void setConexao(Connection cn) {
		
		super.setConexao( cn );
		lcVenda.setConexao( cn );
		lcCli.setConexao( cn );
	}

	public void actionPerformed( ActionEvent e ) {
	
		if( e.getSource() == btTrocaDoc ){
			
			alteraCli();
		}
		
	}
}
