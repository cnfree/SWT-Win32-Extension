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
package org.sf.feeling.swt.win32.extension.hook.interceptor;

/**
 * Instances of this class represent the result of hook intercepting.
 * 
 * @author <a href="mailto:cnfree2000@hotmail.com">cnfree</a>
 */
public class InterceptorFlag {

	/**
	 * Specific flag TRUE represents that the interceptor allow the hookproc to
	 * execute normally.
	 */
	public final static InterceptorFlag TRUE = new InterceptorFlag(1342302);

	/**
	 * Specific flag FALSE represents that the interceptor doesn't allow the
	 * hookproc to execute normally.
	 */
	public final static InterceptorFlag FALSE = new InterceptorFlag(-1342301);

	private final int value;

	/**
	 * Constructs a new InterceptorFlag with the given value.
	 * 
	 * @param value
	 *            the value of the flag
	 */
	public InterceptorFlag(int value) {
		this.value = value;
	}

	/**
	 * Get the value of flag.
	 * 
	 * @return flag value
	 */
	public int getValue() {
		return value;
	}

	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (!(obj instanceof InterceptorFlag))
			return false;
		InterceptorFlag flag = (InterceptorFlag) obj;
		return flag.getValue() == value;
	}

	public int hashCode() {
		return 13 * value + 7;
	}

	/**
	 * Check if the value is user custom defining.
	 * 
	 * @return if the value is user custom defining
	 */
	public boolean isCustom() {
		return !(TRUE.equals(this) || FALSE.equals(this));
	}
}
