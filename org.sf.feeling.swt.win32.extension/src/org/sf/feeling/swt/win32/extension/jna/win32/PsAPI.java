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
package org.sf.feeling.swt.win32.extension.jna.win32;

import org.sf.feeling.swt.win32.extension.jna.Native;
import org.sf.feeling.swt.win32.extension.jna.Type;
import org.sf.feeling.swt.win32.extension.jna.datatype.LONG;
import org.sf.feeling.swt.win32.extension.jna.datatype.win32.DWORD;
import org.sf.feeling.swt.win32.extension.jna.datatype.win32.HANDLE;
import org.sf.feeling.swt.win32.extension.jna.exception.NativeException;
import org.sf.feeling.swt.win32.extension.jna.ptr.NullPointer;
import org.sf.feeling.swt.win32.extension.jna.ptr.Pointer;
import org.sf.feeling.swt.win32.extension.jna.ptr.memory.MemoryBlockFactory;

public class PsAPI
{

	public static final String DLL_NAME = "PSAPI.DLL";

	/**
	 * <p>
	 * The EnumProcesses function retrieves the process identifier for each
	 * process object in the system.
	 * </p>
	 * 
	 * <pre>
	 * 	 BOOL EnumProcesses(
	 * 	 DWORD* pProcessIds,
	 * 	 DWORD cb,
	 * 	 DWORD* pBytesReturned
	 * 	 );
	 * 
	 * 
	 * 
	 * 	 Parameters
	 * 
	 * 	 pProcessIds
	 * 	 [out] Pointer to an array that receives the list of process identifiers.
	 * 	 cb
	 * 	 [in] Size of the pProcessIds array, in bytes.
	 * 	 pBytesReturned
	 * 	 [out] Number of bytes returned in the pProcessIds array.
	 * 
	 * 	 Return Values
	 * 
	 * 	 If the function succeeds, the return value is nonzero.
	 * 
	 * 	 If the function fails, the return value is zero. To get extended error information, call GetLastError.
	 * 	 Remarks
	 * 
	 * 	 It is a good idea to use a large array, because it is hard to predict how many processes there will be at the time you call EnumProcesses.
	 * 
	 * 	 To determine how many processes were enumerated, divide the pBytesReturned value by sizeof(DWORD). There is no indication given when the buffer is too small to store all process identifiers. Therefore, if pBytesReturned equals cb, consider retrying the call with a larger array.
	 * 
	 * 	 To obtain process handles for the processes whose identifiers you have just obtained, call the OpenProcess function.
	 * 
	 * </pre>
	 * 
	 * </p>
	 */
	public static boolean EnumProcesses( int pProcessIds, DWORD cb,
			DWORD pBytesReturned ) throws NativeException,
			IllegalAccessException
	{
		Native nEnumProcess = new Native( DLL_NAME, "EnumProcesses" );
		nEnumProcess.setRetVal( Type.INT );

		nEnumProcess.setParameter( 0, pProcessIds );
		nEnumProcess.setParameter( 1, cb.getValue( ) );
		nEnumProcess.setParameter( 2, pBytesReturned.getPointer( ).getPointer( ) );

		nEnumProcess.invoke( );
		return nEnumProcess.getRetValAsInt( ) != 0;

	}

	// You might want to use WindowsUtils.enumerateProcesses(false) instead
	public static int[] EnumProcess( int maxSizeOfInitialBuffer )
			throws NativeException, IllegalAccessException
	{
		if ( maxSizeOfInitialBuffer < 4 )
		{
			maxSizeOfInitialBuffer = 4;
		}
		Pointer pProcessIds = new Pointer( MemoryBlockFactory.createMemoryBlock( maxSizeOfInitialBuffer ) );

		DWORD nReadProcess = new DWORD( 0 );

		if ( !EnumProcesses( pProcessIds.getPointer( ),
				new DWORD( maxSizeOfInitialBuffer ),
				nReadProcess ) )
		{
			return null;
		}
		else
		{
			int sizeNeeded = nReadProcess.getValue( );
			// To determine how many processes were enumerated, divide the
			// pBytesReturned value by sizeof(DWORD).
			// !!!!! There is no indication given when the buffer is too small
			// to store all process identifiers. Therefore, if pBytesReturned
			// equals cb, consider retrying the call with a larger array.!!!!
			if ( sizeNeeded >= maxSizeOfInitialBuffer )
			{
				System.err.println( maxSizeOfInitialBuffer
						+ " is to low, will recall with "
						+ ( sizeNeeded + 1024 ) );
				return EnumProcess( sizeNeeded + 1024 );
			}
			else
			{
				int numProcess = nReadProcess.getValue( ) / 4;
				int[] process = new int[numProcess];
				for ( int i = 0; i < numProcess; i++ )
				{
					process[i] = pProcessIds.getAsInt( i * 4 );
				}
				pProcessIds.dispose( );
				return process;
			}
		}
	}

	/**
	 * <pre>
	 * 	 Retrieves a handle for each module in the specified process.
	 * 
	 * 	 BOOL WINAPI EnumProcessModules(
	 * 	 HANDLE hProcess,
	 * 	 HMODULE* lphModule,
	 * 	 DWORD cb,
	 * 	 LPDWORD lpcbNeeded
	 * 	 );
	 * 
	 * 	 Parameters
	 * 
	 * 	 hProcess
	 * 	 [in] A handle to the process.
	 * 	 lphModule
	 * 	 [out] An array that receives the list of module handles.
	 * 	 cb
	 * 	 [in] The size of the lphModule array, in bytes.
	 * 	 lpcbNeeded
	 * 	 [out] The number of bytes required to store all module handles in the lphModule array.
	 * 
	 * 	 Return Values
	 * 
	 * 	 If the function succeeds, the return value is nonzero.
	 * 
	 * 	 If the function fails, the return value is zero. To get extended error information, call GetLastError.
	 * </pre>
	 * 
	 */
	public static Pointer EnumProcessModules( HANDLE hProcessHandle,
			int maxSizeOfInitialBuffer ) throws NativeException,
			IllegalAccessException
	{
		if ( maxSizeOfInitialBuffer < 4 )
		{
			maxSizeOfInitialBuffer = 4;
		}

		Native nEnumProcessModules = new Native( DLL_NAME, "EnumProcessModules" );
		nEnumProcessModules.setRetVal( Type.INT );

		LONG lpcbNeeded = new LONG( 0 );
		Pointer lphModule = new Pointer( MemoryBlockFactory.createMemoryBlock( maxSizeOfInitialBuffer ) );

		nEnumProcessModules.setParameter( 0, hProcessHandle.getValue( ) );
		nEnumProcessModules.setParameter( 1, lphModule );
		nEnumProcessModules.setParameter( 2, maxSizeOfInitialBuffer );
		nEnumProcessModules.setParameter( 3, lpcbNeeded.getPointer( ) );

		nEnumProcessModules.invoke( );

		int sizeNeeded = lpcbNeeded.getValue( );

		if ( nEnumProcessModules.getRetValAsInt( ) == 0 )
		{
			lphModule.dispose( );
			return NullPointer.NULL;
		}
		if ( sizeNeeded > maxSizeOfInitialBuffer )
		{
			lphModule.dispose( );
			System.err.println( maxSizeOfInitialBuffer
					+ " is to low, will recall with "
					+ sizeNeeded );
			return EnumProcessModules( hProcessHandle, sizeNeeded );
		}
		else
		{
			return lphModule;
		}
	}

	/**
	 * 
	 * <pre>
	 *  Retrieves the base name of the specified module.
	 * 
	 * 	 DWORD WINAPI GetModuleBaseName(
	 * 	 HANDLE hProcess,
	 * 	 HMODULE hModule,
	 * 	 LPTSTR lpBaseName,
	 * 	 DWORD nSize
	 * 	 );
	 * 
	 * 	 Parameters
	 * 
	 * 	 hProcess
	 * 	 [in] Handle to the process that contains the module. To specify the current process, use GetCurrentProcess.
	 * 
	 * 	 The handle must have the PROCESS_QUERY_INFORMATION and PROCESS_VM_READ access rights. For more information, see Process Security and Access Rights.
	 * 	 hModule
	 * 	 [in] Handle to the module. If this parameter is NULL, this function returns the name of the file used to create the calling process.
	 * 	 lpBaseName
	 * 	 [out] Pointer to the buffer that receives the base name of the module. If the base name is longer than maximum number of characters specified by the nSize parameter, the base name is truncated.
	 * 	 nSize
	 * 	 [in] Size of the lpBaseName buffer, in characters.
	 * 
	 * 	 Return Values
	 * 
	 * 	 If the function succeeds, the return value specifies the length of the string copied to the buffer, in characters.
	 * 
	 * 	 If the function fails, the return value is zero. To get extended error information, call GetLastError.
	 * </pre>
	 */

	public static String GetModuleBaseName( HANDLE hProcessHandle, int hModule,
			int nSize ) throws NativeException, IllegalAccessException
	{
		Native nGetModuleBaseName = new Native( DLL_NAME, "GetModuleBaseNameA" );
		nGetModuleBaseName.setRetVal( Type.INT );

		Pointer lpBaseName = new Pointer( MemoryBlockFactory.createMemoryBlock( nSize + 1 ) );

		nGetModuleBaseName.setParameter( 0, hProcessHandle.getValue( ) );
		nGetModuleBaseName.setParameter( 1, hModule );
		nGetModuleBaseName.setParameter( 2, lpBaseName );
		nGetModuleBaseName.setParameter( 3, nSize );
		nGetModuleBaseName.invoke( );
		String ret = null;
		if ( nGetModuleBaseName.getRetValAsInt( ) != 0 )
		{
			ret = lpBaseName.getAsString( );
		}
		lpBaseName.dispose( );
		return ret;
	}
}
