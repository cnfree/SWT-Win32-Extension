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

public class DoubleByReference extends ByReference
{

	public DoubleByReference( ) throws NativeException
	{
		this( 0d );
	}

	public DoubleByReference( double value ) throws NativeException
	{
		super( 8 );
		setValue( value );
	}

	public void setValue( double value ) throws NativeException
	{
		getPointer( ).setDoubleAt( 0, value );
	}

	public double getValue( ) throws NativeException
	{
		return getPointer( ).getAsDouble( 0 );
	}

}
