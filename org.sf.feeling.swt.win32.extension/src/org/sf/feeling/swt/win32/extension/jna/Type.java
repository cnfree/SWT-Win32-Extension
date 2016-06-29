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

import org.sf.feeling.swt.win32.extension.jna.datatype.BasicData;
import org.sf.feeling.swt.win32.extension.jna.datatype.CHAR;
import org.sf.feeling.swt.win32.extension.jna.datatype.DOUBLE;
import org.sf.feeling.swt.win32.extension.jna.datatype.FLOAT;
import org.sf.feeling.swt.win32.extension.jna.datatype.INT;
import org.sf.feeling.swt.win32.extension.jna.datatype.LONGLONG;
import org.sf.feeling.swt.win32.extension.jna.datatype.SHORT;
import org.sf.feeling.swt.win32.extension.jna.datatype.win32.Structure;

public class Type
{

	private String mValue;
	private int mNativeType;

	Type( int nativeType, String val )
	{
		mValue = val;
		mNativeType = nativeType;
	}

	public String getType( )
	{
		return mValue;
	}

	public int getNativeType( )
	{
		return mNativeType;
	}

	public final static Type VOID = new Type( -1, "Void" );
	public final static Type INT = new Type( 0, "Int" );
	public final static Type CHAR = new Type( 2, "Char" );
	public final static Type SHORT = new Type( 1, "Short" );
	public final static Type LONG = new Type( 9, "Long" );
	public final static Type DOUBLE = new Type( 7, "Double" );
	public final static Type STRING = new Type( 4, "String" );
	public final static Type FLOAT = new Type( 8, "Float" );
	public final static Type PSTRUCT = new Type( 6, "Stuct*" );

	public static Type getDataType( BasicData data )
	{
		if ( data instanceof INT )
		{
			return Type.INT;
		}
		else if ( data instanceof CHAR )
		{
			return Type.CHAR;
		}
		else if ( data instanceof SHORT )
		{
			return Type.SHORT;
		}
		else if ( data instanceof LONGLONG )
		{
			return Type.LONG;
		}
		else if ( data instanceof FLOAT )
		{
			return Type.FLOAT;
		}
		else if ( data instanceof DOUBLE )
		{
			return Type.DOUBLE;
		}
		else if ( data instanceof Structure )
		{
			return Type.PSTRUCT;
		}
		else 
			throw new IllegalArgumentException( "Unknown Data Type" );
	}
}
