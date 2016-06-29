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

public class SHFILEOPSTRUCTA extends SHFILEOPSTRUCT {

	private static final long serialVersionUID = 6885768298651571481L;
	public static final int sizeof = 32;

	public static SHFILEOPSTRUCTA valueOf(int point) {
		SHFILEOPSTRUCTA struct = new SHFILEOPSTRUCTA();
		Extension.CreateStructByPoint(point, struct);
		return struct;
	}

}
