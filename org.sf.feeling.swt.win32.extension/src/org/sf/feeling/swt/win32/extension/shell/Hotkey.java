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
package org.sf.feeling.swt.win32.extension.shell;

import java.util.Random;
import java.util.Vector;

import org.sf.feeling.swt.win32.extension.Win32;
import org.sf.feeling.swt.win32.extension.jna.Callback;
import org.sf.feeling.swt.win32.extension.jna.Native;
import org.sf.feeling.swt.win32.extension.jna.datatype.LONG;
import org.sf.feeling.swt.win32.extension.jna.datatype.UINT;
import org.sf.feeling.swt.win32.extension.jna.datatype.win32.LPARAM;
import org.sf.feeling.swt.win32.extension.jna.datatype.win32.WPARAM;
import org.sf.feeling.swt.win32.extension.jna.exception.NativeException;
import org.sf.feeling.swt.win32.extension.jna.win32.Kernel32;
import org.sf.feeling.swt.win32.extension.jna.win32.User32;
import org.sf.feeling.swt.win32.extension.jna.win32.structure.HWND;
import org.sf.feeling.swt.win32.extension.jna.win32.structure.MSG;
import org.sf.feeling.swt.win32.extension.jna.win32.structure.MSG.WindowsConstants;

/**
 *
 *<pre>
 * EXAMPLE USAGE 1:
 *
 - Extend the Hotkey-class and overwrite "public int callback(long[] values)"
 - Don't forget to return super.callback(values) in case your HOTKEY was not called, else return 0 (see example 2).
 *
 final MyHotkey hotkey = new MyHotkey(Hotkey.MOD_CONTROL + Hotkey.MOD_ALT, 0x77);
 // register your hotkey
 h.registerHotkey();
 
 // somewhere else unregister the hotkey
 h.unregisterHotkey();
 
 NOTE:If you do not overwrite "public int callback(long[] values)" nothing will happen...
 </pre>
 */


public class Hotkey implements Callback, Runnable
{
	
	public static final int MOD_ALT = 0x1;      //Alt
	public static final int MOD_SHIFT = 0x4;    //Shift
	public static final int MOD_CONTROL = 0x2;	//CTRL
	public static final int MOD_WIN = 0x8;       //Windows
	
	private HWND messageWindow = null;
	private static final Vector hotkeys = new Vector();
	
	private int myAddress = -1;
	private int iAtom = 0;
	private int prevWndProc = 0;
	private int modifiers = -1;
	private int key = -1;
	private Callback callback = null;
	private boolean isRegistered = false;
	private Thread thread = null;
	private boolean loopStarted = false;
	
	static
	{
		Runtime.getRuntime().addShutdownHook(new Thread()
		{
			public void run()
			{
				try
				{
					unregisterAllHotkeys();
					//Native.unLoadAllLibraries();
				}
				catch (NativeException ex)
				{
					ex.printStackTrace();
				}
				catch (IllegalAccessException ex)
				{
					ex.printStackTrace();
				}
			}
		});
	}
	
	/** Creates a new instance of HotkeyCallback */
	public Hotkey(int modifiers, int key)
	{
		this.modifiers = modifiers;
		this.key = key;
	}
	public Hotkey(int modifiers, int key, Callback c)
	{
		this(modifiers, key);
		this.callback = c;
	}
	
	public void run()
	{
		try
		{
			createNativeWindow();
			if(callback == null)
			{
				setCallback(this);
			}
			prevWndProc = User32.SetWindowLong(messageWindow, WindowsConstants.GWL_WNDPROC, new LONG(callback.getCallbackAddress()));
			registerHotkeyInternal();
			startNativeMessageLoop();
		}
		catch (IllegalAccessException ex)
		{
			ex.printStackTrace();
		}
		catch (NativeException ex)
		{
			ex.printStackTrace();
		}
	}
	
	private void stopNativeMessageLoop()
	{
		loopStarted = false;
		try
		{
			User32.PostMessage(messageWindow, new UINT(Win32.WM_QUIT), new WPARAM(iAtom), new LPARAM(0));
		}
		catch (IllegalAccessException ex)
		{
			ex.printStackTrace();
		}
		catch (NativeException ex)
		{
			ex.printStackTrace();
		}
	}
	// This MUST be the last call in your public void main()-method as it only (maybe) returns when you call stopMessageLoop() somewhere else!
	private final void startNativeMessageLoop() throws NativeException, IllegalAccessException
	{
		if(loopStarted)
		{
			return;
		}
		
		loopStarted = true;
		try
		{
			MSG Msg = new MSG();
			while(User32.GetMessage(Msg, messageWindow, 0, 0) > 0)
			{
				User32.TranslateMessage(Msg);
				User32.DispatchMessage(Msg);
				Msg.getPointer().zeroMemory();
			}
		}
		finally
		{
			loopStarted = false;
		}
	}
	
	public static Vector getInstalledHotkeys()
	{
		return hotkeys;
	}
	public static final void unregisterAllHotkeys() throws NativeException, IllegalAccessException
	{
		for(int i = hotkeys.size()-1; i>=0; i--)
		{
			final Hotkey k = (Hotkey)hotkeys.get(i);
			if(k.isRegistered())
			{
				k.unregisterHotkey();
			}
		}
	}
	public int getModifiers()
	{
		return modifiers;
	}
	public int getKey()
	{
		return key;
	}
	public int getPrevWndProc()
	{
		return prevWndProc;
	}
	public int getAtom()
	{
		return iAtom;
	}
	public boolean isRegistered()
	{
		return isRegistered;
	}
	public HWND getNativeHWND()
	{
		return messageWindow;
	}
	// this native window is needed for processing the native window events
	private void createNativeWindow() throws NativeException, IllegalAccessException
	{
		messageWindow = new HWND(User32.CreateWindowEx(0, "Button", ""+new Random().nextInt(Integer.MAX_VALUE), 0, 0, 0, 0, 0, 0, 0, 0, 0));
	}
	private void destroyNativeWindow() throws NativeException, IllegalAccessException
	{
		if(messageWindow != null)
		{
			User32.DestroyWindow(messageWindow);
		}
		messageWindow = null;
	}
	public final void registerHotkey()
	{
		thread = new Thread(this);
		thread.start();
	}
	private final boolean registerHotkeyInternal() throws NativeException, IllegalAccessException
	{
		iAtom = Kernel32.GlobalAddAtom("HotKey"+new Random().nextInt(Integer.MAX_VALUE));
		if(iAtom != 0)
		{
			isRegistered = User32.RegisterHotKey(messageWindow, iAtom, modifiers, key);
			
			if(isRegistered)
			{
				//prevWndProc = User32.SetWindowLong(messageWindow, MSG.WindowsConstants.GWL_WNDPROC, new LONG(callback.getCallbackAddress()));
				//if(prevWndProc != 0) {
				hotkeys.add(this);
				return true;
				//}
			}
			unregisterHotkey();
		}
		return false;
	}
	public final void unregisterHotkey() throws NativeException, IllegalAccessException
	{
		try
		{
			stopNativeMessageLoop();
			if(messageWindow != null)
			{
				User32.UnregisterHotKey(messageWindow, iAtom);
				User32.SetWindowLong(messageWindow, WindowsConstants.GWL_WNDPROC, new LONG(prevWndProc));
			}
			Kernel32.GlobalDeleteAtom(iAtom);
		}
		finally
		{
			try
			{
				destroyNativeWindow();
			}
			catch (IllegalAccessException ex)
			{
				ex.printStackTrace();
			}
			catch (NativeException ex)
			{
				ex.printStackTrace();
			}
			isRegistered = false;
			iAtom = 0;
			prevWndProc = 0;
			hotkeys.remove(this);
		}
	}
	
	public void setCallback(Callback c) throws NativeException, IllegalAccessException
	{
		if(c == null)
		{
			return;
		}
		// unregister old callback if existing
		if(callback != null)
		{
			if(messageWindow != null)
			{
				User32.SetWindowLong(messageWindow, WindowsConstants.GWL_WNDPROC, new LONG(prevWndProc));
			}
			Native.releaseCallback(callback);
		}
		
		this.callback = c;
	}
	public int callback(long[] values)
	{
		try
		{
			return User32.CallWindowProc(getPrevWndProc(),(int)values[0], (int)values[1], (int)values[2], (int)values[3]);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return 1;
	}
	public int getCallbackAddress() throws NativeException
	{
		if(myAddress == -1)
		{
			myAddress = Native.createCallback(4, this);
		}
		return myAddress;
	}
	
	public Callback getCallback()
	{
		return callback;
	}
}
