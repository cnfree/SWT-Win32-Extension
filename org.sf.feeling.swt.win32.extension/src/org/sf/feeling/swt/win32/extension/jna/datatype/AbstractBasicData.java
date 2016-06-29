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

public abstract class AbstractBasicData implements BasicData
{

	protected Object mValue;
	protected Pointer pointer;
	protected int offset;

	protected byte getNextByte( ) throws NativeException
	{
		return pointer.getAsByte( offset++ );
	}

	protected void setNextByte( byte data ) throws NativeException
	{
		pointer.setByteAt( offset++, data );
	}

	protected short getNextShort( ) throws NativeException
	{
		try
		{
			return pointer.getAsShort( offset );
		}
		finally
		{
			offset += 2;
		}
	}

	protected void setNextShort( short data ) throws NativeException
	{
		try
		{
			pointer.setShortAt( offset, data );
		}
		finally
		{
			offset += 2;
		}
	}

	protected int getNextInt( ) throws NativeException
	{
		try
		{
			return pointer.getAsInt( offset );
		}
		finally
		{
			offset += 4;
		}
	}
	
	protected void setNextInt( int data ) throws NativeException
	{
		try
		{
			pointer.setIntAt( offset, data );
		}
		finally
		{
			offset += 4;
		}
	}

	protected long getNextLong( ) throws NativeException
	{
		try
		{
			return pointer.getAsLong( offset );
		}
		finally
		{
			offset += 8;
		}
	}
	
	protected void setNextLong( long data ) throws NativeException
	{
		try
		{
			pointer.setLongAt( offset, data );
		}
		finally
		{
			offset += 8;
		}
	}

	/**
	 * Method getPointer
	 * 
	 * @return a Pointer
	 * 
	 */
	public Pointer getPointer( )
	{
		return pointer;
	}

	protected AbstractBasicData( Object lValue )
	{
		mValue = lValue;
	}

	public String getValueAsString( )
	{
		if ( mValue == null )
			return null;
		else
			return mValue.toString( );
	}

	public String toString( )
	{
		if ( mValue == null )
		{
			return "NULL";
		}
		return mValue.toString( );
	}
}
