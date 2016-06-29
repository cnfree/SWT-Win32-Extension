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
package org.sf.feeling.swt.win32.extension.system;

import org.eclipse.swt.internal.win32.DLLVERSIONINFO;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.TCHAR;

/**
 * This class represents the DLLVERSIONINFO structure and provides the ability
 * to load this structure from a specified library.
 * <p>
 * <b>Note:</b> Not all libraries have the <code>DllGetVersion</code>
 * function, which is used by this class. If you want to get the version of any
 * library, please use {@link FileVersionInfo}.
 * 
 * @author <a href="mailto:cnfree2000@hotmail.com">cnfree</a>
 */
public class DllVersionInfo {
	/**
	 * The DLL was built for all Microsoft Windows platforms.
	 */
	public static final int DLLVER_PLATFORM_WINDOWS = 1;

	/**
	 * The DLL was built specifically for Microsoft Windows NT.
	 */
	public static final int DLLVER_PLATFORM_NT = 2;

	private int majorVersion;
	private int minorVersion;
	private int buildNumber;
	private int platformID;

	/**
	 * Create a new DllVersionInfo instance.
	 * 
	 */
	public DllVersionInfo() {
	}

	/**
	 * Returns the major version of the DLL. If the DLL version is 4.0.950, this
	 * value will be 4.
	 * 
	 * @return the major version of the DLL.
	 */
	public int getMajorVersion() {
		return majorVersion;
	}

	/**
	 * Returns the minor version of the DLL. If the DLL version is 4.0.950, this
	 * value will be 0.
	 * 
	 * @return the minor version of the DLL.
	 */
	public int getMinorVersion() {
		return minorVersion;
	}

	/**
	 * Returns the build number of the DLL. If the DLL version is 4.0.950, this
	 * value will be 950.
	 * 
	 * @return the build number of the DLL.
	 */
	public int getBuildNumber() {
		return buildNumber;
	}

	/**
	 * Returns the platform identifier for which the DLL was built. This can be
	 * one of the following values:
	 * 
	 * @return the platform identifier for which the DLL was built.
	 */
	public int getPlatformID() {
		return platformID;
	}

	/**
	 * Load the specified library.
	 * 
	 * @param libraryName
	 *            the name of library to be loaded.
	 */
	public void loadVersionInfo(String libraryName) {
		DLLVERSIONINFO dvi = new DLLVERSIONINFO();
		dvi.cbSize = DLLVERSIONINFO.sizeof;
		TCHAR lpLibFileName = new TCHAR(0, libraryName, true); //$NON-NLS-1$
		int hModule = OS.LoadLibrary(lpLibFileName);
		if (hModule != 0) {
			String name = "DllGetVersion\0"; //$NON-NLS-1$
			byte[] lpProcName = new byte[name.length()];
			for (int i = 0; i < lpProcName.length; i++) {
				lpProcName[i] = (byte) name.charAt(i);
			}
			int DllGetVersion = OS.GetProcAddress(hModule, lpProcName);
			if (DllGetVersion != 0)
				OS.Call(DllGetVersion, dvi);
			OS.FreeLibrary(hModule);
		}
		this.buildNumber = dvi.dwBuildNumber;
		this.majorVersion = dvi.dwMajorVersion;
		this.minorVersion = dvi.dwMinorVersion;
		this.platformID = dvi.dwPlatformID;
	}

	/**
	 * Returns the specified library information details.
	 */
	public String toString() {
		return "DllVersionInfo: [Major Version = " + getMajorVersion()
				+ "; Minor Version = " + getMinorVersion()
				+ "; Build Number = " + getBuildNumber() + "; PlatformID = "
				+ getPlatformID() + "]";
	}

}
