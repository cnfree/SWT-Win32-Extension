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

public class Office2007ThemeRender extends OfficeThemeRender {

	private static final Color COLOR_TEXT_GRAY = ColorCache.getInstance()
			.getColor(
					GraphicsUtil.calculateColor(RGB_LIST_FOREGROUND, RGB_WHITE,
							75));

	private static final Color COLOR_GRAY_LIGHT = ColorCache.getInstance()
			.getColor(
					GraphicsUtil.calculateColor(COLOR_TEXT_GRAY.getRGB(),
							RGB_WHITE, 20));

	protected void initRender() {
		menu_item_outer_top_track1 = ColorCache.getInstance().getColor(0xff,
				0xff, 0xfb);
		menu_item_outer_top_track2 = ColorCache.getInstance().getColor(0xff,
				0xf9, 0xe3);
		menu_item_outer_bottom_track1 = ColorCache.getInstance().getColor(0xff,
				0xf2, 0xc9);
		menu_item_outer_bottom_track2 = ColorCache.getInstance().getColor(0xff,
				0xf8, 0xb5);
		menu_item_inner_top_track1 = ColorCache.getInstance().getColor(0xff,
				0xfc, 0xe5);
		menu_item_inner_top_track2 = ColorCache.getInstance().getColor(0xff,
				0xeb, 0xa6);
		menu_item_inner_bottom_track1 = ColorCache.getInstance().getColor(0xff,
				0xd5, 0x67);
		menu_item_inner_bottom_track2 = ColorCache.getInstance().getColor(0xff,
				0xe4, 0x91);
		menu_item_border_track = ColorCache.getInstance().getColor(0x79, 0x99,
				0xc2);

		menu_item_outer_top_selected1 = ColorCache.getInstance().getColor(245,
				245, 245);
		menu_item_outer_top_selected2 = ColorCache.getInstance().getColor(245,
				245, 245);
		menu_item_outer_bottom_selected1 = ColorCache.getInstance().getColor(
				245, 245, 245);
		menu_item_outer_bottom_selected2 = ColorCache.getInstance().getColor(
				245, 245, 245);
		menu_item_inner_top_selected1 = ColorCache.getInstance().getColor(245,
				245, 245);
		menu_item_inner_top_selected2 = ColorCache.getInstance().getColor(245,
				245, 245);
		menu_item_inner_bottom_selected1 = ColorCache.getInstance().getColor(
				245, 245, 245);
		menu_item_inner_bottom_selected2 = ColorCache.getInstance().getColor(
				245, 245, 245);
		menu_item_border_selected = ColorCache.getInstance().getColor(134, 134,
				134);

		menu_chevronImage = ImageCache.getImage("icons/chevron.gif");

		menu_item_fg = ColorCache.getInstance().getColor(0x15, 0x42, 0x8b);
		menu_item_fg_disabled = COLOR_TEXT_GRAY;

		menubar_bgColors = new Color[] { ColorCache.getInstance().getColor(191,
				219, 255) };
		menubar_bgColors_percents = null;

		showMenuItemShadow = true;

		tool_item_outer_top_track1 = ColorCache.getInstance().getColor(0xff,
				0xff, 0xfb);
		tool_item_outer_top_track2 = ColorCache.getInstance().getColor(0xff,
				0xf9, 0xe3);
		tool_item_outer_bottom_track1 = ColorCache.getInstance().getColor(0xff,
				0xf2, 0xc9);
		tool_item_outer_bottom_track2 = ColorCache.getInstance().getColor(0xff,
				0xf8, 0xb5);
		tool_item_inner_top_track1 = ColorCache.getInstance().getColor(0xff,
				0xfc, 0xe5);
		tool_item_inner_top_track2 = ColorCache.getInstance().getColor(0xff,
				0xeb, 0xa6);
		tool_item_inner_bottom_track1 = ColorCache.getInstance().getColor(0xff,
				0xd5, 0x67);
		tool_item_inner_bottom_track2 = ColorCache.getInstance().getColor(0xff,
				0xe4, 0x91);
		tool_item_border_track = ColorCache.getInstance().getColor(0x79, 0x99,
				0xc2);

		tool_item_outer_top_selected1 = ColorCache.getInstance().getColor(0xe9,
				0xa8, 0x61);
		tool_item_outer_top_selected2 = ColorCache.getInstance().getColor(0xf7,
				0xa4, 0x27);
		tool_item_outer_bottom_selected1 = ColorCache.getInstance().getColor(
				0xf6, 0x9c, 0x18);
		tool_item_outer_bottom_selected2 = ColorCache.getInstance().getColor(
				0xfd, 0xad, 0x11);
		tool_item_inner_top_selected1 = ColorCache.getInstance().getColor(0xfe,
				0xb9, 0x6c);
		tool_item_inner_top_selected2 = ColorCache.getInstance().getColor(0xfd,
				0xa4, 0x61);
		tool_item_inner_bottom_selected1 = ColorCache.getInstance().getColor(
				0xfc, 0x8f, 0x3d);
		tool_item_inner_bottom_selected2 = ColorCache.getInstance().getColor(
				0xff, 0xd0, 0x86);
		tool_item_border_selected = ColorCache.getInstance().getColor(0x79,
				0x99, 0xc2);

		tool_item_outer_top_checked1 = ColorCache.getInstance().getColor(0xf9,
				0xc0, 0x67);
		tool_item_outer_top_checked2 = ColorCache.getInstance().getColor(250,
				0xc3, 0x5d);
		tool_item_outer_bottom_checked1 = ColorCache.getInstance().getColor(
				0xf8, 190, 0x51);
		tool_item_outer_bottom_checked2 = ColorCache.getInstance().getColor(
				0xff, 0xd0, 0x31);
		tool_item_inner_top_checked1 = ColorCache.getInstance().getColor(0xfe,
				0xd6, 0xa8);
		tool_item_inner_top_checked2 = ColorCache.getInstance().getColor(0xfc,
				180, 100);
		tool_item_inner_bottom_checked1 = ColorCache.getInstance().getColor(
				0xfc, 0xa1, 0x36);
		tool_item_inner_bottom_checked2 = ColorCache.getInstance().getColor(
				0xfe, 0xee, 170);
		tool_item_border_checked = ColorCache.getInstance().getColor(0x79,
				0x99, 0xc2);

		tool_item_outer_top_checked_track1 = ColorCache.getInstance().getColor(
				0xf9, 0xca, 0x71);
		tool_item_outer_top_checked_track2 = ColorCache.getInstance().getColor(
				250, 0xcd, 0x67);
		tool_item_outer_bottom_checked_track1 = ColorCache.getInstance()
				.getColor(0xf8, 200, 0x5b);
		tool_item_outer_bottom_checked_track2 = ColorCache.getInstance()
				.getColor(0xff, 0xda, 0x3b);
		tool_item_inner_top_checked_track1 = ColorCache.getInstance().getColor(
				0xfe, 0xb9, 0x6c);
		tool_item_inner_top_checked_track2 = ColorCache.getInstance().getColor(
				0xfd, 0xa4, 0x61);
		tool_item_inner_bottom_checked_track1 = ColorCache.getInstance()
				.getColor(0xfc, 0xa1, 0x36);
		tool_item_inner_bottom_checked_track2 = ColorCache.getInstance()
				.getColor(0xfe, 0xee, 170);
		tool_item_border_checked_track = ColorCache.getInstance().getColor(
				0x79, 0x99, 0xc2);

		tool_item_outer_top_showmenu_color1 = ColorCache.getInstance()
				.getColor(245, 245, 245);
		tool_item_outer_top_showmenu_color2 = ColorCache.getInstance()
				.getColor(245, 245, 245);
		tool_item_outer_bottom_showmenu_color1 = ColorCache.getInstance()
				.getColor(245, 245, 245);
		tool_item_outer_bottom_showmenu_color2 = ColorCache.getInstance()
				.getColor(245, 245, 245);
		tool_item_inner_top_showmenu_color1 = ColorCache.getInstance()
				.getColor(245, 245, 245);
		tool_item_inner_top_showmenu_color2 = ColorCache.getInstance()
				.getColor(245, 245, 245);
		tool_item_inner_bottom_showmenu_color1 = ColorCache.getInstance()
				.getColor(245, 245, 245);
		tool_item_inner_bottom_showmenu_color2 = ColorCache.getInstance()
				.getColor(245, 245, 245);
		tool_item_border_showmenu_color = ColorCache.getInstance().getColor(
				134, 134, 134);

		tool_chevronImage = ImageCache.getImage("icons/chevron.gif");

		tool_item_separater_darkcolor = ColorCache.getInstance().getColor(154,
				198, 255);
		tool_item_separater_lightcolor = ColorCache.getInstance().getColor(255,
				255, 255);

		tool_item_fg = ColorCache.getInstance().getColor(0x15, 0x42, 0x8b);
		tool_item_fg_disabled = COLOR_TEXT_GRAY;

		showToolImageShadow = false;

		showToolItemShadow = true;

		tool_item_arrow_bg = ColorCache.getInstance()
				.getColor(0x15, 0x42, 0x8b);
		tool_item_arrow_bg_track = ColorCache.getInstance().getColor(0x15,
				0x42, 0x8b);
		tool_item_arrow_bg_disabled = COLOR_TEXT_GRAY;

		tool_item_check_separater_drakcolor = ColorCache.getInstance()
				.getColor(205, 181, 130);
		tool_item_check_separater_lightcolor = ColorCache.getInstance()
				.getColor(255, 246, 233);

		toolbar_bgColors = new Color[] {
				ColorCache.getInstance().getColor(227, 238, 255),
				ColorCache.getInstance().getColor(217, 232, 253),
				ColorCache.getInstance().getColor(163, 193, 234) };
		toolbar_bgColors_percents = new int[] { 55, 100 };

		menu_control_bg = ColorCache.getInstance().getColor(245, 245, 245);
		menu_control_fg = ColorCache.getInstance().getColor(134, 134, 134);

		menu_control_image_rect_bg = ColorCache.getInstance().getColor(233,
				238, 238);
		menu_control_image_rect_fg = ColorCache.getInstance().getColor(197,
				197, 197);

		menu_control_item_separater_color = ColorCache.getInstance().getColor(
				197, 197, 197);

		menu_control_item_outer_top_track1 = ColorCache.getInstance().getColor(
				0xff, 0xff, 0xfb);
		menu_control_item_outer_top_track2 = ColorCache.getInstance().getColor(
				0xff, 0xf9, 0xe3);
		menu_control_item_outer_bottom_track1 = ColorCache.getInstance()
				.getColor(0xff, 0xf2, 0xc9);
		menu_control_item_outer_bottom_track2 = ColorCache.getInstance()
				.getColor(0xff, 0xf8, 0xb5);
		menu_control_item_inner_top_track1 = ColorCache.getInstance().getColor(
				0xff, 0xfc, 0xe5);
		menu_control_item_inner_top_track2 = ColorCache.getInstance().getColor(
				0xff, 0xeb, 0xa6);
		menu_control_item_inner_bottom_track1 = ColorCache.getInstance()
				.getColor(0xff, 0xd5, 0x67);
		menu_control_item_inner_bottom_track2 = ColorCache.getInstance()
				.getColor(0xff, 0xe4, 0x91);
		menu_control_item_border_track = ColorCache.getInstance().getColor(216,
				202, 149);

		menu_control_item_fg = ColorCache.getInstance().getColor(0x15, 0x42,
				0x8b);
		menu_control_item_fg_disabled = COLOR_TEXT_GRAY;

		menu_control_item_arrow_bg = ColorCache.getInstance().getColor(0x15,
				0x42, 0x8b);
		menu_control_item_arrow_bg_track = ColorCache.getInstance().getColor(
				0x15, 0x42, 0x8b);
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

		tool_menu_control_border = ColorCache.getInstance().getColor(134, 134,
				134);
	}
}
