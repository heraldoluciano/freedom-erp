/*
 * @(#)CtlSigDisplay.java	1.5 98/05/29 SMI
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

import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

import javax.comm.SerialPort;

public class CtlSigDisplay extends Panel
{
	SerialPort	port = null;
	ToggleLabel	RTSLabel,
			DTRLabel,
			OELabel,
			FELabel,
			PELabel,
			BILabel;
	Label		CTSLabel,
			DSRLabel,
			RILabel,
			CDLabel,
			DALabel,
			BELabel;
	boolean		DA,
			BE;
	private Color	onColor,
			offColor;

	public CtlSigDisplay(SerialPort	port)
	{
		super();

		this.setPort(port);

		this.setLayout(new FlowLayout());

		RTSLabel = new ToggleLabel("RTS");
		RTSLabel.addMouseListener(new ToggleMouseListener(this));
		this.add(RTSLabel);

		CTSLabel = new Label("CTS");
		this.add(CTSLabel);

		DTRLabel = new ToggleLabel("DTR");
		DTRLabel.addMouseListener(new ToggleMouseListener(this));
		this.add(DTRLabel);

		DSRLabel = new Label("DSR");
		this.add(DSRLabel);

		RILabel = new Label("RI");
		this.add(RILabel);

		CDLabel = new Label("CD");
		this.add(CDLabel);

		OELabel = new ToggleLabel("OE");
		OELabel.addMouseListener(new OneWayMouseListener(this));
		this.add(OELabel);

		FELabel = new ToggleLabel("FE");
		FELabel.addMouseListener(new OneWayMouseListener(this));
		this.add(FELabel);

		PELabel = new ToggleLabel("PE");
		PELabel.addMouseListener(new OneWayMouseListener(this));
		this.add(PELabel);

		BILabel = new ToggleLabel("BI");
		BILabel.addMouseListener(new OneWayMouseListener(this));
		this.add(BILabel);

		DALabel = new Label("DA");
		this.add(DALabel);
		DA = false;

		BELabel = new Label("BE");
		this.add(BELabel);
		BE = true;

		onColor = Color.green;
		offColor = Color.black;
	}

	public void setPort(SerialPort	port)
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
	
			RTSLabel.setState(port.isRTS());
			CTSLabel.setForeground(port.isCTS()
						 ? onColor : offColor);
	
			DTRLabel.setState(port.isDTR());
			DSRLabel.setForeground(port.isDSR()
						 ? onColor : offColor);
	
			RILabel.setForeground(port.isRI() ? onColor : offColor);
			CDLabel.setForeground(port.isCD() ? onColor : offColor);
		}
	}

	public void showErrorValues()
	{
		if (this.port != null)
		{
			/*
			 *  Get the state of the error conditions for this port
			 */
	
			OELabel.setForeground(OELabel.getState()
						 ? onColor : offColor);

			FELabel.setForeground(FELabel.getState()
						 ? onColor : offColor);

			PELabel.setForeground(PELabel.getState()
						 ? onColor : offColor);

			BILabel.setForeground(BILabel.getState()
						 ? onColor : offColor);

			DALabel.setForeground(DA ? onColor : offColor);

			BELabel.setForeground(BE ? onColor : offColor);
		}
	}

	public void clearValues()
	{
		RTSLabel.setState(false);
		CTSLabel.setForeground(offColor);

		DTRLabel.setState(false);
		DSRLabel.setForeground(offColor);

		RILabel.setForeground(offColor);
		CDLabel.setForeground(offColor);
	}

	public void clearErrorValues()
	{
		OELabel.setForeground(offColor);
		FELabel.setForeground(offColor);
		PELabel.setForeground(offColor);
		BILabel.setForeground(offColor);
		DALabel.setForeground(offColor);
		BELabel.setForeground(offColor);
	}
}

class ToggleMouseListener implements MouseListener
{
	CtlSigDisplay	owner;

	public ToggleMouseListener(CtlSigDisplay	owner)
	{
		super();

		this.owner = owner;
	}

	public void mouseClicked(MouseEvent e)
	{
	}

	public void mouseEntered(MouseEvent e)
	{
	}

	public void mouseExited(MouseEvent e)
	{
	}

	public void mousePressed(MouseEvent e)
	{
		ToggleLabel	label = (ToggleLabel) e.getComponent();

		if (this.owner.port != null)
		{
			/*
			 *  Toggle the state of the control signal
			 */
	
			label.setState(!label.getState());
	
			if (label == this.owner.RTSLabel)
			{
				this.owner.port.setRTS(label.getState());
				label.setState(this.owner.port.isRTS());
			}
	
			else if (label == this.owner.DTRLabel)
			{
				this.owner.port.setDTR(label.getState());
				label.setState(this.owner.port.isDTR());
			}
		}
	}

	public void mouseReleased(MouseEvent e)
	{
	}
}

class OneWayMouseListener implements MouseListener
{
	CtlSigDisplay	owner;

	public OneWayMouseListener(CtlSigDisplay	owner)
	{
		super();

		this.owner = owner;
	}

	public void mouseClicked(MouseEvent e)
	{
	}

	public void mouseEntered(MouseEvent e)
	{
	}

	public void mouseExited(MouseEvent e)
	{
	}

	public void mousePressed(MouseEvent e)
	{
		ToggleLabel	label = (ToggleLabel) e.getComponent();

		label.setState(false);
	}

	public void mouseReleased(MouseEvent e)
	{
	}
}
