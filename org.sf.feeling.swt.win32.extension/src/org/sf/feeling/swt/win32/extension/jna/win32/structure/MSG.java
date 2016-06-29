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

package org.sf.feeling.swt.win32.extension.jna.win32.structure;

import org.sf.feeling.swt.win32.extension.jna.datatype.AbstractBasicData;
import org.sf.feeling.swt.win32.extension.jna.datatype.UINT;
import org.sf.feeling.swt.win32.extension.jna.datatype.win32.LPARAM;
import org.sf.feeling.swt.win32.extension.jna.datatype.win32.WPARAM;
import org.sf.feeling.swt.win32.extension.jna.exception.NativeException;
import org.sf.feeling.swt.win32.extension.jna.ptr.Pointer;
import org.sf.feeling.swt.win32.extension.jna.ptr.memory.MemoryBlockFactory;

/**
 * $Id: MSG.java,v 1.5 2011/03/10 10:28:14 cnfree Exp $
 * 
 * <pre>
 * The C structure
 * typedef struct {
 * &nbsp;	HWND hwnd;
 * &nbsp;	 UINT message;
 * &nbsp;	WPARAM wParam;
 * &nbsp;	LPARAM lParam;
 * &nbsp;	DWORD time;
 * &nbsp;	POINT pt;
 * } MSG, *PMSG;
 * </pre>
 */
public class MSG extends AbstractBasicData
{

	public MSG( ) throws NativeException
	{
		super( null );
		createPointer( );
	}

	/**
	 * Method createPointer
	 * 
	 * @return a MemoryBlock
	 * 
	 */
	public Pointer createPointer( ) throws NativeException
	{
		pointer = new Pointer( MemoryBlockFactory.createMemoryBlock( sizeOf( ) ) );
		return pointer;
	}

	/**
	 * Method getValueFromPointer
	 * 
	 * @return a T
	 * 
	 */
	public Object getValueFromPointer( )
	{
		return this;
	}

	/**
	 * Method getValue
	 * 
	 * @return a T
	 * 
	 */
	public MSG getValue( )
	{
		return this;
	}

	/**
	 * Method getSizeOf
	 * 
	 * @return the size of this data
	 */
	public int getSizeOf( )
	{
		return sizeOf( );
	}

	public static int sizeOf( )
	{
		return HWND.sizeOf( )
				+ UINT.sizeOf( )
				+ WPARAM.sizeOf( )
				+ LPARAM.sizeOf( )
				+ 4
				+ POINT.sizeOf( );
	}

	/**
	 * Method getMessage()
	 * 
	 * @return returns the message identifier
	 * @throws NativeException
	 */
	public UINT getMessage( ) throws NativeException
	{
		return new UINT( pointer.getAsShort( 4 ) );
	}

	public static class WindowsConstants
	{

		/*
		 * ShowWindow() Commands
		 */

		public static final int SW_HIDE = 0;
		public static final int SW_SHOWNORMAL = 1;
		public static final int SW_NORMAL = 1;
		public static final int SW_SHOWMINIMIZED = 2;
		public static final int SW_SHOWMAXIMIZED = 3;
		public static final int SW_MAXIMIZE = 3;
		public static final int SW_SHOWNOACTIVATE = 4;
		public static final int SW_SHOW = 5;
		public static final int SW_MINIMIZE = 6;
		public static final int SW_SHOWMINNOACTIVE = 7;
		public static final int SW_SHOWNA = 8;
		public static final int SW_RESTORE = 9;
		public static final int SW_SHOWDEFAULT = 10;
		public static final int SW_FORCEMINIMIZE = 11;
		public static final int SW_MAX = 11;

		/*
		 * Window Styles
		 */
		public static final int WS_OVERLAPPED = 0x00000000;
		public static final int WS_POPUP = 0x80000000;
		public static final int WS_CHILD = 0x40000000;
		public static final int WS_MINIMIZE = 0x20000000;
		public static final int WS_VISIBLE = 0x10000000;
		public static final int WS_DISABLED = 0x08000000;
		public static final int WS_CLIPSIBLINGS = 0x04000000;
		public static final int WS_CLIPCHILDREN = 0x02000000;
		public static final int WS_MAXIMIZE = 0x01000000;
		public static final int WS_CAPTION = 0x00C00000; /*
														 * WS_BORDER |
														 * WS_DLGFRAME
														 */
		public static final int WS_BORDER = 0x00800000;
		public static final int WS_DLGFRAME = 0x00400000;
		public static final int WS_VSCROLL = 0x00200000;
		public static final int WS_HSCROLL = 0x00100000;
		public static final int WS_SYSMENU = 0x00080000;
		public static final int WS_THICKFRAME = 0x00040000;
		public static final int WS_GROUP = 0x00020000;
		public static final int WS_TABSTOP = 0x00010000;

		public static final int WS_MINIMIZEBOX = 0x00020000;
		public static final int WS_MAXIMIZEBOX = 0x00010000;

		public static final int WS_TILED = WS_OVERLAPPED;
		public static final int WS_ICONIC = WS_MINIMIZE;
		public static final int WS_SIZEBOX = WS_THICKFRAME;

		public static final int WS_EX_ACCEPTFILES = 0x10;

		public static final int WS_EX_APPWINDOW = 0x40000;

		public static final int WS_EX_CLIENTEDGE = 0x200;

		public static final int WS_EX_CONTEXTHELP = 0x400;

		public static final int WS_EX_CONTROLPARENT = 0x10000;

		public static final int WS_EX_DLGMODALFRAME = 0x1;

		public static final int WS_EX_LEFT = 0x0;

		public static final int WS_EX_LEFTSCROLLBAR = 0x4000;

		public static final int WS_EX_LTRREADING = 0x0;

		public static final int WS_EX_MDICHILD = 0x40;

		public static final int WS_EX_NOACTIVATE = 0x8000000;

		public static final int WS_EX_NOPARENTNOTIFY = 0x4;

		public static final int WS_EX_OVERLAPPEDWINDOW = 0x300;

		public static final int WS_EX_PALETTEWINDOW = 0x188;

		public static final int WS_EX_RIGHT = 0x1000;

		public static final int WS_EX_RIGHTSCROLLBAR = 0x0;

		public static final int WS_EX_RTLREADING = 0x2000;

		public static final int WS_EX_STATICEDGE = 0x20000;

		public static final int WS_EX_TOOLWINDOW = 0x80;

		public static final int WS_EX_TOPMOST = 0x8;

		public static final int WS_EX_TRANSPARENT = 0x20;

		public static final int WS_EX_WINDOWEDGE = 0x100;

		public static final int WS_EX_LAYERED = 0x80000;

		public static final int LWA_ALPHA = 0x2;

		public final static int GWL_EXSTYLE = -20;
		public final static int GWL_STYLE = -16;
		public final static int GWL_WNDPROC = -4;
		public final static int GWLP_WNDPROC = -4;
		public final static int GWL_HINSTANCE = -6;
		public final static int GWLP_HINSTANCE = -6;
		public final static int GWL_HWNDPARENT = -8;
		public final static int GWLP_HWNDPARENT = -8;
		public final static int GWL_ID = -12;
		public final static int GWLP_ID = -12;
		public final static int GWL_USERDATA = -21;
		public final static int GWLP_USERDATA = -21;

		public final static int CW_USEDEFAULT = ( (int) 0x80000000 );

		/*
		 * Common Window Styles
		 */
		public static final int WS_OVERLAPPEDWINDOW = ( WS_OVERLAPPED
				| WS_CAPTION
				| WS_SYSMENU
				| WS_THICKFRAME
				| WS_MINIMIZEBOX | WS_MAXIMIZEBOX );

		public static final int WS_POPUPWINDOW = ( WS_POPUP | WS_BORDER | WS_SYSMENU );

		public static final int WS_CHILDWINDOW = ( WS_CHILD );

		public static final int WS_TILEDWINDOW = WS_OVERLAPPEDWINDOW;

		public static final int SC_SIZE = 0xF000;
		public static final int SC_MAXIMIZE = 0xF030;
		public static final int SC_MINIMIZE = 0xF020;
		public static final int SC_CLOSE = 0xF060;

	}
}
