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

package org.sf.feeling.swt.win32.extension.example;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.program.Program;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.sf.feeling.swt.win32.extension.Win32;
import org.sf.feeling.swt.win32.extension.example.page.BasePage;
import org.sf.feeling.swt.win32.extension.example.page.ICategoryPage;
import org.sf.feeling.swt.win32.extension.example.provider.CategoryProviderFactory;
import org.sf.feeling.swt.win32.extension.example.provider.ICategoryProvider;
import org.sf.feeling.swt.win32.extension.graphics.GraphicsUtil;
import org.sf.feeling.swt.win32.extension.hook.Hook;
import org.sf.feeling.swt.win32.extension.hook.JournalPlaybackHook;
import org.sf.feeling.swt.win32.extension.hook.JournalRecordHook;
import org.sf.feeling.swt.win32.extension.shell.ApplicationBar;
import org.sf.feeling.swt.win32.extension.shell.SystemMenuItem;
import org.sf.feeling.swt.win32.extension.shell.SystemMenuManager;
import org.sf.feeling.swt.win32.extension.shell.Windows;
import org.sf.feeling.swt.win32.extension.widgets.CMenu;
import org.sf.feeling.swt.win32.extension.widgets.CMenuItem;
import org.sf.feeling.swt.win32.extension.widgets.CToolBar;
import org.sf.feeling.swt.win32.extension.widgets.CToolItem;
import org.sf.feeling.swt.win32.extension.widgets.MenuBar;
import org.sf.feeling.swt.win32.extension.widgets.MenuControl;
import org.sf.feeling.swt.win32.extension.widgets.PopupMenu;
import org.sf.feeling.swt.win32.extension.widgets.Separator;
import org.sf.feeling.swt.win32.extension.widgets.ShellWrapper;
import org.sf.feeling.swt.win32.extension.widgets.Shortcut;
import org.sf.feeling.swt.win32.extension.widgets.ThemeConstants;
import org.sf.feeling.swt.win32.extension.widgets.listener.MenuAdapter;
import org.sf.feeling.swt.win32.extension.widgets.theme.BlackGlossyThemeRender;
import org.sf.feeling.swt.win32.extension.widgets.theme.GlossyThemeRender;
import org.sf.feeling.swt.win32.extension.widgets.theme.Office2003ThemeRender;
import org.sf.feeling.swt.win32.extension.widgets.theme.Office2007ThemeRender;
import org.sf.feeling.swt.win32.extension.widgets.theme.VS2003ThemeRender;
import org.sf.feeling.swt.win32.extension.widgets.theme.VS2005ThemeRender;
import org.sf.feeling.swt.win32.internal.extension.util.ImageCache;

public class Example
{

	private void initial( )
	{
		final Display display = new Display( );

		shell = new Shell( display );
		shell.setSize( 750, 490 );
		shell.setImages( new Image[]{
				ImageCache.getImage( "/eclipse.png" ),
				ImageCache.getImage( "/eclipse32.png" ),
				ImageCache.getImage( "/eclipse48.png" )
		} );
		GridLayout layout = new GridLayout( );
		layout.marginWidth = layout.marginHeight = layout.verticalSpacing = 0;
		shell.setLayout( layout );
		shell.setText( "Eclipse SWT Win32 Extension Example" );
		menubar = new MenuBar( shell, SWT.NONE );

		menubar.getControl( )
				.setLayoutData( new GridData( GridData.FILL_HORIZONTAL ) );

		menuSeparator = new Separator( shell, SWT.SHADOW_IN | SWT.HORIZONTAL );
		menuSeparator.setLayoutData( new GridData( GridData.FILL_HORIZONTAL ) );
		setLayoutData( menuSeparator, true );
		toolbar = new CToolBar( shell, CToolBar.SMALL_ICON );
		toolbar.getControl( )
				.setLayoutData( new GridData( GridData.FILL_HORIZONTAL ) );

		toolSeparator = new Separator( shell, SWT.SHADOW_IN | SWT.HORIZONTAL );
		toolSeparator.setLayoutData( new GridData( GridData.FILL_HORIZONTAL ) );
		setLayoutData( toolSeparator, true );
		Composite container = new Composite( shell, SWT.NONE );
		container.setLayout( new FillLayout( ) );
		BasePage page = new BasePage( );
		page.buildUI( container );
		container.setLayoutData( new GridData( GridData.FILL_BOTH ) );
		ICategoryProvider provider = CategoryProviderFactory.getInstance( )
				.getCategoryProvider( );

		page.setCategoryProvider( provider );
		page.refresh( );
		container.forceFocus( );

		shell.addDisposeListener( new DisposeListener( ) {

			public void widgetDisposed( DisposeEvent arg0 )
			{
				DIRTY = true;
				manager.dispose( );
				if ( Hook.MOUSE.isInstalled( Example.this ) )
					Hook.MOUSE.uninstall( Example.this );
				if ( Hook.KEYBOARD.isInstalled( Example.this ) )
					Hook.KEYBOARD.uninstall( Example.this );
				if ( JournalRecordHook.isInstalled( ) )
					JournalRecordHook.unInstallHook( );
				if ( JournalPlaybackHook.isInstalled( ) )
					JournalPlaybackHook.unInstallHook( );
				if ( Windows.isWindowTransparent( shell.handle ) )
				{
					Windows.setWindowTransparent( shell.handle, false );
				}
				Windows.hideWindowBlend( shell.handle, 1000 );
			}

		} );

		shell.layout( );
		wrapper = new ShellWrapper( shell );
		manager = new SystemMenuManager( shell );
		SystemMenuItem item = new SystemMenuItem( manager, SWT.SEPARATOR );
		final SystemMenuItem aboutItem = new SystemMenuItem( manager, SWT.NONE );
		aboutItem.setText( "About SWT Win32 Extension" );
		aboutItem.setImage( ImageCache.getImage( "/about.bmp" ) );
		aboutItem.addListener( new Listener( ) {

			public void handleEvent( Event event )
			{
				new AboutDialog( (Shell) event.widget );
			}
		} );
		manager.addItem( item );
		manager.addItem( aboutItem );

		createSystemMenu( );
		createPageMenu( page, provider );
		createDemonstrationMenu( );
		createToolBarSettingMenu( );
		createHelpMenu( );
		createExitToolItem( );

		final PopupMenu popupMenu = new PopupMenu( menubar.getControl( ) );
		final PopupMenu popupMenu1 = new PopupMenu( toolbar.getControl( ) );
		CMenu menu = new CMenu( );
		for ( int i = 0; i < menubar.getMenu( ).getItemCount( ); i++ )
			menu.addItem( menubar.getMenu( ).getItem( i ) );
		popupMenu.setMenu( menu );
		popupMenu1.setMenu( menu );
		menu.addMenuListener( new MenuAdapter( ) {

			public void menuShown( Event e )
			{
				popupMenu.setTheme( menubar.getTheme( ) );
				popupMenu.refresh( );
				popupMenu1.setTheme( toolbar.getTheme( ) );
				popupMenu1.refresh( );
			}

		} );
		if ( Win32.getWin32Version( ) >= Win32.VERSION( 5, 0 ) )
		{
			wrapper.installTheme( ThemeConstants.STYLE_OFFICE2007 );
			handleOffice2007Selection( );
		}
		else
		{
			wrapper.installTheme( ThemeConstants.STYLE_VISTA );
			handleVistaSelection( );
			office2007Style.setEnabled( false );
			glossyStyle.setEnabled( false );

		}
		glossy.setEnabled( GraphicsUtil.checkGdip( ) );

		wrapper.open( );
		// shell.open();

		while ( !shell.isDisposed( ) )
		{
			if ( !display.readAndDispatch( ) )
				display.sleep( );
		}
		display.dispose( );
	}

	private void createExitToolItem( )
	{
		CToolItem exitTool = new CToolItem( SWT.NONE );
		exitTool.setImage( ImageCache.getImage( "/exit.gif" ) );
		exitTool.setToolTip( "Exit Win32 Extension Example" );
		toolbar.addItem( exitTool );
		exitTool.addSelectionListener( new SelectionAdapter( ) {

			public void widgetSelected( SelectionEvent event )
			{
				shell.close( );
			}

		} );
	}

	private void createToolBarSettingMenu( )
	{

		final CToolItem leftItem = new CToolItem( "Left", SWT.CHECK );
		final CToolItem rightItem = new CToolItem( "Right", SWT.CHECK );
		final CToolItem topItem = new CToolItem( "Top", SWT.CHECK );
		final CToolItem bottomItem = new CToolItem( "Bottom", SWT.CHECK );

		toolbar.addItem( leftItem );
		toolbar.addItem( rightItem );
		toolbar.addItem( topItem );
		toolbar.addItem( bottomItem );
		toolbar.addItem( new CToolItem( SWT.SEPARATOR ) );

		SelectionListener listener = new SelectionAdapter( ) {

			public void widgetSelected( SelectionEvent event )
			{
				if ( event.data == leftItem )
				{
					toolbar.setStyle( SWT.LEFT );
					leftItem.setSelection( ( toolbar.getStyle( ) & SWT.LEFT ) != 0 );
					rightItem.setSelection( ( toolbar.getStyle( ) & SWT.RIGHT ) != 0 );
					topItem.setSelection( ( toolbar.getStyle( ) & SWT.TOP ) != 0 );
					bottomItem.setSelection( ( toolbar.getStyle( ) & SWT.BOTTOM ) != 0 );
				}
				else if ( event.data == rightItem )
				{
					toolbar.setStyle( SWT.RIGHT );
					leftItem.setSelection( ( toolbar.getStyle( ) & SWT.LEFT ) != 0 );
					rightItem.setSelection( ( toolbar.getStyle( ) & SWT.RIGHT ) != 0 );
					topItem.setSelection( ( toolbar.getStyle( ) & SWT.TOP ) != 0 );
					bottomItem.setSelection( ( toolbar.getStyle( ) & SWT.BOTTOM ) != 0 );
				}
				else if ( event.data == topItem )
				{
					toolbar.setStyle( SWT.TOP );
					leftItem.setSelection( ( toolbar.getStyle( ) & SWT.LEFT ) != 0 );
					rightItem.setSelection( ( toolbar.getStyle( ) & SWT.RIGHT ) != 0 );
					topItem.setSelection( ( toolbar.getStyle( ) & SWT.TOP ) != 0 );
					bottomItem.setSelection( ( toolbar.getStyle( ) & SWT.BOTTOM ) != 0 );
				}
				else if ( event.data == bottomItem )
				{
					toolbar.setStyle( SWT.BOTTOM );
					leftItem.setSelection( ( toolbar.getStyle( ) & SWT.LEFT ) != 0 );
					rightItem.setSelection( ( toolbar.getStyle( ) & SWT.RIGHT ) != 0 );
					topItem.setSelection( ( toolbar.getStyle( ) & SWT.TOP ) != 0 );
					bottomItem.setSelection( ( toolbar.getStyle( ) & SWT.BOTTOM ) != 0 );
				}
			}

		};
		leftItem.addSelectionListener( listener );
		rightItem.addSelectionListener( listener );
		topItem.addSelectionListener( listener );
		bottomItem.addSelectionListener( listener );

		leftItem.setSelection( true );
	}

	private void createHelpMenu( )
	{
		CMenuItem item = new CMenuItem( "&Help", SWT.NONE );
		menubar.getMenu( ).addItem( item );
		CMenu menu = new CMenu( );
		item.setMenu( menu );
		CMenuItem welcome = new CMenuItem( "&Welcome", SWT.NONE );
		welcome.setImage( ImageCache.getImage( "/home.gif" ) );

		menu.addItem( welcome );
		SelectionListener homeListener = new SelectionAdapter( ) {

			public void widgetSelected( SelectionEvent e )
			{
				Program.launch( "http://feeling.sf.net" );
			}

		};
		welcome.addSelectionListener( homeListener );
		CToolItem welcomeTool = new CToolItem( "Welcome", SWT.NONE );
		welcomeTool.setImage( ImageCache.getImage( "/home.gif" ) );
		welcomeTool.addSelectionListener( homeListener );
		toolbar.addItem( welcomeTool );

		SelectionListener bugListener = new SelectionAdapter( ) {

			public void widgetSelected( SelectionEvent e )
			{
				Program.launch( "http://sourceforge.net/tracker/?group_id=182187" );
			}

		};

		CMenuItem reportBug = new CMenuItem( "&Report Bug", SWT.NONE );
		reportBug.setImage( ImageCache.getImage( "/bug.gif" ) );
		menu.addItem( reportBug );
		reportBug.addSelectionListener( bugListener );
		CToolItem reportBugTool = new CToolItem( "Report Bug", SWT.NONE );
		reportBugTool.setImage( ImageCache.getImage( "/bug.gif" ) );
		reportBugTool.addSelectionListener( bugListener );
		toolbar.addItem( reportBugTool );

		menu.addItem( new CMenuItem( null, SWT.SEPARATOR ) );

		CMenuItem about = new CMenuItem( "&About SWT Win32 Extension", SWT.NONE );
		menu.addItem( about );
		about.addSelectionListener( new SelectionAdapter( ) {

			public void widgetSelected( SelectionEvent e )
			{
				new AboutDialog( shell );
			}

		} );

		toolbar.addItem( new CToolItem( null, SWT.SEPARATOR ) );

	}

	private void createDemonstrationMenu( )
	{
		CMenuItem item = new CMenuItem( "&Demonstration", SWT.NONE );
		menubar.getMenu( ).addItem( item );
		CMenu menu = new CMenu( );
		item.setMenu( menu );
		createHelloWorldItem( menu );
		menu.addItem( new CMenuItem( null, SWT.SEPARATOR ) );
		createTransparentItem( menu );
		createOnTopItem( menu );
		createFlashItem( menu );
		menu.addItem( new CMenuItem( null, SWT.SEPARATOR ) );
		createVistaItem( menu );
		createOffice2007Item( menu );
		createGlossyItem( menu );

		menu.addItem( new CMenuItem( null, SWT.SEPARATOR ) );
		createTaskbarItem( menu );

		CToolItem styleToolItem = new CToolItem( "Style", SWT.NONE );
		styleToolItem.addSelectionListener( new SelectionAdapter( ) {

			public void widgetSelected( SelectionEvent event )
			{
				Example.getInstance( ).getWrapper( ).unWrapper( );
			}

		} );
		toolbar.addItem( styleToolItem );
		CMenu toolMenu = new CMenu( );
		toolMenu.addItem( menu.getItem( "&Vista Style" ) );
		toolMenu.addItem( menu.getItem( "&Office2007 Style" ) );
		toolMenu.addItem( menu.getItem( "&Glossy Style" ) );
		styleToolItem.setMenu( toolMenu );

		toolbar.addItem( new CToolItem( SWT.SEPARATOR ) );
	}

	private void createGlossyItem( CMenu menu )
	{
		glossyStyle = new CMenuItem( "&Glossy Style", SWT.CHECK ) {

			public boolean getSelection( )
			{
				return wrapper != null
						&& wrapper.isThemeInstalled( )
						&& ThemeConstants.STYLE_GLOSSY.equals( wrapper.getTheme( ) );
			}
		};
		glossyStyle.setShortcut( new Shortcut( true, false, false, 'B' ) );
		menu.addItem( glossyStyle );

		SelectionListener listener = new SelectionAdapter( ) {

			public void widgetSelected( SelectionEvent e )
			{
				if ( !glossyStyle.getSelection( ) )
				{
					Example.getInstance( )
							.getWrapper( )
							.installTheme( ThemeConstants.STYLE_GLOSSY );
					handleGlossySelection( );
				}
				else
				{
					Example.getInstance( ).getWrapper( ).unWrapper( );
				}
				if ( Windows.isWindowTransparent( shell.handle ) )
				{
					Display.getDefault( ).asyncExec( new Runnable( ) {

						public void run( )
						{
							Windows.setWindowTransparent( shell.handle, true );
						}
					} );

				}
			}
		};
		glossyStyle.addSelectionListener( listener );
	}

	private void createHelloWorldItem( CMenu menu )
	{
		CMenuItem helloWorld = new CMenuItem( "&Hello World", SWT.CHECK ) {

			public boolean getSelection( )
			{
				return example != null;
			}
		};
		helloWorld.setShortcut( new Shortcut( true, false, false, 'W' ) );
		menu.addItem( helloWorld );
		helloWorld.addSelectionListener( new SelectionAdapter( ) {

			public void widgetSelected( SelectionEvent e )
			{
				if ( example == null )
				{
					example = new HelloWorld( shell );
					example.getShell( )
							.addDisposeListener( new DisposeListener( ) {

								public void widgetDisposed( DisposeEvent e )
								{
									example = null;
								}

							} );
					example.open( );
				}
				else
				{
					example.close( );
				}
			}
		} );
	}

	private void createTaskbarItem( CMenu menu )
	{
		CMenuItem taskBar = new CMenuItem( "Task &Bar", SWT.NONE );
		menu.addItem( taskBar );
		CMenu taskBarMenu = new CMenu( );
		taskBar.setMenu( taskBarMenu );
		final CMenuItem autoHidden = new CMenuItem( "&Auto Hidden", SWT.CHECK ) {

			public boolean getSelection( )
			{
				return ApplicationBar.getAppBarState( shell.handle ) == Win32.STATE_AUTOHIDE
						|| ApplicationBar.getAppBarState( shell.handle ) == Win32.STATE_AUTOHIDE_ALWAYSONTOP;
			}
		};
		final CMenuItem alwaysOnTop = new CMenuItem( "&Always &On Top",
				SWT.CHECK ) {

			public boolean getSelection( )
			{
				return ApplicationBar.getAppBarState( shell.handle ) == Win32.STATE_ALWAYSONTOP
						|| ApplicationBar.getAppBarState( shell.handle ) == Win32.STATE_AUTOHIDE_ALWAYSONTOP;
			}
		};
		taskBarMenu.addItem( autoHidden );
		taskBarMenu.addItem( alwaysOnTop );
		SelectionListener listener = new SelectionAdapter( ) {

			public void widgetSelected( SelectionEvent e )
			{
				if ( e.data instanceof CMenuItem )
				{
					CMenuItem item = (CMenuItem) e.data;
					final boolean isAlwaysOnTop = item == alwaysOnTop ? !alwaysOnTop.getSelection( )
							: alwaysOnTop.getSelection( );
					final boolean isAutoHidden = item == autoHidden ? !autoHidden.getSelection( )
							: autoHidden.getSelection( );
					Display.getDefault( ).asyncExec( new Runnable( ) {

						public void run( )
						{
							if ( isAlwaysOnTop && isAutoHidden )
							{
								ApplicationBar.setAppBarState( shell.handle,
										Win32.STATE_AUTOHIDE_ALWAYSONTOP );
							}
							else if ( isAlwaysOnTop )
							{
								ApplicationBar.setAppBarState( shell.handle,
										Win32.STATE_ALWAYSONTOP );
							}
							else if ( isAutoHidden )
							{
								ApplicationBar.setAppBarState( shell.handle,
										Win32.STATE_AUTOHIDE );
							}
							else
							{
								ApplicationBar.setAppBarState( shell.handle,
										Win32.STATE_NONE );
							}
						}
					} );

				}
			}
		};
		autoHidden.addSelectionListener( listener );
		alwaysOnTop.addSelectionListener( listener );
		if ( Win32.getWin32Version( ) <= Win32.VERSION( 5, 0 ) )
		{
			autoHidden.setEnabled( false );
			alwaysOnTop.setEnabled( false );
		}

	}

	private void createVistaItem( CMenu menu )
	{
		vista = new CMenuItem( "&Vista Style", SWT.CHECK ) {

			public boolean getSelection( )
			{
				return wrapper != null
						&& wrapper.isThemeInstalled( )
						&& ThemeConstants.STYLE_VISTA.equals( wrapper.getTheme( ) );
			}
		};

		vista.setShortcut( new Shortcut( true, false, false, 'V' ) );
		menu.addItem( vista );

		SelectionListener listener = new SelectionAdapter( ) {

			public void widgetSelected( SelectionEvent e )
			{
				if ( !vista.getSelection( ) )
				{
					Example.getInstance( )
							.getWrapper( )
							.installTheme( ThemeConstants.STYLE_VISTA );
					handleVistaSelection( );
				}
				else
				{
					Example.getInstance( ).getWrapper( ).unWrapper( );
				}
				if ( Windows.isWindowTransparent( shell.handle ) )
				{
					Display.getDefault( ).asyncExec( new Runnable( ) {

						public void run( )
						{
							Windows.setWindowTransparent( shell.handle, true );
						}
					} );

				}
			}
		};
		vista.addSelectionListener( listener );
	}

	private void createOffice2007Item( CMenu menu )
	{
		office2007Style = new CMenuItem( "&Office2007 Style", SWT.CHECK ) {

			public boolean getSelection( )
			{
				return wrapper != null
						&& wrapper.isThemeInstalled( )
						&& ThemeConstants.STYLE_OFFICE2007.equals( wrapper.getTheme( ) );
			}
		};
		office2007Style.setShortcut( new Shortcut( true, false, false, 'O' ) );
		menu.addItem( office2007Style );

		SelectionListener listener = new SelectionAdapter( ) {

			public void widgetSelected( SelectionEvent e )
			{
				if ( !office2007Style.getSelection( ) )
				{
					Example.getInstance( )
							.getWrapper( )
							.installTheme( ThemeConstants.STYLE_OFFICE2007 );
					handleOffice2007Selection( );
				}
				else
				{
					Example.getInstance( ).getWrapper( ).unWrapper( );
				}
				if ( Windows.isWindowTransparent( shell.handle ) )
				{
					Display.getDefault( ).asyncExec( new Runnable( ) {

						public void run( )
						{
							Windows.setWindowTransparent( shell.handle, true );
						}
					} );

				}
			}
		};
		office2007Style.addSelectionListener( listener );
	}

	private void createFlashItem( CMenu menu )
	{
		final CMenuItem flash = new CMenuItem( "&Flash Window", SWT.NONE );
		flash.setShortcut( new Shortcut( true, false, false, 'F' ) );
		menu.addItem( flash );
		flash.addSelectionListener( new SelectionAdapter( ) {

			public void widgetSelected( SelectionEvent e )
			{
				Windows.flashWindow( shell.handle, 0, 3 );
			}

		} );
	}

	private void createOnTopItem( CMenu menu )
	{
		final CMenuItem onTop = new CMenuItem( "&Window On Top", SWT.NONE );
		menu.addItem( onTop );
		CMenu topMenu = new CMenu( );
		onTop.setMenu( topMenu );
		final CMenuItem onTopItem = new CMenuItem( "&On Top", SWT.NONE );
		final CMenuItem notOnTopItem = new CMenuItem( "&Not On Top", SWT.NONE );
		topMenu.addItem( onTopItem );
		topMenu.addItem( notOnTopItem );
		SelectionListener listener = new SelectionAdapter( ) {

			public void widgetSelected( SelectionEvent e )
			{
				if ( e.data == onTopItem )
					Windows.setWindowAlwaysOnTop( shell.handle, true );
				else if ( e.data == notOnTopItem )
					Windows.setWindowAlwaysOnTop( shell.handle, false );
			}

		};
		onTopItem.addSelectionListener( listener );
		notOnTopItem.addSelectionListener( listener );
	}

	private void createTransparentItem( CMenu menu )
	{
		if ( Win32.getWin32Version( ) < Win32.VERSION( 5, 0 ) )
			return;
		CMenuItem transparent = new CMenuItem( "&Transparency", SWT.NONE );
		menu.addItem( transparent );
		CMenu transparentMenu = new CMenu( );
		transparent.setMenu( transparentMenu );
		final CMenuItem transparentItem = new CMenuItem( "&Non Transparent",
				SWT.NONE );
		transparentMenu.addItem( transparentItem );
		transparentMenu.addItem( new CMenuItem( null, SWT.SEPARATOR ) );
		final CMenuItem level90 = new CMenuItem( "&90% Level", SWT.NONE );
		transparentMenu.addItem( level90 );
		final CMenuItem level75 = new CMenuItem( "&75% Level", SWT.NONE );
		transparentMenu.addItem( level75 );
		final CMenuItem level50 = new CMenuItem( "&50% Level", SWT.NONE );
		transparentMenu.addItem( level50 );
		SelectionListener listener = new SelectionAdapter( ) {

			public void widgetSelected( SelectionEvent e )
			{
				if ( e.data instanceof CMenuItem )
				{
					CMenuItem item = (CMenuItem) e.data;
					if ( item == transparentItem )
						Windows.setWindowTransparent( shell.handle, false );
					else if ( item == level90 )
						Windows.setWindowTransparent( shell.handle,
								(byte) ( 255 * 0.9 ) );
					else if ( item == level75 )
						Windows.setWindowTransparent( shell.handle,
								(byte) ( 255 * 0.75 ) );
					else if ( item == level50 )
						Windows.setWindowTransparent( shell.handle,
								(byte) ( 255 * 0.50 ) );
				}
			}
		};
		transparentItem.addSelectionListener( listener );
		level90.addSelectionListener( listener );
		level75.addSelectionListener( listener );
		level50.addSelectionListener( listener );
	}

	private void createPageMenu( final BasePage page, ICategoryProvider provider )
	{
		CMenuItem item = new CMenuItem( "&Example Pages", SWT.NONE );
		menubar.getMenu( ).addItem( item );
		final CMenu menu = new CMenu( );
		item.setMenu( menu );
		menu.addMenuListener( new MenuAdapter( ) {

			public void menuShown( Event e )
			{
				if ( e.data != null && e.data instanceof MenuControl )
				{
					for ( int i = 0; i < menu.getItemCount( ); i++ )
					{
						menu.getItem( i )
								.setSelection( page.getSelectionIndex( ) == i );
					}
					( (MenuControl) e.data ).refresh( );
				}
			}

		} );
		ICategoryPage[] pages = provider.getCategories( );
		SelectionListener listener = new SelectionAdapter( ) {

			public void widgetSelected( SelectionEvent e )
			{
				if ( e.data instanceof CMenuItem )
				{
					CMenuItem item = (CMenuItem) e.data;
					page.setSelection( menu.indexOf( item ) );
				}
			}
		};
		for ( int i = 0; i < pages.length; i++ )
		{
			CMenuItem cItem = new CMenuItem( pages[i].getDisplayLabel( ),
					SWT.CHECK );
			cItem.addSelectionListener( listener );
			menu.addItem( cItem );
		}
	}

	private void createSystemMenu( )
	{
		CMenuItem item = new CMenuItem( "&Appearance", SWT.NONE );
		menubar.getMenu( ).addItem( 0, item );
		CMenu menu = new CMenu( );
		item.setMenu( menu );
		vs2003 = new CMenuItem( "&VS2003", SWT.RADIO );
		vs2003.setSelection( menubar.getTheme( ) instanceof VS2003ThemeRender );
		office2003 = new CMenuItem( "&Office2003", SWT.RADIO );
		office2003.setSelection( menubar.getTheme( ) instanceof Office2003ThemeRender );
		vs2005 = new CMenuItem( "V&S2005", SWT.RADIO );
		vs2005.setSelection( menubar.getTheme( ) instanceof VS2005ThemeRender );
		office2007 = new CMenuItem( "O&ffice2007", SWT.RADIO );
		office2007.setSelection( menubar.getTheme( ) instanceof Office2007ThemeRender );
		glossy = new CMenuItem( "&Glossy", SWT.RADIO );
		glossy.setSelection( menubar.getTheme( ) instanceof GlossyThemeRender );
		menu.addItem( vs2003 );
		menu.addItem( vs2005 );
		menu.addItem( office2003 );
		menu.addItem( office2007 );
		menu.addItem( glossy );

		menu.addItem( new CMenuItem( null, SWT.SEPARATOR ) );
		final CMenuItem mulitLine = new CMenuItem( "&Multi Line", SWT.CHECK );
		menu.addItem( mulitLine );
		mulitLine.setSelection( menubar.isMultiLine( ) );
		menu.addItem( new CMenuItem( null, SWT.SEPARATOR ) );
		final CMenuItem exit = new CMenuItem( "&Exit", SWT.NONE );
		exit.setImage( ImageCache.getImage( "/exit.gif" ) );
		exit.setText( "Exit" );
		exit.setShortcut( new Shortcut( true, false, false, 'X' ) );
		menu.addItem( exit );

		SelectionListener listener = new SelectionAdapter( ) {

			public void widgetSelected( SelectionEvent e )
			{
				if ( e.data instanceof CMenuItem )
				{
					CMenuItem item = (CMenuItem) e.data;
					if ( item.equals( vs2003 ) )
					{
						handleVs2003Selection( );
					}
					else if ( item.equals( vs2005 ) )
					{
						handleVs2005Selection( );
					}
					else if ( item.equals( office2003 ) )
					{
						handleOffice2003Selection( );
					}
					else if ( item.equals( office2007 ) )
					{
						handleOffice2007Selection( );
					}
					else if ( item.equals( glossy ) )
					{
						handleGlossySelection( );
					}
					else if ( item.equals( mulitLine ) )
					{
						item.setSelection( !item.getSelection( ) );
						menubar.setMultiLine( item.getSelection( ) );
						toolbar.setMultiLine( item.getSelection( ) );
						menubar.getControl( ).redraw( );
						toolbar.getControl( ).redraw( );
					}
					else if ( item.equals( exit ) )
					{
						shell.close( );
					}
				}
			}

		};
		vs2003.addSelectionListener( listener );
		vs2005.addSelectionListener( listener );
		office2003.addSelectionListener( listener );
		office2007.addSelectionListener( listener );
		glossy.addSelectionListener( listener );
		mulitLine.addSelectionListener( listener );
		exit.addSelectionListener( listener );

		appearanceTool = new CToolItem( "Appearance", SWT.NONE );
		appearanceTool.setMenu( menu );
		toolbar.addItem( appearanceTool );
	}

	private void setLayoutData( Control control, boolean exclude )
	{
		GridData gd = (GridData) control.getLayoutData( );
		gd.exclude = exclude;
		control.setLayoutData( gd );
		control.setVisible( !exclude );
		control.getParent( ).layout( );
	}

	private static Example instance;

	public static void main( String[] args )
	{
		instance = new Example( );
		instance.initial( );
	}

	public static boolean DIRTY = false;

	private ShellWrapper wrapper;

	private MenuBar menubar;

	private SystemMenuManager manager;

	private Shell shell;

	public Shell getShell( )
	{
		return shell;
	}

	private HelloWorld example;

	private CMenuItem vista;

	private CMenuItem vs2003;

	private CMenuItem office2003;

	private CMenuItem vs2005;

	private CMenuItem office2007;

	private CMenuItem glossy;

	private CToolBar toolbar;

	private Separator menuSeparator;

	private Separator toolSeparator;

	private CToolItem appearanceTool;

	private CMenuItem office2007Style;

	private CMenuItem glossyStyle;

	public ShellWrapper getWrapper( )
	{
		return wrapper;
	}

	public static Example getInstance( )
	{
		return instance;
	}

	public MenuBar getMenuBar( )
	{
		return menubar;
	}

	public void handleOffice2007Selection( )
	{
		vs2003.setSelection( false );
		vs2005.setSelection( false );
		office2003.setSelection( false );
		office2007.setSelection( true );
		glossy.setSelection( false );
		menubar.setTheme( new Office2007ThemeRender( ) );
		toolbar.setTheme( new Office2007ThemeRender( ) );
		menubar.getControl( ).redraw( );
		toolbar.getControl( ).redraw( );
		setLayoutData( menuSeparator, true );
		setLayoutData( toolSeparator, true );
	}

	public void handleGlossySelection( )
	{
		vs2003.setSelection( false );
		vs2005.setSelection( false );
		office2003.setSelection( false );
		office2007.setSelection( false );
		glossy.setSelection( true );
		menubar.setTheme( new BlackGlossyThemeRender( ) );
		toolbar.setTheme( new BlackGlossyThemeRender( ) );
		menubar.getControl( ).redraw( );
		toolbar.getControl( ).redraw( );
		setLayoutData( menuSeparator, true );
		setLayoutData( toolSeparator, true );
	}

	public void handleVistaSelection( )
	{
		vs2003.setSelection( false );
		vs2005.setSelection( true );
		office2003.setSelection( false );
		office2007.setSelection( false );
		glossy.setSelection( false );
		menubar.setTheme( new VS2005ThemeRender( ) );
		toolbar.setTheme( new VS2005ThemeRender( ) );
		menubar.getControl( ).redraw( );
		toolbar.getControl( ).redraw( );
		setLayoutData( menuSeparator, false );
		setLayoutData( toolSeparator, false );
	}

	private void handleVs2005Selection( )
	{
		vs2003.setSelection( false );
		vs2005.setSelection( true );
		office2003.setSelection( false );
		office2007.setSelection( false );
		glossy.setSelection( false );
		menubar.setTheme( new VS2005ThemeRender( ) );
		toolbar.setTheme( new VS2005ThemeRender( ) );
		menubar.getControl( ).redraw( );
		toolbar.getControl( ).redraw( );
		setLayoutData( menuSeparator, false );
		setLayoutData( toolSeparator, false );
	}

	private void handleVs2003Selection( )
	{
		vs2003.setSelection( true );
		vs2005.setSelection( false );
		office2003.setSelection( false );
		office2007.setSelection( false );
		glossy.setSelection( false );
		menubar.setTheme( new VS2003ThemeRender( ) );
		toolbar.setTheme( new VS2003ThemeRender( ) );
		menubar.getControl( ).redraw( );
		toolbar.getControl( ).redraw( );
		setLayoutData( menuSeparator, false );
		setLayoutData( toolSeparator, false );
	}

	private void handleOffice2003Selection( )
	{
		vs2003.setSelection( false );
		vs2005.setSelection( false );
		office2003.setSelection( true );
		office2007.setSelection( false );
		glossy.setSelection( false );
		menubar.setTheme( new Office2003ThemeRender( ) );
		toolbar.setTheme( new Office2003ThemeRender( ) );
		menubar.getControl( ).redraw( );
		toolbar.getControl( ).redraw( );
		setLayoutData( menuSeparator, true );
		setLayoutData( toolSeparator, true );
	}

}