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

import java.util.Hashtable;

import org.eclipse.swt.widgets.Shell;

public class WNDCallbackManager
{
	private static Hashtable callbackMap = new Hashtable();

	public static void disposeCallback(Shell shell, WNDCallback callback)
	{
		WNDCallbackList list = (WNDCallbackList) callbackMap.get(shell);
		if (list != null)
		{
			callback.setOldAddress(list.getOldAddress(callback.getNewAddress()));
			list.disposeCallback(callback.getNewAddress());
			if (list.getSize() == 1)
			{
				callbackMap.remove(shell);
			}
			callback.dispose();
		}
	}

	public static boolean addCallback(Shell shell, WNDCallback callback)
	{
		WNDCallbackList list = (WNDCallbackList) callbackMap.get(shell);
		if (list != null)
		{
			return list.putCallbackAddress(callback.getOldAddress(), callback.getNewAddress());
		}
		else
		{
			WNDCallbackList callbackList = new WNDCallbackList();
			callbackMap.put(shell, callbackList);
			return callbackList.putCallbackAddress(callback.getOldAddress(), callback
					.getNewAddress());
		}
	}

}
