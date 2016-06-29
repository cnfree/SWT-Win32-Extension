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

import java.util.Iterator;

import org.sf.feeling.swt.win32.internal.extension.Extension;

/**
 * The instance of this class is used to access a registry key in the system
 * registry.
 * 
 * @author <a href="mailto:cnfree2000@hotmail.com">cnfree</a>
 * 
 */
public class RegistryKey
{
	static
	{
		Extension.loadNativeLibrary("swt-extension-win32");
	}

	private RootKey root = RootKey.HKEY_CURRENT_USER;

	private String path = "";

	/**
	 * Constructs a new <code>RegistryKey</code> referencing the root path of
	 * the <code>RootKey.HKEY_CURRENT_USER</code> root key.
	 */
	public RegistryKey()
	{
	} // RegistryKey()

	/**
	 * Constructs a new <code>RegistryKey</code> referencing the root path of
	 * the specified <code>RootKey</code>.
	 * 
	 * @param root
	 *            the root key of this <code>RegistryKey</code>
	 */
	public RegistryKey(RootKey root)
	{
		this.root = root;
	} // RegistryKey()

	/**
	 * Constructs a new <code>RegistryKey</code> referencing the specified path
	 * of the <code>RootKey.HKEY_CURRENT_USER</code> root key.
	 * 
	 * @param path
	 *            the path of this <code>RegistryKey</code>
	 */
	public RegistryKey(String path)
	{
		this.path = path;
	} // RegistryKey()

	/**
	 * Constructs a new <code>RegistryKey</code> referencing the specified path
	 * of the specified <code>RootKey</code>.
	 * 
	 * @param root
	 *            the root key of this <code>RegistryKey</code>
	 * @param path
	 *            the path of this <code>RegistryKey</code>
	 */
	public RegistryKey(RootKey root, String path)
	{
		this.root = root;
		this.path = path;
	} // RegistryKey

	/**
	 * Returns this <code>RegistryKey</code>'s root key.
	 * 
	 * @return the root key
	 */
	public RootKey getRootKey()
	{
		return root;
	} // getRootKey()

	/**
	 * Returns this <code>RegistryKey</code>'s <code>path</code>.
	 * 
	 * @return the <code>path</code>
	 */
	public String getPath()
	{
		return path;
	} // getPath()

	/**
	 * Returns this <code>RegistryKey</code>'s <code>name</code>. The name is
	 * last portion of a <code>path</code>. For example, for the
	 * <code>path</code> <i>Software\\BEQ Technologies\\TestKey</i>, the
	 * <code>name</code> would be <i>TestKey</i>.
	 * 
	 * @return the <code>name</code>
	 */
	public String getName()
	{
		return path.substring(1 + path.lastIndexOf("\\"));
	} // getName()

	/**
	 * Tests if this registry key, as defined by the <code>RootKey</code> and
	 * <code>path</code>, currently exists in the system registry.
	 * 
	 * @return <code>true</code> if this key exists in the registry
	 */
	public boolean exists()
	{
		return ExistsKey();
	};

	private native boolean ExistsKey();

	/**
	 * Creates this registry key in the system registry.
	 * 
	 * @throws RegistryException
	 *             if this registry key already exists in the registry
	 */
	public void create()
	{
		CreateKey();
	};

	private native void CreateKey();

	/**
	 * Creates the specified subkey.
	 * 
	 * @param name
	 *            name the subkey to be created
	 * 
	 * @return the created subkey
	 * 
	 * @throws RegistryException
	 *             if this registry key does not exist in the registry, or if
	 *             the specified subkey name already exists.
	 */
	public RegistryKey createSubkey(String name)
	{
		RegistryKey r = new RegistryKey(this.root, this.path + "\\" + name);
		r.create();
		return r;
	} // createSubkey()

	/**
	 * Deletes this registry key and any subkeys or values from the system
	 * registry.
	 * 
	 * <p>
	 * <b>WARNING: This method can potentially cause catastrophic damage to the
	 * system registry. USE WITH EXTREME CARE!</b>
	 * 
	 * @throws RegistryException
	 *             if this registry key does not already exist in the registry
	 */
	public void delete()
	{
		DeleteKey();
	};

	private native void DeleteKey();

	/**
	 * Tests if this registry key possesses subkeys. Use <code>subkeys</code> to
	 * retrieve an iterator for available subkeys.
	 * 
	 * @return <code>true</code> if this registry key possesses subkeys
	 * 
	 * @throws RegistryException
	 *             if this registry key does not already exist in the registry
	 */
	public boolean hasSubkeys()
	{
		return HasSubkeys();
	};

	private native boolean HasSubkeys();

	/**
	 * Tests if this registry key possesses the specified subkey.
	 * 
	 * @param name
	 *            the subkey to test for
	 * 
	 * @return <code>true</code> if this registry key possesses subkeys
	 * 
	 * @throws RegistryException
	 *             if this registry key does not already exist in the registry
	 */
	public boolean hasSubkey(String name)
	{
		RegistryKey r = new RegistryKey(this.root, this.path + "\\" + name);
		return r.exists();
	} // hasSubkey()

	/**
	 * Returns an iterator for available subkeys.
	 * 
	 * @return an iterator of <code>RegistryKey</code>'s
	 * 
	 * @throws RegistryException
	 *             if this registry key does not already exist in the registry
	 */
	public Iterator subkeys()
	{
		return new KeyIterator(this);
	} // subkeys()

	/**
	 * Returns the specified subkey.
	 * 
	 * @param name
	 *            the subkey to get for
	 * @return a <code>RegistryKey</code>
	 * 
	 * @throws RegistryException
	 *             if this registry key does not already exist in the registry
	 */
	public RegistryKey getSubkey(String name)
	{
		if (hasSubkey(name))
		{
			Iterator iter = subkeys();
			while (iter.hasNext())
			{
				RegistryKey subkey = (RegistryKey) iter.next();
				if (name.equals(subkey.getName())) return subkey;
			}
		}
		return null;
	}

	/**
	 * Returns an iterator for available values.
	 * 
	 * @return an iterator of <code>RegistryValue</code>'s
	 * 
	 * @throws RegistryException
	 *             if this registry key does not already exist in the registry
	 */
	public Iterator values()
	{
		return new ValueIterator(this);
	} // values()

	/**
	 * Tests if this registry key possess the specified value.
	 * 
	 * @param name
	 *            the name of the value to be tested
	 * 
	 * @return <code>true</code> if this registry keys possess the specified
	 *         value
	 * 
	 * @throws RegistryException
	 *             if this registry key does not already exist in the registry
	 */
	public boolean hasValue(String name)
	{
		return HasKeyValue(name);
	};

	private native boolean HasKeyValue(String name);

	/**
	 * Tests if this registry key possess any values.
	 * 
	 * @return <code>true</code> if this registry keys possess any values
	 * 
	 * @throws RegistryException
	 *             if this registry key does not already exist in the registry
	 */
	public boolean hasValues()
	{
		return HasKeyValues();
	};

	private native boolean HasKeyValues();

	/**
	 * Returns a <code>RegistryValue</code> representing the specified value.
	 * 
	 * @param name
	 *            the name of the value to be retreived
	 * 
	 * @return the RegistryValue of the specified value name
	 * 
	 * @throws RegistryException
	 *             if this registry key does not already exist in the registry,
	 *             or if this registry key does not possess the specified value
	 */
	public RegistryValue getValue(String name)
	{
		return GetKeyValue(name);
	};

	private native RegistryValue GetKeyValue(String name);

	/**
	 * Sets the properties of a registry value according to the properties of
	 * the specified <code>RegistryValue</code>. If the specified value exists,
	 * it will be modified; if not, it will be created.
	 * 
	 * @param value
	 *            the <code>RegistryValue</code>
	 * 
	 * @throws RegistryException
	 *             if this registry key does not already exist in the registry
	 */
	public void setValue(RegistryValue value)
	{
		SetKeyValue(value);
	};

	private native void SetKeyValue(RegistryValue value);

	/**
	 * Deletes the specified value from this registry key.
	 * 
	 * @param name
	 *            the name of the value to be deleted
	 * 
	 * @throws RegistryException
	 *             if this registry key does not already exist in the registry,
	 *             or if the specified value is not possess by this registry key
	 */
	public void deleteValue(String name)
	{
		DeleteKeyValue(name);
	};

	private native void DeleteKeyValue(String name);

	/**
	 * Returns a string representation of this <code>RegistryKey</code>.
	 * 
	 * @return a string representation of this <code>RegistryKey</code>
	 */
	public String toString()
	{
		return (root.toString() + "\\" + this.path);
	} // toString()
} // RegistryKey
