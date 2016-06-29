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

import org.sf.feeling.swt.win32.extension.Win32;

/**
 * Window custom style constant class.
 * 
 * @author <a href="mailto:cnfree2000@hotmail.com">cnfree</a>
 * 
 */
public class ThemeConstants {
	/**
	 * Windows Vista Style;
	 */
	public final static String STYLE_VISTA = "vista";

	public final static String STYLE_OFFICE2007 = "office2007";

	public static final String STYLE_GLOSSY = "glossy";

	/**
	 * Help Button id.
	 */
	public final static int BUTTON_HELP = 0;

	/**
	 * Minimize Button id.
	 */
	public final static int BUTTON_MIN = 1;

	/**
	 * Maximize Button id
	 */
	public final static int BUTTON_MAX = 2;

	/**
	 * Restore Button id
	 */
	public final static int BUTTON_REV = 3;

	/**
	 * Close Button id.
	 */
	public final static int BUTTON_CLOSE = 4;

	public static boolean containTheme(String theme) {
		if (STYLE_VISTA.equals(theme))
			return true;
		else if (STYLE_OFFICE2007.equals(theme)) {
			if (Win32.getWin32Version() >= Win32.VERSION(5, 0))
				return true;
		} else if (STYLE_GLOSSY.equals(theme)) {
			if (Win32.getWin32Version() >= Win32.VERSION(5, 0))
				return true;
		}
		return false;
	}

}
