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
package org.sf.feeling.swt.win32.internal.extension.widgets;


public interface IContainer {

	public final static int BORDER_NW = 0;
	public final static int BORDER_N = 1;
	public final static int BORDER_NE = 2;
	public final static int BORDER_W = 3;
	public final static int BORDER_E = 4;
	public final static int BORDER_SW = 5;
	public final static int BORDER_S = 6;
	public final static int BORDER_SE = 7;

	public abstract ISkinable getSkin(int borderId);

	public abstract void setSkin(int borderId, ISkinable drawable);

	public abstract ISkinable getSkin();

	public abstract void setSkin(ISkinable drawable);

}