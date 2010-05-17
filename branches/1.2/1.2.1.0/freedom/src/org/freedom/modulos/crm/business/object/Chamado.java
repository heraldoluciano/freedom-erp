package org.freedom.modulos.crm.business.object;

import java.sql.Types;
import java.util.Vector;

import org.freedom.infra.beans.Field;
import org.freedom.infra.pojos.Constant;



public class Chamado {

	private static final long serialVersionUID = 1L;
	
	public enum Campos {
		CODEMP, CODFILIAL, CODCHAMADO, DESCCHAMADO, DETCHAMADO, OBSCHAMADO, CODEMPCL, CODFILIALCL,
		CODCLI, SOLICITANTE, PRIORIDADE, CODEMPTC, CODFILIALTC, CODTPCHAMADO, STATUS, DTCHAMADO,
		DTPREVISAO, QTDHORASPREVISAO, DTCONCLUSAO, DTINS, HINS, IDUSUINS, DTALT, HALT, IDUSUALT
	}
	
	public static final Constant STATUS_PENDENTE = new Constant("Pendente", "PE" );
	public static final Constant STATUS_ANALISE = new Constant("Em analise", "AN" );	
	public static final Constant STATUS_EM_ANDAMENTO = new Constant("Em andamento", "EA" );
	public static final Constant STATUS_CANCELADO = new Constant("Cancelado", "CA" );
	public static final Constant STATUS_CONCLUIDO = new Constant("Concluído", "CO" );
	
	private Field codchamado = new Field( "codchamado", null, Types.INTEGER );
	private Field deschamado = new Field( "descchamado", null, Types.CHAR );
	private Field detchamado = new Field( "detchamado", null, Types.CHAR );
	private Field obschamado = new Field( "obschamado", null, Types.CHAR );
	private Field solicitante = new Field( "solicitante", null, Types.CHAR );
	private Field prioridade = new Field( "prioridade", null, Types.INTEGER );
	private Field codtpchamado = new Field( "codtpchamado", null, Types.INTEGER );
	private Field status = new Field( "status", null, Types.CHAR );
	private Field dtchamado = new Field( "dtchamado", null, Types.DATE );
	private Field dtprevisao = new Field( "dtprevisao", null, Types.DATE );
	private Field qtdhorasprevisao = new Field( "qtdhorasprevisao", null, Types.DECIMAL );
	private Field dtconclusao = new Field( "dtconclusao", null, Types.DATE );

	public static Vector<String> getLabels( ) {

		Vector<String> ret = new Vector<String>();
		
		ret.addElement( "<--Selecione-->" );
	
		ret.add( STATUS_PENDENTE.getName() );
		ret.add( STATUS_ANALISE.getName() );
		ret.add( STATUS_EM_ANDAMENTO.getName() );
		ret.add( STATUS_CANCELADO.getName() );
		ret.add( STATUS_CONCLUIDO.getName() );
		
		return ret;
		
	}
	
	public static Vector<Object> getValores( ) {
		
		Vector<Object> ret = new Vector<Object>();
		
		ret.addElement( "" );
		
		ret.add( (String) STATUS_PENDENTE.getValue() );
		ret.add( (String) STATUS_ANALISE.getValue() );
		ret.add( (String) STATUS_EM_ANDAMENTO.getValue() );
		ret.add( (String) STATUS_CANCELADO.getValue() );
		ret.add( (String) STATUS_CONCLUIDO.getValue() );
		
		return ret;

	}
	
	
}
