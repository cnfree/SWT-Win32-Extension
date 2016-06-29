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
 * 
 * <pre>
 * Structure C
 * typedef struct tagPOINT {
 * &nbsp;	LONG  x;
 * &nbsp;	LONG  y;
 * } POINT, *PPOINT, NEAR *NPPOINT, FAR *LPPOINT;
 * 
 * </pre>
 */
public class POINT extends Structure
{

	protected int x, y;

	public POINT( )
	{
		this( 0, 0 );
	}

	public POINT( int x, int y )
	{
		super( );
		try
		{
			createPointer( );
		}
		catch ( NativeException e )
		{
			throw new RuntimeException( e );
		}
		this.x = x;
		this.y = y;
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
		pointer = new Pointer( MemoryBlockFactory.createMemoryBlock( sizeOf( ) ) );
		return pointer;
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
		x = pointer.getAsInt( 0 );
		y = pointer.getAsInt( 4 );
		return this;
	}

	/**
	 * Returns X, you must call getValueFromPointer before reading this value
	 * !!!
	 * 
	 * @return an int
	 */
	public int getX( )
	{
		return x;
	}

	/**
	 * Returns Y, you must call getValueFromPointer before reading this value
	 * !!!
	 * 
	 * @return an int
	 */
	public int getY( )
	{
		return y;
	}

	public void setX( int x ) throws NativeException
	{
		this.x = x;
		pointer.setIntAt( 0, x );
	}

	public void setY( int y ) throws NativeException
	{
		this.y = y;
		pointer.setIntAt( 4, y );
	}

	/**
	 * Method getValue
	 * 
	 * @return a T
	 * 
	 */
	public POINT getValue( )
	{
		try
		{
			pointer.setIntAt( 0, x );
			pointer.setIntAt( 4, y );
		}
		catch ( NativeException ex )
		{
			ex.printStackTrace( );
		}
		return this;
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
		return 8;
	}

}
