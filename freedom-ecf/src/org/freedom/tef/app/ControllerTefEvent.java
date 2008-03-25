package org.freedom.tef.app;


public class ControllerTefEvent {
	
	public static final int ERROR = 100;
	
	public static final int WARNING = 101;
	
	public static final int CONFIRM = 102;
	
	public static final int BEGIN_PRINT = 200;
	
	public static final int PRINT = 201;
	
	public static final int END_PRINT = 202;
	
	public static final int RE_PRINT = 203;
	
	private ControllerTef controllerTef;

	private int action;
	
	private String message;
	
	public ControllerTefEvent() {
		super();
	}
	
	public ControllerTefEvent( final ControllerTef controllerTef, 
			                   final int action ) {
		this();
		this.controllerTef = controllerTef;
		this.action = action;
	}
	
	public ControllerTefEvent( final ControllerTef controllerTef, 
			                   final int action,
			                   final String message ) {
		this();
		this.controllerTef = controllerTef;
		this.action = action;
		this.message = message;
	}
	
	public ControllerTef getControllerTef() {	
		return controllerTef;
	}
	
	public void setControllerTef( final ControllerTef controllerTef ) {	
		this.controllerTef = controllerTef;
	}
	
	public int getAction() {	
		return action;
	}
	
	public void setAction( final int action ) {	
		this.action = action;
	}
	
	public String getMessage() {	
		return message;
	}
	
	public void setMessage( final String message ) {	
		this.message = message;
	}
}
