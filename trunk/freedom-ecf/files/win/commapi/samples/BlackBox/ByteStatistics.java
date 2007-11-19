/*
 * @(#)ByteStatistics.java	1.11 98/06/01 SMI
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
import java.awt.BorderLayout;

import javax.comm.SerialPort;

public class ByteStatistics extends Panel
{
	private BufferSize	buffer;
	private ByteCounter	counter,
				rate;
	private	long		lastTime,
				lastBaudRate,
				lastCount;
	private int		bitsPerCharacter;
	private boolean		avgRate,
				inputBuffer;
	private SerialPort	port;

	public ByteStatistics(String		text,
			      int		size,
			      SerialPort	port,
			      boolean		inputBuffer,
			      boolean   	average)
	{
		super();

		this.inputBuffer = inputBuffer;

		this.setLayout(new BorderLayout());

		counter = new ByteCounter(text, size);

		this.add("West", counter);

		rate = new ByteCounter("Baud Rate", 6);

		this.add("East", rate);

		buffer = new BufferSize(6, port, inputBuffer);

		this.add("Center", buffer);

		this.setBitsPerCharacter(10);

		this.setPort(port);

		this.lastTime = 0;

		this.lastCount = 0;

		this.lastBaudRate = 0;

		if (average)
		{
			this.avgRate = true;
		}

		else 
		{
			this.avgRate = false;
		}
	}

	public ByteStatistics(String		text,
			      int		size,
			      SerialPort	port,
			      boolean		inputBuffer)
	{
		this(text, size, port, inputBuffer, true);
	}

	public void setPort(SerialPort	port)
	{
		this.port = port;

		this.buffer.setPort(port);
	}

	public void setBitsPerCharacter(int	val)
	{
		this.bitsPerCharacter = val;
	}

	public void showValues()
	{
		this.buffer.showValue();
	}

	public void clearValues()
	{
		this.buffer.setValue(0);

		this.counter.setValue(0);

		this.rate.setValue(0);
	}

	public long getValue()
	{
		return this.counter.getValue();
	}

	public void setValue(long	val)
	{
		if (val == 0)
		{
			this.resetRate();
		}

		this.counter.setValue(val);

		this.setRate();
	}

	public void incrementValue(long	val)
	{
		this.counter.incrementValue(val);

		this.setRate();
	}

	public void resetRate()
	{
/*
		if (port != null)
		{
			if (inputBuffer)
			{
				System.out.println("Reseting receive baud rate for "
						  + port.getName());
			}
	
			else
			{
				System.out.println("Reseting transmit baud rate for "
						  + port.getName());
			}
		}
*/
		this.lastTime = 0;
		this.lastBaudRate = 0;
	}

	private void setRate()
	{
		long	baudRate,
			time = System.currentTimeMillis(),
			val = this.counter.getValue();

		if ((this.rate.getValue() == 0) && (this.lastBaudRate != 0))
		{
			this.resetRate();
		}

		if ((this.lastTime == 0) || (val == 0)
		 || (val < this.lastCount))
		{
			this.lastTime = time;
			this.lastCount = val;
		}

		else if (time > this.lastTime)
		{
			baudRate = ((val - this.lastCount)
				 * this.bitsPerCharacter * 1000)
				 / (time - this.lastTime);

//		System.out.println("((" + val + " - " + this.lastCount + ")"
//				   + " * " + this.bitsPerCharacter
//				   + " * 1000 ) / "
//				   + "(" + time + " - " + this.lastTime + ")");

			if (this.avgRate)
			{
				/*
				 *  This is a silly hack to auto-reset 
				 *  the counter if the user stops transmitting
				 */

				if (inputBuffer && (baudRate < 10))
				{
					this.resetRate();
				}
			}

			else
			{
				this.lastCount = val;
				this.lastTime = time;
			}

			this.rate.setValue((int) baudRate);
			this.lastBaudRate = baudRate;
		}
	}
}
