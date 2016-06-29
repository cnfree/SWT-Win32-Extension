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
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tracker;


public class MouseResizeListener implements Listener, DisposeListener {
  
  private Shell shell;
  
  private BorderCanvas border;
  
  private int borderType;
  
  private Point point = new Point(-1, -1);
  
  private boolean resizable;
  
  private boolean valid;
  
  private Tracker tracker;
  
  public MouseResizeListener(int type, BorderCanvas border, boolean resizable) {
    this.borderType = type;
    this.border = border;
    this.resizable = resizable;
    this.valid = false;
    this.shell = border.getShell();
  }
  
  public void handleEvent(Event e) {
    if (!resizable) {
      return;
    }
    
    if (!shell.getMaximized()) {
      switch (e.type) {
        case SWT.MouseDown:
          if (valid) {
            point.x = e.x;
            point.y = e.y;
            
            int style = SWT.RESIZE;
            switch (borderType) {
              case IContainer.BORDER_N:
                style |= SWT.UP;
                break;
              case IContainer.BORDER_S:
                style |= SWT.DOWN;
                break;
              case IContainer.BORDER_W:
                style |= SWT.LEFT;
                break;
              case IContainer.BORDER_E:
                style |= SWT.RIGHT;
                break;
              case IContainer.BORDER_NW:
                style |= SWT.UP | SWT.LEFT;
                break;
              case IContainer.BORDER_SE:
                style |= SWT.DOWN | SWT.RIGHT;
                break;
              case IContainer.BORDER_NE:
                style |= SWT.UP | SWT.RIGHT;
                break;
              case IContainer.BORDER_SW:
                style |= SWT.DOWN | SWT.LEFT;
                break;
              default:
                break;
            }
            
            tracker = new Tracker(border.getDisplay(), style);
            tracker.setStippled(true);
            tracker.setRectangles(new Rectangle[] { shell.getBounds() });
            tracker.addDisposeListener(this);
            tracker.open();
            tracker.dispose();
            tracker = null;
          }
          break;
        case SWT.MouseMove:
          valid = false;
          if (point.x < 0 && point.y < 0) {
            int space = 5;
            Rectangle bounds = border.getBounds();
            switch (borderType) {
              case IContainer.BORDER_N:
                if (e.y >= 0 && e.y <= space) {
                  border.setCursor(border.getDisplay().getSystemCursor(SWT.CURSOR_SIZENS));
                  valid = true;
                } else {
                  border.setCursor(border.getDisplay().getSystemCursor(SWT.CURSOR_ARROW));
                }
                break;
              case IContainer.BORDER_S:
                if (e.y >= (bounds.height - space) && e.y <= bounds.height) {
                  border.setCursor(border.getDisplay().getSystemCursor(SWT.CURSOR_SIZENS));
                  valid = true;
                } else {
                  border.setCursor(border.getDisplay().getSystemCursor(SWT.CURSOR_ARROW));
                }
                break;
              case IContainer.BORDER_W:
                if (e.x >= 0 && e.x <= space) {
                  border.setCursor(border.getDisplay().getSystemCursor(SWT.CURSOR_SIZEWE));
                  valid = true;
                } else {
                  border.setCursor(border.getDisplay().getSystemCursor(SWT.CURSOR_ARROW));
                }
                break;
              case IContainer.BORDER_E:
                if (e.x >= (bounds.width - space) && e.x <= bounds.width) {
                  border.setCursor(border.getDisplay().getSystemCursor(SWT.CURSOR_SIZEWE));
                  valid = true;
                } else {
                  border.setCursor(border.getDisplay().getSystemCursor(SWT.CURSOR_ARROW));
                }
                break;
              case IContainer.BORDER_NW:
                if (e.x <= space && e.x >= 0 && e.y <= space && e.y >= 0) {
                  border.setCursor(border.getDisplay().getSystemCursor(SWT.CURSOR_SIZENWSE));
                  valid = true;
                } else {
                  border.setCursor(border.getDisplay().getSystemCursor(SWT.CURSOR_ARROW));
                }
                break;
              case IContainer.BORDER_SE:
                if (e.x <= bounds.width && e.x >= (bounds.width - space) && e.y <= bounds.height && e.y >= (bounds.height - space)) {
                  border.setCursor(border.getDisplay().getSystemCursor(SWT.CURSOR_SIZENWSE));
                  valid = true;
                } else {
                  border.setCursor(border.getDisplay().getSystemCursor(SWT.CURSOR_ARROW));
                }
                break;
              case IContainer.BORDER_NE:
                if (e.x <= bounds.width && e.x >= (bounds.width - space) && e.y <= space && e.y >= 0) {
                  border.setCursor(border.getDisplay().getSystemCursor(SWT.CURSOR_SIZENESW));
                  valid = true;
                } else {
                  border.setCursor(border.getDisplay().getSystemCursor(SWT.CURSOR_ARROW));
                }
                break;
              case IContainer.BORDER_SW:
                if (e.x <= space && e.x >= 0 && e.y <= bounds.height && e.y >= (bounds.height - space)) {
                  border.setCursor(border.getDisplay().getSystemCursor(SWT.CURSOR_SIZENESW));
                  valid = true;
                } else {
                  border.setCursor(border.getDisplay().getSystemCursor(SWT.CURSOR_ARROW));
                }
                break;
              default:
                border.setCursor(border.getDisplay().getSystemCursor(SWT.CURSOR_ARROW));
                break;
            }
          } else {
            border.setCursor(border.getDisplay().getSystemCursor(SWT.CURSOR_ARROW));
          }
          break;
        case SWT.MouseExit:
          valid = false;
          border.setCursor(border.getDisplay().getSystemCursor(SWT.CURSOR_ARROW));
          break;
        default:
          valid = false;
          break;
      }
    }
  }
  
  public void widgetDisposed(DisposeEvent e) {
    valid = false;
    shell.setBounds(tracker.getRectangles()[0]);
    point.x = -1;
    point.y = -1;
  }
}
