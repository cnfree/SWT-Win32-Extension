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
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;


/**
 * A non-native size grip which looks (and almost feels) like the native Win32 size grip.
 * <p>
 * The SHADOW_IN style causes highlight lines to be drawn at the right and bottom border.
 * This style should be used when placing the size grip on top of the bottom right corner
 * of a FramedComposite with style SHADOW_IN. If the FLAT style is set, the size grip is
 * drawn in a Windows XP style instead of the normal Windows Classic style.
 * </p><p>
 * <dl>
 * <dt><b>Styles:</b></dt>
 * <dd>SHADOW_IN, FLAT</dd>
 * <dt><b>Events:</b></dt>
 * <dd>(none)</dd>
 * </dl>
 * </p><p>
 * NOTE: The visibility of this widget is controlled by the "maximized" state of the
 * shell. The size grip is hidden when the shell is maximized, even if it has been made
 * visible by calling <code>setVisible(true)</code>. getVisible() always returns
 * the value set with setVisible(). isVisible() returns the true visibility, as usual.
 * </p>
 * 
 * @author <a href="mailto:cnfree2000@hotmail.com">cnfree</a>
 */

public final class SizeGrip extends Canvas
{
  private static final int WIDTH  = 13;
  private static final int HEIGHT = 13;
  private static final long UPDATE_DELAY = 25;
  
  private int mouseDownOffsetX, mouseDownOffsetY, snapBackX, snapBackY;
  private boolean cancelled;
  private Cursor sizeCursor;
  private Point minSize;
  private boolean userVisible = true;
  private volatile long lastUpdate;
  private Timer timer = new Timer(true);
  private TimerTask timerTask;
  private Composite resizableParent;


  public SizeGrip(Composite parent, int style)
  {
    this(parent, parent.getShell(), style);
  }


  public SizeGrip(Composite parent, final Composite resizableParent, int style)
  {
    super(parent, style = checkStyle (style));
    this.resizableParent = resizableParent;
    setSize(WIDTH, HEIGHT);

    sizeCursor = new Cursor(getDisplay(), SWT.CURSOR_SIZENWSE);
    setCursor(sizeCursor);

    addListener(SWT.Dispose, new Listener()
    {
      public void handleEvent(Event e)
      {
        if(sizeCursor != null)
        {
          sizeCursor.dispose();
          sizeCursor = null;
        }
      }
    });

    addListener(SWT.Paint, new Listener()
    {
      public void handleEvent(Event event)
      {
        Rectangle r = getClientArea();
        if(r.width == 0 || r.height == 0) return;
    
        Display disp = getDisplay();
        Color shadow = disp.getSystemColor(SWT.COLOR_WIDGET_NORMAL_SHADOW);
        Color highlight = disp.getSystemColor(SWT.COLOR_WIDGET_HIGHLIGHT_SHADOW);

        event.gc.setLineWidth(1);

        if((getStyle() & SWT.FLAT) != 0)
        {
          event.gc.setBackground(highlight);
          event.gc.fillRectangle(r.width-3, r.height-3, 2, 2);
          event.gc.fillRectangle(r.width-7, r.height-3, 2, 2);
          event.gc.fillRectangle(r.width-11, r.height-3, 2, 2);
          event.gc.fillRectangle(r.width-3, r.height-7, 2, 2);
          event.gc.fillRectangle(r.width-7, r.height-7, 2, 2);
          event.gc.fillRectangle(r.width-3, r.height-11, 2, 2);
          event.gc.setBackground(shadow);
          event.gc.fillRectangle(r.width-4, r.height-4, 2, 2);
          event.gc.fillRectangle(r.width-8, r.height-4, 2, 2);
          event.gc.fillRectangle(r.width-12, r.height-4, 2, 2);
          event.gc.fillRectangle(r.width-4, r.height-8, 2, 2);
          event.gc.fillRectangle(r.width-8, r.height-8, 2, 2);
          event.gc.fillRectangle(r.width-4, r.height-12, 2, 2);
          event.gc.setForeground(highlight);
        }
        else
        {
          event.gc.setForeground(shadow);
          event.gc.drawLine(r.width-3, r.height-2, r.width-2, r.height-3);
          event.gc.drawLine(r.width-4, r.height-2, r.width-2, r.height-4);
          event.gc.drawLine(r.width-7, r.height-2, r.width-2, r.height-7);
          event.gc.drawLine(r.width-8, r.height-2, r.width-2, r.height-8);
          event.gc.drawLine(r.width-11, r.height-2, r.width-2, r.height-11);
          event.gc.drawLine(r.width-12, r.height-2, r.width-2, r.height-12);
  
          event.gc.setForeground(highlight);
          event.gc.drawLine(r.width-5, r.height-2, r.width-2, r.height-5);
          event.gc.drawLine(r.width-9, r.height-2, r.width-2, r.height-9);
          event.gc.drawLine(r.width-13, r.height-2, r.width-2, r.height-13);
        }
        
        if((getStyle() & SWT.SHADOW_IN) != 0)
        {
          if(event.width > WIDTH) event.gc.drawLine(0, r.height-1, r.width-14, r.height-1);
          if(event.height > HEIGHT) event.gc.drawLine(r.width-1, 0, r.width-1, r.height-14);
        }
      }
    });

    addListener(SWT.MouseDown, new Listener()
    {
      public void handleEvent(Event event)
      {
        if(event.button == 1)
        {
          mouseDownOffsetX = event.x;
          mouseDownOffsetY = event.y;
          Point p = resizableParent.getSize();
          snapBackX = p.x;
          snapBackY = p.y;
          cancelled = false;
          //System.out.println("x="+mouseDownOffsetX+", y="+mouseDownOffsetY);
        }
        else if(event.button == 3 && (event.stateMask & SWT.BUTTON1) != 0) // chord click
        {
          if(snapBackX > 0 && snapBackY > 0)
          {
            resizableParent.setSize(snapBackX, snapBackY);
            snapBackX = 0;
            snapBackY = 0;
            cancelled = true;
          }
        }
      }
    });

    addListener(SWT.MouseMove, new Listener()
    {
      public void handleEvent(final Event event)
      {
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

    final Listener resizeListener = (resizableParent instanceof Shell) ? new Listener()
    {
      public void handleEvent(Event event)
      {
        updateVisibility();
      }
    } : null;

    if(resizeListener != null) resizableParent.addListener(SWT.Resize, resizeListener);
    
    addListener(SWT.Dispose, new Listener()
    {
      public void handleEvent(Event event)
      {
        timer.cancel();
        if(resizeListener != null) resizableParent.removeListener(SWT.Resize, resizeListener);
      }
    });

    updateVisibility();
  }


  private void performResize(Event event)
  {
    // Make sure we stay within the container parent's client area
    Rectangle ca;
    if(resizableParent instanceof Shell) ca = getDisplay().getClientArea();
    else ca = getDisplay().map(resizableParent.getParent(), null, resizableParent.getParent().getClientArea());
    Point limit = toControl(ca.x + ca.width - 1, ca.y + ca.height - 1);
    event.x = Math.min(event.x, limit.x);
    event.y = Math.min(event.y, limit.y);

    Point p = resizableParent.getSize();
    int newX = p.x + event.x - mouseDownOffsetX;
    int newY = p.y + event.y - mouseDownOffsetY;
    if(minSize != null)
    {
      newX = Math.max(minSize.x, newX);
      newY = Math.max(minSize.y, newY);
    }
    if(newX != p.x || newY != p.y) resizableParent.setSize(newX, newY);
  }


  private void updateVisibility()
  {
    if(resizableParent instanceof Shell)
    {
      boolean vis = super.getVisible();
      boolean max = ((Shell)resizableParent).getMaximized();
      boolean newVis = userVisible && !max;
      if(vis != newVis) super.setVisible(newVis);
    }
    else if(userVisible != super.getVisible()) super.setVisible(userVisible);
  }


  public Point computeSize(int wHint, int hHint, boolean changed)
  {
    checkWidget();
    if(wHint == SWT.DEFAULT) wHint = WIDTH;
    if(hHint == SWT.DEFAULT) hHint = HEIGHT;
    return new Point(wHint, hHint);
  }


  private static int checkStyle(int style)
  {
    int mask = SWT.SHADOW_IN | SWT.FLAT;
    style &= mask;
    return style;
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


  public boolean getVisible()
  {
    checkWidget();
    return userVisible;
  }


  public void setVisible(boolean visible)
  {
    checkWidget();
    userVisible = visible;
    updateVisibility();
  }
}
