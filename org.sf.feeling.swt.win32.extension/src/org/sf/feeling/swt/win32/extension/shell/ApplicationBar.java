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

import org.eclipse.swt.internal.win32.RECT;
import org.sf.feeling.swt.win32.extension.Win32;
import org.sf.feeling.swt.win32.internal.extension.APPBARDATA;
import org.sf.feeling.swt.win32.internal.extension.Extension;

/**
 * The ApplicationBar class sets or retrieves the application bar state
 * implemented by the BarState class.
 * 
 * @author <a href="mailto:cnfree2000@hotmail.com">cnfree</a>
 */
public class ApplicationBar
{

	/**
	 * Always-on-top on, autohide off (value is 2).
	 */
	public static final int STATE_ALWAYSONTOP = Win32.STATE_ALWAYSONTOP;

	/**
	 * Autohide on, always-on-top off (value is 1).
	 * 
	 * Don't support for Win7 and later.
	 */
	public static final int STATE_AUTOHIDE = Win32.STATE_AUTOHIDE;

	/**
	 * Autohide and always-on-top both on (value is 1|2).
	 */
	public static final int STATE_AUTOHIDE_ALWAYSONTOP = Win32.STATE_AUTOHIDE_ALWAYSONTOP;

	/**
	 * The null state (value is 0).
	 */
	public static final int STATE_NONE = Win32.STATE_NONE;

	/**
	 * Set specified appbar state. <br>
	 * <b>Important:</b>Microsoft 2000 and 98 shield the function, so you should
	 * check the OS Version at first.<br>
	 * The taskbar supports two display options: Auto-Hide and, in Windows Vista
	 * and earlier only, Always On Top (the taskbar is always in this mode in
	 * Windows 7 and later).
	 * 
	 * @param handle
	 *            the handle of the specified appbar
	 * @param state
	 *            the state of the specified appbar
	 * @return the result if set appbar state successfully
	 */
	public static int setAppBarState( int handle, int state )
	{
		APPBARDATA appBarData = new APPBARDATA( );
		appBarData.lParam = state;
		appBarData.hWnd = handle;
		return Extension.SHAppBarMessage( Win32.AMB_SETSTATE, appBarData );
	}

	/**
	 * Get state of specified appbar.
	 * 
	 * @param handle
	 *            the handle of the specified appbar
	 * @return the state of the specified appbar
	 */
	public static int getAppBarState( int handle )
	{
		APPBARDATA appBarData = new APPBARDATA( );
		appBarData.hWnd = handle;
		appBarData.cbSize = 24;
		int rc = Extension.SHAppBarMessage( Win32.AMB_GETSTATE, appBarData );
		return rc;
	}

	/**
	 * Get position and size of the specified appbar
	 * 
	 * @param handle
	 *            the handle of the specified appbar
	 * @return the information of the size and position of the specified appbar
	 */
	public static RECT getAppBarPosition( int handle )
	{
		APPBARDATA appBarData = new APPBARDATA( );
		appBarData.hWnd = handle;
		Extension.SHAppBarMessage( Win32.AMB_GETTASKBARPOS, appBarData );
		RECT rc = new RECT( );
		rc.left = appBarData.rcLeft;
		rc.top = appBarData.rcTop;
		rc.right = appBarData.rcRight;
		rc.bottom = appBarData.rcBottom;
		return rc;
	}
}
