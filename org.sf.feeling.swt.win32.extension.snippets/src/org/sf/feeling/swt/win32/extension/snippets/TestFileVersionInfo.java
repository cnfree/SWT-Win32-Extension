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

import org.sf.feeling.swt.win32.extension.system.FileVersionInfo;

public class TestFileVersionInfo {
	public static void main(String[] args) {
		FileVersionInfo info = new FileVersionInfo();
		info.loadVersionInfo("Comctl32.dll");
		System.out.println("Dll comctl32.dll version = "
				+ info.getFileVersion());
		System.out.println("MajorVersion=" + info.getMajorVersion());
		System.out.println("MinorVersion=" + info.getMinorVersion());
		System.out
				.println("----------------File Version Info Detail----------------");
		System.out.println("Comments=" + info.getComments());
		System.out.println("CompanyName=" + info.getCompanyName());
		System.out.println("FileDescription=" + info.getFileDescription());
		System.out.println("FileVersion=" + info.getFileVersion());
		System.out.println("InternalName=" + info.getInternalName());
		System.out.println("LegalCopyright=" + info.getLegalCopyright());
		System.out.println("LegalTrademarks=" + info.getLegalTrademarks());
		System.out.println("OriginalFilename=" + info.getOriginalFilename());
		System.out.println("PrivateBuild=" + info.getPrivateBuild());
		System.out.println("ProductName=" + info.getProductName());
		System.out.println("ProductVersion=" + info.getProductVersion());
		System.out.println("SpecialBuild=" + info.getSpecialBuild());
	}
}
