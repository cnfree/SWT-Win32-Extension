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

public class INT extends AbstractBasicData
{

	public INT( int value )
	{
		super( new Integer( value ) );
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
	 * Method createPointer
	 * 
	 * @return a MemoryBlock
	 * 
	 */
	public Pointer createPointer( ) throws NativeException
	{
		pointer = new Pointer( MemoryBlockFactory.createMemoryBlock( sizeOf( ) ) );
		pointer.setIntAt( 0, ( (Integer) mValue ).intValue( ) );
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
		mValue = new Integer( pointer.getAsInt( 0 ) );
		return mValue;
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

	public void setValue( int value ) throws NativeException
	{
		mValue = new Integer( value );
		pointer.setIntAt( 0, value );
	}

	public int getValue( )
	{
		try
		{
			return ( (Integer) getValueFromPointer( ) ).intValue( );
		}
		catch ( NativeException e )
		{
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
		return Machine.SIZE * 4;
	}
}
