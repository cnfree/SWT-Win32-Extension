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
import org.sf.feeling.swt.win32.internal.extension.MSLLHOOKSTRUCT;

/**
 * A MouseLLHookStruct object is a MSLLHOOKSTRUCT struct wrapper. It provide the
 * way to set and get MSLLHOOKSTRUCT data.
 * 
 * @author <a href="mailto:cnfree2000@hotmail.com">cnfree</a>
 * 
 */
public class MouseLLHookStruct {

	private MSLLHOOKSTRUCT struct;

	public int getPointX() {
		return struct.pointX;
	}

	public int getPointY() {
		return struct.pointY;
	}

	public int getMouseData() {
		return struct.mouseData;
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
	 *            MSLLHOOKSTRUCT struct
	 */
	public MouseLLHookStruct(MSLLHOOKSTRUCT struct) {
		this.struct = struct;
	}

	public void setPointX(int pointX) {
		struct.pointX = pointX;
	}

	public void setPointY(int pointY) {
		struct.pointY = pointY;
	}

	public void setMouseData(int mouseData) {
		struct.mouseData = mouseData;
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
	 * Get a MouseLLHookStruct object via a memory MSLLHOOKSTRUCT struct point.
	 * 
	 * @param point
	 *            a memory MSLLHOOKSTRUCT struct point
	 * @return An MSLLHOOKSTRUCT object.
	 */
	public static MouseLLHookStruct valueOf(int point) {
		MSLLHOOKSTRUCT struct = new MSLLHOOKSTRUCT();
		Extension.CreateStructByPoint(point, struct);
		return new MouseLLHookStruct(struct);
	}

	/**
	 * Set java object data to memory point.
	 * 
	 * @param point
	 *            Specify memory MSLLHOOKSTRUCT struct point.
	 * @return return true if operation is successful, otherwise return false.
	 */
	public boolean saveToPoint(int point) {
		return Extension.SaveStructToPoint(struct, point);
	}

	/**
	 * Set specified MouseLLHookStruct object as this object content.
	 * 
	 * @param struct
	 *            Specified MouseLLHookStruct object will be set.
	 */
	public void setValue(MouseLLHookStruct struct) {
		if (this.struct == null)
			this.struct = struct.struct;
		else {
			this.struct.pointX = struct.struct.pointX;
			this.struct.pointY = struct.struct.pointY;
			this.struct.mouseData = struct.struct.mouseData;
			this.struct.flags = struct.struct.flags;
			this.struct.time = struct.struct.time;
			this.struct.dwExtraInfo = struct.struct.dwExtraInfo;
		}
	}
}
