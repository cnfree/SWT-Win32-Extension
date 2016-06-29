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

import org.eclipse.swt.graphics.Color;
import org.sf.feeling.swt.win32.extension.graphics.GraphicsUtil;
import org.sf.feeling.swt.win32.internal.extension.util.ColorCache;
import org.sf.feeling.swt.win32.internal.extension.util.ImageCache;

public class VS2005ThemeRender extends GeneralThemeRender {

	private static final Color COLOR_HIGHLIGHT_BACKLIGHT = ColorCache
			.getInstance().getColor(
					GraphicsUtil.calculateColor(RGB_LIST_SELECTION, RGB_WHITE,
							70));

	private static final Color COLOR_HIGHLIGHT_FORELIGHT = ColorCache
			.getInstance().getColor(RGB_LIST_SELECTION);

	private static final Color COLOR_TEXT_GRAY = ColorCache.getInstance()
			.getColor(
					GraphicsUtil.calculateColor(RGB_LIST_FOREGROUND, RGB_WHITE,
							75));

	private static final Color COLOR_HIGHLIGHT_LIGHT = ColorCache.getInstance()
			.getColor(
					GraphicsUtil.calculateColor(RGB_LIST_SELECTION, RGB_WHITE,
							20));

	private static final Color COLOR_HIGHLIGHT_HIGHLIGHT_BACKLIGHT = ColorCache
			.getInstance().getColor(
					GraphicsUtil.mergeColors(RGB_LIST_SELECTION, 0.5f,
							RGB_LIST_BACKGROUND, 0.5f));
	
	private static final Color COLOR_GRAY_LIGHT = ColorCache.getInstance().getColor(
			GraphicsUtil.calculateColor(COLOR_TEXT_GRAY.getRGB(),
					RGB_WHITE, 20));

	protected void initRender() {
		menu_item_bg_track1 = COLOR_HIGHLIGHT_BACKLIGHT;
		menu_item_bg_track2 = menu_item_bg_track1;

		menu_item_bg_selected1 = ColorCache.getInstance().getColor(251, 251,
				249);
		menu_item_bg_selected2 = ColorCache.getInstance().getColor(247, 245,
				239);

		menu_item_border_track = COLOR_HIGHLIGHT_FORELIGHT;
		menu_item_border_selected = ColorCache.getInstance().getColor(
				RGB_WIDGET_NORMAL_SHADOW);

		menu_chevronImage = ImageCache.getImage("icons/chevron.gif");

		menu_item_fg = ColorCache.getInstance().getColor(RGB_WIDGET_FOREGROUND);
		menu_item_fg_disabled = COLOR_TEXT_GRAY;

		menubar_bgColors = new Color[] { ColorCache.getInstance().getColor(
				RGB_WIDGET_BACKGROUND) };
		menubar_bgColors_percents = null;
		
		showMenuItemShadow = true;

		tool_item_check_selection_bg_normal1 = COLOR_HIGHLIGHT_BACKLIGHT;
		tool_item_check_selection_bg_normal2 = COLOR_HIGHLIGHT_BACKLIGHT;
		tool_item_check_selection_fg_normal = COLOR_HIGHLIGHT_FORELIGHT;

		tool_item_check_selection_bg_track1 = COLOR_HIGHLIGHT_BACKLIGHT;
		tool_item_check_selection_bg_track2 = COLOR_HIGHLIGHT_BACKLIGHT;
		tool_item_check_selection_fg_track = COLOR_HIGHLIGHT_FORELIGHT;

		tool_item_bg_track1 = COLOR_HIGHLIGHT_BACKLIGHT;
		tool_item_bg_track2 = COLOR_HIGHLIGHT_BACKLIGHT;
		tool_item_fg_track = COLOR_HIGHLIGHT_FORELIGHT;

		tool_item_showmenu_bg1 = ColorCache.getInstance().getColor(251, 251,
				249);
		tool_item_showmenu_bg2 = ColorCache.getInstance().getColor(247, 245,
				239);
		tool_item_showmenu_fg = ColorCache.getInstance().getColor(
				RGB_WIDGET_NORMAL_SHADOW);

		tool_item_bg_selected1 = COLOR_HIGHLIGHT_HIGHLIGHT_BACKLIGHT;
		tool_item_bg_selected2 = COLOR_HIGHLIGHT_HIGHLIGHT_BACKLIGHT;
		tool_item_fg_selected = COLOR_HIGHLIGHT_FORELIGHT;

		tool_chevronImage = ImageCache.getImage("icons/chevron.gif");

		tool_item_separater_darkcolor = ColorCache.getInstance().getColor(
				RGB_WIDGET_NORMAL_SHADOW);
		tool_item_separater_lightcolor = ColorCache.getInstance().getColor(
				RGB_WIDGET_HIGHLIGHT_SHADOW);

		tool_item_fg = ColorCache.getInstance().getColor(RGB_WIDGET_FOREGROUND);
		tool_item_fg_disabled = COLOR_TEXT_GRAY;

		showToolImageShadow = false;
		
		showToolItemShadow = true;

		tool_item_arrow_bg = ColorCache.getInstance().getColor(
				RGB_WIDGET_FOREGROUND);
		tool_item_arrow_bg_track = ColorCache.getInstance().getColor(
				RGB_WIDGET_FOREGROUND);
		tool_item_arrow_bg_disabled = COLOR_TEXT_GRAY;

		tool_item_check_separater_color = COLOR_HIGHLIGHT_FORELIGHT;

		toolbar_bgColors = new Color[] { ColorCache.getInstance().getColor(
				RGB_WIDGET_BACKGROUND) };
		toolbar_bgColors_percents = null;

		menu_control_bg = ColorCache.getInstance()
				.getColor(RGB_LIST_BACKGROUND);
		menu_control_fg = ColorCache.getInstance().getColor(
				RGB_WIDGET_NORMAL_SHADOW);

		menu_control_image_rect_bg = ColorCache.getInstance().getColor(196,
				195, 172);
		menu_control_image_rect_fg = ColorCache.getInstance().getColor(254,
				254, 251);

		menu_control_item_separater_color = COLOR_TEXT_GRAY;

		menu_control_item_bg_track = COLOR_HIGHLIGHT_BACKLIGHT;
		menu_control_item_border_track = COLOR_HIGHLIGHT_FORELIGHT;

		menu_control_item_fg = ColorCache.getInstance().getColor(
				RGB_WIDGET_FOREGROUND);
		menu_control_item_fg_disabled = COLOR_TEXT_GRAY;

		menu_control_item_arrow_bg = ColorCache.getInstance().getColor(
				RGB_WIDGET_FOREGROUND);
		menu_control_item_arrow_bg_track = ColorCache.getInstance().getColor(
				RGB_WIDGET_FOREGROUND);
		menu_control_item_arrow_bg_disabled = COLOR_TEXT_GRAY;

		menu_control_item_check_rect_bg_track = ColorCache.getInstance()
				.getColor(
						GraphicsUtil.mergeColors(COLOR_HIGHLIGHT_FORELIGHT
								.getRGB(), 0.1f, RGB_WIDGET_BACKGROUND, 0.4f,
								RGB_LIST_BACKGROUND, 0.5f));
		menu_control_item_check_rect_border_track = ColorCache.getInstance()
				.getColor(0, 0, 128);

		menu_control_item_check_rect_bg = COLOR_HIGHLIGHT_LIGHT;
		menu_control_item_check_rect_bg_disabled = COLOR_GRAY_LIGHT;
		
		menu_control_item_check_rect_border = COLOR_HIGHLIGHT_FORELIGHT;
		menu_control_item_check_rect_border_disabled = COLOR_TEXT_GRAY;

		showMenuImageShadow = false;

		checkImage = ImageCache.getImage("icons/check.gif");
		redioImage = ImageCache.getImage("icons/radio.gif");
		
		tool_menu_control_border = ColorCache.getInstance().getColor(
				RGB_WIDGET_NORMAL_SHADOW);
	}
}
