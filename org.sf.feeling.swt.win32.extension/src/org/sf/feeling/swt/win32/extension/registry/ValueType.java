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
 * The instance of this class represents the type of system registry key.
 * 
 * @author <a href="mailto:cnfree2000@hotmail.com">cnfree</a>
 * 
 */
public class ValueType {
	/** The value type name displayed by <code>toString</code> */
	private final String name;

	/**
	 * The value type integer value used by the native Windows registry
	 * functions
	 */
	private final int value;

	/**
	 * Constructs a new value type with the specified display name and value.
	 * 
	 * @param name -
	 *            the display name of the value type
	 * @param value -
	 *            the integer value of the value type
	 */
	private ValueType(String name, int value) {
		this.name = name;
		this.value = value;
	} // RootKey()

	/**
	 * Returns the integer value (used by the native Windows registry functions)
	 * of the value type.
	 * 
	 * @return the integer value of the value type.
	 */
	protected int getValue() {
		return this.value;
	} // getValue()

	/**
	 * Returns the display name of the value type.
	 * 
	 * @return a string representation of the value type.
	 */
	public String toString() {
		return this.name;
	} // toString()

	/**
	 * The <code>REG_NONE</code> data type represents data with no defined
	 * type.
	 */
	public static final ValueType REG_NONE = new ValueType("REG_NONE", 0);

	/**
	 * The <code>REG_SZ</code> data type represents a null-terminated string.
	 */
	public static final ValueType REG_SZ = new ValueType("REG_SZ", 1);

	/**
	 * The <code>REG_EXPAND_SZ</code> data type represents a null-terminated
	 * string that contains unexpanded references to environment variables (for
	 * example, "%PATH%").
	 */
	public static final ValueType REG_EXPAND_SZ = new ValueType(
			"REG_EXPAND_SZ", 2);

	/**
	 * The <code>REG_BINARY</code> data type represents binary data in any
	 * form.
	 */
	public static final ValueType REG_BINARY = new ValueType("REG_BINARY", 3);

	/**
	 * The <code>REG_DWORD</code> data type represents a 32-bit number.
	 */
	public static final ValueType REG_DWORD = new ValueType("REG_DWORD", 4);

	/**
	 * The <code>REG_DWORD_LITTLE_ENDIAN</code> data type represents a 32-bit
	 * number in little-endian format.
	 * 
	 * <p>
	 * In little-endian format, a multi-byte value is stored in memory from the
	 * lowest byte (the "little end") to the highest byte. For example, the
	 * value 0x12345678 is stored as (0x78 0x56 0x34 0x12) in little-endian
	 * format.
	 */
	public static final ValueType REG_DWORD_LITTLE_ENDIAN = new ValueType(
			"REG_DWORD_BIG_ENDIAN", 5);

	/**
	 * The <code>REG_DWORD_BIG_ENDIAN</code> data type represents a 32-bit
	 * number in little-endian format (this is equivalent to
	 * <code>REG_DWORD</code>).
	 * 
	 * In big-endian format, a multi-byte value is stored in memory from the
	 * highest byte (the "big end") to the lowest byte. For example, the value
	 * 0x12345678 is stored as (0x12 0x34 0x56 0x78) in big-endian format.
	 */
	public static final ValueType REG_DWORD_BIG_ENDIAN = new ValueType(
			"REG_DWORD_LITTLE_ENDIAN", 4);

	/**
	 * The <code>REG_MULTI_SZ</code> data type represents an array of
	 * null-terminated strings, terminated by two null characters.
	 */
	public static final ValueType REG_MULTI_SZ = new ValueType("REG_MULTI_SZ",
			7);
} // ValueType
