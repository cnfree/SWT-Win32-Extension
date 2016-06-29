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

import org.sf.feeling.swt.win32.extension.hook.data.JournalHookData;
import org.sf.feeling.swt.win32.internal.extension.EVENTMSG;

/**
 * This class create the JournalHookData by specified information, it's just
 * convenience for user using because user don't need care about the type of
 * HookData type.
 * 
 * @author <a href="mailto:cnfree2000@hotmail.com">cnfree</a>
 * 
 */
public abstract class JournalHookInterceptor implements HookInterceptor {

	/**
	 * Create the JournalHookData, and allow user to intercept HookProc by the
	 * JournalHookData.
	 * 
	 * @see HookInterceptor#intercept(int,int,int)
	 */
	public InterceptorFlag intercept(int nCode, int wParam, int lParam) {
		JournalHookData hookData = new JournalHookData();
		hookData.setWParam(wParam);
		hookData.setLParam(lParam);
		hookData.setNCode(nCode);
		hookData.setStruct(EVENTMSG.valueOf(lParam));
		return intercept(hookData);
	}

	/**
	 * Interceptor the HookProc.
	 * 
	 * @param hookData
	 *            The information of JournalHook Data
	 * 
	 * @return Intercepted result.
	 * 
	 * @see HookInterceptor#intercept(int,int,int)
	 * 
	 */
	public abstract InterceptorFlag intercept(JournalHookData hookData);
}
