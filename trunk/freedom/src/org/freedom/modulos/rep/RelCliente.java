/**
 * @version 03/2007 <BR>
 * @author Setpoint Informática Ltda.<BR>
 * @author Alex Rodrigues<BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.rep <BR>
 * Classe:
 * @(#)RelCliente.java <BR>
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
 * Relatorio de clientes, em dois modos: completo e resumido.
 * 
 */

package org.freedom.modulos.rep;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.JLabel;

import net.sf.jasperreports.engine.JasperPrintManager;

import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JRadioGroup;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FPrinterJob;
import org.freedom.telas.FRelatorio;

public class RelCliente extends FRelatorio {

	private static final long serialVersionUID = 1;

	private final JTextFieldPad txtCodTpCli = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private final JTextFieldFK txtDescTpCli = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );

	private final JTextFieldPad txtCodVend = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private final JTextFieldFK txtNomeVend = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );
	
	private JRadioGroup rgModo;
	
	private JRadioGroup rgOrdem;
	
	private final ListaCampos lcTipoCli = new ListaCampos( this );
	
	private final ListaCampos lcVend = new ListaCampos( this );

	public RelCliente() {

		super();
		setTitulo( "Relatorio de clientes" );		
		setAtribos( 50, 50, 325, 290 );

		montaRadioGrupos();
		montaListaCampos();
		montaTela();
	}
	
	private void montaRadioGrupos() {

		Vector<String> labs = new Vector<String>();
		labs.add( "completo" );
		labs.add( "resumido" );
		Vector<String> vals = new Vector<String>();
		vals.add( "C" );
		vals.add( "R" );
		rgModo = new JRadioGroup( 1, 2, labs, vals );
		
		Vector<String> labs1 = new Vector<String>();
		labs1.add( "Código" );
		labs1.add( "Descrição" );
		Vector<String> vals1 = new Vector<String>();
		vals1.add( "CODVEND" );
		vals1.add( "NOMEVEND" );
		rgOrdem = new JRadioGroup( 1, 2, labs1, vals1 );		
	}
	
	private void montaListaCampos() {
		
		lcTipoCli.add( new GuardaCampo( txtCodTpCli, "CodTipoCli", "Cód.tp.cli.", ListaCampos.DB_PK, false ) );
		lcTipoCli.add( new GuardaCampo( txtDescTpCli, "DescTipoCli", "Descrição do tipo de cliente", ListaCampos.DB_SI, false ) );
		lcTipoCli.montaSql( false, "TIPOCLI", "RP" );
		lcTipoCli.setQueryCommit( false );
		lcTipoCli.setReadOnly( true );
		txtCodTpCli.setListaCampos( lcTipoCli );
		txtCodTpCli.setTabelaExterna( lcTipoCli );
		txtCodTpCli.setPK( true );
		txtCodTpCli.setNomeCampo( "CodTipoCli" );
		
		lcVend.add( new GuardaCampo( txtCodVend, "CodVend", "Cód.vend.", ListaCampos.DB_PK, false ) );
		lcVend.add( new GuardaCampo( txtNomeVend, "NomeVend", "Nome do vendedor", ListaCampos.DB_SI, false ) );
		lcVend.montaSql( false, "VENDEDOR", "RP" );
		lcVend.setQueryCommit( false );
		lcVend.setReadOnly( true );
		txtCodVend.setListaCampos( lcVend );
		txtCodVend.setTabelaExterna( lcVend );
		txtCodVend.setPK( true );
		txtCodVend.setNomeCampo( "CodVend" );
	}
	
	private void montaTela() {
		
		adic( new JLabel( "Modo :" ), 10, 10, 200, 20 );
		adic( rgModo, 10, 35, 290, 30 );
		adic( new JLabel( "Ordem do relatorio :" ), 10, 70, 200, 20 );
		adic( rgOrdem, 10, 95, 290, 30 );
		
		adic( new JLabel( "Cód.tp.cli." ), 10, 130, 77, 20 );
		adic( txtCodTpCli, 10, 150, 77, 20 );
		adic( new JLabel( "Descrição do tipo de cliente" ), 90, 130, 210, 20 );
		adic( txtDescTpCli, 90, 150, 210, 20 );
		
		adic( new JLabel( "Cód.vend." ), 10, 170, 77, 20 );
		adic( txtCodVend, 10, 190, 77, 20 );
		adic( new JLabel( "Nome do vendedor" ), 90, 170, 210, 20 );
		adic( txtNomeVend, 90, 190, 210, 20 );
	}

	@ Override
	public void imprimir( boolean visualizar ) {

		try {
			
			String relatorio = "C".equals( rgModo.getVlrString() ) ? "rpclientecomp.jasper" : "rpclienteresum.jasper";
			String modo = "C".equals( rgModo.getVlrString() ) ? "( completo )" : " ( resumido )";
			String filtro = null;
			
			StringBuilder sql = new StringBuilder();
			
			sql.append( "SELECT CODCLI,RAZCLI,NOMECLI,CNPJCLI,INSCCLI, " );
			sql.append( "ENDCLI,CIDCLI,ESTCLI,CEPCLI,BAIRCLI,DDDCLI, " );
			sql.append( "FONECLI,FAXCLI,EMAILCLI " );
			sql.append( "FROM RPCLIENTE " );
			sql.append( "WHERE CODEMP=? AND CODFILIAL=? " );
			if ( txtCodTpCli.getVlrString().trim().length() > 0 ) {
				sql.append( "AND CODCLI=" + txtCodTpCli.getVlrInteger().intValue() );
				filtro = "Tipo de cliente : " + txtDescTpCli.getVlrString().trim();
			}
			if ( txtNomeVend.getVlrString().trim().length() > 0 ) {
				sql.append( "AND CODVEND=" + txtCodVend.getVlrInteger().intValue() );
				filtro = "Vendedor : " + txtNomeVend.getVlrString().trim();
			}
			sql.append( "ORDER BY " + rgOrdem.getVlrString() );
			
			PreparedStatement ps = con.prepareStatement( sql.toString() );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "RPCLIENTE" ) );
			ResultSet rs = ps.executeQuery();
			
			HashMap<String,Object> hParam = new HashMap<String, Object>();

			hParam.put( "CODEMP", Aplicativo.iCodEmp );
			hParam.put( "SUBREPORT_DIR", RelCliente.class.getResource( "relatorios/" ).getPath() );
			hParam.put( "REPORT_CONNECTION", con );
			
			FPrinterJob dlGr = new FPrinterJob( "modulos/rep/relatorios/"+relatorio, "CLIENTES" + modo, filtro, rs, hParam, this );

			if ( visualizar ) {
				dlGr.setVisible( true );
			}
			else {
				JasperPrintManager.printReport( dlGr.getRelatorio(), true );
			}
			
		} catch ( Exception e ) {
			Funcoes.mensagemErro( this, "Erro ao montar relatorio!\n" + e.getMessage() );
			e.printStackTrace();
		}

	}

	public void setConexao( Connection cn ) {

		super.setConexao( cn );

		lcTipoCli.setConexao( cn );
		lcVend.setConexao( cn );
	}

}
