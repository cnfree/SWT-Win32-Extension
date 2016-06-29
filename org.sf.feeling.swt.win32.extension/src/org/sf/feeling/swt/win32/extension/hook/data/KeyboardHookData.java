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

package org.sf.feeling.swt.win32.extension.hook.data;

import org.sf.feeling.swt.win32.extension.Win32;
import org.sf.feeling.swt.win32.internal.extension.util.FlagSet;

/**
 * {@link Win32#WH_KEYBOARD_LL} Hook Data. Be used for wrapping
 * {@link Win32#WH_KEYBOARD_LL} HookProc information.
 * 
 * @author <a href="mailto:cnfree2000@hotmail.com">cnfree</a>
 */
public class KeyboardHookData extends HookData
{

	private static final long serialVersionUID = 842476378142661597L;

	/**
	 * Specifies the virtual key code.
	 * 
	 * @return virtual key code
	 */
	public long getVirtualKeyCode( )
	{
		return getWParam( );
	}

	/**
	 * Specifies the repeat count. The value is the number of times the
	 * keystroke is repeated as a result of the user's holding down the key.
	 * 
	 * @return the repeat count.
	 */
	public int getRepeatCount( )
	{
		FlagSet flagSet = new FlagSet( getLParam( ) );
		return flagSet.getBits( 0, 15 );
	}

	/**
	 * Specifies the scan code. The value depends on the OEM.
	 * 
	 * @return scan code.
	 */
	public int getScanCode( )
	{
		FlagSet flagSet = new FlagSet( getLParam( ) );
		return flagSet.getBits( 16, 23 );
	}

	/**
	 * Specifies whether the key is an extended key, such as a function key or a
	 * key on the numeric keypad.
	 * 
	 * @return true, if the key is an extended key; otherwise, it is false.
	 */
	public boolean isExtendedKey( )
	{
		FlagSet flagSet = new FlagSet( getLParam( ) );
		return flagSet.getBit( 24 );
	}

	/**
	 * Specifies the context code.
	 * 
	 * @return true, if the ALT key is down; otherwise, it is false.
	 */
	public boolean isAltPressed( )
	{
		FlagSet flagSet = new FlagSet( getLParam( ) );
		return flagSet.getBit( 29 );
	}

	/**
	 * Specifies the previous key state.
	 * 
	 * @return true, if the key is down before the message is sent; false if the
	 *         key is up.
	 */
	public boolean getPreviousState( )
	{
		FlagSet flagSet = new FlagSet( getLParam( ) );
		return flagSet.getBit( 30 );
	}

	/**
	 * Specifies the transition state.
	 * 
	 * @return true, if the key is being pressed; false if it is being released.
	 */
	public boolean getTransitionState( )
	{
		FlagSet flagSet = new FlagSet( getLParam( ) );
		return !flagSet.getBit( 31 );
	}
}
