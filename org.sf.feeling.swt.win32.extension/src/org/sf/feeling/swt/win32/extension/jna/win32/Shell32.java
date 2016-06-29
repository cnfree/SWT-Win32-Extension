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
import org.sf.feeling.swt.win32.extension.jna.datatype.win32.DWORD;
import org.sf.feeling.swt.win32.extension.jna.exception.NativeException;
import org.sf.feeling.swt.win32.extension.jna.win32.structure.FILEINFO;
import org.sf.feeling.swt.win32.extension.jna.win32.structure.HWND;
import org.sf.feeling.swt.win32.extension.jna.win32.structure.ITEMID;
import org.sf.feeling.swt.win32.extension.jna.win32.structure.NOTIFYICONDATA;
import org.sf.feeling.swt.win32.extension.jna.win32.structure.SHELLEXECUTEINFO;
import org.sf.feeling.swt.win32.extension.jna.win32.structure.SHFILEOPSTRUCT;

public class Shell32
{

	public static final String DLL_NAME = "shell32.dll";

	// uFlags:
	// Flag that specifies the file information to retrieve. If uFlags includes
	// the SHGFI_ICON
	// or SHGFI_SYSICONINDEX value, the return value is the handle to the system
	// image
	// list that contains the large icon images. If the SHGFI_SMALLICON value is
	// included
	// with SHGFI_ICON or SHGFI_SYSICONINDEX, the return value is the handle to
	// the
	// image list that contains the small icon images.

	// If uFlags does not include SHGFI_EXETYPE, SHGFI_ICON, SHGFI_SYSICONINDEX,
	// or SHGFI_SMALLICON, the return value is nonzero if successful, or zero
	// otherwise.

	// This parameter can be a combination of the following values:

	// Modifies SHGFI_ICON, causing the function to retrieve the file//s large
	// icon.
	public static final int SHGFI_LARGEICON = 0x0;

	// Modifies SHGFI_ICON, causing the function to retrieve the file//s small
	// icon.
	public static final int SHGFI_SMALLICON = 0x1;

	// Modifies SHGFI_ICON, causing the function to retrieve the file//s open
	// icon. A
	// container object displays an open icon to indicate that the container is
	// open.
	public static final int SHGFI_OPENICON = 0x2;

	// Modifies SHGFI_ICON, causing the function to retrieve a shell-sized icon.
	// If
	// this flag is not specified, the function sizes the icon according to the
	// system
	// metric values.
	// The return value is *supposed to be* the handle of the system image list
	// which
	// could be passed to the ImageList_GetIconSize function to get the icon
	// size.
	// But the return value *is only* nonzero if successful, or zero otherwise.
	public static final int SHGFI_SHELLICONSIZE = 0x4;

	// Indicates that pszPath is the address of an ITEMIDLIST structure rather
	// than
	// a path name.
	public static final int SHGFI_PIDL = 0x8;

	// Indicates that the function should not attempt to access the file
	// specified by
	// pszPath. Rather, it should act as if the file specified by pszPath exists
	// with
	// the file attributes passed in dwFileAttributes. This flag cannot be
	// combined
	// with the SHGFI_ATTRIBUTES, SHGFI_EXETYPE, or SHGFI_PIDL flags.
	public static final int SHGFI_USEFILEATTRIBUTES = 0x10;

	// Retrieves the handle of the icon that represents the file and the index
	// of the
	// icon within the system image list. The handle is copied to the hIcon
	// member
	// of the structure specified by psfi, and the index is copied to the iIcon
	// member.

	// The return value is *supposed to be* the handle of the system image list,
	// .....boolean instead...!!!
	// ** SHGFI_ICON creates a copy of the icon in memory. The DestroyIcon **
	// ** function must be called to free any memory the icon occupied. **
	public static final int SHGFI_ICON = 0x100;

	// Retrieves the display name for the file. The name is copied to the
	// szDisplayName
	// member of the structure specified by psfi. The returned display name uses
	// the
	// long filename, if any, rather than the 8.3 form of the filename.
	public static final int SHGFI_DISPLAYNAME = 0x200;

	// Retrieves the string that describes the file//s type. The string is
	// copied to the
	// szTypeName member of the structure specified by psfi.
	public static final int SHGFI_TYPENAME = 0x400;

	// Retrieves the file attribute flags. The flags are copied to the
	// dwAttributes member
	// of the structure specified by psfi. See the constants at the end of this
	// file.
	public static final int SHGFI_ATTRIBUTES = 0x800;

	// Retrieves the name of the file that contains the icon representing the
	// file. The
	// name is copied to the szDisplayName member of the structure specified by
	// psfi.
	public static final int SHGFI_ICONLOCATION = 0x1000;

	// Returns the type of the executable file if pszPath identifies an
	// executable file.
	// To retrieve the executable file type, uFlags must specify only
	// SHGFI_EXETYPE.
	// The return value specifies the type of the executable file:
	// LowWord value HighWord value Type
	// 0 Nonexecutable file or an error condition.
	// "NE" or "PE" 3.0, 3.5, or 4.0 Windows application
	// "MZ" 0 MS-DOS .EXE, .COM or .BAT file
	// "PE" 0 Win32 console application
	public static final int SHGFI_EXETYPE = 0x2000;

	// Constants to represent the strings as ASCII char codes
	public static final int EXE_WIN16 = 0x454E; // "NE"
	public static final int EXE_DOS16 = 0x5A4D; // "MZ"
	public static final int EXE_WIN32 = 0x4550; // "PE"
	// public static final int EXE_DOS32 = 0x4543 // "CE"

	// Retrieves the index of the icon within the system image list. The index
	// is copied to
	// the iIcon member of the structure specified by psfi. The return value is
	// the handle of
	// the system image list.
	public static final int SHGFI_SYSICONINDEX = 0x4000;

	// Modifies SHGFI_ICON, causing the function to add the link overlay to the
	// file//s icon.
	public static final int SHGFI_LINKOVERLAY = 0x8000;

	// Modifies SHGFI_ICON, causing the function to blend the file//s icon with
	// the system
	// highlight color.
	public static final int SHGFI_SELECTED = 0x10000;

	// ============================================================

	// From IShellFolder::GetAttributesOf (SFGAO)
	// The following list the attribute flags that may be returned by
	// SHGFI_ATTRIBUTES.
	// File object attributes include capability flags, display attributes,
	// contents flags,
	// and miscellaneous attributes.

	// Capability flags:
	public static final int DROPEFFECT_COPY = 1;
	public static final int DROPEFFECT_MOVE = 2;
	public static final int DROPEFFECT_LINK = 4;
	public static final int SFGAO_CANCOPY = DROPEFFECT_COPY; // // Objects can
																// be copied
	public static final int SFGAO_CANMOVE = DROPEFFECT_MOVE; // // Objects can
																// be moved
	public static final int SFGAO_CANLINK = DROPEFFECT_LINK; // // Objects can
																// have
																// shortcuts
	public static final int SFGAO_CANRENAME = 0x10; // // Objects can be renamed
	public static final int SFGAO_CANDELETE = 0x20; // // Objects can be deleted
	public static final int SFGAO_HASPROPSHEET = 0x40; // // Objects have
														// property sheets
	public static final int SFGAO_DROPTARGET = 0x100; // // Objects are drop
														// target
	public static final int SFGAO_CAPABILITYMASK = 0x177; // // Mask for the
															// capability flags

	// Display attributes:
	public static final int SFGAO_LINK = 0x10000; // // Is a shortcut (link)
	public static final int SFGAO_SHARE = 0x20000; // // Is shared
	public static final int SFGAO_READONLY = 0x40000; // // Is read-only
	public static final int SFGAO_GHOSTED = 0x80000; // // Is ghosted icon
	public static final int SFGAO_DISPLAYATTRMASK = 0xF0000; // // Mask for the
																// display
																// attributes.

	// Contents attributes (flags):
	public static final int SFGAO_FILESYSANCESTOR = 0x10000000; // // Contains
																// file a system
																// folder
	public static final int SFGAO_FOLDER = 0x20000000; // // Is a folder.
	public static final int SFGAO_FILESYSTEM = 0x40000000; // // Is a file
															// system object
															// (file/folder/root)
	public static final int SFGAO_HASSUBFOLDER = 0x80000000; // // Expandable in
																// the map pane
	public static final int SFGAO_CONTENTSMASK = 0x80000000; // // Mask for
																// contents
																// attributes

	// Miscellaneous attributes:
	public static final int SFGAO_VALIDATE = 0x1000000; // // invalidate cached
														// information
	public static final int SFGAO_REMOVABLE = 0x2000000; // // Is removeable
															// media
	public static final int SFGAO_COMPRESSED = 0x4000000; // // Object is
															// compressed (use
															// alt color)

	public static final int CSIDL_DESKTOP = 0x0; // Desktop
	public static final int CSIDL_INTERNET = 0x1; // Internet
	public static final int CSIDL_PROGRAMS = 0x2; // Startmen?: Programme
	public static final int CSIDL_CONTROLS = 0x3; // Systemsteuerung
	public static final int CSIDL_PRINTERS = 0x4; // Drucker
	public static final int CSIDL_PERSONAL = 0x5; // Eigene Dateien
	public static final int CSIDL_FAVORITES = 0x6; // IE: Favoriten
	public static final int CSIDL_STARTUP = 0x7; // Autostart
	public static final int CSIDL_RECENT = 0x8; // Zuletzt benutzte Dokumente
	public static final int CSIDL_SENDTO = 0x9; // Senden an / SendTo
	public static final int CSIDL_BITBUCKET = 0xA; // Papierkorb
	public static final int CSIDL_STARTMENU = 0xB; // Startmen?
	public static final int CSIDL_MYMUSIC = 0xD; // Eigene Musik
	public static final int CSIDL_MYVIDEO = 0xE; // Eigene Videos
	public static final int CSIDL_DESKTOPDIRECTORY = 0x10; // Desktopverzeichnis
	public static final int CSIDL_DRIVES = 0x11; // Mein Computer
	public static final int CSIDL_NETWORK = 0x12; // Netzwerk
	public static final int CSIDL_NETHOOD = 0x13; // Netzwerkumgebung
	public static final int CSIDL_FONTS = 0x14; // Windows\Fonts
	public static final int CSIDL_TEMPLATES = 0x15; // Vorlagen
	public static final int CSIDL_COMMON_STARTMENU = 0x16; // "All Users" -
															// Startmen?
	public static final int CSIDL_COMMON_PROGRAMS = 0x17; // "All Users" -
															// Programme
	public static final int CSIDL_COMMON_STARTUP = 0x18; // "All Users" -
															// Autostart
	public static final int CSIDL_COMMON_DESKTOPDIRECTORY = 0x19; // "All Users"
																	// - Desktop
	public static final int CSIDL_APPDATA = 0x1A; // Anwendungsdaten
	public static final int CSIDL_PRINTHOOD = 0x1B; // Druckumgebung
	public static final int CSIDL_LOCAL_APPDATA = 0x1C; // Lokale
														// Einstellungen\Anwendungsdaten
	public static final int CSIDL_COMMON_FAVORITES = 0x1F; // "All Users" -
															// Favoriten
	public static final int CSIDL_INTERNET_CACHE = 0x20; // IE:
	public static final int CSIDL_COOKIES = 0x21; // IE: Cookies
	public static final int CSIDL_HISTORY = 0x22; // IE: Verlauf
	public static final int CSIDL_COMMON_APPDATA = 0x23; // "All Users" -
															// Anwendungsdaten
	public static final int CSIDL_WINDOWS = 0x24; // Windows
	public static final int CSIDL_SYSTEM = 0x25; // Windows\System32
	public static final int CSIDL_PROGRAM_FILES = 0x26; // C:\Programme
	public static final int CSIDL_MYPICTURES = 0x27; // Eigene Bilder
	public static final int CSIDL_PROFILE = 0x28; // Anwenderprofil
													// (Benutzername)
	public static final int CSIDL_SYSTEMX86 = 0x29; // Windows\System32
	public static final int CSIDL_PROGRAM_FILES_COMMON = 0x2B; // Gemeinsame
																// Dateien
	public static final int CSIDL_COMMON_TEMPLATES = 0x2D; // "All Users" -
															// Vorlagen
	public static final int CSIDL_COMMON_DOCUMENTS = 0x2E; // "All Users" -
															// Dokumente
	public static final int CSIDL_COMMON_ADMINTOOLS = 0x2F; // "All Users" -
															// Verwaltung
	public static final int CSIDL_ADMINTOOLS = 0x30; // Start
														// Programme\Verwaltung

	/** Creates a new instance of Shell32 */
	public Shell32( )
	{
	}

	/*
	 * HRESULT SHGetSpecialFolderLocation( HWND hwndOwner, int nFolder,
	 * LPITEMIDLIST *ppidl );
	 * 
	 * Example:
	 * 
	 * ITEMID p = new ITEMID(); Shell32.SHGetSpecialFolderLocation(new
	 * HWND(iconData.hWnd),Shell32.CSIDL_BITBUCKET,p); FILEINFO fileInfo = new
	 * FILEINFO(); Shell32.SHGetFileInfo(p.getSpecialPath(),new
	 * DWORD(0),fileInfo, fileInfo.getSizeOf(), Shell32.SHGFI_PIDL |
	 * Shell32.SHGFI_DISPLAYNAME | Shell32.SHGFI_TYPENAME | Shell32.SHGFI_ICON |
	 * Shell32.SHGFI_SMALLICON); fileInfo.getIcon();
	 */

	public static boolean SHGetSpecialFolderLocation( HWND hwndOwner,
			int nFolder, ITEMID ppidl ) throws NativeException,
			IllegalAccessException
	{

		Native SHGetSpecialFolderLocation = new Native( DLL_NAME,
				"SHGetSpecialFolderLocation" );
		SHGetSpecialFolderLocation.setRetVal( Type.INT );
		int pos = 0;
		SHGetSpecialFolderLocation.setParameter( pos++, hwndOwner.getValue( ) );
		SHGetSpecialFolderLocation.setParameter( pos++, nFolder );
		SHGetSpecialFolderLocation.setParameter( pos++, ppidl.getPointer( ) );
		SHGetSpecialFolderLocation.invoke( );
		pos = SHGetSpecialFolderLocation.getRetValAsInt( );

		return ( pos != 0 );
	}

	/*
	 * int SHFileOperation( SHFILEOPSTRUCT *FileOp; );
	 */
	public static boolean SHFileOperation( SHFILEOPSTRUCT fileOp )
			throws NativeException, IllegalAccessException
	{
		Native SHFileOperation = new Native( DLL_NAME, "SHFileOperationA" );
		SHFileOperation.setRetVal( Type.INT );
		SHFileOperation.setParameter( 0, fileOp.createPointer( ) );
		SHFileOperation.invoke( );
		return ( SHFileOperation.getRetValAsInt( ) != 0 );
	}

	/*
	 * DWORD_PTR SHGetFileInfo( LPCTSTR pszPath, DWORD dwFileAttributes,
	 * SHFILEINFO *psfi, UINT cbFileInfo, UINT uFlags );
	 */

	public static DWORD SHGetFileInfo( String pszPath, DWORD dwFileAttributes,
			FILEINFO psfi, int cbFileInfo, int uFlags ) throws NativeException,
			IllegalAccessException
	{

		Native SHGetFileInfo = new Native( DLL_NAME, "SHGetFileInfo" );
		SHGetFileInfo.setRetVal( Type.INT );
		int pos = 0;

		// check if pszPath is a number.
		// If i.e. SHFILEINFO.SHGFI_PIDL is set within uFlags we need to set
		// Type.INT
		try
		{
			Integer.parseInt( pszPath );
			SHGetFileInfo.setParameter( pos++, Type.INT, pszPath );
		}
		catch ( NumberFormatException e )
		{
			SHGetFileInfo.setParameter( pos++, pszPath );
		}

		SHGetFileInfo.setParameter( pos++, dwFileAttributes.getValue( ) );
		SHGetFileInfo.setParameter( pos++, psfi.getPointer( ) );
		SHGetFileInfo.setParameter( pos++, cbFileInfo );
		SHGetFileInfo.setParameter( pos++, uFlags );
		SHGetFileInfo.invoke( );
		pos = SHGetFileInfo.getRetValAsInt( );

		return new DWORD( pos );
	}

	/*
	 * DWORD dwMessage, PNOTIFYICONDATA lpdata
	 */
	/*
	 * For an example have a look at org.xvolks.test.windows.trayicon.TrayIcon
	 * 
	 * Dont forget to delete the icon when you close your application:
	 * 
	 * Shell32.Shell_NotifyIcon(NOTIFYICONDATA.NIM_DELETE, iconData);
	 */
	public static boolean Shell_NotifyIcon( int dwMessage, NOTIFYICONDATA lpdata )
			throws NativeException, IllegalAccessException
	{

		Native nShell_NotifyIcon = new Native( DLL_NAME, "Shell_NotifyIcon"
				+ ( WindowVersion.supportsUnicode( ) ? "W" : "A" ) );
		nShell_NotifyIcon.setRetVal( Type.INT );
		int i = 0;
		nShell_NotifyIcon.setParameter( i++, dwMessage );
		nShell_NotifyIcon.setParameter( i++, lpdata.getValue( ).getPointer( ) );
		nShell_NotifyIcon.invoke( );

		return nShell_NotifyIcon.getRetValAsInt( ) != 0;
	}

	/*
	 * BOOL ShellExecuteEx( LPSHELLEXECUTEINFO lpExecInfo );
	 */
	public static boolean ShellExecuteEx( SHELLEXECUTEINFO lpExecInfo )
			throws NativeException, IllegalAccessException
	{
		Native ShellExecuteEx = new Native( DLL_NAME, "ShellExecuteEx" );
		ShellExecuteEx.setRetVal( Type.INT );
		int pos = 0;

		ShellExecuteEx.setParameter( pos++, lpExecInfo.createPointer( ) );
		ShellExecuteEx.invoke( );
		pos = ShellExecuteEx.getRetValAsInt( );

		return ( pos != 0 );
	}

	public static class HChangeNotifyEventID
	{

		public static HChangeNotifyEventID SHCNE_ASSOCCHANGED = new HChangeNotifyEventID( 0x08000000 );

		private int hChangeNotifyEventID;

		private HChangeNotifyEventID( int hChangeNotifyEventID )
		{
			this.hChangeNotifyEventID = hChangeNotifyEventID;
		}

		public int getValue( )
		{
			return hChangeNotifyEventID;
		}
	}

	public static class HChangeNotifyFlags
	{

		public static HChangeNotifyFlags SHCNF_IDLIST = new HChangeNotifyFlags( 0x0000 );

		private int hChangeNotifyFlags;

		private HChangeNotifyFlags( int hChangeNotifyFlags )
		{
			this.hChangeNotifyFlags = hChangeNotifyFlags;
		}

		public int getValue( )
		{
			return hChangeNotifyFlags;
		}
	}

	public static boolean SHChangeNotify( HChangeNotifyEventID wEventId,
			HChangeNotifyFlags uFlags, int dwItem1, int dwItem2 )
			throws NativeException, IllegalAccessException
	{
		Native SHChangeNotify = new Native( DLL_NAME, "SHChangeNotify" );
		SHChangeNotify.setRetVal( Type.VOID );
		int pos = 0;
		SHChangeNotify.setParameter( pos++, wEventId.getValue( ) );
		SHChangeNotify.setParameter( pos++, wEventId.getValue( ) );
		SHChangeNotify.setParameter( pos++, dwItem1 );
		SHChangeNotify.setParameter( pos++, dwItem2 );
		SHChangeNotify.invoke( );
		pos = SHChangeNotify.getLastError( );
		return ( pos == 0 );
	}

}
