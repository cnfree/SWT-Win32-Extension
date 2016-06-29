/*******************************************************************************
 * Copyright (c) 2011 cnfree.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  cnfree  - initial API and implementation
 *******************************************************************************/
package org.sf.feeling.swt.win32.extension.jna;

public class Machine {
	public static final int SIZE;
	
	static {
		//TODO find the current machine here to see if we should use 64 bits pointers
		SIZE = 1;
		//On AMD64 / Windows64 / Linux 64 set SIZE to 2
	}
}
