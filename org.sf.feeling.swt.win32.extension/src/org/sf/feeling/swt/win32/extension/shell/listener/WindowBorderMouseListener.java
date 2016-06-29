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
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseTrackListener;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.sf.feeling.swt.win32.internal.extension.Extension;

public class WindowBorderMouseListener implements MouseListener, MouseTrackListener {
    private Shell shell;

    private int position;

    private Control control;

    public final static int LEFT = 0;

    public final static int RIGHT = 1;

    public final static int UP = 2;

    public final static int DOWN = 3;

    public final static int LEFTUP = 4;

    public final static int RIGHTUP = 5;

    public final static int LEFTDOWN = 6;

    public final static int RIGHTDOWN = 7;

    public final static int MOVE = 8;

    public WindowBorderMouseListener(Control control, int position) {
        this.shell = control.getShell();
        this.position = position;
        this.control = control;
    }

    public void mouseDoubleClick(MouseEvent e) {
        
    }

    public void mouseDown(MouseEvent e) {
        if (e.button == 1) {
            if(shell.getMaximized())return;
            switch (position) {
            case LEFT:
                Extension.ReleaseCapture();
                Extension.SendMessage(shell.handle, Extension.WM_SYSCOMMAND, 0xF001, 0);
                break;
            case RIGHT:
                Extension.ReleaseCapture();
                Extension.SendMessage(shell.handle, Extension.WM_SYSCOMMAND, 0xF002, 0);
                break;
            case UP:
                Extension.ReleaseCapture();
                Extension.SendMessage(shell.handle, Extension.WM_SYSCOMMAND, 0xF003, 0);
                break;
            case DOWN:
                Extension.ReleaseCapture();
                Extension.SendMessage(shell.handle, Extension.WM_SYSCOMMAND, 0xF006, 0);
                break;
            case LEFTUP:
                Extension.ReleaseCapture();
                Extension.SendMessage(shell.handle, Extension.WM_SYSCOMMAND, 0xF004, 0);
                break;
            case RIGHTUP:
                Extension.ReleaseCapture();
                Extension.SendMessage(shell.handle, Extension.WM_SYSCOMMAND, 0xF005, 0);
                break;
            case LEFTDOWN:
                Extension.ReleaseCapture();
                Extension.SendMessage(shell.handle, Extension.WM_SYSCOMMAND, 0xF007, 0);
                break;
            case RIGHTDOWN:
                Extension.ReleaseCapture();
                Extension.SendMessage(shell.handle, Extension.WM_SYSCOMMAND, 0xF008, 0);
                break;
            case MOVE:
                control.setCursor(new Cursor(shell.getDisplay(),
                        SWT.CURSOR_ARROW));
                Extension.ReleaseCapture();
                Extension.SendMessage(shell.handle, Extension.WM_SYSCOMMAND, 0xF012, 0);
                break;
            }
        }
    }

    public void mouseUp(MouseEvent e) {
    }

    public void mouseEnter(MouseEvent e) {
        if(shell.getMaximized())return;
        switch (position) {
        case LEFT:
            control.setCursor(new Cursor(shell.getDisplay(), SWT.CURSOR_SIZEE));
            break;
        case RIGHT:
            control.setCursor(new Cursor(shell.getDisplay(), SWT.CURSOR_SIZEW));
            break;
        case UP:
            control.setCursor(new Cursor(shell.getDisplay(), SWT.CURSOR_SIZEN));
            break;
        case DOWN:
            control.setCursor(new Cursor(shell.getDisplay(), SWT.CURSOR_SIZES));
            break;
        case LEFTUP:
            control
                    .setCursor(new Cursor(shell.getDisplay(), SWT.CURSOR_SIZENW));
            break;
        case RIGHTUP:
            control
                    .setCursor(new Cursor(shell.getDisplay(), SWT.CURSOR_SIZENE));
            break;
        case LEFTDOWN:
            control
                    .setCursor(new Cursor(shell.getDisplay(), SWT.CURSOR_SIZESW));
            break;
        case RIGHTDOWN:
            control
                    .setCursor(new Cursor(shell.getDisplay(), SWT.CURSOR_SIZESE));
            break;
        case MOVE:
            control.setCursor(new Cursor(shell.getDisplay(),
                    SWT.CURSOR_SIZEALL));
            break;
        }
    }

    public void mouseExit(MouseEvent e) {
        control.setCursor(new Cursor(shell.getDisplay(),
                SWT.CURSOR_ARROW));
    }

    public void mouseHover(MouseEvent e) {
        if(shell.getMaximized())return;
        switch (position) {
        case LEFT:
            control.setCursor(new Cursor(shell.getDisplay(), SWT.CURSOR_SIZEE));
            break;
        case RIGHT:
            control.setCursor(new Cursor(shell.getDisplay(), SWT.CURSOR_SIZEW));
            break;
        case UP:
            control.setCursor(new Cursor(shell.getDisplay(), SWT.CURSOR_SIZEN));
            break;
        case DOWN:
            control.setCursor(new Cursor(shell.getDisplay(), SWT.CURSOR_SIZES));
            break;
        case LEFTUP:
            control
                    .setCursor(new Cursor(shell.getDisplay(), SWT.CURSOR_SIZENW));
            break;
        case RIGHTUP:
            control
                    .setCursor(new Cursor(shell.getDisplay(), SWT.CURSOR_SIZENE));
            break;
        case LEFTDOWN:
            control
                    .setCursor(new Cursor(shell.getDisplay(), SWT.CURSOR_SIZESW));
            break;
        case RIGHTDOWN:
            control
                    .setCursor(new Cursor(shell.getDisplay(), SWT.CURSOR_SIZESE));
            break;
        case MOVE:
            control.setCursor(new Cursor(shell.getDisplay(),
                    SWT.CURSOR_SIZEALL));
            break;
        }
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}