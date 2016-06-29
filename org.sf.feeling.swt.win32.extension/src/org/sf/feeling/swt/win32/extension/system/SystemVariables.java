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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.sf.feeling.swt.win32.extension.registry.RegistryKey;
import org.sf.feeling.swt.win32.extension.registry.RegistryValue;
import org.sf.feeling.swt.win32.extension.registry.RootKey;
import org.sf.feeling.swt.win32.internal.extension.Extension;

/**
 * This class allows managing system environment variables. *
 * 
 * @author <a href="mailto:cnfree2000@hotmail.com">cnfree</a>
 */
public class SystemVariables {

	private static final String SYSTEM_ENVIRONMENT = "System\\CurrentControlSet\\Control\\Session Manager\\Environment";
	private static final int HWND_BROADCAST = 0xffff;
	private RegistryKey registryKey;
	private static SystemVariables systemVariables = null;

	private SystemVariables() {
		registryKey = new RegistryKey(RootKey.HKEY_LOCAL_MACHINE,
				SYSTEM_ENVIRONMENT);
		if (!registryKey.exists())
			throw new UnsupportedOperationException();
	}

	/**
	 * Get a global SystemVariables instance.
	 * 
	 * @return a global SystemVariables instance.
	 */
	public static SystemVariables getInstance() {
		if (systemVariables == null)
			systemVariables = new SystemVariables();
		return systemVariables;
	}

	/**
	 * Returns the value of the specified variable from the environment block of
	 * the calling process.
	 * 
	 * @param variable
	 *            name of the variable to get the value for.
	 * @return value for the variable.
	 */
	public String getValue(String variable) {
		if (!registryKey.exists())
			throw new UnsupportedOperationException();
		if (registryKey.hasValue(variable))
			return (String) registryKey.getValue(variable).getData();
		else
			return null;
	}

	/**
	 * Sets the value of an environment variable for the current process.
	 * 
	 * @param variable
	 *            variable name.
	 * @param value
	 *            variable value.
	 */
	public void setVariable(String variable, String value) {
		if (!registryKey.exists())
			throw new UnsupportedOperationException();
		registryKey.setValue(new RegistryValue(variable, value));
		fireSettingChanged();
	}

	/**
	 * Sends a broadcast message saying that a variable is changed.
	 */
	private void fireSettingChanged() {
		Extension.SendMessage(HWND_BROADCAST, Extension.WM_SETTINGCHANGE, 0, 0);
	}

	/**
	 * Verifies if there is a specified variable in the environment.
	 * 
	 * @param variable
	 *            variable name.
	 * @return true, if the specified variable exists in the environment;
	 *         otherwise false.
	 */
	public boolean contains(String variable) {
		if (!registryKey.exists())
			throw new UnsupportedOperationException();
		return registryKey.hasValue(variable);
	}

	/**
	 * Remove specified variable.
	 * 
	 * @param variable
	 *            variable name.
	 */
	public void removeVariable(String variable) {
		if (!registryKey.exists())
			throw new UnsupportedOperationException();
		if (contains(variable))
			registryKey.deleteValue(variable);
	}

	/**
	 * Returns an array of variable names.
	 * 
	 * @return an array of variable names.
	 */
	public String[] getVariableNames() {
		if (!registryKey.exists())
			throw new UnsupportedOperationException();
		if (registryKey.hasValues()) {
			List nameList = new ArrayList();
			Iterator iter = registryKey.values();
			while (iter.hasNext()) {
				RegistryValue value = (RegistryValue) iter.next();
				nameList.add(value.getName());
			}
			return (String[]) nameList.toArray(new String[0]);
		}
		return new String[0];
	}
}
