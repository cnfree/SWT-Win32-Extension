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
package org.sf.feeling.swt.win32.extension.util;

import java.io.File;
import java.util.Comparator;
import java.util.SortedSet;
import java.util.TreeSet;

import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.TextLayout;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;

public class UIUtil
{
	private static ImageLoader loader;

	/**
	 * Return the string display width, if the text draw on the control.
	 * 
	 * @param control
	 *            string showing control
	 * @param string
	 *            the string
	 * @return the string display width
	 */
	public static int GetStringWidth(Control control, String string)
	{
		if (control == null || string == null) return 0;
		GC gc = new GC(control);
		int width = gc.stringExtent(string).x;
		gc.dispose();
		return width;
	}

	/**
	 * Returns a point which is the result of converting the argument, which is
	 * specified in display relative coordinates, to coordinates relative to the
	 * control.
	 * 
	 * @param control
	 *            the control to be coordinated relative (must not be null)
	 * @param pointAtDisplay
	 *            the point to be translated (must not be null)
	 * @return the translated coordinates
	 * 
	 * @exception IllegalArgumentException
	 *                <ul>
	 *                <li>ERROR_NULL_ARGUMENT - if the point is null</li>
	 *                </ul>
	 * @exception SWTException
	 *                <ul>
	 *                <li>ERROR_WIDGET_DISPOSED - if the receiver has been
	 *                disposed</li>
	 *                <li>ERROR_THREAD_INVALID_ACCESS - if not called from the
	 *                thread that created the receiver</li>
	 *                </ul>
	 */
	public static Point GetPositionAtControl(Control control, Point pointAtDisplay)
	{
		if (control == null || pointAtDisplay == null)
		{
			SWT.error(SWT.ERROR_NULL_ARGUMENT);
			return null;
		}
		return control.toControl(pointAtDisplay);
	}

	/**
	 * Returns a point which is the result of converting the argument, which is
	 * specified in coordinates relative to the control, to display relative
	 * coordinates.
	 * 
	 * @param control
	 *            the control to be coordinated relative (must not be null)
	 * @param pointAtControl
	 *            the point to be translated (must not be null)
	 * @return the translated coordinates
	 * 
	 * @exception IllegalArgumentException
	 *                <ul>
	 *                <li>ERROR_NULL_ARGUMENT - if the point is null</li>
	 *                </ul>
	 * @exception SWTException
	 *                <ul>
	 *                <li>ERROR_WIDGET_DISPOSED - if the receiver has been
	 *                disposed</li>
	 *                <li>ERROR_THREAD_INVALID_ACCESS - if not called from the
	 *                thread that created the receiver</li>
	 *                </ul>
	 */
	public static Point GetPositionAtDisplay(Control control, Point pointAtControl)
	{
		if (control == null || pointAtControl == null)
		{
			SWT.error(SWT.ERROR_NULL_ARGUMENT);
			return null;
		}
		return control.toDisplay(pointAtControl);
	}

	/**
	 * Saves the image data in this ImageLoader to a file with the specified
	 * name. The format parameter can have one of the following values:
	 * <dl>
	 * <dt><code>IMAGE_BMP</code></dt>
	 * <dd>Windows BMP file format, no compression</dd>
	 * <dt><code>IMAGE_BMP_RLE</code></dt>
	 * <dd>Windows BMP file format, RLE compression if appropriate</dd>
	 * <dt><code>IMAGE_GIF</code></dt>
	 * <dd>GIF file format</dd>
	 * <dt><code>IMAGE_ICO</code></dt>
	 * <dd>Windows ICO file format</dd>
	 * <dt><code>IMAGE_JPEG</code></dt>
	 * <dd>JPEG file format</dd>
	 * <dt><code>IMAGE_PNG</code></dt>
	 * <dd>PNG file format</dd>
	 * </dl>
	 * 
	 * @param imageData
	 *            the image will be saved
	 * @param imageFile
	 *            the name of the file to write the images to
	 * @param format
	 *            the format to write the images in
	 * 
	 * @exception IllegalArgumentException
	 *                <ul>
	 *                <li>ERROR_NULL_ARGUMENT - if the file name is null</li>
	 *                </ul>
	 * @exception SWTException
	 *                <ul>
	 *                <li>ERROR_IO - if an IO error occurs while writing to the
	 *                file</li>
	 *                <li>ERROR_INVALID_IMAGE - if the image data contains
	 *                invalid data</li>
	 *                <li>ERROR_UNSUPPORTED_FORMAT - if the image data cannot
	 *                be saved to the requested format</li>
	 *                </ul>
	 */
	public static void SaveImage(ImageData imageData, File imageFile, int format)
	{
		if (loader == null) loader = new ImageLoader();
		loader.save(imageFile.getAbsolutePath(), format);
	}

	/**
	 * Returns all font names for current system. Give this method because AWT
	 * method will cause Vista's Aero to be disabled
	 * 
	 * NOTES: Java 1.4 only support true type fonts.
	 * 
	 * @return font names.
	 */
	public static String[] getSystemFontNames()
	{
		return getSystemFontNames(null);
	}

	/**
	 * Returns all font names for current system. NOTES: Java 1.4 only support
	 * true type fonts. Give this method because AWT method will cause Vista's
	 * Aero to be disabled
	 * 
	 * @param comparator
	 *            Sort comparator.
	 * @return font names.
	 */
	public static String[] getSystemFontNames(Comparator comparator)
	{
		SortedSet set = new TreeSet(comparator);
		FontData[] fontDatas = (FontData[]) Display.getCurrent().getFontList(null, false);
		for (int i = 0; i < fontDatas.length; i++)
		{
			set.add(fontDatas[i].getName());
		}
		fontDatas = (FontData[]) Display.getCurrent().getFontList(null, true);
		for (int i = 0; i < fontDatas.length; i++)
		{
			set.add(fontDatas[i].getName());
		}
		String[] fonts = new String[set.size()];
		set.toArray(fonts);
		return fonts;
	}

	/**
	 * Returns all font names for current system. NOTES: Java 1.4 only support
	 * true type fonts. Give this method because AWT method will cause Vista's
	 * Aero to be disabled
	 * 
	 * @param comparator
	 *            Sort comparator.
	 * @param trueType
	 *            if true type font
	 * @return font names.
	 */
	public static String[] getSystemFontNames(Comparator comparator, boolean trueType)
	{
		SortedSet set = new TreeSet(comparator);
		FontData[] fontDatas = (FontData[]) Display.getCurrent().getFontList(null, trueType);
		for (int i = 0; i < fontDatas.length; i++)
		{
			set.add(fontDatas[i].getName());
		}
		String[] fonts = new String[set.size()];
		set.toArray(fonts);
		return fonts;
	}

	/**
	 * Shorten the given text <code>t</code> so that its length doesn't exceed
	 * the given width. The default implementation replaces characters in the
	 * center of the original string with an ellipsis ("..."). Override if you
	 * need a different strategy.
	 * 
	 * @param gc
	 *            the gc to use for text measurement
	 * @param t
	 *            the text to shorten
	 * @param width
	 *            the width to shorten the text to, in pixels
	 * @return the shortened text
	 */
	public static String shortenText(GC gc, int drawFlags, String t, int width)
	{
		if (t == null) return null;
		int w = gc.textExtent(ELLIPSIS, drawFlags).x;
		if (width <= w) return t;
		int l = t.length();
		int max = l / 2;
		int min = 0;
		int mid = (max + min) / 2 - 1;
		if (mid <= 0) return t;
		TextLayout layout = new TextLayout(Display.getDefault());
		layout.setText(t);
		mid = validateOffset(layout, mid);
		while (min < mid && mid < max)
		{
			String s1 = t.substring(0, mid);
			String s2 = t.substring(validateOffset(layout, l - mid), l);
			int l1 = gc.textExtent(s1, drawFlags).x;
			int l2 = gc.textExtent(s2, drawFlags).x;
			if (l1 + w + l2 > width)
			{
				max = mid;
				mid = validateOffset(layout, (max + min) / 2);
			}
			else if (l1 + w + l2 < width)
			{
				min = mid;
				mid = validateOffset(layout, (max + min) / 2);
			}
			else
			{
				min = max;
			}
		}
		String result = mid == 0 ? t : t.substring(0, mid) + ELLIPSIS
				+ t.substring(validateOffset(layout, l - mid), l);
		layout.dispose();
		return result;
	}

	private static final String ELLIPSIS = "...";

	private static int validateOffset(TextLayout layout, int offset)
	{
		int nextOffset = layout.getNextOffset(offset, SWT.MOVEMENT_CLUSTER);
		if (nextOffset != offset) return layout.getPreviousOffset(nextOffset,
				SWT.MOVEMENT_CLUSTER);
		return offset;
	}
}
