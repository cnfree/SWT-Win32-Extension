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
package org.sf.feeling.swt.win32.internal.extension.graphics;

import java.lang.reflect.Field;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.GCData;
import org.eclipse.swt.graphics.Path;
import org.eclipse.swt.internal.gdip.Gdip;
import org.eclipse.swt.internal.gdip.PointF;
import org.sf.feeling.swt.win32.internal.extension.Extension;

public class GCExtension {

	private GC gc;

	private GCData data;

	public GCExtension(GC gc) {
		this.gc = gc;
		data = getGCData(gc);
	}

	public void fillGradientPath(Path path, float[] centerPoint,
			Color centerColor, int centerColorAlpha, Color[] surroundColors,
			int[] surroundColorAlphas) {
		if (gc == null || gc.handle == 0)
			SWT.error(SWT.ERROR_GRAPHIC_DISPOSED);
		if (path == null || centerPoint == null || centerColor == null
				|| surroundColorAlphas == null)
			SWT.error(SWT.ERROR_NULL_ARGUMENT);
		if (path.handle == 0 || centerPoint.length < 2
				|| centerColor.handle == 0
				|| surroundColors.length != surroundColorAlphas.length)
			SWT.error(SWT.ERROR_INVALID_ARGUMENT);
		for (int i = 0; i < surroundColors.length; i++) {
			if (surroundColors[i] == null || surroundColors[i].handle == 0)
				SWT.error(SWT.ERROR_INVALID_ARGUMENT);
		}

		int brush = Gdip.PathGradientBrush_new(path.handle);
		if (brush == 0)
			SWT.error(SWT.ERROR_NO_HANDLES);
		PointF point = new PointF();
		point.X = centerPoint[0];
		point.Y = centerPoint[1];
		Gdip.PathGradientBrush_SetCenterPoint(brush, point);

		int colorRef = centerColor.handle;
		int rgb = ((colorRef >> 16) & 0xFF) | (colorRef & 0xFF00)
				| ((colorRef & 0xFF) << 16);
		int color = Gdip.Color_new(centerColorAlpha << 24 | rgb);
		if (color == 0)
			SWT.error(SWT.ERROR_NO_HANDLES);
		Gdip.PathGradientBrush_SetCenterColor(brush, color);
		Gdip.Color_delete(color);

		int[] colors = new int[surroundColors.length];
		for (int i = 0; i < surroundColors.length; i++) {
			colorRef = surroundColors[i].handle;
			rgb = ((colorRef >> 16) & 0xFF) | (colorRef & 0xFF00)
					| ((colorRef & 0xFF) << 16);
			colors[i] = Gdip.Color_new(surroundColorAlphas[i] << 24 | rgb);
			if (colors[i] == 0)
				SWT.error(SWT.ERROR_NO_HANDLES);
		}
		Gdip.PathGradientBrush_SetSurroundColors(brush, colors,
				new int[] { colors.length });
		for (int i = 0; i < surroundColors.length; i++) {
			Gdip.Color_delete(colors[i]);
		}
		boolean advanced = gc.getAdvanced();
		if (!advanced)
			gc.setAdvanced(true);
		int mode = Extension.GetPolyFillMode(gc.handle) == Extension.WINDING ? Gdip.FillModeWinding
				: Gdip.FillModeAlternate;
		Gdip.GraphicsPath_SetFillMode(path.handle, mode);
		Gdip.Graphics_FillPath(data.gdipGraphics, brush, path.handle);
		if (!advanced)
			gc.setAdvanced(false);
		Gdip.PathGradientBrush_delete(brush);
	}

	private GCData getGCData(GC gc) {
		GCData data = null;
		try {
			Object obj = null;
			Field field = gc.getClass().getDeclaredField("data");
			if (field != null) {
				field.setAccessible(true);
				obj = field.get(gc);
			}
			if (obj != null && obj instanceof GCData)
				data = (GCData) obj;
		} catch (Exception e) {
			SWT.error(SWT.ERROR_UNSPECIFIED, e);
		}
		return data;
	}
}
