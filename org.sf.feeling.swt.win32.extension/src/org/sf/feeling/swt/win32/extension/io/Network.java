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

import org.sf.feeling.swt.win32.internal.extension.Extension;

/**
 * This class is a utility class about network.
 * 
 * @author <a href="mailto:cnfree2000@hotmail.com">cnfree</a>
 * 
 */
public class Network {
	/**
	 * Get the time of pinging a remote destination.
	 * 
	 * @param host
	 *            the specified remote destination
	 * @param dateSize
	 *            the size of the data package
	 * @return the time of pinging remote destination
	 */
	public static int ping(String host, int dateSize) {
		return Extension.Ping(host, dateSize);
	}
}
