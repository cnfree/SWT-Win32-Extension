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

import org.sf.feeling.swt.win32.extension.jna.datatype.win32.Structure;
import org.sf.feeling.swt.win32.extension.jna.exception.NativeException;
import org.sf.feeling.swt.win32.extension.jna.ptr.Pointer;
import org.sf.feeling.swt.win32.extension.jna.ptr.memory.MemoryBlockFactory;

/**
 * FILETIME
 * 
 * Contains a 64-bit value representing the number of 100-nanosecond intervals
 * since January 1, 1601 (UTC).
 * 
 * typedef struct _FILETIME { DWORD dwLowDateTime; DWORD dwHighDateTime; }
 * FILETIME, PFILETIME;
 * 
 * Members
 * 
 * dwLowDateTime The low-order part of the file time. dwHighDateTime The
 * high-order part of the file time.
 * 
 * Remarks
 * 
 * To convert a FILETIME structure into a time that is easy to display to a
 * user, use the FileTimeToSystemTime function.
 * 
 * It is not recommended that you add and subtract values from the FILETIME
 * structure to obtain relative times. Instead, you should
 * 
 * Copy the resulting FILETIME structure to a ULARGE_INTEGER structure using
 * memcpy (using memcpy instead of direct assignment can prevent alignment
 * faults on 64-bit Windows). Use normal 64-bit arithmetic on the ULARGE_INTEGER
 * value.
 * 
 * Not all file systems can record creation and last access time and not all
 * file systems record them in the same manner. For example, on NT FAT, create
 * time has a resolution of 10 milliseconds, write time has a resolution of 2
 * seconds, and access time has a resolution of 1 day (really, the access date).
 * On NTFS, access time has a resolution of 1 hour. Therefore, the GetFileTime
 * function may not return the same file time information set using the
 * SetFileTime function. Furthermore, FAT records times on disk in local time.
 * However, NTFS records times on disk in UTC. For more information, see File
 * Times.
 * 
 * 
 */

public class FILETIME extends Structure
{

	public FILETIME( )
	{
		super(  );
		try
		{
			createPointer( );
			mValue = this;
		}
		catch ( NativeException e )
		{
			throw new RuntimeException( e );
		}
	}

	public int getLowDateTime( ) throws NativeException
	{
		offset = 0;
		return getNextInt( );
	}

	public int getHighDateTime( ) throws NativeException
	{
		offset = 4;
		return getNextInt( );
	}

	public Object getValueFromPointer( )
	{
		return this;
	}

	public FILETIME getValue( )
	{
		return this;
	}

	public int getSizeOf( )
	{
		return 8;
	}

	public Pointer createPointer( ) throws NativeException
	{
		pointer = new Pointer( MemoryBlockFactory.createMemoryBlock( getSizeOf( ) ) );
		return pointer;
	}

	public String toString( )
	{
		try
		{
			return getLowDateTime( ) + "/" + getHighDateTime( );
		}
		catch ( NativeException e )
		{
			e.printStackTrace( );
			return e.toString( );
		}
	}

}
