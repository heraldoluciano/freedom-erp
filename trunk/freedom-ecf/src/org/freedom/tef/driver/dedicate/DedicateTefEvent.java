package org.freedom.tef.driver.dedicate;

import org.freedom.tef.app.TefAction;



public class DedicateTefEvent {
	
	private DedicateTefListener dedicateTefListener;

	private TefAction action;
	
	private String message;
	
	public DedicateTefEvent( final DedicateTefListener dedicateTefListener, 
			                 final TefAction action ) { 
		this( dedicateTefListener, action, null );
	}	
	
	public DedicateTefEvent( final DedicateTefListener dedicateTefListener, 
			                 final TefAction action,
			                 final String message ) { 
		super();
		this.dedicateTefListener = dedicateTefListener;
		this.action = action;
		this.message = message;
	}	
	
	public DedicateTefListener getSource() {	
		return dedicateTefListener;
	}

	public TefAction getAction() {	
		return action;
	}
	
	public String getMessage() {
		return this.message;
	}
}
