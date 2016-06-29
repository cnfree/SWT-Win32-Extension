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
package org.sf.feeling.swt.win32.extension.io;

import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.TCHAR;
import org.sf.feeling.swt.win32.internal.extension.Extension;
import org.sf.feeling.swt.win32.internal.extension.MCI_OPEN_PARMS;
import org.sf.feeling.swt.win32.internal.extension.MCI_OPEN_PARMSA;
import org.sf.feeling.swt.win32.internal.extension.MCI_OPEN_PARMSW;
import org.sf.feeling.swt.win32.internal.extension.MCI_STATUS_PARMS;

/**
 * This class is a utility class about CD Drive operation.<br/>
 * 
 * <b>Important</b>: Vista doesn't support it.
 * 
 * @author <a href="mailto:cnfree2000@hotmail.com">cnfree</a>
 * 
 */
public class CDDrive
{

	private static final String MCI_DEVTYPE_CD_AUDIO = "CDAudio";

	private static final int MCI_OPEN_TYPE = 8192;

	private static final int MCI_OPEN = 2051;

	private static final int MCI_OPEN_ELEMENT = 512;

	private static final int MCI_SET = 2061;

	private static final int MCI_SET_DOOR_OPEN = 256;

	private static final int MCI_SET_DOOR_CLOSED = 512;

	private static final int MCI_STATUS_MODE = 4;

	private static final int MCI_STATUS_ITEM = 256;

	private static final int MCI_WAIT = 256;

	private static final int MCI_STATUS = 2068;

	private static final int MCI_MODE_OPEN = 530;

	private static final int MCI_STATUS_READY = 7;

	private static final int DBT_DEVICEARRIVAL = 32768;

	private static final int DBT_DEVICEREMOVECOMPLETE = 32772;

	public static final int DEVICE_LOADED = DBT_DEVICEARRIVAL;

	public static final int DEVICE_EJECTED = DBT_DEVICEREMOVECOMPLETE;

	/**
	 * Get CD Device ID.
	 * 
	 * @param path
	 *            is a root file like A:\, C:\ etc. But you should remove the
	 *            char "\" from the file path, pass "A:", "C:" etc.
	 * @return the device id of the specific CD Drive. If the operation is
	 *         successful, the id doesn't equals 0.
	 */
	public static int getDeviceID(String path)
	{
		MCI_OPEN_PARMS parms = null;
		TCHAR strDeviceType = new TCHAR(0, MCI_DEVTYPE_CD_AUDIO, true);
		TCHAR strElementName = new TCHAR(0, path, true);
		int hHeap = Extension.GetProcessHeap();
		int byteCount = strDeviceType.length() * TCHAR.sizeof;
		int lpstrDeviceType = Extension
				.HeapAlloc(hHeap, Extension.HEAP_ZERO_MEMORY, byteCount);
		Extension.MoveMemory(lpstrDeviceType, strDeviceType, byteCount);

		byteCount = strElementName.length() * TCHAR.sizeof;
		int lpstrElementName = Extension.HeapAlloc(hHeap, Extension.HEAP_ZERO_MEMORY,
				byteCount);
		Extension.MoveMemory(lpstrElementName, strElementName, byteCount);

		if (Extension.IsUnicode)
		{
			parms = new MCI_OPEN_PARMSW();
			parms.dwCallback = 0;
			((MCI_OPEN_PARMSW) parms).lpstrDeviceType = lpstrDeviceType;
			((MCI_OPEN_PARMSW) parms).lpstrElementName = lpstrElementName;
		}
		else
		{
			parms = new MCI_OPEN_PARMSA();
			parms.dwCallback = 0;
			((MCI_OPEN_PARMSA) parms).lpstrDeviceType = lpstrDeviceType;
			((MCI_OPEN_PARMSA) parms).lpstrElementName = lpstrElementName;
		}
		int res = Extension.MciSendCommand(0, MCI_OPEN, MCI_OPEN_TYPE | MCI_OPEN_ELEMENT,
				parms);
		if (res != 0)
		{
			OS.HeapFree(hHeap, 0, lpstrDeviceType);
			OS.HeapFree(hHeap, 0, lpstrElementName);
			return 0;
		}
		OS.HeapFree(hHeap, 0, lpstrDeviceType);
		OS.HeapFree(hHeap, 0, lpstrElementName);
		return parms.wDeviceID;
	}

	private static boolean operateDoor(int deviceID, boolean isOpen)
	{
		int res = 0;
		if (deviceID == 0) return false;
		if (isOpen) res = Extension.MciSendCommand(deviceID, MCI_SET, MCI_SET_DOOR_OPEN, null);
		else
			res = Extension.MciSendCommand(deviceID, MCI_SET, MCI_SET_DOOR_CLOSED, null);
		return res == 0;
	}

	/**
	 * Open the specific CD Drive.
	 * 
	 * @param deviceID
	 *            identifying a CD device id.
	 * @return if the operation is successful.
	 */
	public static boolean openDoor(int deviceID)
	{
		return operateDoor(deviceID, true);
	}

	/**
	 * Close the specific CD Drive.
	 * 
	 * @param deviceID
	 *            identifying a CD device id.
	 * @return if the operation is successful.
	 */
	public static boolean closeDoor(int deviceID)
	{
		return operateDoor(deviceID, false);
	}

	// FIXME: NoteBook CDROM always return true
	/**
	 * Get the CD Drive opened status.<br>
	 * <b>Important</b>:NoteBook CDROM always return true.
	 * 
	 * @param deviceID
	 *            identifying a CD device id.
	 * @return if the specific CD Drive is opened.
	 */
	public static boolean isDoorOpened(int deviceID)
	{
		if (deviceID == 0) return false;
		MCI_STATUS_PARMS status = new MCI_STATUS_PARMS();
		status.dwItem = MCI_STATUS_MODE;
		int res = Extension.MciSendCommand(deviceID, MCI_STATUS, MCI_STATUS_ITEM | MCI_WAIT,
				status);
		if (res != 0) return false;
		return status.dwReturn == MCI_MODE_OPEN;
	}

	/**
	 * Get the CD Drive ready status.
	 * 
	 * @param deviceID
	 *            identifying a CD device id.
	 * @return if the specific CD Drive is ready.
	 */
	public static boolean isDiscReady(int deviceID)
	{
		if (deviceID == 0) return false;
		MCI_STATUS_PARMS status = new MCI_STATUS_PARMS();
		status.dwItem = MCI_STATUS_READY;
		int res = Extension.MciSendCommand(deviceID, MCI_STATUS, MCI_STATUS_ITEM, status);
		if (res != 0) return false;
		return status.dwReturn != 0;
	}
}
