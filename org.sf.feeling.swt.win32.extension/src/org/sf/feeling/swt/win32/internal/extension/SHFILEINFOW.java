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

public class SHFILEINFOW extends SHFILEINFO {

	private static final long serialVersionUID = 2877421867519607897L;
	public char[] szDisplayName = new char[Extension.MAX_PATH];
	public char[] szTypeName = new char[80];
	public static int sizeof = 692;

	public static SHFILEINFOW valueOf(int point) {
		SHFILEINFOW struct = new SHFILEINFOW();
		Extension.CreateStructByPoint(point, struct);
		return struct;
	}

}
