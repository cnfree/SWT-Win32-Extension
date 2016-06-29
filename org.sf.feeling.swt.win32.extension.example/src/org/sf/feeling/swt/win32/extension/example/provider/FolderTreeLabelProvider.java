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

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.sf.feeling.swt.win32.extension.Win32;
import org.sf.feeling.swt.win32.extension.example.util.ImageUtil;
import org.sf.feeling.swt.win32.extension.shell.IShellFolder;
import org.sf.feeling.swt.win32.extension.shell.IShellFolder.CanonicalPIDL;
import org.sf.feeling.swt.win32.extension.shell.IShellFolder.DesktopFolder;
import org.sf.feeling.swt.win32.extension.shell.IShellFolder.RelativePIDL;

public class FolderTreeLabelProvider extends LabelProvider
{
	private DesktopFolder desktop;

	public String getText( Object element )
	{
		if ( element instanceof DesktopFolder )
		{
			desktop = (DesktopFolder) element;
			return desktop.getDisplayName( );
		}
		else if ( desktop != null && !desktop.isDisposed( ) )
		{
			CanonicalPIDL handle = null;
			if ( element instanceof String )
			{
				String path = (String) element;
				handle = IShellFolder.getCanonicalPIDL( desktop, path );
			}
			else if ( element instanceof CanonicalPIDL )
			{
				handle = (CanonicalPIDL) element;
			}
			if ( handle == null )
				return null;
			if ( Win32.getWin32Version( ) < Win32.VERSION( 6, 0 ) )
			{
				if ( handle != null )
				{
					IShellFolder folder = IShellFolder.getRelativeParent( desktop,
							handle );
					RelativePIDL relativeHandle = IShellFolder.getRelativePIDL( handle );
					String displayPath = folder.getPIDLDisplayName( relativeHandle,
							IShellFolder.SHGDNF.INFOLDER );
					if ( !( element instanceof CanonicalPIDL ) )
					{
						folder.dispose( );
						relativeHandle.dispose( );
						handle.dispose( );
					}
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
		}
		return null;
	}

	public Image getImage( Object element )
	{
		if ( element instanceof DesktopFolder )
		{
			desktop = (DesktopFolder) element;
			return desktop.getIcon( false );
		}
		if ( desktop != null && !desktop.isDisposed( ) )
		{
			CanonicalPIDL handle = null;
			if ( element instanceof String )
			{
				String path = (String) element;
				handle = IShellFolder.getCanonicalPIDL( desktop, path );
			}
			else if ( element instanceof CanonicalPIDL )
			{
				handle = (CanonicalPIDL) element;
			}
			if ( handle == null )
				return null;

			Image fileImage = IShellFolder.getIcon( handle, false );
			return fileImage;
		}
		return ImageUtil.getDirectoryImage( );
	}
}
