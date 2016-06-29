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

package org.sf.feeling.swt.win32.extension.widgets.theme;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;
import org.sf.feeling.swt.win32.extension.widgets.ThemeConstants;
import org.sf.feeling.swt.win32.internal.extension.util.ColorCache;

public abstract class ThemeRender
{

	private static final String PACKAGE_PREFIX = "org.eclipse.swt.extension.widgets.theme.";

	public ThemeRender( )
	{
		checkSubclass( );
		initRender( );
	}

	void checkSubclass( )
	{
		if ( !isValidClass( getClass( ) ) )
			SWT.error( SWT.ERROR_INVALID_SUBCLASS );
	}

	static boolean isValidClass( Class clazz )
	{
		String name = clazz.getName( );
		int index = name.lastIndexOf( '.' );
		return name.substring( 0, index + 1 ).equals( PACKAGE_PREFIX );
	}

	protected abstract void initRender( );

	protected final static RGB RGB_LIST_SELECTION;

	protected final static RGB RGB_WIDGET_BACKGROUND;

	protected final static RGB RGB_WIDGET_NORMAL_SHADOW;

	protected final static RGB RGB_WIDGET_HIGHLIGHT_SHADOW;

	protected final static RGB RGB_WIDGET_FOREGROUND;

	protected final static RGB RGB_LIST_FOREGROUND;

	protected final static RGB RGB_LIST_BACKGROUND;

	protected final static RGB RGB_WHITE = new RGB( 255, 255, 255 );

	protected final static RGB RGB_BLACK = new RGB( 0, 0, 0 );

	static
	{
		Color COLOR_LIST_SELECTION = Display.getDefault( )
				.getSystemColor( SWT.COLOR_LIST_SELECTION );
		RGB_LIST_SELECTION = COLOR_LIST_SELECTION.getRGB( );
		COLOR_LIST_SELECTION.dispose( );

		Color COLOR_WIDGET_BACKGROUND = Display.getDefault( )
				.getSystemColor( SWT.COLOR_WIDGET_BACKGROUND );
		RGB_WIDGET_BACKGROUND = COLOR_WIDGET_BACKGROUND.getRGB( );
		COLOR_WIDGET_BACKGROUND.dispose( );

		Color COLOR_WIDGET_NORMAL_SHADOW = Display.getDefault( )
				.getSystemColor( SWT.COLOR_WIDGET_NORMAL_SHADOW );
		RGB_WIDGET_NORMAL_SHADOW = COLOR_WIDGET_NORMAL_SHADOW.getRGB( );
		COLOR_WIDGET_NORMAL_SHADOW.dispose( );

		Color COLOR_WIDGET_HIGHLIGHT_SHADOW = Display.getDefault( )
				.getSystemColor( SWT.COLOR_WIDGET_HIGHLIGHT_SHADOW );
		RGB_WIDGET_HIGHLIGHT_SHADOW = COLOR_WIDGET_HIGHLIGHT_SHADOW.getRGB( );
		COLOR_WIDGET_HIGHLIGHT_SHADOW.dispose( );

		Color COLOR_WIDGET_FOREGROUND = Display.getDefault( )
				.getSystemColor( SWT.COLOR_WIDGET_FOREGROUND );
		RGB_WIDGET_FOREGROUND = COLOR_WIDGET_FOREGROUND.getRGB( );
		COLOR_WIDGET_FOREGROUND.dispose( );

		Color COLOR_LIST_FOREGROUND = Display.getDefault( )
				.getSystemColor( SWT.COLOR_LIST_FOREGROUND );
		RGB_LIST_FOREGROUND = COLOR_LIST_FOREGROUND.getRGB( );
		COLOR_LIST_FOREGROUND.dispose( );

		Color COLOR_LIST_BACKGROUND = Display.getDefault( )
				.getSystemColor( SWT.COLOR_LIST_BACKGROUND );
		RGB_LIST_BACKGROUND = COLOR_LIST_BACKGROUND.getRGB( );
		COLOR_LIST_BACKGROUND.dispose( );
	}

	protected Color menuShadowColor = ColorCache.getInstance( )
			.getColor( RGB_WIDGET_FOREGROUND );

	protected Image menu_chevronImage;

	protected Color menu_item_fg;

	protected Color menu_item_fg_disabled;

	protected Color[] menubar_bgColors;

	protected int[] menubar_bgColors_percents;

	protected boolean showMenuItemShadow;

	protected Image tool_chevronImage;

	protected Color tool_item_separater_darkcolor;

	protected Color tool_item_separater_lightcolor;

	protected Color tool_item_fg;

	protected Color tool_item_fg_disabled;

	protected boolean showToolImageShadow;

	protected boolean showToolItemShadow;

	protected Color tool_item_arrow_bg;

	protected Color tool_item_arrow_bg_track;

	protected Color tool_item_arrow_bg_disabled;

	protected Color[] toolbar_bgColors;

	protected int[] toolbar_bgColors_percents;

	protected Color menu_control_bg;

	protected Color menu_control_fg;

	protected Color menu_control_image_rect_bg;

	protected Color menu_control_image_rect_fg;

	protected Color menu_control_item_separater_color;

	protected Color menu_control_item_fg;

	protected Color menu_control_item_fg_disabled;

	protected Color menu_control_item_arrow_bg;

	protected Color menu_control_item_arrow_bg_track;

	protected Color menu_control_item_arrow_bg_disabled;

	protected Color menu_control_item_check_rect_bg_track;

	protected Color menu_control_item_check_rect_border_track;

	protected Color menu_control_item_check_rect_bg;

	protected Color menu_control_item_check_rect_bg_disabled;

	protected Color menu_control_item_check_rect_border;

	protected Color menu_control_item_check_rect_border_disabled;

	protected boolean showMenuImageShadow;

	protected Image checkImage;

	protected Image redioImage;

	protected Color tool_menu_control_border;

	public Image getMenu_chevronImage( )
	{
		return menu_chevronImage;
	}

	public void setMenu_chevronImage( Image menu_chevronImage )
	{
		this.menu_chevronImage = menu_chevronImage;
	}

	public Color getMenu_item_fg( )
	{
		return menu_item_fg;
	}

	public void setMenu_item_fg( Color menu_item_fg )
	{
		this.menu_item_fg = menu_item_fg;
	}

	public Color getMenu_item_fg_disabled( )
	{
		return menu_item_fg_disabled;
	}

	public void setMenu_item_fg_disabled( Color menu_item_fg_disabled )
	{
		this.menu_item_fg_disabled = menu_item_fg_disabled;
	}

	public Color[] getMenubar_bgColors( )
	{
		return menubar_bgColors;
	}

	public void setMenubar_bgColors( Color[] menubar_bgColors )
	{
		this.menubar_bgColors = menubar_bgColors;
	}

	public int[] getMenubar_bgColors_percents( )
	{
		return menubar_bgColors_percents;
	}

	public void setMenubar_bgColors_percents( int[] menubar_bgColors_percents )
	{
		this.menubar_bgColors_percents = menubar_bgColors_percents;
	}

	public boolean isShowToolImageShadow( )
	{
		return showToolImageShadow;
	}

	public void setShowToolImageShadow( boolean showToolImageShadow )
	{
		this.showToolImageShadow = showToolImageShadow;
	}

	public Image getTool_chevronImage( )
	{
		return tool_chevronImage;
	}

	public void setTool_chevronImage( Image tool_chevronImage )
	{
		this.tool_chevronImage = tool_chevronImage;
	}

	public Color getTool_item_arrow_bg( )
	{
		return tool_item_arrow_bg;
	}

	public void setTool_item_arrow_bg( Color tool_item_arrow_bg )
	{
		this.tool_item_arrow_bg = tool_item_arrow_bg;
	}

	public Color getTool_item_arrow_bg_disabled( )
	{
		return tool_item_arrow_bg_disabled;
	}

	public void setTool_item_arrow_bg_disabled(
			Color tool_item_arrow_bg_disabled )
	{
		this.tool_item_arrow_bg_disabled = tool_item_arrow_bg_disabled;
	}

	public Color getTool_item_arrow_bg_track( )
	{
		return tool_item_arrow_bg_track;
	}

	public void setTool_item_arrow_bg_track( Color tool_item_arrow_bg_track )
	{
		this.tool_item_arrow_bg_track = tool_item_arrow_bg_track;
	}

	public Color getTool_item_fg( )
	{
		return tool_item_fg;
	}

	public void setTool_item_fg( Color tool_item_fg )
	{
		this.tool_item_fg = tool_item_fg;
	}

	public Color getTool_item_fg_disabled( )
	{
		return tool_item_fg_disabled;
	}

	public void setTool_item_fg_disabled( Color tool_item_fg_disabled )
	{
		this.tool_item_fg_disabled = tool_item_fg_disabled;
	}

	public Color getTool_item_separater_darkcolor( )
	{
		return tool_item_separater_darkcolor;
	}

	public void setTool_item_separater_darkcolor(
			Color tool_item_separater_darkcolor )
	{
		this.tool_item_separater_darkcolor = tool_item_separater_darkcolor;
	}

	public Color getTool_item_separater_lightcolor( )
	{
		return tool_item_separater_lightcolor;
	}

	public void setTool_item_separater_lightcolor(
			Color tool_item_separater_lightcolor )
	{
		this.tool_item_separater_lightcolor = tool_item_separater_lightcolor;
	}

	public Color[] getToolbar_bgColors( )
	{
		return toolbar_bgColors;
	}

	public void setToolbar_bgColors( Color[] toolbar_bgColors )
	{
		this.toolbar_bgColors = toolbar_bgColors;
	}

	public int[] getToolbar_bgColors_percents( )
	{
		return toolbar_bgColors_percents;
	}

	public void setToolbar_bgColors_percents( int[] toolbar_bgColors_percents )
	{
		this.toolbar_bgColors_percents = toolbar_bgColors_percents;
	}

	public boolean isShowMenuItemShadow( )
	{
		return showMenuItemShadow;
	}

	public void setShowMenuItemShadow( boolean showMenuItemShadow )
	{
		this.showMenuItemShadow = showMenuItemShadow;
	}

	public boolean isShowToolItemShadow( )
	{
		return showToolItemShadow;
	}

	public void setShowToolItemShadow( boolean showToolItemShadow )
	{
		this.showToolItemShadow = showToolItemShadow;
	}

	public Image getCheckImage( )
	{
		return checkImage;
	}

	public void setCheckImage( Image checkImage )
	{
		this.checkImage = checkImage;
	}

	public Color getMenu_control_bg( )
	{
		return menu_control_bg;
	}

	public void setMenu_control_bg( Color menu_control_bg )
	{
		this.menu_control_bg = menu_control_bg;
	}

	public Color getMenu_control_fg( )
	{
		return menu_control_fg;
	}

	public void setMenu_control_fg( Color menu_control_fg )
	{
		this.menu_control_fg = menu_control_fg;
	}

	public Color getMenu_control_image_rect_bg( )
	{
		return menu_control_image_rect_bg;
	}

	public void setMenu_control_image_rect_bg( Color menu_control_image_rect_bg )
	{
		this.menu_control_image_rect_bg = menu_control_image_rect_bg;
	}

	public Color getMenu_control_image_rect_fg( )
	{
		return menu_control_image_rect_fg;
	}

	public void setMenu_control_image_rect_fg( Color menu_control_image_rect_fg )
	{
		this.menu_control_image_rect_fg = menu_control_image_rect_fg;
	}

	public Color getMenu_control_item_arrow_bg( )
	{
		return menu_control_item_arrow_bg;
	}

	public void setMenu_control_item_arrow_bg( Color menu_control_item_arrow_bg )
	{
		this.menu_control_item_arrow_bg = menu_control_item_arrow_bg;
	}

	public Color getMenu_control_item_arrow_bg_disabled( )
	{
		return menu_control_item_arrow_bg_disabled;
	}

	public void setMenu_control_item_arrow_bg_disabled(
			Color menu_control_item_arrow_bg_disabled )
	{
		this.menu_control_item_arrow_bg_disabled = menu_control_item_arrow_bg_disabled;
	}

	public Color getMenu_control_item_arrow_bg_track( )
	{
		return menu_control_item_arrow_bg_track;
	}

	public void setMenu_control_item_arrow_bg_track(
			Color menu_control_item_arrow_bg_track )
	{
		this.menu_control_item_arrow_bg_track = menu_control_item_arrow_bg_track;
	}

	public Color getMenu_control_item_check_rect_bg( )
	{
		return menu_control_item_check_rect_bg;
	}

	public void setMenu_control_item_check_rect_bg(
			Color menu_control_item_check_rect_bg )
	{
		this.menu_control_item_check_rect_bg = menu_control_item_check_rect_bg;
	}

	public Color getMenu_control_item_check_rect_bg_track( )
	{
		return menu_control_item_check_rect_bg_track;
	}

	public void setMenu_control_item_check_rect_bg_track(
			Color menu_control_item_check_rect_bg_track )
	{
		this.menu_control_item_check_rect_bg_track = menu_control_item_check_rect_bg_track;
	}

	public Color getMenu_control_item_check_rect_border( )
	{
		return menu_control_item_check_rect_border;
	}

	public void setMenu_control_item_check_rect_border(
			Color menu_control_item_check_rect_border )
	{
		this.menu_control_item_check_rect_border = menu_control_item_check_rect_border;
	}

	public Color getMenu_control_item_check_rect_border_disabled( )
	{
		return menu_control_item_check_rect_border_disabled;
	}

	public void setMenu_control_item_check_rect_border_disabled(
			Color menu_control_item_check_rect_border_disabled )
	{
		this.menu_control_item_check_rect_border_disabled = menu_control_item_check_rect_border_disabled;
	}

	public Color getMenu_control_item_check_rect_border_track( )
	{
		return menu_control_item_check_rect_border_track;
	}

	public void setMenu_control_item_check_rect_border_track(
			Color menu_control_item_check_rect_border_track )
	{
		this.menu_control_item_check_rect_border_track = menu_control_item_check_rect_border_track;
	}

	public Color getMenu_control_item_fg( )
	{
		return menu_control_item_fg;
	}

	public void setMenu_control_item_fg( Color menu_control_item_fg )
	{
		this.menu_control_item_fg = menu_control_item_fg;
	}

	public Color getMenu_control_item_fg_disabled( )
	{
		return menu_control_item_fg_disabled;
	}

	public void setMenu_control_item_fg_disabled(
			Color menu_control_item_fg_disabled )
	{
		this.menu_control_item_fg_disabled = menu_control_item_fg_disabled;
	}

	public Color getMenu_control_item_separater_color( )
	{
		return menu_control_item_separater_color;
	}

	public void setMenu_control_item_separater_color(
			Color menu_control_item_separater_color )
	{
		this.menu_control_item_separater_color = menu_control_item_separater_color;
	}

	public Image getRedioImage( )
	{
		return redioImage;
	}

	public void setRedioImage( Image redioImage )
	{
		this.redioImage = redioImage;
	}

	public boolean isShowMenuImageShadow( )
	{
		return showMenuImageShadow;
	}

	public void setShowMenuImageShadow( boolean showMenuImageShadow )
	{
		this.showMenuImageShadow = showMenuImageShadow;
	}

	public Color getTool_menu_control_border( )
	{
		return tool_menu_control_border;
	}

	public void setTool_menu_control_border( Color tool_menu_control_border )
	{
		this.tool_menu_control_border = tool_menu_control_border;
	}

	public Color getMenu_control_item_check_rect_bg_disabled( )
	{
		return menu_control_item_check_rect_bg_disabled;
	}

	public void setMenu_control_item_check_rect_bg_disabled(
			Color menu_control_item_check_rect_bg_disabled )
	{
		this.menu_control_item_check_rect_bg_disabled = menu_control_item_check_rect_bg_disabled;
	}

	public Color getMenuShadowColor( )
	{
		return menuShadowColor;
	}

	public void setMenuShadowColor( Color menuShadowColor )
	{
		this.menuShadowColor = menuShadowColor;
	}

	public static ThemeRender getThemeRender( String theme )
	{
		if ( ThemeConstants.STYLE_OFFICE2007.equals( theme ) )
			return new Office2007ThemeRender( );
		if ( ThemeConstants.STYLE_GLOSSY.equals( theme ) )
			return new BlackGlossyThemeRender( );
		if ( ThemeConstants.STYLE_VISTA.equals( theme ) )
			return new VS2005ThemeRender( );
		return null;
	}
}
