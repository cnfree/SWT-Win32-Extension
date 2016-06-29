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

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.sf.feeling.swt.win32.extension.registry.RegistryKey;
import org.sf.feeling.swt.win32.extension.registry.RootKey;
import org.sf.feeling.swt.win32.extension.shell.ShellFolder;

public class RegistryTreeContentProvider implements ITreeContentProvider {

	public Object[] getChildren(Object parent) {
		List list = new ArrayList();
		if (parent instanceof Object[])
			return ((Object[]) parent);
		else if (parent instanceof ShellFolder) {
			list.add(new RegistryKey(RootKey.HKEY_CLASSES_ROOT));
			list.add(new RegistryKey(RootKey.HKEY_CURRENT_USER));
			list.add(new RegistryKey(RootKey.HKEY_LOCAL_MACHINE));
			list.add(new RegistryKey(RootKey.HKEY_USERS));
			list.add(new RegistryKey(RootKey.HKEY_CURRENT_CONFIG));
		} else if (parent instanceof RegistryKey) {
			RegistryKey key = (RegistryKey) parent;
			Iterator iter = key.subkeys();
			while (iter.hasNext()) {
				list.add(iter.next());
			}
		}
		return list.toArray();
	}

	public Object getParent(Object element) {
		return null;
	}

	public boolean hasChildren(Object parent) {
		if (parent instanceof Object[])
			return ((Object[]) parent).length > 0;
		else if (parent instanceof ShellFolder)
			return true;
		else if (parent instanceof RegistryKey) {
			RegistryKey key = (RegistryKey) parent;
			if (key.hasSubkeys())
				return true;
		}
		return false;
	}

	public Object[] getElements(Object parent) {
		if (parent instanceof Object[])
			return (Object[]) parent;
		return new Object[0];
	}

	public void dispose() {
		// TODO Auto-generated method stub

	}

	public void inputChanged(Viewer arg0, Object arg1, Object arg2) {
		// TODO Auto-generated method stub

	}

}
