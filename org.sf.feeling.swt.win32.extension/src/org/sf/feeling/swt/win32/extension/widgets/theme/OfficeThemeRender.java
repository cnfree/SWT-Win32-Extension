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

public abstract class OfficeThemeRender extends ThemeRender{
		
	protected Color menu_item_outer_top_track1;
	protected Color menu_item_outer_top_track2;
	protected Color menu_item_outer_bottom_track1;
	protected Color menu_item_outer_bottom_track2;
	protected Color menu_item_inner_top_track1;
	protected Color menu_item_inner_top_track2;
	protected Color menu_item_inner_bottom_track1;
	protected Color menu_item_inner_bottom_track2;
	protected Color menu_item_border_track;
	
	
	protected Color menu_item_outer_top_selected1;
	protected Color menu_item_outer_top_selected2;
	protected Color menu_item_outer_bottom_selected1;
	protected Color menu_item_outer_bottom_selected2;
	protected Color menu_item_inner_top_selected1;
	protected Color menu_item_inner_top_selected2;
	protected Color menu_item_inner_bottom_selected1;
	protected Color menu_item_inner_bottom_selected2;
	protected Color menu_item_border_selected;
	

	protected Color tool_item_outer_top_track1;
	protected Color tool_item_outer_top_track2;
	protected Color tool_item_outer_bottom_track1;
	protected Color tool_item_outer_bottom_track2;
	protected Color tool_item_inner_top_track1;
	protected Color tool_item_inner_top_track2;
	protected Color tool_item_inner_bottom_track1;
	protected Color tool_item_inner_bottom_track2;
	protected Color tool_item_border_track;
	
	protected Color tool_item_outer_top_selected1;
	protected Color tool_item_outer_top_selected2;
	protected Color tool_item_outer_bottom_selected1;
	protected Color tool_item_outer_bottom_selected2;
	protected Color tool_item_inner_top_selected1;
	protected Color tool_item_inner_top_selected2;
	protected Color tool_item_inner_bottom_selected1;
	protected Color tool_item_inner_bottom_selected2;
	protected Color tool_item_border_selected;
	
	protected Color tool_item_outer_top_checked1;
	protected Color tool_item_outer_top_checked2;
	protected Color tool_item_outer_bottom_checked1;
	protected Color tool_item_outer_bottom_checked2;
	protected Color tool_item_inner_top_checked1;
	protected Color tool_item_inner_top_checked2;
	protected Color tool_item_inner_bottom_checked1;
	protected Color tool_item_inner_bottom_checked2;
	protected Color tool_item_border_checked;
	
	protected Color tool_item_outer_top_checked_track1;
	protected Color tool_item_outer_top_checked_track2;
	protected Color tool_item_outer_bottom_checked_track1;
	protected Color tool_item_outer_bottom_checked_track2;
	protected Color tool_item_inner_top_checked_track1;
	protected Color tool_item_inner_top_checked_track2;
	protected Color tool_item_inner_bottom_checked_track1;
	protected Color tool_item_inner_bottom_checked_track2;
	protected Color tool_item_border_checked_track;
	
	protected Color tool_item_outer_top_showmenu_color1;
	protected Color tool_item_outer_top_showmenu_color2;
	protected Color tool_item_outer_bottom_showmenu_color1;
	protected Color tool_item_outer_bottom_showmenu_color2;
	protected Color tool_item_inner_top_showmenu_color1;
	protected Color tool_item_inner_top_showmenu_color2;
	protected Color tool_item_inner_bottom_showmenu_color1;
	protected Color tool_item_inner_bottom_showmenu_color2;
	protected Color tool_item_border_showmenu_color;
	
	protected Color tool_item_check_separater_drakcolor;
	protected Color tool_item_check_separater_lightcolor;
	
	protected Color menu_control_item_outer_top_track1;
	protected Color menu_control_item_outer_top_track2;
	protected Color menu_control_item_outer_bottom_track1;
	protected Color menu_control_item_outer_bottom_track2;
	protected Color menu_control_item_inner_top_track1;
	protected Color menu_control_item_inner_top_track2;
	protected Color menu_control_item_inner_bottom_track1;
	protected Color menu_control_item_inner_bottom_track2;
	protected Color menu_control_item_border_track;

	
	public Image getCheckImage() {
		return checkImage;
	}
	public void setCheckImage(Image checkImage) {
		this.checkImage = checkImage;
	}
	
	public Color getMenu_control_item_border_track() {
		return menu_control_item_border_track;
	}
	public void setMenu_control_item_border_track(
			Color menu_control_item_border_track) {
		this.menu_control_item_border_track = menu_control_item_border_track;
	}
	
	public Color getMenu_control_item_inner_bottom_track1() {
		return menu_control_item_inner_bottom_track1;
	}
	public void setMenu_control_item_inner_bottom_track1(
			Color menu_control_item_inner_bottom_track1) {
		this.menu_control_item_inner_bottom_track1 = menu_control_item_inner_bottom_track1;
	}
	public Color getMenu_control_item_inner_bottom_track2() {
		return menu_control_item_inner_bottom_track2;
	}
	public void setMenu_control_item_inner_bottom_track2(
			Color menu_control_item_inner_bottom_track2) {
		this.menu_control_item_inner_bottom_track2 = menu_control_item_inner_bottom_track2;
	}
	public Color getMenu_control_item_inner_top_track1() {
		return menu_control_item_inner_top_track1;
	}
	public void setMenu_control_item_inner_top_track1(
			Color menu_control_item_inner_top_track1) {
		this.menu_control_item_inner_top_track1 = menu_control_item_inner_top_track1;
	}
	public Color getMenu_control_item_inner_top_track2() {
		return menu_control_item_inner_top_track2;
	}
	public void setMenu_control_item_inner_top_track2(
			Color menu_control_item_inner_top_track2) {
		this.menu_control_item_inner_top_track2 = menu_control_item_inner_top_track2;
	}
	public Color getMenu_control_item_outer_bottom_track1() {
		return menu_control_item_outer_bottom_track1;
	}
	public void setMenu_control_item_outer_bottom_track1(
			Color menu_control_item_outer_bottom_track1) {
		this.menu_control_item_outer_bottom_track1 = menu_control_item_outer_bottom_track1;
	}
	public Color getMenu_control_item_outer_bottom_track2() {
		return menu_control_item_outer_bottom_track2;
	}
	public void setMenu_control_item_outer_bottom_track2(
			Color menu_control_item_outer_bottom_track2) {
		this.menu_control_item_outer_bottom_track2 = menu_control_item_outer_bottom_track2;
	}
	public Color getMenu_control_item_outer_top_track1() {
		return menu_control_item_outer_top_track1;
	}
	public void setMenu_control_item_outer_top_track1(
			Color menu_control_item_outer_top_track1) {
		this.menu_control_item_outer_top_track1 = menu_control_item_outer_top_track1;
	}
	public Color getMenu_control_item_outer_top_track2() {
		return menu_control_item_outer_top_track2;
	}
	public void setMenu_control_item_outer_top_track2(
			Color menu_control_item_outer_top_track2) {
		this.menu_control_item_outer_top_track2 = menu_control_item_outer_top_track2;
	}
	
	public Color getMenu_item_border_selected() {
		return menu_item_border_selected;
	}
	public void setMenu_item_border_selected(Color menu_item_border_selected) {
		this.menu_item_border_selected = menu_item_border_selected;
	}
	public Color getMenu_item_border_track() {
		return menu_item_border_track;
	}
	public void setMenu_item_border_track(Color menu_item_border_track) {
		this.menu_item_border_track = menu_item_border_track;
	}
	
	public Color getMenu_item_inner_bottom_selected1() {
		return menu_item_inner_bottom_selected1;
	}
	public void setMenu_item_inner_bottom_selected1(
			Color menu_item_inner_bottom_selected1) {
		this.menu_item_inner_bottom_selected1 = menu_item_inner_bottom_selected1;
	}
	public Color getMenu_item_inner_bottom_selected2() {
		return menu_item_inner_bottom_selected2;
	}
	public void setMenu_item_inner_bottom_selected2(
			Color menu_item_inner_bottom_selected2) {
		this.menu_item_inner_bottom_selected2 = menu_item_inner_bottom_selected2;
	}
	public Color getMenu_item_inner_bottom_track1() {
		return menu_item_inner_bottom_track1;
	}
	public void setMenu_item_inner_bottom_track1(Color menu_item_inner_bottom_track1) {
		this.menu_item_inner_bottom_track1 = menu_item_inner_bottom_track1;
	}
	public Color getMenu_item_inner_bottom_track2() {
		return menu_item_inner_bottom_track2;
	}
	public void setMenu_item_inner_bottom_track2(Color menu_item_inner_bottom_track2) {
		this.menu_item_inner_bottom_track2 = menu_item_inner_bottom_track2;
	}
	public Color getMenu_item_inner_top_selected1() {
		return menu_item_inner_top_selected1;
	}
	public void setMenu_item_inner_top_selected1(Color menu_item_inner_top_selected1) {
		this.menu_item_inner_top_selected1 = menu_item_inner_top_selected1;
	}
	public Color getMenu_item_inner_top_selected2() {
		return menu_item_inner_top_selected2;
	}
	public void setMenu_item_inner_top_selected2(Color menu_item_inner_top_selected2) {
		this.menu_item_inner_top_selected2 = menu_item_inner_top_selected2;
	}
	public Color getMenu_item_inner_top_track1() {
		return menu_item_inner_top_track1;
	}
	public void setMenu_item_inner_top_track1(Color menu_item_inner_top_track1) {
		this.menu_item_inner_top_track1 = menu_item_inner_top_track1;
	}
	public Color getMenu_item_inner_top_track2() {
		return menu_item_inner_top_track2;
	}
	public void setMenu_item_inner_top_track2(Color menu_item_inner_top_track2) {
		this.menu_item_inner_top_track2 = menu_item_inner_top_track2;
	}
	public Color getMenu_item_outer_bottom_selected1() {
		return menu_item_outer_bottom_selected1;
	}
	public void setMenu_item_outer_bottom_selected1(
			Color menu_item_outer_bottom_selected1) {
		this.menu_item_outer_bottom_selected1 = menu_item_outer_bottom_selected1;
	}
	public Color getMenu_item_outer_bottom_selected2() {
		return menu_item_outer_bottom_selected2;
	}
	public void setMenu_item_outer_bottom_selected2(
			Color menu_item_outer_bottom_selected2) {
		this.menu_item_outer_bottom_selected2 = menu_item_outer_bottom_selected2;
	}
	public Color getMenu_item_outer_bottom_track1() {
		return menu_item_outer_bottom_track1;
	}
	public void setMenu_item_outer_bottom_track1(Color menu_item_outer_bottom_track1) {
		this.menu_item_outer_bottom_track1 = menu_item_outer_bottom_track1;
	}
	public Color getMenu_item_outer_bottom_track2() {
		return menu_item_outer_bottom_track2;
	}
	public void setMenu_item_outer_bottom_track2(Color menu_item_outer_bottom_track2) {
		this.menu_item_outer_bottom_track2 = menu_item_outer_bottom_track2;
	}
	public Color getMenu_item_outer_top_selected1() {
		return menu_item_outer_top_selected1;
	}
	public void setMenu_item_outer_top_selected1(Color menu_item_outer_top_selected1) {
		this.menu_item_outer_top_selected1 = menu_item_outer_top_selected1;
	}
	public Color getMenu_item_outer_top_selected2() {
		return menu_item_outer_top_selected2;
	}
	public void setMenu_item_outer_top_selected2(Color menu_item_outer_top_selected2) {
		this.menu_item_outer_top_selected2 = menu_item_outer_top_selected2;
	}
	public Color getMenu_item_outer_top_track1() {
		return menu_item_outer_top_track1;
	}
	public void setMenu_item_outer_top_track1(Color menu_item_outer_top_track1) {
		this.menu_item_outer_top_track1 = menu_item_outer_top_track1;
	}
	public Color getMenu_item_outer_top_track2() {
		return menu_item_outer_top_track2;
	}
	public void setMenu_item_outer_top_track2(Color menu_item_outer_top_track2) {
		this.menu_item_outer_top_track2 = menu_item_outer_top_track2;
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
	
	public Color getTool_item_border_checked() {
		return tool_item_border_checked;
	}
	public void setTool_item_border_checked(Color tool_item_border_checked) {
		this.tool_item_border_checked = tool_item_border_checked;
	}
	public Color getTool_item_border_checked_track() {
		return tool_item_border_checked_track;
	}
	public void setTool_item_border_checked_track(
			Color tool_item_border_checked_track) {
		this.tool_item_border_checked_track = tool_item_border_checked_track;
	}
	public Color getTool_item_border_selected() {
		return tool_item_border_selected;
	}
	public void setTool_item_border_selected(Color tool_item_border_selected) {
		this.tool_item_border_selected = tool_item_border_selected;
	}
	public Color getTool_item_border_showmenu_color() {
		return tool_item_border_showmenu_color;
	}
	public void setTool_item_border_showmenu_color(
			Color tool_item_border_showmenu_color) {
		this.tool_item_border_showmenu_color = tool_item_border_showmenu_color;
	}
	public Color getTool_item_border_track() {
		return tool_item_border_track;
	}
	public void setTool_item_border_track(Color tool_item_border_track) {
		this.tool_item_border_track = tool_item_border_track;
	}
	public Color getTool_item_check_separater_drakcolor() {
		return tool_item_check_separater_drakcolor;
	}
	public void setTool_item_check_separater_drakcolor(
			Color tool_item_check_separater_drakcolor) {
		this.tool_item_check_separater_drakcolor = tool_item_check_separater_drakcolor;
	}
	public Color getTool_item_check_separater_lightcolor() {
		return tool_item_check_separater_lightcolor;
	}
	public void setTool_item_check_separater_lightcolor(
			Color tool_item_check_separater_lightcolor) {
		this.tool_item_check_separater_lightcolor = tool_item_check_separater_lightcolor;
	}
	
	public Color getTool_item_inner_bottom_checked_track1() {
		return tool_item_inner_bottom_checked_track1;
	}
	public void setTool_item_inner_bottom_checked_track1(
			Color tool_item_inner_bottom_checked_track1) {
		this.tool_item_inner_bottom_checked_track1 = tool_item_inner_bottom_checked_track1;
	}
	public Color getTool_item_inner_bottom_checked_track2() {
		return tool_item_inner_bottom_checked_track2;
	}
	public void setTool_item_inner_bottom_checked_track2(
			Color tool_item_inner_bottom_checked_track2) {
		this.tool_item_inner_bottom_checked_track2 = tool_item_inner_bottom_checked_track2;
	}
	public Color getTool_item_inner_bottom_checked1() {
		return tool_item_inner_bottom_checked1;
	}
	public void setTool_item_inner_bottom_checked1(
			Color tool_item_inner_bottom_checked1) {
		this.tool_item_inner_bottom_checked1 = tool_item_inner_bottom_checked1;
	}
	public Color getTool_item_inner_bottom_checked2() {
		return tool_item_inner_bottom_checked2;
	}
	public void setTool_item_inner_bottom_checked2(
			Color tool_item_inner_bottom_checked2) {
		this.tool_item_inner_bottom_checked2 = tool_item_inner_bottom_checked2;
	}
	public Color getTool_item_inner_bottom_selected1() {
		return tool_item_inner_bottom_selected1;
	}
	public void setTool_item_inner_bottom_selected1(
			Color tool_item_inner_bottom_selected1) {
		this.tool_item_inner_bottom_selected1 = tool_item_inner_bottom_selected1;
	}
	public Color getTool_item_inner_bottom_selected2() {
		return tool_item_inner_bottom_selected2;
	}
	public void setTool_item_inner_bottom_selected2(
			Color tool_item_inner_bottom_selected2) {
		this.tool_item_inner_bottom_selected2 = tool_item_inner_bottom_selected2;
	}
	public Color getTool_item_inner_bottom_showmenu_color1() {
		return tool_item_inner_bottom_showmenu_color1;
	}
	public void setTool_item_inner_bottom_showmenu_color1(
			Color tool_item_inner_bottom_showmenu_color1) {
		this.tool_item_inner_bottom_showmenu_color1 = tool_item_inner_bottom_showmenu_color1;
	}
	public Color getTool_item_inner_bottom_showmenu_color2() {
		return tool_item_inner_bottom_showmenu_color2;
	}
	public void setTool_item_inner_bottom_showmenu_color2(
			Color tool_item_inner_bottom_showmenu_color2) {
		this.tool_item_inner_bottom_showmenu_color2 = tool_item_inner_bottom_showmenu_color2;
	}
	public Color getTool_item_inner_bottom_track1() {
		return tool_item_inner_bottom_track1;
	}
	public void setTool_item_inner_bottom_track1(Color tool_item_inner_bottom_track1) {
		this.tool_item_inner_bottom_track1 = tool_item_inner_bottom_track1;
	}
	public Color getTool_item_inner_bottom_track2() {
		return tool_item_inner_bottom_track2;
	}
	public void setTool_item_inner_bottom_track2(Color tool_item_inner_bottom_track2) {
		this.tool_item_inner_bottom_track2 = tool_item_inner_bottom_track2;
	}
	public Color getTool_item_inner_top_checked_track1() {
		return tool_item_inner_top_checked_track1;
	}
	public void setTool_item_inner_top_checked_track1(
			Color tool_item_inner_top_checked_track1) {
		this.tool_item_inner_top_checked_track1 = tool_item_inner_top_checked_track1;
	}
	public Color getTool_item_inner_top_checked_track2() {
		return tool_item_inner_top_checked_track2;
	}
	public void setTool_item_inner_top_checked_track2(
			Color tool_item_inner_top_checked_track2) {
		this.tool_item_inner_top_checked_track2 = tool_item_inner_top_checked_track2;
	}
	public Color getTool_item_inner_top_checked1() {
		return tool_item_inner_top_checked1;
	}
	public void setTool_item_inner_top_checked1(Color tool_item_inner_top_checked1) {
		this.tool_item_inner_top_checked1 = tool_item_inner_top_checked1;
	}
	public Color getTool_item_inner_top_checked2() {
		return tool_item_inner_top_checked2;
	}
	public void setTool_item_inner_top_checked2(Color tool_item_inner_top_checked2) {
		this.tool_item_inner_top_checked2 = tool_item_inner_top_checked2;
	}
	public Color getTool_item_inner_top_selected1() {
		return tool_item_inner_top_selected1;
	}
	public void setTool_item_inner_top_selected1(Color tool_item_inner_top_selected1) {
		this.tool_item_inner_top_selected1 = tool_item_inner_top_selected1;
	}
	public Color getTool_item_inner_top_selected2() {
		return tool_item_inner_top_selected2;
	}
	public void setTool_item_inner_top_selected2(Color tool_item_inner_top_selected2) {
		this.tool_item_inner_top_selected2 = tool_item_inner_top_selected2;
	}
	public Color getTool_item_inner_top_showmenu_color1() {
		return tool_item_inner_top_showmenu_color1;
	}
	public void setTool_item_inner_top_showmenu_color1(
			Color tool_item_inner_top_showmenu_color1) {
		this.tool_item_inner_top_showmenu_color1 = tool_item_inner_top_showmenu_color1;
	}
	public Color getTool_item_inner_top_showmenu_color2() {
		return tool_item_inner_top_showmenu_color2;
	}
	public void setTool_item_inner_top_showmenu_color2(
			Color tool_item_inner_top_showmenu_color2) {
		this.tool_item_inner_top_showmenu_color2 = tool_item_inner_top_showmenu_color2;
	}
	public Color getTool_item_inner_top_track1() {
		return tool_item_inner_top_track1;
	}
	public void setTool_item_inner_top_track1(Color tool_item_inner_top_track1) {
		this.tool_item_inner_top_track1 = tool_item_inner_top_track1;
	}
	public Color getTool_item_inner_top_track2() {
		return tool_item_inner_top_track2;
	}
	public void setTool_item_inner_top_track2(Color tool_item_inner_top_track2) {
		this.tool_item_inner_top_track2 = tool_item_inner_top_track2;
	}
	public Color getTool_item_outer_bottom_checked_track1() {
		return tool_item_outer_bottom_checked_track1;
	}
	public void setTool_item_outer_bottom_checked_track1(
			Color tool_item_outer_bottom_checked_track1) {
		this.tool_item_outer_bottom_checked_track1 = tool_item_outer_bottom_checked_track1;
	}
	public Color getTool_item_outer_bottom_checked_track2() {
		return tool_item_outer_bottom_checked_track2;
	}
	public void setTool_item_outer_bottom_checked_track2(
			Color tool_item_outer_bottom_checked_track2) {
		this.tool_item_outer_bottom_checked_track2 = tool_item_outer_bottom_checked_track2;
	}
	public Color getTool_item_outer_bottom_checked1() {
		return tool_item_outer_bottom_checked1;
	}
	public void setTool_item_outer_bottom_checked1(
			Color tool_item_outer_bottom_checked1) {
		this.tool_item_outer_bottom_checked1 = tool_item_outer_bottom_checked1;
	}
	public Color getTool_item_outer_bottom_checked2() {
		return tool_item_outer_bottom_checked2;
	}
	public void setTool_item_outer_bottom_checked2(
			Color tool_item_outer_bottom_checked2) {
		this.tool_item_outer_bottom_checked2 = tool_item_outer_bottom_checked2;
	}
	public Color getTool_item_outer_bottom_selected1() {
		return tool_item_outer_bottom_selected1;
	}
	public void setTool_item_outer_bottom_selected1(
			Color tool_item_outer_bottom_selected1) {
		this.tool_item_outer_bottom_selected1 = tool_item_outer_bottom_selected1;
	}
	public Color getTool_item_outer_bottom_selected2() {
		return tool_item_outer_bottom_selected2;
	}
	public void setTool_item_outer_bottom_selected2(
			Color tool_item_outer_bottom_selected2) {
		this.tool_item_outer_bottom_selected2 = tool_item_outer_bottom_selected2;
	}
	public Color getTool_item_outer_bottom_showmenu_color1() {
		return tool_item_outer_bottom_showmenu_color1;
	}
	public void setTool_item_outer_bottom_showmenu_color1(
			Color tool_item_outer_bottom_showmenu_color1) {
		this.tool_item_outer_bottom_showmenu_color1 = tool_item_outer_bottom_showmenu_color1;
	}
	public Color getTool_item_outer_bottom_showmenu_color2() {
		return tool_item_outer_bottom_showmenu_color2;
	}
	public void setTool_item_outer_bottom_showmenu_color2(
			Color tool_item_outer_bottom_showmenu_color2) {
		this.tool_item_outer_bottom_showmenu_color2 = tool_item_outer_bottom_showmenu_color2;
	}
	public Color getTool_item_outer_bottom_track1() {
		return tool_item_outer_bottom_track1;
	}
	public void setTool_item_outer_bottom_track1(Color tool_item_outer_bottom_track1) {
		this.tool_item_outer_bottom_track1 = tool_item_outer_bottom_track1;
	}
	public Color getTool_item_outer_bottom_track2() {
		return tool_item_outer_bottom_track2;
	}
	public void setTool_item_outer_bottom_track2(Color tool_item_outer_bottom_track2) {
		this.tool_item_outer_bottom_track2 = tool_item_outer_bottom_track2;
	}
	public Color getTool_item_outer_top_checked_track1() {
		return tool_item_outer_top_checked_track1;
	}
	public void setTool_item_outer_top_checked_track1(
			Color tool_item_outer_top_checked_track1) {
		this.tool_item_outer_top_checked_track1 = tool_item_outer_top_checked_track1;
	}
	public Color getTool_item_outer_top_checked_track2() {
		return tool_item_outer_top_checked_track2;
	}
	public void setTool_item_outer_top_checked_track2(
			Color tool_item_outer_top_checked_track2) {
		this.tool_item_outer_top_checked_track2 = tool_item_outer_top_checked_track2;
	}
	public Color getTool_item_outer_top_checked1() {
		return tool_item_outer_top_checked1;
	}
	public void setTool_item_outer_top_checked1(Color tool_item_outer_top_checked1) {
		this.tool_item_outer_top_checked1 = tool_item_outer_top_checked1;
	}
	public Color getTool_item_outer_top_checked2() {
		return tool_item_outer_top_checked2;
	}
	public void setTool_item_outer_top_checked2(Color tool_item_outer_top_checked2) {
		this.tool_item_outer_top_checked2 = tool_item_outer_top_checked2;
	}
	public Color getTool_item_outer_top_selected1() {
		return tool_item_outer_top_selected1;
	}
	public void setTool_item_outer_top_selected1(Color tool_item_outer_top_selected1) {
		this.tool_item_outer_top_selected1 = tool_item_outer_top_selected1;
	}
	public Color getTool_item_outer_top_selected2() {
		return tool_item_outer_top_selected2;
	}
	public void setTool_item_outer_top_selected2(Color tool_item_outer_top_selected2) {
		this.tool_item_outer_top_selected2 = tool_item_outer_top_selected2;
	}
	public Color getTool_item_outer_top_showmenu_color1() {
		return tool_item_outer_top_showmenu_color1;
	}
	public void setTool_item_outer_top_showmenu_color1(
			Color tool_item_outer_top_showmenu_color1) {
		this.tool_item_outer_top_showmenu_color1 = tool_item_outer_top_showmenu_color1;
	}
	public Color getTool_item_outer_top_showmenu_color2() {
		return tool_item_outer_top_showmenu_color2;
	}
	public void setTool_item_outer_top_showmenu_color2(
			Color tool_item_outer_top_showmenu_color2) {
		this.tool_item_outer_top_showmenu_color2 = tool_item_outer_top_showmenu_color2;
	}
	public Color getTool_item_outer_top_track1() {
		return tool_item_outer_top_track1;
	}
	public void setTool_item_outer_top_track1(Color tool_item_outer_top_track1) {
		this.tool_item_outer_top_track1 = tool_item_outer_top_track1;
	}
	public Color getTool_item_outer_top_track2() {
		return tool_item_outer_top_track2;
	}
	public void setTool_item_outer_top_track2(Color tool_item_outer_top_track2) {
		this.tool_item_outer_top_track2 = tool_item_outer_top_track2;
	}
	
	void checkSubclass() {

	}
}
