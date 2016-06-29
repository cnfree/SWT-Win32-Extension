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
package org.sf.feeling.swt.win32.extension.io;

import org.eclipse.swt.graphics.Point;
import org.sf.feeling.swt.win32.extension.Win32;
import org.sf.feeling.swt.win32.internal.extension.Extension;

/**
 * A utility class that can emulate mouse event.
 * 
 * @author <a href="mailto:cnfree2000@hotmail.com">cnfree</a>
 * 
 */
public class Mouse {
	/**
	 * Mouse left key.
	 */
	public final static int MOUSE_LEFT = 0;

	/**
	 * Mouse right key.
	 */
	public final static int MOUSE_RIGHT = 1;

	/**
	 * Mouse middle wheel key.
	 */
	public final static int MOUSE_MIDDLE = 2;
	
	/**
	 * Max X coordinate on screen
	 */
	public final static int MAX_X_ABSOLUTE = 65535;

	/**
	 * Max Y coordinate on screen
	 */
	public final static int MAX_Y_ABSOLUTE = 65535;

	/**
	 * Emulate mouse move to specified position.
	 * 
	 * @param position
	 *            specify a position where the mouse will move to.
	 * @param absolute
	 *            whether the specified position is a absolute position.
	 */
	public static void mouseMove(Point position, boolean absolute) {
		int flag = Win32.MOUSEEVENTF_MOVE;
		if (absolute)
			flag |= Win32.MOUSEEVENTF_ABSOLUTE;
		Extension.Mouse_Event(flag, position.x, position.y, 0, 0);
	}

	/**
	 * Emulate mouse click to specified position.
	 * 
	 * @param button
	 *            Mouse key index. Only one of: MOUSE_LEFT, MOUSE_RIGHT,
	 *            MOUSE_MIDDLE.
	 * @param position
	 *            specify a position where the mouse will click on.
	 * @param absolute
	 *            whether the specified position is a absolute position.
	 */
	public static void mouseClick(int button, Point position, boolean absolute) {
		int flag = 0;
		if (absolute)
			flag = Win32.MOUSEEVENTF_ABSOLUTE;
		switch (button) {
		case MOUSE_LEFT:
			flag |= (Win32.MOUSEEVENTF_LEFTDOWN | Win32.MOUSEEVENTF_LEFTUP);
			break;
		case MOUSE_RIGHT:
			flag |= (Win32.MOUSEEVENTF_RIGHTDOWN | Win32.MOUSEEVENTF_RIGHTUP);
			break;
		case MOUSE_MIDDLE:
			flag |= (Win32.MOUSEEVENTF_MIDDLEDOWN | Win32.MOUSEEVENTF_MIDDLEUP);
			break;
		}
		Extension.Mouse_Event(flag, position.x, position.y, 0, 0);
	}

	/**
	 * Emulate mouse double click to specified position.
	 * 
	 * @param button
	 *            Mouse key index. Only one of: MOUSE_LEFT, MOUSE_RIGHT,
	 *            MOUSE_MIDDLE.
	 * @param position
	 *            specify a position where the mouse will click on.
	 * @param absolute
	 *            whether the specified position is a absolute position.
	 */
	public static void mouseDoubleClick(int button, Point position,
			boolean absolute) {
		mouseClick(button, position, absolute);
		mouseClick(button, position, absolute);
	}

	/**
	 * Emulate mouse down event at the specified position.
	 * 
	 * @param button
	 *            Mouse key index. Only one of: MOUSE_LEFT, MOUSE_RIGHT,
	 *            MOUSE_MIDDLE.
	 * @param position
	 *            specify a position where the mouse down event will be created.
	 * @param absolute
	 *            whether the specified position is a absolute position.
	 */
	public static void mouseDown(int button, Point position, boolean absolute) {
		int flag = 0;
		if (absolute)
			flag = Win32.MOUSEEVENTF_ABSOLUTE;
		switch (button) {
		case MOUSE_LEFT:
			flag |= Win32.MOUSEEVENTF_LEFTDOWN;
			break;
		case MOUSE_RIGHT:
			flag |= Win32.MOUSEEVENTF_RIGHTDOWN;
			break;
		case MOUSE_MIDDLE:
			flag |= Win32.MOUSEEVENTF_MIDDLEDOWN;
			break;
		}
		if (flag != 0)
			Extension.Mouse_Event(flag, position.x, position.y, 0, 0);
	}

	/**
	 * Emulate mouse up event at the specified position.
	 * 
	 * @param button
	 *            Mouse key index. Only one of: MOUSE_LEFT, MOUSE_RIGHT,
	 *            MOUSE_MIDDLE.
	 * @param position
	 *            specify a position where the mouse up event will be created.
	 * @param absolute
	 *            whether the specified position is a absolute position.
	 */
	public static void mouseUp(int button, Point position, boolean absolute) {
		int flag = 0;
		if (absolute)
			flag = Win32.MOUSEEVENTF_ABSOLUTE;
		switch (button) {
		case MOUSE_LEFT:
			flag |= Win32.MOUSEEVENTF_LEFTUP;
			break;
		case MOUSE_RIGHT:
			flag |= Win32.MOUSEEVENTF_RIGHTUP;
			break;
		case MOUSE_MIDDLE:
			flag |= Win32.MOUSEEVENTF_MIDDLEUP;
			break;
		}
		if (flag != 0)
			Extension.Mouse_Event(flag, position.x, position.y, 0, 0);
	}

	/**
	 * Emulate mouse wheel event.
	 * 
	 * @param scrollSteps
	 *            specify the wheel scroll steps.
	 * @param downward
	 *            set true if downward, otherwise set false
	 */
	public static void mouseWheel(int scrollSteps, boolean downward) {
		int data = Win32.WHEEL_DELTA * scrollSteps;
		if (downward)
			data *= -1;
		Extension.Mouse_Event(Win32.MOUSEEVENTF_WHEEL, 0, 0, data, 0);
	}
}
