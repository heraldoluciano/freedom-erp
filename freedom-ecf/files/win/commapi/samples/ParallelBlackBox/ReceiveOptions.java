/*
 * @(#)ReceiveOptions.java	1.1 98/06/02 SMI
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
import java.awt.FlowLayout;

import javax.comm.ParallelPort;

public class ReceiveOptions extends Panel
{
	private ReceiveTimeout		timeout;
	private ReceiveThreshold	threshold;
	private ReceiveFraming		framing;

	public ReceiveOptions(ParallelPort	port)
	{
		super();

		this.setLayout(new FlowLayout());

		timeout = new ReceiveTimeout(6, port);
		this.add(timeout);

		threshold = new ReceiveThreshold(6, port);
		this.add(threshold);

		framing = new ReceiveFraming(6, port);
		this.add(framing);
	}

	public void setPort(ParallelPort	port)
	{
		this.timeout.setPort(port);
		this.threshold.setPort(port);
		this.framing.setPort(port);
	}

	public void showValues()
	{
		this.timeout.showValue();
		this.threshold.showValue();
		this.framing.showValue();
	}

	public void clearValues()
	{
		this.timeout.setValue(0);
		this.threshold.setValue(0);
		this.framing.setValue(0);
	}
}
