/*
 * @(#)SimpleRead.java	1.12 98/06/25 SMI
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

package javax.comm;

import	java.io.*;
import	java.util.*;

/**
 * Communications port management. <CODE>CommPortIdentifier</CODE>
 * is the central class for controlling access to communications ports.
 * It includes methods for:
 * <UL>
 * <LI>
 * Determining the communications ports made available by the driver.
 * <LI>
 * Opening communications ports for I/O operations.
 * <LI>
 * Determining port ownership.
 * <LI>
 * Resolving port ownership contention.
 * <LI>
 * Managing events that indicate changes in port ownership status.
 * </UL>
 * <P>
 * An application first uses methods in <CODE>CommPortIdentifier</CODE>
 * to negotiate with the driver to discover which communication ports
 * are available and then select a port for opening. It then uses
 * methods in other classes like <CODE>CommPort</CODE>, <CODE>ParallelPort</CODE>
 * and <CODE>SerialPort</CODE> to communicate through the port.
 *
 * @author      Jagane Sundar
 * @version     1.11, 23 Jan 1998
 * @see         javax.comm.CommPort
 * @see         javax.comm.CommPortOwnershipListener
 * @see         javax.comm.ParallelPort
 * @see         javax.comm.SerialPort
 */
public class CommPortIdentifier {

    /**
     * Obtains an enumeration object that contains a <CODE>CommPortIdentifier</CODE>
     * object for each port in the system.
     *
     * @return	an <CODE> Enumeration </CODE> object that can be used to
     *		enumerate all the ports known to the system
     *
     * @see	java.util.Enumeration
     */
    static public Enumeration getPortIdentifiers() {

	SecurityManager sec = System.getSecurityManager();
	if (sec != null) {
	    sec.checkRead(propfilename);
	}

	return new CommPortEnumerator();
    }

    /**
     * Obtains a <CODE>CommPortIdentifier</CODE> object by using a port name.
     * The port name may have been stored in persistent storage by the application.
     *
     * @param		<CODE>portName</CODE>	name of the port to open
     * @return		a <CODE>CommPortIdentifier</CODE> object
     * @exception	NoSuchPortException if the port does not exist
     */
    static public CommPortIdentifier getPortIdentifier(String portName) 
						throws NoSuchPortException {

	SecurityManager sec = System.getSecurityManager();
	if (sec != null) {
	    sec.checkRead(propfilename);
	}

	CommPortIdentifier	portId = null;

	synchronized (lock) {
	    portId = masterIdList;
	    while (portId != null) {
		if (portId.name.equals(portName))
		    break;
		portId = portId.next;
	    }
	}
	if (portId != null) {
	    return portId;
	} else {
	    throw new NoSuchPortException();
	}
    }

    /**
     * Obtains the <CODE>CommPortIdentifier</CODE> object corresponding
     * to a port that has already been opened by the application.
     *
     * @param		port	a CommPort object obtained from a previous open
     * @return		a CommPortIdentifier object
     * @exception	NoSuchPortException if the port object is invalid
     */
    static public CommPortIdentifier getPortIdentifier(CommPort port)
						throws NoSuchPortException {

	SecurityManager sec = System.getSecurityManager();
	if (sec != null) {
	    sec.checkRead(propfilename);
	}

        CommPortIdentifier   portId = null;

        synchronized (lock) {
            portId = masterIdList;
            while (portId != null) {
                if (portId.port == port)
                    break;
                portId = portId.next;
            }
        }
	if (portId != null) {
	    return portId;
	} else {
	    throw new NoSuchPortException();
	}
    }

    /**
     * Adds <CODE>CommPort</CODE> object to the list of ports.
     */
    private static void addPort(CommPort port, int portType) {

	SecurityManager sec = System.getSecurityManager();
	if (sec != null) {
	    sec.checkRead(propfilename);
	}

	CommPortIdentifier newentry = 
		    new CommPortIdentifier(port.getName(), port, portType, null);
	CommPortIdentifier cur = masterIdList, prev = null;

	synchronized (lock) {
	    while (cur != null) {
	        prev = cur;
	        cur = cur.next;
	    }
	    if (prev != null) {
	        prev.next = newentry;
	    } else {
	        masterIdList = newentry;
	    }
	}
    }

    /**
     * Adds <CODE>portName</CODE> to the list of ports.
     * @param	portName The name of the port being added
     * @param   portType The type of the port being added
     * @param   driver The driver representing the port being added
     * @see	javax.comm.CommDriver
     * @since CommAPI 1.1
     */
    public static void addPortName(String portName, int portType,
						CommDriver driver) {

	SecurityManager sec = System.getSecurityManager();
	if (sec != null) {
	    sec.checkRead(propfilename);
	}

	CommPortIdentifier newentry = 
		    new CommPortIdentifier(portName, null, portType, driver);
	CommPortIdentifier cur = masterIdList, prev = null;

	synchronized (lock) {
	    while (cur != null) {
	        prev = cur;
	        cur = cur.next;
	    }
	    if (prev != null) {
	        prev.next = newentry;
	    } else {
	        masterIdList = newentry;
	    }
	}
    }

    /**
     * Returns the name of the port. The port name should be identifiable
     * by the user. Ideally, it should be the label on the hardware.
     * For example, "COM1" and "COM2" on PCs; "Serial A" and "Serial B"
     * on Sun Ultra workstations. The port name may be stored by an application
     * and subsequently used to create a <CODE>CommPortIdentifier</CODE>
     * object using <CODE>getPortIdentifier(String portName)</CODE> method.
     *
     * @return	the name of the port
     */
    public String getName() {
	return name;
    }
    String name;

    private int portType;
    /**
     * RS-232 serial port
     */
    public static final int PORT_SERIAL = 1;
    /**
     * IEEE 1284 parallel port
     */
    public static final int PORT_PARALLEL = 2;

    /**
     * Returns the port type.
     *
     * @return	portType - PORT_SERIAL or PORT_PARALLEL
     */
    public int getPortType() {
	return portType;
    }

    private boolean maskOwnershipEvents;

    /**
     * Opens the communications port. <CODE>open</CODE> obtains
     * exclusive ownership of the port. If the port is owned by some
     * other application, a <CODE>PORT_OWNERSHIP_REQUESTED</CODE> event
     * is propagated using the <CODE>CommPortOwnershipListener</CODE>
     * event mechanism. If the application that owns the port calls
     * <CODE>close</CODE> during the event processing, then this
     * <CODE>open</CODE> will succeed.
     *
     * There is one <CODE>InputStream</CODE> and one <CODE>OutputStream</CODE>
     * associated with each port. After a port is opened with
     * <CODE>open</CODE>, then all calls to <CODE>getInputStream</CODE>
     * will return the same stream object until <CODE>close</CODE>
     * is called.
     *
     * @param	    appname	Name of application making this call.
     *              This name will become the owner of the port.
     *              Useful when resolving ownership contention.
     * @param	    timeout	time in milliseconds to block waiting
     *              for port open.
     * @return	    a <CODE>CommPort</CODE> object
     * @exception	PortInUseException if the port is in use by some
     *			    other application that is not willing to relinquish
     *              ownership
     */
    public synchronized CommPort open(String appname, int timeout)
						throws PortInUseException {

	if (owned) {
	    maskOwnershipEvents = true;
	    fireOwnershipEvent(
			CommPortOwnershipListener.PORT_OWNERSHIP_REQUESTED);
	    maskOwnershipEvents = false;
	    if (owned) {
		throw new PortInUseException(owner);
	    }
	}

	port = driver.getCommPort(name, portType);

	if (port == null) {

	    /*
	     * Unable to create port. Most likely, port is in use by
	     * another application
	     */
	    /*******************************************************************
	     * FILL IN HERE.
	     * Use underlying OS's IPC mechanisms to communicate with
	     * other Java VMs that may be willing to give up ownership of
	     * port.
	     ******************************************************************/
	    if (port == null) {
		throw new PortInUseException("Unknown Windows Application");
	    }

	}

	owned = true;
	owner = appname;

	return port;

    }

    /**
     * Returns the owner of the port.
     *
     * @return	current owner of the port. 
     */
    public String getCurrentOwner() {
	if (owned) {
	    return owner;
	} else {
	    /*******************************************************************
	     * FILL IN HERE.
	     * Use underlying OS's IPC mechanisms to obtain information
	     * about the current owner of the port. The current owner
	     * may be another Java COMM application or an OS native app.
	     ******************************************************************/
	}
    }

    /**
     * Checks whether the port is owned.
     *
     * @return	boolean	<CODE>true</CODE> if the port is owned by some application,
     * 		<CODE>false</CODE> if the port is not owned.
     */
    public boolean isCurrentlyOwned() {
	if (owned) {
	    return true;
	} else {
	    /*******************************************************************
	     * FILL IN HERE.
	     * Use underlying OS's IPC mechanisms to obtain information
	     * about the current owner of the port. The current owner
	     * may be another Java COMM application or an OS native app.
	     ******************************************************************/
	}
    }

    OwnershipEventThread oeThread;

    CpoList cpoList = new CpoList();

    /**
     * Registers an interested application so that it can receive notification
     * of changes in port ownership. This includes notification of the following events:
     * <UL>
     * <LI>
     * <CODE>PORT_OWNED</CODE>: Port became owned
     * <LI>
     * <CODE>PORT_UNOWNED</CODE>: Port became unowned
     * <LI>
     * <CODE>PORT_OWNERSHIP_REQUESTED</CODE>: If the application owns this port
     * and is willing to give up ownership, then it should call
     * <CODE>close</CODE> now.
     * </UL>
     * The <CODE>ownershipChange</CODE> method of the listener registered
     * using <CODE>addPortOwnershipListener</CODE> will be called with one
     * of the above events.
     *
     * @param	listener a <CODE>CommPortOwnershipListener</CODE> callback object
     */
    public void addPortOwnershipListener(CommPortOwnershipListener listener) {

	cpoList.add(listener);

	if (oeThread == null) {
	    oeThread = new OwnershipEventThread(this);
	    oeThread.start();
	}

    }

    /**
     * Deregisters a <CODE>CommPortOwnershipListener</CODE> registered using
     * <CODE>addPortOwnershipListener</CODE>
     *
     * @param	listener The CommPortOwnershipListener object that was
     *		previously registered using addPortOwnershipListener
     */
    public void removePortOwnershipListener(CommPortOwnershipListener lsnr) {

	cpoList.remove(lsnr);

    }

    void ownershipThreadWaiter() {

	    /*******************************************************************
	     * FILL IN HERE.
	     * Use underlying OS's IPC mechanisms to wait for ownership
	     * event notifications from other Java VMs that may be
	     * running Java Communications API applications.
	     ******************************************************************/
	int ret;
	if (ret) {
	    maskOwnershipEvents = true;
	    switch(ret) {
	    case 0:
		fireOwnershipEvent(CommPortOwnershipListener.PORT_OWNED);
		break;
	    case 1:
		fireOwnershipEvent(CommPortOwnershipListener.PORT_UNOWNED);
		break;
	    case 2:
	        fireOwnershipEvent(
			CommPortOwnershipListener.PORT_OWNERSHIP_REQUESTED);
		break;
	    }
	    maskOwnershipEvents = false;
	}

    }

    synchronized void internalClosePort() {
	owned = false;
	owner = null;
	port = null;
	if (!maskOwnershipEvents) {
	    fireOwnershipEvent(CommPortOwnershipListener.PORT_UNOWNED);
	}
    }

    CommPortIdentifier next;
    private CommPort port;
    private CommDriver driver;

    CommPortIdentifier(String name, CommPort port, int portType,CommDriver dr) {
	this.name = name;
	this.port = port;
	this.portType = portType;
	this.next = null;
	this.driver = dr;

    }

    void fireOwnershipEvent(int eventType) {

	/*
	 * Need to clone the ownership listener list, before firing the
	 * event, because the event handlers have a habit of calling
	 * removeOwnershipListener() which causes a deadlock.
	 */
	CpoList clone = cpoList.clonelist();
	clone.fireOwnershipEvent(eventType);

    }

    private static String[] parsePropsFile(InputStream in) {

        Vector strs = new Vector();

        try {

            byte ba[] = new byte[4096];
            int index = 0;
            boolean skip_to_eol = false;
            int b;
            while ((b = in.read()) != -1) {
                // System.err.println("Read - " + b);
                switch (b) {
                case '\t':
                case ' ':
                    break;
                case '\r':
                case '\n':
                    if (index > 0) {
                        String str = new String(ba, 0, 0,index);
                        strs.addElement(str);
                    }
                    index = 0;
                    skip_to_eol = false;
                    break;
                case '#':
                    skip_to_eol = true;
                    if (index > 0) {
                        String str = new String(ba, 0, 0,index);
                        strs.addElement(str);
                    }
                    index = 0;
                    break;
                default:
                    if (!skip_to_eol && index < 4096)
                        ba[index++] = (byte) b;
                }
            }
        } catch (Throwable ex) {
            System.err.println("Caught " + ex + " parsing prop file.");

        }

        // Convert to String array if strs has any elements.
        if (strs.size() > 0) {
            String strarray[] = new String[strs.size()];
            for (int i = 0; i < strs.size(); i++) {
                strarray[i] = (String) strs.elementAt(i);
            }
            return strarray;
        } else {
            return null;
        }

    }

    /* for synchronizing access to the list of ports */
    static Object 	lock;
    static String 	propfilename;
    static {

	System.err.println(
"****************************************************************************");
	System.err.println("Java Communications API - Early Access");
	System.err.println(
	    "Copyright (c) 1997 Sun Microsystems, Inc. All Rights Reserved.");
	System.err.println(
"****************************************************************************");

	lock = new Object();

	/*
	 * Check our property and our properties file for list
	 * of drivers
	 */
	String pdrivers;
	if ((pdrivers = System.getProperty("javax.comm.properties")) != null) {
	    System.err.println("Comm Drivers: " + pdrivers);
	}
	propfilename =   System.getProperty("java.home") +
				File.separator +
				"lib" +
				File.separator +
				"javax.comm.properties";
	File propfile = new File(propfilename);
	InputStream fis;
	try {
	    fis = new BufferedInputStream(new FileInputStream(propfile));

	    String drivers[] = parsePropsFile(fis);

	    if (drivers != null) {
		for (int i = 0; i < drivers.length; i++) {
		    if (drivers[i].regionMatches(true, 0, "driver=", 0, 7)) {
			String drivername = drivers[i].substring(7);
			drivername.trim();
			try {
			    CommDriver driver = (CommDriver)
					Class.forName(drivername).newInstance();
			    driver.initialize();
			} catch (Throwable th) {
			    System.err.println("Caught " + th 
				+ " while loading driver " + drivername);
			}
		    }
		}
	    }

	} catch (Throwable ex) {
	    /* silently ignore any exception */
	    System.err.println(ex);
	}

    }

    static CommPortIdentifier	masterIdList;

    /*
     * ownership of this port
     */
    boolean owned;	/* this boolean indicates ownership in this VM */
    String owner;	/* this value describes ownership within this VM */

    /**
     * Opens the communications port using a <CODE>FileDescriptor</CODE>
     * object on platforms that support this technique.
     *
     * @param		fd	The <CODE>FileDescriptor</CODE> object used to build
                        a <CODE>CommPort</CODE>.
     * @return		a <CODE>CommPort</CODE> object.
     * @exception	UnsupportedCommOperationException	is thrown
     *                  on platforms which do not support this functionality.
     */
    public CommPort open(FileDescriptor fd)
			throws UnsupportedCommOperationException {
	throw new UnsupportedCommOperationException();
    }
}

/**
 * List of ports.
 *
 * @author      Jagane Sundar
 * @version     1.11, 23 Jan 1998
 * @see         javax.comm.CommPort
 * @see         javax.comm.CommPortIdentifier
 */
class CommPortEnumerator implements Enumeration {

    private CommPortIdentifier	curEntry;

    /**
     * Returns the next element of this enumeration.
     *
     * @return	the next element of this enumeration.
     */
    public Object nextElement() {
	synchronized (CommPortIdentifier.lock) {
	    if (curEntry != null) {
		curEntry = curEntry.next;
	    } else {
		curEntry = CommPortIdentifier.masterIdList;
	    }
	}
	return curEntry;
    }

    /**
     * Tests if this enumeration contains more elements. 
     *
     * @return	true if this enumeration contains more elements; false otherwise.
     */
    public boolean hasMoreElements() {
	synchronized (CommPortIdentifier.lock) {
	    if (curEntry != null) {
		return curEntry.next == null ? false : true;
	    } else {
		return CommPortIdentifier.masterIdList == null ? false : true;
	    }
	}
    }
}

/**
 * Port thread management.
 *
 * @author      Jagane Sundar
 * @version     1.11, 23 Jan 1998
 * @see         javax.comm.CommPort
 * @see         javax.comm.CommPortIdentifier
 * @see         java.lang.thread
 */
class OwnershipEventThread extends Thread {

    CommPortIdentifier portId;

    OwnershipEventThread(CommPortIdentifier id) {
	portId = id;
    }

    /**
     * If this thread was constructed using a separate Runnable run object,
     * then that Runnable object's run method is called;
     * otherwise, <CODE>run</CODE> does nothing and returns. 
     */
    public void run() {

	while (!portId.cpoList.isEmpty()) {

	    portId.ownershipThreadWaiter();

	}
	portId.oeThread = null;

    }
}

class CpoListEntry {

    CpoListEntry		next;
    CommPortOwnershipListener	listener;

    CpoListEntry(CommPortOwnershipListener lsnr) {
	listener = lsnr;
	next = null;
    }

}

class CpoList {

    CpoListEntry	listHead = null;

    synchronized void add(CommPortOwnershipListener lsnr) {

	CpoListEntry cur = listHead;
	while (cur != null) {
	    if (cur.listener == lsnr) {
		return;
	    }
	    cur = cur.next;
	}

	CpoListEntry n = new CpoListEntry(lsnr);
	n.next = listHead;
	listHead = n;

    }

    synchronized void remove(CommPortOwnershipListener lsnr) {

	CpoListEntry prev = null;
	CpoListEntry cur = listHead;
	while (cur != null) {
	    if (cur.listener == lsnr) {
		if (prev != null)
		    prev.next = cur.next;
		else
		    listHead = cur.next;
		cur.listener = null;
		cur.next = null;
		return;
	    }
	    prev = cur;
	    cur = cur.next;
	}
    }

    synchronized CpoList clonelist() {
	CpoListEntry clonehead = null;
	CpoListEntry nclone = null;

	CpoListEntry cur = listHead;

	while (cur != null) {

	    nclone = new CpoListEntry(cur.listener);
	    nclone.next = clonehead;
	    clonehead = nclone;

	    cur = cur.next;
	}

	CpoList ret = new CpoList();
	ret.listHead = clonehead;
	return ret;
    }

    synchronized boolean isEmpty() {
	return listHead == null;
    }

    synchronized void fireOwnershipEvent(int eventType) {
	CpoListEntry cur = listHead;
	while (cur != null) {
	    cur.listener.ownershipChange(eventType);
	    cur = cur.next;
	}
    }

    synchronized void dump() {
	CpoListEntry cur = listHead;
	while (cur != null) {
	    System.err.println("    CpoListEntry - " + cur.listener.toString());
	    cur = cur.next;
	}
    }
}
