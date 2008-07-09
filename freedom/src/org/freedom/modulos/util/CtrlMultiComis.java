package org.freedom.modulos.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JTextField;

import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.telas.Aplicativo;

public class CtrlMultiComis {
    
	private int numComissionados = 1;
	private Connection con = null; 
	private ItemComis[] listComis = null;
	private JTextFieldPad txtCodvendPrinc;
	public CtrlMultiComis(final Connection con, final int numComissionados, final JTextFieldPad txtCodvendPrinc) {
		this.con = con;
		this.numComissionados = numComissionados;
		this.txtCodvendPrinc = txtCodvendPrinc;
	}
	
	public class ItemComis {
		private int seqitrc = 0;
		private String obrigitrc = null;
		private JTextFieldPad txtCodvend = null;
		public ItemComis() {
			txtCodvend = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);
		}
		public int getSeqitrc() {
			return seqitrc;
		}
		public void setSeqitrc( int seqitrc ) {
			this.seqitrc = seqitrc;
		}
		public String getObrigitrc() {
			return obrigitrc;
		}
		public void setObrigitrc( String obrigitrc ) {
			this.obrigitrc = obrigitrc;
		}
		public void setEnabled(final boolean enabled) {
			txtCodvend.setEnabled( enabled );
		}
		public void limpa() {
			txtCodvend.setVlrString( "" );
		}
	}
	
	public void loadRegraComis( final int codregrcomis) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		int pos = 0;
		if (listComis == null) {
			listComis = new ItemComis[numComissionados];
		}
		try {
			ps = con.prepareStatement( "SELECT SEQITRC, OBRIGITRC FROM VDITREGRACOMIS IR " +
					"WHERE IR.CODEMP=? AND IR.CODFILIAL=? AND IR.CODREGRCOMIS=? ");
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "VDITREGRACOMIS" ) );
			ps.setInt( 3, codregrcomis );
			rs = ps.executeQuery();
			while (rs.next()) {
				listComis[ pos ].setEnabled( true );
				listComis[ pos ].setSeqitrc( rs.getInt( "SEQITRC" ) );
				listComis[ pos ].setObrigitrc( rs.getString( "OBRIGITRC" ) );
				pos ++;
			}
			for (int i=pos; i<numComissionados; i++) {
				listComis[ i ].limpa();
				listComis[ i ].setEnabled( false );
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
