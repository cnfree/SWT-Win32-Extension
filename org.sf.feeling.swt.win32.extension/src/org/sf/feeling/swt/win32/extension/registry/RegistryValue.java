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
 * The instance of this class represents the data of specified registry key.
 * 
 * @author <a href="mailto:cnfree2000@hotmail.com">cnfree</a>
 * 
 */
public class RegistryValue {
	private String name = "";
	private ValueType type = ValueType.REG_SZ;
	private Object data = null;

	/**
	 * Constructs a new, empty, <code>RegistryValue</code>.
	 */
	public RegistryValue() {
	} // RegistryValue()

	/**
	 * Constructs a new <code>RegistryValue</code> with the specified data.
	 * The type is defaulted to <code>ValueType.REG_SZ</code>. The
	 * <code>name</code> defaults to ""; the default value name.
	 * 
	 * @param data
	 *            the <code>RegistryValue</code>'s data
	 */
	public RegistryValue(Object data) {
		this.data = data;
	} // RegistryValue()

	/**
	 * Constructs a new <code>RegistryValue</code> with the specified name and
	 * data. The type is defaulted to <code>ValueType.REG_SZ</code>.
	 * 
	 * @param name
	 *            the <code>RegistryValue</code>'s name
	 * @param data
	 *            the <code>RegistryValue</code>'s data
	 */
	public RegistryValue(String name, Object data) {
		this.name = name;
		this.data = data;
	} // RegistryValue()

	/**
	 * Constructs a new <code>RegistryValue</code> with the specified name,
	 * type, and data.
	 * 
	 * @param name
	 *            the <code>RegistryValue</code>'s name
	 * @param type
	 *            the <code>RegistryValue</code>'s type
	 * @param data
	 *            the <code>RegistryValue</code>'s data
	 */
	public RegistryValue(String name, ValueType type, Object data) {
		this.name = name;
		this.type = type;
		this.data = data;
	} // RegistryValue()

	/**
	 * Constructs a new <code>RegistryValue</code> with the specified name,
	 * and data. The type is <code>ValueType.REG_DWORD</code>.
	 * 
	 * @param name
	 *            the <code>RegistryValue</code>'s name
	 * @param data
	 *            the <code>RegistryValue</code>'s data
	 */
	public RegistryValue(String name, boolean data) {
		this(name, ValueType.REG_DWORD, new Boolean(data));
	} // RegistryValue()

	/**
	 * Constructs a new <code>RegistryValue</code> with the specified name,
	 * and data. The type is <code>ValueType.REG_BINARY</code>.
	 * 
	 * @param name
	 *            the <code>RegistryValue</code>'s name
	 * @param data
	 *            the <code>RegistryValue</code>'s data
	 */
	public RegistryValue(String name, byte data) {
		this(name, ValueType.REG_BINARY, new Byte(data));
	} // RegistryValue()

	/**
	 * Constructs a new <code>RegistryValue</code> with the specified name,
	 * and data. The type is <code>ValueType.REG_DWORD</code>.
	 * 
	 * @param name
	 *            the <code>RegistryValue</code>'s name
	 * @param data
	 *            the <code>RegistryValue</code>'s data
	 */
	public RegistryValue(String name, int data) {
		this(name, ValueType.REG_DWORD, new Integer(data));
	} // RegistryValue()

	/**
	 * Constructs a new <code>RegistryValue</code> with the specified name,
	 * and data. The type is <code>ValueType.REG_DWORD</code>.
	 * 
	 * @param name
	 *            the <code>RegistryValue</code>'s name
	 * @param data
	 *            the <code>RegistryValue</code>'s data
	 */
	public RegistryValue(String name, long data) {
		this(name, ValueType.REG_DWORD, new Long(data));
	} // RegistryValue()

	/**
	 * Constructs a new <code>RegistryValue</code> with the specified name,
	 * and data. The type is <code>ValueType.REG_BINARY</code>.
	 * 
	 * @param name
	 *            the <code>RegistryValue</code>'s name
	 * @param data
	 *            the <code>RegistryValue</code>'s data
	 */
	public RegistryValue(String name, float data) {
		this(name, ValueType.REG_BINARY, new Float(data));
	} // RegistryValue()

	/**
	 * Constructs a new <code>RegistryValue</code> with the specified name,
	 * and data. The type is <code>ValueType.REG_BINARY</code>.
	 * 
	 * @param name
	 *            the <code>RegistryValue</code>'s name
	 * @param data
	 *            the <code>RegistryValue</code>'s data
	 */
	public RegistryValue(String name, double data) {
		this(name, ValueType.REG_BINARY, new Double(data));
	} // RegistryValue()

	/**
	 * Returns this <code>RegistryValue</code>'s name.
	 * 
	 * @return the <code>RegistryValue</code>'s name
	 */
	public String getName() {
		return this.name;
	} // getName()

	/**
	 * Sets this <code>RegistryValue</code>'s name.
	 * 
	 * @param name
	 *            the <code>RegistryValue</code>'s name
	 */
	public void setName(String name) {
		this.name = name;
	} // setName()

	/**
	 * Returns this <code>RegistryValue</code>'s <code>ValueType</code>.
	 * 
	 * @return the <code>RegistryValue</code>'s type
	 */
	public ValueType getType() {
		return this.type;
	} // getType()

	/**
	 * Sets this <code>RegistryValue</code>'s <code>ValueType</code>.
	 * 
	 * @param type
	 *            the <code>RegistryValue</code>'s type
	 */
	public void setType(ValueType type) {
		this.type = type;
	} // setType()

	/**
	 * Returns this <code>RegistryValue</code>'s data.
	 * 
	 * @return the <code>RegistryValue</code>'s data
	 */
	public Object getData() {
		return this.data;
	} // getData()

	/**
	 * Sets this <code>RegistryValue</code>'s data.
	 * 
	 * @param data
	 *            the <code>RegistryValue</code>'s data
	 */
	public void setData(Object data) {
		this.data = data;
	} // setData()

	/**
	 * Sets this <code>RegistryValue</code>'s data.
	 * 
	 * @param data
	 *            the <code>RegistryValue</code>'s data
	 */
	public void setData(byte data) {
		setData(new Byte(data));
	} // setData()

	/**
	 * Sets this <code>RegistryValue</code>'s data.
	 * 
	 * @param data
	 *            the <code>RegistryValue</code>'s data
	 */
	public void setData(boolean data) {
		setData(new Boolean(data));
	} // setData()

	/**
	 * Sets this <code>RegistryValue</code>'s data.
	 * 
	 * @param data
	 *            the <code>RegistryValue</code>'s data
	 */
	public void setData(int data) {
		setData(new Integer(data));
	} // setData()

	/**
	 * Sets this <code>RegistryValue</code>'s data.
	 * 
	 * @param data
	 *            the <code>RegistryValue</code>'s data
	 */
	public void setData(long data) {
		setData(new Long(data));
	} // setData()

	/**
	 * Sets this <code>RegistryValue</code>'s data.
	 * 
	 * @param data
	 *            the <code>RegistryValue</code>'s data
	 */
	public void setData(float data) {
		setData(new Float(data));
	} // setData()

	/**
	 * Sets this <code>RegistryValue</code>'s data.
	 * 
	 * @param data
	 *            the <code>RegistryValue</code>'s data
	 */
	public void setData(double data) {
		setData(new Double(data));
	} // setData()

	/**
	 * Returns a string representation of this <code>RegistryValue</code>.
	 * 
	 * @return the <code>RegistryValue</code> as a String
	 * 
	 * @throws NullPointerException
	 *             if the <code>name</code> or <code>data</code> properties
	 *             are null.
	 */
	public String toString() {
		if (this.name == null || this.data == null) {
			throw new NullPointerException("Neither name not data may be null");
		} // if

		String dataval = "<no data>";

		if (this.type == ValueType.REG_SZ
				|| this.type == ValueType.REG_EXPAND_SZ
				|| this.type == ValueType.REG_MULTI_SZ) {
			dataval = data.toString();
		} // if
		else if (this.type == ValueType.REG_DWORD
				|| this.type == ValueType.REG_DWORD_LITTLE_ENDIAN
				|| this.type == ValueType.REG_DWORD_BIG_ENDIAN) {
			dataval = ((Integer) data).toString();
		} // else if
		else if (this.type == ValueType.REG_NONE
				|| this.type == ValueType.REG_BINARY) {
			StringBuffer sb = new StringBuffer();
			byte[] b = (byte[]) data;

			// data.toString().getBytes();
			for (int index = 0; index < b.length; index++) {
				sb.append(" " + Byte.toString(b[index]));
			} // for
			dataval = sb.toString();
		} // else if

		return (this.name + ":" + type.toString() + ":" + dataval);
	} // toString()
} // RegistryValue
