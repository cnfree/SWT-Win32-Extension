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
package org.sf.feeling.swt.win32.extension.jna;

/**
 * Represents the call convention of the function (STDCALL, CDECL)
 * 
 */
public class Convention
{

	private String description;
	private int value;

	public int getValue( )
	{
		return value;
	}

	public String getDescription( )
	{
		return description;
	}

	public final static Convention STDCALL = new Convention( 0, "STDCALL" );

	public final static Convention CDECL = new Convention( 1, "CDECL" );

	static void setDefaultStyle( Convention convention )
	{
		DEFAULT = convention;
	}

	public static Convention DEFAULT = Convention.STDCALL;

	private Convention( int value, String description )
	{
		this.value = value;
		this.description = description;
	}

	/**
	 * 
	 * @param v
	 * @return a valid enum value (by default STDCALL)
	 */
	public static Convention fromInt( int v )
	{
		switch ( v )
		{
			case 0 :
				return STDCALL;
			case 1 :
				return CDECL;
			default :
				return DEFAULT;
		}
	}
}
