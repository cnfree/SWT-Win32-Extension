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
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;


/**
 * Instances of this class are composites with a border around the contents.
 * <dl>
 * <dt><b>Styles:</b></dt>
 * <dd>SHADOW_NONE, SHADOW_ETCHED_IN, SHADOW_ETCHED_OUT, SHADOW_IN, SHADOW_OUT</dd>
 * <dt><b>Events:</b></dt>
 * <dd>(none)</dd>
 * </dl>
 * <p>
 * Note: Only one of the above styles may be specified. The default, if no style is
 * specified explicitly, is SHADOW_ETCHED_IN. SHADOW_NONE draws a solid 1px border
 * in the widget's foreground color.
 * </p>
 *
 * @author <a href="mailto:cnfree2000@hotmail.com">cnfree</a>
 */

public class FramedComposite extends Composite
{
  private int trimSize;
  private int style;


  public FramedComposite(Composite parent, int style)
  {
    super(parent, style = checkStyle (style));

    this.style = style;

    if((style & SWT.SHADOW_ETCHED_IN) != 0 || (style & SWT.SHADOW_ETCHED_OUT) != 0) trimSize = 2;
    else trimSize = 1;

    addPaintListener(new PaintListener()
    {
      public void paintControl(PaintEvent event) { onPaint(event); }
    });
  }


  private static int checkStyle(int style)
  {
    int mask = SWT.SHADOW_ETCHED_IN | SWT.SHADOW_ETCHED_OUT | SWT.SHADOW_IN | SWT.SHADOW_OUT | SWT.SHADOW_NONE;
    style &= mask;
    if(style == 0) style = SWT.SHADOW_ETCHED_IN;
    return style;
  }


  public Rectangle computeTrim(int x, int y, int width, int height)
  {
    checkWidget();
    Rectangle trim = super.computeTrim(x, y, width, height);
    trim.x -= trimSize;
    trim.y -= trimSize;
    trim.width += 2*trimSize;
    trim.height += 2*trimSize;
    return trim;
  }
  

  public Rectangle getClientArea()
  {
    checkWidget();
    Rectangle r = super.getClientArea();
    r.x += trimSize;
    r.y += trimSize;
    r.width -= 2*trimSize;
    r.height -= 2*trimSize;
    if(r.width < 0) r.width = 0;
    if(r.height < 0) r.height = 0;
    return r;
  }


  private void onPaint(PaintEvent event)
  {
    Rectangle r = super.getClientArea();
    if(r.width == 0 || r.height == 0) return;

    Display disp = getDisplay();
    event.gc.setLineWidth(1);

    Color shadow = disp.getSystemColor(SWT.COLOR_WIDGET_NORMAL_SHADOW);
    Color highlight = disp.getSystemColor(SWT.COLOR_WIDGET_HIGHLIGHT_SHADOW);
    if(shadow == null || highlight == null) return;

    if((style & SWT.SHADOW_NONE) != 0)
    {
      event.gc.setForeground(getForeground());
      event.gc.drawRectangle(r.x, r.y, r.width-1, r.height-1);
    }
    if((style & SWT.SHADOW_IN) != 0)
    {
      drawBevelRect(event.gc, r.x, r.y, r.width-1, r.height-1, shadow, highlight);
    }
    else if((style & SWT.SHADOW_OUT) != 0)
    {
      drawBevelRect(event.gc, r.x, r.y, r.width-1, r.height-1, highlight, shadow);
    }
    else if((style & SWT.SHADOW_ETCHED_IN) != 0)
    {
      drawBevelRect(event.gc, r.x, r.y, r.width-1, r.height-1, shadow, highlight);
      drawBevelRect(event.gc, r.x+1, r.y+1, r.width-3, r.height-3, highlight, shadow);
    }
    else // if((style & SWT.SHADOW_ETCHED_OUT) != 0)
    {
      drawBevelRect(event.gc, r.x, r.y, r.width-1, r.height-1, highlight, shadow);
      drawBevelRect(event.gc, r.x+1, r.y+1, r.width-3, r.height-3, shadow, highlight);
    }
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
