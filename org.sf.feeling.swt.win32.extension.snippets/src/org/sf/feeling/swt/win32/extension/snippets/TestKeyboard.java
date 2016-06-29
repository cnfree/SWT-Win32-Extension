package org.sf.feeling.swt.win32.extension.snippets;

import org.sf.feeling.swt.win32.extension.Win32;
import org.sf.feeling.swt.win32.extension.io.Keyboard;

public class TestKeyboard {
	public static void main(String[] args) {
		Keyboard.keyDown(Win32.VK_LWIN, 0, false);
		Keyboard.keyDown('M', 0, false);
		Keyboard.keyUp('M', 0, false);
		Keyboard.keyUp(Win32.VK_LWIN, 0, false);
	}
}
