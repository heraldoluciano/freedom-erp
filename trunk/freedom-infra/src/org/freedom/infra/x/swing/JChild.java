package org.freedom.infra.x.swing;

import javax.swing.JInternalFrame;
import java.awt.Dimension;
import javax.swing.JPanel;
import java.awt.GridBagLayout;
import java.awt.BorderLayout;
import javax.swing.JScrollPane;
import java.awt.GridBagConstraints;

public class JChild extends JInternalFrame {

	private JPanel jpnContainer = null;
	private JPanel jpnTop = null;
	private JPanel jpnClient = null;
	private JPanel jpnSouth = null;
	private JScrollPane jspClient = null;

	/**
	 * This method initializes 
	 * 
	 */
	public JChild() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
        this.setSize(new Dimension(470, 280));
        this.setContentPane(getJpnContainer());
			
	}

	/**
	 * This method initializes jpnContainer	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJpnContainer() {
		if (jpnContainer == null) {
			jpnContainer = new JPanel();
			jpnContainer.setLayout(new BorderLayout());
			jpnContainer.add(getJpnTop(), BorderLayout.NORTH);
			jpnContainer.add(getJpnClient(), BorderLayout.CENTER);
			jpnContainer.add(getJpnSouth(), BorderLayout.SOUTH);
		}
		return jpnContainer;
	}

	/**
	 * This method initializes jpnTop	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJpnTop() {
		if (jpnTop == null) {
			jpnTop = new JPanel();
			jpnTop.setLayout(new GridBagLayout());
		}
		return jpnTop;
	}

	/**
	 * This method initializes jpnClient	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJpnClient() {
		if (jpnClient == null) {
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.fill = GridBagConstraints.BOTH;
			gridBagConstraints.weighty = 1.0;
			gridBagConstraints.weightx = 1.0;
			jpnClient = new JPanel();
			jpnClient.setLayout(new GridBagLayout());
			jpnClient.add(getJspClient(), gridBagConstraints);
		}
		return jpnClient;
	}

	/**
	 * This method initializes jpnSouth	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJpnSouth() {
		if (jpnSouth == null) {
			jpnSouth = new JPanel();
			jpnSouth.setLayout(new GridBagLayout());
		}
		return jpnSouth;
	}

	/**
	 * This method initializes jspClient	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJspClient() {
		if (jspClient == null) {
			jspClient = new JScrollPane();
			jspClient.setBorder(null);
		}
		return jspClient;
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
