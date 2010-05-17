package org.freedom.modulos.gms.business.object;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.util.HashMap;

import javax.swing.SwingConstants;

import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.infra.pojos.Constant;
import org.freedom.library.functions.Funcoes;
import org.freedom.library.persistence.ListaCampos;
import org.freedom.library.swing.component.JLabelPad;
import org.freedom.library.swing.frame.Aplicativo;
import org.freedom.modulos.fnc.view.dialog.utility.DLInfoPlanoPag;

public class RecMerc implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	//Status do recebimento

	public static final Constant STATUS_NAO_SALVO = new Constant("Não Salvo", null );

	public static final Color COR_NAO_SALVO = Color.GRAY;

	public static final Constant STATUS_PENDENTE = new Constant("Pendente", "PE"); 

	public static final Color COR_PENDENTE = Color.ORANGE;

	public static final Constant STATUS_PESAGEM_1 = new Constant("Pesagem 1", "E1");

	public static final Color COR_PESAGEM_1 = Color.BLUE;

	public static final Constant STATUS_PESAGEM_2 = new Constant("Pesagem 1", "E2");

	public static final Color COR_PESAGEM_2 = Color.BLUE;

	public static final Constant STATUS_RECEBIMENTO_FINALIZADO = new Constant("Finalizado", "FN");

	public static final Color COR_RECEBIMENTO_FINALIZADO = new Color( 45, 190, 60 );

	public static final Constant STATUS_NOTA_ENTRADA_EMITIDA = new Constant("Nota emitida", "NE");

	public static final Color COR_NOTA_ENTRADA_EMITIDA = Color.RED;

	private HashMap<String, Object> primeirapesagem = null;

	private HashMap<String, Object> segundapesagem = null;

	private HashMap<String, Object> rendapesagem = null;

	private Integer ticket = null;

	private Integer codfor = null;

	private Integer codtipomov = null;

	private DbConnection con = null;

	private Component orig = null;

	private String serie = null;

	private Integer docserie = null;
	
	private String tipofrete = null;
	
	private Integer codcompra = null;


	public RecMerc(Component orig, Integer ticket, DbConnection con) {

		setTicket( ticket );
		setConexao( con );
		setOrig(orig);

		CarregaRecMerc();

		buscaPesagens();

	}

	private void buscaPesagens() {

		buscaPrimeiraPesagem();
		buscaSegundaPesagem();
		buscaRenda();

	}

	public static void atualizaStatus( String status, JLabelPad lbstatus ) {

		lbstatus.setForeground( Color.WHITE );
		lbstatus.setFont( new Font( "Arial", Font.BOLD, 13 ) );
		lbstatus.setOpaque( true );
		lbstatus.setHorizontalAlignment( SwingConstants.CENTER );

		if ( status == STATUS_NAO_SALVO.getValue()) {
			lbstatus.setText( STATUS_PENDENTE.getName() );
			lbstatus.setBackground( COR_NAO_SALVO );
		}
		else if ( STATUS_PENDENTE.getValue().equals( status )) {
			lbstatus.setText( STATUS_PENDENTE.getName() );
			lbstatus.setBackground( COR_PENDENTE );
		}
		else if ( STATUS_PESAGEM_1.getValue().equals( status )) {
			lbstatus.setText( STATUS_PESAGEM_1.getName() );
			lbstatus.setBackground( COR_PESAGEM_1 );
		}
		else if ( STATUS_PESAGEM_2.getValue().equals( status )) {
			lbstatus.setText( STATUS_PESAGEM_2.getName() );
			lbstatus.setBackground( COR_PESAGEM_2 );
		}
		else if ( STATUS_RECEBIMENTO_FINALIZADO.getValue().equals( status )) {
			lbstatus.setText( STATUS_RECEBIMENTO_FINALIZADO.getName() );
			lbstatus.setBackground( COR_RECEBIMENTO_FINALIZADO );
		}
		else if ( STATUS_NOTA_ENTRADA_EMITIDA.getValue().equals( status )) {
			lbstatus.setText( STATUS_NOTA_ENTRADA_EMITIDA.getName() );
			lbstatus.setBackground( COR_NOTA_ENTRADA_EMITIDA );
		}

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

			con.commit();

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
			sql.append( "and p.codemp=i.codemptp and p.codfilial=i.codfilialtp and p.codprocrecmerc=i.codprocrecmerc and p.tipoprocrecmerc=? " );
			sql.append( "and pd.codemp=i.codemppd and pd.codfilial=i.codfilialpd and pd.codprod=i.codprod " );
			sql.append( "order by a.dataamost, a.codamostragem desc" );

			ps = con.prepareStatement( sql.toString() );

			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "EQRECMERC" ) );
			ps.setInt( 3, getTicket() );
			ps.setString( 4, (String)TipoRecMerc.PROCESSO_PESAGEM_FINAL.getValue() );

			rs = ps.executeQuery();

			if ( rs.next() ) {

				pesagem.put( "peso", rs.getBigDecimal( "peso" ) );
				pesagem.put( "data", Funcoes.dateToStrDate( rs.getDate( "data" ) ) );
				pesagem.put( "hora", rs.getString( "hora" ) );
				pesagem.put( "unid", rs.getString( "codunid" ).trim() );

			}

			con.commit();

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
			ps.setString( 4, (String)TipoRecMerc.PROCESSO_DESCARREGAMENTO.getValue() );

			rs = ps.executeQuery();

			if ( rs.next() ) {

				pesagem.put( "media", rs.getBigDecimal( "media" ) );
				pesagem.put( "renda", rs.getString( "renda" ) );

			}

			con.commit();

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

	private void setConexao(DbConnection con) {
		this.con = con;
	}

	private void setTicket(Integer ticket) {
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

			if(rs.next()) {
				codcompra = rs.getInt( 1 );
			}

		}
		catch (Exception e) {
			e.printStackTrace();
		}

		setCodcompra( codcompra );

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
			sql.append( "from eqrecmerc rm left outer join eqtiporecmerc tr on " );
			sql.append( "tr.codemp=rm.codemp and tr.codfilial=rm.codfilial and tr.codtiporecmerc=rm.codtiporecmerc " );
			
			sql.append( "left outer join eqtipomov tm on " );
			sql.append( "tm.codemp=tr.codemptc and tm.codfilial=tr.codfilialtc and tm.codtipomov=tr.codtipomovcp " );
			
			sql.append( "left outer join lfseqserie ss " );
			sql.append( "on ss.codemp=tm.codempse and ss.codfilial=tm.codfilialse and ss.serie=tm.serie and ");
			sql.append( "codempss=? and codfilialss=? and ativserie='S'" );
			sql.append( "where rm.codemp=? and rm.codfilial=? and rm.ticket=? ");
			

			ps = con.prepareStatement( sql.toString() );
			
			System.out.println("SQL:" + sql.toString());
			
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "LFSEQSERIE" ) );
			ps.setInt( 3, Aplicativo.iCodEmp );
			ps.setInt( 4, ListaCampos.getMasterFilial( "EQRECMERC" ) );
			ps.setInt( 5, getTicket() );
		

			rs = ps.executeQuery();

			if (rs.next()) {
				setCodtipomov( rs.getInt( "codtipomov" ));
				setSerie( rs.getString( "serie" ) );
				setDocserie( rs.getInt( "docserie" ) );
				setTipofrete( rs.getString( "tipofrete" ) );
				setCodfor( rs.getInt( "codfor" ) );
			} 

			con.commit();

		}
		catch (Exception e) {
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
			
			if(codplanopag == null) {
				return null;
			}			

			sql.append( "insert into cpcompra (");
			sql.append( "codemp, codfilial, codcompra, ");
			sql.append( "codemppg, codfilialpg, codplanopag, " );
			sql.append( "codempfr, codfilialfr, codfor, ");
			sql.append( "codempse, codfilialse, serie, doccompra, ");
			sql.append( "codemptm, codfilialtm, codtipomov, " );
			sql.append( "dtentcompra, dtemitcompra, tipofretecompra" );
			sql.append( ") values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)" );

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
			
			ps.execute();
			
			con.commit();
			ps.close();
			
			geraItemCompra( getCodcompra() );
			
			
		}
		catch (Exception e) {
			Funcoes.mensagemErro( null, "Erro ao gerar compra!", true, con, e);
			setCodcompra( null );
			e.printStackTrace();
		}
		
		return getCodcompra();

	}
	
	public Integer geraItemCompra(Integer codcompra) {

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
			ps.setInt( 3, getTicket());
			
			ps.setInt( 4, Aplicativo.iCodEmp);
			ps.setInt( 5, ListaCampos.getMasterFilial( "CPCOMPRA" ) );
			ps.setInt( 6, codcompra );
			ps.setBigDecimal( 7, pesoliq );
			
			ps.execute();
			ps.close();

		}
		catch (Exception e) {
			Funcoes.mensagemErro( null, "Erro ao gerar itens de compra!", true, con, e);
			setCodcompra( null );
			e.printStackTrace();
		}
		
		return getCodcompra();

	}
	
	private Integer getPlanoPag() {

		Integer codplanopag = null;

		try {

			DLInfoPlanoPag dl = new DLInfoPlanoPag(getOrig(), con);
			dl.setConexao( con );
			dl.setVisible(true);

			if (dl.OK) {
				codplanopag = dl.getValor();
				dl.dispose();
			} 
			else {
				dl.dispose();
			}

		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return codplanopag;
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




}



