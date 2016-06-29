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

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Region;

/**
 * This class is a utility class, and provides a static method to calculate the
 * control graphic path by the image region. An image region represents some
 * structured portions or pieces of an Image.
 * 
 * @author <a href="mailto:cnfree2000@hotmail.com">cnfree</a>
 */
public class ImageRegion {

	/**
	 * Calculate the graphics path that representing the figure in the image,
	 * excluding the transparent color.
	 * 
	 * @param image
	 *            The image to calculate graphics path.
	 * @param transparentPixel
	 *            The pixel which you want to exclude from the figure. If
	 *            transparentPixel<=0, will use image default transparent
	 *            pixel.
	 * @return Calculated graphics path
	 */
	public static Region calculateControlGraphicsPath(Image image,
			int transparentPixel) {
		return calculateControlGraphicsPath(image.getImageData(),
				transparentPixel);
	}

	/**
	 * Calculate the graphics path that representing the figure in the image,
	 * excluding the transparent color.
	 * 
	 * @param image
	 *            The image to calculate graphics path.
	 * @param transparentPixel
	 *            The pixel which you want to exclude from the figure. If
	 *            transparentPixel<=0, will use image default transparent
	 *            pixel.
	 * @param offsetX
	 *            specify an offset x coordinate.
	 * @param offsetY
	 *            specify an offset y coordinate.
	 * @return Calculated graphics path
	 */
	public static Region calculateControlGraphicsPath(Image image,
			int transparentPixel, int offsetX, int offsetY) {
		return calculateControlGraphicsPath(image.getImageData(),
				transparentPixel, offsetX, offsetY);
	}

	/**
	 * Calculate the graphics path that representing the figure in the image
	 * data, excluding the transparent color.
	 * 
	 * @param data
	 *            The image data to calculate graphics path.
	 * @param transparentPixel
	 *            The pixel which you want to exclude from the figure. If
	 *            transparentPixel<=0, will use image default transparent
	 *            pixel.
	 * @return Calculated graphics path
	 */
	public static Region calculateControlGraphicsPath(ImageData data,
			int transparentPixel) {
		return calculateControlGraphicsPath(data, transparentPixel, 0, 0);
	}

	/**
	 * Calculate the graphics path that representing the figure in the image
	 * data, excluding the transparent color.
	 * 
	 * @param data
	 *            The image data to calculate graphics path.
	 * @param transparentPixel
	 *            The pixel which you want to exclude from the figure. If
	 *            transparentPixel<=0, will use image default transparent
	 *            pixel.
	 * @param offsetX
	 *            specify an offset x coordinate.
	 * @param offsetY
	 *            specify an offset y coordinate.
	 * @return Calculated graphics path
	 */
	public static Region calculateControlGraphicsPath(ImageData data,
			int transparentPixel, int offsetX, int offsetY) {
		Region region = new Region();
		if (data == null)
			return region;
		int colorTransparentPixel;
		// Use the top left pixel as our transparent color
		if (transparentPixel <= 0) {
			if (data.transparentPixel == -1)
				colorTransparentPixel = data.getPixel(0, 0);
			else
				colorTransparentPixel = data.transparentPixel;
		} else
			colorTransparentPixel = transparentPixel;

		// This is to store the column value where an opaque pixel is first
		// found.This value will determine where we start scanning for trailing
		// opaque pixels.
		int colOpaquePixel = 0;

		int width = data.width;
		int height = data.height;

		// Go through all rows (Y axis)
		for (int row = 0; row < height; row++) {
			// Reset value
			colOpaquePixel = 0;

			// Go through all columns (X axis)
			for (int col = 0; col < width; col++) {
				// If this is an opaque pixel, mark it and search for anymore
				// trailing behind
				if (data.getPixel(col, row) != colorTransparentPixel) {
					// Opaque pixel found, mark current position
					colOpaquePixel = col;

					// Create another variable to set the current pixel position
					int colNext = col;

					// Starting from current found opaque pixel, search for
					// anymore opaque pixels trailing behind, until a
					// transparent pixel is found or minimum width is reached
					for (colNext = colOpaquePixel; colNext < width; colNext++)
						if (data.getPixel(colNext, row) == colorTransparentPixel)
							break;

					// Form a rectangle for line of opaque pixels found and add
					// // it to our graphics path
					region.add(new int[] { offsetX + colOpaquePixel,
							offsetY + row, offsetX + colNext, offsetY + row,
							offsetX + colNext, offsetY + row + 1,
							offsetX + colOpaquePixel, offsetY + row + 1 });

					// No need to scan the line of opaque pixels just found
					col = colNext;
				}
			}
		}

		// Return calculated graphics path
		return region;
	}

	/**
	 * Calculate the graphics path that representing image opaque area path.
	 * 
	 * @param image
	 *            The image to calculate graphics path.
	 * @param transparentPixel
	 *            The pixel which you want to exclude from the figure. If
	 *            transparentPixel<=0, will use image default transparent
	 *            pixel.
	 * @return Calculated graphics path
	 */
	public static Region calculateOpaquePath(Image image, int transparentPixel) {
		return calculateOpaquePath(image.getImageData(), transparentPixel);
	}

	public static Region calculateOpaquePath(Image image, int transparentPixel,
			int offsetX, int offsetY) {
		return calculateOpaquePath(image.getImageData(), transparentPixel,
				offsetX, offsetY);
	}

	/**
	 * Calculate the graphics path that representing image opaque area path.
	 * 
	 * @param data
	 *            The image data to calculate graphics path.
	 * @param transparentPixel
	 *            The pixel which you want to exclude from the figure. If
	 *            transparentPixel<=0, will use image default transparent
	 *            pixel.
	 * @param offsetX
	 *            specify an offset x coordinate.
	 * @param offsetY
	 *            specify an offset y coordinate.
	 * @return Calculated graphics path
	 */
	public static Region calculateOpaquePath(ImageData data,
			int transparentPixel, int offsetX, int offsetY) {
		Region region = new Region();
		region.add(new int[] { offsetX, offsetY, offsetX + data.width, offsetY,
				offsetX + data.width, data.height + offsetY, offsetX,
				data.height + offsetY });
		Region graphicsRegion = calculateControlGraphicsPath(data, transparentPixel,
				offsetX, offsetY);
		region.subtract(graphicsRegion);
		graphicsRegion.dispose();
		return region;
	}

	/**
	 * Calculate the graphics path that representing image opaque area path.
	 * 
	 * @param data
	 *            The image data to calculate graphics path.
	 * @param transparentPixel
	 *            The pixel which you want to exclude from the figure. If
	 *            transparentPixel<=0, will use image default transparent
	 *            pixel.
	 * @return Calculated graphics path
	 */
	public static Region calculateOpaquePath(ImageData data,
			int transparentPixel) {
		return calculateOpaquePath(data, transparentPixel, 0, 0);
	}
}
