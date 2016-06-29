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

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Slider;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.forms.widgets.TableWrapData;
import org.eclipse.ui.forms.widgets.TableWrapLayout;
import org.sf.feeling.swt.win32.extension.Win32;
import org.sf.feeling.swt.win32.extension.example.Example;
import org.sf.feeling.swt.win32.extension.example.util.WidgetUtil;
import org.sf.feeling.swt.win32.extension.graphics.ImageRegion;
import org.sf.feeling.swt.win32.extension.shell.Windows;
import org.sf.feeling.swt.win32.extension.shell.listener.WindowMoveListener;
import org.sf.feeling.swt.win32.extension.widgets.CMenu;
import org.sf.feeling.swt.win32.extension.widgets.CMenuItem;
import org.sf.feeling.swt.win32.extension.widgets.PopupMenu;
import org.sf.feeling.swt.win32.extension.widgets.ThemeConstants;
import org.sf.feeling.swt.win32.extension.widgets.listener.MenuAdapter;
import org.sf.feeling.swt.win32.internal.extension.util.ImageCache;

public class WindowPage extends SimpleTabPage
{

	private Button vistaBtn;

	private Button borderBtn;

	private Button captionBtn;

	private Button paletteStyleBtn;

	private Button transparentBtn;

	private Slider slider;

	private Button flashBtn;

	private Button onTopBtn;

	private Button office2007Btn;

	private Button glossyBtn;

	public void buildUI(Composite parent)
	{
		super.buildUI(parent);
		TableWrapLayout layout = new TableWrapLayout();
		layout.leftMargin = 10;
		layout.rightMargin = 10;
		layout.topMargin = 15;
		layout.verticalSpacing = 20;
		container.getBody().setLayout(layout);

		createTitle();
		createWindowArea();
	}

	private void createWindowArea()
	{
		TableWrapData td;
		Section windowSection = WidgetUtil.getToolkit().createSection(container.getBody(),
				Section.EXPANDED);
		td = new TableWrapData(TableWrapData.FILL);
		windowSection.setLayoutData(td);
		windowSection.setText("Window Decorations List:");
		WidgetUtil.getToolkit().createCompositeSeparator(windowSection);
		Composite fileInfoClient = WidgetUtil.getToolkit().createComposite(windowSection);
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		fileInfoClient.setLayout(layout);
		if (Win32.getWin32Version() >= Win32.VERSION(5, 0))
		{
			transparentBtn = WidgetUtil.getToolkit().createButton(fileInfoClient,
					"Transparent", SWT.CHECK);
			slider = new Slider(fileInfoClient, SWT.HORIZONTAL);
			slider.setEnabled(false);
			slider.setMinimum(0);
			slider.setMaximum(285);
			slider.setThumb(30);
			slider.setSelection(255);
			transparentBtn.addSelectionListener(new SelectionAdapter()
			{

				public void widgetSelected(SelectionEvent e)
				{
					if (transparentBtn.getSelection())
					{
						slider.setEnabled(true);
						Windows.setWindowTransparent(container.getShell().handle,
								(byte) slider.getSelection());
					}
					else
					{
						slider.setEnabled(false);
						Windows.setWindowTransparent(container.getShell().handle, false);
					}

				}

			});
			slider.addSelectionListener(new SelectionAdapter()
			{

				public void widgetSelected(SelectionEvent e)
				{
					Windows.setWindowTransparent(container.getShell().handle, (byte) slider
							.getSelection());
				}

			});
			transparentBtn.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			slider.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		}

		onTopBtn = WidgetUtil.getToolkit().createButton(fileInfoClient, "Always-on-top",
				SWT.CHECK);
		onTopBtn.addSelectionListener(new SelectionAdapter()
		{

			public void widgetSelected(SelectionEvent e)
			{
				Windows.setWindowAlwaysOnTop(container.getShell().handle, onTopBtn
						.getSelection());
			}

		});
		GridData gd = new GridData();
		gd.horizontalSpan = 2;
		onTopBtn.setLayoutData(gd);

		paletteStyleBtn = WidgetUtil.getToolkit().createButton(fileInfoClient,
				"Palette Style", SWT.CHECK);

		paletteStyleBtn.addSelectionListener(new SelectionAdapter()
		{

			public void widgetSelected(SelectionEvent e)
			{
				Windows.setPaletteWindow(container.getShell().handle, paletteStyleBtn
						.getSelection());
				paletteStyleBtn.getShell().redraw();
				if (Windows.isWindowTransparent(paletteStyleBtn.getShell().handle))
				{
					Windows.setWindowTransparent(paletteStyleBtn.getShell().handle, true);
				}
			}

		});

		gd = new GridData();
		gd.horizontalSpan = 2;
		paletteStyleBtn.setLayoutData(gd);

		captionBtn = WidgetUtil.getToolkit().createButton(fileInfoClient, "Show Caption",
				SWT.CHECK);

		captionBtn.addSelectionListener(new SelectionAdapter()
		{

			public void widgetSelected(SelectionEvent e)
			{
				if (captionBtn.getSelection()) Windows
						.showTitleBar(container.getShell().handle);
				else
					Windows.hideTitleBar(container.getShell().handle);
				if (Windows.isWindowTransparent(paletteStyleBtn.getShell().handle))
				{
					Windows.setWindowTransparent(paletteStyleBtn.getShell().handle, true);
				}
			}

		});

		gd = new GridData();
		gd.horizontalSpan = 2;
		captionBtn.setLayoutData(gd);

		borderBtn = WidgetUtil.getToolkit().createButton(fileInfoClient, "Show Border",
				SWT.CHECK);

		borderBtn.addSelectionListener(new SelectionAdapter()
		{

			public void widgetSelected(SelectionEvent e)
			{
				Windows.setBorderThick(container.getShell().handle, borderBtn.getSelection());
				if (Windows.isWindowTransparent(paletteStyleBtn.getShell().handle))
				{
					Windows.setWindowTransparent(paletteStyleBtn.getShell().handle, true);
				}
			}

		});

		gd = new GridData();
		gd.horizontalSpan = 2;
		borderBtn.setLayoutData(gd);

		flashBtn = WidgetUtil.getToolkit().createButton(fileInfoClient, "Window Flash",
				SWT.CHECK);
		flashBtn.addSelectionListener(new SelectionAdapter()
		{

			public void widgetSelected(SelectionEvent e)
			{
				Windows.flashWindow(container.getShell().handle, flashBtn.getSelection());
			}

		});

		gd = new GridData();
		gd.horizontalSpan = 2;
		flashBtn.setLayoutData(gd);

		final Shell[] shells = new Shell[1];
		final Button imageRegionBtn = WidgetUtil.getToolkit().createButton(fileInfoClient,
				"Show Custom-Shaped Window", SWT.CHECK);
		imageRegionBtn.addSelectionListener(new SelectionAdapter()
		{

			public void widgetSelected(SelectionEvent e)
			{
				if (imageRegionBtn.getSelection())
				{
					shells[0] = new Shell(container.getShell(), SWT.NO_TRIM);
					Image image = ImageCache.getImage("/cup.bmp");
					shells[0].setSize(image.getImageData().width, image.getImageData().height);
					shells[0].setBackgroundImage(image);
					shells[0].setRegion(ImageRegion.calculateControlGraphicsPath(image, image
							.getImageData().getPixel(0, 0)));
					WindowMoveListener l = new WindowMoveListener(shells[0], false);
					shells[0].addListener(SWT.MouseDown, l);
					shells[0].addListener(SWT.MouseUp, l);
					shells[0].addListener(SWT.MouseMove, l);
					shells[0].setLayout(new GridLayout());
					CMenu menu = new CMenu();
					CMenuItem item = new CMenuItem("&Close", SWT.NONE);
					menu.addItem(item);
					final PopupMenu popupMenu = new PopupMenu(shells[0], Example.getInstance()
							.getMenuBar().getTheme());
					popupMenu.setMenu(menu);

					item.addSelectionListener(new SelectionAdapter()
					{
						public void widgetSelected(SelectionEvent arg0)
						{
							shells[0].close();
							imageRegionBtn.setSelection(false);
						}
					});
					menu.addMenuListener(new MenuAdapter()
					{

						public void menuShown(Event e)
						{
							popupMenu.setTheme(Example.getInstance().getMenuBar().getTheme());
							popupMenu.refresh();

						}

					});

					Monitor primary = Display.getDefault().getPrimaryMonitor();
					Rectangle bounds = primary.getBounds();
					Rectangle rect = shells[0].getBounds();
					int x = bounds.x + (bounds.width - rect.width) / 2;
					int y = bounds.y + (bounds.height - rect.height) / 2;
					shells[0].setLocation(x, y);
					shells[0].setVisible(true);
				}
				else
				{
					shells[0].close();
				}
			}

		});

		gd = new GridData();
		gd.horizontalSpan = 2;
		imageRegionBtn.setLayoutData(gd);

		vistaBtn = WidgetUtil.getToolkit().createButton(fileInfoClient,
				"Wrap shell as Vista style", SWT.CHECK);

		vistaBtn.addSelectionListener(new SelectionAdapter()
		{
			public void widgetSelected(SelectionEvent e)
			{
				if (vistaBtn.getSelection())
				{
					Example.getInstance().getWrapper()
							.installTheme(ThemeConstants.STYLE_VISTA);
					Example.getInstance().handleVistaSelection();
				}
				else
				{
					Example.getInstance().getWrapper().unWrapper();
				}
				if (Windows.isWindowTransparent(paletteStyleBtn.getShell().handle))
				{
					Display.getDefault().asyncExec(new Runnable()
					{
						public void run()
						{
							Windows.setWindowTransparent(paletteStyleBtn.getShell().handle,
									true);
						}
					});
				}
				resetButtons();
			}

		});
		
		glossyBtn = WidgetUtil.getToolkit().createButton(fileInfoClient,
				"Wrap shell as Glossy style", SWT.CHECK);
		glossyBtn.setEnabled(Win32.getWin32Version() >= Win32.VERSION(5, 0));
		glossyBtn.addSelectionListener(new SelectionAdapter()
		{
			public void widgetSelected(SelectionEvent e)
			{
				if (glossyBtn.getSelection())
				{
					Example.getInstance().getWrapper()
							.installTheme(
							ThemeConstants.STYLE_GLOSSY);
					Example.getInstance().handleGlossySelection();
				}
				else
				{
					Example.getInstance().getWrapper().unWrapper();
				}
				if (Windows.isWindowTransparent(paletteStyleBtn.getShell().handle))
				{
					Display.getDefault().asyncExec(new Runnable()
					{
						public void run()
						{
							Windows.setWindowTransparent(paletteStyleBtn.getShell().handle,
									true);
						}
					});
				}
				resetButtons();
			}

		});

		gd = new GridData();
		gd.horizontalSpan = 2;
		vistaBtn.setLayoutData(gd);

		office2007Btn = WidgetUtil.getToolkit().createButton(fileInfoClient,
				"Wrap shell as Office2007 style", SWT.CHECK);
		office2007Btn.setEnabled(Win32.getWin32Version() >= Win32.VERSION(5, 0));
		office2007Btn.addSelectionListener(new SelectionAdapter()
		{
			public void widgetSelected(SelectionEvent e)
			{
				if (office2007Btn.getSelection())
				{
					Example.getInstance().getWrapper().installTheme(
							ThemeConstants.STYLE_OFFICE2007);
					Example.getInstance().handleOffice2007Selection();
				}
				else
				{
					Example.getInstance().getWrapper().unWrapper();
				}
				if (Windows.isWindowTransparent(paletteStyleBtn.getShell().handle))
				{
					Display.getDefault().asyncExec(new Runnable()
					{
						public void run()
						{
							Windows.setWindowTransparent(paletteStyleBtn.getShell().handle,
									true);
						}
					});
				}
				resetButtons();
			}

		});

		gd = new GridData();
		gd.horizontalSpan = 2;
		office2007Btn.setLayoutData(gd);

		windowSection.setClient(fileInfoClient);
	}

	private void createTitle()
	{
		WidgetUtil.createFormText(container.getBody(),
				"This page demonstrates various window decorations by using SWT Win32 Extension.");
	}

	public String getDisplayName()
	{
		return "Window Decorations Example";
	}

	public void activate()
	{
		resetButtons();
		Windows.setWindowAlwaysOnTop(container.getShell().handle, false);
	}

	private void resetButtons()
	{
		Display.getDefault().asyncExec(new Runnable()
		{
			public void run()
			{
				if (getControl() == null || getControl().isDisposed()) return;
				boolean installd = Example.getInstance().getWrapper() == null ? false
						: Example.getInstance().getWrapper().isThemeInstalled();
				vistaBtn.setSelection(installd
						&& Example.getInstance().getWrapper().getTheme().equals(
								ThemeConstants.STYLE_VISTA));
				office2007Btn.setSelection(installd
						&& Example.getInstance().getWrapper().getTheme().equals(
								ThemeConstants.STYLE_OFFICE2007));
				glossyBtn.setSelection(installd
						&& Example.getInstance().getWrapper().getTheme().equals(
								ThemeConstants.STYLE_GLOSSY));
				paletteStyleBtn.setEnabled(!installd);
				captionBtn.setEnabled(!installd);
				borderBtn.setEnabled(!installd);
				paletteStyleBtn.setSelection(Windows
						.isPalleteWindow(container.getShell().handle));
				borderBtn.setSelection(Windows.isBorderThick(container.getShell().handle));
				captionBtn
						.setSelection(Windows.isTitleBarVisible(container.getShell().handle));
				if (transparentBtn != null)
				{
					transparentBtn.setSelection(Windows.isWindowTransparent(container
							.getShell().handle));
					slider.setEnabled(transparentBtn.getSelection());
					slider.setSelection(Windows
							.getWindowTransparency(container.getShell().handle));
				}
			}
		});

	}

	public void deActivate()
	{
		if (Windows.flashWindow(container.getShell().handle, false))
		;
		flashBtn.setSelection(false);
		Windows.setWindowAlwaysOnTop(container.getShell().handle, false);
		onTopBtn.setSelection(false);
	}

}
