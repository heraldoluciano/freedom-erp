package org.freedom.objetos;

import java.util.Vector;

import org.freedom.infra.pojos.Constant;

public class TipoMov implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;
	
	//Tipo de movimento de entrada
	
	public static final Constant TM_ORCAMENTO_COMPRA = new Constant("Orçamento", "OC"); 
	public static final Constant TM_PEDIDO_COMPRA = new Constant("Pedido", "PC");
	public static final Constant TM_COMPRA = new Constant("Compra", "CP");		
	public static final Constant TM_ORDEM_DE_PRODUCAO = new Constant("Ordem de produção", "OP");
	public static final Constant TM_DEVOLUCAO_VENDA = new Constant("Devolução", "DV");
	public static final Constant TM_DEVOLUCAO_REMESSA = new Constant("Devolução de remessa", "DR");
	public static final Constant TM_CONHECIMENTO_FRETE_COMPRA = new Constant("Conhecimento de frete", "CF");
	public static final Constant TM_NOTA_FISCAL_COMPLEMENTAR_COMPRA = new Constant("Nota fiscal complementar", "CO");
	
	//Tipo de movimento de saída
	
	public static final Constant TM_ORCAMENTO_VENDA = new Constant("Orçamento", "OV");
	public static final Constant TM_PEDIDO_VENDA = new Constant("Pedido", "PV");
	public static final Constant TM_VENDA = new Constant("Venda comum", "VD");
	public static final Constant TM_VENDA_ECF = new Constant("Venda ECF", "VE");
	public static final Constant TM_VENDA_TELEVENDAS = new Constant("Venda Telemarketing", "VT");
	public static final Constant TM_VENDA_SERVICO = new Constant("Venda Serviço", "SE");
	public static final Constant TM_BONIFICACAO_SAIDA = new Constant("Bonificação", "BN");
	public static final Constant TM_DEVOLUCAO_COMPRA = new Constant("Devolução", "DV");
	public static final Constant TM_TRANSFERENCIA_SAIDA = new Constant("Transferência", "TR");
	public static final Constant TM_PERDA_SAIDA = new Constant("Perda", "PE");
	public static final Constant TM_CONSIGNACAO_SAIDA = new Constant("Consignação", "CS");
	public static final Constant TM_DEVOLUCAO_CONSIGNACAO = new Constant("Devolução de consignação", "CE");
	public static final Constant TM_REQUISICAO_DE_MATERIAL = new Constant("Requisição de material", "RM");
	public static final Constant TM_NOTA_FISCAL_COMPLEMENTAR_SAIDA = new Constant("Nota fiscal complementar", "CO");
	public static final Constant TM_REMESSA_SAIDA = new Constant("Remessa", "VR");	
	
	//Tipo de movimento de inventário
	
	public static final Constant TM_INVENTARIO = new Constant("Inventário de estoque", "IV");
	
	//Tipos de fluxo
	
	public static final Constant ENTRADA = new Constant("Entrada", "E");
	public static final Constant SAIDA = new Constant("Saída", "S");
	public static final Constant INVENTARIO = new Constant("Inventário", "I");	
		
	public static Vector<String> getLabels( String tipo ) {

		Vector<String> ret = new Vector<String>();
		
		ret.addElement( "<--Selecione-->" );
		
		if ( ENTRADA.getValue().equals( tipo ) ) {
			
			ret.add( TM_ORCAMENTO_COMPRA.getName() );
			ret.add( TM_PEDIDO_COMPRA.getName() );
			ret.add( TM_COMPRA.getName() );
			ret.add( TM_ORDEM_DE_PRODUCAO.getName() );
			ret.add( TM_DEVOLUCAO_VENDA.getName() );
			ret.add( TM_DEVOLUCAO_REMESSA.getName() );
			ret.add( TM_CONHECIMENTO_FRETE_COMPRA.getName() );
			ret.add( TM_NOTA_FISCAL_COMPLEMENTAR_COMPRA.getName() );			
		
		}
		else if ( SAIDA.getValue().equals( tipo ) ) {
			
			ret.add( TM_ORCAMENTO_VENDA.getName() );
			ret.add( TM_PEDIDO_VENDA.getName() );
			ret.add( TM_VENDA.getName() );
			ret.add( TM_VENDA_ECF.getName() );
			ret.add( TM_VENDA_TELEVENDAS.getName() );
			ret.add( TM_VENDA_SERVICO.getName() );
			ret.add( TM_BONIFICACAO_SAIDA.getName() );
			ret.add( TM_DEVOLUCAO_COMPRA.getName() );
			ret.add( TM_TRANSFERENCIA_SAIDA.getName() );
			ret.add( TM_PERDA_SAIDA.getName() );
			ret.add( TM_CONSIGNACAO_SAIDA.getName() );
			ret.add( TM_DEVOLUCAO_CONSIGNACAO.getName() );
			ret.add( TM_REQUISICAO_DE_MATERIAL.getName() );
			ret.add( TM_NOTA_FISCAL_COMPLEMENTAR_SAIDA.getName() );
			ret.add( TM_REMESSA_SAIDA.getName() );	

		}
		else if ( INVENTARIO.equals( tipo ) ) {
			ret.add( TM_INVENTARIO.getName() );
		}
		
		return ret;
		
	}
	
	public static Vector<String> getValores( String tipo ) {
		
		Vector<String> ret = new Vector<String>();
		
		ret.addElement( "" );
		
		if ( ENTRADA.getValue().equals( tipo ) ) {
			
			ret.add( TM_ORCAMENTO_COMPRA.getValue() );
			ret.add( TM_PEDIDO_COMPRA.getValue() );
			ret.add( TM_COMPRA.getValue() );
			ret.add( TM_ORDEM_DE_PRODUCAO.getValue() );
			ret.add( TM_DEVOLUCAO_VENDA.getValue() );
			ret.add( TM_DEVOLUCAO_REMESSA.getValue() );
			ret.add( TM_CONHECIMENTO_FRETE_COMPRA.getValue() );
			ret.add( TM_NOTA_FISCAL_COMPLEMENTAR_COMPRA.getValue() );

		}
		else if ( SAIDA.getValue().equals( tipo ) ) {
			
			ret.add( TM_ORCAMENTO_VENDA.getValue() );
			ret.add( TM_PEDIDO_VENDA.getValue() );
			ret.add( TM_VENDA.getValue() );
			ret.add( TM_VENDA_ECF.getValue() );
			ret.add( TM_VENDA_TELEVENDAS.getValue() );
			ret.add( TM_VENDA_SERVICO.getValue() );
			ret.add( TM_BONIFICACAO_SAIDA.getValue() );
			ret.add( TM_DEVOLUCAO_COMPRA.getValue() );
			ret.add( TM_TRANSFERENCIA_SAIDA.getValue() );
			ret.add( TM_PERDA_SAIDA.getValue() );
			ret.add( TM_CONSIGNACAO_SAIDA.getValue() );
			ret.add( TM_DEVOLUCAO_CONSIGNACAO.getValue() );
			ret.add( TM_REQUISICAO_DE_MATERIAL.getValue() );
			ret.add( TM_NOTA_FISCAL_COMPLEMENTAR_SAIDA.getValue() );
			ret.add( TM_REMESSA_SAIDA.getValue() );	

		}
		else if ( INVENTARIO.getValue().equals( tipo ) ) {
			ret.add( TM_INVENTARIO.getValue() );
		}
		
		return ret;

	}
	
	
	
	

}



