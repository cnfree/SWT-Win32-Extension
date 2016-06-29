/*******************************************************************************
 * Copyright (c) 2007 cnfree.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  cnfree  - initial API and implementation
 *******************************************************************************/

package org.sf.feeling.swt.win32.internal.extension.util;

public class LONG
{

	public int /* long */value;

	public LONG( int /* long */value )
	{
		this.value = value;
	}

	public boolean equals( Object object )
	{
		if ( object == this )
			return true;
		if ( !( object instanceof LONG ) )
			return false;
		LONG obj = (LONG) object;
		return obj.value == this.value;
	}

	public int hashCode( )
	{
		return (int) /* 64 */value;
	}

	public static int MAKELONG( int a, int b )
	{
		return a | ( b << 16 );
	}
}
