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

class MenuDrawCommand {
	protected int row;

	protected int col;

	protected boolean enabled;

	protected boolean hasSubMenu;

	protected boolean isChevron;

	protected Rectangle drawRect;

	protected Rectangle selectRect;

	protected CMenuItem menuItem;

	protected boolean topBorder;

	protected boolean bottomBorder;

	public MenuDrawCommand(Rectangle drawRect) {
		row = -1;
		col = -1;
		enabled = true;
		hasSubMenu = false;
		this.drawRect = drawRect;
		selectRect = drawRect;
		menuItem = null;
		isChevron = true;
		topBorder = false;
		bottomBorder = false;
	}

	public MenuDrawCommand(Rectangle drawRect, boolean expansion) {
		row = -1;
		col = -1;
		enabled = true;
		hasSubMenu = false;
		this.drawRect = drawRect;
		selectRect = drawRect;
		isChevron = false;
		menuItem = null;
		topBorder = false;
		bottomBorder = false;
	}

	public MenuDrawCommand(CMenuItem item, Rectangle drawRect) {
		InternalConstruct(item, drawRect, drawRect, -1, -1);
	}

	public MenuDrawCommand(CMenuItem item, Rectangle drawRect,
			Rectangle selectRect) {
		InternalConstruct(item, drawRect, selectRect, -1, -1);
	}

	public MenuDrawCommand(CMenuItem item, Rectangle drawRect, int row, int col) {
		InternalConstruct(item, drawRect, drawRect, row, col);
	}

	public void InternalConstruct(CMenuItem item, Rectangle drawRect,
			Rectangle selectRect, int row, int col) {
		this.row = row;
		this.col = col;
		this.drawRect = drawRect;
		this.selectRect = selectRect;
		this.menuItem = item;
		isChevron = false;
	}

	public CMenuItem getMenuItem() {
		return menuItem;
	}

	public Rectangle getDrawRect() {
		return drawRect;
	}

	public Rectangle getSelectRect() {
		return selectRect;
	}

	public boolean isSeparator() {
		if (menuItem != null)
			return (menuItem.getText().equals("-") || (menuItem.getStyle() & SWT.SEPARATOR) != 0);
		return false;
	}

	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}

	public boolean isEnabled() {
		if (menuItem != null)
			return menuItem.isEnabled();
		return true;
	}

	public boolean hasSubMenu() {
		if (menuItem != null)
			return (menuItem.getMenu() != null);
		return false;
	}

	public boolean isChevron() {
		return isChevron;
	}

	public void setDrawRect(Rectangle rectangle) {
		this.drawRect = rectangle;
	}

}
