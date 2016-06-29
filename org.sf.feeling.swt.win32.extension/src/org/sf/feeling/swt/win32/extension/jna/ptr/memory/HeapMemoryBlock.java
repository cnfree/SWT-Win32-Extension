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

/**
 * <p><b>Win32 : </b>HeapMemoryBlock is a block of memory reserved from the heap
 * with the function : HeapAlloc (see MSDN)
 * <br> This allocation is the fastest du to its implementation. Seems to hang with some DLL.
 * </p>
 * <p><b>Linux : </b>Not implemented yet</p>
 * <br>
 */
import org.sf.feeling.swt.win32.extension.jna.Native;
import org.sf.feeling.swt.win32.extension.jna.exception.NativeException;

public class HeapMemoryBlock extends AbstractMemoryBlock
{
	public HeapMemoryBlock(int size) throws NativeException
	{
		super(size);
		reserveMemory(size);
	}
	
	/**
	 * Method reserveMemory allocate a block of native memory
	 * @param    size                in bytes of the block
	 * @return   the address of the reserved memory
	 * @exception   NativeException
	 */
	public int reserveMemory(int size) throws NativeException
	{
		setSize(size);
		if (getPointer() != 0)
		{
			dispose();
		}
		setPointer(Native.allocMemory(size));
		return getPointer();
	}
	
	/**
	 * Method dispose provides a way to free the memory
	 * @exception   NativeException
	 */
	public void dispose() throws NativeException
	{
		if (getPointer() != 0)
		{
			Native.freeMemory(getPointer());
			setPointer(0);
		}
	}
	
}
