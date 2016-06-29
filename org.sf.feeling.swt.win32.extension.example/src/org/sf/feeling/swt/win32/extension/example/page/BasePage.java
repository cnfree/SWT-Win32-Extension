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

import java.util.HashMap;

import org.eclipse.jface.util.SafeRunnable;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.sf.feeling.swt.win32.extension.example.provider.ICategoryProvider;
import org.sf.feeling.swt.win32.extension.example.widget.FormWidgetFactory;
import org.sf.feeling.swt.win32.extension.example.widget.Tab;
import org.sf.feeling.swt.win32.extension.example.widget.TabbedPropertyList;
import org.sf.feeling.swt.win32.extension.example.widget.TabbedPropertyTitle;

public class BasePage
{

	private TabbedPropertyList categoryList;

	private Composite container;

	private ScrolledComposite sComposite;

	private Composite infoPane;

	private ICategoryProvider categoryProvider;

	public void buildUI( Composite parent )
	{
		container = new Composite( parent, SWT.NONE );
		GridLayout layout = new GridLayout( );
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		layout.horizontalSpacing = 0;
		layout.verticalSpacing = 0;
		layout.numColumns = 2;
		container.setLayout( layout );

		categoryList = new TabbedPropertyList( container );
		GridData gd = new GridData( GridData.FILL_VERTICAL );
		gd.verticalSpan = 2;
		categoryList.setLayoutData( gd );
		categoryList.addListener( SWT.Selection, new Listener( ) {

			public void handleEvent( Event event )
			{
				SafeRunnable.run( new SafeRunnable( ) {

					public void run( )
					{
						processListSelected( );
					}
				} );
			}
		} );
		setCategoryProvider( categoryProvider );
		title = new TabbedPropertyTitle( container,
				FormWidgetFactory.getInstance( ) );
		title.setLayoutData( new GridData( GridData.FILL_HORIZONTAL ) );
		sComposite = new ScrolledComposite( container, SWT.H_SCROLL
				| SWT.V_SCROLL );
		sComposite.setLayoutData( new GridData( GridData.FILL_BOTH ) );
		sComposite.setExpandHorizontal( true );
		sComposite.setExpandVertical( true );
		sComposite.addControlListener( new ControlAdapter( ) {

			public void controlResized( ControlEvent e )
			{
				computeSize( );
			}
		} );
		infoPane = new Composite( sComposite, SWT.NONE );
		sComposite.setContent( infoPane );
		layout = new GridLayout( );
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		infoPane.setLayout( layout );
	}

	private void computeSize( )
	{
		// sComposite.setMinSize( infoPane.computeSize( SWT.DEFAULT, SWT.DEFAULT
		// ) );
		infoPane.layout( );
	}

	public int getSelectionIndex( )
	{
		return categoryList.getSelectionIndex( );
	}

	private void processListSelected( )
	{
		if ( categoryProvider == null )
		{
			return;
		}

		int index = categoryList.getSelectionIndex( );
		if ( index == -1 )
		{
			return;
		}

		TabPage page = getCategoryPane( categoryList.getSelectionIndex( ) );
		title.setTitle( page.getDisplayName( ), null );
		showPage( page );
	}

	public void setSelection( int index )
	{
		categoryList.setSelection( index );
		TabPage page = getCategoryPane( categoryList.getSelectionIndex( ) );
		title.setTitle( page.getDisplayName( ), null );
		showPage( page );
	}

	private TabPage currentPage;

	private TabbedPropertyTitle title;

	private void showPage( TabPage page )
	{
		if ( page != currentPage )
		{
			if ( currentPage != null )
			{
				( (GridData) currentPage.getControl( ).getLayoutData( ) ).exclude = true;
				currentPage.getControl( ).setVisible( false );
				currentPage.deActivate( );
			}
			( (GridData) page.getControl( ).getLayoutData( ) ).exclude = false;
			page.getControl( ).setVisible( true );
			currentPage = page;
			computeSize( );
			currentPage.activate( );
			page.refresh( );
		}
	}

	public void setCategoryProvider( ICategoryProvider categoryProvider )
	{
		this.categoryProvider = categoryProvider;
		if ( categoryProvider == null )
		{
			return;
		}
		if ( categoryList == null )
			return;
		ICategoryPage[] pages = categoryProvider.getCategories( );
		if ( pages.length != 0 )
		{
			Tab[] categoryLabels = new Tab[pages.length];
			for ( int i = 0; i < pages.length; i++ )
			{
				categoryLabels[i] = new Tab( );
				categoryLabels[i].setText( pages[i].getDisplayLabel( ) );
			}
			categoryList.setElements( categoryLabels );
			if ( categoryList.getTabList( ).length > 0 )
			{
				categoryList.setSelection( 0 );
			}
		}
	}

	public void refresh( )
	{
		processListSelected( );
	}

	HashMap pageMap;

	private TabPage getCategoryPane( int index )
	{
		if ( pageMap == null )
		{
			pageMap = new HashMap( categoryProvider.getCategories( ).length );
		}
		String key = Integer.toString( index );
		TabPage page = (TabPage) pageMap.get( key );
		if ( page == null )
		{
			page = categoryProvider.getCategories( )[index].createPage( );
			page.buildUI( infoPane );
			computeSize( );
		}
		page.getControl( ).setLayoutData( new GridData( GridData.FILL_BOTH ) );
		pageMap.put( key, page );
		return page;
	}
}