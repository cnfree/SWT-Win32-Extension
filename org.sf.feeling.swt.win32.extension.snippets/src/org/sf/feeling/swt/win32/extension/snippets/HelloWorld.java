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
package org.sf.feeling.swt.win32.extension.snippets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;
import org.sf.feeling.swt.win32.extension.graphics.ImageRegion;
import org.sf.feeling.swt.win32.extension.shell.listener.WindowMoveListener;

public class HelloWorld {

	public static void main(String[] args) {
		final Shell shell = new Shell(SWT.NO_TRIM | SWT.ON_TOP);
		FontData fontData = shell.getFont().getFontData()[0];
		fontData.setHeight(48);
		fontData.setStyle(SWT.BOLD);
		Font font = new Font(shell.getDisplay(), fontData);
		GC tempGC = new GC(shell);
		tempGC.setFont(font);
		Point point = tempGC.textExtent("Hello ");
		int helloWidth = point.x;
		int worldWidth = tempGC.textExtent("World").x;
		tempGC.dispose();

		Image image = new Image(shell.getDisplay(), helloWidth + worldWidth,
				point.y);
		GC gc = new GC(image);
		gc.setFont(font);
		gc.setForeground(Display.getDefault().getSystemColor(SWT.COLOR_RED));
		gc.drawText("Hello ", 0, 0);
		gc.setForeground(Display.getDefault().getSystemColor(SWT.COLOR_BLUE));
		gc.drawText("World", worldWidth, 0);
		gc.dispose();

		shell.setSize(helloWidth + worldWidth, point.y);

		shell.setRegion(ImageRegion.calculateControlGraphicsPath(image, image
				.getImageData().getPixel(0, 0)));
		WindowMoveListener l = new WindowMoveListener(shell, false);
		shell.addListener(SWT.MouseDown, l);
		shell.addListener(SWT.MouseUp, l);
		shell.addListener(SWT.MouseMove, l);
		shell.setLayout(new GridLayout());
		shell.setBackgroundImage(image);
		Menu menu = new Menu(shell);
		MenuItem item = new MenuItem(menu, SWT.NONE);
		shell.setMenu(menu);
		item.setText(" Close ");
		item.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				shell.close();
				shell.dispose();
			}
		});

		Monitor primary = Display.getDefault().getPrimaryMonitor();
		Rectangle bounds = primary.getBounds();
		Rectangle rect = shell.getBounds();
		int x = bounds.x + (bounds.width - rect.width) / 2;
		int y = bounds.y + (bounds.height - rect.height) / 2;
		shell.setLocation(x, y);
		shell.open();
		while (!shell.isDisposed()) {
			if (!Display.getDefault().readAndDispatch())
				Display.getDefault().sleep();
		}
		image.dispose();
		Display.getDefault().dispose();
	}
}
