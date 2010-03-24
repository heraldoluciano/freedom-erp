package org.freedom.objetos;

import java.util.Vector;

import org.freedom.infra.pojos.Constant;

public class TipoRecMerc implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;
	
	//Tipo de recebimento de mercadoria
	
	public static final Constant TIPO_RECEBIMENTO_PESAGEM = new Constant("Recebimento com pesagem", "RP"); 
	
	public static final Constant TIPO_COLETA_DE_MATERIAIS = new Constant("Coleta de materiais", "CM");
	
	//Processos
	
	public static final Constant PROCESSO_PESAGEM_INICIAL = new Constant( "Pesagem inicial", "PI" );
	
	public static final Constant PROCESSO_DESCARREGAMENTO = new Constant( "Descarregamento", "TR");
	
	public static final Constant PROCESSO_PESAGEM_FINAL = new Constant( "Pesagem final", "PF");
	
	public static final Constant PROCESSO_COLETA = new Constant( "Coleta", "CM");
		
	public static Vector<String> getLabelsTipoRecMerc( ) {

		Vector<String> ret = new Vector<String>();
		
		ret.addElement( "<--Selecione-->" );
		
		ret.add( TIPO_RECEBIMENTO_PESAGEM.getName() );
		ret.add( TIPO_COLETA_DE_MATERIAIS.getName() );

		return ret;
		
	}

	public static Vector<String> getValoresTipoRecMerc( ) {

		Vector<String> ret = new Vector<String>();
		
		ret.addElement( "" );
			
		ret.add( TIPO_RECEBIMENTO_PESAGEM.getValue() );
		ret.add( TIPO_COLETA_DE_MATERIAIS.getValue() );

		return ret;
		
	}
	
	public static Vector<String> getLabelsProcesso( String tipo ) {

		Vector<String> ret = new Vector<String>();
		
		ret.addElement( "<--Selecione-->" );
		
		if( TIPO_RECEBIMENTO_PESAGEM.getValue().equals( tipo )) {
			ret.add( PROCESSO_PESAGEM_INICIAL.getName() );
			ret.add( PROCESSO_DESCARREGAMENTO.getName() );
			ret.add( PROCESSO_PESAGEM_FINAL.getName() );
		}
		else if( TIPO_COLETA_DE_MATERIAIS.getValue().equals( tipo )) {
			ret.add( PROCESSO_COLETA.getName() );
		}
				
		return ret;
		
	}
	
	public static Vector<Object> getValoresProcesso( String tipo ) {
		
		Vector<Object> ret = new Vector<Object>();
		
		ret.addElement( "" );
		
		if( TIPO_RECEBIMENTO_PESAGEM.getValue().equals( tipo )) {	
			ret.add( PROCESSO_PESAGEM_INICIAL.getValue() );
			ret.add( PROCESSO_DESCARREGAMENTO.getValue() );
			ret.add( PROCESSO_PESAGEM_FINAL.getValue() );
		}
		else if( TIPO_COLETA_DE_MATERIAIS.getValue().equals( tipo )) {
			ret.add( PROCESSO_COLETA.getValue() );
		}
		
		return ret;

	}
	
	
	
	

}



