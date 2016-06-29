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

package org.sf.feeling.swt.win32.extension.shell;

import org.eclipse.swt.internal.Callback;
import org.eclipse.swt.widgets.Event;
import org.sf.feeling.swt.win32.extension.Win32;
import org.sf.feeling.swt.win32.internal.extension.Extension;
import org.sf.feeling.swt.win32.internal.extension.callback.WNDCallback;
import org.sf.feeling.swt.win32.internal.extension.callback.WNDCallbackManager;

class SystemMenuMsgHook
{
	private int oldAddress;

	private SystemMenuManager manager;

	private Callback windowMsgCallback;;

	private int newAddress;

	private WNDCallback callback;

	public SystemMenuMsgHook(SystemMenuManager manager)
	{
		this.manager = manager;
	}

	public void installHook()
	{
		if (callback == null)
		{
			windowMsgCallback = new Callback(this, "WindowMsgProc", 4);
			newAddress = windowMsgCallback.getAddress();
			oldAddress = Extension.SetWindowLong(manager.getShell().handle, Win32.GWL_WNDPROC,
					newAddress);
			callback = new WNDCallback(manager.getShell(), oldAddress, newAddress)
			{
				public void dispose()
				{
					disposeCallback();
				}
			};
			WNDCallbackManager.addCallback(manager.getShell(), callback);
		}
	}

	public void unInstallHook()
	{
		if (callback != null) WNDCallbackManager.disposeCallback(manager.getShell(), callback);
	}

	private void disposeCallback()
	{
		if (callback != null)
		{
			Extension.SetWindowLong(manager.getShell().handle, Win32.GWL_WNDPROC, callback
					.getOldAddress());
			windowMsgCallback.dispose();
			windowMsgCallback = null;
			newAddress = 0;
			callback = null;
		}
	}

	int WindowMsgProc(int handle, int msg, int wParam, int lParam)
	{
		if (msg == Win32.WM_SYSCOMMAND)
		{
			SystemMenuItem item = manager.getItem(wParam);
			if (item != null)
			{
				Event event = new Event();
				event.type = wParam;
				event.detail = lParam;
				event.widget = manager.getShell();
				event.time = Extension.GetTickCount();
				item.fireSelectedEvent(event);
			}
		}
		return Extension.CallWindowProc(oldAddress, handle, msg, wParam, lParam);
	}
}
