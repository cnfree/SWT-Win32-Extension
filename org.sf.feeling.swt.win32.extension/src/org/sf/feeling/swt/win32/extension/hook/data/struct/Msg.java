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

import org.eclipse.swt.internal.win32.MSG;
import org.eclipse.swt.internal.win32.OS;
import org.sf.feeling.swt.win32.internal.extension.Extension;

/**
 * A Msg object is a MSG struct wrapper. It provide the way to set and get MSG
 * data.
 * 
 * @author <a href="mailto:cnfree2000@hotmail.com">cnfree</a>
 * 
 */
public class Msg {
	private MSG msg;

	public int getMessage() {
		return msg.message;
	}

	public int getLParam() {
		return msg.lParam;
	}

	public int getWParam() {
		return msg.wParam;
	}

	public int getTime() {
		return msg.time;
	}

	public int getHwnd() {
		return msg.hwnd;
	}

	public int getX() {
		return msg.x;
	}

	public int getY() {
		return msg.y;
	}

	/**
	 * <b>Important</b>:It's not for user, just for wrapping struct data and
	 * providing it to user.
	 * 
	 * @param msg
	 *            MSG struct
	 */
	public Msg(MSG msg) {
		this.msg = msg;
	}

	public void setMessage(int message) {
		msg.message = message;
	}

	public void setLParam(int lParam) {
		msg.lParam = lParam;
	}

	public void setWParam(int wParam) {
		msg.wParam = wParam;
	}

	public void setTime(int time) {
		msg.time = time;
	}

	public void setHwnd(int hwnd) {
		msg.hwnd = hwnd;
	}

	public void setX(int x) {
		msg.x = x;
	}

	public void setY(int y) {
		msg.y = y;
	}

	/**
	 * Get a Msg object via a memory MSG struct
	 * point.
	 * 
	 * @param point
	 *            a memory MSG struct point
	 * @return An MSG object.
	 */
	public static Msg valueOf(int point) {
		MSG msg = new MSG();
		Extension.MoveMemory(msg, point, MSG.sizeof);
		return new Msg(msg);
	}

	/**
	 * Set java object data to memory point.
	 * 
	 * @param point
	 *            Specify memory MSG struct point.
	 */
	public void saveToPoint(int point) {
		OS.MoveMemory(point, msg, MSG.sizeof);
	}

	/**
	 * Set specified Msg object as this object content.
	 * 
	 * @param msg
	 *            Specified Msg object will be set.
	 */
	public void setValue(Msg msg) {
		if (this.msg == null)
			this.msg = msg.msg;
		else {
			this.msg.hwnd = msg.msg.hwnd;
			this.msg.message = msg.msg.message;
			this.msg.wParam = msg.msg.wParam;
			this.msg.lParam = msg.msg.lParam;
			this.msg.time = msg.msg.time;
			this.msg.x = msg.msg.x;
			this.msg.y = msg.msg.y;
		}
	}
}
