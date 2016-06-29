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
import org.sf.feeling.swt.win32.internal.extension.MEMORYSTATUS;

/**
 * This class represents MEMORYSTATUS structure.
 * 
 * @author <a href="mailto:cnfree2000@hotmail.com">cnfree</a>
 */
public class MemoryStatus
{

	private long totalPhys;

	private long availPhys;

	private long totalPageFile;

	private long availPageFile;

	private long totalVirtual;

	private long availVirtual;

	private int memoryLoad;

	private static MemoryStatus memoryStatus = null;

	private MemoryStatus()
	{
	}

	/**
	 * Get a global MemoryStatus instance.
	 * 
	 * @return a global MemoryStatus instance.
	 */
	public synchronized static MemoryStatus getInstance()
	{
		if (memoryStatus == null)
		{
			memoryStatus = new MemoryStatus();
			memoryStatus.refreshStatus();
		}
		return memoryStatus;
	}

	/**
	 * Get MEMORYSTATUS information and refresh MemoryStatus internal data.
	 */
	public void refreshStatus()
	{
		MEMORYSTATUS status = Extension.GlobalMemoryStatus();
		memoryStatus.totalPhys = status.ullTotalPhys;
		memoryStatus.totalVirtual = status.ullTotalVirtual;
		memoryStatus.totalVirtual = status.ullTotalPageFile;
		memoryStatus.availPhys = status.ullAvailPhys;
		memoryStatus.availPageFile = status.ullAvailPageFile;
		memoryStatus.availVirtual = status.ullAvailVirtual;
		memoryStatus.memoryLoad = status.dwMemoryLoad;
	}

	/**
	 * Get system total physical memory size.
	 * 
	 * @return system total physical memory size.
	 */
	public long getTotalPhys()
	{
		return totalPhys;
	}

	/**
	 * Get system available physical memory size.
	 * 
	 * @return system available physical memory size.
	 */
	public long getAvailPhys()
	{
		return availPhys;
	}

	/**
	 * Get system total physical memory page file.
	 * 
	 * @return system total physical memory page file.
	 */
	public long getTotalPageFile()
	{
		return totalPageFile;
	}

	/**
	 * Get system available physical memory page file.
	 * 
	 * @return system available physical memory page file.
	 */
	public long getAvailPageFile()
	{
		return availPageFile;
	}

	/**
	 * Get system total virtual memory size.
	 * 
	 * @return system total virtual memory size.
	 */
	public long getTotalVirtual()
	{
		return totalVirtual;
	}

	/**
	 * Get system available virtual memory size.
	 * 
	 * @return system available virtual memory size.
	 */
	public long getAvailVirtual()
	{
		return availVirtual;
	}

	/**
	 * A number between 0 and 100 that specifies the approximate percentage of
	 * physical memory that is in use (0 indicates no memory use and 100
	 * indicates full memory use).
	 * 
	 * @return the percentage of physical memory that is in use.
	 */
	public int getMemoryLoad()
	{
		return memoryLoad;
	}
}
