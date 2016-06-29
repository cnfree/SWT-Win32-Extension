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
package org.sf.feeling.swt.win32.extension.hook;

import org.eclipse.swt.internal.Callback;
import org.sf.feeling.swt.win32.extension.Win32;
import org.sf.feeling.swt.win32.extension.hook.interceptor.HookInterceptor;
import org.sf.feeling.swt.win32.extension.hook.interceptor.InterceptorFlag;
import org.sf.feeling.swt.win32.internal.extension.Extension;

/**
 * This class enables to install {@link Win32#WH_KEYBOARD_LL} Hook, and retrieve
 * information and deal with the hook from them using {@link HookInterceptor}.
 * 
 * @author <a href="mailto:cnfree2000@hotmail.com">cnfree</a>
 */
public final class Keyboard_LLHook {

	private static int hKeyboardHook = 0;
	private static boolean isInstall = false;

	static int Keyboard_LLHookProc(int nCode, int wParam, int lParam) {
		if (interceptor != null) {
			InterceptorFlag flag = interceptor.intercept(nCode, wParam, lParam);
			if (InterceptorFlag.FALSE.equals(flag))
				return -1;
			else if (flag.isCustom())
				return flag.getValue();
		}
		return Extension.CallNextHookEx(hKeyboardHook, nCode, wParam, lParam);
	}

	private static HookInterceptor interceptor;
	private static Callback callback;
	private static int address;

	/**
	 * Adds a hook event interceptor.
	 * 
	 * @param interceptor
	 *            a hook event interceptor.
	 */
	public static void addHookInterceptor(HookInterceptor interceptor) {
		Keyboard_LLHook.interceptor = interceptor;
	}

	private Keyboard_LLHook() {
	};

	/**
	 * Install the {@link Win32#WH_KEYBOARD_LL} hook. Returns <code>true</code> if
	 * the hook is installed, and false otherwise.
	 * 
	 * @return the hook installed state
	 */
	public static boolean installHook() {
		if (isInstalled())
			throw new IllegalStateException("Hook is already instaled.");
		callback = new Callback(Keyboard_LLHook.class, "Keyboard_LLHookProc", 3);
		address = callback.getAddress();
		hKeyboardHook = Extension.SetWindowsHookEx(Win32.WH_KEYBOARD_LL,
				address, Extension.GetModuleHandle(null), 0);
		isInstall = hKeyboardHook != 0;
		return isInstall;
	}

	/**
	 * Uninstall the {@link Win32#WH_KEYBOARD_LL} hook. Returns <code>true</code>
	 * if the hook is uninstalled, and false otherwise.
	 * 
	 * @return the hook uninstalled state
	 */
	public static boolean unInstallHook() {
		if (!isInstalled())
			throw new IllegalStateException("Hook is not installed.");
		if (isInstall) {
			isInstall = !Extension.UnhookWindowsHookEx(hKeyboardHook);
			callback.dispose();
			callback = null;
			address = 0;
		}
		return !isInstall;
	}

	/**
	 * Returns <code>true</code> if the hook is installed, and false
	 * otherwise.
	 * 
	 * @return the hook state
	 */
	public static boolean isInstalled() {
		return isInstall;
	}
}
