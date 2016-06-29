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

import java.util.Vector;

class WNDCallbackList {
	private Vector callbackList = new Vector();

	public int getOldAddress(int newAddress) {
		if (callbackList.indexOf(new Integer(newAddress)) <= 0)
			return -1;
		return ((Integer) callbackList.get(callbackList.indexOf(new Integer(
				newAddress)) - 1)).intValue();
	}

	public boolean putCallbackAddress(int oldAddress, int newAddress) {
		if (callbackList.size() == 0) {
			callbackList.add(new Integer(oldAddress));
			callbackList.add(new Integer(newAddress));
			return true;
		} else {
			if (((Integer) callbackList.lastElement()).intValue() == oldAddress) {
				callbackList.add(new Integer(newAddress));
				return true;
			}
		}
		return false;
	}

	public void disposeCallback(int address) {
		callbackList.remove(new Integer(address));
	}

	public int getSize() {
		return callbackList.size();
	}
}
