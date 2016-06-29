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

import org.sf.feeling.swt.win32.extension.jna.Native;
import org.sf.feeling.swt.win32.extension.jna.exception.NativeException;

/**
 * Represents a reference to a pointer to native data. In C notation,
 * <code>void**</code>.
 * 
 */
public class PointerByReference extends ByReference
{

	private Pointer value;

	public PointerByReference( ) throws NativeException
	{
		this( null );
	}

	public PointerByReference( Pointer value ) throws NativeException
	{
		super( Native.POINTER_SIZE );
		setValue( value );
	}

	public void setValue( Pointer value ) throws NativeException
	{
		this.value = value;
		getPointer( ).setIntAt( 0, value.getPointer( ) );
	}

	public Pointer getValue( ) throws NativeException
	{
		return value;
	}
}
