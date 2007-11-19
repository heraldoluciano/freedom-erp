/*
 * @(#)StopBits.java	1.3 98/07/17 SMI
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

import java.awt.Choice;

import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

import javax.comm.SerialPort;
import javax.comm.UnsupportedCommOperationException;

class StopBits extends Choice implements ItemListener
{
	private SerialPortDisplay	owner;

	public StopBits(SerialPortDisplay	owner)
	{
		super();

		this.add("Unknown");
		this.add("1");
		this.add("1.5");
		this.add("2");
		this.addItemListener(this);

		this.owner = owner;
	}

	protected void showValue()
	{
		if (this.owner.open)
		{
			/*
			 *  Get the number of stop bits
			 */
	
			switch (this.owner.port.getStopBits())
			{
			case SerialPort.STOPBITS_1:
	
				this.select("1");
				this.owner.numDataBits += 1;
				break;
	
			case SerialPort.STOPBITS_2:
	
				this.select("2");
				this.owner.numDataBits += 2;
				break;
	
			case SerialPort.STOPBITS_1_5:
	
				this.select("1.5");
				this.owner.numDataBits += 2;
				break;
	
			default:
	
				this.select("Unknown");
				break;
			}
		}

		else
		{
			this.select("Unknown");
		}
	}

	public void itemStateChanged(ItemEvent	ev)
	{
		SerialPort	port;
		String 		sel = (String) ev.getItem();
		int		value = 0;

		if (sel.equals("1"))
		{
			value = SerialPort.STOPBITS_1;
		}

		else if (sel.equals("2"))
		{
			value = SerialPort.STOPBITS_2;
		}

		else if (sel.equals("1.5"))
		{
			value = SerialPort.STOPBITS_1_5;
		}

		else
		{
			this.showValue();
		}

		port = this.owner.port;

		if ((value > 0) && (port != null))
		{
			/*
			 *  Set the number of stop bits.
			 *
			 *  Note: we must set all of the parameters, not just 
			 *  the number of stop bits, hence the use of get*
			 */

			try
			{
				port.setSerialPortParams(port.getBaudRate(),
							 port.getDataBits(), 
							 value, 
							 port.getParity());
			}
		
			catch (UnsupportedCommOperationException e)
			{
				System.out.println("Cannot set stop bit size to "
						   + sel + " for port " 
						   + port.getName());
			}
		}

		this.owner.showValues();
	}
}

