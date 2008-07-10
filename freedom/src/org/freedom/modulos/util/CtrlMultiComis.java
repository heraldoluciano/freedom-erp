package org.freedom.modulos.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JTextFieldFK;
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
		private JTextFieldFK txtNomevend = null;
		private JTextFieldPad txtPerccomis = null;
		private JLabelPad lbCodvend = new JLabelPad("Cod.comis.");
		private JLabelPad lbNomevend = new JLabelPad("Nome do comissionado");
		private JLabelPad lbPercvend = new JLabelPad("% comis.");
		public ItemComis() {
			txtCodvend = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);
			txtNomevend = new JTextFieldFK(JTextFieldPad.TP_STRING, 50, 0);
			txtPerccomis = new JTextFieldPad(JTextFieldPad.TP_NUMERIC, 7, Aplicativo.casasDecFin );
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
			txtPerccomis.setEnabled( enabled );
		}
		public void limpa() {
			txtCodvend.setVlrString( "" );
		}
		public JTextFieldPad getTxtCodvend() {
			return txtCodvend;
		}
		public void setTxtCodvend( JTextFieldPad txtCodvend ) {
			this.txtCodvend = txtCodvend;
		}
		public JTextFieldFK getTxtNomevend() {
			return txtNomevend;
		}
		public void setTxtNomevend( JTextFieldFK txtNomevend ) {
			this.txtNomevend = txtNomevend;
		}
		public JLabelPad getLbCodvend() {
			return lbCodvend;
		}
		public void setLbCodvend( JLabelPad lbCodvend ) {
			this.lbCodvend = lbCodvend;
		}
		public JLabelPad getLbNomevend() {
			return lbNomevend;
		}
		public void setLbNomevend( JLabelPad lbNomevend ) {
			this.lbNomevend = lbNomevend;
		}
		public JTextFieldPad getTxtPerccomis() {
			return txtPerccomis;
		}
		public void setTxtPerccomis( JTextFieldPad txtPerccomis ) {
			this.txtPerccomis = txtPerccomis;
		}
		public JLabelPad getLbPercvend() {
			return lbPercvend;
		}
		public void setLbPercvend( JLabelPad lbPercvend ) {
			this.lbPercvend = lbPercvend;
		}
	}
	
	public ItemComis[] getListComis() {
		return this.listComis;
	}

	public void loadRegraComis( final int codregrcomis) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		int pos = 0;
		if (listComis == null) {
			listComis = new ItemComis[numComissionados];
			for (int i=0; i<numComissionados; i++) {
				listComis[ i ] = new ItemComis();
			}
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
