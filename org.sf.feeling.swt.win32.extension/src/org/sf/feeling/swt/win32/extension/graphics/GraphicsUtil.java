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
package org.sf.feeling.swt.win32.extension.graphics;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Path;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Rectangle;
import org.sf.feeling.swt.win32.internal.extension.Extension;

public class GraphicsUtil {
	public static RGB calculateColor(Color front, Color back, int alpha) {
		float frontRed = front.getRed();
		float frontGreen = front.getGreen();
		float frontBlue = front.getBlue();
		float backRed = back.getRed();
		float backGreen = back.getGreen();
		float backBlue = back.getBlue();

		float fRed = (frontRed * alpha / 255) + backRed
				* ((float) (255 - alpha) / 255);
		float fGreen = (frontGreen * alpha / 255) + backGreen
				* ((float) (255 - alpha) / 255);
		float fBlue = (frontBlue * alpha / 255) + backBlue
				* ((float) (255 - alpha) / 255);

		int newRed = (int) fRed;
		int newGreen = (int) fGreen;
		int newBlue = (int) fBlue;

		return new RGB(newRed, newGreen, newBlue);
	}

	public static RGB calculateColor(RGB front, RGB back, int alpha) {
		float frontRed = front.red;
		float frontGreen = front.green;
		float frontBlue = front.blue;
		float backRed = back.red;
		float backGreen = back.green;
		float backBlue = back.blue;

		float fRed = (frontRed * alpha / 255) + backRed
				* ((float) (255 - alpha) / 255);
		float fGreen = (frontGreen * alpha / 255) + backGreen
				* ((float) (255 - alpha) / 255);
		float fBlue = (frontBlue * alpha / 255) + backBlue
				* ((float) (255 - alpha) / 255);

		int newRed = (int) fRed;
		int newGreen = (int) fGreen;
		int newBlue = (int) fBlue;

		return new RGB(newRed, newGreen, newBlue);
	}

	public static RGB mergeColors(Color color1, float percent1, Color color2,
			float percent2, Color color3, float percent3) {
		int red = (int) ((color1.getRed() * percent1)
				+ (color2.getRed() * percent2) + (color3.getRed() * percent3));
		int green = (int) ((color1.getGreen() * percent1)
				+ (color2.getGreen() * percent2) + (color3.getGreen() * percent3));
		int blue = (int) ((color1.getBlue() * percent1)
				+ (color2.getBlue() * percent2) + (color3.getBlue() * percent3));

		// Limit check against individual component
		if (red < 0)
			red = 0;
		if (red > 255)
			red = 255;
		if (green < 0)
			green = 0;
		if (green > 255)
			green = 255;
		if (blue < 0)
			blue = 0;
		if (blue > 255)
			blue = 255;

		return new RGB(red, green, blue);
	}

	public static RGB mergeColors(RGB color1, float percent1, RGB color2,
			float percent2, RGB color3, float percent3) {
		int red = (int) ((color1.red * percent1) + (color2.red * percent2) + (color3.red * percent3));
		int green = (int) ((color1.green * percent1)
				+ (color2.green * percent2) + (color3.green * percent3));
		int blue = (int) ((color1.blue * percent1) + (color2.blue * percent2) + (color3.blue * percent3));

		// Limit check against individual component
		if (red < 0)
			red = 0;
		if (red > 255)
			red = 255;
		if (green < 0)
			green = 0;
		if (green > 255)
			green = 255;
		if (blue < 0)
			blue = 0;
		if (blue > 255)
			blue = 255;

		return new RGB(red, green, blue);
	}

	public static RGB mergeColors(Color color1, float percent1, Color color2,
			float percent2) {
		return mergeColors(color1, percent1, color2, percent2, new Color(null,
				0, 0, 0), 0f);
	}

	public static RGB mergeColors(RGB color1, float percent1, RGB color2,
			float percent2) {
		return mergeColors(color1, percent1, color2, percent2,
				new RGB(0, 0, 0), 0f);
	}

	public static Path createRoundPath(Rectangle rect, float cut) {
		Path path = new Path(null);

		path.moveTo(rect.x + cut, (float) rect.y);
		path.lineTo(rect.x + rect.width - cut, (float) rect.y);

		path.lineTo(rect.x + rect.width - cut, (float) rect.y);
		path.lineTo((float) rect.x + rect.width, rect.y + cut);

		path.lineTo((float) rect.x + rect.width, rect.y + cut);
		path.lineTo((float) rect.x + rect.width, rect.y + rect.height - cut);

		path.lineTo((float) rect.x + rect.width, rect.y + rect.height - cut);
		path.lineTo(rect.x + rect.width - cut, (float) rect.y + rect.height);

		path.lineTo(rect.x + rect.width - cut, (float) rect.y + rect.height);
		path.lineTo(rect.x + cut, (float) rect.y + rect.height);

		path.lineTo(rect.x + cut, (float) rect.y + rect.height);
		path.lineTo((float) rect.x, rect.y + rect.height - cut);

		path.lineTo((float) rect.x, rect.y + rect.height - cut);
		path.lineTo((float) rect.x, rect.y + cut);

		path.lineTo((float) rect.x, rect.y + cut);
		path.lineTo(rect.x + cut, (float) rect.y);

		return path;
	}

	public static Path createRoundRectangle(Rectangle rectangle, int radius) {
		Path path = new Path(null);
		int l = rectangle.x;
		int t = rectangle.y;
		int w = rectangle.width;
		int h = rectangle.height;
		int d = radius << 1;

		path.addArc(l, t, d, d, -180, -90);
		path.lineTo(l + radius, t);
		path.lineTo(l + w - radius, t);
		path.addArc(l + w - d, t, d, d, -270, -90);
		path.lineTo(l + w, t + radius);
		path.lineTo(l + w, t + h - radius);
		path.addArc(l + w - d, t + h - d, d, d, 0, -90);
		path.lineTo(l + w - radius, t + h);
		path.lineTo(l + radius, t + h); // top
		path.addArc(l, t + h - d, d, d, -90, -90);
		path.lineTo(l, t + h - radius);
		path.lineTo(l, t + radius);
		path.close();

		return path;
	}

	public static Path createTopRoundRectangle(Rectangle rectangle, int radius) {
		Path path = new Path(null);
		int l = rectangle.x;
		int t = rectangle.y;
		int w = rectangle.width;
		int h = rectangle.height;
		int d = radius << 1;

		path.addArc(l, t, d, d, -180, -90); // topleft
		path.lineTo(l + radius, t);
		path.lineTo(l + w - radius, t);
		path.addArc(l + w - d, t, d, d, -270, -90); // topright
		path.lineTo(l + w, t + radius);
		path.lineTo(l + w, t + h);
		path.lineTo(l, t + h);
		path.lineTo(l, t + radius);
		path.close();

		return path;
	}

	public static Path createBottomRoundRectangle(Rectangle rectangle,
			int radius) {
		Path path = new Path(null);
		int l = rectangle.x;
		int t = rectangle.y;
		int w = rectangle.width;
		int h = rectangle.height;
		int d = radius << 1;

		path.moveTo(l, t);
		path.lineTo(l + w, t);
		path.lineTo(l + w, t + h - radius);
		path.addArc(l + w - d, t + h - d, d, d, 0, -90);
		path.lineTo(l + w - radius, t + h);
		path.lineTo(l + radius, t + h);
		path.addArc(l, t + h - d, d, d, -90, -90);
		path.lineTo(l, t + h - radius);
		path.lineTo(l, t);
		path.close();

		return path;
	}

	public static Rectangle inflate(Rectangle rect, int width, int height) {
		Rectangle rect1 = new Rectangle(rect.x, rect.y, rect.width, rect.height);
		rect1.x -= width;
		rect1.y -= height;
		rect1.width += 2 * width;
		rect1.height += 2 * height;
		return rect1;
	}

	private static boolean gdip = true;
	static {
		gdip = Extension.LoadLibrary("gdiplus.dll") != 0;
	}

	public static boolean checkGdip() {
		return gdip;
	}

	public static Rectangle createRectangleFromLTRB(int left, int top,
			int right, int bottom) {
		return new Rectangle(left, top, right - left, bottom - top);
	}

	public static Path createEllipsePath(float[] bounds) {
		float l = bounds[0];
		float t = bounds[1];
		float w = bounds[2];
		float h = bounds[3];
		float d = w;
		float g = h;

		Path path = new Path(null);

		path.addArc(l, t, d, g, -180, -90);
		path.addArc(l + w - d, t, d, g, -270, -90);
		path.addArc(l + w - d, t + h - g, d, g, 0, -90);
		path.addArc(l, t + h - g, d, g, -90, -90);
		path.close();
		return path;
	}

	public static void drawArrow(GC gc, Rectangle rect, int style) {
		Point point = new Point(rect.x + (rect.width / 2), rect.y
				+ (rect.height / 2));
		int[] points = null;
		switch (style) {
		case SWT.LEFT:
			points = new int[] { point.x + 2, point.y - 4, point.x + 2,
					point.y + 4, point.x - 2, point.y };
			gc.fillPolygon(points);
			break;

		/*
		 * Low efficiency because of Win98 bug.
		 */
		case SWT.UP:
			gc.fillRectangle(new Rectangle(point.x, point.y - 1, 1, 1));
			gc.fillRectangle(new Rectangle(point.x - 1, point.y, 3, 1));
			gc.fillRectangle(new Rectangle(point.x - 2, point.y + 1, 5, 1));
			break;

		case SWT.RIGHT:
			points = new int[] { point.x - 2, point.y - 4, point.x - 2,
					point.y + 4, point.x + 2, point.y };
			gc.fillPolygon(points);
			break;

		/*
		 * Low efficiency because of Win98 bug.
		 */
		default:
			gc.fillRectangle(new Rectangle(point.x - 2, point.y - 1, 5, 1));
			gc.fillRectangle(new Rectangle(point.x - 1, point.y, 3, 1));
			gc.fillRectangle(new Rectangle(point.x, point.y + 1, 1, 1));
			break;
		}

	}
}
