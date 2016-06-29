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

public class CategoryPage implements ICategoryPage
{

	private String displayLabel;
	private Class pageClass;

	public CategoryPage( String displayLabel ,Class pageClass )
	{
		this.displayLabel = displayLabel;
		this.pageClass = pageClass;
	}

	public String getDisplayLabel( )
	{
		return displayLabel;
	}

	public TabPage createPage(  )
	{
		try
		{
			return (TabPage) pageClass.getConstructor( null )
					.newInstance( null );
		}
		catch ( Exception e )
		{
			e.printStackTrace();
			return null;
		}
	}
}
