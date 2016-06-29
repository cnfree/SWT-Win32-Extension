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
package org.sf.feeling.swt.win32.internal.extension.callback;

import org.eclipse.swt.widgets.Shell;

public abstract class WNDCallback {
	private int newAddress;
	private int oldAddress;
	private Shell Shell;

	public WNDCallback(Shell shell, int oldAddress, int newAddress) {
		this.Shell = shell;
		this.oldAddress = oldAddress;
		this.newAddress = newAddress;
	}

	public int getNewAddress() {
		return newAddress;
	}

	public int getOldAddress() {
		return oldAddress;
	}

	public Shell getShell() {
		return Shell;
	}

	public abstract void dispose();

	void setNewAddress(int newAddress) {
		this.newAddress = newAddress;
	}

	void setOldAddress(int oldAddress) {
		this.oldAddress = oldAddress;
	}

	void setShell(Shell shell) {
		Shell = shell;
	}
}
