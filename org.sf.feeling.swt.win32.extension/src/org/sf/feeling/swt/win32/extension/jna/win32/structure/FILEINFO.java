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
 * typedef struct _SHFILEINFO { 
 * 	HICON hIcon; 
 * 	int iIcon; 
 * 	DWORD dwAttributes;
 * 	TCHAR szDisplayName[MAX_PATH]; 
 * 	TCHAR szTypeName[80]; 
 * } SHFILEINFO;
 * 
 * Members
 * 
 * hIcon Handle to the icon that represents the file. You are responsible
 * for destroying this handle with DestroyIcon when you no longer need it.
 * iIcon Index of the icon image within the system image list. dwAttributes
 * Array of values that indicates the attributes of the file object. For
 * information about these values, see the IShellFolder::GetAttributesOf
 * method. szDisplayName String that contains the name of the file as it
 * appears in the Microsoft Windows Shell, or the path and file name of the
 * file that contains the icon representing the file. szTypeName String that
 * describes the type of file.
 * 
 * 
 * Example:
 * 
 * SHFILEINFO fileInfo = new SHFILEINFO();
 * Shell32.SHGetFileInfo("C:\\Anyfile.ext",new DWORD(0),fileInfo,
 * 	fileInfo.getSizeOf(), SHFILEINFO.SHGFI_DISPLAYNAME |
 * 	SHFILEINFO.SHGFI_TYPENAME | SHFILEINFO.SHGFI_ICON |
 * 	SHFILEINFO.SHGFI_SMALLICON);
 * System.out.println(fileInfo.getDisplayName());
 * System.out.println(fileInfo.getIcon()); 
 * 
 * Note:Icon needs to be destroyed afterwards with DestroyIcon(LONG hIcon);
 * </pre>
 */
public class FILEINFO extends Structure
{

	public static final int MAX_PATH = 256;

	/** Creates a new instance of SHFILEINFO */
	public FILEINFO( )
	{
		super( );
		try
		{
			createPointer( );
		}
		catch ( Exception e )
		{
			throw new RuntimeException( e );
		}
	}

	public Pointer createPointer( ) throws NativeException
	{
		pointer = new Pointer( MemoryBlockFactory.createMemoryBlock( getSizeOf( ) ) );
		return pointer;
	}

	public int getSizeOf( )
	{
		return 384;
	}

	public Object getValueFromPointer( ) throws NativeException
	{
		return this;
	}

	public FILEINFO getValue( )
	{
		return this;
	}

	public int getIcon( ) throws NativeException
	{
		return pointer.getAsInt( 0 );
	}

	public int getIndex( ) throws NativeException
	{
		return pointer.getAsInt( 4 );
	}

	public String getDisplayName( ) throws NativeException
	{
		byte[] b = new byte[MAX_PATH];
		for ( int i = 0; i < MAX_PATH - 12; i++ )
			b[i] = pointer.getAsByte( i + 12 );
		return new String( b ).trim( );
	}

	public String getTypeName( ) throws NativeException
	{
		byte[] b = new byte[80];
		for ( int i = 0; i < 80; i++ )
			b[i] = pointer.getAsByte( i + 12 + MAX_PATH );
		return new String( b ).trim( );
	}
}
