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

import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.graphics.Image;
import org.sf.feeling.swt.win32.extension.registry.RegistryValue;
import org.sf.feeling.swt.win32.extension.registry.ValueType;

public class RegistryTableLabelProvider implements ITableLabelProvider {

	public Image getColumnImage(Object element, int columnIndex) {
		return null;
	}

	public String getColumnText(Object element, int columnIndex) {
		RegistryValue value = (RegistryValue) element;
		switch (columnIndex) {
		case 0:
			return value.getName().trim().length() == 0 ? "(Default)" : value
					.getName().trim();
		case 1:
			return value.getType().toString();
		case 2:
			if (ValueType.REG_BINARY.equals(value.getType())) {
				byte[] data = (byte[]) value.getData();
				return bin2hex(data);
			} else
				return value.getData().toString();
		}
		return "";
	}

	public static String bin2hex(byte[] bs) {
        char[] digital = "0123456789ABCDEF".toCharArray();
        StringBuffer sb = new StringBuffer("");
        int bit;
        for (int i = 0; i < bs.length; i++) {
            bit = (bs[i] & 0x0f0) >> 4;
            sb.append(digital[bit]);
            bit = bs[i] & 0x0f;
            sb.append(digital[bit] + " ");
        }
        return sb.toString();
    }

	public void addListener(ILabelProviderListener listener) {
	}

	public void dispose() {
	}

	public boolean isLabelProperty(Object element, String property) {
		return false;
	}

	public void removeListener(ILabelProviderListener listener) {
	}

}
