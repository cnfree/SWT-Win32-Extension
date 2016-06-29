
package org.sf.feeling.swt.win32.extension.shell;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.sf.feeling.swt.win32.extension.jna.exception.NativeException;
import org.sf.feeling.swt.win32.extension.jna.ptr.IntByReference;
import org.sf.feeling.swt.win32.internal.extension.Extension;
import org.sf.feeling.swt.win32.internal.extension.util.LONG;

public class IExtractIcon
{

	public static interface GIL
	{

		int OPENICON = 1;
		int FORSHELL = 2;
		int ASYNC = 32;
		int DEFAULTICON = 64;
		int FORSHORTCUT = 128;
		int CHECKSHIELD = 512;
		int SIMULATEDOC = 1;
		int PERINSTANCE = 2;
		int PERCLASS = 4;
		int NOTFILENAME = 8;
		int DONTCACHE = 16;
		int SHIELD = 512;
		int FORCENOSHIELD = 1024;
	}

	private int handle;

	public IExtractIcon( int handle )
	{
		this.handle = handle;
	}

	public String getIconLocation( int uflags, IntByReference index, int pwFlags )
	{
		if ( handle == 0 || index == null || uflags < 0 || pwFlags < 0 )
			return null;
		return Extension.IExtractIcon_GetIconLocation( handle,
				uflags,
				index.getPointer( ).getPointer( ),
				pwFlags );
	}

	public ImageData[] extractIcons( String location, IntByReference index,
			int largeHeight, int smallHeight )
	{
		if ( handle == 0
				|| index == null
				|| location == null
				|| largeHeight <= 0
				|| smallHeight <= 0 )
			return null;

		ImageData[] images = new ImageData[2];

		int[] phiconSmall = new int[1];
		int[] phiconLarge = new int[1];
		try
		{
			Extension.IExtractIcon_Extract( handle,
					location,
					index.getValue( ),
					phiconLarge,
					phiconSmall,
					LONG.MAKELONG( largeHeight, smallHeight ) );
		}
		catch ( NativeException e )
		{
			e.printStackTrace( );
			return null;
		}
		if ( phiconLarge[0] != 0 )
		{
			Image image = Image.win32_new( null, SWT.ICON, phiconLarge[0] );
			ImageData imageData = image.getImageData( );
			image.dispose( );
			images[0] = imageData;
		}
		if ( phiconSmall[0] != 0 )
		{
			Image image = Image.win32_new( null, SWT.ICON, phiconSmall[0] );
			ImageData imageData = image.getImageData( );
			image.dispose( );
			images[1] = imageData;
		}
		return images;
	}
}
