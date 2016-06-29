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
package org.sf.feeling.swt.win32.extension.jna.datatype;
/**
 * <p>LONG is an implementation of the C LONG data, an int in Java</p>
 * <p>To get a LPLONG call createPointer() or after getPointer().</p>
 * <p>To retreive the value pointed by this object call getValueFromPointer()</p>
 */

public class LONG extends INT
{

	public LONG( int value )
	{
		super( value );
	}
	
}
