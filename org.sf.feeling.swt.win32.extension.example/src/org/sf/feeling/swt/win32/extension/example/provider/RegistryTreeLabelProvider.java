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
import org.sf.feeling.swt.win32.extension.example.Example;
import org.sf.feeling.swt.win32.extension.registry.RegistryKey;
import org.sf.feeling.swt.win32.extension.shell.IShellFolder;
import org.sf.feeling.swt.win32.extension.shell.IShellFolder.CanonicalPIDL;
import org.sf.feeling.swt.win32.extension.shell.IShellFolder.DesktopFolder;
import org.sf.feeling.swt.win32.extension.shell.ShellFolder;
import org.sf.feeling.swt.win32.extension.shell.ShellIcon;
import org.sf.feeling.swt.win32.internal.extension.util.ImageCache;

public class RegistryTreeLabelProvider extends LabelProvider
{

	public String getText( Object element )
	{
		if ( element == ShellFolder.COMPUTER )
		{
			DesktopFolder desktop = IShellFolder.getDesktopFolder( Example.getInstance( )
					.getShell( ).handle );

			CanonicalPIDL pidl = ShellFolder.getFolderPIDL( ShellFolder.COMPUTER );
			String computeName = IShellFolder.getPIDLDisplayName( desktop,
					pidl,
					IShellFolder.SHGDNF.NORMAL );
			pidl.dispose( );
			return computeName;
		}
		else if ( element instanceof RegistryKey )
		{
			if ( ( (RegistryKey) element ).getName( ).length( ) > 0 )
			{
				return ( (RegistryKey) element ).getName( );
			}
			else
				return ( (RegistryKey) element ).getRootKey( ).toString( );
		}
		return null;
	}

	public Image getImage( Object element )
	{
		if ( element == ShellFolder.COMPUTER )
			return ShellIcon.getSysFolderIcon( ShellFolder.COMPUTER.getFolderID( ),
					ShellIcon.ICON_SMALL );
		return ImageCache.getImage( "/folder.gif" );
	}

}
