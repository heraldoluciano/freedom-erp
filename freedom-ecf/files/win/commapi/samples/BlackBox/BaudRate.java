/*
 * @(#)BaudRate.java	1.3 98/07/16 SMI
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

class BaudRate extends Choice implements ItemListener
{
	private SerialPortDisplay	owner;

	public BaudRate(SerialPortDisplay	owner)
	{
		super();

		this.add("Unknown");
		this.add("50");
		this.add("75");
		this.add("110");
		this.add("134");
		this.add("150");
		this.add("200");
		this.add("300");
		this.add("600");
		this.add("1200");
		this.add("1800");
		this.add("2400");
		this.add("4800");
		this.add("9600");
		this.add("19200");
		this.add("28800");  // This is a known bad value to test
		this.add("38400");
		this.add("57600");
		this.add("115200");
		this.addItemListener(this);

		this.owner = owner;
	}

	protected void showValue()
	{
		SerialPort	port = this.owner.port;

		if (this.owner.open)
		{
			/*
			 *  Get the baud rate
			 */
	
			this.select(new Integer(port.getBaudRate()).toString());
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

		if (sel.equals("Unknown"))
		{
			this.showValue();
			return;
		}

		else
		{
			value = (new Integer(sel)).intValue();
		}

		port = this.owner.port;

		if ((value > 0) && (port != null))
		{
			/*
			 *  Set the baud rate.
			 *
			 *  Note: we must set all of the parameters, not just 
			 *  the baud rate, hence the use of get*
			 */

			try
			{
				port.setSerialPortParams(value,
							 port.getDataBits(), 
							 port.getStopBits(), 
							 port.getParity());
			}
		
			catch (UnsupportedCommOperationException e)
			{
				System.out.println("Cannot set baud rate to "
						   + sel + " for port " 
						   + port.getName());
			}
		}

		this.owner.showValues();
	}
}

