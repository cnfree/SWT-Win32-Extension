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
 * <p>New Memory blocks should extends this class</p>
 */
public abstract class AbstractMemoryBlock implements MemoryBlock {
	
	private int pointer;
	private int size;
	
	AbstractMemoryBlock(int size) {
		this.size = size;
	}
	
	
	/**
	 * Method getSize
	 * @return   the size of this memory block
	 * @exception   NullPointerException if the pointer is null
	 */
	public final int getSize() throws NullPointerException {
		return size;
	}
	
	
	/**
	 * Method getPointer
	 * @return   the pointer that addresses the memory block
	 */
	public final int getPointer() throws NullPointerException {
		return pointer;
	}
	
	protected void setPointer(int pointer) {
		if(pointer == 0)
			size = 0;
		this.pointer = pointer;
	}
	
	protected void setSize(int size) {
		this.size = size;
	}
	
}
