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

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.graphics.Region;
import org.eclipse.swt.internal.Callback;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.POINT;
import org.eclipse.swt.internal.win32.RECT;
import org.eclipse.swt.internal.win32.TCHAR;
import org.sf.feeling.swt.win32.extension.Win32;
import org.sf.feeling.swt.win32.extension.jna.Function;
import org.sf.feeling.swt.win32.extension.jna.StructConverter;
import org.sf.feeling.swt.win32.extension.jna.datatype.win32.DWORD;
import org.sf.feeling.swt.win32.extension.jna.datatype.win32.HANDLE;
import org.sf.feeling.swt.win32.extension.jna.exception.NativeException;
import org.sf.feeling.swt.win32.extension.jna.ptr.NullPointer;
import org.sf.feeling.swt.win32.extension.jna.ptr.Pointer;
import org.sf.feeling.swt.win32.extension.jna.ptr.memory.MemoryBlockFactory;
import org.sf.feeling.swt.win32.extension.jna.win32.Gdi32;
import org.sf.feeling.swt.win32.extension.jna.win32.Kernel32;
import org.sf.feeling.swt.win32.extension.jna.win32.User32;
import org.sf.feeling.swt.win32.extension.jna.win32.structure.BITMAPINFOHEADER;
import org.sf.feeling.swt.win32.extension.jna.win32.structure.DC;
import org.sf.feeling.swt.win32.extension.jna.win32.structure.HWND;
import org.sf.feeling.swt.win32.extension.jna.win32.structure.LRECT;
import org.sf.feeling.swt.win32.internal.extension.Extension;
import org.sf.feeling.swt.win32.internal.extension.Extension2;
import org.sf.feeling.swt.win32.internal.extension.FLASHWINFO;
import org.sf.feeling.swt.win32.internal.extension.util.LONG;

/**
 * Windows Utility Class.
 * 
 * @author <a href="mailto:cnfree2000@hotmail.com">cnfree</a>
 * 
 */
public class Windows
{

	public static class ShowWindowCommand
	{

		public static final int SW_ERASE = 0x4;

		public static final int SW_HIDE = 0x0;

		public static final int SW_INVALIDATE = 0x2;

		public static final int SW_MINIMIZE = 0x6;

		public static final int SW_PARENTOPENING = 0x3;

		public static final int SW_RESTORE = Extension.IsWinCE ? 0xd : 0x9;

		public static final int SW_SCROLLCHILDREN = 0x1;

		public static final int SW_SHOW = 0x5;

		public static final int SW_SHOWMAXIMIZED = Extension.IsWinCE ? 0xb
				: 0x3;

		public static final int SW_SHOWMINIMIZED = 0x2;

		public static final int SW_SHOWMINNOACTIVE = 0x7;

		public static final int SW_SHOWNA = 0x8;

		public static final int SW_SHOWNOACTIVATE = 0x4;
	}

	private static List childList = new ArrayList( );

	private static final String FUNTION_ENUMCHILDWINDOWS = "EnumChildWindows";

	private static final String FUNTION_ENUMWINDOWS = "EnumWindows";

	private static final String FUNTION_FINDWINDOWEX = Extension.IsUnicode ? "FindWindowExW"
			: "FindWindowExA";

	private static final String FUNTION_ISWINDOW = "IsWindow";

	private static final String USER32_LIB = "user32";

	private static List windowsList = new ArrayList( );

	/**
	 * Brings the window to the top of the Z order. <b>NOTE:</b>this function
	 * does not make a window a top-level window.
	 */
	public static void bringToTop( int handle )
	{
		Extension.BringWindowToTop( handle );
	}

	/**
	 * Converts the client-area coordinates of a specified point to screen
	 * coordinates. The method does not modify the passed point instance.
	 * 
	 * @param point
	 *            contains the client coordinates to be converted.
	 * @return converted point of the sreen coordinates.
	 */
	public static Point clientToScreen( int handle, Point point )
	{
		POINT pt = new POINT( );
		pt.x = point.x;
		pt.y = point.y;
		OS.ClientToScreen( handle, pt );
		return new Point( pt.x, pt.y );
	}

	/**
	 * Destroys a window.
	 */
	public static boolean destroyWindow( int handle )
	{
		return Extension.DestroyWindow( handle );
	}

	static int enumChildWindowsProc( int hwnd, int lParam )
	{
		childList.add( new LONG( hwnd ) );
		return 1;
	}

	public static int[] enumChildWindows( int handle )
	{
		childList.clear( );
		Callback callback = new Callback( Windows.class,
				"enumChildWindowsProc",
				2 );
		int address = callback.getAddress( );
		if ( address != 0 )
		{
			try
			{
				Function function = new Function( USER32_LIB,
						FUNTION_ENUMCHILDWINDOWS );
				function.invoke_I( handle, address, 0 );
				function.close( );
			}
			catch ( Exception e )
			{
				SWT.error( SWT.ERROR_INVALID_ARGUMENT );
			}
			callback.dispose( );
		}
		int[] handles = new int[windowsList.size( )];
		for ( int i = 0; i < windowsList.size( ); i++ )
			handles[i] = ( (LONG) windowsList.get( i ) ).value;
		return handles;
	}

	static int enumWindowsProc( int hwnd, int lParam )
	{
		windowsList.add( new LONG( hwnd ) );
		return 1;
	}

	public static int[] enumWindows( )
	{
		windowsList.clear( );
		Callback callback = new Callback( Windows.class, "enumWindowsProc", 2 );
		int address = callback.getAddress( );
		if ( address != 0 )
		{
			try
			{
				Function function = new Function( USER32_LIB,
						FUNTION_ENUMWINDOWS );
				function.invoke_I( address, 0 );
				function.close( );
			}
			catch ( Exception e )
			{
				SWT.error( SWT.ERROR_INVALID_ARGUMENT );
			}
			callback.dispose( );
		}
		int[] handles = new int[windowsList.size( )];
		for ( int i = 0; i < windowsList.size( ); i++ )
			handles[i] = ( (LONG) windowsList.get( i ) ).value;
		return handles;
	}

	/**
	 * Get specified window handle.
	 * 
	 * @param className
	 *            specified window's className.
	 * @return specified window handle.
	 */
	public static int findWindow( String className )
	{
		return Extension.FindWindow( new TCHAR( 0, className, true ), null );
	}

	/**
	 * Searches the window by a specified class name and its title.
	 * 
	 * @param className
	 * @param windowName
	 * @return handle of the found window if succeeds.
	 */
	public static int findWindow( String className, String windowName )
	{
		TCHAR CLASSNAME = null;
		TCHAR WINDOWNAME = null;
		if ( className != null )
			CLASSNAME = new TCHAR( 0, className, true );
		if ( windowName != null )
			WINDOWNAME = new TCHAR( 0, windowName, true );
		return Extension.FindWindow( CLASSNAME, WINDOWNAME );
	}

	/**
	 * Searches a window by a specified name.
	 * 
	 * @param windowName
	 * @return handle of the found window if succeeds.
	 */
	public static int findWindowByName( String windowName )
	{
		return findWindow( null, windowName );
	}

	/**
	 * Searches a child window by specified class name and its title.
	 * 
	 * @param parent
	 *            handle of the parent window.
	 * @param className
	 * @param windowName
	 * @return handle of the found window if succeeds.
	 */
	public static int findWindowEx( int parent, String className,
			String windowName )
	{
		return findWindowEx( parent, 0, className, windowName );
	}

	public static int findWindowEx( int parent, int hwndChildAfter,
			String className, String windowName )
	{
		int result = 0;
		int lpClassName = 0;
		int lpWindowName = 0;
		int hHeap = Extension.GetProcessHeap( );

		if ( className != null )
		{
			TCHAR buffer = new TCHAR( 0, className, true );
			int byteCount = buffer.length( ) * TCHAR.sizeof;
			lpClassName = Extension.HeapAlloc( hHeap,
					Extension.HEAP_ZERO_MEMORY,
					byteCount );
			Extension.MoveMemory( lpClassName, buffer, byteCount );
		}
		if ( windowName != null )
		{
			TCHAR buffer = new TCHAR( 0, windowName, true );
			int byteCount = buffer.length( ) * TCHAR.sizeof;
			lpWindowName = Extension.HeapAlloc( hHeap,
					Extension.HEAP_ZERO_MEMORY,
					byteCount );
			Extension.MoveMemory( lpWindowName, buffer, byteCount );
		}

		try
		{
			Function function = new Function( USER32_LIB, FUNTION_FINDWINDOWEX );
			result = function.invoke_I( parent,
					hwndChildAfter,
					lpClassName,
					lpWindowName );
			function.close( );
		}
		catch ( Exception e )
		{
			SWT.error( SWT.ERROR_INVALID_ARGUMENT );
		}
		if ( lpClassName != 0 )
			OS.HeapFree( hHeap, 0, lpClassName );
		if ( lpWindowName != 0 )
			OS.HeapFree( hHeap, 0, lpWindowName );
		return result;
	}

	/**
	 * Flashes the specified window. It does not change the active state of the
	 * window.
	 * 
	 * @param hwnd
	 *            specified window handle.
	 * @param flash
	 *            whether flash the window.
	 * @return return true if do the operation successfully, else return false.
	 * 
	 */
	public static boolean flashWindow( int hwnd, boolean flash )
	{
		FLASHWINFO flashInfo = new FLASHWINFO( );
		if ( flash )
			flashInfo.dwFlags = FLASHWINFO.FLASHW_ALL | FLASHWINFO.FLASHW_TIMER;
		flashInfo.hwnd = hwnd;
		return Extension.FlashWindowEx( flashInfo );
	}

	public static boolean flashWindow( int hwnd, int timeout, int count )
	{
		FLASHWINFO flashInfo = new FLASHWINFO( );
		flashInfo.dwFlags = FLASHWINFO.FLASHW_ALL | FLASHWINFO.FLASHW_TIMER;
		flashInfo.dwTimeout = timeout;
		flashInfo.uCount = count;
		flashInfo.hwnd = hwnd;
		return Extension.FlashWindowEx( flashInfo );
	}

	public static String getClassName( int handle )
	{
		TCHAR buffer = new TCHAR( 0, 128 );
		Extension.GetClassName( handle, buffer, buffer.length( ) );
		return buffer.toString( 0, buffer.strlen( ) );
	}

	/**
	 * Returns a desktop window handle.
	 * 
	 * @return desktop window handle.
	 */
	public static int getDesktopWindow( )
	{
		return Extension.GetDesktopWindow( );
	}

	/**
	 * Retrieves window that has keyboard focus if the window is attached to the
	 * message queue of calling thread.
	 * 
	 * @return window that has keyboard focus.
	 */
	public static int getFocus( )
	{
		return Extension.GetFocus( );
	}

	/**
	 * Returns a handle to the foreground window (the window with which the user
	 * is currently working).
	 * 
	 * @return a handle to the foreground window
	 */
	public static int getForegroundWindow( )
	{
		return Extension.GetForegroundWindow( );
	}

	/*
	 * AW_SLIDE Uses slide animation. By default, roll animation is used. This
	 * flag is ignored when used with the AW_CENTER flag. AW_ACTIVATE Activates
	 * the window. Do not use this flag with AW_HIDE. AW_BLEND Uses a fade
	 * effect. This flag can be used only if hwnd is a top-level window. AW_HIDE
	 * Hides the window. By default, the window is shown. AW_CENTER Makes the
	 * window appear to collapse inward if the AW_HIDE flag is used or expand
	 * outward. If the AW_HIDE flag is not used. AW_HOR_POSITIVE Animate the
	 * window from left to right. This flag can be used with roll or slide
	 * animation. It is ignored when used with the AW_CENTER flag.
	 * AW_HOR_NEGATIVE Animate the window from right to left. This flag can be
	 * used with roll or slide animation. It is ignored when used with the
	 * AW_CENTER flag. AW_VER_POSITIVE Animate the window from top to bottom.
	 * This flag can be used with roll or slide animation.It is ignored when
	 * used with the AW_CENTER flag. AW_VER_NEGATIVE Animate the window from
	 * bottom to top. This flag can be used with roll or slide animation.It is
	 * ignored when used with the AW_CENTER flag.
	 */

	/**
	 * Returns the window parent.
	 * 
	 * @return window parent.
	 */
	public static int getParent( int handle )
	{
		return Extension.GetParent( handle );
	}

	/**
	 * Returns the process identifier.
	 * 
	 * @return the process identifier
	 */
	public static int getProcessId( int handle )
	{
		int[] pid = new int[1];
		Extension.GetWindowThreadProcessId( handle, pid );
		return pid[0];
	}

	/**
	 * Get system tray handle.
	 * 
	 * @return system tray handle.
	 */
	public static int getSystemTray( )
	{
		return findWindow( Win32.SHELL_TRAY );
	}

	/**
	 * Returns the identifier of the thread that created this window.
	 * 
	 * @return the identifier of the thread that created this window
	 */
	public static int getThreadId( int handle )
	{
		return Extension.GetWindowThreadProcessId( handle, null );
	}

	/**
	 * Returns the name of the window class.
	 * 
	 * @return window class name.
	 */
	public static String getWindowClassName( int handle )
	{
		TCHAR buffer = new TCHAR( 0, 128 );
		Extension.GetClassName( handle, buffer, buffer.length( ) );
		return buffer.toString( 0, buffer.strlen( ) );
	}

	/**
	 * Get specified window extension style.
	 * 
	 * @param hwnd
	 *            specified window handle.
	 * @return specified window extension style.
	 */
	public static int getWindowExStyle( int hwnd )
	{
		return Extension.GetWindowLong( hwnd, Win32.GWL_EXSTYLE );
	}

	/**
	 * Returns a handle to the window that contains the specified point.
	 * 
	 * @param x
	 *            specifies the x-coordinate of the point
	 * @param y
	 *            specifies the y-coordinate of the point
	 * @return a handle to the window that contains the specified point
	 */
	public static int getWindowFromPoint( int x, int y )
	{
		POINT point = new POINT( );
		point.x = x;
		point.y = y;
		return Extension.WindowFromPoint( point );
	}

	/**
	 * Get the rectangle information of the specified window.
	 * 
	 * @param hwnd
	 *            specified window handle.
	 * @return the rectangle information of the specified window.
	 * 
	 */
	public static Rectangle getWindowRect( int hwnd )
	{
		RECT rect = new RECT( );
		Extension.GetWindowRect( hwnd, rect );
		return new Rectangle( rect.left,
				rect.top,
				rect.right - rect.left,
				rect.bottom - rect.top );
	}

	/**
	 * Get the width of window border.
	 * 
	 * @return the width of window border.
	 */
	public static int getWindowBorderWidth( )
	{
		return Extension.GetSystemMetrics( Win32.SM_CXSIZEFRAME );
	}

	/**
	 * Get the width of window title bar.
	 * 
	 * @return the width of window title bar.
	 */
	public static int getTitleBarHeight( )
	{
		return Extension.GetSystemMetrics( Win32.SM_CYCAPTION );
	}

	/**
	 * Get the width of window title bar button.
	 * 
	 * @return the width of window title bar button.
	 */
	public static int getTitleBarButtonWidth( )
	{
		return Extension.GetSystemMetrics( Win32.SM_CXSIZE );
	}
	
	/**
	 * Get the height of window title bar button.
	 * 
	 * @return the height of window title bar button.
	 */
	public static int getTitleBarButtonHeight( )
	{
		return Extension.GetSystemMetrics( Win32.SM_CYSIZE );
	}

	/**
	 * Get specified window style.
	 * 
	 * @param hwnd
	 *            specified window handle.
	 * @return specified window style.
	 */
	public static int getWindowStyle( int hwnd )
	{
		return Extension.GetWindowLong( hwnd, Win32.GWL_STYLE );
	}

	/**
	 * Returns text of a specified window's title bar (if it has one). If the
	 * specified window is a control, the text of the control is returned.
	 * 
	 * @return text of the specified window's title bar.
	 */
	public static String getWindowText( int handle )
	{
		int length = Extension.GetWindowTextLength( handle );
		if ( length == 0 )
			return "";
		/* Use the character encoding for the default locale */
		TCHAR buffer = new TCHAR( 0, length + 1 );
		Extension.GetWindowText( handle, buffer, length + 1 );
		return buffer.toString( 0, length );
	}

	/**
	 * @return a window transparency value.
	 */
	public static int getWindowTransparency( int handle )
	{
		if ( Win32.getWin32Version( ) >= Win32.VERSION( 5, 0 ) )
		{
			byte[] pbAlpha = new byte[1];
			if ( Extension2.GetLayeredWindowAttributes( handle,
					null,
					pbAlpha,
					null ) )
			{
				return pbAlpha[0] & 0xFF;
			}
		}
		return 0xFF;
	}

	/**
	 * Hide specified shell's title bar.
	 * 
	 * @param hwnd
	 *            specified shell handle.
	 */
	public static void hideTitleBar( int hwnd )
	{
		int lStyle = Extension.GetWindowLong( hwnd, Win32.GWL_STYLE );
		Extension.SetWindowLong( hwnd, Win32.GWL_STYLE, lStyle
				& ~Win32.WS_CAPTION );
		Extension.SetWindowPos( hwnd, 0, 0, 0, 0, 0, Win32.SWP_NOSIZE
				| Win32.SWP_NOMOVE
				| Win32.SWP_NOZORDER
				| Win32.SWP_NOACTIVATE
				| Win32.SWP_FRAMECHANGED );
	}

	/**
	 * Use fade effect to hide the specified window.
	 * 
	 * @param hwnd
	 *            specified window handle.
	 * @param time
	 *            the time of showing effect.
	 * @return true, if the fade effect is successful.
	 */
	public static boolean hideWindowBlend( int hwnd, int time )
	{
		return Extension2.AnimateWindow( hwnd, time, Win32.AW_HIDE
				| Win32.AW_CENTER
				| Win32.AW_BLEND );
	}

	/**
	 * Checks if the window border is visible.
	 * 
	 * @return true, if the window border is visible.
	 */
	public static boolean isBorderThick( int hwnd )
	{
		final long windowStyle = getWindowStyle( hwnd );
		return ( windowStyle & Win32.WS_THICKFRAME ) != 0;
	}

	public static boolean isIconic( int handle )
	{
		return Extension.IsIconic( handle );
	}

	/**
	 * Checks if the window is maximized.
	 * 
	 * @return true, if the window is maximized
	 */
	public static boolean isMaximized( int handle )
	{
		return Extension.IsZoomed( handle );
	}

	/**
	 * Get specified window palette style status.
	 * 
	 * @param hwnd
	 *            specified window handle.
	 * @return return true if specifed window is palette style, other case
	 *         return false;
	 */
	public static boolean isPalleteWindow( int hwnd )
	{
		final int windowStyle = getWindowExStyle( hwnd );
		return ( windowStyle & Win32.WS_EX_TOOLWINDOW ) != 0;
	}

	/**
	 * Get the title bar visible status.
	 * 
	 * @param hwnd
	 *            specified shell handle.
	 * @return the title bar visible status.
	 */
	public static boolean isTitleBarVisible( int hwnd )
	{
		final long windowStyle = getWindowStyle( hwnd );
		return ( windowStyle & Win32.WS_CAPTION ) != 0;
	}

	/**
	 * Returns true if the window is visible.
	 * 
	 * @return true if the window is visible.
	 */
	public static boolean isVisible( int handle )
	{
		int windowStyle = Extension.GetWindowLong( handle, Win32.GWL_STYLE );
		return ( windowStyle & Win32.WS_VISIBLE ) != 0;
	}

	/**
	 * Checks if this handle is real pointer to an existing window.
	 * 
	 * @return true is this handle is real window; false otherwise
	 */
	public static boolean isWindow( int handle )
	{
		try
		{
			Function function = new Function( USER32_LIB, FUNTION_ISWINDOW );
			int result = function.invoke_I( handle );
			function.close( );
			return result != 0;
		}
		catch ( Exception e )
		{
			SWT.error( SWT.ERROR_INVALID_ARGUMENT );
			return false;
		}
	}

	public static boolean isWindowAsDialogModel( int hwnd )
	{
		int windowStyle = getWindowExStyle( hwnd );
		return ( windowStyle & Win32.WS_EX_DLGMODALFRAME ) != 0;
	}

	/**
	 * @return true, if the window is transparent; otherwise false.
	 */
	public static boolean isWindowTransparent( int handle )
	{
		if ( Win32.getWin32Version( ) >= Win32.VERSION( 5, 0 ) )
		{
			final long windowStyle = getWindowExStyle( handle );
			return ( windowStyle & Win32.WS_EX_LAYERED ) != 0;
		}
		return false;
	}

	/**
	 * Converts the screen coordinates of a specified point on the screen to
	 * client-area coordinates. The method does not modify the passed point
	 * instance.
	 * 
	 * @param point
	 *            screen coordinates.
	 * @return converted point of the client-area coordinates.
	 */
	public static Point screenToClient( int handle, Point point )
	{
		POINT pt = new POINT( );
		pt.x = point.x;
		pt.y = point.y;
		OS.ScreenToClient( handle, pt );
		return new Point( pt.x, pt.y );
	}

	/**
	 * Set specified window's border as thick border style.
	 * 
	 * @param hwnd
	 *            specified window handle.
	 * @param thick
	 *            whether set window's border as thick border style.
	 */
	public static void setBorderThick( int hwnd, boolean thick )
	{
		int oldWindowStyle = getWindowStyle( hwnd );
		int newWindowStyle;
		if ( thick )
		{
			newWindowStyle = oldWindowStyle | Win32.WS_THICKFRAME;
		}
		else
		{
			newWindowStyle = oldWindowStyle & ~Win32.WS_THICKFRAME;
		}
		setWindowStyle( hwnd, newWindowStyle );
	}

	/**
	 * Sets the keyboard focus to the specified window. The window must be
	 * attached to the calling thread's message queue.
	 * 
	 * @return If the function succeeds, the return value is the handle to the
	 *         window that previously had the keyboard focus.
	 */
	public static int setFocus( int handle )
	{
		return Extension.SetFocus( handle );
	}

	/**
	 * Set specified window is palette style.
	 * 
	 * @param hwnd
	 *            specified window handle.
	 * @param isPalette
	 *            whether palette style.
	 */
	public static void setPaletteWindow( int hwnd, boolean isPalette )
	{
		int oldWindowStyle = getWindowExStyle( hwnd );
		int newWindowStyle;
		if ( isPalette )
			newWindowStyle = oldWindowStyle | Win32.WS_EX_PALETTEWINDOW;
		else
			newWindowStyle = oldWindowStyle & ~Win32.WS_EX_PALETTEWINDOW;
		setWindowExStyle( hwnd, newWindowStyle );
		Extension.RedrawWindow( hwnd, null, 0, Win32.RDW_FRAME
				| Win32.RDW_INVALIDATE );
	}

	/**
	 * Changes the parent of a window.
	 * 
	 * @param parent
	 *            a new parent window.
	 */
	public static void setParent( int handle, int parent )
	{
		Extension.SetParent( handle, parent );
	}

	/**
	 * Sets a custom window region. Returns a previously assigned region in
	 * order to restore it later.
	 * 
	 * @param region
	 * @return region previously assigned to the window.
	 */
	public static Region setRegion( int handle, Region region )
	{
		Region prevRegion = Region.win32_new( null,
				Extension.GetWindowRgn( handle, 0 ) );
		Extension.SetWindowRgn( handle, region.handle, true );
		return prevRegion;
	}

	/**
	 * Shows or hides the window.
	 * 
	 * @param visible
	 *            if <code>true</code>, shows this window; otherwise, hides this
	 *            window.
	 */
	public static void setVisible( int handle, boolean visible )
	{
		int windowStyle = Extension.GetWindowLong( handle, Win32.GWL_STYLE );

		if ( !visible )
			windowStyle &= ~Win32.WS_VISIBLE;
		else
			windowStyle |= Win32.WS_VISIBLE;

		Extension.SetWindowLong( handle, Win32.GWL_STYLE, windowStyle );
	}

	/**
	 * Set specified shell always on the top
	 * 
	 * @param hwnd
	 *            specified shell handle.
	 * @param onTop
	 *            whether on top
	 */
	public static void setWindowAlwaysOnTop( int hwnd, boolean onTop )
	{
		if ( onTop )
			Extension.SetWindowPos( hwnd,
					Win32.HWND_TOPMOST,
					0,
					0,
					0,
					0,
					Win32.SWP_NOMOVE | Win32.SWP_NOSIZE );
		else
			Extension.SetWindowPos( hwnd,
					Win32.HWND_NOTOPMOST,
					0,
					0,
					0,
					0,
					Win32.SWP_NOMOVE | Win32.SWP_NOSIZE );

		return;
	}

	public static void setWindowAsDialogModel( int hwnd, boolean isDialogModel )
	{
		int oldWindowStyle = getWindowExStyle( hwnd );
		int newWindowStyle;
		if ( isDialogModel )
			newWindowStyle = oldWindowStyle | Win32.WS_EX_DLGMODALFRAME;
		else
			newWindowStyle = oldWindowStyle & ~Win32.WS_EX_DLGMODALFRAME;
		setWindowExStyle( hwnd, newWindowStyle );
		Extension.RedrawWindow( hwnd, null, 0, Win32.RDW_FRAME
				| Win32.RDW_INVALIDATE );
	}

	/**
	 * Set extended style of the specified window.
	 * 
	 * @param hwnd
	 *            specified window handle.
	 * @param style
	 *            specified window extended style.
	 */
	public static void setWindowExStyle( int hwnd, int style )
	{
		Extension.SetWindowLong( hwnd, Win32.GWL_EXSTYLE, style );
		Extension.SetWindowPos( hwnd, 0, 0, 0, 0, 0, Win32.SWP_NOSIZE
				| Win32.SWP_NOMOVE
				| Win32.SWP_NOZORDER
				| Win32.SWP_DRAWFRAME );
	}

	public static int setWindowIcon( int handle, Image image, boolean isSmall )
	{
		return Extension.SendMessage( handle,
				Win32.WM_SETICON,
				image.handle,
				isSmall ? 0 : 1 );
	}

	/**
	 * Set specified shell maximized.
	 * 
	 * @param hwnd
	 *            specified shell handle.
	 */
	public static void setWindowMaximized( int hwnd )
	{
		Extension.SendMessage( hwnd, Win32.WM_SYSCOMMAND, Win32.SC_MAXIMIZE, 0 );
	}

	/**
	 * Set specified shell minimized.
	 * 
	 * @param hwnd
	 *            specified shell handle.
	 */
	public static void setWindowMinimized( int hwnd )
	{
		Extension.SendMessage( hwnd, Win32.WM_SYSCOMMAND, Win32.SC_MINIMIZE, 0 );
	}

	/**
	 * Let specified shell do restored operation.
	 * 
	 * @param hwnd
	 *            specified shell handle.
	 */
	public static void setWindowRestored( int hwnd )
	{
		Extension.SendMessage( hwnd, Win32.WM_SYSCOMMAND, Win32.SC_RESTORE, 0 );
	}

	/**
	 * Set style of the specified window.
	 * 
	 * @param hwnd
	 *            specified window handle.
	 * @param style
	 *            specified window style.
	 */
	public static void setWindowStyle( int hwnd, int style )
	{
		Extension.SetWindowLong( hwnd, Win32.GWL_STYLE, style );
		Extension.SetWindowPos( hwnd, 0, 0, 0, 0, 0, Win32.SWP_NOSIZE
				| Win32.SWP_NOMOVE
				| Win32.SWP_NOZORDER
				| Win32.SWP_DRAWFRAME );
	}

	public static boolean setWindowText( int handle, String text )
	{
		if ( text == null )
			SWT.error( SWT.ERROR_NULL_ARGUMENT );
		/* Use the character encoding for the default locale */
		TCHAR buffer = new TCHAR( 0, text, true );
		return Extension.SetWindowText( handle, buffer );
	}

	/**
	 * Sets or removes window transparancy. <br>
	 * 
	 * @param transparent
	 *            specifies whether or not to make window transparent
	 */
	public static void setWindowTransparent( int handle, boolean transparent )
	{
		if ( Win32.getWin32Version( ) >= Win32.VERSION( 5, 0 ) )
		{
			int oldWindowStyle = getWindowExStyle( handle );
			int newWindowStyle;
			if ( transparent )
			{
				newWindowStyle = oldWindowStyle | Win32.WS_EX_LAYERED;
				setWindowExStyle( handle, newWindowStyle );
				setWindowTransparent( handle, 0, (byte) 0, Win32.LWA_ALPHA );
			}
			else
			{
				newWindowStyle = oldWindowStyle & ~Win32.WS_EX_LAYERED;
				setWindowExStyle( handle, newWindowStyle );
			}
			Extension.RedrawWindow( handle, null, 0, Win32.RDW_UPDATENOW
					| Win32.RDW_NOERASE );
		}
		else
			throw new UnsupportedOperationException( "The windows version is too low to support this operation." );
	}

	/**
	 * Set specified window transparent.</br><b>Important:</b>Windows Version
	 * need Windows 2000 or later.
	 * 
	 * @param hwnd
	 *            specified window handle.
	 * @param alpha
	 *            degree of window transparent(0&lt;=alpha&lt;255)
	 * @return return true if do this operation successfully.
	 */
	public static boolean setWindowTransparent( int hwnd, byte alpha )
	{
		return setWindowTransparent( hwnd, 0, alpha, Win32.LWA_ALPHA );
	}

	/**
	 * Set specified window transparent.</br><b>Important:</b>Windows Version
	 * need Windows 2000 or later.
	 * 
	 * @param hwnd
	 *            specified window handle.
	 * @param color
	 *            specified color which is used as transparent color(need type =
	 *            LWA_COLORKEY).
	 * @param alpha
	 *            degree of window transparent(need type = LWA_ALPHA, and
	 *            0&lt;=alpha&lt;255)
	 * @param type
	 *            transparent type(LWA_ALPHA or LWA_COLORKEY)
	 * @return return true if do this operation successfully.
	 */
	public static boolean setWindowTransparent( int hwnd, int color,
			byte alpha, int type )
	{
		if ( Win32.getWin32Version( ) >= Win32.VERSION( 5, 0 ) )
		{
			int lWindowLong = Extension.GetWindowLong( hwnd, Win32.GWL_EXSTYLE )
					| Win32.WS_EX_LAYERED;
			Extension.SetWindowLong( hwnd, Win32.GWL_EXSTYLE, lWindowLong );
			boolean rc = Extension2.SetLayeredWindowAttributes( hwnd,
					color,
					alpha,
					type );
			Extension.RedrawWindow( hwnd, null, 0, Win32.RDW_ERASE
					| Win32.RDW_INVALIDATE
					| Win32.RDW_FRAME
					| Win32.RDW_ALLCHILDREN );
			return rc;
		}
		else
			throw new UnsupportedOperationException( "The windows version is too low to support this operation." );
	}

	/**
	 * Sets a specified window's show state.
	 * 
	 * @param command
	 *            Specifies how the window is to be shown.
	 */
	public static void show( int handle, int command )
	{
		Extension.ShowWindow( handle, command );
	}

	/**
	 * Show windows cursor
	 * 
	 * @param bShow
	 *            whether show the windows cursor
	 * @return return true if show cursor successfully, else return false.
	 */
	public static final boolean showCursor( boolean bShow )
	{
		return Extension2.ShowCursor( bShow );
	}

	/**
	 * Show specified shell's title bar.
	 * 
	 * @param hwnd
	 *            specified shell handle.
	 */
	public static void showTitleBar( int hwnd )
	{
		int lStyle = Extension.GetWindowLong( hwnd, Win32.GWL_STYLE );
		Extension.SetWindowLong( hwnd, Win32.GWL_STYLE, lStyle
				| Win32.WS_CAPTION );
		Extension.SetWindowPos( hwnd, 0, 0, 0, 0, 0, Win32.SWP_NOSIZE
				| Win32.SWP_NOMOVE
				| Win32.SWP_NOZORDER
				| Win32.SWP_NOACTIVATE
				| Win32.SWP_FRAMECHANGED );
	}

	/**
	 * Use fade effect to show the specified window.
	 * 
	 * @param hwnd
	 *            specified window handle.
	 * @param time
	 *            the time of showing effect.
	 * @return return true if do operation successfully, else return false.
	 */
	public static boolean showWindowBlend( int hwnd, int time )
	{
		return Extension2.AnimateWindow( hwnd, time, Win32.AW_CENTER
				| Win32.AW_BLEND );
	}

	public static int getActiveWindow( )
	{
		return Extension.GetActiveWindow( );
	}

	public static void forceCloseWindow( int handle )
	{
		Extension.SendMessage( handle, Win32.WM_CLOSE, 0, 0 );
	}

	public static int[] enumControlChildren( int handle )
	{
		List handles = new ArrayList( );
		if ( handle != 0 )
		{
			int child = Extension.GetWindow( handle, Win32.GW_CHILD );
			while ( child != 0 )
			{
				handles.add( new Integer( child ) );
				child = Extension.GetWindow( child, Win32.GW_HWNDNEXT );
			}
		}
		int[] handleArray = new int[handles.size( )];
		for ( int i = 0; i < handles.size( ); i++ )
		{
			handleArray[i] = ( (Integer) handles.get( i ) ).intValue( );
		}
		return handleArray;
	}

	public static boolean takeScreenShotAndWriteToDisk( int hwnd, String path,
			boolean useNative ) throws InterruptedException,
			IllegalAccessException, NativeException, FileNotFoundException,
			IOException
	{
		if ( hwnd == 0 )
		{
			System.err.println( "HWND is null!" );
			return false;
		}

		HWND hWnd = new HWND( hwnd );

		// set the target window to foreground
		HWND foregroundHWND = User32.GetForegroundWindow( );
		setForegroundWindowEx( hwnd );

		Pointer bfheader = null;
		Pointer bitmap = null;
		DC hdcScreen = new DC( 0 );
		DC hdcTemp = new DC( 0 );

		int hbmScreen = 0;
		int hbmOld = 0;

		try
		{
			while ( User32.GetForegroundWindow( ).getValue( ) != hwnd )
			{
				// sleep for a moment until the target window is in foreground
				Thread.sleep( 10L );
			}

			LRECT screenDimension = new LRECT( );

			if ( !User32.GetWindowRect( hWnd, screenDimension ) )
			{
				System.err.println( "Could not get screenDimension of HWND: "
						+ hwnd );
				return false;
			}

			int width = screenDimension.getWidth( );
			int height = screenDimension.getHeight( );

			hdcScreen = User32.GetWindowDC( hWnd );
			hdcTemp = Gdi32.CreateCompatibleDC( hdcScreen );

			hbmScreen = Gdi32.CreateCompatibleBitmap( hdcScreen, width, height );
			hbmOld = Gdi32.SelectObject( hdcTemp, hbmScreen );

			if ( !Gdi32.BitBlt( hdcTemp,
					0,
					0,
					width,
					height,
					hdcScreen,
					0,
					0,
					Gdi32.SRCCOPY ) )
			{
				System.err.println( "BitBlt was not successful!" );
				return false;
			}

			BITMAPINFOHEADER infobmp = new BITMAPINFOHEADER( );
			infobmp.getPointer( ).zeroMemory( );
			infobmp.getPointer( ).setIntAt( 0, infobmp.getSizeOf( ) );
			infobmp.getPointer( ).setIntAt( 4, width );
			infobmp.getPointer( ).setIntAt( 8, height );
			infobmp.getPointer( ).setShortAt( 12, (short) 1 );
			infobmp.getPointer( ).setShortAt( 14, (short) 24 );
			infobmp.getPointer( ).setIntAt( 16, 0 );
			infobmp.getPointer( ).setIntAt( 20, 0 );
			infobmp.getPointer( ).setIntAt( 24, 0 );
			infobmp.getPointer( ).setIntAt( 28, 0 );
			infobmp.getPointer( ).setIntAt( 32, 0 );
			infobmp.getPointer( ).setIntAt( 36, 0 );

			bitmap = new Pointer( MemoryBlockFactory.createMemoryBlock( width
					* height
					* 3 ) );

			if ( Gdi32.GetDIBits( hdcTemp,
					hbmScreen,
					0,
					height,
					bitmap,
					infobmp,
					Gdi32.DIB_RGB_COLORS ) == 0 )
			{
				System.err.println( "GetDIBits was not successful!" );
				return false;
			}

			bfheader = new Pointer( MemoryBlockFactory.createMemoryBlock( 14 ) );
			bfheader.setShortAt( 0, (short) 19778 );
			bfheader.setIntAt( 2, bfheader.getSize( )
					+ bitmap.getSize( )
					+ infobmp.getSizeOf( ) );
			bfheader.setShortAt( 6, (short) 0 );
			bfheader.setShortAt( 8, (short) 0 );
			bfheader.setIntAt( 10, bfheader.getSize( ) + infobmp.getSizeOf( ) );

			if ( useNative )
			{
				HANDLE hfile = Kernel32.CreateFile( path,
						Kernel32.AccessMask.GENERIC_WRITE.getValue( ),
						0,
						null,
						Kernel32.CreationDisposition.OPEN_ALWAYS.getValue( ),
						0,
						0 );
				if ( hfile.getValue( ) != 0 )
				{
					try
					{
						DWORD bytesWritten = new DWORD( 0 );
						Kernel32.WriteFile( hfile,
								bfheader,
								bfheader.getSize( ),
								bytesWritten,
								NullPointer.NULL );
						Kernel32.WriteFile( hfile,
								infobmp.getPointer( ),
								infobmp.getPointer( ).getSize( ),
								bytesWritten,
								NullPointer.NULL );
						Kernel32.WriteFile( hfile,
								bitmap,
								bitmap.getSize( ),
								bytesWritten,
								NullPointer.NULL );
					}
					finally
					{
						Kernel32.CloseHandle( hfile );
					}
				}
				else
					System.err.println( "CreateFile was not successful!" );
			}
			else
			{
				RandomAccessFile ra = new RandomAccessFile( path, "rw" );
				try
				{
					ra.writeShort( StructConverter.swapShort( (short) 19778 ) );
					ra.writeInt( StructConverter.swapInt( bfheader.getSize( )
							+ bitmap.getSize( )
							+ infobmp.getSizeOf( ) ) );

					ra.writeShort( StructConverter.swapShort( (short) 0 ) );
					ra.writeShort( StructConverter.swapShort( (short) 0 ) );
					ra.writeInt( StructConverter.swapInt( bfheader.getSize( )
							+ infobmp.getSizeOf( ) ) );

					ra.writeInt( StructConverter.swapInt( infobmp.getPointer( )
							.getAsInt( 0 ) ) );
					ra.writeInt( StructConverter.swapInt( infobmp.getPointer( )
							.getAsInt( 4 ) ) );
					ra.writeInt( StructConverter.swapInt( infobmp.getPointer( )
							.getAsInt( 8 ) ) );
					ra.writeShort( StructConverter.swapShort( infobmp.getPointer( )
							.getAsShort( 12 ) ) );
					ra.writeShort( StructConverter.swapShort( infobmp.getPointer( )
							.getAsShort( 14 ) ) );
					ra.writeInt( StructConverter.swapInt( infobmp.getPointer( )
							.getAsInt( 16 ) ) );
					ra.writeInt( StructConverter.swapInt( infobmp.getPointer( )
							.getAsInt( 20 ) ) );
					ra.writeInt( StructConverter.swapInt( infobmp.getPointer( )
							.getAsInt( 24 ) ) );
					ra.writeInt( StructConverter.swapInt( infobmp.getPointer( )
							.getAsInt( 28 ) ) );
					ra.writeInt( StructConverter.swapInt( infobmp.getPointer( )
							.getAsInt( 32 ) ) );
					ra.writeInt( StructConverter.swapInt( infobmp.getPointer( )
							.getAsInt( 36 ) ) );

					ra.write( bitmap.getMemory( ) );
				}
				finally
				{
					if ( ra != null )
						ra.close( );
				}
			}
		}
		finally
		{
			Gdi32.SelectObject( hdcTemp, hbmOld );
			Gdi32.DeleteObject( hbmScreen );
			Gdi32.DeleteDC( hdcTemp );
			User32.ReleaseDC( hWnd, hdcScreen );
			if ( bitmap != null )
				bitmap.dispose( );
			if ( bfheader != null )
				bfheader.dispose( );

			if ( foregroundHWND.getValue( ) != 0
					&& foregroundHWND.getValue( ) != hwnd )
			{
				// set the old window to foreground
				setForegroundWindowEx( foregroundHWND.getValue( ) );
			}
		}
		return true;
	}

	public static boolean setForegroundWindowEx( int hwnd )
			throws NativeException, IllegalAccessException
	{
		HWND hWnd = new HWND( hwnd );
		User32.SwitchToThisWindow( hWnd, true );
		setWindowAlwaysOnTop( hwnd, true );

		try
		{
			int lThreadWindow = User32.GetWindowThreadProcessId( hWnd );
			int lThreadForeWin = User32.GetWindowThreadProcessId( User32.GetForegroundWindow( ) );
			if ( lThreadWindow == lThreadForeWin )
			{
				return User32.SetForegroundWindow( hWnd );
			}
			else
			{
				User32.AttachThreadInput( lThreadForeWin, lThreadWindow, true );
				boolean b = User32.SetForegroundWindow( hWnd );
				User32.AttachThreadInput( lThreadForeWin, lThreadWindow, false );
				return b;
			}
		}
		finally
		{
			setWindowAlwaysOnTop( hwnd, false );
		}
	}
}
