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

/**
 * <pre>
 * typedef struct tagBITMAPINFOHEADER{ 
 * 	DWORD biSize; 
 * 	LONG biWidth; 
 * 	LONG biHeight; 
 * 	WORD biPlanes; 
 * 	WORD biBitCount; 
 * 	DWORD biCompression; 
 * 	DWORD biSizeImage; 
 * 	LONG biXPelsPerMeter; 
 * 	LONG biYPelsPerMeter; 
 * 	DWORD biClrUsed;
 * 	DWORD biClrImportant; 
 * } BITMAPINFOHEADER, *PBITMAPINFOHEADER;
 * <pre>
 */
public class BITMAPINFOHEADER extends Structure
{
	/** Creates a new instance of BITMAPINFOHEADER */
	public BITMAPINFOHEADER( )
	{
		super( );
		try
		{
			createPointer( );
		}
		catch ( NativeException e )
		{
			throw new RuntimeException( e );
		}
	}

	public Pointer createPointer( ) throws NativeException
	{
		pointer = new Pointer( MemoryBlockFactory.createMemoryBlock( sizeOf( ) ) );
		return pointer;
	}

	public int getSizeOf( )
	{
		return sizeOf( );
	}

	public Object getValueFromPointer( )
	{
		return this;
	}

	public BITMAPINFOHEADER getValue( )
	{
		return this;
	}

	public static int sizeOf( )
	{
		return 40;
	}

}
