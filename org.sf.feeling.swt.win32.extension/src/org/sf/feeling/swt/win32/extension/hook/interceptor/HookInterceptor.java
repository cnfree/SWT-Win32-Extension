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

/**
 * The interface of Hook Intercerptor, it's used for intercepting HookProc and
 * return if intercept the HookProc.
 * 
 * @author <a href="mailto:cnfree2000@hotmail.com">cnfree</a>
 */
public interface HookInterceptor {

	/**
	 * Interceptor the HookProc.
	 * 
	 * @param nCode
	 *            HookProc nCode
	 * @param wParam
	 *            HookProc wParam
	 * @param lParam
	 *            HookProc lParam
	 * @return Intercepted result.
	 */
	public InterceptorFlag intercept(int nCode, int wParam, int lParam);

}
