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
/**

 *
 * <p>LONG is an implementation of the C float data,</p>
 * <p>To get a LPFLOAT call createPointer() or after getPointer().</p>
 * <p>To retreive the value pointed by this object call getValueFromPointer()</p>
 * <br>
 * <br>This file was submitted as a patch by Jim (jjones7947)
 */
import org.sf.feeling.swt.win32.extension.jna.Machine;
import org.sf.feeling.swt.win32.extension.jna.exception.NativeException;
import org.sf.feeling.swt.win32.extension.jna.ptr.Pointer;
import org.sf.feeling.swt.win32.extension.jna.ptr.memory.MemoryBlockFactory;

public class FLOAT extends AbstractBasicData
{

	public FLOAT( float value )
	{
		super( new Float( value ) );
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
		pointer.setFloatAt( 0, ( (Float) mValue ).floatValue( ) );
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
		mValue = new Float( pointer.getAsFloat( 0 ) );
		return mValue;
	}

	public void setValue( float lValue ) throws NativeException
	{
		mValue = new Float( lValue );
		pointer.setFloatAt( 0, lValue );
	}

	public float getValue( )
	{
		try
		{
			return ( (Float) getValueFromPointer( ) ).floatValue( );
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
