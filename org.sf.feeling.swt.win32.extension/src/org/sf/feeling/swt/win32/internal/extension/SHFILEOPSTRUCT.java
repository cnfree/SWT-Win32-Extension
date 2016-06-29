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
package org.sf.feeling.swt.win32.internal.extension;

public abstract class SHFILEOPSTRUCT extends STRUCT{

	private static final long serialVersionUID = 7198934472422400506L;
	public int hwnd;
    public int wFunc;
    public int pFrom;
    public int pTo;
    public int fFlags;
    public int fAnyOperationsAborted;
    public int hNameMappings;
    public int lpszProgressTitle;
    public static final int sizeof = 32;
    
}
