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

import java.util.StringTokenizer;

import org.sf.feeling.swt.win32.extension.jna.datatype.SHORT;
import org.sf.feeling.swt.win32.extension.jna.datatype.win32.DWORD;
import org.sf.feeling.swt.win32.extension.jna.datatype.win32.Structure;
import org.sf.feeling.swt.win32.extension.jna.exception.NativeException;
import org.sf.feeling.swt.win32.extension.jna.ptr.Pointer;
import org.sf.feeling.swt.win32.extension.jna.ptr.memory.MemoryBlockFactory;

public class GUID extends Structure
{

	public static final GUID GUID_IO_MEDIA_ARRIVAL = new GUID( "A5DCBF10-6530-11D2-901F-00C04FB951ED" );

	private DWORD data1 = new DWORD( 0 );
	private SHORT data2 = new SHORT( (short)0 );
	private SHORT data3 = new SHORT( (short)0 );
	private byte[] data4 = new byte[8];

	private String getHexString( int number, int prci )
	{
		String string =  Integer.toHexString( number );
		if ( string.length( ) > prci )
			return string.substring( string.length( ) - prci );
		else if ( string.length( ) < prci )
		{
			int empty = prci - string.length( );
			StringBuffer buffer = new StringBuffer( );
			for ( int i = 0; i < empty; i++ )
			{
				buffer.append( "0" );
			}
			buffer.append( string );
			return buffer.toString( );
		}
		return string;
	}

	public String toString( )
	{
		return new StringBuffer( ).append( getHexString( data1.getValue( ), 8 ) )
				.append( "-" )
				.append( getHexString( data2.getValue( ), 4 ) )
				.append( "-" )
				.append( getHexString( data3.getValue( ), 4 ) )
				.append( "-" )
				.append( getHexString( data4[0], 2 ) )
				.append( getHexString( data4[1], 2 ) )
				.append( "-" )
				.append( getHexString( data4[2], 2 ) )
				.append( getHexString( data4[3], 2 ) )
				.append( getHexString( data4[4], 2 ) )
				.append( getHexString( data4[5], 2 ) )
				.append( getHexString( data4[6], 2 ) )
				.append( getHexString( data4[7], 2 ) )
				.toString( )
				.toUpperCase( );
	}

	public GUID( )
	{
		this( (GUID) null );
	}

	public GUID( String value ) throws NumberFormatException
	{
		super( );
		try
		{
			createPointer( );
			if ( value.matches( "\\{[\\-0-9a-fA-F]+\\}" ) )
			{
				value = value.substring( 1, value.length( ) - 1 );
			}
			StringTokenizer st = new StringTokenizer( value, "-" );
			data1.setValue( (int) Long.parseLong( st.nextToken( ), 16 ) );
			data2.setValue( Short.parseShort( st.nextToken( ), 16 ) );
			data3.setValue( Short.parseShort( st.nextToken( ), 16 ) );
			String l1 = st.nextToken( );
			String l2 = st.nextToken( );
			data4[0] = (byte) Short.parseShort( l1.substring( 0, 2 ), 16 );
			data4[1] = (byte) Short.parseShort( l1.substring( 2 ), 16 );
			for ( int i = 0; i < 6; i++ )
			{
				data4[i + 2] = (byte) Short.parseShort( l2.substring( 2 * i,
						2 * i + 2 ), 16 );
			}
			setValue( this );
		}
		catch ( NativeException e )
		{
			throw new RuntimeException( e );
		}
	}

	/**
	 * Copy constructor (copied by values)
	 * 
	 * @param value
	 */
	public GUID( GUID value )
	{
		super( value );
		try
		{
			createPointer( );
			if ( value != null )
			{
				data1.setValue( value.data1.getValue( ) );
				data2.setValue( value.data2.getValue( ) );
				data3.setValue( value.data3.getValue( ) );
				System.arraycopy( value.data4, 0, data4, 0, 8 );
				setValue( this );
			}
		}
		catch ( NativeException e )
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
		return sizeOf( );
	}

	public static int sizeOf( )
	{
		return 4 + 2 + 2 + 8;
	}

	public Object getValueFromPointer( ) throws NativeException
	{
		data1.setValue( getNextInt( ) );
		data2.setValue(  getNextShort( ) );
		data3.setValue(  getNextShort( ) );
		System.arraycopy( pointer.getMemory( ), offset, data4, 0, 8 );
		return this;
	}

	public GUID getValue( ) throws NativeException
	{
		return (GUID) getValueFromPointer( );
	}

	public void setValue( GUID guid ) throws NativeException
	{
		int offset = 0;
		offset += pointer.setIntAt( offset, guid.data1.getValue( ) );
		offset += pointer.setShortAt( offset, guid.data2.getValue( ) );
		offset += pointer.setShortAt( offset, guid.data3.getValue( ) );
		for ( int i = 0; i < 8; i++ )
		{
			offset += pointer.setByteAt( offset, guid.data4[i] );
		}
	}

	public void setValue( byte[] src, int offset ) throws NativeException
	{
		byte[] buffer = pointer.getMemory( );
		System.arraycopy( src, offset, buffer, 0, 16 );
		pointer.setMemory( buffer );
		getValueFromPointer( );
	}

	public static GUID fromPointer( Pointer pointer ) throws NativeException
	{
		GUID guid = new GUID( );
		guid.pointer = pointer;
		return guid.getValue( );
	}
}
