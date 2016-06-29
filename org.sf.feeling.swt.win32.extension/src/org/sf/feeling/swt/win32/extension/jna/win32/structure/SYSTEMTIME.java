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

import org.sf.feeling.swt.win32.extension.jna.datatype.AbstractBasicData;
import org.sf.feeling.swt.win32.extension.jna.exception.NativeException;
import org.sf.feeling.swt.win32.extension.jna.ptr.Pointer;
import org.sf.feeling.swt.win32.extension.jna.ptr.memory.MemoryBlockFactory;

/**
 * SystemTime
 * 
 * 
 * <pre>
 * 	typedef struct _SYSTEMTIME {
 * 		  WORD wYear;
 * 		  WORD wMonth;
 * 		  WORD wDayOfWeek;
 * 		  WORD wDay;
 * 		  WORD wHour;
 * 		  WORD wMinute;
 * 		  WORD wSecond;
 * 		  WORD wMilliseconds;
 * 		} SYSTEMTIME,
 * </pre>
 */
public class SYSTEMTIME extends AbstractBasicData
{

	public short wYear;
	public short wMonth;
	public short wDayOfWeek;
	public short wDay;
	public short wHour;
	public short wMinute;
	public short wSecond;
	public short wMilliseconds;

	public Pointer createPointer( ) throws NativeException
	{
		pointer = new Pointer( MemoryBlockFactory.createMemoryBlock( getSizeOf( ) ) );
		return pointer;
	}

	public int getSizeOf( )
	{
		return 8 * 2; // 8 WORDS of 2 bytes
	}

	public Object getValueFromPointer( ) throws NativeException
	{
		wYear = getNextShort( );
		wMonth = getNextShort( );
		wDayOfWeek = getNextShort( );
		wDay = getNextShort( );
		wHour = getNextShort( );
		wMinute = getNextShort( );
		wSecond = getNextShort( );
		wMilliseconds = getNextShort( );
		return this;
	}

	public void setValueToPointer( ) throws NativeException
	{
		offset = 0;
		setNextShort( wYear );
		setNextShort( wMonth );
		setNextShort( wDayOfWeek );
		setNextShort( wDay );
		setNextShort( wHour );
		setNextShort( wMinute );
		setNextShort( wSecond );
		setNextShort( wMilliseconds );
	}

	public SYSTEMTIME getValue( ) throws NativeException
	{
		return ( (SYSTEMTIME) getValueFromPointer( ) );
	}

	public SYSTEMTIME( ) throws NativeException
	{
		super( null );
		createPointer( );
		mValue = this;
	}

	public String toString( )
	{
		return wYear
				+ "/"
				+ wMonth
				+ "/"
				+ wDay
				+ " at "
				+ wHour
				+ ":"
				+ wMinute
				+ ":"
				+ wSecond
				+ ":"
				+ wMilliseconds;
	}
}
