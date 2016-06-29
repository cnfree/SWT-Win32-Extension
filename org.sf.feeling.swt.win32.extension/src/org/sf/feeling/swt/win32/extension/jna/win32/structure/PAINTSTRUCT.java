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

import org.sf.feeling.swt.win32.extension.jna.datatype.AbstractBasicData;
import org.sf.feeling.swt.win32.extension.jna.exception.NativeException;
import org.sf.feeling.swt.win32.extension.jna.ptr.Pointer;
import org.sf.feeling.swt.win32.extension.jna.ptr.memory.MemoryBlockFactory;

/**
 *
 *
 typedef struct tagPAINTSTRUCT {
 HDC  hdc;
 BOOL fErase;
 RECT rcPaint;
 BOOL fRestore;
 BOOL fIncUpdate;
 BYTE rgbReserved[32];
 } PAINTSTRUCT, *PPAINTSTRUCT;
 */
public class PAINTSTRUCT extends AbstractBasicData
{
	public DC hdc;
	public boolean fErase;
	public LRECT rcPaint;
	public boolean fRestore;
	public boolean fIncUpdate;
	public byte rgbReserved[] = new byte[32];
	
	/** Creates a new instance of PAINTSTRUCT */
	public PAINTSTRUCT()
	{
		super(null);
		try
		{
			createPointer();
		}
		catch (NativeException e)
		{
			throw new RuntimeException( e );
		}
	}
	public Object getValueFromPointer() throws NativeException
	{
		offset = 0;
		hdc = new DC(getNextInt());
		fErase = getNextByte() == (byte)1 ? true : false;
		rcPaint = new LRECT();
		rcPaint.setLeft(getNextInt());
		rcPaint.setTop(getNextInt());
		rcPaint.setRight(getNextInt());
		rcPaint.setBottom(getNextInt());
		
		fRestore = getNextByte() == (byte)1 ? true : false;
		fIncUpdate = getNextByte() == (byte)1 ? true : false;
		
		if(rgbReserved != null)
		{
			for(int i = 0; i < rgbReserved.length; i++)
			{
				rgbReserved[i] = getNextByte();
			}
		}
		offset = 0;
		return this;
	}
	
	public Pointer createPointer() throws NativeException
	{
		if(pointer == null)
		{
			pointer = new Pointer(MemoryBlockFactory.createMemoryBlock(sizeOf()));
		}
		return pointer;
	}
	/**
	 * Method getValue
	 *
	 * @return   a T
	 *
	 */
	public PAINTSTRUCT getValue()
	{
		try
		{
			pointer.setIntAt(0, hdc != null ? hdc.getValue() : 0);
			pointer.setByteAt(4, fErase ? (byte)1 : (byte)0);
			pointer.setIntAt(5, rcPaint.getLeft());
			pointer.setIntAt(9, rcPaint.getTop());
			pointer.setIntAt(13, rcPaint.getRight());
			pointer.setIntAt(17, rcPaint.getBottom());
			pointer.setByteAt(21, fRestore ? (byte)1 : (byte)0);
			pointer.setByteAt(22, fIncUpdate ? (byte)1 : (byte)0);
			
			if(rgbReserved != null)
			{
				for(int i = 0; i < rgbReserved.length; i++)
				{
					pointer.setByteAt(23+i, rgbReserved[i]);
				}
			}
		}
		catch (NativeException ex)
		{
			ex.printStackTrace();
		}
		
		return this;
	}
	
	/**
	 * Method getSizeOf
	 * @return   the size of this data
	 */
	public int getSizeOf()
	{
		return sizeOf();
	}
	
	public static int sizeOf()
	{
		return 55;
	}
	
	
}
