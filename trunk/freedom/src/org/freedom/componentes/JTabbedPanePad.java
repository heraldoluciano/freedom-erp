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
public class JTabbedPanePad extends JTabbedPane implements ChangeListener {
	// Flag que indica para o component procurar o primeiro campo para foco.
	private boolean initFirstFocus = true;
    private Component firstFocus = null;
	
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
	
	public JTabbedPanePad(Component firstFocus) {
		this();
		setFirstFocus(firstFocus);
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
		//if (e.)
		firstFocus();
		//System.out.println("Teste do change listener do TTabbedPanePad");
	}
	
	public synchronized void firstFocus() {
	  	if ( (firstFocus!=null) && (firstFocus.hasFocus()) && (initFirstFocus) )
	  		firstFocus.requestFocus();
/*		Component c = null;
		if (initFirstFocus) {
			c = getComponentFocus(null);
			if (c!=null)
				c.requestFocus();
		} */
	}

    public synchronized void setFirstFocus(Component firstFocus) {
	  	this.firstFocus = firstFocus;
	}
	
/*	public synchronized Component getComponentFocus(Component cf) {
		Component c = null;
		//String nome = "";
		if (cf==null) {
			for (int i=0; i<getComponentCount(); i++) {
				cf = getComponent(i);
				if (cf!=null) {
					//nome = cf.getName();
					//System.out.println(nome);
					if (cf instanceof Container) {
						c = getContainerFocus( (Container) cf);
						if (c!=null) {
						//	nome = c.getName();
							//System.out.println(nome);
							break;
						}
					}
					else {
						if (cf.hasFocus()) {
							c = cf;
							break;
						}
					}
				}
				c = null;
			}
		}
		else {
			if (cf instanceof Container) {
				c = getContainerFocus((Container) cf);
			}
			else if (cf.hasFocus())
				c = cf;
		}
		return c;
	} */
/*	private synchronized Component getContainerFocus(Container cf) {
		Component c = null;
		if (cf != null) {
			for (int i=0; i<cf.getComponentCount(); i++) {
				c = cf.getComponent(i);
				if (c!=null) {
					if (c instanceof Container) {
						c = getContainerFocus((Container) c);
						break;
					}
					else if (c.hasFocus())
						break;
					else
						c = null;
				}
			}
		}
		return c;
	}*/
}
