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
import org.sf.feeling.swt.win32.internal.extension.Extension2;

public class Mutex {

	private int handle;
	private boolean createSuccessful = true;
	private boolean disposed = false;

	public Mutex(boolean initialOwner, String name) throws MutexException {
		try {
			handle = Extension2.CreateMutex(initialOwner, name);
		} catch (MutexException e) {
			createSuccessful = false;
			disposed = true;
			throw e;
		}
	}

	public boolean isCreatedSuccessful() {
		return createSuccessful;
	}

	public boolean isDisposed() {
		return disposed;
	}

	public void dispose() {
		if (createSuccessful && !disposed) {
			Extension.CloseHandle(handle);
			disposed = true;
		}
	}
}
