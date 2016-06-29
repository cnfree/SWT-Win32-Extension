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

package org.sf.feeling.swt.win32.extension.jna.ptr;

import org.sf.feeling.swt.win32.extension.jna.exception.NativeException;

public class IntByReference extends ByReference
{

	public IntByReference( ) throws NativeException
	{
		this( 0 );
	}

	public IntByReference( int value ) throws NativeException
	{
		super( 4 );
		setValue( value );
	}

	public void setValue( int value ) throws NativeException
	{
		getPointer( ).setIntAt( 0, value );
	}

	public int getValue( ) throws NativeException
	{
		return getPointer( ).getAsInt( 0 );
	}
}
