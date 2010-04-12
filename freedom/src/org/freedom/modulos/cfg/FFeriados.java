/**
 * @version 25/06/2008 <BR>
 * @author Setpoint Informática Ltda. <BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.fnc <BR>
 * Classe:
 * @(#)FFeriados.java <BR>
 * 
 * Este arquivo é parte do sistema Freedom-ERP, o Freedom-ERP é um software livre; você pode redistribui-lo e/ou <BR>
 * modifica-lo dentro dos termos da Licença Pública Geral GNU como publicada pela Fundação do Software Livre (FSF); <BR>
 * na versão 2 da Licença, ou (na sua opnião) qualquer versão. <BR>
 * Este programa é distribuido na esperança que possa ser  util, mas SEM NENHUMA GARANTIA; <BR>
 * sem uma garantia implicita de ADEQUAÇÂO a qualquer MERCADO ou APLICAÇÃO EM PARTICULAR. <BR>
 * Veja a Licença Pública Geral GNU para maiores detalhes. <BR>
 * Você deve ter recebido uma cópia da Licença Pública Geral GNU junto com este programa, se não, <BR>
 * de acordo com os termos da LPG-PC <BR>
 * <BR>
 * 
 */
package org.freedom.modulos.cfg;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.JCheckBoxPad;
import org.freedom.library.JPanelPad;
import org.freedom.library.JTextFieldPad;
import org.freedom.library.ListaCampos;
import org.freedom.library.Tabela;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import javax.swing.JScrollPane;
import org.freedom.acao.DeleteEvent;
import org.freedom.acao.DeleteListener;
import org.freedom.acao.InsertEvent;
import org.freedom.acao.InsertListener;
import org.freedom.acao.PostEvent;
import org.freedom.acao.PostListener;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FTabDados;

public class FFeriados extends FTabDados implements PostListener, DeleteListener, MouseListener, InsertListener {


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
		lcCampos.addDeleteListener( this );
		lcCampos.addInsertListener( this ) ;
		
	}

	private void montaTela() {
		
			
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
		int anoatual = Funcoes.getAno( new Date() );
		Date dataini = Funcoes.encodeDate( anoatual, 1, 1 );
		Date datafim = Funcoes.encodeDate( anoatual, 12, 31 );
		
		
		try {
			
			sSQL.append( "SELECT F.DATAFER, F.DESCFER, F.BANCFER, F.TRABFER ");
			sSQL.append( "FROM SGFERIADO F ");
			sSQL.append( "WHERE F.CODEMP=? AND F.CODFILIAL=? AND " );
			sSQL.append( "DATAFER BETWEEN ? AND ? " );
			sSQL.append( "ORDER BY F.DATAFER DESC" );
			
			ps = con.prepareStatement( sSQL.toString() );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "SGFERIADO" ) );
			ps.setDate( 3, Funcoes.dateToSQLDate( dataini ) );
			ps.setDate( 4, Funcoes.dateToSQLDate( datafim ) );
			rs = ps.executeQuery();
			
			tabData.limpa();
			
			 for (int i=0; rs.next(); i++) {
				 
				 tabData.adicLinha();
				 tabData.setValor( rs.getDate( "DATAFER" ), i, EcolFeriado.DATAFER.ordinal() );
				 tabData.setValor( rs.getString( "DESCFER" ), i, EcolFeriado.DESCFER.ordinal() );
				 tabData.setValor( rs.getString( "BANCFER" ), i, EcolFeriado.BANCFER.ordinal() );
				 tabData.setValor( rs.getString( "TRABFER" ), i, EcolFeriado.TRABFER.ordinal() );
			 }
			 
			 rs.close();	
			 ps.close();
			 
			 con.commit();
			 
		} catch ( SQLException err ) {
		
			Funcoes.mensagemErro( this, "Erro ao carregar tabela! " + err.getMessage() );
			err.printStackTrace();
		}
	}
	
	private void editar(){
			
		txtData.setVlrDate( (Date) tabData.getValor( tabData.getLinhaSel(), EcolFeriado.DATAFER.ordinal() ) );
		lcCampos.carregaDados();
		
	}
	
	public void afterPost( PostEvent pevt ) {
		
		super.afterPost(pevt);
		montaTab();
	}
	
	public void beforePost( PostEvent pevt ) {

		super.beforePost(pevt);
		
	}
	
	public void setConexao( DbConnection cn ) {
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

	public void afterDelete( DeleteEvent devt ) {
		if (devt.getListaCampos()==lcCampos) {
			montaTab();
		}
	}

	public void beforeDelete( DeleteEvent devt ) {

		
	}

	public void afterInsert( InsertEvent ievt ) {
       if ( ievt.getListaCampos()==lcCampos ) {
    	  cbBanc.setVlrString( "S" );
    	  cbTrabFer.setVlrString( "S" );
       }
	}

	public void beforeInsert( InsertEvent ievt ) {

		
	}

}
