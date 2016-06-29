package org.sf.feeling.swt.win32.extension.snippets;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.sf.feeling.swt.win32.extension.hook.JournalRecordHook;
import org.sf.feeling.swt.win32.extension.hook.Keyboard_LLHook;
import org.sf.feeling.swt.win32.extension.hook.Mouse_LLHook;
import org.sf.feeling.swt.win32.extension.hook.data.JournalHookData;
import org.sf.feeling.swt.win32.extension.hook.data.Keyboard_LLHookData;
import org.sf.feeling.swt.win32.extension.hook.data.Mouse_LLHookData;
import org.sf.feeling.swt.win32.extension.hook.interceptor.InterceptorFlag;
import org.sf.feeling.swt.win32.extension.hook.interceptor.JournalHookInterceptor;
import org.sf.feeling.swt.win32.extension.hook.interceptor.Keyboard_LLHookInterceptor;
import org.sf.feeling.swt.win32.extension.hook.interceptor.Mouse_LLHookInterceptor;
import org.sf.feeling.swt.win32.internal.extension.Extension;

public class TestLowLevelHook {
	static final int WH_MOUSE = 7;
	static final int WH_MOUSE_LL = 14;
	static final int WM_LBUTTONDOWN = 0x201;
	static final int WM_RBUTTONDOWN = 0x204;
	static final int WM_MBUTTONDOWN = 0x207;
	static int hMouseHook = 0;

	public static void main(String[] args) {
		new TestLowLevelHook();
		Display display = new Display();
		Shell shell = new Shell(display);
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		// Mouse_LLHook.UnInstallHook();
		// Keyboard_LLHook.UnInstallHook();
		JournalRecordHook.unInstallHook();
		display.dispose();
	}

	public TestLowLevelHook() {
		Mouse_LLHook.addHookInterceptor(new Mouse_LLHookInterceptor() {

			public InterceptorFlag intercept(Mouse_LLHookData hookData) {
				int wParam = hookData.getWParam();
				switch (wParam) {
				case Mouse_LLHookData.WM_LBUTTONDOWN:
					System.out.println("Left Button Down");
				case Mouse_LLHookData.WM_MOUSEMOVE:
					System.out.println("Mouse Move");
				}
				return InterceptorFlag.TRUE;
			}

		});
		// Mouse_LLHook.InstallHook();

		Keyboard_LLHook.addHookInterceptor(new Keyboard_LLHookInterceptor() {

			public InterceptorFlag intercept(Keyboard_LLHookData hookData) {
				int wParam = hookData.getWParam();
				switch (wParam) {
				case Keyboard_LLHookData.WM_KEYDOWN:
					System.out.println("Key Down");
				case Keyboard_LLHookData.WM_SYSKEYDOWN:
					System.out.println("System Key Down");
				}
				return InterceptorFlag.TRUE;
			}

		});
		// Keyboard_LLHook.InstallHook();

		JournalRecordHook
				.addHookInterceptor(new JournalHookInterceptor() {

					public InterceptorFlag intercept(JournalHookData hookData) {
						if (hookData.getMessage() == Extension.WM_KEYDOWN) {
							byte[] keyboard = new byte[256];
							short[] result = new short[1];
							Extension.ToAscii(hookData.getParamL(), hookData
									.getParamH(), keyboard, result, 0);
							System.out.print((char)result[0]);
						}
						return InterceptorFlag.TRUE;
					}

				});
		JournalRecordHook.installHook();
	}
}