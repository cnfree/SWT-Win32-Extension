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
import org.sf.feeling.swt.win32.extension.jna.datatype.SHORT;
import org.sf.feeling.swt.win32.extension.jna.datatype.UINT;
import org.sf.feeling.swt.win32.extension.jna.datatype.win32.BOOL;
import org.sf.feeling.swt.win32.extension.jna.datatype.win32.Structure;
import org.sf.feeling.swt.win32.extension.jna.exception.NativeException;
import org.sf.feeling.swt.win32.extension.jna.ptr.NullPointer;
import org.sf.feeling.swt.win32.extension.jna.ptr.Pointer;
import org.sf.feeling.swt.win32.extension.jna.ptr.memory.GlobalMemoryBlock;
import org.sf.feeling.swt.win32.extension.jna.ptr.memory.MemoryBlockFactory;

public class SHFILEOPSTRUCT extends Structure
{

	public static final int FO_MOVE = 1;
	public static final int FO_COPY = 2;
	public static final int FO_DELETE = 3;
	public static final int FO_RENAME = 4;

	public static interface FILEOP_FLAGS
	{

		short FOF_MULTIDESTFILES = 1;
		short FOF_CONFIRMMOUSE = 2;
		short FOF_SILENT = 4;
		short FOF_RENAMEONCOLLISION = 8;
		short FOF_NOCONFIRMATION = 16;
		short FOF_WANTMAPPINGHANDLE = 32;
		short FOF_ALLOWUNDO = 64;
		short FOF_FILESONLY = 128;
		short FOF_SIMPLEPROGRESS = 256;
		short FOF_NOCONFIRMMKDIR = 512;
		short FOF_NOERRORUI = 1024;
		short FOF_NOCOPYSECURITYATTRIBS = 2048;
	}

	/**
	 * <pre>
	 * typedef struct _SHFILEOPSTRUCT {
	 *   HWND         hwnd;
	 *   UINT         wFunc;
	 *   LPCTSTR      pFrom;
	 *   LPCTSTR      pTo;
	 *   FILEOP_FLAGS fFlags;
	 *   BOOL         fAnyOperationsAborted;
	 *   LPVOID       hNameMappings;
	 *   LPCTSTR      lpszProgressTitle;
	 * } SHFILEOPSTRUCT, *LPSHFILEOPSTRUCT;
	 * </pre>
	 */

	public SHFILEOPSTRUCT( ) throws NativeException
	{
		super( );
		lpszProgressTitlePointer = new Pointer( MemoryBlockFactory.createMemoryBlock( 256 ) );
		pFromPointer = new Pointer( MemoryBlockFactory.createMemoryBlock( 256 ) );
		pToPointer = new Pointer( MemoryBlockFactory.createMemoryBlock( 256 ) );
	}

	private void toPointer( ) throws NativeException
	{
		pFromPointer.zeroMemory( );
		if ( pFrom != null )
		{
			pFromPointer.setStringAt( 0, pFrom, false );
		}
		pToPointer.zeroMemory( );
		if ( pTo != null )
		{
			pToPointer.setStringAt( 0, pTo, false );
		}
		lpszProgressTitlePointer.zeroMemory( );
		if ( lpszProgressTitle != null )
		{
			lpszProgressTitlePointer.setStringAt( 0, lpszProgressTitle );
		}

		offset = 0;

		offset += pointer.setIntAt( offset, hwnd == null ? 0 : hwnd.getValue( ) );
		offset += pointer.setIntAt( offset,
				wFunc == null ? 0 : wFunc.getValue( ) );
		offset += pointer.setIntAt( offset,
				pFrom == null ? NullPointer.NULL.getPointer( )
						: pFromPointer.getPointer( ) );
		offset += pointer.setIntAt( offset,
				pTo == null ? NullPointer.NULL.getPointer( )
						: pToPointer.getPointer( ) );
		offset += pointer.setShortAt( offset,
				fFlags == null ? 0 : fFlags.getValue( ) );
		offset += pointer.setIntAt( offset, fAnyOperationsAborted == null ? 0
				: fAnyOperationsAborted.getValue( ) );
		offset += pointer.setIntAt( offset, NullPointer.NULL.getPointer( ) );
		offset += pointer.setIntAt( offset,
				lpszProgressTitle == null ? NullPointer.NULL.getPointer( )
						: lpszProgressTitlePointer.getPointer( ) );
		offset = 0;
	}

	public HWND hwnd;
	public UINT wFunc;
	public String pFrom;
	public String pTo;
	public SHORT fFlags;
	public BOOL fAnyOperationsAborted;
	public UINT hNameMappings;
	public String lpszProgressTitle;

	public Pointer lpszProgressTitlePointer;
	public Pointer pFromPointer;
	public Pointer pToPointer;

	public int getSizeOf( )
	{
		return 32;
	}

	public Pointer createPointer( ) throws NativeException
	{
		if ( pointer == null )
		{
			pointer = new Pointer( new GlobalMemoryBlock( getSizeOf( ) ) );
		}
		toPointer( );
		return pointer;
	}

	public Object getValueFromPointer( ) throws NativeException
	{
		fromPointer( );
		return this;
	}

	private void fromPointer( ) throws NativeException
	{
		offset = 0;
		hwnd = new HWND( getNextInt( ) );
		wFunc = new UINT( getNextInt( ) );

		int nextInt = getNextInt( );
		if ( nextInt != 0 )
		{
			pFrom = Native.getMemoryAsString( nextInt, 256 );
		}

		nextInt = getNextInt( );
		if ( nextInt != 0 )
		{
			pTo = Native.getMemoryAsString( nextInt, 256 );
		}

		fFlags = new SHORT( getNextShort( ) );

		fAnyOperationsAborted = new BOOL( getNextInt( ) );

		hNameMappings = new UINT( getNextInt( ) );

		nextInt = getNextInt( );
		if ( nextInt != 0 )
		{
			lpszProgressTitle = Native.getMemoryAsString( nextInt, 256 );
		}
	}

	public SHFILEOPSTRUCT getValue( ) throws NativeException
	{
		return (SHFILEOPSTRUCT) getValueFromPointer( );
	}

}
