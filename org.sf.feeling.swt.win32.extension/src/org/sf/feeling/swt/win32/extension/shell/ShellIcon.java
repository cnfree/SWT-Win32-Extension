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

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.internal.win32.TCHAR;
import org.sf.feeling.swt.win32.extension.Win32;
import org.sf.feeling.swt.win32.internal.extension.Extension;
import org.sf.feeling.swt.win32.internal.extension.SHFILEINFO;
import org.sf.feeling.swt.win32.internal.extension.SHFILEINFOA;
import org.sf.feeling.swt.win32.internal.extension.SHFILEINFOW;

/**
 * Shell Icon Utility class.
 * 
 * @author <a href="mailto:cnfree2000@hotmail.com">cnfree</a>
 * 
 */
public class ShellIcon
{

	public static final int ICON_SMALL = 1 << 16;

	public static final int ICON_LARGE = 1 << 15;

	private static int pidl;

	/**
	 * Get system icons list.
	 * 
	 * @param style
	 *            icon style, {@link #ICON_SMALL} or {@link #ICON_LARGE}
	 * @return System icons list.
	 */
	public static Image[] getSystemIcons( int style )
	{
		return getFileIcons( new File( Extension.GetSystemDirectory( )
				+ "\\shell32.dll" ), style );
	}

	/**
	 * Get specified system icons list.
	 * 
	 * @param index
	 *            a zero-based index of the icon to retrieve.
	 * @param style
	 *            icon style, {@link #ICON_SMALL} or {@link #ICON_LARGE}
	 * @return Specified system icons list
	 */
	public static Image[] getSystemIcons( int index, int style )
	{
		return getFileIcons( new File( Extension.GetSystemDirectory( )
				+ "\\shell32.dll" ), index, style );
	}

	/**
	 * Retrieves icons from the specified executable file.
	 * 
	 * @param file
	 *            is a file name to extract the icon from.
	 * @param style
	 *            icon style, one of {@link #ICON_SMALL}or {@link #ICON_LARGE}.
	 * 
	 * @return Returns icons that is contained in the specified file.
	 */
	public static Image[] getFileIcons( File file, int style )
	{
		TCHAR lpszFile = new TCHAR( 0, file.getAbsolutePath( ), true );
		int totleIcon = Extension.ExtractIconEx( lpszFile, -1, null, null, 0 );
		if ( totleIcon < 1 )
			return new Image[0];
		else
		{
			List imageList = new LinkedList( );
			for ( int i = 0; i < totleIcon; i++ )
			{
				int[] phiconSmall = new int[1];
				int[] phiconLarge = new int[1];
				if ( ( style & ICON_LARGE ) > 0 )
				{
					Extension.ExtractIconEx( lpszFile, i, phiconLarge, null, 1 );
					if ( phiconLarge[0] != 0 )
						imageList.add( Image.win32_new( null,
								SWT.ICON,
								phiconLarge[0] ) );
				}
				if ( ( style & ICON_SMALL ) > 0 )
				{
					Extension.ExtractIconEx( lpszFile, i, null, phiconSmall, 1 );
					if ( phiconSmall[0] != 0 )
						imageList.add( Image.win32_new( null,
								SWT.ICON,
								phiconSmall[0] ) );

				}
			}
			Image[] images = new Image[imageList.size( )];
			imageList.toArray( images );
			return images;
		}
	}

	/**
	 * Extracts an associated icon found in a file or an icon found in an
	 * associated executable file.
	 * 
	 * @param hWnd
	 *            Specify a window handle.
	 * @param file
	 *            is a file name to extract the icon from.
	 * @param index
	 *            a zero-based index of the icon to retrieve.
	 * @return Returns an icon representation of an image that is contained in
	 *         the specified file.
	 */
	public static Image getFileAssociatedIcons( int hWnd, File file, int index )
	{
		int iconhWnd = Extension.ExtractAssociatedIcon( hWnd,
				file.getAbsolutePath( ),
				index );
		return Image.win32_new( null, SWT.ICON, iconhWnd );
	}

	/**
	 * Extracts an associated icon found in a file or an icon found in an
	 * associated executable file.
	 * 
	 * @param hWnd
	 *            Specify a window handle.
	 * @param file
	 *            is a file name to extract the icon from.
	 * @return Returns an icon representation of an image that is contained in
	 *         the specified file.
	 */
	public static Image getFileAssociatedIcons( int hWnd, File file )
	{
		return getFileAssociatedIcons( hWnd, file, -1 );
	}

	/**
	 * Retrieves icons from the specified executable file.
	 * 
	 * @param file
	 *            is a file name to extract the icon from.
	 * @param index
	 *            a zero-based index of the icon to retrieve.
	 * @param style
	 *            icon style, {@link #ICON_SMALL} or {@link #ICON_LARGE}.
	 * @return Returns icons that is contained in the specified file.
	 */
	public static Image[] getFileIcons( File file, int index, int style )
	{
		TCHAR lpszFile = new TCHAR( 0, file.getAbsolutePath( ), true );
		int totleIcon = Extension.ExtractIconEx( lpszFile, -1, null, null, 0 );
		if ( totleIcon < 1 || index < -1 || index >= totleIcon )
			return new Image[0];
		else
		{
			List imageList = new LinkedList( );
			int[] phiconSmall = new int[1];
			int[] phiconLarge = new int[1];

			if ( ( style & ICON_LARGE ) > 0 )
			{
				Extension.ExtractIconEx( lpszFile, index, phiconLarge, null, 1 );
				if ( phiconLarge[0] != 0 )
					imageList.add( Image.win32_new( null,
							SWT.ICON,
							phiconLarge[0] ) );
			}
			if ( ( style & ICON_SMALL ) > 0 )
			{
				Extension.ExtractIconEx( lpszFile, index, null, phiconSmall, 1 );
				if ( phiconSmall[0] != 0 )
					imageList.add( Image.win32_new( null,
							SWT.ICON,
							phiconSmall[0] ) );
			}
			Image[] images = new Image[imageList.size( )];
			imageList.toArray( images );
			return images;
		}
	}

	/**
	 * Retrieves the icon of specified shell folder.
	 * 
	 * @param folder
	 *            shell folder.
	 * @param style
	 *            icon style.
	 * @return the icon of specified shell folder.
	 * 
	 */
	public static Image getSysFolderIcon( ShellFolder folder, int style )
	{
		return getSysFolderIcon( folder.getFolderID( ), style );
	}

	/**
	 * Retrieves the icon of specified shell folder.
	 * 
	 * @param folderId
	 *            shell folder id.
	 * @param style
	 *            icon style.
	 * @return the icon of specified shell folder.
	 */
	public static Image getSysFolderIcon( int folderId, int style )
	{
		int type = 0;
		if ( ( style & ICON_SMALL ) > 0 )
			type |= Win32.ICON_SMALL;
		if ( ( style & ICON_LARGE ) > 0 )
			type |= Win32.ICON_LARGE;
		pidl = Extension.SHGetSpecialFolderLocation( folderId );
		SHFILEINFO shInfo;
		if ( Extension.IsUnicode )
			shInfo = new SHFILEINFOW( );
		else
			shInfo = new SHFILEINFOA( );
		Extension.SHGetFileInfo( pidl,
				0,
				shInfo,
				SHFILEINFO.sizeof,
				Win32.SHGFI_DISPLAYNAME
						| Win32.SHGFI_SYSICONINDEX
						| Win32.SHGFI_ICON
						| Win32.SHGFI_PIDL
						| type );
		return Image.win32_new( null, SWT.ICON, shInfo.hIcon );
	}

}
