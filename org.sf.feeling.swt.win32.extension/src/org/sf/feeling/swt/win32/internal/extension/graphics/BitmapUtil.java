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

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.internal.win32.BITMAP;
import org.eclipse.swt.internal.win32.BITMAPINFOHEADER;
import org.eclipse.swt.internal.win32.ICONINFO;
import org.sf.feeling.swt.win32.internal.extension.Extension;

public class BitmapUtil {
	public static int /* long */create32bitDIB(Image image) {
		int transparentPixel = -1, alpha = -1;
		int /* long */hMask = 0, hBitmap = 0;
		byte[] alphaData = null;
		switch (image.type) {
		case SWT.ICON:
			ICONINFO info = new ICONINFO();
			Extension.GetIconInfo(image.handle, info);
			hBitmap = info.hbmColor;
			hMask = info.hbmMask;
			break;
		case SWT.BITMAP:
			ImageData data = image.getImageData();
			hBitmap = image.handle;
			alpha = data.alpha;
			alphaData = data.alphaData;
			transparentPixel = data.transparentPixel;
			break;
		}
		BITMAP bm = new BITMAP();
		Extension.GetObject(hBitmap, BITMAP.sizeof, bm);
		int imgWidth = bm.bmWidth;
		int imgHeight = bm.bmHeight;
		int /* long */hDC = Extension.GetDC(0);
		int /* long */srcHdc = Extension.CreateCompatibleDC(hDC);
		int /* long */oldSrcBitmap = Extension.SelectObject(srcHdc, hBitmap);
		int /* long */memHdc = Extension.CreateCompatibleDC(hDC);
		BITMAPINFOHEADER bmiHeader = new BITMAPINFOHEADER();
		bmiHeader.biSize = BITMAPINFOHEADER.sizeof;
		bmiHeader.biWidth = imgWidth;
		bmiHeader.biHeight = -imgHeight;
		bmiHeader.biPlanes = 1;
		bmiHeader.biBitCount = (short) 32;
		bmiHeader.biCompression = Extension.BI_RGB;
		byte[] bmi = new byte[BITMAPINFOHEADER.sizeof];
		Extension.MoveMemory(bmi, bmiHeader, BITMAPINFOHEADER.sizeof);
		int /* long */[] pBits = new int /* long */[1];
		int /* long */memDib = Extension.CreateDIBSection(0, bmi, Extension.DIB_RGB_COLORS,
				pBits, 0, 0);
		if (memDib == 0)
			SWT.error(SWT.ERROR_NO_HANDLES);
		int /* long */oldMemBitmap = Extension.SelectObject(memHdc, memDib);
		BITMAP dibBM = new BITMAP();
		Extension.GetObject(memDib, BITMAP.sizeof, dibBM);
		int sizeInBytes = dibBM.bmWidthBytes * dibBM.bmHeight;
		Extension.BitBlt(memHdc, 0, 0, imgWidth, imgHeight, srcHdc, 0, 0, Extension.SRCCOPY);
		byte red = 0, green = 0, blue = 0;
		if (transparentPixel != -1) {
			if (bm.bmBitsPixel <= 8) {
				byte[] color = new byte[4];
				Extension.GetDIBColorTable(srcHdc, transparentPixel, 1, color);
				blue = color[0];
				green = color[1];
				red = color[2];
			} else {
				switch (bm.bmBitsPixel) {
				case 16:
					blue = (byte) ((transparentPixel & 0x1F) << 3);
					green = (byte) ((transparentPixel & 0x3E0) >> 2);
					red = (byte) ((transparentPixel & 0x7C00) >> 7);
					break;
				case 24:
					blue = (byte) ((transparentPixel & 0xFF0000) >> 16);
					green = (byte) ((transparentPixel & 0xFF00) >> 8);
					red = (byte) (transparentPixel & 0xFF);
					break;
				case 32:
					blue = (byte) ((transparentPixel & 0xFF000000) >>> 24);
					green = (byte) ((transparentPixel & 0xFF0000) >> 16);
					red = (byte) ((transparentPixel & 0xFF00) >> 8);
					break;
				}
			}
		}
		byte[] srcData = new byte[sizeInBytes];
		Extension.MoveMemory(srcData, pBits[0], sizeInBytes);
		if (hMask != 0) {
			Extension.SelectObject(srcHdc, hMask);
			for (int y = 0, dp = 0; y < imgHeight; ++y) {
				for (int x = 0; x < imgWidth; ++x) {
					if (Extension.GetPixel(srcHdc, x, y) != 0) {
						srcData[dp + 0] = srcData[dp + 1] = srcData[dp + 2] = srcData[dp + 3] = (byte) 0;
					} else {
						srcData[dp + 3] = (byte) 0xFF;
					}
					dp += 4;
				}
			}
		} else if (alpha != -1) {
			for (int y = 0, dp = 0; y < imgHeight; ++y) {
				for (int x = 0; x < imgWidth; ++x) {
					srcData[dp + 3] = (byte) alpha;
					if (srcData[dp + 3] == 0)
						srcData[dp + 0] = srcData[dp + 1] = srcData[dp + 2] = 0;
					dp += 4;
				}
			}
		} else if (alphaData != null) {
			for (int y = 0, dp = 0, ap = 0; y < imgHeight; ++y) {
				for (int x = 0; x < imgWidth; ++x) {
					srcData[dp + 3] = alphaData[ap++];
					if (srcData[dp + 3] == 0)
						srcData[dp + 0] = srcData[dp + 1] = srcData[dp + 2] = 0;
					dp += 4;
				}
			}
		} else if (transparentPixel != -1) {
			for (int y = 0, dp = 0; y < imgHeight; ++y) {
				for (int x = 0; x < imgWidth; ++x) {
					if (srcData[dp] == blue && srcData[dp + 1] == green
							&& srcData[dp + 2] == red) {
						srcData[dp + 0] = srcData[dp + 1] = srcData[dp + 2] = srcData[dp + 3] = (byte) 0;
					} else {
						srcData[dp + 3] = (byte) 0xFF;
					}
					dp += 4;
				}
			}
		} else {
			for (int y = 0, dp = 0; y < imgHeight; ++y) {
				for (int x = 0; x < imgWidth; ++x) {
					srcData[dp + 3] = (byte) 0xFF;
					dp += 4;
				}
			}
		}
		Extension.MoveMemory(pBits[0], srcData, sizeInBytes);
		Extension.SelectObject(srcHdc, oldSrcBitmap);
		Extension.SelectObject(memHdc, oldMemBitmap);
		Extension.DeleteObject(srcHdc);
		Extension.DeleteObject(memHdc);
		Extension.ReleaseDC(0, hDC);
		if (hBitmap != image.handle && hBitmap != 0)
			Extension.DeleteObject(hBitmap);
		if (hMask != 0)
			Extension.DeleteObject(hMask);
		return memDib;
	}
}
