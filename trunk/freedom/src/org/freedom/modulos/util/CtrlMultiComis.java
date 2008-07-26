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

public class CtrlMultiComis implements CarregaListener {

    private int numComissionados = 1;

    private Connection con = null;

    private ItemComis[] listComis = null;

    private int codvenda = -1;

    private String tipovenda = "";

    private JTextFieldPad txtTipovendaPrinc = null;

    private JTextFieldPad txtCodvendaPrinc = null;

    private JTextFieldPad txtCodvendPrinc = null;

    private ListaCampos lcMaster = null;

    private Component owner = null;

    public CtrlMultiComis( final Component owner, 
	                   final Connection con, 
	                   final ListaCampos lcMaster, 
	                   final int numComissionados, 
	                   final JTextFieldPad txtTipovenda, 
	                   final JTextFieldPad txtCodvenda, 
	                   final JTextFieldPad txtCodvendPrinc ) {

	this.owner = owner;
	this.con = con;
	this.lcMaster = lcMaster;
	this.txtTipovendaPrinc = txtTipovenda;
	this.txtCodvendaPrinc = txtCodvenda;
	this.numComissionados = numComissionados;
	this.txtCodvendPrinc = txtCodvendPrinc;
	
	if ( lcMaster != null ) {
	    lcMaster.addCarregaListener( this );
	}
    }

    public boolean isEnabled() {
	
	boolean result = false;
	
	if ( listComis != null ) {
	    for ( ItemComis itemcomis : listComis ) {
		if ( ( itemcomis != null ) && ( itemcomis.getTxtCodvend().isEnabled() ) ) {
		    result = true;
		    break;
		}
	    }
	}
	return false;
    }

    public void loadVendaComis( final String tipovenda, final int codvenda, final int codregrcomis ) {
	
	if ( listComis == null ) {
	    
	    listComis = new ItemComis[ numComissionados ];
	    
	    for ( int i = 0; i < numComissionados; i++ ) {
		listComis[ i ] = new ItemComis();
	    }
	}
	
	try {
	    
	    PreparedStatement ps = con.prepareStatement( 
		    "SELECT VC.SEQVC, VC.CODREGRCOMIS, VC.SEQITRC, VC.CODVEND, VC.PERCVC " + 
		    "FROM VDVENDACOMIS VC " + 
		    "WHERE VC.CODEMP=? AND VC.CODFILIAL=? AND VC.TIPOVENDA=? AND VC.CODVENDA=?" );
	    ps.setInt( 1, Aplicativo.iCodEmp );
	    ps.setInt( 2, ListaCampos.getMasterFilial( "VDITREGRACOMIS" ) );
	    ps.setString( 3, tipovenda );
	    ps.setInt( 4, codvenda );

	    ResultSet rs = ps.executeQuery();

	    int pos = 0;
	    while ( rs.next() ) {
		listComis[ pos ].getTxtSeqvc().setVlrInteger( rs.getInt( "SEQVC" ) );
		listComis[ pos ].setSeqitrc( rs.getInt( "SEQITRC" ) );
		listComis[ pos ].getTxtTipovenda().setVlrString( lcMaster.getCampo( "TIPOVENDA" ).getVlrString() );
		listComis[ pos ].getTxtPerccomis().setVlrBigDecimal( rs.getBigDecimal( "PERCVC" ) );
		pos++;
	    }

	    for ( int i = pos; i < numComissionados; i++ ) {
		listComis[ i ].limpa();
	    }

	    for ( ItemComis itemcomis : listComis ) {
		if ( itemcomis != null ) {
		    // itemcomis.getLcVend().carregaDados();
		}
	    }

	} catch ( SQLException e ) {
	    e.printStackTrace();
	}
    }

    public ItemComis[] getListComis() {
	return this.listComis;
    }

    public void loadRegraComis( final int codregrcomis ) {
	
	if ( listComis == null ) {
	    
	    listComis = new ItemComis[ numComissionados ];
	    
	    for ( int i = 0; i < numComissionados; i++ ) {
		listComis[ i ] = new ItemComis();
	    }
	}
	try {
	    
	    PreparedStatement ps = con.prepareStatement( 
		    "SELECT IR.SEQITRC, IR.OBRIGITRC, IR.PERCCOMISITRC " + 
		    "FROM VDITREGRACOMIS IR " + 
		    "WHERE IR.CODEMP=? AND IR.CODFILIAL=? AND IR.CODREGRCOMIS=? " );
	   
	    ps.setInt( 1, Aplicativo.iCodEmp );
	    ps.setInt( 2, ListaCampos.getMasterFilial( "VDITREGRACOMIS" ) );
	    ps.setInt( 3, codregrcomis );
	   
	    ResultSet rs = ps.executeQuery();

	    int pos = 0;
	    while ( rs.next() ) {
		listComis[ pos ].setEnabled( true );
		listComis[ pos ].setSeqitrc( rs.getInt( "SEQITRC" ) );
		listComis[ pos ].setObrigitrc( rs.getString( "OBRIGITRC" ) );
		listComis[ pos ].setPerccomis( rs.getFloat( "PERCCOMISITRC" ) );
		pos++;
	    }
	    for ( int i = pos; i < numComissionados; i++ ) {
		listComis[ i ].limpa();
		listComis[ i ].setEnabled( false );
	    }

	} catch ( SQLException e ) {
	    e.printStackTrace();
	}
    }

    public boolean validaItens() {
	
	boolean result = true;
	
	if ( listComis != null ) {
	    
	    for ( ItemComis itemcomis : listComis ) {
		
		if ( ( itemcomis != null ) ) {
		    if ( ( "S".equals( itemcomis.getObrigitrc() ) ) && ( itemcomis.getTxtCodvend().isEnabled() ) ) {
			if ( "".equals( itemcomis.getTxtCodvend().getVlrString() ) ) {
			    Funcoes.mensagemInforma( owner, "Preencha o comissionado!" );
			    itemcomis.getTxtCodvend().requestFocus();
			    result = false;
			    break;
			}
			if ( "".equals( itemcomis.getTxtPerccomis().getVlrString() ) ) {
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

    public void salvaItens( final String tipovenda, final int codvenda ) {
	
	if ( listComis != null ) {
	    
	    for ( ItemComis itemcomis : listComis ) {
		
		if ( ( itemcomis != null ) ) {
		    if ( "S".equals( itemcomis.getObrigitrc() ) && itemcomis.getTxtCodvend().isEnabled() ) {
			if ( itemcomis.getLcVendaComis().getStatus() == ListaCampos.LCS_EDIT  
				|| itemcomis.getLcVendaComis().getStatus() == ListaCampos.LCS_INSERT ) {
			    itemcomis.getTxtTipovenda().setVlrString( tipovenda );
			    itemcomis.getTxtCodvend().setVlrInteger( codvenda );
			    itemcomis.getTxtSeqvc().setVlrInteger( itemcomis.getSeqitrc() );
			    itemcomis.getLcVendaComis().post();
			}
		    }
		}
	    }
	}
    }

    public void afterCarrega( CarregaEvent cevt ) { }

    public void beforeCarrega( CarregaEvent cevt ) { }

    public class ItemComis implements InsertListener, CarregaListener {
        
        private int seqitrc = 0;
    
        private float perccomis = 0;
    
        private String obrigitrc = null;
    
        private final JTextFieldPad txtTipovenda = new JTextFieldPad( JTextFieldPad.TP_STRING, 1, 0 );
    
        private final JTextFieldPad txtCodvenda = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 10, 0 );
    
        private final JTextFieldPad txtSeqvc = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 5, 0 );
    
        private final JTextFieldPad txtCodvend = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
    
        private final JTextFieldFK txtNomevend = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );
    
        private final JTextFieldPad txtPerccomis = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 7, Aplicativo.casasDecFin );
    
        private JLabelPad lbCodvend = new JLabelPad( "Cód.comis." );
    
        private JLabelPad lbNomevend = new JLabelPad( "Nome do comissionado" );
    
        private JLabelPad lbPercvend = new JLabelPad( "% comis." );
    
        private ListaCampos lcVend = new ListaCampos( owner, "VD" );
    
        private ListaCampos lcVendaComis = new ListaCampos( owner, "VC" );
    
        public ItemComis() {
            
            lcVendaComis.setMaster( lcMaster );
            lcMaster.adicDetalhe( lcVendaComis );
            
            txtTipovenda.setNomeCampo( "TIPOVENDA" );
            txtCodvenda.setNomeCampo( "CODVENDA" );
            txtSeqvc.setNomeCampo( "SEQVC" );
            txtPerccomis.setNomeCampo( "PERCVC" );
            
            txtCodvend.setNomeCampo( "codvend" );
            
            lcVend.add( new GuardaCampo( txtCodvend, "Codvend", "Cód.comis.", ListaCampos.DB_PK, false ) );
            lcVend.add( new GuardaCampo( txtNomevend, "Nomevend", "Nome do comissionado", ListaCampos.DB_SI, false ) );
            lcVend.add( new GuardaCampo( txtPerccomis, "Perccomis", "% Comiss.", ListaCampos.DB_SI, false ) );
            lcVend.setWhereAdic( "ATIVOCOMIS='S'" );
            lcVend.montaSql( false, "VENDEDOR", "VD" );
            lcVend.setQueryCommit( false );
            lcVend.setReadOnly( true );
            txtCodvend.setTabelaExterna( lcVend );
            
            lcVendaComis.addInsertListener( this );
            
            lcVend.setConexao( con );
            lcVendaComis.setConexao( con );
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
            if ( "S".equals( obrigitrc ) ) {
        	txtCodvend.setRequerido( true );
        	txtPerccomis.setRequerido( true );
            }
        }
    
        public void setEnabled( final boolean enabled ) {
            txtCodvend.setEnabled( enabled );
            txtPerccomis.setEnabled( enabled );
        }
    
        public void limpa() {
            txtCodvend.setVlrString( "" );
        }
    
        public JTextFieldPad getTxtCodvend() {
            return txtCodvend;
        }
    
        public JTextFieldFK getTxtNomevend() {
            return txtNomevend;
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
    
        public JLabelPad getLbPercvend() {
            return lbPercvend;
        }
    
        public void setLbPercvend( JLabelPad lbPercvend ) {
            this.lbPercvend = lbPercvend;
        }
    
        public JTextFieldPad getTxtSeqvc() {    
            return txtSeqvc;
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
    
        public JTextFieldPad getTxtCodvenda() {    
            return txtCodvenda;
        }
    
        public float getPerccomis() {    
            return perccomis;
        }
    
        public void setPerccomis( float perccomis ) {    
            this.perccomis = perccomis;
        }
    
        public void afterInsert( InsertEvent ievt ) {
            if ( ievt.getListaCampos() == lcVendaComis ) {
        	txtPerccomis.setVlrBigDecimal( new BigDecimal( getPerccomis() ) );
            }
        }
    
        public void beforeInsert( InsertEvent ievt ) { }
    
        public void afterPost( PostEvent pevt ) {
            if ( pevt.getListaCampos() == lcMaster ) {
            }
        }
    
        public void beforePost( PostEvent pevt ) {
            if ( pevt.getListaCampos() == lcMaster ) {
            }
        }
    
        public void afterCarrega( CarregaEvent cevt ) { }
    
        public void beforeCarrega( CarregaEvent cevt ) { }
    }

}
