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

import org.eclipse.swt.internal.win32.MSG;
import org.sf.feeling.swt.win32.extension.hook.data.struct.Msg;

public class MessageHookData extends HookData
{
	private static final long serialVersionUID = -8107270291651986557L;

	private MSG struct;

	/**
	 * <b>Important</b>: This method is used by the native library to create a
	 * hook data. Not for user.
	 * 
	 * @param struct
	 *            MSG struct
	 */
	public void setStruct(MSG struct)
	{
		this.struct = struct;
	}

	public int getMessage()
	{
		return struct.message;
	}

	public int getX()
	{
		return struct.x;
	}

	public int getY()
	{
		return struct.y;
	}

	public int getTime()
	{
		return struct.time;
	}

	public int getMessageHwnd()
	{
		return struct.hwnd;
	}

	public int getMessageParamL()
	{
		return struct.lParam;
	}

	public int getMessageParamW()
	{
		return struct.wParam;
	}

	/**
	 * Get MSG struct from HookProc.
	 * 
	 * @return MSG struct
	 */
	public Msg getStruct()
	{
		return new Msg(struct);
	}
}
