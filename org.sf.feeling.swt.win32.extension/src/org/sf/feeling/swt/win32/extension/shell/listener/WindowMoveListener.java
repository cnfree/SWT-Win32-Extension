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
package org.sf.feeling.swt.win32.extension.shell.listener;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.sf.feeling.swt.win32.internal.extension.Extension;

public class WindowMoveListener implements Listener {
	Point origin;
	private Shell shell;
	private boolean showTracker = true;

	public WindowMoveListener(Shell shell, boolean showTracker) {
		this.shell = shell;
		this.showTracker = showTracker;

	}

	public void handleEvent(Event e) {
		if (shell.getMaximized())
			return;
		if (!showTracker) {
			switch (e.type) {
			case SWT.MouseDown:
				origin = new Point(e.x, e.y);
				break;
			case SWT.MouseUp:
				origin = null;
				break;
			case SWT.MouseMove:
				if (origin != null) {
					Point p = Display.getCurrent().map(shell, null, e.x, e.y);
					shell.setLocation(p.x - origin.x, p.y - origin.y);
				}
				break;
			}
		} else {
			switch (e.type) {
			case SWT.MouseDown:
				Extension.ReleaseCapture();
				Extension.DefWindowProc(shell.handle,
						Extension.WM_NCLBUTTONDOWN, Extension.HTCAPTION, 0);
			}
		}
	}
}