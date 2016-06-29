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

public class MCI_STATUS_PARMS extends STRUCT {
	private static final long serialVersionUID = -1721004982903920082L;
	public int dwCallback;
	public int dwReturn;
	public int dwItem;
	public int dwTrack;
	public static int sizeof = 16;

	public static MCI_STATUS_PARMS valueOf(int point) {
		MCI_STATUS_PARMS struct = new MCI_STATUS_PARMS();
		Extension.CreateStructByPoint(point, struct);
		return struct;
	}

}
