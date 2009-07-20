/**
 * @version 05/12/2008 <BR>
 * @author Setpoint Informática Ltda.
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)FCancCompra.java <BR>
 * 
 * Este arquivo é parte do sistema Freedom-ERP, o Freedom-ERP é um software livre; você pode redistribui-lo e/ou <BR>
 * modifica-lo dentro dos termos da Licença Pública Geral GNU como publicada pela Fundação do Software Livre (FSF); <BR>
 * na versão 2 da Licença, ou (na sua opnião) qualquer versão. <BR>
 * Este programa é distribuido na esperança que possa ser  util, mas SEM NENHUMA GARANTIA; <BR>
 * sem uma garantia implicita de ADEQUAÇÂO a qualquer MERCADO ou APLICAÇÃO EM PARTICULAR. <BR>
 * Veja a Licença Pública Geral GNU para maiores detalhes. <BR>
 * Você deve ter recebido uma cópia da Licença Pública Geral GNU junto com este programa, se não, <BR>
 * escreva para a Fundação do Software Livre(FSF) Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA <BR> <BR>
 *
 * Comentários sobre a classe...
 */

package org.freedom.modulos.std;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.freedom.infra.model.jdbc.DbConnection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import org.freedom.bmps.Icone;
import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JPanelPad;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FFilho;

public class FCancCompra extends FFilho implements ActionListener{

	private static final long serialVersionUID = 1L;
	
	private JTextFieldPad txtCodCompra = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	
	private JTextFieldPad txtDocCompra = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	
	private JTextFieldPad txtVlrLiqCompra = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 15, Aplicativo.casasDec );
	
	private JTextFieldPad txtStatusCompra = new JTextFieldPad( JTextFieldPad.TP_STRING, 4, 0 );	
	
	private JButton btCancelar = new JButton( "Cancelar", Icone.novo( "btCancelar.gif" ));
	
	private JButton btSair = new JButton( "Sair", Icone.novo( "btSair.gif" ));
	
	private JPanelPad pinCli = new JPanelPad( 350, 100 );
	
	private JPanelPad pnRod = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );
	
	private ListaCampos lcCompra = new ListaCampos(this);
	
	public FCancCompra() {

		super( false );
		setTitulo( "Cancelamento" );
		setAtribos( 50, 50, 340, 170 );
		
		Funcoes.setBordReq( txtCodCompra );
		txtDocCompra.setAtivo( false );
		txtVlrLiqCompra.setAtivo( false );
		
		montaTela();
		montaListaCampos();
	}
	
	private void montaTela(){
		
		Container c = getContentPane();
		c.setLayout( new BorderLayout() );
		
		btSair.setPreferredSize( new Dimension( 100,30 ));
		
		pnRod.setPreferredSize( new Dimension( 350,30 ));
		pnRod.add( btSair,BorderLayout.EAST );
		
		c.add( pnRod,BorderLayout.SOUTH );
		c.add( pinCli,BorderLayout.CENTER );
		
		btCancelar.setToolTipText( "Cancela Venda" );
		
		pinCli.adic( new JLabelPad( "Nº Compra" ), 7, 0, 80, 20 );
		pinCli.adic( txtCodCompra, 7, 20, 80, 20 );
		pinCli.adic( new JLabelPad( "Doc.Compra" ), 90, 0, 80, 20 );
		pinCli.adic( txtDocCompra, 90, 20, 80, 20 );
		pinCli.adic( new JLabelPad( "Vlr.liq.Compra" ), 173, 0, 80, 20 );
		pinCli.adic( txtVlrLiqCompra, 173, 20, 100, 20 );
		pinCli.adic( btCancelar, 7, 50, 130, 30 );
		
		btSair.addActionListener( this );
		btCancelar.addActionListener( this );
		
	}
	
	private void montaListaCampos(){
		
		lcCompra.add(new GuardaCampo( txtCodCompra,  "CodCompra", "Cód.Compra", ListaCampos.DB_PK, null, false ));
		lcCompra.add(new GuardaCampo( txtDocCompra, "DocCompra", "Documento", ListaCampos.DB_SI, null, false ));
		lcCompra.add(new GuardaCampo( txtVlrLiqCompra, "VlrLiqCompra", "Vlr.Liq.Compra", ListaCampos.DB_SI, null, false ));
	    lcCompra.add(new GuardaCampo( txtStatusCompra, "StatusCompra", "Status", ListaCampos.DB_SI, false ));
		lcCompra.montaSql( false, "COMPRA", "CP");
		lcCompra.setReadOnly( true );
		txtCodCompra.setTabelaExterna( lcCompra );
		txtCodCompra.setFK( true );
		txtCodCompra.setNomeCampo( "CodCompra" );
	}

	public void setConexao( DbConnection cn ){
	
		super.setConexao( cn );
		lcCompra.setConexao( cn );
		
	}

	private void cancelar( int codCompra, String sStatus ){
		
		PreparedStatement ps = null;
		StringBuffer sSQL = new StringBuffer();
	
		if ( codCompra == 0 ) {
			
			Funcoes.mensagemInforma( null, "Nenhuma Compra foi selecionada!" );
			txtCodCompra.requestFocus();
		}
		
		else if ( sStatus.substring( 0, 1 ).equals( "C" )){
		
			Funcoes.mensagemInforma( null, "Compra ja foi cancelada!" );
			txtCodCompra.requestFocus();
		}
		
		else{
			if (Funcoes.mensagemConfirma( null, "Deseja realmente cancelar esta compra?" ) == JOptionPane.YES_OPTION ) {
				
				sSQL.append( "UPDATE CPCOMPRA SET STATUSCOMPRA = 'X" + sStatus.substring(0,1) + "' ");
				sSQL.append( "WHERE CODEMP=? AND CODFILIAL=? AND CODCOMPRA=?" );			
				
				try {
					
					ps = con.prepareStatement( sSQL.toString() );
					ps.setInt( 1, Aplicativo.iCodEmp );
					ps.setInt( 2, ListaCampos.getMasterFilial( "CPCOMPRA" ) );
					ps.setInt( 3, codCompra );
					ps.executeUpdate();					
					ps.close();
					
					con.commit();
					
					Funcoes.mensagemInforma( this, "A Compra " + txtCodCompra.getVlrInteger() + " foi cancelada com sucesso!" );
					
				} catch ( SQLException err ) {					
					err.printStackTrace();
					Funcoes.mensagemErro( this, "Erro ao cancelar compra! " + err.getMessage() );
				}
			}
		}
	}	
	
	public void actionPerformed( ActionEvent e ) {

		if ( e.getSource() == btSair ){			
			dispose();
		}		
		else if ( e.getSource() == btCancelar ){
			cancelar( txtCodCompra.getVlrInteger(), txtStatusCompra.getVlrString() );
		}
	}
}
