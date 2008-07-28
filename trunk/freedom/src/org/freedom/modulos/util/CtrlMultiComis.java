package org.freedom.modulos.util;

import java.awt.Component;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
    
    private boolean save = true;
    

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

    public boolean isSave() {
        return save;
    }

    public void setSave( boolean save ) {
        this.save = save;
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

    public void loadVendaComis( final String tipovenda, final int codvenda ) {
	
	if ( listComis == null ) {
	    
	    listComis = new ItemComis[ numComissionados ];
	    
	    for ( int i = 0; i < numComissionados; i++ ) {
		listComis[ i ] = new ItemComis();
	    }
	}
	
	try {
	    
	    PreparedStatement ps = con.prepareStatement( 
		    "SELECT VC.SEQVC FROM VDVENDACOMIS VC " + 
		    "WHERE VC.CODEMP=? AND VC.CODFILIAL=? AND VC.TIPOVENDA=? AND VC.CODVENDA=?" );
	    ps.setInt( 1, Aplicativo.iCodEmp );
	    ps.setInt( 2, ListaCampos.getMasterFilial( "VDITREGRACOMIS" ) );
	    ps.setString( 3, tipovenda );
	    ps.setInt( 4, codvenda );

	    ResultSet rs = ps.executeQuery();
	    
	    List<Integer> seqs = new ArrayList<Integer>();

	    while ( rs.next() ) {
		seqs.add( rs.getInt( "SEQVC" ) );
	    }
	    
	    rs.close();
	    ps.close();
	    
	    if ( !con.getAutoCommit() ) {
		con.commit();
	    }
	    
	    int pos = 0;
	    for ( Integer seqvc : seqs ) {
		listComis[ pos ].getTxtTipovenda().setVlrString( tipovenda );
		listComis[ pos ].getTxtCodvenda().setVlrInteger( codvenda );
		listComis[ pos ].getTxtSeqvc().setVlrInteger( seqvc );
		listComis[ pos ].getLcVendaComis().carregaDados();
		pos++;
	    }

	    for ( int i = pos; i < numComissionados; i++ ) {
		listComis[ i ].limpa();
		listComis[ i ].getLcVend().carregaDados();
	    }

	    for ( ItemComis itemcomis : listComis ) {
		if ( itemcomis != null ) {
		    itemcomis.getLcVend().carregaDados();
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
		listComis[ pos ].setCodregrcomis( codregrcomis );
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

    public void salvaItens( final String tipovenda, 
	                    final int codvenda ) {
	
	if ( listComis != null ) {
	    for ( ItemComis itemcomis : listComis ) {		
		if ( ( itemcomis != null ) ) {
		    
		    if ( "S".equals( itemcomis.getObrigitrc() ) && itemcomis.getTxtCodvend().isEnabled() ) {
			if ( itemcomis.getLcVendaComis().getStatus() == ListaCampos.LCS_EDIT  
				|| itemcomis.getLcVendaComis().getStatus() == ListaCampos.LCS_INSERT ) {
			    itemcomis.getTxtTipovenda().setVlrString( tipovenda );
			    itemcomis.getTxtCodvenda().setVlrInteger( codvenda );
			    itemcomis.getTxtSeqvc().setVlrInteger( itemcomis.getSeqitrc() );

			    /*System.out.println( "codvenda  = " + itemcomis.getTxtCodvenda().getVlrString() );
			    System.out.println( "tipovenda = " + itemcomis.getTxtTipovenda().getVlrString() );
			    System.out.println( "sequencia = " + itemcomis.getTxtSeqvc().getVlrString() );
			    System.out.println( "codregra  = " + itemcomis.getTxtCodRegra().getVlrString() );
			    System.out.println( "seqregra  = " + itemcomis.getTxtSeqRegra().getVlrString() );
			    System.out.println( "codvend   = " + itemcomis.getTxtCodvend().getVlrString() );
			    System.out.println( "nomevend  = " + itemcomis.getTxtNomevend().getVlrString() );
			    System.out.println( "perccomis = " + itemcomis.getTxtPerccomis().getVlrString() );*/
			    
			    setSave( itemcomis.getLcVendaComis().post() );
			}
		    }
		}
	    }
	}
    }

    public void afterCarrega( CarregaEvent cevt ) { }

    public void beforeCarrega( CarregaEvent cevt ) { }

    public class ItemComis implements InsertListener, CarregaListener {
        
        private int codregrcomis = 0;
        
        private int seqitrc = 0;
    
        private float perccomis = 0;
    
        private String obrigitrc = null;
    
        private final JTextFieldPad txtTipovenda = new JTextFieldPad( JTextFieldPad.TP_STRING, 1, 0 );
    
        private final JTextFieldPad txtCodvenda = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 10, 0 );
    
        private final JTextFieldPad txtSeqvc = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 5, 0 );
    
        private final JTextFieldPad txtCodvend = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
    
        private final JTextFieldFK txtNomevend = new JTextFieldFK( JTextFieldPad.TP_STRING, 50, 0 );
    
        private final JTextFieldPad txtCodRegra = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
    
        private final JTextFieldPad txtSeqRegra = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
    
        private final JTextFieldPad txtPerccomis = new JTextFieldPad( JTextFieldPad.TP_NUMERIC, 7, Aplicativo.casasDecFin );
    
        private final JLabelPad lbCodvend = new JLabelPad( "Cód.comis." );
    
        private final JLabelPad lbNomevend = new JLabelPad( "Nome do comissionado" );
    
        private final JLabelPad lbPercvend = new JLabelPad( "% comis." );
    
        private final ListaCampos lcVend = new ListaCampos( owner, "VD" );
    
        private final ListaCampos lcRegraComis = new ListaCampos( owner, "RC" );
    
        private final ListaCampos lcVendaComis = new ListaCampos( owner, "VC" );
    
        public ItemComis() {
            
            txtTipovenda.setNomeCampo( "TIPOVENDA" );
            txtCodvenda.setNomeCampo( "CODVENDA" );
            txtSeqvc.setNomeCampo( "SEQVC" );
            txtPerccomis.setNomeCampo( "PERCVC" );
            
            txtCodvend.setNomeCampo( "CODVEND" );
            txtCodvend.setFK( true );
            lcVend.add( new GuardaCampo( txtCodvend, "CodVend", "Cód.comis.", ListaCampos.DB_PK, false ) );
            lcVend.add( new GuardaCampo( txtNomevend, "NomeVend", "Nome do comissionado", ListaCampos.DB_SI, false ) );
            lcVend.add( new GuardaCampo( txtPerccomis, "PercComVend", "% Comiss.", ListaCampos.DB_SI, false ) );
            lcVend.setWhereAdic( "ATIVOCOMIS='S'" );
            lcVend.montaSql( false, "VENDEDOR", "VD" );
            lcVend.setQueryCommit( false );
            lcVend.setPodeIns( false );
            lcVend.setReadOnly( true );
            txtCodvend.setTabelaExterna( lcVend );
            
            txtCodRegra.setNomeCampo( "CODREGRCOMIS" );
            txtSeqRegra.setNomeCampo( "SEQITRC" );
            lcRegraComis.add( new GuardaCampo( txtCodRegra, "CodRegrComis", "Cód.rg.comis.", ListaCampos.DB_PK, false ) );
            txtSeqRegra.add( new GuardaCampo( txtSeqRegra, "SeqItRc", "Seq.rg.comis.", ListaCampos.DB_PK, false ) );
            lcRegraComis.montaSql( false, "ITREGRACOMIS", "VD" );
            lcRegraComis.setQueryCommit( false );
            lcRegraComis.setPodeIns( false );
            lcRegraComis.setReadOnly( true );
            txtCodRegra.setTabelaExterna( lcRegraComis );
            txtSeqRegra.setTabelaExterna( lcRegraComis );
            
            lcVendaComis.setMaster( lcMaster );
            lcMaster.adicDetalhe( lcVendaComis );
            
            lcVendaComis.setConexao( con );
            lcVend.setConexao( con );
            lcRegraComis.setConexao( con );
            
            lcVendaComis.addInsertListener( this );
            lcVend.addCarregaListener( this );
        }
    
        public int getCodregrcomis() {
            return codregrcomis;
        }
    
        public void setCodregrcomis( int codregrcomis ) {
            this.codregrcomis = codregrcomis;
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
    
        public JTextFieldPad getTxtCodRegra() {
	    return txtCodRegra;
	}

	public JTextFieldPad getTxtSeqRegra() {
	    return txtSeqRegra;
	}

	public JLabelPad getLbCodvend() {
            return lbCodvend;
        }
    
        public JLabelPad getLbNomevend() {
            return lbNomevend;
        }
    
        public JTextFieldPad getTxtPerccomis() {
            return txtPerccomis;
        }
    
        public JLabelPad getLbPercvend() {
            return lbPercvend;
        }
    
        public JTextFieldPad getTxtSeqvc() {    
            return txtSeqvc;
        }
    
        public ListaCampos getLcVendaComis() {    
            return lcVendaComis;
        }
    
        public ListaCampos getLcVend() {    
            return lcVend;
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
    
        public void afterInsert( InsertEvent e ) {
           /* if ( e.getListaCampos() == lcVendaComis ) {
        	txtPerccomis.setVlrBigDecimal( new BigDecimal( getPerccomis() ) );
            }*/
        }
    
        public void beforeInsert( InsertEvent e ) { }
    
        public void afterPost( PostEvent e ) {
            if ( e.getListaCampos() == lcMaster ) {
            }
        }
    
        public void beforePost( PostEvent e ) {
            if ( e.getListaCampos() == lcMaster ) {
            }
        }
    
        public void afterCarrega( CarregaEvent e ) {
            if ( e.getListaCampos() == lcVend ) {
        	txtPerccomis.setVlrBigDecimal( new BigDecimal( getPerccomis() ) );
        	
        	txtCodRegra.setVlrInteger( codregrcomis );
        	txtSeqRegra.setVlrInteger( seqitrc );
        	lcRegraComis.carregaDados();
            }
        }
    
        public void beforeCarrega( CarregaEvent e ) { }
    }

}
