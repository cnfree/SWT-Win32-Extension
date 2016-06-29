package org.sf.feeling.swt.win32.extension.example;

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
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Shell;
import org.sf.feeling.swt.win32.extension.graphics.ImageRegion;
import org.sf.feeling.swt.win32.extension.shell.listener.WindowMoveListener;
import org.sf.feeling.swt.win32.extension.widgets.CMenu;
import org.sf.feeling.swt.win32.extension.widgets.CMenuItem;
import org.sf.feeling.swt.win32.extension.widgets.PopupMenu;
import org.sf.feeling.swt.win32.extension.widgets.listener.MenuAdapter;

public class HelloWorld
{
	private Shell shell;

	public HelloWorld(Shell parent)
	{
		shell = new Shell(parent, SWT.NO_TRIM | SWT.ON_TOP);
		FontData[] fontDatas = Display.getCurrent().getFontList(null, true);
		FontData fontData = Display.getDefault().getSystemFont().getFontData()[0];
		for (int i = 0; i < fontDatas.length; i++)
		{
			if (fontDatas[i].getName().equals("Courier New"))
			{
				fontData = fontDatas[i];
				break;
			}
		}
		fontData.setHeight(48);
		fontData.setStyle(SWT.BOLD);
		Font font = new Font(shell.getDisplay(), fontData);
		GC tempGC = new GC(shell);
		tempGC.setFont(font);
		Point point = tempGC.textExtent("Hello ");
		int helloWidth = point.x;
		int worldWidth = tempGC.textExtent("World").x;
		tempGC.dispose();

		Image image = new Image(shell.getDisplay(), helloWidth + worldWidth, point.y);
		GC gc = new GC(image);
		gc.setFont(font);
		gc.setForeground(Display.getDefault().getSystemColor(SWT.COLOR_RED));
		gc.drawText("Hello ", 0, 0);
		gc.setForeground(Display.getDefault().getSystemColor(SWT.COLOR_BLUE));
		gc.drawText("World", helloWidth, 0);
		gc.dispose();

		shell.setSize(helloWidth + worldWidth, point.y);

		shell.setRegion(ImageRegion.calculateControlGraphicsPath(image, image.getImageData()
				.getPixel(0, 0)));
		WindowMoveListener l = new WindowMoveListener(shell, false);
		shell.addListener(SWT.MouseDown, l);
		shell.addListener(SWT.MouseUp, l);
		shell.addListener(SWT.MouseMove, l);
		shell.setLayout(new GridLayout());
		shell.setBackgroundImage(image);
		CMenu menu = new CMenu();
		CMenuItem item = new CMenuItem(" &Close ", SWT.NONE);
		menu.addItem(item);
		final PopupMenu popupMenu = new PopupMenu(shell, Example.getInstance().getMenuBar()
				.getTheme());
		popupMenu.setMenu(menu);
		item.addSelectionListener(new SelectionAdapter()
		{
			public void widgetSelected(SelectionEvent event)
			{
				shell.close();
				shell.dispose();
			}
		});
		menu.addMenuListener(new MenuAdapter()
		{

			public void menuShown(Event e)
			{
				popupMenu.setTheme(Example.getInstance().getMenuBar().getTheme());
				popupMenu.refresh();

			}

		});

		Rectangle bounds = parent.getBounds();
		Rectangle rect = shell.getBounds();
		int x = bounds.x + (bounds.width - rect.width) / 2;
		int y = bounds.y + (bounds.height - rect.height) / 2;
		shell.setLocation(x, y);

	}

	public void open()
	{
		shell.setVisible(true);
	}

	public void close()
	{
		if (shell != null && !shell.isDisposed()) shell.dispose();
	}

	public Shell getShell()
	{
		return shell;
	}
}
