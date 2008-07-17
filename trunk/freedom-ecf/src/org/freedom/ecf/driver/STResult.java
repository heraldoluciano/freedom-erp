package org.freedom.ecf.driver;

import java.util.ArrayList;
import java.util.List;

public class STResult {
	private boolean metError = false;
	private List<ItemResult> listResult = new ArrayList<ItemResult>();

	public STResult() {
		super();
	}
	
	public static STResult getInstanceNotImplemented() {
	   STResult result = new STResult();
	   result.add(result.new ItemResult(true, -1,  "Funçao não implementada."));
	   return result;
	}

	public static STResult getInstanceOk() {
		   STResult result = new STResult();
		   result.add(result.new ItemResult(false, 1,  "Situação OK."));
		   return result;
	}
	
	public boolean isCode(final int code) {
		boolean result = false;
		if (listResult!=null) {
			for (ItemResult itemresult: listResult) {
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
	public boolean isMetError() {
		return metError;
	}
	
	public void setMetError( boolean metError ) {
		this.metError = metError;
	}
	
	public void add(ItemResult itemresult) {
		if (itemresult!=null) {
			if (itemresult.isError()) {
				this.metError = true;
			}
			listResult.add( itemresult );
		}
	}
	
	public class ItemResult {
		private boolean error = false;
		private String message = null;
		int code = 0;
		public ItemResult(boolean error, int code, String message) {
			this.error = error;
			this.code = code;
			this.message = message;
		}
		
		public boolean isError() {
			return error;
		}
		
		public void setError( boolean error ) {
			this.error = error;
		}
		
		public int getCode() {
			return code;
		}
		
		public void setCode( int code ) {
			this.code = code;
		}

		
		public String getMessage() {
		
			return message;
		}

		
		public void setMessage( String message ) {
		
			this.message = message;
		}
	}
}