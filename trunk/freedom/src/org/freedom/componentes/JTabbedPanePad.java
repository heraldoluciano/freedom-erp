/*
 * Created on 22/03/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package org.freedom.componentes;

import java.awt.Component;

import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * @author robson 
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class JTabbedPanePad extends JTabbedPane implements ChangeListener  {
	// Flag que indica para o component procurar o primeiro campo para foco.
	private boolean initFirstFocus = true;
	/**
	 * 
	 */
	public JTabbedPanePad() {
		super();
		setChangedListener();
	}

	/**
	 * @param arg0
	 */
	public JTabbedPanePad(int arg0) {
		super(arg0);
		setChangedListener();
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public JTabbedPanePad(int arg0, int arg1) {
		super(arg0, arg1);
		setChangedListener();
	}
	
	private void setChangedListener() {
		addChangeListener(this);
	}
	
	public void setInitFirstFocus(boolean initFirstFocus) {
		this.initFirstFocus = initFirstFocus;
	}
	
	public boolean getInitFirstFocus() {
		return this.initFirstFocus;
	}
	
	public void stateChanged(ChangeEvent e) {
		firstFocus(getSelectedComponent());
	}
	
	public void firstFocus(Component c) {
	  	if ( (initFirstFocus) && (c!=null) ) {
	  		if (c instanceof JPanelPad) {
	  		//	System.out.println("Entrou no first focus do tabbed");
	  			( (JPanelPad) c).firstFocus();
	  		}
		}
	}

}
