
package org.sf.feeling.swt.win32.extension.util;

import org.eclipse.swt.internal.win32.TCHAR;
import org.sf.feeling.swt.win32.internal.extension.Extension;

public class DoubleNullTerminatedTChar extends TCHAR
{

	public DoubleNullTerminatedTChar( int codePage, String string )
	{
		super( codePage, string, true );

		if ( Extension.IsUnicode )
		{
			char[] chars = this.chars;
			char[] newChars = new char[chars.length + 1];
			System.arraycopy( chars, 0, newChars, 0, chars.length );
			newChars[chars.length] = '\0';
			this.chars = newChars;
		}
		else
		{
			byte[] bytes = this.bytes;
			byte[] newBytes = new byte[bytes.length + 1];
			System.arraycopy( bytes, 0, newBytes, 0, bytes.length );
			newBytes[bytes.length] = '\0';
			this.bytes = newBytes;
		}
	}

}
