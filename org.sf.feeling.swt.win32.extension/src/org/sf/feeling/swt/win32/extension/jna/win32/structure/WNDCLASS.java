/*******************************************************************************
 * Copyright (c) 2011 cnfree.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  cnfree  - initial API and implementation
 *******************************************************************************/
package org.sf.feeling.swt.win32.extension.jna.win32.structure;
import org.sf.feeling.swt.win32.extension.jna.Native;
import org.sf.feeling.swt.win32.extension.jna.datatype.LONG;
import org.sf.feeling.swt.win32.extension.jna.datatype.win32.Structure;
import org.sf.feeling.swt.win32.extension.jna.exception.NativeException;
import org.sf.feeling.swt.win32.extension.jna.ptr.NullPointer;
import org.sf.feeling.swt.win32.extension.jna.ptr.Pointer;
import org.sf.feeling.swt.win32.extension.jna.ptr.memory.GlobalMemoryBlock;
import org.sf.feeling.swt.win32.extension.jna.ptr.memory.MemoryBlockFactory;


public class WNDCLASS extends Structure
{
	public static final int CS_VREDRAW = 0x00000001;
	public static final int CS_HREDRAW = 0x00000002;
	public static final int CS_KEYCVTWINDOW = 0x00000004;
	public static final int CS_DBLCLKS = 0x00000008;
	public static final int CS_OWNDC = 0x00000020;
	public static final int CS_CLASSDC = 0x00000040;
	public static final int CS_PARENTDC = 0x00000080;
	public static final int CS_NOKEYCVT = 0x00000100;
	public static final int CS_NOCLOSE = 0x00000200;
	public static final int CS_SAVEBITS = 0x00000800;
	public static final int CS_BYTEALIGNCLIENT = 0x00001000;
	public static final int CS_BYTEALIGNWINDOW = 0x00002000;
	public static final int CS_GLOBALCLASS = 0x00004000;
	public static final int CS_IME = 0x00010000;
	public static final int CS_DROPSHADOW = 0x00020000;
	
	private LONG style = new LONG(0);
	private LONG lpfnWndProc = new LONG(0);
	private int cbClsExtra = 0;
	private int cbWndExtra = 0;
	private LONG hInstance = new LONG(0);
	private LONG hIcon = new LONG(0);
	private LONG hCursor = new LONG(0);
	private LONG hbrBackground = new LONG(0);
	private String lpszMenuName = null;
	private String lpszClassName = null;
	
	private final Pointer lpszMenuNamePointer;
	private final Pointer lpszClassNamePointer;
	
	public WNDCLASS() throws NativeException
	{
		this((WNDCLASS)null);
	}
	protected WNDCLASS(WNDCLASS lValue) throws NativeException
	{
		super(lValue);
		lpszMenuNamePointer = new Pointer(MemoryBlockFactory.createMemoryBlock(256));
		lpszClassNamePointer = new Pointer(MemoryBlockFactory.createMemoryBlock(256));
	}
	
	private void toPointer() throws NativeException
	{
		
		offset = 0;
		offset += pointer.setIntAt(offset, style.getValue());
		offset += pointer.setIntAt(offset, lpfnWndProc.getValue());
		offset += pointer.setIntAt(offset, cbClsExtra);
		offset += pointer.setIntAt(offset, cbWndExtra);
		offset += pointer.setIntAt(offset, hInstance.getValue());
		offset += pointer.setIntAt(offset, hIcon.getValue());
		offset += pointer.setIntAt(offset, hCursor.getValue());
		offset += pointer.setIntAt(offset, hbrBackground.getValue());
		
		lpszClassNamePointer.zeroMemory();
		if(lpszClassName != null)
		{
			lpszClassNamePointer.setStringAt(0, lpszClassName);
		}
		lpszMenuNamePointer.zeroMemory();
		if(lpszMenuName != null)
		{			
			lpszMenuNamePointer.setStringAt(0, lpszMenuName);
		}
		offset += pointer.setIntAt(offset, lpszMenuName == null ? NullPointer.NULL.getPointer() : lpszMenuNamePointer.getPointer());
		offset += pointer.setIntAt(offset, lpszClassName == null ? NullPointer.NULL.getPointer() : lpszClassNamePointer.getPointer());
		offset = 0;
	}
	private void fromPointer() throws NativeException
	{
		style = new LONG(getNextInt());
		lpfnWndProc = new LONG(getNextInt());
		cbClsExtra = getNextInt();
		cbWndExtra = getNextInt();
		hInstance = new LONG(getNextInt());
		hIcon = new LONG(getNextInt());
		hCursor = new LONG(getNextInt());
		hbrBackground = new LONG(getNextInt());
		
		int nextInt = getNextInt();
		if(nextInt != 0)
		{
			lpszMenuName = Native.getMemoryAsString(nextInt, 256);
		}
		
		nextInt = getNextInt();
		if(nextInt != 0)
		{
			lpszClassName = Native.getMemoryAsString(nextInt, 256);
		}
	}
	
	public String toString()
	{
		String s =  "style: "+style+"\n";
		s += "lpfnWndProc: "+lpfnWndProc+"\n";
		s += "cbClsExtra: "+cbClsExtra+"\n";
		s += "cbWndExtra: "+cbWndExtra+"\n";
		s += "hInstance: "+hInstance+"\n";
		s += "hIcon: "+hIcon+"\n";
		s += "hCursor: "+hCursor+"\n";
		s += "hbrBackground: "+hbrBackground+"\n";
		s += "lpszMenuName: "+lpszMenuName+"\n";
		s += "lpszClassName: "+lpszClassName+"\n";
		return s;
	}
	
	public Pointer createPointer() throws NativeException
	{
		if(pointer == null)
		{
			pointer = new Pointer(new GlobalMemoryBlock(getSizeOf()));
		}
		toPointer();
		return pointer;
	}
	
	public int getSizeOf()
	{
		return 40;
	}
	
	public Object getValueFromPointer() throws NativeException
	{
		fromPointer();
		return this;
	}
	
	public WNDCLASS getValue( ) throws NativeException
	{
		return (WNDCLASS)getValueFromPointer();
	}
	
	public int getCbClsExtra()
	{
		return cbClsExtra;
	}
	
	public void setCbClsExtra(int cbClsExtra)
	{
		this.cbClsExtra = cbClsExtra;
	}
	
	public int getCbWndExtra()
	{
		return cbWndExtra;
	}
	
	public void setCbWndExtra(int cbWndExtra)
	{
		this.cbWndExtra = cbWndExtra;
	}
	
	public LONG getHbrBackground()
	{
		return hbrBackground;
	}
	
	public void setHbrBackground(LONG hbrBackground)
	{
		this.hbrBackground = hbrBackground;
	}
	
	public LONG getHCursor()
	{
		return hCursor;
	}
	
	public void setHCursor(LONG cursor)
	{
		hCursor = cursor;
	}
	
	public LONG getHIcon()
	{
		return hIcon;
	}
	
	public void setHIcon(LONG icon)
	{
		hIcon = icon;
	}
	
	public LONG getHInstance()
	{
		return hInstance;
	}
	
	public void setHInstance(LONG instance)
	{
		hInstance = instance;
	}
	
	public LONG getLpfnWndProc()
	{
		return lpfnWndProc;
	}
	
	public void setLpfnWndProc(LONG lpfnWndProc)
	{
		this.lpfnWndProc = lpfnWndProc;
	}
	
	public String getLpszClassName()
	{
		return lpszClassName;
	}
	
	public void setLpszClassName(String lpszClassName)
	{
		this.lpszClassName = lpszClassName;
	}
	
	public String getLpszMenuName()
	{
		return lpszMenuName;
	}
	
	public void setLpszMenuName(String lpszMenuName)
	{
		this.lpszMenuName = lpszMenuName;
	}
	
	public LONG getStyle()
	{
		return style;
	}
	
	public void setStyle(LONG style)
	{
		this.style = style;
	}
	
}
