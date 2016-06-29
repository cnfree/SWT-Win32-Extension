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
import org.sf.feeling.swt.win32.extension.jna.ptr.Pointer;
import org.sf.feeling.swt.win32.extension.jna.win32.Kernel32;

public class GlobalMemoryBlock extends AbstractMemoryBlock
{
	public static final int GMEM_FIXED = 0;
	public static final int GMEM_ZEROINIT = 0x40;
	public static final int GPTR = GMEM_FIXED | GMEM_ZEROINIT;
	public static final int GMEM_MOVEABLE = 0x2;
	public static final int GHND = GMEM_MOVEABLE | GMEM_ZEROINIT;
	
	/*
	final static Native globalAlloc;
	final static Native globalFree;
	static {
		try {
			globalAlloc = new Native("Kernel32.dll", "GlobalAlloc");
			globalAlloc.setRetVal(Type.INT);
			globalFree = new Native("Kernel32.dll", "GlobalFree");
			globalFree.setRetVal(Type.INT);
		}
		catch(Exception e) {
			throw new IllegalStateException(e);
		}
	}
	 **/
	
	private int type;
	
	public GlobalMemoryBlock(int size) throws NativeException
	{
		this(size, GPTR);
	}
	
	public GlobalMemoryBlock(int size, int type) throws NativeException
	{
		super(size);
		this.type = type;
		reserveMemory(size);
	}
	
	/**
	 * Method reserveMemory allocate a block of native memory
	 * @param    size                in bytes of the block
	 * @return   a MemoryBlock representing the reserved memory
	 * @exception   NativeException
	 */
	public int reserveMemory(int size) throws NativeException
	{
		setSize(size);
		if(getPointer() != 0)
		{
			dispose();
		}
		/*
		try {
			globalAlloc.setParameter(0, Type.INT, "" + type);
			globalAlloc.setParameter(1, Type.INT, "" + size);
			globalAlloc.invoke();
			setPointer(Integer.parseInt(globalAlloc.getRetVal()));
		}
		catch(IllegalAccessException e) {
			throw new IllegalStateException(e);
		}
		 **/
		try
		{
			setPointer(Kernel32.GlobalAlloc(size, type));
		}
		catch(IllegalAccessException e)
		{
			throw new IllegalStateException(e.getLocalizedMessage( ));
		}
		return getPointer();
	}
	
	/**
	 * Method dispose provides a way to free the memory
	 * @exception   NativeException
	 */
	public void dispose() throws NativeException
	{
		/*
		if(getPointer() != null) {
			try {
				globalFree.setParameter(0, Type.INT, getPointer().toString());
				globalFree.invoke();
				if(Integer.parseInt(globalFree.getRetVal()) != 0) {
					throw new NativeException("Memory not freed !");
				}
				setPointer(null);
			}
			catch(IllegalAccessException e) {
				throw new IllegalStateException(e);
			}
		}
		 */
		try
		{
			if(getPointer() != 0)
			{
				if(Kernel32.GlobalFree(getPointer()) != 0)
				{
					throw new NativeException("Memory not freed !");
				}
				setPointer(0);
			}
		}
		catch(IllegalAccessException e)
		{
			throw new IllegalStateException(e.getLocalizedMessage( ));
		}
	}
	public int globalLock() throws NativeException, IllegalAccessException
	{
		return Kernel32.GlobalLock(getPointer());
	}
	public boolean globalUnlock() throws NativeException, IllegalAccessException
	{
		return Kernel32.GlobalUnlock(getPointer());
	}
	public boolean copyMemory(Pointer fromPointer) throws NativeException, IllegalAccessException
	{
		int destPointer = globalLock();
		if(destPointer != 0)
		{
			Kernel32.RtlMoveMemory(destPointer, fromPointer.getPointer(), fromPointer.getSize());
		}
		return (destPointer != 0 && globalUnlock());
	}
	public boolean copyMemory(Pointer fromPointer, int size) throws NativeException, IllegalAccessException
	{
		int destPointer = globalLock();
		if(destPointer != 0)
		{
			Kernel32.RtlMoveMemory(destPointer, fromPointer.getPointer(), size);
		}
		return (destPointer != 0 && globalUnlock());
	}
}
