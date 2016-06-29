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

import java.util.Hashtable;

class ShortcutMap {
	private Hashtable table = new Hashtable();

	public void putShortcut(Shortcut shortcut, CMenuItem item) {
		if (!table.containsKey(shortcut))
			table.put(shortcut, item);
	}

	public CMenuItem getMenuItem(Shortcut shortcut) {
		return (CMenuItem) table.get(shortcut);
	}

	public void dispose() {
		table.clear();
	}

	public boolean contains(Shortcut shortcut) {
		return table.containsKey(shortcut);
	}

	public void removeShortcut(Shortcut shortcut) {
		table.remove(shortcut);
	}
}
