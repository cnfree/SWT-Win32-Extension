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

import org.sf.feeling.swt.win32.internal.extension.EVENTMSG;
import org.sf.feeling.swt.win32.internal.extension.Extension;

/**
 * An EventMsg object is an EVENTMSG struct wrapper. It provide the way to set
 * and get EVENTMSG data.
 * 
 * @author <a href="mailto:cnfree2000@hotmail.com">cnfree</a>
 * 
 */
public class EventMsg {
	private EVENTMSG msg;

	public int getMessage() {
		return msg.message;
	}

	public int getParamL() {
		return msg.paramL;
	}

	public int getParamH() {
		return msg.paramH;
	}

	public int getTime() {
		return msg.time;
	}

	public int getHwnd() {
		return msg.hwnd;
	}

	/**
	 * <b>Important</b>:It's not for user, just for wrapping struct data and
	 * providing it to user.
	 * 
	 * @param msg
	 *            EVENTMSG struct
	 */
	public EventMsg(EVENTMSG msg) {
		this.msg = msg;
	}

	public void setMessage(int message) {
		msg.message = message;
	}

	public void setParamL(int paramL) {
		msg.paramL = paramL;
	}

	public void setParamH(int paramH) {
		msg.paramH = paramH;
	}

	public void setTime(int time) {
		msg.time = time;
	}

	public void setHwnd(int hwnd) {
		msg.hwnd = hwnd;
	}

	/**
	 * Get an EvnetMsg object via a memory EVENTMSG struct point.
	 * 
	 * @param point
	 *            a memory EVENTMSG struct point
	 * @return An EvnetMsg object.
	 */
	public static EventMsg valueOf(int point) {
		EVENTMSG struct = new EVENTMSG();
		Extension.CreateStructByPoint(point, struct);
		return new EventMsg(struct);
	}

	/**
	 * Set java object data to memory point.
	 * 
	 * @param point
	 *            Specify memory EVENTMSG struct point.
	 * @return return true if operation is successful, otherwise return false.
	 */
	public boolean saveToPoint(int point) {
		return Extension.SaveStructToPoint(msg, point);
	}

	/**
	 * Set specified EventMsg object as this object content.
	 * 
	 * @param msg
	 *            Specified EventMsg object will be set.
	 */
	public void setValue(EventMsg msg) {
		if (this.msg == null)
			this.msg = msg.msg;
		else {
			this.msg.hwnd = msg.msg.hwnd;
			this.msg.message = msg.msg.message;
			this.msg.paramH = msg.msg.paramH;
			this.msg.paramL = msg.msg.paramL;
			this.msg.time = msg.msg.time;
		}
	}
}
