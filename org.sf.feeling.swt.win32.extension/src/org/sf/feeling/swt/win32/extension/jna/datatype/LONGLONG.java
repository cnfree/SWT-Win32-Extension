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

import org.sf.feeling.swt.win32.extension.jna.Machine;
import org.sf.feeling.swt.win32.extension.jna.exception.NativeException;
import org.sf.feeling.swt.win32.extension.jna.ptr.Pointer;
import org.sf.feeling.swt.win32.extension.jna.ptr.memory.MemoryBlockFactory;

public class LONGLONG extends AbstractBasicData
{

	public LONGLONG( long value )
	{
		super( new Long( value ) );
		try
		{
			createPointer( );
		}
		catch ( NativeException e )
		{
			throw new RuntimeException( e );
		}
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

	/**
	 * Method createPointer
	 * 
	 * @return a MemoryBlock
	 * 
	 */
	public Pointer createPointer( ) throws NativeException
	{
		pointer = new Pointer( MemoryBlockFactory.createMemoryBlock( sizeOf( ) ) );
		pointer.setLongAt( 0, ( (Long) mValue ).longValue( ) );
		return pointer;
	}

	/**
	 * Method getValueFromPointer
	 * 
	 * @return a T
	 * 
	 */
	public Object getValueFromPointer( ) throws NativeException
	{
		mValue = new Long( pointer.getAsLong( 0 ) );
		return mValue;
	}

	public long getValue( )
	{
		try
		{
			return ( (Long) getValueFromPointer( ) ).intValue( );
		}
		catch ( NativeException e )
		{
			e.printStackTrace( );
			throw new RuntimeException( e );
		}
	}
	/**
	 * Method sizeOf
	 * 
	 * @return the size of this structure
	 */
	public static int sizeOf( )
	{
		return Machine.SIZE * 8;
	}
}
