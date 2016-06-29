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

public class MEMORYSTATUS extends STRUCT {

	private static final long serialVersionUID = -6059369508959624927L;
	public int dwLength;

	public int dwMemoryLoad;

	public long ullTotalPhys;

	public long ullAvailPhys;

	public long ullTotalPageFile;

	public long ullAvailPageFile;

	public long ullTotalVirtual;

	public long ullAvailVirtual;

	public static final int sizeof = 64;

	public static MEMORYSTATUS valueOf(int point) {
		MEMORYSTATUS struct = new MEMORYSTATUS();
		Extension.CreateStructByPoint(point, struct);
		return struct;
	}

}
