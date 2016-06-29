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

/*******************************************************************************
 * Win32GetDiskFreeSpaceEx A shell (in form of a class) around the
 * GetDiskFreeSpaceEx() win32 call
 * **************************************************************************
 * freeBytesAvailable [out] Pointer to a variable that receives the total number
 * of free bytes on the disk that are available to the user associated with the
 * calling thread. This parameter can be NULL. Windows 2000/XP: If per-user
 * quotas are in use, this value may be less than the total number of free bytes
 * on the disk.
 * 
 * totalNumberOfBytes [out] Pointer to a variable that receives the total number
 * of bytes on the disk that are available to the user associated with the
 * calling thread. This parameter can be NULL. Windows 2000/XP: If per-user
 * quotas are in use, this value may be less than the total number of bytes on
 * the disk.
 * 
 * totalNumberOfFreeBytes [out] Pointer to a variable that receives the total
 * number of free bytes on the disk. This parameter can be NULL. Return Values
 * If the function succeeds, the return value is nonzero.
 * 
 * If the function fails, the return value is zero. To get extended error
 * information, call GetLastError.
 * 
 ******************************************************************************/

public class DISKFREESPACE extends STRUCT {

	private static final long serialVersionUID = 847586804567093690L;
	public boolean returnValue; // true if call succeeded, false otherwise
	public int lastError; // contains the Win32 error if error occurred
	public long freeBytesAvailable; // contains value returned from call if call
									// succeeded
	public long totalNumberOfBytes; // contains value returned from call if call
									// succeeded
	public long totalNumberOfFreeBytes; // contains value returned from call if
										// call succeeded

	public static int sizeof = 32;

	public static DISKFREESPACE valueOf(int point) {
		DISKFREESPACE struct = new DISKFREESPACE();
		Extension.CreateStructByPoint(point, struct);
		return struct;
	}
}
