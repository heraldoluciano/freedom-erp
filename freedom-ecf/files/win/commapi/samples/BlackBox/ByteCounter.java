/*
 * @(#)ByteCounter.java	1.4 98/06/01 SMI
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

public class ByteCounter extends Panel implements MouseListener
{
	private long		value,
				defaultValue;
	private Label		countLabel;
	private TextField	counter;

	public ByteCounter(String	text,
			   int		size,
			   int		defaultValue)
	{
		super();

		this.setLayout(new BorderLayout());

		this.countLabel = new Label(text);
		this.countLabel.addMouseListener(this);
		this.add("West", this.countLabel);

		this.counter = new TextField(new Integer(defaultValue).toString(), 
					     size);
		this.add("East", this.counter);

		this.value = defaultValue;
		this.defaultValue = defaultValue;
	}

	public ByteCounter(String	text,
			   int		size)
	{
		this(text, size, 0);
	}

	public long getValue()
	{
		return this.value;
	}

	public void setValue(long	val)
	{
		this.value = val;

		this.counter.setText(new Long(this.value).toString());
	}

	public void setDefaultValue(long	val)
	{
		this.defaultValue = val;
	}

	public void incrementValue(long	val)
	{
		this.value += val;

		this.counter.setText(new Long(this.value).toString());
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
