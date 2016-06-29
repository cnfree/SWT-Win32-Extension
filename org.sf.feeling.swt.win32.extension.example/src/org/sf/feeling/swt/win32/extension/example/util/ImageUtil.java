
package org.sf.feeling.swt.win32.extension.example.util;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.program.Program;
import org.sf.feeling.swt.win32.extension.example.Example;
import org.sf.feeling.swt.win32.extension.io.FileSystem;
import org.sf.feeling.swt.win32.extension.shell.IShellFolder;
import org.sf.feeling.swt.win32.extension.shell.IShellFolder.CanonicalPIDL;
import org.sf.feeling.swt.win32.extension.shell.IShellFolder.DesktopFolder;

public class ImageUtil
{

	static
	{
		try
		{
			String tempFolder = FileSystem.getTempPath( )
					+ System.currentTimeMillis( )
					+ "\\";
			File folder = new File( tempFolder );
			folder.mkdirs( );

			DesktopFolder shellFolder = IShellFolder.getDesktopFolder( Example.getInstance( )
					.getShell( ).handle );
			CanonicalPIDL folderHandle = IShellFolder.getCanonicalPIDL( shellFolder,
					folder.getCanonicalPath( ) );
			Image folderImage = IShellFolder.getIcon( folderHandle, false );
			folderHandle.dispose( );
			JFaceResources.getImageRegistry( ).put( "Directory", folderImage );

			File tempFile = new File( folder, "" + System.currentTimeMillis( ) );
			tempFile.createNewFile( );

			CanonicalPIDL fileHandle = IShellFolder.getCanonicalPIDL( shellFolder,
					tempFile.getCanonicalPath( ) );
			Image fileImage = IShellFolder.getIcon( fileHandle, false );
			fileHandle.dispose( );
			JFaceResources.getImageRegistry( ).put( "File", fileImage );

			String exe = ".exe";
			Program program = Program.findProgram( exe );
			ImageData imageData = program.getImageData( );
			if ( program != null && imageData != null )
			{
				getProgramImage( exe, imageData );
			}
		}
		catch ( IOException e )
		{
			e.printStackTrace( );
		}
	}

	public static Image getDirectoryImage( )
	{
		Image image = JFaceResources.getImageRegistry( ).get( "Directory" );
		if ( image != null )
			return image;
		return null;
	}

	public static Image getFileImage( )
	{
		Image image = JFaceResources.getImageRegistry( ).get( "File" );
		if ( image != null )
			return image;
		return null;
	}

	public static Image getExeImage( )
	{
		Image image = JFaceResources.getImageRegistry( ).get( ".exe" );
		if ( image != null )
			return image;
		return null;
	}

	public static Image getProgramImage( String extension, ImageData imageData )
	{
		Image image = JFaceResources.getImageRegistry( )
				.get( extension.toLowerCase( ) );
		if ( image != null )
			return image;
		else
		{
			if ( imageData != null )
			{
				JFaceResources.getImageRegistry( )
						.put( extension.toLowerCase( ),
								new Image( null, imageData ) );
				return JFaceResources.getImageRegistry( )
						.get( extension.toLowerCase( ) );
			}
			return null;
		}
	}

	public static boolean compareImageData( ImageData src, ImageData dist )
	{
		if ( src == null || dist == null )
			return false;
		if ( src.data == null || !Arrays.equals( src.data, dist.data ) )
			return false;
		if ( src.alphaData != null
				&& !Arrays.equals( src.alphaData, dist.alphaData ) )
			return false;
		if ( src.alphaData == null && dist.alphaData != null )
			return false;
		return true;
	}

	public static Image getProgramImage( String extension )
	{
		Image image = JFaceResources.getImageRegistry( )
				.get( extension.toLowerCase( ) );
		if ( image != null )
			return image;
		return null;
	}
}
