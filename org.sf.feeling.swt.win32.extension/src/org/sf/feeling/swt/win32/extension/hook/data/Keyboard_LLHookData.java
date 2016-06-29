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
import org.sf.feeling.swt.win32.extension.hook.data.struct.KeyboardLLHookStruct;
import org.sf.feeling.swt.win32.internal.extension.Extension;
import org.sf.feeling.swt.win32.internal.extension.KBDLLHOOKSTRUCT;

/**
 * {@link Win32#WH_KEYBOARD_LL} Hook Data. Be used for wrapping
 * {@link Win32#WH_KEYBOARD_LL} HookProc information.
 * 
 * @author <a href="mailto:cnfree2000@hotmail.com">cnfree</a>
 */
public class Keyboard_LLHookData extends HookData {

	private static final long serialVersionUID = 1246846046638873890L;

	private KBDLLHOOKSTRUCT struct;

	/**
	 * KEYDOWN Event constant
	 */
	public static final int WM_KEYDOWN = Extension.WM_KEYDOWN;
	/**
	 * KEYUP Event constant
	 */
	public static final int WM_KEYUP = Extension.WM_KEYUP;
	/**
	 * KEYLAST Event constant
	 */
	public static final int WM_KEYLAST = Extension.WM_KEYLAST;
	/**
	 * KEYFIRST Event constant
	 */
	public static final int WM_KEYFIRST = Extension.WM_KEYFIRST;
	/**
	 * SYSKEYDOWN Event constant
	 */
	public static final int WM_SYSKEYDOWN = Extension.WM_SYSKEYDOWN;
	/**
	 * SYSKEYUP Event constant
	 */
	public static final int WM_SYSKEYUP = Extension.WM_SYSKEYUP;

	/**
	 * <b>Important:</b> This method is used by the native library to create a hook
	 *             data. Not for user.
	 * @param struct
	 *            EVENTMSG struct
	 */
	public void setStruct(KBDLLHOOKSTRUCT struct) {
		this.struct = struct;
	}

	/**
	 * Get KBDLLHOOKSTRUCT member scanCode from HookProc.
	 * 
	 * @return KBDLLHOOKSTRUCT member scanCode.
	 */
	public int getScanCode() {
		if (struct != null)
			return struct.scanCode;
		else
			return 0;
	}

	/**
	 * Get KBDLLHOOKSTRUCT member time from HookProc.
	 * 
	 * @return KBDLLHOOKSTRUCT member time.
	 */
	public int getTime() {
		if (struct != null)
			return struct.time;
		else
			return 0;
	}

	/**
	 * Get KBDLLHOOKSTRUCT member time from HookProc.
	 * 
	 * @return KBDLLHOOKSTRUCT member time.
	 */
	public int vkCode() {
		if (struct != null)
			return struct.vkCode;
		else
			return 0;
	}

	/**
	 * Get KBDLLHOOKSTRUCT member flags from HookProc.
	 * 
	 * @return KBDLLHOOKSTRUCT member flags.
	 */
	public int getFlags() {
		if (struct != null)
			return struct.flags;
		else
			return 0;
	}

	/**
	 * Get KBDLLHOOKSTRUCT member dwExtraInfo from HookProc.
	 * 
	 * @return KBDLLHOOKSTRUCT member dwExtraInfo.
	 */
	public int getExtraInfo() {
		if (struct != null)
			return struct.dwExtraInfo;
		else
			return 0;
	}

	/**
	 * Get KBDLLHOOKSTRUCT struct from HookProc.
	 * 
	 * @return KBDLLHOOKSTRUCT struct
	 */
	public KeyboardLLHookStruct getStruct() {
		return new KeyboardLLHookStruct(struct);
	}
}
