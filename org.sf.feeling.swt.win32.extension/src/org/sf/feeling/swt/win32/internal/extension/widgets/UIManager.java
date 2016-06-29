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
package org.sf.feeling.swt.win32.internal.extension.widgets;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Event;
import org.sf.feeling.swt.win32.extension.graphics.GraphicsUtil;
import org.sf.feeling.swt.win32.extension.widgets.ThemeConstants;
import org.sf.feeling.swt.win32.internal.extension.util.ColorCache;
import org.sf.feeling.swt.win32.internal.extension.util.ImageCache;

/**
 * Theme manage class.
 * 
 * @author <a href="mailto:cnfree2000@hotmail.com">cnfree</a>
 * 
 */
public class UIManager {

	private UIManager() {
	}

	private static List themes = new ArrayList();

	public static void installTheme(IWindow window) {
		if (GraphicsUtil.checkGdip())
			installTheme(ThemeConstants.STYLE_OFFICE2007, window);
	}

	public static void installTheme(String theme) {
		for (int i = 0; i < themes.size(); i++) {
			installTheme(theme, (IWindow) themes.get(i));
		}
	}

	public static void installTheme(String theme, final IWindow window) {
		if (themes.contains(window))
			unInstallTheme(window);
		if (!ThemeConstants.containTheme(theme))
			theme = ThemeConstants.STYLE_OFFICE2007;

		Image nImage = ImageCache.getImage("/icons/" + theme + "/w_n.gif");
		final String tempTheme = theme;
		window.setSkin(IContainer.BORDER_N, new ImageSkin(window.getDisplay(),
				nImage) {
			public Color getForeground(Event event) {
				switch (event.type) {
				case SWT.Deactivate:
					return getTitleBarDeactiveBackground(tempTheme);
				default:
					return getTitleBarForeground(tempTheme);
				}
			}
		});

		Image sImage = ImageCache.getImage("/icons/" + theme + "/w_s.gif");
		window.setSkin(IContainer.BORDER_S, new ImageSkin(window.getDisplay(),
				sImage));

		Image wImage = ImageCache.getImage("/icons/" + theme + "/w_w.gif");
		window.setSkin(IContainer.BORDER_W, new ImageSkin(window.getDisplay(),
				wImage));

		Image eImage = ImageCache.getImage("/icons/" + theme + "/w_e.gif");
		window.setSkin(IContainer.BORDER_E, new ImageSkin(window.getDisplay(),
				eImage));

		Image nwImage = ImageCache.getImage("/icons/" + theme + "/w_nw.gif");
		window.setSkin(IContainer.BORDER_NW, new ImageSkin(window.getDisplay(),
				nwImage));

		Image swImage = ImageCache.getImage("/icons/" + theme + "/w_sw.gif");
		window.setSkin(IContainer.BORDER_SW, new ImageSkin(window.getDisplay(),
				swImage));

		Image neImage = ImageCache.getImage("/icons/" + theme + "/w_ne.gif");
		window.setSkin(IContainer.BORDER_NE, new ImageSkin(window.getDisplay(),
				neImage));

		Image seImage = ImageCache.getImage("/icons/" + theme + "/w_se.gif");
		window.setSkin(IContainer.BORDER_SE, new ImageSkin(window.getDisplay(),
				seImage));

		{
			Image cImage = ImageCache.getImage("/icons/" + theme + "/w_c.gif");
			window.setSkin(new ImageSkin(window.getDisplay(), cImage));
		}

		{
			Image minImage1 = ImageCache.getImage("/icons/" + theme + "/w_min.gif");
			Image minImage2 = ImageCache.getImage("/icons/" + theme + "/w_min_h.gif");
			Image minImage3 = ImageCache.getImage("/icons/" + theme + "/w_min_c.gif");
			ImageSkin minSkin = new ImageSkin(window.getDisplay(), minImage1);
			minSkin.setMouseHover(minImage2);
			minSkin.setMouseDown(minImage3);
			window.setButtonSkin(ThemeConstants.BUTTON_MIN, minSkin);

			Image maxImage1 = ImageCache.getImage("/icons/" + theme + "/w_max.gif");
			Image maxImage2 = ImageCache.getImage("/icons/" + theme + "/w_max_h.gif");
			Image maxImage3 = ImageCache.getImage("/icons/" + theme + "/w_max_c.gif");
			ImageSkin maxSkin = new ImageSkin(window.getDisplay(), maxImage1);
			maxSkin.setMouseHover(maxImage2);
			maxSkin.setMouseDown(maxImage3);
			window.setButtonSkin(ThemeConstants.BUTTON_MAX, maxSkin);

			Image revImage1 = ImageCache.getImage("/icons/" + theme + "/w_rev.gif");
			Image revImage2 = ImageCache.getImage("/icons/" + theme + "/w_rev_h.gif");
			Image revImage3 = ImageCache.getImage("/icons/" + theme + "/w_rev_c.gif");
			ImageSkin revSkin = new ImageSkin(window.getDisplay(), revImage1);
			revSkin.setMouseHover(revImage2);
			revSkin.setMouseDown(revImage3);
			window.setButtonSkin(ThemeConstants.BUTTON_REV, revSkin);

			Image clsImage1 = ImageCache.getImage("/icons/" + theme + "/w_cls.gif");
			Image clsImage2 = ImageCache.getImage("/icons/" + theme + "/w_cls_h.gif");
			Image clsImage3 = ImageCache.getImage("/icons/" + theme + "/w_cls_c.gif");
			ImageSkin clsSkin = new ImageSkin(window.getDisplay(), clsImage1);
			clsSkin.setMouseHover(clsImage2);
			clsSkin.setMouseDown(clsImage3);
			window.setButtonSkin(ThemeConstants.BUTTON_CLOSE, clsSkin);

			/*
			 * Not all theme have help button.
			 */
			try {
				Image hlpImage1 = ImageCache.getImage("/icons/" + theme
						+ "/w_hlp.gif");
				Image hlpImage2 = ImageCache.getImage("/icons/" + theme
						+ "/w_hlp_h.gif");
				Image hlpImage3 = ImageCache.getImage("/icons/" + theme
						+ "/w_hlp_c.gif");
				ImageSkin hlpSkin = new ImageSkin(window.getDisplay(),
						hlpImage1);
				hlpSkin.setMouseHover(hlpImage2);
				hlpSkin.setMouseDown(hlpImage3);
				window.setButtonSkin(ThemeConstants.BUTTON_HELP, hlpSkin);
			} catch (RuntimeException e) {
				window.setButtonSkin(ThemeConstants.BUTTON_HELP, null);
			}
			themes.add(window);
			window.getShell().addDisposeListener(new DisposeListener() {

				public void widgetDisposed(DisposeEvent e) {
					unInstallTheme(window);
				}

			});
			window.layout();
		}
	}

	public static void unInstallTheme(IWindow window) {
		if (!themes.contains(window))
			return;
		themes.remove(window);
	}

	public static boolean isInstall(IWindow window) {
		return themes.contains(window);
	}

	public static void transformWindowsButtonLayoutData(ButtonCanvas canvse,
			String theme) {
		if (!themeProperties.containsKey(theme)) {
			Properties pros = loadThemeProperties(theme);
			if (pros != null) {
				themeProperties.put(theme, pros);
			}
		}
		Properties pros = (Properties)themeProperties.get(theme);
		if (pros != null) {
			try {
				String value = pros
						.getProperty("Button.LayoutData.VerticalIndent");
				GridData gd = (GridData) canvse.getLayoutData();
				int verticalIndent = Integer.parseInt(value);
				gd.verticalIndent = verticalIndent;
				canvse.setLayoutData(gd);
				canvse.getParent().layout();
			} catch (NumberFormatException e) {
			}
		}
	}

	private static Properties loadThemeProperties(String theme) {
		try {
			Properties props = new Properties();
			InputStream in = UIManager.class.getResourceAsStream("/icons/" + theme
					+ "/" + theme + ".properties");
			props.load(in);
			in.close();
			return props;
		} catch (Exception e) {
			SWT.error(SWT.ERROR_IO, e);
		}
		return null;
	}

	public static Color getTitleBarForeground(String theme) {
		if (!themeProperties.containsKey(theme)) {
			Properties pros = loadThemeProperties(theme);
			if (pros != null) {
				themeProperties.put(theme, pros);
			}
		}
		Properties pros = (Properties)themeProperties.get(theme);
		if (pros != null) {
			try {
				String value = pros.getProperty("TitlBar.Foreground");
				String[] rgbs = value.split(",");
				if (rgbs.length == 3) {
					int r = Integer.parseInt(rgbs[0]);
					int g = Integer.parseInt(rgbs[1]);
					int b = Integer.parseInt(rgbs[2]);
					return ColorCache.getInstance().getColor(r, g, b);
				}

			} catch (Exception e) {

			}
		}
		return ColorCache.getInstance().getBlack();
	}

	public static Color getTitleBarDeactiveBackground(String theme) {
		if (!themeProperties.containsKey(theme)) {
			Properties pros = loadThemeProperties(theme);
			if (pros != null) {
				themeProperties.put(theme, pros);
			}
		}
		Properties pros = (Properties)themeProperties.get(theme);
		if (pros != null) {
			try {
				String value = pros.getProperty("TitlBar.Foreground.Deactive");
				String[] rgbs = value.split(",");
				if (rgbs.length == 3) {
					int r = Integer.parseInt(rgbs[0]);
					int g = Integer.parseInt(rgbs[1]);
					int b = Integer.parseInt(rgbs[2]);
					return ColorCache.getInstance().getColor(r, g, b);
				}
			} catch (Exception e) {
			}
		}
		return ColorCache.getInstance().getWhite();
	}

	public static int getTitleBarFontStyle(String theme) {
		if (!themeProperties.containsKey(theme)) {
			Properties pros = loadThemeProperties(theme);
			if (pros != null) {
				themeProperties.put(theme, pros);
			}
		}
		Properties pros = (Properties)themeProperties.get(theme);
		int style = SWT.NONE;
		if (pros != null) {
			try {
				String value = pros.getProperty("TitlBar.Font.Style");
				if ("bold".equalsIgnoreCase(value))
					style |= SWT.BOLD;
				if ("italic".equalsIgnoreCase(value))
					style |= SWT.ITALIC;

			} catch (Exception e) {
			}
		}
		return style;
	}

	public static boolean isTitleUsedShadow(String theme) {
		if (!themeProperties.containsKey(theme)) {
			Properties pros = loadThemeProperties(theme);
			if (pros != null) {
				themeProperties.put(theme, pros);
			}
		}
		Properties pros = (Properties)themeProperties.get(theme);
		if (pros != null) {
			try {
				String value = pros.getProperty("TitlBar.Title.Shadow");
				return Boolean.valueOf(value).booleanValue();
			} catch (Exception e) {
			}
		}
		return false;
	}

	private static HashMap themeProperties = new HashMap();
}
