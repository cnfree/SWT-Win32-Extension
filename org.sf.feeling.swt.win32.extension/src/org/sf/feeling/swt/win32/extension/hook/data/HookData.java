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

/**
 * Non-Low Level Hook Data. Be used for wrapping HookProc information.
 * 
 * @author <a href="mailto:cnfree2000@hotmail.com">cnfree</a>
 */
public class HookData implements java.io.Serializable {
	private static final long serialVersionUID = 3012103039977068200L;
	private int lParam;
	private int wParam;
	private int nCode;

	/**
	 * Get HookProc lparam.
	 * 
	 * @return HookProc lparam.
	 */
	public int getLParam() {
		return lParam;
	}

	/**
	 * Get HookProc wparam.
	 * 
	 * @return HookProc wparam.
	 */
	public int getWParam() {
		return wParam;
	}

	/**
	 * <b>Important:</b> This method is used by the native library to create a hook
	 *             data. Not for user.
	 * @param param
	 *            HookProc lParam
	 */
	public void setLParam(int param) {
		lParam = param;
	}

	/**
	 * <b>Important:</b> This method is used by the native library to create a hook
	 *             data. Not for user.
	 * @param param
	 *            HookProc wParam
	 */
	public void setWParam(int param) {
		wParam = param;
	}

	/**
	 * Get HookProc nCode.
	 * 
	 * @return HookProc nCode.
	 */
	public int getNCode() {
		return nCode;
	}

	/**
	 * <b>Important:</b> This method is used by the native library to create a hook
	 *             data. Not for user.
	 * @param code
	 *            HookProc nCode
	 */
	public void setNCode(int code) {
		nCode = code;
	}
}
