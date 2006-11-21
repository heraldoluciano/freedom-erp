package org.freedom.infra.x.swing;

import javax.swing.JPanel;
import java.awt.Dimension;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.ImageIcon;

public class DbNavigator extends JPanel {

	private JPanel jpnButton = null;
	private JButton jbtFirst = null;
	private JButton jbtPrior = null;
	private JButton jbtNext = null;
	private JButton jbtLast = null;
	private JButton jbtNew = null;
	private JButton jbtEdit = null;
	private JButton jbtSave = null;
	private JButton jbtCancel = null;

	/**
	 * This method initializes 
	 * 
	 */
	public DbNavigator() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
        this.setLayout(new BorderLayout());
        this.setSize(new Dimension(256, 45));
        this.add(getJpnButton(), BorderLayout.CENTER);
			
	}

	/**
	 * This method initializes jpnButton	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJpnButton() {
		if (jpnButton == null) {
			GridLayout gridLayout = new GridLayout();
			gridLayout.setRows(1);
			gridLayout.setColumns(7);
			jpnButton = new JPanel();
			jpnButton.setPreferredSize(new Dimension(300, 5));
			jpnButton.setLayout(gridLayout);
			jpnButton.add(getJbtFirst(), null);
			jpnButton.add(getJbtPrior(), null);
			jpnButton.add(getJbtNext(), null);
			jpnButton.add(getJbtLast(), null);
			jpnButton.add(getJbtNew(), null);
			jpnButton.add(getJbtEdit(), null);
			jpnButton.add(getJbtSave(), null);
			jpnButton.add(getJbtCancel(), null);
		}
		return jpnButton;
	}

	/**
	 * This method initializes jbtFirst	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJbtFirst() {
		if (jbtFirst == null) {
			jbtFirst = new JButton();
		}
		return jbtFirst;
	}

	/**
	 * This method initializes jbtPrior	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJbtPrior() {
		if (jbtPrior == null) {
			jbtPrior = new JButton();
		}
		return jbtPrior;
	}

	/**
	 * This method initializes jbtNext	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJbtNext() {
		if (jbtNext == null) {
			jbtNext = new JButton();
		}
		return jbtNext;
	}

	/**
	 * This method initializes jbtLast	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJbtLast() {
		if (jbtLast == null) {
			jbtLast = new JButton();
		}
		return jbtLast;
	}

	/**
	 * This method initializes jbtNew	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJbtNew() {
		if (jbtNew == null) {
			jbtNew = new JButton();
			jbtNew.setIcon(new ImageIcon(getClass().getResource("/org/freedom/infra/images/btNew.gif")));
		}
		return jbtNew;
	}

	/**
	 * This method initializes jbtEdit	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJbtEdit() {
		if (jbtEdit == null) {
			jbtEdit = new JButton();
		}
		return jbtEdit;
	}

	/**
	 * This method initializes jbtSave	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJbtSave() {
		if (jbtSave == null) {
			jbtSave = new JButton();
			jbtSave.setIcon(new ImageIcon(getClass().getResource("/org/freedom/infra/images/btSave.gif")));
		}
		return jbtSave;
	}

	/**
	 * This method initializes jbtCancel	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJbtCancel() {
		if (jbtCancel == null) {
			jbtCancel = new JButton();
		}
		return jbtCancel;
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
