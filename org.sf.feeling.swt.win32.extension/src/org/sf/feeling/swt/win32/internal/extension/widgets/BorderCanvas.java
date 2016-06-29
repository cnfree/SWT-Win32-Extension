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

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

public class BorderCanvas extends Canvas {
  
  private int borderType;
  
  private ISkinable drawable;
  
  private Image swapImage;
  
  public BorderCanvas(Composite parent, int border) {
    super(parent, SWT.NONE);
    
    this.borderType = border;
    
    setBackgroundMode(SWT.INHERIT_DEFAULT);
    
    createLayoutData(border);
    
    addListener(SWT.Resize, new Listener() {
      public void handleEvent(Event event) {
        updateSkin(event);
      }
    });
  }
  
  public int getType() {
    return borderType;
  }
  
  public ISkinable getDrawable() {
    return drawable;
  }
  
  public void setDrawable(ISkinable border) {
    
    GridData layoutData = (GridData) getLayoutData();
    switch (getType()) {
      case IContainer.BORDER_N:
      case IContainer.BORDER_S:
        layoutData.heightHint = border.getInitHeight();
        break;
      case IContainer.BORDER_W:
      case IContainer.BORDER_E:
        layoutData.widthHint = border.getInitWidth();
        break;
      case IContainer.BORDER_NW:
      case IContainer.BORDER_NE:
      case IContainer.BORDER_SW:
      case IContainer.BORDER_SE:
      default:
        layoutData.widthHint = border.getInitWidth();
        layoutData.heightHint = border.getInitHeight();
        break;
    }
    setLayoutData(layoutData);
    
    this.drawable = border;
  }
  
  public void setHeight(int height) {
    
    GridData layoutData = (GridData) getLayoutData();
    switch (getType()) {
      case IContainer.BORDER_N:
      case IContainer.BORDER_S:
        layoutData.heightHint = height;
        break;
      case IContainer.BORDER_NW:
      case IContainer.BORDER_NE:
      case IContainer.BORDER_SW:
      case IContainer.BORDER_SE:
      default:
        layoutData.heightHint = height;
        break;
    }
    setLayoutData(layoutData);
  }
  
  public void setWidth(int width) {
    
    GridData layoutData = (GridData) getLayoutData();
    switch (getType()) {
      case IContainer.BORDER_W:
      case IContainer.BORDER_E:
        layoutData.widthHint = width;
        break;
      case IContainer.BORDER_NW:
      case IContainer.BORDER_NE:
      case IContainer.BORDER_SW:
      case IContainer.BORDER_SE:
      default:
        layoutData.widthHint = width;
        break;
    }
    setLayoutData(layoutData);
  }
  
  private void createLayoutData(int border) {
    GridData layoutData;
    switch (border) {
      case IContainer.BORDER_N:
      case IContainer.BORDER_S:
        layoutData = new GridData(GridData.FILL, GridData.FILL, true, false);
        break;
      case IContainer.BORDER_W:
      case IContainer.BORDER_E:
        layoutData = new GridData(GridData.FILL, GridData.FILL, false, true);
        break;
      case IContainer.BORDER_NW:
      case IContainer.BORDER_NE:
      case IContainer.BORDER_SW:
      case IContainer.BORDER_SE:
      default:
        layoutData = new GridData(GridData.FILL, GridData.FILL, false, false);
        break;
    }
    setLayoutData(layoutData);
  }
  
  public void dispose() {
    super.dispose();
    
    if (swapImage != null) {
      swapImage.dispose();
      swapImage = null;
    }
  }
  
  public void updateSkin(Event event) {
    
    if (swapImage != null) {
      swapImage.dispose();
      swapImage = null;
    }
    
    if (drawable != null) {
      
      Color foreground = drawable.getForeground(event);
      if (foreground != null && !foreground.isDisposed()) {
        setForeground(foreground);
      }
      
      GridData data = (GridData) getLayoutData();
      Rectangle bounds = getBounds();
      
      int width = 0;
      int height = 0;
      
      width = data.widthHint > 0 ? data.widthHint : bounds.width;
      height = data.heightHint > 0 ? data.heightHint : bounds.height;
      
      width = width > 0 ? width : drawable.getInitWidth();
      height = height > 0 ? height : drawable.getInitHeight();
      
      if (width > 0 && height > 0) {
        swapImage = new Image(getDisplay(), width, height);
        GC gc = new GC(swapImage);
        drawable.configue(event);
        drawable.paint(gc, 0, 0, width, height); 
        gc.dispose();
        setBackgroundImage(swapImage);
      }
    }
  }
}