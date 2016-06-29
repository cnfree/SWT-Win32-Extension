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
import org.eclipse.swt.widgets.Event;
import org.sf.feeling.swt.win32.extension.widgets.listener.MenuListener;

public class CMenu {

	private List items = new ArrayList();

	public CMenuItem addItem(CMenuItem value) {
		items.add(value);
		return value;
	}

	public void addItems(CMenuItem[] values) {
		for (int i = 0; i < values.length; i++) {
			addItem(values[i]);
		}
	}

	public void removeItem(CMenuItem value) {
		value.disposed();
		items.remove(value);
	}

	public void addItem(int index, CMenuItem value) {
		items.add(index, value);
	}

	public void removeAll() {
		while (items.size() > 0)
			removeItem(getItem(0));
	}

	public boolean contains(CMenuItem value) {
		return items.contains(value);
	}

	public int getItemCount() {
		return items.size();
	}

	public CMenuItem getItem(int index) {
		if (index < 0 || index >= items.size())
			SWT.error(SWT.ERROR_INVALID_ARGUMENT);
		return (CMenuItem) items.get(index);
	}

	public CMenuItem getItem(String text) {
		for (int i = 0; i < items.size(); i++) {
			CMenuItem item = getItem(i);
			if (item.getText().equals(text))
				return item;
		}
		return null;
	}

	public boolean visibleItems() {
		for (int i = 0; i < items.size(); i++) {
			CMenuItem item = getItem(i);
			if (item.isVisible()) {
				if (!item.getText().equals("-")) {
					if (item.getMenu() != null
							&& item.getMenu().getItemCount() > 0
							&& !item.getMenu().visibleItems())
						continue;
					return true;
				}
			}
		}

		return false;
	}

	public int indexOf(CMenuItem value) {
		return items.indexOf(value);
	}

	private List listeners;

	public void addMenuListener(MenuListener listener) {
		if (listener == null)
			return;
		if (listeners == null)
			listeners = new ArrayList();
		if (!listeners.contains(listener))
			listeners.add(listener);
	}

	public void removeMenuListener(MenuListener listener) {
		if (listener == null)
			return;
		if ( listeners == null )
			return;
		else {
			listeners.remove(listener);
			if (listeners.size() == 0)
				listeners = null;
		}
	}

	void fireMenuEvent(final Event event) {
		if (listeners != null) {
			for (int i = 0; i < listeners.size(); i++) {
				final MenuListener listener = (MenuListener) listeners.get(i);
				switch (event.type) {
				case SWT.OPEN:
					listener.menuShown(event);
					break;
				case SWT.CLOSE:
					listener.menuHidden(event);
					break;
				case SWT.SELECTED:
					listener.menuSelected(event);
					break;
				}
			}
		}
	}

	public void dispose() {
		listeners.clear();
		removeAll();
	}
}
