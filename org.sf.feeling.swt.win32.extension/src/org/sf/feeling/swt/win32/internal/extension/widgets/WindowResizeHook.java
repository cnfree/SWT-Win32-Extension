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
package org.sf.feeling.swt.win32.internal.extension.widgets;

import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.internal.Callback;
import org.eclipse.swt.internal.win32.MINMAXINFO;
import org.eclipse.swt.internal.win32.OS;
import org.sf.feeling.swt.win32.extension.Win32;
import org.sf.feeling.swt.win32.extension.widgets.Window;
import org.sf.feeling.swt.win32.internal.extension.Extension;
import org.sf.feeling.swt.win32.internal.extension.Extension2;
import org.sf.feeling.swt.win32.internal.extension.callback.WNDCallback;
import org.sf.feeling.swt.win32.internal.extension.callback.WNDCallbackManager;

public class WindowResizeHook
{
	private int oldAddress;

	private Window window;

	private Callback windowResizeCallback;

	private int newAddress;

	private WNDCallback callback;

	public WindowResizeHook(Window window)
	{
		this.window = window;
		window.getShell().addDisposeListener(new DisposeListener()
		{

			public void widgetDisposed(DisposeEvent e)
			{
				unInstallHook();
			}
		});
	}

	public void installHook()
	{
		if (callback == null)
		{
			windowResizeCallback = new Callback(this, "WindowResizeProc", 4);
			newAddress = windowResizeCallback.getAddress();
			oldAddress = Extension2.SetWindowLongPtr(window.getShell().handle,
					Win32.GWL_WNDPROC, newAddress);
			callback = new WNDCallback(window.getShell(), oldAddress, newAddress)
			{
				public void dispose()
				{
					disposeCallback();
				}
			};
			WNDCallbackManager.addCallback(window.getShell(), callback);
		}
	}

	public void unInstallHook()
	{
		if (callback != null) WNDCallbackManager.disposeCallback(window.getShell(), callback);
	}

	private void disposeCallback()
	{
		if (callback != null)
		{
			Extension2.SetWindowLongPtr(window.getShell().handle, Win32.GWL_WNDPROC, callback
					.getOldAddress());
			windowResizeCallback.dispose();
			windowResizeCallback = null;
			newAddress = 0;
			callback = null;
		}
	}

	int WindowResizeProc(int handle, int msg, int wParam, int lParam)
	{
		if (msg == OS.WM_GETMINMAXINFO && window.isThemeInstalled())
		{
			Rectangle bound = window.getShell().getDisplay().getPrimaryMonitor().getBounds();
			MINMAXINFO info = new MINMAXINFO();
			Extension.MoveMemory(info, lParam, MINMAXINFO.sizeof);
			if (info.ptMaxSize_x > 0 && info.ptMaxSize_y > 0)
			{
				Rectangle rect = org.sf.feeling.swt.win32.extension.shell.Windows
						.getWindowRect(org.sf.feeling.swt.win32.extension.shell.Windows
								.getSystemTray());
				if (rect.x <= 0)
				{
					if (rect.y <= 0)
					{
						if (rect.width >= bound.width)
						{
							// up taskbar
							info.ptMaxPosition_y = rect.y + rect.height;
							info.ptMaxSize_y = bound.height - info.ptMaxPosition_y;
							info.ptMaxPosition_x = 0;
							info.ptMaxSize_x = bound.width;
						}
						else
						{
							// left taskbar
							info.ptMaxPosition_x = rect.width + rect.x;
							info.ptMaxSize_x = bound.width - (rect.width + rect.x);
							info.ptMaxPosition_y = 0;
							info.ptMaxSize_y = bound.height;
						}
					}
					else
					{
						// down taskbar
						info.ptMaxPosition_x = 0;
						info.ptMaxSize_x = bound.width;
						info.ptMaxPosition_y = 0;
						info.ptMaxSize_y = rect.y;
					}
				}
				else if (rect.x > 0)
				{
					// right taskbar
					info.ptMaxPosition_x = 0;
					info.ptMaxSize_x = rect.x;
					info.ptMaxPosition_y = 0;
					info.ptMaxSize_y = bound.height;
				}

				Extension.MoveMemory(lParam, info, MINMAXINFO.sizeof);
			}
		}
		if (msg == Win32.WM_CHANGEUISTATE && window.isThemeInstalled()
				&& window.getShell().getMaximized())
		{
			window.getShell().setMaximized(true);
		}

		return Extension.CallWindowProc(oldAddress, handle, msg, wParam, lParam);
	}
}
