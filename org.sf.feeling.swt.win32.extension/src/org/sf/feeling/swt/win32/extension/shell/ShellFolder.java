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

package org.sf.feeling.swt.win32.extension.shell;

import org.sf.feeling.swt.win32.extension.shell.IShellFolder.CanonicalPIDL;
import org.sf.feeling.swt.win32.internal.extension.Extension;

/**
 * This class provides access to standard Windows shell folders.
 * <p>
 * <b>Note</b>: This functionality is available for Windows 98 / 2000 / XP.</br>
 * Under Windows NT 4.0 or Windows 95 Microsoft Internet Explorer with
 * <b>Desktop Update</b> must be installed.
 * 
 * @author <a href="mailto:cnfree2000@hotmail.com">cnfree</a>
 */
public class ShellFolder
{

	/**
	 * [2000/ME] Administrative Tools directory for current user.</br>
	 * path=&lt;user name&gt;\Start Menu\Programs\Administrative Tools
	 */
	public static final ShellFolder ADMINTOOLS = new ShellFolder( 0x0030 );

	/**
	 * [All] Non-localized Startup directory in the Start menu for current
	 * user.</br>
	 */
	public static final ShellFolder ALTSTARTUP = new ShellFolder( 0x001d );

	/**
	 * [IE4] Application data directory for current user.</br> path=&lt;user
	 * name&gt;\Application Data
	 */
	public static final ShellFolder APPDATA = new ShellFolder( 0x001a );

	/**
	 * [All] Recycle Bin.</br> path=&lt;desktop&gt;\Recycle Bin
	 */
	public static final ShellFolder BITBUCKET = new ShellFolder( 0x000a, true );

	/**
	 * [XP] Windows XP directory for files that will be burned to CD.</br>
	 * path=USERPROFILE\Local Settings\Application Data\Microsoft\CD Burning
	 */
	public static final ShellFolder CDBURN_AREA = new ShellFolder( 0x003b );

	/**
	 * [2000/ME] Administrative Tools directory for all users.</br> path=All
	 * Users\Start Menu\Programs\Administrative Tools
	 */
	public static final ShellFolder COMMON_ADMINTOOLS = new ShellFolder( 0x002f );

	/**
	 * [All] Non-localized Startup directory in the Start menu for all
	 * users.</br>
	 */
	public static final ShellFolder COMMON_ALTSTARTUP = new ShellFolder( 0x001e );

	/**
	 * [2000/ME] Application data directory for all users.</br> path=All
	 * Users\Application Data
	 */
	public static final ShellFolder COMMON_APPDATA = new ShellFolder( 0x0023 );

	/**
	 * [NT] Desktop directory for all users.</br> path=All Users\Desktop
	 */
	public static final ShellFolder COMMON_DESKTOPDIRECTORY = new ShellFolder( 0x0019 );

	/**
	 * [IE4] My Documents directory for all users.</br> path=All Users\Documents
	 */
	public static final ShellFolder COMMON_DOCUMENTS = new ShellFolder( 0x002e );

	/**
	 * [NT] Favorites directory for all users.</br> path=All Users\Favorites
	 */
	public static final ShellFolder COMMON_FAVORITES = new ShellFolder( 0x001f );

	/**
	 * [XP] Music directory for all users.</br> path=All Users\My Music
	 */
	public static final ShellFolder COMMON_MUSIC = new ShellFolder( 0x0035 );

	/**
	 * [XP] Image directory for all users.</br> path=All Users\My Pictures
	 */
	public static final ShellFolder COMMON_PICTURES = new ShellFolder( 0x0036 );

	/**
	 * [NT] Start menu "Programs" directory for all users.</br> path=All
	 * Users\Start Menu\Programs
	 */
	public static final ShellFolder COMMON_PROGRAMS = new ShellFolder( 0x0017 );

	/**
	 * [NT] Start menu root directory for all users.</br> path=All Users\Start
	 * Menu
	 */
	public static final ShellFolder COMMON_STARTMENU = new ShellFolder( 0x0016 );

	/**
	 * [NT] Start menu Startup directory for all users.</br> path=All
	 * Users\Startup
	 */
	public static final ShellFolder COMMON_STARTUP = new ShellFolder( 0x0018 );

	/**
	 * [NT] Document templates directory for all users.</br> path=All
	 * Users\Templates
	 */
	public static final ShellFolder COMMON_TEMPLATES = new ShellFolder( 0x002d );

	/**
	 * [XP] Video directory for all users.</br> path=All Users\My Video
	 */
	public static final ShellFolder COMMON_VIDEO = new ShellFolder( 0x0037 );

	/**
	 * [All] Control Panel applets.</br> path=My Computer\Control Panel
	 */
	public static final ShellFolder CONTROLS = new ShellFolder( 0x0003, true );

	/**
	 * [All] Cookies directory.
	 */
	public static final ShellFolder COOKIES = new ShellFolder( 0x0021 );

	/**
	 * [All] Namespace root (shown as "Desktop", but is parent to my computer,
	 * control panel, my documents, etc.)</br> path=&lt;desktop&gt;
	 */
	public static final ShellFolder DESKTOP = new ShellFolder( 0x0000, true );

	/**
	 * [All] Desktop directory (for desktop icons, folders, etc.) for the
	 * current user.</br> path=&lt;user name&gt;\Desktop
	 */
	public static final ShellFolder DESKTOPDIRECTORY = new ShellFolder( 0x0010 );

	/**
	 * [All] My Computer (drives and mapped network drives).</br> path=My
	 * Computer
	 */
	public static final ShellFolder DRIVES = new ShellFolder( 0x0011, true );

	/**
	 * [All] Favorites directory for the current user.</br> path=&lt;user
	 * name&gt;\Favorites
	 */
	public static final ShellFolder FAVORITES = new ShellFolder( 0x0006 );

	/**
	 * [All] Fonts directory.</br> path=windows\fonts
	 */
	public static final ShellFolder FONTS = new ShellFolder( 0x0014 );

	/**
	 * [All] Internet Explorer history items for the current user
	 */
	public static final ShellFolder HISTORY = new ShellFolder( 0x0022 );

	/**
	 * [All] Internet root
	 */
	public static final ShellFolder INTERNET = new ShellFolder( 0x0001, true );

	/**
	 * [IE4] Temporary Internet Files directory for the current user
	 */
	public static final ShellFolder INTERNET_CACHE = new ShellFolder( 0x0020 );

	/**
	 * [2000/ME] Local (non-roaming) application data directory for the current
	 * user.</br> path=&lt;user name&gt;\Local Settings\Applicaiton Data (non
	 * roaming)
	 */
	public static final ShellFolder LOCAL_APPDATA = new ShellFolder( 0x001c );

	/**
	 * logical "My Documents" desktop icon.</br>
	 */
	public static final ShellFolder MYDOCUMENTS = new ShellFolder( 0x000c, true );

	/**
	 * [All] My Music directory for the current user.</br> path="My Music"
	 * folder
	 */
	public static final ShellFolder MYMUSIC = new ShellFolder( 0x000d );

	/**
	 * [2000/ME] Image directory for the current user.</br> path="My Music"
	 * folder
	 */
	public static final ShellFolder MYPICTURES = new ShellFolder( 0x0027 );

	/**
	 * [XP] Video directory for the current user.</br> path="My Videos" folder
	 */
	public static final ShellFolder MYVIDEO = new ShellFolder( 0x000e );

	/**
	 * [All] My Network Places directory for the current user.</br>
	 * path=&lt;user name&gt;\nethood
	 */
	public static final ShellFolder NETHOOD = new ShellFolder( 0x0013 );

	/**
	 * [All] Root of network namespace (Network Neighbourhood).</br>
	 * path=Network Neighborhood (My Network Places)
	 */
	public static final ShellFolder NETWORK = new ShellFolder( 0x0012 );

	/**
	 * [All] My Documents directory for the current user.</br> path=My Documents
	 */
	public static final ShellFolder PERSONAL = new ShellFolder( 0x0005 );

	/**
	 * [All] List of installed printers.</br> path=My Computer\Printers
	 */
	public static final ShellFolder PRINTERS = new ShellFolder( 0x0004, true );

	/**
	 * [All] Network printers directory for the current user
	 */
	public static final ShellFolder PRINTHOOD = new ShellFolder( 0x001b, true );

	/**
	 * [2000/ME] The current user's profile directory.
	 */
	public static final ShellFolder PROFILE = new ShellFolder( 0x0028 );

	/**
	 * [XP] The directory that holds user profiles.</br>
	 * 
	 * @see ShellFolder#PROFILE
	 */
	public static final ShellFolder PROFILES = new ShellFolder( 0x003e );

	/**
	 * [2000/ME] Program Files directory.</br> path=C:\Program Files
	 */
	public static final ShellFolder PROGRAM_FILES = new ShellFolder( 0x0026 );

	/**
	 * [2000] Directory for files that are used by several applications. Usually
	 * Program Files\Common.</br> path=C:\Program Files\Common
	 */
	public static final ShellFolder PROGRAM_FILES_COMMON = new ShellFolder( 0x002b );

	/**
	 * [All] Start menu "Programs" directory for the current user.</br>
	 * path=Start Menu\Programs
	 */
	public static final ShellFolder PROGRAMS = new ShellFolder( 0x0002 );

	/**
	 * [All] Recent Documents directory for the current user.</br> path=&lt;user
	 * name&gt;\Recent
	 */
	public static final ShellFolder RECENT = new ShellFolder( 0x0008 );

	/**
	 * [All] "Send To" directory for the current user.</br> path=&lt;user
	 * name&gt;\SendTo
	 */
	public static final ShellFolder SEND_TO = new ShellFolder( 0x0009 );

	/**
	 * [All] Start Menu root for the current user.</br> path=&lt;user
	 * name&gt;\Start Menu
	 */
	public static final ShellFolder START_MENU = new ShellFolder( 0x000b );

	/**
	 * [All] Start Menu "Startup" folder for the current user.</br> path=Start
	 * Menu\Programs\Startup
	 */
	public static final ShellFolder STARTUP = new ShellFolder( 0x0007 );

	/**
	 * [2000/ME] System directory. Usually \Windows\System32.</br>
	 * path=GetSystemDirectory()
	 */
	public static final ShellFolder SYSTEM = new ShellFolder( 0x0025 );

	/**
	 * [All] Document templates directory for the current user.</br>
	 */
	public static final ShellFolder TEMPLATES = new ShellFolder( 0x0015 );

	/**
	 * [2000/ME] Windows root directory, can also be accessed via the
	 * environment variables %windir% or %SYSTEMROOT%.</br>
	 * path=GetWindowsDirectory()
	 */
	public static final ShellFolder WINDOWS = new ShellFolder( 0x0024 );

	/**
	 * [All] My Computer (drives and mapped network drives).</br> path=My
	 * Computer
	 */
	public static final ShellFolder COMPUTER = new ShellFolder( 0x0011 );

	/**
	 * Network and Dial-up Connections
	 */
	public static final ShellFolder NET_CONNECTIONS = new ShellFolder( 0x0031 );

	private boolean _virtual;

	private int _folderID;

	/**
	 * 
	 * @param folderID
	 *            identifying a standard shell folder.
	 * @param virtual
	 *            specifies that the folder is virtual.
	 */
	private ShellFolder( int folderID, boolean virtual )
	{
		this._folderID = folderID;
		_virtual = virtual;
	}

	/**
	 * 
	 * @param folderID
	 *            identifying a standard shell folder.
	 */
	private ShellFolder( int folderID )
	{
		this( folderID, false );
	}

	/**
	 * 
	 * @return folder ID.
	 */
	public int getFolderID( )
	{
		return _folderID;
	}

	/**
	 * 
	 * @return true, if the folder is virtual; otherwise false.
	 */
	public boolean isVirtual( )
	{
		return _virtual;
	}

	/**
	 * Returns absolute path of the shell folder. If the folder does not exist,
	 * returns empty string.
	 * 
	 * @return absolute path of the shell folder.
	 */
	public String getAbsolutePath( int hwnd )
	{
		return getSysFolder( hwnd, _folderID );
	}

	/**
	 * Get specified shell folder absolute path.
	 * 
	 * @param hWnd
	 *            system handle.
	 * @param folderId
	 *            shell folder id.
	 * @return specified shell folder absolute path.
	 */
	public static String getSysFolder( int hWnd, int folderId )
	{
		return Extension.SHGetSpecialFolderPath( hWnd, folderId, false );
	}

	/**
	 * Get specified shell folder absolute path.
	 * 
	 * @param hWnd
	 *            system handle.
	 * @param folder
	 *            shell folder
	 * @return specified shell folder absolute path.
	 */
	public static String getSysFolder( int hWnd, ShellFolder folder )
	{
		return Extension.SHGetSpecialFolderPath( hWnd,
				folder.getFolderID( ),
				false );
	}

	/**
	 * Get specified shell folder canonical PIDL.
	 * 
	 * @param folder
	 *            shell folder
	 * 
	 * @see IShellFolder
	 * @return shell folder canonical PIDL.
	 */
	public static CanonicalPIDL getFolderPIDL( ShellFolder folder )
	{
		return new CanonicalPIDL( Extension.SHGetSpecialFolderLocation( folder.getFolderID( ) ) );
	}
}
