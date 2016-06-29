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

package org.sf.feeling.swt.win32.extension.hook.listener;

import org.sf.feeling.swt.win32.extension.hook.data.HookData;

/**
 * The interface of Hook event listener, it's used for listening HookProc event.
 * 
 * @author <a href="mailto:cnfree2000@hotmail.com">cnfree</a>
 */
public interface HookEventListener {

	/**
	 * Accpet and deal with the give hook data.
	 * 
	 * @param data
	 *            the hook data waiting for accepting and dealing with
	 */
	public void acceptHookData(HookData data);

}
