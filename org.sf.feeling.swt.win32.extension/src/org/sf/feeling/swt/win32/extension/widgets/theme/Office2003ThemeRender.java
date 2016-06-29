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

public class Office2003ThemeRender extends GeneralThemeRender {

	private static final Color COLOR_TEXT_GRAY = ColorCache.getInstance()
			.getColor(
					GraphicsUtil.calculateColor(RGB_LIST_FOREGROUND, RGB_WHITE,
							75));

	private static final Color COLOR_GRAY_LIGHT = ColorCache.getInstance()
			.getColor(
					GraphicsUtil.calculateColor(COLOR_TEXT_GRAY.getRGB(),
							RGB_WHITE, 20));

	protected void initRender() {
		menu_item_bg_track1 = ColorCache.getInstance().getColor(255, 244, 204);
		menu_item_bg_track2 = ColorCache.getInstance().getColor(255, 208, 145);

		menu_item_bg_selected1 = ColorCache.getInstance().getColor(227, 238,
				255);
		menu_item_bg_selected2 = ColorCache.getInstance().getColor(147, 181,
				231);

		menu_item_border_track = ColorCache.getInstance().getColor(0, 45, 150);
		menu_item_border_selected = ColorCache.getInstance().getColor(0, 45,
				150);

		menu_chevronImage = ImageCache.getImage("icons/chevron.gif");

		menu_item_fg = ColorCache.getInstance().getColor(RGB_WIDGET_FOREGROUND);
		menu_item_fg_disabled = COLOR_TEXT_GRAY;

		menubar_bgColors = new Color[] {
				ColorCache.getInstance().getColor(147, 181, 231),
				ColorCache.getInstance().getColor(195, 218, 249) };
		menubar_bgColors_percents = new int[] { 100 };

		showMenuItemShadow = true;

		tool_item_check_selection_bg_normal1 = ColorCache.getInstance()
				.getColor(0xff, 0xd5, 140);
		tool_item_check_selection_bg_normal2 = ColorCache.getInstance()
				.getColor(0xff, 0xad, 0x56);
		tool_item_check_selection_fg_normal = ColorCache.getInstance()
				.getColor(0, 45, 150);

		tool_item_check_selection_bg_track1 = ColorCache.getInstance()
				.getColor(0xfe, 0x95, 0x4e);
		tool_item_check_selection_bg_track2 = ColorCache.getInstance()
				.getColor(0xff, 0xd3, 0x8e);
		tool_item_check_selection_fg_track = ColorCache.getInstance().getColor(
				0, 45, 150);

		tool_item_bg_track1 = ColorCache.getInstance().getColor(255, 244, 204);
		tool_item_bg_track2 = ColorCache.getInstance().getColor(255, 208, 145);
		tool_item_fg_track = ColorCache.getInstance().getColor(0, 45, 150);

		tool_item_showmenu_bg1 = ColorCache.getInstance().getColor(227, 238,
				255);
		tool_item_showmenu_bg2 = ColorCache.getInstance().getColor(147, 181,
				231);
		tool_item_showmenu_fg = ColorCache.getInstance().getColor(0, 45, 150);

		tool_item_bg_selected1 = ColorCache.getInstance().getColor(0xfe, 0x95,
				0x4e);
		tool_item_bg_selected2 = ColorCache.getInstance().getColor(0xff, 0xd3,
				0x8e);
		tool_item_fg_selected = ColorCache.getInstance().getColor(0, 45, 150);

		tool_chevronImage = ImageCache.getImage("icons/chevron.gif");

		tool_item_separater_darkcolor = ColorCache.getInstance().getColor(154,
				198, 255);
		tool_item_separater_lightcolor = ColorCache.getInstance().getColor(255,
				255, 255);

		tool_item_fg = ColorCache.getInstance().getColor(RGB_WIDGET_FOREGROUND);
		tool_item_fg_disabled = COLOR_TEXT_GRAY;

		showToolImageShadow = false;

		showToolItemShadow = true;

		tool_item_arrow_bg = ColorCache.getInstance().getColor(
				RGB_WIDGET_FOREGROUND);
		tool_item_arrow_bg_track = ColorCache.getInstance().getColor(
				RGB_WIDGET_FOREGROUND);
		tool_item_arrow_bg_disabled = COLOR_TEXT_GRAY;

		tool_item_check_separater_color = ColorCache.getInstance().getColor(0,
				45, 150);

		toolbar_bgColors = new Color[] {
				ColorCache.getInstance().getColor(227, 238, 255),
				ColorCache.getInstance().getColor(185, 211, 246),
				ColorCache.getInstance().getColor(135, 173, 228) };
		toolbar_bgColors_percents = new int[] { 60, 100 };

		menu_control_bg = ColorCache.getInstance().getColor(246, 246, 246);
		menu_control_fg = ColorCache.getInstance().getColor(0, 45, 150);

		menu_control_image_rect_fg = ColorCache.getInstance().getColor(227,
				238, 255);
		menu_control_image_rect_bg = ColorCache.getInstance().getColor(147,
				181, 231);

		menu_control_item_separater_color = ColorCache.getInstance().getColor(
				106, 140, 203);

		menu_control_item_bg_track = ColorCache.getInstance().getColor(255,
				238, 194);
		menu_control_item_border_track = ColorCache.getInstance().getColor(0,
				0, 128);

		menu_control_item_fg = ColorCache.getInstance().getColor(
				RGB_WIDGET_FOREGROUND);
		menu_control_item_fg_disabled = COLOR_TEXT_GRAY;

		menu_control_item_arrow_bg = ColorCache.getInstance().getColor(
				RGB_WIDGET_FOREGROUND);
		menu_control_item_arrow_bg_track = ColorCache.getInstance().getColor(
				RGB_WIDGET_FOREGROUND);
		menu_control_item_arrow_bg_disabled = COLOR_TEXT_GRAY;

		menu_control_item_check_rect_bg_track = ColorCache.getInstance()
				.getColor(255, 128, 62);
		menu_control_item_check_rect_border_track = ColorCache.getInstance()
				.getColor(0, 0, 128);

		menu_control_item_check_rect_bg = ColorCache.getInstance().getColor(
				255, 192, 111);

		menu_control_item_check_rect_bg_disabled = COLOR_GRAY_LIGHT;

		menu_control_item_check_rect_border = ColorCache.getInstance()
				.getColor(0, 0, 128);
		menu_control_item_check_rect_border_disabled = COLOR_TEXT_GRAY;

		showMenuImageShadow = false;

		checkImage = ImageCache.getImage("icons/check.gif");
		redioImage = ImageCache.getImage("icons/radio.gif");

		tool_menu_control_border = ColorCache.getInstance()
				.getColor(0, 45, 150);
	}
}
