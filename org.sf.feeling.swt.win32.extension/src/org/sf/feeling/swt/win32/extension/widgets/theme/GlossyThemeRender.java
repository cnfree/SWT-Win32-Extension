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
import org.eclipse.swt.graphics.Image;

public abstract class GlossyThemeRender extends ThemeRender {

	protected Color menu_item_bg_color1;

	protected int menu_item_bg_color1_alpha;

	protected Color menu_item_bg_color2;

	protected int menu_item_bg_color2_alpha;

	protected Color menu_item_outer_border;

	protected Color menu_item_inner_border_track;

	protected Color menu_item_inner_border_selected;

	protected Color menu_item_bg_glossy_track1;

	protected int menu_item_bg_glossy_track1_alpha;

	protected Color menu_item_bg_glossy_track2;

	protected int menu_item_bg_glossy_track2_alpha;

	protected Color menu_item_bg_glossy_selected1;

	protected int menu_item_bg_glossy_selected1_alpha;

	protected Color menu_item_bg_glossy_selected2;

	protected int menu_item_bg_glossy_selected2_alpha;

	protected Color menu_item_bg_glow_track;

	protected Color tool_item_bg_color1;

	protected int tool_item_bg_color1_alpha;

	protected Color tool_item_bg_color2;

	protected int tool_item_bg_color2_alpha;

	protected Color tool_item_outer_border;

	protected Color tool_item_inner_border_track;

	protected Color tool_item_inner_border_selected;

	protected Color tool_item_bg_glossy_track1;

	protected int tool_item_bg_glossy_track1_alpha;

	protected Color tool_item_bg_glossy_track2;

	protected int tool_item_bg_glossy_track2_alpha;

	protected Color tool_item_bg_glossy_selected1;

	protected int tool_item_bg_glossy_selected1_alpha;

	protected Color tool_item_bg_glossy_selected2;

	protected int tool_item_bg_glossy_selected2_alpha;

	protected Color tool_item_bg_glossy_showMenu;

	protected Color tool_item_bg_glow_track;

	protected Color tool_item_bg_checked_glow_track;

	protected Color tool_item_bg_checked_glow;

	protected Color tool_item_check_separater_color;

	protected Color toolbar_bg_glossy_color1;

	protected int toolbar_bg_glossy_color1_alpha;

	protected Color toolbar_bg_glossy_color2;

	protected int toolbar_bg_glossy_color2_alpha;

	protected Color toolbar_bg_glow_color1;

	protected int toolbar_bg_glow_color1_alpha;

	protected Color toolbar_bg_glow_color2;

	protected int toolbar_bg_glow_color2_alpha;

	protected Color menu_control_item_bg_track;

	protected Color menu_control_item_bg_color1;

	protected int menu_control_item_bg_color1_alpha;

	protected Color menu_control_item_bg_color2;

	protected int menu_control_item_bg_color2_alpha;

	protected Color menu_control_item_outer_border;

	protected Color menu_control_item_inner_border;

	protected Color menu_control_item_bg_glossy_track1;

	protected int menu_control_item_bg_glossy_track1_alpha;

	protected Color menu_control_item_bg_glossy_track2;

	protected int menu_control_item_bg_glossy_track2_alpha;

	protected Color menu_control_item_bg_glow;


	public Image getCheckImage() {
		return checkImage;
	}

	public void setCheckImage(Image checkImage) {
		this.checkImage = checkImage;
	}

	public Color getMenu_control_item_bg_color1() {
		return menu_control_item_bg_color1;
	}

	public void setMenu_control_item_bg_color1(Color menu_control_item_bg_color1) {
		this.menu_control_item_bg_color1 = menu_control_item_bg_color1;
	}

	public int getMenu_control_item_bg_color1_alpha() {
		return menu_control_item_bg_color1_alpha;
	}

	public void setMenu_control_item_bg_color1_alpha(
			int menu_control_item_bg_color1_alpha) {
		this.menu_control_item_bg_color1_alpha = menu_control_item_bg_color1_alpha;
	}

	public Color getMenu_control_item_bg_color2() {
		return menu_control_item_bg_color2;
	}

	public void setMenu_control_item_bg_color2(Color menu_control_item_bg_color2) {
		this.menu_control_item_bg_color2 = menu_control_item_bg_color2;
	}

	public int getMenu_control_item_bg_color2_alpha() {
		return menu_control_item_bg_color2_alpha;
	}

	public void setMenu_control_item_bg_color2_alpha(
			int menu_control_item_bg_color2_alpha) {
		this.menu_control_item_bg_color2_alpha = menu_control_item_bg_color2_alpha;
	}

	public Color getMenu_control_item_bg_glossy_track1() {
		return menu_control_item_bg_glossy_track1;
	}

	public void setMenu_control_item_bg_glossy_track1(
			Color menu_control_item_bg_glossy_track1) {
		this.menu_control_item_bg_glossy_track1 = menu_control_item_bg_glossy_track1;
	}

	public int getMenu_control_item_bg_glossy_track1_alpha() {
		return menu_control_item_bg_glossy_track1_alpha;
	}

	public void setMenu_control_item_bg_glossy_track1_alpha(
			int menu_control_item_bg_glossy_track1_alpha) {
		this.menu_control_item_bg_glossy_track1_alpha = menu_control_item_bg_glossy_track1_alpha;
	}

	public Color getMenu_control_item_bg_glossy_track2() {
		return menu_control_item_bg_glossy_track2;
	}

	public void setMenu_control_item_bg_glossy_track2(
			Color menu_control_item_bg_glossy_track2) {
		this.menu_control_item_bg_glossy_track2 = menu_control_item_bg_glossy_track2;
	}

	public int getMenu_control_item_bg_glossy_track2_alpha() {
		return menu_control_item_bg_glossy_track2_alpha;
	}

	public void setMenu_control_item_bg_glossy_track2_alpha(
			int menu_control_item_bg_glossy_track2_alpha) {
		this.menu_control_item_bg_glossy_track2_alpha = menu_control_item_bg_glossy_track2_alpha;
	}

	public Color getMenu_control_item_bg_glow() {
		return menu_control_item_bg_glow;
	}

	public void setMenu_control_item_bg_glow(Color menu_control_item_bg_glow) {
		this.menu_control_item_bg_glow = menu_control_item_bg_glow;
	}

	public Color getMenu_control_item_inner_border() {
		return menu_control_item_inner_border;
	}

	public void setMenu_control_item_inner_border(
			Color menu_control_item_inner_border) {
		this.menu_control_item_inner_border = menu_control_item_inner_border;
	}

	public Color getMenu_control_item_outer_border() {
		return menu_control_item_outer_border;
	}

	public void setMenu_control_item_outer_border(
			Color menu_control_item_outer_border) {
		this.menu_control_item_outer_border = menu_control_item_outer_border;
	}

	public Color getMenu_item_bg_color1() {
		return menu_item_bg_color1;
	}

	public void setMenu_item_bg_color1(Color menu_item_bg_color1) {
		this.menu_item_bg_color1 = menu_item_bg_color1;
	}

	public int getMenu_item_bg_color1_alpha() {
		return menu_item_bg_color1_alpha;
	}

	public void setMenu_item_bg_color1_alpha(int menu_item_bg_color1_alpha) {
		this.menu_item_bg_color1_alpha = menu_item_bg_color1_alpha;
	}

	public Color getMenu_item_bg_color2() {
		return menu_item_bg_color2;
	}

	public void setMenu_item_bg_color2(Color menu_item_bg_color2) {
		this.menu_item_bg_color2 = menu_item_bg_color2;
	}

	public int getMenu_item_bg_color2_alpha() {
		return menu_item_bg_color2_alpha;
	}

	public void setMenu_item_bg_color2_alpha(int menu_item_bg_color2_alpha) {
		this.menu_item_bg_color2_alpha = menu_item_bg_color2_alpha;
	}

	public Color getMenu_item_bg_glossy_selected1() {
		return menu_item_bg_glossy_selected1;
	}

	public void setMenu_item_bg_glossy_selected1(
			Color menu_item_bg_glossy_selected1) {
		this.menu_item_bg_glossy_selected1 = menu_item_bg_glossy_selected1;
	}

	public int getMenu_item_bg_glossy_selected1_alpha() {
		return menu_item_bg_glossy_selected1_alpha;
	}

	public void setMenu_item_bg_glossy_selected1_alpha(
			int menu_item_bg_glossy_selected1_alpha) {
		this.menu_item_bg_glossy_selected1_alpha = menu_item_bg_glossy_selected1_alpha;
	}

	public Color getMenu_item_bg_glossy_selected2() {
		return menu_item_bg_glossy_selected2;
	}

	public void setMenu_item_bg_glossy_selected2(
			Color menu_item_bg_glossy_selected2) {
		this.menu_item_bg_glossy_selected2 = menu_item_bg_glossy_selected2;
	}

	public int getMenu_item_bg_glossy_selected2_alpha() {
		return menu_item_bg_glossy_selected2_alpha;
	}

	public void setMenu_item_bg_glossy_selected2_alpha(
			int menu_item_bg_glossy_selected2_alpha) {
		this.menu_item_bg_glossy_selected2_alpha = menu_item_bg_glossy_selected2_alpha;
	}

	public Color getMenu_item_bg_glossy_track1() {
		return menu_item_bg_glossy_track1;
	}

	public void setMenu_item_bg_glossy_track1(Color menu_item_bg_glossy_track1) {
		this.menu_item_bg_glossy_track1 = menu_item_bg_glossy_track1;
	}

	public int getMenu_item_bg_glossy_track1_alpha() {
		return menu_item_bg_glossy_track1_alpha;
	}

	public void setMenu_item_bg_glossy_track1_alpha(
			int menu_item_bg_glossy_track1_alpha) {
		this.menu_item_bg_glossy_track1_alpha = menu_item_bg_glossy_track1_alpha;
	}

	public Color getMenu_item_bg_glossy_track2() {
		return menu_item_bg_glossy_track2;
	}

	public void setMenu_item_bg_glossy_track2(Color menu_item_bg_glossy_track2) {
		this.menu_item_bg_glossy_track2 = menu_item_bg_glossy_track2;
	}

	public int getMenu_item_bg_glossy_track2_alpha() {
		return menu_item_bg_glossy_track2_alpha;
	}

	public void setMenu_item_bg_glossy_track2_alpha(
			int menu_item_bg_glossy_track2_alpha) {
		this.menu_item_bg_glossy_track2_alpha = menu_item_bg_glossy_track2_alpha;
	}

	public Color getMenu_item_bg_glow_track() {
		return menu_item_bg_glow_track;
	}

	public void setMenu_item_bg_glow_track(Color menu_item_bg_glow_track) {
		this.menu_item_bg_glow_track = menu_item_bg_glow_track;
	}

	public Color getMenu_item_inner_border_selected() {
		return menu_item_inner_border_selected;
	}

	public void setMenu_item_inner_border_selected(
			Color menu_item_inner_border_selected) {
		this.menu_item_inner_border_selected = menu_item_inner_border_selected;
	}

	public Color getMenu_item_inner_border_track() {
		return menu_item_inner_border_track;
	}

	public void setMenu_item_inner_border_track(
			Color menu_item_inner_border_track) {
		this.menu_item_inner_border_track = menu_item_inner_border_track;
	}

	public Color getMenu_item_outer_border() {
		return menu_item_outer_border;
	}

	public void setMenu_item_outer_border(Color menu_item_outer_border) {
		this.menu_item_outer_border = menu_item_outer_border;
	}

	public Image getRedioImage() {
		return redioImage;
	}

	public void setRedioImage(Image redioImage) {
		this.redioImage = redioImage;
	}

	public boolean isShowMenuImageShadow() {
		return showMenuImageShadow;
	}

	public void setShowMenuImageShadow(boolean showMenuImageShadow) {
		this.showMenuImageShadow = showMenuImageShadow;
	}

	public Color getTool_item_bg_checked_glow() {
		return tool_item_bg_checked_glow;
	}

	public void setTool_item_bg_checked_glow(Color tool_item_bg_checked_glow) {
		this.tool_item_bg_checked_glow = tool_item_bg_checked_glow;
	}

	public Color getTool_item_bg_checked_glow_track() {
		return tool_item_bg_checked_glow_track;
	}

	public void setTool_item_bg_checked_glow_track(
			Color tool_item_bg_checked_glow_track) {
		this.tool_item_bg_checked_glow_track = tool_item_bg_checked_glow_track;
	}

	public Color getTool_item_bg_color1() {
		return tool_item_bg_color1;
	}

	public void setTool_item_bg_color1(Color tool_item_bg_color1) {
		this.tool_item_bg_color1 = tool_item_bg_color1;
	}

	public int getTool_item_bg_color1_alpha() {
		return tool_item_bg_color1_alpha;
	}

	public void setTool_item_bg_color1_alpha(int tool_item_bg_color1_alpha) {
		this.tool_item_bg_color1_alpha = tool_item_bg_color1_alpha;
	}

	public Color getTool_item_bg_color2() {
		return tool_item_bg_color2;
	}

	public void setTool_item_bg_color2(Color tool_item_bg_color2) {
		this.tool_item_bg_color2 = tool_item_bg_color2;
	}

	public int getTool_item_bg_color2_alpha() {
		return tool_item_bg_color2_alpha;
	}

	public void setTool_item_bg_color2_alpha(int tool_item_bg_color2_alpha) {
		this.tool_item_bg_color2_alpha = tool_item_bg_color2_alpha;
	}

	public Color getTool_item_bg_glossy_selected1() {
		return tool_item_bg_glossy_selected1;
	}

	public void setTool_item_bg_glossy_selected1(
			Color tool_item_bg_glossy_selected1) {
		this.tool_item_bg_glossy_selected1 = tool_item_bg_glossy_selected1;
	}

	public int getTool_item_bg_glossy_selected1_alpha() {
		return tool_item_bg_glossy_selected1_alpha;
	}

	public void setTool_item_bg_glossy_selected1_alpha(
			int tool_item_bg_glossy_selected1_alpha) {
		this.tool_item_bg_glossy_selected1_alpha = tool_item_bg_glossy_selected1_alpha;
	}

	public Color getTool_item_bg_glossy_selected2() {
		return tool_item_bg_glossy_selected2;
	}

	public void setTool_item_bg_glossy_selected2(
			Color tool_item_bg_glossy_selected2) {
		this.tool_item_bg_glossy_selected2 = tool_item_bg_glossy_selected2;
	}

	public int getTool_item_bg_glossy_selected2_alpha() {
		return tool_item_bg_glossy_selected2_alpha;
	}

	public void setTool_item_bg_glossy_selected2_alpha(
			int tool_item_bg_glossy_selected2_alpha) {
		this.tool_item_bg_glossy_selected2_alpha = tool_item_bg_glossy_selected2_alpha;
	}

	public Color getTool_item_bg_glossy_track1() {
		return tool_item_bg_glossy_track1;
	}

	public void setTool_item_bg_glossy_track1(Color tool_item_bg_glossy_track1) {
		this.tool_item_bg_glossy_track1 = tool_item_bg_glossy_track1;
	}

	public int getTool_item_bg_glossy_track1_alpha() {
		return tool_item_bg_glossy_track1_alpha;
	}

	public void setTool_item_bg_glossy_track1_alpha(
			int tool_item_bg_glossy_track1_alpha) {
		this.tool_item_bg_glossy_track1_alpha = tool_item_bg_glossy_track1_alpha;
	}

	public Color getTool_item_bg_glossy_track2() {
		return tool_item_bg_glossy_track2;
	}

	public void setTool_item_bg_glossy_track2(Color tool_item_bg_glossy_track2) {
		this.tool_item_bg_glossy_track2 = tool_item_bg_glossy_track2;
	}

	public int getTool_item_bg_glossy_track2_alpha() {
		return tool_item_bg_glossy_track2_alpha;
	}

	public void setTool_item_bg_glossy_track2_alpha(
			int tool_item_bg_glossy_track2_alpha) {
		this.tool_item_bg_glossy_track2_alpha = tool_item_bg_glossy_track2_alpha;
	}

	public Color getTool_item_bg_glow_track() {
		return tool_item_bg_glow_track;
	}

	public void setTool_item_bg_glow_track(Color tool_item_bg_glow_track) {
		this.tool_item_bg_glow_track = tool_item_bg_glow_track;
	}

	public Color getTool_item_check_separater_color() {
		return tool_item_check_separater_color;
	}

	public void setTool_item_check_separater_color(
			Color tool_item_check_separater_color) {
		this.tool_item_check_separater_color = tool_item_check_separater_color;
	}

	public Color getTool_item_inner_border_selected() {
		return tool_item_inner_border_selected;
	}

	public void setTool_item_inner_border_selected(
			Color tool_item_inner_border_selected) {
		this.tool_item_inner_border_selected = tool_item_inner_border_selected;
	}

	public Color getTool_item_inner_border_track() {
		return tool_item_inner_border_track;
	}

	public void setTool_item_inner_border_track(
			Color tool_item_inner_border_track) {
		this.tool_item_inner_border_track = tool_item_inner_border_track;
	}

	public Color getTool_item_outer_border() {
		return tool_item_outer_border;
	}

	public void setTool_item_outer_border(Color tool_item_outer_border) {
		this.tool_item_outer_border = tool_item_outer_border;
	}

	public Color getToolbar_bg_glossy_color1() {
		return toolbar_bg_glossy_color1;
	}

	public void setToolbar_bg_glossy_color1(Color toolbar_bg_glossy_color1) {
		this.toolbar_bg_glossy_color1 = toolbar_bg_glossy_color1;
	}

	public int getToolbar_bg_glossy_color1_alpha() {
		return toolbar_bg_glossy_color1_alpha;
	}

	public void setToolbar_bg_glossy_color1_alpha(
			int toolbar_bg_glossy_color1_alpha) {
		this.toolbar_bg_glossy_color1_alpha = toolbar_bg_glossy_color1_alpha;
	}

	public Color getToolbar_bg_glossy_color2() {
		return toolbar_bg_glossy_color2;
	}

	public void setToolbar_bg_glossy_color2(Color toolbar_bg_glossy_color2) {
		this.toolbar_bg_glossy_color2 = toolbar_bg_glossy_color2;
	}

	public int getToolbar_bg_glossy_color2_alpha() {
		return toolbar_bg_glossy_color2_alpha;
	}

	public void setToolbar_bg_glossy_color2_alpha(
			int toolbar_bg_glossy_color2_alpha) {
		this.toolbar_bg_glossy_color2_alpha = toolbar_bg_glossy_color2_alpha;
	}

	public Color getToolbar_bg_glow_color1() {
		return toolbar_bg_glow_color1;
	}

	public void setToolbar_bg_glow_color1(Color toolbar_bg_glow_color1) {
		this.toolbar_bg_glow_color1 = toolbar_bg_glow_color1;
	}

	public int getToolbar_bg_glow_color1_alpha() {
		return toolbar_bg_glow_color1_alpha;
	}

	public void setToolbar_bg_glow_color1_alpha(int toolbar_bg_glow_color1_alpha) {
		this.toolbar_bg_glow_color1_alpha = toolbar_bg_glow_color1_alpha;
	}

	public Color getToolbar_bg_glow_color2() {
		return toolbar_bg_glow_color2;
	}

	public void setToolbar_bg_glow_color2(Color toolbar_bg_glow_color2) {
		this.toolbar_bg_glow_color2 = toolbar_bg_glow_color2;
	}

	public int getToolbar_bg_glow_color2_alpha() {
		return toolbar_bg_glow_color2_alpha;
	}

	public void setToolbar_bg_glow_color2_alpha(int toolbar_bg_glow_color2_alpha) {
		this.toolbar_bg_glow_color2_alpha = toolbar_bg_glow_color2_alpha;
	}

	public Color getTool_item_bg_glossy_showMenu() {
		return tool_item_bg_glossy_showMenu;
	}

	public void setTool_item_bg_glossy_showMenu(
			Color tool_item_bg_glossy_showMenu) {
		this.tool_item_bg_glossy_showMenu = tool_item_bg_glossy_showMenu;
	}

	public Color getMenu_control_item_bg_track() {
		return menu_control_item_bg_track;
	}

	public void setMenu_control_item_bg_track(Color menu_control_item_bg_track) {
		this.menu_control_item_bg_track = menu_control_item_bg_track;
	}

	void checkSubclass() {

	}
}
