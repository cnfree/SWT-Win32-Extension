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
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.sf.feeling.swt.win32.extension.Win32;
import org.sf.feeling.swt.win32.extension.shell.Windows;
import org.sf.feeling.swt.win32.extension.widgets.theme.BlackGlossyThemeRender;
import org.sf.feeling.swt.win32.extension.widgets.theme.ThemeRender;

class ToolMenuControl extends MenuControl
{
	private int toolStyle;

	protected ToolMenuControl(MenuHolder holder, ThemeRender theme, int style)
	{
		super(holder, theme);
		this.toolStyle = style;
	}

	private CToolItem[] items;

	void setToolItems(CToolItem[] items)
	{
		this.items = items;
	}

	private Point topRight;

	CToolBar toolbar;

	void setTopRightLocation(Point location)
	{
		this.topRight = location;
	}

	protected void createAndShowWindow()
	{
		if (shell == null || shell.isDisposed())
		{
			int style = SWT.ON_TOP | SWT.NO_FOCUS | SWT.TOOL | SWT.NO_TRIM;
			if (Win32.getWin32Version() < Win32.VERSION(5, 0))
			{
				style = SWT.ON_TOP | SWT.NO_FOCUS;
			}
			shell = new Shell(holder.getShell(), style);
			if (Win32.getWin32Version() < Win32.VERSION(5, 0))
			{
				Windows.hideTitleBar(shell.handle);
			}
			GridLayout layout = new GridLayout();
			layout.marginWidth = layout.marginHeight = 1;
			shell.setLayout(layout);

			shell.setBackground(theme.getTool_menu_control_border());
			toolbar = new CToolBar(shell, toolStyle | SWT.MULTI, theme)
			{
				protected boolean checkEnableHandleEvent()
				{
					return true;
				}

				public void hideMenu()
				{
					holder.hideMenu();
				}
			};
			GridData gd = new GridData(GridData.FILL_VERTICAL);
			gd.widthHint = 150;
			toolbar.getControl().setLayoutData(gd);
			toolbar.addItems(items);
			shell.layout();
			Display.getDefault().addFilter(SWT.MouseDown, new Listener()
			{

				public void handleEvent(Event event)
				{
					if (shell == null || shell.isDisposed())
					{
						Display.getDefault().removeFilter(SWT.MouseDown, this);
						return;
					}
					if (((Control) event.widget).isDisposed()
							|| holder.checkMouseDownEvent(((Control) event.widget).toDisplay(
									event.x, event.y)))
					{
						holder.hideMenu();
					}

				}
			});
			toolbar.addSelectionListener(new SelectionAdapter()
			{

				public void widgetSelected(SelectionEvent e)
				{
					if (e.data instanceof CToolItem)
					{
						holder.hideMenu();
					}
				}

			});
			toolbar.recalculate();
			toolbar.setFocusControl(true);
			toolbar.getControl().getParent().layout();
			shell.setSize(toolbar.getControl().getBounds().width + 2,
					toolbar.recalculate().height + 2);
			if (topRight != null)
			{
				int menuGap = 1;
				if (theme instanceof BlackGlossyThemeRender) menuGap = 0;

				if (topRight.y + shell.getSize().y <= Display.getDefault().getClientArea().y
						+ Display.getDefault().getClientArea().height) shell
						.setLocation(new Point(topRight.x - shell.getSize().x, topRight.y
								- menuGap));
				else
					shell.setLocation(new Point(topRight.x - shell.getSize().x, topRight.y
							- holder.getControl().getSize().y - shell.getSize().y + menuGap));
			}
			Display.getDefault().syncExec(new Runnable()
			{
				public void run()
				{
					if (!shell.isDisposed())
					{
						if (Win32.getWin32Version() >= Win32.VERSION(5, 0)) Windows
								.showWindowBlend(shell.handle, 200);
						else
							shell.setVisible(true);
						toolbar.refresh();
					}
				}
			});

			final Listener keyDownListener = new Listener()
			{

				public void handleEvent(Event e)
				{
					if (shell == null || shell.isDisposed())
					{
						Display.getDefault().removeFilter(SWT.KeyDown, this);
						e.doit = false;
						return;
					}

					Event e1 = new Event();
					e1.time = e.time;
					e1.doit = e.doit;
					e1.character = e.character;
					e1.keyCode = e.keyCode;

					if (e1.keyCode == SWT.ARROW_RIGHT || e1.keyCode == SWT.ARROW_DOWN)
					{
						e1.detail = SWT.TRAVERSE_ARROW_NEXT;
						toolbar.getControl().notifyListeners(SWT.Traverse, e1);
					}
					if (e1.keyCode == SWT.ARROW_LEFT || e1.keyCode == SWT.ARROW_UP)
					{
						e1.detail = SWT.TRAVERSE_ARROW_PREVIOUS;
						toolbar.getControl().notifyListeners(SWT.Traverse, e1);
					}
					if (e1.character == SWT.CR)
					{
						e1.detail = SWT.TRAVERSE_RETURN;
						toolbar.getControl().notifyListeners(SWT.Traverse, e1);
					}
					if (e1.character == SWT.ESC)
					{
						e1.detail = SWT.TRAVERSE_ESCAPE;
						toolbar.getControl().notifyListeners(SWT.Traverse, e1);
					}
				}
			};
			Display.getDefault().addFilter(SWT.KeyDown, keyDownListener);

		}
	}
}
