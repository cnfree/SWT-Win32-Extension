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
package org.sf.feeling.swt.win32.extension.hook.data.struct;

import org.sf.feeling.swt.win32.internal.extension.Extension;
import org.sf.feeling.swt.win32.internal.extension.KBDLLHOOKSTRUCT;

/**
 * A KeyboardLLHookStruct object is a KBDLLHOOKSTRUCT struct wrapper. It provide
 * the way to set and get KBDLLHOOKSTRUCT data.
 * 
 * @author <a href="mailto:cnfree2000@hotmail.com">cnfree</a>
 * 
 */
public class KeyboardLLHookStruct {
	private KBDLLHOOKSTRUCT struct;

	public int getVkCode() {
		return struct.vkCode;
	}

	public int getScanCode() {
		return struct.scanCode;
	}

	public int getFlags() {
		return struct.flags;
	}

	public int getTime() {
		return struct.time;
	}

	public int getDwExtraInfo() {
		return struct.dwExtraInfo;
	}

	/**
	 * <b>Important</b>:It's not for user, just for wrapping struct data and
	 * providing it to user.
	 * 
	 * @param struct
	 *            KBDLLHOOKSTRUCT struct
	 */
	public KeyboardLLHookStruct(KBDLLHOOKSTRUCT struct) {
		this.struct = struct;
	}

	public void setVkCode(int vkCode) {
		struct.vkCode = vkCode;
	}

	public void setScanCode(int scanCode) {
		struct.scanCode = scanCode;
	}

	public void setFlags(int flags) {
		struct.flags = flags;
	}

	public void setTime(int time) {
		struct.time = time;
	}

	public void setDwExtraInfo(int dwExtraInfo) {
		struct.dwExtraInfo = dwExtraInfo;
	}

	/**
	 * Get a KeyboardLLHookStruct object via a memory KBDLLHOOKSTRUCT struct
	 * point.
	 * 
	 * @param point
	 *            a memory KBDLLHOOKSTRUCT struct point
	 * @return A KeyboardLLHookStruct object.
	 */
	public static KeyboardLLHookStruct valueOf(int point) {
		KBDLLHOOKSTRUCT struct = new KBDLLHOOKSTRUCT();
		Extension.CreateStructByPoint(point, struct);
		return new KeyboardLLHookStruct(struct);
	}

	/**
	 * Set java object data to memory point.
	 * 
	 * @param point
	 *            Specify memory KBDLLHOOKSTRUCT struct point.
	 * @return return true if operation is successful, otherwise return false.
	 */
	public boolean saveToPoint(int point) {
		return Extension.SaveStructToPoint(struct, point);
	}

	/**
	 * Set specified KeyboardLLHookStruct object as this object content.
	 * 
	 * @param struct
	 *            Specified KeyboardLLHookStruct object will be set.
	 */
	public void setValue(KeyboardLLHookStruct struct) {
		if (this.struct == null)
			this.struct = struct.struct;
		else {
			this.struct.vkCode = struct.struct.vkCode;
			this.struct.scanCode = struct.struct.scanCode;
			this.struct.flags = struct.struct.flags;
			this.struct.time = struct.struct.time;
			this.struct.dwExtraInfo = struct.struct.dwExtraInfo;
		}
	}
}
