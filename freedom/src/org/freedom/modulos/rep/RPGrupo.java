/**
 * @version 02/2007 <BR>
 * @author Setpoint Informática Ltda.<BR>
 * @author Alex Rodrigues
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.rep <BR>
 * Classe:
 * @(#)RPGrupo.java <BR>
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
 * Tela de cadastro de grupo e sub-grupo de produtos.
 * 
 */

package org.freedom.modulos.rep;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JScrollPane;

import org.freedom.bmps.Icone;
import org.freedom.componentes.JPanelPad;
import org.freedom.componentes.Tabela;
import org.freedom.telas.FFilho;

public class RPGrupo extends FFilho implements ActionListener, MouseListener, KeyListener {

	private static final long serialVersionUID = 1L;
	
	private final JPanelPad panelGrupos = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );
	
	private final JPanelPad panelRodape = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );
	
	private final JPanelPad panelBotoes = new JPanelPad( JPanelPad.TP_JPANEL, new FlowLayout( FlowLayout.CENTER, 6, 4 ) );
	
	private final JPanelPad panelSair = new JPanelPad( JPanelPad.TP_JPANEL, new FlowLayout( FlowLayout.CENTER, 6, 4 ) );

	private final JButton btSair = new JButton( "Sair", Icone.novo( "btSair.gif" ) );

	private final JButton btGrupo = new JButton( "Grupo", Icone.novo( "btNovo.gif" ) );

	private final JButton btSubGrupo = new JButton( "Sub-Grupo", Icone.novo( "btNovo.gif" ) );

	private final Tabela tab = new Tabela();

	public RPGrupo() {

		super( false );
		setTitulo( "Cadastro de Grupos e Sub-Grupos" );
		setAtribos( 50, 50, 500, 350 );

		montaTela();

		tab.adicColuna( "Cód.grupo" );
		tab.adicColuna( "Descrição do grupo" );
		tab.adicColuna( "Sigla" );

		tab.setTamColuna( 120, 0 );
		tab.setTamColuna( 280, 1 );
		tab.setTamColuna( 83, 2 );

		btSair.addActionListener( this );
		btGrupo.addActionListener( this );
		btSubGrupo.addActionListener( this );
		tab.addMouseListener( this );
		tab.addKeyListener( this );

	}
	
	private void montaTela() {

		getContentPane().setLayout( new BorderLayout() );

		
		panelGrupos.setBorder( BorderFactory.createEtchedBorder() );
		
		panelGrupos.add( new JScrollPane( tab ), BorderLayout.CENTER );
		
		
		btGrupo.setPreferredSize( new Dimension( 150, 30 ) );
		btSubGrupo.setPreferredSize( new Dimension( 150, 30 ) );
		btSair.setPreferredSize( new Dimension( 100, 30 ) );
		
		panelRodape.setPreferredSize( new Dimension( 100, 44 ) );
		panelRodape.setBorder( BorderFactory.createEtchedBorder() );
		
		panelBotoes.add( btGrupo );
		panelBotoes.add( btSubGrupo );
		
		panelSair.add( btSair );
		
		panelRodape.add( panelBotoes, BorderLayout.WEST );
		panelRodape.add( panelSair, BorderLayout.EAST );
		
		getContentPane().add( panelGrupos, BorderLayout.CENTER );
		getContentPane().add( panelRodape, BorderLayout.SOUTH );
	}

	private void montaTab() {

		/*String sSQL = "SELECT CODGRUP,DESCGRUP,SIGLAGRUP,ESTNEGGRUP,ESTLOTNEGGRUP FROM EQGRUPO WHERE " + "CODEMP=? AND CODFILIAL =? ORDER BY CODGRUP";
		PreparedStatement ps = null;
		ResultSet rs = null;
		tab.limpa();
		try {
			ps = con.prepareStatement( sSQL );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "EQGRUPO" ) );
			rs = ps.executeQuery();

			for ( int i = 0; rs.next(); i++ ) {
				tab.adicLinha();
				tab.setValor( rs.getString( "CodGrup" ), i, 0 );
				tab.setValor( rs.getString( "DescGrup" ), i, 1 );
				tab.setValor( rs.getString( "SiglaGrup" ), i, 2 );
				tab.setValor( rs.getString( "ESTNEGGRUP" ), i, 3 );
				tab.setValor( rs.getString( "ESTLOTNEGGRUP" ), i, 4 );
			}

			if ( !con.getAutoCommit() )
				con.commit();
		} catch ( SQLException err ) {
			Funcoes.mensagemErro( this, "Erro ao consultar a tabela EQGRUPO! ! !\n" + err.getMessage(), true, con, err );
		}*/
	}

	public void actionPerformed( ActionEvent evt ) {

		if ( evt.getSource() == btSair ) {
			dispose();
		}/*
		else if ( evt.getSource() == btGrupo ) {
			gravaNovoGrup();
			montaTab();
		}
		else if ( evt.getSource() == btSubGrupo ) {
			gravaNovoSubGrup();
			montaTab();
		}*/
	}

	public void keyPressed( KeyEvent kevt ) {

		/*if ( ( kevt.getKeyCode() == KeyEvent.VK_DELETE ) & ( kevt.getSource() == tab ) ) {
			deletar();
			montaTab();
		}*/
	}

	public void keyTyped( KeyEvent kevt ) { }

	public void keyReleased( KeyEvent kevt ) { }

	public void mouseEntered( MouseEvent mevt ) { }

	public void mousePressed( MouseEvent mevt ) { }

	public void mouseClicked( MouseEvent mevt ) {

		/*if ( ( mevt.getSource() == tab ) & ( mevt.getClickCount() == 2 ) & ( tab.getLinhaSel() >= 0 ) ) {
			if ( ( "" + tab.getValor( tab.getLinhaSel(), 0 ) ).trim().length() > 4 ) {
				editaSubGrup();
				montaTab();
			}
			else {
				editaGrup();
				montaTab();
			}
		}*/
	}

	public void mouseExited( MouseEvent mevt ) { }

	public void mouseReleased( MouseEvent mevt ) { }

	public void setConexao( Connection cn ) {
	
		super.setConexao( cn );
		montaTab();
	}
}
