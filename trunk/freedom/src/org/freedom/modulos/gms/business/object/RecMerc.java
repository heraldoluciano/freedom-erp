package org.freedom.modulos.gms.business.object;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.SwingConstants;

import org.freedom.bmps.Icone;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.infra.pojos.Constant;
import org.freedom.library.functions.Funcoes;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.component.JLabelPad;
import org.freedom.library.swing.frame.Aplicativo;
import org.freedom.modulos.fnc.view.dialog.utility.DLInfoPlanoPag;
import org.freedom.modulos.gms.view.dialog.utility.DLInfoVendedor;
import org.freedom.modulos.std.business.component.Orcamento;

public class RecMerc implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	// Status do recebimento

	public static final Constant STATUS_NAO_SALVO = new Constant( "Não Salvo", null );

	public static final Color COR_NAO_SALVO = Color.GRAY;

	public static final Constant STATUS_PENDENTE = new Constant( "Pendente", "PE" );

	public static final Color COR_PENDENTE = Color.ORANGE;

	public static final Constant STATUS_PESAGEM_1 = new Constant( "1a Pesagem", "E1" );

	public static final Color COR_PESAGEM_1 = Color.BLUE;

	public static final Constant STATUS_DESCARREGAMENTO = new Constant( "Descarregamento", "E2" );

	public static final Color COR_DESCARREGAMENTO = Color.BLUE;

	public static final Constant STATUS_RECEBIMENTO_FINALIZADO = new Constant( "Finalizado", "FN" );

	public static final Color COR_RECEBIMENTO_FINALIZADO = new Color( 45, 190, 60 );

	public static final Constant STATUS_PEDIDO_COMPRA_EMITIDO = new Constant( "Pedido emitido", "PC" );

	public static final Color COR_PEDIDO_COMPRA_EMITIDO = new Color( 45, 190, 60 );

	public static final Constant STATUS_NOTA_ENTRADA_EMITIDA = new Constant( "Nota emitida", "NE" );

	public static final Color COR_NOTA_ENTRADA_EMITIDA = new Color( 45, 190, 60 );

	public static String IMG_TAMANHO_M = "16x16";

	public static String IMG_TAMANHO_P = "10x10";

	private HashMap<String, Object> primeirapesagem = null;

	private HashMap<String, Object> segundapesagem = null;

	private HashMap<String, Object> rendapesagem = null;

	private Integer ticket = null;

	private Integer codfor = null;

	private Integer codcli = null;

	private Integer codvend = null;

	private Integer codtipomov = null;

	private DbConnection con = null;

	private Component orig = null;

	private String serie = null;

	private Integer docserie = null;

	private String tipofrete = null;

	private Integer codcompra = null;

	private Integer codfrete = null;

	private Integer coddestinat = null;

	private Integer codremet = null;
	
	private Integer codtran = null;

	private Integer codorc = null;

	private Object[] oPrefs = null;
	
	private Date dtent = null;
	
	private BigDecimal precopeso = null;

	public static ImageIcon getImagem( String status, String tamanho ) {

		ImageIcon img = null;

		ImageIcon IMG_PENDENTE = Icone.novo( "blAzul0_" + tamanho + ".png" );

		ImageIcon IMG_PESAGEM1 = Icone.novo( "blAzul1_" + tamanho + ".png" );

		ImageIcon IMG_DESCARREGAMENTO = Icone.novo( "blAzul2_" + tamanho + ".png" );

		ImageIcon IMG_FINALIZADO = Icone.novo( "os_pronta_" + tamanho + ".png" );

		ImageIcon IMG_PEDIDO = Icone.novo( "os_orcamento_" + tamanho + ".png" );

		ImageIcon IMG_NOTA = Icone.novo( "os_finalizada_" + tamanho + ".png" );

		try {

			if ( status.equals( STATUS_PENDENTE.getValue() ) ) {
				return IMG_PENDENTE;
			}
			else if ( status.equals( STATUS_PESAGEM_1.getValue() ) ) {
				return IMG_PESAGEM1;
			}
			else if ( status.equals( STATUS_DESCARREGAMENTO.getValue() ) ) {
				return IMG_DESCARREGAMENTO;
			}
			else if ( status.equals( STATUS_RECEBIMENTO_FINALIZADO.getValue() ) ) {
				return IMG_FINALIZADO;
			}
			else if ( status.equals( STATUS_PEDIDO_COMPRA_EMITIDO.getValue() ) ) {
				return IMG_PEDIDO;
			}
			else if ( status.equals( STATUS_NOTA_ENTRADA_EMITIDA.getValue() ) ) {
				return IMG_NOTA;
			}

		} catch ( Exception e ) {
			e.printStackTrace();
		}

		return img;
	}

	public RecMerc( Component orig, Integer ticket, DbConnection con ) {

		setTicket( ticket );
		setConexao( con );
		setOrig( orig );

		CarregaRecMerc();

		buscaPesagens();

	}

	private void buscaPesagens() {

		buscaPrimeiraPesagem();
		buscaSegundaPesagem();
		buscaRenda();

	}

	private void geraPrefereOrc() {

		oPrefs = Orcamento.getPrefere();
	}

	public static void atualizaStatus( String status, JLabelPad lbstatus ) {

		lbstatus.setForeground( Color.WHITE );
		lbstatus.setFont( new Font( "Arial", Font.BOLD, 13 ) );
		lbstatus.setOpaque( true );
		lbstatus.setHorizontalAlignment( SwingConstants.CENTER );

		if ( status == STATUS_NAO_SALVO.getValue() ) {
			lbstatus.setText( STATUS_PENDENTE.getName() );
			lbstatus.setBackground( COR_NAO_SALVO );
		}
		else if ( STATUS_PENDENTE.getValue().equals( status ) ) {
			lbstatus.setText( STATUS_PENDENTE.getName() );
			lbstatus.setBackground( COR_PENDENTE );
		}
		else if ( STATUS_PESAGEM_1.getValue().equals( status ) ) {
			lbstatus.setText( STATUS_PESAGEM_1.getName() );
			lbstatus.setBackground( COR_PESAGEM_1 );
		}
		else if ( STATUS_DESCARREGAMENTO.getValue().equals( status ) ) {
			lbstatus.setText( STATUS_DESCARREGAMENTO.getName() );
			lbstatus.setBackground( COR_DESCARREGAMENTO );
		}
		else if ( STATUS_RECEBIMENTO_FINALIZADO.getValue().equals( status ) ) {
			lbstatus.setText( STATUS_RECEBIMENTO_FINALIZADO.getName() );
			lbstatus.setBackground( COR_RECEBIMENTO_FINALIZADO );
		}
		else if ( STATUS_PEDIDO_COMPRA_EMITIDO.getValue().equals( status ) ) {
			lbstatus.setText( STATUS_PEDIDO_COMPRA_EMITIDO.getName() );
			lbstatus.setBackground( COR_PEDIDO_COMPRA_EMITIDO );
		}
		else if ( STATUS_NOTA_ENTRADA_EMITIDA.getValue().equals( status ) ) {
			lbstatus.setText( STATUS_NOTA_ENTRADA_EMITIDA.getName() );
			lbstatus.setBackground( COR_NOTA_ENTRADA_EMITIDA );
		}

	}

	public static Vector<String> getLabels() {

		Vector<String> ret = new Vector<String>();

		ret.add( STATUS_PENDENTE.getName() );
		ret.add( STATUS_PESAGEM_1.getName() );
		ret.add( STATUS_DESCARREGAMENTO.getName() );
		ret.add( STATUS_RECEBIMENTO_FINALIZADO.getName() );
		ret.add( STATUS_PEDIDO_COMPRA_EMITIDO.getName() );
		ret.add( STATUS_NOTA_ENTRADA_EMITIDA.getName() );

		return ret;

	}

	public static Vector<Object> getValores() {

		Vector<Object> ret = new Vector<Object>();

		ret.add( STATUS_PENDENTE.getValue() );
		ret.add( STATUS_PESAGEM_1.getValue() );
		ret.add( STATUS_DESCARREGAMENTO.getValue() );
		ret.add( STATUS_RECEBIMENTO_FINALIZADO.getValue() );
		ret.add( STATUS_PEDIDO_COMPRA_EMITIDO.getValue() );
		ret.add( STATUS_NOTA_ENTRADA_EMITIDA.getValue() );

		return ret;

	}

	private void buscaPrimeiraPesagem() {

		HashMap<String, Object> pesagem = null;
		StringBuilder sql = new StringBuilder();

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			pesagem = new HashMap<String, Object>();

			sql.append( "select first 1 a.pesoamost peso, a.dataamost data, a.horaamost hora, pd.codunid, a.seqamostragem " );
			sql.append( "from eqrecamostragem a, eqitrecmerc i, eqprocrecmerc p, eqproduto pd " );
			sql.append( "where " );
			sql.append( "a.codemp=i.codemp and a.codfilial=i.codfilial and a.ticket=i.ticket and a.coditrecmerc=i.coditrecmerc " );
			sql.append( "and i.codemp=? and i.codfilial=? and i.ticket=? " );
			sql.append( "and p.codemp=i.codemptp and p.codfilial=i.codfilialtp and p.codprocrecmerc=i.codprocrecmerc and p.tipoprocrecmerc=? " );
			sql.append( "and pd.codemp=i.codemppd and pd.codfilial=i.codfilialpd and pd.codprod=i.codprod " );
			sql.append( "order by a.dataamost desc, a.codamostragem desc" );

			ps = con.prepareStatement( sql.toString() );

			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "EQRECMERC" ) );
			ps.setInt( 3, getTicket() );
			ps.setString( 4, (String) TipoRecMerc.PROCESSO_PESAGEM_INICIAL.getValue() );

			rs = ps.executeQuery();

			if ( rs.next() ) {

				pesagem.put( "peso", rs.getBigDecimal( "peso" ) );
				pesagem.put( "data", Funcoes.dateToStrDate( rs.getDate( "data" ) ) );
				pesagem.put( "hora", rs.getString( "hora" ) );
				pesagem.put( "unid", rs.getString( "codunid" ).trim() );
				pesagem.put( "interno", rs.getString( "seqamostragem" ) );

			}

//			con.commit();

		} catch ( Exception e ) {
			e.printStackTrace();
		}
		setPrimeirapesagem( pesagem );
	}

	private void buscaSegundaPesagem() {

		HashMap<String, Object> pesagem = null;
		StringBuilder sql = new StringBuilder();

		PreparedStatement ps = null;
		ResultSet rs = null;

		try { 

			pesagem = new HashMap<String, Object>();

			sql.append( "select first 1 a.pesoamost peso, a.dataamost data, a.horaamost hora, pd.codunid " );
			sql.append( "from eqrecamostragem a, eqitrecmerc i, eqprocrecmerc p, eqproduto pd " );
			sql.append( "where " );
			sql.append( "a.codemp=i.codemp and a.codfilial=i.codfilial and a.ticket=i.ticket and a.coditrecmerc=i.coditrecmerc " );
			sql.append( "and i.codemp=? and i.codfilial=? and i.ticket=? " );
			sql.append( "and p.codemp=i.codemptp and p.codfilial=i.codfilialtp and p.codprocrecmerc=i.codprocrecmerc and p.tipoprocrecmerc=?  and p.codtiporecmerc=i.codtiporecmerc " );
			sql.append( "and pd.codemp=i.codemppd and pd.codfilial=i.codfilialpd and pd.codprod=i.codprod " );
			sql.append( "order by a.dataamost, a.codamostragem desc" );

			ps = con.prepareStatement( sql.toString() );

			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "EQRECMERC" ) );
			ps.setInt( 3, getTicket() );
			ps.setString( 4, (String) TipoRecMerc.PROCESSO_PESAGEM_FINAL.getValue() );

			rs = ps.executeQuery();

			if ( rs.next() ) {

				pesagem.put( "peso", rs.getBigDecimal( "peso" ) );
				pesagem.put( "data", Funcoes.dateToStrDate( rs.getDate( "data" ) ) );
				pesagem.put( "hora", rs.getString( "hora" ) );
				pesagem.put( "unid", rs.getString( "codunid" ).trim() );

			}

//			con.commit();

		} catch ( Exception e ) {
			e.printStackTrace();
		}
		setSegundapesagem( pesagem );
	}

	private void buscaRenda() {

		HashMap<String, Object> pesagem = null;
		StringBuilder sql = new StringBuilder();

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			pesagem = new HashMap<String, Object>();

			sql.append( "select first 1 i.mediaamostragem media, i.rendaamostragem renda " );
			sql.append( "from eqitrecmerc i, eqprocrecmerc p " );
			sql.append( "where " );
			sql.append( "i.codemp=? and i.codfilial=? and i.ticket=? " );
			sql.append( "and p.codemp=i.codemptp and p.codfilial=i.codfilialtp and p.codprocrecmerc=i.codprocrecmerc and p.tipoprocrecmerc=? " );
			sql.append( "order by i.coditrecmerc desc" );

			ps = con.prepareStatement( sql.toString() );

			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "EQRECMERC" ) );
			ps.setInt( 3, getTicket() );
			ps.setString( 4, (String) TipoRecMerc.PROCESSO_DESCARREGAMENTO.getValue() );

			rs = ps.executeQuery();

			if ( rs.next() ) {

				pesagem.put( "media", rs.getBigDecimal( "media" ) );
				pesagem.put( "renda", rs.getString( "renda" ) );

			}

//			con.commit();

		} catch ( Exception e ) {
			e.printStackTrace();
		}
		setRendapesagem( pesagem );
	}

	public HashMap<String, Object> getPrimeirapesagem() {

		return primeirapesagem;
	}

	public void setPrimeirapesagem( HashMap<String, Object> primeirapesagem ) {

		this.primeirapesagem = primeirapesagem;
	}

	public HashMap<String, Object> getSegundapesagem() {

		return segundapesagem;
	}

	public void setSegundapesagem( HashMap<String, Object> segundapesagem ) {

		this.segundapesagem = segundapesagem;
	}

	public HashMap<String, Object> getRendapesagem() {

		return rendapesagem;
	}

	public void setRendapesagem( HashMap<String, Object> rendapesagem ) {

		this.rendapesagem = rendapesagem;
	}

	public DbConnection getCon() {

		return con;
	}

	public void setCon( DbConnection con ) {

		this.con = con;
	}

	public Integer getTicket() {

		return ticket;
	}

	private void setConexao( DbConnection con ) {

		this.con = con;
	}

	private void setTicket( Integer ticket ) {

		this.ticket = ticket;
	}

	private void geraCodCompra() {

		StringBuilder sql = new StringBuilder();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Integer codcompra = 1;

		try {

			sql.append( "select coalesce(max(codcompra),0) + 1 from cpcompra " );
			sql.append( "where codemp=? and codfilial=? " );

			ps = con.prepareStatement( sql.toString() );

			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "CPCOMPRA" ) );

			rs = ps.executeQuery();

			if ( rs.next() ) {
				codcompra = rs.getInt( 1 );
			}

		} catch ( Exception e ) {
			e.printStackTrace();
		}

		setCodcompra( codcompra );

	}
	
	

	private void geraCodFrete() {

		StringBuilder sql = new StringBuilder();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Integer codfrete = 1;

		try {

			sql.append( "select coalesce(max(codfrete),0) + 1 from lffrete " );
			sql.append( "where codemp=? and codfilial=? " );

			ps = con.prepareStatement( sql.toString() );

			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "LFFRETE" ) );

			rs = ps.executeQuery();

			if ( rs.next() ) {
				codfrete = rs.getInt( 1 );
			}

		} catch ( Exception e ) {
			e.printStackTrace();
		}

		setCodfrete( codfrete );

	}


	private void geraCodOrc() {

		StringBuilder sql = new StringBuilder();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Integer codorc = 1;

		try {

			sql.append( "select coalesce(max(codorc),0) + 1 from vdorcamento " );
			sql.append( "where codemp=? and codfilial=? " );

			ps = con.prepareStatement( sql.toString() );

			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "VDORCAMENTO" ) );

			rs = ps.executeQuery();

			if ( rs.next() ) {
				codorc = rs.getInt( 1 );
			}

		} catch ( Exception e ) {
			e.printStackTrace();
		}

		setCodorc( codorc );

	}

	private void geraCodVend() {

		StringBuilder sql = new StringBuilder();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Integer codorc = 1;

		try {

			sql.append( "select codvend from vdcliente " );
			sql.append( "where codemp=? and codfilial=? and codcli=?" );

			ps = con.prepareStatement( sql.toString() );

			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "VDCLIENTE" ) );
			ps.setInt( 3, getCodcli() );

			rs = ps.executeQuery();

			if ( rs.next() ) {
				codvend = rs.getInt( 1 );
			}

			if ( codvend == null ) {
				getCodVendTela();
			}

		} catch ( Exception e ) {
			e.printStackTrace();
		}

		setCodvend( codvend );

	}

	private void geraCodTipoMovCP() {

		StringBuilder sql = new StringBuilder();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Integer codorc = 1;

		try {

			sql.append( "select codtipomov from sgprefere8 " );
			sql.append( "where codemp=? and codfilial=? " );

			ps = con.prepareStatement( sql.toString() );

			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "SGPREFERE1" ) );

			rs = ps.executeQuery();

			if ( rs.next() ) {
				codtipomov = rs.getInt( 1 );
			}

		} catch ( Exception e ) {
			e.printStackTrace();
		}

		setCodtipomov( codtipomov );

	}
	
	private void geraCodTipoMovOrc() {

		StringBuilder sql = new StringBuilder();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Integer codorc = 1;

		try {

			sql.append( "select codtipomov2 from sgprefere1 " );
			sql.append( "where codemp=? and codfilial=? " );

			ps = con.prepareStatement( sql.toString() );

			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "SGPREFERE1" ) );

			rs = ps.executeQuery();

			if ( rs.next() ) {
				codtipomov = rs.getInt( 1 );
			}

		} catch ( Exception e ) {
			e.printStackTrace();
		}

		setCodtipomov( codtipomov );

	}

	
	private void CarregaRecMerc() {

		StringBuilder sql = new StringBuilder();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Integer codtipomov = null;
		String serietipomov = null;
		String seqserietipomov = null;

		try {

			sql.append( "select rm.tipofrete ,rm.codfor, tm.codtipomov, tm.serie, coalesce(ss.docserie,0) docserie " );
			sql.append( ", rm.codcli, fr.codunifcod codremet, fi.codunifcod coddestinat, rm.codtran, rm.dtent, coalesce((br.vlrfrete/coalesce(br.qtdfrete,1)),0) vlrfrete " );			

			sql.append( "from eqrecmerc rm left outer join eqtiporecmerc tr on " );
			sql.append( "tr.codemp=rm.codemp and tr.codfilial=rm.codfilial and tr.codtiporecmerc=rm.codtiporecmerc " );
			
			sql.append( "left outer join cpforneced fr on " );
			sql.append( "fr.codemp=rm.codempfr and fr.codfilial=rm.codfilialfr and fr.codfor=rm.codfor " );
			
			sql.append( "left outer join sgfilial fi on " );
			sql.append( "fi.codemp=rm.codemp and fi.codfilial=rm.codfilial " );
			
			sql.append( "left outer join sgbairro br on " );
			sql.append( "br.codpais=rm.codpais and br.siglauf=rm.siglauf and br.codmunic=rm.codmunic and br.codbairro=rm.codbairro " );
			
			
			sql.append( "left outer join eqtipomov tm on " );
			sql.append( "tm.codemp=tr.codemptc and tm.codfilial=tr.codfilialtc and tm.codtipomov=tr.codtipomovcp " );

			sql.append( "left outer join lfseqserie ss " );
			sql.append( "on ss.codemp=tm.codempse and ss.codfilial=tm.codfilialse and ss.serie=tm.serie and " );
			sql.append( "codempss=? and codfilialss=? and ativserie='S' " );
			sql.append( "where rm.codemp=? and rm.codfilial=? and rm.ticket=? " );

			ps = con.prepareStatement( sql.toString() );

			System.out.println( "SQL:" + sql.toString() );

			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "LFSEQSERIE" ) );
			ps.setInt( 3, Aplicativo.iCodEmp );
			ps.setInt( 4, ListaCampos.getMasterFilial( "EQRECMERC" ) );
			ps.setInt( 5, getTicket() );

			rs = ps.executeQuery();

			if ( rs.next() ) {
				setCodtipomov( rs.getInt( "codtipomov" ) );
				setSerie( rs.getString( "serie" ) );
				setDocserie( rs.getInt( "docserie" ) );
				setTipofrete( rs.getString( "tipofrete" ) );
				setCodfor( rs.getInt( "codfor" ) );
				setCodcli( rs.getInt( "codcli" ) );
				setCodremet( rs.getInt("codremet") );
				setCoddestinat( rs.getInt("coddestinat") );
				setCodtran( rs.getInt("codtran") );
				setDtent( Funcoes.sqlDateToDate( rs.getDate( "dtent" )) );
				setPrecopeso( rs.getBigDecimal( "vlrfrete" ) );
			}

//			con.commit();

		} catch ( Exception e ) {
			Funcoes.mensagemErro( orig, "Erro ao buscar informações do recebimento de mercadorias!", true, con, e );

			e.printStackTrace();
		}

	}

	public Integer geraCompra() {

		StringBuilder sql = new StringBuilder();

		Integer ticket = null;
		BigDecimal pesoliq = null;
		BigDecimal peso1 = null;
		BigDecimal peso2 = null;
		String unid = null;
		PreparedStatement ps = null;
		Integer codplanopag = null;

		try {

			geraCodCompra();

			codplanopag = getPlanoPag();

			if ( codplanopag == null ) {
				return null;
			}

			sql.append( "insert into cpcompra (" );
			sql.append( "codemp, codfilial, codcompra, " );
			sql.append( "codemppg, codfilialpg, codplanopag, " );
			sql.append( "codempfr, codfilialfr, codfor, " );
			sql.append( "codempse, codfilialse, serie, doccompra, " );
			sql.append( "codemptm, codfilialtm, codtipomov, " );
			sql.append( "dtentcompra, dtemitcompra, tipofretecompra," );
			sql.append( "codemptn, codfilialtn, codtran, codemprm, codfilialrm, ticket " );
			sql.append( ") values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)" );

			ps = con.prepareStatement( sql.toString() );

			int param = 1;

			ps.setInt( param++, Aplicativo.iCodEmp );
			ps.setInt( param++, ListaCampos.getMasterFilial( "CPCOMPRA" ) );
			ps.setInt( param++, getCodcompra() );

			ps.setInt( param++, Aplicativo.iCodEmp );
			ps.setInt( param++, ListaCampos.getMasterFilial( "VDPLANOPAG" ) );
			ps.setInt( param++, codplanopag );

			ps.setInt( param++, Aplicativo.iCodEmp );
			ps.setInt( param++, ListaCampos.getMasterFilial( "CPFORNECED" ) );
			ps.setInt( param++, getCodfor() );

			ps.setInt( param++, Aplicativo.iCodEmp );
			ps.setInt( param++, ListaCampos.getMasterFilial( "LFSEQSERIE" ) );
			ps.setString( param++, getSerie() );
			ps.setInt( param++, getDocserie() );

			ps.setInt( param++, Aplicativo.iCodEmp );
			ps.setInt( param++, ListaCampos.getMasterFilial( "EQTIPOMOV" ) );
			ps.setInt( param++, getCodtipomov() );

			ps.setDate( param++, Funcoes.dateToSQLDate( new Date() ) );
			ps.setDate( param++, Funcoes.dateToSQLDate( new Date() ) );

			ps.setString( param++, getTipofrete() );
			
			ps.setInt( param++, Aplicativo.iCodEmp );
			ps.setInt( param++, ListaCampos.getMasterFilial( "VDTRANSP" ) );
			ps.setInt( param++, getCodtran() );
			
			ps.setInt( param++, Aplicativo.iCodEmp );
			ps.setInt( param++, ListaCampos.getMasterFilial( "EQRECMERC" ) );
			ps.setInt( param++, getTicket() );

			ps.execute();

			ps.close();

			geraItemCompra( getCodcompra() );

			// Ser for CIF (Por conta do comprador) deve gerar conhecimento para controle do pagamento.
			if( "C".equals( getTipofrete()) ) {
				
				geraFreteRecMerc();
				
			}
			else {
				con.commit();
			}
			

		} 
		catch ( Exception e ) {
			Funcoes.mensagemErro( null, "Erro ao gerar compra!", true, con, e );
			setCodcompra( null );
			e.printStackTrace();
		}

		return getCodcompra();

	}

	public void geraFreteRecMerc() {

		StringBuilder sql = new StringBuilder();

		Integer ticket = null;
		BigDecimal pesoliq = null;
		BigDecimal peso1 = null;
		BigDecimal peso2 = null;
		PreparedStatement ps = null;

		try {

			HashMap<String, Object> p1 = getPrimeirapesagem();

			peso1 = (BigDecimal) p1.get( "peso" );

			HashMap<String, Object> p2 = getSegundapesagem();

			peso2 = (BigDecimal) p2.get( "peso" );

			pesoliq = peso1.subtract( peso2 );

			
			geraCodFrete();

			sql.append( "insert into lffrete (" );
			sql.append( "codemp, codfilial, codfrete,  " );
			sql.append( "codemptn, codfilialtn, codtran, " );
			sql.append( "codemptm, codfilialtm, codtipomov, serie, docfrete, " );
			sql.append( "tipofrete, tipopgto, " );
			sql.append( "codempre, codfilialre, codremet, " );
			sql.append( "codempde, codfilialde, coddestinat, " );
			sql.append( "dtemitfrete, qtdfrete, vlrmercadoria, vlrfrete, " );
			sql.append( "pesobruto, pesoliquido, codemprm, codfilialrm, ticket " );

			sql.append( ") values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ,?, ?, ?, ?, ? )" );

			ps = con.prepareStatement( sql.toString() );

			int param = 1;

			ps.setInt( param++, Aplicativo.iCodEmp );
			ps.setInt( param++, ListaCampos.getMasterFilial( "LFFRETE" ) );
			ps.setInt( param++, getCodfrete() );

			ps.setInt( param++, Aplicativo.iCodEmp );
			ps.setInt( param++, ListaCampos.getMasterFilial( "VDTRANSP" ) );
			ps.setInt( param++, getCodtran());

			ps.setInt( param++, Aplicativo.iCodEmp );
			ps.setInt( param++, ListaCampos.getMasterFilial( "EQTIPOMOV" ) );
			
			Integer codtipomov = TipoMov.getTipoMovFrete(); 
			String serie = TipoMov.getSerieTipoMov( codtipomov );
			
			ps.setInt( param++, codtipomov );
			ps.setString( param++, serie);		
			
//			ps.setInt( param++, TipoMov.getDocSerie( serie ));
			
			ps.setInt( param++, getTicket());
			
			ps.setString( param++, getTipofrete());			
			
			ps.setString( param++, "A"); // Frete a pagar
			
			ps.setInt( param++, Aplicativo.iCodEmp );
			ps.setInt( param++, ListaCampos.getMasterFilial( "SGUNIFCOD" ) );
			ps.setInt( param++, getCodremet() );

			ps.setInt( param++, Aplicativo.iCodEmp );
			ps.setInt( param++, ListaCampos.getMasterFilial( "SGUNIFCOD" ) );
			ps.setInt( param++, getCoddestinat() );
			
			ps.setDate( param++, Funcoes.dateToSQLDate( getDtent() ) );
			
			ps.setBigDecimal( param++, pesoliq );
			
			ps.setBigDecimal( param++, getValorLiqCompra() );
			
			ps.setBigDecimal( param++, getValorFrete( getPrecopeso(), pesoliq ) );
			
			ps.setBigDecimal( param++, pesoliq );
			
			ps.setBigDecimal( param++, pesoliq );
			
			ps.setInt( param++, Aplicativo.iCodEmp );
			
			ps.setInt( param++, ListaCampos.getMasterFilial( "EQRECMERC" ) );
			
			ps.setInt( param++, getTicket() );
			
			ps.execute();

			ps.close();
			
			/// Vincula Compra/Frete
			
			sql = new StringBuilder();
			
			sql.append( "insert into lffretecompra (codemp, codfilial, codfrete, codcompra) values (? ,?, ?, ?) " );
			
			ps = con.prepareStatement( sql.toString() );
			
			param = 1;
			
			ps.setInt( param++, Aplicativo.iCodEmp );
			ps.setInt( param++, ListaCampos.getMasterFilial( "LFFRETE" ) );
			ps.setInt( param++, getCodfrete() );
			ps.setInt( param++, getCodcompra() );	
						
			ps.execute();

			con.commit();
			
			ps.close();
			

		} 
		catch ( Exception e ) {
			Funcoes.mensagemErro( null, "Erro ao gerar conhecimento de frete!", true, con, e );
			setCodcompra( null );
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception err) {
				err.printStackTrace();
			}
		}

	}
	
	public BigDecimal getValorLiqCompra() {
		BigDecimal ret = null;
		
		try {

			StringBuilder sql = new StringBuilder();
			
			sql.append( "select vlrliqcompra from cpcompra where " );			
			sql.append( "codemp=? and codfilial=? and codcompra=? " );
			
			PreparedStatement ps = Aplicativo.getInstace().getConexao().prepareStatement( sql.toString() );
			
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "CPCOMPRA" ) );			
			ps.setInt( 3, getCodcompra() );
			
			ResultSet rs = ps.executeQuery();

			if ( rs.next() ) {
				ret = rs.getBigDecimal( "vlrliqcompra" );
				
			}

			rs.close();
			ps.close();

		} 
		catch ( SQLException e ) {
			e.printStackTrace();		
		}
		return ret;
	}
	
	public Integer geraOrcamento() {

		StringBuilder sql = new StringBuilder();

		Integer ticket = null;
		BigDecimal pesoliq = null;
		BigDecimal peso1 = null;
		BigDecimal peso2 = null;
		String unid = null;
		PreparedStatement ps = null;
		Integer codplanopag = null;

		try {

			geraCodOrc();

			geraCodVend();

			geraCodTipoMovOrc();

			if ( oPrefs == null ) {
				geraPrefereOrc();
			}

			codplanopag = getPlanoPag();

			if ( codplanopag == null ) {
				return null;
			}

			sql.append( "insert into vdorcamento (" );
			sql.append( "codemp, codfilial, tipoorc, codorc, " );
			sql.append( "dtorc, dtvencorc, codemppg, codfilialpg, codplanopag, " );
			sql.append( "codempcl, codfilialcl, codcli, " );
			sql.append( "codempvd, codfilialvd, codvend, " );
			sql.append( "codemptm, codfilialtm, codtipomov, " );
			sql.append( "statusorc " );

			sql.append( ") values ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )" );

			ps = con.prepareStatement( sql.toString() );

			int param = 1;

			ps.setInt( param++, Aplicativo.iCodEmp );
			ps.setInt( param++, ListaCampos.getMasterFilial( "VDORCAMENTO" ) );
			ps.setString( param++, "O" );
			ps.setInt( param++, getCodorc() );

			ps.setDate( param++, Funcoes.dateToSQLDate( new Date() ) );
			ps.setDate( param++, Funcoes.dateToSQLDate( Orcamento.getVencimento( (Integer) oPrefs[ Orcamento.PrefOrc.DIASVENCORC.ordinal() ] ) ) );

			ps.setInt( param++, Aplicativo.iCodEmp );
			ps.setInt( param++, ListaCampos.getMasterFilial( "VDPLANOPAG" ) );
			ps.setInt( param++, codplanopag );

			ps.setInt( param++, Aplicativo.iCodEmp );
			ps.setInt( param++, ListaCampos.getMasterFilial( "VDCLIENTE" ) );
			ps.setInt( param++, getCodcli() );

			ps.setInt( param++, Aplicativo.iCodEmp );
			ps.setInt( param++, ListaCampos.getMasterFilial( "VDVENDEDOR" ) );
			ps.setInt( param++, getCodvend() );

			ps.setInt( param++, Aplicativo.iCodEmp );
			ps.setInt( param++, ListaCampos.getMasterFilial( "EQTIPOMOV" ) );
			ps.setInt( param++, getCodtipomov() );

			ps.setString( param++, Orcamento.STATUS_ABERTO.getValue().toString() );

			ps.execute();

			con.commit();
			ps.close();

			geraItemOrc( getCodorc() );

		} catch ( Exception e ) {
			Funcoes.mensagemErro( null, "Erro ao gerar orçamento!", true, con, e );
			setCodcompra( null );
			e.printStackTrace();
		}

		return getCodorc();

	}

	public Integer geraItemCompra( Integer codcompra ) {

		StringBuilder sql = new StringBuilder();

		BigDecimal pesoliq = null;
		BigDecimal peso1 = null;
		BigDecimal peso2 = null;
		String unid = null;
		PreparedStatement ps = null;
		Integer codplanopag = null;

		try {

			HashMap<String, Object> p1 = getPrimeirapesagem();

			peso1 = (BigDecimal) p1.get( "peso" );

			HashMap<String, Object> p2 = getSegundapesagem();

			peso2 = (BigDecimal) p2.get( "peso" );
			unid = (String) p2.get( "unid" );

			pesoliq = peso1.subtract( peso2 );

			sql.append( "execute procedure cpadicitcomprarecmercsp(?,?,?,?,?,?,?)" );

			ps = con.prepareStatement( sql.toString() );

			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "EQRECMERC" ) );
			ps.setInt( 3, getTicket() );

			ps.setInt( 4, Aplicativo.iCodEmp );
			ps.setInt( 5, ListaCampos.getMasterFilial( "CPCOMPRA" ) );
			ps.setInt( 6, codcompra );
			ps.setBigDecimal( 7, pesoliq ); 			

			ps.execute();
			ps.close();

		} catch ( Exception e ) {
			Funcoes.mensagemErro( null, "Erro ao gerar itens de compra!", true, con, e );
			setCodcompra( null );
			e.printStackTrace();
		}

		return getCodcompra();

	}

	public Integer geraItemOrc( Integer codorc ) {

		StringBuilder sql = new StringBuilder();

		PreparedStatement ps = null;
		Integer codplanopag = null;

		try {

			sql.append( "execute procedure vdadicitorcrecmercsp(?,?,?,?,?,?)" );

			ps = con.prepareStatement( sql.toString() );

			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "EQRECMERC" ) );
			ps.setInt( 3, getTicket() );

			ps.setInt( 4, Aplicativo.iCodEmp );
			ps.setInt( 5, ListaCampos.getMasterFilial( "VDORCAMENTO" ) );
			ps.setInt( 6, getCodorc() );

			ps.execute();
			ps.close();

		} catch ( Exception e ) {
			Funcoes.mensagemErro( null, "Erro ao gerar itens de orçamento!", true, con, e );
			setCodorc( null );
			e.printStackTrace();
		}

		return getCodorc();

	}

	private Integer getPlanoPag() {

		Integer codplanopag = null;

		try {

			DLInfoPlanoPag dl = new DLInfoPlanoPag( getOrig(), con );
			dl.setConexao( con );
			
			dl.setVisible( true );

			if ( dl.OK ) {
				codplanopag = dl.getValor();
				dl.dispose();
			}
			else {
				dl.dispose();
			}

		} catch ( Exception e ) {
			e.printStackTrace();
		}
		return codplanopag;
	}
	
	public static Integer getSolicitacao(Component orig) {

		StringBuilder sql = new StringBuilder();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Integer codsol = 1;

		try {

			sql.append( "select coalesce(max(codsol),0) from cpsolicitacao " );
			sql.append( "where sitsol='AT' and codemp=? and codfilial=? " );

			ps = Aplicativo.getInstace().getConexao().prepareStatement( sql.toString() );

			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "CPSOLICITACAO" ) );

			rs = ps.executeQuery();

			if ( rs.next() ) {
				codsol = rs.getInt( 1 );
			}

		} catch ( Exception e ) {
			e.printStackTrace();
		}

		return codsol;
	}

	private void getCodVendTela() {

		try {

			DLInfoVendedor dl = new DLInfoVendedor( getOrig(), con );
			dl.setConexao( con );
			dl.setVisible( true );

			if ( dl.OK ) {
				setCodvend( dl.getValor() );
				dl.dispose();
			}
			else {
				dl.dispose();
			}

		} catch ( Exception e ) {
			e.printStackTrace();
		}
	}

	public BigDecimal getValorFrete(BigDecimal precopeso, BigDecimal peso) {

		BigDecimal ret = null;

		try {

			ret = precopeso.multiply( peso );

		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return ret;

	}

	public Integer getCodfor() {

		return codfor;
	}

	public void setCodfor( Integer codfor ) {

		this.codfor = codfor;
	}

	public Component getOrig() {

		return orig;
	}

	public void setOrig( Component orig ) {

		this.orig = orig;
	}

	public Integer getCodtipomov() {

		return codtipomov;
	}

	public void setCodtipomov( Integer codtipomov ) {

		this.codtipomov = codtipomov;
	}

	public String getSerie() {

		return serie;
	}

	public void setSerie( String serie ) {

		this.serie = serie;
	}

	public Integer getDocserie() {

		return docserie;
	}

	public void setDocserie( Integer docserie ) {

		this.docserie = docserie;
	}

	public String getTipofrete() {

		return tipofrete;
	}

	public void setTipofrete( String tipofrete ) {

		this.tipofrete = tipofrete;
	}

	public Integer getCodcompra() {

		return codcompra;
	}

	public void setCodcompra( Integer codcompra ) {

		this.codcompra = codcompra;
	}

	public Integer getCodfrete() {

		return codfrete;
	}

	public void setCodfrete( Integer codfrete ) {

		this.codfrete = codfrete;
	}


	public Integer getCodorc() {

		return codorc;
	}

	public void setCodorc( Integer codorc ) {

		this.codorc = codorc;
	}

	public Integer getCodcli() {

		return codcli;
	}

	public void setCodcli( Integer codcli ) {

		this.codcli = codcli;
	}

	public Integer getCodvend() {

		return codvend;
	}

	public void setCodvend( Integer codvend ) {

		this.codvend = codvend;
	}


	public Integer getCoddestinat() {

		return coddestinat;
	}


	public void setCoddestinat( Integer coddestinat ) {

		this.coddestinat = coddestinat;
	}


	public Integer getCodremet() {

		return codremet;
	}


	public void setCodremet( Integer codremet ) {

		this.codremet = codremet;
	}

	
	public Integer getCodtran() {
	
		return codtran;
	}

	
	public void setCodtran( Integer codtran ) {
	
		this.codtran = codtran;
	}

	
	public Date getDtent() {
	
		return dtent;
	}

	
	public void setDtent( Date dtent ) {
	
		this.dtent = dtent;
	}

	
	public BigDecimal getPrecopeso() {
	
		return precopeso;
	}

	
	public void setPrecopeso( BigDecimal precopeso ) {
	
		this.precopeso = precopeso;
	}

}
