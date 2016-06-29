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

package org.sf.feeling.swt.win32.extension.widgets;

import java.util.Timer;
import java.util.TimerTask;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;


/**
 * A border for a resizable container. This border control usually fills the
 * entire container, with a content pane above it (not covering the actual
 * border area).
 * 
 * <p>If the style SWT.BORDER ist set, a beveled border (as used on Windows
 * Classic window decorations) will be drawn. Without this style, no drawing
 * is done.</p>
 *
 * @author <a href="mailto:cnfree2000@hotmail.com">cnfree</a>
 */

public class SizeBorder extends Canvas
{
  private static final long UPDATE_DELAY = 25;
  
  private static final int AREA_NONE = 0;
  private static final int AREA_N    = 1;
  private static final int AREA_S    = 2;
  private static final int AREA_E    = 4;
  private static final int AREA_W    = 8;
  private static final int AREA_NW   = 9;
  private static final int AREA_NE   = 5;
  private static final int AREA_SE   = 6;
  private static final int AREA_SW   = 10;
  
  private Rectangle snapBack;
  private boolean cancelled = true;
  private volatile long lastUpdate;
  private Timer timer = new Timer(true);
  private TimerTask timerTask;
  private Composite resizableParent;
  private Point minSize, mouseDownOffset;
  private int borderWidth = 4, cornerSize = 16;
  private Display display;
  private Cursor cursor, cursorNWSE, cursorNESW, cursorWE, cursorNS;
  private int currentArea;


  public SizeBorder(Composite parent, int style)
  {
    this(parent, parent.getShell(), style);
  }

  
  public SizeBorder(Composite parent, final Composite resizableParent, int style)
  {
    super(parent, checkStyle (style));
    this.resizableParent = resizableParent;
    this.display = getDisplay();

    cursorNWSE = new Cursor(getDisplay(), SWT.CURSOR_SIZENWSE);
    cursorNESW = new Cursor(getDisplay(), SWT.CURSOR_SIZENESW);
    cursorWE = new Cursor(getDisplay(), SWT.CURSOR_SIZEWE);
    cursorNS = new Cursor(getDisplay(), SWT.CURSOR_SIZENS);

    addListener(SWT.Dispose, new Listener()
    {
      public void handleEvent(Event e)
      {
        cursorNWSE.dispose();
        cursorNESW.dispose();
        cursorWE.dispose();
        cursorNS.dispose();
      }
    });

    if((style & SWT.BORDER) != 0)
    {
      final Color highlightShadowColor = display.getSystemColor(SWT.COLOR_WIDGET_HIGHLIGHT_SHADOW);
      final Color lightShadowColor = display.getSystemColor(SWT.COLOR_WIDGET_LIGHT_SHADOW);
      final Color normalShadowColor = display.getSystemColor(SWT.COLOR_WIDGET_NORMAL_SHADOW);
      final Color darkShadowColor = display.getSystemColor(SWT.COLOR_WIDGET_DARK_SHADOW);
  
      addListener(SWT.Paint, new Listener()
      {
        public void handleEvent(Event event)
        {
          Rectangle r = getClientArea();
          if(r.width == 0 || r.height == 0) return;
          drawBevelRect(event.gc, r.x, r.y, r.width-1, r.height-1, lightShadowColor, darkShadowColor);
          drawBevelRect(event.gc, r.x+1, r.y+1, r.width-3, r.height-3, highlightShadowColor, normalShadowColor);
        }
      });
    }

    addListener(SWT.MouseDown, new Listener()
    {
      public void handleEvent(Event event)
      {
        if(event.button == 1)
        {
          currentArea = areaAtPoint(event.x, event.y);
          if(currentArea == AREA_NONE) return;
          if(resizableParent instanceof Shell)
            mouseDownOffset = toDisplay(event.x, event.y);
          else
            mouseDownOffset = display.map(SizeBorder.this, resizableParent.getParent(), event.x, event.y);
          snapBack = resizableParent.getBounds();
          cancelled = false;
        }
        else if(event.button == 3 && (event.stateMask & SWT.BUTTON1) != 0) // chord click
        {
          if(snapBack != null)
          {
            resizableParent.setBounds(snapBack);
            snapBack = null;
            cancelled = true;
          }
        }
      }
    });

    addListener(SWT.MouseMove, new Listener()
    {
      public void handleEvent(final Event event)
      {
        if((event.stateMask & SWT.BUTTON1) == 0) updateCursor(areaAtPoint(event.x, event.y));

        if(!cancelled && (event.stateMask & SWT.BUTTON1) != 0)
        {
          if(timerTask != null)
          {
            timerTask.cancel();
            timerTask = null;
          }
          long now = System.currentTimeMillis();
          if(lastUpdate + UPDATE_DELAY < now)
          {
            performResize(event);
            lastUpdate = now;
          }
          else
          {
            timerTask = new TimerTask()
            {
              public void run()
              {
                final TimerTask executingTask = this;
                event.display.asyncExec(new Runnable()
                {
                  public void run()
                  {
                    if(executingTask != timerTask) return;
                    performResize(event);
                  }
                });
              }
            };
            timer.schedule(timerTask, UPDATE_DELAY);
          }
        }
      }
    });
    
    addListener(SWT.MouseUp, new Listener()
    {
      public void handleEvent(Event event)
      {
        if(timerTask != null)
        {
          timerTask.cancel();
          timerTask = null;
        }
        if(!cancelled && (event.stateMask & SWT.BUTTON1) != 0)
        {
          performResize(event);
        }
      }
    });
    
    addListener(SWT.Show, new Listener()
    {
      public void handleEvent(Event event)
      {
        Point p = toControl(display.getCursorLocation());
        updateCursor(areaAtPoint(p.x, p.y));
      }
    });

    addListener(SWT.Dispose, new Listener()
    {
      public void handleEvent(Event event)
      {
        timer.cancel();
      }
    });
  }


  private static int checkStyle(int style)
  {
    //int mask = SWT.NONE;
    //style &= mask;
    style = SWT.NO_FOCUS;
    return style;
  }


  private void performResize(Event event)
  {
    // Make sure we stay within the container parent's client area
    Rectangle ca;
    if(resizableParent instanceof Shell) ca = getDisplay().getClientArea();
    else ca = getDisplay().map(resizableParent.getParent(), null, resizableParent.getParent().getClientArea());
    Point caOffset = toControl(ca.x, ca.y);
    event.x = Math.max(Math.min(event.x, caOffset.x + ca.width - 1), caOffset.x);
    event.y = Math.max(Math.min(event.y, caOffset.y + ca.height - 1), caOffset.y);

    // Compute movement relative to position at MouseDown event
    Point movement = (resizableParent instanceof Shell)
      ? toDisplay(event.x, event.y)
      : display.map(this, resizableParent.getParent(), event.x, event.y);
    movement.x -= mouseDownOffset.x;
    movement.y -= mouseDownOffset.y;
    
    // Compute new size and position
    int newW = snapBack.width, newH = snapBack.height, newX = snapBack.x, newY = snapBack.y;
    if((currentArea & AREA_E) != 0) newW += movement.x;
    else if((currentArea & AREA_W) != 0) { newW -= movement.x; newX += snapBack.width - newW; }
    if((currentArea & AREA_S) != 0) newH += movement.y;
    else if((currentArea & AREA_N) != 0) { newH -= movement.y; newY += snapBack.height - newH; }

    // Do not go below the container's minimum size
    int minW, minH;
    if(minSize != null) { minW = minSize.x; minH = minSize.y; }
    else { minW = 0; minH = 0; }
    int maxX = snapBack.x + snapBack.width - minW;
    int maxY = snapBack.y + snapBack.height - minH;

    newW = Math.max(minW, newW);
    newH = Math.max(minH, newH);
    newX = Math.min(maxX, newX);
    newY = Math.min(maxY, newY);

    resizableParent.setBounds(newX, newY, newW, newH);
  }
  
  
  private void updateCursor(int area)
  {
    Cursor c = null;
    switch(area)
    {
      case AREA_N:  case AREA_S:  c = cursorNS;   break;
      case AREA_W:  case AREA_E:  c = cursorWE;   break;
      case AREA_NW: case AREA_SE: c = cursorNWSE; break;
      case AREA_NE: case AREA_SW: c = cursorNESW; break;
    }
    if(cursor == c) return;
    cursor = c;
    setCursor(c);
  }
  
  
  private int areaAtPoint(int x, int y)
  {
    Point size = getSize();
    if(x < borderWidth) // left edge
    {
      if(y < cornerSize) return AREA_NW;
      else if(y >= size.y-cornerSize) return AREA_SW;
      else return AREA_W;
    }
    else if(x >= size.x-borderWidth) // right edge
    {
      if(y >= size.y-cornerSize) return AREA_SE;
      else if(y < cornerSize) return AREA_NE;
      else return AREA_E;
    }
    else if(y < borderWidth) // top edge
    {
      if(x < cornerSize) return AREA_NW;
      else if(x >= size.x-cornerSize) return AREA_NE;
      else return AREA_N;
    }
    else if(y >= size.y-borderWidth) // bottom edge
    {
      if(x >= size.x-cornerSize) return AREA_SE;
      else if(x < cornerSize) return AREA_SW;
      else return AREA_S;
    }
    else return AREA_NONE;
  }
  
  
  public Point computeSize(int wHint, int hHint, boolean changed)
  {
    checkWidget();
    if(wHint == SWT.DEFAULT) wHint = 0;
    if(hHint == SWT.DEFAULT) hHint = 0;
    return new Point(wHint, hHint);
  }


  public boolean setFocus()
  {
    checkWidget();
    return false;
  }


  public boolean isReparentable ()
  {
    checkWidget();
    return false;
  }


  /**
   * Set the allowed minimum size for the shell. The SizeGrip will
   * not resize the shell to a smaller size.
   * <p>
   * Note: This does <em>not</em> affect other ways of resizing the shell,
   * like using the size controls which are placed on the trimmings by
   * the window manager.
   * </p>
   */

  public void setMinimumShellSize(Point p)
  {
    checkWidget();
    this.minSize = p;
  }


  /**
   * Set the allowed minimum size for the shell. The SizeGrip will
   * not resize the shell to a smaller size.
   * <p>
   * Note: This does <em>not</em> affect other ways of resizing the shell,
   * like using the size controls which are placed on the trimmings by
   * the window manager.
   * </p>
   */

  public void setMinimumShellSize(int width, int height)
  {
    checkWidget();
    this.minSize = new Point(width, height);
  }
  
  
  public void setBorderWidth(int width)
  {
    checkWidget();
    borderWidth = width;
    Point p = toControl(display.getCursorLocation());
    updateCursor(areaAtPoint(p.x, p.y));
  }
  
  
  public void setCornerSize(int size)
  {
    checkWidget();
    cornerSize = size;
    Point p = toControl(display.getCursorLocation());
    updateCursor(areaAtPoint(p.x, p.y));
  }

  
  private static void drawBevelRect(GC gc, int x, int y, int w, int h, Color topleft, Color bottomright)
  {
    gc.setForeground(bottomright);
    gc.drawLine(x + w, y, x + w, y + h);
    gc.drawLine(x, y + h, x + w, y + h);

    gc.setForeground(topleft);
    gc.drawLine(x, y, x + w - 1, y);
    gc.drawLine(x, y, x, y + h - 1);
  }
}
