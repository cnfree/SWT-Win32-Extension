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

import java.io.File;
import java.io.IOException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.sf.feeling.swt.win32.extension.example.util.WidgetUtil;
import org.sf.feeling.swt.win32.extension.shell.IShellFolder;
import org.sf.feeling.swt.win32.extension.shell.ShellFolder;
import org.sf.feeling.swt.win32.extension.system.Kernel;
import org.sf.feeling.swt.win32.extension.system.ProcessEntry;
import org.sf.feeling.swt.win32.extension.widgets.NativeControl;
import org.sf.feeling.swt.win32.internal.extension.util.ColorCache;

public class NativeControlPage extends TabPage
{

	private Composite container;

	private String path;

	public void buildUI( Composite parent )
	{

		container = WidgetUtil.getToolkit( ).createComposite( parent );
		GridLayout layout = new GridLayout( );
		layout.marginLeft = 10;
		layout.marginRight = 10;
		layout.marginTop = 0;
		layout.verticalSpacing = 15;
		container.setLayout( layout );

		createTitle( );

		CTabFolder tabFolder = new CTabFolder( container, SWT.FLAT );

		tabFolder.setSelectionBackground( new Color[]{
				ColorCache.getInstance( ).getColor( 230, 233, 240 ),
				ColorCache.getInstance( ).getColor( 157, 167, 195 ),
				ColorCache.getInstance( ).getColor( 242, 244, 247 ),
		}, new int[]{
			75
		}, true );

		tabFolder.setSimple( false );;
		tabFolder.setUnselectedCloseVisible( true );

		tabFolder.setLayoutData( new GridData( GridData.FILL_BOTH ) );

		
		createNotepadItem( tabFolder );
		createCalculatorItem( tabFolder );
		ProcessEntry[] processes = Kernel.getSystemProcessesSnap( );
		boolean flag = true;
		for ( int i = 0; i < processes.length; i++ )
		{
			if("taskmgr.exe".equals( processes[i].getProcessName( )))
			{
				flag = false;
				break;
			}
		}
		if(flag)
			createTaskManagerItem( tabFolder );
		
		
	}

	private void createCalculatorItem( CTabFolder tabFolder )
	{
		final CTabItem calculatorItem = new CTabItem( tabFolder, SWT.NONE );
		calculatorItem.setText( "Calculator " );
		try
		{
			String path = new File( ShellFolder.SYSTEM.getAbsolutePath( getControl( ).handle ),
					"calc.exe" ).getCanonicalPath( );
			calculatorItem.setImage( IShellFolder.getIcon( IShellFolder.getCanonicalPIDL( IShellFolder.getDesktopFolder( getControl( ).handle ),
					path ),
					false ) );
		}
		catch ( IOException e )
		{
			e.printStackTrace( );
		}
		Composite composite = WidgetUtil.getToolkit( )
				.createComposite( tabFolder );
		GridLayout layout = new GridLayout( );
		composite.setLayout( layout );

		final NativeControl calculator = new NativeControl( composite, SWT.NONE );
		calculator.setStartCommand( "\"calc.exe\"", true );
		GridData gd = new GridData( GridData.FILL_BOTH );
		calculator.setLayoutData( gd );

		calculator.addDisposeListener( new DisposeListener( ) {

			public void widgetDisposed( DisposeEvent arg0 )
			{
				calculatorItem.dispose( );
			}
		} );
		
		calculatorItem.setControl( composite );
	}

	private void createNotepadItem( final CTabFolder tabFolder )
	{
		final CTabItem notepadItem = new CTabItem( tabFolder, SWT.NONE );
		notepadItem.setText( "Notepad " );
		try
		{
			path = new File( ShellFolder.SYSTEM.getAbsolutePath( getControl( ).handle ),
					"notepad.exe" ).getCanonicalPath( );
			notepadItem.setImage( IShellFolder.getIcon( IShellFolder.getCanonicalPIDL( IShellFolder.getDesktopFolder( getControl( ).handle ),
					path ),
					false ) );
		}
		catch ( IOException e )
		{
			notepadItem.dispose( );
			return;
		}
		Composite composite = WidgetUtil.getToolkit( )
				.createComposite( tabFolder );
		GridLayout layout = new GridLayout( );
		composite.setLayout( layout );

		final NativeControl notepad = new NativeControl( composite, SWT.NONE );
		notepad.setStartCommand( "\"" + path + "\"", true );
		GridData gd = new GridData( GridData.FILL_BOTH );
		notepad.setLayoutData( gd );
		notepadItem.setControl( composite );
		notepad.addDisposeListener( new DisposeListener( ) {

			public void widgetDisposed( DisposeEvent arg0 )
			{
				notepadItem.dispose( );
			}
		} );

		tabFolder.addSelectionListener( new SelectionAdapter( ) {

			public void widgetSelected( SelectionEvent e )
			{
				if ( tabFolder.getSelection( ) == notepadItem )
				{
					notepad.forceChildWndActive( );
				}
			}

		} );
	}
	
	private void createTaskManagerItem( final CTabFolder tabFolder )
	{
		final CTabItem taskmgrItem = new CTabItem( tabFolder, SWT.NONE );
		taskmgrItem.setText( "Task Manager " );
		try
		{
			path = new File( ShellFolder.SYSTEM.getAbsolutePath( getControl( ).handle ),
					"taskmgr.exe" ).getCanonicalPath( );
			taskmgrItem.setImage( IShellFolder.getIcon( IShellFolder.getCanonicalPIDL( IShellFolder.getDesktopFolder( getControl( ).handle ),
					path ),
					false ) );
		}
		catch ( IOException e )
		{
			taskmgrItem.dispose( );
			return;
		}
		Composite composite = WidgetUtil.getToolkit( )
				.createComposite( tabFolder );
		GridLayout layout = new GridLayout( );
		composite.setLayout( layout );

		final NativeControl taskmgr = new NativeControl( composite, SWT.NONE );
		taskmgr.setStartCommand( "\"" + path + "\"", true );
		GridData gd = new GridData( GridData.FILL_BOTH );
		taskmgr.setLayoutData( gd );
		taskmgrItem.setControl( composite );
		taskmgr.addDisposeListener( new DisposeListener( ) {

			public void widgetDisposed( DisposeEvent arg0 )
			{
				taskmgrItem.dispose( );
			}
		} );
	}

	private void createTitle( )
	{
		Label label = WidgetUtil.getToolkit( )
				.createLabel( container,
						"This page demonstrates how to embed native controls into swt program.",
						SWT.WRAP );
		GridData gd = new GridData( );
		gd.widthHint = 300;
		gd.horizontalAlignment = SWT.FILL;
		label.setLayoutData( gd );
	}

	public Composite getControl( )
	{
		return container;
	}

	public String getDisplayName( )
	{
		return "Native Control Example";
	}

}
