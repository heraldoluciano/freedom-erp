/*
 * @(#)BlackBox.java	1.13 98/10/20 SMI
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

import java.io.*;

import java.awt.Frame;
import java.awt.FlowLayout;
import java.awt.Color;
import java.awt.Insets;
import java.awt.Dimension;
import java.awt.event.WindowListener;
import java.awt.event.WindowEvent;

import java.util.Enumeration;

import javax.comm.CommPort;
import javax.comm.CommPortIdentifier;
import javax.comm.SerialPort;
import javax.comm.NoSuchPortException;
import javax.comm.PortInUseException;

public class BlackBox extends Frame implements WindowListener
{
	static int			portNum = 0,
					panelNum = 0,
					rcvDelay = 0;
	static SerialPortDisplay[]	portDisp;
	static BlackBox 		win;
	static boolean			threaded = true,
					silentReceive = false,
					modemMode = false,
					friendly = false;

	public BlackBox()
	{
		super("Serial Port Black Box Tester");
		addNotify();

		addWindowListener(this);
	}

	public void windowIconified(WindowEvent event)
	{
	}

	public void windowDeiconified(WindowEvent event)
	{
	}

	public void windowOpened(WindowEvent event)
	{
	}

	public void windowClosed(WindowEvent event)
	{
	}

	public void windowActivated(WindowEvent event)
	{
	}

	public void windowDeactivated(WindowEvent event)
	{
	}

	public void windowClosing(WindowEvent event)
	{
		cleanup();
		dispose();
		System.exit(0);
	}

	public static void main(String[] args)
	{
		Enumeration 		ports;
		CommPortIdentifier	portId;
		boolean			allPorts = true,
					lineMonitor = false;
		int			idx = 0;

		win = new BlackBox();
		win.setLayout(new FlowLayout());
		win.setBackground(Color.gray);

		portDisp = new SerialPortDisplay[4];

		while (args.length > idx)
		{
			if (args[idx].equals("-h"))
			{
				printUsage();
			}

			else if (args[idx].equals("-f"))
			{
				friendly = true;
	
				System.out.println("Friendly mode");
			}

			else if (args[idx].equals("-n"))
			{
				threaded = false;
	
				System.out.println("No threads");
			}

			else if (args[idx].equals("-l"))
			{
				lineMonitor = true;
	
				System.out.println("Line Monitor mode");
			}

			else if (args[idx].equals("-m"))
			{
				modemMode = true;
	
				System.out.println("Modem mode");
			}

			else if (args[idx].equals("-s"))
			{
				silentReceive = true;
	
				System.out.println("Silent Reciever");
			}

			else if (args[idx].equals("-d"))
			{
				idx++;
				rcvDelay = new Integer(args[idx]).intValue();
	
				System.out.println("Receive delay = "
						  + rcvDelay + " msecs");
			}

			else if (args[idx].equals("-p"))
			{
				idx++;

				while (args.length > idx)
				{
					/*
					 *  Get the specific port
					 */
		
					try
					{
						portId = 
						   CommPortIdentifier.getPortIdentifier(args[idx]);

						System.out.println("Opening port "
							    + portId.getName());
	
						win.addPort(portId);
					}
	
					catch (NoSuchPortException e)
					{
						System.out.println("Port "
								  + args[idx]
								  + " not found!");
					}

					idx++;
				}

				allPorts = false;

				break;
			}

			else
			{
				System.out.println("Unknown option "
						  + args[idx]);
				printUsage();
			}

			idx++;
		}

		if (allPorts)
		{
			/*
			 *  Get an enumeration of all of the comm ports 
			 *  on the machine
			 */
	
			ports = CommPortIdentifier.getPortIdentifiers();
	
			if (ports == null)
			{
				System.out.println("No comm ports found!");
	
				return;
			}

			while (ports.hasMoreElements())
			{
				/*
				 *  Get the specific port
				 */
	
				portId = (CommPortIdentifier) 
							ports.nextElement();

				win.addPort(portId);
			}
		}

		if (portNum > 0)
		{
			if (lineMonitor)
			{
				if (portNum >= 2)
				{
					portDisp[0].setLineMonitor(portDisp[1], 
								   true);
				}

				else
				{
					System.out.println("Need 2 ports for line monitor!");
					System.exit(0);
				}
			}
		}

		else
		{
			System.out.println("No serial ports found!");
			System.exit(0);
		}
    	}

	private void addPort(CommPortIdentifier	portId)
	{
		/*
		 *  Is this a serial port?
		 */

		if (portId.getPortType()
		 == CommPortIdentifier.PORT_SERIAL)
		{
			//  Is the port in use?	

			if (portId.isCurrentlyOwned())
			{
				System.out.println("Detected "
						   + portId.getName()
						   + " in use by "
						   + portId.getCurrentOwner());
			}

			/*
			 *  Open the port and add it to our GUI
			 */

			try
			{
				portDisp[portNum] = new 
					SerialPortDisplay(portId, 
							  threaded,
							  friendly,
							  silentReceive,
							  modemMode,
							  rcvDelay,
							  win);

				this.portNum++;
			}

			catch (PortInUseException e)
			{
				System.out.println(portId.getName()
						   + " in use by "
						   + e.currentOwner);
			}
		}
	}

	public void addPanel(SerialPortDisplay	panel)
	{
		Dimension	dim;
		Insets		ins;

		win.add(panel);

		win.validate();

		dim = panel.getSize();

		ins = win.getInsets();

		dim.height = ((this.panelNum + 1) * (dim.height + ins.top
				                  + ins.bottom)) + 10;

		dim.width = dim.width + ins.left + ins.right + 20;

		win.setSize(dim);

		win.show();

		panelNum++;
	}

	static void printUsage()
	{
		System.out.println("Usage: BlackBox [-h] | [-f] [-l] [-m] [-n] [-s] [-d receive_delay] [-p ports]");

		System.out.println("Where:");

		System.out.println("\t-h	this usage message");

		System.out.println("\t-f	friendly - relinquish port if requested");

		System.out.println("\t-l	run as a line monitor");

		System.out.println("\t-m	newline is \\n\\r (modem mode)");

		System.out.println("\t-n	do not use receiver threads");

		System.out.println("\t-s	don't display received data");

		System.out.println("\t-d	sleep for receive_delay msecs after each read");

		System.out.println("\t-p	list of ports to open (separated by spaces)");

		System.exit(0);
	}

	private void cleanup()
	{
		SerialPort	p;

		while (portNum > 0)
		{
			portNum--;
			panelNum--;

			/*
			 *  Close the port
			 */

			p = portDisp[portNum].getPort();

			if (p != null)
			{
				System.out.println("Closing port "
						   + portNum 
						   + " ("
						   + p.getName()
						   + ")");

				portDisp[portNum].closeBBPort();
			}
		}
	}
}
