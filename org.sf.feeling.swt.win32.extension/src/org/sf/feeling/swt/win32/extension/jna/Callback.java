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

import org.sf.feeling.swt.win32.extension.jna.exception.NativeException;

/**
 * <b>Note:</b>It only works on jdk1.5 or later.
 */
public interface Callback
{

	/**
	 * Method callback
	 * 
	 * @param values
	 *            an long[]
	 * 
	 * @return an int
	 */
	public int callback( long[] values );

	/**
	 * This method should call Native.createCallback() AND MUST allow multiple
	 * calls
	 * <p>
	 * Something like :
	 * 
	 * <pre>
	 * 
	 * 
	 * abstract class MyCallback implements Callback
	 * {
	 * 
	 * 	private int myAddress = -1;
	 * 
	 * 	public int getCallbackAddress( ) throws NativeException
	 * 	{
	 * 		if ( myAddress == -1 )
	 * 		{
	 * 			myAddress = Native.createCallback( numParam, this );
	 * 		}
	 * 		return myAddress;
	 * 	}
	 * }
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @return the address of the callback function
	 */
	public int getCallbackAddress( ) throws NativeException;
}
