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

package org.sf.feeling.swt.win32.internal.extension.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;

public class ColorCache {

	public static final RGB BLACK = new RGB(0, 0, 0);

	public static final RGB WHITE = new RGB(255, 255, 255);

	private static Map colorTable;

	private static ColorCache instance;

	/**
	 * Disposes all colors held in the cache.
	 * <p>
	 * <b>IMPORTANT: ONLY CALL WHEN YOU WANT TO DISPOSE ALL CACHED COLORS!</b>
	 * 
	 */
	public static void disposeAll() {
		getInstance().dispose();
		instance = null;
		colorTable = null;
	}

	private ColorCache() {
		if (colorTable == null) {
			colorTable = new HashMap();
		}
	}

	/**
	 * Returns the active instance of this class.
	 * 
	 * @return ColorCache instance
	 */
	public static ColorCache getInstance() {
		if (instance == null) {
			instance = new ColorCache();
		}

		return instance;
	}

	// see disposeAll();
	private void dispose() {
		Iterator e = colorTable.values().iterator();
		while (e.hasNext()) {
			Object obj = e.next();
			if (obj != null)
				((Color) obj).dispose();
		}
		colorTable.clear();
	}

	/**
	 * Returns the color white R255, G255, B255
	 * 
	 * @return White color
	 */
	public Color getWhite() {
		return getColor(new RGB(255, 255, 255));
	}

	/**
	 * Returns the color black R0, G0, B0
	 * 
	 * @return Black color
	 */
	public Color getBlack() {
		return getColor(new RGB(0, 0, 0));
	}

	/**
	 * Returns a color that is also cached if it has not been created before.
	 * 
	 * @param rgb
	 *            RGB colors
	 * @return Color
	 */
	public Color getColor(RGB rgb) {
		Color color = (Color) colorTable.get(rgb);

		if (color == null) {
			color = new Color(Display.getCurrent(), rgb);
			colorTable.put(rgb, color);
		}

		return color;
	}

	/**
	 * Returns a color that is also cached if it has not been created before.
	 * 
	 * @param r
	 *            Red
	 * @param g
	 *            Green
	 * @param b
	 *            Blue
	 * @return Color
	 */
	public Color getColor(int r, int g, int b) {

		RGB rgb = new RGB(r, g, b);
		Color color = (Color) colorTable.get(rgb);

		if (color == null) {
			color = new Color(Display.getCurrent(), rgb);
			colorTable.put(rgb, color);
		}

		return color;
	}

	static {
		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				disposeAll();
			}
		});
	}
}
