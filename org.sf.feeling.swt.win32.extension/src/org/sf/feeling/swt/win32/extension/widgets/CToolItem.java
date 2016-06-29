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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

/**
 * Style: SWT.CHECK or SWT.SEPARATOR
 * 
 * @author <a href="mailto:cnfree2000@hotmail.com">cnfree</a>
 * 
 */
public class CToolItem
{

	private String text = "";

	private String toolTip;

	private int style = SWT.NONE;

	public CToolItem(int style)
	{
		this.style = style;
	}

	public CToolItem()
	{
		this.style = SWT.SEPARATOR;
	}

	public CToolItem(String text, int style)
	{
		this.style = style;
		this.text = text == null ? "" : text;
	}

	public String getText()
	{
		return text;
	}

	public void setText(String text)
	{
		if (text == null) this.text = "";
		else
			this.text = text;
	}

	private Image image;
	
	private Image disableImage;

	public Image getDisableImage()
	{
		return disableImage;
	}

	public void setDisableImage(Image disabledImage)
	{
		this.disableImage = disabledImage;
	}

	public boolean isVisible()
	{
		return visible;
	}

	private boolean visible = true;

	private boolean enabled = true;

	public boolean isEnabled()
	{
		return enabled;
	}

	public void setEnabled(boolean enabled)
	{
		this.enabled = enabled;
	}

	public void setVisible(boolean visible)
	{
		this.visible = visible;
	}

	private CMenu menu;

	public void setMenu(CMenu menu)
	{
		this.menu = menu;
	}

	public CMenu getMenu()
	{
		return menu;
	}

	public Image getImage()
	{
		return image;
	}

	public void setImage(Image image)
	{
		this.image = image;
	}

	public int getStyle()
	{
		return style;
	}

	private boolean selected = false;

	public boolean getSelection()
	{
		return selected;
	}

	public void setSelection(boolean selected)
	{
		this.selected = selected;
	}

	public boolean canSelected()
	{
		if (getMenu() == null) return true;
		return listeners != null;
	}

	private List listeners;

	public void addSelectionListener(SelectionListener listener)
	{
		if (isDisposed()) return;
		if (listener == null) return;
		if (listeners == null) listeners = new ArrayList();
		if (!listeners.contains(listener)) listeners.add(listener);
	}

	public void removeSelectionListener(SelectionListener listener)
	{
		if (isDisposed()) return;
		if (listener == null)
			return;
		else {
			listeners.remove(listener);
			if (listeners.size() == 0)
				listeners = null;
		}
	}

	void fireSelectionEvent(final SelectionEvent event)
	{
		if (isDisposed()) return;
		if (listeners != null)
		{
			for (int i = 0; i < listeners.size(); i++)
			{
				final SelectionListener listener = (SelectionListener) listeners.get(i);
				Display.getDefault().asyncExec(new Runnable()
				{
					public void run()
					{
						listener.widgetSelected(event);
					}
				});
			}
		}
	}

	private boolean isDisposed;

	public void disposed()
	{
		isDisposed = true;
		listeners.clear();
		listeners = null;
	}

	public boolean isDisposed()
	{
		return isDisposed;
	}

	public String getToolTip()
	{
		return toolTip;
	}

	public void setToolTip(String toolTip)
	{
		this.toolTip = toolTip;
	}
}
