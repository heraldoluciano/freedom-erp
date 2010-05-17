package org.freedom.ecf.test;

import java.math.BigDecimal;
import java.util.List;

import org.freedom.ecf.app.Control;
import org.freedom.ecf.driver.AbstractECFDriver;

import junit.framework.TestCase;


public class ControlTest extends TestCase {

	public ControlTest( String name ) {

		super( name );
	}
	
	public void testInstanciarControl() {
		
		Control control = null;
		try {
			control = new Control( "org.freedom.ecf.driver.ECFBematech" );
		} catch ( IllegalArgumentException e ) {
		} catch ( NullPointerException e ) {
		}		
		assertTrue( "Control instaciado", control != null );
	}
	
	public void testNaoInstanciarControl_Ecf_Nulo() {
		
		boolean assertresult = false;		
		try {
			new Control( "nome errado" );
		} catch ( NullPointerException e ) {
			assertresult = true;
		}		
		assertTrue( "Control não instanciado por não carregar ecfdriver.", assertresult );
	}
	
	public void testNaoInstanciarControl_Null() {
		
		boolean assertresult = false;		
		try {
			new Control( null );
		} catch ( IllegalArgumentException e ) {
			assertresult = true;
		}		
		assertTrue( "Control não instanciado por driver nulo.", assertresult );
	}
	
	public void testNaoInstanciarControl_Invalido() {
		
		boolean assertresult = false;		
		try {
			new Control( "" );
		} catch ( IllegalArgumentException e ) {
			assertresult = true;
		}		
		assertTrue( "Control não instanciado por parametro invalido.", assertresult );
	}
	
	public void testProgramaHorarioVerao() {

		Control control = new Control( "org.freedom.ecf.driver.ECFBematech" );
		
		System.out.print( "programando horário de verão > " );
		assertTrue( control.setHorarioDeVerao( false ) );	
		System.out.println( control.getMessageLog() );
		
		System.out.print( "é horario de verão > " );
		boolean tmp = control.isHorarioDeVerao();
		System.out.println( tmp );
		assertTrue( tmp );	
	}
	
	public void testNomeiaDepartamento() {

		Control control = new Control( "org.freedom.ecf.driver.ECFBematech" );
		
		System.out.print( "nomeia deparatamento > " );
		assertTrue( control.nomeiaDepartamento( 2, "Vestuario" ) );	
		System.out.println( control.getMessageLog() );
	}

	public void testLeituraX() {
		
		Control control = new Control( "org.freedom.ecf.driver.ECFDaruma" );
		
		System.out.print( "leitura X > " );
		assertTrue( control.leituraX() );	
		System.out.println( control.getMessageLog() );

		System.out.print( "leitura X > " );
		assertTrue( control.leituraX( true ) );	
		System.out.println( control.getMessageLog() );
		System.out.println( control.readSerialPort() );
	}

	public void testSuprimento() {
		
		Control control = new Control( "org.freedom.ecf.driver.ECFBematech" );
		
		System.out.println( "suprimento > " );
		assertTrue( control.suprimento( new BigDecimal( "53.795" ) ) );

		System.out.println( "suprimento Cheque > " );
		assertTrue( control.suprimento( new BigDecimal( "53.793" ), "Cheque" ) );
	}
	
	public void testNumeroDocumento() {

		Control control = new Control( "org.freedom.ecf.driver.ECFBematech" );
		
		System.out.print( "número do documento > " );
		Integer tmp = null;
		System.out.println( tmp = control.getNumeroDocumento() );
		assertTrue( tmp != null );
	}
	
	public void testSubTotal() {

		Control control = new Control( "org.freedom.ecf.driver.ECFBematech" );
		
		System.out.print( "sub total > " );
		BigDecimal tmp = null;
		System.out.println( tmp = control.getSubTotal() );
		assertTrue( tmp != null );
	}
	
	public void testNumeroCaixa() {

		Control control = new Control( "org.freedom.ecf.driver.ECFBematech" );
		
		System.out.print( "número do caixa > " );
		Integer tmp = null;
		System.out.println( tmp = control.getNumeroCaixa() );
		assertTrue( tmp != null );
	}
	
	public void testVerificaEstado() {

		Control control = new Control( "org.freedom.ecf.driver.ECFBematech" );
		
		System.out.print( "verifica estado da impressora > " );
		String tmp = null;
		System.out.println( tmp = control.getEstadoImpressora() );
		assertTrue( tmp != null );
	}
	
	public void testCupomFiscal() {

		Control control = new Control( "org.freedom.ecf.driver.ECFBematech" );
		
		System.out.print( "abre cupom fiscal > " );
		assertTrue( control.abreCupom( "00.000.000/000-00" ) );
		System.out.println( control.getMessageLog() );
		/*
		System.out.print( "unidade de medida > " );
		assertTrue( control.unidadeMedida( "Kg" ) );
		System.out.println( control.getMessageLog() );
		
		System.out.print( "aumenta descrição do item > " );
		assertTrue( control.aumetaDescricaoItem( 
				"123456789à123456789â123456789ã123456789ü123456789ç123456789 123456789 123456789 123456789 123456789 " +
				"123456789à123456789â123456789ã123456789ü123456789ç123456789 123456789 123456789 123456789 123456789 " +
				"123456789à123456789â123456789ã123456789ü123456789ç123456789 123456789 123456789 123456789 123456789 " ) );
		System.out.println( control.getMessageLog() );
		*/
		System.out.print( "venda item > " );
		assertTrue( control.vendaItem( 
				"1", "PRODUTO TESTE", new BigDecimal( "18" ), AbstractECFDriver.QTD_INTEIRO,
				new BigDecimal( "1" ), AbstractECFDriver.DUAS_CASAS_DECIMAIS, new BigDecimal( "23.99" ),
				AbstractECFDriver.DESCONTO_PERC, new BigDecimal( "0" ) ) );
		System.out.println( control.getMessageLog() );
		/*
		System.out.print( "venda item > " );
		assertTrue( control.vendaItem( 
				"1", "PRODUTO TESTE áàâã éèê íì óòôõ úùûü", new BigDecimal( "18" ), new BigDecimal( "1.5" ),
				new BigDecimal( "15.334" ), new BigDecimal( "0" ) ) );
		System.out.println( control.getMessageLog() );
		
		System.out.print( "venda item > " );
		assertTrue( control.vendaItem( 
				"1", "PRODUTO TESTE", new BigDecimal( "18" ), AbstractECFDriver.QTD_INTEIRO,
				new BigDecimal( "2" ), AbstractECFDriver.TRES_CASAS_DECIMAIS, new BigDecimal( "15.334" ),
				AbstractECFDriver.DESCONTO_PERC, new BigDecimal( "0" ) ) );
		System.out.println( control.getMessageLog() );
		
		System.out.print( "venda item > " );
		assertTrue( control.vendaItem( 
				"1", "PRODUTO TESTE", new BigDecimal( "18" ), AbstractECFDriver.QTD_DECIMAL,
				new BigDecimal( "1.5" ), AbstractECFDriver.TRES_CASAS_DECIMAIS, new BigDecimal( "15.334" ),
				AbstractECFDriver.DESCONTO_PERC, new BigDecimal( "0" ) ) );
		System.out.println( control.getMessageLog() );
		
		System.out.print( "venda item departamento > " );
		assertTrue( control.vendaItemDepartamento( 
				"1", "PRODUTO TESTE", new BigDecimal( "18" ), new BigDecimal( "15.334" ), 
				 new BigDecimal( "1.5" ), new BigDecimal( "0" ), new BigDecimal( "0" ), 2, "kg" ) );
		System.out.println( control.getMessageLog() );
		
		System.out.print( "cancela item anterior > " );
		assertTrue( control.cancelaItem() );
		System.out.println( control.getMessageLog() );
		
		System.out.print( "cancela item generico (2) > " );
		assertTrue( control.cancelaItem( 2 ) );
		System.out.println( control.getMessageLog() );
		*/
		System.out.print( "inicia fechamento > " );
		assertTrue( control.iniciaFechamentoCupom( 
				AbstractECFDriver.DESCONTO_VALOR, new BigDecimal( "3.99" ) ) );
		System.out.println( control.getMessageLog() );
		/*
		System.out.print( "efetua forma de pagamento Dinheiro > " );
		assertTrue( control.efetuaFormaPagamento( new BigDecimal( "10" ) ) );
		System.out.println( control.getMessageLog() );
		
		System.out.print( "efetua forma de pagamento Cheque > " );
		assertTrue( control.efetuaFormaPagamento( "Cheque", new BigDecimal( "35" ), "Cheque de terceiro" ) );
		System.out.println( control.getMessageLog() );
		*/
		System.out.print( "efetua forma de pagamento Cartão > " );
		assertTrue( control.efetuaFormaPagamento( "Cartão", new BigDecimal( "20" ) ) );
		System.out.println( control.getMessageLog() );
		
		System.out.print( "finaliza cupom > " );
		assertTrue( control.finalizaFechamentoCupom( "Obrigado e volte sempre!" ) );
		System.out.println( control.getMessageLog() );
		/*
		System.out.print( "cancelamento de cupom> " );
		assertTrue( control.cancelaCupom() );
		System.out.println( control.getMessageLog() );
		
		System.out.print( "estorna forma de pagamento> " );
		assertTrue( control.estornoFormaPagamento( "Cheque", "Cartão", new BigDecimal( "35" ) ) );
		System.out.println( control.getMessageLog() );
		*/
	}
	
	public void testCancelaCupom() {
		
		Control control = new Control( "org.freedom.ecf.driver.ECFBematech" );

		System.out.print( "cancelamento de cupom fiscal > " );
		assertTrue( control.cancelaCupom() );
		System.out.println( control.getMessageLog() );
	}
	
	public void testComprovanteNaoFiscalVinculado() {

		Control control = new Control( "org.freedom.ecf.driver.ECFBematech" );
		
		testCupomFiscal();

		System.out.print( "abre comprovante não fiscal vinculado > " );
		assertTrue( control.abreComprovanteNaoFiscalVinculado( 
				"Cartão", new BigDecimal( "20" ), control.getNumeroDocumento() ) );
		System.out.println( control.getMessageLog() );

		System.out.print( "usando comprovante não fiscal vinculado > " );
		assertTrue( control.usaComprovanteNaoFiscalVinculado( getMensagemTef() ) );
		System.out.println( control.getMessageLog() );

		System.out.print( "fechando comprovante não fiscal vinculado > " );
		assertTrue( control.fecharRelatorioGerencial() );
		System.out.println( control.getMessageLog() );
	}
	
	public void testRelatorioGerencial() {

		Control control = new Control( "org.freedom.ecf.driver.ECFBematech" );

		System.out.print( "relatório gerencial > " );
		assertTrue( control.relatorioGerencial( getMensagemTef() ) );
		System.out.println( control.getMessageLog() );

		System.out.print( "fechamento de relatorio gerencial > " );
		assertTrue( control.fecharRelatorioGerencial() );
		System.out.println( control.getMessageLog() );
	}
	
	public void testReducaoZ() {

		Control control = new Control( "org.freedom.ecf.driver.ECFBematech" );

		System.out.print( "redução Z > " );
		assertTrue( control.reducaoZ() );
		System.out.println( control.getMessageLog() );
	}

	public void testSangria() {

		Control control = new Control( "org.freedom.ecf.driver.ECFBematech" );

		System.out.println( "sangria > " );
		assertTrue( control.sangria( new BigDecimal( "53.795" ) ) );

		System.out.println( "sangria Cheque > " );
		assertTrue( control.suprimento( new BigDecimal( "53.793" ), "Cheque" ) );
	}

	public void testGetAllAliquotas() {

		Control control = new Control( "org.freedom.ecf.driver.ECFBematech" );

		List<String> aliquotas = control.getAllAliquotas();

		for ( String s : aliquotas ) {
			System.out.println( s );
		}
	}

	public void testGetIndexAliquota() {

		Control control = new Control( "org.freedom.ecf.driver.ECFBematech" );

		assertTrue( control.getIndexAliquota( 18f ) != null );
		assertTrue( control.getIndexAliquota( 20.18f ) == null );
		assertTrue( control.getIndexAliquota( 200.185f ) == null );
		assertTrue( control.getIndexAliquota( 0.01f ) != null );
	}
	
	private String getMensagemTef() {
		 String tef = "*SIMULADO* REDECARD\n" +
			"  DEBITO A VISTA\n" +
			"\n" +
			"  ESTAB: 000000000000005\n" +
			"  N.CARTAO: 589916******7898\n" +    
			"  DATA: 14/09  HORA: 16:02:12\n" +
			"  TERMINAL: SW000002  DOC: 140012\n" +
			"  AUTORIZACAO: 140012\n" +
			"  VALOR: 2,00\n" +
			"  <ARQC:>--> 817EAAA98EDE82E5 \n" +
			"  TRANSACAO AUTORIZADA MEDIANTE\n" +
			"  USO DE SENHA \n" +
			"                                 (SiTef)\n" +
			"\n" +
			"\n" +
			"  *SIMULADO* REDECARD\n" +
			"  DEBITO A VISTA\n" +
			"\n" +
			"  ESTAB: 000000000000005\n" +
			"  N.CARTAO: 589916******7898 \n" +   
			"  DATA: 14/09  HORA: 16:02:12\n" +
			"  TERMINAL: SW000002  DOC: 140012\n" +
			"  AUTORIZACAO: 140012\n" +
			"  VALOR: 2,00\n" +
			"  <ARQC:>--> 817EAAA98EDE82E5 \n" +
			"  TRANSACAO AUTORIZADA MEDIANTE\n" +
			"  USO DE SENHA\n" +
			"                               (SiTef)";
		 return tef;
	}
}
