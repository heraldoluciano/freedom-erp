/**
 * @version 01/03/2007 <BR>
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
 * Tela de remessa de arquivo, contendo os dados dos clientes e recebimentos, para o banco selecionado. 
 * 
 */

package org.freedom.modulos.fnc;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;
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
		panelFiltros.adic( new JLabel( "Cód.banco" ), 7, 0, 100, 20 );
		panelFiltros.adic( txtCodBanco, 7, 20, 70, 20 );
		panelFiltros.adic( new JLabel( "Nome do banco" ), 80, 0, 310, 20 );
		panelFiltros.adic( txtNomeBanco, 80, 20, 310, 20 );
		
		JLabel bordaData = new JLabel();
		bordaData.setBorder( BorderFactory.createEtchedBorder() );
		
		JLabel periodo = new JLabel( "Periodo", SwingConstants.CENTER  );
		periodo.setOpaque( true );
		
		JLabel filtro = new JLabel( "filtro", SwingConstants.CENTER  );
		filtro.setOpaque( true );

		panelFiltros.adic( periodo, 15, 40, 80, 20 );
		panelFiltros.adic( txtDtIni, 15, 75, 100, 20 );
		panelFiltros.adic( new JLabel( "até", SwingConstants.CENTER ), 115, 75, 40, 20 );
		panelFiltros.adic( txtDtFim, 155, 75, 100, 20 );
		panelFiltros.adic( bordaData, 7, 50, 256, 60 );

		panelFiltros.adic( filtro, 280, 42, 60, 16 );
		panelFiltros.adic( rgData, 270, 60, 120, 50 );
		
		panelFiltros.adic( btCarrega, 413, 65, 160, 30 );
		
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
		btExporta.setPreferredSize( new Dimension( 160, 30 ) );
		panelRodape.add( btExporta, BorderLayout.WEST );
				
	}
	
	private void carregaTab() {
		
		if ( txtCodBanco.getVlrString().trim().length() < 1 ) {
			Funcoes.mensagemErro( this, "O código do banco é obrigatorio!" );
			return;
		}
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuilder sSQL = new StringBuilder();
		String sDtFiltro = "E".equals( rgData.getVlrString() ) ? "IR.DTITREC" : "IR.DTVENCITREC";
		
		try {
			
			tab.limpa();
			
			sSQL.append( "SELECT IR.CODREC, IR.NPARCITREC, R.DOCREC, R.CODCLI, C.RAZCLI, IR.DTITREC, IR.DTVENCITREC," );
			sSQL.append( "IR.VLRAPAGITREC, FC.AGENCIACLI, FC.IDENTCLI, FR.SITREMESSA, FR.SITRETORNO " );
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
			sSQL.append( " BETWEEN ? AND ? AND IR.STATUSITREC IN ('R1','RL') AND "  );
			sSQL.append( "IR.CODEMPBO=? AND IR.CODFILIALBO=? AND IR.CODBANCO=? " );

			
			ps = con.prepareStatement( sSQL.toString() );
			ps.setDate( 1, Funcoes.dateToSQLDate( txtDtIni.getVlrDate() ) );
			ps.setDate( 2, Funcoes.dateToSQLDate( txtDtFim.getVlrDate() ) );
			ps.setInt( 3, Aplicativo.iCodEmp );
			ps.setInt( 4, ListaCampos.getMasterFilial( "FNITRECEBER" ) );
			ps.setInt( 5, txtCodBanco.getVlrInteger() );
			
			rs = ps.executeQuery();
			
			int i = 0;
			for ( i = 0; rs.next(); i++ ) {
								
				tab.adicLinha();
				tab.setValor( new Boolean( true ), i, COL_SEL );
				tab.setValor( rs.getString( "RAZCLI" ), i, COL_RAZCLI );
				tab.setValor( new Integer(rs.getInt( "CODCLI" )), i, COL_CODCLI );
				tab.setValor( new Integer(rs.getInt( "CODREC" )), i, COL_CODREC );
				tab.setValor( rs.getString( "DOCREC" ), i, COL_DOCREC );
				tab.setValor( new Integer(rs.getInt( "NPARCITREC" )), i, COL_NRPARC );
				tab.setValor( rs.getBigDecimal( "VLRAPAGITREC" ), i, COL_VLRAPAG );
				tab.setValor( rs.getDate( "DTITREC" ), i, COL_DTREC );
				tab.setValor( rs.getDate( "DTVENCITREC" ), i, COL_DTVENC );
				
				tab.setValor( rs.getString( "AGENCIACLI" ), i, COL_AGENCIACLI );
				tab.setValor( rs.getString( "IDENTCLI" ), i, COL_IDENTCLI );
				tab.setValor( rs.getString( "SITREMESSA" ), i, COL_SITREM );
				tab.setValor( rs.getString( "SITRETORNO" ), i, COL_SITRET );
			}
			
			rs.close();
			ps.close();
			
			if ( ! con.getAutoCommit() ) {
				con.commit();
			}
			
			if ( i > 0 ) {
				lbStatus.setText( "     tabela carregada com " + i + " itens..." );	
			}
			else {
				lbStatus.setText( "" );				
			}
			
		} catch ( Exception e ) {
			Funcoes.mensagemErro( this, "Erro ao busca dados!\n" +  e.getMessage() );
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
		if (consisteExporta()) {
			
		}
	}
	
	private boolean consisteExporta() {
		boolean retorno = true;
		Vector vLinha = null;
		for (int i=0; i<tab.getNumLinhas(); i++) {
			vLinha = tab.getLinha(i);
			if ( (((Boolean) vLinha.elementAt(COL_SEL)).booleanValue()) &&  
				 (("".equals((String) vLinha.elementAt(COL_AGENCIACLI))) || 
				  ("".equals((String) vLinha.elementAt(COL_IDENTCLI))))) {
			   if (!completaTabela(i, (Integer) vLinha.elementAt(COL_CODCLI),
					   (String) vLinha.elementAt(COL_RAZCLI),
					   (String) vLinha.elementAt(COL_AGENCIACLI),
					   (String) vLinha.elementAt(COL_IDENTCLI) )) {
				   break;
			   }
			}
		}
		return retorno;
	}
	
	private boolean completaTabela(int linha, Integer codCli, String razCli, String agenciaCli, String identCli) {
		boolean retorno = true;
		Object[] valores = DLIdentCli.execIdentCli( this, codCli, razCli, agenciaCli, identCli );
		retorno = ((Boolean) valores[0]).booleanValue(); 
		if ( retorno ) {
			ajustaClientes(codCli,(String) valores[1], (String) valores[2] );
		} else {
			tab.setValor( new Boolean(false), linha, COL_SEL );
		}
		return retorno;
	}
	
	public void ajustaClientes(Integer codCli, String agenciaCli, String identCli) {
		for (int i=0; i<tab.getNumLinhas(); i++) {
		   if ( (((Boolean) tab.getValor( i, COL_SEL )).booleanValue()) && 
				(codCli.equals( (Integer) tab.getValor( i, COL_CODCLI ))) ) {
			   tab.setValor( agenciaCli, i, COL_AGENCIACLI );
			   tab.setValor( identCli, i, COL_IDENTCLI );
		   }
		}
	}
	
	public void setConexao( Connection cn ) {

		super.setConexao( cn );
		lcBanco.setConexao( cn );
	}

}
