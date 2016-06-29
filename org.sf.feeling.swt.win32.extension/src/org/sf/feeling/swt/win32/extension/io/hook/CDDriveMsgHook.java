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

package org.sf.feeling.swt.win32.extension.io.hook;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.internal.Callback;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Shell;
import org.sf.feeling.swt.win32.extension.Win32;
import org.sf.feeling.swt.win32.extension.io.CDDrive;
import org.sf.feeling.swt.win32.extension.io.listener.CDDriveChangeListener;
import org.sf.feeling.swt.win32.internal.extension.Extension;
import org.sf.feeling.swt.win32.internal.extension.callback.WNDCallback;
import org.sf.feeling.swt.win32.internal.extension.callback.WNDCallbackManager;

/**
 * Instances of this class represent the "Hook" which listen the message about
 * CD Drive changed.</b>
 * 
 * <b>Important</b>: Vista doesn't support it.
 * 
 * @author <a href="mailto:cnfree2000@hotmail.com">cnfree</a>
 * 
 */
public class CDDriveMsgHook {
	private static final int WM_DEVICECHANGE = 0x219;

	private int oldAddress;

	private Shell shell;

	private Callback windowMsgCallback;;

	private int newAddress;

	private WNDCallback callback;

	/**
	 * Constructs a new instance of this class.
	 * 
	 * @param shell
	 *            the shell to listen the window message.
	 */
	public CDDriveMsgHook(Shell shell) {
		this.shell = shell;
	}

	/**
	 * Install the msg hook and start listening the window message.
	 */
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

	/**
	 * Uninstall the msg hook and stop listening the window message.
	 */
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
		if (msg == WM_DEVICECHANGE) {
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

	/**
	 * Adds the listener to the collection of listeners who will be notified
	 * when operations are performed on the receiver, by sending the listener
	 * one of the messages defined in the <code>CDDriveChangeListener</code>
	 * interface.
	 * 
	 * @param listener
	 *            the listener which should be notified
	 * 
	 * @see CDDriveChangeListener
	 * @see #removeChangeListener
	 */
	public void addChangeListener(CDDriveChangeListener listener) {
		if (listener == null)
			return;
		if (listeners == null)
			listeners = new ArrayList();
		if (!listeners.contains(listener))
			listeners.add(listener);
	}

	/**
	 * Removes the listener from the collection of listeners who will be
	 * notified when operations are performed on the receiver.
	 * 
	 * @param listener
	 *            the listener which should no longer be notified
	 * 
	 * 
	 * @see CDDriveChangeListener
	 * @see #addChangeListener
	 */
	public void removeChangeListener(CDDriveChangeListener listener) {
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
				final CDDriveChangeListener listener = (CDDriveChangeListener) listeners
						.get(i);
				Display.getDefault().asyncExec(new Runnable() {
					public void run() {
						if (event.type == CDDrive.DEVICE_LOADED)
							listener.driveLoaded(event);
						else if (event.type == CDDrive.DEVICE_EJECTED)
							listener.driveEjected(event);
					}
				});
			}
		}
	}
}
