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

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

public class ImageCache {

	private static HashMap mImageMap;

	private static List mExternallyCreatedCache;

	static {
		mImageMap = new HashMap();
		mExternallyCreatedCache = new ArrayList();
	}

	/**
	 * Returns an image that is also cached if it has to be created and does not
	 * already exist in the cache.
	 * 
	 * @param fileName
	 *            Filename of image to fetch
	 * @return Image file or null if it could not be found
	 */
	public static Image getImage(String fileName) {
		Image image = (Image) mImageMap.get(fileName);
		if (image == null) {
			image = createImage(fileName);
			mImageMap.put(fileName, image);
		}
		return image;
	}

	// creates the image, and tries really hard to do so
	private static Image createImage(String fileName) {
		ClassLoader classLoader = ImageCache.class.getClassLoader();
		InputStream is = classLoader.getResourceAsStream(fileName);
		if (is == null) {
			// the old way didn't have leading slash, so if we can't find the
			// image stream,
			// let's see if the old way works.
			is = classLoader.getResourceAsStream(fileName.substring(1));

			if (is == null) {
				is = classLoader.getResourceAsStream(fileName);
				if (is == null) {
					is = classLoader.getResourceAsStream(fileName.substring(1));
					if (is == null) {
						// logger.debug("null input stream for both " + path + "
						// and " + path);
						return null;
					}
				}
			}
		}

		Image img = new Image(Display.getDefault(), is);
		try {
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return img;
	}

	/**
	 * Disposes ALL images that have been cached.
	 * 
	 */
	public static void dispose() {
		Iterator e = mImageMap.values().iterator();
		while (e.hasNext()) {
			Object obj = e.next();
			if (obj != null)
				((Image) obj).dispose();
		}
	}

	/**
	 * Disposes a specific image and removes it from the image cache.
	 * 
	 * @param img
	 *            Image to dispose
	 */
	public static void dispose(Image img) {
		if (img == null)
			return;

		Iterator set = mImageMap.entrySet().iterator();
		while (set.hasNext()) {
			Entry entry = (Entry) set.next();
			if (entry.getValue() == img) {
				mImageMap.remove(entry.getKey());
				if (entry.getValue() != null)
					((Image) entry.getValue()).dispose();
				return;
			}
		}

		// not in the hash, dispose it if it's in the externally cached list
		if (mExternallyCreatedCache.contains(img))
			mExternallyCreatedCache.remove(img);

		// dispose
		if (!img.isDisposed())
			img.dispose();
	}

	public static void ensureImageIsCached(Image img) {
		// check local cache first, it might have been an image created by us
		Collection cachedImages = mImageMap.values();
		if (cachedImages.contains(img))
			return;

		// add it to the external cache so we can dispose it if asked
		if (!mExternallyCreatedCache.contains(img))
			mExternallyCreatedCache.add(img);
	}

	static {
		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				dispose();
			}
		});
	}

}
