package org.freedom.modulos.pcp;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JScrollPane;

import org.freedom.acao.RadioGroupEvent;
import org.freedom.acao.RadioGroupListener;
import org.freedom.componentes.JCheckBoxPad;
import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JPanelPad;
import org.freedom.componentes.JRadioGroup;
import org.freedom.componentes.JTextAreaPad;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FFDialogo;


public class DLAcaoCorretiva extends FFDialogo implements RadioGroupListener {

	private static final long serialVersionUID = 1L;
	
	private final JPanelPad panelAnalises = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );
	
	private final JPanelPad panelItensAnalises = new JPanelPad();
	
	private final JPanelPad panelCausa = new JPanelPad( new GridLayout( 1, 1 ) );
	
	private final JPanelPad panelAcao = new JPanelPad( new GridLayout( 1, 1 ) );
	
	private final JPanelPad panelBotoes = new JPanelPad( new FlowLayout( FlowLayout.CENTER, 10, 10 ) );

	private final JTextFieldPad txtCodOP = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private final JTextFieldPad txtSeqOP = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private final JTextFieldPad txtCodProdEst = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private final JTextFieldPad txtRefProdEst = new JTextFieldPad( JTextFieldPad.TP_STRING, 13, 0 );

	private final JTextFieldPad txtSeqEst = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 5, 0 );

	private final JTextFieldPad txtDescEst = new JTextFieldPad( JTextFieldPad.TP_STRING, 50, 0 );
	
	private final JRadioGroup<String, String> rgSolucao;
	
	private final JTextAreaPad txaCausa = new JTextAreaPad();
	
	private final JTextAreaPad txaAcao = new JTextAreaPad();
	
	private final JButton btInclusao = new JButton( "Inclusão" );
	
	private final JButton btDescarte = new JButton( "Descarte" );
	
	private final ListaCampos lcAcaoCorretiva = new ListaCampos( this );
	
	private boolean bPref = false;
	
	private HashMap<Integer, JCheckBoxPad> analises = new HashMap<Integer, JCheckBoxPad>();
	

	public DLAcaoCorretiva( Connection con, EMs m, Object[] keys ) {
		
		setTitulo( "Acão corretiva - " + m.getDescription() );
		setAtribos( 635, 555 );
		setConexao( con );
		
		txtCodOP.setVlrInteger( (Integer) keys[ 0 ] );
		txtSeqOP.setVlrInteger( (Integer) keys[ 1 ] );
		txtCodProdEst.setVlrInteger( (Integer) keys[ 2 ] );
		txtRefProdEst.setVlrString( (String) keys[ 3 ] );
		txtSeqEst.setVlrInteger( (Integer) keys[ 4 ] );
		txtDescEst.setVlrString( (String) keys[ 5 ] );
		
		Vector<String> vLabs = new Vector<String>();
		vLabs.add( "Inclusão de Insumos" );
		vLabs.add( "Descarte" );
		Vector<String> vVals = new Vector<String>();
		vVals.add( "II" );
		vVals.add( "DT" );
		rgSolucao = new JRadioGroup<String, String>( 1, 2, vLabs, vVals );
		
		bPref = getUsaRef();
		
		montaAnalises();
		montaTela();
		
		rgSolucao.addRadioGroupListener( this );
		
		rgSolucao.setVlrString( "II" );
	}
	
	private void montaTela() {		
		
		txtCodOP.setAtivo( false );
		txtSeqOP.setAtivo( false );
		txtCodProdEst.setAtivo( false );
		txtRefProdEst.setAtivo( false );
		txtSeqEst.setAtivo( false );
		txtDescEst.setAtivo( false );
		
		adic( new JLabelPad( "Nº.OP." ), 7, 10, 80, 20 );
		adic( txtCodOP, 7, 30, 80, 20 );

		adic( new JLabelPad( "Seq.OP." ), 90, 10, 80, 20 );
		adic( txtSeqOP, 90, 30, 80, 20 );

		if ( bPref ) {
			adic( new JLabelPad( "Referência" ), 173, 10, 80, 20 );
			adic( txtRefProdEst, 173, 30, 80, 20 );
		}
		else {
			adic( new JLabelPad( "Cód.prod." ), 173, 10, 80, 20 );
			adic( txtCodProdEst, 173, 30, 80, 20 );
		}

		adic( new JLabelPad( "Seq.Est." ), 256, 10, 80, 20 );
		adic( txtSeqEst, 256, 30, 80, 20 );

		adic( new JLabelPad( "Descrição da estrutura principal" ), 339, 10, 330, 20 );
		adic( txtDescEst, 339, 30, 270, 20 );		
		
		panelAnalises.setBorder( BorderFactory.createTitledBorder( "Analises" ) );
		panelAnalises.add( new JScrollPane( panelItensAnalises ), BorderLayout.CENTER );
		panelItensAnalises.tiraBorda();
		adic( panelAnalises, 7, 60, 602, 120 );
				
		adic( new JLabelPad( "Solução" ), 17, 180, 100, 20 );
		adic( rgSolucao, 10, 200, 596, 30 );
		
		panelCausa.setBorder( BorderFactory.createTitledBorder( "Causas" ) );
		panelCausa.add( new JScrollPane( txaCausa ) );
		adic( panelCausa, 7, 235, 602, 110 );
		
		panelAcao.setBorder( BorderFactory.createTitledBorder( "Ações corretivas" ) );
		panelAcao.add( new JScrollPane( txaAcao ) );
		adic( panelAcao, 7, 350, 602, 110 );
				
		btInclusao.setPreferredSize( new Dimension( 100, 30 ) );
		btDescarte.setPreferredSize( new Dimension( 100, 30 ) );

		panelBotoes.add( btInclusao );
		panelBotoes.add( btDescarte );
		
		pnRodape.add( panelBotoes, BorderLayout.WEST );
		
	}
	
	private void montaAnalises() {
		
		carregaAnalises();
		
		int y = 5;
		for ( JCheckBoxPad cb : analises.values() ) {
			panelItensAnalises.adic( cb, 10, y, 500, 20 );
			y+=20;
		}		
		
		panelItensAnalises.setPreferredSize( new Dimension( 500, y+5 ) );
	}
	
	private void carregaAnalises() {
		
		try {

			StringBuilder sql = new StringBuilder();
			sql.append( "SELECT C.SEQOPCQ, T.DESCTPANALISE " );
			sql.append( "FROM" );
			sql.append( "  PPOPCQ C, PPTIPOANALISE T, PPESTRUANALISE E " );
			sql.append( "WHERE" );
			sql.append( "  C.CODEMP=? AND C.CODFILIAL=? AND C.CODOP=? AND C.SEQOP=? AND" );
			sql.append( "  E.CODEMP=C.CODEMPEA AND E.CODFILIAL=C.CODFILIALEA AND E.CODESTANALISE=C.CODESTANALISE AND" );
			sql.append( "  T.CODEMP=E.CODEMPTA AND T.CODFILIAL=E.CODFILIALTA AND T.CODTPANALISE=E.CODTPANALISE AND" );
			sql.append( "  C.STATUS='RC'" );
			
			PreparedStatement ps = con.prepareStatement( sql.toString() );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "PPOPCQ" ) );
			ps.setInt( 3, txtCodOP.getVlrInteger() );
			ps.setInt( 4, txtSeqOP.getVlrInteger() );

			ResultSet rs = ps.executeQuery();

			while ( rs.next() ) {
				analises.put( rs.getInt( "SEQOPCQ" ), new JCheckBoxPad( rs.getString( "DESCTPANALISE" ), "S", "N" ) );
			}

			rs.close();
			ps.close();

			if ( !con.getAutoCommit() ) {
				con.commit();
			}
		} catch ( SQLException err ) {
			err.printStackTrace();
			Funcoes.mensagemErro( this, "Erro ao carregar analises!\n" + err.getMessage(), true, con, err );
		}
	}
	
	private boolean getUsaRef() {
		
		boolean usarRef = false;
		try {

			PreparedStatement ps = con.prepareStatement( "SELECT P1.USAREFPROD FROM SGPREFERE1 P1 WHERE P1.CODEMP=? AND P1.CODFILIAL=?" );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "SGPREFERE1" ) );

			ResultSet rs = ps.executeQuery();

			if ( rs.next() ) {
				usarRef = "S".equals( rs.getString( "USAREFPROD" ) );
			}

			rs.close();
			ps.close();

			if ( !con.getAutoCommit() ) {
				con.commit();
			}
		} catch ( SQLException err ) {
			err.printStackTrace();
			Funcoes.mensagemErro( this, "Erro ao carregar a tabela PREFERE1!\n" + err.getMessage(), true, con, err );
		}

		return usarRef;
	}
	
	public void valorAlterado( RadioGroupEvent e ) {

		if ( e.getSource() == rgSolucao ) {
			if ( "II".equals( rgSolucao.getVlrString() ) ) {
				btInclusao.setEnabled( true );
				btDescarte.setEnabled( false );
			} else {
				btInclusao.setEnabled( false );
				btDescarte.setEnabled( true );
			}
		}		
	}

	enum EMs {		
		
		MATERIAIS( "Materiais" ), 
		MAQUINA( "Máquinas" ), 
		METODO( "Métodos" ), 
		MEIO_AMBIENTE( "Meio Ambiente" ), 
		MAO_DE_OBRA( "Mão-de-obra" ), 
		MEDIDA( "Medidas" );	
		
		private String description;
		
		EMs( String description ) {
			this.description = description;
		}
		
		public String getDescription() {
			return this.description;
		}
	};
}
