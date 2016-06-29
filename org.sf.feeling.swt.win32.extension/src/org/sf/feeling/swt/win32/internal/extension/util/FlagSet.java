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
package org.sf.feeling.swt.win32.internal.extension.util;

import org.sf.feeling.swt.win32.extension.hook.data.KeyboardHookData;

/**
 * The instance of this class is used for computing {@link KeyboardHookData}.
 * 
 * @author <a href="mailto:cnfree2000@hotmail.com">cnfree</a>
 */
public class FlagSet {

	public FlagSet() {
		flag = 0;
	}

	public FlagSet(int l) {
		flag = 0;
		flag = l;
	}

	public void and(int l) {
		flag &= l;
	}

	public void or(int l) {
		flag |= l;
	}

	public void add(int l) {
		or(l);
	}

	public void remove(int l) {
		flag &= ~l;
	}

	public boolean contains(int l) {
		return (flag & l) == l;
	}

	public void clear() {
		flag = 0;
	}

	public long getFlags() {
		return flag;
	}

	public void setupFlag(int l, boolean flag) {
		if (flag)
			add(l);
		else
			remove(l);
	}

	public String toString() {
		StringBuffer stringbuffer = (new StringBuffer("FlagSet: [")).append(
				Long.toBinaryString(getFlags())).append(']');
		return stringbuffer.toString();
	}

	public int getBits(int i, int j) {
		if (0 <= i && i <= j && j <= 64) {
			long l = getMask(i, j);
			long l1 = (flag & l) >> i;
			return (int) l1;
		} else {
			throw new RuntimeException("Incorrect range of bits.");
		}
	}

	public boolean getBit(int i) {
		return (flag & (long) (1 << i)) != 0L;
	}

	public void setBit(int i, boolean flag) {
		if (flag)
			this.flag = this.flag | (1 << i);
		else
			this.flag = this.flag & (~(1 << i));
	}

	public void setBits(int i, int j, int l) {
		long l1 = getMask(i, j);
		long l2 = l << i;
		flag |= l2 & l1;
	}

	public static long getMask(int i, int j) {
		int k = (j - i) + 1;
		long l;
		for (l = 1L; k-- > 0; l *= 2L)
			;
		l--;
		return l << i;
	}

	protected int flag;
}