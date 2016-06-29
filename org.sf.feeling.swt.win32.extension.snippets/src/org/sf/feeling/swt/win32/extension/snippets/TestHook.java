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

import org.sf.feeling.swt.win32.extension.hook.Hook;
import org.sf.feeling.swt.win32.extension.hook.data.HookData;
import org.sf.feeling.swt.win32.extension.hook.data.KeyboardHookData;
import org.sf.feeling.swt.win32.extension.hook.data.MouseHookData;
import org.sf.feeling.swt.win32.extension.hook.listener.HookEventListener;

public class TestHook
{

	public static void main( String args[] )
	{

		Object lock = new Object( );
		Hook.KEYBOARD.addListener( lock, new HookEventListener( ) {

			public void acceptHookData( HookData hookData )
			{
				if ( hookData != null )
				{
					System.out.println( "VirtualKeyCode ="
							+ ( (KeyboardHookData) hookData ).getVirtualKeyCode( ) );
					System.err.println( "ScanCode = "
							+ ( (KeyboardHookData) hookData ).getScanCode( ) );
				}
			}

		} );

		Hook.MOUSE.addListener( lock, new HookEventListener( ) {

			public void acceptHookData( HookData hookData )
			{
				if ( hookData != null )
				{
					System.out.println( "Mouse Point X = "
							+ ( (MouseHookData) hookData ).getPointX( ) );
					System.err.println( "Mouse Point Y ="
							+ ( (MouseHookData) hookData ).getPointY( ) );
				}
			}

		} );

		Hook.KEYBOARD.install(lock  );
		Hook.MOUSE.install( lock );

		/*
		 * Hook.JOURNALRECORD.install(); Hook.JOURNALRECORD.addListener(new
		 * HookEventListener() { public void acceptHookData(HookData hookData) {
		 * if (hookData != null) { JournalRecordHookData data =
		 * (JournalRecordHookData) hookData; if (data.getMessage() ==
		 * Extension.WM_KEYUP) { int j =
		 * Extension.GetAsyncKeyState(Extension.VK_SHIFT); int i =
		 * Extension.MapVirtualKey(data.getParamL(), 2); char ch = (char) (i &
		 * 0xFF); if (j != 0) System.out.println(ch +
		 * " Key Up with Shift Down"); else System.out.println(ch +
		 * " Key Up without Shift Down"); } if (data.getMessage() ==
		 * Extension.WM_KEYDOWN) { int j =
		 * Extension.GetAsyncKeyState(Extension.VK_SHIFT); int i =
		 * Extension.MapVirtualKey(data.getParamL(), 2); char ch = (char) (i &
		 * 0xFF); if (j != 0) System.out .println(ch +
		 * " Key Down with Shift Down"); else System.out.println(ch +
		 * " Key Down without Shift Down"); } }
		 * 
		 * } });
		 */
	}

}
