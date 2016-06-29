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

public class MOUSEHOOKSTRUCT extends STRUCT {

	private static final long serialVersionUID = 8846128007222243643L;
	public int pointX;
	public int pointY;
	public int hwnd;
	public int wHitTestCode;
	public int dwExtraInfo;
	public static final int sizeof = 20;

	public static MOUSEHOOKSTRUCT valueOf(int point) {
		MOUSEHOOKSTRUCT struct = new MOUSEHOOKSTRUCT();
		Extension.CreateStructByPoint(point, struct);
		return struct;
	}

}
