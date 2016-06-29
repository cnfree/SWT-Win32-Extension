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

import java.util.ArrayList;
import java.util.List;

import org.sf.feeling.swt.win32.extension.jna.datatype.UINT;
import org.sf.feeling.swt.win32.extension.jna.datatype.win32.DWORD;
import org.sf.feeling.swt.win32.extension.jna.exception.NativeException;
import org.sf.feeling.swt.win32.extension.jna.ptr.Pointer;

public class DEV_BROADCAST_VOLUME extends DEV_BROADCAST_HDR
{

	/**
	 * The logical unit mask identifying one or more logical units. Each bit in
	 * the mask corresponds to one logical drive. Bit 0 represents drive A, bit
	 * 1 represents drive B, and so on.
	 */
	private DWORD dbcv_unitmask = new DWORD( 0 );

	/** This parameter can be one of the following values. */
	private UINT dbcv_flags = new UINT( 0 );

	public DEV_BROADCAST_VOLUME( )
	{
	}

	public DEV_BROADCAST_VOLUME( Pointer ptr )
	{
		super( ptr );
		try
		{
			getValueFromPointer( );
		}
		catch ( NativeException e )
		{
			throw new RuntimeException( e );
		}
	}

	public Object getValueFromPointer( ) throws NativeException
	{
		super.getValueFromPointer( );
		dbcv_unitmask = new DWORD( getNextInt( ) );
		dbcv_flags = new UINT( getNextShort( ) );
		return this;
	}

	public DEV_BROADCAST_VOLUME getValue( ) throws NativeException
	{
		return (DEV_BROADCAST_VOLUME)getValueFromPointer();
	}


	/**
	 * The logical unit mask identifying one or more logical units. Each bit in
	 * the mask corresponds to one logical drive. Bit 0 represents drive A, bit
	 * 1 represents drive B, and so on.
	 */
	public DWORD getDbcv_unitmask( )
	{
		return dbcv_unitmask;
	}

	/** This parameter can be one of the following values. */
	public UINT getDbcv_flags( )
	{
		return dbcv_flags;
	}

	public String[] getVolumes( )
	{
		char unit = 'A';
		List volumes = new ArrayList( );
		int bitMask = 1;
		int value = getDbcv_unitmask( ).getValue( );
		for ( int i = 0; i < 32; i++ )
		{
			if ( ( bitMask & value ) > 0 )
			{
				volumes.add( "" + (char) ( unit + i ) );
			}
			bitMask = bitMask << 1;
		}
		return (String[]) volumes.toArray( new String[0] );
	}

}
