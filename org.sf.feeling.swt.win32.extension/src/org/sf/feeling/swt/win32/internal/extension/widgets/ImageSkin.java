package org.sf.feeling.swt.win32.internal.extension.widgets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Event;

/*******************************************************************************
 * Copyright (c) 2007 cnfree. All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse
 * Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: cnfree - initial API and implementation
 ******************************************************************************/
public class ImageSkin implements ISkinable {

	private Device device;

	private Image image;

	private Image mouseHover;

	private Image activeImage;

	private Image mouseDown;

	private Image keyDown;

	public ImageSkin(Device device, Image image) {
		if (device == null) {
			throw new IllegalArgumentException("Device is null");
		}
		if (device.isDisposed()) {
			throw new IllegalArgumentException("Device is disposed");
		}
		if (image == null) {
			throw new IllegalArgumentException("Image is null");
		}
		this.device = device;
		this.image = image;
		this.activeImage = image;
		this.mouseHover = image;
		this.mouseDown = image;
		this.keyDown = image;
		this.currentImage = image;
	}

	public Color getForeground(Event event) {
		switch (event.type) {
		case SWT.Deactivate:
			return device.getSystemColor(SWT.COLOR_WHITE);
		default:
			return device.getSystemColor(SWT.COLOR_BLACK);
		}
	}

	public int getInitHeight() {
		return image.getImageData().height;
	}

	public int getInitWidth() {
		return image.getImageData().width;
	}

	public Image getActiveImage() {
		return activeImage;
	}

	public void setActiveImage(Image image) {
		if (image == null) {
			throw new IllegalArgumentException("Image is null");
		}
		this.activeImage = image;
	}

	public Image getMouseHover() {
		return mouseHover;
	}

	public void setMouseHover(Image image) {
		if (image == null) {
			throw new IllegalArgumentException("Image is null");
		}
		this.mouseHover = image;
	}

	public Image getKeyDown() {
		return keyDown;
	}

	public void setKeyDown(Image image) {
		if (image == null) {
			throw new IllegalArgumentException("Image is null");
		}
		this.keyDown = image;
	}

	public Image getMouseDown() {
		return mouseDown;
	}

	public void setMouseDown(Image image) {
		if (image == null) {
			throw new IllegalArgumentException("Image is null");
		}
		this.mouseDown = image;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		if (image == null) {
			throw new IllegalArgumentException("Image is null");
		}
		this.image = image;
	}

	private Image currentImage;

	public void configue(Event event) {

		switch (event.type) {
		case SWT.Activate:
			currentImage = activeImage;
			break;
		case SWT.MouseEnter:
			currentImage = mouseHover;
			break;
		case SWT.MouseDown:
			currentImage = mouseDown;
			break;
		case SWT.MouseUp:
			currentImage = mouseHover;
			break;
		case SWT.KeyDown:
			currentImage = keyDown;
			break;
		default:
			currentImage = image;
			break;
		}
	}

	public void paint(GC gc, int x, int y, int width, int height) {
		if (currentImage != null) {
			gc.drawImage(currentImage, 0, 0, currentImage.getImageData().width,
					currentImage.getImageData().height, x, y, width, height);
		}
	}

	public void paint(GC gc, int x, int y) {
		if (currentImage != null) {
			gc.drawImage(currentImage, x, y);
		}
	}
}
