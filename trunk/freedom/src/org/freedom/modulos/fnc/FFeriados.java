/**
 * @version 25/06/2008 <BR>
 * @author Setpoint Informática Ltda. <BR>
 * @author Reginaldo Garcia Heua
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.fnc <BR>
 * Classe:
 * @(#)FFeriados.java <BR>
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
 */
package org.freedom.modulos.fnc;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import net.sf.nachocalendar.components.DateField;
import net.sf.nachocalendar.tasks.TaskCalendarFactory;

import org.freedom.acao.CarregaEvent;
import org.freedom.acao.CarregaListener;
import org.freedom.acao.InsertEvent;
import org.freedom.acao.InsertListener;
import org.freedom.acao.PostEvent;
import org.freedom.componentes.JButtonPad;
import org.freedom.componentes.JCheckBoxPad;
import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JPanelPad;
import org.freedom.componentes.JTextAreaPad;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.Tabela;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FTabDados;

public class FFeriados extends FTabDados implements ActionListener, MouseListener, KeyListener, InsertListener {


	private enum EcolFeriado{
	
			DATAFER, DESCFER, BANCFER, TRABFER
	};
	
	private static final long serialVersionUID = 1L;
	
	private JPanelPad pnRod = new JPanelPad();
	
	private JPanelPad pnTabela = new JPanelPad( new BorderLayout());
	
	private final JPanelPad panelCampos = new JPanelPad();
	
	private JTextFieldPad txtData = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );
	
	private JTextFieldPad txaDescFer = new JTextFieldPad( JTextFieldPad.TP_STRING, 50, 0 );
	
	//private DateField calendarpanel = TaskCalendarFactory.createDateField();
	
	private Tabela tabData = new Tabela();
	
	private JScrollPane spnData = new JScrollPane( tabData );
	
	private JCheckBoxPad cbBanc = new JCheckBoxPad("Feriado Bancário?", "S", "N" );
	
	private JCheckBoxPad cbTrabFer = new JCheckBoxPad("Feriado Trabalista?", "S", "N" );
	
	public FFeriados() {

		setTitulo( "Cadastro de Feriados" );
		setAtribos( 50, 50, 600, 300 );
		montaTela();
		
		tabData.addMouseListener( this );
		tabData.addKeyListener( this );
		
	}

	private void montaTela() {
		
		
		nav.setAtivo( 1, false );
		
		tabData.adicColuna( "Data Feriado" );
		tabData.adicColuna( "Descrição do Feriado" );
		tabData.adicColuna( "Bancário" );
		tabData.adicColuna( "Trabalista" );
		tabData.setTamColuna( 350, EcolFeriado.DESCFER.ordinal() );
		
		setPainel( panelCampos );
		
		panelCampos.setPreferredSize( new Dimension( 750, 110 ) );
		pnCliente.add( panelCampos, BorderLayout.SOUTH );
		pnTabela.setPreferredSize( new Dimension( 750, 110 ) );
		pnCliente.add( pnTabela, BorderLayout.CENTER );
		pnTabela.add( spnData,  BorderLayout.CENTER );
		
		adicCampo( txtData,7, 20, 80, 20, "DataFer", "Data Feriado", ListaCampos.DB_PK, true );
		adicCampo( txaDescFer, 100, 20, 300, 20,"DescFer","Descrição do feriado", ListaCampos.DB_SI, false );
		adicDB( cbBanc, 410, 15, 200, 20, "BancFer", "", false );
		adicDB( cbTrabFer, 410, 35, 200, 20, "TrabFer", "", false );
		setListaCampos( false, "FERIADO", "SG" );
	
	}
	
	private void montaTab(){
		
		StringBuilder sSQL = new StringBuilder();
		ResultSet rs = null;
		PreparedStatement ps = null;
		
		
		try {
			
			sSQL.append( "SELECT F.DATAFER, F.DESCFER, F.BANCFER, F.TRABFER ");
			sSQL.append( "FROM SGFERIADO F WHERE F.CODEMP=? AND F.CODFILIAL=? " );
			sSQL.append( "ORDER BY F.DATAFER DESC" );
			
			
			ps = con.prepareStatement( sSQL.toString() );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "SGFERIADO" ) );
			rs = ps.executeQuery();
			
			 for (int i=0; rs.next(); i++) {
				 
				 tabData.adicLinha();
				 tabData.setValor( rs.getDate( "DATAFER" ), i, EcolFeriado.DATAFER.ordinal() );
				 tabData.setValor( rs.getString( "DESCFER" ), i, EcolFeriado.DESCFER.ordinal() );
				 tabData.setValor( rs.getString( "BANCFER" ), i, EcolFeriado.BANCFER.ordinal() );
				 tabData.setValor( rs.getString( "TRABFER" ), i, EcolFeriado.TRABFER.ordinal() );
			 }
			 
			 rs.close();	
			 ps.close();
			 
			 if ( !con.getAutoCommit() ) {
				 con.commit();
				
			 }
			 
		} catch ( SQLException err ) {
		
			Funcoes.mensagemErro( this, "Erro ao carregar tabela! " + err.getMessage() );
			err.printStackTrace();
		}
	}
	
	private void editar(){
			
		txtData.setVlrDate( (Date) tabData.getValor( tabData.getLinhaSel(), EcolFeriado.DATAFER.ordinal() ) );
		txaDescFer.setVlrString( (String) tabData.getValor( tabData.getLinhaSel(), EcolFeriado.DESCFER.ordinal() ) );
		cbBanc.setVlrString( (String) tabData.getValor( tabData.getLinhaSel(), EcolFeriado.BANCFER.ordinal() ) );
		cbTrabFer.setVlrString( (String) tabData.getValor( tabData.getLinhaSel(), EcolFeriado.TRABFER.ordinal() ) );
		
	}
	
	private void deleta(){
		
		StringBuilder sSQL = new StringBuilder();
		ResultSet rs = null;
		PreparedStatement ps = null;
		
		if( tabData.getLinhaSel() > -1 ){
			
			try {
				
				sSQL.append( "DELETE FROM SGFERIADO F WHERE F.DATAFER=? AND F.CODEMP=? AND F.CODFILIAL=?" );
				
				ps = con.prepareStatement( sSQL.toString() );
				ps.setDate( 1,  (java.sql.Date) tabData.getValor( tabData.getLinhaSel() , EcolFeriado.DATAFER.ordinal() ) );
				ps.setInt( 2, Aplicativo.iCodEmp );
				ps.setInt( 3, ListaCampos.getMasterFilial( "SGFERIADO" ) );
				ps.executeUpdate();
				
				tabData.delLinha( tabData.getLinhaSel() );
				Funcoes.mensagemInforma( this, "Deletado com sucesso!" );
				
				 
				if ( !con.getAutoCommit() ) {
					con.commit();
					
				}
				
			} catch ( SQLException e ) {
				
				e.printStackTrace();
				Funcoes.mensagemErro( this, "Erro ao deletar registro!" + e.getMessage() );
			}
			
		}else{
			
			Funcoes.mensagemInforma( this, "Selecione uma linha no grid!" );
		}
	}

	public void afterPost( PostEvent pevt ) {
		
		super.afterPost(pevt);
	}
	
	public void beforePost( PostEvent pevt ) {

		super.beforePost(pevt);
		
		if( tabData.getNumLinhas() > 0 ){
			
			tabData.limpa();
			montaTab();
			
		}else{
		
			montaTab();
		}
	}
	
	public void actionPerformed( ActionEvent evt ) {

		super.actionPerformed(evt);
		
	//	if( evt.getSource() == bt ){
			
			if( tabData.getNumLinhas() > 0 ){
			
				tabData.limpa();
				montaTab();
				
			}else{
			
				montaTab();
			}
		}
//	}
	
	public void keyPressed( KeyEvent kevt ) {

		super.keyPressed(kevt);
		
		if( kevt.getKeyCode() == KeyEvent.VK_DELETE ){
			if( kevt.getSource() == tabData ){
				deleta();
			}
		}
	}

	public void setConexao( Connection cn ) {
		super.setConexao( cn );
		montaTab();
	}

	public void mouseClicked( MouseEvent e ) {

		if( e.getSource() == tabData ){
			if( e.getClickCount() == 2 ){
				editar();
			}
		}
	}

	public void mouseEntered( MouseEvent e ) {}

	public void mouseExited( MouseEvent e ) {}

	public void mousePressed( MouseEvent e ) {}

	public void mouseReleased( MouseEvent e ) {}

	public void afterInsert( InsertEvent ievt ) {}

	public void beforeInsert( InsertEvent ievt ) {}
}
