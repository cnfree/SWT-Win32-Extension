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

public abstract class MCI_OPEN_PARMS extends STRUCT {
	private static final long serialVersionUID = 5785478706010179217L;
	public int dwCallback;
	public int wDeviceID;
	public int lpstrDeviceType;
	public int lpstrElementName;
	public int lpstrAlias;
	public static int sizeof = 20;
}
