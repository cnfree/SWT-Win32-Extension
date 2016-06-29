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
import org.sf.feeling.swt.win32.extension.hook.data.struct.MouseHookStruct;
import org.sf.feeling.swt.win32.internal.extension.MOUSEHOOKSTRUCT;

/**
 * {@link Win32#WH_MOUSE} Hook Data. Be used for wrapping
 * {@link Win32#WH_MOUSE} HookProc information.
 * 
 * @author <a href="mailto:cnfree2000@hotmail.com">cnfree</a>
 */
public class MouseHookData extends HookData {

	private static final long serialVersionUID = -5852911981921525661L;
	private MOUSEHOOKSTRUCT struct;

	/**
	 * <b>Important</b>: This method is used by the native library to create a
	 * hook data. Not for user.
	 * 
	 * @param struct
	 *            MOUSEHOOKSTRUCT struct
	 */
	public void setStruct(MOUSEHOOKSTRUCT struct) {
		this.struct = struct;
	}

	/**
	 * Specifies the x-coordinates of the cursor, in screen coordinates.
	 * 
	 * @return MOUSEHOOKSTRUCT member pt.x
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
	 * @return MOUSEHOOKSTRUCT member pt.y
	 */
	public int getPointY() {
		if (struct != null)
			return struct.pointY;
		else
			return 0;
	}

	/**
	 * Specifies the hit-test value.
	 * 
	 * @return MOUSEHOOKSTRUCT member wHitTestCode
	 */
	public int getHitTestCode() {
		if (struct != null)
			return struct.wHitTestCode;
		else
			return 0;
	}

	/**
	 * Specifies extra information associated with the message.
	 * 
	 * @return MOUSEHOOKSTRUCT member dwExtraInfo
	 */
	public int getExtraInfo() {
		if (struct != null)
			return struct.dwExtraInfo;
		else
			return 0;
	}

	/**
	 * Get MOUSEHOOKSTRUCT struct from HookProc.
	 * 
	 * @return MOUSEHOOKSTRUCT struct
	 */
	public MouseHookStruct getStruct() {
		return new MouseHookStruct(struct);
	}
}
