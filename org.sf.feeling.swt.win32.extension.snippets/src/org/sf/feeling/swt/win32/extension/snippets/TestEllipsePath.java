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

import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.sf.feeling.swt.win32.extension.graphics.GraphicsUtil;

public class TestEllipsePath
{

	public static void main(String[] args)
	{
		final Shell shell = new Shell();
		shell.setSize(150, 150);
		shell.addPaintListener(new PaintListener()
		{

			public void paintControl(PaintEvent e)
			{
				Rectangle rect = new Rectangle((shell.getClientArea().width - 100) / 2, (shell
						.getClientArea().height - 50) / 2, 100, 50);
				e.gc.drawPath(GraphicsUtil.createEllipsePath(new float[] { rect.x, rect.y,
						rect.width, rect.height }));
			}

		});

		shell.open();
		while (!shell.isDisposed())
		{
			if (!Display.getDefault().readAndDispatch()) Display.getDefault().sleep();
		}
		Display.getDefault().dispose();
	}
}
