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
import java.util.HashMap;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.graphics.Region;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.sf.feeling.swt.win32.extension.Win32;
import org.sf.feeling.swt.win32.extension.graphics.ImageRegion;
import org.sf.feeling.swt.win32.extension.shell.ShellIcon;
import org.sf.feeling.swt.win32.extension.shell.SystemMenuManager;
import org.sf.feeling.swt.win32.extension.shell.Windows;
import org.sf.feeling.swt.win32.extension.shell.listener.WindowMoveListener;
import org.sf.feeling.swt.win32.internal.extension.Extension;
import org.sf.feeling.swt.win32.internal.extension.util.ColorCache;
import org.sf.feeling.swt.win32.internal.extension.widgets.BorderCanvas;
import org.sf.feeling.swt.win32.internal.extension.widgets.ButtonCanvas;
import org.sf.feeling.swt.win32.internal.extension.widgets.IContainer;
import org.sf.feeling.swt.win32.internal.extension.widgets.ISkinable;
import org.sf.feeling.swt.win32.internal.extension.widgets.IWindow;
import org.sf.feeling.swt.win32.internal.extension.widgets.ImageSkin;
import org.sf.feeling.swt.win32.internal.extension.widgets.MouseResizeListener;
import org.sf.feeling.swt.win32.internal.extension.widgets.SkinableWidget;
import org.sf.feeling.swt.win32.internal.extension.widgets.UIManager;
import org.sf.feeling.swt.win32.internal.extension.widgets.WindowResizeHook;

/**
 * A Window can wrap a shell with specified custom style.
 * 
 * @author <a href="mailto:cnfree2000@hotmail.com">cnfree</a>
 * 
 */
public class Window extends SkinableWidget implements IWindow
{

	protected Shell shell;

	private Composite titleArea;

	private Composite buttonArea;

	private Map buttons;

	private ControlAdapter resizeListener;

	private ShellAdapter shellListener;

	private WindowResizeHook resizeHook;

	private SystemMenuManager menuManager;

	private String theme = ThemeConstants.STYLE_OFFICE2007;

	Window( )
	{
		super( );
	}

	public Window( int style )
	{
		super( null, style );
		initWindow( style );
	}

	public Window( Shell shell )
	{
		super( shell, SWT.NONE );
		initWindow( SWT.PRIMARY_MODAL );
	}

	public Window( Shell shell, int style )
	{
		super( shell, style );
		initWindow( style );
	}

	public Window( Shell shell, int style, String theme )
	{
		super( shell, style );
		initWindow( style );
		this.theme = theme;
	}

	public Shell getShell( )
	{
		return shell;
	}

	public Composite getClientArea( )
	{
		return super.getClientArea( );
	}

	public ISkinable getButtonSkin( int button )
	{
		return ( (ButtonCanvas) buttons.get( new Integer( button ) ) ).getDrawable( );
	}

	public void setButtonSkin( int button, ISkinable drawable )
	{
		ButtonCanvas canvse = ( (ButtonCanvas) buttons.get( new Integer( button ) ) );
		canvse.setDrawable( drawable );
		UIManager.transformWindowsButtonLayoutData( canvse, theme );
		buttonArea.layout( );
		getBorder( IContainer.BORDER_N ).layout( );
	}

	public Image getImage( )
	{
		Image image = shell.getImage( );
		if ( image == null )
		{
			Image[] images = shell.getImages( );
			if ( images != null && images.length > 0 )
				return images[0];
			else
				return null;
		}
		else
			return image;
	}

	public void setImage( Image image )
	{
		if ( image == null )
			return;
		shell.setImage( image );
		if ( isThemeInstalled( ) )
		{
			Label windowImage = (Label) titleArea.getData( "image" );
			GridData layoutData = (GridData) windowImage.getLayoutData( );
			layoutData.widthHint = 16;
			layoutData.heightHint = 16;
			windowImage.setLayoutData( layoutData );
			windowImage.setBackgroundImage( image );

			titleArea.layout( );
		}
	}

	public String getText( )
	{
		return shell.getText( );
	}

	public void setText( String text )
	{
		shell.setText( text );
		if ( isThemeInstalled( ) )
		{
			Composite windowTitle = (Composite) titleArea.getData( "title" );
			windowTitle.redraw( );
			windowTitle.update( );
		}
	}

	public void setBounds( Rectangle bounds )
	{
		shell.setBounds( bounds );
	}

	public void setBounds( int x, int y, int width, int height )
	{
		shell.setBounds( x, y, width, height );
	}

	public Rectangle getBounds( )
	{
		return shell.getBounds( );
	}

	public void setLocation( Point location )
	{
		shell.setLocation( location );
	}

	public void setLocation( int x, int y )
	{
		shell.setLocation( x, y );
	}

	public Point getLocation( )
	{
		return shell.getLocation( );
	}

	public void setSize( Point size )
	{
		shell.setSize( size );
	}

	public void setSize( int width, int height )
	{
		shell.setSize( width, height );
	}

	public Point getSize( )
	{
		return shell.getSize( );
	}

	public void setEnabled( int type, boolean enabled )
	{
		BorderCanvas border = (BorderCanvas) getBorder( IContainer.BORDER_N );
		ButtonCanvas button = (ButtonCanvas) buttons.get( new Integer( type ) );

		{
			GridData gridData = (GridData) button.getLayoutData( );
			gridData.exclude = !enabled;
			button.setLayoutData( gridData );
			button.setVisible( enabled );
		}

		switch ( type )
		{
			case ThemeConstants.BUTTON_MAX :
				ButtonCanvas revButton = (ButtonCanvas) buttons.get( new Integer( ThemeConstants.BUTTON_REV ) );
				GridData revData = (GridData) revButton.getLayoutData( );
				revData.exclude = !enabled;
				revButton.setLayoutData( revData );
				revButton.setVisible( enabled );
				break;
			case ThemeConstants.BUTTON_REV :
				ButtonCanvas maxButton = (ButtonCanvas) buttons.get( new Integer( ThemeConstants.BUTTON_REV ) );
				GridData maxData = (GridData) maxButton.getLayoutData( );
				maxData.exclude = !enabled;
				maxButton.setLayoutData( maxData );
				maxButton.setVisible( enabled );
				break;
		}
		border.layout( );
	}

	public boolean getEnabled( int type )
	{
		ButtonCanvas button = (ButtonCanvas) buttons.get( new Integer( type ) );
		return button.getVisible( );
	}

	public void layout( )
	{
		if ( menubar != null && !menubar.getControl( ).isDisposed( ) )
		{
			GridData gd = (GridData) menubar.getControl( ).getLayoutData( );
			gd.exclude = ( menubar.getMenu( ) == null || menubar.getMenu( )
					.getItemCount( ) == 0 );
			menubar.getControl( ).setLayoutData( gd );
			gd = (GridData) separator.getLayoutData( );
			gd.exclude = ( menubar.getMenu( ) == null || menubar.getMenu( )
					.getItemCount( ) == 0 );
			separator.setLayoutData( gd );
		}

		ISkinable drawable = ( (ButtonCanvas) buttons.get( new Integer( ThemeConstants.BUTTON_CLOSE ) ) ).getDrawable( );
		if ( drawable != null )
		{
			( (GridData) buttonArea.getLayoutData( ) ).heightHint = drawable.getInitHeight( )
					+ ( (GridData) titleArea.getLayoutData( ) ).verticalIndent;
		}

		getBorderContainer( ).layout( );
		shell.layout( );
		redrawShell( );
	}

	public MenuBar getMenuBar( )
	{
		return menubar;
	}

	public void open( )
	{
		if ( !UIManager.isInstall( this ) )
		{
			if ( theme != null )
				UIManager.installTheme( theme, this );
			else
				UIManager.installTheme( this );
		}
		Display display = shell.getDisplay( );
		layout( );
		hookShellUI( );
		shell.open( );
		while ( !shell.isDisposed( ) )
		{
			if ( !display.readAndDispatch( ) )
				display.sleep( );
		}
	}

	public void close( )
	{
		shell.close( );
	}

	public Display getDisplay( )
	{
		return shell.getDisplay( );
	}

	public void setCenter( )
	{
		Rectangle shellRect = shell.getBounds( );
		Rectangle displayRect = shell.getDisplay( ).getBounds( );
		int x = ( displayRect.width - shellRect.width ) / 2;
		int y = ( displayRect.height - shellRect.height ) / 2;
		shell.setLocation( x, y );
	}

	public void help( Event event )
	{
	}

	protected Composite createContents( Composite parent, int style )
	{
		if ( parent != null && style != SWT.NONE )
		{
			if ( parent instanceof Shell )
			{
				shell = new Shell( (Shell) parent, createShellStyle( style ) );
			}
			else
			{
				shell = new Shell( parent.getShell( ), createShellStyle( style ) );
			}
		}
		else if ( parent != null )
		{
			if ( parent instanceof Shell )
			{
				shell = new Shell( (Shell) parent );
			}
			else
			{
				shell = new Shell( parent.getShell( ) );
			}
		}
		else if ( style != SWT.NONE )
		{
			shell = new Shell( createShellStyle( style ) );
		}
		else
		{
			shell = new Shell( );
		}
		return shell;
	}

	protected void hookShellUI( )
	{
		resizeListener = new ControlAdapter( ) {

			public void controlResized( ControlEvent e )
			{
				if ( getClientArea( ) == null || getClientArea( ).isDisposed( ) )
					return;
				if ( shell.getMaximized( ) )
				{
					setShellMax( );
				}
				else if ( !shell.getMinimized( ) )
				{
					restoreShell( );
				}
				redrawShell( );

			}
		};
		shell.addControlListener( resizeListener );
		shellListener = new ShellAdapter( ) {

			public void shellActivated( ShellEvent e )
			{
				if ( getClientArea( ) == null || getClientArea( ).isDisposed( ) )
					return;
				redrawShell( );
			}
		};
		shell.addShellListener( shellListener );
		Windows.hideTitleBar( shell.handle );
		Windows.setBorderThick( shell.handle, false );
		Windows.setWindowAsDialogModel( shell.handle, false );
		if ( resizeHook == null )
			resizeHook = new WindowResizeHook( this );
		resizeHook.installHook( );
		if ( menuManager == null )
			menuManager = new SystemMenuManager( shell );
	}

	protected void unHookShellUI( )
	{
		unHookShellUI( true );
	}

	protected void unHookShellUI( boolean forceRestore )
	{
		shell.removeControlListener( resizeListener );
		shell.removeShellListener( shellListener );
		if ( UIManager.isInstall( this ) )
			UIManager.unInstallTheme( this );
		if ( forceRestore )
		{
			Display.getDefault( ).asyncExec( new Runnable( ) {

				public void run( )
				{
					if ( shell.isDisposed( ) )
						return;
					Windows.setWindowAsDialogModel( shell.handle,
							( shell.getStyle( ) & SWT.BORDER ) != 0 );
					Windows.showTitleBar( shell.handle );
					Windows.setBorderThick( shell.handle, true );
				}
			} );
		}
	}

	private int createShellStyle( int style )
	{
		style |= SWT.NO_BACKGROUND;
		if ( ( style & SWT.TITLE ) == 0 )
		{
			style = style | SWT.TITLE | SWT.CLOSE;
		}
		if ( ( style & SWT.BORDER ) != 0 )
		{
			style = style & ~SWT.BORDER;
		}
		return style;
	}

	protected void initWindow( int style )
	{
		if ( ( style & SWT.TITLE ) == 0 )
			return;
		Canvas northBorder = getBorder( IContainer.BORDER_N );
		{
			GridLayout gridLayout = new GridLayout( );
			gridLayout.numColumns = 2;
			gridLayout.horizontalSpacing = 0;
			gridLayout.verticalSpacing = 0;
			gridLayout.marginHeight = 0;
			gridLayout.marginWidth = 0;
			northBorder.setLayout( gridLayout );
		}

		{
			titleArea = new Composite( northBorder, SWT.NONE );
			GridData taData = new GridData( SWT.FILL, SWT.FILL, true, true );
			taData.verticalIndent = 3;
			titleArea.setLayoutData( taData );
			GridLayout taLayout = new GridLayout( );
			taLayout.numColumns = 3;
			taLayout.horizontalSpacing = 0;
			taLayout.verticalSpacing = 0;
			taLayout.marginHeight = 0;
			taLayout.marginWidth = 0;
			titleArea.setLayout( taLayout );

			createWindowImage( titleArea );

			createWindowTitle( titleArea );
		}

		{
			createButtons( northBorder );
		}
		boolean resizable = ( style & SWT.RESIZE ) != 0;

		{
			final MouseResizeListener mouseResizeListener = new MouseResizeListener( IContainer.BORDER_N,
					(BorderCanvas) getBorder( IContainer.BORDER_N ),
					resizable );
			getBorder( IContainer.BORDER_N ).addListener( SWT.MouseDown,
					mouseResizeListener );
			getBorder( IContainer.BORDER_N ).addListener( SWT.MouseMove,
					mouseResizeListener );
			getBorder( IContainer.BORDER_N ).addListener( SWT.MouseExit,
					mouseResizeListener );
		}
		{
			final MouseResizeListener mouseResizeListener = new MouseResizeListener( IContainer.BORDER_E,
					(BorderCanvas) getBorder( IContainer.BORDER_E ),
					resizable );
			getBorder( IContainer.BORDER_E ).addListener( SWT.MouseDown,
					mouseResizeListener );
			getBorder( IContainer.BORDER_E ).addListener( SWT.MouseMove,
					mouseResizeListener );
			getBorder( IContainer.BORDER_E ).addListener( SWT.MouseExit,
					mouseResizeListener );
		}
		{
			final MouseResizeListener mouseResizeListener = new MouseResizeListener( IContainer.BORDER_S,
					(BorderCanvas) getBorder( IContainer.BORDER_S ),
					resizable );
			getBorder( IContainer.BORDER_S ).addListener( SWT.MouseDown,
					mouseResizeListener );
			getBorder( IContainer.BORDER_S ).addListener( SWT.MouseMove,
					mouseResizeListener );
			getBorder( IContainer.BORDER_S ).addListener( SWT.MouseExit,
					mouseResizeListener );
		}
		{
			final MouseResizeListener mouseResizeListener = new MouseResizeListener( IContainer.BORDER_W,
					(BorderCanvas) getBorder( IContainer.BORDER_W ),
					resizable );
			getBorder( IContainer.BORDER_W ).addListener( SWT.MouseDown,
					mouseResizeListener );
			getBorder( IContainer.BORDER_W ).addListener( SWT.MouseMove,
					mouseResizeListener );
			getBorder( IContainer.BORDER_W ).addListener( SWT.MouseExit,
					mouseResizeListener );
		}
		{
			final MouseResizeListener mouseResizeListener = new MouseResizeListener( IContainer.BORDER_NE,
					(BorderCanvas) getBorder( IContainer.BORDER_NE ),
					resizable );
			getBorder( IContainer.BORDER_NE ).addListener( SWT.MouseDown,
					mouseResizeListener );
			getBorder( IContainer.BORDER_NE ).addListener( SWT.MouseMove,
					mouseResizeListener );
			getBorder( IContainer.BORDER_NE ).addListener( SWT.MouseExit,
					mouseResizeListener );
		}
		{
			final MouseResizeListener mouseResizeListener = new MouseResizeListener( IContainer.BORDER_NW,
					(BorderCanvas) getBorder( IContainer.BORDER_NW ),
					resizable );
			getBorder( IContainer.BORDER_NW ).addListener( SWT.MouseDown,
					mouseResizeListener );
			getBorder( IContainer.BORDER_NW ).addListener( SWT.MouseMove,
					mouseResizeListener );
			getBorder( IContainer.BORDER_NW ).addListener( SWT.MouseExit,
					mouseResizeListener );
		}
		{
			final MouseResizeListener mouseResizeListener = new MouseResizeListener( IContainer.BORDER_SE,
					(BorderCanvas) getBorder( IContainer.BORDER_SE ),
					resizable );
			getBorder( IContainer.BORDER_SE ).addListener( SWT.MouseDown,
					mouseResizeListener );
			getBorder( IContainer.BORDER_SE ).addListener( SWT.MouseMove,
					mouseResizeListener );
			getBorder( IContainer.BORDER_SE ).addListener( SWT.MouseExit,
					mouseResizeListener );
		}
		{
			final MouseResizeListener mouseResizeListener = new MouseResizeListener( IContainer.BORDER_SW,
					(BorderCanvas) getBorder( IContainer.BORDER_SW ),
					resizable );
			getBorder( IContainer.BORDER_SW ).addListener( SWT.MouseDown,
					mouseResizeListener );
			getBorder( IContainer.BORDER_SW ).addListener( SWT.MouseMove,
					mouseResizeListener );
			getBorder( IContainer.BORDER_SW ).addListener( SWT.MouseExit,
					mouseResizeListener );
		}

		{
			final WindowMoveListener mouseMoveListener = new WindowMoveListener( shell,
					true );
			titleArea.addListener( SWT.MouseDown, mouseMoveListener );
			titleArea.addListener( SWT.MouseMove, mouseMoveListener );
			titleArea.addListener( SWT.MouseUp, mouseMoveListener );

			Composite windowTitle = (Composite) titleArea.getData( "title" );
			windowTitle.addListener( SWT.MouseDown, mouseMoveListener );
			windowTitle.addListener( SWT.MouseMove, mouseMoveListener );
			windowTitle.addListener( SWT.MouseUp, mouseMoveListener );
		}

		{
			setEnabled( ThemeConstants.BUTTON_HELP, false );

			if ( ( style & SWT.MAX ) != 0 )
			{
				setEnabled( ThemeConstants.BUTTON_MAX, true );
			}
			else
			{
				setEnabled( ThemeConstants.BUTTON_MAX, false );
			}

			if ( ( style & SWT.MIN ) != 0 )
			{
				setEnabled( ThemeConstants.BUTTON_MIN, true );
			}
			else
			{
				setEnabled( ThemeConstants.BUTTON_MIN, false );
			}

			if ( ( style & SWT.CLOSE ) != 0 )
			{
				setEnabled( ThemeConstants.BUTTON_CLOSE, true );
			}
			else
			{
				setEnabled( ThemeConstants.BUTTON_CLOSE, false );
			}

			buttonArea.layout( );
		}

		checkButtonStatus( );
	}

	private void createButtons( final Composite parent )
	{

		buttonArea = new Composite( parent, SWT.NONE );

		buttonArea.setLayoutData( new GridData( SWT.FILL,
				SWT.BEGINNING,
				false,
				false ) );
		GridLayout bcLayout = new GridLayout( );
		bcLayout.numColumns = 5;
		bcLayout.horizontalSpacing = 0;
		bcLayout.verticalSpacing = 0;
		bcLayout.marginHeight = 0;
		bcLayout.marginWidth = 0;
		buttonArea.setLayout( bcLayout );

		buttons = new HashMap( 5 );

		int buttonType = 0;
		for ( buttonType = 0; buttonType < 5; buttonType++ )
		{
			final ButtonCanvas buttonCanvas = new ButtonCanvas( buttonArea,
					buttonType );
			buttonCanvas.setLayoutData( new GridData( GridData.FILL,
					GridData.FILL,
					false,
					false ) );

			switch ( buttonType )
			{
				case ThemeConstants.BUTTON_MAX :
					buttonCanvas.addListener( SWT.MouseUp, new Listener( ) {

						public void handleEvent( Event event )
						{
							if ( event.button != 1 )
								return;
							Rectangle rect = new Rectangle( 0,
									0,
									buttonCanvas.getBounds( ).width,
									buttonCanvas.getBounds( ).height );
							if ( !rect.contains( event.x, event.y ) )
								return;
							shell.setMaximized( true );
						}
					} );
					break;
				case ThemeConstants.BUTTON_REV :
					buttonCanvas.addListener( SWT.MouseUp, new Listener( ) {

						public void handleEvent( Event event )
						{
							if ( event.button != 1 )
								return;
							Rectangle rect = new Rectangle( 0,
									0,
									buttonCanvas.getBounds( ).width,
									buttonCanvas.getBounds( ).height );
							if ( !rect.contains( event.x, event.y ) )
								return;
							shell.setMaximized( false );
						}
					} );
					break;
				case ThemeConstants.BUTTON_MIN :
					buttonCanvas.addListener( SWT.MouseUp, new Listener( ) {

						public void handleEvent( Event event )
						{
							if ( event.button != 1 )
								return;
							Rectangle rect = new Rectangle( 0,
									0,
									buttonCanvas.getBounds( ).width,
									buttonCanvas.getBounds( ).height );
							if ( !rect.contains( event.x, event.y ) )
								return;
							shell.setMinimized( true );
						}
					} );
					break;
				case ThemeConstants.BUTTON_CLOSE :
					buttonCanvas.addListener( SWT.MouseUp, new Listener( ) {

						public void handleEvent( Event event )
						{
							if ( event.button != 1 )
								return;
							Rectangle rect = new Rectangle( 0,
									0,
									buttonCanvas.getBounds( ).width,
									buttonCanvas.getBounds( ).height );
							if ( !rect.contains( event.x, event.y ) )
								return;
							shell.close( );
						}
					} );
					break;
				case ThemeConstants.BUTTON_HELP :
					buttonCanvas.addListener( SWT.MouseUp, new Listener( ) {

						public void handleEvent( Event event )
						{
							if ( event.button != 1 )
								return;
							Rectangle rect = new Rectangle( 0,
									0,
									buttonCanvas.getBounds( ).width,
									buttonCanvas.getBounds( ).height );
							if ( !rect.contains( event.x, event.y ) )
								return;
							help( event );
						}
					} );
					break;
			}

			buttons.put( new Integer( buttonType ), buttonCanvas );
		}
	}

	protected Composite getBorderContainer( )
	{
		return shell;
	}

	protected void hiddenBorders( boolean isHidden )
	{
		Composite composite = getBorderContainer( );
		if ( composite == null )
			return;
		GridLayout layout = (GridLayout) composite.getLayout( );
		if ( layout == null )
			return;
		if ( isHidden )
		{
			layout.numColumns = 1;
		}
		else
		{
			layout.numColumns = 3;
		}
		hiddenControl( getBorder( IContainer.BORDER_NW ), isHidden );
		hiddenControl( getBorder( IContainer.BORDER_NE ), isHidden );
		hiddenControl( getBorder( IContainer.BORDER_W ), isHidden );
		hiddenControl( getBorder( IContainer.BORDER_E ), isHidden );
		hiddenControl( getBorder( IContainer.BORDER_SW ), isHidden );
		hiddenControl( getBorder( IContainer.BORDER_S ), isHidden );
		hiddenControl( getBorder( IContainer.BORDER_SE ), isHidden );
		hiddenControl( getBorder( IContainer.BORDER_S ).getParent( ), isHidden );
		composite.layout( );
	}

	private void hiddenControl( Control control, boolean isHidden )
	{
		GridData gd = (GridData) control.getLayoutData( );
		gd.exclude = isHidden;
		control.setLayoutData( gd );
		control.setVisible( !isHidden );
	}

	private void createWindowImage( Composite parent )
	{
		Label windowImage = new Label( parent, SWT.NONE );
		GridData gd = new GridData( SWT.CENTER, SWT.CENTER, false, false );
		gd.horizontalIndent = 1;
		gd.widthHint = 16;
		gd.heightHint = 16;
		windowImage.setLayoutData( gd );
		windowImage.setAlignment( SWT.CENTER );
		Image image = null;
		try
		{
			Class cl = Class.forName( "org.eclipse.swt.widgets.Decorations" );
			Field f = cl.getDeclaredField( "smallImage" );
			f.setAccessible( true );
			image = (Image) f.get( shell );
			if ( image == null && shell.getImages( ) != null )
			{
				if ( shell.getImages( ).length > 0 )
					image = shell.getImages( )[0];
			}
			if ( image == null && shell.getImage( ) != null )
			{
				image = shell.getImage( );
			}
			if ( image == null )
			{
				Image[] systemImages = ShellIcon.getSystemIcons( 2,
						ShellIcon.ICON_SMALL );
				if ( systemImages != null && systemImages.length > 0 )
				{
					image = systemImages[0];
					shell.setImage( image );
				}
			}
			if ( image != null )
			{
				if ( image.getImageData( ).width != 16 )
				{
					Image newImage = new Image( null, image.getImageData( )
							.scaledTo( 16, 16 ) );
					windowImage.setImage( newImage );
				}
				else
					windowImage.setImage( image );
			}
		}
		catch ( Exception e )
		{
			SWT.error( SWT.ERROR_UNSPECIFIED, e );
		}
		windowImage.addListener( SWT.MouseDown, new Listener( ) {

			public void handleEvent( Event event )
			{
				if ( !menuManager.isMenuVisiable( ) )
					menuManager.showMenu( getClientArea( ).getParent( )
							.toDisplay( new Point( 0, 0 ) ) );
			}

		} );

		windowImage.addListener( SWT.MouseDoubleClick, new Listener( ) {

			public void handleEvent( Event event )
			{
				shell.close( );
			}

		} );

		parent.setData( "image", windowImage );
	}

	private void createWindowTitle( Composite parent )
	{
		final Composite windowTitle = new Composite( parent, SWT.NONE );
		GridData wtData = new GridData( GridData.FILL,
				GridData.FILL,
				true,
				true );
		wtData.horizontalIndent = 5;
		windowTitle.setLayoutData( wtData );
		Listener listener = new Listener( ) {

			public void handleEvent( Event e )
			{
				switch ( e.type )
				{
					case SWT.Paint :
						FontData tFontData = e.gc.getFont( ).getFontData( )[0];
						tFontData.setStyle( UIManager.getTitleBarFontStyle( theme ) );
						Font tFont = new Font( getDisplay( ), tFontData );
						e.gc.setFont( tFont );

						int fontHeight = e.gc.getFontMetrics( ).getHeight( );
						int areaHeight = titleArea.getBounds( ).height;

						Color foreground = getBorder( IContainer.BORDER_N ).getForeground( );

						{
							if ( UIManager.isTitleUsedShadow( theme ) )
							{
								RGB fRGB = foreground.getRGB( );
								RGB nfRGB = new RGB( 255 - fRGB.red,
										255 - fRGB.green,
										255 - fRGB.blue );
								Color eForeground = ColorCache.getInstance( )
										.getColor( nfRGB );
								e.gc.setForeground( eForeground );
								e.gc.drawText( shell.getText( ),
										1,
										( areaHeight - fontHeight ) / 2 + 1,
										true );
							}
						}

						e.gc.setForeground( foreground );
						e.gc.drawText( shell.getText( ),
								0,
								( areaHeight - fontHeight ) / 2,
								true );
						tFont.dispose( );

						break;
					case SWT.MouseDoubleClick :
						if ( getEnabled( ThemeConstants.BUTTON_MAX )
								|| getEnabled( ThemeConstants.BUTTON_REV ) )
						{
							if ( shell.getMaximized( ) )
							{
								shell.setMaximized( false );
							}
							else
							{
								shell.setMaximized( true );
							}
						}
						break;
				}
			}
		};
		windowTitle.addListener( SWT.Paint, listener );
		windowTitle.addListener( SWT.MouseDoubleClick, listener );
		windowTitle.addListener( SWT.MenuDetect, new Listener( ) {

			public void handleEvent( Event event )
			{
				menuManager.showMenu( );
			}

		} );
		parent.setData( "title", windowTitle );
	}

	public void redrawShell( )
	{
		if ( !UIManager.isInstall( this ) )
		{
			Extension.SetWindowRgn( shell.handle, 0, true );
			return;
		}
		Region region = new Region( );
		Rectangle rect = shell.getClientArea( );
		region.add( rect );
		if ( !shell.getMaximized( ) )
		{
			ImageData data = ( (ImageSkin) Window.this.getSkin( IContainer.BORDER_NW ) ).getImage( )
					.getImageData( );
			Region opaqueRegion = ImageRegion.calculateOpaquePath( data,
					data.transparentPixel );
			region.subtract( opaqueRegion );
			opaqueRegion.dispose( );

			data = ( (ImageSkin) Window.this.getSkin( IContainer.BORDER_NE ) ).getImage( )
					.getImageData( );
			opaqueRegion = ImageRegion.calculateOpaquePath( data,
					data.transparentPixel,
					rect.width - data.width,
					0 );
			region.subtract( opaqueRegion );
			opaqueRegion.dispose( );
			data = ( (ImageSkin) Window.this.getSkin( IContainer.BORDER_SW ) ).getImage( )
					.getImageData( );
			opaqueRegion = ImageRegion.calculateOpaquePath( data,
					data.transparentPixel,
					0,
					rect.height - data.height );
			region.subtract( opaqueRegion );
			opaqueRegion.dispose( );

			data = ( (ImageSkin) Window.this.getSkin( IContainer.BORDER_SE ) ).getImage( )
					.getImageData( );
			opaqueRegion = ImageRegion.calculateOpaquePath( data,
					data.transparentPixel,
					rect.width - data.width,
					rect.height - data.height );
			region.subtract( opaqueRegion );
			opaqueRegion.dispose( );
		}

		int hRegion = 0;
		hRegion = Extension.CreateRectRgn( 0, 0, 0, 0 );
		Extension.CombineRgn( hRegion, region.handle, hRegion, Win32.RGN_OR );
		Extension.SetWindowRgn( shell.handle, hRegion, true );
		Extension.DeleteObject( hRegion );
		region.dispose( );
	}

	protected void setShellMax( )
	{
		hiddenBorders( true );
		checkButtonStatus( true );
	}

	protected void restoreShell( )
	{
		hiddenBorders( false );
		checkButtonStatus( false );
	}

	private void checkButtonStatus( boolean maximized )
	{
		if ( getClientArea( ) == null || getClientArea( ).isDisposed( ) )
			return;
		if ( !getEnabled( ThemeConstants.BUTTON_MAX )
				&& !getEnabled( ThemeConstants.BUTTON_REV ) )
		{
			return;
		}

		ButtonCanvas max = (ButtonCanvas) buttons.get( new Integer( ThemeConstants.BUTTON_MAX ) );
		hiddenControl( max, maximized );

		ButtonCanvas rev = (ButtonCanvas) buttons.get( new Integer( ThemeConstants.BUTTON_REV ) );
		hiddenControl( rev, !maximized );

		buttonArea.layout( );
		getBorder( IContainer.BORDER_N ).layout( );
	}

	private void checkButtonStatus( )
	{
		if ( getClientArea( ) == null || getClientArea( ).isDisposed( ) )
			return;
		if ( !getEnabled( ThemeConstants.BUTTON_MAX )
				&& !getEnabled( ThemeConstants.BUTTON_REV ) )
		{
			return;
		}

		boolean maximized = shell.getMaximized( );
		checkButtonStatus( maximized );
	}

	public boolean isThemeInstalled( )
	{
		return UIManager.isInstall( this );
	}

	public String getTheme( )
	{
		return theme;
	}

	public void setTheme( String theme )
	{
		this.theme = theme;
	}
}
