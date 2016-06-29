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

import org.sf.feeling.swt.win32.extension.hook.data.Mouse_LLHookData;
import org.sf.feeling.swt.win32.internal.extension.MSLLHOOKSTRUCT;

/**
 * This class create the Mouse_LLHookData by specified information, it's just
 * convenience for user using because user don't need care about the type of
 * HookData type.
 * 
 * @author <a href="mailto:cnfree2000@hotmail.com">cnfree</a>
 * 
 */
public abstract class Mouse_LLHookInterceptor implements HookInterceptor {

	/**
	 * Create the Mouse_LLHookData, and allow user to intercept HookProc by the
	 * Mouse_LLHookData.
	 * 
	 * @see HookInterceptor#intercept(int,int,int)
	 */
	public InterceptorFlag intercept(int nCode, int wParam, int lParam) {
		Mouse_LLHookData hookData = new Mouse_LLHookData();
		hookData.setWParam(wParam);
		hookData.setLParam(lParam);
		hookData.setNCode(nCode);
		hookData.setStruct(MSLLHOOKSTRUCT.valueOf(lParam));
		return intercept(hookData);
	}

	/**
	 * Interceptor the HookProc.
	 * 
	 * @param hookData
	 *            The information of Mouse_LLHook Data
	 * 
	 * @return Intercepted result.
	 * 
	 * @see HookInterceptor#intercept(int,int,int)
	 */
	public abstract InterceptorFlag intercept(Mouse_LLHookData hookData);
}
