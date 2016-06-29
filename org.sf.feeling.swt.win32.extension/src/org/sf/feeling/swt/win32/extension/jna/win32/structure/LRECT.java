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

import org.eclipse.swt.graphics.Rectangle;
import org.sf.feeling.swt.win32.extension.jna.Machine;
import org.sf.feeling.swt.win32.extension.jna.datatype.win32.Structure;
import org.sf.feeling.swt.win32.extension.jna.exception.NativeException;
import org.sf.feeling.swt.win32.extension.jna.ptr.Pointer;
import org.sf.feeling.swt.win32.extension.jna.ptr.memory.MemoryBlockFactory;

public class LRECT extends Structure
{

	/** Creates a new instance of LRECT */
	public LRECT( )
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
	}

	/** Creates a new instance of LRECT from a Java rectangle */
	public LRECT( Rectangle rectangle )
	{
		this( );
		try
		{
			setTop( (int) rectangle.y );
			setLeft( (int) rectangle.x );
			setRight( (int) ( rectangle.x + rectangle.width ) );
			setBottom( (int) ( rectangle.y + rectangle.height ) );
		}
		catch ( NativeException e )
		{
			e.printStackTrace( );
			throw new RuntimeException( e );
		}
	}

	public int getWidth( ) throws NativeException
	{
		return getRight( ) - getLeft( );
	}

	public int getHeight( ) throws NativeException
	{
		return getBottom( ) - getTop( );
	}

	public int getLeft( ) throws NativeException
	{
		return pointer.getAsInt( 0 );
	}

	public int getRight( ) throws NativeException
	{
		return pointer.getAsInt( 8 );
	}

	public int getTop( ) throws NativeException
	{
		return pointer.getAsInt( 4 );
	}

	public int getBottom( ) throws NativeException
	{
		return pointer.getAsInt( 12 );
	}

	public void setLeft( int value ) throws NativeException
	{
		pointer.setIntAt( 0, value );
	}

	public void setRight( int value ) throws NativeException
	{
		pointer.setIntAt( 8, value );
	}

	public void setTop( int value ) throws NativeException
	{
		pointer.setIntAt( 4, value );
	}

	public void setBottom( int value ) throws NativeException
	{
		pointer.setIntAt( 12, value );
	}

	public Pointer createPointer( ) throws NativeException
	{
		pointer = new Pointer( MemoryBlockFactory.createMemoryBlock( sizeOf( ) ) );
		return pointer;
	}

	public Object getValueFromPointer( ) throws NativeException
	{
		return this;
	}

	public LRECT getValueValue( ) throws NativeException
	{
		return this;
	}

	public int getSizeOf( )
	{
		return sizeOf( );
	}

	public static int sizeOf( )
	{
		return Machine.SIZE * 16;
	}

}
