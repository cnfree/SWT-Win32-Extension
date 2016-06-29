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
 * The instance of this class is used to enumerate subkeys from the specified
 * <code>RegistryKey</code>.
 * 
 * @author <a href="mailto:cnfree2000@hotmail.com">cnfree</a>
 * 
 */
public class KeyIterator implements Iterator {
	private RegistryKey key = null;

	// the following variables are used by the native library
	private int index = -1;
	private int hkey = 0;
	private int maxsize;
	private int count;

	/**
	 * Constructs a new <code>KeyIterator</code> to enumerate subkeys from the
	 * specified <code>RegistryKey</code>.
	 * 
	 * @param key
	 *            The registry key from which to enumerate subkeys.
	 */
	public KeyIterator(RegistryKey key) {
		this.key = key;
	}

	/**
	 * Returns <code>true</code> if the iteration contains more
	 * <code>RegistryKey</code> elements. (In other words, returns
	 * <code>true</code> if <code>next</code> would return a
	 * <code>RegistryKey</code> element rather than throwing an exception).
	 * 
	 * @return <code>true</code> if the iterator has more
	 *         <code>RegistryKey</code> elements.
	 */
	public native boolean KeyIteratorHasNext();

	public boolean hasNext() {
		return KeyIteratorHasNext();
	};

	/**
	 * Returns the next <code>RegistryKey</code> element in the iteration.
	 * 
	 * @return the next <code>RegistryKey</code> element in the iteration.
	 * 
	 * @throws NoSuchElementException
	 *             if the iteration contains no more <code>RegistryKey</code>
	 *             elements.
	 */
	public Object next() {
		if (key.getPath().length() > 0)
			return new RegistryKey(key.getRootKey(), key.getPath() + "\\"
					+ getNext());
		else
			return new RegistryKey(key.getRootKey(), getNext());
	} // next()

	/**
	 * Returns the next <code>RegistryKey</code> element in the iteration.
	 * 
	 * @return the name of the next <code>RegistryKey</code> element in the
	 *         iteration.
	 * 
	 * @throws NoSuchElementException
	 *             if the iteration contains no more <code>RegistryKey</code>
	 *             elements.
	 */
	private native String KeyIteratorGetNext();

	private String getNext() {
		return KeyIteratorGetNext();
	}

	/**
	 * The optional <code>remove</code> operation is not supported by this
	 * Iterator.
	 * 
	 * @throws java.lang.UnsupportedOperationException
	 *             if the remove operation is not supported by this Iterator.
	 */
	public void remove() {
		throw new UnsupportedOperationException(
				"The remove operation is not supported by this Iterator");
	} // remove()
} // KeyIterator
