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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.MouseTrackAdapter;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Path;
import org.eclipse.swt.graphics.Pattern;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.internal.Callback;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.sf.feeling.swt.win32.extension.Win32;
import org.sf.feeling.swt.win32.extension.graphics.GraphicsUtil;
import org.sf.feeling.swt.win32.extension.widgets.theme.GeneralThemeRender;
import org.sf.feeling.swt.win32.extension.widgets.theme.GlossyThemeRender;
import org.sf.feeling.swt.win32.extension.widgets.theme.OfficeThemeRender;
import org.sf.feeling.swt.win32.extension.widgets.theme.ThemeRender;
import org.sf.feeling.swt.win32.extension.widgets.theme.VS2005ThemeRender;
import org.sf.feeling.swt.win32.internal.extension.Extension;
import org.sf.feeling.swt.win32.internal.extension.graphics.GCExtension;

public final class MenuBar implements MenuHolder, Bar
{

	private static final int CHEVRON_LENGTH = 12;

	private static final int DRAW_FLAGS = SWT.DRAW_MNEMONIC
			| SWT.DRAW_TAB
			| SWT.DRAW_TRANSPARENT
			| SWT.DRAW_DELIMITER;

	private static final int HORIZON_GAP = 5;

	private static final int HORIZON_MARGIN = 2, VERTICAL_MARGIN = 2;

	private static final int KEY_DOWN = 16777218;

	private static final int KEY_LEFT = 16777219;

	private static final int KEY_RIGHT = 16777220;

	private static final int KEY_UP = 16777217;

	private static final int SEPARATOR_WIDTH = 15;

	private static final int SHADOW_GAP = 4;

	private static final int VERTICAL_GAP = 1;

	private boolean altKeyDown = false;

	private CMenuItem chevronStartItem;

	private List drawCommands = new ArrayList( );

	private boolean drawUpwards;

	private GridLayout layout;

	private CMenu menu = new CMenu( );

	private CLabel menubar;

	private Callback mouseCallback;

	private int newAddress;

	private boolean nonAltKeyDown = false;

	private int oldAddress;

	private MenuControl popupMenu;

	private boolean selected = false;

	private int style = SWT.NONE;

	private ThemeRender theme;

	private int trackItemIndex = -1;

	public MenuBar( Composite parent, int style )
	{
		this( parent, style, new VS2005ThemeRender( ) );
	}

	public MenuBar( Composite parent, int style, ThemeRender theme )
	{
		menubar = new CLabel( parent, style ) {

			public Point computeSize( int wHint, int hHint, boolean changed )
			{
				if ( this.isVisible( ) )
				{
					Rectangle rect = recalculate( );
					return new Point( rect.width, rect.height );
				}
				else
					return super.computeSize( wHint, hHint, changed );
			}
		};
		this.style = style;
		layout = new GridLayout( );
		layout.marginWidth = layout.marginHeight = 0;
		layout.horizontalSpacing = 0;
		setTheme( theme );
		menubar.setLayout( layout );
		menubar.addPaintListener( new PaintListener( ) {

			public void paintControl( PaintEvent e )
			{
				onPaint( e );
			}

		} );
		menubar.addDisposeListener( new DisposeListener( ) {

			public void widgetDisposed( DisposeEvent e )
			{
				hideMenu( );
				MenuHolderManager.deRegistryShortcut( MenuBar.this );
			}

		} );

		final ShellAdapter shellListener = new ShellAdapter( ) {

			public void shellClosed( ShellEvent e )
			{
				hideMenu( );
			}

			public void shellDeactivated( ShellEvent e )
			{
				hideMenu( );
			}

			public void shellIconified( ShellEvent e )
			{
				hideMenu( );
			}
		};
		menubar.getShell( ).addShellListener( shellListener );

		final Listener mouseDownListener = new Listener( ) {

			public void handleEvent( Event e )
			{
				if ( getShell( ) == null || getShell( ).isDisposed( ) )
				{
					Display.getDefault( ).removeFilter( SWT.MouseDown, this );
					return;
				}
				if ( e.widget == null
						|| e.widget.isDisposed( )
						|| checkMouseDownEvent( ( (Control) e.widget ).toDisplay( e.x,
								e.y ) ) )
				{
					hideMenu( );
				}
			}
		};

		Display.getDefault( ).addFilter( SWT.MouseDown, mouseDownListener );

		final Listener keyDownListener = new Listener( ) {

			public void handleEvent( Event e )
			{
				MenuHolder holder = MenuHolderManager.getActiveHolder( );
				if ( holder != null && holder != MenuBar.this )
				{
					altKeyDown = false;
					nonAltKeyDown = true;
					return;
				}
				if ( getShell( ) == null || getShell( ).isDisposed( ) )
				{
					Display.getDefault( ).removeFilter( SWT.KeyDown, this );
					return;
				}
				KeyEvent ke = new KeyEvent( e );
				if ( ke.keyCode == SWT.ALT || ke.keyCode == SWT.F10 )
				{
					if ( ke.keyCode == SWT.ALT )
						altKeyDown = true;
					nonAltKeyDown = false;
				}
				else
				{
					int ch = ke.keyCode;
					if ( ch == 0 )
						ch = ke.character;
					String pattern = "&" + (char) ch;
					if ( ( altKeyDown == true || trackItemIndex > -1 )
							&& popupMenu == null )
					{
						boolean flag = false;
						for ( int i = 0; i < menu.getItemCount( ); i++ )
						{
							if ( menu.getItem( i )
									.getText( )
									.toLowerCase( )
									.indexOf( pattern.toLowerCase( ) ) > -1 )
							{
								setSelection( i, true );
								Display.getDefault( )
										.asyncExec( new Runnable( ) {

											public void run( )
											{
												if ( popupMenu != null )
													popupMenu.setSelection( 0 );
											}
										} );
								flag = true;
								break;
							}
						}
						if ( !flag && ch < 256 )
						{
							trackItemIndex = -1;
							refresh( );
							altKeyDown = false;
							nonAltKeyDown = true;
							return;
						}
					}
					else if ( popupMenu != null )
					{
						if ( popupMenu.getCurrentMenu( ) != null )
							popupMenu.getCurrentMenu( ).dealAltKeyEvent( ke );
						else
							popupMenu.dealAltKeyEvent( ke );
					}
					nonAltKeyDown = true;
				}
				if ( ke.keyCode == KEY_RIGHT && trackItemIndex > -1 )
				{
					if ( popupMenu == null
							|| popupMenu.getCurrentMenu( ) == null
							|| popupMenu.getCurrentMenu( ).isSubMenuEnd( ) )
					{
						refresh( );
						int index = drawCommands.size( );
						if ( chevronStartItem != null )
							index = menu.indexOf( chevronStartItem ) + 1;
						if ( ++trackItemIndex == index )
							trackItemIndex = 0;
						if ( !selected )
						{
							refresh( );
							drawCommand( trackItemIndex, true );
						}
						else
						{
							if ( popupMenu != null )
							{
								popupMenu.hideMenu( );
								popupMenu = null;
							}
							refresh( );
							drawCommand( trackItemIndex, true );
							if ( !isShowMenu( ) && selected )
							{
								final MenuDrawCommand command = (MenuDrawCommand) drawCommands.get( trackItemIndex );
								Display.getDefault( )
										.asyncExec( new Runnable( ) {

											public void run( )
											{
												showMenu( command );
											}
										} );
							}
							return;
						}
					}
					else
					{
						popupMenu.getCurrentMenu( ).subMenuSelected( );
					}
				}
				if ( ke.keyCode == KEY_LEFT && trackItemIndex > -1 )
				{
					if ( popupMenu == null
							|| popupMenu.getCurrentMenu( ) == null
							|| popupMenu.getCurrentMenu( ) == popupMenu )
					{
						refresh( );
						if ( --trackItemIndex == -1 )
						{
							if ( chevronStartItem != null )
								trackItemIndex = menu.indexOf( chevronStartItem );
							else
								trackItemIndex = drawCommands.size( ) - 1;
						}
						if ( !selected )
						{
							refresh( );
							drawCommand( trackItemIndex, true );
						}
						else
						{
							if ( popupMenu != null )
							{
								popupMenu.hideMenu( );
								popupMenu = null;
							}
							refresh( );
							drawCommand( trackItemIndex, true );
							if ( !isShowMenu( ) && selected )
							{
								final MenuDrawCommand command = (MenuDrawCommand) drawCommands.get( trackItemIndex );
								Display.getDefault( )
										.asyncExec( new Runnable( ) {

											public void run( )
											{
												showMenu( command );
											}
										} );
							}
							return;
						}
					}
					else
					{
						popupMenu.getCurrentMenu( ).parentMenuSelected( );
					}
				}
				if ( ( ke.keyCode == KEY_DOWN || ke.keyCode == KEY_UP )
						&& trackItemIndex > -1 )
				{
					if ( !selected )
					{
						selected = true;
						refresh( );
						if ( !isShowMenu( ) && selected )
						{
							final MenuDrawCommand command = (MenuDrawCommand) drawCommands.get( trackItemIndex );
							Display.getDefault( ).asyncExec( new Runnable( ) {

								public void run( )
								{
									showMenu( command );
								}
							} );
						}
					}
					else
					{
						if ( popupMenu != null )
						{
							if ( popupMenu.getCurrentMenu( ) == null )
							{
								popupMenu.setSelection( 0 );
							}
							else
							{
								if ( ke.keyCode == KEY_DOWN )
									popupMenu.getCurrentMenu( ).downSelected( );
								else
									popupMenu.getCurrentMenu( ).upSelected( );
							}
						}
					}
				}
				if ( ke.keyCode == SWT.ESC )
				{
					if ( selected == false )
					{
						refresh( );
						trackItemIndex = -1;
					}
					else
					{
						if ( popupMenu == null
								|| popupMenu.getCurrentMenu( ) == null
								|| popupMenu.getCurrentMenu( ) == popupMenu )
						{
							selected = false;
							refresh( );
							drawCommand( trackItemIndex, false );
							if ( popupMenu != null )
							{
								popupMenu.hideMenu( );
								popupMenu = null;
							}
						}
						else
						{
							popupMenu.getCurrentMenu( ).parentMenuSelected( );
						}
					}
				}
				if ( ke.keyCode == SWT.CR )
				{
					if ( popupMenu != null
							&& popupMenu.getCurrentMenu( ) != null )
					{
						popupMenu.getCurrentMenu( ).handleSelectedEvent( );
					}
					else
					{
						if ( popupMenu == null
								&& trackItemIndex != -1
								&& selected == false )
						{
							selected = true;
							refresh( );
							drawCommand( trackItemIndex, true );
							if ( !isShowMenu( ) && selected )
							{
								showMenu( (MenuDrawCommand) drawCommands.get( trackItemIndex ) );
							}
						}
					}
				}
			}
		};
		Display.getDefault( ).addFilter( SWT.KeyDown, keyDownListener );

		final Listener keyUpListener = new Listener( ) {

			public void handleEvent( Event e )
			{
				MenuHolder holder = MenuHolderManager.getActiveHolder( );
				if ( holder != null && holder != MenuBar.this )
					return;
				if ( getShell( ) == null || getShell( ).isDisposed( ) )
				{
					Display.getDefault( ).removeFilter( SWT.KeyUp, this );
					return;
				}
				KeyEvent ke = new KeyEvent( e );
				if ( ( ke.keyCode == SWT.ALT || ke.keyCode == SWT.F10 )
						&& !nonAltKeyDown )
				{
					if ( drawCommands.size( ) > 0 )
					{
						// If no item is currently tracked then...
						if ( trackItemIndex == -1 )
						{
							// ...start tracking the first valid command
							for ( int i = 0; i < drawCommands.size( ); i++ )
							{
								MenuDrawCommand dc = (MenuDrawCommand) drawCommands.get( i );

								if ( !dc.isSeparator( )
										&& ( dc.isChevron( ) || dc.isEnabled( ) ) )
								{
									refresh( );
									trackItemIndex = i;
									refresh( );
									drawCommand( trackItemIndex, true );
									MenuBar.this.getControl( ).forceFocus( );
									break;
								}
							}
						}
						else
						{
							selected = false;
							refresh( );
							drawCommand( trackItemIndex, false );
							trackItemIndex = -1;
							hideMenu( );
						}
					}
				}
				if ( ke.keyCode == SWT.ALT )
					altKeyDown = false;
			}
		};
		Display.getDefault( ).addFilter( SWT.KeyUp, keyUpListener );

		menubar.addDisposeListener( new DisposeListener( ) {

			public void widgetDisposed( DisposeEvent e )
			{
				Display.getDefault( ).removeFilter( SWT.MouseDown,
						mouseDownListener );
				Display.getDefault( ).removeFilter( SWT.KeyUp, keyUpListener );
				Display.getDefault( ).removeFilter( SWT.KeyDown,
						keyDownListener );
				menubar.getShell( ).removeShellListener( shellListener );
			}

		} );

		menubar.addMouseMoveListener( new MouseMoveListener( ) {

			public void mouseMove( MouseEvent e )
			{
				MenuHolder holder = MenuHolderManager.getActiveHolder( );
				if ( holder != null && holder != MenuBar.this )
					return;
				handleMouseMoveEvent( new Point( e.x, e.y ) );
			}
		} );

		menubar.addMouseTrackListener( new MouseTrackAdapter( ) {

			public void mouseExit( MouseEvent e )
			{
				MenuHolder holder = MenuHolderManager.getActiveHolder( );
				if ( holder != null && holder != MenuBar.this )
					return;
				handleMouseMoveEvent( new Point( e.x, e.y ) );
			}
		} );

		menubar.addMouseListener( new MouseAdapter( ) {

			private boolean hideMenuCommand = false;

			public void mouseDown( MouseEvent e )
			{
				if ( e.button != 1 )
					return;
				for ( int i = 0; i < drawCommands.size( ); i++ )
				{
					MenuDrawCommand command = ( (MenuDrawCommand) drawCommands.get( i ) );
					if ( command.getDrawRect( ).contains( e.x, e.y ) )
					{
						if ( !selected && !isShowMenu( ) )
						{
							selected = true;
							refresh( );
							trackItemIndex = i;
							refresh( );
							drawCommand( trackItemIndex, true );
							showMenu( command );
						}
						else
						{
							hideMenuCommand = true;
						}
						return;
					}
				}
				selected = false;
				return;
			}

			public void mouseUp( MouseEvent e )
			{
				if ( isShowMenu( ) )
				{
					if ( hideMenuCommand )
					{
						hideMenuCommand = false;
						popupMenu.hideMenu( );
						popupMenu = null;
						selected = false;
						refresh( );
					}
				}
			}
		} );

		MenuHolderManager.registerHolder( this );
	}

	public boolean checkMouseDownEvent( Point location )
	{
		if ( menubar.isDisposed( ) )
			return true;
		if ( new Rectangle( 0, 0, menubar.getSize( ).x, menubar.getSize( ).y ).contains( menubar.toControl( location ) ) )
		{
			for ( int i = 0; i < drawCommands.size( ); i++ )
			{
				MenuDrawCommand command = ( (MenuDrawCommand) drawCommands.get( i ) );
				if ( command.getDrawRect( )
						.contains( menubar.toControl( location ) ) )
				{
					return false;
				}
			}
			return true;
		}
		if ( popupMenu != null )
			return popupMenu.checkMouseDownEvent( location );
		return true;
	}

	private void drawAllCommands( GC gc )
	{
		for ( int i = 0; i < drawCommands.size( ); i++ )
		{
			MenuDrawCommand dc = (MenuDrawCommand) drawCommands.get( i );
			drawSingleCommand( gc, dc, ( i == trackItemIndex ) );
		}
	}

	private void drawCommand( int drawItem, boolean tracked )
	{
		GC gc = new GC( menubar );
		drawSingleCommand( gc,
				(MenuDrawCommand) drawCommands.get( drawItem ),
				tracked );
		gc.dispose( );
	}

	public void drawSelectionUpwards( )
	{
		// Double check the state is correct for this method to be called
		if ( ( trackItemIndex != -1 ) && ( selected ) )
		{
			// This flag is tested in the DrawCommand method
			drawUpwards = true;

			// Force immediate redraw of the item
			drawCommand( trackItemIndex, true );
		}
	}

	private void drawSingleCommand( GC gc, MenuDrawCommand dc, boolean tracked )
	{
		Rectangle drawRect = dc.getDrawRect( );
		CMenuItem mc = dc.getMenuItem( );
		if ( !dc.isSeparator( ) )
		{
			if ( dc.isEnabled( ) )
			{
				if ( tracked )
				{
					if ( theme instanceof GeneralThemeRender )
					{
						GeneralThemeRender themeRender = (GeneralThemeRender) theme;
						if ( selected )
						{
							gc.setForeground( themeRender.getMenu_item_bg_selected1( ) );
							gc.setBackground( themeRender.getMenu_item_bg_selected2( ) );
							gc.fillGradientRectangle( drawRect.x,
									drawRect.y,
									drawRect.width,
									drawRect.height,
									true );
							gc.setForeground( themeRender.getMenu_item_border_selected( ) );
							gc.drawRectangle( drawRect );
						}
						else
						{
							gc.setForeground( themeRender.getMenu_item_bg_track1( ) );
							gc.setBackground( themeRender.getMenu_item_bg_track2( ) );
							gc.fillGradientRectangle( drawRect.x,
									drawRect.y,
									drawRect.width,
									drawRect.height,
									true );
							gc.setForeground( themeRender.getMenu_item_border_track( ) );
							gc.drawRectangle( drawRect );
						}
					}
					else if ( theme instanceof OfficeThemeRender )
					{
						OfficeThemeRender themeRender = (OfficeThemeRender) theme;
						Color[] colors = null;
						if ( selected )
						{
							colors = new Color[]{
									themeRender.getMenu_item_outer_top_selected1( ),
									themeRender.getMenu_item_outer_top_selected2( ),
									themeRender.getMenu_item_outer_bottom_selected1( ),
									themeRender.getMenu_item_outer_bottom_selected2( ),
									themeRender.getMenu_item_inner_top_selected1( ),
									themeRender.getMenu_item_inner_top_selected2( ),
									themeRender.getMenu_item_inner_bottom_selected1( ),
									themeRender.getMenu_item_inner_bottom_selected2( ),
									themeRender.getMenu_item_border_selected( )
							};
						}
						else
						{
							colors = new Color[]{
									themeRender.getMenu_item_outer_top_track1( ),
									themeRender.getMenu_item_outer_top_track2( ),
									themeRender.getMenu_item_outer_bottom_track1( ),
									themeRender.getMenu_item_outer_bottom_track2( ),
									themeRender.getMenu_item_inner_top_track1( ),
									themeRender.getMenu_item_inner_top_track2( ),
									themeRender.getMenu_item_inner_bottom_track1( ),
									themeRender.getMenu_item_inner_bottom_track2( ),
									themeRender.getMenu_item_border_track( )
							};
						}
						drawGradientBack( gc, drawRect, colors );
						drawGradientBorder( gc, drawRect, colors );
					}
					else if ( theme instanceof GlossyThemeRender )
					{
						GlossyThemeRender themeRender = (GlossyThemeRender) theme;
						gc.setAntialias( SWT.ON );
						Rectangle outerBorder = new Rectangle( drawRect.x,
								drawRect.y,
								drawRect.width + 1,
								drawRect.height + 1 );
						Rectangle innerBorder = GraphicsUtil.inflate( outerBorder,
								-1,
								-1 );
						Rectangle glossy = new Rectangle( innerBorder.x,
								innerBorder.y,
								innerBorder.width,
								innerBorder.height / 2 );
						Rectangle glow = GraphicsUtil.createRectangleFromLTRB( outerBorder.x,
								outerBorder.y
										+ Math.round( outerBorder.height * .5f ),
								outerBorder.x + outerBorder.width,
								outerBorder.y + outerBorder.height );

						Pattern pattern = new Pattern( null,
								0,
								glossy.y,
								0,
								glossy.y + glossy.height,
								themeRender.getMenu_item_bg_color1( ),
								themeRender.getMenu_item_bg_color1_alpha( ),
								themeRender.getMenu_item_bg_color2( ),
								themeRender.getMenu_item_bg_color2_alpha( ) );
						Path path = new Path( null );
						path.addRectangle( glossy.x,
								glossy.y,
								glossy.width,
								glossy.height );
						gc.setBackgroundPattern( pattern );
						gc.fillPath( path );
						path.dispose( );

						path = GraphicsUtil.createRoundRectangle( outerBorder,
								2 );
						gc.setForeground( themeRender.getMenu_item_outer_border( ) );
						gc.drawPath( path );
						path.dispose( );

						if ( !selected )
							pattern = new Pattern( null,
									0,
									glossy.y,
									0,
									glossy.y + glossy.height,
									themeRender.getMenu_item_bg_glossy_track1( ),
									themeRender.getMenu_item_bg_glossy_track1_alpha( ),
									themeRender.getMenu_item_bg_glossy_track2( ),
									themeRender.getMenu_item_bg_glossy_track2_alpha( ) );
						else
							pattern = new Pattern( null,
									0,
									glossy.y,
									0,
									glossy.y + glossy.height,
									themeRender.getMenu_item_bg_glossy_selected1( ),
									themeRender.getMenu_item_bg_glossy_selected1_alpha( ),
									themeRender.getMenu_item_bg_glossy_selected2( ),
									themeRender.getMenu_item_bg_glossy_selected2_alpha( ) );
						path = GraphicsUtil.createTopRoundRectangle( glossy, 2 );
						gc.setBackgroundPattern( pattern );
						gc.fillPath( path );
						path.dispose( );

						Color innerBorderColor = selected
								|| ( mc != null
										&& ( mc.getStyle( ) & SWT.CHECK ) != 0 && mc.getSelection( ) ) ? themeRender.getMenu_item_inner_border_selected( )
								: themeRender.getMenu_item_inner_border_track( );

						path = GraphicsUtil.createRoundRectangle( innerBorder,
								2 );
						gc.setForeground( innerBorderColor );
						gc.drawPath( path );
						path.dispose( );

						if ( !selected )
						{
							path = GraphicsUtil.createRoundRectangle( glow, 2 );
							gc.setClipping( path );
							path.dispose( );

							Color glowColor = themeRender.getMenu_item_bg_glow_track( );
							path = createBottomRadialPath( glow );
							float[] point = new float[2];
							float[] bounds = new float[4];
							path.getBounds( bounds );
							point[0] = ( bounds[0] + bounds[0] + bounds[2] ) / 2f;
							point[1] = ( bounds[1] + bounds[1] + bounds[3] ) / 2f;
							GCExtension extension = new GCExtension( gc );
							extension.fillGradientPath( path,
									point,
									glowColor,
									255,
									new Color[]{
										glowColor
									},
									new int[]{
										0
									} );
							path.dispose( );
							gc.setClipping( (Path) null );
						}
						gc.setAdvanced( false );
					}
					if ( selected )
						showMenuShadow( gc,
								dc.getSelectRect( ),
								dc.getMenuItem( ) );
				}
			}
			if ( dc.isChevron )
			{
				Image image = theme.getMenu_chevronImage( );
				int yPos = drawRect.y + VERTICAL_GAP;
				int xPos = drawRect.x
						+ ( ( drawRect.width - image.getImageData( ).width ) / 2 )
						+ 1;
				if ( selected && tracked )
				{
					xPos += 1;
					yPos += 1;
				}
				gc.drawImage( image, xPos, yPos );
			}
			else
			{
				if ( dc.isEnabled( ) && menubar.isEnabled( ) )
				{
					gc.setForeground( theme.getMenu_item_fg( ) );
				}
				else
					gc.setForeground( theme.getMenu_item_fg_disabled( ) );
				int lineHeight = gc.getFontMetrics( ).getHeight( );
				int lineWidth = gc.textExtent( mc.getText( ), DRAW_FLAGS ).x;
				gc.drawText( mc.getText( ),
						drawRect.x + ( drawRect.width - lineWidth ) / 2,
						drawRect.y + ( drawRect.height - lineHeight ) / 2,
						DRAW_FLAGS );

			}

		}

	}

	private Path createBottomRadialPath( Rectangle glow )
	{
		float[] bounds = new float[4];
		bounds[0] = glow.x;
		bounds[1] = glow.y;
		bounds[2] = glow.width;
		bounds[3] = glow.height;
		bounds[0] -= bounds[2] * .35f;
		bounds[1] -= bounds[3] * .15f;
		bounds[2] *= 1.7f;
		bounds[3] *= 2.3f;
		return GraphicsUtil.createEllipsePath( bounds );
	}

	private void drawGradientBack( GC g, Rectangle rect, Color[] colors )
	{
		g.setAdvanced( true );
		Rectangle backRect = new Rectangle( rect.x,
				rect.y,
				rect.width + 1,
				rect.height + 1 );
		Rectangle backRect1 = GraphicsUtil.inflate( backRect, -1, -1 );
		int height = backRect1.height / 2;
		Rectangle rect1 = new Rectangle( backRect1.x,
				backRect1.y,
				backRect1.width,
				height );
		Rectangle rect2 = new Rectangle( backRect1.x,
				backRect1.y + height,
				backRect1.width,
				backRect1.height - height );

		g.setForeground( colors[0] );
		g.setBackground( colors[1] );
		g.fillGradientRectangle( rect1.x,
				rect1.y,
				rect1.width,
				rect1.height,
				true );

		g.setForeground( colors[2] );
		g.setBackground( colors[3] );
		g.fillGradientRectangle( rect2.x,
				rect2.y,
				rect2.width,
				rect2.height,
				true );

		Rectangle backRect2 = GraphicsUtil.inflate( backRect1, -1, -1 );
		height = backRect2.height / 2;
		rect1 = new Rectangle( backRect2.x,
				backRect2.y,
				backRect2.width,
				height );
		rect2 = new Rectangle( backRect2.x,
				backRect2.y + height,
				backRect2.width,
				backRect2.height - height );

		g.setForeground( colors[4] );
		g.setBackground( colors[5] );
		g.fillGradientRectangle( rect1.x,
				rect1.y,
				rect1.width,
				rect1.height,
				true );

		g.setForeground( colors[6] );
		g.setBackground( colors[7] );
		g.fillGradientRectangle( rect2.x,
				rect2.y,
				rect2.width,
				rect2.height,
				true );

		g.setAdvanced( false );
	}

	private void drawGradientBorder( GC g, Rectangle rect, Color[] colors )
	{
		if ( GraphicsUtil.checkGdip( ) )
		{
			g.setAdvanced( true );
			Rectangle backRect = new Rectangle( rect.x,
					rect.y,
					rect.width,
					rect.height );
			Path path = GraphicsUtil.createRoundPath( backRect, 1.2f );
			g.setForeground( colors[8] );
			g.drawPath( path );
			path.dispose( );
			g.setAdvanced( false );
		}
		else
		{
			g.setForeground( colors[8] );
			g.drawRectangle( rect );
		}
	}

	public Color getBackground( )
	{
		if ( menubar != null && !menubar.isDisposed( ) )
			return menubar.getBackground( );
		return null;
	}

	public Control getControl( )
	{
		return menubar;
	}

	protected MenuDrawCommand getCurrentCommand( )
	{
		if ( menubar.isDisposed( ) )
			return null;
		if ( trackItemIndex < 0 || trackItemIndex >= drawCommands.size( ) )
			return null;
		return (MenuDrawCommand) drawCommands.get( trackItemIndex );
	}

	public CMenu getMenu( )
	{
		return menu;
	}

	public CMenuItem getSelection( )
	{
		if ( trackItemIndex == -1 )
			return null;
		else
		{
			MenuDrawCommand dc = (MenuDrawCommand) drawCommands.get( trackItemIndex );
			if ( dc == null )
				return null;
			else
				return dc.getMenuItem( );
		}
	}

	public int getSelectionIndex( )
	{
		return trackItemIndex;
	}

	public Shell getShell( )
	{
		if ( menubar.isDisposed( ) )
			return null;
		return menubar.getShell( );
	}

	public ThemeRender getTheme( )
	{
		return theme;
	}

	private void handleMouseMoveEvent( Point screenPos )
	{
		for ( int i = 0; i < drawCommands.size( ); i++ )
		{
			final MenuDrawCommand command = ( (MenuDrawCommand) drawCommands.get( i ) );
			if ( command.getDrawRect( ).contains( screenPos.x, screenPos.y ) )
			{
				if ( trackItemIndex != i )
				{
					if ( popupMenu != null )
					{
						popupMenu.hideMenu( );
						popupMenu = null;
					}
					refresh( );
					trackItemIndex = i;
					refresh( );
					drawCommand( trackItemIndex, true );
					if ( !isShowMenu( ) && selected )
					{
						Display.getDefault( ).asyncExec( new Runnable( ) {

							public void run( )
							{
								showMenu( command );
							}
						} );
					}
					return;
				}
				return;
			}
		}
		if ( trackItemIndex >= 0 )
		{
			if ( !selected )
			{
				refresh( );
				drawCommand( trackItemIndex, false );
				trackItemIndex = -1;
			}
		}
	}

	public void hideMenu( )
	{
		if ( popupMenu != null )
		{
			popupMenu.hideMenu( );
			popupMenu = null;
		}
		if ( menubar.isDisposed( ) )
			return;
		selected = false;
		refresh( );
		trackItemIndex = -1;
	}

	private void installMouseHook( )
	{
		mouseCallback = new Callback( this, "MouseProc", 3 );
		newAddress = mouseCallback.getAddress( );
		if ( newAddress == 0 )
			SWT.error( SWT.ERROR_NO_MORE_CALLBACKS );
		int threadId = Extension.GetCurrentThreadId( );
		oldAddress = Extension.SetWindowsHookEx( Win32.WH_MOUSE,
				newAddress,
				0,
				threadId );
		popupMenu.getControl( ).addDisposeListener( new DisposeListener( ) {

			public void widgetDisposed( DisposeEvent e )
			{
				if ( mouseCallback != null )
				{
					Extension.UnhookWindowsHookEx( oldAddress );
					mouseCallback.dispose( );
					mouseCallback = null;
					newAddress = 0;
				}
			}

		} );
	}

	public boolean isMultiLine( )
	{
		return ( style & SWT.MULTI ) != 0;
	}

	private boolean isShowMenu( )
	{
		return popupMenu != null && !popupMenu.getShell( ).isDisposed( );
	}

	int MouseProc( int /* long */code, int /* long */wParam,
			int /* long */lParam )
	{
		int result = Extension.CallNextHookEx( oldAddress, code, wParam, lParam );
		if ( code < 0 )
			return result;
		switch ( wParam )
		{
			case Win32.WM_NCLBUTTONDOWN :
			case Win32.WM_NCRBUTTONDOWN :
				hideMenu( );
				break;
		}
		return result;
	}

	private void onPaint( PaintEvent e )
	{
		recalculate( );
		menubar.getParent( ).layout( );
		drawAllCommands( e.gc );
	}

	protected Rectangle recalculate( )
	{
		int length = menubar.getSize( ).x;
		if ( length > 0 )
		{
			int rows = 0;
			int columns = 0;
			int cellMinLength = HORIZON_GAP * 2;
			int lengthStart = HORIZON_MARGIN;
			int verticalStart = VERTICAL_MARGIN;
			if ( ( style & SWT.MULTI ) == 0 )
				length -= ( HORIZON_MARGIN + CHEVRON_LENGTH + 1 );
			else
				length -= HORIZON_GAP;
			drawCommands.clear( );
			chevronStartItem = null;
			GC gc = new GC( menubar );
			int BREADTH_GAP = ( gc.getFontMetrics( ).getHeight( ) / 3 );
			int rowHeight = gc.getFontMetrics( ).getHeight( )
					+ BREADTH_GAP
					* 2
					+ 1;
			int index = 0;
			for ( int i = 0; i < menu.getItemCount( ); i++ )
			{
				CMenuItem item = menu.getItem( i );
				if ( !item.isVisible( ) )
					continue;
				int cellLength = 0;
				if ( item.getText( ).equals( "-" )
						|| ( item.getStyle( ) & SWT.SEPARATOR ) != 0 )
					cellLength = SEPARATOR_WIDTH;
				else
				{
					cellLength = cellMinLength
							+ gc.textExtent( item.getText( ), DRAW_FLAGS ).x
							+ 1;
				}

				Rectangle cellRect = new Rectangle( lengthStart, VERTICAL_GAP
						+ rowHeight
						* rows, cellLength, rowHeight );
				lengthStart += cellLength;
				columns++;

				if ( ( lengthStart > length ) && ( columns > 1 ) )
				{
					if ( ( style & SWT.MULTI ) != 0 )
					{
						rows++;
						columns = 1;
						lengthStart = cellLength;
						cellRect.x = HORIZON_MARGIN;
						cellRect.y += rowHeight;

					}
					else
					{
						if ( index <= trackItemIndex )
						{
							removeItemTracking( );
						}
						chevronStartItem = item;
						cellRect.y = 0;
						cellRect.width = CHEVRON_LENGTH + HORIZON_MARGIN;
						cellRect.x = menubar.getClientArea( ).width
								- cellRect.width
								- 1;
						cellRect.height = rowHeight + verticalStart - 2;
						drawCommands.add( new MenuDrawCommand( cellRect ) );
						break;
					}
				}
				Rectangle selectRect = cellRect;
				selectRect.height -= ( VERTICAL_GAP );
				drawCommands.add( new MenuDrawCommand( item,
						cellRect,
						selectRect ) );
				index++;
			}
			gc.dispose( );

			int controlHeight = ( rows + 1 )
					* rowHeight
					+ 2
					* verticalStart
					- 2;
			if ( menubar.getBounds( ).height != controlHeight )
			{
				Rectangle rect = new Rectangle( menubar.getBounds( ).x,
						menubar.getBounds( ).y,
						menubar.getBounds( ).width,
						controlHeight );
				return rect;
			}
		}
		return menubar.getBounds( );
	}

	public void refresh( )
	{
		if ( menubar != null && !menubar.isDisposed( ) )
			menubar.redraw( );
	}

	private void removeItemTracking( )
	{
		if ( trackItemIndex != -1 && trackItemIndex < drawCommands.size( ) )
		{
			MenuDrawCommand dc = (MenuDrawCommand) drawCommands.get( trackItemIndex );
			if ( dc.getMenuItem( ) != null )
			{
				Event event = new Event( );
				event.data = dc.getMenuItem( );
				event.detail = SWT.Deactivate;
				menubar.notifyListeners( SWT.Deactivate, event );
			}
			trackItemIndex = -1;
		}
	}

	public void setMenu( CMenu menu )
	{
		if ( this.menu != null )
			MenuHolderManager.deRegistryShortcut( this );
		this.menu = menu;
		MenuHolderManager.registryShortcut( this );
	}

	public void setMultiLine( boolean multi )
	{
		if ( !multi )
			style = style & ~SWT.MULTI;
		else
			style |= SWT.MULTI;
	}

	public void setSelection( int index, boolean isSelected )
	{
		if ( drawCommands.size( ) > 0 )
		{
			// If no item is currently tracked then...
			if ( index > -1 && index < drawCommands.size( ) )
			{
				// ...start tracking the first valid command
				MenuDrawCommand dc = (MenuDrawCommand) drawCommands.get( index );
				if ( !dc.isSeparator( )
						&& ( dc.isChevron( ) || dc.isEnabled( ) ) )
				{
					refresh( );
					trackItemIndex = index;
					selected = isSelected;
					refresh( );
					drawCommand( trackItemIndex, true );
					if ( selected )
					{
						showMenu( dc );
					}
				}
			}
			else
			{
				selected = false;
				refresh( );
				drawCommand( trackItemIndex, false );
				trackItemIndex = -1;
				hideMenu( );
			}
		}
	}

	public void setTheme( ThemeRender theme )
	{
		if ( menubar == null || menubar.isDisposed( ) || theme == null )
			return;
		if ( !GraphicsUtil.checkGdip( ) && theme instanceof GlossyThemeRender )
			return;
		this.theme = theme;
		if ( theme.getMenubar_bgColors_percents( ) == null )
		{
			menubar.setBackground( theme.getMenubar_bgColors( )[0] );
		}
		else
			menubar.setBackground( theme.getMenubar_bgColors( ),
					theme.getMenubar_bgColors_percents( ),
					false );
	}

	private void showMenu( MenuDrawCommand command )
	{
		if ( command == null || getCurrentCommand( ) == null )
			return;
		if ( command.getMenuItem( ) != getCurrentCommand( ).getMenuItem( ) )
			return;
		if ( command.isChevron( ) )
		{
			CMenu menu = new CMenu( );
			int index = this.menu.indexOf( chevronStartItem );
			if ( index > 0 )
			{
				for ( int i = index; i < this.menu.getItemCount( ); i++ )
				{
					menu.addItem( this.menu.getItem( i ) );
				}
				if ( menu.getItemCount( ) > 0 )
				{
					drawUpwards = false;
					popupMenu = new MenuControl( this, theme );
					popupMenu.menu = menu;
					popupMenu.bar = this;
					popupMenu.screenPos = menubar.toDisplay( new Point( command.getDrawRect( ).x,
							command.getDrawRect( ).y
									+ command.getDrawRect( ).height ) );
					popupMenu.leftScreenPos = popupMenu.screenPos;
					popupMenu.aboveScreenPos = menubar.toDisplay( new Point( command.getDrawRect( ).x,
							command.getDrawRect( ).y + 2 ) );
					popupMenu.borderGap = command.getDrawRect( ).width;

					Event event = new Event( );
					event.data = popupMenu;
					event.type = SWT.OPEN;
					popupMenu.menu.fireMenuEvent( event );

					popupMenu.createAndShowWindow( );
					installMouseHook( );
				}
			}

		}
		else
		{
			if ( popupMenu != null || command.getMenuItem( ) == null )
			{
				return;
			}
			if ( !command.getMenuItem( ).isEnabled( ) )
				return;
			if ( command.getMenuItem( ).getMenu( ) == null )
				return;
			drawUpwards = false;
			popupMenu = new MenuControl( this, theme );
			popupMenu.menu = command.getMenuItem( ).getMenu( );
			popupMenu.bar = this;
			popupMenu.screenPos = menubar.toDisplay( new Point( command.getDrawRect( ).x,
					command.getDrawRect( ).y + command.getDrawRect( ).height ) );
			popupMenu.leftScreenPos = popupMenu.screenPos;
			popupMenu.aboveScreenPos = menubar.toDisplay( new Point( command.getDrawRect( ).x,
					command.getDrawRect( ).y + 2 ) );

			popupMenu.borderGap = command.getDrawRect( ).width;

			Event event = new Event( );
			event.data = popupMenu;
			event.type = SWT.OPEN;
			popupMenu.menu.fireMenuEvent( event );

			popupMenu.createAndShowWindow( );
			installMouseHook( );

		}
	}

	private void showMenuShadow( GC gc, Rectangle drawRect, CMenuItem mc )
	{
		/*
		 * Win98 && Win2000 doesn't support GDI+ ?
		 */
		if ( !GraphicsUtil.checkGdip( ) || !theme.isShowMenuItemShadow( ) )
			return;
		if ( mc == null )
			return;
		gc.setAdvanced( true );
		if ( mc.getMenu( ) != null
				&& mc.getMenu( ).getItemCount( ) > 0
				&& mc.isEnabled( ) )
		{
			if ( drawUpwards )
			{
				int rightLeft = drawRect.x + drawRect.width + 1;
				int rightTop = drawRect.y + 1;
				int top = drawRect.y + drawRect.height + 1;
				int left = drawRect.x + SHADOW_GAP;
				int width = drawRect.width + 1;
				int height = SHADOW_GAP;

				Pattern pattern = new Pattern( gc.getDevice( ),
						rightLeft,
						9999,
						rightLeft + SHADOW_GAP,
						9999,
						theme.getMenuShadowColor( ),
						48,
						theme.getMenuShadowColor( ),
						0 );
				gc.setForegroundPattern( pattern );
				gc.setBackgroundPattern( pattern );
				gc.fillRectangle( new Rectangle( rightLeft,
						rightTop,
						SHADOW_GAP,
						drawRect.height ) );
				pattern.dispose( );

				pattern = new Pattern( gc.getDevice( ),
						left + SHADOW_GAP,
						top - SHADOW_GAP,
						left,
						top + height,
						theme.getMenuShadowColor( ),
						64,
						theme.getMenuShadowColor( ),
						0 );
				gc.setForegroundPattern( pattern );
				gc.setBackgroundPattern( pattern );
				gc.fillRectangle( new Rectangle( left, top, SHADOW_GAP, height ) );
				pattern.dispose( );

				pattern = new Pattern( gc.getDevice( ),
						left + width - SHADOW_GAP - 2,
						top - SHADOW_GAP - 2,
						left + width,
						top + height,
						theme.getMenuShadowColor( ),
						64,
						theme.getMenuShadowColor( ),
						0 );
				gc.setForegroundPattern( pattern );
				gc.setBackgroundPattern( pattern );
				gc.fillRectangle( new Rectangle( left + width - SHADOW_GAP,
						top,
						SHADOW_GAP - 1,
						height - 1 ) );
				pattern.dispose( );

				pattern = new Pattern( gc.getDevice( ),
						9999,
						top,
						9999,
						top + height,
						theme.getMenuShadowColor( ),
						48,
						theme.getMenuShadowColor( ),
						0 );
				gc.setForegroundPattern( pattern );
				gc.setBackgroundPattern( pattern );
				gc.fillRectangle( new Rectangle( left + SHADOW_GAP, top, width
						- SHADOW_GAP
						* 2, height ) );
				pattern.dispose( );
			}
			else
			{
				int rightLeft = drawRect.x + drawRect.width + 1;
				int rightTop = drawRect.y + SHADOW_GAP;

				Pattern pattern = new Pattern( gc.getDevice( ),
						rightLeft - SHADOW_GAP,
						rightTop + SHADOW_GAP,
						rightLeft + SHADOW_GAP,
						rightTop,
						theme.getMenuShadowColor( ),
						64,
						theme.getMenuShadowColor( ),
						0 );
				gc.setForegroundPattern( pattern );
				gc.setBackgroundPattern( pattern );
				gc.fillRectangle( new Rectangle( rightLeft, drawRect.y
						+ SHADOW_GAP, SHADOW_GAP - 2, 1 ) );
				gc.fillRectangle( new Rectangle( rightLeft, drawRect.y
						+ 1
						+ SHADOW_GAP, SHADOW_GAP - 1, 1 ) );
				gc.fillRectangle( new Rectangle( rightLeft, drawRect.y
						+ 2
						+ SHADOW_GAP, SHADOW_GAP, SHADOW_GAP - 2 ) );
				pattern.dispose( );

				rightTop += SHADOW_GAP;

				pattern = new Pattern( gc.getDevice( ),
						rightLeft,
						9999,
						rightLeft + SHADOW_GAP,
						9999,
						theme.getMenuShadowColor( ),
						48,
						theme.getMenuShadowColor( ),
						0 );
				gc.setForegroundPattern( pattern );
				gc.setBackgroundPattern( pattern );
				gc.fillRectangle( new Rectangle( rightLeft,
						rightTop,
						SHADOW_GAP,
						drawRect.y + drawRect.height - rightTop + 1 ) );
				pattern.dispose( );
			}
		}
		gc.setAdvanced( false );
	}

	public boolean isAcitve( )
	{
		return trackItemIndex > -1;
	}

}
