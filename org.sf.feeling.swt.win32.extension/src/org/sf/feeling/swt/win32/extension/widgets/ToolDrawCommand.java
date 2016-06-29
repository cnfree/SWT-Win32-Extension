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
package org.sf.feeling.swt.win32.extension.widgets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Rectangle;

class ToolDrawCommand
{
	protected int row;

	protected int col;

	protected boolean enabled;

	protected boolean hasMenu;

	protected boolean isChevron;

	protected boolean isSeparator;

	protected Rectangle drawRect;

	protected Rectangle selectRect;

	protected CToolItem toolItem;

	protected boolean topBorder;

	protected boolean bottomBorder;

	public ToolDrawCommand(Rectangle drawRect)
	{
		row = -1;
		col = -1;
		enabled = true;
		hasMenu = false;
		isSeparator = false;
		this.drawRect = drawRect;
		selectRect = drawRect;
		toolItem = null;
		isChevron = true;
		topBorder = false;
		bottomBorder = false;
	}

	public ToolDrawCommand(Rectangle drawRect, boolean expansion)
	{
		row = -1;
		col = -1;
		enabled = true;
		hasMenu = false;
		isSeparator = !expansion;
		this.drawRect = drawRect;
		selectRect = drawRect;
		isChevron = false;
		toolItem = null;
		topBorder = false;
		bottomBorder = false;
	}

	public ToolDrawCommand(CToolItem item, Rectangle drawRect)
	{
		InternalConstruct(item, drawRect, drawRect, -1, -1);
	}

	public ToolDrawCommand(CToolItem item, Rectangle drawRect, Rectangle selectRect)
	{
		InternalConstruct(item, drawRect, selectRect, -1, -1);
	}

	public ToolDrawCommand(CToolItem item, Rectangle drawRect, int row, int col)
	{
		InternalConstruct(item, drawRect, drawRect, row, col);
	}

	public void InternalConstruct(CToolItem item, Rectangle drawRect, Rectangle selectRect,
			int row, int col)
	{
		this.row = row;
		this.col = col;
		enabled = item.isEnabled();
		this.drawRect = drawRect;
		this.selectRect = selectRect;
		this.toolItem = item;
		isSeparator = (toolItem.getText().equals("-") || (toolItem.getStyle() & SWT.SEPARATOR) != 0);
		hasMenu = (toolItem.getMenu() != null);
		isChevron = false;
	}

	public CToolItem getToolItem()
	{
		return toolItem;
	}

	public Rectangle getDrawRect()
	{
		return drawRect;
	}

	public Rectangle getSelectRect()
	{
		return selectRect;
	}

	public boolean isSeparator()
	{
		return isSeparator;
	}

	public int getRow()
	{
		return row;
	}

	public int getCol()
	{
		return col;
	}

	public boolean isEnabled()
	{
		return enabled;
	}

	public boolean hasMenu()
	{
		return hasMenu;
	}

	public void setSeparator(boolean isSeparator)
	{
		this.isSeparator = isSeparator;
	}

	public boolean isChevron()
	{
		return isChevron;
	}

	public void setDrawRect(Rectangle rectangle)
	{
		this.drawRect = rectangle;
	}
}
