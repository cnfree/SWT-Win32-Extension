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

public class FloatByReference extends ByReference
{

	public FloatByReference( ) throws NativeException
	{
		this( 0f );
	}

	public FloatByReference( float value ) throws NativeException
	{
		super( 4 );
		setValue( value );
	}

	public void setValue( float value ) throws NativeException
	{
		getPointer( ).setFloatAt( 0, value );
	}

	public float getValue( ) throws NativeException
	{
		return getPointer( ).getAsFloat( 0 );
	}

}
