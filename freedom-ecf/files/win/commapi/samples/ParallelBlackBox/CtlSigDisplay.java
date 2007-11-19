/*
 * @(#)CtlSigDisplay.java	1.1 98/06/02 SMI
 *
 * Author: Tom Corson
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

import java.awt.Panel;
import java.awt.Label;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.comm.ParallelPort;

public class CtlSigDisplay extends Panel
{
	ParallelPort	port = null;
	Label		SELLabel,
			BSYLabel,
			TOLabel,
			PELabel,
			POLabel,
			BELabel;
	boolean		DA,
			BE;
	private Color	onColor,
			offColor;

	public CtlSigDisplay(ParallelPort	port)
	{
		super();

		this.setPort(port);

		this.setLayout(new FlowLayout());

		SELLabel = new Label("SEL");
		this.add(SELLabel);

		BSYLabel = new Label("BSY");
		this.add(BSYLabel);

		TOLabel = new Label("TO");
		this.add(TOLabel);

		PELabel = new Label("PE");
		this.add(PELabel);

		POLabel = new Label("PO");
		this.add(POLabel);

		DA = false;

		BELabel = new Label("BE");
		this.add(BELabel);
		BE = true;

		onColor = Color.green;
		offColor = Color.black;
	}

	public void setPort(ParallelPort	port)
	{
		this.port = port;
	}

	public void showValues()
	{
		if (this.port != null)
		{
			/*
			 *  Get the state of the control signals for this port
			 */
	
			SELLabel.setForeground(port.isPrinterSelected()
						 ? onColor : offColor);
	
			BSYLabel.setForeground(port.isPrinterBusy()
						 ? onColor : offColor);
		}
	}

	public void showErrorValues()
	{
		if (this.port != null)
		{
			/*
			 *  Get the state of the error conditions for this port
			 */
	
	
			TOLabel.setForeground(port.isPrinterTimedOut()
						 ? onColor : offColor);
	
			PELabel.setForeground(port.isPrinterError()
						 ? onColor : offColor);
	
			POLabel.setForeground(port.isPaperOut() ? onColor : offColor);

			BELabel.setForeground(BE ? onColor : offColor);
		}
	}

	public void clearValues()
	{
		SELLabel.setForeground(offColor);
		BSYLabel.setForeground(offColor);
	}

	public void clearErrorValues()
	{
		TOLabel.setForeground(offColor);
		PELabel.setForeground(offColor);
		POLabel.setForeground(offColor);
		BELabel.setForeground(offColor);
	}
}
