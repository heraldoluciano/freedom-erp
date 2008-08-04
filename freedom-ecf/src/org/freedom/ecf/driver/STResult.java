
package org.freedom.ecf.driver;

import java.util.ArrayList;
import java.util.List;

public class STResult {

	private boolean inError = false;

	private List<Status> listResult = new ArrayList<Status>();

	public STResult() {

		super();
	}

	public static STResult getInstanceNotImplemented() {

		STResult result = new STResult();
		result.add( StatusStandart.FUNCAO_NAO_IMPLEMENTADA );
		return result;
	}

	public static STResult getInstanceNotComunication() {

		STResult result = new STResult();
		result.add( StatusStandart.IMPRESSORA_SEM_COMUNICACAO );
		return result;
	}

	public static STResult getInstanceOk() {

		STResult result = new STResult();
		result.add( StatusStandart.RETORNO_OK );
		return result;
	}

	public boolean isInError() {
		return inError;
	}

	public void add( Status status ) {

		if ( status != null ) {
			if ( status.getRelevanc() == Status.RELEVANC_ERRO ) {
				this.inError = true;
			}
			listResult.add( status );
		}
	}

	public void addAll( STResult stresult ) {

		if ( stresult != null ) {
			for ( Status status : stresult.listResult ) {
				add( status );
			}
		}
	}
	
	public boolean isCode(final int code) {
		boolean result = false;
		if (listResult!=null) {
			for (Status itemresult: listResult) {
				if ( (itemresult!=null) && (itemresult.getCode()==code) ) {
					result = true;
					break;
				}
			}
		}
		return result;
	}
	
	public int getFirstCode() {
		int result = 0;
		if ((listResult!=null) && (!listResult.isEmpty())) {
			if (listResult.get( 0 )!=null) {
				result = listResult.get( 0 ).getCode();
			}
		}
		return result;
	}
	
	public String getMessages() {
		
		StringBuilder messages = new StringBuilder();
		
		for ( Status status : listResult ) {
			messages.append( status.getMessage() );
			messages.append( "\n" );
		}
		
		return messages.toString();
	}
}