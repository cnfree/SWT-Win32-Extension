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

public class BlackGlossyThemeRender extends GlossyThemeRender {

	private static final Color COLOR_TEXT_GRAY = ColorCache.getInstance()
			.getColor(
					GraphicsUtil.calculateColor(RGB_LIST_FOREGROUND, RGB_WHITE,
							75));

	private static final Color COLOR_GRAY_LIGHT = ColorCache.getInstance()
			.getColor(
					GraphicsUtil.calculateColor(COLOR_TEXT_GRAY.getRGB(),
							RGB_WHITE, 20));

	protected void initRender() {
		menu_item_bg_color1 = ColorCache.getInstance().getColor(0x68, 0x7C,
				0xAC);

		menu_item_bg_color1_alpha = 217;

		menu_item_bg_color2 = ColorCache.getInstance().getColor(0xAA, 0xB5,
				0xD0);

		menu_item_bg_color2_alpha = 74;

		menu_item_outer_border = ColorCache.getInstance().getColor(0x03, 0x07,
				0x0D);

		menu_item_inner_border_track = ColorCache.getInstance().getColor(0xBF,
				0xC4, 0xCE);

		menu_item_inner_border_selected = ColorCache.getInstance().getColor(
				0x4b, 0x4b, 0x4b);

		menu_item_bg_glossy_track1 = ColorCache.getInstance().getWhite();

		menu_item_bg_glossy_track1_alpha = 85;

		menu_item_bg_glossy_track2 = ColorCache.getInstance().getWhite();

		menu_item_bg_glossy_track2_alpha = 1;

		menu_item_bg_glossy_selected1 = ColorCache.getInstance().getBlack();

		menu_item_bg_glossy_selected1_alpha = 150;

		menu_item_bg_glossy_selected2 = ColorCache.getInstance().getBlack();

		menu_item_bg_glossy_selected2_alpha = 100;

		menu_item_bg_glow_track = ColorCache.getInstance().getColor(0x30, 0x73,
				0xCE);

		menu_chevronImage = ImageCache.getImage("icons/chevron_white.gif");

		menu_item_fg = ColorCache.getInstance().getWhite();

		menu_item_fg_disabled = COLOR_TEXT_GRAY;

		menubar_bgColors = new Color[] { ColorCache.getInstance().getColor(73,
				73, 73) };

		menubar_bgColors_percents = null;

		showMenuItemShadow = true;

		tool_item_bg_color1 = ColorCache.getInstance().getColor(0x68, 0x7C,
				0xAC);

		tool_item_bg_color1_alpha = 217;

		tool_item_bg_color2 = ColorCache.getInstance().getColor(0xAA, 0xB5,
				0xD0);

		tool_item_bg_color2_alpha = 74;

		tool_item_outer_border = ColorCache.getInstance().getColor(0x03, 0x07,
				0x0D);

		tool_item_inner_border_track = ColorCache.getInstance().getColor(0xBF,
				0xC4, 0xCE);

		tool_item_inner_border_selected = ColorCache.getInstance().getColor(
				0x4b, 0x4b, 0x4b);

		tool_item_bg_glossy_track1 = ColorCache.getInstance().getWhite();

		tool_item_bg_glossy_track1_alpha = 85;

		tool_item_bg_glossy_track2 = ColorCache.getInstance().getWhite();

		tool_item_bg_glossy_track2_alpha = 1;

		tool_item_bg_glossy_selected1 = ColorCache.getInstance().getBlack();

		tool_item_bg_glossy_selected1_alpha = 150;

		tool_item_bg_glossy_selected2 = ColorCache.getInstance().getBlack();

		tool_item_bg_glossy_selected2_alpha = 100;

		tool_item_bg_glossy_showMenu = ColorCache.getInstance().getColor(73,
				73, 73);

		tool_item_bg_glow_track = ColorCache.getInstance().getColor(0x30, 0x73,
				0xCE);

		tool_item_bg_checked_glow_track = ColorCache.getInstance().getColor(
				0x70, 0xD4, 0xFF);

		tool_item_bg_checked_glow = ColorCache.getInstance().getColor(0x57,
				0xC6, 0xEF);

		tool_chevronImage = ImageCache.getImage("icons/chevron_white.gif");

		tool_item_separater_darkcolor = ColorCache.getInstance().getColor(42,
				42, 42);

		tool_item_separater_lightcolor = ColorCache.getInstance().getColor(117,
				117, 117);

		tool_item_fg = ColorCache.getInstance().getWhite();

		tool_item_fg_disabled = COLOR_TEXT_GRAY;

		showToolImageShadow = false;

		showToolItemShadow = true;

		tool_item_arrow_bg = COLOR_TEXT_GRAY;

		tool_item_arrow_bg_track = ColorCache.getInstance().getWhite();

		tool_item_arrow_bg_disabled = COLOR_TEXT_GRAY;

		tool_item_check_separater_color = ColorCache.getInstance().getColor(
				200, 200, 200);

		toolbar_bgColors = new Color[] { ColorCache.getInstance().getBlack() };

		toolbar_bgColors_percents = null;

		toolbar_bg_glossy_color1 = ColorCache.getInstance().getColor(0xff,
				0xff, 0xff);

		toolbar_bg_glossy_color1_alpha = 170;

		toolbar_bg_glossy_color2 = ColorCache.getInstance()
				.getColor(65, 65, 65);

		toolbar_bg_glossy_color2_alpha = 255;

		toolbar_bg_glow_color1 = ColorCache.getInstance().getColor(0x43, 0x53,
				0x7A);

		toolbar_bg_glow_color1_alpha = 0;

		toolbar_bg_glow_color2 = ColorCache.getInstance().getColor(0x43, 0x53,
				0x7A);

		toolbar_bg_glow_color2_alpha = 255;

		menu_control_bg = ColorCache.getInstance().getColor(88, 88, 88);

		menu_control_fg = ColorCache.getInstance().getBlack();

		menu_control_image_rect_fg = ColorCache.getInstance().getColor(88, 88,
				88);

		menu_control_image_rect_bg = ColorCache.getInstance().getColor(88, 88,
				88);

		menu_control_item_separater_color = ColorCache.getInstance().getColor(
				180, 180, 180);

		menu_control_item_bg_track = ColorCache.getInstance().getBlack();

		menu_control_item_bg_color1 = ColorCache.getInstance().getColor(0xff,
				0xff, 0xff);

		menu_control_item_bg_color1_alpha = 170;

		menu_control_item_bg_color2 = ColorCache.getInstance().getColor(65, 65,
				65);

		menu_control_item_bg_color2_alpha = 255;

		menu_control_item_outer_border = ColorCache.getInstance().getColor(
				0x03, 0x07, 0x0D);

		menu_control_item_inner_border = ColorCache.getInstance().getColor(
				0xBF, 0xC4, 0xCE);

		menu_control_item_bg_glossy_track1 = ColorCache.getInstance()
				.getWhite();

		menu_control_item_bg_glossy_track1_alpha = 85;

		menu_control_item_bg_glossy_track2 = ColorCache.getInstance()
				.getWhite();

		menu_control_item_bg_glossy_track2_alpha = 1;

		menu_control_item_bg_glow = ColorCache.getInstance().getColor(0x30,
				0x73, 0xCE);

		menu_control_item_fg = ColorCache.getInstance().getWhite();

		menu_control_item_fg_disabled = COLOR_TEXT_GRAY;

		menu_control_item_arrow_bg = COLOR_TEXT_GRAY;

		menu_control_item_arrow_bg_track = ColorCache.getInstance().getWhite();

		menu_control_item_arrow_bg_disabled = COLOR_TEXT_GRAY;

		menu_control_item_check_rect_bg_track = ColorCache.getInstance()
				.getColor(255, 227, 149);
		menu_control_item_check_rect_border_track = ColorCache.getInstance()
				.getColor(242, 149, 54);

		menu_control_item_check_rect_bg = ColorCache.getInstance().getColor(
				255, 227, 149);
		menu_control_item_check_rect_border = ColorCache.getInstance()
				.getColor(242, 149, 54);

		menu_control_item_check_rect_bg_disabled = COLOR_GRAY_LIGHT;
		menu_control_item_check_rect_border_disabled = COLOR_TEXT_GRAY;

		showMenuImageShadow = false;

		checkImage = ImageCache.getImage("icons/check2007.gif");

		redioImage = ImageCache.getImage("icons/radio2007.gif");

		tool_menu_control_border = ColorCache.getInstance().getColor(
				RGB_WIDGET_NORMAL_SHADOW);
	}
}
