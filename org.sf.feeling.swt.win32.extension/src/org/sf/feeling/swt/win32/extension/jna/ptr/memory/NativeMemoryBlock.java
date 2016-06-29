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

 *
 * <p>This class represents a memory block known by its pointer</p>
 *
 */
import org.sf.feeling.swt.win32.extension.jna.exception.NativeException;

public class NativeMemoryBlock extends AbstractMemoryBlock
{
	
	public NativeMemoryBlock(int address, int size)
	{
		super(size);
		setPointer(address);
	}
	
	/**
	 * Method reserveMemory allocate a block of native memory
	 * @param    size                in bytes of the block
	 * @return   the address of the reserved memory
	 * @exception   NativeException
	 */
	public int reserveMemory(int size)
	{
		return getPointer();
	}
	
	/**
	 * Method dispose provides a way to free the memory
	 * <p>This implementation does nothing as we do not know how this memory block was allocated</p>
	 * @exception   NativeException
	 */
	public void dispose()
	{
	}
	
}
