/**
 * @version 03/2007 <BR>
 * @author Setpoint Informática Ltda.<BR>
 * @author Alex Rodrigues<BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.rep <BR>
 * Classe:
 * @(#)RelVendedor.java <BR>
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
 * Relatorio de vendedores.
 * 
 */

package org.freedom.modulos.rep;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.JLabel;

import net.sf.jasperreports.engine.JasperPrintManager;

import org.freedom.componentes.JRadioGroup;
import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FPrinterJob;
import org.freedom.telas.FRelatorio;

public class RelVendedor extends FRelatorio {

	private static final long serialVersionUID = 1;
	
	private JRadioGroup rgOrdem;

	public RelVendedor() {

		super();
		setTitulo( "Relatorio de vendedores" );		
		setAtribos( 50, 50, 285, 150 );
		
		Vector<String> labs = new Vector<String>();
		labs.add( "Código" );
		labs.add( "Nome" );
		Vector<String> vals = new Vector<String>();
		vals.add( "CODVEND" );
		vals.add( "NOMEVEND" );
		rgOrdem = new JRadioGroup( 1, 2, labs, vals );
		
		adic( new JLabel( "Ordem do relatorio :" ), 10, 10, 200, 20 );
		adic( rgOrdem, 10, 35, 250, 30 );
	}

	@ Override
	public void imprimir( boolean visualizar ) {

		try {
			
			StringBuilder sql = new StringBuilder();
			
			sql.append( "SELECT CODVEND, NOMEVEND, ENDVEND, CIDVEND, CEPVEND, " );
			sql.append( "ESTVEND, DDDVEND, FONEVEND, FAXVEND, EMAILVEND, PERCCOMIS " ); 
			sql.append( "FROM RPVENDEDOR " );
			sql.append( "WHERE CODEMP=? AND CODFILIAL=? " );
			sql.append( "ORDER BY " + rgOrdem.getVlrString() );
			
			PreparedStatement ps = con.prepareStatement( sql.toString() );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "RPVENDEDOR" ) );
			ResultSet rs = ps.executeQuery();
			
			HashMap<String,Object> hParam = new HashMap<String, Object>();

			hParam.put( "CODEMP", Aplicativo.iCodEmp );
			hParam.put( "SUBREPORT_DIR", "/opt/freedom/reports/" );
			hParam.put( "REPORT_CONNECTION", con );
			
			FPrinterJob dlGr = new FPrinterJob( "modulos/rep/relatorios/rpvendedor.jasper", "VENDEDORES", null, rs, hParam, this );

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

}
