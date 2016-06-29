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

import org.sf.feeling.swt.win32.extension.io.CDDrive;
import org.sf.feeling.swt.win32.extension.io.FileSystem;

public class TestCDDrive {

	public static void main(String[] args) {
		String[] drives = FileSystem.getLogicalDrives();
		for (int i = 0; i < drives.length; i++) {
			if (FileSystem.getDriveType(drives[i]) == FileSystem.DRIVE_TYPE_CDROM)
			{
				String drive = drives[i].substring(0, 2);
				final int deviceId = CDDrive.getDeviceID(drive);
				if (!CDDrive.isDoorOpened(deviceId)) {
					CDDrive.openDoor(deviceId);
				}
				CDDrive.closeDoor(deviceId);
				break;
			}
		}
	}
}
