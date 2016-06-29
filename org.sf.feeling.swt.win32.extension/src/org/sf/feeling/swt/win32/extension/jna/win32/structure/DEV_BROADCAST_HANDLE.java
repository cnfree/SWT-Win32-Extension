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

import org.sf.feeling.swt.win32.extension.jna.datatype.LONG;
import org.sf.feeling.swt.win32.extension.jna.datatype.win32.DWORD;
import org.sf.feeling.swt.win32.extension.jna.datatype.win32.HANDLE;
import org.sf.feeling.swt.win32.extension.jna.exception.NativeException;
import org.sf.feeling.swt.win32.extension.jna.ptr.Pointer;
import org.sf.feeling.swt.win32.extension.jna.ptr.memory.MemoryBlockFactory;

public class DEV_BROADCAST_HANDLE extends DEV_BROADCAST_HDR
{

	private HANDLE dbch_handle = new HANDLE( 0 );
	private HANDLE dbch_hdevnotify = new HANDLE( 0 );
	private LONG dbch_nameoffset = new LONG( 0 );
	private GUID dbch_eventguid = new GUID( );
	private LONG dbch_data = new LONG( 0 );

	public DEV_BROADCAST_HANDLE( Pointer pointer )
	{
		super( pointer );
		try
		{
			dbch_eventguid = new GUID( );
			getValueFromPointer( );
		}
		catch ( NativeException e )
		{
			throw new RuntimeException( e );
		}
	}

	public DEV_BROADCAST_HANDLE( int DBT_DEVTYP, GUID guid )
	{
		super( );
		dbch_devicetype = new DWORD( DBT_DEVTYP );
		dbch_eventguid = new GUID( guid );
		try
		{
			createPointer( );
			int offset = 0;
			offset += pointer.setIntAt( offset, dbch_size.getValue( ) );
			offset += pointer.setIntAt( offset, dbch_devicetype.getValue( ) );
			offset += pointer.setIntAt( offset, dbch_reserved.getValue( ) );
			offset += pointer.setIntAt( offset, dbch_handle.getValue( ) );
			offset += pointer.setIntAt( offset, dbch_hdevnotify.getValue( ) );
			Pointer pointerOrg = dbch_eventguid.getPointer( );
			for ( int i = 0; i < 16; i++ )
			{
				byte asByte = pointerOrg.getAsByte( i );
				pointer.setByteAt( offset++, asByte );
			}
			offset += pointer.setIntAt( offset, dbch_nameoffset.getValue( ) );
			offset += pointer.setByteAt( offset,
					( (Integer) dbch_data.getValueFromPointer( ) ).byteValue( ) );
		}
		catch ( NativeException e )
		{
			e.printStackTrace( );
		}
	}

	public Pointer createPointer( ) throws NativeException
	{
		pointer = new Pointer( MemoryBlockFactory.createMemoryBlock( getSizeOf( ) ) );
		return pointer;
	}

	public static int getSize( )
	{
		return 6 * 4 + 16 + 1;
	}

	public int getSizeOf( )
	{
		return dbch_size.getValue( );
	}

	public Object getValueFromPointer( ) throws NativeException
	{
		offset = 0;
		super.getValueFromPointer( );
		dbch_handle.setValue( getNextInt( ) );
		dbch_hdevnotify.setValue( getNextInt( ) );
		dbch_eventguid.setValue( pointer.getMemory( ), offset );
		offset += 16;
		dbch_nameoffset.setValue( getNextInt( ) );
		dbch_data.setValue( getNextByte( ) );
		return this;
	}

	public DEV_BROADCAST_HANDLE getValue( ) throws NativeException
	{
		return (DEV_BROADCAST_HANDLE)getValueFromPointer();
	}

	public HANDLE getDbch_handle( )
	{
		return dbch_handle;
	}

	public HANDLE getDbch_hdevnotify( )
	{
		return dbch_hdevnotify;
	}

	public GUID getDbch_eventguid( )
	{
		return dbch_eventguid;
	}

	public LONG getDbch_nameoffset( )
	{
		return dbch_nameoffset;
	}

	public LONG getDbch_data( )
	{
		return dbch_data;
	}

}
