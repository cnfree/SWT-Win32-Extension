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
import org.sf.feeling.swt.win32.extension.jna.ptr.memory.HeapMemoryBlock;
import org.sf.feeling.swt.win32.extension.jna.ptr.memory.MemoryBlockFactory;

public class BITMAP extends Structure
{
    
    /** Creates a new instance of BITMAP */
    public BITMAP()
    {
        super();
        try
        {
            createPointer();
        }
        catch (NativeException e)
        {
            throw new RuntimeException(e);
        }
    }
    
    public Pointer createBitmapBuffer() throws NativeException
    {
        return new Pointer(new HeapMemoryBlock(getNeededBufferSize()));
    }
    
    public int getWidth() throws NativeException
    {
        return pointer.getAsInt(4);
    }
    public int getHeight() throws NativeException
    {
        return pointer.getAsInt(8);
    }
    public int getNeededBufferSize() throws NativeException
    {
        return getWidth()*getHeight()*4;
    }
    public int getRealBitmapSize() throws NativeException
    {
        return getWidth()*getHeight();
    }
    public Pointer createPointer() throws NativeException
    {
        pointer = new Pointer(MemoryBlockFactory.createMemoryBlock(getSizeOf()));
        return pointer;
    }
    
    public int getSizeOf()
    {
        return sizeOf();
    }
    public static int sizeOf()
    {
        return 24;
    }
    
    public Object getValueFromPointer()
    {
        return this;
    }
    
    public BITMAP getValue()
    {
        return this;
    }
    
}
