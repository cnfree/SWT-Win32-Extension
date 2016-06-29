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

import org.sf.feeling.swt.win32.extension.example.page.CategoryPage;
import org.sf.feeling.swt.win32.extension.example.page.ICategoryPage;





public class CategoryProvider implements ICategoryProvider
{

	private ICategoryPage[] categories;

	public CategoryProvider( String category, Class pageClass )
	{
		this( new String[]{
			category
		}, new Class[]{
			pageClass
		} );
	}

	public CategoryProvider( String[] categories, Class[] pageClasses )
	{
		this.categories = new ICategoryPage[categories.length];
		for ( int i = 0; i < categories.length; i++ )
		{
			this.categories[i] = new CategoryPage( categories[i],pageClasses[i] );
		}
	}

	public ICategoryPage[] getCategories( )
	{
		return categories;
	}
}
