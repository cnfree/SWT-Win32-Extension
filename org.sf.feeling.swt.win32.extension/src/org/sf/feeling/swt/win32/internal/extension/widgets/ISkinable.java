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

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.widgets.Event;

public interface ISkinable {
  
  public abstract int getInitWidth();
  
  public abstract int getInitHeight();
  
  public abstract Color getForeground(Event event);
  
  public abstract void configue(Event event);
  
  public abstract void paint(GC gc, int x, int y, int width, int height);
  
  public abstract void paint(GC gc, int x, int y);
}
