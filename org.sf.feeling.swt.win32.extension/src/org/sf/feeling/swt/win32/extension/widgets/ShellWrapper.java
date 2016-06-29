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

package org.sf.feeling.swt.win32.extension.widgets;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.sf.feeling.swt.win32.extension.shell.Windows;
import org.sf.feeling.swt.win32.extension.widgets.listener.MenuAdapter;
import org.sf.feeling.swt.win32.internal.extension.util.LONG;
import org.sf.feeling.swt.win32.internal.extension.widgets.UIManager;

/**
 * A shell custom style wrapper, default style is vista style.
 * 
 * @author <a href="mailto:cnfree2000@hotmail.com">cnfree</a>
 * 
 */
public class ShellWrapper extends Window
{

	private Layout oldLayout;

	private Menu shellMenu;

	private boolean removeMarign = true;

	public boolean isRemoveMarign( )
	{
		return removeMarign;
	}

	public void setRemoveMarign( boolean removeMarign )
	{
		this.removeMarign = removeMarign;
	}

	/**
	 * Wrap a shell.
	 * 
	 * @param shell
	 *            shell.
	 */
	public ShellWrapper( Shell shell )
	{
		super( );
		this.shell = shell;

	}

	/**
	 * Wrap a shell with specified style.
	 * 
	 * @param shell
	 *            shell.
	 * @param theme
	 *            now only support ThemeConstants.STYLE_VISTA.
	 */
	public ShellWrapper( Shell shell, String theme )
	{
		super( );
		setTheme( theme );
		this.shell = shell;
	}

	public void installTheme( )
	{
		if ( ( shell.getStyle( ) & SWT.TITLE ) != 0 )
		{
			if ( !isThemeInstalled( ) )
			{
				createWidget( shell, SWT.NONE );
				initWindow( shell.getStyle( ) );
				theamOnSelf( isRemoveMarign( ) );
			}
			UIManager.installTheme( getTheme( ), this );
		}
	}

	public void installTheme( final String theme )
	{
		setTheme( theme );
		if ( shell == null )
			return;
		boolean max = shell.getMaximized( );
		boolean visible = shell.getVisible( );
		if ( ( shell.getStyle( ) & SWT.TITLE ) != 0 )
		{
			if ( !isThemeInstalled( ) )
			{
				shell.setVisible( false );
				createWidget( shell, SWT.NONE );
				initWindow( shell.getStyle( ) );
				theamOnSelf( isRemoveMarign( ) );
				if ( !max )
				{
					Rectangle rect = shell.getBounds( );
					shell.setBounds( rect.x,
							rect.y,
							rect.width + Windows.getWindowBorderWidth( ) * 2,
							rect.height + Windows.getWindowBorderWidth( ) * 2 );
				}
			}
			UIManager.installTheme( getTheme( ), this );
			shell.setVisible( visible );
		}
	}

	protected Composite createContents( Composite parent, int style )
	{
		hookShellUI( );
		Composite composite = new Composite( shell, SWT.NONE );
		composite.setBackgroundMode( SWT.INHERIT_DEFAULT );
		composite.setData( "Container" );
		return composite;
	}

	protected Composite getBorderContainer( )
	{
		Composite composite = null;
		List childrens = new ArrayList( );
		childrens.addAll( Arrays.asList( shell.getChildren( ) ) );
		for ( int i = 0; i < childrens.size( ); i++ )
		{
			Control control = (Control) childrens.get( i );
			if ( control.getData( ) != null
					&& control.getData( ).equals( "Container" ) )
			{
				composite = ( (Composite) control );
				break;
			}
		}
		return composite;
	}

	/**
	 * Open the shell.
	 */
	public void open( )
	{
		if ( !this.isThemeInstalled( ) )
			installTheme( );
		shell.open( );
	}

	private void theamOnSelf( boolean removeMargin )
	{
		List childrens = new ArrayList( );
		childrens.addAll( Arrays.asList( shell.getChildren( ) ) );
		Composite composite = null;
		for ( int i = 0; i < childrens.size( ); i++ )
		{
			Control control = (Control) childrens.get( i );
			if ( control.getData( ) != null
					&& control.getData( ).equals( "Container" ) )
			{
				composite = ( (Composite) control );
				break;
			}

		}
		if ( composite != null )
		{
			childrens.remove( composite );
			oldLayout = shell.getLayout( );
			getClientArea( ).setLayout( shell.getLayout( ) );
			getClientArea( ).setBackgroundMode( shell.getBackgroundMode( ) );
			getClientArea( ).setBackground( shell.getBackground( ) );
			getClientArea( ).setBackgroundImage( shell.getBackgroundImage( ) );
			getClientArea( ).setForeground( shell.getForeground( ) );
			getClientArea( ).setFont( shell.getFont( ) );
			try
			{
				Layout layout = getClientArea( ).getLayout( );
				if ( layout != null
						&& hasMarginSetting( layout )
						&& removeMargin )
				{
					Field field = layout.getClass( )
							.getDeclaredField( "marginWidth" );
					field.setAccessible( true );
					field.setInt( layout, 0 );
					field = layout.getClass( )
							.getDeclaredField( "marginHeight" );
					field.setAccessible( true );
					field.setInt( layout, 0 );
				}
			}
			catch ( Exception e )
			{
				SWT.error( SWT.ERROR_UNSPECIFIED, e );
			}
			if ( shell.getMenuBar( ) != null )
			{
				shellMenu = shell.getMenuBar( );
				shell.setMenuBar( null );
				createMenuBar( );
				MenuHolderManager.registryShortcut( getMenuBar( ) );
			}

			for ( int i = 0; i < childrens.size( ); i++ )
			{
				( (Control) childrens.get( i ) ).setParent( getClientArea( ) );
			}
			shell.setLayout( new FillLayout( ) );
			layout( );
		}
	}

	private boolean hasMarginSetting( Layout layout )
	{
		Field[] fields = layout.getClass( ).getDeclaredFields( );
		for ( int i = 0; i < fields.length; i++ )
		{
			if ( "marginWidth".equals( fields[i].getName( ) ) )
				return true;
		}
		return false;
	}

	private CMenuItem createMenuItem( final MenuItem item )
	{
		final CMenuItem cItem = new CMenuItem( item.getStyle( ) );
		String text = item.getText( );
		String[] splits = text.split( "\t" );
		cItem.setText( splits[0] );
		cItem.setImage( item.getImage( ) );
		cItem.setEnabled( item.isEnabled( ) );
		cItem.setSelection( item.getSelection( ) );
		if ( splits.length > 1 )
		{
			Shortcut shortcut = new Shortcut( splits[1] );
			cItem.setShortcut( shortcut );
		}
		cItem.addSelectionListener( new SelectionAdapter( ) {

			public void widgetSelected( SelectionEvent e )
			{
				if ( !item.isDisposed( ) )
				{
					Event event = new Event( );
					event.data = e.data;
					event.detail = e.detail;
					event.widget = item;
					event.doit = e.doit;
					event.display = e.display;
					item.notifyListeners( SWT.Selection, event );
				}
			}
		} );

		if ( item.getMenu( ) != null )
		{
			Menu menu = item.getMenu( );
			CMenu cMenu = createCMenu( menu );
			cItem.setMenu( cMenu );
		}
		return cItem;
	}

	private CMenu createCMenu( final Menu menu )
	{
		final CMenu cMenu = new CMenu( );
		cMenu.addMenuListener( new MenuAdapter( ) {

			public void menuShown( Event e )
			{
				if ( !menu.isDisposed( ) )
				{
					menu.notifyListeners( SWT.Show, e );
					MenuHolderManager.deRegistryShortcut( getMenuBar( ) );
					cMenu.removeAll( );
					createMenu( menu, cMenu );
					MenuHolderManager.registryShortcut( getMenuBar( ) );
				}
			}

			public void menuHidden( Event e )
			{
				if ( !menu.isDisposed( ) )
				{
					menu.notifyListeners( SWT.Hide, e );
				}
			}
		} );
		createMenu( menu, cMenu );
		return cMenu;
	}

	private void createMenu( final Menu menu, final CMenu cMenu )
	{
		for ( int i = 0; i < menu.getItemCount( ); i++ )
		{
			final CMenuItem item = createMenuItem( menu.getItem( i ) );
			cMenu.addItem( item );
			if ( !menu.equals( shellMenu ) )
				continue;
			menu.getItem( i ).addDisposeListener( new DisposeListener( ) {

				public void widgetDisposed( DisposeEvent e )
				{
					Display.getDefault( ).asyncExec( new Runnable( ) {

						public void run( )
						{
							if ( !menu.isDisposed( ) )
							{
								refreshMenuBar( );
							}
						}
					} );
				}
			} );

		}
	}

	/**
	 * Uninstall custom style wrapper.
	 */
	public void unWrapper( )
	{
		boolean max = shell.getMaximized( );
		if ( !isThemeInstalled( ) )
			return;
		if ( oldLayout == null )
			shell.setLayout( getClientArea( ).getLayout( ) );
		shell.setLayout( oldLayout );
		Control[] children = getClientArea( ).getChildren( );
		for ( int i = 0; i < children.length; i++ )
		{
			children[i].setParent( shell );
		}
		getBorderContainer( ).dispose( );
		if ( shellMenu != null )
			shell.setMenuBar( shellMenu );
		shell.layout( );

		if ( !max )
		{
			Rectangle rect = shell.getBounds( );
			shell.setBounds( rect.x,
					rect.y,
					rect.width - Windows.getWindowBorderWidth( ) * 2,
					rect.height - Windows.getWindowBorderWidth( ) * 2 );
		}

		unHookShellUI( );
		redrawShell( );
	}

	public Menu getShellMenu( )
	{
		return shellMenu;
	}

	private boolean refreshed = false;

	public void refreshMenuBar( )
	{
		if ( !refreshed )
		{
			refreshed = true;
			new Thread( ) {

				public void run( )
				{
					try
					{
						Thread.sleep( 200 );
					}
					catch ( InterruptedException e )
					{
					}

					Display.getDefault( ).syncExec( new Runnable( ) {

						public void run( )
						{
							if ( shellMenu != null && !shellMenu.isDisposed( ) )
							{
								createMenuBar( );
							}
						}
					} );
					refreshed = false;
				}
			}.start( );
		}
	}

	private HashMap fixedItemMap = new HashMap( );

	public void addFixedMenuItem( int index, CMenuItem item )
	{
		fixedItemMap.put( new LONG( index ), item );
	}

	private void createMenuBar( )
	{
		if ( getMenuBar( ) == null || getMenuBar( ).getControl( ).isDisposed( ) )
			return;
		getMenuBar( ).getMenu( ).removeAll( );
		if ( shellMenu != null )
			createMenu( shellMenu, getMenuBar( ).getMenu( ) );
		if ( !fixedItemMap.isEmpty( ) )
		{
			Iterator iter = fixedItemMap.entrySet( ).iterator( );
			while ( iter.hasNext( ) )
			{
				Entry entry = (Entry) iter.next( );
				int index = ( (LONG) entry.getKey( ) ).value;
				if ( index > -1
						&& index < getMenuBar( ).getMenu( ).getItemCount( ) )
				{
					getMenuBar( ).getMenu( ).addItem( index,
							(CMenuItem) entry.getValue( ) );
				}
				else
				{
					getMenuBar( ).getMenu( )
							.addItem( (CMenuItem) entry.getValue( ) );
				}
			}
		}
		getMenuBar( ).refresh( );
	}
}
