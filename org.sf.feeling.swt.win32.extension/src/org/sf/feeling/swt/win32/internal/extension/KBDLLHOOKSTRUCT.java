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

public class KBDLLHOOKSTRUCT extends STRUCT {

	private static final long serialVersionUID = -2608529834317381667L;
	public int vkCode;
	public int scanCode;
	public int flags;
	public int time;
	public int dwExtraInfo;
	public static final int sizeof = 24;

	public static KBDLLHOOKSTRUCT valueOf(int point) {
		KBDLLHOOKSTRUCT struct = new KBDLLHOOKSTRUCT();
		Extension.CreateStructByPoint(point, struct);
		return struct;
	}

}
