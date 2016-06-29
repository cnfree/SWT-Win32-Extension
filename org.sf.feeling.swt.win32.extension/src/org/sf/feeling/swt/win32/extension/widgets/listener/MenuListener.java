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
package org.sf.feeling.swt.win32.extension.widgets.listener;

import org.eclipse.swt.events.MenuAdapter;
import org.eclipse.swt.widgets.Event;

/**
 * Classes which implement this interface provide methods that deal with the
 * hiding and showing of menus.
 * <p>
 * After creating an instance of a class that implements this interface it can
 * be added to a menu using the <code>addMenuListener</code> method and
 * removed using the <code>removeMenuListener</code> method. When the menu is
 * hidden, shown or selected, the appropriate method will be invoked.
 * </p>
 * 
 * @see MenuAdapter
 */
public interface MenuListener {

	/**
	 * Sent when a menu is shown.
	 * 
	 * @param e
	 *            an event containing information about the menu operation
	 */
	void menuShown(Event e);

	/**
	 * Sent when a menu is hidden.
	 * 
	 * @param e
	 *            an event containing information about the menu operation
	 */
	void menuHidden(Event e);

	/**
	 * Sent when a menu is selected.
	 * 
	 * @param e
	 *            an event containing information about the menu operation
	 */
	void menuSelected(Event e);
}
