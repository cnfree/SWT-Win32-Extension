package org.sf.feeling.swt.win32.extension.widgets;

import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

interface MenuHolder {
	void hideMenu();

	public Shell getShell();
	
	public Control getControl();

	boolean checkMouseDownEvent(Point location);

	CMenu getMenu();
	
	boolean isAcitve();
}
