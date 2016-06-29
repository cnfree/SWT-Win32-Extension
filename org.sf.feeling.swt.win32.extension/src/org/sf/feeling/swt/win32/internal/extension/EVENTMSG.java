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

public class EVENTMSG extends STRUCT {

	private static final long serialVersionUID = -6933683063976164623L;
	public int message;
	public int paramL;
	public int paramH;
	public int time;
	public int hwnd;

	public static int sizeof = 20;

	public static EVENTMSG valueOf(int point) {
		EVENTMSG struct = new EVENTMSG();
		Extension.CreateStructByPoint(point, struct);
		return struct;
	}

}
