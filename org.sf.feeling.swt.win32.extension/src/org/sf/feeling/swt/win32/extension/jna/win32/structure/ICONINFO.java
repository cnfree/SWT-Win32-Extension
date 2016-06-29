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

import org.sf.feeling.swt.win32.extension.jna.datatype.win32.Structure;
import org.sf.feeling.swt.win32.extension.jna.exception.NativeException;
import org.sf.feeling.swt.win32.extension.jna.ptr.Pointer;
import org.sf.feeling.swt.win32.extension.jna.ptr.memory.MemoryBlockFactory;
import org.sf.feeling.swt.win32.extension.jna.win32.Gdi32;

public class ICONINFO extends Structure
{
	
	/*
		BOOL fIcon;
		DWORD xHotspot;
		DWORD yHotspot;
		HBITMAP hbmMask;
		HBITMAP hbmColor;
	 */
	
	/** Creates a new instance of ICONINFO */
	public ICONINFO()
	{
		super();
		try
		{
			createPointer();
		}
		catch (NativeException e)
		{
			throw new RuntimeException( e );
		}
	}
	
	public void dispose() throws NativeException, IllegalAccessException
	{
		Gdi32.DeleteObject(getBitmapMask());
		Gdi32.DeleteObject(getBitmapColor());
		getPointer().dispose();
	}
	
	public boolean isIcon() throws NativeException
	{
		return (pointer.getAsByte(0) != 0);
	}
	public int getBitmapMask() throws NativeException
	{
		return pointer.getAsInt(12);
	}
	public int getBitmapColor() throws NativeException
	{
		return pointer.getAsInt(16);
	}
	
	public Pointer createPointer() throws NativeException
	{
		pointer = new Pointer(MemoryBlockFactory.createMemoryBlock(getSizeOf()));
		return pointer;
	}
	
	public int getSizeOf()
	{
		return 20;
	}
	
	public Object getValueFromPointer()
	{
		return this;
	}
	
	public ICONINFO getValue()
	{
		return this;
	}
	
}
