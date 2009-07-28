package org.freedom.funcoes.exporta;

import java.io.File;
import java.io.FileWriter;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Funcoes;
import org.freedom.infra.model.jdbc.DbConnection;
import org.freedom.telas.Aplicativo;

public class EbsContabil extends Contabil {
	
	private DbConnection con;
	
	private Date dtini;
	
	private Date dtfim;
	
	private int sequencial;
	
	private List<String> readrowsSaida = new ArrayList<String>();
	
	
	public EbsContabil() {
		
		super();
		this.sequencial = 1;
	}

	@Override
	public void createFile( File filecontabil ) throws Exception {	
		
		sizeMax = readrows.size() + readrowsSaida.size();
		
		if ( sizeMax == 0 ) {
			throw new Exception( "Nenhum registro encontrado para exportação!" );
		}
		
		fireActionListenerForMaxSize();

		progressInRows = 1;
		
		File entradas = new File( filecontabil.getPath() + "\\NOTAENT.TXT" );
		entradas.createNewFile();

		FileWriter fwEntradas = new FileWriter( entradas );

		for ( String row : readrows ) {

			fwEntradas.write( row );
			fwEntradas.write( RETURN );
			fwEntradas.flush();
			
			progressInRows++;
			
			fireActionListenerProgressInRows();
		}

		fwEntradas.close();
		
		File saidas = new File( filecontabil.getPath() + "\\NOTASAI.TXT" );
		saidas.createNewFile();

		FileWriter fwSaidas = new FileWriter( saidas );

		for ( String row : readrowsSaida ) {

			fwSaidas.write( row );
			fwSaidas.write( RETURN );
			fwSaidas.flush();
			
			progressInRows++;
			
			fireActionListenerProgressInRows();
		}

		fwSaidas.close();
	}

	public void execute( final DbConnection con, final Date dtini, final Date dtfim ) throws Exception {
		
		this.con = con;
		this.dtini = dtini;
		this.dtfim = dtfim;
		
		entradas();
		saidas();
	}

	private void emitente( char tipo, int codigo ) throws Exception {

		StringBuilder sql = new StringBuilder();	
		
		if ( tipo == 'C' ) {
    		sql.append( "SELECT " );
    		sql.append( "C.RAZCLI RAZAO," );
    		sql.append( "C.NOMECLI NOME," );
    		sql.append( "COALESCE(C.CNPJCLI,C.CPFCLI) CNPJ," );
    		sql.append( "COALESCE(C.INSCCLI,'ISENTO') INSC," );
    		sql.append( "M.CODMUNIC CODMUNIC," );
    		sql.append( "M.NOMEMUNIC MUNICIPIO," );
    		sql.append( "M.SIGLAUF UF," );
    		sql.append( "M.CODPAIS PAIS, " );
    		sql.append( "C.ENDCLI ENDERECO," );
    		sql.append( "C.NUMCLI NUMERO," );
    		sql.append( "C.BAIRCLI BAIRRO," );
    		sql.append( "C.CEPCLI CEP," );
    		sql.append( "C.DDDCLI DDD," );
    		sql.append( "C.FONECLI FONE," );
    		sql.append( "C.COMPLCLI COMPLEMENTO " );
    		sql.append( "FROM VDCLIENTE C " );
    		sql.append( "LEFT OUTER JOIN SGMUNICIPIO M " );
    		sql.append( "ON M.CODPAIS=C.CODPAIS AND M.SIGLAUF=C.SIGLAUF AND M.CODMUNIC=C.CODMUNIC " );
    		sql.append( "WHERE C.CODEMP=? AND C.CODFILIAL=? AND C.CODCLI=?" );
		}
		else if ( tipo == 'F' ) {
    		sql.append( "SELECT " ); 
    		sql.append( "F.RAZFOR RAZAO," );
    		sql.append( "F.NOMEFOR NOME," );
    		sql.append( "COALESCE(F.CNPJFOR,F.CPFFOR) CNPJ," );
    		sql.append( "COALESCE(F.INSCFOR,'ISENTO') INSC," );
    		sql.append( "M.CODMUNIC CODMUNIC," );
    		sql.append( "M.NOMEMUNIC MUNICIPIO," );
    		sql.append( "M.SIGLAUF UF," );
    		sql.append( "M.CODPAIS PAIS," );
    		sql.append( "F.ENDFOR ENDERECO," );
    		sql.append( "F.NUMFOR NUMERO," );
    		sql.append( "F.BAIRFOR BAIRRO," );
    		sql.append( "F.CEPFOR CEP," );
    		sql.append( "F.DDDFONEFOR DDD," );
    		sql.append( "F.FONEFOR FONE," );
    		sql.append( "F.COMPLFOR COMPLEMENTO " );
    		sql.append( "FROM CPFORNECED F " );
    		sql.append( "LEFT OUTER JOIN SGMUNICIPIO M " );
    		sql.append( "ON M.CODPAIS=F.CODPAIS AND M.SIGLAUF=F.SIGLAUF AND M.CODMUNIC=F.CODMUNIC " ); 
    		sql.append( "WHERE F.CODEMP=? AND F.CODFILIAL=? AND F.CODFOR=?" );
		}

		PreparedStatement ps = con.prepareStatement( sql.toString() );
		ps.setInt( 1, Aplicativo.iCodEmp );
		ps.setInt( 2, ListaCampos.getMasterFilial( tipo == 'C' ? "VDVENDA" : "CPCOMPRA" ) );
		ps.setInt( 3, codigo );

		ResultSet rs = ps.executeQuery();
		EmitenteDestinatario emitenteDestinatario = null;

		while ( rs.next() ) {
			
			emitenteDestinatario = new EmitenteDestinatario();		
			emitenteDestinatario.setRazaoSocial( rs.getString( "RAZAO" ) );			
			emitenteDestinatario.setNomeFantazia( rs.getString( "NOME" ) );		
			emitenteDestinatario.setCnpj( rs.getString( "CNPJ" ) );			
			emitenteDestinatario.setInscricao( rs.getString( "INSC" ) );		
			emitenteDestinatario.setCidade( rs.getString( "MUNICIPIO" ) );	
			emitenteDestinatario.setMunicipio( rs.getInt( "CODMUNIC" ) );			
			emitenteDestinatario.setEstado( rs.getString( "UF" ) );		
			emitenteDestinatario.setPais( rs.getInt( "CODPAIS" ) );		
			emitenteDestinatario.setEndereco( rs.getString( "ENDERECO" ) );	
			emitenteDestinatario.setNumero( rs.getInt( "NUMERO" ) );				
			emitenteDestinatario.setBairro( rs.getString( "BAIRRO" ) );				
			emitenteDestinatario.setCep( rs.getInt( "CEP" ) );	
			try {
				emitenteDestinatario.setDdd( rs.getInt( "DDD" ) );			
				emitenteDestinatario.setTelefone( rs.getInt( "FONE" ) );
			} catch ( Exception e ) {
				System.out.println( "Erro ao formatar telefone do cliente/fornecedor["+rs.getString( "RAZAO" ).trim()+"]:\n" + e.getMessage() );
			}
			emitenteDestinatario.setComplemento( rs.getString( "COMPLEMENTO" ) );
			
			emitenteDestinatario.setContaCliente( 0 );			
			emitenteDestinatario.setHistoricoCliente( 0 );			
			emitenteDestinatario.setContaFornecedor( 0 );			
			emitenteDestinatario.setProdutor( false );			
			emitenteDestinatario.setHistoricoFornecedor( 0 );			
			emitenteDestinatario.setIndentificacaoExterior( null );				
			emitenteDestinatario.setSuframa( null );			
			
			emitenteDestinatario.setSequencial( sequencial++ );

			readrows.add( emitenteDestinatario.toString() );
		}

		rs.close();
		ps.close();
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
		
		HeaderEntrada headerEntradas = new HeaderEntrada();
		headerEntradas.setDataArquivo( Calendar.getInstance().getTime() );
		headerEntradas.setCnpj( cnpj );
		headerEntradas.setSequencial( sequencial++ );
		
		readrows.add( headerEntradas.toString() );
	}

	private void entradas() throws Exception {

		StringBuilder sql = new StringBuilder();		
		sql.append( "select c.codcompra, c.codfor," );
		sql.append( "c.dtentcompra, c.doccompra, c.dtemitcompra, c.serie, c.vlrliqcompra, c.vlrbaseipicompra, c.vlripicompra," );
		sql.append( "tm.codmodnota, tm.especietipomov, coalesce(f.cnpjfor, f.cpffor) cnpjfor, p.datapag " );
		sql.append( "from cpcompra c, eqtipomov tm, lfmodnota mn, lfserie s, cpforneced f, fnpagar p " );
		sql.append( "where c.codemp=? and c.codfilial=? and c.dtentcompra between ? and ? and " );
		sql.append( "tm.codemp=c.codemptm and tm.codfilial=c.codfilialtm and tm.codtipomov=c.codtipomov and " );
		sql.append( "mn.codemp=tm.codempmn and mn.codfilial=tm.codfilialmn and mn.codmodnota=tm.codmodnota and " );
		sql.append( "s.codemp=c.codempse and s.codfilial=c.codfilialse and s.serie=c.serie and " );
		sql.append( "f.codemp=c.codempfr and f.codfilial=c.codfilialfr and f.codfor=c.codfor and " );
		sql.append( "p.codempcp=c.codemp and p.codfilialcp=c.codfilial and p.codcompra=c.codcompra " );
		sql.append( "order by c.codcompra " );

		PreparedStatement ps = con.prepareStatement( sql.toString() );
		ps.setInt( 1, Aplicativo.iCodEmp );
		ps.setInt( 2, ListaCampos.getMasterFilial( "CPCOMPRA" ) );
		ps.setDate( 3, Funcoes.dateToSQLDate( dtini ) );
		ps.setDate( 4, Funcoes.dateToSQLDate( dtfim ) );

		ResultSet rs = ps.executeQuery();
		Entrada entradas = null;
		boolean readHeader = true;
		boolean readTrailler = false;

		while ( rs.next() ) {
			
			if ( readHeader ) {
				headerEntradas();
				readHeader = false;
				readTrailler = true;
			}
			
			entradas = new Entrada();
			entradas.setDataLancamento( rs.getDate( "dtentcompra" ) );
			entradas.setNota( rs.getInt( "doccompra" ) );
			entradas.setDataEmissao( rs.getDate( "dtemitcompra" ) );
			entradas.setModeloNota( rs.getInt( "codmodnota" ) );
			entradas.setSerie( rs.getString( "serie" ) );
			entradas.setSubSerie( null );
			entradas.setVariacaoCfop( 1 );

			StringBuilder sqlCFOP = new StringBuilder();		
			sqlCFOP.append( "select ic.codnat from cpitcompra ic " );
			sqlCFOP.append( "where ic.codemp=? and ic.codfilial=? and ic.codcompra=? order by ic.coditcompra" );

			PreparedStatement psCFOP = con.prepareStatement( sqlCFOP.toString() );
			psCFOP.setInt( 1, Aplicativo.iCodEmp );
			psCFOP.setInt( 2, ListaCampos.getMasterFilial( "CPITCOMPRA" ) );
			psCFOP.setInt( 3, rs.getInt( "codcompra" ) );

			ResultSet rsCFOP = psCFOP.executeQuery();
			
			if ( rsCFOP.next() ) {
				entradas.setCfop( Integer.parseInt( rsCFOP.getString( "codnat" ) ) );
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
			sqlICMS.append( "select ic.percicmsitcompra aliquota, sum(ic.vlrbaseicmsitcompra) base, sum(ic.vlricmsitcompra) valor " );
			sqlICMS.append( "from cpitcompra ic " );
			sqlICMS.append( "where ic.codemp=? and ic.codfilial=? and ic.codcompra=? " );
			sqlICMS.append( "group by ic.percicmsitcompra" );

			PreparedStatement psICMS = con.prepareStatement( sqlICMS.toString() );
			psICMS.setInt( 1, rs.getInt( "codcompra" ) );
			psICMS.setInt( 2, Aplicativo.iCodEmp );
			psICMS.setInt( 3, ListaCampos.getMasterFilial( "CPITCOMPRA" ) );

			ResultSet rsICMS = psICMS.executeQuery();
			
			for ( int i=0; i<4 && rsICMS.next(); i++) {
			
				if ( i == 0 ) {
					entradas.setBaseICMSa( rsICMS.getBigDecimal( "base" ) );
					entradas.setAliquotaICMSa( rsICMS.getBigDecimal( "aliquota" ) );
					entradas.setValorICMSa( rsICMS.getBigDecimal( "valor" ) );
				}
				else if ( i == 1 ) {
					entradas.setBaseICMSb( rsICMS.getBigDecimal( "base" ) );
					entradas.setAliquotaICMSb( rsICMS.getBigDecimal( "aliquota" ) );
					entradas.setValorICMSb( rsICMS.getBigDecimal( "valor" ) );
				}
				else if ( i == 2 ) {
					entradas.setBaseICMSc( rsICMS.getBigDecimal( "base" ) );
					entradas.setAliquotaICMSc( rsICMS.getBigDecimal( "aliquota" ) );
					entradas.setValorICMSc( rsICMS.getBigDecimal( "valor" ) );
				}
				else if ( i == 3 ) {
					entradas.setBaseICMSd( rsICMS.getBigDecimal( "base" ) );
					entradas.setAliquotaICMSd( rsICMS.getBigDecimal( "aliquota" ) );
					entradas.setValorICMSd( rsICMS.getBigDecimal( "valor" ) );
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

			emitente( 'F', rs.getInt( "codfor" ) );

			entradas.setSequencial( sequencial++ );
			readrows.add( entradas.toString() );
			
			itensEntrada( rs.getInt( "codcompra" ) );
		}
		
		if ( readTrailler ) {
			traillerEntrada();
		}

		rs.close();
		ps.close();

		con.commit();
	}

	private void itensEntrada( int compra ) throws Exception {

		StringBuilder sql = new StringBuilder();		
		sql.append( "SELECT " );
		sql.append( "I.CODITCOMPRA," );
		sql.append( "I.CODPROD, P.REFPROD," );
		sql.append( "I.QTDITCOMPRA," );
		sql.append( "(I.QTDITCOMPRA * I.PRECOITCOMPRA) VALOR," );
		sql.append( "I.VLRDESCITCOMPRA," );
		sql.append( "I.VLRBASEICMSITCOMPRA," );
		sql.append( "I.PERCICMSITCOMPRA," );
		sql.append( "I.VLRIPIITCOMPRA," );
		sql.append( "I.PERCIPIITCOMPRA " );
		sql.append( "FROM CPITCOMPRA I, EQPRODUTO P " );
		sql.append( "WHERE I.CODEMP=? AND I.CODFILIAL=? AND I.CODCOMPRA=? AND " );
		sql.append( "P.CODEMP=I.CODEMPPD AND P.CODFILIAL=I.CODFILIALPD AND P.CODPROD=I.CODPROD " );
		sql.append( "ORDER BY I.CODITCOMPRA" );

		PreparedStatement ps = con.prepareStatement( sql.toString() );
		ps.setInt( 1, Aplicativo.iCodEmp );
		ps.setInt( 2, ListaCampos.getMasterFilial( "CPITCOMPRA" ) );
		ps.setInt( 3, compra );

		ResultSet rs = ps.executeQuery();
		ItemEntrada itemEntrada = null;

		while ( rs.next() ) {
			
			itemEntrada = new ItemEntrada();			
			itemEntrada.setCodigo( rs.getInt( "CODPROD" ) );			
			itemEntrada.setQuantidade( rs.getBigDecimal( "QTDITCOMPRA" ) );			
			itemEntrada.setValor( rs.getBigDecimal( "VALOR" ) );			
			itemEntrada.setQuantidade2( null );			
			itemEntrada.setDesconto( rs.getBigDecimal( "VLRDESCITCOMPRA" ) );			
			itemEntrada.setBaseICMS( rs.getBigDecimal( "VLRBASEICMSITCOMPRA" ) );			
			itemEntrada.setAliquotaICMS( rs.getBigDecimal( "PERCICMSITCOMPRA" ) );			
			itemEntrada.setValorIPI( rs.getBigDecimal( "VLRIPIITCOMPRA" ) );	
			itemEntrada.setAliquotaIPI( rs.getBigDecimal( "PERCIPIITCOMPRA" ) );		
			itemEntrada.setBaseIPI( rs.getBigDecimal( "VALOR" ) );			
			itemEntrada.setIndentificacao( rs.getString( "REFPROD" ) );							
			itemEntrada.setBaseICMSSusTributaria( null );			
			itemEntrada.setPercentualReducaoBaseICMS( null );			
			itemEntrada.setSituacaoTributaria( 0 );		
			itemEntrada.setSituacaoTributariaIPI( 0 );		
			itemEntrada.setSituacaoTributariaPIS( 0 );			
			itemEntrada.setBasePIS( null );			
			itemEntrada.setAliquotaPIS( null );			
			itemEntrada.setQuantidadeBasePIS( null );			
			itemEntrada.setValorAliquotaPIS( null );			
			itemEntrada.setValorPIS( null );			
			itemEntrada.setSituacaoTributariaCOFINS( 0 );			
			itemEntrada.setBaseCOFINS( null );			
			itemEntrada.setAliquotaCOFINS( null );			
			itemEntrada.setQuantidadeBaseCOFINS( null );			
			itemEntrada.setValorAliquotaCOFINS( null );			
			itemEntrada.setValorICMSSubTributaria( null );
			itemEntrada.setSequencial( sequencial++ );

			readrows.add( itemEntrada.toString() );
		}

		rs.close();
		ps.close();
	}

	private void traillerEntrada() throws Exception {

		/*StringBuilder sql = new StringBuilder();		
		sql.append( "" );

		PreparedStatement ps = con.prepareStatement( sql.toString() );
		ps.setInt( 1, Aplicativo.iCodEmp );
		ps.setInt( 2, ListaCampos.getMasterFilial( "CPITCOMPRA" ) );
		ps.setDate( 3, Funcoes.dateToSQLDate( dtini ) );
		ps.setDate( 4, Funcoes.dateToSQLDate( dtfim ) );

		ResultSet rs = ps.executeQuery();
		TraillerEntrada traillerEntradas = null;

		while ( rs.next() ) {
			
			traillerEntradas = new TraillerEntrada();			
			traillerEntradas.setValorNota( null );			
			traillerEntradas.setBasePIS( null );			
			traillerEntradas.setBaseCOFINS( null );
			traillerEntradas.setBaseContribuicaoSocial( null );			
			traillerEntradas.setBaseImpostoRenda( null );			
			traillerEntradas.setBaseICMSa( null );			
			traillerEntradas.setValorICMSa( null );			
			traillerEntradas.setBaseICMSb( null );			
			traillerEntradas.setValorICMSb( null );			
			traillerEntradas.setBaseICMSc( null );			
			traillerEntradas.setValorICMSc( null );			
			traillerEntradas.setBaseICMSd( null );			
			traillerEntradas.setValorICMSd( null );			
			traillerEntradas.setValorICMSIsentas( null );			
			traillerEntradas.setValorICMSOutras( null );			
			traillerEntradas.setBaseIPI( null );			
			traillerEntradas.setValorIPI( null );			
			traillerEntradas.setValorIPIIsentas( null );			
			traillerEntradas.setValorIPIOutras( null );			
			traillerEntradas.setValorSubTributaria( null );			
			traillerEntradas.setBaseSubTriburaria( null );			
			traillerEntradas.setValorICMSSubTributaria( null );			
			traillerEntradas.setValorDiferidas( null );
			traillerEntradas.setSequencial( sequencial++ );

			readrows.add( traillerEntradas.toString() );
		}

		rs.close();
		ps.close();

		con.commit();*/
	}
	
	private void headerSaida() throws Exception {
		
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
		
		HeaderSaida headerSaida = new HeaderSaida();
		headerSaida.setDataArquivo( Calendar.getInstance().getTime() );
		headerSaida.setCnpj( cnpj );
		headerSaida.setSequencial( sequencial++ );
		
		readrowsSaida.add( headerSaida.toString() );
	}
	
	private void saidas() throws Exception {

		StringBuilder sql = new StringBuilder();		
		sql.append( "SELECT " );
		sql.append( "V.CODVENDA," );
		sql.append( "V.DOCVENDA," );
		sql.append( "V.DTEMITVENDA," );
		sql.append( "TM.CODMODNOTA," );
		sql.append( "V.SERIE," );
		sql.append( "V.VLRLIQVENDA," );
		sql.append( "V.VLRPRODVENDA," );
		sql.append( "V.VLRIPIVENDA," );
		sql.append( "TM.ESPECIETIPOMOV " );
		sql.append( "FROM VDVENDA V, EQTIPOMOV TM " );
		sql.append( "WHERE V.CODEMP=? AND V.CODFILIAL=? AND V.DTEMITVENDA BETWEEN ? AND ? AND " );
		sql.append( "TM.CODEMP=V.CODEMPTM AND TM.CODFILIAL=V.CODFILIALTM AND TM.CODTIPOMOV=V.CODTIPOMOV" );

		PreparedStatement ps = con.prepareStatement( sql.toString() );
		ps.setInt( 1, Aplicativo.iCodEmp );
		ps.setInt( 2, ListaCampos.getMasterFilial( "VDVENDA" ) );
		ps.setDate( 3, Funcoes.dateToSQLDate( dtini ) );
		ps.setDate( 4, Funcoes.dateToSQLDate( dtfim ) );

		ResultSet rs = ps.executeQuery();
		Saida saida = null;
		boolean readHeader = true;
		boolean readTrailler = false;

		while ( rs.next() ) {
			
			if ( readHeader ) {
				headerSaida();
				readHeader = false;
				readTrailler = true;
			}
			
			saida = new Saida();			
			saida.setDataLancamento( Calendar.getInstance().getTime() );			
			saida.setNumeroInicial( rs.getInt( "DOCVENDA" ) );			
			saida.setNumeroFinal( 0 );			
			saida.setDataEmissao( rs.getDate( "DTEMITVENDA" ) );			
			saida.setModelo( rs.getInt( "CODMODNOTA" ) );			
			saida.setSerie( rs.getString( "SERIE" ) );			
			saida.setSubSerie( null );			
			saida.setCfop( 0 );			
			saida.setVariacaoCfop( 0 );			
			saida.setClassificacao1( 01 ); // Padrão do Cordilheira			
			saida.setClassificacao2( 0 );			
			saida.setCnpjDestinatario( null );			
			saida.setValorNota( rs.getBigDecimal( "VLRLIQVENDA" ) );			
			saida.setBasePIS( null );			
			saida.setBaseCOFINS( null );			
			saida.setBaseCSLL( null );			
			saida.setBaseIRPJ( null );	
			
			saida.setBaseICMSa( null );			
			saida.setAliquotaICMSa( null );			
			saida.setValorICMSa( null );			
			saida.setBaseICMSb( null );			
			saida.setAliquotaICMSb( null );			
			saida.setValorICMSb( null );			
			saida.setBaseICMSc( null );			
			saida.setAliquotaICMSc( null );			
			saida.setValorICMSc( null );			
			saida.setBaseICMSd( null );			
			saida.setAliquotaICMSd( null );		
			
			saida.setValorICMSd( null );			
			saida.setValorICMSIsentas( null );			
			saida.setValorICMSOutras( null );	
			
			saida.setBaseIPI( rs.getBigDecimal( "VLRLIQVENDA" ) );			
			saida.setValorIPI( rs.getBigDecimal( "VLRPRODVENDA" ) );			
			saida.setValorIPIIsentas( null );			
			saida.setValorIPIOutras( null );			
			saida.setValorSubTributaria( null );			
			saida.setBaseSubTributaria( null );			
			saida.setValorICMSSubTributaria( null );			
			saida.setValorDiferidas( null );			
			saida.setBaseISS( null );			
			saida.setAliquotaISS( null );			
			saida.setValorISS( null );			
			saida.setValorISSIsentos( null );			
			saida.setValorIRRF( null );			
			saida.setObservacoesLivrosFiscais( null );			
			saida.setEspecie( rs.getString( "ESPECIETIPOMOV" ) );				
			saida.setVendaAVista( null );						
			saida.setCfopSubTributaria( 0 );			
			saida.setValorPISCOFINS( null );			
			saida.setModalidadeFrete( 0 );			
			saida.setValorPIS( null );			
			saida.setValorCOFINS( null );			
			saida.setValorCSLL( null );			
			saida.setDataRecebimento( null );			
			saida.setOperacaoContabil( 0 );			
			saida.setValorMateriais( null );			
			saida.setValorSubEmpreitada( null );			
			saida.setCodigoServico( 0 );			
			saida.setClifor( 0 );			
			saida.setIndentificadorExterior( null );
			saida.setSequencial( sequencial++ );
			
			emitente( 'C', rs.getInt( "codcli" ) );

			readrowsSaida.add( saida.toString() );
			
			itensSaida();
		}
		
		if ( readTrailler ) {
			traillerSaida();
		}

		rs.close();
		ps.close();

		con.commit();
	}

	private void itensSaida() throws Exception {

		StringBuilder sql = new StringBuilder();		
		sql.append( "" );

		PreparedStatement ps = con.prepareStatement( sql.toString() );
		ps.setInt( 1, Aplicativo.iCodEmp );
		ps.setInt( 2, ListaCampos.getMasterFilial( "VDITVENDA" ) );
		ps.setDate( 3, Funcoes.dateToSQLDate( dtini ) );
		ps.setDate( 4, Funcoes.dateToSQLDate( dtfim ) );

		ResultSet rs = ps.executeQuery();
		ItemSaida itemSaida = null;

		while ( rs.next() ) {
			
			itemSaida = new ItemSaida();				
			itemSaida.setCodigoItem( 0 );			
			itemSaida.setQuantidade( null );			
			itemSaida.setValor( null );			
			itemSaida.setQuantidade2( null );			
			itemSaida.setDesconto( null );			
			itemSaida.setBaseICMS( null );			
			itemSaida.setAliquotaICMS( null );			
			itemSaida.setValorIPI( null );			
			itemSaida.setBaseICMSSubTributaria( null );			
			itemSaida.setAliquotaIPI( null );			
			itemSaida.setPercentualReducaoBaseICMS( null );			
			itemSaida.setSituacaoTributaria( 0 );			
			itemSaida.setIndentificacao( null );			
			itemSaida.setSituacaoTributariaIPI( 0 );			
			itemSaida.setBaseIPI( null );			
			itemSaida.setSituacaoTributariaPIS( 0 );			
			itemSaida.setBasePIS( null );			
			itemSaida.setAliquotaPIS( null );			
			itemSaida.setQuantidadeBasePIS( null );			
			itemSaida.setValorAliquotaPIS( null );			
			itemSaida.setValorPIS( null );			
			itemSaida.setSituacaoTributariaCOFINS( 0 );			
			itemSaida.setBaseCOFINS( null );			
			itemSaida.setAliquotaCOFINS( null );			
			itemSaida.setValorCOFINS( null );		
			itemSaida.setValorICMSSubTributaria( null );
			itemSaida.setSequencial( sequencial++ );

			readrowsSaida.add( itemSaida.toString() );
		}

		rs.close();
		ps.close();

		con.commit();
	}

	private void traillerSaida() throws Exception {

		StringBuilder sql = new StringBuilder();		
		sql.append( "" );

		PreparedStatement ps = con.prepareStatement( sql.toString() );
		ps.setInt( 1, Aplicativo.iCodEmp );
		ps.setInt( 2, ListaCampos.getMasterFilial( "CPITCOMPRA" ) );
		ps.setDate( 3, Funcoes.dateToSQLDate( dtini ) );
		ps.setDate( 4, Funcoes.dateToSQLDate( dtfim ) );

		ResultSet rs = ps.executeQuery();
		TraillerSaida traillerSaida = null;

		while ( rs.next() ) {
			
			traillerSaida = new TraillerSaida();			
			traillerSaida.setValorNota( null );			
			traillerSaida.setBasePIS( null );			
			traillerSaida.setBaseCOFINS( null );			
			traillerSaida.setBaseCSLL( null );			
			traillerSaida.setBaseIRPJ( null );			
			traillerSaida.setBaseICMSa( null );			
			traillerSaida.setValorICMSa( null );			
			traillerSaida.setBaseICMSb( null );			
			traillerSaida.setValorICMSb( null );
			traillerSaida.setBaseICMSc( null );			
			traillerSaida.setValorICMSc( null );			
			traillerSaida.setBaseICMSd( null );			
			traillerSaida.setValorICMSd( null );			
			traillerSaida.setValorICMSIsentas( null );			
			traillerSaida.setValorICMSOutras( null );			
			traillerSaida.setBaseIPI( null );			
			traillerSaida.setValorIPI( null );			
			traillerSaida.setValorIPIIsentas( null );			
			traillerSaida.setValorIPIOutras( null );			
			traillerSaida.setValorMercadoriasSubTributaria( null );			
			traillerSaida.setBaseSubTributaria( null );			
			traillerSaida.setValorICMSSubTributarias( null );			
			traillerSaida.setValorDireridas( null );			
			traillerSaida.setBaseISS( null );			
			traillerSaida.setValorISS( null );			
			traillerSaida.setValorIsentas( null );			
			traillerSaida.setValorIRRFISS( null ); 
			traillerSaida.setSequencial( sequencial++ );

			readrowsSaida.add( traillerSaida.toString() );
		}

		rs.close();
		ps.close();

		con.commit();
	}
		
	private String format( String text, int tam ) {
		
		String strTmp = "";

		if ( text != null ) {
			strTmp = text;
		}

		return Funcoes.adicionaEspacos( strTmp, tam );
	}

	private String format( BigDecimal value, int size, int decimal ) {
		
		BigDecimal valueTmp = null;
		
		if ( value != null ) {
			valueTmp = value.setScale( decimal, BigDecimal.ROUND_HALF_UP )
						.divide( new BigDecimal( "1" + Funcoes.strZero( "0", decimal ) ) ); 
		}
		else {
			valueTmp = new BigDecimal( "0.00" ); 
		}
		
		return Funcoes.strZero( String.valueOf( valueTmp ).replace( ".", "" ), size );
	}

	private String format( int value, int size ) {
		
		return Funcoes.strZero( String.valueOf( value ), size );
	}

	private String format( Date date ) {
		
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime( date );
		StringBuffer result = new StringBuffer();
		result.append( Funcoes.strZero( String.valueOf( cal.get( Calendar.DAY_OF_MONTH ) ), 2 ) );
		result.append( Funcoes.strZero( String.valueOf( cal.get( Calendar.MONTH ) + 1 ), 2 ) );
		result.append( Funcoes.strZero( String.valueOf( cal.get( Calendar.YEAR ) ), 4 ) );
		
		return result.toString();
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
		
		private int cep;
		
		private int municipio;
		
		private	int ddd;
		
		private int telefone;
		
		private int contaCliente;
		
		private int historicoCliente;
		
		private int contaFornecedor;
		
		private boolean produtor;
		
		private int historicoFornecedor;
		
		private String indentificacaoExterior;
		
		private int numero;
		
		private String complemento;
		
		private String suframa;
		
		private int pais;
		
		private int sequencial;

		private int getTipoRegistro() {
			return tipoRegistro;
		}

		private String getCnpj() {
			return cnpj;
		}

		private void setCnpj( String cnpj ) {
			this.cnpj = cnpj;
		}

		private String getRazaoSocial() {
			return razaoSocial;
		}

		private void setRazaoSocial( String razaoSocial ) {
			this.razaoSocial = razaoSocial;
		}

		private String getNomeFantazia() {
			return nomeFantazia;
		}

		private void setNomeFantazia( String nomeFantazia ) {
			this.nomeFantazia = nomeFantazia;
		}

		private String getEstado() {
			return estado;
		}

		private void setEstado( String estado ) {
			this.estado = estado;
		}

		private String getInscricao() {
			return inscricao;
		}

		private void setInscricao( String inscricao ) {
			this.inscricao = inscricao;
		}

		private String getEndereco() {
			return endereco;
		}

		private void setEndereco( String endereco ) {
			this.endereco = endereco;
		}

		private String getBairro() {
			return bairro;
		}

		private void setBairro( String bairro ) {
			this.bairro = bairro;
		}

		private String getCidade() {
			return cidade;
		}

		private void setCidade( String cidade ) {
			this.cidade = cidade;
		}

		private int getCep() {
			return cep;
		}

		private void setCep( int cep ) {
			this.cep = cep;
		}

		private int getMunicipio() {
			return municipio;
		}

		private void setMunicipio( int municipio ) {
			this.municipio = municipio;
		}

		private int getDdd() {
			return ddd;
		}

		private void setDdd( int ddd ) {
			this.ddd = ddd;
		}

		private int getTelefone() {
			return telefone;
		}

		private void setTelefone( int telefone ) {
			this.telefone = telefone;
		}

		private int getContaCliente() {
			return contaCliente;
		}

		private void setContaCliente( int contaCliente ) {
			this.contaCliente = contaCliente;
		}

		private int getHistoricoCliente() {
			return historicoCliente;
		}

		private void setHistoricoCliente( int historicoCliente ) {
			this.historicoCliente = historicoCliente;
		}

		private int getContaFornecedor() {
			return contaFornecedor;
		}

		private void setContaFornecedor( int contaFornecedor ) {
			this.contaFornecedor = contaFornecedor;
		}

		private boolean isProdutor() {
			return produtor;
		}

		private void setProdutor( boolean produtor ) {
			this.produtor = produtor;
		}

		private int getHistoricoFornecedor() {
			return historicoFornecedor;
		}

		private void setHistoricoFornecedor( int historicoFornecedor ) {
			this.historicoFornecedor = historicoFornecedor;
		}

		private String getIndentificacaoExterior() {
			return indentificacaoExterior;
		}

		private void setIndentificacaoExterior( String indentificacaoExterior ) {
			this.indentificacaoExterior = indentificacaoExterior;
		}

		private int getNumero() {
			return numero;
		}

		private void setNumero( int numero ) {
			this.numero = numero;
		}

		private String getComplemento() {
			return complemento;
		}

		private void setComplemento( String complemento ) {
			this.complemento = complemento;
		}

		private String getSuframa() {
			return suframa;
		}

		private void setSuframa( String suframa ) {
			this.suframa = suframa;
		}

		private int getPais() {
			return pais;
		}

		private void setPais( int pais ) {
			this.pais = pais;
		}

		private int getSequencial() {
			return sequencial;
		}

		private void setSequencial( int sequencial ) {
			this.sequencial = sequencial;
		}

		@Override
		public String toString() {

			StringBuilder emitenteDestinatario = new StringBuilder( 500 );
			
			emitenteDestinatario.append( getTipoRegistro() );
			emitenteDestinatario.append( format( Funcoes.setMascara( getCnpj(), "##.###.###/####-##" ), 18 ) );			
			emitenteDestinatario.append( format( getRazaoSocial(), 40 ) );			
			emitenteDestinatario.append( format( getNomeFantazia(), 20 ) );			
			emitenteDestinatario.append( format( getEstado(), 2 ) );			
			emitenteDestinatario.append( format( getInscricao(), 20 ) );		
			emitenteDestinatario.append( format( getEndereco(), 40 ) );			
			emitenteDestinatario.append( format( getBairro(), 20 ) );			
			emitenteDestinatario.append( format( getCidade(), 20 ) );			
			emitenteDestinatario.append( format( getCep(), 8 ) );			
			emitenteDestinatario.append( format( getMunicipio(), 4 ) );			
			emitenteDestinatario.append( format( getDdd(), 3 ) );			
			emitenteDestinatario.append( format( getTelefone(), 10 ) );			
			emitenteDestinatario.append( format( getContaCliente(), 6 ) );			
			emitenteDestinatario.append( format( getHistoricoCliente(), 3 ) );			
			emitenteDestinatario.append( format( getContaFornecedor(), 6 ) );			
			emitenteDestinatario.append( format( getHistoricoFornecedor(), 3 ) );			
			emitenteDestinatario.append( isProdutor() ? "S" : "N" );			
			emitenteDestinatario.append( format( getIndentificacaoExterior(), 18 ) );			
			emitenteDestinatario.append( format( getNumero(), 5 ) );			
			emitenteDestinatario.append( format( getComplemento(), 20 ) );			
			emitenteDestinatario.append( format( getSuframa(), 9 ) );			
			emitenteDestinatario.append( format( getPais(), 5 ) );				
			emitenteDestinatario.append( format( " ", 207 ) );				
			emitenteDestinatario.append( format( " ", 5 ) );			
			emitenteDestinatario.append( format( getSequencial(), 6 ) );
			
			return emitenteDestinatario.toString();
		}
	}
		
	private class HeaderEntrada {
		
		private final int tipoRegistro = 0;
		
		private Date dataArquivo;
		
		private String cnpj;
		
		private int calculaBases;
		
		private int sequencial;
		
		
		private HeaderEntrada() {
			setCalculaBases( 1 );
		}
		
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

			StringBuilder headerEntrada = new StringBuilder( 500 );
			
			headerEntrada.append( getTipoRegistro() );
			headerEntrada.append( format( getDataArquivo() ) );
			headerEntrada.append( format( Funcoes.setMascara( getCnpj(), "##.###.###/####-##" ), 18 ) );
			headerEntrada.append( getCalculaBases() );
			headerEntrada.append( format( " ", 3 ) );
			headerEntrada.append( format( " ", 443 ) );
			headerEntrada.append( format( " ", 20 ) );
			headerEntrada.append( format( getSequencial(), 6 ) );
			
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
			entrada.append( format( getDataLancamento() ) );
			entrada.append( format( getNota(), 6 ) );
			entrada.append( format( getDataEmissao() ) );
			entrada.append( format( getModeloNota(), 2 ) );
			entrada.append( format( getSerie(), 3 ) );
			entrada.append( format( getSubSerie(), 3 ) );
			entrada.append( format( getCfop(), 4 ) );
			entrada.append( format( getVariacaoCfop(), 2 ) );
			entrada.append( format( getClassificacaoIntegracao(), 2 ) );
			entrada.append( format( getClassificacaoIntegracao2(), 2 ) );
			entrada.append( format( Funcoes.setMascara( getCnfjFornecedor(), "##.###.###/####-##" ), 18 ) );
			entrada.append( format( getValorNota(), 12, 2 ) );
			entrada.append( format( getBasePIS(), 12, 2 ) );
			entrada.append( format( getBaseCOFINS(), 12, 2 ) );
			entrada.append( format( getBaseCSLL(), 12, 2 ) );
			entrada.append( format( getBaseIR(), 12, 2 ) );
			entrada.append( format( getBaseICMSa(), 12, 2 ) );
			entrada.append( format( getAliquotaICMSa(), 4, 2 ) );
			entrada.append( format( getValorICMSa(), 12, 2 ) );
			entrada.append( format( getBaseICMSb(), 12, 2 ) );
			entrada.append( format( getAliquotaICMSb(), 4, 2 ) );
			entrada.append( format( getValorICMSb(), 12, 2 ) );
			entrada.append( format( getBaseICMSc(), 12, 2 ) );
			entrada.append( format( getAliquotaICMSc(), 4, 2 ) );
			entrada.append( format( getValorICMSc(), 12, 2 ) );
			entrada.append( format( getBaseICMSd(), 12, 2 ) );
			entrada.append( format( getAliquotaICMSd(), 4, 2 ) );
			entrada.append( format( getValorICMSd(), 12, 2 ) );
			entrada.append( format( getValorICMSIsentas(), 12, 2 ) );
			entrada.append( format( getValorICMSOutras(), 12, 2 ) );
			entrada.append( format( getBaseIPI(), 12, 2 ) );
			entrada.append( format( getValorIPI(), 12, 2 ) );
			entrada.append( format( getValorIPIIsentas(), 12, 2 ) );
			entrada.append( format( getValorIPIOutras(), 12, 2 ) );
			entrada.append( format( getValorSubTributaria(), 12, 2 ) );
			entrada.append( format( getBaseSubTributaria(), 12, 2 ) );
			entrada.append( format( getValorICMSSubTributaria(), 12, 2 ) );
			entrada.append( format( getValorDiferidas(), 12, 2 ) );
			entrada.append( format( getObservacaoLivroFiscal(), 50 ) );
			entrada.append( format( getEspecieNota(), 5 ) );
			entrada.append( format( getVendaAVista(), 1 ) );
			entrada.append( format( getCfopSubTributaria(), 4 ) );
			entrada.append( format( getBasePISCOFINSSubTributaria(), 8, 2 ) );
			entrada.append( format( getBaseISS(), 12, 2 ) );
			entrada.append( format( getAliquotaISS(), 4, 2 ) );
			entrada.append( format( getValorISS(), 12, 2 ) );
			entrada.append( format( getValorISSIsentas(), 12, 2 ) );
			entrada.append( format( getValorIRRF(), 12, 2 ) );
			entrada.append( format( getValorPIS(), 12, 2 ) );
			entrada.append( format( getValorCOFINS(), 12, 2 ) );
			entrada.append( format( getValorCSLL(), 12, 2 ) );
			entrada.append( format( getDataPagamento() ) );
			entrada.append( format( getCodigoOperacaoContabil(), 4 ) );
			entrada.append( format( " ", 6 ) );			
			entrada.append( format( getIndentificacaoExterior(), 18 ) );			
			entrada.append( format( getValorINSS(), 12, 2 ) );			
			entrada.append( format( getValorFUNRURAL(), 12, 2 ) );			
			entrada.append( format( getCodigoItemServico(), 4 ) );			
			entrada.append( format( " ", 18 ) );					
			entrada.append( format( " ", 5 ) );			
			entrada.append( format( getSequencial(), 6 ) );
			
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
			itemEntrada.append( format( getCodigo(), 10 ) );			
			itemEntrada.append( format( getQuantidade(), 9, 3 ) );		
			itemEntrada.append( format( getValor(), 9, 2 ) );			
			itemEntrada.append( format( getQuantidade2(), 13, 3 ) );			
			itemEntrada.append( format( getDesconto(), 12, 2 ) );			
			itemEntrada.append( format( getBaseICMS(), 12, 2 ) );			
			itemEntrada.append( format( getAliquotaICMS(), 5, 2 ) );			
			itemEntrada.append( format( getValorIPI(), 12, 2 ) );			
			itemEntrada.append( format( getBaseICMSSusTributaria(), 12, 2 ) );			
			itemEntrada.append( format( getAliquotaIPI(), 5, 2 ) );		
			itemEntrada.append( format( getPercentualReducaoBaseICMS(), 5, 2 ) );			
			itemEntrada.append( format( getSituacaoTributaria(), 3 ) );			
			itemEntrada.append( format( getIndentificacao(), 15 ) );			
			itemEntrada.append( format( getSituacaoTributariaIPI(), 3 ) );			
			itemEntrada.append( format( getBaseIPI(), 12, 2 ) );			
			itemEntrada.append( format( getSituacaoTributariaPIS(), 3 ) );			
			itemEntrada.append( format( getBasePIS(), 12, 2 ) );			
			itemEntrada.append( format( getAliquotaPIS(), 5, 2 ) );			
			itemEntrada.append( format( getQuantidadeBasePIS(), 12, 2 ) );			
			itemEntrada.append( format( getValorAliquotaPIS(), 12, 2 ) );			
			itemEntrada.append( format( getValorPIS(), 12, 2 ) );			
			itemEntrada.append( format( getSituacaoTributariaCOFINS(), 3 ) );			
			itemEntrada.append( format( getBaseCOFINS(), 12, 2 ) );			
			itemEntrada.append( format( getAliquotaCOFINS(), 5, 2 ) );			
			itemEntrada.append( format( getQuantidadeBaseCOFINS(), 12, 2 ) );			
			itemEntrada.append( format( getValorAliquotaCOFINS(), 12, 2 ) );			
			itemEntrada.append( format( getValorICMSSubTributaria(), 12, 2 ) );			
			itemEntrada.append( format( " ", 224 ) );				
			itemEntrada.append( format( " ", 5 ) );			
			itemEntrada.append( format( getSequencial(), 6 ) );
			
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
			trallerEntrada.append( format( getValorNota(), 12, 2 ) );			
			trallerEntrada.append( format( getBasePIS(), 12, 2 ) );			
			trallerEntrada.append( format( getBaseCOFINS(), 12, 2 ) );			
			trallerEntrada.append( format( getBaseContribuicaoSocial(), 12, 2 ) );			
			trallerEntrada.append( format( getBaseImpostoRenda(), 12, 2 ) );			
			trallerEntrada.append( format( getBaseICMSa(), 12, 2 ) );			
			trallerEntrada.append( format( getValorICMSa(), 12, 2 ) );			
			trallerEntrada.append( format( getBaseICMSb(), 12, 2 ) );			
			trallerEntrada.append( format( getValorICMSb(), 12, 2 ) );			
			trallerEntrada.append( format( getBaseICMSc(), 12, 2 ) );			
			trallerEntrada.append( format( getValorICMSc(), 12, 2 ) );			
			trallerEntrada.append( format( getBaseICMSd(), 12, 2 ) );			
			trallerEntrada.append( format( getValorICMSd(), 12, 2 ) );			
			trallerEntrada.append( format( getValorICMSIsentas(), 12, 2 ) );			
			trallerEntrada.append( format( getValorICMSOutras(), 12, 2 ) );			
			trallerEntrada.append( format( getBaseIPI(), 12, 2 ) );			
			trallerEntrada.append( format( getValorIPI(), 12, 2 ) );			
			trallerEntrada.append( format( getValorIPIIsentas(), 12, 2 ) );			
			trallerEntrada.append( format( getValorIPIOutras(), 12, 2 ) );			
			trallerEntrada.append( format( getValorSubTributaria(), 12, 2 ) );			
			trallerEntrada.append( format( getBaseSubTriburaria(), 12, 2 ) );			
			trallerEntrada.append( format( getValorICMSSubTributaria(), 12, 2 ) );		
			trallerEntrada.append( format( getValorDiferidas(), 12, 2 ) );
			trallerEntrada.append( format( " ", 217 ) );
			trallerEntrada.append( format( getSequencial(), 6 ) );
			
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
			headerSaida.append( format( getDataArquivo() ) );	
			headerSaida.append( format( getCnpj(), 18 ) );
			headerSaida.append( format( getCalculaBases(), 1 ) );
			headerSaida.append( format( " ", 3 ) );
			headerSaida.append( format( " ", 443 ) );
			headerSaida.append( format( " ", 20 ) );
			headerSaida.append( format( getSequencial(), 6 ) );
			
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
			saida.append( format( getDataLancamento() ) );			
			saida.append( format( getNumeroInicial(), 6 ) );			
			saida.append( format( getNumeroFinal(), 6 ) );			
			saida.append( format( getDataEmissao() ) );
			saida.append( format( " ", 3 ) );
			saida.append( format( getModelo(), 2 ) );			
			saida.append( format( getSerie(), 3 ) );			
			saida.append( format( getSubSerie(), 3 ) );			
			saida.append( format( getCfop(), 4 ) );			
			saida.append( format( getVariacaoCfop(), 2 ) );		
			saida.append( format( getClassificacao1(), 2 ) );			
			saida.append( format( getClassificacao2(), 2 ) );			
			saida.append( format( getCnpjDestinatario(), 18 ) );			
			saida.append( format( getValorNota(), 12, 2 ) );			
			saida.append( format( getBasePIS(), 12, 2 ) );			
			saida.append( format( getBaseCOFINS(), 12, 2 ) );			
			saida.append( format( getBaseCSLL(), 12, 2 ) );
			saida.append( format( getBaseIRPJ(), 12, 2 ) );
			saida.append( format( " ", 8 ) );
			saida.append( format( getBaseICMSa(), 12, 2 ) );			
			saida.append( format( getAliquotaICMSa(), 4, 2 ) );			
			saida.append( format( getValorICMSa(), 12, 2 ) );			
			saida.append( format( getBaseICMSb(), 12, 2 ) );			
			saida.append( format( getAliquotaICMSb(), 4, 2 ) );			
			saida.append( format( getValorICMSb(), 12, 2 ) );			
			saida.append( format( getBaseICMSc(), 12, 2 ) );			
			saida.append( format( getAliquotaICMSc(), 4, 2 ) );			
			saida.append( format( getValorICMSc(), 12, 2 ) );			
			saida.append( format( getBaseICMSd(), 12, 2 ) );			
			saida.append( format( getAliquotaICMSd(), 4, 2 ) );			
			saida.append( format( getValorICMSd(), 12, 2 ) );			
			saida.append( format( getValorICMSIsentas(), 12, 2 ) );			
			saida.append( format( getValorICMSOutras(), 12, 2 ) );			
			saida.append( format( getBaseIPI(), 12, 2 ) );			
			saida.append( format( getValorIPI(), 12, 2 ) );			
			saida.append( format( getValorIPIIsentas(), 12, 2 ) );			
			saida.append( format( getValorIPIOutras(), 12, 2 ) );			
			saida.append( format( getValorSubTributaria(), 12, 2 ) );			
			saida.append( format( getBaseSubTributaria(), 12, 2 ) );			
			saida.append( format( getValorICMSSubTributaria(), 12, 2 ) );			
			saida.append( format( getValorDiferidas(), 12, 2 ) );			
			saida.append( format( getBaseISS(), 12, 2 ) );			
			saida.append( format( getAliquotaISS(), 4, 2 ) );			
			saida.append( format( getValorISS(), 12, 2 ) );			
			saida.append( format( getValorISSIsentos(), 12, 2 ) );			
			saida.append( format( getValorIRRF(), 12, 2 ) );			
			saida.append( format( getObservacoesLivrosFiscais(), 50 ) );			
			saida.append( format( getEspecie(), 5 ) );			
			saida.append( format( getVendaAVista(), 1 ) );			
			saida.append( format( getCfopSubTributaria(), 4 ) );			
			saida.append( format( getValorPISCOFINS(), 12, 2 ) );			
			saida.append( format( getModalidadeFrete(), 1 ) );			
			saida.append( format( getValorPIS(), 12, 2 ) );			
			saida.append( format( getValorCOFINS(), 12, 2 ) );			
			saida.append( format( getValorCSLL(), 12, 2 ) );			
			saida.append( format( getDataRecebimento() ) );			
			saida.append( format( getOperacaoContabil(), 4 ) );			
			saida.append( format( getValorMateriais(), 12, 2 ) );			
			saida.append( format( getValorSubEmpreitada(), 12, 2 ) );			
			saida.append( format( getCodigoServico(), 4 ) );			
			saida.append( format( getClifor(), 6 ) );			
			saida.append( format( getIndentificadorExterior(), 18 ) );		
			saida.append( format( " ", 5 ) );
			saida.append( format( getSequencial(), 6 ) );
			
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
			itemSaida.append( format( getCodigoItem(), 10 ) );			
			itemSaida.append( format( getQuantidade(), 12, 2 ) );			
			itemSaida.append( format( getValor(), 12, 2 ) );			
			itemSaida.append( format( getQuantidade2(), 13, 3 ) );			
			itemSaida.append( format( getDesconto(), 12, 2 ) );			
			itemSaida.append( format( getBaseICMS(), 12, 2 ) );			
			itemSaida.append( format( getAliquotaICMS(), 5, 2 ) );			
			itemSaida.append( format( getValorIPI(), 12, 2 ) );			
			itemSaida.append( format( getBaseICMSSubTributaria(), 12, 2 ) );			
			itemSaida.append( format( getAliquotaIPI(), 5, 2 ) );			
			itemSaida.append( format( getPercentualReducaoBaseICMS(), 5, 2 ) );			
			itemSaida.append( format( getSituacaoTributaria(), 3 ) );			
			itemSaida.append( format( getIndentificacao(), 15 ) );			
			itemSaida.append( format( getSituacaoTributariaIPI(), 3 ) );			
			itemSaida.append( format( getBaseIPI(), 12, 2 ) );			
			itemSaida.append( format( getSituacaoTributariaPIS(), 3 ) );			
			itemSaida.append( format( getBasePIS(), 12, 2 ) );			
			itemSaida.append( format( getAliquotaPIS(), 5, 2 ) );			
			itemSaida.append( format( getQuantidadeBasePIS(), 12, 2 ) );			
			itemSaida.append( format( getValorAliquotaPIS(), 12, 2 ) );			
			itemSaida.append( format( getValorPIS(), 12, 2 ) );			
			itemSaida.append( format( getSituacaoTributariaCOFINS(), 3 ) );			
			itemSaida.append( format( getBaseCOFINS(), 12, 2 ) );			
			itemSaida.append( format( getAliquotaCOFINS(), 5, 2 ) );			
			itemSaida.append( format( getValorCOFINS(), 12, 2 ) );			
			itemSaida.append( format( getValorICMSSubTributaria(), 12, 2 ) );		
			itemSaida.append( format( " ", 224 ) );					
			itemSaida.append( format( " ", 5 ) );			
			itemSaida.append( format( getSequencial(), 6 ) );
			
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
			itemSaida.append( format( getValorNota(), 12, 2 ) );		
			itemSaida.append( format( getBasePIS(), 12, 2 ) );			
			itemSaida.append( format( getBaseCOFINS(), 12, 2 ) );			
			itemSaida.append( format( getBaseCSLL(), 12, 2 ) );			
			itemSaida.append( format( getBaseIRPJ(), 12, 2 ) );			
			itemSaida.append( format( getBaseICMSa(), 12, 2 ) );			
			itemSaida.append( format( getValorICMSa(), 12, 2 ) );			
			itemSaida.append( format( getBaseICMSb(), 12, 2 ) );			
			itemSaida.append( format( getValorICMSb(), 12, 2 ) );			
			itemSaida.append( format( getBaseICMSc(), 12, 2 ) );			
			itemSaida.append( format( getValorICMSc(), 12, 2 ) );			
			itemSaida.append( format( getBaseICMSd(), 12, 2 ) );			
			itemSaida.append( format( getValorICMSd(), 12, 2 ) );			
			itemSaida.append( format( getValorICMSIsentas(), 12, 2 ) );		
			itemSaida.append( format( getValorICMSOutras(), 12, 2 ) );			
			itemSaida.append( format( getBaseIPI(), 12, 2 ) ); 			
			itemSaida.append( format( getValorIPI(), 12, 2 ) ); 			
			itemSaida.append( format( getValorIPIIsentas(), 12, 2 ) );			
			itemSaida.append( format( getValorIPIOutras(), 12, 2 ) );			
			itemSaida.append( format( getValorMercadoriasSubTributaria(), 12, 2 ) );			
			itemSaida.append( format( getBaseSubTributaria(), 12, 2 ) );			
			itemSaida.append( format( getValorICMSSubTributarias(), 12, 2 ) );			
			itemSaida.append( format( getValorDireridas(), 12, 2 ) );			
			itemSaida.append( format( getBaseISS(), 12, 2 ) );			
			itemSaida.append( format( getValorISS(), 12, 2 ) );			
			itemSaida.append( format( getValorIsentas(), 12, 2 ) );
			itemSaida.append( format( getValorIRRFISS(), 12, 2 ) );			
			itemSaida.append( format( " ", 169 ) );			
			itemSaida.append( format( getSequencial(), 6 ) );
			
			return itemSaida.toString();
		}
	}
}
