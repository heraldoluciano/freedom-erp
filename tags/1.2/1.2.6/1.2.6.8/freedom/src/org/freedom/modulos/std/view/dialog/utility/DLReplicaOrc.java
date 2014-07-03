/**
 * @version 03/09/2013 <BR>
 * @author Setpoint Informática Ltda./Robson Sanchez <BR>
 * 
 *         Projeto: Freedom <BR>
 * 
 *         Pacote: org.freedom.modulos.std.view.dialog.utility <BR>
 *         Classe: @(#)DLReplicaOrc.java <BR>
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
 *         Dialog responsável por selecionar a empresa para replicação de orçamento
 */
package org.freedom.modulos.std.view.dialog.utility;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.library.swing.component.JComboBoxPad;
import org.freedom.library.swing.component.JLabelPad;
import org.freedom.library.swing.dialog.FFDialogo;
import org.freedom.library.swing.frame.Aplicativo;

public class DLReplicaOrc extends FFDialogo {

	private static final long serialVersionUID = 1L;

	private Vector<String> vrazemp = null;
	
	private Vector<Integer> vcodemp = null;
	
	private JComboBoxPad cbEmpresa = null;
	
	private Integer codemp = new Integer(0); 

	public DLReplicaOrc( Component cOrig ) {
		super( cOrig );
		setTitulo( "Cópia de orçamento" );
		setAtribos( 340, 200 );
	}

	private void montaTela() {
		adic( new JLabelPad( "Empresa" ), 7, 5, 200, 20 );
		adic( cbEmpresa, 7, 25, 300, 20 );
	}
	
	public void actionPerformed( ActionEvent evt ) {
		if ( evt.getSource() == btOK ) {
			setCodemp(cbEmpresa.getVlrInteger());
		}
		super.actionPerformed( evt );
	}

	private void loadEmpresas() {
		StringBuilder sql = new StringBuilder();
		sql.append("select codemp, razemp from sgempresa where codemp<>?");
		vrazemp = new Vector<String>();
		vcodemp = new Vector<Integer>();
		PreparedStatement ps;
		try {
			ps = con.prepareStatement( sql.toString() );
			int param = 1;
			ps.setInt( param, Aplicativo.iCodEmp );
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				vrazemp.add( rs.getString( "RAZEMP" ) );
				vcodemp.add( rs.getInt( "CODEMP" ) );
			}
			rs.close();
			ps.close();
			con.commit();
			cbEmpresa = new JComboBoxPad(vrazemp, vcodemp, JComboBoxPad.TP_INTEGER, 10, 0);
		} catch ( SQLException e ) {
			e.printStackTrace();
		}
	}

	public void setConexao(DbConnection cn) {
		super.setConexao(cn);
		loadEmpresas();
		montaTela();
	}
	
	public Integer getCodemp() {
		return codemp;
	}

	public void setCodemp( Integer codemp ) {
		this.codemp = codemp;
	}
}
