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
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

class ClientAreaCanvas extends Canvas {

	private ISkinable drawable;

	public ClientAreaCanvas(Composite parent) {
		super(parent, SWT.NONE);

		setBackgroundMode(SWT.INHERIT_DEFAULT);

		createLayoutData();

		addListener(SWT.Resize, new Listener() {
			public void handleEvent(Event event) {
				updateSkin(event);
			}
		});
	}

	public ISkinable getDrawable() {
		return drawable;
	}

	public void setDrawable(ISkinable border) {

		GridData layoutData = (GridData) getLayoutData();
		layoutData.widthHint = border.getInitWidth();
		layoutData.heightHint = border.getInitHeight();
		setLayoutData(layoutData);

		this.drawable = border;
	}

	public void setHeight(int height) {

		GridData layoutData = (GridData) getLayoutData();
		layoutData.heightHint = height;
		setLayoutData(layoutData);
	}

	public void setWidth(int width) {

		GridData layoutData = (GridData) getLayoutData();
		layoutData.widthHint = width;
		setLayoutData(layoutData);
	}

	private void createLayoutData() {
		GridData layoutData = new GridData(GridData.FILL, GridData.FILL, true,
				true);
		setLayoutData(layoutData);
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
}