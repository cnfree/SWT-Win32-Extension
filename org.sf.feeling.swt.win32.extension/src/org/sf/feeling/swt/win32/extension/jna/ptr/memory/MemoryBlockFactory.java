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

package org.sf.feeling.swt.win32.extension.jna.ptr.memory;

import java.lang.reflect.Constructor;

import org.sf.feeling.swt.win32.extension.jna.exception.NativeException;

/**
 * 
 * <p>
 * This factory permits to reserve a block of memory of the default type
 * </p>
 * <p>
 * <b>HeapMemoryBlock</b> : is currently the default type.
 * </p>
 * <p>
 * You should always call <code>setPreferredMemoryType</code> before, default
 * type is subject to change !!
 * </p>
 * 
 */
public class MemoryBlockFactory
{

	private MemoryBlockFactory( )
	{
	}

	private static Constructor preferredConstructor;

	public static void setPreferredMemoryType( Class type )
			throws NoSuchMethodException
	{
		try
		{
			preferredConstructor = type.getDeclaredConstructor( new Class[]{
				int.class
			} );
			MemoryBlock mem = (MemoryBlock) preferredConstructor.newInstance( new Object[]{
				new Integer( 1 )
			} );
			mem.dispose( );
		}
		catch ( Exception e )
		{
			try
			{
				e.printStackTrace( );
				throw new NoSuchMethodException( type.toString( )
						+ " not found" );
			}
			catch ( NullPointerException ex )
			{
				throw new NoSuchMethodException( "Class not found" );
			}
		}
	}

	public static MemoryBlock createMemoryBlock( int size )
			throws NativeException
	{
		try
		{
			if ( preferredConstructor == null )
			{
				setPreferredMemoryType( HeapMemoryBlock.class );
			}
			return (MemoryBlock) preferredConstructor.newInstance( new Object[]{
				new Integer( size )
			} );
		}
		catch ( Exception e )
		{
			e.printStackTrace( );
			throw new NativeException( e.toString( ) );
		}
	}
}
