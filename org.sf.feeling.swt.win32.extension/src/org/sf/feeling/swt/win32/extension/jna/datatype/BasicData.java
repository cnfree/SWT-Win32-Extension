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

package org.sf.feeling.swt.win32.extension.jna.datatype;

import org.sf.feeling.swt.win32.extension.jna.exception.NativeException;
import org.sf.feeling.swt.win32.extension.jna.ptr.Pointer;

/**
 * <p>
 * Instances of BasicData are specialized blocks of memory
 * </p>
 * <p>
 * They are values, memory, pointers to the memory
 * </p>
 */
public interface BasicData
{

	/**
	 * Method getValueFromPointer gets the value of this data from the native
	 * memory block
	 * 
	 * @return a T
	 * 
	 * @exception NativeException
	 * 
	 */
	public Object getValueFromPointer( ) throws NativeException;

	/**
	 * Method getPointer gets the Pointer created by createPointer()
	 * 
	 * @return a Pointer the pointer or null if createPointer() was not called
	 * 
	 */
	public Pointer getPointer( );

	/**
	 * Method getSizeOf
	 * 
	 * @return the size of this data
	 * 
	 */
	public int getSizeOf( );

	/**
	 * Method createPointer reserves a native MemoryBlock and copy its value in
	 * it
	 * 
	 * @return a Pointer on the reserved memory
	 * 
	 * @exception NativeException
	 * 
	 */
	public Pointer createPointer( ) throws NativeException;
}
