/*
 * @(#)NullSerialPort.java	1.12 98/06/25 SMI
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

import	java.io.*;
import  java.util.TooManyListenersException;
import	javax.comm.*;

class NullSerialPort extends SerialPort {

    NullSerialPort(String name) throws IOException {
	this.name = name;
    }

    private InputStream ins;
    public InputStream getInputStream() throws IOException {
	if (closed) throw new IllegalStateException("Port Closed");
	return ins;
    }

    private OutputStream outs;
    public OutputStream getOutputStream()  throws IOException {
	if (closed) throw new IllegalStateException("Port Closed");
	return outs;
    }

    /**
     * The following methods deal with receive thresholds
     */

    private int rcvThreshold = -1;

    public void enableReceiveThreshold(int thresh) 
	throws UnsupportedCommOperationException {

	if (closed) throw new IllegalStateException("Port Closed");
	rcvThreshold = thresh;
    }

    public void disableReceiveThreshold() {
	if (closed) throw new IllegalStateException("Port Closed");
	rcvThreshold = -1;
    }
    public boolean isReceiveThresholdEnabled() {
	if (closed) throw new IllegalStateException("Port Closed");
	return rcvThreshold != -1;
    }
    public int getReceiveThreshold() {
	if (closed) throw new IllegalStateException("Port Closed");
	return rcvThreshold;
    }

    /**
     * The following methods deal with receive timeouts
     */
    int rcvTimeout = -1;

    public void enableReceiveTimeout(int rcvTimeout) 
	throws UnsupportedCommOperationException {

	if (closed) throw new IllegalStateException("Port Closed");
	this.rcvTimeout = rcvTimeout;
    }

    public void disableReceiveTimeout() {
	if (closed) throw new IllegalStateException("Port Closed");
	this.rcvTimeout = -1;
    }

    public int getReceiveTimeout() {
	if (closed) throw new IllegalStateException("Port Closed");
	return rcvTimeout;
    }

    public boolean isReceiveTimeoutEnabled() {
	if (closed) throw new IllegalStateException("Port Closed");
	return rcvTimeout == -1 ? false : true;
    }

    /**
     * The following methods deal with receive framing
     */
    private boolean	framing = false;
    private int		framingByte;
    boolean		framingByteReceived;

    public void disableReceiveFraming() {
	if (closed) throw new IllegalStateException("Port Closed");
	framing = false;
    }

    public void enableReceiveFraming(int framingByte) 
	throws UnsupportedCommOperationException {
	if (closed) throw new IllegalStateException("Port Closed");
	framing = true;
	this.framingByte = framingByte & 0xff;
    }

    public int getReceiveFramingByte() {
	if (closed) throw new IllegalStateException("Port Closed");
	return framingByte;
    }

    public boolean isReceiveFramingEnabled() {
	if (closed) throw new IllegalStateException("Port Closed");
	return framing;
    }

    public void setInputBufferSize(int size) {
	if (closed) throw new IllegalStateException("Port Closed");
    }
    public int getInputBufferSize() {
	if (closed) throw new IllegalStateException("Port Closed");
	return 0;
    }
    public void setOutputBufferSize(int size) {
	if (closed) throw new IllegalStateException("Port Closed");
    }
    public int getOutputBufferSize() {
	if (closed) throw new IllegalStateException("Port Closed");
	return 0;
    }

    /* Serial Port methods */

    private int baudrate;
    private int parity;
    private int dataBits;
    private int stopBits;
    private int flowcontrol;

    public int getBaudRate() {
	if (closed) throw new IllegalStateException("Port Closed");
	return baudrate;
    }
    public int getDataBits() {
	if (closed) throw new IllegalStateException("Port Closed");
	return dataBits;
    }
    public int getStopBits() {
	if (closed) throw new IllegalStateException("Port Closed");
	return stopBits;
    }
    public int getParity() {
	if (closed) throw new IllegalStateException("Port Closed");
	return parity;
    }
    public void setFlowControlMode(int flowcontrol) 
	throws UnsupportCommOperationException {
	if (closed) throw new IllegalStateException("Port Closed");
	this.flowcontrol = flowcontrol;
    }
    public int getFlowControlMode() {
	if (closed) throw new IllegalStateException("Port Closed");
	return flowcontrol;
    }

    public void setSerialPortParams(int baudrate, int dataBits,
	int stopBits, int parity) throws UnsupportedCommOperationException {

	if (closed) throw new IllegalStateException("Port Closed");

	this.baudrate = baudrate;
	this.parity = parity;
	this.dataBits = dataBits;
	this.stopBits = stopBits;

    }

    public void sendBreak(int millis)  {
	if (closed) throw new IllegalStateException("Port Closed");
    }

    private boolean dtr = true;
    public void setDTR(boolean dtr) {

	if (closed) throw new IllegalStateException("Port Closed");

	/*
	 * Illegal to change state of DTR when hardware flow control is on
	 */
	if ((flowcontrol & FLOWCONTROL_RTSCTS_IN) == FLOWCONTROL_RTSCTS_IN) {
	    return;
	}
	this.dtr = dtr;

    }
    public boolean isDTR() {
	if (closed) throw new IllegalStateException("Port Closed");
	return dtr;
    }

    private boolean rts = true;
    public void setRTS(boolean rts) {

	if (closed) throw new IllegalStateException("Port Closed");

	/*
	 * Illegal to change state of RTS when hardware flow control is on
	 */
	if ((flowcontrol & FLOWCONTROL_RTSCTS_IN) == FLOWCONTROL_RTSCTS_IN) {
	    throw new IllegalStateException(
			"Cannot modify RTS when Hardware flowcontrol is on.");
	}

	this.rts = rts;
    }
    public boolean isRTS() {
	if (closed) throw new IllegalStateException("Port Closed");
	return rts;
    }

    public boolean isCTS() {
	if (closed) throw new IllegalStateException("Port Closed");
	return true;
    }

    public boolean isDSR() {
	if (closed) throw new IllegalStateException("Port Closed");
	return true;
    }

    public boolean isRI() {
	if (closed) throw new IllegalStateException("Port Closed");
	return false;
    }

    public boolean isCD() {
	if (closed) throw new IllegalStateException("Port Closed");
	return true;
    }

    public synchronized void addEventListener(SerialPortEventListener lsnr) 
	throws TooManyListenersException
	{

	if (closed) throw new IllegalStateException("Port Closed");

    }

    public synchronized void removeEventListener() {
	if (closed) throw new IllegalStateException("Port Closed");
    }

    public synchronized void notifyOnDataAvailable(boolean notify) {
	if (closed) throw new IllegalStateException("Port Closed");
    }

    public synchronized void notifyOnOutputEmpty(boolean notify) {
	if (closed) throw new IllegalStateException("Port Closed");
    }

    public synchronized void notifyOnCTS(boolean notify) {
	if (closed) throw new IllegalStateException("Port Closed");
    }

    public synchronized void notifyOnDSR(boolean notify) {
	if (closed) throw new IllegalStateException("Port Closed");
    }

    public synchronized void notifyOnCarrierDetect(boolean notify) {
	if (closed) throw new IllegalStateException("Port Closed");
    }

    public synchronized void notifyOnRingIndicator(boolean notify) {
	if (closed) throw new IllegalStateException("Port Closed");
    }

    public synchronized void notifyOnOverrunError(boolean notify) {
	if (closed) throw new IllegalStateException("Port Closed");
    }

    public synchronized void notifyOnParityError(boolean notify) {
	if (closed) throw new IllegalStateException("Port Closed");
    }

    public synchronized void notifyOnFramingError(boolean notify) {
	if (closed) throw new IllegalStateException("Port Closed");
    }

    public synchronized void notifyOnBreakInterrupt(boolean notify) {
	if (closed) throw new IllegalStateException("Port Closed");
    }

    /* finalizer. so we can close the comm port and release native resources */
    protected void finalize() throws Throwable {
    }

    private boolean closed = false;

    public void close() {

	closed = true;
	/*
	 * WARNING *** WARNING *** WARNING *** WARNING *** WARNING *** WARNING
	 * AFTER YOU ARE DONE WITH YOUR OWN close() processing,
	 * YOU MUST MAKE THIS CALL TO CommPort's close(). OTHERWISE
	 * CommPort's HOUSEKEEPING WILL FAIL.
	 * WARNING *** WARNING *** WARNING *** WARNING *** WARNING *** WARNING
	 */
	super.close();

    }

}
