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

package org.sf.feeling.swt.win32.extension.sound.hook;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.internal.Callback;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.sf.feeling.swt.win32.extension.Win32;
import org.sf.feeling.swt.win32.internal.extension.Extension;
import org.sf.feeling.swt.win32.internal.extension.callback.WNDCallback;
import org.sf.feeling.swt.win32.internal.extension.callback.WNDCallbackManager;

/**
 * <b>Important</b>: Vista doesn't support it.
 * 
 * @author <a href="mailto:cnfree2000@hotmail.com">cnfree</a>
 * 
 */
public class MixerMsgHook {
	private static final int MM_MIXM_CONTROL_CHANGE = 0x3D1;

	private int oldAddress;

	private Shell shell;

	private Callback windowMsgCallback;;

	private int newAddress;

	private WNDCallback callback;

	public MixerMsgHook(Shell shell) {
		this.shell = shell;
	}

	public void installHook() {
		if (callback == null) {
			windowMsgCallback = new Callback(this, "WindowMsgProc", 4);
			newAddress = windowMsgCallback.getAddress();
			oldAddress = Extension.SetWindowLong(shell.handle,
					Win32.GWL_WNDPROC, newAddress);
			callback = new WNDCallback(shell, oldAddress, newAddress) {
				public void dispose() {
					disposeCallback();
				}
			};
			WNDCallbackManager.addCallback(shell, callback);
			shell.addDisposeListener(new DisposeListener() {
				public void widgetDisposed(DisposeEvent e) {
					unInstallHook();
				}
			});
		}
	}

	public void unInstallHook() {
		if (callback != null)
			WNDCallbackManager.disposeCallback(shell, callback);
	}

	private void disposeCallback() {
		if (callback != null) {
			Extension.SetWindowLong(shell.handle, Win32.GWL_WNDPROC, callback
					.getOldAddress());
			windowMsgCallback.dispose();
			windowMsgCallback = null;
			newAddress = 0;
			callback = null;
		}
	}

	int WindowMsgProc(int handle, int msg, int wParam, int lParam) {
		if (msg == MM_MIXM_CONTROL_CHANGE) {
			Event event = new Event();
			event.type = wParam;
			event.detail = lParam;
			event.widget = shell;
			event.time = Extension.GetTickCount();
			fireChangedEvent(event);
		}
		return Extension
				.CallWindowProc(oldAddress, handle, msg, wParam, lParam);
	}

	private List listeners;

	public void addChangeListener(Listener listener) {
		if (listener == null)
			return;
		if (listeners == null)
			listeners = new ArrayList();
		if (!listeners.contains(listener))
			listeners.add(listener);
	}

	public void removeChangeListener(Listener listener) {
		if (listener == null)
			return;
		if (listeners != null) {
			listeners.remove(listener);
			if (listeners.size() == 0)
				listeners = null;
		}
	}

	void fireChangedEvent(final Event event) {
		if (listeners != null) {
			for (int i = 0; i < listeners.size(); i++) {
				final Listener listener = (Listener) listeners.get(i);
				Display.getDefault().asyncExec(new Runnable() {
					public void run() {
						listener.handleEvent(event);
					}
				});
			}
		}
	}
}
