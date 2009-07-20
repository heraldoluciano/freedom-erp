package org.freedom.funcoes.exporta;

import java.math.BigDecimal;
import org.freedom.infra.model.jdbc.DbConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;

public class EbsContabil extends Contabil {
	
	private final DbConnection con;
	
	private final List<String> readrows;
	
	private final Date dtini;
	
	private final Date dtfim;
	
	private int sequencial;
	
	private EbsContabil( final DbConnection con, final List<String> readrows, final Date dtini, final Date dtfim ) {
		
		super();
		this.con = con;
		this.readrows = readrows;
		this.dtini = dtini;
		this.dtfim = dtfim;
		this.sequencial = 1;
	}
	
	private void headerEntradas() throws Exception {
		
		StringBuilder sql = new StringBuilder();
		sql.append( "select f.razfilial, f.cnpjfilial from sgfilial f where f.codemp=? and f.codfilial=?" );

		PreparedStatement ps = con.prepareStatement( sql.toString() );
		ps.setInt( 1, Aplicativo.iCodEmp );
		ps.setInt( 2, ListaCampos.getMasterFilial( "sgfilial" ) );

		ResultSet rs = ps.executeQuery();
		String cnpj = null;

		if ( rs.next() ) {

			cnpj = rs.getString( "cnpjfilial" );
		}

		rs.close();
		ps.close();

		con.commit();
		
		HeaderEntrada headerEntradas = new HeaderEntrada();
		headerEntradas.setDataArquivo( Calendar.getInstance().getTime() );
		headerEntradas.setCnpj( cnpj );
		headerEntradas.setSequencial( sequencial++ );
		
		readrows.add( headerEntradas.toString() );
	}

	private void entradas() throws Exception {

		StringBuilder sql = new StringBuilder();		
		sql.append( "select c.codcompra, " );
		sql.append( "c.dtentcompra, c.doccompra, c.dtemitcompra, c.serie, c.vlrliqcompra, c.vlrbaseipicompra, c.vlripicompra," );
		sql.append( "tm.codmodnota, tm.especietipomov, coalesce(f.cnpjfor, f.cpffor) cnpjfor, p.datapag " );
		sql.append( "from cpcompra c, eqtipomov tm, lfmodnota mn, lfserie s, cpforneced f, fnpagar p " );
		sql.append( "where c.codemp=? and c.codfilial=? and c.dtentcompra between ? and ? and " );
		sql.append( "tm.codemp=c.codemptm and tm.codfilial=c.codfilialtm and tm.codtipomov=c.codtipomov and " );
		sql.append( "mn.codemp=tm.codempmn and mn.codfilial=tm.codfilialmn and mn.codmodnota=tm.codmodnota and " );
		sql.append( "s.codemp=c.codempse and s.codfilial=c.codfilialse and s.serie=c.serie and " );
		sql.append( "f.codemp=c.codempfr and f.codfilial=c.codfilialfr and f.codfor=c.codfor and " );
		sql.append( "p.codempcp=c.codemp and p.codfilialcp=c.codfilial and p.codcompra=p.codcompra" );

		PreparedStatement ps = con.prepareStatement( sql.toString() );
		ps.setInt( 1, Aplicativo.iCodEmp );
		ps.setInt( 2, ListaCampos.getMasterFilial( "CPCOMPRA" ) );
		ps.setDate( 3, Funcoes.dateToSQLDate( dtini ) );
		ps.setDate( 4, Funcoes.dateToSQLDate( dtfim ) );

		ResultSet rs = ps.executeQuery();
		Entrada entradas = null;

		while ( rs.next() ) {
			
			entradas = new Entrada();
			entradas.setDataLancamento( rs.getDate( "dtentcompra" ) );
			entradas.setNota( rs.getInt( "doccompra" ) );
			entradas.setDataEmissao( rs.getDate( "dtemitcompra" ) );
			entradas.setModeloNota( rs.getInt( "codmodnota" ) );
			entradas.setSerie( rs.getString( "serie" ) );
			entradas.setSubSerie( null );

			StringBuilder sqlCFOP = new StringBuilder();		
			sql.append( "select ic.codnat from cpitcompra ic " );
			sql.append( "where ic.codemp=? and ic.codfilial=? and ic.codcompra=? order by ic.coditcompra" );

			PreparedStatement psCFOP = con.prepareStatement( sqlCFOP.toString() );
			ps.setInt( 1, rs.getInt( "codcompra" ) );
			ps.setInt( 2, Aplicativo.iCodEmp );
			ps.setInt( 3, ListaCampos.getMasterFilial( "CPITCOMPRA" ) );

			ResultSet rsCFOP = ps.executeQuery();
			
			if ( rsCFOP.next() ) {
				entradas.setCfop( Integer.parseInt( rs.getString( "codnat" ) ) );
			}
			rsCFOP.close();
			psCFOP.close();
			
			entradas.setClassificacaoIntegracao( 0 );
			entradas.setClassificacaoIntegracao2( 0 );
			entradas.setCnfjFornecedor( rs.getString( "cnpjfor" ) );
			entradas.setValorNota( rs.getBigDecimal( "vlrliqcompra" ) );
			entradas.setBasePIS( null );
			entradas.setBaseCOFINS( null );
			entradas.setBaseCSLL( null );
			entradas.setBaseIR( null );
			
			StringBuilder sqlICMS = new StringBuilder();		
			sql.append( "select ic.percicmsitcompra aliquota, sum(ic.vlrbaseicmsitcompra) base, sum(ic.vlricmsitcompra) valor " );
			sql.append( "from cpitcompra ic " );
			sql.append( "where ic.codemp=? and ic.codfilial=? and ic.codcompra=? " );
			sql.append( "group by ic.percicmsitcompra" );

			PreparedStatement psICMS = con.prepareStatement( sql.toString() );
			ps.setInt( 1, rs.getInt( "codcompra" ) );
			ps.setInt( 2, Aplicativo.iCodEmp );
			ps.setInt( 3, ListaCampos.getMasterFilial( "CPITCOMPRA" ) );

			ResultSet rsICMS = ps.executeQuery();
			
			for ( int i=0; i<4 && rsICMS.next(); i++) {
			
				if ( i == 0 ) {
					entradas.setBaseICMSa( rs.getBigDecimal( "base" ) );
					entradas.setAliquotaICMSa( rs.getBigDecimal( "aliquota" ) );
					entradas.setValorICMSa( rs.getBigDecimal( "valor" ) );
				}
				else if ( i == 1 ) {
					entradas.setBaseICMSb( rs.getBigDecimal( "base" ) );
					entradas.setAliquotaICMSb( rs.getBigDecimal( "aliquota" ) );
					entradas.setValorICMSb( rs.getBigDecimal( "valor" ) );
				}
				else if ( i == 2 ) {
					entradas.setBaseICMSc( rs.getBigDecimal( "base" ) );
					entradas.setAliquotaICMSc( rs.getBigDecimal( "aliquota" ) );
					entradas.setValorICMSc( rs.getBigDecimal( "valor" ) );
				}
				else if ( i == 3 ) {
					entradas.setBaseICMSd( rs.getBigDecimal( "base" ) );
					entradas.setAliquotaICMSd( rs.getBigDecimal( "aliquota" ) );
					entradas.setValorICMSd( rs.getBigDecimal( "valor" ) );
				}
			}
			rsICMS.close();
			psICMS.close();
			
			entradas.setValorICMSIsentas( null );
			entradas.setValorICMSOutras( null );
			entradas.setBaseIPI( rs.getBigDecimal( "vlrbaseipicompra" ) );
			entradas.setValorIPI( rs.getBigDecimal( "vlripicompra" ) );
			entradas.setValorIPIIsentas( null );
			entradas.setValorIPIOutras( null );
			entradas.setValorSubTributaria( null );
			entradas.setBaseSubTributaria( null );
			entradas.setValorICMSSubTributaria( null );
			entradas.setValorDiferidas( null );
			entradas.setObservacaoLivroFiscal( null );
			entradas.setEspecieNota( rs.getString( "especietipomov" ) );
			entradas.setVendaAVista( rs.getDate( "dtemitcompra" ).compareTo( rs.getDate( "datapag" ) ) == 0 ? "S" : "N" );
			entradas.setCfopSubTributaria( null );
			entradas.setBasePISCOFINSSubTributaria( null );
			entradas.setBaseISS( null );
			entradas.setAliquotaISS( null );
			entradas.setValorISS( null );
			entradas.setValorISSIsentas( null );
			entradas.setValorIRRF( null );
			entradas.setValorPIS( null );
			entradas.setValorCOFINS( null );
			entradas.setValorCSLL( null );
			entradas.setDataPagamento( rs.getDate( "datapag" ) );
			entradas.setCodigoOperacaoContabil( 0 );
			entradas.setIndentificacaoExterior( null );		
			entradas.setValorINSS( null );			
			entradas.setValorFUNRURAL( null );		
			entradas.setCodigoItemServico( 0 );
			entradas.setSequencial( sequencial++ );

			readrows.add( entradas.toString() );
		}

		rs.close();
		ps.close();

		con.commit();
	}

	private void itensEntradas() throws Exception {

		StringBuilder sql = new StringBuilder();		
		sql.append( "" );

		PreparedStatement ps = con.prepareStatement( sql.toString() );
		ps.setInt( 1, Aplicativo.iCodEmp );
		ps.setInt( 2, ListaCampos.getMasterFilial( "CPITCOMPRA" ) );
		ps.setDate( 3, Funcoes.dateToSQLDate( dtini ) );
		ps.setDate( 4, Funcoes.dateToSQLDate( dtfim ) );

		ResultSet rs = ps.executeQuery();
		Entrada entradas = null;

		while ( rs.next() ) {
			
			entradas = new Entrada();
			entradas.setSequencial( sequencial++ );

			readrows.add( entradas.toString() );
		}

		rs.close();
		ps.close();

		con.commit();
	}
	
	public static void execute( final DbConnection con, final List<String> readrows, final Date dtini, final Date dtfim ) throws Exception {

		EbsContabil ebs = new EbsContabil( con, readrows, dtini, dtfim );
		
		ebs.headerEntradas();
		//ebs.emitente();		
		ebs.entradas();	
		ebs.itensEntradas();
		
	}
	
	private class EmitenteDestinatario {
		
		private final int tipoRegistro = 4;
		
		private String cnpj;
		
		private String razaoSocial;
		
		private String nomeFantazia;
		
		private String estado;
		
		private String inscricao;
	
		private String endereco;
		
		private String bairro;
		
		private String cidade;
		
		private String cep;
		
		private int municipio;
		
		private	int ddd;
		
		private int telefone;
		
		private int contaCliente;
		
		private int historicoCliente;
		
		private int contaFornecedor;
		
		private int historicoFornecedor;
		
		private String indentificacaoExterior;
		
		private int numero;
		
		private String complemento;
		
		private String suframa;
		
		private int pais;
		
		private int sequencial;
	}
		
	private class HeaderEntrada {
		
		private final int tipoRegistro = 0;
		
		private Date dataArquivo;
		
		private String cnpj;
		
		private int calculaBases;
		
		private int sequencial;
		
		
		private int getTipoRegistro() {
			return tipoRegistro;
		}

		private Date getDataArquivo() {
			return dataArquivo;
		}

		private void setDataArquivo( Date dataArquivo ) {
			this.dataArquivo = dataArquivo;
		}

		private String getCnpj() {
			return cnpj;
		}

		private void setCnpj( String cnpj ) {
			this.cnpj = cnpj;
		}

		private int getCalculaBases() {
			return calculaBases;
		}

		private void setCalculaBases( int calculaBases ) {
			this.calculaBases = calculaBases;
		}

		private int getSequencial() {
			return sequencial;
		}

		private void setSequencial( int sequencial ) {
			this.sequencial = sequencial;
		}

		@Override
		public String toString() {

			StringBuilder headerEntrada = new StringBuilder();
			
			headerEntrada.append( getTipoRegistro() );
			headerEntrada.append( format( getDataArquivo(), DATE, 8, 0 ) );
			headerEntrada.append( format( Funcoes.setMascara( getCnpj(), "##.###.###/####-##" ), CHAR, 18, 0 ) );
			headerEntrada.append( getCalculaBases() );
			headerEntrada.append( format( "", CHAR, 3, 0 ) );
			headerEntrada.append( format( "", CHAR, 444, 0 ) );
			headerEntrada.append( format( "", CHAR, 20, 0 ) );
			headerEntrada.append( format( getSequencial(), NUMERIC, 6, 0 ) );
			
			return headerEntrada.toString();
		}
	}

	private class Entrada {
		
		private final int tipoRegistro = 1;
		
		private Date dataLancamento;
		
		private int nota;
		
		private Date dataEmissao;
		
		private int modeloNota;
		
		private String serie;
		
		private String subSerie;
		
		private int cfop;
		
		private int variacaoCfop;
		
		private int classificacaoIntegracao;
		
		private int classificacaoIntegracao2;
		
		private String cnfjFornecedor;
		
		private BigDecimal valorNota;
		
		private BigDecimal basePIS;
		
		private BigDecimal baseCOFINS;
		
		private BigDecimal baseCSLL;
		
		private BigDecimal baseIR;
		
		private BigDecimal baseICMSa;
		
		private BigDecimal aliquotaICMSa;
		
		private BigDecimal valorICMSa;
		
		private BigDecimal baseICMSb;
		
		private BigDecimal aliquotaICMSb;
		
		private BigDecimal valorICMSb;
		
		private BigDecimal baseICMSc;
		
		private BigDecimal aliquotaICMSc;
		
		private BigDecimal valorICMSc;
		
		private BigDecimal baseICMSd;
		
		private BigDecimal aliquotaICMSd;
		
		private BigDecimal valorICMSd;		
		
		private BigDecimal valorICMSIsentas;
		
		private BigDecimal valorICMSOutras;
		
		private BigDecimal baseIPI;
		
		private BigDecimal valorIPI;
		
		private BigDecimal valorIPIIsentas;
		
		private BigDecimal valorIPIOutras;
		
		private BigDecimal valorSubTributaria;
		
		private BigDecimal baseSubTributaria;
		
		private BigDecimal valorICMSSubTributaria;
		
		private BigDecimal valorDiferidas;
		
		private String observacaoLivroFiscal;
		
		private String especieNota;
		
		private String vendaAVista;
		
		private String cfopSubTributaria;
		
		private BigDecimal basePISCOFINSSubTributaria;
		
		private BigDecimal baseISS;
		
		private BigDecimal aliquotaISS;
		
		private BigDecimal valorISS;
		
		private BigDecimal valorISSIsentas;
		
		private BigDecimal valorIRRF;
		
		private BigDecimal valorPIS;
		
		private BigDecimal valorCOFINS;
		
		private BigDecimal valorCSLL;
		
		private Date dataPagamento;
		
		private int codigoOperacaoContabil;
		
		private String indentificacaoExterior;
		
		private BigDecimal valorINSS;
		
		private BigDecimal valorFUNRURAL;
		
		private int codigoItemServico;
		
		private int sequencial;
		
		
		private Entrada() {}

		private int getTipoRegistro() {
			return tipoRegistro;
		}

		private Date getDataLancamento() {
			return dataLancamento;
		}

		private void setDataLancamento( Date dataLancamento ) {
			this.dataLancamento = dataLancamento;
		}

		private int getNota() {
			return nota;
		}

		private void setNota( int nota ) {
			this.nota = nota;
		}

		private Date getDataEmissao() {
			return dataEmissao;
		}

		private void setDataEmissao( Date dataEmissao ) {
			this.dataEmissao = dataEmissao;
		}

		private int getModeloNota() {
			return modeloNota;
		}

		private void setModeloNota( int modeloNota ) {
			this.modeloNota = modeloNota;
		}

		private String getSerie() {
			return serie;
		}

		private void setSerie( String serie ) {
			this.serie = serie;
		}

		private String getSubSerie() {
			return subSerie;
		}

		private void setSubSerie( String subSerie ) {
			this.subSerie = subSerie;
		}

		private int getCfop() {
			return cfop;
		}

		private void setCfop( int cfop ) {
			this.cfop = cfop;
		}

		private int getVariacaoCfop() {
			return variacaoCfop;
		}

		private void setVariacaoCfop( int variacaoCfop ) {
			this.variacaoCfop = variacaoCfop;
		}

		private int getClassificacaoIntegracao() {
			return classificacaoIntegracao;
		}

		private void setClassificacaoIntegracao( int classificacaoIntegracao ) {
			this.classificacaoIntegracao = classificacaoIntegracao;
		}

		private int getClassificacaoIntegracao2() {
			return classificacaoIntegracao2;
		}

		private void setClassificacaoIntegracao2( int classificacaoIntegracao2 ) {
			this.classificacaoIntegracao2 = classificacaoIntegracao2;
		}

		private String getCnfjFornecedor() {
			return cnfjFornecedor;
		}

		private void setCnfjFornecedor( String cnfjFornecedor ) {
			this.cnfjFornecedor = cnfjFornecedor;
		}

		private BigDecimal getValorNota() {
			return valorNota;
		}

		private void setValorNota( BigDecimal valorNota ) {
			this.valorNota = valorNota;
		}

		private BigDecimal getBasePIS() {
			return basePIS;
		}

		private void setBasePIS( BigDecimal basePIS ) {
			this.basePIS = basePIS;
		}

		private BigDecimal getBaseCOFINS() {
			return baseCOFINS;
		}

		private void setBaseCOFINS( BigDecimal baseCOFINS ) {
			this.baseCOFINS = baseCOFINS;
		}

		private BigDecimal getBaseCSLL() {
			return baseCSLL;
		}

		private void setBaseCSLL( BigDecimal baseCSLL ) {
			this.baseCSLL = baseCSLL;
		}

		private BigDecimal getBaseIR() {
			return baseIR;
		}

		private void setBaseIR( BigDecimal baseIR ) {
			this.baseIR = baseIR;
		}

		private BigDecimal getBaseICMSa() {
			return baseICMSa;
		}

		private void setBaseICMSa( BigDecimal baseICMSa ) {
			this.baseICMSa = baseICMSa;
		}

		private BigDecimal getAliquotaICMSa() {
			return aliquotaICMSa;
		}

		private void setAliquotaICMSa( BigDecimal aliquotaICMSa ) {
			this.aliquotaICMSa = aliquotaICMSa;
		}

		private BigDecimal getValorICMSa() {
			return valorICMSa;
		}

		private void setValorICMSa( BigDecimal valorICMSa ) {
			this.valorICMSa = valorICMSa;
		}

		private BigDecimal getBaseICMSb() {
			return baseICMSb;
		}

		private void setBaseICMSb( BigDecimal baseICMSb ) {
			this.baseICMSb = baseICMSb;
		}

		private BigDecimal getAliquotaICMSb() {
			return aliquotaICMSb;
		}

		private void setAliquotaICMSb( BigDecimal aliquotaICMSb ) {
			this.aliquotaICMSb = aliquotaICMSb;
		}

		private BigDecimal getValorICMSb() {
			return valorICMSb;
		}

		private void setValorICMSb( BigDecimal valorICMSb ) {
			this.valorICMSb = valorICMSb;
		}

		private BigDecimal getBaseICMSc() {
			return baseICMSc;
		}

		private void setBaseICMSc( BigDecimal baseICMSc ) {
			this.baseICMSc = baseICMSc;
		}

		private BigDecimal getAliquotaICMSc() {
			return aliquotaICMSc;
		}

		private void setAliquotaICMSc( BigDecimal aliquotaICMSc ) {
			this.aliquotaICMSc = aliquotaICMSc;
		}

		private BigDecimal getValorICMSc() {
			return valorICMSc;
		}

		private void setValorICMSc( BigDecimal valorICMSc ) {
			this.valorICMSc = valorICMSc;
		}

		private BigDecimal getBaseICMSd() {
			return baseICMSd;
		}

		private void setBaseICMSd( BigDecimal baseICMSd ) {
			this.baseICMSd = baseICMSd;
		}

		private BigDecimal getAliquotaICMSd() {
			return aliquotaICMSd;
		}

		private void setAliquotaICMSd( BigDecimal aliquotaICMSd ) {
			this.aliquotaICMSd = aliquotaICMSd;
		}

		private BigDecimal getValorICMSd() {
			return valorICMSd;
		}

		private void setValorICMSd( BigDecimal valorICMSd ) {
			this.valorICMSd = valorICMSd;
		}

		private BigDecimal getValorICMSIsentas() {
			return valorICMSIsentas;
		}

		private void setValorICMSIsentas( BigDecimal valorICMSIsentas ) {
			this.valorICMSIsentas = valorICMSIsentas;
		}

		private BigDecimal getValorICMSOutras() {
			return valorICMSOutras;
		}

		private void setValorICMSOutras( BigDecimal valorICMSOutras ) {
			this.valorICMSOutras = valorICMSOutras;
		}

		private BigDecimal getBaseIPI() {
			return baseIPI;
		}

		private void setBaseIPI( BigDecimal baseIPI ) {
			this.baseIPI = baseIPI;
		}

		private BigDecimal getValorIPI() {
			return valorIPI;
		}

		private void setValorIPI( BigDecimal valorIPI ) {
			this.valorIPI = valorIPI;
		}

		private BigDecimal getValorIPIIsentas() {
			return valorIPIIsentas;
		}

		private void setValorIPIIsentas( BigDecimal valorIPIIsentas ) {
			this.valorIPIIsentas = valorIPIIsentas;
		}

		private BigDecimal getValorIPIOutras() {
			return valorIPIOutras;
		}

		private void setValorIPIOutras( BigDecimal valorIPIOutras ) {
			this.valorIPIOutras = valorIPIOutras;
		}

		private BigDecimal getValorSubTributaria() {
			return valorSubTributaria;
		}

		private void setValorSubTributaria( BigDecimal valorSubTributaria ) {
			this.valorSubTributaria = valorSubTributaria;
		}

		private BigDecimal getBaseSubTributaria() {
			return baseSubTributaria;
		}

		private void setBaseSubTributaria( BigDecimal baseSubTributaria ) {
			this.baseSubTributaria = baseSubTributaria;
		}

		private BigDecimal getValorICMSSubTributaria() {
			return valorICMSSubTributaria;
		}

		private void setValorICMSSubTributaria( BigDecimal valorICMSSubTributaria ) {
			this.valorICMSSubTributaria = valorICMSSubTributaria;
		}

		private BigDecimal getValorDiferidas() {
			return valorDiferidas;
		}

		private void setValorDiferidas( BigDecimal valorDiferidas ) {
			this.valorDiferidas = valorDiferidas;
		}

		private String getObservacaoLivroFiscal() {
			return observacaoLivroFiscal;
		}

		private void setObservacaoLivroFiscal( String observacaoLivroFiscal ) {
			this.observacaoLivroFiscal = observacaoLivroFiscal;
		}

		private String getEspecieNota() {
			return especieNota;
		}

		private void setEspecieNota( String especieNota ) {
			this.especieNota = especieNota;
		}

		private String getVendaAVista() {
			return vendaAVista;
		}

		private void setVendaAVista( String vendaAVista ) {
			this.vendaAVista = vendaAVista;
		}

		private String getCfopSubTributaria() {
			return cfopSubTributaria;
		}

		private void setCfopSubTributaria( String cfopSubTributaria ) {
			this.cfopSubTributaria = cfopSubTributaria;
		}

		private BigDecimal getBasePISCOFINSSubTributaria() {
			return basePISCOFINSSubTributaria;
		}

		private void setBasePISCOFINSSubTributaria( BigDecimal basePISCOFINSSubTributaria ) {
			this.basePISCOFINSSubTributaria = basePISCOFINSSubTributaria;
		}

		private BigDecimal getBaseISS() {
			return baseISS;
		}

		private void setBaseISS( BigDecimal baseISS ) {
			this.baseISS = baseISS;
		}

		private BigDecimal getAliquotaISS() {
			return aliquotaISS;
		}

		private void setAliquotaISS( BigDecimal aliquotaISS ) {
			this.aliquotaISS = aliquotaISS;
		}

		private BigDecimal getValorISS() {
			return valorISS;
		}

		private void setValorISS( BigDecimal valorISS ) {
			this.valorISS = valorISS;
		}

		private BigDecimal getValorISSIsentas() {
			return valorISSIsentas;
		}

		private void setValorISSIsentas( BigDecimal valorISSIsentas ) {
			this.valorISSIsentas = valorISSIsentas;
		}

		private BigDecimal getValorIRRF() {
			return valorIRRF;
		}

		private void setValorIRRF( BigDecimal valorIRRF ) {
			this.valorIRRF = valorIRRF;
		}

		private BigDecimal getValorPIS() {
			return valorPIS;
		}

		private void setValorPIS( BigDecimal valorPIS ) {
			this.valorPIS = valorPIS;
		}

		private BigDecimal getValorCOFINS() {
			return valorCOFINS;
		}

		private void setValorCOFINS( BigDecimal valorCOFINS ) {
			this.valorCOFINS = valorCOFINS;
		}

		private BigDecimal getValorCSLL() {
			return valorCSLL;
		}

		private void setValorCSLL( BigDecimal valorCSLL ) {
			this.valorCSLL = valorCSLL;
		}

		private Date getDataPagamento() {
			return dataPagamento;
		}

		private void setDataPagamento( Date dataPagamento ) {
			this.dataPagamento = dataPagamento;
		}

		private int getCodigoOperacaoContabil() {
			return codigoOperacaoContabil;
		}

		private void setCodigoOperacaoContabil( int codigoOperacaoContabil ) {
			this.codigoOperacaoContabil = codigoOperacaoContabil;
		}

		private String getIndentificacaoExterior() {
			return indentificacaoExterior;
		}

		private void setIndentificacaoExterior( String indentificacaoExterior ) {
			this.indentificacaoExterior = indentificacaoExterior;
		}

		private BigDecimal getValorINSS() {
			return valorINSS;
		}

		private void setValorINSS( BigDecimal valorINSS ) {
			this.valorINSS = valorINSS;
		}

		private BigDecimal getValorFUNRURAL() {
			return valorFUNRURAL;
		}

		private void setValorFUNRURAL( BigDecimal valorFUNRURAL ) {
			this.valorFUNRURAL = valorFUNRURAL;
		}

		private int getCodigoItemServico() {
			return codigoItemServico;
		}

		private void setCodigoItemServico( int codigoItemServico ) {
			this.codigoItemServico = codigoItemServico;
		}

		private int getSequencial() {
			return sequencial;
		}

		private void setSequencial( int sequencial ) {
			this.sequencial = sequencial;
		}

		@Override
		public String toString() {

			StringBuilder entrada = new StringBuilder();
			
			entrada.append( getTipoRegistro() );
			entrada.append( format( getDataLancamento(), DATE, 8, 0 ) );
			entrada.append( format( getNota(), NUMERIC, 6, 0 ) );
			entrada.append( format( getDataEmissao(), DATE, 8, 0 ) );
			entrada.append( format( getModeloNota(), NUMERIC, 2, 0 ) );
			entrada.append( format( getSerie(), CHAR, 3, 0 ) );
			entrada.append( format( getSubSerie(), CHAR, 3, 0 ) );
			entrada.append( format( getCfop(), NUMERIC, 4, 0 ) );
			entrada.append( format( getClassificacaoIntegracao(), NUMERIC, 2, 0 ) );
			entrada.append( format( getClassificacaoIntegracao2(), NUMERIC, 2, 0 ) );
			entrada.append( format( Funcoes.setMascara( getCnfjFornecedor(), "##.###.###/####-##" ), CHAR, 18, 0 ) );
			entrada.append( format( getValorNota(), NUMERIC, 12, 2 ) );
			entrada.append( format( getBasePIS(), NUMERIC, 12, 2 ) );
			entrada.append( format( getBaseCOFINS(), NUMERIC, 12, 2 ) );
			entrada.append( format( getBaseCSLL(), NUMERIC, 12, 2 ) );
			entrada.append( format( getBaseIR(), NUMERIC, 12, 2 ) );
			entrada.append( format( getBaseICMSa(), NUMERIC, 12, 2 ) );
			entrada.append( format( getAliquotaICMSa(), NUMERIC, 4, 2 ) );
			entrada.append( format( getValorICMSa(), NUMERIC, 12, 2 ) );
			entrada.append( format( getBaseICMSb(), NUMERIC, 12, 2 ) );
			entrada.append( format( getAliquotaICMSb(), NUMERIC, 4, 2 ) );
			entrada.append( format( getValorICMSb(), NUMERIC, 12, 2 ) );
			entrada.append( format( getBaseICMSc(), NUMERIC, 12, 2 ) );
			entrada.append( format( getAliquotaICMSc(), NUMERIC, 4, 2 ) );
			entrada.append( format( getValorICMSc(), NUMERIC, 12, 2 ) );
			entrada.append( format( getBaseICMSd(), NUMERIC, 12, 2 ) );
			entrada.append( format( getAliquotaICMSd(), NUMERIC, 4, 2 ) );
			entrada.append( format( getValorICMSd(), NUMERIC, 12, 2 ) );
			entrada.append( format( getValorICMSIsentas(), NUMERIC, 12, 2 ) );
			entrada.append( format( getValorICMSOutras(), NUMERIC, 12, 2 ) );
			entrada.append( format( getBaseIPI(), NUMERIC, 12, 2 ) );
			entrada.append( format( getValorIPI(), NUMERIC, 12, 2 ) );
			entrada.append( format( getValorIPIIsentas(), NUMERIC, 12, 2 ) );
			entrada.append( format( getValorIPIOutras(), NUMERIC, 12, 2 ) );
			entrada.append( format( getValorSubTributaria(), NUMERIC, 12, 2 ) );
			entrada.append( format( getBaseSubTributaria(), NUMERIC, 12, 2 ) );
			entrada.append( format( getValorICMSSubTributaria(), NUMERIC, 12, 2 ) );
			entrada.append( format( getValorDiferidas(), NUMERIC, 12, 2 ) );
			entrada.append( format( getObservacaoLivroFiscal(), CHAR, 50, 0 ) );
			entrada.append( format( getEspecieNota(), CHAR, 5, 0 ) );
			entrada.append( format( getVendaAVista(), CHAR, 1, 0 ) );
			entrada.append( format( getCfopSubTributaria(), NUMERIC, 4, 0 ) );
			entrada.append( format( getBasePISCOFINSSubTributaria(), NUMERIC, 8, 2 ) );
			entrada.append( format( getBaseISS(), NUMERIC, 12, 2 ) );
			entrada.append( format( getAliquotaISS(), NUMERIC, 4, 2 ) );
			entrada.append( format( getValorISS(), NUMERIC, 12, 2 ) );
			entrada.append( format( getValorISSIsentas(), NUMERIC, 12, 2 ) );
			entrada.append( format( getValorIRRF(), NUMERIC, 12, 2 ) );
			entrada.append( format( getValorPIS(), NUMERIC, 12, 2 ) );
			entrada.append( format( getValorCOFINS(), NUMERIC, 12, 2 ) );
			entrada.append( format( getValorCSLL(), NUMERIC, 12, 2 ) );
			entrada.append( format( getDataPagamento(), DATE, 8, 0 ) );
			entrada.append( format( getCodigoOperacaoContabil(), NUMERIC, 4, 0 ) );
			entrada.append( format( "", CHAR, 6, 0 ) );			
			entrada.append( format( getIndentificacaoExterior(), CHAR, 18, 0 ) );			
			entrada.append( format( getValorINSS(), NUMERIC, 12, 2 ) );			
			entrada.append( format( getValorFUNRURAL(), NUMERIC, 12, 2 ) );			
			entrada.append( format( getCodigoItemServico(), NUMERIC, 4, 0 ) );			
			entrada.append( format( "", CHAR, 18, 0 ) );					
			entrada.append( format( "", CHAR, 5, 0 ) );			
			entrada.append( format( getSequencial(), NUMERIC, 6, 0 ) );
			
			return entrada.toString();
		}
	}

	private class ItemEntrada {
		
		private final int tipoRegistro = 2;
		
		private int codigo;
		
		private BigDecimal quantidade;
		
		private BigDecimal valor;
		
		private BigDecimal quantidade2;
		
		private BigDecimal desconto;
		
		private BigDecimal baseICMS;
		
		private BigDecimal aliquotaICMS;
		
		private BigDecimal valorIPI;
		
		private BigDecimal baseICMSSusTributaria;
		
		private BigDecimal aliquotaIPI;
		
		private BigDecimal percentualReducaoBaseICMS;
		
		private BigDecimal situacaoTributaria;
		
		private String indentificacao;
		
		private int situacaoTributariaIPI;
		
		private BigDecimal baseIPI;
		
		private int situacaoTributariaPIS;
		
		private BigDecimal basePIS;
		
		private BigDecimal aliquotaPIS;
		
		private BigDecimal quantidadeBasePIS;
		
		private BigDecimal valorAliquotaPIS;
		
		private BigDecimal valorPIS;
		
		private int situacaoTributariaCOFINS;
		
		private BigDecimal baseCOFINS;
		
		private BigDecimal aliquotaCOFINS;
		
		private BigDecimal quantidadeBaseCOFINS;
		
		private BigDecimal valorAliquotaCOFINS;
		
		private BigDecimal valorICMSSubTributaria;
		
		private int sequencial;

		private int getTipoRegistro() {
			return tipoRegistro;
		}

		private int getCodigo() {
			return codigo;
		}

		private void setCodigo( int codigo ) {
			this.codigo = codigo;
		}

		private BigDecimal getQuantidade() {
			return quantidade;
		}

		private void setQuantidade( BigDecimal quantidade ) {
			this.quantidade = quantidade;
		}

		private BigDecimal getValor() {
			return valor;
		}

		private void setValor( BigDecimal valor ) {
			this.valor = valor;
		}

		private BigDecimal getQuantidade2() {
			return quantidade2;
		}

		private void setQuantidade2( BigDecimal quantidade2 ) {
			this.quantidade2 = quantidade2;
		}

		private BigDecimal getDesconto() {
			return desconto;
		}

		private void setDesconto( BigDecimal desconto ) {
			this.desconto = desconto;
		}

		private BigDecimal getBaseICMS() {
			return baseICMS;
		}

		private void setBaseICMS( BigDecimal baseICMS ) {
			this.baseICMS = baseICMS;
		}

		private BigDecimal getAliquotaICMS() {
			return aliquotaICMS;
		}

		private void setAliquotaICMS( BigDecimal aliquotaICMS ) {
			this.aliquotaICMS = aliquotaICMS;
		}

		private BigDecimal getValorIPI() {
			return valorIPI;
		}

		private void setValorIPI( BigDecimal valorIPI ) {
			this.valorIPI = valorIPI;
		}

		private BigDecimal getBaseICMSSusTributaria() {
			return baseICMSSusTributaria;
		}

		private void setBaseICMSSusTributaria( BigDecimal baseICMSSusTributaria ) {
			this.baseICMSSusTributaria = baseICMSSusTributaria;
		}

		private BigDecimal getAliquotaIPI() {
			return aliquotaIPI;
		}

		private void setAliquotaIPI( BigDecimal aliquotaIPI ) {
			this.aliquotaIPI = aliquotaIPI;
		}

		private BigDecimal getPercentualReducaoBaseICMS() {
			return percentualReducaoBaseICMS;
		}

		private void setPercentualReducaoBaseICMS( BigDecimal percentualReducaoBaseICMS ) {
			this.percentualReducaoBaseICMS = percentualReducaoBaseICMS;
		}

		private BigDecimal getSituacaoTributaria() {
			return situacaoTributaria;
		}

		private void setSituacaoTributaria( BigDecimal situacaoTributaria ) {
			this.situacaoTributaria = situacaoTributaria;
		}

		private String getIndentificacao() {
			return indentificacao;
		}

		private void setIndentificacao( String indentificacao ) {
			this.indentificacao = indentificacao;
		}

		private int getSituacaoTributariaIPI() {
			return situacaoTributariaIPI;
		}

		private void setSituacaoTributariaIPI( int situacaoTributariaIPI ) {
			this.situacaoTributariaIPI = situacaoTributariaIPI;
		}

		private BigDecimal getBaseIPI() {
			return baseIPI;
		}

		private void setBaseIPI( BigDecimal baseIPI ) {
			this.baseIPI = baseIPI;
		}

		private int getSituacaoTributariaPIS() {
			return situacaoTributariaPIS;
		}

		private void setSituacaoTributariaPIS( int situacaoTributariaPIS ) {
			this.situacaoTributariaPIS = situacaoTributariaPIS;
		}

		private BigDecimal getBasePIS() {
			return basePIS;
		}

		private void setBasePIS( BigDecimal basePIS ) {
			this.basePIS = basePIS;
		}

		private BigDecimal getAliquotaPIS() {
			return aliquotaPIS;
		}

		private void setAliquotaPIS( BigDecimal aliquotaPIS ) {
			this.aliquotaPIS = aliquotaPIS;
		}

		private BigDecimal getQuantidadeBasePIS() {
			return quantidadeBasePIS;
		}

		private void setQuantidadeBasePIS( BigDecimal quantidadeBasePIS ) {
			this.quantidadeBasePIS = quantidadeBasePIS;
		}

		private BigDecimal getValorAliquotaPIS() {
			return valorAliquotaPIS;
		}

		private void setValorAliquotaPIS( BigDecimal valorAliquotaPIS ) {
			this.valorAliquotaPIS = valorAliquotaPIS;
		}

		private BigDecimal getValorPIS() {
			return valorPIS;
		}

		private void setValorPIS( BigDecimal valorPIS ) {
			this.valorPIS = valorPIS;
		}

		private int getSituacaoTributariaCOFINS() {
			return situacaoTributariaCOFINS;
		}

		private void setSituacaoTributariaCOFINS( int situacaoTributariaCOFINS ) {
			this.situacaoTributariaCOFINS = situacaoTributariaCOFINS;
		}

		private BigDecimal getBaseCOFINS() {
			return baseCOFINS;
		}

		private void setBaseCOFINS( BigDecimal baseCOFINS ) {
			this.baseCOFINS = baseCOFINS;
		}

		private BigDecimal getAliquotaCOFINS() {
			return aliquotaCOFINS;
		}

		private void setAliquotaCOFINS( BigDecimal aliquotaCOFINS ) {
			this.aliquotaCOFINS = aliquotaCOFINS;
		}

		private BigDecimal getQuantidadeBaseCOFINS() {
			return quantidadeBaseCOFINS;
		}

		private void setQuantidadeBaseCOFINS( BigDecimal quantidadeBaseCOFINS ) {
			this.quantidadeBaseCOFINS = quantidadeBaseCOFINS;
		}

		private BigDecimal getValorAliquotaCOFINS() {
			return valorAliquotaCOFINS;
		}

		private void setValorAliquotaCOFINS( BigDecimal valorAliquotaCOFINS ) {
			this.valorAliquotaCOFINS = valorAliquotaCOFINS;
		}

		private BigDecimal getValorICMSSubTributaria() {
			return valorICMSSubTributaria;
		}

		private void setValorICMSSubTributaria( BigDecimal valorICMSSubTributaria ) {
			this.valorICMSSubTributaria = valorICMSSubTributaria;
		}

		private int getSequencial() {
			return sequencial;
		}

		private void setSequencial( int sequencial ) {
			this.sequencial = sequencial;
		}

		@Override
		public String toString() {

			StringBuilder itemEntrada = new StringBuilder();

			itemEntrada.append( getTipoRegistro() );
			itemEntrada.append( format( getCodigo(), NUMERIC, 10, 0 ) );			
			itemEntrada.append( format( getQuantidade(), NUMERIC, 9, 3 ) );		
			itemEntrada.append( format( getValor(), NUMERIC, 9, 2 ) );			
			itemEntrada.append( format( getQuantidade2(), NUMERIC, 13, 3 ) );			
			itemEntrada.append( format( getDesconto(), NUMERIC, 12, 2 ) );			
			itemEntrada.append( format( getBaseICMS(), NUMERIC, 12, 2 ) );			
			itemEntrada.append( format( getAliquotaICMS(), NUMERIC, 5, 2 ) );			
			itemEntrada.append( format( getValorIPI(), NUMERIC, 12, 2 ) );			
			itemEntrada.append( format( getBaseICMSSusTributaria(), NUMERIC, 12, 2 ) );			
			itemEntrada.append( format( getAliquotaIPI(), NUMERIC, 5, 2 ) );		
			itemEntrada.append( format( getPercentualReducaoBaseICMS(), NUMERIC, 5, 2 ) );			
			itemEntrada.append( format( getSituacaoTributaria(), NUMERIC, 3, 0 ) );			
			itemEntrada.append( format( getIndentificacao(), CHAR, 15, 0 ) );			
			itemEntrada.append( format( getSituacaoTributariaIPI(), NUMERIC, 3, 0 ) );			
			itemEntrada.append( format( getBaseIPI(), NUMERIC, 12, 2 ) );			
			itemEntrada.append( format( getSituacaoTributariaPIS(), NUMERIC, 3, 0 ) );			
			itemEntrada.append( format( getBasePIS(), NUMERIC, 12, 2 ) );			
			itemEntrada.append( format( getAliquotaPIS(), NUMERIC, 5, 2 ) );			
			itemEntrada.append( format( getQuantidadeBasePIS(), NUMERIC, 12, 2 ) );			
			itemEntrada.append( format( getValorAliquotaPIS(), NUMERIC, 12, 2 ) );			
			itemEntrada.append( format( getValorPIS(), NUMERIC, 12, 2 ) );			
			itemEntrada.append( format( getSituacaoTributariaCOFINS(), NUMERIC, 3, 0 ) );			
			itemEntrada.append( format( getBaseCOFINS(), NUMERIC, 12, 2 ) );			
			itemEntrada.append( format( getAliquotaCOFINS(), NUMERIC, 5, 2 ) );			
			itemEntrada.append( format( getQuantidadeBaseCOFINS(), NUMERIC, 12, 2 ) );			
			itemEntrada.append( format( getValorAliquotaCOFINS(), NUMERIC, 12, 2 ) );			
			itemEntrada.append( format( getValorICMSSubTributaria(), NUMERIC, 12, 2 ) );			
			itemEntrada.append( format( "", CHAR, 224, 0 ) );				
			itemEntrada.append( format( "", CHAR, 5, 0 ) );			
			itemEntrada.append( format( getSequencial(), NUMERIC, 6, 0 ) );
			
			return itemEntrada.toString();
		}
	}

	private class TraillerEntrada {
		
		private final int tipoRegistro = 3;
		
		private BigDecimal valorNota;
		
		private BigDecimal basePIS;
		
		private BigDecimal baseCOFINS;
		
		private BigDecimal baseContribuicaoSocial;
		
		private BigDecimal baseImpostoRenda;
		
		private BigDecimal baseICMSa;
		
		private BigDecimal valorICMSa;
		
		private BigDecimal baseICMSb;
		
		private BigDecimal valorICMSb;
		
		private BigDecimal baseICMSc;
		
		private BigDecimal valorICMSc;
		
		private BigDecimal baseICMSd;
		
		private BigDecimal valorICMSd;
		
		private BigDecimal valorICMSIsentas;
		
		private BigDecimal valorICMSOutras;
		
		private BigDecimal baseIPI;
		
		private BigDecimal valorIPI;
		
		private BigDecimal valorIPIIsentas;
		
		private BigDecimal valorIPIOutras;
		
		private BigDecimal valorSubTributaria;
		
		private BigDecimal baseSubTriburaria;
		
		private BigDecimal valorICMSSubTributaria;
		
		private BigDecimal valorDiferidas;
		
		private int sequencial;

		private int getTipoRegistro() {
			return tipoRegistro;
		}

		private BigDecimal getValorNota() {
			return valorNota;
		}

		private void setValorNota( BigDecimal valorNota ) {
			this.valorNota = valorNota;
		}

		private BigDecimal getBasePIS() {
			return basePIS;
		}

		private void setBasePIS( BigDecimal basePIS ) {
			this.basePIS = basePIS;
		}

		private BigDecimal getBaseCOFINS() {
			return baseCOFINS;
		}

		private void setBaseCOFINS( BigDecimal baseCOFINS ) {
			this.baseCOFINS = baseCOFINS;
		}

		private BigDecimal getBaseContribuicaoSocial() {
			return baseContribuicaoSocial;
		}

		private void setBaseContribuicaoSocial( BigDecimal baseContribuicaoSocial ) {
			this.baseContribuicaoSocial = baseContribuicaoSocial;
		}

		private BigDecimal getBaseImpostoRenda() {
			return baseImpostoRenda;
		}

		private void setBaseImpostoRenda( BigDecimal baseImpostoRenda ) {
			this.baseImpostoRenda = baseImpostoRenda;
		}

		private BigDecimal getBaseICMSa() {
			return baseICMSa;
		}

		private void setBaseICMSa( BigDecimal baseICMSa ) {
			this.baseICMSa = baseICMSa;
		}

		private BigDecimal getValorICMSa() {
			return valorICMSa;
		}

		private void setValorICMSa( BigDecimal valorICMSa ) {
			this.valorICMSa = valorICMSa;
		}

		private BigDecimal getBaseICMSb() {
			return baseICMSb;
		}

		private void setBaseICMSb( BigDecimal baseICMSb ) {
			this.baseICMSb = baseICMSb;
		}

		private BigDecimal getValorICMSb() {
			return valorICMSb;
		}

		private void setValorICMSb( BigDecimal valorICMSb ) {
			this.valorICMSb = valorICMSb;
		}

		private BigDecimal getBaseICMSc() {
			return baseICMSc;
		}

		private void setBaseICMSc( BigDecimal baseICMSc ) {
			this.baseICMSc = baseICMSc;
		}

		private BigDecimal getValorICMSc() {
			return valorICMSc;
		}

		private void setValorICMSc( BigDecimal valorICMSc ) {
			this.valorICMSc = valorICMSc;
		}

		private BigDecimal getBaseICMSd() {
			return baseICMSd;
		}

		private void setBaseICMSd( BigDecimal baseICMSd ) {
			this.baseICMSd = baseICMSd;
		}

		private BigDecimal getValorICMSd() {
			return valorICMSd;
		}

		private void setValorICMSd( BigDecimal valorICMSd ) {
			this.valorICMSd = valorICMSd;
		}

		private BigDecimal getValorICMSIsentas() {
			return valorICMSIsentas;
		}

		private void setValorICMSIsentas( BigDecimal valorICMSIsentas ) {
			this.valorICMSIsentas = valorICMSIsentas;
		}

		private BigDecimal getValorICMSOutras() {
			return valorICMSOutras;
		}

		private void setValorICMSOutras( BigDecimal valorICMSOutras ) {
			this.valorICMSOutras = valorICMSOutras;
		}

		private BigDecimal getBaseIPI() {
			return baseIPI;
		}

		private void setBaseIPI( BigDecimal baseIPI ) {
			this.baseIPI = baseIPI;
		}

		private BigDecimal getValorIPI() {
			return valorIPI;
		}

		private void setValorIPI( BigDecimal valorIPI ) {
			this.valorIPI = valorIPI;
		}

		private BigDecimal getValorIPIIsentas() {
			return valorIPIIsentas;
		}

		private void setValorIPIIsentas( BigDecimal valorIPIIsentas ) {
			this.valorIPIIsentas = valorIPIIsentas;
		}

		private BigDecimal getValorIPIOutras() {
			return valorIPIOutras;
		}

		private void setValorIPIOutras( BigDecimal valorIPIOutras ) {
			this.valorIPIOutras = valorIPIOutras;
		}

		private BigDecimal getValorSubTributaria() {
			return valorSubTributaria;
		}

		private void setValorSubTributaria( BigDecimal valorSubTributaria ) {
			this.valorSubTributaria = valorSubTributaria;
		}

		private BigDecimal getBaseSubTriburaria() {
			return baseSubTriburaria;
		}

		private void setBaseSubTriburaria( BigDecimal baseSubTriburaria ) {
			this.baseSubTriburaria = baseSubTriburaria;
		}

		private BigDecimal getValorICMSSubTributaria() {
			return valorICMSSubTributaria;
		}

		private void setValorICMSSubTributaria( BigDecimal valorICMSSubTributaria ) {
			this.valorICMSSubTributaria = valorICMSSubTributaria;
		}

		private BigDecimal getValorDiferidas() {
			return valorDiferidas;
		}

		private void setValorDiferidas( BigDecimal valorDiferidas ) {
			this.valorDiferidas = valorDiferidas;
		}

		private int getSequencial() {
			return sequencial;
		}

		private void setSequencial( int sequencial ) {
			this.sequencial = sequencial;
		}

		@Override
		public String toString() {

			StringBuilder trallerEntrada = new StringBuilder();
			
			trallerEntrada.append( getTipoRegistro() );			
			trallerEntrada.append( format( getValorNota(), NUMERIC, 12, 2 ) );			
			trallerEntrada.append( format( getBasePIS(), NUMERIC, 12, 2 ) );			
			trallerEntrada.append( format( getBaseCOFINS(), NUMERIC, 12, 2 ) );			
			trallerEntrada.append( format( getBaseContribuicaoSocial(), NUMERIC, 12, 2 ) );			
			trallerEntrada.append( format( getBaseImpostoRenda(), NUMERIC, 12, 2 ) );			
			trallerEntrada.append( format( getBaseICMSa(), NUMERIC, 12, 2 ) );			
			trallerEntrada.append( format( getValorICMSa(), NUMERIC, 12, 2 ) );			
			trallerEntrada.append( format( getBaseICMSb(), NUMERIC, 12, 2 ) );			
			trallerEntrada.append( format( getValorICMSb(), NUMERIC, 12, 2 ) );			
			trallerEntrada.append( format( getBaseICMSc(), NUMERIC, 12, 2 ) );			
			trallerEntrada.append( format( getValorICMSc(), NUMERIC, 12, 2 ) );			
			trallerEntrada.append( format( getBaseICMSd(), NUMERIC, 12, 2 ) );			
			trallerEntrada.append( format( getValorICMSd(), NUMERIC, 12, 2 ) );			
			trallerEntrada.append( format( getValorICMSIsentas(), NUMERIC, 12, 2 ) );			
			trallerEntrada.append( format( getValorICMSOutras(), NUMERIC, 12, 2 ) );			
			trallerEntrada.append( format( getBaseIPI(), NUMERIC, 12, 2 ) );			
			trallerEntrada.append( format( getValorIPI(), NUMERIC, 12, 2 ) );			
			trallerEntrada.append( format( getValorIPIIsentas(), NUMERIC, 12, 2 ) );			
			trallerEntrada.append( format( getValorIPIOutras(), NUMERIC, 12, 2 ) );			
			trallerEntrada.append( format( getValorSubTributaria(), NUMERIC, 12, 2 ) );			
			trallerEntrada.append( format( getBaseSubTriburaria(), NUMERIC, 12, 2 ) );			
			trallerEntrada.append( format( getValorICMSSubTributaria(), NUMERIC, 12, 2 ) );		
			trallerEntrada.append( format( getValorDiferidas(), NUMERIC, 12, 2 ) );
			trallerEntrada.append( format( "", CHAR, 217, 0 ) );
			trallerEntrada.append( format( getSequencial(), NUMERIC, 6, 0 ) );
			
			return trallerEntrada.toString();
		}
	}
	
	private class HeaderSaida {
		
		private final int tipoRegistro = 0;
		
		private Date dataArquivo;
		
		private String cnpj;
		
		private int calculaBases;
		
		private int sequencial;

		
		public int getTipoRegistro() {		
			return tipoRegistro;
		}
		
		public Date getDataArquivo() {		
			return dataArquivo;
		}
		
		public void setDataArquivo( Date dataArquivo ) {		
			this.dataArquivo = dataArquivo;
		}
		
		public String getCnpj() {		
			return cnpj;
		}
		
		public void setCnpj( String cnpj ) {		
			this.cnpj = cnpj;
		}
		
		public int getCalculaBases() {		
			return calculaBases;
		}
		
		public void setCalculaBases( int calculaBases ) {		
			this.calculaBases = calculaBases;
		}
		
		public int getSequencial() {
			return sequencial;
		}

		public void setSequencial( int sequencial ) {		
			this.sequencial = sequencial;
		}

		@Override
		public String toString() {

			StringBuilder headerSaida = new StringBuilder();
			
			headerSaida.append( getTipoRegistro() );			
			headerSaida.append( format( getDataArquivo(), DATE, 8, 0 ) );	
			headerSaida.append( format( getCnpj(), CHAR, 18, 0 ) );
			headerSaida.append( format( getCalculaBases(), CHAR, 1, 0 ) );
			headerSaida.append( format( "", CHAR, 3, 0 ) );
			headerSaida.append( format( "", CHAR, 443, 0 ) );
			headerSaida.append( format( "", CHAR, 20, 0 ) );
			headerSaida.append( format( getSequencial(), NUMERIC, 6, 0 ) );
			
			return headerSaida.toString();
		}
	}

	private class Saida {
		
		private final int tipoRegistro = 1;
		
		private Date dataLancamento;
		
		private int numeroInicial;
		
		private int numeroFinal;
		
		private Date dataEmissao;
		
		private int modelo;
		
		private String serie;
		
		private String subSerie;
		
		private int cfop;
		
		private int variacaoCfop;
	
		private int classificacao1;
		
		private int classificacao2;
		
		private String cnpjDestinatario;
		
		private BigDecimal valorNota;
		
		private BigDecimal basePIS;
		
		private BigDecimal baseCOFINS;
		
		private BigDecimal baseCSLL;
		
		private BigDecimal baseIRPJ;
		
		private BigDecimal baseICMSa;
		
		private BigDecimal aliquotaICMSa;
		
		private BigDecimal valorICMSa;
		
		private BigDecimal baseICMSb;
		
		private BigDecimal aliquotaICMSb;
		
		private BigDecimal valorICMSb;
		
		private BigDecimal baseICMSc;
		
		private BigDecimal aliquotaICMSc;
		
		private BigDecimal valorICMSc;
		
		private BigDecimal baseICMSd;
		
		private BigDecimal aliquotaICMSd;
		
		private BigDecimal valorICMSd;
		
		private BigDecimal valorICMSIsentas;
		
		private BigDecimal valorICMSOutras;
		
		private BigDecimal baseIPI;
		
		private BigDecimal valorIPI;
		
		private BigDecimal valorIPIIsentas;
		
		private BigDecimal valorIPIOutras;
		
		private BigDecimal valorSubTributaria;
		
		private BigDecimal baseSubTributaria;
		
		private BigDecimal valorICMSSubTributaria;
		
		private BigDecimal valorDiferidas;
		
		private BigDecimal baseISS;
		
		private BigDecimal aliquotaISS;
		
		private BigDecimal valorISS;
		
		private BigDecimal valorISSIsentos;
		
		private BigDecimal valorIRRF;
		
		private String observacoesLivrosFiscais;
		
		private String especie;
		
		private String vendaAVista;
		
		private int cfopSubTributaria;
		
		private BigDecimal valorPISCOFINS;
		
		private int modalidadeFrete;
		
		private BigDecimal valorPIS;
		
		private BigDecimal valorCOFINS;
		
		private BigDecimal valorCSLL;
		
		private Date dataRecebimento;
		
		private int operacaoContabil;
		
		private BigDecimal valorMateriais;
		
		private BigDecimal valorSubEmpreitada;
		
		private int codigoServico;
		
		private int clifor;
		
		private String indentificadorExterior;
		
		private int sequencial;

		
		private int getTipoRegistro() {		
			return tipoRegistro;
		}
		
		private Date getDataLancamento() {		
			return dataLancamento;
		}
		
		private void setDataLancamento( Date dataLancamento ) {		
			this.dataLancamento = dataLancamento;
		}
		
		private int getNumeroInicial() {		
			return numeroInicial;
		}
		
		private void setNumeroInicial( int numeroInicial ) {		
			this.numeroInicial = numeroInicial;
		}
		
		private int getNumeroFinal() {		
			return numeroFinal;
		}
		
		private void setNumeroFinal( int numeroFinal ) {		
			this.numeroFinal = numeroFinal;
		}
		
		private Date getDataEmissao() {		
			return dataEmissao;
		}
		
		private void setDataEmissao( Date dataEmissao ) {		
			this.dataEmissao = dataEmissao;
		}
		
		private int getModelo() {		
			return modelo;
		}
		
		private void setModelo( int modelo ) {		
			this.modelo = modelo;
		}
		
		private String getSerie() {		
			return serie;
		}
		
		private void setSerie( String serie ) {		
			this.serie = serie;
		}
		
		private String getSubSerie() {		
			return subSerie;
		}
		
		private void setSubSerie( String subSerie ) {		
			this.subSerie = subSerie;
		}
		
		private int getCfop() {		
			return cfop;
		}
		
		private void setCfop( int cfop ) {		
			this.cfop = cfop;
		}
		
		private int getVariacaoCfop() {		
			return variacaoCfop;
		}
		
		private void setVariacaoCfop( int variacaoCfop ) {		
			this.variacaoCfop = variacaoCfop;
		}
		
		private int getClassificacao1() {		
			return classificacao1;
		}
		
		private void setClassificacao1( int classificacao1 ) {		
			this.classificacao1 = classificacao1;
		}
		
		private int getClassificacao2() {		
			return classificacao2;
		}
		
		private void setClassificacao2( int classificacao2 ) {		
			this.classificacao2 = classificacao2;
		}
		
		private String getCnpjDestinatario() {		
			return cnpjDestinatario;
		}
		
		private void setCnpjDestinatario( String cnpjDestinatario ) {		
			this.cnpjDestinatario = cnpjDestinatario;
		}
		
		private BigDecimal getValorNota() {		
			return valorNota;
		}
		
		private void setValorNota( BigDecimal valorNota ) {		
			this.valorNota = valorNota;
		}
		
		private BigDecimal getBasePIS() {		
			return basePIS;
		}
		
		private void setBasePIS( BigDecimal basePIS ) {		
			this.basePIS = basePIS;
		}
		
		private BigDecimal getBaseCOFINS() {		
			return baseCOFINS;
		}
		
		private void setBaseCOFINS( BigDecimal baseCOFINS ) {		
			this.baseCOFINS = baseCOFINS;
		}
		
		private BigDecimal getBaseCSLL() {		
			return baseCSLL;
		}
		
		private void setBaseCSLL( BigDecimal baseCSLL ) {		
			this.baseCSLL = baseCSLL;
		}		
		
		private BigDecimal getBaseIRPJ() {		
			return baseIRPJ;
		}
		
		private void setBaseIRPJ( BigDecimal baseIRPJ ) {		
			this.baseIRPJ = baseIRPJ;
		}

		private BigDecimal getBaseICMSa() {		
			return baseICMSa;
		}
		
		private void setBaseICMSa( BigDecimal baseICMSa ) {		
			this.baseICMSa = baseICMSa;
		}
		
		private BigDecimal getAliquotaICMSa() {		
			return aliquotaICMSa;
		}
		
		private void setAliquotaICMSa( BigDecimal aliquotaICMSa ) {		
			this.aliquotaICMSa = aliquotaICMSa;
		}
		
		private BigDecimal getValorICMSa() {		
			return valorICMSa;
		}
		
		private void setValorICMSa( BigDecimal valorICMSa ) {		
			this.valorICMSa = valorICMSa;
		}
		
		private BigDecimal getBaseICMSb() {		
			return baseICMSb;
		}
		
		private void setBaseICMSb( BigDecimal baseICMSb ) {		
			this.baseICMSb = baseICMSb;
		}
		
		private BigDecimal getAliquotaICMSb() {		
			return aliquotaICMSb;
		}
		
		private void setAliquotaICMSb( BigDecimal aliquotaICMSb ) {		
			this.aliquotaICMSb = aliquotaICMSb;
		}
		
		private BigDecimal getValorICMSb() {		
			return valorICMSb;
		}
		
		private void setValorICMSb( BigDecimal valorICMSb ) {		
			this.valorICMSb = valorICMSb;
		}
		
		private BigDecimal getBaseICMSc() {		
			return baseICMSc;
		}
		
		private void setBaseICMSc( BigDecimal baseICMSc ) {		
			this.baseICMSc = baseICMSc;
		}
		
		private BigDecimal getAliquotaICMSc() {		
			return aliquotaICMSc;
		}
		
		private void setAliquotaICMSc( BigDecimal aliquotaICMSc ) {		
			this.aliquotaICMSc = aliquotaICMSc;
		}
		
		private BigDecimal getValorICMSc() {		
			return valorICMSc;
		}
		
		private void setValorICMSc( BigDecimal valorICMSc ) {		
			this.valorICMSc = valorICMSc;
		}
		
		private BigDecimal getBaseICMSd() {		
			return baseICMSd;
		}
		
		private void setBaseICMSd( BigDecimal baseICMSd ) {		
			this.baseICMSd = baseICMSd;
		}
		
		private BigDecimal getAliquotaICMSd() {		
			return aliquotaICMSd;
		}
		
		private void setAliquotaICMSd( BigDecimal aliquotaICMSd ) {		
			this.aliquotaICMSd = aliquotaICMSd;
		}
		
		private BigDecimal getValorICMSd() {		
			return valorICMSd;
		}
		
		private void setValorICMSd( BigDecimal valorICMSd ) {		
			this.valorICMSd = valorICMSd;
		}
		
		private BigDecimal getValorICMSIsentas() {		
			return valorICMSIsentas;
		}
		
		private void setValorICMSIsentas( BigDecimal valorICMSIsentas ) {		
			this.valorICMSIsentas = valorICMSIsentas;
		}
		
		private BigDecimal getValorICMSOutras() {		
			return valorICMSOutras;
		}
		
		private void setValorICMSOutras( BigDecimal valorICMSOutras ) {		
			this.valorICMSOutras = valorICMSOutras;
		}
		
		private BigDecimal getBaseIPI() {		
			return baseIPI;
		}
		
		private void setBaseIPI( BigDecimal baseIPI ) {		
			this.baseIPI = baseIPI;
		}
		
		private BigDecimal getValorIPI() {		
			return valorIPI;
		}
		
		private void setValorIPI( BigDecimal valorIPI ) {		
			this.valorIPI = valorIPI;
		}
		
		private BigDecimal getValorIPIIsentas() {		
			return valorIPIIsentas;
		}
		
		private void setValorIPIIsentas( BigDecimal valorIPIIsentas ) {		
			this.valorIPIIsentas = valorIPIIsentas;
		}
		
		private BigDecimal getValorIPIOutras() {		
			return valorIPIOutras;
		}
		
		private void setValorIPIOutras( BigDecimal valorIPIOutras ) {		
			this.valorIPIOutras = valorIPIOutras;
		}
		
		private BigDecimal getValorSubTributaria() {		
			return valorSubTributaria;
		}
		
		private void setValorSubTributaria( BigDecimal valorSubTributaria ) {		
			this.valorSubTributaria = valorSubTributaria;
		}
		
		private BigDecimal getBaseSubTributaria() {		
			return baseSubTributaria;
		}
		
		private void setBaseSubTributaria( BigDecimal baseSubTributaria ) {		
			this.baseSubTributaria = baseSubTributaria;
		}
		
		private BigDecimal getValorICMSSubTributaria() {		
			return valorICMSSubTributaria;
		}
		
		private void setValorICMSSubTributaria( BigDecimal valorICMSSubTributaria ) {		
			this.valorICMSSubTributaria = valorICMSSubTributaria;
		}
		
		private BigDecimal getValorDiferidas() {		
			return valorDiferidas;
		}
		
		private void setValorDiferidas( BigDecimal valorDiferidas ) {		
			this.valorDiferidas = valorDiferidas;
		}
		
		private BigDecimal getBaseISS() {		
			return baseISS;
		}
		
		private void setBaseISS( BigDecimal baseISS ) {		
			this.baseISS = baseISS;
		}
		
		private BigDecimal getAliquotaISS() {		
			return aliquotaISS;
		}
		
		private void setAliquotaISS( BigDecimal aliquotaISS ) {		
			this.aliquotaISS = aliquotaISS;
		}
		
		private BigDecimal getValorISS() {		
			return valorISS;
		}
		
		private void setValorISS( BigDecimal valorISS ) {		
			this.valorISS = valorISS;
		}
		
		private BigDecimal getValorISSIsentos() {		
			return valorISSIsentos;
		}
		
		private void setValorISSIsentos( BigDecimal valorISSIsentos ) {		
			this.valorISSIsentos = valorISSIsentos;
		}
		
		private BigDecimal getValorIRRF() {		
			return valorIRRF;
		}
		
		private void setValorIRRF( BigDecimal valorIRRF ) {		
			this.valorIRRF = valorIRRF;
		}
		
		private String getObservacoesLivrosFiscais() {		
			return observacoesLivrosFiscais;
		}
		
		private void setObservacoesLivrosFiscais( String observacoesLivrosFiscais ) {		
			this.observacoesLivrosFiscais = observacoesLivrosFiscais;
		}
		
		private String getEspecie() {		
			return especie;
		}
		
		private void setEspecie( String especie ) {		
			this.especie = especie;
		}
		
		private String getVendaAVista() {		
			return vendaAVista;
		}
		
		private void setVendaAVista( String vendaAVista ) {		
			this.vendaAVista = vendaAVista;
		}
		
		private int getCfopSubTributaria() {		
			return cfopSubTributaria;
		}
		
		private void setCfopSubTributaria( int cfopSubTributaria ) {		
			this.cfopSubTributaria = cfopSubTributaria;
		}
		
		private BigDecimal getValorPISCOFINS() {		
			return valorPISCOFINS;
		}
		
		private void setValorPISCOFINS( BigDecimal valorPISCOFINS ) {		
			this.valorPISCOFINS = valorPISCOFINS;
		}
		
		private int getModalidadeFrete() {		
			return modalidadeFrete;
		}
		
		private void setModalidadeFrete( int modalidadeFrete ) {		
			this.modalidadeFrete = modalidadeFrete;
		}
		
		private BigDecimal getValorPIS() {		
			return valorPIS;
		}
		
		private void setValorPIS( BigDecimal valorPIS ) {		
			this.valorPIS = valorPIS;
		}
		
		private BigDecimal getValorCOFINS() {		
			return valorCOFINS;
		}
		
		private void setValorCOFINS( BigDecimal valorCOFINS ) {		
			this.valorCOFINS = valorCOFINS;
		}
		
		private BigDecimal getValorCSLL() {		
			return valorCSLL;
		}
		
		private void setValorCSLL( BigDecimal valorCSLL ) {		
			this.valorCSLL = valorCSLL;
		}
		
		private Date getDataRecebimento() {		
			return dataRecebimento;
		}
		
		private void setDataRecebimento( Date dataRecebimento ) {		
			this.dataRecebimento = dataRecebimento;
		}
		
		private int getOperacaoContabil() {		
			return operacaoContabil;
		}
		
		private void setOperacaoContabil( int operacaoContabil ) {		
			this.operacaoContabil = operacaoContabil;
		}
		
		private BigDecimal getValorMateriais() {		
			return valorMateriais;
		}
		
		private void setValorMateriais( BigDecimal valorMateriais ) {		
			this.valorMateriais = valorMateriais;
		}
		
		private BigDecimal getValorSubEmpreitada() {	
			return valorSubEmpreitada;
		}
		
		private void setValorSubEmpreitada( BigDecimal valorSubEmpreitada ) {		
			this.valorSubEmpreitada = valorSubEmpreitada;
		}
		
		private int getCodigoServico() {		
			return codigoServico;
		}
		
		private void setCodigoServico( int codigoServico ) {		
			this.codigoServico = codigoServico;
		}		
		
		private int getClifor() {		
			return clifor;
		}
		
		private void setClifor( int clifor ) {		
			this.clifor = clifor;
		}

		private String getIndentificadorExterior() {		
			return indentificadorExterior;
		}
		
		private void setIndentificadorExterior( String indentificadorExterior ) {		
			this.indentificadorExterior = indentificadorExterior;
		}
		
		private int getSequencial() {		
			return sequencial;
		}
		
		private void setSequencial( int sequencial ) {		
			this.sequencial = sequencial;
		}		

		@Override
		public String toString() {

			StringBuilder saida = new StringBuilder();
			
			saida.append( getTipoRegistro() );	
			saida.append( format( getDataLancamento(), DATE, 8, 0 ) );			
			saida.append( format( getNumeroInicial(), NUMERIC, 6, 0 ) );			
			saida.append( format( getNumeroFinal(), NUMERIC, 6, 0 ) );			
			saida.append( format( getDataEmissao(), DATE, 8, 0 ) );
			saida.append( format( "", CHAR, 3, 0 ) );
			saida.append( format( getModelo(), NUMERIC, 2, 0 ) );			
			saida.append( format( getSerie(), CHAR, 3, 0 ) );			
			saida.append( format( getSubSerie(), CHAR, 3, 0 ) );			
			saida.append( format( getCfop(), NUMERIC, 4, 0 ) );			
			saida.append( format( getVariacaoCfop(), NUMERIC, 2, 0 ) );		
			saida.append( format( getClassificacao1(), NUMERIC, 2, 0 ) );			
			saida.append( format( getClassificacao2(), NUMERIC, 2, 0 ) );			
			saida.append( format( getCnpjDestinatario(), CHAR, 18, 0 ) );			
			saida.append( format( getValorNota(), NUMERIC, 12, 2 ) );			
			saida.append( format( getBasePIS(), NUMERIC, 12, 2 ) );			
			saida.append( format( getBaseCOFINS(), NUMERIC, 12, 2 ) );			
			saida.append( format( getBaseCSLL(), NUMERIC, 12, 2 ) );
			saida.append( format( getBaseIRPJ(), NUMERIC, 12, 2 ) );
			saida.append( format( "", CHAR, 8, 0 ) );
			saida.append( format( getBaseICMSa(), NUMERIC, 12, 2 ) );			
			saida.append( format( getAliquotaICMSa(), NUMERIC, 4, 2 ) );			
			saida.append( format( getValorICMSa(), NUMERIC, 12, 2 ) );			
			saida.append( format( getBaseICMSb(), NUMERIC, 12, 2 ) );			
			saida.append( format( getAliquotaICMSb(), NUMERIC, 4, 2 ) );			
			saida.append( format( getValorICMSb(), NUMERIC, 12, 2 ) );			
			saida.append( format( getBaseICMSc(), NUMERIC, 12, 2 ) );			
			saida.append( format( getAliquotaICMSc(), NUMERIC, 4, 2 ) );			
			saida.append( format( getValorICMSc(), NUMERIC, 12, 2 ) );			
			saida.append( format( getBaseICMSd(), NUMERIC, 12, 2 ) );			
			saida.append( format( getAliquotaICMSd(), NUMERIC, 4, 2 ) );			
			saida.append( format( getValorICMSd(), NUMERIC, 12, 2 ) );			
			saida.append( format( getValorICMSIsentas(), NUMERIC, 12, 2 ) );			
			saida.append( format( getValorICMSOutras(), NUMERIC, 12, 2 ) );			
			saida.append( format( getBaseIPI(), NUMERIC, 12, 2 ) );			
			saida.append( format( getValorIPI(), NUMERIC, 12, 2 ) );			
			saida.append( format( getValorIPIIsentas(), NUMERIC, 12, 2 ) );			
			saida.append( format( getValorIPIOutras(), NUMERIC, 12, 2 ) );			
			saida.append( format( getValorSubTributaria(), NUMERIC, 12, 2 ) );			
			saida.append( format( getBaseSubTributaria(), NUMERIC, 12, 2 ) );			
			saida.append( format( getValorICMSSubTributaria(), NUMERIC, 12, 2 ) );			
			saida.append( format( getValorDiferidas(), NUMERIC, 12, 2 ) );			
			saida.append( format( getBaseISS(), NUMERIC, 12, 2 ) );			
			saida.append( format( getAliquotaISS(), NUMERIC, 4, 2 ) );			
			saida.append( format( getValorISS(), NUMERIC, 12, 2 ) );			
			saida.append( format( getValorISSIsentos(), NUMERIC, 12, 2 ) );			
			saida.append( format( getValorIRRF(), NUMERIC, 12, 2 ) );			
			saida.append( format( getObservacoesLivrosFiscais(), CHAR, 50, 0 ) );			
			saida.append( format( getEspecie(), CHAR, 5, 0 ) );			
			saida.append( format( getVendaAVista(), CHAR, 1, 0 ) );			
			saida.append( format( getCfopSubTributaria(), NUMERIC, 4, 2 ) );			
			saida.append( format( getValorPISCOFINS(), NUMERIC, 12, 2 ) );			
			saida.append( format( getModalidadeFrete(), NUMERIC, 1, 0 ) );			
			saida.append( format( getValorPIS(), NUMERIC, 12, 2 ) );			
			saida.append( format( getValorCOFINS(), NUMERIC, 12, 2 ) );			
			saida.append( format( getValorCSLL(), NUMERIC, 12, 2 ) );			
			saida.append( format( getDataRecebimento(), DATE, 8, 0 ) );			
			saida.append( format( getOperacaoContabil(), NUMERIC, 4, 0 ) );			
			saida.append( format( getValorMateriais(), NUMERIC, 12, 2 ) );			
			saida.append( format( getValorSubEmpreitada(), NUMERIC, 12, 2 ) );			
			saida.append( format( getCodigoServico(), NUMERIC, 4, 0 ) );			
			saida.append( format( getClifor(), NUMERIC, 6, 0 ) );			
			saida.append( format( getIndentificadorExterior(), CHAR, 18, 0 ) );		
			saida.append( format( "", CHAR, 5, 0 ) );
			saida.append( format( getSequencial(), NUMERIC, 6, 0 ) );
			
			return saida.toString();
		}
	}

	private class ItemSaida {
		
		private final int tipoRegistro = 2;
		
		private int codigoItem;
		
		private BigDecimal quantidade;
		
		private BigDecimal valor;
		
		private BigDecimal quantidade2;
		
		private BigDecimal desconto;
		
		private BigDecimal baseICMS;
		
		private BigDecimal aliquotaICMS;
		
		private BigDecimal valorIPI;
		
		private BigDecimal baseICMSSubTributaria;
		
		private BigDecimal aliquotaIPI;
		
		private BigDecimal percentualReducaoBaseICMS;
		
		private int situacaoTributaria;
		
		private String indentificacao;
		
		private int situacaoTributariaIPI;
		
		private BigDecimal baseIPI;
		
		private int situacaoTributariaPIS;
		
		private BigDecimal basePIS;
		
		private BigDecimal aliquotaPIS;
		
		private BigDecimal quantidadeBasePIS;
		
		private BigDecimal valorAliquotaPIS;
		
		private BigDecimal valorPIS;
		
		private int situacaoTributariaCOFINS;
		
		private BigDecimal baseCOFINS;
		
		private BigDecimal aliquotaCOFINS;
		
		private BigDecimal valorCOFINS;
		
		private BigDecimal valorICMSSubTributaria;
		
		private int sequencial;

		private int getCodigoItem() {
			return codigoItem;
		}

		private void setCodigoItem( int codigoItem ) {
			this.codigoItem = codigoItem;
		}

		private BigDecimal getQuantidade() {
			return quantidade;
		}

		private void setQuantidade( BigDecimal quantidade ) {
			this.quantidade = quantidade;
		}

		private BigDecimal getValor() {
			return valor;
		}

		private void setValor( BigDecimal valor ) {
			this.valor = valor;
		}

		private BigDecimal getQuantidade2() {
			return quantidade2;
		}

		private void setQuantidade2( BigDecimal quantidade2 ) {
			this.quantidade2 = quantidade2;
		}

		private BigDecimal getDesconto() {
			return desconto;
		}

		private void setDesconto( BigDecimal desconto ) {
			this.desconto = desconto;
		}

		private BigDecimal getBaseICMS() {
			return baseICMS;
		}

		private void setBaseICMS( BigDecimal baseICMS ) {
			this.baseICMS = baseICMS;
		}

		private BigDecimal getAliquotaICMS() {
			return aliquotaICMS;
		}

		private void setAliquotaICMS( BigDecimal aliquotaICMS ) {
			this.aliquotaICMS = aliquotaICMS;
		}

		private BigDecimal getValorIPI() {
			return valorIPI;
		}

		private void setValorIPI( BigDecimal valorIPI ) {
			this.valorIPI = valorIPI;
		}

		private BigDecimal getBaseICMSSubTributaria() {
			return baseICMSSubTributaria;
		}

		private void setBaseICMSSubTributaria( BigDecimal baseICMSSubTributaria ) {
			this.baseICMSSubTributaria = baseICMSSubTributaria;
		}

		private BigDecimal getAliquotaIPI() {
			return aliquotaIPI;
		}

		private void setAliquotaIPI( BigDecimal aliquotaIPI ) {
			this.aliquotaIPI = aliquotaIPI;
		}

		private BigDecimal getPercentualReducaoBaseICMS() {
			return percentualReducaoBaseICMS;
		}

		private void setPercentualReducaoBaseICMS( BigDecimal percentualReducaoBaseICMS ) {
			this.percentualReducaoBaseICMS = percentualReducaoBaseICMS;
		}

		private int getSituacaoTributaria() {
			return situacaoTributaria;
		}

		private void setSituacaoTributaria( int situacaoTributaria ) {
			this.situacaoTributaria = situacaoTributaria;
		}

		private String getIndentificacao() {
			return indentificacao;
		}

		private void setIndentificacao( String indentificacao ) {
			this.indentificacao = indentificacao;
		}

		private int getSituacaoTributariaIPI() {
			return situacaoTributariaIPI;
		}

		private void setSituacaoTributariaIPI( int situacaoTributariaIPI ) {
			this.situacaoTributariaIPI = situacaoTributariaIPI;
		}

		private BigDecimal getBaseIPI() {
			return baseIPI;
		}

		private void setBaseIPI( BigDecimal baseIPI ) {
			this.baseIPI = baseIPI;
		}

		private int getSituacaoTributariaPIS() {
			return situacaoTributariaPIS;
		}

		private void setSituacaoTributariaPIS( int situacaoTributariaPIS ) {
			this.situacaoTributariaPIS = situacaoTributariaPIS;
		}

		private BigDecimal getBasePIS() {
			return basePIS;
		}

		private void setBasePIS( BigDecimal basePIS ) {
			this.basePIS = basePIS;
		}

		private BigDecimal getAliquotaPIS() {
			return aliquotaPIS;
		}

		private void setAliquotaPIS( BigDecimal aliquotaPIS ) {
			this.aliquotaPIS = aliquotaPIS;
		}

		private BigDecimal getQuantidadeBasePIS() {
			return quantidadeBasePIS;
		}

		private void setQuantidadeBasePIS( BigDecimal quantidadeBasePIS ) {
			this.quantidadeBasePIS = quantidadeBasePIS;
		}

		private BigDecimal getValorAliquotaPIS() {
			return valorAliquotaPIS;
		}

		private void setValorAliquotaPIS( BigDecimal valorAliquotaPIS ) {
			this.valorAliquotaPIS = valorAliquotaPIS;
		}

		private BigDecimal getValorPIS() {
			return valorPIS;
		}

		private void setValorPIS( BigDecimal valorPIS ) {
			this.valorPIS = valorPIS;
		}

		private int getSituacaoTributariaCOFINS() {
			return situacaoTributariaCOFINS;
		}

		private void setSituacaoTributariaCOFINS( int situacaoTributariaCOFINS ) {
			this.situacaoTributariaCOFINS = situacaoTributariaCOFINS;
		}

		private BigDecimal getBaseCOFINS() {
			return baseCOFINS;
		}

		private void setBaseCOFINS( BigDecimal baseCOFINS ) {
			this.baseCOFINS = baseCOFINS;
		}

		private BigDecimal getAliquotaCOFINS() {
			return aliquotaCOFINS;
		}

		private void setAliquotaCOFINS( BigDecimal aliquotaCOFINS ) {
			this.aliquotaCOFINS = aliquotaCOFINS;
		}

		private BigDecimal getValorCOFINS() {
			return valorCOFINS;
		}

		private void setValorCOFINS( BigDecimal valorCOFINS ) {
			this.valorCOFINS = valorCOFINS;
		}

		private BigDecimal getValorICMSSubTributaria() {
			return valorICMSSubTributaria;
		}

		private void setValorICMSSubTributaria( BigDecimal valorICMSSubTributaria ) {
			this.valorICMSSubTributaria = valorICMSSubTributaria;
		}

		private int getSequencial() {
			return sequencial;
		}

		private void setSequencial( int sequencial ) {
			this.sequencial = sequencial;
		}

		private int getTipoRegistro() {
			return tipoRegistro;
		}

		@Override
		public String toString() {

			StringBuilder itemSaida = new StringBuilder();
			
			itemSaida.append( getTipoRegistro() );	
			itemSaida.append( format( getCodigoItem(), NUMERIC, 10, 0 ) );			
			itemSaida.append( format( getQuantidade(), NUMERIC, 12, 2 ) );			
			itemSaida.append( format( getValor(), NUMERIC, 12, 2 ) );			
			itemSaida.append( format( getQuantidade2(), NUMERIC, 13, 3 ) );			
			itemSaida.append( format( getDesconto(), NUMERIC, 12, 2 ) );			
			itemSaida.append( format( getBaseICMS(), NUMERIC, 12, 2 ) );			
			itemSaida.append( format( getAliquotaICMS(), NUMERIC, 5, 2 ) );			
			itemSaida.append( format( getValorIPI(), NUMERIC, 12, 2 ) );			
			itemSaida.append( format( getBaseICMSSubTributaria(), NUMERIC, 12, 2 ) );			
			itemSaida.append( format( getAliquotaIPI(), NUMERIC, 5, 2 ) );			
			itemSaida.append( format( getPercentualReducaoBaseICMS(), NUMERIC, 5, 2 ) );			
			itemSaida.append( format( getSituacaoTributaria(), NUMERIC, 3, 0 ) );			
			itemSaida.append( format( getIndentificacao(), CHAR, 15, 0 ) );			
			itemSaida.append( format( getSituacaoTributariaIPI(), NUMERIC, 12, 2 ) );			
			itemSaida.append( format( getBaseIPI(), NUMERIC, 12, 2 ) );			
			itemSaida.append( format( getSituacaoTributariaPIS(), NUMERIC, 3, 0 ) );			
			itemSaida.append( format( getBasePIS(), NUMERIC, 12, 2 ) );			
			itemSaida.append( format( getAliquotaPIS(), NUMERIC, 5, 2 ) );			
			itemSaida.append( format( getQuantidadeBasePIS(), NUMERIC, 12, 2 ) );			
			itemSaida.append( format( getValorAliquotaPIS(), NUMERIC, 12, 2 ) );			
			itemSaida.append( format( getValorPIS(), NUMERIC, 12, 2 ) );			
			itemSaida.append( format( getSituacaoTributariaCOFINS(), NUMERIC, 3, 0 ) );			
			itemSaida.append( format( getBaseCOFINS(), NUMERIC, 12, 2 ) );			
			itemSaida.append( format( getAliquotaCOFINS(), NUMERIC, 5, 2 ) );			
			itemSaida.append( format( getValorCOFINS(), NUMERIC, 12, 2 ) );			
			itemSaida.append( format( getValorICMSSubTributaria(), NUMERIC, 12, 2 ) );		
			itemSaida.append( format( "", CHAR, 224, 0 ) );					
			itemSaida.append( format( "", CHAR, 5, 0 ) );			
			itemSaida.append( format( getSequencial(), NUMERIC, 6, 0 ) );
			
			return itemSaida.toString();
		}
	}

	private class TraillerSaida {
		
		private final int tipoRegistro = 3;
		
		private BigDecimal valorNota;
		
		private BigDecimal basePIS;
		
		private BigDecimal baseCOFINS;
		
		private BigDecimal baseCSLL;
		
		private BigDecimal baseIRPJ;
		
		private BigDecimal baseICMSa;
		
		private BigDecimal valorICMSa;
		
		private BigDecimal baseICMSb;
		
		private BigDecimal valorICMSb;
		
		private BigDecimal baseICMSc;
		
		private BigDecimal valorICMSc;
		
		private BigDecimal baseICMSd;
		
		private BigDecimal valorICMSd;
		
		private BigDecimal valorICMSIsentas;
		
		private BigDecimal valorICMSOutras;
		
		private BigDecimal baseIPI; 
		
		private BigDecimal valorIPI; 
		
		private BigDecimal valorIPIIsentas;
		
		private BigDecimal valorIPIOutras;
		
		private BigDecimal valorMercadoriasSubTributaria;
		
		private BigDecimal baseSubTributaria;
		
		private BigDecimal valorICMSSubTributarias;
		
		private BigDecimal valorDireridas;
		
		private BigDecimal baseISS;
		
		private BigDecimal valorISS;
		
		private BigDecimal valorIsentas;
		
		private BigDecimal valorIRRFISS; 
		
		private int sequencial;

		private BigDecimal getValorNota() {
			return valorNota;
		}

		private void setValorNota( BigDecimal valorNota ) {
			this.valorNota = valorNota;
		}

		private BigDecimal getBasePIS() {
			return basePIS;
		}

		private void setBasePIS( BigDecimal basePIS ) {
			this.basePIS = basePIS;
		}

		private BigDecimal getBaseCOFINS() {
			return baseCOFINS;
		}

		private void setBaseCOFINS( BigDecimal baseCOFINS ) {
			this.baseCOFINS = baseCOFINS;
		}

		private BigDecimal getBaseCSLL() {
			return baseCSLL;
		}

		private void setBaseCSLL( BigDecimal baseCSLL ) {
			this.baseCSLL = baseCSLL;
		}

		private BigDecimal getBaseIRPJ() {
			return baseIRPJ;
		}

		private void setBaseIRPJ( BigDecimal baseIRPJ ) {
			this.baseIRPJ = baseIRPJ;
		}

		private BigDecimal getBaseICMSa() {
			return baseICMSa;
		}

		private void setBaseICMSa( BigDecimal baseICMSa ) {
			this.baseICMSa = baseICMSa;
		}

		private BigDecimal getValorICMSa() {
			return valorICMSa;
		}

		private void setValorICMSa( BigDecimal valorICMSa ) {
			this.valorICMSa = valorICMSa;
		}

		private BigDecimal getBaseICMSb() {
			return baseICMSb;
		}

		private void setBaseICMSb( BigDecimal baseICMSb ) {
			this.baseICMSb = baseICMSb;
		}

		private BigDecimal getValorICMSb() {
			return valorICMSb;
		}

		private void setValorICMSb( BigDecimal valorICMSb ) {
			this.valorICMSb = valorICMSb;
		}

		private BigDecimal getBaseICMSc() {
			return baseICMSc;
		}

		private void setBaseICMSc( BigDecimal baseICMSc ) {
			this.baseICMSc = baseICMSc;
		}

		private BigDecimal getValorICMSc() {
			return valorICMSc;
		}

		private void setValorICMSc( BigDecimal valorICMSc ) {
			this.valorICMSc = valorICMSc;
		}

		private BigDecimal getBaseICMSd() {
			return baseICMSd;
		}

		private void setBaseICMSd( BigDecimal baseICMSd ) {
			this.baseICMSd = baseICMSd;
		}

		private BigDecimal getValorICMSd() {
			return valorICMSd;
		}

		private void setValorICMSd( BigDecimal valorICMSd ) {
			this.valorICMSd = valorICMSd;
		}

		private BigDecimal getValorICMSIsentas() {
			return valorICMSIsentas;
		}

		private void setValorICMSIsentas( BigDecimal valorICMSIsentas ) {
			this.valorICMSIsentas = valorICMSIsentas;
		}

		private BigDecimal getValorICMSOutras() {
			return valorICMSOutras;
		}

		private void setValorICMSOutras( BigDecimal valorICMSOutras ) {
			this.valorICMSOutras = valorICMSOutras;
		}

		private BigDecimal getBaseIPI() {
			return baseIPI;
		}

		private void setBaseIPI( BigDecimal baseIPI ) {
			this.baseIPI = baseIPI;
		}

		private BigDecimal getValorIPI() {
			return valorIPI;
		}

		private void setValorIPI( BigDecimal valorIPI ) {
			this.valorIPI = valorIPI;
		}

		private BigDecimal getValorIPIIsentas() {
			return valorIPIIsentas;
		}

		private void setValorIPIIsentas( BigDecimal valorIPIIsentas ) {
			this.valorIPIIsentas = valorIPIIsentas;
		}

		private BigDecimal getValorIPIOutras() {
			return valorIPIOutras;
		}

		private void setValorIPIOutras( BigDecimal valorIPIOutras ) {
			this.valorIPIOutras = valorIPIOutras;
		}

		private BigDecimal getValorMercadoriasSubTributaria() {
			return valorMercadoriasSubTributaria;
		}

		private void setValorMercadoriasSubTributaria( BigDecimal valorMercadoriasSubTributaria ) {
			this.valorMercadoriasSubTributaria = valorMercadoriasSubTributaria;
		}

		private BigDecimal getBaseSubTributaria() {
			return baseSubTributaria;
		}

		private void setBaseSubTributaria( BigDecimal baseSubTributaria ) {
			this.baseSubTributaria = baseSubTributaria;
		}

		private BigDecimal getValorICMSSubTributarias() {
			return valorICMSSubTributarias;
		}

		private void setValorICMSSubTributarias( BigDecimal valorICMSSubTributarias ) {
			this.valorICMSSubTributarias = valorICMSSubTributarias;
		}

		private BigDecimal getValorDireridas() {
			return valorDireridas;
		}

		private void setValorDireridas( BigDecimal valorDireridas ) {
			this.valorDireridas = valorDireridas;
		}

		private BigDecimal getBaseISS() {
			return baseISS;
		}

		private void setBaseISS( BigDecimal baseISS ) {
			this.baseISS = baseISS;
		}

		private BigDecimal getValorISS() {
			return valorISS;
		}

		private void setValorISS( BigDecimal valorISS ) {
			this.valorISS = valorISS;
		}

		private BigDecimal getValorIsentas() {
			return valorIsentas;
		}

		private void setValorIsentas( BigDecimal valorIsentas ) {
			this.valorIsentas = valorIsentas;
		}

		private BigDecimal getValorIRRFISS() {
			return valorIRRFISS;
		}

		private void setValorIRRFISS( BigDecimal valorIRRFISS ) {
			this.valorIRRFISS = valorIRRFISS;
		}

		private int getSequencial() {
			return sequencial;
		}

		private void setSequencial( int sequencial ) {
			this.sequencial = sequencial;
		}

		private int getTipoRegistro() {
			return tipoRegistro;
		}

		@Override
		public String toString() {

			StringBuilder itemSaida = new StringBuilder();
			
			itemSaida.append( getTipoRegistro() );	
			itemSaida.append( format( getValorNota(), NUMERIC, 12, 2 ) );		
			itemSaida.append( format( getBasePIS(), NUMERIC, 12, 2 ) );			
			itemSaida.append( format( getBaseCOFINS(), NUMERIC, 12, 2 ) );			
			itemSaida.append( format( getBaseCSLL(), NUMERIC, 12, 2 ) );			
			itemSaida.append( format( getBaseIRPJ(), NUMERIC, 12, 2 ) );			
			itemSaida.append( format( getBaseICMSa(), NUMERIC, 12, 2 ) );			
			itemSaida.append( format( getValorICMSa(), NUMERIC, 12, 2 ) );			
			itemSaida.append( format( getBaseICMSb(), NUMERIC, 12, 2 ) );			
			itemSaida.append( format( getValorICMSb(), NUMERIC, 12, 2 ) );			
			itemSaida.append( format( getBaseICMSc(), NUMERIC, 12, 2 ) );			
			itemSaida.append( format( getValorICMSc(), NUMERIC, 12, 2 ) );			
			itemSaida.append( format( getBaseICMSd(), NUMERIC, 12, 2 ) );			
			itemSaida.append( format( getValorICMSd(), NUMERIC, 12, 2 ) );			
			itemSaida.append( format( getValorICMSIsentas(), NUMERIC, 12, 2 ) );		
			itemSaida.append( format( getValorICMSOutras(), NUMERIC, 12, 2 ) );			
			itemSaida.append( format( getBaseIPI(), NUMERIC, 12, 2 ) ); 			
			itemSaida.append( format( getValorIPI(), NUMERIC, 12, 2 ) ); 			
			itemSaida.append( format( getValorIPIIsentas(), NUMERIC, 12, 2 ) );			
			itemSaida.append( format( getValorIPIOutras(), NUMERIC, 12, 2 ) );			
			itemSaida.append( format( getValorMercadoriasSubTributaria(), NUMERIC, 12, 2 ) );			
			itemSaida.append( format( getBaseSubTributaria(), NUMERIC, 12, 2 ) );			
			itemSaida.append( format( getValorICMSSubTributarias(), NUMERIC, 12, 2 ) );			
			itemSaida.append( format( getValorDireridas(), NUMERIC, 12, 2 ) );			
			itemSaida.append( format( getBaseISS(), NUMERIC, 12, 2 ) );			
			itemSaida.append( format( getValorISS(), NUMERIC, 12, 2 ) );			
			itemSaida.append( format( getValorIsentas(), NUMERIC, 12, 2 ) );
			itemSaida.append( format( getValorIRRFISS(), NUMERIC, 12, 2 ) );			
			itemSaida.append( format( "", CHAR, 169, 0 ) );			
			itemSaida.append( format( getSequencial(), NUMERIC, 6, 0 ) );
			
			return itemSaida.toString();
		}
	}
}
