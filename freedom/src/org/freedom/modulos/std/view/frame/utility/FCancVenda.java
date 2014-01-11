/**
 * @version 14/07/2003 <BR>
 * @author Setpoint Informática Ltda./Fernando Oliveira da Silva <BR>
 * 
 *         Projeto: Freedom <BR>
 * 
 *         Pacote: org.freedom.modulos.std <BR>
 *         Classe: @(#)FCancVenda.java <BR>
 * 
 *         Este arquivo é parte do sistema Freedom-ERP, o Freedom-ERP é um software livre; você pode redistribui-lo e/ou <BR>
 *         modifica-lo dentro dos termos da Licença Pública Geral GNU como publicada pela Fundação do Software Livre (FSF); <BR>
 *         na versão 2 da Licença, ou (na sua opnião) qualquer versão. <BR>
 *         Este programa é distribuido na esperança que possa ser util, mas SEM NENHUMA GARANTIA; <BR>
 *         sem uma garantia implicita de ADEQUAÇÂO a qualquer MERCADO ou APLICAÇÃO EM PARTICULAR. <BR>
 *         Veja a Licença Pública Geral GNU para maiores detalhes. <BR>
 *         Você deve ter recebido uma cópia da Licença Pública Geral GNU junto com este programa, se não, <BR>
 *         escreva para a Fundação do Software Livre(FSF) Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA <BR>
 * <BR>
 * 
 *         Comentários sobre a classe...
 */

package org.freedom.modulos.std.view.frame.utility;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

import org.freedom.bmps.Icone;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.functions.Funcoes;
import org.freedom.library.persistence.GuardaCampo;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.component.JButtonPad;
import org.freedom.library.swing.component.JCheckBoxPad;
import org.freedom.library.swing.component.JLabelPad;
import org.freedom.library.swing.component.JPanelPad;
import org.freedom.library.swing.component.JTextAreaPad;
import org.freedom.library.swing.component.JTextFieldFK;
import org.freedom.library.swing.component.JTextFieldPad;
import org.freedom.library.swing.frame.Aplicativo;
import org.freedom.library.swing.frame.FFilho;
import org.freedom.modules.nfe.bean.FreedomNFEKey;
import org.freedom.modules.nfe.control.AbstractNFEFactory;
import org.freedom.modules.nfe.control.AbstractNFEFactory.TYPE_PROC;
import org.freedom.modulos.nfe.database.jdbc.NFEConnectionFactory;


public class FCancVenda extends FFilho implements ActionListener {

	private static final long serialVersionUID = 1L;

	private static final String APLIC_CONTRIB_NFE = "0";

	private static final String APLIC_FISCO_NFE = "3";
	
	private JPanelPad pinCli = new JPanelPad( 350, 100 );

	private JPanelPad pnRod = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private JTextFieldPad txtCodVenda = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 9, 0 );

	private JTextFieldPad txtDocVenda = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 9, 0 );

	private JTextFieldPad txtSerie = new JTextFieldPad( JTextFieldPad.TP_STRING, 4, 0 );

	private JTextFieldPad txtVlrLiqVenda = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 15, 2 );

	private JTextFieldPad txtStatusVenda = new JTextFieldPad( JTextFieldPad.TP_STRING, 2, 0 );

	private JTextFieldPad txtBloqVenda = new JTextFieldPad( JTextFieldPad.TP_STRING, 1, 0 );

	private JTextFieldPad txtTipoVenda = new JTextFieldPad( JTextFieldPad.TP_STRING, 1, 0 );

	private JTextFieldPad txtCodtipomov = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 9, 0 );

	private JTextFieldFK txtDesctipomov = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );
	
	private JTextFieldPad txtChaveNfe = new JTextFieldPad( JTextFieldPad.TP_STRING, 44, 0 );
	
	private JTextFieldPad txtCodmodnota = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 9, 0 );
	
	private JCheckBoxPad cbFiscaltipomov = new JCheckBoxPad( "Fiscal", "S", "N" );

	private JTextAreaPad txaMotivoCancVenda = new JTextAreaPad(250);

	private JScrollPane spnMotivoCancVenda = new JScrollPane( txaMotivoCancVenda );

	private JButtonPad btCancelar = new JButtonPad( "Cancelar", Icone.novo( "btCancelar.png" ) );

	private JButtonPad btSair = new JButtonPad( "Sair", Icone.novo( "btSair.png" ) );

	private ListaCampos lcVenda = new ListaCampos( this );
	
	private ListaCampos lcTipomov = new ListaCampos( this, "TM" );
	
	private Object[] oPrefs = null;

	private NFEConnectionFactory nfecf = null;

	private enum POS_PREFS {
		 PROCEMINFE, AMBIENTENFE
	}

	public FCancVenda() {

		super( false );
		setTitulo( "Cancelamento" );
		setAtribos( 50, 50, 450, 340 );

		Funcoes.setBordReq( txtCodVenda );
		txtDocVenda.setAtivo( false );
		txtSerie.setAtivo( false );
		txtVlrLiqVenda.setAtivo( false );
		//txaMotivoCancVenda.setBackground( Color.WHITE );

		lcVenda.add( new GuardaCampo( txtCodVenda, "CodVenda", "Cód.Venda", ListaCampos.DB_PK, false ) );
		lcVenda.add( new GuardaCampo( txtDocVenda, "DocVenda", "Documento", ListaCampos.DB_SI, false ) );
		lcVenda.add( new GuardaCampo( txtSerie, "Serie", "Série", ListaCampos.DB_SI, false ) );
		lcVenda.add( new GuardaCampo( txtBloqVenda, "BloqVenda", "Bloqueio", ListaCampos.DB_SI, false ) );
		lcVenda.add( new GuardaCampo( txtTipoVenda, "TipoVenda", "Tp.venda", ListaCampos.DB_SI, false ) );
		lcVenda.add( new GuardaCampo( txtVlrLiqVenda, "VlrLiqVenda", "V. Liq.", ListaCampos.DB_SI, false ) );
		lcVenda.add( new GuardaCampo( txtStatusVenda, "StatusVenda", "Status", ListaCampos.DB_SI, false ) );
		lcVenda.add( new GuardaCampo( txtCodtipomov, "Codtipomov", "Cód.tipo mov.", ListaCampos.DB_FK, txtDesctipomov, false ) );
		lcVenda.add( new GuardaCampo( txtChaveNfe, "Chavenfevenda", "Chave de acesso", ListaCampos.DB_SI, false ) );
		
		lcVenda.montaSql( false, "VENDA", "VD" );
		lcVenda.setReadOnly( true );
		txtCodVenda.setTabelaExterna( lcVenda, null );
		txtCodVenda.setFK( true );
		txtCodVenda.setNomeCampo( "CodVenda" );

		lcTipomov.add( new GuardaCampo( txtCodtipomov, "Codtipomov", "Cód.tp.mov.", ListaCampos.DB_PK, null, false ) );
		lcTipomov.add( new GuardaCampo( txtDesctipomov, "Desctipomov", "Descrição tipo de movimento", ListaCampos.DB_SI, null, false ) );
		lcTipomov.add( new GuardaCampo( txtCodmodnota, "Codmodnota", "Cód.mod.nf.", ListaCampos.DB_SI, null, false ) );
		lcTipomov.add( new GuardaCampo( cbFiscaltipomov, "Fiscaltipomov", "Fiscal", ListaCampos.DB_SI, null, false ) );
		lcTipomov.montaSql( false, "TIPOMOV", "EQ" );
		lcTipomov.setReadOnly( true );
		txtCodtipomov.setTabelaExterna( lcTipomov, null );
		txtCodtipomov.setFK( true );
		txtCodtipomov.setNomeCampo( "Codtipomov" );
		txtCodtipomov.setSoLeitura( true );
		txtCodmodnota.setSoLeitura( true );
		txtChaveNfe.setEditable( false );
		cbFiscaltipomov.setEnabled( false );
		Container c = getContentPane();
		c.setLayout( new BorderLayout() );

		btSair.setPreferredSize( new Dimension( 100, 30 ) );

		pnRod.setPreferredSize( new Dimension( 350, 30 ) );
		pnRod.add( btSair, BorderLayout.EAST );

		c.add( pnRod, BorderLayout.SOUTH );
		c.add( pinCli, BorderLayout.CENTER );

		btCancelar.setToolTipText( "Cancela Venda" );

		pinCli.adic( new JLabelPad( "Nº Pedido" ), 7, 0, 80, 20 );
		pinCli.adic( txtCodVenda, 7, 20, 80, 20 );
		pinCli.adic( new JLabelPad( "Doc." ), 90, 0, 67, 20 );
		pinCli.adic( txtDocVenda, 90, 20, 67, 20 );
		pinCli.adic( new JLabelPad( "Série" ), 160, 0, 67, 20 );
		pinCli.adic( txtSerie, 160, 20, 67, 20 );
		pinCli.adic( new JLabelPad( "Valor" ), 230, 0, 100, 20 );
		pinCli.adic( txtVlrLiqVenda, 230, 20, 100, 20 );
		pinCli.adic( new JLabelPad( "Fiscal" ), 333, 0, 60, 20 );
		pinCli.adic( cbFiscaltipomov, 333, 20, 60, 20 );
		pinCli.adic( new JLabelPad( "Cód.tp.mov." ), 7, 40, 80, 20 );
		pinCli.adic( txtCodtipomov, 7, 60, 80, 20 );
		pinCli.adic( new JLabelPad( "Descrição do tipo de movimento" ), 90, 40, 246, 20 );
		pinCli.adic( txtDesctipomov, 90, 60, 246, 20 );
		pinCli.adic( new JLabelPad( "Cód.mod.nf." ), 339, 40, 70, 20 );
		pinCli.adic( txtCodmodnota, 339, 60, 70, 20 );
		pinCli.adic( new JLabelPad( "Chave de acesso da NFe" ), 7, 80, 400, 20 );
		pinCli.adic( txtChaveNfe, 7, 100, 400, 20 );
		pinCli.adic( new JLabelPad( "Motivo de cancelamento" ), 7, 120, 400, 20 );
		pinCli.adic( spnMotivoCancVenda, 7, 140, 400, 70 );
		
		pinCli.adic( btCancelar, 7, 230, 130, 30 );

		btSair.addActionListener( this );
		btCancelar.addActionListener( this );

	}

	public boolean cancelar( int codvenda, String status, String motivocancvenda ) {

		boolean result = false;

		if ( codvenda == 0 ) {
			Funcoes.mensagemInforma( null, "Nenhuma venda foi selecionada!" );
			txtCodVenda.requestFocus();
		}
		else if ( status.substring( 0, 1 ).equals( "C" ) )
			Funcoes.mensagemInforma( null, "Venda ja foi cancelada!!" );

		else if ( "VPD".indexOf( status.substring( 0, 1 ) ) != -1 ) {

			if ( Funcoes.mensagemConfirma( null, "Deseja realmente cancelar esta venda?" ) == JOptionPane.YES_OPTION ) {
				boolean cancVenda = true;
				int codemp = Aplicativo.iCodEmp;
				int codfilial = ListaCampos.getMasterFilial( "VDVENDA" );
				int modelo = txtCodmodnota.getVlrInteger();
				String serie = txtSerie.getVlrString();
				int docvenda = txtDocVenda.getVlrInteger();
				String dirNFE = Aplicativo.strTemp;
				String tipovenda = "V";
				String fiscaltipomov = cbFiscaltipomov.getVlrString();
				String chavenfe = txtChaveNfe.getVlrString();
				String justificativa = txaMotivoCancVenda.getVlrString();
				if ( APLIC_CONTRIB_NFE.equals(oPrefs[POS_PREFS.PROCEMINFE.ordinal()]) && "S".equals( fiscaltipomov )) {
					if ("".equals(chavenfe)) {
						Funcoes.mensagemInforma( this, "Nota fiscal eletrônica sem chave de acesso não pode ser cancelada !" );
						return result;
					}
					FreedomNFEKey key = new FreedomNFEKey( codemp, codfilial, tipovenda, codvenda, modelo, serie, docvenda, dirNFE );
					nfecf.setMotivoCancNfe(justificativa);
					nfecf.setChaveNfe( chavenfe );
					nfecf.setKey(key);
					nfecf.post();
					// trocar isValid para isAutorizada
					cancVenda = nfecf.getObjNFEFactory().isAutorizada();
				}
				if (cancVenda) {
					PreparedStatement ps = null;
					StringBuilder sql = new StringBuilder();
					sql.append( "UPDATE VDVENDA SET MOTIVOCANCVENDA=?, STATUSVENDA = ? " ); 
					sql.append( "WHERE CODEMP=? AND CODFILIAL=? AND CODVENDA=? AND TIPOVENDA='V'");
	
					try {
	 
						ps = con.prepareStatement( sql.toString() );
						int param = 1;
						ps.setString( param++, motivocancvenda );
						ps.setString( param++, "C"+status.substring( 0, 1 ) );
						ps.setInt( param++, Aplicativo.iCodEmp );
						ps.setInt( param++, ListaCampos.getMasterFilial( "VDVENDA" ) );
						ps.setInt( param++, codvenda );
						ps.executeUpdate();
	
						ps.close();
						con.commit();
	
						result = true;
	
						FCancVendaOrc.cancelar( con, codvenda, txtTipoVenda.getVlrString(), txtStatusVenda.getVlrString(), txtBloqVenda.getVlrString() );
	
					} catch ( SQLException err ) {
						Funcoes.mensagemErro( null, "Erro ao cancelar a venda!\n" + err.getMessage(), true, con, err );
					} finally {
						ps = null;
						sql = null;
					}
				}
			}

		}

		return result;

	}

	public void actionPerformed( ActionEvent evt ) {

		if ( evt.getSource() == btSair )
			dispose();
		else if ( evt.getSource() == btCancelar ) {
			if ("".equals( txaMotivoCancVenda.getVlrString().trim()) ) {
				Funcoes.mensagemInforma( this, "Preencha o motivo do cancelamento !" );
				txaMotivoCancVenda.requestFocus();
				
			} else {
				cancelar( txtCodVenda.getVlrInteger().intValue(), txtStatusVenda.getVlrString(), txaMotivoCancVenda.getVlrString() );
			}
		}
	}

	public void setConexao( DbConnection cn ) {

		super.setConexao( cn );
		lcVenda.setConexao( cn );
		lcTipomov.setConexao( cn );
		oPrefs = prefs();
		
		setNfecf( new NFEConnectionFactory( con, Aplicativo.getInstace().getConexaoNFE()
				, AbstractNFEFactory.TP_NF_OUT, false
				, (String) oPrefs[POS_PREFS.PROCEMINFE.ordinal()]
				, (String) oPrefs[POS_PREFS.AMBIENTENFE.ordinal()]
				, Aplicativo.strTemp, TYPE_PROC.CANCELAMENTO) );
	}
	
	private Object[] prefs() {

		Object[] retorno = new Object[ POS_PREFS.values().length ];
		StringBuffer sSQL = new StringBuffer();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			sSQL.append( "SELECT COALESCE(P1.PROCEMINFE,'3') PROCEMINFE, COALESCE(P1.AMBIENTENFE,'2') AMBIENTENFE " );
			sSQL.append( "FROM SGPREFERE1 P1 " );
			sSQL.append( "WHERE P1.CODEMP=? AND P1.CODFILIAL=? " );
			ps = con.prepareStatement( sSQL.toString() );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "SGPREFERE1" ) );
			rs = ps.executeQuery();
			if ( rs.next() ) {
				retorno[ POS_PREFS.PROCEMINFE.ordinal()] = rs.getString( POS_PREFS.PROCEMINFE.toString() ); 
				retorno[ POS_PREFS.AMBIENTENFE.ordinal()] = rs.getString( POS_PREFS.AMBIENTENFE.toString() ); 
			}
			rs.close();
			ps.close();
			con.commit();
		} catch ( SQLException err ) {
			err.printStackTrace();
			Funcoes.mensagemErro( this, "Erro ao carregar a tabela PREFERE1!\n" + err.getMessage(), true, con, err );
		} finally {
			rs = null;
			ps = null;
		}
		return retorno;
	}

	public void setNfecf( NFEConnectionFactory nfecf ) {

		this.nfecf = nfecf;
	}
}
