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
package org.sf.feeling.swt.win32.extension.jna.datatype.win32;

import org.sf.feeling.swt.win32.extension.jna.datatype.INT;
import org.sf.feeling.swt.win32.extension.jna.exception.NativeException;

public class BOOL extends INT
{

	public BOOL( int value )
	{
		super( value );
	}

	public void setValue( boolean value ) throws NativeException
	{
		setValue( value ? 1 : 0 );
	}

	public boolean getValueAsBoolean( )
	{
		return getValue( ) != 0;
	}
}
