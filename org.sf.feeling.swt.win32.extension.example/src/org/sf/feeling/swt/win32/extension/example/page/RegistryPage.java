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

import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
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
import org.sf.feeling.swt.win32.extension.example.provider.RegistryTableContentProvider;
import org.sf.feeling.swt.win32.extension.example.provider.RegistryTableLabelProvider;
import org.sf.feeling.swt.win32.extension.example.provider.RegistryTreeContentProvider;
import org.sf.feeling.swt.win32.extension.example.provider.RegistryTreeLabelProvider;
import org.sf.feeling.swt.win32.extension.example.util.WidgetUtil;
import org.sf.feeling.swt.win32.extension.shell.ShellFolder;

public class RegistryPage extends SimpleTabPage
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
				SWT.V_SCROLL | SWT.H_SCROLL );
		TreeViewer registryViewer = new TreeViewer( tree );
		registryViewer.setLabelProvider( new RegistryTreeLabelProvider( ) );
		registryViewer.setContentProvider( new RegistryTreeContentProvider( ) );
		registryViewer.getTree( )
				.setLayoutData( new GridData( GridData.FILL_BOTH ) );
		registryViewer.setAutoExpandLevel( 2 );

		Composite tableComposite = new Composite( sash, SWT.NONE );
		tableComposite.setLayout( layout );
		Table table = WidgetUtil.getToolkit( ).createTable( tableComposite,
				SWT.V_SCROLL | SWT.H_SCROLL );
		final TableViewer registryTable = new TableViewer( table );
		table.setLinesVisible( false );
		table.setHeaderVisible( true );
		registryTable.getTable( )
				.setLayoutData( new GridData( GridData.FILL_BOTH ) );
		registryTable.setLabelProvider( new RegistryTableLabelProvider( ) );
		registryTable.setContentProvider( new RegistryTableContentProvider( ) );

		String[] columns = new String[]{
				"Name", "Type", "Data"
		};

		int[] widths = new int[]{
				100, 80, 150
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

		registryTable.setColumnProperties( columns );

		sash.setWeights( new int[]{
				7, 13
		} );
		sash.setLayoutData( new GridData( GridData.FILL_BOTH ) );

		registryViewer.setInput( new Object[]{
			ShellFolder.COMPUTER
		} );
		registryViewer.addSelectionChangedListener( new ISelectionChangedListener( ) {

			public void selectionChanged( SelectionChangedEvent event )
			{
				TreeSelection selection = (TreeSelection) event.getSelection( );
				if ( selection.getFirstElement( ) != null )
					registryTable.setInput( selection.getFirstElement( ) );
			}

		} );
	}

	private void createTitle( )
	{
		Label label = WidgetUtil.getToolkit( )
				.createLabel( container,
						"This page demonstrates a simple registry viewer created by using RegistryKey class methods.",
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
		return "Registry Example";
	}

}
