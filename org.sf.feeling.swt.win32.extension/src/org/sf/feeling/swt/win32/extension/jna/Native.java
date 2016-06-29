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
package org.sf.feeling.swt.win32.extension.jna;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;

import org.sf.feeling.swt.win32.extension.jna.datatype.BasicData;
import org.sf.feeling.swt.win32.extension.jna.datatype.CHAR;
import org.sf.feeling.swt.win32.extension.jna.datatype.DOUBLE;
import org.sf.feeling.swt.win32.extension.jna.datatype.FLOAT;
import org.sf.feeling.swt.win32.extension.jna.datatype.INT;
import org.sf.feeling.swt.win32.extension.jna.datatype.LONG;
import org.sf.feeling.swt.win32.extension.jna.datatype.LONGLONG;
import org.sf.feeling.swt.win32.extension.jna.datatype.SHORT;
import org.sf.feeling.swt.win32.extension.jna.datatype.win32.DWORD;
import org.sf.feeling.swt.win32.extension.jna.datatype.win32.HANDLE;
import org.sf.feeling.swt.win32.extension.jna.datatype.win32.Structure;
import org.sf.feeling.swt.win32.extension.jna.exception.NativeException;
import org.sf.feeling.swt.win32.extension.jna.ptr.NullPointer;
import org.sf.feeling.swt.win32.extension.jna.ptr.Pointer;
import org.sf.feeling.swt.win32.extension.jna.ptr.memory.NativeMemoryBlock;
import org.sf.feeling.swt.win32.extension.jna.win32.DbgHelp;
import org.sf.feeling.swt.win32.extension.jna.win32.Kernel32;
import org.sf.feeling.swt.win32.extension.jna.win32.structure.HWND;
import org.sf.feeling.swt.win32.extension.jna.win32.structure.SECURITY_ATTRIBUTES;
import org.sf.feeling.swt.win32.internal.extension.Extension;

/**
 * This is the main class for calling native functions.<br>
 * 
 * <ul>
 * <li>create a new Native object (Native messageBox = new Native("User32.dll",
 * "MessageBoxA");</li>
 * <li>set its return type (messageBox.setRetVal(Type.INT);</li>
 * <li>pass some parameters (messageBox.setParameter(0, Type.INT, "0");</li>
 * <li>pass some parameters (messageBox.setParameter(1, Type.STRING, "message");
 * </li>
 * <li>pass some parameters (messageBox.setParameter(2, Type.STRING, "caption");
 * </li>
 * <li>pass some parameters (messageBox.setParameter(3, Type.INT, "" + 0);</li>
 * <li>then invoke the function (messageBox.invoke();</li>
 * <li>you can get its return value (messageBox.getRetVal();</li>
 * </ul>
 * So simple :) <br>
 * if you have to deal with pointers you can create some, here is a sample, it
 * uses one pointer but could have be done with 3 (one per PULARGE_INTEGER)
 * <hr>
 * The C function to call
 * 
 * <pre>
 * 
 * 
 * 
 * 
 * 
 * BOOL GetDiskFreeSpaceEx( LPCTSTR lpDirectoryName,
 * 		PULARGE_INTEGER lpFreeBytesAvailable,
 * 		PULARGE_INTEGER lpTotalNumberOfBytes,
 * 		PULARGE_INTEGER lpTotalNumberOfFreeBytes );
 * </pre>
 * 
 * <HR>
 * The implementation in Java with Native
 * 
 * <pre>
 * 
 * 
 * 
 * 
 * 
 * public static final FreeDiskSpace getDiskFreeSpaceEx( String drive )
 * 		throws NativeException, IllegalAccessException
 * {
 * 	if ( drive == null )
 * 		throw new NullPointerException( &quot;The drive name cannot be null !&quot; );
 * 	Pointer lpFreeBytesAvailable = new Pointer( 24 );
 * 	int i = 0;
 * 	Native fs = new Native( &quot;Kernel32.dll&quot;, &quot;GetDiskFreeSpaceExA&quot; );
 * 	fs.setRetVal( Type.INT );
 * 	fs.setParameter( i++, Type.STRING, drive );
 * 	fs.setParameter( i++, lpFreeBytesAvailable.getPointer( ), 8 );
 * 	fs.setParameter( i++, lpFreeBytesAvailable.getPointer( ) + 8, 8 );
 * 	fs.setParameter( i++, lpFreeBytesAvailable.getPointer( ) + 16, 8 );
 * 	fs.invoke( );
 * 	FreeDiskSpace dsp = new FreeDiskSpace( drive, lpFreeBytesAvailable );
 * 	lpFreeBytesAvailable.dispose( );
 * 	return dsp;
 * }
 * </pre>
 */
public class Native
{

	static
	{
		Extension.loadNativeLibrary( "swt-extension-jna-win32" );
	}

	private final static Map mLibs = new HashMap( );

	public static final String DLL_NAME = "swt-extension-jna-win32.dll";

	private static int currentModuleHandle;
	/**
	 * Pointer on the function address
	 */
	private int functionAddress;
	private boolean mIsClosed = false;
	private final String mDllName;
	private final String mFunctionName;
	private static Map mCallbacks = new TreeMap( );
	/*
	 * The following fields are used by native side directly and must not be
	 * renamed!!
	 */
	/**
	 * Pre-allocated parameter array, need to grow if needed
	 */
	private Vector parameters = new Vector( );
	private Vector parameterTypes = new Vector( );
	private String mRetValue;
	// Used by native side (handle of the library)
	private int moduleHandle;
	// Used by native side
	private int convention;
	// Used by native side
	private int lastError;

	private int mRetType;

	private Vector getParameters( )
	{
		return parameters;
	}

	private Vector getParameterTypes( )
	{
		return parameterTypes;
	}

	public String toString( )
	{
		return mDllName + "-" + mFunctionName;
	}

	public static void setDefaultCallingConvention( Convention defaultConvention )
	{
		Convention.setDefaultStyle( defaultConvention );
	}

	/**
	 * Constructor exact call of new Native(dllName, functionName, false,
	 * Convention.DEFAULT); <br>
	 * Creates a function without debug output
	 * 
	 * @param dllName
	 *            the name of library file
	 * @param functionName
	 *            the decorated name of the function (MessageBoxA instead of
	 *            MessageBox)
	 * 
	 * @exception NativeException
	 *                if the dll was not found, function name is incorrect...
	 * @see org.sf.feeling.swt.win32.extension.jna.Native#getDLLFileExports(String)
	 */
	public Native( String dllName, String functionName ) throws NativeException
	{
		this( dllName, functionName, Convention.DEFAULT );
	}

	/**
	 * Constructor
	 * 
	 * 
	 * 
	 * @deprecated the nativeDebug in meaningless now
	 * 
	 * @param dllName
	 *            a String the name of the library; that DLL must be in the
	 *            library.path
	 * @param functionName
	 *            a String the name of the function this is the decorated name
	 *            (@see org.sf.feeling.swt.win32.extension.jna.Native#
	 *            getDLLFileExports(String))
	 * @param convention
	 *            convention of function call
	 * @exception NativeException
	 *                if the dll was not found, function name is incorrect...
	 * @see org.sf.feeling.swt.win32.extension.jna.Native#getDLLFileExports(String)
	 */
	public Native( String dllName, String functionName, Convention convention )
			throws NativeException
	{
		this.convention = convention.getValue( );
		mDllName = dllName;
		mFunctionName = functionName;

		// load the library
		loadLibrary( );

		try
		{
			setRetVal( Type.VOID );
		}
		catch ( IllegalAccessException e )
		{
		}
	}

	/**
	 * Creates a function without debug output that can call an anonymous
	 * function by it's address *
	 * 
	 * @exception NativeException
	 *                if the dll was not found, function name is incorrect...
	 */
	public Native( int address, Convention convention ) throws NativeException
	{
		this.convention = convention.getValue( );
		mDllName = "Anonymous";
		mFunctionName = mDllName;
		functionAddress = address;
		try
		{
			setRetVal( Type.VOID );
		}
		catch ( IllegalAccessException e )
		{
			e.printStackTrace( );
		}
	}

	// Loads the native library
	private final boolean loadLibrary( ) throws NativeException
	{
		synchronized ( mLibs )
		{
			// search the cache for this library
			final Native libDesc = (Native) mLibs.get( mDllName );

			// has this library been loaded already?
			if ( libDesc == null || libDesc.getModuleHandle( ) == 0 )
			{
				// not yet loaded --> do it now
				functionAddress = LoadLibrary( mDllName, mFunctionName );
				// add the reference to the cache
				mLibs.put( mDllName, this );
			}
			// library has been loaded already
			else
			{
				// get the hModule
				moduleHandle = libDesc.getModuleHandle( );
				// find the function in the already loaded library
				functionAddress = FindFunction( moduleHandle, mFunctionName );
			}
			mIsClosed = false;
			return functionAddress != 0;
		}
	}

	public static final boolean isLibraryLoaded( String name )
	{
		synchronized ( mLibs )
		{
			final Native libDesc = (Native) mLibs.get( name );

			return ( libDesc != null && libDesc.getModuleHandle( ) != 0 && libDesc.getFunctionPointer( ) != 0 );
		}
	}

	/**
	 * Gets the native pointer of a function, can be used to pass function
	 * pointer to an other function.
	 * 
	 * @return the native function pointer
	 */
	public int getFunctionPointer( )
	{
		// throwClosed();
		return functionAddress;
	}

	/**
	 * Gets the native handle to the dll referenced by this Native instance
	 * 
	 * @return the HMODULE associated with the native DLL referenced by this
	 *         Native instance
	 */
	public int getModuleHandle( )
	{
		return moduleHandle;
	}

	public void setParameter( int pos, int value )
			throws IllegalAccessException
	{
		setParameter( pos, Type.INT, value + "" );
	}

	/**
	 * Method setParameter <br>
	 * Sets the parameter at index <code>pos</code>
	 * 
	 * @param pos
	 *            the offset of the parameter
	 * @param type
	 *            one of the enum entry (INT, STRING...)
	 * @param value
	 *            the parameter in its String representation
	 * 
	 * @throws IllegalAccessException
	 *             if this object has been disposed
	 * 
	 */
	public void setParameter( int pos, Type type, String value )
			throws IllegalAccessException
	{
		if ( value == null )
		{
			setParameter( pos, 0 );
		}
		else
		{
			setParameter( pos, type, ( value + '\0' ).getBytes( ) );
		}
	}

	/**
	 * Method setParameter <br>
	 * Sets the parameter at index <code>pos</code>
	 * 
	 * @param pos
	 *            the offset of the parameter
	 * @param value
	 *            the String parameter (this parameter must be a in one) !
	 * @throws IllegalAccessException
	 *             if this object has been disposed
	 * 
	 */
	public void setParameter( int pos, String value )
			throws IllegalAccessException
	{
		setParameter( pos, Type.STRING, value );
	}

	/**
	 * GetLastError workaround for Win32.
	 * 
	 * @return the GetLastError status set by the last call to nInvoke
	 */
	public int getLastError( )
	{
		return lastError;
	}

	/**
	 * Method setParameter <br>
	 * Sets the parameter at index <code>pos</code>
	 * 
	 * @param pos
	 *            the offset of the parameter
	 * @param type
	 *            one of the enum entry (INT, STRING...)
	 * @param value
	 *            the parameter in its byte[] representation
	 * 
	 * @exception NativeException
	 * 
	 */
	public void setParameter( int pos, Type type, byte[] value )
			throws /* NativeException, */IllegalAccessException
	{
		// throwClosed();

		if ( parameters.size( ) <= pos )
		{
			// Fills the vector with null and expect that the call will give
			// right values later
			int i = parameters.size( );
			while ( i++ <= pos )
			{
				parameters.add( new byte[4] );
				parameterTypes.add( new Integer( Type.INT.getNativeType( ) ) );
			}
		}
		parameters.set( pos, value );
		parameterTypes.set( pos, new Integer( type.getNativeType( ) ) );

		// ---------------------------------------DEBUG CODE -
		// final String pouet;
		// if(parameterTypes.get(pos) == Type.PSTRUCT.getNativeType()) {
		// int val=StructConverter.bytesIntoInt(parameters.get(pos), 0);
		// pouet = "0x"+Integer.toHexString(val)+ " soit "+val+" dec";
		// } else {
		// pouet = new String(parameters.get(pos));
		// }
		// getLogger().log(SEVERITY.DEBUG,
		// "Adding parameter "+pos+", value = "+pouet);
		// ---------------------------------------DEBUG CODE -

	}

	/**
	 * Method setParameter <br>
	 * Sets the parameter at index <code>pos</code>
	 * 
	 * @param pos
	 *            the offset of the parameter
	 * @param p
	 *            a pointer object representing an address
	 * 
	 * @exception NativeException
	 * @exception IllegalAccessException
	 *                if <code>dispose()</code> have already been called.
	 * 
	 */
	public void setParameter( int pos, Pointer p ) throws NativeException,
			IllegalAccessException
	{
		// throwClosed();
		if ( p == null || p.getPointer( ) == 0 )
		{
			setParameter( pos, 0 );
		}
		else
		{
			byte[] buf = new byte[4];
			StructConverter.intIntoBytes( p.getPointer( ), buf, 0 );
			setParameter( pos, Type.PSTRUCT, buf );
		}
	}

	public void setParameter( int pos, Structure structure )
			throws NativeException, IllegalAccessException
	{
		setParameter( pos, structure.createPointer( ) );
	}

	public void setParameter( int pos, BasicData data ) throws NativeException,
			IllegalAccessException
	{
		if ( data instanceof INT )
		{
			setParameter( pos, Type.INT, ( (INT) data ).getValueAsString( ) );
		}
		else if ( data instanceof SHORT )
		{
			setParameter( pos, Type.SHORT, ( (SHORT) data ).getValueAsString( ) );
		}
		else if ( data instanceof CHAR )
		{
			setParameter( pos, Type.CHAR, ( (SHORT) data ).getValueAsString( ) );
		}
		else if ( data instanceof LONGLONG )
		{
			setParameter( pos,
					Type.LONG,
					( (LONGLONG) data ).getValueAsString( ) );
		}
		else if ( data instanceof FLOAT )
		{
			setParameter( pos, Type.FLOAT, ( (FLOAT) data ).getValueAsString( ) );
		}
		else if ( data instanceof DOUBLE )
		{
			setParameter( pos,
					Type.DOUBLE,
					( (DOUBLE) data ).getValueAsString( ) );
		}
		else if ( data instanceof Structure )
		{
			setParameter( pos, (Structure) data );
		}
		else{
			throw new IllegalArgumentException( "Unknown Data Type" );
		}
		// setParameter( pos, structure.createPointer( ) );
	}

	/**
	 * Method setRetVal fixes the return type of this function.
	 * 
	 * @param type
	 *            a Type generally VOID or INT, if VOID it is not necessary to
	 *            call this function
	 * 
	 * @exception NativeException
	 * @exception IllegalAccessException
	 *                if <code>dispose()</code> have already been called.
	 * 
	 */
	public void setRetVal( Type type ) throws NativeException,
			IllegalAccessException
	{
		// throwClosed();
		mRetType = type.getNativeType( );
	}

	/**
	 * Method getRetVal gets the value returned by the function, should be
	 * verified to avoid invalid pointers when getting out values
	 * 
	 * @return the value returned by this function in its String representation<br>
	 *         BOOL functions return int values
	 * 
	 * @exception IllegalAccessException
	 *                if <code>dispose()</code> have already been called.
	 * 
	 */
	public String getRetVal( ) throws IllegalAccessException
	{
		throwClosed( );
		return mRetValue;
	}

	/**
	 * Method getRetValAsInt gets the value returned by the function, should be
	 * verified to avoid invalid pointers when getting out values
	 * 
	 * @return the value returned by this function in its Integer representation<br>
	 *         BOOL functions return int values
	 * 
	 * @exception IllegalAccessException
	 *                if <code>dispose()</code> have already been called.
	 * 
	 */
	public int getRetValAsInt( ) throws IllegalAccessException
	{
		return new Long( getRetVal( ) ).intValue( );
	}

	/**
	 * Method invoke calls the function
	 * 
	 * @exception NativeException
	 * @exception IllegalAccessException
	 *                if <code>dispose()</code> have already been called.
	 * 
	 */
	public void invoke( ) throws NativeException, IllegalAccessException
	{
		// throwClosed();

		// check if the library was (accidentially) unloaded before invoke() was
		// called
		if ( !isLibraryLoaded( mDllName ) && !"Anonymous".equals( mDllName ) )
		{
			loadLibrary( );
		}
		Invoke( functionAddress );
	}

	/**
	 * Method disposes free native pointers and memory internally used by the
	 * swt-extension-jna dll
	 * <p style="text-align: center; font-weight: bolder;">
	 * >>>>>>> Should not be called manually! <<<<<<<
	 * </p>
	 * 
	 * @exception NativeException
	 * 
	 */
	private synchronized final void unLoad( ) throws NativeException
	{
		// throwClosed();

		try
		{
			if ( isLibraryLoaded( mDllName ) )
			{
				Dispose( moduleHandle );
			}
			mIsClosed = true;
			moduleHandle = 0;
		}
		finally
		{
			synchronized ( mLibs )
			{
				mLibs.remove( mDllName );
			}
		}
	}

	/**
	 * Unloads a specific library. Only call this if you really know what you
	 * are doing!
	 * 
	 * @param name
	 *            the name of the library exactly spelled like at creation time.
	 * @return true if the library was freed
	 * @throws NativeException
	 */
	public static final boolean unLoadLibrary( String name )
			throws NativeException
	{
		synchronized ( mLibs )
		{
			if ( mLibs.containsKey( name ) )
			{
				( (Native) mLibs.get( name ) ).unLoad( );
				return true;
			}
			return false;
		}
	}

	/**
	 * Unloads all loaded libraries. This is automatically called when the JVM
	 * exits but may also be called to clean up. Only call this if you really
	 * know what you are doing!
	 * 
	 */
	public static final void unLoadAllLibraries( )
	{
		synchronized ( mLibs )
		{
			if ( !mLibs.isEmpty( ) )
			{
				// sleep a little
				try
				{
					Thread.sleep( 100L );
				}
				catch ( InterruptedException ex )
				{
					// ignore
				}

				Object keys[] = mLibs.keySet( ).toArray( );
				for ( int i = 0; i < keys.length; i++ )
				{
					try
					{
						unLoadLibrary( keys[i].toString( ) );
					}
					catch ( Throwable e )
					{
						e.printStackTrace( );
					}
				}
			}
		}
	}

	/**
	 * Method getFunctionName
	 * 
	 * @return the name of this function
	 * 
	 */
	public final String getFunctionName( )
	{
		return mFunctionName;
	}

	/**
	 * Method getDLLName
	 * 
	 * @return the name of this DLL
	 * 
	 */
	public final String getDLLName( )
	{
		return mDllName;
	}

	/**
	 * 
	 * 
	 * 
	 * @return the convention of call (CDECL, STDCALL)
	 */
	public Convention getStyle( )
	{
		return Convention.fromInt( convention );
	}

	/**
	 * Indicates whether some other object is "equal to" this one.
	 * 
	 * @return true if the dll and the funtionName are equals
	 */

	public boolean equals( Object obj )
	{
		if ( obj == null || !( obj instanceof Native ) )
		{
			return false;
		}
		else
		{
			Native n = (Native) obj;
			return mDllName.equals( n.getDLLName( ) )
					&& mFunctionName.equals( n.getFunctionName( ) );
		}
	}

	private void throwClosed( ) throws IllegalAccessException
	{
		if ( mIsClosed )
		{
			throw new IllegalAccessException( "This function ("
					+ getFunctionName( )
					+ " in "
					+ getDLLName( )
					+ ") is already closed" );
		}
	}

	/**
	 * Method allocMemory allocates native block of memory : don't forget to
	 * free it with <code>freeMemory(int)</code>!!!!
	 * 
	 * @param size
	 *            the size of the memory block to reserve in bytes
	 * 
	 * @return the address of the reserved memory (a pointer)
	 * 
	 * @exception NativeException
	 *                if the memory cannot be reserved
	 * 
	 */
	public static int allocMemory( int size ) throws NativeException
	{
		return Malloc( size );
	}

	/**
	 * Method freeMemory try to free the block of memory pointed by
	 * <code>pointer</code><br>
	 * No checks are done to see if the pointer is valid (TODO ?)
	 * 
	 * @param pointer
	 *            the address of a memory block to free
	 * 
	 * @exception NativeException
	 *                if the memory cannon be freed
	 * 
	 */
	public static void freeMemory( int pointer ) throws NativeException
	{
		Free( pointer );
	}

	/**
	 * Method setMemory fills the native memory with the content of
	 * <code>buffer</code> <br>
	 * <b>Be aware that buffer overflows are no checked !!!!</b>
	 * 
	 * @param pointer
	 *            the address of the native memory
	 * @param buffer
	 *            a String to memcpy
	 * 
	 * @exception NativeException
	 * 
	 */
	public static void setMemory( int pointer, String buffer )
			throws NativeException
	{
		setMemory( pointer, buffer.getBytes( ) );
	}

	/**
	 * Method setMemory fills the native memory with the content of
	 * <code>buffer</code> <br>
	 * <b>Be aware that buffer overflows are no checked !!!!</b>
	 * 
	 * @param pointer
	 *            the address of the native memory
	 * @param buffer
	 *            a byte[] to memcpy
	 * 
	 * @exception NativeException
	 * 
	 */
	public static void setMemory( int pointer, byte[] buffer )
			throws NativeException
	{
		setMemory( pointer, buffer, 0, buffer.length );
	}

	/**
	 * Method setMemory fills the native memory with the content of
	 * <code>buffer</code> <br>
	 * <b>Be aware that buffer overflows are no checked !!!!</b>
	 * 
	 * @param pointer
	 *            the address of the native memory
	 * @param buffer
	 *            a byte[] to memcpy
	 * @param offset
	 *            the offset of the native side
	 * @param len
	 *            the number of bytes to copy
	 * 
	 * @exception NativeException
	 * 
	 */
	public static void setMemory( int pointer, byte[] buffer, int offset,
			int len ) throws NativeException
	{
		SetMemory( pointer, buffer, offset, len );
	}

	/**
	 * Method getMemory
	 * 
	 * @param pointer
	 *            the address of the native memory
	 * @param size
	 *            number of bytes to copy
	 * 
	 * @return a copy of the memory at address <code>pointer</code>
	 * 
	 * @exception NativeException
	 * 
	 */
	public static byte[] getMemory( int pointer, int size )
			throws NativeException
	{
		return GetMemory( pointer, size );
	}

	// returns the length of the string the pointer is pointing to
	// might cause high CPU-usage for very large Strings or when used repeatedly
	// in a loop
	public static int getStrLen( int pointer ) throws NativeException
	{
		int counter = 0;
		while ( getMemory( pointer++, 1 )[0] != 0 )
		{
			counter++;
		}
		return counter;
	}

	/**
	 * Method getMemoryAsString
	 * 
	 * @param pointer
	 *            the address of the native char*
	 * 
	 * @return a String copy of the memory at address <code>pointer</code> to
	 *         the next null-terminator (best-effort). More CPU-intensive than
	 *         getMemoryAsString(int pointer, int size) as we are grabbing the
	 *         String byte-by-byte and check each byte for null-termination
	 * 
	 * 
	 * @exception NativeException
	 * 
	 */
	public static String getMemoryAsString( int pointer )
			throws NativeException
	{
		return getMemoryAsString( pointer, getStrLen( pointer ) );
	}

	/**
	 * Method getUnicodeMemoryAsString
	 * 
	 * @param pointer
	 *            the address of the native char*
	 * 
	 * @return a String copy of the memory at address <code>pointer</code> to
	 *         the next null-terminator (best-effort). If the Pointer contains a
	 *         Unicode String use this instead of getMemoryAsString!
	 * 
	 * 
	 * @exception NativeException
	 * 
	 */
	public static String getUnicodeMemoryAsString( int pointer )
			throws NativeException
	{
		StringBuffer s = new StringBuffer( );
		byte[] b;
		while ( ( b = getMemory( pointer, 1 ) )[0] != 0 )
		{
			s.append( new String( b ) );
			pointer += 2;
		}
		return s.toString( );
	}

	/**
	 * Method getMemoryAsString
	 * 
	 * @param pointer
	 *            the address of the native char*
	 * @param size
	 *            number of bytes to copy
	 * 
	 * @return a String copy of the memory at address <code>pointer</code>
	 *         limited by a NULL terminator if the char* is lower than
	 *         </code>size</code> characters
	 * 
	 * @exception NativeException
	 * 
	 */
	public static String getMemoryAsString( int pointer, int size )
			throws NativeException
	{
		byte[] buf = GetMemory( pointer, size );

		for ( int i = 0; i < buf.length; i++ )
		{
			if ( buf[i] == 0 )
			{
				return new String( buf, 0, i );
			}
		}
		return new String( buf );
	}

	// reads the Memory completely without stopping at the first NULL-Terminator
	public static String getMemoryAsString( int pointer, int size,
			boolean readFully ) throws NativeException
	{
		if ( readFully )
		{
			return new String( GetMemory( pointer, size ) );
		}
		return getMemoryAsString( pointer, size );
	}

	/**
	 * Method getCurrentModule
	 * 
	 * @return the HMODULE associated with the swt-extension-jna DLL
	 * 
	 * @exception NativeException
	 * 
	 */
	public static int getCurrentModule( ) throws NativeException
	{
		if ( currentModuleHandle == 0 )
		{
			currentModuleHandle = GetCurrentModule( );
		}
		// check hModule
		if ( currentModuleHandle == 0 )
		{
			try
			{
				currentModuleHandle = Kernel32.LoadLibrary( Native.DLL_NAME )
						.getValue( );
			}
			catch ( IllegalAccessException ex )
			{
				ex.printStackTrace( );
			}
		}
		return currentModuleHandle;
	}

	// returns if a function is exported in the given library

	public static boolean isFunctionExported( String lib, String function )
			throws NativeException, InterruptedException
	{
		String[] funcs = getDLLFileExports( lib );
		if ( funcs != null )
		{
			for ( int i = 0; i < funcs.length; i++ )
			{
				if ( funcs[i].equalsIgnoreCase( function ) )
				{
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Method getDLLFileExports gets all the names of the functions exported by
	 * a library
	 * 
	 * @param dllFile
	 *            the name of a library
	 * @param demangled
	 *            if true Native tries to demangle C++ function names
	 * 
	 * @return a String[] the names of the functions
	 * 
	 * @exception NativeException
	 * @exception IllegalAccessException
	 * 
	 */
	public static String[] getDLLFileExports( String dllFile, boolean demangled )
			throws NativeException, InterruptedException
	{

		try
		{
			HANDLE hFile = Kernel32.CreateFile( dllFile,
					Kernel32.AccessMask.GENERIC_READ,
					Kernel32.ShareMode.FILE_SHARE_READ,
					null,
					Kernel32.CreationDisposition.OPEN_EXISTING,
					Kernel32.FileAttribute.FILE_ATTRIBUTE_NORMAL,
					0 );
			if ( hFile.equals( HANDLE.INVALID_HANDLE_VALUE ) )
			{
				System.err.println( ">>>ERROR<<< : "
						+ dllFile
						+ " file not found, CreateFile returned an invalid handle\n" );
				return null;
			}

			HANDLE hFileMapping = Kernel32.CreateFileMapping( hFile,
					(SECURITY_ATTRIBUTES) null,
					Kernel32.PageAccess.PAGE_READONLY,
					new DWORD( 0 ),
					new DWORD( 0 ),
					(String) null );
			if ( hFileMapping.equals( HANDLE.INVALID_HANDLE_VALUE ) )
			{
				Kernel32.CloseHandle( hFile );
				System.err.println( ">>>ERROR<<< : CreateFileMapping returned a NULL handle\n" );
				return null;
			}

			LONG lpFileBase = Kernel32.MapViewOfFileEx( hFileMapping,
					Kernel32.FileMap.FILE_MAP_READ,
					new DWORD( 0 ),
					new DWORD( 0 ),
					new DWORD( 0 ),
					new LONG( 0 ) );
			if ( lpFileBase.getValue( ) == 0 )
			{
				Kernel32.CloseHandle( hFileMapping );
				Kernel32.CloseHandle( hFile );
				System.err.println( ">>>ERROR<<< : MapViewOfFile returned 0\n" );
				return null;
			}
			int IMAGE_DOS_HEADER_SIZE = 14 * 2 + 4 * 2 + 2 * 2 + 10 * 2 + 4;
			// Composed of Signature + IMAGE_FILE_HEADER +
			// IMAGE_OPTIONAL_HEADER32
			// See
			// http://www.reactos.org/generated/doxygen/d1/d72/struct__IMAGE__OPTIONAL__HEADER32.html
			// for example
			// typedef struct _IMAGE_NT_HEADERS32 {
			// DWORD Signature;
			// IMAGE_FILE_HEADER FileHeader;
			// IMAGE_OPTIONAL_HEADER32 OptionalHeader;
			// }
			int IMAGE_NUMBEROF_DIRECTORY_ENTRIES = 16;
			// int IMAGE_DIRECTORY_ENTRY_EXPORT = 0;
			int IMAGE_OPTIONAL_HEADER32_SIZE = 24
					* 4
					+ IMAGE_NUMBEROF_DIRECTORY_ENTRIES
					* 2
					* 4;
			int IMAGE_NT_HEADERS32_SIZE = 4 + 5 * 4 + IMAGE_OPTIONAL_HEADER32_SIZE;
			int IMAGE_NT_SIGNATURE = 0x00004550;
			int IMAGE_EXPORT_DIRECTORY_SIZE = 10 * 4;
			Pointer pImg_DOS_Header = new Pointer( new NativeMemoryBlock( lpFileBase.getValue( ),
					IMAGE_DOS_HEADER_SIZE ) );

			Pointer pImg_NT_Header = new Pointer( new NativeMemoryBlock( pImg_DOS_Header.getPointer( )
					+ pImg_DOS_Header.getAsInt( IMAGE_DOS_HEADER_SIZE - 4 ),
					IMAGE_NT_HEADERS32_SIZE ) );

			if ( !Kernel32.IsBadReadPtr( pImg_NT_Header )
					|| pImg_NT_Header.getAsInt( 0 ) != IMAGE_NT_SIGNATURE )
			{
				Kernel32.UnmapViewOfFile( lpFileBase );
				Kernel32.CloseHandle( hFileMapping );
				Kernel32.CloseHandle( hFile );
				System.err.println( ">>>ERROR<<< : IsBadReadPtr returned false, pointer is "
						+ pImg_NT_Header.getPointer( )
						+ "\n" );
				return null;
			}
			System.err.println( ">>>INFO<<< : IsBadReadPtr returned true, pointer is "
					+ pImg_NT_Header.getPointer( )
					+ "\n" );
			// IMAGE_OPTIONAL_HEADER32
			Pointer pOptionalHeader = new Pointer( new NativeMemoryBlock( pImg_NT_Header.getPointer( )
					+ IMAGE_NT_HEADERS32_SIZE
					- IMAGE_OPTIONAL_HEADER32_SIZE,
					IMAGE_OPTIONAL_HEADER32_SIZE ) );
			// (IMAGE_EXPORT_DIRECTORY)

			Pointer pImg_Export_Dir = new Pointer( new NativeMemoryBlock( pOptionalHeader.getAsInt( 24 * 4 ),
					4 ) );
			if ( pImg_Export_Dir.isNull( ) )
			{
				Kernel32.UnmapViewOfFile( lpFileBase );
				Kernel32.CloseHandle( hFileMapping );
				Kernel32.CloseHandle( hFile );
				System.err.println( ">>>ERROR<<< : pImg_Export_Dir is NULL\n" );
				return null;
			}
			pImg_Export_Dir = new Pointer( new NativeMemoryBlock( DbgHelp.ImageRvaToVa( pImg_NT_Header,
					pImg_DOS_Header,
					pImg_Export_Dir.asLONG( ),
					NullPointer.NULL )
					.getValue( ),
					IMAGE_EXPORT_DIRECTORY_SIZE ) );

			LONG ppdwNames = new LONG( pImg_Export_Dir.getAsInt( 8 * 4/* AddressOfNames */) );
			ppdwNames = DbgHelp.ImageRvaToVa( pImg_NT_Header,
					pImg_DOS_Header,
					ppdwNames,
					NullPointer.NULL );
			if ( ppdwNames.getValue( ) == 0 )
			{
				Kernel32.UnmapViewOfFile( lpFileBase );
				Kernel32.CloseHandle( hFileMapping );
				Kernel32.CloseHandle( hFile );
				System.err.println( ">>>ERROR<<< : ImageRvaToVa returned NULL\n" );
				return null;
			}

			int iNoOfExports = pImg_Export_Dir.getAsInt( 6 * 4/* NumberOfNames */);
			String[] pszFunctions = new String[iNoOfExports];

			System.err.println( "pszFunctions = " + pszFunctions.length + "\n" );

			for ( int i = 0, ippdwNames = ppdwNames.getValue( ); i < iNoOfExports; i++, ippdwNames += 4 )
			{
				System.err.println( "ippdwNames[" + i + "] : " + ippdwNames );
				LONG szFunc = DbgHelp.ImageRvaToVa( pImg_NT_Header,
						pImg_DOS_Header,
						new LONG( new Pointer( new NativeMemoryBlock( ippdwNames,
								4 ) ).getAsInt( 0 ) ),
						NullPointer.NULL );
				pszFunctions[i] = new Pointer( new NativeMemoryBlock( szFunc.getValue( ),
						1000 ) ).getAsString( ).trim( );

				System.err.println( pszFunctions[i] + "\n" );
			}
			Kernel32.UnmapViewOfFile( lpFileBase );
			Kernel32.CloseHandle( hFileMapping );
			Kernel32.CloseHandle( hFile );
			return pszFunctions;

		}
		catch ( IllegalAccessException e )
		{
			e.printStackTrace( );
			return null;
		}
	}

	// copies source-pointer memory to dest-pointer
	// if dest is not large enough only the portion of source is copied that
	// fits into dest
	public static void copyMemory( Pointer source, Pointer dest )
			throws NativeException
	{
		if ( dest == null
				|| source == null
				|| source.getSize( ) == 0
				|| dest.getSize( ) == 0 )
		{
			return;
		}
		if ( dest.getSize( ) >= source.getSize( ) )
		{
			dest.setMemory( source.getMemory( ) );
		}
		else
		{
			dest.setMemory( Native.getMemory( source.getPointer( ),
					dest.getSize( ) ) );
		}
	}

	/**
	 * Method getDLLFileExports gets all the names of the functions exported by
	 * a library
	 * 
	 * @param dllFile
	 *            the name of a library
	 * 
	 * @return a String[] the names of the functions
	 * 
	 * @exception NativeException
	 * 
	 */
	public static String[] getDLLFileExports( String dllFile )
			throws NativeException, InterruptedException
	{
		return getDLLFileExports( dllFile, false );
	}

	public static int callback( int address, long[] values )
	{
		final Callback c = (Callback) mCallbacks.get( new Integer( address ) );
		if ( c != null )
		{
			return c.callback( values );
		}
		return -1;
	}

	/**
	 * Method createCallback when a callback is no more used you should/must
	 * release it with <code>releaseCallback</code>
	 * 
	 * <b>Note:</b>It only works on jdk1.5 or later.
	 * 
	 * @param numParams
	 *            the number of parameters the callback function sould receive
	 * @param callback
	 *            a Callback object that will handle the callback
	 * 
	 * @return the native handle of the callback function (this is function
	 *         pointer)
	 * 
	 * @exception NativeException
	 *                if something goes wrong
	 * 
	 */
	public static int createCallback( int numParams, Callback callback )
			throws NativeException
	{
		if ( "1.5".compareTo( System.getProperty( "java.version" ) ) > 0 )
		{
			throw new Error( "Invalid Java Version." );
		}
		int address = CreateCallBack( numParams );
		mCallbacks.put( new Integer( address ), callback );
		return address;
	}

	/**
	 * Method releaseCallback releases a callback previously created with
	 * <code>createCallback</code>
	 * 
	 * @param callback
	 *            a Callback
	 * 
	 * @return true is this callback was released sucessfully
	 * 
	 * @exception NativeException
	 * 
	 */
	public static boolean releaseCallback( Callback callback )
			throws NativeException
	{
		boolean ret = false;
		if ( null != mCallbacks.remove( new Integer( callback.getCallbackAddress( ) ) ) )
		{
			ret = ReleaseCallBack( callback.getCallbackAddress( ) );
		}
		return ret;
	}

	/**
	 * Method registerWindowProc register a WindowProc for the Window
	 * <code>hwnd</code>
	 * 
	 * @param hwnd
	 *            the HANDLE of the window
	 * @param proc
	 *            a WindowProc object that be called by native events
	 * 
	 * @return the previous function pointer used as a WindowProc for this
	 *         window.
	 * 
	 * @exception NativeException
	 *                if the SetWindowLongPtr fails or somthing weird happens
	 *                (can crash too ;) )...
	 * 
	 */
	public static int registerWindowProc( int hwnd, WindowProc proc )
			throws NativeException
	{
		return RegisterWindowProc( hwnd, proc );
	}

	/**
	 * Method registerWindowProc register a WindowProc for the Window.<br>
	 * Calling this method is equivalent to call <br>
	 * <i>registerWindowProc(hwnd.getValue(), proc)</i><br>
	 * 
	 * @param hwnd
	 *            the HANDLE of the window
	 * @param proc
	 *            a WindowProc object that be called by native events
	 * 
	 * @return the previous function pointer used as a WindowProc for this
	 *         window.
	 * 
	 * @exception NativeException
	 *                if the SetWindowLongPtr fails or somthing weird happens
	 *                (can crash too ;) )...
	 * 
	 */
	public static int registerWindowProc( HWND hwnd, WindowProc proc )
			throws NativeException
	{
		return RegisterWindowProc( hwnd.getValue( ), proc );
	}

	/**
	 * The size of a native pointer (<code>void*</code>) on the current
	 * platform, in bytes.
	 */
	public static final int POINTER_SIZE;
	/** Size of a native <code>long</code> type, in bytes. */
	public static final int LONG_SIZE;
	/** Size of a native <code>wchar_t</code> type, in bytes. */
	public static final int WCHAR_SIZE;
	/** Size of a native <code>size_t</code> type, in bytes. */
	public static final int SIZE_T_SIZE;

	private static final int TYPE_VOIDP = 0;
	private static final int TYPE_LONG = 1;
	private static final int TYPE_WCHAR_T = 2;
	private static final int TYPE_SIZE_T = 3;

	static
	{
		POINTER_SIZE = Sizeof( TYPE_VOIDP );
		LONG_SIZE = Sizeof( TYPE_LONG );
		WCHAR_SIZE = Sizeof( TYPE_WCHAR_T );
		SIZE_T_SIZE = Sizeof( TYPE_SIZE_T );
	}

	/**
	 * Obtain a Java String from the given native byte array. If there is no NUL
	 * terminator, the String will comprise the entire array. If the system
	 * property <code>jna.encoding</code> is set, its value will override the
	 * platform default encoding (if supported).
	 */
	public static String toString( byte[] buf )
	{
		return toString( buf, System.getProperty( "jna.encoding" ) );
	}

	/**
	 * Obtain a Java String from the given native byte array, using the given
	 * encoding. If there is no NUL terminator, the String will comprise the
	 * entire array. If the <code>encoding</code> parameter is null, the
	 * platform default encoding will be used.
	 */
	public static String toString( byte[] buf, String encoding )
	{
		String s = null;
		if ( encoding != null )
		{
			try
			{
				s = new String( buf, encoding );
			}
			catch ( UnsupportedEncodingException e )
			{
			}
		}
		if ( s == null )
		{
			s = new String( buf );
		}
		int term = s.indexOf( 0 );
		if ( term != -1 )
			s = s.substring( 0, term );
		return s;
	}

	/**
	 * Obtain a Java String from the given native wchar_t array. If there is no
	 * NUL terminator, the String will comprise the entire array.
	 */
	public static String toString( char[] buf )
	{
		String s = new String( buf );
		int term = s.indexOf( 0 );
		if ( term != -1 )
			s = s.substring( 0, term );
		return s;
	}

	// No String.replace available in 1.4
	public static String replace( String s1, String s2, String str )
	{
		StringBuffer buf = new StringBuffer( );
		while ( true )
		{
			int idx = str.indexOf( s1 );
			if ( idx == -1 )
			{
				buf.append( str );
				break;
			}
			else
			{
				buf.append( str.substring( 0, idx ) );
				buf.append( s2 );
				str = str.substring( idx + s1.length( ) );
			}
		}
		return buf.toString( );
	}

	static native int Sizeof( int type );

	private native int LoadLibrary( String dllName, String funcPointer )
			throws NativeException;

	private native int FindFunction( int libHandle, String funcPointer )
			throws NativeException;

	private native void Invoke( int pointer ) throws NativeException;

	private native void Dispose( int pointer ) throws NativeException;

	private static native int Malloc( int size ) throws NativeException;

	private static native void Free( int pointer ) throws NativeException;

	private static native void SetMemory( int pointer, byte[] buff, int offset,
			int len ) throws NativeException;

	private static native byte[] GetMemory( int pointer, int len )
			throws NativeException;

	private static native int GetCurrentModule( ) throws NativeException;

	private static native int CreateCallBack( int numParams )
			throws NativeException;

	private static native boolean ReleaseCallBack( int pos )
			throws NativeException;

	private static native int RegisterWindowProc( int hwnd, Object winProc )
			throws NativeException;
}
