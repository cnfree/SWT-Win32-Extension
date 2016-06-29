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

import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Tree;
import org.sf.feeling.swt.win32.extension.example.provider.FileTableContentProvider;
import org.sf.feeling.swt.win32.extension.example.provider.FileTableLabelProvider;
import org.sf.feeling.swt.win32.extension.example.provider.FolderTreeContentProvider;
import org.sf.feeling.swt.win32.extension.example.provider.FolderTreeLabelProvider;
import org.sf.feeling.swt.win32.extension.example.util.WidgetUtil;
import org.sf.feeling.swt.win32.extension.shell.IShellFolder;
import org.sf.feeling.swt.win32.extension.shell.IShellFolder.CanonicalPIDL;
import org.sf.feeling.swt.win32.extension.shell.ShellFolder;

public class ShellExplorerPage extends SimpleTabPage
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
		createRegistryArea( );
	}

	private void createRegistryArea( )
	{

		SashForm sash = new SashForm( container, SWT.HORIZONTAL );
		Composite treeComposite = new Composite( sash, SWT.NONE );
		treeComposite.setBackground( WidgetUtil.getToolkit( )
				.getColors( )
				.getForeground( ) );
		GridLayout layout = new GridLayout( );
		layout.marginHeight = layout.marginWidth = 1;
		treeComposite.setLayout( layout );
		Tree tree = WidgetUtil.getToolkit( ).createTree( treeComposite,
				SWT.V_SCROLL | SWT.H_SCROLL | SWT.VIRTUAL );
		final TreeViewer folderViewer = new TreeViewer( tree );
		folderViewer.setLabelProvider( new FolderTreeLabelProvider( ) );
		folderViewer.setContentProvider( new FolderTreeContentProvider( ) );
		folderViewer.getTree( )
				.setLayoutData( new GridData( GridData.FILL_BOTH ) );
		folderViewer.addDoubleClickListener( new IDoubleClickListener( ) {

			public void doubleClick( DoubleClickEvent e )
			{
				Object selection =  ( (StructuredSelection) e.getSelection( ) ).getFirstElement( );
				if(selection instanceof String){
					String path = (String) ( (StructuredSelection) e.getSelection( ) ).getFirstElement( );
					CanonicalPIDL pidl = IShellFolder.getCanonicalPIDL( IShellFolder.getDesktopFolder( container.handle ),
							path );
					IShellFolder.launchPIDL( pidl );
					if ( pidl != null )
						pidl.dispose( );
				}
				else if(selection instanceof IShellFolder){
					CanonicalPIDL pidl = ShellFolder.getFolderPIDL( ShellFolder.DESKTOP );
					IShellFolder.launchPIDL( pidl );
					if ( pidl != null )
						pidl.dispose( );
				}
			}
		} );
		folderViewer.setAutoExpandLevel( 2 );

		Composite tableComposite = new Composite( sash, SWT.NONE );
		tableComposite.setLayout( layout );
		Table table = WidgetUtil.getToolkit( ).createTable( tableComposite,
				SWT.V_SCROLL | SWT.H_SCROLL | SWT.VIRTUAL );
		final TableViewer fileTable = new TableViewer( table );
		table.setLinesVisible( false );
		table.setHeaderVisible( true );
		fileTable.getTable( )
				.setLayoutData( new GridData( GridData.FILL_BOTH ) );
		fileTable.setLabelProvider( new FileTableLabelProvider( IShellFolder.getDesktopFolder( container.handle ) ) );
		fileTable.setContentProvider( new FileTableContentProvider( IShellFolder.getDesktopFolder( container.handle ) ) );
		fileTable.addDoubleClickListener( new IDoubleClickListener( ) {

			public void doubleClick( DoubleClickEvent e )
			{
				String path = (String) ( (StructuredSelection) e.getSelection( ) ).getFirstElement( );
				CanonicalPIDL pidl = IShellFolder.getCanonicalPIDL( IShellFolder.getDesktopFolder( container.handle ),
						path );
				IShellFolder.launchPIDL( pidl );
				if ( pidl != null )
					pidl.dispose( );
			}
		} );
		String[] columns = new String[]{
				"Name", "Size"
		};

		int[] widths = new int[]{
				230, 100
		};

		for ( int i = 0; i < columns.length; i++ )
		{
			TableColumn column = new TableColumn( table, SWT.LEFT );
			column.setResizable( columns[i] != null );
			if ( columns[i] != null )
			{
				column.setText( columns[i] );
			}
			column.setWidth( widths[i] );
		}

		fileTable.setColumnProperties( columns );

		sash.setWeights( new int[]{
				7, 13
		} );
		sash.setLayoutData( new GridData( GridData.FILL_BOTH ) );

		folderViewer.setInput( new Object[]{
			IShellFolder.getDesktopFolder( container.handle )
		} );
		folderViewer.addSelectionChangedListener( new ISelectionChangedListener( ) {

			public void selectionChanged( SelectionChangedEvent event )
			{
				TreeSelection selection = (TreeSelection) event.getSelection( );
				if ( selection.getFirstElement( ) != null )
					fileTable.setInput( selection.getFirstElement( ) );
			}
		} );
	}

	private void createTitle( )
	{
		Label label = WidgetUtil.getToolkit( )
				.createLabel( container,
						"This page demonstrates a simple shell exlporer created by using IShellFolder Interface methods.",
						SWT.WRAP );
		GridData gd = new GridData( );
		gd.horizontalSpan = 2;
		label.setLayoutData( gd );
	}

	public Composite getControl( )
	{
		return container;
	}

	public String getDisplayName( )
	{
		return "Shell Explorer";
	}

}
