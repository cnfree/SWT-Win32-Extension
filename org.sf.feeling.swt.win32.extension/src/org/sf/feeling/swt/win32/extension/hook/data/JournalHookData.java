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
import org.sf.feeling.swt.win32.extension.hook.data.struct.EventMsg;
import org.sf.feeling.swt.win32.internal.extension.EVENTMSG;

/**
 * {@link Win32#WH_JOURNALPLAYBACK} Hook Data. Be used for wrapping
 * {@link Win32#WH_JOURNALPLAYBACK} HookProc information.
 * 
 * @author <a href="mailto:cnfree2000@hotmail.com">cnfree</a>
 */
public class JournalHookData extends HookData {

	private static final long serialVersionUID = -2935728631946658042L;
	private EVENTMSG struct;

	/**
	 * <b>Important:</b> This method is used by the native library to create a
	 * hook data. Not for user.
	 * 
	 * @param struct
	 *            EVENTMSG struct
	 */
	public void setStruct(EVENTMSG struct) {
		this.struct = struct;
	}

	/**
	 * Specifies the message.
	 * 
	 * @return EVENTMSG member message.
	 */
	public int getMessage() {
		if (struct != null)
			return struct.message;
		else
			return 0;
	}

	/**
	 * Specifies additional information about the message. The exact meaning
	 * depends on the message value.
	 * 
	 * @return EVENTMSG member paramL.
	 */
	public int getParamL() {
		if (struct != null)
			return struct.paramL;
		else
			return 0;
	}

	/**
	 * Specifies additional information about the message. The exact meaning
	 * depends on the message value.
	 * 
	 * @return EVENTMSG member paramH.
	 */
	public int getParamH() {
		if (struct != null)
			return struct.paramH;
		else
			return 0;
	}

	/**
	 * Specifies the time at which the message was posted.
	 * 
	 * @return EVENTMSG member time.
	 */
	public int getTime() {
		if (struct != null)
			return struct.time;
		else
			return 0;
	}

	/**
	 * Handle to the window to which the message was posted.
	 * 
	 * @return EVENTMSG member hwnd.
	 */
	public int getHwnd() {
		if (struct != null)
			return struct.hwnd;
		else
			return 0;
	}

	/**
	 * Get EVENTMSG struct from HookProc.
	 * 
	 * @return EVENTMSG struct
	 */
	public EventMsg getStruct() {
		return new EventMsg(struct);
	}
}
