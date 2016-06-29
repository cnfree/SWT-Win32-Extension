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

package org.sf.feeling.swt.win32.extension.example.page;

public interface ICategoryPage
{

	/**
	 * Returns the display label for the category
	 * 
	 * @return the display label
	 */
	public String getDisplayLabel( );

	/**
	 * Creates the page control with the given parent and style
	 * 
	 * @param parent
	 *            the parent of the page control
	 * @param style
	 *            the style of the page control
	 * 
	 * @return the page control created
	 */

	public TabPage createPage( );

}