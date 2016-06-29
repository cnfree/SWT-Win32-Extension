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
package org.sf.feeling.swt.win32.extension.widgets;

import org.eclipse.swt.SWT;
import org.sf.feeling.swt.win32.internal.extension.util.FlagSet;

public class Shortcut extends FlagSet
{
	private static final int MOD_ALT = 0x0001;

	private static final int MOD_CONTROL = 0x0002;

	private static final int MOD_SHIFT = 0x0004;

	private int keyCode;

	public Shortcut(boolean ctrl, boolean shift, boolean alt, int keyCode)
	{
		this(keyCode);
		setControl(ctrl);
		setShift(shift);
		setAlt(alt);
	}

	public Shortcut(boolean ctrl, boolean shift, boolean alt, String key)
	{
		this(parseKeyCode(key));
		setControl(ctrl);
		setShift(shift);
		setAlt(alt);
	}

	private static int parseKeyCode(String key)
	{
		if (key.equalsIgnoreCase("F1")) return SWT.F1;
		if (key.equalsIgnoreCase("F2")) return SWT.F2;
		if (key.equalsIgnoreCase("F3")) return SWT.F3;
		if (key.equalsIgnoreCase("F4")) return SWT.F4;
		if (key.equalsIgnoreCase("F5")) return SWT.F5;
		if (key.equalsIgnoreCase("F6")) return SWT.F6;
		if (key.equalsIgnoreCase("F7")) return SWT.F7;
		if (key.equalsIgnoreCase("F8")) return SWT.F8;
		if (key.equalsIgnoreCase("F9")) return SWT.F9;
		if (key.equalsIgnoreCase("F10")) return SWT.F10;
		if (key.equalsIgnoreCase("F11")) return SWT.F11;
		if (key.equalsIgnoreCase("F12")) return SWT.F12;
		if (key.equalsIgnoreCase("Ins") || key.equalsIgnoreCase("Insert")) return SWT.INSERT;
		if (key.equalsIgnoreCase("Del") || key.equalsIgnoreCase("Delete")) return SWT.DEL;
		if (key.equalsIgnoreCase("Backspace") || key.equalsIgnoreCase("BS")) return SWT.BS;
		if (key.equalsIgnoreCase("Esc")) return SWT.ESC;
		if (key.equalsIgnoreCase("Tab")) return SWT.TAB;
		if (key.equalsIgnoreCase("Enter")) return SWT.CR;
		if (key.equalsIgnoreCase("Home")) return SWT.HOME;
		if (key.equalsIgnoreCase("End")) return SWT.END;
		if (key.equalsIgnoreCase("PageUp")) return SWT.PAGE_UP;
		if (key.equalsIgnoreCase("PageDown")) return SWT.PAGE_DOWN;
		if (key.equalsIgnoreCase("Pause")) return SWT.PAUSE;
		if (key.equalsIgnoreCase("ScrollLock")) return SWT.SCROLL_LOCK;
		if (key.equalsIgnoreCase("NumLock")) return SWT.NUM_LOCK;
		if (key.equalsIgnoreCase("CapsLock")) return SWT.CAPS_LOCK;
		if (key.equalsIgnoreCase("Space")) return ' ';
		if (key.length() > 0) return key.charAt(0);
		else
			return 0;
	}

	public Shortcut(boolean ctrl, boolean shift, boolean alt, char key)
	{
		this((int) key);
		setControl(ctrl);
		setShift(shift);
		setAlt(alt);
	}

	public Shortcut(int keyCode)
	{
		super();
		if (keyCode >= 'a' && keyCode <= 'z') this.keyCode = keyCode - 32;
		else
			this.keyCode = keyCode;
	}

	public Shortcut(String text)
	{
		String[] splits = text.split("\\+");
		if (splits.length > 0)
		{
			String key = splits[splits.length - 1];
			keyCode = parseKeyCode(key);
			for (int i = 0; i < splits.length - 1; i++)
			{
				if ("Ctrl".equalsIgnoreCase(splits[i])) setControl(true);
				else if ("Shift".equalsIgnoreCase(splits[i])) setShift(true);
				else if ("Alt".equalsIgnoreCase(splits[i])) setAlt(true);
			}
		}

	}

	public void setAlt(boolean set)
	{
		setupFlag(MOD_ALT, set);
	}

	public boolean isAlt()
	{
		return contains(MOD_ALT);
	}

	public void setControl(boolean set)
	{
		setupFlag(MOD_CONTROL, set);
	}

	public boolean isControl()
	{
		return contains(MOD_CONTROL);
	}

	public void setShift(boolean set)
	{
		setupFlag(MOD_SHIFT, set);
	}

	public boolean isShift()
	{
		return contains(MOD_SHIFT);
	}

	public int getKeyCode()
	{
		return keyCode;
	}

	public int hashCode()
	{
		return 5 + flag * 7 + keyCode * 13;
	}

	public boolean equals(Object obj)
	{
		if (obj == this) return true;
		if (obj == null) return false;
		if (!(obj instanceof Shortcut)) return false;
		Shortcut temp = (Shortcut) obj;
		if (temp.flag == this.flag && temp.keyCode == this.keyCode) return true;
		return false;
	}

	public String toString()
	{
		StringBuffer buffer = new StringBuffer();
		if (isControl()) buffer.append("Ctrl+");
		if (isShift()) buffer.append("Shift+");
		if (isAlt()) buffer.append("Alt+");
		switch (keyCode)
		{
		case SWT.F1:
			buffer.append("F1");
			break;
		case SWT.F2:
			buffer.append("F2");
			break;
		case SWT.F3:
			buffer.append("F3");
			break;
		case SWT.F4:
			buffer.append("F4");
			break;
		case SWT.F5:
			buffer.append("F5");
			break;
		case SWT.F6:
			buffer.append("F6");
			break;
		case SWT.F7:
			buffer.append("F7");
			break;
		case SWT.F8:
			buffer.append("F8");
			break;
		case SWT.F9:
			buffer.append("F9");
			break;
		case SWT.F10:
			buffer.append("F10");
			break;
		case SWT.F11:
			buffer.append("F11");
			break;
		case SWT.F12:
			buffer.append("F12");
			break;
		case SWT.INSERT:
			buffer.append("Ins");
			break;
		case SWT.DEL:
			buffer.append("Del");
			break;
		case SWT.BS:
			buffer.append("Backspace");
			break;
		case SWT.ESC:
			buffer.append("Esc");
			break;
		case SWT.TAB:
			buffer.append("Tab");
			break;
		case SWT.CR:
			buffer.append("Enter");
			break;
		case SWT.HOME:
			buffer.append("Home");
			break;
		case SWT.END:
			buffer.append("End");
			break;
		case SWT.PAGE_UP:
			buffer.append("PageUp");
			break;
		case SWT.PAGE_DOWN:
			buffer.append("PageDown");
			break;
		case SWT.PAUSE:
			buffer.append("Pause");
			break;
		case SWT.SCROLL_LOCK:
			buffer.append("ScrollLock");
			break;
		case SWT.NUM_LOCK:
			buffer.append("NumLock");
			break;
		case SWT.CAPS_LOCK:
			buffer.append("CapsLock");
			break;
		case ' ':
			buffer.append("Space");
			break;
		case SWT.NONE:
			if (buffer.length() > 0) buffer = buffer.deleteCharAt(buffer.length() - 1);
			break;
		default:
			buffer.append((char) keyCode);
		}
		return buffer.toString().trim();
	}
}
