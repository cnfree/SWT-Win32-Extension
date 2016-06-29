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

import org.sf.feeling.swt.win32.extension.jna.exception.NativeException;
import org.sf.feeling.swt.win32.extension.jna.ptr.NullPointer;
import org.sf.feeling.swt.win32.extension.jna.ptr.Pointer;

/**
 * SecurityAttributes this utility class is used by Kernel32.createFile().<br>
 * This is the native peer.
 * 
 * <pre>
 *  typedef struct _SECURITY_ATTRIBUTES {
 * &nbsp;	DWORD nLength;
 * &nbsp;	LPVOID lpSecurityDescriptor;
 * &nbsp;	BOOL bInheritHandle;
 * &nbsp;} SECURITY_ATTRIBUTES,
 * &nbsp;*PSECURITY_ATTRIBUTES;
 * </pre>
 */
public class SECURITY_ATTRIBUTES
{

	/*
	 */

	private int nLength;
	private Pointer lpSecurityDescriptor;
	private boolean bInheritHandle;

	private Pointer self;

	public SECURITY_ATTRIBUTES( )
	{
		nLength = 12;
		lpSecurityDescriptor = new NullPointer( );
		bInheritHandle = true;
		try
		{
			self = Pointer.createPointer( nLength );
		}
		catch ( NativeException e )
		{
			throw new RuntimeException( e );
		}
	}

	public Pointer getPointer( )
	{
		return self;
	}

	public void dispose( ) throws NativeException
	{
		self.dispose( );
		lpSecurityDescriptor.dispose( );
	}

	protected void finalize( ) throws Throwable
	{
		try
		{
			dispose( );
		}
		catch ( NativeException e )
		{
			e.printStackTrace( );
		}
	}

	/**
	 * Sets NLength
	 * 
	 * @param nLength
	 *            an int
	 */
	public void setNLength( int nLength ) throws NativeException
	{
		this.nLength = nLength;
		self.setIntAt( 0, nLength );
	}

	/**
	 * Returns NLength
	 * 
	 * @return an int
	 */
	public int getNLength( ) throws NativeException
	{
		nLength = self.getAsInt( 0 );
		return nLength;
	}

	/**
	 * Sets LpSecurityDescriptor
	 * 
	 * @param lpSecurityDescriptor
	 *            Pointer
	 */
	public void setLpSecurityDescriptor( Pointer lpSecurityDescriptor )
			throws NativeException
	{
		this.lpSecurityDescriptor = lpSecurityDescriptor;
		self.setIntAt( 4, lpSecurityDescriptor.getPointer( ) );
	}

	/**
	 * Returns LpSecurityDescriptor
	 * 
	 * @return a Pointer
	 */
	public Pointer getLpSecurityDescriptor( )
	{
		return lpSecurityDescriptor;
	}

	/**
	 * Sets BInheritHandle
	 * 
	 * @param bInheritHandle
	 *            a boolean
	 */
	public void setBInheritHandle( boolean bInheritHandle )
			throws NativeException
	{
		this.bInheritHandle = bInheritHandle;
		lpSecurityDescriptor.setIntAt( 8, bInheritHandle ? 1 : 0 );
	}

	/**
	 * Returns BInheritHandle
	 * 
	 * @return a boolean
	 */
	public boolean isBInheritHandle( ) throws NativeException
	{
		bInheritHandle = lpSecurityDescriptor.getAsInt( 8 ) != 0;
		return bInheritHandle;
	}

}
