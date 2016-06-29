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

import org.sf.feeling.swt.win32.internal.extension.Extension;
import org.sf.feeling.swt.win32.internal.extension.SYSTEMINFO;

/**
 * This class represents SYSTEMINFO structure.
 * 
 * @author <a href="mailto:cnfree2000@hotmail.com">cnfree</a>
 * 
 */
public class SystemInfo {

	private int oemId;
	private int pageSize;
	private int minAppAddress;
	private int maxAppAddress;
	private int activeProcessorMask;
	private int numberOfProcessors;
	private int processorType;
	private int allocationGranularity;
	private static SystemInfo systemInfo = null;

	private SystemInfo() {
	}

	/**
	 * Get a global SystemInfo instance.
	 * 
	 * @return a global SystemInfo instance.
	 */
	public synchronized static SystemInfo getInstance() {
		if (systemInfo == null) {
			SYSTEMINFO info = Extension.GetSystemInfo();
			systemInfo = new SystemInfo();
			systemInfo.oemId = info.dwOemId;
			systemInfo.pageSize = info.dwPageSize;
			systemInfo.minAppAddress = info.lpMinimumApplicationAddress;
			systemInfo.maxAppAddress = info.lpMaximumApplicationAddress;
			systemInfo.activeProcessorMask = info.dwActiveProcessorMask;
			systemInfo.numberOfProcessors = info.dwNumberOfProcessors;
			systemInfo.processorType = info.dwProcessorType;
			systemInfo.allocationGranularity = info.dwAllocationGranularity;
		}
		return systemInfo;
	}

	/**
	 * Get OS OEM id.
	 * 
	 * @return OS OEM id.
	 */
	public int getOemId() {
		return oemId;
	}

	/**
	 * Get OS page size.
	 * 
	 * @return OS page size.
	 */
	public int getPageSize() {
		return pageSize;
	}

	public int getMinAppAddress() {
		return minAppAddress;
	}

	public int getMaxAppAddress() {
		return maxAppAddress;
	}

	public int getActiveProcessorMask() {
		return activeProcessorMask;
	}

	/**
	 * Get the number of computer processor.
	 * 
	 * @return the number of computer processor
	 */
	public int getNumberOfProcessors() {
		return numberOfProcessors;
	}

	/**
	 * Get processor type.
	 * 
	 * @return processor type.
	 */
	public int getProcessorType() {
		return processorType;
	}

	public int getAllocationGranularity() {
		return allocationGranularity;
	}

	/**
	 * Get computer name.
	 * 
	 * @return computer name.
	 */
	public String getComputerName() {
		return Extension.GetComputerName();
	}

	/**
	 * Set computer name;
	 * 
	 * @param name
	 *            new computer name
	 * @return return true if do this operation successfully, else return false;
	 */
	public boolean setComputerName(String name) {
		if (name.getBytes().length > 31)
			throw new UnsupportedOperationException(
					"Compute Name length can't more than 31.");
		return Extension.SetComputerName(name);
	}

	/**
	 * Get current login user name.
	 * 
	 * @return current login user name.
	 */
	public String getUserName() {
		return Extension.GetUserName();
	}

	/**
	 * Get network MAC address array.
	 * 
	 * @return network MAC address array.
	 */
	public static String[] getMACAddresses() {
		int[] macids = Extension.GetMACID();
		if (macids.length == 0)
			return new String[0];
		else {
			String[] macAddresses = new String[macids.length];
			for (int i = 0; i < macids.length; i++) {
				int[] macAddress = Extension.GetMACAddress(macids[i]);
				if (macAddress.length == 0)
					macAddresses[i] = "";
				else {
					String strMacAdress = "";
					for (int j = 0; j < macAddress.length; j++) {
						String hex = Integer.toHexString(macAddress[j])
								.toUpperCase();
						if (hex.length() == 1)
							hex = "0" + hex;
						strMacAdress += hex;
						if (j != macAddress.length - 1)
							strMacAdress += "-";
					}
					macAddresses[i] = strMacAdress;

				}
			}
			return macAddresses;
		}
	}

	/**
	 * Get computer CPU usage.
	 * 
	 * @return computer CPU usage.
	 */
	public static int getCpuUsages() {
		return Extension.GetCpuUsages();
	}

	/**
	 * Get computer CPU id.
	 * 
	 * @return computer CPU id.
	 */
	public static String getCPUID() {
		return Extension.GetCPUID();
	}

	/**
	 * Get network MAC id array.
	 * 
	 * @return network MAC id array.
	 */
	public static int[] getMACID() {
		return Extension.GetMACID();
	}

}
