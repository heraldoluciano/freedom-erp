/*
 * @(#)LPTMode.java	1.3 98/07/17 SMI
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

import javax.comm.ParallelPort;
import javax.comm.UnsupportedCommOperationException;

class LPTMode extends Choice implements ItemListener
{
	private ParallelPortDisplay	owner;

	public LPTMode(ParallelPortDisplay	owner)
	{
		super();

		this.add("Unknown");
		this.add("Any");
		this.add("ECP");
		this.add("EPP");
		this.add("NIBBLE");
		this.add("PS2");
		this.add("SPP");
		this.addItemListener(this);

		this.owner = owner;
	}

	protected void showValue()
	{
		ParallelPort	port = this.owner.port;

		if (this.owner.open)
		{
			/*
			 *  Get the port mode
			 */
	
			switch (this.owner.port.getMode())
			{
			case ParallelPort.LPT_MODE_ANY:
	
				this.select("Any");
				break;
	
			case ParallelPort.LPT_MODE_ECP:
	
				this.select("ECP");
				break;
	
			case ParallelPort.LPT_MODE_EPP:
	
				this.select("EPP");
				break;
	
			case ParallelPort.LPT_MODE_NIBBLE:
	
				this.select("NIBBLE");
				break;
	
			case ParallelPort.LPT_MODE_PS2:
	
				this.select("PS2");
				break;
	
			case ParallelPort.LPT_MODE_SPP:
	
				this.select("SPP");
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
		ParallelPort	port;
		String 		sel = (String) ev.getItem();
		int		value = 0;

		if (sel.equals("Any"))
		{
			value = ParallelPort.LPT_MODE_ANY;
		}

		else if (sel.equals("ECP"))
		{
			value = ParallelPort.LPT_MODE_ECP;
		}

		else if (sel.equals("EPP"))
		{
			value = ParallelPort.LPT_MODE_EPP;
		}

		else if (sel.equals("NIBBLE"))
		{
			value = ParallelPort.LPT_MODE_NIBBLE;
		}

		else if (sel.equals("PS2"))
		{
			value = ParallelPort.LPT_MODE_PS2;
		}

		else if (sel.equals("SPP"))
		{
			value = ParallelPort.LPT_MODE_SPP;
		}

		else
		{
			this.showValue();
			return;
		}


		port = this.owner.port;

		if (port != null)
		{
			/*
			 *  Set the mode.
			 */

			try
			{
				port.setMode(value);
			}

			catch (UnsupportedCommOperationException e)
			{
				System.out.println("Cannot set mode to "
						   + sel + " for port " 
						   + port.getName());
			}

		}

		this.owner.showValues();
	}
}

