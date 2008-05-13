
package org.freedom.infra.x.swing;

import java.awt.Dimension;
import java.awt.Point;

import javax.swing.JComponent;
import javax.swing.JLayeredPane;

public class FDPanel extends JLayeredPane {

	private static final long serialVersionUID = 1l;
	

	public FDPanel() {
	}

	public void add( final JComponent component,
					 final int x,
					 final int y,
					 final int width,
					 final int height ) {

		component.setBounds( x, y, width, height );
		add( component, JLayeredPane.DEFAULT_LAYER );
	}

	public void add( final JComponent component,
					 final Point point,
					 final Dimension dimension ) {

		add( component, point.x, point.y, dimension.width, dimension.height );
	}

}
