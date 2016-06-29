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

import org.eclipse.swt.widgets.Event;

/**
 * This adapter class provides default implementations for the methods described
 * by the <code>MenuListener</code> interface.
 * <p>
 * Classes that wish to deal with <code>MenuEvent</code>s can extend this
 * class and override only the methods which they are interested in.
 * </p>
 * 
 * @see MenuListener
 */
public abstract class MenuAdapter implements MenuListener {
	/**
	 * Sent when a menu is hidden. The default behavior is to do nothing.
	 * 
	 * @param e
	 *            an event containing information about the menu operation
	 */
	public void menuHidden(Event e) {

	}

	/**
	 * Sent when a menu is selected. The default behavior is to do nothing.
	 * 
	 * @param e
	 *            an event containing information about the menu operation
	 */
	public void menuSelected(Event e) {

	}

	/**
	 * Sent when a menu is shown. The default behavior is to do nothing.
	 * 
	 * @param e
	 *            an event containing information about the menu operation
	 */
	public void menuShown(Event e) {

	}

}
