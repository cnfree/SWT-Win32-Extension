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

package org.sf.feeling.swt.win32.extension.example.widget;

import org.eclipse.swt.graphics.Image;

public class Tab
{

	private boolean indented = false;
	private Image image = null;
	private String text = null;

	public Image getImage( )
	{
		// TODO Auto-generated method stub
		return image;
	}

	public String getText( )
	{
		// TODO Auto-generated method stub
		return text;
	}

	public boolean isIndented( )
	{
		// TODO Auto-generated method stub
		return indented;
	}

	public void setImage( Image image )
	{
		this.image = image;
	}

	public void setIndented( boolean indented )
	{
		this.indented = indented;
	}

	public void setText( String text )
	{
		this.text = text;
	}

}
