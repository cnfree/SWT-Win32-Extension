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

import org.sf.feeling.swt.win32.extension.Win32;

/**
 * <b>Important:</b>This class is not for developer.
 * 
 * @author <a href="mailto:cnfree2000@hotmail.com">cnfree</a>
 * 
 */
public class EventObject {

	private int eventHandle;
	private String name;

	/**
	 * Creates a new named event object.
	 * 
	 * @param name
	 *            the name of the event; if this parameter is null then event
	 *            object created without name.
	 */
	public EventObject(String name) {
		eventHandle = Extension.CreateEvent(true, false, name);
		this.name = name;
	}

	/**
	 * Waits for an event object.
	 * 
	 * @param time
	 *            wait for time
	 */
	public void waitFor(int time) {
		Extension.WaitForSingleObject(eventHandle, time);
	}

	/**
	 * Waits for an event object.
	 */
	public void waitFor() {
		Extension.WaitForSingleObject(eventHandle, Win32.INFINITE_TIMEOUT);
	}

	/**
	 * Resets the state of the event object.
	 */
	public void reset() {
		Extension.ResetEvent(eventHandle);
	}

	/**
	 * Notifies the event object.
	 */
	public void notifyEvent() {
		Extension.SetEvent(eventHandle);
	}

	/**
	 * Closes the event object.
	 */
	public void close() {
		Extension2.CloseHandle(eventHandle);
	}

	/**
	 * Returns the event name.
	 * 
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Return the event handle.
	 * 
	 * @return event handle
	 */
	public int getEventHandle() {
		return eventHandle;
	}
}
