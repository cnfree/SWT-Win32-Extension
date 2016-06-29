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

package org.sf.feeling.swt.win32.extension.shell;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.SHELLEXECUTEINFO;
import org.eclipse.swt.internal.win32.TCHAR;
import org.sf.feeling.swt.win32.extension.Win32;
import org.sf.feeling.swt.win32.extension.jna.Function;
import org.sf.feeling.swt.win32.extension.jna.ptr.IntByReference;
import org.sf.feeling.swt.win32.internal.extension.Extension;
import org.sf.feeling.swt.win32.internal.extension.SHFILEINFO;
import org.sf.feeling.swt.win32.internal.extension.SHFILEINFOA;
import org.sf.feeling.swt.win32.internal.extension.SHFILEINFOW;

public class IShellFolder
{

	public final static class CanonicalPIDL extends PIDL
	{

		private RelativePIDL relativePIDL;

		CanonicalPIDL( int pidl )
		{
			super( pidl, true );
		}

		public CanonicalPIDL( int pidl, RelativePIDL relativePIDL )
		{
			super( pidl, true );
			this.relativePIDL = relativePIDL;
		}

		CanonicalPIDL( PIDL pidl )
		{
			super( pidl.pidl, true );
		}

		public RelativePIDL getRelativePIDL( )
		{
			return relativePIDL;
		}
	}

	public static class DesktopFolder extends IShellFolder
	{

		DesktopFolder( int hWnd, int handle, CanonicalPIDL pidl )
		{
			super( hWnd, handle, pidl );
		}

		public void dispose( )
		{
			Extension.ReleaseIShellFolder( handle );
			IShellFolder[] shellFolders = (IShellFolder[]) ShellFolderList.toArray( new IShellFolder[0] );

			for ( int i = 0; i < shellFolders.length; i++ )
			{
				IShellFolder shellFolder = shellFolders[i];
				if ( shellFolder.handle == handle )
				{
					shellFolder.handle = 0;
					ShellFolderList.remove( shellFolder );
				}
			}
			handle = 0;
		}

		public String getDisplayName( )
		{
			CanonicalPIDL handle = ShellFolder.getFolderPIDL( ShellFolder.DESKTOP );
			String displayName = IShellFolder.getPIDLDisplayName( this,
					handle,
					IShellFolder.SHGDNF.NORMAL );
			handle.dispose( );
			return displayName;
		}

		public Image getIcon( boolean large )
		{
			return ShellIcon.getSysFolderIcon( ShellFolder.DESKTOP,
					large ? ShellIcon.ICON_LARGE : ShellIcon.ICON_SMALL );
		}
	}

	static class PIDL
	{

		private boolean absolute;
		private int pidl;

		PIDL( int pidl, boolean absolute )
		{
			this.pidl = pidl;
			this.absolute = absolute;
			PIDLList.add( this );
		}

		public void dispose( )
		{
			if ( ppMalloc != 0 && pidl != 0 )
			{
				Extension.ReleaseItemIdList( ppMalloc, pidl );

				PIDL[] pidis = (PIDL[]) PIDLList.toArray( new PIDL[0] );

				for ( int i = 0; i < pidis.length; i++ )
				{
					PIDL PIDL = pidis[i];
					if ( PIDL.pidl == pidl )
					{
						PIDL.pidl = 0;
						PIDLList.remove( PIDL );
					}
				}
			}
		}

		int getPidl( )
		{
			return pidl;
		}

		public boolean isCanonical( )
		{
			return absolute;
		}

		public boolean isDisposed( )
		{
			return pidl == 0;
		}
	}

	public final static class RelativePIDL extends PIDL
	{

		private IShellFolder parentFolder;

		RelativePIDL( int pidl )
		{
			super( pidl, false );
		}

		public RelativePIDL( int pidl, IShellFolder parentFolder )
		{
			super( pidl, false );
			this.parentFolder = parentFolder;
		}

		RelativePIDL( PIDL pidl )
		{
			super( pidl.pidl, false );
		}

		public IShellFolder getParentFolder( )
		{
			return parentFolder;
		}
	}

	/**
	 * Attributes that can be retrieved on an item (file or folder) or set of
	 * items.
	 * 
	 * @see IShellFolder#getAttributesOf(RelativePIDL, int)
	 * @see IShellFolder#getAttributes(CanonicalPIDL)
	 * 
	 * @author cnfree
	 */
	public static interface SFGAO
	{

		/**
		 * The specified items can be hosted inside a web browser or Windows
		 * Explorer frame.
		 */
		int BROWSABLE = 0x8000000;
		/**
		 * The specified items can be copied.
		 */
		int CANCOPY = 1;
		/**
		 * The specified items can be deleted.
		 */
		int CANDELETE = 0x20;
		/**
		 * Shortcuts can be created for the specified items.
		 */
		int CANLINK = 4;
		/**
		 * Not supported.
		 */
		int CANMONIKER = 0x400000;
		/**
		 * The specified items can be moved.
		 */
		int CANMOVE = 2;
		/**
		 * The specified items can be renamed. Note that this value is
		 * essentially a suggestion; not all namespace clients allow items to be
		 * renamed. However, those that do must have this attribute set.
		 */
		int CANRENAME = 0x10;

		/**
		 * This flag is a mask for the capability attributes: {@link #CANCOPY},
		 * {@link #CANMOVE}, {@link #CANLINK}, {@link #CANRENAME},
		 * {@link #CANDELETE}, {@link #HASPROPSHEET}, and {@link #DROPTARGET}.
		 * Callers normally do not use this value.
		 */
		int CAPABILITYMASK = 0x177;
		/**
		 * The specified items are compressed.
		 */
		int COMPRESSED = 0x4000000;
		/**
		 * This flag is a mask for content attributes, at present only
		 * {@link #HASSUBFOLDER}. Callers normally do not use this value.
		 */
		int CONTENTSMASK = 0x80000000;
		/**
		 * Do not use.
		 */
		int DISPLAYATTRMASK = 0xfc000;
		/**
		 * The specified items are drop targets.
		 */
		int DROPTARGET = 0x100;
		/**
		 * The specified items are encrypted and might require special
		 * presentation.
		 */
		int ENCRYPTED = 0x2000;
		/**
		 * The specified folders are either file system folders or contain at
		 * least one descendant (child, grandchild, or later) that is a file
		 * system ({@link #FILESYSTEM}) folder.
		 */
		int FILESYSANCESTOR = 0x10000000;
		/**
		 * The specified folders or files are part of the file system (that is,
		 * they are files, directories, or root directories). The parsed names
		 * of the items can be assumed to be valid Win32 file system paths.
		 * These paths can be either UNC or drive-letter based.
		 */
		int FILESYSTEM = 0x40000000;
		/**
		 * The specified items are folders. Some items can be flagged with both
		 * {@link #STREAM} and {@link #FOLDER}, such as a compressed file with a
		 * .zip file name extension. Some applications might include this flag
		 * when testing for items that are both files and containers.
		 */
		int FOLDER = 0x20000000;
		/**
		 * The specified items are shown as dimmed and unavailable to the user.
		 */
		int GHOSTED = 0x8000;
		/**
		 * The specified items have property sheets.
		 */
		int HASPROPSHEET = 0x40;
		/**
		 * Not supported.
		 */
		int HASSTORAGE = 0x400000;
		/**
		 * The specified folders have subfolders. The {@link #HASSUBFOLDER}
		 * attribute is only advisory and might be returned by Shell folder
		 * implementations even if they do not contain subfolders. Note,
		 * however, that the converse-failing to return {@link #HASSUBFOLDER}
		 * -definitively states that the folder objects do not have subfolders.
		 * 
		 * Returning {@link #HASSUBFOLDER} is recommended whenever a significant
		 * amount of time is required to determine whether or not any subfolders
		 * exist. For example, the Shell always returns {@link #HASSUBFOLDER}
		 * when a folder is located on a network drive.
		 */
		int HASSUBFOLDER = 0x80000000;
		/**
		 * The item is hidden and should not be displayed unless the Show hidden
		 * files and folders option is enabled in Folder Settings.
		 */
		int HIDDEN = 0x80000;
		/**
		 * Accessing the item (through IStream or other storage interfaces) is
		 * expected to be a slow operation. Applications should avoid accessing
		 * items flagged with {@link #ISSLOW}.
		 */
		int ISSLOW = 0x4000;
		/**
		 * The specified items are shortcuts.
		 */
		int LINK = 0x10000;
		/**
		 * The items contain new content, as defined by the particular
		 * application.
		 */
		int NEWCONTENT = 0x200000;
		/**
		 * The items are nonenumerated items and should be hidden. They are not
		 * returned through an enumerator such as that created by the
		 * {@link IShellFolder#getPIDLItems(int)} method.
		 */
		int NONENUMERATED = 0x100000;
		/**
		 * The specified items are read-only. In the case of folders, this means
		 * that new items cannot be created in those folders.
		 */
		int READONLY = 0x40000;
		/**
		 * The specified items are on removable media or are themselves
		 * removable devices.
		 */
		int REMOVABLE = 0x2000000;
		/**
		 * The specified objects are shared.
		 */
		int SHARE = 0x20000;
		/**
		 * The specified items can be bound to an IStorage object through
		 * {@link IShellFolder#getPIDLItems(int)}.
		 */
		int STORAGE = 8;
		/**
		 * Children of this item are accessible through IStream or IStorage.
		 * Those children are flagged with {@link #STORAGE} or {@link #STREAM}.
		 */
		int STORAGEANCESTOR = 0x800000;
		/**
		 * This flag is a mask for the storage capability attributes:
		 * {@link #STORAGE}, {@link #LINK}, {@link #READONLY}, {@link #STREAM},
		 * {@link #STORAGEANCESTOR}, {@link #FILESYSANCESTOR}, {@link #FOLDER},
		 * and {@link #FILESYSTEM}. Callers normally do not use this value.
		 */
		int STORAGECAPMASK = 0x70c50008;
		/**
		 * Indicates that the item has a stream associated with it. That stream
		 * can be accessed through a call to
		 * {@link IShellFolder#getPIDLItems(int)}.
		 */
		int STREAM = 0x400000;
		/**
		 * Only for Win7 or later. The specified items are system items.
		 */
		int SYSTEM = 0x00001000;

		/**
		 * When specified as input, {@link #VALIDATE} instructs the folder to
		 * validate that the items contained in a folder or Shell item array
		 * exist. If one or more of those items do not exist,
		 * {@link IShellFolder#getAttributesOf(RelativePIDL, int)} return a
		 * failure code. This flag is never returned as an [out] value.
		 */
		int VALIDATE = 0x1000000;
	}

	/**
	 * Determines the types of items included in an enumeration. These values
	 * are used with the {@link IShellFolder#getPIDLItems(int)} method.
	 * 
	 * @see IShellFolder#getPIDLItems(int)
	 * 
	 * @author cnfree
	 * 
	 */
	public static interface SHCONTF
	{

		/**
		 * Include items that are folders in the enumeration.
		 */
		int FOLDERS = 32;
		/**
		 * Include hidden items in the enumeration. This does not include hidden
		 * system items.
		 */
		int INCLUDEHIDDEN = 128;
		/**
		 * No longer used; always assumed.
		 */
		int INIT_ON_FIRST_NEXT = 256;
		/**
		 * The calling application is looking for printer objects.
		 */
		int NETPRINTERSRCH = 512;
		/**
		 * Include items that are not folders in the enumeration.
		 */
		int NONFOLDERS = 64;
		/**
		 * The calling application is looking for resources that can be shared.
		 */
		int SHAREABLE = 1024;
		/**
		 * Include items with accessible storage and their ancestors, including
		 * hidden items.
		 */
		int STORAGE = 2048;
	}

	/**
	 * Defines the values used with the
	 * {@link IShellFolder#getPIDLDisplayName(RelativePIDL, int)} specify the
	 * type of file or folder names used by those methods.
	 * 
	 * @see IShellFolder#getPIDLDisplayName(DesktopFolder, CanonicalPIDL, int)
	 * @see IShellFolder#getPIDLDisplayName(RelativePIDL, int)
	 * 
	 * @author cnfree
	 * 
	 */
	public static interface SHGDNF
	{

		/**
		 * The name is displayed in an address bar combo box.
		 */
		int FORADDRESSBAR = 0x4000;
		/**
		 * The name is used for in-place editing when the user renames the item.
		 */
		int FOREDITING = 0x1000;
		/**
		 * The name is used for parsing. That is, it can be passed to
		 * {@link IShellFolder#getPIDLDisplayName(RelativePIDL, int)} to recover
		 * the object's PIDL. The form this name takes depends on the particular
		 * object. When {@link #FORPARSING} is used alone, the name is relative
		 * to the desktop. When combined with {@link #INFOLDER}, the name is
		 * relative to the folder from which the request was made.
		 * 
		 * @see IShellFolder#getPIDLName(RelativePIDL)
		 * @see IShellFolder#getPIDLCanonicalName(DesktopFolder, CanonicalPIDL)
		 * @see IShellFolder#getCanonicalPIDL(DesktopFolder, String)
		 */
		int FORPARSING = 0x8000;
		/**
		 * The name is relative to the folder from which the request was made.
		 * This is the name display to the user when used in the context of the
		 * folder. For example, it is used in the view and in the address bar
		 * path segment for the folder. This name should not include
		 * disambiguation information-for instance "username" instead of
		 * "username (on Machine)" for a particular user's folder.
		 * 
		 * Use this flag in combinations with {@link #FORPARSING} and
		 * {@link #FOREDITING}.
		 */
		int INFOLDER = 0x1;
		/**
		 * When not combined with another flag, return the parent-relative name
		 * that identifies the item, suitable for displaying to the user. This
		 * name often does not include extra information such as the file name
		 * extension and does not need to be unique. This name might include
		 * information that identifies the folder that contains the item. For
		 * instance, this flag could cause
		 * {@link IShellFolder#getPIDLDisplayName} to return the string
		 * "username (on Machine)" for a particular user's folder.
		 */
		int NORMAL = 0;
	}

	private static DesktopFolder desktop;

	private static List PIDLList = new ArrayList( );

	private static int ppMalloc;

	private static final String SHEll32_LIB = "shell32";

	private static List ShellFolderList = new ArrayList( );

	public static int getAttributes( PIDL pidl )
	{
		if ( pidl == null || pidl.getPidl( ) == 0 )
			return 0;
		SHFILEINFO shInfo;
		if ( Extension.IsUnicode )
			shInfo = new SHFILEINFOW( );
		else
			shInfo = new SHFILEINFOA( );
		Extension.SHGetFileInfo( pidl.getPidl( ),
				0,
				shInfo,
				SHFILEINFO.sizeof,
				Win32.SHGFI_PIDL | Win32.SHGFI_ATTRIBUTES );
		return shInfo.dwAttributes;
	}

	public static CanonicalPIDL getCanonicalPIDL( DesktopFolder desktop,
			String pidlName )
	{
		RelativePIDL pidl = desktop.getPIDL( pidlName );
		if ( pidl != null )
			return desktop.getCanonicalPIDL( pidl );
		return null;
	}
	public static DesktopFolder getDesktopFolder( int hWnd )
	{
		if ( !( desktop == null || desktop.isDisposed( ) ) )
		{
			return desktop;
		}
		init( );
		try
		{
			Function function = new Function( SHEll32_LIB, "SHGetDesktopFolder" );
			IntByReference outReference = new IntByReference( );
			function.invoke_I( outReference.getPointer( ).getPointer( ) );
			function.close( );
			if ( outReference.getValue( ) == 0 )
				return null;

			CanonicalPIDL pidl = ShellFolder.getFolderPIDL( ShellFolder.DESKTOP );
			desktop = new DesktopFolder( hWnd, outReference.getValue( ), pidl );
			return desktop;
		}
		catch ( Exception e )
		{
			SWT.error( SWT.ERROR_FAILED_LOAD_LIBRARY );
			return null;
		}
	}

	public static Image getIcon( PIDL pidl, boolean large )
	{
		if ( pidl == null || pidl.getPidl( ) == 0 )
			return null;
		int type = 0;
		if ( !large )
			type |= Win32.ICON_SMALL;
		else
			type |= Win32.ICON_LARGE;

		SHFILEINFO shInfo;
		if ( Extension.IsUnicode )
			shInfo = new SHFILEINFOW( );
		else
			shInfo = new SHFILEINFOA( );
		Extension.SHGetFileInfo( pidl.getPidl( ),
				0,
				shInfo,
				SHFILEINFO.sizeof,
				Win32.SHGFI_ICON | Win32.SHGFI_PIDL | type );
		if ( shInfo.hIcon != 0 )
		{
			return Image.win32_new( null, SWT.ICON, shInfo.hIcon );
		}
		return null;
	}

	public static String getPIDLCanonicalName( DesktopFolder desktop,
			CanonicalPIDL pidl )
	{
		RelativePIDL relative = new RelativePIDL( pidl );
		return desktop.getPIDLName( relative );
	}

	public static String getPIDLDisplayName( DesktopFolder desktop,
			CanonicalPIDL pidl, int flags )
	{
		RelativePIDL relative = new RelativePIDL( pidl );
		return desktop.getPIDLDisplayName( relative, flags );
	}

	public static IShellFolder getRelativeParent( DesktopFolder desktop,
			CanonicalPIDL pidl )
	{
		if ( pidl == null || pidl.getPidl( ) == 0 || desktop == null )
			return null;
		int parentHandle = Extension.IShellFolder_GetParent( pidl.getPidl( ) );
		return new IShellFolder( desktop.hWnd, parentHandle );
	}

	public static RelativePIDL getRelativePIDL( CanonicalPIDL pidl )
	{
		if ( pidl == null || pidl.getPidl( ) == 0 )
			return null;
		int relativePIDL = Extension.IShellFolder_GetRelativeHandle( pidl.getPidl( ) );
		if ( relativePIDL == 0 )
			return null;
		return new RelativePIDL( relativePIDL );
	}

	public static String getTypeName( PIDL pidl )
	{
		if ( pidl == null || pidl.getPidl( ) == 0 )
			return null;
		SHFILEINFO shInfo;
		if ( Extension.IsUnicode )
			shInfo = new SHFILEINFOW( );
		else
			shInfo = new SHFILEINFOA( );
		Extension.SHGetFileInfo( pidl.getPidl( ),
				0,
				shInfo,
				SHFILEINFO.sizeof,
				Win32.SHGFI_PIDL | Win32.SHGFI_TYPENAME );

		if ( !Extension.IsUnicode )
		{
			byte[] data = ( (SHFILEINFOA) shInfo ).szTypeName;
			TCHAR tchar = new TCHAR( 0, data.length );
			tchar.bytes = data;
			return tchar.toString( 0, tchar.strlen( ) );
		}
		else
		{
			char[] data = ( (SHFILEINFOW) shInfo ).szTypeName;
			TCHAR tchar = new TCHAR( 0, data.length );
			tchar.chars = data;
			return tchar.toString( 0, tchar.strlen( ) );
		}
	}

	public static boolean hasSubFolder( PIDL pidl )
	{
		if ( pidl == null || pidl.getPidl( ) == 0 )
			return false;
		int flag = SFGAO.HASSUBFOLDER;
		int result = getAttributes( pidl );
		return ( result & flag ) != 0;
	}

	private static void init( )
	{
		if ( ppMalloc == 0 )
		{
			ppMalloc = Extension.SHGetMalloc( );
		}
	}

	public static boolean isFileSystem( PIDL pidl )
	{
		if ( pidl == null || pidl.getPidl( ) == 0 )
			return true;
		int flag = SFGAO.FILESYSTEM;
		int result = getAttributes( pidl );
		return ( result & flag ) != 0;
	}

	public static boolean isFolder( PIDL pidl )
	{
		if ( pidl == null || pidl.getPidl( ) == 0 )
			return false;
		int flag = SFGAO.FOLDER;
		int result = getAttributes( pidl );
		return ( result & flag ) != 0;
	}

	public static boolean isHidden( PIDL pidl )
	{
		if ( pidl == null || pidl.getPidl( ) == 0 )
			return false;
		int flag = SFGAO.HIDDEN;
		int result = getAttributes( pidl );
		return ( result & flag ) != 0;
	}

	public static boolean isLinkFile( PIDL pidl )
	{
		if ( pidl == null || pidl.getPidl( ) == 0 )
			return false;
		int flag = SFGAO.LINK;
		int result = getAttributes( pidl );
		return ( result & flag ) != 0;
	}

	public static boolean isStorage( PIDL pidl )
	{
		if ( pidl == null || pidl.getPidl( ) == 0 )
			return false;
		int flag = SFGAO.FOLDER | SFGAO.STREAM;
		int result = getAttributes( pidl );
		return ( result & flag ) != 0;
	}

	public static boolean isSystemFolder( PIDL pidl )
	{
		PIDL drives = ShellFolder.getFolderPIDL( ShellFolder.DRIVES );
		String systemFoldeType = IShellFolder.getTypeName( drives );
		drives.dispose( );
		return systemFoldeType.equals( IShellFolder.getTypeName( pidl ) );
	}

	public static boolean launchPIDL( CanonicalPIDL pidl )
	{
		if ( pidl == null || pidl.getPidl( ) == 0 )
			return false;
		SHELLEXECUTEINFO info = new SHELLEXECUTEINFO( );
		info.cbSize = SHELLEXECUTEINFO.sizeof;
		info.lpIDList = pidl.getPidl( );
		info.nShow = OS.SW_SHOW;
		info.fMask = Win32.SEE_MASK_IDLIST;
		boolean result = OS.ShellExecuteEx( info );
		return result;
	}

	public static void releaseShellMalloc( )
	{
		if ( ppMalloc != 0 )
		{
			if ( desktop != null && !desktop.isDisposed( ) )
				desktop.dispose( );

			PIDL[] pidis = (PIDL[]) PIDLList.toArray( new PIDL[0] );
			for ( int i = 0; i < pidis.length; i++ )
			{
				pidis[i].dispose( );
			}
			Extension.ReleaseShellMalloc( ppMalloc );
			ppMalloc = 0;
		}
	}

	CanonicalPIDL bindingPIDL;
	int handle;
	int hWnd;

	
	IShellFolder( int hWnd, int handle )
	{
		this.hWnd = hWnd;
		this.handle = handle;
		ShellFolderList.add( this );
	}

	IShellFolder( int hWnd, int handle, CanonicalPIDL pidl )
	{
		this.hWnd = hWnd;
		this.handle = handle;
		this.bindingPIDL = pidl;
		ShellFolderList.add( this );
	}

	public void dispose( )
	{
		if ( handle != 0 )
		{
			if ( desktop != null && handle != desktop.handle )
			{
				Extension.ReleaseIShellFolder( handle );
				IShellFolder[] shellFolders = (IShellFolder[]) ShellFolderList.toArray( new IShellFolder[0] );

				for ( int i = 0; i < shellFolders.length; i++ )
				{
					IShellFolder shellFolder = shellFolders[i];
					if ( shellFolder.handle == handle )
					{
						shellFolder.handle = 0;
						ShellFolderList.remove( shellFolder );
					}
				}
				handle = 0;
			}
		}
	}

	public int getAttributesOf( RelativePIDL pidl, int flags )
	{
		if ( handle == 0 || pidl == null || pidl.getPidl( ) == 0 )
			return 0;
		return Extension.IShellFolder_GetAttributesOf( handle,
				pidl.getPidl( ),
				flags );
	}

	public CanonicalPIDL getBindingPIDL( )
	{
		return bindingPIDL;
	}

	public CanonicalPIDL getCanonicalPIDL( RelativePIDL pidl )
	{
		if ( this.bindingPIDL != null )
		{
			int point = Extension.IShellFolder_ConcatPIDLs( this.bindingPIDL.getPidl( ),
					pidl.getPidl( ) );
			return new CanonicalPIDL( point, pidl );
		}
		return null;
	}

	public RelativePIDL getFullyQualPIDL( RelativePIDL pidl )
	{
		if ( handle == 0 || pidl == null || pidl.getPidl( ) == 0 )
			return null;
		String name = this.getPIDLName( pidl );
		if ( name != null )
			return getDesktopFolder( handle ).getPIDL( name );
		return null;
	}

	public IExtractIcon getIExtractIcon( RelativePIDL pidl )
	{
		if ( handle == 0 || pidl == null || pidl.getPidl( ) == 0 )
			return null;
		int pExtractIcon = Extension.IShellFolder_GetIExtractIcon( handle,
				hWnd,
				pidl.getPidl( ) );
		if ( pExtractIcon != 0 )
			return new IExtractIcon( pExtractIcon );
		return null;
	}

	public RelativePIDL getPIDL( String name )
	{
		if ( handle == 0 || name == null )
			return null;
		TCHAR lpszInfo = new TCHAR( 0, name, true );
		int pidl = Extension.IShellFolder_ParseDisplayName( handle,
				hWnd,
				lpszInfo.chars );
		if ( pidl == 0 )
			return null;
		return new RelativePIDL( pidl );
	}

	public String getPIDLDisplayName( RelativePIDL pidl, int flags )
	{
		if ( handle == 0 || pidl == null || pidl.getPidl( ) == 0 )
			return null;
		return Extension.IShellFolder_GetDisplayNameOf( handle,
				pidl.getPidl( ),
				flags );
	}

	public RelativePIDL[] getPIDLItems( int flags )
	{
		if ( handle == 0 )
			return new RelativePIDL[0];
		List pidls = Extension.IShellFolder_EnumObjects( handle, hWnd, flags );
		if ( pidls == null )
			return new RelativePIDL[0];
		RelativePIDL[] items = new RelativePIDL[pidls.size( )];
		for ( int i = 0; i < items.length; i++ )
		{
			items[i] = new RelativePIDL( ( (Integer) pidls.get( i ) ).intValue( ),
					this );
		}
		return items;
	}

	public IShellFolder getPIDLItemsHolder( RelativePIDL pidl )
	{
		if ( handle == 0 || pidl == null || pidl.getPidl( ) == 0 )
			return null;
		int point = Extension.IShellFolder_BindToObject( handle, pidl.getPidl( ) );
		if ( point != 0 )
		{
			IShellFolder folder = new IShellFolder( hWnd, point );
			folder.bindingPIDL = getCanonicalPIDL( pidl );
			return folder;
		}
		else
			return null;
	}

	public String getPIDLName( RelativePIDL pidl )
	{
		if ( handle == 0 || pidl == null || pidl.getPidl( ) == 0 )
			return null;
		return Extension.IShellFolder_GetDisplayNameOf( handle,
				pidl.getPidl( ),
				IShellFolder.SHGDNF.FORPARSING );
	}

	public boolean isDisposed( )
	{
		return handle == 0;
	}

}
