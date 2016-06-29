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

public class TOKEN_PRIVILEGES extends Structure
{
	
	public static final int TOKEN_ADJUST_PRIVILEGES = 0x00000020;
	public static final int TOKEN_QUERY = 0x00000008;
	public static final int SE_PRIVILEGE_ENABLED = 0x00000002;
	
	public int PrivilegeCount;
	public int ignoredLuid = 0;
	public int TheLuid;
	public int Attributes;
	
	public TOKEN_PRIVILEGES()
	{
		super( );
		try
		{
			createPointer();
		}
		catch (NativeException e)
		{
			throw new RuntimeException(e);
		}
	}
	
	public Object getValueFromPointer() throws NativeException
	{
		return this;
	}
	
	public TOKEN_PRIVILEGES getValue()
	{
		return this;
	}
	
	public Pointer createPointer() throws NativeException
	{
		pointer = new Pointer(MemoryBlockFactory.createMemoryBlock(getSizeOf()));
		return pointer;
	}
	public int getSizeOf()
	{
		return (4+4+4+4);
	}
	public Pointer getPointer()
	{
		try
		{
			pointer.setIntAt(0,PrivilegeCount);
			pointer.setIntAt(4,TheLuid);
			pointer.setIntAt(8,ignoredLuid);
			pointer.setIntAt(12,Attributes);
		}
		catch(NativeException e)
		{
			e.printStackTrace();
		}
		return pointer;
	}
}
