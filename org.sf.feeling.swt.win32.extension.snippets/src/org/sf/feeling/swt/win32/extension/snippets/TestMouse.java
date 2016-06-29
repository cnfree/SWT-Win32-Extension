package org.sf.feeling.swt.win32.extension.snippets;

import org.eclipse.swt.graphics.Point;
import org.sf.feeling.swt.win32.extension.io.Mouse;

public class TestMouse {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		for (int i = 0; i < 1000; i++) {
			Mouse.mouseMove(new Point(1, 0), false);
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
