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

public abstract class SHFILEINFO extends STRUCT{

	private static final long serialVersionUID = 5222637777546101124L;
	public int hIcon;
	public int iIcon;
	public int dwAttributes;
	public final static int sizeof = Extension.IsUnicode ? 692 : 352;
}
