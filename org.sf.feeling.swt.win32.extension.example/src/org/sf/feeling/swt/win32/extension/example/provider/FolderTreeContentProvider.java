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
import java.util.List;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.sf.feeling.swt.win32.extension.shell.IShellFolder;
import org.sf.feeling.swt.win32.extension.shell.IShellFolder.CanonicalPIDL;
import org.sf.feeling.swt.win32.extension.shell.IShellFolder.DesktopFolder;
import org.sf.feeling.swt.win32.extension.shell.IShellFolder.RelativePIDL;

public class FolderTreeContentProvider implements ITreeContentProvider
{

	private DesktopFolder desktop;

	public Object[] getChildren( Object parent )
	{
		if ( !hasChildren( parent ) )
			return new Object[0];
		if ( parent instanceof Object[] )
			return ( (Object[]) parent );
		else if ( parent instanceof DesktopFolder )
		{
			desktop = (DesktopFolder) parent;
			CanonicalPIDL[] children = getChildrens( desktop, null );
			List list = getSortPaths( children );
			return list.toArray( );
		}
		else if ( desktop != null && !desktop.isDisposed( ) )
		{
			RelativePIDL handle = null;
			IShellFolder folder = null;
			handle = ( (CanonicalPIDL) parent ).getRelativePIDL( );
			if ( handle.getParentFolder( ) != null )
			{
				folder = handle.getParentFolder( ).getPIDLItemsHolder( handle );
			}
			if ( folder != null )
			{
				CanonicalPIDL[] children = getChildrens( folder, parent );
				List list = getSortPaths( children );
				return list.toArray( );
			}
			if ( parent instanceof String )
			{
				handle.dispose( );
			}
		}
		return new String[0];
	}

	private CanonicalPIDL[] getChildrens( IShellFolder folder, Object parent )
	{
		List names = new ArrayList( );
		{
			RelativePIDL[] handles = folder.getPIDLItems( IShellFolder.SHCONTF.FOLDERS );
			for ( int i = 0; i < handles.length; i++ )
			{
				names.add( folder.getCanonicalPIDL( handles[i] ) );
				continue;
			}
		}
		return (CanonicalPIDL[]) names.toArray( new CanonicalPIDL[0] );
	}

	private List getSortPaths( CanonicalPIDL[] children )
	{
		List virtualList = new ArrayList( );
		List folderList = new ArrayList( );
		for ( int i = 0; i < children.length; i++ )
		{
			CanonicalPIDL child = children[i];
			if ( IShellFolder.isSystemFolder( child ) )
			{
				virtualList.add( child );
			}
			else
			{
				File file = new File( IShellFolder.getPIDLCanonicalName( desktop,
						child ) );
				if ( !file.exists( ) || file.isDirectory( ) )
					folderList.add( child );
				else
				{
					child.dispose( );
				}
			}
		}

		List list = new ArrayList( );
		list.addAll( virtualList );
		list.addAll( folderList );
		return list;
	}

	public Object getParent( Object element )
	{
		return null;
	}

	public boolean hasChildren( Object parent )
	{
		if ( parent instanceof Object[] )
			return ( (Object[]) parent ).length > 0;
		else if ( parent instanceof DesktopFolder )
		{
			desktop = (DesktopFolder) parent;
			return true;
		}
		else if ( desktop != null && !desktop.isDisposed( ) )
		{
			if ( parent instanceof String )
			{
				String path = (String) parent;
				boolean isFolder = false;
				File file = new File( path );
				if ( file.exists( ) )
				{
					CanonicalPIDL handle = IShellFolder.getCanonicalPIDL( desktop,
							path );
					isFolder = IShellFolder.hasSubFolder( handle );
					if ( handle != null )
						handle.dispose( );
				}
				else
				{
					CanonicalPIDL handle = IShellFolder.getCanonicalPIDL( desktop,
							path );
					isFolder = IShellFolder.hasSubFolder( handle )
							|| IShellFolder.isFolder( handle );
					if ( handle != null )
						handle.dispose( );
				}
				return isFolder;
			}
			else if ( parent instanceof CanonicalPIDL )
			{
				CanonicalPIDL handle = (CanonicalPIDL) parent;
				return IShellFolder.hasSubFolder( handle )
						|| IShellFolder.isFolder( handle );
			}
		}
		return false;
	}

	public Object[] getElements( Object parent )
	{
		if ( parent instanceof Object[] )
			return (Object[]) parent;
		return new Object[0];
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
