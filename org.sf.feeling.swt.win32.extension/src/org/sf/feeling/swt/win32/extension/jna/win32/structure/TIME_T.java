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

import java.util.Date;

import org.sf.feeling.swt.win32.extension.jna.datatype.LONG;


public class TIME_T extends LONG
{

	public TIME_T( int value )
	{
		super( value );
	}

	public Date getAsDate( )
	{
		return new Date( getValue( ) * 1000 );
	}

}
