/*
 * @(#)AlertDialog.java	1.3 98/06/04 SMI
 *
 * Copyright (c) 1998 Sun Microsystems, Inc. All Rights Reserved.
 *
 * Sun grants you ("Licensee") a non-exclusive, royalty free, license
 * to use, modify and redistribute this software in source and binary
 * code form, provided that i) this copyright notice and license appear
 * on all copies of the software; and ii) Licensee does not utilize the
 * software in a manner which is disparaging to Sun.
 *
 * This software is provided "AS IS," without a warranty of any kind.
 * ALL EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND WARRANTIES,
 * INCLUDING ANY IMPLIED WARRANTY OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE OR NON-INFRINGEMENT, ARE HEREBY EXCLUDED. SUN AND
 * ITS LICENSORS SHALL NOT BE LIABLE FOR ANY DAMAGES SUFFERED BY
 * LICENSEE AS A RESULT OF USING, MODIFYING OR DISTRIBUTING THE
 * SOFTWARE OR ITS DERIVATIVES. IN NO EVENT WILL SUN OR ITS LICENSORS
 * BE LIABLE FOR ANY LOST REVENUE, PROFIT OR DATA, OR FOR DIRECT,
 * INDIRECT, SPECIAL, CONSEQUENTIAL, INCIDENTAL OR PUNITIVE DAMAGES,
 * HOWEVER CAUSED AND REGARDLESS OF THE THEORY OF LIABILITY, ARISING
 * OUT OF THE USE OF OR INABILITY TO USE SOFTWARE, EVEN IF SUN HAS BEEN
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGES.
 *
 * This software is not designed or intended for use in on-line control
 * of aircraft, air traffic, aircraft navigation or aircraft
 * communications; or in the design, construction, operation or
 * maintenance of any nuclear facility. Licensee represents and
 * warrants that it will not use or redistribute the Software for such
 * purposes.
 */

import java.awt.*;
import java.awt.event.*;

/**
A single response modal alert dialog. This class is configurable for message 
and title. The width of the dialog will be longer than the longest message 
line.  When the OK button is pressed the dialog returns.
*/
public class AlertDialog extends Dialog implements ActionListener {

    /**
    Creates a new <code>AlertDialog</code> with three lines of message and
    a title.

    @param parent Any Frame.
    @param title The title to appear in the border of the dialog.
    @param lineOne The first line of the message in the dialog.
    @param lineTwo The second line of the message in the dialog.
    @param lineThree The third line of the message in the dialog.
    */
    public AlertDialog(Frame parent, 
		       String title, 
		       String lineOne, 
		       String lineTwo,
		       String lineThree) {
	super(parent, title, true);

	Panel labelPanel = new Panel();
	labelPanel.setLayout(new GridLayout(3, 1));
	labelPanel.add(new Label(lineOne, Label.CENTER));
	labelPanel.add(new Label(lineTwo, Label.CENTER));
	labelPanel.add(new Label(lineThree, Label.CENTER));
	add(labelPanel, "Center");

	Panel buttonPanel = new Panel();
	Button okButton = new Button("OK");
	okButton.addActionListener(this);
	buttonPanel.add(okButton);
	add(buttonPanel, "South");

	FontMetrics fm = getFontMetrics(getFont());
	int width = Math.max(fm.stringWidth(lineOne), 
		 Math.max(fm.stringWidth(lineTwo), fm.stringWidth(lineThree)));

	setSize(width + 40, 150);
	setLocation(parent.getLocationOnScreen().x + 30, 
		    parent.getLocationOnScreen().y + 30);
	setVisible(true);
    }

    /**
    Handles events from the OK button. When OK is pressed the dialog becomes
    invisible, disposes of its self, and retruns.
    */
    public void actionPerformed(ActionEvent e) {
	setVisible(false);
	dispose();
    }
}
