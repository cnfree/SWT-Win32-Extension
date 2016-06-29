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
package org.sf.feeling.swt.win32.internal.extension;

public class FLASHWINFO extends STRUCT {

	private static final long serialVersionUID = 5929798161373816913L;
	public int cbSize = sizeof;
	public int hwnd;
	public int dwFlags;
	public int uCount;
	public int dwTimeout;
	public static final int sizeof = 20;

	public static final int FLASHW_STOP = 0;
	public static final int FLASHW_CAPTION = 1;
	public static final int FLASHW_TRAY = 2;
	public static final int FLASHW_ALL = FLASHW_CAPTION | FLASHW_TRAY;
	public static final int FLASHW_TIMER = 4;
	public static final int FLASHW_TIMERNOFG = 0xC;

	public static FLASHWINFO valueOf(int point) {
		FLASHWINFO struct = new FLASHWINFO();
		Extension.CreateStructByPoint(point, struct);
		return struct;
	}
}
