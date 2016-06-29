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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.sf.feeling.swt.win32.extension.Win32;
import org.sf.feeling.swt.win32.extension.shell.IShellFolder;
import org.sf.feeling.swt.win32.extension.shell.IShellFolder.CanonicalPIDL;
import org.sf.feeling.swt.win32.extension.shell.IShellFolder.DesktopFolder;
import org.sf.feeling.swt.win32.extension.shell.IShellFolder.RelativePIDL;

public class FileTableContentProvider implements IStructuredContentProvider
{

	DesktopFolder desktop;

	public FileTableContentProvider( DesktopFolder desktop )
	{
		this.desktop = desktop;
	}

	public Object[] getElements( Object parent )
	{
		IShellFolder folder = null;
		if ( parent instanceof IShellFolder )
		{
			folder = (IShellFolder) parent;
		}
		if ( parent instanceof String )
		{
			RelativePIDL handle = desktop.getPIDL( (String) parent );
			folder = desktop.getPIDLItemsHolder( handle );
			handle.dispose( );
		}
		if ( parent instanceof CanonicalPIDL )
		{
			RelativePIDL pidl = ( (CanonicalPIDL) parent ).getRelativePIDL( );
			if ( pidl != null )
			{
				folder = pidl.getParentFolder( ).getPIDLItemsHolder( pidl );
			}
			else
			{
				String name = IShellFolder.getPIDLCanonicalName( desktop,
						(CanonicalPIDL) parent );
				RelativePIDL handle = desktop.getPIDL( name );
				folder = desktop.getPIDLItemsHolder( handle );
				handle.dispose( );
			}
		}
		if ( folder != null )
		{
			String[] names = getChildrenNames( folder );
			return getSortPaths( names ).toArray( );
		}
		return new String[0];
	}

	private String[] getChildrenNames( IShellFolder folder )
	{
		List names = new ArrayList( );
		{
			RelativePIDL[] handles = folder.getPIDLItems( IShellFolder.SHCONTF.NONFOLDERS );
			for ( int i = 0; i < handles.length; i++ )
			{
				String name = folder.getPIDLName( handles[i] );
				if ( IShellFolder.getCanonicalPIDL( desktop, name ) == null )
				{
					handles[i].dispose( );
					continue;
				}
				if ( name != null && name.trim( ).length( ) > 0 )
				{
					if ( Win32.getWin32Version( ) <= Win32.VERSION( 5, 1 ) )
					{
						File file = new File( name );
						if ( !file.exists( ) || file.getParentFile( ) == null )
						{
							continue;
						}
					}
					names.add( name );
				}
				handles[i].dispose( );
			}
		}
		if ( Win32.getWin32Version( ) > Win32.VERSION( 5, 1 ) )
		{
			RelativePIDL[] handles = folder.getPIDLItems( IShellFolder.SHCONTF.FOLDERS );
			for ( int i = 0; i < handles.length; i++ )
			{
				String name = folder.getPIDLName( handles[i] );
				if ( name != null && name.trim( ).length( ) > 0 )
				{
					File file = new File( name );
					if ( file.exists( ) )
					{
						if ( !file.isDirectory( ) )
						{
							names.add( name );
						}
						else
						{
							RelativePIDL handle = desktop.getPIDL( (String) name );
							IShellFolder subFolder = desktop.getPIDLItemsHolder( handle );
							if ( subFolder == null )
								names.add( name );
						}
					}
				}
				handles[i].dispose( );
			}
		}
		return (String[]) names.toArray( new String[0] );
	}

	private List getSortPaths( String[] names )
	{
		List virtualList = new ArrayList( );
		List folderList = new ArrayList( );
		List linkList = new ArrayList( );
		List fileList = new ArrayList( );
		for ( int i = 0; i < names.length; i++ )
		{
			File file = new File( names[i] );
			if ( !file.exists( ) )
			{
				virtualList.add( names[i] );
			}
			else if ( file.isDirectory( ) )
			{
				folderList.add( names[i] );
			}
			else if ( names[i].toLowerCase( ).endsWith( ".lnk" )
					|| names[i].toLowerCase( ).endsWith( ".url" ) )
			{
				linkList.add( names[i] );
			}
			else
				fileList.add( names[i] );

		}
		Collections.sort( virtualList );
		Collections.sort( folderList );
		Collections.sort( linkList );
		Collections.sort( fileList );

		List list = new ArrayList( );
		list.addAll( virtualList );
		list.addAll( folderList );
		list.addAll( linkList );
		list.addAll( fileList );
		return list;
	}

	public void dispose( )
	{
		// TODO Auto-generated method stub

	}

	public void inputChanged( Viewer arg0, Object arg1, Object arg2 )
	{
		// TODO Auto-generated method stub

	}

}
