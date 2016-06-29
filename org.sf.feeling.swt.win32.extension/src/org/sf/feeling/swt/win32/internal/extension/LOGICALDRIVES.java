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

public class LOGICALDRIVES extends STRUCT {

	private static final long serialVersionUID = -5725884481107037078L;
	public boolean returnValue; // true if call succeeded, false otherwise
	public int lastError; // contains the Win32 error if an error occurred

	public static int sizeof = 8;

	public static LOGICALDRIVES valueOf(int point) {
		LOGICALDRIVES struct = new LOGICALDRIVES();
		Extension.CreateStructByPoint(point, struct);
		return struct;
	}

}
