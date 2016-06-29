/*******************************************************************************
 * Copyright (c) 2010 cnfree.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  cnfree  - initial API and implementation
 *******************************************************************************/

package org.sf.feeling.swt.win32.extension.system;

import java.util.List;

import org.sf.feeling.swt.win32.extension.jna.datatype.win32.DWORD;
import org.sf.feeling.swt.win32.extension.jna.datatype.win32.HANDLE;
import org.sf.feeling.swt.win32.extension.jna.exception.NativeException;
import org.sf.feeling.swt.win32.extension.jna.ptr.Pointer;
import org.sf.feeling.swt.win32.extension.jna.win32.Kernel32;
import org.sf.feeling.swt.win32.extension.jna.win32.structure.PROCESSENTRY32;
import org.sf.feeling.swt.win32.internal.extension.Extension;
import org.sf.feeling.swt.win32.internal.extension.Extension2;

public class Kernel
{

	/**
	 * Specifies all possible access flags for the process object.
	 */
	public static final int PROCESS_ALL_ACCESS = 0x1F0FFF;

	/**
	 * Enables using the process handle in the CreateRemoteThread function to
	 * create a thread in the process.
	 */
	public static final int PROCESS_CREATE_THREAD = 0x2;

	/**
	 * Enables using the process handle as either the source or target process
	 * in the DuplicateHandle function to duplicate a handle.
	 */
	public static final int PROCESS_DUP_HANDLE = 0x40;

	/**
	 * Enables using the process handle in the GetExitCodeProcess and
	 * GetPriorityClass functions to read information from the process object.
	 */
	public static final int PROCESS_QUERY_INFORMATION = 0x400;

	/**
	 * Enables using the process handle in the SetPriorityClass function to set
	 * the priority class of the process.
	 */
	public static final int PROCESS_SET_INFORMATION = 0x200;

	/**
	 * Enables using the process handle in the TerminateProcess function to
	 * terminate the process.
	 */
	public static final int PROCESS_TERMINATE = 0x1;

	/**
	 * Enables using the process handle in the VirtualProtectEx and
	 * WriteProcessMemory functions to modify the virtual memory of the process.
	 */
	public static final int PROCESS_VM_OPERATION = 0x8;

	/**
	 * Enables using the process handle in the ReadProcessMemory function to
	 * read from the virtual memory of the process.
	 */
	public static final int PROCESS_VM_READ = 0x10;

	/**
	 * Enables using the process handle in the WriteProcessMemory function to
	 * write to the virtual memory of the process.
	 */
	public static final int PROCESS_VM_WRITE = 0x20;

	/**
	 * Enables using the process handle in any of the wait functions to wait for
	 * the process to terminate.
	 */
	public static final int SYNCHRONIZE = 0x100000;

	public static final int SE_BACKUP_PRIVILEGE = 17;
	public static final int SE_RESTORE_PRIVILEGE = 18;
	public static final int SE_SHUTDOWN_PRIVILEGE = 19;
	public static final int SE_DEBUG_PRIVILEGE = 20;

	public static final int PAGE_NOACCESS = 0x01;
	public static final int PAGE_READONLY = 0x02;
	public static final int PAGE_READWRITE = 0x04;
	public static final int PAGE_WRITECOPY = 0x08;
	public static final int PAGE_EXECUTE = 0x10;
	public static final int PAGE_EXECUTE_READ = 0x20;
	public static final int PAGE_EXECUTE_READWRITE = 0x40;
	public static final int PAGE_EXECUTE_WRITECOPY = 0x80;
	public static final int PAGE_GUARD = 0x100;
	public static final int PAGE_NOCACHE = 0x200;
	public static final int PAGE_WRITECOMBINE = 0x400;

	public static int openProcess( int desiredAccess, boolean inheritHandle,
			int processId )
	{
		return Extension2.OpenProcess( desiredAccess, inheritHandle, processId );
	}

	public static int openProcess( int processId )
	{
		return openProcess( PROCESS_ALL_ACCESS, false, processId );
	}

	public static boolean enablePrivilege( int privilege )
	{
		return enablePrivilege( privilege, true, false );
	}

	public static boolean enablePrivilege( int privilege, boolean enable,
			boolean currentThread )
	{
		return Extension2.EnablePrivilege( privilege, enable, currentThread );
	}

	public static boolean killProcess( int processId )
	{
		if ( enablePrivilege( SE_DEBUG_PRIVILEGE ) )
		{
			int hProcess = openProcess( PROCESS_TERMINATE, false, processId );
			if ( hProcess == 0 )
				return false;
			boolean result = Extension2.TerminateProcess( hProcess, 0 );
			Extension.CloseHandle( hProcess );
			return result;
		}
		else
			return false;
	}

	public static boolean writeProcessMemory( int hProcess, int inBaseAddress,
			int inputBufferPoint, int size )
	{
		return Extension2.WriteProcessMemory( hProcess,
				inBaseAddress,
				inputBufferPoint,
				size );
	}

	public static boolean readProcessMemory( int hProcess, int inBaseAddress,
			int outputBufferPoint, int size )
	{
		return Extension2.ReadProcessMemory( hProcess,
				inBaseAddress,
				outputBufferPoint,
				size );
	}

	public static boolean virtualProtectEx( int hProcess, int inBaseAddress,
			int size, int privilege )
	{
		return Extension2.VirtualProtectEx( hProcess,
				inBaseAddress,
				size,
				privilege );
	}

	public static ProcessEntry[] getSystemProcessesSnap( )
	{
		List processInfos = Extension.GetSystemProcessesSnap( );
		return (ProcessEntry[]) processInfos.toArray( new ProcessEntry[0] );
	}

	public static ModuleEntry[] getProcessModulesSnap( int processId )
	{
		List moduleEntries = Extension.GetProcessModulesSnap( processId );
		return (ModuleEntry[]) moduleEntries.toArray( new ModuleEntry[0] );
	}

	public static ThreadEntry[] getProcessThreadsSnap( int processId )
	{
		List threadEntries = Extension.GetProcessThreadsSnap( processId );
		return (ThreadEntry[]) threadEntries.toArray( new ThreadEntry[0] );
	}

	public static boolean setProcessPriority( String processName, int priority )
			throws NativeException, IllegalAccessException
	{
		if ( processName == null )
		{
			return false;
		}

		boolean ret = false;
		int dwProcessId = getProcessId( processName );
		if ( dwProcessId != 0 )
		{
			HANDLE hProc = Kernel32.OpenProcess( Kernel32.PROCESS_ALL_ACCESS,
					false,
					dwProcessId );
			if ( hProc.getValue( ) != 0 )
			{
				ret = Kernel32.SetPriorityClass( hProc, new DWORD( priority ) );
			}
			Kernel32.CloseHandle( hProc );
		}

		return ret;
	}

	public static final int getProcessId( String szExeName )
			throws NativeException, IllegalAccessException
	{
		if ( szExeName != null )
		{
			PROCESSENTRY32 Pc = new PROCESSENTRY32( );

			HANDLE hSnapshot = Kernel32.CreateToolhelp32Snapshot( Kernel32.TH32CS_SNAPALL,
					0 );
			if ( hSnapshot.getValue( ) != 0 )
			{
				if ( Kernel32.Process32First( hSnapshot, Pc ) )
				{
					do
					{
						if ( Pc.getValue( ).szExeFile.equalsIgnoreCase( szExeName ) )
						{
							return Pc.th32ProcessID.getValue( );
						}
						Pc.resetPointer( );
					} while ( Kernel32.Process32Next( hSnapshot, Pc ) );
				}
			}
			Kernel32.CloseHandle( hSnapshot );
		}
		return 0;
	}

	public static boolean attachDllToProcess( String szModuleName,
			String szProcessName ) throws NativeException,
			IllegalAccessException
	{
		int dwProcessId = getProcessId( szProcessName );
		if ( dwProcessId != 0 )
		{
			HANDLE hProc = Kernel32.OpenProcess( Kernel32.PROCESS_ALL_ACCESS,
					false,
					dwProcessId );
			try
			{
				if ( hProc.getValue( ) != 0 )
				{
					int AllocSpace = Kernel32.VirtualAllocEx( hProc,
							0,
							szModuleName.length( ),
							new DWORD( Kernel32.MEM_COMMIT ),
							new DWORD( Kernel32.PAGE_READWRITE ) );
					if ( Kernel32.WriteProcessMemory( hProc,
							AllocSpace,
							Pointer.createPointerFromString( szModuleName ),
							szModuleName.length( ) ) )
					{
						HANDLE thread = Kernel32.CreateRemoteThread( hProc,
								null,
								0,
								Kernel32.GetProcAddress( new HANDLE( Kernel32.GetModuleHandle( "Kernel32" ) ),
										"LoadLibraryA" ),
								AllocSpace,
								new DWORD( 0 ),
								new DWORD( 0 ) );
						try
						{
							if ( thread.getValue( ) != 0 )
							{
								return true;
							}
						}
						finally
						{
							Kernel32.VirtualFreeEx( hProc,
									AllocSpace,
									0,
									new DWORD( Kernel32.MEM_RELEASE ) );
							Kernel32.CloseHandle( thread );
						}
					}
				}
			}
			finally
			{
				Kernel32.CloseHandle( hProc );
			}
		}
		return false;
	}
}
