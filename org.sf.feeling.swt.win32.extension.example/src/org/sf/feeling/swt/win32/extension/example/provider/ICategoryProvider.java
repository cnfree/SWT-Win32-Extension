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
package org.sf.feeling.swt.win32.extension.example.provider;

import org.sf.feeling.swt.win32.extension.example.page.ICategoryPage;


/**
 * The instance which provides category pages element.
 */
public interface ICategoryProvider
{

	/**
	 * Returns the category pages
	 * 
	 * @return the category pages
	 */
	public ICategoryPage[] getCategories( );
}