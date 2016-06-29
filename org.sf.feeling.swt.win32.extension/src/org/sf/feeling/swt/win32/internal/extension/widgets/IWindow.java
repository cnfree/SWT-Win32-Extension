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

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public interface IWindow extends IContainer {

	public abstract Composite getClientArea();

	public abstract Display getDisplay();

	public abstract void setButtonSkin(int buttonId, ISkinable drawable);

	public ISkinable getButtonSkin(int buttonId);

	public abstract void setEnabled(int buttonId, boolean enabled);

	public abstract boolean getEnabled(int buttonId);

	public abstract Image getImage();

	public abstract void setImage(Image image);

	public abstract String getText();

	public abstract void setText(String text);

	public abstract void setBounds(Rectangle bounds);

	public abstract void setBounds(int x, int y, int width, int height);

	public abstract Rectangle getBounds();

	public abstract void setLocation(Point location);

	public abstract void setLocation(int x, int y);

	public abstract Point getLocation();

	public abstract void setSize(Point size);

	public abstract void setSize(int width, int height);

	public abstract Point getSize();

	public abstract void layout();

	public abstract void open();

	public abstract void close();

	public abstract Shell getShell();

}