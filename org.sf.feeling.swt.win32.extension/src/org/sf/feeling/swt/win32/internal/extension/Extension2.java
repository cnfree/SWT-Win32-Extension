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

import org.eclipse.swt.internal.win32.RECT;
import org.eclipse.swt.internal.win32.TCHAR;

public class Extension2
{

	static
	{
		Extension.loadNativeLibrary( "swt-extension-win32" );
	}

	public static final native boolean ShowCursor( boolean bShow );

	public static final native boolean AnimateWindow( int hwnd, int dwTime,
			int dwFlags );

	public static final native boolean CloseHandle( int eventHandle );

	public static final native short GetAsyncKeyState( int nVirtKey );

	public static final native int GetSubMenu( int hMenu, int position );

	public static final native int GetMenuItemID( int hMenu, int position );

	public static final boolean AppendMenu( int /* long */hMenu, int uFlags,
			int /* long */uIDNewItem, TCHAR lpNewItem )
	{
		if ( Extension.IsUnicode )
		{
			char[] lpNewItem1 = lpNewItem == null ? null : lpNewItem.chars;
			return AppendMenuW( hMenu, uFlags, uIDNewItem, lpNewItem1 );
		}
		byte[] lpNewItem1 = lpNewItem == null ? null : lpNewItem.bytes;
		return AppendMenuA( hMenu, uFlags, uIDNewItem, lpNewItem1 );
	}

	public static final boolean InsertMenu( int /* long */hMenu, int position,
			int uFlags, int /* long */uIDNewItem, TCHAR lpNewItem )
	{
		if ( Extension.IsUnicode )
		{
			char[] lpNewItem1 = lpNewItem == null ? null : lpNewItem.chars;
			return InsertMenuW( hMenu, position, uFlags, uIDNewItem, lpNewItem1 );
		}
		byte[] lpNewItem1 = lpNewItem == null ? null : lpNewItem.bytes;
		return InsertMenuA( hMenu, position, uFlags, uIDNewItem, lpNewItem1 );
	}

	public static final native boolean AppendMenuW( int /* long */hMenu,
			int uFlags, int /* long */uIDNewItem, char[] lpNewItem );

	public static final native boolean AppendMenuA( int /* long */hMenu,
			int uFlags, int /* long */uIDNewItem, byte[] lpNewItem );

	public static final native boolean InsertMenuW( int /* long */hMenu,
			int positon, int uFlags, int /* long */uIDNewItem, char[] lpNewItem );

	public static final native boolean InsertMenuA( int /* long */hMenu,
			int positon, int uFlags, int /* long */uIDNewItem, byte[] lpNewItem );

	public static final native int TrackPopupMenu( int /* long */hMenu,
			int uFlags, int x, int y, int nReserved, int /* long */hWnd,
			RECT prcRect );

	public static final native boolean SetLayeredWindowAttributes( int hwnd,
			int color, byte alpha, int type );

	public static final native boolean GetLayeredWindowAttributes(
			int /* long */hwnd, int[] pcrKey, byte[] pbAlpha, int[] pdwFlags );

	public static final int /* long */SetWindowLongPtr( int /* long */hWnd,
			int nIndex, int /* long */dwNewLong )
	{
		if ( Extension.IsUnicode )
			return SetWindowLongPtrW( hWnd, nIndex, dwNewLong );
		return SetWindowLongPtrA( hWnd, nIndex, dwNewLong );
	}

	public static final native int /* long */SetWindowLongPtrW(
			int /* long */hWnd, int nIndex, int /* long */dwNewLong );

	public static final native int /* long */SetWindowLongPtrA(
			int /* long */hWnd, int nIndex, int /* long */dwNewLong );

	public static final native boolean Beep( int freq, int druation );

	public static final native boolean MessageBeep( int type );

	public static final native int CreateMutex( boolean initialOwner,
			String name );

	public static final native boolean EnablePrivilege( int privilege,
			boolean enable, boolean currentThread );

	public static final native int OpenProcess( int desiredAccess,
			boolean inheritHandle, int processId );

	public static final native boolean TerminateProcess( int hProcess,
			int exitCode );

	public static final native boolean ReadProcessMemory( int hProcess,
			int inBaseAddress, int outputBufferPoint, int size );

	public static final native boolean WriteProcessMemory( int hProcess,
			int inBaseAddress, int inputBufferPoint, int size );

	public static final native boolean VirtualProtectEx( int hProcess,
			int inBaseAddress, int size, int privilege );

}
