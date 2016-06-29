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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.sf.feeling.swt.win32.extension.system.Kernel;
import org.sf.feeling.swt.win32.extension.system.ModuleEntry;
import org.sf.feeling.swt.win32.extension.system.ProcessEntry;

public class ProcessExplorerContentProvider implements ITreeContentProvider
{

	public Object[] getElements( Object parent )
	{
		return Kernel.getSystemProcessesSnap( );
	}

	public void dispose( )
	{
		// TODO Auto-generated method stub

	}

	public void inputChanged( Viewer arg0, Object arg1, Object arg2 )
	{
		// TODO Auto-generated method stub

	}

	public Object[] getChildren( Object parentElement )
	{
		if ( parentElement instanceof ProcessEntry )
		{
			ModuleEntry[] entries = Kernel.getProcessModulesSnap( ( (ProcessEntry) parentElement ).getProcessId( ) );
			List list = new ArrayList( );
			for ( int i = 0; i < entries.length; i++ )
			{
				if ( entries[i].getModuleName( )
						.toLowerCase( )
						.equals( ( (ProcessEntry) parentElement ).getProcessName( )
								.toLowerCase( ) ) )
					continue;
				list.add( entries[i].getModuleName( ) );
			}

			Collections.sort( list );
			return list.toArray( );
		}
		return new Object[0];
	}

	public Object getParent( Object element )
	{
		return null;
	}

	public boolean hasChildren( Object element )
	{
		if ( element instanceof ProcessEntry )
			return Kernel.getProcessModulesSnap( ( (ProcessEntry) element ).getProcessId( ) ).length > 0;
		return false;
	}

}
