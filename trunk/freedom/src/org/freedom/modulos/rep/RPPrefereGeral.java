/**
 * @version 02/2007 <BR>
 * @author Setpoint Informática Ltda.<BR>
 * @author Alex Rodrigues<BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.rep <BR>
 * Classe:
 * @(#)RPPrefereGeral.java <BR>
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
 * Tela para cadastro de preferências para o sistema.
 * 
 */

package org.freedom.modulos.rep;

import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JCheckBoxPad;
import org.freedom.componentes.JPasswordFieldPad;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FDados;

public class RPPrefereGeral extends FDados implements ActionListener {

	private static final long serialVersionUID = 1L;
	
	private final JCheckBoxPad cbIpiComis = new JCheckBoxPad( "Incluir IPI no calculo da comissão?", "S", "N" );
	
	private final JCheckBoxPad cbIPIPed = new JCheckBoxPad( "Imprimir IPI no pedido?", "S", "N" );
	
	private final JCheckBoxPad cbCodBarProd = new JCheckBoxPad( "Usar código de barras no pedido?", "S", "N" );
	
	private final JCheckBoxPad cbEndCliPed = new JCheckBoxPad( "Incluir endereço dos clientes no pedido?", "S", "N" );
	
	private final JCheckBoxPad cbOrdemPed = new JCheckBoxPad( "Ordena pedido por ordem alfabetíca?", "S", "N" );

	private final JTextFieldPad txtServidorSMTP = new JTextFieldPad( JTextFieldPad.TP_STRING, 50, 0 );

	private final JTextFieldPad txtPortaSMTP = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private final JTextFieldPad txtUsuarioSMTP = new JTextFieldPad( JTextFieldPad.TP_STRING, 30, 0 );	
	
	private final JPasswordFieldPad txtSenhaSMTP = new JPasswordFieldPad( 30 );
	
	private final JCheckBoxPad cbAutenticaSMTP = new JCheckBoxPad( "Autenticar ?", "S", "N" );

	private final JTextFieldPad txtCasasDesc = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 5, 0 );

	private final JTextFieldPad txtCasasDescFin = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 5, 0 );
	
	private final JTextFieldPad txtCodMoeda = new JTextFieldPad( JTextFieldPad.TP_STRING, 4, 0 );
	
	private final JTextFieldFK txtNomeMoeda = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );
	
	private final JTextFieldPad txtLayoutPed = new JTextFieldPad( JTextFieldPad.TP_STRING, 15, 0 );
	
	private final ListaCampos lcMoeda = new ListaCampos( this, "MO" );
	

	public RPPrefereGeral() {

		super( false );
		setTitulo( "Preferências gerais" );		
		setAtribos( 50, 50, 425, 470 );
		
		montaListaCampos();		
		montaTela();
		setListaCampos( false, "PREFERE1", "SG" );
		
		nav.setAtivo( 0, false );
		lcCampos.setPodeExc( false );
	}
	
	private void montaListaCampos() {
		
		/*********
		 * MOEDA *
		 *********/

		lcMoeda.add( new GuardaCampo( txtCodMoeda, "CodMoeda", "Cód.moeda", ListaCampos.DB_PK, false ) );
		lcMoeda.add( new GuardaCampo( txtNomeMoeda, "SingMoeda", "Descrição da moeda", ListaCampos.DB_SI, false ) );
		lcMoeda.montaSql( false, "MOEDA", "RP" );
		lcMoeda.setQueryCommit( false );
		lcMoeda.setReadOnly( true );
		txtCodMoeda.setTabelaExterna( lcMoeda );
	}
	
	private void montaTela() {
		
		JLabel vendas = new JLabel( "Vendas", SwingConstants.CENTER );
		vendas.setOpaque( true );
		JLabel linha1 = new JLabel();
		linha1.setBorder( BorderFactory.createEtchedBorder() );
		
		adic( vendas, 27, 0, 80, 20 );
		adic( linha1, 7, 10, 397, 120 );
		
		adicDB( cbIpiComis, 17, 20, 300, 20, "IpiComis", null, true );
		adicDB( cbIPIPed, 17, 40, 300, 20, "IPIPed", null, true );
		adicDB( cbCodBarProd, 17, 60, 300, 20, "CodBarProd", null, true );
		adicDB( cbEndCliPed, 17, 80, 300, 20, "EndCliPed", null, true );
		adicDB( cbOrdemPed, 17, 100, 300, 20, "OrdemPed", null, true );
		
		JLabel email = new JLabel( "E - Mail", SwingConstants.CENTER );
		email.setOpaque( true );
		JLabel linha2 = new JLabel();
		linha2.setBorder( BorderFactory.createEtchedBorder() );
		
		adic( email, 27, 130, 80, 20 );
		adic( linha2, 7, 140, 397, 100 );
		
		adicCampo( txtServidorSMTP, 17, 170, 230, 20, "ServidorSMTP", "Servidor de SMTP", ListaCampos.DB_SI, false );
		adicCampo( txtPortaSMTP, 250, 170, 41, 20, "PortaSMTP", "Porta", ListaCampos.DB_SI, false );
		adicDB( cbAutenticaSMTP, 294, 170, 100, 20, "AutenticaSMTP", "", false );
		adicCampo( txtUsuarioSMTP, 17, 210, 190, 20, "UsuarioSMTP", "Id do usuario", ListaCampos.DB_SI, false );
		adicCampo( txtSenhaSMTP, 210, 210, 184, 20, "SenhaSMTP", "Senha do usuario", ListaCampos.DB_SI, false );
		
		JLabel campos = new JLabel( "Campos", SwingConstants.CENTER );
		campos.setOpaque( true );
		JLabel linha3 = new JLabel();
		linha3.setBorder( BorderFactory.createEtchedBorder() );
		
		adic( campos, 27, 240, 80, 20 );
		adic( linha3, 7, 250, 397, 140 );
		
		adicCampo( txtCasasDesc, 17, 280, 150, 20, "CasasDec", "Decimais", ListaCampos.DB_SI, false );
		adicCampo( txtLayoutPed, 240, 280, 154, 20, "LayoutPed", "Classe para pedido", ListaCampos.DB_SI, false );
		adicCampo( txtCasasDescFin, 17, 320, 150, 20, "CasasDecFin", "Decimais ( financeiro )", ListaCampos.DB_SI, false );
		adicCampo( txtCodMoeda, 17, 360, 100, 20, "CodMoeda", "Cód.moeda", ListaCampos.DB_FK, txtNomeMoeda, false );
		adicDescFK( txtNomeMoeda, 120, 360, 274, 20, "SingMoeda", "Descrição da moeda" );
		
	}

	@ Override
	public synchronized void setConexao( Connection cn ) {

		super.setConexao( cn );
		lcMoeda.setConexao( cn );
		lcCampos.carregaDados();
	}
	
	public static List<Object> getPrefere( final Connection con ) {
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuilder sSQL = new StringBuilder();
		List<Object> prefere = new ArrayList<Object>( EPrefere.values().length );
		
		try {
			
			sSQL.append( "SELECT IPICOMIS,IPIPED,CODBARPROD,ENDCLIPED,ORDEMPED," );
			sSQL.append( "SERVIDORSMTP,PORTASMTP,USUARIOSMTP,SENHASMTP,AUTENTICASMTP," );
			sSQL.append( "CASASDEC,CASASDECFIN,CODMOEDA,LAYOUTPED " );
			sSQL.append( "FROM SGPREFERE1 WHERE CODEMP=? AND CODFILIAL=?" );
			ps = con.prepareStatement( sSQL.toString() );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "SGPREFERE1" ) );
			rs = ps.executeQuery();
			
			if ( rs.next() ) {
				
				prefere.add( EPrefere.IPICOMIS.ordinal(), rs.getString( "IPICOMIS" ) );
				prefere.add( EPrefere.IPIPED.ordinal(), rs.getString( "IPIPED" ) );
				prefere.add( EPrefere.CODBARPROD.ordinal(), rs.getString( "CODBARPROD" ) );
				prefere.add( EPrefere.ENDCLIPED.ordinal(), rs.getString( "ENDCLIPED" ) );
				prefere.add( EPrefere.ORDEMPED.ordinal(), rs.getString( "ORDEMPED" ) );
				prefere.add( EPrefere.SERVIDORSMTP.ordinal(), rs.getString( "SERVIDORSMTP" ) );
				prefere.add( EPrefere.PORTASMTP.ordinal(), rs.getInt( "PORTASMTP" ) );
				prefere.add( EPrefere.USUARIOSMTP.ordinal(), rs.getString( "USUARIOSMTP" ) );
				prefere.add( EPrefere.SENHASMTP.ordinal(), rs.getString( "SENHASMTP" ) );
				prefere.add( EPrefere.AUTENTICASMTP.ordinal(), rs.getString( "AUTENTICASMTP" ) );
				prefere.add( EPrefere.CASASDEC.ordinal(), rs.getInt( "CASASDEC" ) );
				prefere.add( EPrefere.CASASDECFIN.ordinal(), rs.getInt( "CASASDECFIN" ) );
				prefere.add( EPrefere.CODMOEDA.ordinal(), rs.getString( "CODMOEDA" ) );
				prefere.add( EPrefere.LAYOUTPED.ordinal(), rs.getString( "LAYOUTPED" ) );
			}
			
			rs.close();
			ps.close();
			
			if ( !con.getAutoCommit() ) {
				con.commit();
			}
			
		} catch ( Exception e ) {
			Funcoes.mensagemErro( null, "Erro ao buscar preferências!\n" + e.getMessage() );
			e.printStackTrace();
		}
		
		return prefere;
	}
	
	public enum EPrefere {
		IPICOMIS,
	    IPIPED,
	    CODBARPROD,
	    ENDCLIPED,
	    ORDEMPED,
	    SERVIDORSMTP,
	    PORTASMTP,
	    USUARIOSMTP,
	    SENHASMTP,
	    AUTENTICASMTP,
	    CASASDEC,
	    CASASDECFIN,
	    CODMOEDA,
	    LAYOUTPED;
	}
}
