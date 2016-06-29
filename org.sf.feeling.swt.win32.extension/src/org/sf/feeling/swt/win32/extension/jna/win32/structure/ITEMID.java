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
 * typedef struct _SHITEMID { USHORT cb; BYTE abID[1]; } SHITEMID;
 */
public class ITEMID extends Structure
{

	private int size = 5;

	public ITEMID( )
	{
		super( );
		try
		{
			createPointer( );
		}
		catch ( Exception e )
		{
			throw new RuntimeException( e );
		}
	}

	public Pointer createPointer( ) throws NativeException
	{
		pointer = new Pointer( MemoryBlockFactory.createMemoryBlock( getSizeOf( ) ) );
		return pointer;
	}

	public int getSizeOf( )
	{
		return size;
	}

	public Pointer setData( byte data[] ) throws NativeException
	{
		if ( data != null )
		{
			size = data.length;
			pointer.zeroMemory( );
			createPointer( );
			pointer.setMemory( data );
		}
		return pointer;
	}

	public Object getValueFromPointer( ) throws NativeException
	{
		return this;
	}

	public ITEMID getValue( )
	{
		return this;
	}

	public String getSpecialPath( ) throws NativeException
	{
		return "" + pointer.getAsInt( 0 );
	}

	public int getID( ) throws NativeException
	{
		return pointer.getAsByte( 4 );
	}
}
