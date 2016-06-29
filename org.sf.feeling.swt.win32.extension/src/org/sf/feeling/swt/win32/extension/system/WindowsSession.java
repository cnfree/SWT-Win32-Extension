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

/**
 * A window session utility class.
 * 
 * @author <a href="mailto:cnfree2000@hotmail.com">cnfree</a>
 * 
 */
public class WindowsSession
{

	/**
	 * Log off computer.
	 * 
	 * @param isNotify
	 *            Notify other applications about logoff
	 * @return If logoff successfully, return true, else return false.
	 */
	public static boolean Logoff(boolean isNotify)
	{
		return Extension.Logoff(!isNotify);
	}

	/**
	 * Shut down computer.
	 * 
	 * @param isNotify
	 *            Notify other applications about shutdown
	 * @return If shutdown successfully, return true, else return false.
	 */
	public static boolean Shutdown(boolean isNotify)
	{
		return Extension.Shutdown(!isNotify);
	}

	/**
	 * Restart computer.
	 * 
	 * @param isNotify
	 *            Notify other applications about reboot
	 * @return If reboot successfully, return true, else return false.
	 */
	public static boolean Reboot(boolean isNotify)
	{
		return Extension.Reboot(!isNotify);
	}

	/**
	 * Suspend workstation.
	 * 
	 * @param isNotify
	 *            Notify other applications about suspend workstation
	 * @return If suspend workstation successfully, return true, else return
	 *         false.
	 */
	public static boolean SuspendWorkstation(boolean isNotify)
	{
		return Extension.SuspendWorkstation(isNotify);
	}

	/**
	 * Hibernate workstation.
	 * 
	 * @param isNotify
	 *            Notify other applications about hibernate workstation
	 * @return If hibernate workstation successfully, return true, else return
	 *         false.
	 */
	public static boolean HibernateWorkstation(boolean isNotify)
	{
		return Extension.HibernateWorkstation(isNotify);
	}

	/**
	 * Lock workstation.
	 * 
	 * @return If lock workstation successfully, return true, else return false.
	 */
	public static boolean LockWorkstation()
	{
		return Extension.LockWorkstation();
	}

	/**
	 * When use this API to shutdown computer will popup a shutdown computer
	 * dialog.
	 * 
	 * @param info
	 *            The information that user needs to know
	 * @param time
	 *            The time that the information will be displayed
	 * @param isNotify
	 *            Notify other applications about initiate shutdown
	 * @return If initiate shutdown successfully, return true, else return
	 *         false.
	 */
	public static boolean InitiateShutdown(String info, int time, boolean isNotify)
	{
		return Extension.InitiateShutdown(info, time, isNotify);
	}

	/**
	 * When use this API to reboot computer will popup a shutdown computer
	 * dialog.
	 * 
	 * @param info
	 *            The information that user needs to know
	 * @param time
	 *            The time that the information will be displayed
	 * @param isNotify
	 * @return If initiate reboot successfully, return true, else return false.
	 */
	public static boolean InitiateReboot(String info, int time, boolean isNotify)
	{
		return Extension.InitiateReboot(info, time, isNotify);
	}

	public static int getTickCount()
	{
		return Extension.GetTickCount();
	}
}
