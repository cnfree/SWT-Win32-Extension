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

package org.sf.feeling.swt.win32.extension.jna.ptr;

import java.io.UnsupportedEncodingException;

import org.sf.feeling.swt.win32.extension.jna.Native;
import org.sf.feeling.swt.win32.extension.jna.StructConverter;
import org.sf.feeling.swt.win32.extension.jna.datatype.LONG;
import org.sf.feeling.swt.win32.extension.jna.exception.NativeException;
import org.sf.feeling.swt.win32.extension.jna.ptr.memory.MemoryBlock;
import org.sf.feeling.swt.win32.extension.jna.ptr.memory.MemoryBlockFactory;
import org.sf.feeling.swt.win32.extension.jna.ptr.memory.NativeMemoryBlock;
import org.sf.feeling.swt.win32.internal.extension.Extension;

/**
 * 
 * <p>
 * <b>This class encapsulate a native pointer.</b>
 * </p>
 * <p>
 * To create a pointer you should first create a memory block that can be
 * addressed by this Pointer. See
 * org.xvolks.Native.pointers.memory.MemoryBlockFactory
 * </p>
 * <p>
 * A Pointer can also be obtained by using a Structure (BasicData) or one of its
 * concreate implementation (LONG, MemoryStatusEx...) with the method
 * BasicData.createPointer().
 * </p>
 * 
 * @see org.sf.feeling.swt.win32.extension.jna.ptr.memory.MemoryBlockFactory
 * @see org.sf.feeling.swt.win32.extension.jna.datatype.BasicData <br>
 */

public class Pointer
{

	private MemoryBlock mem;

	public Pointer( MemoryBlock mem )
	{
		this.mem = mem;
	}

	// some convenience methods to create pointers
	public static Pointer createPointerFromString( String memory )
			throws NativeException
	{
		if ( memory == null )
		{
			return NullPointer.NULL;
		}
		Pointer p = new Pointer( MemoryBlockFactory.createMemoryBlock( memory.length( ) + 1 ) );
		p.setStringAt( 0, memory );
		return p;
	}

	public static Pointer createPointerFromString( String memory, boolean wide )
			throws NativeException
	{
		if ( !wide )
			return createPointerFromString( memory );
		else
		{
			if ( memory == null )
			{
				return NullPointer.NULL;
			}
			Pointer p = new Pointer( MemoryBlockFactory.createMemoryBlock( ( memory.length( ) + 1 ) * 2 ) );
			Extension.MoveMemory( p.getPointer( ),
					( memory + "\0" ).toCharArray( ),
					p.getSize( ) );
			return p;
		}
	}

	/**
	 * Generate an ANSI String pointer.
	 * 
	 * @param memory
	 * @param encoding
	 * @return the string pointer
	 * @throws NativeException
	 * @throws UnsupportedEncodingException
	 */
	public static Pointer createPointerFromString( String memory,
			String encoding ) throws NativeException,
			UnsupportedEncodingException
	{
		if ( memory == null )
		{
			return NullPointer.NULL;
		}
		byte[] str = memory.getBytes( encoding );
		Pointer p = new Pointer( MemoryBlockFactory.createMemoryBlock( str.length + 1 ) );
		p.setMemory( str );
		return p;
	}

	public static Pointer createPointer( int size ) throws NativeException
	{
		if ( size <= 0 )
		{
			return NullPointer.NULL;
		}
		return new Pointer( MemoryBlockFactory.createMemoryBlock( size ) );
	}

	public static Pointer createPointerToNativeMemory( int nativeAddress,
			int size ) throws NativeException
	{
		if ( nativeAddress == 0 || size <= 0 )
		{
			return NullPointer.NULL;
		}
		return new Pointer( new NativeMemoryBlock( nativeAddress, size ) );
	}

	/**
	 * Method dispose frees the memory addressed by this pointer
	 * 
	 * @exception NativeException
	 * 
	 */
	public void dispose( ) throws NativeException
	{
		mem.dispose( );
	}

	/**
	 * Method getPointer
	 * 
	 * @return the address of this pointer
	 * 
	 */
	public int getPointer( )
	{
		return mem.getPointer( );
	}

	/**
	 * Method getSize
	 * 
	 * @return the size of the memory block addressed by this pointer
	 * 
	 */
	public int getSize( )
	{
		return mem.getSize( );
	}

	/**
	 * Method setMemory fills the native memory with the content of
	 * <code>buffer</code>
	 * 
	 * @param buffer
	 *            the source to strcpy
	 * 
	 * @exception NativeException
	 * @exception ArrayIndexOutOfBoundsException
	 *                if the size of the buffer is greater than the size of the
	 *                native memory
	 */
	public void setMemory( String buffer ) throws NativeException,
			ArrayIndexOutOfBoundsException
	{
		if ( buffer.length( ) > mem.getSize( ) )
			throw new ArrayIndexOutOfBoundsException( "This string is bigger than the current memory addressed by my pointer : "
					+ buffer.length( )
					+ ">"
					+ mem.getSize( ) );
		Native.setMemory( mem.getPointer( ), buffer );
	}

	/**
	 * Method setMemory fills the native memory with the content of
	 * <code>buffer</code>
	 * 
	 * @param buffer
	 *            the source to memcpy
	 * 
	 * @exception NativeException
	 * @exception ArrayIndexOutOfBoundsException
	 *                if the size of the buffer is greater than the size of the
	 *                native memory
	 */
	public void setMemory( byte[] buffer ) throws NativeException
	{
		if ( buffer.length > mem.getSize( ) )
			throw new ArrayIndexOutOfBoundsException( "This buffer is bigger than the current memory addressed by my pointer : "
					+ buffer.length
					+ ">"
					+ mem.getSize( ) );
		Native.setMemory( mem.getPointer( ), buffer );
	}

	/**
	 * Method setByteAt
	 * 
	 * @param offset
	 *            int the native memory block
	 * @param value
	 *            the byte to write
	 * 
	 * @return the size of written data : here 1
	 * 
	 * @exception NativeException
	 * @exception ArrayIndexOutOfBoundsException
	 *                if offset > size of the native buufer
	 * 
	 */
	public int setByteAt( int offset, byte value ) throws NativeException
	{
		if ( offset > mem.getSize( ) )
			throw new ArrayIndexOutOfBoundsException( "This data is bigger than the current memory addressed by my pointer : "
					+ offset
					+ ">"
					+ mem.getSize( ) );
		byte[] buf = new byte[1];
		buf[0] = value;
		Native.setMemory( mem.getPointer( ), buf, offset, 1 );
		return 1;
	}

	/**
	 * Method setShortAt
	 * 
	 * @param offset
	 *            int the native memory block
	 * @param value
	 *            the short to write
	 * 
	 * @return the size of written data : here 2
	 * 
	 * @exception NativeException
	 * @exception ArrayIndexOutOfBoundsException
	 *                if offset + 2 > size of the native buufer
	 * 
	 */
	public int setShortAt( int offset, short value ) throws NativeException
	{
		if ( offset + 2 > mem.getSize( ) )
			throw new ArrayIndexOutOfBoundsException( "This data is bigger than the current memory addressed by my pointer : "
					+ ( offset + 2 )
					+ ">"
					+ mem.getSize( ) );
		byte[] buf = new byte[2];
		StructConverter.shortIntoBytes( value, buf, 0 );
		Native.setMemory( mem.getPointer( ), buf, offset, 2 );
		return 2;
	}

	/**
	 * Method setIntAt
	 * 
	 * @param offset
	 *            int the native memory block
	 * @param value
	 *            the integer to write
	 * 
	 * @return the size of written data : here 4
	 * 
	 * @exception NativeException
	 * @exception ArrayIndexOutOfBoundsException
	 *                if offset + 4 > size of the native buufer
	 * 
	 */
	public int setIntAt( int offset, int value ) throws NativeException
	{
		if ( offset + 4 > mem.getSize( ) )
			throw new ArrayIndexOutOfBoundsException( "This data is bigger than the current memory addressed by my pointer : "
					+ ( offset + 4 )
					+ ">"
					+ mem.getSize( ) );
		byte[] buf = new byte[4];
		StructConverter.intIntoBytes( value, buf, 0 );
		Native.setMemory( mem.getPointer( ), buf, offset, 4 );
		return 4;
	}

	/**
	 * Method setLongAt
	 * 
	 * @param offset
	 *            int the native memory block
	 * @param value
	 *            the long to write
	 * 
	 * @return the size of written data : here 8
	 * 
	 * @exception NativeException
	 * @exception ArrayIndexOutOfBoundsException
	 *                if offset + 8 > size of the native buufer
	 * 
	 */
	public int setLongAt( int offset, long value ) throws NativeException
	{
		if ( offset + 8 > mem.getSize( ) )
			throw new ArrayIndexOutOfBoundsException( "This data is bigger than the current memory addressed by my pointer : "
					+ ( offset + 8 )
					+ ">"
					+ mem.getSize( ) );
		byte[] buf = new byte[8];
		StructConverter.longIntoBytes( value, buf, 0 );
		Native.setMemory( mem.getPointer( ), buf, offset, 8 );
		return 8;
	}

	/**
	 * Method setFloatAt
	 * 
	 * @param offset
	 *            int the native memory block
	 * @param value
	 *            the float to write
	 * 
	 * @return the size of written data : here 8
	 * 
	 * @exception NativeException
	 * @exception ArrayIndexOutOfBoundsException
	 *                if offset + 8 > size of the native buufer
	 * 
	 */
	public int setFloatAt( int offset, float value ) throws NativeException
	{
		if ( offset + 4 > mem.getSize( ) )
			throw new ArrayIndexOutOfBoundsException( "This data is bigger than the current memory addressed by my pointer : "
					+ ( offset + 8 )
					+ ">"
					+ mem.getSize( ) );
		byte[] buf = new byte[8];
		StructConverter.floatIntoBytes( value, buf, 0 );
		Native.setMemory( mem.getPointer( ), buf, offset, 8 );
		return 8;
	}

	/**
	 * Method setDoubleAt
	 * 
	 * @param offset
	 *            int the native memory block
	 * @param value
	 *            the double to write
	 * 
	 * @return the size of written data : here 8
	 * 
	 * @exception NativeException
	 * @exception ArrayIndexOutOfBoundsException
	 *                if offset + 8 > size of the native buufer
	 * 
	 */
	public int setDoubleAt( int offset, double value ) throws NativeException
	{
		if ( offset + 8 > mem.getSize( ) )
			throw new ArrayIndexOutOfBoundsException( "This data is bigger than the current memory addressed by my pointer : "
					+ ( offset + 8 )
					+ ">"
					+ mem.getSize( ) );
		byte[] buf = new byte[8];
		StructConverter.doubleIntoBytes( value, buf, 0 );
		Native.setMemory( mem.getPointer( ), buf, offset, 8 );
		return 8;
	}

	/**
	 * Method setStringAt
	 * 
	 * @param offset
	 *            int the native memory block
	 * @param value
	 *            the String to write
	 * 
	 * @return the size of written data : here the length of <code>value</code>
	 * 
	 * @exception NativeException
	 * @exception ArrayIndexOutOfBoundsException
	 *                if offset + len(value) > size of the native buufer
	 */
	public int setStringAt( int offset, String value ) throws NativeException
	{
		int len = value.getBytes( ).length;
		if ( offset + len >= mem.getSize( ) )
			throw new ArrayIndexOutOfBoundsException( "This data is bigger than the current memory addressed by my pointer : "
					+ ( offset + len )
					+ ">"
					+ mem.getSize( ) );
		Native.setMemory( mem.getPointer( ), value.getBytes( ), offset, len );
		return len;
	}

	public int setStringAt( int offset, String value, boolean wide )
			throws NativeException
	{
		if ( !wide )
			return setStringAt( offset, value );
		else
		{
			int len = value.length( ) * 2;
			if ( offset + len >= mem.getSize( ) )
				throw new ArrayIndexOutOfBoundsException( "This data is bigger than the current memory addressed by my pointer : "
						+ ( offset + len )
						+ ">"
						+ mem.getSize( ) );
			Extension.MoveMemory( mem.getPointer( ),
					( value ).toCharArray( ),
					len );
			return len;
		}
	}

	/**
	 * Method zeroMemory performs a memset(pointer, 0)
	 * 
	 * @exception NativeException
	 * 
	 */
	public void zeroMemory( ) throws NativeException
	{
		setMemory( new byte[mem.getSize( )] );
	}

	/**
	 * Method getMemory
	 * 
	 * @return a copy of the native memory
	 * 
	 * @exception NativeException
	 * 
	 */
	public byte[] getMemory( ) throws NativeException
	{
		return Native.getMemory( mem.getPointer( ), mem.getSize( ) );
	}

	/**
	 * Method getAsString
	 * 
	 * @return a copy of the native memory as String
	 * 
	 * @exception NativeException
	 * 
	 */
	public String getAsString( ) throws NativeException
	{
		return Native.getMemoryAsString( mem.getPointer( ), mem.getSize( ) );
	}

	public String getAsString( boolean wide ) throws NativeException
	{
		if ( !wide )
			return getAsString( );
		else
		{
			int length = Extension.wcslen( mem.getPointer( ) );
			char[] Source1 = new char[length];
			Extension.MoveMemory( Source1, mem.getPointer( ), length * 2 );
			return new String( Source1, 0, length );
		}
	}

	/**
	 * Method getAsByte
	 * 
	 * @param offset
	 * 
	 * @return the byte at offset <code>offset</code>
	 * 
	 * @exception NativeException
	 * 
	 */
	public byte getAsByte( int offset ) throws NativeException
	{
		return Native.getMemory( mem.getPointer( ), mem.getSize( ) )[offset];
	}

	/**
	 * Method getAsShort
	 * 
	 * @param offset
	 *            an int
	 * 
	 * @return the short/WORD at offset <code>offset</code>
	 * 
	 * @exception NativeException
	 * 
	 */
	public short getAsShort( int offset ) throws NativeException
	{
		return StructConverter.bytesIntoShort( Native.getMemory( mem.getPointer( ),
				mem.getSize( ) ),
				offset );
	}

	/**
	 * Method getAsInt
	 * 
	 * @param offset
	 *            an int
	 * 
	 * @return the int/DWORD at offset <code>offset</code>
	 * 
	 * @exception NativeException
	 * 
	 */
	public int getAsInt( int offset ) throws NativeException
	{
		return StructConverter.bytesIntoInt( Native.getMemory( mem.getPointer( ),
				mem.getSize( ) ),
				offset );
	}

	/**
	 * Method getAsLong
	 * 
	 * @param offset
	 *            an int
	 * 
	 * @return the long/int64 at offset <code>offset</code>
	 * 
	 * @exception NativeException
	 * 
	 */
	public long getAsLong( int offset ) throws NativeException
	{
		return StructConverter.bytesIntoLong( Native.getMemory( mem.getPointer( ),
				mem.getSize( ) ),
				offset );
	}

	/**
	 * Method getAsFloat
	 * 
	 * @param offset
	 *            an int
	 * 
	 * @return the float at offset <code>offset</code>
	 * 
	 * @exception NativeException
	 * 
	 */
	public float getAsFloat( int offset ) throws NativeException
	{
		return StructConverter.bytesIntoFloat( Native.getMemory( mem.getPointer( ),
				mem.getSize( ) ),
				offset );
	}

	/**
	 * Method getAsDouble
	 * 
	 * @param offset
	 *            an int
	 * 
	 * @return the double at offset <code>offset</code>
	 * 
	 * @exception NativeException
	 * 
	 */
	public double getAsDouble( int offset ) throws NativeException
	{
		return StructConverter.bytesIntoDouble( Native.getMemory( mem.getPointer( ),
				mem.getSize( ) ),
				offset );
	}

	/**
	 * Called by the garbage collector on an object when garbage collection
	 * determines that there are no more references to the object. A subclass
	 * overrides the <code>finalize</code> method to dispose of system resources
	 * or to perform other cleanup.
	 * <p>
	 * The general contract of <tt>finalize</tt> is that it is invoked if and
	 * when the Java<font size="-2"><sup>TM</sup></font> virtual machine has
	 * determined that there is no longer any means by which this object can be
	 * accessed by any thread that has not yet died, except as a result of an
	 * action taken by the finalization of some other object or class which is
	 * ready to be finalized. The <tt>finalize</tt> method may take any action,
	 * including making this object available again to other threads; the usual
	 * purpose of <tt>finalize</tt>, however, is to perform cleanup actions
	 * before the object is irrevocably discarded. For example, the finalize
	 * method for an object that represents an input/output connection might
	 * perform explicit I/O transactions to break the connection before the
	 * object is permanently discarded.
	 * <p>
	 * The <tt>finalize</tt> method of class <tt>Object</tt> performs no special
	 * action; it simply returns normally. Subclasses of <tt>Object</tt> may
	 * override this definition.
	 * <p>
	 * The Java programming language does not guarantee which thread will invoke
	 * the <tt>finalize</tt> method for any given object. It is guaranteed,
	 * however, that the thread that invokes finalize will not be holding any
	 * user-visible synchronization locks when finalize is invoked. If an
	 * uncaught exception is thrown by the finalize method, the exception is
	 * ignored and finalization of that object terminates.
	 * <p>
	 * After the <tt>finalize</tt> method has been invoked for an object, no
	 * further action is taken until the Java virtual machine has again
	 * determined that there is no longer any means by which this object can be
	 * accessed by any thread that has not yet died, including possible actions
	 * by other objects or classes which are ready to be finalized, at which
	 * point the object may be discarded.
	 * <p>
	 * The <tt>finalize</tt> method is never invoked more than once by a Java
	 * virtual machine for any given object.
	 * <p>
	 * Any exception thrown by the <code>finalize</code> method causes the
	 * finalization of this object to be halted, but is otherwise ignored. <br>
	 * <p>
	 * <b> Calls dispose()</b>
	 * </p>
	 * 
	 * @throws Throwable
	 *             the <code>Exception</code> raised by this method
	 */
	protected void finalize( ) throws Throwable
	{
		super.finalize( );
		try
		{
			dispose( );
		}
		catch ( Throwable e )
		{
			e.printStackTrace( );
		}
	}

	/**
	 * 
	 * @return true if this pointer is NULL
	 */
	public boolean isNull( )
	{
		return mem == null || mem.getPointer( ) == 0;
	}

	public LONG asLONG( )
	{
		return new LONG( getPointer( ) );
	}
}
