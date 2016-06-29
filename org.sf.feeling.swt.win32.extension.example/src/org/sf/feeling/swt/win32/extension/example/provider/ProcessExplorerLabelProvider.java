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

import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.graphics.Image;
import org.sf.feeling.swt.win32.extension.system.ProcessEntry;

public class ProcessExplorerLabelProvider implements ITableLabelProvider
{

	public Image getColumnImage( Object element, int columnIndex )
	{
		return null;
	}

	public String getColumnText( Object element, int columnIndex )
	{
		if ( element instanceof ProcessEntry )
		{
			switch ( columnIndex )
			{
				case 0 :
					return ( (ProcessEntry) element ).getProcessName( );
				case 1 :
					return new Integer( ( (ProcessEntry) element ).getProcessId( ) ).toString( );
				case 2 :
					int priority = ( (ProcessEntry) element ).getPriorityBase( );
					if ( priority <= 4 )
						return "Idle";
					else if ( priority > 4 && priority < 8 )
						return "Below Normal";
					else if ( priority == 8 )
						return "Normal";
					else if ( priority > 8 && priority < 13 )
						return "Above Normal";
					else if ( priority >= 13 && priority < 24 )
						return "High";
					else if ( priority >= 24 )
						return "Real Time";
				case 3 :
					return new Integer( ( (ProcessEntry) element ).getThreadsCount( ) ).toString( )+"  ";
				case 4 :
					return ( (ProcessEntry) element ).getExePath( );
				default :
					break;
			}
		}
		else if ( element instanceof String && columnIndex == 0 )
		{
			return ( (String) element );
		}
		return "";
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
