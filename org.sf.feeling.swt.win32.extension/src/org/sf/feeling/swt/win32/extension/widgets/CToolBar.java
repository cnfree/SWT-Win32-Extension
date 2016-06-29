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
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.MouseTrackAdapter;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.events.ShellListener;
import org.eclipse.swt.events.TraverseEvent;
import org.eclipse.swt.events.TraverseListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.PaletteData;
import org.eclipse.swt.graphics.Path;
import org.eclipse.swt.graphics.Pattern;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.graphics.Region;
import org.eclipse.swt.internal.Callback;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.sf.feeling.swt.win32.extension.Win32;
import org.sf.feeling.swt.win32.extension.graphics.GraphicsUtil;
import org.sf.feeling.swt.win32.extension.widgets.theme.GeneralThemeRender;
import org.sf.feeling.swt.win32.extension.widgets.theme.GlossyThemeRender;
import org.sf.feeling.swt.win32.extension.widgets.theme.OfficeThemeRender;
import org.sf.feeling.swt.win32.extension.widgets.theme.ThemeRender;
import org.sf.feeling.swt.win32.extension.widgets.theme.VS2005ThemeRender;
import org.sf.feeling.swt.win32.internal.extension.Extension;
import org.sf.feeling.swt.win32.internal.extension.graphics.GCExtension;

/**
 * Style: SWT.MULITI, SWT.NONE, LARGE_ICON or SMALL_ICON
 * 
 * @author <a href="mailto:cnfree2000@hotmail.com">cnfree</a>
 * 
 */
public class CToolBar implements MenuHolder, Bar
{

	private static final int KEY_LEFT = 16777219;

	private static final int KEY_RIGHT = 16777220;

	private static final int KEY_DOWN = 16777218;

	private static final int KEY_UP = 16777217;

	private static final int SUBMENUWIDTH = 11;

	private static final int CHEVRON_LENGTH = 12;

	private static final int HORIZON_GAP = 4;

	private static final int HORIZON_MARGIN = 2, VERTICAL_MARGIN = 1;

	private static final int SEPARATOR_WIDTH = 4;

	private static final int SHADOW_GAP = 4;

	private static final int VERTICAL_GAP = 3;

	private CToolItem chevronStartItem;

	private List drawCommands = new ArrayList();

	private boolean drawUpwards;

	private GridLayout layout;

	private CLabel toolbar;

	private MenuControl popupMenu;

	private boolean selected = false;

	private int style = SWT.NONE;

	private ThemeRender theme;

	private int trackItemIndex = -1;

	private final static int IMAGE_MIN_WIDTH = 16;

	private final static int IMAGE_MIN_HEIGHT = 16;

	private int imageWidth = IMAGE_MIN_WIDTH;

	private int imageHeight = IMAGE_MIN_HEIGHT;

	public final static int LARGE_ICON = 1 << 13;

	public final static int SMALL_ICON = 1 << 14;

	private static final int DRAW_FLAGS = SWT.DRAW_TAB | SWT.DRAW_TRANSPARENT
			| SWT.DRAW_DELIMITER;

	public CToolBar(Composite parent, int style)
	{
		this(parent, style, new VS2005ThemeRender());
	}

	private boolean menuTracked;

	public CToolBar(Composite parent, int style, ThemeRender theme)
	{
		toolbar = new CLabel(parent, SWT.NONE)
		{
			public Point computeSize(int wHint, int hHint, boolean changed)
			{
				if (this.isVisible())
				{
					Rectangle rect = recalculate();
					return new Point(rect.width, rect.height);
				}
				else
					return super.computeSize(wHint, hHint, changed);
			}
		};
		this.style = style;
		layout = new GridLayout();
		layout.marginWidth = layout.marginHeight = 0;
		layout.horizontalSpacing = 0;
		setTheme(theme);
		toolbar.setLayout(layout);
		toolbar.addPaintListener(new PaintListener()
		{

			public void paintControl(PaintEvent e)
			{
				onPaint(e);
			}

		});

		toolbar.addDisposeListener(new DisposeListener()
		{

			public void widgetDisposed(DisposeEvent e)
			{
				hideCurrentMenu();
			}

		});

		final ShellListener shellListener = new ShellListener()
		{
			public void shellClosed(ShellEvent e)
			{
				hideCurrentMenu();
			}

			public void shellDeactivated(ShellEvent e)
			{
				hideCurrentMenu();
			}

			public void shellIconified(ShellEvent e)
			{
				hideCurrentMenu();
			}

			public void shellActivated(ShellEvent e)
			{
				hideCurrentMenu();
			}

			public void shellDeiconified(ShellEvent e)
			{
				hideCurrentMenu();
			}
		};
		toolbar.getShell().addShellListener(shellListener);

		final Listener mouseDownListener = new Listener()
		{

			public void handleEvent(Event e)
			{
				if (getShell() == null || getShell().isDisposed())
				{
					Display.getDefault().removeFilter(SWT.MouseDown, this);
					return;
				}
				if (e.widget == null || e.widget.isDisposed()
						|| checkMouseDownEvent(((Control) e.widget).toDisplay(e.x, e.y)))
				{
					checkMouseDownEvent(((Control) e.widget).toDisplay(e.x, e.y));
					hideCurrentMenu();
				}
			}
		};

		Display.getDefault().addFilter(SWT.MouseDown, mouseDownListener);

		final Listener keyDownListener = new Listener()
		{

			public void handleEvent(Event e)
			{
				if (toolbar == null || toolbar.isDisposed())
				{
					Display.getDefault().removeFilter(SWT.KeyDown, this);
					e.doit = false;
					return;
				}
				KeyEvent ke = new KeyEvent(e);
				if ((ke.keyCode == SWT.ALT) || ke.keyCode == SWT.F10)
				{
					hideCurrentMenu();
					return;
				}
				if (getCurrentMenu() == null)
				{
					return;
				}

				getCurrentMenu().dealAltKeyEvent(ke);

				if (ke.keyCode == KEY_RIGHT && trackItemIndex > -1)
				{
					getCurrentMenu().subMenuSelected();
				}
				if (ke.keyCode == KEY_LEFT && trackItemIndex > -1)
				{
					getCurrentMenu().parentMenuSelected();
				}
				if (ke.keyCode == KEY_DOWN || ke.keyCode == KEY_UP)
				{
					if (ke.keyCode == KEY_DOWN) getCurrentMenu().downSelected();
					else
						getCurrentMenu().upSelected();
				}
				if (ke.keyCode == SWT.ESC)
				{
					if (getCurrentMenu().parentMenu == null)
					{
						hideCurrentMenu();
//						trackItemIndex = -1;
						selected = false;
						refresh();
					}
					else
					{
						getCurrentMenu().parentMenuSelected();
					}
				}
				if (ke.keyCode == SWT.CR)
				{
					getCurrentMenu().handleSelectedEvent();
				}
			}
		};
		Display.getDefault().addFilter(SWT.KeyDown, keyDownListener);

		toolbar.addKeyListener(new KeyAdapter()
		{
		});

		toolbar.addFocusListener(new FocusListener()
		{

			public void focusGained(FocusEvent e)
			{
				setFocusControl(true);
				trackItemIndex = calculateNextTraverseIndex(trackItemIndex, true);
				drawCommand(trackItemIndex, true);
			}

			public void focusLost(FocusEvent e)
			{
				setFocusControl(false);
				hideCurrentMenu();
				selected = false;
				refresh();
				trackItemIndex = -1;
			}

		});
		toolbar.addTraverseListener(new TraverseListener()
		{

			public void keyTraversed(TraverseEvent e)
			{
				if (isShowMenu() && popupMenu instanceof ToolMenuControl)
				{
					if (e.detail != SWT.TRAVERSE_ESCAPE)
					{
						e.doit = false;
					}
					else
					{
						if (((ToolMenuControl) popupMenu).toolbar.getCurrentMenu() == null)
						{
							popupMenu.hideMenu();
							popupMenu = null;
							selected = false;
							refresh();
						}
					}
					return;
				}
				switch (e.detail)
				{
				case SWT.TRAVERSE_PAGE_NEXT:
				case SWT.TRAVERSE_PAGE_PREVIOUS:
					e.doit = false;
					return;
				}

				if (e.detail == SWT.TRAVERSE_ARROW_PREVIOUS
						|| e.detail == SWT.TRAVERSE_ARROW_NEXT)
				{
					if (e.detail == SWT.TRAVERSE_ARROW_PREVIOUS && e.keyCode == SWT.ARROW_LEFT)
					{
						if (isShowMenu())
						{
							popupMenu.hideMenu();
							popupMenu = null;
							selected = false;
						}
						selected = false;
						menuTracked = false;
						refresh();
						trackItemIndex = calculateNextTraverseIndex(trackItemIndex, false);

					}
					else if (e.detail == SWT.TRAVERSE_ARROW_NEXT
							&& e.keyCode == SWT.ARROW_RIGHT)
					{
						if (isShowMenu())
						{
							popupMenu.hideMenu();
							popupMenu = null;
							selected = false;
						}
						selected = false;
						menuTracked = false;
						refresh();
						trackItemIndex = calculateNextTraverseIndex(trackItemIndex, true);
					}
					else
					{

						if (!isShowMenu() && trackItemIndex > -1)
						{
							ToolDrawCommand command = ((ToolDrawCommand) drawCommands
									.get(trackItemIndex));
							CToolItem item = command.getToolItem();
							if (item == null || !item.canSelected())
							{
								showMenu(command);
								selected = true;
							}
							else if (item.canSelected())
							{
								if (item.getMenu() != null)
								{
									showMenu(command);
									selected = true;
									menuTracked = true;
								}
							}
							refresh();
						}
					}
				}
				else if (e.detail == SWT.TRAVERSE_RETURN && trackItemIndex > -1
						&& !menuTracked)
				{
					ToolDrawCommand command = ((ToolDrawCommand) drawCommands
							.get(trackItemIndex));
					CToolItem item = command.getToolItem();
					Event event = new Event();
					event.widget = toolbar;
					event.data = item;
					event.type = SWT.SELECTED;
					getItem(trackItemIndex).fireSelectionEvent(new SelectionEvent(event));
					fireSelectionEvent(event);
					Display.getDefault().asyncExec(new Runnable()
					{
						public void run()
						{
							refresh();
						}
					});
				}
				else
					e.doit = true;
			}
		});

		toolbar.addDisposeListener(new DisposeListener()
		{

			public void widgetDisposed(DisposeEvent e)
			{
				Display.getDefault().removeFilter(SWT.MouseDown, mouseDownListener);
				Display.getDefault().removeFilter(SWT.KeyDown, keyDownListener);

			}

		});

		toolbar.addMouseMoveListener(new MouseMoveListener()
		{
			public void mouseMove(MouseEvent e)
			{
				handleMouseMoveEvent(new Point(e.x, e.y));
			}
		});

		toolbar.addMouseTrackListener(new MouseTrackAdapter()
		{

			public void mouseExit(MouseEvent e)
			{
				handleMouseMoveEvent(new Point(e.x, e.y));
			}
		});

		toolbar.addMouseListener(new MouseAdapter()
		{
			private boolean hideCurrentMenuCommand = false;

			public void mouseDown(MouseEvent e)
			{
				if (e.button != 1) return;
				for (int i = 0; i < drawCommands.size(); i++)
				{
					ToolDrawCommand command = ((ToolDrawCommand) drawCommands.get(i));
					if (command.getDrawRect().contains(e.x, e.y)
							&& (command.getToolItem() == null || command.getToolItem()
									.isEnabled()))
					{
						if (!selected && !isShowMenu())
						{
							selected = true;
							refresh();
							trackItemIndex = i;
							refresh();

							CToolItem item = command.getToolItem();
							if (item != null && item.getMenu() != null
									&& getMenuRect(command).contains(e.x, e.y))
							{
								menuTracked = true;
								drawCommand(trackItemIndex, true);
								showMenu(command);
							}
							else
							{
								menuTracked = false;
								drawCommand(trackItemIndex, true);
								if (item == null || !item.canSelected()) showMenu(command);
							}
						}
						else
						{
							hideCurrentMenuCommand = true;
						}
						return;
					}
				}
				selected = false;
				return;
			}

			public void mouseUp(MouseEvent e)
			{
				if (isShowMenu())
				{
					if (hideCurrentMenuCommand)
					{
						hideCurrentMenuCommand = false;
						popupMenu.hideMenu();
						popupMenu = null;
						selected = false;
						refresh();
					}
				}
				else
				{
					selected = false;
					if (trackItemIndex >= 0 && e.button == 1)
					{
						drawCommand(trackItemIndex, true);
						CToolItem item = ((ToolDrawCommand) drawCommands.get(trackItemIndex))
								.getToolItem();
						if (item != null && item.isEnabled())
						{
							Event event = new Event();
							event.widget = toolbar;
							event.data = item;
							event.type = SWT.SELECTED;
							getItem(trackItemIndex).fireSelectionEvent(
									new SelectionEvent(event));
							fireSelectionEvent(event);
							Display.getDefault().asyncExec(new Runnable()
							{
								public void run()
								{
									refresh();
								}
							});
						}
					}
					refresh();
				}
			}
		});
		MenuHolderManager.registerHolder(this);
	}

	private int calculateNextTraverseIndex(int index, boolean backward)
	{
		if (backward)
		{
			for (int i = index + 1; i < drawCommands.size(); i++)
			{
				ToolDrawCommand dc = (ToolDrawCommand) drawCommands.get(i);
				if (dc.isEnabled() && !dc.isSeparator()) return i;
			}

			for (int i = 0; i < index; i++)
			{
				ToolDrawCommand dc = (ToolDrawCommand) drawCommands.get(i);
				if (dc.isEnabled() && !dc.isSeparator()) return i;
			}
		}
		else
		{
			for (int i = index - 1; i >= 0; i--)
			{
				ToolDrawCommand dc = (ToolDrawCommand) drawCommands.get(i);
				if (dc.isEnabled() && !dc.isSeparator()) return i;
			}

			for (int i = drawCommands.size() - 1; i > index; i--)
			{
				ToolDrawCommand dc = (ToolDrawCommand) drawCommands.get(i);
				if (dc.isEnabled() && !dc.isSeparator()) return i;
			}
		}
		return -1;
	}

	private List listeners;

	public void addSelectionListener(SelectionListener listener)
	{
		if (toolbar.isDisposed()) return;
		if (listener == null) return;
		if (listeners == null) listeners = new ArrayList();
		if (!listeners.contains(listener)) listeners.add(listener);
	}

	public void removeSelectionListener(SelectionListener listener)
	{
		if (toolbar.isDisposed()) return;
		if (listener == null) return;
		else
		{
			listeners.remove(listener);
			if (listeners.size() == 0) listeners = null;
		}
	}

	void fireSelectionEvent(final Event event)
	{
		if (toolbar.isDisposed()) return;
		if (listeners != null)
		{
			final SelectionEvent selectEvent = new SelectionEvent(event);
			for (int i = 0; i < listeners.size(); i++)
			{
				final SelectionListener listener = (SelectionListener) listeners.get(i);
				Display.getDefault().asyncExec(new Runnable()
				{
					public void run()
					{
						listener.widgetSelected(selectEvent);
					}
				});
			}
		}
	}

	public boolean checkMouseDownEvent(Point location)
	{
		if (toolbar.isDisposed()) return true;
		if (new Rectangle(0, 0, toolbar.getSize().x, toolbar.getSize().y).contains(toolbar
				.toControl(location)))
		{
			for (int i = 0; i < drawCommands.size(); i++)
			{
				ToolDrawCommand command = ((ToolDrawCommand) drawCommands.get(i));
				if (command.getDrawRect().contains(toolbar.toControl(location)))
				{
					return false;
				}
			}
			return true;
		}
		if (popupMenu != null) return popupMenu.checkMouseDownEvent(location);
		return true;
	}

	private void drawAllCommands(GC gc)
	{
		for (int i = 0; i < drawCommands.size(); i++)
		{
			ToolDrawCommand dc = (ToolDrawCommand) drawCommands.get(i);
			drawSingleCommand(gc, dc, (i == trackItemIndex));
		}
	}

	private void drawCommand(int drawItem, boolean tracked)
	{
		if (drawItem < 0 || drawItem >= drawCommands.size()) return;
		GC gc = new GC(toolbar);
		drawSingleCommand(gc, (ToolDrawCommand) drawCommands.get(drawItem), tracked);
		gc.dispose();
	}

	public void drawSelectionUpwards()
	{
		// Double check the state is correct for this method to be called
		if ((trackItemIndex != -1) && (selected))
		{
			// This flag is tested in the DrawCommand method
			drawUpwards = true;

			// Force immediate redraw of the item
			drawCommand(trackItemIndex, true);
		}
	}

	private void drawSingleCommand(GC gc, ToolDrawCommand dc, boolean tracked)
	{
		Rectangle drawRect = dc.getDrawRect();
		CToolItem mc = dc.getToolItem();
		if (!dc.isSeparator())
		{
			if (dc.isEnabled())
			{
				if (tracked)
				{
					if (theme instanceof GeneralThemeRender)
					{
						GeneralThemeRender themeRender = (GeneralThemeRender) theme;
						if (selected)
						{
							if (mc == null)
							{
								gc.setForeground(themeRender.getTool_item_bg_selected1());
								gc.setBackground(themeRender.getTool_item_bg_selected2());
								gc.fillGradientRectangle(drawRect.x, drawRect.y,
										drawRect.width, drawRect.height, true);
								gc.setForeground(themeRender.getTool_item_fg_selected());
								gc.drawRectangle(drawRect);
							}
							else
							{
								if (menuTracked || (mc.getMenu() != null && !mc.canSelected()))
								{
									gc.setForeground(themeRender.getTool_item_showmenu_bg1());
									gc.setBackground(themeRender.getTool_item_showmenu_bg2());
									gc.fillGradientRectangle(drawRect.x, drawRect.y,
											drawRect.width, drawRect.height, true);
									gc.setForeground(themeRender.getTool_item_showmenu_fg());
									gc.drawRectangle(drawRect);
								}
								else
								{
									gc.setForeground(themeRender.getTool_item_bg_selected1());
									gc.setBackground(themeRender.getTool_item_bg_selected2());
									if (mc.getMenu() != null)
									{
										Rectangle noneMenuRect = getNonMenuRect(dc);
										gc.fillGradientRectangle(noneMenuRect.x,
												noneMenuRect.y, noneMenuRect.width,
												noneMenuRect.height, true);
										gc.setForeground(themeRender.getTool_item_bg_track1());
										gc.setBackground(themeRender.getTool_item_bg_track2());
										Rectangle menuRect = getMenuRect(dc);
										gc.fillGradientRectangle(menuRect.x, menuRect.y,
												menuRect.width, menuRect.height, true);
									}
									else
									{
										gc.fillGradientRectangle(drawRect.x, drawRect.y,
												drawRect.width, drawRect.height, true);
									}
									gc.setForeground(themeRender.getTool_item_fg_selected());
									gc.drawRectangle(drawRect);
								}
							}
						}
						else if (mc != null && (mc.getStyle() & SWT.CHECK) != 0
								&& mc.getSelection())
						{
							gc.setForeground(themeRender
									.getTool_item_check_selection_bg_track1());
							gc.setBackground(themeRender
									.getTool_item_check_selection_bg_track2());
							gc.fillGradientRectangle(drawRect.x, drawRect.y, drawRect.width,
									drawRect.height, true);
							gc.setForeground(themeRender
									.getTool_item_check_selection_fg_track());
							gc.drawRectangle(drawRect);
						}
						else
						{
							gc.setForeground(themeRender.getTool_item_bg_track1());
							gc.setBackground(themeRender.getTool_item_bg_track2());
							gc.fillGradientRectangle(drawRect.x, drawRect.y, drawRect.width,
									drawRect.height, true);
							gc.setForeground(themeRender.getTool_item_fg_track());
							gc.drawRectangle(drawRect);
						}
					}
					else if (theme instanceof OfficeThemeRender)
					{
						OfficeThemeRender themeRender = (OfficeThemeRender) theme;
						Color[] colors = null;
						if (selected)
						{
							if (mc == null)
							{
								colors = new Color[] {
										themeRender.getTool_item_outer_top_selected1(),
										themeRender.getTool_item_outer_top_selected2(),
										themeRender.getTool_item_outer_bottom_selected1(),
										themeRender.getTool_item_outer_bottom_selected2(),
										themeRender.getTool_item_inner_top_selected1(),
										themeRender.getTool_item_inner_top_selected2(),
										themeRender.getTool_item_inner_bottom_selected1(),
										themeRender.getTool_item_inner_bottom_selected2(),
										themeRender.getTool_item_border_selected(), };
								drawGradientBack(gc, drawRect, colors);
								drawGradientBorder(gc, drawRect, colors);
							}
							else
							{
								if (menuTracked || (mc.getMenu() != null && !mc.canSelected()))
								{
									colors = new Color[] {
											themeRender
													.getTool_item_outer_top_showmenu_color1(),
											themeRender
													.getTool_item_outer_top_showmenu_color2(),
											themeRender
													.getTool_item_outer_bottom_showmenu_color1(),
											themeRender
													.getTool_item_outer_bottom_showmenu_color2(),
											themeRender
													.getTool_item_inner_top_showmenu_color1(),
											themeRender
													.getTool_item_inner_top_showmenu_color2(),
											themeRender
													.getTool_item_inner_bottom_showmenu_color1(),
											themeRender
													.getTool_item_inner_bottom_showmenu_color2(),
											themeRender.getTool_item_border_showmenu_color(), };
									drawGradientBack(gc, drawRect, colors);
									drawGradientBorder(gc, drawRect, colors);
								}
								else
								{
									colors = new Color[] {
											themeRender.getTool_item_outer_top_selected1(),
											themeRender.getTool_item_outer_top_selected2(),
											themeRender.getTool_item_outer_bottom_selected1(),
											themeRender.getTool_item_outer_bottom_selected2(),
											themeRender.getTool_item_inner_top_selected1(),
											themeRender.getTool_item_inner_top_selected2(),
											themeRender.getTool_item_inner_bottom_selected1(),
											themeRender.getTool_item_inner_bottom_selected2(),
											themeRender.getTool_item_border_selected(), };
									if (mc.getMenu() != null)
									{
										drawGradientBack(gc, getNonMenuRect(dc), colors);
										colors = new Color[] {
												themeRender.getTool_item_outer_top_track1(),
												themeRender.getTool_item_outer_top_track2(),
												themeRender.getTool_item_outer_bottom_track1(),
												themeRender.getTool_item_outer_bottom_track2(),
												themeRender.getTool_item_inner_top_track1(),
												themeRender.getTool_item_inner_top_track2(),
												themeRender.getTool_item_inner_bottom_track1(),
												themeRender.getTool_item_inner_bottom_track2(),
												themeRender.getTool_item_border_track(), };
										drawGradientBack(gc, getMenuRect(dc), colors);
									}
									else
										drawGradientBack(gc, drawRect, colors);
									drawGradientBorder(gc, drawRect, colors);
								}
							}
						}
						else if (mc != null && (mc.getStyle() & SWT.CHECK) != 0
								&& mc.getSelection())
						{
							colors = new Color[] {
									themeRender.getTool_item_outer_top_checked_track1(),
									themeRender.getTool_item_outer_top_checked_track2(),
									themeRender.getTool_item_outer_bottom_checked_track1(),
									themeRender.getTool_item_outer_bottom_checked_track2(),
									themeRender.getTool_item_inner_top_checked_track1(),
									themeRender.getTool_item_inner_top_checked_track2(),
									themeRender.getTool_item_inner_bottom_checked_track1(),
									themeRender.getTool_item_inner_bottom_checked_track2(),
									themeRender.getTool_item_border_checked_track(), };
							drawGradientBack(gc, drawRect, colors);
							drawGradientBorder(gc, drawRect, colors);
						}
						else
						{
							colors = new Color[] {
									themeRender.getTool_item_outer_top_track1(),
									themeRender.getTool_item_outer_top_track2(),
									themeRender.getTool_item_outer_bottom_track1(),
									themeRender.getTool_item_outer_bottom_track2(),
									themeRender.getTool_item_inner_top_track1(),
									themeRender.getTool_item_inner_top_track2(),
									themeRender.getTool_item_inner_bottom_track1(),
									themeRender.getTool_item_inner_bottom_track2(),
									themeRender.getTool_item_border_track(), };
							drawGradientBack(gc, drawRect, colors);
							drawGradientBorder(gc, drawRect, colors);
						}
					}
					else if (theme instanceof GlossyThemeRender)
					{
						drawGlossyItem(gc, drawRect, dc, tracked);
					}
					if (selected) showMenuShadow(gc, dc.getDrawRect(), dc.getToolItem());
				}
				else if (mc != null && (mc.getStyle() & SWT.CHECK) != 0 && mc.getSelection())
				{
					if (theme instanceof GeneralThemeRender)
					{
						GeneralThemeRender themeRender = (GeneralThemeRender) theme;
						gc
								.setForeground(themeRender
										.getTool_item_check_selection_bg_normal1());
						gc
								.setBackground(themeRender
										.getTool_item_check_selection_bg_normal2());
						gc.fillGradientRectangle(drawRect.x, drawRect.y, drawRect.width,
								drawRect.height, true);
						gc.setForeground(themeRender.getTool_item_check_selection_fg_normal());
						gc.drawRectangle(drawRect);
					}
					else if (theme instanceof OfficeThemeRender)
					{
						OfficeThemeRender themeRender = (OfficeThemeRender) theme;
						Color[] colors = new Color[] {
								themeRender.getTool_item_outer_top_checked1(),
								themeRender.getTool_item_outer_top_checked2(),
								themeRender.getTool_item_outer_bottom_checked1(),
								themeRender.getTool_item_outer_bottom_checked2(),
								themeRender.getTool_item_inner_top_checked1(),
								themeRender.getTool_item_inner_top_checked2(),
								themeRender.getTool_item_inner_bottom_checked1(),
								themeRender.getTool_item_inner_bottom_checked2(),
								themeRender.getTool_item_border_checked(), };
						drawGradientBack(gc, drawRect, colors);
						drawGradientBorder(gc, drawRect, colors);
					}
					else if (theme instanceof GlossyThemeRender)
					{
						drawGlossyItem(gc, drawRect, dc, tracked);
					}
				}
			}
			if (dc.isChevron)
			{
				Image image = theme.getTool_chevronImage();
				int yPos = drawRect.y + VERTICAL_GAP;
				int xPos = drawRect.x + ((drawRect.width - image.getImageData().width) / 2)
						+ 1;
				if (selected && tracked)
				{
					xPos += 1;
					yPos += 1;
				}
				gc.drawImage(image, xPos, yPos);
			}
			else
			{
				if (dc.isEnabled() && toolbar.isEnabled())
				{
					gc.setForeground(theme.getTool_item_fg());
				}
				else
					gc.setForeground(theme.getTool_item_fg_disabled());
				drawImageAndText(gc, dc, selected, tracked);
			}

		}
		else
		{
			Color darkColor = theme.getTool_item_separater_darkcolor();
			Color lightColor = theme.getTool_item_separater_lightcolor();
			drawSeparator(gc, dc.getDrawRect(), lightColor, darkColor);
		}

	}

	private void drawGlossyItem(GC gc, Rectangle drawRect, ToolDrawCommand dc, boolean tracked)
	{
		GlossyThemeRender themeRender = (GlossyThemeRender) theme;
		gc.setAntialias(SWT.ON);
		Rectangle outerBorder = new Rectangle(drawRect.x, drawRect.y, drawRect.width + 1,
				drawRect.height + 1);
		Rectangle innerBorder = GraphicsUtil.inflate(outerBorder, -1, -1);
		Rectangle glossy = new Rectangle(innerBorder.x, innerBorder.y, innerBorder.width,
				innerBorder.height / 2);
		Rectangle glow = GraphicsUtil.createRectangleFromLTRB(outerBorder.x, outerBorder.y
				+ Math.round(outerBorder.height * .5f), outerBorder.x + outerBorder.width,
				outerBorder.y + outerBorder.height);

		Pattern pattern = new Pattern(null, 0, glossy.y, 0, glossy.y + glossy.height,
				themeRender.getTool_item_bg_color1(), themeRender
						.getTool_item_bg_color1_alpha(), themeRender.getTool_item_bg_color2(),
				themeRender.getTool_item_bg_color2_alpha());
		Path path = new Path(null);
		path.addRectangle(glossy.x, glossy.y, glossy.width, glossy.height);
		gc.setBackgroundPattern(pattern);
		gc.fillPath(path);
		path.dispose();

		// draw border
		path = GraphicsUtil.createRoundRectangle(outerBorder, 2);
		gc.setForeground(themeRender.getTool_item_outer_border());
		gc.drawPath(path);
		path.dispose();

		if (tracked
				&& dc.getToolItem() != null
				&& selected
				&& (menuTracked || (dc.getToolItem().getMenu() != null && !dc.getToolItem()
						.canSelected())))
		{
			path = GraphicsUtil.createTopRoundRectangle(innerBorder, 2);
			gc.setBackground(themeRender.getTool_item_bg_glossy_showMenu());
			gc.fillPath(path);
			path.dispose();

			path = GraphicsUtil.createTopRoundRectangle(innerBorder, 2);
			gc.setForeground(themeRender.getTool_item_bg_glossy_showMenu());
			gc.drawPath(path);
			path.dispose();
			gc.setAdvanced(false);
			return;
		}
		else if (tracked && dc.getToolItem() != null && selected
				&& dc.getToolItem().getMenu() != null)
		{
			pattern = new Pattern(null, 0, glossy.y, 0, glossy.y + glossy.height, themeRender
					.getTool_item_bg_glossy_selected1(), themeRender
					.getTool_item_bg_glossy_selected1_alpha(), themeRender
					.getTool_item_bg_glossy_selected2(), themeRender
					.getTool_item_bg_glossy_selected2_alpha());
			Rectangle nonMenuRect = getNonMenuRect(dc);
			Rectangle nonMenuGlossy = new Rectangle(innerBorder.x, innerBorder.y,
					nonMenuRect.width - 1, innerBorder.height / 2);
			path = createTopLeftRoundRectangle(nonMenuGlossy, 2);
			gc.setBackgroundPattern(pattern);
			gc.fillPath(path);
			path.dispose();

			pattern = new Pattern(null, 0, glossy.y, 0, glossy.y + glossy.height, themeRender
					.getTool_item_bg_glossy_track1(), themeRender
					.getTool_item_bg_glossy_track1_alpha(), themeRender
					.getTool_item_bg_glossy_track2(), themeRender
					.getTool_item_bg_glossy_track2_alpha());

			Rectangle menuRect = getMenuRect(dc);
			Rectangle menuGlossy = new Rectangle(menuRect.x, innerBorder.y,
					menuRect.width - 1, innerBorder.height / 2);
			path = createTopRightRoundRectangle(menuGlossy, 2);
			gc.setBackgroundPattern(pattern);
			gc.fillPath(path);
			path.dispose();

			Color innerBorderColor = themeRender.getTool_item_inner_border_selected();

			path = createLeftRoundPath(new Rectangle(innerBorder.x, innerBorder.y,
					nonMenuRect.width - 1, innerBorder.height), 2);
			gc.setForeground(innerBorderColor);
			gc.drawPath(path);
			path.dispose();

			innerBorderColor = themeRender.getTool_item_inner_border_track();

			path = createRightRoundPath(new Rectangle(menuRect.x, innerBorder.y,
					menuRect.width, innerBorder.height), 2);
			gc.setForeground(innerBorderColor);
			gc.drawPath(path);
			path.dispose();

			path = GraphicsUtil.createRoundRectangle(glow, 2);
			gc.setClipping(path);
			path.dispose();

			Color glowColor = themeRender.getTool_item_bg_glow_track();
			path = createBottomRadialPath(glow);
			float[] point = new float[2];
			float[] bounds = new float[4];
			path.getBounds(bounds);
			point[0] = (bounds[0] + bounds[0] + bounds[2]) / 2f;
			point[1] = (bounds[1] + bounds[1] + bounds[3]) / 2f;
			GCExtension extension = new GCExtension(gc);
			extension.fillGradientPath(path, point, glowColor, 255, new Color[] { glowColor },
					new int[] { 0 });
			path.dispose();
			gc.setClipping((Region) null);

			gc.setAdvanced(false);
			return;
		}

		if (!(selected && tracked)) pattern = new Pattern(null, 0, glossy.y, 0, glossy.y
				+ glossy.height, themeRender.getTool_item_bg_glossy_track1(), themeRender
				.getTool_item_bg_glossy_track1_alpha(), themeRender
				.getTool_item_bg_glossy_track2(), themeRender
				.getTool_item_bg_glossy_track2_alpha());
		else
			pattern = new Pattern(null, 0, glossy.y, 0, glossy.y + glossy.height, themeRender
					.getTool_item_bg_glossy_selected1(), themeRender
					.getTool_item_bg_glossy_selected1_alpha(), themeRender
					.getTool_item_bg_glossy_selected2(), themeRender
					.getTool_item_bg_glossy_selected2_alpha());
		path = GraphicsUtil.createTopRoundRectangle(glossy, 2);
		gc.setBackgroundPattern(pattern);
		gc.fillPath(path);
		path.dispose();

		Color innerBorderColor = (selected && tracked)
				|| (dc.getToolItem() != null && (dc.getToolItem().getStyle() & SWT.CHECK) != 0 && dc
						.getToolItem().getSelection()) ? themeRender
				.getTool_item_inner_border_selected() : themeRender
				.getTool_item_inner_border_track();

		path = GraphicsUtil.createRoundRectangle(innerBorder, 2);
		gc.setForeground(innerBorderColor);
		gc.drawPath(path);
		path.dispose();

		if (!((menuTracked || (dc.getToolItem() != null && dc.getToolItem().getMenu() != null && !dc
				.getToolItem().canSelected()))
				&& selected && tracked))
		{
			path = GraphicsUtil.createRoundRectangle(glow, 2);
			gc.setClipping(path);
			path.dispose();

			Color glowColor = themeRender.getTool_item_bg_glow_track();

			if (dc.getToolItem() != null && (dc.getToolItem().getStyle() & SWT.CHECK) != 0
					&& dc.getToolItem().getSelection())
			{
				if (tracked)
				{
					glowColor = themeRender.getTool_item_bg_checked_glow_track();
				}
				else
				{
					glowColor = themeRender.getTool_item_bg_checked_glow();
				}
			}

			path = createBottomRadialPath(glow);
			float[] point = new float[2];
			float[] bounds = new float[4];
			path.getBounds(bounds);
			point[0] = (bounds[0] + bounds[0] + bounds[2]) / 2f;
			point[1] = (bounds[1] + bounds[1] + bounds[3]) / 2f;
			GCExtension extension = new GCExtension(gc);
			extension.fillGradientPath(path, point, glowColor, 255, new Color[] { glowColor },
					new int[] { 0 });
			path.dispose();
			gc.setClipping((Region) null);
		}
		gc.setAdvanced(false);
	}

	private Path createBottomRadialPath(Rectangle glow)
	{
		float[] bounds = new float[4];
		bounds[0] = glow.x;
		bounds[1] = glow.y;
		bounds[2] = glow.width;
		bounds[3] = glow.height;
		bounds[0] -= bounds[2] * .35f;
		bounds[1] -= bounds[3] * .15f;
		bounds[2] *= 1.7f;
		bounds[3] *= 2.3f;
		return GraphicsUtil.createEllipsePath(bounds);
	}

	/*
	 * Three status: normal, hover(VS2003), disable.
	 */
	private void drawImageAndText(GC gc, ToolDrawCommand mc, boolean selected, boolean tracked)
	{
		gc.setAdvanced(false);
		Rectangle drawRect = mc.getSelectRect();
		if (mc.getToolItem().getMenu() != null) drawRect = new Rectangle(drawRect.x,
				drawRect.y, drawRect.width - SUBMENUWIDTH, drawRect.height);
		int left = drawRect.x;
		int top = drawRect.y;
		int right = drawRect.width + left;
		int bottom = drawRect.height + top;
		int length = gc.textExtent(mc.getToolItem().getText(), DRAW_FLAGS).x;
		int height = gc.getFontMetrics().getHeight();
		String text = null;
		if (mc.getToolItem().getText().length() > 0) text = mc.getToolItem().getText();
		Image image = mc.getToolItem().getImage();

		if (text == null && hasText && ((style & SWT.TOP) != 0 || (style & SWT.BOTTOM) != 0)) text = "";

		if (text != null && image != null)
		{

			if ((style & SWT.TOP) != 0)
			{
				int x = left + Math.round((drawRect.width - image.getImageData().width) / 2f);
				int y = top + Math.round((imageHeight - image.getImageData().height) / 2f);
				drawImage(gc, mc, x, y, tracked);
				gc.drawText(text, left + Math.round((drawRect.width - length) / 2f), top
						+ imageHeight + VERTICAL_MARGIN, DRAW_FLAGS);
			}
			else if ((style & SWT.BOTTOM) != 0)
			{
				int x = left + Math.round((drawRect.width - image.getImageData().width) / 2f);
				int y = bottom - imageHeight
						+ Math.round((imageHeight - image.getImageData().height) / 2f);
				drawImage(gc, mc, x, y, tracked);
				gc
						.drawText(text, left + Math.round((drawRect.width - length) / 2f), top
								+ drawRect.height - imageHeight - VERTICAL_MARGIN - height,
								DRAW_FLAGS);
			}
			else if ((style & SWT.RIGHT) != 0)
			{
				gc.drawText(text, right - imageWidth - HORIZON_MARGIN - length, top
						+ Math.round((drawRect.height - height) / 2f), DRAW_FLAGS);
				int x = (right - imageWidth) + (imageWidth - image.getImageData().width) / 2;
				int y = top + Math.round((drawRect.height - image.getImageData().height) / 2f);
				drawImage(gc, mc, x, y, tracked);
			}
			else
			{
				int x = left + (imageWidth - image.getImageData().width) / 2;
				int y = top + Math.round((drawRect.height - image.getImageData().height) / 2f);
				drawImage(gc, mc, x, y, tracked);
				gc.drawText(text, left + HORIZON_MARGIN + imageWidth, top
						+ Math.round((drawRect.height - height) / 2f), DRAW_FLAGS);
			}
		}
		else
		{
			if (text != null)
			{
				gc.drawText(text, left + Math.round((drawRect.width - length) / 2f), top
						+ Math.round((drawRect.height - height) / 2f), DRAW_FLAGS);
			}
			else if (image != null)
			{
				int x = left + Math.round((drawRect.width - image.getImageData().width) / 2f);
				int y = top + Math.round((drawRect.height - image.getImageData().height) / 2f);
				drawImage(gc, mc, x, y, tracked);
			}
		}

		if (mc.getToolItem().getMenu() != null)
		{
			Rectangle menuRect = getMenuRect(mc);
			gc.setAdvanced(false);
			if (mc.isEnabled())
			{
				if (tracked) gc.setBackground(theme.getTool_item_arrow_bg_track());
				else
				{
					gc.setBackground(theme.getTool_item_arrow_bg());
				}
			}
			else
				gc.setBackground(theme.getTool_item_arrow_bg_disabled());
			GraphicsUtil.drawArrow(gc, menuRect, SWT.DOWN);

			if (tracked && !(menuTracked && selected) && mc.getToolItem().canSelected()
					&& mc.getToolItem().isEnabled())
			{
				if (!(theme instanceof OfficeThemeRender))
				{
					if (theme instanceof GeneralThemeRender) gc
							.setForeground(((GeneralThemeRender) theme)
									.getTool_item_check_separater_color());
					else if (theme instanceof GlossyThemeRender) gc
							.setForeground(((GlossyThemeRender) theme)
									.getTool_item_check_separater_color());
					gc.drawLine(menuRect.x, menuRect.y + 1, menuRect.x, menuRect.y
							+ menuRect.height - 1);
				}
				else
				{
					if (!selected)
					{
						gc.setForeground(((OfficeThemeRender) theme)
								.getTool_item_check_separater_drakcolor());
						gc.drawLine(menuRect.x, menuRect.y + 1, menuRect.x, menuRect.y
								+ menuRect.height - 1);
						gc.setForeground(((OfficeThemeRender) theme)
								.getTool_item_check_separater_lightcolor());
						gc.drawLine(menuRect.x + 1, menuRect.y + 1, menuRect.x + 1, menuRect.y
								+ menuRect.height - 1);
					}
				}
			}
		}
	}

	private Rectangle getMenuRect(ToolDrawCommand mc)
	{
		int menuLeft = mc.getSelectRect().x + mc.getSelectRect().width + 3 - SUBMENUWIDTH;
		int menuRight = mc.getDrawRect().x + mc.getDrawRect().width;
		Rectangle menuRect = new Rectangle(menuLeft, mc.getDrawRect().y, menuRight - menuLeft,
				mc.getDrawRect().height);
		return menuRect;
	}

	private Rectangle getNonMenuRect(ToolDrawCommand mc)
	{
		int left = mc.getDrawRect().x;
		int right = mc.getSelectRect().x + mc.getSelectRect().width + 3 - SUBMENUWIDTH;
		Rectangle menuRect = new Rectangle(left, mc.getDrawRect().y, right - left, mc
				.getDrawRect().height);
		return menuRect;
	}

	private void drawImage(GC gc, ToolDrawCommand mc, int x, int y, boolean tracked)
	{
		Image image = mc.getToolItem().getImage();
		Image disableImage = mc.getToolItem().getDisableImage();
		if (image != null)
		{
			if (mc.isEnabled())
			{
				if (tracked && !selected
						&& (theme.isShowToolImageShadow() && GraphicsUtil.checkGdip()))
				{
					ImageData shadowImage = image.getImageData();
					PaletteData palette = new PaletteData(new RGB[] { new RGB(0, 0, 0),
							new RGB(154, 156, 146) });
					ImageData data = new ImageData(shadowImage.width, shadowImage.height, 1,
							palette);
					data.transparentPixel = shadowImage.transparentPixel;
					if (shadowImage.getTransparencyType() == SWT.TRANSPARENCY_PIXEL)
					{
						for (int pixelX = 0; pixelX < shadowImage.width; pixelX++)
						{
							for (int pixelY = 0; pixelY < shadowImage.width; pixelY++)
							{
								int dstPixel = shadowImage.getPixel(pixelX, pixelY);
								if (dstPixel != shadowImage.transparentPixel)
								{
									data.setPixel(pixelX, pixelY, 1);
								}
								else
									data.setPixel(pixelX, pixelY, 0);

							}
						}

						gc.setAdvanced(true);

						Image tempImage = new Image(gc.getDevice(), data);
						gc.drawImage(tempImage, x + 1, y + 1);
						tempImage.dispose();
						gc.setAdvanced(false);
						gc.drawImage(image, x - 1, y - 1);
					}
					else
					{
						gc.drawImage(image, x, y);
					}
				}
				else
				{
					if (GraphicsUtil.checkGdip() && theme.isShowToolImageShadow())
					{
						RGB[] rgbs = image.getImageData().palette.getRGBs();
						if (rgbs != null)
						{
							for (int i = 0; i < rgbs.length; i++)
							{
								RGB rgb = rgbs[i];
								rgb.red = (rgb.red + 76) - (((rgb.red + 32) / 64) * 19);
								rgb.green = (rgb.green + 76) - (((rgb.green + 32) / 64) * 19);
								rgb.blue = (rgb.blue + 76) - (((rgb.blue + 32) / 64) * 19);
							}
							ImageData shadowImage = image.getImageData();
							ImageData data = new ImageData(shadowImage.width,
									shadowImage.height, shadowImage.depth, new PaletteData(
											rgbs));
							data.transparentPixel = shadowImage.transparentPixel;
							for (int pixelX = 0; pixelX < shadowImage.width; pixelX++)
							{
								for (int pixelY = 0; pixelY < shadowImage.width; pixelY++)
								{
									int dstPixel = shadowImage.getPixel(pixelX, pixelY);
									if (dstPixel != shadowImage.transparentPixel)
									{
										data.setPixel(pixelX, pixelY, dstPixel);
									}
									else
										data.setPixel(pixelX, pixelY,
												shadowImage.transparentPixel);
								}
							}
							Image fadedImage = new Image(null, data);
							gc.drawImage(fadedImage, x, y);
							fadedImage.dispose();
						}
					}
					else
						gc.drawImage(image, x, y);
				}
			}
			else
			{
				// Draw a image disabled
				if (disableImage == null)
				{
					disableImage = new Image(null, image, SWT.IMAGE_DISABLE);
					gc.drawImage(disableImage, x, y);
					disableImage.dispose();
				}
				else
				{
					gc.drawImage(disableImage, x, y);
				}

			}
		}

	}

	private void drawGradientBack(GC g, Rectangle rect, Color[] colors)
	{
		g.setAdvanced(true);
		Rectangle backRect = new Rectangle(rect.x, rect.y, rect.width + 1, rect.height + 1);
		Rectangle backRect1 = GraphicsUtil.inflate(backRect, -1, -1);
		int height = backRect1.height / 2;
		Rectangle rect1 = new Rectangle(backRect1.x, backRect1.y, backRect1.width, height);
		Rectangle rect2 = new Rectangle(backRect1.x, backRect1.y + height, backRect1.width,
				backRect1.height - height);

		g.setForeground(colors[0]);
		g.setBackground(colors[1]);
		g.fillGradientRectangle(rect1.x, rect1.y, rect1.width, rect1.height, true);

		g.setForeground(colors[2]);
		g.setBackground(colors[3]);
		g.fillGradientRectangle(rect2.x, rect2.y, rect2.width, rect2.height, true);

		Rectangle backRect2 = GraphicsUtil.inflate(backRect1, -1, -1);
		height = backRect2.height / 2;
		rect1 = new Rectangle(backRect2.x, backRect2.y, backRect2.width, height);
		rect2 = new Rectangle(backRect2.x, backRect2.y + height, backRect2.width,
				backRect2.height - height);

		g.setForeground(colors[4]);
		g.setBackground(colors[5]);
		g.fillGradientRectangle(rect1.x, rect1.y, rect1.width, rect1.height, true);

		g.setForeground(colors[6]);
		g.setBackground(colors[7]);
		g.fillGradientRectangle(rect2.x, rect2.y, rect2.width, rect2.height, true);

		g.setAdvanced(false);
	}

	private void drawGradientBorder(GC g, Rectangle rect, Color[] colors)
	{
		if (GraphicsUtil.checkGdip())
		{
			g.setAdvanced(true);
			Rectangle backRect = new Rectangle(rect.x, rect.y, rect.width, rect.height);
			Path path = GraphicsUtil.createRoundPath(backRect, 1.2f);
			g.setForeground(colors[8]);
			g.drawPath(path);
			path.dispose();
			g.setAdvanced(false);
		}
		else
		{
			g.setForeground(colors[8]);
			g.drawRectangle(rect);
		}
	}

	public Control getControl()
	{
		return toolbar;
	}

	protected ToolDrawCommand getCurrentCommand()
	{
		if (toolbar.isDisposed()) return null;

		if (trackItemIndex < 0 || trackItemIndex >= drawCommands.size()) return null;
		return (ToolDrawCommand) drawCommands.get(trackItemIndex);
	}

	public CToolItem getSelection()
	{
		if (trackItemIndex == -1) return null;
		else
		{
			ToolDrawCommand dc = (ToolDrawCommand) drawCommands.get(trackItemIndex);
			if (dc == null) return null;
			else
				return dc.getToolItem();
		}
	}

	public int getSelectionIndex()
	{
		return trackItemIndex;
	}

	public Shell getShell()
	{
		if (toolbar.isDisposed()) return null;
		return toolbar.getShell();
	}

	public ThemeRender getTheme()
	{
		return theme;
	}

	private boolean isFocusControl = false;

	boolean isFocusControl()
	{
		return isFocusControl;
	}

	void setFocusControl(boolean focus)
	{
		isFocusControl = focus;
	}

	private void handleMouseMoveEvent(Point screenPos)
	{
		if (!checkEnableHandleEvent()) return;
		for (int i = 0; i < drawCommands.size(); i++)
		{
			final ToolDrawCommand command = ((ToolDrawCommand) drawCommands.get(i));
			if (command.getDrawRect().contains(screenPos.x, screenPos.y))
			{
				if (trackItemIndex != i)
				{
					if ( command.isSeparator( ) && isFocusControl( ) )
						return;
					selected = false;
					toolbar.setToolTipText(null);
					if (popupMenu != null)
					{
						popupMenu.hideMenu();
						popupMenu = null;
					}
					refresh();
					trackItemIndex = i;
					if (command.getToolItem() != null
							&& command.getToolItem().getToolTip() != null)
					{
						toolbar.setToolTipText(command.getToolItem().getToolTip());
					}
					refresh();
					if (!isShowMenu() && command.getToolItem() != null
							&& command.getToolItem().getMenu() != null)
					{
						if (getMenuRect(command).contains(screenPos))
						{
							menuTracked = true;
						}
						else
							menuTracked = false;
					}
					else
					{
						menuTracked = false;
					}
					drawCommand(trackItemIndex, true);
					return;
				}
				return;
			}
		}
		toolbar.setToolTipText(null);
		if ( trackItemIndex >= 0 )
		{
			if (!selected)
			{
				refresh();
				drawCommand(trackItemIndex, false);
				trackItemIndex = -1;
			}
			else
			{
				if (!isShowMenu())
				{
					selected = false;
					refresh();
					drawCommand(trackItemIndex, false);
					trackItemIndex = -1;
				}
			}
		}
	}

	protected boolean checkEnableHandleEvent()
	{
		MenuHolder holder = MenuHolderManager.getActiveHolder();
		if (holder != null && holder != CToolBar.this) return false;
		return true;
	}

	public void hideMenu()
	{
		hideCurrentMenu();
	}

	private void hideCurrentMenu()
	{
		if (popupMenu != null)
		{
			popupMenu.hideMenu();
			popupMenu = null;
		}
		if (toolbar.isDisposed()) return;
		selected = false;
		refresh();
		if (!isFocusControl())
		{
			drawCommand(trackItemIndex, false);
			trackItemIndex = -1;
		}
	}

	public boolean isMultiLine()
	{
		return (style & SWT.MULTI) != 0;
	}

	private boolean isShowMenu()
	{
		return popupMenu != null && !popupMenu.getShell().isDisposed();
	}

	private void onPaint(PaintEvent e)
	{
		if (theme instanceof GlossyThemeRender)
		{
			drawGlossyThemeBackGround(e.gc);
		}
		recalculate();
		toolbar.getParent().layout();
		drawAllCommands(e.gc);
	}

	private void drawGlossyThemeBackGround(GC gc)
	{
		GlossyThemeRender themeRender = (GlossyThemeRender) theme;
		Rectangle glossyRect = new Rectangle(0, 0, toolbar.getSize().x,
				toolbar.getSize().y / 2);
		Pattern pattern = new Pattern(null, 0, 0, 0, glossyRect.height, themeRender
				.getToolbar_bg_glossy_color1(), themeRender
				.getToolbar_bg_glossy_color1_alpha(), themeRender
				.getToolbar_bg_glossy_color2(), themeRender
				.getToolbar_bg_glossy_color2_alpha());
		gc.setBackgroundPattern(pattern);
		Path path = new Path(null);
		path.addRectangle(glossyRect.x, glossyRect.y, glossyRect.width, glossyRect.height);
		gc.fillPath(path);
		path.dispose();

		int glowSize = (int) (toolbar.getSize().y * 0.15);
		Rectangle glowRect = new Rectangle(0, toolbar.getSize().y - glowSize, toolbar
				.getSize().x, glowSize);
		pattern = new Pattern(null, 0, glowRect.y, 0, glowRect.y + glowRect.height,
				themeRender.getToolbar_bg_glow_color1(), themeRender
						.getToolbar_bg_glow_color1_alpha(), themeRender
						.getToolbar_bg_glow_color2(), themeRender
						.getToolbar_bg_glow_color2_alpha());
		gc.setBackgroundPattern(pattern);
		path = new Path(null);
		path.addRectangle(glowRect.x, glowRect.y, glowRect.width, glowRect.height);
		gc.fillPath(path);
		path.dispose();
	}

	private boolean hasText = false;

	protected Rectangle recalculate()
	{
		hasText = false;
		int length = toolbar.getSize().x;

		imageWidth = IMAGE_MIN_WIDTH;
		imageHeight = IMAGE_MIN_HEIGHT;
		for (int i = 0; i < getItemCount(); i++)
		{
			Image image = getItem(i).getImage();
			if (image != null && !image.isDisposed())
			{
				int width = image.getImageData().width;
				int height = image.getImageData().height;
				if (width > imageWidth) imageWidth = width;
				if (height > imageHeight) imageHeight = height;
			}
		}

		if (length > 0)
		{
			int rows = 0;
			int columns = 0;

			int cellMinLength = HORIZON_GAP * 2;
			int lengthStart = HORIZON_MARGIN;

			int verticalStart = 0;
			if ((style & SWT.MULTI) == 0) length -= (HORIZON_MARGIN + CHEVRON_LENGTH + 1);
			else
				length -= HORIZON_GAP;
			drawCommands.clear();
			chevronStartItem = null;
			GC gc = new GC(toolbar);
			int BREADTH_GAP = (gc.getFontMetrics().getHeight() / 3) + 1;
			int rowHeight = gc.getFontMetrics().getHeight() + BREADTH_GAP * 2 + 1;
			if ((style & SWT.TOP) != 0 || (style & SWT.BOTTOM) != 0)
			{
				rowHeight += imageHeight;
				rowHeight += VERTICAL_MARGIN;
			}
			else
			{
				int imageRowHeight = imageHeight + BREADTH_GAP * 2 + 1;
				rowHeight = (imageRowHeight > rowHeight ? imageRowHeight : rowHeight);
			}
			// rowHeight += (VERTICAL_GAP * 2 + 1);
			int index = 0;
			for (int i = 0; i < getItemCount(); i++)
			{
				CToolItem item = getItem(i);
				if (!item.isVisible()) continue;
				int cellLength = 0;
				if (item.getText().equals("-") || (item.getStyle() & SWT.SEPARATOR) != 0) cellLength = HORIZON_GAP
						+ SEPARATOR_WIDTH;
				else
				{
					if (item.getText() == null)
					{
						cellLength = cellMinLength + imageWidth;
					}
					else if (item.getImage() == null)
					{
						cellLength = cellMinLength
								+ gc.textExtent(item.getText(), DRAW_FLAGS).x;
					}
					else
					{
						if ((style & SWT.TOP) == 0 && (style & SWT.BOTTOM) == 0)
						{
							cellLength = cellMinLength
									+ gc.textExtent(item.getText(), DRAW_FLAGS).x
									+ HORIZON_MARGIN;
							cellLength += imageWidth;
						}
						else
						{
							cellLength = gc.textExtent(item.getText(), DRAW_FLAGS).x;
							cellLength = cellLength < imageWidth ? imageWidth : cellLength;
							cellLength += (cellMinLength);
						}
						hasText = true;
					}
					if (item.getMenu() != null) cellLength += SUBMENUWIDTH;
				}

				Rectangle cellRect = new Rectangle(lengthStart, rowHeight * rows + 2,
						cellLength, rowHeight - 1);
				lengthStart += cellLength;
				columns++;

				if ((lengthStart > length) && (columns > 1))
				{
					if ((style & SWT.MULTI) != 0)
					{
						rows++;
						columns = 1;
						lengthStart = cellLength;
						cellRect.x = HORIZON_MARGIN - 1;
						cellRect.y += rowHeight;

					}
					else
					{
						if (index <= trackItemIndex)
						{
							removeItemTracking();
						}
						chevronStartItem = item;
						cellRect.y = 1;
						cellRect.width = CHEVRON_LENGTH + HORIZON_MARGIN;
						cellRect.x = toolbar.getClientArea().width - cellRect.width - 1;
						cellRect.height = rowHeight + 1;

						Rectangle drawRect = new Rectangle(cellRect.x, cellRect.y,
								cellRect.width, cellRect.height);
						drawRect.height -= 2; // border=1, vertical space = 1;
						// 2border+vertical
						// drawRect.x += 1;// remove border
						// drawRect.width -= 2;

						drawCommands.add(new ToolDrawCommand(drawRect));
						break;
					}
				}
				Rectangle drawRect = new Rectangle(cellRect.x, cellRect.y, cellRect.width,
						cellRect.height);
				drawRect.height -= 2; // border=1, vertical space = 1;
				// 2border+vertical
				// drawRect.x += 1;// remove border
				// drawRect.width -= 2;

				Rectangle selectRect = new Rectangle(cellRect.x, cellRect.y, cellRect.width,
						cellRect.height);
				selectRect.height -= (2 * VERTICAL_GAP + 1);
				selectRect.width -= (2 * HORIZON_GAP + 1);
				selectRect.x += HORIZON_GAP;
				selectRect.y += VERTICAL_GAP;
				drawCommands.add(new ToolDrawCommand(item, drawRect, selectRect));
				index++;
			}
			gc.dispose();
			int controlHeight = (rows + 1) * rowHeight + 2 * verticalStart + 2;
			if (toolbar.getBounds().height != controlHeight)
			{
				Rectangle rect = new Rectangle(toolbar.getBounds().x, toolbar.getBounds().y,
						toolbar.getBounds().width, controlHeight);
				return rect;
			}
		}
		return toolbar.getBounds();
	}

	public void refresh()
	{
		if (toolbar != null && !toolbar.isDisposed()) toolbar.redraw();
	}

	private void removeItemTracking()
	{
		if (trackItemIndex != -1 && trackItemIndex < drawCommands.size())
		{
			ToolDrawCommand dc = (ToolDrawCommand) drawCommands.get(trackItemIndex);
			if (dc.getToolItem() != null)
			{
				Event event = new Event();
				event.data = dc.getToolItem();
				event.detail = SWT.Deactivate;
				toolbar.notifyListeners(SWT.Deactivate, event);
			}
			trackItemIndex = -1;
		}
	}

	public void setBackground(Color color)
	{
		if (toolbar != null && !toolbar.isDisposed()) toolbar.setBackground(color);
	}

	public void setBackground(Color[] colors, int[] percents, boolean vertical)
	{
		if (toolbar != null && !toolbar.isDisposed()) toolbar.setBackground(colors, percents,
				vertical);
	}

	public void setBackgroundImage(Image image)
	{
		if (toolbar != null && !toolbar.isDisposed()) toolbar.setBackgroundImage(image);
	}

	public void setMultiLine(boolean multi)
	{
		if (!multi) style = style & ~SWT.MULTI;
		else
			style |= SWT.MULTI;
	}

	public void setSelection(int index, boolean isSelected)
	{
		if (drawCommands.size() > 0)
		{
			// If no item is currently tracked then...
			if (index > -1 && index < drawCommands.size())
			{
				// ...start tracking the first valid command
				ToolDrawCommand dc = (ToolDrawCommand) drawCommands.get(index);
				if (!dc.isSeparator() && (dc.isChevron() || dc.isEnabled()))
				{
					refresh();
					trackItemIndex = index;
					selected = isSelected;
					refresh();
					drawCommand(trackItemIndex, true);
					if (selected)
					{
						showMenu(dc);
					}
				}
			}
			else
			{
				selected = false;
				refresh();
				drawCommand(trackItemIndex, false);
				trackItemIndex = -1;
				hideCurrentMenu();
			}
		}
	}

	public void setTheme(ThemeRender theme)
	{
		if (toolbar == null || toolbar.isDisposed()) return;
		if (!GraphicsUtil.checkGdip() && theme instanceof GlossyThemeRender) return;
		this.theme = theme;
		if (theme.getToolbar_bgColors_percents() == null)
		{
			toolbar.setBackground(theme.getToolbar_bgColors()[0]);
		}
		else
			toolbar.setBackground(theme.getToolbar_bgColors(), theme
					.getToolbar_bgColors_percents(), true);
	}

	private void showMenu(ToolDrawCommand command)
	{
		if (command == null || getCurrentCommand() == null) return;
		if (command.getToolItem() != getCurrentCommand().getToolItem()) return;
		if (command.isChevron())
		{
			List items = new ArrayList();
			int index = indexOf(chevronStartItem);
			if (index > 0)
			{
				for (int i = index; i < getItemCount(); i++)
				{
					if (!((getItem(i).getStyle() & SWT.SEPARATOR) != 0 || getItem(i).getText()
							.equals("-"))) items.add(getItem(i));
				}
				if (items.size() > 0)
				{
					drawUpwards = false;
					popupMenu = new ToolMenuControl(this, theme, this.style);
					((ToolMenuControl) popupMenu).setToolItems((CToolItem[]) (items
							.toArray(new CToolItem[0])));
					((ToolMenuControl) popupMenu).setTopRightLocation(toolbar.toDisplay(
							toolbar.getSize().x, toolbar.getSize().y));
					popupMenu.createAndShowWindow();
					installMouseHook();
				}
			}

		}
		else
		{
			drawUpwards = false;
			popupMenu = new MenuControl(this, theme);
			popupMenu.menu = command.getToolItem().getMenu();
			popupMenu.screenPos = toolbar.toDisplay(new Point(command.getDrawRect().x, command
					.getDrawRect().y
					+ command.getDrawRect().height));
			popupMenu.bar = this;
			popupMenu.leftScreenPos = popupMenu.screenPos;
			popupMenu.aboveScreenPos = toolbar.toDisplay(new Point(command.getDrawRect().x,
					command.getDrawRect().y + 1));
			// popupMenu.menuBar = this;
			popupMenu.borderGap = command.getDrawRect().width;
			popupMenu.createAndShowWindow();
			installMouseHook();
		}
	}

	private Callback mouseCallback;

	private int newAddress;

	private int oldAddress;

	private void installMouseHook()
	{
		mouseCallback = new Callback(this, "MouseProc", 3);
		newAddress = mouseCallback.getAddress();
		if (newAddress == 0) SWT.error(SWT.ERROR_NO_MORE_CALLBACKS);
		int threadId = Extension.GetCurrentThreadId();
		oldAddress = Extension.SetWindowsHookEx(Win32.WH_MOUSE, newAddress, 0, threadId);
		popupMenu.getControl().addDisposeListener(new DisposeListener()
		{

			public void widgetDisposed(DisposeEvent e)
			{
				if (mouseCallback != null)
				{
					Extension.UnhookWindowsHookEx(oldAddress);
					mouseCallback.dispose();
					mouseCallback = null;
					newAddress = 0;
				}
			}

		});
	}

	int MouseProc(int /* long */code, int /* long */wParam, int /* long */lParam)
	{
		int result = Extension.CallNextHookEx(oldAddress, code, wParam, lParam);
		if (code < 0) return result;
		switch (wParam)
		{
		case Win32.WM_NCLBUTTONDOWN:
		case Win32.WM_NCRBUTTONDOWN:
			hideCurrentMenu();
			break;
		}
		return result;
	}

	private void showMenuShadow(GC gc, Rectangle drawRect, CToolItem mc)
	{
		if (!GraphicsUtil.checkGdip() || !theme.isShowToolItemShadow()) return;
		if (mc == null) return;
		gc.setAdvanced(true);
		if (mc.getMenu() != null && mc.getMenu().getItemCount() > 0 && mc.isEnabled()
				&& (menuTracked || !mc.canSelected()))
		{
			if (drawUpwards)
			{
				int rightLeft = drawRect.x + drawRect.width + 1;
				int rightTop = drawRect.y + 1;
				int top = drawRect.y + drawRect.height + 1;
				int left = drawRect.x + SHADOW_GAP;
				int width = drawRect.width + 1;
				int height = SHADOW_GAP;

				Pattern pattern = new Pattern(gc.getDevice(), rightLeft, 9999, rightLeft
						+ SHADOW_GAP, 9999, theme.getMenuShadowColor(), 48, theme
						.getMenuShadowColor(), 0);
				gc.setForegroundPattern(pattern);
				gc.setBackgroundPattern(pattern);
				gc.fillRectangle(new Rectangle(rightLeft, rightTop, SHADOW_GAP,
						drawRect.height));
				pattern.dispose();

				pattern = new Pattern(gc.getDevice(), left + SHADOW_GAP, top - SHADOW_GAP,
						left, top + height, theme.getMenuShadowColor(), 64, theme
								.getMenuShadowColor(), 0);
				gc.setForegroundPattern(pattern);
				gc.setBackgroundPattern(pattern);
				gc.fillRectangle(new Rectangle(left, top, SHADOW_GAP, height));
				pattern.dispose();

				pattern = new Pattern(gc.getDevice(), left + width - SHADOW_GAP - 2, top
						- SHADOW_GAP - 2, left + width, top + height, theme
						.getMenuShadowColor(), 64, theme.getMenuShadowColor(), 0);
				gc.setForegroundPattern(pattern);
				gc.setBackgroundPattern(pattern);
				gc.fillRectangle(new Rectangle(left + width - SHADOW_GAP, top, SHADOW_GAP - 1,
						height - 1));
				pattern.dispose();

				pattern = new Pattern(gc.getDevice(), 9999, top, 9999, top + height, theme
						.getMenuShadowColor(), 48, theme.getMenuShadowColor(), 0);
				gc.setForegroundPattern(pattern);
				gc.setBackgroundPattern(pattern);
				gc.fillRectangle(new Rectangle(left + SHADOW_GAP, top, width - SHADOW_GAP * 2,
						height));
				pattern.dispose();
			}
			else
			{
				int rightLeft = drawRect.x + drawRect.width + 1;
				int rightTop = drawRect.y + SHADOW_GAP;

				Pattern pattern = new Pattern(gc.getDevice(), rightLeft - SHADOW_GAP, rightTop
						+ SHADOW_GAP, rightLeft + SHADOW_GAP, rightTop, theme
						.getMenuShadowColor(), 64, theme.getMenuShadowColor(), 0);
				gc.setForegroundPattern(pattern);
				gc.setBackgroundPattern(pattern);
				gc.fillRectangle(new Rectangle(rightLeft, drawRect.y + SHADOW_GAP,
						SHADOW_GAP - 2, 1));
				gc.fillRectangle(new Rectangle(rightLeft, drawRect.y + 1 + SHADOW_GAP,
						SHADOW_GAP - 1, 1));
				gc.fillRectangle(new Rectangle(rightLeft, drawRect.y + 2 + SHADOW_GAP,
						SHADOW_GAP, SHADOW_GAP - 2));
				pattern.dispose();

				rightTop += SHADOW_GAP;

				pattern = new Pattern(gc.getDevice(), rightLeft, 9999, rightLeft + SHADOW_GAP,
						9999, theme.getMenuShadowColor(), 48, theme.getMenuShadowColor(), 0);
				gc.setForegroundPattern(pattern);
				gc.setBackgroundPattern(pattern);
				gc.fillRectangle(new Rectangle(rightLeft, rightTop, SHADOW_GAP, drawRect.y
						+ drawRect.height - rightTop + 1));
				pattern.dispose();
			}
		}
		gc.setAdvanced(false);
	}

	public boolean isAcitve()
	{
		return trackItemIndex > -1;
	}

	private List items = new ArrayList();

	public CToolItem addItem(CToolItem value)
	{
		items.add(value);
		return value;
	}

	public void addItems(CToolItem[] values)
	{
		for (int i = 0; i < values.length; i++)
		{
			addItem(values[i]);
		}
	}

	public void removeAll()
	{
		while (items.size() > 0)
			removeItem(getItem(0));
	}

	public void removeItem(CToolItem value)
	{
		value.disposed();
		items.remove(value);
	}

	public void addItem(int index, CToolItem value)
	{
		items.add(index, value);
	}

	public boolean contains(CToolItem value)
	{
		return items.contains(value);
	}

	public int getItemCount()
	{
		return items.size();
	}

	public CToolItem getItem(int index)
	{
		if (index < 0 || index >= items.size()) SWT.error(SWT.ERROR_INVALID_ARGUMENT);
		return (CToolItem) items.get(index);
	}

	public CToolItem getItem(String text)
	{
		for (int i = 0; i < items.size(); i++)
		{
			CToolItem item = getItem(i);
			if (item.getText().equals(text)) return item;
		}
		return null;
	}

	public boolean visibleItems()
	{
		for (int i = 0; i < items.size(); i++)
		{
			CToolItem item = getItem(i);
			if (item.isVisible())
			{
				if (!item.getText().equals("-"))
				{
					if (item.getMenu() != null && item.getMenu().getItemCount() > 0
							&& !item.getMenu().visibleItems()) continue;
					return true;
				}
			}
		}

		return false;
	}

	public int indexOf(CToolItem value)
	{
		return items.indexOf(value);
	}

	private void drawSeparator(GC g, Rectangle rect, Color light, Color dark)
	{
		int x = rect.x + HORIZON_GAP;
		int y = rect.y;
		int bottom = y + rect.height;
		g.setForeground(dark);
		g.drawLine(x, y + VERTICAL_GAP + 1, x, bottom - VERTICAL_GAP + 1);
		g.setForeground(light);
		g.drawLine(x + 1, y + VERTICAL_GAP + 1, x + 1, bottom - VERTICAL_GAP + 1);
	}

	public CMenu getMenu()
	{
		return null;
	}

	public int getStyle()
	{
		return style;
	}

	public void setStyle(int style)
	{
		this.style = style;
		refresh();
	}

	MenuControl getCurrentMenu()
	{
		if (popupMenu != null && popupMenu instanceof ToolMenuControl) return null;
		if (popupMenu != null && popupMenu.trackItemIndex != -1) return popupMenu
				.getCurrentMenu();
		else if (popupMenu != null) return popupMenu;
		return null;
	}

	private Path createTopLeftRoundRectangle(Rectangle rectangle, int radius)
	{
		Path path = new Path(null);

		int l = rectangle.x;
		int t = rectangle.y;
		int w = rectangle.width;
		int h = rectangle.height;
		int d = radius << 1;

		path.addArc(l, t, d, d, -180, -90); // topleft
		path.lineTo(l + radius, t);
		path.lineTo(l + w, t);
		path.lineTo(l + w, t + h);
		path.lineTo(l, t + h);
		path.lineTo(l, t + radius);
		path.close();

		return path;
	}

	private Path createTopRightRoundRectangle(Rectangle rectangle, int radius)
	{
		Path path = new Path(null);

		int l = rectangle.x;
		int t = rectangle.y;
		int w = rectangle.width;
		int h = rectangle.height;
		int d = radius << 1;

		path.moveTo(l, t); // topleft
		path.lineTo(l + w - radius, t);
		path.addArc(l + w - d, t, d, d, -270, -90); // topright
		path.lineTo(l + w, t + radius);
		path.lineTo(l + w, t + h);
		path.lineTo(l, t + h);
		path.lineTo(l, t);
		path.close();

		return path;
	}

	public static Path createLeftRoundPath(Rectangle rectangle, int radius)
	{
		Path path = new Path(null);

		int l = rectangle.x;
		int t = rectangle.y;
		int w = rectangle.width;
		int h = rectangle.height;
		int d = radius << 1;

		path.moveTo(l + w, t);
		path.lineTo(l + radius, t);
		path.addArc(l, t, d, d, 90, 90);

		path.lineTo(l, t + radius);
		path.lineTo(l, t + h - radius);
		path.addArc(l, t + h - d, d, d, 180, 90);
		path.lineTo(l + radius, t + h);
		path.lineTo(l + w, t + h);

		return path;
	}

	public static Path createRightRoundPath(Rectangle rectangle, int radius)
	{
		Path path = new Path(null);

		int l = rectangle.x;
		int t = rectangle.y;
		int w = rectangle.width;
		int h = rectangle.height;
		int d = radius << 1;

		path.moveTo(l, t);
		path.lineTo(l + w - radius, t);
		path.addArc(l + w - d, t, d, d, -270, -90);
		path.lineTo(l + w, t + radius);
		path.lineTo(l + w, t + h - radius);
		path.addArc(l + w - d, t + h - d, d, d, 0, -90);
		path.lineTo(l + w - radius, t + h);
		path.lineTo(l, t + h); // top

		return path;
	}
}
