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
package org.sf.feeling.swt.win32.extension.shell.listener;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.MouseTrackListener;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class WindowResizeListener extends MouseAdapter implements MouseTrackListener,
        MouseMoveListener {
    private Control control;

    private int position;

    public final static int LEFT = 0;

    public final static int RIGHT = 1;

    public final static int TOP = 2;

    public final static int DOWN = 3;
    
    private Shell shell;
   
    private int minWidth;
    
    private int minHeight;
    
    public WindowResizeListener(Control control, int position) {
        this.control = control;
        this.position = position;
        this.shell = control.getShell();
    }

    public void mouseEnter(MouseEvent arg0) {
        switch (position) {
        case WindowResizeListener.LEFT:
            control.setCursor(new Cursor(Display.getDefault(),
                    SWT.CURSOR_SIZEE));
            break;
        case WindowResizeListener.RIGHT:
            control.setCursor(new Cursor(Display.getDefault(),
                    SWT.CURSOR_SIZEW));
            break;
        case WindowResizeListener.TOP:
            control.setCursor(new Cursor(Display.getDefault(),
                    SWT.CURSOR_SIZEN));
            break;
        case WindowResizeListener.DOWN:
            control.setCursor(new Cursor(Display.getDefault(),
                    SWT.CURSOR_SIZES));
            break;
        }
        control.addMouseListener(this);
    }

    public void mouseExit(MouseEvent arg0) {
        control.setCursor(new Cursor(Display.getDefault(),
                SWT.CURSOR_ARROW));
        control.removeMouseListener(this);
    }

    public void mouseHover(MouseEvent arg0) {
        switch (position) {
        case WindowResizeListener.LEFT:
            control.setCursor(new Cursor(Display.getDefault(),
                    SWT.CURSOR_SIZEE));
            break;
        case WindowResizeListener.RIGHT:
            control.setCursor(new Cursor(Display.getDefault(),
                    SWT.CURSOR_SIZEW));
            break;
        case WindowResizeListener.TOP:
            control.setCursor(new Cursor(Display.getDefault(),
                    SWT.CURSOR_SIZEN));
            break;
        case WindowResizeListener.DOWN:
            control.setCursor(new Cursor(Display.getDefault(),
                    SWT.CURSOR_SIZES));
            break;
        }
    }

    public void mouseDown(MouseEvent e) {
        if (e.button == 1) {
            this.control.addMouseMoveListener(this);
        }
    }

    public void mouseUp(MouseEvent e) {
        Point location = shell.getLocation();
        Point size = shell.getSize();
        if (e.button == 1) {
            this.control.removeMouseMoveListener(this);
           
            switch (position) {
            case LEFT:
                if(size.x-e.x<minWidth)return;
                shell.setSize(size.x - e.x, size.y);
                shell.setLocation(location.x + e.x,
                        location.y);
                break;
            case RIGHT:
                if(size.x+e.x<minWidth)return;
                shell.setSize(size.x + e.x, size.y);
                break;
            case TOP:
                if(size.y-e.y<minHeight)return;
                shell.setSize(size.x, size.y - e.y);
                shell.setLocation(location.x,
                        location.y + e.y);
                break;
            case DOWN:
                if(size.y+e.y<minHeight)return;
                shell.setSize(size.x, size.y + e.y);
                break;
            }
            handleMouseUpEvent(e);
        }
    }
    

    public void handleMouseUpEvent(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mouseMove(MouseEvent e) {
        Point location = shell.getLocation();
        Point size = shell.getSize();

        
        switch (position) {
        case LEFT:
            if(size.x-e.x<minWidth)return;
            shell.setSize(size.x - e.x, size.y);
            shell.setLocation(location.x + e.x,
                    location.y);
            break;
        case RIGHT:
            if(size.x+e.x<minWidth)return;
            shell.setSize(size.x + e.x, size.y);
            break;
        case TOP:
            if(size.y-e.y<minHeight)return;
            shell.setSize(size.x, size.y - e.y);
            shell.setLocation(location.x,
                    location.y + e.y);
            break;
        case DOWN:
            if(size.y+e.y<minHeight)return;
            shell.setSize(size.x, size.y + e.y);
            break;
        }
        handleMouseMoveEvent(e);
    }
    public void handleMouseMoveEvent(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public int getMinWidth() {
        return minWidth;
    }
    public void setMinWidth(int minWidth) {
        this.minWidth = minWidth>1?minWidth:1;
    }
    public int getMinHeight() {
        return minHeight;
    }
    public void setMinHeigth(int minHeight) {
        this.minHeight = minHeight>1?minHeight:1;
    }
}