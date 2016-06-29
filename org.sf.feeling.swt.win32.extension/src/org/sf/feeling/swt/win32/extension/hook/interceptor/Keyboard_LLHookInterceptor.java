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
package org.sf.feeling.swt.win32.extension.hook.interceptor;

import org.sf.feeling.swt.win32.extension.hook.data.Keyboard_LLHookData;
import org.sf.feeling.swt.win32.internal.extension.KBDLLHOOKSTRUCT;

/**
 * This class create the Keyboard_LLHookData by specified information, it's just
 * convenience for user using because user don't need care about the type of
 * HookData type.
 * 
 * @author <a href="mailto:cnfree2000@hotmail.com">cnfree</a>
 * 
 */
public abstract class Keyboard_LLHookInterceptor implements HookInterceptor {

	/**
	 * Create the Keyboard_LLHookData, and allow user to intercept HookProc by
	 * the Keyboard_LLHookData.
	 * 
	 * @see HookInterceptor#intercept(int,int,int)
	 */
	public InterceptorFlag intercept(int nCode, int wParam, int lParam) {
		Keyboard_LLHookData hookData = new Keyboard_LLHookData();
		hookData.setWParam(wParam);
		hookData.setLParam(lParam);
		hookData.setNCode(nCode);
		hookData.setStruct(KBDLLHOOKSTRUCT.valueOf(lParam));
		return intercept(hookData);
	}

	/**
	 * Interceptor the HookProc.
	 * 
	 * @param hookData
	 *            The information of Keyboard_LLHook Data
	 * 
	 * @return Intercepted result.
	 * 
	 * @see HookInterceptor#intercept(int,int,int)
	 */
	public abstract InterceptorFlag intercept(Keyboard_LLHookData hookData);
}
