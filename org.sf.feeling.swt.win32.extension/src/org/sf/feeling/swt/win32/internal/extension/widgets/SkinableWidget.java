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

import java.util.HashMap;
import java.util.Iterator;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.sf.feeling.swt.win32.extension.widgets.MenuBar;
import org.sf.feeling.swt.win32.extension.widgets.Separator;

public abstract class SkinableWidget implements IContainer {

	private Composite composite;

	private ClientAreaCanvas clientArea;

	private HashMap borders;

	protected MenuBar menubar;

	protected Separator separator;

	public SkinableWidget(Composite parent, int style) {
		createWidget(parent, style);
	}

	protected SkinableWidget() {

	}

	public ISkinable getSkin(int type) {
		return ((BorderCanvas) borders.get(new Integer(type))).getDrawable();
	}

	public void setSkin(int type, ISkinable drawable) {
		((BorderCanvas) borders.get(new Integer(type))).setDrawable(drawable);
		Event event = new Event();
		event.type = SWT.Paint;
		((BorderCanvas) borders.get(new Integer(type))).updateSkin(event);
	}

	public ISkinable getSkin() {
		return clientArea.getDrawable();
	}

	public void setSkin(ISkinable drawable) {
		clientArea.setDrawable(drawable);
	}

	protected abstract Composite createContents(Composite parent, int style);

	protected Canvas getBorder(int type) {
		return (Canvas) borders.get(new Integer(type));
	}

	protected Composite getClientArea() {
		return clientArea;
	}

	protected void createWidget(Composite parent, int style) {
		composite = createContents(parent, style);

		if (composite == null) {
			throw new IllegalArgumentException(
					"Can not create contents, null value found.");
		}

		createLayout(composite);

		clientArea = createBordersAndCilentArea(composite);

		if (clientArea == null) {
			throw new IllegalArgumentException(
					"Can not create client area, null value found.");
		}

		applyEvents(composite);
	}

	private void createLayout(Composite parent) {
		GridLayout layout = new GridLayout();
		layout.numColumns = 3;
		layout.horizontalSpacing = 0;
		layout.verticalSpacing = 0;
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		parent.setLayout(layout);
		parent.setBackgroundMode(SWT.INHERIT_DEFAULT);
	}

	private ClientAreaCanvas createBordersAndCilentArea(Composite parent) {
		borders = new HashMap(8);
		Composite northBorderContainer = new Composite(parent, SWT.NONE);

		borders.put(new Integer(IContainer.BORDER_NW), new BorderCanvas(
				northBorderContainer, IContainer.BORDER_NW));
		borders.put(new Integer(IContainer.BORDER_N), new BorderCanvas(
				northBorderContainer, IContainer.BORDER_N));
		borders.put(new Integer(IContainer.BORDER_NE), new BorderCanvas(
				northBorderContainer, IContainer.BORDER_NE));
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 3;
		northBorderContainer.setLayoutData(gd);
		GridLayout layout = new GridLayout();
		layout.numColumns = 3;
		layout.horizontalSpacing = 0;
		layout.verticalSpacing = 0;
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		northBorderContainer.setLayout(layout);
		northBorderContainer.setBackgroundMode(SWT.INHERIT_DEFAULT);

		borders.put(new Integer(IContainer.BORDER_W), new BorderCanvas(parent,
				IContainer.BORDER_W));

		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayoutData(new GridData(GridData.FILL, GridData.FILL,
				true, true));
		layout = new GridLayout();
		layout.numColumns = 1;
		layout.horizontalSpacing = 0;
		layout.verticalSpacing = 0;
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		composite.setLayout(layout);
		composite.setBackgroundMode(SWT.INHERIT_DEFAULT);

		menubar = new MenuBar(composite, SWT.NONE);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.exclude = true;
		menubar.getControl().setLayoutData(gd);

		separator = new Separator(composite, SWT.HORIZONTAL | SWT.SHADOW_IN);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.exclude = true;
		separator.setLayoutData(gd);

		ClientAreaCanvas clientArea = new ClientAreaCanvas(composite);
		clientArea.setLayoutData(new GridData(GridData.FILL, GridData.FILL,
				true, true));

		borders.put(new Integer(IContainer.BORDER_E), new BorderCanvas(parent,
				IContainer.BORDER_E));

		Composite sourthBorderContainer = new Composite(parent, SWT.NONE);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 3;
		sourthBorderContainer.setLayoutData(gd);
		layout = new GridLayout();
		layout.numColumns = 3;
		layout.horizontalSpacing = 0;
		layout.verticalSpacing = 0;
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		sourthBorderContainer.setLayout(layout);
		sourthBorderContainer.setBackgroundMode(SWT.INHERIT_DEFAULT);

		borders.put(new Integer(IContainer.BORDER_SW), new BorderCanvas(
				sourthBorderContainer, IContainer.BORDER_SW));
		borders.put(new Integer(IContainer.BORDER_S), new BorderCanvas(
				sourthBorderContainer, IContainer.BORDER_S));
		borders.put(new Integer(IContainer.BORDER_SE), new BorderCanvas(
				sourthBorderContainer, IContainer.BORDER_SE));

		return clientArea;
	}

	private void applyEvents(Composite parent) {
		InternalListener listener = new InternalListener();
		parent.addListener(SWT.Activate, listener);
		parent.addListener(SWT.Deactivate, listener);
		parent.addListener(SWT.MouseEnter, listener);
		parent.addListener(SWT.MouseExit, listener);
		parent.addListener(SWT.MouseDown, listener);
		parent.addListener(SWT.MouseUp, listener);
		/**
		 * Remove key listener, because the traverse listener depends the key
		 * listeners.
		 */
		// parent.addListener(SWT.KeyDown, listener);
		// parent.addListener(SWT.KeyUp, listener);
	}

	private class InternalListener implements Listener {

		public void handleEvent(Event event) {
			Iterator iter = borders.values().iterator();
			for (; iter.hasNext();) {
				BorderCanvas border = (BorderCanvas) iter.next();
				if (!border.isDisposed()) {
					border.updateSkin(event);
				}
			}
			if (!clientArea.isDisposed())
				clientArea.updateSkin(event);
		}
	}

}