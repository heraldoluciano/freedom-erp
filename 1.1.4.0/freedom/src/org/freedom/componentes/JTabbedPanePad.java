/*
 * Created on 22/03/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package org.freedom.componentes;

import java.awt.Component;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JTabbedPane;

/**
 * @author robson 
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class JTabbedPanePad extends JTabbedPane implements FocusListener  {

	private static final long serialVersionUID = 1L;
	private boolean initFirstFocus = true;
	private boolean controlFocus = true;
	// Flag que indica para o component procurar o primeiro campo para foco.
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
		addFocusListener(this);
	}

	public void focusGained(FocusEvent arg0) {
		//System.out.println("Ganhou foco no Tabbed");
		firstFocus(getSelectedComponent());
		controlFocus = false;
	}
	public void focusLost(FocusEvent arg0) {
		//System.out.println("Perdeu foco no Tabbed");
		//initFirstFocus = true;

	}
	
	public void setInitFirstFocus(boolean initFirstFocus) {
		this.initFirstFocus = initFirstFocus;
	}
	
	public boolean getInitFirstFocus() {
		return this.initFirstFocus;
	}
	
	
	public void firstFocus(Component c) {
	  	if ( (initFirstFocus) && (c!=null) && (controlFocus) ) {
	  		if (c instanceof JPanelPad) {
	  		//	System.out.println("Entrou no first focus do tabbed");
	  			if (!( (JPanelPad) c).firstFocus()) 
	  				transferFocus() ;
	  		}
		}
	}

}
