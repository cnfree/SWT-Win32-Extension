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
package org.sf.feeling.swt.win32.extension.snippets;

import org.sf.feeling.swt.win32.extension.system.DllVersionInfo;

public class TestLibraryVersionInfo {
	public static void main(String[] args) {
		DllVersionInfo info = new DllVersionInfo();
		info.loadVersionInfo("comctl32.dll");
		System.out.println("Dll comctl32.dll version = " + info.toString());
	}
}
