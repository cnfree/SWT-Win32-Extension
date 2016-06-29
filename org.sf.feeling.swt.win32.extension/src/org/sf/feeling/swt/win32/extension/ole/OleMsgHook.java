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

package org.sf.feeling.swt.win32.extension.ole;

import org.eclipse.swt.SWT;
import org.eclipse.swt.internal.Callback;
import org.eclipse.swt.internal.win32.MSG;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.widgets.Display;
import org.sf.feeling.swt.win32.extension.Win32;
import org.sf.feeling.swt.win32.extension.hook.data.struct.Msg;
import org.sf.feeling.swt.win32.internal.extension.Extension;
import org.sf.feeling.swt.win32.internal.extension.util.LONG;

/**
 * An Ole Msg hook is used to hook ole control event.
 * 
 * @author <a href="mailto:cnfree2000@hotmail.com">cnfree</a>
 * 
 */
public class OleMsgHook {
	private int procHandle;

	private OleContainer container;

	private Callback oleMsgCallback;

	private int oleMsgProcAddress;

	/**
	 * Constructs a new instance of this class given its ole container
	 * 
	 * @param container
	 *            an ole container(can't be null)
	 */
	public OleMsgHook(OleContainer container) {
		this.container = container;
		HHOOK = container.getControl( ).handle + "_" + container.hashCode() + "_" + "_HHOOK";
		HHOOKMSG = container.getControl( ).handle + "_" + container.hashCode() + "_"
				+ "_HHOOK_MSG";
	}

	private final String HHOOK;

	private final String HHOOKMSG;

	/**
	 * Install the hook.
	 */
	public void installHook() {
		if (oleMsgCallback == null) {
			oleMsgCallback = new Callback(this, "OleMsgProc", 3);
			oleMsgProcAddress = oleMsgCallback.getAddress();
			if (oleMsgProcAddress == 0)
				SWT.error(SWT.ERROR_NO_MORE_CALLBACKS);
			int threadId = Extension.GetCurrentThreadId();
			procHandle = Extension.SetWindowsHookEx(Extension.WH_GETMESSAGE,
					oleMsgProcAddress, 0, threadId);
			Display.getDefault().setData(HHOOK, new LONG(procHandle));
			Display.getDefault().setData(HHOOKMSG, new MSG());
		}
	}

	/**
	 * Uninstall the hook and dispose the system garbage.
	 */
	public void unInstallHook() {
		if (oleMsgCallback != null) {
			Extension.UnhookWindowsHookEx(procHandle);
			oleMsgCallback.dispose();
			oleMsgCallback = null;
			oleMsgProcAddress = 0;
		}
	}

	int OleMsgProc(int /* long */code, int /* long */wParam,
			int /* long */lParam) {
		LONG hHook = (LONG) Display.getDefault().getData(HHOOK);
		if (hHook == null)
			return 0;
		if (code < 0) {
			return OS.CallNextHookEx(hHook.value, (int) /* 64 */code, wParam,
					lParam);
		}
		MSG msg = (MSG) Display.getDefault().getData(HHOOKMSG);
		OS.MoveMemory(msg, lParam, MSG.sizeof);
		if (!container.getControl( ).isDisposed()) {
			if (container.getHookInterceptor() != null) {
				Msg message = new Msg(msg);
				if (container.getHookInterceptor().intercept(message, code,
						wParam, lParam) == true) {
					message.setMessage(Win32.WM_NULL);
					message.setLParam(0);
					message.setWParam(0);
					message.saveToPoint(lParam);
					return 0;
				}
			}
		}
		return Extension.CallNextHookEx(hHook.value, (int) /* 64 */code,
				wParam, lParam);
	}
}
