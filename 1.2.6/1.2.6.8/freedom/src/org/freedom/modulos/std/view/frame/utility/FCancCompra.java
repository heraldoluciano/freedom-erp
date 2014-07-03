/**
 * @version 05/12/2008 <BR>
 * @author Setpoint Informática Ltda.
 * 
 *         Projeto: Freedom <BR>
 * 
 *         Pacote: org.freedom.modulos.std <BR>
 *         Classe: @(#)FCancCompra.java <BR>
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
import java.util.Date;

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

public class FCancCompra extends FFilho implements ActionListener {

	private static final long serialVersionUID = 1L;

	private static final String APLIC_CONTRIB_NFE = "0";

	private static final String APLIC_FISCO_NFE = "3";

	private JTextFieldPad txtCodCompra = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 9, 0 );

	private JTextFieldPad txtDocCompra = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 9, 0 );

	private JTextFieldPad txtSerie = new JTextFieldPad( JTextFieldPad.TP_STRING, 4, 0 );

	private JTextFieldPad txtVlrLiqCompra = new JTextFieldPad( JTextFieldPad.TP_DECIMAL, 15, Aplicativo.casasDec );

	private JTextFieldPad txtStatusCompra = new JTextFieldPad( JTextFieldPad.TP_STRING, 4, 0 );
	
	private JTextFieldPad txtBloqCompra = new JTextFieldPad( JTextFieldPad.TP_STRING, 1, 0 );

	private JTextFieldPad txtTipoCompra = new JTextFieldPad( JTextFieldPad.TP_STRING, 1, 0 );

	private JTextFieldPad txtCodtipomov = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 9, 0 );

	private JTextFieldFK txtDesctipomov = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );
	
	private JTextFieldPad txtChaveNfe = new JTextFieldPad( JTextFieldPad.TP_STRING, 44, 0 );
	
	private JTextFieldPad txtCodmodnota = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 9, 0 );
	
	private JCheckBoxPad cbFiscaltipomov = new JCheckBoxPad( "Fiscal", "S", "N" );

	private JTextAreaPad txaMotivoCancCompra = new JTextAreaPad(250);

	private JScrollPane spnMotivoCancCompra = new JScrollPane( txaMotivoCancCompra );

	private JButtonPad btCancelar = new JButtonPad( "Cancelar", Icone.novo( "btCancelar.png" ) );

	private JButtonPad btSair = new JButtonPad( "Sair", Icone.novo( "btSair.png" ) );

	private JPanelPad pinCli = new JPanelPad( 350, 100 );

	private JPanelPad pnRod = new JPanelPad( JPanelPad.TP_JPANEL, new BorderLayout() );

	private ListaCampos lcCompra = new ListaCampos( this );
	
	private ListaCampos lcTipomov = new ListaCampos( this, "TM" );
	
	private Object[] oPrefs = null;

	private NFEConnectionFactory nfecf = null;

	private enum POS_PREFS {
		 PROCEMINFE, AMBIENTENFE, CNPJFILIAL, SIGLAUF, TIPOEMISSAONFE
	}


	public FCancCompra() {

		super( false );
		
		setTitulo( "Cancelamento" );
		setAtribos( 50, 50, 450, 340 );

		Funcoes.setBordReq( txtCodCompra );
		txtDocCompra.setAtivo( false );
		txtSerie.setAtivo( false );
		txtVlrLiqCompra.setAtivo( false );
		txtStatusCompra.setAtivo( false );

		montaTela();
		montaListaCampos();
	}

	private void montaTela() {

		Container c = getContentPane();
		c.setLayout( new BorderLayout() );

		btSair.setPreferredSize( new Dimension( 100, 30 ) );

		pnRod.setPreferredSize( new Dimension( 350, 30 ) );
		pnRod.add( btSair, BorderLayout.EAST );

		c.add( pnRod, BorderLayout.SOUTH );
		c.add( pinCli, BorderLayout.CENTER );

		btCancelar.setToolTipText( "Cancela Compra" );

		pinCli.adic( new JLabelPad( "Nº Pedido" ), 7, 0, 80, 20 );
		pinCli.adic( txtCodCompra, 7, 20, 80, 20 );
		pinCli.adic( new JLabelPad( "Doc." ), 90, 0, 67, 20 );
		pinCli.adic( txtDocCompra, 90, 20, 67, 20 );
		pinCli.adic( new JLabelPad( "Série" ), 160, 0, 67, 20 );
		pinCli.adic( txtSerie, 160, 20, 67, 20 );
		pinCli.adic( new JLabelPad( "Valor" ), 230, 0, 100, 20 );
		pinCli.adic( txtVlrLiqCompra, 230, 20, 100, 20 );
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
		pinCli.adic( spnMotivoCancCompra, 7, 140, 400, 70 );
		
		pinCli.adic( btCancelar, 7, 230, 130, 30 );

		btSair.addActionListener( this );
		btCancelar.addActionListener( this );

	}

	private void montaListaCampos() {

		lcCompra.add( new GuardaCampo( txtCodCompra, "CodCompra", "Cód.Compra", ListaCampos.DB_PK, null, false ) );
		lcCompra.add( new GuardaCampo( txtSerie, "Serie", "Série", ListaCampos.DB_SI, null, false ) );
		lcCompra.add( new GuardaCampo( txtDocCompra, "DocCompra", "Documento", ListaCampos.DB_SI, null, false ) );
		lcCompra.add( new GuardaCampo( txtBloqCompra, "BloqCompra", "Bloqueio", ListaCampos.DB_SI, false ) );
		lcCompra.add( new GuardaCampo( txtVlrLiqCompra, "VlrLiqCompra", "V. Liq.", ListaCampos.DB_SI, false ) );
		lcCompra.add( new GuardaCampo( txtStatusCompra, "StatusCompra", "Status", ListaCampos.DB_SI, false ) );
		lcCompra.add( new GuardaCampo( txtCodtipomov, "Codtipomov", "Cód.tipo mov.", ListaCampos.DB_FK, txtDesctipomov, false ) );
		lcCompra.add( new GuardaCampo( txtChaveNfe, "ChavenfeCompra", "Chave de acesso", ListaCampos.DB_SI, false ) );
		lcCompra.montaSql( false, "COMPRA", "CP" );
		lcCompra.setReadOnly( true );
		txtCodCompra.setTabelaExterna( lcCompra, null );
		txtCodCompra.setFK( true );
		txtCodCompra.setNomeCampo( "CodCompra" );
		
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

	}

	public void setConexao( DbConnection cn ) {

		super.setConexao( cn );
		lcCompra.setConexao( cn );
		lcTipomov.setConexao( cn );
		oPrefs = prefs();
		
		setNfecf( new NFEConnectionFactory( con, Aplicativo.getInstace().getConexaoNFE()
				, AbstractNFEFactory.TP_NF_IN, false
				, (String) oPrefs[POS_PREFS.PROCEMINFE.ordinal()]
				, (String) oPrefs[POS_PREFS.AMBIENTENFE.ordinal()]
				, Aplicativo.strTemp, TYPE_PROC.CANCELAMENTO
				, (String) oPrefs[POS_PREFS.CNPJFILIAL.ordinal()]
				, (String) oPrefs[POS_PREFS.SIGLAUF.ordinal()]
				, new Integer((String) oPrefs[POS_PREFS.TIPOEMISSAONFE.ordinal()])));


	}
	
	private boolean verifPagar( int codemp, int codfilial, int codcompra ) {
		boolean result = true;
		StringBuilder sql = new StringBuilder();
		try {
			sql.append("select ip.statusitpag from fnpagar p, fnitpagar ip ");
			sql.append("where p.codempcp=? and p.codfilialcp=? and p.codcompra=? ");
			sql.append("and ip.codemp=p.codemp and ip.codfilial=p.codfilial and ip.codpag=p.codpag ");
			sql.append("and ip.statusitpag in ('PL','PP')");
			PreparedStatement ps = con.prepareStatement( sql.toString() );
			int param = 1;
			ps.setInt( param++, codemp );
			ps.setInt( param++, codfilial );
			ps.setInt( param++, codcompra );
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				result = false;
			}
			rs.close();
			ps.close();
			con.commit();
			
		} catch (SQLException err) {
			err.printStackTrace();
			try {
				con.rollback();
			} catch (SQLException err2) {
				err2.printStackTrace();
			}
			result = false;
		}
		return result;
	}
	

	public boolean cancelar( int codcompra, String status, String motivocanccompra ) {

		boolean result = false;
		int codemp = Aplicativo.iCodEmp;
		int codfilial = ListaCampos.getMasterFilial( "CPCOMPRA" );
		String justificativa = txaMotivoCancCompra.getVlrString().trim();
		if ( codcompra == 0 ) {
			Funcoes.mensagemInforma( null, "Nenhuma compra foi selecionada!" );
			txtCodCompra.requestFocus();
		} else if ( status.substring( 0, 1 ).equals( "X" ) ) {
			Funcoes.mensagemInforma( null, "Compra já foi cancelada !" );
		} else if (justificativa.length()<15) {
			Funcoes.mensagemInforma( null, "Tamanho mínimo para motivo/justificativa é de 15 caracteres !" );
		} else if (justificativa.length()>255) {
			Funcoes.mensagemInforma( null, "Tamanho máximo para motivo/justificativa é de 255 caracteres !" );
		} else if ( "CPD".indexOf( status.substring( 0, 1 ) ) != -1 ) {
			if (!verifPagar(codemp, codfilial, codcompra )) {
				Funcoes.mensagemInforma( this, "Esta compra possui títulos pagos.\nExecute o estorno na manutenção de contas a pagar !" );
				return result;
			}
			if ( Funcoes.mensagemConfirma( null, "Deseja realmente cancelar esta compra ?" ) == JOptionPane.YES_OPTION ) {
				boolean cancCompra = true;
				int modelo = txtCodmodnota.getVlrInteger();
				String serie = txtSerie.getVlrString();
				int doccompra = txtDocCompra.getVlrInteger();
				String dirNFE = Aplicativo.strTemp;
				String fiscaltipomov = cbFiscaltipomov.getVlrString();
				String chavenfe = txtChaveNfe.getVlrString();
				if ( APLIC_CONTRIB_NFE.equals(oPrefs[POS_PREFS.PROCEMINFE.ordinal()]) && "S".equals( fiscaltipomov )) {
					if ("".equals(chavenfe)) {
						Funcoes.mensagemInforma( this, "Nota fiscal eletrônica sem chave de acesso não pode ser cancelada !" );
						return result;
					}
					FreedomNFEKey key = new FreedomNFEKey( codemp, codfilial, codcompra, modelo, serie, doccompra, dirNFE );
					nfecf.setMotivoCancNfe(justificativa);
					nfecf.setData_evento( new Date() );
					nfecf.setChaveNfe( chavenfe );
					nfecf.setKey(key);
					nfecf.post();
					// trocar isValid para isAutorizada
					cancCompra = nfecf.getObjNFEFactory().isAutorizada();
				}
				if (cancCompra) {
					try {
						cancCompraFreedom(codemp, codfilial, codcompra, status, motivocanccompra);
						result = true;
					} catch ( SQLException err ) {
						Funcoes.mensagemErro( null, "Erro ao cancelar a compra !\n" + err.getMessage(), true, con, err );
					} finally {
						lcCompra.carregaDados();
					}
				}
			}

		}

		return result;

	}

	private void cancCompraFreedom(int codemp, int codfilial, int codcompra, String status, String motivocanccompra) throws SQLException {
		PreparedStatement ps = null;
		StringBuilder sql = new StringBuilder(); 

		// Desbloquear a compra, caso seja necessário
		sql.append( "UPDATE CPCOMPRA SET EMMANUT='S', BLOQCOMPRA='N' ");
		sql.append( "WHERE CODEMP=? AND CODFILIAL=? AND CODCOMPRA=? AND BLOQCOMPRA='S' ");
		ps = con.prepareStatement( sql.toString() );
		int param = 1;
		ps.setInt( param++, codemp );
		ps.setInt( param++, codfilial );
		ps.setInt( param++, codcompra );
		ps.executeUpdate();
		ps.close();
		// Ajusta o emmanut para 'N'
		sql.delete( 0, sql.length() ); 
		sql.append( "UPDATE CPCOMPRA SET EMMANUT='N' ");
		sql.append( "WHERE CODEMP=? AND CODFILIAL=? AND CODCOMPRA=? AND EMMANUT='S' ");
		ps = con.prepareStatement( sql.toString() );
		param = 1;
		ps.setInt( param++, codemp );
		ps.setInt( param++, codfilial );
		ps.setInt( param++, codcompra );
		ps.executeUpdate();
		ps.close();
		// Executa o cancelamento
		sql.delete( 0, sql.length() ); 
		sql.append( "UPDATE CPCOMPRA SET MOTIVOCANCCOMPRA=?, STATUSCOMPRA = ? " ); 
		sql.append( "WHERE CODEMP=? AND CODFILIAL=? AND CODCOMPRA=?" );
		ps = con.prepareStatement( sql.toString() );
		param = 1;
		ps.setString( param++, motivocanccompra );
		ps.setString( param++, "X"+status.substring( 0, 1 ) );
		ps.setInt( param++, codemp );
		ps.setInt( param++, codfilial );
		ps.setInt( param++, codcompra );
		ps.executeUpdate();
		ps.close();
		con.commit();

	}
	public void actionPerformed( ActionEvent e ) {

		if ( e.getSource() == btSair ) {
			dispose();
		}
		else if ( e.getSource() == btCancelar ) {
			cancelar( txtCodCompra.getVlrInteger(), txtStatusCompra.getVlrString(), txaMotivoCancCompra.getVlrString() );
		}
	}
	
	private Object[] prefs() {

		Object[] retorno = new Object[ POS_PREFS.values().length ];
		StringBuffer sSQL = new StringBuffer();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			sSQL.append( "SELECT COALESCE(P1.PROCEMINFE,'3') PROCEMINFE, COALESCE(P1.AMBIENTENFE,'2') AMBIENTENFE " );
			sSQL.append( ", F.CNPJFILIAL, F.SIGLAUF, coalesce(P1.TIPOEMISSAONFE,'1') TIPOEMISSAONFE ");
			sSQL.append( "FROM SGPREFERE1 P1 INNER JOIN SGFILIAL F ON " );
			sSQL.append( "F.CODEMP=P1.CODEMP AND F.CODFILIAL=P1.CODFILIAL ");
			sSQL.append( "WHERE P1.CODEMP=? AND P1.CODFILIAL=? " );
			ps = con.prepareStatement( sSQL.toString() );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "SGPREFERE1" ) );
			rs = ps.executeQuery();
			if ( rs.next() ) {
				retorno[ POS_PREFS.PROCEMINFE.ordinal()] = rs.getString( POS_PREFS.PROCEMINFE.toString() ); 
				retorno[ POS_PREFS.AMBIENTENFE.ordinal()] = rs.getString( POS_PREFS.AMBIENTENFE.toString() ); 
				retorno[ POS_PREFS.CNPJFILIAL.ordinal()] = rs.getString( POS_PREFS.CNPJFILIAL.toString() ); 
				retorno[ POS_PREFS.SIGLAUF.ordinal()] = rs.getString( POS_PREFS.SIGLAUF.toString() ); 
				retorno[ POS_PREFS.TIPOEMISSAONFE.ordinal()] = rs.getString( POS_PREFS.TIPOEMISSAONFE.toString() ); 
				
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
