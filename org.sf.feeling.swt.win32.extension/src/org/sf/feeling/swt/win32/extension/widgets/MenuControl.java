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
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.MouseTrackAdapter;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
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
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.sf.feeling.swt.win32.extension.Win32;
import org.sf.feeling.swt.win32.extension.graphics.GraphicsUtil;
import org.sf.feeling.swt.win32.extension.shell.Windows;
import org.sf.feeling.swt.win32.extension.widgets.theme.GeneralThemeRender;
import org.sf.feeling.swt.win32.extension.widgets.theme.GlossyThemeRender;
import org.sf.feeling.swt.win32.extension.widgets.theme.OfficeThemeRender;
import org.sf.feeling.swt.win32.extension.widgets.theme.ThemeRender;
import org.sf.feeling.swt.win32.internal.extension.graphics.GCExtension;

public class MenuControl
{

	protected List commands;

	protected CMenu menu;

	protected static final int[] POSITION = { 2, 1, 0, 1, 2, 4, 3, 5, 4, 4, 2, 6, 5, 5, 1, 10,
			2, 2, 0, 0 };

	protected static int DRAW_FLAGS = SWT.DRAW_MNEMONIC | SWT.DRAW_TAB | SWT.DRAW_TRANSPARENT
			| SWT.DRAW_DELIMITER;

	protected final static int IMAGE_WIDTH = 16;

	protected final static int IMAGE_HEIGHT = 16;

	protected ThemeRender theme;

	protected Shell shell;

	protected MenuHolder holder;

	protected MenuControl(MenuHolder holder, ThemeRender theme)
	{
		this.holder = holder;
		this.theme = theme;
	}

	protected class PI
	{
		private final static int BorderTop = 0;

		private final static int BorderLeft = 1;

		private final static int BorderBottom = 2;

		private final static int BorderRight = 3;

		private final static int ImageGapTop = 4;

		private final static int ImageGapLeft = 5;

		private final static int ImageGapBottom = 6;

		private final static int ImageGapRight = 7;

		private final static int TextGapLeft = 8;

		private final static int TextGapRight = 9;

		private final static int SubMenuGapLeft = 10;

		private final static int SubMenuWidth = 11;

		private final static int SubMenuGapRight = 12;

		private final static int SeparatorHeight = 13;

		private final static int SeparatorWidth = 14;

		private final static int ShortcutGap = 15;
	};

	protected int trackItemIndex = -1;

	protected boolean ignoreTaskbar = false;

	protected void createAndShowWindow()
	{
		// Decide if we need layered windows

		// Process the menu commands to determine where each one needs to be
		// drawn and return the size of the window needed to display it.
		Point winSize = generateDrawPositions();
		Point posForScreen = correctPositionForScreen(winSize);

		// Special case, if there are no menu options to show then show nothing
		// by making the window 0,0 in size.
		if (menu == null || menu.getItemCount() == 0) winSize = new Point(0, 0);
		if (shell == null || shell.isDisposed())
		{
			/*
			 * Fix win98 swt bug. In Win98, SWT doesn't support SWT.NO_TRIM and
			 * SWT.TOOL well. Pure swt style is SWT.ON_TOP | SWT.NO_TRIM |
			 * SWT.NO_FOCUS | SWT.TOOL, and needn't hide TitleBar.
			 */
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

			shell.addPaintListener(new PaintListener()
			{
				public void paintControl(PaintEvent e)
				{
					refreshAllCommands(e.gc);
				}
			});
			Display.getDefault().addFilter(SWT.MouseDown, new Listener()
			{

				public void handleEvent(Event event)
				{
					if (shell == null || shell.isDisposed())
					{
						Display.getDefault().removeFilter(SWT.MouseDown, this);
						return;
					}
					if (holder.checkMouseDownEvent(((Control) event.widget).toDisplay(event.x,
							event.y)))
					{
						holder.hideMenu();
					}

				}
			});
			shell.addMouseMoveListener(new MouseMoveListener()
			{
				public void mouseMove(MouseEvent e)
				{
					handleMouseMoveEvent(e);
				}
			});
			shell.addMouseTrackListener(new MouseTrackAdapter()
			{
				public void mouseEnter(MouseEvent e)
				{
					handleMouseMoveEvent(e);
				}

				public void mouseExit(MouseEvent e)
				{
					if (trackItemIndex != -1)
					{
						MenuDrawCommand command = ((MenuDrawCommand) commands
								.get(trackItemIndex));
						if (command.getMenuItem().getMenu() == null) handleMouseMoveEvent(e);
					}
				}
			});
			shell.addMouseListener(new MouseAdapter()
			{

				public void mouseUp(MouseEvent e)
				{
					if (trackItemIndex != -1 && e.button == 1)
					{
						handleSelectedEvent();
					}
				}
			});
		}

		shell.setLocation(posForScreen);
		shell.setSize(winSize);
		new Thread()
		{
			public void run()
			{
				try
				{
					if (parentMenu != null)
					{
						Thread.sleep(300);
					}
					else
					{
						Thread.sleep(50);
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
							}
						}
					});

				} catch (InterruptedException e)
				{

				}
			}
		}.start();
	}

	private Point generateDrawPositions()
	{
		// Create a collection of drawing objects
		commands = new ArrayList();
		cellMinHeight = POSITION[PI.ImageGapTop] + IMAGE_HEIGHT + POSITION[PI.ImageGapBottom];

		int cellMinWidth = POSITION[PI.ImageGapLeft] + IMAGE_HEIGHT
				+ POSITION[PI.ImageGapRight] + POSITION[PI.TextGapLeft]
				+ POSITION[PI.TextGapRight] + POSITION[PI.SubMenuGapLeft]
				+ POSITION[PI.SubMenuWidth] + POSITION[PI.SubMenuGapRight];

		GC gc = new GC(holder.getShell());
		// Find cell height needed to draw text
		int textHeight = gc.getFontMetrics().getHeight();

		// If height needs to be more to handle image then use image height
		if (textHeight < cellMinHeight) textHeight = cellMinHeight;

		// Make sure no column in the menu is taller than the screen
		int screenHeight = Display.getDefault().getClientArea().height;

		// Define the starting positions for calculating cells
		int xStart = POSITION[PI.BorderLeft];
		int yStart = POSITION[PI.BorderTop];
		int yPosition = yStart;

		// Largest cell for column defaults to minimum cell width
		int xColumnMaxWidth = cellMinWidth;

		int xPreviousColumnWidths = 0;
		int xMaximumColumnHeight = 0;

		// Track the row/col of each cell
		int row = 0;
		int col = 0;

		// Contains the collection of items in the current column
		ArrayList columnItems = new ArrayList();
		{
			shortCutWidth = 0;
			for (int i = 0; menu != null && i < menu.getItemCount(); i++)
			{
				CMenuItem item = menu.getItem(i);

				// Ignore items that are marked as hidden
				if (!item.isVisible()) continue;

				// If this command has menu items (and so it a submenu item)
				// then check
				// if any of the submenu items are visible. If none are
				// visible then there
				// is no point in showing this submenu item
				if ((item.getMenu() != null && item.getMenu().getItemCount() > 0)
						&& (!item.getMenu().visibleItems())) continue;

				int cellWidth = 0;
				int cellHeight = 0;

				// Is this a horizontal separator?
				if (item.getText().equals("-") || (item.getStyle() & SWT.SEPARATOR) != 0)
				{
					cellWidth = cellMinWidth;
					cellHeight = POSITION[PI.SeparatorHeight];
				}
				else
				{
					// Use precalculated height
					cellHeight = textHeight;

					// Calculate the text width portion of the cell
					Point dimension = gc.textExtent(item.getText(), DRAW_FLAGS);

					// Always add 1 to ensure that rounding is up and not
					// down
					cellWidth = cellMinWidth + dimension.x + 1;

					// Does the menu command have a shortcut defined?
					if (item.getShortcut() != null)
					{
						// Find the width of the shortcut text
						dimension = gc.stringExtent(item.getShortcut().toString());
						if (dimension.x > shortCutWidth) shortCutWidth = dimension.x;
					}
				}

				// If the new cell expands past the end of the screen...
				if ((yPosition + cellHeight) >= screenHeight)
				{
					// .. then need to insert a column break

					// Move row/col tracking to the next column
					row = 0;
					col++;

					// Apply cell width to the current column entries
					applySizeToColumnList(columnItems, xColumnMaxWidth);

					// Move cell position across to start of separator
					// position
					xStart += xColumnMaxWidth;

					// Get width of the separator area
					int xSeparator = POSITION[PI.SeparatorWidth];

					MenuDrawCommand dcSep = new MenuDrawCommand(new Rectangle(xStart, yStart,
							xSeparator, 0), false);

					// Add to list of items for drawing
					commands.add(dcSep);

					// Move over the separator
					xStart += xSeparator;

					// Reset cell position to top of column
					yPosition = yStart;

					// Accumulate total width of previous columns
					xPreviousColumnWidths += xColumnMaxWidth + xSeparator;

					// Largest cell for column defaults to minimum cell
					// width
					xColumnMaxWidth = cellMinWidth;

				}

				// Create a new position rectangle (the width will be reset
				// later once the
				// width of the column has been determined but the other
				// values are correct)
				Rectangle cellRect = new Rectangle(xStart, yPosition, cellWidth, cellHeight);

				// Create a drawing object
				MenuDrawCommand dc = new MenuDrawCommand(item, cellRect, row, col);

				if (commands.size() > 0)
				{
					// Find the previous command (excluding
					// separators) and make it as needed a border
					for (int index = commands.size() - 1; index >= 0; index--)
					{
						if (!((MenuDrawCommand) commands.get(index)).isSeparator())
						{
							((MenuDrawCommand) commands.get(index)).bottomBorder = true;
							break;
						}
					}
				}

				// Add to list of items for drawing
				commands.add(dc);

				// Add to list of items in this column
				columnItems.add(dc);

				// Remember the biggest cell width in this column
				if (cellWidth > xColumnMaxWidth) xColumnMaxWidth = cellWidth;

				// Move down to start of next cell in column
				yPosition += cellHeight;

				// Remember the tallest column in the menu
				if (yPosition > xMaximumColumnHeight) xMaximumColumnHeight = yPosition;

				row++;
			}
			xColumnMaxWidth += POSITION[PI.ShortcutGap] + shortCutWidth + 1;
			// Apply cell width to the current column entries
			applySizeToColumnList(columnItems, xColumnMaxWidth);
		}

		// Must remember to release the HDC resource!
		gc.dispose();

		// Find width/height of window
		int windowWidth = POSITION[PI.BorderLeft] + xPreviousColumnWidths + xColumnMaxWidth
				+ POSITION[PI.BorderRight];

		int windowHeight = POSITION[PI.BorderTop] + xMaximumColumnHeight
				+ POSITION[PI.BorderBottom];

		return new Point(windowWidth, windowHeight);
	}

	private void applySizeToColumnList(ArrayList columnList, int cellWidth)
	{
		// Each cell in the same column needs to be the same width, this has
		// already
		// been calculated and passed in as the widest cell in the column
		for (int i = 0; i < columnList.size(); i++)
		{
			MenuDrawCommand dc = (MenuDrawCommand) columnList.get(i);

			// Grab the current drawing rectangle
			Rectangle cellRect = dc.getDrawRect();

			// Modify the width to that requested
			dc.setDrawRect(new Rectangle(cellRect.x, cellRect.y, cellWidth, cellRect.height));
		}

		// Clear collection out ready for reuse
		columnList.clear();
	}

	protected Point screenPos;

	private boolean excludeTop;

	private int excludeOffset;

	protected MenuControl parentMenu;

	protected Bar bar;

	protected Point aboveScreenPos;

	protected CMenuItem parentMenuItem;

	protected int borderGap;

	protected Point leftScreenPos;

	protected MenuControl popupMenu;

	private int cellMinHeight;

	protected boolean popupRight = true;

	private Point correctPositionForScreen(Point winSize)
	{
		Rectangle screenRect;
		if (!ignoreTaskbar) screenRect = holder.getShell().getMonitor().getClientArea();
		else
			screenRect = holder.getShell().getMonitor().getBounds();
		Point screenPos = this.screenPos;

		int screenWidth = screenRect.width;
		int screenLeft = screenRect.x;
		int screenRight = screenLeft + screenWidth;
		int screenHeight = screenRect.height;
		int screenTop = screenRect.y;
		int screenBottom = screenTop + screenHeight;

		// Default to excluding menu border from top
		excludeTop = true;
		excludeOffset = 0;

		// Calculate the downward position first
		// Ensure the end of the menu is not off the bottom of the screen
		if ((screenPos.y + winSize.y) > screenBottom)
		{
			// If the parent control exists then try and position upwards
			// instead
			if ((bar != null) && (parentMenu == null))
			{
				// Is there space above the required position?
				if ((aboveScreenPos.y - winSize.y) > screenTop)
				{
					// Great...do that instead
					screenPos.y = aboveScreenPos.y - winSize.y;

					// Remember to exclude border from bottom of menu and
					// not the top
					excludeTop = false;

					// Inform parent it needs to redraw the selection
					// upwards
					bar.drawSelectionUpwards();
				}
			}
			else if (isPopupMenu())
			{
				screenPos.y -= winSize.y;
			}

			// Did the above logic still fail?
			if ((screenPos.y + winSize.y) > screenBottom)
			{
				// If not a top level PopupMenu then..
				if (parentMenu != null)
				{
					// Reverse direction of drawing this and submenus

					// Is there space above the required position?
					if ((aboveScreenPos.y - winSize.y) > screenTop)
					{
						screenPos.y = (aboveScreenPos.y + 1) - winSize.y + cellMinHeight;
					}
					else
					{
						screenPos.y = screenTop;
					}
				}
				else
				{
					screenPos.y = screenBottom - winSize.y - 1;

					// If there was a parent command that initiated us
					// (i.e. we are not a submenu)
					if (parentMenuItem != null)
					{
						GC gc = new GC(this.holder.getShell());

						// Find size of the parent command
						Point size = gc.stringExtent(parentMenuItem.getText());

						gc.dispose();
						// Move across the submenu so it does not cover
						// the parent command
						screenPos.x += size.x;

						// Do not leave a part of the border empty, as
						// we cannot link up with parent
						borderGap = 0;

					}
				}
			}
		}

		// Calculate the across position next
		if (popupRight)
		{
			// Ensure that right edge of menu is not off right edge of screen
			if ((screenPos.x + winSize.x) > screenRight)
			{
				// If not a top level PopupMenu then...
				if (parentMenu != null || isPopupMenu())
				{
					// Reverse direction
					popupRight = false;

					// Adjust across position
					screenPos.x = leftScreenPos.x - winSize.x;

					if (screenPos.x < screenLeft)
					{
						screenPos.x = screenLeft;
					}

				}
				else
				{
					// Find new position of X coordinate
					int newX = screenRight - winSize.x - 1;
					// Modify the adjust needed when drawing top/bottom
					// border
					excludeOffset = screenPos.x - newX;
					// Use new position for popping up menu
					screenPos.x = newX;
				}
			}
		}
		else
		{
			// Start by using the left screen pos instead
			screenPos.x = leftScreenPos.x;

			// Ensure the left edge of the menu is not off the left of the
			// screen
			if ((screenPos.x - winSize.x) < screenLeft)
			{
				// Reverse direction
				popupRight = true;

				// Is there space below the required position?
				if ((this.screenPos.x + winSize.x) > screenRight) screenPos.x = screenRight
						- winSize.x - 1;
				else
					screenPos.x = this.screenPos.x;
			}
			else
				screenPos.x -= winSize.x;
		}

		return screenPos;
	}

	protected boolean isPopupMenu = false;

	private int shortCutWidth;

	private boolean isPopupMenu()
	{
		return isPopupMenu;
	}

	private void refreshAllCommands(GC gc)
	{
		drawBackground(gc, shell.getSize());
		drawAllCommands(gc);
		if (trackItemIndex != -1) drawCommand(gc, trackItemIndex, true);
	}

	private void drawAllCommands(GC g)
	{
		for (int i = 0; i < commands.size(); i++)
		{
			// Grab some commonly used values
			MenuDrawCommand dc = (MenuDrawCommand) commands.get(i);

			// Draw this command only
			drawSingleCommand(g, dc, false);
		}
	}

	private void drawBackground(GC g, Point rectWin)
	{
		// Calculate some common values
		Rectangle main = new Rectangle(0, 0, rectWin.x - 1, rectWin.y - 1);
		int imageColWidth = POSITION[PI.ImageGapLeft] + IMAGE_WIDTH
				+ POSITION[PI.ImageGapRight];

		int xStart = POSITION[PI.BorderLeft];
		int yStart = POSITION[PI.BorderTop];
		int yHeight = main.height - yStart - POSITION[PI.BorderBottom] - 1;
		Rectangle imageRect = new Rectangle(xStart, yStart, imageColWidth, yHeight);
		// Style specific drawing

		g.setBackground(theme.getMenu_control_bg());
		g.fillRectangle(main);

		// Draw single line border around the main area
		g.setForeground(theme.getMenu_control_fg());
		g.drawRectangle(main);

		// Should the border be drawn with part of the border
		// missing?
		if (borderGap > 0)
		{
			// Remove the appropriate section of the border
			// if (direction == SWT.HORIZONTAL) {
			g.setBackground(theme.getMenu_control_bg());
			int width = borderGap - 1;
			if (theme instanceof GlossyThemeRender) width++;
			if (excludeTop)
			{
				g.fillRectangle(main.x + 1 + excludeOffset, main.y, width, 1);
			}
			else
			{
				g.fillRectangle(main.x + 1 + excludeOffset, main.y + main.height, width, 1);
			}
		}
		if (theme instanceof GeneralThemeRender || theme instanceof GlossyThemeRender)
		{
			g.setBackground(theme.getMenu_control_image_rect_bg());
			g.setForeground(theme.getMenu_control_image_rect_fg());
			g.fillGradientRectangle(imageRect.x, imageRect.y, imageRect.width,
					imageRect.height, false);
		}
		else if (theme instanceof OfficeThemeRender)
		{
			g.setBackground(theme.getMenu_control_image_rect_bg());
			g.fillRectangle(imageRect);
			g.setForeground(theme.getMenu_control_image_rect_fg());
			g.drawLine(imageRect.x + imageRect.width - 1, imageRect.y + 1, imageRect.x
					+ imageRect.width - 1, imageRect.y + imageRect.height - 2);
		}
	}

	private void drawSingleCommand(GC g, MenuDrawCommand dc, boolean hotCommand)
	{
		Rectangle drawRect = dc.getDrawRect();
		CMenuItem mc = dc.getMenuItem();

		// Remember some often used values
		int textGapLeft = POSITION[PI.TextGapLeft];
		int imageGapLeft = POSITION[PI.ImageGapLeft];
		int imageGapRight = POSITION[PI.ImageGapRight];
		int imageLeft = drawRect.x + imageGapLeft;

		// Calculate some common values
		int imageColWidth = imageGapLeft + IMAGE_WIDTH + imageGapRight;

		int subMenuX = drawRect.x + drawRect.width - POSITION[PI.SubMenuGapRight]
				- POSITION[PI.SubMenuWidth];

		// Text drawing rectangle needs to know the right most position for
		// drawing to stop. This is the width of the window minus the relevant
		// values
		int shortCutX = subMenuX - POSITION[PI.SubMenuGapLeft] - POSITION[PI.TextGapRight];

		// Is this item a separator?
		if (dc.isSeparator())
		{
			// Draw the image column background
			g.setForeground(theme.getMenu_control_item_separater_color());
			g.drawLine(drawRect.x + imageColWidth + textGapLeft, drawRect.y + 2, drawRect.x
					+ drawRect.width - 3, drawRect.y + 2);

		}
		else
		{
			int leftPos = drawRect.x + imageColWidth + textGapLeft;

			// Should the command be drawn selected?
			if (hotCommand)
			{
				Rectangle selectArea = new Rectangle(drawRect.x + 1, drawRect.y,
						drawRect.width - 3, drawRect.height - 1);

				if (theme instanceof GeneralThemeRender)
				{
					g.setBackground(((GeneralThemeRender) theme)
							.getMenu_control_item_bg_track());
					g.fillRectangle(selectArea);
					g.setForeground(((GeneralThemeRender) theme)
							.getMenu_control_item_border_track());
					g.drawRectangle(selectArea);
				}
				else if (theme instanceof OfficeThemeRender)
				{
					OfficeThemeRender themeRender = (OfficeThemeRender) theme;
					Color[] colors = new Color[] {
							themeRender.getMenu_control_item_outer_top_track1(),
							themeRender.getMenu_control_item_outer_top_track2(),
							themeRender.getMenu_control_item_outer_bottom_track1(),
							themeRender.getMenu_control_item_outer_bottom_track2(),
							themeRender.getMenu_control_item_inner_top_track1(),
							themeRender.getMenu_control_item_inner_top_track2(),
							themeRender.getMenu_control_item_inner_bottom_track1(),
							themeRender.getMenu_control_item_inner_bottom_track2(),
							themeRender.getMenu_control_item_border_track() };
					drawGradientBack(g, selectArea, colors);
					drawGradientBorder(g, selectArea, colors);
				}
				else if (theme instanceof GlossyThemeRender)
				{
					GlossyThemeRender themeRender = (GlossyThemeRender) theme;
					Rectangle outerBorder = new Rectangle(selectArea.x, selectArea.y,
							selectArea.width, selectArea.height);
					Rectangle innerBorder = GraphicsUtil.inflate(outerBorder, -1, -1);
					Rectangle glossy = new Rectangle(innerBorder.x, innerBorder.y,
							innerBorder.width, innerBorder.height / 2);
					Rectangle glow = GraphicsUtil.createRectangleFromLTRB(outerBorder.x,
							outerBorder.y + Math.round(outerBorder.height * .5f),
							outerBorder.x + outerBorder.width, outerBorder.y
									+ outerBorder.height);

					g.setBackground(themeRender.getMenu_control_item_bg_track());
					g.fillRectangle(innerBorder);
					g.setAntialias(SWT.ON);

					Pattern pattern = new Pattern(null, 0, glossy.y, 0, glossy.y
							+ glossy.height, themeRender.getMenu_control_item_bg_color1(),
							themeRender.getMenu_control_item_bg_color1_alpha(), themeRender
									.getMenu_control_item_bg_color2(), themeRender
									.getMenu_control_item_bg_color2_alpha());
					Path path = new Path(null);
					path.addRectangle(glossy.x, glossy.y, glossy.width, glossy.height);
					g.setBackgroundPattern(pattern);
					g.fillPath(path);
					path.dispose();

					// draw border
					path = GraphicsUtil.createRoundRectangle(outerBorder, 2);
					g.setForeground(themeRender.getMenu_control_item_outer_border());
					g.drawPath(path);
					path.dispose();

					pattern = new Pattern(null, 0, glossy.y, 0, glossy.y + glossy.height,
							themeRender.getMenu_control_item_bg_glossy_track1(), themeRender
									.getMenu_control_item_bg_glossy_track1_alpha(),
							themeRender.getMenu_control_item_bg_glossy_track2(), themeRender
									.getMenu_control_item_bg_glossy_track2_alpha());

					path = GraphicsUtil.createTopRoundRectangle(glossy, 2);
					g.setBackgroundPattern(pattern);
					g.fillPath(path);
					path.dispose();

					Color innerBorderColor = themeRender.getMenu_control_item_inner_border();

					path = GraphicsUtil.createRoundRectangle(innerBorder, 2);
					g.setForeground(innerBorderColor);
					g.drawPath(path);
					path.dispose();

					path = GraphicsUtil.createRoundRectangle(glow, 2);
					g.setClipping(path);
					path.dispose();

					Color glowColor = themeRender.getMenu_control_item_bg_glow();

					path = createBottomRadialPath(glow);
					float[] point = new float[2];
					float[] bounds = new float[4];
					path.getBounds(bounds);
					point[0] = (bounds[0] + bounds[0] + bounds[2]) / 2f;
					point[1] = (bounds[1] + bounds[1] + bounds[3]) / 2f;
					GCExtension extension = new GCExtension(g);
					extension.fillGradientPath(path, point, glowColor, 255,
							new Color[] { glowColor }, new int[] { 0 });
					path.dispose();

					g.setClipping((Path) null);
					g.setAdvanced(false);
				}
			}

			if (mc != null)
			{
				// Calculate text drawing rectangle
				Rectangle strRect = new Rectangle(leftPos, drawRect.y, shortCutX - leftPos,
						drawRect.height);

				if (dc.isEnabled())
				{
					g.setForeground(theme.getMenu_control_item_fg());
				}
				else
					g.setForeground(theme.getMenu_control_item_fg_disabled());
				int lineHeight = g.getFontMetrics().getHeight();
				g.drawText(mc.getText(), strRect.x, strRect.y + (strRect.height - lineHeight)
						/ 2, DRAW_FLAGS);

				if (mc.getShortcut() != null)
				{
					int lineWidth = shortCutWidth;
					g.drawText(mc.getShortcut().toString(), strRect.x + strRect.width
							- lineWidth, strRect.y + (strRect.height - lineHeight) / 2,
							DRAW_FLAGS);
				}

				int imageTop = drawRect.y + (drawRect.height - IMAGE_HEIGHT) / 2;

				if (dc.hasSubMenu())
				{
					if (dc.isEnabled())
					{
						if (hotCommand) g.setBackground(theme
								.getMenu_control_item_arrow_bg_track());
						else
							g.setBackground(theme.getMenu_control_item_arrow_bg());
					}
					else
						g.setBackground(theme.getMenu_control_item_arrow_bg_disabled());
					GraphicsUtil.drawArrow(g, new Rectangle(drawRect.x + drawRect.width
							- POSITION[PI.SubMenuWidth], drawRect.y,
							POSITION[PI.SubMenuGapRight] - POSITION[PI.SubMenuWidth],
							drawRect.height), SWT.RIGHT);
				}
				if (((dc.getMenuItem().getStyle() & SWT.CHECK) != 0 || (dc.getMenuItem()
						.getStyle() & SWT.RADIO) != 0)
						&& dc.getMenuItem().getSelection())
				{
					Rectangle boxRect = new Rectangle(imageLeft - 1, imageTop - 1,
							IMAGE_HEIGHT + 2, IMAGE_WIDTH + 2);
					if (theme instanceof GeneralThemeRender)
					{
						if (dc.isEnabled())
						{
							if (hotCommand)
							{
								g.setBackground(theme
										.getMenu_control_item_check_rect_bg_track());
								g.setForeground(theme
										.getMenu_control_item_check_rect_border_track());
							}
							else
							{
								g.setBackground(theme.getMenu_control_item_check_rect_bg());
								g
										.setForeground(theme
												.getMenu_control_item_check_rect_border());
							}
						}
						else
						{
							g.setBackground(theme
									.getMenu_control_item_check_rect_bg_disabled());
							g.setForeground(theme
									.getMenu_control_item_check_rect_border_disabled());
						}
						g.fillRectangle(boxRect);
						g.drawRectangle(boxRect);
					}
					else if (theme instanceof OfficeThemeRender
							|| theme instanceof GlossyThemeRender)
					{
						Color bgColor, fgColor;
						if (dc.isEnabled())
						{
							if (hotCommand)
							{
								bgColor = theme.getMenu_control_item_check_rect_bg_track();
								fgColor = theme.getMenu_control_item_check_rect_border_track();
							}
							else
							{
								bgColor = theme.getMenu_control_item_check_rect_bg();
								fgColor = theme.getMenu_control_item_check_rect_border();
							}
						}
						else
						{
							bgColor = theme.getMenu_control_item_check_rect_bg_disabled();
							fgColor = theme.getMenu_control_item_check_rect_border_disabled();
						}
						Color[] colors = new Color[] { bgColor, bgColor, bgColor, bgColor,
								bgColor, bgColor, bgColor, bgColor, fgColor };
						if (theme instanceof GlossyThemeRender) boxRect = GraphicsUtil
								.inflate(boxRect, -1, -1);
						drawGradientBack(g, boxRect, colors);
						drawGradientBorder(g, boxRect, colors);
					}
				}

				Image image = null;
				Image disableImage = null;
				if ((dc.getMenuItem().getStyle() & SWT.CHECK) != 0
						&& dc.getMenuItem().getSelection())
				{
					image = theme.getCheckImage();
				}
				else if ((dc.getMenuItem().getStyle() & SWT.RADIO) != 0
						&& dc.getMenuItem().getSelection())
				{
					image = theme.getRedioImage();
				}
				else
				{
					image = mc.getImage();
					disableImage = mc.getDisableImage();
				}

				// Is there an image to be drawn?
				if (image != null)
				{
					ImageData imageData = image.getImageData();
					int x = imageLeft + (IMAGE_WIDTH - imageData.width) / 2;
					int y = imageTop + (IMAGE_HEIGHT - imageData.height) / 2;
					if (mc.isEnabled())
					{
						if (hotCommand
								&& (theme.isShowMenuImageShadow() && GraphicsUtil.checkGdip())
								&& ((dc.getMenuItem().getStyle() & SWT.CHECK) == 0 && (dc
										.getMenuItem().getStyle() & SWT.RADIO) == 0))
						{
							ImageData shadowImage = image.getImageData();
							PaletteData palette = new PaletteData(new RGB[] {
									new RGB(0, 0, 0), new RGB(154, 156, 146) });
							ImageData data = new ImageData(shadowImage.width,
									shadowImage.height, 1, palette);
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
								boolean advanced = g.getAdvanced();
								if (!advanced)
								{
									g.setAdvanced(true);
								}
								Image tempImage = new Image(g.getDevice(), data);
								g.drawImage(tempImage, x + 1, y + 1);
								tempImage.dispose();
								g.setAdvanced(advanced);
								g.drawImage(image, x - 1, y - 1);
							}
							else
							{
								g.drawImage(image, x, y);
							}
						}
						else
						{
							if (theme.isShowMenuImageShadow() && GraphicsUtil.checkGdip())
							{
								RGB[] rgbs = image.getImageData().palette.getRGBs();
								if (rgbs != null)
								{
									for (int i = 0; i < rgbs.length; i++)
									{
										RGB rgb = rgbs[i];
										rgb.red = (rgb.red + 76)
												- (((rgb.red + 32) / 64) * 19);
										rgb.green = (rgb.green + 76)
												- (((rgb.green + 32) / 64) * 19);
										rgb.blue = (rgb.blue + 76)
												- (((rgb.blue + 32) / 64) * 19);
									}
									ImageData shadowImage = image.getImageData();
									ImageData data = new ImageData(shadowImage.width,
											shadowImage.height, shadowImage.depth,
											new PaletteData(rgbs));
									data.transparentPixel = shadowImage.transparentPixel;
									for (int pixelX = 0; pixelX < shadowImage.width; pixelX++)
									{
										for (int pixelY = 0; pixelY < shadowImage.width; pixelY++)
										{
											int dstPixel = shadowImage
													.getPixel(pixelX, pixelY);
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
									g.drawImage(fadedImage, x, y);
									fadedImage.dispose();
								}
							}
							else
								g.drawImage(image, x, y);
						}
					}
					else
					{
						// Draw a image disabled
						if (disableImage == null)
						{
							disableImage = new Image(null, image, SWT.IMAGE_DISABLE);
							g.drawImage(disableImage, x, y);
							disableImage.dispose();
						}
						else
						{
							g.drawImage(disableImage, x, y);
						}
					}

				}
			}
		}

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

	public void hideMenu()
	{
		if (shell == null || shell.isDisposed()) return;
		trackItemIndex = -1;
		if (popupMenu != null)
		{
			popupMenu.hideMenu();
			popupMenu = null;
		}
		if (menu != null)
		{
			Event event = new Event();
			event.widget = shell;
			event.data = this;
			event.type = SWT.CLOSE;
			menu.fireMenuEvent(event);
		}
		shell.dispose();
	}

	protected void drawCommand(int drawItem, boolean tracked)
	{
		if (drawItem < 0 || drawItem >= commands.size()) return;
		MenuDrawCommand command = (MenuDrawCommand) commands.get(drawItem);
		if (command.isSeparator()) return;
		GC gc = new GC(shell);
		drawSingleCommand(gc, (MenuDrawCommand) commands.get(drawItem), tracked);
		gc.dispose();
	}
	
	protected void drawCommand(GC gc, int drawItem, boolean tracked)
	{
		if (drawItem < 0 || drawItem >= commands.size()) return;
		MenuDrawCommand command = (MenuDrawCommand) commands.get(drawItem);
		if (command.isSeparator()) return;
		drawSingleCommand(gc, (MenuDrawCommand) commands.get(drawItem), tracked);
	}

	private void refreshCurrentCommand()
	{
		if (trackItemIndex > -1)
		{
			Rectangle rect = ((MenuDrawCommand) commands.get(trackItemIndex)).getDrawRect();
			shell.redraw(rect.x, rect.y, rect.width, rect.height, false);
			hideSubMenu(((MenuDrawCommand) commands.get(trackItemIndex)));
		}
	}

	private void hideSubMenu(MenuDrawCommand command)
	{
		trackItemIndex = -1;
		if (popupMenu != null)
		{
			popupMenu.hideMenu();
			popupMenu = null;
		}
	}

	private void showSubMenu(MenuDrawCommand command)
	{
		if (command.hasSubMenu() && command.getMenuItem().isEnabled())
		{
			if (popupMenu == null) popupMenu = new MenuControl(holder, theme);
			else
				popupMenu.hideMenu();
			popupMenu.menu = command.getMenuItem().getMenu();
			popupMenu.screenPos = shell.toDisplay(new Point(command.getDrawRect().x
					+ command.getDrawRect().width, command.getDrawRect().y
					- POSITION[PI.BorderTop]));
			popupMenu.leftScreenPos = shell.toDisplay(new Point(command.getDrawRect().x,
					command.getDrawRect().y - POSITION[PI.BorderTop]));
			popupMenu.aboveScreenPos = shell.toDisplay(new Point(command.getDrawRect().x,
					command.getDrawRect().y + 1));
			popupMenu.menu = command.getMenuItem().getMenu();
			popupMenu.parentMenu = this;
			popupMenu.ignoreTaskbar = ignoreTaskbar;
			popupMenu.popupRight = popupRight;

			Event event = new Event();
			event.data = popupMenu;
			event.type = SWT.OPEN;
			popupMenu.menu.fireMenuEvent(event);

			popupMenu.createAndShowWindow();
		}
	}

	private void handleMouseMoveEvent(MouseEvent e)
	{
		for (int i = 0; i < commands.size(); i++)
		{
			MenuDrawCommand command = ((MenuDrawCommand) commands.get(i));
			if (command.getDrawRect().contains(e.x, e.y))
			{
				if (trackItemIndex != i)
				{
					refreshCurrentCommand();
					trackItemIndex = i;
					drawCommand(trackItemIndex, true);
					showSubMenu(command);
					return;
				}
				return;
			}
		}
		refreshCurrentCommand();
		trackItemIndex = -1;

	}

	public Shell getShell()
	{
		return holder.getShell();
	}

	public boolean checkMouseDownEvent(Point mouseLocation)
	{
		if (shell == null || shell.isDisposed()) return true;
		if (new Rectangle(0, 0, shell.getSize().x, shell.getSize().y).contains(shell
				.toControl(mouseLocation))) return false;
		else if (popupMenu != null && !popupMenu.getShell().isDisposed())
		{
			boolean flag = popupMenu.checkMouseDownEvent(mouseLocation);
			if (!flag) return false;
		}
		return true;
	}

	MenuControl getCurrentMenu()
	{
		if (trackItemIndex == -1) return null;
		else if (popupMenu != null && popupMenu.trackItemIndex != -1) return popupMenu
				.getCurrentMenu();
		else
			return this;
	}

	public void setSelection(int index)
	{
		if (shell.isDisposed()) return;
		if (getSelectionIndex() == index) return;
		if (index > -1 && index < commands.size())
		{
			final MenuDrawCommand command = ((MenuDrawCommand) commands.get(index));
			if (trackItemIndex != index) refreshCurrentCommand();
			trackItemIndex = index;
			drawCommand(trackItemIndex, true);
			if (popupMenu == null && getCurrentCommand() == command) showSubMenu(command);
		}
		else
		{
			refreshCurrentCommand();
			trackItemIndex = -1;
		}
	}

	protected MenuDrawCommand getCurrentCommand()
	{
		if (shell.isDisposed()) return null;
		if (trackItemIndex < 0 && trackItemIndex >= commands.size()) return null;
		return (MenuDrawCommand) commands.get(trackItemIndex);
	}

	public CMenuItem getSelection()
	{
		if (trackItemIndex == -1) return null;
		MenuDrawCommand command = ((MenuDrawCommand) commands.get(trackItemIndex));
		if (command != null) return command.getMenuItem();
		return null;
	}

	public int getSelectionIndex()
	{
		return trackItemIndex;
	}

	public void downSelected()
	{
		int index = trackItemIndex + 1;
		if (index >= commands.size()) index = 0;
		MenuDrawCommand command = ((MenuDrawCommand) commands.get(index));
		if (command.isSeparator())
		{
			index++;
			if (index >= commands.size()) index = 0;
		}
		final int temp = index;
		Display.getDefault().asyncExec(new Runnable()
		{
			public void run()
			{
				setSelection(temp);
			}
		});

	}

	public void upSelected()
	{
		int index = trackItemIndex - 1;
		if (index <= -1) index = commands.size() - 1;
		MenuDrawCommand command = ((MenuDrawCommand) commands.get(index));
		if (command.isSeparator())
		{
			index--;
			if (index == -1) index = commands.size() - 1;
		}
		final int temp = index;
		Display.getDefault().asyncExec(new Runnable()
		{
			public void run()
			{
				setSelection(temp);
			}
		});
	}

	public boolean isSubMenuEnd()
	{
		if (trackItemIndex == -1) return true;
		MenuDrawCommand command = ((MenuDrawCommand) commands.get(trackItemIndex));
		if (command.hasSubMenu() && command.isEnabled()) return false;
		else
			return true;
	}

	public void subMenuSelected()
	{
		if (trackItemIndex == -1) return;
		MenuDrawCommand command = ((MenuDrawCommand) commands.get(trackItemIndex));
		if (command.hasSubMenu())
		{
			if (popupMenu == null && command.isEnabled()) showSubMenu(command);
			popupMenu.setSelection(0);
		}
		return;
	}

	public void parentMenuSelected()
	{
		if (parentMenu != null)
		{
			refreshCurrentCommand();
			trackItemIndex = -1;
		}
	}

	protected void dealAltKeyEvent(KeyEvent ke)
	{
		int ch = ke.keyCode;
		if (ch == 0) ch = ke.character;
		String pattern = "&" + (char) ch;
		for (int i = 0; i < menu.getItemCount(); i++)
		{
			if (menu.getItem(i).getText().toLowerCase().indexOf(pattern.toLowerCase()) > -1)
			{
				setSelection(i);
				if (popupMenu != null) popupMenu.setSelection(0);
				else
					handleSelectedEvent();
				return;
			}
		}
	}

	void handleSelectedEvent()
	{
		if (trackItemIndex == -1) return;
		CMenuItem item = menu.getItem(trackItemIndex);
		if (item.getMenu() == null && !item.isSeparator() && item.isEnabled())
		{
			holder.hideMenu();
			Event event = new Event();
			event.data = item;
			event.widget = shell;
			item.fireSelectionEvent(event);

			event = new Event();
			event.widget = shell;
			event.data = this;
			event.type = SWT.SELECTED;
			menu.fireMenuEvent(event);
		}
	}

	public void refresh()
	{
		if (shell != null && !shell.isDisposed()) shell.redraw();
	}

	public ThemeRender getTheme()
	{
		return theme;
	}

	public void setTheme(ThemeRender theme)
	{
		this.theme = theme;
	}

	public Control getControl()
	{
		return shell;
	}

	public CMenu getMenu()
	{
		return menu;
	}

}
