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

package org.sf.feeling.swt.win32.extension.example.provider;

import java.io.File;
import java.text.NumberFormat;

import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.program.Program;
import org.sf.feeling.swt.win32.extension.Win32;
import org.sf.feeling.swt.win32.extension.example.util.ImageUtil;
import org.sf.feeling.swt.win32.extension.shell.IShellFolder;
import org.sf.feeling.swt.win32.extension.shell.IShellFolder.CanonicalPIDL;
import org.sf.feeling.swt.win32.extension.shell.IShellFolder.DesktopFolder;
import org.sf.feeling.swt.win32.extension.shell.IShellFolder.RelativePIDL;
import org.sf.feeling.swt.win32.extension.shell.ShellIcon;

public class FileTableLabelProvider implements ITableLabelProvider
{

	DesktopFolder desktop;

	public FileTableLabelProvider( DesktopFolder desktop )
	{
		this.desktop = desktop;
	}

	public Image getColumnImage( Object element, int index )
	{
		if ( element instanceof String
				&& desktop != null
				&& !desktop.isDisposed( ) )
		{
			if ( index == 0 )
			{
				String path = (String) element;
				Image image = null;

				File file = new File( path );
				int extIndex = file.getAbsolutePath( ).lastIndexOf( '.' );
				if ( file.exists( ) )
				{
					if ( file.isFile( ) && extIndex >= 0 )
					{
						String extension = file.getAbsolutePath( )
								.substring( extIndex );

						if ( ".exe".equals( extension.toLowerCase( ) )
								&& file.length( ) < 1024 * 1024 * 50 )
						{
							CanonicalPIDL handle = IShellFolder.getCanonicalPIDL( desktop,
									path );
							Image fileImage = IShellFolder.getIcon( handle,
									false );
							if ( fileImage != null )
							{
								image = ImageUtil.getExeImage( );
								if ( image != null && fileImage != null )
								{
									if ( !ImageUtil.compareImageData( fileImage.getImageData( ),
											image.getImageData( ) ) )
									{
										image = ImageUtil.getProgramImage( path,
												fileImage.getImageData( ) );
									}
								}
								fileImage.dispose( );
							}
							handle.dispose( );
						}
						else if ( ".ico".equals( extension.toLowerCase( ) ) )
						{
							Image[] fileImage = ShellIcon.getFileIcons( file,
									ShellIcon.ICON_SMALL );
							if ( fileImage != null
									&& fileImage.length > 0
									&& fileImage[0] != null
									&& fileImage[0].getImageData( ) != null )
							{
								image = ImageUtil.getProgramImage( path,
										fileImage[0].getImageData( ) );
							}
						}
						else if ( file.length( ) < 1024 * 5 )
						{
							CanonicalPIDL handle = IShellFolder.getCanonicalPIDL( desktop,
									path );
							if ( IShellFolder.isLinkFile( handle ) )
							{
								Image fileImage = IShellFolder.getIcon( handle,
										false );
								if ( fileImage != null )
								{
									image = ImageUtil.getProgramImage( path,
											fileImage.getImageData( ) );
									fileImage.dispose( );
								}
							}
							handle.dispose( );
						}
						if ( image == null )
							image = ImageUtil.getProgramImage( extension );
						if ( image == null )
						{
							Program program = Program.findProgram( extension );

							if ( program != null )
							{
								ImageData programImageData = program.getImageData( );
								image = ImageUtil.getProgramImage( extension,
										programImageData );
							}
							else
							{
								if ( !".exe".equals( extension.toLowerCase( ) ) )
								{
									CanonicalPIDL handle = IShellFolder.getCanonicalPIDL( desktop,
											path );
									Image fileImage = IShellFolder.getIcon( handle,
											false );
									if ( fileImage != null )
									{
										image = ImageUtil.getProgramImage( extension,
												fileImage.getImageData( ) );
										fileImage.dispose( );
									}
									handle.dispose( );
								}
							}
						}
					}
					if ( file.isFile( ) && image == null )
					{
						image = ImageUtil.getFileImage( );
					}
					if ( file.isDirectory( ) )
					{
						image = ImageUtil.getDirectoryImage( );
					}
				}
				else
				{
					image = JFaceResources.getImage( path );
					if ( image == null )
					{
						CanonicalPIDL handle = IShellFolder.getCanonicalPIDL( desktop,
								path );
						if ( handle != null )
						{
							Image fileImage = IShellFolder.getIcon( handle,
									false );
							image = ImageUtil.getProgramImage( path,
									fileImage == null ? null
											: fileImage.getImageData( ) );
							if ( fileImage != null )
								fileImage.dispose( );
							handle.dispose( );
						}
					}
				}
				return image;
			}
		}
		return null;
	}

	public String getColumnText( Object element, int columnIndex )
	{
		if ( element instanceof String
				&& desktop != null
				&& !desktop.isDisposed( ) )
		{
			String path = (String) element;
			switch ( columnIndex )
			{
				case 0 :
					CanonicalPIDL handle = IShellFolder.getCanonicalPIDL( desktop,
							path );
					if ( Win32.getWin32Version( ) < Win32.VERSION( 6, 0 ) )
					{
						if ( handle != null )
						{
							IShellFolder folder = IShellFolder.getRelativeParent( desktop,
									handle );
							RelativePIDL relativeHandle = IShellFolder.getRelativePIDL( handle );
							String displayPath = folder.getPIDLDisplayName( relativeHandle,
									IShellFolder.SHGDNF.INFOLDER );
							relativeHandle.dispose( );
							folder.dispose( );
							handle.dispose( );
							if ( displayPath != null )
								return displayPath;
						}
					}
					else
					{
						String displayPath = IShellFolder.getPIDLDisplayName( desktop,
								handle,
								IShellFolder.SHGDNF.INFOLDER );
						if ( displayPath != null )
							return displayPath;
					}
				case 1 :
					File file = new File( path );
					if ( file.exists( ) && !file.isDirectory( ) )
					{
						return format( file.length( ) );
					}
			}
		}
		return "";
	}

	private String format( float totalNumberOfFreeBytes )
	{
		NumberFormat format = NumberFormat.getInstance( );
		format.setMaximumFractionDigits( 1 );
		format.setMinimumFractionDigits( 0 );
		if ( totalNumberOfFreeBytes < 1024 )
			return format.format( totalNumberOfFreeBytes ) + " Bytes";
		totalNumberOfFreeBytes = totalNumberOfFreeBytes / 1024;
		if ( totalNumberOfFreeBytes < 1024 )
			return format.format( totalNumberOfFreeBytes ) + " KB";
		totalNumberOfFreeBytes = totalNumberOfFreeBytes / 1024;
		if ( totalNumberOfFreeBytes < 1024 )
			return format.format( totalNumberOfFreeBytes ) + " MB";
		totalNumberOfFreeBytes = totalNumberOfFreeBytes / 1024;
		if ( totalNumberOfFreeBytes < 1024 )
			return format.format( totalNumberOfFreeBytes ) + " GB";
		return null;
	}

	public void addListener( ILabelProviderListener listener )
	{
	}

	public void dispose( )
	{
	}

	public boolean isLabelProperty( Object element, String property )
	{
		return false;
	}

	public void removeListener( ILabelProviderListener listener )
	{
	}

}
