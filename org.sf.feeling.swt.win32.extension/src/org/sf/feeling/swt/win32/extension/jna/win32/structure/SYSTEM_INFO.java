/*******************************************************************************
 * Copyright (c) 2011 cnfree.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  cnfree  - initial API and implementation
 *******************************************************************************/
package org.sf.feeling.swt.win32.extension.jna.win32.structure;
import org.sf.feeling.swt.win32.extension.jna.exception.NativeException;
import org.sf.feeling.swt.win32.extension.jna.ptr.Pointer;

/**
 * SystemInfo this utility class is used by Kernel32.getNativeSystemInfo().<br>
 * This is the native peer.
 * <pre>
 *typedef struct _SYSTEM_INFO {
 * &nbsp;	union {
 * &nbsp;		DWORD dwOemId;
 * &nbsp;		struct {
 * &nbsp;			WORD wProcessorArchitecture;
 * &nbsp;			WORD wReserved;
 * &nbsp;		};
 * &nbsp;	};
 * &nbsp;	DWORD dwPageSize;
 * &nbsp;	LPVOID lpMinimumApplicationAddress;
 * &nbsp;	LPVOID lpMaximumApplicationAddress;
 * &nbsp;	DWORD_PTR dwActiveProcessorMask;
 * &nbsp;	DWORD dwNumberOfProcessors;
 * &nbsp;	DWORD dwProcessorType;
 * &nbsp;	DWORD dwAllocationGranularity;
 * &nbsp;	WORD wProcessorLevel;
 * &nbsp;	WORD wProcessorRevision;
 * &nbsp;} SYSTEM_INFO;
 *
 */
public class SYSTEM_INFO {
	
	public short wProcessorArchitecture;
	public short wReserved;
	public int dwPageSize;
	public int lpMinimumApplicationAddress;
	public int lpMaximumApplicationAddress;
	public int  dwActiveProcessorMask;
	public int  dwNumberOfProcessors;
	public int  dwProcessorType;
	public int  dwAllocationGranularity;
	public short wProcessorLevel;
	public short wProcessorRevision;
	
	public SYSTEM_INFO(Pointer lpSystemInfo) throws NativeException {
		int offset = -4;
		wProcessorArchitecture = lpSystemInfo.getAsShort(offset +4);
		wReserved = lpSystemInfo.getAsShort(offset +6);
		dwPageSize = lpSystemInfo.getAsInt(offset +8);
		lpMinimumApplicationAddress = lpSystemInfo.getAsInt(offset +12);
		lpMaximumApplicationAddress = lpSystemInfo.getAsInt(offset +16);
		dwActiveProcessorMask = lpSystemInfo.getAsInt(offset +20);
		dwNumberOfProcessors = lpSystemInfo.getAsInt(offset +24);
		dwProcessorType = lpSystemInfo.getAsInt(offset +28);
		dwAllocationGranularity = lpSystemInfo.getAsInt(offset +32);
		wProcessorLevel = lpSystemInfo.getAsShort(offset +36);
		wProcessorRevision = lpSystemInfo.getAsShort(offset +38);
	}
	
	public String toString() {
		return new StringBuffer("wProcessorArchitecture : " + wProcessorArchitecture).
			append("\nwReserved : " + wReserved).
			append("\ndwPageSize : " + dwPageSize).
			append("\nlpMinimumApplicationAddress : " + lpMinimumApplicationAddress).
			append("\nlpMaximumApplicationAddress : " + lpMaximumApplicationAddress).
			append("\ndwActiveProcessorMask : " + dwActiveProcessorMask).
			append("\ndwNumberOfProcessors : " + dwNumberOfProcessors).
			append("\ndwProcessorType : " + dwProcessorType).
			append("\ndwAllocationGranularity : " + dwAllocationGranularity).
			append("\nwProcessorLevel : " + wProcessorLevel).
			append("\nwProcessorRevision : " + wProcessorRevision).toString();
	}
}
