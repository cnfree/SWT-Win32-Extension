/*******************************************************************************
 * Copyright (c) 2007 cnfree.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  cnfree  - initial API and implementation
 *******************************************************************************/

package org.sf.feeling.swt.win32.internal.extension;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.swt.internal.gdip.RectF;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.TCHAR;
import org.sf.feeling.swt.win32.extension.registry.KeyIterator;
import org.sf.feeling.swt.win32.extension.registry.RegistryKey;
import org.sf.feeling.swt.win32.extension.registry.RegistryValue;
import org.sf.feeling.swt.win32.extension.registry.ValueIterator;

public class Extension extends OS
{

	private static Set libSet = new HashSet( );

	static
	{
		libSet.add( "swt-extension-win32" );
		LibraryLoader.loadNativeLibrary( "swt-extension-win32" );
	}

	public static void loadNativeLibrary( String libName )
	{
		if ( !libSet.contains( libName ) )
		{
			libSet.add( libName );
			LibraryLoader.loadNativeLibrary( libName );
		}
	}

	public static final native void GetDiskFreeSpace( String drive,
			DISKFREESPACE diskFreeSpace );

	public static final DISKFREESPACE GetDiskFreeSpace( String drive )
	{
		DISKFREESPACE diskFreeSpace = new DISKFREESPACE( );
		GetDiskFreeSpace( drive, diskFreeSpace );
		return diskFreeSpace;
	}

	public static final native void GetSystemInfo( SYSTEMINFO systemInfo );

	public static final SYSTEMINFO GetSystemInfo( )
	{
		SYSTEMINFO system_info = new SYSTEMINFO( );
		GetSystemInfo( system_info );
		return system_info;
	}

	public static final native void GlobalMemoryStatus(
			MEMORYSTATUS memoryStatus );

	public static final MEMORYSTATUS GlobalMemoryStatus( )
	{
		MEMORYSTATUS memorystatus = new MEMORYSTATUS( );
		memorystatus.dwLength = MEMORYSTATUS.sizeof;
		GlobalMemoryStatus( memorystatus );
		return memorystatus;
	}

	public static final native int GetDriveType( String drive );

	public static final native String[] GetLogicalDrives( );

	public static final native String GetVolumeSerialNumber( String drive );

	public static final native String GetVolumeLabel( String drive );

	public static final native boolean SetVolumeLabel( String drive,
			String label );

	public static final native String GetCurrentDirectory( );

	public static final native boolean SetCurrentDirectory( String directory );

	public static final native boolean RegistryKeyIteratorHasNext(
			KeyIterator iterator );

	public static final native void DeleteRegistryKey( RegistryKey key );

	public static final native boolean RegistryKeyHasSubKeys( RegistryKey key );

	public static final native boolean RegistryKeyHasValue( RegistryKey key,
			String name );

	public static final native String RegistryKeyValueIteratorGetNext(
			ValueIterator iterator );

	public static final native boolean RegistryKeyHasValues( RegistryKey key );

	public static final native RegistryValue RegistryKeyGetValue(
			RegistryKey key, String name );

	public static final native void RegistryKeySetValue( RegistryKey key,
			RegistryValue value );

	public static final native void RegistryKeyDeleteValue( RegistryKey key,
			String name );

	public static final native String RegistryKeyIteratorGetNext(
			KeyIterator iterator );

	public static final native boolean ExistsRegistryKey( RegistryKey key );

	public static final native boolean RegistryKeyValueIteratorHasNext(
			ValueIterator iterator );

	public static final native void CreateRegistryKey( RegistryKey key );

	public static final native String GetShortCutTarget( String linkFile );

	public static final native int SetShortCutTarget( String linkFile,
			String targetFile );

	public static final native String GetShortCutArguments( String linkFile );

	public static final native int SetShortCutArguments( String linkFile,
			String arguments );

	public static final native String GetShortCutWorkingDirectory(
			String linkFile );

	public static final native int SetShortCutWorkingDirectory(
			String linkFile, String workingDirectory );

	public static final native String GetShortCutDescription( String linkFile );

	public static final native int SetShortCutDescription( String linkFile,
			String description );

	public static final native int CreateShortCut( String sourceFile,
			String linkFile );

	public static final native int SHFileOperationA(
			SHFILEOPSTRUCT shFileOpStruct );

	public static final native int SHFileOperationW(
			SHFILEOPSTRUCT shFileOpStruct );

	public static final native boolean FlashWindow( int hwnd, boolean bInvert );

	public static final native String GetSystemDirectory( );

	public static final native String GetWindowsDirectory( );

	public static final native String GetTempPath( );

	public static final native String GetLongPathName( String shortPathName );

	public static final native boolean SetWallPaper( char[] picturePath,
			int style );

	public static boolean SetWallPaper( String picturePath, int style )
	{
		return SetWallPaper( picturePath.toCharArray( ), style );
	}

	public static final native int SHGetSpecialFolderLocation( int folderId );

	public static final native String SHGetPathFromIDList( int pidl );

	public static final native int SHGetFileInfoW( int pszPath,
			int dwFileAttributes, SHFILEINFOW psfi, int cbFileInfo, int uFlags );

	public static final native int SHGetFileInfoA( int pszPath,
			int dwFileAttributes, SHFILEINFOA psfi, int cbFileInfo, int uFlags );

	public static int SHGetFileInfo( int pszPath, int dwFileAttributes,
			SHFILEINFO psfi, int cbFileInfo, int uFlags )
	{
		if ( IsUnicode )
		{
			return SHGetFileInfoW( pszPath,
					dwFileAttributes,
					(SHFILEINFOW) psfi,
					cbFileInfo,
					uFlags );
		}
		return SHGetFileInfoA( pszPath,
				dwFileAttributes,
				(SHFILEINFOA) psfi,
				cbFileInfo,
				uFlags );
	}

	public static final native String GetCPUID( );

	public static final native int[] GetMACID( );

	public static final native int[] GetMACAddress( int MACID );

	public static final native int GetCpuUsages( );

	public static final native int Ping( String host, int dateSize );

	public static final native boolean Reboot( boolean force );

	public static final native boolean Shutdown( boolean force );

	public static final native boolean Logoff( boolean force );

	public static final native boolean LockWorkstation( );

	public static final native boolean SuspendWorkstation( boolean suspend,
			boolean force );

	public static boolean SuspendWorkstation( boolean force )
	{
		return SuspendWorkstation( true, force );
	}

	public static boolean HibernateWorkstation( boolean force )
	{
		return SuspendWorkstation( false, force );
	}

	public static final native boolean InitiateShutdownA( byte[] info,
			int time, boolean force, boolean reboot );

	public static final native boolean InitiateShutdownW( char[] info,
			int time, boolean force, boolean reboot );

	public static boolean InitiateShutdown( String info, int time,
			boolean force, boolean reboot )
	{
		TCHAR lpszInfo = new TCHAR( 0, info, true );
		if ( IsUnicode )
		{
			return InitiateShutdownW( lpszInfo.chars, time, force, reboot );
		}
		else
			return InitiateShutdownA( lpszInfo.bytes, time, force, reboot );
	}

	public static boolean InitiateShutdown( String info, int time, boolean force )
	{
		return InitiateShutdown( info, time, force, false );
	}

	public static boolean InitiateReboot( String info, int time, boolean force )
	{
		return InitiateShutdown( info, time, force, true );
	}

	public static final native int ExtractAssociatedIconA( int hint,
			byte[] info, int index );

	public static final native int ExtractAssociatedIconW( int hint,
			char[] info, int index );

	public static int ExtractAssociatedIcon( int hint, String iconPath,
			int index )
	{
		TCHAR lpszInfo = new TCHAR( 0, iconPath, true );
		if ( IsUnicode )
		{
			return ExtractAssociatedIconW( hint, lpszInfo.chars, index );
		}
		else
			return ExtractAssociatedIconA( hint, lpszInfo.bytes, index );
	}

	public static final native boolean FlashWindowEx( FLASHWINFO lpFlashWInfo );

	public static final native String SHGetSpecialFolderPath( int hwnd,
			int csidl, boolean create );

	public static final native int SHAppBarMessage( int dwMessage,
			APPBARDATA pData );

	public static final native String GetComputerName( );

	public static final native boolean SetComputerName( String name );

	public static final native String GetUserName( );

	public static final native int CreateEvent( boolean manualReset,
			boolean initialState, String name );

	public static final native int ResetEvent( int eventHandle );

	public static final native int SetEvent( int eventHandle );

	public static final native int WaitForSingleObject( int handle, int timeout );

	public static final native int InstallSystemHook( int hookType, int threadId );

	public static final native int UnInstallSystemHook( int hookType );

	public static final native Object ReadHookData( int value );

	public static final native boolean CreateStructByPoint( int point,
			Object struct );

	public static final native boolean SaveStructToPoint( Object struct,
			int point );

	public static final native int GetClassLongA( int hwnd, int nIndex );

	public static final native int GetClassLongW( int hwnd, int nIndex );

	public static final int GetClassLong( int /* long */hWnd, int nIndex )
	{
		if ( IsUnicode )
			return GetClassLongW( hWnd, nIndex );
		return GetClassLongA( hWnd, nIndex );
	}

	public static final native int SetClassLongW( int /* long */hWnd, int nIndex,
			int dwNewLong );

	public static final native int SetClassLongA( int /* long */hWnd, int nIndex,
			int dwNewLong );

	public static final int SetClassLong( int /* long */hWnd, int nIndex,
			int dwNewLong )
	{
		if ( IsUnicode )
			return SetClassLongW( hWnd, nIndex, dwNewLong );
		return SetClassLongA( hWnd, nIndex, dwNewLong );
	}

	public static final native int InvokeIGGI_I( int arg0, String arg1,
			String arg2, int arg3, int func ) throws Exception;

	public static final native int InvokeIG_I( int arg0, String arg1, int peer )
			throws Exception;

	public static final native int InvokeIIII_I( int arg0, int arg1, int arg2,
			int arg3, int func ) throws Exception;

	public static final native int InvokeII_I( int arg0, int arg1, int func )
			throws Exception;

	public static final native int InvokeIII_I( int arg0, int arg1, int arg2,
			int func ) throws Exception;

	public static final native int InvokeI_I( int arg0, int func )
			throws Exception;

	public static final native int Invoke_I( int func ) throws Exception;

	public static final native int InvokeG_I( String arg0, int peer )
			throws Exception;

	public static final native int InvokeGG_I( String arg0, String arg1,
			int peer ) throws Exception;

	public static native int InvokeP_I( byte[] arg0, int func )
			throws Exception;

	public static final native int InvokeIIIIO( int arg0, int arg1, int arg2,
			int arg3, int func ) throws Exception;

	public static final native int InvokeIO( int arg0, int peer )
			throws Exception;

	public static final native int InvokeIGO( int arg0, String arg1, int peer )
			throws Exception;

	public static final native byte[] InvokeI_S( int arg0, int returnSize,
			int func ) throws Exception;

	public static final native byte[] InvokeP_S( byte[] arg0, int returnSize,
			int func ) throws Exception;

	public static final native byte[] InvokePI_S( byte[] arg0, int arg1,
			int returnSize, int func ) throws Exception;

	public static final int /* long */LoadLibrary( String libFileName )
	{
		TCHAR lpLibFileName = new TCHAR( 0, libFileName, true );
		return LoadLibrary( lpLibFileName );
	}

	public static int LoadFunction( int dllHandle, String functionName )
	{
		if ( dllHandle != 0 )
		{
			byte[] lpProcName = new byte[functionName.length( ) + 1];
			for ( int i = 0; i < functionName.length( ); i++ )
			{
				lpProcName[i] = (byte) functionName.charAt( i );
			}
			lpProcName[functionName.length( )] = '\0';
			return GetProcAddress( dllHandle, lpProcName );
		}
		return 0;
	}

	public static final native int SetMenuItemBitmaps( int hMenu,
			int nPosition, int wFlags, int hBitmapUnchecked, int hBitmapChecked );

	public static final native void Mouse_Event( int dwFlags, int dx, int dy,
			int dwData, int dwExtraInfo );

	public static final native void Keyboard_Event( byte bVk, byte bScan,
			int dwFlags, int dwExtraInfo );

	public static int MciSendCommand( int arg0, int arg1, int arg2, Object arg3 )
	{
		if ( IsUnicode )
		{
			return MciSendCommandW( arg0, arg1, arg2, arg3 );
		}
		return MciSendCommandA( arg0, arg1, arg2, arg3 );
	}

	public static final native int MciSendCommandA( int arg0, int arg1,
			int arg2, Object arg3 );

	public static final native int MciSendCommandW( int arg0, int arg1,
			int arg2, Object arg3 );

	public static final native int[] GetFileVersionInfo( String filePath );

	public static final native String GetFileVersionInfoValue( int handle,
			String keyName );

	public static final native void FileVersionInfo_delete( int handle );

	public static final native int GraphicsPath_AddEllipse( int path, RectF rect );

	public static final native int MixerGetNumDevs( );

	public static final native int MixerOpen( MIXERHANDLE mixer, int deviceId,
			int dwCallback, int dwInstance, int dwFlags );

	public static final native int MixerClose( int hMixer );

	public static final native int MixerGetDevCapsA( int deviceId,
			MIXERCAPSA mixerCaps );

	public static final native int MixerGetDevCapsW( int deviceId,
			MIXERCAPSW mixerCaps );

	public static final native int MixerGetLineInfoA( int deviceId,
			MIXERLINEA mixerCaps, int dwComponentType );

	public static final native int MixerGetLineInfoW( int deviceId,
			MIXERLINEW mixerCaps, int dwComponentType );

	public static final native boolean GetPlaybackVolume( int deviceId,
			int dwSrcType, MIXERVOLUME volume, boolean bMono );

	public static final native boolean SetPlaybackVolume( int deviceId,
			int dwSrcType, MIXERVOLUME volume, boolean bMono );

	public static final native boolean GetRecordingVolume( int deviceId,
			int dwSrcType, MIXERVOLUME volume, boolean bMono );

	public static final native boolean SetRecordingVolume( int deviceId,
			int dwSrcType, MIXERVOLUME volume, boolean bMono );

	public static final native boolean SetMixerMute( int deviceId,
			int dwDstType, int dwSrcType, boolean value );

	public static final native boolean IsMixerMute( int deviceId,
			int dwDstType, int dwSrcType );

	public static final native boolean IsMixerMasterMute( int deviceId );

	public static final native boolean SetMixerMasterMute( int deviceId,
			boolean value );

	public static native List IShellFolder_EnumObjects( int handle, int hwnd,
			int flags );

	public static native String IShellFolder_GetDisplayNameOf( int handle,
			int pidl, int flags );

	public static native int IShellFolder_GetAttributesOf( int handle,
			int pidl, int flags );

	public static native int IShellFolder_BindToObject( int handle, int pidl );
	
	public static native int IShellFolder_ConcatPIDLs( int pidl1, int pidl2);
	
	public static native int IShellFolder_GetIExtractIcon( int handle,
			int hwnd, int pidl );

	public static native int IShellFolder_ParseDisplayName( int handle,
			int wnd, char[] name );

	public static native int SHGetMalloc( );

	public static native int ReleaseShellMalloc( int malloc );

	public static native int ReleaseIShellFolder( int handle );

	public static native void ReleaseItemIdList( int malloc, int lpEnumiIdList );

	public static native String IExtractIcon_GetIconLocation( int handle,
			int uFlags, int index, int pwFlags );

	public static native int IExtractIcon_Extract( int handle, String location,
			int index, int[] largeIcon, int[] smallIcon, int size );

	public static native int IShellFolder_GetParent( int handle );

	public static native int IShellFolder_GetRelativeHandle( int handle );

	public static native List GetSystemProcessesSnap( );

	public static native List GetProcessModulesSnap( int processId );

	public static native List GetProcessThreadsSnap( int processId );

}