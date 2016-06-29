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
package org.sf.feeling.swt.win32.extension.io;

import org.sf.feeling.swt.win32.extension.Win32;
import org.sf.feeling.swt.win32.internal.extension.Extension;

/**
 * A utility class that can emulate keyboard event.
 * 
 * @author <a href="mailto:cnfree2000@hotmail.com">cnfree</a>
 * 
 */
public class Keyboard {
	/**
	 * Emulate a key down event.
	 * 
	 * @param virtualKey
	 *            specified virtual key code.
	 * @param scanKey
	 *            specified scan key code.
	 * @param extended
	 *            specify whether extended key.
	 */
	public static void keyDown(int virtualKey, int scanKey, boolean extended) {
		int flag = Win32.KEYEVENTF_KEYDOWN;
		if (extended)
			flag |= Win32.KEYEVENTF_KEYDOWN;
		Extension.Keyboard_Event((byte) virtualKey, (byte) scanKey, flag, 0);
	}

	/**
	 * Emulate a key up event.
	 * 
	 * @param virtualKey
	 *            specified virtual key code.
	 * @param scanKey
	 *            specified scan key code.
	 * @param extended
	 *            specify whether extended key.
	 */
	public static void keyUp(int virtualKey, int scanKey, boolean extended) {
		int flag = Win32.KEYEVENTF_KEYUP;
		if (extended)
			flag |= Win32.KEYEVENTF_EXTENDEDKEY;
		Extension.Keyboard_Event((byte) virtualKey, (byte) scanKey, flag, 0);
	}
}
