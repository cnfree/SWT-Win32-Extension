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

/**
 * <code>MutexException</code> signals that a mutex operation has failed.
 * 
 * @see java.lang.Exception
 * 
 */
public class MutexException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8814886984523039211L;

	/**
	 * Constructs a new <code>MutexException</code> with no detail message.
	 */
	public MutexException() {
	}

	/**
	 * Constructs a new <code>MutexException</code> with the specified detail
	 * message.
	 * 
	 * @param msg
	 *            the detail message.
	 */
	public MutexException(String msg) {
		super(msg);
	}
}
