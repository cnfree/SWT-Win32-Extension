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
import org.sf.feeling.swt.win32.extension.jna.ptr.memory.NativeMemoryBlock;

/**
 * <pre>
 * 
 * typedef struct {
 *   char String[16];
 * } IP_ADDRESS_STRING, *PIP_ADDRESS_STRING, IP_MASK_STRING, *PIP_MASK_STRING;
 *  
 * typedef struct _IP_ADDR_STRING {
 *   struct _IP_ADDR_STRING* Next;			// 4
 *   IP_ADDRESS_STRING IpAddress;			// 16
 *   IP_MASK_STRING IpMask;				// 16 
 *   DWORD Context;						// 4
 * } IP_ADDR_STRING, *PIP_ADDR_STRING;
 * </pre>
 * 
 */
public class IP_ADDR_STRING extends Structure
{

	int next;
	String IpAddress;
	String IpMask;
	int Context;

	protected IP_ADDR_STRING( )
	{
		super(  );
		try
		{
			createPointer( );
		}
		catch ( NativeException e )
		{
			throw new RuntimeException( e );
		}
	}

	private IP_ADDR_STRING( int address )
	{
		super(  );
		pointer = new Pointer( new NativeMemoryBlock( address, getSizeOf( ) ) );
	}

	private IP_ADDR_STRING( Pointer pointer )
	{
		super(  );
		this.pointer = pointer;
	}

	public static IP_ADDR_STRING fromAddress( int address )
			throws NativeException
	{
		return new IP_ADDR_STRING( address ).getValue( );
	}

	public static IP_ADDR_STRING fromPointer( Pointer pointer )
			throws NativeException
	{
		return new IP_ADDR_STRING( pointer ).getValue( );
	}

	public Pointer createPointer( ) throws NativeException
	{
		pointer = new Pointer( MemoryBlockFactory.createMemoryBlock( getSizeOf( ) ) );
		return pointer;
	}

	public int getSizeOf( )
	{
		return 4 + 16 + 16 + 4;
	}

	private String getString( byte[] buffer, int pos, int len )
	{
		String s = new String( buffer, pos, len );
		int pos0 = s.indexOf( '\0' );
		if ( pos != -1 )
			s = s.substring( 0, pos0 );
		return s;
	}

	public Object getValueFromPointer( ) throws NativeException
	{
		next = pointer.getAsInt( 0 );
		byte[] buffer = pointer.getMemory( );
		IpAddress = getString( buffer, 4, 16 );
		IpMask = getString( buffer, 4 + 16, 16 );
		Context = pointer.getAsInt( 4 + 16 + 16 );
		return this;
	}

	public IP_ADDR_STRING getValue( ) throws NativeException
	{
		return (IP_ADDR_STRING) getValueFromPointer( );
	}

	public int getNext( )
	{
		return next;
	}

	public String getIpAddress( )
	{
		return IpAddress;
	}

	public String getIpMask( )
	{
		return IpMask;
	}

	public int getContext( )
	{
		return Context;
	}

}
