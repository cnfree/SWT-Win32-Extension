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

public class MSLLHOOKSTRUCT extends STRUCT {

	private static final long serialVersionUID = 4035120911058948798L;
	public int pointX;
	public int pointY;
	public int mouseData;
	public int flags;
	public int time;
	public int dwExtraInfo;
	public static final int sizeof = 24;

	public static MSLLHOOKSTRUCT valueOf(int point) {
		MSLLHOOKSTRUCT struct = new MSLLHOOKSTRUCT();
		Extension.CreateStructByPoint(point, struct);
		return struct;
	}

}
