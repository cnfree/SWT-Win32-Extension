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

package org.sf.feeling.swt.win32.extension.example.page;

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.sf.feeling.swt.win32.extension.example.provider.ProcessExplorerContentProvider;
import org.sf.feeling.swt.win32.extension.example.provider.ProcessExplorerLabelProvider;
import org.sf.feeling.swt.win32.extension.example.util.WidgetUtil;
import org.sf.feeling.swt.win32.extension.shell.ShellFolder;

public class ProcessExplorerPage extends SimpleTabPage
{

	private Composite container;

	public void buildUI( Composite parent )
	{
		container = WidgetUtil.getToolkit( ).createComposite( parent );
		GridLayout layout = new GridLayout( );
		layout.marginLeft = 10;
		layout.marginRight = 15;
		layout.marginBottom = 15;
		layout.verticalSpacing = 15;
		container.setLayout( layout );

		createTitle( );
		createProcessExplorerArea( );
	}

	private void createProcessExplorerArea( )
	{
		Composite composite = new Composite( container, SWT.NONE );
		GridLayout layout = new GridLayout( );
		layout.marginHeight = layout.marginWidth = 1;
		composite.setLayout( layout );

		composite.setLayoutData( new GridData( GridData.FILL_BOTH ) );

		final Tree tree = WidgetUtil.getToolkit( ).createTree( composite,
				SWT.V_SCROLL | SWT.H_SCROLL );
		tree.setLinesVisible( false );
		tree.setHeaderVisible( true );
		final TreeViewer processViewer = new TreeViewer( tree );
		processViewer.getTree( )
				.setLayoutData( new GridData( GridData.FILL_BOTH ) );
		processViewer.setLabelProvider( new ProcessExplorerLabelProvider( ) );
		processViewer.setContentProvider( new ProcessExplorerContentProvider( ) );

		String[] columns = new String[]{
				"Image Name",
				"PID",
				"Base Priority",
				"Threads",
				"Image Path Name"
		};

		int[] widths = new int[]{
				130, 50, 100, 70, 180
		};

		int[] style = new int[]{
				SWT.LEFT, SWT.LEFT, SWT.RIGHT, SWT.RIGHT, SWT.LEFT
		};
		for ( int i = 0; i < columns.length; i++ )
		{

			TreeColumn column = new TreeColumn( processViewer.getTree( ),
					style[i] );
			column.setResizable( columns[i] != null );
			if ( columns[i] != null )
			{
				column.setText( columns[i] );
			}
			column.setWidth( widths[i] );
		}

		processViewer.setInput( new Object[]{
			ShellFolder.COMPUTER
		} );

		Thread thread = new Thread( ) {

			public void run( )
			{
				while ( true )
				{
					Display.getDefault( ).syncExec( new Runnable( ) {

						public void run( )
						{
							if ( tree != null && !tree.isDisposed( ) )
							{
								processViewer.refresh( );
							}
						}
					} );
					try
					{
						Thread.sleep( 1000 );
					}
					catch ( InterruptedException e ){}
				}
			}
		};
		thread.setDaemon( true );
		thread.start( );
	}

	private void createTitle( )
	{
		Label label = WidgetUtil.getToolkit( )
				.createLabel( container,
						"This page demonstrates a simple process explorer created by SWT Win32 Extension.",
						SWT.WRAP );
		GridData gd = new GridData( );
		gd.horizontalSpan = 2;
		gd.horizontalAlignment = SWT.FILL;
		gd.widthHint = 300;
		label.setLayoutData( gd );
	}

	public Composite getControl( )
	{
		return container;
	}

	public String getDisplayName( )
	{
		return "Process Explorer";
	}

}
