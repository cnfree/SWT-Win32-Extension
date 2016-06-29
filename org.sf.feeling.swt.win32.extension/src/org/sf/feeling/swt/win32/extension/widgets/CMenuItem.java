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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;

/**
 * Style: SWT.CHECK or SWT.RADIO or SWT.SEPARATOR
 * 
 * @author <a href="mailto:cnfree2000@hotmail.com">cnfree</a>
 * 
 */
public class CMenuItem {

	private String text = "";

	private int style = SWT.NONE;

	public CMenuItem(int style) {
		this.style = style;
	}

	public CMenuItem() {
	}

	public CMenuItem(String text, int style) {
		this.style = style;
		this.text = text == null ? "" : text;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		if (text == null)
			this.text = "";
		else
			this.text = text;
	}

	private Image image;

	private Image disableImage;

	public Image getDisableImage() {
		return disableImage;
	}

	public void setDisableImage(Image disabledImage) {
		this.disableImage = disabledImage;
	}

	private Shortcut shortcut;

	public boolean isVisible() {
		return visible;
	}

	private boolean visible = true;

	private boolean enabled = true;

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	private CMenu menu;

	public void setMenu(CMenu menu) {
		this.menu = menu;
	}

	public CMenu getMenu() {
		return menu;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public Shortcut getShortcut() {
		return shortcut;
	}

	public void setShortcut(Shortcut shortcut) {
		this.shortcut = shortcut;
	}

	public int getStyle() {
		return style;
	}

	private boolean selected = false;

	public boolean getSelection() {
		return selected;
	}

	public void setSelection(boolean selected) {
		this.selected = selected;
	}

	private List listeners;

	public void addSelectionListener(SelectionListener listener) {
		if (isDisposed())
			return;
		if (listener == null)
			return;
		if (listeners == null)
			listeners = new ArrayList();
		if (!listeners.contains(listener))
			listeners.add(listener);
	}

	public void removeSelectionListener(SelectionListener listener) {
		if (isDisposed())
			return;
		if (listener == null)
			return;
		else {
			listeners.remove(listener);
			if (listeners.size() == 0)
				listeners = null;
		}
	}

	void fireSelectionEvent(final Event event) {
		if (isDisposed() || !isEnabled())
			return;
		if (listeners != null) {
			for (int i = 0; i < listeners.size(); i++) {
				final SelectionEvent selectEvent = new SelectionEvent(event);
				final SelectionListener listener = (SelectionListener) listeners
						.get(i);
				Display.getDefault().asyncExec(new Runnable() {
					public void run() {
						listener.widgetSelected(selectEvent);
					}
				});
			}
		}
	}

	private boolean isDisposed;

	public void disposed() {
		isDisposed = true;
		if (listeners != null) {
			listeners.clear();
			listeners = null;
		}
	}

	public boolean isDisposed() {
		return isDisposed;
	}

	public boolean isSeparator() {
		return getText().equals("-") || (getStyle() & SWT.SEPARATOR) != 0;
	}
}
