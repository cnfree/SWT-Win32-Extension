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

import java.util.HashMap;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.internal.win32.MENUITEMINFO;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.TCHAR;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.sf.feeling.swt.win32.extension.Win32;
import org.sf.feeling.swt.win32.internal.extension.Extension;
import org.sf.feeling.swt.win32.internal.extension.Extension2;
import org.sf.feeling.swt.win32.internal.extension.graphics.BitmapUtil;

/**
 * A System menu manage instance can manage a shell's system menu.
 * 
 * @author <a href="mailto:cnfree2000@hotmail.com">cnfree</a>
 * 
 */
public class SystemMenuManager {
	private int hMenu;
	private Map itemMap = new HashMap();

	private Shell shell;
	private SystemMenuMsgHook hook;

	/**
	 * Create a system menu manager.<br>
	 * <b>Important:</b>One shell should have only one system menu manager.
	 * 
	 * @param shell
	 *            the shell which be wanted to observe its system menu.
	 */
	public SystemMenuManager(Shell shell) {
		this.shell = shell;
		hMenu = Extension.GetSystemMenu(shell.handle, false);
		hook = new SystemMenuMsgHook(this);
		hook.installHook();
		shell.addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {
				dispose();
			}
		});
	}

	/**
	 * Add an item on the end of the system menu.
	 * 
	 * @param item
	 *            the menu item will be insert.
	 */
	public void addItem(SystemMenuItem item) {
		addItem(item, -1);
	}

	/**
	 * Add an item on the specified position of the system menu.
	 * 
	 * @param item
	 *            the menu item will be insert.
	 * @param position
	 *            the specified position of the system menu, if position<0,
	 *            will add on the end of system menu.
	 */
	public void addItem(SystemMenuItem item, int position) {
		addItem(item, position, Win32.MF_BYPOSITION);
	}

	/**
	 * Add an item on the specified position of the system menu.
	 * 
	 * @param item
	 *            the menu item will be insert.
	 * @param position
	 *            if(style == Win32.MF_BYCOMMAND) position is menu id, else
	 *            position is the index of the system menu.
	 * @param style
	 *            Win32.MF_BYCOMMAND or Win32.MF_BYPOSITION
	 */
	public void addItem(SystemMenuItem item, int position, int style) {
		checkState();
		if (itemMap.containsKey(item.getId()))
			return;
		int uFlags = Win32.MF_BYCOMMAND;
		if (style == Win32.MF_BYPOSITION)
			uFlags = Win32.MF_BYPOSITION;
		TCHAR lpNewItem = null;
		if ((item.getStyle() & SWT.SEPARATOR) != 0) {
			uFlags |= Win32.MF_SEPARATOR;
		} else {
			uFlags |= Win32.MF_STRING;
			lpNewItem = new TCHAR(0, item.getText(), true);
		}
		Extension2.InsertMenu(hMenu, position, uFlags, item.getId().intValue(),
				lpNewItem);
		if ((item.getStyle() & SWT.SEPARATOR) == 0) {
			item.setEnabled(item.isEnabled());
			item.setSelection(item.getSelection());
			if (item.getImage() != null)
				setImage(item.getId().intValue(), item.getImage());
		}
		itemMap.put(item.getId(), item);
	};

	private void checkState() {
		if (shell == null) {
			throw new NullPointerException("Shell is null");
		} else if (shell.isDisposed()) {
			throw new IllegalStateException("Shell has been closed");
		}
	}

	boolean getEnabled(int itemId) {
		checkState();
		MENUITEMINFO info = new MENUITEMINFO();
		info.cbSize = MENUITEMINFO.sizeof;
		info.fMask = Win32.MIIM_STATE;
		boolean success = Extension.GetMenuItemInfo(hMenu, itemId, false, info);
		if (!success)
			SWT.error(SWT.ERROR_CANNOT_GET_ENABLED);
		int bits = Win32.MFS_DISABLED | Win32.MFS_GRAYED;
		if ((info.fState & bits) == 0)
			return true;
		else
			return false;
	}

	SystemMenuItem getItem(int itemId) {
		return (SystemMenuItem) itemMap.get(new Integer(itemId));
	}

	boolean getSelection(int itemId) {
		checkState();
		MENUITEMINFO info = new MENUITEMINFO();
		info.cbSize = MENUITEMINFO.sizeof;
		info.fMask = Win32.MIIM_STATE;
		boolean success = Extension.GetMenuItemInfo(hMenu, itemId, false, info);
		if (!success)
			SWT.error(SWT.ERROR_CANNOT_GET_SELECTION);
		return (info.fState & Win32.MFS_CHECKED) != 0;
	}

	/**
	 * Get the system menu manager observed shell.
	 * 
	 * @return the shell of system menu manager observed
	 */
	public Shell getShell() {
		return shell;
	}

	String getText(int itemId) {
		checkState();
		MENUITEMINFO info = new MENUITEMINFO();
		info.cbSize = MENUITEMINFO.sizeof;
		if (Win32.getWin32Version() >= Win32.VERSION(4, 10)) {
			info.fMask = Win32.MIIM_STRING;
		} else {
			info.fMask = Win32.MIIM_TYPE;
			info.fType = Win32.MFT_STRING;
		}
		int /* long */hHeap = Extension.GetProcessHeap();
		int /* long */pszText = 0;
		pszText = Extension.HeapAlloc(hHeap, Extension.HEAP_ZERO_MEMORY, 1024);
		info.dwTypeData = pszText;
		info.cch = 1024;
		boolean success = Extension.GetMenuItemInfo(hMenu, itemId, false, info);
		if (!success) {
			if (pszText != 0)
				OS.HeapFree(hHeap, 0, pszText);
			SWT.error(SWT.ERROR_CANNOT_GET_TEXT);
		}
		pszText = info.dwTypeData;
		TCHAR buffer = new TCHAR(0, info.cch / TCHAR.sizeof);
		OS.MoveMemory(buffer, pszText, info.cch);
		if (pszText != 0)
			OS.HeapFree(hHeap, 0, pszText);
		return buffer.toString(0, buffer.strlen());
	}

	private void initSysMenuPopup(int style, int clsStyle) {
		boolean gray = (style & Win32.WS_THICKFRAME) == 0
				|| (style & (Win32.WS_MAXIMIZE | Win32.WS_MINIMIZE)) != 0;
		setEnabled(Win32.SC_SIZE, !gray);
		gray = (style & (Win32.WS_MAXIMIZE | Win32.WS_MINIMIZE)) != 0;
		setEnabled(Win32.SC_MOVE, !gray);
		gray = (style & Win32.WS_MINIMIZEBOX) == 0
				|| (style & Win32.WS_MINIMIZE) != 0;
		setEnabled(Win32.SC_MINIMIZE, !gray);
		gray = (style & Win32.WS_MAXIMIZEBOX) == 0
				|| (style & Win32.WS_MAXIMIZE) != 0;
		setEnabled(Win32.SC_MAXIMIZE, !gray);
		gray = (style & (Win32.WS_MAXIMIZE | Win32.WS_MINIMIZE)) == 0;
		setEnabled(Win32.SC_RESTORE, !gray);

		gray = (clsStyle & Win32.CS_NOCLOSE) != 0;
		/* The menu item must keep its state if it's disabled */
		if (gray)
			setEnabled(Win32.SC_CLOSE, false);
	}

	void removeItem(int itemId) {
		checkState();
		Extension.RemoveMenu(hMenu, itemId, Win32.MF_BYCOMMAND);
		itemMap.remove(new Integer(itemId));
	}

	/**
	 * Reset the shell's system menu, this operation will remove all of the
	 * custom menu items.
	 */
	public void resetMenu() {
		checkState();
		Extension.GetSystemMenu(shell.handle, true);
		itemMap.clear();
		hMenu = Extension.GetSystemMenu(shell.handle, false);
	}

	void setEnabled(int itemId, boolean enabled) {
		checkState();
		MENUITEMINFO info = new MENUITEMINFO();
		info.cbSize = MENUITEMINFO.sizeof;
		info.fMask = Win32.MIIM_STATE;
		boolean success = Extension.GetMenuItemInfo(hMenu, itemId, false, info);
		if (!success) {
			return;
		}
		int bits = Win32.MFS_DISABLED | Win32.MFS_GRAYED;
		if (enabled) {
			if ((info.fState & bits) == 0)
				return;
			info.fState &= ~bits;
		} else {
			if ((info.fState & bits) == bits)
				return;
			info.fState |= bits;
		}
		success = Extension.SetMenuItemInfo(hMenu, itemId, false, info);
		if (!success) {
			if (Win32.getWin32Version() >= Win32.VERSION(6, 0)) {
				success = itemId == Extension.GetMenuDefaultItem(hMenu,
						Win32.MF_BYCOMMAND, Win32.GMDI_USEDISABLED);
			}
			if (!success)
				SWT.error(SWT.ERROR_CANNOT_SET_ENABLED);
		}
	}

	void setImage(int itemId, Image image) {
		checkState();
		int bitmap = image != null ? BitmapUtil.create32bitDIB(image) : 0;
		Extension.SetMenuItemBitmaps(hMenu, itemId, Win32.MF_BITMAP, bitmap,
				bitmap);
	}

	void setSelection(int itemId, boolean selected) {
		checkState();
		MENUITEMINFO info = new MENUITEMINFO();
		info.cbSize = MENUITEMINFO.sizeof;
		info.fMask = Win32.MIIM_STATE;
		boolean success = Extension.GetMenuItemInfo(hMenu, itemId, false, info);
		if (!success)
			SWT.error(SWT.ERROR_CANNOT_SET_SELECTION);
		if (!success)
			SWT.error(SWT.ERROR_CANNOT_SET_SELECTION);
		info.fState &= ~Win32.MFS_CHECKED;
		if (selected)
			info.fState |= Win32.MFS_CHECKED;
		success = Extension.SetMenuItemInfo(hMenu, itemId, false, info);
		if (!success) {
			if (Win32.getWin32Version() >= Win32.VERSION(6, 0)) {
				success = itemId == Extension.GetMenuDefaultItem(hMenu,
						Win32.MF_BYCOMMAND, Win32.GMDI_USEDISABLED);
			}
			if (!success)
				SWT.error(SWT.ERROR_CANNOT_SET_SELECTION);
		}
	}

	void setText(int itemId, String text) {
		checkState();
		int /* long */hHeap = Extension.GetProcessHeap();
		int /* long */pszText = 0;
		boolean success = false;

		MENUITEMINFO info = new MENUITEMINFO();
		info.cbSize = MENUITEMINFO.sizeof;
		/* Use the character encoding for the default locale */
		TCHAR buffer = new TCHAR(0, text, true);
		int byteCount = buffer.length() * TCHAR.sizeof;
		pszText = Extension.HeapAlloc(hHeap, Extension.HEAP_ZERO_MEMORY,
				byteCount);
		OS.MoveMemory(pszText, buffer, byteCount);
		/*
		 * Bug in Windows 2000. For some reason, when MIIM_TYPE is set on a menu
		 * item that also has MIIM_BITMAP, the MIIM_TYPE clears the MIIM_BITMAP
		 * style. The fix is to use MIIM_STRING.
		 */
		if (Win32.getWin32Version() >= Win32.VERSION(4, 10)) {
			info.fMask = Win32.MIIM_STRING;
		} else {
			info.fMask = Win32.MIIM_TYPE;
			info.fType = Win32.MFT_STRING;
		}
		info.dwTypeData = pszText;
		success = OS.SetMenuItemInfo(hMenu, itemId, false, info);

		if (pszText != 0)
			OS.HeapFree(hHeap, 0, pszText);
		if (!success)
			SWT.error(SWT.ERROR_CANNOT_SET_TEXT);
	}

	/**
	 * Show the system menu
	 */
	public void showMenu() {
		int pos = Extension.GetMessagePos();
		showMenu(new Point((short) (pos & 0xFFFF), (short) (pos >> 16)));
	}

	/**
	 * Show the system menu on the specified position.
	 * 
	 * @param location
	 *            the position of the system menu will be displayed.
	 */
	public void showMenu(Point location) {
		checkState();
		int flags = Win32.TPM_LEFTBUTTON | Win32.TPM_LEFTALIGN
				| Win32.TPM_TOPALIGN | Win32.TPM_NONOTIFY | Win32.TPM_RETURNCMD;
		if (Extension.GetKeyState(Win32.VK_LBUTTON) >= 0)
			flags |= Win32.TPM_RIGHTBUTTON;

		initSysMenuPopup(Extension
				.GetWindowLongA(shell.handle, Win32.GWL_STYLE), Extension
				.GetClassLong(shell.handle, Win32.GCL_STYLE));

		visiable = true;
		int cmd = Extension2.TrackPopupMenu(hMenu, flags, location.x < 0 ? 0
				: location.x, location.y, 0, shell.handle, null);
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				visiable = false;
			}
		});
		if (cmd != 0)
			Extension.SendMessage(shell.handle, Win32.WM_SYSCOMMAND, cmd, 0);
	}

	boolean visiable = false;

	boolean disposed = false;

	public void dispose() {
		if (!disposed) {
			disposed = true;
			hook.unInstallHook();
			itemMap.clear();
		}
	}

	public boolean isDisposed() {
		return disposed;
	}

	public boolean isMenuVisiable() {
		return visiable;
	}
}
