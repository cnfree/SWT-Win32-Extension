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
import java.util.NoSuchElementException;

/**
 * The instance of this class is used to enumerate values from the specified
 * <code>RegistryKey</code>.
 * 
 * @author <a href="mailto:cnfree2000@hotmail.com">cnfree</a>
 * 
 */
public class ValueIterator implements Iterator {
	private RegistryKey key = null;

	// the following variables are used by the native library
	private int index = -1;
	private int hkey = 0;
	private int maxsize;
	private int count;

	/**
	 * Constructs a new <code>ValueIterator</code> to enumerate values from
	 * the specified <code>RegistryKey</code>.
	 * 
	 * @param key
	 *            The registry key from which to enumerate values.
	 */
	public ValueIterator(RegistryKey key) {
		this.key = key;
	} // ValueIterator()

	/**
	 * Returns <code>true</code> if the iteration contains more RegistryValue
	 * elements. (In other words, returns <code>true</code> if
	 * <code>next</code> would return a RegistryValue element rather than
	 * throwing an exception.)
	 * 
	 * @return <code>true</code> if the iterator has more RegistryValue
	 *         elements.
	 */
	public boolean hasNext() {
		return KeyValueIteratorHasNext();
	};

	public native boolean KeyValueIteratorHasNext();

	/**
	 * Returns the next RegistryValue element in the iteration.
	 * 
	 * @return the next RegistryValue element in the iteration.
	 * 
	 * @throws NoSuchElementException -
	 *             if the iteration contains no more RegistryValue elements.
	 */
	public Object next() {
		return key.getValue(getNext());
		// return new RegistryKey(key.getRootKey(), key.getPath() + "\\" +
		// (String)getNext());
	} // next()

	/**
	 * Returns the next RegistryValue element in the iteration.
	 * 
	 * @return the name of the next RegistryValue element in the iteration.
	 * 
	 * @throws NoSuchElementException -
	 *             if the iteration contains no more RegistryValue elements.
	 */
	private String getNext() {
		return KeyValueIteratorGetNext();
	};

	private native String KeyValueIteratorGetNext();

	/**
	 * The optional <code>remove</code> operation is not supported by this
	 * Iterator.
	 * 
	 * @throws UnsupportedOperationException -
	 *             if the remove operation is not supported by this Iterator.
	 */
	public void remove() {
		throw new UnsupportedOperationException(
				"The remove operation is not supported by this Iterator");
	} // remove()
} // ValueIterator
