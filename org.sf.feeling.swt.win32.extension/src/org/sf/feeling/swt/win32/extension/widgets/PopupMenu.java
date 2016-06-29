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
package org.sf.feeling.swt.win32.extension.widgets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.events.ShellListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.internal.Callback;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.sf.feeling.swt.win32.extension.Win32;
import org.sf.feeling.swt.win32.extension.widgets.theme.ThemeRender;
import org.sf.feeling.swt.win32.extension.widgets.theme.VS2005ThemeRender;
import org.sf.feeling.swt.win32.internal.extension.Extension;

public class PopupMenu extends MenuControl implements MenuHolder {

	private Control control;

	private Listener listener;

	private Callback mouseCallback;

	private int newAddress;

	private int oldAddress;

	public PopupMenu(Control control, ThemeRender theme) {
		this(control, theme, true);
	}

	public PopupMenu(Control control, ThemeRender theme, boolean autoDetect) {
		super(null, theme);
		this.holder = this;
		this.control = control;
		isPopupMenu = true;
		if (autoDetect) {
			listener = new Listener() {
				public void handleEvent(Event event) {
					if (MenuHolderManager.getActiveHolder() != null
							&& MenuHolderManager.getActiveHolder() != PopupMenu.this)
						return;
					open();
				}
			};
			control.addListener(SWT.MenuDetect, listener);
		}
		MenuHolderManager.registerHolder(this);
	}

	public PopupMenu(Control control) {
		this(control, true);
	}

	public PopupMenu(Control control, boolean autoDetect) {
		this(control, new VS2005ThemeRender(), autoDetect);
	}

	public void setMenu(CMenu menu) {
		this.menu = menu;
		MenuHolderManager.registryShortcut(this);
	}

	public void dispose() {
		hideMenu();
		if (!control.isDisposed())
			control.removeListener(SWT.MenuDetect, listener);
	}

	public void setLocation(Point location) {
		screenPos = location;
	}

	public void hideMenu() {
		super.hideMenu();
		screenPos = null;
	}

	public void open() {
		if (screenPos == null)
			screenPos = Display.getDefault().getCursorLocation();
		leftScreenPos = screenPos;
		popupRight = true;
		if (control == null || control.isDisposed())
			return;
		control.addDisposeListener(new DisposeListener() {

			public void widgetDisposed(DisposeEvent e) {
				dispose();
			}

		});
		final ShellListener shellListener = new ShellAdapter() {

			public void shellClosed(ShellEvent e) {
				hideMenu();
			}

			public void shellDeactivated(ShellEvent e) {
				hideMenu();
			}

			public void shellIconified(ShellEvent e) {
				hideMenu();
			}

		};
		control.getShell().addShellListener(shellListener);
		ignoreTaskbar = true;

		Event event = new Event();
		event.data = this;
		event.type = SWT.OPEN;
		menu.fireMenuEvent(event);

		createAndShowWindow();
		installMouseHook();
		shell.addDisposeListener(new DisposeListener() {

			public void widgetDisposed(DisposeEvent e) {
				if (!control.isDisposed())
					control.getShell().removeShellListener(shellListener);
			}

		});

		final Listener mouseDownListener = new Listener() {

			public void handleEvent(Event e) {
				if (shell == null || shell.isDisposed()) {
					Display.getDefault().removeFilter(SWT.MouseDown, this);
					return;
				}
				if (e.widget == null
						|| e.widget.isDisposed()
						|| checkMouseDownEvent(((Control) e.widget).toDisplay(
								e.x, e.y))) {
					hideMenu();
				}
			}
		};

		Display.getDefault().addFilter(SWT.MouseDown, mouseDownListener);

		final Listener keyDownListener = new Listener() {

			public void handleEvent(Event e) {
				if (shell == null || shell.isDisposed()) {
					Display.getDefault().removeFilter(SWT.KeyDown, this);
					e.doit = false;
					return;
				}
				KeyEvent ke = new KeyEvent(e);
				if ((ke.keyCode == SWT.ALT) || ke.keyCode == SWT.F10) {
					hideMenu();
					return;
				}
				getCurrentMenu().dealAltKeyEvent(ke);

				if (ke.keyCode == SWT.ARROW_RIGHT && trackItemIndex > -1)
				{
					getCurrentMenu().subMenuSelected();
				}
				if (ke.keyCode == SWT.ARROW_LEFT && trackItemIndex > -1)
				{
					getCurrentMenu().parentMenuSelected();
				}
				if (ke.keyCode == SWT.ARROW_DOWN || ke.keyCode == SWT.ARROW_UP)
				{
					if (ke.keyCode == SWT.ARROW_DOWN)
						getCurrentMenu().downSelected();
					else
						getCurrentMenu().upSelected();
				}
				if (ke.keyCode == SWT.ESC) {
					if (getCurrentMenu().parentMenu == null)
						getCurrentMenu().hideMenu();
					else
						getCurrentMenu().parentMenuSelected();
				}
				if (ke.keyCode == SWT.CR) {
					getCurrentMenu().handleSelectedEvent();
				}
			}
		};
		Display.getDefault().addFilter(SWT.KeyDown, keyDownListener);

		shell.addDisposeListener(new DisposeListener() {

			public void widgetDisposed(DisposeEvent e) {
				Display.getDefault().removeFilter(SWT.MouseDown,
						mouseDownListener);
				Display.getDefault().removeFilter(SWT.KeyDown, keyDownListener);
				getShell().removeShellListener(shellListener);
			}

		});

	}

	MenuControl getCurrentMenu() {
		if (popupMenu != null && popupMenu.trackItemIndex != -1)
			return popupMenu.getCurrentMenu();
		else
			return this;
	}

	private void installMouseHook() {
		if (mouseCallback == null) {
			mouseCallback = new Callback(this, "MouseProc", 3);
			newAddress = mouseCallback.getAddress();
			if (newAddress == 0)
				SWT.error(SWT.ERROR_NO_MORE_CALLBACKS);
			int threadId = Extension.GetCurrentThreadId();
			oldAddress = Extension.SetWindowsHookEx(Win32.WH_MOUSE, newAddress,
					0, threadId);
			shell.addDisposeListener(new DisposeListener() {

				public void widgetDisposed(DisposeEvent e) {
					if (mouseCallback != null) {
						Extension.UnhookWindowsHookEx(oldAddress);
						mouseCallback.dispose();
						mouseCallback = null;
						newAddress = 0;
					}
				}

			});
		}
	}

	int MouseProc(int /* long */code, int /* long */wParam,
			int /* long */lParam) {
		int result = Extension.CallNextHookEx(oldAddress, code, wParam, lParam);
		if (code < 0)
			return result;
		switch (wParam) {
		case Win32.WM_NCLBUTTONDOWN:
		case Win32.WM_NCRBUTTONDOWN:
			hideMenu();
			break;
		}
		return result;
	}

	public Shell getShell() {
		if (control.isDisposed())
			return null;
		if (control instanceof Shell)
			return (Shell) control;
		return control.getShell();
	}

	public boolean isAcitve() {
		return shell != null && !shell.isDisposed();
	}
}
