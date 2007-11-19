/*
 * @(#)NullDriver.java	1.12 98/06/25 SMI
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

package samples.NullDriver;

import	java.net.*;
import	java.io.*;
import	java.util.*;
import	javax.comm.*;


public class NullDriver implements CommDriver {

    /*
     * initialize() will be called by the CommPortIdentifier's static
     * initializer. The responsibility of this method is:
     * 1) Ensure that that the hardware is present.
     * 2) Load any required native libraries.
     * 3) Register the port names with the CommPortIdentifier.
     */
    public void initialize() {

	/*
	 * Do native initialization here.
	 * This may include loading any native shared libraries necessary.
	 */

	/* Register port names with CommPortIdentifier */
	CommPortIdentifier.addPortName("SerialPort1",
				CommPortIdentifier.PORT_SERIAL, this);
	CommPortIdentifier.addPortName("SerialPort2",
				CommPortIdentifier.PORT_SERIAL, this);

    }

    /*
     * getCommPort() will be called by CommPortIdentifier from its open()
     * method. portName is a string that was registered earlier using the
     * CommPortIdentifier.addPortName() method. getCommPort() returns an
     * object that extends either SerialPort or ParallelPort.
     */
    public CommPort getCommPort(String portName, int portType) {

	CommPort port = null;

	try {
	    switch (portType) {
	    case CommPortIdentifier.PORT_SERIAL:
		port = (CommPort) new NullSerialPort(portName);
		break;
	    case CommPortIdentifier.PORT_PARALLEL:
		break;
	    }
	} catch (IOException ex) {
	    /*
	     * We failed to construct the port object.
	     * Most likely the port is owned by another application.
	     */
	}
	return port;

    }

}
