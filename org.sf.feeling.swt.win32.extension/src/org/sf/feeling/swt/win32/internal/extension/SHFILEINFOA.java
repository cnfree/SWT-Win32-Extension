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

public class SHFILEINFOA extends SHFILEINFO {

	private static final long serialVersionUID = -636293449602441337L;
	public byte[] szDisplayName = new byte[Extension.MAX_PATH];
	public byte[] szTypeName = new byte[80];
	public static int sizeof = 352;

	public static SHFILEINFOA valueOf(int point) {
		SHFILEINFOA struct = new SHFILEINFOA();
		Extension.CreateStructByPoint(point, struct);
		return struct;
	}

}
