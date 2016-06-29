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
package org.sf.feeling.swt.win32.extension.jna.ptr.memory;
import org.sf.feeling.swt.win32.extension.jna.exception.NativeException;

/**
 * <p>This interface represents a block of native memory</p>
 * This software is released under the LGPL.
 */
public interface MemoryBlock
{
	
	/**
	 * Method getPointer
	 *
	 * @return   the pointer that addresses the memory block
	 * @exception   NullPointerException if the pointer is null
	 *
	 */
	public int getPointer() throws NullPointerException;
	
	/**
	 * Method getSize
	 *
	 * @return   the size of this memory block
	 *
	 * @exception   NullPointerException if the pointer is null
	 *
	 */
	public int getSize() throws NullPointerException;
	
	/**
	 * Method reserveMemory allocate a block of native memory
	 *
	 * @param    size                in bytes of the block
	 *
	 * @return   the address of the reserved memory
	 *
	 * @exception   NativeException
	 *
	 */
	public int reserveMemory(int size) throws NativeException;
	
	/**
	 * Method dispose provides a way to free the memory
	 *
	 * @exception   NativeException
	 *
	 */
	public void dispose() throws NativeException;
	
	
}
