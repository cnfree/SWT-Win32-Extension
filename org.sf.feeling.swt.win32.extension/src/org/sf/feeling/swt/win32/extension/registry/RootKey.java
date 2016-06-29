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
package org.sf.feeling.swt.win32.extension.registry;

/**
 * This class is a utility class, and provide the root registry keies of system.
 * 
 * @author <a href="mailto:cnfree2000@hotmail.com">cnfree</a>
 * 
 */
public final class RootKey {
	/** The root key name displayed by <code>toString</code> */
	private final String name;

	/** The root key value used by the native Windows registry functions */
	private final int value;

	/**
	 * Constructs a new root key with the specified display name and value.
	 * 
	 * @param name -
	 *            the display name of the root key
	 * @param value -
	 *            the integer value of the root key
	 */
	private RootKey(String name, int value) {
		this.name = name;
		this.value = value;
	} // RootKey()

	/**
	 * Returns the integer value (used by the native Windows registry functions)
	 * of the root key.
	 * 
	 * @return the integer value of the root key.
	 */
	protected int getValue() {
		return this.value;
	} // getValue()

	/**
	 * Returns the display name of the root key.
	 * 
	 * @return a string representation of the root key.
	 */
	public String toString() {
		return this.name;
	} // toString()

	/**
	 * Registry entries subordinate to this key define types (or classes) of
	 * documents and the properties associated with those types. Shell and COM
	 * applications use the information stored under this key. File viewers and
	 * user longerface extensions store their OLE class identifiers in
	 * <code>HKEY_CLASSES_ROOT</code>, and in-process servers are registered
	 * in this key.
	 */
	public static final RootKey HKEY_CLASSES_ROOT = new RootKey(
			"HKEY_CLASSES_ROOT", 0x80000000);

	/**
	 * Registry entries subordinate to this key define the preferences of the
	 * current user. These preferences include the settings of environment
	 * variables, data about program groups, colors, printers, network
	 * connections, and application preferences.
	 */
	public static final RootKey HKEY_CURRENT_USER = new RootKey(
			"HKEY_CURRENT_USER", 0x80000001);

	/**
	 * Registry entries subordinate to this key define the physical state of the
	 * computer, including data about the bus type, system memory, and installed
	 * hardware and software. It contains subkeys that hold current
	 * configuration data, including Plug and Play information (the Enum branch,
	 * which includes a complete list of all hardware that has ever been on the
	 * system), network logon preferences, network security information,
	 * software-related information (such as server names and the location of
	 * the server), and other system information.
	 */
	public static final RootKey HKEY_LOCAL_MACHINE = new RootKey(
			"HKEY_LOCAL_MACHINE", 0x80000002);

	/**
	 * Registry entries subordinate to this key define the default user
	 * configuration for new users on the local computer and the user
	 * configuration for the current user.
	 */
	public static final RootKey HKEY_USERS = new RootKey("HKEY_USERS",
			0x80000003);

	/**
	 * Registry entries subordinate to this key contain information about the
	 * current hardware profile of the local computer system. The information
	 * under <code>HKEY_CURRENT_CONFIG</code> describes only the differences
	 * between the current hardware configuration and the standard
	 * configuration.
	 */
	public static final RootKey HKEY_CURRENT_CONFIG = new RootKey(
			"HKEY_CURRENT_CONFIG", 0x80000005);

	/**
	 * Registry entries subordinate to this key allow you to access performance
	 * data. The data is not actually stored in the registry; the registry
	 * functions cause the system to collect the data from its source.
	 * 
	 * <p>
	 * NOTE: HKEY_PERFORMANCE_DATA is defined only for Windows NT 4.0, Windows
	 * 2000, and Windows XP operating systems.
	 */
	public static final RootKey HKEY_PERFORMANCE_DATA = new RootKey(
			"HKEY_PERFORMANCE_DATA", 0x80000004);

	/**
	 * Registry entries subordinate to this key allow you to collect performance
	 * data.
	 * 
	 * <p>
	 * NOTE: HKEY_DYN_DATA is defined only for Windows 95, Windows 98, and
	 * Windows ME operating systems.
	 */
	public static final RootKey HKEY_DYN_DATA = new RootKey("HKEY_DYN_DATA",
			0x80000006);
} // RootKey
