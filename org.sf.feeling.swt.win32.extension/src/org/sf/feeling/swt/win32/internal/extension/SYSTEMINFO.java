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

public class SYSTEMINFO extends STRUCT {

	private static final long serialVersionUID = -5785925551925689529L;
	public int dwOemId;
	public int dwPageSize;
	public int lpMinimumApplicationAddress;
	public int lpMaximumApplicationAddress;
	public int dwActiveProcessorMask;
	public int dwNumberOfProcessors;
	public int dwProcessorType;
	public int dwAllocationGranularity;

	public static int sizeof = 32;

	public static SYSTEMINFO valueOf(int point) {
		SYSTEMINFO struct = new SYSTEMINFO();
		Extension.CreateStructByPoint(point, struct);
		return struct;
	}

}
