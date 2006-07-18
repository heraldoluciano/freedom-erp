package org.freedom.ecf.driver;

import java.util.Date;

public class Teste {
	
	public Teste(){
		System.out.println("teste");
	}
	
	/**
	 * @param args
	 */
	public static void main(final String[] args) {

		final ECFBematech ecf = new ECFBematech(ECFBematech.COM1);
		
		Date data = new Date();
		System.out.println("Inicio --> " + data.getHours() + ":" + data.getMinutes() + ":" + data.getSeconds() );
		
		
		//ecf.programaCaracterParaAutenticacao(new char[]{1,2,4,8,16,32,64,128,64,32,16,8,4,2,1,129,129,129});

		ecf.autenticacaoDeDocumento();
		ecf.comprovanteNFiscalNVinculado( "01", 10f,"Cartao Credito  ");
		
		
		
		//ecf.abreComprovanteNFiscalVinculado("Cheque Predatado", 1, 726 );
		// não vinga...
		//ecf.usaComprovanteNFiscalVinculado("Testando comprovante nao fiscal vinculado...");
		
		
		
		//ecf.aberturaDeCupom();
		
		//ecf.programaUnidadeMedida("KG");
		//ecf.aumentaDescItem("Caneta                       0123456789" );
		
		//ecf.vendaItem("0000000000001", "Caneta                       ", "FF", 1f, 0.12f, 0f);
		
		//ecf.vendaItemTresCasas("0000000000001", "Caneta                       ", "FF", 1f, 0.125f, 0f);
		
		//ecf.vendaItemDepartamento("FF", 15.001f, 1f, 0.001f, 0.001f, 3, "UN", "0000000000001", "Caneta");
		
		//ecf.iniciaFechamentoCupom(ECFBematech.DESCONTO_PERCENTUAL,0);
		//ecf.efetuaFormaPagamento(1,1f,"ta pago");
		//ecf.terminaFechamentoCupom("Obrigado pela Preferencia!!!");
		
		
		//ecf.programaFormaPagamento("Cartao Credito  ");// 2
		//ecf.programaFormaPagamento("Cartao Visa     ");// 3
		
		data = new Date();
		System.out.println("Fim -----> " + data.getHours() + ":" + data.getMinutes() + ":" + data.getSeconds() );
				
		System.exit(0);
	}

}
