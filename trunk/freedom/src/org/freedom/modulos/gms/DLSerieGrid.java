/*
 * Projeto: Freedom
 * Pacote: org.freedom.modules.std
 * Classe: @(#)DLBuscaVenda.java
 * 
 * Este arquivo é parte do sistema Freedom-ERP, o Freedom-ERP é um software livre; você pode redistribui-lo e/ou <BR>
 * modifica-lo dentro dos termos da Licença Pública Geral GNU como publicada pela Fundação do Software Livre (FSF); <BR>
 * na versão 2 da Licença, ou (na sua opnião) qualquer versão. <BR>
 * Este programa é distribuido na esperança que possa ser  util, mas SEM NENHUMA GARANTIA; <BR>
 * sem uma garantia implicita de ADEQUAÇÂO a qualquer MERCADO ou APLICAÇÃO EM PARTICULAR. <BR>
 * Veja a Licença Pública Geral GNU para maiores detalhes. <BR>
 * Você deve ter recebido uma cópia da Licença Pública Geral GNU junto com este programa, se não, <BR>
 * escreva para a Fundação do Software Livre(FSF) Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA <BR> <BR>
 */

package org.freedom.modulos.gms;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;

import org.freedom.acao.TabelaSelEvent;
import org.freedom.acao.TabelaSelListener;
import org.freedom.componentes.JPanelPad;
import org.freedom.componentes.Tabela;
import org.freedom.funcoes.Funcoes;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.telas.FFDialogo;


/**
 * Tela para registro dos números de série na compra.
 * 
 * @author Setpoint Informática Ltda./Anderson Sanchez
 * @version 23/03/2010
 */
public class DLSerieGrid extends FFDialogo implements MouseListener, TabelaSelListener {

	private static final long serialVersionUID = 1L;

	private JPanelPad panelGeral = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private JPanelPad panelMaster = new JPanelPad( 700, 60 );

	private JPanelPad panelGrid = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );
	
	private Tabela tabItens = new Tabela();
	
	private Integer codemp;
	
	private Integer codfilial;
	
	private Integer codcompra;
	
	private Integer coditcompra;
	
	private Integer codemppd;
	
	private Integer codfilialpd;
	
	private Integer codprod;
	
	private String descprod;
			
	private enum ITENS {
		SEQITSERIE, NUMSERIE, STATUS, DTFABRICSERIE, DTVALIDSERIE;
	}
	
	
	public DLSerieGrid( ) {

		super();
		
		setAtribos( 520, 320 );
		setResizable( true );
		montaListeneres();
				
		montaTela();		

	}

	
	private void montaTela() {
		
		setPanel( panelGeral );
		
//		panelGeral.add( panelMaster, BorderLayout.SOUTH );
		
		// ***** Grid
		
		panelGeral.add( panelGrid, BorderLayout.CENTER );
		panelGrid.setBorder( BorderFactory.createEtchedBorder() );
				
		tabItens.setRowHeight( 21 );
		
		tabItens.adicColuna( "Seq." );
		tabItens.adicColuna( "Número de série" );
		tabItens.adicColuna( "" );
		tabItens.adicColuna( "Dt.Fabricação" );
		tabItens.adicColuna( "Dt.Validade" );
		
		tabItens.setTamColuna( 30, ITENS.SEQITSERIE.ordinal() );
		tabItens.setTamColuna( 270, ITENS.NUMSERIE.ordinal() );
		tabItens.setTamColuna( 20, ITENS.STATUS.ordinal() );
		tabItens.setTamColuna( 80, ITENS.DTFABRICSERIE.ordinal() );
		tabItens.setTamColuna( 80, ITENS.DTVALIDSERIE.ordinal() );
	
//		tabItens.setColunaEditavel( ITENS.NUMSERIE.ordinal(), true );
//		tabItens.setColunaEditavel( ITENS.DTFABRICSERIE.ordinal(), true );
//		tabItens.setColunaEditavel( ITENS.DTVALIDSERIE.ordinal(), true );
		
		panelGrid.add( new JScrollPane( tabItens ), BorderLayout.CENTER );
		

	}
	
	private void carregaSeries() {
		
		try {
			
			StringBuilder sql = new StringBuilder();
			sql.append( "select cs.seqitserie, se.numserie, se.dtfabricserie, se.dtvalidserie " );
			sql.append( "from cpitcompraserie cs left outer join eqserie se on ");
			sql.append( "se.codemp=cs.codemppd and se.codfilial=cs.codfilialpd and se.codprod=cs.codprod and se.numserie=cs.numserie " );
			sql.append( "where cs.codemp=? and cs.codfilial=? and cs.codcompra=? and cs.coditcompra=? " );
			sql.append( "order by cs.codcompra, cs.coditcompra, cs.seqitserie " );
			
			PreparedStatement ps = con.prepareStatement( sql.toString() );
			
			ps.setInt( 1, getCodemp() );
			ps.setInt( 2, getCodfilial() );
			ps.setInt( 3, getCodcompra() );
			ps.setInt( 4, getCoditcompra() );

			ResultSet rs = ps.executeQuery();
			
			tabItens.limpa();
			
			int row = 0;
			while ( rs.next() ) {
				
				tabItens.adicLinha();
				tabItens.setValor( rs.getInt( ITENS.SEQITSERIE.toString() ), row, ITENS.SEQITSERIE.ordinal() );
				tabItens.setValor( rs.getString( ITENS.NUMSERIE.toString() ), row, ITENS.NUMSERIE.ordinal() );
				tabItens.setValor( Funcoes.sqlDateToStrDate( rs.getDate( ITENS.DTFABRICSERIE.toString() )), row, ITENS.DTFABRICSERIE.ordinal() );
				tabItens.setValor( Funcoes.sqlDateToStrDate( rs.getDate( ITENS.DTVALIDSERIE.toString() )), row, ITENS.DTVALIDSERIE.ordinal() );
					
				row++;
			}
			
			con.commit();
			
		} catch ( SQLException e ) {
			e.printStackTrace();
			Funcoes.mensagemErro( this, "Erro ao buscar nota de remessa.\n" + e.getMessage(), true, con, e );
		}
	}

	@Override
	public void actionPerformed( ActionEvent e ) { 

/*		if ( e.getSource() == btBuscar ) {
			carregaSeries();
		}
		else {*/
			super.actionPerformed( e );
		//}
	}

	public void keyPressed( KeyEvent e ) {

		if ( e.getSource() == tabItens && e.getKeyCode() == KeyEvent.VK_ENTER ) {
			
			if ( tabItens.getNumLinhas() > 0 && btOK.isEnabled() ) {
				btOK.doClick();
			}
			else if ( !btOK.isEnabled() ) {
				if ( tabItens.getLinhaSel() == tabItens.getNumLinhas() - 1 ) {
					tabItens.setLinhaSel( tabItens.getNumLinhas() - 2 );
				}
				else {
					tabItens.setLinhaSel( tabItens.getLinhaSel() - 1 );
				}
			}
		}
		else if ( e.getKeyCode() == KeyEvent.VK_ESCAPE ) {
			btCancel.doClick();
		}
	}

	public Integer getCodemp() {
		
		return codemp;
	}

	
	public void setCodemp( Integer codemp ) {
	
		this.codemp = codemp;
	}

	
	public Integer getCodfilial() {
	
		return codfilial;
	}

	
	public void setCodfilial( Integer codfilial ) {
	
		this.codfilial = codfilial;
	}

	
	public Integer getCodcompra() {
	
		return codcompra;
	}

	
	public void setCodcompra( Integer codcompra ) {
	
		this.codcompra = codcompra;
	}

	
	public Integer getCoditcompra() {
	
		return coditcompra;
	}

	
	public void setCoditcompra( Integer coditcompra ) {
	
		this.coditcompra = coditcompra;
	}
	
	
	
	
	public Integer getCodemppd() {
	
		return codemppd;
	}


	
	public void setCodemppd( Integer codemppd ) {
	
		this.codemppd = codemppd;
	}
	
	public Integer getCodfilialpd() {
	
		return codfilialpd;
	}


	
	public void setCodfilialpd( Integer codfilialpd ) {
	
		this.codfilialpd = codfilialpd;
	}


	
	public Integer getCodprod() {
	
		return codprod;
	}


	
	public void setCodprod( Integer codprod ) {
	
		this.codprod = codprod;
	}

	
	
	
	public String getDescprod() {
	
		return descprod;
	}


	
	public void setDescprod( String descprod ) {
	
		this.descprod = descprod;
	}


	public void ok() {
		
		atualizaCpItCompraSerie();	
				
		super.ok();
	}
	
	private void atualizaCpItCompraSerie() {
		StringBuilder sql = new StringBuilder();
		PreparedStatement ps = null;
		
		try {
			sql.append( "update cpitcompraserie set codemppd=?, codfilialpd=?, codprod=?, numserie=? " );
			sql.append( "where codemp=? and codfilial=? and codcompra=? and coditcompra=? and seqitserie=? " );
			
			int i = 0;
			
			while (i < tabItens.getNumLinhas()) {
				
				ps = con.prepareStatement( sql.toString() );
				
				if( "".equals( (String) tabItens.getValor( i, ITENS.NUMSERIE.ordinal() ) )) {
					i++;
					return;
				}
				
				ps.setInt( 1, getCodemppd() );
				ps.setInt( 2, getCodfilialpd() );
				ps.setInt( 3, getCodprod());
				
				ps.setString( 4, (String) tabItens.getValor( i, ITENS.NUMSERIE.ordinal() ) );
				
				ps.setInt( 5, getCodemp() );
				ps.setInt( 6, getCodfilial() );
				ps.setInt( 7, getCodcompra() );
				ps.setInt( 8, getCoditcompra() );
				ps.setInt( 9, (Integer) tabItens.getValor( i, ITENS.SEQITSERIE.ordinal() ) );
					
				ps.executeUpdate();
				
				i++;				
			}
			
			con.commit();
			 
		}				
		catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
    public void setConexao(DbConnection cn) {
    	
    	super.setConexao( cn );
    	
    	setTitulo( "Números de série para o produto " + getDescprod() + " desta compra." );
    	carregaSeries();
    	
    }
    
	
	public void mouseClicked( MouseEvent mevt ) {
		Tabela tabEv = (Tabela) mevt.getSource();
		
		if ( mevt.getClickCount() == 2 ) {					
			if( tabEv == tabItens && tabEv.getLinhaSel() > -1 ) {
				
				abreDLSerie();
				
			}
		}				
	}
	
	private void montaListeneres() {
		
		tabItens.addTabelaSelListener( this );	
		tabItens.addMouseListener( this );	

	}

	private void abreDLSerie() {
		DLSerie dl = new DLSerie( this, null, getCodprod(), getDescprod(), con ); 
		
		if( ! "".equals( (String) tabItens.getValor( tabItens.getLinhaSel(), ITENS.NUMSERIE.ordinal() )) ) {
			dl.setNumSerie( (String) tabItens.getValor( tabItens.getLinhaSel(), ITENS.NUMSERIE.ordinal() ) );
			dl.carregaSerie();
		}
		
		dl.setVisible( true );
		
		if ( dl.OK ) {
			
			tabItens.setValor( dl.getNumSerie(), tabItens.getLinhaSel(), ITENS.NUMSERIE.ordinal() );
			tabItens.setValor( Funcoes.dateToStrDate( dl.getDtFabricSerie()), tabItens.getLinhaSel(), ITENS.DTFABRICSERIE.ordinal() );
			tabItens.setValor( Funcoes.dateToStrDate( dl.getDtValidSerie()), tabItens.getLinhaSel(), ITENS.DTVALIDSERIE.ordinal() );
			
		}
		dl.dispose();

	}
	
	public void mouseEntered( MouseEvent arg0 ) {

		// TODO Auto-generated method stub
		
	}


	public void mouseExited( MouseEvent arg0 ) {

		// TODO Auto-generated method stub
		
	}


	public void mousePressed( MouseEvent arg0 ) {

		// TODO Auto-generated method stub
		
	}


	public void mouseReleased( MouseEvent arg0 ) {

		// TODO Auto-generated method stub
		
	}


	public void valorAlterado( TabelaSelEvent evt ) {

		// TODO Auto-generated method stub
		
	}

}
