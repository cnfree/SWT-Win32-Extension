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
import org.sf.feeling.swt.win32.extension.hook.data.MouseHookData;

public abstract class MouseHookEventListener implements HookEventListener {

	public void acceptHookData(HookData data) {
		if (data instanceof MouseHookData) {
			MouseHookData hookData = (MouseHookData) data;
			acceptMouseHookData(hookData);
		}
	}

	public abstract void acceptMouseHookData(MouseHookData data);
}
