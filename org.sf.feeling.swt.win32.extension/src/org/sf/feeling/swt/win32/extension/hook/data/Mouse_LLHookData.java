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
package org.sf.feeling.swt.win32.extension.hook.data;

import org.sf.feeling.swt.win32.extension.Win32;
import org.sf.feeling.swt.win32.extension.hook.data.struct.MouseLLHookStruct;
import org.sf.feeling.swt.win32.internal.extension.Extension;
import org.sf.feeling.swt.win32.internal.extension.MSLLHOOKSTRUCT;

/**
 * {@link Win32#WH_MOUSE_LL} Hook Data. Be used for wrapping
 * {@link Win32#WH_MOUSE_LL} HookProc information.
 * 
 * @author <a href="mailto:cnfree2000@hotmail.com">cnfree</a>
 */
public class Mouse_LLHookData extends HookData {

	private static final long serialVersionUID = -8417365938806543229L;

	private MSLLHOOKSTRUCT struct;

	public static final int WM_LBUTTONDOWN = Win32.WM_LBUTTONDOWN;
	public static final int WM_LBUTTONUP = Win32.WM_LBUTTONUP;
	public static final int WM_LBUTTONDBLCLK = Win32.WM_LBUTTONDBLCLK;

	public static final int WM_RBUTTONDOWN = Win32.WM_RBUTTONDOWN;
	public static final int WM_RBUTTONUP = Win32.WM_RBUTTONUP;
	public static final int WM_RBUTTONDBLCLK = Win32.WM_RBUTTONDBLCLK;

	public static final int WM_MBUTTONDOWN = Win32.WM_MBUTTONDOWN;
	public static final int WM_MBUTTONUP = Win32.WM_MBUTTONUP;
	public static final int WM_MBUTTONDBLCLK = Win32.WM_MBUTTONDBLCLK;

	public static final int WM_NCLBUTTONDOWN = Extension.WM_NCLBUTTONDOWN;
	public static final int WM_NCLBUTTONUP = Win32.WM_NCLBUTTONUP;
	public static final int WM_NCLBUTTONDBLCLK = Win32.WM_NCLBUTTONDBLCLK;

	public static final int WM_NCRBUTTONDOWN = Win32.WM_NCRBUTTONDOWN;
	public static final int WM_NCRBUTTONUP = Win32.WM_NCRBUTTONUP;
	public static final int WM_NCRBUTTONDBLCLK = Win32.WM_NCRBUTTONDBLCLK;

	public static final int WM_NCMBUTTONDOWN = Win32.WM_NCMBUTTONDOWN;
	public static final int WM_NCMBUTTONUP = Win32.WM_NCMBUTTONUP;
	public static final int WM_NCMBUTTONDBLCLK = Win32.WM_NCMBUTTONDBLCLK;

	public static final int WM_NCMOUSEMOVE = Win32.WM_NCMOUSEMOVE;

	public static final int WM_MOUSEWHEEL = Win32.WM_MOUSEWHEEL;
	public static final int WM_MOUSEFIRST = Win32.WM_MOUSEFIRST;
	public static final int WM_MOUSEMOVE = Win32.WM_MOUSEMOVE;

	/**
	 * <b>Important:</b> This method is used by the native library to create a hook
	 *             data. Not for user.
	 * @param struct
	 *            MSLLHOOKSTRUCT struct
	 */
	public void setStruct(MSLLHOOKSTRUCT struct) {
		this.struct = struct;
	}

	/**
	 * Specifies the x-coordinates of the cursor, in screen coordinates.
	 * 
	 * @return MSLLHOOKSTRUCT member pt.x
	 */
	public int getPointX() {
		if (struct != null)
			return struct.pointX;
		else
			return 0;
	}

	/**
	 * Specifies the y-coordinates of the cursor, in screen coordinates.
	 * 
	 * @return MSLLHOOKSTRUCT member pt.y
	 */
	public int getPointY() {
		if (struct != null)
			return struct.pointY;
		else
			return 0;
	}

	/**
	 * Specifies the time stamp for the MSLLHOOKSTRUCT struct.
	 * 
	 * @return MSLLHOOKSTRUCT member time
	 */
	public int getTime() {
		if (struct != null)
			return struct.time;
		else
			return 0;
	}

	/**
	 * If the message is {@link Win32#WM_MOUSEWHEEL}, the high-order word of this
	 * member is the wheel delta. The low-order word is reserved. A positive
	 * value indicates that the wheel was rotated forward, away from the user; a
	 * negative value indicates that the wheel was rotated backward, toward the
	 * user. One wheel click is defined as {@link Win32#WHEEL_DELTA}, which is 120.
	 * If the message is {@link Win32#WM_XBUTTONDOWN}, {@link Win32#WM_XBUTTONUP},
	 * {@link Win32#WM_XBUTTONDBLCLK}, {@link Win32#WM_NCXBUTTONDOWN},
	 * {@link Win32#WM_NCXBUTTONUP}, or {@link Win32#WM_NCXBUTTONDBLCLK}, the high-order
	 * word specifies which X button was pressed or released, and the low-order
	 * word is reserved. This value can be one or more of the following values.
	 * Otherwise, mouseData is not used.
	 * 
	 * @return MSLLHOOKSTRUCT member mouseData
	 */
	public int getMouseData() {
		if (struct != null)
			return struct.mouseData;
		else
			return 0;
	}

	/**
	 * Specifies the event-injected flag.
	 * 
	 * @return MSLLHOOKSTRUCT member flags
	 */
	public int getFlags() {
		if (struct != null)
			return struct.flags;
		else
			return 0;
	}

	/**
	 * Specifies extra information associated with the message.
	 * 
	 * @return MSLLHOOKSTRUCT member dwExtraInfo
	 */
	public int getExtraInfo() {
		if (struct != null)
			return struct.dwExtraInfo;
		else
			return 0;
	}

	/**
	 * Get MSLLHOOKSTRUCT struct from HookProc.
	 * 
	 * @return MSLLHOOKSTRUCT struct
	 */
	public MouseLLHookStruct getStruct() {
		return new MouseLLHookStruct(struct);
	}
}
