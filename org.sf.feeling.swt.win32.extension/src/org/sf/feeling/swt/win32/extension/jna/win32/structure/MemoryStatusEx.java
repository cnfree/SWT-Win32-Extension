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

package org.sf.feeling.swt.win32.extension.jna.win32.structure;

/**
 * <pre>
 * typedef struct _MEMORYSTATUSEX {
 DWORD dwLength;
 DWORD dwMemoryLoad;
 DWORDLONG ullTotalPhys;
 DWORDLONG ullAvailPhys;
 DWORDLONG ullTotalPageFile;
 DWORDLONG ullAvailPageFile;
 DWORDLONG ullTotalVirtual;
 DWORDLONG ullAvailVirtual;
 DWORDLONG ullAvailExtendedVirtual;
 } MEMORYSTATUSEX,
 *LPMEMORYSTATUSEX;
 * </pre>

 */
import org.sf.feeling.swt.win32.extension.jna.datatype.win32.Structure;
import org.sf.feeling.swt.win32.extension.jna.exception.NativeException;
import org.sf.feeling.swt.win32.extension.jna.ptr.Pointer;
import org.sf.feeling.swt.win32.extension.jna.ptr.memory.GlobalMemoryBlock;

public class MemoryStatusEx extends Structure
{

	public int dwLength;
	public int dwMemoryLoad;
	public long ullTotalPhys;
	public long ullAvailPhys;
	public long ullTotalPageFile;
	public long ullAvailPageFile;
	public long ullTotalVirtual;
	public long ullAvailVirtual;
	public long ullAvailExtendedVirtual;

	public MemoryStatusEx( )
	{
		super( );
	}

	/**
	 * Method getValueFromPointer
	 * 
	 * @return a T
	 * 
	 * @exception NativeException
	 * 
	 */
	public Object getValueFromPointer( ) throws NativeException
	{
		offset = 0;
		dwLength = getNextInt( );
		dwMemoryLoad = getNextInt( );
		ullTotalPhys = getNextLong( );
		ullAvailPhys = getNextLong( );
		ullTotalPageFile = getNextLong( );
		ullAvailPageFile = getNextLong( );
		ullTotalVirtual = getNextLong( );
		ullAvailVirtual = getNextLong( );
		ullAvailExtendedVirtual = getNextLong( );
		return this;
	}

	public MemoryStatusEx getValue( ) throws NativeException
	{
		return (MemoryStatusEx) getValueFromPointer( );
	}

	/**
	 * Method getSizeOf
	 * 
	 * @return the size of this data
	 */
	public int getSizeOf( )
	{
		return sizeOf( );
	}

	public static int sizeOf( )
	{
		return 2 * 4 + 7 * 8;
	}

	/**
	 * Method createPointer reserves a native MemoryBlock and copy its value in
	 * it
	 * 
	 * @return a Pointer on the reserved memory
	 * @exception NativeException
	 */
	public Pointer createPointer( ) throws NativeException
	{
		pointer = new Pointer( new GlobalMemoryBlock( sizeOf( ) ) );
		pointer.setIntAt( 0, sizeOf( ) );
		return pointer;
	}

	public String toString( )
	{
		return "dwLength : "
				+ dwLength
				+ "\ndwMemoryLoad : "
				+ dwMemoryLoad
				+ "\nullTotalPhys : "
				+ ullTotalPhys
				+ "\nullAvailPhys : "
				+ ullAvailPhys
				+ "\nullTotalPageFile : "
				+ ullTotalPageFile
				+ "\nullAvailPageFile : "
				+ ullAvailPageFile
				+ "\nullTotalVirtual : "
				+ ullTotalVirtual
				+ "\nullAvailVirtual : "
				+ ullAvailVirtual
				+ "\nullAvailExtendedVirtual : "
				+ ullAvailExtendedVirtual;
	}
}
