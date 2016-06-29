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

import org.sf.feeling.swt.win32.extension.hook.data.struct.Msg;

/**
 * An OleHookInterception is used to intercept ole control event.
 * 
 * @author <a href="mailto:cnfree2000@hotmail.com">cnfree</a>
 * 
 */
public interface OleHookInterceptor {
	/**
	 * Listen the ole control event.
	 * 
	 * @param message
	 *            hook message data
	 * @param code
	 *            hook code data
	 * @param wParam
	 *            hook wParam data
	 * @param lParam
	 *            hook lParam data
	 * @return return true if eat the message, else return false
	 */
	boolean intercept(Msg message, int code, int wParam, int lParam);
}
