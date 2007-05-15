/**
 * @version 03/2007 <BR>
 * @author Setpoint Informática Ltda.<BR>
 * @author Alex Rodrigues<BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.rep <BR>
 * Classe:
 * @(#)RelFornecedor.java <BR>
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
 * Relatorio de fornecedores, em dois modos: completo e resumido.
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

public class RelFornecedor extends FRelatorio {

	private static final long serialVersionUID = 1;

	private final JTextFieldPad txtCodFor = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );

	private final JTextFieldFK txtRazFor = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );
	
	private JRadioGroup rgModo;
	
	private JRadioGroup rgOrdem;
	
	private final ListaCampos lcFor = new ListaCampos( this );

	public RelFornecedor() {

		super();
		setTitulo( "Relatorio de fonecedores" );		
		setAtribos( 50, 50, 325, 250 );
		
		lcFor.add( new GuardaCampo( txtCodFor, "CodFor", "Cód.for.", ListaCampos.DB_PK, false ) );
		lcFor.add( new GuardaCampo( txtRazFor, "RazFor", "Razão social do fornecedor", ListaCampos.DB_SI, false ) );
		lcFor.montaSql( false, "FORNECEDOR", "RP" );
		lcFor.setQueryCommit( false );
		lcFor.setReadOnly( true );
		txtCodFor.setListaCampos( lcFor );
		txtCodFor.setTabelaExterna( lcFor );
		txtCodFor.setPK( true );
		txtCodFor.setNomeCampo( "CodFor" );
		
		Vector<String> labs = new Vector<String>();
		labs.add( "Completo" );
		labs.add( "Resumido" );
		Vector<String> vals = new Vector<String>();
		vals.add( "C" );
		vals.add( "R" );
		rgModo = new JRadioGroup( 1, 2, labs, vals );
		
		Vector<String> labs1 = new Vector<String>();
		labs1.add( "Código" );
		labs1.add( "Razão social" );
		Vector<String> vals1 = new Vector<String>();
		vals1.add( "CODFOR" );
		vals1.add( "RAZFOR" );
		rgOrdem = new JRadioGroup( 1, 2, labs1, vals1 );
		
		adic( new JLabel( "Modo :" ), 10, 10, 200, 20 );
		adic( rgModo, 10, 35, 290, 30 );
		adic( new JLabel( "Ordem do relatorio :" ), 10, 70, 200, 20 );
		adic( rgOrdem, 10, 95, 290, 30 );
		
		adic( new JLabel( "Cód.for." ), 10, 130, 77, 20 );
		adic( txtCodFor, 10, 150, 77, 20 );
		adic( new JLabel( "Razão social do fornecedor" ), 90, 130, 210, 20 );
		adic( txtRazFor, 90, 150, 210, 20 );
	}

	@ Override
	public void imprimir( boolean visualizar ) {

		try {
			
			String relatorio = "C".equals( rgModo.getVlrString() ) ? "rpfornecedorcomp.jasper" : "rpfornecedorresum.jasper";
			String modo = "C".equals( rgModo.getVlrString() ) ? "completo" : "resumido";
			
			StringBuilder sql = new StringBuilder();
			
			sql.append( "SELECT CODFOR,RAZFOR,NOMEFOR,CNPJFOR,INSCFOR,ENDFOR,CIDFOR,ESTFOR,CEPFOR,BAIRFOR,DDDFOR," );
			sql.append( "FONEFOR,FAXFOR,EMAILFOR,CODREPFOR " );
			sql.append( "FROM RPFORNECEDOR " );
			sql.append( "WHERE CODEMP=? AND CODFILIAL=? " );
			if ( txtRazFor.getVlrString().trim().length() > 0 ) {
				sql.append( "AND CODFOR=" + txtCodFor.getVlrInteger().intValue() );
			}
			sql.append( "ORDER BY " + rgOrdem.getVlrString() );
			
			PreparedStatement ps = con.prepareStatement( sql.toString() );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "RPFORNECEDOR" ) );
			ResultSet rs = ps.executeQuery();
			
			HashMap<String,Object> hParam = new HashMap<String, Object>();

			hParam.put( "CODEMP", Aplicativo.iCodEmp );
			//hParam.put( "SUBREPORT_DIR", RelFornecedor.class.getResource( "relatorios/" ).getPath() );
			hParam.put( "REPORT_CONNECTION", con );
			
			FPrinterJob dlGr = new FPrinterJob( "modulos/rep/relatorios/"+relatorio, "FORNECEDORES ( "+ modo +" )", null, rs, hParam, this );

			if ( visualizar ) {
				dlGr.setVisible( true );
			}
			else {
				JasperPrintManager.printReport( dlGr.getRelatorio(), true );
			}
			
			dispose();
		} catch ( Exception e ) {
			Funcoes.mensagemErro( this, "Erro ao montar relatorio!\n" + e.getMessage() );
			e.printStackTrace();
		}

	}

	public void setConexao( Connection cn ) {

		super.setConexao( cn );

		lcFor.setConexao( cn );
	}

}
