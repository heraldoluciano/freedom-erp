package org.freedom.modulos.util;

import java.awt.Component;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.freedom.acao.CarregaEvent;
import org.freedom.acao.CarregaListener;
import org.freedom.acao.InsertEvent;
import org.freedom.acao.InsertListener;
import org.freedom.acao.PostEvent;
import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;

public class CtrlMultiComis {
    
	private int numComissionados = 1;
	private Connection con = null; 
	private ItemComis[] listComis = null;
	private String cpTipovenda = null;
	private String cpCodvenda = null;
	private JTextFieldPad txtTipovendaPrinc = null;
	private JTextFieldPad txtCodvendaPrinc = null;
	private JTextFieldPad txtCodvendPrinc = null;
	private ListaCampos lcMaster = null;
	private Component owner = null; 
	private String vendacomis = null;
	
	public CtrlMultiComis(final java.awt.Component owner, final Connection con, 
			final ListaCampos lcMaster,	final int numComissionados, 
			final String cpTipovenda, final String cpCodvenda, 
			final JTextFieldPad txtTipovenda, final JTextFieldPad txtCodvenda, 
			final JTextFieldPad txtCodvendPrinc, final String vendacomis) {
		this.con = con;
		this.cpTipovenda = cpTipovenda;
		this.cpCodvenda = cpCodvenda;
		this.txtTipovendaPrinc = txtTipovenda;
		this.txtCodvendaPrinc = txtCodvenda;
		this.numComissionados = numComissionados;
		this.txtCodvendPrinc = txtCodvendPrinc;
		this.owner = owner;
		this.vendacomis = vendacomis;
		this.lcMaster = lcMaster;
	}
	
	public class ItemComis implements InsertListener, CarregaListener {
		private int seqitrc = 0;
		private float perccomis = 0;
		private String obrigitrc = null;
		private JTextFieldPad txtTipovenda = null;
		private JTextFieldPad txtCodvenda = null;
		private JTextFieldPad txtSeqvc = null;
		private JTextFieldPad txtCodvend = null;
		private JTextFieldFK txtNomevend = null;
		private JTextFieldPad txtPerccomis = null;
		private JLabelPad lbCodvend = new JLabelPad("Cód.comis.");
		private JLabelPad lbNomevend = new JLabelPad("Nome do comissionado");
		private JLabelPad lbPercvend = new JLabelPad("% comis.");
		private ListaCampos lcVend = new ListaCampos(owner, "VD");
		private ListaCampos lcVendaComis = new ListaCampos(owner, "VC");
		public ItemComis() {
			lcVend.setConexao( con );
			lcVendaComis.setConexao( con );
			lcVendaComis.setMaster( lcMaster );
			lcMaster.adicDetalhe( lcVendaComis );
			txtTipovenda = new JTextFieldPad(JTextFieldPad.TP_STRING, 1, 0);
			txtCodvenda = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 10, 0);
			txtSeqvc = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 5, 0);
			txtCodvend = new JTextFieldPad(JTextFieldPad.TP_INTEGER, 8, 0);
			txtNomevend = new JTextFieldFK(JTextFieldPad.TP_STRING, 50, 0);
			txtPerccomis = new JTextFieldPad(JTextFieldPad.TP_NUMERIC, 7, Aplicativo.casasDecFin );
			txtCodvend.setNomeCampo( "codvend" );
			txtTipovenda.setNomeCampo( cpTipovenda );
			txtCodvenda.setNomeCampo( cpCodvenda );
			lcVend.add( new GuardaCampo( txtCodvend, "Codvend", "Cód.comis.", ListaCampos.DB_PK,  false ) );
			lcVend.add( new GuardaCampo( txtNomevend, "Nomevend", "Nome do comissionado", ListaCampos.DB_SI, false ) );
			lcVend.setWhereAdic( "ATIVOCOMIS='S'" );
			lcVend.montaSql( false, "VENDEDOR", "VD" );
			lcVend.setQueryCommit( false );
			lcVend.setReadOnly( true );
			txtCodvend.setTabelaExterna( lcVend );
			lcVendaComis.addInsertListener( this );
			//lcMaster.addPostListener( this );
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
			txtCodvend.setRequerido( "S".equals(obrigitrc) );
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
		
		public JTextFieldPad getTxtSeqvc() {
		
			return txtSeqvc;
		}
		
		public void setTxtSeqvc( JTextFieldPad txtSeqvc ) {
		
			this.txtSeqvc = txtSeqvc;
		}
		
		public ListaCampos getLcVendaComis() {
		
			return lcVendaComis;
		}
		
		public void setLcVendaComis( ListaCampos lcVendaComis ) {
		
			this.lcVendaComis = lcVendaComis;
		}
		
		public JTextFieldPad getTxtTipovenda() {
		
			return txtTipovenda;
		}
		
		public void setTxtTipovenda( JTextFieldPad txtTipovenda ) {
		
			this.txtTipovenda = txtTipovenda;
		}
		
		public JTextFieldPad getTxtCodvenda() {
		
			return txtCodvenda;
		}
		
		public void setTxtCodvenda( JTextFieldPad txtCodvenda ) {
		
			this.txtCodvenda = txtCodvenda;
		}
		
		public float getPerccomis() {
		
			return perccomis;
		}
		
		public void setPerccomis( float perccomis ) {
		
			this.perccomis = perccomis;
		}
		public void afterInsert( InsertEvent ievt ) {
			if ( ievt.getListaCampos()==lcVendaComis ) {
				txtPerccomis.setVlrBigDecimal( new BigDecimal(getPerccomis()) );
			}
		}
		public void beforeInsert( InsertEvent ievt ) {

		}
		public void afterPost( PostEvent pevt ) {
			if ( pevt.getListaCampos()==lcMaster) {
				salvaItens();
			}
		}
		public void beforePost( PostEvent pevt ) {
			if ( pevt.getListaCampos()==lcMaster ) {
				if (!validaItens()) {
					pevt.cancela();
					return;
				}
			}
		}
		public void afterCarrega( CarregaEvent cevt ) {
			
		}
		public void beforeCarrega( CarregaEvent cevt ) {
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
			ps = con.prepareStatement( "SELECT IR.SEQITRC, IR.OBRIGITRC, IR.PERCCOMISITRC " +
					"FROM VDITREGRACOMIS IR " +
					"WHERE IR.CODEMP=? AND IR.CODFILIAL=? AND IR.CODREGRCOMIS=? ");
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "VDITREGRACOMIS" ) );
			ps.setInt( 3, codregrcomis );
			rs = ps.executeQuery();
			while (rs.next()) {
				listComis[ pos ].setEnabled( true );
				listComis[ pos ].setSeqitrc( rs.getInt( "SEQITRC" ) );
				listComis[ pos ].setObrigitrc( rs.getString( "OBRIGITRC" ) );
				listComis[ pos ].setPerccomis( rs.getFloat( "PERCCOMISITRC" ) );
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

	public boolean validaItens() {
		boolean result = true;
		if (listComis!=null) {
			for (ItemComis itemcomis: listComis) {
				if ( (itemcomis!=null) ) { 
					if ( ("S".equals(itemcomis.getObrigitrc()) ) && 
						 (itemcomis.getTxtCodvend().isEnabled() ) ) {
						if ("".equals( itemcomis.getTxtCodvend().getVlrString() )) {
						    Funcoes.mensagemInforma( owner, "Preencha o comissionado!" );
						    itemcomis.getTxtCodvend().requestFocus();
							result = false;
							break;
						}
						if ("".equals( itemcomis.getTxtPerccomis().getVlrString() )) {
						    Funcoes.mensagemInforma( owner, "Preencha o % de comissao!" );
						    itemcomis.getTxtCodvend().requestFocus();
							result = false;
							break;
						}
					}
				}
			}
		}
		return result;
	}
	
	public void salvaItens() {
		if (listComis!=null) {
			for (ItemComis itemcomis: listComis) {
				if ( (itemcomis!=null) ) { 
					if ( ("S".equals(itemcomis.getObrigitrc()) ) && 
						 (itemcomis.getTxtCodvend().isEnabled() ) ) {
						if ( (itemcomis.getLcVendaComis().getStatus()==ListaCampos.LCS_EDIT) || 
							 (itemcomis.getLcVendaComis().getStatus()==ListaCampos.LCS_INSERT)) {
							itemcomis.getTxtTipovenda().setVlrString( lcMaster.getCampo( cpTipovenda ).getVlrString() );
							itemcomis.getTxtCodvend().setVlrInteger( lcMaster.getCampo( cpCodvenda ).getVlrInteger() );
							itemcomis.getTxtSeqvc().setVlrInteger( itemcomis.getSeqitrc() );
							itemcomis.getLcVendaComis().post();
						}
					}
				}
			}
		}
	}

}
