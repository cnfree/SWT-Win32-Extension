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

import java.util.ArrayList;

import org.sf.feeling.swt.win32.extension.jna.Native;
import org.sf.feeling.swt.win32.extension.jna.Type;
import org.sf.feeling.swt.win32.extension.jna.datatype.LONG;
import org.sf.feeling.swt.win32.extension.jna.datatype.win32.DWORD;
import org.sf.feeling.swt.win32.extension.jna.datatype.win32.HANDLE;
import org.sf.feeling.swt.win32.extension.jna.exception.NativeException;
import org.sf.feeling.swt.win32.extension.jna.ptr.NullPointer;
import org.sf.feeling.swt.win32.extension.jna.ptr.Pointer;
import org.sf.feeling.swt.win32.extension.jna.ptr.memory.MemoryBlockFactory;
import org.sf.feeling.swt.win32.extension.jna.win32.structure.TOKEN_PRIVILEGES;

public class Advapi32
{

	public static final String DLL_NAME = "Advapi32.dll";
	private static String mLastErrorCode = "0";
	public static final int STANDARD_RIGHTS_REQUIRED = 0xF0000;
	public static final int SC_MANAGER_CONNECT = 0x1;
	public static final int SC_MANAGER_CREATE_SERVICE = 0x2;
	public static final int SC_MANAGER_ENUMERATE_SERVICE = 0x4;
	public static final int SC_MANAGER_LOCK = 0x8;
	public static final int SC_MANAGER_MODIFY_BOOT_CONFIG = 0x20;
	public static final int SC_MANAGER_QUERY_LOCK_STATUS = 0x10;
	public static final int SC_MANAGER_ALL_ACCESS = ( STANDARD_RIGHTS_REQUIRED
			| SC_MANAGER_CONNECT
			| SC_MANAGER_CREATE_SERVICE
			| SC_MANAGER_ENUMERATE_SERVICE
			| SC_MANAGER_LOCK
			| SC_MANAGER_QUERY_LOCK_STATUS | SC_MANAGER_MODIFY_BOOT_CONFIG );

	/** File system driver service. */
	public static final int SERVICE_FILE_SYSTEM_DRIVER = 0x00000002;
	/** Driver service. */
	public static final int SERVICE_KERNEL_DRIVER = 0x00000001;
	/** Service that runs in its own process. */
	public static final int SERVICE_WIN32_OWN_PROCESS = 0x00000010;

	/**
	 * Service that shares a process with one or more other services. For more
	 * information, see Service Programs.
	 */
	public static final int SERVICE_WIN32_SHARE_PROCESS = 0x00000020;
	/**
	 * The service can interact with the desktop.
	 */
	public static final int SERVICE_INTERACTIVE_PROCESS = 0x00000100;
	/**
	 * A service started automatically by the service control manager during
	 * system startup. For more information, see Automatically Starting
	 * Services.
	 */
	public static final int SERVICE_AUTO_START = 0x00000002;
	/**
	 * A device driver started by the system loader. This value is valid only
	 * for driver services.
	 */
	public static final int SERVICE_BOOT_START = 0x00000000;
	/**
	 * A service started by the service control manager when a process calls the
	 * StartService function. For more information, see Starting Services on
	 * Demand.
	 */
	public static final int SERVICE_DEMAND_START = 0x00000003;
	/**
	 * A service that cannot be started. Attempts to start the service result in
	 * the error code ERROR_SERVICE_DISABLED.
	 */
	public static final int SERVICE_DISABLED = 0x00000004;
	/**
	 * A device driver started by the IoInitSystem function. This value is valid
	 * only for driver services. For more information, see Interactive Services.
	 */
	public static final int SERVICE_SYSTEM_START = 0x00000001;
	/**
	 * The startup program logs the error in the event log, if possible. If the
	 * last-known-good configuration is being started, the startup operation
	 * fails. Otherwise, the system is restarted with the last-known good
	 * configuration.
	 */
	public static final int SERVICE_ERROR_CRITICAL = 0x00000003;
	/**
	 * The startup program ignores the error and continues the startup
	 * operation.
	 */
	public static final int SERVICE_ERROR_IGNORE = 0x00000000;
	/**
	 * The startup program logs the error in the event log but continues the
	 * startup operation.
	 */
	public static final int SERVICE_ERROR_NORMAL = 0x00000001;
	/**
	 * The startup program logs the error in the event log. If the
	 * last-known-good configuration is being started, the startup operation
	 * continues. Otherwise, the system is restarted with the last-known-good
	 * configuration.
	 */
	public static final int SERVICE_ERROR_SEVERE = 0x00000002;
	/**
	 * The reason code is defined by the user. If this flag is not present, the
	 * reason code is defined by the system. If this flag is specified with a
	 * system reason code, the function call fails. Users can create custom
	 * major reason codes in the range SERVICE_STOP_REASON_MAJOR_MIN_CUSTOM
	 * (0x00400000) through SERVICE_STOP_REASON_MAJOR_MAX_CUSTOM (0x00ff0000)
	 * and minor reason codes in the range SERVICE_STOP_REASON_MINOR_MIN_CUSTOM
	 * (0x00000100) through SERVICE_STOP_REASON_MINOR_MAX_CUSTOM (0x0000FFFF).
	 */
	public static final int SERVICE_STOP_CUSTOM = 0x20000000;

	/**
	 * The service stop was planned.
	 */
	public static final int SERVICE_STOP_PLANNED = 0x40000000;

	/**
	 * The service stop was not planned.
	 */
	public static final int SERVICE_STOP_UNPLANNED = 0x10000000;

	/**
	 * Notifies a paused service that it should resume. The hService handle must
	 * have the SERVICE_PAUSE_CONTINUE access right.
	 */
	public static final int SERVICE_CONTROL_CONTINUE = 0x00000003;
	/**
	 * Notifies a service that it should report its current status information
	 * to the service control manager. The hService handle must have the
	 * SERVICE_INTERROGATE access right. Note that this control is not generally
	 * useful as the SCM is aware of the current state of the service.
	 */
	public static final int SERVICE_CONTROL_INTERROGATE = 0x00000004;
	/**
	 * Notifies a network service that there is a new component for binding. The
	 * hService handle must have the SERVICE_PAUSE_CONTINUE access right.
	 * However, this control code has been deprecated; use Plug and Play
	 * functionality instead.
	 */
	public static final int SERVICE_CONTROL_NETBINDADD = 0x00000007;
	/**
	 * Notifies a network service that one of its bindings has been disabled.
	 * The hService handle must have the SERVICE_PAUSE_CONTINUE access right.
	 * However, this control code has been deprecated; use Plug and Play
	 * functionality instead.
	 */
	public static final int SERVICE_CONTROL_NETBINDDISABLE = 0x0000000A;
	/**
	 * Notifies a network service that a disabled binding has been enabled. The
	 * hService handle must have the SERVICE_PAUSE_CONTINUE access right.
	 * However, this control code has been deprecated; use Plug and Play
	 * functionality instead.
	 */
	public static final int SERVICE_CONTROL_NETBINDENABLE = 0x00000009;
	/**
	 * Notifies a network service that a component for binding has been removed.
	 * The hService handle must have the SERVICE_PAUSE_CONTINUE access right.
	 * However, this control code has been deprecated; use Plug and Play
	 * functionality instead.
	 */
	public static final int SERVICE_CONTROL_NETBINDREMOVE = 0x00000008;
	/**
	 * Notifies a service that its startup parameters have changed. The hService
	 * handle must have the SERVICE_PAUSE_CONTINUE access right.
	 */
	public static final int SERVICE_CONTROL_PARAMCHANGE = 0x00000006;

	/**
	 * Notifies a service that it should pause. The hService handle must have
	 * the SERVICE_PAUSE_CONTINUE access right.
	 */
	public static final int SERVICE_CONTROL_PAUSE = 0x00000002;
	/**
	 * Notifies a service that it should stop. The hService handle must have the
	 * SERVICE_STOP access right. After sending the stop request to a service,
	 * you should not send other controls to the service. Service object
	 * specific access type
	 */
	public static final int SERVICE_CONTROL_STOP = 0x00000001;

	public static final int SERVICE_QUERY_CONFIG = 0x1;
	public static final int SERVICE_CHANGE_CONFIG = 0x2;
	public static final int SERVICE_QUERY_STATUS = 0x4;
	public static final int SERVICE_ENUMERATE_DEPENDENTS = 0x8;
	public static final int SERVICE_START = 0x10;
	public static final int SERVICE_STOP = 0x20;
	public static final int SERVICE_PAUSE_CONTINUE = 0x40;
	public static final int SERVICE_INTERROGATE = 0x80;
	public static final int SERVICE_USER_DEFINED_CONTROL = 0x100;
	public static final int SERVICE_ALL_ACCESS = ( STANDARD_RIGHTS_REQUIRED
			| SERVICE_QUERY_CONFIG
			| SERVICE_CHANGE_CONFIG
			| SERVICE_QUERY_STATUS
			| SERVICE_ENUMERATE_DEPENDENTS
			| SERVICE_START
			| SERVICE_STOP
			| SERVICE_PAUSE_CONTINUE
			| SERVICE_INTERROGATE | SERVICE_USER_DEFINED_CONTROL );

	/*
	 * BOOL WINAPI StartService( SC_HANDLE hService, DWORD dwNumServiceArgs,
	 * LPCTSTR* lpServiceArgVectors );
	 */
	public static boolean StartService( int hService, int dwNumServiceArgs,
			String[] lpServiceArgVectors ) throws NativeException,
			IllegalAccessException
	{

		Native StartService = new Native( DLL_NAME, "StartServiceA" );
		Pointer arrayOfLPCSTR = null;
		ArrayList savedPointers = new ArrayList( );
		try
		{
			StartService.setRetVal( Type.INT );

			int i = 0;
			StartService.setParameter( i++, hService );
			StartService.setParameter( i++, dwNumServiceArgs );
			if ( lpServiceArgVectors != null )
			{
				arrayOfLPCSTR = new Pointer( MemoryBlockFactory.createMemoryBlock( lpServiceArgVectors.length * 4 ) );
				int j = 0;
				for ( int z = 0; z < lpServiceArgVectors.length; z++ )
				{
					String s = lpServiceArgVectors[z];
					final Pointer p = new Pointer( MemoryBlockFactory.createMemoryBlock( s.length( ) + 1 ) );
					p.setMemory( s );
					arrayOfLPCSTR.setIntAt( j, p.getPointer( ) );
					j += 4;
					savedPointers.add( p );
				}
				StartService.setParameter( i++, arrayOfLPCSTR );
			}
			else
			{
				StartService.setParameter( i++, 0 );
			}
			StartService.invoke( );

			return ( StartService.getRetValAsInt( ) != 0 );
		}
		finally
		{
			for ( int z = 0; z < savedPointers.size( ); z++ )
			{
				Pointer p = (Pointer) savedPointers.get( z );

				if ( p != null )
				{
					p.dispose( );
				}
			}
			if ( arrayOfLPCSTR != null )
			{
				arrayOfLPCSTR.dispose( );
			}
		}
	}

	/*
	 * BOOL WINAPI ControlService( SC_HANDLE hService, DWORD dwControl,
	 * LPSERVICE_STATUS lpServiceStatus );
	 */

	public static boolean ControlService( int hService, DWORD dwControl,
			Pointer pControlParams ) throws NativeException,
			IllegalAccessException
	{

		Native ControlService = new Native( DLL_NAME, "ControlService" );

		ControlService.setRetVal( Type.INT );

		int i = 0;
		ControlService.setParameter( i++, hService );
		ControlService.setParameter( i++, dwControl.getValue( ) );
		ControlService.setParameter( i++, pControlParams );
		ControlService.invoke( );

		return ( ControlService.getRetValAsInt( ) != 0 );

	}

	/*
	 * BOOL WINAPI UnlockServiceDatabase( SC_LOCK ScLock );
	 */
	public static boolean UnlockServiceDatabase( int ScLock )
			throws NativeException, IllegalAccessException
	{
		Native UnlockServiceDatabase = new Native( DLL_NAME,
				"UnlockServiceDatabase" );
		UnlockServiceDatabase.setRetVal( Type.INT );

		int i = 0;
		UnlockServiceDatabase.setParameter( i++, ScLock );
		UnlockServiceDatabase.invoke( );

		return ( UnlockServiceDatabase.getRetValAsInt( ) != 0 );
	}

	/*
	 * SC_LOCK WINAPI LockServiceDatabase( SC_HANDLE hSCManager )
	 */
	public static int LockServiceDatabase( int hSCManager )
			throws NativeException, IllegalAccessException
	{
		Native LockServiceDatabase = new Native( DLL_NAME,
				"LockServiceDatabase" );
		LockServiceDatabase.setRetVal( Type.INT );

		int i = 0;
		LockServiceDatabase.setParameter( i++, hSCManager );
		LockServiceDatabase.invoke( );

		return LockServiceDatabase.getRetValAsInt( );

	}

	/*
	 * BOOL WINAPI ChangeServiceConfig( SC_HANDLE hService, DWORD dwServiceType,
	 * DWORD dwStartType, DWORD dwErrorControl, LPCTSTR lpBinaryPathName,
	 * LPCTSTR lpLoadOrderGroup, LPDWORD lpdwTagId, LPCTSTR lpDependencies,
	 * LPCTSTR lpServiceStartName, LPCTSTR lpPassword, LPCTSTR lpDisplayName );
	 */
	public static boolean ChangeServiceConfig( int hService,
			DWORD dwServiceType, DWORD dwStartType, DWORD dwErrorControl,
			String lpBinaryPathName, String lpLoadOrderGroup, DWORD lpdwTagId,
			String lpDependencies, String lpServiceStartName,
			String lpPassword, String lpDisplayName ) throws NativeException,
			IllegalAccessException
	{

		Native ChangeServiceConfig = new Native( DLL_NAME,
				"ChangeServiceConfigA" );

		ChangeServiceConfig.setRetVal( Type.INT );

		int i = 0;
		ChangeServiceConfig.setParameter( i++, hService );
		ChangeServiceConfig.setParameter( i++, dwServiceType.getValue( ) );
		ChangeServiceConfig.setParameter( i++, dwStartType.getValue( ) );
		ChangeServiceConfig.setParameter( i++, dwErrorControl.getValue( ) );
		ChangeServiceConfig.setParameter( i++, lpBinaryPathName );
		ChangeServiceConfig.setParameter( i++, lpLoadOrderGroup );
		ChangeServiceConfig.setParameter( i++, lpdwTagId.getValue( ) );
		ChangeServiceConfig.setParameter( i++, lpDependencies );
		ChangeServiceConfig.setParameter( i++, lpServiceStartName );
		ChangeServiceConfig.setParameter( i++, lpPassword );
		ChangeServiceConfig.setParameter( i++, lpDisplayName );
		ChangeServiceConfig.invoke( );

		return ( ChangeServiceConfig.getRetValAsInt( ) != 0 );
	}

	/*
	 * SC_HANDLE WINAPI OpenService( SC_HANDLE hSCManager, LPCTSTR
	 * lpServiceName, DWORD dwDesiredAccess );
	 */
	public static int OpenService( int hSCManager, String lpServiceName,
			DWORD dwDesiredAccess ) throws NativeException,
			IllegalAccessException
	{
		Native OpenService = new Native( DLL_NAME, "OpenServiceA" );
		OpenService.setRetVal( Type.INT );

		int i = 0;
		OpenService.setParameter( i++, hSCManager );
		OpenService.setParameter( i++, lpServiceName );
		OpenService.setParameter( i++, dwDesiredAccess.getValue( ) );
		OpenService.invoke( );

		return OpenService.getRetValAsInt( );
	}

	/*
	 * BOOL WINAPI DeleteService( SC_HANDLE hService );
	 */
	public static boolean DeleteService( int hSCObject )
			throws NativeException, IllegalAccessException
	{
		Native DeleteService = new Native( DLL_NAME, "DeleteService" );

		DeleteService.setRetVal( Type.INT );

		int i = 0;
		DeleteService.setParameter( i++, hSCObject );
		DeleteService.invoke( );

		return ( DeleteService.getRetValAsInt( ) != 0 );

	}

	/*
	 * BOOL WINAPI CloseServiceHandle( SC_HANDLE hSCObject );
	 */

	public static boolean CloseServiceHandle( int hSCObject )
			throws NativeException, IllegalAccessException
	{
		Native CloseServiceHandle = new Native( DLL_NAME, "CloseServiceHandle" );

		CloseServiceHandle.setRetVal( Type.INT );

		int i = 0;
		CloseServiceHandle.setParameter( i++, hSCObject );
		CloseServiceHandle.invoke( );

		return ( CloseServiceHandle.getRetValAsInt( ) != 0 );
	}

	/*
	 * SC_HANDLE WINAPI OpenSCManager( LPCTSTR lpMachineName, LPCTSTR
	 * lpDatabaseName, DWORD dwDesiredAccess );
	 */

	public static int OpenSCManager( String lpMachineName,
			String lpDatabaseName, DWORD dwDesiredAccess )
			throws NativeException, IllegalAccessException
	{
		Native OpenSCManager = new Native( DLL_NAME, "OpenSCManagerA" );

		OpenSCManager.setRetVal( Type.INT );

		int i = 0;
		OpenSCManager.setParameter( i++, lpMachineName );
		OpenSCManager.setParameter( i++, lpDatabaseName );
		OpenSCManager.setParameter( i++, dwDesiredAccess.getValue( ) );
		OpenSCManager.invoke( );

		return OpenSCManager.getRetValAsInt( );

	}

	/*
	 * SC_HANDLE WINAPI CreateService( SC_HANDLE hSCManager, LPCTSTR
	 * lpServiceName, LPCTSTR lpDisplayName, DWORD dwDesiredAccess, DWORD
	 * dwServiceType, DWORD dwStartType, DWORD dwErrorControl, LPCTSTR
	 * lpBinaryPathName, LPCTSTR lpLoadOrderGroup, LPDWORD lpdwTagId, LPCTSTR
	 * lpDependencies, LPCTSTR lpServiceStartName, LPCTSTR lpPassword );
	 */

	public static int CreateService( int hSCManager, String lpServiceName,
			String lpDisplayName, DWORD dwDesiredAccess, DWORD dwServiceType,
			DWORD dwStartType, DWORD dwErrorControl, String lpBinaryPathName,
			String lpLoadOrderGroup, DWORD lpdwTagId, String lpDependencies,
			String lpServiceStartName, String lpPassword )
			throws NativeException, IllegalAccessException
	{

		Native CreateService = new Native( DLL_NAME, "CreateServiceA" );

		CreateService.setRetVal( Type.INT );

		int i = 0;
		CreateService.setParameter( i++, hSCManager );
		CreateService.setParameter( i++, lpServiceName );
		CreateService.setParameter( i++, lpDisplayName );
		CreateService.setParameter( i++, dwDesiredAccess.getValue( ) );
		CreateService.setParameter( i++, dwServiceType.getValue( ) );
		CreateService.setParameter( i++, dwStartType.getValue( ) );
		CreateService.setParameter( i++, dwErrorControl.getValue( ) );
		CreateService.setParameter( i++, lpBinaryPathName );
		CreateService.setParameter( i++, lpLoadOrderGroup );
		CreateService.setParameter( i++, lpdwTagId.getValue( ) );
		CreateService.setParameter( i++, lpDependencies );
		CreateService.setParameter( i++, lpServiceStartName );
		CreateService.setParameter( i++, lpPassword );
		CreateService.invoke( );

		return CreateService.getRetValAsInt( );
	}

	/*
	 * HANDLE TokenHandle, BOOL DisableAllPrivileges, PTOKEN_PRIVILEGES
	 * NewState, DWORD BufferLength, PTOKEN_PRIVILEGES PreviousState, PDWORD
	 * ReturnLength
	 */
	public static boolean AdjustTokenPrivileges( HANDLE TokenHandle,
			boolean DisableAllPrivileges, TOKEN_PRIVILEGES NewState )
			throws NativeException, IllegalAccessException
	{
		Native nAdjustTokenPrivileges = new Native( DLL_NAME,
				"AdjustTokenPrivileges" );
		nAdjustTokenPrivileges.setRetVal( Type.INT );

		int i = 0;
		nAdjustTokenPrivileges.setParameter( i++, TokenHandle.getValue( ) );
		nAdjustTokenPrivileges.setParameter( i++, DisableAllPrivileges ? -1 : 0 );
		nAdjustTokenPrivileges.setParameter( i++, NewState.getPointer( ) );
		nAdjustTokenPrivileges.setParameter( i++, 0 );
		nAdjustTokenPrivileges.setParameter( i++, NullPointer.NULL );
		nAdjustTokenPrivileges.setParameter( i++, 0 );
		nAdjustTokenPrivileges.invoke( );

		return nAdjustTokenPrivileges.getRetValAsInt( ) != 0;
	}

	/*
	 * LPCTSTR lpSystemName, LPCTSTR lpName, PLUID lpLuid
	 */
	public static boolean LookupPrivilegeValue( String lpSystemName,
			String lpName, LONG lpLuid ) throws NativeException,
			IllegalAccessException
	{
		Native nLookupPrivilegeValue = new Native( DLL_NAME,
				"LookupPrivilegeValueA" ); // Use ANSI
		nLookupPrivilegeValue.setRetVal( Type.INT );

		int i = 0;
		nLookupPrivilegeValue.setParameter( i++, lpSystemName );
		nLookupPrivilegeValue.setParameter( i++, lpName );
		nLookupPrivilegeValue.setParameter( i++, lpLuid.getPointer( ) );
		nLookupPrivilegeValue.invoke( );

		return nLookupPrivilegeValue.getRetValAsInt( ) != 0;
	}

	/*
	 * HANDLE ProcessHandle, DWORD DesiredAccess, PHANDLE TokenHandle
	 */
	public static boolean OpenProcessToken( HANDLE ProcessHandle,
			int DesiredAccess, HANDLE TokenHandle ) throws NativeException,
			IllegalAccessException
	{
		Native nOpenProcessToken = new Native( DLL_NAME, "OpenProcessToken" );
		nOpenProcessToken.setRetVal( Type.INT );

		int i = 0;
		nOpenProcessToken.setParameter( i++, ProcessHandle.getValue( ) );
		nOpenProcessToken.setParameter( i++, DesiredAccess );
		nOpenProcessToken.setParameter( i++, TokenHandle.getPointer( ) );
		nOpenProcessToken.invoke( );

		return nOpenProcessToken.getRetValAsInt( ) != 0;
	}

	public static String GetUserName( ) throws NativeException,
			IllegalAccessException
	{
		Native Native = new Native( DLL_NAME, "GetUserNameA" );

		Pointer p = Pointer.createPointer( 256 );
		DWORD size = new DWORD( p.getSize( ) );
		try
		{
			Native.setRetVal( Type.INT );
			Native.setParameter( 0, p.getPointer( ) );
			Native.setParameter( 1, size.getPointer( ) );

			Native.invoke( );

			return p.getAsString( );
		}
		finally
		{
			p.dispose( );
		}
	}


	public static int getLastErrorCode( )
	{
		try
		{
			return Integer.parseInt( mLastErrorCode );
		}
		catch ( Throwable e )
		{
			e.printStackTrace( );
			return -1;
		}
	}
}
