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

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;


/**
 * Instances of this class are non-native separator lines.
 * <dl>
 * <dt><b>Styles:</b></dt>
 * <dd>SHADOW_IN, SHADOW_OUT, SHADOW_NONE, HORIZONTAL, VERTICAL</dd>
 * <dt><b>Events:</b></dt>
 * <dd>(none)</dd>
 * </dl>
 * <p>
 * Note: Only one of SHADOW_IN, SHADOW_OUT and SHADOW_NONE may be specified.
 * If neither ist specified, the default value SHADOW_IN is used. If SHADOW_NONE
 * is specified, a single line is drawn with the control's foreground color.
 * Only one of HORIZONTAL and VERTICAL may be specified. The default is VERTICAL.
 * </p>
 *
 * @author <a href="mailto:cnfree2000@hotmail.com">cnfree</a>
 */

public final class Separator extends Canvas
{
  private int lineSize;
  private int style;


  public Separator(Composite parent, int style)
  {
    super(parent, style = checkStyle (style));

    this.style = style;

    if((style & SWT.SHADOW_IN) != 0 || (style & SWT.SHADOW_OUT) != 0) lineSize = 2;
    else lineSize = 1;

    addPaintListener(new PaintListener()
    {
      public void paintControl(PaintEvent event) { onPaint(event); }
    });
  }


  private static int checkStyle(int style)
  {
    int mask = SWT.SHADOW_IN | SWT.SHADOW_OUT| SWT.SHADOW_NONE | SWT.HORIZONTAL | SWT.VERTICAL;
    style &= mask;
    if((style & (SWT.SHADOW_IN | SWT.SHADOW_OUT| SWT.SHADOW_NONE)) == 0) style |= SWT.SHADOW_IN;
    if((style & (SWT.HORIZONTAL | SWT.VERTICAL)) == 0) style |= SWT.VERTICAL;
    return style;
  }


  public Point computeSize(int wHint, int hHint, boolean changed)
  {
    checkWidget();
    if(wHint == SWT.DEFAULT) wHint = lineSize;
    if(hHint == SWT.DEFAULT) hHint = lineSize;
    return new Point(wHint, hHint);
  }
  

  public boolean setFocus()
  {
    checkWidget();
    return false;
  }


  private void onPaint(PaintEvent event)
  {
    Rectangle r = getClientArea();
    if(r.width == 0 || r.height == 0) return;
    boolean horiz = ((style & SWT.HORIZONTAL) != 0);
    int mid = horiz ? r.y + (r.height/2) : r.x + (r.width/2);

    Display disp = getDisplay();
    event.gc.setLineWidth(1);

    if((style & SWT.SHADOW_IN) != 0)
    {
      Color shadow = disp.getSystemColor(SWT.COLOR_WIDGET_NORMAL_SHADOW);
      Color highlight = disp.getSystemColor(SWT.COLOR_WIDGET_HIGHLIGHT_SHADOW);
      event.gc.setForeground(shadow);
      if(horiz) event.gc.drawLine(r.x, mid-1, r.x+r.width-1, mid-1);
      else event.gc.drawLine(mid-1, r.y, mid-1, r.y+r.height-1);
      event.gc.setForeground(highlight);
      if(horiz) event.gc.drawLine(r.x, mid, r.x+r.width-1, mid);
      else event.gc.drawLine(mid, r.y, mid, r.y+r.height-1);
    }
    else if((style & SWT.SHADOW_OUT) != 0)
    {
      Color shadow = disp.getSystemColor(SWT.COLOR_WIDGET_NORMAL_SHADOW);
      Color highlight = disp.getSystemColor(SWT.COLOR_WIDGET_HIGHLIGHT_SHADOW);
      event.gc.setForeground(highlight);
      if(horiz) event.gc.drawLine(r.x, mid-1, r.x+r.width-1, mid-1);
      else event.gc.drawLine(mid-1, r.y, mid-1, r.y+r.height-1);
      event.gc.setForeground(shadow);
      if(horiz) event.gc.drawLine(r.x, mid, r.x+r.width-1, mid);
      else event.gc.drawLine(mid, r.y, mid, r.y+r.height-1);
    }
    else
    {
      event.gc.setForeground(getForeground());
      if(horiz) event.gc.drawLine(r.x, mid, r.x+r.width-1, mid);
      else event.gc.drawLine(mid, r.y, mid, r.y+r.height-1);
    }
  }
}
