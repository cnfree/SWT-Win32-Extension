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

public class ByteByReference extends ByReference
{

	public ByteByReference( ) throws NativeException
	{
		this( (byte) 0 );
	}

	public ByteByReference( byte value ) throws NativeException
	{
		super( 1 );
		setValue( value );
	}

	public void setValue( byte value ) throws NativeException
	{
		getPointer( ).setByteAt( 0, value );
	}

	public byte getValue( ) throws NativeException
	{
		return getPointer( ).getAsByte( 0 );
	}

}
