/*******************************************************************************
 * Copyright (c) 2011 cnfree.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  cnfree  - initial API and implementation
 *******************************************************************************/
package org.sf.feeling.swt.win32.extension.jna.ptr;

import org.sf.feeling.swt.win32.extension.jna.exception.NativeException;

/**
 * <p>This class encapsulate a native NULL pointer.</p>
 */
public class NullPointer extends Pointer {
	public static Pointer NULL = new NullPointer();
	/**
	 * Constructor creates a null Pointer with a size of allocated memory set to zero
	 *
	 */
	public NullPointer() {
		super(null);
	}
	
	/**
	 * Method dispose does nothing because there is no need to free some memory
	 *
	 */
	
	public void dispose() {
	}
	
	/**
	 * Method getPointer
	 * @return   the address of this pointer
	 */
	
	public int getPointer() {
		return 0;
	}
	
	
	
	/**
	 * Method getSize
	 * @return   the size of the memory block addressed by this pointer
	 */
	
	public int getSize() {
		return 4;
	}
	
	
	
	
	/**
	 * Method getAsByte
	 *
	 * @param    offset              not used
	 *
	 * @return   nothing
	 *
	 * @exception   NativeException
	 * @exception   NullPointerException allways
	 *
	 */
	
	public byte getAsByte(int offset) throws NativeException {
		throw new NullPointerException("This pointer is null");
	}
	
	/**
	 * Method getAsInt
	 *
	 * @param    offset              not used
	 *
	 * @return   nothing
	 *
	 * @exception   NativeException
	 * @exception   NullPointerException allways
	 *
	 */
	
	public int getAsInt(int offset) throws NativeException {
		throw new NullPointerException("This pointer is null");
	}
	
	/**
	 * Method getAsLong
	 *
	 * @param    offset              not used
	 *
	 * @return   nothing
	 *
	 * @exception   NativeException
	 * @exception   NullPointerException allways
	 *
	 */
	
	public long getAsLong(int offset) throws NativeException {
		throw new NullPointerException("This pointer is null");
	}
	
	/**
	 * Method getAsShort
	 *
	 * @param    offset              not used
	 *
	 * @return   nothing
	 *
	 * @exception   NativeException
	 * @exception   NullPointerException allways
	 *
	 */
	
	public short getAsShort(int offset) throws NativeException {
		throw new NullPointerException("This pointer is null");
	}
	
	/**
	 * Method getAsString
	 *
	 * @return   nothing
	 *
	 * @exception   NativeException
	 * @exception   NullPointerException allways
	 *
	 */
	
	public String getAsString() throws NativeException {
		throw new NullPointerException("This pointer is null");
	}
	
	/**
	 * Method getMemory
	 *
	 * @return   nothing
	 *
	 * @exception   NativeException
	 * @exception   NullPointerException allways
	 *
	 */
	
	public byte[] getMemory() throws NativeException {
		throw new NullPointerException("This pointer is null");
	}
	
	/**
	 * Method setByteAt
	 *
	 * @param    offset              not used
	 * @param    value               not used
	 *
	 *
	 * @return   nothing
	 *
	 * @exception   NativeException
	 * @exception   NullPointerException allways
	 *
	 */
	
	public int setByteAt(int offset, byte value) throws NativeException {
		throw new NullPointerException("This pointer is null");
	}
	
	/**
	 * Method setIntAt
	 *
	 *
	 * @param    offset              not used
	 * @param    value               not used
	 *
	 *
	 * @return   nothing
	 *
	 * @exception   NativeException
	 * @exception   NullPointerException allways
	 *
	 *
	 */
	
	public int setIntAt(int offset, int value) throws NativeException {
		throw new NullPointerException("This pointer is null");
	}
	
	/**
	 * Method setLongAt
	 *
	 * @param    offset              not used
	 * @param    value               not used
	 *
	 *
	 * @return   nothing
	 *
	 * @exception   NativeException
	 * @exception   NullPointerException allways
	 *
	 */
	public int setLongAt(int offset, int value) throws NativeException {
		throw new NullPointerException("This pointer is null");
	}
	
	/**
	 * Method setMemory
	 *
	 *
	 * @param    buffer              not used
	 *
	 * @exception   NativeException
	 * @exception   NullPointerException allways
	 *
	 */
	
	public void setMemory(String buffer) throws NativeException {
		throw new NullPointerException("This pointer is null");
	}
	
	/**
	 * Method setMemory
	 *
	 * @param    buffer              not used
	 *
	 * @exception   NativeException
	 * @exception   NullPointerException allways
	 *
	 */
	
	public void setMemory(byte[] buffer) throws NativeException {
		throw new NullPointerException("This pointer is null");
	}
	
	/**
	 * Method setShortAt
	 *
	 *
	 * @param    offset              not used
	 * @param    value               not used
	 *
	 *
	 * @return   nothing
	 *
	 * @exception   NativeException
	 * @exception   NullPointerException allways
	 *
	 */
	
	public int setShortAt(int offset, short value) throws NativeException {
		throw new NullPointerException("This pointer is null");
	}
	
	/**
	 * Method setStringAt
	 *
	 *
	 * @param    offset              not used
	 * @param    value               not used
	 *
	 *
	 * @return   nothing
	 *
	 * @exception   NativeException
	 * @exception   NullPointerException allways
	 *
	 */
	
	public int setStringAt(int offset, String value) throws NativeException {
		throw new NullPointerException("This pointer is null");
	}
	
	/**
	 * Method zeroMemory
	 *
	 *
	 * @exception   NullPointerException allways
	 *
	 */
	
	public void zeroMemory() throws NativeException {
		throw new NullPointerException("This pointer is null");
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
