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

import org.sf.feeling.swt.win32.extension.Win32;
import org.sf.feeling.swt.win32.extension.io.Keyboard;
import org.sf.feeling.swt.win32.extension.jna.exception.NativeException;
import org.sf.feeling.swt.win32.extension.jna.ptr.Pointer;
import org.sf.feeling.swt.win32.extension.jna.win32.Shell32;
import org.sf.feeling.swt.win32.internal.extension.Extension;
import org.sf.feeling.swt.win32.internal.extension.SHFILEOPSTRUCT;
import org.sf.feeling.swt.win32.internal.extension.SHFILEOPSTRUCTA;

/**
 * Shell System Utility class.
 * 
 * @author <a href="mailto:cnfree2000@hotmail.com">cnfree</a>
 * 
 */
public class ShellSystem
{

	public final static int WALLPAPER_CENTER = Win32.WPSTYLE_CENTER;

	public final static int WALLPAPER_TILE = Win32.WPSTYLE_TILE;

	public final static int WALLPAPER_STRETCH = Win32.WPSTYLE_STRETCH;

	public final static int WALLPAPER_MAX = Win32.WPSTYLE_MAX;

	/**
	 * Set specified file as wallpaper.<br>
	 * <note>Warning:</note> I don't know why need set twice on the Win7 then
	 * make it effective.
	 * 
	 * @param picture
	 *            is a file to set as wallpaper.
	 * @param style
	 *            display style, one of {@link #WALLPAPER_CENTER},
	 *            {@link #WALLPAPER_TILE}, {@link #WALLPAPER_STRETCH},
	 *            {@link #WALLPAPER_MAX}.
	 * @return If set wallpaper successfully, return true, else return false.
	 */
	public static boolean setWallPaper( File picture, int style )
	{
		return Extension.SetWallPaper( picture.getAbsolutePath( ).toCharArray( ),
				style );
	}

	/**
	 * Set specified file as wallpaper. <br>
	 * <note>Warning:</note> I don't know why need set twice on the Win7 then
	 * make it effective.
	 * 
	 * @param picturePath
	 *            is a file path to set as wallpaper.
	 * @param style
	 *            display style, one of {@link #WALLPAPER_CENTER},
	 *            {@link #WALLPAPER_TILE}, {@link #WALLPAPER_STRETCH},
	 *            {@link #WALLPAPER_MAX}.
	 * @return If set wallpaper successfully, return true, else return false.
	 */
	public static boolean setWallPaper( String picturePath, int style )
	{
		if ( picturePath == null )
			return false;
		return Extension.SetWallPaper( picturePath.toCharArray( ), style );
	}

	/**
	 * Move specified file to recycle
	 * 
	 * @param hWnd
	 *            system handle.
	 * @param file
	 *            specified file will be moved to recycle
	 * @param confirm
	 *            whether pop up confirm dialog.
	 * @return return true if the operation successful.
	 */
	public static boolean removeToRecycle( int hWnd, File file, boolean confirm )
	{
		int result;
		SHFILEOPSTRUCT lpFileOp = new SHFILEOPSTRUCTA( );
		lpFileOp.wFunc = Win32.FO_DELETE;
		String fileName = file.getAbsolutePath( );

		Pointer pointer;
		try
		{
			pointer = Pointer.createPointerFromString( fileName + "\0",
					Extension.IsUnicode );
		}
		catch ( NativeException e )
		{
			e.printStackTrace( );
			return false;
		}

		lpFileOp.pFrom = pointer.getPointer( );
		lpFileOp.pTo = pointer.getPointer( );

		lpFileOp.fFlags = Win32.FOF_ALLOWUNDO;
		if ( !confirm )
			lpFileOp.fFlags = lpFileOp.fFlags
					| Win32.FOF_NOCONFIRMATION
					| Win32.FOF_SILENT;
		if ( Extension.IsUnicode )
		{
			result = Extension.SHFileOperationW( lpFileOp );
		}
		else
			result = Extension.SHFileOperationA( lpFileOp );

		return result == 0;
	}

	/**
	 * Show desktop
	 */
	public static void showDesktop( )
	{
		Keyboard.keyDown( Win32.VK_LWIN, 0, false );
		Keyboard.keyDown( 'M', 0, false );
		Keyboard.keyUp( 'M', 0, false );
		Keyboard.keyUp( Win32.VK_LWIN, 0, false );
	}

	/**
	 * Notifies the system of an event that an application has performed. An
	 * application should use this function if it performs an action that may
	 * affect the Shell.
	 * 
	 * @return true if the operation successful.
	 */
	public static boolean shellChangeNotify( )
	{
		try
		{
			return Shell32.SHChangeNotify( Shell32.HChangeNotifyEventID.SHCNE_ASSOCCHANGED,
					Shell32.HChangeNotifyFlags.SHCNF_IDLIST,
					0,
					0 );
		}
		catch ( Exception e )
		{
			e.printStackTrace( );
		}
		return false;
	}
}
