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

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

public class ButtonCanvas extends Canvas {

	private int type;

	private ISkinable drawable;

	private boolean active;

	public ButtonCanvas(Composite parent, int type) {
		super(parent, SWT.NONE);

		this.type = type;

		setBackgroundMode(SWT.INHERIT_DEFAULT);

		createLayoutData(type);

		InternalListener listener = new InternalListener();
		addListener(SWT.Deactivate, listener);
		addListener(SWT.MouseEnter, listener);
		addListener(SWT.MouseExit, listener);
		addListener(SWT.MouseDown, listener);
		addListener(SWT.MouseUp, listener);
		/**
		 * Remove key listener, because the traverse listener depends the key
		 * listeners.
		 */
		// addListener(SWT.KeyDown, listener);
		// addListener(SWT.KeyUp, listener);
		addListener(SWT.Resize, listener);
		addPaintListener(new PaintListener() {

			public void paintControl(PaintEvent e) {
				if (drawable != null) {
					drawable.paint(e.gc, 0, 0);
				}
			}

		});
	}

	public boolean getActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public int getType() {
		return type;
	}

	public ISkinable getDrawable() {
		return drawable;
	}

	public void setDrawable(ISkinable drawable) {

		GridData layoutData = (GridData) getLayoutData();
		if (drawable != null) {
			layoutData.widthHint = drawable.getInitWidth();
			layoutData.heightHint = drawable.getInitHeight();
		} else {
			layoutData.widthHint = 0;
			layoutData.heightHint = 0;
		}
		setLayoutData(layoutData);
		this.drawable = drawable;
	}

	public void updateSkin(Event event) {

		if (drawable != null) {

			Color foreground = drawable.getForeground(event);
			if (foreground != null && !foreground.isDisposed()) {
				setForeground(foreground);
			}
			drawable.configue(event);
			redraw();
		}
	}

	private void createLayoutData(int border) {
		GridData layoutData = new GridData(GridData.FILL, GridData.FILL, false,
				false);
		setLayoutData(layoutData);
	}

	private class InternalListener implements Listener {
		public void handleEvent(Event event) {
			if (event.type == SWT.MouseDown && event.button != 1)
				return;
			updateSkin(event);
		}
	}
}
