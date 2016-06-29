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

package org.sf.feeling.swt.win32.extension.jna.ptr;

/**
 * Type representing a type-safe native pointer.
 */
public abstract class PointerType
{

	private Pointer pointer;

	/**
	 * All <code>PointerType</code> classes represent a native {@link Pointer}.
	 */
	public Class nativeType( )
	{
		return Pointer.class;
	}

	/** Convert this object to its native type (a {@link Pointer}). */
	public Object toNative( )
	{
		return getPointer( );
	}

	/** Returns the associated native {@link Pointer}. */
	public Pointer getPointer( )
	{
		return pointer;
	}

	public void setPointer( Pointer p )
	{
		this.pointer = p;
	}

	/**
	 * The hash code for a <code>PointerType</code> is the same as that for its
	 * pointer.
	 */
	public int hashCode( )
	{
		return pointer != null ? pointer.hashCode( ) : 0;
	}

	/**
	 * Instances of <code>PointerType</code> with identical pointers compare
	 * equal by default.
	 */
	public boolean equals( Object o )
	{
		if ( o == this )
			return true;
		if ( o instanceof PointerType )
		{
			Pointer p = ( (PointerType) o ).getPointer( );
			if ( pointer == null )
				return p == null;
			return pointer.equals( p );
		}
		return false;
	}

	public String toString( )
	{
		return pointer == null ? "NULL" : pointer.toString( );
	}
}
