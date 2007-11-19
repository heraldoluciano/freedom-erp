/*
 * @(#)ReceiveFraming.java	1.2 98/07/17 SMI
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
import java.awt.TextField;
import java.awt.BorderLayout;

import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.comm.ParallelPort;
import javax.comm.UnsupportedCommOperationException;

public class ReceiveFraming extends Panel implements MouseListener, ActionListener
{
	private int		value,
				defaultValue;
	private Label		label;
	private TextField	data;
	private ParallelPort	port = null;
	private boolean		inputBuffer;

	public ReceiveFraming(int		size,
			      ParallelPort	port)
	{
		super();

		this.setPort(port);

		this.inputBuffer = inputBuffer;

		this.setLayout(new BorderLayout());

		this.label = new Label("Framing");
		this.label.addMouseListener(this);
		this.add("West", this.label);

		this.data = new TextField(new Integer(defaultValue).toString(), 
					     size);
		this.data.addActionListener(this);
		this.add("East", this.data);

		this.showValue();

		this.defaultValue = this.value;
	}

	public void setPort(ParallelPort	port)
	{
		this.port = port;
	}

	public int getValue()
	{
		/*
		 *  Get the current framing.
		 */

		if ((port != null) && port.isReceiveFramingEnabled())
		{
			this.value = port.getReceiveFramingByte();
		}

		else
		{
			this.value = 0;
		}

		return this.value;
	}

	public void showValue()
	{
		this.data.setText("0x" + Integer.toString(this.getValue(), 16));
	}

	public void setValue(int	val)
	{
		/*
		 *  Set the new framing.
		 */

		if (port != null)
		{
			if (val > 0)
			{
			    try {
				port.enableReceiveFraming(val);
			    } catch (UnsupportedCommOperationException ucoe) {
				ucoe.printStackTrace();
			    }

			}
	
			else
			{
				port.disableReceiveFraming();
			}
		}

		this.showValue();
	}

	public void setDefaultValue(int	val)
	{
		this.defaultValue = val;
	}

	public void actionPerformed(ActionEvent e)
	{
		String	s = e.getActionCommand();

		try
		{
			this.setValue(Integer.parseInt(s, 16));
		}

		catch (NumberFormatException ex)
		{
			System.out.println("Bad value = 0x"
					  + e.getActionCommand());

			this.showValue();
		}
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
		this.setValue(this.defaultValue);
	}

	public void mouseReleased(MouseEvent e)
	{
	}
}
