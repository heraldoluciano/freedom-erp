/*
 * @(#)Transmitter.java	1.4 98/06/15 SMI
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

import java.lang.Thread;

import java.io.IOException;

import java.awt.Panel;
import java.awt.Label;
import java.awt.TextArea;
import java.awt.Checkbox;
import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.FlowLayout;

import java.awt.event.TextListener;
import java.awt.event.TextEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

import javax.comm.ParallelPort;

public class Transmitter extends Panel implements TextListener, ItemListener, Runnable
{
	private Panel			p,
					p1;
	private TextArea		text;
	private Checkbox		auto;
	private ByteStatistics		counter;
	private ParallelPortDisplay	owner;
	private	Thread			thr;
	private Color			onColor,
					offColor;

	private boolean			first;
	static  String			testString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ\r\nabcdefghijklmnopqrstuvwxyz\r\n1234567890\r\n";		

	public Transmitter(ParallelPortDisplay	owner,
			   int			rows,
			   int			cols)
	{
		super();

		this.first = true;

		this.owner = owner;

		this.setLayout(new BorderLayout());

		p = new Panel();
		p.setLayout(new FlowLayout());

		p1 = new Panel();
		p1.setLayout(new BorderLayout());

		p1.add("West", new Label("Auto Transmit"));
		auto = new Checkbox();
		auto.addItemListener(this);
		p1.add("East", auto);
		p.add(p1);

		this.add("North", p);

		this.text = new TextArea(rows, cols);
		this.text.append("Type here");
		this.text.addTextListener(this);
		this.add("Center", text);

		this.counter = new ByteStatistics("Bytes Sent", 10, 
						  owner.port, false);
		this.add("South", this.counter);

		this.thr = null;

		this.onColor = Color.green;
		this.offColor = Color.black;
	}

	public void setPort(ParallelPort	port)
	{
		this.counter.setPort(port);
	}

	public void showValues()
	{
		this.counter.showValues();
	}

	public void clearValues()
	{
		this.counter.clearValues();
	}

	public void setBitsPerCharacter(int	val)
	{
		this.counter.setBitsPerCharacter(val);
	}

	/*
	 *	Handler for transmit text area events
	 */

	public void textValueChanged(TextEvent	ev)
	{
		if (first && (this.text.getCaretPosition() > 0))
		{
			first = false;

			this.text.replaceRange("", 
					       0, 
					       this.text.getCaretPosition()
						 - 1);
		}

		if (!first)
		{
			this.sendData();
		}

	}

	public void run()
	{
		this.sendData();
	}

	public void sendString(String 	str)
	{
		int	count;

		count = str.length();

		if (count > 0)
		{
			this.owner.ctlSigs.BE = false;
	
			try
			{
				this.owner.out.write(str.getBytes());
	
				this.counter.incrementValue((long) count);
	
				this.owner.ctlSigs.showValues();
				this.owner.ctlSigs.showErrorValues();
			}
	
			catch (IOException ex)
			{
				if (this.owner.open)
				{
					System.out.println(owner.port.getName() 
							   + ": Cannot write to output stream");
	
					this.auto.setState(false);
				}
			}
		}
	}

	private void sendData()
	{
		String	str;

		if (this.owner.open && this.auto.getState())
		{
			while (this.owner.open && this.auto.getState())
			{
				sendString(testString);
			}
		}

		else
		{
			str = this.text.getText();

			sendString(str);

			this.text.setText("");
		}
	} 

	/*
	 *	Handler for checkbox events
	 */

	public void itemStateChanged(ItemEvent	ev)
	{
		if (this.auto.getState() && (thr == null) && this.owner.open)
		{
			this.auto.setForeground(this.onColor);

			startTransmit();
		}

		else
		{
			stopTransmit();
		}
	}

	private void startTransmit()
	{
		if (this.owner.open && (thr == null))
		{
			counter.resetRate();

			thr = new Thread(this, "Xmt " + owner.port.getName());

			thr.start();
		}
	}

	public void stopTransmit()
	{
		thr = null;

		this.auto.setState(false);
		this.auto.setForeground(this.offColor);
	}
}
