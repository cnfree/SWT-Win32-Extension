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
import org.sf.feeling.swt.win32.internal.extension.MOUSEHOOKSTRUCT;

/**
 * A MouseHookStruct object is a MOUSEHOOKSTRUCT struct wrapper. It provide the
 * way to set and get MOUSEHOOKSTRUCT data.
 * 
 * @author <a href="mailto:cnfree2000@hotmail.com">cnfree</a>
 * 
 */
public class MouseHookStruct {

	private MOUSEHOOKSTRUCT struct;

	public int getPointX() {
		return struct.pointX;
	}

	public int getPointY() {
		return struct.pointY;
	}

	public int getHwnd() {
		return struct.hwnd;
	}

	public int getWHitTestCode() {
		return struct.wHitTestCode;
	}

	public int getDwExtraInfo() {
		return struct.dwExtraInfo;
	}

	/**
	 * <b>Important</b>:It's not for user, just for wrapping struct data and
	 * providing it to user.
	 * 
	 * @param struct
	 *            MOUSEHOOKSTRUCT struct
	 */
	public MouseHookStruct(MOUSEHOOKSTRUCT struct) {
		this.struct = struct;
	}

	public void setPointX(int pointX) {
		struct.pointX = pointX;
	}

	public void setPointY(int pointY) {
		struct.pointY = pointY;
	}

	public void setHwnd(int hwnd) {
		struct.hwnd = hwnd;
	}

	public void setWHitTestCode(int hitTestCode) {
		struct.wHitTestCode = hitTestCode;
	}

	public void setDwExtraInfo(int dwExtraInfo) {
		struct.dwExtraInfo = dwExtraInfo;
	}

	/**
	 * Get a MouseHookStruct object via a memory MOUSEHOOKSTRUCT struct point.
	 * 
	 * @param point
	 *            a memory MOUSEHOOKSTRUCT struct point
	 * @return An MOUSEHOOKSTRUCT object.
	 */
	public static MouseHookStruct valueOf(int point) {
		MOUSEHOOKSTRUCT struct = new MOUSEHOOKSTRUCT();
		Extension.CreateStructByPoint(point, struct);
		return new MouseHookStruct(struct);
	}

	/**
	 * Set java object data to memory point.
	 * 
	 * @param point
	 *            Specify memory MOUSEHOOKSTRUCT struct point.
	 * @return return true if operation is successful, otherwise return false.
	 */
	public boolean saveToPoint(int point) {
		return Extension.SaveStructToPoint(struct, point);
	}

	/**
	 * Set specified MouseHookStruct object as this object content.
	 * 
	 * @param struct
	 *            Specified MouseHookStruct object will be set.
	 */
	public void setValue(MouseHookStruct struct) {
		if (this.struct == null)
			this.struct = struct.struct;
		else {
			this.struct.pointX = struct.struct.pointX;
			this.struct.pointY = struct.struct.pointY;
			this.struct.hwnd = struct.struct.hwnd;
			this.struct.wHitTestCode = struct.struct.wHitTestCode;
			this.struct.dwExtraInfo = struct.struct.dwExtraInfo;
		}
	}
}
