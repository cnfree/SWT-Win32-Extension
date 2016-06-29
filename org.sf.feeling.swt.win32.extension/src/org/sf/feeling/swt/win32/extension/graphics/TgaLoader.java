/*******************************************************************************
 * Copyright (c) 2010 cnfree.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  cnfree  - initial API and implementation
 *******************************************************************************/
package org.sf.feeling.swt.win32.extension.graphics;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.PaletteData;
import org.eclipse.swt.graphics.RGB;

public final class TgaLoader {

	/**
	 * 0 - no image data in file
	 */
	public static final int TYPE_NO_IMAGE = 0;

	/**
	 * 1 - uncompressed, color-mapped image
	 */
	public static final int TYPE_COLORMAPPED = 1;

	/**
	 * 2 - uncompressed, true-color image
	 */
	public static final int TYPE_TRUECOLOR = 2;

	/**
	 * 3 - uncompressed, black and white image
	 */
	public static final int TYPE_BLACKANDWHITE = 3;

	/**
	 * 9 - run-length encoded, color-mapped image
	 */
	public static final int TYPE_COLORMAPPED_RLE = 9;

	/**
	 * 10 - run-length encoded, true-color image
	 */
	public static final int TYPE_TRUECOLOR_RLE = 10;

	/**
	 * 11 - run-length encoded, black and white image
	 */
	public static final int TYPE_BLACKANDWHITE_RLE = 11;

	// private to enforce use of static methods.
	private TgaLoader() {
	}

	/**
	 * <code>loadImage</code> is a manual image loader for the TGA image.
	 * 
	 * @param fis
	 *            InputStream of an uncompressed 24b RGB or 32b RGBA TGA
	 * @return <code>org.eclipse.swt.graphics.Image</code> object that contains
	 *         the image
	 */
	public static ImageData loadImage(InputStream fis) throws IOException {
		return loadImage(fis, true);
	}

	/**
	 * <code>loadImage</code> is a manual image loader for the TGA image.
	 * 
	 * @param fis
	 *            InputStream of an uncompressed 24b RGB or 32b RGBA TGA
	 * @param transparent
	 *            Set to true to make the image transparent.
	 * @return <code>org.eclipse.swt.graphics.Image</code> object that contains
	 *         the image
	 * @throws java.io.IOException
	 */
	public static ImageData loadImage(InputStream fis, boolean transparent)
			throws IOException {
		return loadImage(fis, false, transparent);
	}

	/**
	 * <code>loadImage</code> is a manual image loader for the TGA image.
	 * 
	 * @return <code>org.eclipse.swt.graphics.Image</code> object that contains
	 *         the image
	 * @param fis
	 *            InputStream of an uncompressed TGA.
	 * @param exp32
	 *            Add a dummy Alpha channel to 24b RGB image.
	 * @param transparent
	 *            Set to true to make the image transparent.
	 * 
	 * @throws java.io.IOException
	 */
	public static ImageData loadImage(InputStream fis, boolean exp32,
			boolean transparent) throws IOException {
		// open a stream to the file
		BufferedInputStream bis = new BufferedInputStream(fis, 8192);
		DataInputStream dis = new DataInputStream(bis);

		// ---------- Start Reading the TGA header ---------- //
		// length of the image id (1 byte)
		int idLength = dis.readUnsignedByte();

		// Type of color map (if any) included with the image
		// 0 - no color map data is included
		// 1 - a color map is included
		int colorMapType = dis.readUnsignedByte();

		// Type of image being read:
		int imageType = dis.readUnsignedByte();
		if (imageType != TYPE_COLORMAPPED && imageType != TYPE_COLORMAPPED_RLE
				&& imageType != TYPE_TRUECOLOR
				&& imageType != TYPE_TRUECOLOR_RLE) {
			throw new IOException("Unsupported TGA type: " + imageType);
		}

		/*
		 * Read Color Map Specification (5 bytes) Index of first color map entry
		 * (if we want to use it, uncomment and remove extra read.)
		 */
		dis.readShort();
		// number of entries in the color map
		short cMapLength = flipEndian(dis.readShort());
		// number of bits per color map entry
		int cMapDepth = dis.readUnsignedByte();

		/*
		 * Read Image Specification (10 bytes) horizontal coordinate of lower
		 * left corner of image. (if we want to use it, uncomment and remove
		 * extra read.)
		 */
		dis.readShort();
		/*
		 * vertical coordinate of lower left corner of image. (if we want to use
		 * it, uncomment and remove extra read.)
		 */
		dis.readShort();
		// width of image - in pixels
		int width = flipEndian(dis.readShort());
		// height of image - in pixels
		int height = flipEndian(dis.readShort());
		// bits per pixel in image.
		int pixelDepth = dis.readUnsignedByte();

		boolean flip = false;
		int imageDescriptor = dis.readUnsignedByte();
		if ((imageDescriptor & 32) != 0) // bit 5 : if 1, flip top/bottom
			flip = true;

		// ---------- Done Reading the TGA header ---------- //

		// Skip image ID
		if (idLength > 0)
			bis.skip(idLength);

		ColorMapEntry[] cMapEntries = null;
		if (colorMapType != 0) {
			// read the color map.
			int bytesInColorMap = (cMapDepth * cMapLength) >> 3;
			int bitsPerColor = Math.min(cMapDepth / 3, 8); // one of 2, 3, 4

			byte[] cMapData = new byte[bytesInColorMap];
			bis.read(cMapData);

			// Only go to the trouble of constructing the color map
			// table if this is declared a color mapped image.
			if (imageType == TYPE_COLORMAPPED
					|| imageType == TYPE_COLORMAPPED_RLE) {
				cMapEntries = new ColorMapEntry[cMapLength];
				int alphaSize = cMapDepth - (3 * bitsPerColor);
				float scalar = 255f / ((float) (Math.pow(2, bitsPerColor)) - 1);
				float alphaScalar = 255f / ((float) (Math.pow(2, alphaSize)) - 1);
				for (int i = 0; i < cMapLength; i++) {
					ColorMapEntry entry = new ColorMapEntry();
					int offset = cMapDepth * i;
					entry.blue = (byte) (int) (getBitsAsByte(cMapData, offset,
							bitsPerColor) * scalar);
					entry.green = (byte) (int) (getBitsAsByte(cMapData, offset
							+ bitsPerColor, bitsPerColor) * scalar);
					entry.red = (byte) (int) (getBitsAsByte(cMapData, offset
							+ (2 * bitsPerColor), bitsPerColor) * scalar);
					if (alphaSize <= 0)
						entry.alpha = (byte) 255;
					else
						entry.alpha = (byte) (int) (getBitsAsByte(cMapData,
								offset + (3 * bitsPerColor), alphaSize) * alphaScalar);

					cMapEntries[i] = entry;
				}
			}
		}

		// Allocate image data array
		byte[] rawData = null;
		int dl;
		if ((pixelDepth == 32) || (exp32)) {
			rawData = new byte[width * height * 4];
			dl = 4;
		} else {
			rawData = new byte[width * height * 3];
			dl = 3;
		}

		byte data_16[] = null;
		if (pixelDepth == 16)
			data_16 = new byte[width * height * 2];

		int rawDataIndex = 0;

		if (imageType == TYPE_TRUECOLOR) {
			byte red = 0;
			byte green = 0;
			byte blue = 0;
			byte alpha = 0;

			/*
			 * Faster than doing a 16-or-24-or-32 check on each individual
			 * pixel, just make a separate loop for each.
			 */
			if (pixelDepth == 16) {
				float scalar = 255f / 31f;
				byte data[] = new byte[2];
				for (int i = 0; i < height; i++) {
					if (!flip)
						rawDataIndex = (height - 1 - i) * width * dl;
					for (int j = 0; j < width; j++) {
						data[1] = dis.readByte();
						data[0] = dis.readByte();
						data_16[rawDataIndex * 2 / dl + 1] = data[1];
						data_16[rawDataIndex * 2 / dl] = data[0];
						rawData[rawDataIndex++] = (byte) (int) (getBitsAsByte(
								data, 1, 5) * scalar); // r:1-5
						rawData[rawDataIndex++] = (byte) (int) (getBitsAsByte(
								data, 6, 5) * scalar); // g:6-10
						rawData[rawDataIndex++] = (byte) (int) (getBitsAsByte(
								data, 11, 5) * scalar); // b:11-15
						if (dl == 4) {
							// create an alpha channel
							alpha = getBitsAsByte(data, 0, 1);
							if (alpha == 1)
								alpha = (byte) 255;
							rawData[rawDataIndex++] = alpha;
						}
					}
				}
			} else if (pixelDepth == 24 || pixelDepth == 32) {
				for (int i = 0; i < height; i++) {
					if (!flip)
						rawDataIndex = (height - 1 - i) * width * dl;
					for (int j = 0; j < width; j++) {
						blue = dis.readByte();
						green = dis.readByte();
						red = dis.readByte();
						rawData[rawDataIndex++] = red;
						rawData[rawDataIndex++] = green;
						rawData[rawDataIndex++] = blue;
						if (pixelDepth == 32) {
							alpha = dis.readByte();
							rawData[rawDataIndex++] = alpha;
						} else if (dl == 4) {
							// create an alpha channel
							rawData[rawDataIndex++] = (byte) 255;
						}

					}
				}
			} else
				throw new IOException("Unsupported TGA true color depth: "
						+ pixelDepth);

		} else if (imageType == TYPE_TRUECOLOR_RLE) {
			int bytesPerIndex = pixelDepth / 8;
			if (bytesPerIndex < 2 || bytesPerIndex > 4) {
				throw new IOException(
						"TGA: unknown colormap indexing size used: "
								+ bytesPerIndex);
			}
			byte[][] colors = new byte[width * height][bytesPerIndex];
			int count = 0;
			while (count != colors.length) {
				int size = dis.readByte();
				if ((size & 0x80) != 0) {
					size &= 0x07f;
					byte[] index = new byte[bytesPerIndex];
					for (int i = 0; i < bytesPerIndex; i++) {
						index[i] = dis.readByte();
					}
					while (size-- >= 0) {
						colors[count++] = index;
					}
				} else {
					while (size-- >= 0) {
						for (int i = 0; i < bytesPerIndex; i++) {
							colors[count][i] = dis.readByte();
						}
						count++;
					}
				}
			}

			/*
			 * Faster than doing a 16-or-24-or-32 check on each individual
			 * pixel, just make a separate loop for each.
			 */
			if (pixelDepth == 32 || pixelDepth == 24) {
				for (int i = 0; i < height; i++) {
					if (!flip) {
						rawDataIndex = (height - 1 - i) * width * dl;
					}
					for (int j = 0; j < width; j++) {
						rawData[rawDataIndex++] = colors[i * width + j][2];
						rawData[rawDataIndex++] = colors[i * width + j][1];
						rawData[rawDataIndex++] = colors[i * width + j][0];
						if (pixelDepth == 32) {
							rawData[rawDataIndex++] = colors[i * width + j][3];
						} else if (dl == 4) {
							rawData[rawDataIndex++] = (byte) 255;
						}
					}
				}
			} else if (pixelDepth == 16) {
				float scalar = 255f / 31f;
				byte data[] = new byte[2];
				for (int i = 0; i < height; i++) {
					if (!flip) {
						rawDataIndex = (height - 1 - i) * width * dl;
					}
					for (int j = 0; j < width; j++) {
						data[1] = colors[i * width + j][0];
						data[0] = colors[i * width + j][1];
						data_16[rawDataIndex * 2 / dl + 1] = data[1];
						data_16[rawDataIndex * 2 / dl] = data[0];
						rawData[rawDataIndex++] = (byte) (int) (getBitsAsByte(
								data, 11, 5) * scalar); // red
						rawData[rawDataIndex++] = (byte) (int) (getBitsAsByte(
								data, 6, 5) * scalar);// green
						rawData[rawDataIndex++] = (byte) (int) (getBitsAsByte(
								data, 1, 5) * scalar);// blue
						if (dl == 4) {
							rawData[rawDataIndex++] = (byte) 255;
						}
					}
				}
			} else {
				throw new IOException("Unsupported TGA true color depth: "
						+ pixelDepth);
			}
		} else if (imageType == TYPE_COLORMAPPED) {
			int bytesPerIndex = pixelDepth / 8;
			for (int i = 0; i < height; i++) {
				if (!flip)
					rawDataIndex = (height - 1 - i) * width * dl;
				for (int j = 0; j < width; j++) {
					int index;
					if (bytesPerIndex == 1) {
						index = dis.readUnsignedByte();
					} else if (bytesPerIndex == 2) {
						index = flipEndian(dis.readShort());
					} else {
						throw new IOException(
								"TGA: unknown colormap indexing size used: "
										+ bytesPerIndex);
					}
					if (index >= cMapEntries.length || index < 0)
						throw new IOException(
								"TGA: Invalid color map entry referenced: "
										+ index);
					ColorMapEntry entry = cMapEntries[index];
					rawData[rawDataIndex++] = entry.red;
					rawData[rawDataIndex++] = entry.green;
					rawData[rawDataIndex++] = entry.blue;
					if (dl == 4) {
						rawData[rawDataIndex++] = entry.alpha;
					}
				}
			}
		} else if (imageType == TYPE_COLORMAPPED_RLE) {
			int bytesPerIndex = pixelDepth / 8;
			int[] indexes = new int[width * height];
			int count = 0;
			while (count != indexes.length) {
				int size = dis.readByte();
				if ((size & 0x80) != 0) {
					size &= 0x07f;
					int index;
					if (bytesPerIndex == 1) {
						index = dis.readUnsignedByte();
					} else if (bytesPerIndex == 2) {
						index = flipEndian(dis.readShort());
					} else {
						throw new IOException(
								"TGA: unknown colormap indexing size used: "
										+ bytesPerIndex);
					}
					if (index >= cMapEntries.length || index < 0)
						throw new IOException(
								"TGA: Invalid color map entry referenced: "
										+ indexes[count]);
					while (size-- >= 0) {
						indexes[count++] = index;
					}
				} else {
					while (size-- >= 0) {
						if (bytesPerIndex == 1) {
							indexes[count] = dis.readUnsignedByte();
						} else if (bytesPerIndex == 2) {
							indexes[count] = flipEndian(dis.readShort());
						} else {
							throw new IOException(
									"TGA: unknown colormap indexing size used: "
											+ bytesPerIndex);
						}
						if (indexes[count] >= cMapEntries.length
								|| indexes[count] < 0)
							throw new IOException(
									"TGA: Invalid color map entry referenced: "
											+ indexes[count]);
						count++;
					}
				}
			}
			for (int i = 0; i < height; i++) {
				if (!flip) {
					rawDataIndex = (height - 1 - i) * width * dl;
				}
				for (int j = 0; j < width; j++) {
					ColorMapEntry entry = cMapEntries[indexes[i * width + j]];
					rawData[rawDataIndex++] = entry.red;
					rawData[rawDataIndex++] = entry.green;
					rawData[rawDataIndex++] = entry.blue;
					if (dl == 4) {
						rawData[rawDataIndex++] = entry.alpha;
					}
				}
			}
		}
		fis.close();

		int red, green, blue;
		PaletteData palette = loadPalette(cMapEntries, pixelDepth);
		if (dl == 4) {
			int pixel[] = new int[width * height];
			int alpha[] = new int[width * height];
			int z = 0;
			for (int i = 0; i != width; i++)
				for (int j = 0; j != height; j++) {
					if (data_16 != null) {
						pixel[z] = ((data_16[(j + i * height) * 2 + 1] & 0xFF) << 0)
								| ((data_16[(j + i * height) * 2] & 0xFF) << 8);
					} else {
						red = rawData[(j + i * height) * 4];
						green = rawData[(j + i * height) * 4 + 1];
						blue = rawData[(j + i * height) * 4 + 2];
						RGB rgb = new RGB(red & 0xFF, green & 0xFF, blue & 0xFF);
						pixel[z] = palette.getPixel(rgb);
					}
					alpha[z] = (rawData[(j + i * height) * 4 + 3] & 0xFF);
					z++;
				}

			ImageData data = new ImageData(width, height, pixelDepth, palette);
			for (int y = 0; y < data.height; y++) {
				for (int x = 0; x < data.width; x++) {
					data.setPixel(x, y, pixel[y * width + x]);
					if (transparent)
						data.setAlpha(x, y, alpha[y * width + x]);
				}
			}
			return data;
		} else {
			int pixel[] = new int[width * height];
			int z = 0;
			for (int i = 0; i != width; i++) {
				for (int j = 0; j != height; j++) {
					if (data_16 != null) {
						pixel[z++] = ((data_16[(j + i * height) * 2 + 1] & 0xFF) << 0)
								| ((data_16[(j + i * height) * 2] & 0xFF) << 8);
					} else {
						red = rawData[(j + i * height) * 3];
						green = rawData[(j + i * height) * 3 + 1];
						blue = rawData[(j + i * height) * 3 + 2];
						RGB rgb = new RGB(red & 0xFF, green & 0xFF, blue & 0xFF);
						pixel[z++] = palette.getPixel(rgb);
					}
				}
			}
			ImageData data = new ImageData(width, height, pixelDepth, palette);
			for (int y = 0; y < data.height; y++) {
				for (int x = 0; x < data.width; x++) {
					data.setPixel(x, y, pixel[y * width + x]);
				}
			}
			return data;
		}
	}

	/**
	 * Saves the image data to an output stream, and the data format is TGA.
	 * 
	 * @param fos
	 *            The data output stream will be wrote.
	 * @param image
	 *            The image data will be saved
	 * @throws IOException
	 */
	public static void saveImage(OutputStream fos, ImageData image)
			throws IOException {
		saveImage(fos, image, false, false, true);
	}

	/**
	 * Saves the image data to an output stream, and the data format is TGA.
	 * 
	 * @param fos
	 *            The data output stream will be wrote.
	 * @param image
	 *            The image data will be saved.
	 * @param compress
	 *            Use the RLE compress model.
	 * @throws IOException
	 */
	public static void saveImage(OutputStream fos, ImageData image,
			boolean compress) throws IOException {
		saveImage(fos, image, false, false, compress);
	}

	/**
	 * Saves the image data to an output stream, and the data format is TGA.
	 * 
	 * @param fos
	 *            The data output stream will be wrote.
	 * @param image
	 *            The image data will be saved
	 * @param vflip
	 *            Flip the image vertically, if vfilp is true, the origin in
	 *            upper left-hand corner. Must be false for Truevision images.
	 * @param exp32
	 *            Save as a 32 bit image.
	 * @param compress
	 *            Use the RLE compress model.
	 * @throws IOException
	 */
	public static void saveImage(OutputStream fos, ImageData image,
			boolean vflip, boolean exp32, boolean compress) throws IOException {

		int dl;
		if ((image.depth == 32) || (exp32)) {
			dl = 4;
		} else {
			if (image.getTransparencyType() == SWT.TRANSPARENCY_NONE) {
				dl = 3;
			} else {
				dl = 4;
			}
		}

		boolean useColorMap = (dl == 3) && !image.palette.isDirect;

		BufferedOutputStream bos = new BufferedOutputStream(fos, 8192);
		DataOutputStream dos = new DataOutputStream(bos);

		// length of the image id (1 byte)
		dos.writeByte(0);

		// Type of color map (if any) included with the image
		// 0 - no color map data is included
		// 1 - a color map is included
		if (useColorMap)
			dos.writeByte(1);
		else
			dos.writeByte(0);

		// Type of image
		if (useColorMap) {
			if (compress) {
				dos.writeByte(TYPE_COLORMAPPED_RLE);
			} else {
				dos.writeByte(TYPE_COLORMAPPED);
			}
		} else {
			if (compress) {
				dos.writeByte(TYPE_TRUECOLOR_RLE);
			} else {
				dos.writeByte(TYPE_TRUECOLOR);
			}
		}

		// Color Map Specification (5 bytes)
		if (useColorMap) {
			/*
			 * Index of first color map entry (if we want to use it, uncomment
			 * and remove extra read.)
			 */
			dos.writeShort(0);
			// number of entries in the color map
			dos.writeShort(flipEndian((short) image.palette.getRGBs().length));
			// number of bits per color map entry
			dos.writeByte((short) 24);
		} else {
			dos.writeShort(0);
			dos.writeShort(0);
			dos.writeByte(0);
		}

		/*
		 * Image Specification (10 bytes) horizontal coordinate of lower left
		 * corner of image. (if we want to use it, uncomment and remove extra
		 * read.)
		 */
		dos.writeShort(0);
		/*
		 * vertical coordinate of lower left corner of image. (if we want to use
		 * it, uncomment and remove extra read.) int yOffset =
		 * flipEndian(dis.readShort());
		 */
		dos.writeShort(0);

		// width of image - in pixels
		dos.writeShort(flipEndian((short) image.width));

		// height of image - in pixels
		dos.writeShort(flipEndian((short) image.height));

		// bits per pixel in image.
		if (dl == 4) {
			dos.writeByte(32);
		} else {
			dos.writeByte(image.depth);
		}

		int imageDescriptor = 0;
		if (dl == 4) {
			imageDescriptor = 8; // 32 bit
		} else {
			if (useColorMap)
				imageDescriptor = 0; // color map must be 0
			else if (image.depth == 24)
				imageDescriptor = 0;
			else if (image.depth == 16)
				imageDescriptor = 1;
		}
		if (vflip && !useColorMap) {
			imageDescriptor |= 32;
		}
		dos.writeByte(imageDescriptor);

		boolean flip = false;
		if ((imageDescriptor & 32) != 0) // bit 5 : if 1, flip top/bottom
			flip = true;

		// write color map
		if (useColorMap) {
			for (int i = 0; i < image.palette.getRGBs().length; i++) {
				RGB rgb = image.palette.getRGBs()[i];
				dos.writeByte((byte) rgb.blue);
				dos.writeByte((byte) rgb.green);
				dos.writeByte((byte) rgb.red);
			}
		}

		byte[] rawData = null;
		if (dl == 4) {
			rawData = new byte[image.width * image.height * 4];
		} else if (dl == 3) {
			rawData = new byte[image.width * image.height * 3];
		}

		int height = image.height;
		int width = image.width;
		int rawDataIndex = 0;

		if (dl == 3) {
			for (int i = 0; i < height; i++) {
				if (!flip)
					rawDataIndex = (height - 1 - i) * width * dl;
				for (int j = 0; j < width; j++) {
					int pixelValue = image.getPixel(j, i);
					RGB rgb = image.palette.getRGB(pixelValue);
					rawData[rawDataIndex++] = (byte) rgb.red; // red
					rawData[rawDataIndex++] = (byte) rgb.green; // green
					rawData[rawDataIndex++] = (byte) rgb.blue; // blue
				}
			}
			if (!compress) {
				if (useColorMap) {
					int bytesPerIndex = image.depth / 8;
					List rgbs = Arrays.asList(image.palette.getRGBs());
					for (int i = 0; i < rawData.length;) {
						RGB rgb = new RGB(rawData[i] & 0xFF,
								rawData[i + 1] & 0xFF, rawData[i + 2] & 0xFF);
						int index = rgbs.indexOf(rgb);
						if (index > -1) {
							// 8 bit
							if (bytesPerIndex == 1) {
								dos.writeByte(index);
							}
							// 16 bit
							else if (bytesPerIndex == 2) {
								dos.writeShort(flipEndian((short) index));
							} else {
								throw new IOException(
										"TGA: unknown colormap indexing size used: "
												+ bytesPerIndex);
							}
						} else {
							throw new IOException(
									"TGA: Invalid color map entry referenced: "
											+ index);
						}
						i = i + 3;
					}
				} else {
					if (image.depth == 24) {
						for (int i = 0; i < rawData.length;) {
							dos.writeByte(rawData[i + 2]); // blue
							dos.writeByte(rawData[i + 1]); // green
							dos.writeByte(rawData[i]); // red
							i = i + 3;
						}
					} else if (image.depth == 16) {
						for (int i = 0; i < rawData.length;) {
							RGB rgb = new RGB((rawData[i] & 0xFF),
									(rawData[i + 1] & 0xFF),
									(rawData[i + 2] & 0xFF));
							int pixel = image.palette.getPixel(rgb);
							dos.writeByte((byte) (pixel & 0xFF));
							dos.writeByte((byte) ((pixel >> 8) & 0xFF));
							i = i + 3;
						}
					}
				}
			} else {
				CompressColor[] colors = new CompressColor[rawData.length / 3];
				for (int i = 0; i < rawData.length / 3; i++) {
					if (i < rawData.length / 3 - 1) {
						colors[i] = new CompressColor(new int[] {
								rawData[i * 3], rawData[i * 3 + 1],
								rawData[i * 3 + 2] }, new int[] {
								rawData[(i + 1) * 3], rawData[(i + 1) * 3 + 1],
								rawData[(i + 1) * 3 + 2] }, i);
					} else if (i == rawData.length / 3 - 1) {
						colors[i] = new CompressColor(new int[] {
								rawData[i * 3], rawData[i * 3 + 1],
								rawData[i * 3 + 2] }, null, i);
					}
				}
				List compressColorMap = getCompressColorMap(colors, image.width);
				if (useColorMap) {
					int bytesPerIndex = image.depth / 8;
					List rgbs = Arrays.asList(image.palette.getRGBs());
					for (int i = 0; i < compressColorMap.size(); i++) {
						CompressColor[] compressColors = (CompressColor[]) compressColorMap
								.get(i);
						if (equalColor(compressColors[0].self,
								compressColors[0].right)) {
							dos.writeByte(1 << 7 & 0xfe
									| (compressColors.length - 1));
							RGB rgb = new RGB(
									rawData[compressColors[0].index * 3] & 0xFF,
									rawData[compressColors[0].index * 3 + 1] & 0xFF,
									rawData[compressColors[0].index * 3 + 2] & 0xFF);
							int index = rgbs.indexOf(rgb);
							if (index > -1) {
								// 8 bit
								if (bytesPerIndex == 1) {
									dos.writeByte(index);
								}
								// 16 bit
								else if (bytesPerIndex == 2) {
									dos.writeShort(flipEndian((short) index));
								} else {
									throw new IOException(
											"TGA: unknown colormap indexing size used: "
													+ bytesPerIndex);
								}
							} else {
								throw new IOException(
										"TGA: Invalid color map entry referenced: "
												+ index);
							}
						} else {
							dos.writeByte(0 << 7 & 0xfe
									| (compressColors.length - 1));
							for (int j = 0; j < compressColors.length; j++) {
								RGB rgb = new RGB(
										rawData[compressColors[j].index * 3] & 0xFF,
										rawData[compressColors[j].index * 3 + 1] & 0xFF,
										rawData[compressColors[j].index * 3 + 2] & 0xFF);
								int index = rgbs.indexOf(rgb);
								if (index > -1) {
									// 8 bit
									if (bytesPerIndex == 1) {
										dos.writeByte(index);
									}
									// 16 bit
									else if (bytesPerIndex == 2) {
										dos
												.writeShort(flipEndian((short) index));
									} else {
										throw new IOException(
												"TGA: unknown colormap indexing size used: "
														+ bytesPerIndex);
									}
								} else {
									throw new IOException(
											"TGA: Invalid color map entry referenced: "
													+ index);
								}
							}
						}
					}
				} else {
					for (int i = 0; i < compressColorMap.size(); i++) {
						CompressColor[] compressColors = (CompressColor[]) compressColorMap
								.get(i);
						if (equalColor(compressColors[0].self,
								compressColors[0].right)) {
							dos.writeByte(1 << 7 & 0xfe
									| (compressColors.length - 1));
							if (image.depth == 24) {
								dos.writeByte(compressColors[0].self[2]); // blue
								dos.writeByte(compressColors[0].self[1]); // green
								dos.writeByte(compressColors[0].self[0]); // red
							} else if (image.depth == 16) {
								RGB rgb = new RGB(
										(compressColors[0].self[0] & 0xFF),
										(compressColors[0].self[1] & 0xFF),
										(compressColors[0].self[2] & 0xFF));
								int pixel = image.palette.getPixel(rgb);
								dos.writeByte((byte) (pixel & 0xFF));
								dos.writeByte((byte) ((pixel >> 8) & 0xFF));
							}
						} else {
							dos.writeByte(0 << 7 & 0xfe
									| (compressColors.length - 1));
							for (int j = 0; j < compressColors.length; j++) {
								if (image.depth == 24) {
									dos.writeByte(compressColors[j].self[2]); // blue
									dos.writeByte(compressColors[j].self[1]); // green
									dos.writeByte(compressColors[j].self[0]); // red
								} else if (image.depth == 16) {
									RGB rgb = new RGB(
											(compressColors[j].self[0] & 0xFF),
											(compressColors[j].self[1] & 0xFF),
											(compressColors[j].self[2] & 0xFF));
									int pixel = image.palette.getPixel(rgb);
									dos.writeByte((byte) (pixel & 0xFF));
									dos.writeByte((byte) ((pixel >> 8) & 0xFF));
								}
							}
						}
					}
				}
			}
		} else if (dl == 4) {
			ImageData srcMask = null;
			int alphaMask = 0, alphaShift = 0;
			if (image.maskData != null) {
				srcMask = image.getTransparencyMask();
				alphaMask = ~(image.palette.redMask | image.palette.greenMask | image.palette.blueMask);
				while (alphaMask != 0 && ((alphaMask >>> alphaShift) & 1) == 0)
					alphaShift++;
			}
			for (int i = 0; i < height; i++) {
				if (!flip)
					rawDataIndex = (height - 1 - i) * width * dl;
				for (int j = 0; j < width; j++) {
					int pixelValue = image.getPixel(j, i);
					RGB rgb = image.palette.getRGB(pixelValue);
					rawData[rawDataIndex++] = (byte) rgb.red; // red
					rawData[rawDataIndex++] = (byte) rgb.green; // green
					rawData[rawDataIndex++] = (byte) rgb.blue; // blue

					// compute transparent pixel
					int srcAlpha = 255;
					if (image.maskData != null) {
						if (image.depth == 32) {
							srcAlpha = (pixelValue & alphaMask) >>> alphaShift;
							if (srcAlpha == 0) {
								srcAlpha = srcMask.getPixel(j, i) != 0 ? 255
										: 0;
							}
						} else {
							if (srcMask.getPixel(j, i) == 0)
								srcAlpha = 0;
						}
					} else if (image.transparentPixel != -1) {
						if (image.transparentPixel == pixelValue)
							srcAlpha = 0;
					} else if (image.alpha != -1) {
						srcAlpha = image.alpha;
					} else if (image.alphaData != null) {
						srcAlpha = image.getAlpha(j, i);
					}
					rawData[rawDataIndex++] = (byte) srcAlpha;
				}
			}
			if (!compress) {
				for (int i = 0; i < rawData.length;) {
					dos.writeByte(rawData[i + 2]); // blue
					dos.writeByte(rawData[i + 1]); // green
					dos.writeByte(rawData[i]); // red
					dos.writeByte(rawData[i + 3]); // alpha
					i = i + 4;
				}
			} else {
				CompressColor[] colors = new CompressColor[rawData.length / 4];
				for (int i = 0; i < rawData.length / 4; i++) {
					if (i < rawData.length / 4 - 1) {
						colors[i] = new CompressColor(new int[] {
								rawData[i * 4], rawData[i * 4 + 1],
								rawData[i * 4 + 2], rawData[i * 4 + 3] },
								new int[] { rawData[(i + 1) * 4],
										rawData[(i + 1) * 4 + 1],
										rawData[(i + 1) * 4 + 2],
										rawData[(i + 1) * 4 + 3] }, i);
					} else if (i == rawData.length / 4 - 1) {
						colors[i] = new CompressColor(new int[] {
								rawData[i * 4], rawData[i * 4 + 1],
								rawData[i * 4 + 2], rawData[i * 4 + 3] }, null,
								i);
					}
				}
				List compressColorMap = getCompressColorMap(colors, image.width);
				for (int i = 0; i < compressColorMap.size(); i++) {
					CompressColor[] compressColors = (CompressColor[]) compressColorMap
							.get(i);
					if (equalColor(compressColors[0].self,
							compressColors[0].right)) {
						dos.writeByte(1 << 7 & 0xfe
								| (compressColors.length - 1));
						dos.writeByte(compressColors[0].self[2]); // blue
						dos.writeByte(compressColors[0].self[1]); // green
						dos.writeByte(compressColors[0].self[0]); // red
						dos.writeByte(compressColors[0].self[3]); // alpha
					} else {
						dos.writeByte(0 << 7 & 0xfe
								| (compressColors.length - 1));
						for (int j = 0; j < compressColors.length; j++) {
							dos.writeByte(compressColors[j].self[2]); // blue
							dos.writeByte(compressColors[j].self[1]); // green
							dos.writeByte(compressColors[j].self[0]); // red
							dos.writeByte(compressColors[j].self[3]); // alpha
						}
					}
				}
			}
		}
		dos.close();
	}

	private static List getCompressColorMap(CompressColor[] colors, int width) {
		List list = new ArrayList();
		List chunk = new ArrayList();
		int type = -1;
		int count = 0;
		for (int i = 0; i < colors.length; i++) {
			CompressColor color = colors[i];
			if (type == -1) {
				if (equalColor(color.self, color.right)) {
					type = 1;
				} else {
					type = 0;
				}
				chunk.add(color);
				count++;
				if (count == width) {
					type = -1;
					list.add((CompressColor[]) chunk
							.toArray(new CompressColor[0]));
					chunk.clear();
					count = 0;
				}
			} else if (type == 0) {
				chunk.add(color);
				count++;
				if (equalColor(color.self, color.right) || chunk.size() == 128
						|| count == width) {
					type = -1;
					list.add((CompressColor[]) chunk
							.toArray(new CompressColor[0]));
					chunk.clear();
					if (count == width)
						count = 0;
				}
			} else if (type == 1) {
				chunk.add(color);
				count++;
				if (!equalColor(color.self, color.right) || chunk.size() == 128
						|| count == width) {
					type = -1;
					list.add((CompressColor[]) chunk
							.toArray(new CompressColor[0]));
					chunk.clear();
					if (count == width)
						count = 0;
				}
			}
		}
		return list;
	}

	private static boolean equalColor(int[] color1, int[] color2) {
		if (color1 == null && color2 != null)
			return false;
		else if (color1 != null && color2 == null)
			return false;
		else if (color1 != null && color2 != null) {
			if (color1.length != color2.length)
				return false;
			else {
				for (int i = 0; i < color1.length; i++) {
					if (color1[i] != color2[i])
						return false;
				}
			}
		}
		return true;
	}

	private static byte getBitsAsByte(byte[] data, int offset, int length) {
		int offsetBytes = offset / 8;
		int indexBits = offset % 8;
		int rVal = 0;

		// start at data[offsetBytes]... spill into next byte as needed.
		for (int i = length; --i >= 0;) {
			byte b = data[offsetBytes];
			int test = indexBits == 7 ? 1 : 2 << (6 - indexBits);
			if ((b & test) != 0) {
				if (i == 0)
					rVal++;
				else
					rVal += (2 << i - 1);
			}
			indexBits++;
			if (indexBits == 8) {
				indexBits = 0;
				offsetBytes++;
			}
		}

		return (byte) rVal;
	}

	/**
	 * <code>flipEndian</code> is used to flip the endian bit of the header
	 * file.
	 * 
	 * @param signedShort
	 *            the bit to flip.
	 * @return the flipped bit.
	 */
	private static short flipEndian(short signedShort) {
		int input = signedShort & 0xFFFF;
		return (short) (input << 8 | (input & 0xFF00) >>> 8);
	}

	static class ColorMapEntry {
		byte red, green, blue, alpha;

		public String toString() {
			return "entry: " + red + "," + green + "," + blue + "," + alpha;
		}
	}

	static class CompressColor {
		int[] self, right;
		int index;

		public CompressColor(int[] self, int[] right, int index) {
			this.self = self;
			this.right = right;
			this.index = index;
		}
	}

	private static PaletteData loadPalette(ColorMapEntry[] mapEntries, int depth)
			throws IOException {
		if (mapEntries != null && mapEntries.length > 0) {
			int numColors = mapEntries.length;
			if (numColors == 0) {
				numColors = 1 << depth;
			}
			return paletteFromBytes(mapEntries, numColors);
		}
		// if doesn't define the color map
		if (depth < 16) {
			throw new IOException("Unsupported TGA color depth: " + depth);
		} else if (depth == 16) {
			return new PaletteData(0x7C00, 0x3E0, 0x1F);
		} else
			return new PaletteData(0xFF, 0xFF00, 0xFF0000);
	}

	private static PaletteData paletteFromBytes(ColorMapEntry[] mapEntries,
			int numColors) {
		RGB[] colors = new RGB[numColors];
		for (int i = 0; i < numColors; i++) {
			colors[i] = new RGB(mapEntries[i].red & 0xFF,
					mapEntries[i].green & 0xFF, mapEntries[i].blue & 0xFF);
		}
		return new PaletteData(colors);
	}

}