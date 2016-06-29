
package org.sf.feeling.swt.win32.extension.jna.datatype;

import org.sf.feeling.swt.win32.extension.jna.exception.NativeException;

public class bool extends CHAR
{

	public bool( byte value )
	{
		super( value );
	}

	public boolean getValueAsBoolean( )
	{
		return getValue( ) != 0;
	}

	public void setValue( boolean bool ) throws NativeException
	{
		setValue( bool ? (byte) 1 : (byte) 0 );
	}

	public void setValue( byte value ) throws NativeException
	{
		setValue( value > 0 ? (byte) 1 : (byte) 0 );
	}
}
