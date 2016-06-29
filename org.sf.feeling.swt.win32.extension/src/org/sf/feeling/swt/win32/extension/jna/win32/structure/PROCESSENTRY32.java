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
import org.sf.feeling.swt.win32.extension.jna.datatype.win32.DWORD;
import org.sf.feeling.swt.win32.extension.jna.datatype.win32.Structure;
import org.sf.feeling.swt.win32.extension.jna.exception.NativeException;
import org.sf.feeling.swt.win32.extension.jna.ptr.Pointer;

/**
 *
 * typedef struct tagPROCESSENTRY32 {
 DWORD dwSize;
 DWORD cntUsage;
 DWORD th32ProcessID;
 ULONG_PTR th32DefaultHeapID;
 DWORD th32ModuleID;
 DWORD cntThreads;
 DWORD th32ParentProcessID;
 LONG pcPriClassBase;
 DWORD dwFlags;
 TCHAR szExeFile[MAX_PATH];
 } PROCESSENTRY32,
 */
public class PROCESSENTRY32 extends Structure
{
	public DWORD dwSize = new DWORD(sizeOf());
	public DWORD cntUsage = new DWORD(0);
	public DWORD th32ProcessID = new DWORD(0);
	public LONG th32DefaultHeapID = new LONG(0);
	public DWORD th32ModuleID = new DWORD(0);
	public DWORD cntThreads = new DWORD(0);
	public DWORD th32ParentProcessID = new DWORD(0);
	public LONG pcPriClassBase = new LONG(0);
	public DWORD dwFlags = new DWORD(0);
	public String szExeFile = "";
		
	/** Creates a new instance of PROCESSENTRY32 */
	public PROCESSENTRY32() throws NativeException
	{
		super();
		createPointer();
		resetPointer();		
	}
	public PROCESSENTRY32(int nativeAddress) throws NativeException
	{
		this();
		pointer.setMemory(Native.getMemory(nativeAddress, sizeOf()));
		getValueFromPointer();
	}
	
	public void resetPointer() throws NativeException
	{
		pointer.zeroMemory();
		// always set dwSize!
		pointer.setIntAt(0, dwSize.getValue());
	}
	
	public PROCESSENTRY32 getValue()
	{
		try
		{
			pointer.zeroMemory();
			
			int i = -4;
			pointer.setIntAt(i+=4, dwSize.getValue());
			pointer.setIntAt(i+=4, cntUsage.getValue());
			pointer.setIntAt(i+=4, th32ProcessID.getValue());
			pointer.setIntAt(i+=4, th32DefaultHeapID.getValue());
			pointer.setIntAt(i+=4, th32ModuleID.getValue());
			pointer.setIntAt(i+=4, cntThreads.getValue());
			pointer.setIntAt(i+=4, th32ParentProcessID.getValue());
			pointer.setIntAt(i+=4, pcPriClassBase.getValue());
			pointer.setIntAt(i+=4, dwFlags.getValue());
			
			pointer.setStringAt(i+=4, szExeFile);
		}
		catch (NativeException ex)
		{
			ex.printStackTrace();
		}
		return this;
	}
	
	public Pointer createPointer() throws NativeException
	{
		if(pointer == null)
		{
			pointer = Pointer.createPointer(sizeOf());
		}
		return pointer;
	}
	
	public int getSizeOf()
	{
		return sizeOf();
	}
	
	public Object getValueFromPointer() throws NativeException
	{
		offset = 0;
		
		dwSize = new DWORD(getNextInt());
		cntUsage = new DWORD(getNextInt());
		th32ProcessID = new DWORD(getNextInt());
		th32DefaultHeapID = new LONG(getNextInt());
		th32ModuleID = new DWORD(getNextInt());
		cntThreads = new DWORD(getNextInt());
		th32ParentProcessID = new DWORD(getNextInt());
		pcPriClassBase = new LONG(getNextInt());
		dwFlags = new DWORD(getNextInt());
		
		byte[] b = new byte[256];
		for(int i = 0; i < b.length; i++)
		{
			b[i] = getNextByte();
			if(b[i] == 0)
			{
				break;
			}
		}
		szExeFile = new String(b).trim();

		offset = 0;
		return this;
	}
	
	public static int sizeOf()
	{
		return 296;
	}
	
}
