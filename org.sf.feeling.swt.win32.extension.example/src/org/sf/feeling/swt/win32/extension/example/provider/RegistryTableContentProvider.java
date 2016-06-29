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
package org.sf.feeling.swt.win32.extension.example.provider;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.sf.feeling.swt.win32.extension.registry.RegistryKey;

public class RegistryTableContentProvider implements IStructuredContentProvider {

	public Object[] getElements(Object parent) {
		List list = new ArrayList();
		if (parent instanceof RegistryKey) {
			RegistryKey key = (RegistryKey) parent;
			if (key.hasValues()) {

				Iterator iter = key.values();
				while (iter.hasNext()) {
					list.add(iter.next());
				}
				return list.toArray();
			}
		}
		return list.toArray();
	}

	public void dispose() {
		// TODO Auto-generated method stub

	}

	public void inputChanged(Viewer arg0, Object arg1, Object arg2) {
		// TODO Auto-generated method stub

	}

}
