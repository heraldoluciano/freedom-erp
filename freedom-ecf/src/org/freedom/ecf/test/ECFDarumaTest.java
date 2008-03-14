package org.freedom.ecf.test;

import static org.freedom.ecf.driver.EStatus.RETORNO_OK;
import junit.framework.TestCase;

import org.freedom.ecf.driver.ECFDaruma;

public class ECFDarumaTest extends TestCase {

	public ECFDarumaTest( String name ) {

		super( name );
	}

	public void testComandosDeInicializacao() {

		ECFDaruma ecf = new ECFDaruma( "COM1" );
		/*
		assertTrue( trataRetornoFuncao( ecf.alteraSimboloMoeda( "R" ) ) );

		assertTrue( trataRetornoFuncao( ecf.adicaoDeAliquotaTriburaria( "0001", ECFDaruma.ICMS ) ) );
		
		assertTrue( trataRetornoFuncao( ecf, ecf.programaHorarioVerao() ) );
		
		assertTrue( trataRetornoFuncao( ecf.nomeiaTotalizadorNaoSujeitoICMS( 4, "Totalizador teste" ) ) );

		assertTrue( trataRetornoFuncao( ecf.programaTruncamentoArredondamento( '1' ) ) );

		assertTrue( trataRetornoFuncao( ecf.programarEspacoEntreLinhas( 8 ) ) );

		assertTrue( trataRetornoFuncao( ecf.programarLinhasEntreCupons( 5 ) ) );

		assertTrue( trataRetornoFuncao( ecf.nomeiaDepartamento( 2, "Teste" ) ) );*/
	}
	
	public void testCancelaCupom() {

		ECFDaruma ecf = new ECFDaruma( "COM1" );

		System.out.print( "cancelamento de Cupom > " );
		assertTrue( trataRetornoFuncao( ecf, ecf.cancelaCupom() ) );		
	}

	public void testComandosDeCupomFiscal() {

		ECFDaruma ecf = new ECFDaruma( "COM1" );
		
		System.out.print( "aberturaDeCupom > " );
		assertTrue( trataRetornoFuncao( ecf, ecf.aberturaDeCupom() ) );
		/*
		System.out.print( "aberturaDeCupom String > " );
		assertTrue( trataRetornoFuncao( ecf, ecf.aberturaDeCupom( 
				"00.000.000/0000-00         " +
				"Nome do Cliente                           " +
				"Endereço do Cliente nº99" ) ) );
		*/
		System.out.print( "vendaItem > " );
		assertTrue( trataRetornoFuncao( ecf, ecf.vendaItem( 
				"0000000000001", "Produto Teste                ", "FF", 'I', 1f, 10f, 'D', 0f ) ) );
		/*
		System.out.print( "subtotal > " );
		System.out.println( ecf.retornoSubTotal() );
		
		System.out.print( "programaUnidadeMedida > " );
		assertTrue( trataRetornoFuncao( ecf, ecf.programaUnidadeMedida( "Kg" ) ) );

		System.out.print( "vendaItem > " );
		assertTrue( trataRetornoFuncao( ecf, ecf.vendaItem( 
				"0000000000001", "Produto Teste                ", "FF", 'I', 1f, 10f, 'D', 0f ) ) );

		System.out.print( "cancelaItemAnterior > " );
		assertTrue( trataRetornoFuncao( ecf, ecf.cancelaItemAnterior() ) );

		System.out.print( "aumentaDescItem > " ); 
		assertTrue( trataRetornoFuncao( ecf, ecf.aumentaDescItem( 
				"Descricao do item aumentada para 60 caracteres" ) ) );
		 
		System.out.print( "vendaItemTresCasas > " ); 
		assertTrue( trataRetornoFuncao( ecf, ecf.vendaItemTresCasas( 
				"1234567890002", "Produto Teste                ", "FF", 'I', 2f, 2.050f, 'D', 0.10f ) ) );
		
		System.out.print( "vendaItemDepartamento > " ); 
		assertTrue( trataRetornoFuncao( ecf, ecf.vendaItemDepartamento( 
				"FF", 1f, 10f, 0.50f, 0.50f, 2, "Kg", "1234567890003", "Descricao do produto" ) ) ); 
		 
		System.out.print( "cancelaItemGenerico 2 > " ); 
		assertTrue( trataRetornoFuncao( ecf, ecf.cancelaItemGenerico( 2 ) ) );		
		*/
		System.out.print( "iniciaFechamentoCupom > " );
		assertTrue( trataRetornoFuncao( ecf, ecf.iniciaFechamentoCupom( ECFDaruma.ACRECIMO_VALOR, 0.00f ) ) );
		/*
		System.out.print( "efetuaFormaPagamento Dinheiro > " );
		assertTrue( trataRetornoFuncao( ecf, ecf.efetuaFormaPagamento( "01", 10.00f, null ) ) );
		*/
		System.out.print( "programaFormaPagamento > " ); 
		String f2 = ecf.programaFormaPagamento( "Cheque          " ); 
		System.out.println( f2 );
		assertTrue( ! "".equals( f2 ) );

		System.out.print( "efetuaFormaPagamento Cheque > " ); 
		assertTrue( trataRetornoFuncao( ecf, ecf.efetuaFormaPagamento( f2, 10f, "Cheque" ) ) );
		/*
		System.out.print( "estornoFormaPagamento > " ); 
		assertTrue( trataRetornoFuncao( ecf, ecf.estornoFormaPagamento( "Cheque          ", "Dinheiro", 5.50f ) ) );
		*/
		System.out.print( "finalizaFechamentoCupom > " );
		assertTrue( trataRetornoFuncao( ecf, ecf.finalizaFechamentoCupom( "Obrigado e volte sempre pra testar!" ) ) );


		//System.out.print( "cancelaCupom > " ); 
		//assertTrue( trataRetornoFuncao( ecf.cancelaCupom() ) );
	}
	
	public void testComandosDeOperacoesNaoFiscais() {

		ECFDaruma ecf = new ECFDaruma( "COM1" );
		/*	
		System.out.print( "relatorioGerencial > " ); 
		assertTrue( trataRetornoFuncao( ecf, ecf.relatorioGerencial( 
				"Abrindo Relatorio Gerencial                   \n" ) ) );	
		
		System.out.print( "relatorioGerencial usando > " ); 
		assertTrue( trataRetornoFuncao( ecf, ecf.relatorioGerencial( 
				"Utilizando Relatorio Gerencial                \n" ) ) );	
		
		System.out.print( "fechamentoRelatorioGerencial > " ); 
		assertTrue( trataRetornoFuncao( ecf, ecf.fechamentoRelatorioGerencial() ) );
		*/
		
		System.out.print( "comprovanteNFiscalNVinculado suprimento > " ); 
		assertTrue( trataRetornoFuncao( ecf, ecf.comprovanteNFiscalNVinculado( ECFDaruma.SUPRIMENTO, 50f, "Dinheiro        " ) ) );
		
		System.out.print( "comprovanteNFiscalNVinculado sangria > " ); 
		assertTrue( trataRetornoFuncao( ecf, ecf.comprovanteNFiscalNVinculado( ECFDaruma.SANGRIA, 45f, "Dinheiro        " ) ) );
		
		System.out.print( "comprovanteNFiscalNVinculado não ICMS > " ); 
		assertTrue( trataRetornoFuncao( ecf, ecf.comprovanteNFiscalNVinculado( "03", 35f, "Dinheiro        " ) ) );
		
		/*
		testComandosDeCupomFiscal();
		System.out.print( "abreComprovanteNFiscalVinculado > " ); 
		assertTrue( trataRetornoFuncao( ecf, ecf.abreComprovanteNFiscalVinculado( "Cheque          ", 10f, Integer.parseInt( ecf.retornoNumeroCupom() ) ) ) );
		
		System.out.print( "usaComprovanteNFiscalVinculado > " ); 
		assertTrue( trataRetornoFuncao( ecf, ecf.usaComprovanteNFiscalVinculado( 
				"Comprovante \nTeste\nteste\nteste..." ) ) );		

		System.out.print( "fechamentoRelatorioGerencial > " ); 
		assertTrue( trataRetornoFuncao( ecf, ecf.fechamentoRelatorioGerencial() ) );
		*/
	}
	
	public void testComandosDeAutenticacao() {
		
		//ECFDaruma ecf = new ECFDaruma( "COM1" );
		
		System.out.print( "programaCaracterParaAutenticacao > " ); 
		/*int [] sesc = {143,137,137,249,0,255,137,137,137,0,143,137,137,249,0,255,129,129 };
		int [] sesc = {1,2,4,8,16,32,64,128,64,32,16,8,4,2,1,129,129,129 };
		assertTrue( trataRetornoFuncao( ecf.programaCaracterParaAutenticacao( sesc ) ) );	
		
		testComandosDeCupomFiscal();
		System.out.print( "autenticacaoDeDocumento > " ); 
		assertTrue( trataRetornoFuncao( ecf.autenticacaoDeDocumento() ) );*/
	}
	
	public void testComandosDeRelatoriosFiscais() {
		
		ECFDaruma ecf = new ECFDaruma( "COM1" );

		
		System.out.print( "leituraX > " ); 
		assertTrue( trataRetornoFuncao( ecf, ecf.leituraX() ) );
		/*
		System.out.print( "leituraXSerial > " ); 
		assertTrue( trataRetornoFuncao( ecf.leituraXSerial() ) );
		System.out.println( new String( ecf.getBytesLidos() ) );
		
		Calendar cal = Calendar.getInstance();
		Date hoje = cal.getTime();
		cal.set( Calendar.MONTH, cal.get( Calendar.MONTH ) - 1 );
		Date antes = cal.getTime();
		
		System.out.print( "leituraMemoriaFiscal data > " );
		assertTrue( trataRetornoFuncao( ecf.leituraMemoriaFiscal( antes, hoje, ECFDaruma.IMPRESSAO ) ) );
		System.out.println( new String( ecf.getBytesLidos() ) );
		
		System.out.print( "leituraMemoriaFiscal data retorno > " );
		assertTrue( trataRetornoFuncao( ecf.leituraMemoriaFiscal( antes, hoje, ECFDaruma.RETORNO ) ) );
		System.out.println( new String( ecf.getBytesLidos() ) );
		
		System.out.print( "leituraMemoriaFiscal redução > " ); 
		assertTrue( trataRetornoFuncao( ecf.leituraMemoriaFiscal( 605, 610, ECFDaruma.IMPRESSAO ) ) );
		System.out.println( new String( ecf.getBytesLidos() ) );
		
		System.out.print( "leituraMemoriaFiscal redução retorno > " ); 
		assertTrue( trataRetornoFuncao( ecf.leituraMemoriaFiscal( 605, 610, ECFDaruma.RETORNO ) ) );
		System.out.println( new String( ecf.getBytesLidos() ) );
		*/
		//System.out.print( "reducaoZ > " );  
		//assertTrue( trataRetornoFuncao( ecf, ecf.reducaoZ() ) );
	}

	public void testComandosDeInformacoesDaImpressora() {

		ECFDaruma ecf = new ECFDaruma( "COM1" );
		
		System.out.print( "leitura do estado > " ); 
		System.out.println( ecf.getStatus() );	
		
		System.out.print( "retorno de aliquotas > " ); 
		System.out.println( ecf.retornoAliquotas() );	
		
		System.out.print( "retorno de totalizadores parcias > " ); 
		System.out.println( ecf.retornoTotalizadoresParciais() );	
		
		System.out.print( "retorno de número do cupom > " ); 
		System.out.println( ecf.retornoNumeroCupom() );	
		
		System.out.println( "retorno de variáveis > " ); 
		System.out.println( "\tGrande total > " + ecf.retornoVariaveis( ECFDaruma.V_GRANDE_TOTAL ) );
		System.out.println( "\tFlags Fiscais > " + ecf.retornoVariaveis( ECFDaruma.V_FLAG_FISCAL ) );
		
		System.out.print( "retorno do estado do papel > " ); 
		System.out.println( ecf.retornoEstadoPapel() );	
		
		System.out.print( "retorno da ultima redução Z > " ); 
		System.out.println( ecf.retornoUltimaReducao() );	
	}

	public void testComandosDeImpressaoDeCheques() {

		ECFDaruma ecf = new ECFDaruma( "COM1" );
		/*
		System.out.print( "programaMoedaSingular > " ); 
		assertTrue( trataRetornoFuncao( ecf.programaMoedaSingular( "Real" ) ) );
		
		System.out.print( "programaMoedaPlural > " ); 
		assertTrue( trataRetornoFuncao( ecf.programaMoedaPlural( "Reais" ) ) );
		*/
		System.out.print( "imprimeCheque > " ); 
		System.out.println( ecf.imprimeCheque( 
				1.30f, "Favorecido                                   ", 
				"Localidade                 ", 19, 11, 2007 ) );
		
		System.out.print( "retornoStatusCheque > " ); 
		System.out.println( ecf.retornoStatusCheque() );
		
	}
	
	public void testGetStatus() {
		try {
			ECFDaruma ecf = new ECFDaruma( "COM1" );
			
			System.out.print( "retorno de status > " ); 
			ecf.getStatus();		
			System.out.println( new String( ecf.getBytesLidos() ) );
		} catch ( RuntimeException e ) {
			e.printStackTrace();
		}
	}
	
	public boolean trataRetornoFuncao( final ECFDaruma ecf, final int arg ) {

		boolean returnOfAction = true;

		String str = ecf.decodeReturnECF( arg ).getMessage();

		if ( ! RETORNO_OK.getMessage().equals( str ) ) {
			returnOfAction = false;
		}
		System.out.println( str );

		return returnOfAction;
	}
}
