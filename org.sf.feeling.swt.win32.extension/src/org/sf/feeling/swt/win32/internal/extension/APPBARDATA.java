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

public class APPBARDATA extends STRUCT {
	private static final long serialVersionUID = -5746173330103087919L;
	public int cbSize;
	public int hWnd;
	public int uCallbackMessage;
	public int uEdge;
	public int rcLeft;
	public int rcTop;
	public int rcRight;
	public int rcBottom;
	public int lParam;

	public static final int sizeof = 36;

	public static APPBARDATA valueOf(int point) {
		APPBARDATA struct = new APPBARDATA();
		Extension.CreateStructByPoint(point, struct);
		return struct;
	}

}
