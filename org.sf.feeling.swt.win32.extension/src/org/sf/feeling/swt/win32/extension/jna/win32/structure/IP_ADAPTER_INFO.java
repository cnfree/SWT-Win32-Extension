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

import java.util.LinkedList;
import java.util.List;

import org.sf.feeling.swt.win32.extension.jna.datatype.AbstractBasicData;
import org.sf.feeling.swt.win32.extension.jna.datatype.win32.DWORD;
import org.sf.feeling.swt.win32.extension.jna.exception.NativeException;
import org.sf.feeling.swt.win32.extension.jna.ptr.NullPointer;
import org.sf.feeling.swt.win32.extension.jna.ptr.Pointer;
import org.sf.feeling.swt.win32.extension.jna.ptr.memory.MemoryBlockFactory;
import org.sf.feeling.swt.win32.extension.jna.ptr.memory.NativeMemoryBlock;

/**
 * <pre>
 * typedef struct _IP_ADAPTER_INFO {
 *   struct _IP_ADAPTER_INFO* Next;                        // 4
 *   DWORD ComboIndex;										// 4
 *   char AdapterName[MAX_ADAPTER_NAME_LENGTH+4];        	// 256 +4 = 260
 *   char Description[MAX_ADAPTER_DESCRIPTION_LENGTH+4];  	// 128 +4 = 132 
 *   UINT AddressLength; 									// 4
 *   BYTE Address[MAX_ADAPTER_ADDRESS_LENGTH]; 			// 8 
 *   DWORD Index;											// 4
 *   UINT Type; 											// 4
 *   UINT DhcpEnabled; 									// 4 
 *   PIP_ADDR_STRING CurrentIpAddress; 					// 4
 *   IP_ADDR_STRING IpAddressList; 						// 40
 *   IP_ADDR_STRING GatewayList;							// 40
 *   IP_ADDR_STRING DhcpServer;							// 40
 *   BOOL HaveWins;										// 2
 *   IP_ADDR_STRING PrimaryWinsServer;						// 40
 *   IP_ADDR_STRING SecondaryWinsServer;					// 40
 *   time_t LeaseObtained; 								// 4
 *   time_t LeaseExpires; 									// 4
 * } IP_ADAPTER_INFO, *PIP_ADAPTER_INFO;
 * 
 * </pre>
 * 
 */
public class IP_ADAPTER_INFO extends AbstractBasicData
{

	public static final int MAX_ADAPTER_ADDRESS_LENGTH = 8;
	public static final int MAX_ADAPTER_DESCRIPTION_LENGTH = 128;
	public static final int MAX_ADAPTER_NAME_LENGTH = 256;

	private int Next; // 4
	private DWORD ComboIndex = new DWORD( 0 ); // 4
	private String AdapterName; // 256 +4 = 260
	private String Description; // 128 +4 = 132
	private int AddressLength; // 4
	private byte[] Address = new byte[MAX_ADAPTER_ADDRESS_LENGTH]; // 8
	private DWORD Index = new DWORD( 0 ); // 4
	private int Type; // 4
	private int DhcpEnabled; // 4
	private final Pointer CurrentIpAddress = NullPointer.NULL; // 4
	private IP_ADDR_STRING IpAddressList; // 40
	private IP_ADDR_STRING GatewayList; // 40
	private IP_ADDR_STRING DhcpServer; // 40
	private boolean HaveWins; // 4
	private IP_ADDR_STRING PrimaryWinsServer; // 40
	private IP_ADDR_STRING SecondaryWinsServer; // 40
	private TIME_T LeaseObtained = new TIME_T( 0 ); // 4
	private TIME_T LeaseExpires = new TIME_T( 0 ); // 4

	public IP_ADAPTER_INFO( )
	{
		super( null );
		try
		{
			createPointer( );
		}
		catch ( NativeException e )
		{
			throw new RuntimeException( e );
		}
	}

	private IP_ADAPTER_INFO( int address )
	{
		super( null );
		pointer = new Pointer( new NativeMemoryBlock( address, getSizeOf( ) ) );
	}

	public static IP_ADAPTER_INFO fromAddress( int address )
			throws NativeException
	{
		return new IP_ADAPTER_INFO( address ).getValue( );
	}

	private IP_ADAPTER_INFO( boolean dummy )
	{
		super( null );
		if ( dummy != false )
		{
			throw new IllegalArgumentException( "Dummy must be false, AH! Ah! AH!" );
		}
	}

	/**
	 * Creates a Memory place to call GetAdaptersInfo, then you need to get the
	 * list Of IP_ADAPTER_INFO from getListOf_IP_ADAPTER_INFO
	 * 
	 * @param size
	 *            size of the Array of IP_ADAPTER_INFO
	 * @return a NullPointer if the size < 1
	 * @throws NativeException
	 */
	public static Pointer reserveMemoryOf_IP_ADAPTER_INFO( int size )
			throws NativeException
	{
		if ( size < 1 )
		{
			return NullPointer.NULL;
		}
		else
		{
			return new Pointer( MemoryBlockFactory.createMemoryBlock( size
					* new IP_ADAPTER_INFO( false ).getSizeOf( ) ) );
		}
	}

	/**
	 * Create a list of IP_ADAPTER_INFO from a pointer (see
	 * createArrayOf_IP_ADAPTER_INFO)
	 * 
	 * @param p
	 *            the native memory containing the structures
	 * @return the List
	 * @throws NativeException
	 */
	public static IP_ADAPTER_INFO[] getListOf_IP_ADAPTER_INFO( Pointer p )
			throws NativeException
	{
		IP_ADAPTER_INFO ip_adapter_info = new IP_ADAPTER_INFO( false );
		int size = p.getSize( ) / ip_adapter_info.getSizeOf( );
		if ( size * ip_adapter_info.getSizeOf( ) != p.getSize( ) )
		{
			throw new IllegalArgumentException( "The pointer passed in has not a IP_ADAPTER_INFO size boundary "
					+ p.getSize( )
					+ " vs. "
					+ ip_adapter_info.getSizeOf( )
					+ ". Call the other method if you are sure of what you are doing." );
		}
		else
		{
			return getListOf_IP_ADAPTER_INFO( p, size );
		}
	}

	/**
	 * This method is potentially unsafe because it only checks that the pointer
	 * addresses a memory region that is at least equal size of size *
	 * sizeof(IP_ADAPTER_INFO)
	 * 
	 * @param p
	 *            the native memory containing the structures
	 * @param size
	 *            : max. number of IP_ADAPTER_INFO structures
	 * @return the list
	 * @throws NativeException
	 */
	public static IP_ADAPTER_INFO[] getListOf_IP_ADAPTER_INFO( Pointer p,
			int size ) throws NativeException
	{
		IP_ADAPTER_INFO ip_adapter_info = new IP_ADAPTER_INFO( false );
		int totalSize = size * ip_adapter_info.getSizeOf( );
		if ( p.getSize( ) < totalSize )
		{
			throw new IllegalArgumentException( "This pointer is addressing a too small memory region, size is "
					+ p.getSize( )
					+ ", needed size is "
					+ totalSize );
		}

		List array = new LinkedList( );
		int address = p.getPointer( );
		for ( int i = 0; address != 0 && i < size; i++ )
		{
			ip_adapter_info = fromAddress( address );
			array.add( ip_adapter_info );
			address = ip_adapter_info.Next;
		}
		return (IP_ADAPTER_INFO[]) array.toArray( new IP_ADAPTER_INFO[0] );
	}

	public Pointer createPointer( ) throws NativeException
	{
		pointer = new Pointer( MemoryBlockFactory.createMemoryBlock( getSizeOf( ) ) );
		return pointer;
	}

	public int getSizeOf( )
	{
		return 4
				+ 4
				+ 260
				+ 132
				+ 4
				+ 8
				+ 4
				+ 4
				+ 4
				+ 4
				+ 40
				+ 40
				+ 40
				+ 4
				+ 40
				+ 40
				+ 4
				+ 4;
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
		int pos = 0;
		byte[] mem = pointer.getMemory( );
		Next = pointer.getAsInt( pos );
		pos += 4;
		ComboIndex.setValue( pointer.getAsInt( pos ) );
		pos += 4;
		AdapterName = getString( mem, pos, MAX_ADAPTER_NAME_LENGTH + 4 );
		pos += MAX_ADAPTER_NAME_LENGTH + 4;
		Description = getString( mem, pos, MAX_ADAPTER_DESCRIPTION_LENGTH + 4 );
		pos += MAX_ADAPTER_DESCRIPTION_LENGTH + 4;
		AddressLength = pointer.getAsInt( pos );
		pos += 4;
		System.arraycopy( mem, pos, Address, 0, AddressLength );
		pos += MAX_ADAPTER_ADDRESS_LENGTH;// AddressLength;
		Index.setValue( pointer.getAsInt( pos ) );
		pos += 4;
		Type = pointer.getAsInt( pos );
		pos += 4;
		DhcpEnabled = pointer.getAsInt( pos );
		pos += 4;
		/* CurrentIpAddress = NullPointer.NULL; */pos += 4;
		IpAddressList = IP_ADDR_STRING.fromAddress( pointer.getPointer( ) + pos );
		pos += IpAddressList.getSizeOf( );
		GatewayList = IP_ADDR_STRING.fromAddress( pointer.getPointer( ) + pos );
		pos += GatewayList.getSizeOf( );
		DhcpServer = IP_ADDR_STRING.fromAddress( pointer.getPointer( ) + pos );
		pos += DhcpServer.getSizeOf( );
		HaveWins = pointer.getAsInt( pos ) != 0;
		pos += 4;

		PrimaryWinsServer = IP_ADDR_STRING.fromAddress( pointer.getPointer( )
				+ pos );
		pos += PrimaryWinsServer.getSizeOf( );
		SecondaryWinsServer = IP_ADDR_STRING.fromAddress( pointer.getPointer( )
				+ pos );
		pos += SecondaryWinsServer.getSizeOf( );

		LeaseObtained = new TIME_T( pointer.getAsInt( pos ) );
		pos += 4;
		LeaseExpires = new TIME_T( pointer.getAsInt( pos ) );

		return this;
	}

	public IP_ADAPTER_INFO getValue( ) throws NativeException
	{
		return (IP_ADAPTER_INFO) getValueFromPointer( );
	}

	public int getNext( )
	{
		return Next;
	}

	public DWORD getComboIndex( )
	{
		return ComboIndex;
	}

	public String getAdapterName( )
	{
		return AdapterName;
	}

	public String getDescription( )
	{
		return Description;
	}

	public int getAddressLength( )
	{
		return AddressLength;
	}

	private String getHexString( int number, int prci )
	{
		String string = Integer.toHexString( number );
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

	public String getAddressAsWindowsFormat( )
	{
		return new StringBuffer( ).append( getHexString( Address[0], 2 ) )
				.append( "-" )
				.append( getHexString( Address[1], 2 ) )
				.append( "-" )
				.append( getHexString( Address[2], 2 ) )
				.append( "-" )
				.append( getHexString( Address[3], 2 ) )
				.append( "-" )
				.append( getHexString( Address[4], 2 ) )
				.append( "-" )
				.append( getHexString( Address[5], 2 ) )
				.toString( )
				.toUpperCase( );
	}

	public String getAddressAsUnixFormat( )
	{
		return new StringBuffer( ).append( getHexString( Address[0], 2 ) )
				.append( ":" )
				.append( getHexString( Address[1], 2 ) )
				.append( ":" )
				.append( getHexString( Address[2], 2 ) )
				.append( ":" )
				.append( getHexString( Address[3], 2 ) )
				.append( ":" )
				.append( getHexString( Address[4], 2 ) )
				.append( ":" )
				.append( getHexString( Address[5], 2 ) )
				.toString( )
				.toUpperCase( );
	}

	public byte[] getAddress( )
	{
		return Address;
	}

	public DWORD getIndex( )
	{
		return Index;
	}

	public int getType( )
	{
		return Type;
	}

	public int getDhcpEnabled( )
	{
		return DhcpEnabled;
	}

	public Pointer getCurrentIpAddress( )
	{
		return CurrentIpAddress;
	}

	public IP_ADDR_STRING getIpAddressList( )
	{
		return IpAddressList;
	}

	public IP_ADDR_STRING getGatewayList( )
	{
		return GatewayList;
	}

	public IP_ADDR_STRING getDhcpServer( )
	{
		return DhcpServer;
	}

	public boolean isHaveWins( )
	{
		return HaveWins;
	}

	public IP_ADDR_STRING getPrimaryWinsServer( )
	{
		return PrimaryWinsServer;
	}

	public IP_ADDR_STRING getSecondaryWinsServer( )
	{
		return SecondaryWinsServer;
	}

	public TIME_T getLeaseObtained( )
	{
		return LeaseObtained;
	}

	public TIME_T getLeaseExpires( )
	{
		return LeaseExpires;
	}

}
